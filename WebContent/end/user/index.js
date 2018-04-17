var webSocket = new WebSocket('ws://localhost:8080/task_sys/websocket/web');
webSocket.binaryType = 'arraybuffer'
webSocket.onerror = function(event) {};

webSocket.onclose = function(event) {
	layer.msg('您已与服务器断开即时链接，请刷新页面重连', {
        time: 20000, //20s后自动关闭
        btn: ['确定']
      });
    
};

webSocket.onopen = function(event) {
    console.log("链接关闭")
};
webSocket.onmessage = function(event) {
    var data = JSON.parse(event.data);
    if (data['type'] == "chatMessage") {
        window.getMessage(data['data']);
    } else {
        window.openRbBox(data);
    }

};

layui.use(['layer', "layim"], function() {
    var layer = layui.layer,
        layim = layui.layim;

    layim.config({
        brief: false,
        init: {
            url: '/task_sys/actions/im_init.action' //接口地址（返回的数据格式见下文）
                ,
            type: 'get' //默认get，一般可不填
                ,
            data: {} //额外参数
        },
        title: "我的消息",
        isgroup: false
    });
    layim.on('sendMessage', function(res) {
        var mine = res.mine; //包含我发送的消息及我的信息
        var to = res.to; //对方的信息
        if (webSocket.readyState === webSocket.CLOSED) {
            layer.msg("您已掉线，请刷新界面", { icon: 5 });
        } else {
            webSocket.send(JSON.stringify({
                type: 'chatMessage' //随便定义，用于在服务端区分消息类型
                    ,
                data: res
            }));
        }


    });
    window.chat = function(user) {
        layim.chat({
            name: user.name,
            type: 'friend',
            avatar: user.headImg,
            id: user.userId
        });
    }
    window.getMessage = function(data) {
        layim.getMessage(data);
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
                window.location = "/task_sys/end/login/login.jsp";
            },
            error: function(data) {
                layer.closeAll('loading');
                window.location = "/task_sys/end/login/login.jsp";
            }
        });
    }


    window.openRbBox = function(data) {
        console.log(data);
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
                layer.open({
                    type: 2 //此处以iframe举例
                        ,
                    title: data.content.title,
                    area: ['700px', '400px'],
                    shade: 0,
                    maxmin: true,
                    offset: [ //为了演示，随机坐标
                        Math.random() * ($(window).height() - 300), Math.random() * ($(window).width() - 390)
                    ],
                    content: '/task_sys/end/views/showNotice.html?noticeId=' + data.content.noticeId,
                    zIndex: layer.zIndex //重点1
                        ,
                    success: function(layero) {
                        layer.setTop(layero); //重点2
                    }
                });
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
                layer.open({
                    type: 2 //此处以iframe举例
                        ,
                    title: data.content.title,
                    area: ['700px', '400px'],
                    shade: 0,
                    maxmin: true,
                    offset: [ //为了演示，随机坐标
                        Math.random() * ($(window).height() - 300), Math.random() * ($(window).width() - 390)
                    ],
                    content: '/task_sys/end/views/showPrivateMessage.html?privateMessageId=' + data.content.privateMessageId,
                    zIndex: layer.zIndex //重点1
                        ,
                    success: function(layero) {
                        layer.setTop(layero); //重点2
                    }
                });
            };
        } else if (data['type'] == 2) {
            var messageSound = document.getElementById("messageSound");
            messageSound.play();
            title = "新任务";
            yesButton = "接单";
            content = $("#task-box");
            area = ["420px", "310px"]
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
            time: 20000, //20s后自动关闭
            btn: yesButton,
            area: area,
            title: title,
            btnAlign: 'c', //按钮居中
            shade: 0, //不显示遮罩
            yes: yesFunction
        });
    };
});