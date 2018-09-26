(function(){
	$.Response_Load = {
		// 加载前方法，可在有大量DOM操作之前调用，ajax里可在beforeSend里调用
		Before: function (title,message,speed){
			LoadGropOpen(title,message);
			if(speed==="" || speed==undefined){
				alertGroupOpen(".eoulu-response-load-bg",".eoulu-response-load",200);
			}else{
				alertGroupOpen(".eoulu-response-load-bg",".eoulu-response-load",speed);
			}
			
			$(window).on("resize",function(){
				alertResponse(".eoulu-response-load",250,355);
			});
		},
		// 加载期间方法，一般在有大量DOM操作并有ajax请求的beforeSend里调用
		Loading: function (message){
			LoadAlterText(message);
		},
		// 加载后方法，ajax里可在complete里调用
		After: function (message,speed){
			LoadGropClose(message,speed);
		}
	};

	var LoadGropOpen = function (title,message){
		if($(".eoulu-response-load").length == 0){
			var _html = "";
			_html += '<div class="eoulu-response-load-bg" style="display:none"></div><div class="eoulu-response-load" style="display:none"><div class="eoulu-response-load-tit">' + title + '</div><div class="eoulu-response-load-message"><img width="190" height="14"><p>'+message+'</p></div></div>';
			$("body").append(_html); 
			LoadGropCss();
		}else{
			$(".eoulu-response-load-tit").text(title);
			$(".eoulu-response-load-message p").text(message);
		}
	};

	var LoadGropCss = function (){
		// 背景巨幕样式
		$(".eoulu-response-load-bg").css({ 'width': '100%', 'height': '100%', 'z-index': '99998', 'position': 'fixed',
      'filter': 'Alpha(opacity=60)', 'background-color': 'rgba(10%,20%,30%,0.6)', 'top': '0', 'left': '0', 'opacity': '0.6'
    });
		// loading主体
		$(".eoulu-response-load").css({'position':'fixed','width':'355px','height':'190px','z-index':'99999','background-color':'rgba(255,255,255,0.95)','border-radius':'10px','font-family':'microsoft yahei'});
		alertResponse(".eoulu-response-load",250,355);
		// title
		$(".eoulu-response-load-tit").css({'height':'30px','line-height':'30px','font-size':'16px','color': '#fff','padding': '2px 15px','background-color': '#707070','border-top-left-radius':'10px','border-top-right-radius':'10px'});
		$(".eoulu-response-load-message img").attr("src","./image/response-loading.gif");
		$(".eoulu-response-load-message img").css({'margin':'30px auto'});
		$(".eoulu-response-load-message").css({'margin':'5px auto','width':'350px','height':'165px','font-size':'16px','text-align':'center'});
		$(".eoulu-response-load-message p").css({'height': '30px', 'line-height': '30px','width':'350px','text-overflow':'ellipsis','white-space':'nowrap','overflow':'hidden','text-align':'center'});
	};

	var LoadAlterText = function (message){
		if($(".eoulu-response-load").length != 0){
			$(".eoulu-response-load-message p").text(message);
		}
	};

	var LoadGropClose = function (message,speed){
		if($(".eoulu-response-load").length != 0){
			$(".eoulu-response-load-message p").text(message);
			if(!speed){
				alertGroupDelayClose(".eoulu-response-load-bg",".eoulu-response-load",200);
			}else{
				alertGroupDelayClose(".eoulu-response-load-bg",".eoulu-response-load",speed);
				}
		}
	};
})();
/*变量与函数定义*/
// 添加修改提交data对象
var addSubmitObj = new Object();
addSubmitObj.Type = null;
// addSubmitObj.ID = undefined;
addSubmitObj.Department = null;
addSubmitObj.Name = null;
// addSubmitObj.Pass = undefined;
addSubmitObj.TotalAmount = null;
addSubmitObj.TravelDay = null;
// addSubmitObj.BillScreenshot = null;
// addSubmitObj.ElectronicInvoice = null;
// addSubmitObj.TravelPaper = null;
// addSubmitObj.Others = null;
addSubmitObj.DetailJson = null;
addSubmitObj.TravelJson = null;
var updateSubmitObj = new Object();
updateSubmitObj.Type = null;
updateSubmitObj.ID = null;
updateSubmitObj.Department = null;
updateSubmitObj.Name = null;
updateSubmitObj.Pass = null;
updateSubmitObj.TotalAmount = null;
updateSubmitObj.TravelDay = null;
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
reimburseState.remindDeleteloading = false;
reimburseState.detailObject = {
	DeleteFile: "",
	ElectronicInvoice: "",
	TravelPaper: "",
	Others: "",
	DeleteFileNoSubmit: ""
};

reimburseState.hasSearch = false;
reimburseState.Application = {
	ID: null,
	State: '',
	Name: '',
	FilingDate: '',
	Reason: ''
};
reimburseState.exportAll = false;
reimburseState.detailModalUpdate = false;
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
		str+='<tr><td colspan="8">无数据......</td></tr>';
	}else if(data.length > 1){
		data.map(function(currentValue, index, arr){
			if(index > 0){
				var iNo = parseInt((CurrentPage-1)*10 + index);
				var titleStr = "审核第"+iNo+"条记录<span class='glyphicon glyphicon-remove popover_close' aria-hidden='true'></span>";
				var ireason = currentValue.Reason == "--" ? "" : currentValue.Reason;
				var contentStr = "<div class='container-fluid'>"+
							"<div class='row'>"+
								"<div class='form-group'>"+
								    "<label>已发送过的未通过原因：</label>"+
								    "<div class='well well-sm'>"+ireason+"</div>"+
								"</div>"+
							"</div>"+
							"<div class='row'>"+
								"<div class='form-group'>"+
								    "<label for='exampleInputEmail1'>原因</label>"+
								    "<input type='text' class='form-control' id='exampleInputEmail1' placeholder='填写需要发送的未通过原因'>"+
								"</div>"+
							"</div>"+
							"<div class='row' style='text-align:center'>"+
								"<div style='display: inline-block;'>"+
									"<input type='button' class='btn btn-success' id='ReimburseApplication_y' value='通过'><input type='button' class='btn btn-warning' id='ReimburseApplication_n' value='未通过'>"+
								"</div>"+
							"</div>"+
						"</div>";

				str+='<tr>'+
					'<td><input type="checkbox"></td>'+
					'<td class="update_td" data-iid="'+currentValue.ID+'">'+iNo+'</td>'+
					'<td class="hastd_Department" title="'+currentValue.Department+'">'+currentValue.Department+'</td>'+
					'<td class="hastd_Name" title="'+currentValue.Name+'">'+currentValue.Name+'</td>'+
					'<td class="hastd_TotalAmount" title="'+currentValue.TotalAmount+'">'+currentValue.TotalAmount+'</td>'+
					'<td class="reimburse_detail_td" title="点此查看详情"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></td>'+
					'<td class="hastd_FilingDate" title="'+currentValue.FilingDate+'">'+currentValue.FilingDate+'</td>'+
					'<td class="hastd_BillScreenshot" title="'+currentValue.BillScreenshot+'" data-ivalue="'+currentValue.BillScreenshot+'"></td>'+
					'<td class="hastd_ElectronicInvoice" title="'+currentValue.ElectronicInvoice+'" data-ivalue="'+currentValue.ElectronicInvoice+'"></td>'+
					'<td class="hastd_TravelPaper" title="'+currentValue.TravelPaper+'" data-ivalue="'+currentValue.TravelPaper+'"></td>'+
					'<td class="hastd_Others" title="'+currentValue.Others+'" data-ivalue="'+currentValue.Others+'"></td>'+
					'<td class="hastd_Pass" title="'+ireason+'"><input type="button" value="'+currentValue.Pass+'" data-container="body" data-toggle="popover" data-placement="left" data-content="'+contentStr+'" title="'+titleStr+'"></td>'+
				'</tr>';
			}
		});
	}
	$(".m_table table tbody").empty().append(str);
	$(".m_page #currentPage").text(CurrentPage);
	$(".m_page #allPage").text(pageCounts);
	pageStyle(CurrentPage, pageCounts);
	$('[data-toggle="popover"]:not([value="通过"])').popover({
		html: true
	});
	$(".m_table thead .t0>input, .m_table tbody td:nth-child(1)>input").prop("checked",reimburseState.exportAll);
	$('[data-toggle="popover"]:not([value="通过"])').each(function(){
		$(this).attr("title", $(this).parent().attr("title"));
	});
	$('[data-toggle="popover"][value="通过"]').each(function(){
		$(this).attr("title", "已通过");
	});
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
function uploadFiles(Folder, fileObj, curTr, OperateType, OldAttachment){                                               
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
        			curTr.find(".detail_filename_td .detail_filename").text(data[0].FileName).attr("title", data[0].FileName);
        			curTr.find(".detail_filename_td").data("operatetype", OperateType).data("attachment", data[0].FileName).data("oldattachment", OldAttachment);
	        		$.Response_Load.After("上传成功", 1700);
        		}else if(data[0].Message.indexOf("失败") > -1){
        			$.Response_Load.After("上传失败", 1700);
        		}else{
        			$.Response_Load.After("文件已存在", 1700);
        		}
        	}else if(data.length == 0){
        		$.Response_Load.After("文件读取至服务器失败！", 1700);
        	}
        },
        error: function(){
        	$.Response_Load.After("网络繁忙！上传失败！", 1700);
        },
		beforeSend: function(XMLHttpRequest){
            $.Response_Load.Before("上传提示","正在提交...", 300);
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
        				LiStr+='<li class="list-group-item list-group-item-success" title="'+currentValue.FileName+'"><span class="badge uploaded">删除</span><span class="badge">成功</span>'+currentValue.FileName+'</li>';
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

// 模态框关闭init
function modalCloseInit(){
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

	reimburseState.remindDeleteloading = true;
	// 详情文件提交状态init
	reimburseState.detailObject.DeleteFile = "";

	reimburseState.detailModalUpdate = false;
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
	var item2 = $(".add_NonStandard_details_address_table>tbody .detail_td_Days");
	var insertItem2 = $(".add_NonStandard_details_address_table>tfoot .insert_detail_td_calc_days");
	calcDays(item2, insertItem2);
});
$(".add_NonStandard_tit_r, #NonStandard_addclose").click(function(){
	$(".bg_cover, .add_NonStandard").slideUp(300);
	for(var k in addSubmitObj){
		addSubmitObj[k] = null;
	}
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
		// var copyReimburseCategory = [...reimburseCategory];
		var copyReimburseCategory = _.cloneDeep(reimburseCategory);
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
					'<td class="detail_td_Attachment">'+currentValue.Attachment+'</td>'+
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
$("#NonStandard_addsubmit").click(function(){
	for(var kk in addSubmitObj){
		if(kk=="Type"){
			addSubmitObj[kk] = "add";
			continue;
		}
		if(kk=="TotalAmount" || kk=="DetailJson" || kk=="TravelJson" || kk=="TravelDay"){
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
	var TravelDay = $(".add_NonStandard td.insert_detail_td_calc_days").text();
	if(TravelDay === "" || TravelDay == "NaN"){
		$.MsgBox_Unload.Alert("提交总天数提示","天数为空或未刷新！");
		return false;
	}
	addSubmitObj.TravelDay = TravelDay;
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
	var iThat = $(this);
	$.ajax({
		type: "POST",
		url: "Reimburse",
		dataType: 'text',
		data: addSubmitObj,
		success: function(data){
			// if(data=="true"){
			// 	$.MsgBox_Unload.Alert("提示","附件保存成功！");
			// 	$("#NonStandard_addclose").trigger("click");
			// 	sosuoInit();
			// 	getPageData(undefined, undefined, 1);
			// }else if(data=="false"){
			// 	$.MsgBox_Unload.Alert("提示","附件保存失败！");
			// }
			if(data !== "0"){
				$.MsgBox_Unload.Alert("提示", "添加成功！");
				$("#NonStandard_addclose").trigger("click");
				sosuoInit();
				getPageData(undefined, undefined, 1);
			}else if(data === "0"){
				$.MsgBox_Unload.Alert("提示", "添加失败！");
			}
		},
		error: function(){
			$.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
		},
		beforeSend: function(XMLHttpRequest){
            eouluGlobal.C_btnDisabled(iThat, true, "正在提交...");
        },
		complete: function(XMLHttpRequest, textStatus){
		    if(textStatus=="success"){
		    }
		    eouluGlobal.C_btnAbled(iThat, true, "提交");
		}
	});
});

// 修改模块信息的提交
$("#NonStandard_updatesubmit").click(function(){
	for(var kk in updateSubmitObj){
		if(kk=="Type"){
			updateSubmitObj[kk] = "update";
			continue;
		}
		if(kk=="ID" || kk=="Pass" || kk=="TotalAmount" || kk=="DetailJson" || kk=="TravelJson" || kk=="TravelDay"){
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
	// 总天数
	var TravelDay = $(".update_NonStandard td.insert_detail_td_calc_days").text();
	if(TravelDay === "" || TravelDay == "NaN"){
		$.MsgBox_Unload.Alert("提交总天数提示","天数为空或未刷新！");
		return false;
	}
	updateSubmitObj.TravelDay = TravelDay;
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
			var iAttachment = dom.find(".detail_td_Attachment").text();
			if(iAttachment === undefined || iAttachment === null || iAttachment == "--"){
				iAttachment = "";
			}
			item.Attachment = iAttachment;
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
	var iThat = $(this);
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
            eouluGlobal.C_btnDisabled(iThat, true, "正在提交...");
        },
		complete: function(XMLHttpRequest, textStatus){
		    if(textStatus=="success"){
		    }
		    eouluGlobal.C_btnAbled(iThat, true, "提交");
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
	// console.log($(this));
	// console.log($(this)[0]);
	// console.log($(this)[0].files);
	var curFileList = $(this)[0].files;
	var curFileListStr = '';
	var uploadFileNo = reimburseState.allUploadObj[$(this).parents(".iParent").data("iclassify")].uploadFileNo;
	var uploadFileList = reimburseState.allUploadObj[$(this).parents(".iParent").data("iclassify")].uploadFileList;
	$.each(curFileList, function(iname, ivalue){
		uploadFileNo++;
		curFileListStr+='<li class="list-group-item" title="'+ivalue.name+'" value="'+uploadFileNo+'"><span class="badge noUpload">删除</span><span class="badge">待上传</span>'+ivalue.name+'</li>';
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
			// 保存提交日期
			reimburseState.allUploadObj.HasSummitYM = curParent.siblings(".hastd_FilingDate").text().trim().split("-")[0] + curParent.siblings(".hastd_FilingDate").text().trim().split("-")[1];
			reimburseState.allUploadObj.StaffName = StaffName;
			reimburseState.allUploadObj.ID = RequestID;
			// 详情
			var detailStr = '';
			var SumAmount = 0;
			detailData.map(function(currentValue, index, arr){
				if(index > 0){
					var iAttachment = currentValue.Attachment == "--" ? "" : currentValue.Attachment;
					iAttachment = iAttachment == "" ? "未上传" : iAttachment;
					// 加和金额
					var iiAmount = currentValue.Amount;
					if(iiAmount == "--" || iiAmount == undefined || iiAmount == ""){
						iiAmount = 0;
					}
					SumAmount+=Number(iiAmount);
					detailStr+='<tr data-iid="'+currentValue.ID+'">'+
							'<td title="'+currentValue.Type+'">'+currentValue.Type+'</td>'+
							'<td title="'+currentValue.Amount+'">'+currentValue.Amount+'</td>'+
							'<td title="'+currentValue.MainContent+'">'+currentValue.MainContent+'</td>'+
							'<td title="'+currentValue.CustomerName+'">'+currentValue.CustomerName+'</td>'+
							'<td title="'+currentValue.City.replace(";;"," -- ")+'">'+currentValue.City.replace(";;"," -- ")+'</td>'+
							'<td title="'+currentValue.Time.replace(";;"," -- ")+'">'+currentValue.Time.replace(";;"," -- ")+'</td>'+
							'<td class="detail_filename_td" data-operatetype="no" data-attachment="" data-oldattachment=""><input type="file"><span class="detail_filename" title="'+iAttachment+'" data-originfile="'+iAttachment+'">'+iAttachment+'</span><span class="glyphicon glyphicon-open" aria-hidden="true"></span><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></td>'+
						'</tr>';
				}
			});
			SumAmount = SumAmount.toFixed(2);
			detailStr+='<tr><td>金额总计</td><td colspan="6" style="text-align: left;padding-left: 15px;">'+SumAmount+'</td></tr>';
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
			// 其他
			var oldFileStrC = curParent.siblings(".hastd_Others").data("ivalue");
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
	if(!reimburseState.detailModalUpdate){
		var OperateTypeChange = false;
		$("div.modal-body_detail tbody>tr").each(function(){
			var OperateType = $(this).children(".detail_filename_td").data("operatetype");
			if(OperateType == "add" || OperateType == "update" || OperateType == "delete"){
				OperateTypeChange = true;
				return false;
			}
		});
		if(OperateTypeChange || $(".iParent ul>li.list-group-item-success").length || reimburseState.detailObject.DeleteFileNoSubmit != ""){
			$.Response_Load.Before("直接关闭提示","正在撤销操作...",500);
			// var ElectronicInvoice = reimburseState.detailObject.ElectronicInvoice;
			// var TravelPaper = reimburseState.detailObject.TravelPaper;
			// var Others = reimburseState.detailObject.Others;

			var fileArray3 = [];
			$(".iParent ul>li.list-group-item-success").each(function(){
				fileArray3.push($(this).attr("title"));
			});
			var newfileArray3 = globalArrStrUnique(fileArray3);
			var DeleteFile = newfileArray3.length == 0 ? "" : (newfileArray3.join("::")+"::");

			$("div.modal-body_detail tbody>tr").each(function(){
				var OperateType = $(this).children(".detail_filename_td").data("operatetype");
				if(OperateType === undefined || OperateType === null || OperateType == "no") return true;

				var OldAttachment = $(this).children(".detail_filename_td").data("oldattachment");
				if(OldAttachment === undefined || OldAttachment === null){
					OldAttachment = "";
				}

				var Attachment = $(this).children(".detail_filename_td").data("attachment");
				if(Attachment === undefined || Attachment === null){
					Attachment = "";
				}
				// 删除origin
				var submitOldAttachment;
				if(OldAttachment == ""){
					submitOldAttachment = "";
				}else{
					var originFile = $(this).find("span.detail_filename").data("originfile");
					if(originFile == "未上传"){
						originFile = "";
					}
					var submitOldAttachmentArr = _.pull(OldAttachment.toString().split("::"), originFile);
					submitOldAttachment = (submitOldAttachmentArr.length == 0 ? "" : (submitOldAttachmentArr.join("::")+"::"));
				}

				DeleteFile+=submitOldAttachment;
				DeleteFile+=(Attachment == "" ? "" : (Attachment + "::"));
			});
			DeleteFile+=reimburseState.detailObject.DeleteFileNoSubmit;
			console.log(DeleteFile);
			$.Response_Load.Loading("正在撤销操作...");
			$.ajax({
				type: "POST",
				url: "ReimburseAttachment",
				data: {
					ID: undefined,
					ElectronicInvoice: undefined,
					TravelPaper: undefined,
					Others: undefined,
					AttachmentJson: undefined,
					Folder: ("报销申请"+reimburseState.allUploadObj.HasSummitYM+"-"+reimburseState.allUploadObj.StaffName+"-"+reimburseState.allUploadObj.ID),
					DeleteFile: DeleteFile,
					IsRevoke: "YES"
				}
			}).then(function(data){
				console.log(typeof data);
				if(data == "true"){
					$.Response_Load.After("撤销成功！", 1000);
					getDataByGoPage(Number($("span#currentPage").text()));
					reimburseState.detailObject.DeleteFile = "";
					reimburseState.detailObject.DeleteFileNoSubmit = "";
				}else if(data == "false"){
					$.Response_Load.After("撤销失败！", 1000);
				}else{
					$.Response_Load.After(data, 1000);
				}
			},function(){
				$.Response_Load.After("网络繁忙！撤销失败", 1000);
			}).always(function(){
				modalCloseInit();
			});
		}else{
			modalCloseInit();
		}
	}else{
		modalCloseInit();
	}
});

// 详情记录单条选文件
$(document).on("click", ".detail_filename+span", function(){
	$(this).prev().prev().val("").trigger("click");
});
$(document).on("change", ".detail_filename_td>input[type='file']", function(){
	var curTr = $(this).parent().parent();
	var OperateType, OldAttachment;
	var oldFileName = $(this).next().attr("title");
	if(oldFileName == "未上传" && $(this).parent().data("operatetype") == "no"){
		OperateType = "add";
	}else{
		OperateType = "update";
	}
	var ioldattachment = $(this).parent().data("oldattachment");
	if(ioldattachment === undefined || ioldattachment === null || ioldattachment == ""){
		OldAttachment = (oldFileName == "未上传" ? "" : oldFileName);
	}else{
		OldAttachment = String(ioldattachment);
		OldAttachment += (oldFileName == "未上传" ? "" : ("::" + oldFileName));
	}

	var Folder = "报销申请"+reimburseState.allUploadObj.HasSummitYM+"-"+reimburseState.allUploadObj.StaffName+"-"+reimburseState.allUploadObj.ID;
	var fileObj = $(this)[0].files;
	// console.log(fileObj);
	uploadFiles(Folder, fileObj, curTr, OperateType, OldAttachment);
});
/*上传整体end*/

// 详情提交
$(".modal-footer>.btn-success").click(function(){
	var fileArray = [];
	$("#add_fileList_ul>li.list-group-item-success, #add_fileList_ul>li.list-group-item-info").each(function(){
		fileArray.push($(this).attr("title"));
	});
	var newfileArray = globalArrStrUnique(fileArray);
	var ElectronicInvoice = newfileArray.length == 0 ? "" : (newfileArray.join("::")+"::");

	var fileArray2 = [];
	$("#add_fileList_ul2>li.list-group-item-success, #add_fileList_ul2>li.list-group-item-info").each(function(){
		fileArray2.push($(this).attr("title"));
	});
	var newfileArray2 = globalArrStrUnique(fileArray2);
	var TravelPaper = newfileArray2.length == 0 ? "" : (newfileArray2.join("::")+"::");

	var fileArray3 = [];
	$("#add_fileList_ul3>li.list-group-item-success, #add_fileList_ul3>li.list-group-item-info").each(function(){
		fileArray3.push($(this).attr("title"));
	});
	var newfileArray3 = globalArrStrUnique(fileArray3);
	var Others = newfileArray3.length == 0 ? "" : (newfileArray3.join("::")+"::");

	var AttachmentArr = [];
	$("div.modal-body_detail tbody>tr").each(function(){
		var OperateType = $(this).children(".detail_filename_td").data("operatetype");
		if(OperateType === undefined || OperateType === null || OperateType == "no") return true;
		var item = {};
		item.ID = String($(this).data("iid"));
		item.OperateType = OperateType;
		var Attachment = $(this).children(".detail_filename_td").data("attachment");
		if(Attachment === undefined || Attachment === null){
			Attachment = "";
		}
		var OldAttachment = $(this).children(".detail_filename_td").data("oldattachment");
		if(OldAttachment === undefined || OldAttachment === null){
			OldAttachment = "";
		}
		if(OperateType == "add"){
			item.Attachment = Attachment;
			item.iclassify = "R3";
		}else if(OperateType == "update"){
			item.OldAttachment = OldAttachment;
			item.Attachment = Attachment;
			item.iclassify = "R2";
		}else if(OperateType == "delete"){
			item.OldAttachment = OldAttachment;
			item.iclassify = "R1";
		}
		AttachmentArr.push(item);
	});
	console.log(_.sortBy(AttachmentArr, function(o) { return o.iclassify; }));
	var AttachmentJson = JSON.stringify(_.sortBy(AttachmentArr, function(o) { return o.iclassify; }));
	$.ajax({
		type: "POST",
		url: "ReimburseAttachment",
		data: {
			ID: reimburseState.allUploadObj.ID,
			ElectronicInvoice: ElectronicInvoice,
			TravelPaper: TravelPaper,
			Others: Others,
			AttachmentJson: AttachmentJson,
			Folder: ("报销申请"+reimburseState.allUploadObj.HasSummitYM+"-"+reimburseState.allUploadObj.StaffName+"-"+reimburseState.allUploadObj.ID),
			DeleteFile: reimburseState.detailObject.DeleteFile
		}
	}).then(function(data){
		if(data == "true"){
			$.MsgBox_Unload.Alert("更新提示","更新成功！");
			reimburseState.detailModalUpdate = true;
			reimburseState.detailObject.DeleteFile = "";
			reimburseState.detailObject.DeleteFileNoSubmit = "";
			$('#reimburseModal').modal('hide');
			getDataByGoPage(Number($("span#currentPage").text()));
		}else if(data == "false"){
			$.MsgBox_Unload.Alert("更新提示","更新失败！");
		}else{
			$.MsgBox_Unload.Alert("更新提示", data);
		}
	},function(){
		$.MsgBox_Unload.Alert("查看详情提示","网络繁忙！");
	}).always(function(){

	});
});

// 文件下载
$(document).on("click","li.list-group-item-success, li.list-group-item-info, span.detail_filename",function(e){
	e.stopPropagation();
	var fileName = $(this).attr("title");
	if(!fileName || fileName == "未上传"){
		$.MsgBox_Unload.Alert("下载提示","无数据或文件已被删除！");
		return false;
	}
	// var FolderMonthHook = $(this).parent().siblings(".hastd_FilingDate").text();
	// if(!regHasDate.test(FolderMonthHook) && !regHasDateNoCTOR.test(FolderMonthHook)){
	// 	$.MsgBox_Unload.Alert("提示","提交日期没有或格式错误！");
	// 	return false;
	// }
	// var FolderMonth;
	// if(regHasDate.test(FolderMonthHook)){
	// 	FolderMonth = FolderMonthHook.match(regHasDate)[0].split("-");
	// 	FolderMonth.pop();
	// 	FolderMonth = FolderMonth.join("");
	// }else if(regHasDateNoCTOR.test(FolderMonthHook)){
	// 	FolderMonth = FolderMonthHook.match(regHasDateNoCTOR)[0].substring(0,FolderMonthHook.match(regHasDateNoCTOR)[0].length-2);
	// }
	$.ajax({
		type: "GET",
		url: "ReimburseAttachment",
		data: {
			Type: 'download',
			ID: reimburseState.allUploadObj.ID,
			StaffName: reimburseState.allUploadObj.StaffName,
			FileName: fileName,
			FolderMonth: reimburseState.allUploadObj.HasSummitYM
		}
	}).then(function(data){
		window.open(data);
	},function(){
		$.MsgBox_Unload.Alert("下载提示","网络繁忙或文件已被删除！");
	});
});

// 已上传文件删除
$(document).on("click", "span.badge.uploaded", function(e){
	e.stopPropagation();
	if(!reimburseState.remindDeleteloading){
		$.Response_Load.Before("删除提示","已删除，请记得提交保存", 300);
		$.Response_Load.After("已删除，请记得提交保存", 800);
		reimburseState.remindDeleteloading = true;
	}
	reimburseState.detailObject.DeleteFile += $(this).parent().attr("title");
	reimburseState.detailObject.DeleteFile += "::";
	if($(this).parent().is(".list-group-item-success")){
		reimburseState.detailObject.DeleteFileNoSubmit += $(this).parent().attr("title");
		reimburseState.detailObject.DeleteFileNoSubmit += "::";
	}
	$(this).parent().remove();
});

// 票据截图删除
$(document).on("click", "td.detail_filename_td>.glyphicon-trash", function(e){
	e.stopPropagation();
	if($(this).prev().prev().attr("title") == "未上传") return false;
	if(!reimburseState.remindDeleteloading){
		$.Response_Load.Before("删除提示","已删除，请记得提交保存", 300);
		$.Response_Load.After("已删除，请记得提交保存", 800);
		reimburseState.remindDeleteloading = true;
	}
	var oldFileName = $(this).siblings(".detail_filename").attr("title");
	var oldattachment = $(this).parent().data("oldattachment");
	var newoldattachment;
	if(oldattachment === undefined || oldattachment === null || oldattachment == ""){
		newoldattachment = oldFileName;
	}else{
		newoldattachment = oldattachment+"::"+oldFileName;
	}
	$(this).parent().data("operatetype", "delete").data("oldattachment", newoldattachment).data("attachment", "").children(".detail_filename").attr("title", "未上传").text("未上传");
});

// 审核popover
var curThIndex = -1;
$(document).on("click", ".hastd_Pass>input", function(e){
	e.stopPropagation();
	if($(this).val() == "通过"){
		reimburseState.Application.State = '';
		return false;
	}
	reimburseState.Application.ID = $(this).parent().siblings(".update_td").data("iid").toString();
	reimburseState.Application.Name = $(this).parent().siblings(".hastd_Name").text();
	reimburseState.Application.FilingDate = $(this).parent().siblings(".hastd_FilingDate").text();

	curThIndex = $(this).parent().parent().index();
	$('.m_table tbody>tr:not(:eq('+curThIndex+')) [data-toggle="popover"]').popover('hide');
});

$(document).on("click", "div.popover", function(e){
	e.stopPropagation();
});

$(document).on("click", function(){
	$('[data-toggle="popover"]:not([value="通过"])').popover('hide');
});

// 取消
$(document).on("click", "span.popover_close", function(e){
	e.stopPropagation();
	$('[data-toggle="popover"]:not([value="通过"])').popover('hide');
});

$(document).on("hidden.bs.popover", '[data-toggle="popover"]:not([value="通过"])', function(){
	if($("div.popover").length === 0){
		reimburseState.Application = {
			ID: null,
			State: '',
			Name: '',
			FilingDate: '',
			Reason: ''
		};
	}
});
// $('[data-toggle="popover"]:not([value="通过"])').on('hidden.bs.popover', function () {
	
// });
// 审核popover end

// 通过
$(document).on("click","#ReimburseApplication_y",function(){
	var iThat = $(this);
	eouluGlobal.C_btnDisabled(iThat, true, "正在发送...");
	reimburseState.Application.State = '通过';
	$.ajax({
		type: "POST",
		url: "ReimburseApplication",
		data: {
			ID: reimburseState.Application.ID,
			State: reimburseState.Application.State,
			Name: reimburseState.Application.Name,
			FilingDate: reimburseState.Application.FilingDate
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
		eouluGlobal.C_btnAbled(iThat, true, "通过");
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
	},function(){
		$.MsgBox_Unload.Alert("导出提示","网络繁忙或文件已被删除！");
	});
});

// 通知列表
$(".m_button_l>input:nth-child(4)").click(function(){
	window.open("ReimburseMailList");
});