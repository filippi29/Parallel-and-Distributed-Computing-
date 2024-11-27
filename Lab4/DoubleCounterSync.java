class DoubleCounter {
	
    private int n1;
    private int n2;
    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public void inc1() { //increases n1 using synchronized
        synchronized (lock1) {
            n1++;
        }
    }

    public void inc2() { //increases n2 using synchronized
        synchronized (lock2) {
            n2++;
        }
    }

    public int getN1() {
        synchronized (lock1) {  //using lock1,lock2 in getN1 and getN2 is necessary bc they are called by the threads
            return n1;          // and if they were called by Main , there would be no need for that
        }
    }

    public int getN2() {
        synchronized (lock2) {
            return n2;
        }
    }
}

public class DoubleCounterSync {
	
    public static void main(String[] args) {
    	
        DoubleCounter counter = new DoubleCounter();

        
        Thread thread1 = new Thread(new Runnable() {
            
            public void run() {
                for (int i = 0; i < 20; i++) {
                    counter.inc1();
                }
            }
        });


        Thread thread2 = new Thread(new Runnable() {        
        	
            public void run() {
                for (int i = 0; i < 100; i++) {
                    counter.inc2();
                }
            }
        });


        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        
        System.out.println("n1 = " + counter.getN1());
        System.out.println("n2 = " + counter.getN2());
    }
}
