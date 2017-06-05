package util;

public class DateUtil 
{
	 public static boolean validateDateTimeStr(String dataTimeStr)
	 {
		 if(dataTimeStr.length() != "yyyyMMddHHmmss".length())
	    		return false;
	    			
		   try{
			   int year= Integer.parseInt(dataTimeStr.substring(0,4));
			   int month= Integer.parseInt(dataTimeStr.substring(5,7));
			   int day= Integer.parseInt(dataTimeStr.substring(7,9));
			   if(year<1900 || year>2200 || month<1 || month>12 || day <1 || day >31) 
			   {
				   return false;
			   }
			   int hour= Integer.parseInt(dataTimeStr.substring(9,11));
			   int minute= Integer.parseInt(dataTimeStr.substring(11,13));
			   int second= Integer.parseInt(dataTimeStr.substring(13,15));
			   if(hour<0 || hour>23 || minute<0 || minute>59 || second <0 || second >59)
			   {
				   return false;
			   }
		   }catch(Exception e)
	       {
			   return false;
	       }
		   return true;
	}
}
