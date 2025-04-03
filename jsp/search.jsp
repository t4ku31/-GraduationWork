<%@ page contentType="text/html; charset=UTF-8" import="bean.GameBean, java.util.List" %> 
<html> 
<head> 
    <title>ゲーム検索 - Steam風</title> 
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jsp/css/HF.css"> 
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jsp/css/search.css"> 
</head> 
<body> 
    <%@ include file="header.jsp" %> 
     
    <div class="container"> 
        <div class="search-container"> 
            <h1>ゲーム検索</h1> 
             
            <!-- タイトル検索フォーム --> 
            <form action="UserSearchTitle.action" method="get"> 
                <input type="text" name="title" id="title" placeholder="ゲームタイトルを入力" value="<%= request.getParameter("title") != null ? request.getParameter("title") : "" %>"> 
                <input type="submit" value="タイトル検索"> 
            </form> 
             
            <!-- ジャンル検索フォーム --> 
            <form action="UserSearchGenre.action" method="get"> 
                <select name="genre" id="genre"> 
                    <option value="">ジャンル選択</option> 
                    <option value="MMO" <%= "MMO".equals(request.getParameter("genre")) ? "selected" : "" %>>MMO</option> 
                    <option value="FPS" <%= "FPS".equals(request.getParameter("genre")) ? "selected" : "" %>>FPS</option> 
                    <option value="RPG" <%= "RPG".equals(request.getParameter("genre")) ? "selected" : "" %>>RPG</option> 
                    <option value="アクション" <%= "アクション".equals(request.getParameter("genre")) ? "selected" : "" %>>アクション</option> 
                    <option value="インディー" <%= "インディー".equals(request.getParameter("genre")) ? "selected" : "" %>>インディー</option> 
                    <option value="カジュアル" <%= "カジュアル".equals(request.getParameter("genre")) ? "selected" : "" %>>カジュアル</option> 
                    <option value="シュミレーション" <%= "シュミレーション".equals(request.getParameter("genre")) ? "selected" : "" %>>シミュレーション</option> 
                    <option value="ストラテジー" <%= "ストラテジー".equals(request.getParameter("genre")) ? "selected" : "" %>>ストラテジー</option> 
                    <option value="スポーツ" <%= "スポーツ".equals(request.getParameter("genre")) ? "selected" : "" %>>スポーツ</option> 
                    <option value="レース" <%= "レース".equals(request.getParameter("genre")) ? "selected" : "" %>>レース</option> 
                </select> 
                <input type="submit" value="ジャンル検索"> 
            </form> 
        </div> 
         
        <% 
        // 検索条件がない場合は全件表示
        String title = request.getParameter("title"); 
        String genre = request.getParameter("genre"); 
        boolean isTitleEmpty = (title == null || title.isEmpty()); 
        boolean isGenreEmpty = (genre == null || genre.isEmpty()); 

        if (isTitleEmpty && isGenreEmpty) { 
            if(request.getAttribute("forwarded") == null) {
                request.setAttribute("forwarded", Boolean.TRUE);
        %> 
            <jsp:forward page="UserSearchAllGame.action" /> 
        <% }} %> 

        <!-- ゲーム一覧表示部分 --> 
        <div class="game-list"> 
            <% 
                List<GameBean> gamelist = (List<GameBean>) request.getAttribute("gamelist"); 
                if (gamelist != null && !gamelist.isEmpty()) { 
                    for (GameBean game : gamelist) { 
            %> 
                <div class="game-item"> 
                    <div class="game-thumb"> 
                        <a href="UserGetGameInfo.action?game_id=<%= game.getGame_id() %>"> 
                            <% 
                                String thumbnail = "placeholder_cover.jpg"; 
                                if(game != null && game.getThumbnail_url() != null && !game.getThumbnail_url().isEmpty()){ 
                                    thumbnail = game.getThumbnail_url(); 
                                    if(!(thumbnail.endsWith(".jpg") || thumbnail.endsWith(".png") || thumbnail.endsWith(".gif"))) { 
                                        thumbnail += ".jpg"; 
                                    } 
                                } 
                            %> 
                            <img src="<%= request.getContextPath() %><%= thumbnail %>" alt="<%= game.getTitle() %>"> 
                        </a> 
                    </div> 
                     
                    <div class="game-info"> 
                        <h2 class="game-title"> 
                            <a href="UserGetGameInfo.action?game_id=<%= game.getGame_id() %>"> 
                                <%= game.getTitle() %> 
                            </a> 
                        </h2> 
                        <p class="game-release">リリース日: <%= game.getRelease_date() %></p> 
                        <p class="game-developer">開発元: <%= game.getDeveloper() %></p> 
                    </div> 
                     
                    <div class="game-price"> 
                        ¥<%= game.getPrice() %> 
                    </div> 
                </div> 
            <% 
                    } 
                } else { 
            %> 
                <p style="text-align:center;">該当するゲームが見つかりませんでした。</p> 
            <% 
                } 
            %> 
        </div> 
    </div> 
     
    <%@ include file="footer.jsp" %> 
</body> 
</html>
