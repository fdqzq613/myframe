package com.some.example.proxy;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 */
@Slf4j
public class CglibProxy {
    public static void main(String[] args) {
        //在指定目录下生成动态代理类，可以反编译查看内容
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\code");
        //创建Enhancer对象，类似于JDK动态代理的Proxy类，下一步就是设置几个参数
        Enhancer enhancer = new Enhancer();
        //设置目标类的字节码文件
        enhancer.setSuperclass(Some.class);
        //设置回调函数
        enhancer.setCallback(new MyMethodInterceptor());

        //这里的creat方法就是正式创建代理类
        Some proxy = (Some)enhancer.create();
        //调用代理类方法
        proxy.doSome();
    }

    public static class Some{
        void doSome(){
            log.info("我执行了");
        }
    }
    public static class MyMethodInterceptor implements MethodInterceptor{

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            log.info("准备执行");
            Object object = methodProxy.invokeSuper(obj, args);
            log.info("执行完成");
            return object;
        }
    }
}
