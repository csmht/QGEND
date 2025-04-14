

import com.alibaba.fastjson2.JSON;
import csmht.Dao.ClassObject.Board;
import csmht.Dao.ClassObject.Post;
import csmht.Dao.JDBC;
import csmht.Dao.Pool;


import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static csmht.Dao.Constant.Hot;

public class text {

    public static void main(String[] args) throws SQLException, InterruptedException {

        String key0 = "title";
        Post Json = new Post();
        Json.setPost_id(1);
        Json.setUser_id(0);
        Json.setTitle("测试");
        Connection con = Pool.Pool.getPool();

        List<Post> post = new ArrayList<Post>();
        ResultSet rs = null;
        try {
            con.setAutoCommit(false);

            String[] main = {"post", "user"};
            String[] hot = {"post.user_id", "user.user_id"};


            String[] key = null;
            String[] value = null;

            if (Json == null || Json.getUserName() == null) {
                key = new String[]{};
                value = new String[]{};
            } else if (key0.equals("title")) {
                value = new String[]{Json.getTitle()};
            } else if (key0.equals("user.name")) {
                value = new String[]{String.valueOf(Json.getUser_id())};
            } else if (key0.equals("post_id")) {
                value = new String[]{String.valueOf(Json.getPost_id())};
            } else {
               System.out.println("no");
               return;
            }

            rs = JDBC.find(con, main, hot, key, value,Hot);

            while (rs.next()) {
                Post tow = new Post();
                tow = csmht.Dao.Find.FindPost(con, "post_id", rs.getString("post_id"),Hot);
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
        System.out.println(json);
    }

}
