public class Main {

    public static void main(String[] args) {

        // Ο αριθμός των threads (μπορείς να τον αλλάξεις)
        int numThreads = 5;

        // Δημιουργία του barrier με τον αριθμό των threads
        linearBarrier testBarrier = new linearBarrier(numThreads);

        // Δημιουργία ενός πίνακα threads
        testThread[] testThreads = new testThread[numThreads];

        // Εκκίνηση κάθε thread
        for (int i = 0; i < numThreads; i++) {
            testThreads[i] = new testThread(i, testBarrier);
            testThreads[i].start();  // Εκκίνηση κάθε thread
        }

        // Περιμένουμε να ολοκληρωθούν όλα τα threads
        for (int i = 0; i < numThreads; i++) {
            try {
                testThreads[i].join();  // Περιμένει κάθε thread να τελειώσει
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Εκτυπώνουμε το χρόνο εκτέλεσης
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - System.currentTimeMillis()) + " milliseconds");
    }
}
