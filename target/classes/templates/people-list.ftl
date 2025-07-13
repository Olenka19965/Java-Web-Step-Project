<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Next User</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: #f9f9f9;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }
        img {
            max-width: 400px;
            width: 100%;
            height: auto;
            border-radius: 30px;
            box-shadow: 0 12px 30px rgba(0,0,0,0.3);
            margin-bottom: 30px;
        }
        h2 {
            margin-bottom: 20px;
            font-size: 32px;
        }
        .btn-group {
            display: flex;
            gap: 30px;
        }
        .btn {
            width: 140px;
            height: 60px;
            font-size: 20px;
        }
    </style>
</head>
<body>
<#if user??>
    <img src="${user.photo}" alt="User Photo">
    <h2>${user.name}</h2>
    <form method="post" action="/users" class="btn-group">
        <input type="hidden" name="userId" value="${user.id}">
        <button name="action" value="yes" class="btn btn-success">❤️ Yes</button>
        <button name="action" value="no" class="btn btn-danger">❌ No</button>
    </form>
<#else>
    <h3>No users available</h3>
</#if>
</body>
</html>
