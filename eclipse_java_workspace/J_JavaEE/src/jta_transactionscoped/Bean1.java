package jta_transactionscoped;

@javax.transaction.TransactionScoped
public class Bean1 implements java.io.Serializable {

    public String getId() {
        return "ObjectId for Bean1 is " + this + "";
    }

    @javax.annotation.PreDestroy
    public void preDestroy() {
        System.out.println("In Bean1 predestroy");
    }
}
