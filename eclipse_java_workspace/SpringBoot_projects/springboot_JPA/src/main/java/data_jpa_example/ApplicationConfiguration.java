package data_jpa_example;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
 
@SpringBootApplication
public class ApplicationConfiguration {
	public void datasource() {
		//spring boot 的 DataSourceBuilder 优先使用hikari
		DataSource datasource=DataSourceBuilder.create().url("").username("").password("").build();
	}
}
