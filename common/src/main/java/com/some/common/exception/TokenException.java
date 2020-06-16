package com.some.common.exception;

import com.some.common.constants.SystemEnum;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-16 09:32
 */
public class TokenException extends RespException {
    public TokenException(SystemEnum.codesEnum code) {
        super(code);
    }

}
