package csmht.Dao.ClassObject;

public class LikePost {


    private Post post;


    private String like_time;



    public LikePost(Post post, String like_time) {
        this.post = post;
        this.like_time = like_time;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getLike_time() {
        return like_time;
    }

}
