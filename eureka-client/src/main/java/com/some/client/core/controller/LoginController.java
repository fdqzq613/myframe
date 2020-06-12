package com.some.client.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-12 15:39
 */
@Controller
@Api(value = "登录", tags = "登录")
@Slf4j
public class LoginController {
    @ApiOperation(value = "登录页", notes = "登录页", httpMethod = "GET")
    @RequestMapping("/login")
    public String page(HttpServletRequest request) {
        return "redirect:http://www.baidu.com";
    }

}
