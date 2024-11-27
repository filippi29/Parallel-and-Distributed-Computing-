

public class NumIntSeqParSync {
	public static void main(String[] args) {

        long numSteps = 10000000;
       // double sum = 0.0;
        int numThreads = Runtime.getRuntime().availableProcessors();
        
        SharedCounter counter  = new SharedCounter();

        /* parse command line
        if (args.length != 1) {
		System.out.println("arguments:  number_of_steps");
                System.exit(1);
        }
        try {
		numSteps = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
		System.out.println("argument "+ args[0] +" must be long int");
		System.exit(1);
        }*/
        
        
        /* start timing */
        long startTime = System.currentTimeMillis();

        double step = 1.0 / (double)numSteps;

        // create threads
     	NumIntThread threads[] = new NumIntThread[numThreads];
     	
    	// thread execution   
     	for (int i = 0; i < numThreads; i++) 
   		{
  			threads[i] = new NumIntThread(i, numThreads, numSteps, step, counter);
  			threads[i].start();
     	}
     	// wait for threads to terminate and collect result
        
        double sum1 = 0.0;         
		for (int i = 0; i < numThreads; i++) {
			try {
				threads[i].join();
				sum1 = sum1 + threads[i].getMySum();
			} catch (InterruptedException e) {}
		}
        
        /* do computation */
        
        double pi = sum1 * step;

        /* end timing and print result */
        long endTime = System.currentTimeMillis();
        System.out.printf("sequential program results with %d steps\n", numSteps);
        System.out.printf("computed pi = %22.20f\n" , pi);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }
}

class SharedCounter{
	private double sum;

	
	public SharedCounter() {
		this.sum = 0.0;
	}
	
	public synchronized void inc(double mySum ) {
		sum += mySum;
		
	}
	
}

class NumIntThread extends Thread{
	public double mySum;
	private int myId;
	private long myStart;
	private long myStop;
	private double myStep;
	private SharedCounter counter;

	// constructor
		public NumIntThread(int id, int numThreads, long numSteps, double myStep, SharedCounter counter)
		{
			mySum = 0.0;
			myId = id;
			myStart = myId * (numSteps / numThreads);
			myStop = myStart + (numSteps / numThreads);
			if (myId == (numThreads - 1)) myStop = numSteps;
			this.myStep = myStep;
			this.counter = counter;
		}
		
		public double getMySum() {
			return mySum;
		}

		// thread code
		public void run()
		{
			for (long i=myStart; i < myStop; ++i) {
	            double x = ((double)i+0.5)*myStep;
	            mySum += 4.0/(1.0+x*x);
	            
	        }
			
			counter.inc(mySum);
		}
}

