package com.rush.house.filter;


import com.alibaba.fastjson.JSON;
import com.rush.house.common.constant.Constants;
import com.rush.house.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by 17512 on 2018/10/29.
 */
public class LoginFilter implements Filter {
    
    private static final Logger log = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        log.info("doFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        /**
         * 不需要验证登录的接口
         */
        Boolean pass = false;
        for (String api : Constants.NO_AUTH_APIS) {
            if (request.getServletPath().contains(api)) {
                pass = true;
                break;
            }
        }
        if (pass) {
            chain.doFilter(request, response);
            return;
        }
        /**
         * 验证用户session
         */
        HttpSession session = request.getSession();
        Object userSession = session.getAttribute("user");
        if (userSession == null) {
            redirectToLogin(response);
            return;
        }
        User user = JSON.parseObject(userSession.toString(), User.class);
        if (user == null || StringUtils.isBlank(user.getUsername())) {
            redirectToLogin(response);
            return;
        }
        /**
         * 通过filter
         */
        chain.doFilter(request, response);
        return;
    }

    @Override
    public void destroy() {
        log.info("destroy");
    }

    private void redirectToLogin (HttpServletResponse response) {
        try {
            response.sendRedirect(Constants.HOUSE_HOST + Constants.HOUSE_API_NEED_LOGIN);
        } catch (IOException e) {
            log.error("filter重定向异常", e);
        }
    }
}
