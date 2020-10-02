package behavior.visitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.util.SimpleTypeVisitor7;
class FileVisitorTest extends SimpleFileVisitor<Path>  //FileVisitor
{ 
    public FileVisitResult visitFile(Path file,BasicFileAttributes attrs)//不用像父类一样要声明异常
    { 
        System.out.println("file:"+file.getParent()+"/"+file.getFileName());
        //attrs.isSymbolicLink()对windows的快捷方式无法识别
        //attrs.isRegularFile();
		
		//FileVisitResult.SKIP_SIBLINGS;
		//FileVisitResult.SKIP_SUBTREE;
		//ileVisitResult.TERMINATE;
        return FileVisitResult.CONTINUE; 
    } 
    public FileVisitResult preVisitDirectory(Path dir,BasicFileAttributes attrs)
    {
    	System.out.println("进入目录:"+dir.getFileName());
        return FileVisitResult.CONTINUE; 
    } 
    public FileVisitResult visitFileFailed(Path file,IOException e){
    	System.out.println(file.getFileName()+"仿问失败");
        return FileVisitResult.CONTINUE; 
    } 
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) 
	{
		System.out.println("离开目录:"+dir.getFileName());
		 return FileVisitResult.CONTINUE; 
	}
}

public class TestVisitor {

	public static void main(String[] args) {
		//JDK 中的Visitor
		FileVisitor fileVisitor=null;
		
		try {
			Files.walkFileTree(Paths.get("c:/temp"), new FileVisitorTest());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SimpleFileVisitor simpleFileVisitor=null;
		
		ElementVisitor x=null;//JDK 中的Visitor
		SimpleTypeVisitor7 type=null;
		SourceVersion y=null;//enum中也可以有方法
	}

}
