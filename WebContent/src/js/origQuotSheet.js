/*****函数定义与变量定义*****/
var addSubmitObj = new Object();
addSubmitObj.Type = null;
addSubmitObj.CustomerID = null;
addSubmitObj.Model = null;
addSubmitObj.ChuckType = null;
addSubmitObj.TemperatureRequirement = null;
addSubmitObj.Microscope = null;
addSubmitObj.VersionNumber = null;
addSubmitObj.FilePath = null;
addSubmitObj.Remarks = null;
addSubmitObj.QuoteTotal = null;
var updateSubmitObj = new Object();
updateSubmitObj.ID = null;
updateSubmitObj.Type = null;
updateSubmitObj.CustomerID = null;
updateSubmitObj.Model = null;
updateSubmitObj.ChuckType = null;
updateSubmitObj.TemperatureRequirement = null;
updateSubmitObj.Microscope = null;
updateSubmitObj.VersionNumber = null;
updateSubmitObj.FilePath = null;
updateSubmitObj.Remarks = null;
updateSubmitObj.QuoteTotal = null;
var hasSearch = 0;
var IsFileUpload = 0;
// var regDate1 = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
// var regDate2 = /^[1-9]\d{3}-(0[1-9]|1[0-2])$/;
// var regDate3 = /^(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
// var regDate4 = /^[1-9]\d{3}$/;
// var regDate5 = /^(0[1-9]|1[0-2])$/;
// var regDate6 = /^(0[1-9]|[1-2][0-9]|3[0-1])$/;
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
	            '<td class="update_td" data-iid="'+data[i].ID+'" data-customer-id="'+data[i].CustomerID+'">'+parseInt((icurPage-1)*10 + i)+'</td>'+
	            '<td class="hastd_CustomerName" title="'+data[i].CustomerName+'">'+data[i].CustomerName+'</td>'+
	            '<td class="hastd_CustomerDepartment" title="'+data[i].CustomerDepartment+'">'+data[i].CustomerDepartment+'</td>'+
	            '<td class="hastd_Contact" title="'+data[i].Contact+'">'+data[i].Contact+'</td>'+
	            '<td class="hastd_Model" title="'+data[i].Model+'">'+data[i].Model+'</td>'+
	            '<td class="hastd_ChuckType" title="'+data[i].ChuckType+'">'+data[i].ChuckType+'</td>'+
	            '<td class="hastd_TemperatureRequirement" title="'+data[i].TemperatureRequirement+'">'+data[i].TemperatureRequirement+'</td>'+
	            '<td class="hastd_Microscope" title="'+data[i].Microscope+'">'+data[i].Microscope+'</td>'+
	            '<td class="hastd_VersionNumber" title="'+data[i].VersionNumber+'">'+data[i].VersionNumber+'</td>'+
	            '<td class="hastd_QuoteTotal" title="'+data[i].QuoteTotal+'">'+data[i].QuoteTotal+'</td>'+
	            '<td class="hastd_FilePath" title="'+data[i].FilePath+'">'+data[i].FilePath+'</td>'+
	            '<td class="hastd_Remarks" title="'+data[i].Remarks+'">'+data[i].Remarks+'</td>'+
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
		url:"OriginalQuotation",
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
			url:"OriginalQuotation",
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
			url:"OriginalQuotation",
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

//上传文件
function uploadFiles(){                                               
    var formData = new FormData();
    formData.enctype="multipart/form-data";
    formData.append("file",$("#serFinRepUpload")[0].files[0]);//append()里面的第一个参数file对应permission/upload里面的参数file            
    $.ajax({
        type:"POST",
        async:true,  //这里要设置异步上传，才能成功调用myXhr.upload.addEventListener('progress',function(e){}),progress的回掉函数
        accept:'text/html;charset=UTF-8',
        data:formData,
        // contentType:"multipart/form-data",
        url: "OriginalQuotationUpload",
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
                    $(".progressIn").css("width",newWidth+"px");
                    $(".progressIn").text(percent);
                }, false); // for handling the progress of the upload
            }
            return myXhr;
        },                    
        success:function(data){
        	console.log(typeof data);
        	var fileName_add = $("input.serFinRepUploadName").val();
        	if(data.indexOf("上传失败")>-1){
        		$("span.isUpload").text("上传失败！");
        		$(".progressIn").css("width","30px");
        		$(".progressIn").text("0%");
        	}else if(data.indexOf("读取报价失败，请手输")>-1){
        		if(IsFileUpload ==1){
        			$(".add_NonStandard .info_FilePath").val(fileName_add);
        			$(".add_NonStandard .info_FilePath").attr("title",fileName_add);
        			$("span.isUpload").text("上传成功！");
        			$.MsgBox_Unload.Alert("提示","读取报价失败，请手输价格");
        			setTimeout(function(){
        				$(".dropFileTit span").trigger("click");
        			},1000);
        		}else if(IsFileUpload==2){
        			$(".update_NonStandard .info_FilePath").val(fileName_add);
        			$(".update_NonStandard .info_FilePath").attr("title",fileName_add);
        			$("span.isUpload").text("上传成功！");
        			$.MsgBox_Unload.Alert("提示","读取报价失败，请手输价格");
        			setTimeout(function(){
        				$(".dropFileTit span").trigger("click");
        			},1000);
        		}else{
        			$("span.isUpload").text("操作有误！");
        		}
        	}else{
        		if(IsFileUpload ==1){
        			$(".add_NonStandard .info_FilePath").val(fileName_add);
        			$(".add_NonStandard .info_FilePath").attr("title",fileName_add);
        			$(".add_NonStandard .info_QuoteTotal").val(data);
        			$("span.isUpload").text("上传成功！");
        			setTimeout(function(){
        				$(".dropFileTit span").trigger("click");
        			},1000);
        		}else if(IsFileUpload==2){
        			$(".update_NonStandard .info_FilePath").val(fileName_add);
        			$(".update_NonStandard .info_FilePath").attr("title",fileName_add);
        			$(".update_NonStandard .info_QuoteTotal").val(data);
        			$("span.isUpload").text("上传成功！");
        			setTimeout(function(){
        				$(".dropFileTit span").trigger("click");
        			},1000);
        		}else{
        			$("span.isUpload").text("操作有误！");
        		}
        	}
        },
        error:function(){
        	$("span.isUpload").text("");
        	$(".progressIn").css("width","30px");
        	$(".progressIn").text("0%");
            $.MsgBox_Unload.Alert("上传提示","网络繁忙！上传失败！");
        }
    });                             
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
	$(".add_NonStandard_body_in [class^='info_']").each(function(){
		// if($(this).prop("tagName")=="SELECT"||$(this).prop("tagName")=="select"){
		// 	$(this).val("0");
		// }else{
		// 	$(this).val("");
		// }
		$(this).val("");
		if($(this).is(".info_FilePath")){
			$(this).attr("title","");
		}
	});
	$(".bg_cover").slideDown(350);
	$(".add_NonStandard").slideDown(350);
	IsFileUpload = 1;
});

$(document).on("click",".update_td",function(){
	// jQuery.data($(this), "customerId");
	updateSubmitObj.ID = Number($(this).data("iid")).toString();
	updateSubmitObj.CustomerID = Number($(this).data("customerId")).toString();
	var that = $(this);
	$(".update_NonStandard_body_in [class^='info_']").each(function(){
		var subClassName = $(this).attr("class").split(" ")[0].replace("info_","hastd_");
		var oldVal = that.siblings("."+subClassName).text();
		var newVal = globalDataHandle(oldVal,"");
		$(this).val(newVal);
		if($(this).is(".info_FilePath")){
			$(this).attr("title",newVal);
		}
	});
	$(".bg_cover").slideDown(350);
	$(".update_NonStandard").slideDown(350);
	IsFileUpload = 2;
});

// 关闭
$("#NonStandard_addclose, .add_NonStandard_tit_r").on("click",function(){
	for(var k in addSubmitObj){
		addSubmitObj[k] = null;
	}
	$(".bg_cover").slideUp(350);
	$(".add_NonStandard").slideUp(350);
	IsFileUpload = 0;
	// $(".add_NonStandard .info_FilePath").attr("value","");
});

$("#NonStandard_updateclose, .update_NonStandard_tit_r").on("click",function(){
	for(var k in updateSubmitObj){
		updateSubmitObj[k] = null;
	}
	$(".bg_cover").slideUp(350);
	$(".update_NonStandard").slideUp(350);
	IsFileUpload = 0;
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
                        str+='<option value="'+newData[i].CustomerName+'" text="'+newData[i].ID+'" contact="'+newData[i].Contact+'" contactinfo="'+newData[i].ContactInfo1+'" area="'+newData[i].Area+'" city="'+newData[i].City+'" email="'+newData[i].Email+'" department="'+newData[i].CustomerDepartment+'">'+newData[i].CustomerName+'&nbsp;:&nbsp;'+newData[i].Contact+'</option>';
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
                        str+='<option value="'+newData[i].CustomerName+'" text="'+newData[i].ID+'" contact="'+newData[i].Contact+'" contactinfo="'+newData[i].ContactInfo1+'" area="'+newData[i].Area+'" city="'+newData[i].City+'" email="'+newData[i].Email+'" department="'+newData[i].CustomerDepartment+'">'+newData[i].CustomerName+'&nbsp;:&nbsp;'+newData[i].Contact+'</option>';
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
    $("#addModelSel").fadeOut(200);
    $("#updateModelSel").fadeOut(200);
});

$(document).on("click", '#add_CustomerName option', function(e) {
	e.stopPropagation();
    $('.add_NonStandard .info_CustomerName').val($('#add_CustomerName').val());
    addSubmitObj.CustomerID = $(this).attr("text");
    // $('.add_keysight .info_customer_attn').val($(this).attr("contact"));
    $('.add_NonStandard .info_Contact').val($(this).attr("contact"));
    $('.add_NonStandard .info_CustomerDepartment').val($(this).attr("department"));
    $('#add_CustomerName').hide();
});
$(document).on("click", '#update_CustomerName option', function(e) {
	e.stopPropagation();
    $('.update_NonStandard .info_CustomerName').val($('#update_CustomerName').val());
    updateSubmitObj.CustomerID = $(this).attr("text");
    $('.update_NonStandard .info_Contact').val($(this).attr("contact"));
    $('.update_NonStandard .info_CustomerDepartment').val($(this).attr("department"));
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
	}
	console.log("add我执行了吗");
	console.log(addSubmitObj);
	$.ajax({
		type:"POST",
		url:"OriginalQuotation",
		dataType:'json',
		data:addSubmitObj,
		success:function(data){
			console.log(typeof data);
			if(data==true){
				$.MsgBox_Unload.Alert("提示","添加成功！");
				$("#NonStandard_addclose").trigger("click");
			}else if(data==false){
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
	}
	console.log("update我执行了吗");
	console.log(updateSubmitObj);
	$.ajax({
		type:"POST",
		url:"OriginalQuotation",
		dataType:'json',
		data:updateSubmitObj,
		success:function(data){
			console.log(typeof data);
			if(data==true){
				$.MsgBox_Unload.Alert("提示","修改成功！");
				$("#NonStandard_updateclose").trigger("click");
			}else if(data==false){
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

// // 发布按钮事件
// $(document).on("click",".isPublished input",function(){
//     $(".bg_cover").slideDown(200);
//     $(".publish_div").slideDown(200);
//     $(".publish_div").attr("value",$(this).parent().siblings("td.update_td").data("iid"));
//     if($(this).val()=="未发布"){
//         $(".publish_div_body").text("是否发布？");
//         var str1 = '<input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" id="publish_yes" value="是">'+
//         '<input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" id="publish_no" value="否">';
//         $(".publish_div_foot_in").html(str1);
//     }else if($(this).val()=="已发布"){
//         $(".publish_div_body").text("您已发布过！");
//         var str2 = '<input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" id="has_publish" value="好的">';
//         $(".publish_div_foot_in").html(str2);
//     }
// });

// // 是 否 好的
// $(document).on("click","#publish_no, #has_publish",function(){
//     $(".publish_div").slideUp(200);
//     $(".bg_cover").slideUp(200);
// });
// $(document).on("click","#publish_yes",function(){
//     var email_curPage = Number($(".pageInfo #currentPage").text());
//     var email_data = {};
//     var ID = $(".publish_div").attr("value");
//     var currentTr = $(".m_table tbody tr").find("td.update_td[data-iid='"+ID+"']").parent();
//     var ProjectName = currentTr.find(".hastd_ProjectName").text();
//     var ProjectStage = currentTr.find(".hastd_ProjectStage").text();
//     var ResponsibleMan = currentTr.find(".hastd_ResponsibleMan").text();
//     var PublishDate = currentTr.find(".hastd_PublishDate").text();
//     var StageExpectedDate = currentTr.find(".hastd_StageExpectedDate").text();
//     var ProjectProgress = currentTr.find(".hastd_ProjectProgress").text();
    
//     if(PublishDate == "--"||PublishDate==""||PublishDate=="0000-00-00"){
//         $.MsgBox_Unload.Alert("提示","发布时间为空，请检查");
//         $("#publish_no").trigger("click");
//         return;
//     }else if(StageExpectedDate == "--"||StageExpectedDate==""||StageExpectedDate=="0000-00-00"){
//         $.MsgBox_Unload.Alert("提示","阶段预计时间为空，请检查");
//         $("#publish_no").trigger("click");
//         return;
//     }else{
//         email_data.ID = ID;
//         email_data.ProjectName = ProjectName;
//         email_data.ProjectStage = ProjectStage;
//         email_data.ResponsibleMan = ResponsibleMan;
//         email_data.PublishDate = PublishDate;
//         email_data.StageExpectedDate = StageExpectedDate;
//         email_data.ProjectProgress = ProjectProgress;
//         $.ajax({
//             type:'GET',
//             url:'NonStandardOperate',
//             dataType:'text',
//             data:email_data,
//             success: function(data){
//                 console.log(data);
//                 console.log(typeof data);
//                 $.MsgBox_Unload.Alert("提示",data);
//             },
//             error: function(){
//                 $.MsgBox_Unload.Alert("提示","服务器繁忙！");
//             },
//             complete: function(XMLHttpRequest, textStatus){
// 			    if(textStatus=="success"){
// 			    	if(hasSearch == 0){
// 			    		tableRenderAjax(email_curPage);
// 			    	}else if(hasSearch == 1){
// 			    		searchGetParam(email_curPage);
// 			    	}
// 	    			$(".publish_div").fadeOut(200);
//                 	$(".bg_cover").fadeOut(200);
// 			    }
//             }
//         });
//     }
// });

$("#FilePath1").on("click",function(){
	$(".dropFileBox").slideDown(200);
	$(".cover_bg2").slideDown(200);
});
$("#FilePath2").on("click",function(){
	$(".dropFileBox").slideDown(200);
	$(".cover_bg2").slideDown(200);
});

// 上传报价单
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
	}else if(fileExt1!=''&&fileExt2!=''){
		if(fileExt1["0"]!="pdf"||fileExt2["0"]!="pdf"){
			$.MsgBox_Unload.Alert("上传提示","文件后缀名错误，请上传pdf");
			return false;
		}
	}
	if(judgeFile && judgeFile2){
		uploadFiles();
	}else{
		$.MsgBox_Unload.Alert("上传提示","请检查是否选择或更换了文件");
	}
});

// 点击浏览
$("#serFinRepUpload").on("change",function(){
	$("span.isUpload").text("");
	$(".progressIn").css("width","30px");
	$(".progressIn").text("0%");
	console.log("文件上传改变值"+$(this).val());
	var newFileName1 = $(this).val().indexOf("\\fakepath\\")>-1?$(this).val().split("\\fakepath\\")[1]:$(this).val().split("\\").pop();
	console.log("赋给input的值"+newFileName1);
	$("input.serFinRepUploadName").val(newFileName1);
	$("input.serFinRepUploadName").attr("title",newFileName1);
});

// 关闭上传
$(".dropFileTit span").on("click",function(){
	$(".cover_bg2").slideUp(200);
	$(".dropFileBox").slideUp(400);
	$("span.isUpload").text("");
	$(".progressIn").css("width","30px");
	$(".progressIn").text("0%");
	$("input.serFinRepUploadName").val("");
	$("input.serFinRepUploadName").attr("title","");
	$("#serFinRepUpload").val("");
});

// 服务报告点击下载
$(document).on("click",".hastd_FilePath",function(e){
	e.stopPropagation();
	var fileName = $(this).text().trim();
	if(fileName==""||fileName=="--"){
		$.MsgBox_Unload.Alert("下载提示","无文件可下载");
		return false;
	}
	var baseUrl = window.location.href.split(globalProjectName)[0];
	// window.location.href = url+"LogisticsFile/File/"+fileName;
	window.open(baseUrl+"LogisticsFile/File/"+fileName);
	console.log("服务报告下载"+baseUrl+"LogisticsFile/File/"+fileName);
});

// 下拉箭头
$(".add_NonStandard .down_span").on("click",function(e){
	e.stopPropagation();
	$("#addModelSel").fadeIn(200);
});
$(".update_NonStandard .down_span").on("click",function(e){
	e.stopPropagation();
	$("#updateModelSel").fadeIn(200);
});

$("#addModelSel option").on("click",function(e){
	$(".add_NonStandard .info_Model").val($("#addModelSel").val());
	e.stopPropagation();
	$("#addModelSel").fadeOut(200);
});

$("#updateModelSel option").on("click",function(e){
	$(".update_NonStandard .info_Model").val($("#updateModelSel").val());
	e.stopPropagation();
	$("#updateModelSel").fadeOut(200);
});

// 表格悬浮
$(document).on("mouseenter",".m_table tbody td",function(){
	$(this).parent().css({"border":"2px solid red"});
});
$(document).on("mouseleave",".m_table tbody td",function(){
	$(this).parent().css({"border":"1px solid transparent"});
});