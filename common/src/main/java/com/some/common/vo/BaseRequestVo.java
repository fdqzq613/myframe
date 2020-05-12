package com.some.common.vo;

import com.alibaba.fastjson.JSONObject;


public class BaseRequestVo {

	@Override
	public String toString() {
		return JSONObject.toJSON(this).toString();
	}
}
