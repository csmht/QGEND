package csmht.Model.admin;

import com.alibaba.fastjson2.JSON;
import csmht.View.UserBaseServlet;
import csmht.dao.Find;
import csmht.dao.JDBC;
import csmht.dao.Pool;
import csmht.dao.classobject.Board;
import csmht.dao.classobject.Report;
import csmht.dao.classobject.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
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
import java.util.Objects;


@WebServlet("/Admin/*")
public class AdminImpl extends UserBaseServlet implements AdminService {

    @Override
    public void FindPassBoard(HttpServletRequest req, HttpServletResponse resp) throws SQLException, InterruptedException, IOException {
        FindBan(req,resp,"board_id");
    }

    @Override
    public void PassBoard(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException {
        PassBoard(req,resp,"YES");
    }

    private void PassBoard(HttpServletRequest req, HttpServletResponse resp, String sort) throws SQLException, InterruptedException, IOException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Board Json = JSON.parseObject(oneLine, Board.class);
        List<Report> board = new ArrayList<>();
        Connection con = Pool.Pool.getPool();
        int board_id = Json.getBoard_id();

        try{

            con.setAutoCommit(false);
            String[] value2;
            String[] key2 = {"pass"};
            if(sort.equals("Un")){
                value2 = new String[]{"2"};
            }else {
                value2 = new String[]{"1"};
            }

            String[] key1 = {"board_id"};
            String[] value1 = {""+board_id};

            JDBC.update(con,"board",key2,value2,key1,value1);


            con.commit();
            resp.getWriter().write("OK");
            resp.getWriter().close();
        }catch (Exception e){
            con.rollback();
            e.printStackTrace();
        }finally {
            Pool.Pool.returnConn(con);
        }









        String json = JSON.toJSONString(board);
        PrintWriter writer = resp.getWriter();
        writer.write(json);
        writer.close();

    }

    @Override
    public void UnPassBoard(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException {
        PassBoard(req,resp,"Un");
    }

    private void FindBan(HttpServletRequest req, HttpServletResponse resp,String sort) throws SQLException, InterruptedException, IOException {
        List<Report> board = new ArrayList<>();
        Connection con = Pool.Pool.getPool();
        Connection con1 = Pool.Pool.getPool();
        ResultSet rs = null;

        try{

            String[] main = {"report"};
            String[] mun = {};
            String[] key = {"pass"};
            String[] value = {"0"};

            rs = JDBC.find(con,main,mun,key,value,"");

            while(rs.next()){


                Report b = new Report();
                if(sort.equals("board_id")?!Objects.equals(rs.getString(sort), "0"):!Objects.equals(rs.getString("reported_user_id"), "0")){

                    if(sort.equals("board_id")){
                        Board board1 = new Board();
                        board1 = Find.FindBoard(con1,"board_id",rs.getString("board_id"),"");
                        b.setBoard(board1);
                    }else {
                        User u = new User();

                        if(rs.getInt("reported_user_id")!=0){
                            u = Find.FindUser(con1,"user_id",rs.getString("user_id"),"");
                            b.setUser(u);
                        }

                    }


                    b.setReport_user_id(rs.getInt("reported_user_id"));
                    b.setContent(rs.getString("content"));
                    b.setReport_id(rs.getInt("report_id"));
                    board.add(b);
                }
            }



        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if(rs != null){
                rs.close();
            }
            Pool.Pool.returnConn(con);
            Pool.Pool.returnConn(con1);
        }

        String json = JSON.toJSONString(board);
        PrintWriter writer = resp.getWriter();
        writer.write(json);
        writer.close();
    }


    @Override
    public void FindBanUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, InterruptedException, IOException {
        FindBan(req,resp,"user_id");
    }

    @Override
    public void BanUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException {
        BanUser(req,resp,"YES");
    }

    private void BanUser(HttpServletRequest req, HttpServletResponse resp, String sort) throws SQLException, InterruptedException, IOException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Report Json = JSON.parseObject(oneLine, Report.class);

        Connection con = Pool.Pool.getPool();
        ResultSet rs = null;

        try{
            con.setAutoCommit(false);

            String[]  main = new String[]{"report"};
            String[] mun = new String[]{};
            String[] key = new String[]{"report_id"};
            String[] value = new String[]{Json.getReport_id()+""};

            rs = JDBC.find(con,main,mun,key,value,"");
            rs.next();
            int user_id = rs.getInt("reported_user_id");



            main = new String[]{"pass"};
            mun = new String[]{"1"};
            key = new String[]{"reported_user_id"};
            value = new String[]{user_id + ""};

            JDBC.update(con,"report",main,mun,key,value);


            if(sort.equals("YES")){

                main =new String[] {"pass"};
                mun =new String[] {"1"};
                key =new String[] {"user_id"};
                value = new String[] {user_id+""};

                JDBC.update(con,"user",main,mun,key,value);
            }


            con.commit();
            resp.getWriter().write("OK");
        }catch (Exception e){
            con.rollback();
            e.printStackTrace();
        }finally {
            if(rs != null){
                rs.close();
            }
            Pool.Pool.returnConn(con);
        }



    }


    @Override
    public void UnBanUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException {
        BanUser(req,resp,"Un");
    }

    @Override
    public void FindBoard(HttpServletRequest req, HttpServletResponse resp) throws SQLException, InterruptedException, IOException {
        List<Board> board = new ArrayList<>();
        Connection con = Pool.Pool.getPool();
        Connection con1 = Pool.Pool.getPool();
        ResultSet rs = null;

        try{

           String[] main = {"board"};
           String[] mun = {};
           String[] key = {"pass"};
           String[] value = {"0"};

           rs = JDBC.find(con,main,mun,key,value,"");

           while (rs.next()){
               Board b ;
               b = csmht.dao.Find.FindBoard(con1,"board_id",rs.getString("board_id"),"");
               board.add(b);
           }




        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if(rs != null){
                rs.close();
            }
            Pool.Pool.returnConn(con);
            Pool.Pool.returnConn(con1);
        }

        String json = JSON.toJSONString(board);
        PrintWriter writer = resp.getWriter();
        writer.write(json);
        writer.close();
    }

    @Override
    public void FindLog(HttpServletRequest req, HttpServletResponse resp) throws SQLException, InterruptedException, IOException {

    }

    @Override
    public void BanBoard(HttpServletRequest req, HttpServletResponse resp) throws SQLException, InterruptedException, IOException {
        BanBoard(req,resp,"YES");
    }

    private void BanBoard(HttpServletRequest req, HttpServletResponse resp, String sort) throws SQLException, InterruptedException, IOException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Report Json = JSON.parseObject(oneLine, Report.class);

        Connection con = Pool.Pool.getPool();
        ResultSet rs = null;

        try {
            con.setAutoCommit(false);

            String[]  main = new String[]{"report"};
            String[] mun = new String[]{};
            String[] key = new String[]{"report_id"};
            String[] value = new String[]{Json.getReport_id()+""};

            rs = JDBC.find(con,main,mun,key,value,"");
            rs.next();
            int board_id = rs.getInt("board_id");

            main = new String[]{"pass"};
            mun = new String[]{"1"};
            key = new String[]{"board_id"};
            value = new String[]{board_id+""};

            JDBC.update(con,"report",main,mun,key,value);

            if(sort.equals("YES")){
                main =new String[] {"pass"};
                mun =new String[] {"2"};
                key =new String[] {"board_id"};
                value = new String[] {board_id+""};

                JDBC.update(con,"board",main,mun,key,value);
            }


            con.commit();


            resp.getWriter().write("OK");
        }catch (Exception e){
            con.rollback();
            e.printStackTrace();
        }finally {
            if(rs != null){
                rs.close();
            }
            Pool.Pool.returnConn(con);
        }





    }

    @Override
    public void UnBanBoard(HttpServletRequest req, HttpServletResponse resp) throws SQLException, InterruptedException, IOException {
        BanBoard(req,resp,"Un");
    }


}
