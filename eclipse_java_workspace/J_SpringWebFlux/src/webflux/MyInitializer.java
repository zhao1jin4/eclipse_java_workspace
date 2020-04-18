package webflux;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServletHttpHandlerAdapter;
import org.springframework.web.server.adapter.AbstractReactiveWebInitializer;
public class MyInitializer extends AbstractReactiveWebInitializer {
	@Override
	protected Class<?>[] getConfigClasses() {
		return new Class[] {WebConfig.class};
	}
	HttpHandler x;
	ServletHttpHandlerAdapter y;
}
