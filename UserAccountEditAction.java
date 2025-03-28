package action;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.UsersDAO;
import bean.UserBean;

public class UserAccountEditAction extends Action { // ★ Action を継承
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        boolean editResult = false;
		HttpSession session = request.getSession(); //session取得
        try {
            int userId = (int) request.getSession().getAttribute("user_id");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            
            System.out.println(username + "from user_edit.jsp");
            System.out.println(email + "from user_edit.jsp");

            UsersDAO usersDao = new UsersDAO();
            UserBean userBean = new UserBean(userId,username,email);
            
            System.out.println(username + "from userBean.jsp");
            System.out.println(email + "from userBean.jsp");
            
            editResult = usersDao.editUser(userBean);

            if (editResult) {
            	session.setAttribute("username",userBean.getUsername());
                request.setAttribute("email", userBean.getEmail());
                return "/jsp/top.jsp";
            }
            else 
            request.setAttribute("errorMessage", "アカウント更新に失敗しました。");	
            return"/jsp/user_edit.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "アカウント更新に失敗しました。");
            return "/jsp/user_edit.jsp";
        }
    	
    }
}
