package com.some.common.exception;


import com.some.common.constants.SystemEnum;

public class UnauthorizedException extends RespException {
    public UnauthorizedException() {
        super(SystemEnum.codesEnum.ERROR_UNAUTHORIZED);
    }
    
    public UnauthorizedException(String msg) {
        super(SystemEnum.codesEnum.ERROR_UNAUTHORIZED.getCode(),msg);
    }
}
