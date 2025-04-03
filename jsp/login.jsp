<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%
    String loginError = (String) request.getAttribute("loginError");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ログイン</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/HF.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/login.css">
    <style>
        .error-message {
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <div class="main-content">
        <h2>ログイン</h2>
        
        <% if (loginError != null) { %>
            <p class="error-message"><%= loginError %></p>
        <% } %>
        
        <form action="UserLogin.action" method="post">
            <label for="username">ユーザー名</label>
            <input type="text" id="username" name="username" required>
            
            <label for="password">パスワード</label>
            <input type="password" id="password" name="password" required>
            
            <button type="submit" class="button">ログイン</button>
        </form>
        
        <form action="sign_up.jsp" method="post">
            <button type="submit" class="button">アカウントを作成</button>
        </form>
    </div>
    
    <%@ include file="footer.jsp" %>
</body>
</html>
