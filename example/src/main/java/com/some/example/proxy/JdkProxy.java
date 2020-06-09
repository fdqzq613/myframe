package com.some.example.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 */
@Slf4j
public class JdkProxy {

    public static void main(String[] args) {
        IHandler handler = new IHandler() {
            @Override
            public final void doSome() {
                log.info("我执行了");
            }
        };
        IHandler handlerProxy=(IHandler) Proxy.newProxyInstance(handler.getClass().getClassLoader(), handler.getClass().getInterfaces(),new JDKDynamicProxy(handler));

        //handlerProxy.doSome();
        IHandler handlerProxy1 = (IHandler) new JDKDynamicProxy().createProxy(handler);
        //handlerProxy1.doSome();
        IHandler handlerProxy2 = (IHandler) new JDKDynamicProxy().createProxy(handlerProxy1);
        handlerProxy2.doSome();
    }

    public static class JDKDynamicProxy implements InvocationHandler {
        private Object target;
        public JDKDynamicProxy(IHandler target){
            this.target = target;
        }
        public JDKDynamicProxy(){

        }
        public Object createProxy(Object target) {
            this.target = target;
            return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                    target.getClass().getInterfaces(), this);//返回代理对象
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            log.info("准备执行");
            Object result = method.invoke(target, args);
           log.info("执行完成");
            return result;
        }
    }
    public interface IHandler{
       void doSome();
    }
}
