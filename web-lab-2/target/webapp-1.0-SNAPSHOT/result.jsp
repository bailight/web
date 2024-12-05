<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html lang="en">
<head>
    <title>Результат</title>
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
            <h1>Результат</h1>
            <table id="resultTable" style="height: auto; width: 300px; padding: 20px;">
                <tr>
                    <td>x :</td>
                    <td><%= request.getAttribute("x") %></td>
                </tr>
                <tr>
                    <td>y :</td>
                    <td><%= request.getAttribute("y") %></td>
                </tr>
                <tr>
                    <td>r :</td>
                    <td><%= request.getAttribute("r") %></td>
                </tr>
                <tr>
                    <td>Result :</td>
                    <td><%= request.getAttribute("check") %></td>
                </tr>
                <tr>
                    <td>CurTime :</td>
                    <td><%= request.getAttribute("CurTime") %></td>
                </tr>
                <tr>
                    <td>Execution time :</td>
                    <td><%= request.getAttribute("executionTime") %></td>
                </tr>
            </table>
            <p><a href="${pageContext.request.contextPath}/Ctrl"><button>Вернуть в главное</button></a></p>
        </div>
    </div>
</main>
</body>
</html>