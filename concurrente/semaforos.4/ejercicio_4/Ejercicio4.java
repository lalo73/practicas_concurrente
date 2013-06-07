package ejercicio_4;

import java.util.concurrent.Semaphore;
import utils.Utils;

public class Ejercicio4 {
	static int y = 0, z = 0;

	public static void main(String[] args) {

		final Semaphore semaphore = new Semaphore(0);

		Thread a = new Thread() {

			public void run() {
				Utils.acquire(semaphore);
				int x;
				x = y + z;
				Utils.print(x);
			}
		};
		Thread b = new Thread() {
			public void run() {
				y = 1;
				semaphore.release();
				z = 2;
			}
		};
		a.start();
		b.start();
		

	}
}
