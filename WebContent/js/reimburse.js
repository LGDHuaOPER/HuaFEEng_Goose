/*变量与函数定义*/
// 添加修改提交data对象
var addSubmitObj = new Object();
addSubmitObj.Type = null;
// addSubmitObj.ID = undefined;
addSubmitObj.Name = null;
addSubmitObj.Department = null;
// addSubmitObj.Pass = undefined;
addSubmitObj.TotalAmount = null;
// addSubmitObj.BillScreenshot = null;
// addSubmitObj.ElectronicInvoice = null;
// addSubmitObj.TravelPaper = null;
// addSubmitObj.Others = null;
addSubmitObj.DetailJson = null;
addSubmitObj.TravelJson = null;
var updateSubmitObj = new Object();
updateSubmitObj.Type = null;
updateSubmitObj.ID = null;
updateSubmitObj.Name = null;
updateSubmitObj.Department = null;
updateSubmitObj.Pass = null;
updateSubmitObj.TotalAmount = null;
updateSubmitObj.BillScreenshot = null;
updateSubmitObj.ElectronicInvoice = null;
updateSubmitObj.TravelPaper = null;
updateSubmitObj.Others = null;
updateSubmitObj.DetailJson = null;
updateSubmitObj.TravelJson = null;

// 状态定义
var reimburseState = new Object();
reimburseState.allUploadObj = {
	"a": {
		uploadFileNo: 0,
		uploadFileList: {}
	},
	"b": {
		uploadFileNo: 0,
		uploadFileList: {}
	},
	"c": {
		uploadFileNo: 0,
		uploadFileList: {}
	},
	HasSummitYM: null,
	StaffName: null,
	ID: null
};
reimburseState.uploadFileNo = 0;
reimburseState.uploadFileList = {};
reimburseState.hasSearch = false;
reimburseState.curFileupload = '';
reimburseState.addResID = '';
reimburseState.OperaClassify = '';
reimburseState.Application = {
	ID: null,
	State: '',
	Name: '',
	FilingDate: '',
	Reason: ''
};
reimburseState.exportAll = false;
reimburseState.hasReminedShield = true;
reimburseState.addHasSummitYM = globalGetToday(false).substring(0, globalGetToday(false).length - 3).replace(/-/g,"");
reimburseState.updateHasSummitYM = null;
// 天数的正则
// var reimburse_daysReg = /^((\d)*|0\.5)$/;
var reimburse_daysReg = /^((([1-9][0-9]*)+(\.[0,5]{1})?)|0\.5)$/;
// 报销类型数组
var reimburseCategory = ["飞机票","高铁/动车票","出租车票","住宿费","汽油费","停车费","快递费","其他","请餐费","招待费","礼品费"];
// 报销类型增加一行字符串
var reimburseCategoryAddStr = '<tr><td class="detail_td_Amount"></td><td class="detail_td_MainContent"></td><td class="detail_td_CustomerName"></td><td class="detail_td_City"><input type="text" class="detail_td_City_1">&nbsp--&nbsp;<input type="text" class="detail_td_City_2"></td><td class="detail_td_Time"><input type="date" class="detail_td_Time_1">&nbsp--&nbsp;<input type="date" class="detail_td_Time_2"></td><td class="detail_td_Operation"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></td></tr>';
var reimburseCategoryStr = '';
reimburseCategory.map(function(currentValue, index, arr){
	reimburseCategoryStr+='<tr value="'+currentValue+'"><td class="detail_td_Type">'+currentValue+'</td><td class="detail_td_Amount"></td><td class="detail_td_MainContent"></td><td class="detail_td_CustomerName"></td><td class="detail_td_City"><input type="text" class="detail_td_City_1">&nbsp--&nbsp;<input type="text" class="detail_td_City_2"></td><td class="detail_td_Time"><input type="date" class="detail_td_Time_1">&nbsp--&nbsp;<input type="date" class="detail_td_Time_2"></td><td class="detail_td_Operation"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></td></tr>';
});

// 地点、事由添加字符串
var address_businessStr = '<tr><td>出差地点</td><td class="detail_td_TravelPlace"></td><td>事由</td><td class="detail_td_MainContent"></td><td>往返时间</td><td class="detail_td_TravelTime"><input type="date" class="detail_td_TravelTime_1">&nbsp--&nbsp;<input type="date" class="detail_td_TravelTime_2"></td><td>天数&nbsp;<span class="glyphicon glyphicon-refresh refresh_days" aria-hidden="true"></span></td><td class="detail_td_Days">0</td><td><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></td></tr>';

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

// 拆分文件路径字符串
function splitFilePath(str){
	var resStr = '';
	if(str=="" || str=="--"){
		resStr = '';
	}else{
		var FilePathArr = str.split("::");
		FilePathArr = $.grep(FilePathArr,function(currentValue,index){
			return currentValue != "";
		});
		var len = FilePathArr.length - 1;
		FilePathArr.map(function(currentValue,index,arr){
			if(index<len){
				resStr+='<span class="downFile_span" title="'+currentValue+'">'+currentValue+'</span><br/>';
			}else{
				resStr+='<span class="downFile_span" title="'+currentValue+'">'+currentValue+'</span>';
			}
		});
	}
	return resStr;
}

// 页面进入时获取数据
function getPageData(Year, Month, CurrentPage){
	$.ajax({
		type: "GET",
		url: "Reimburse",
		data: {
			LoadType: "data",
			Year: Year,
			Month: Month,
			CurrentPage: CurrentPage
		},
		dataType: "json"
	}).then(function(res){
		var data = res.datas;
		var pageCounts = res.pageCount;
		renderPageData(data, CurrentPage, pageCounts);
		if(!Year && !Month){
			reimburseState.hasSearch = false;
		}else{
			reimburseState.hasSearch = true;
		}
	},function(){
		$.MsgBox_Unload.Alert("提示","服务器繁忙！");
	});
}

// 渲染页面数据
function renderPageData(data, CurrentPage, pageCounts){
	var str = '';
	if(data.length == 1){
		str+='<tr><td colspan="12">无数据......</td></tr>';
	}else if(data.length > 1){
		data.map(function(currentValue, index, arr){
			if(index > 0){
				var BillScreenshotStr = splitFilePath(currentValue.BillScreenshot);
				var ElectronicInvoiceStr = splitFilePath(currentValue.ElectronicInvoice);
				var TravelPaperStr = splitFilePath(currentValue.TravelPaper);
				var OthersStr = splitFilePath(currentValue.Others);
				str+='<tr>'+
					'<td><input type="checkbox"></td>'+
					'<td class="update_td" data-iid="'+currentValue.ID+'">'+parseInt((CurrentPage-1)*10 + index)+'</td>'+
					'<td class="hastd_Department" title="'+currentValue.Department+'">'+currentValue.Department+'</td>'+
					'<td class="hastd_Name" title="'+currentValue.Name+'">'+currentValue.Name+'</td>'+
					'<td class="hastd_TotalAmount" title="'+currentValue.TotalAmount+'">'+currentValue.TotalAmount+'</td>'+
					'<td class="reimburse_detail_td" title="点此查看详情"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></td>'+
					'<td class="hastd_FilingDate" title="'+currentValue.FilingDate+'">'+currentValue.FilingDate+'</td>'+
					'<td class="hastd_BillScreenshot" title="'+currentValue.BillScreenshot+'">'+BillScreenshotStr+'</td>'+
					'<td class="hastd_ElectronicInvoice" title="'+currentValue.ElectronicInvoice+'" data-ivalue="'+currentValue.ElectronicInvoice+'">'+ElectronicInvoiceStr+'</td>'+
					'<td class="hastd_TravelPaper" title="'+currentValue.TravelPaper+'" data-ivalue="'+currentValue.TravelPaper+'">'+TravelPaperStr+'</td>'+
					'<td class="hastd_Others" title="'+currentValue.Others+'" data-ivalue="'+currentValue.Others+'">'+OthersStr+'</td>'+
					'<td class="hastd_Pass" data-container="body" data-toggle="popover" data-placement="top"><input type="button" value="'+currentValue.Pass+'"></td>'+
				'</tr>';
			}
		});
	}
	$(".m_table table tbody").empty().append(str);
	$(".m_page #currentPage").text(CurrentPage);
	$(".m_page #allPage").text(pageCounts);
	pageStyle(CurrentPage, pageCounts);
	$('[data-toggle="popover"]').popover({
		title: "请审核",
		content: '<div class="form-group"><label for="exampleInputEmail1">原因</label><input type="text" class="form-control" id="exampleInputEmail1" placeholder="原因"></div><div style="text-align:right"><input type="button" class="btn btn-success" id="ReimburseApplication_y" value="通过"><input type="button" class="btn btn-warning" id="ReimburseApplication_n" value="未通过"></div>',
		html: true
	});
	$(".m_table thead .t0>input, .m_table tbody td:nth-child(1)>input").prop("checked",reimburseState.exportAll);
}

// 翻页获取数据
function getDataByGoPage(currentPage){
	var Year;
	var Month;
	if(!reimburseState.hasSearch){
		Year = undefined;
		Month = undefined;
	}else{
		Year = $(".m_button_r_in_l div.btn-group>button:nth-child(1)").attr("title");
		Month = $(".m_button_r_in_m div.btn-group>button:nth-child(1)").attr("title");
	}
	getPageData(Year, Month, currentPage);
}

// 计算天数
function calcDays(item, insertItem){
	var sum = 0;
	item.each(function(){
		sum+=Number($(this).text());
	});
	insertItem.text(sum);
}

// td可输入属性
function tdContenteditable(dom){
	dom.prop("contenteditable","true");
}

// addupdate模块表格字符串初始化
function addupdateInitTableStr(classify){
	$("."+classify+"_NonStandard_details_table>tbody").empty().append(reimburseCategoryStr);
	$("."+classify+"_NonStandard_details_address_table>tbody").empty().append(address_businessStr);
	var dom = $("."+classify+"_NonStandard_details_table>tbody tr").children(".detail_td_Amount, .detail_td_MainContent, .detail_td_CustomerName");
	tdContenteditable(dom);
	var dom2 = $("."+classify+"_NonStandard_details_address_table>tbody tr").children(".detail_td_TravelPlace, .detail_td_MainContent, .detail_td_Days");
	tdContenteditable(dom2);
}

// check一行是否都填了东西
function checkLineAllNull(dom){
	var flag = true;
	dom.children(":not(.detail_td_Type):not(.detail_td_Operation)").each(function(){
		if($(this).is(".detail_td_City")){
			var that1 = $(this);
			that1.children("input").each(function(){
				var iVal1 = $(this).val().trim();
				if(iVal1 != "" && iVal1 !="--"){
					flag = false;
				}
			});
			return true;
		}
		if($(this).is(".detail_td_Time")){
			var that2 = $(this);
			that2.children("input").each(function(){
				var iVal2 = $(this).val();
				if(iVal2 != "" && iVal2 !="0000-00-00"){
					flag = false;
				}
			});
			return true;
		}
		var iText = $(this).text().trim();
		if(iText != "" && iText != "--"){
			flag = false;
		}
	});
	return flag;
}
function checkLineAllNull2(dom){
	var flag = true;
	dom.children("td[class^='detail_td_']").each(function(){
		if($(this).is(".detail_td_TravelTime")){
			var that2 = $(this);
			that2.children("input").each(function(){
				var iVal2 = $(this).val();
				if(iVal2 != "" && iVal2 !="0000-00-00"){
					flag = false;
				}
			});
			return true;
		}
		if($(this).is(".detail_td_Days")){
			return true;
		}
		var iText = $(this).text().trim();
		if(iText != "" && iText != "--"){
			flag = false;
		}
	});
	return flag;
}

// 搜索初始化
function sosuoInit(){
    reimburseState.hasSearch = false;
    $(".m_button_r_in_l div.btn-group>button:nth-child(1)").attr("title", "选择年");
	$(".m_button_r_in_l div.btn-group>button:nth-child(1)").text("选择年");
	$(".m_button_r_in_m div.btn-group>button:nth-child(1)").attr("title", "All");
	$(".m_button_r_in_m div.btn-group>button:nth-child(1)").text("All");
}

//上传文件
function uploadFiles(Folder, fileObj, curTr){                                               
    var formData = new FormData();
    formData.enctype="multipart/form-data";
    formData.append("Folder",Folder);
    // formData.append("ID",ID);
    $.each(fileObj, function(iname, ivalue){
    	formData.append("file",ivalue);
    });
    //formData.append("file",$("#serFinRepUpload")[0].files[0]);//append()里面的第一个参数file对应permission/upload里面的参数file         
    // formData.append("Operate","upload");
    $.ajax({
        type: "POST",
        async: true,  //这里要设置异步上传，才能成功调用myXhr.upload.addEventListener('progress',function(e){}),progress的回掉函数
        // accept: 'text/html;charset=UTF-8',
        accept: 'application/json; charset=utf-8',
        data: formData,
        // contentType:"multipart/form-data",
        url: "BatchUpload",
        processData: false, // 告诉jQuery不要去处理发送的数据
        contentType: false, // 告诉jQuery不要去设置Content-Type请求头
        cache: false,
        dataType: "json",                   
        success: function(data){
        	if(data.length > 0){
        		if(data[0].Message == "上传成功"){
	        		$.Response_Load.After("上传成功", 1600);
        		}else if(data[0].Message.indexOf("失败") > -1){
        			$.Response_Load.After("上传失败", 1600);
        		}else{
        			$.Response_Load.After("文件已存在", 1600);
        		}
        	}else if(data.length == 0){
        		$.Response_Load.After("文件读取至服务器失败！", 1600);
        	}
        },
        error: function(){
        	$.Response_Load.After("网络繁忙！上传失败！", 1600);
        },
		beforeSend: function(XMLHttpRequest){
            $.Response_Load.Before("上传提示","正在提交...",200);
        },
		complete: function(XMLHttpRequest, textStatus){
		}
    });                           
}


//上传文件
function uploadFiles2(iParentDOM, Folder, iThat){                                               
    var formData = new FormData();
    formData.enctype="multipart/form-data";
    formData.append("Folder",Folder);
    // formData.append("ID",ID);
    var fileList = reimburseState.allUploadObj[iParentDOM.data("iclassify")].uploadFileList;
    $.each(fileList, function(iname, ivalue){
    	formData.append("file",ivalue);
    });
    //formData.append("file",$("#serFinRepUpload")[0].files[0]);//append()里面的第一个参数file对应permission/upload里面的参数file         
    // formData.append("Operate","upload");
    $.ajax({
        type: "POST",
        async: true,  //这里要设置异步上传，才能成功调用myXhr.upload.addEventListener('progress',function(e){}),progress的回掉函数
        // accept: 'text/html;charset=UTF-8',
        accept: 'application/json; charset=utf-8',
        data: formData,
        // contentType:"multipart/form-data",
        url: "BatchUpload",
        processData: false, // 告诉jQuery不要去处理发送的数据
        contentType: false, // 告诉jQuery不要去设置Content-Type请求头
        cache: false,
        dataType: "json",
        xhr: function(){                        
            myXhr = $.ajaxSettings.xhr();
            if(myXhr.upload){ // check if upload property exists
                myXhr.upload.addEventListener('progress',function(e){                           
                    var loaded = e.loaded;                  //已经上传大小情况 
                    var total = e.total;                      //附件总大小 
                    var percent = (Math.floor(1000*loaded/total)/10)+"%";     //已经上传的百分比  
                    console.log("已经上传了："+percent);
                    var newWidthFloat =  globalToPoint(percent);
                    var newWidth = (newWidthFloat*100).toFixed(0);
                    iParentDOM.find("div.add_fileList_info>div:nth-child(1)>div.progress-bar").attr("aria-valuenow",newWidth).css("width",percent).text(percent);
                }, false); // for handling the progress of the upload
            }
            return myXhr;
        },                    
        success: function(data){
        	if(data.length > 0){
        		var LiStr = '';
        		var successNO = 0;
        		data.map(function(currentValue, index, arr){
        			if(currentValue.Message.indexOf("成功")>-1){
        				LiStr+='<li class="list-group-item list-group-item-success" title="'+currentValue.FileName+'"><span class="badge">成功</span><span class="badge uploaded">删除</span>'+currentValue.FileName+'</li>';
        				successNO++;
        			}else if(currentValue.Message.indexOf("失败")>-1){
        				LiStr+='<li class="list-group-item list-group-item-danger" title="'+currentValue.FileName+'"><span class="badge">失败</span>'+currentValue.FileName+'</li>';
        			}else{
        				LiStr+='<li class="list-group-item list-group-item-warning" title="'+currentValue.FileName+'"><span class="badge">已存在</span>'+currentValue.FileName+'</li>';
        			}
        		});
        		iParentDOM.find("ul>li>span.noUpload:contains('删除')").parent().remove();
        		iParentDOM.find("ul").append(LiStr);
        		// var successFloat = (successNO/(data.length)).toFixed(3);
        		// var successValueNow = successFloat*100;
        		// var successPercent = successValueNow+"%";
        		// $("div."+classify+"_fileList_info>div:nth-child(2)>div.progress-bar").attr("aria-valuenow",successValueNow).css("width",successPercent).text(successPercent);
        		// 重置文件待上传
        		reimburseState.allUploadObj[iParentDOM.data("iclassify")].uploadFileNo = 0;
        		for(var kk in reimburseState.allUploadObj[iParentDOM.data("iclassify")].uploadFileList){
        			delete reimburseState.allUploadObj[iParentDOM.data("iclassify")].uploadFileList[kk];
        		}
        		iParentDOM.find("input[type='file']").val("");
        	}else if(data.length == 0){
        		$.MsgBox_Unload.Alert("上传提示","文件读取至服务器失败！");
        	}
        },
        error: function(){
        	iParentDOM.find("div.add_fileList_info>div:nth-child(1)>div.progress-bar").attr("aria-valuenow","0").css("width","0%").text("0%");
            $.MsgBox_Unload.Alert("上传提示","网络繁忙！上传失败！");
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


/*页面onload*/
$(function(){
	addupdateInitTableStr("add");
	getPageData(undefined, undefined, 1);
	// 初始化年月
	var curY = new Date().getFullYear();
	var ii = 2018;
	do{
	    var str1 = '<li title="'+ii+'">'+ii+'年</li>';
	    $(".m_button_r_in_l ul").append(str1);
	    ii++;
	}while (ii < curY+1); 
	var iii = 1;
	do{
		if(iii<10){
			iii = "0"+iii;
		}
	    var str2 = '<li title="'+iii+'">'+iii+'月</li>';
	    $(".m_button_r_in_m ul").append(str2);
	    iii = Number(iii);
	    iii++;
	}while (iii < 13);

	// 初始化模态框
	$("#reimburseModal").modal({keyboard: true, show: false});
	// 初始化部门选项
	var str11 = '<option value="" disabled="disabled">请选择</option>';
	globalEouluAllDepart.map(function(currentValue, index, arr){
		str11+='<option value="'+currentValue+'">'+currentValue+'</option>';
	});
	$("select.info_Department").empty().append(str11);
});


/*
* event handler
*/
// 报销类型的add一行
$(document).on("click",".add_NonStandard_details_table span.glyphicon-plus, .update_NonStandard_details_table span.glyphicon-plus",function(e){
	e.stopPropagation();
	var parentTr = $(this).parent().parent();
	if(parentTr.attr("value")){
		var dom;
		parentTr.after(reimburseCategoryAddStr);
		var ivalue = parentTr.attr("value");
		parentTr.next().attr("value",ivalue);
		if($(this).parents(".add_NonStandard_details_table").length){
			var allValueTr = $(".add_NonStandard_details_table tbody tr[value='"+ivalue+"']");
			var valueLen = allValueTr.length;
			allValueTr.eq(0).children("td:nth-child(1)").attr("rowspan",valueLen);
			dom = $(".add_NonStandard_details_table>tbody tr").children(".detail_td_Amount, .detail_td_MainContent, .detail_td_CustomerName");
		}else if($(this).parents(".update_NonStandard_details_table").length){
			var allValueTr2 = $(".update_NonStandard_details_table tbody tr[value='"+ivalue+"']");
			var valueLen2 = allValueTr2.length;
			allValueTr2.eq(0).children("td:nth-child(1)").attr("rowspan",valueLen2);
			dom = $(".update_NonStandard_details_table>tbody tr").children(".detail_td_Amount, .detail_td_MainContent, .detail_td_CustomerName");
		}
		tdContenteditable(dom);
	}
});

// 地点、事由的add一行
$(document).on("click",".add_NonStandard_details_address_table span.glyphicon-plus, .update_NonStandard_details_address_table span.glyphicon-plus",function(e){
	e.stopPropagation();
	var parentTr = $(this).parent().parent();
	parentTr.after(address_businessStr);
	var classify;
	if($(this).parents(".add_NonStandard").length){
		classify = "add";
	}else if($(this).parents(".update_NonStandard").length){
		classify = "update";
	}
	var dom = $("."+classify+"_NonStandard_details_address_table>tbody tr").children(".detail_td_TravelPlace, .detail_td_MainContent, .detail_td_Days");
	tdContenteditable(dom);
});

// 添加模块打开关闭
$(".m_button_l input[value='添加']").click(function(){
	$(".bg_cover, .add_NonStandard").slideDown(300);
	$(".add_NonStandard_body [class^='info_']").each(function(){
		if($(this).is(".info_Name")){
			$(this).empty();
			return true;
		}
		if($(this).is(".info_BillScreenshot")||$(this).is(".info_ElectronicInvoice")||$(this).is(".info_TravelPaper")||$(this).is(".info_Others")){
			$(this).attr("title","");
		}
		$(this).val("");
	});
	addupdateInitTableStr("add");
	$(".add_NonStandard blockquote .glyphicon-cloud-upload, #NonStandard_addsubmit").prop("disabled","disabled").css("cursor","not-allowed");
	reimburseState.OperaClassify = 'add';
	$(".add_NonStandard_details_files").hide();
});
$(".add_NonStandard_tit_r, #NonStandard_addclose").click(function(){
	if(reimburseState.addResID){
		sosuoInit();
		getPageData(undefined, undefined, 1);
	}
	$(".bg_cover, .add_NonStandard").slideUp(300);
	for(var k in addSubmitObj){
		addSubmitObj[k] = null;
	}
	reimburseState.addResID = '';
	reimburseState.OperaClassify = '';
	$(".add_NonStandard .info_Name, .add_NonStandard .info_Department").prop("disabled",false);
});

// 修改模块打开关闭
$(document).on("click",".update_td",function(){
	var reimburse_name = $("span#reimburse_name").text();
	var iName = $(this).siblings(".hastd_Name").text();
	if(reimburse_name!=iName){
		return false;
	}

	var Department = $(this).siblings(".hastd_Department").text();
	var that = $(this);
	updateSubmitObj.ID = Number($(this).data("iid")).toString();
	updateSubmitObj.Pass = $(this).siblings(".hastd_Pass").children("input[type='button']").val();

	// 保存修改的提交日期
	reimburseState.updateHasSummitYM = $(this).siblings(".hastd_FilingDate").text().trim().split("-")[0] + $(this).siblings(".hastd_FilingDate").text().trim().split("-")[1];
	var RequestID = $(this).data("iid").toString();
	$.ajax({
		type: "GET",
		url: "ReimburseApplication",
		data: {
			RequestID: RequestID
		},
		dataType: "json"
	}).then(function(res){
		var detailData = res.detail;
		var travelData = res.travel;
		// 详情
		var detailStr = '';
		var copyReimburseCategory = [ ...reimburseCategory ];
		var repeatReimburseCategory = [];
		detailData.map(function(currentValue, index, arr){
			if(index > 0){
				var curIndex = $.inArray(currentValue.Type, copyReimburseCategory);
				var curIndex2 = $.inArray(currentValue.Type, repeatReimburseCategory);
				if(curIndex > -1){
					copyReimburseCategory.splice(curIndex,1);
				}
				
				detailStr+='<tr data-iid="'+currentValue.ID+'" value="'+currentValue.Type+'">';
				if(curIndex2 == -1){
					detailStr+='<td title="'+currentValue.Type+'" class="detail_td_Type">'+currentValue.Type+'</td>';
					repeatReimburseCategory.push(currentValue.Type);
				}
					detailStr+='<td title="'+currentValue.Amount+'" class="detail_td_Amount">'+currentValue.Amount+'</td>'+
					'<td title="'+currentValue.MainContent+'" class="detail_td_MainContent">'+currentValue.MainContent+'</td>'+
					'<td title="'+currentValue.CustomerName+'" class="detail_td_CustomerName">'+currentValue.CustomerName+'</td>'+
					'<td title="'+currentValue.City.replace(";;"," -- ")+'" class="detail_td_City"><input type="text" class="detail_td_City_1" value="'+currentValue.City.split(";;")[0]+'">&nbsp--&nbsp;<input type="text" class="detail_td_City_2" value="'+currentValue.City.split(";;")[1]+'"></td>'+
					'<td title="'+currentValue.Time.replace(";;"," -- ")+'" class="detail_td_Time"><input type="date" class="detail_td_Time_1" value="'+currentValue.Time.split(";;")[0]+'">&nbsp--&nbsp;<input type="date" class="detail_td_Time_2" value="'+currentValue.Time.split(";;")[1]+'"></td>'+
					'<td class="detail_td_Operation"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></td>'+
				'</tr>';
			}
		});
		copyReimburseCategory.map(function(currentValue, index, arr){
			detailStr+='<tr value="'+currentValue+'"><td class="detail_td_Type">'+currentValue+'</td><td class="detail_td_Amount"></td><td class="detail_td_MainContent"></td><td class="detail_td_CustomerName"></td><td class="detail_td_City"><input type="text" class="detail_td_City_1">&nbsp--&nbsp;<input type="text" class="detail_td_City_2"></td><td class="detail_td_Time"><input type="date" class="detail_td_Time_1">&nbsp--&nbsp;<input type="date" class="detail_td_Time_2"></td><td class="detail_td_Operation"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></td></tr>';
		});
		// 事由
		var travelStr = '';
		travelData.map(function(currentValue, index, arr){
			if(index > 0){
				travelStr+='<tr data-iid="'+currentValue.ID+'">'+
						'<td>出差地点</td>'+
						'<td title="'+currentValue.TravelPlace+'" class="detail_td_TravelPlace">'+currentValue.TravelPlace+'</td>'+
						'<td>事由</td>'+
						'<td title="'+currentValue.MainContent+'" class="detail_td_MainContent">'+currentValue.MainContent+'</td>'+
						'<td>往返时间</td>'+
						'<td title="'+currentValue.TravelTime.replace(";;"," -- ")+'" class="detail_td_TravelTime"><input type="date" class="detail_td_TravelTime_1" value="'+currentValue.TravelTime.split(";;")[0]+'">&nbsp--&nbsp;<input type="date" class="detail_td_TravelTime_2" value="'+currentValue.TravelTime.split(";;")[1]+'"></td>'+
						'<td>天数&nbsp;<span class="glyphicon glyphicon-refresh refresh_days" aria-hidden="true"></span></td>'+
						'<td title="'+currentValue.Days+'" class="detail_td_Days">'+currentValue.Days+'</td>'+
						'<td><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></td>'+
					'</tr>';
			}
		});

		if(detailData.length == 1 && travelData.length == 1){
			addupdateInitTableStr("update");
		}else if(detailData.length > 1 && travelData.length == 1){
			$(".update_NonStandard_details_table>tbody").empty().append(detailStr);
			$(".update_NonStandard_details_address_table>tbody").empty().append(address_businessStr);
		}else if(detailData.length == 1 && travelData.length > 1){
			$(".update_NonStandard_details_table>tbody").empty().append(reimburseCategoryStr);
			$(".update_NonStandard_details_address_table>tbody").empty().append(travelStr);
		}else{
			$(".update_NonStandard_details_table>tbody").empty().append(detailStr);
			$(".update_NonStandard_details_address_table>tbody").empty().append(travelStr);
		}
		var item2 = $(".update_NonStandard_details_address_table>tbody .detail_td_Days");
		var insertItem2 = $(".update_NonStandard_details_address_table>tfoot .insert_detail_td_calc_days");
		calcDays(item2, insertItem2);
		repeatReimburseCategory.map(function(currentValue, index, arr){
			var allValueTr2 = $(".update_NonStandard_details_table tbody tr[value='"+currentValue+"']");
			var valueLen2 = allValueTr2.length;
			allValueTr2.eq(0).children("td:nth-child(1)").attr("rowspan",valueLen2);
		});
		
		var dom01 = $(".update_NonStandard_details_table>tbody tr").children(".detail_td_Amount, .detail_td_MainContent, .detail_td_CustomerName");
		tdContenteditable(dom01);
		var dom02 = $(".update_NonStandard_details_address_table>tbody tr").children(".detail_td_TravelPlace, .detail_td_MainContent, .detail_td_Days");
		tdContenteditable(dom02);

		$(".bg_cover, .update_NonStandard").slideDown(300);
		globalGetStaffAllInfoByDepart(Department,function(data){
			var str = '';
			data.map(function(currentValue, index, arr){
				if(index > 0){
					str+='<option value="'+currentValue.StaffName+'" text="'+currentValue.ID+'">'+currentValue.StaffName+'</option>';
				}
			});
			$(".update_NonStandard .info_Name").empty().append(str);
			// 填充信息
			$(".update_NonStandard_body [class^='info_']").each(function(){
				var subClassName = $(this).attr("class").split(" ")[0].replace("info_","hastd_");
				var oldVal = that.siblings("."+subClassName).attr("title");
				var newVal = globalDataHandle(oldVal,"");
				$(this).val(newVal);
				if($(this).is(".info_BillScreenshot")||$(this).is(".info_ElectronicInvoice")||$(this).is(".info_TravelPaper")||$(this).is(".info_Others")){
					$(this).attr("title",newVal);
				}
			});
			reimburseState.OperaClassify = 'update';
		});
	},function(){
		$.MsgBox_Unload.Alert("查看详情提示","网络繁忙！");
	});

});

$(".update_NonStandard_tit_r, #NonStandard_updateclose").click(function(){
	$(".bg_cover, .update_NonStandard").slideUp(300);
	for(var k in updateSubmitObj){
		updateSubmitObj[k] = null;
	}
	reimburseState.OperaClassify = '';
});

// 上传界面打开关闭
$("span.glyphicon-cloud-upload").click(function(){
	if($(this).css("cursor") == "not-allowed"){
		return false;
	}
	if($(this).parents(".add_NonStandard").length){
		$(".add_NonStandard_details_files").slideToggle();
	}else if($(this).parents(".update_NonStandard").length){
		$(".update_NonStandard_details_files").slideToggle();
	}
});

// 根据部门选择人员
$(document).on("change",".info_Department",function(){
	var that = $(this);
	var iVal = $(this).val();
	if(iVal == "" || iVal == "请选择"){
		return false;
	}else{
		globalGetStaffAllInfoByDepart(iVal,function(data){
			var str = '';
			data.map(function(currentValue, index, arr){
				if(index > 0){
					str+='<option value="'+currentValue.StaffName+'" text="'+currentValue.ID+'">'+currentValue.StaffName+'</option>';
				}
			});
			if(that.parents(".add_NonStandard").length){
				$(".add_NonStandard .info_Name").empty().append(str);
			}else if(that.parents(".update_NonStandard").length){
				$(".update_NonStandard .info_Name").empty().append(str);
			}
		});
	}
});

// 添加模块信息的提交
$("#NonStandard_addsubmit_1").click(function(){
	for(var kk in addSubmitObj){
		if(kk=="Type"){
			addSubmitObj[kk] = "add";
			continue;
		}
		if(kk=="TotalAmount" || kk=="DetailJson" || kk=="TravelJson"){
			continue;
		}
		addSubmitObj[kk] = $(".add_NonStandard").find(".info_"+kk).val();
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
	var TotalAmount = 0.00;
	$(".add_NonStandard .detail_td_Amount").each(function(){
		if($(this).text() != ""){
			TotalAmount+=Number($(this).text());
		}
	});
	addSubmitObj.TotalAmount = TotalAmount.toFixed(2);
	// DetailJson
	var DetailJson = [];
	$(".add_NonStandard_details_div tbody tr").each(function(){
		var dom = $(this);
		var iFlag = checkLineAllNull(dom);
		if(!iFlag){
			var item = {};
			item.Type = dom.attr("value");
			item.Amount = Number(dom.children(".detail_td_Amount").text()).toFixed(2);
			item.MainContent = dom.children(".detail_td_MainContent").text().trim();
			item.CustomerName = dom.children(".detail_td_CustomerName").text().trim();
			item.City = dom.find(".detail_td_City_1").val().trim()+";;"+dom.find(".detail_td_City_2").val().trim();
			item.Time = dom.find(".detail_td_Time_1").val()+";;"+dom.find(".detail_td_Time_2").val();
			DetailJson.push(item);
		}
	});
	addSubmitObj.DetailJson = JSON.stringify(DetailJson);
	// TravelJson checkLineAllNull2
	var TravelJson = [];
	$(".add_NonStandard_details_address tbody tr").each(function(){
		var dom = $(this);
		var iFlag = checkLineAllNull2(dom);
		if(!iFlag){
			var item = {};
			item.TravelPlace = dom.children(".detail_td_TravelPlace").text().trim();
			item.MainContent = dom.children(".detail_td_MainContent").text().trim();
			item.TravelTime = dom.find(".detail_td_TravelTime_1").val()+";;"+dom.find(".detail_td_TravelTime_2").val();
			item.Days = Number(dom.children(".detail_td_Days").text());
			TravelJson.push(item);
		}
	});
	addSubmitObj.TravelJson = JSON.stringify(TravelJson);
	console.log("add我执行了吗");
	console.log(addSubmitObj);
	$.ajax({
		type: "POST",
		url: "Reimburse",
		dataType: 'text',
		data: addSubmitObj,
		success: function(data){
			if(data==="0"){
				$.MsgBox_Unload.Alert("提示","信息添加失败！");
			}else{
				$(".add_NonStandard blockquote .glyphicon-cloud-upload, #NonStandard_addsubmit").prop("disabled",false).css("cursor","pointer");
				reimburseState.addResID = data;
				$(".add_NonStandard .info_Name, .add_NonStandard .info_Department").prop("disabled",true);
				$.MsgBox_Unload.Alert("提示","信息添加成功！");
				// 暂存年月
				reimburseState.addHasSummitYM = globalGetToday(false).substring(0, globalGetToday(false).length - 3).replace(/-/g,"");
			}
		},
		error: function(){
			$.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
		},
		beforeSend: function(XMLHttpRequest){
            $("#NonStandard_addsubmit_1").css("cursor","not-allowed").prop("disabled","disabled");
        },
		complete: function(XMLHttpRequest, textStatus){
		    if(textStatus=="success"){
		    }
		    $("#NonStandard_addsubmit_1").css("cursor","pointer").prop("disabled",false);
		}
	});
});

// add提交第二步
$("#NonStandard_addsubmit").click(function(){
	var ID = reimburseState.addResID;
	var BillScreenshot = $(".add_NonStandard .info_BillScreenshot").val();
	var ElectronicInvoice = $(".add_NonStandard .info_ElectronicInvoice").val();
	var TravelPaper = $(".add_NonStandard .info_TravelPaper").val();
	var Others = $(".add_NonStandard .info_Others").val();
	$.ajax({
		type: "POST",
		url: "Reimburse",
		data: {
			Type: "saveFile",
			ID: ID,
			BillScreenshot: BillScreenshot,
			ElectronicInvoice: ElectronicInvoice,
			TravelPaper: TravelPaper,
			Others: Others
		},
		dataType: "text"
	}).then(function(data){
		if(data=="true"){
			$.MsgBox_Unload.Alert("提示","附件保存成功！");
			$("#NonStandard_addclose").trigger("click");
		}else if(data=="false"){
			$.MsgBox_Unload.Alert("提示","附件保存失败！");
			// sosuoInit();
			// getPageData(undefined, undefined, 1);
		}
	},function(){
		$.MsgBox_Unload.Alert("提示","网络繁忙！");
	});
});

// 修改模块信息的提交
$("#NonStandard_updatesubmit").click(function(){
	for(var kk in updateSubmitObj){
		if(kk=="Type"){
			updateSubmitObj[kk] = "update";
			continue;
		}
		if(kk=="ID" || kk=="Pass" || kk=="TotalAmount" || kk=="DetailJson" || kk=="TravelJson"){
			continue;
		}
		updateSubmitObj[kk] = $(".update_NonStandard").find(".info_"+kk).val();
	}
	// 表单验证
	for(var kkk in updateSubmitObj){
		updateSubmitObj[kkk] = globalDataHandle(updateSubmitObj[kkk],"").trim();
		if(kkk=="Department"&&updateSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择部门！");
			return false;
		}
		if(kkk=="Name"&&updateSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择姓名！");
			return false;
		}
	}
	var TotalAmount = 0.00;
	$(".update_NonStandard .detail_td_Amount").each(function(){
		if($(this).text() != ""){
			TotalAmount+=Number($(this).text());
		}
	});
	updateSubmitObj.TotalAmount = TotalAmount.toFixed(2);
	// DetailJson
	var DetailJson = [];
	$(".update_NonStandard_details_div tbody tr").each(function(){
		var dom = $(this);
		var iFlag = checkLineAllNull(dom);
		if(!iFlag){
			var item = {};
			item.Type = dom.attr("value");
			item.Amount = Number(dom.children(".detail_td_Amount").text()).toFixed(2);
			item.MainContent = dom.children(".detail_td_MainContent").text().trim();
			item.CustomerName = dom.children(".detail_td_CustomerName").text().trim();
			item.City = dom.find(".detail_td_City_1").val().trim()+";;"+dom.find(".detail_td_City_2").val().trim();
			item.Time = dom.find(".detail_td_Time_1").val()+";;"+dom.find(".detail_td_Time_2").val();
			DetailJson.push(item);
		}
	});
	updateSubmitObj.DetailJson = JSON.stringify(DetailJson);
	// TravelJson checkLineAllNull2
	var TravelJson = [];
	$(".update_NonStandard_details_address tbody tr").each(function(){
		var dom = $(this);
		var iFlag = checkLineAllNull2(dom);
		if(!iFlag){
			var item = {};
			item.TravelPlace = dom.children(".detail_td_TravelPlace").text().trim();
			item.MainContent = dom.children(".detail_td_MainContent").text().trim();
			item.TravelTime = dom.find(".detail_td_TravelTime_1").val()+";;"+dom.find(".detail_td_TravelTime_2").val();
			item.Days = Number(dom.children(".detail_td_Days").text());
			TravelJson.push(item);
		}
	});
	updateSubmitObj.TravelJson = JSON.stringify(TravelJson);
	console.log("update我执行了吗");
	console.log(updateSubmitObj);
	$.ajax({
		type: "POST",
		url: "Reimburse",
		dataType: 'text',
		data: updateSubmitObj,
		success: function(data){
			if(data == "true"){
				$("#NonStandard_updateclose").trigger("click");
				var icurrentPage = Number($("span#currentPage").text());
				getDataByGoPage(icurrentPage);
				$.MsgBox_Unload.Alert("提示","信息修改成功！");
			}else if(data == "false"){
				$.MsgBox_Unload.Alert("提示","信息修改失败！");
			}
		},
		error: function(){
			$.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
		},
		beforeSend: function(XMLHttpRequest){
            $("#NonStandard_updatesubmit").css("cursor","not-allowed").prop("disabled","disabled");
        },
		complete: function(XMLHttpRequest, textStatus){
		    if(textStatus=="success"){
		    }
		    $("#NonStandard_updatesubmit").css("cursor","pointer").prop("disabled",false);
		}
	});
});

// 年月的点击切换
$(document).on("click", ".m_button_r_in .dropdown-menu>li", function(){
	var inText = $(this).text();
	var inTitle = $(this).attr("title");
	$(this).parent().prev().prev().text(inText);
	$(this).parent().prev().prev().attr("title",inTitle);
});

// 搜索
$(".m_button_r_in_r input[value='搜索']").click(function(){
	var	Year = $(".m_button_r_in_l div.btn-group>button:nth-child(1)").attr("title");
	var	Month = $(".m_button_r_in_m div.btn-group>button:nth-child(1)").attr("title");
	if(Year == "选择年"){
		Year = new Date().getFullYear();
		$(".m_button_r_in_l div.btn-group>button:nth-child(1)").attr("title", Year);
		$(".m_button_r_in_l div.btn-group>button:nth-child(1)").text(Year+"年");
	}
	getPageData(Year, Month, 1);
});
// 搜索取消
$(".m_button_r_in_r input[value='取消']").click(function(){
	sosuoInit();
	getPageData(undefined, undefined, 1);
	$("#jumpNumber").val("");
	reimburseState.exportAll = false;
	$(".m_button_l>input:nth-child(2)").val("全选").removeClass("btn-warning").addClass("btn-info");
});

// 翻页功能
$("#jumpNumber").on("input propertychange",function(){
    var newVal = $(this).val().replace(/[^\d]/g,'');
    $(this).val(newVal);
});

	// 翻页
$("#fistPage").click(function(){
    var currentPage =1;
    getDataByGoPage(currentPage);
});

$("#lastPage").click(function(){
    var currentPage =Number($("#allPage").text());
    getDataByGoPage(currentPage);
});

$("#upPage").click(function(){
    var currentPage = Number($("#currentPage").text());
    if(currentPage == 1){
        return;
    }else{
        currentPage--;
        getDataByGoPage(currentPage);
    }
});

$("#nextPage").click(function(){
    var currentPage = Number($("#currentPage").text());
    var pageCounts = Number($("#allPage").text());
    if(currentPage == pageCounts){
        return;
    }else{
        currentPage++;
        getDataByGoPage(currentPage);
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
        getDataByGoPage(currentPage);
    }
});

/*上传整体*/
// input file域触发点击
$(".trigger_click").click(function(e){
	e.preventDefault();
	$(this).next().trigger("click");
});

// 点击浏览切换文件
$(".modal-body .row fieldset input[type='file']").on("change",function(){
	var $iParent = $(this).parents(".iParent");
	if(!$iParent.find(".add_fileList_info").is(':visible')){
		$iParent.find(".add_fileList_info").slideDown(150);
	}
	console.log($(this));
	console.log($(this)[0]);
	console.log($(this)[0].files);
	var curFileList = $(this)[0].files;
	var curFileListStr = '';
	var uploadFileNo = reimburseState.allUploadObj[$(this).parents(".iParent").data("iclassify")].uploadFileNo;
	var uploadFileList = reimburseState.allUploadObj[$(this).parents(".iParent").data("iclassify")].uploadFileList;
	$.each(curFileList, function(iname, ivalue){
		uploadFileNo++;
		curFileListStr+='<li class="list-group-item" title="'+ivalue.name+'" value="'+uploadFileNo+'"><span class="badge noUpload">删除</span>'+ivalue.name+'</li>';
		uploadFileList[uploadFileNo] = ivalue;
		// curFileListStr+=ivalue.name+"::";
	});
	$iParent.find("ul").append(curFileListStr);
	$iParent.find(".add_fileList_info>div>div.progress-bar").prop("aria-valuenow","0").css("width","0%").text("0%");
});

// 等待上传文件删除
$(document).on("click",".iParent ul>li>span.noUpload:contains('删除')",function(){
	var iValue = $(this).parent().attr("value");
	delete reimburseState.allUploadObj[$(this).parents(".iParent").data("iclassify")].uploadFileList[iValue];
	$(this).parents(".iParent").find("input[type='file']").val("");
	$(this).parent().remove();
});

// 正式文件上传
$(".iParent label[class^='add_info_upload']>button").click(function(){
	if(Object.keys(reimburseState.allUploadObj[$(this).parents(".iParent").data("iclassify")].uploadFileList).length == 0){
		$.MsgBox_Unload.Alert("上传提示","请选择文件！");
		return false;
	}
	var iParentDOM = $(this).parents(".iParent");
	var Folder = "报销申请"+reimburseState.allUploadObj.HasSummitYM+"-"+reimburseState.allUploadObj.StaffName+"-"+reimburseState.allUploadObj.ID;
	var iThat = $(this);
	uploadFiles2(iParentDOM, Folder, iThat);
});

// 报销详情模态框弹出
$(document).on("click", ".reimburse_detail_td span", function(){
	var curParent = $(this).parent();
	var RequestID = curParent.siblings(".update_td").data("iid").toString();
	var StaffName = curParent.siblings(".hastd_Name").text();
	// 保存提交日期
	reimburseState.allUploadObj.HasSummitYM = curParent.siblings(".hastd_FilingDate").text().trim().split("-")[0] + curParent.siblings(".hastd_FilingDate").text().trim().split("-")[1];
	reimburseState.allUploadObj.StaffName = StaffName;
	reimburseState.allUploadObj.ID = RequestID;
	$.ajax({
		type: "GET",
		url: "ReimburseApplication",
		data: {
			RequestID: RequestID
		},
		dataType: "json"
	}).then(function(res){
		var detailData = res.detail;
		var travelData = res.travel;
		if(detailData.length == 1 && travelData.length == 1){
			$.MsgBox_Unload.Alert("查看详情提示","无报销详情记录");
		}else{
			// 详情
			var detailStr = '';
			detailData.map(function(currentValue, index, arr){
				if(index > 0){
					detailStr+='<tr data-iid="'+currentValue.ID+'">'+
							'<td title="'+currentValue.Type+'">'+currentValue.Type+'</td>'+
							'<td title="'+currentValue.Amount+'">'+currentValue.Amount+'</td>'+
							'<td title="'+currentValue.MainContent+'">'+currentValue.MainContent+'</td>'+
							'<td title="'+currentValue.CustomerName+'">'+currentValue.CustomerName+'</td>'+
							'<td title="'+currentValue.City.replace(";;"," -- ")+'">'+currentValue.City.replace(";;"," -- ")+'</td>'+
							'<td title="'+currentValue.Time.replace(";;"," -- ")+'">'+currentValue.Time.replace(";;"," -- ")+'</td>'+
							'<td class="detail_filename_td"><input type="file"><span class="detail_filename">12327643718234784567890</span><span class="glyphicon glyphicon-open" aria-hidden="true"></span><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></td>'+
						'</tr>';
				}
			});
			$(".modal-body_detail tbody").empty().append(detailStr);
			// 事由等
			var travelStr = '';
			if(travelData.length == 1){
				travelStr+='<tr>'+
						'<td>出差地点</td>'+
						'<td></td>'+
						'<td>事由</td>'+
						'<td></td>'+
						'<td>往返时间</td>'+
						'<td></td>'+
						'<td>天数</td>'+
						'<td></td>'+
					'</tr>';
			}else{
				travelData.map(function(currentValue, index, arr){
					if(index > 0){
						travelStr+='<tr data-iid="'+currentValue.ID+'">'+
								'<td>出差地点</td>'+
								'<td title="'+currentValue.TravelPlace+'">'+currentValue.TravelPlace+'</td>'+
								'<td>事由</td>'+
								'<td title="'+currentValue.MainContent+'">'+currentValue.MainContent+'</td>'+
								'<td>往返时间</td>'+
								'<td title="'+currentValue.TravelTime.replace(";;"," -- ")+'">'+currentValue.TravelTime.replace(";;"," -- ")+'</td>'+
								'<td>天数</td>'+
								'<td title="'+currentValue.Days+'" class="details_calc_days">'+currentValue.Days+'</td>'+
							'</tr>';
					}
				});
			}
			$(".modal-body_travel tbody").empty().append(travelStr);
			var item2 = $(".modal-body_travel tbody .details_calc_days");
			var insertItem2 = $(".modal-body_travel .insert_detail_calc_days");
			calcDays(item2, insertItem2);
			$(".modal-header .modal-title").text(StaffName+"的报销详情");
			$(".bg_cover").slideDown(250);
			$('#reimburseModal').modal('show');

			// 文件回显
			// 滴滴电子发票
			var oldFileStrA = curParent.siblings(".hastd_ElectronicInvoice").data("ivalue");
			if(oldFileStrA !== null && oldFileStrA !== undefined){
				var iAttachmentArr = $.grep(String(oldFileStrA).split("::"),function(currentValue,index){
				    return currentValue != "";
				});
				var iAttachmentStr = '';
				iAttachmentArr.map(function(currentValue, index, arr){
					iAttachmentStr+='<li class="list-group-item list-group-item-info" title="'+currentValue+'"><span class="badge uploaded">删除</span><span class="badge">已上传</span>'+currentValue+'</li>';
				});
				$("#add_fileList_ul").empty().append(iAttachmentStr);
			}
			// 滴滴行程单
			var oldFileStrB = curParent.siblings(".hastd_TravelPaper").data("ivalue");
			if(oldFileStrB !== null && oldFileStrB !== undefined){
				var iAttachmentArr2 = $.grep(String(oldFileStrB).split("::"),function(currentValue,index){
				    return currentValue != "";
				});
				var iAttachmentStr2 = '';
				iAttachmentArr2.map(function(currentValue, index, arr){
					iAttachmentStr2+='<li class="list-group-item list-group-item-info" title="'+currentValue+'"><span class="badge uploaded">删除</span><span class="badge">已上传</span>'+currentValue+'</li>';
				});
				$("#add_fileList_ul2").empty().append(iAttachmentStr2);
			}
			// 滴滴电子发票
			var oldFileStrC = curParent.siblings(".hastd_ElectronicInvoice").data("ivalue");
			if(oldFileStrC !== null && oldFileStrC !== undefined){
				var iAttachmentArr3 = $.grep(String(oldFileStrC).split("::"),function(currentValue,index){
				    return currentValue != "";
				});
				var iAttachmentStr3 = '';
				iAttachmentArr3.map(function(currentValue, index, arr){
					iAttachmentStr3+='<li class="list-group-item list-group-item-info" title="'+currentValue+'"><span class="badge uploaded">删除</span><span class="badge">已上传</span>'+currentValue+'</li>';
				});
				$("#add_fileList_ul3").empty().append(iAttachmentStr3);
			}
		}
	},function(){
		$.MsgBox_Unload.Alert("查看详情提示","网络繁忙！");
	});
});

// 详情模态框关闭事件
$('#reimburseModal').on('hide.bs.modal', function (e) {
	$(".bg_cover").slideUp(250);

	$("div.add_fileList_info").each(function(){
		if($(this).is(':visible')){
			$(this).slideUp(150, function(){
				$(this).children("div").children("div.progress-bar").attr("aria-valuenow","0").css("width","0%").text("0%");
			});
		}
	});
	$(".iParent ul").empty();	

	reimburseState.allUploadObj["a"].uploadFileNo = 0;
	reimburseState.allUploadObj["b"].uploadFileNo = 0;
	reimburseState.allUploadObj["c"].uploadFileNo = 0;
	for(var k in reimburseState.allUploadObj["a"].uploadFileList){
		delete reimburseState.allUploadObj["a"].uploadFileList[k];
	}
	for(var kk in reimburseState.allUploadObj["b"].uploadFileList){
		delete reimburseState.allUploadObj["b"].uploadFileList[kk];
	}
	for(var kkk in reimburseState.allUploadObj["c"].uploadFileList){
		delete reimburseState.allUploadObj["c"].uploadFileList[kkk];
	}
	$(".iParent input[type='file']").val("");
});

// 详情记录单条选文件
$(document).on("click", ".detail_filename+span", function(){
	$(this).prev().prev().val("").trigger("click");
});
$(document).on("change", ".detail_filename_td>input[type='file']", function(){
	var curTr = $(this).parent().parent();
	var Folder = "报销申请"+reimburseState.allUploadObj.HasSummitYM+"-"+reimburseState.allUploadObj.StaffName+"-"+reimburseState.allUploadObj.ID;
	var fileObj = $(this)[0].files;
	console.log(fileObj);
	uploadFiles(Folder, fileObj, curTr);
});

/*上传整体end*/
// 详情提交
$(".modal-footer>.btn-success").click(function(){

});

// 上传附件框打开
$(".line_relative>input[value='上传']").click(function(){
	var curClassifyHook;
	if($(this).parents(".add_NonStandard").length){
		curClassifyHook = "add_NonStandard";
	}else if($(this).parents(".update_NonStandard").length){
		curClassifyHook = "update_NonStandard";
	}
	reimburseState.curFileupload = curClassifyHook+"++"+$(this).siblings("input[type='text']").attr("class");
	var ih1 = $(".add_NonStandard").height();
	var ih2 = $(".update_NonStandard").height();
	var ih = ih1 - ih2 > 0 ? ih1:ih2;
	$(".upload_bgcover").css("height",ih+"px");
	$(".upload_bgcover, .dropFileBox").slideDown(250);
});

// 上传事件
$("input.dropUp2").on("click",function(){
	var judgeFile = $("input.serFinRepUploadName").val();
	var judgeFile2 = $("#serFinRepUpload").val();
	var filename1=judgeFile.replace(/.*(\/|\\)/, ""); 
	var filename2=judgeFile2.replace(/.*(\/|\\)/, ""); 
	var fileExt1=(/[.]/.exec(filename1)) ? /[^.]+$/.exec(filename1.toLowerCase()) : '';
	var fileExt2=(/[.]/.exec(filename2)) ? /[^.]+$/.exec(filename2.toLowerCase()) : '';
	if(fileExt1==''||fileExt2==''){
		$.MsgBox_Unload.Alert("上传提示","文件没有后缀名，请重新上传");
		return false;
	}
	// else if(fileExt1!=''&&fileExt2!=''){
	// 	if(fileExt1["0"]!="pdf"||fileExt2["0"]!="pdf"){
	// 		$.MsgBox_Unload.Alert("上传提示","文件后缀名错误，请上传pdf");
	// 		return false;
	// 	}
	// }
	if(judgeFile && judgeFile2){
		var StaffName, ID, iMonth;
		if(reimburseState.OperaClassify == 'add'){
			StaffName = $(".add_NonStandard .info_Name").val();
			ID = reimburseState.addResID;
			iMonth = reimburseState.addHasSummitYM;
		}else if(reimburseState.OperaClassify == 'update'){
			StaffName = $(".update_NonStandard .info_Name").val();
			ID = updateSubmitObj.ID;
			iMonth = reimburseState.updateHasSummitYM;
		}
		/*后台文件夹姓名出现null，暂时处理之*/
		if(!StaffName){
			$.MsgBox_Unload.Alert("上传提示","请检查是否错误更换了姓名");
			return false;
		}
		uploadFiles(StaffName, ID, iMonth);
		// uploadFiles("施聪华", "3");
	}else{
		$.MsgBox_Unload.Alert("上传提示","请检查是否选择或更换了文件");
	}
});

// 点击浏览切换文件
$("#serFinRepUpload").on("change",function(){
	console.log($(this));
	console.log($(this)[0]);
	console.log($(this)[0].files);
	var curFileList = $(this)[0].files;
	var curFileListStr = '';
	$.each(curFileList, function(iname, ivalue){
		curFileListStr+=ivalue.name+"::";
	});
	$("span.isUpload").text("");
	$(".progressIn").css("width","30px");
	$(".progressIn").text("0%");
	// console.log("文件上传改变值"+$(this).val());
	// var newFileName1 = $(this).val().indexOf("\\fakepath\\")>-1?$(this).val().split("\\fakepath\\")[1]:$(this).val().split("\\").pop();
	// console.log("赋给input的值"+newFileName1);
	$("input.serFinRepUploadName").val(curFileListStr);
	$("input.serFinRepUploadName").attr("title",curFileListStr);
});

// 上传附件框关闭
$(".dropFileTit span").on("click",function(){
	$(".upload_bgcover, .dropFileBox").slideUp(250);
	$("span.isUpload").text("");
	$(".progressIn").css("width","30px");
	$(".progressIn").text("0%");
	$("input.serFinRepUploadName").val("");
	$("input.serFinRepUploadName").attr("title","");
	$("#serFinRepUpload").val("");
});

// 附件点击下载
$(document).on("click","span.downFile_span",function(e){
	e.stopPropagation();
	var fileName = $(this).text().trim();
	if(fileName==""||fileName=="--"){
		$.MsgBox_Unload.Alert("下载提示","无文件可下载");
		return false;
	}
	var ID = $(this).parent().siblings(".update_td").data("iid").toString();
	var StaffName = $(this).parent().siblings(".hastd_Name").text();
	var FolderMonthHook = $(this).parent().siblings(".hastd_FilingDate").text();
	if(!regHasDate.test(FolderMonthHook) && !regHasDateNoCTOR.test(FolderMonthHook)){
		$.MsgBox_Unload.Alert("提示","提交日期没有或格式错误！");
		return false;
	}
	var FolderMonth;
	if(regHasDate.test(FolderMonthHook)){
		FolderMonth = FolderMonthHook.match(regHasDate)[0].split("-");
		FolderMonth.pop();
		FolderMonth = FolderMonth.join("");
	}else if(regHasDateNoCTOR.test(FolderMonthHook)){
		FolderMonth = FolderMonthHook.match(regHasDateNoCTOR)[0].substring(0,FolderMonthHook.match(regHasDateNoCTOR)[0].length-2);
	}
	$.ajax({
		type: "GET",
		url: "ReimburseAttachment",
		data: {
			Type: 'download',
			ID: ID,
			StaffName: StaffName,
			FileName: fileName,
			FolderMonth: FolderMonth
		}
	}).then(function(data){
		window.open(data);
		if(!reimburseState.hasReminedShield){
			setTimeout(function(){
				$.MsgBox_Unload.Alert("新窗口导出提示","若无反应请取消浏览器屏蔽");
			},500);
			reimburseState.hasReminedShield = true;
		}
	},function(){
		$.MsgBox_Unload.Alert("下载提示","网络繁忙或文件已被删除！");
	});
	// var baseUrl = window.location.href.split(globalProjectName)[0];
	// window.location.href = url+"LogisticsFile/File/"+fileName;
	// window.open(baseUrl+"LogisticsFile/File/"+fileName);
	// console.log("服务报告下载"+baseUrl+"LogisticsFile/File/"+fileName);
});


// 审核
$(document).on("click",".hastd_Pass input",function(){
	$("div.popover:not(:last)").remove();
	if($(this).val() == "通过"){
		reimburseState.Application.State = '';
		// $("div.popover").hide();
		$("div.popover").remove();
		return false;
	}
	reimburseState.Application.ID = $(this).parent().siblings(".update_td").data("iid").toString();
	// reimburseState.Application.State = $(this).val();
	reimburseState.Application.Name = $(this).parent().siblings(".hastd_Name").text();
	reimburseState.Application.FilingDate = $(this).parent().siblings(".hastd_FilingDate").text();
	if($("div.popover").length == 0){
		reimburseState.Application = {
			ID: null,
			State: '',
			Name: '',
			FilingDate: '',
			Reason: ''
		};
	}
});

$(document).on("click","#ReimburseApplication_y",function(){
	reimburseState.Application.State = '通过';
	$.ajax({
		type: "POST",
		url: "ReimburseApplication",
		data: {
			ID: reimburseState.Application.ID,
			State: reimburseState.Application.State
		},
		dataType: "text"
	}).then(function(data){
		if(data.indexOf("操作成功")>-1){
			$("div.popover").remove();
			var currentPage = Number($("span#currentPage").text());
			getDataByGoPage(currentPage);
			reimburseState.Application = {
				ID: null,
				State: '',
				Name: '',
				FilingDate: '',
				Reason: ''
			};
		}
		$.MsgBox_Unload.Alert("审批提示",data);
	},function(){
		$.MsgBox_Unload.Alert("审批提示","网络繁忙！");
	});
});

$(document).on("click","#ReimburseApplication_n",function(){
	reimburseState.Application.State = '未通过';
	var Reason = $("#exampleInputEmail1").val().trim();
	$("#ReimburseApplication_n").css("cursor","not-allowed").prop("disabled","disabled");
	$.ajax({
		type: "POST",
		url: "ReimburseApplication",
		data: {
			ID: reimburseState.Application.ID,
			State: reimburseState.Application.State,
			Name: reimburseState.Application.Name,
			FilingDate: reimburseState.Application.FilingDate,
			Reason: Reason
		},
		dataType: "text"
	}).then(function(data){
		if(data.indexOf("操作成功")>-1){
			$("div.popover").remove();
			var currentPage = Number($("span#currentPage").text());
			getDataByGoPage(currentPage);
			reimburseState.Application = {
				ID: null,
				State: '',
				Name: '',
				FilingDate: '',
				Reason: ''
			};
		}
		$.MsgBox_Unload.Alert("审批提示",data);
	},function(){
		$.MsgBox_Unload.Alert("审批提示","网络繁忙！");
	}).always(function(){
		$("#ReimburseApplication_n").css("cursor","pointer").prop("disabled",false);
	});
});

// 天数刷新
$(document).on("click", ".refresh_days", function(){
	var $irefresh = $(this).parent().next();
	var startT = $(this).parent().prev().children(".detail_td_TravelTime_1").val();
	var endT = $(this).parent().prev().children(".detail_td_TravelTime_2").val();
	if(!startT || !endT) return false;
	$irefresh.text(Number(globalCalcTimeDiff(endT,startT,true).replace("天","")) + 1);
	$irefresh.trigger("blur");
});

// 天数blur事件
$(document).on("blur","[class$='NonStandard_details_address'] .detail_td_Days",function(){
	var iVal = $(this).text().trim();
	if(!reimburse_daysReg.test(iVal)){
		$(this).text("");
	}else{
		$(this).text(iVal);
	}
	var classify;
	if($(this).parents(".add_NonStandard").length){
		classify = "add";
	}else if($(this).parents(".update_NonStandard").length){
		classify = "update";
	}
	var item2 = $("."+classify+"_NonStandard_details_address_table>tbody .detail_td_Days");
	var insertItem2 = $("."+classify+"_NonStandard_details_address_table>tfoot .insert_detail_td_calc_days");
	calcDays(item2, insertItem2);
});

// 金额blur事件
$(document).on("blur","[class$='NonStandard_details_div'] .detail_td_Amount",function(){
	var iVal = $(this).text().trim();
	if(!regIntOr1To2DeciPoi.test(iVal)){
		$(this).text("");
	}else{
		$(this).text(iVal);
	}
});

// 复选框联动
$(document).on("click",".m_table thead .t0>input",function(){
	$(".m_table tbody td:nth-child(1)>input").prop("checked",$(this).prop("checked"));
});
$(document).on("click",".m_table tbody td:nth-child(1)>input",function(){
	$(".m_table thead .t0>input").prop("checked",$(".m_table tbody td:nth-child(1)>input").length==$(".m_table tbody td:nth-child(1)>input").filter(":checked").length);
});

// 导出
$(".m_button_l>input:nth-child(2)").click(function(){
	$(this).toggleClass("btn-info btn-warning");
	if($(this).hasClass("btn-warning")){
		$(this).val("全不选");
		reimburseState.exportAll = true;
		$(".m_table thead .t0>input, .m_table tbody td:nth-child(1)>input").prop("checked",true);
	}else if($(this).hasClass("btn-info")){
		$(this).val("全选");
		reimburseState.exportAll = false;
		$(".m_table thead .t0>input, .m_table tbody td:nth-child(1)>input").prop("checked",false);
	}
});
$(".m_button_l>input:nth-child(3)").click(function(){
	var ids;
	if(reimburseState.exportAll){
		ids = undefined;
	}else{
		var idsArr = [];
		$(".m_table tbody td:nth-child(1)>input").each(function(){
			if($(this).prop("checked")){
				idsArr.push($(this).parent().siblings(".update_td").data("iid").toString());
			}
		});
		if(idsArr.length == 0){
			return false;
		}
		ids = idsArr.join(",");
	}
	var Year,Month;
	if(reimburseState.hasSearch){
		Year = $(".m_button_r_in_l div.btn-group>button:nth-child(1)").attr("title");
		Month = $(".m_button_r_in_m div.btn-group>button:nth-child(1)").attr("title");
	}else{
		Year = undefined;
		Month = undefined;
	}
	$.ajax({
		type: "GET",
		url: "ReimburseAttachment",
		data: {
			Type: 'export',
			ids: ids,
			Year: Year,
			Month: Month
		}
	}).then(function(data){
		window.open(data);
		if(!reimburseState.hasReminedShield){
			setTimeout(function(){
				$.MsgBox_Unload.Alert("新窗口导出提示","若无反应请取消浏览器屏蔽");
			},500);
			reimburseState.hasReminedShield = true;
		}
	},function(){
		$.MsgBox_Unload.Alert("导出提示","网络繁忙或文件已被删除！");
	});
});

// 通知列表
$(".m_button_l>input:nth-child(4)").click(function(){
	window.open("ReimburseMailList");
});