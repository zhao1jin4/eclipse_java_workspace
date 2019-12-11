/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package apache_dubbo27_server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

//如使用spring 加 dubbo-config-spring-2.7.4.1.jar
 
 
public class SpringXMLDubboServer {
    public static void main(String[] args) throws Exception {
    	
    	//-Ddubbo.properties.file=apache_dubbo27_server/dubbo.properties  这样好像不行
    	//同老版本,同.jar不能和新版本放在一起，放在not_offen中
    	System.setProperty("dubbo.properties.file", "apache_dubbo27_server/dubbo.properties");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("apache_dubbo27_server/dubbo-provider.xml");
        context.start();
        System.in.read();
    }
}
