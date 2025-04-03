<%@page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTMl>
<html>
	<head>
		<meta charset="UTF-8">
		<title>login.jsp</title>
	</head>
	<body>
	<%@ include file="header.jsp" %>
		<p><br>
		サインイン
		</p>	
		<form action="<%=request.getContextPath()%>/UserLoginDEBUG.action" method="POST">
			ユーザー名：<input type="text" name="user"><br>
			パスワード：<input type="password" name="pass"><br>
			<input type="submit" name="button" value="ログイン">
		</form>
		<p>Steamは初めてですか
			<form action="<%=request.getContextPath()%>/sign_up.jsp" method="POST">
				<input type="submit" name="button" value="アカウント作成">
			</form>
		</p>
		<%@ include file="footer.jsp" %>
	</body>
</html>