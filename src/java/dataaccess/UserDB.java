package dataaccess;

import java.sql.*;
import java.util.*;
import models.Role;
import models.User;

/**
 *
 * @author marce
 */
public class UserDB {

    public List<User> getAll() throws Exception {
        List<User> users = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select email, first_name, last_name, password, role_id, role_name from user u, role r where (u.role = r.role_id)";

        try {
            ps = c.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String email = rs.getString(1);        
                String fname = rs.getString(2);
                String lname = rs.getString(3);
                String pword = rs.getString(4);
                Role role = new Role(rs.getInt(5), rs.getString(6));
                User user = new User(email, fname, lname, pword, role);
                users.add(user);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(c);
        }

        return users;
    }

    public User get(String email) throws Exception   {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        String sql = "select first_name, last_name, password, role_id, role_name from user u join role r on (r.role_id = u.role) where email=?";

        ps = c.prepareStatement(sql);
        ps.setString(1, email);
        rs = ps.executeQuery();

        if (rs.next()) {
            String fname = rs.getString(1);
            String lname = rs.getString(2);
            String pword = rs.getString(3);
            Role role = new Role(rs.getInt(4), rs.getString(5));
            user = new User(email, fname, lname, pword, role);
        }

        DBUtil.closeResultSet(rs);
        DBUtil.closePreparedStatement(ps);
        cp.freeConnection(c);

        return user;
    }

    public void insert(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO user (email,first_name,last_name,password,role) VALUES (?, ?, ?, ?, ?)";

        try {
            ps = c.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getRole().getRoleID());
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(c);
        }
    }

    public void update(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "update user set first_name=?, last_name=?, password=?, role=? where email=?";

        try {
            ps = c.prepareStatement(sql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getRole().getRoleID());
            ps.setString(5, user.getEmail());
            ps.executeUpdate();

        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(c);
        }
    }

    public void delete(String email) throws Exception  {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "delete from user where email=?";

        try {
            ps = c.prepareStatement(sql);
            ps.setString(1, email);
            ps.executeUpdate();

        } finally {

            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(c);
        }
    }
}