package com.xiaoju.nova.strategy.abtest.test;

import com.xiaoju.nova.strategy.abtest.annotation.DispatchByMethod;
import com.xiaoju.nova.strategy.abtest.annotation.DispatchByParam;



@DispatchByMethod(Major = "add", Minor = "add2")
public class testClass {

	@DispatchByParam
	public int add(int a, int b) {
		return a + b;
	}

	public int add2(int a, int b) {
		return 10 * a + b;
	}
}
