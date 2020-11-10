package com.some.web.url;

import com.some.common.utils.ApplicationContextUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: 常用参数
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-11-05 10:17
 */
@Slf4j
@Component
public class RD {
    private static UrlConfig urlConfig;
    private static DefaultUrlHandler urlHandler;
    private static String ctx;
    static{
        urlConfig = ApplicationContextUtils.getBean(UrlConfig.class);
        urlHandler  = new DefaultUrlHandler(urlConfig);
        ctx = StringUtils.isEmpty(urlConfig.getCtx())?"http://localhost:"+urlConfig.getPort()+urlConfig.getContextPath():urlConfig.getCtx();
    }
    public static String getContextPath(){
        return urlConfig.getContextPath();
    }
    public static String getCtx(){
        return ctx;
    }
    /**
     * 外网cdn地址
     * @param path
     * @return
     */
    public static String getStaticPath(String path) {
        return urlHandler.getStaticPath(path);
    }

    /**
     * 内网cdn地址 -这里暂时用本地地址
     * @param path
     * @return
     */
    public static String getInternalStaticPath(String path) {
        return urlHandler.getInternalStaticPath(path);
    }


}
