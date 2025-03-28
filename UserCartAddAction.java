package action;

import bean.CartBean;
import dao.CartsDAO;
import tool.*;
import javax.servlet.http.*;

public class UserCartAddAction extends Action {

    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        boolean result = false;
        try {
            HttpSession session = request.getSession();
            // セッションからユーザーIDを取得（ログイン済みの場合のみ存在する前提）
            Integer userId = (Integer) session.getAttribute("user_id");
            if (userId == null) {
                // ユーザーがログインしていない場合はログインページへリダイレクト（もしくは別途エラーハンドリング）
                return "/jsp/login.jsp";
            }
            
            // CartBeanにユーザーIDとゲームIDをセット（ゲームIDはリクエストパラメータから取得）
            CartBean cartbean = new CartBean();
            cartbean.setUser_id(userId);
            cartbean.setGame_id(Integer.parseInt(request.getParameter("game_id")));
            
            // CartsDAOのインスタンスを生成してカート追加処理を実施
            CartsDAO cartsdao = new CartsDAO();
            result = cartsdao.addCart(cartbean);
            
            request.setAttribute("result", result);
        } catch(Exception e) {
            e.printStackTrace(); // ログ出力
        }
        
        return "/jsp/cart.jsp";
    }
}
