package util;

import java.sql.*;
/**
 * Created by YR on 2018/11/30.
 */
public class jdbcUtil {
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/web?serverTimezone=CTT";
    private static String user = "root";
    private static String pwd = "123456";
    static Connection conn = null;
    static PreparedStatement stmt = null;

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return conn = DriverManager.getConnection(url, user, pwd);
    }

    public static PreparedStatement getStmt(String sql) throws SQLException {
        return stmt = conn.prepareStatement(sql);
    }

    public static void close(Connection conn, PreparedStatement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

