package jax_rs_spring2;
import javax.xml.bind.annotation.XmlAccessorType;

import javax.xml.bind.annotation.XmlAccessType;

import javax.xml.bind.annotation.XmlType;

/**  

* Web Service传输信息的DTO.  

* 分离entity类与web service接口间的耦合，隔绝entity类的修改对接口的影响. 使用JAXB 2.0的annotation标注JAVA-XML映射，尽量使用默认约定.  *   

*/ 

@XmlAccessorType(XmlAccessType.FIELD) @XmlType(name = "User") 

public class ParamDTO  {

        protected Integer id;

        protected String name; 

         public Integer getId()      {

              return id;

        }

        public void setId(Integer value)      {

              id = value;

       }

        public String getName()      {

              return name;

      }

        public void setName(String value)      {

             name = value;

      } 

}