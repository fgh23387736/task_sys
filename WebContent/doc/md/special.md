- <a name="register">注册</a>
		
		POST /task_sys/actions/user_register.action
		to{
			username(String):#登录名，
			password(String):#密码MD5一级加密，
			rePassword(String):#重复密码MD5一级加密
		}
		#状态码为201时表示增加成功 并返回下列信息
		return {
			userId:#用户Id
		}
		#修改失败时（状态码非201）并返回下列信息

		return {
			error:#出错原因
		}
		
		401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
		422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。

- <a name="userLogin">普通用户登陆</a>

		POST /task_sys/actions/user_login.action
		to{
			userName(String):#用户名，
			password(String):#密码MD5一级加密
		}
		#状态码为201时表示登陆成功 并返回下列信息
		return {
			userId:#人员Id
		}
		#修改失败时（状态码非201）并返回下列信息

		return {
			error:#出错原因
		}
		
		401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
		422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
	
- <a name="userSignOut">普通用户登出</a>

		POST /task_sys/actions/user_signOut.action
		to{			
		}
		#状态码为201时表示登出成功
		return {
		}
		#修改失败时（状态码非201）并返回下列信息
		return {
			error:#出错原因
		}
		
		401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
		422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。

- <a name="adminLogin">管理员登陆</a>

		POST /task_sys/actions/admin_login.action
		to{
			userName(String):#用户名，
			password(String):#密码MD5一级加密
		}
		#状态码为201时表示登陆成功 并返回下列信息
		return {
			adminId:#Id
		}
		#修改失败时（状态码非201）并返回下列信息

		return {
			error:#出错原因
		}
		
		401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
		422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
	
- <a name="adminSignOut">管理员登出</a>

		POST /task_sys/actions/admin_signOut.action
		to{			
		}
		#状态码为201时表示登出成功
		return {
		}
		#修改失败时（状态码非201）并返回下列信息
		return {
			error:#出错原因
		}
		
		401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
		422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
	
	
- <a name="getMenu">获取web端菜单</a>
	
		POST /task_sys/actions/special_getMenu.action
		to:{
		}
		
		#正确返回时状态码为200
		return{
			"data": [
		        {
		            "menuId": "1", 
		            "menuName": "总目录", 
		            "menuIcon": "fa-cog", 
		            "menuHref": "", 
		            "parentMenuId": "0"
		        }, 
		        {
		            "menuId": "home", 
		            "menuName": "首页", 
		            "menuIcon": "&#xe68e;", 
		            "menuHref": "views/home/index.html", 
		            "parentMenuId": "1"
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
	

- <a name="adminGetWebInfo">管理员用获取站点数据接口</a>
	
		POST /task_sys/actions/special_adminGetWebInfo.action
		to:{
		}
		
		#正确返回时状态码为200
		return{
			userNumber:,#用户数量
			taskNumber:,#任务数量
			systemMoney:,#账户余额
			suspenedUserNumber:,#封停用户数量
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
	
- <a name="agreeCancelApply">同意取消申请</a>
	
		POST /task_sys/actions/cancelApply_agree.action
		to:{
			cancelApplyId:,#申请Id
			dealReason(String):,#处理原因
		}
		
		#正确返回时状态码为201
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
			410 Gone -[GET]：用户请求的资源被永久删除，且不会再得到的。
			500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。

- <a name="agreeCancelApply">拒绝取消申请</a>
	
		POST /task_sys/actions/cancelApply_refuse.action
		to:{
			cancelApplyId:,#申请Id
			dealReason(String):,#处理原因
		}
		
		#正确返回时状态码为201
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
			410 Gone -[GET]：用户请求的资源被永久删除，且不会再得到的。
			500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。

- <a name="receiveTask">接单</a>

		POST /task_sys/actions/task_receiveTask.action
		to{
			taskId:,#任务Id
		}
		#状态码为201时表示登陆成功 并返回下列信息
		return {
		}
		#修改失败时（状态码非201）并返回下列信息

		return {
			error:#出错原因
		}
		
		401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
		422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。

- <a name="confirmTask">确认订单完成</a>

		POST /task_sys/actions/task_confirmTask.action
		to{
			taskId:,#任务Id
		}
		#状态码为201时表示登陆成功 并返回下列信息
		return {
		}
		#修改失败时（状态码非201）并返回下列信息

		return {
			error:#出错原因
		}
		
		401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
		422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
		
- <a name="cancelTask">取消未确认订单</a>

		POST /task_sys/actions/task_cancelTask.action
		to{
			taskId:,#任务Id
		}
		#状态码为201时表示登陆成功 并返回下列信息
		return {
		}
		#修改失败时（状态码非201）并返回下列信息

		return {
			error:#出错原因
		}
		
		401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
		422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
	