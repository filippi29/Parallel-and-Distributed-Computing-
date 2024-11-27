
// GIA int bufferSize = Math.max(noProds * noIterations, 1); // Smallest amount of size is 1 (Sthn klash ProducerConsumer)
// GIA SIZE = MAX
import java.util.concurrent.Semaphore;

public class Buffer2 {
	private int[] contents;
	private int size;
	private int front, back;
	private int counter = 0;
	private Semaphore mutex = new Semaphore(1);
	private Semaphore bufferFull = new Semaphore(0);
	private Semaphore bufferEmpty;

	// Constructor
	public Buffer2(int s) {
		this.size = s;
		contents = new int[size];
		for (int i = 0; i < size; i++)
			contents[i] = 0;
		this.front = 0;
		this.back = -1;
		this.bufferEmpty = new Semaphore(size);
	}

	// Put an item into buffer
	public void put(int data) {
		try {
			bufferEmpty.acquire();
		} catch (InterruptedException e) {
		}
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
		}
		back = (back + 1);
		contents[back] = data;
		counter++;
		System.out.println(
				"Prod " + Thread.currentThread().getName() + " No " + data + " Loc " + back + " Count = " + counter);
		mutex.release();
		bufferFull.release();
	}

	// Get an item from bufffer
	public int get() {
		int data = 0;
		try {
			bufferFull.acquire();
		} catch (InterruptedException e) {
		}
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
		}
		data = contents[front];
		System.out.println("  Cons " + Thread.currentThread().getName() + " No " + data + " Loc " + front + " Count = "
				+ (counter - 1));
		front = (front + 1);
		counter--;
		if (counter == 0)
			System.out.println("The buffer is empty");
		mutex.release();
		bufferEmpty.release();
		return data;
	}
}
