package jpa_procedure;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;

public class MainProcedure {
public static void main(String[] args) {
	
	/*
-- MySQL 存储过程
delimiter //
CREATE PROCEDURE teacherid_count  ( 
    IN teacherId INT,  
    OUT Count INT  
 )  
 BEGIN  
     SELECT COUNT(*) INTO  Count  
     FROM jpa_student t 
     WHERE t.TEACHER_ID = teacherId ;
 END//
delimiter ;
 
	 */
	EntityManagerFactory emf =  Persistence.createEntityManagerFactory("MyJPA");
	EntityManager entityManager = emf.createEntityManager();
	   
	StoredProcedureQuery query = entityManager.createStoredProcedureQuery( "teacherid_count");
	query.registerStoredProcedureParameter( "teacherId", Long.class, ParameterMode.IN);
	query.registerStoredProcedureParameter( "Count", Long.class, ParameterMode.OUT);

	query.setParameter("teacherId", 1L);

	query.execute();
	Long Count = (Long) query.getOutputParameterValue("Count");
	System.out.println("Count="+Count);
}
}
