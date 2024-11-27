
public class NumIntRecursive {

    public static void main(String[] args) {
 
	int size = 100000000; //numSteps
	int limit = 9000000; 
    double sum = 0.0;

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

    double step = 1.0 / (double)size;
   
	/* create and start thread 0 */
	Recursive mythread = new Recursive(0, size, limit, step);
	mythread.start();        

	/* wait for thread 0 */
	try {
		mythread.join();
		sum = mythread.myResult;
	} catch (InterruptedException e) {}

	System.out.println("Sum "+sum);
	
	   double pi = sum * step;
	   /* end timing and print result */
       long endTime = System.currentTimeMillis();
       System.out.printf("sequential program results with %d steps\n", size);
       System.out.printf("computed pi = %22.20f\n" , pi);
       System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
       System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
   
    }
}

class Recursive extends Thread {

	private int myFrom;
	private int myTo;
	private int myLimit;
	private int[] myA;
	public  double myResult;
	public double myStep;
   
	public Recursive (int from, int to, int limit,  double step) {
		this.myFrom = from;
		this.myTo = to;
		this.myLimit = limit;
		this.myResult = 0;
		this.myStep = step;

	}

	public void run() {
		/* do recursion until limit is reached */
		//System.out.println("In thread"+Thread.currentThread().getName());
		int workload = myTo - myFrom;
		if (workload <= myLimit) {
		    //System.out.printf("Cutoff %d %d \n",myFrom, myTo);
			myResult = 0;
        	for (int i=myFrom; i<myTo; i++)
        		 {
        		double x = ((double)i+0.5)*myStep;
        		myResult +=  4.0/(1.0+x*x);
        		//}
		} 
		}else {
			int mid = myFrom + workload / 2;
			//System.out.printf("L %d %d %d \n",myFrom, mid, myLimit);
			Recursive threadL = new Recursive(myFrom, mid, myLimit,myStep);
			threadL.start();
			//System.out.printf("R %d %d %d \n", mid, myTo, myLimit);
			Recursive threadR = new Recursive(mid, myTo, myLimit, myStep);
			threadR.start();
			try {
				threadL.join();
				myResult = threadL.myResult;
				threadR.join();
				myResult += threadR.myResult;
			} catch (InterruptedException e) {}
	    }
   }

}

