package action;

import bean.UserBean;
import bean.User_PasswordBean;
import dao.UsersDAO;
import dao.User_PasswordsDAO;
import tool.*;
import javax.servlet.http.*;

public class UserLoginAction extends Action {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        boolean loginSuccess = false;
        String loginError = null;

        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            SaltedHash saltedhash = new SaltedHash();
            User_PasswordsDAO userpasswordsdao = new User_PasswordsDAO();
            UsersDAO usersdao = new UsersDAO();

            UserBean userbean = usersdao.getUser(username);

            if (userbean != null) {
                User_PasswordBean userpassbean = userpasswordsdao.getHashedPass(userbean.getUser_id());
                String hashedpass = saltedhash.hashPassword(password, userpassbean.getSalt());

                if (userpassbean.getHashed_password().equals(hashedpass)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user_id", userbean.getUser_id());
                    session.setAttribute("username", userbean.getUsername());
                    
                    // "redirect"パラメータがあればセッションに保存
                    String redirectParam = request.getParameter("redirect");
                    if(redirectParam != null && !redirectParam.isEmpty()){
                        session.setAttribute("redirectAfterLogin", redirectParam);
                    }
                    loginSuccess = true;
                }
            }

            if (!loginSuccess) {
                loginError = "ユーザー名またはパスワードが違います";
            }

        } catch (Exception e) {
            loginError = "ログイン処理中にエラーが発生しました";
        } finally {
            request.setAttribute("loginSuccess", loginSuccess);
            request.setAttribute("loginError", loginError);
            
            if (loginSuccess) {
		    HttpSession session = request.getSession();
		    String redirectAfterLogin = (String) session.getAttribute("redirectAfterLogin");
		    if (redirectAfterLogin != null && !redirectAfterLogin.isEmpty()){
		        session.removeAttribute("redirectAfterLogin");
		        // リダイレクト用プレフィックスを付与して返す
		        return "redirect:" + redirectAfterLogin;
		    } else {
		        return "/jsp/top.jsp";
		    }
		} else {
		    return "/jsp/login.jsp";
		}
        }
    }
}
