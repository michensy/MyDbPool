# 手写实现自定义连接池

## 运行环境

* idea

* jdk 1.7

* mysql-connector-java-5.1.39

## 配置文件

```properties
# 数据库参数
jdbcDriver = com.mysql.jdbc.Driver
jdbcUrl = jdbc:mysql://localhost:3306/myThreadPool
userName = root
password = root
# 连接池参数
initCount = 10
stepSize = 4
maxSize = 150
```