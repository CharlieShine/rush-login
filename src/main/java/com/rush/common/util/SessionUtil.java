package com.rush.common.util;

import com.alibaba.fastjson.JSON;
import com.rush.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 从session中获取用户相关信息的util
 */
public class SessionUtil {

    public static String getUserName (HttpServletRequest req) {
        User user = getUser(req);
        if (user == null) {
            return null;
        }
        return user.getUsername();
    }

    public static String getMobile (HttpServletRequest req) {
        User user = getUser(req);
        if (user == null) {
            return null;
        }
        return user.getMobile();
    }

    public static HttpSession clearSession (HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return session;
        }
        session.removeAttribute("user");
        session.invalidate();
        return null;
    }

    public static User getUser (HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return null;
        }
        Object u = session.getAttribute("user");
        if (u == null) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(u), User.class);
    }

    public static Boolean setUser (HttpServletRequest req, User user) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return false;
        }
        session.setAttribute("user", JSON.toJSONString(user));
        return true;
    }
}
