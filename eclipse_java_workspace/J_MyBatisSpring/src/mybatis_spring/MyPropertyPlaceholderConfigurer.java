package mybatis_spring;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 为数据库连接配置信息加密处理
 * 
 * @author zhaojin
 *
 */
public class MyPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	private String[] encryptPropNames = { "usernameEnc", "passwordEnc" };
	@Override
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
