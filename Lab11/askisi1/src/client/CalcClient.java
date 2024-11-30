package client;
import utils.Calc;

import java.rmi.*;
import java.rmi.registry.*;

public class CalcClient {
	
	private static final String HOST = "localhost";
	private static final int PORT = Registry.REGISTRY_PORT; // 1099
	
	public static void main(String[] args) {
				
		try {
			// Locate rmi registry
			
			Registry registry = LocateRegistry.getRegistry(HOST, PORT);
			
			// Look up for a soecific name and get remote reference (stub)
			String rmiObjectName = "MyAddition";
			Calc ref = (Calc)registry.lookup(rmiObjectName);
			
			// Do remote method invocation
			int result = ref.calc("+",5, 6);
			System.out.println("The adding 5 and 6 is " + result);
		} catch (RemoteException re) {
			System.out.println("Remote Exception");
			re.printStackTrace();
		} catch (Exception e) {
			System.out.println("Other Exception");
			e.printStackTrace();
		}
	}
}

