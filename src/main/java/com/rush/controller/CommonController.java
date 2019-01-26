package com.rush.controller;

import com.rush.common.constant.Constants;
import com.rush.common.result.ResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 17512 on 2019/1/24.
 */
@RestController
public class CommonController {

    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @RequestMapping(path = "/401", method = {RequestMethod.GET})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseBean unauthorized() {
        return new ResponseBean(Constants.CODE_ERROR, "请登录");
    }

    @RequestMapping(path = "/404", method = {RequestMethod.GET})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseBean notFound() {
        return new ResponseBean(Constants.CODE_ERROR, "方法不存在");
    }
}
