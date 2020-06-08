package com.some.authclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-05 18:07
 */
@RestController
public class CallbackController {
    @GetMapping("/callback")
    public String callback(String code) {
        return "testNoPower";
    }
}
