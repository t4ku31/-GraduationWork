package bean;
import java.io.Serializable;

public class Order_detailsBean implements Serializable {
    private int purchase_id;
    private int game_id;
    private int price;

    // コンストラクタ
    public Order_detailsBean() {}

    public Order_detailsBean(int purchase_id, int game_id, int price) {
        this.purchase_id = purchase_id;
        this.game_id = game_id;
        this.price = price;
    }

    // 各ゲッター
    public int getPurchase_id() {
        return purchase_id;
    }

    public int getGame_id() {
        return game_id;
    }

    public int getPrice() {
        return price;
    }

    // 各セッター
    public void setPurchase_id(int purchase_id) {
        this.purchase_id = purchase_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}