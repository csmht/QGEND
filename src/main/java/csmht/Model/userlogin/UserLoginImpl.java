package csmht.Model.userlogin;

import com.alibaba.fastjson2.JSON;
import csmht.dao.classobject.User;
import csmht.dao.JDBC;
import csmht.dao.Pool;
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
public class UserLoginImpl extends UserBaseServlet implements UserLoginService {

    @Override
    public void UserLogin(HttpServletRequest req, HttpServletResponse res) throws SQLException, InterruptedException, IOException {

        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        User Json = JSON.parseObject(oneLine, User.class);
        Connection con = Pool.Pool.getPool();
        String[] a = {"user"};
        String[] b = {};
        String[] c = {"email", "password"};
        String[] d = {Json.getEmail(),Json.getPassword()};


        ResultSet rs = JDBC.find(con,a,b,c,d,"");
        if(rs.next()) {
            res.getWriter().write("true");
            HttpSession session = req.getSession();
            session.setMaxInactiveInterval(1800);
            session.setAttribute("id",rs.getString("user_id"));
            session.setAttribute("admin",rs.getString("admin"));

        }else {
            res.getWriter().write("false");
        }
        rs.close();
        Pool.Pool.returnConn(con);

    }

    @Override
    public void UserRegister(HttpServletRequest req, HttpServletResponse res) throws SQLException, InterruptedException, IOException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        User Json = JSON.parseObject(oneLine, User.class);
        Connection con = Pool.Pool.getPool();
        ResultSet rs = null;

        boolean pd = false;
        try{
            con.setAutoCommit(false);
            String[] a = {"user"};
            String[] b = {};
            String[] c = {"password","email"};
            String[] d = {Json.getPassword(),Json.getEmail()};
            rs = JDBC.find(con,a,b,c,d,"");
            if(rs.next()) {
                res.getWriter().write("more");
                return;
            }
            String[] e = {"password","email","name"};
            String[] f = {Json.getPassword(),Json.getEmail(),Json.getEmail()};
            JDBC.add(con,"user",e,f);
            con.commit();
            pd = true;
        }catch(SQLException e) {
            con.rollback();
            e.printStackTrace();
        }finally {
            if (rs != null) {
                rs.close();
            }
            Pool.Pool.returnConn(con);
            res.getWriter().write(pd+"");
        }

    }


}
