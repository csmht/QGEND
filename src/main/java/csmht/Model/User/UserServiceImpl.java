package csmht.Model.User;


import com.alibaba.fastjson2.JSON;
import com.mysql.cj.Session;
import csmht.Dao.BaseString;
import csmht.Dao.ClassObject.Post;
import csmht.Dao.ClassObject.User;
import csmht.Dao.ClassObject.UserLike;
import csmht.Dao.JDBC;
import csmht.Dao.Pool;
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
import java.util.Base64;


@WebServlet("/User/Service/*")
public class UserServiceImpl extends UserBaseServlet implements UserService {


    @Override
    public void BrowsePost(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }


    @Override
    public void Like(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException {

        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        UserLike Json = JSON.parseObject(oneLine, UserLike.class);

        HttpSession session = req.getSession();
        Json.setUser_id((Integer) session.getAttribute("id"));

        Connection con = Pool.Pool.getPool();
        ResultSet rs = null;

        try {
            con.setAutoCommit(false);

            String[] main = {"user_like"};
            String[] mun = {};
            String[] key = {"user_id","post_id"};
            String[] value = {Json.getUser_id()+"",Json.getPost_id()+""};

            rs =  JDBC.find(con,main,mun,key,value,"");

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
    public void ChangeUser(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        User Json = JSON.parseObject(oneLine, User.class);
        Connection con = Pool.Pool.getPool();
        ResultSet rs = null;

        try{

            String[] key = {};
            Object[] value = {};
            if(Json == null||Json.getUser_id() == -1){
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            con.setAutoCommit(false);

            String[] key1 = {"user_id"};
            String[] value1 = {Json.getUser_id()+""};

            if(Json.getImage() == null){
                key = new String[]{"name","email"};
                value = new Object[]{String.valueOf(Json.getUser_id()),Json.getEmail()};
            }else {
                key = new String[]{"name","email","image"};
                String jsonImage = Json.getImage();
                byte[] image = csmht.Dao.BaseString.getBase64(jsonImage);

                value = new Object[]{String.valueOf(Json.getUser_id()),Json.getEmail(),image};
            }

            int a = JDBC.update(con,"user",key,value,key1,value1);


            if(a==0){
                con.rollback();
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            con.commit();
        }catch (SQLException e) {
            con.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void FollowBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }

    @Override
    public void UnFollowBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }


}
