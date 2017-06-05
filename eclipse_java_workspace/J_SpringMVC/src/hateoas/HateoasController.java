package hateoas;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HateoasController
{
    private static final String TEMPLATE = "Hello, %s!";

    @RequestMapping("/hateoas")
    @ResponseBody
    public HttpEntity<Greeting> greeting(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {

        Greeting greeting = new Greeting(String.format(TEMPLATE, name));
        greeting.add(linkTo(methodOn(HateoasController.class).greeting(name)).withSelfRel());
        
        //依赖于 objenesis-2.1.jar
        //linkTo 生成 ("href":) 放入 add 到 _links  中
        // methodOn(GreetingController.class).greeting(name) 生成  "href":"http://localhost:8080/J_SpringMVC/greeting?name=World"
        // withSelfRel 生成 Link对象 生成  "rel":"self" 
        /* 
        	{"content":"Hello, World!",
        		"links":
        			[{
        				"rel":"self",
        				"href":"http://localhost:8080/J_SpringMVC/greeting?name=World"
        			}]
        	}
        */
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }
}