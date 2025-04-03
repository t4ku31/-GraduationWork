<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, bean.Payment_cardsBean" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>支払い方法選択</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/HF.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/card.css">
  <script>
    // --------------------------
    // 1) 既存/新規 切り替え
    // --------------------------
    function toggleForms(option) {
      document.getElementById('existingCardForm').style.display = (option === 'existing') ? 'block' : 'none';
      document.getElementById('newCardForm').style.display = (option === 'new') ? 'block' : 'none';
    }
    window.onload = function() {
      let selectedOption = localStorage.getItem('selectedCardOption') || 'existing';
      document.getElementById(selectedOption + 'Option').checked = true;
      toggleForms(selectedOption);
      validateNewCardForm(); // ページロード時に一度チェック
    };
    function saveSelection(option) {
      localStorage.setItem('selectedCardOption', option);
      toggleForms(option);
    }

    // --------------------------
    // 2) 既存カード情報
    // --------------------------
    function updateCardInfo() {
      const select = document.getElementById('existingCardSelect');
      const idx = select.selectedIndex;
      if (idx < 1) {
        document.getElementById('existingCardType').value = '';
        document.getElementById('existingCardNumber').value = '';
        return;
      }
      const opt = select.options[idx];
      document.getElementById('existingCardType').value = opt.getAttribute('data-cardType');
      document.getElementById('existingCardNumber').value = opt.getAttribute('data-cardNumber');
    }

    // --------------------------
    // 3) 新規カード入力補助
    // --------------------------

    // カード番号：入力中に4桁ごとに '-' を挿入
    function formatCardNumber() {
      let input = document.getElementById('newCardNumber');
      let digits = input.value.replace(/\D/g, '');
      let formatted = digits.match(/.{1,4}/g);
      if (formatted) {
        input.value = formatted.join('-');
      } else {
        input.value = '';
      }
      validateNewCardForm();
    }

    // 有効期限：2桁入力で自動的に '/'
    function formatExpiry() {
      let input = document.getElementById('newCardExpiry');
      let value = input.value;
      if (value.length === 2 && value.indexOf('/') === -1) {
        input.value = value + '/';
      }
      validateNewCardForm();
    }

    // フォーム送信時にカード番号の '-' を除去
    function removeHyphens() {
      let input = document.getElementById('newCardNumber');
      input.value = input.value.replace(/-/g, '');
    }

    // --------------------------
    // 4) バリデーション
    // --------------------------
    function validateNewCardForm() {
      const cardType = document.getElementById('newCardType').value;
      const cardNumber = document.getElementById('newCardNumber').value.replace(/-/g, '');
      const expiry = document.getElementById('newCardExpiry').value;
      const cardHolder = document.getElementById('newCardHolder').value;
      const securityCode = document.getElementById('newSecurityCode').value;

      // 必須チェック
      if (!cardType || !cardNumber || !expiry || !cardHolder || !securityCode) {
        document.getElementById('newCardSubmitBtn').disabled = true;
        return;
      }
      // カード番号16桁
      if (!/^\d{16}$/.test(cardNumber)) {
        document.getElementById('newCardSubmitBtn').disabled = true;
        return;
      }
      // 有効期限 MM/YY
      if (!/^\d{2}\/\d{2}$/.test(expiry)) {
        document.getElementById('newCardSubmitBtn').disabled = true;
        return;
      }
      // セキュリティコード3桁
      if (!/^\d{3}$/.test(securityCode)) {
        document.getElementById('newCardSubmitBtn').disabled = true;
        return;
      }
      // すべてOKならボタン有効
      document.getElementById('newCardSubmitBtn').disabled = false;
    }
  </script>
</head>
<body>
  <%@ include file="header.jsp" %>
  <div class="card-container">
    <h1 class="card-title">支払い方法選択</h1>

    <% if(request.getAttribute("message") != null) { %>
      <div class="error-message"><%= request.getAttribute("message") %></div>
    <% } %>

    <!-- ラジオボタン：登録済みor新規 -->
    <div>
      <label>
        <input type="radio" id="existingOption" name="cardOption" value="existing"
               onclick="saveSelection('existing')"> 登録済みのカードを使用
      </label>
      <label>
        <input type="radio" id="newOption" name="cardOption" value="new"
               onclick="saveSelection('new')">新規カードを登録
      </label>
    </div>

    <!-- 登録済みカード選択フォーム -->
    <div id="existingCardForm" style="display:none;">
      <form action="UserDispReceipt.action?referrer=card.jsp" method="post">
        <select id="existingCardSelect" name="selectedCardId" required onchange="updateCardInfo()">
          <option value="">カードを選択してください</option>
          <%
            List<Payment_cardsBean> cardList = (List<Payment_cardsBean>) request.getAttribute("cardList");
            if (cardList != null) {
              for (Payment_cardsBean card : cardList) {
                String originalNumber = card.getCard_number();
                String cardType = card.getCard_type();
                // 表示用は4桁区切り
                String displayedNumber = (originalNumber != null) 
                       ? originalNumber.replaceAll("(\\d{4})(?=\\d)", "$1-") : "";
                // 下4桁
                String last4 = (originalNumber != null && originalNumber.length() >= 4)
                               ? originalNumber.substring(originalNumber.length() - 4)
                               : "****";
          %>
          <option value="<%= card.getCard_id() %>"
                  data-cardType="<%= cardType %>"
                  data-cardNumber="<%= originalNumber %>">
            <%= cardType %> (**** **** **** <%= last4 %>)
          </option>
          <%  } } %>
        </select>

        <!-- 隠しフィールドでカードタイプ/番号を次ページへ送信 -->
        <input type="hidden" id="existingCardType" name="existingCardType" value="">
        <input type="hidden" id="existingCardNumber" name="existingCardNumber" value="">

        <button type="submit" class="submit-button">このカードを使用</button>
      </form>
    </div>

    <!-- 新規カード登録フォーム -->
    <div id="newCardForm" style="display:none;">
      <form action="UserCreditcardAdd.action" method="post" onsubmit="removeHyphens()">
        <div class="form-group">
          <label>カード種類</label>
          <select id="newCardType" name="cardType" required oninput="validateNewCardForm()">
            <option value="">選択してください</option>
            <option value="visa">VISA</option>
            <option value="mastercard">MasterCard</option>
            <option value="jcb">JCB</option>
          </select>
        </div>
        <div class="form-group">
          <label>カード番号</label>
          <!-- 4桁区切り: oninput呼び出し -->
          <input type="text" id="newCardNumber" name="cardNumber" maxlength="19" required
                 placeholder="xxxx-xxxx-xxxx-xxxx"
                 oninput="formatCardNumber();"
                 onblur="validateNewCardForm()">
        </div>
        <div class="form-group">
          <label>有効期限 (MM/YY)</label>
          <input type="text" id="newCardExpiry" name="expriy" maxlength="5" required
                 placeholder="MM/YY"
                 oninput="formatExpiry();"
                 onblur="validateNewCardForm()">
        </div>
        <div class="form-group">
          <label>カード名義</label>
          <input type="text" id="newCardHolder" name="cardHolder" required oninput="validateNewCardForm()">
        </div>
        <div class="form-group">
          <label>セキュリティコード</label>
          <input type="text" id="newSecurityCode" name="securityCode" maxlength="3" required
                 oninput="validateNewCardForm()">
        </div>
        <!-- バリデーションに応じて無効化されるボタン -->
        <button type="submit" id="newCardSubmitBtn" class="submit-button" disabled>カードを登録する</button>
      </form>
    </div>
  </div>

  <%@ include file="footer.jsp" %>
</body>
</html>
