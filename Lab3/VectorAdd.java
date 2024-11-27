/* Vector Addition a = b + c                       */
/* ics21060                                        */
/* Lab3, exercise 1                                */


class VectorAddThread extends Thread {
    private double[] a;
    private double[] b;
    private double[] c;
    
    private int myStart;
    private int myStop;

    public VectorAddThread(double[] a, double[] b, double[] c, int start, int end) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.myStart = start;
        this.myStop = end;
    }

    public void run() {
        for (int i = myStart; i < myStop; i++) {
            a[i] = b[i] + c[i];
        }
    } 
}

public class VectorAdd {
	
    public static void main(String[] args) {
    	
        int size = 1000;
        int numThreads = Runtime.getRuntime().availableProcessors();

        double[] a = new double[size];
        double[] b = new double[size];
        double[] c = new double[size];

        // initialize vectors a and b
        for(int i = 0; i < size; i++) {
            a[i] = 0.0;
    		b[i] = 1.0;
            c[i] = 0.5;
        }
    		
       
        
        // create threads
        VectorAddThread threads[] = new VectorAddThread[numThreads];
        
        // thread execution   
        for (int i = 0; i < numThreads; i++) { // based on code SqrtGroupThread
            int start = i * (size / numThreads);
            int end = start + (size/numThreads);
            if (i == (numThreads - 1)) {
            	end = size;
            }
            
            threads[i] = new VectorAddThread(a, b, c, start, end);
            threads[i].start();
        }

        // wait for threads to terminate            
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // for debugging
        for (int i = 0; i < size; i++) {
            System.out.println(a[i]);
        }
    }
}
