

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
        System.out.println(Hot);
        Board Json = new Board();
        Connection con = Pool.Pool.getPool();

        List<Board> board = new ArrayList<Board>();

        try{
            con.setAutoCommit(false);

            String[] main={"board","user"};
            String[] hot={"board.user_id","user.user_id"};
            String[] key={"user.name"};
            String[] value={Json.getUserName()};
            if(value[0] == null){
                key = new String[]{};
                value = new String[]{};
            }

            ResultSet rs = JDBC.find(con,main,hot,key,value,"");

            while(rs.next()){
                Board tow = new Board();
                tow = csmht.Dao.Find.FindBoard(con,"board_id",rs.getString("board_id"),Hot);
                board.add(tow);
            }
            rs.close();

            con.commit();

        }catch (Exception e){
            con.rollback();
            e.printStackTrace();
        }finally {
            Pool.Pool.returnConn(con);
        }



        String json = JSON.toJSONString(board);
        System.out.println(json);
    }

}
