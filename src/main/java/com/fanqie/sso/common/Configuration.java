package com.fanqie.sso.common;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/5/6
 * @version: v1.0.0
 */
public class Configuration {
    private static String WEB_HOST="";


    public  void setWebHost(String webHost) {
        Configuration.WEB_HOST = webHost;
    }

    public static String getWebHost() {
        return WEB_HOST;
    }
}
