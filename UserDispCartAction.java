package action;

import bean.CartBean;
import bean.GameBean;
import dao.CartsDAO;
import dao.GamesDAO;
import tool.*;
import javax.servlet.http.*;
import java.util.List;
import java.util.ArrayList;
import java.net.URLEncoder;

public class UserDispCartAction extends Action {
    
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        
        // ログインチェック：ユーザーIDがセッションにない場合はログインページへリダイレクト
        if (session.getAttribute("user_id") == null) {
            String currentUrl = request.getRequestURL().toString();
            if (request.getQueryString() != null) {
                currentUrl += "?" + request.getQueryString();
            }
            String encodedUrl = URLEncoder.encode(currentUrl, "UTF-8");
            return "/login.jsp?redirect=" + encodedUrl;
        }
        
        // セッションからユーザーIDを取得
        int userid = Integer.parseInt(session.getAttribute("user_id").toString());
        
        // DAOの生成
        GamesDAO gamesDao = new GamesDAO();
        CartsDAO cartsDao = new CartsDAO();
        
        // カート情報を取得
        List<CartBean> cartList = cartsDao.getCart(userid);
        List<GameBean> gameList = new ArrayList<>();
        
        // 各カート内アイテムに対して、ゲーム情報を取得しリストに格納
        for (CartBean cart : cartList) {
            gameList.add(gamesDao.getGameinfo(cart.getGame_id()));
        }
        
        request.setAttribute("gblist", gameList);
        return "/jsp/cart.jsp";
    }
}
