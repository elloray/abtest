package com.xiaoju.nova.strategy.abtest.proxy;

public interface DispatchStrategy {

	// 根据参数AB时，判断是否该走小流量
	public boolean validate(Object[] args);

	// 流量试验中参数怎么修正
	public Object[] adjust(Object[] args);
	
}
