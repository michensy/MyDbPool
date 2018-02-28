package cn.ddiffer.dbPool;

/**
 * 实现自定义的连接池
 * Created by Zidong on 2018/2/26 18:52
 */
public class PooledConnection implements IMyPool {
    // 数据库驱动
    private String jdbcDriver = "";

    // 数据库连接参数
    private String jdbcUrl = "";

    private String userName = "";

    private String password = "";

    // 连接池规格
    private static int initCount;

    private static int stepSize;

    private static int maxSize;

    public PooledConnection() {
        init();
    }

    /**
     * 初始化方法
     */
    private void init() {

    }


    @Override
    public PooledConnection getConnection() {
        return null;
    }

    @Override
    public void createConnections(int count) {

    }
}