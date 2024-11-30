package server;
import utils.Calc;

import java.rmi.*;
import java.rmi.server.*;

public class CalcImpl extends UnicastRemoteObject implements Calc {
	
	private static final long serialVersionUID = 1;

	public CalcImpl() throws RemoteException {
	}

	@Override
	public int calc(String opcode, int num1, int num2) throws RemoteException {


		switch (opcode){
			case "+":
				return num1 + num2;
			case "-":
				return num1 - num2;
			case "*":
				return num1 * num2;
			case "/":
				return num1 / num2;
		}
		return 0;

	}
}
