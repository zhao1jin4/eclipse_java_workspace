package nosql_mongodb_morphia;

import java.util.Arrays;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import junit.framework.Assert;

 

public final class QuickTour {
    private QuickTour() {
    }
	//http://morphiaorg.github.io/morphia/1.4/getting-started/quick-tour/
    public static void main(final String[] args) {
        final Morphia morphia = new Morphia();

        // tell morphia where to find your classes
        // can be called multiple times with different packages or classes
        morphia.mapPackage("nosql_mongodb_morphia");

        // create the Datastore connecting to the database running on the default port on the local host
//        final Datastore datastore = morphia.createDatastore(new MongoClient(), "morphia_example");
//        datastore.getDB().dropDatabase();
       
    	MongoCredential credential = MongoCredential.createCredential("zh", "reporting", "123".toCharArray());
	    ServerAddress[] addrs=	new ServerAddress[] { new ServerAddress("127.0.0.1", 27017) };
		MongoClientOptions opts= new MongoClientOptions.Builder().build();
	 	MongoClient mongoClient  = new MongoClient(Arrays.asList(addrs),  credential,opts );  
		Datastore datastore = morphia.createDatastore(mongoClient,"reporting");

        
        datastore.ensureIndexes();

        final Employee elmer = new Employee("Elmer Fudd", 50000.0);
        datastore.save(elmer);

        final Employee daffy = new Employee("Daffy Duck", 40000.0);
        datastore.save(daffy);

        final Employee pepe = new Employee("Pepé Le Pew", 25000.0);
        datastore.save(pepe);

        elmer.getDirectReports().add(daffy);//保存的是directReports数据，0是{$ref:"",$id:""}
        elmer.getDirectReports().add(pepe);

        datastore.save(elmer);

        Query<Employee> query = datastore.find(Employee.class);
        final long employees = query.count();

        Assert.assertEquals(3, employees);

        long underpaid = datastore.find(Employee.class)
                                  .filter("salary <=", 30000)
                                  .count();
        Assert.assertEquals(1, underpaid);

        underpaid = datastore.find(Employee.class)
                             .field("salary").lessThanOrEq(30000)
                             .count();
        Assert.assertEquals(1, underpaid);

        final Query<Employee> underPaidQuery = datastore.find(Employee.class)
                                                        .filter("salary <=", 30000);
        final UpdateOperations<Employee> updateOperations = datastore.createUpdateOperations(Employee.class)
                                                                     .inc("salary", 10000);

        final UpdateResults results = datastore.update(underPaidQuery, updateOperations);

        Assert.assertEquals(1, results.getUpdatedCount());

        final Query<Employee> overPaidQuery = datastore.find(Employee.class)
                                                       .filter("salary >", 100000);
        datastore.delete(overPaidQuery);
    }
}