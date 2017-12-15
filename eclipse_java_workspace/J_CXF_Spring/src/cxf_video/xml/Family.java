package cxf_video.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Family
{
    @XmlElement(name="theFather")  
    private String father;
    
    @XmlElement(name="theMather")  
    private String mather;

    public String getFather()
    {
        return father;
    }

    public void setFather(String father)
    {
        this.father = father;
    }

    public String getMather()
    {
        return mather;
    }

    public void setMather(String mather)
    {
        this.mather = mather;
    }
 
}
