package com.some.common.exception;


import com.some.common.constants.IMsgEnum;
import com.some.common.result.RespResult;

import java.text.MessageFormat;


public class RespException extends RuntimeException{
	protected int code=906;
    public RespException(String message) {
        super(message);
    }
    public RespException(String message, Throwable cause) {
        super(message, cause);
    }
    public RespException(int code, String message) {
    	super(message);
    	this.code=code;
    }
    public RespException(IMsgEnum codesEnum) {
    	super(codesEnum.getMsg());
    	this.code=codesEnum.getCode();
    }
    /**
     * 格式化返回信息
     * @param codesEnum
     * @param arguments
     */
    public RespException(IMsgEnum codesEnum, Object ...  arguments) {
    	super(MessageFormat.format(codesEnum.getMsg(),arguments));
    	this.code=codesEnum.getCode();
    }
    public int getCode(){
    	return this.code;
    }
    @Override
    public String getMessage() {
    	
    	return super.getMessage();
    }
    
    public RespResult getErrResponse(){
    	return new RespResult(this.code,this.getMessage());
    }
}
