package com.xiaoju.nova.strategy.abtest.statistics;

public interface Collector {
	public boolean collectMajor(Object[] objects);
	public boolean collectMinor(Object[] objects);
}
