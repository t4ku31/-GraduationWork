package action;

import bean.UserBean;
import dao.UsersDAO;
import tool.*;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.util.List;
import java.util.ArrayList;

public class UserDispUserInfoAction extends Action {
	
	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		List<int> purchaseIdList = null;
		List<GameBean> gamebeanList = null;
		List<Order_detailsBean> odBeanList = null;
	//セッション取得
		HttpSession session = request.getSession();
			int userid = Integer.parseInt(session.getAttribute("user_id").toString());
	//bean
		UserBean userbean = null;
		OrdersBean ordersbean = new OrdersBean();
		List<GameBean> gamelist = ArrayList<>();
	//dao取得	
		UsersDAO userdao = new UsersDAO();
		OrdersDAO ordersDao = new OrdersDAO();
	//dbからユーザの持つ注文idをすべて取得	
		purchaseIdList = ordersDao.getAllPurchaseId( session.getAttribute("user_id") );
		
		int i = 0;
		for(int id : purchaseIDList){
			odbeanList = getOrderDetails(id);
			request.setAttribute("odbeanList",odbeanList);
		
	
	//
	
	
	
	//DBからデータを取得する
		userbean = userdao.getUser(userid);
		
		request.setAttribute("avatar",userbean.getAvatar());
		
		return"/jsp/mypage.jsp";
	}
}
