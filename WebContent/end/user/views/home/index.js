layui.use(["layer"], function() {
    init();

    function init() {
        /*var time=getNowFormatDate();
        document.getElementById('nowDate').innerHTML=time;*/
        layer.load(2);
        $.ajax({
            url: '/task_sys/actions/user_getByIds.action',
            type: 'GET',
            dataType: 'json',
            data: {
                ids: "",
                keys: ""
            },
            success: function(data) {
                document.getElementById('ReleaseTaskNumber').innerHTML = data.resultList[0].releaseTaskNumber;
                document.getElementById('ReceiveTaskNumber').innerHTML = data.resultList[0].receiveTaskNumber;
                document.getElementById('Money').innerHTML = data.resultList[0].money;
                document.getElementById('GoodEnvaluate').innerHTML = data.resultList[0].goodEnvaluateNumber;
                document.getElementById('name').innerHTML = data.resultList[0].name;
                document.getElementById('phone').innerHTML = data.resultList[0].phone;
                $('#headImg').attr("src", data.resultList[0].headImg);
                //makeCharts(data.startProject,data.endProject);
                makeCharts(data.resultList[0]);
                layer.closeAll('loading');
            },
            error: function(data) {
                layer.closeAll('loading');
                layer.msg(JSON.parse(data.responseText).error, {
                    icon: 5
                });
            }
        });
    }

    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate +
            " " + date.getHours() + seperator2 + date.getMinutes() +
            seperator2 + date.getSeconds();
        return currentdate;
    }

    function format(format, date) {
        var o = {
            "M+": date.getMonth() + 1, //month
            "d+": date.getDate(), //day
            "h+": date.getHours(), //hour
            "m+": date.getMinutes(), //minute
            "s+": date.getSeconds(), //second
            "q+": Math.floor((date.getMonth() + 3) / 3), //quarter
            "S": date.getMilliseconds() //millisecond
        }
        if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
            (date.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(format))
                format = format.replace(RegExp.$1,
                    RegExp.$1.length == 1 ? o[k] :
                    ("00" + o[k]).substr(("" + o[k]).length));
        return format;
    }

    function makeCharts(mydata) {
    	var data = [];
        option = {
            backgroundColor: '#2c343c',

            title: {
                text: '评价',
                left: 'center',
                top: 20,
                textStyle: {
                    color: '#ccc'
                }
            },

            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },

            visualMap: {
                show: false,
                min: -10,
                max: 20,
                inRange: {
                    colorLightness: [0, 1]
                }
            },
            series: [{
                name: '访问来源',
                type: 'pie',
                radius: '55%',
                center: ['50%', '50%'],
                data: [
                    { value: mydata.goodEnvaluateNumber, name: '好评' },
                    { value: mydata.middleEnvaluateNumber, name: '中评' },
                    { value: mydata.badEnvaluateNumber, name: '差评' }
                ].sort(function(a, b) { return a.value - b.value; }),
                roseType: 'radius',
                label: {
                    normal: {
                        textStyle: {
                            color: 'rgba(255, 255, 255, 0.3)'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        lineStyle: {
                            color: 'rgba(255, 255, 255, 0.3)'
                        },
                        smooth: 0.2,
                        length: 10,
                        length2: 20
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#c23531',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },

                animationType: 'scale',
                animationEasing: 'elasticOut',
                animationDelay: function(idx) {
                    return Math.random() * 200;
                }
            }]
        };

        
        var myChart = echarts.init(document.getElementById('main'));
        myChart.setOption(option);

    }

});