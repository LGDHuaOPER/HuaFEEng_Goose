/*****函数定义与变量定义*****/
var addSubmitObj = new Object();
addSubmitObj.Type = null;
addSubmitObj.Number = null;
addSubmitObj.Project = null;
addSubmitObj.CustomerTitle = null;
addSubmitObj.CustomerName = null;
addSubmitObj.LinkInfo = null;
addSubmitObj.StaffName = null;
addSubmitObj.ContractNo = null;
addSubmitObj.ProductVersion = null;
addSubmitObj.FileName = null;
var updateSubmitObj = new Object();
updateSubmitObj.ID = null;
updateSubmitObj.Type = null;
updateSubmitObj.Number = null;
updateSubmitObj.Project = null;
updateSubmitObj.CustomerTitle = null;
updateSubmitObj.CustomerName = null;
updateSubmitObj.LinkInfo = null;
updateSubmitObj.StaffName = null;
updateSubmitObj.ContractNo = null;
updateSubmitObj.ProductVersion = null;
updateSubmitObj.FileName = null;
var downloadObj = new Object();
downloadObj.Number = null;
downloadObj.Project = null;
downloadObj.CustomerTitle = null;
downloadObj.CustomerName = null;
downloadObj.LinkInfo = null;
downloadObj.StaffName = null;
downloadObj.ContractNo = null;
downloadObj.ProductVersion = null;
downloadObj.Type = null;
downloadObj.FileName = null;
downloadObj.PreviewJson = null;
var hasSearch = 0;
var signatureObj = {};
var ReportID;
var ReportNumber;
var curFileName = "";
var down_FileName = "";
// 删除记录用的变量
var delete_email_curPage = 1;
var delete_ID = null;
var delete_res_flag = null;
// var regDate1 = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
// var regDate2 = /^[1-9]\d{3}-(0[1-9]|1[0-2])$/;
// var regDate3 = /^(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
// var regDate4 = /^[1-9]\d{3}$/;
// var regDate5 = /^(0[1-9]|1[0-2])$/;
// var regDate6 = /^(0[1-9]|[1-2][0-9]|3[0-1])$/;
// 服务完成报告item字符串
var serviceReportStr = '<tr>'+
                            '<td class="item-No"><span class="glyphicon glyphicon-remove-circle delete_item"></span>&nbsp;<span class="NoService"></span></td>'+
                            '<td class="service_ServiceItem"></td>'+
                            '<td class="service_Isfinished"><select class="isFinish_sel"><option value="0">请选择</option><option value="是">是</option><option value="否">否</option></select></td>'+
                            '<td class="service_Remarks"></td>'+
                            '<td class="service_ConfirmedSignature"><span class="glyphicon glyphicon-pencil iSignature"></span></td>'+
                            '<td class="service_ConfirmDate"><span class="glyphicon glyphicon-pencil iSignature"></span></td>'+
                        '</tr>';

function pageStyle(currentPage,pageCounts){
    if(pageCounts == 1){
        $("#fistPage").attr("disabled","disabled");
        $("#upPage").attr("disabled","disabled");
        $("#nextPage").attr("disabled","disabled");
        $("#lastPage").attr("disabled","disabled");
        buttonDisabled("#fistPage, #upPage, #nextPage, #lastPage");
    }else if(currentPage == 1){
        $("#fistPage").attr("disabled","disabled");
        $("#upPage").attr("disabled","disabled");
        $("#lastPage").attr("disabled",false);
        $("#nextPage").attr("disabled",false);
        buttonDisabled("#fistPage, #upPage");
        buttonAbled("#nextPage, #lastPage");
    }else if(currentPage == pageCounts){
        $("#lastPage").attr("disabled","disabled");
        $("#nextPage").attr("disabled","disabled");
        $("#fistPage").attr("disabled",false);
        $("#upPage").attr("disabled",false);
        buttonDisabled("#nextPage, #lastPage");
        buttonAbled("#fistPage, #upPage");
    }else{
        $("#lastPage, #nextPage, #fistPage, #upPage").attr("disabled",false);
        buttonAbled("#lastPage, #nextPage, #fistPage, #upPage");
    }
}
function buttonDisabled(iClass) {
    return $(iClass).removeClass("buttonDisabled buttonAbled").addClass("buttonDisabled");
}
function buttonAbled(jClass) {
    return $(jClass).removeClass("buttonDisabled buttonAbled").addClass("buttonAbled");
}

// 表格渲染
function tableRender(icurPage,pageCounts,data){
	var str = '';
	for(var i =1;i<data.length;i++){
	    str+='<tr>'+
	            '<td class="update_td" data-iid="'+data[i].ID+'">'+parseInt((icurPage-1)*10 + i)+'</td>'+
	            '<td class="hastd_Number" title="'+data[i].Number+'">'+data[i].Number+'</td>'+
	            '<td class="hastd_Project" title="'+data[i].Project+'">'+data[i].Project+'</td>'+
	            '<td class="hastd_CustomerTitle" title="'+data[i].CustomerTitle+'">'+data[i].CustomerTitle+'</td>'+
	            '<td class="hastd_CustomerName" title="'+data[i].CustomerName+'">'+data[i].CustomerName+'</td>'+
	            '<td class="hastd_LinkInfo" title="'+data[i].LinkInfo+'">'+data[i].LinkInfo+'</td>'+
	            '<td class="hastd_StaffName" title="'+data[i].StaffName+'">'+data[i].StaffName+'</td>'+
	            '<td class="reportView" title="报告预览"><span class="glyphicon glyphicon-eye-open"></span></td>'+
	            '<td class="hastd_ContractNo" title="'+data[i].ContractNo+'" style="display:none">'+data[i].ContractNo+'</td>'+
	            '<td class="hastd_ProductVersion" title="'+data[i].ProductVersion+'" style="display:none">'+data[i].ProductVersion+'</td>'+
	            '<td class="hastd_FileName" title="'+data[i].FileName+'" style="display:none">'+data[i].FileName+'</td>'+
	            '<td class="delete_report_td" title="删除" style="display:none"><input type="button" value="删除"></td>'+
	        '</tr>';
	}
	$(".m_table table tbody").empty().append(str);
	$(".m_page #currentPage").text(icurPage);
	$(".m_page #allPage").text(pageCounts);
	deleteServiceReport();
	setTimeout(tableCanDrag,50);
}

// 表格渲染ajax请求
function tableRenderAjax(icurPage){
	$.ajax({
		type: "GET",
		url: "ServiceReport",
		data: {
			LoadType: "data",
			CurrentPage: icurPage
		},
		dataType: 'json',
		success: function(res){
			console.log(typeof res);
			var pageCounts = res.pageCount;
			var data = res.data;
			tableRender(icurPage,pageCounts,data);
			pageStyle(icurPage,pageCounts);
		},
		error:function(){
			$.MsgBox_Unload.Alert("提示","服务器繁忙！");
		},
		complete: function(XMLHttpRequest, textStatus){
		    // var newInfo1 = transportInfo();
		    // $(".g_container_body_info_l").text(newInfo1);
		    // tdDateHandle(".DateOfSign, .CargoPeriod, .ExpectedDeliveryPeriod, .ActualDelivery","","#000");
		}
	});
}

// 搜索后表格渲染ajax请求
function searchTABRenderAjax(icurPage,QueryType,Column1,Content1,Column2,Content2){
	// 单一搜索
	if(Column2==undefined&&Content2==undefined){
		$.ajax({
			type: "GET",
			url: "ServiceReport",
			data: {
				LoadType: "data",
				QueryType: QueryType,
				Column1: Column1,
				Content1: Content1,
				CurrentPage: icurPage
			},
			dataType: 'json',
			success: function(res){
				console.log(typeof res);
				var pageCounts = res.pageCount;
				var data = res.data;
				tableRender(icurPage,pageCounts,data);
				pageStyle(icurPage,pageCounts);
			},
			error: function(){
				$.MsgBox_Unload.Alert("提示","服务器繁忙！");
			},
			complete: function(XMLHttpRequest, textStatus){
			    if(textStatus=="success"){
					hasSearch = 1;
			    }
			}
		});
	}else{
		// 组合查询
		$.ajax({
			type: "GET",
			url: "ServiceReport",
			data: {
				LoadType: "data",
				QueryType: QueryType,
				Column1: Column1,
				Content1: Content1,
				Column2: Column2,
				Content2: Content2,
				CurrentPage: icurPage
			},
			dataType:'json',
			success:function(res){
				console.log(typeof res);
				var pageCounts = res.pageCount;
				var data = res.data;
				tableRender(icurPage,pageCounts,data);
				pageStyle(icurPage,pageCounts);
			},
			error:function(){
				$.MsgBox_Unload.Alert("提示","服务器繁忙！");
			},
			complete: function(XMLHttpRequest, textStatus){
			    if(textStatus=="success"){
					hasSearch = 1;
			    }
			}
		});
	}
}

// 搜索获取参数，参数判断
function searchGetParam(icurPage){
	var QueryType = $(".m_button_r_top input[name='querytype']:checked").val();
	var Column1 = $(".m_button_r_m .input-group-btn button").attr("title");
	var Content1 = $(".m_button_r_m input").val().trim();
	// if(Column1=="发布时间"||Column1=="阶段预计完成时间"||Column1=="项目预计完成时间"||Column1=="阶段实际完成时间"){
	// 	if(!regDate1.test(Content1)&&!regDate2.test(Content1)&&!regDate3.test(Content1)&&!regDate4.test(Content1)&&!regDate5.test(Content1)&&!regDate6.test(Content1)){
	// 		$.MsgBox_Unload.Alert("格式错误提示","例子：2018-05-02,2018-06,2018,06-01,08");
	// 		return false;
	// 	}
	// }
	if(QueryType==undefined||QueryType==""||QueryType==null||QueryType=="on"){
		$.MsgBox_Unload.Alert("提示","请选择搜索类别");
		return false;
	}else if(QueryType=="singleSelect"){
		if(Column1==undefined||Column1==""||Column1==null||Column1=="选择"){
			$.MsgBox_Unload.Alert("提示","请选择搜索字段");
			return false;
		}else if(Content1==""||Content1==null||Content1==undefined){
			$.MsgBox_Unload.Alert("提示","搜索内容为空");
			return false;
		}else{
			searchTABRenderAjax(icurPage,QueryType,Column1,Content1);
		}
	}else if(QueryType=="mixSelect"){
		var Column2 = $(".m_button_r_l .input-group-btn button").attr("title");
		var Content2 = $(".m_button_r_l input").val().trim();
		var tempColumn1,tempColumn2,tempContent1,tempContent2;
		if(Column1==undefined||Column1==""||Column1==null||Column1=="选择"){
			tempColumn1=1;
		}
		if(Column2==undefined||Column2==""||Column2==null||Column2=="选择"){
			tempColumn2=1;
		}
		if(Content1==""||Content1==null||Content1==undefined){
			tempContent1=1;
		}
		if(Content2==""||Content2==null||Content2==undefined){
			tempContent2=1;
		}
		// if(Column2=="发布时间"||Column2=="阶段预计完成时间"||Column2=="项目预计完成时间"||Column2=="阶段实际完成时间"){
		// 	if(!regDate1.test(Content2)&&!regDate2.test(Content2)&&!regDate3.test(Content2)&&!regDate4.test(Content2)&&!regDate5.test(Content2)&&!regDate6.test(Content2)){
		// 		$.MsgBox_Unload.Alert("格式错误提示","例子：2018-05-02,2018-06,2018,06-01,08");
		// 		return false;
		// 	}
		// }
		if(tempColumn1==1||tempColumn2==1){
			$.MsgBox_Unload.Alert("提示","请选择搜索字段");
			return false;
		}else if(tempContent1==1||tempContent2==1){
			$.MsgBox_Unload.Alert("提示","搜索内容为空");
			return false;
		}else{
			searchTABRenderAjax(icurPage,QueryType,Column1,Content1,Column2,Content2);
		}
	}
}


// 搜索初始化
function sosuoInit(){
    $("input[name='querytype'][value='singleSelect']").prop("checked","checked");
    $("input[name='querytype'][value='singleSelect']").parent().addClass("active").css("color","#333");
    $("input[name='querytype'][value='mixSelect']").prop("checked",false);
    $("input[name='querytype'][value='mixSelect']").parent().removeClass("active").css("color","#fff");
    $(".m_button_r_l .input-group").fadeOut(100);
    $(".m_button_r_l .input-group-btn button").html("选择<span class='caret'></span>");
    // $(".m_button_r_l .input-group-btn button").text("选择");
    $(".m_button_r_l .input-group-btn button").attr("title","");
    $(".m_button_r_l .input-group input").val("");
    $(".m_button_r_m .input-group-btn button").html("选择<span class='caret'></span>");
    $(".m_button_r_m .input-group-btn button").attr("title","");
    $(".m_button_r_m .input-group input").val("");
}

// 计算服务完成报告item序号
function calcServiceNo(){
	$(".serviceReport_table tbody tr").each(function(){
		var No = $(this).index() + 1;
		$(this).find(".NoService").text(No);
	});
}

// 可填项
function canFillItem(){
	$(".service_ServiceItem, .service_Remarks").prop("contenteditable","true");
}

// 删除权限判断
function deleteServiceReport(){
	if($("#DeleteServiceReport_span").length && $("#DeleteServiceReport_span").text()=="是"){
		$(".t2, .t3, .t4, .t5, .t6, .t7, .t8, .t12").css({
			"min-width":"118.125px",
			"width":"11.8125%"
		});
		$(".delete_report_td").css("border-right","0px solid #999");
		$(".t12, .delete_report_td").show();
	}
}

// 表格拖拽
function tableCanDrag(){
	$(".m_table .JCLRgrips").remove();
	$(".m_table table").colResizable({
	    gripInnerHtml: '<span class="glyphicon glyphicon-resize-horizontal" aria-hidden="true"></span>',
	    partialRefresh: true,
	    // postbackSafe: true,
	    minWidth: 53
	});
}

/*****页面加载完成*****/
$(function(){
	sosuoInit();
	tableRenderAjax(1);
	// 暂时注释
	// $.ajax({
	// 	type: "GET",
	// 	url: "GetStaffInfo",
	// 	data: {
	// 		param: "",
	// 		Type: "staff"
	// 	},
	// 	dataType: "json",
	// 	success: function(data){
	// 		var str = '<option value="" disabled>请选择</option>';
	// 		data.map(function(currentValue,index,arr){
	// 			if(index>0){
	// 				str+='<option value="'+currentValue.StaffName+'" data-id="'+currentValue.ID+'">'+currentValue.StaffName+'</option>';
	// 			}
	// 		});
	// 		$("#addStaffName, #updateStaffName").empty().append(str);
	// 	},
	// 	error: function(){
	// 		$.MsgBox_Unload.Alert("提示","服务器繁忙！获取员工姓名失败");
	// 	}
	// });
	// 
	// deleteServiceReport();
	// setTimeout(tableCanDrag,100);
	$("#exampleModal").modal({keyboard: true, show: false});
});


/*****
* event listener
*****/
// 打开
$(".m_button_l input[value='添加']").on("click",function(){
	var staffCode;
	$.ajax({
		type: "GET",
		url: "ServiceReport",
		data: {
			LoadType: "staffCode"
		},
		dataType: 'text',
		success: function(res){
			staffCode = res;
		},
		error: function(){
			$.MsgBox_Unload.Alert("提示","服务器繁忙！");
		},
		complete: function(XMLHttpRequest, textStatus){
		    if(textStatus=="success"){
		    	$(".add_NonStandard_body_in [class^='info_']").each(function(){
		    		// if($(this).prop("tagName")=="SELECT"||$(this).prop("tagName")=="select"){
		    		// 	$(this).val("0");
		    		// }else{
		    		// 	$(this).val("");
		    		// }
		    		if($(this).is(".info_Number")){
		    			$(this).val(staffCode);
		    			return true;
		    		}
		    		$(this).val("");
		    	});
		    	$(".bg_cover").slideDown(350);
		    	$(".add_NonStandard").slideDown(350);
		    }
		}
	});
});

$(document).on("click",".update_td",function(){
	// jQuery.data($(this), "customerId");
	updateSubmitObj.ID = Number($(this).data("iid")).toString();
	var that = $(this);
	$(".update_NonStandard_body_in [class^='info_']").each(function(){
		var subClassName = $(this).attr("class").split(" ")[0].replace("info_","hastd_");
		var oldVal = that.siblings("."+subClassName).text();
		var newVal = globalDataHandle(oldVal,"");
		$(this).val(newVal);
	});
	$(".bg_cover").slideDown(350);
	$(".update_NonStandard").slideDown(350);
});

// 关闭
$("#NonStandard_addclose, .add_NonStandard_tit_r").on("click",function(){
	for(var k in addSubmitObj){
		addSubmitObj[k] = null;
	}
	$(".bg_cover").slideUp(350);
	$(".add_NonStandard").slideUp(350);
});

$("#NonStandard_updateclose, .update_NonStandard_tit_r").on("click",function(){
	for(var k in updateSubmitObj){
		updateSubmitObj[k] = null;
	}
	$(".bg_cover").slideUp(350);
	$(".update_NonStandard").slideUp(350);
});

// 翻页功能
$("#jumpNumber").on("input propertychange",function(){
    var newVal = $(this).val().replace(/[^\d]/g,'');
    $(this).val(newVal);
});

	// 翻页
$("#fistPage").click(function(){
    var currentPage =1;
    if(hasSearch==0){
    	tableRenderAjax(currentPage);
    }else if(hasSearch==1){
    	searchGetParam(currentPage);
    }
});

$("#lastPage").click(function(){
    var currentPage =Number($("#allPage").text());
    if(hasSearch==0){
    	tableRenderAjax(currentPage);
    }else if(hasSearch==1){
    	searchGetParam(currentPage);
    }
});

$("#upPage").click(function(){
    var currentPage = Number($("#currentPage").text());
    if(currentPage == 1){
        return;
    }else{
        currentPage--;
        if(hasSearch==0){
        	tableRenderAjax(currentPage);
        }else if(hasSearch==1){
        	searchGetParam(currentPage);
        }
    }
});

$("#nextPage").click(function(){
    var currentPage = Number($("#currentPage").text());
    var pageCounts = Number($("#allPage").text());
    if(currentPage == pageCounts){
        return;
    }else{
        currentPage++;
        if(hasSearch==0){
        	tableRenderAjax(currentPage);
        }else if(hasSearch==1){
        	searchGetParam(currentPage);
        }
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
        if(hasSearch==0){
        	tableRenderAjax(currentPage);
        }else if(hasSearch==1){
        	searchGetParam(currentPage);
        }
    }
});

// 添加提交
$("#NonStandard_addsubmit").on("click",function(){
	var info_Number_arr = $(".add_NonStandard .info_Number").val().trim().split("-");
	console.log(info_Number_arr);
	console.log(info_Number_arr.length);
	if(info_Number_arr.length != 3){
		$.MsgBox_Unload.Alert("报告编号格式提示","格式错误！示例：SRV-T023-001");
		return false;
	}else{
		if($.inArray("", info_Number_arr)>-1 || $.inArray(undefined, info_Number_arr)>-1){
			$.MsgBox_Unload.Alert("报告编号格式提示","格式错误！示例：SRV-T023-002");
			return false;
		}
	}
	for(var kk in addSubmitObj){
		if(kk=="Type"){
			addSubmitObj[kk] = "add";
			continue;
		}
		addSubmitObj[kk] = $(".add_NonStandard").find(".info_"+kk).val();
	}
	// 表单验证
	for(var kkk in addSubmitObj){
		addSubmitObj[kkk] = globalDataHandle(addSubmitObj[kkk],"").trim();
		if(kkk=="Number"&&addSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未填写报告编号！");
			return false;
		}
		if(kkk=="Project"&&addSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择项目！");
			return false;
		}
		if(kkk=="FileName"&&addSubmitObj[kkk].indexOf(".")>-1){
			$.MsgBox_Unload.Alert("提示","文件名不能含有“.”！");
			return false;
		}
	}
	console.log("add我执行了吗");
	console.log(addSubmitObj);
	$.ajax({
		type: "POST",
		url: "ServiceReport",
		dataType: 'text',
		data: addSubmitObj,
		success: function(data){
			console.log(typeof data);
			if(data=="true"){
				$.MsgBox_Unload.Alert("提示","添加成功！");
				$("#NonStandard_addclose").trigger("click");
			}else if(data=="false"){
				$.MsgBox_Unload.Alert("提示","添加失败！");
			}
		},
		error:function(){
			$.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
		},
		complete: function(XMLHttpRequest, textStatus){
		    if(textStatus=="success"){
				tableRenderAjax(1);
				hasSearch = 0;
    			sosuoInit();
		    }
		}
	});
});

// 修改提交
$("#NonStandard_updatesubmit").on("click",function(){
	var info_Number_arr = $(".update_NonStandard .info_Number").val().trim().split("-");
	if(info_Number_arr.length != 3){
		$.MsgBox_Unload.Alert("报告编号格式提示","格式错误！示例：SRV-T023-001");
		return false;
	}else{
		if($.inArray("", info_Number_arr)>-1 || $.inArray(undefined, info_Number_arr)>-1){
			$.MsgBox_Unload.Alert("报告编号格式提示","格式错误！示例：SRV-T023-002");
			return false;
		}
	}
	for(var kk in updateSubmitObj){
		if(kk=="Type"){
			updateSubmitObj[kk] = "update";
			continue;
		}
		if(kk=="ID"){
			continue;
		}
		updateSubmitObj[kk] = $(".update_NonStandard").find(".info_"+kk).val();
	}
	// 表单验证
	for(var kkk in updateSubmitObj){
		updateSubmitObj[kkk] = globalDataHandle(updateSubmitObj[kkk],"").trim();
		if(kkk=="Number"&&updateSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未填写报告编号！");
			return false;
		}
		if(kkk=="Project"&&updateSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择项目！");
			return false;
		}
		if(kkk=="FileName"&&updateSubmitObj[kkk].indexOf(".")>-1){
			$.MsgBox_Unload.Alert("提示","文件名不能含有“.”！");
			return false;
		}
	}
	console.log("update我执行了吗");
	console.log(updateSubmitObj);
	$.ajax({
		type: "POST",
		url: "ServiceReport",
		dataType: 'text',
		data: updateSubmitObj,
		success: function(data){
			console.log(typeof data);
			if(data=="true"){
				$.MsgBox_Unload.Alert("提示","修改成功！");
				$("#NonStandard_updateclose").trigger("click");
			}else if(data=="false"){
				$.MsgBox_Unload.Alert("提示","修改失败！");
			}
		},
		error:function(){
			$.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
		},
		complete: function(XMLHttpRequest, textStatus){
		    if(textStatus=="success"){
				tableRenderAjax(1);
				hasSearch = 0;
    			sosuoInit();
		    }
		}
	});
});

// 搜索功能
$(".m_button_r_top label").on("click",function(){
    if($(this).children().val()=="singleSelect"){
        $(this).css("color","#333");
        $(this).siblings("label").css("color","#fff");
        $(".m_button_r_l div.input-group").fadeOut(100);
    }else{
        $(this).css("color","#333");
        $(this).siblings("label").css("color","#fff");
        $(".m_button_r_l div.input-group").fadeIn(100);
    }
});
$(".m_button_r_l .dropdown-menu li").on("click",function(){
	var inText = $(this).text();
	var inTitle = $(this).attr("title");
	$(this).parent().siblings("button").text(inTitle);
	$(this).parent().siblings("button").attr("title",inText);
});
$(".m_button_r_m .dropdown-menu li").on("click",function(){
	var inText = $(this).text();
	var inTitle = $(this).attr("title");
	$(this).parent().siblings("button").text(inTitle);
	$(this).parent().siblings("button").attr("title",inText);
});

// 取消搜索
$("#NonStandard_cancel").on("click",function(){
	hasSearch = 0;
	sosuoInit();
	tableRenderAjax(1);
	for(var k in addSubmitObj){
		addSubmitObj[k] = null;
	}
	for(var kk in updateSubmitObj){
		updateSubmitObj[kk] = null;
	}
	$("#jumpNumber").val("");
});

// 搜索请求
$("#NonStandard_search").on("click",function(){
	searchGetParam(1);
});

// 表格悬浮
$(document).on("mouseenter",".m_table tbody td",function(){
	$(this).parent().css({"border":"2px solid red"});
});
$(document).on("mouseleave",".m_table tbody td",function(){
	$(this).parent().css({"border":"1px solid transparent"});
});

// 服务完成报告模板
$(document).on("click",".reportView",function(){
	ReportID = $(this).siblings(".update_td").data("iid");
	ReportNumber = $(this).siblings(".hastd_Number").text();
	curFileName = $(this).siblings(".hastd_FileName").text();
	var filenameHook = ReportNumber.replace("SRV-","").replace("-","");
	if(curFileName==""||curFileName=="--"){
		down_FileName = filenameHook;
	}else{
		down_FileName = curFileName;
	}
	// setTimeout(function(){
	// 	$("#input_down_file")[0].focus();
	// },50);
	for(var ii in downloadObj){
		if(ii == "Type"){
			downloadObj[ii] = "download";
			continue;
		}
		if(ii == "FileName"){
			continue;
		}
		if(ii == "PreviewJson"){
			continue;
		}
		downloadObj[ii] = $(this).siblings(".hastd_"+ii).text();
	}
	$.ajax({
		type: "GET",
		url: "ServiceReportPreview",
		data: {
			ReportID: ReportID
		},
		dataType: "json"
	}).then(function(data){
		var str = '';
		data.map(function(currentValue,index,arr){
			if(index>0){
				str+='<tr>'+
	                    '<td class="item-No" data-id="'+currentValue.ID+'"><span class="glyphicon glyphicon-remove-circle delete_item"></span>&nbsp;<span class="NoService"></span></td>'+
	                    '<td class="service_ServiceItem">'+currentValue.ServiceItem+'</td>'+
	                    '<td class="service_Isfinished">'+currentValue.Isfinished+'</td>'+
	                    '<td class="service_Remarks">'+currentValue.Remarks+'</td>'+
	                    '<td class="service_ConfirmedSignature"><span class="glyphicon glyphicon-pencil iSignature"></span><img src="data:image/png;base64,'+currentValue.ConfirmedSignature+'" style="max-height: 60px; max-width: 110px;"></td>'+
	                    '<td class="service_ConfirmDate"><span class="glyphicon glyphicon-pencil iSignature"></span><img src="data:image/png;base64,'+currentValue.ConfirmDate+'" style="max-height: 60px; max-width: 110px;"></td>'+
	                '</tr>';
			}
		});
		$(".serviceReport_table tbody").empty().append(str);
		calcServiceNo();
		canFillItem();
		$(".serviceReport_div").slideDown(200);
		$(".bg_cover").slideDown(200);
	},function(){
		$.MsgBox_Unload.Alert("提示", "网络繁忙！");
	}).always(function(){

	});
});

$(".serviceReport_top_r").click(function(){
	for(var k in downloadObj){
		downloadObj[k] = null;
	}
	$(".serviceReport_div").slideUp(300);
	$(".bg_cover").slideUp(300);
	curFileName = "";
	down_FileName = "";
	ReportNumber = "";
});

// item添加
$("#add_serviceReport").on("click",function(){
	$(".serviceReport_table tbody").append(serviceReportStr);
	calcServiceNo();
	canFillItem();
});
// 删除
$(document).on("click",".delete_item",function(){
	$(this).parent().parent().remove();
	calcServiceNo();
});

// 电子签名打开
$(document).on("click",".iSignature",function(){
	var signatureID = $(this).parent().siblings(".item-No").data("id");
	if(signatureID){
		return false;
	}
	$(".wrapper_sign").slideDown(300);
	$(".cover_bg2").slideDown(300);
	$("#signature").empty();
	$("#signature").jSignature();
	var index = $(this).parent().parent().index();
	var classify = $(this).parent().attr("class");
	signatureObj.index = index;
	signatureObj.classify = classify;
});

$(".wrapper_sign_tit_r").click(function(){
	$(".wrapper_sign").slideUp(300);
	$(".cover_bg2").slideUp(300);
});

var $sigdiv = $("#signature");
$("#signature_reset").on("click",function(){
	$sigdiv.jSignature("reset");
});

$("#signature_OK").on("click",function(){
	var datapair = $sigdiv.jSignature("getData", "svgbase64");
	var datapairPng = $sigdiv.jSignature("getData", "image");
	// console.log(datapair);
	var i = new Image();
	i.src = "data:" + datapairPng[0] + "," + datapairPng[1];
	// i.width = "110";
	i.style = "max-height:60px;max-width:110px;";
	// $(i).data("png","data:" + datapairPng[0] + "," + datapairPng[1]);
	// console.log(i);
	// console.log($(i).data("png"));
	var index = signatureObj.index;
	var classify = signatureObj.classify;
	var curTd = $(".serviceReport_table tbody tr").eq(index).find("."+classify);
	curTd.find("img").remove();
	curTd.append(i);
	$(".wrapper_sign").slideUp(300);
	$(".cover_bg2").slideUp(300);
});

// 保存
$(".serviceReport_top_save").click(function(){
	var PreviewJson = [];
	var len = $(".serviceReport_table tbody tr").length;
	if(len == 0){
		$.MsgBox_Unload.Alert("提示", "请先添加一条再保存！");
		return false;
	}
	var isAllData = 1;
	var isFinishVal = 1;
	$(".serviceReport_table tbody tr").each(function(){
		var item = {};
		var iServiceItem = $(this).children(".service_ServiceItem").text().trim();
		if(iServiceItem==""||iServiceItem=="--"){
			isAllData = 0;
			return false;
		}
		var iIsfinished = $(this).children(".service_Isfinished").text();
		if(iIsfinished!="是"&&iIsfinished!="否"){
			isFinishVal = 0;
			return false;
		}
		var ConfirmedSignature;
		var ConfirmDate;
		if(!$(this).children(".service_ConfirmedSignature").find("img").length){
			ConfirmedSignature = "";
		}else{
			var srcVal1 = $(this).children(".service_ConfirmedSignature").find("img").attr("src");
			if(srcVal1 == "data:image/png;base64,"){
				ConfirmedSignature = "";
			}else{
				ConfirmedSignature = srcVal1;
			}
			// data:image/png;base64,
			// ConfirmedSignature = $(this).children(".service_ConfirmedSignature").find("img").data("png");
		}

		if(!$(this).children(".service_ConfirmDate").find("img").length){
			ConfirmDate = "";
		}else{
			var srcVal2 = $(this).children(".service_ConfirmDate").find("img").attr("src");
			if(srcVal2 == "data:image/png;base64,"){
				ConfirmDate = "";
			}else{
				ConfirmDate = srcVal2;
			}
			// ConfirmDate = $(this).children(".service_ConfirmDate").find("img").attr("src");
			// ConfirmDate = $(this).children(".service_ConfirmDate").find("img").data("png");
		}
		
		item.ServiceItem = iServiceItem;
		item.Isfinished = iIsfinished;
		item.Remarks = $(this).children(".service_Remarks").text().trim();
		item.ConfirmedSignature = ConfirmedSignature;
		item.ConfirmDate = ConfirmDate;
		PreviewJson.push(item);
	});
	if(isAllData==0){
		$.MsgBox_Unload.Alert("提示", "有完成内容项未填！");
		return false;
	}
	if(isFinishVal==0){
		$.MsgBox_Unload.Alert("提示", "有是否达标项未填！");
		return false;
	}
	
	var PreviewJsonStr = $.fn.stringifyArr(PreviewJson);
	// downloadObj.PreviewJson = PreviewJsonStr;
	var iNumber = ReportNumber;
	$.ajax({
		type: "POST",
		url: "ServiceReportPreview",
		data: {
			Type: "save",
			PreviewJson: PreviewJsonStr,
			ReportID: ReportID,
			Number: iNumber
		},
		dataType: "text"
	}).then(function(data){
		console.log(typeof data);
		if(data=="true"){
			$.MsgBox_Unload.Alert("提示", "保存成功！");
			// $(".serviceReport_top_r").trigger("click");
		}else if(data=="false"){
			$.MsgBox_Unload.Alert("提示", "保存失败！");
		}
	},
	function(){
		$.MsgBox_Unload.Alert("提示", "服务器繁忙，保存有误！");
	});
});

// 选择是否达标
$(document).on("click",".service_Isfinished",function(e){
  e.stopPropagation();
  if($(this).text()=="是"||$(this).text()=="否"){
    $(this).text("");
    var str11 = '<select class="isFinish_sel">'+
                    '<option value="0">请选择</option>'+
                    '<option value="是">是</option>'+
                    '<option value="否">否</option>'+
                    '</select>';
    $(this).html(str11);
  }
});

// 选择是否
$(document).on("change",".isFinish_sel",function(){
  var ival = $(this).val();
  if(ival == 0){
    return;
  }else{
    var that = $(this).parent();
    $(this).detach();
    that.html("").text(ival);
  }
});

// 下载
$(".serviceReport_top_down").click(function(){
	// $.MsgBox_Unload.Alert("提示", "下载前请记得保存！");
	var PreviewJson = [];
	var len = $(".serviceReport_table tbody tr").length;
	if(len == 0){
		$.MsgBox_Unload.Alert("提示", "请先添加一条再下载！");
		return false;
	}
	var isAllData = 1;
	var isFinishVal = 1;
	$(".serviceReport_table tbody tr").each(function(){
		var item = {};
		var iServiceItem = $(this).children(".service_ServiceItem").text().trim();
		if(iServiceItem==""||iServiceItem=="--"){
			isAllData = 0;
			return false;
		}
		var iIsfinished = $(this).children(".service_Isfinished").text();
		if(iIsfinished!="是"&&iIsfinished!="否"){
			isFinishVal = 0;
			return false;
		}
		var ConfirmedSignature;
		var ConfirmDate;
		if(!$(this).children(".service_ConfirmedSignature").find("img").length){
			ConfirmedSignature = "";
		}else{
			var srcVal1 = $(this).children(".service_ConfirmedSignature").find("img").attr("src");
			if(srcVal1 == "data:image/png;base64,"){
				ConfirmedSignature = "";
			}else{
				ConfirmedSignature = srcVal1;
			}
		}
		if(!$(this).children(".service_ConfirmDate").find("img").length){
			ConfirmDate = "";
		}else{
			var srcVal2 = $(this).children(".service_ConfirmDate").find("img").attr("src");
			if(srcVal2 == "data:image/png;base64,"){
				ConfirmDate = "";
			}else{
				ConfirmDate = srcVal2;
			}
		}
		
		item.ServiceItem = iServiceItem;
		item.Isfinished = iIsfinished;
		item.Remarks = $(this).children(".service_Remarks").text().trim();
		item.ConfirmedSignature = ConfirmedSignature;
		item.ConfirmDate = ConfirmDate;
		PreviewJson.push(item);
	});
	if(isAllData==0){
		$.MsgBox_Unload.Alert("提示", "有完成内容项未填！");
		return false;
	}
	if(isFinishVal==0){
		$.MsgBox_Unload.Alert("提示", "有是否达标项未填！");
		return false;
	}
	
	var PreviewJsonStr = $.fn.stringifyArr(PreviewJson);
	downloadObj.PreviewJson = PreviewJsonStr;
	// var newFileName = $("#input_down_file").val().trim();
	// if(newFileName==""||newFileName=="--"||newFileName.indexOf(".")>-1){
	// 	$.MsgBox_Unload.Alert("文件名格式提示", "不能为空或含有“.”等特殊符号！");
	// 	return false;
	// }
	downloadObj.FileName = down_FileName+".doc";
	$.ajax({
		type: "POST",
		url: "ServiceReportPreview",
		data: downloadObj,
		dataType: "text",
		beforeSend: function(XMLHttpRequest){
		    $(".serviceReport_top_down").attr("disabled","disabled");
		    $(".serviceReport_top_down").css({
		      "cursor":"not-allowed"
		    });
		},
		success: function(data){
			var baseHref = window.location.href.split("cfChicken8")[0]+"cfChicken8/";
		  	window.location.href = baseHref+data;
		},
		error:function(){
		  $.MsgBox_Unload.Alert("提示","网络繁忙！");
		},
		complete: function(XMLHttpRequest, textStatus){
		    $(".serviceReport_top_down").attr("disabled",false);
		    $(".serviceReport_top_down").css({
		      "cursor":"pointer"
		    });
		}
	});
});

// 删除记录模态框弹出
$(document).on("click",".delete_report_td input",function(){
	// $(window).trigger("resize");
	delete_email_curPage = Number($(".pageInfo #currentPage").text());
	delete_ID = $(this).parent().siblings(".update_td").data("iid");
	delete_res_flag = null;
	$(".cover_bg3").slideDown(200);
	$('#exampleModal').modal('show');
	// $.ajax({
	// 	type: "POST",
	// 	url: "ServiceReport",
	// 	data: {
	// 		Type: "delete",
	// 		ID: ID
	// 	},
	// 	dataType: "text"
	// }).then(function(data){
	// 	if(data.indexOf("没有权限")>-1){
	// 		$.MsgBox_Unload.Alert("提示","没有权限！");
	// 	}else if(data=="false"){
	// 		$.MsgBox_Unload.Alert("提示","删除失败！");
	// 	}else if(data=="true"){
	// 		$.MsgBox_Unload.Alert("提示","删除成功！");
	// 		if(hasSearch == 0){
	//     		tableRenderAjax(email_curPage);
	//     	}else if(hasSearch == 1){
	//     		searchGetParam(email_curPage);
	//     	}
	// 	}
	// },function(){
	// 	$.MsgBox_Unload.Alert("提示","网络繁忙！");
	// });
});

// 删除模态框关闭事件
$('#exampleModal').on('hide.bs.modal', function (e) {
	$(".cover_bg3").slideUp(200,function(){
		delete_email_curPage = 1;
		delete_ID = null;
	});
});
$('#exampleModal').on('hidden.bs.modal', function (e) {
	if(delete_res_flag == 1){
		$.MsgBox_Unload.Alert("提示","没有权限！");
	}else if(delete_res_flag == 2){
		$.MsgBox_Unload.Alert("提示","删除失败！");
	}else if(delete_res_flag == 3){
		$.MsgBox_Unload.Alert("提示","删除成功！");
		if(hasSearch == 0){
    		tableRenderAjax(delete_email_curPage);
    	}else if(hasSearch == 1){
    		searchGetParam(delete_email_curPage);
    	}
	}else if(delete_res_flag == 4){
		$.MsgBox_Unload.Alert("提示","网络繁忙！");
	}else{
		// $.MsgBox_Unload.Alert("提示","网络繁忙！");
	}
});

// 删除记录
$(document).on("click","#delete_service_yes",function(){
	if(delete_ID){
		var dom1 = $("#delete_service_yes");
		globalBtnNotAllow(dom1);
		$.ajax({
			type: "POST",
			url: "ServiceReport",
			data: {
				Type: "delete",
				ID: delete_ID
			},
			dataType: "text"
		}).then(function(data){
			if(data.indexOf("没有权限")>-1){
				delete_res_flag = 1;
			}else if(data=="false"){
				delete_res_flag = 2;
			}else if(data=="true"){
				delete_res_flag = 3;
			}
		},function(){
			delete_res_flag = 4;
		}).always(function(){
			globalBtnAllow(dom1);
			$('#exampleModal').modal('hide');
		});
	}
});

// 验证非法文件名
$(".info_FileName").on("blur",function(e){
	var iVal = $(this).val().trim();
	if(iVal==""){
		$(this).val("");
		return false;
	}
	if(fileIllegalChar.test(iVal)){
		$.MsgBox_Unload.Alert("文件名提示","有非法字符！");
		var iiVal = iVal.replace(fileReplaceIllegalChar,"");
		$(this).val(iiVal);
	}else{
		console.log("无");
	}
});

// $(window).on("resize",function(){
// 	var iw = $("#NonStandard_wrapper").width();
// 	var ih = $("#NonStandard_wrapper").height();
// 	$(".bg_cover, .cover_bg2, .cover_bg3").css({"width":iw+"px", "height":ih+"px"});
// });