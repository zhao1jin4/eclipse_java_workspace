
package jpa.composite_id;
 
 import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

 
 
 
 
 public class TestComposite {
     
     EntityManagerFactory emf = null;
     
     @BeforeEach  
     public void before() {
         //根据在persistence.xml中配置的persistence-unit name 创建EntityManagerFactory
         emf = Persistence.createEntityManagerFactory("MyJPA");
     }
     
     @Test
     public void save() {
         EntityManager em = emf.createEntityManager();
         em.getTransaction().begin();
         
         AirLinePk pk=new AirLinePk();
         pk.setFromCity("北京");
         pk.setToCity("上海");
         AirLine line=new AirLine();
         line.setName("东方航空");
         line.setPk(pk);
         
         em.persist(line);
         //--same key
         pk=new AirLinePk();
         pk.setFromCity("北京");
         pk.setToCity("上海");
         
         line=new AirLine();
         line.setName("吉祥航空");
         line.setPk(pk);
         
         em.persist(line);
         
         
         em.getTransaction().commit();
         em.close();
     }
     
     
     
     /**
      * 关闭EntityManagerFactory
      */
     @AfterEach
     public void after() {
         if(null != emf) {
             emf.close();
         }
     }
 
 }