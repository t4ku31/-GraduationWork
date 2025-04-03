package dao;

import javax.naming.InitialContext;
import bean.LanguageBean;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.sql.*;

public class LanguagesDAO extends DAO {
		// ******  ゲーム対応言語の取得 ******	
	public List<LanguageBean> getLanguages(int game_id){
		//変数宣言
		List<LanguageBean> languages = new ArrayList<>();
		Connection con = null;//
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
				String sql = "SELECT game_id, language FROM languages WHERE id = ?"; 
				String gameid = String.valueOf(game_id);
			//DBに接続
				con = getConnection();
			//SQL文をセット	
				ps = con.prepareStatement(sql);
			//プレースホルダにgame_idセット	
				ps.setString(1,gameid  );
			//sql文の実行	
				rs = ps.executeQuery();
				
		while(rs.next()) {//rs.next() は、検索結果が1件以上あった場合に、次の行に進むという意味です。
			LanguageBean languagebean = new LanguageBean();
			languagebean.setGame_id(rs.getInt("game_id"));//ここでは、データベースから取得したユーザー名（utilisateur_nom）を ub にセットしています。
			languagebean.setLanguage(rs.getString("language"));
			languages.add(languagebean);  // リストに追加
		}
		}catch(Exception e){
			System.out.println("Error: (LanguagesDAO.search) " + e);
		}finally {//接続を閉じる
			try{
				if(ps != null) ps.close();//ps（PreparedStatement）は、SQL文をデータベースに送るためのものです。
				if(con != null)	con.close();//con（Connection）は、データベースへの接続そのものです。
			}catch(SQLException e){
				con = null;//もしエラーが発生した場合、conをnullにすることで、接続が閉じられていることを示します。
				System.out.println("Error: (LanguagesDAO.search) " + e);
			}
		}
		return languages;
	}
}	
	
				
				
				
				
				
				
				
