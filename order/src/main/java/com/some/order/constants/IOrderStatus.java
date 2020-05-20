package com.some.order.constants;

import com.some.common.constants.IMsgEnum;

/**
 * @description: status
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-20 17:44
 */
public interface IOrderStatus {

    public enum orderEnum implements IMsgEnum {
        ORDER_ONLY_CREATE_NO(600,"只生成订单号，还未生成订单" ),
        ORDER_CREATE_NOPAY(601,"订单生成待支付" ),
        ORDER_PAY(602,"订单已经支付" );

        private int code;
        private String msg;
        orderEnum(int code,String msg){
            this.code=code;
            this.msg=msg;
        }
        public int getCode() {
            return code;
        }
        public void setCode(int code) {
            this.code = code;
        }
        public String getMsg() {
            return msg;
        }
        public void setMsg(String msg) {
            this.msg = msg;
        }
    }




}
