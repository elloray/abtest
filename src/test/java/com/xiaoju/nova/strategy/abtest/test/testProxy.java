package com.xiaoju.nova.strategy.abtest.test;

import com.xiaoju.nova.strategy.abtest.example.ExampleDispatchStrategy;
import com.xiaoju.nova.strategy.abtest.proxy.ProxyFactory;

public class testProxy {
	public static void main(String[] args) throws InterruptedException {
		// TrafficProxy proxy = new TrafficProxy(new HashMap<String, Double>());
		// testClass test = (testClass) proxy.getProxy(testClass.class);

		testClass test = (testClass) ProxyFactory.getProxy(testClass.class,
				new ExampleDispatchStrategy());
		for (int i = 0; i < 10; i++) {
			System.out.println("目前是第" + i + "轮");
			// Thread.sleep(1000);
			System.out.println(test.add(1, 2));
		}
	}
}
 