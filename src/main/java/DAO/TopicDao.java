package DAO;

import pojo.Order;
import pojo.Topic;
import util.jdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by YR on 2018/12/14.
 */
public class TopicDao {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    //发帖
    public boolean senttopic(String title, String message, String uid) {
        try {
            conn = jdbcUtil.getConnection ();
            String str = UUID.randomUUID().toString().replaceAll("-","");
            Date date = new Date();
            Timestamp time = new Timestamp(date.getTime());
            String sql1 = "INSERT INTO topic(TID,TITLE,DATE) VALUES (?,?,?)";
            stmt = jdbcUtil.getStmt (sql1);
            stmt.setString (1, str);
            stmt.setString (2, title);
            stmt.setTimestamp (3,time);
            stmt.executeUpdate ();

            String sql2 = "INSERT INTO message(TID,UID,MSG,DATE) VALUES (?,?,?,?)";
            stmt = jdbcUtil.getStmt (sql2);
            stmt.setString (1, str);
            stmt.setString (2, uid);
            stmt.setString (2, message);
            stmt.setTimestamp (4,time);
            stmt.executeUpdate ();
            return true;
        } catch (SQLException e) {
            e.printStackTrace ();
        } finally {
            jdbcUtil.close (conn, stmt, rs);
        }

        return false;
    }


    public boolean sentmessage(String tid, String message, String uid) {
        try {
            conn = jdbcUtil.getConnection ();
            Date date = new Date();
            Timestamp time = new Timestamp(date.getTime());
            String sql = "INSERT INTO message(TID,UID,MSG,DATE) VALUES (?,?,?,?)";
            stmt = jdbcUtil.getStmt (sql);
            stmt.setString (1, tid);
            stmt.setString (2, uid);
            stmt.setString (2, message);
            stmt.setTimestamp (4,time);
            stmt.executeUpdate ();
            return true;
        } catch (SQLException e) {
            e.printStackTrace ();
        } finally {
            jdbcUtil.close (conn, stmt, rs);
        }
        return false;
    }

    public List<Topic> tlist() {
        List<Topic> topics = new ArrayList<> ();
        try {
            conn = jdbcUtil.getConnection ();
            String sql = "select * from title";
            stmt = jdbcUtil.getStmt (sql);
            rs = stmt.executeQuery ();
            while (rs.next()) {
                Topic topic = new Topic ();
                topic.setTid (rs.getString ("tid"));
                topic.setTitile (rs.getString ("title"));
                topic.setDate (rs.getTimestamp ("date"));
                topics.add(topic);
            }
        }  catch (SQLException e) {
            e.printStackTrace ();
        } finally {
            jdbcUtil.close (conn, stmt, rs);
        }
        return topics;
    }

    public List<Topic> mlist(String tid) {
        List<Topic> topics = new ArrayList<> ();
        try {
            conn = jdbcUtil.getConnection ();
            String sql = "select * from message where tid = ?";
            stmt = jdbcUtil.getStmt (sql);
            stmt.setString (1,tid);
            rs = stmt.executeQuery ();
            while (rs.next()) {
                Topic topic = new Topic ();
                topic.setTid (rs.getString ("tid"));
                topic.setTitile (rs.getString ("title"));
                topic.setUid (rs.getString ("uid"));
                topic.setMessage (rs.getString ("msg"));
                topic.setDate (rs.getTimestamp ("date"));
                topics.add(topic);
            }
        }  catch (SQLException e) {
            e.printStackTrace ();
        } finally {
            jdbcUtil.close (conn, stmt, rs);
        }
        return topics;
    }
}
