package csmht.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC {


    public static ResultSet find(Connection conn,String[] main,String[] sub,String[] key,String[] val) throws SQLException {
        StringBuilder sql = new StringBuilder("select ");
        for (int i = 0; i < main.length; i++) {
            sql.append(main[i]).append(".*");
            if(i!=main.length-1) sql.append(",");
        }
        sql.append(" from ").append(main[0]).append(" ");
        for(int i = 1; i < sub.length; i++) {
            sql.append(" join ").append(main[i]).append(" on ").append(sub[0]).append(" = ").append(sub[i]);
        }
        sql.append(" ");



        for (int i =0; i < key.length; i++) {
            if(i==0){
                sql.append(" where ");
            }else{
                sql.append(" and ");
            }

            sql.append(key[i]).append(" = ?");
        }

        PreparedStatement ps = conn.prepareStatement(String.valueOf(sql));
        for (int i =0; i < val.length; i++) {
            ps.setString(i+1, val[i]);
        }

        return ps.executeQuery();
    }

    public static int add(Connection conn,String main,String[] key,String[] val) throws SQLException {

        StringBuilder sql = new StringBuilder("insert into ");
        sql.append(main).append(" (");
        sql.append(key[0]);
        for(int i =1; i < key.length; i++) {
            sql.append(",").append(key[i]);
        }
        sql.append(") values (");
        for(int i =0; i < val.length; i++) {
            sql.append(val[i]);
            if(i!=val.length-1) sql.append(",");
        }
        sql.append(")");
        return conn.prepareStatement(sql.toString()).executeUpdate();
    }


    public static int delete(Connection conn,String main,String[] key,String[] val) throws SQLException {
        StringBuilder sql = new StringBuilder("delete from ");
        sql.append(main);
        sql.append(" where ");
        for(int i =0; i < key.length; i++) {
            sql.append(key[i]).append(" = ?");
            if(i!=key.length-1){sql.append(",");
            } else sql.append(" and ");
        }
        PreparedStatement ps = conn.prepareStatement(sql.toString());
        for(int i =0; i < val.length; i++) {
            ps.setString(i+1, val[i]);
        }
        return ps.executeUpdate();
    }

    /**
     *
     * @param conn
     * @param main
     * @param key1 changer
     * @param val1
     * @param key2 where
     * @param val2
     * @return
     * @throws SQLException
     */
    public static int update(Connection conn,String main,String[] key1,String[] val1,String[] key2,String[] val2) throws SQLException {
        StringBuilder sql = new StringBuilder("update ");
        sql.append(main);
        sql.append(" set ");
        for(int i =0; i < key1.length; i++) {
            sql.append(key1[i]).append(" = ?");
            if(i!=key1.length-1) sql.append(",");
        }
        sql.append(" where ");
        for(int i =0; i < val1.length; i++) {
            sql.append(key2[i]).append(" = ?");
            if(i!=val2.length-1) sql.append(",");
        }
        PreparedStatement ps = conn.prepareStatement(sql.toString());
        int i =0;
        for(i =0; i < val1.length; i++) {
            ps.setString(i+1, val2[i]);
        }
        for (int j=i; j < val2.length+i; j++) {
            ps.setString(i+1, val1[j-i]);
        }

        return ps.executeUpdate();

    }

    public static ResultSet find(Connection conn,String sql,String[] val) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ps = conn.prepareStatement(sql);
        for(int i =0; i < val.length; i++) {
            ps.setString(i+1, val[i]);
        }
        rs = ps.executeQuery();
        return rs;
    }


}
