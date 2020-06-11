package com.some.web.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @version V1.0
 * @author qzq
 * @date   2020年4月16日
 */
public class BeanUtils {
	public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null){
            	emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
	/**
	 * 只拷贝非空的属性
	 * @param source
	 * @param target
	 * @author qzq
	 * @date 2020年4月16日 下午3:56:09
	 */
	public static void copyPropertiesIgnoreNull(Object source, Object target){
		org.springframework.beans.BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }
	/**
	 * 拷贝属性,包括空值
	 * @param source
	 * @param target
	 * @author qzq
	 * @date 2020年4月16日 下午4:07:12
	 */
	public static void copyProperties(Object source, Object target){
		org.springframework.beans.BeanUtils.copyProperties(source, target);
	}
}
