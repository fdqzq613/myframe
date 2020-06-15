package com.some.web.exception;


import com.some.common.constants.SystemEnum;
import com.some.common.exception.RespException;
import com.some.common.exception.UnauthorizedException;
import com.some.common.result.RespResult;
import com.some.common.utils.AjaxUtils;
import com.some.web.utils.ConfigUtils;
import com.some.web.vo.ConfigVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	@Autowired
	private ConfigVo configVo;
	//自定义路径
	public static final String DEF_LOGIN_URL="DEF_LOGIN_URL";
	@ExceptionHandler(value = BindException.class)
	public Object MethodArgumentNotValidHandler(HttpServletRequest request, HttpServletResponse response,
			BindException e, Model model) throws Exception {

		StringBuilder sb = new StringBuilder("URL:" + request.getRequestURI() + "  错误：");

		List<Map<String, String>> ls = new ArrayList<Map<String, String>>();
		// 解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
		for (FieldError error : e.getBindingResult().getFieldErrors()) {
			// sb.append(error.getCode()+"---"+error.getArguments()+"---"+error.getDefaultMessage());
			sb.append("field:").append(error.getField()).append(",").append(error.getDefaultMessage()).append(",");
			Map<String, String> errMap = new HashMap<String, String>();
			errMap.put("field", error.getField());
			errMap.put("msg", error.getDefaultMessage());
			ls.add(errMap);
		}
		logger.warn(sb.toString());
		RespResult<List<Map<String, String>>> respResult = new RespResult<List<Map<String, String>>>();
		// msg只提示第一个
		Map<String, String> errFirstMap = ls.get(0);
		respResult.setCode(SystemEnum.codesEnum.ERROR_RUNTIME.getCode());
		respResult.setMsg(errFirstMap.get("msg"));
		respResult.setData(ls);
		return new ResponseEntity<Object>(respResult, HttpStatus.OK);
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Object ExceptionHandler(HttpServletRequest request, Exception e, Model model) throws Exception {

		if (e instanceof RespException) {
			RespException respException = (RespException) e;
			if (ConfigUtils.isDev()) {
				// 改异常只在debug模式或者开发环境输出
				logger.warn("请求：{}，返回编码：{}；消息：{}" , request.getRequestURI(),respException.getCode(), e.getMessage(),e);
			} else if("debug".equals(configVo.getLogLevel())){
				logger.warn("请求：{}，返回编码：{}；消息：{}" , request.getRequestURI(),respException.getCode(), e.getMessage(),e);
			}else {
				if (!(e instanceof UnauthorizedException)) {
					logger.warn("请求：{}，返回编码：{}；消息：{}" , request.getRequestURI(),respException.getCode(), e.getMessage());
				}
			}

			if (e instanceof UnauthorizedException) {
				if (request.getRequestURI().equals("/")
						|| request.getRequestURI().equals(request.getContextPath() + "/")
						|| request.getRequestURI().equals("/home")
						|| request.getRequestURI().equals(request.getContextPath() + "/home")) {
					if(ConfigUtils.isDev()&&request.getRequestURL().toString().indexOf("127")==-1&&request.getRequestURL().toString().indexOf("localhost")==-1){
						String url = request.getRequestURL().toString();
						//url = url.substring(0, url.indexOf("/"))
						if(url.indexOf("/",10)!=-1){
							url = url.substring(0, url.indexOf("/",10)+1);
						}else{
							url=url+"/";
						}
						return new ModelAndView("redirect:"+url+"login");
					}
					Object defUrl = request.getAttribute(DEF_LOGIN_URL);
					return defUrl==null?new ModelAndView("redirect:/login"):new ModelAndView("redirect:"+defUrl);
				}else{
					//其他路径的要打印日志
					logger.warn("返回，编码：" + respException.getCode() + "；消息：" + e.getMessage());
					if (!AjaxUtils.isAjax(request)&&"false".equals(request.getAttribute("isJson"))) {
						Object defUrl = request.getAttribute(DEF_LOGIN_URL);
						return defUrl==null?new ModelAndView("redirect:/login"):new ModelAndView("redirect:"+defUrl);
					}
				}
			}
			model.addAttribute("code", respException.getCode());
			model.addAttribute("msg", e.getMessage());
			// model.addAttribute("e", e);
		} else {
			logger.error("请求：{}，异常：{}" ,request.getRequestURI(), e.getMessage(), e);

			model.addAttribute("code", SystemEnum.codesEnum.ERROR_RUNTIME.getCode());
			model.addAttribute("msg", e.getMessage());
			// model.addAttribute("e", e);
		}
		// return new ResponseEntity<Object>(model.asMap(),HttpStatus.OK);

		if (AjaxUtils.isAjax(request)) {
			return new ResponseEntity<Object>(model.asMap(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(model.asMap(), HttpStatus.OK);
		}
	}
   private String getParam(HttpServletRequest request){
	   StringBuilder sb = new StringBuilder(request.getRequestURI()).append("\r\n params:");
	   Map<String, String[]> map = request.getParameterMap();
	   if(map!=null&&map.size()>0){
		   for(String key:map.keySet()){
			   String[] value = map.get(key);
			   String firstValue = value!=null&&value.length>0?value[0]:"";
			   sb.append(key).append("=").append(firstValue);
		   }
	   }
	   
	   return sb.toString();
   }
}
