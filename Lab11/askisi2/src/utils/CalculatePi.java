package utils;
import java.rmi.*;

public interface CalculatePi extends Remote {

    // Ypografh ths apomakrysmenhs methodoy.
    public double pi2(long number) throws RemoteException;
}