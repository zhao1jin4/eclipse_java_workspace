package jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

public class JsonWebTokenMain {
	public static void main(String[] args) {

// We need a signing key, so we'll create one just for this example. Usually
// the key would be read from your application configuration instead.
		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);// 两次调用产生不同的key,可以使用私钥

		String jws = Jwts.builder().setSubject("Joe").signWith(key).compact();

		String res=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getBody().getSubject();// Joe
		System.out.println(res);
		
		try {

		    Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws);
		    //OK, we can trust this JWT

		} catch (JwtException e) {
		    //don't trust the JWT!
		}
		
	}
}
