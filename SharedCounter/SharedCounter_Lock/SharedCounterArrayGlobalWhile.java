
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
 
public class SharedCounterArrayGlobalWhile {
 
    public static void main(String[] args) {
 
        int end = 10000;
        int numThreads = 4;
        Lock lock = new ReentrantLock();
        
        SharedCounter counter = new SharedCounter(end);

 
        CounterThread threads[] = new CounterThread[numThreads];
 
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(end,counter,lock);
            threads[i].start();
        }
 
 
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            }
            catch (InterruptedException e) {}
        }
        check_array (end,counter);
    }
 
    static void check_array ( int end, SharedCounter counter)  {
        int i, errors = 0;
 
        System.out.println ("Checking...");
 
        for (i = 0; i < end; i++) {
            if (counter.getArray()[i]!= 1) {
                errors++;
                System.out.printf("%d: %d should be 1\n", i, counter.getArray()[i]);
            }
        }
        System.out.println (errors+" errors.");
    }
 
    static class CounterThread extends Thread {
 
        
        int end;
        SharedCounter counter;
        Lock lock;
 
        public CounterThread( int end,SharedCounter counter,  Lock lock) {
            
            this.end = end;
            this.counter = counter;
            this.lock = lock;
        }
 
        public void run() {
 
            while (true) {
 
                lock.lock();
                try {
                    if (counter.get() >= end)
                        break;
                   // counter.getArray()[counter.get()]++;
                    counter.inc();
                } finally {
                    lock.unlock();
                }
 
            }
        }
    }
 
}
 
class SharedCounter {
    private int counter ;
    private  int[] array ;
    private int end ;
   // Lock lock = new ReentrantLock();
    
    public SharedCounter (int end){
    	this.end = end;
	    this.counter = 0;
	    this.array = new int[end];
    }
 
    public void inc(){
    	array[counter]++;
        counter++;
       /* lock.lock();
   		try {
   		    counter = counter + 1;
   		} finally {    
   		   lock.unlock();
   		}*/ 
       }


 
    public int get(){
        return counter;
    	/*lock.lock();
		try {
		    return counter;
		} finally {    
		   lock.unlock();
		} */  
    }

	public int[] getArray() {
		return array;
	}
    
    
}
