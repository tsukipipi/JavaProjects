package ConnectMySQLDemo;

import javafx.scene.input.DataFormat;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

public class JDBCTimeDemo {

    static public void main(String[] args){

        //insertdata();
        querydata();
    }

    public static void insertdata(){
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            //注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            //建立连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","amatsuki");

            for(int i=0; i<100; i++){
                pst = conn.prepareStatement("insert into timetest(username,userpassword,regtime,lastlogintime)values(?,?,?,?)");
                pst.setObject(1,"rabbit" + i);
                pst.setObject(2,"19960330");

                //随机时间
                int random = 1000000000 + new Random().nextInt(1000000000);
                //随即一个注册时间
                java.sql.Date date = new java.sql.Date(System.currentTimeMillis() - random);
                //最后登录时间
                java.sql.Timestamp stamp = new Timestamp(System.currentTimeMillis());
                pst.setDate(3,date);
                pst.setTimestamp(4,stamp);
                //执行
                pst.execute();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(pst!=null){
                    pst.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(conn!=null){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //将字符串代表的时间转为long数字(格式：yyyy-MM-dd hh:mm:ss)
    public static long str2DataTime(String dataStr){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            return format.parse(dataStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void querydata(){
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            //注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            //建立连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "amatsuki");

            pst = conn.prepareStatement("select * from timetest where regtime > ? and regtime <?");
            java.sql.Date start = new java.sql.Date(str2DataTime("2018-04-05 00:00:00"));
            java.sql.Date end = new java.sql.Date(str2DataTime("2018-04-12 00:00:00"));
            pst.setObject(1,start);
            pst.setObject(2,end);
            //执行查询
            rs= pst.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString("username") + "--" + rs.getDate("regtime"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{
                if(rs!=null){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(pst!=null){
                    pst.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(conn!=null){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
