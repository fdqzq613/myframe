package com.some.order.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.some.web.domain.BaseModel;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author qzq
 * @since 2020-05-21
 */
@TableName("t_order_detail")
public class OrderDetail extends BaseModel<OrderDetail> {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 订单号
     */
    private Long orderNo;

    /**
     * 商品编码
     */
    private Long goodsNo;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 创建人
     */
    private Long createUserid;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private Long modifyUserid;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }
    public Long getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(Long goodsNo) {
        this.goodsNo = goodsNo;
    }
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
    public Long getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(Long createUserid) {
        this.createUserid = createUserid;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public Long getModifyUserid() {
        return modifyUserid;
    }

    public void setModifyUserid(Long modifyUserid) {
        this.modifyUserid = modifyUserid;
    }
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
        "id=" + id +
        ", orderNo=" + orderNo +
        ", goodsNo=" + goodsNo +
        ", num=" + num +
        ", createUserid=" + createUserid +
        ", createTime=" + createTime +
        ", modifyUserid=" + modifyUserid +
        ", modifyTime=" + modifyTime +
        "}";
    }
}
