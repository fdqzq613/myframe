package com.some.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-05 09:12
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public Principal getUser(Principal principal) {
        log.info(principal.toString());
        return principal;
    }
}
