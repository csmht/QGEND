import csmht.dao.Pool;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class PoolTest {



    @Test
    void getPool(){
        Connection conn = null;
        try {
            conn = Pool.Pool.getPool();
            assert conn != null;
            Pool.Pool.returnConn(conn);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    void getPool2(){
        Connection con1 = null;
        Connection con2 = null;
        Connection con3 = null;
        Connection con4 = null;
        Connection con5 = null;
        Connection con6 = null;
        Connection con7 = null;

        try{
            con1 = Pool.Pool.getPool();
            assert con1 != null;
            con2 = Pool.Pool.getPool();
            assert con2 != null;
            con3 = Pool.Pool.getPool();
            assert con3 != null;
            con4 = Pool.Pool.getPool();
            assert con4 != null;
            con5 = Pool.Pool.getPool();
            assert con5 != null;


            try {
                con6 = Pool.Pool.getPool();
                if(con6 != null){
                    System.out.println(true);
                }
                assert con6 != null;
                con7 = Pool.Pool.getPool();
                assert con7 != null;
            } catch (SQLException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            Pool.Pool.returnConn(con1);
            Pool.Pool.returnConn(con2);
            Pool.Pool.returnConn(con3);
            Pool.Pool.returnConn(con4);
            Pool.Pool.returnConn(con5);
            Pool.Pool.returnConn(con6);
            Pool.Pool.returnConn(con7);

        }catch (Exception e){
            e.printStackTrace();
        }





    }




}
