package com.github.elloray.abtest.example;

import com.github.elloray.abtest.proxy.DispatchStrategy;

public class ExampleDispatchStrategy implements DispatchStrategy {

	public boolean validate(Object[] args) {
		return false;
	}

	public Object[] adjust(Object[] args) {
		return args;
	}
}
