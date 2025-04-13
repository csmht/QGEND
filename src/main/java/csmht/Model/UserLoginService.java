package csmht.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public interface UserLoginService {
   /**
    * 用户登录
    * @param req
    * @param res
    * @throws SQLException
    * @throws InterruptedException
    * @throws IOException
    */
   public void UserLogin(HttpServletRequest req, HttpServletResponse res) throws SQLException, InterruptedException, IOException;


   /**
    * 用户注册
    * @param req
    * @param res
    * @throws SQLException
    * @throws InterruptedException
    * @throws IOException
    */
   public void UserRegister(HttpServletRequest req, HttpServletResponse res) throws SQLException, InterruptedException, IOException;

}
