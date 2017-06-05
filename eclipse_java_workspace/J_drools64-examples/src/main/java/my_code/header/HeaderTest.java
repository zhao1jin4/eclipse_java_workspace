package my_code.header;


import java.util.ArrayList;
import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.core.factmodel.ClassDefinition;
import org.drools.definition.type.FactType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;

import my_code.Message;


 

public class HeaderTest {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-header");
        	
        	
        	
            /*********************global   start*********************/
        	//用于设置返回值
	        List listData = new ArrayList();
	        kSession.setGlobal("list", listData);//第一个参数会传给 .drl global的定义,fireRule后这值会配对的关联修改
	        //设置服务
	        GlobalService service = new GlobalService();
	        kSession.setGlobal("service", service);
	            
	        Message message = new Message();
	        message.setMessage("david");
	        kSession.insert(message);
	      
        	/*********************global   end*********************/
            
            
        	
        	/*********************function   start*********************/
        	/*********************function   end*********************/
        	
        	
        	
        	
        	/*********************query   在当前的WorkingMemory 当中查找Fact  start*********************/
        	Message message1 = new Message();
        	message1.setMessage("david");
	        kSession.insert(message1);
	
	        Message message2 = new Message();
	        message2.setMessage("david2");
	        kSession.insert(message2);
	          
	        QueryResults results = kSession.getQueryResults("queryDavid");
	        for(QueryResultsRow qr : results) {
	        	Message msg = (Message)qr.get("msg");//参数名对应drl的:前部分
	          	System.out.println("queryDavid = " + msg.getMessage());
	        }
	        QueryResults results2 = kSession.getQueryResults("queryDavid2", "david2");
	        for(QueryResultsRow qr : results2) {
	          	Message msg = (Message)qr.get("message");
	          	System.out.println("queryDavid2 = " + msg.getMessage());
	        }
        	/*********************query   在当前的WorkingMemory 当中查找Fact  end*********************/
            
            
	        
	        /*********************declare   start*********************/
        	ClassDefinition addressType = (ClassDefinition) kContainer.getKieBase("header").getFactType("com.sample.header", "Address");//kmodule配置base名可选
//	        ClassDefinition addressType = (ClassDefinition) kSession.getKieBase().getFactType("com.sample.header", "Address");//drl package名,declare名
            Object obj = addressType.newInstance();
            addressType.set( obj,"city","wuhan" );
            kSession.insert(obj);
        	/*********************declare   end*********************/
	        

            /*********************declare   start*********************/
            String city = (String)addressType.get( obj,"city" );
            System.out.println("获取子定义的类属性值：" + city);
            /*********************declare   end*********************/
	        
	        
        	//引爆所有规则
            kSession.fireAllRules();
            
            /*********************global   start*********************/
            System.out.println("global list=：" + listData);
            listData = (List)kSession.getGlobal("list");
            System.out.println("全局结果：" + listData);
            /*********************global   end*********************/
            
            
            
          
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
     
    
}
