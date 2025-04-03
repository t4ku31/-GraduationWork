<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="bean.GameBean, bean.CartBean, dao.GamesDAO, dao.CartsDAO, java.util.List, java.util.ArrayList, java.net.URLEncoder" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>ショッピングカート</title>
    <!-- 既存のCSSファイル（HF.css）と下記カート用CSSを読み込む想定 -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jsp/css/HF.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jsp/css/cart.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <%
       // ログインチェック
       if(session.getAttribute("user_id") == null) {
           String currentUrl = request.getRequestURL().toString();
           if(request.getQueryString() != null) {
               currentUrl += "?" + request.getQueryString();
           }
           String encodedUrl = URLEncoder.encode(currentUrl, "UTF-8");
           response.sendRedirect(request.getContextPath() + "/jsp/login.jsp?redirect=" + encodedUrl);
           return;
       }
       int userId = Integer.parseInt(session.getAttribute("user_id").toString());
       %>
      
       <jsp:forward page="UserDispCart.action">
         <jsp:param name="user_id" value="<%= session.getAttribute(\"user_id\") %>" />
       </jsp:forward>

       <% 
       List<GameBean> gblist = (List<GameBean>) request.getAttribute("gblist");
       // 合計金額計算
       int total = 0;
       for(GameBean g : gblist){
           total += g.getPrice();
       }
    %>


    
    <div class="cart-page-container">
      <h1 class="cart-page-title">ショッピングカート</h1>
      
      <%
        if(gblist == null || gblist.isEmpty()) {
      %>
        <!-- カートが空の場合 -->
        <p class="cart-empty-message">現在、カートに商品はありません。</p>
      <%
        } else {
      %>
        <!-- 2カラムレイアウト: 左に商品一覧、右にサマリー -->
        <div class="cart-main">
          
          <!-- 左カラム: 商品一覧 -->
          <div class="cart-items-section">
  <%
    for(GameBean game : gblist){
        String thumbnail = game.getThumbnail_url();
        if(thumbnail == null || thumbnail.isEmpty()){
            thumbnail = "/image/placeholder_cover.jpg";
        }
        if(!(thumbnail.endsWith(".jpg") || thumbnail.endsWith(".png") || thumbnail.endsWith(".gif"))){
            thumbnail += ".jpg";
        }
  %>
  <!-- アイテム1つ分 -->
  <div class="cart-item-row">
    <!-- サムネイル（クリック可能） -->
    <div class="cart-item-thumb">
      <a href="UserGetGameInfo.action?game_id=<%= game.getGame_id() %>">
        <img src="${pageContext.request.contextPath}<%= thumbnail %>" alt="<%= game.getTitle() %>" />
      </a>
    </div>

    <!-- タイトル・価格（タイトルはクリック可能） -->
    <div class="cart-item-details">
      <a href="UserGetGameInfo.action?game_id=<%= game.getGame_id() %>" class="cart-item-title">
        <%= game.getTitle() %>
      </a>
      <div class="cart-item-price">¥<%= game.getPrice() %></div>
    </div>

    <!-- 削除ボタン -->
    <div class="cart-item-actions">
      <form action="UserCartDelete.action" method="post">
        <input type="hidden" name="game_id" value="<%= game.getGame_id() %>">
        <button type="submit" class="cart-link-button">削除</button>
      </form>
    </div>
  </div>
  <% } %>
</div><!-- /.cart-items-section -->
          
          <!-- 右カラム: サマリー -->
          <div class="cart-summary-section">
            <div class="cart-summary-box">
              <p class="cart-summary-title">推定合計額</p>
              <p class="cart-summary-price">¥<%= total %></p>
              
              <!-- 購入or決済ページへ -->
              <form action="UserGetAllCard.action?referrer=cart.jsp" method="post">
                <button type="submit" class="purchase-button">支払いに進む</button>
              </form>
            </div>
          </div><!-- /.cart-summary-section -->
          
        </div><!-- /.cart-main -->
      <%
        }
      %>
    </div>
    
    <%@ include file="footer.jsp" %>
</body>
</html>
