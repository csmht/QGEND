package csmht.Model.FindData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public interface FindDataServlet {

    /**
     * 创造者查找多个模块
     * 热度排序
     * @param req
     * @param resp
     */
    public void UserManyBoardHot(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, InterruptedException;

    /**
     * 创造者查找多个模块
     * 时间排序
     * @param req
     * @param resp
     */
    public void UserManyBoardNew(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException;

    /**
     * 名字查找多个模块
     * 热度排序
     * @param req
     * @param resp
     */
    public void NameManyBoardHot(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, InterruptedException;

    /**
     * 名字查找多个模块
     * 时间排序
     * @param req
     * @param resp
     */
    public void NameManyBoardNew(HttpServletRequest req, HttpServletResponse resp);

    /**
     * 查找单个模块
     * @param req
     * @param resp
     */
    public void OneBoard(HttpServletRequest req, HttpServletResponse resp);

    /**
     * 查找多个帖子
     * 热度排序
     * @param req
     * @param resp
     */
    public void ManyPostHot(HttpServletRequest req, HttpServletResponse resp);

    /**
     * 查找多个帖子
     * 时间排序
     * @param req
     * @param resp
     */
    public void ManyPostNew(HttpServletRequest req, HttpServletResponse resp);

    /**
     * 查找单个帖子
     * @param req
     * @param resp
     */
    public void OnePost(HttpServletRequest req, HttpServletResponse resp);

    /**
     * 查找多个用户，默认热度排序
     * @param req
     * @param resp
     */
    public void ManyUser(HttpServletRequest req, HttpServletResponse resp);

    /**
     * 查找单个用户
     * @param req
     * @param resp
     */
    public void OneUser(HttpServletRequest req, HttpServletResponse resp);



}
