package com.some.web.utils;

import com.some.common.utils.ApplicationContextUtils;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 
 * 功能说明:
 *
 * @version V1.0
 * @author qzq
 * @since  JDK1.6
 */
@Deprecated
public class JdbcTemplateUtils {
  public static JdbcTemplate getJdbcTemplate(){
	  return ApplicationContextUtils.getBean("jdbcTemplate");
  }
}
