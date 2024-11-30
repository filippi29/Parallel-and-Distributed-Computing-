import java.net.*;
import java.io.*;

public class ServerProtocol {

    public Reply processRequest(Request theInput) {


        return doServerComputation(theInput);
//		System.out.println("Received message from client: " + theInput);
//		String theOutput = theInput;
//		System.out.println("Send message to client: " + theOutput);
//		return theOutput;
    }

    public Reply doServerComputation(Request data) {


        Reply result = new Reply();   //afto pou epistrefoume einai pleon typou Reply oxi String

//        int a = Integer.parseInt(tokens[1]);
//        int b = Integer.parseInt(tokens[2]);  exoume to num1,num2


        switch (data.getOpcode()){
            case "+":
                result.setOpcode("ok");
				result.setValue(data.getFirst() + data.getSecond());   // i getFirst epistrefei to num1 kai i getSecond to num2 (einai sto Request)
                break;
            case "-":
                result.setOpcode("ok");
                result.setValue(data.getFirst() - data.getSecond());
                break;
            case "*":
                result.setOpcode("ok");
                result.setValue(data.getFirst() * data.getSecond());
                break;
            case "/":
                result.setOpcode("ok");
                result.setValue(data.getFirst() / data.getSecond());
                break;
        }
        return result;
    }
}