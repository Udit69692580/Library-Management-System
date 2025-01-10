package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.entity.User;

public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) {
        super();
        this.conn = conn;
    }

    // Add new user (Register)
    public boolean addUser(User u) {
        boolean f = false;
        try {
            String sql = "INSERT INTO user(name, qualification, email, password, role) VALUES (?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setString(4, "user"); // Default role is user

            int i = ps.executeUpdate();
            if (i == 1) {
                f = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    // User login
    public User login(String email, String password) {
        User u = null;
        try {
            String sql = "SELECT * FROM user WHERE email=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                u = new User();
                u.setId(rs.getInt(1));
                u.setName(rs.getString(2));
                u.setEmail(rs.getString(3));
                u.setPassword(rs.getString(4));
                u.setRole(rs.getString(5));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    // Update user profile
    public boolean updateUser(User u) {
        boolean f = false;
        try {
            String sql = "UPDATE user SET name=?, qualification=?, email=?, password=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setInt(4, u.getId());

            int i = ps.executeUpdate();
            if (i == 1) {
                f = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    // Get user by ID
    public User getUserById(int id) {
        User u = null;
        try {
            String sql = "SELECT * FROM user WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    // Check if email exists
    public boolean checkEmail(String email) {
        boolean f = false;
        try {
            String sql = "SELECT * FROM user WHERE email=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                f = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    // Delete user
    public boolean deleteUser(int id) {
        boolean f = false;
        try {
            String sql = "DELETE FROM user WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int i = ps.executeUpdate();
            if (i == 1) {
                f = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    // Get all users (for admin)
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<User>();
        User u = null;
        try {
            String sql = "SELECT * FROM user WHERE role=? ORDER BY id DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "user");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Change password
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        boolean f = false;
        try {
            // First verify old password
            String verifySQL = "SELECT * FROM user WHERE id=? AND password=?";
            PreparedStatement verifyPs = conn.prepareStatement(verifySQL);
            verifyPs.setInt(1, userId);
            verifyPs.setString(2, oldPassword);
            ResultSet rs = verifyPs.executeQuery();

            if (rs.next()) {
                // Old password is correct, proceed with update
                String updateSQL = "UPDATE user SET password=? WHERE id=?";
                PreparedStatement updatePs = conn.prepareStatement(updateSQL);
                updatePs.setString(1, newPassword);
                updatePs.setInt(2, userId);

                int i = updatePs.executeUpdate();
                if (i == 1) {
                    f = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    // Update user role (admin only)
    public boolean updateUserRole(int userId, String newRole) {
        boolean f = false;
        try {
            String sql = "UPDATE user SET role=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newRole);
            ps.setInt(2, userId);

            int i = ps.executeUpdate();
            if (i == 1) {
                f = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    // Search users by name or email (continued)
    public List<User> searchUsers(String query) {
        List<User> list = new ArrayList<User>();
        User u = null;
        try {
            String sql = "SELECT * FROM user WHERE (name LIKE ? OR email LIKE ?) AND role=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + query + "%");
            ps.setString(2, "%" + query + "%");
            ps.setString(3, "user");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get total number of users
    public int getTotalUsers() {
        int total = 0;
        try {
            String sql = "SELECT COUNT(*) FROM user WHERE role=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "user");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    // Get users with pagination
    public List<User> getUsersWithPagination(int start, int total) {
        List<User> list = new ArrayList<User>();
        User u = null;
        try {
            String sql = "SELECT * FROM user WHERE role=? ORDER BY id DESC LIMIT ?,?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "user");
            ps.setInt(2, start-1);
            ps.setInt(3, total);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Reset password
    public boolean resetPassword(String email, String newPassword) {
        boolean f = false;
        try {
            String sql = "UPDATE user SET password=? WHERE email=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setString(2, email);

            int i = ps.executeUpdate();
            if (i == 1) {
                f = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    // Update user status (active/inactive)
    public boolean updateUserStatus(int userId, boolean isActive) {
        boolean f = false;
        try {
            String sql = "UPDATE user SET is_active=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, isActive);
            ps.setInt(2, userId);

            int i = ps.executeUpdate();
            if (i == 1) {
                f = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    // Get user statistics (for admin dashboard)
    public Map<String, Integer> getUserStatistics() {
        Map<String, Integer> stats = new HashMap<>();
        try {
            // Get total active users
            String activeSQL = "SELECT COUNT(*) FROM user WHERE role=? AND is_active=true";
            PreparedStatement activePs = conn.prepareStatement(activeSQL);
            activePs.setString(1, "user");
            ResultSet activeRs = activePs.executeQuery();
            if (activeRs.next()) {
                stats.put("activeUsers", activeRs.getInt(1));
            }

            // Get total inactive users
            String inactiveSQL = "SELECT COUNT(*) FROM user WHERE role=? AND is_active=false";
            PreparedStatement inactivePs = conn.prepareStatement(inactiveSQL);
            inactivePs.setString(1, "user");
            ResultSet inactiveRs = inactivePs.executeQuery();
            if (inactiveRs.next()) {
                stats.put("inactiveUsers", inactiveRs.getInt(1));
            }

            // Get new users in last 30 days
            String newUsersSQL = "SELECT COUNT(*) FROM user WHERE role=? AND created_at >= DATE_SUB(NOW(), INTERVAL 30 DAY)";
            PreparedStatement newUsersPs = conn.prepareStatement(newUsersSQL);
            newUsersPs.setString(1, "user");
            ResultSet newUsersRs = newUsersPs.executeQuery();
            if (newUsersRs.next()) {
                stats.put("newUsers", newUsersRs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stats;
    }

    // Validate user credentials
    public boolean validateCredentials(String email, String password) {
        boolean isValid = false;
        try {
            String sql = "SELECT * FROM user WHERE email=? AND password=? AND is_active=true";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isValid = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }
}