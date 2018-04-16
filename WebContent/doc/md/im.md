- <a name="init">初始化</a>
	
		POST /task_sys/actions/im_init.action
		to:{
		}
		
		#正确返回时状态码为200
		return{
		    "code": 0,
		    "msg": "",
		    "data": {
		        "mine": {
		            "username": "纸飞机",
		            "id": "100000",
		            "status": "online",
		            "sign": "在深邃的编码世界，做一枚轻盈的纸飞机",
		            "avatar": "//res.layui.com/images/fly/avatar/00.jpg"
		        },
		        "friend": [
		            {
		                "groupname": "知名人物",
		                "id": 0,
		                "list": [
		                    {
		                        "username": "贤心",
		                        "id": "100001",
		                        "avatar": "//tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg",
		                        "sign": "这些都是测试数据，实际使用请严格按照该格式返回",
		                        "status": "online"
		                    },
		                    
		                    {
		                        "username": "徐小峥",
		                        "id": "666666",
		                        "avatar": "//tva1.sinaimg.cn/crop.0.0.512.512.180/6a4acad5jw8eqi6yaholjj20e80e8t9f.jpg",
		                        "sign": "代码在囧途，也要写到底"
		                    }
		                ]
		            },
		            {
		                "groupname": "网红声优",
		                "id": 1,
		                "list": [
		                    {
		                        "username": "罗玉凤",
		                        "id": "121286",
		                        "avatar": "//tva4.sinaimg.cn/crop.0.0.640.640.180/4a02849cjw8fc8vn18vktj20hs0hs75v.jpg",
		                        "sign": "在自己实力不济的时候，不要去相信什么媒体和记者。他们不是善良的人，有时候候他们的采访对当事人而言就是陷阱"
		                    } 
		                ]
		            }
		        ],
		        "group": [
		            {
		                "groupname": "前端群",
		                "id": "101",
		                "avatar": "//tva1.sinaimg.cn/crop.0.0.200.200.50/006q8Q6bjw8f20zsdem2mj305k05kdfw.jpg"
		            },
		            {
		                "groupname": "Fly社区官方群",
		                "id": "102",
		                "avatar": "//tva2.sinaimg.cn/crop.0.0.199.199.180/005Zseqhjw1eplix1brxxj305k05kjrf.jpg"
		            }
		        ]
		    }
		}

		错误时除返回码还要返回错误信息
		return{
			"code": 1,
		    "msg": "",#错误信息
		}
