
package data_jpa_procedure;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
 
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RepositoryProcedureTests {
	//存储过程 官方示例，在mysql上只部分成功，User就没什么用
	@Autowired UserRepository repository;
 
	@Test
	public void invokeDerivedStoredProcedure() {//OK
		Integer res1=repository.plus1inout(1);
		System.out.println(res1);
		Assertions.assertEquals(res1,2);
	}

	@Autowired EntityManager em;
	@Test
	public void plainJpa21() { //OK

		StoredProcedureQuery proc = em.createStoredProcedureQuery("plus1inout");
		proc.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		proc.registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT);

		proc.setParameter(1, 1);
		proc.execute();
		
		Object obj=proc.getOutputParameterValue(2);
		System.out.println(obj);
		Assertions.assertEquals(obj, (Object) 2);
	}

	
}
