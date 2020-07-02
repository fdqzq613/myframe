package com.some.dubbo.consumer;

import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.Directory;
import org.apache.dubbo.rpc.cluster.support.AbstractClusterInvoker;
import org.apache.dubbo.rpc.cluster.support.wrapper.AbstractCluster;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-07-01 17:07
 */
public class MyFailCluster extends AbstractCluster {

    public MyFailCluster() {
    }

    @Override
    public <T> AbstractClusterInvoker<T> doJoin(Directory<T> directory) throws RpcException {
        return new MyFailClusterInvoker(directory);
    }
}
