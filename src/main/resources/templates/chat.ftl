<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/assets/favicon.png">

    <title>Chat</title>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css"
          integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
    <!-- Bootstrap core CSS -->
    <link href="/assets/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link rel="stylesheet" href="/assets/bootstrap.min.css">
    <link rel="stylesheet" href="/assets/style.css">
</head>
<body>

<div class="container">
    <div class="row">
        <div class="chat-main chat-main col-lg-8 offset-lg-2 col-12 ">
            <div class="col-md-12 chat-header">
                <div class="row header-one text-white p-1">
                    <div class="col-md-6 name pl-2">
                        <i class="fa fa-comment"></i>
                        <h6 class="ml-1 mb-0">${receiver.name}</h6>
                    </div>
                    <div class="col-md-6 options text-right pr-0">
                        <i class="fa fa-window-minimize hide-chat-box hover text-center pt-1"></i>
                        <p class="arrow-up mb-0">
                            <i class="fa fa-arrow-up text-center pt-1"></i>
                        </p>
                        <i class="fa fa-times hover text-center pt-1"></i>
                    </div>
                </div>
                <div class="row header-two w-100">
                    <div class="col-md-6 options-left pl-1">
                        <i class="fa fa-video-camera mr-3"></i>
                        <i class="fa fa-user-plus"></i>
                    </div>
                    <div class="col-md-6 options-right text-right pr-2">
                        <i class="fa fa-cog"></i>
                    </div>
                </div>
            </div>
            <div class="chat-content">
                <div class="col-md-12 chats pt-3 pl-2 pr-3 pb-3">
                 <ul class="p-0">
                    <#list messages as m>
                        <#if m.receiverId() == receiver.id>
                            <li class="send-msg float-right mb-2">
                                <p class="pt-1 pb-1 pl-2 pr-2 m-0 rounded">
                                    ${m.content()}
                                </p>
                                <span class="send-msg-time">${m.prettyTime()}</span>
                            </li>
                        <#else>
                            <li class="receive-msg float-left mb-2">
                                <div class="sender-img">
                                    <img src="${m.senderImg()}" class="float-left">
                                </div>
                                <div class="receive-msg-desc float-left ml-2">
                                    <p class="bg-white m-0 pt-1 pb-1 pl-2 pr-2 rounded">
                                        ${m.content()}
                                    </p>
                                    <span class="receive-msg-time">
                                        ${receiver.name}, ${m.prettyTime()}
                                    </span>
                                </div>
                            </li>
                        </#if>
                    </#list>
                 </ul>
                </div>
                <div class="col-md-12 p-2 msg-box border border-primary">
<#--                    <form action="/messages?id=${receiver.id}" method="POST"></form>-->
                    <form class="row" method="POST">
                        <div class="col-md-2 options-left">
                            <i class="fa fa-smile"></i>
                        </div>
                        <div class="col-md-7 pl-0">
                            <input type="text" name="message" class="border-0" placeholder="Send message" />
                        </div>
                        <div class="col-md-3 text-right options-right">
                            <button type="submit" class="btn btn-light">
                                <i class="fa fa-comment mr-2"></i>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
