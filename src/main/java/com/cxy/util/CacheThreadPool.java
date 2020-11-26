package com.cxy.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CacheThreadPool {
	
	private static Executor executor = null;
	
	private CacheThreadPool(){}
	
	public static Executor genExectuor(){
		if(executor == null){
			executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*6);
//			executor = Executors.newCachedThreadPool();
			return executor;
		} else {
			return executor;
		}
	}

}
