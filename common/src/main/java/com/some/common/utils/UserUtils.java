package com.some.common.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserUtils {
	/**
	 * 当前登陆用户
 	 */
	private static ThreadLocal<Map<String, String>> curUser = new ThreadLocal<>();
	/**
	 * 
	 * 重置
	 * @author qzq
	 */
	public static void reset(){
		curUser.remove();
	}

	/**
	 * 设置用户id
	 * 
	 * @param userId
	 * @author qzq
	 */
	public static void setUserId(String userId, String token, HttpServletRequest request) {

		Map<String, String> info = new HashMap<>();
		info.put("user_id", userId);
		info.put("token", token);
		if (request != null) {
			info.put("ip", IpUtils.getRemoteAddr(request));
			info.put("client_id", request.getParameter("client_id"));
		}
		curUser.set(info);
	}

	/**
	 * 获取用户明细
	 * @return
	 * @author qzq
	 */
	public static Map<String, String> getUserDetail(){
		return curUser.get();
	}
	/**
	 * 获取用户属性值
	 * @param key
	 * @return
	 * @author qzq
	 */
	public static String getProp(String key){
		Map<String, String> detail = getUserDetail();
		if(detail==null){
			return null;
		}
		return detail.get(key);
	}
	/**
	 * 设置完全的用户信息
	 * @param info
	 * @param request
	 * @author qzq
	 */
	public static void setUserId( Map<String, String> info, HttpServletRequest request) {
		curUser.set(info);
	}



	/**
	 * 获取当前登陆用户id
	 * 
	 * @return
	 * @author qzq
	 */
	public static String getUserId() {
		return curUser.get() == null ? null : curUser.get().get("user_id");
	}

	/**
	 * 获取当前用户令牌
	 * 
	 * @return
	 * @author qzq
	 */
	public static String getToken() {
		return curUser.get() == null ? null : curUser.get().get("token");
	}

}
