package com.some.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-05 14:09
 */
public class Secret {
    public static void main(String[] args) {
       String s =  "{bcrypt}" + new BCryptPasswordEncoder().encode("123456");
        System.out.println(s);
    }
}
