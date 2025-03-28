package action;

import bean.*;
import javax.servlet.RequestDispatcher;
import dao.OrdersDAO;
import dao.GamesDAO;
import tool.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

	public class UserDispReceiptAction extends Action{
	
	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
			OrdersBean orders_bean = new OrdersBean();
			OrdersDAO orders_dao = new OrdersDAO();
			GamesDAO games_dao = new GamesDAO();
			GameBean game_bean = new GameBean();
			
			//セッションを取得
			HttpSession session = request.getSession(false);
			//1.sessionからuser_idを取得
			int user_id = (Integer)session.getAttribute("user_id");
				
			
			//requestでgame_idを取得
			int game_id = Integer.parseInt(request.getParameter("game_id"));
			
			//OrdersDAOのpurchaseIdの引数を取得
			int purchase_id= orders_dao.getPurchaseId(user_id,game_id);
			
			//2.OrdersDAOのordersテーブルから注文情報取得
			List<OrdersBean> orders_bean_list = orders_dao.getOrders(purchase_id);
			
			//OrdersDAOのOrder_detailsDAOから注文詳細を取得
			List<Order_detailsBean> od_details_bean = orders_dao.getOrderDetails(purchase_id);
				
			//GamesDAOのgetGameinfoからゲーム情報を取得
			game_bean = games_dao.getGameinfo(game_id);
			
			
			String purchase_date = orders_bean.getPurchase_date();
			int total_price 	 = orders_bean.getTotal_price();
			String title 	     = game_bean.getTitle();
			int price			 = game_bean.getPrice();
			
			
			//ReceiptBeanのインスタンス化と格納	
			ReceiptBean receipt_bean = new ReceiptBean(purchase_id,purchase_date,total_price,game_id,title,price);
			
			
			
			
			
			//requestにセットしJSPにフォワード
			//requestセット
			request.setAttribute("receipt_bean",receipt_bean);
			
			
			//JSPにフォワード
			return("receipt.jsp");
			
	}
}
				
				
				
				
				
				