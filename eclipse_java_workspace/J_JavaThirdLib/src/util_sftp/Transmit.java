package util_sftp;


import java.io.InputStream;
import java.util.Vector;

/**
 * parameter name pattern
 * file 	 /theDir/theFileName.txt
 * dir 		 /theDir/
 * filename  theFileName.txt
 * @author Li Zhaojin
 */
public interface Transmit 
{
	public void connect() throws Exception;
	public void close();

	public void uploadFile(String clientDir,String serverDir,String filename) throws Exception;
	public void uploadFile(InputStream in,String serverFile) throws Exception; 
	
	public void downloadFile(String serverFile, String clientFile)  throws Exception; 
	public InputStream downloadFile(String downloadFile)throws Exception ;
	
	public Vector<String> listFiles(String dir) throws Exception;
	public Vector<String> listDirs(String dir) throws Exception; 
	
	public boolean isExistFile(String file)throws Exception;
	public boolean isExistDir(String dir)throws Exception;
	
	public void renameMoveFile(String sourceFile,String destFile) throws Exception;
	public void copyFile(String serverSourceFile,String serverDestFile) throws Exception;
	public void deleteFile(String serverDir, String filename)throws Exception ;
	public void makeDir(String dir) throws Exception;
}
