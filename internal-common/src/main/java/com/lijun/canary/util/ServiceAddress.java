package com.lijun.canary.util;

/**
 * @author : LiJun
 * @since : 2023-12-22 18:59
 **/
public class ServiceAddress {

    /**
     * 短信服务名
     */
    public static final String SERVICE_ACCOUNT_URL = "http://service-account";

    /**
     * 用户服务
     */
    public static final String SERVICE_ORDER_URL = "http://service-order";


    public static String getServiceAccountUrl(String path){
        return SERVICE_ACCOUNT_URL+path;
    }
}
