package bean_validation;

import javax.validation.Valid;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

public class Order 
{
	@Size(min=2,max=20 ,message="订单号必须为2-20的长度")
	private String orderId;
	
	@Length(min=2,max=20)//hiberante
	private String customer;
	
	@NotBlank 
	@Email  
	private String email;
	
	@NotNull(message="建立日期为null")
	@NotEmpty(message="建立日期不能为空串") 
	private String createDate;
	
	@Pattern(regexp="^[0-9]{13}$" ,message="手机号必须是13位数字")
	private String telepone;
	
	@Pattern(regexp="^(Y|N){1}$" ,message="isPay必须是Y或N")
    private String isPay;
	
	
	@Digits(integer=4,fraction=2) //整数最多4位，小数最多2位
	@DecimalMax("5555.32")
	@DecimalMin("10.00")
	private float price;
	
	@Status(message="状态应只可是 'created', 'paid', shipped', closed'") //自定义验证
	private String status;  
	
	@Null(message="下一个必须为空")
	private Order nextOrder;
	
	@AssertTrue(message="有效必须为true")  // @AssertFalse 
	private boolean available;
	
	@Valid   // 嵌套验证 
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
