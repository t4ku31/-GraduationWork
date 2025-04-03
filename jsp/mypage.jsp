<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, bean.*, dao.*" %>
<%
    // リクエストスコープからユーザ情報を取得
    UserBean user = (UserBean) request.getAttribute("userbean");
    if(user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    // DAOのインスタンスを作成
    OrdersDAO ordersDao = new OrdersDAO();
    GamesDAO gamesDao = new GamesDAO();
    
    // ユーザの購入した注文ID一覧を取得（重複する可能性があるため後で重複除去）
    List<Integer> purchaseIdList = ordersDao.getAllPurchaseId(user.getUser_id());
    
    // 重複する注文番号を除去するためLinkedHashSetを利用（順序維持）
    Set<Integer> uniquePurchaseIds = new LinkedHashSet<>();
    for(Integer id : purchaseIdList) {
        uniquePurchaseIds.add(id);
    }
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>マイページ - Steam</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/HF.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/mypage.css">
</head>
<body>
    <%@ include file="/jsp/header.jsp" %>
    
    <div class="container">
        <!-- プロフィールセクション -->
        <div class="profile">
            <div class="profile-info">
                <h2><%= user.getUsername() %></h2>
                <p>Email: <%= user.getEmail() %></p>
                <form action="user_edit.jsp" method="post">
                    <input type="submit" name="user_edit" value="マイページ編集">
                </form>
                <form action="UserLogout.action" method="post">
                    <input type="submit" value="ログアウト">
                </form>
            </div>
        </div>
        
        <!-- 所持ゲーム一覧 -->
        <section class="games">
            <h3>所持ゲーム一覧</h3>
            <div class="order-list">
                <%
                    if(uniquePurchaseIds != null && !uniquePurchaseIds.isEmpty()){
                        for(Integer purchaseId : uniquePurchaseIds){
                            // 各注文の注文情報を取得
                            OrdersBean order = ordersDao.getOrder(purchaseId);
                            // 各注文に対応する注文詳細（購入したゲーム一覧）を取得
                            List<Order_detailsBean> orderDetailsList = ordersDao.getOrderDetails(purchaseId);
                %>
                <div class="order-group">
                    <h3>注文番号: <%= order.getPurchase_id() %> （注文日: <%= order.getPurchase_date() %>）</h3>
                    <div class="game-list">
                        <%
                            for(Order_detailsBean od : orderDetailsList){
                                // ゲーム情報を取得
                                GameBean game = gamesDao.getGameinfo(od.getGame_id());
                                if(game != null){
                                    String thumbnail = game.getThumbnail_url();
                                    if(thumbnail == null || thumbnail.isEmpty()){
                                        thumbnail = "/images/default.jpg"; // サムネイルが無い場合の代替画像
                                    } else if(!(thumbnail.endsWith(".jpg") || thumbnail.endsWith(".png") || thumbnail.endsWith(".gif"))){
                                        thumbnail += ".jpg";
                                    }
                        %>
                        <div class="game-card">
                            <div class="game-thumbnail">
                                <img src="<%= request.getContextPath() + thumbnail %>" alt="<%= game.getTitle() %>">
                            </div>
                            <div class="game-info">
                                <a href="UserGetGameInfo.action?game_id=<%= game.getGame_id() %>">
                                    <%= game.getTitle() %>
                                </a>
                                <p>開発元: <%= game.getDeveloper() %></p>
                                <p>価格: ¥<%= game.getPrice() %></p>
                                <form action="UserDispPurchaseDetails.action" method="post">
                                    <input type="hidden" name="gameid" value="<%= game.getGame_id() %>">
                                    <input type="submit" name="purchase_details" value="注文詳細">
                                </form>
                            </div>
                        </div>
                        <%
                                } // if(game != null)
                            } // for(Order_detailsBean od)
                        %>
                    </div> <!-- end .game-list -->
                </div> <!-- end .order-group -->
                <%
                        } // for each unique purchaseId
                    } else {
                %>
                <p class="no-games">購入したゲームはありません。</p>
                <%
                    }
                %>
            </div> <!-- end .order-list -->
            
            <!-- 支払い情報一覧へのリンク -->
            <form action="UserGetAllCard.action" method="post">
                <input type="submit" name="getAllCard" value="支払い情報一覧">
            </form>
        </section>
    </div>
    
    <%@ include file="/jsp/footer.jsp" %>
</body>
</html>
