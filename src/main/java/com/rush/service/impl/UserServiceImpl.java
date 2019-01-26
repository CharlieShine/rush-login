package com.rush.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rush.entity.User;
import com.rush.mapper.UserMapper;
import com.rush.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.rush.shiro.util.JWTUtil;
import org.apache.shiro.SecurityUtils;
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

    @Override
    public User getUserFromPrincipal() {
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        String username = JWTUtil.getUsername(token);
        User user = this.selectByUserName(username);
        return user;
    }
}
