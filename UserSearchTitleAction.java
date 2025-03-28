package action;

import bean.GameBean;
import dao.GamesDAO;
import javax.servlet.http.*;
import java.util.ArrayList;
import java.util.List;

public class UserSearchTitleAction extends Action {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        try {
        	String title = request.getParameter("title");
          	List<GameBean> gamelist = new ArrayList<>();
          	GamesDAO gamesdao = new GamesDAO();
           	
           	gamelist = gamesdao.searchGameTitle(title);
			request.setAttribute("gamelist",gamelist);
  
        } catch (Exception e) {
        	e.printStackTrace(); // ログ出力
        } finally {
            return "/jsp/search.jsp";
        }
    }
}
