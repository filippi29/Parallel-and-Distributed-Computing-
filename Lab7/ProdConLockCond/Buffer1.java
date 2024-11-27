//GIA SIZE =1

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Buffer1 {
	private int contents;
	private Lock lock = new ReentrantLock();
	private Condition bufferFull = lock.newCondition();
	private Condition bufferEmpty = lock.newCondition();

	// Constructor
	public Buffer1(int s) {
		contents = 0;
	}

	// Put an item into buffer
	public void put(int data) {

		System.out.println("The buffer is full");
		try {
			bufferFull.await();
		} catch (InterruptedException e) {
		}

		contents = data;
		System.out.println("Prod " + Thread.currentThread().getName() + " No " + data + " Count = 1");
		// buffer was empty
		bufferEmpty.signalAll();

	}

	// Get an item from bufffer
	public int get() {
		int data = 0;

		System.out.println("The buffer is empty");
		try {
			bufferEmpty.await();
		} catch (InterruptedException e) {
		}

		data = contents;
		System.out.println("  Cons " + Thread.currentThread().getName() + " No " + data + " Count = 0 ");

		// buffer was full
		bufferFull.signalAll();

		return data;
	}
}
