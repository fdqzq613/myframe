package com.some.order.coordinator;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-05-22 15:28
 */
public class OrderCoordinatorHolder {

    private static Map<Long,OrderCoordinator> holder = new HashMap<>();
    public static void put(Long orderNo,OrderCoordinator orderCoordinator){
        holder.put(orderNo,orderCoordinator);
    }
    public static  OrderCoordinator get(Long orderNo){
        return holder.get(orderNo);
    }
}
