package ConnectMySQLDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchDemo {

    static private String db_url = "jdbc:mysql://localhost:3306/test";
    static private String user = "root";
    static private String password = "amatsuki";
    static Connection conn;
    static Statement stmt;

    public static void main(String[] args){

        try{
            //注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            //建立连接
            conn = DriverManager.getConnection(db_url,user,password);
            //设为手动提交
            conn.setAutoCommit(false);
            //执行sql语句的statement，批处理用statement
            stmt = conn.createStatement();
            //执行sql语句
            for (int i = 0; i < 20000; i++) {
                stmt.addBatch("insert into usertest (username,userpassword,currenttime) values ('pipi','amatsuki',now())");
            }
            stmt.executeBatch();
            //提交事务
            conn.commit();

        }catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
