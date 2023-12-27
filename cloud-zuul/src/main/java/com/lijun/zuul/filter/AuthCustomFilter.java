package com.lijun.zuul.filter;

import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

/**
 * 授权过滤器
 *
 * @author : LiJun
 * @date : 2023-12-19 15:45
 **/
@Slf4j
@Component
public class AuthCustomFilter extends BaseCustomFilter {

    /**
     * 定义过滤器类型
     *      pre     ：   在执行路由请求之前执行
     *      routing ：   在路由请求时调用
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
        return 0;
    }

    /**
     * 该过滤器是否生效
     */
    @Override
    public boolean shouldFilter() {
        //获取请求上下文，拿到request信息
        RequestContext currentContext = RequestContext.getCurrentContext();

        //不执行该过滤器
        if (isBreak()){
            return false;
        }


        HttpServletRequest request = currentContext.getRequest();
        String requestUri= request.getRequestURI();
        log.info("shouldFilter: "+requestUri);

        //todo 是否需要授权

        return false;
    }

    /**
     * 拦截后的具体业务逻辑
     * @return  返回NULL,继续向后执行
     */
    @Override
    public Object run() {
        //上下文贯穿 所有filter，包含所有参数
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        log.info("run: "+request.getRequestURI());

        //todo 授权校验

        return null;
    }
}
