package com.some.web.url;

public interface UrlHandler {
    /**
     * 外网cdn地址
     * @param path
     * @return
     */
    public  String getStaticPath(String path) ;
    /**
     * 内网cdn地址 -这里暂时用本地地址
     * @param path
     * @return
     */
    public  String getInternalStaticPath(String path) ;
}
