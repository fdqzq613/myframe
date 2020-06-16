package com.some.common.cache;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-16 09:39
 */
public class DefaultTokenDisableCache implements ITokenDisableCache {


    @Override
    public void reflesh() {

    }

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public void put(String key, Object value) {

    }

    @Override
    public boolean containsKey(String key) {
        return false;
    }
}
