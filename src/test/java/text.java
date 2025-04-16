

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
        Connection con = Pool.Pool.getPool();
        byte[] byteArray = {72, 101, 108, 108, 111};

//        JDBC.add(con,"user",new String[]{"email","name","image"},new Object[]{"6666@66666","csmht",byteArray});

        String[] a = new String[]{"user_id"};
        String[] b = new String[]{"676"};
        byte[] pngHeader = { (byte) 137, 80, 78, 71, 13, 10, 26, 10 };

//       JDBC.update(con,"user",new String[]{"email","name","image","password"},new Object[]{"789654123@33333","ttttttt",pngHeader,"77777"},a,b);
//

    }

}
