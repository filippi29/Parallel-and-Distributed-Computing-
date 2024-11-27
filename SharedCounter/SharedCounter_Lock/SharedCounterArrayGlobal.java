

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedCounterArrayGlobal {

    public static void main(String[] args) {
    	
        int end = 1000;
        int[] array = new int[end];
        int numThreads = 4;
        Lock lock = new ReentrantLock();

        CounterThread threads[] = new CounterThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(array, end, lock);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        check_array(array, end, numThreads);
    }

    static void check_array(int[] array, int end, int numThreads) {
        int errors = 0;

        System.out.println("Checking...");

        for (int i = 0; i < end; i++) {
            if (array[i] != numThreads * i) {
                errors++;
                System.out.printf("%d: %d should be %d\n", i, array[i], numThreads * i);
            }
        }
        System.out.println(errors + " errors.");
    }

    static class CounterThread extends Thread {

        int[] array;
        int end;
        Lock lock;

        public CounterThread(int[] array, int end,  Lock lock) {
            this.array = array;
            this.end = end;
            this.lock = lock;
        }

        public void run() {
            for (int i = 0; i < end; i++) {
                lock.lock();
                try {
                    for (int j = 0; j < i; j++)
                        array[i]++;
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}


