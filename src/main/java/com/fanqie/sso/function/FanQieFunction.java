package com.fanqie.sso.function;

import com.fanqie.sso.common.Configuration;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;

/**
 * DESC :函数， 读取默认的应用地址
 * @author : 番茄木-ZLin
 * @data : 2015/3/26
 * @version: v1.0.0
 */
public class FanQieFunction {
    private static  final Logger log = Logger.getLogger(FanQieFunction.class);
    public FanQieFunction() {
    }

    /**
     * 获取默认的 web应用的主机名称
     */
    public static String obtDefaultWebHost(){
//        InputStream in =FanQieFunction.class.getResourceAsStream("/service.properties");
//        Properties p = new Properties();
//        try {
//            p.load(in);
//        } catch (IOException e) {
//            log.error("读取配置文件异常:"+e);
//        }
//        return  p.getProperty("web.app.url");
        return Configuration.getWebHost();
    }

   /**
     * 获取默认的 web登录后跳转的页面
     */
    public static String obtDefaultWebIndex(){
        //进行URL 编码
        try {
            return URLEncoder.encode(obtDefaultWebHost()+"/index","UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("URL 编码异常:"+e);
        }
        return  "";
    }

    /**
     * 读取默认的应用的首页地址
     */
    public static String obtDefaultWebUrl(){
        return  obtDefaultWebHost();
    }

    /**
     * 读取默认的应用的注册地址
     */
    public static String obtRegisterUrl(){
        return  Configuration.getRegisterUrl()+"?v="+new Date().getTime();
    }

    //忘记密码跳转页面
    public static String forgetPassword(){
        return  Configuration.getForgetPassword();
    }

    /**
     * 解码跳转的url
     */
    public static String obtRunUrl(String encodeUrl){
        try {
           return URLDecoder.decode(encodeUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return  obtDefaultWebUrl();
        }
    }
}
