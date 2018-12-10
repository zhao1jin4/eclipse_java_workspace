package reactor_project;

//import static reactor.event.selector.Selectors.$;
 

/*


import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.composable.Deferred;
import reactor.core.composable.Promise;
import reactor.core.composable.Stream;
import reactor.core.composable.spec.Promises;
import reactor.core.composable.spec.Streams;
import reactor.core.spec.Reactors;
import reactor.event.Event;
import reactor.event.registry.Registration;
import reactor.event.selector.Selector;
import reactor.event.selector.Selectors;
import reactor.function.Consumer;
import reactor.function.Function;
import reactor.function.Predicate;
import reactor.tuple.Tuple2;

public class MainReactor {
    org.apache.commons.io.IOUtils x12;
	//deps 
//	com.lmax.disruptor.WaitStrategy x; disruptor-3.2.1.jar
//	jsr166e.ConcurrentHashMapV8  y;   
//	com.gs.collections.api.block.procedure.Procedure z; gs-collections-api-7.0.3.jar
//	com.gs.collections.impl.list.mutable.MultiReaderFastList z1; gs-collections-7.0.3.jar
	
	
	public static void main(String[] args) throws Exception  {
		// This helper method is like jQuery��s.
		// It just creates a Selector instance so you don��t have to "new" one up.
		
		Environment env = new Environment();

		// This factory call creates a Reactor.
		Reactor reactor = Reactors.reactor()
		  .env(env) // our current Environment
		  .dispatcher(Environment.EVENT_LOOP) //BlockingQueueDispatchers ,�¼�����ʱ�ȴ洢��һ��Blockingqueue�У�����ͳһ�ĺ�̨�߳�һһ˳��ִ�� 
		  .get(); // get the object when finished configuring

		
		 new Consumer<Tuple2<String, Float>>(){ //Tuple���Դ��������
				@Override
				public void accept(Tuple2<String, Float> arg0) {
				}
			 };
			 
			 
		 Selector sel=Selectors.object("parse");
		// Selector sel= $("parse");  //$("parse") ͬ Selectors.object("parse")
		 
		// Register a consumer to handle events sent with a key that matches "parse"
		Registration reg=reactor.on(sel, new Consumer<Event<String>>() {
		  @Override
		  public void accept(Event<String> ev) {
		    System.out.println("Received event with data: " + ev.getData());
		  }
		});
		reg.pause(); //��ͣ��,��notify���õ�
		System.out.println("paused !");
		reactor.notify("parse", Event.wrap("data"));
		 
		Thread.sleep(1000);
		 
		reg.resume();
		System.out.println("resumed !");
		// Send an event to this Reactor and trigger all actions that match the given key
		reactor.notify("parse", Event.wrap("data"));

		 
//---------------
		// Create a deferred for accepting stream values
		Deferred<String, Stream<String>> deferred = Streams.<String>defer()
		  .env(env)
		  .dispatcher(Environment.RING_BUFFER)
		  .get();
		 
		Stream<String> stream = deferred.compose();
		
		
		
		//---	
		Stream<String> filtered = stream   
				.map(new Function<String, String>() 
				{
				    public String apply(String s) {
				      return s.toLowerCase();
				    }
				  })
				  .filter(new Predicate<String>()
				{
				    public boolean test(String s) {
				      return s.startsWith("nsprefix:");
				    }
				  });
		//---			
		// consume values
		stream.consume(new Consumer<String>() { //stream.consume(), ���� filtered.consume() ��ʹ�ù��˹���
		  public void accept(String s) {
			  System.out.println("---- stream accepted :"+s);
		  }
		});
		
		
		// producer calls accept
		deferred.accept("123nsPrefix:Hello World!");
		
		
		
		promise();
		
		 
	}
	
	
	public static  void promise() throws Exception
	{
	
		Environment env = new Environment();
			
			// Create a deferred for accepting future values
		Deferred<String, Promise<String>> deferred = Promises.<String>defer()
		  .env(env)
		  .dispatcher(Environment.RING_BUFFER)
		  .get();
		
		
		// Set a Consumer for the value
		deferred.compose().onSuccess(new Consumer<String>() {
		  public void accept(String s) {
			  System.out.println("---promise deferred:"+s);
		  }
		});
		// When a value is available, set it on the Deferred
		deferred.accept("Hello World!");
	
		//---------------------- ͬ��,�뵱�����´�ֵ,���ò���
		{
			// Create a completed Promise
			Promise<String> p = Promises.success("Hello World!").get();
	
			// Consumers assigned on a complete Promise will be submitted immediately
			p.onSuccess(new Consumer<String>() {
			  public void accept(String s) {
				  System.out.println("---promise onSuccess :"+s);
			  }
			});
			
			// Create a Promise in an error state
			Promise<String> pError = Promises.<String>error(new IllegalStateException()).get();
			pError.onError(new Consumer<Throwable>(){
				@Override
				public void accept(Throwable ex) {
					System.out.println("---promise onError get :"+ex.getClass().getName());
				}
			});
		}
		//-------------
		 
		Deferred<String, Promise<String>> deferred1 = Promises.<String>defer()
				  //.env(env).dispatcher(Environment.RING_BUFFER) //������ deferred1.accept ����ȴ�ִ�����,���ӻ�ȴ�
				  .get();
		//Promise<String> p1 = Promises.success("12333").get();//���ò���
		Promise<String> p1=deferred1.compose(); 
		
		// Transform the String into a Float using map()
		Promise<Float> p2 = p1.map(new Function<String, Float>() {
		  public Float apply(String s) {
		    return Float.parseFloat(s);
		  }
		}).filter(new Predicate<Float>() {
		  public boolean test(Float f) {
			    return f > 100f;
			  }
			});
		
		//p2.then(onSuccess, onError)
		p2.onSuccess(new Consumer<Float>() { //p1.onSuccess
		  public void accept(Float f) {
			  try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			  System.out.println("---promise Float:"+f);
		  }
		});
		deferred1.accept("182.2");
		
	}


}
*/
