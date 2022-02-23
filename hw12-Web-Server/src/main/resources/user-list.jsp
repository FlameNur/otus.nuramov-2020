<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Management Application</title>
</head>
<body>
    <!-- Устанавливаем по центру -->
    <div style="text-align: center;">
        <h1>User Management</h1>
        <h2>
            <!-- ????? Тут куча вопросов при работе со ссылками -->
            <a href="/new">Add New User</a>
            <a href="/list">List All Users</a>
        </h2>
    </div>


    <div>
        <table>
            <caption>List of Users</caption>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Age</th>
                <th>Phone</th>
                <th>Address</th>
            </tr>






            <%--<tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Country</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="user" items="${listUser}">
                <tr>
                    <td><c:out value="${user.id}" /></td>
                    <td><c:out value="${user.name}" /></td>
                    <td><c:out value="${user.email}" /></td>
                    <td><c:out value="${user.country}" /></td>
                    <td>
                        <a href="edit?id=<c:out value='${user.id}' />">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="delete?id=<c:out value='${user.id}' />">Delete</a>
                    </td>
                </tr>
            </c:forEach>--%>
        </table>
    </div>
</body>
</html>