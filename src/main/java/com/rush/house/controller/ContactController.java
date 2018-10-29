package com.rush.house.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.rush.house.common.result.JSONResult;
import com.rush.house.entity.Contact;
import com.rush.house.service.ContactService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 17512 on 2018/10/19.
 */
@RestController
public class ContactController {

    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    /**
     * 添加联系人
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/contact/add", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONResult add (@RequestParam(name = "mobile", required = false) String mobile,
                           HttpServletRequest req) {
        try {
            if (StringUtils.isBlank(mobile)) {
                return new JSONResult(false, "mobile不能为空!");
            }
            Contact contact = new Contact();
            contact.setMobile(mobile);
            contact.setName("name");
            contact.setRemark("remark");
            contactService.insert(contact);
            return new JSONResult(true, "添加联系人成功!");
        } catch (Exception e) {
            log.error("添加联系人异常", e);
            return new JSONResult(false, "添加联系人异常:" + e.getMessage());
        }
    }
}
