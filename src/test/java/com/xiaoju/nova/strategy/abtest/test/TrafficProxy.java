package com.xiaoju.nova.strategy.abtest.test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

import org.apache.log4j.Logger;

import com.xiaoju.nova.strategy.abtest.annotation.DispatchByMethod;



public class TrafficProxy implements MethodInterceptor {

	private Logger logger = Logger.getLogger(TrafficProxy.class);
	private Method MinorMethod = null;
	private DispatchByMethod annotation;
	private AtomicInteger counter = new AtomicInteger(0);

	// 流量配置
	private HashMap<String, Double> Traffics = new HashMap<String, Double>();

	public TrafficProxy(HashMap<String, Double> Traffics) {
		this.Traffics = Traffics;
		logger.info("Traffics is loaded");
	}

	// 创建代理
	public Object getProxy(Class<?> clazz) {
		annotation = clazz.getAnnotation(DispatchByMethod.class);
		// 创建筛选器
		Filter filter = new Filter(annotation.Major());
		// 创建Enhancer
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		// 设置两个拦截器实例
		enhancer.setCallbacks(new Callback[] { NoOp.INSTANCE, this });
		// 设置筛选规则
		enhancer.setCallbackFilter(filter);
		// 判断注解是否为空
		if (annotation == null) {
			throw new IllegalArgumentException(
					"this class doesn't have ABTest annotation");
		}
		//获取minor方法
		Method[] Methods = clazz.getMethods();
		for (Method method : Methods) {
			if (method.getName().equals(annotation.Minor())) {
				MinorMethod = method;
			}
		}
		logger.info("proxy is created");
		return enhancer.create();
	}

	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		int a = Integer.parseInt(args[0].toString());
		int b = Integer.parseInt(args[1].toString());
		System.out.println("两个参数为：" + a + ":" + b);
		Object result = null;
//		if (counter.getAndIncrement() % 5 == 1) {
//			result = MinorMethod.invoke(obj, args);
//			return result;
//		}
		result = proxy.invokeSuper(obj, args);
		return result;
	}
}
