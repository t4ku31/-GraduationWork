<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    HttpSession sessionObj = request.getSession(false);
    String username = (sessionObj != null) ? (String) sessionObj.getAttribute("username") : null;
%>
<header id="header">
  <nav>
    <a href="<%= request.getContextPath() %>/jsp/top.jsp">
      <img id="logo" src="../image/logo.jpg" alt="ロゴ画像">
    </a>
    <a href="<%= request.getContextPath() %>/jsp/search.jsp">
      <p>検索</p>
    </a>
    <% if (username != null) { %>
      <a href="UserDispUserInfo.action">
        <p><%= username %></p>
      </a>
    <% } else { %>
      <a href="<%= request.getContextPath() %>/jsp/login.jsp">
        <p>ログイン</p>
      </a>
    <% } %>
    <a href="<%= request.getContextPath() %>/jsp/cart.jsp">
      <p>カート</p>
    </a>
  </nav>
</header>
