package cn.ddiffer.dbPool;


import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Zidong on 2018/3/1 4:29
 */
public class PoolDemo {

    // 获得连接池
    private static MyPoolImpl myPool = PoolManager.getMyPoolImplInstance();

    public static void main(String[] args) throws SQLException {
        // 获取连接池对象
        PoolConnection connection = myPool.getConnection();
        // 执行 sql
        ResultSet resultSet = connection.executeSqlAndGetResult("select * from user");
        // 遍历结果集
        if (resultSet != null) {
            while (resultSet.next()){
                String name = resultSet.getString("name");
                System.out.println(name);
            }
        }
    }


}
