package csmht.Model.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public interface UserService {
    /**
     * 用户举报帖子
     * 帖子id  用户id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void BrowsePost(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, ParseException, InterruptedException;


    /**
     * 关注模块
     * boar_id模块id user_id用户id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void FollowBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, ParseException, InterruptedException;

    /**
     * 取关模块
     * user_id 用户id  board_id 模块id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UnFollowBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, ParseException, InterruptedException;


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
    public void FollowUser(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException, ParseException;

    /**
     * 取关用户
     * user_id user2_id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void UnFollowUser(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException, ParseException;

    /**
     * 举报模块
     * user_id board_id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void BrowseBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, ParseException, InterruptedException;

    /**
     * 举报用户
     * user_id a
     * @param req
     * @param res
     * @throws SQLException
     */
    public void BrowseUser(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, ParseException, InterruptedException;

    /**
     * 退出登录
     * @param req
     * @param resp
     */
    public void Logout(HttpServletRequest req, HttpServletResponse resp) throws IOException;

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
     * user_id post_id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void addCommentToPost(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException, ParseException;


    /**
     * 评论评论
     * user_id post_id comment_id
     * @param req
     * @param res
     * @throws SQLException
     */
    public void addCommentToComment(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException, ParseException;

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

    /**
     * 判断是否关注用户
     * user_id user_id
     * @param req
     * @param res
     * @throws SQLException
     * @throws IOException
     * @throws InterruptedException
     */
    public void isFollowUser(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException, ParseException;

    /**
     * 判断是否关注贴吧
     * user_id board_id
     * @param req
     * @param res
     * @throws SQLException
     * @throws IOException
     * @throws InterruptedException
     */
    public void isFollowBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException, ParseException;

    /**
     * 是否为贴吧管理员
     * @param req
     * @param res
     * @throws SQLException
     * @throws IOException
     * @throws InterruptedException
     * @throws ParseException
     */
    public void isBoardAdmin(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException, ParseException;

    /**
     * 修改模块信息
     * @param req
     * @param res
     * @throws SQLException
     * @throws IOException
     * @throws InterruptedException
     * @throws ParseException
     */
    public void UpdateBoard(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException, ParseException;

    /**
     * 被举报的帖子
     * board_id
     * @param req
     * @param res
     * @throws SQLException
     * @throws IOException
     * @throws InterruptedException
     * @throws ParseException
     */
    public void FindBrowsePost(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException, ParseException;

    /**
     * 禁止用户在该模块发帖
     * user_id board_id
     * @param req
     * @param res
     * @throws SQLException
     * @throws IOException
     * @throws InterruptedException
     * @throws ParseException
     */
    public void BanUser(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, InterruptedException, ParseException;
}
