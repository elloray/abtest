package com.github.elloray.abtest.statistics;

public interface Collector {
	public boolean collectMajor(Object[] objects);
	public boolean collectMinor(Object[] objects);
}
