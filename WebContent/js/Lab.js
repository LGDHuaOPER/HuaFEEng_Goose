/*****函数定义与变量定义*****/
var addSubmitObj = new Object();
addSubmitObj.Type = null;
addSubmitObj.CommodityID = null;
addSubmitObj.Number = null;
addSubmitObj.Laboratory = null;
addSubmitObj.Picture = null;
var updateSubmitObj = new Object();
updateSubmitObj.ID = null;
updateSubmitObj.Type = null;
updateSubmitObj.CommodityID = null;
updateSubmitObj.Number = null;
updateSubmitObj.Laboratory = null;
updateSubmitObj.Picture = null;
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
// 详细配置用的记录ID
var LabID;
var searchPartModelIndex = -1;
var curFileupload;
var curModel;
// 维护实验室地点
var LaboratoryArr = ["北京","石家庄","苏州","厦门","深圳","成都"];
// var regDate1 = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
// var regDate2 = /^[1-9]\d{3}-(0[1-9]|1[0-2])$/;
// var regDate3 = /^(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
// var regDate4 = /^[1-9]\d{3}$/;
// var regDate5 = /^(0[1-9]|1[0-2])$/;
// var regDate6 = /^(0[1-9]|[1-2][0-9]|3[0-1])$/;
// 详细配置item字符串
var serviceReportStr = '<tr>'+
		                    '<td class="item-No" data-id=""><span class="glyphicon glyphicon-remove-circle delete_item"></span>&nbsp;<span class="NoService"></span></td>'+
		                    '<td class="service_Model"></td>'+
		                    '<td class="service_Description"></td>'+
		                    '<td class="service_Qty">1</td>'+
	                	'</tr>';

// 定义状态集
var LabState = new Object();
LabState.uploadFileNo = 0;
LabState.uploadFileList = {};

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

// 获取图片文件blob文件流
// function getImageBlob(){

// }

// 插入缩略图
function insertThumbnails(){
	$("td.hastd_Picture").each(function(){
		var ThumbnailsFilename = $(this).attr("title");
		if(ThumbnailsFilename=="" || ThumbnailsFilename=="--"){
			$(this).empty().text("未上传图片");
			return true;
		}
		var that = $(this);
		var url = "GetLabPicture?FileName="+ThumbnailsFilename;
	    var xhr = new XMLHttpRequest();
	    xhr.open('GET', url, true);
	    xhr.responseType = "blob";
	    //xhr.setRequestHeader("client_type", "DESKTOP_WEB");
	    //xhr.setRequestHeader("desktop_web_access_key", _desktop_web_access_key);
	    xhr.onload = function () {
	        if (this.status == 200) {
	            var blob = this.response;
	            globalGetImageDataurl(blob,0.4,that,null,50);
	        }
	    };
	    xhr.send();
	});
}

// 表格渲染
function tableRender(icurPage,pageCounts,data){
	var str = '';
	for(var i =1;i<data.length;i++){
	    str+='<tr>'+
	            '<td class="update_td" data-iid="'+data[i].ID+'" data-commodityid="'+data[i].CommodityID+'">'+parseInt((icurPage-1)*10 + i)+'</td>'+
	            '<td class="hastd_Model" title="'+data[i].Model+'">'+data[i].Model+'</td>'+
	            '<td class="hastd_Description" title="'+data[i].Description+'">'+data[i].Description+'</td>'+
	            '<td class="hastd_Number" title="'+data[i].Number+'">'+data[i].Number+'</td>'+
	            '<td class="hastd_Laboratory" title="'+data[i].Laboratory+'">'+data[i].Laboratory+'</td>'+
	            '<td class="hastd_Picture" title="'+data[i].Picture+'"><img src="image/loading/Spinner-1s-50px.gif" alt="产品图片"></td>'+
	            '<td class="detailed_part" title="详细配置"><span class="glyphicon glyphicon-eye-open"></span></td>'+
	            '<td class="detailed_part" title="详细配置"><span class="glyphicon glyphicon-eye-open"></span></td>'+
	            '<td class="detailed_part" title="详细配置"><span class="glyphicon glyphicon-eye-open"></span></td>'+
	        '</tr>';
	}
	$(".m_table table tbody").empty().append(str);
	$(".m_page #currentPage").text(icurPage);
	$(".m_page #allPage").text(pageCounts);
	insertThumbnails();
}

// 表格渲染ajax请求
function tableRenderAjax(icurPage){
	$.ajax({
		type: "GET",
		url: "Lab",
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

// 计算服务完成报告item序号
function calcServiceNo(){
	$(".serviceReport_table tbody tr").each(function(){
		var No = $(this).index() + 1;
		$(this).find(".NoService").text(No);
	});
}

// 可填项
function canFillItem(){
	$(".service_Qty").prop("contenteditable","true");
}

//上传文件
function uploadFiles(){                                           
    var formData = new FormData();
    formData.enctype="multipart/form-data";
    formData.append("file",$("#serFinRepUpload")[0].files[0]);//append()里面的第一个参数file对应permission/upload里面的参数file         
    formData.append("Category","labPicture");
    $.ajax({
        type: "POST",
        async: true,  //这里要设置异步上传，才能成功调用myXhr.upload.addEventListener('progress',function(e){}),progress的回掉函数
        accept: 'text/html;charset=UTF-8',
        data: formData,
        // contentType:"multipart/form-data",
        url: "UploadLabPicture",
        processData: false, // 告诉jQuery不要去处理发送的数据
        contentType: false, // 告诉jQuery不要去设置Content-Type请求头
        cache: false,
        dataType: "text",
        xhr:function(){                        
            myXhr = $.ajaxSettings.xhr();
            if(myXhr.upload){ // check if upload property exists
                myXhr.upload.addEventListener('progress',function(e){                           
                    var loaded = e.loaded;                  //已经上传大小情况 
                    var total = e.total;                      //附件总大小 
                    var percent = Math.floor(100*loaded/total)+"%";     //已经上传的百分比  
                    console.log("已经上传了："+percent);  
                    var newWidthFloat =  globalToPoint(percent);  
                    var newWidth = newWidthFloat*400;
                    console.log("进度条宽度："+newWidth);   
                    $(".progressIn").css("width",newWidth+"px").text(percent);
                }, false); // for handling the progress of the upload
            }
            return myXhr;
        },                    
        success:function(data){
        	if(data.indexOf("上传成功")>-1){
        		$("span.isUpload").text("上传成功！");
		    	var fileName_add = $("input.serFinRepUploadName").val();
			    $(curFileupload).val(fileName_add).attr("title",fileName_add);
			    $(".dropFileTit span").trigger("click");
        	}else{
    			$("span.isUpload").text("上传失败！");
    			$(".progressIn").css("width","30px").text("0%");
        	}
        	$.MsgBox_Unload.Alert("提示",data);
        },
        error:function(){
        	$("span.isUpload").text("");
        	$(".progressIn").css("width","30px").text("0%");
            $.MsgBox_Unload.Alert("上传提示","网络繁忙！上传失败！");
        }
    });                             
}


/*****页面加载完成*****/
$(function(){
	tableRenderAjax(1);
	// 插入实验室
	var LaboratoryStr = '<option value="" disabled="disabled">请选择</option>';
	LaboratoryArr.map(function(currentValue,index,arr){
		LaboratoryStr+='<option value="'+currentValue+'">'+currentValue+'</option>';
	});
	$("select[name='Laboratory_sel']").empty().append(LaboratoryStr);
	// 表格列宽调整
	setTimeout(function(){
		$(".m_table table").colResizable({
		    gripInnerHtml: '<span class="glyphicon glyphicon-resize-horizontal" aria-hidden="true"></span>',
		    partialRefresh: true,
		    minWidth: 62
		});
	},100);
});


/*****
* event listener
*****/
// 打开
$(".m_button_l input[value='添加']").on("click",function(){
	$(".bg_cover, .add_NonStandard").slideDown(350);
	$(".add_NonStandard_body_in [id^='add_info_']").each(function(){
		// if($(this).prop("tagName")=="SELECT"||$(this).prop("tagName")=="select"){
		// 	$(this).val("0");
		// }else{
		// 	$(this).val("");
		// }
		$(this).val("");
		if($(this).is("#add_info_Picture")){
			$(this).attr("title","");
		}
	});
});

$(document).on("click",".update_td",function(){
	$(".bg_cover, .update_NonStandard").slideDown(350);
	// jQuery.data($(this), "customerId");
	updateSubmitObj.ID = Number($(this).data("iid")).toString();
	// updateSubmitObj.CommodityID = Number($(this).data("commodityid")).toString();
	var that = $(this);
	$(".update_NonStandard_body_in [id^='update_info_']").each(function(){
		var subClassName = $(this).attr("id").replace("update_info_","hastd_");
		var oldVal;
		if($(this).is("#update_info_Picture")){
			oldVal = that.siblings("."+subClassName).attr("title");
		}else{
			oldVal = that.siblings("."+subClassName).text();
		}
		var newVal = globalDataHandle(oldVal,"");
		$(this).val(newVal);
		if($(this).is("#update_info_Picture")){
			$(this).attr("title",newVal);
		}
	});
});

// 关闭
$("#NonStandard_addclose, .add_NonStandard_tit_r").on("click",function(){
	$(".bg_cover, .add_NonStandard").slideUp(300);
	if($('div.add_fileList_info').is(':visible')){
		$('div.add_fileList_info').slideUp(150, function(){
			$("div.add_fileList_info>div>div.progress-bar").attr("aria-valuenow","0").css("width","0%").text("0%");
		});
	}
	$("#add_fileList_ul").empty();
	
	for(var k in addSubmitObj){
		addSubmitObj[k] = null;
	}
	LabState.uploadFileNo = 0;
	for(var kk in LabState.uploadFileList){
		delete LabState.uploadFileList[kk];
	}
	$("#add_file_Upload").val("");
});

$("#NonStandard_updateclose, .update_NonStandard_tit_r").on("click",function(){
	$(".bg_cover, .update_NonStandard").slideUp(300);
	if($('div.update_fileList_info').is(':visible')){
		$('div.update_fileList_info').slideUp(150, function(){
			$("div.update_fileList_info>div>div.progress-bar").attr("aria-valuenow","0").css("width","0%").text("0%");
		});
	}
	$("#update_fileList_ul").empty();

	for(var k in updateSubmitObj){
		updateSubmitObj[k] = null;
	}
	
	LabState.uploadFileNo = 0;
	for(var kk in LabState.uploadFileList){
		delete LabState.uploadFileList[kk];
	}
	$("#update_file_Upload").val("");
});

// 翻页功能
$("#jumpNumber").on("input propertychange",function(){
    var newVal = $(this).val().replace(/[^\d]/g,'');
    $(this).val(newVal);
});

	// 翻页
$("#fistPage").click(function(){
    var currentPage =1;
    tableRenderAjax(currentPage);
});

$("#lastPage").click(function(){
    var currentPage =Number($("#allPage").text());
    tableRenderAjax(currentPage);
});

$("#upPage").click(function(){
    var currentPage = Number($("#currentPage").text());
    if(currentPage == 1){
        return;
    }else{
        currentPage--;
        tableRenderAjax(currentPage);
    }
});

$("#nextPage").click(function(){
    var currentPage = Number($("#currentPage").text());
    var pageCounts = Number($("#allPage").text());
    if(currentPage == pageCounts){
        return;
    }else{
        currentPage++;
        tableRenderAjax(currentPage);
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
        tableRenderAjax(currentPage);
    }
});

// 添加提交
$("#NonStandard_addsubmit").on("click",function(){
	for(var kk in addSubmitObj){
		if(kk=="Type"){
			addSubmitObj[kk] = "add";
			continue;
		}
		if(kk=="CommodityID"){
			continue;
		}
		addSubmitObj[kk] = $(".add_NonStandard").find(".info_"+kk).val();
	}
	// 表单验证
	for(var kkk in addSubmitObj){
		addSubmitObj[kkk] = globalDataHandle(addSubmitObj[kkk],"").trim();
		if(kkk=="CommodityID" && addSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择系统名称！");
			return false;
		}
	}
	console.log("add我执行了吗");
	console.log(addSubmitObj);
	$.ajax({
		type: "POST",
		url: "Lab",
		dataType: 'text',
		data: addSubmitObj,
		success: function(data){
			// console.log(typeof data);
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
				// hasSearch = 0;
    // 			sosuoInit();
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
		if(kk=="ID"){
			continue;
		}
		if(kk=="CommodityID"){
			continue;
		}
		updateSubmitObj[kk] = $(".update_NonStandard").find(".info_"+kk).val();
	}
	// 表单验证
	for(var kkk in updateSubmitObj){
		updateSubmitObj[kkk] = globalDataHandle(updateSubmitObj[kkk],"").trim();
		if(kkk=="CommodityID" && updateSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择系统名称！");
			return false;
		}
	}
	console.log("update我执行了吗");
	console.log(updateSubmitObj);
	$.ajax({
		type: "POST",
		url: "Lab",
		dataType: 'text',
		data: updateSubmitObj,
		success: function(data){
			// console.log(typeof data);
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
				// hasSearch = 0;
    // 			sosuoInit();
		    }
		}
	});
});

// 搜索型号
function addSearchModelHandle(){
	var param = $(".add_NonStandard #add_info_Model").val().trim();
	var item = "#add_Commodity";
	if(param == ""){
		$(".add_NonStandard #add_info_Model").val("");
	}else if(param == "--"){
		$.MsgBox_Unload.Alert("提示", "搜索值格式错误！");
	}else{
		// globalGetCommodityInfo("型号", param, item);
		globalGetCommoditySectionInfo(param, item);
	}
}
var addThrottle = ecDo.delayFn(addSearchModelHandle, 700, 1000);
$(".add_NonStandard #add_info_Model").on("input propertychange",addThrottle);

function updateSearchModelHandle(){
	var param = $(".update_NonStandard #update_info_Model").val().trim();
	var item = "#update_Commodity";
	if(param == ""){
		$(".update_NonStandard #update_info_Model").val("");
	}else if(param == "--"){
		$.MsgBox_Unload.Alert("提示", "搜索值格式错误！");
	}else{
		// globalGetCommodityInfo("型号", param, item);
		globalGetCommoditySectionInfo(param, item);
	}
}
var updateThrottle = ecDo.delayFn(updateSearchModelHandle, 700, 1000);
$(".update_NonStandard #update_info_Model").on("input propertychange",updateThrottle);

// 搜索后点击option
$(document).on("click","#add_Commodity option, #update_Commodity option",function(e){
	e.stopPropagation();
	if($(this).parents("#add_Commodity").length){
		$(".add_NonStandard #add_info_Model").val($("#add_Commodity").val());
		$(".add_NonStandard #add_info_Description").val($(this).attr("commodityname"));
		$("#add_Commodity").fadeOut(200);
		// addSubmitObj.CommodityID = $(this).attr("text");
	}else if($(this).parents("#update_Commodity").length){
		$(".update_NonStandard #update_info_Model").val($("#update_Commodity").val());
		$(".update_NonStandard #update_info_Description").val($(this).attr("commodityname"));
		$("#update_Commodity").fadeOut(200);
		// updateSubmitObj.CommodityID = $(this).attr("text");
	}
});

// 点击文档隐藏select
$(document).on("click",function(){
	$("#add_Commodity, #update_Commodity, #part_search_select").fadeOut(200);
});

// 表格悬浮
$(document).on("mouseenter",".m_table tbody td",function(){
	$(this).parent().css({"border":"2px solid red"});
});
$(document).on("mouseleave",".m_table tbody td",function(){
	$(this).parent().css({"border":"1px solid transparent"});
});

// 详细配置
$(document).on("click",".detailed_part span",function(){
	LabID = $(this).parent().siblings(".update_td").data("iid");
	curModel = $(this).parent().siblings(".hastd_Model").text();
	// setTimeout(function(){
	// 	$("#input_down_file")[0].focus();
	// },50);
	$.ajax({
		type: "GET",
		url: "LabConfig",
		data: {
			LabID: LabID
		},
		dataType: "json"
	}).then(function(data){
		var str = '';
		data.map(function(currentValue,index,arr){
			if(index>0){
				str+='<tr>'+
	                    '<td class="item-No" data-id="'+currentValue.PartID+'"><span class="glyphicon glyphicon-remove-circle delete_item"></span>&nbsp;<span class="NoService"></span></td>'+
	                    '<td class="service_Model">'+currentValue.Model+'</td>'+
	                    '<td class="service_Description">'+currentValue.Description+'</td>'+
	                    '<td class="service_Qty">'+currentValue.Qty+'</td>'+
	                '</tr>';
			}
		});
		$(".serviceReport_table tbody").empty().append(str);
		calcServiceNo();
		canFillItem();
		$(".serviceReport_div").slideDown(200);
		$(".cover_bg2").slideDown(200);
	},function(){
		$.MsgBox_Unload.Alert("提示", "网络繁忙！");
	}).always(function(){

	});
});

$(".serviceReport_top_r").click(function(){
	LabID = "";
	curModel = "";
	for(var k in downloadObj){
		downloadObj[k] = null;
	}
	$(".serviceReport_div, .cover_bg2").slideUp(300);
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

// 详细配置型号搜索打开
$(document).on("click",".service_Model",function(){
	searchPartModelIndex = $(this).parent().index();
	$("#part_search_input").val("");
	if($(this).siblings(".item-No").data("id")){
		$(".part_search_top_l").text("修改详细配置--"+$(this).text());
	}else{
		$(".part_search_top_l").text("添加详细配置item");
	}
	$(".cover_bg3, .part_search_div").slideDown(300);
});

// 详细配置型号搜索关闭
$(".part_search_top_r").click(function(){
	$(".cover_bg3, .part_search_div").slideUp(300);
	searchPartModelIndex = -1;
});

// 详细配置型号搜索
function partSearchModelHandle(){
	var param = $("#part_search_input").val().trim();
	var item = "#part_search_select";
	if(param == ""){
		$("#part_search_input").val("");
	}else if(param == "--"){
		$.MsgBox_Unload.Alert("提示", "搜索值格式错误！");
	}else{
		// globalGetCommodityInfo("型号", param, item);
		globalGetCommoditySectionInfo(param, item);
	}
}
var partThrottle = ecDo.delayFn(partSearchModelHandle, 700, 1000);
$("#part_search_input").on("input propertychange",partThrottle);

// 详细配置型号搜索后的option点击
$(document).on("click","#part_search_select option",function(e){
	e.stopPropagation();
	if(searchPartModelIndex > -1){
		var curTr = $(".serviceReport_table tbody tr").eq(searchPartModelIndex);
		curTr.find(".item-No").data("id",$(this).attr("text"));
		curTr.find(".service_Model").text($("#part_search_select").val());
		curTr.find(".service_Description").text($(this).attr("commodityname"));
		curTr.find(".service_Description").attr("title",$(this).attr("commodityname"));
	}
	$("#part_search_select").fadeOut(200);
	$(".part_search_top_r").trigger("click");
});

// 保存详细配置
$(".serviceReport_top_save").click(function(){
	var ConfigJson = [];
	var len = $(".serviceReport_table tbody tr").length;
	if(len == 0){
		$.MsgBox_Unload.Alert("提示", "请先添加一条再保存！");
		return false;
	}
	var isPartID = 1;
	var isQty = 1;
	$(".serviceReport_table tbody tr").each(function(){
		var item = {};
		var iPartID = $(this).children(".item-No").data("id");
		if(!iPartID){
			isPartID = 0;
		}
		var iQty = $(this).children(".service_Qty").text();
		
		item.PartID = iPartID;
		item.Qty = iQty;
		ConfigJson.push(item);
	});
	if(isPartID==0){
		$.MsgBox_Unload.Alert("提示", "有型号（Part Number）未选择！");
		return false;
	}
	
	var ConfigJsonStr = $.fn.stringifyArr(ConfigJson);
	$.ajax({
		type: "POST",
		url: "LabConfig",
		data: {
			ConfigJson: ConfigJsonStr,
			LabID: LabID
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

// 数量失去焦点事件
$(document).on("blur",".service_Qty",function(){
	var curVal = $(this).text().replace(/[^\d]+/g,"");
	if(curVal == ""){
		$(this).text("0");
	}else{
		$(this).text(curVal);
	}
});

// 详细配置下载
$(".serviceReport_top_down").click(function(){
	// $.MsgBox_Unload.Alert("提示", "下载前请记得保存！");
	var methods = "GET";
	var iurl = "UploadLabPicture?Model="+curModel+"&LabID="+LabID;
	var filename = null;
	globalUrlDownloadFile(methods, iurl, filename, null);
});


// 显示图片
$(document).on("click",".hastd_Picture img",function(){
	var picFileName = $(this).parent().attr("title");
	if(picFileName=="" || picFileName=="--"){
		$.MsgBox_Unload.Alert("提示","没有图片！");
		return false;
	}
	var imgFilePrefix = picFileName.replace(imgSuffix,"");
	$(".showPic_top_l").text(imgFilePrefix);
	var url = "GetLabPicture?FileName="+picFileName;
    var xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);
    xhr.responseType = "blob";
    //xhr.setRequestHeader("client_type", "DESKTOP_WEB");
    //xhr.setRequestHeader("desktop_web_access_key", _desktop_web_access_key);
    xhr.onload = function () {
        if (this.status == 200) {
            var blob = this.response;
            imageResizeTool.filetoDataURL(blob,function(res){
                var img = document.createElement("img");
                img.onload = function (e) {
                    window.URL.revokeObjectURL(img.src);
                    if(img.width>760){
                    	console.log("1");
                    	img.width = 760;
                    }
                    if(img.height>1000){
                    	console.log("2");
                    	img.height = 1000;
                    }
                };
                img.src = res;
                $(".showPic_body_in").empty().append(img);
            });
            $(".showPic_bgcover, .showPic").slideDown(200);
            // var img = document.createElement("img");
            // img.onload = function (e) {
            //     window.URL.revokeObjectURL(img.src);
            // };
            // img.src = window.URL.createObjectURL(blob);
            // img.width = 740;
            // $(".showPic_bgcover, .showPic").slideDown(300);
            // $(".showPic_body_in").html(img);
        }
    };
    xhr.send();
});

// 关闭显示图片
$(".showPic_top_r span").click(function(){
	$(".showPic_bgcover, .showPic").slideUp(200);
});

// 下载显示图片
$(".showPic_top_m span").click(function(){
	var that = $(".showPic_body_in img");
	if(!that.length){
		$.MsgBox_Unload.Alert("下载提示","文件已被删除");
		return false;
	}
	var imgFile = $(".showPic_top_l").text()+".png";
	globalAutoDownloadImg(that,imgFile);
});

// 上传图片打开
$(".add_upload_Picture, .update_upload_Picture").on("click",function(){
	if($(this).parents(".add_NonStandard").length){
		curFileupload = "#add_info_Picture";
	}else if($(this).parents(".update_NonStandard").length){
		curFileupload = "#update_info_Picture";
	}
	$(".upload_bgcover, .dropFileBox").slideDown(300);
});

// 上传图片
$("input.dropUp2").on("click",function(){



	// console.log($("#serFinRepUpload")[0].files[0]); // 属性name为文件名，size为大小
	// 
	// 下面是获取实际路径（评论说有错）
	// $("input[type='file']").on('change', function () {
	//     var oFReader = new FileReader();
	//     var file = document.getElementById('input-file').files[0];
	//     oFReader.readAsDataURL(file);
	//     oFReader.onloadend = function(oFRevent){
	//         var src = oFRevent.target.result;
	//         $('.content').attr('src',src);
	//         alert(src);
	//     }
	// });
	// 
	//修改配置获取真实路径。附带不用修改浏览器安全配置的javascript代码，兼容ie， firefox全系列。参数obj为input file对象
	// function getPath(obj) {
	//     if (obj) {
	//         if (window.navigator.userAgent.indexOf("MSIE") >= 1) {
	//             obj.select();
	//             return document.selection.createRange().text;
	//         } else if (window.navigator.userAgent.indexOf("Firefox") >= 1) {
	//             if (obj.files) {
	//                 return obj.files.item(0).getAsDataURL();
	//             }
	//             return obj.value;
	//         }
	//         return obj.value;
	//     }
	// }
	// 
	// console.log($("#serFinRepUpload")[0].files[0].value);  // undefined
	// console.log($("#serFinRepUpload")[0].value);  // C:\fakepath\StandardProduct.jsp
	var judgeFile = $("input.serFinRepUploadName").val();
	var judgeFile2 = $("#serFinRepUpload").val();
	var filename1=judgeFile.replace(/.*(\/|\\)/, ""); 
	var filename2=judgeFile2.replace(/.*(\/|\\)/, ""); 
	var fileExt1=(/[.]/.exec(filename1)) ? /[^.]+$/.exec(filename1.toLowerCase()) : '';
	var fileExt2=(/[.]/.exec(filename2)) ? /[^.]+$/.exec(filename2.toLowerCase()) : '';
	if(fileExt1==''||fileExt2==''){
		$.MsgBox_Unload.Alert("上传提示","文件没有后缀名，请重新上传");
		return false;
	}else if(!imgSuffix.test(judgeFile) || !imgSuffix.test(judgeFile2)){
		$.MsgBox_Unload.Alert("上传提示","文件不是图片类型，请重新上传");
		return false;
	}
	// else if(fileExt1!=''&&fileExt2!=''){
	// 	if(fileExt1["0"]!="pdf"||fileExt2["0"]!="pdf"){
	// 		$.MsgBox_Unload.Alert("上传提示","文件后缀名错误，请上传pdf");
	// 		return false;
	// 	}
	// }
	// 验证图片长宽和大小
	// var image = new Image();
 //    image.src = $("#serFinRepUpload")[0].value;
 //    var filesize = image.filesize;
 //    if(filesize>1048576){
 //    	$.MsgBox_Unload.Alert("上传提示","请上传大小小于1M的图片！");
	// 	return false;
 //    }
 //    var width = image.width;
 //    if(width>1000){
 //    	$.MsgBox_Unload.Alert("上传提示","请上传宽度小于1000像素的图片！");
	// 	return false;
 //    }
 //    var height = image.height;
 //    if(height>1000){
 //    	$.MsgBox_Unload.Alert("上传提示","请上传高度小于1000像素的图片！");
	// 	return false;
 //    }
 	globalGetImgWHSize($("#serFinRepUpload")[0].files[0], function(width,height,filesize){
	    if(filesize>1048576){
	    	$.MsgBox_Unload.Alert("上传提示","请上传大小小于1M的图片！");
	    }else if(width>1000){
	    	$.MsgBox_Unload.Alert("上传提示","请上传宽度小于1000像素的图片！");
	    }else if(height>1000){
	    	$.MsgBox_Unload.Alert("上传提示","请上传高度小于1000像素的图片！");
	    }else{
			uploadFiles();
	    }
 	});
	// if(judgeFile && judgeFile2){
	// 	uploadFiles();
	// }else{
	// 	$.MsgBox_Unload.Alert("上传提示","请检查是否选择或更换了文件");
	// }
});

// 点击浏览
$("#serFinRepUpload").on("change",function(){
	$("span.isUpload").text("");
	$(".progressIn").css("width","30px").text("0%");
	console.log("文件上传改变值"+$(this).val());
	var newFileName1 = $(this).val().indexOf("\\fakepath\\")>-1?$(this).val().split("\\fakepath\\")[1]:$(this).val().split("\\").pop();
	console.log("赋给input的值"+newFileName1);
	$("input.serFinRepUploadName").val(newFileName1).attr("title",newFileName1);
});

// 关闭上传
$(".dropFileTit span").on("click",function(){
	$(".upload_bgcover, .dropFileBox").slideUp(300);
	$("span.isUpload").text("");
	$(".progressIn").css("width","30px").text("0%");
	$("input.serFinRepUploadName").val("").attr("title","");
	$("#serFinRepUpload").val("");
});

// input file域触发点击
$(".trigger_click").click(function(e){
	e.preventDefault();
	$(this).next().trigger("click");
});