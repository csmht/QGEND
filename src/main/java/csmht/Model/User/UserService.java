package csmht.Model.User;


import com.alibaba.fastjson2.JSON;
import csmht.Dao.ClassObject.User;
import csmht.Dao.ClassObject.UserLike;
import csmht.Dao.JDBC;
import csmht.Dao.Pool;
import csmht.Dao.UserClass;
import csmht.View.UserBaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


@WebServlet("/User/*")
public class UserService extends UserBaseServlet implements csmht.Model.UserService {


    @Override
    public void UserBrowsePost(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }


    @Override
    public void UserLike(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException {

        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        UserLike Json = JSON.parseObject(oneLine, UserLike.class);

        Connection con = Pool.Pool.getPool();


        try {
            con.setAutoCommit(false);

            ResultSet rs = JDBC.find(con, new String[]{"user_like"},new String[]{},new String[]{"user_id","post_id"},new String[]{Json.getUser_id()+"",Json.getPost_id()+""},"");

            if(!rs.next()) {
                rs = JDBC.find(con,"SELECT post* FROM post WHERE post_id = '?'",new String[]{Json.getPost_id()+""});
                JDBC.update(con,"post",new String[]{"likes"},new String[]{String.valueOf((rs.getInt("likes")+1))},new String[]{"post_id"},new String[]{Json.getPost_id()+""});
                JDBC.add(con,"user_like",new String[]{"user_id","post_id"},new String[]{Json.getUser_id()+"",Json.getPost_id()+""});
            }

            con.commit();

        } catch (SQLException e) {
            con.rollback();
            e.printStackTrace();
//            throw new RuntimeException(e);
        }finally {
            Pool.Pool.returnConn(con);
        }


    }

    @Override
    public void UserUnLike(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }

    @Override
    public void UserFollowUser(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }

    @Override
    public void UserUnFollowUser(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }


    @Override
    public void UserBrowseBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }

    @Override
    public void UserAddPost(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }

    @Override
    public void UserDeletePost(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }

    @Override
    public void UserAddBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }

    @Override
    public void UserDeleteBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }

    @Override
    public void UserAddComment(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }

    @Override
    public void UserDeleteComment(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }

    @Override
    public void UserFollowBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }

    @Override
    public void UserUnFollowBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }


}
