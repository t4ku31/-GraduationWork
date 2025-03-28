package action;

import bean.OrdersBean;
import bean.Order_detailsBean;
import bean.CartBean;
import dao.*
import tool.*;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.*;


public class PurchaseCartAction extends Action {
	
	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		try{
			HttpSession session = request.getSession();
			int userid =(Integer)session.getAttribute("user_id"); 
			
			CartsDAO cdao = new CartsDAO();
			
			List<CartBean> cartbeanlist = new ArrayList<>();
			cartbeanlist = cdao.getCart(userid);//ユーザーIDとゲームIDが入ってくる
			
			//ここまで（1）
			
			OrdersDAO odao = new OrdersDAO();
			//OrdersBean obean = new OrdersBean();
			
			int purchase_id  = (Integer)request.getAttribute("cad_id");
			
			int cardid = (Integer)request.getAttribute("cad_id");
			
	        int total_price = (Integer)session.getAttribute("total_price");
	        
	        OrdersBean ob = new OrdersBean();
	        ob.setPurchase_id(purchase_id);
	        ob.setUser_id(userid);
	        ob.setCard_id(cardid);
	        ob.setTotal_price(total_price);
	        
	    	int purchase_id = odao.registerOrder(ob);
			
	        
	        //ここまで（2）
	        
	        List<Order_detailsBean> odblist = new ArrayList<>();
	        GamesDAO gdao = new GamesDAO();
	        for(CartBean cb : cartbeanlist) {
	            Order_detailsBean odb = new Order_detailsBean();
	            odb.setPurchase_id(purchase_id);
	            odb.setGame_id(cb.getGame_id());
	      		gdao.getGameinfo(cb.getGame_id());
	      		int 
	            odb.setGame_id(cb.getInt("game_id"));
	            odb.setPrice(cb.getInt("price"));
	            odblist.add(odb);
	        }
	            boolean boo = odao.registerOrderDetails(odblist);
            //ここまで（３）
            
            boolean blean = cdao.deleteCartAll(userid);
	        
		}catch(Exception e) {
			String msg = e.getMessage();
			request.setAttribute("error message",msg);
		} finally {
			return "/jsp/Purchase_completed.jsp";
		}
	}
}