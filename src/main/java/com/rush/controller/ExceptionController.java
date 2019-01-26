package com.rush.controller;

import com.alibaba.fastjson.JSONObject;
import com.rush.common.exception.UnauthorizedException;
import com.rush.common.result.ResponseBean;
import org.apache.shiro.ShiroException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionController {

    private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ResponseBean handle401(ShiroException e) {
        return new ResponseBean(401, e.getMessage(), null);
    }

    // 捕捉UnauthorizedException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseBean handle401() {
        return new ResponseBean(401, "请登录", null);
    }

    // 捕捉BadRequestException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResponseBean globalException(HttpServletRequest req, Throwable ex) {
        JSONObject reqInfo = new JSONObject();
        reqInfo.put("url", req.getRequestURL().toString());
        reqInfo.put("msg", "请求出错");
        reqInfo.put("method", req.getMethod());
        reqInfo.put("parameters", req.getParameterMap());
        reqInfo.put("queryString", req.getQueryString());
        req.getQueryString();
        log.error(reqInfo.toJSONString(), ex);
        return new ResponseBean(getStatus(req).value(), ex.getMessage(), null);
    }

    private HttpStatus getStatus(HttpServletRequest req) {
        Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}

