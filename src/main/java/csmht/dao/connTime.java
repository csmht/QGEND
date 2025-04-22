package csmht.dao;

import java.sql.Connection;
import java.util.concurrent.ConcurrentHashMap;

public class connTime {

   public static ConcurrentHashMap<Connection,Long> map = new ConcurrentHashMap<>();
   public void In(Connection conn) {
       map.put(conn,System.currentTimeMillis());
   }

    public void delate(Connection conn){
       map.remove(conn);
   }


}
