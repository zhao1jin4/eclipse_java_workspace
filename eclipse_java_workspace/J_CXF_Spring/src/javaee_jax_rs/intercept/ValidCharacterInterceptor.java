package javaee_jax_rs.intercept;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;

public class ValidCharacterInterceptor implements ReaderInterceptor
{
    @Override
    public Object aroundReadFrom(ReaderInterceptorContext readerInterceptorContext) throws IOException, WebApplicationException {
        final InputStream originalInputStream = readerInterceptorContext.getInputStream();
        readerInterceptorContext.setInputStream(new InputStream() 
        {

            @Override
            public int read() throws IOException {
                boolean isOk;
                int b;
                do {
                    b = originalInputStream.read();
                    isOk = b == -1 || Character.isLetterOrDigit(b) || Character.isWhitespace(b) || b == ((int) '.');
                } while (!isOk);

                return b;
            }
        });
        try {
            return readerInterceptorContext.proceed();
        } finally {
            readerInterceptorContext.setInputStream(originalInputStream);
        }
    }
}
