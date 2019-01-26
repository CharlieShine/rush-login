package com.rush.controller;

import com.alibaba.fastjson.JSONObject;
import com.rush.common.constant.Constants;
import com.rush.common.result.ResponseBean;
import com.rush.shiro.util.JWTUtil;
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

/**
 * Created by 17512 on 2018/10/29.
 */
@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/register", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseBean register (@RequestParam(name = "username") String username,
                                  @RequestParam(name = "password") String password,
                                  @RequestParam(name = "role") String role) {
        User user = userService.selectByUserName(username);
        if (user != null) {
            return new ResponseBean(Constants.CODE_ERROR, "用户已存在!");
        }
        user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncode(username, password));
        user.setRole(role);
        userService.insert(user);
        return new ResponseBean(Constants.CODE_SUCCESS, "用户注册成功!");
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResponseBean login (@RequestParam(name = "username") String username,
                               @RequestParam(name = "password") String password) {
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
    }

    private String passwordEncode (String username, String password) {
        password = StrUtils.md5(username + Constants.MD5_SALT + password);
        return password;
    }

    /**
     * 获取用户信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/info", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresAuthentication
    @RequiresRoles(value = {Constants.ROLE_ADMIN}, logical = Logical.OR)
    public ResponseBean info () {
        User user = userService.getUserFromPrincipal();
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return new ResponseBean(Constants.CODE_SUCCESS, "你已登录");
        } else {
            return new ResponseBean(Constants.CODE_SUCCESS, "你是访客");
        }
    }
}
