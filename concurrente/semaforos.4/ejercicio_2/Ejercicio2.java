package ejercicio_2;

import java.util.concurrent.Semaphore;
import utils.Utils;

public class Ejercicio2 {
	public static void main(String[] args) {
		final Semaphore semaphoreRO = new Semaphore(0);
		final Semaphore semaphoreC = new Semaphore(0);

		Thread a = new Thread() {
			public void run() {
				Utils.print("A");
				semaphoreC.release();
				Utils.acquire(semaphoreRO);
				Utils.print("R");
				Utils.acquire(semaphoreRO);
				Utils.print("O");
			}
		};

		Thread b = new Thread() {
			public void run() {
				Utils.acquire(semaphoreC);
				Utils.print("C");
				semaphoreRO.release();
				Utils.print("E");
				semaphoreRO.release();
			}
		};
		b.start();
		a.start();

	}
}
