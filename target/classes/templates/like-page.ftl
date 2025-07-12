<!doctype html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Вподобані профілі</title>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css"
          integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/style.css">
</head>
<body style="background-color: #f5f5f5;">
<div class="container mt-4">
    <h2 class="text-center mb-4">Вподобані профілі</h2>

    <#if likedUsers?size == 0>
        <p class="text-center">Поки що немає вподобаних профілів.</p>
    <#else>
        <div class="row">
            <#list likedUsers as user>
                <div class="col-md-4 mb-4">
                    <div class="card">
                        <div class="card-body text-center">
                            <img src="${user.photo}" class="rounded-circle img-fluid mb-2" alt="${user.name}" width="150">
                            <h5 class="mb-3">${user.name}</h5>

                            <a href="/messages/${user.id}" class="btn btn-primary btn-sm mb-2">
                                <i class="fas fa-comments"></i> Перейти до чату
                            </a>

                            <div class="row">
                                <div class="col-6">
                                    <form method="post" action="/liked">
                                        <input type="hidden" name="action" value="dislike">
                                        <input type="hidden" name="targetId" value="${user.id}">
                                        <button type="submit" class="btn btn-outline-danger btn-block btn-sm">
                                            <i class="fa fa-times"></i> Dislike
                                        </button>
                                    </form>
                                </div>
                                <div class="col-6">
                                    <form method="post" action="/liked">
                                        <input type="hidden" name="action" value="like">
                                        <input type="hidden" name="targetId" value="${user.id}">
                                        <button type="submit" class="btn btn-outline-success btn-block btn-sm">
                                            <i class="fa fa-heart"></i> Like
                                        </button>
                                    </form>
                                </div>
                            </div>

                            <form method="post" action="/liked" class="mt-2">
                                <input type="hidden" name="action" value="remove">
                                <input type="hidden" name="targetId" value="${user.id}">
                                <button type="submit" class="btn btn-link btn-sm text-danger">
                                    <i class="fas fa-times-circle"></i> Видалити лайк
                                </button>
                            </form>

                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </#if>
</div>
</body>
</html>
