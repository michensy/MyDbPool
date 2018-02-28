package cn.ddiffer.dbPool;

/**
 * Created by Zidong on 2018/2/26 18:53
 */
public interface IMyPool {

    /**
     * 获取数据库连接
     * @return
     */
    PoolConnection getConnection();

    /**
     * 创建数据库连接
     * @param count
     */
    void createConnections(int count);

}
