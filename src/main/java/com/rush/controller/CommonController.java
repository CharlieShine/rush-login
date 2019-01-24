package com.rush.controller;

import com.rush.common.result.JSONResult;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 17512 on 2019/1/24.
 */
@RestController
public class CommonController {

    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @RequestMapping(path = "/401", method = {RequestMethod.GET})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public JSONResult unauthorized() {
        return new JSONResult(false, "请登录");
    }

    @RequestMapping(path = "/404", method = {RequestMethod.GET})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public JSONResult notFound() {
        return new JSONResult(false, "方法不存在");
    }

    @RequestMapping(path = "/requireAuth", method = {RequestMethod.POST})
    @RequiresAuthentication
    public JSONResult requireAuth() {
        return new JSONResult(true, "已登录");
    }
}
