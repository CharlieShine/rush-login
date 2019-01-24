package com.rush.service;

import com.rush.entity.Contact;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author licl
 * @since 2018-10-29
 */
public interface ContactService extends IService<Contact> {

    Contact selectByMobile(String mobile);
}
