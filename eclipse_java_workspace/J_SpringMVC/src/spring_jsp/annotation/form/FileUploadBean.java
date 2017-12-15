package spring_jsp.annotation.form;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadBean 
{
	private String description;
	private Date birthday;
	private List<MultipartFile> photos; 
	public List<MultipartFile> getPhotos() {
		return photos;
	}
	public void setPhotos(List<MultipartFile> photos) {
		this.photos = photos;
	}
	
	
	//---
	private MultipartFile img; //OK
	
	public MultipartFile getImg() {
		return img;
	}
	public void setImg(MultipartFile img) {
		this.img = img;
	}
	/*
	//-----
	private byte[] img; //OK,Òª ByteArrayMultipartFileEditor ×ªµÄ
	public byte[] getImg()
	{
		return img;
	}

	public void setImg(byte[] img)
	{
		this.img = img;
	}
	*/
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public Date getBirthday()
	{
		return birthday;
	}
	public void setBirthday(Date birthday)
	{
		this.birthday = birthday;
	}


}
