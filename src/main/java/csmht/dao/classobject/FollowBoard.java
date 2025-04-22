package csmht.dao.classobject;

import com.alibaba.fastjson2.annotation.JSONField;

public class FollowBoard {


    @JSONField(name="follow_time")
    private String follow_time;

    @JSONField(name="follow_board")
    private Board board;

    public FollowBoard(Board board, String follow_time) {
        this.board = board;
        this.follow_time = follow_time;
    }

    public void setFollow_time(String follow_time) {
        this.follow_time = follow_time;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getFollow_time() {
        return follow_time;
    }

    public Board getBoard() {
        return board;
    }
}
