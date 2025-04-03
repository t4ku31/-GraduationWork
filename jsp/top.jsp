<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Steam風レイアウト</title>
    <link rel="stylesheet" type="text/css" href="css/HF.css">
    <link rel="stylesheet" href="css/top.css">
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="store-grid">
        <div class="game-card">
            <img src="<%=request.getContextPath()%>/image/Bullet_Storm.jpg" alt="Bullet Storm">
        </div>
        <div class="game-card">
            <img src="<%=request.getContextPath()%>/image/Eternal_Legends.jpg" alt="Eternal Legends">
        </div>
        <div class="game-card">
            <img src="<%=request.getContextPath()%>/image/Legends_of_Arcanum.jpg" alt="Legends of Arcanum">
        </div>
        <div class="game-card">
            <img src="<%=request.getContextPath()%>/image/Resident_beagle.jpg" alt="Resident Beagle">
        </div>
        <div class="game-card">
            <img src="<%=request.getContextPath()%>/image/The_Fast_and_the_Spurious.jpg" alt="The Fast and the Spurious">
        </div>
         <div class="game-card">
            <img src="<%=request.getContextPath()%>/image/Strategy_for_the_earth.jpg" alt="Strategy_for_the_earth.jpg">
        </div>
    </div>

    <%@ include file="footer.jsp" %>
</body>
</html>
