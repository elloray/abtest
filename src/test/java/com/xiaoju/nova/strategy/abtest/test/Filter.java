package com.xiaoju.nova.strategy.abtest.test;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

import com.xiaoju.nova.strategy.abtest.Constrants;

import net.sf.cglib.proxy.CallbackFilter;

public class Filter implements CallbackFilter {

	private static final int OTHER_METHOD = 0;
	private static final int MAJOR_METHOD = 1;
	private static final int MINOR_METHOD = 1;
	// 计数器
	private AtomicInteger counter = new AtomicInteger(1);

	private String MajorMethodName = null;

	public int accept(Method method) {
		if (MajorMethodName == null)
			throw new NullPointerException("method name is null");
		else if (MajorMethodName.equals(method.getName())) {
			//流量切分
			if (counter.getAndIncrement() * Constrants.TRAFFIC_RATE >= 1) {
				counter.set(0);
				return MINOR_METHOD;
			}
			return MAJOR_METHOD;
		}
		return OTHER_METHOD;
	}

	public Filter(String MethodName) {
		this.MajorMethodName = MethodName;
	}
}
