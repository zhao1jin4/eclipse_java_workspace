//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package spring_fdfs;

import java.io.Serializable;
import java.util.Date;

public class FileModel implements Serializable {
    private static final long serialVersionUID = 5466045325683312547L;
    private String fileName;
    private String extName;
    private Long size;
    private String md5;
    private String contentType;
    private byte[] data;
    private Date uploadDate;
    private String creator;

    public FileModel() {
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtName() {
        return this.extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public Long getSize() {
        return this.size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Date getUploadDate() {
        return this.uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String toString() {
        return "fileName=" + this.fileName + ",extName=" + this.extName + ",size=" + this.size + ",md5=" + this.md5 + ",contentType=" + this.contentType + ",uploadDate=" + this.uploadDate.toString() + ",creator=" + this.creator;
    }
}
