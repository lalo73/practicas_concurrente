package ejercicio_1;

import java.util.concurrent.Semaphore;

import utils.Utils;

public class Ejericio1 {
	public static void main(String[] args) {

		final Semaphore semaphoreF = new Semaphore(0);
		final Semaphore semaphoreC = new Semaphore(0);

		Thread a = new Thread() {
			public void run() {
				Utils.print("A");
				semaphoreF.release();
				Utils.print("B");
				try {
					semaphoreC.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Utils.print("C");
			}
		};

		Thread b = new Thread() {
			public void run() {
				try {
					semaphoreF.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Utils.print("F");
				semaphoreC.release();
				Utils.print("D");
				Utils.print("G");
			}
		};
		b.start();
		a.start();

	}

}
