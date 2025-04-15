package csmht.Model.FindData;

import com.alibaba.fastjson2.JSON;
import csmht.Dao.ClassObject.*;
import csmht.Dao.JDBC;
import csmht.Dao.Pool;
import csmht.View.UserBaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static csmht.Dao.Constant.Hot;
import static csmht.Dao.Constant.New;


@WebServlet("/User/Find/*")
public class FindDataImpl extends UserBaseServlet implements FindDataService {


    private void Boar(HttpServletRequest req, HttpServletResponse resp, String key0, String sort) throws IOException, SQLException, InterruptedException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Board Json = JSON.parseObject(oneLine, Board.class);
        Connection con = Pool.Pool.getPool();

        List<Board> board = new ArrayList<Board>();
        ResultSet rs = null;
        try {
            con.setAutoCommit(false);

            String[] main = {"board", "user"};
            String[] hot = {"board.user_id", "user.user_id"};


            String[] key = null;
            String[] value = null;

            key = new String[]{key0};
            if (Json == null || Json.getTitle() == null) {
                key = new String[]{};
                value = new String[]{};
            } else if (key0.equals("title")) {
                value = new String[]{"'%" + Json.getTitle() + "'%"};
            } else if (key0.equals("user.name")) {
                value = new String[]{Json.getUserName() + "%"};
            } else if (key0.equals("board_id")) {
                value = new String[]{String.valueOf(Json.getBoard_id())};
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

            rs = JDBC.find(con, main, hot, key, value, sort);

            while (rs.next()) {
                Board tow = new Board();
                tow = csmht.Dao.Find.FindBoard(con, "board_id", rs.getString("board_id"), sort);
                board.add(tow);
            }

            con.commit();
        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        String json = JSON.toJSONString(board);
        PrintWriter writer = resp.getWriter();
        writer.write(json);
        writer.close();
    }

    private void UserManyBoar(HttpServletRequest req, HttpServletResponse resp, String aNew) throws IOException, SQLException, InterruptedException {
        Boar(req, resp, "user.name", aNew);
    }

    private void NameManyBoar(HttpServletRequest req, HttpServletResponse resp, String hot2) throws IOException, SQLException, InterruptedException {
        Boar(req, resp, "title", hot2);
    }

    @Override
    public void OneBoard(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Board Json = JSON.parseObject(oneLine, Board.class);
        Connection con = Pool.Pool.getPool();

        Board board = new Board();
        ResultSet rs = null;
        try {
            con.setAutoCommit(false);

            String[] main = {"board", "user"};
            String[] hot = {"board.user_id", "user.user_id"};


            String[] key = null;
            String[] value = null;


            if (Json == null || Json.getBoard_id() == -1) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }  else {
                key = new String[]{"board_id"};
                value = new String[]{String.valueOf(Json.getBoard_id())};
            }

            rs = JDBC.find(con, main, hot, key, value,Hot);

            if (rs.next()) {
                Board tow = new Board();
                tow = csmht.Dao.Find.FindBoard(con, "board_id", rs.getString("board_id"),Hot);
                board = tow;
            }


            main = new String[]{"board","post"};
            hot = new String[]{"board.board_id", "post.board_id"};
            key = new String[]{"post.board_id"};
            value = new String[]{String.valueOf(Json.getBoard_id())};

            rs = JDBC.find(con,main,hot,key,value,"ORDER BY post.likes DESC");

            while (rs.next()) {
                Post tow = new Post();
                tow = csmht.Dao.Find.FindPost(con, "post_id", rs.getString("post_id"),Hot);
                board.addPost(tow);
            }

            con.commit();
        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        String json = JSON.toJSONString(board);
        PrintWriter writer = resp.getWriter();
        writer.write(json);
        writer.close();
    }

    @Override
    public void UserManyBoardHot(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, InterruptedException {
        UserManyBoar(req, resp, Hot);
    }

    @Override
    public void UserManyBoardNew(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException {
        UserManyBoar(req, resp, New);
    }


    @Override
    public void NameManyBoardHot(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, InterruptedException {
        NameManyBoar(req, resp, Hot);
    }


    @Override
    public void NameManyBoardNew(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException {
        NameManyBoar(req, resp, New);
    }

    private void Post(HttpServletRequest req, HttpServletResponse resp ,String key0, String sort) throws SQLException, IOException, InterruptedException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Post Json = JSON.parseObject(oneLine, Post.class);
        Connection con = Pool.Pool.getPool();

        List<Post> post = new ArrayList<Post>();
        ResultSet rs = null;
        try {
            con.setAutoCommit(false);

            String[] main = {"post", "user"};
            String[] hot = {"post.user_id", "user.user_id"};


            String[] key = null;
            String[] value = null;
            key = new String[]{key0};
            if (Json == null || Json.getTitle() == null) {
                key = new String[]{};
                value = new String[]{};
            } else if (key0.equals("title")) {

                value = new String[]{"%" + Json.getTitle() + "%"};
            } else if (key0.equals("user.name")) {

                value = new String[]{"%" + Json.getUser_id() + "%"};
            } else if (key0.equals("post_id")) {

                value = new String[]{String.valueOf(Json.getPost_id())};
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            rs = JDBC.find(con, main, hot, key, value, sort);

            while (rs.next()) {
                Post tow = new Post();
                tow = csmht.Dao.Find.FindPost(con, "post_id", rs.getString("post_id"), sort);
                post.add(tow);
            }

            con.commit();
        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        String json = JSON.toJSONString(post);
        PrintWriter writer = resp.getWriter();
        writer.write(json);
        writer.close();
    }

    private void TitleManyPost(HttpServletRequest req, HttpServletResponse resp,String sort) throws IOException, SQLException, InterruptedException {
        Post(req, resp, "title", sort);
    }

    private void UserManyPost(HttpServletRequest req, HttpServletResponse resp, String sort) throws IOException, SQLException, InterruptedException {
        Post(req,resp,"user.name", sort);
    }

    @Override
    public void UserManyPostHot(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, InterruptedException {
        UserManyPost(req,resp,Hot);
    }

    @Override
    public void UserManyPostNew(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException {
        UserManyPost(req,resp,New);
    }

    @Override
    public void TitleManyPostHot(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, InterruptedException {
        TitleManyPost(req,resp,Hot);
    }

    @Override
    public void TitleManyPostNew(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException {
        TitleManyPost(req,resp,New);
    }

    @Override
    public void OnePost(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Post Json = JSON.parseObject(oneLine, Post.class);
        Connection con = Pool.Pool.getPool();

        Post post = new Post();
        ResultSet rs = null;
        try {
            con.setAutoCommit(false);

            String[] main = {"post", "user"};
            String[] hot = {"post.user_id", "user.user_id"};


            String[] key = null;
            String[] value = null;
            key = new String[]{};
            if (Json == null || Json.getPost_id() == -1) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            } else {
                key = new String[]{};
                value = new String[]{};
            }

            rs = JDBC.find(con, main, hot, key, value,Hot);

            if (rs.next()) {
                Post tow = new Post();
                tow = csmht.Dao.Find.FindPost(con, "post_id", rs.getString("post_id"),Hot);
                post = tow;
            }else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }


            post.setComments(csmht.Dao.Find.FindComment(con,"post_id",String.valueOf(post.getPost_id()),Hot));

            for(Comment c : post.getComments()) {
               c.setCommComment(csmht.Dao.Find.FindComment(con,"comment1_id",String.valueOf(c.getComment_Id()),Hot));
            }

            con.commit();
        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        String json = JSON.toJSONString(post);
        PrintWriter writer = resp.getWriter();
        writer.write(json);
        writer.close();
    }


    private void User(HttpServletRequest req, HttpServletResponse resp ,String key0) throws SQLException, IOException, InterruptedException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        User Json = JSON.parseObject(oneLine, User.class);
        Connection con = Pool.Pool.getPool();
        String sort = Hot;
        List<User> post = new ArrayList<User>();
        ResultSet rs = null;
        try {
            con.setAutoCommit(false);

            String[] main = {"user"};
            String[] hot = {};


            String[] key = null;
            String[] value = null;

            key = new String[]{key0};
            if(Json == null || Json.getUserName() == null) {
                sort = "";
                key = new String[]{"user_id"};
                value = new String[]{(String) req.getSession().getAttribute("id")};
            } else if (key0.equals("user.name")) {
                value = new String[]{Json.getUserName() + "%"};
            } else if (key0.equals("user_id")) {
                sort = "";
                value = new String[]{String.valueOf(Json.getUser_id())};
            }else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }


            rs = JDBC.find(con, main, hot, key, value,sort);

            while (rs.next()) {
                User tow = new User();
                tow = csmht.Dao.Find.FindUser(con, "user_id", rs.getString("user_id"),sort);
                post.add(tow);
            }

            con.commit();
        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        String json = null;
        if(sort.isEmpty()){
            json = JSON.toJSONString(post.get(0));
        }else {
            json = JSON.toJSONString(post);
        }

        PrintWriter writer = resp.getWriter();
        writer.write(json);
        writer.close();
    }

    @Override
    public void ManyUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException {
        User(req,resp,"user.name");
    }

    @Override
    public void OneUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException {
        User(req,resp,"user_id");
    }

    @Override
    public void MyUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        User Json = JSON.parseObject(oneLine, User.class);
        Connection con = Pool.Pool.getPool();
        String sort = Hot;
        User user = new User();
        ResultSet rs = null;

        if(Json == null || Json.getUser_id() == -1) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        user = csmht.Dao.Find.FindUser(con,"user_id",String.valueOf(Json.getUser_id()),"");

        String[] main = {"user","user_like","user_follow","board_follow","view_history"};
        String[] hot = {"user.user_id","user_like.user_id","user_follow.user_id","board_follow.user_id","view_history.user_id"};
        String[] key = {"user.user_id"};
        String[] value = {Json.getUser_id()+""};

        rs = JDBC.find(con,main,hot,key,value,"");

        while (rs.next()) {
            String post_id = rs.getString("user_like.post_id");
            String board_id = rs.getString("board_id");
            String user_id = rs.getString("follower_id");
            String history_id = rs.getString("view_history.post_id");

            if(post_id!=null){
              LikePost post =  new LikePost(csmht.Dao.Find.FindPost(con,"post_id",post_id,""),rs.getString("user_like.create_time"));
              user.addLikePost(post);
            }
            if(board_id!=null){
              FollowBoard board = new FollowBoard(csmht.Dao.Find.FindBoard(con,"board_id",board_id,""), rs.getString("board_follow.follow_time"));  ;
              user.addFollowBoard(board);
            }
            if(user_id!=null){
               FollowUser user1 = new FollowUser(csmht.Dao.Find.FindUser(con,"user_id",user_id,""),rs.getString("user_follow.follow_time")) ;
               user.addFollowUser(user1);
            }
            if(history_id!=null){
                HistoryPost post = new HistoryPost(csmht.Dao.Find.FindPost(con,"post_id",history_id,""),rs.getString("view_time"));
                user.addHistoryPost(post);
            }

        }


        rs.close();


        String json = JSON.toJSONString(user);
        PrintWriter writer = resp.getWriter();
        writer.write(json);
        writer.close();
    }

    @Override
    public void CommComment(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Comment Json = JSON.parseObject(oneLine, Comment.class);
        Connection con = Pool.Pool.getPool();

        List<Comment> post = new ArrayList<>();
        ResultSet rs = null;
        try {
            con.setAutoCommit(false);

            String[] main = {"comment_comment"};
            String[] hot = {};


            String[] key = null;
            String[] value = null;
            key = new String[]{};
            if(Json == null || Json.getComment_Id() == -1) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }else {
                key=new String[]{"comment1_id"};
                value = new String[]{String.valueOf(Json.getComment_Id())};
            }

            rs = JDBC.find(con,main, hot, key, value,Hot);


            rs = JDBC.find(con, main, hot, key, value,Hot);

            while (rs.next()) {

            }

            con.commit();
        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        String json = JSON.toJSONString(post);
        PrintWriter writer = resp.getWriter();
        writer.write(json);
        writer.close();

    }

    @Override
    public void HistoryPost(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Post Json = JSON.parseObject(oneLine, Post.class);
        Connection con = Pool.Pool.getPool();

        List<Post> post = new ArrayList<>();
        ResultSet rs = null;
        try{
            con.setAutoCommit(false);
            String[] main = {"view_history"};
            String[] hot = {};
            String[] key = {"user_id"};
            String[] value = {String.valueOf(Json.getUser_id())};

            rs = JDBC.find(con,main,hot,key,value,"ORDER BY likes view_time DESC");

            while (rs.next()) {
                Post tow = new Post();
                tow = csmht.Dao.Find.FindPost(con, "post_id", rs.getString("post_id"),Hot);
                post.add(tow);
            }



            con.commit();
        }catch (Exception e){
            con.rollback();
            e.printStackTrace();
        }







        String json = JSON.toJSONString(post);
        PrintWriter writer = resp.getWriter();
        writer.write(json);
        writer.close();
    }




//    @Override
//    public void FollowUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException {
//        BufferedReader one = req.getReader();
//        String oneLine = one.readLine();
//
//        User Json = JSON.parseObject(oneLine, User.class);
//
//        Connection con = Pool.Pool.getPool();
//
//
//        List<User> user = new ArrayList<>();
//
//        ResultSet rs = null;
//
//
//        try {
//            con.setAutoCommit(false);
//
//            String[] main = {"user_follow"};
//            String[] hot = {};
//            String[] key = {"user_id"};
//            String[] value = {String.valueOf(Json.getUser_id())};
//
//
//            rs = JDBC.find(con,);
//
//
//
//            con.commit();
//        }catch (Exception e){
//            if(rs != null){
//                rs.close();
//            }
//            con.rollback();
//            e.printStackTrace();
//        }
//
//
//    }
//
//    @Override
//    public void FollowBoard(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException {
//
//    }
//
//    @Override
//    public void LikePost(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException {
//
//    }


}
