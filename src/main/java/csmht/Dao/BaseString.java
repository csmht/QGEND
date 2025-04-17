package csmht.Dao;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;

public class BaseString {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
    public BaseString bassString = new BaseString();
    
    public static byte[] getBase64(String imageBase64) {
        if (imageBase64.contains("data:")) {
            int start = imageBase64.indexOf(",");
            imageBase64 = imageBase64.substring(start + 1);
        }
        final Base64.Decoder decoder = Base64.getDecoder();
        imageBase64 = imageBase64.replaceAll("\r|\n", "");
        imageBase64 = imageBase64.trim();
        return decoder.decode(imageBase64);
    }

    public static String getTime(String time) throws ParseException {



        String ans ;
        if(time!=null) {
            Date targetDate = sdf.parse(time);
            Date currentDate = new Date();

            if (targetDate.after(currentDate)) {
                ans = sdf.format(targetDate);
            } else {
                ans  = sdf.format(currentDate);
            }
        }else {
            ans = sdf.format(new Date());
        }

        return ans;
    }

}
