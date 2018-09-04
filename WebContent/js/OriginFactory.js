
$('#searchContent1').keypress(function (event) {
    $('#search').val('search');
    var keynum = (event.keyCode ? event.keyCode : event.which);
    if (keynum == '13') {
        $('#top_text_from').submit();
    }
});
$('#searchContent2').keypress(function (event) {
    $('#search').val('search');
    var keynum = (event.keyCode ? event.keyCode : event.which);
    if (keynum == '13') {
        $('#top_text_from').submit();
    }
});

//判断是否延迟
function isSendChange(text1,value){
	/*alert(1)*/
	/* console.log(text1)*/
	 var ID = text1.childNodes[1].getAttribute("value") ;
	 console.log(ID)
	/* alert(ID)*/
	   
		 if(value == "YES"){
			 /*alert(2)*/
		    	 var Type="yes";
			console.log("发送")
			$('.cover-color').show();
		    $('.contract_send').show();
		    
			  //点击确认按钮
			    $(document).on("click","#send_submit",function(){
			    	var PO = text1.getElementsByClassName("PO")[0].innerText;
			    	var SO=text1.getElementsByClassName("SO")[0].innerText;
			    	var FactoryPeriod=text1.getElementsByClassName("FactoryPeriod")[0].innerText;
			    	var DelayPeriod=text1.getElementsByClassName("DelayPeriod")[0].innerText;
			    	var DelayReason=text1.getElementsByClassName("DelayReason")[0].innerText;
			    	console.log(PO)
			    	console.log(SO)
			    	console.log(FactoryPeriod)
			    	console.log(DelayPeriod)
			    	console.log(DelayReason)
			    	console.log("发送1")
			    	   $.ajax({
			            type : 'get',
			            url : 'SendFromOriginFactory',
			            data : {
			            	ID:ID,
			            	Type : Type,
			            	PO:PO,
			            	SO : SO,
			            	FactoryPeriod:FactoryPeriod,
			            	DelayPeriod : DelayPeriod,
			            	DelayReason :DelayReason
			            	
			            },
			            dataType : 'json',
			            success : function (data) {
			            	
			                $.MsgBox.Alert('提示','延迟发送成功!');
			                /*$('.cover_color').hide();*/
			                $('.contract_send').hide();
			            },
			            error : function () {
			                $.MsgBox.Alert("提示", "延迟发送失败！");
			            }
			        }); 
			        	    
			    });
		     }else{
		    	 var Type="no";
					$.ajax({
				        type: 'get',
				        url: 'SendFromOriginFactory',
				        data: {
				        	ID:ID,
				        	Type : Type
				        },
				        success:function (data) {
				        	console.log("success")
				        }
				    })
				}
	}




$(function(){

	//判断是否发货栏 状态   
	for(var i = 1; i < $("#table1 tr").length ; i++){
		var tdLength =  $("#table1 tr").eq(i).children().length;
	/*	alert( $("#table1 tr").eq(i).children().eq(11).text())*/
		if( $("#table1 tr").eq(i).children().eq(11).text() == "yes"){
			/*alert(2)*/
			var str = '<label><input type="radio" name="'+i+'isSend" checked="checked" onclick="isSendChange(this.parentNode.parentNode.parentNode,this.value)" value="YES">YES </label>'+
			'<label><input type="radio"  name="'+i+'isSend" onclick="isSendChange(this.parentNode.parentNode.parentNode,this.value)" value="NO">NO</label>';
			$("#table1 tr").eq(i).children().eq(11).html(str);
		}
		else{
		/*	alert(3)*/
			var str = '<label><input type="radio" name="'+i+'isSend" onclick="isSendChange(this.parentNode.parentNode.parentNode,this.value)" value="YES">YES </label>'+
			'<label><input type="radio"  name="'+i+'isSend" checked="checked"  onclick="isSendChange(this.parentNode.parentNode.parentNode,this.value)" value="NO">NO</label>';
			$("#table1 tr").eq(i).children().eq(11).html(str);
		}
	}

	$(document).on("mouseover",".PSD-email-td, .PT-email-td",function(){
		$(this).css({"cursor":"pointer","color":"red","text-decoration":"underline"});
	});
	$(document).on("mouseout",".PSD-email-td, .PT-email-td",function(){
		$(this).css({"cursor":"pointer","color":"#333","text-decoration":"none"});
	});

	$(document).on("mouseover","#table1 .Inform",function(){
		$(this).css({"cursor":"pointer","color":"red","text-decoration":"underline"});
	});
	$(document).on("mouseout","#table1 .Inform",function(){
		$(this).css({"cursor":"pointer","color":"#333","text-decoration":"none"});
	});

	$(document).on("click",".PSD-email-td",function(){
		if($(this).text()=="YES"){
			var Type = "PushShipDate";
			var FactoryID = $(this).siblings("td.contract-edit").attr("value");
			$.ajax({
		        type: 'POST',
		        url: 'SendPushEmail',
		        data: {
		            Type: Type,
		            FactoryID: FactoryID
		        },
		        contentType: "application/x-www-form-urlencoded; charset=utf-8",
		        dataType:"json",
		        success: function (data) {
		        	console.log(data);
		        	console.log(typeof data);
		        	if(data.length>1){
		        		var CCList = data[1]["CCList"];
		        		var MainBody = data[1]["MainBody"];
		        		var Receptor = data[1]["Receptor"];
		        		var Subject = data[1]["Subject"];
		        		var curPO = $(this).siblings("td.PO").text().trim();
		        		$(".m-subject").attr("value",curPO);
		        		$(".PSD-email-tem").attr("value",FactoryID);
		        		$(".MailBar_cover_color").fadeIn(100);
		        		$(".MailBar_cover_color").css("height",$("#originfactory_wrapper").height()+"px");
		        		$(".PSD-email-tem").fadeIn(100);
		        		$(".m-recipient input").val("");
		        		$(".m-CC input").val("");
		        		var ReceptorArr = Receptor.split(";");
		        		if(ReceptorArr.indexOf("")==ReceptorArr.length-1){
		        			ReceptorArr.pop();
		        		}
		        		var insertRowStr1 = '<div class="u-recipient">'+
					'<div class="u-recipient-div" style="visibility:visible;"><span class="i-recipient"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-recipient-div" style="visibility:hidden;opacity:0;"><span class="i-recipient"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-recipient-div" style="visibility:hidden;opacity:0;"><span class="i-recipient"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
				'</div>';
						var RArrLen = ReceptorArr.length;
						var RArrLen2 = ReceptorArr.length-1;
						$(".u-recipient").eq(1).remove();
		        		$(".u-recipient").eq(1).remove();
		        		if(ReceptorArr.length<4){
		        			for(var qq = 0;qq<3;qq++){
		        				$(".u-recipient-div:eq("+qq+")").find("span.i-add").fadeIn(50);
		        			}
		        		}else if(ReceptorArr.length<7){
		        			$(".m-recipient").append(insertRowStr1);
		        			for(var qq = 0;qq<6;qq++){
		        				$(".u-recipient-div:eq("+qq+")").find("span.i-add").fadeIn(50);
		        			}
		        		}else{
		        			$(".m-recipient").append(insertRowStr1);
		        			$(".m-recipient").append(insertRowStr1);
		        			for(var qq = 0;qq<9;qq++){
		        				$(".u-recipient-div:eq("+qq+")").find("span.i-add").fadeIn(50);
		        			}
		        		}
		        		$(".u-recipient-div:gt("+RArrLen2+")").css({"visibility":"hidden","opacity":"0"});
	        			$(".u-recipient-div:lt("+RArrLen+")").css({"visibility":"visible","opacity":"1"});
	        			$(".u-recipient-div:lt("+RArrLen2+")").find("span.i-add").fadeOut(50);
	        			$(".u-recipient-div:eq("+RArrLen2+")").find("span.i-add").fadeIn(50);
	        			for(var ijj = 0;ijj<RArrLen;ijj++){
	        				$(".u-recipient-div:eq("+ijj+")").find("input").val(ReceptorArr[ijj]);
	        			}
	        			var temp = 0;
	        			$(".u-recipient-div").each(function(){
	        			    if($(this).css("visibility")=="visible"){
	        			        temp++;
	        			    }
	        			});
	        			for(var ww = 1;ww<temp;ww++){
	        			    $(".u-recipient-div").eq(ww).find("span.i-recipient").text("收件人"+ww);
	        			}

	        			// 抄送人
		        		var CCListArr = CCList.split(";");
		        		if(CCListArr.indexOf("")==CCListArr.length-1){
		        			CCListArr.pop();
		        		}
		        		var insertRowStr2 = '<div class="u-CC">'+
					'<div class="u-CC-div" style="visibility:visible;"><span class="i-CC"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-CC-div" style="visibility:hidden;opacity:0;"><span class="i-CC"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-CC-div" style="visibility:hidden;opacity:0;"><span class="i-CC"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
				'</div>';
						var CArrLen = CCListArr.length;
						var CArrLen2 = CCListArr.length-1;
						$(".u-CC").eq(1).remove();
		        		$(".u-CC").eq(1).remove();
		        		if(CCListArr.length<4){
		        			for(var ee = 0;ee<3;ee++){
		        				$(".u-CC-div:eq("+ee+")").find("span.i-add").fadeIn(50);
		        			}
		        		}else if(CCListArr.length<7){
		        			$(".m-CC").append(insertRowStr2);
		        			for(var ee = 0;ee < 6 ; ee++){
		        				$(".u-CC-div:eq("+ee+")").find("span.i-add").fadeIn(50);
		        			}
		        		}else{
		        			$(".m-CC").append(insertRowStr2);
		        			$(".m-CC").append(insertRowStr2);
		        			for(var ee = 0;ee<9;ee++){
		        				$(".u-CC-div:eq("+ee+")").find("span.i-add").fadeIn(50);
		        			}
		        		}
		        		$(".u-CC-div:gt("+CArrLen2+")").css({"visibility":"hidden","opacity":"0"});
	        			$(".u-CC-div:lt("+CArrLen+")").css({"visibility":"visible","opacity":"1"});
	        			$(".u-CC-div:lt("+CArrLen2+")").find("span.i-add").fadeOut(50);
	        			$(".u-CC-div:eq("+CArrLen2+")").find("span.i-add").fadeIn(50);
	        			for(var ijjj = 0;ijjj<CArrLen;ijjj++){
	        				$(".u-CC-div:eq("+ijjj+")").find("input").val(CCListArr[ijjj]);
	        			}
	        			var temp2 = 0;
	        			$(".u-CC-div").each(function(){
	        			    if($(this).css("visibility")=="visible"){
	        			        temp2++;
	        			    }
	        			});
	        			for(var rr = 1;rr<temp2;rr++){
	        			    $(".u-CC-div").eq(rr).find("span.i-CC").text("抄送人"+rr);
	        			}

		        		$(".m-subject input").val(Subject);
		        		var wrapCount = globalPlaceholderCount(MainBody);
		        		$(".m-content .wrap-p:gt(0)").remove();
		        		if(wrapCount==2){
		        			var newMainBody = MainBody.split("\n")[1];
		        			$(".m-content div p.i-content").text(newMainBody);
		        		}else if(wrapCount>2){
		        			var newMainBody2 = MainBody.split("\n");
		        			var pStr = '';
		        			for(var LL=2;LL<newMainBody2.length-1;LL++){
		        				pStr+= '<p class="wrap-p" contenteditable="true"></p>';
		        			}
		        			$("p.i-content").after(pStr);
		        			$("p.i-content").text(newMainBody2[1]);
		        			for(var LLL=1;LLL<newMainBody2.length-2;LLL++){
		        				$(".m-content .wrap-p:eq("+LLL+")").text(newMainBody2[LLL+1]);
		        			}
		        		}
		        	}
		        },
		        error: function () {
		            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
		        }
		    });
		}else if($(this).text()=="NO"){
			var curPO = $(this).siblings("td.PO").text().trim();
			$(".m-subject").attr("value",curPO);
			var FactoryID1 = $(this).siblings("td.contract-edit").attr("value");
			$(".PSD-email-tem").attr("value",FactoryID1);
			$(".MailBar_cover_color").fadeIn(100);
			$(".MailBar_cover_color").css("height",$("#originfactory_wrapper").height()+"px");
			$(".PSD-email-tem").fadeIn(100);
			$(".m-recipient input").val("");
			$(".m-CC input").val("");
			$(".u-recipient").eq(1).remove();
			$(".u-recipient").eq(1).remove();
			$(".u-recipient-div:gt(0)").css({"visibility":"hidden","opacity":"0"});
			$(".u-recipient-div:eq(0)").find("span.i-add").fadeIn(50);
			$(".u-recipient-div:eq(0)").find("input").val("AChen3@formfactor.com");
			$(".u-CC").eq(1).remove();
			$(".u-CC").eq(1).remove();
			$(".u-CC-div:eq(0)").find("span.i-add").fadeOut(50);
			$(".u-CC-div:eq(1)").find("span.i-add").fadeOut(50);
			$(".u-CC-div:eq(2)").find("span.i-add").fadeIn(50);
			$(".u-CC-div:eq(0)").find("input").val("jiangyaping@eoulu.com");
			$(".u-CC-div:eq(1)").find("input").val("zhaona@eoulu.com");
			$(".u-CC-div:eq(2)").find("input").val("zhaowenzhen@eoulu.com");
			$(".m-subject input").val("Please help update the ship date of "+curPO+" within today");
			$(".m-content div p:nth-child(1)").text("Hi, Ailin,");
			$(".m-content .i-content").text("We are really urgent. Would you please help update the ship date of "+curPO+" within today?");
			$(".m-content .wrap-p:gt(0)").remove();
		}
	});

	$(document).on("click","#PSD-do",function(){
		var Type = "PushShipDate";
		var temp = 0;
        $(".u-recipient-div").each(function(){
            if($(this).css("visibility")=="visible"){
                temp++;
            }
        });
        var temp1 = 0;
        $(".u-CC-div").each(function(){
            if($(this).css("visibility")=="visible"){
                temp1++;
            }
        });
		var Receptor = [];
		for(var ii=0;ii<temp;ii++){
			var tempVal = $(".u-recipient-div").eq(ii).find("input[type='text']").val().trim();
			if(tempVal!="" && tempVal!="--"){
				Receptor.push(tempVal);
			}
		}
		var CopyList = [];
		for(var jj=0;jj<temp1;jj++){
			var tempVal2 = $(".u-CC-div").eq(jj).find("input[type='text']").val().trim();
			if(tempVal2!="" && tempVal2!="--"){
				CopyList.push(tempVal2);
			}
		}
		var Subject = $(".m-subject input").val().trim();
		var FactoryID = $(".PSD-email-tem").attr("value");
		var str1 = '';
		var str2 = '';
		var pLen = $(".m-content>div p.wrap-p").length;
		str1+='Hi, Ailin,\n';
		str2+='<span>Hi, Ailin,</span><br><br>';
		for(var pp=0;pp<pLen;pp++){
			var curPTxt = $(".m-content>div p.wrap-p").eq(pp).text();
			str1 += curPTxt+'\n';
			str2 += '<span>'+curPTxt+'</span><br><br>';
		}
		str1+='Thanks for your support.';
		str2+='<span>Thanks for your support.</span>';
		var Content = str1;
		var sendContent = str2;
		console.log("Content");
		console.log(Content);
		console.log("sendContent");
		console.log(sendContent);
		if(Receptor.length != temp){
			$.MsgBox_Unload.Alert("提示","有收件人为空！请检查");
			return;
		}else if(CopyList.length != temp1){
			$.MsgBox_Unload.Alert("提示","有抄送人为空！请检查");
			return;
		}else{
			$.ajax({
		        type: 'GET',
		        url: 'SendPushEmail',
		        data: {
		            Type: Type,
		            Receptor: Receptor,
		            CopyList: CopyList,
		            Subject: Subject,
		            Content: Content,
		            FactoryID: FactoryID,
		            sendContent: sendContent
		        },
		        beforeSend:function(XMLHttpRequest){  
                     $("#PSD-do").prop("disabled","disabled");
                     $("#PSD-do").css("cursor","not-allowed");
                },
		        success: function (data) {
		        	console.log(data);
		        	$.MsgBox.Alert("提示",data);
		        },
		        complete:function(XMLHttpRequest,textStatus){  
                	$("#PSD-do").prop("disabled",false);
                	$("#PSD-do").css("cursor","pointer");
                },
		        error: function () {
		            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
		        }
		    });
		}
	});

	$(document).on("click",".PT-email-td",function(){
		if($(this).text()=="YES"){
			var Type = "PushTracking";
			var FactoryID = $(this).siblings("td.contract-edit").attr("value");
			$.ajax({
		        type: 'POST',
		        url: 'SendPushEmail',
		        data: {
		            Type: Type,
		            FactoryID: FactoryID
		        },
		        contentType: "application/x-www-form-urlencoded; charset=utf-8",
		        dataType:"json",
		        success: function (data) {
		        	console.log(data);
		        	console.log(typeof data);
		        	if(data.length>1){
		        		var CCList = data[1]["CCList"];
		        		var MainBody = data[1]["MainBody"];
		        		var Receptor = data[1]["Receptor"];
		        		var Subject = data[1]["Subject"];
		        		var curPO = $(this).siblings("td.PO").text().trim();
		        		$(".m-subject2").attr("value",curPO);
		        		// var FactoryID1 = $(this).siblings("td.contract-edit").attr("value");
		        		$(".PT-email-tem").attr("value",FactoryID);
		        		$(".MailBar_cover_color").fadeIn(100);
		        		$(".MailBar_cover_color").css("height",$("#originfactory_wrapper").height()+"px");
		        		$(".PT-email-tem").fadeIn(100);
		        		$(".m-recipient2 input").val("");
		        		$(".m-CC2 input").val("");
		        		var ReceptorArr = Receptor.split(";");
		        		if(ReceptorArr.indexOf("")==ReceptorArr.length-1){
		        			ReceptorArr.pop();
		        		}
		        		var insertRowStr1 = '<div class="u-recipient2">'+
					'<div class="u-recipient-div2" style="visibility:visible;"><span class="i-recipient2"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-recipient-div2" style="visibility:hidden;opacity:0;"><span class="i-recipient2"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-recipient-div2" style="visibility:hidden;opacity:0;"><span class="i-recipient2"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
				'</div>';
						var RArrLen = ReceptorArr.length;
						var RArrLen2 = ReceptorArr.length-1;
						$(".u-recipient2").eq(1).remove();
		        		$(".u-recipient2").eq(1).remove();
		        		if(ReceptorArr.length<4){
		        			for(var qq = 0;qq<3;qq++){
		        				$(".u-recipient-div2:eq("+qq+")").find("span.i-add").fadeIn(50);
		        			}
		        		}else if(ReceptorArr.length<7){
		        			$(".m-recipient2").append(insertRowStr1);
		        			for(var qq = 0;qq<6;qq++){
		        				$(".u-recipient-div2:eq("+qq+")").find("span.i-add").fadeIn(50);
		        			}
		        		}else{
		        			$(".m-recipient2").append(insertRowStr1);
		        			$(".m-recipient2").append(insertRowStr1);
		        			for(var qq = 0;qq<9;qq++){
		        				$(".u-recipient-div2:eq("+qq+")").find("span.i-add").fadeIn(50);
		        			}
		        		}
		        		$(".u-recipient-div2:gt("+RArrLen2+")").css({"visibility":"hidden","opacity":"0"});
	        			$(".u-recipient-div2:lt("+RArrLen+")").css({"visibility":"visible","opacity":"1"});
	        			$(".u-recipient-div2:lt("+RArrLen2+")").find("span.i-add").fadeOut(50);
	        			$(".u-recipient-div2:eq("+RArrLen2+")").find("span.i-add").fadeIn(50);
	        			for(var ijj = 0;ijj<RArrLen;ijj++){
	        				$(".u-recipient-div2:eq("+ijj+")").find("input").val(ReceptorArr[ijj]);
	        			}
	        			var temp = 0;
	        			$(".u-recipient-div2").each(function(){
	        			    if($(this).css("visibility")=="visible"){
	        			        temp++;
	        			    }
	        			});
	        			for(var ww = 1;ww<temp;ww++){
	        			    $(".u-recipient-div2").eq(ww).find("span.i-recipient2").text("收件人"+ww);
	        			}

	        			// 抄送人
		        		var CCListArr = CCList.split(";");
		        		if(CCListArr.indexOf("")==CCListArr.length-1){
		        			CCListArr.pop();
		        		}
		        		var insertRowStr2 = '<div class="u-CC2">'+
					'<div class="u-CC-div2" style="visibility:visible;"><span class="i-CC2"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-CC-div2" style="visibility:hidden;opacity:0;"><span class="i-CC2"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-CC-div2" style="visibility:hidden;opacity:0;"><span class="i-CC2"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
				'</div>';
						var CArrLen = CCListArr.length;
						var CArrLen2 = CCListArr.length-1;
						$(".u-CC2").eq(1).remove();
		        		$(".u-CC2").eq(1).remove();
		        		if(CCListArr.length<4){
		        			for(var ee = 0;ee<3;ee++){
		        				$(".u-CC-div2:eq("+ee+")").find("span.i-add").fadeIn(50);
		        			}
		        		}else if(CCListArr.length<7){
		        			$(".m-CC2").append(insertRowStr2);
		        			for(var ee = 0;ee < 6 ; ee++){
		        				$(".u-CC-div2:eq("+ee+")").find("span.i-add").fadeIn(50);
		        			}
		        		}else{
		        			$(".m-CC2").append(insertRowStr2);
		        			$(".m-CC2").append(insertRowStr2);
		        			for(var ee = 0;ee<9;ee++){
		        				$(".u-CC-div2:eq("+ee+")").find("span.i-add").fadeIn(50);
		        			}
		        		}
		        		$(".u-CC-div2:gt("+CArrLen2+")").css({"visibility":"hidden","opacity":"0"});
	        			$(".u-CC-div2:lt("+CArrLen+")").css({"visibility":"visible","opacity":"1"});
	        			$(".u-CC-div2:lt("+CArrLen2+")").find("span.i-add").fadeOut(50);
	        			$(".u-CC-div2:eq("+CArrLen2+")").find("span.i-add").fadeIn(50);
	        			for(var ijjj = 0;ijjj<CArrLen;ijjj++){
	        				$(".u-CC-div2:eq("+ijjj+")").find("input").val(CCListArr[ijjj]);
	        			}
	        			var temp2 = 0;
	        			$(".u-CC-div2").each(function(){
	        			    if($(this).css("visibility")=="visible"){
	        			        temp2++;
	        			    }
	        			});
	        			for(var rr = 1;rr<temp2;rr++){
	        			    $(".u-CC-div2").eq(rr).find("span.i-CC2").text("抄送人"+rr);
	        			}
		        		$(".m-subject2 input").val(Subject);
		        		var wrapCount = globalPlaceholderCount(MainBody);
		        		$(".m-content2 .wrap-p2:gt(0)").remove();
		        		if(wrapCount==2){
		        			var newMainBody = MainBody.split("\n")[1];
		        			$(".m-content2 div p.i-content2").text(newMainBody);
		        		}else if(wrapCount>2){
		        			var newMainBody2 = MainBody.split("\n");
		        			var pStr = '';
		        			for(var LL=2;LL<newMainBody2.length-1;LL++){
		        				pStr+= '<p class="wrap-p2" contenteditable="true"></p>';
		        			}
		        			$("p.i-content2").after(pStr);
		        			$("p.i-content2").text(newMainBody2[1]);
		        			for(var LLL=1;LLL<newMainBody2.length-2;LLL++){
		        				$(".m-content2 .wrap-p2:eq("+LLL+")").text(newMainBody2[LLL+1]);
		        			}
		        		}
		        	}
		        },
		        error: function () {
		            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
		        }
		    });

		}else if($(this).text()=="NO"){
			var curPO = $(this).siblings("td.PO").text().trim();
			$(".m-subject2").attr("value",curPO);
			var FactoryID2 = $(this).siblings("td.contract-edit").attr("value");
			$(".PT-email-tem").attr("value",FactoryID2);
			$(".MailBar_cover_color").fadeIn(100);
			$(".MailBar_cover_color").css("height",$("#originfactory_wrapper").height()+"px");
			$(".PT-email-tem").fadeIn(100);
			$(".m-recipient2 input").val("");
			$(".m-CC2 input").val("");
			$(".u-recipient2").eq(1).remove();
			$(".u-recipient2").eq(1).remove();
			$(".u-recipient-div2:gt(0)").css({"visibility":"hidden","opacity":"0"});
			$(".u-recipient-div2:eq(0)").find("span.i-add").fadeIn(50);
			$(".u-recipient-div2:eq(0)").find("input").val("AChen3@formfactor.com");
			$(".u-CC2").eq(1).remove();
			$(".u-CC2").eq(1).remove();
			$(".u-CC-div2:eq(0)").find("span.i-add").fadeOut(50);
			$(".u-CC-div2:eq(1)").find("span.i-add").fadeOut(50);
			$(".u-CC-div2:eq(2)").find("span.i-add").fadeIn(50);
			$(".u-CC-div2:eq(0)").find("input").val("jiangyaping@eoulu.com");
			$(".u-CC-div2:eq(1)").find("input").val("zhaona@eoulu.com");
			$(".u-CC-div2:eq(2)").find("input").val("zhaowenzhen@eoulu.com");
			$(".m-subject2 input").val("Please help update the Tracking NO. of "+curPO+" within today");
			$(".m-content2 div p:nth-child(1)").text("Hi, Ailin,");
			$(".m-content2 .i-content2").text("We are really urgent. Would you please help update the Tracking NO. of "+curPO+" within today?");
			$(".m-content2 .wrap-p2:gt(0)").remove();
		}
	});

	$(document).on("click","#PT-do",function(){
		var Type = "PushTracking";
		var temp = 0;
        $(".u-recipient-div2").each(function(){
            if($(this).css("visibility")=="visible"){
                temp++;
            }
        });
        var temp1 = 0;
        $(".u-CC-div2").each(function(){
            if($(this).css("visibility")=="visible"){
                temp1++;
            }
        });
		var Receptor = [];
		for(var ii=0;ii<temp;ii++){
			var tempVal = $(".u-recipient-div2").eq(ii).find("input[type='text']").val().trim();
			if(tempVal!="" && tempVal!="--"){
				Receptor.push(tempVal);
			}
		}
		var CopyList = [];
		for(var jj=0;jj<temp1;jj++){
			var tempVal2 = $(".u-CC-div2").eq(jj).find("input[type='text']").val().trim();
			if(tempVal2!="" && tempVal2!="--"){
				CopyList.push(tempVal2);
			}
		}
		var Subject = $(".m-subject2 input").val().trim();
		var FactoryID = $(".PT-email-tem").attr("value");
		var str1 = '';
		var str2 = '';
		var pLen = $(".m-content2>div p.wrap-p2").length;
		str1+='Hi, Ailin,\n';
		str2+='<span>Hi, Ailin,</span><br><br>';
		for(var pp=0;pp<pLen;pp++){
			var curPTxt = $(".m-content2>div p.wrap-p2").eq(pp).text();
			str1 += curPTxt+'\n';
			str2 += '<span>'+curPTxt+'</span><br><br>';
		}
		str1+='Thanks for your support.';
		str2+='<span>Thanks for your support.</span>';
		var Content = str1;
		var sendContent = str2;
		console.log("Content");
		console.log(Content);
		console.log("sendContent");
		console.log(sendContent);
		if(Receptor.length != temp){
			$.MsgBox_Unload.Alert("提示","有收件人为空！请检查");
			return;
		}else if(CopyList.length != temp1){
			$.MsgBox_Unload.Alert("提示","有抄送人为空！请检查");
			return;
		}else{
			$.ajax({
		        type: 'GET',
		        url: 'SendPushEmail',
		        data: {
		            Type: Type,
		            Receptor: Receptor,
		            CopyList: CopyList,
		            Subject: Subject,
		            Content: Content,
		            FactoryID: FactoryID,
		            sendContent: sendContent
		        },
		        beforeSend:function(XMLHttpRequest){  
                     $("#PT-do").prop("disabled","disabled");
                     $("#PT-do").css("cursor","not-allowed");
                },
		        success: function (data) {
		        	console.log(data);
		        	$.MsgBox.Alert("提示",data);
		        },
		        complete:function(XMLHttpRequest,textStatus){  
                    $("#PT-do").prop("disabled",false);
                    $("#PT-do").css("cursor","pointer");
                },
		        error: function () {
		            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
		        }
		    });
		}
	});

	$(document).on("keypress","p.wrap-p",function(event){
		var keynum = (event.keyCode ? event.keyCode : event.which);
		if (keynum == '13') {
			event.preventDefault();
			var str = '<p class="wrap-p" contenteditable="true"></p>';
			$(this).after(str);
			$(this).next().focus();
		}
	});
	$(document).on("keydown","p.wrap-p:not('.i-content')",function(event){
		var keynum = (event.keyCode ? event.keyCode : event.which);
		if(keynum == '8'){
			// alert("222");
			if($(this).text()==""){
				$(this).prev().focus();
				$(this).remove();
			}
		}
	});
	$(window).on("resize",function(){
		percentWAlertResponse(".PSD-email-tem",100);
		percentWAlertResponse(".PT-email-tem",100);
		percentWAlertResponse(".Inform-email-tem",100);
		var iW1 = Math.abs($(window).width() - $(".InformSel").width())/2;
		$(".InformSel").css("left",iW1+"px");
	});
	$(".PSD-email-tem-tit-r, #PSD-can").on("click",function(){
		$(".PSD-email-tem").fadeOut(100);
		$(".MailBar_cover_color").fadeOut(100);
	});

	$(document).on("keypress","p.wrap-p2",function(event){
		var keynum = (event.keyCode ? event.keyCode : event.which);
		if (keynum == '13') {
			event.preventDefault();
			var str = '<p class="wrap-p2" contenteditable="true"></p>';
			$(this).after(str);
			$(this).next().focus();
		}
	});
	$(document).on("keydown","p.wrap-p2:not('.i-content2')",function(event){
		var keynum = (event.keyCode ? event.keyCode : event.which);
		if(keynum == '8'){
			// alert("222");
			if($(this).text()==""){
				$(this).prev().focus();
				$(this).remove();
			}
		}
	});
	$(".PT-email-tem-tit-r, #PT-can").on("click",function(){
		$(".PT-email-tem").fadeOut(100);
		$(".MailBar_cover_color").fadeOut(100);
	});

	// To CC 添加删除
	$(document).on("click",".m-recipient span.i-add",function(){
		var insertRowStr = '<div class="u-recipient">'+
					'<div class="u-recipient-div" style="visibility:visible;"><span class="i-recipient"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-recipient-div" style="visibility:hidden;opacity:0;"><span class="i-recipient"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-recipient-div" style="visibility:hidden;opacity:0;"><span class="i-recipient"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
				'</div>';
		globalToCCAdd(".m-recipient",".u-recipient",".u-recipient-div","span.i-add",9,3,insertRowStr,"span.i-recipient","收件人");
	});
	$(document).on("click",".m-CC span.i-add",function(){
		var insertRowStr = '<div class="u-CC">'+
					'<div class="u-CC-div" style="visibility:visible;"><span class="i-CC"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-CC-div" style="visibility:hidden;opacity:0;"><span class="i-CC"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-CC-div" style="visibility:hidden;opacity:0;"><span class="i-CC"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
				'</div>';
		globalToCCAdd(".m-CC",".u-CC",".u-CC-div","span.i-add",9,3,insertRowStr,"span.i-CC","抄送人");
	});

	// 删除
	$(document).on("click",".m-recipient span.i-del",function(){
		var delVal = $(this).siblings("input[type='text']").val();
		// alert(delVal);
		globalToCCDel(".u-recipient",".u-recipient-div",3,delVal,"span.i-recipient","收件人");
	});
	$(document).on("click",".m-CC span.i-del",function(){
		var delVal = $(this).siblings("input[type='text']").val();
		// alert(delVal);
		globalToCCDel(".u-CC",".u-CC-div",3,delVal,"span.i-CC","抄送人");
	});


	// Push Tracking 添加 TO CC
	$(document).on("click",".m-recipient2 span.i-add",function(){
		var insertRowStr = '<div class="u-recipient2">'+
					'<div class="u-recipient-div2" style="visibility:visible;"><span class="i-recipient2"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-recipient-div2" style="visibility:hidden;opacity:0;"><span class="i-recipient2"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-recipient-div2" style="visibility:hidden;opacity:0;"><span class="i-recipient2"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
				'</div>';
		globalToCCAdd(".m-recipient2",".u-recipient2",".u-recipient-div2","span.i-add",9,3,insertRowStr,"span.i-recipient2","收件人");
	});
	$(document).on("click",".m-CC2 span.i-add",function(){
		var insertRowStr = '<div class="u-CC2">'+
					'<div class="u-CC-div2" style="visibility:visible;"><span class="i-CC2"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-CC-div2" style="visibility:hidden;opacity:0;"><span class="i-CC2"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-CC-div2" style="visibility:hidden;opacity:0;"><span class="i-CC2"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
				'</div>';
		globalToCCAdd(".m-CC2",".u-CC2",".u-CC-div2","span.i-add",9,3,insertRowStr,"span.i-CC2","抄送人");
	});
	// 删除
	$(document).on("click",".m-recipient2 span.i-del",function(){
		var delVal = $(this).siblings("input[type='text']").val();
		// alert(delVal);
		globalToCCDel(".u-recipient2",".u-recipient-div2",3,delVal,"span.i-recipient2","收件人");
	});
	$(document).on("click",".m-CC2 span.i-del",function(){
		var delVal = $(this).siblings("input[type='text']").val();
		// alert(delVal);
		globalToCCDel(".u-CC2",".u-CC-div2",3,delVal,"span.i-CC2","抄送人");
	});


	/*仓库地址选择*/
	function hasSendTnformShow(){
		$("div.m-title3 span").fadeIn(100);
		$("div.m-title3 button").fadeIn(100);
	}
	function hasSendTnformClose(){
		$("div.m-title3 span").fadeOut(100);
		$("div.m-title3 button").fadeOut(100);
	}
	$(document).on("click","#table1 .Inform",function(){
		var Inform = $(this).text().trim();
		var trackingNo = $(this).siblings("td.TrackingNO").text().trim();
		if(trackingNo.indexOf("--")==0){
			trackingNo = trackingNo.split("--")[1];
		}
		var FactoryID3 = $(this).siblings("td.contract-edit").attr("value");
		var InformNew = globalDataHandle(Inform,"");
		var trackingNoNew = globalDataHandle(trackingNo,"");
		var iW = Math.abs($(window).width() - $(".InformSel").width())/2;
		$(".InformSel").css("left",iW+"px");
		if(InformNew=="" && trackingNoNew!=""){
			$(".MailBar_cover_color").fadeIn(100);
			$(".MailBar_cover_color").css("height",$("#originfactory_wrapper").height()+"px");
        	$(".InformSel").attr("value",trackingNoNew);
			$(".InformSel").fadeIn(100);
			$(".Inform-email-tem").attr("value",FactoryID3);
			hasSendTnformClose();
		}else if(InformNew=="" && trackingNoNew==""){
			$(".InformSel").attr("value","");
			$(".InformSel").fadeOut(100);
			$(".Inform-email-tem").fadeOut(100);
			$(".Inform-email-tem").attr("value","");
			$.MsgBox_Unload.Alert("提示","当前trackingNo为空");
			hasSendTnformClose();
		}else if(InformNew!="" && trackingNoNew!=""){
			// $(".Inform-email-tem-tit").attr("value",InformSelVal);
			var Type = "Inform";
			var FactoryID = $(this).siblings("td.contract-edit").attr("value");
			$.ajax({
		        type: 'POST',
		        url: 'SendPushEmail',
		        data: {
		            Type: Type,
		            FactoryID: FactoryID
		        },
		        contentType: "application/x-www-form-urlencoded; charset=utf-8",
		        dataType:"json",
		        success: function (data) {
		        	console.log(data);
		        	console.log(typeof data);

		        	if(data.length>1){
		        		var CCList = data[1]["CCList"];
		        		var MainBody = data[1]["MainBody"];
		        		var Receptor = data[1]["Receptor"];
		        		var Subject = data[1]["Subject"];

		        		$(".Inform-email-tem").attr("value",FactoryID);
		        		$(".Inform-email-tem-tit").attr("value",InformNew);
		        		$(".MailBar_cover_color").fadeIn(100);
		        		$(".MailBar_cover_color").css("height",$("#originfactory_wrapper").height()+"px");
        				$(".InformSel").attr("value",trackingNoNew);
		        		$(".Inform-email-tem").fadeIn(100);
						hasSendTnformShow();
		        		$(".m-recipient3 input").val("");
		        		$(".m-CC3 input").val("");
		        		var ReceptorArr = Receptor.split(";");
		        		if(ReceptorArr.indexOf("")==ReceptorArr.length-1){
		        			ReceptorArr.pop();
		        		}
		        		var insertRowStr1 = '<div class="u-recipient3">'+
					'<div class="u-recipient-div3" style="visibility:visible;"><span class="i-recipient3"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-recipient-div3" style="visibility:hidden;opacity:0;"><span class="i-recipient3"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-recipient-div3" style="visibility:hidden;opacity:0;"><span class="i-recipient3"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
				'</div>';
						var RArrLen = ReceptorArr.length;
						var RArrLen2 = ReceptorArr.length-1;
						$(".u-recipient3").eq(1).remove();
		        		$(".u-recipient3").eq(1).remove();
		        		if(ReceptorArr.length<4){
		        			for(var qq = 0;qq<3;qq++){
		        				$(".u-recipient-div3:eq("+qq+")").find("span.i-add").fadeIn(50);
		        			}
		        		}else if(ReceptorArr.length<7){
		        			$(".m-recipient3").append(insertRowStr1);
		        		}else{
		        			$(".m-recipient3").append(insertRowStr1);
		        			$(".m-recipient3").append(insertRowStr1);
		        		}
		        		$(".u-recipient-div3:gt("+RArrLen2+")").css({"visibility":"hidden","opacity":"0"});
	        			$(".u-recipient-div3:lt("+RArrLen+")").css({"visibility":"visible","opacity":"1"});
	        			$(".u-recipient-div3:lt("+RArrLen2+")").find("span.i-add").fadeOut(50);
	        			$(".u-recipient-div3:eq("+RArrLen2+")").find("span.i-add").fadeIn(50);
	        			for(var ijj = 0;ijj<RArrLen;ijj++){
	        				$(".u-recipient-div3:eq("+ijj+")").find("input").val(ReceptorArr[ijj]);
	        			}
	        			var temp = 0;
	        			$(".u-recipient-div3").each(function(){
	        			    if($(this).css("visibility")=="visible"){
	        			        temp++;
	        			    }
	        			});
	        			for(var ww = 1;ww<temp;ww++){
	        			    $(".u-recipient-div3").eq(ww).find("span.i-recipient3").text("收件人"+ww);
	        			}

	        			// 抄送人
		        		var CCListArr = CCList.split(";");
		        		if(CCListArr.indexOf("")==CCListArr.length-1){
		        			CCListArr.pop();
		        		}
		        		var insertRowStr2 = '<div class="u-CC3">'+
					'<div class="u-CC-div3" style="visibility:visible;"><span class="i-CC3"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-CC-div3" style="visibility:hidden;opacity:0;"><span class="i-CC3"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-CC-div3" style="visibility:hidden;opacity:0;"><span class="i-CC3"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
				'</div>';
						var CArrLen = CCListArr.length;
						var CArrLen2 = CCListArr.length-1;
						$(".u-CC3").eq(1).remove();
		        		$(".u-CC3").eq(1).remove();
		        		if(CCListArr.length<4){
		        			for(var ee = 0;ee<3;ee++){
		        				$(".u-CC-div3:eq("+ee+")").find("span.i-add").fadeIn(50);
		        			}
		        		}else if(CCListArr.length<7){
		        			$(".m-CC3").append(insertRowStr2);
		        		}else{
		        			$(".m-CC3").append(insertRowStr2);
		        			$(".m-CC3").append(insertRowStr2);
		        		}
		        		$(".u-CC-div3:gt("+CArrLen2+")").css({"visibility":"hidden","opacity":"0"});
	        			$(".u-CC-div3:lt("+CArrLen+")").css({"visibility":"visible","opacity":"1"});
	        			$(".u-CC-div3:lt("+CArrLen2+")").find("span.i-add").fadeOut(50);
	        			$(".u-CC-div3:eq("+CArrLen2+")").find("span.i-add").fadeIn(50);
	        			for(var ijjj = 0;ijjj<CArrLen;ijjj++){
	        				$(".u-CC-div3:eq("+ijjj+")").find("input").val(CCListArr[ijjj]);
	        			}
	        			var temp2 = 0;
	        			$(".u-CC-div3").each(function(){
	        			    if($(this).css("visibility")=="visible"){
	        			        temp2++;
	        			    }
	        			});
	        			for(var rr = 1;rr<temp2;rr++){
	        			    $(".u-CC-div3").eq(rr).find("span.i-CC3").text("抄送人"+rr);
	        			}

	        			// $(".m-subject3 input").val(Inform+" 收货通知：运单号FEDEX "+trackingNoNew+" 入贵司仓库，请帮忙查收！");
	        			// $(".m-content3 div p:nth-child(1)").text("您好！");
	        			// $(".m-content3 .i-content3").text("我司有一包裹运单号FEDEX "+trackingNoNew+"，近期会入贵司仓库，请帮忙查收！");
	        			// $(".m-content3 .wrap-p3:gt(0)").remove();

	        			$(".m-subject3 input").val(Subject);
	        			var wrapCount = globalPlaceholderCount(MainBody);
	        			$(".m-content3 .wrap-p3:gt(0)").remove();
	        			if(wrapCount==2){
	        				var newMainBody = MainBody.split("\n")[1];
	        				$(".m-content3 div p.i-content3").text(newMainBody);
	        			}else if(wrapCount>2){
		        			var newMainBody2 = MainBody.split("\n");
		        			var pStr = '';
		        			for(var LL=2;LL<newMainBody2.length-1;LL++){
		        				pStr+= '<p class="wrap-p3" contenteditable="true"></p>';
		        			}
		        			$("p.i-content3").after(pStr);
		        			$("p.i-content3").text(newMainBody2[1]);
		        			for(var LLL=1;LLL<newMainBody2.length-2;LLL++){
		        				$(".m-content3 .wrap-p3:eq("+LLL+")").text(newMainBody2[LLL+1]);
		        			}
		        		}

	        			$(".InformSel").attr("value",trackingNoNew);
	        			$(".InformSel").fadeOut(100);
		        		// $(".m-content2 div").html("").html(MainBody);
		        	}
		        },
		        error: function () {
		            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
		        }
		    });

		}
	});
	$(document).on("click",".m-title3 button",function(){
		$(".Inform-email-tem").fadeOut(100);
	    $(".InformSel").fadeIn(100);
	    hasSendTnformClose();
	});
	$(document).on("change","#InformSel-body-sel",function(){
		$("#InformSel-body-sel").attr("name",$(this).find("option:checked").attr("name"));
		var trackingNo = $(".InformSel").attr("value");
		var InformSelVal = $(this).val();
		var index = globalFFWarehouse.indexOf(InformSelVal);
		var InformSelName = $(this).attr("name");
		// alert(InformSelVal+":"+index+":"+InformSelName);
		if(index>0 && index==InformSelName && InformSelVal!=globalFFWarehouse[0]){
			$(".m-recipient3 input").val("");
			$(".m-CC3 input").val("");
			$(".InformSel").fadeOut(100);
			$(".MailBar_cover_color").fadeIn(100);
			$(".Inform-email-tem-tit").attr("value",InformSelVal);
			var TOCCObj = globalFFWarehouseTOCC[index];
			var TO = TOCCObj["TO"];
			var CC = TOCCObj["CC"];
			$(".Inform-email-tem").fadeIn(100);
			$(".u-recipient3").eq(1).remove();
			$(".u-recipient3").eq(1).remove();
			var TOLast = TO.length - 1;
			$(".u-recipient-div3:eq(0)").find("span.i-add").fadeIn(50);
			$(".u-recipient-div3:eq(1)").find("span.i-add").fadeIn(50);
			$(".u-recipient-div3:eq(2)").find("span.i-add").fadeIn(50);
			$(".u-recipient-div3:gt("+TOLast+")").css({"visibility":"hidden","opacity":"0"});
			$(".u-recipient-div3:lt("+TO.length+")").css({"visibility":"visible","opacity":"1"});
			$(".u-recipient-div3:lt("+TOLast+")").find("span.i-add").fadeOut(50);
			$(".u-recipient-div3:eq("+TOLast+")").find("span.i-add").fadeIn(50);
			for(var ij = 0;ij<TO.length;ij++){
				$(".u-recipient-div3:eq("+ij+")").find("input").val(TO[ij]);
			}
			$(".u-CC").eq(1).remove();
			$(".u-CC").eq(1).remove();
			var CCLast = CC.length - 1;
			$(".u-CC-div3:eq(0)").find("span.i-add").fadeIn(50);
			$(".u-CC-div3:eq(1)").find("span.i-add").fadeIn(50);
			$(".u-CC-div3:eq(2)").find("span.i-add").fadeIn(50);
			$(".u-CC-div3:gt("+CCLast+")").css({"visibility":"hidden","opacity":"0"});
			$(".u-CC-div3:lt("+CC.length+")").css({"visibility":"visible","opacity":"1"});
			$(".u-CC-div3:lt("+CCLast+")").find("span.i-add").fadeOut(50);
			$(".u-CC-div3:eq("+CCLast+")").find("span.i-add").fadeIn(50);
			for(var ijj = 0;ijj<CC.length;ijj++){
				$(".u-CC-div3:eq("+ijj+")").find("input").val(CC[ijj]);
			}

			$(".m-subject3 input").val(InformSelVal+" 收货通知：运单号FEDEX "+trackingNo+" 入贵司仓库，请帮忙查收！");
			$(".m-content3 div p:nth-child(1)").text("您好！");
			$(".m-content3 .i-content3").text("我司有一包裹运单号FEDEX "+trackingNo+"，近期会入贵司仓库，请帮忙查收！");
			$(".m-content3 .wrap-p3:gt(0)").remove();
		}else{
			$(".Inform-email-tem-tit").attr("value","");
		}
	});
	// 仓库TO\CC添加删除
	$(document).on("click",".m-recipient3 span.i-add",function(){
		var insertRowStr = '<div class="u-recipient3">'+
					'<div class="u-recipient-div3" style="visibility:visible;"><span class="i-recipient3"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-recipient-div3" style="visibility:hidden;opacity:0;"><span class="i-recipient3"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-recipient-div3" style="visibility:hidden;opacity:0;"><span class="i-recipient3"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
				'</div>';
		globalToCCAdd(".m-recipient3",".u-recipient3",".u-recipient-div3","span.i-add",9,3,insertRowStr,"span.i-recipient3","收件人");
	});
	$(document).on("click",".m-CC3 span.i-add",function(){
		var insertRowStr = '<div class="u-CC3">'+
					'<div class="u-CC-div3" style="visibility:visible;"><span class="i-CC3"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-CC-div3" style="visibility:hidden;opacity:0;"><span class="i-CC3"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
					'<div class="u-CC-div3" style="visibility:hidden;opacity:0;"><span class="i-CC3"></span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>'+
				'</div>';
		globalToCCAdd(".m-CC3",".u-CC3",".u-CC-div3","span.i-add",9,3,insertRowStr,"span.i-CC3","抄送人");
	});

	// 删除
	$(document).on("click",".m-recipient3 span.i-del",function(){
		var delVal = $(this).siblings("input[type='text']").val();
		// alert(delVal);
		globalToCCDel(".u-recipient3",".u-recipient-div3",3,delVal,"span.i-recipient3","收件人");
	});
	$(document).on("click",".m-CC3 span.i-del",function(){
		var delVal = $(this).siblings("input[type='text']").val();
		// alert(delVal);
		globalToCCDel(".u-CC3",".u-CC-div3",3,delVal,"span.i-CC3","抄送人");
	});

	$(document).on("click","#Inform-do",function(){
		var Type = "Inform";
		var temp = 0;
        $(".u-recipient-div3").each(function(){
            if($(this).css("visibility")=="visible"){
                temp++;
            }
        });
        var temp1 = 0;
        $(".u-CC-div3").each(function(){
            if($(this).css("visibility")=="visible"){
                temp1++;
            }
        });
		var Receptor = [];
		for(var ii=0;ii<temp;ii++){
			var tempVal = $(".u-recipient-div3").eq(ii).find("input[type='text']").val().trim();
			if(tempVal!="" && tempVal!="--"){
				Receptor.push(tempVal);
			}
		}
		var CopyList = [];
		for(var jj=0;jj<temp1;jj++){
			var tempVal2 = $(".u-CC-div3").eq(jj).find("input[type='text']").val().trim();
			if(tempVal2!="" && tempVal2!="--"){
				CopyList.push(tempVal2);
			}
		}
		var Subject = $(".m-subject3 input").val().trim();
		var FactoryID = $(".Inform-email-tem").attr("value");
		var str1 = '';
		var str2 = '';
		var pLen = $(".m-content3>div p.wrap-p3").length;
		str1+='您好！\n';
		str2+='<span>您好！</span><br><br>';
		for(var pp=0;pp<pLen;pp++){
			var curPTxt = $(".m-content3>div p.wrap-p3").eq(pp).text();
			str1 += curPTxt+'\n';
			str2 += '<span>'+curPTxt+'</span><br><br>';
		}
		str1+='烦请仓库签收后提供签收单和随货单据扫描件，非常感谢！';
		str2+='<span>烦请仓库签收后提供签收单和随货单据扫描件，非常感谢！</span>';
		var Content = str1;
		var sendContent = str2;
		console.log("Content");
		console.log(Content);
		console.log("sendContent");
		console.log(sendContent);
		var Address = $(".Inform-email-tem-tit").attr("value");
		if(Receptor.length != temp){
			$.MsgBox_Unload.Alert("提示","有收件人为空！请检查");
			return;
		}else if(CopyList.length != temp1){
			$.MsgBox_Unload.Alert("提示","有抄送人为空！请检查");
			return;
		}else{
			$.ajax({
		        type: 'GET',
		        url: 'SendPushEmail',
		        data: {
		            Type: Type,
		            Receptor: Receptor,
		            CopyList: CopyList,
		            Subject: Subject,
		            Content: Content,
		            FactoryID: FactoryID,
		            Address: Address,
		            sendContent: sendContent
		        },
		        beforeSend:function(XMLHttpRequest){  
                    $("#Inform-do").prop("disabled","disabled");
                    $("#Inform-do").css("cursor","not-allowed");
                },
		        success: function (data) {
		        	console.log(data);
		        	$.MsgBox.Alert("提示",data);
		        },
		        complete:function(XMLHttpRequest,textStatus){  
                    $("#Inform-do").prop("disabled",false);
                    $("#Inform-do").css("cursor","pointer");
                },
		        error: function () {
		            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
		        }
		    });
		}
	});

	$(document).on("click",".InformSel-tit-r",function(){
		$(".InformSel").fadeOut(100);
		$(".MailBar_cover_color").fadeOut(100);
	});

	$(document).on("keypress","p.wrap-p3",function(event){
		var keynum = (event.keyCode ? event.keyCode : event.which);
		if (keynum == '13') {
			event.preventDefault();
			var str = '<p class="wrap-p3" contenteditable="true"></p>';
			$(this).after(str);
			$(this).next().focus();
		}
	});
	$(document).on("keydown","p.wrap-p3:not('.i-content3')",function(event){
		var keynum = (event.keyCode ? event.keyCode : event.which);
		if(keynum == '8'){
			// alert("222");
			if($(this).text()==""){
				$(this).prev().focus();
				$(this).remove();
			}
		}
	});
	$(".Inform-email-tem-tit-r, #Inform-can").on("click",function(){
		$(".Inform-email-tem").fadeOut(100);
		$(".MailBar_cover_color").fadeOut(100);
	});

});

//上传和下载功能
$(".up_filesoa").click(function(){
	// alert($(".MailBar #Mail_fileToUpload").val());
	$(".MailBar_cover_color_file").show();
	$(".MailBar").show();
	$(".MailBar #Mail_fileToUpload").val("");
	
		$("#Mail_Send").on("click",function(){
			var  ID = $(".contract_update .contract_title").attr("value");
			/*alert(ID)*/
			
			var v1 = $(".MailBar #Mail_fileToUpload").val();
			
			if(v1 == ''){
				$.MsgBox_Unload.Alert("提示", "请检查是否选择文件！");
				return;
			}
			var SoaFileName = v1.split("\\").pop();
			// alert(SoaFileName);
			$.ajaxFileUpload({  
		        url:'upLoad',
		        secureuri:false,  
		        fileElementId:'Mail_fileToUpload',//file标签的id  
		        dataType: 'JSON',//返回数据的类型  
		        data:{
		        	ID : ID,
		        	Type : 1,
		        	fileName : SoaFileName
		        },//一同上传的数据  
		        success: function (data) {
		        	console.log(data);
		        	if(data){
		           		$.MsgBox_Unload.Alert("提示", "上传成功！");	
		           		$("#Mail_Send").unbind("click");
		           		$(".MailBar").hide();
		           		$(".MailBar_cover_color_file").hide();
		        	}else{
		        		$.MsgBox_Unload.Alert("提示", "上传失败请稍后再试！");
		        	}
		        },  
		        error: function (e) {  
		            alert("error");  
		        }  
		    }); 
			
		});
});
$(".up_filebafa").click(function(){
	// alert($(".MailBar #Mail_fileToUpload").val());
	$(".MailBar_cover_color_file").show();
	$(".MailBar").show();
	$(".MailBar #Mail_fileToUpload").val("");
	
		$("#Mail_Send").on("click",function(){
			var  ID = $(".contract_update .contract_title").attr("value");
			/*alert(ID)*/
			
			var v1 = $(".MailBar #Mail_fileToUpload").val();
			
			if(v1 == ''){
				$.MsgBox_Unload.Alert("提示", "请检查是否选择文件！");
				return;
			}
			var BafaFileName = v1.split("\\").pop();
			// alert(BafaFileName);
			$.ajaxFileUpload({  
		        url:'upLoad',
		        secureuri:false,  
		        fileElementId:'Mail_fileToUpload',//file标签的id  
		        dataType: 'JSON',//返回数据的类型  
		        data:{
		        	ID : ID,
		        	Type : 2,
		        	fileName : BafaFileName
		        },//一同上传的数据  
		        success: function (data) {
		        	console.log(data);
		        	if(data){
		           		$.MsgBox_Unload.Alert("提示", "上传成功！");	
		           		$("#Mail_Send").unbind("click");
		           		$(".MailBar").hide();
		           		$(".MailBar_cover_color_file").hide();
		        	}else{
		        		$.MsgBox_Unload.Alert("提示", "上传失败请稍后再试！");
		        	}
		        },  
		        error: function (e) {  
		            alert("error");  
		        }  
		    }); 
			
		});
});

$(".up_file-invoice").on("click",function(){
	// alert($(".MailBar #Mail_fileToUpload").val());
	$(".MailBar_cover_color_file").show();
	$(".MailBar").show();
	$(".MailBar #Mail_fileToUpload").val("");
	
		$("#Mail_Send").on("click",function(){
			var  ID = $(".contract_update .contract_title").attr("value");
			/*alert(ID)*/
			
			var v1 = $(".MailBar #Mail_fileToUpload").val();
			
			if(v1 == ''){
				$.MsgBox_Unload.Alert("提示", "请检查是否选择文件！");
				return;
			}
			var InvoiceFileName = $(".MailBar #Mail_fileToUpload").val().split("\\").pop();
			$.ajaxFileUpload({  
		        url:'upLoad',
		        secureuri:false,  
		        fileElementId:'Mail_fileToUpload',//file标签的id  
		        dataType: 'JSON',//返回数据的类型  
		        data:{
		        	ID : ID,
		        	Type : 3,
		        	fileName : InvoiceFileName
		        },//一同上传的数据  
		        success: function (data) {
		        	console.log(data);
		        	if(data){
		        		$("#InvoiceFile").removeAttr("readonly");
		        		$("#InvoiceFile").val(InvoiceFileName);
		        		$("#InvoiceFile").attr("readonly","readonly");
		           		$.MsgBox_Unload.Alert("提示", "上传成功！");
		           		$("#Mail_Send").unbind("click");	
		           		$(".MailBar").hide();
		           		$(".MailBar_cover_color_file").hide();
		        	}else{
		        		$.MsgBox_Unload.Alert("提示", "上传失败请稍后再试！");
		        	}
		        },  
		        error: function (e) {  
		            alert("error");  
		        }  
		    }); 
		});
});

$(".up_file-invoice2").on("click",function(){
	// alert($(".MailBar #Mail_fileToUpload").val());
	$(".MailBar_cover_color_file").show();
	$(".MailBar").show();
	$(".MailBar #Mail_fileToUpload").val("");
	
		$("#Mail_Send").on("click",function(){
			var ID = $(".contract_update .contract_title").attr("value");
			var v1 = $(".MailBar #Mail_fileToUpload").val();
			if(v1 == ''){
				$.MsgBox_Unload.Alert("提示", "请检查是否选择文件！");
				return;
			}
			var InvoiceFileName = $(".MailBar #Mail_fileToUpload").val().split("\\").pop();
			$.ajaxFileUpload({  
		        url:'upLoad',
		        secureuri:false,  
		        fileElementId:'Mail_fileToUpload',//file标签的id  
		        dataType: 'JSON',//返回数据的类型  
		        data:{
		        	ID : ID,
		        	Type : 4,
		        	fileName : InvoiceFileName
		        },//一同上传的数据  
		        success: function (data) {
		        	console.log(data);
		        	if(data){
		        		$("#InvoiceFile2").val(InvoiceFileName);
		           		$.MsgBox_Unload.Alert("提示", "上传成功！");
		           		$("#Mail_Send").unbind("click");	
		           		$(".MailBar").hide();
		           		$(".MailBar_cover_color_file").hide();
		        	}else{
		        		$.MsgBox_Unload.Alert("提示", "上传失败请稍后再试！");
		        	}
		        },  
		        error: function (e) {  
		            alert("error");  
		        }  
		    }); 
		});
});

$(".up_file-invoice3").on("click",function(){
	// alert($(".MailBar #Mail_fileToUpload").val());
	$(".MailBar_cover_color_file").show();
	$(".MailBar").show();
	$(".MailBar #Mail_fileToUpload").val("");
	
		$("#Mail_Send").on("click",function(){
			var ID = $(".contract_update .contract_title").attr("value");
			var v1 = $(".MailBar #Mail_fileToUpload").val();
			if(v1 == ''){
				$.MsgBox_Unload.Alert("提示", "请检查是否选择文件！");
				return;
			}
			var InvoiceFileName = $(".MailBar #Mail_fileToUpload").val().split("\\").pop();
			$.ajaxFileUpload({  
		        url:'upLoad',
		        secureuri:false,  
		        fileElementId:'Mail_fileToUpload',//file标签的id  
		        dataType: 'JSON',//返回数据的类型  
		        data:{
		        	ID : ID,
		        	Type : 5,
		        	fileName : InvoiceFileName
		        },//一同上传的数据  
		        success: function (data) {
		        	console.log(data);
		        	if(data){
		        		$("#InvoiceFile3").val(InvoiceFileName);
		           		$.MsgBox_Unload.Alert("提示", "上传成功！");
		           		$("#Mail_Send").unbind("click");	
		           		$(".MailBar").hide();
		           		$(".MailBar_cover_color_file").hide();
		        	}else{
		        		$.MsgBox_Unload.Alert("提示", "上传失败请稍后再试！");
		        	}
		        },  
		        error: function (e) {  
		            alert("error");  
		        }  
		    }); 
		});
});

	
$('.MailBar_close').click(function () {
	$("#Mail_Send").unbind("click");
	  $('.MailBar_cover_color_file').hide();
	  $('.MailBar').hide();
});
	

$(".down_filesoa").click(function(){
	var tr=$(this).parent().parent();
	var  ID = tr.find('td').eq(0).attr("value");
	$.ajax({
	        type: 'post',
	        url: 'dwonLoad',
	        data: {
	            ID :ID,
	            Type : 1
	        },
	        success: function (data) {
	        	console.log(data);
	        	if(data != 'no'){
		        	console.log(data);
		        	window.location.href=data;
		        	// window.open(data);
	        	}else{
	        		 $.MsgBox.Alert("提示", "没有文件可下载！");
	        	}
	        },
	        error: function () {
	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    });
});
$(".down_filebafa").click(function(){
		var tr=$(this).parent().parent();
		var  ID = tr.find('td').eq(0).attr("value");
		/*alert(ID)*/
	
	$.ajax({
	        type: 'post',
	        url: 'dwonLoad',
	        data: {
	            ID :ID,
	            Type : 2
	        },
	        success: function (data) {
	        	if(data != 'no'){
		        	console.log(data);
		        	window.location.href=data;
		        	// window.open(data);
	        	}else{
	        		 $.MsgBox.Alert("提示", "没有文件可下载！");
	        	}
	        },
	        error: function () {
	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    });
});
// 发票文件下载
$(".InvoiceFile1_td").click(function(){
	if($(this).text()=="无"){
		return false;
	}
		var tr=$(this).parent().parent();
		var ID = tr.find('td').eq(0).attr("value");
		/*alert(ID)*/
		// alert($(this).val()+":");
	$.ajax({
	        type: 'post',
	        url: 'dwonLoad',
	        data: {
	            ID :ID,
	            Type : 3
	        },
	        success: function (data) {
	        	if(data != 'no'){
		        	console.log(data);
		        	window.location.href=data;
		        	// window.open(data);
	        	}else{
	        		 $.MsgBox_Unload.Alert("提示","没有文件可下载！");
	        	}
	        },
	        error: function () {
	            $.MsgBox_Unload.Alert("提示","服务器繁忙！");
	        }
	    });
});

$(".InvoiceFile2_td").click(function(){
	if($(this).text()=="无"){
		return false;
	}
		var tr=$(this).parent().parent();
		var ID = tr.find('td').eq(0).attr("value");
		/*alert(ID)*/
		// alert($(this).val()+":");
	$.ajax({
	        type: 'post',
	        url: 'dwonLoad',
	        data: {
	            ID :ID,
	            Type : 4
	        },
	        success: function (data) {
	        	if(data != 'no'){
		        	console.log(data);
		        	window.location.href=data;
		        	// window.open(data);
	        	}else{
	        		 $.MsgBox_Unload.Alert("提示","没有文件可下载！");
	        	}
	        },
	        error: function () {
	            $.MsgBox_Unload.Alert("提示","服务器繁忙！");
	        }
	    });
});

$(".InvoiceFile3_td").click(function(){
	if($(this).text()=="无"){
		return false;
	}
		var tr=$(this).parent().parent();
		var ID = tr.find('td').eq(0).attr("value");
		/*alert(ID)*/
		// alert($(this).val()+":");
	$.ajax({
	        type: 'post',
	        url: 'dwonLoad',
	        data: {
	            ID :ID,
	            Type : 5
	        },
	        success: function (data) {
	        	if(data != 'no'){
		        	console.log(data);
		        	window.location.href=data;
		        	// window.open(data);
	        	}else{
	        		 $.MsgBox_Unload.Alert("提示","没有文件可下载！");
	        	}
	        },
	        error: function () {
	            $.MsgBox_Unload.Alert("提示","服务器繁忙！");
	        }
	    });
});

// $(document).on("click",".invoice-down",function(){
// 	var url = window.location.href;
// 	if(url.indexOf("8080")!=-1){
// 		url = "http://localhost:8080/LogisticsFile/File/";
// 	}
// 	if(url.indexOf("8085")!=-1){
// 		url = "http://58.210.123.22:8085/LogisticsFile/File/";
// 	}
// 	var href = url+$(this).val();
// 	window.location.href = href;
// });

// ----------------------------------------搜索框----------------------------------------------
if($('input[name="queryType"]:checked').val()=='single'){
/*	alert(1)
	alert($('input[name="queryType"]:checked').val())*/
    $('.select-content').css('margin-left','33%');
}else{
	/*alert(2)
	alert($('input[name="queryType"]:checked').val())*/
    $('.select-content').css('margin-left','23%');

}
function Check(selected) {
	 if (selected == "single") {
	        $('.select2').hide();
	        $('.select-content').css('margin-left','33%');
	    } else {
	        $('.select2').show();
	        $('.select-content').css('margin-left','23%');
	    }
}
//查询
function selectSearch(){
	$('#top_text_from').submit();
}

/*function selectSearch() {
	
	if ($('.select1 .time').css("display") == "none"
			&& $('.select2 .time').css("display") == "none") { // 非货期查询
		console.log("ininnin")
		if ($(".select2").css("display") == "none") {
			if ($("#searchContent1").val() != "") {
				$('#search').val('search');
				
				$('#top_text_from').submit();
			}
		} else {
			if ($("#searchContent1").val() != ""
					&& $("#searchContent2").val() != "") {
				$('#search').val('search');
				$('#top_text_from').submit();
			}
		}
	} else { // 货期查询 时间框
		if ($('.select2').css("display") == "none") { // 单一货期查询
			var startTimeDate = $(".select1 .startTime").val().replace("-", "")
					.replace("-", "");
			var endTimeDate = $(".select1 .endTime").val().replace("-", "")
					.replace("-", "");
			if (startTimeDate < endTimeDate) {
				$('#search').val('search');
				var newUrl = document.getElementById("top_text_from").action
						+ "?ActualDelivery=no&column=DateOfSign&condition=All";
				$('#top_text_from').attr('action', newUrl);
				$('#top_text_from').submit();
			} else {
				alert("请正确输入时间！");
			}
		} else if ($('.select1 .time').css("display") == "none"
				&& $('.select2 .time').css("display") != "none") {
			var startTimeDate = $(".select2 .startTime").val().replace("-", "")
					.replace("-", "");
			var endTimeDate = $(".select2 .endTime").val().replace("-", "")
					.replace("-", "");
			if (startTimeDate < endTimeDate) {
				$('#search').val('search');
				var newUrl = document.getElementById("top_text_from").action
						+ "?ActualDelivery=no&column=DateOfSign&condition=All";
				$('#top_text_from').attr('action', newUrl);
				$('#top_text_from').submit();
			} else {
				alert("请正确输入时间！");
			}
		} else if ($('.select1 .time').css("display") != "none"
				&& $('.select2 .time').css("display") == "none") {
			var startTimeDate = $(".select1 .startTime").val().replace("-", "")
					.replace("-", "");
			var endTimeDate = $(".select1 .endTime").val().replace("-", "")
					.replace("-", "");
			if (startTimeDate < endTimeDate) {
				$('#search').val('search');
				var newUrl = document.getElementById("top_text_from").action
						+ "?ActualDelivery=no&column=DateOfSign&condition=All";
				$('#top_text_from').attr('action', newUrl);
				$('#top_text_from').submit();
			} else {
				alert("请正确输入时间！");
			}
		} else { // 组合货期查询
			var start1TimeDate = $(".select1 .startTime").val()
					.replace("-", "").replace("-", "");
			var end1TimeDate = $(".select1 .endTime").val().replace("-", "")
					.replace("-", "");
			var start2TimeDate = $(".select2 .startTime").val()
					.replace("-", "").replace("-", "");
			var end2TimeDate = $(".select2 .endTime").val().replace("-", "")
					.replace("-", "");
			if (start1TimeDate < end1TimeDate && start2TimeDate < end2TimeDate) {
				$('#search').val('search');
				var newUrl = document.getElementById("top_text_from").action
						+ "?ActualDelivery=no&column=DateOfSign&condition=All";
				$('#top_text_from').attr('action', newUrl);
				$('#top_text_from').submit();
			} else {
				alert("请正确输入时间！")
			}
		}
	}

}
*/
//点击取消
function selectCancel() {

    $('input[name="content1"]').val('');
    $('input[name="content2"]').val('');
    $('input[name="start_time1"]').val('');
    $('input[name="end_time1"]').val('');
    $('input[name="start_time2"]').val('');
    $('input[name="end_time2"]').val('');
   window.location.href="OriginFactory";
}
$('#searchContent1').keypress(function(event){ 
    $('#search').val('search');
    var keynum = (event.keyCode ? event.keyCode : event.which);    
    if(keynum == '13'){  
        $('#top_text_from').submit();
    }    
});   
$('#searchContent2').keypress(function(event){ 
    $('#search').val('search');
    var keynum = (event.keyCode ? event.keyCode : event.which);    
    if(keynum == '13'){  
            $('#top_text_from').submit();
    }    
}); 

$('#type1').click(function(){
	if($(this).val().indexOf('货期')>=0){
		$('#searchContent1').hide();
		$('.select1 .time').show();
	}else{
		$('#searchContent1').show();
		$('.select1 .time').hide();
	}	
});

$('#type2').click(function(){
	if($(this).val().indexOf('货期')>=0){
		$('#searchContent2').hide();
		$('.select2 .time').show();
	}else{
		$('#searchContent2').show();
		$('.select2 .time').hide();
	}	
});
