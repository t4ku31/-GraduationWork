<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>アカウント情報編集</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .container {
            width: 70%;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            margin-top: 30px;
        }
        h2 {
            color: #333;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            font-size: 14px;
            color: #333;
        }
        input[type="text"], input[type="email"], input[type="file"] {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            font-size: 16px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 15px 32px;
            font-size: 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        .avatar-preview {
            width: 100px;
            height: 100px;
            object-fit: cover;
            border-radius: 50%;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="container">
        <h2>アカウント情報編集</h2>

        <!-- アカウント編集フォーム -->
        <form action="<%=request.getContextPath()%>/editAccount" method="POST" enctype="multipart/form-data">
            <!-- ユーザー名 -->
            <div class="form-group">
                <label for="username">ユーザー名:</label>
                <input type="text" id="username" name="username" placeholder="新しいユーザー名" required>
            </div>

            <!-- アイコン画像 -->
            <div class="form-group">
                <label for="avatar">アイコン画像:</label>
                <input type="file" id="avatar" name="avatar" accept="image/*" onchange="previewAvatar(event)">
                <!-- プレビュー表示 -->
                <img id="avatarPreview" class="avatar-preview" src="#" alt="アイコン画像プレビュー" style="display:none;">
            </div>

            <!-- メールアドレス -->
            <div class="form-group">
                <label for="email">メールアドレス:</label>
                <input type="email" id="email" name="email" placeholder="新しいメールアドレス" required>
            </div>

            <!-- 送信ボタン -->
            <div class="form-group">
                <input type="submit" value="変更を保存">
            </div>
        </form>
    </div>

    <%@ include file="footer.jsp" %>

    <script>
        // アイコン画像のプレビュー表示
        function previewAvatar(event) {
            var preview = document.getElementById('avatarPreview');
            var file = event.target.files[0];
            var reader = new FileReader();

            reader.onload = function() {
                preview.style.display = 'block';
                preview.src = reader.result;
            }

            if (file) {
                reader.readAsDataURL(file);
            }
        }
    </script>
</body>
</html>
