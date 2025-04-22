package csmht.dao.classobject;

import com.alibaba.fastjson2.annotation.JSONField;

public class HistoryPost {

    @JSONField(name="view_time")
    private String view_time;

    @JSONField(name="history_post")
    private Post post;

    public HistoryPost(Post post, String view_time) {
        this.post = post;
        this.view_time = view_time;
    }

    public String getView_time() {
        return view_time;
    }

    public void setView_time(String view_time) {
        this.view_time = view_time;
    }

    public Post getHistoryPost() {
        return post;
    }

    public void setHistoryPost(Post post) {
        this.post = post;
    }

}
