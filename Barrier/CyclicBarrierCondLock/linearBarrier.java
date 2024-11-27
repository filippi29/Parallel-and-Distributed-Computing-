import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class linearBarrier {
    private int arrived = 0;
    private int totalThreads;
    //private Semaphore waiting = new Semaphore(1);
    //private Semaphore leaving = new Semaphore(0);
    private Lock lock = new ReentrantLock();
    Condition cWait = lock.newCondition();
    Condition cLeave = lock.newCondition();
    private  boolean waiting = false;
    private boolean leaving = true;

    public linearBarrier (int total) {
        this.totalThreads = total;
    }

    public void before_barrier()
    {
        lock.lock(); //waiting
        try {
            arrived++;
            System.out.println("waiting "+Thread.currentThread().getName());
            if (arrived == totalThreads) {
                waiting = true;
                leaving = false;
            }

            while (!waiting) cWait.await();{
                cWait.signal();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }

        lock.lock(); // leaving
        try {
            arrived--;
            if (arrived == 0) {
                waiting = false;
                leaving = true;
            }
            while (!leaving) cLeave.await();
            cLeave.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }}
