package csmht.Dao.ClassObject;

import com.alibaba.fastjson2.annotation.JSONField;
import csmht.Dao.UserClass;

import java.util.ArrayList;
import java.util.List;

public class Comment extends UserClass {
    @JSONField(name="comment_id")
    private int comment_Id;

    @JSONField(name="likes")
    private int likes;

    @JSONField(name="post_id")
    private int post_Id;

    @JSONField(name="content")
    private String content;

    @JSONField(name="create_time")
    private String create_time;

    @JSONField(name="comment_comment")
    private List<Comment> commComment = new ArrayList<>();

    public int getComment_Id() {
        return comment_Id;
    }

    public void setComment_Id(int comment_Id) {
        this.comment_Id = comment_Id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public List<Comment> getCommComment() {
        return commComment;
    }

    public void setCommComment(List<Comment> commComment) {
        this.commComment = commComment;
    }

    public void addComment(Comment comment) {
        this.commComment.add(comment);
    }

}
