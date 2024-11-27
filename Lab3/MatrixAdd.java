/* Matrix Addition A = B + C                       */
/* ics21060                                        */
/* Lab3, exercise 1                                */


class SqrtThread extends Thread {
    private double[][] matrix; //matrix =  a[] (πίνακας)
    private int myStart;
    private int myStop;

    public SqrtThread(double[][] matrix, int startRow, int endRow) {
        this.matrix = matrix;
        this.myStart = startRow;
        this.myStop = endRow;
    }

    public void run() { // based on code SqrtGroupThread
        for (int i = myStart; i < myStop; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = Math.sqrt(matrix[i][j]); //ρίζα των στοχείων του πίνακα
            }
        }
    }
}

public class MatrixAdd {
	
    public static void main(String[] args) {
        int size = 10;
        int numThreads = Runtime.getRuntime().availableProcessors();

        double[][] a = new double[size][size];
        double[][] b = new double[size][size];
        double[][] c = new double[size][size];

        // Initialize matrices a, b, and c
        /* for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                a[i][j] = i * size + j; //  κάθε στοιχείο παίρνει ως τιμή τον αροθμό θέσης του στοιχείου στον πίνακα
            }
        } */
        
        
        for(int i = 0; i < size; i++) 
            for(int j = 0; j < size; j++) { 
              a[i][j] = 0.1;
      		  b[i][j] = 0.3;
              c[i][j] = 0.5;
            }   
          
          for(int i = 0; i < size; i++) 
            for(int j = 0; j < size; j++)  
              a[i][j] = b[i][j] + c[i][j];

        
        
     //for debugging
        System.out.println("matrix a:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("That was matrix a!\n");

        
        // Create threads
        SqrtThread threads[] = new SqrtThread[numThreads];
        
        
        // Thread execution   
        for (int i = 0; i < numThreads; i++) {
            int startRow = i * (size / numThreads); //based on the constructor of code SqrtGroupThread
            int endRow = startRow + (size/numThreads);
            if (i == (numThreads - 1)) {
            	endRow = size;
            }
            
            
            threads[i] = new SqrtThread(a, startRow, endRow);
            threads[i].start();
        }

        // Wait for threads to terminate            
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //for debugging
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }
}
