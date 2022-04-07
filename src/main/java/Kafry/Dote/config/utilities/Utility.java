package Kafry.Dote.config.utilities;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Common Utility
 * @author  : CheolHwan Ihn
 * @version : 2022-04-07 creation
 */
public class Utility {

    /** Object = String null 일때 String 객체로 리턴 */
    public static String toString(Object obj){
        if(null != obj) return obj.toString();
        else return "";
    }

    /** Object = null 또는 String "" 일때 String 문자로 대체 */
    public static String nullToValue(Object obj1, Object obj2) {
        if(null == obj1 || "".equals(obj1.toString())) return obj2==null?"":obj2.toString();
        else return obj1.toString();
    }

    /** 현재시간을 Timestamp 형태로 리턴
     * - timeZone : Asia/Seoul */
    public static Timestamp getTimestamp() {
        return Utility.getTimestamp("Asia/Seoul");
    }

    /** 현재시간을 format 형태로 리턴 <br>
     * - 입력예 : getTime("yyyyMMddHHmmssSSS")
     * - timeZone : Asia/Seoul */
    public static String getTime(String format) {
        return Utility.getTime(format, "Asia/Seoul");
    }

    /** 입력한 표준시의 현재시간을 Timestamp 형태로 리턴 <br>
     * - 입력예 : getTime("yyyyMMddHHmmssSSS")<br>
     * - timeZone<br>
     *   1.Asia/Seoul : 한국표준시<br>
     *   2.JST : 일본표준시<br>
     *   3.Greenwich : 그리니치표준시<br>
     *   4.America/Los_Angeles : 태평양표준시<br>
     *   5.America/New_York : 동부표준시<br>
     *   6.Pacific/Honolulu : 하와이표준시<br>
     *   7.Asia/Shanghai : 중국표준시 */
    public static Timestamp getTimestamp(String timeZone) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
        return Timestamp.valueOf(formatter.format(new Date().getTime()));
    }

    /** 입력한 표준시의 현재시간을 format 형태로 리턴 <br>
     * - 입력예 : getTime("yyyyMMddHHmmssSSS")<br>
     * - timeZone<br>
     *   1.Asia/Seoul : 한국표준시<br>
     *   2.JST : 일본표준시<br>
     *   3.Greenwich : 그리니치표준시<br>
     *   4.America/Los_Angeles : 태평양표준시<br>
     *   5.America/New_York : 동부표준시<br>
     *   6.Pacific/Honolulu : 하와이표준시<br>
     *   7.Asia/Shanghai : 중국표준시 */
    public static String getTime(String format, String timeZone) {
        SimpleDateFormat formatter = null;
        Calendar calendar = null;
        String rtnTime = "";
        try {
            formatter = new SimpleDateFormat(format);
            formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
            calendar = new GregorianCalendar();
            rtnTime = formatter.format(calendar.getTime());
        } catch(IllegalArgumentException ie) {
            throw ie;
        }
        return rtnTime;
    }

    /** 입력시간을 기준으로 (일, 주, 월, 년) 가감한 시간을 리턴 <br>
     *  일 Calendar.DATE          <br>
     *  주 Calendar.WEEK_OF_YEAR  <br>
     *  월 Calendar.MONTH         <br>
     *  년 Calendar.YEAR          <br>
     *  ex> Utility.getAddDate("20091130", "yyyy-MM-dd", Calendar.MONTH, -3) Return "2009-10-30" <br>
     *  ex> Utility.getAddDate("2009-11-30 12:10:20", "yyyyMMddHHmmss", Calendar.MONTH, -3) Return "20090730121020" <br>
     *  ex> Utility.getAddDate("20091130121020", "yyyy-MM-dd HH:mm:ss", Calendar.MONTH, -3) Return "2009-07-30 12:10:20" */
    public static String getAddTime(String time, int field, int amount, String returnDateFormat) throws Exception {
        Calendar calendar = new GregorianCalendar();
        String replaceTime = time.replaceAll("[^0-9]", "");
        SimpleDateFormat formatter1 = null;
        switch (replaceTime.length()) {
            case 4: formatter1 = new SimpleDateFormat("yyyy"); break;
            case 6: formatter1 = new SimpleDateFormat("yyyyMM"); break;
            case 8: formatter1 = new SimpleDateFormat("yyyyMMdd"); break;
            case 10: formatter1 = new SimpleDateFormat("yyyyMMddHH"); break;
            case 12: formatter1 = new SimpleDateFormat("yyyyMMddHHmm"); break;
            case 14: formatter1 = new SimpleDateFormat("yyyyMMddHHmmss"); break;
            case 17: formatter1 = new SimpleDateFormat("yyyyMMddHHmmssSSS"); break;
        }
        try {
            Date formatDate = formatter1.parse(replaceTime);
            calendar.setTime(formatDate);
        } catch(ParseException pe) {
            throw pe;
        }
        calendar.add(field, amount);
        SimpleDateFormat formatter2 = new SimpleDateFormat(returnDateFormat);
        return formatter2.format(calendar.getTime());
    }
}
