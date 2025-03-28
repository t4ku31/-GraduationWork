package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bean.Payment_cardsBean;
import dao.Payment_cardsDAO;

public class UserRegisterCardAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            // リクエストパラメータからカード情報を取得
            int cardId = Integer.parseInt(request.getParameter("card_id"));
            int userId = Integer.parseInt(request.getParameter("user_id"));
            String cardType = request.getParameter("card_type");
            String cardNumber = request.getParameter("card_number");
            int securityCode = Integer.parseInt(request.getParameter("security_code"));
            int expiryMonth = Integer.parseInt(request.getParameter("card_expiry_month"));
            int expiryYear = Integer.parseInt(request.getParameter("card_expiry_year"));
            String cardHolderName = request.getParameter("card_holder_name");
            
            // Payment_cardsBean のパラメータ付きコンストラクタを利用してカード情報をセット
            Payment_cardsBean cardBean = new Payment_cardsBean(
                cardId, 
                userId, 
                cardNumber, 
                expiryMonth, 
                expiryYear, 
                cardHolderName, 
                cardType, 
                securityCode
            );
            
            // DAO を利用してカード情報を登録
            Payment_cardsDAO dao = new Payment_cardsDAO();
            boolean registered = dao.registerCard(cardBean);
            
            if (registered) {
                // 登録成功時: カード番号とカード種類を request にセットし purchase_conf.jsp にフォワード
                request.setAttribute("card_number", cardNumber);
                request.setAttribute("card_type", cardType);
                return "/purchase_conf.jsp";
            } else {
                // 登録失敗時: エラーメッセージを設定し、カード入力画面 (card.jsp) に戻る
                request.setAttribute("message", "カード情報の登録に失敗しました。");
                return "/card.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 例外発生時もエラーメッセージを設定し、カード入力画面 (card.jsp) に戻る
            request.setAttribute("message", "エラーが発生しました。");
            return "/card.jsp";
        }
    }
}
