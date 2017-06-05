package spring_jsp.annotation.form;
import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
public class EmailForm implements Serializable {   
	
	@NotNull(message = "������Ϊ��Null")
	@NotEmpty(message = "������Ϊ��Empty")
	@NotBlank(message = "������Ϊ��Blank")
    @Pattern(regexp = "^[_.0-9a-z-]+@([0-9a-z][0-9a-z-]+.)+[a-z]{2,3}$",
    		message = "{validation.email_format}")
    private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
}  