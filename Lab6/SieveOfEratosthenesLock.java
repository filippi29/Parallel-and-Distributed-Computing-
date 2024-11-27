import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class SieveOfEratosthenesLock {
	static int totalTasks;
	static int nThreads;
	static int tasksAssigned = -1;
	static Lock lock = new ReentrantLock();
	static boolean[] prime;
	static int size;
	
	public static void main(String[] args)
	{  
		size = 1000000000;
		prime = new boolean[size+1];
		int limit = (int)Math.sqrt(size) + 1;
		totalTasks = limit;
        nThreads =  Runtime.getRuntime().availableProcessors();
        
		for(int i=2; i<size; i++)
		{
			prime[i] = true; 
		} 

		long start = System.currentTimeMillis(); //ksekinaei na metraei
		Thread[] threads = new Thread[nThreads];
		for (int i = 0; i < threads.length; ++i)
		{
			threads[i] = new Thread(new Worker(i));
		}	
		
		for (int i = 0; i < threads.length; ++i)
		{
			threads[i].start();
		}

		for (int i = 0; i < threads.length; ++i)
		{
			try {
				threads[i].join();
			}
			catch (InterruptedException e) {
				System.err.println("this should not happen");
			}
		}
		
	
		
		if (args.length != 1) {
			System.out.println("Usage: java SieveOfEratosthenes <size>");
			System.exit(1);
		}

		try {
			size = Integer.parseInt(args[0]);
		}
		catch (NumberFormatException nfe) {
	   		System.out.println("Integer argument expected");
    		System.exit(1);
		}
		if (size <= 0) {
				System.out.println("size should be positive integer");
	    		System.exit(1);
		}


               
		// get current time and calculate elapsed time
		long elapsedTimeMillis = System.currentTimeMillis()-start;

		int count = 0;    //katametrisi
		for(int i = 2; i <= size; i++) 
			if (prime[i] == true) {
				//System.out.println(i); 
				count++;
			}
				
		System.out.println("number of primes "+count); 
		System.out.println("time in ms = "+ elapsedTimeMillis);
	}

	private static int getTask()
	{
        lock.lock();
        try {
			
			if (++tasksAssigned < totalTasks) 
				return tasksAssigned;
			else
				return -1;
        } finally {
        	lock.unlock() ;
        }			
	}
	
	
	private static class Worker implements Runnable {

		public Worker(int i) {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			
			int element;

			while ((element = getTask()) >= 0)
			{
				int p = element;
			
				// If prime[p] is not changed, then it is a prime
				if(prime[p] == true)
				{
					// Update all multiples of p
					for (int i = p*p; i <= size; i += p)
						prime[i] = false;
				
			}
			}
		}
		
	}
}


 

