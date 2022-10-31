package dataaccess;

import java.sql.*;
import java.util.*;
import models.Role;

/**
 *
 * @author marce
 */
public class RoleDB {

    public List<Role> getAll() throws Exception {
        List<Role> roles = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM role";

        try {
            ps = c.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int role_id = rs.getInt(1);
                String role_name = rs.getString(2);
                Role role = new Role(role_id, role_name);
                roles.add(role);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(c);
        }

        return roles;
    }

    public Role get(int role_id) throws Exception {
        Role role = null;
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM role WHERE role_id=?";

        try {
            ps = c.prepareStatement(sql);
            ps.setInt(1, role_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                String role_name = rs.getString(2);
                role = new Role(role_id, role_name);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(c);
        }
        
        return role;
    }
}