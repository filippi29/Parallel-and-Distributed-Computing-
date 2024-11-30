package utils;
import java.rmi.*;

public interface Calc extends Remote {
	
	// Ypografh ths apomakrysmenhs methodoy.
	public int calc(String opcode, int num1, int num2) throws RemoteException;
}
