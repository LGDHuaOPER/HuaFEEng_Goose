/*update*/

$(function(){
	$("#username").val(Cookie.getCookie('username'));
	/*username验证*/
	$("#username").on("blur", function(){
		var uname = $(this).val().trim();
		if( uname == "" || uname == null){
			$("#display").html("请输入用户名");
			$("#display").fadeIn();
		}
	});
	$("#username").on("focus", function(){
		$("#display").fadeOut();
	});

	/*password验证*/
	var  reg = /^[a-zA-Z0-9]{8,20}$/;

	/*有bug*/
	// $(".menu .checktxt").on("click", function(){
	// 	var ischeck = $("#check7").attr("checked");
	// 	console.log(ischeck);
	// 	if (ischeck) {
	// 		document.getElementById("check7").setAttribute("checked", false);
	// 	}else{
	// 		document.getElementById("check7").setAttribute("checked", true);
	// 		$("#check7").attr("checked","checked");
	// 	}
	// });
	
	/*登录按钮*/
	$(".sub").on("click",function(){
		var uname = $("#username").val().trim();
		var upass = $("#psd").val().trim();
		
		if(uname !="" && uname != null && reg.test(upass)){
			if($('#check7').is(':checked')){
				Cookie.setCookie("username",$("#username").val(), 7);
			}else{
				Cookie.removeCookie("username");
			}
		}
		if (uname == "" || uname == null) {
			$("#display").html("请输入用户名");
			$("#display").fadeIn();
			return false;
		}
		if(!reg.test(upass) && upass !="" && upass != null){
			$("#display").html("请输入8~20位数字与大小写字母混合的密码");
			$("#display").fadeIn();
			return false;
		}
		if(upass =="" || upass == null){
			$("#display").html("密码不能为空");
			$("#display").fadeIn();
			return false;
		}
		return true;
	});

	/*Reset 重置事件*/
	$(".res").on("click",function(){
		$("#display").fadeOut();
		$("#username").val("");
		$("#psd").val("");
	});

	/*password keyup事件*/
	$("#psd").keyup(function(){
		var upass = $(this).val().trim();
		if(upass =="" || upass == null){
			$("#display").html("密码不能为空");
			$("#display").fadeIn();
		}else{
			$("#display").fadeOut();
		}
	});

	/*UI交互*/
	$(".menu li").mouseenter(function(){
			$(this).children("div").addClass("unline");
			$(this).find("i").addClass("changeicon");
	});
	$(".menu li").mouseleave(function(){
			$(this).children("div").removeClass("unline");
			$(this).find("i").removeClass("changeicon");
	});

	// $(".menu li:eq(0)").mouseenter(function(){
	// 	$(".menu li:eq(0) .h33").addClass("unline");
	// 	$(".menu li:eq(0) i").addClass("changeicon");
	// });
	// $(".menu li:eq(0)").mouseleave(function(){
	// 	$(".menu li:eq(0) .h33").removeClass("unline");
	// 	$(".menu li:eq(0) i").removeClass("changeicon");
	// });
	// 	$(".menu li:eq(1) .h33").addClass("unline");
	// 	$(".menu li:eq(1) i").addClass("changeicon");
	// });
	// $(".menu li:eq(1)").mouseleave(function(){
	// 	$(".menu li:eq(1) .h33").removeClass("unline");
	// 	$(".menu li:eq(1) i").removeClass("changeicon");
	// });
});
