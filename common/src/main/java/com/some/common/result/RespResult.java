package com.some.common.result;

import com.alibaba.fastjson.JSON;
import com.some.common.constants.IMsgEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.text.MessageFormat;


@ApiModel(value = "RespResult", description = "统一返回格式")
public class RespResult<T> implements IResult,Serializable{
	public static int SUCCESS=200;
	@ApiModelProperty(value = "返回状态编码", example = "200")
	private int code;
	@ApiModelProperty(value = "返回消息", example = "success")
	private String msg;
	@ApiModelProperty(value = "返回数据",example = "list")
	private T data;

	public boolean isOk(){
		return this.code==200?true:false;
	}
	/**
	 * 默认创建，code=200
	 * @return
	 * @author qzq
	 * @date 2019年2月18日 上午10:57:27
	 */
	public static <T> RespResult<T> create() {
		return new RespResult<T>(SUCCESS,"success");
	}

	public static <T> RespResult<T> create(int code, String msg) {
		return new RespResult<T>(code, msg);
	}

	public static <T> RespResult<T> create(int code, String msg, T data) {
		return new RespResult<T>(code, msg, data);
	}
	
	public static <T> RespResult<T> create( T data) {
		return new RespResult<T>(SUCCESS,"success",data);
	}

	public RespResult(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public RespResult(int code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public RespResult() {
	}

	public void setCodeAndMsg(IMsgEnum codesEnum, Object... arguments) {
		this.code = codesEnum.getCode();
		this.msg = MessageFormat.format(codesEnum.getMsg(), arguments);
	}

	public int getCode() {
		return code;
	}

	public RespResult<T> setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public RespResult<T> setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public T getData() {
		return data;
	}

	public RespResult<T> setData(T data) {
		this.data = data;
		return this;
	}

	/**
	 * @return
	 * @author qzq
	 * @date 2019年2月15日 下午4:38:16
	 */
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
