import org.hibernate.SessionFactory
import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader
import org.springframework.jdbc.datasource.DriverManagerDataSource

def reader = new GroovyBeanDefinitionReader(myApplicationContext)
reader.beans {
	dataSource(BasicDataSource) {                  // <--- invokeMethod
		driverClassName = "org.hsqldb.jdbcDriver"
		url = "jdbc:hsqldb:mem:grailsDB"
		username = "sa"                            // <-- setProperty
		password = ""
		settings = [mynew:"setting"]
	}
	sessionFactory(SessionFactory) {
		dataSource = dataSource                    // <-- getProperty for retrieving references
	}
	myService(MyService) {
		nestedBean = { AnotherBean bean ->         // <-- setProperty with closure for nested bean
			dataSource = dataSource
		}
	}
}


beans {
	dataSource(DriverManagerDataSource) {
		driverClassName = "org.hsqldb.jdbcDriver"
		url = "jdbc:hsqldb:mem:grailsDB"
		username = "sa"
		password = ""
		settings = [mynew:"setting"]
	}
	sessionFactory(SessionFactory) {
		dataSource = dataSource
	}
	myService(MyService) {
		nestedBean = { AnotherBean bean ->
			dataSource = dataSource
		}
	}
}