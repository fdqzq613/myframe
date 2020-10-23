package com.some.common.utils;


import com.some.common.exception.RespException;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * 
 * 功能说明:
 *
 * @version V1.0
 * @author qzq
 * @since  JDK1.6
 */
public class AssertUtils {

	/**
	 * 判断是否为非空，如果为空则抛msg异常
	 * @param o
	 * @param msg
	 * @author qzq
	 */
	public static void assertIsNotEmpty(Object o,String msg){
		if(o==null){
			throw new RespException(msg);
		}
		if(o instanceof String){
			if(StringUtils.isEmpty(o.toString())){
				throw new RespException(msg);
			}
		}else if(o instanceof Collection){
			Collection c = (Collection)o;
			if(c.isEmpty()){
				throw new RespException(msg);
			}
		}
		
	}
	
	/**
	 * 判断是否为true，如果为false则抛msg异常
	 * @param o
	 * @param msg
	 * @author qzq
	 */
	public static void assertIsTrue(boolean o,String msg){
		if(o==false){
			throw new RespException(msg);
		}
	}
	
	/**
	 * 判断是否为false，如果为true则抛msg异常
	 * @param o
	 * @param msg
	 * @author qzq
	 */
	public static void assertNotTrue(boolean o,String msg){
		if(o==true){
			throw new RespException(msg);
		}
	}

	/**
	 * equals方法的判断，如果不相等则抛msg异常
	 * @param src
	 * @param dest
	 * @param msg
	 */
	public static void assertIsEqual(Object src,Object dest,String msg){
		if(src==null){
			if(dest!=null){
				throw new RespException(msg);
			}
			return;
		}
	   if(src instanceof Number){
			if(src!=dest){
				throw new RespException(msg);
			}
		}if(!src.equals(dest)){
			throw new RespException(msg);
		}

	}
}
