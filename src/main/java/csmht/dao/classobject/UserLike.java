package csmht.dao.classobject;

import com.alibaba.fastjson2.annotation.JSONField;

public class UserLike extends UserClass {

    @JSONField(name="post_id")
    private int post_id;



    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }
}
