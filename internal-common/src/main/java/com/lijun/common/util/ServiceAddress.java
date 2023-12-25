package com.lijun.common.util;

/**
 * @author : LiJun
 * @date : 2023-12-22 18:59
 **/
public class ServiceAddress {

    /**
     * 短信服务名
     */
    public static final String SERVICE_SMS_URL = "http://service-sms";

    /**
     * 用户服务
     */
    public static final String SERVICE_USER_URL = "http://service-user";


    public static String getServiceSmsUrl(String path){
        return SERVICE_SMS_URL+path;
    }
}
