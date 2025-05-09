package csmht.dao.classobject;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


public class Post extends UserClass {


    @JSONField(name="post_id")
    private int post_id = -1;

    @JSONField(name="title")
    private String title;

    @JSONField(name="content")
    private String content;

    @JSONField(name="likes")
    private int likes;

    @JSONField(name="views")
    private int views;

    @JSONField(name="create_time")
    private String create_time;

    @JSONField(name="board_id")
    private int board_id = -1;

    @JSONField(name="PostComment")
    private List<Comment> comments = new ArrayList<>();

    @JSONField(name="image")
    private String image;

    @JSONField(name="new_time")
    private String new_time;

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikes() {
        return this.likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
    public int getBoard_id() {
        return board_id;
    }

    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

 public String getImage() {
        return image;
 }

 public void setImage(String image) {
        this.image = image;
 }

 public void addImage(byte[] image) {
        this.image = Base64.getEncoder().encodeToString(image);
 }

 public String getNew_time() {
        return new_time;
 }

 public void setNew_time(String new_time) {
        this.new_time = new_time;
 }

}
