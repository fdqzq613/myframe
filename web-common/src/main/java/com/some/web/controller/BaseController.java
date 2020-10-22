package com.some.web.controller;

import com.some.common.constants.SystemEnum;
import com.some.common.result.RespResult;
import com.some.common.utils.UserUtils;
import com.some.web.page.BasePage;
import com.some.web.page.CommonPageRequest;
import com.some.web.utils.BeanUtils;
import com.some.web.vo.BasePageRequestVo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;


public class BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 统一返回RespResult
     *
     * @param data
     * @return
     * @author qzq
     * @date 2017年7月14日 上午10:37:06
     */
    public <T> RespResult<T> getSuccessRespResult(T data) {
        RespResult<T> respResult = new RespResult<T>();
        respResult.setCodeAndMsg(SystemEnum.codesEnum.SUCCESS);
        respResult.setData(data);
        return respResult;
    }

    public <T> RespResult<T> getSuccessRespResult() {
        return (RespResult<T>) getSuccessRespResult("");
    }

    public <T> RespResult<T> getSuccessMsg(T data, String msg) {
        RespResult<T> respResult = new RespResult<T>();
        respResult.setCode(SystemEnum.codesEnum.SUCCESS.getCode());
        respResult.setMsg(msg);
        respResult.setData(data);
        return respResult;
    }

    public <T> RespResult<T> getFailMsg(String msg) {
        RespResult<T> respResult = new RespResult<T>();
        respResult.setCode(SystemEnum.codesEnum.FAIL.getCode());
        respResult.setMsg(msg);
        return respResult;
    }

    public <T> RespResult<T> getFailMsg(T data, String msg) {
        RespResult<T> respResult = new RespResult<T>();
        respResult.setCode(SystemEnum.codesEnum.FAIL.getCode());
        respResult.setMsg(msg);
        respResult.setData(data);
        return respResult;
    }


    public <T> RespResult<T> getFailRespResult(T data) {
        RespResult<T> respResult = new RespResult<T>();
        respResult.setCodeAndMsg(SystemEnum.codesEnum.FAIL);
        respResult.setData(data);
        return respResult;
    }

    /**
     * 格式化字符串
     *
     * @param str
     * @param arguments
     * @return
     * @author qzq
     */
    protected String formatString(String str, Object... arguments) {

        return MessageFormat.format(str, arguments);
    }

    /**
     * 生成查询bean
     *
     * @param pageRequestVo
     * @param sort
     * @return
     * @author qzq
     * @date 2018年10月19日 下午3:54:16
     */
    public CommonPageRequest createPageRequest(BasePageRequestVo pageRequestVo, Sort sort) {
        return new CommonPageRequest(pageRequestVo.getPage() - 1, pageRequestVo.getPageCount(), sort);
    }

    /**
     * 获取getParameter值
     *
     * @param key
     * @return
     * @author qzq
     */
    protected String getPara(String key) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request.getParameter(key);
    }

    /**
     * 获取getParameter值转INT
     *
     * @param key
     * @return
     * @author qzq
     */
    protected Integer getPara2Int(String key) {
        String myValue = getPara(key);
        return StringUtils.isBlank(myValue) ? null : Integer.parseInt(myValue);
    }

    /**
     * 以,号隔开转机会
     *
     * @param ids
     * @return
     * @author qzq
     */
    protected List<String> change2Collection(String ids) {
        if (StringUtils.isEmpty(ids)) {
            return null;
        }
        String[] myIds = ids.split(",");
        return Arrays.asList(myIds);
    }

    /**
     * 分页条件
     *
     * @param pageRequestVo
     * @return
     * @author qzq
     * @date 2018年11月23日 下午2:33:17
     */
    public <T> BasePage<T> buildPage(BasePageRequestVo pageRequestVo) {
        return new BasePage<T>(pageRequestVo.getPage(), pageRequestVo.getPageCount());
    }

    /**
     * 只拷贝非空的属性
     *
     * @param source
     * @param target
     * @author qzq
     * @date 2020年4月16日 下午3:56:09
     */
    protected void copyPropertiesIgnoreNull(Object source, Object target) {
        BeanUtils.copyPropertiesIgnoreNull(source, target);
    }

    protected String getUserId() {
        return UserUtils.getUserId();
    }
}
