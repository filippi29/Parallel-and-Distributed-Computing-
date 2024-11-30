package client;
import utils.CalculatePi;

import java.rmi.*;
import java.rmi.registry.*;

/*
 * Execute:
 * java FactorialClient
 */
public class PiClient {
	
	private static final String HOST = "localhost";
	private static final int PORT = Registry.REGISTRY_PORT; // 1099
	
	public static void main(String[] args) {
				
		try {
			// Locate rmi registry
			
			Registry registry = LocateRegistry.getRegistry(HOST, PORT);
			
			// Look up for a soecific name and get remote reference (stub)
			String rmiObjectName = "Factorial";
			CalculatePi ref = (CalculatePi)registry.lookup(rmiObjectName);
			
			// Do remote method invocation
			double result = ref.pi2(10000);
			System.out.println("The pi of number 10000 is " + result);
		} catch (RemoteException re) {
			System.out.println("Remote Exception");
			re.printStackTrace();
		} catch (Exception e) {
			System.out.println("Other Exception");
			e.printStackTrace();
		}
	}
}

