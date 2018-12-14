package DAO;

import pojo.Order;
import pojo.Product;
import pojo.User;
import util.jdbcUtil;

import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by YR on 2018/12/4.
 */
public class OrderDao {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    public boolean add(Product product, User buyer,String address,String phone) {
        try {
            conn = jdbcUtil.getConnection ();
            String str = UUID.randomUUID().toString().replaceAll("-","");
            Date date = new Date();
            Timestamp time = new Timestamp(date.getTime());
            String sql = "INSERT INTO order(OID,ORDERTIME,TOTAL,STATE,ADDRESS,PID,TELEPHONE,UID) VALUES (?,?,?,?,?,?,?,?)";
            stmt = jdbcUtil.getStmt (sql);
            stmt.setString (1, str);
            stmt.setTimestamp (2, time);
            stmt.setDouble (3,product.getShop_price ());
            stmt.setInt (4, 0);
            stmt.setString (5, address);
            stmt.setString (6,product.getId ());
            stmt.setString (7,phone);
            stmt.setString (8,buyer.getName ());
            stmt.executeUpdate ();
            return true;
        } catch (SQLException e) {
            e.printStackTrace ();
        } finally {
            jdbcUtil.close (conn, stmt, rs);
        }
        return false;
    }

    public List<Order> blist(String uid) {
        List<Order> orders = new ArrayList<> ();
        try {
            conn = jdbcUtil.getConnection ();
            String sql = "select * from order WHERE UID = ?";
            stmt = jdbcUtil.getStmt (sql);
            stmt.setString(1,uid);
            rs = stmt.executeQuery ();
            while (rs.next()) {
                Order order = new Order();
                order.setOid (rs.getString ("oid"));
                order.setDate (rs.getTimestamp ("ordertime"));
                order.setState (rs.getInt ("state"));
                order.setUid (rs.getString ("uid"));
                order.setPid (rs.getString ("pid"));
                order.setPhone (rs.getString("telephone"));
                order.setAddress (rs.getString ("address"));
                order.setTotal (rs.getDouble ("total"));
                orders.add(order);
            }
        }  catch (SQLException e) {
            e.printStackTrace ();
        } finally {
            jdbcUtil.close (conn, stmt, rs);
        }
        return orders;
    }

    public List<Order> slist(String uid) {
        List<Product> products=new ProductDao ().listByseller (uid);
        List<Order> orders = new ArrayList<> ();
        for (Product product:products) {
            String pid=product.getId ();
            try {
                conn = jdbcUtil.getConnection ();
                String sql = "select * from order WHERE PID = ?";
                stmt = jdbcUtil.getStmt (sql);
                stmt.setString(1,pid);
                rs = stmt.executeQuery ();
                while (rs.next()) {
                    Order order = new Order();
                    order.setOid (rs.getString ("oid"));
                    Timestamp data = (rs.getTimestamp ("ordertime"));
                    order.setDate((rs.getTimestamp ("ordertime")));
                    order.setState (rs.getInt ("state"));
                    order.setUid (rs.getString ("uid"));
                    order.setPid (rs.getString ("pid"));
                    order.setPhone (rs.getString("telephone"));
                    order.setAddress (rs.getString ("address"));
                    order.setTotal (rs.getDouble ("total"));
                    orders.add(order);
                }
            }  catch (SQLException e) {
                e.printStackTrace ();
            } finally {
                jdbcUtil.close (conn, stmt, rs);
            }
        }
        return orders;
    }

    public void delete(String oid) {
        try {
            String sql = "delete from order where oid = ?";
            stmt = jdbcUtil.getStmt (sql);
            stmt.setString (1, oid);
            rs = stmt.executeQuery ();
        }  catch (SQLException e) {
            e.printStackTrace ();
        } finally {
            jdbcUtil.close (conn, stmt, rs);
        }
    }

    public void setState(String oid){
        try {
            String sql = "UPDATE from order state= 1 where oid = ?";
            stmt = jdbcUtil.getStmt (sql);
            stmt.setString (1, oid);
            rs = stmt.executeQuery ();
        } catch (SQLException e) {
            e.printStackTrace ();
        } finally {
            jdbcUtil.close (conn, stmt, rs);
        }
    }
}
