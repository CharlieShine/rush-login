package com.rush.house.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rush.house.entity.Contact;
import com.rush.house.mapper.ContactMapper;
import com.rush.house.service.ContactService;
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
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements ContactService {

    @Autowired
    private ContactMapper contactMapper;

    @Override
    public Contact selectByMobile(String mobile) {
        EntityWrapper<Contact> wrapper = new EntityWrapper<>();
        wrapper.where("mobile = {0}", mobile);
        List<Contact> contacts = contactMapper.selectList(wrapper);
        return (contacts != null && contacts.size() > 0) ? contacts.get(0) : null;
    }
}
