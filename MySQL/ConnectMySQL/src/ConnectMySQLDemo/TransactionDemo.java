package ConnectMySQLDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.time.LocalDate.now;

public class TransactionDemo {

    static public void main(String[] args){
        Connection conn = null;
        PreparedStatement pst1 = null;
        PreparedStatement pst2 = null;

        try {
            //注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            //建立连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","amatsuki");
            //设置为手动提交事务，缺省为自动提交
            conn.setAutoCommit(false);

            //事务开始
            pst1 = conn.prepareStatement("insert into usertest(username,userpassword,currenttime)values(?,?,?)");
            pst1.setObject(1,"pipi");
            pst1.setObject(2,"amatsuki");
            pst1.setObject(3,now());
            pst1.execute();
            System.out.println("第一次插入数据");

            try{
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //模拟执行失败(values的参数写成四个)
            pst2 = conn.prepareStatement("insert into usertest(username,userpassword,currenttime)values(?,?,?,?)");
            pst2.setObject(1,"rabbit");
            pst2.setObject(2,"19960330");
            pst2.setObject(3,now());
            pst2.execute();
            System.out.println("第二次插入数据");

            //提交事务
            conn.commit();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            try{
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try{
                if(pst1!=null){
                    pst1.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try{
                if(pst2!=null){
                    pst2.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try{
                if(conn!=null){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
