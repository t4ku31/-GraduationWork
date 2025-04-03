package tool;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * パスワードをソルト付きでハッシュ化し、検証も行うクラスの例。
 * 実際の運用では、キー長や反復回数、例外処理などを
 * サービス要件に合わせて調整してください。
 */
public class SaltedHash {

    // ソルトのバイト長
    private static final int SALT_LENGTH = 16;       // 128bit
    // ストレッチング回数
    private static final int ITERATIONS = 65536;
    // 生成する鍵の長さ
    private static final int KEY_LENGTH = 256;       // 256bit
    // アルゴリズム
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    /**
     * ソルトを生成する
     * @return Base64エンコードされたソルト文字列
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[SALT_LENGTH];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    /**
     * パスワードをハッシュ化する
     * @param password 平文パスワード
     * @param salt Base64エンコードされたソルト文字列
     * @return Base64エンコードされたハッシュ文字列
     */
    public static String hashPassword(String password, String salt) throws Exception {
        // ソルト（Base64）のデコード
        byte[] saltBytes = Base64.getDecoder().decode(salt);

        // PBEKeySpecにパスワードやソルト、アルゴリズム設定を渡す
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] hashBytes = factory.generateSecret(spec).getEncoded();

        // ハッシュをBase64エンコードして返す
        return Base64.getEncoder().encodeToString(hashBytes);
    }

    /**
     * パスワードが正しいか検証する
     * @param candidate パスワード（ユーザがログインフォームに入力したもの）
     * @param salt DB等に保存されたBase64エンコード済みソルト
     * @param storedHash DB等に保存されたBase64エンコード済みハッシュ
     * @return candidate が正しいパスワードなら true
     */
    public static boolean verifyPassword(String candidate, String salt, String storedHash) {
        try {
            // candidate を同じソルトでハッシュ化
            String candidateHash = hashPassword(candidate, salt);
            // DBに保存してある hash と比較
            return candidateHash.equals(storedHash);
        } catch (Exception e) {
            // 何らかの例外が発生した場合は不一致とみなす
            return false;
        }
    }

    /**
     * 動作確認用の main メソッド（任意）
     */
    public static void main(String[] args) throws Exception {
        // ソルト作成
        String salt = SaltedHash.generateSalt();
        System.out.println("Generated Salt  : " + salt);

        // 例: 平文パスワード
        String plainPassword = "Abc12345";

        // ハッシュ生成
        String hashedPassword = SaltedHash.hashPassword(plainPassword, salt);
        System.out.println("Hashed Password : " + hashedPassword);

        // 検証
        boolean match = SaltedHash.verifyPassword(plainPassword, salt, hashedPassword);
        System.out.println("Password match  : " + match);
    }
}
