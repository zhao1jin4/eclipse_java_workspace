import java.util.Base64;

public class CookieDecode {

	public static void main(String[] args) {
		String cookieBase64EncStr="dXNlcjoxNTQ0MDgxMjMyODAwOjRjNjkwODc0ZTE3NjIzMWJkMzM1ZGYwZGY4NWE5NjE1";
		
			
			Base64.Decoder base64Decoder=Base64.getDecoder();
			byte[] decoded=base64Decoder.decode(cookieBase64EncStr);
			System.out.println(new String(decoded));

	}

}
