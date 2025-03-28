package action;

import bean.UserBean;
import bean.User_PasswordBean;
import dao.UsersDAO;
import dao.User_PasswordsDAO;
import tool.*;
import tool.SaltedHash;
import javax.servlet.http.*;

public class UserAccountCreateAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        boolean errorresult = true;
        
        try {
            String  = request.getParameter("username");
        	String password = request.getParameter("password");
        	String email = request.getParameter("email");
	

            // バリデーション（※ Normalize クラスの実装は別途用意してください）
            Normalize normalize = new Normalize();
            if (!normalize.checkUsername(username)) {
                throw new Exception("ユーザー名は6～20文字の英数字またはアンダースコアのみ使用できます。");
            }
            if (!normalize.checkPass(password)) {
                throw new Exception("パスワードは8～16文字で、大文字・小文字・数字を含む必要があります。");
            }
            if (!normalize.checkEmail(email)) {
                throw new Exception("正しいメールアドレスを入力してください。");
            }
            
            UsersDAO usersDao = new UsersDAO();
            // 既に同じユーザー名が存在しないかチェック
            UserBean existingUser = usersDao.getUser(username);
            if (existingUser != null) {
                // 既に登録済みの場合
                request.setAttribute("registrationStatus", "already");
                request.setAttribute("errorresult", false);
                return "/jsp/sign_up.jsp";
            }
            
            // ユーザー情報の登録
            UserBean userBean = new UserBean();
            userBean.setUsername("username");
            userBean.setEmail("email");
            userBean.setRole("user");
            userBean.setAvatar("default_icon.png");
            
            int userId = usersDao.registerUser(userBean);
            if (userId == -1) {
                throw new Exception("ユーザー登録に失敗しました。");
            }
            
            // パスワードのハッシュ化と user_passwords テーブルへの登録
            String salt = SaltedHash.generateSalt();
            String hashedPassword = SaltedHash.hashPassword(password, salt);
            
            User_PasswordBean passwordBean = new User_PasswordBean();
            passwordBean.setUser_id(userId);
            passwordBean.setHashed_password(hashedPassword);
            passwordBean.setSalt(salt);
            
            User_PasswordsDAO userPasswordsDao = new User_PasswordsDAO();
            boolean passwordRegistered = userPasswordsDao.registerHashedPassword(passwordBean);
            if (!passwordRegistered) {
                throw new Exception("パスワードの登録に失敗しました。");
            }
            
            // セッションにユーザー情報を保存
            HttpSession session = request.getSession();
            session.setAttribute("user_id", userId);
            session.setAttribute("username", username);
            
            // 登録成功
            request.setAttribute("registrationStatus", "success");
            
        } catch (Exception e) {
            errorresult = false;
            // ORA-00001 の場合はユーザー名重複とみなす
            String msg = e.getMessage();
            if (msg != null && msg.contains("ORA-00001")) {
                request.setAttribute("errorMessage", "すでに使用されているユーザー名です。");
            } else {
                request.setAttribute("errorMessage", msg);
            }
        }
        
        request.setAttribute("errorresult", errorresult);
        return "/jsp/sign_up.jsp";
    }
}
