package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TableServiceImpl {

    @Autowired
    DataSource dataSource;

    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;

    public Connection getConnectionLocal() throws Exception{
        return dataSource.getConnection();
    }
    public List<String> getTables() throws Exception{

        List<String> tableList = new ArrayList<>();

        Connection connection = null;
        ResultSet tablesResult = null;
        PreparedStatement ps = null;
        try {
            connection = getConnectionLocal();

            String sql = "select table_name from information_schema.tables where table_schema='test';";
            // tablesResult = metaData.getTables(null, null, "%", null);
            ps = connection.prepareStatement(sql);
            // 获取结果集
            tablesResult = ps.executeQuery();
            while (tablesResult.next()) {
                tableList.add(tablesResult.getString("table_name"));

            }

        } catch (SQLException e) {
           e.printStackTrace();
        } finally {
            // 关闭连接，释放资源
            release(tablesResult, ps, connection);
        }
        return tableList;
    }

    /**
     * @description 释放资源
     */
    public void release(ResultSet rs, PreparedStatement ps, Connection connection) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs = null;
        }

        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ps = null;
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection = null;
        }
    }

    public List<String> getTableFieldByTableName(String tableName) throws Exception{

        List<String> tableFileList = new ArrayList<>();

        Connection connection = null;
        ResultSet tablesResult = null;
        PreparedStatement ps = null;
        try {
            connection = getConnectionLocal();

            String sql = "select COLUMN_NAME,column_comment from INFORMATION_SCHEMA.Columns where table_name=? and table_schema='test'";
            // tablesResult = metaData.getTables(null, null, "%", null);
            ps = connection.prepareStatement(sql);
            // 获取结果集
            ps.setString(1,tableName);
            tablesResult = ps.executeQuery();
            while (tablesResult.next()) {
                tableFileList.add(tablesResult.getString("COLUMN_NAME"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接，释放资源
            release(tablesResult, ps, connection);
        }
        return tableFileList;
    }


    //jdbc 调用
    public  List<String> getTableNames() {
        List<String> tableList = new ArrayList<>();
        String URL = url;
        String USER = userName;
        String PASSWORD = password;
        // 1.加载驱动程序
        try {
            Class.forName(driver);
            // 2.获得数据库链接
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            // 3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
            //预编译
            String sql = "select table_name from information_schema.tables where table_schema='test';";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
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

    public  List<String> getTableFiles(String tableName) {
        List<String> tableFileList = new ArrayList<>();
        String URL = url;
        String USER = userName;
        String PASSWORD = password;
        // 1.加载驱动程序
        try {
            Class.forName(driver);
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

}
