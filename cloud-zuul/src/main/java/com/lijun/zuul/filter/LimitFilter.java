package com.lijun.zuul.filter;

import cn.hutool.json.JSONUtil;
import com.google.common.util.concurrent.RateLimiter;
import com.lijun.common.constant.CommonStatusEnum;
import com.lijun.common.dto.ResponseResult;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 网关限流过滤器
 * @author : LiJun
 * @date : 2023-12-27 14:41
 **/
@Component
public class LimitFilter extends BaseCustomFilter {

    /**
     * 创建令牌桶，设置令牌生成速率为：每秒2个
     * 如果参数设置为 0.1 表示每秒10个
     */
    @SuppressWarnings("all")
    public static final RateLimiter RATE_LIMITER = RateLimiter.create(10);


    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext currentContext = RequestContext.getCurrentContext();

        //没获取到令牌
        if (!RATE_LIMITER.tryAcquire()){
            //设置后续 route 类型Filter不执行，即请求不转发给后续服务
            currentContext.setSendZuulResponse(false);
            //设置后续自定义Filter不执行
            setBreak(true);

            currentContext.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
            currentContext.getResponse().setContentType("application/json; charset=utf-8");
            currentContext.setResponseBody(JSONUtil.toJsonStr(ResponseResult.limit("网关限流")));
        }
        return null;
    }
}
