package bean;

import java.util.List;
import java.io.Serializable;

public class GameBean implements Serializable {
    private int game_id;
    private String title;
    private String developer;
    private int price;
    private String release_date;
    private String description;
    private String thumbnail_url;
    private List<String> languages;
    private List<String> genres;

    // コンストラクタ
    public GameBean() {}

    public GameBean(int game_id, String title, String developer, int price, String release_date, String description, String thumbnail_url) {
        this.game_id = game_id;
        this.title = title;
        this.developer = developer;
        this.price = price;
        this.release_date = release_date;
        this.description = description;
        this.thumbnail_url = thumbnail_url;
    }

    // 各ゲッター
    public int getGame_id() {
        return game_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDeveloper() {
        return developer;
    }

    public int getPrice() {
        return price;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }
    
    public List<String> getLanguages() {
    	return languages;
    }
    
    public List<String> getGenres() {
        return genres;
    }
    
    // 各セッター
    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }
    
    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }
    
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}