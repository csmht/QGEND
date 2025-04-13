package csmht.Dao.Find;

import com.alibaba.fastjson2.JSON;
import csmht.Dao.ClassObject.Board;
import csmht.Dao.ClassObject.Post;
import csmht.Dao.ClassObject.User;
import csmht.Dao.ClassObject.UserCommentPost;
import csmht.Dao.JDBC;
import csmht.Dao.Pool;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;



public class Find {

    public Post FindPost(int post_id) throws SQLException, IOException, InterruptedException {
        Post post = new Post();
        Connection con = Pool.Pool.getPool();

        ResultSet rs = null;


            // id find
            String[] main={"post"};
            String[] sub={};
            String[] key={"post_id"};
            String[] value={String.valueOf(post_id)};
            rs = JDBC.find(con,main,sub,key,value);



        if(rs.next()){
            post.setPost_id(rs.getInt("post_id"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setBoard_id(rs.getInt("board_id"));
            post.setUser_id(rs.getInt("user_id"));
            post.setLikes(rs.getInt("likes"));
            post.setViews(rs.getInt("views"));
        }

        return post;
    }

    public Board FindBoard(int board_id) throws SQLException {
        Board board = new Board();
        Connection con = null;
        ResultSet rs = null;

        String[] main={"board","image"};
        String[] sub={"board.image_id","image.image_id"};
        String[] key={"board_id"};
        String[] value={String.valueOf(board_id)};
        rs = JDBC.find(con,main,sub,key,value);
        if(rs.next()){
            board.setBoard_id(rs.getInt("board_id"));
            board.setTitle(rs.getString("title"));
            board.setContent(rs.getString("content"));
            board.setUser_id(rs.getInt("user_id"));
            board.setPass(rs.getBoolean("pass"));
            board.setImage_id(rs.getInt("image_id"));





        }

        return board;
    }

    public User FindUser(HttpServletRequest req, HttpServletResponse res) throws SQLException {


        return new User();
    }

    public UserCommentPost FindUserCommentPost(HttpServletRequest req, HttpServletResponse res) throws SQLException {

        return new UserCommentPost();
    }
}
