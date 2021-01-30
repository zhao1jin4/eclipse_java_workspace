package mybatis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class SqlLogCondition implements Condition {
	@Value("${mylatch.sql.log:x}")//拿不到值
	private String sqlLogStr;
	@Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		String configVal=context.getEnvironment().getProperty("mylatch.sql.log");
		boolean res="true".equalsIgnoreCase(configVal);
        return res;
    }
}