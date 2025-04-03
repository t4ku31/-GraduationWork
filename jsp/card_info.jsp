<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, bean.Payment_cardsBean" %>
<%@ page import="dao.Payment_cardsDAO" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>カード情報管理</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/jsp/css/HF.css">
    <%-- ヘッダーをページの一番上に固定 --%>
    <jsp:include page="header.jsp" />
</head>
<body>
    <!-- 登録されたカード情報を取得 -->
    <%
        List<Payment_cardsBean> cardList = (List<Payment_cardsBean>) request.getAttribute("cardList");
        if(cardList != null && !cardList.isEmpty()){
            for(Payment_cardsBean card : cardList) {
    %>
                <p>カードタイプ: <%= card.getCard_type() %></p>
                <p>カード番号: <%= card.getCard_number() %></p>
                <p>有効期限: <%= card.getCard_expiry_month() %> / <%= card.getCard_expiry_year() %></p>
                <p>カード所有者: <%= card.getCard_holder_name() %></p>
                
                <form action="UserCreditcardDelete.action" method="post">
                    <input type="hidden" name="cardId" value="<%= card.getCard_id() %>">
                    <input type="submit" value="削除">
                </form>
    <%
            }
        } else {
    %>
            <p>カード情報はありません。</p>
    <%
        }
    %>

    <%-- フッターをページの一番下に固定 --%>
    <jsp:include page="footer.jsp" />
</body>
</html>
