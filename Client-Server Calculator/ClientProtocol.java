import java.net.*;
import java.io.*;

public class ClientProtocol {

    BufferedReader user = new BufferedReader(new InputStreamReader(System.in));


    //pleon den einai String, alla Request giati exoume Objects
    public Request prepareRequest() throws IOException {
        boolean done = false;
        String theOutput = " ";
        while (!done) {
            System.out.print("Enter message to send to server:");
            System.out.print("example + 3 4 ");
            String data = user.readLine();
            done = checkData(data);       //Μέθοδος που ελέγχει αν είναι αποδεκτό το input
            theOutput = data;
        }

        //xorizoume to Output
        String[] temp = theOutput.split(" ");
        String opCode = temp[0];
        int num1 = Integer.parseInt(temp[1]);
        int num2 = Integer.parseInt(temp[2]);

        //kai ta stelnoume sto Request
        return new Request(opCode, num1, num2);
    }

    private boolean checkData(String data) {
        String[] tokens = data.split(" ");
        if (tokens.length != 3) {
            return false;
        }
        String first = tokens[0];
        if (!(first.equals("+") || first.equals("-") || first.equals("*") || first.equals("/"))) {
            return false;
        }
        String second = tokens[1];
        if (!isNumeric(second)) {
            return false;
        }
        String third = tokens[2];
        if (!isNumeric(third)) {
            return false;
        }
        return true;
    }

    public static boolean isNumeric(String strNum) {     //συνάρτηση που ελέγχει αν είναι αριθμός
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void processReply(Reply theInput) throws IOException {
        System.out.println("Message received from server: " + theInput.getValue());
    }
}
