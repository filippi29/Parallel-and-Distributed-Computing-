import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class ParallelRGBtoGrayScale {
    
    static class RGBtoGrayScaleTask extends RecursiveAction {
    	
        private static final int THRESHOLD = 10000; // ������� ��� ������ �� ���� ��������� ������� pixels ��� ������������
        private BufferedImage img;
        private int myStart;
        private int myStop;

        public RGBtoGrayScaleTask(BufferedImage img, int start, int end) {
            this.img = img;
            this.myStart = start;
            this.myStop = end;
        }

       
        protected void compute() {   //������� ��� �������.
            if (myStop - myStart <= THRESHOLD) {  // �������� ��� ���������� ����������
                convertRGBtoGrayScale(myStart, myStop);
            }
            else {
                int mid = myStart + (myStop - myStart) / 2;  // ������������� ��� ������� �� ��������� ��������
                invokeAll(new RGBtoGrayScaleTask(img, myStart, mid),  // ��������� ��� �������� ��� ������������ ��� ��������� �� ������������ ���� �� ����������� ��� ����� ������ ���� ����� 
                          new RGBtoGrayScaleTask(img, mid, myStop));
            }
        }

        private void convertRGBtoGrayScale(int start, int end) {
            for (int y = start; y < end; y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    int pixel = img.getRGB(x, y);
                    Color color = new Color(pixel, true);
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    int gray = (red + green + blue) / 3;
                    Color grayColor = new Color(gray, gray, gray);
                    img.setRGB(x, y, grayColor.getRGB());
                }
            }
        }
    }

    public static void main(String[] args) {
        String fileNameR = "C:\\eclipse\\Pixels\\src\\original.jpg";
        String fileNameW = "C:\\eclipse\\Pixels\\src\\new.jpg";
        
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileNameR));
        } catch (IOException e) {
            e.printStackTrace();
        }

        long start = System.currentTimeMillis(); 
        
        
        // ����� ������ ForkJoinPool ��� ���������� ���� pool ������� ��� ��� ������������
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new RGBtoGrayScaleTask(img, 0, img.getHeight())); //�������� ��� pool

        // Stop timing
        long elapsedTimeMillis = System.currentTimeMillis() - start;

        //Saving the modified image to Output file
        try {
            File file = new File(fileNameW);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
      
        System.out.println("Done...");
        System.out.println("Time in ms = " + elapsedTimeMillis);
    }
}

