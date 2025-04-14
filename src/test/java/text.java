

import com.alibaba.fastjson2.JSON;
import csmht.Dao.ClassObject.Board;
import csmht.Dao.JDBC;
import csmht.Dao.Pool;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static csmht.Dao.Constant.Hot;

public class text {

    public static void main(String[] args) throws SQLException, InterruptedException {

        Board Json = new Board();
        Connection con = Pool.Pool.getPool();

        Json.setBoard_id(1);

        Board board = new Board();
        ResultSet rs = null;
        try{
            con.setAutoCommit(false);

            String[] main={"board","user"};
            String[] hot={"board.user_id","user.user_id"};


            String[] key;
            String[] value;

            if(Json.getBoard_id() == -1){
                return;
            }else {
                key=new String[] {"board_id"};
                value=new String[] {String.valueOf(Json.getBoard_id())};
            }

            rs = JDBC.find(con,main,hot,key,value,Hot);

            while(rs.next()){
                board = csmht.Dao.Find.FindBoard(con,"board_id",rs.getString("board_id"), Hot);
            }

            // 找帖子






            con.commit();
        }catch (Exception e){
            con.rollback();
            e.printStackTrace();
        }finally {
            if (rs != null) {
                rs.close();
            }
        }

        String json = JSON.toJSONString(board);
        System.out.println(json);
    }

}
