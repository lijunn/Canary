package com.lijun.zuul.filter;

import cn.hutool.json.JSONUtil;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.google.common.util.concurrent.RateLimiter;
import com.lijun.common.dto.ResponseResult;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 网关限流过滤器
 *
 * @author : LiJun
 * @date : 2023-12-27 14:41
 **/
@Component
public class LimitFilter extends BaseCustomFilter implements InitializingBean {


    @Override
    public void afterPropertiesSet() {

        //初始化 sentinel 限流
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();

        //设置受限流保护的资源
        rule.setResource("ZuulSentinel");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置现在 QPS 为
        rule.setCount(10);

        //添加限流规则
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    /**
     * Sentinel 限流
     */
    private boolean sentinelLimiter() {
        try (Entry ignored = SphU.entry("ZuulSentinel")) {
            return false;
        } catch (BlockException e) {
            return true;
        }
    }


    /**
     * 创建令牌桶，设置令牌生成速率为：每秒2个
     * 如果参数设置为 0.1 表示每秒10个
     */
    @SuppressWarnings("all")
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(10);

    /**
     * 令牌桶限流
     */
    @SuppressWarnings("all")
    private boolean rateLimiter() {
        return !RATE_LIMITER.tryAcquire();
    }


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
        //是否限流
        if (sentinelLimiter()) {
            RequestContext currentContext = RequestContext.getCurrentContext();
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
