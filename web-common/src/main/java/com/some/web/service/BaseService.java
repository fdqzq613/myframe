package com.some.web.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.some.common.constants.SystemEnum;
import com.some.common.result.RespResult;
import com.some.common.utils.UserUtils;
import com.some.web.utils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * 基础service 
 * @version V1.0
 * @author qzq
 * @date   2019年5月6日
 */
public class BaseService<M extends BaseMapper<P>, P> extends ServiceImpl<M , P>   {
	private static final Logger logger = LoggerFactory.getLogger(BaseService.class);
	/**
	  * 统一返回RespResult
	  * @param data
	  * @return
	  * @author qzq
	  * @date 2017年7月14日 上午10:37:06
	  */
	public <T> RespResult<T> getSuccessRespResult(T data){
		RespResult<T> respResult = new RespResult<T>();
		respResult.setCodeAndMsg(SystemEnum.codesEnum.SUCCESS);
		respResult.setData(data);
		return respResult;
	}
	
	public <T> RespResult<T> getSuccessRespResult(){
		return (RespResult<T>) getSuccessRespResult("");
	}
	public <T> RespResult<T> getSuccessMsg(T data, String msg){
		RespResult<T> respResult = new RespResult<T>();
		respResult.setCode(SystemEnum.codesEnum.SUCCESS.getCode());
		respResult.setMsg(msg);
		respResult.setData(data);
		return respResult;
	}
	
	public <T> RespResult<T> getFailMsg(String msg){
		RespResult<T> respResult = new RespResult<T>();
		respResult.setCode(SystemEnum.codesEnum.FAIL.getCode());
		respResult.setMsg(msg);
		return respResult;
	}
	
	public <T> RespResult<T> getFailMsg(T data, String msg){
		RespResult<T> respResult = new RespResult<T>();
		respResult.setCode(SystemEnum.codesEnum.FAIL.getCode());
		respResult.setMsg(msg);
		respResult.setData(data);
		return respResult;
	}


	public <T> RespResult<T> getFailRespResult(T data){
		RespResult<T> respResult = new RespResult<T>();
		respResult.setCodeAndMsg(SystemEnum.codesEnum.FAIL);
		respResult.setData(data);
		return respResult;
	}
	
	   /**
	    * 格式化字符串
	    * @param str
	    * @param arguments
	    * @return
	    * @author qzq
	    */
	   protected String formatString(String str,Object ... arguments){
		   
		   return MessageFormat.format(str,  arguments);
	   }
	   
		/**
		 * 只拷贝非空的属性
		 * @param source
		 * @param target
		 * @author qzq
		 * @date 2020年4月16日 下午3:56:09
		 */
	   protected  void copyPropertiesIgnoreNull(Object source, Object target){
		   BeanUtils.copyPropertiesIgnoreNull(source, target);
	   }

	protected String getUserId(){
		return UserUtils.getUserId();
	}
}
