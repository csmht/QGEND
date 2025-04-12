import csmht.Dao.JDBC;
import csmht.Dao.Pool;

import java.sql.Connection;
import java.sql.SQLException;

public class text {

    public static void main(String[] args) throws SQLException, InterruptedException {

        String a = "user";
        String[] b = {"user.user_id","user.password"};
        String[] c = {"'666'","666"};
        String[] d = {"666"};
        Connection conn = Pool.Pool.getPool();
//        JDBC.find(conn,a,b,c,d);
        JDBC.add(conn,a,b,c);
    }

}
