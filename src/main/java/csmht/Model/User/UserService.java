package csmht.Model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public interface UserService {
    /**
     * 用户举报模块
     * 模块id  用户id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserBrowsePost(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 关注模块
     * boar_id模块id user_id用户id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserFollowBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 取关模块
     * user_id 用户id  board_id 模块id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserUnFollowBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException;


    /**
     * 喜欢帖子
     * user_id 用户id post_id帖子id
     * @param req
     * @param res
     * @throws SQLException
     * @throws IOException
     * @throws InterruptedException
     */
    public void UserLike(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException;

    /**
     * 取消喜欢帖子
     * user_id  post_id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserUnLike(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 关注用户
     * user_id user2_id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserFollowUser(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 取关用户
     * user_id user2_id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserUnFollowUser(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 举报帖子
     * user_id post_id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserBrowseBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 发帖
     * user_id board_id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserAddPost(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 删贴
     * user_id post_id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserDeletePost(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 申请模块
     * user_id
     * Board
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserAddBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 删除模块
     * board_id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserDeleteBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 评论帖子
     * comment_id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserAddCommentToPost(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 删除帖子评论
     * comment_id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserDeleteCommentToPost(HttpServletRequest req, HttpServletResponse res) throws SQLException;
}
