package csmht.dao;

import redis.clients.jedis.Jedis;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JedisTool {

    static {

    }


    public static void LikeComment(String user_id,String comment_id) throws ParseException {
        String userLike = "user" + user_id + "likeComment";
        Jedis jedis = RedisPool.getJedis();
        String now = BaseString.getTime("1000-01-01 00:00:00");
        jedis.hset(userLike,comment_id,now);
        String postLike = "comment" + comment_id + "like";
        jedis.sadd(postLike,user_id);
        jedis.incrBy("commentLike"+comment_id,1);
        jedis.close();
    }

    public static void unLikeComment(String user_id,String comment_id) throws ParseException {
        String userLike = "user" + user_id + "likeComment";
        Jedis jedis = RedisPool.getJedis();

        jedis.hdel(userLike,comment_id);
        String postLike = "comment" + comment_id + "like";
        jedis.srem(postLike,user_id);
        jedis.incrBy("commentLike"+comment_id,-1);
        jedis.close();
    }

    public static int FindCommentLike(String comment_id) throws ParseException {
        Jedis jedis = RedisPool.getJedis();
        int ans;
        if(jedis.get("commentLike"+comment_id)==null){
            ans = 0;
        }else {
            ans = Integer.parseInt(jedis.get("commentLike"+comment_id));
        }


        jedis.close();
        return ans;
    }


    public static void ViewPost(String user_id,String post_id) throws ParseException {
        String now = BaseString.getTime("1000-01-01 00:00:00");

        Jedis jedis = RedisPool.getJedis();

        String userLike = "user" + user_id + "view";
        jedis.hset(userLike,post_id,now);
        String postLike = "post" + post_id + "view";
        jedis.sadd(postLike,user_id);

        jedis.incrBy("postView" + post_id,1);

        jedis.close();
    }

    public static Map<String, String> FindViewPost(String user_id){
        String userFollow = "user" + user_id + "view";
        Jedis jedis = RedisPool.getJedis();
        Map<String,String> map =jedis.hgetAll(userFollow);
        jedis.close();
        return map;
    }

    public static boolean isLike(String user_id,String post_id){
        Jedis jedis = RedisPool.getJedis();
        String userLike = "user" + user_id + "like";
        boolean isLike = jedis.hexists(userLike,post_id);
        jedis.close();
        return isLike;
    }

    public static void unLike(String user_id,String post_id){
        Jedis jedis = RedisPool.getJedis();
        String userUnLike = "user" + user_id + "like";
        jedis.hdel(userUnLike,post_id);
        String postLike = "post" + post_id + "like";
        jedis.srem(postLike,user_id);

        jedis.incrBy("post" + post_id +"Like",-1);
        jedis.close();
    }

    public static void Like(String user_id,String post_id) throws ParseException {
        String now = BaseString.getTime("1000-01-01 00:00:00");

        Jedis jedis = RedisPool.getJedis();

        String userLike = "user" + user_id + "like";
        jedis.hset(userLike,post_id,now);
        String postLike = "post" + post_id + "like";
        jedis.sadd(postLike,user_id);


        jedis.incr("post"+ post_id +"Like" );
        jedis.close();
    }

    public static boolean isFollowBoard(String user_id,String board_id){
        Jedis jedis = RedisPool.getJedis();
        String userFollow = "user" + user_id + "follow";
        boolean isFollow = jedis.hexists(userFollow,board_id);
        jedis.close();
       return isFollow;
    }

    public static void unFollowBoard(String user_id,String board_id){
        Jedis jedis = RedisPool.getJedis();
        String userUnFollow = "user" + user_id + "follow";
        jedis.hdel(userUnFollow,board_id);
        String boardLike = "board" + board_id + "follow";
        jedis.srem(boardLike,board_id);

        jedis.incrBy("boardFollow" + board_id,-1);
        jedis.close();
    }

    public static void FollowBoard(String user_id,String board_id) throws ParseException {
        String now = BaseString.getTime("1000-01-01 00:00:00");

        Jedis jedis = RedisPool.getJedis();
        String userFollow = "user" + user_id + "follow";
        jedis.hset(userFollow,board_id,now);
        String boardLike = "board" + board_id + "follow";
        jedis.sadd(boardLike,user_id);

        jedis.incrBy("boardFollow" + board_id,1);
        jedis.close();

    }

    public static Map<String, String> FindFollowBoard(String user_id){

        String userFollow = "user" + user_id + "follow";

        Jedis jedis = RedisPool.getJedis();
        Map<String,String> map =jedis.hgetAll(userFollow);

        jedis.close();
        return map;


    }

    public static Map<String, String> FindLikeUser(String user_id){
        String userLike = "user" + user_id + "like";

        return getStringStringMap(userLike);
    }

    private static Map<String, String> getStringStringMap(String userLike) {
        Jedis jedis = RedisPool.getJedis();
        Map<String,String> map =jedis.hgetAll(userLike);
        jedis.close();
        return map;
    }


    public static void FollowUser(String user_id,String board_id) throws ParseException {
        String now = BaseString.getTime("1000-01-01 00:00:00");
        Jedis jedis = RedisPool.getJedis();
        String userFollow = "userFollow" + user_id + "follow";
        jedis.hset(userFollow,board_id,now);
        String boardLike = "userFollow" + board_id + "follower";
        jedis.sadd(boardLike,user_id);

        jedis.close();
    }



    public static void unFollowUser(String user_id,String board_id){
        Jedis jedis = RedisPool.getJedis();
        String userUnFollow = "userFollow" + user_id + "follow";
        jedis.hdel(userUnFollow,board_id);
        String boardLike = "userFollow" + board_id + "follower";
        jedis.sadd(boardLike,user_id);
        jedis.close();
    }

    public static Boolean isFollowUser(String user_id,String board_id){
        Jedis jedis = RedisPool.getJedis();
        String userFollow = "userFollow" + user_id + "follow";
        Boolean isFollow = jedis.hexists(userFollow,board_id);
        jedis.close();
        return isFollow;
    }

    public static Map<String,String> FindFollowUser(String user_id){
        String userFollow = "userFollow" + user_id + "follow";
        return getStringStringMap(userFollow);
    }

    public static int FindLikesPost(String user_id){
        Jedis jedis = RedisPool.getJedis();
        String userLike = "post" + user_id + "Like";
        int ans;
        if(jedis.get(userLike)==null){
            ans = 0;
        }  else {
            ans = Integer.parseInt(jedis.get(userLike));
        }

        jedis.close();
        return ans;
    }

    public static int FindLikesBoard(String boardId) {
        Jedis jedis = RedisPool.getJedis();
        String userLike = "boardFollow" + boardId;
        int ans;
        if(jedis.get(userLike) == null) {
            ans = 0;
        }else {
            ans = Integer.parseInt(jedis.get(userLike));
        }


        jedis.close();
        return ans;
    }

    public static int FindPostView(String s) {
        Jedis jedis = RedisPool.getJedis();
        String userLike = "postView" + s ;

        int ans;
        if(jedis.get(userLike) == null) {
            ans = 0;
        }else {
            ans = Integer.parseInt(jedis.get(userLike));
        }


        jedis.close();
        return ans;
    }
}
