1.  <a name='task'></a>**任务表[增](#task_add)、[删](#task_delete)、[改](#task_change)、[查](#task_search)**
	- <a name="task_add">增</a>

			POST /task_sys/actions/task_add.action
			to{
				price(Double):#报酬，
				content(String):#任务描述，
				name(String):#名称，
				address(String):#地址，
			}
			#状态码为201时表示增加成功 并返回下列信息
			return {
				taskId:#项目Id
			}
			#修改失败时（状态码非201）并返回下列信息
			return {
				error:#出错原因
			}
			
			401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
			422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
	- <a name="task_delete">删</a>

			POST /task_sys/actions/task_deleteByIds.action
			#只有高级别管理员和用户本身在任务未被接受时有权限进行本操作，若不是高级别管理员中断程序并返回401
			to:{
				ids："id1+id2+id3+..." #要删除的id
			}
			
			
		
			正确返回时状态码为204
			return{
			}
			
			错误时除返回码还要返回错误信息
			return{
				error:'错误信息'
			}
		
			错误码：
				401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
				403 Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。
				404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
				406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）
				500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。
	- <a name="task_change">改</a>

			#不提供修改接口
	- <a name="task_search">查</a>  
			
			#根据Id搜索
			POST /task_sys/actions/task_getByIds.action
			to:{
				keys:'Id+Title+Price...',#需要获取的属性名，每个属性之间用'+'隔开
				page:1,#当前页数，（可选，Page和PageSize必须同时存在）
				pageSize：10，#每页数据条数（可选，Page和PageSize必须同时存在）
				ids:'1+2+3+...',#搜索记录Id
			}
			
			#根据创建用户搜索
			POST /task_sys/actions/task_getByUserAndName.action
			to:{
				keys:'Id+Title+Price...',#需要获取的属性名，每个属性之间用'+'隔开
				page:1,#当前页数，（可选，Page和PageSize必须同时存在）
				pageSize：10，#每页数据条数（可选，Page和PageSize必须同时存在）
				user(int):,#所属用户Id
				name(String):#项目名称
			}

			#根据加入用户搜索
			POST /task_sys/actions/task_getByJoinUser.action
			to:{
				keys:'Id+Title+Price...',#需要获取的属性名，每个属性之间用'+'隔开
				page:1,#当前页数，（可选，Page和PageSize必须同时存在）
				pageSize：10，#每页数据条数（可选，Page和PageSize必须同时存在）
				user(int):,#用户Id
			}
				
			#根据状态和发布接受用户搜索
			POST /task_sys/actions/task_getByReleaseUserAndReceiveUserAndState.action
			to:{
				keys:'Id+Title+Price...',#需要获取的属性名，每个属性之间用'+'隔开
				page:1,#当前页数，（可选，Page和PageSize必须同时存在）
				pageSize：10，#每页数据条数（可选，Page和PageSize必须同时存在）
				releaseUser(int):,#发布用户Id
				receiveUser(int):,#接受用户Id
				state(int):,#状态若为null或-1则表示所有
			}			

			#若Keys为空则表示搜索下方全部字段(signCode除外)
			Keys∈{
				taskId(int):#项目id
				price(Double):#报酬，
				content(String):#任务描述，
				name(String):#名称，
				address(String):#地址，
				releaseUser(json):{
					userId:,#用户id
					name:,#用户姓名
					phone:,#用户电话
				}#任务发布人，
				receiveUser(json):{
					userId:,#用户id
					name:,#用户姓名
					phone:,#用户电话
				}#任务接受人，
				state(int):,#状态{
					0:未接受
					1:已接受
					2:已完成
					3:已取消
				},
				releaseIsConfirm(boolean):,#发送方是否确认{
					1:true
					0|null:false
				}
				receiveIsConfirm(boolean):,#接收方是否确认{
					1:true
					0|null:false
				}
				releaseIsEnvaluate(boolean):,#发送方是否评论{
					1:true
					0|null:false
				}
				receiveIsEnvaluate(boolean):,#接收方是否评论{
					1:true
					0|null:false
				}
				releaseConfirmTime(String):,#发布者确认时间
				receiveConfirmTime(String):,#接受者确认时间
				releaseTime(String):,#发布时间
				receiveTime(String):,#接受时间
			}

			#正确返回时状态码为200
			return{
				total：10,#未分页时搜索到总数据条数，当Page和PageSize不存在时就是resultList的长度
				resultList[
					{
						Id:,
						...
					},
					{
						Id:,
						...
					},
					...
				]

			}

			错误时除返回码还要返回错误信息
			return{
				error:'错误信息'
			}

			错误码：
				401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
				403 Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。
				404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
				406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）。
				410 Gone -[GET]：用户请求的资源被永久删除，且不会再得到的。
				500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。
