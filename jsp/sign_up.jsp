<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>アカウント登録</title>
    <link rel="stylesheet" href="./css/sign_up.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jsp/css/HF.css">
</head>
<body>
	  <%@ include file="header.jsp" %>
    <div class="container">
        <h2>アカウント登録</h2>

        <!-- サーバーサイドのエラーメッセージ表示 -->
        <%
            // エラーがあれば errorMessage を表示
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
            <p style="color: red;"><%= errorMessage %></p>
        <%
            } else {
                // registrationStatus に応じたメッセージを表示
                String registrationStatus = (String) request.getAttribute("registrationStatus");
                if ("already".equals(registrationStatus)) {
        %>
                    <p style="color: red;">既に登録されているアカウントです。</p>
        <%
                } else if ("success".equals(registrationStatus)) {
        %>
                    <p style="color: green;">アカウント登録が完了しました。</p>
        <%
                }
            }
        %>

        <!-- フロントコントローラ方式の場合、action URL を適切に指定 -->
        <form id="signupForm" action="UserAccountCreate.action" method="post">
            <label for="username">ユーザー名:</label>
            <input type="text" id="username" name="username" required
                   pattern="^[a-zA-Z0-9_]{6,20}$"
                   title="ユーザー名は6～20文字の英数字またはアンダースコアのみ可">

            <label for="email">メールアドレス:</label>
            <input type="email" id="email" name="email" required>

            <label for="password">パスワード:</label>
            <!-- 初期状態: パスワードを隠す -->
            <input type="password" id="password" name="password" required
                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,16}$"
                   title="大文字・小文字・数字を含む8～16文字で入力してください。">
            <!-- 表示切り替えチェックボックス -->
            <input type="checkbox" id="showPassword">パスワードを表示

            <button type="submit">登録</button>
        </form>
    </div>

    <script>
    document.addEventListener("DOMContentLoaded", function() {
        // パスワード表示/非表示の切り替え
        const showPassword = document.getElementById("showPassword");
        const passwordField = document.getElementById("password");
        showPassword.addEventListener("change", function() {
            passwordField.type = this.checked ? "text" : "password";
        });

        // フォーム送信前のバリデーション
        document.getElementById("signupForm").addEventListener("submit", function(event) {
            let username = document.getElementById("username").value;
            let password = passwordField.value;
            let email    = document.getElementById("email").value;

            let usernameRegex = /^[a-zA-Z0-9_]{6,20}$/;
            let passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,16}$/;
            let emailRegex    = /^[A-Za-z0-9+_.-]+@(.+)$/;

            if (!usernameRegex.test(username)) {
                alert("ユーザー名は6～20文字の英数字またはアンダースコアのみ使えます。");
                event.preventDefault();
            }
            if (!passwordRegex.test(password)) {
                alert("パスワードは8～16文字で、大文字・小文字・数字を含んでください。");
                event.preventDefault();
            }
            if (!emailRegex.test(email)) {
                alert("正しいメールアドレスを入力してください。");
                event.preventDefault();
            }
        });
    });
    </script>
    <%@ include file="footer.jsp" %>
</body>
</html>
