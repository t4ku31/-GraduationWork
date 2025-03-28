package common;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = {"*.action"})
public class FrontController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        try {
            // リクエストパスから最後の部分のみ抽出する
            String path = request.getServletPath();
            if (path.contains("/")) {
                path = path.substring(path.lastIndexOf('/') + 1);
            }
            // "action." パッケージを明示的に付加し、".action" を "Action" に変換する
            String name = "action." + path.replace(".action", "Action");
            
            // Actionクラスを動的に生成
            action.Action action = (action.Action) Class.forName(name).getDeclaredConstructor().newInstance();
            // execute の戻り値のURLへフォワード
            String url = action.execute(request, response);
		if (url.startsWith("redirect:")) {
		    // 「redirect:」プレフィックスを除いたURLへリダイレクト
		    response.sendRedirect(url.substring("redirect:".length()));
		} else {
		    RequestDispatcher rd = request.getRequestDispatcher(url);
		    rd.forward(request, response);
		}
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        doPost(request, response);
    }
}
