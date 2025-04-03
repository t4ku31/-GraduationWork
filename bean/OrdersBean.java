package bean;
import java.io.Serializable;

public class OrdersBean implements Serializable {
    private int purchase_id;
    private int user_id;
    private int card_id;
    private String purchase_date;
    private int total_price;

    // コンストラクタ
    public OrdersBean() {}

    public OrdersBean(int purchase_id, int user_id, int card_id, String purchase_date, int total_price) {
        this.purchase_id = purchase_id;
        this.user_id = user_id;
        this.card_id = card_id;
        this.purchase_date = purchase_date;
        this.total_price = total_price;
    }

    // 各ゲッター
    public int getPurchase_id() {
        return purchase_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getCard_id() {
        return card_id;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public int getTotal_price() {
        return total_price;
    }

    // 各セッター
    public void setPurchase_id(int purchase_id) {
        this.purchase_id = purchase_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }
}
