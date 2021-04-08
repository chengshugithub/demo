package com.example.demo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Java中使用JDBC连接数据库
 *  1） 加载驱动 2） 创建数据库连接
 *  3） 创建执行sql的语句 4） 执行语句 5） 处理执行结果 6） 释放资源
 * @author liu.hb
 *
 */
public class DBHelper {
    /**
     * Statement 和 PreparedStatement之间的关系和区别.
     * 关系：PreparedStatement继承自Statement,都是接口
     * 区别：PreparedStatement可以使用占位符，是预编译的，批处理比Statement效率高
     */

    public static List<String> getTableNames() {
        List<String> tableList = new ArrayList<>();
//        String URL = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
        String URL = "jdbc:mysql://192.168.13.158:3306/test?serverTimezone=UTC";
        String USER = "root";
//        String PASSWORD = "root";
        String PASSWORD = "root123456";
        // 1.加载驱动程序
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2.获得数据库链接
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            // 3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
            //预编译
            String sql = "select table_name from information_schema.tables where table_schema='test';";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
//			String sql="select * from userinfo where UserName='"+name+"'";
//			Statement statement = conn.createStatement();
//			ResultSet rs = statement.executeQuery(sql);
            // 4.处理数据库的返回结果(使用ResultSet类)
            while (rs.next()) {
                tableList.add(rs.getString("table_name"));
            }

            // 关闭资源
            conn.close();
            rs.close();
            statement.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableList;
    }

    public static List<String> getTableFiles(String tableName) {
        List<String> tableFileList = new ArrayList<>();
//        String URL = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
        String URL = "jdbc:mysql://192.168.13.158:3306/test?serverTimezone=UTC";
        String USER = "root";
//        String PASSWORD = "root";
        String PASSWORD = "root123456";
        // 1.加载驱动程序
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "select COLUMN_NAME,column_comment from INFORMATION_SCHEMA.Columns where table_name=? and table_schema='test'";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,tableName);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                tableFileList.add(rs.getString("COLUMN_NAME"));
            }

            // 关闭资源
            conn.close();
            rs.close();
            statement.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableFileList;
    }


    public static void main(String[] args) {
        List<String> tableList = getTableNames();
        for(String table:tableList){
            System.out.println(table);
        }
        String tableName="user";
        List<String> tableFileList = getTableFiles(tableName);
        for(String file:tableFileList){
            System.out.println(file);
        }
    }
}

