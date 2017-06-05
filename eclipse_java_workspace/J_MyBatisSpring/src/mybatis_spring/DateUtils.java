package mybatis_spring;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils
{
    private DateUtils()
    {
        
    }
    /**
     * 因static 方法 线程安全原因不要把 SimpleDateFormat放在类级别
     * @param date
     * @return
     */
    public static String formatWithISO(Date date)
    {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd");
        return isoFormat.format(date);
    }

    public static String formatWith8NO(Date date)
    {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyyMMdd");
        return isoFormat.format(date);
    }
    public static String getCurrentDate()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat shortFormat = new SimpleDateFormat("yyyyMMdd");
        return shortFormat.format(cal.getTime());
    }
    public static String getCurrentDateTime()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat shortFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        return shortFormat.format(cal.getTime());
    }
    
    /**
     * 今天是当月的最后一天吗
     * @return
     */
    public static boolean todayIsLastDayOfMonth()
    {
        Calendar now = Calendar.getInstance();
        int day = now.get(Calendar.DAY_OF_MONTH);
        int maxDay = now.getActualMaximum(Calendar.DAY_OF_MONTH);
        return day == maxDay;
    }
    /**
     * 指定日期 是 当月的最后一天吗
     * @return
     */
    public static boolean isLastDayOfMonth(Date date)
    {
        Calendar now = Calendar.getInstance();
        int maxDay = now.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        Calendar giveCal = Calendar.getInstance();
        giveCal.setTime(date);
        int giveDay = giveCal.get(Calendar.DAY_OF_MONTH);
        return giveDay == maxDay;
    }
}
