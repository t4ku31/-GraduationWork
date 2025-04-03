<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, bean.GameBean" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>購入確認</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/HF.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/card.css">
</head>
<body>
  <%@ include file="header.jsp" %>

  <div class="card-container">
    <h1 class="card-title">購入確認</h1>

    <% if(request.getAttribute("message") != null) { %>
      <div class="error-message"><%= request.getAttribute("message") %></div>
    <% } %>

    <p>以下の内容でよろしければ、購入を確定してください。</p>

    <%
      List<GameBean> gameList = (List<GameBean>) request.getAttribute("gameList");
      Integer totalAmount = (Integer) request.getAttribute("totalAmount");
      String cardType = (String) request.getAttribute("cardType");
      String cardNumber = (String) request.getAttribute("cardNumber");
    %>

    <!-- カート内のゲーム一覧 -->
    <table border="1">
      <tr><th>画像</th><th>タイトル</th><th>価格</th></tr>
      <%
        if (gameList != null) {
          for (GameBean game : gameList) {
            String thumbnail = game.getThumbnail_url();
            if(thumbnail == null || thumbnail.isEmpty()){
                thumbnail = "/image/placeholder_cover.jpg";
            }
            if(!(thumbnail.endsWith(".jpg") || thumbnail.endsWith(".png") || thumbnail.endsWith(".gif"))){
                thumbnail += ".jpg";
            }
      %>
        <tr>
          <td><img src="${pageContext.request.contextPath}<%= thumbnail %>" alt="ゲーム画像" width="100"></td>
          <td><%= game.getTitle() %></td>
          <td><%= game.getPrice() %>円</td>
        </tr>
      <%
          }
        }
      %>
    </table>

    <p>合計金額: <strong><%= (totalAmount != null) ? totalAmount : 0 %>円</strong></p>

    <!-- 支払いに使用するカード情報 -->
    <p>支払カード: <%= (cardType != null) ? cardType.toUpperCase() : "不明" %></p>
    <p>カード番号 下4桁: <%= (cardNumber != null) ? cardNumber : "****" %></p>

    <!-- 購入確定ボタン -->
    <form action="UserCompletePurchase.action" method="post">
      <!-- カードID -->
      <input type="hidden" name="card_id" value="<%= request.getAttribute("card_id") != null ? request.getAttribute("card_id") : 9999 %>">
      <!-- 合計金額 -->
      <input type="hidden" name="total_price" value="<%= request.getAttribute("totalAmount") != null ? request.getAttribute("totalAmount") : 0 %>">
      <button type="submit">購入を確定する</button>
    </form>
    
  </div>

  <%@ include file="footer.jsp" %>
</body>
</html>
