package dao;

import bean.GameBean;
import java.util.ArrayList;
import java.sql.*;
import java.util.List;

public class GamesDAO extends DAO {

    // ****** ゲーム情報を全件取得 ******
    public List<GameBean> getAllGame() throws Exception, SQLException {
        List<GameBean> games = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM games";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            rs = ps.executeQuery();

            while (rs.next()) {
                GameBean game = new GameBean();
                game.setGame_id(rs.getInt("game_id"));
                game.setTitle(rs.getString("title"));
                game.setDeveloper(rs.getString("developer"));
                game.setPrice(rs.getInt("price"));
                game.setRelease_date(rs.getString("release_date"));
                game.setDescription(rs.getString("description"));
                game.setThumbnail_url(rs.getString("thumbnail_url"));
                games.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return games;
    }

    // ****** 指定したタイトルのゲームを部分一致検索する ******
	public List<GameBean> searchGameTitle(String title) throws Exception, SQLException {
	    List<GameBean> games = new ArrayList<>();
	    String sql = "SELECT * FROM games WHERE LOWER(title) LIKE ?";
	    
	    try (Connection con = getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	         
	         // 入力されたタイトルを小文字に変換して部分一致検索用にワイルドカードを追加
	         ps.setString(1, "%" + title.toLowerCase() + "%");
	         try (ResultSet rs = ps.executeQuery()) {
	             while (rs.next()) {
	                 GameBean game = new GameBean();
	                 game.setGame_id(rs.getInt("game_id"));
	                 game.setTitle(rs.getString("title"));
	                 game.setDeveloper(rs.getString("developer"));
	                 game.setPrice(rs.getInt("price"));
	                 game.setRelease_date(rs.getString("release_date"));
	                 game.setDescription(rs.getString("description"));
	                 game.setThumbnail_url(rs.getString("thumbnail_url"));
	                 games.add(game);
	             }
	         }
	    } catch (SQLException e) {
	         e.printStackTrace();
	    } catch (Exception e) {
	         e.printStackTrace();
	    }
	    return games;
	}



    // ****** 指定したジャンルのゲームを検索する ******
	public List<GameBean> searchGameGenre(String genre) throws Exception, SQLException {
		List<GameBean> games = new ArrayList<>();
		String sql = "SELECT g.* FROM games g INNER JOIN genres ge ON g.game_id = ge.game_id WHERE ge.genre = ?";
	    
		try (Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(sql)) {
	         
			ps.setString(1, genre);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					GameBean game = new GameBean();
					game.setGame_id(rs.getInt("game_id"));
					game.setTitle(rs.getString("title"));
					game.setDeveloper(rs.getString("developer"));
					game.setPrice(rs.getInt("price"));
					game.setRelease_date(rs.getString("release_date"));
					game.setDescription(rs.getString("description"));
					game.setThumbnail_url(rs.getString("thumbnail_url"));
					games.add(game);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return games;
	}


// ****** 指定したゲームIDのゲーム情報と対応言語、ジャンルを取得する ******
    public GameBean getGameinfo(int gameId) throws Exception, SQLException {
        GameBean game = null;
        String sql = "SELECT * FROM games WHERE game_id = ?";

        // ゲーム情報の取得
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, gameId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    game = new GameBean();
                    game.setGame_id(rs.getInt("game_id"));
                    game.setTitle(rs.getString("title"));
                    game.setDeveloper(rs.getString("developer"));
                    game.setPrice(rs.getInt("price"));
                    game.setRelease_date(rs.getString("release_date"));
                    game.setDescription(rs.getString("description"));
                    game.setThumbnail_url(rs.getString("thumbnail_url"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (game == null) {
            return null;
        }

        // languagesテーブルから対応言語を取得
        List<String> languages = new ArrayList<>();
        String langSql = "SELECT language FROM languages WHERE game_id = ?";
        try (Connection con = getConnection();
             PreparedStatement psLang = con.prepareStatement(langSql)) {
            psLang.setInt(1, gameId);
            try (ResultSet rsLang = psLang.executeQuery()) {
                while (rsLang.next()) {
                    languages.add(rsLang.getString("language"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        game.setLanguages(languages);

        // genresテーブルからジャンル情報を取得（game_idで検索）
        List<String> genres = new ArrayList<>();
        String genreSql = "SELECT genre FROM genres WHERE game_id = ?";
        try (Connection con = getConnection();
             PreparedStatement psGenre = con.prepareStatement(genreSql)) {
            psGenre.setInt(1, gameId);
            try (ResultSet rsGenre = psGenre.executeQuery()) {
                while (rsGenre.next()) {
                    genres.add(rsGenre.getString("genre"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        game.setGenres(genres);

        return game;
    }
}
