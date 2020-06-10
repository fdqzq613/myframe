package com.some.example.thread;

import java.util.concurrent.*;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-09 17:02
 */
public class ExecutorsTest {
   private ConcurrentHashMap concurrentHashMap;
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<Object> future = executorService.submit(new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                Thread.sleep(10000);
                return "333";
            }
        });

        try {
            Object o =  future.get();
            System.out.println(o);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
