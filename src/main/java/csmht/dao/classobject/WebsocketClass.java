package csmht.dao.classobject;

import com.alibaba.fastjson2.annotation.JSONField;
import csmht.dao.JDBC;
import csmht.dao.Pool;
import csmht.dao.UserClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WebsocketClass {

    @JSONField(name="id")
    private int id;

    @JSONField(name="form")
    private int form;

    @JSONField(name="to")
    private int to;

    @JSONField(name="content")
    private String content;

    @JSONField(name="users")
    private List<UserClass> users;

    @JSONField(name="websocketClasses")
    private List<WebsocketClass> websocketClasses;

    public WebsocketClass(int form, int to, String content) {
        this.form = form;
        this.to = to;
        this.content = content;
    }
    public WebsocketClass() {}

    public int getForm() {
        return form;
    }
    public void setForm(int form) {
        this.form = form;
    }
    public int getTo() {
        return to;
    }
    public void setTo(int to) {
        this.to = to;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

   public List<UserClass> getUsers() {
        return users;
   }

   public void setUsers(List<UserClass> users) {
        this.users = users;
   }

    public List<WebsocketClass> getWebsocketClasses() {
        return websocketClasses;
    }

    public void setWebsocketClasses(List<WebsocketClass> websocketClasses) {
        this.websocketClasses = websocketClasses;
    }

    public static void Save(Object form, Object to, String content) throws SQLException, InterruptedException {
        Connection con = Pool.Pool.getPool();
        con.setAutoCommit(false);
        String[] key = {"form","toto","content"};
        Object[] value = {form,to,content};
        JDBC.add(con,"chat",key,value);
        con.commit();
        Pool.Pool.returnConn(con);
    }

    public static void Save(WebsocketClass wc) throws SQLException, InterruptedException {
        WebsocketClass.Save(wc.getForm(),wc.getTo(), wc.getContent());
    }

    public static List<WebsocketClass> Read(String who) throws SQLException, InterruptedException {
        Connection con = Pool.Pool.getPool();
        List<WebsocketClass> list = new ArrayList<WebsocketClass>();

        String[] main = {"chat"};
        String[] sub = {};
        String[] key = {"form"};
        String[] value = {who};

        ResultSet rs = JDBC.find(con,main,sub,key,value,"");
        while (rs.next()) {
            WebsocketClass wc = new WebsocketClass();
            wc.setForm(rs.getInt("form"));
            wc.setTo(rs.getInt("toto"));
            wc.setContent(rs.getString("content"));
            list.add(wc);
        }
        key[0] = "toto";
        rs = JDBC.find(con,main,sub,key,value,"");
        while (rs.next()) {
            WebsocketClass wc = new WebsocketClass();
            wc.setForm(rs.getInt("form"));
            wc.setTo(rs.getInt("toto"));
            wc.setContent(rs.getString("content"));
            list.add(wc);
        }

        Pool.Pool.returnConn(con);
        return list;
    }

}
