package csmht.Dao.ClassObject;

import com.alibaba.fastjson2.annotation.JSONField;
import csmht.Dao.UserClass;

public class UserCommentPost extends UserClass {
    @JSONField(name="comment_id")
    private int comment_Id;

    @JSONField(name="likes")
    private int likes;

    @JSONField(name="post_id")
    private int post_Id;

    @JSONField(name="comment")
    private String comment;

    @JSONField(name="creat_time")
    private String creat_time;

    public int getComment_Id() {
        return comment_Id;
    }
    public void setComment_Id(int comment_Id) {
        this.comment_Id = comment_Id;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getCreat_time() {
        return creat_time;
    }
    public void setCreat_time(String creat_time) {
        this.creat_time = creat_time;
    }

    public int getLikes() {
        return likes;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }
    public int getPost_Id() {
        return post_Id;
    }
    public void setPost_Id(int post_Id) {
        this.post_Id = post_Id;
    }


}
