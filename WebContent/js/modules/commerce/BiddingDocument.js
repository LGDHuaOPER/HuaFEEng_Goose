/*variable、function define*/
// add定时器
var addWinCleseTimer;
// 定义部门
var BiddingDocDepart = ["销售部","应用部","标准服务部"];
var BiddingDocDepartStaff = [];
// 定义状态集
var BiddingDocumentState = new Object();
BiddingDocumentState.uploadFileNo = 0;
BiddingDocumentState.uploadFileList = {};
BiddingDocumentState.hasSearch = false;
BiddingDocumentState.searchColumn = undefined;
BiddingDocumentState.searchContent = undefined;

// 主页面获取数据
function getDataByPage(currentPage, Column, Content){
	$.ajax({
		type: "GET",
		url: "BiddingDocument",
		data: {
			LoadType: "data",
			CurrentPage: currentPage,
			Column: Column,
			Content: Content
		},
		dataType: "json"
	}).then(function(res){
		var data = res.data;
		var pageCount = res.pageCount;
		renderPageData(data, currentPage, pageCount);
		if(!Column && !Content){
			BiddingDocumentState.hasSearch = false;
			BiddingDocumentState.searchColumn = undefined;
			BiddingDocumentState.searchContent = undefined;
		}else{
			BiddingDocumentState.hasSearch = true;
			BiddingDocumentState.searchColumn = Column;
			BiddingDocumentState.searchContent = Content;
		}
	},function(){
		$.MsgBox_Unload.Alert("提示","服务器繁忙！页面数据获取失败");
	}).always(function(){

	});
}

// 渲染页面数据
function renderPageData(data, currentPage, pageCount){
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
				
				str+='<tr>'+
					'<td class="update_td" data-iid="'+currentValue.ID+'">'+parseInt((currentPage-1)*10 + index)+'</td>'+
					'<td class="hastd_FileName" title="'+currentValue.FileName+'" value="'+currentValue.Path+'">'+currentValue.FileName+'</td>'+
					'<td class="hastd_Year" title="'+currentValue.Year+'">'+currentValue.Year+'</td>'+
					'<td class="hastd_Submitter" title="'+currentValue.Submitter+'">'+currentValue.Submitter+'</td>'+
					'<td class="hastd_Score" title="'+currentValue.Score+'">'+currentValue.Score+'</td>'+
					'<td class="hastd_downloadFile" value="'+currentValue.Path+'" title="点此下载文件"><span class="glyphicon glyphicon-save-file" aria-hidden="true"></span></td>'+
				'</tr>';
			}
		});
	}
	$(".m_table table tbody").empty().append(str);
	$(".m_page #currentPage").text(currentPage);
	$(".m_page #allPage").text(pageCount);
	pageStyle(currentPage, pageCount);
}

// 翻页组件按钮逻辑
function pageStyle(currentPage,pageCount){
    if(pageCount == 1){
        $("#fistPage, #upPage, #nextPage, #lastPage, #Gotojump").prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
    }else if(currentPage == 1){
        $("#fistPage, #upPage").prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
        $("#lastPage, #nextPage, #Gotojump").prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }else if(currentPage == pageCount){
        $("#lastPage, #nextPage").prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
        $("#fistPage, #upPage, #Gotojump").prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }else{
        $("#lastPage, #nextPage, #fistPage, #upPage, #Gotojump").prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }
}

// 翻页获取数据
function getDataByGoPage(currentPage){
	var Column;
	var Content;
	if(!BiddingDocumentState.hasSearch){
		Column = undefined;
		Content = undefined;
	}else{
		Column = BiddingDocumentState.searchColumn;
		Content = BiddingDocumentState.searchContent;
	}
	getDataByPage(currentPage, Column, Content);
}

// 搜索组件Init
function searchInit(){
	$(".m_button_r_in_l #search_year").hide().val("");
	$(".m_button_r_in_l .in_search").show().val("");
	$(".m_button_r_in>div .input-group-btn>button:nth-child(1)").text("选择字段").attr("title","选择字段");
}

//上传文件
function uploadFiles(Year, iThat){                                               
    var formData = new FormData();
    formData.enctype="multipart/form-data";
    formData.append("Year",Year);
    // formData.append("ID",ID);
    var fileList = BiddingDocumentState.uploadFileList;
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
        url: "BiddingDocumentUpload",
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
                    $("div.fileList_info>div:nth-child(1)>div.progress-bar").attr("aria-valuenow",newWidth).css("width",percent).text(percent);
                    // console.log("进度条宽度："+newWidth); 
                    // $(".progressIn").css("width",newWidth+"px");
                    // $(".progressIn").text(percent);
                }, false); // for handling the progress of the upload
            }
            return myXhr;
        },                    
        success: function(data){
        	if(data.length > 0){
        		var LiStr = '';
        		data.map(function(currentValue, index, arr){
        			if(currentValue.Message.indexOf("成功")>-1){
        				LiStr+='<li class="list-group-item list-group-item-success" title="'+currentValue.FileName+'"><span class="badge">成功</span>'+currentValue.FileName+'</li>';
        			}else if(currentValue.Message.indexOf("失败")>-1){
        				LiStr+='<li class="list-group-item list-group-item-danger" title="'+currentValue.FileName+'"><span class="badge">失败</span>'+currentValue.FileName+'</li>';
        			}else{
        				LiStr+='<li class="list-group-item list-group-item-warning" title="'+currentValue.FileName+'"><span class="badge">异常</span>'+currentValue.FileName+'</li>';
        			}
        		});
        		$("#fileList_ul").empty().append(LiStr);
        		var successFloat = (($("#fileList_ul>li.list-group-item-success").length)/(data.length)).toFixed(3);
        		var successValueNow = successFloat*100;
        		var successPercent = successValueNow+"%";
        		$("div.fileList_info>div:nth-child(2)>div.progress-bar").attr("aria-valuenow",successValueNow).css("width",successPercent).text(successPercent);
        	}else if(data.length == 0){
        		$.MsgBox_Unload.Alert("上传提示","文件读取至服务器失败！");
        	}
        	addWinCleseTimer = setTimeout("addWindowClose()",2000);
        	getDataByPage(1, undefined, undefined);
    //     	if(data.success == undefined && data.error == undefined){
    //     		$.MsgBox_Unload.Alert("提示","网络繁忙，文件读取失败！");
    //     	}else{
	   //      	var errorList = data.error.split("::");
	   //      	var successList = data.success.split("::");
	   //      	errorList.pop();
	   //      	successList.pop();
	   //      	var imsg = '';
	   //      	var iimsg = '';
	   //      	var erLen = errorList.length;
	   //      	var sucLen = successList.length;
	   //      	if(erLen == 0){
	   //      		imsg = '全部上传成功';
	   //      		iimsg = sucLen+'个文件上传成功';
	   //      	}else if(sucLen == 0){
	   //      		imsg = '全部上传失败';
	   //      		iimsg = erLen+'个文件上传失败';
	   //      		$(".progressIn").css("width","30px");
	   //      		$(".progressIn").text("0%");
	   //      	}else{
	   //      		imsg = '部分上传成功';
	   //      		iimsg = sucLen+'个文件上传成功，'+erLen+'个上传失败';
	   //      	}
	   //      	$("span.isUpload").text(imsg);
				// var curFileuploadArr;
				// if(reimburseState.curFileupload.indexOf("++")>-1){
				// 	curFileuploadArr = reimburseState.curFileupload.split("++");
				// }else{
				// 	curFileuploadArr = ["",""];
				// } 
				// var oldFileArray = $("."+curFileuploadArr[0]+" ."+curFileuploadArr[1]).val().split("::");
				// oldFileArray = $.grep(oldFileArray,function(currentValue,index){
				//     return currentValue != "";
				// });
				// var newFileArray0 = oldFileArray.concat(successList);
				// var newFileArray = globalArrStrUnique(newFileArray0);
				// var newFileString = newFileArray.length == 0 ? "" : newFileArray.join("::")+"::";
				// $("."+curFileuploadArr[0]+" ."+curFileuploadArr[1]).val(newFileString);
				// $("."+curFileuploadArr[0]+" ."+curFileuploadArr[1]).attr("title",newFileString);
				// if(sucLen != 0){
				// 	$(".dropFileTit span").trigger("click");
				// }
	   //      	$.MsgBox_Unload.Alert("提示",iimsg);
    //     	}
        },
        error: function(){
        	$("div.fileList_info>div>div.progress-bar").prop("aria-valuenow","0").css("width","0%").text("0%");
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

function addWindowClose(){
	$("#NonStandard_addclose").trigger("click");
}

/*page onload*/
$(function(){
	getDataByPage(1, undefined, undefined);
	// 获取员工列表
	var iIndex = 0;
	var myFunc = function(data){
	    if(data.length > 1){
	        data.map(function(currentValue,index,arr){
	            if(index > 0){
	                BiddingDocDepartStaff.push(currentValue.StaffName);
	            }
	        });
	    }
	    iIndex++;
	    if(iIndex < BiddingDocDepart.length){
	        $.when(
	            $.ajax({
	                type: 'GET',
	                url: 'GetStaffInfo',
	                data: {
	                    Type: "common",
	                    Department: BiddingDocDepart[iIndex]
	                },
	                dataType: "json"
	            })
	            ).done(myFunc);
	    }else if(iIndex == BiddingDocDepart.length){
	        console.log(BiddingDocDepartStaff);
	        var staffStr = '<option value="" disabled>请选择</option>';
	        BiddingDocDepartStaff.map(function(currentValue, index, arr){
	        	staffStr+='<option value="'+currentValue+'">'+currentValue+'</option>';
	        });
	        $("select[name='update_info_Submitter']").empty().append(staffStr);
	    }
	};
	$.when(
	    $.ajax({
	        type: 'GET',
	        url: 'GetStaffInfo',
	        data: {
	            Type: "common",
	            Department: BiddingDocDepart[iIndex]
	        },
	        dataType: "json"
	    })
	    ).then(myFunc, function(){
	    $.MsgBox_Unload.Alert("获取员工姓名提示","初始化失败！");
	});

	globalBuildYOptionStr(2016,$("select[name='search_Year']"));
});


/*event handler*/
// 添加上传
$(".m_button_l>input[value='上传']").click(function(){
	$(".add_NonStandard_body_in select, .add_NonStandard_body_in input").val("");
	$(".bg_cover, .add_NonStandard").slideDown(250);
});
// 添加上传关闭
$(".add_NonStandard_tit_r, #NonStandard_addclose").click(function(){
	$(".bg_cover, .add_NonStandard").slideUp(250);
	if($('div.fileList_info').is(':visible')){
		$('div.fileList_info').slideUp(150, function(){
			$("div.fileList_info>div>div.progress-bar").attr("aria-valuenow","0").css("width","0%").text("0%");
		});
	}
	BiddingDocumentState.uploadFileNo = 0;
	for(var k in BiddingDocumentState.uploadFileList){
		delete BiddingDocumentState.uploadFileList[k];
	}
	$("#fileList_ul").empty();
	if(addWinCleseTimer){
		clearTimeout(addWinCleseTimer);
		addWinCleseTimer = undefined;
	}
});

// 点击浏览切换文件
$("#BiddingDocumentUpload").on("change",function(){
	if(!$('div.fileList_info').is(':visible')){
		$('div.fileList_info').slideDown(150);
	}
	console.log($(this));
	console.log($(this)[0]);
	console.log($(this)[0].files);
	var curFileList = $(this)[0].files;
	var curFileListStr = '';
	$.each(curFileList, function(iname, ivalue){
		BiddingDocumentState.uploadFileNo++;
		curFileListStr+='<li class="list-group-item" title="'+ivalue.name+'" value="'+BiddingDocumentState.uploadFileNo+'"><span class="badge">删除</span>'+ivalue.name+'</li>';
		BiddingDocumentState.uploadFileList[BiddingDocumentState.uploadFileNo] = ivalue;
		// curFileListStr+=ivalue.name+"::";
	});
	$("#fileList_ul").append(curFileListStr);
	// console.log("文件上传改变值"+$(this).val());
	// var newFileName1 = $(this).val().indexOf("\\fakepath\\")>-1?$(this).val().split("\\fakepath\\")[1]:$(this).val().split("\\").pop();
	// console.log("赋给input的值"+newFileName1);
});

// 文件删除
$(document).on("click","#fileList_ul>li>span:contains('删除')",function(){
	var iValue = $(this).parent().attr("value");
	delete BiddingDocumentState.uploadFileList[iValue];
	$(this).parent().remove();
});

// 添加提交
$("#NonStandard_addsubmit").click(function(){
	var iThat = $(this);
	var Year = $(".add_NonStandard select[name='search_Year']").val();
	if(!Year){
		$.MsgBox_Unload.Alert("添加提示","请选择年份！");
		return false;
	}
	if(Object.keys(BiddingDocumentState.uploadFileList).length == 0){
		$.MsgBox_Unload.Alert("添加提示","请选择文件！");
		return false;
	}
	uploadFiles(Year, iThat);
});

// 修改
$(document).on("click",".update_td",function(){
	$(".bg_cover, .update_NonStandard").slideDown(250);
	var tr = $(this).parent();
	$(".update_NonStandard").attr("value",$(this).data("iid"));
	$(".update_NonStandard select[name='search_Year']").val(tr.children(".hastd_Year").text());
	$(".update_NonStandard [name='update_info_Submitter']").val(tr.children(".hastd_Submitter").text());
	$(".update_NonStandard [name='update_info_Score']").val(tr.children(".hastd_Score").text());
});
// 修改关闭
$(".update_NonStandard_tit_r, #NonStandard_updateclose").click(function(){
	$(".bg_cover, .update_NonStandard").slideUp(250);
});

// 修改提交
$("#NonStandard_updatesubmit").click(function(){
	var ID = $(".update_NonStandard").attr("value");
	var Year = $(".update_NonStandard select[name='search_Year']").val();
	if(!Year){
		$.MsgBox_Unload.Alert("修改提示","请选择年份！");
		return false;
	}
	var Submitter = $(".update_NonStandard [name='update_info_Submitter']").val();
	var Score = $(".update_NonStandard [name='update_info_Score']").val();
	var iThat = $(this);
	iThat.css("cursor","not-allowed").prop("disabled","disabled");
	$.ajax({
		type: "POST",
		url: "BiddingDocument",
		data: {
			ID: ID,
			Year: Year,
			Submitter: Submitter,
			Score: Score
		},
		dataType: "text"
	}).then(function(data){
		// console.log(typeof data);
		if(data == "true"){
			$.MsgBox_Unload.Alert("修改提示", "修改成功！");
			getDataByGoPage(Number($("span#currentPage").text()));
			$("#NonStandard_updateclose").trigger("click");
		}else if(data == "false"){
			$.MsgBox_Unload.Alert("修改提示", "修改失败！");
		}else{
			$.MsgBox_Unload.Alert("修改提示", data);
		}
	},function(){
		$.MsgBox_Unload.Alert("提示","服务器繁忙！");
	}).always(function(){
		iThat.css("cursor","pointer").prop("disabled",false);
	});
});

// 搜索字段点击
$(document).on("click",".m_button_r_in_l ul.dropdown-menu>li",function(){
	var iText = $(this).text();
	var iTitle = $(this).attr("title");
	$(this).parent().siblings("button:nth-child(1)").attr("title",iTitle).text(iText);
	if(iText == "年份"){
		$(".m_button_r_in_l .in_search").hide(50,function(){
			$(".m_button_r_in_l #search_year").show().val("");
		});
	}else{
		$(".m_button_r_in_l #search_year").hide(50,function(){
			$(".m_button_r_in_l .in_search").show().val("");
		});
	}
});

// 搜索
$(".m_button_r_in_r>input:nth-child(1)").click(function(){
	var Column = $(".m_button_r_in_l div.input-group-btn>button:nth-child(1)").attr("title");
	if(!Column || Column == "选择字段"){
		$.MsgBox_Unload.Alert("搜索提示","请选择字段！");
		return false;
	}
	var Content;
	if(Column == "年份"){
		Content = $("select#search_year").val();
	}else{
		Content = $("input.in_search").val().trim();
	}
	if(!Content){
		$.MsgBox_Unload.Alert("搜索内容提示","请选择或填写内容！");
		return false;
	}
	getDataByPage(1, Column, Content);
});

// 取消搜索
$(".m_button_r_in_r>input:nth-child(2)").click(function(){
	getDataByPage(1, undefined, undefined);
	searchInit();
	$("#jumpNumber").val("");
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

// 下载
$(document).on("click",".hastd_downloadFile",function(){
	var iPath = $(this).attr("value");
	if(!iPath || iPath == "--"){
		$.MsgBox_Unload.Alert("下载提示","无数据或文件已被删除！");
		return false;
	}
	if(iPath.indexOf("E:")==0){
		iPath = iPath.replace(/\\/g,"/").substring(2);
		console.log(iPath);
	}
	window.open(globalHostName+iPath);
});

// 评分验证 change keydown keyup
$("input[name='update_info_Score']").on("input propertychange",function(e){
	e.stopPropagation();
	var iValue = $(this).val();
	if(iValue == ""){
		return false;
	}
	if(!reg0To120.test(iValue)){
		$(this).val("0");
	}else{
		$(this).val(iValue);
	}
});