package com.some.common.constants;


public class SystemEnum {
	//返回代码常量
	public enum codesEnum implements IMsgEnum{
		ERROR_UNAUTHORIZED(401,"未授权" ),ERROR_TOKEN_TIMEOUT(408,"令牌超时" ),
		SUCCESS(200,"success" ),FAIL(969,"fail" ),ERROR_RUNTIME(906,"接口异常:{0}" )
		;
    
	private int code;
	private String msg;
	codesEnum(int code,String msg){
		this.code=code;
		this.msg=msg;
	}
	@Override
	public int getCode() {
		return code;
	}
	@Override
	public void setCode(int code) {
		this.code = code;
	}
	@Override
	public String getMsg() {
		return msg;
	}
	@Override
	public void setMsg(String msg) {
		this.msg = msg;
	}
	}
}
