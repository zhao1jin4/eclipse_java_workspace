 package nio.file;


import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;

public class NIOFileWatch
{
	//监视目录的变化
	public static void JDK67WatchDir() throws Exception
    { 
    	Path path=Paths.get("c:/temp");//目录
    	WatchService  watcher = FileSystems.getDefault().newWatchService(); 
        path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_DELETE,StandardWatchEventKinds.ENTRY_MODIFY);
        while(true)
        { 
            WatchKey key = watcher.take(); //这里会阻塞
            for(WatchEvent<?> event : key.pollEvents())
            { 
                WatchEvent.Kind kind = event.kind(); 
                if(kind == StandardWatchEventKinds.OVERFLOW)//事件可能lost or discarded 
                    continue; 
                WatchEvent<Path> e = (WatchEvent<Path>)event; 
                Path fileName = e.context(); 
                System.out.printf("Event %s has happened,which fileName is %s%n"  ,kind.name(),fileName); 
                // 创建文件　kind.name()返回  ENTRY_CREATE　,path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE
                // 修改文件　kind.name()返回  ENTRY_MODIFY
                // 删除文件　kind.name()返回  ENTRY_DELETE
            } 
            if(!key.reset())
                break; 
        } 
    } 
	public static void main(String... args)throws Exception //main的新写法
	{
		//JDK67WatchDir();
		Files.walkFileTree(Paths.get("c:/temp"), new FileVisitorTest()); 		//仿问者遍历目录
	}	

}
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