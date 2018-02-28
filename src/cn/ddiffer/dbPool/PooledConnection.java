package cn.ddiffer.dbPool;

/**
 * 实现自定义的连接池
 * Created by Zidong on 2018/2/26 18:52
 */
public class PooledConnection implements IMyPool {

    @Override
    public PooledConnection getConnection() {
        return null;
    }

    @Override
    public void createConnections(int count) {

    }
}
