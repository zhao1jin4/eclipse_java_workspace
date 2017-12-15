package jax_rs_spring2;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class JsonBean {

 private Integer command;

 private Integer protocolVersion;

 private String  platformType;

 private Map<String, Object> param;

 public Integer getCommand() {

  return command;

 }

 public void setCommand(Integer command) {

  this.command = command;

 }

 public Integer getProtocolVersion() {

     return protocolVersion;

 }

 public void setProtocolVersion(Integer protocolVersion) {

     this.protocolVersion = protocolVersion;

 }

 public String getPlatformType() {

     return platformType;

 }

 public void setPlatformType(String platformType) {

    this.platformType = platformType;

 }

 public Map<String, Object> getParam() {

     return param;

 }

 public void setParam(Map<String, Object> param) {

     this.param = param;

 }

 }