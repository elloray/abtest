package com.xiaoju.nova.strategy.abtest.statistics;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.xiaoju.nova.strategy.abtest.exception.CollectorEmptyException;
import com.xiaoju.nova.strategy.abtest.exception.ColletorFullException;

public class Collector<E> {

	private BlockingQueue<E> datas = new ArrayBlockingQueue<E>(
			Integer.MAX_VALUE);

	public boolean collect(E data) {
		boolean result = datas.offer(data);
		if(result){
			throw new ColletorFullException("collector is full");
		}
		return result;
	}

	public E take() {
		E element = datas.poll();
		if (element == null) {
			throw new CollectorEmptyException("collector is empty");
		}
		return element;
	}
	
	
}
