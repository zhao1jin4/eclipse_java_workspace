package apache_antXml;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.Project;

public class MyTask extends Task
{

	private int size;
	private File dir;
	public void setSize(int size)
	{
		this.size = size;
		log("�����Զ��������setSize:"+size , Project.MSG_INFO);
	}
	public void setDir(File dir)
	{
		this.dir = dir;
		log("�����Զ��������setDir:"+dir.getAbsolutePath() , Project.MSG_INFO);
	}
	
	
	
	

	@Override
	public void execute() throws BuildException
	{
		// TODO Auto-generated method stub
		super.execute();
		log("�����Զ��������excecute---" , Project.MSG_INFO);
		Project proj=getProject();
		File base=proj.getBaseDir();
		log("�����Զ�base.dir is:"+base.getAbsolutePath(),Project.MSG_DEBUG);
		
		
	}

	@Override
	public void init() throws BuildException
	{
		super.init();
		log("�����Զ��������init" , Project.MSG_INFO);
	}

}
