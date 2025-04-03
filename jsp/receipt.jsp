<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" isELIgnored="true" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.OrdersBean" %>
<%@ page import="bean.GameBean" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>購入確認レシート</title>
  <link rel="stylesheet" type="text/css" href="css/receipt.css">
  <link rel="stylesheet" type="text/css" href="css/HF.css">
</head>
<body>
  <div class="container">
    <%@ include file="header.jsp" %>
    
    <section class="order-details">
      <h2>注文詳細</h2>
      <%
        OrdersBean ordersBean = (OrdersBean) request.getAttribute("ordersBean");
        if (ordersBean != null) {
      %>
          <p>注文番号: <%= ordersBean.getPurchase_id() %></p>
          <p>注文日: <%= ordersBean.getPurchase_date() %></p>
          <p>合計金額: <%= ordersBean.getTotal_price() %>円</p>
      <%
        } else {
      %>
          <p>注文情報が見つかりません。</p>
      <%
        }
      %>
    </section>
    
    <section class="game-details">
      <h2>購入したゲーム</h2>
      <ul>
      <%
        List<GameBean> gameBeanList = (List<GameBean>) request.getAttribute("gameBeanList");
        if (gameBeanList != null && !gameBeanList.isEmpty()) {
          for (GameBean game : gameBeanList) {
      %>
            <li>
              <% 
                if (game != null && game.getThumbnail_url() != null && !game.getThumbnail_url().isEmpty()) {
                  String thumbnail = game.getThumbnail_url();
                  // 拡張子が jpg/png/gif で終わらない場合は ".jpg" を付加
                  if (!(thumbnail.endsWith(".jpg") || thumbnail.endsWith(".png") || thumbnail.endsWith(".gif"))) {
                    thumbnail += ".jpg";
                  }
              %>
              <div class="game-card">
                <div class="game-thumbnail">
                  <img src="<%= request.getContextPath() + thumbnail %>" alt="<%= game.getTitle() %>">
                </div>
              </div>
              <% } %>
              <h3><%= game.getTitle() %></h3>
              <p><%= game.getDescription() %></p>
              <p>価格: <%= game.getPrice() %>円</p>
            </li>
      <%
          }
        } else {
      %>
            <li>ゲーム情報が見つかりません。</li>
      <%
        }
      %>
      </ul>
    </section>
    
    <%@ include file="footer.jsp" %>
  </div>
</body>
</html>
