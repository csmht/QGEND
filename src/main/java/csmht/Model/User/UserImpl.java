package csmht.Model.User;

import com.alibaba.fastjson2.JSON;
import csmht.Dao.JDBC;
import csmht.Dao.Pool;
import csmht.Model.UserService;
import csmht.View.UserBaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


@WebServlet("/UserIn/*")
public class UserImpl extends UserBaseServlet implements UserService {

    @Override
    public void UserLogin(HttpServletRequest req, HttpServletResponse res) throws SQLException, InterruptedException, IOException {

        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        User Json = JSON.parseObject(oneLine, csmht.Model.User.User.class);
        Connection con = Pool.Pool.getPool();
        String[] a = {"user"};
        String[] b = {};
        String[] c = {"id", "password"};
        String[] d = {Json.getUserID(),Json.getPassword()};
        ResultSet rs = JDBC.find(con,a,b,c,d);
        if(rs.next()) {
            res.getWriter().write("true");
            HttpSession session = req.getSession();
            session.setAttribute("id",rs.getString("id"));
        }else {
            res.getWriter().write("false");
        }



    }

    @Override
    public void UserRegister(HttpServletRequest req, HttpServletResponse res) throws SQLException, InterruptedException, IOException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        User Json = JSON.parseObject(oneLine, csmht.Model.User.User.class);
        Connection con = Pool.Pool.getPool();
        boolean pd = false;
        try{
            con.setAutoCommit(false);
            String[] a = {"user"};
            String[] b = {};
            String[] c = {"id","password","email"};
            String[] d = {Json.getUserID(),Json.getPassword(),Json.getEmail()};
            ResultSet rs = JDBC.find(con,a,b,c,d);
            if(rs.next()) {
                res.getWriter().write("more");
                return;
            }
            String[] e = {"id", "password","email","name"};
            String[] f = {Json.getUserID(),Json.getPassword(),Json.getEmail(),Json.getUserID()};
            int i = JDBC.add(con,"user",e,f);
            con.commit();
            pd = true;
        }catch(SQLException e) {
            con.rollback();
            e.printStackTrace();
        }finally {
            res.getWriter().write(pd+"");
        }

    }


}
