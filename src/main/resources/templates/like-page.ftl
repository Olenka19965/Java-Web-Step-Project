<!doctype html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Вподобані профілі</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
          crossorigin="anonymous">
    <style>
        body {
            background-color: #f5f5f5;
        }

        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            transition: transform 0.2s ease-in-out;
            background-color: #fff;
        }

        .card:hover {
            transform: translateY(-5px);
        }

        .card img {
            width: 150px;
            height: 150px;
            object-fit: cover;
            border-radius: 50%;
            border: 4px solid #ddd;
        }

        .btn-block {
            width: 100%;
        }

        .btn-outline-danger, .btn-outline-success {
            font-size: 14px;
        }

        .btn-link {
            font-size: 13px;
        }

        h2 {
            font-weight: 600;
            color: #333;
        }

        h5 {
            font-weight: 500;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center mb-4">💖 Вподобані профілі</h2>

    <#if likedUsers?size == 0>
        <p class="text-center">Поки що немає вподобаних профілів.</p>
    <#else>
        <div class="row justify-content-center">
            <#list likedUsers as user>
                <div class="col-sm-6 col-md-4 col-lg-3 mb-4">
                    <div class="card text-center p-3">
                        <img src="${user.photo}" alt="${user.name}">
                        <h5>${user.name}</h5>

                        <a href="/messages/${user.id}" class="btn btn-primary btn-sm mt-2 mb-3">
                            <i class="fas fa-comments"></i> Перейти до чату
                        </a>

                        <div class="d-flex justify-content-between">
                            <form method="post" action="/liked">
                                <input type="hidden" name="action" value="dislike">
                                <input type="hidden" name="targetId" value="${user.id}">
                                <button type="submit" class="btn btn-outline-danger btn-sm">
                                    <i class="fas fa-times"></i> Dislike
                                </button>
                            </form>

                            <form method="post" action="/liked">
                                <input type="hidden" name="action" value="like">
                                <input type="hidden" name="targetId" value="${user.id}">
                                <button type="submit" class="btn btn-outline-success btn-sm">
                                    <i class="fas fa-heart"></i> Like
                                </button>
                            </form>
                        </div>

                        <form method="post" action="/liked" class="mt-2">
                            <input type="hidden" name="action" value="remove">
                            <input type="hidden" name="targetId" value="${user.id}">
                            <button type="submit" class="btn btn-link text-danger">
                                <i class="fas fa-trash-alt"></i> Видалити лайк
                            </button>
                        </form>
                    </div>
                </div>
            </#list>
        </div>
    </#if>
</div>

</body>
</html>
