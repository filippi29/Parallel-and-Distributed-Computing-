import  java.util.concurrent.Semaphore;

class linearBarrier {
    private int arrived = 0;
    private int totalThreads;
    private Semaphore waiting = new Semaphore(0);
    private Semaphore leaving = new Semaphore(1);
    private Semaphore mutex = new Semaphore(1);

    public linearBarrier (int total) {
        this.totalThreads = total;
    }

    public void before_barrier()
    {
        try {
            mutex.acquire();
            //waiting.acquire();
            arrived++;
            System.out.println("waiting "+Thread.currentThread().getName());
            if (arrived == totalThreads) {
                waiting.release();   // prepare waiting room
                leaving.release();   //close leaving room
            }
            mutex.release();

            waiting.acquire();      // enter waiting room
            waiting.release();      // exit waiting room

            mutex.acquire();        // protected decrement of arrived
            arrived--;
            if (arrived == 0) {
                waiting.acquire(); // close waiting room
                leaving.release(); // prepare leaving room
            }
            mutex.release();

            leaving.acquire(); // enter leaving room
            leaving.release(); // exit leaving room

        } catch (InterruptedException e) {};
        waiting.release();

        try {
            System.out.println("leaving "+Thread.currentThread().getName());
            leaving.acquire();
        } catch (InterruptedException e) {};
        leaving.release();
    }
}