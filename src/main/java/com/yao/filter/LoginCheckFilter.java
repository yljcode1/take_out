package com.yao.filter;

import com.alibaba.fastjson.JSON;
import com.yao.common.BaseContext;
import com.yao.common.Response;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * 登录过滤器
 *
 * @date: 2023-01-30
 * @author: yao
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    // 路径匹配通配符，可用于白名单
    private final static AntPathMatcher MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        log.info("拦截到的请求:{}", request.getRequestURI());
        // 过滤白名单
        String[] writeUri = new String[]{"/employee/login", "/employee/logout", "/backend/**", "/front/**"};
        // 获取到请求的uri
        String requestURI = request.getRequestURI();
        // 判断是否属于白名单的数据
        boolean flag = this.isValidateUri(requestURI, writeUri);

        // 判断uri是否需要处理
        // 如果不需要则直接放行
        // 判断登录状态，已登录直接放行
        if (flag) {
            log.info("本次请求{}不需要处理", requestURI);
            filterChain.doFilter(request, response);
            return;
        }
        if (request.getSession().getAttribute("employee") != null) {
            log.info("用戶已经登录");
            Long employeeId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(employeeId);
            filterChain.doFilter(request, response);
            return;
        }
        // 未登录返回登录界面
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(Response.error("NOTLOGIN")));
        return;

    }

    /**
     * @param requestURI 请求地址
     * @param writeUri   白名单
     * @return 是否白名单
     */
    private boolean isValidateUri(String requestURI, String[] writeUri) {
        for (int i = 0; i < writeUri.length; i++) {
            if (MATCHER.match(writeUri[i], requestURI)) {
                return true;
            }
        }
        return false;
    }
}
