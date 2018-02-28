package cn.ddiffer.dbPool;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 连接池对象
 * Created by Zidong on 2018/3/1 0:14
 */
public class PoolConnection {

    private Connection conn; /*Connection对象*/

    private boolean isBusy;  /*当前连接是否被占用*/

    /**
     * 创建连接池对象
     * @param conn 连接对象
     * @param isBusy 是否被占用
     */
    PoolConnection(Connection conn, boolean isBusy) {
        this.conn = conn;
        this.isBusy = isBusy;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    /**
     * 执行 sql，返回资源
     * @param sql
     * @return
     */
    public ResultSet executeSqlAndGetResult(String sql) {
        Statement statement = null;
        ResultSet resultSet = null;
        // 获得执行平台
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
