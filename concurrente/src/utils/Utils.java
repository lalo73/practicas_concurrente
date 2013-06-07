package utils;

import java.util.concurrent.Semaphore;

public class Utils {

	public static void print(Object object) {
		System.out.println(object);
	}

	public static void acquire(Semaphore semaphore) {
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
