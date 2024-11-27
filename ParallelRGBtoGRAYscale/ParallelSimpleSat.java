import java.awt.image.BufferedImage;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelSimpleSat {
    
    static class SimpleSatTask extends RecursiveAction {
    	
        private static final int THRESHOLD = 1000; // Σταθερά που ορίζει το όριο κατώτατου αριθμού pixels για παραλληλισμό
        private BufferedImage img;
        
        private int size;
        private int start;
        private int end;

        public SimpleSatTask(int size, int start, int end) {
            this.size = size;
            this.start = start;
            this.end = end;
        }

       
        protected void compute() {
            if (end - start <= THRESHOLD) {
                checkCircuit(start, end);
            } else {
                int middle = start + (end - start) / 2;
                invokeAll(new SimpleSatTask(size, start, middle), 
                          new SimpleSatTask(size, middle, end));
            }
        }

        private void checkCircuit(int start, int end) {
        	
            for (int z = start; z < end; z++) {
                boolean[] v = new boolean[size];
                for (int i = size - 1; i >= 0; i--) 
                    v[i] = (z & (1 << i)) != 0;
                
             // Check the ouptut of the circuit for input v
                boolean value = 
                    (v[0] || v[1]) &&
                    (!v[1] || !v[3]) &&
                    (v[2] || v[3]) &&
                    (!v[3] || !v[4]) &&
                    (v[4] || !v[5]) &&
                    (v[5] || !v[6]) &&
                    (v[5] || v[6]) &&
                    (v[6] || !v[15]) &&
                    (v[7] || !v[8]) &&
                    (!v[7] || !v[13]) &&
                    (v[8] || v[9]) &&
                    (v[8] || !v[9]) &&
                    (!v[9] || !v[10]) &&
                    (v[9] || v[11]) &&
                    (v[10] || v[11]) &&
                    (v[12] || v[13]) &&
                    (v[13] || !v[14]) &&
                    (v[14] || v[15]) &&
                    (v[14] || v[16]) &&
                    (v[17] || v[1]) &&
                    (v[18] || !v[0]) &&
                    (v[19] || v[1]) &&
                    (v[19] || !v[18]) &&
                    (!v[19] || !v[9]) &&
                    (v[0] || v[17]) &&
                    (!v[1] || v[20]) &&
                    (!v[21] || v[20]) &&
                    (!v[22] || v[20]) &&
                    (!v[21] || !v[20]) &&
                    (v[22] || !v[20]);

                if (value) {
                    printResult(v, z);
                }
            }
        }

        private void printResult(boolean[] v, int z) {
            StringBuilder result = new StringBuilder();
            result.append(z);
            for (int i = 0; i < size; i++) {
                result.append(v[i] ? " 1" : " 0");
            }
            System.out.println(result.toString());
        }
    }

    public static void main(String[] args) {
    	
    	// Circuit input size (number of bits)
        int size = 28;
        
        // Number of possible inputs (bit combinations)
        int iterations = (int) Math.pow(2, size);
        
        // Start timing
        long start = System.currentTimeMillis();

        // Check all possible inputs
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new SimpleSatTask(size, 0, iterations));

     // Stop timing 
        long elapsedTimeMillis = System.currentTimeMillis() - start;
        
        System.out.println("\nAll done\n");
        System.out.println("Time in ms = " + elapsedTimeMillis);
    }
}

