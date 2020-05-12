package com.some.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: ajax
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-12 15:45
 */
public class AjaxUtils {
    public static boolean isAjax(HttpServletRequest request){
        return  	request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest");
    }
}
