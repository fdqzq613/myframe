package com.some.web.url;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-11-05 10:57
 */
@Data
@Slf4j
public class DefaultUrlHandler implements UrlHandler{
    private String internalStaticPath;
    private String staticPath;
    private UrlConfig urlConfig;
    public DefaultUrlHandler(UrlConfig urlConfig){
        if(urlConfig.getStaticPath().startsWith("/cdn")){
            this.staticPath = getMergeUrl("http://localhost:"+urlConfig.getPort()+urlConfig.getContextPath(),urlConfig.getStaticPath());
        }
        this.internalStaticPath = getMergeUrl("http://localhost:"+urlConfig.getPort()+urlConfig.getContextPath(),urlConfig.getStaticPath());
        this.urlConfig = urlConfig;
    }
    @Override
    public String getStaticPath(String path) {
        try {
            if(StringUtils.isEmpty(path)){
                return this.staticPath;
            }
            return UrlCache.getUrl(getMergeUrl(this.staticPath,path), path);
        } catch (Exception e) {
            log.error("StaticPath获取出错，" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public String getInternalStaticPath(String path) {
      return getMergeUrl(internalStaticPath,path);
    }

    public static String getMergeUrl(String pre,String subUrl){
        if(pre.endsWith("/")&&!subUrl.startsWith("/")){
            return pre+subUrl;
        }else if(pre.endsWith("/")&&subUrl.startsWith("/")){
            return pre+subUrl.substring(1);
        }else if(!pre.endsWith("/")&&subUrl.startsWith("/")){
            return pre+subUrl;
        }else{
            //!pre.endsWith("/")&&!subUrl.startsWith("/")
            return pre+"/"+subUrl;
        }
    }
}
