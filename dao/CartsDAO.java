package dao;

import bean.CartBean;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class CartsDAO extends DAO {

    // ****** カート追加メソッド ****//
    public boolean addCart(CartBean cb) throws SQLException, Exception {
        boolean ret = false;
        String sql = "INSERT INTO carts (user_id, game_id) VALUES (?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cb.getUser_id());
            ps.setInt(2, cb.getGame_id());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ret = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // 例外を再スロー
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("一般的なエラーが発生しました: " + e.getMessage()); // 一般的な例外を再スロー
        }
        return ret;
    }

    // ****** カート削除メソッド *****//
    public boolean deleteCart(CartBean cb) throws SQLException, Exception {
        boolean ret = false;
        String sql = "DELETE FROM carts WHERE user_id = ? AND game_id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cb.getUser_id());
            ps.setInt(2, cb.getGame_id());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ret = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // 例外を再スロー
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("一般的なエラーが発生しました: " + e.getMessage()); // 一般的な例外を再スロー
        }
        return ret;
    }
    
    // ****** カート全削除メソッド *****//
    public boolean deleteCartAll(int userId) throws SQLException, Exception {
        boolean ret = false;
        String sql = "DELETE FROM carts WHERE user_id = ?";
        
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ret = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // 例外を再スロー
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("一般的なエラーが発生しました: " + e.getMessage()); // 一般的な例外を再スロー
        }
        return ret;
    }


    // ****** carts情報取得メソッド ****//
    public List<CartBean> getCart(int userId) throws SQLException, Exception {
        List<CartBean> carts = new ArrayList<>();
        String sql = "SELECT * FROM carts WHERE user_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CartBean cb = new CartBean();
                    cb.setUser_id(rs.getInt("user_id"));
                    cb.setGame_id(rs.getInt("game_id"));
                    carts.add(cb);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // 例外を再スロー
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("一般的なエラーが発生しました: " + e.getMessage()); // 一般的な例外を再スロー
        }
        return carts;
    }
}
