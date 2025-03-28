package action;

import tool.Normalize;
import bean.Payment_cardsBean;
import dao.Payment_cardsDAO;
import tool.*;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;

public class UserCreditcardAddAction extends Action {
	
	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
	try{
		//1.card.jspからパラメータ（カード情報）を取得し、変数に格納。
         String cardNumber = request.getParameter("cardNumber");
         String expriy = request.getParameter("expriy");
         String cardHolder = request.getParameter("cardHolder");
         String securityCode = request.getParameter("securityCode");
			
		 int len =securityCode.length();//lengthは長さ(文字列)をlen(変数)に格納
		
		
		
		//4.Normalizeのインスタンスを生成
		Normalize normalize = new Normalize();
		
		//5.NormalizeのcheckCardnoを用いてパラメータのカード番号の桁数を確認する。
		//6.結果がfalseならfalseをreturnする。Trueなら下記の処理をする。
		if(!normalize.checkCardno(cardNumber)){//NormalizeのcheckCardnoを用いてパラメータのカード番号の桁数を確認する。
			throw new Exception("クレジットカード番号は16桁で入力してください。");
		}
		if(!normalize.checkCardExpiry(expriy)){
			throw new Exception("有効期限が有効ではありません。");
		}
		if(len != 3){
			throw new Exception("セキュリティコードが有効ではありません");
		}
		
		
		//2.Payment_cardsDAOのインスタンスを生成
		Payment_cardsDAO card_dao = new Payment_cardsDAO();
		
		//3.クレジットカード情報の登録	Payment_cardsBeanのインスタンスを生成。
		Payment_cardsBean pay_cardBean = new Payment_cardsBean();
		
		String str = expriy;
		String expripri[] = str.split("/");
		//7.Payment_cardsBeanのセッターを用いて、パラメータの値をフィールドに格納。
		pay_cardBean.setCard_number(cardNumber);
		pay_cardBean.setCard_expiry_month(Integer.parseInt(expripri[0]));
		pay_cardBean.setCard_expiry_year(Integer.parseInt(expripri[1]));
		pay_cardBean.setCard_holder_name(cardHolder);
		pay_cardBean.setSecurity_code(Integer.parseInt(securityCode));
		
		//8.Payment_cardsDAOのsearchCardメソッドを用いて(※引数はカード番号)DBに今回使用するカードが登録されてるか確認
		boolean judge = card_dao.searchCard(Integer.parseInt(cardNumber));
		
		////9.登録されていなければPayment_cardsDAOのregisterCardメソッドで登録する。
		if(judge == false){
			boolean pycdb = card_dao.registerCard(pay_cardBean);
			if(pycdb == false){
				throw new Exception("登録できません");
				}
			}
		
		request.setAttribute("cardnumber",cardNumber);//左が変数名、右が入れたい値　リクエストスコープ格納
		
		} catch (Exception e) {
            return "jsp/card.jsp";
        }
        
        //10 card_info.jspの絶対パスをreturnする。
        return "jsp/card_info.jsp";
    

    }
}


		
		
		
		
		  
		