// GIA SIZE = 1

public class Buffer1 {
	private int contents;
	private boolean bufferEmpty = true;
	private boolean bufferFull = false;

	// Constructor
	public Buffer1(int s) {
		contents = 0;

	}

	// Put an item into buffer
	public void put(int data) {
		while (bufferFull) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		contents = data;
		System.out.println("Item " + data + ". Count = 1");
		bufferEmpty = false;
		bufferFull = true;
		System.out.println("The buffer is full");

		// buffer was empty
		notifyAll();
	}

	// Get an item from bufffer
	public int get() {
		while (bufferEmpty) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		int data = contents;

		System.out.println("Item " + data + ". Count = 0");

		bufferFull = false;

		bufferEmpty = true;
		System.out.println("The buffer is empty");

		// buffer was full
		notifyAll();
		return data;
	}
}
