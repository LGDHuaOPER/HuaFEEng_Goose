/*variable、function define*/
// 定义部门
// var BiddingDocDepart = ["销售部","应用部","标准服务部"];
// var BiddingDocDepartStaff = [];
// 定义搜索字段
var search_all_param = new Object();
search_all_param.scheme = ["选择字段","应用类型","客户名称","方案名称","机台型号","制定时间"];
search_all_param.standard = ["选择字段","产品名称","产品类型","适用机台","版本号","设计时间","使用情况"];
search_all_param.customization = ["选择字段","产品名称","产品类型","适用机台","版本号","设计时间","使用情况"];
// 定义状态集
var ApplicationGalleryState = new Object();
ApplicationGalleryState.uploadFileNoCAD = 0;
ApplicationGalleryState.uploadFileListCAD = {};
ApplicationGalleryState.uploadFileNoPDF = 0;
ApplicationGalleryState.uploadFileListPDF = {};

ApplicationGalleryState.hasSearch = false;
ApplicationGalleryState.searchQueryType = undefined;
ApplicationGalleryState.searchColumn1 = undefined;
ApplicationGalleryState.searchContent1 = undefined;
ApplicationGalleryState.searchColumn2 = undefined;
ApplicationGalleryState.searchContent2 = undefined;
ApplicationGalleryState.hasLoadAddUpdateSection = false;
ApplicationGalleryState.hasLoadAddUpdatePageData = null;
ApplicationGalleryState.curUpdateID = null;

// 主页面获取数据
function getDataByPage(Category, QueryType, Column1, Content1, Column2, Content2, CurrentPage){
	$.ajax({
		type: "GET",
		url: "ApplicationGallery",
		data: {
			LoadType: "data",
			Category: Category,
			QueryType: QueryType,
			Column1: Column1,
			Content1: Content1,
			Column2: Column2,
			Content2: Content2,
			CurrentPage: CurrentPage
		},
		dataType: "json"
	}).then(function(res){
		var data = res.data;
		var pageCount = res.pageCount;
		renderPageData(Category, data, CurrentPage, pageCount);
		if(QueryType === undefined){
			ApplicationGalleryState.hasSearch = false;
		}else{
			ApplicationGalleryState.hasSearch = true;
		}
		ApplicationGalleryState.searchQueryType = QueryType;
		ApplicationGalleryState.searchColumn1 = Column1;
		ApplicationGalleryState.searchContent1 = Content1;
		ApplicationGalleryState.searchColumn2 = Column2;
		ApplicationGalleryState.searchContent2 = Content2;
	},function(){
		$.MsgBox_Unload.Alert("提示","服务器繁忙！页面数据获取失败");
	}).always(function(){

	});
}

// 渲染页面数据
function renderPageData(Category, data, CurrentPage, pageCount){
	var str = '';
	if(data.length == 1){
		var colspanNo;
		if(Category=="scheme"){
			colspanNo = 9;
		}else{
			colspanNo = 10;
		}
		str+='<tr><td colspan="'+colspanNo+'">无数据......</td></tr>';
	}else if(data.length > 1){
		if(Category == "scheme"){
			data.map(function(currentValue, index, arr){
				if(index > 0){
					var iNO = parseInt((CurrentPage-1)*10 + index);
					var iCADdrawingsStr = "";
					var iCADdrawings = currentValue.CADdrawings;
					var iCADdrawingsArr = iCADdrawings.split("::");
					iCADdrawingsArr = $.grep(iCADdrawingsArr,function(jcurrentValue,jindex){
						return jcurrentValue != "";
					});
					if(iCADdrawingsArr.length == 0){
						iCADdrawingsStr+="未上传";
					}else if(iCADdrawingsArr.length < 3){
						iCADdrawingsArr.map(function(currentValue, index, arr){
							if(index != iCADdrawingsArr.length){
								iCADdrawingsStr+="<span class='td_download_file' title='"+currentValue+"'>"+currentValue+"</span><br>";
							}else{
								iCADdrawingsStr+="<span class='td_download_file' title='"+currentValue+"'>"+currentValue+"</span>";
							}
						});
					}else{
						var iCADconStr = '<div class="container-fluid" style="padding-left: 1px;padding-right: 1px;padding-bottom:5px;">';
						iCADconStr+='<ol>';
						iCADdrawingsArr.map(function(icurrentValue, iindex, iarr){
							iCADconStr+='<li title="'+icurrentValue+'">'+icurrentValue+'</li>';
						});
						iCADconStr+='</ol>';
						iCADconStr+='</div>';

						iCADdrawingsStr+="<span class='td_download_file' title='"+iCADdrawingsArr[0]+"'>"+iCADdrawingsArr[0]+"</span><br>";
						iCADdrawingsStr+="<span class='td_download_file' title='"+iCADdrawingsArr[1]+"'>"+iCADdrawingsArr[1]+"</span><br>";
						iCADdrawingsStr+="<a tabindex='0' class='btn btn-info' role='button' data-toggle='popover' data-trigger='focus' data-placement='left' title='第"+iNO+"条记录--CAD图纸' data-content='"+iCADconStr+"'>显示更多...</a>";
					}

					var iPDFdrawingsStr = "";
					var iPDFdrawings = currentValue.PDFdrawings;
					var iPDFdrawingsArr = iPDFdrawings.split("::");
					iPDFdrawingsArr = $.grep(iPDFdrawingsArr,function(jcurrentValue,jindex){
						return jcurrentValue != "";
					});
					if(iPDFdrawingsArr.length == 0){
						iPDFdrawingsStr+="未上传";
					}else if(iPDFdrawingsArr.length < 3){
						iPDFdrawingsArr.map(function(currentValue, index, arr){
							if(index != iPDFdrawingsArr.length){
								iPDFdrawingsStr+="<span class='td_download_file' title='"+currentValue+"'>"+currentValue+"</span><br>";
							}else{
								iPDFdrawingsStr+="<span class='td_download_file' title='"+currentValue+"'>"+currentValue+"</span>";
							}
						});
					}else{
						var iPDFconStr = '<div class="container-fluid" style="padding-left: 1px;padding-right: 1px;padding-bottom:5px;">';
						iPDFconStr+='<ol>';
						iPDFdrawingsArr.map(function(icurrentValue, iindex, iarr){
							iPDFconStr+='<li title="'+icurrentValue+'">'+icurrentValue+'</li>';
						});
						iPDFconStr+='</ol>';
						iPDFconStr+='</div>';

						iPDFdrawingsStr+="<span class='td_download_file' title='"+iPDFdrawingsArr[0]+"'>"+iPDFdrawingsArr[0]+"</span><br>";
						iPDFdrawingsStr+="<span class='td_download_file' title='"+iPDFdrawingsArr[1]+"'>"+iPDFdrawingsArr[1]+"</span><br>";
						iPDFdrawingsStr+="<a tabindex='0' class='btn btn-info' role='button' data-toggle='popover' data-trigger='focus' data-placement='left' title='第"+iNO+"条记录--PDF图纸' data-content='"+iPDFconStr+"'>显示更多...</a>";
					}

					str+='<tr>'+
						'<td class="update_td" data-iid="'+currentValue.ID+'">'+parseInt((CurrentPage-1)*10 + index)+'</td>'+
						'<td class="hastd_ApplicationType" title="'+currentValue.ApplicationType+'">'+currentValue.ApplicationType+'</td>'+
						'<td class="hastd_CustomerName" title="'+currentValue.CustomerName+'">'+currentValue.CustomerName+'</td>'+
						'<td class="hastd_SchemeName" title="'+currentValue.SchemeName+'">'+currentValue.SchemeName+'</td>'+
						'<td class="hastd_MachineModel" title="'+currentValue.MachineModel+'">'+currentValue.MachineModel+'</td>'+
						'<td class="hastd_MakeTime" title="'+currentValue.MakeTime+'">'+currentValue.MakeTime+'</td>'+
						'<td class="hastd_CADdrawings" data-ivalue="'+currentValue.CADdrawings+'">'+iCADdrawingsStr+'</td>'+
						'<td class="hastd_PDFdrawings" data-ivalue="'+currentValue.PDFdrawings+'">'+iPDFdrawingsStr+'</td>'+
						'<td class="hastd_Remark" title="'+currentValue.Remark+'">'+currentValue.Remark+'</td>'+
					'</tr>';
				}
			});
		}else{
			data.map(function(currentValue, index, arr){
				if(index > 0){
					var iNO = parseInt((CurrentPage-1)*10 + index);
					var iCADdrawingsStr = "";
					var iCADdrawings = currentValue.CADdrawings;
					var iCADdrawingsArr = iCADdrawings.split("::");
					iCADdrawingsArr = $.grep(iCADdrawingsArr,function(jcurrentValue,jindex){
						return jcurrentValue != "";
					});
					if(iCADdrawingsArr.length == 0){
						iCADdrawingsStr+="未上传";
					}else if(iCADdrawingsArr.length < 3){
						iCADdrawingsArr.map(function(currentValue, index, arr){
							if(index != iCADdrawingsArr.length){
								iCADdrawingsStr+="<span class='td_download_file' title='"+currentValue+"'>"+currentValue+"</span><br>";
							}else{
								iCADdrawingsStr+="<span class='td_download_file' title='"+currentValue+"'>"+currentValue+"</span>";
							}
						});
					}else{
						var iCADconStr = '<div class="container-fluid" style="padding-left: 1px;padding-right: 1px;padding-bottom:5px;">';
						iCADconStr+='<ol>';
						iCADdrawingsArr.map(function(icurrentValue, iindex, iarr){
							iCADconStr+='<li title="'+icurrentValue+'">'+icurrentValue+'</li>';
						});
						iCADconStr+='</ol>';
						iCADconStr+='</div>';

						iCADdrawingsStr+="<span class='td_download_file' title='"+iCADdrawingsArr[0]+"'>"+iCADdrawingsArr[0]+"</span><br>";
						iCADdrawingsStr+="<span class='td_download_file' title='"+iCADdrawingsArr[1]+"'>"+iCADdrawingsArr[1]+"</span><br>";
						iCADdrawingsStr+="<a tabindex='0' class='btn btn-info' role='button' data-toggle='popover' data-trigger='focus' data-placement='left' title='第"+iNO+"条记录--CAD图纸' data-content='"+iCADconStr+"'>显示更多...</a>";
					}

					var iPDFdrawingsStr = "";
					var iPDFdrawings = currentValue.PDFdrawings;
					var iPDFdrawingsArr = iPDFdrawings.split("::");
					iPDFdrawingsArr = $.grep(iPDFdrawingsArr,function(jcurrentValue,jindex){
						return jcurrentValue != "";
					});
					if(iPDFdrawingsArr.length == 0){
						iPDFdrawingsStr+="未上传";
					}else if(iPDFdrawingsArr.length < 3){
						iPDFdrawingsArr.map(function(currentValue, index, arr){
							if(index != iPDFdrawingsArr.length){
								iPDFdrawingsStr+="<span class='td_download_file' title='"+currentValue+"'>"+currentValue+"</span><br>";
							}else{
								iPDFdrawingsStr+="<span class='td_download_file' title='"+currentValue+"'>"+currentValue+"</span>";
							}
						});
					}else{
						var iPDFconStr = '<div class="container-fluid" style="padding-left: 1px;padding-right: 1px;padding-bottom:5px;">';
						iPDFconStr+='<ol>';
						iPDFdrawingsArr.map(function(icurrentValue, iindex, iarr){
							iPDFconStr+='<li title="'+icurrentValue+'">'+icurrentValue+'</li>';
						});
						iPDFconStr+='</ol>';
						iPDFconStr+='</div>';

						iPDFdrawingsStr+="<span class='td_download_file' title='"+iPDFdrawingsArr[0]+"'>"+iPDFdrawingsArr[0]+"</span><br>";
						iPDFdrawingsStr+="<span class='td_download_file' title='"+iPDFdrawingsArr[1]+"'>"+iPDFdrawingsArr[1]+"</span><br>";
						iPDFdrawingsStr+="<a tabindex='0' class='btn btn-info' role='button' data-toggle='popover' data-trigger='focus' data-placement='left' title='第"+iNO+"条记录--PDF图纸' data-content='"+iPDFconStr+"'>显示更多...</a>";
					}

					str+='<tr>'+
						'<td class="update_td" data-iid="'+currentValue.ID+'">'+parseInt((CurrentPage-1)*10 + index)+'</td>'+
						'<td class="hastd_ProductName" title="'+currentValue.ProductName+'">'+currentValue.ProductName+'</td>'+
						'<td class="hastd_ProductType" title="'+currentValue.ProductType+'">'+currentValue.ProductType+'</td>'+
						'<td class="hastd_Machine" title="'+currentValue.Machine+'">'+currentValue.Machine+'</td>'+
						'<td class="hastd_VersionNO" title="'+currentValue.VersionNO+'">'+currentValue.VersionNO+'</td>'+
						'<td class="hastd_DesignTime" title="'+currentValue.DesignTime+'">'+currentValue.DesignTime+'</td>'+
						'<td class="hastd_UseCondition" title="'+currentValue.UseCondition+'">'+currentValue.UseCondition+'</td>'+
						'<td class="hastd_CADdrawings" data-ivalue="'+currentValue.CADdrawings+'">'+iCADdrawingsStr+'</td>'+
						'<td class="hastd_PDFdrawings" data-ivalue="'+currentValue.PDFdrawings+'">'+iPDFdrawingsStr+'</td>'+
						'<td class="hastd_Remark" title="'+currentValue.Remark+'">'+currentValue.Remark+'</td>'+
					'</tr>';
				}
			});
		}
	}
	$("#"+Category+"_a").children("table").find("tbody").empty().append(str);
	var pageClassify;
	if(Category == "scheme"){
		pageClassify = "";
	}else if(Category == "standard"){
		pageClassify = "2";
	}else if(Category == "customization"){
		pageClassify = "3";
	}
	$(".g_body #currentPage"+pageClassify).text(CurrentPage);
	$(".g_body #allPage"+pageClassify).text(pageCount);
	pageStyle(CurrentPage, pageCount, pageClassify);
	$(".hastd_CADdrawings>a, .hastd_PDFdrawings>a").popover({
		title: "请预览",
		html: true
	});
}

// 翻页组件按钮逻辑
// flag 为按钮ID后缀  如 pageStyle(CurrentPage, pageCount, "2");
function pageStyle(currentPage, pageCounts, flag){
    flag = flag || "";
    if(pageCounts == 1){
        $("#fistPage"+flag+", #upPage"+flag+", #nextPage"+flag+", #lastPage"+flag+", #Gotojump"+flag).prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
    }else if(currentPage == 1){
        $("#fistPage"+flag+", #upPage"+flag).prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
        $("#lastPage"+flag+", #nextPage"+flag+", #Gotojump"+flag).prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }else if(currentPage == pageCounts){
        $("#lastPage"+flag+", #nextPage"+flag).prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
        $("#fistPage"+flag+", #upPage"+flag+", #Gotojump"+flag).prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }else{
        $("#lastPage"+flag+", #nextPage"+flag+", #fistPage"+flag+", #upPage"+flag+", #Gotojump"+flag).prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }
}

// 翻页获取数据
function getDataByGoPage(currentPage){
	var Column1, Content1, Column2, Content2;
	if(!ApplicationGalleryState.hasSearch){
		Column1 = undefined;
		Content1 = undefined;
		Column2 = undefined;
		Content2 = undefined;
	}else{
		Column1 = ApplicationGalleryState.searchColumn1;
		Content1 = ApplicationGalleryState.searchContent1;
		Column2 = ApplicationGalleryState.searchColumn2;
		Content2 = ApplicationGalleryState.searchContent2;
	}
	var Category = $('.tab_wrapper>ul>li.active').children("a").data("idiagram");
	var QueryType = ApplicationGalleryState.searchQueryType;
	getDataByPage(Category, QueryType, Column1, Content1, Column2, Content2, currentPage);
}

// 搜索字段填充
function searchParamInsert(){
	var search = $('.tab_wrapper>ul>li.active').children("a").data("idiagram");
	var searchParamArr = search_all_param[search];
	var str = '';
	searchParamArr.map(function(currentValue, index, arr){
		str+='<li title="'+currentValue+'">'+currentValue+'</li>';
	});
	$(".search_in ul.dropdown-menu").empty().append(str);
}

// 搜索组件Init
function searchInit(){
	$('.tab_wrapper>ul>li:not(:eq(0))').removeClass("active");
	$('.tab_wrapper>ul>li:eq(0)').addClass("active");
	ApplicationGalleryState.hasSearch = false;
	ApplicationGalleryState.searchQueryType = undefined;
	ApplicationGalleryState.searchColumn1 = undefined;
	ApplicationGalleryState.searchContent1 = undefined;
	ApplicationGalleryState.searchColumn2 = undefined;
	ApplicationGalleryState.searchContent2 = undefined;
	searchParamInsert();
	$("input[name='querytype'][value='singleSelect']").prop("checked","checked");
	$("input[name='querytype'][value='singleSelect']").parent().addClass("active").css("color","#333");
	$("input[name='querytype'][value='mixSelect']").prop("checked",false);
	$("input[name='querytype'][value='mixSelect']").parent().removeClass("active").css("color","#fff");
	$(".mixSelect_search").fadeOut(50,function(){
		$(".singleSelect_search input[type='date'], .singleSelect_search select").fadeOut(50);
	});
	$(".m_button div.input-group-btn>button:eq(0)").attr("title", "选择字段").text("选择字段");
	$(".singleSelect_search input[type='text'], .singleSelect_search input[type='date'], .singleSelect_search select, .mixSelect_search input[type='text'], .mixSelect_search input[type='date'], .mixSelect_search select").val("");
}

//上传文件
function uploadFiles(iThat, ul_wrapperDOM, fileType){                                               
    var formData = new FormData();
    formData.enctype="multipart/form-data";
    formData.append("Folder","ApplicationGallery");
    // formData.append("ID",ID);
    var fileList, successType;
    if(fileType == "CADdrawings"){
    	fileList = ApplicationGalleryState.uploadFileListCAD;
    	successType = "CAD";
    }else if(fileType == "PDFdrawings"){
    	fileList = ApplicationGalleryState.uploadFileListPDF;
    	successType = "PDF";
    }
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
                    ul_wrapperDOM.find("div.progress-bar").attr("aria-valuenow",newWidth).css("width",percent).text(percent);
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
        		var successNO = 0;
        		data.map(function(currentValue, index, arr){
        			if(currentValue.Message.indexOf("成功")>-1){
        				LiStr+='<li class="list-group-item list-group-item-success" title="'+currentValue.FileName+'" data-iitype="'+successType+'"><span class="badge">成功</span>'+currentValue.FileName+'</li>';
        				successNO++;
        			}else if(currentValue.Message.indexOf("失败")>-1){
        				LiStr+='<li class="list-group-item list-group-item-danger" title="'+currentValue.FileName+'"><span class="badge">失败</span>'+currentValue.FileName+'</li>';
        			}else{
        				LiStr+='<li class="list-group-item list-group-item-warning" title="'+currentValue.FileName+'"><span class="badge">文件已存在</span>'+currentValue.FileName+'</li>';
        			}
        		});
        		ul_wrapperDOM.find("li>span:contains('删除')").parent().remove();
        		ul_wrapperDOM.find("ul").append(LiStr);
        		// 上传成功数占比
        		// var successFloat = (successNO/(data.length)).toFixed(3);
        		// var successValueNow = successFloat*100;
        		// var successPercent = successValueNow+"%";
        		// $("div."+classify+"_fileList_info>div:nth-child(2)>div.progress-bar").attr("aria-valuenow",successValueNow).css("width",successPercent).text(successPercent);
        		if(fileType == "CADdrawings"){
        			ApplicationGalleryState.uploadFileNoCAD = 0;
        			for(var kk in ApplicationGalleryState.uploadFileListCAD){
        				delete ApplicationGalleryState.uploadFileListCAD[kk];
        			}
        		}else if(fileType == "PDFdrawings"){
        			ApplicationGalleryState.uploadFileNoPDF = 0;
        			for(var kkk in ApplicationGalleryState.uploadFileListPDF){
        				delete ApplicationGalleryState.uploadFileListPDF[kkk];
        			}
        		}
        		ul_wrapperDOM.prev().children("input[type='file']").val("");
        	}else if(data.length == 0){
        		$.MsgBox_Unload.Alert("上传提示","文件读取至服务器失败！");
        	}
        },
        error: function(){
        	ul_wrapperDOM.find("div.progress-bar").prop("aria-valuenow","0").css("width","0%").text("0%");
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

/*page onload*/
searchInit();
getDataByPage("scheme", undefined, undefined, undefined, undefined, undefined, 1);

// // 获取员工列表
// var iIndex = 0;
// var myFunc = function(data){
//     if(data.length > 1){
//         data.map(function(currentValue,index,arr){
//             if(index > 0){
//                 BiddingDocDepartStaff.push(currentValue.StaffName);
//             }
//         });
//     }
//     iIndex++;
//     if(iIndex < BiddingDocDepart.length){
//         $.when(
//             $.ajax({
//                 type: 'GET',
//                 url: 'GetStaffInfo',
//                 data: {
//                     Type: "common",
//                     Department: BiddingDocDepart[iIndex]
//                 },
//                 dataType: "json"
//             })
//             ).done(myFunc);
//     }else if(iIndex == BiddingDocDepart.length){
//         console.log(BiddingDocDepartStaff);
//         var staffStr = '<option value="" disabled>请选择</option>';
//         BiddingDocDepartStaff.map(function(currentValue, index, arr){
//         	staffStr+='<option value="'+currentValue+'">'+currentValue+'</option>';
//         });
//         $("select[name='update_info_Submitter']").empty().append(staffStr);
//     }
// };
// $.when(
//     $.ajax({
//         type: 'GET',
//         url: 'GetStaffInfo',
//         data: {
//             Type: "common",
//             Department: BiddingDocDepart[iIndex]
//         },
//         dataType: "json"
//     })
//     ).then(myFunc, function(){
//     $.MsgBox_Unload.Alert("获取员工姓名提示","初始化失败！");
// });


/*event handler*/
// 方案图 标准产品图 定制产品图 切换
$('.tab_wrapper>ul>li>a').click(function(e) {
  e.preventDefault();
  $(this).tab('show');
});
$('.tab_wrapper>ul>li>a').on('shown.bs.tab', function (e) {
    console.log(e.target); // newly activated tab
    console.log(e.relatedTarget); // previous active tab
    console.log($(e.target));
    ApplicationGalleryState.hasSearch = false;
    ApplicationGalleryState.searchQueryType = undefined;
    ApplicationGalleryState.searchColumn1 = undefined;
    ApplicationGalleryState.searchContent1 = undefined;
    ApplicationGalleryState.searchColumn2 = undefined;
    ApplicationGalleryState.searchContent2 = undefined;
    searchParamInsert();
    var Category = $(e.target).data("idiagram");
    getDataByPage(Category, undefined, undefined, undefined, undefined, undefined, 1);
});

// 添加打开
$(".m_button>.row>div:nth-child(1)>span").click(function(){	
	var Category = $('.tab_wrapper>ul>li.active').children("a").data("idiagram");
	var wrapper_div = $("div.add_wrapper_"+Category);
	if(!ApplicationGalleryState.hasLoadAddUpdateSection){
		wrapper_div.load('html/modules/serviced/ApplicationGallery.html #add_section_'+Category, function(response,status,xhr){
			// console.log(response);
			// console.log(status); // success
			// console.log(xhr);
			if(status == "success"){
				ApplicationGalleryState.hasLoadAddUpdateSection = true;
				ApplicationGalleryState.hasLoadAddUpdatePageData = response;
				console.log(typeof ApplicationGalleryState.hasLoadAddUpdatePageData);
				$(".bg_cover").slideDown(250);
				wrapper_div.slideDown(250);
				wrapper_div.find(".add_section_all_body_in select, .add_section_all_body_in input").val("");
			}else{
				$.MsgBox_Unload.Alert("提示","网络繁忙！读取添加模板失败！");
			}
		});
	}else{
		// 已经请求成功过
		$(".bg_cover").slideDown(250);
		wrapper_div.slideDown(250);
		var insertDOM = $(ApplicationGalleryState.hasLoadAddUpdatePageData);
		insertDOM.each(function(i, el){
			if($(el).is("#add_section_"+Category)){
				wrapper_div.empty().append($(el));
				return false;
			}
		});
		wrapper_div.find(".add_section_all_body_in select, .add_section_all_body_in input").val("");
	}
});

// 添加修改关闭
$(document).on("click","[class$='_section_all_foot_in']>input[value='取消'], [class$='_section_all_tit_r']",function(e){
	e.stopPropagation();
	var parentsDOM = $(this).parents(".ApplicationGallery_post");
	var wrapper_div = parentsDOM.parent();
	$(".bg_cover").slideUp(250);
	wrapper_div.slideUp(250);
	// 初始化
	var itype = parentsDOM.data("itype");
	var icategory = parentsDOM.data("icategory");
	if($('div.'+itype+'_fileList_info').is(':visible')){
		$('div.'+itype+'_fileList_info').slideUp(150, function(){
			$("div."+itype+"_fileList_info>div>div.progress-bar").attr("aria-valuenow","0").css("width","0%").text("0%");
		});
	}
	$("#"+icategory+"_"+itype+"_CADdrawings_list").empty();
	$("#"+icategory+"_"+itype+"_PDFdrawings_list").empty();
	ApplicationGalleryState.uploadFileNoCAD = 0;
	for(var k in ApplicationGalleryState.uploadFileListCAD){
		delete ApplicationGalleryState.uploadFileListCAD[k];
	}
	ApplicationGalleryState.uploadFileNoPDF = 0;
	for(var kk in ApplicationGalleryState.uploadFileListPDF){
		delete ApplicationGalleryState.uploadFileListPDF[kk];
	}
	parentsDOM.find("input[type='file']").val("");
	ApplicationGalleryState.curUpdateID = undefined;
});

// 文件上传的trigger
$(document).on("click",".trigger_click",function(e){
	e.preventDefault();
	$(this).next().trigger("click");
});

// 点击浏览切换文件
$(document).on("change",".ApplicationGallery_post input[type='file']",function(){
	var parentsDOM = $(this).parents(".ApplicationGallery_post");
	var itype = parentsDOM.data("itype");
	var icategory = parentsDOM.data("icategory");
	var ul_wrapperDOM = $(this).parent().next();
	var fileType = $(this).data("ifiletype");
	if(!(ul_wrapperDOM.find('div.'+itype+'_fileList_info').is(':visible'))){
		ul_wrapperDOM.find('div.'+itype+'_fileList_info').slideDown(150);
	}
	// console.log($(this));
	// console.log($(this)[0]);
	// console.log($(this)[0].files);
	// 
	var curFileList = $(this)[0].files;
	var curFileListStr = '';
	$.each(curFileList, function(iname, ivalue){
		if(fileType == "CADdrawings"){
			ApplicationGalleryState.uploadFileNoCAD++;
			curFileListStr+='<li class="list-group-item" title="'+ivalue.name+'" value="'+ApplicationGalleryState.uploadFileNoCAD+'" data-iitype="CAD"><span class="badge">删除</span>'+ivalue.name+'</li>';
			ApplicationGalleryState.uploadFileListCAD[ApplicationGalleryState.uploadFileNoCAD] = ivalue;
		}else if(fileType == "PDFdrawings"){
			ApplicationGalleryState.uploadFileNoPDF++;
			curFileListStr+='<li class="list-group-item" title="'+ivalue.name+'" value="'+ApplicationGalleryState.uploadFileNoPDF+'" data-iitype="PDF"><span class="badge">删除</span>'+ivalue.name+'</li>';
			ApplicationGalleryState.uploadFileListPDF[ApplicationGalleryState.uploadFileNoPDF] = ivalue;
		}
	});
	ul_wrapperDOM.find("ul").append(curFileListStr);
	ul_wrapperDOM.children("div").find("div.progress-bar").prop("aria-valuenow","0").css("width","0%").text("0%");
});

// 文件删除
$(document).on("click","ul>li>span:contains('删除')",function(){
	var iitype = $(this).parent().data("iitype");
	var iValue = $(this).parent().attr("value");
	if(iitype == "CAD"){
		delete ApplicationGalleryState.uploadFileListCAD[iValue];
	}else if(iitype == "PDF"){
		delete ApplicationGalleryState.uploadFileListPDF[iValue];
	}
	$(this).parent().remove();
});

// 添加修改提交
$(document).on("click","div[class$='_section_all_foot_in']>[id^='submit']",function(){
	var iThat = $(this);
	var wrapperDOM = $(this).parents(".ApplicationGallery_post");
	var Category = wrapperDOM.data("icategory");
	var Type = wrapperDOM.data("itype");
	var ID;
	var cnType;
	if(Type == "add"){
		ID = "0";
		cnType = "添加";
	}else{
		ID = ApplicationGalleryState.curUpdateID;
		cnType = "修改";
	}
	if(Category == "scheme"){
		var ApplicationType = wrapperDOM.find("#"+Type+"_scheme_ApplicationType").val().trim();
		var CustomerName = wrapperDOM.find("#"+Type+"_scheme_CustomerName").val().trim();
		var SchemeName = wrapperDOM.find("#"+Type+"_scheme_SchemeName").val().trim();
		var MachineModel = wrapperDOM.find("#"+Type+"_scheme_MachineModel").val().trim();
		var MakeTime = wrapperDOM.find("#"+Type+"_scheme_MakeTime").val().trim();
		var Remark = wrapperDOM.find("#"+Type+"_scheme_Remark").val().trim();

		if(ApplicationType === ""){
			$.MsgBox_Unload.Alert("提示","请填写应用类型！");
			return false;
		}

		var fileArray = [];
		wrapperDOM.find("#scheme_"+Type+"_CADdrawings_list").find("li.list-group-item-success, li.list-group-item-info").each(function(){
			fileArray.push($(this).attr("title"));
		});
		var newfileArray = globalArrStrUnique(fileArray);
		var CADdrawings = newfileArray.length == 0 ? "" : (newfileArray.join("::")+"::");

		var fileArray2 = [];
		wrapperDOM.find("#scheme_"+Type+"_PDFdrawings_list").find("li.list-group-item-success, li.list-group-item-info").each(function(){
			fileArray2.push($(this).attr("title"));
		});
		var newfileArray2 = globalArrStrUnique(fileArray2);
		var PDFdrawings = newfileArray2.length == 0 ? "" : (newfileArray2.join("::")+"::");

		$.ajax({
			type: "POST",
			url: "ApplicationGallery",
			data: {
				Category: Category,
				Type: Type,
				ID: ID,
				ApplicationType: ApplicationType,
				CustomerName: CustomerName,
				SchemeName: SchemeName,
				MachineModel: MachineModel,
				MakeTime: MakeTime,
				CADdrawings: CADdrawings,
				PDFdrawings: PDFdrawings,
				Remark: Remark
			},
			dataType: "json"
		}).then(function(data){
			if(data == true){
				wrapperDOM.find("[class$='_section_all_tit_r']").trigger("click");
				$.MsgBox_Unload.Alert("提示", cnType+"成功");
				getDataByPage(Category, undefined, undefined, undefined, undefined, undefined, 1);
			}else if(data == false){
				$.MsgBox_Unload.Alert("提示", cnType+"失败");
			}else{
				$.MsgBox_Unload.Alert("提示", data);
			}
		},function(){
			$.MsgBox_Unload.Alert("提示", "网络繁忙！");
		}).always(function(){

		});
	}else{
		// 后2个的提交
		var ProductName = wrapperDOM.find("#"+Type+"_"+Category+"_ProductName").val().trim();
		var ProductType = wrapperDOM.find("#"+Type+"_"+Category+"_ProductType").val().trim();
		var Machine = wrapperDOM.find("#"+Type+"_"+Category+"_Machine").val().trim();
		var VersionNO = wrapperDOM.find("#"+Type+"_"+Category+"_VersionNO").val().trim();
		var DesignTime = wrapperDOM.find("#"+Type+"_"+Category+"_DesignTime").val().trim();
		var UseCondition = wrapperDOM.find("#"+Type+"_"+Category+"_UseCondition").val();
		var Remark2 = wrapperDOM.find("#"+Type+"_"+Category+"_Remark").val().trim();

		if(ProductName === ""){
			$.MsgBox_Unload.Alert("提示","请填写产品名称！");
			return false;
		}

		if(!UseCondition){
			$.MsgBox_Unload.Alert("提示","请选择使用情况！");
			return false;
		}

		var fileArray3 = [];
		wrapperDOM.find("#"+Category+"_"+Type+"_CADdrawings_list").find("li.list-group-item-success, li.list-group-item-info").each(function(){
			fileArray3.push($(this).attr("title"));
		});
		var newfileArray3 = globalArrStrUnique(fileArray3);
		var CADdrawings2 = newfileArray3.length == 0 ? "" : (newfileArray3.join("::")+"::");

		var fileArray4 = [];
		wrapperDOM.find("#"+Category+"_"+Type+"_PDFdrawings_list").find("li.list-group-item-success, li.list-group-item-info").each(function(){
			fileArray4.push($(this).attr("title"));
		});
		var newfileArray4 = globalArrStrUnique(fileArray4);
		var PDFdrawings2 = newfileArray4.length == 0 ? "" : (newfileArray4.join("::")+"::");

		$.ajax({
			type: "POST",
			url: "ApplicationGallery",
			data: {
				Category: Category,
				Type: Type,
				ID: ID,
				ProductName: ProductName,
				ProductType: ProductType,
				Machine: Machine,
				VersionNO: VersionNO,
				DesignTime: DesignTime,
				Usage: UseCondition,
				CADdrawings: CADdrawings2,
				PDFdrawings: PDFdrawings2,
				Remark: Remark2
			},
			dataType: "json"
		}).then(function(data){
			if(data == true){
				wrapperDOM.find("[class$='_section_all_tit_r']").trigger("click");
				$.MsgBox_Unload.Alert("提示", cnType+"成功");
				getDataByPage(Category, undefined, undefined, undefined, undefined, undefined, 1);
			}else if(data == false){
				$.MsgBox_Unload.Alert("提示", cnType+"失败");
			}else{
				$.MsgBox_Unload.Alert("提示", data);
			}
		},function(){
			$.MsgBox_Unload.Alert("提示", "网络繁忙！");
		}).always(function(){

		});
	}
});

// 添加修改文件上传
$(document).on("click",".ApplicationGallery_post input[type='file']+label>button",function(){
	var iThat = $(this);
	// var parentsDOM = $(this).parents(".ApplicationGallery_post");
	// var itype = parentsDOM.data("itype");
	// var icategory = parentsDOM.data("icategory");
	var ul_wrapperDOM = $(this).parent().parent().next();
	var fileType = $(this).parent().prev().data("ifiletype");
	if(fileType == "CADdrawings"){
		if(Object.keys(ApplicationGalleryState.uploadFileListCAD).length == 0){
			$.MsgBox_Unload.Alert("添加提示","请选择文件！");
			return false;
		}
	}else if(fileType == "PDFdrawings"){
		if(Object.keys(ApplicationGalleryState.uploadFileListPDF).length == 0){
			$.MsgBox_Unload.Alert("添加提示","请选择文件！");
			return false;
		}
	}
	uploadFiles(iThat, ul_wrapperDOM, fileType);
});

// 修改打开
$(document).on("click",".update_td",function(){
	var Category = $('.tab_wrapper>ul>li.active').children("a").data("idiagram");
	var wrapper_div = $("div.update_wrapper_"+Category);
	var iid = $(this).data("iid").toString();
	var tr = $(this).parent();
	if(!ApplicationGalleryState.hasLoadAddUpdateSection){
		wrapper_div.load('html/modules/serviced/ApplicationGallery.html #update_section_'+Category, function(response,status,xhr){
			// console.log(response);
			// console.log(status); // success
			// console.log(xhr);
			if(status == "success"){
				ApplicationGalleryState.hasLoadAddUpdateSection = true;
				ApplicationGalleryState.hasLoadAddUpdatePageData = response;
				console.log(typeof ApplicationGalleryState.hasLoadAddUpdatePageData);
				$(".bg_cover").slideDown(250);
				wrapper_div.slideDown(250);
				// 填充内容
				ApplicationGalleryState.curUpdateID = iid;
				wrapper_div.find("fieldset .form-control").each(function(){
					var subClassName = "hastd_"+$(this).attr("id").split("_")[2];
					var oldVal = tr.children("."+subClassName).attr("title");
					var newVal = globalDataHandle(oldVal, "");
					$(this).val(newVal);
				});

				var iCADdrawings = tr.find(".hastd_CADdrawings").data("ivalue").toString();
				var iCADdrawingsArr = iCADdrawings.split("::");
				iCADdrawingsArr = $.grep(iCADdrawingsArr,function(currentValue,index){
				    return currentValue != "";
				});
				var iCADdrawingsStr = '';
				iCADdrawingsArr.map(function(currentValue, index, arr){
					iCADdrawingsStr+='<li class="list-group-item list-group-item-info" title="'+currentValue+'"><span class="badge">已上传</span>'+currentValue+'</li>';
				});
				$("#"+Category+"_update_CADdrawings_list").empty().append(iCADdrawingsStr);

				var iPDFdrawings = tr.find(".hastd_PDFdrawings").data("ivalue").toString();
				var iPDFdrawingsArr = iPDFdrawings.split("::");
				iPDFdrawingsArr = $.grep(iPDFdrawingsArr,function(currentValue,index){
				    return currentValue != "";
				});
				var iPDFdrawingsStr = '';
				iPDFdrawingsArr.map(function(currentValue, index, arr){
					iPDFdrawingsStr+='<li class="list-group-item list-group-item-info" title="'+currentValue+'"><span class="badge">已上传</span>'+currentValue+'</li>';
				});
				$("#"+Category+"_update_PDFdrawings_list").empty().append(iPDFdrawingsStr);

			}else{
				$.MsgBox_Unload.Alert("提示","网络繁忙！读取修改模板失败！");
			}
		});
	}else{
		// 已经请求成功过
		$(".bg_cover").slideDown(250);
		wrapper_div.slideDown(250);
		var insertDOM = $(ApplicationGalleryState.hasLoadAddUpdatePageData);
		insertDOM.each(function(i, el){
			if($(el).is("#update_section_"+Category)){
				wrapper_div.empty().append($(el));
				return false;
			}
		});
		// 填充内容
		ApplicationGalleryState.curUpdateID = iid;
		wrapper_div.find("fieldset .form-control").each(function(){
			var subClassName = "hastd_"+$(this).attr("id").split("_")[2];
			var oldVal = tr.children("."+subClassName).attr("title");
			var newVal = globalDataHandle(oldVal, "");
			$(this).val(newVal);
		});

		var iCADdrawings = tr.find(".hastd_CADdrawings").data("ivalue").toString();
		var iCADdrawingsArr = iCADdrawings.split("::");
		iCADdrawingsArr = $.grep(iCADdrawingsArr,function(currentValue,index){
		    return currentValue != "";
		});
		var iCADdrawingsStr = '';
		iCADdrawingsArr.map(function(currentValue, index, arr){
			iCADdrawingsStr+='<li class="list-group-item list-group-item-info" title="'+currentValue+'"><span class="badge">已上传</span>'+currentValue+'</li>';
		});
		$("#"+Category+"_update_CADdrawings_list").empty().append(iCADdrawingsStr);

		var iPDFdrawings = tr.find(".hastd_PDFdrawings").data("ivalue").toString();
		var iPDFdrawingsArr = iPDFdrawings.split("::");
		iPDFdrawingsArr = $.grep(iPDFdrawingsArr,function(currentValue,index){
		    return currentValue != "";
		});
		var iPDFdrawingsStr = '';
		iPDFdrawingsArr.map(function(currentValue, index, arr){
			iPDFdrawingsStr+='<li class="list-group-item list-group-item-info" title="'+currentValue+'"><span class="badge">已上传</span>'+currentValue+'</li>';
		});
		$("#"+Category+"_update_PDFdrawings_list").empty().append(iPDFdrawingsStr);

	}
});

// 单选和多选切换
$(".search_in>div:eq(0) label").on("click",function(){
    if($(this).children().val()=="singleSelect"){
        $(this).css("color","#333");
        $(this).siblings("label").css("color","#fff");
        $(".mixSelect_search").fadeOut(100);
    }else{
        $(this).css("color","#333");
        $(this).siblings("label").css("color","#fff");
        $(".mixSelect_search").fadeIn(100);
    }
});

// 搜索字段点击
$(document).on("click",".search_in ul.dropdown-menu>li",function(){
    var iText = $(this).text();
    var iTitle = $(this).attr("title");
    $(this).parent().siblings("button:nth-child(1)").attr("title",iTitle).text(iText);
    var doubleParentDOM = $(this).parent().parent();
    if(iText == "制定时间" || iText == "设计时间"){
    	doubleParentDOM.siblings("input[type='text'], select").hide(10,function(){
    		doubleParentDOM.siblings("input[type='date']").fadeIn(50).val("");
    	});
    }else if(iText == "使用情况"){
        doubleParentDOM.siblings("input[type='text'], input[type='date']").hide(10,function(){
        	doubleParentDOM.siblings("select").fadeIn(50).val("");
        });
    }else if(iText == "选择字段"){
    	doubleParentDOM.siblings("input[type='text'], input[type='date'], select").hide(10);
    }else{
    	doubleParentDOM.siblings("select, input[type='date']").hide(10,function(){
    		doubleParentDOM.siblings("input[type='text']").fadeIn(50).val("");
    	});
    }
});

// 搜索
$(".classify_search_y").click(function(){
	var QueryType = $(".search_in input[name='querytype']:checked").val();
	var Column1, Content1, Column2, Content2;
	if(!QueryType || QueryType=="on"){
		$.MsgBox_Unload.Alert("提示","请选择搜索类别");
		return false;
	}else if(QueryType=="singleSelect"){
		Column1 = $(".singleSelect_search div.input-group-btn>button:eq(0)").text();
		if(!Column1 || Column1=="选择字段"){
			$.MsgBox_Unload.Alert("提示","请选择搜索字段");
			return false;
		}else if(Column1 == "使用情况"){
			Content1 = $(".singleSelect_search>div select").val();
		}else if(Column1 == "制定时间" || Column1 == "设计时间"){
			Content1 = $(".singleSelect_search>div input[type='date']").val();
		}else{
			Content1 = $(".singleSelect_search>div input[type='text']").val().trim();
		}
		if(!Content1){
			$.MsgBox_Unload.Alert("提示","搜索内容必填或必选");
			return false;
		}
	}else if(QueryType=="mixSelect"){
		Column1 = $(".singleSelect_search div.input-group-btn>button:eq(0)").text();
		if(!Column1 || Column1=="选择字段"){
			$.MsgBox_Unload.Alert("提示","请选择搜索字段");
			return false;
		}else if(Column1 == "使用情况"){
			Content1 = $(".singleSelect_search>div select").val();
		}else if(Column1 == "制定时间" || Column1 == "设计时间"){
			Content1 = $(".singleSelect_search>div input[type='date']").val();
		}else{
			Content1 = $(".singleSelect_search>div input[type='text']").val().trim();
		}
		if(!Content1){
			$.MsgBox_Unload.Alert("提示","搜索内容必填或必选");
			return false;
		}

		Column2 = $(".mixSelect_search div.input-group-btn>button:eq(0)").text();
		if(!Column2 || Column2=="选择字段"){
			$.MsgBox_Unload.Alert("提示","请选择搜索字段");
			return false;
		}else if(Column2 == "使用情况"){
			Content2 = $(".mixSelect_search>div select").val();
		}else if(Column2 == "制定时间" || Column2 == "设计时间"){
			Content2 = $(".mixSelect_search>div input[type='date']").val();
		}else{
			Content2 = $(".mixSelect_search>div input[type='text']").val().trim();
		}
		if(!Content2){
			$.MsgBox_Unload.Alert("提示","搜索内容必填或必选");
			return false;
		}
	}
	var Category = $('.tab_wrapper>ul>li.active').children("a").data("idiagram");
	getDataByPage(Category, QueryType, Column1, Content1, Column2, Content2, 1);
});

// 取消搜索
$(".classify_search_n").click(function(){
	getDataByPage("scheme", undefined, undefined, undefined, undefined, undefined, 1);
	searchInit();
	$("[id^='jumpNumber']").val("");
});

// 翻页功能
$("[id^='jumpNumber']").on("input propertychange",function(){
    var newVal = $(this).val().replace(/[^\d]/g,'');
    $(this).val(newVal);
});

	// 翻页
$("[id^='fistPage']").click(function(){
    var currentPage =1;
    getDataByGoPage(currentPage);
});

$("[id^='lastPage']").click(function(){
    var currentPage = Number($(this).parent().prev().find("[id^='allPage']").text());
    getDataByGoPage(currentPage);
});

$("[id^='upPage']").click(function(){
    var currentPage = Number($(this).parent().prev().find("[id^='currentPage']").text());
    if(currentPage == 1){
        return;
    }else{
        currentPage--;
        getDataByGoPage(currentPage);
    }
});

$("[id^='nextPage']").click(function(){
    var currentPage = Number($(this).parent().prev().find("[id^='currentPage']").text());
    var pageCounts = Number($(this).parent().prev().find("[id^='allPage']").text());
    if(currentPage == pageCounts){
        return;
    }else{
        currentPage++;
        getDataByGoPage(currentPage);
    }
});
	//跳页
$("[id^='Gotojump']").click(function(){
    var currentPage = $(this).siblings("input[id^='jumpNumber']").val().trim();
    var pageCounts = Number($(this).parent().prev().find("[id^='allPage']").text());
    var oldCurrentPage = Number($(this).parent().prev().find("[id^='currentPage']").text());
    if(currentPage == oldCurrentPage || currentPage <= 0 || currentPage>pageCounts){
        $(this).siblings("input[id^='jumpNumber']").val("");
        return;
    }else{
        getDataByGoPage(currentPage);
    }
});

// 下载
$(document).on("click","span.td_download_file, li.list-group-item-success, li.list-group-item-info, ol>li",function(){
	var iPath = $(this).attr("title");
	if(!iPath || iPath == "--"){
		$.MsgBox_Unload.Alert("下载提示","无数据或文件已被删除！");
		return false;
	}
	$.ajax({
		type: "GET",
		url: "FileDownload",
		data: {
			Folder: "ApplicationGallery",
			FileName: iPath
		}
	}).then(function(data){
		window.open(globalHostName+"/"+data);
	},function(){
		$.MsgBox_Unload.Alert("提示","服务器繁忙！");
	});
	// if(iPath.indexOf("E:")==0){
	// 	iPath = iPath.replace(/\\/g,"/").substring(2);
	// 	console.log(iPath);
	// }
});

// 评分验证 change keydown keyup
// $("input[name='update_info_Score']").on("input propertychange",function(e){
// 	e.stopPropagation();
// 	var iValue = $(this).val();
// 	if(iValue == ""){
// 		return false;
// 	}
// 	if(!reg0To120.test(iValue)){
// 		$(this).val("0");
// 	}else{
// 		$(this).val(iValue);
// 	}
// });