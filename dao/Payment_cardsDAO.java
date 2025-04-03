package dao;

import bean.Payment_cardsBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Payment_cardsDAO extends DAO {

    // ******  1.クレジットカード情報を登録する *******
    public int registerCard(Payment_cardsBean cardBean) {
	    // カードをINSERTするSQL（card_idはCARD_SEQ.nextvalで採番）
	    String insertSql = 
	        "INSERT INTO Payment_cards "
	      + "(card_id, user_id, card_type, card_number, security_code, card_expiry_month, card_expiry_year, card_holder_name) "
	      + "VALUES (CARD_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?)";
	    
	    // INSERT後にシーケンスの現在値を取得するSQL
	    String seqSql = "SELECT CARD_SEQ.CURRVAL FROM DUAL"; // Oracle特有

	    try (Connection con = getConnection()) {
	        // 1) INSERT処理
	        try (PreparedStatement ps = con.prepareStatement(insertSql)) {
	            ps.setInt(1, cardBean.getUser_id());
	            ps.setString(2, cardBean.getCard_type());
	            ps.setString(3, cardBean.getCard_number());
	            ps.setInt(4, cardBean.getSecurity_code());
	            ps.setInt(5, cardBean.getCard_expiry_month());
	            ps.setInt(6, cardBean.getCard_expiry_year());
	            ps.setString(7, cardBean.getCard_holder_name());

	            ps.executeUpdate(); // 挿入実行
	        }

	        // 2) 挿入したcard_idを取得する
	        try (PreparedStatement seqPs = con.prepareStatement(seqSql);
	             ResultSet rs = seqPs.executeQuery()) {
	            if (rs.next()) {
	                // シーケンスから生成された最新のcard_idを返す
	                return rs.getInt(1);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    // 失敗したら -1 等を返して呼び出し元で判定
	    return -1;
	}


    // ******  3.クレジットカード情報を取得 *******
    public List<Payment_cardsBean> getCard(int cardId) {
        String sql = "SELECT card_id, user_id, card_type, card_number, security_code, card_expiry_month, card_expiry_year, card_holder_name "
                   + "FROM Payment_cards WHERE card_id = ?";
        List<Payment_cardsBean> cardList = new ArrayList<>();

        try (
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, cardId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Payment_cardsBean card = new Payment_cardsBean();
                card.setCard_id(rs.getInt("card_id"));
                card.setUser_id(rs.getInt("user_id"));
                card.setCard_type(rs.getString("card_type"));
                card.setCard_number(rs.getString("card_number"));
                card.setSecurity_code(rs.getInt("security_code"));
                card.setCard_expiry_month(rs.getInt("card_expiry_month"));
                card.setCard_expiry_year(rs.getInt("card_expiry_year"));
                card.setCard_holder_name(rs.getString("card_holder_name"));
                cardList.add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return cardList;
    }

    // ******  4.クレジットカード情報の重複チェック *******
    public boolean searchCard(String cardnumber) {
        // ここでもテーブル名を修正
        String sql = "SELECT COUNT(*) FROM Payment_cards WHERE card_number = ?";
        boolean judge = false;

        try (
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, cardnumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                judge = (count == 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return judge;
    }

	public boolean deleteCard(int cardId) {
		String sql = "DELETE FROM Payment_cards WHERE card_id = ?";
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, cardId);
			int affectedRows = ps.executeUpdate();
			return (affectedRows > 0);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
    //ユーザーがもってるカード情報
    public List<Payment_cardsBean> getAllCards(int userId) {
	    String sql = "SELECT card_id, user_id, card_type, card_number, security_code, "
	               + "card_expiry_month, card_expiry_year, card_holder_name "
	               + "FROM Payment_cards WHERE user_id = ?";

	    List<Payment_cardsBean> cardList = new ArrayList<>();

	    try (
	        Connection con = getConnection();
	        PreparedStatement ps = con.prepareStatement(sql)
	    ) {
	        ps.setInt(1, userId);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            Payment_cardsBean card = new Payment_cardsBean();
	            card.setCard_id(rs.getInt("card_id"));
	            card.setUser_id(rs.getInt("user_id"));
	            card.setCard_type(rs.getString("card_type"));
	            card.setCard_number(rs.getString("card_number"));
	            card.setSecurity_code(rs.getInt("security_code"));
	            card.setCard_expiry_month(rs.getInt("card_expiry_month"));
	            card.setCard_expiry_year(rs.getInt("card_expiry_year"));
	            card.setCard_holder_name(rs.getString("card_holder_name"));
	            cardList.add(card);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return cardList;
	}
}
