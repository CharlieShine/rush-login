package com.rush.house.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rush.house.entity.User;
import com.rush.house.mapper.UserMapper;
import com.rush.house.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author licl
 * @since 2018-10-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectByUserName(String username) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.where("username = {0}", username);
        List<User> users = userMapper.selectList(wrapper);
        if (users != null && users.size() > 0) {
            return users.get(0);
        }
        return null;
    }
}
