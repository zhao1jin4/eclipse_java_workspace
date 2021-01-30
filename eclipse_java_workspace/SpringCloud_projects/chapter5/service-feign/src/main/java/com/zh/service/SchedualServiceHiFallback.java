package com.zh.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

 
@Component
public class SchedualServiceHiFallback implements SchedualServiceHi {
//    @Override
//    public String sayHiFromClientOne(String name) {
//        return "sorry "+name;
//    }

	public String feignMVC(String owner) {
		 return "sorry feignMVC "+owner;
	}

	@Override
	public ResponseEntity<byte[]> feignMVCByte(String owner) {
		 return new ResponseEntity<byte[]>(("sorry feignMVCByte "+owner).getBytes(), HttpStatus.OK);
	}
}
