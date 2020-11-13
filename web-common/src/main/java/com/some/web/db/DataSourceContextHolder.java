package com.some.web.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

public class DataSourceContextHolder {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceContextHolder.class);
    private static final ThreadLocal<Stack<String>> contextHolder = new InheritableThreadLocal<>();

    /**
     *  设置数据源
     * @param db
     */
    public static void setDataSource(String db){
        Stack<String> st = contextHolder.get();
        if(st==null){
            st = new Stack<String>();
            contextHolder.set(st);
        }
        st.push(db);
    }

    /**
     * 取得当前数据源
     * @return
     */
    public static String getDataSource(){
        Stack<String> st = contextHolder.get();
        //不移除
        return st==null||st.isEmpty()?null:st.peek();
    }

    /**
     * 清除上下文数据
     */
    public static void clear(){
        contextHolder.remove();
    }

    /**
     * 清除当前db
     */
    public static void remove(){
        Stack<String> st = contextHolder.get();
        if(st==null||st.isEmpty()){
            logger.warn("数据源切换异常");
        }else {
            st.pop();
        }
    }

}
