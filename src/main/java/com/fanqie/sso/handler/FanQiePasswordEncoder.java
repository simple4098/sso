package com.fanqie.sso.handler;

import org.apache.log4j.Logger;
import org.jasig.cas.authentication.handler.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * DESC : 自定义加密方式
 * @author : 番茄木-ZLin
 * @data : 2015/3/23
 * @version: v1.0.0
 */
public class FanQiePasswordEncoder  implements PasswordEncoder {
    private  static  final Logger log =   Logger.getLogger(FanQiePasswordEncoder.class);

    public String encode(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte byteData[] = md.digest();
            // 二进制转换为十六进制
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            log.error("加密异常"+e);
            return null;
        }
    }
}
