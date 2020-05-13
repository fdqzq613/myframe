package com.some.rpc.common;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import com.netflix.hystrix.strategy.properties.HystrixProperty;

/**
 * 
 * @version V1.0
 * @author qzq
 * @date   2020年5月6日
 */
@Component
public class FeignHystrixConcurrencyStrategy extends HystrixConcurrencyStrategy {  

	  private static final Logger log = LoggerFactory.getLogger(FeignHystrixConcurrencyStrategy.class);  
	  private HystrixConcurrencyStrategy delegate;  

	  public FeignHystrixConcurrencyStrategy() {  
	    try {  
	      this.delegate = HystrixPlugins.getInstance().getConcurrencyStrategy();  
	      if (this.delegate instanceof FeignHystrixConcurrencyStrategy) {  
	        // Welcome to singleton hell...  
	        return;  
	      }  
	      HystrixCommandExecutionHook commandExecutionHook =  
	          HystrixPlugins.getInstance().getCommandExecutionHook();  
	      HystrixEventNotifier eventNotifier = HystrixPlugins.getInstance().getEventNotifier();  
	      HystrixMetricsPublisher metricsPublisher = HystrixPlugins.getInstance().getMetricsPublisher();  
	      HystrixPropertiesStrategy propertiesStrategy =  
	          HystrixPlugins.getInstance().getPropertiesStrategy();  
	      this.logCurrentStateOfHystrixPlugins(eventNotifier, metricsPublisher, propertiesStrategy);  
	      HystrixPlugins.reset();  
	      HystrixPlugins.getInstance().registerConcurrencyStrategy(this);  
	      HystrixPlugins.getInstance().registerCommandExecutionHook(commandExecutionHook);  
	      HystrixPlugins.getInstance().registerEventNotifier(eventNotifier);  
	      HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher);  
	      HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy);  
	    } catch (Exception e) {  
	      log.error("Failed to register Sleuth Hystrix Concurrency Strategy", e);  
	    }  
	  }  

	  private void logCurrentStateOfHystrixPlugins(HystrixEventNotifier eventNotifier,  
	      HystrixMetricsPublisher metricsPublisher, HystrixPropertiesStrategy propertiesStrategy) {  
	    if (log.isDebugEnabled()) {  
	      log.debug("Current Hystrix plugins configuration is [" + "concurrencyStrategy ["  
	          + this.delegate + "]," + "eventNotifier [" + eventNotifier + "]," + "metricPublisher ["  
	          + metricsPublisher + "]," + "propertiesStrategy [" + propertiesStrategy + "]," + "]");  
	      log.debug("Registering Sleuth Hystrix Concurrency Strategy.");  
	    }  
	  }  

	  @Override  
	  public <T> Callable<T> wrapCallable(Callable<T> callable) {  
	    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();  
	    return new WrappedCallable<>(callable, requestAttributes);  
	  }  

	  @Override  
	  public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey,  
	      HystrixProperty<Integer> corePoolSize, HystrixProperty<Integer> maximumPoolSize,  
	      HystrixProperty<Integer> keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {  
	    return this.delegate.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime,  
	        unit, workQueue);  
	  }  

	  @Override  
	  public BlockingQueue<Runnable> getBlockingQueue(int maxQueueSize) {  
	    return this.delegate.getBlockingQueue(maxQueueSize);  
	  }  

	  @Override  
	  public <T> HystrixRequestVariable<T> getRequestVariable(HystrixRequestVariableLifecycle<T> rv) {  
	    return this.delegate.getRequestVariable(rv);  
	  }  

	  static class WrappedCallable<T> implements Callable<T> {  
	    private final Callable<T> target;  
	    private final RequestAttributes requestAttributes;  

	    public WrappedCallable(Callable<T> target, RequestAttributes requestAttributes) {  
	      this.target = target;  
	      this.requestAttributes = requestAttributes;  
	    }  

	    @Override  
	    public T call() throws Exception {  
	      try {  
	        RequestContextHolder.setRequestAttributes(requestAttributes);  
	        return target.call();  
	      } finally {  
	        RequestContextHolder.resetRequestAttributes();  
	      }  
	    }  
	  }  
	}  