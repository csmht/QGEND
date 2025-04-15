package csmht.Model.User;


import com.alibaba.fastjson2.JSON;
import csmht.Dao.ClassObject.UserLike;
import csmht.Dao.JDBC;
import csmht.Dao.Pool;
import csmht.View.UserBaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


@WebServlet("/User/Service/")
public class UserServiceImpl extends UserBaseServlet implements UserService {


    @Override
    public void BrowsePost(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }


    @Override
    public void Like(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException {

        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        UserLike Json = JSON.parseObject(oneLine, UserLike.class);

        Connection con = Pool.Pool.getPool();
        ResultSet rs = null;

        try {
            con.setAutoCommit(false);

            rs =  JDBC.find(con, new String[]{"user_like"},new String[]{},new String[]{"user_id","post_id"},new String[]{Json.getUser_id()+"",Json.getPost_id()+""},"");

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
            rs.close();
            Pool.Pool.returnConn(con);
        }


    }

    @Override
    public void UnLike(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }

    @Override
    public void FollowUser(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }

    @Override
    public void UnFollowUser(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }


    @Override
    public void BrowseBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }

    @Override
    public void AddPost(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }

    @Override
    public void DeletePost(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }

    @Override
    public void AddBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }

    @Override
    public void DeleteBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }

    @Override
    public void AddCommentToPost(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }

    @Override
    public void DeleteCommentToPost(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }

    @Override
    public void FollowBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }

    @Override
    public void UnFollowBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }


}
