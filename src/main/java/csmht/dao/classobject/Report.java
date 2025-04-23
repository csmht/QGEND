package csmht.dao.classobject;

import com.alibaba.fastjson2.annotation.JSONField;

public class Report {

    @JSONField(name="report_id")
    private int report_id;

    @JSONField(name="report_user_id")
    private int report_user_id;

    @JSONField(name="report_time")
    private String report_time;

    @JSONField(name="post")
    private Post post;

    @JSONField(name="board")
    private Board board;

    @JSONField(name="user")
    private User user;

    @JSONField(name="report_content")
    private String content;

    public int getReport_user_id() {
        return report_user_id;
    }
    public void setReport_user_id(int report_user_id) {
        this.report_user_id = report_user_id;
    }
    public String getReport_time() {
        return report_time;
    }

    public void setReport_time(String report_time) {
        this.report_time = report_time;
    }

    public Post getPost() {
        return post;
    }
    public void setPost(Post post) {
        this.post = post;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getReport_id() {
        return report_id;
    }

    public void setReport_id(int report_id) {
        this.report_id = report_id;
    }

}
