package com.rush.common.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 17512 on 2018/9/7.
 */
public class Constants {

    public static final String HOST = "http://editor.zhikr.cn/rush";

    public static final String API_NEED_LOGIN = "/user/needLogin";
    public static final String API_LOGIN = "/user/login";
    public static final String API_REGISTER = "/user/register";
    public static final String API_401 = "/401";
    public static final String API_404 = "/404";

    public static final List<String> NO_AUTH_APIS = new ArrayList<>();
    static {
        NO_AUTH_APIS.add(API_NEED_LOGIN);
        NO_AUTH_APIS.add(API_LOGIN);
        NO_AUTH_APIS.add(API_REGISTER);
        NO_AUTH_APIS.add(API_401);
        NO_AUTH_APIS.add(API_404);
    }

    public static final String MD5_SALT = "433d2d24";

    public static final String ROLE_ADMIN = "role_admin";
    public static final String ROLE_MEMBER = "role_member";

}
