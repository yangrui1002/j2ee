package DAO;

import pojo.Product;
import util.jdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by YR on 2018/12/1.
 */
public class ProductDao {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    public boolean sell(String cid, String uid, String name, String image, double price, int num, String pdesc){
        try{
            conn = jdbcUtil.getConnection ();
            String str = UUID.randomUUID().toString().replaceAll("-","");
            Date time= new java.sql.Date(new java.util.Date().getTime());
            String sql = "INSERT INTO product(PID,PNAME,SHOP_PRICE,PIMAGE,PDATE,PDESC,CID,UID,NUM) VALUES (?,?,?,?,?,?,?,?,?)";
            stmt = jdbcUtil.getStmt (sql);
            stmt.setString (1, str);
            stmt.setString (2, name);
            stmt.setDouble (3, price);
            stmt.setString (4,image);
            stmt.setDate (5, time);
            stmt.setString (6,pdesc);
            stmt.setString (7,cid);
            stmt.setString (8,uid);
            stmt.setInt (9,num);
            stmt.executeUpdate ();
            return true;
        }catch (SQLException e) {
            e.printStackTrace ();
        } finally {
            jdbcUtil.close (conn, stmt, rs);
        }
        return false;
    }

    public boolean buy(String pid,int n){
        Product product = findProduct (pid);
        if(product==null || product.getNum() < n){
            return false;
        }
        else {
            if(product.getNum ()==n){
                delete (pid);
            }
            else{
                int m=product.getNum () - n;
                try{
                    conn = jdbcUtil.getConnection ();
                    String sql = "UPDATE FROM product SET UNM = m WHERE pid = ?";
                    stmt = jdbcUtil.getStmt (sql);
                    stmt.setString (1, pid);
                    stmt.executeUpdate ();
                    return true;
                }catch (SQLException e) {
                    e.printStackTrace ();
                } finally {
                    jdbcUtil.close (conn, stmt, rs);
                }
            }
        }
        return false;
    }

    public Product findByName(String name) {
        try {
            conn = jdbcUtil.getConnection ();
            String sql = "SELECT * FROM product WHERE PNAME = ?";
            stmt = jdbcUtil.getStmt (sql);
            stmt.setString (1, name);
            rs = stmt.executeQuery ();
            Product product = null;
            while (rs.next ()) {
                product = new Product ();
                product.setId (rs.getString ("pid"));
                product.setCid (rs.getString ("cid"));
                product.setUid (rs.getString ("uid"));
                product.setPimage (rs.getString ("pimage"));
                product.setShop_price (rs.getDouble ("shop_price"));
                product.setPdesc (rs.getString ("pdesc"));
                product.setNum (rs.getInt ("num"));
            }
            return product;
        } catch (SQLException e) {
            e.printStackTrace ();
        } finally {
            jdbcUtil.close (conn, stmt, rs);
        }
        return null;
    }

    public Product findProduct(String id) {
        try {
            conn = jdbcUtil.getConnection ();
            String sql = "SELECT * FROM product WHERE PID = ?";
            stmt = jdbcUtil.getStmt (sql);
            stmt.setString (1, id);
            rs = stmt.executeQuery ();
            Product product = null;
            while (rs.next ()) {
                product = new Product ();
                product.setId (rs.getString ("pid"));
                product.setCid (rs.getString ("cid"));
                product.setUid (rs.getString ("uid"));
                product.setPimage (rs.getString ("pimage"));
                product.setShop_price (rs.getDouble ("shop_price"));
                product.setPdesc (rs.getString ("pdesc"));
                product.setNum (rs.getInt ("num"));
            }
            return product;

        } catch (SQLException e) {
            e.printStackTrace ();
        } finally {
            jdbcUtil.close (conn, stmt, rs);
        }
        return null;
    }

    public int getTotal() {
        int total = 0;
        try {
            conn = jdbcUtil.getConnection ();
            String sql = "select count(pname) from product";
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

    public List<Product> list() {
        return list(0, Short.MAX_VALUE);
    }

    public List<Product> list(int start, int count) {
        List<Product> products = new ArrayList<> ();
        try {
            conn = jdbcUtil.getConnection ();
            String sql = "select * from product LIMIT ?, ?";
            stmt = jdbcUtil.getStmt (sql);
            stmt.setInt(1, start);
            stmt.setInt(2, count);
            rs = stmt.executeQuery ();

            while (rs.next()) {
                Product product = new Product ();
                product.setId (rs.getString ("pid"));
                product.setPname (rs.getString ("pname"));
                product.setCid (rs.getString ("cid"));
                product.setUid (rs.getString ("uid"));
                product.setPimage (rs.getString ("pimage"));
                product.setShop_price (rs.getDouble ("shop_price"));
                product.setPdesc (rs.getString ("pdesc"));
                product.setNum (rs.getInt ("num"));
                products.add(product);
            }
        }  catch (SQLException e) {
            e.printStackTrace ();
        } finally {
            jdbcUtil.close (conn, stmt, rs);
        }
        return products;
    }

    public List<Product> listByseller(String uid) {
        List<Product> products = new ArrayList<> ();
        try {
            conn = jdbcUtil.getConnection ();
            String sql = "select * from product WHERE uid = ?";
            stmt = jdbcUtil.getStmt (sql);
            stmt.setString(1, uid);
            rs = stmt.executeQuery ();
            while (rs.next()) {
                Product product = new Product ();
                product.setId (rs.getString ("pid"));
                product.setCid (rs.getString ("cid"));
                product.setUid (rs.getString ("uid"));
                product.setPimage (rs.getString ("pimage"));
                product.setShop_price (rs.getDouble ("shop_price"));
                product.setPdesc (rs.getString ("desc"));
                product.setNum (rs.getInt ("num"));
                products.add(product);
            }
        }  catch (SQLException e) {
            e.printStackTrace ();
        } finally {
            jdbcUtil.close (conn, stmt, rs);
        }
        return products;
    }

    public List<Product> clist(String cid) {
        List<Product> products = new ArrayList<> ();
        try {
            conn = jdbcUtil.getConnection ();
            String sql = "select * from product where cid = ?";
            stmt = jdbcUtil.getStmt (sql);
            stmt.setString (1,cid);
            rs = stmt.executeQuery ();

            while (rs.next()) {
                Product product = new Product ();
                product.setId (rs.getString ("pid"));
                product.setPname (rs.getString ("pname"));
                product.setCid (rs.getString ("cid"));
                product.setUid (rs.getString ("uid"));
                product.setPimage (rs.getString ("pimage"));
                product.setShop_price (rs.getDouble ("shop_price"));
                product.setPdesc (rs.getString ("pdesc"));
                product.setNum (rs.getInt ("num"));
                System.out.println (rs.getString ("pimage"));
                products.add(product);
            }
        }  catch (SQLException e) {
            e.printStackTrace ();
        } finally {
            jdbcUtil.close (conn, stmt, rs);
        }
        return products;
    }

    public void delete(String id) {
        try {
            String sql = "delete from product where pid = ?";
            stmt = jdbcUtil.getStmt (sql);
            stmt.setString (1, id);
            rs = stmt.executeQuery ();
        }  catch (SQLException e) {
            e.printStackTrace ();
        } finally {
            jdbcUtil.close (conn, stmt, rs);
        }
    }
}
