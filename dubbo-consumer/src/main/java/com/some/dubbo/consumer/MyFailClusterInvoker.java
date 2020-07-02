package com.some.dubbo.consumer;

import org.apache.dubbo.rpc.AsyncRpcResult;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.Directory;
import org.apache.dubbo.rpc.cluster.LoadBalance;
import org.apache.dubbo.rpc.cluster.support.AbstractClusterInvoker;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-07-01 17:10
 */
@Slf4j
public class MyFailClusterInvoker<T> extends AbstractClusterInvoker<T> {

    public MyFailClusterInvoker(Directory<T> directory) {
        super(directory);
    }

    @Override
    public Result doInvoke(Invocation invocation, List<Invoker<T>> invokers, LoadBalance loadbalance) throws RpcException {
        try {
            this.checkInvokers(invokers, invocation);
            Invoker<T> invoker = this.select(loadbalance, invocation, invokers, (List)null);
            return invoker.invoke(invocation);
        } catch (Throwable e) {
            log.error(e.getMessage(),e);
            return AsyncRpcResult.newDefaultAsyncResult((Object)null, (Throwable)null, invocation);
        }
    }
}
