package gateway;

import java.time.ZonedDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

// tag::code[]
@SpringBootApplication
@EnableConfigurationProperties(UriConfiguration.class)
@RestController
public class Application { 
	
	// 请求 http://127.0.0.1:9999/get 转到  http://httpbin.org/get 返回的数据有Hello:world
	//curl --dump-header - --header 'Host:www.hystrix.com' http://localhost:9999/delay/3    结果显示fallback
    public static void main(String[] args) {
        
    	SpringApplication.run(Application.class, args);
    	
    	ZonedDateTime m=java.time.ZonedDateTime.parse("2017-01-20T17:42:47.789+08:00[Asia/Shanghai]");
		System.out.println(m);
		
		org.springframework.cloud.gateway.handler.predicate.AfterRoutePredicateFactory afterRoute;
		org.springframework.cloud.gateway.filter.factory.AddRequestHeaderGatewayFilterFactory addRequest;
		org.springframework.cloud.gateway.filter.factory.RewritePathGatewayFilterFactory rewritePath;
		
		//global filter
		org.springframework.cloud.gateway.filter.LoadBalancerClientFilter loadBalancer;
    
		//限流
		org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory ratelimit;
    }
/*
    //编码方式
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
        String httpUri = uriConfiguration.getHttpbin();
        return builder.routes()
            .route(p -> p
                .path("/get")
                .filters(f -> f.addRequestHeader("Hello", "World"))
                .uri(httpUri))
            .route(p -> p
                .host("*.hystrix.com")
                .filters(f -> f
                    .hystrix(config -> config
                        .setName("mycmd")
                        .setFallbackUri("forward:/fallback")))
                .uri(httpUri))
            .build();
    }

    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }
 */
    
    @Bean  //自定义 Filter 要编码
    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r ->
                		 r.host("*.hystrix.com") //相当于 predicates 测试用 curl --dump-header - --header 'Host:www.hystrix.com' http://localhost:9999/get 
                		//r.path("/customer/**") //相当于 predicates 测试用 curl localhost:9999/customer/123  会请求到http://httpbin.org/customer/123肯定不存在，但日志有了
                        .filters(f -> f.filter(new RequestTimeFilter())//自己的filter
                                .addResponseHeader("X-Response-Default-Foo", "Default-Bar"))
                        .uri("http://httpbin.org:80")//可以用lb://
                        .order(0)
                        .id("customer_filter_router")
                )
                .build();
    }

    /*
     	自定义filterFactory   可用配置文件
	    filters:
	    - RequestTime=false
     */
    @Bean
    public RequestTimeGatewayFilterFactory elapsedGatewayFilterFactory() {
        return new RequestTimeGatewayFilterFactory();
    }
   
/*
    //自定义 GlobalFilter 不需要在配置文件中配置，作用在所有的路由上
    //curl --dump-header - localhost:9999/get
	@Bean
	public TokenFilter tokenFilter(){
	        return new TokenFilter();
	}
 */

   /* redis 限流 未试？？？
    Redis和lua脚本实现了令牌桶的方式
    spring-cloud-gateway-core-2.0.2.RELEASE.jar/META-INF/scripts/request_rate_limiter.lua 
    pom.xml中加 <artifactId>spring-boot-starter-data-redis-reactive</artifactId>
   jmeter 进行压测，配置10 thread去循环请求 localhost:9999/get 循环间隔1s
   */
   //根据Hostname进行限流
   @Bean
   public HostAddrKeyResolver hostAddrKeyResolver() {
       return new HostAddrKeyResolver();
   }
   //根据uri去限流
   @Bean
  public UriKeyResolver uriKeyResolver() {
      return new UriKeyResolver();
  }
   //用户的维度去限流
   @Bean
   KeyResolver userKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));
    }




   

}

@ConfigurationProperties
class UriConfiguration {
    
    private String httpbin = "http://httpbin.org:80";

    public String getHttpbin() {
        return httpbin;
    }

    public void setHttpbin(String httpbin) {
        this.httpbin = httpbin;
    }
}
