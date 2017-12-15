package apache_net_ftp;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.cache.DefaultFilesCache;
import org.apache.commons.vfs2.impl.DefaultFileSystemManager;
import org.apache.commons.vfs2.provider.local.DefaultLocalFileProvider;
import org.apache.commons.vfs2.provider.sftp.SftpFileProvider;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystem; 
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.apache.commons.vfs2.provider.zip.ZipFileProvider;
public class TestVFS {
	public static void main(String[] args) {
		try{
			
			//sftp://myusername:mypassword@somehost/pub/downloads/somefile.tgz
			//ftp://myusername:mypassword@somehost/pub/downloads/somefile.tgz
			
			//CIFS
			//smb://[ username [: password ]@] hostname [: port ][ absolute-path ] 
			
			
			FileSystemManager fsManager = VFS.getManager();//OK
			
			DefaultFileSystemManager manager = new DefaultFileSystemManager();
			manager.addProvider("sftp", new SftpFileProvider());
			manager.addProvider("zip", new ZipFileProvider());
			manager.addProvider("file", new DefaultLocalFileProvider());
			manager.setFilesCache(new DefaultFilesCache());
			manager.init();//OK
			
			
			
			//FileObject sftp = fsManager.resolveFile( "ftp://zh:lzj@127.0.0.1/" );//Server-U OK
			
			FileSystemOptions opts = new FileSystemOptions();
			SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
			FileObject sftp = manager.resolveFile("sftp://zh:lzj@10.39.101.238/ftp",opts );//jsch.jar,VMware IP can not connect

			FileObject[] children = sftp.getChildren();
			System.out.println( "Children of " + sftp.getName().getURI() );
			for ( int i = 0; i < children.length; i++ )
			{
			    System.out.println( children[ i ].getName().getBaseName() );
			}
			
			FileObject localfs=manager.resolveFile("G:/ftp/");
			if (!sftp.exists()) {
				sftp.createFolder();
			}
			// sftp.copyFrom(localfs, Selectors.SELECT_FILES);//upload
			localfs.copyFrom(sftp, Selectors.SELECT_FILES);// download

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

