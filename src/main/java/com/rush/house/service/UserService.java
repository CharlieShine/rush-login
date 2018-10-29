package com.rush.house.service;

import com.rush.house.entity.User;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author licl
 * @since 2018-10-29
 */
public interface UserService extends IService<User> {

    User selectByUserName(String username);
}
