package rmi_calculator.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import rmi_calculator.Calculator;


public class CalculatorImpl extends UnicastRemoteObject implements Calculator 
//要extends UnicastRemoteObject 和那个接口extends Remote
{
	private static final long serialVersionUID = 1L;

	public CalculatorImpl() throws java.rmi.RemoteException {
		super();
	}

	public long add(long a, long b) throws RemoteException {
		return a + b;
	}

	public long sub(long a, long b) throws RemoteException {
		return a - b;
	}

	public long mul(long a, long b) throws RemoteException {
		return a * b;
	}

	public long div(long a, long b) throws RemoteException {
		return a / b;
	}
}