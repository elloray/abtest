package com.xiaoju.nova.strategy.abtest.example;

import com.xiaoju.nova.strategy.abtest.proxy.DispatchStrategy;

public class ExampleDispatchStrategy implements DispatchStrategy {

	public boolean validate(Object[] args) {
		return false;
	}

	public Object[] adjust(Object[] args) {
		return args;
	}
}
