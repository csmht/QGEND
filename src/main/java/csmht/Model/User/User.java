package csmht.Model.User;

import com.alibaba.fastjson2.annotation.JSONField;

public class User {

    @JSONField(name = "User_ID")
    int id;

    @JSONField(name = "UserID")
    String UserID;

    @JSONField(name="PassWord")
    String password;


    @JSONField(name = "email")
    String email;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userId) {
        this.UserID = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
