package dao;

import bean.UserBean;
import java.sql.*;

public class UsersDAO extends DAO {

    // ****** ユーザー登録メソッド *****
    public int registerUser(UserBean userbean) throws Exception, SQLException {
        String insertSql = "INSERT INTO users (user_id, role, email, username, avatar) " +
                           "VALUES (user_seq.NEXTVAL, ?, ?, ?, ?)";
        String seqSql = "SELECT user_seq.CURRVAL FROM DUAL";

        int userId = -1;

        try (Connection con = getConnection()) {
            con.setAutoCommit(false); // トランザクション開始

            try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                ps.setString(1, userbean.getRole());
                ps.setString(2, userbean.getEmail());
                ps.setString(3, userbean.getUsername());
                ps.setString(4, userbean.getAvatar());
                ps.executeUpdate();
            }

            // 同一トランザクション内で user_seq.CURRVAL を取得
            try (PreparedStatement seqPs = con.prepareStatement(seqSql);
                 ResultSet rs = seqPs.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getInt(1);
                }
            }

            con.commit(); // コミット
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("データベースエラー: " + e.getMessage());
        }
        return userId;
    }
    
    // ****** ユーザー取得メソッド（ユーザIDで検索） ******
    public UserBean getUser(int userId) throws Exception, SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        UserBean user = null;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new UserBean();
                    user.setUser_id(rs.getInt("user_id"));
                    user.setRole(rs.getString("role"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setAvatar(rs.getString("avatar"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("データベースエラー: " + e.getMessage());
        }
        return user;
    }
    
    // ****** ユーザー取得メソッド（ユーザー名で検索） ******
    public UserBean getUser(String username) throws Exception, SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        UserBean user = null;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new UserBean();
                    user.setUser_id(rs.getInt("user_id"));
                    user.setRole(rs.getString("role"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setAvatar(rs.getString("avatar"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("データベースエラー: " + e.getMessage());
        }
        return user;
    }

    // ****** ユーザー更新メソッド *****
   public boolean editUser(UserBean userbean) throws Exception, SQLException {
     String sql = "UPDATE users SET email = ?, username = ? WHERE user_id = ?";
    int affectedRows = 0;
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, userbean.getEmail() );
        ps.setString(2, userbean.getUsername() );
        ps.setInt(3, userbean.getUser_id());
        affectedRows = ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace(); 
    }catch (Exception e) {
        e.printStackTrace();
    }
    return affectedRows > 0;
}
}
