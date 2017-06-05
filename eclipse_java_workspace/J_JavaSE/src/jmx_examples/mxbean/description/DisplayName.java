/**
 * DisplayName.java - This annotation allows to supply
 * a display name for a method in the MBean interface.
 */

package jmx_examples.mxbean.description;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.management.DescriptorKey;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DisplayName {//自已建立一个annotation
    @DescriptorKey("displayName")//jconsole中[描述符]中会新增加属性名 
    String value();
}
