package csmht.Dao.ClassObject;

public class FollowBoard {

    private String follow_time;

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
