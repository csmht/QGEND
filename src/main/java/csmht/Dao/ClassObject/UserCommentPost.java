package csmht.Dao.ClassObject;

import com.alibaba.fastjson2.annotation.JSONField;
import csmht.Dao.UserClass;

import java.util.List;

public class UserCommentPost extends UserClass {
    @JSONField(name="comment_id")
    private int comment_Id;

    @JSONField(name="likes")
    private int likes;

    @JSONField(name="post_id")
    private int post_Id;

    @JSONField(name="comment")
    private String comment;

    @JSONField(name="create_time")
    private String create_time;

    @JSONField(name="comment_comment")
    private List<UserCommComm> commComment;

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
    public String getCreate_time() {
        return create_time;
    }
    public void setCreate_time(String creat_time) {
        this.create_time = creat_time;
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

    public String getComment_comment() {
        return comment;
    }

    public void setComment_comment(String comment_comment) {
        this.comment = comment_comment;
    }

}
