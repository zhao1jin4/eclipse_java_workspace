package apache_camel.service;

import org.springframework.stereotype.Service;

import apache_camel.vo.CommonResponse;
import apache_camel.vo.Student;

@Service
public class StudentService {
	public CommonResponse save(Student stu) throws Exception {
		CommonResponse res=new CommonResponse();
		System.out.println("save student "+stu);
		if(1==1)
			throw new Exception("test exception");
			//throw new RuntimeException("test runtime exception");
		 
		return res;
	}
	public CommonResponse getById(int id) {
		CommonResponse res=new CommonResponse();
		System.out.println("getById id "+id);
		Student s=new Student();
		s.setId(id);
		s.setName("lisi");
		res.setExt(s);
		return res;
	}
	public CommonResponse asyncGen(Student stu,boolean isRegenerate )
	{
		CommonResponse res=new CommonResponse();
		System.out.println("isRegenerate "+isRegenerate);
		return res;
	}
}
