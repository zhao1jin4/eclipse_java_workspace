package jdknew;



class In implements OutInterface.InInterface
{
	@Override
	public void inMethod() {
		System.out.println("inMethod");
		
	}
}
class Out implements OutInterface
{
	@Override
	public void outMethod() {
		System.out.println("outMethod");
	}
}

public class InterfaceTest {
	
	public static void main(String[] args) 
	{
		Out out1=new Out();
		out1.outMethod(); 
		 //out1.InClass.CHAR_SET;//∑¿Œ ≤ªµΩ
		 
		 new In().inMethod();
		 new ChildInInterface(){
			@Override
			public void inMethod() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void childIn() {
				System.out.println("childIn");
				
			}
		 }.childIn();
		 
		 System.out.println(OutInterface.InClass.CHAR_SET);
		 
	}

}
