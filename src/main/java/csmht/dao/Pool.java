package csmht.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

public class Pool {

    public static Pool Pool = new Pool();
    private static final CopyOnWriteArrayList<Connection> waitConn = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<Connection> useConn = new CopyOnWriteArrayList<>();
    private static final String url = "jdbc:mysql://localhost:3306/forum?useUnicode=true&characterEncoding=UTF-8";
    private static final String user = "root";
    private static final String password = "0603";
    static int minPool = 1;
    static int maxPool = 5;
    static long maxTime = 5000;
    static connTime connTime = new connTime();

    static {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            for (int i = 0 ;i < minPool ; i++){
                Connection conn = DriverManager.getConnection(url, user, password);
                waitConn.add(conn);
            }
        } catch (Exception e) {
            System.out.println(e);
        }


        Timer timer = new Timer();
        timer.schedule(new connInspect(), 0, 1000);
    }

    public synchronized Connection getPool() throws SQLException, InterruptedException {
        while(waitConn.size()+useConn.size()<maxPool){
            waitConn.add(DriverManager.getConnection(url, user, password));
        }

        if(waitConn.isEmpty()){
            wait();
        }
        Connection conn = waitConn.remove(0);
        useConn.add(conn);
        connTime.In(conn);
        return conn;

    }

    public synchronized void returnConn(Connection conn) {

        try {
            if (conn != null) {
                if (!conn.getAutoCommit()) {
                    conn.rollback();
                }

                conn.setAutoCommit(false);
                conn.prepareStatement("SHOW TABLES;").executeQuery();
                conn.rollback();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        useConn.remove(conn);
        try {
            if(conn!=null && waitConn.size() + useConn.size()<=maxPool){

                    if(!conn.isClosed()){
                        waitConn.add(conn);
                        notifyAll();
                    }

            }else {
                if(conn != null){
                    conn.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connTime.delate(conn);

    }


    public static void closePool() {
        while (!waitConn.isEmpty()) {
            try {
                Connection conn = waitConn.remove(0);
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class connInspect extends TimerTask {

        @Override
        public synchronized void run() {


//            try {
//                getUsePool();
//                getWaitPool();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }



            try {
                for (Connection conn : csmht.dao.connTime.map.keySet()){
                    long time = System.currentTimeMillis() - csmht.dao.connTime.map.get(conn);
                    if(time >= maxTime){
                            Pool.returnConn(conn);
                    }
                }

                while(waitConn.size()+useConn.size()>maxPool){
                    if(!waitConn.isEmpty()){
                        Connection conn = waitConn.remove(0);
                        conn.close();
                    }
                }

                if(!waitConn.isEmpty()){
                    notifyAll();
                }

                for(Connection conn : waitConn){
                    if(conn.isClosed()){
                        waitConn.remove(conn);
                    }
                }

                for(Connection conn : useConn){
                    if(conn.isClosed()){
                        useConn.remove(conn);
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static TimerTask getUsePool() throws SQLException {
        System.out.println("----------------------");
        System.out.println("user:" + useConn.size());
        int i =0;
        for(Connection conn : useConn){
            if(conn.isClosed()){

                System.out.println("userNO");
            }
        }
        return null;
    }

    public static TimerTask getWaitPool() throws SQLException {
        System.out.println("wait:" + waitConn.size());
        int i =0;
        for(Connection conn : waitConn){
            if(conn.isClosed()){
                System.out.println("waitNO");
            }
        }return null;
    }

}
