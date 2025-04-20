package csmht.Model.User;


import com.alibaba.fastjson2.JSON;
import csmht.Dao.BaseString;
import csmht.Dao.ClassObject.*;
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
import java.util.Objects;


@WebServlet("/User/Service/*")
public class UserServiceImpl extends UserBaseServlet implements UserService {

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
    public void FollowUser(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException, ParseException {
        FollowUser(req,res,"follow");
    }

    @Override
    public void UnFollowUser(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException, ParseException {
        FollowUser(req,res,"un");
    }

    private void FollowUser(HttpServletRequest req, HttpServletResponse res,String sort) throws SQLException, IOException, InterruptedException, ParseException {
        Follow(req,res,sort,"user");
    }

    private void Follow(HttpServletRequest req, HttpServletResponse res,String sort,String who) throws SQLException, IOException, InterruptedException, ParseException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Post Json = JSON.parseObject(oneLine, Post.class);

        HttpSession session = req.getSession();
        int a  = Integer.parseInt(session.getAttribute("id").toString());
        int likes;

        Json.setCreate_time(BaseString.getTime(Json.getCreate_time()));

        Connection con = Pool.Pool.getPool();
        Connection con2 = Pool.Pool.getPool();
        ResultSet rs = null;


        try {
            con.setAutoCommit(false);
            con2.setAutoCommit(false);

            String who1;
            String[] main;
            String[] mun;
            String[] key;
            String[] key1;
            String[] key2;
            String[] value;
            String[] value1;
            String[] value2;

            if(who.equals("user")){
                //用户
                main = new String[]{"user"};
                mun = new String[]{};
                key = new String[]{"user_id"};
                value = new String[]{Json.getUser_id()+""};
                rs = JDBC.find(con2,main,mun,key,value,"");
                rs.next();
                likes = rs.getInt("user_likes");


                if(sort.equals("follow")){
                    key = new String[]{"follower_id","user_id","create_time"};
                    value = new String[]{Json.getUser_id()+"",a+"", Json.getCreate_time()};
                    value2 = new String[]{likes + 1 + ""};
                }else {
                    key = new String[]{"follower_id","user_id"};
                    value = new String[]{Json.getUser_id()+"",a+""};
                    value2 = new String[]{likes - 1 + ""};
                }

                value1 = new String[]{Json.getUser_id()+""};
                key1 = new String[]{"user_id"};
                key2 = new String[]{"user_likes"};

                who = "user_follow";
                who1 = "user";
            }else{


                main = new String[]{"board"};
                mun = new String[]{};
                key = new String[]{"board_id"};
                value = new String[]{Json.getBoard_id()+""};
                rs = JDBC.find(con2,main,mun,key,value,"");
                rs.next();
                likes = rs.getInt("likes");



                if(sort.equals("follow")){
                    key = new String[]{"board_id","user_id","create_time"};
                    value = new String[]{Json.getBoard_id()+"",a+"", Json.getCreate_time()};
                    value2 = new String[]{likes + 1 + ""};
                }else {
                    key = new String[]{"board_id","user_id"};
                    value = new String[]{Json.getBoard_id()+"",a+""};
                    value2 = new String[]{likes - 1 + ""};
                }

                key1 = new String[]{"board_id"};
                value1 = new String[]{Json.getBoard_id()+""};
                key2 = new String[]{"likes"};
                who = "board_follow";
                who1 = "board";
            }


            if(sort.equals("follow")){
                JDBC.add(con,who,key,value);
            }else {
                JDBC.delete(con,who,key,value);
            }

            JDBC.update(con,who1,key2,value2,key1,value1);

            res.getWriter().write("OK");
            con2.commit();
            con.commit();
        } catch (Exception e) {
            con.rollback();
            con2.rollback();
            e.printStackTrace();
        }finally {
            if (rs != null) {
                rs.close();
            }
            Pool.Pool.returnConn(con2);
            Pool.Pool.returnConn(con);
        }



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

            //验证身份
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
            String[] value = {Json.getPost_id()+""};
            Boolean admin = false;
            int board_id = 0;

            //是否有删除权限
            rs = JDBC.find(con,"SELECT * FROM post WHERE post_id = ?",value);

            if(rs.next()){
                if(rs.getInt("user_id") == a){
                    admin = true;
                }else {
                    board_id = rs.getInt("board_id");
                    value = new String[]{board_id+""};
                    rs = JDBC.find(con,"SELECT * FROM board WHERE board_id = ?",value);
                    if(rs.next()&&rs.getInt("user_id") == a){
                        admin = true;
                    }else {
                        value = new String[]{a+""};
                        rs = JDBC.find(con,"SELECT * FROM user WHERE user_id = ?",value);

                        if(rs.next()&&rs.getBoolean("admin")){
                            admin = true;
                        }

                    }
                }
            }

            if(admin){


                con.setAutoCommit(false);



                key =new String[] {"user_id", "post_id"};
                value =new String[] {Json.getUser_id() + "",Json.getPost_id()+""};

                JDBC.delete(con, "post", key, value);


                key = new String[]{"post_id"};
                String[] value1 = {Json.getPost_id() + ""};
                JDBC.delete(con, "post", key, value1);



                con.commit();

                res.getWriter().write("OK");
            }else {
                res.setStatus(403);
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



            String[] key ;
            String[] value ;





                con.setAutoCommit(false);



                key =new String[] {"user_id","title","content","create_time","image"};
                value =new String[] {Json.getUser_id() + "",Json.getTitle(),Json.getContent(),Json.getCreate_time(),Json.getImage()};

                JDBC.add(con, "board", key, value);


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
    public void DeleteBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Board Json = JSON.parseObject(oneLine, Board.class);

        HttpSession session = req.getSession();
        int a  = Integer.parseInt(session.getAttribute("id").toString());
        Json.setUser_id(a);

        Connection con = Pool.Pool.getPool();
        ResultSet rs ;
        boolean admin = false;

        String[] main = {"board"};
        String[] mun = {};
        String[] key = {"board.user_id"};
        String[] value = {Json.getUser_id()+""};

        //是否有删除权限
        rs = JDBC.find(con,main,mun,key,value,"");

        if(!rs.next()){
            rs = JDBC.find(con,"SELECT * FROM user WHERE user_id = ?;",value);
            if(!rs.next()||!rs.getBoolean("admin")){
                admin = false;
            }else {
                admin = true;
            }
        }

        Connection con1 = Pool.Pool.getPool();
        try{



                con.setAutoCommit(false);
                con1.setAutoCommit(false);

                if(admin) {


                key = new String[]{"board_id"};
                String[] key1 = {"post_id"};
                value = new String[]{Json.getBoard_id() + ""};
                String[] value1;

                JDBC.delete(con, "board_follow", key, value);
                JDBC.delete(con1, "banned_user", key, value);

                rs = JDBC.find(con1, "SELECT * FROM post WHERE board_id = ?;", value);

                while (rs.next()) {
                    value1 = new String[]{rs.getString("post_id")};
                    JDBC.delete(con, "comment", key1, value1);
                    JDBC.delete(con, "comment_comment", key1, value1);
                    JDBC.delete(con, "user_like", key1, value1);
                    JDBC.delete(con, "user_like", key1, value1);
                }

                JDBC.delete(con, "post", key, value);


                JDBC.delete(con, "board", key, value);

                res.getWriter().write("OK");
            }
            con.commit();
            con1.commit();
        }catch (Exception e){

            e.printStackTrace();
            con1.rollback();
            con.rollback();
        }finally {
            if (rs != null) {
                rs.close();
            }
            res.getWriter().close();
            Pool.Pool.returnConn(con);
            Pool.Pool.returnConn(con1);
        }

    }


    @Override
    public void ChangeUser(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        User Json = JSON.parseObject(oneLine, User.class);
        Connection con = Pool.Pool.getPool();


        try{

            String[] key ;
            Object[] value ;
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
                byte[] image = BaseString.getBase64(jsonImage);

                value = new Object[]{String.valueOf(Json.getUserName()),Json.getEmail(),image};
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
    public void isFollowUser(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException, ParseException {
            isFollow(req,res,"user");
    }

    @Override
    public void isFollowBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException, ParseException {
            isFollow(req,res,"board");
    }



    public void isFollow(HttpServletRequest req, HttpServletResponse res,String sort) throws SQLException, IOException, InterruptedException, ParseException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Post Json = JSON.parseObject(oneLine, Post.class);

        HttpSession session = req.getSession();
        int a  = Integer.parseInt(session.getAttribute("id").toString());




        Connection con = Pool.Pool.getPool();
        ResultSet rs = null;

        try{
            String[] main;
            String[] mun = {};

            String[] key ;
            String[] value ;

            if(Objects.equals(sort, "user")){
                main = new String[]{"user_follow"};
                key = new String[]{"user_id","follower_id"};
                value = new String[]{a+"",Json.getUser_id()+""};
            }else {
                main = new String[]{"board_follow"};
                key = new String[]{"board_id","user_id"};
                value = new String[]{Json.getBoard_id()+"",a+""};
            }

            rs = JDBC.find(con,main,mun,key,value,"");


            if(rs.next()){
                res.getWriter().write("true");
            }else {
                res.getWriter().write("false");
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (rs != null) {
                rs.close();
            }
            Pool.Pool.returnConn(con);
            res.getWriter().close();
        }

    }

    @Override
    public void FollowBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, ParseException, InterruptedException {
        FollowBoard(req,res,"follow");
    }

    @Override
    public void UnFollowBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, ParseException, InterruptedException {
        FollowBoard(req,res,"un");
    }

    private void FollowBoard(HttpServletRequest req, HttpServletResponse res,String sort) throws SQLException, IOException, ParseException, InterruptedException {
        Follow(req,res,sort,"board");
    }



    @Override
    public void isBoardAdmin(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException, ParseException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Board Json = JSON.parseObject(oneLine, Board.class);

        HttpSession session = req.getSession();
        int a  = Integer.parseInt(session.getAttribute("id").toString());

        Connection con = Pool.Pool.getPool();
        ResultSet rs = null;

        try{


            String[] main = {"board"};
            String[] mun = {};
            String[] key  = {"user_id","board_id"};
            String[] value = {a+"",Json.getBoard_id()+""};

            rs = JDBC.find(con,main,mun,key,value,"");

            if(rs.next()){
                res.getWriter().write("true");
            }else {
                res.getWriter().write("false");
            }



        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (rs != null) {
                rs.close();
            }
            Pool.Pool.returnConn(con);
            res.getWriter().close();
        }

    }

    @Override
    public void UpdateBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException, ParseException {

        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Post Json = JSON.parseObject(oneLine, Post.class);

        HttpSession session = req.getSession();
        int a  = Integer.parseInt(session.getAttribute("id").toString());

        Connection con = Pool.Pool.getPool();
        ResultSet rs = null;




        boolean admin = false;

        String[] main = {"board"};
        String[] mun = {};
        String[] key = {"board.user_id"};
        String[] value = {a+""};

        //是否有修改权限
        rs = JDBC.find(con,main,mun,key,value,"");

        if(rs.next()){
            rs = JDBC.find(con,"SELECT * FROM user WHERE user_id = ?;",value);
            if(!rs.next()||!rs.getBoolean("admin")){
                value = new String[]{a+"",Json.getBoard_id()+""};
                rs = JDBC.find(con,"SELECT * FROM board WHERE user_id = ? and board_id = ?;",value);

                if(rs.next()){
                    admin = true;
                }else {
                    admin = false;
                }

            }else {
                admin = true;
            }
        }

        try {
            con.setAutoCommit(false);

            if(admin){
           main =new String[]  {"board_id"};
            mun =new String[]  {Json.getBoard_id()+""};
            Object[] op ;
            if(Json.getImage()!=null){
                key =new String[]{"image","title","content"};
                String jsonImage = Json.getImage();
                byte[] image = BaseString.getBase64(jsonImage);
               op =new Object[] {image,Json.getTitle(),Json.getContent()};
            }else{
                key =new String[]{"title","content"};
                op = new Object[] {Json.getTitle(),Json.getContent()};
            }


            JDBC.update(con,"board",key,op,main,mun);




            }

            con.commit();
            res.getWriter().write("OK");
        }catch (Exception e){
            e.printStackTrace();
            con.rollback();


        }finally {
            if (rs != null) {
                rs.close();
            }res.getWriter().close();
            Pool.Pool.returnConn(con);
        }
    }

    @Override
    public void FindBrowsePost(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException, ParseException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Post Json = JSON.parseObject(oneLine, Post.class);

        HttpSession session = req.getSession();
        int a  = Integer.parseInt(session.getAttribute("id").toString());

        Connection con = Pool.Pool.getPool();
        ResultSet rs = null;




        boolean admin = false;

        String[] main = {"board"};
        String[] mun = {};
        String[] key = {"board.user_id"};
        String[] value = {a+""};

        //是否有修改权限
        rs = JDBC.find(con,main,mun,key,value,"");

        if(rs.next()){
            rs = JDBC.find(con,"SELECT * FROM user WHERE user_id = ?;",value);
            if(!rs.next()||!rs.getBoolean("admin")){
                value = new String[]{a+"",Json.getBoard_id()+""};
                rs = JDBC.find(con,"SELECT * FROM board WHERE user_id = ? and board_id = ?;",value);

                if(rs.next()){
                    admin = true;
                }else {
                    admin = false;
                }

            }else {
                admin = true;
            }
        }

            try {


                con.setAutoCommit(false);

                if(admin) {








                }
                con.commit();

            } catch (Exception e) {
                e.printStackTrace();
                con.rollback();
            } finally {
                if (rs != null) {
                    rs.close();
                }
                Pool.Pool.returnConn(con);
            }


    }


    @Override
    public void addCommentToPost(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException, ParseException {
        addComment(req,res,"post");
    }

    @Override
    public void addCommentToComment(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException, ParseException {
        addComment(req,res,"comment");
    }

    public void addComment(HttpServletRequest req, HttpServletResponse res,String to) throws SQLException, IOException, InterruptedException, ParseException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Comment Json = JSON.parseObject(oneLine, Comment.class);
        Comment ans ;
        Json.setCreate_time(BaseString.getTime("1000-01-01 12:12:12"));

        HttpSession session = req.getSession();
        int a  = Integer.parseInt(session.getAttribute("id").toString());

        Connection con = Pool.Pool.getPool();
        ResultSet rs = null;

        try{
            con.setAutoCommit(false);

            String[] main = {"comment"};
            String[] mun = {};
            String[] key = {"user_id","post_id","content","create_time"};
            String[] value = {a+"",Json.getPost_Id()+"",Json.getContent(),Json.getCreate_time()};

            JDBC.add(con,"comment",key,value);

            if(to.equals("comment")){
                int id;
                rs = JDBC.find(con,main,mun,key,value,"");

                if(rs.next()){
                    id = rs.getInt("comment_id");

                    key = new String[]{"comment1_id","comment2_id","post_id"};
                    value = new String[]{Json.getComment_Id()+"",id+"",Json.getPost_Id()+""};
                    JDBC.add(con,"comment_comment",key,value);

                    String[] aa = {"comment1_id"};
                    String[] bb = {id+""};
                    String[] cc = {"comment_id"};
                    String[] dd = {Json.getComment_Id()+""};

                    JDBC.update(con,"comment",aa,bb,cc,dd);
                }


            }
            con.commit();
            res.getWriter().write("OK");

        } catch (Exception e) {
            e.printStackTrace();
            con.rollback();
            res.sendError(403);
        }finally {
            if (rs != null) {
                rs.close();
            }
            Pool.Pool.returnConn(con);
            res.getWriter().close();
        }

    }




    @Override
    public void BrowsePost(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, ParseException, InterruptedException {
       Browse(req,res,"post");
    }

    @Override
    public void BrowseBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, ParseException, InterruptedException {
        Browse(req,res,"board");
    }

    @Override
    public void BrowseUser(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, ParseException, InterruptedException {
        Browse(req,res,"user");
    }

    private void Browse(HttpServletRequest req, HttpServletResponse res,String sort) throws SQLException, IOException, InterruptedException, ParseException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Post Json = JSON.parseObject(oneLine, Post.class);
        Json.setCreate_time(BaseString.getTime("1000-01-01 12:12:12"));

        HttpSession session = req.getSession();
        int id  = Integer.parseInt(session.getAttribute("id").toString());

        Connection con = Pool.Pool.getPool();
        ResultSet rs = null;


        try {
            con.setAutoCommit(false);

            String[] main ;
            String[] mun ;
            String[] key = {"who","user_id","content","create_time"};
            String[] value = {"who",id+"",Json.getContent(),Json.getCreate_time()};

            if(sort.equals("post")){
               key[0] = "post_id";
                value[0] = String.valueOf(Json.getPost_id());

            }else if(sort.equals("board")){
                key[0] = "board_id";
                value[0] = String.valueOf(Json.getBoard_id());

            }else{
                key[0] = "reported_user_id";
                value[0] = String.valueOf(Json.getUser_id());

            }

            JDBC.add(con,"report",key,value);


            res.getWriter().write("OK");





            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
            res.sendError(300);
            con.rollback();
        }finally {
            if (rs != null) {
                rs.close();
            }
            Pool.Pool.returnConn(con);
        }



    }


    @Override
    public void DeleteCommentToPost(HttpServletRequest req, HttpServletResponse res) throws SQLException {

    }

}
