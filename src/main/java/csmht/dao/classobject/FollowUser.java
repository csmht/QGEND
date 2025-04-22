package csmht.dao.classobject;

import com.alibaba.fastjson2.annotation.JSONField;

public class FollowUser {

    @JSONField(name="follow_time")
    private String follow_time;

    @JSONField(name="follow_user")
    private User follow_user;

    public FollowUser(User follow_user, String follow_time) {
        this.follow_time = follow_time;
        this.follow_user = follow_user;
    }

    public void setFollow_user(User follow_user) {
        this.follow_user = follow_user;
    }
    public User getFollow_user() {
        return follow_user;
    }
    public void setFollow_time(String follow_time) {
        this.follow_time = follow_time;
    }
    public String getFollow_time() {
        return follow_time;
    }
}
