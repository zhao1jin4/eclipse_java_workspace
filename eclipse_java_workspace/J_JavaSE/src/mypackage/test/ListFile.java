package mypackage.test;



import java.io.File;

class ListFile
{
	String extension;
	String path;
	String ext[];
	public ListFile(String extension,String path)
	{
		this.extension=extension;
		this.path=path;
		
		ext=extension.split(",");
	}
	//ext='jpeg,gif'   path="c:\good\"
	public void testFile(String path)//全路径 无=
	{
		
		File f=new File(path);//是一个文件夹
		
		File[] filename=f.listFiles();
		for (int i=0;i<filename.length;i++)
		{
			if(filename[i].isDirectory())
				testFile(filename[i].getAbsolutePath());
			else if (filename[i].isFile())
				checkFile(filename[i]);
		
		}
		
	}
	public void checkFile(File f)
	{
		
		String temp=f.getName();
		if(temp.indexOf(".")!=-1)
		{
			for(int i=0;i<ext.length;i++)
			{
				if((temp.substring(temp.indexOf(".")+1)).equals(ext[i]))
				{
					System.out.println(f.getAbsolutePath());
					
				}
			}

		}
	}
	

}