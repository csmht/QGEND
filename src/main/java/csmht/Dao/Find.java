package csmht.Dao;


import csmht.Dao.ClassObject.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Find {


    /**
     * 寻找帖子
     * @param key0 约束列:
     * title--->标题
     * post_id--->帖子id
     * user_id--->创建者id
     * @param value0 约束条件
     * @param sort Hot or New
     * @return
     * @throws SQLException
     * @throws IOException
     * @throws InterruptedException
     */
    static public Post FindPost(Connection con,String key0,String value0,String sort) throws SQLException, IOException, InterruptedException {
        Post post = new Post();

        ResultSet rs = null;


            // id find
            String[] main={"post","user"};
            String[] sub={"post.user_id","user.user_id"};
            String[] key={key0};
            String[] value={value0};
            rs = JDBC.find(con,main,sub,key,value,sort);



        if(rs.next()){
            post.setUserName(rs.getString("user.name"));
            post.setPost_id(rs.getInt("post_id"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setBoard_id(rs.getInt("board_id"));
            post.setUser_id(rs.getInt("user_id"));
            post.setLikes(rs.getInt("likes"));
            post.setViews(rs.getInt("views"));
            post.setCreate_time(rs.getString("create_time"));
        }

        return post;
    }

    /**
     * 寻找模块
     * @param key0 约束列
     *            title
     *            board_id
     *            user_id
     * @param value0 约束条件
     * @param sort Hot or New
     * @return
     * @throws SQLException
     * @throws IOException
     */
    static public Board FindBoard(Connection con,String key0,String value0,String sort) throws SQLException, IOException {
        Board board = new Board();
        ResultSet rs = null;

        String[] main={"board","user"};
        String[] sub={"board.user_id","user.user_id"};
        String[] key={key0};
        String[] value={value0};
        rs = JDBC.find(con,main,sub,key,value,sort);
        if(rs.next()){
            board.setUserName(rs.getString("user.name"));
            board.setBoard_id(rs.getInt("board_id"));
            board.setTitle(rs.getString("title"));
            board.setContent(rs.getString("content"));
            board.setUser_id(rs.getInt("user_id"));
            board.setPass(rs.getBoolean("pass"));
            board.setLikes(rs.getInt("likes"));
            InputStream inputStream = rs.getBinaryStream("image");
            if (inputStream != null) {
                byte[] image = new byte[inputStream.available()];
                inputStream.read(image);
                inputStream.close();
                board.setImage(image);
            }





        }
        rs.close();
        return board;
    }


    /**
     * 寻找用户
     * @param key0 约束列 user_id name
     * @param value0  约束值
     * @param sort Hot or New
     * @return
     * @throws SQLException
     * @throws InterruptedException
     * @throws IOException
     */
    static public User FindUser(Connection con,String key0,String value0,String sort) throws SQLException, InterruptedException, IOException {
        User user = new User();
        ResultSet rs = null;

        String[] main={"user"};
        String[] sub={};
        String[] key={key0};
        String[] value={value0};
        rs = JDBC.find(con,main,sub,key,value,sort);

        if(rs.next()){
            user.setUser_id(rs.getInt("user_id"));
            user.setPassword(rs.getString("password"));
            user.setUserName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setAdmin(rs.getBoolean("admin"));
            user.setPass(rs.getString("pass"));


            InputStream inputStream = rs.getBinaryStream("image");
            if (inputStream != null) {
                byte[] image = new byte[inputStream.available()];
                inputStream.read(image);
                inputStream.close();
                user.setImage(image);
            }


        }
        rs.close();
        return user;
    }

    /**
     * 寻找帖子评论
     * @param key0 约束列 user_id,post_id
     * @param value0
     * @param sort Hot or New
     * @return
     * @throws SQLException
     */
    static public List<Comment> FindPostComment(Connection con, String key0, String value0, String sort) throws SQLException, InterruptedException {
        List<Comment> comment = new ArrayList<>();
        ResultSet rs = null;

        if(sort==null|| sort.isEmpty()){
            sort = "";
        }

        String[] main={"comment","user"};
        String[] sub={"comment.user_id","user.user_id"};
        String[] key={key0};
        String[] value={value0};

        rs = JDBC.find(con,main,sub,key,value,sort);

        if(rs.next()){
            Comment one = new Comment();
            one.setUserName(rs.getString("user.name"));
            one.setUser_id(rs.getInt("user_id"));
            one.setPost_Id(rs.getInt("post_id"));
            one.setContent(rs.getString("comment"));
            one.setComment_Id(rs.getInt("comment_id"));
            one.setCreate_time(rs.getString("create_time"));
            one.setLikes(rs.getInt("likes"));
            comment.add(one);
        }
        rs.close();
        return comment;
    }

    /**
     * 寻找评论评论
     * @param con
     * @param comm_id
     * @param sort
     * @return
     * @throws SQLException
     * @throws InterruptedException
     */
    static public CommComment FindCommComment(Connection con, int comm_id, String sort) throws SQLException, InterruptedException {
        CommComment commComment = new CommComment();
        ResultSet rs = null;

        if(sort==null|| sort.isEmpty()){
            sort = "";
        }

        String[] main={"comment_comment","user"};
        String[] sub={"comment_comment.user_id","user.user_id"};
        String[] key={"comment_id"};
        String[] value={String.valueOf(comm_id)};
        rs = JDBC.find(con,main,sub,key,value,sort);

        if(rs.next()){
            commComment.setUserName(rs.getString("user.name"));
            commComment.setCommComm_id(rs.getInt("commcomm_id"));
            commComment.setUser_id(rs.getInt("user_id"));
            commComment.setComment(rs.getString("comment"));
            commComment.setComm_id(rs.getInt("comment_id"));
            commComment.setCreate_time(rs.getString("create_time"));
            commComment.setLikes(rs.getInt("likes"));
        }
        rs.close();
        return commComment;
    }

}
