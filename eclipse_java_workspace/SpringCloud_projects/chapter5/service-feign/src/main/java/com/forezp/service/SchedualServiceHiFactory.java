package com.forezp.service;

import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;
 
@Component
public class SchedualServiceHiFactory  implements FallbackFactory<SchedualServiceHi>  {

	@Override
	public SchedualServiceHi create(Throwable cause) {
		return new SchedualServiceHi() {
		 @Override
		    public String sayHiFromClientOne(String name) {
		        return "sorry "+name;
		    }
			public String feignMVC(String owner) {
				 return "sorry feignMVC "+owner;
			}
		} ;
	}
}
