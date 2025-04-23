package csmht.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Captcha {

    public static Boolean isCaptcha(HttpServletRequest req, HttpServletResponse resp,String captcha) {
        boolean answer = false;
        HttpSession session = req.getSession();
        String pd = (String) session.getAttribute("captcha");

        if(captcha.equalsIgnoreCase(pd)) {
            answer = true;
        }


        return answer;
    }
}
