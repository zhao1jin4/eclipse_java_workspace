package swagger1x_spring;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

@Configuration
@EnableSwagger
public class SwaggerConfig
{
    private SpringSwaggerConfig springSwaggerConfig;
    
    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig)
    {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean
    public SwaggerSpringMvcPlugin customImplementation() throws IOException
    {
        Properties prop = new Properties();
        String pathString = this.getClass().getClassLoader().getResource("/").getPath();
        pathString+="swagger1x_spring/apiInfo.properties";
        InputStream in = new FileInputStream(pathString);
        prop.load(in);
        in.close();
        ApiInfo apiInfo = new ApiInfo("项目标题",  "项目描述", "官方URL",  "联系人aa@sina.com", null, null);
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(apiInfo).includePatterns(".*?");
    }
}