import csmht.dao.Find;
import csmht.dao.Pool;
import csmht.dao.classobject.Board;
import csmht.dao.classobject.Comment;
import csmht.dao.classobject.Post;
import csmht.dao.classobject.User;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class FindTest {
    @Test
    void user(){
        Connection con = null;
        try{
            con = Pool.Pool.getPool();

            User user = Find.FindUser(con,"user_id","0","");

            assert user.getUser_id()==0;

            Pool.Pool.returnConn(con);
        }catch(Exception e){
            assert false;
            e.printStackTrace();
        }

    }

    @Test
    void post(){
        Connection con = null;
        try{
            con = Pool.Pool.getPool();
            Post post = new Post();
            post =  Find.FindPost(con,"post_id","0","");
            assert Objects.equals(post.getTitle(), "cs");
        }catch(Exception e){
            assert false;
            e.printStackTrace();
        }
    }

    @Test
    void userLike(){
        Connection con = null;
        try{
            con = Pool.Pool.getPool();

           Board one = Find.FindBoard(con,"board_id","0","");
            assert Objects.equals(one.getTitle(), "cs");
        }catch (Exception e){
            assert false;
            e.printStackTrace();
        }
    }

    @Test
    void postLike(){
        Connection con = null;
        try{
            con = Pool.Pool.getPool();

           List<Comment> post = Find.FindComment(con,"comment_id","0","");
            assert Objects.equals(post.get(0).getContent(), "cs");
        }catch (Exception e){
            e.printStackTrace();
            assert false;

        }
    }


}
