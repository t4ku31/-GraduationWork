package bean;
import java.io.Serializable;

public class CartBean implements Serializable {
    private int user_id;
    private int game_id;

    // コンストラクタ
    public CartBean() {}

    public CartBean(int user_id, int game_id) {
        this.user_id = user_id;
        this.game_id = game_id;
    }

    // 各ゲッター
    public int getUser_id() {
        return user_id;
    }

    public int getGame_id() {
        return game_id;
    }

    // 各セッター
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }
}
