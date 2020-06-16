package com.some.web.utils;


import com.some.common.utils.ApplicationContextUtils;
import com.some.web.vo.ConfigVo;

/**
 * 
 * 功能说明:
 *
 * @version V1.0
 * @author qzq
 * @since  JDK1.6
 */
public class ConfigUtils {
 public static String getActiveProfile(){
	 ConfigVo configVo = ApplicationContextUtils.getBean("configVo");
	 if(configVo.getActiveProfile()==null){
		 String activeProfile =  ApplicationContextUtils.getApplicationContext().getEnvironment().getActiveProfiles()[0];
		 configVo.setActiveProfile(activeProfile);
	 }
	 return configVo.getActiveProfile();
 }
 /**
  * 是否开放环境
  * @return
  * @author qzq
  */
 public static boolean isDev(){
	 return "kf".equals(getActiveProfile())?true:false;
 }
}
