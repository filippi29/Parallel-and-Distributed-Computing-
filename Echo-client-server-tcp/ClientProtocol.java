import java.io.*;

public class ClientProtocol {

    BufferedReader user = new BufferedReader(new InputStreamReader(System.in));

    public String prepareRequest() throws IOException {
        System.out.println("Pick a number:");
        System.out.println(" 1. metatropi apo kefalaia se peza");
        System.out.println(" 2. metatropi apo peza se kefalaia");
        System.out.println(" 3. metatropi se ASCII");
        System.out.println(" 4. metatropi apo ASCII");
        System.out.println(" 5. mirrored message");
        System.out.println(" 6. get current date and time");
        System.out.println( "7. counts times that a letter appears in the text");

        System.out.println("Enter message to send to server:");
        String theOutput = user.readLine();
        return theOutput;
    }

    public void processReply(String theInput) throws IOException {

        System.out.println("Message received from server: " + theInput);
    }
}
