package com.lijun.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : LiJun
 * @date : 2023-12-19 15:45
 **/
public class AuthFilter extends ZuulFilter {

    /**
     * 定义过滤器类型
     *      pre     ：   在执行路由请求之前执行
     *      routing ：   在路由请求是调用
     *      post    ：   在routing和error过滤器之后执行
     *      error   ：   处理请求出现异常的时候执行
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 拦截器顺序，值越小，越在前
     */
    @Override
    public int filterOrder() {
        return -1;
    }

    /**
     * 该过滤器是否生效
     */
    @Override
    public boolean shouldFilter() {
        //获取请求上下文，拿到request信息
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String requestUri= request.getRequestURI();

        String checkUri = "api";
        return requestUri.contains(checkUri);
    }

    /**
     * 拦截后的具体业务逻辑
     * @return  返回NULL,继续向后执行
     */
    @Override
    public Object run() throws ZuulException {
        //上下文贯穿 所有filter，包含所有参数
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        // option请求，直接放行
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            return null;
        }

        String token = request.getHeader("Authorization");

        return null;
    }
}
