package com.some.web.aop;


import com.some.common.result.IResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 功能说明: 拦截输出路径 规范返回参数
 * 
 * @version V1.0
 * @author qzq
 * @since JDK1.6
 */
@Aspect
@Component
public class CheckControllerAop {
	protected static final Logger logger = LoggerFactory.getLogger(CheckControllerAop.class);

	public CheckControllerAop() {
		logger.info("aop");
	}

	@Around("execution(public* com..controller..*.*(..))")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		try {
			RequestAttributes ra = RequestContextHolder.getRequestAttributes();
			ServletRequestAttributes sra = (ServletRequestAttributes) ra;
			HttpServletRequest request = sra.getRequest();
			// https处理
			// HttpsUtils.initHead(request);
			logger.info("调用方法" + request.getRequestURI());
			// 执行完方法的返回值
			Object result = pjp.proceed();
			// 注意此处page只是为了兼容旧代码 新代码不能直接返回page
			if (result != null && !(result instanceof IResult) && !(result instanceof String)
					&& !(result instanceof Page)) {
				throw new RuntimeException("controller 返回参数不规范，请返回实现BaseResult接口的结果bean，如RespResult");
			}
			if (result == null) {
				return result;
			}
			return result;
		} finally {
		}
	}
}
