package com.fanqie.sso.principal;

import org.jasig.cas.authentication.Credential;
import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.authentication.principal.PrincipalResolver;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.jasig.services.persondir.IPersonAttributeDao;
import org.jasig.services.persondir.support.StubPersonAttributeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/4/3.
 */
public class PersonDirectoryPrincipalFanQieResolver  implements PrincipalResolver {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private boolean returnNullIfNoAttributes = false;
    @NotNull
    private IPersonAttributeDao attributeRepository = new StubPersonAttributeDao(new HashMap());
    private String principalAttributeName;

    @NotNull
    private JdbcTemplate jdbcTemplate;

    @NotNull
    private DataSource dataSource;
    @NotNull
    private String sqlId;

    public PersonDirectoryPrincipalFanQieResolver() {
    }

    public boolean supports(Credential credential) {
        return true;
    }

    public final Principal resolve(Credential credential) {
        this.logger.debug("Attempting to resolve a principal...");
        String principalId = this.extractPrincipalId(credential);
        String id = getJdbcTemplate().queryForObject(this.sqlId, String.class, principalId,principalId);
        Map<String,Object> convertedAttributes = new HashMap<String, Object>();
        if(principalId == null) {
            this.logger.debug("Got null for extracted principal ID; returning null.");
            return null;
        } else {
            this.logger.debug("Creating SimplePrincipal for [{}]", principalId);
            convertedAttributes.put("userId",id);
            return new SimplePrincipal(principalId, convertedAttributes);
        }
    }

    public final void setAttributeRepository(IPersonAttributeDao attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    public void setReturnNullIfNoAttributes(boolean returnNullIfNoAttributes) {
        this.returnNullIfNoAttributes = returnNullIfNoAttributes;
    }

    public void setPrincipalAttributeName(String attribute) {
        this.principalAttributeName = attribute;
    }

    protected String extractPrincipalId(Credential credential) {
        return credential.getId();
    }

    /**
     * Method to set the datasource and generate a JdbcTemplate.
     *
     * @param dataSource the datasource to use.
     */
    public final void setDataSource(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    /**
     * Method to return the jdbcTemplate.
     *
     * @return a fully created JdbcTemplate.
     */
    protected final JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    protected final DataSource getDataSource() {
        return this.dataSource;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }
}
