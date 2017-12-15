package rmi_calculator.client;

import java.rmi.Naming;

import rmi_calculator.Calculator;
public class CalculatorClient 
{
	public static void main(String[] args)
	{
		try {
			Calculator c = (Calculator) Naming.lookup("rmi://localhost:1099/CalculatorService");
			System.out.println(c.sub(100, 99));
			System.out.println(c.add(3, 16));
			System.out.println(c.mul(2, 10));
			System.out.println(c.div(20, 2));
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}