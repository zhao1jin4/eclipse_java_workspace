package bean_validation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

public class Order 
{
	@Size(min=2,max=20 ,message="�����ű���Ϊ2-20�ĳ���")
	private String orderId;
	
	@Length(min=2,max=20)//hiberante
	private String customer;
	
	@NotBlank //hiberante
	@Email //hiberante
	private String email;
	
	@NotNull(message="��������Ϊnull")
	@NotEmpty(message="�������ڲ���Ϊ�մ�") //hiberante
	private String createDate;
	
	@Pattern(regexp="^[0-9]{13}$" ,message="�ֻ��ű�����13λ����")
	private String telepone;
	
	@javax.validation.constraints.Pattern(regexp="^(Y|N){1}$" ,message="isPay������Y��N")
    private String isPay;
	
	@Range(min=0,message="����Ҫ����0")//hiberante
	private float price;
	
	@Status(message="״̬Ӧֻ���� 'created', 'paid', shipped', closed'") //�Զ�����֤
	private String status;  
	
	 @Valid   // Ƕ����֤
	 private User user;

	 
	public String getIsPay() {
		return isPay;
	}

	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelepone() {
		return telepone;
	}

	public void setTelepone(String telepone) {
		this.telepone = telepone;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	} 
}
