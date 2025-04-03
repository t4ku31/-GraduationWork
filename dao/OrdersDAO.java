package dao;

import bean.OrdersBean;
import bean.Order_detailsBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAO extends DAO {

    /**
     * 注文情報および注文詳細をまとめて登録し、
     * シーケンスで採番された purchase_id を返す (Oracle版)
     * ※ RETURNING 句は使用せず、INSERT 後に SELECT order_seq.CURRVAL で取得します。
     */
    public int registerOrderWithDetails(OrdersBean ob, List<Order_detailsBean> orderDetailsList) throws Exception {
        int generatedPurchaseId = -1;
        
        // orders テーブル INSERT 用 SQL (RETURNING 句なし)
        String orderSql =
            "INSERT INTO orders (purchase_id, user_id, card_id, purchase_date, total_price) " +
            "VALUES (order_seq.NEXTVAL, ?, ?, SYSTIMESTAMP, ?)";
        // シーケンスの最新値を取得する SQL
        String seqSql = "SELECT order_seq.CURRVAL FROM dual";
        
        System.out.println("ob.getCard_id() = " + ob.getCard_id());
        // order_details テーブル INSERT 用 SQL
        String detailSql = "INSERT INTO order_details (purchase_id, game_id, price) VALUES (?, ?, ?)";
        
        Connection con = null;
        PreparedStatement psOrder = null;
        Statement stmtSeq = null;
        PreparedStatement psDetail = null;
        
        try {
            con = getConnection();
            con.setAutoCommit(false); // トランザクション開始
            
            // (1) orders テーブルに INSERT
            psOrder = con.prepareStatement(orderSql);
            psOrder.setInt(1, ob.getUser_id());
            psOrder.setInt(2, ob.getCard_id());
            psOrder.setInt(3, ob.getTotal_price());
            int inserted = psOrder.executeUpdate();
            if (inserted != 1) {
                throw new SQLException("Order insert failed");
            }
            
            // (2) シーケンスから直前の発番値 (CURRVAL) を取得
            stmtSeq = con.createStatement();
            try (ResultSet rs = stmtSeq.executeQuery(seqSql)) {
                if (rs.next()) {
                    generatedPurchaseId = rs.getInt(1);
                }
            }
            
            // (3) order_details テーブルにバッチで INSERT
            psDetail = con.prepareStatement(detailSql);
            for (Order_detailsBean od : orderDetailsList) {
                psDetail.setInt(1, generatedPurchaseId);
                psDetail.setInt(2, od.getGame_id());
                psDetail.setInt(3, od.getPrice());
                psDetail.addBatch();
            }
            psDetail.executeBatch();
            
            con.commit(); // 正常終了ならコミット
            
        } catch (Exception e) {
            if (con != null) {
                try {
                    con.rollback(); // エラー時はロールバック
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (psOrder != null) {
                try { psOrder.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            if (stmtSeq != null) {
                try { stmtSeq.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            if (psDetail != null) {
                try { psDetail.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return generatedPurchaseId;
    }

    /**
     * 特定の purchase_id に紐づく注文情報を取得
     */
    public OrdersBean getOrder(int purchaseId) throws Exception {
        OrdersBean order = null;
        String sql = "SELECT * FROM orders WHERE purchase_id = ?";
        
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
             ps.setInt(1, purchaseId);
             
             try (ResultSet rs = ps.executeQuery()) {
                 if (rs.next()) {  // 最初の1件のみ取得
                     order = new OrdersBean();
                     order.setPurchase_id(rs.getInt("purchase_id"));
                     order.setUser_id(rs.getInt("user_id"));
                     order.setCard_id(rs.getInt("card_id"));
                     
                     Timestamp ts = rs.getTimestamp("purchase_date");
                     if (ts != null) {
                         order.setPurchase_date(ts.toString());
                     }
                     
                     order.setTotal_price(rs.getInt("total_price"));
                 }
             }
        }
        
        return order;
    }

    /**
     * 特定の purchase_id に紐づく注文詳細情報を取得
     */
    public List<Order_detailsBean> getOrderDetails(int purchaseId) throws Exception {
        List<Order_detailsBean> orderDetailsList = new ArrayList<>();
        String sql = "SELECT * FROM order_details WHERE purchase_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, purchaseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order_detailsBean odb = new Order_detailsBean();
                    odb.setPurchase_id(rs.getInt("purchase_id"));
                    odb.setGame_id(rs.getInt("game_id"));
                    odb.setPrice(rs.getInt("price"));
                    orderDetailsList.add(odb);
                }
            }
        }
        return orderDetailsList;
    }

    /**
     * ユーザIDとゲームIDから、購入した注文の purchase_id を取得
     */
    public int getPurchaseId(int userId, int gameId) throws Exception {
        int purchaseId = 0;
        String sql =
            "SELECT orders.purchase_id FROM orders " +
            "INNER JOIN order_details ON orders.purchase_id = order_details.purchase_id " +
            "WHERE user_id = ? AND game_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, gameId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    purchaseId = rs.getInt("purchase_id");
                }
            }
        }
        return purchaseId;
    }

    /**
     * ユーザIDに紐づく全購入ID (purchase_id) 一覧を取得
     */
    public List<Integer> getAllPurchaseId(int userId) throws Exception {
        List<Integer> purchaseIdList = new ArrayList<>();
        String sql =
            "SELECT orders.purchase_id FROM orders " +
            "INNER JOIN order_details ON orders.purchase_id = order_details.purchase_id " +
            "WHERE user_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    purchaseIdList.add(rs.getInt("purchase_id"));
                }
            }
        }
        return purchaseIdList;
    }
}
