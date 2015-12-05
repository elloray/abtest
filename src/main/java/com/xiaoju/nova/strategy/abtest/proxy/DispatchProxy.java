package com.xiaoju.nova.strategy.abtest.proxy;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.xiaoju.nova.strategy.abtest.Constrants;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class DispatchProxy implements MethodInterceptor {

	private final static Logger logger = Logger.getLogger(DispatchProxy.class);

	private Method MinorMethod;
	private DispatchStrategy dispatchStrategy;
	// 计数器
	private volatile double counter = 0;

	public void setMinorMethod(Method MinorMethod) {
		this.MinorMethod = MinorMethod;
	}
	public void setDispatchStrategy(DispatchStrategy dispatchStrategy) {
		this.dispatchStrategy = dispatchStrategy;
	}
	
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		if (MinorMethod == null) {
			if (dispatchStrategy.validate(args)) {
				if (dispatch()) {
					logger.info("leads to experinment flow : "
							+ method.getName());
					return proxy.invokeSuper(obj, dispatchStrategy.adjust(args));
				}
			}
			logger.info("leads to normal flow : " + method.getName());
			return proxy.invokeSuper(obj, args);
		} else {
			if (dispatch()) {
				logger.info("leads to experinment flow : "
						+ MinorMethod.getName());
				return MinorMethod.invoke(obj, args);
			} else {
				logger.info("leads to normal flow : " + method.getName());
				return proxy.invokeSuper(obj, args);
			}
		}
	}

	// 流量切分
	public boolean dispatch() {
		counter += Constrants.TRAFFIC_RATE;
		if (counter >= 1) {
			counter -= 1;
			return true;
		} else {
			return false;
		}
	}
}
