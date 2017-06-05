package springdata_mongodb.bak;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;

import com.mongodb.Mongo;

@Configuration
class SampleMongoConfiguration extends AbstractMongoConfiguration {

  @Override
  protected String getDatabaseName() {
    return "database";
  }

  @Override
  public Mongo mongo() throws Exception {
    return new Mongo();
  }

  @Bean
  @Override
  public MappingMongoConverter mappingMongoConverter() throws Exception 
  {
    MappingMongoConverter mmc = super.mappingMongoConverter();
    mmc.setTypeMapper(customTypeMapper());
    return mmc;
  }

  @Bean
  public MongoTypeMapper customTypeMapper() 
  {
    return new CustomMongoTypeMapper();
  }
  
  
}