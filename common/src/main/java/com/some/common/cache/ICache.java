package com.some.common.cache;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-16 09:45
 */
public interface ICache <T> {
    void reflesh();
    T get(String key);
    void put(String key,T value);

    boolean containsKey(String key);
}
