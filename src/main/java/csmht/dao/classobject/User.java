package csmht.dao.classobject;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

public class User extends UserClass {

    @JSONField(name="captcha")
    public String captcha;

    @JSONField(name="PassWord")
    private String password;

    @JSONField(name = "Email")
    private String email;

    @JSONField(name="admin")
    private Boolean admin;

    @JSONField(name="pass")
    private String pass;

    @JSONField(name="image")
    private String image;

    @JSONField(name="create_time")
    private String create_time;

    @JSONField(name="follow_user")
    private List<FollowUser> follow_user = new ArrayList<FollowUser>();

    @JSONField(name="follow_board")
    private List<FollowBoard> follow_board = new ArrayList<>();

    @JSONField(name="like_post")
    private List<LikePost> like_post = new ArrayList<>();

    @JSONField(name="history_post")
    private List<HistoryPost> history_post = new ArrayList<>();

    @JSONField(name="")




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

    public Boolean getAdmin() {return admin;}

    public void setAdmin(Boolean admin) {this.admin = admin;}

    public String getPass() {return pass;}

    public void setPass(String pass) {this.pass = pass;}

    public String getImage() {return image;}

    public void setImage(String image) {this.image = image;}

    public String getCreate_time() {return create_time;}

    public void setCreate_time(String create_time) {this.create_time = create_time;}

    public List<FollowUser> getFollow_user() {return follow_user;}

    public void setFollow_user(List<FollowUser> follow_user) {this.follow_user = follow_user;}

    public void addFollowUser(FollowUser follow_user) {
        this.follow_user.add(follow_user);
    }

    public List<FollowBoard> getFollow_board() {return follow_board;}

    public void setFollow_board(List<FollowBoard> follow_board) {this.follow_board = follow_board;}

    public void addFollowBoard(FollowBoard follow_board) {
        this.follow_board.add(follow_board);
    }

    public List<LikePost> getLike_post() {return like_post;}

    public void setLike_post(List<LikePost> like_post) {this.like_post = like_post;}

    public void addLikePost(LikePost like_post) {
        this.like_post.add(like_post);
    }

    public List<HistoryPost> getHistory_post() {return history_post;}

    public void setHistory_post(List<HistoryPost> history_post) {

        this.history_post = history_post;
    }

    public void addHistoryPost(HistoryPost history_post) {
        this.history_post.add(history_post);
    }

    public String getCaptcha() {return captcha;}

    public void setCaptcha(String captcha) {this.captcha = captcha;}
}
