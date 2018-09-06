// windows图片
var windowsPng = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAD0AAAA9CAYAAAAeYmHpAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyNpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMTQwIDc5LjE2MDQ1MSwgMjAxNy8wNS8wNi0wMTowODoyMSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIChNYWNpbnRvc2gpIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOjI2QkQ5RTY1QTI5OTExRThBMUMzOEYyM0YxMTcyOTdDIiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOjI2QkQ5RTY2QTI5OTExRThBMUMzOEYyM0YxMTcyOTdDIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6MjZCRDlFNjNBMjk5MTFFOEExQzM4RjIzRjExNzI5N0MiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6MjZCRDlFNjRBMjk5MTFFOEExQzM4RjIzRjExNzI5N0MiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz6pnN9NAAACWElEQVR42uybvUtbURiH741JQJBWqrhYsbMFtVgcRNBJRHFwS3WoDXTprot/hJMFKR1cbJAkToqTky4W8QOkDRRapIjEFpqKVTAaf299A4dLRZMmgXvye+HJe+/JMZfnfHEuHF1HI7KQDSC9ABPgGWhw/B/HYAu8B4nYWDAnha4KP5JC0O/YGyuiCvETV3t4DfQZFX6B7+DSx5Jh0ALqjLJlMBLEx0tD+Dd4DeJokSu/dy06NIQUBTOgFgyDUZF+ZdSLQjZhy3iGywXSHORlxL7LO8rQfm5M+qSl83kenOp1V0C7XeIwv7rZFtrjP/S2KeBUXwSqUdqhNKUpTWlKU5rSlKY0pSlNaUpTmtKUpjSlKU1pSlOa0pSmtC8iaFx3RBayOfZ0FfT0OUhZ7NoGQl7pVGws2GmrMabuN6RWr3QlHvwA6QN4ahTLOa9xNPh5JYf3mXNzBKOxAs/rBUOeMmn9LrBRxsaWtateb9Nys6M3zfiyv8zSm2AXZAykbK/Mzx0BD/V6Xw7PvcHFrBYcglEMtU2L5rKckYsbIzkq0mFt7Q6jrrT8gePvE4MydZ941o+PoCd/NvQx0qqngm2xLesJRvHR380JLuQcaDeYBl8sk/0MpqSHRVgK3FvmQV0Rq/lXzZ/+sUL/T6zLIgtOQHuBf5uG6B9voVvCBSO/b98t5SbH2FRk8Lv13HtTmtKUpjSlKU1p68K9Yzf0Fmnwno3TqvlCX1FLFfIyVANy+uZ3V8ibYRK7t8mCpfW/8n76uENDEM8W09OLSAM+k5URsQTh6G0VrgUYAMr2lcOfV7YEAAAAAElFTkSuQmCC";

var timeLineDataObj = {};

var projectParamNameMap = {
	"futureC_T1": "futureC T1",
	"futureD_T1": "futureD T1",
	"cfChicken_T1": "冲锋鹅 T1",
	"cfChicken_T2": "冲锋鹅 T2",
	"EUCP_T1": "EUCP T1"
};

var sortFlag = false;
var vmHasInit = false;
//选时间blur
$(".MailBar_All .upyear").blur(function(){
	var Year  = $(".MailBar_All .upyear option:selected").val();
	$("#All_Send3").val(Year);
});

// 上传完成后自动刷新
function autoRefrush(){
	$("#jumpNumber").text("");
	$(".tsearch").val("");
	if($("#currentTab").data("iidiagram").toString() == "Norms"){  //开发规范
 	    $.ajax({
 	        type: 'GET',
 	        url: 'Software',
 	        data: {
 	        	Type :"Specification",
 	        	currentPage : 1,
 	        	queryType :"common"
 	        },
 	        dataType: "json",
 	        success: function (data) {
 	        	var str = '';
 	        	if( data.datas.length> 1){
 	        		for(var i = 1; i < data.datas.length;i++){
 	        			str += 
 	 	        			'<tr>'+
 								'<td class="Serial" value="'+data.datas[i].ID+'">'+i+'</td>'+
 								'<td><span class="glyphicon glyphicon-file" aria-hidden="true" value="'+data.datas[i].ID+'"></span></td>'+
 								'<td class="ManualWest">'+data.datas[i].FileName+'</td>'+       
 								'<td><span class="glyphicon glyphicon-save export" aria-hidden="true"></span></td>'+
 							'</tr>';
	 	        	}
 	        	}
 	        	$(".pageInfo #currentPage").text(data.currentPage);
 	        	$(".pageInfo #allPage").text(data.pageCounts);
 	        	$("#table1 tbody").empty().append(str);
 	        	jumpDisable();
 	        },
 	        error : function () {
 	        	$.MsgBox_Unload.Alert("提示", "服务器繁忙！");
 	        }
 	    });
	}
	else if($("#currentTab").data("iidiagram").toString() == "CommonProblem"){  //问题
		$.ajax({
 	        type : 'get',
 	        url : 'Software',
 	       data:{
	        	Type :"CommonProblem",
	        	currentPage : 1,
	        	queryType :"common"
	        },
	        dataType:"json",
 	        success : function (data) {
 	        	var str = '';
 	          	if( data.datas.length> 1){
 	        		for(var i = 1; i < data.datas.length;i++){
 	        			var ProblemCont_name = data.datas[i].FileName.split("-")[0];
 	        			var ProblemCont_Author = data.datas[i].FileName.split("-")[2].split(".")[0];
 	        			var DocuType = data.datas[i].DocuType;
 	        			str += 
 	        			'<tr style="width:97%;padding:0 1.5%;">'+
							'<td >'+
								'<p  style="cursor:pointer;" title="排序" class="ProblemNum Serial" value="'+data.datas[i].ID+'">'+i+'</p>'+
								'<ol  class="ProblemCont">'+
									'<li class="ProblemCont_name" >'+ProblemCont_name+'</li>'+
									'<li class="ProblemCont_cont" >'+data.datas[i].Content+'</li>'+
									'<li class="ProblemCont_Marking">'+
										'<div class="ProblemCont_Author" >作者：'+ProblemCont_Author+'</div>'+
										'<div class="DocuType">文档类型：'+DocuType+'</div>'+
										'<div class="ProblemCont_Time">'+data.datas[i].OperatingTime.slice(0,16)+'</div>'+
									'</li>'+
								'</ol>'+
							'</td>'+
						'</tr>';
	 	        	}
 	        	}
 	          	$(".pageInfo #currentPage").text(data.currentPage);
 	        	$(".pageInfo #allPage").text(data.pageCounts);
 	        	$("#table2").empty().append(str);
 	        	jumpDisable();
 	        },
 	        error : function () {
 	        	$.MsgBox_Unload.Alert("提示", "服务器繁忙！");
 	        }
 	    });
	}
	else if($("#currentTab").data("iidiagram").toString() == "Manage"){
		// 软件项目实施进度
		$.ajax({
 	        type : 'get',
 	        url : 'Software',
 	        data:{
 	        	Type :"Software",
 	        	currentPage : 1,
 	        	queryType :"common"
 	        },
 	        dataType:"json",
 	        success : function (data) {
 	        	var str = '';
 	        	for(var i = 1; i < data.datas.length;i++){
 	        		str += 
 	        			'<tr>'+
							'<td class="Serial" value="'+data.datas[i].ID+'">'+i+'</td>'+
							'<td><span class="glyphicon glyphicon-file" aria-hidden="true" value="'+data.datas[i].ID+'"></span></td>'+
							'<td class="ManualWest">'+data.datas[i].FileName+'</td>'+       
							'<td><span class="glyphicon glyphicon-save export" aria-hidden="true"></span></td>'+
							/*'<td><a href="'+data.datas[i].FilePath+'" class="export"></a></td>'+*/
						'</tr>';
	 	        	}
 	        	$(".pageInfo #currentPage").text(data.currentPage);
 	        	$(".pageInfo #allPage").text(data.pageCounts);
 	        	$("#table3 tbody").empty().append(str);
 	        	jumpDisable();
 	        },
 	        error: function () {
 	        	$.MsgBox_Unload.Alert("提示", "服务器繁忙！");
 	        }
 	    });
	}
}

/*时间线函数整体*/
function time_lineGetData(immediate,classify){
	var CurrentPage = 1;

	var searchObj = {
	    classify: classify
	};

	var searchParam = $.param(searchObj);

	var immediateFn = function(curTime){
		time_lineAjax(classify, function(ires){
			timeLineDataObj[searchParam] = {};
			var searchParamVal = timeLineDataObj[searchParam];
			searchParamVal[CurrentPage] = {};
			searchParamVal[CurrentPage].searchObj = $.extend(true, {}, searchObj);
			searchParamVal[CurrentPage].expire = curTime + (1000*60*1);
			searchParamVal[CurrentPage].resp = $.extend(true, [], ires);
		});
	};

	var expireFalseFn = function(resp){
	    resp = resp || [];
	    time_lineRender(resp, classify);
	};

	var expireTrueFn = function(curTime){
		time_lineAjax(classify, function(ires){
			var searchParamVal = timeLineDataObj[searchParam];
			searchParamVal[CurrentPage].expire = curTime + (1000*60*1);
			searchParamVal[CurrentPage].resp = $.extend(true, [], ires);
		});
	};

	var pageNotExistFn = function(curTime){
		time_lineAjax(classify, function(ires){
			var searchParamVal = timeLineDataObj[searchParam];
			searchParamVal[CurrentPage] = {};
			searchParamVal[CurrentPage].searchObj = $.extend(true, {}, searchObj);
			searchParamVal[CurrentPage].expire = curTime + (1000*60*1);
			searchParamVal[CurrentPage].resp = $.extend(true, [], ires);
		});
	};

	var searchParamNotExistFn = function(curTime){
		time_lineAjax(classify, function(ires){
			timeLineDataObj[searchParam] = {};
			var searchParamVal = timeLineDataObj[searchParam];
			searchParamVal[CurrentPage] = {};
			searchParamVal[CurrentPage].searchObj = $.extend(true, {}, searchObj);
			searchParamVal[CurrentPage].expire = curTime + (1000*60*1);
			searchParamVal[CurrentPage].resp = $.extend(true, [], ires);
		});
	};

	globalInertLoadNoSearch(immediate, immediateFn, timeLineDataObj, searchParam, CurrentPage, expireFalseFn, expireTrueFn, pageNotExistFn, searchParamNotExistFn);
}

function time_lineAjax(classify, fn){
	$.ajax({
		type: "GET",
		url: "VersionManagement",
		data: {
			ProjectName: classify
		},
		dataType: "json",
		success: function(res){
			time_lineRender(res, classify);
			fn && fn(res);
		},
		error: function(){
			$.MsgBox_Unload.Alert("提示", "服务器繁忙！读取发布记录失败");
		}
	});
}

$(document).on("click",".VersionManagement_div_body .main .year>h2>a",function(e){
	e.preventDefault();
	$(this).parents(".year").toggleClass("close");
});

// 时间线数据渲染函数
function time_lineRender(res, classify){
	$("#"+classify+"_a").empty();
	if(res.length == 1){
		if(vmHasInit){
			// 已经加载过
			$.Response_Load.Before("时间线绘制提示","无数据！",300);
			$.Response_Load.After("无数据！", 400);
		}else{
			var paramObj = ecDo.getUrlPrmt(window.location.href);
			if(paramObj.vmClassify == undefined){
				$.Response_Load.Before("时间线绘制提示","无数据！",250);
				$.Response_Load.After("无数据！", 300);
			}else{
				// 需要自动跳转
				if(classify == "futureC_T1"){
					$.Response_Load.Before("时间线绘制提示","正在读取数据！",350);
					$.Response_Load.After("正在读取数据！", 350);
				}else{
					$.Response_Load.Before("时间线绘制提示","无数据！",250);
					$.Response_Load.After("无数据！", 300);
				}
			}
		}
		return false;
	}else{
		var iprojectName;
		$.each(projectParamNameMap, function(iname, ivalue){
			if(iname == classify){
				iprojectName = ivalue;
				return false;
			}
		});
		var str = '<div class="i-content">'+
						'<div class="wrapper">'+
							'<div class="light"><i></i></div>'+
								'<hr class="line-left">'+
								'<hr class="line-right">'+
								'<div class="main">'+
									'<h1 class="title"><span class="glyphicon glyphicon-time" aria-hidden="true"></span>'+iprojectName+'<br><span class="small_tit">产品发布记录</span></h1>';
		// 排序
		var oldRes = []; 
		res.map(function(currentValue, index, arr){
			if(index > 0){
				var BoardingTime = currentValue.BoardingTime;
				if(BoardingTime != undefined && BoardingTime != "" && BoardingTime != "--"){
					var miTime = new Date(BoardingTime).getTime();
					currentValue.miTime = miTime;
					oldRes.push(currentValue);
				}
			}
		});
		// console.log(oldRes);
		var newRes = ecDo.arraySort(oldRes,"miTime,Version,Registrant");
		// console.log(newRes);
		newRes.reverse();
		// console.log(newRes);
		var newDataObj = {};
		newRes.map(function(currentValue, index, arr){
			var BoardingTime = currentValue.BoardingTime;
			if(BoardingTime != undefined && BoardingTime != "" && BoardingTime != "--"){
				var timeNO = ";"+BoardingTime.split("-")[0];
				if(newDataObj[timeNO] === undefined){
					newDataObj[timeNO] = [];
				}
				// var miTime = new Date(BoardingTime).getTime();
				// var iitem = {
				// 	miTime: miTime
				// };
				// var canSortObj = $.extend(true, {}, currentValue, iitem);
				// console.log(canSortObj);
				newDataObj[timeNO].push(currentValue);
			}
		});
		// console.log(newDataObj);
		// 开始
		$.each(newDataObj, function(ivalue, iname){
			str+='<div class="year">'+
					'<h2><a href="#">'+ivalue.replace(";", "")+'年<i></i></a></h2>'+
					'<div class="list">'+
						'<ul>';
			iname.map(function(currentValue, index, arr){
				str+='<li class="cls" data-iid="'+currentValue.ID+'">'+
						'<p class="date">'+currentValue.BoardingTime.split("-")[1]+'月'+currentValue.BoardingTime.split("-")[2]+'日</p>'+
						'<p class="intro">'+currentValue.Version+'</p>'+
						'<p class="version">'+currentValue.Registrant+'</p>'+
						'<div class="more">';
				var UpdatedContentArr;
				if(currentValue.UpdatedContent == undefined || currentValue.UpdatedContent === "" || currentValue.UpdatedContent == "--"){
					UpdatedContentArr = [];
				}else{
					UpdatedContentArr = currentValue.UpdatedContent.split("VM@splitor");
					UpdatedContentArr = $.grep(UpdatedContentArr,function(icurrentValue,iindex){
					    return icurrentValue != "";
					});
				}
				UpdatedContentArr.map(function(val, i, ar){
					var ii = (i+1).toString();
					str+='<p>';
					str+=ii;
					str+='、';
					str+=val;
					str+='</p>';
				});
				str+='</div></li>';
			});
			str+='</ul></div></div>';
		});
		// 结束
		str+='</div></div></div>';
		$("#"+classify+"_a").append(str);
		$("#"+classify+"_a").find(".main .year .list").each(function(e, target){
			var $target=  $(target),
			$ul = $target.find("ul");
			$target.height($ul.outerHeight());
			$ul.css("position", "absolute");
		});
	}
	vmHasInit = true;
}

/*时间线函数整体 end*/

$(function(){
	if($("#catalog").text() == "Log"){
		$("#year_area").show();
		$(".InterSearch").hide();   //搜索框收起
		sessionStorage.removeItem("tab");  //清除软件文档 缓存
	}else{
		$("#year_area").hide();
	}
	if($("#catalog").text() == "Original"||$("#catalog").text()=="Sales"||$("#catalog").text()=="Image"){
		$("#area").hide();
		sessionStorage.removeItem("tab");  // 清除软件文档缓存
	}else{
		$("#area").show();
	}
	if($("#catalog").text()=="Software"){
		$("#area").hide();
		$(".changeBox").show();
	}else{
		$("#area").show();
		sessionStorage.removeItem("tab");  // 清除软件文档缓存
	}
	//页面加载 a  href 
	var catalog = $("#catalog").text();
	$(".south a").attr("href","DocumentUpload?Area=south&catalog="+catalog+"&Year=2018&Type=Cascade");
	$(".north a").attr("href","DocumentUpload?Area=north&catalog="+catalog+"&Year=2018&Type=Cascade");
	$(".southwest a").attr("href","DocumentUpload?Area=southwest&catalog="+catalog+"&Year=2018&Type=Cascade");
	jumpDisable();

	pageStyle(Number($('#currentPage').html()), Number($('#allPage').html()), "");

	var content = $(".scontent").text();
	$(".tsearch").val(content);

	// 判断是否要跳转
	var paramObj = ecDo.getUrlPrmt(window.location.href);
	if(paramObj.vmClassify != undefined){
		$(".loading_div_g_div").show();
		var vmClassify = paramObj.vmClassify.replace("ZzZ","_");
		$(".m_pagination>.tab_wrapper li[data-iidiagram='VersionManagement']").children("a").trigger("click");
		setTimeout(function(){
			$(".VersionManagement_div_tit_r li[data-projectname='"+vmClassify+"']").children("a").trigger("click");
			$(".loading_div_g_div").hide();
		},1000);
	}
});

//装机实施手册相关页面
$('.MV_CFCs').click(function(){
	$('#show_MV_CFCs').show();
});

//装机报告和装机日志
$('.MV_report_daily').click(function(){
	$('#show_MV_CFCs').show();
});

//装机图片
$('.MV_picture').click(function(){
	$('#show_MV_CFCs').show();
});

//服务请求和服务完成
$('.serve').click(function(){
	$('#show_MV_CFCs').show();
});

//EUCP软件实施管理文档
$('.software_actualize').click(function(){
	$('#show_MV_CFCs').show();
});

//点击上传文件
	$(".writeEmail").click(function(){
		var myDate = new Date();
		var Y = myDate.getFullYear();
		var date = Y ;
		$(".MailBar .upyear option[value="+ date +"]").attr("selected",true);
		
			$(".PwdCont").show();
			$(".PwdInput").val("");
			if($("#currentTab").data("iidiagram").toString() == "CommonProblem"){
				$(".SoftType").show();
				$(".NameFormat").show();
				$(".MailBar").css("height","390px");
				$("#Mail_Send").css("top","325px");
			}
			else{
				$(".SoftType").hide();
				$(".NameFormat").hide();
				$(".MailBar").css("height","320px");
				$("#Mail_Send").css("top","263px");
			}
		
		$(".MailBar").show();
		$(".MailBar_cover_color").show();
		$(".MailBar_cover_color").css("height",$(document).height()+30);
		$(".MailBar_All").hide();
		$("#Mail_Send").attr("disabled",false);
		$("#Mail_Send").removeClass("isClick").addClass("bToggle");
	});
	
//点击批量上传文件
	$(".All_upload ").click(function(){
		var myDate = new Date();
		var Y = myDate.getFullYear();
		var date = Y ;
		$(".MailBar_All .upyear option[value="+ date +"]").attr("selected",true);
		$(".MailBar_All").show();
		$(".MailBar_cover_color").show();
		$(".MailBar_cover_color").css("height",$(document).height()+10);
		$(".MailBar").hide();
		$("#All_Send").attr("disabled",false);
		$("#All_Send").removeClass("isClick").addClass("bToggle");
		
			$(".PwdCont").show();
			$(".PwdInput").val("");
			if($("#currentTab").data("iidiagram").toString() == "CommonProblem"){
				$(".SoftType").show();
				$(".NameFormat").show();
				$(".MailBar_All").css("height","390px");
				$("#All_Send").css("top","325px");
			}
			else{
				$(".SoftType").hide();
				$(".NameFormat").hide();
				$(".MailBar_All").css("height","320px");
				$("#All_Send").css("top","263px");
			}
	});
	$(".MailBar_close").click(function(){
		$(".MailBar_cover_color").hide();
		$(".MailBar").hide();
		$(".MailBar_All").hide();
	});

 //选择文件之后执行上传  
    $('#Mail_Send').on('click', function() {
    	$(this).attr("disabled",true);
    	$("#Mail_Send").removeClass("bToggle").addClass("isClick");
    	var catalog;
    	var Area;
    	var Year;
    	var Type;
    	var v1 = $(".MailBar #Mail_fileToUpload").val();
		if(v1 == ''){
			$.MsgBox_Unload.Alert("提示", "请检查是否选择文件！");
			 $(this).attr("disabled",false);
			 $("#Mail_Send").removeClass("isClick").addClass("bToggle");
			return;
		}
    		var flag = true;
    		var FileName = $(".MailBar #Mail_fileToUpload").val();
    		var Password = $(".MailBar .PwdInput").val();
    		var DocuType = $(".MailBar  .uptype option:selected").attr('value');
    		console.log(FileName);
    		console.log(Password);
    		var iurl;
    		if($("#currentTab").data("iidiagram").toString() == "Norms"){  //开发规范
    			iurl = "SoftwareSpecificationUpload";
    			var data={
    					FileName:FileName,
    					Password:Password,
    			};
    		}
    		else if($("#currentTab").data("iidiagram").toString() == "CommonProblem"){  //问题
    			iurl = "CommonProblemUpload";
    			if(FileName.split("\\")[2].split("-").length !=3){   //判断上传文件名 格式
    				flag = false;
    				$.MsgBox_Unload.Alert("提示", "文件名格式错误!",function(){ $("#Mail_Send").attr("disabled",false);$("#Mail_Send").removeClass("isClick").addClass("bToggle");});	
    				
    			}
    			else{
    				flag = true;
    			}
    			var data={
    					FileName:FileName,
    					Password:Password,
    					DocuType:DocuType
    			};
    		}
    		else if($("#currentTab").data("iidiagram").toString() == "Manage"){
    			iurl = "SoftwareDepartmentUpload";
    			var data={
    					FileName:FileName,
    					Password:Password,
    			};
    		}
    		//软件管理
    		catalog = "Software";
    		Type ="Software";
    		if(flag){
    			$.ajaxFileUpload({  
                    url:iurl,
                    secureuri:false,  
                    fileElementId:'Mail_fileToUpload',//file标签的id  
                    dataType : 'text',//返回数据的类型  
                    data:data,//一同上传的数据  
                    success: function (data) {
                   		$.MsgBox_Unload.Alert("提示",data);	
                   		$(".MailBar").hide();
                   		$(".MailBar_cover_color").hide();
                   		$(this).attr("disabled",false);
                   		$("#Mail_Send").removeClass("isClick").addClass("bToggle");
                   		autoRefrush();
                    },  
                    error: function (e) {  
                    	$.MsgBox_Unload.Alert("提示","上传失败！");	
                        $(this).attr("disabled",false);
                        $("#Mail_Send").removeClass("isClick").addClass("bToggle");
                    }  
                });
    		}
    });  
//-------------------------------------下载装机实施手册-----------------------------------------------------
$(document).on("click",".export",function () {
	var tr=$(this).parent().parent();
	var ID = tr.find('td').eq(0).attr("value");
	var catalog;
		//下载软件管理
		$.ajax({
	        type: 'get',
	        url: 'SoftwareDepartmentDownload',
	        data: {
	            ID :ID
	        },
	        success: function (data) {
	        	console.log(data);
	        	window.location.href=data;
	        },
	        error: function () {
	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    });
});

//软件文档    预览
$(document).on("click",".ProblemCont_name, .ManualWest",function(){
		var ID;
		if($(this).attr("Class")=="ProblemCont_name"){
			ID = $(this).parent().prev().attr("value");
		}
		else{
			ID = $(this).parent().find("td").eq(0).attr("value");
		}
		var iurl;
		if($("#currentTab").data("iidiagram").toString() == "Norms"){
			iurl ="SoftwareSpecificationDownload"; 
		}
		else if($("#currentTab").data("iidiagram").toString() == "CommonProblem"){
			iurl ="CommonProblemDownload"; 
		}
		else if($("#currentTab").data("iidiagram").toString() == "Manage"){
			iurl ="SoftwareDepartmentDownload"; 
		}
		$.ajax({
 	        type: 'get',
 	        url: iurl,
 	        async: false,
 	        data:{
 	        	ID: ID,
 	        },
 	        success : function (data) {
 	        	console.log(data);
 	        	window.location.href=data;
 	        },
 	        error: function () {
 	        	console.log("error");
 	        }
 	    });
});

function Check(selected) {
	if (selected == "singleSelect") {
        $('.select2').hide();
        $('.select-content').css('margin-left','33%');
    } else {
        $('.select2').show();
        $('.select-content').css('margin-left','23%');
    }
}

//实现批量上传压缩包
$('#All_Send').on('click', function() {
	$("#All_Send").attr("disabled",true);
	$("#All_Send").removeClass("bToggle").addClass("isClick");
	var v2 = $(".MailBar_All #Mail_fileToUpload").val();
	if(v2 == ''){
		$.MsgBox_Unload.Alert("提示", "请检查是否选择文件！");
		$("#All_Send").attr("disabled",false);
		$("#All_Send").removeClass("isClick").addClass("bToggle");
		return;
	}
    var form = new FormData(document.getElementById("f"));
	var catalog;
	var Area;
	var Year;
	var Type;
	
		//软件管理
		catalog = "Software";
		Type ="Software";
		 console.log('11111111'+catalog);
         	$("#All_Send2").val(Area);
        	$("#All_Send3").val(Year);
        	$("#All_Send4").val(Type);
    		var Password = $(".MailBar_All .PwdInput").val();
    		var DocuType = $(".MailBar_All  .uptype option:selected").attr('value');
    		console.log(Password);
    		console.log(DocuType);
    		var iurl;
    		if($("#currentTab").data("iidiagram").toString() == "Norms"){  //开发规范
    			iurl = "SoftwareSpecificationBatchUpload";
    			var dataType = "TEXT";
    			form.append("Password", Password);
    		}
    		else if($("#currentTab").data("iidiagram").toString() == "CommonProblem"){  //问题
    			iurl = "CommonProblemBatchUpload";
    			var dataType = "JSON";
    			form.append("DocuType", DocuType);
    			form.append("Password", Password);
    		}
    		else if($("#currentTab").data("iidiagram").toString() == "Manage"){
    			iurl = "SoftwareDepartmentBatchUpload";
    			var dataType = "JSON";
    			form.append("Password", Password);
    		}
    		
    		console.log(form);
    		$.ajax({
              	url:iurl,
              	type:"post",
              	data:form,
              	processData:false,
              	contentType:false,
              	dataType : dataType,
              	success:function(data){
	                $.MsgBox_Unload.Alert("提示",data);
	                $("#All_Send").attr("disabled",false);
	                $("#All_Send").removeClass("isClick").addClass("bToggle");
	                $(".MailBar_All").hide();
	                $(".MailBar_cover_color").hide();
	                window.clearInterval();
	                autoRefrush();
              	},
              	error:function(e){
            	  	$.MsgBox.Alert("提示",'网络错误，请稍候重试！');
                  	$("#All_Send").attr("disabled",false);
                  	$("#All_Send").removeClass("isClick").addClass("bToggle");
                  	window.clearInterval();
              	}
          	});  
	
});  

//点击搜索
$(".iiisearch").click(function(){
	$("#show_MV_CFCs").attr("searchFlag","SingleSelect");
	var content = $(".tsearch").val();
	var catalog = $(".scatalog").text();
	var Area = $(".sarea").text();
	var type = $(".otype").text();
	searchOrjump(1,content,sortFlag);
});

//软件文档 tab切换   Norms CommonProblem Manage
$('.tab_wrapper>ul>li>a').click(function(e) {
  	e.preventDefault();
  	$(this).tab('show');
  	$(this).parent().attr("ID","currentTab").siblings().removeAttr("ID");
  	$("#jumpNumber").text("");
  	$(".tsearch").val("");
});
$('.tab_wrapper>ul>li>a').on('shown.bs.tab', function (e) {
    console.log(e.target); // newly activated tab
    console.log(e.relatedTarget); // previous active tab
    console.log($(e.target));
    if($(e.target).text() == "软件开发规范"){
    	// sessionStorage.setItem("tab", "Norms");
 	    $.ajax({
 	        type: 'GET',
 	        url: 'Software',
 	        data: {
 	        	Type :"Specification",
 	        	currentPage : 1,
 	        	queryType :"common"
 	        },
 	        dataType: "json",
 	        success: function (data) {
 	        	var str = '';
 	        	if( data.datas.length> 1){
 	        		for(var i = 1; i < data.datas.length;i++){
 	        			str += 
 	 	        			'<tr>'+
 								'<td class="Serial" value="'+data.datas[i].ID+'">'+i+'</td>'+
 								'<td><span class="glyphicon glyphicon-file" aria-hidden="true" value="'+data.datas[i].ID+'"></span></td>'+
 								'<td class="ManualWest">'+data.datas[i].FileName+'</td>'+       
 								'<td><span class="glyphicon glyphicon-save export" aria-hidden="true"></span></td>'+
 								/* '<td><a href="'+data.datas[i].FilePath+'" class="export"></a></td>'+ */
 							'</tr>';
	 	        	}
 	        	}
 	        	$(".pageInfo #currentPage").text(data.currentPage);
 	        	$(".pageInfo #allPage").text(data.pageCounts);
 	        	$("#table1 tbody").empty().append(str);
 	        	jumpDisable();
 	        },
 	        error : function () {
 	        	$.MsgBox_Unload.Alert("提示", "服务器繁忙！");
 	        }
 	    });
    }else if($(e.target).text() == "软件开发常见问题库"){
    	// sessionStorage.setItem("tab", "CommonProblem");
		$.ajax({
 	        type : 'get',
 	        url : 'Software',
 	       data:{
	        	Type :"CommonProblem",
	        	currentPage : 1,
	        	queryType :"common"
	        },
	        dataType:"json",
 	        success : function (data) {
 	        	var str = '';
 	          	if( data.datas.length> 1){
 	        		for(var i = 1; i < data.datas.length;i++){
 	        			var ProblemCont_name = data.datas[i].FileName.split("-")[0];
 	        			var ProblemCont_Author = data.datas[i].FileName.split("-")[2].split(".")[0];
 	        			var DocuType = data.datas[i].DocuType;
 	        			str += 
 	        			'<tr style="width:97%;padding:0 1.5%;">'+
							'<td >'+
								'<p  style="cursor:pointer;" title="排序" class="ProblemNum Serial" value="'+data.datas[i].ID+'">'+i+'</p>'+
								'<ol  class="ProblemCont">'+
									'<li class="ProblemCont_name" >'+ProblemCont_name+'</li>'+
									'<li class="ProblemCont_cont" >'+data.datas[i].Content+'</li>'+
									'<li class="ProblemCont_Marking">'+
										'<div class="ProblemCont_Author" >作者：'+ProblemCont_Author+'</div>'+
										'<div class="DocuType">文档类型：'+DocuType+'</div>'+
										'<div class="ProblemCont_Time">'+data.datas[i].OperatingTime.slice(0,16)+'</div>'+
									'</li>'+
								'</ol>'+
							'</td>'+
						'</tr>';
	 	        	}
 	        	}
 	          	$(".pageInfo #currentPage").text(data.currentPage);
 	        	$(".pageInfo #allPage").text(data.pageCounts);
 	        	$("#table2").empty().append(str);
 	        	jumpDisable();
 	        },
 	        error : function () {
 	        	$.MsgBox_Unload.Alert("提示", "服务器繁忙！");
 	        }
 	    });
    }else if($(e.target).text() == "软件项目实施进度"){
    	// sessionStorage.removeItem("tab");
		$.ajax({
 	        type : 'get',
 	        url : 'Software',
 	        data:{
 	        	Type :"Software",
 	        	currentPage : 1,
 	        	queryType :"common"
 	        },
 	        dataType:"json",
 	        success : function (data) {
 	        	var str = '';
 	        	for(var i = 1; i < data.datas.length;i++){
 	        		str += 
 	        			'<tr>'+
							'<td class="Serial" value="'+data.datas[i].ID+'">'+i+'</td>'+
							'<td><span class="glyphicon glyphicon-file" aria-hidden="true" value="'+data.datas[i].ID+'"></span></td>'+
							'<td class="ManualWest">'+data.datas[i].FileName+'</td>'+       
							'<td><span class="glyphicon glyphicon-save export" aria-hidden="true"></span></td>'+
							/*'<td><a href="'+data.datas[i].FilePath+'" class="export"></a></td>'+*/
						'</tr>';
	 	        	}
 	        	$(".pageInfo #currentPage").text(data.currentPage);
 	        	$(".pageInfo #allPage").text(data.pageCounts);
 	        	$("#table3 tbody").empty().append(str);
 	        	jumpDisable();
 	        },
 	        error: function () {
 	        	$.MsgBox_Unload.Alert("提示", "服务器繁忙！");
 	        }
 	    });
    }else if($(e.target).text() == "产品发布记录"){
    	$(".relative_div, #page").slideUp(100,function(){
			$(".VersionManagement_div_tit_r li:not(.active) img").attr("src",windowsPng);
    		$(".VersionManagement_div_tit_r li.active img").attr("src", $(".VersionManagement_div_tit_r li.active img").data("isrc").toString());
    	});
    	var classify = $(".VersionManagement_div_tit_r li.active").data("projectname").toString();
    	// time_lineAjax(classify);
    	time_lineGetData(false,classify);
    	$("#projectname_select").val(classify);
    }
    if($(e.target).text() != "产品发布记录"){
    	$(".relative_div, #page").slideDown(200);
    }
});
	
$('.VersionManagement_div_tit_r>ul>li>a').click(function(e) {
  	e.preventDefault();
  	$(this).tab('show');
  	$(".VersionManagement_div_tit_r li.actived img").attr("src",windowsPng);
  	$(this).children("img").attr("src", $(this).children("img").data("isrc").toString());
  	$(this).parent().addClass("actived").siblings().removeClass("actived");
});

$('.VersionManagement_div_tit_r>ul>li>a').on('shown.bs.tab', function(e){
	var classify = $(e.target).parent().data("projectname").toString();
	// time_lineAjax(classify);
	time_lineGetData(false,classify);
	$("#projectname_select").val(classify);
});

// 软件文档排序
$(document).on("click","#table1 th.TitleName, #table2 .ProblemNum, #table3 th.TitleName",function(){
	sortFlag = !sortFlag;
	var currentPage = $("#currentPage").text();
	var content = $(".tsearch").val();
	if(sortFlag){
		$(this).children("span").show();
	}else{
		$(this).children("span").hide();
	}
	searchOrjump(currentPage,content,sortFlag);
});
		
// 添加产品发布记录打开
$(".VersionManagement_div_tit_add>span").click(function(){
	$(".VersionManagement, .MailBar_cover_color22").slideDown(250);
	$(".VersionManagement_body_in input, .VersionManagement_body_in select").val("");
	$(".appendp_p_div").empty().append('<textarea class="form-control" rows="3"></textarea>');
});
$(".VersionManagement_tit>span.glyphicon, #VersionManagement_cancel").click(function(){
	$(".VersionManagement, .MailBar_cover_color22").slideUp(250);
});

// 添加一条更新内容
$(".add_line_p").click(function(){
	var str = '<textarea class="form-control" rows="3"></textarea>';
	$(".appendp_p_div").append(str);
});

// 密码显隐
$("#VM_add_password+span").click(function(){
	$(this).toggleClass("glyphicon-eye-open glyphicon-eye-close");
	if($(this).prev().attr("type") == "text"){
		$(this).prev().attr("type", "password");
	}else if($(this).prev().attr("type") == "password"){
		$(this).prev().attr("type", "text");
	}
});

// 产品发布记录提交
$("#VersionManagement_submit").click(function(){
	var ProjectName = $("#VM_add_ProjectName").val();
	if(!ProjectName){
		$.MsgBox_Unload.Alert("添加提示", "项目名称必选！");
		return false;
	}
	var Version = $("#VM_add_Version").val().trim();
	if(Version === ""){
		$.MsgBox_Unload.Alert("添加提示", "版本号必填！");
		return false;
	}
	var BoardingTime = $("#VM_add_BoardingTime").val();
	if(BoardingTime === ""){
		$.MsgBox_Unload.Alert("添加提示", "登记时间必填！");
		return false;
	}
	var Password = $("#VM_add_password").val();
	if(Password === ""){
		$.MsgBox_Unload.Alert("添加提示", "邮箱密码必填！");
		return false;
	}
	var Registrant = $("#VM_add_Registrant").val().trim();
	var UpdatedContent = '';
	$("div.appendp_p_div>textarea").each(function(){
		var iText = $(this).val().trim();
		var newIText;
		if(regPrepSerialNO.test(iText)){
			newIText = iText.replace(regPrepSerialNO, "");
		}else if(regPrepSerialNONotImmediate.test(iText)){
			newIText = iText.replace(regPrepSerialNONotImmediate, "");
		}else{
			newIText = iText;
		}
		if(newIText !== ""){
			UpdatedContent+=newIText;
			UpdatedContent+='VM@splitor';
		}
	});
	UpdatedContent = UpdatedContent.replace(/[\r\n]+/g, "<br>");
	var vmClassifyHref = eouluGlobal.S_settingURLParam({vmClassify: ProjectName.replace("_", "ZzZ")}, true);
	var iThat = $(this);
	$.ajax({
		type: "POST",
		url: "VersionManagement",
		data: {
			ProjectName: ProjectName,
			Version: Version,
			Registrant: Registrant,
			BoardingTime: BoardingTime,
			Password: Password,
			UpdatedContent: UpdatedContent,
			vmClassifyHref: vmClassifyHref
		},
		dataType: "json",
		beforeSend: function(XMLHttpRequest){
		    $.Response_Load.Before("添加提示","正在提交并发送邮件...",200);
		    eouluGlobal.C_btnDisabled(iThat, true, "正在发送...");
		},
		success: function (data) {
			console.log(typeof data);
			var istatus = data.status;
			var imessage = data.message;
			if(istatus == "ok"){
				$("#VersionManagement_cancel").trigger("click");
				if($(".VersionManagement_div_tit_r li.actived").data("projectname").toString() != ProjectName){
					$("#"+ProjectName+"_a").siblings().removeClass("active in");
					$("#"+ProjectName+"_a").addClass("active in");
					$(".VersionManagement_div_tit_r li.actived img").attr("src",windowsPng);
					var $curActive = $(".VersionManagement_div_tit_r li[data-projectname='"+ProjectName+"'] img");
					$curActive.attr("src", $curActive.data("isrc").toString());
					$curActive.parent().parent().addClass("actived active").siblings().removeClass("actived active");
				}
				$("#projectname_select").val(ProjectName);
				time_lineGetData(true,ProjectName);
				// $(".VersionManagement_div_tit_r li[data-projectname='"+ProjectName+"']").children("a").trigger("click");
			}
			$.Response_Load.After(imessage, 2100);
		},
		error: function () {
			$.Response_Load.After("服务器繁忙，请稍后重试！", 2000);
		    // $.MsgBox_Unload.Alert("提示", "服务器繁忙，请稍后重试！");
		},
		complete: function(XMLHttpRequest, textStatus){
		    if(textStatus=='success'){
		        // $.Response_Load.After("提交操作完成！",300);
		    }else if(textStatus=='error'){
		        // $.Response_Load.After("提交操作失败！",300);
		    }else if(textStatus=='timeout'){
		        var xmlhttp = window.XMLHttpRequest ? new window.XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHttp");  
		        xmlhttp.abort();
		        $.Response_Load.After("连接超时！", 600);
		    }
		    eouluGlobal.C_btnAbled(iThat, true, "提交");
		}
	});
});

/*原script*/
/****************** 跳页 **********************/
function FistPage(arg) {
	var content = $(".tsearch").val();
	if($(".changeBox").css("display")!="none"){
		searchOrjump(1,content,sortFlag);
	}
	else{
		window.location.href = arg + "1";
	}
}
function UpPage(arg) {
	var content = $(".tsearch").val();
	if($(".changeBox").css("display")!="none"){
		var page = parseFloat($("#currentPage").text())-1;
		searchOrjump(page,content,sortFlag);
	}
	else{
		window.location.href = arg;
	}
}
function NextPage(arg) {
	var content = $(".tsearch").val();
	if($(".changeBox").css("display")!="none"){
		var page = parseFloat($("#currentPage").text())+1;
		searchOrjump(page,content,sortFlag);
	}
	else{
		window.location.href = arg;
	}
}
function change(){
	var jumpNumber = $("#jumpNumber").text();
	if(jumpNumber.replace(/[^\d]/g,'')){
	}else{
		$("#jumpNumber").text('');
	}
}
function PageJump(arg) {
	var content = $(".tsearch").val();
	var jumpNumber = $("#jumpNumber").text();
	
	if (jumpNumber == null || jumpNumber == 0) {
		jumpNumber = $('#currentPage').html();
	} else if (jumpNumber > parseInt($('#allPage').html())) {
		jumpNumber = $('#allPage').html();
	}
	if($(".changeBox").css("display")!="none"){
		searchOrjump(jumpNumber,content,sortFlag);
	}
	else{
		window.location.href = arg + jumpNumber;
	}
}
function LastPage(arg) {
	var content = $(".tsearch").val();
	var jumpNumber = parseInt($('#allPage').html());
	if($(".changeBox").css("display")!="none"){
		searchOrjump(jumpNumber,content,sortFlag);
	}
	else{
		window.location.href = arg + jumpNumber;
	}
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

//跳页或者搜索执行的ajax 函数            当前页              内容               排序          搜索状态
function searchOrjump(currentPage,content,flag){
	var queryType = $("#show_MV_CFCs").attr("searchFlag");
	var idata;
	if(!queryType){queryType = "common";}
	if(flag){
		if($("#currentTab").data("iidiagram").toString() == "Norms"){  //开发规范
			idata={
					Type:"Specification",
					currentPage:currentPage,
					queryType :queryType,
					column:"FileName",
					Reorder : "asc",
					content:content,
			};
		}
		else if($("#currentTab").data("iidiagram").toString() == "CommonProblem"){  //问题
			idata={
					Type:"CommonProblem",
					currentPage:currentPage,
					queryType :queryType,
					column:"FileName",
					Reorder : "asc",
					content:content,
			};
		}
		else if($("#currentTab").data("iidiagram").toString() == "Manage"){
			idata={
					Type:"Software",
					currentPage:currentPage,
					queryType :queryType,
					column:"FileName",
					Reorder : "asc",
					content:content,
			};
		}
	}
	else{    //不排序
		if($("#currentTab").data("iidiagram").toString() == "Norms"){  //开发规范
			idata={
					Type:"Specification",
					currentPage:currentPage,
					/* queryType:"SingleSelect", */
					queryType : queryType,
					content:content,
			};
		}
		else if($("#currentTab").data("iidiagram").toString() == "CommonProblem"){  //问题
			idata={
					Type:"CommonProblem",
					currentPage:currentPage,
					queryType : queryType,
					content:content,
			};
		}
		else if($("#currentTab").data("iidiagram").toString() == "Manage"){
			idata={
					Type:"Software",
					currentPage:currentPage,
					queryType : queryType,
					content:content,
			};
		}
	}
	 $.ajax({
 	        type : 'get',
 	        url : 'Software',
 	        data: idata,
 	        dataType:"json",
 	        success : function (data) {
 	        	$("#show_MV_CFCs").attr("searchFlag",data.queryType);
 	        	var currentTab = $("#currentTab").data("iidiagram").toString();
 	        	// var currentTab =  sessionStorage.getItem("tab");
 	        	if(currentTab == "Norms"){
	 	        	var str = '';
	 	        	if( data.datas.length> 1){
	 	        		for(var i = 1; i < data.datas.length;i++){
	 	        			str += 
	 	 	        			'<tr>'+
	 								'<td class="Serial" value="'+data.datas[i].ID+'">'+((currentPage-1)*10+i)+'</td>'+
	 								'<td><span class="glyphicon glyphicon-file" aria-hidden="true" value="'+data.datas[i].ID+'"></span></td>'+
	 								'<td  class="ManualWest">'+data.datas[i].FileName+'</td>'+       
	 								'<td><span class="glyphicon glyphicon-save export" aria-hidden="true"></span></td>'+
	 								/* '<td><a href="'+data.datas[i].FilePath+'" class="export"></a></td>'+ */
	 							'</tr>';
		 	        	}
	 	        	}
	 	        	$(".pageInfo #currentPage").text(data.currentPage);
	 	        	$(".pageInfo #allPage").text(data.pageCounts);
	 	        	$("#table1 tbody").empty().append(str);
	 	        	jumpDisable();
 	        	}else if(currentTab == "CommonProblem"){
 	        		// 常见问题
	 	        	var str = '';
	 	          	if( data.datas.length> 1){
	 	        		for(var i = 1; i < data.datas.length;i++){
	 	        			var ProblemCont_name = data.datas[i].FileName.split("-")[0];
	 	        			var ProblemCont_Author = data.datas[i].FileName.split("-")[2].split(".")[0];
	 	        			var DocuType = data.datas[i].DocuType;
	 	        			str += 
	 	        			'<tr style="width:97%;padding:0 1.5%;">'+
								'<td >'+
									'<p style="cursor:pointer;" title="排序" class="ProblemNum Serial" value="'+data.datas[i].ID+'">'+((currentPage-1)*5+i)+'</p>'+
									'<ol  class="ProblemCont">'+
										'<li class="ProblemCont_name" >'+ProblemCont_name+'</li>'+
										'<li class="ProblemCont_cont" >'+data.datas[i].Content+'</li>'+
										'<li class="ProblemCont_Marking">'+
											'<div class="ProblemCont_Author" >作者：'+ProblemCont_Author+'</div>'+
											'<div class="DocuType">文档类型：'+DocuType+'</div>'+
											'<div class="ProblemCont_Time">'+data.datas[i].OperatingTime.slice(0,16)+'</div>'+			
										'</li>'+
									'</ol>'+
								'</td>'+
							'</tr>';
		 	        	}
	 	        	}
	 	          	$(".pageInfo #currentPage").text(data.currentPage);
	 	        	$(".pageInfo #allPage").text(data.pageCounts);
	 	        	$("#table2").empty().append(str);
	 	        	jumpDisable();
 	        	}else if(currentTab == "Manage"){
 	        		// 软件项目实施进度
	 	        	var str = '';
	 	        	for(var i = 1; i < data.datas.length;i++){
	 	        		str += 
	 	        			'<tr>'+
								'<td class="Serial" value="'+data.datas[i].ID+'">'+((currentPage-1)*10+i)+'</td>'+
								'<td><span class="glyphicon glyphicon-file" aria-hidden="true" value="'+data.datas[i].ID+'"></span></td>'+
								'<td  class="ManualWest">'+data.datas[i].FileName+'</td>'+       
								'<td><span class="glyphicon glyphicon-save export" aria-hidden="true"></span></td>'+
								/* '<td><a href="'+data.datas[i].FilePath+'" class="export"></a></td>'+ */
							'</tr>';
		 	        	}
	 	        	$(".pageInfo #currentPage").text(data.currentPage);
	 	        	$(".pageInfo #allPage").text(data.pageCounts);
	 	        	$("#table3 tbody").empty().append(str);
	 	        	jumpDisable();
 	        	}
 	        },
 	        error: function () {
 	        	$.MsgBox_Unload.Alert("提示", "服务器繁忙！");
 	        }
 	    });
	}

	function jumpDisable(){
		pageStyle(Number($('#currentPage').html()), Number($('#allPage').html()), "");
	}

/*回到顶部*/
$('#Transport_wrapper').scroll(function() {
    // console.log($(this).scrollTop());

    //当window的scrolltop距离大于1时，go to 
    if ($(this).scrollTop() > 300) {
        $('div.gotoPageTop').fadeIn(100);
    } else {
        $('div.gotoPageTop').fadeOut(100);
    }
});

$('div.gotoPageTop').click(function(e) {
	e.stopPropagation();
    $('#Transport_wrapper').animate({
        scrollTop: 0
    }, 500);
});
