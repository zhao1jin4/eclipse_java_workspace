package basic.enum_;

import java.util.EnumMap;
import java.util.EnumSet;


public class EnumTest
{

	public static void main(String[] args)
	{
		
		for( Enum color:ColorEnum.values())
		{
			
			System.out.println(color.ordinal() + color.name());
		}
		
		EnumSet<ColorEnum>  colorSet=EnumSet.noneOf(ColorEnum.class);//�յ�
		colorSet=EnumSet.allOf(ColorEnum.class);//ȫ������
		
		Enum red=ColorEnum.RED;
		for( Enum color:red.getClass().getEnumConstants())
		{
			System.out.println(color.ordinal() + color.name());
		}
		
		colorSet.removeAll(EnumSet.of(ColorEnum.RED)); 
		colorSet.removeAll(EnumSet.range(ColorEnum.BLUE, ColorEnum.GREEN));
		
		colorSet.addAll(EnumSet.of(ColorEnum.BLUE));//��Ԫ��
	
		System.out.println( EnumSet.complementOf(colorSet) ); //complement���� ,  ���˸�����Ԫ���������Ԫ��
		
		
		
		EnumMap<ColorEnum,String> map =new EnumMap<>(ColorEnum.class);
		map.put(ColorEnum.RED, "255");
		
	}
	
}


enum ColorEnum
{
	RED,BLUE,GREEN,WHITE
}