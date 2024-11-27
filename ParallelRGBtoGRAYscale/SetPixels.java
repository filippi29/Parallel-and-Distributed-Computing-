import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;
import javax.imageio.ImageIO;

public class SetPixels {

    static class SetPixelsTask extends RecursiveAction {
    	
        private static final int THRESHOLD = 10000; // Σταθερά που ορίζει το όριο κατώτατου αριθμού pixels για παραλληλισμό
        
        private BufferedImage img;
        private int myStart;
        private int myStop;
        
        private int redShift;
        private int greenShift;
        private int blueShift;

        public SetPixelsTask(BufferedImage img, int start, int end, int redShift, int greenShift, int blueShift) {
            this.img = img;
            this.myStart = start;
            this.myStop = end;
            this.redShift = redShift;
            this.greenShift = greenShift;
            this.blueShift = blueShift;
        }

        
        protected void compute() { //μέθοδος που εκτελεί.
            if (myStop - myStart <= THRESHOLD) { // εκτέλεση της μετατροπής κατευθείαν
                modifyPixels(myStart, myStop);
            } 
            else {
                int mid = myStart + (myStop - myStart) / 2;  // τμηματοποίηση της εικόνας σε μικρότερα κομμάτια
                invokeAll(new SetPixelsTask(img, myStart, mid, redShift, greenShift, blueShift),
                          new SetPixelsTask(img, mid, myStop, redShift, greenShift, blueShift));
            }
        }

        private void modifyPixels(int start, int end) {
            for (int y = start; y < end; y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    int pixel = img.getRGB(x, y);
                    Color color = new Color(pixel, true);
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    red = (red + redShift) % 256;
                    green = (green + greenShift) % 256;
                    blue = (blue + blueShift) % 256;
                    Color newColor = new Color(red, green, blue);
                    img.setRGB(x, y, newColor.getRGB());
                }
            }
        }
    }
    

    public static void main(String[] args) {
    	
        if (args.length > 4) {
            System.out.println("Usage: java ParallelSetPixels  <file to read > <file to write>");
            return;
        }

        String inputFileName = "C:\\eclipse\\Pixels\\src\\original2.jpg";
        String outputFileName =  "C:\\eclipse\\Pixels\\src\\new2.jpg";
        
        int redShift = 100;
        int greenShift = 100;
        int blueShift = 100;

        
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(inputFileName));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        ForkJoinPool pool = new ForkJoinPool();
        
        long startTime = System.currentTimeMillis();
        
        pool.invoke(new SetPixelsTask(img, 0, img.getHeight(), redShift, greenShift, blueShift));
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("Image processing completed in: " + (endTime - startTime) + " ms.");

        try {
            File outputFile = new File(outputFileName);
            ImageIO.write(img, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

