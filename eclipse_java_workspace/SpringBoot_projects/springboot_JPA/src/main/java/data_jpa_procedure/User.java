package data_jpa_procedure;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

 
@Entity
public class User {

	@Id @GeneratedValue
	private Long id;
}
