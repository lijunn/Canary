package com.lijun.canary.filter;

import cn.hutool.json.JSONUtil;
import com.google.common.util.concurrent.RateLimiter;
import com.lijun.canary.dto.ResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User 服务限流过滤器
 * @author : LiJun
 * @date : 2023-12-28 11:05
 **/
@Component
public class LimitFilter implements Filter {

    /**
     * 创建令牌桶，设置令牌生成速率为：每秒2个
     * 如果参数设置为 0.1 表示每秒10个
     */
    @SuppressWarnings("all")
    public static final RateLimiter RATE_LIMITER = RateLimiter.create(1);

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @SuppressWarnings("all")
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //获取不到令牌
        if (!RATE_LIMITER.tryAcquire()){
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json; charset=utf-8");
            PrintWriter printWriter = response.getWriter();
            printWriter.write(JSONUtil.toJsonStr(ResponseResult.limit("service-order 限流")));
            printWriter.close();
            return;
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
