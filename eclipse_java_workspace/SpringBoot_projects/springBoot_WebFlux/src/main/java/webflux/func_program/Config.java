package webflux.func_program;

import java.lang.reflect.Method;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
//函数式编程
@Configuration
public class Config {
    @Bean
    @Autowired
    //RouterFunction类型的Bean会注册URL地址
    public RouterFunction<ServerResponse>routerFunction(final CalculatorHandler calculatorHandler) {
        return RouterFunctions.route(
        		RequestPredicates.path("/calculator"), 
        		request ->
                		request.queryParam("operator").map
                			(
                				operator ->
		                			Mono.justOrEmpty(ReflectionUtils.findMethod(CalculatorHandler.class, operator, ServerRequest.class))
		                        	//.flatMap(method ->  (Mono<ServerResponse>) ReflectionUtils.invokeMethod(method, calculatorHandler, request))
			                        .flatMap(new Function<Method,Mono<ServerResponse>>()
			                        {
			                        	 public Mono<ServerResponse> apply(Method method)
			                        	 {   //如果前面的方法存在（justOrEmpty做的），调用这里，否则不调用这里
			                        		 return (Mono<ServerResponse>) ReflectionUtils.invokeMethod(method, calculatorHandler, request);
			                        	 }
			                        }) 
		                        .switchIfEmpty(ServerResponse.noContent().build())//operator参数值错误(如不是add)
		                        .onErrorResume(ex -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build())//???
                           ).orElse(ServerResponse.status(HttpStatus.NOT_ACCEPTABLE) .build() ) //没有传operator参数
	        );
    }
}