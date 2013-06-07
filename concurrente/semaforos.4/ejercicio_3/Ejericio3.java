package ejercicio_3;

import java.util.concurrent.Semaphore;

import utils.Utils;

public class Ejericio3 extends Thread {

	public String first;

	public Ejericio3(String first) {
		this.first = first;
	}

	public static void main(String[] args) {

		final Semaphore semaphoreOK = new Semaphore(0);
		final Semaphore semaphoreI = new Semaphore(0);
		final Semaphore semaphoreO = new Semaphore(0);
		
		Ejericio3 _1 = new Ejericio3("R") {
			public void run() {
				Utils.print(this.first);
				semaphoreI.release();
				Utils.acquire(semaphoreOK);
				Utils.print("OK_r");
			}

		};

		Ejericio3 _2 = new Ejericio3("I") {
			public void run() {
				Utils.acquire(semaphoreI);
				Utils.print(this.first);
				semaphoreO.release();
				Utils.acquire(semaphoreOK);
				Utils.print("OK_i");
			}
		};

		Ejericio3 _3 = new Ejericio3("O") {
			public void run() {
				Utils.acquire(semaphoreO);
				Utils.print(this.first);
				semaphoreOK.release();
				semaphoreOK.release();
				Utils.print("OK_o");
			}
		};

		_3.start();
		_2.start();
		_1.start();

	}

}
