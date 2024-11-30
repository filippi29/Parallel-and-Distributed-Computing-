package server;
import utils.CalculatePi;

import java.rmi.*;
import java.rmi.server.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// H klash ayth ylopoiei thn apomakrysmenh diepafh Factorial.
public class CalculatePiImpl extends UnicastRemoteObject implements CalculatePi {
	
	private static final long serialVersionUID = 1;

	Lock lock = new ReentrantLock();

	// Kataskeyasths.
	public CalculatePiImpl() throws RemoteException {
		anum = new HashMap<Long, Double>();
	}
	
	// Kodikas ylopoihshs ths apomakrysmenhs methodoy.
//	public int fact(int number) throws RemoteException {
//		int result = 1;
//		for(int i = 1; i <= number; i++) {
//			result *= i;
//		}
//		return result;
//	}

	private static Map<Long, Double> anum;  //χρήση δομής HashMap


	public double pi(long num) {  //υπολογισμός του pi
		double sum = 0.0;

		double step = 1.0 / (double) num;
		/* do computation */
		for (long i = 0; i < num; ++i) {
			double x = ((double) i + 0.5) * step;
			sum += 4.0 / (1.0 + x * x);
		}
		double pi = sum * step;

		return pi;
	}

	@Override
	public double pi2(long num) throws RemoteException {
		double result;
		lock.lock();
		try {
			if (anum.containsKey(num)) {
				System.out.println("already used");
				result  = anum.get(num);
			} else {
				double temp = pi(num);
				anum.put(num, temp);  //εισχωρεί στη δομή μας τον τρέχον αριθμό και το pi του
				result =  temp;
			}
			// access to the shared resource
		} finally {
			lock.unlock();
		}
		return result;
	}
}
