class testThread extends Thread {

    private int threadID;
    private linearBarrier myBarrier;

    public testThread(int tid, linearBarrier bar) {
        this.threadID = tid;
        this.myBarrier = bar;
    }

    public void run() {

        for (int i = 0; i < 10; i++) {
            System.out.println("Thread " + threadID + " started");
            myBarrier.before_barrier();  // Καλεί την before_barrier για το φράγμα
            try {
                sleep((int)(Math.random() * 1000));  // Προσομοίωση εργασίας
            } catch (InterruptedException e) {}
            System.out.println("Thread " + threadID + " reached barrier");
            myBarrier.before_barrier();  // Καλεί ξανά την before_barrier
            System.out.println("Thread " + threadID + " passed barrier");
            myBarrier.before_barrier();  // Καλεί ξανά την before_barrier
        }
    }
}
