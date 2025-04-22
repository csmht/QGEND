package csmht.dao.classobject;

import com.alibaba.fastjson2.annotation.JSONField;
import csmht.dao.UserClass;

public class CommComment extends UserClass {

    @JSONField(name="commcomm_id")
    private int commComm_id;

    @JSONField(name="comm_id")
    private int comm_id;

    @JSONField(name="comment")
    private String comment;

    @JSONField(name="likes")
    private int likes;

    @JSONField(name="create_id")
    private String create_time;


    public void setCommComm_id(int commComm_id) {
        this.commComm_id = commComm_id;
    }

    public int getCommComm_id() {
        return commComm_id;
    }

    public void setComm_id(int comm_id) {
        this.comm_id = comm_id;
    }

    public int getComm_id() {
        return comm_id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getLikes() {
        return likes;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCreate_time() {
        return create_time;
    }
}
