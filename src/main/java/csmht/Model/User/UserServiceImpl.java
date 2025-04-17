package csmht.Model.User;


import com.alibaba.fastjson2.JSON;
import com.mysql.cj.Session;
import csmht.Dao.BaseString;
import csmht.Dao.ClassObject.Board;
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
import java.text.ParseException;
import java.util.Base64;
import java.util.List;


@WebServlet("/User/Service/*")
public class UserServiceImpl extends UserBaseServlet implements UserService {


    @Override
    public void BrowsePost(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }



    private void Like(HttpServletRequest req, HttpServletResponse res,String sort) throws SQLException, InterruptedException, IOException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        UserLike Json = JSON.parseObject(oneLine, UserLike.class);

        HttpSession session = req.getSession();
        int a  = Integer.parseInt(session.getAttribute("id").toString());
        Json.setUser_id(a);

        Connection con = Pool.Pool.getPool();
        ResultSet rs = null;

        try {
            con.setAutoCommit(false);

            String[] main = {"user_like"};
            String[] mun = {};
            String[] key = {"user_id","post_id"};
            String[] value = {Json.getUser_id()+"",Json.getPost_id()+""};

            rs =  JDBC.find(con,main,mun,key,value,"");

            //返回是否喜欢
            if(sort.equals("is")){
                if(rs.next()){
                    res.getWriter().write("like");
                }else {
                    res.getWriter().write("unlike");
                }

            }else {


                if ((rs.next()&&sort.equals("unlike"))||(!rs.next()&&sort.equals("like"))) {

                    rs = JDBC.find(con, "SELECT * FROM post WHERE post_id = ?", new String[]{Json.getPost_id() + ""});
                    if (rs.next()) {
                        String[] key1 = {"likes"};


                        String[] value1 = {String.valueOf((rs.getInt("likes") + 1))};
                        if (sort.equals("unlike")) {
                            value1 = new String[]{String.valueOf((rs.getInt("likes") - 1))};
                        }

                        key = new String[]{"post_id"};
                        value = new String[]{Json.getPost_id() + ""};

                        JDBC.update(con, "post", key1, value1, key, value);

                        key = new String[]{"user_id", "post_id"};
                        value = new String[]{Json.getUser_id() + "", Json.getPost_id() + ""};


                        if (sort.equals("unlike")) {
                            JDBC.delete(con, "user_like", key, value);
                        } else {
                            JDBC.add(con, "user_like", key, value);
                        }

                    }
                    res.setStatus(200);
                }

            }

            con.commit();
        } catch (SQLException e) {
            con.rollback();
            e.printStackTrace();

        }finally {
            if (rs != null) {
                rs.close();
            }
            Pool.Pool.returnConn(con);
        }




    }


    @Override
    public void Like(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException {
        Like(req,res,"like");
    }

    @Override
    public void isLike(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException {
        Like(req,res,"is");
    }

    @Override
    public void UnLike(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException {
        Like(req,res,"unlike");
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
    public void AddPost(HttpServletRequest req, HttpServletResponse res) throws SQLException, InterruptedException, IOException, ParseException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Post Json = JSON.parseObject(oneLine, Post.class);

        HttpSession session = req.getSession();
        int a  = Integer.parseInt(session.getAttribute("id").toString());
        Json.setUser_id(a);

        Json.setCreate_time(BaseString.getTime(Json.getCreate_time()));


        Connection con = Pool.Pool.getPool();
        ResultSet rs = null;

        try{

            String[] main = {"banned_user"};
            String[] mun = {};
            String[] key = {"user_id","board_id"};
            String[] value = {Json.getUser_id()+"",Json.getBoard_id()+""};

            rs = JDBC.find(con,main,mun,key, value,"");

            if(rs.next()){
                    res.getWriter().write("ban");
            }else {
                Object[] value1;

                con.setAutoCommit(false);

                if(Json.getImage()!=null){
                    String image = Json.getImage();

                    byte[] imageByte = BaseString.getBase64(image);



                    key = new String[]{"user_id", "board_id", "title", "content", "create_time","image"};
                    value1 = new Object[]{Json.getUser_id() + "", Json.getBoard_id() + "", Json.getTitle(), Json.getContent(), Json.getCreate_time(),imageByte};


                }else {

                    key = new String[]{"user_id", "board_id", "title", "content", "create_time"};
                    value1 = new Object[]{Json.getUser_id() + "", Json.getBoard_id() + "", Json.getTitle(), Json.getContent(), Json.getCreate_time()};
                }
                JDBC.add(con, "post", key, value1);







                con.commit();

                res.getWriter().write("OK");
            }

        }catch (Exception e){

            e.printStackTrace();
            con.rollback();
        }finally {
            if (rs != null) {
                rs.close();
            }
            res.getWriter().close();
            Pool.Pool.returnConn(con);
        }


    }

    @Override
    public void DeletePost(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException, ParseException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Post Json = JSON.parseObject(oneLine, Post.class);

        HttpSession session = req.getSession();
        int a  = Integer.parseInt(session.getAttribute("id").toString());
        Json.setUser_id(a);

        Json.setCreate_time(BaseString.getTime(Json.getCreate_time()));

        Connection con = Pool.Pool.getPool();
        ResultSet rs = null;

        try{

            String[] main = {"post","board"};
            String[] mun = {"post.user_id","board.user_id"};
            String[] key = {"post.user_id","board.user_id"};
            String[] value = {Json.getUser_id()+"",Json.getUser_id()+""};


            //是否有删除权限
            rs = JDBC.find(con,main,mun,key,value,"");

            if(!rs.next()){
                res.setStatus(403);
            }else {


                con.setAutoCommit(false);



                key =new String[] {"user_id", "post_id"};
                value =new String[] {Json.getUser_id() + "",Json.getPost_id()+""};

                JDBC.delete(con, "post", key, value);


                key = new String[]{"post_id"};
                String[] value1 = {Json.getPost_id() + ""};
                JDBC.delete(con, "user_like", key, value1);



                con.commit();

                res.getWriter().write("OK");
            }

        }catch (Exception e){

            e.printStackTrace();
            con.rollback();
        }finally {
            if (rs != null) {
                rs.close();
            }
            res.getWriter().close();
            Pool.Pool.returnConn(con);
        }
    }

    @Override
    public void AddBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, ParseException, InterruptedException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Board Json = JSON.parseObject(oneLine, Board.class);

        HttpSession session = req.getSession();
        int a  = Integer.parseInt(session.getAttribute("id").toString());
        Json.setUser_id(a);

        Json.setCreate_time(BaseString.getTime(Json.getCreate_time()));

        Connection con = Pool.Pool.getPool();
        ResultSet rs = null;

        try{

            String[] main = {};
            String[] mun = {};
            String[] key = {};
            String[] value = {Json.getUser_id()+""};





                con.setAutoCommit(false);



                key =new String[] {"user_id","title","content","create_time","image"};
                value =new String[] {Json.getUser_id() + "",Json.getBoard_id()+"",Json.getTitle(),Json.getContent(),Json.getCreate_time(),Json.getImage()};

                JDBC.add(con, "post", key, value);


                con.commit();

                res.getWriter().write("OK");


        }catch (Exception e){

            e.printStackTrace();
            con.rollback();
        }finally {
            if (rs != null) {
                rs.close();
            }
            res.getWriter().close();
            Pool.Pool.returnConn(con);
        }
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
        }finally {
            Pool.Pool.returnConn(con);

        }
    }

    @Override
    public void FollowBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }

    @Override
    public void UnFollowBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }


}
