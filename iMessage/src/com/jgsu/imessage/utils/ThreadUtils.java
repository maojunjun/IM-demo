package com.jgsu.imessage.utils;

import android.os.Handler;

public class ThreadUtils {

	public static void runInThread(Runnable r) {
		new Thread(r).start();
	};

	
	private static Handler hanlder = new Handler();

	public static void runUnThread(Runnable r) {
		
		hanlder.post(r);
		
	};
	
	
}
