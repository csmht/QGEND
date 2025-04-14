

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
        Json.setUserName("text");
        Connection con = Pool.Pool.getPool();

        List<Board> board = new ArrayList<Board>();

        try{
            con.setAutoCommit(false);

            String[] main={"user","board"};
            String[] hot={"user.user_id","board.user_id"};
            String[] key={"user.name"};
            String[] value={Json.getUserName()};

            ResultSet rs = JDBC.find(con,main,hot,key,value,"");

            while(rs.next()){
                Board tow = new Board();
                tow = csmht.Dao.Find.FindBoard(con,"board_id",rs.getString("board_id"),Hot);
                board.add(tow);
            }

            con.commit();
        }catch (Exception e){
            con.rollback();
            e.printStackTrace();
        }
    }

}
