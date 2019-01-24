package com.rush.controller;

import com.alibaba.fastjson.JSONObject;
import com.rush.common.constant.Constants;
import com.rush.common.result.ResponseBean;
import com.rush.common.util.JWTUtil;
import com.rush.common.util.SessionUtil;
import com.rush.common.util.StrUtils;
import com.rush.entity.User;
import com.rush.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 17512 on 2018/10/29.
 */
@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 获取用户信息
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/info", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresAuthentication
    @RequiresRoles(value = {Constants.ROLE_ADMIN}, logical = Logical.OR)
    public ResponseBean info (HttpServletRequest req) {
        try {
            User user = SessionUtil.getUser(req);
            Subject subject = SecurityUtils.getSubject();
            if (subject.isAuthenticated()) {
                return new ResponseBean(Constants.CODE_SUCCESS, "You are already logged in");
            } else {
                return new ResponseBean(Constants.CODE_SUCCESS, "You are guest");
            }
        } catch (Exception e) {
            log.error("获取用户信息异常", e);
            return new ResponseBean(Constants.CODE_ERROR, "获取用户信息异常:" + e.getMessage());
        }
    }

    /**
     * 用户注册
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/register", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseBean register (@RequestParam(name = "username", required = false) String username,
                                @RequestParam(name = "password", required = false) String password,
                                HttpServletRequest req) {
        try {
            if (StringUtils.isBlank(username)) {
                return new ResponseBean(Constants.CODE_ERROR, "用户名不能为空!");
            }
            if (StringUtils.isBlank(password)) {
                return new ResponseBean(Constants.CODE_ERROR, "密码不能为空!");
            }
            User user = userService.selectByUserName(username);
            if (user != null) {
                return new ResponseBean(Constants.CODE_ERROR, "用户已存在!");
            }
            user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncode(username, password));
            userService.insert(user);
            return new ResponseBean(Constants.CODE_SUCCESS, "用户注册成功!");
        } catch (Exception e) {
            log.error("用户注册异常", e);
            return new ResponseBean(Constants.CODE_ERROR, "用户注册异常:" + e.getMessage());
        }
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @RequiresGuest
    public ResponseBean login (@RequestParam(name = "username", required = false) String username,
                             @RequestParam(name = "password", required = false) String password,
                             HttpServletRequest req) {
        try {
            if (StringUtils.isBlank(username)) {
                return new ResponseBean(Constants.CODE_ERROR, "用户名不能为空!");
            }
            if (StringUtils.isBlank(password)) {
                return new ResponseBean(Constants.CODE_ERROR, "密码不能为空!");
            }
            User user = userService.selectByUserName(username);
            if (user == null) {
                return new ResponseBean(Constants.CODE_ERROR, "用户不存在!");
            }
            if  (!user.getPassword().equals(passwordEncode(username, password))) {
                return new ResponseBean(Constants.CODE_ERROR, "密码错误!");
            }
            String token = JWTUtil.sign(username, passwordEncode(username, password));
            JSONObject data = new JSONObject();
            data.put("token", token);
            return new ResponseBean(Constants.CODE_SUCCESS, "登录成功!", data);
        } catch (Exception e) {
            log.error("用户登录异常", e);
            return new ResponseBean(Constants.CODE_ERROR, "用户登录异常:" + e.getMessage());
        }
    }

    /**
     * 退出登录
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseBean login (HttpServletRequest req) {
        try {
            Subject subject = SecurityUtils.getSubject();
            if (subject != null) {
                subject.logout();
            }
            return new ResponseBean(Constants.CODE_SUCCESS, "退出登录成功!");
        } catch (Exception e) {
            log.error("退出登录异常", e);
            return new ResponseBean(Constants.CODE_SUCCESS, "退出登录异常:" + e.getMessage());
        }
    }

    private String passwordEncode (String username, String password) {
        password = StrUtils.md5(username + Constants.MD5_SALT + password);
        return password;
    }
}
