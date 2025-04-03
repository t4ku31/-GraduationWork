package dao;

import bean.User_PasswordBean;
import java.sql.*;

public class User_PasswordsDAO extends DAO {
    // ****** 登録メソッド ****
    public boolean registerHashedPassword(User_PasswordBean password) {
        String sql = "INSERT INTO user_passwords (user_id, hashed_password, salt) VALUES(?, ?, ?)";
        
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setInt(1, password.getUser_id());
            ps.setString(2, password.getHashed_password());
            ps.setString(3, password.getSalt());
            ps.executeUpdate();
            return true; // 正常に登録完了
            
        } catch (SQLException e) {
            e.printStackTrace(); // ログ出力
            return false; // 登録失敗
        } catch (Exception e) {
            e.printStackTrace(); // ログ出力
            return false; // 登録失敗
        }
    }
    
    // ****** 検索メソッド ****
    public User_PasswordBean getHashedPass(int user_id) {
        User_PasswordBean gb = new User_PasswordBean();
        // 事前にSQL文を定義する
        String sql = "SELECT * FROM user_passwords WHERE user_id = ?";
        
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setInt(1, user_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    gb.setUser_id(rs.getInt("user_id"));
                    gb.setHashed_password(rs.getString("hashed_password"));
                    gb.setSalt(rs.getString("salt"));
                } else {
                    gb.setUser_id(-1); // データがない場合の処理
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            // エラー時は、必要に応じて gb にエラー状態を設定するなどの対応を検討
        }
        
        return gb;
    }
}
