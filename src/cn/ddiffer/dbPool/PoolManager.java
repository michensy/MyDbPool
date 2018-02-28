package cn.ddiffer.dbPool;

/**
 * 用于获取单例的 PoolConnection对象
 * Created by Zidong on 2018/3/1 3:27
 */
public class PoolManager {

    // 使用内部类的方式实现单例管理
    private static class createMyPoolImpl{
        private static MyPoolImpl myPool = new MyPoolImpl();
    };

    public static MyPoolImpl getMyPoolImplInstance() {
        return createMyPoolImpl.myPool;
    }
}
