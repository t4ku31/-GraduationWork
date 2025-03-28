package action;

import bean.CartBean;
import dao.CartsDAO;
import tool.*;
import javax.servlet.http.*;

public class UserCartDeleteAction extends Action {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        try {
            HttpSession session = request.getSession();
            // セッションからユーザーIDを取得
            Integer userId = (Integer) session.getAttribute("user_id");
            if (userId == null) {
                response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
                return null;
            }
            
            // リクエストパラメータから game_id を取得
            String gameIdStr = request.getParameter("game_id");
            if(gameIdStr == null || gameIdStr.isEmpty()){
                throw new Exception("game_idパラメータがありません");
            }
            int gameId = Integer.parseInt(gameIdStr);
            
            // CartBean を生成して値をセット
            CartBean cartbean = new CartBean();
            cartbean.setUser_id(userId);
            cartbean.setGame_id(gameId);
            
            CartsDAO cartsdao = new CartsDAO();
            boolean result = cartsdao.deleteCart(cartbean);
            request.setAttribute("result", result);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "/jsp/cart.jsp";
    }
}
