package org.apache.camel.example.spring.boot.rest.jpa;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.NotifyBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CamelContext camelContext;
    /*
 单元测试可以
 
camel:
  springboot:
    name: CamelRestJpa

  component:
    servlet:
      mapping:
        contextPath: /camel-rest-jpa/*
        
     */
    @Test
    public void newOrderTest() { 
        ResponseEntity<Order> response = restTemplate.getForEntity("/camel-rest-jpa/books/order/1", Order.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    public void booksTest() {
        ResponseEntity<List<Book>> response = restTemplate.exchange("/camel-rest-jpa/books",
            HttpMethod.GET, null, new ParameterizedTypeReference<List<Book>>() {
            });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
