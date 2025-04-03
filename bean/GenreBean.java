package bean;
import java.io.Serializable;

public class GenreBean implements Serializable {
    private int game_id;
    private String genre;

    // コンストラクタ
    public GenreBean() {}

    public GenreBean(int game_id, String genre) {
        this.game_id = game_id;
        this.genre = genre;
    }

    // 各ゲッター
    public int getGame_id() {
        return game_id;
    }

    public String getGenre() {
        return genre;
    }

    // 各セッター
    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
