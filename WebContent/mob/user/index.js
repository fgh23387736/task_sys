var webSocket;

function initWebSocket() {
    console.log("init");
    webSocket = new WebSocket('ws://' + window.ip + '/task_sys/websocket/web');
    webSocket.binaryType = 'arraybuffer'
    webSocket.onerror = function(event) {};

    webSocket.onclose = function(event) {
        layer.msg('您已与服务器断开即时链接，请刷新页面重连', {
            time: 20000, //20s后自动关闭
            btn: ['确定']
        });

    };

    webSocket.onopen = function(event) {};
    webSocket.onmessage = function(event) {
        console.log(event.data);
        var data = JSON.parse(event.data);
        if (data['type'] == "chatMessage") {
            window.getMessage(data['data']);
        } else {
            window.openRbBox(data);
        }

    };
}




layui.use(['layer', "layim"], function() {
    var layer = layui.layer,
        layim = layui.layim;
    window.isMin = true;
    var theChatIndex = layer.open({
        type: 2,
        title: "<span style='color:green;'><i class='fa fa-commenting'></i>我的消息</span>",
        shade: 0,
        maxmin: true,
        area: [$(window).width() + "px", $(window).height() + "px"],
        offset: "rb",
        closeBtn: 0,
        content: '/task_sys/mob/views/chat.html',
        success: function(layero, index) {
            layero.attr("minLeft", ($(window).width() - 182) + "px")
            layer.min(index);
            var iframeWin = window[layero.find('iframe')[0]['name']];
            window.chat = function(user) {

                layer.restore(index);
                window.isMin = false;
                iframeWin.chat(user);
            }
            window.getMessage = function(data) {
                iframeWin.getMessage(data);
            }
            window.initWebSocket();
        },
        min: function(dom) {
            window.isMin = true;
        },
        full: function(dom) {
            window.isMin = false;
        },
        restore: function(dom) {
            window.isMin = false;
        }
    });
    $(window).resize(function() { //当浏览器大小变化时
        console.log(window.isMin);
        if (!window.isMin) {
            layer.restore(theChatIndex);
            window.isMin = false;
        }
    });

    window.setChatNumber = function(number) {
        if (number > 0) {
            layer.title("<span style='color:green;'><i class='fa fa-commenting'></i>我的消息<span class='layui-badge' id='chatNumber'>" + number + "</span></span>", theChatIndex)
        } else {
            layer.title("<span style='color:green;'><i class='fa fa-commenting'></i>我的消息</span>", theChatIndex)
        }

    }

    window.signOut = function() {
        layer.load(2);
        $.ajax({
            url: '/task_sys/actions/user_signOut.action',
            type: 'POST',
            dataType: 'json',
            data: {},
            success: function(data) {
                layer.closeAll('loading');
                window.location = "/task_sys/mob/login/login.jsp";
            },
            error: function(data) {
                layer.closeAll('loading');
                window.location = "/task_sys/mob/login/login.jsp";
            }
        });
    }


    window.openRbBox = function(data) {

        var title = "";
        var yesFunction = function() {};
        var yesButton = ""
        var content = ""
        var area = "auto"
        if (data['type'] == 0) {
            var messageSound = document.getElementById("messageSound");
            messageSound.play();
            title = "新公告";
            yesButton = "查看";
            content =
                "<div style='padding:10px;'>" +
                "管理员发布了新公告《" + data.content.title + "》" +
                "</div>";
            yesFunction = function() {
                var theIndex = layer.open({
                    type: 2 //此处以iframe举例
                        ,
                    title: data.content.title,
                    shade: 0,
                    maxmin: true,
                    area: [$(window.top.document).width() + "px", $(window.top.document).height() + "px"],
                    offset: [ //为了演示，随机坐标
                        Math.random() * ($(window).height() - 300), Math.random() * ($(window).width() - 390)
                    ],
                    content: '/task_sys/mob/views/showNotice.html?noticeId=' + data.content.noticeId,
                });
                layer.full(theIndex);
                layer.close(index);

            };
        } else if (data['type'] == 1) {
            var messageSound = document.getElementById("messageSound");
            messageSound.play();
            title = "新私信";
            yesButton = "查看";
            content =
                "<div style='padding:10px;'>" +
                "管理员给您发送了新私信《" + data.content.title + "》" +
                "</div>";
            yesFunction = function() {
                var theIndex = layer.open({
                    type: 2 //此处以iframe举例
                        ,
                    title: data.content.title,
                    shade: 0,
                    maxmin: true,
                    area: [$(window.top.document).width() + "px", $(window.top.document).height() + "px"],
                    offset: [ //为了演示，随机坐标
                        Math.random() * ($(window).height() - 300), Math.random() * ($(window).width() - 390)
                    ],
                    content: '/task_sys/mob/views/showPrivateMessage.html?privateMessageId=' + data.content.privateMessageId,
                });
                layer.full(theIndex);
                layer.close(index);
            };
        } else if (data['type'] == 2) {
            var messageSound = document.getElementById("messageSound");
            messageSound.play();
            title = "新任务";
            yesButton = "接单";
            content = $("#task-box");
            $('#task-box-headImg').attr("src", data['content']['releaseUser']["headImg"]);
            $('#task-box-address').html(data['content']['address']);
            $('#task-box-name').html(data['content']['name']);
            $('#task-box-money').html(data['content']['price']);
            $('#task-box-content-content').html(data['content']['content']);
            yesFunction = function() {
                layer.load(2);
                $.ajax({
                    url: '/task_sys/actions/task_receiveTask.action',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        taskId: data['content']['taskId']
                    },
                    success: function(data) {
                        layer.closeAll('loading');
                        layer.msg("抢单成功", { icon: 6 });
                        $('[menuid="receive-notConfirmTask"]').click();
                        layer.close(index);
                    },
                    error: function(data) {
                        layer.closeAll('loading');
                        if (data.responseJSON != undefined) {
                            layer.msg(data.responseJSON.error, { icon: 5 });
                        } else {
                            layer.msg("系统错误", { icon: 5 });
                        }
                    }
                });
            };
        }

        var index = layer.open({
            type: 1,
            offset: "rt", //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            //id: 'layerDemo' + type, //防止重复弹出
            content: content,
            time: 200000, //20s后自动关闭
            btn: yesButton,
            area: area,
            title: title,
            btnAlign: 'c', //按钮居中
            shade: 0, //不显示遮罩
            yes: yesFunction
        });
    };
    //    var testdata = '{"type":2,"content":{"address":"哈工啊十公寓","price":1,"name":"1","releaseUser":{"badEnvaluateNumber":0,"headImg":"/task_sys/assets/images/100.jpg","middleEnvaluateNumber":0,"name":"无名氏1","goodEnvaluateNumber":0,"userId":2},"taskId":14,"content":"这是很长的一段介绍你信不信"}}';
    //    window.openRbBox(JSON.parse(testdata));
});