package swagger;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
public class RequestModel     {

    @JsonProperty("StartTime")
    private Date startTime; 
    
    @JsonProperty("Status")
   	private  String status;
           
    @JsonProperty("ID")
	private  Long id;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
  
 
}
