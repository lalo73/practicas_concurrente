package utils;

import java.util.concurrent.Semaphore;

public class Utils {

	public static void print(String string) {
		System.out.println(string);
	}

	public static void acquire(Semaphore semaphore) {
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
