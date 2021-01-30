package mybatis_spring;


import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * 为数据库连接配置信息加密处理
 */
public class MyPropertyPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer {
	private String[] encryptPropNames = { "usernameEnc", "passwordEnc" };
	 
	protected String convertProperty(String propertyName, String propertyValue) {
		if (isEncryptProp(propertyName)) 
		{ 
			String decryptValue = DESUtil.getDecryptString(propertyValue);
			return decryptValue;
		} else 
		{
			return propertyValue;
		}
	}
	private boolean isEncryptProp(String propertyName) 
	{
		for (String encryptpropertyName : encryptPropNames) 
		{
			if (encryptpropertyName.equals(propertyName))
				return true;
		}
		return false;
	}
}
