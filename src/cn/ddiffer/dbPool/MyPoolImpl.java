package cn.ddiffer.dbPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;


/**
 * 实现自定义的连接池
 * Created by Zidong on 2018/2/26 18:52
 */
public class MyPoolImpl implements IMyPool {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    // 数据库驱动
    private String jdbcDriver;

    // 数据库连接参数
    private String jdbcUrl;
    private String userName;
    private String password;

    // 连接池对象
    private static Vector<PoolConnection> poolConnections = new Vector<PoolConnection>();

    // 连接池规格
    private static int initCount;
    private static int stepSize;
    private static int maxSize;

    public MyPoolImpl() {
        init();
    }

    /**
     * 初始化方法，将按照配置参数创建连接池
     */
    private void init() {
        // 从流中读取 db.properties文件中的参数
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("db.Properties");
        Properties pp = new Properties();
        try {
            pp.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 初始化数据库连接参数
        jdbcDriver = pp.getProperty("jdbcDriver");
        jdbcUrl = pp.getProperty("jdbcUrl");
        userName = pp.getProperty("userName");
        password = pp.getProperty("password");
        // 初始化连接池参数
        initCount = Integer.parseInt(pp.getProperty("initCount"));
        stepSize = Integer.parseInt(pp.getProperty("stepSize"));
        maxSize = Integer.parseInt(pp.getProperty("maxSize"));
        // 注册驱动
        try {
           Driver driver  = (Driver) Class.forName(jdbcDriver).newInstance();
           DriverManager.registerDriver(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 创建连接
        createConnections(initCount);
    }

    /**
     * 从连接池中获取连接对象
     * @return
     */
    @Override
    public PoolConnection getConnection() {
        PoolConnection conn = null;
        // 如果连接池中没有连接，则先创建连接池
        if (poolConnections == null || poolConnections.size() <= 0) {
            createConnections(initCount);
        }
        conn = getConn();
        while (conn == null) {
            logger.info("所有连接均被占用，正在扩容连接池。。。");
            createConnections(stepSize);
            conn = getConn();
        }
        return conn;
    }

    public synchronized PoolConnection getConn() {
        // 遍历连接池
        for (PoolConnection poolConnection : poolConnections) {
            if(!poolConnection.isBusy()) {
                Connection conn = poolConnection.getConn();
                try {
                    // 如果 Connection不可用，则创建新的 Connection替换掉
                    if (!conn.isValid(2000)) {
                        conn = DriverManager.getConnection(jdbcUrl, userName, password);
                        return poolConnection;
                    }
                    poolConnection.setBusy(true);
                    return poolConnection;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // 说明连接池中所有的连接对象均被占用
        return null;
    }

    /**
     * 创建指定数量的 Connection并存放到连接池对象中
     * @param initCount
     */
    @Override
    public void createConnections(int initCount) {
        Connection connection = null;
        if (initCount <= 0) return;
        if (poolConnections.size() + initCount > maxSize) {
            logger.info("指定创建的连接对象数量超过最大值。。。已经将连接数量设置为最大值。。。");
            initCount = maxSize;
        }
        for (int i = 0; i < initCount; i++) {
            try {
                connection = DriverManager.getConnection(jdbcUrl, userName, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (connection != null) {
                PoolConnection poolConnection = new PoolConnection(connection, false);
                // 存入连接池对象中
                poolConnections.add(poolConnection);
            }
        }
    }
}