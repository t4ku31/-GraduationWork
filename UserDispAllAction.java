package action;

import tool.Normalize;
import bean.Payment_cardsBean;
import dao.Payment_cardsDAO;
import tool.*;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.util.ArrayList;
import java.util.List;


public class UserDispAllAction extends Action {
	
	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		try{
			HttpSession session = request.getSession();
			
			int userid =(Integer)session.getAttribute("user_id"); 
			
			Payment_cardsDAO pcdao = new Payment_cardsDAO();
			
			List<Payment_cardsBean> cardslist = new ArrayList<>();
			
			int cardid = (Integerrequest.getAttribute("cad_id");
			
			cardslist = pcdao.getCard(cardid);
			request.setAttribute("card_info",cardslist);
			
		}catch(Exception e) {
			String msg = e.getMessage();
			request.setAttribute("error message",msg);
		} finally {
			return "/jsp/card_info.jsp";
		}
	}
}