package com.some.web.filter;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 
 * @version V1.0
 * @author qzq
 * @date   2020年4月7日
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private static final Logger logger = LoggerFactory.getLogger(XssHttpServletRequestWrapper.class);
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        String strHeader = super.getHeader(name);
        if(StringUtils.isEmpty(strHeader)){
            return strHeader;

        }
        return getValue(strHeader,Whitelist.relaxed());
    }

    @Override
    public String getParameter(String name) {
        String strParameter = super.getParameter(name);
        if(StringUtils.isEmpty(strParameter)){
            return strParameter;
        }
        return getValue(strParameter,Whitelist.relaxed());
    }

    private String getValue(String bodyHtml, Whitelist whitelist){
    	return HtmlUtils.htmlEscape(bodyHtml,"UTF-8");
        //return Jsoup.clean(bodyHtml,Whitelist.relaxed());
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if(values==null){
            return values;
        }
        int length = values.length;
        String[] escapseValues = new String[length];
        for(int i = 0;i<length;i++){
            //过滤一切可能的xss攻击字符串
            escapseValues[i] = getValue(values[i], Whitelist.relaxed()).trim();
            if(!StringUtils.equals(escapseValues[i],values[i])){
                logger.warn("xss字符串过滤前：{},过滤后：{}",values[i],escapseValues[i]);
            }
        }
        return escapseValues;
    }

}
