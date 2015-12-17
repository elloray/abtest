package com.github.elloray.abtest.proxy;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import com.github.elloray.abtest.annotation.DispatchByMethod;

public class ProxyFactory {

	private final static Logger logger = Logger.getLogger(ProxyFactory.class);

	private ProxyFactory() {
	}

	public static Object getProxy(Class<?> clazz, DispatchStrategy dispatchStrategy) {
		DispatchByMethod annotation = clazz
				.getAnnotation(DispatchByMethod.class);
		// 创建Enhancer
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		// 创建筛选器
		MethodFilter filter = new MethodFilter(annotation.Major());
		// 定义ab方法
		Method MinorMethod = null;
		// 获取minor方法
		Method[] Methods = clazz.getMethods();
		for (Method method : Methods) {
			if (method.getName().equals(annotation.Minor())) {
				MinorMethod = method;
			}
		}
		// 设置拦截器实例
		DispatchProxy dispatchProxy = new DispatchProxy();
		dispatchProxy.setMinorMethod(MinorMethod);
		dispatchProxy.setDispatchStrategy(dispatchStrategy);
		enhancer.setCallbacks(new Callback[] { NoOp.INSTANCE, dispatchProxy });
		// 设置筛选规则
		enhancer.setCallbackFilter(filter);
		logger.info("proxy is created");
		return enhancer.create();
	}

}
