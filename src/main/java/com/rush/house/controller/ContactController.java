package com.rush.house.controller;

import com.rush.house.common.result.JSONResult;
import com.rush.house.entity.Contact;
import com.rush.house.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 17512 on 2018/10/19.
 */
@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    /**
     * 添加联系人
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping("/contact/add")
    public JSONResult hello (@RequestParam(name = "mobile") String mobile,
                             HttpServletRequest req) {
        Contact contact = new Contact();
        contact.setMobile(mobile);
        contact.setName("name");
        contact.setRemark("remark");
        contactService.insert(contact);
        return new JSONResult(true, "添加联系人成功!");
    }
}
