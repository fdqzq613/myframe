package com.some.order.mapper;


import com.some.order.domain.OrderDetail;
import com.some.web.repository.BaseDefaultMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qzq
 * @since 2020-05-21
 */
public interface OrderDetailMapper extends BaseDefaultMapper<OrderDetail> {

    public Integer getLockNum(@Param("goodsNo") long goodsNo);

    public int updateLockNum(@Param("lockNum") long lockNum, @Param("oldLockNum") long oldLockNum, @Param("modifyTime") LocalDateTime modifyTime, @Param("goodsNo") long goodsNo);
}
