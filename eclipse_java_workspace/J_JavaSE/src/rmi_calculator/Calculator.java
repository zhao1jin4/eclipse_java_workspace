package rmi_calculator;

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface Calculator extends Remote  
//要Remote接口,在Server端和Client端必须是相同的包名,方法抛RemoteException
{
	public long add(long a, long b) throws RemoteException;
	public long sub(long a, long b) throws RemoteException;
	public long mul(long a, long b) throws RemoteException;
	public long div(long a, long b) throws RemoteException;
}