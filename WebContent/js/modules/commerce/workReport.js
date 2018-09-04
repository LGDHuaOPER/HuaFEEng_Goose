/*variable、function define*/
// 添加修改提交data对象
var addSubmitObj = new Object();
addSubmitObj.Type = null;
addSubmitObj.Name = null;
addSubmitObj.Department = null;
addSubmitObj.Morning = null;
addSubmitObj.Afternoon = null;
var updateSubmitObj = new Object();
updateSubmitObj.Type = null;
updateSubmitObj.ID = null;
updateSubmitObj.Morning = null;
updateSubmitObj.Afternoon = null;
updateSubmitObj.Introspection = null;
updateSubmitObj.MorningPlan = null;
updateSubmitObj.AfternoonPlan = null;

// 上午保存提交data对象
var updateSubmitObjAM = new Object();
updateSubmitObjAM.Type = null;
updateSubmitObjAM.ID = null;
updateSubmitObjAM.Morning = null;
updateSubmitObjAM.Afternoon = null;
updateSubmitObjAM.Introspection = null;
updateSubmitObjAM.MorningPlan = null;
updateSubmitObjAM.AfternoonPlan = null;
// 下午保存提交data对象
var updateSubmitObjPM = new Object();
updateSubmitObjPM.Type = null;
updateSubmitObjPM.ID = null;
updateSubmitObjPM.Morning = null;
updateSubmitObjPM.Afternoon = null;
updateSubmitObjPM.Introspection = null;
updateSubmitObjPM.MorningPlan = null;
updateSubmitObjPM.AfternoonPlan = null;

// 工作日志邮件data对象
var emailDataObj = new Object();
emailDataObj.Type = "log";
emailDataObj.ID = null;
emailDataObj.Password = null;
emailDataObj.ToList = null;
emailDataObj.CopyToList = null;
emailDataObj.Name = null;
emailDataObj.Morning = null;
emailDataObj.Afternoon = null;
emailDataObj.MorningPlan = null;
emailDataObj.AfternoonPlan = null;
emailDataObj.Introspection = null;

// 定义状态集
var WorkReportState = new Object();
WorkReportState.headerAndFooter = false;
WorkReportState.dailyLogClose = 1;
WorkReportState.updateCurrentPage = 1;
WorkReportState.updateHook = false;
WorkReportState.updateStep = 0;
WorkReportState.mailListHook = false;
WorkReportState.mailList = [];
WorkReportState.hasSavedToList = null;
WorkReportState.hasSavedCCList = null;
WorkReportState.curUpdateName = null;
WorkReportState.curStaffNameAMPM = null;

// 翻页组件按钮逻辑
function pageStyle(currentPage,pageCounts){
    if(pageCounts == 1){
        $("#fistPage, #upPage, #nextPage, #lastPage, #Gotojump").prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
    }else if(currentPage == 1){
        $("#fistPage, #upPage").prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
        $("#lastPage, #nextPage, #Gotojump").prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }else if(currentPage == pageCounts){
        $("#lastPage, #nextPage").prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
        $("#fistPage, #upPage, #Gotojump").prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }else{
        $("#lastPage, #nextPage, #fistPage, #upPage, #Gotojump").prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }
}

// 主页面加载数据
function getPageData(CurrentPage){
	$.ajax({
		type: "GET",
		url: "WorkReport",
		data: {
			LoadType: "data",
			CurrentPage: CurrentPage
		},
		dataType: "json"
	}).then(function(res){
		var data = res.datas;
		var pageCounts = res.pageCount;
		renderPageData(data, CurrentPage, pageCounts);
	},function(){
		$.MsgBox_Unload.Alert("提示","服务器繁忙！页面数据获取失败");
	});
}

// 渲染页面数据
function renderPageData(data, CurrentPage, pageCounts){
	var str = '';
	if(data.length == 1){
		// 权限判断
		var colspanNo;
		if($("span#SendComments_has_span").length && $("span#SendComments_has_span").text()=="是"){
			colspanNo = 8;
		}else{
			colspanNo = 7;
		}
		str+='<tr><td colspan="'+colspanNo+'">无数据......</td></tr>';
	}else if(data.length > 1){
		data.map(function(currentValue, index, arr){
			if(index > 0){
				var iComments = currentValue.Comments;
				var iCommentsStr;
				if(iComments=="未发送" || iComments=="" || iComments=="--"){
					iCommentsStr = '<button type="button" class="btn btn-warning btn-sm">未发送</button>';
				}else if(iComments=="已发送"){
					iCommentsStr = '<button type="button" class="btn btn-success btn-sm">已发送</button>';
				}
				str+='<tr>'+
					'<td class="update_td" data-iid="'+currentValue.ID+'">'+parseInt((CurrentPage-1)*10 + index)+'</td>'+
					'<td class="hastd_Department" title="'+currentValue.Department+'">'+currentValue.Department+'</td>'+
					'<td class="hastd_Name" title="'+currentValue.Name+'">'+currentValue.Name+'</td>'+
					'<td class="hastd_Morning" value="'+currentValue.Morning+'" title="点此查看详情"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></td>'+
					'<td class="hastd_Afternoon" value="'+currentValue.Afternoon+'" title="点此查看详情"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></td>'+
					'<td class="workReport_prompt_td" title="点此发送催促邮件"><button type="button" class="btn btn-info btn-sm">发送</button></td>'+
					'<td class="workReport_tasklog_td" title="点此编写日志，发送邮件"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></td>'+
					'<td class="workReport_Comments_td" title="点此发送意见" value="'+iComments+'">'+iCommentsStr+'</td>'+
					'<td class="hastd_AfternoonPlan" value="'+currentValue.AfternoonPlan+'">'+currentValue.AfternoonPlan+'</td>'+
					'<td class="hastd_MorningPlan" value="'+currentValue.MorningPlan+'">'+currentValue.MorningPlan+'</td>'+
					'<td class="hastd_Introspection" value="'+currentValue.Introspection+'">'+currentValue.Introspection+'</td>'+
					'<td class="hastd_ToList" value="'+currentValue.ToList+'">'+currentValue.ToList+'</td>'+
					'<td class="hastd_CopyToList" value="'+currentValue.CopyToList+'">'+currentValue.CopyToList+'</td>'+
				'</tr>';
			}
		});
	}
	$(".m_table table tbody").empty().append(str);
	$(".m_page #currentPage").text(CurrentPage);
	$(".m_page #allPage").text(pageCounts);
	pageStyle(CurrentPage, pageCounts);
	SendCommentsAuthority();
	// $('[data-toggle="popover"]').popover({
	// 	title: "请审核",
	// 	content: '<div class="form-group"><label for="exampleInputEmail1">原因</label><input type="text" class="form-control" id="exampleInputEmail1" placeholder="原因"></div><div style="text-align:right"><input type="button" class="btn btn-success" id="ReimburseApplication_y" value="通过"><input type="button" class="btn btn-warning" id="ReimburseApplication_n" value="未通过"></div>',
	// 	html: true
	// });
}

// 取邮件列表
function getMailList(fn,fn2){
	if(!WorkReportState.mailListHook){
		$.ajax({
			type: "GET",
			url: "GetAllEmail",
			dataType: "json"
		}).then(function(data){
			fn && fn(data);
			WorkReportState.mailListHook = true;
		},function(){
			$.MsgBox_Unload.Alert("提示","服务器繁忙！邮箱数据获取失败");
		});
	}else{
		fn2 && fn2();
	}
}

// 构造li字符串
function buildLiStr(arra){
	var str = '';
	arra.map(function(currentValue, index, arr){
		str+='<li title="'+currentValue+'">'+currentValue+'</li>';
	});
	return str;
}

// 收件人、抄送人回显
function ToCCListBackshow(str01,str02){
	if(str01!="" && str01!="--"){
		var hasSavedToListArr = str01.split(";");
		hasSavedToListArr = $.grep(hasSavedToListArr,function(currentValue,index){
			return currentValue != "";
		});
		var hasSavedToListStr = '';
		hasSavedToListArr.map(function(currentValue, index, arr){
			$(".selectMailList_body_l ul>li[title='"+currentValue+"']").remove();
			hasSavedToListStr+='<li title="'+currentValue+'">'+currentValue+'</li>';
		});
		$(".selectMailList_body_r_to ul").append(hasSavedToListStr);
	}

	if(str02!="" && str02!="--"){
		var hasSavedCCListArr = str02.split(";");
		hasSavedCCListArr = $.grep(hasSavedCCListArr,function(currentValue,index){
			return currentValue != "";
		});
		var hasSavedCCListStr = '';
		hasSavedCCListArr.map(function(currentValue, index, arr){
			$(".selectMailList_body_l ul>li[title='"+currentValue+"']").remove();
			hasSavedCCListStr+='<li title="'+currentValue+'">'+currentValue+'</li>';
		});
		$(".selectMailList_body_r_cc ul").append(hasSavedCCListStr);
	}
}

// 发送意见权限
function SendCommentsAuthority(){
	if($("span#SendComments_has_span").length && $("span#SendComments_has_span").text()=="是"){
		$(".m_table thead, .m_table tbody").removeClass("inormal").addClass("jurisdiction");
		$(".workReport_prompt_td>button").css("cursor","pointer").prop("disabled",false);
		$(".workReport_prompt_td").attr("title","点此发送催促邮件");
	}else{
		$(".m_table thead, .m_table tbody").removeClass("jurisdiction").addClass("inormal");
		$(".workReport_prompt_td>button").css("cursor","not-allowed").prop("disabled","disabled");
		$(".workReport_prompt_td").attr("title","您没有此权限！");
	}
}

// 上下午工作内容提交ajax
function ampmWorkConSubmit(submitData, iThat){
	$.ajax({
		type: "POST",
		url: "WorkReport",
		dataType: 'json',
		data: submitData,
		success: function(data){
			if(data == true){
				$.MsgBox_Unload.Alert("提示","信息修改成功！");
				$('#workReportModal').modal('hide');
				getPageData(Number($("span#currentPage").text()));
			}else if(data == false){
				$.MsgBox_Unload.Alert("提示","信息修改失败！");
			}
		},
		error: function(){
			$.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
		},
		beforeSend: function(XMLHttpRequest){
            iThat.css("cursor","not-allowed").prop("disabled","disabled");
        },
		complete: function(XMLHttpRequest, textStatus){
		    if(textStatus=="success"){
		    }
		    iThat.css("cursor","pointer").prop("disabled",false);
		}
	});
}

/*onload*/
$(function(){
	// 初始化页面数据
	getPageData(1);
	// 初始化模态框
	$("#workReportModal").modal({keyboard: true, show: false});
});

/*event handler*/
// 添加框打开关闭
$(".m_button_l>input[value='添加']").click(function(){
	$(".bg_cover, .add_NonStandard").slideDown(300);
	$("[id^='add_info_']").val("");
	$(".add_NonStandard .overplus_number_no").text("500");
});
$(".add_NonStandard_tit_r>button, #NonStandard_addclose").click(function(){
	$(".bg_cover, .add_NonStandard").slideUp(300);
	for(var k in addSubmitObj){
		addSubmitObj[k] = null;
	}
});
//添加框提交
$("#NonStandard_addsubmit").click(function(){
	for(var kk in addSubmitObj){
		if(kk=="Type"){
			addSubmitObj[kk] = "add";
			continue;
		}
		// if(kk=="ID" || kk=="Pass" || kk=="TotalAmount" || kk=="DetailJson" || kk=="TravelJson"){
		// 	continue;
		// }
		addSubmitObj[kk] = $("#add_info_"+kk).val();
	}
	// 表单验证
	for(var kkk in addSubmitObj){
		addSubmitObj[kkk] = globalDataHandle(addSubmitObj[kkk],"").trim();
		if(kkk=="Department"&&addSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择部门！");
			return false;
		}
		if(kkk=="Name"&&addSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择姓名！");
			return false;
		}
	}
	// console.log("add我执行了吗");
	// console.log(addSubmitObj);
	$.ajax({
		type: "POST",
		url: "WorkReport",
		dataType: 'json',
		data: addSubmitObj,
		success: function(data){
			if(data == true){
				getPageData(1);
				$("#NonStandard_addclose").trigger("click");
				$.MsgBox_Unload.Alert("提示","信息添加成功！");
			}else if(data == false){
				$.MsgBox_Unload.Alert("提示","信息添加失败！");
			}
		},
		error: function(){
			$.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
		},
		beforeSend: function(XMLHttpRequest){
            $("#NonStandard_addsubmit").css("cursor","not-allowed").prop("disabled","disabled");
        },
		complete: function(XMLHttpRequest, textStatus){
		    if(textStatus=="success"){
		    }
		    $("#NonStandard_addsubmit").css("cursor","pointer").prop("disabled",false);
		}
	});
});

// 上下午工作计划查看、修改
$(document).on("click",".hastd_Morning, .hastd_Afternoon",function(){
	var subClassName = $(this).attr("class").split(" ")[0].replace("hastd_","modal_task_detail_");
	var subClassName2 = $(this).attr("class").split(" ")[0].replace("hastd_","model_submit_");
	var StaffName = $(this).siblings(".hastd_Name").text();
	WorkReportState.curStaffNameAMPM = StaffName;
	var content = $(this).attr("value");
	$("[class^='modal_task_detail_'], #workReportModal [class*='model_submit_']").hide(50,function(){
		$("."+subClassName+", ."+subClassName2).show();
	});
	$(".modal-header .modal-title").text(StaffName+"的工作内容");
	$("."+subClassName).find("textarea").val(content);
	// 内容剩余字数
	$("#workReportModal .overplus_number_no").each(function(){
		var Len = $(this).parent().parent().siblings("textarea").val().length;
		var num = 500 - Len;
		$(this).text(num);
	});
	if(subClassName == "modal_task_detail_Morning"){
		for(var k in updateSubmitObjAM){
			if(k=="Type"){
				updateSubmitObjAM[k] = "update";
				continue;
			}
			if(k=="ID"){
				updateSubmitObjAM[k] = Number($(this).siblings(".update_td").data("iid")).toString();
				continue;
			}
			if(k=="Morning"){
				continue;
			}
			updateSubmitObjAM[k] = $(this).siblings(".hastd_"+k).attr("value");
		}
	}else if(subClassName == "modal_task_detail_Afternoon"){
		for(var kk in updateSubmitObjPM){
			if(kk=="Type"){
				updateSubmitObjPM[kk] = "update";
				continue;
			}
			if(kk=="ID"){
				updateSubmitObjPM[kk] = Number($(this).siblings(".update_td").data("iid")).toString();
				continue;
			}
			if(kk=="Afternoon"){
				continue;
			}
			updateSubmitObjPM[kk] = $(this).siblings(".hastd_"+kk).attr("value");
		}
	}
	
	$(".bg_cover").slideDown(200);
	$('#workReportModal').modal('show');
});
// 上下午工作内容查看关闭事件
$('#workReportModal').on('hide.bs.modal', function (e) {
	$(".bg_cover").slideUp(200);
	for(var k in updateSubmitObjAM){
		updateSubmitObjAM[k] = null;
	}
	for(var kk in updateSubmitObjPM){
		updateSubmitObjPM[kk] = null;
	}
	WorkReportState.curStaffNameAMPM = null;
});
// 上下午工作内容提交事件
$("#workReportModal [class*='model_submit_']").click(function(){
	var curName = $("span#workReportName").text();
	if(WorkReportState.curStaffNameAMPM != curName){
		$.MsgBox_Unload.Alert("保存工作内容提示","不是本人，无法保存！");
		return false;
	}
	var iThat = $(this);
	if($(this).is(".model_submit_Morning")){
		updateSubmitObjAM.Morning = $("#detail_Morning").val().trim();
		ampmWorkConSubmit(updateSubmitObjAM, iThat);
	}else if($(this).is(".model_submit_Afternoon")){
		updateSubmitObjPM.Afternoon = $("#detail_Afternoon").val().trim();
		ampmWorkConSubmit(updateSubmitObjPM, iThat);
	}
});

// 工作日志模板打开
$(document).on("click",".workReport_tasklog_td",function(){
	updateSubmitObj.ID = Number($(this).siblings(".update_td").data("iid")).toString();
	emailDataObj.ID = Number($(this).siblings(".update_td").data("iid")).toString();
	WorkReportState.updateCurrentPage = Number($("span#currentPage").text());
	WorkReportState.updateHook = false;
	WorkReportState.hasSavedToList = $(this).siblings(".hastd_ToList").text();
	WorkReportState.hasSavedCCList = $(this).siblings(".hastd_CopyToList").text();
	$(".bg_cover, .daily_log_temp, .operation_nav_bar").slideDown(300,function(){
		if(!WorkReportState.headerAndFooter){
			WorkReportState.headerAndFooter = true;
			$(".daily_log_temp_head, .daily_log_temp_foot").empty();
			$(".daily_log_temp_head").load('html/modules/commerce/HeaderAndFooter.html #section_header_jpg',function(response,status,xhr){
				// console.log(response);
				// console.log(status); // success
				// console.log(xhr);
			});
			$(".daily_log_temp_foot").load('html/modules/commerce/HeaderAndFooter.html #section_footer_jpg',function(response,status,xhr){
			});
		}
	});
	$(".operation_nav_bar .navbar-header button>span").removeClass("glyphicon-envelope").addClass("glyphicon-file");
	WorkReportState.updateStep = 0;
	$(".navbar-collapse>ul>li:nth-child(1)>button").prop("disabled",true);
	$(".navbar-collapse>ul>li:nth-child(2)").show();
	$(".navbar-collapse>ul>li:nth-child(3)").hide();
	WorkReportState.dailyLogClose = 1;
	var dayStr = globalGetToday().substring(2).replace(/-/g,"");
	var name = $(this).siblings(".hastd_Name").text();
	emailDataObj.Name = name;
	WorkReportState.curUpdateName = name;
	$(".daily_log_temp_body_tit").text("工作日志"+dayStr+"-"+name);
	// 填充内容、计划、反思
	var that = $(this);
	$(".daily_log_temp [id^='update_info_']").each(function(){
		var subClassName = $(this).attr("id").replace("update_info_", "hastd_");
		var oldVal = that.siblings("."+subClassName).attr("value");
		var newVal = globalDataHandle(oldVal,"");
		$(this).val(newVal);
	});

	// 内容、计划、反思剩余字数
	$(".daily_log_temp .overplus_number_no").each(function(){
		var Len = $(this).parent().parent().siblings("textarea").val().length;
		var num = 500 - Len;
		$(this).text(num);
	});
});

// 上一步
$(".navbar-collapse>ul>li:nth-child(1)").click(function(){
	switch(WorkReportState.updateStep)
	{
		case 1:
		$(".daily_log_temp").slideDown(200);
		$(".navbar-collapse>ul>li:nth-child(1)>button").prop("disabled",true);
		break;

		case 2:
		$(".mail_template").slideUp(200, function(){
			WorkReportState.dailyLogClose = 1;
			$(".daily_log_temp").slideDown(250);
			$(".operation_nav_bar .navbar-header button>span").removeClass("glyphicon-envelope").addClass("glyphicon-file");
			$(".navbar-collapse>ul>li:nth-child(2)").show();
			$(".navbar-collapse>ul>li:nth-child(3)").hide();
		});
		break;

		case 3:
		$(".mail_template").slideDown(250);
		break;

		default:
		return false;
	}
	WorkReportState.updateStep--;
});

// 工作日志模板、邮件模板关闭
$(".navbar-collapse>ul>li:nth-child(4)").click(function(){
	if(WorkReportState.dailyLogClose == 1){
		$(".daily_log_temp").slideUp(250);
		WorkReportState.updateStep = 1;
		$(".navbar-collapse>ul>li:nth-child(1)>button").prop("disabled",false);
	}else if(WorkReportState.dailyLogClose == 2){
		$(".mail_template").slideUp(250);
		WorkReportState.updateStep = 3;
	}
});

// 操作栏关闭
$(".navbar-collapse>ul>li:nth-child(6)").click(function(){
	$(".bg_cover, .daily_log_temp, .operation_nav_bar, .mail_template").slideUp(250);
	for(var k in updateSubmitObj){
		updateSubmitObj[k] = null;
	}
	for(var kk in emailDataObj){
		if(kk == "Type"){
			continue;
		}
		emailDataObj[kk] = null;
	}
	if(WorkReportState.updateHook){
		getPageData(WorkReportState.updateCurrentPage);
	}
	WorkReportState.hasSavedToList = null;
	WorkReportState.hasSavedCCList = null;
	WorkReportState.curUpdateName = null;
});

// 工作日志模板提交保存
$(".navbar-collapse>ul>li:nth-child(2)").click(function(){
	var curName = $("span#workReportName").text();
	if(WorkReportState.curUpdateName != curName){
		$.MsgBox_Unload.Alert("保存工作日志提示","不是本人，无法修改！");
		return false;
	}
	var iThat = $(this).children("button");
	for(var kk in updateSubmitObj){
		if(kk=="Type"){
			updateSubmitObj[kk] = "update";
			continue;
		}
		if(kk=="ID"){
			continue;
		}
		updateSubmitObj[kk] = $("#update_info_"+kk).val();
	}
	// 表单验证
	for(var kkk in updateSubmitObj){
		updateSubmitObj[kkk] = globalDataHandle(updateSubmitObj[kkk],"").trim();
		// if(kkk=="Department"&&updateSubmitObj[kkk]==""){
		// 	$.MsgBox_Unload.Alert("提示","未选择部门！");
		// 	return false;
		// }
	}
	console.log("update我执行了吗");
	console.log(updateSubmitObj);
	$.ajax({
		type: "POST",
		url: "WorkReport",
		dataType: 'json',
		data: updateSubmitObj,
		success: function(data){
			if(data == true){
				$.MsgBox_Unload.Alert("提示","信息修改成功！");
				WorkReportState.updateHook = true;
				$(".daily_log_temp").slideUp(200, function(){
					WorkReportState.dailyLogClose = 2;
					WorkReportState.updateStep = 2;
					$(".mail_template").slideDown(250);
					$(".operation_nav_bar .navbar-header button>span").removeClass("glyphicon-file").addClass("glyphicon-envelope");
					$(".navbar-collapse>ul>li:nth-child(1)>button").prop("disabled",false);
					$(".navbar-collapse>ul>li:nth-child(2)").hide();
					$(".navbar-collapse>ul>li:nth-child(3)").show();
					// 填充数据
					var curEmail = $("span#email").text();
					var titl = "Eoulu："+$(".daily_log_temp_body_tit").text();
					$(".mail_template_body>fieldset:nth-child(1) input").val(curEmail);
					$(".mail_template_body>fieldset:nth-child(5) input").val(titl);
					$(".mail_template_body>fieldset:nth-child(3) textarea").val(WorkReportState.hasSavedToList);
					$(".mail_template_body>fieldset:nth-child(4) textarea").val(WorkReportState.hasSavedCCList);
					var currentUserName = $("span#userName").text();
					if(!$.cookie(currentUserName+"workReportMailPass")){
						$(".mail_template_body>fieldset:nth-child(2) input").val("");
					}else{
						$(".mail_template_body>fieldset:nth-child(2) input").val($.cookie(currentUserName+"workReportMailPass"));
					}
				});
			}else if(data == false){
				$.MsgBox_Unload.Alert("提示","信息修改失败！");
			}
		},
		error: function(){
			$.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
		},
		beforeSend: function(XMLHttpRequest){
            iThat.css("cursor","not-allowed").prop("disabled","disabled");
        },
		complete: function(XMLHttpRequest, textStatus){
		    if(textStatus=="success"){
		    }
		    iThat.css("cursor","pointer").prop("disabled",false);
		}
	});
});

// 翻页功能
$("#jumpNumber").on("input propertychange",function(){
    var newVal = $(this).val().replace(/[^\d]/g,'');
    $(this).val(newVal);
});

	// 翻页
$("#fistPage").click(function(){
    var currentPage =1;
    getPageData(currentPage);
});

$("#lastPage").click(function(){
    var currentPage =Number($("#allPage").text());
    getPageData(currentPage);
});

$("#upPage").click(function(){
    var currentPage = Number($("#currentPage").text());
    if(currentPage == 1){
        return;
    }else{
        currentPage--;
        getPageData(currentPage);
    }
});

$("#nextPage").click(function(){
    var currentPage = Number($("#currentPage").text());
    var pageCounts = Number($("#allPage").text());
    if(currentPage == pageCounts){
        return;
    }else{
        currentPage++;
        getPageData(currentPage);
    }
});
	//跳页
$("#Gotojump").click(function(){
    var currentPage = $("#jumpNumber").val().trim();
    var pageCounts = Number($("#allPage").text());
    var oldCurrentPage = Number($("#currentPage").text());
    if(currentPage == oldCurrentPage || currentPage <= 0 || currentPage>pageCounts){
        $("#jumpNumber").val('');
        return;
    }else{
        getPageData(currentPage);
    }
});

// 密码框显隐
$(".mail_template_body>fieldset:nth-child(2) .form-control-feedback").click(function(){
	$(this).toggleClass("glyphicon-eye-open glyphicon-eye-close");
	if($(this).is(".glyphicon-eye-close")){
		$(this).siblings("input").attr("type","text");
	}else if($(this).is(".glyphicon-eye-open")){
		$(this).siblings("input").attr("type","password");
	}
});

// 邮件列表显示
$(".mail_template_body>fieldset:nth-child(3) .form-control-feedback, .mail_template_body>fieldset:nth-child(4) .form-control-feedback").click(function(){
	getMailList(function(data){
		data.map(function(currentValue, index, arr){
			if(index>0){
				WorkReportState.mailList.push(currentValue.Email);
			}
		});
		WorkReportState.mailList.sort(ascCompare);
		var liStr = buildLiStr(WorkReportState.mailList);
		$(".selectMailList ul").empty();
		$(".selectMailList_body_l ul").append(liStr);
		var str11 = $(".mail_template_body>fieldset:nth-child(3) textarea").val();
		var str12 = $(".mail_template_body>fieldset:nth-child(4) textarea").val();
		ToCCListBackshow(str11,str12);
	},function(){
		var liStr2 = buildLiStr(WorkReportState.mailList);
		$(".selectMailList ul").empty();
		$(".selectMailList_body_l ul").append(liStr2);
		var str21 = $(".mail_template_body>fieldset:nth-child(3) textarea").val();
		var str22 = $(".mail_template_body>fieldset:nth-child(4) textarea").val();
		ToCCListBackshow(str21,str22);
	});
	$(".selectMailList_cover, .selectMailList").slideDown(250);
});

// 邮件列表选择删除邮箱样式
$(document).on("click",".selectMailList ul>li",function(){
	$(this).toggleClass("hasSelected");
});

// 选入邮箱
$(".selectMailList_body_m_top>button:nth-child(1)").click(function(){
	var tempArr = [];
	$(".selectMailList_body_l ul>li.hasSelected").each(function(){
		tempArr.push($(this).attr("title"));
		$(this).remove();
	});
	var liStr = buildLiStr(tempArr);
	$(".selectMailList_body_r_to ul").append(liStr);
});
$(".selectMailList_body_m_bottom>button:nth-child(1)").click(function(){
	var tempArr = [];
	$(".selectMailList_body_l ul>li.hasSelected").each(function(){
		tempArr.push($(this).attr("title"));
		$(this).remove();
	});
	var liStr = buildLiStr(tempArr);
	$(".selectMailList_body_r_cc ul").append(liStr);
});

// 移出邮箱
$(".selectMailList_body_m_top>button:nth-child(2)").click(function(){
	var tempArr = [];
	$(".selectMailList_body_r_to ul>li.hasSelected").each(function(){
		tempArr.push($(this).attr("title"));
		$(this).remove();
	});
	var liStr = buildLiStr(tempArr);
	$(".selectMailList_body_l ul").append(liStr);
});
$(".selectMailList_body_m_bottom>button:nth-child(2)").click(function(){
	var tempArr = [];
	$(".selectMailList_body_r_cc ul>li.hasSelected").each(function(){
		tempArr.push($(this).attr("title"));
		$(this).remove();
	});
	var liStr = buildLiStr(tempArr);
	$(".selectMailList_body_l ul").append(liStr);
});

// 收件人、抄送人确定
$("#selectMailList_ok").click(function(){
	var ToListArr = [];
	var CCListArr = [];
	$(".selectMailList_body_r_to ul>li").each(function(){
		ToListArr.push($(this).attr("title"));
	});
	$(".selectMailList_body_r_cc ul>li").each(function(){
		CCListArr.push($(this).attr("title"));
	});
	var ToListStr, CCListStr;
	if(ToListArr.length == 0){
		ToListStr = "";
	}else{
		ToListStr = ToListArr.join(";")+";";
	}

	if(CCListArr.length == 0){
		CCListStr = "";
	}else{
		CCListStr = CCListArr.join(";")+";";
	}
	$(".mail_template_body>fieldset:nth-child(3) textarea").val(ToListStr);
	$(".mail_template_body>fieldset:nth-child(4) textarea").val(CCListStr);
	$(".selectMailList_cover, .selectMailList").slideUp(250);
});

// 收件人、抄送人取消
$("#selectMailList_no").click(function(){
	$(".selectMailList_cover, .selectMailList").slideUp(250);
});

// 工作日志模板发送邮件
$(".navbar-collapse>ul>li:nth-child(3)").click(function(){
	var that = $(this).children("button");
	that.css("cursor","not-allowed").prop("disabled","disabled");
	emailDataObj.Password = $(".mail_template_body>fieldset:nth-child(2) input").val();
	emailDataObj.ToList = $(".mail_template_body>fieldset:nth-child(3) textarea").val();
	emailDataObj.CopyToList = $(".mail_template_body>fieldset:nth-child(4) textarea").val();
	emailDataObj.Morning = $("#update_info_Morning").val();
	emailDataObj.Afternoon = $("#update_info_Afternoon").val();
	emailDataObj.MorningPlan = $("#update_info_MorningPlan").val();
	emailDataObj.AfternoonPlan = $("#update_info_AfternoonPlan").val();
	emailDataObj.Introspection = $("#update_info_Introspection").val();
	$.ajax({
		type: "POST",
		url: "WorkReportSend",
		data: emailDataObj,
		dataType: "json"
	}).then(function(data){
		if(data == true){
			$.MsgBox_Unload.Alert("发邮件提示", "发送成功！");
			var currentUserName = $("span#userName").text();
			$.cookie(currentUserName+"workReportMailPass", emailDataObj.Password, { expires: 360 });
			$(".navbar-collapse>ul>li:nth-child(6)").trigger("click");
		}else if(data == false){
			$.MsgBox_Unload.Alert("发邮件提示", "发送失败！");
		}else{
			$.MsgBox_Unload.Alert("发邮件提示", data);
		}
	},function(){
		$.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
	}).always(function(){
		that.css("cursor","pointer").prop("disabled",false);
	});
});

// 发送催促邮件
$(document).on("click",".workReport_prompt_td>button",function(){
	var that = $(this);
	that.css("cursor","not-allowed").prop("disabled","disabled");
	var Name = $(this).parent().siblings(".hastd_Name").text();
	$.ajax({
		type: "GET",
		url: "WorkReportSend",
		data: {
			Name: Name
		},
		dataType: "json"
	}).then(function(data){
		if(data == true){
			$.MsgBox_Unload.Alert("发邮件提示", "操作成功！");
			getPageData(Number($("span#currentPage").text()));
		}else if(data == false){
			$.MsgBox_Unload.Alert("发邮件提示", "操作失败！");
		}else{
			$.MsgBox_Unload.Alert("发邮件提示", data);
		}
	},function(){
		$.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
	}).always(function(){
		that.css("cursor","pointer").prop("disabled",false);
	});
});

// 发送意见打开关闭
$(document).on("click",".workReport_Comments_td",function(){
	$(".suggestion_div, .bg_cover").slideDown(250);
	$(".suggestion_div").attr("title",$(this).siblings(".update_td").data("iid")).attr("value",$(this).siblings(".hastd_Name").text());
	$("#suggestion_textarea").val("");
});
$("#suggestion_no").click(function(){
	$(".suggestion_div, .bg_cover").slideUp(250);
});

// 发送意见发邮件
$("#suggestion_ok").click(function(){
	var that = $(this);
	that.css("cursor","not-allowed").prop("disabled","disabled");
	var ID = $(".suggestion_div").attr("title");
	var Name = $(".suggestion_div").attr("value");
	var Text = $("#suggestion_textarea").val().replace(/[\r\n]+/g, "<br><br>");
	$.ajax({
		type: "POST",
		url: "WorkReportSend",
		data: {
			Type: "comments",
			ID: ID,
			Text: Text,
			Name: Name
		},
		dataType: "text"
	}).then(function(data){
		if(data.indexOf("操作成功")>-1){
			$.MsgBox_Unload.Alert("发邮件提示", "发送意见操作成功！");
			getPageData(Number($("span#currentPage").text()));
			$(".suggestion_div, .bg_cover").slideUp(250);
		}else if(data.indexOf("操作失败")>-1){
			$.MsgBox_Unload.Alert("发邮件提示", "发送意见操作失败！");
		}else{
			$.MsgBox_Unload.Alert("发邮件提示", data);
		}
	},function(){
		$.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
	}).always(function(){
		that.css("cursor","pointer").prop("disabled",false);
	});
});

// 动态提示剩余字数
$(document).on("change keydown keyup",".form-group>textarea[id*='_info_'], #workReportModal textarea[id^='detail_']",function(){
	var len = $(this).val().length;
	var num = 500 - len;
    $(this).siblings("label").find(".overplus_number_no").text(num);
    if(len > 499){
    	$(this).val($(this).val().substring(0,500));
    	$(this).siblings("label").find(".overplus_number_no").text("0");
    }
});