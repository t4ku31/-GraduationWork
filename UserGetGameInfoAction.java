package action;

import bean.GameBean;
import dao.GamesDAO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserGetGameInfoAction extends Action {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            // リクエストパラメータからゲームIDを取得
            String gameIdStr = request.getParameter("game_id");
            int gameId = Integer.parseInt(gameIdStr);
            
            // DAOを使ってゲーム詳細情報を取得
            GamesDAO gamesdao = new GamesDAO();
            GameBean game = gamesdao.getGameinfo(gameId);
            
            // 取得したゲーム情報をリクエスト属性にセット
            request.setAttribute("game", game);
        } catch(Exception e) {
            e.printStackTrace();  // ログ出力
        } finally {
            // ゲーム詳細表示用のJSPにフォワード
            return "/jsp/game_info.jsp";
        }
    }
}
