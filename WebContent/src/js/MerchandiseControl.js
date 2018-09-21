if($('input[name="selected"]:checked').val()=='singleSelect'){
    $('.select-content').css('margin-left','33%');
}else{
    $('.select-content').css('margin-left','23%');

}
function Check(selected) {
	 if (selected == "singleSelect") {
	        $('.select2').hide();
	        $('.select-content').css('margin-left','33%');
	    } else {
	        $('.select2').show();
	        $('.select-content').css('margin-left','23%');
	    }
}


function INSearch() {
    $('#search').val('search');
    $('#top_text_from').submit();
}
function Cancel() {
  /*  $('#search').val('cancel');
    $('input[name="searchContent1"]').val('');
    $('input[name="searchContent2"]').val('');
    $('#top_text_from').submit();*/
	window.location.href = 'Commodity';
}




//新添加的功能-增加里面的上传文件
$(".contract_add .upfile_span").click(function(){
	/*alert(4)*/
	$(".MailBar_cover_color_file_add").show();
	$(".MailBar_add").show();
})
$(".MailBar_add .MailBar_close").click(function(){
	$(".MailBar_cover_color_file_add").hide();
	$(".MailBar_add").hide();
	/*$.MsgBox_Unload.Alert("提示", "请检查是否选择文件！");*/
})
//新添加的功能-增加里面的上传文件的上传
$(".MailBar_add #Mail_Send1").click(function(){
	/*alert(2)*/
			/*var  ID = $(".contract_update .contract_title").attr("value");
			alert(ID)*/
			/*ID不能直接*/
			var v1 = $(".MailBar_add #Mail_fileToUpload1").val();
			
			if(v1 == ''){
				$.MsgBox_Unload.Alert("提示", "请检查是否选择文件！");
				return;
			}
			$.ajaxFileUpload({ 
		        url:'CommodityPurchaseRecord',
		        secureuri:false,  
		        fileElementId:'Mail_fileToUpload1',//file标签的id  
		       dataType: 'JSON',//返回数据的类型  
		        data:{
		        	SupplierID : 0,
		        	Commodity : 0
		        },//一同上传的数据  
		        success: function (data) {
		        	console.log(data);
		        	console.log(data.substring(1,data.length-1));
		        	$.MsgBox_Unload.Alert("提示", data);	
		        	$(" .MailBar_add").hide();
	           		$(".MailBar_cover_color_file_add").hide();
	           		$(".MailBar_cover_color").show();
		        	/*if(data =='"上传成功,最后记得保存！"'){
		           		$.MsgBox_Unload.Alert("提示", "上传成功，请稍后提交！");	
		           		$(" .MailBar_add").hide();
		           		$(".MailBar_cover_color_file_add").hide();
		           		$(".MailBar_cover_color").show();
		        	}else if(data =='"上传失败！"'){
		        		$.MsgBox_Unload.Alert("提示", "上传失败请稍后再试！");
		        	}*/
		        },  
		        error: function () {  
		        	$.MsgBox_Unload.Alert("提示", "网络错误请稍后再试！"); 
		        }  
		    }); 
			
		})
/*添加里面的下载功能*/
		$(".contract_add .downfile_span").click(function(){
			//点击以后发请求，如果存在文件，后台返回true直接进行下载，如果返回false提示用户：没有可供下载的文件
			$.ajax({
				type:'get',
				url:"DownloadCommodityPurchaseRecord",
				data:{
					Commodity:0
				},
				success:function(data){
					alert(data)
					if(data != '' && data != '"--"' && data != '""'){
						var d = data.substring(1,data.length-1);
						window.location.href=d;
					}else{
						$.MsgBox_Unload.Alert("提示", "没有可下载的文件！");
					}
				},
				error:function(){
					$.MsgBox_Unload.Alert("提示", "网络错误请稍候再试！");
				}
			})
		})
/*添加里面的预览功能*/
		$(".contract_add .showfile_span").click(function(){
			//点击以后发请求，如果存在文件，后台返回true直接进行下载，如果返回false提示用户：没有可供预览的文件
			$.ajax({
				type:'get',
				url:"DownloadCommodityPurchaseRecord",
				data:{
					Preview : 'Preview' ,
					Commodity : 0
				},
				success:function(data){
					if(data != '' && data != '"--"' && data != '""'){
						var d = data.substring(1,data.length-1);
						window.location.href=d;
					}else{
						$.MsgBox_Unload.Alert("提示", "没有可预览的文件！");
					}
				},
				error:function(){
					$.MsgBox_Unload.Alert("提示", "网络错误请稍候再试！");
				}
			})
		})
/*添加里面的发送邮件功能*/
		$(".contract_add .sendEmail_span").click(function(){
			var shoujianMan = $(".contract_add #shoujianMan").val();
			var chaosongMan = $(".contract_add #chaosongMan").val();
			var chaosongMan1 = $(".contract_add #chaosongMan1").val();
			var chaosongMan2 = $(".contract_add #chaosongMan2").val();
			if(chaosongMan != '' && chaosongMan1 != '' && chaosongMan2 != '' ){
				var CCList = chaosongMan +','+ chaosongMan1 +','+ chaosongMan2 ;
			}else if(chaosongMan != '' && chaosongMan1 != '' && chaosongMan2 == '' ){
				var CCList = chaosongMan +','+ chaosongMan1;
			}else if(chaosongMan != '' && chaosongMan1 == '' && chaosongMan2 == ''){
				var CCList = chaosongMan +','+ chaosongMan2;
			}else if(chaosongMan == '' && chaosongMan1 == '' && chaosongMan2 == ''){
				var CCList ='';
			}
			var zhuti = $(".contract_add #zhuti").val();
			var neirong = $(".contract_add #neirong").val();
			var Password = $(".contract_add #EmailPassword").val();
			if(shoujianMan == ''){
				$.MsgBox_Unload.Alert("提示", "收件人必填！");
				return;
			}
			if(zhuti == ''){
				$.MsgBox_Unload.Alert("提示", "邮件主题必填！");
				return;
			}
			if(neirong == ''){
				$.MsgBox_Unload.Alert("提示", "邮箱内容必填！");
				return;
			}
			if(Password == ''){
				$.MsgBox_Unload.Alert("提示", "邮箱密码必填！");
				return;
			}
			
			var neirong2 = neirong.slice(4,-7);
			
			neirong2 = '<p style="font-family: Microsoft YaHei;font-size: 14px;color:black">您好！<br><br>'+neirong2+"<br><br>顺颂商祺！</p>";
			
			$.ajax({
				type:'get',
				url:'CommodityMail',
				data:{
					ID :0,
					MailID:0,
					Consignee : shoujianMan ,
					CCList : CCList ,
					Subject : zhuti ,
					Content : neirong2,
					Content2 : neirong,
					Password : Password
				},
				dataType:'JSON',
				success:function(data){
					$.MsgBox_Unload.Alert("提示", data);
				/*	alert(data)*/
					/*if(data){
						$.MsgBox_Unload.Alert("提示", "邮件发送成功！");
					}else{
						$.MsgBox_Unload.Alert("提示", "发送失败！");
					}*/
				},
				error:function(){
					$.MsgBox_Unload.Alert("提示", "网络错误请稍候重试！");
				}
			})
			
		})

		
		//新增的功能-修改里面的上传文件
$(".contract_update .upfile_span").click(function(){
	$(".MailBar_cover_color_file_update").show();
	$(".MailBar_update").show();
})
$(".MailBar_update .MailBar_close").click(function(){
	$(".MailBar_cover_color_file_update").hide();
	$(".MailBar_update").hide();
	/*$.MsgBox_Unload.Alert("提示", "请检查是否选择文件！");*/
})
//新添加的功能-修改里面的上传文件的上传
$(".MailBar_update #Mail_Send").click(function(){
			var  ID = $(".contract_update .contract_title").attr("value");
		/*	alert(ID)*/
			var SupplierID =$("#SupplierID").text();
			if(SupplierID == '--'||"供应商管理ID"){
				SupplierID = 0;
			}
			var  ID = $(".contract_update .contract_title").attr("value");
			/*alert(SupplierID)
			alert(ID)*/
			var v1 = $(".MailBar_update #Mail_fileToUpload").val();
			
			if(v1 == ''){
				$.MsgBox_Unload.Alert("提示", "请检查是否选择文件！");
				return;
			}
			$.ajaxFileUpload({  
		        url:'CommodityPurchaseRecord',
		        secureuri:false,  
		        fileElementId:'Mail_fileToUpload',//file标签的id  
		        dataType: 'JSON',//返回数据的类型  
		        data:{
		        	SupplierID : SupplierID,
		        	Commodity :ID 
		        },//一同上传的数据  
		        success: function (data) {
		        	console.log(data);
		        	console.log(data.substring(1,data.length-1));
		        	/*var message = data.substring(1,data.length-1);*/
		        	$.MsgBox_Unload.Alert("提示",data );	
	           		$(".MailBar_update").hide();
	           		$(".MailBar_cover_color_file_update").hide();
	           		$(".MailBar_cover_color").show();
		        	
		        	/*if(data =='"上传成功,最后记得保存！"'){
		           		$.MsgBox_Unload.Alert("提示", "上传成功，请稍后提交！");	
		           		$(".MailBar_update").hide();
		           		$(".MailBar_cover_color_file_update").hide();
		           		$(".MailBar_cover_color").show();
		        	}else if(data =='"上传失败！"'){
		        		$.MsgBox_Unload.Alert("提示", "上传失败请稍后再试！");
		        	}*/
		        },  
		        error: function () {  
		        	$.MsgBox_Unload.Alert("提示", "网络错误请稍后再试！");
		        }  
		    }); 
			
		})
/*修改里面的下载功能*/
		$(".contract_update .downfile_span").click(function(){
			//点击以后发请求，如果存在文件，后台返回true直接进行下载，如果返回false提示用户：没有可供下载的文件
			var  ID = $(".contract_update .contract_title").attr("value");
			$.ajax({
				type:'get',
				url:"DownloadCommodityPurchaseRecord",
				data:{
					Commodity:ID
				},
				success:function(data){
				/*	alert(data)*/
					if(data != '' && data != '"--"' && data != '""'){
						/*alert(1)*/
						var d = data.substring(1,data.length-1);
						window.location.href=d;
					}else{
						/*alert(2)*/
						$.MsgBox_Unload.Alert("提示", "没有可下载的文件！");
					}
				},
				error:function(){
					$.MsgBox_Unload.Alert("提示", "网络错误请稍候再试！");
				}
			})
		})
/*修改里面的预览功能*/
		$(".contract_update .showfile_span").click(function(){
			//点击以后发请求，如果存在文件，后台返回true直接进行下载，如果返回false提示用户：没有可供预览的文件
			var  ID = $(".contract_update .contract_title").attr("value");
			$.ajax({
				type:'get',
				url:"DownloadCommodityPurchaseRecord",
				data:{
					Commodity :ID,
					Preview : 'Preview'
				},
				success:function(data){
					
					if(data != '' && data != '"--"' && data != '""'){
						var d = data.substring(1,data.length-1);
						window.location.href=d;
					}else{
						$.MsgBox_Unload.Alert("提示", "没有可预览的文件！");
					}
				},
				error:function(){
					$.MsgBox_Unload.Alert("提示", "网络错误请稍候再试！");
				}
			})
		})
/*修改里面的发送邮件功能*/
		$(".contract_update .sendEmail_span").click(function(){
			var shoujianMan = $(".contract_update #shoujianMan").val();
			var chaosongMan = $(".contract_update #chaosongMan").val();
			var chaosongMan1 = $(".contract_update #chaosongMan1").val();
			var chaosongMan2 = $(".contract_update #chaosongMan2").val();
			if(chaosongMan != '' && chaosongMan1 != '' && chaosongMan2 != '' ){
				var CCList = chaosongMan +','+ chaosongMan1 +','+ chaosongMan2 ;
			}else if(chaosongMan != '' && chaosongMan1 != '' && chaosongMan2 == '' ){
				var CCList = chaosongMan +','+ chaosongMan1;
			}else if(chaosongMan != '' && chaosongMan1 == '' && chaosongMan2 == ''){
				var CCList = chaosongMan +','+ chaosongMan2;
			}else if(chaosongMan == '' && chaosongMan1 == '' && chaosongMan2 == ''){
				var CCList ='';
			}
			var zhuti = $(".contract_update #zhuti").val();
			var neirong = $(".contract_update #neirong").val();
			/*alert(neirong)*/	/*alert(neirong)*/
			var Password = $(".contract_update #EmailPassword").val();
			if(shoujianMan == ''){
				$.MsgBox_Unload.Alert("提示", "收件人必填！");
				return;
			}
			if(zhuti == '' || zhuti == '--'){
				$.MsgBox_Unload.Alert("提示", "邮件主题必填！");
				return;
			}
			if(neirong == '' || neirong == '--'){
				$.MsgBox_Unload.Alert("提示", "邮箱内容必填！");
				return;
			}
			if(Password == ''){
				$.MsgBox_Unload.Alert("提示", "邮箱密码必填！");
				return;
			}
			
			var  ID = $(".contract_update .contract_title").attr("value");
			
			if(ID == '--'){
				
				ID = 0;
			}
			var MailID = $(".contract_update #MailID").text();
			
			if(MailID == '--'){
				MailID = 0;
			}
			/*var neirong = neirong.substring()*/
			/*alert(neirong)*/
			var neirong2 = neirong.slice(4,-7);
			/*alert(neirong2)*/
			neirong2 = '<p style="font-family: Microsoft YaHei;font-size: 14px;color:black">您好！<br><br>'+neirong2+"<br><br>顺颂商祺！</p>";
			/*alert(neirong2)*/
			$.ajax({
				type:'get',
				url:'CommodityMail',
				data:{
					ID :ID,
					MailID:MailID,
					Consignee : shoujianMan ,
					CCList : CCList ,
					Subject : zhuti ,
					Content : neirong2,
					Content2 :neirong,
					Password : Password
				},
				dataType:'JSON',
				success:function(data){
					$.MsgBox_Unload.Alert("提示", data);
					/*alert(data)*/
					/*if(data != 'false'){
						
						$.MsgBox_Unload.Alert("提示", "邮件发送成功！");
					}else{
						
						$.MsgBox_Unload.Alert("提示", "发送失败,请检查你的邮箱！");
					}*/
				},
				error:function(){
					$.MsgBox_Unload.Alert("提示", "网络错误请稍候重试！");
				}
			})
			
		})
