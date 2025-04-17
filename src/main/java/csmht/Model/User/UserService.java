package csmht.Model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public interface UserService {
    /**
     * 用户举报模块
     * 模块id  用户id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void BrowsePost(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 关注模块
     * boar_id模块id user_id用户id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void FollowBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 取关模块
     * user_id 用户id  board_id 模块id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UnFollowBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException;


    /**
     * 喜欢帖子
     * user_id 用户id post_id帖子id
     * @param req
     * @param res
     * @throws SQLException
     * @throws IOException
     * @throws InterruptedException
     */
    public void Like(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException;

    public void isLike(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException;

    /**
     * 取消喜欢帖子
     * user_id  post_id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UnLike(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException;

    /**
     * 关注用户
     * user_id user2_id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void FollowUser(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 取关用户
     * user_id user2_id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UnFollowUser(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 举报帖子
     * user_id post_id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void BrowseBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 发帖
     * user_id board_id content title image[9] create_time
     * @param req
     * @param res
     * @throws SQLException
     */
    public void AddPost(HttpServletRequest req, HttpServletResponse res) throws SQLException, InterruptedException, IOException, ParseException;

    /**
     * 删贴
     * user_id post_id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void DeletePost(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException, ParseException;

    /**
     * 申请模块
     * user_id   boardName
     * @param req
     * @param res
     * @throws SQLException
     */
    public void AddBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, ParseException, InterruptedException;

    /**
     * 删除模块
     * user_id board_id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void DeleteBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException;

    /**
     * 评论帖子
     * user_id comment_id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void AddCommentToPost(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 删除帖子评论
     * user_id comment_id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void DeleteCommentToPost(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 修改用户信息
     * User_id userName Email image
     * @param req
     * @param res
     * @throws SQLException
     */
    public void ChangeUser(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException;



}
