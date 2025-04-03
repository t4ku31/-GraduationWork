package bean;
import java.io.Serializable;

public class User_PasswordBean implements Serializable {
    private int user_id;
    private String hashed_password;
    private String salt;

    // コンストラクタ
    public User_PasswordBean() {}

    public User_PasswordBean(int user_id, String hashed_password, String salt) {
        this.user_id = user_id;
        this.hashed_password = hashed_password;
        this.salt = salt;
    }

    // 各ゲッター
    public int getUser_id() {
        return user_id;
    }

    public String getHashed_password() {
        return hashed_password;
    }

    public String getSalt() {
        return salt;
    }

    // 各セッター
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setHashed_password(String hashed_password) {
        this.hashed_password = hashed_password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
