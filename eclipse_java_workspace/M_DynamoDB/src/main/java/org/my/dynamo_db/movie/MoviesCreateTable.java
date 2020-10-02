package org.my.dynamo_db.movie;

import java.util.Arrays;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

public class MoviesCreateTable {

    public static void main(String[] args) throws Exception {

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
            .build();
        DynamoDB dynamoDB = new DynamoDB(client);
        String tableName = "Movies";
        Table table = dynamoDB.createTable(tableName,
            Arrays.asList(new KeySchemaElement("year", KeyType.HASH), // Partition
                new KeySchemaElement("title", KeyType.RANGE)), // Sort key
            Arrays.asList(new AttributeDefinition("year", ScalarAttributeType.N),
                new AttributeDefinition("title", ScalarAttributeType.S)),
            new ProvisionedThroughput(10L, 10L));
        table.waitForActive();
 

        //table.delete();
        //table.waitForDelete();
        
    }
}