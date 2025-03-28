package action;

import tool.Normalize;
import bean.Payment_cardsBean;
import dao.Payment_cardsDAO;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;

public class UserCreditcardRegisterAction extends Action {

    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        try {
            // 1. card.jspからパラメータ（カード情報）を取得し、変数に格納。
            // ※ JSP の入力項目に合わせてパラメータ名を取得
            String cardType    = request.getParameter("card_type");         // カード種類（プルダウン）
            String cardNumber  = request.getParameter("card_number");         // カード番号
            String expiry      = request.getParameter("card_expiry");           // 有効期限 (MM/YY)
            String cardHolder  = request.getParameter("card_holder_name");      // カード名義
            String securityCode= request.getParameter("security_code");         // セキュリティコード

            // セキュリティコードの桁数を取得
            int len = securityCode.length();

            // 2. Normalize のインスタンスを生成
            Normalize normalize = new Normalize();

            // 3. Normalize の checkCardno を用いてカード番号の桁数チェック（16桁であること）
            if (!normalize.checkCardno(cardNumber)) {
                throw new Exception("クレジットカード番号は16桁で入力してください。");
            }
            // 4. 有効期限の形式チェック（MM/YY形式）※ checkCardExpiry は Normalize に実装されている前提
            if (!normalize.checkCardExpiry(expiry)) {
                throw new Exception("有効期限が有効ではありません。");
            }
            // 5. セキュリティコードが3桁でなければエラー
            if (len != 3) {
                throw new Exception("セキュリティコードは3桁で入力してください。");
            }

            // 6. Payment_cardsDAO のインスタンスを生成
            Payment_cardsDAO card_dao = new Payment_cardsDAO();

            // 7. ユーザーIDはセッションから取得（ログインしていなければエラー）
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("user_id");
            if (userId == null) {
                throw new Exception("ユーザーがログインしていません。");
            }

            // 8. 有効期限の文字列（MM/YY）を分割して月と年を取得
            String[] expiryParts = expiry.split("/");
            int expMonth = Integer.parseInt(expiryParts[0]);
            int expYear  = Integer.parseInt(expiryParts[1]);

            // 9. Payment_cardsBean のパラメータ付きコンストラクタを用いて、各フィールドに値をセット。
            //    card_id は自動採番のため 0 をセット
            Payment_cardsBean pay_cardBean = new Payment_cardsBean(
                0,                   // card_id
                userId,              // ユーザーID
                cardNumber,          // カード番号
                expMonth,            // 有効期限（月）
                expYear,             // 有効期限（年）
                cardHolder,          // カード名義
                cardType,            // カード種類
                Integer.parseInt(securityCode)  // セキュリティコード
            );

            // 10. Payment_cardsDAO の searchCard メソッドを用いて、
            //     DB に今回使用するカード番号が登録されているか確認（※本来はカード番号で検索すべきですが、既存メソッドの仕様に合わせています）
            boolean judge = card_dao.searchCard(Integer.parseInt(cardNumber));
            
            // 11. 登録されていなければ、registerCard メソッドでカード情報を登録する。
            if (!judge) {
                boolean registrationSuccess = card_dao.registerCard(pay_cardBean);
                if (!registrationSuccess) {
                    throw new Exception("カード情報の登録に失敗しました。");
                }
            }

            // 12. 成功時は、カード番号とカード種類をリクエストスコープに格納
            request.setAttribute("card_number", cardNumber);
            request.setAttribute("card_type", cardType);

        } catch (Exception e) {
            // エラー発生時は、エラーメッセージをリクエストスコープに格納し、card.jsp に戻る
            request.setAttribute("message", e.getMessage());
            return "card.jsp";
        }
        
        // 13. 正常登録時は、purchase_conf.jsp に遷移
        return "purchase_conf.jsp";
    }
}
