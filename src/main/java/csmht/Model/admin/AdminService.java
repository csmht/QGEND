package csmht.Model.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public interface AdminService {
    /**
     * 要审查的模块
     * @param req
     * @param resp
     */
    public void FindPassBoard(HttpServletRequest req, HttpServletResponse resp) throws SQLException, InterruptedException, IOException;

    /**
     * 通过模块
     * @param req
     * @param resp
     */
    public void PassBoard(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException;

    /**
     * 不通过模块
     * @param req
     * @param resp
     */
    public void UnPassBoard(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException;

    /**
     * 查找被举报的用户
     * @param req
     * @param resp
     */
    public void FindBanUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, InterruptedException, IOException;

    /**
     * 封禁用户
     * @param req
     * @param resp
     */
    public void BanUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException;

    /**
     * 不封禁用户
     * @param req
     * @param resp
     */
    public void UnBanUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, InterruptedException;

    /**
     * 查询申请模块
     * @param req
     * @param resp
     * @throws SQLException
     * @throws InterruptedException
     * @throws IOException
     */
    public void FindBoard(HttpServletRequest req, HttpServletResponse resp) throws SQLException, InterruptedException, IOException;

    /**
     * 查找日志
     * @param req
     * @param resp
     * @throws SQLException
     * @throws InterruptedException
     * @throws IOException
     */
    public void FindLog(HttpServletRequest req, HttpServletResponse resp) throws SQLException, InterruptedException, IOException;

    /**
     * 封禁模块
     * @param req
     * @param resp
     * @throws SQLException
     * @throws InterruptedException
     * @throws IOException
     */
    public void BanBoard(HttpServletRequest req, HttpServletResponse resp) throws SQLException, InterruptedException, IOException;

    /**
     * 不封禁模块
     * @param req
     * @param resp
     * @throws SQLException
     * @throws InterruptedException
     * @throws IOException
     */
    public void UnBanBoard(HttpServletRequest req, HttpServletResponse resp) throws SQLException, InterruptedException, IOException;
}
