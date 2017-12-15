//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package spring_fdfs;
 
 
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Resource;
import org.csource.common.DFSException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;

public class FileSystemClient {
    @Resource
    private StorageClient1 stclient;
    @Resource
    ClientGlobal dfsClientConnection;

    public FileSystemClient() {
    }

    public String upload(FileModel model) throws IOException, DFSException {
        if(model != null) {
            NameValuePair[] meta_list = new NameValuePair[]{new NameValuePair("fileName", model.getFileName()), new NameValuePair("extName", model.getExtName()), new NameValuePair("size", model.getSize() + ""), new NameValuePair("md5", model.getMd5() + ""), new NameValuePair("contentType", model.getContentType()), null, null};
            if(model.getUploadDate() != null) {
                meta_list[5] = new NameValuePair("uploadDate", (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(model.getUploadDate()));
            }

            meta_list[6] = new NameValuePair("creator", model.getCreator());
            return this.stclient.upload_file1(model.getData(), model.getExtName(), meta_list);
        } else {
            throw new IllegalArgumentException("Param is not allowed as a nullPoint.");
        }
    }

    public String uploadBinary(byte[] fileBuff, String fileExtName) throws IOException, DFSException {
        return this.stclient.upload_file1(fileBuff, fileExtName, (NameValuePair[])null);
    }

    public String uploadBinary(byte[] fileBuff, String fileExtName, NameValuePair[] meta_list) throws IOException, DFSException {
        return this.stclient.upload_file1(fileBuff, fileExtName, meta_list);
    }

    public FileModel download(String fileName) throws IOException, DFSException, ParseException {
        NameValuePair[] meta_list = this.stclient.get_metadata1(fileName);
        if(meta_list == null) {
            return null;
        } else {
            FileModel model = new FileModel();
            NameValuePair[] var4 = meta_list;
            int var5 = meta_list.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                NameValuePair nameValuePair = var4[var6];
                if(nameValuePair.getName().equals("contentType")) {
                    model.setContentType(nameValuePair.getValue());
                } else if(nameValuePair.getName().equals("fileName")) {
                    model.setFileName(nameValuePair.getValue());
                } else if(nameValuePair.getName().equals("extName")) {
                    model.setExtName(nameValuePair.getValue());
                } else if(nameValuePair.getName().equals("size")) {
                    Long size = Long.valueOf(Long.parseLong(nameValuePair.getValue()));
                    model.setSize(size);
                } else if(nameValuePair.getName().equals("md5")) {
                    model.setMd5(nameValuePair.getValue());
                } else if(nameValuePair.getName().equals("uploadDate") && nameValuePair.getValue() != null) {
                    Date date = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).parse(nameValuePair.getValue());
                    model.setUploadDate(date);
                } else if(nameValuePair.getName().equals("creator")) {
                    model.setCreator(nameValuePair.getValue());
                }
            }

            model.setData(this.stclient.download_file1(fileName));
            return model;
        }
    }

    public byte[] downloadBinary(String fileName) throws IOException, DFSException {
        return this.stclient.download_file1(fileName);
    }

    public void delete(String fileName) throws IOException, DFSException {
        this.stclient.delete_file1(fileName);
    }

    public Boolean ifExist(String fileName) throws DFSException, IOException {
        return Boolean.valueOf(this.stclient.get_file_info1(fileName) != null);
    }
}
