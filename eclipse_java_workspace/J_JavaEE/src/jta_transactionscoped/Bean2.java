package jta_transactionscoped;

@javax.transaction.TransactionScoped
public class Bean2 implements java.io.Serializable {

    public String getId() {
        return "ObjectId for Bean2 is " + this + "";
    }

    @javax.annotation.PreDestroy
    public void preDestroy() {
        System.out.println("In Bean2 predestroy");
    }
}
