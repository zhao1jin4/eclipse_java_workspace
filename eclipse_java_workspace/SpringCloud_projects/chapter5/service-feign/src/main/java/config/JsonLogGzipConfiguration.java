package config;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.codec.Decoder;
import feign.optionals.OptionalDecoder;
//也是不能放在@SpringBootApplication包下

@Configuration
public class JsonLogGzipConfiguration  
{
	//打印 openfeign 返回json日志
	@Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;
	
	@Bean
    public Decoder feignDecoder() {
        return new MyRestfulLogGzipDecoder(new OptionalDecoder(new ResponseEntityDecoder(new SpringDecoder(this.messageConverters))));
    }
    
}
