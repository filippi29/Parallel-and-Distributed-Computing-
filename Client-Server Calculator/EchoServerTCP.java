import java.net.*;
import java.io.*;

public class EchoServerTCP {
	private static final int PORT = 1234;


	public static void main(String args[]) throws IOException {

		ServerSocket connectionSocket = new ServerSocket(PORT);
		System.out.println("Server is listening to port: " + PORT);

		while (true) {

			System.out.println("Server is listening to port: " + PORT);
			Socket dataSocket = connectionSocket.accept();
			System.out.println("Received request from " + dataSocket.getInetAddress());

			ServerThread sthread = new ServerThread(dataSocket);
			sthread.start();
		}

		


		
	}
}			

