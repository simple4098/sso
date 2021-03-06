package com.fanqie.sso.principal;

import com.fanqie.sso.dao.UserDao;
import org.jasig.cas.authentication.Credential;
import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.authentication.principal.PrincipalResolver;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/4/3.
 */
public class PersonDirectoryPrincipalFanQieResolver  implements PrincipalResolver {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NotNull
    private UserDao userDao;

    public PersonDirectoryPrincipalFanQieResolver() {
    }

    public boolean supports(Credential credential) {
        return true;
    }

    public final Principal resolve(Credential credential) {
        this.logger.debug("Attempting to resolve a principal...");
        String principalId = this.extractPrincipalId(credential);
        Map<String,Object> map =  userDao.findUserInfo(principalId, principalId);
        Map<String,Object> convertedAttributes = new HashMap<String, Object>();
        if (map!=null){
            //Integer parentId = (Integer)map.get("parent_id");
            Integer id = (Integer)map.get("id");
            Integer innId = (Integer)map.get("inn_id");
            convertedAttributes.put("userId",id);
            convertedAttributes.put("innId",innId);
            return new SimplePrincipal(principalId, convertedAttributes);
        }else {
            return  null;
        }
    }


    protected String extractPrincipalId(Credential credential) {
        return credential.getId();
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
