package org.my.dynamo_db.movie;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

public class MoviesCRUD {

    public static void main(String[] args) throws Exception {
    	//https://docs.aws.amazon.com/zh_cn/amazondynamodb/latest/developerguide/GettingStarted.Java.01.html
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
            .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("Movies");

        
       int   year = 2015;
        String  title = "The Big New Movie";
        final Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("plot", "Nothing happens at all.");
        infoMap.put("rating", 0);

        System.out.println("Adding a new item...");
        PutItemOutcome outcome = table
            .putItem(new Item().withPrimaryKey("year", year, "title", title).withMap("info", infoMap));

        
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("year", year, "title", title);
        Item outcome1 = table.getItem(spec);
        
        {
        	 HashMap<String, String> nameMap = new HashMap<String, String>();
             nameMap.put("#yr", "year");

             HashMap<String, Object> valueMap = new HashMap<String, Object>();
             valueMap.put(":yyyy", 1985);

             QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#yr = :yyyy").withNameMap(nameMap)
                 .withValueMap(valueMap);
             
             ItemCollection<QueryOutcome>   items = table.query(querySpec);
             Iterator<Item>  iterator = items.iterator();
             while (iterator.hasNext()) {
             	Item   item = iterator.next();
                 System.out.println(item.getNumber("year") + ": " + item.getString("title"));
             }
        }
      
        {
        	  ScanSpec scanSpec = new ScanSpec().withProjectionExpression("#yr, title, info.rating")
                      .withFilterExpression("#yr between :start_yr and :end_yr").withNameMap(new NameMap().with("#yr", "year"))
                      .withValueMap(new ValueMap().withNumber(":start_yr", 1950).withNumber(":end_yr", 1959));

              ItemCollection<ScanOutcome> items = table.scan(scanSpec);

              Iterator<Item> iter = items.iterator();
              while (iter.hasNext()) {
                  Item item = iter.next();
                  System.out.println(item.toString());
              }
                  
        }
        
        
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("year", year, "title", title)).withConditionExpression("info.rating <= :val")
                .withValueMap(new ValueMap().withNumber(":val", 5.0));

        table.deleteItem(deleteItemSpec);

        
    }
}