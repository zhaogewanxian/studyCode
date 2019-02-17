package com.wanxian.demo.service.impl;


import com.wanxian.demo.service.IDemoService;
import com.wanxian.spring.annotation.Service;

@Service
public class DemoService implements IDemoService {

	public String get(String name) {
		return "My name is " + name;
	}

}
