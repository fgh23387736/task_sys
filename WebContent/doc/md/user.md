1.  <a name='user'></a>**用户[增](#user_add)、[删](#user_delete)、[改](#user_change)、[查](#user_search)**
	- <a name="user_add">增</a>

			#详见专用接口--普通用户注册
	- <a name="user_delete">删</a>

			#不提供删除接口
	- <a name="user_change">改</a>

			POST /task_sys/actions/user_updateByIds.action
			#只有管理员自身有权限进行本操作，若当前用户无权限中断程序并返回401
			to:{
				ids:'1+',#修改Id
				keys:'key1+key2+...'
				key1:
				key2:
				.....
			}
			
			keys∈{
				name(String):#名称，
				phone(String):#电话，
				suspendedDeadline(date):#账号封停截止时间
			}
		
			
			正确返回时状态码为201
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
				406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）。
				500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。
	- <a name="user_search">查</a>  

			#根据Id搜索
			POST /task_sys/actions/user_getByIds.action
			to:{
				keys:'Id+Title+Price...',#需要获取的属性名，每个属性之间用'+'隔开
				page:1,#当前页数，（可选，Page和PageSize必须同时存在）
				pageSize：10，#每页数据条数（可选，Page和PageSize必须同时存在）
				ids:'1+2+3+...',#搜索记录Id
			}

			POST /task_sys/actions/user_getByNameAndUsername.action
			to:{
				keys:'Id+Title+Price...',#需要获取的属性名，每个属性之间用'+'隔开
				page:1,#当前页数，（可选，Page和PageSize必须同时存在）
				pageSize：10，#每页数据条数（可选，Page和PageSize必须同时存在）
				name:#名称模糊搜索
				userName:#用户名模糊搜索
			}			

			#若Keys为空则表示搜索下方全部字段
			Keys∈{
				userId(int):#id
				userName(String):#用户名
				name(String):#名称，
				phone(String):#电话，
				releaseTaskNumber(Integer):#发布任务数
				receiveTaskNumber(Integer):#接受任务数
				suspendedDeadline(date):#账号封停截止时间
				money(Double):#账户余额
				goodEnvaluateNumber(Integer):#好评数
				middleEnvaluateNumber(Integer):#中评数
				badEnvaluateNumber(Integer):#差评数
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
