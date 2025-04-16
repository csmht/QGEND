package csmht.Dao;

import java.util.Base64;

public class BaseString {

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

}
