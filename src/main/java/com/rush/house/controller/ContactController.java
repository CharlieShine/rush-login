package com.rush.house.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.rush.house.common.result.JSONResult;
import com.rush.house.common.util.StrUtils;
import com.rush.house.entity.Contact;
import com.rush.house.service.ContactService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
                           @RequestParam(name = "name", required = false) String name,
                           @RequestParam(name = "gender", required = false) String gender,
                           @RequestParam(name = "remark", required = false) String remark,
                           HttpServletRequest req) {
        try {
            if (StringUtils.isBlank(mobile)) {
                return new JSONResult(false, "手机号不能为空!");
            }
            if (!StrUtils.isMobile(mobile)) {
                return new JSONResult(false, "手机号格式不正确!");
            }
            if (StringUtils.isBlank(name)) {
                return new JSONResult(false, "姓名不能为空!");
            }
            Contact contact = contactService.selectByMobile(mobile);
            if (contact != null) {
                contact.setUpdateTime(new Date());
                contactService.updateById(contact);
            } else {
                contact = new Contact();
                contact.setMobile(mobile);
                contact.setName(name);
                contact.setGender(gender);
                contact.setRemark(remark);
                contactService.insert(contact);
            }
            return new JSONResult(true, "添加联系人成功!");
        } catch (Exception e) {
            log.error("添加联系人异常", e);
            return new JSONResult(false, "添加联系人异常:" + e.getMessage());
        }
    }

    /**
     * 查询联系人
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/contact/getList", method = {RequestMethod.GET, RequestMethod.POST})
    public JSONResult getList (@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                               @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize,
                               @RequestParam(name = "keyword", required = false) String keyword,
                               HttpServletRequest req) {
        try {
            EntityWrapper<Contact> wrapper = new EntityWrapper<>();
            wrapper.where(" 1 = 1 ");
            if (StringUtils.isNotBlank(keyword)) {
                wrapper.andNew("name like {0} OR mobile like {0}", "%" + keyword + "%");
            }
            wrapper.orderBy("id", false);
            Page<Contact> page = new Page<>();
            page.setCurrent(pageNo);
            page.setSize(pageSize);
            page = contactService.selectPage(page, wrapper);
            return new JSONResult(page, "查询联系人成功!", true);
        } catch (Exception e) {
            log.error("查询联系人异常", e);
            return new JSONResult(false, "查询联系人异常:" + e.getMessage());
        }
    }
}
