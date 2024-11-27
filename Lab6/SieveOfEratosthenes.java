class SieveOfEratosthenes
{
	public static void main(String[] args)
	{  
		int numThreads = Runtime.getRuntime().availableProcessors();
		SieveThreads[] threads = new SieveThreads[numThreads];
		
		int size = 1000000000;
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

		boolean[] prime = new boolean[size+1];

		SharedCounter counter =  new SharedCounter(size);
		
		// get current time 
		long start = System.currentTimeMillis();
		
		//for (int p = 2; p*p <= size; p++)
		int limit = (int)Math.sqrt(size) + 1;
		 for (int i = 0; i<numThreads; i++) {
	        	threads[i] = new SieveThreads(i, numThreads, size,limit,  counter);
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
               
		// get current time and calculate elapsed time
		long elapsedTimeMillis = System.currentTimeMillis()-start;

		int count = 0;
		for(int i = 2; i <= size; i++) 
			if (counter.getPrime()[i] == true) {
				//System.out.println(i); 
				count++;
			}
				
		System.out.println("number of primes "+count); 
		System.out.println("time in ms = "+ elapsedTimeMillis);
	}

}
class SharedCounter{
	private boolean prime[];

	public SharedCounter(int size) {
		this.prime = new boolean[size +1];
		for(int i = 2; i <= size; i++)
			prime[i] = true; 		

	}

	public synchronized boolean isPrime(int i) {
		return prime[i];
	}

	public void setPrime(int i) {
		this.prime[i] = false;
	}

	public boolean[] getPrime() {
		return prime;
	}
	
	
}

class SieveThreads extends Thread {
    private int myId;
    private int myStart;
    private int myStop;
    private int size;
    private SharedCounter counter;

    public SieveThreads(int id, int numThreads, int size, long limit, SharedCounter counter) {
        this.size = size;
        this.counter = counter;
        myId = id;
        myStart = (int) (myId * (limit / numThreads));
        myStop = (int) (myStart + (limit / numThreads));
        if (myId == (numThreads - 1)) myStop = (int) limit;
    }

    @Override
    public void run() {
        for (int p = myStart; p <= myStop; p++)
        {
            //If prime[p] is not changed, then it is a prime
            if(counter.isPrime(p))
            {
                // Update all multiples of p
                for (int i = p*p; i <= size; i += p)
                {
                    counter.setPrime(i);
                }

            }
        }
    }
}