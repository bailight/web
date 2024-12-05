<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ошибка</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<header>
    <div id="student-info" class="container">
        <h1>Чэнь Жохань P3222 413107</h1>
    </div>
</header>
<main>
    <div class="container">
        <div id="info" class="card">
            <h1>Ошибка</h1>
            <p><%= request.getAttribute("error") %></p>
            <p><a href="${pageContext.request.contextPath}/Ctrl"><button>Вернуть в главное</button></a></p>
        </div>
    </div>
</main>
</body>
</html>
