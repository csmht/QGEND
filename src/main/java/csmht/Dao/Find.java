package csmht.Dao;


import csmht.Dao.ClassObject.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;



public class Find {

    /**
     * 寻找帖子
     * @param post_id
     * @param sort Hot or New
     * @return
     * @throws SQLException
     * @throws IOException
     * @throws InterruptedException
     */
    public Post FindPost(Connection con,int post_id,String sort) throws SQLException, IOException, InterruptedException {
        Post post = new Post();

        ResultSet rs = null;


            // id find
            String[] main={"post"};
            String[] sub={};
            String[] key={"post_id"};
            String[] value={String.valueOf(post_id)};
            rs = JDBC.find(con,main,sub,key,value,sort);



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

    /**
     * 寻找模块
     * @param board_id
     * @param sort Hot or New
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public Board FindBoard(Connection con,int board_id,String sort) throws SQLException, IOException {
        Board board = new Board();
        ResultSet rs = null;

        String[] main={"board","image"};
        String[] sub={"board.image_id","image.image_id"};
        String[] key={"board_id"};
        String[] value={String.valueOf(board_id)};
        rs = JDBC.find(con,main,sub,key,value,sort);
        if(rs.next()){
            board.setBoard_id(rs.getInt("board_id"));
            board.setTitle(rs.getString("title"));
            board.setContent(rs.getString("content"));
            board.setUser_id(rs.getInt("user_id"));
            board.setPass(rs.getBoolean("pass"));
            board.setImage_id(rs.getInt("image_id"));
            board.setLikes(rs.getInt("likes"));

            String[] main2={"image"};
            String[] sub2={};
            String[] key2={"board_id"};
            String[] value2={String.valueOf(board.getBoard_id())};
            rs = JDBC.find(con,main2,sub2,key2,value2,"");
            if(rs.next()){
                InputStream inputStream = rs.getBinaryStream("content");
                if (inputStream != null) {
                    byte[] image = new byte[inputStream.available()];
                    inputStream.read(image);
                    inputStream.close();
                    board.setImage(image);
                }
            }

        }

        return board;
    }


    /**
     * 寻找用户
     * @param user_id
     * @param sort Hot or New
     * @return
     * @throws SQLException
     * @throws InterruptedException
     * @throws IOException
     */
    public User FindUser(Connection con,int user_id,String sort) throws SQLException, InterruptedException, IOException {
        User user = new User();
        ResultSet rs = null;

        String[] main={"user"};
        String[] sub={};
        String[] key={"user_id"};
        String[] value={String.valueOf(user_id)};
        rs = JDBC.find(con,main,sub,key,value,sort);

        if(rs.next()){
            user.setUser_id(rs.getInt("user_id"));
            user.setPassword(rs.getString("password"));
            user.setUserName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setImage_id(rs.getInt("image_id"));
            user.setAdmin(rs.getBoolean("admin"));
            user.setPass(rs.getString("pass"));

            String[] main2={"image"};
            String[] sub2={};
            String[] key2={"image_id"};
            String[] value2={String.valueOf(user.getImage_id())};

            if(rs.next()){
                InputStream inputStream = rs.getBinaryStream("content");
                if (inputStream != null) {
                    byte[] image = new byte[inputStream.available()];
                    inputStream.read(image);
                    inputStream.close();
                    user.setImage(image);
                }
            }
        }

        return user;
    }

    /**
     * 寻找帖子评论
     * @param sort Hot or New
     * @return
     * @throws SQLException
     */
    public UserCommentPost FindPostComment(Connection con,int post_id,String sort) throws SQLException, InterruptedException {
        UserCommentPost comment = new UserCommentPost();
        ResultSet rs = null;

        if(sort==null|| sort.isEmpty()){
            sort = "";
        }

        String[] main={"comment"};
        String[] sub={};
        String[] key={"post_id"};
        String[] value={String.valueOf(post_id)};

        rs = JDBC.find(con,main,sub,key,value,sort);

        if(rs.next()){
            comment.setUser_id(rs.getInt("user_id"));
            comment.setPost_Id(post_id);
            comment.setComment(rs.getString("comment"));
            comment.setComment_Id(rs.getInt("comment_id"));
            comment.setCreate_time(rs.getString("create_time"));
            comment.setLikes(rs.getInt("likes"));
        }

        return comment;
    }

    public UserCommComm FindCommComment(Connection con,int comm_id,String sort) throws SQLException, InterruptedException {
        UserCommComm comm = new UserCommComm();
        ResultSet rs = null;

        if(sort==null|| sort.isEmpty()){
            sort = "";
        }

        String[] main={"comment_comment"};
        String[] sub={};
        String[] key={"comment_id"};
        String[] value={String.valueOf(comm_id)};
        rs = JDBC.find(con,main,sub,key,value,sort);

        if(rs.next()){

        }

        return comm;
    }
}
