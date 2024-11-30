import java.net.*;
import java.io.*;
public class EchoClientTCP {
	private static final String HOST = "localhost";
	private static final int PORT = 1234;
	private static final String EXIT = "CLOSE";

	public static void main(String args[]) throws IOException, ClassNotFoundException {

		Socket dataSocket = new Socket(HOST, PORT);

		ObjectOutputStream clientOutputStream = new
				ObjectOutputStream(dataSocket.getOutputStream());
		ObjectInputStream clientInputStream = new
				ObjectInputStream(dataSocket.getInputStream());
		       	
		System.out.println("Connection to " + HOST + " established");


		ClientProtocol app = new ClientProtocol();
		
		Request outmsg = app.prepareRequest();

		while(!outmsg.getOpcode().equals("ERR")) {
			clientOutputStream.writeObject(outmsg);

			Reply rep = (Reply)clientInputStream.readObject();

			app.processReply(rep);
			outmsg = app.prepareRequest();
		}

		dataSocket.close();
		System.out.println("Data Socket closed");

	}
}			

