//随机展现背景
$(function() {
//    var length = 4;
//    $(".bg-img li:nth-child(3)").show();
//    setInterval(function() {
//        var randomBgIndex = Math.floor(Math.random() * length);
//        $(".container-fluid .bg-img li").eq(randomBgIndex).addClass("show").siblings().removeClass("show");
//    }, 4000);
    // 绑定过渡效果的类
    $("h1.fade").addClass("fadesin1");
//    $("h2.fade").addClass("fadesin2");
    $("p.fade").addClass("fadesin2");
    $(".m-alter").addClass("fadesin4");
    $(".m-alter input").focus(function () {
        $(this).siblings("span").addClass("animated bounceIn");
    });
    
  //密码框
    var  reg =/^[a-zA-Z0-9]{8,20}$/;
    function backpage(){
    	window.history.back(-1);
    } 
    //
    $("#finishBtn").on("click",function(){
    	var user_pwd = $("input[name='user_pwd']").val().trim();
    	if(!reg.test(user_pwd)){
       	    $.MsgBox_Unload.Alert("提示", "原密码为8~20位数字与大小写字母混合的密码！");
       	 return false;
    	}else if($("input[name='pwd']").val().trim() != $("input[name='new_pwd']").val().trim()){
    	   	 $.MsgBox_Unload.Alert("提示", "两次输入新密码不一致！");
    	   	 return false;
    	}else if(!reg.test($("input[name='new_pwd']").val().trim())){
    	   	 $.MsgBox_Unload.Alert("提示", "新密码为8~20位数字与大小写字母混合的密码！");
    	   	 return false;
    	}else{
    		$.ajax({
    	        type: 'get',
    	        url: 'ModifyUserPassword',
    	        data: {
    	            user_id: $("input[name='user_id']").val().trim(),
    	            user_name: $("input[name='user_name']").val().trim(),
    	            user_pwd: $("input[name='user_pwd']").val().trim(),
    	            new_pwd: $("input[name='new_pwd']").val().trim()

    	        },
    	        dataType: 'json',
    	        success: function (data) {
    	        	data = eval("(" + data + ")");
                    console.log(data);
    	        	console.log(typeof data);
    	        	if(data.message){
    	        		$.MsgBox.Alert("提示", "修改成功！");
    	        		setTimeout(backpage,1200);
    	        	}else{
    	        		$.MsgBox.Alert('提示', "修改失败！");
    	        	}
    	        },
    	        error: function () {
    	             $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
    	        }
    	    });
    		return false;
    	}
    });
});