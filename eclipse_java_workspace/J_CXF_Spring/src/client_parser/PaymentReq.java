package client_parser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement
public class PaymentReq  implements Serializable {
	private static final long serialVersionUID = 1;

    private String transId;
	private Integer paymentMode;

	private List<TranDetail> tranDetails = new ArrayList<TranDetail>(1);

    private String extFeilds;

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public Integer getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Integer paymentMode) {
        this.paymentMode = paymentMode;
    }

    public List<TranDetail> getTranDetails() {
        return tranDetails;
    }

    public void setTranDetails(List<TranDetail> tranDetails) {
        this.tranDetails = tranDetails;
    }

    public String getExtFeilds() {
        return extFeilds;
    }

    public void setExtFeilds(String extFeilds) {
        this.extFeilds = extFeilds;
    }
    @Override
    public String toString() {
        return "PaymentReq{" +
                "transId='" + transId + '\'' +
                ", paymentMode=" + paymentMode +
                ", tranDetails=" + tranDetails +
                ", extFeilds='" + extFeilds + '\'' +
                "} " + super.toString();
    }
 
}
