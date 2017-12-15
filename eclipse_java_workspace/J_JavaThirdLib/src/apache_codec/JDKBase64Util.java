package apache_codec;

import java.util.Base64;

public class JDKBase64Util
{
	public static byte[] base64Decode(byte[] input)
	{
		return Base64.getDecoder().decode(input);
	}
	public static byte[] base64Encode(byte[] input)
	{ 
		return Base64.getEncoder().encode(input);
	}
}
