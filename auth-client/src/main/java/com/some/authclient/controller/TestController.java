package com.some.authclient.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-04 18:06
 */
@Slf4j
@RestController
@RequestMapping(value = "/api")
public class TestController {

    //http://localhost:7589/api/testNoPower
    @GetMapping("/testNoPower")
    public String testNoPower() {
        return "testNoPower";
    }

    //http://localhost:7589/api/testPower
    @GetMapping("/testPower")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String testPower() {
        //for debug
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("authentication: " + authentication.getAuthorities().toString());
        return "testPower";
    }

    /**
     * 权限在资源服务器上定义
     * @return
     */
    //http://localhost:7589/api/testResourcePower
    @GetMapping("/testResourcePower")
    public String testResourcePower() {
        //for debug
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("authentication: " + authentication.getAuthorities().toString());
        return "testResourcePower";
    }

}
