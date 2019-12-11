package com.forezp.service;

import org.springframework.stereotype.Component;

 
@Component
public class SchedualServiceHiHystric implements SchedualServiceHi {
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry "+name;
    }

	public String feignMVC(String owner) {
		 return "sorry feignMVC "+owner;
	}
}
