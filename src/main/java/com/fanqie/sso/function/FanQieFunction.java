package com.fanqie.sso.function;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
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
     * 读取默认的应用的首页地址
     */
    public static String obtDefaultWebUrl(){
        //FanQieFunction.class.getClassLoader().getResourceAsStream
        InputStream in =FanQieFunction.class.getResourceAsStream("/service.properties");
        Properties p = new Properties();
        try {
            p.load(in);
        } catch (IOException e) {
            log.error("读取配置文件异常:"+e);
        }
        return  p.getProperty("web.app.url");
    }

    /**
     * 获取应用的首页
     * @return
     *//*
    public static String obtWebIndexUrl(){
        InputStream in =Object.class.getResourceAsStream("/service.properties");
        Properties p = new Properties();
        try {
            p.load(in);
        } catch (IOException e) {
            log.error("读取配置文件异常:"+e);
        }
        return  p.getProperty("web.app.index");
    }*/
}
