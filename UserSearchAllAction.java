package action;

import bean.GameBean;
import dao.GamesDAO;
import javax.servlet.http.*;
import java.util.ArrayList;
import java.util.List;

public class UserSearchAllAction extends Action {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        try {
        	
          	List<GameBean> gamelist = new ArrayList<>();
          	GamesDAO gamesdao = new GamesDAO();
           	
       		gamelist = gamesdao.getAllGame();
			request.setAttribute("gamelist",gamelist);
  
        } catch (Exception e) {
        	e.printStackTrace(); // ログ出力
        } finally {
            return "/jsp/search.jsp";
        }
    }
}
