package com.github.elloray.abtest.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.CallbackFilter;

import com.github.elloray.abtest.annotation.DispatchByParam;

public class MethodFilter implements CallbackFilter {

	private static final int OTHER_METHOD = 0;
	private static final int EXP_METHOD = 1;

	private String MethodName = null;

	public int accept(Method method) {
		if (MethodName == null) {
			if (method.getAnnotation(DispatchByParam.class) == null)
				return OTHER_METHOD;
			else
				return EXP_METHOD;
		} else if (MethodName.equals(method.getName())) {
			return EXP_METHOD;
		} else {
			return OTHER_METHOD;
		}
	}

	public MethodFilter(String MethodName) {
		this.MethodName = MethodName;
	}

	public MethodFilter() {
	}
}
