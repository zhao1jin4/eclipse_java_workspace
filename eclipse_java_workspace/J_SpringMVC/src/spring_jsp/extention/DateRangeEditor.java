package spring_jsp.extention;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 对应于 UI中的daterange 的日期组件
 */

public class DateRangeEditor extends PropertyEditorSupport
{
 
    @Override
    public void setAsText(String dates) throws IllegalArgumentException {
        DateRange range=new DateRange();
        if( "".equals(dates))
        {
            super.setValue(range);
            return ;
        }

        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String startTimeStr = dates.split(" - ")[0];
            String endTimeStr = dates.split(" - ")[1];
            Date beginDate = sdf.parse(startTimeStr);
            Date endDate = sdf.parse(endTimeStr);

            range.setBeginTime(beginDate);
            range.setEndTime(endDate);

            super.setValue(range);
        } catch (ParseException e) {
        	e.printStackTrace();
        }
    }
    @Override
    public String getAsText()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Object o=getValue();
        DateRange range=(DateRange)o;
        if(null!=range && null !=range.getBeginTime() && null !=range.getEndTime() )
            return  sdf.format(range.getBeginTime())+" - " + sdf.format(range.getEndTime());
         else
            return "";
    }
}