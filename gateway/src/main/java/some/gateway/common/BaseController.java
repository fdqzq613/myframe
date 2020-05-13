package some.gateway.common;

import com.some.common.constants.SystemEnum;
import com.some.common.result.RespResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	

}
