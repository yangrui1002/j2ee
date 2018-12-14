package DAO;

/**
 * Created by YR on 2018/11/30.
 */
import util.jdbcUtil;
import pojo.User;
import util.mailutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.List;

public class UserDao {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    //注册
    public boolean register(String name, String password, String emil) {
        User user = findUser(name);
        if (user == null) {
            try {
                conn = jdbcUtil.getConnection ();
                String str = UUID.randomUUID().toString().replaceAll("-","");
                String sql = "INSERT INTO user(UID,USERNAME,PASSWORD,EMIL) VALUES (?,?,?,?)";
                stmt = jdbcUtil.getStmt (sql);
                stmt.setString (1, str);
                stmt.setString (2, name);
                stmt.setString (3, password);
                stmt.setString (4, emil);
                stmt.executeUpdate ();
                return true;
            } catch (SQLException e) {
                e.printStackTrace ();
            } finally {
                jdbcUtil.close (conn, stmt, rs);
            }
        }
        return false;
    }

    public User findUser(String name) {
        try {
            conn = jdbcUtil.getConnection ();
            String sql = "SELECT * FROM user WHERE USERNAME = ?";
            stmt = jdbcUtil.getStmt (sql);
            stmt.setString (1, name);
            rs = stmt.executeQuery ();
            User user = null;
            while (rs.next ()) {
                user = new User ();
                user.setId (rs.getString ("uid"));
                user.setName (rs.getString ("username"));
                System.out.print (user.getName ());
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace ();
        } finally {
            jdbcUtil.close (conn, stmt, rs);
        }
        return null;
    }

    public boolean login(String name, String password) {
        User user = findUser(name,password);
        if (user == null) {
            return false;
        }
        else {
            return true;
        }
    }

    //查询用户
    public User findUser(String name, String password) {
        try {
            conn = jdbcUtil.getConnection ();
            String sql = "SELECT * FROM user WHERE USERNAME = ? AND PASSWORD = ? ";
            stmt = jdbcUtil.getStmt (sql);
            stmt.setString (1, name);
            stmt.setString (2, password);
            rs = stmt.executeQuery ();
            User user = null;
            while (rs.next ()) {
                System.out.print (12);
                user = new User ();
                user.setName (rs.getString ("username"));
                user.setPassword (rs.getString ("password"));
            }
            return user;

        } catch (SQLException e) {
            e.printStackTrace ();
        } finally {
            jdbcUtil.close (conn, stmt, rs);
        }
        return null;
    }

    public User findnyemil(String emil) {
        try {
            conn = jdbcUtil.getConnection ();
            String sql = "SELECT * FROM user WHERE emil = ?";
            stmt = jdbcUtil.getStmt (sql);
            stmt.setString (1, emil);
            rs = stmt.executeQuery ();
            User user = null;
            while (rs.next ()) {
                user = new User ();
                user.setName (rs.getString ("username"));
                user.setPassword (rs.getString ("password"));
                user.setEmil (rs.getString ("emil"));
            }
            return user;

        } catch (SQLException e) {
            e.printStackTrace ();
        } finally {
            jdbcUtil.close (conn, stmt, rs);
        }
        return null;
    }

    public double balance(String name){
        User user = findUser (name);
        return user.getMoney ();
    }

    public void recharge(String name, double money){
        money += balance (name);
        try {
            conn = jdbcUtil.getConnection ();
            String sql = "UPDATE user set money = ? WHERE USERNAME = ?";
            stmt = jdbcUtil.getStmt (sql);
            stmt.setDouble (1,money);
            stmt.setString (2, name);
            stmt.executeUpdate ();

        } catch (SQLException e) {
            e.printStackTrace ();
        } finally {
            jdbcUtil.close (conn, stmt, rs);
        }
    }

    public int getTotal() {
        int total = 0;
        try {
            conn = jdbcUtil.getConnection ();
            String sql = "select count(username) from user";
            stmt = jdbcUtil.getStmt (sql);
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public List<User> list() {
        return list(0, Short.MAX_VALUE);
    }

    public List<User> list(int start, int count) {
        List<User> users = new ArrayList<> ();
        try {
            conn = jdbcUtil.getConnection ();
            String sql = "select * from user LIMIT ?, ?";
            stmt = jdbcUtil.getStmt (sql);
            stmt.setInt(1, start);
            stmt.setInt(2, count);
            rs = stmt.executeQuery ();

            while (rs.next()) {
                User user = new User();
                String name = rs.getString("username");
                String emil = rs.getString ("emil");
                int is_admin=rs.getInt ("is_admin");
                user.setName (name);
                user.setEmil (emil);
                user.setIs_admin (is_admin);
                users.add(user);
            }
        }  catch (SQLException e) {
            e.printStackTrace ();
        } finally {
            jdbcUtil.close (conn, stmt, rs);
        }
        return users;
    }

    public void delete(String id) {
        try {
            String sql = "delete from user where username = ?";
            stmt = jdbcUtil.getStmt (sql);
            stmt.setString (1, id);
            stmt.executeUpdate ();
        }  catch (SQLException e) {
            e.printStackTrace ();
        } finally {
            jdbcUtil.close (conn, stmt, rs);
        }
    }

    public boolean update(String emil, String password){

        try {
            conn = jdbcUtil.getConnection ();
            String sql = "UPDATE user set password = ? WHERE emil = ?";
            stmt = jdbcUtil.getStmt (sql);
            stmt.setString (1,password);
            stmt.setString (2, emil);
            stmt.executeUpdate ();
            return true;
        } catch (SQLException e) {
            e.printStackTrace ();
        } finally {
            jdbcUtil.close (conn, stmt, rs);
        }
        return false;
    }
}

