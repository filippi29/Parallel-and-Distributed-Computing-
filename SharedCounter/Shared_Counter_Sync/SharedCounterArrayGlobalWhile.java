public class SharedCounterArrayGlobalWhile {
  

    public static void main(String[] args) {

         int end = 10000;
         //int[] array = new int[end];
         int numThreads = 4;

         SharedCounter counter = new SharedCounter(end);

        CounterThread threads[] = new CounterThread[numThreads];
	
		for (int i = 0; i < numThreads; i++) {
			threads[i] = new CounterThread(end,counter);
			threads[i].start();
		}
	
		for (int i = 0; i < numThreads; i++) {
			try {
				threads[i].join();
			}
			catch (InterruptedException e) {}
		} 
        check_array (counter,end);
    }
     
    static void check_array (SharedCounter counter, int end)  {
		int i, errors = 0;

		System.out.println ("Checking...");

        for (i = 0; i < end; i++) {
			if (counter.getArray()[i] != 1) {
				errors++;
				System.out.printf("%d: %d should be 1\n", i, counter.getArray()[i]);
			}         
		}
        System.out.println (errors+" errors.");
    }


    static class CounterThread extends Thread {
    
        int end;
        SharedCounter counter;
  	
       public CounterThread(int end, SharedCounter counter) {
			
			this.end = end;
			this.counter = counter;
		}



	public void run() {
		 synchronized(SharedCounterArrayGlobal.class) {
            while (true) {
					if (counter.get() >= end) 
						break;
					//int currentCounter = counter.get();
					//counter.inc();
					//array[currentCounter]++;
				
                    counter.inc();
					
				}
            } 
	}
		}            	
    }

class SharedCounter{
	private int counter;
	private int end;
	private int array[];

	public SharedCounter(int end) {
		this.counter = 0;
		this.end = end;
		this.array =  new int[end];
	}
	
	public  void inc() {
		array[counter]++;
	    counter = counter + 1;
	}
	
	public  int get() {
	       return counter;
	}

	public int[] getArray() {
		return array;
	}
	     
	
	
	
}
