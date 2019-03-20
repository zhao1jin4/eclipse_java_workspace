package com.example.demo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mybatis.MybatisSpringBoot;
import mybatis.dao.UserMapper;
import mybatis.vo.User;
import not_web.MainApplication;
import not_web.MyProps;

//测试 OK
@RunWith(SpringRunner.class)
@SpringBootTest(classes=MainApplication.class)  
//@SpringBootConfiguration
@ContextConfiguration
public class ConfigTest { 
	@Autowired
	private MyProps myProps;  
	@Autowired
	private  ObjectMapper objectMapper;
	
	@Test
	public void propsTest() throws JsonProcessingException {
		System.out.println("simpleProp: " + myProps.getSimpleProp());
		System.out.println("arrayProps: " + objectMapper.writeValueAsString(myProps.getArrayProps()));
		System.out.println("listProp1: " + objectMapper.writeValueAsString(myProps.getListProp1()));
		System.out.println("listProp2: " + objectMapper.writeValueAsString(myProps.getListProp2()));
		System.out.println("mapProps: " + objectMapper.writeValueAsString(myProps.getMapProps()));
	}
}
