package com.rush.house.common.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 17512 on 2018/9/7.
 */
public class Constants {

    public static final String HOUSE_HOST = "http://editor.zhikr.cn/house";
    public static final String HOUSE_API_NEED_LOGIN = "/user/needLogin";

    public static final List<String> NO_AUTH_APIS = new ArrayList<>();
    static {
        NO_AUTH_APIS.add("/user/needLogin");
        NO_AUTH_APIS.add("/user/login");
        NO_AUTH_APIS.add("/user/register");
    }

    public static final String MD5_SALT = "433d2d24";

}
