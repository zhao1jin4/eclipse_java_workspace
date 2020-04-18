package util;
/**
 * 罗马数字转换为十进制数字，最大表示到3999
 * 自己开发版本
 */
public class Roman2IntMy 
{
	/** 罗马数字枚举类 */
	enum RomanEnum
	{
		I(1,true),
		V(5,false),
		X(10,true),
		L(50,false),
		C(100,true),
		D(500,false),
		M(1000,true),//最大值 是否可被减都可，但可重复
		;
		/** 十进制数 */
		private int num;
		/** 是否重复，是否可被减 */
		private boolean  repeat;
		private RomanEnum(int num, boolean repeat) {
			this.num = num;
			this.repeat = repeat;
		}
		/** 检查罗马字符是否有效，除了 【 1开头的只能被后两位去减  】*/
		public static boolean checkValidate(String str)
		{
			if(str==null || str.length()==0)
				return false;
			char lastChar='\0';
			int repeatTimes=1;
			for(char ch:str.toCharArray())
			{
				RomanEnum  chItem=RomanEnum.valueOf(ch+"");
				boolean isExists=false;
				for(RomanEnum item : RomanEnum.values())
				{
					if(item == chItem)
					{
						isExists=true;
						break;
					}
				}
				if(!isExists)
				{
					System.err.println(ch+" is not available roman char");
					return false;
				}
				if(repeatTimes>1 && !chItem.repeat )
				{
					System.err.println(ch+" can not repeat");
					return false;
				}
				if(ch==lastChar)
					repeatTimes++;
				if(repeatTimes>3)
				{
					System.err.println(ch+" repeat times > 3");
					return false;
				}
				lastChar=ch;
			}
			return true;
		}
	}
	/**  罗马字符转换为十进制数,验证 【 1开头的只能被后两位去减  】*/
	public static int convertRoman2Int(String romanStr)
	{
		if(!RomanEnum.checkValidate(romanStr))
			throw new NumberFormatException("roman value "+romanStr+" format not correct ");
		
		RomanEnum preRoman=null;
		int sum=0;
		int len=romanStr.length();
		for(int i=0;i<len;i++)
		{
			RomanEnum roman=RomanEnum.valueOf(romanStr.charAt(i)+"");
			if(preRoman==null)
			{
				preRoman = roman;
				if(i == len-1)
					sum += roman.num;
			}else if(preRoman.ordinal() >= roman.ordinal())
			{
				sum+=preRoman.num;
				preRoman = roman;
				if(i == len-1)
					sum += roman.num;
			}else
			{
				if(roman.ordinal()-preRoman.ordinal()>2)
					throw new NumberFormatException("roman value "+romanStr+" format not correct");
				sum += (roman.num-preRoman.num);
				preRoman=null; 
			}
		}
		
		return sum;
	}
	public static void main(String args[]) {
		System.out.println(RomanEnum.checkValidate("MCMIIII"));  
//		System.out.println(covertRoman2Int("MCMIII")); // 1903 = MCMIII.
//		System.out.println(covertRoman2Int("DCXXI"));//621
//		System.out.println(covertRoman2Int("MXC"));//1090
//		System.out.println(covertRoman2Int("DCCCXC"));//890
//		System.out.println(covertRoman2Int("XCI"));//91 

		System.out.println(covertRoman2Int("XCM"));//  M在最后会   怎么办???，报错？？？ 最大的M不可被减吗？？？
		
		System.out.println(covertRoman2Int("IXC"));//  C怎么办???，报错？？？ 
		System.out.println(covertRoman2Int("CIX"));//109
	}
}
