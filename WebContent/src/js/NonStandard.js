/*****函数定义与变量定义*****/
var addSubmitObj = new Object();
addSubmitObj.Type = null;
addSubmitObj.CustomerID = null;
addSubmitObj.ProjectName = null;
addSubmitObj.ProjectStage = null;
addSubmitObj.Issuer = null;
addSubmitObj.ResponsibleMan = null;
addSubmitObj.PublishDate = null;
addSubmitObj.StageExpectedDate = null;
addSubmitObj.ProjectExpectedDate = null;
addSubmitObj.ProjectProgress = null;
addSubmitObj.StageActualDate = null;
var updateSubmitObj = new Object();
updateSubmitObj.ID = null;
updateSubmitObj.Type = null;
updateSubmitObj.CustomerID = null;
updateSubmitObj.ProjectName = null;
updateSubmitObj.ProjectStage = null;
updateSubmitObj.Issuer = null;
updateSubmitObj.ResponsibleMan = null;
updateSubmitObj.PublishDate = null;
updateSubmitObj.StageExpectedDate = null;
updateSubmitObj.ProjectExpectedDate = null;
updateSubmitObj.ProjectProgress = null;
updateSubmitObj.StageActualDate = null;
var hasSearch = 0;
var regDate1 = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
var regDate2 = /^[1-9]\d{3}-(0[1-9]|1[0-2])$/;
var regDate3 = /^(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
var regDate4 = /^[1-9]\d{3}$/;
var regDate5 = /^(0[1-9]|1[0-2])$/;
var regDate6 = /^(0[1-9]|[1-2][0-9]|3[0-1])$/;
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
		var delete_str;
		if(data[i].isPublished=="未发布"){
			delete_str = '<input type="button" value="删除">';
		}else if(data[i].isPublished=="已发布"){
			delete_str = "";
		}
	    str+='<tr>'+
	            '<td class="update_td" data-iid="'+data[i].ID+'" data-customer-id="'+data[i].CustomerID+'">'+parseInt((icurPage-1)*10 + i)+'</td>'+
	            '<td class="hastd_CustomerName" title="'+data[i].CustomerName+'">'+data[i].CustomerName+'</td>'+
	            '<td class="hastd_ProjectName" title="'+data[i].ProjectName+'">'+data[i].ProjectName+'</td>'+
	            '<td class="hastd_ProjectStage" title="'+data[i].ProjectStage+'">'+data[i].ProjectStage+'</td>'+
	            '<td class="hastd_ResponsibleMan" title="'+data[i].ResponsibleMan+'">'+data[i].ResponsibleMan+'</td>'+
	            '<td class="hastd_PublishDate" title="'+data[i].PublishDate+'">'+data[i].PublishDate+'</td>'+
	            '<td class="hastd_StageExpectedDate" title="'+data[i].StageExpectedDate+'">'+data[i].StageExpectedDate+'</td>'+
	            '<td class="hastd_ProjectExpectedDate" title="'+data[i].ProjectExpectedDate+'">'+data[i].ProjectExpectedDate+'</td>'+
	            '<td class="hastd_ProjectProgress" title="'+data[i].ProjectProgress+'">'+data[i].ProjectProgress+'</td>'+
	            '<td class="hastd_StageActualDate" title="'+data[i].StageActualDate+'">'+data[i].StageActualDate+'</td>'+
	            '<td class="isPublished"><input type="button" value="'+data[i].isPublished+'"></td>'+
	            '<td class="hastd_Issuer" style="display:none;">'+data[i].Issuer+'</td>'+
	            '<td class="delete_td">'+delete_str+'</td>'+
	        '</tr>';
	}
	$(".m_table table tbody").empty().append(str);
	$(".m_page #currentPage").text(icurPage);
	$(".m_page #allPage").text(pageCounts);
}

// 表格渲染ajax请求
function tableRenderAjax(icurPage){
	$.ajax({
		type:"GET",
		url:"NonStandard",
		data:{
			LoadType: "data",
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
			type:"GET",
			url:"NonStandard",
			data:{
				LoadType: "data",
				QueryType: QueryType,
				Column1: Column1,
				Content1: Content1,
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
	}else{
		// 组合查询
		$.ajax({
			type:"GET",
			url:"NonStandard",
			data:{
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
	if(Column1=="发布时间"||Column1=="阶段预计完成时间"||Column1=="项目预计完成时间"||Column1=="阶段实际完成时间"){
		if(!regDate1.test(Content1)&&!regDate2.test(Content1)&&!regDate3.test(Content1)&&!regDate4.test(Content1)&&!regDate5.test(Content1)&&!regDate6.test(Content1)){
			$.MsgBox_Unload.Alert("格式错误提示","例子：2018-05-02,2018-06,2018,06-01,08");
			return false;
		}
	}
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
		if(Column2=="发布时间"||Column2=="阶段预计完成时间"||Column2=="项目预计完成时间"||Column2=="阶段实际完成时间"){
			if(!regDate1.test(Content2)&&!regDate2.test(Content2)&&!regDate3.test(Content2)&&!regDate4.test(Content2)&&!regDate5.test(Content2)&&!regDate6.test(Content2)){
				$.MsgBox_Unload.Alert("格式错误提示","例子：2018-05-02,2018-06,2018,06-01,08");
				return false;
			}
		}
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

/*****页面加载完成*****/
$(function(){
	sosuoInit();
	tableRenderAjax(1);
});


/*****
* event listener
*****/
// 打开
$(".m_button_l input[value='添加']").on("click",function(){
	$(".add_NonStandard_body_in [class^='info_']").val("");
	$(".bg_cover").slideDown(350);
	$(".add_NonStandard").slideDown(350);
});

$(document).on("click",".update_td",function(){
	// jQuery.data($(this), "customerId");
	updateSubmitObj.ID = Number($(this).data("iid")).toString();
	updateSubmitObj.CustomerID = Number($(this).data("customerId")).toString();
	var that = $(this);
	$(".update_NonStandard_body_in [class^='info_']").each(function(){
		var subClassName = $(this).attr("class").split(" ")[0].replace("info_","hastd_");
		$(this).val(that.siblings("."+subClassName).text());
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

// add搜索客户信息
$(document).on("input propertychange",'.add_NonStandard .info_CustomerName',function(){
    if($(this).val()==""){
        return;
    }
    var addKeyword = $(this).val().trim();
    if(addKeyword == "" || addKeyword == "--"){
        $.MsgBox_Unload.Alert("提示", "客户搜索值为空或格式错误");
        return;
    }else{
        var CustomerName = $(this).val();
        $.ajax({
            type : 'GET',
            url : 'GetCustomer',
            data: {
                CustomerName: CustomerName
            },
            success : function (data) { 
                var newData = JSON.parse(data);
                console.log(newData);
                var str='';
                if(newData.length>1){
                    for(var i = 1;i<newData.length;i++){
                        str+='<option value="'+newData[i].CustomerName+'" text="'+newData[i].ID+'" contact="'+newData[i].Contact+'" contactinfo="'+newData[i].ContactInfo1+'" area="'+newData[i].Area+'" city="'+newData[i].City+'" email="'+newData[i].Email+'">'+newData[i].CustomerName+'&nbsp;:&nbsp;'+newData[i].Contact+'</option>';
                    }
                }
                $("#add_CustomerName").empty().append(str);
                $('#add_CustomerName').fadeIn(200);
            },
            error : function () {
                $.MsgBox_Unload.Alert("提示", "服务器繁忙，客户名称获取有误！");
            }
        });
    }
});

// update搜索客户信息
$(document).on("input propertychange",'.update_NonStandard .info_CustomerName',function(){
    if($(this).val()==""){
        return;
    }
    var addKeyword = $(this).val().trim();
    if(addKeyword == "" || addKeyword == "--"){
        $.MsgBox_Unload.Alert("提示", "客户搜索值为空或格式错误");
        return;
    }else{
        var CustomerName = $(this).val();
        $.ajax({
            type : 'GET',
            url : 'GetCustomer',
            data: {
                CustomerName: CustomerName
            },
            success : function (data) { 
                var newData = JSON.parse(data);
                console.log(newData);
                var str='';
                if(newData.length>1){
                    for(var i = 1;i<newData.length;i++){
                        str+='<option value="'+newData[i].CustomerName+'" text="'+newData[i].ID+'" contact="'+newData[i].Contact+'" contactinfo="'+newData[i].ContactInfo1+'" area="'+newData[i].Area+'" city="'+newData[i].City+'" email="'+newData[i].Email+'">'+newData[i].CustomerName+'&nbsp;:&nbsp;'+newData[i].Contact+'</option>';
                    }
                }
                $("#update_CustomerName").empty().append(str);
                $('#update_CustomerName').show();
            },
            error : function () {
                $.MsgBox_Unload.Alert("提示", "服务器繁忙，客户名称获取有误！");
            }
        });
    }
});

// 点击其他关闭
$(document).on("click", function() {
    $('#add_CustomerName').fadeOut(200);
    $('#update_CustomerName').fadeOut(200);
    $('.ProjectName_select').fadeOut(200);
});

$(document).on("click", '#add_CustomerName option', function(e) {
	e.stopPropagation();
    $('.add_NonStandard .info_CustomerName').val($('#add_CustomerName').val());
    addSubmitObj.CustomerID = $(this).attr("text");
    // $('.add_keysight .info_customer_attn').val($(this).attr("contact"));
    $('#add_CustomerName').hide();
});
$(document).on("click", '#update_CustomerName option', function(e) {
	e.stopPropagation();
    $('.update_NonStandard .info_CustomerName').val($('#update_CustomerName').val());
    updateSubmitObj.CustomerID = $(this).attr("text");
    $('#update_CustomerName').hide();
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
	for(var kk in addSubmitObj){
		if(kk=="Type"){
			addSubmitObj[kk] = "add";
			continue;
		}
		if(kk=="CustomerID"){
			continue;
		}
		addSubmitObj[kk] = $(".add_NonStandard").find(".info_"+kk).val();
	}
	// 表单验证
	for(var kkk in addSubmitObj){
		addSubmitObj[kkk] = globalDataHandle(addSubmitObj[kkk],"").trim();
		if(kkk=="CustomerID"&&addSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择客户！");
			return false;
		}
		if(kkk=="StageExpectedDate"&&addSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择阶段预计完成时间！");
			return false;
		}
	}
	// console.log("add我执行了吗");
	// console.log(addSubmitObj);
	$.ajax({
		type:"POST",
		url:"NonStandardOperate",
		dataType:'text',
		data:addSubmitObj,
		success:function(data){
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
	for(var kk in updateSubmitObj){
		if(kk=="Type"){
			updateSubmitObj[kk] = "update";
			continue;
		}
		if(kk=="CustomerID"){
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
		if(kkk=="CustomerID"&&updateSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择客户！");
			return false;
		}
		if(kkk=="StageExpectedDate"&&updateSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择阶段预计完成时间！");
			return false;
		}
	}
	console.log("update我执行了吗");
	console.log(updateSubmitObj);
	$.ajax({
		type:"POST",
		url:"NonStandardOperate",
		dataType:'text',
		data:updateSubmitObj,
		success:function(data){
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

// 发布按钮事件
$(document).on("click",".isPublished input",function(){
    $(".bg_cover").slideDown(200);
    $(".publish_div").slideDown(200);
    $(".publish_div").attr("value",$(this).parent().siblings("td.update_td").data("iid"));
    if($(this).val()=="未发布"){
        $(".publish_div_body").text("是否发布？");
        var str1 = '<input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" id="publish_yes" value="是">'+
        '<input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" id="publish_no" value="否">';
        $(".publish_div_foot_in").html(str1);
    }else if($(this).val()=="已发布"){
        $(".publish_div_body").text("您已发布过！");
        var str2 = '<input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" id="has_publish" value="好的">';
        $(".publish_div_foot_in").html(str2);
    }
});

// 是 否 好的
$(document).on("click","#publish_no, #has_publish",function(){
    $(".publish_div").slideUp(200);
    $(".bg_cover").slideUp(200);
});
$(document).on("click","#publish_yes",function(){
    var email_curPage = Number($(".pageInfo #currentPage").text());
    var email_data = {};
    var ID = $(".publish_div").attr("value");
    var currentTr = $(".m_table tbody tr").find("td.update_td[data-iid='"+ID+"']").parent();
    var ProjectName = currentTr.find(".hastd_ProjectName").text();
    var ProjectStage = currentTr.find(".hastd_ProjectStage").text();
    var ResponsibleMan = currentTr.find(".hastd_ResponsibleMan").text();
    var PublishDate = currentTr.find(".hastd_PublishDate").text();
    var StageExpectedDate = currentTr.find(".hastd_StageExpectedDate").text();
    var ProjectProgress = currentTr.find(".hastd_ProjectProgress").text();
    
    if(PublishDate == "--"||PublishDate==""||PublishDate=="0000-00-00"){
        $.MsgBox_Unload.Alert("提示","发布时间为空，请检查");
        $("#publish_no").trigger("click");
        return;
    }else if(StageExpectedDate == "--"||StageExpectedDate==""||StageExpectedDate=="0000-00-00"){
        $.MsgBox_Unload.Alert("提示","阶段预计时间为空，请检查");
        $("#publish_no").trigger("click");
        return;
    }else{
        email_data.ID = ID;
        email_data.ProjectName = ProjectName;
        email_data.ProjectStage = ProjectStage;
        email_data.ResponsibleMan = ResponsibleMan;
        email_data.PublishDate = PublishDate;
        email_data.StageExpectedDate = StageExpectedDate;
        email_data.ProjectProgress = ProjectProgress;
        $.ajax({
            type:'GET',
            url:'NonStandardOperate',
            dataType:'text',
            data:email_data,
            success: function(data){
                console.log(data);
                console.log(typeof data);
                $.MsgBox_Unload.Alert("提示",data);
            },
            error: function(){
                $.MsgBox_Unload.Alert("提示","服务器繁忙！");
            },
            complete: function(XMLHttpRequest, textStatus){
			    if(textStatus=="success"){
			    	if(hasSearch == 0){
			    		tableRenderAjax(email_curPage);
			    	}else if(hasSearch == 1){
			    		searchGetParam(email_curPage);
			    	}
	    			$(".publish_div").fadeOut(200);
                	$(".bg_cover").fadeOut(200);
			    }
            }
        });
    }
});

// 删除一条记录
$(document).on("click",".delete_td input",function(){
	var email_curPage = Number($(".pageInfo #currentPage").text());
	var ID = Number($(this).parent().siblings(".update_td").data("iid")).toString();
	$.ajax({
	    type: 'POST',
	    url: 'NonStandardOperate',
	    data: {
	        ID: ID,
	        Type: "delete"
	    },
	    dataType: "text"
	})
	.then(function(data){
	    if(data=="true"){
	    	$.MsgBox_Unload.Alert("删除提示","删除成功！");
	    	if(hasSearch == 0){
	    		tableRenderAjax(email_curPage);
	    	}else if(hasSearch == 1){
	    		searchGetParam(email_curPage);
	    	}
	    }else if(data=="false"){
	    	$.MsgBox_Unload.Alert("删除提示","删除失败！");
	    }
	},function(){
	    $.MsgBox_Unload.Alert("删除提示","服务器繁忙！");
	});
});

// 项目名称搜索
$(document).on("input propertychange",'.info_ProjectName',function(){
	var that = $(this);
    if($(this).val()==""){
        return;
    }
    var addKeyword = $(this).val().trim();
    if(addKeyword == "" || addKeyword == "--"){
        $.MsgBox_Unload.Alert("提示", "项目搜索值为空或格式错误");
        return;
    }else{
        $.ajax({
            type : 'POST',
            url : 'NonStandard',
            data: {
                Key: addKeyword
            },
            dataType: "JSON"
        }).then(function(data){
        	var str = '';
        	data.map(function(currentValue,index,arr){
        		if(index>0){
        			str+='<option value="'+currentValue.ProjectName+'">'+currentValue.ProjectName+'</option>';
        		}
        	});
        	that.siblings("select").empty().append(str).fadeIn(200);
        },function(){
        	$.MsgBox_Unload.Alert("提示", "服务器繁忙，项目名称获取有误！");
        });
    }
});

// 项目名称点击
$(document).on("click",".ProjectName_select option",function(e){
	e.stopPropagation();
	$(this).parent().siblings(".info_ProjectName").val($(this).parent().val());
	$(this).parent().fadeOut(200);
});
