package action;

import tool.Normalize;
import bean.Payment_cardsBean;
import dao.Payment_cardsDAO;
import tool.*;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;

public class UserCreditcardDeleteAction extends Action {
	
	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
	try{
		//1.card.jspからパラメータ（カード情報）を取得し、変数に格納。
         String cardNumber = request.getParameter("cardNumber");
         String expriy = request.getParameter("expriy");
         String cardHolder = request.getParameter("cardHolder");
         String securityCode = request.getParameter("securityCode");
			
		 int len =securityCode.length();
		
		
		
		//4.Normalizeのインスタンスを生成
		Normalize normalize= new Normalize();
		
		//5.Normalize の checkCardno を用いて、パラメータのカード番号の桁数を確認する。
		//結果がfalseならfalseをreturnする。Trueなら下記の処理をする。
		if(!normalize.checkCardno(cardNumber)){ //NormalizeのcheckCardnoを用いてパラメータのカード番号の桁数を確認する。
			throw new Exception("クレジットカード番号は16桁で入力してください。");
		}
		if(!normalize.checkCardExpiry(expriy)){
			throw  new Exception("有効期限が有効ではありません。");
		}
		if(len == 3){
			throw new Exception("セキュリティコードが有効ではありません");
		}
		
		//2.Payment_cardsDAOのインスタンスを生成。
		Payment_cardsDAO card_dao = new Payment_cardsDAO();
		
		//3.クレジットカード情報の登録	Payment_cardsBeanのインスタンスを生成。
		Payment_cardsBean pay_cardBean = new Payment_cardsBean();
		String str = expriy;
		String expriyy[] = str.split("/");
		
		//7.Payment_cardsBeanのセッターを用いて、パラメータの値をフィールドに格納。
		pay_cardBean.setCard_number(cardNumber);
		pay_cardBean.setCard_expiry_month(Integer.parseInt(expriyy[0]));
		pay_cardBean.setCard_expiry_year(Integer.parseInt(expriyy[1]));
		pay_cardBean.setCard_holder_name(cardHolder);
		pay_cardBean.setSecurity_code(Integer.parseInt(securityCode));
		
		
		//6.Payment_cardsDAOのsearchCardメソッドを用いて(※引数はカード番号)DBに今回使用するカードが登録されてるか検索
		boolean judge = card_dao.searchCard(Integer.parseInt(cardNumber));
		
		//7 カードが登録されていれば Payment_cardsDAO の deleteCard メソッドで削除する。
		if(judge == true){
			int card_id = Integer.parseInt(cardNumber);//int型に変換
			boolean pycdb = card_dao.deleteCard(card_id);//削除
			if(pycdb == false){
				throw new Exception("削除できません");
			}
		}
			request.setAttribute("cardnumber",cardNumber);//左が変数名、右が入れたい値　リクエストスコープ格納
		
		}catch(Exception e){
			return "jsp/card.jsp";
		}
		
		 //10 card_info.jspの絶対パスをreturnする。
        return "jsp/card_info.jsp";
    }
}