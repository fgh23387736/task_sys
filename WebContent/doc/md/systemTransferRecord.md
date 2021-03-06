1.  <a name='systemTransferRecord'></a>**系统转账记录表[增](#systemTransferRecord_add)、[删](#systemTransferRecord_delete)、[改](#systemTransferRecord_change)、[查](#systemTransferRecord_search)**
	- <a name="systemTransferRecord_add">增</a>
			
			#此接口用于演示使用
			POST /task_sys/actions/systemTransferRecord_add.action
			to{
				type(Integer):,#交易类型{
					0:充值，
					1:提现
				}
				money(Double):,#金额
			}
			#状态码为201时表示增加成功 并返回下列信息
			return {
				systemTransferRecordId:#Id
			}
			#修改失败时（状态码非201）并返回下列信息

			return {
				error:#出错原因
			}
			
			401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
			422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
	- <a name="systemTransferRecord_delete">删</a>

			#不提供删除接口
	- <a name="systemTransferRecord_change">改</a>

			#不提供修改接口
	- <a name="systemTransferRecord_search">查</a>
	
			#根据Id搜索
			POST /task_sys/actions/systemTransferRecord_getByIds.action
			to:{
				keys:'Id+Title+Price...',#需要获取的属性名，每个属性之间用'+'隔开
				page:1,#当前页数，（可选，Page和PageSize必须同时存在）
				pageSize：10，#每页数据条数（可选，Page和PageSize必须同时存在）
				ids:'1+2+3+...',#搜索记录Id
			}
			
			#根据用户
			POST /task_sys/actions/systemTransferRecord_getByUser.action
			to:{
				keys:'Id+Title+Price...',#需要获取的属性名，每个属性之间用'+'隔开
				page:1,#当前页数，（可选，Page和PageSize必须同时存在）
				pageSize：10，#每页数据条数（可选，Page和PageSize必须同时存在）
				user:#所属用户
			}			

			#若Keys为空则表示搜索下方全部字段
			Keys∈{
				systemTransferRecordId(String):#交易id
				transactionUser(json):{
					userId:,#用户id
					userName:,#用户名
					name:,#名称
				},#交易方
				type(Integer):,#交易类型{
					0:充值，
					1:提现
				}
				money(Double):,#金额
				time(Date):,#交易时间
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
