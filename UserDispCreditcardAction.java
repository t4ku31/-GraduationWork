package action;

import bean.CartBean;
import dao.CartsDAO;
import tool.*;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;

public class UserCartAddAction extends Action {
	
	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
	//変数宣言	
		try{
			CartBean cartbean= (CartBean)request.getAttribute("gameid");
			CartsDAO cartsdao = new CartsDAO();
	
			result = cartsdao.addCart(cartbean);
			request.setAttribute("result",result);
		
			
		}catch(Exception e){
			e.printStackTrace(); // ログ出力
		}
		
		return "/jsp/cart.jsp";
		
	}
}