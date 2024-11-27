public class Main {

    public static void main(String[] args) {

        // Αρχική ώρα πριν την εκτέλεση
        long startTime = System.currentTimeMillis();

        int numThreads = 5;  // Ορίζουμε 5 threads (ή τον αριθμό που θες)

        // Δημιουργία του barrier με τον αριθμό των threads
        linearBarrier testBarrier = new linearBarrier(numThreads);

        // Δημιουργία ενός πίνακα threads για να τα εκκινήσουμε
        testThread[] testThreads = new testThread[numThreads];

        // Εκκίνηση κάθε thread
        for (int i = 0; i < numThreads; i++) {
            testThreads[i] = new testThread(i, testBarrier);
            testThreads[i].start();  // Εκκίνηση κάθε thread
        }

        // Περιμένουμε να ολοκληρωθούν όλα τα threads (το κύριο thread περιμένει)
        for (int i = 0; i < numThreads; i++) {
            try {
                testThreads[i].join();  // Περιμένει κάθε thread να τελειώσει
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Τελική ώρα μετά την εκτέλεση
        long endTime = System.currentTimeMillis();

        // Υπολογισμός και εκτύπωση του χρόνου εκτέλεσης
        long elapsedTime = endTime - startTime;
        System.out.println("Execution time: " + elapsedTime + " milliseconds");
    }
}
