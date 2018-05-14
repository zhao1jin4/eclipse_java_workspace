package my_code.validation;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String uuid;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String email;

	private Set<Account> accounts;
	private String phoneNumber;
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	// make this package private and handle it in factory parent-child
	public Boolean addAccount(Account account) {
		if (accounts == null) {
			accounts = new HashSet<Account>();
		}
		return accounts.add(account);
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public boolean equals(final Object other) {
		if (this == other)
			return true;
		if (!(other instanceof Customer))
			return false;
		Customer castOther = (Customer) other;
		return new EqualsBuilder().append(firstName, castOther.firstName)
				.append(lastName, castOther.lastName)
				.append(dateOfBirth, castOther.dateOfBirth)
				.append(email, castOther.email)
				.append(accounts, castOther.accounts)
				.append(phoneNumber, castOther.phoneNumber).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(441293447, 2056268651).append(firstName)
				.append(lastName).append(dateOfBirth).append(email)
				.append(accounts).append(phoneNumber).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("firstName", firstName)
				.append("lastName", lastName)
				.append("dateOfBirth", dateOfBirth).append("email", email)
				.append("accounts", accounts)
				.append("phoneNumber", phoneNumber).toString();
	}

}
