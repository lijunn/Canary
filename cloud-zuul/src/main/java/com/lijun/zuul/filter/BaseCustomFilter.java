package com.lijun.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 自定义过滤器 基础类
 * @author : LiJun
 * @date : 2023-12-27 17:19
 **/
public abstract class BaseCustomFilter extends ZuulFilter {


    /**
     * 查询是否需要中断当前过滤器，默认值 false
     * @return true：中断，false 不中断
     */
    public boolean isBreak() {
        return getBoolean("break_next_custom_filter", false);
    }

    /**
     * 设置是否中断后续自定义过滤器
     * @param b 是否中断
     */
    public void setBreak(boolean b) {
        RequestContext.getCurrentContext().set("break_next_custom_filter", b);
    }


    public boolean getBoolean(String key, boolean defaultResponse) {
        Boolean b = (Boolean) RequestContext.getCurrentContext().get(key);
        if (b != null) {
            return b;
        }
        return defaultResponse;
    }
}
