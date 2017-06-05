package client_parser;


import java.io.Serializable;
import java.math.BigDecimal;
 
public class TranDetail implements Serializable {
	private static final long serialVersionUID = 1;
	private String payAcctId;// 分账户ID

	private String payType; // 支付方式

	private BigDecimal payAmt; // 转出金额

    private String currencyCode;

    private String extFeilds;

	public String getPayAcctId() {
		return payAcctId;
	}

	public void setPayAcctId(String payAcctId) {
		this.payAcctId = payAcctId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public BigDecimal getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getExtFeilds() {
		return extFeilds;
	}

	public void setExtFeilds(String extFeilds) {
		this.extFeilds = extFeilds;
	}

	@Override
    public String toString() {
        return "TranDetail{" +
                "payAcctId='" + payAcctId + '\'' +
                ", payType='" + payType + '\'' +
                ", payAmt=" + payAmt +
                ", currencyCode='" + currencyCode + '\'' +
                ", extFeilds='" + extFeilds + '\'' +
                "} " + super.toString();
    }
}
