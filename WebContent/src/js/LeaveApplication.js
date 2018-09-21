//下载
/*$(".down").click(function(){
	window.location.href="";
})*/

$('.cover-color').height($(document).height()-80);
//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function() {
	window.location.reload();
});
//--------------------------------------------添加信息1-----------------------------------------------
function AddContract() {
	$('.info_add input[type="date"]').val('');
	$('.info_add input[type="month"]').val('');
	$('.info_add input[type="search"]').val('');
	$('.info_add textarea').val('');
	$('.info_add select').each(function(){
	    $(this).find('option:checked').prop("selected",false);
	    $(this).find('option').filter(function(){return $(this).text()=='';}).prop("selected",true);
	 })
    $('.cover-color').show();
    $('.contract_add').show();
}
$('.contractAdd_close').click(function () {
    $('.cover-color').hide();
    $('.contract_add').hide();
});
$('#add_cancel').click(function () {
	
    $('.cover-color').hide();
    $('.contract_add').hide();
});
$('#add_submit').click(function () {
	
	var Department=$('.contract_add select[name="Department"] option:selected').attr('text');
    if(Department=='请选择'){
    	Department = '';
    }
    var Classify=$('.contract_add select[name="Classify"] option:selected').attr('text');
    if(Classify=='请选择'){
    	Classify = '';
    }
	 var RealName    = $('.contract_add .RealName').val();
	 var Reason    = $('.contract_add .Reason').val();
	 var ToList    = $('.contract_add .To').val();
	 //把<>转义
	 ToList = ToList.replace('<','&lt');
	 ToList = ToList.replace('>','&gt');
	/* alert(ToList)*/
	 
	 var MailContent    = $('.contract_add .MailContent').val();
	 var Password    = $('.contract_add .Password').val();
	
//  发送ajax请求，传参客户名称id，返回客户联系人和联系方式
  //请假时间的判断
    var start_time1 = $(".contract_add .start_time").val();
    var end_time1 = $(".contract_add .end_time").val();
   
    var start_time = start_time1.replace('T', ' ');
    var end_time = end_time1.replace('T', ' ');

    var t1 = new   Date(Date.parse(start_time.replace(/-/g,"/")));
    var t2 = new   Date(Date.parse(end_time.replace(/-/g,"/")));
    var hour3= parseInt(t1.getHours());
    var hour4= parseInt(t2.getHours());
   
    if(hour3<9){
    	$.MsgBox_Unload.Alert('提示',"请假开始时间在09:00之后");
    	return;
    }
    if(hour4>18){
    	$.MsgBox_Unload.Alert('提示',"请假截止时间在18:00之前");
    	return;
    }
    //没填写提醒填写
    if(!start_time){
    	$.MsgBox_Unload.Alert('提示',"请填写请假开始时间");
    	return;
    }
    if(!end_time){
    	$.MsgBox_Unload.Alert('提示',"请填写请假截止时间");
    	return;
    }
    if(!RealName){
    	$.MsgBox_Unload.Alert('提示',"请填写姓名");
    	return;
    }
    if(!Department){
    	$.MsgBox_Unload.Alert('提示',"请填写部门");
    	return;
    }
    if(!Reason){
    	$.MsgBox_Unload.Alert('提示',"请填写请假事由");
    	return;
    }
    if(!Classify){
    	$.MsgBox_Unload.Alert('提示',"请填写请假类别");
    	return;
    }
    if(!ToList){
    	$.MsgBox_Unload.Alert('提示',"请填写收件人");
    	return;
    }
    if(!MailContent){
    	$.MsgBox_Unload.Alert('提示',"请填写邮件正文");
    	return;
    }

    if(!Password){
    	$.MsgBox_Unload.Alert('提示',"请填写邮箱密码");
    	return;
    }
    var Index = MailContent.indexOf('，');
	var MailCall = MailContent.slice(0,Index);
	if(MailCall == 'XXX' || MailCall == ''){
    	$.MsgBox_Unload.Alert('提示',"请填写邮件正文称呼，比如“李经理，您好！”");
    	return;
    }
    
    var s = start_time.substring(0,11);
    var e = end_time.substring(0,11);
    var S = new   Date(Date.parse(s.replace(/-/g,"/")));
    var E =  new   Date(Date.parse(e.replace(/-/g,"/")));
  //计算出相差天数
    var time=E.getTime()-S.getTime() ; //时间差的毫秒数
    if(time < 0){
    	$.MsgBox_Unload.Alert('提示',"请检查请假时间是否正确！");
    	return;
    }
    var days=Math.floor(time/(24*3600*1000)+1)
  //获取系统当前时间  
    var nowdate = new Date();  
    var y = nowdate.getFullYear();  
    var m = nowdate.getMonth()+1;  
    var d = nowdate.getDate();  
    var today = y+'-'+m+'-'+d;  
    var today = new Date(Date.parse(today.replace(/-/g,"/")));
    
    //判断是提前了多久申请的
    var T= S.getTime()-today.getTime()  //时间差的毫秒数
	var Ts=Math.floor(T/(24*3600*1000)-1)
    
if(Classify != '病假' && Classify != '丧假'){
    //请假2天以为需要提前2天请假
    if(days<3 && Ts<2){
    	$.MsgBox_Unload.Alert('提示',"根据人事部规定和要求：请假时间在三天以内的（不包括三天），需提前两天向部门领导申请");
    	return;
    }else if(days>=3 && Ts<3){
    	$.MsgBox_Unload.Alert('提示',"根据人事部规定和要求：请假时间超过三天以上（包括三天），需提前三天向部门领导及人事部门申请");
    	return;
    }
}
  //点击提交以后避免用户重复提交
	$("#add_submit").attr("disabled","disabled");
	$("#add_submit").css({
		"background":"#dddddd",
		"color":"#808080",
		"border":"none",
		"box-shadow":"0 0 0 0 #f8fcfd"
	});
	
	var i = MailContent.indexOf('！');
	var MailContentText = MailContent;
	var MailContent1 = MailContent.slice(0,i+1);
	var MailContent2 = MailContent.slice(i+2,-5);
	var nick = MailContent1.slice(1,3);
	var MailContent3 = '<span style="font-family: Microsoft YaHei;font-size: 14px;color:black">'+MailContent1+'<br><br>'+MailContent2+'<br><br>祝好！<br></span>';
	
   $.ajax({
    	type:'GET',
    	url:'LeaveApplicationOperate',
    	data:{
    		Type:'add',
    		Department:Department,
    		RealName:RealName,
    		Classify:Classify,
    		Reason:Reason,
    		StartTime:start_time,
    		EndTime:end_time,
    		ToList:ToList,
    		/*CopyList:CopyList,*/
    		MailContentText:MailContentText,
    		MailContent:MailContent3,
    		nick:nick,
    		Password:Password
    	},
    	success:function(data){
    			 $.MsgBox.Alert("提示", data);
    			 $('.cover-color').hide();
     		    $('.contract_add').hide();
    	},
	    error: function () {
	        $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	    }
    });
});
//--------------------------------------------修改2-----------------------------------------------
$(".preview").click(function(){
	var tr = $(this).parent();
	$(".contract_update2 .update_id").text(tr.find(".preview").attr('value'));
	 var Department = tr.find('.Department').text();
	    if(Department == ''){
	    	$('.contract_update2').find('select[name="Department"]').find('option[text="请选择"]').prop("selected", true);
	    }else{
	    	$('.contract_update2').find('select[name="Department"]').find('option[text="' + Department + '"]').prop("selected", true);
	    }
	  $(".contract_update2 .RealName").val(tr.find('.RealName').text());
	  $(".contract_update2 .To").val(tr.find('.ToList').text());
	  //MailContent1主页面返回
	  var MailContentText = tr.find('.MailContentText').text();
	  $(".contract_update2 .MailContent").text(MailContentText);
	  
	  var start_time1 = tr.find('.StartTime').text();
	  var end_time1 = tr.find('.EndTime').text();
	   var start_time = start_time1.replace(' ', 'T');
       var end_time = end_time1.replace(' ', 'T');
	  $(".contract_update2 .start_time").val(start_time);
	  $(".contract_update2 .end_time").val(end_time);
	  var Classify = tr.find('.Classify').text();
	    if(Classify == ''){
	    	$('.contract_update2').find('select[name="Classify"]').find('option[text="请选择"]').prop("selected", true);
	    }else{
	    	$('.contract_update2').find('select[name="Classify"]').find('option[text="' + Classify + '"]').prop("selected", true);
	    }
	    $(".contract_update2 .Reason").text(tr.find(".GraduateInstitutions").text());
	 $('.cover-color').show();
	    $('.contract_update2').show();
})
$("#update_submit2").click(function(){
	
	var ID = $(".contract_update2 .update_id").text() ;
	var Department=$('.contract_update2 select[name="Department"] option:selected').attr('text');
    if(Department=='请选择'){
    	Department = '';
    }
    var Classify=$('.contract_update2 select[name="Classify"] option:selected').attr('text');
    if(Classify=='请选择'){
    	Classify = '';
    }
	 var RealName    = $('.contract_update2 .RealName').val();
	 var Reason    = $('.contract_update2 .Reason').val();
	 var ToList    = $('.contract_update2 .To').val();
	 ToList = ToList.replace('<','&lt');
	 ToList = ToList.replace('>','&gt');
	 var MailContent    = $('.contract_update2 .MailContent').val();
	 var Password    = $('.contract_update2 .Password').val();
	
//  发送ajax请求，传参客户名称id，返回客户联系人和联系方式
  //请假时间的判断
    var start_time1 = $(".contract_update2 .start_time").val();
    var end_time1 = $(".contract_update2 .end_time").val();
   
    var start_time = start_time1.replace('T', ' ');
    var end_time = end_time1.replace('T', ' ');

    var t1 = new   Date(Date.parse(start_time.replace(/-/g,"/")));
    var t2 = new   Date(Date.parse(end_time.replace(/-/g,"/")));
    var hour3= parseInt(t1.getHours());
    var hour4= parseInt(t2.getHours());
   
    if(hour3<9){
    	$.MsgBox_Unload.Alert('提示',"请假开始时间在09:00之后");
    	return;
    }
    if(hour4>18){
    	$.MsgBox_Unload.Alert('提示',"请假截止时间在18:00之前");
    	return;
    }
    //没填写提醒填写
    if(!start_time){
    	$.MsgBox_Unload.Alert('提示',"请填写请假开始时间");
    	return;
    }
    if(!end_time){
    	$.MsgBox_Unload.Alert('提示',"请填写请假截止时间");
    	return;
    }
    if(!RealName){
    	$.MsgBox_Unload.Alert('提示',"请填写姓名");
    	return;
    }
    if(!Department){
    	$.MsgBox_Unload.Alert('提示',"请填写部门");
    	return;
    }
    if(!Reason){
    	$.MsgBox_Unload.Alert('提示',"请填写请假事由");
    	return;
    }
    if(!Classify){
    	$.MsgBox_Unload.Alert('提示',"请填写请假类别");
    	return;
    }
    if(!ToList){
    	$.MsgBox_Unload.Alert('提示',"请填写收件人");
    	return;
    }
    if(!MailContent){
    	$.MsgBox_Unload.Alert('提示',"请填写邮件正文");
    	return;
    }

    if(!Password){
    	$.MsgBox_Unload.Alert('提示',"请填写邮箱密码");
    	return;
    }
    var Index = MailContent.indexOf('，');
	var MailCall2 = MailContent.slice(0,Index);
	if(MailCall2 == 'XXX' || MailCall2 == ''){
    	$.MsgBox_Unload.Alert('提示',"请填写邮件正文称呼，比如“李经理，您好！”");
    	return;
    } 
    
    var s = start_time.substring(0,11);
    var e = end_time.substring(0,11);
    var S = new   Date(Date.parse(s.replace(/-/g,"/")));
    var E =  new   Date(Date.parse(e.replace(/-/g,"/")));
  //计算出相差天数
    var time=E.getTime()-S.getTime() ; //时间差的毫秒数
    if(time < 0){
    	$.MsgBox_Unload.Alert('提示',"请检查请假时间是否正确！");
    	return;
    }
    var days=Math.floor(time/(24*3600*1000)+1)
  //获取系统当前时间  
    var nowdate = new Date();  
    var y = nowdate.getFullYear();  
    var m = nowdate.getMonth()+1;  
    var d = nowdate.getDate();  
    var today = y+'-'+m+'-'+d;  
    var today = new   Date(Date.parse(today.replace(/-/g,"/")));
    
    //判断是提前了多久申请的
    var T= S.getTime()-today.getTime()  //时间差的毫秒数
	var Ts=Math.floor(T/(24*3600*1000)-1)
    
if(Classify != '病假' && Classify != '丧假'){
    //请假2天以为需要提前2天请假
    if(days<3 && Ts<2){
    	$.MsgBox_Unload.Alert('提示',"根据人事部规定和要求：请假时间在三天以内的（不包括三天），需提前两天向部门领导申请");
    	return;
    }else if(days>=3 && Ts<3){
    	$.MsgBox_Unload.Alert('提示',"根据人事部规定和要求：请假时间超过三天以上（包括三天），需提前三天向部门领导及人事部门申请");
    	return;
    }
}
  //点击提交以后避免用户重复提交
	$("#update_submit2").attr("disabled","disabled");
	$("#update_submit2").css({
		"background":"#dddddd",
		"color":"#808080",
		"border":"none",
		"box-shadow":"0 0 0 0 #f8fcfd"
	});
	var MailContentText = MailContent;
	var i = MailContent.indexOf('！');
	var MailContent1 = MailContent.slice(0,i+1);
	var MailContent2 = MailContent.slice(i+2,-5);
	var nick = MailContent1.slice(1,3);
	
	var MailContent3 = '<span style="font-family: Microsoft YaHei;font-size: 14px;color:black">'+MailContent1+'<br><br>'+MailContent2+'<br><br>祝好！<br></span>';
	
   $.ajax({
    	type:'GET',
    	url:'LeaveApplicationOperate',
    	data:{
    		Type:'update',
    		ID:ID,
    		Department:Department,
    		RealName:RealName,
    		Classify:Classify,
    		Reason:Reason,
    		StartTime:start_time,
    		EndTime:end_time,
    		ToList:ToList,
    		/*CopyList:CopyList,*/
    		MailContentText:MailContentText,
    		MailContent:MailContent3,
    		nick:nick,
    		Password:Password
    	},
    	success:function(data){
    			 $.MsgBox.Alert("提示", data);
    			 $('.cover-color').hide();
     		    $('.contract_update2').hide();
    	},
	    error: function () {
	        $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	    }
    });
})

//--------------------------------------------审核3-----------------------------------------------
$(".Review1").click(function(){
		$(".contract_update .update_id").text($(this).parent().find(".preview").attr('value'));
	//判断是否有权限
	$.ajax({
		type:'get',
		url:'LeaveApplicationReview',
		success:function(data){
			if(data == '没有权限'){
				$.MsgBox.Alert("提示", "没有审核权限！");	
			}else{
				$(".contract_update .ReasonContent").val('');
				$(".contract_update .Password2").val('');
				$('.cover-color').show();
			    $('.contract_update').show();
			}
		},
		error:function(){
			$.MsgBox_Unload.Alert("提示", "网络错误，请稍候重试！");	
		}
	})
})
$("#update_submit").click(function(){
	var ID = $(".contract_update .update_id").text();
	
	var passYN =$(".pass input[name='passYN']:checked").val(); 
	var ReasonContent = $(".ReasonContent").val();
	var Password = $(".Password2").val();
	
	console.log(passYN)
	console.log(ReasonContent)
	if(!passYN){
		$.MsgBox_Unload.Alert("提示", "请选择是否通过！");	
		return;
	}
	if(passYN=='不通过' && ReasonContent==''){
	$.MsgBox_Unload.Alert("提示", "请输入未通过原因！");	
		return;
	}
	if(Password==''){
		$.MsgBox_Unload.Alert("提示", "请输入邮箱密码！");	
		return;
	}
	var Review = '审批'+passYN;
	//点击提交以后避免用户重复提交
	$("#update_submit").attr("disabled","disabled");
	$("#update_submit").css({
		"background":"#dddddd",
		"color":"#808080",
		"border":"none",
		"box-shadow":"0 0 0 0 #f8fcfd"
	});
	 $.ajax({
	    	type:'get',
	    	url:'LeaveApplicationOperate',
	    	data:{
	    		Type:'Review',
	    		ID:ID,
	    		Review:Review,
	    		FailedReason:ReasonContent,
	    		Password:Password
	    	},
	    	success:function(data){
	    			 $.MsgBox.Alert("提示", data);
	    			 $('.cover-color').hide();
	    		    $('.contract_add').hide();
	    		
	    	},
		    error: function () {
		        $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
		    }
	    });
})
$('.contract_update2 .update_close').click(function () {
    $('.cover-color').hide();
    $('.contract_update2').hide();
});
$('#update_cancel2').click(function () {
    $('.cover-color').hide();
    $('.contract_update2').hide();
});
$('.update_close').click(function () {
    $('.cover-color').hide();
    $('.contract_update').hide();
});
$('#update_cancel').click(function () {
    $('.cover-color').hide();
    $('.contract_update').hide();
});
//--------------------------------------------预览4-----------------------------------------------

$('.preview2').click(function () {
	
	$('.MailBar_cover_color').show();
	var tr=$(this).parent();
	  var ID = tr.find(".preview").attr("value");
		$(".table_PackSize").attr("value",ID);
		$(".ReviewContent").text(tr.find(".Review").text());
		$(".news .Eliminate").text(tr.find(".Eliminate").text());
		$(".news .classify_add").text(tr.find(".Classify").text());
		$(".news .sT_add").text(tr.find(".StartTime").text());
		$(".news .eT_add").text(tr.find(".EndTime").text());
		//审核是否通过
		if($(".ReviewContent").text() == '等待审批'){
			$(".news .ok").removeClass('fa-check-square-o').addClass('fa-square-o');
			$(".news .no").removeClass('fa-check-square-o').addClass('fa-square-o');
			$(".news .reasonText").text('');
		}else if($(".ReviewContent").text() == '审批通过'){
			$(".news .ok").removeClass('fa-square-o').addClass('fa-check-square-o');
			$(".news .no").removeClass('fa-check-square-o').addClass('fa-square-o');
			$(".news .reasonText").text('');
		}else if($(".ReviewContent").text().substring(0,5) == '审批不通过'){
			var ReviewContent = $(".ReviewContent").text();
			var reasonText = ReviewContent.substring(6,ReviewContent.length-1);
			$(".news .reasonText").text(reasonText);
			$(".news .no").removeClass('fa-square-o').addClass('fa-check-square-o');
			$(".news .ok").removeClass('fa-check-square-o').addClass('fa-square-o');
		}
		
		//是否销假
		var Eliminate = $(".news .Eliminate").text();
		if(Eliminate == '未销假'||Eliminate == '--'||Eliminate == ''){
			$(".news .EliminateDate").text('');
		}else{
			  Eliminate = new   Date(Date.parse(Eliminate.replace(/-/g,"/")));
			var Y1 = Eliminate.getFullYear();
			var M1 = Eliminate.getMonth()+1;
			var D1 = Eliminate.getDate();
			Eliminate = Y1+'年'+M1+'月'+D1+'日';
			$(".news .EliminateDate").text(Eliminate);
		}
	
		$(".sizeTr").remove();
		$(".addModel1").remove();
		$("#table_PackSize").show();
		$("#table_Config").hide();
		$(".logo").attr("value","尺寸模板");

    $(".news .Department").text("").text(tr.find(".Department").text());
    $(".news .RealName").text("").text(tr.find(".RealName").text());
    $(".news .GraduateInstitutions").text("").text(tr.find(".GraduateInstitutions").text());
    var Classify = tr.find(".Classify").text();
    console.log(Classify)
    for(var i =1 ;i<8;i++){
    	 var text = $(".news .type").find("span").eq(i).text();
    	 console.log(i+text)
    	 if(text == Classify){
    		 var index = i;
    		 console.log(i+text)
    		 $(".news .type").find("span").eq(index).removeClass('fa-square-o').addClass('fa-check-square-o');
    		 $(".news .type").find("span").eq(index).siblings().removeClass('fa-check-square-o').addClass('fa-square-o');
    		 $(".news .type").find("span").eq(0).removeClass('fa-square-o').removeClass('fa-check-square-o');
    	 }
    }
    var time1 = tr.find(".StartTime").text();
    var time2 = tr.find(".EndTime").text();
    var year1 = time1.slice(0,4);
    var month1 = time1.slice(5,7);
    var day1 = time1.slice(8,10);
    var hour1 = time1.slice(11);
    var year2 = time2.slice(0,4);
    var month2 = time2.slice(5,7);
    var day2 = time2.slice(8,10);
    var hour2 = time2.slice(11);
    console.log(year1)
    console.log(month1)
    console.log(day1)
    console.log(hour1)
    $(".news .year1").text("").text(year1);
    $(".news .month1").text("").text(month1);
    $(".news .day1").text("").text(day1);
    $(".news .hour1").text("").text(hour1);
    $(".news .year2").text("").text(year2);
    $(".news .month2").text("").text(month2);
    $(".news .day2").text("").text(day2);
    $(".news .hour2").text("").text(hour2);
   //共请假时间
    function getHour(s1,s2) {
        s1 = new Date(s1.replace(/-/g, '/'));
        s2 = new Date(s2.replace(/-/g, '/'));
        var ms = Math.abs(s1.getTime() - s2.getTime());
        return ms / 1000 / 60 / 60;
    }
   var hourAmount = getHour(time1,time2);
    var D = parseInt(hourAmount / 24);
    var H = hourAmount % 24;
    console.log(D)
    console.log(H)
    console.log(D == 0 && H !='')
    
    if(H == ''){
    	var T = D+'天';
    }else if(H == 9){
    	var T = D+1+'天';
    }else if(D == 0 && H !=''){
      var T = H+'小时';
    }
    else{
    	var T = D+'天'+ H +'小时';
    }
    
    $(".news .T").text("").text(T);   
    $('.hidePdf').show();
    $(".MarkAdd").css("width",$(".ShippingMark").width()) 
});

$('#contract_close1').click(function () {
    $('.MailBar_cover_color').hide();
    $('.hidePdf').hide();
});
//--------------------------------------------销假5-----------------------------------------------
$(".Eliminate1").click(function(){
	$(this).css('color','rgb(221, 221, 221)');
	$(this).attr('title','邮件发送中，请勿重复点击！');
	$(this).find('.Eliminate1_btn').attr("disabled","disabled");
	var tr= $(this).parent();
	var Review = tr.find(".Review").text();
	if(Review == '审批通过'){
		var ID = tr.find(".preview").attr('value');
		$.ajax({
			type:'get',
			url:'StaffApplicationEliminate',
			data:{
				ID:ID
			},
			success:function(data){
					$.MsgBox.Alert("提示", data);
					$(this).attr('title','邮件发送中，请勿重复点击！');
			},
			error:function(){
				$.MsgBox.Alert("提示", "网络错误请稍候重试！");
				$(this).attr('title','邮件发送中，请勿重复点击！');
			}
		})
	}else{
		$.MsgBox.Alert("提示", "无效操作！");
	}
	
})

//导出请假单
$(".ShowOrHide").click(function(){
	var ID = $(".table_PackSize").attr("value");
	var Department = $(".news .Department").html();
	var RealName = $(".news .RealName").html();
	var Classify = $(".news .classify_add").html();
	var Reason = $(".news .GraduateInstitutions").html();
	var StartTime = $(".news .sT_add").html();
	var EndTime = $(".news .eT_add").html(); 
	$.ajax({
        type : 'get',
        url : 'LeaveApplicationExport',
        data : {
        	ID:ID,
        	Department: Department,
        		RealName: RealName,
        		Classify: Classify,
        		Reason: Reason,
        		StartTime: StartTime,
        		EndTime: EndTime
        },
        success : function (data) {
        	window.location.href=data;
        },
        error : function () {
        	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
        }
    }); 
})
//--------------------------------------------姓名6-----------------------------------------------
$(".contract_add .RealName").on("input propertychange",function(){
	var RealName = $(".contract_add .RealName").val();
	$.ajax({
		type:'get',
		url:'GetStaffApplicationName',
		dataType:'JSON',
		data:{
			keyword:RealName
		},
		success:function(data){
			console.log(data);
			 var str = "";
             for(var i = 1 ; i < data.length ; i++){
            	 str += "<li  class='addListLi'>"+data[i].StaffName+"</li>";
             }
             $(".contract_add .RealNamelist").empty();
            $(".contract_add .RealNamelist").append(str);
             $(".contract_add .RealNamelist").show();
		},
		error:function(){
			 $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
		}
	})
})
$(document).on("click",function(){
	$(".contract_add .RealNamelist").hide();
})
$(document).on("click",".contract_add .addListLi",function(){
	var currentCont = $(this).text();
	$(this).parent().prev().val(currentCont);
	$(".contract_add .RealNamelist").hide();
})
//修改的姓名
$(".contract_update2 .RealName").on("input propertychange",function(){
	var RealName = $(".contract_update2 .RealName").val();
	$.ajax({
		type:'get',
		url:'GetStaffApplicationName',
		dataType:'JSON',
		data:{
			keyword:RealName
		},
		success:function(data){
			console.log(data);
			 var str = "";
             for(var i = 1 ; i < data.length ; i++){
            	 str += "<li  class='addListLi'>"+data[i].StaffName+"</li>";
             }
             $(".contract_update2 .RealNamelist").empty();
             $(".contract_update2 .RealNamelist").append(str);
             $(".contract_update2 .RealNamelist").show();
		},
		error:function(){
			 $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
		}
	})
})
$(document).on("click",function(){
	$(".contract_update2 .RealNamelist").hide();
})
$(document).on("click",".contract_update2 .addListLi",function(){
	var currentCont = $(this).text();
	$(this).parent().prev().val(currentCont);
	$(".contract_update2 .RealNamelist").hide();
})

//--------------------------------------------收件人6-----------------------------------------------
$(".contract_add .To").on("input propertychange",function(){
	/*alert(1)*/
	var To = $(".contract_add .To").val();
	$.ajax({
		type:'get',
		url:'GetStaffApplicationToList',
		dataType:'JSON',
		data:{
			keyword :To
		},
		success:function(data){
			console.log(data);
			 var str = "";
             for(var i = 1 ; i < data.length ; i++){
            	 str += "<li  class='addListLi2'>"+data[i].StaffName+'&lt'+data[i].StaffMail+'&gt'+"</li>";
             }
             $(".contract_add .Tolist").empty();
            $(".contract_add .Tolist").append(str);
             $(".contract_add .Tolist").show();
		},
		error:function(){
			 $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
		}
	})
})
$(document).on("click",function(){
	$(".contract_add .Tolist").hide();
})
$(document).on("click",".contract_add .addListLi2",function(){
	var currentCont = $(this).text();
	$(this).parent().prev().val(currentCont);
	$(".contract_add .Tolist").hide();
})
//修改
$(".contract_update2 .To").on("input propertychange",function(){
	var To = $(".contract_update2 .To").val();
	$.ajax({
		type:'get',
		url:'GetStaffApplicationToList',
		dataType:'JSON',
		data:{
			keyword :To
		},
		success:function(data){
			console.log(data);
			 var str = "";
             for(var i = 1 ; i < data.length ; i++){
            	 str += "<li  class='addListLi2'>"+data[i].StaffName+'&lt'+data[i].StaffMail+'&gt'+"</li>";
             }
             $(".contract_update2 .Tolist").empty();
             $(".contract_update2 .Tolist").append(str);
             $(".contract_update2 .Tolist").show();
		},
		error:function(){
			 $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
		}
	})
})
$(document).on("click",function(){
	$(".contract_update2 .Tolist").hide();
})
$(document).on("click",".contract_update2 .addListLi2",function(){
	var currentCont = $(this).text();
	$(this).parent().prev().val(currentCont);
	$(".contract_update2 .Tolist").hide();
});
