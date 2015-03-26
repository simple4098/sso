package com.fanqie.sso.principal;

import org.jasig.cas.authentication.UsernamePasswordCredential;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/3/23
 * @version: v1.0.0
 */
public class UsernamePasswordFanQieCredentials extends UsernamePasswordCredential {

    @NotNull
    @Size(min=1, message = "user.core.required")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
