package config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Objects;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;

import feign.Response;
import feign.codec.Decoder;

//打印 openfeign 返回json日志,如服务端返回gzip做解码
public final class MyRestfulLogGzipDecoder implements Decoder { 
    final Decoder delegate;

    public MyRestfulLogGzipDecoder(Decoder delegate) {
        Objects.requireNonNull(delegate, "Decoder must not be null. ");
        this.delegate = delegate;
    }


	/* 
	feign.compression.response.enabled=true 如加了这个配置，可不用实现Decoder做gzip解码,但要求feign接口修改返回类型为ResponseEntity<byte[]>
	feign.compression.response.useGzipDecoder=true
	*/
    @Override
    public Object decode(Response response, Type type) throws IOException {
    	String resultStr = null;
        Collection<String> encodingStrings = (Collection)response.headers().get("content-encoding"); 
        if (null != encodingStrings && encodingStrings.contains("gzip")) {//对于部署到k8s上的情况 
            resultStr = IOUtils.toString(new GZIPInputStream(response.body().asInputStream()), "utf-8");
        }else { 
            resultStr = IOUtils.toString(response.body().asInputStream(), "utf-8");
        }
      // 回写body,因为response的流数据只能读一次，这里回写后重新生成response 
        System.out.println("====openFeign response json :"+resultStr);
        return delegate.decode(response.toBuilder().body(resultStr, StandardCharsets.UTF_8).build(), type);
      
    }
}



