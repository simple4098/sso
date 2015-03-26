package com.fanqie.sso.jdbc;

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
import javax.validation.constraints.NotNull;
import java.security.GeneralSecurityException;

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
    private String sql;

    @NotNull
    private String sqlSalt;

    protected final HandlerResult authenticateUsernamePasswordInternal(final UsernamePasswordCredential credential) throws GeneralSecurityException, PreventedException {
        final String username = credential.getUsername();
        final String password = credential.getPassword();
        try {
            final String salt = getJdbcTemplate().queryForObject(this.sqlSalt, String.class, username,username);
            final String encryptedPassword = this.getPasswordEncoder().encode(password+salt);
            final String dbPassword = getJdbcTemplate().queryForObject(this.sql, String.class, username,username);
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


    public void setSql(final String sql) {
        this.sql = sql;
    }

    public void setSqlSalt(final String sqlSalt) {
        this.sqlSalt = sqlSalt;
    }
}
