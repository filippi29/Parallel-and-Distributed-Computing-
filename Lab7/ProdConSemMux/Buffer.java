//GIA size = 1 

import java.util.concurrent.Semaphore;

public class Buffer {
	private int contents;
	private Semaphore producer = new Semaphore(1);
	private Semaphore consumer = new Semaphore(0);

	// Constructor
	public Buffer() {
		contents = 0;
	}

	// Put an item into buffer
	public void put(int data) {
		try {
			producer.acquire();
		} catch (InterruptedException e) {
		}

		contents = data;
		System.out.println("Prod " + Thread.currentThread().getName() + " No " + data + " Count = 1");
		System.out.println("The buffer is full");
		consumer.release();
	}

	// Get an item from bufffer
	public int get() {
		int data = 0;
		try {
			consumer.acquire();
		} catch (InterruptedException e) {
		}
		data = contents;
		System.out.println("  Cons " + Thread.currentThread().getName() + " No " + data + " Count = 0");
		System.out.println("The buffer is empty");
		producer.release();
		return data;
	}
}
