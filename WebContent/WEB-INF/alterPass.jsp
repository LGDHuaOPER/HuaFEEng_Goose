<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<link rel="shortcut icon" href="image/eoulu.ico" />
<link rel="bookmark" href="image/eoulu.ico" />
<title>修改密码</title>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/libs/bootstrap.min.css"
	type="text/css">
<link rel="stylesheet" href="./css/libs/animate.min.css" type="text/css">
<link rel="stylesheet" href="./css/alterPass.css" type="text/css">
<script src="./js/libs/jquery-3.3.1.min.js" defer></script>
<script src="./js/msgbox.js" defer></script>
<script src="./js/alterPass.js" defer></script>
</head>
<body>
	<%@include file="top.jsp"%>
	<input type="hidden" class="model" value="${model}">
	<input type="hidden" class="isOpen" value="${sign}">
	<input type="hidden" class="message" value="${message}">
	<input type="hidden" name="user_id" value="${userID}">
	<input type="hidden" name="user_name" value="${user}">
	<div class="container-fluid">
		<div class="section">
			<div class="m-width">
				<div class="m-tip">
					<h1 class="fade">友情提示：</h1>
					<p class="fade">请定期修改您的密码，以确保账户安全性</p>
					<img class="alter-bg" src="./image/alterPass/alter-bg.png"
						alt="alter-bg" width="549" height="389">
				</div>
				<div class="m-alter">
					<form>
						<div class="u-pass-1">
							<span>原始密码：</span><input type="password" placeholder="请输入原密码"
								name="user_pwd" id="user_pwd" required="required">
						</div>
						<div class="u-pass-2">
							<span>新的密码：</span><input type="password" placeholder="请输入新密码"
								name="new_pwd" id="new_pwd" required="required">
						</div>
						<div class="u-pass-3">
							<span>确认密码：</span><input type="password" placeholder="请再次确认密码"
								name="pwd" id="pwd" required="required">
						</div>
						<div class="u-pass-4">
							<span><input id="finishBtn" class="btn btn-default"
								type="submit" value="提交"></span> <span><input
								class="btn btn-default" type="reset" value="重置"></span>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!--<ul class="bg-img">-->
		<!--<li></li>-->
		<!--<li></li>-->
		<!--<li></li>-->
		<!--<li></li>-->
		<!--</ul>-->
		<div id="eoulu-copy" style="position:absolute;bottom:0;width:calc(100% - 4px);height:33px;font-size:12px;color:#888;line-height:35px;z-index: 2;"><div style="width:100%;text-align:center;display:inline-block;">Copyright  ©&nbsp;<span class="YEAR">2018</span>&nbsp<a href="http://www.eoulu.com/h-col-268.html" class="EHref" target="_blank" style="color:blue;">Eoulu</a> Tech. Co.,Ltd.</div></div>
	</div>
</body>
</html>
