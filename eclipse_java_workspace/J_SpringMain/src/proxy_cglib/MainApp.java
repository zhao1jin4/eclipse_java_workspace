package proxy_cglib;

public class MainApp
{
	public static void main(String[] args)
	{
		AOPInstrumenter instrumenter = new AOPInstrumenter();
		StudentInfoServiceImpl studentInfo = (StudentInfoServiceImpl) instrumenter.getInstrumentedClass(StudentInfoServiceImpl.class);
		studentInfo.findInfo("目标方法");

	}
}
