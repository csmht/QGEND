package csmht.Model.FindData;

import com.alibaba.fastjson2.JSON;
import csmht.Dao.ClassObject.Board;
import csmht.Dao.JDBC;
import csmht.Dao.Pool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static csmht.Dao.Constant.Hot;
import static csmht.Dao.Constant.New;



public class FindDataImpl implements FindDataServlet{
    @Override
    public void UserManyBoardHot(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, InterruptedException {
        UserManyBoar(req,resp, Hot);


    }

    @Override
    public void UserManyBoardNew(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException {
        UserManyBoar(req,resp, New);
    }

    private void UserManyBoar(HttpServletRequest req,HttpServletResponse resp, String aNew) throws IOException, SQLException, InterruptedException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Board Json = JSON.parseObject(oneLine, Board.class);
        Connection con = Pool.Pool.getPool();

        List<Board> board = new ArrayList<Board>();
        ResultSet rs = null;
        try{
            con.setAutoCommit(false);

            String[] main={"user","board"};
            String[] hot={"user.user_id","board.user_id"};
            String[] key={"user.name"};
            String[] value={Json.getUserName()};

            rs = JDBC.find(con,main,hot,key,value,"");

            while(rs.next()){
                Board tow = new Board();
                tow = csmht.Dao.Find.FindBoard(con,"board_id",rs.getString("board_id"), aNew);
                board.add(tow);
            }

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
        PrintWriter writer = resp.getWriter();
        writer.write(json);
        writer.close();

    }

    @Override
    public void NameManyBoardHot(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, InterruptedException {

    }

    @Override
    public void NameManyBoardNew(HttpServletRequest req, HttpServletResponse resp) {

    }

    @Override
    public void OneBoard(HttpServletRequest req, HttpServletResponse resp) {

    }

    @Override
    public void ManyPostHot(HttpServletRequest req, HttpServletResponse resp) {

    }

    @Override
    public void ManyPostNew(HttpServletRequest req, HttpServletResponse resp) {

    }

    @Override
    public void OnePost(HttpServletRequest req, HttpServletResponse resp) {

    }

    @Override
    public void ManyUser(HttpServletRequest req, HttpServletResponse resp) {

    }

    @Override
    public void OneUser(HttpServletRequest req, HttpServletResponse resp) {

    }
}
