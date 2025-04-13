package csmht.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public interface UserLoginService {

   public void UserLogin(HttpServletRequest req, HttpServletResponse res) throws SQLException, InterruptedException, IOException;

   public void UserRegister(HttpServletRequest req, HttpServletResponse res) throws SQLException, InterruptedException, IOException;

}
