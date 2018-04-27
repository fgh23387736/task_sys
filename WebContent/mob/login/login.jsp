<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en" class="fullscreen-bg">

<head>
	<title>便捷校园</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<jsp:include   page="/mob/link.jsp" flush="true"/>
</head>

<body>
	<!-- WRAPPER -->
	<div id="wrapper">
		<div class="vertical-align-wrap">
			<div class="vertical-align-middle">
				<div class="auth-box ">
					<div class="left">
						<div class="content">
							<div class="header">
								<div class="logo text-center"><img src="/task_sys/mob/assets/img/logo-dark.png" alt="Klorofil Logo" style="width:50%;"></div>
								<p class="lead">登陆您的账户</p>
							</div>
							<form class="form-auth-small" action="#">
								<div class="form-group">
									<label for="signin-email" class="control-label sr-only">用户名</label>
									<input type="text" class="form-control" id="Username" value="" placeholder="用户名" onblur="oBlur_1()" onfocus="oFocus_1()">
								</div>
								<div class="form-group">
									<label for="signin-password" class="control-label sr-only">Password</label>
									<input type="password" class="form-control" id="Password" value="" placeholder="Password" onblur="oBlur_2()" onfocus="oFocus_2()">
								</div>
								<div class="btn btn-primary btn-lg btn-block" onclick="submitTest()">登录</div>
								<div class="btn btn-warning btn-lg btn-block" onclick="window.location = '/task_sys/mob/register/register.jsp';">注册</div>
								
							</form>
						</div>
					</div>
					<div class="right">
						<div class="overlay"></div>
						<div class="content text">
							<h1 class="heading">让生活更加惬意</h1>
							<p>--便捷校园</p>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- END WRAPPER -->
	<jsp:include   page="/mob/script.jsp" flush="true"/>
	<script type="text/javascript" src="${pageContext.request.contextPath }/mob/login/login.js"></script>
</body>

</html>
