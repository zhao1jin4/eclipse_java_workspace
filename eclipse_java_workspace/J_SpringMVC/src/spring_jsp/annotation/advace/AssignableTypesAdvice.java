package spring_jsp.annotation.advace;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.AbstractController;

// Target all Controllers assignable to specific classes
@ControllerAdvice(assignableTypes = {Controller.class, AbstractController.class})
public class AssignableTypesAdvice {
	
}