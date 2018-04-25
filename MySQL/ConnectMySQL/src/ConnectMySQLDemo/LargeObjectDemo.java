package ConnectMySQLDemo;

import java.io.*;
import java.sql.*;

public class LargeObjectDemo {

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
            pst = conn.prepareStatement("insert into largeobject(name,info,photo) values (?,?,?)");

            pst.setObject(1,"rabbit");
            //将文本文件内容直接输入到数据库中
            pst.setClob(2,new FileReader(new File("info.txt")));
            //将程序中的字符串输入到数据库中的CLOB字段中
            //pst.setClob(2,new BufferedReader(new InputStreamReader(new ByteArrayInputStream("rabbit is a cute girl！".getBytes()))));
            pst.setBlob(3,new FileInputStream("rabbit.png"));
            pst.execute();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally{
            try{
                if(pst!=null){
                    pst.close();
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


    public static void querydata(){
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Reader r = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            //注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            //建立连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "amatsuki");

            pst = conn.prepareStatement("select * from largeobject where name = ?");
            pst.setObject(1,"rabbit");

            rs = pst.executeQuery();
            while (rs.next()){
                Clob c = rs.getClob("info");
                r = c.getCharacterStream();
                int temp = 0;
                while ((temp=r.read())!=-1) {
                    System.out.print((char)temp);
                }

                Blob b = rs.getBlob("photo");
                is = b.getBinaryStream();
                os = new FileOutputStream("photo.jpg");
                temp = 0;
                while ((temp=is.read())!=-1) {
                    os.write(temp);
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try{
                if(r!=null)  r.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                if(os!=null) os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                if(is!=null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(rs!=null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try{
                if(pst!=null) pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try{
                if(conn!=null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
