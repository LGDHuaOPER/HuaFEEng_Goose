<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- 为移动设备添加 viewport -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
<title>Eoulu-冲锋鹅系统登录页面</title>
<link rel="shortcut icon" href="./image/eoulu.ico" />
<link rel="bookmark" href="./image/eoulu.ico" />
<link rel="stylesheet" type="text/css" href="./font-awesome-4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="./css/login.css">
</head>

<body onload="init()">
	<div class="container">
		<div class="flex-container">
			<div class="container-l">
				<!-- <img src="./image/left-pic.png" alt="signIn-logo"> -->
			</div>
			<div class="container-r">
				<div class="menu">
					<form action="Login" method="post">
						<ul>
							<li>
								<div class="h33">
									<span><i class="icon icon-uname"></i></span><input type="text"
										name="user_name" autofocus="autofocus" id="username"
										value="@eoulu.com" placeholder="请输入用户名" />
								</div>
							</li>
							<li>
								<div class="h33">
									<span><i class="icon icon-psd"></i></span>
									<input type="password" name="user_pwd" id="psd" value=""
										placeholder="请输入密码"  class="psd"/>
									<span class="passeye" style="margin-left:1%"></span>
								</div>
							</li>
						</ul>
						<div class="check7">
							<span class="checkspan"><input type="checkbox" checked="checked" id="check7"><label for="check7"></label></span><span class="checktxt">Rember Me（7 Days）</span><span title="上一版本号：v2.0.0.180914" style="margin-left: 2px;cursor: pointer;">v2.1.0.180918</span>
						</div>
						<div class="sub-res">
							<input type="submit" value="登录" class="sub"> <input
								type="reset" value="重置" class="res">
						</div>
						<div id="display"></div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script src="./js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script>
	<script src="./js/common.js"></script>
	<script src="./js/login.js"></script>
	<script>
		function init(){
			var title = document.title;
			// alert(title);
			var titarr = title.split("");
			var first = titarr.shift();
			titarr.push(first);
			title = titarr.join("");
			document.title = title;
			setTimeout("init()", 1000);
		}
		//小眼睛
		$("span.passeye").click(function(){
	        if ($(".psd").attr("type") == "text") {
	            $(".psd").attr('type',"password") ;
	        }else{
	          $(".psd").attr('type',"text") ;
	        }   
	    });
	</script>
</body>

</html>