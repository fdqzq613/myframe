package com.some.web.init;


import com.some.web.vo.ConfigVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultInit {
	protected static final Logger logger = LoggerFactory.getLogger(DefaultInit.class);
	private static boolean hadInit = false;
	private ConfigVo configVo;

	public DefaultInit(ConfigVo configVo){
		this.configVo = configVo;
	}

	
}
