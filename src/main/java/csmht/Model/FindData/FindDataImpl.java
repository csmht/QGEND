package csmht.Model.FindData;

import com.alibaba.fastjson2.JSON;
import csmht.Dao.ClassObject.Board;
import csmht.Dao.JDBC;
import csmht.Dao.Pool;
import csmht.View.UserBaseServlet;

import javax.servlet.annotation.WebServlet;
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


@WebServlet("/User/Find/*")
public class FindDataImpl extends UserBaseServlet implements FindDataServlet {
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

            String[] main={"board","user"};
            String[] hot={"board.user_id","user.user_id"};


            String[] key;
            String[] value;

            if(Json==null||Json.getUserName()==null){
                key = new String[]{};
                value = new String[]{};
            }else {
                key=new String[] {"user.name"};
                value=new String[] {Json.getUserName()};
            }

            rs = JDBC.find(con,main,hot,key,value,aNew);

            while(rs.next()){
                Board tow = new Board();
                tow = csmht.Dao.Find.FindBoard(con,"board_id",rs.getString("board_id"),aNew);
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
        NameManyBoar(req, resp, Hot);
    }

    private void NameManyBoar(HttpServletRequest req, HttpServletResponse resp, String hot2) throws IOException, SQLException, InterruptedException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Board Json = JSON.parseObject(oneLine, Board.class);
        Connection con = Pool.Pool.getPool();

        List<Board> board = new ArrayList<Board>();
        ResultSet rs = null;
        try{
            con.setAutoCommit(false);

            String[] main={"board","user"};
            String[] hot={"board.user_id","user.user_id"};


            String[] key;
            String[] value;

            if(Json==null||Json.getTitle()==null){
                key = new String[]{};
                value = new String[]{};
            }else {
                key=new String[] {"title"};
                value=new String[] {Json.getTitle()};
            }

            rs = JDBC.find(con,main,hot,key,value, hot2);

            while(rs.next()){
                Board tow = new Board();
                tow = csmht.Dao.Find.FindBoard(con,"board_id",rs.getString("board_id"), hot2);
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
    public void NameManyBoardNew(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException {
        NameManyBoar(req, resp, New);
    }

    @Override
    public void OneBoard(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Board Json = JSON.parseObject(oneLine, Board.class);
        Connection con = Pool.Pool.getPool();

        Board board = new Board();
        ResultSet rs = null;
        try{
            con.setAutoCommit(false);

            String[] main={"board","user"};
            String[] hot={"board.user_id","user.user_id"};


            String[] key;
            String[] value;

            if(Json==null||Json.getBoard_id() == -1){
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
        PrintWriter writer = resp.getWriter();
        writer.write(json);
        writer.close();
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
