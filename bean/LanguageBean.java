package bean;
import java.io.Serializable;

public class LanguageBean implements Serializable {
    private int game_id;
    private String language;

    // コンストラクタ
    public LanguageBean() {}

    public LanguageBean(int game_id, String language) {
        this.game_id = game_id;
        this.language = language;
    }

    // 各ゲッター
    public int getGame_id() {
        return game_id;
    }

    public String getLanguage() {
        return language;
    }

    // 各セッター
    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
