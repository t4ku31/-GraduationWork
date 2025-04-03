package bean;

import java.io.Serializable;

public class Payment_cardsBean implements Serializable {
    private int card_id;
    private int user_id;
    private String card_number;
    private int card_expiry_month;
    private int card_expiry_year;
    private String card_holder_name;
    private String card_type;
    private int security_code;

    // デフォルトコンストラクタ
    public Payment_cardsBean() {}

    // パラメータ付きコンストラクタ (8パラメータ版)
    public Payment_cardsBean(int card_id, int user_id, String card_number, 
                             int card_expiry_month, int card_expiry_year, 
                             String card_holder_name, String card_type, 
                             int security_code) {
        this.card_id = card_id;
        this.user_id = user_id;
        this.card_number = card_number;
        this.card_expiry_month = card_expiry_month;
        this.card_expiry_year = card_expiry_year;
        this.card_holder_name = card_holder_name;
        this.card_type = card_type;
        this.security_code = security_code;
    }

    // 以下、各ゲッター・セッター
    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public int getCard_expiry_month() {
        return card_expiry_month;
    }

    public void setCard_expiry_month(int card_expiry_month) {
        this.card_expiry_month = card_expiry_month;
    }

    public int getCard_expiry_year() {
        return card_expiry_year;
    }

    public void setCard_expiry_year(int card_expiry_year) {
        this.card_expiry_year = card_expiry_year;
    }

    public String getCard_holder_name() {
        return card_holder_name;
    }

    public void setCard_holder_name(String card_holder_name) {
        this.card_holder_name = card_holder_name;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public int getSecurity_code() {
        return security_code;
    }

    public void setSecurity_code(int security_code) {
        this.security_code = security_code;
    }
}
