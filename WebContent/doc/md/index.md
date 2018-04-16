## 一些公共api

> 不采用标准RESTful

> <label id="CD" />restful 状态码
>
	200 OK - [GET]：服务器成功返回用户请求的数据，该操作是幂等的（Idempotent）。
	201 CREATED - [POST/PUT/PATCH]：用户新建或修改数据成功。
	202 Accepted - [*]：表示一个请求已经进入后台排队（异步任务）
	204 NO CONTENT - [DELETE]：用户删除数据成功。
	400 INVALID REQUEST - [POST/PUT/PATCH]：用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的。
	401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
	403 Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。
	404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
	406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）。
	410 Gone -[GET]：用户请求的资源被永久删除，且不会再得到的。
	422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
	500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。



#通用接口

1. [用户](user.html)<完成接口>
1. [管理员](admin.html)<完成接口>
2. [任务](task.html)<完成接口>
3. [取消申请](cancelApply.html)<完成接口>
4. [系统转账记录](systemTransferRecord.html)<完成接口>
5. [内部转账记录](internalTransferRecord.html)<完成接口>
6. [评价表](envaluate.html)<完成接口>
7. [聊天记录](chatRecord.html)<完成文档>
8. [私信](privateMessage.html)<完成接口>
9. [公告](notice.html)<完成接口>
10. [封停记录](suspendedRecord.html)<完成文档>

#专用接口
1. [注册](special.html#register)<完成接口>
1. [普通用户登陆](special.html#userLogin)<完成接口>
2. [普通用户登出](special.html#userSignOut)<完成接口>
1. [管理员登陆](special.html#adminLogin)<完成接口>
2. [管理员用户登出](special.html#adminSignOut)<完成接口>
3. [获取web端菜单](special.html#getMenu)<完成接口>
4. [管理员用获取站点数据接口](special.html#adminGetWebInfo)<完成接口>
5. [同意取消申请](special.html#agreeCancelApply)<完成接口>
6. [拒绝取消申请](special.html#refuseCancelApply)<完成接口>
7. [接单](special.html#receiveTask)<完成接口>
8. [确认订单完成](special.html#confirmTask)<完成接口>
9. [取消未接受订单](special.html#cancelTask)<完成接口>

#聊天接口
1. [初始化](im.html#init)<完成接口>

