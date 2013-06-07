package ejercicio_5;

import java.util.concurrent.Semaphore;

import utils.Utils;

public class Ejericio5 {

	public static void main(String[] args) {

		final Semaphore semaphoreF = new Semaphore(0);
		final Semaphore semaphoreH = new Semaphore(0);
		final Semaphore semaphoreC = new Semaphore(0);
		final int length = 10;

		Thread a = new Thread() {
			public void run() {
				int i = 0;
				int a = 0;
				int c = 0;
				do {
					Utils.print("A" + a++);
					semaphoreF.release();
					Utils.print("B");
					Utils.acquire(semaphoreC);
					Utils.print("C" + c++);
					Utils.print("D");
					i++;
				} while (i < length);
			}
		};
		Thread b = new Thread() {
			public void run() {
				int i = 0;
				int f = 0;
				int e = 0;
				int g = 0;
				do {
					Utils.print("E" + e++);
					semaphoreH.release();
					Utils.acquire(semaphoreF);
					Utils.print("F" + f++);
					Utils.print("G" + g++);
					semaphoreC.release();
					i++;
				} while (i < length);
			}
		};
		Thread c = new Thread() {
			public void run() {
				int i = 0;
				int h = 0;
				do {
					Utils.acquire(semaphoreH);
					Utils.print("H" + h++);
					Utils.print("I");
					i++;
				} while (i < length);
			}
		};

		c.start();
		a.start();
		b.start();


	}

}
