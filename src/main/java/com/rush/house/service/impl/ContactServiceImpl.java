package com.rush.house.service.impl;

import com.rush.house.entity.Contact;
import com.rush.house.mapper.ContactMapper;
import com.rush.house.service.ContactService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
