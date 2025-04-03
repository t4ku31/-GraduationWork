<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    request.setCharacterEncoding("UTF-8");
%>
<%@ page import="bean.UserBean, dao.UsersDAO" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jsp/css/HF.css">
    <title>ユーザー編集</title>
</head>
<body>

    <%-- ヘッダーをページの一番上に固定 --%>
    <jsp:include page="header.jsp" />

    <%-- メインコンテンツ --%>
    <%
        UsersDAO usersdao = new UsersDAO();
        // セッションから取得した user_id を Integer 型として扱い、String に変換する
        Integer userIdObj = (Integer) session.getAttribute("user_id");
		int userId = userIdObj.intValue();
		UserBean user = usersdao.getUser(userId);

        String frompage = request.getParameter("user_edit");
    %>
    
    <h1><%= frompage %></h1>

    <h2 class="title">ユーザー情報の編集</h2>

    <%-- エラーメッセージ表示 --%>
    <% String errorMessage = (String) request.getAttribute("errorMessage");
       if (errorMessage != null && !errorMessage.isEmpty()) { %>
        <p class="error-message"><%= errorMessage %></p>
    <% } %>

    <%-- ユーザー編集フォーム --%>
    <form action="UserAccountEdit.action" method="post" class="edit-form">
        <label for="username">ユーザー名:</label>
        <input type="text" id="username" name="username" class="input-field"
               value="<%= (session.getAttribute("username") != null) ? session.getAttribute("username") : "" %>" 
               required>

        <label for="email">メールアドレス:</label>
        <% if (frompage != null && frompage.equals("マイページ編集")) { %>
            <input type="email" id="email" name="email" class="input-field"
                   value="<%= (user != null && user.getEmail() != null && !user.getEmail().isEmpty()) ? user.getEmail() : "OMANKO" %>" 
                   required>
        <% } else { %>
            <input type="email" id="email" name="email" class="input-field"
                   value="<%= (request.getAttribute("email") != null) ? request.getAttribute("email") : "JYURUJYURU" %>" 
                   required>
        <% } %>

        <input type="submit" class="button" value="保存">
    </form>

    <a href="top.jsp" class="back-link">トップページに戻る</a>

    <%-- フッターをページの一番下に固定 --%>
    <jsp:include page="footer.jsp" />

</body>
</html>
