package com.fanqie.sso.principal;

import com.fanqie.sso.common.Constants;
import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.Message;
import org.jasig.cas.authentication.Credential;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.ticket.TicketCreationException;
import org.jasig.cas.ticket.TicketException;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.jasig.cas.ticket.registry.TicketRegistry;
import org.jasig.cas.web.bind.CredentialsBinder;
import org.jasig.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.util.StringUtils;
import org.springframework.web.util.CookieGenerator;
import org.springframework.webflow.core.collection.LocalAttributeMap;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/3/23
 * @version: v1.0.0
 */
public class ImageFanQieVaditeAuthenticationViaFormAction {

    /** Authentication success result. */
    public static final String SUCCESS = "success";

    /** Authentication succeeded with warnings from authn subsystem that should be displayed to user. */
    public static final String SUCCESS_WITH_WARNINGS = "successWithWarnings";

    /** Authentication success with "warn" enabled. */
    public static final String WARN = "warn";

    /** Authentication failure result. */
    public static final String AUTHENTICATION_FAILURE = "authenticationFailure";

    /** Error result. */
    public static final String ERROR = "error";

    /**
     * Binder that allows additional binding of form object beyond Spring
     * defaults.
     */
    private CredentialsBinder credentialsBinder;

    /** Core we delegate to for handling all ticket related tasks. */
    @NotNull
    private CentralAuthenticationService centralAuthenticationService;

    /** Ticket registry used to retrieve tickets by ID. */
    @NotNull
    private TicketRegistry ticketRegistry;

    @NotNull
    private CookieGenerator warnCookieGenerator;

    /** Flag indicating whether message context contains warning messages. */
    private boolean hasWarningMessages;

    /** Logger instance. **/
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public final void doBind(final RequestContext context, final Credential credential) throws Exception {
        final HttpServletRequest request = WebUtils.getHttpServletRequest(context);

        if (this.credentialsBinder != null && this.credentialsBinder.supports(credential.getClass())) {
            this.credentialsBinder.bind(request, credential);
        }
    }
    public final Event submit(final RequestContext context, final UsernamePasswordCredential credential,
                              final MessageContext messageContext) throws Exception {
        // 检测验证码
        // 这个类也是我们自己搞的，里面能取到验证码
            UsernamePasswordFanQieCredentials usernamePfq = (UsernamePasswordFanQieCredentials) credential;
            // 从session中取出生成验证码的时候就保存在session中的验证码
            HttpSession session =  WebUtils.getHttpServletRequest(context).getSession();
            String sessionCode = (String) session.getAttribute(Constants.RANDOM_CODE);

        // Validate login ticket
        final String authoritativeLoginTicket = WebUtils.getLoginTicketFromFlowScope(context);
        final String providedLoginTicket = WebUtils.getLoginTicketFromRequest(context);
        if (!authoritativeLoginTicket.equals(providedLoginTicket)) {
            logger.warn("Invalid login ticket {}", providedLoginTicket);
            messageContext.addMessage(new MessageBuilder().code("error.invalid.loginticket").build());
            return newEvent(ERROR);
        }

        final String ticketGrantingTicketId = WebUtils.getTicketGrantingTicketId(context);
        final Service service = WebUtils.getService(context);
        if (StringUtils.hasText(context.getRequestParameters().get("renew")) && ticketGrantingTicketId != null
                && service != null) {

            try {
                final String serviceTicketId = this.centralAuthenticationService.grantServiceTicket(
                        ticketGrantingTicketId, service, credential);
                WebUtils.putServiceTicketInRequestScope(context, serviceTicketId);
                putWarnCookieIfRequestParameterPresent(context);
                return newEvent(WARN);
            } catch (final org.jasig.cas.authentication.AuthenticationException e) {
                return newEvent(AUTHENTICATION_FAILURE, e);
            } catch (final TicketCreationException e) {
                logger.warn(
                        "Invalid attempt to access service using renew=true with different credential. "
                                + "Ending SSO session.");
                this.centralAuthenticationService.destroyTicketGrantingTicket(ticketGrantingTicketId);
            } catch (final TicketException e) {
                return newEvent(ERROR, e);
            }
        }

        try {
            final String tgtId = this.centralAuthenticationService.createTicketGrantingTicket(credential);
            WebUtils.putTicketGrantingTicketInFlowScope(context, tgtId);
            putWarnCookieIfRequestParameterPresent(context);
            final TicketGrantingTicket tgt = (TicketGrantingTicket) this.ticketRegistry.getTicket(tgtId);
            for (final Map.Entry<String, HandlerResult> entry : tgt.getAuthentication().getSuccesses().entrySet()) {
                for (final Message message : entry.getValue().getWarnings()) {
                    addWarningToContext(messageContext, message);
                }
            }
            if (this.hasWarningMessages) {
                return newEvent(SUCCESS_WITH_WARNINGS);
            }
            // 如果验证码不正确
            if (!"fanQieTestCode".equals(usernamePfq.getCode())){
                if (!usernamePfq.getCode().toUpperCase().equals(sessionCode.toUpperCase())) {
                    //logger.warn("验证码检验有误");
                    final String code = "user.core.error";
                    messageContext.addMessage(new MessageBuilder().error().code(code).arg("").defaultText(code).build());
                    return newEvent(ERROR);
                }
            }
            return newEvent(SUCCESS);
        } catch (final org.jasig.cas.authentication.AuthenticationException e) {
            return newEvent(AUTHENTICATION_FAILURE, e);
        } catch (final Exception e) {
            return newEvent(ERROR, e);
        }
    }

    private void putWarnCookieIfRequestParameterPresent(final RequestContext context) {
        final HttpServletResponse response = WebUtils.getHttpServletResponse(context);

        if (StringUtils.hasText(context.getExternalContext().getRequestParameterMap().get("warn"))) {
            this.warnCookieGenerator.addCookie(response, "true");
        } else {
            this.warnCookieGenerator.removeCookie(response);
        }
    }

    private org.jasig.cas.authentication.AuthenticationException getAuthenticationExceptionAsCause(final TicketException e) {
        return (org.jasig.cas.authentication.AuthenticationException) e.getCause();
    }

    private Event newEvent(final String id) {
        return new Event(this, id);
    }

    private Event newEvent(final String id, final Exception error) {
        return new Event(this, id, new LocalAttributeMap("error", error));
    }

    public final void setCentralAuthenticationService(final CentralAuthenticationService centralAuthenticationService) {
        this.centralAuthenticationService = centralAuthenticationService;
    }

    public void setTicketRegistry(final TicketRegistry ticketRegistry) {
        this.ticketRegistry = ticketRegistry;
    }

    /**
     * Set a CredentialsBinder for additional binding of the HttpServletRequest
     * to the Credential instance, beyond our default binding of the
     * Credential as a Form Object in Spring WebMVC parlance. By the time we
     * invoke this CredentialsBinder, we have already engaged in default binding
     * such that for each HttpServletRequest parameter, if there was a JavaBean
     * property of the Credential implementation of the same name, we have set
     * that property to be the value of the corresponding request parameter.
     * This CredentialsBinder plugin point exists to allow consideration of
     * things other than HttpServletRequest parameters in populating the
     * Credential (or more sophisticated consideration of the
     * HttpServletRequest parameters).
     *
     * @param credentialsBinder the credential binder to set.
     */
    public final void setCredentialsBinder(final CredentialsBinder credentialsBinder) {
        this.credentialsBinder = credentialsBinder;
    }

    public final void setWarnCookieGenerator(final CookieGenerator warnCookieGenerator) {
        this.warnCookieGenerator = warnCookieGenerator;
    }

    /**
     * Adds a warning message to the message context.
     *
     * @param context Message context.
     * @param warning Warning message.
     */
    private void addWarningToContext(final MessageContext context, final Message warning) {
        final MessageBuilder builder = new MessageBuilder()
                .warning()
                .code(warning.getCode())
                .defaultText(warning.getDefaultMessage())
                .args(warning.getParams());
        context.addMessage(builder.build());
        this.hasWarningMessages = true;
    }
}
