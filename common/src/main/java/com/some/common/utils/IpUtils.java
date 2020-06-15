package com.some.common.utils;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 * @version V1.0
 * @author qzq
 * @date   2019年10月21日
 */
public class IpUtils {
	public static String getRemoteAddr( HttpServletRequest request )
	{
		String userIp = request.getHeader( "x-forwarded-for" ) ;
		if( null == userIp || 0 == userIp.length() || "unknown".equalsIgnoreCase( userIp ) )
		{
			userIp = request.getHeader( "Proxy-Client-IP" ) ;
		}
		if( null == userIp || 0 == userIp.length() || "unknown".equalsIgnoreCase( userIp ) )
		{
			userIp = request.getHeader( "WL-Proxy-Client-IP" ) ;
		}
		if( null == userIp || 0 == userIp.length() || "unknown".equalsIgnoreCase( userIp ) )
		{
			userIp = request.getRemoteAddr() ;
		}			
		return userIp ;
	}
	

}
