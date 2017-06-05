package apache_codec;

import org.apache.commons.codec.binary.Base64;

public class ApacheBase64Util
{
	public static byte[] base64Encode(byte[] data)
	{
		Base64 base64 = new Base64();
		return base64.encode(data);
	}

	public static byte[] base64Decode(byte[] data)
	{
		Base64 base64 = new Base64();
		return base64.decode(data);
	}
	public static byte[] base64DecodeStatic(byte[] data)
	{
		return Base64.decodeBase64(data);
	}
	
}
