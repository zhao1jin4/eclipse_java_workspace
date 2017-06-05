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
	//����Ŀ¼�ı仯
	public static void JDK67WatchDir() throws Exception
    { 
    	Path path=Paths.get("c:/temp");//Ŀ¼
    	WatchService  watcher = FileSystems.getDefault().newWatchService(); 
        path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_DELETE,StandardWatchEventKinds.ENTRY_MODIFY);
        while(true)
        { 
            WatchKey key = watcher.take(); //���������
            for(WatchEvent<?> event : key.pollEvents())
            { 
                WatchEvent.Kind kind = event.kind(); 
                if(kind == StandardWatchEventKinds.OVERFLOW)//�¼�����lost or discarded 
                    continue; 
                WatchEvent<Path> e = (WatchEvent<Path>)event; 
                Path fileName = e.context(); 
                System.out.printf("Event %s has happened,which fileName is %s%n"  ,kind.name(),fileName); 
                // �����ļ���kind.name()����  ENTRY_CREATE��,path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE
                // �޸��ļ���kind.name()����  ENTRY_MODIFY
                // ɾ���ļ���kind.name()����  ENTRY_DELETE
            } 
            if(!key.reset())
                break; 
        } 
    } 
	public static void main(String... args)throws Exception //main����д��
	{
		//JDK67WatchDir();
		Files.walkFileTree(Paths.get("c:/temp"), new FileVisitorTest()); 		//�����߱���Ŀ¼
	}	

}
class FileVisitorTest extends SimpleFileVisitor<Path>  //FileVisitor
{ 
    public FileVisitResult visitFile(Path file,BasicFileAttributes attrs)//��������һ��Ҫ�����쳣
    { 
        System.out.println("file:"+file.getParent()+"/"+file.getFileName());
        //attrs.isSymbolicLink()��windows�Ŀ�ݷ�ʽ�޷�ʶ��
        //attrs.isRegularFile();
		//FileVisitResult.SKIP_SIBLINGS;
		//FileVisitResult.SKIP_SUBTREE;
		//ileVisitResult.TERMINATE;
        return FileVisitResult.CONTINUE; 
    } 
    public FileVisitResult preVisitDirectory(Path dir,BasicFileAttributes attrs)
    {
    	System.out.println("����Ŀ¼:"+dir.getFileName());
        return FileVisitResult.CONTINUE; 
    } 
    public FileVisitResult visitFileFailed(Path file,IOException e){
    	System.out.println(file.getFileName()+"����ʧ��");
        return FileVisitResult.CONTINUE; 
    } 
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) 
	{
		System.out.println("�뿪Ŀ¼:"+dir.getFileName());
		 return FileVisitResult.CONTINUE; 
	}
}