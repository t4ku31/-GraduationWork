package bean;
import java.io.Serializable;

public class UserBean implements Serializable {
    private int user_id;
    private String role;
    private String email;
    private String username;
    private String avatar;

    // コンストラクタ
    public UserBean() {}

    public UserBean(int user_id, String role, String email, String username, String avatar) {
        this.user_id = user_id;
        this.role = role;
        this.email = email;
        this.username = username;
        this.avatar = avatar;
    }
      public UserBean(int user_id,String username,String email) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
    }

    // 各ゲッター
    public int getUser_id() {
        return user_id;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    // 各セッター
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}