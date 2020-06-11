package com.some.web.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-11 13:50
 */
public class CookieUtils {
    public static String getCookie(HttpServletRequest request, String name){
        Cookie[]  cookies = request.getCookies();
        if(cookies==null||cookies.length==0 ){
            return null;
        }
        for(Cookie cookie:cookies){
            if(cookie.getName().equals(name)){
                return cookie.getValue();
            }
        }
        return null;
    }
}
