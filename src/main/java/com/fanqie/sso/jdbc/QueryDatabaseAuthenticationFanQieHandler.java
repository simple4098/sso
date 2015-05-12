package com.fanqie.sso.jdbc;

import com.fanqie.sso.dao.UserDao;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jasig.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.validation.constraints.NotNull;
import java.security.GeneralSecurityException;
import java.util.Map;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/3/23
 * @version: v1.0.0
 */
public  class QueryDatabaseAuthenticationFanQieHandler extends AbstractJdbcUsernamePasswordAuthenticationHandler{
    private  static  final Logger log =   Logger.getLogger(QueryDatabaseAuthenticationFanQieHandler.class);

    @NotNull
    private UserDao userDao;

    protected final HandlerResult authenticateUsernamePasswordInternal(final UsernamePasswordCredential credential) throws GeneralSecurityException, PreventedException {
        final String username = credential.getUsername();
        final String password = credential.getPassword();
        try {
            if (StringUtils.isEmpty(username)){
                throw new LoginException("userName is not Empty");
            }
            if (StringUtils.isEmpty(password)){
                throw new LoginException("password is not Empty");
            }
            Map<String,Object> map = userDao.findUserInfo(username, username);
            String salt = (String)map.get("salt");
            String dbPassword = (String)map.get("password");
            final String encryptedPassword = this.getPasswordEncoder().encode(password+salt);
            if (!dbPassword.equals(encryptedPassword)) {
                throw new FailedLoginException("Password does not match value on record.");
            }
        } catch (final IncorrectResultSizeDataAccessException e) {
            if (e.getActualSize() == 0) {
                throw new AccountNotFoundException(username + " not found with SQL query");
            } else {
                throw new FailedLoginException("Multiple records found for " + username);
            }
        } catch (final DataAccessException e) {
            throw new PreventedException("SQL exception while executing query for " + username, e);
        }
        return createHandlerResult(credential, new SimplePrincipal(username), null);
    }



    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
