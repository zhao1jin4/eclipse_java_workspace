/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.cloud.examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 * @author xiaojing
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient(autoRegister = false)
public class BusinessApplication {

	/*
 
	 
https://github.com/alibaba/spring-cloud-alibaba/tree/master/spring-cloud-alibaba-examples/seata-example 

spring-cloud-starter-alibaba-seata 的父 spring-cloud-alibaba-dependencies 2.2.1.RELEASE 对应seata-all-1.1.0 
 最好使用和服务一样的版本 ,不能排除seata-all使用新的1.2.0 报类找不到 
 
 示例代码要修改 seata-server-1.2.0/seata/conf/file.conf 顶部增加
service {
# vgroup_mapping.<起的组名> 对应于 spring.cloud.alibaba.seata.tx-service-group=business-service  ,如配置为file还要复制到本地项目中
 vgroup_mapping.business-service = "default" 
 vgroup_mapping.account-service = "default" 
 default.grouplist = "127.0.0.1:8091"
 enableDegrade = false
 disableGlobalTransaction = false
}
如要复制到项目中，可能只要一个 vgroup_mapping 
使用 config.txt导入到Nacos,即 python  nacos-config.py localhost:8848  #读上层目录的config.txt 文件
  

business-service 使用feign, 使用Nacos

 bussiness  
	-> storage (-2) 
	-> order (增加一条) -> 再调用account -4 (account随机出错) 后 -> order随机出错 
	
http://127.0.0.1:18081/seata/feign  服务端使用1.2版本,使用nacos和db ,测试模板出错成功回滚（刚启动时报timeout）
http://127.0.0.1:18081/seata/rest  服务端使用1.2版本,使用nacos和db ,测试模板出错成功回滚
	 */
	public static void main(String[] args) {
		SpringApplication.run(BusinessApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@FeignClient("storage-service")
	public interface StorageService {

		@GetMapping(path = "/storage/{commodityCode}/{count}")
		String storage(@PathVariable("commodityCode") String commodityCode,
				@PathVariable("count") int count);

	}

	@FeignClient("order-service")
	public interface OrderService {

		@PostMapping(path = "/order")
		String order(@RequestParam("userId") String userId,
				@RequestParam("commodityCode") String commodityCode,
				@RequestParam("orderCount") int orderCount);

	}

}
