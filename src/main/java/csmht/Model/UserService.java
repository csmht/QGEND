package csmht.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public interface UserService {
    /**
     * 用户举报模块
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserBrowsePost(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 关注模块
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserFollowBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 取关模块
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserUnFollowBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException;


    /**
     * 喜欢帖子
     * @param req
     * @param res
     * @throws SQLException
     * @throws IOException
     * @throws InterruptedException
     */
    public void UserLike(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException;

    /**
     * 取消喜欢帖子
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserUnLike(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 关注用户
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserFollowUser(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 取关用户
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserUnFollowUser(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 举报帖子
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserBrowseBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 发帖
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserAddPost(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 删贴
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserDeletePost(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 申请模块
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserAddBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 删除模块
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserDeleteBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 评论
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserAddComment(HttpServletRequest req, HttpServletResponse res) throws SQLException;

    /**
     * 删评论
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UserDeleteComment(HttpServletRequest req, HttpServletResponse res) throws SQLException;
}
