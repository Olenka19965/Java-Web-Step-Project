<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>User List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h3>User List</h3>
    <table class="table">
        <tbody>
        <#list users as user>
            <tr>
                <td><img src="${user.photo}" alt="Avatar" style="width:50px; border-radius:50%;"></td>
                <td>${user.name}</td>
                <td>
                    <form method="post" action="/users">
                        <input type="hidden" name="userId" value="${user.id}">
                        <button name="action" value="yes" class="btn btn-success btn-sm">Yes</button>
                        <button name="action" value="no" class="btn btn-danger btn-sm">No</button>
                    </form>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
</body>
</html>