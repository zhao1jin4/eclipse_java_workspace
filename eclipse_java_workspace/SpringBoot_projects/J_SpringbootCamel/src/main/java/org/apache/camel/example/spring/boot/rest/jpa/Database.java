 package org.apache.camel.example.spring.boot.rest.jpa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Database {

    public Iterable<Book> findBooks() {
    	List<Book> list=new ArrayList<>();
    	Book book=new Book();
    	book.setItem("java");
    	list.add(book);
    	return list;
    }

    public Order findOrder(Integer id) {
    	Order order =new Order();
    	order.setAmount(22);
    	return order;
    }
}
