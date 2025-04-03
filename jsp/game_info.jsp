<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="dao.GamesDAO, dao.CartsDAO, dao.OrdersDAO, bean.GameBean, bean.CartBean, java.util.List, java.net.URLEncoder" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>
        <%
            String gameIdStr = request.getParameter("game_id");
            int gameId = 0;
            if(gameIdStr != null) {
                try {
                    gameId = Integer.parseInt(gameIdStr);
                } catch(Exception e) { }
            }
            GamesDAO gamesDao = new GamesDAO();
            GameBean game = gamesDao.getGameinfo(gameId);
            out.print(game != null ? game.getTitle() : "ゲーム情報なし");
        %>
    </title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jsp/css/HF.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jsp/css/game_info.css">
</head>
<body>
    <%@ include file="header.jsp" %>

    <%
       boolean loggedIn = (session.getAttribute("user_id") != null);
       int userId = 0;
       if(loggedIn) {
           userId = Integer.parseInt(session.getAttribute("user_id").toString());
       }

       String currentUrl = request.getRequestURL().toString();
       if(request.getQueryString() != null) {
           currentUrl += "?" + request.getQueryString();
       }
       String encodedUrl = URLEncoder.encode(currentUrl, "UTF-8");

       boolean inCart = false;
       boolean isPurchased = false;

       if(loggedIn && game != null) {
           CartsDAO cartsDao = new CartsDAO();
           List<CartBean> cartList = cartsDao.getCart(userId);
           if(cartList != null) {
               for(CartBean cart : cartList) {
                   if(cart.getGame_id() == game.getGame_id()) {
                       inCart = true;
                       break;
                   }
               }
           }

           // **購入済みチェック**
           OrdersDAO ordersDao = new OrdersDAO();
           int purchaseId = ordersDao.getPurchaseId(userId, game.getGame_id());
           isPurchased = (purchaseId > 0);
       }

       String cover = "";
       if(game != null) {
           cover = game.getThumbnail_url();
           if(cover == null || cover.isEmpty()){
               cover = "/image/placeholder_cover.jpg";
           }
           if(!(cover.endsWith(".jpg") || cover.endsWith(".png") || cover.endsWith(".gif"))){
               cover += ".jpg";
           }
       }
    %>

    <div class="game_info-container">
        <% if(game != null) { %>
            <div class="game_info-header">
                <div class="game_info-trailer">
                    <img src="${pageContext.request.contextPath}<%= cover %>" alt="カバー画像">
                </div>

                <div class="game_info-sidebar">
                    <h1 class="game_info-title"><%= game.getTitle() %></h1>
                    <div class="game_info-meta">
                        <span>開発元: <%= game.getDeveloper() %></span>
                        <span>リリース日: <%= game.getRelease_date() %></span>
                    </div>

                    <div class="game_info-purchase">
                        <div class="game_info-price">
                            ¥<%= game.getPrice() %>
                        </div>
                        <%
                            if (!loggedIn) { %>
                                <button class="cart-btn" type="button" onclick="location.href='<%= request.getContextPath() %>/jsp/login.jsp?redirect=<%= encodedUrl %>'">カートに入れる</button>
                        <% } else {
                               if (isPurchased) { %>
                                    <button class="cart-btn purchased-btn" type="button" onclick="location.href='UserDispUserInfo.action'">購入済み</button>
                        <%     } else if (inCart) { %>
                                    <button class="cart-btn" type="button" onclick="location.href='<%= request.getContextPath() %>/jsp/cart.jsp'">カートの中</button>
                        <%     } else { %>
                                    <form action="UserCartAdd.action" method="post">
                                        <input type="hidden" name="game_id" value="<%= game.getGame_id() %>">
                                        <button class="cart-btn" type="submit">カートに入れる</button>
                                    </form>
                        <%     }
                             } %>
                    </div>
                </div>
            </div>

            <div class="game_info-genres">
                <h3>ジャンル</h3>
                <ul>
                    <% for(String genre : game.getGenres()) { %>
                        <li><%= genre %></li>
                    <% } %>
                </ul>
            </div>

            <div class="game_info-languages">
                <h3>対応言語</h3>
                <ul>
                    <% for(String lang : game.getLanguages()) { %>
                        <li><%= lang %></li>
                    <% } %>
                </ul>
            </div>

            <div class="game_info-description">
                <h2>このゲームについて</h2>
                <p><%= game.getDescription() %></p>
            </div>

        <% } else { %>
            <div class="error-message">
                ゲーム情報が取得できませんでした。URLパラメータ "game_id" を確認してください。
            </div>
        <% } %>
    </div>

    <%@ include file="footer.jsp" %>
</body>
</html>
