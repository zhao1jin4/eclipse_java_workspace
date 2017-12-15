package swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
 
 
@Controller
@RequestMapping(value = "swaggerController")
public class SwaggerController {
	@RequestMapping(value = "test", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ApiOperation(value = "���Խӿ�", httpMethod = "POST", notes = "���Խӿ�", response = ResponseModel.class)
    public @ResponseBody ResponseModel newPlan(@ApiParam(required = true) @RequestBody  RequestModel request)
    {
		System.out.println("StartTime="+request.getStartTime());
		ResponseModel resp=new ResponseModel();
		resp.setData("123");
		resp.setErrorMessage("�ɹ�");
		return resp;
    }
}
