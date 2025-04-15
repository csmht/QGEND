package csmht.Dao;

import com.alibaba.fastjson2.annotation.JSONField;

public class UserClass {
    @JSONField(name="user_id")
    int user_id = -1;

    @JSONField(name = "UserName")
    private String UserName;

    @JSONField(name="user_likes")
    private int user_likes;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_likes() {
        return user_likes;
    }

    public void setUser_likes(int user_likes) {
        this.user_likes = user_likes;
    }
}
