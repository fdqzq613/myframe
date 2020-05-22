package com.some.order.coordinator;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-22 15:28
 */
public class OrderCoordinatorHolder {

    @Getter
    private static Map<Long,OrderCoordinator> holder = new HashMap<>();
    public static void put(Long orderNo,OrderCoordinator orderCoordinator){
        holder.put(orderNo,orderCoordinator);
    }
    public static void remove(Long orderNo){
        holder.remove(orderNo);
    }
    public static  OrderCoordinator get(Long orderNo){
        return holder.get(orderNo);
    }
}
