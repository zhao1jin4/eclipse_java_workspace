package swagger1x_spring;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseModel implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -5598840566266782861L;

    @JsonProperty("StatusCode")
    private String StatusCode;

    @JsonProperty("Data")
    private Object Data;

    @JsonProperty("ErrorMessage")
    private String ErrorMessage;

    @JsonIgnore
    public String getStatusCode() {
        return StatusCode;
    }

    @JsonIgnore
    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }

    @JsonIgnore
    public Object getData() {
        return Data;
    }

    @JsonIgnore
    public void setData(Object data) {
        Data = data;
    }

    @JsonIgnore
    public String getErrorMessage() {
        return ErrorMessage;
    }

    @JsonIgnore
    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }
}
