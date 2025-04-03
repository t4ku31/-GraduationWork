<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>購入完了 - Steam風ゲーム販売システム</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jsp/css/HF.css">
</head>
<body>
    <jsp:include page="header.jsp" />

    <div class="container">
        <h2>ご購入ありがとうございました</h2>
        <p>ご注文は正常に完了しました。</p>
        <%
            // OrdersDAO により自動採番された purchase_id が渡されている場合、表示します
            Integer purchaseId = (Integer) request.getAttribute("purchase_id");
            if (purchaseId != null) {
        %>
            <p>注文番号: <strong><%= purchaseId %></strong></p>
        <%
            }
        %>
        <p>今後のご利用を心よりお待ちしております。</p>
        <a href="${pageContext.request.contextPath}/jsp/top.jsp" class="button">トップページへ戻る</a>
    </div>

    <jsp:include page="footer.jsp" />
</body>
</html>
