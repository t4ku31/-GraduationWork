package tool;

import java.util.Calendar;

public class Normalize {
    
    /**
     * ユーザ名のチェック例
     * 条件: 
     *   - 6文字以上20文字以内
     *   - 英数字またはアンダースコアのみ
     */
    public boolean checkUsername(String username) {
        if(username == null) {
            return false;
        }
        // 正規表現: 6～20文字の英数字またはアンダースコア
        return username.matches("^[a-zA-Z0-9_]{6,20}$");
    }

    /**
     * パスワードのチェック例
     * 条件:
     *   - 8文字以上16文字以内
     *   - 英大文字・英小文字・数字それぞれ1文字以上含む
     */
    public boolean checkPass(String pass) {
        if(pass == null) {
            return false;
        }
        // 8～16文字のうち英大文字、小文字、数字が最低1つずつ含まれているか
        boolean lengthOk = pass.matches("^.{8,16}$");                   // 文字数のみチェック
        boolean upperOk  = pass.matches(".*[A-Z].*");                  // 大文字が含まれる
        boolean lowerOk  = pass.matches(".*[a-z].*");                  // 小文字が含まれる
        boolean digitOk  = pass.matches(".*\\d.*");                    // 数字が含まれる
        return lengthOk && upperOk && lowerOk && digitOk;
    }

    /**
     * メールアドレスのチェック例
     * 条件:
     *   - 一般的なメール形式にマッチするか
     */
    public boolean checkEmail(String email) {
        if(email == null) {
            return false;
        }
        // シンプルなチェック
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    /**
     * カード番号のチェック例(単純版)
     * 条件:
     *   - 16桁の数字のみ(例: クレジットカード)
     */
    public boolean checkCardno(String cardNo) {
        if(cardNo == null) {
            return false;
        }
        // 単純に16桁かをチェック
        return cardNo.matches("^\\d{16}$");
    }
    
    /**
     * クレジットカードの有効期限のチェック例
     * 条件:
     *   - フォーマット: MM/yy または MM/yyyy
     *   - 月は 01～12
     *   - 現在日付以降の有効期限であること（当月も有効）
     */
    public boolean checkCardExpiry(String expiry) {
        if(expiry == null) {
            return false;
        }
        // "MM/yy" または "MM/yyyy" 形式をチェック
        if (!expiry.matches("^(0[1-9]|1[0-2])/((\\d{2})|(\\d{4}))$")) {
            return false;
        }
        String[] parts = expiry.split("/");
        int month = Integer.parseInt(parts[0]);
        int year;
        if (parts[1].length() == 2) {
            // 2桁の場合は 2000 年を基準にする (例: "25" → 2025)
            year = Integer.parseInt(parts[1]) + 2000;
        } else {
            year = Integer.parseInt(parts[1]);
        }
        
        // 現在の年月を取得 (Calendar.MONTH は 0 から始まるため +1)
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH) + 1;
        
        // 有効期限が過去の場合は false
        if (year < currentYear || (year == currentYear && month < currentMonth)) {
            return false;
        }
        return true;
    }
}