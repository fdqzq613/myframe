package some.movie.common;

import com.yoya.movie.common.constants.SystemEnum;
import com.yoya.movie.common.result.RespResult;
import com.yoya.movie.common.token.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	   /**
	    * 获取getParameter值
	    * @param key
	    * @return
	    * @author qzq
	    */
	   protected String getPara(String key){
			ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = requestAttributes.getRequest();
			return request.getParameter(key);
	   }
	   /**
	    * 获取getParameter值转INT
	    * @param key
	    * @return
	    * @author qzq
	    */
	   protected Integer getPara2Int(String key){
		   String myValue = getPara(key);
		   return StringUtils.isBlank(myValue)?null:Integer.parseInt(myValue);
	   }
	   
	   /**
	    * 以,号隔开转机会
	    * @param ids
	    * @return
	    * @author qzq
	    */
	   protected List<String> change2Collection(String ids){
		   if(StringUtils.isEmpty(ids)){
			   return null;
		   }
		   String[] myIds = ids.split(",");
		   return Arrays.asList(myIds);
	   } 
	   
		/**
		 * 获取登录用户id
		 * @return
		 * @author qzq
		 * @date 2019年4月23日 下午5:49:06
		 */
		public String getUserId(){
			return UserUtils.getUserId();
		}
}
