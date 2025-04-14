package csmht.Dao.ClassObject;

import com.alibaba.fastjson2.annotation.JSONField;
import csmht.Dao.UserClass;

import java.util.ArrayList;
import java.util.List;

public class Board extends UserClass {
    @JSONField(name="board_id")
    private int board_id = -1;

    @JSONField(name="title")
    private String title;

    @JSONField(name="content")
    private String content;

    @JSONField(name="pass")
    private boolean pass;

    @JSONField(name="create_time")
    private String create_time;

    @JSONField(name="image")
    private byte[] image;

    @JSONField(name="post")
    private List<Post> post = new ArrayList<>();

    @JSONField(name="likes")
    private int likes;

    public int getBoard_id() {
        return board_id;
    }
    public void setBoard_id(int board_id) {
        this.board_id = board_id;
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

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }

    public void addPost(Post post) {
        this.post.add(post);
    }

    public List<Post> getPost() {
        return post;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

}
