// 定义状态集
var mailSettingState = new Object();
mailSettingState.insertFlag = false;
mailSettingState.allMailList = [];
mailSettingState.insertDepartFlag = false;
mailSettingState.hasRequestDepartObj = {};
mailSettingState.curSingleDepartMailStr = null;
mailSettingState.hasRequestDefaultMailObj = new Object();
// mailSettingState.hasRequestDefaultMail = false;
// mailSettingState.hasRequestDefaultMailToList = null;
// mailSettingState.hasRequestDefaultMailCopyList = null;
mailSettingState.getClassify = null;
mailSettingState.setClassify = null;

// 管理员可见
$(function(){
	if($("h5#GAdmin_flag").length){
		$("input.mailSetting_open").show();
		$(".VersionManagement_div_tit_l").addClass("hasAdmin");
	}else{
		$("input.mailSetting_open").hide();
		$(".VersionManagement_div_tit_l").removeClass("hasAdmin");
	}
});

function toccListRender(){
	var item = mailSettingState.hasRequestDefaultMailObj[mailSettingState.getClassify];
	if(item.ToList == null && item.CopyList == null){
		console.warn("mailSettingState.hasRequestDefaultMailToList或mailSettingState.hasRequestDefaultMailCopyList为空！");
		return false;
	}
	var str1 = '', str2 = '';
	item.ToList.map(function(currentValue, index, arr){
		str1+='<li title="'+currentValue+'">'+currentValue+'</li>';
	});
	$(".to_list_cont ul").empty().append(str1);
	item.CopyList.map(function(currentValue, index, arr){
		str2+='<li title="'+currentValue+'">'+currentValue+'</li>';
	});
	$(".cc_list_cont ul").empty().append(str2);
}

// 邮箱设置打开
$(document).on("click","input.mailSetting_open, .mailSet_open",function(){
	$(".mailSetting_div_cover, .mailSetting_div").slideDown(250);
	var iiThat = $(this);
	var change_title_str;
	if(iiThat.is(".mailSet_open")){
		mailSettingState.getClassify = $("select#projectname_select").val();
		mailSettingState.setClassify = $("select#projectname_select").val();
		change_title_str = "设置邮件收件人/抄送人--"+$("select#projectname_select>option:checked").text();
	}else{
		mailSettingState.getClassify = $("#mailSetting_classify").data("iiclassify").toString();
		mailSettingState.setClassify = $("#mailSetting_classify").data("iiclassify").toString();
		change_title_str = "设置邮件收件人/抄送人";
	}
	if(!mailSettingState.insertFlag){
		$("div.mailSetting_div").empty().load('html/modules/software/mailSetting.html #mailSetting_con_id',function(response,status,xhr){
			// console.log(response);
			// console.log(status); // success
			// console.log(xhr);
			if(status == "success"){
				$(".change_title_legend").text(change_title_str);
				mailSettingState.insertFlag = true;
				$("div.select_btn_grop>button:eq(0)").addClass("active").siblings().removeClass("active");
				globalGetAllEmail(function(data){
					data.map(function(currentValue, index, arr){
						if(index > 0){
							mailSettingState.allMailList.push(currentValue.Email);
						}
					});
					// Awesomplete
					$("#singlemate_row_to, #singlemate_row_cc").each(function(i, el){
						new Awesomplete(el, {
							list: mailSettingState.allMailList,
							minChars: 1,
							maxItems: 15,
							autoFirst: true,
							filter: function(text, input) {
								return Awesomplete.FILTER_CONTAINS(text, input.match(/[^;]*$/)[0]);
							},

							item: function(text, input) {
								return Awesomplete.ITEM(text, input.match(/[^;]*$/)[0]);
							},

							replace: function(text) {
								var before = this.input.value.match(/^.+;\s*|/)[0];
								this.input.value = before + text + "; ";
							}
						});
					});
				}, null);
				// 填充默认收件人抄送人
				if(mailSettingState.hasRequestDefaultMailObj[mailSettingState.getClassify] === undefined){
					$.ajax({
						type: "GET",
						url: "MailConfig",
						data: {
							Page: mailSettingState.getClassify
						},
						dataType: "json",
						success: function (data) {
							mailSettingState.hasRequestDefaultMailObj[mailSettingState.getClassify] = {};
							var item = mailSettingState.hasRequestDefaultMailObj[mailSettingState.getClassify];
							item.flag = true;
							var ToList = data[1].ToList;
							var CopyList = data[1].CopyList;
							if(ToList == undefined || ToList == "--"){
								ToList = "";
							}
							if(CopyList == undefined || CopyList == "--"){
								CopyList = "";
							}
							var ToListArr = ToList.split(";");
							ToListArr = $.grep(ToListArr,function(currentValue,index){
							    return currentValue != "";
							});
							var CopyListArr = CopyList.split(";");
							CopyListArr = $.grep(CopyListArr,function(currentValue,index){
							    return currentValue != "";
							});
							item.ToList = ToListArr;
							item.CopyList = CopyListArr;
							toccListRender();
						},
						error: function () {
						    $.MsgBox_Unload.Alert("提示", "服务器繁忙，读取已设置邮箱失败！");
						}
					});
				}else{
					toccListRender();
				}
			}else{
				$.MsgBox_Unload.Alert("加载提示","服务器繁忙！");
			}
		});
	}else{
		$("div.select_btn_grop>button:eq(0)").addClass("active").siblings().removeClass("active");
		$(".singlemate_row").show();
		$(".multiplemate_row").hide();
		$("#singlemate_row_to, #singlemate_row_cc").val("");
		$(".change_title_legend").text(change_title_str);
		// 填充默认收件人抄送人
		if(mailSettingState.hasRequestDefaultMailObj[mailSettingState.getClassify] === undefined){
			$.ajax({
				type: "GET",
				url: "MailConfig",
				data: {
					Page: mailSettingState.getClassify
				},
				dataType: "json",
				success: function (data) {
					mailSettingState.hasRequestDefaultMailObj[mailSettingState.getClassify] = {};
					var item = mailSettingState.hasRequestDefaultMailObj[mailSettingState.getClassify];
					item.flag = true;
					var ToList = data[1].ToList;
					var CopyList = data[1].CopyList;
					if(ToList == undefined || ToList == "--"){
						ToList = "";
					}
					if(CopyList == undefined || CopyList == "--"){
						CopyList = "";
					}
					var ToListArr = ToList.split(";");
					ToListArr = $.grep(ToListArr,function(currentValue,index){
					    return currentValue != "";
					});
					var CopyListArr = CopyList.split(";");
					CopyListArr = $.grep(CopyListArr,function(currentValue,index){
					    return currentValue != "";
					});
					item.ToList = ToListArr;
					item.CopyList = CopyListArr;
					toccListRender();
				},
				error: function () {
				    $.MsgBox_Unload.Alert("提示", "服务器繁忙，读取已设置邮箱失败！");
				}
			});
		}else{
			toccListRender();
		}
	}
});

// 邮箱设置关闭
$(document).on("click","#mailSetting_cancel, .mailSetting_con_tit>span",function(){
	$(".mailSetting_div_cover, .mailSetting_div").slideUp(250, function(){
		mailSettingState.getClassify = null;
		mailSettingState.setClassify = null;
	});
});
// 选择匹配类型
$(document).on("click","div.select_btn_grop>button",function(){
	$(this).addClass("active").siblings().removeClass("active");
	var iclassName = $(this).data("iselect").toString()+"_row";
	$(".mailSetting_con_body_in div.toggle_row:not(."+iclassName+")").slideUp(200,function(){
		$(".mailSetting_con_body_in ."+iclassName).slideDown(200);
		if(iclassName == "multiplemate_row"){
			if(!mailSettingState.insertDepartFlag){
				var str = '<option value="" disabled>请选择</option><option value="所有">所有</option>';
				globalEouluAllDepart.map(function(currentValue, index, arr){
					str+='<option value="'+currentValue+'">'+currentValue+'</option>';
				});
				$("#multiplemate_row_depart").append(str);
				mailSettingState.insertDepartFlag = true;
			}
			$("#multiplemate_row_depart").val("");
		}
	});
});

// 单个匹配，添加至备选区域
$(document).on("click","div.singlemate_row .to_add_in, div.singlemate_row .cc_add_in",function(){
	var iTextareaDOM, insertDOM;
	if($(this).is(".to_add_in")){
		iTextareaDOM = $("#singlemate_row_to");
		insertDOM = $(".to_list_cont ul");
	}else if($(this).is(".cc_add_in")){
		iTextareaDOM = $("#singlemate_row_cc");
		insertDOM = $(".cc_list_cont ul");
	}
	var iValArr = iTextareaDOM.val().trim().replace(/;\s*/g,";;").split(";;");
	iValArr = $.grep(iValArr,function(currentValue,index){
	    return currentValue != "";
	});
	var iValArrNew = globalArrStrUnique(iValArr);
	var str = '';
	iValArrNew.map(function(currentValue, index, arr){
		str+='<li title="'+currentValue+'">'+currentValue+'</li>';
	});
	insertDOM.append(str);
	iTextareaDOM.val("");
});

// 单个匹配，li选中事件
$(document).on("click",".to_list_cont ul>li, .cc_list_cont ul>li",function(){
	$(this).toggleClass("hasSelect");
});

// 单个匹配，li删除事件
$(document).on("click",".to_list_tit button, .cc_list_tit button",function(){
	var parentDOM;
	if($(this).is(".to_list_tit button")){
		parentDOM = $(".to_list_cont ul");
	}else if($(this).is(".cc_list_tit button")){
		parentDOM = $(".cc_list_cont ul");
	}
	var childDOM = parentDOM.children("li.hasSelect");
	if(childDOM.length == 0){
		$.MsgBox_Unload.Alert("删除提示","未选中！");
		return false;
	}
	childDOM.remove();
});

// 部门匹配，change事件
$(document).on("change","#multiplemate_row_depart",function(){
	var iVal = $(this).val();
	if(!iVal){
		console.log("部门未选中！");
	}else if(iVal == "所有"){
		if(mailSettingState.allMailList.length == 0){
			$.MsgBox_Unload.Alert("选择所有提示","所有邮箱初始化失败！请刷新");
			return false;
		}
		var str3 = '';
		mailSettingState.allMailList.map(function(currentValue, index, arr){
			str3+='<li title="'+currentValue+'">'+currentValue+'</li>';
		});
		mailSettingState.curSingleDepartMailStr = str3;
	}else{
		if(Object.getOwnPropertyNames(mailSettingState.hasRequestDepartObj).indexOf(iVal)>-1){
			var str1 = '';
			mailSettingState.hasRequestDepartObj[iVal].map(function(currentValue, index, arr){
				str1+='<li title="'+currentValue+'">'+currentValue+'</li>';
			});
			mailSettingState.curSingleDepartMailStr = str1;
		}else{
			globalGetStaffAllInfoByDepart(iVal,function(data){
				var item = [], str2 = '';
				data.map(function(currentValue, index, arr){
					if(index > 0){
						item.push(currentValue.StaffMail);
						str2+='<li title="'+currentValue.StaffMail+'">'+currentValue.StaffMail+'</li>';
					}
				});
				mailSettingState.hasRequestDepartObj[iVal] = item;
				mailSettingState.curSingleDepartMailStr = str2;
			},null);
		}
	}
	
});

// 部门匹配，添加至等待提交列表
$(document).on("click",".add_to_cc_list>input",function(){
	if(!mailSettingState.curSingleDepartMailStr){
		$.MsgBox_Unload.Alert("添加提示","未选中部门！");
		return false;
	}
	var insertDOM;
	if($(this).val() == "添加到收件人"){
		insertDOM = $(".to_list_cont ul");
	}else if($(this).val() == "添加到抄送人"){
		insertDOM = $(".cc_list_cont ul");
	}
	insertDOM.append(mailSettingState.curSingleDepartMailStr);
	mailSettingState.curSingleDepartMailStr = null;
	$("#multiplemate_row_depart").val("");
});

// 提交设置
$(document).on("click","#mailSetting_submit",function(){
	var toLiDOM = $(".to_list_cont ul>li");
	var ccLiDOM = $(".cc_list_cont ul>li");
	if(toLiDOM.length == 0 && ccLiDOM.length == 0){
		$.MsgBox_Unload.Alert("提交提示","收件人抄送人都未选！");
		return false;
	}
	var ToList = '', CopyList = '';
	var ToListArr = [], CopyListArr = [];
	toLiDOM.each(function(){
		ToListArr.push($(this).attr("title"));
	});
	ccLiDOM.each(function(){
		CopyListArr.push($(this).attr("title"));
	});
	ToListArr = $.grep(ToListArr,function(currentValue,index){
	    return currentValue != "";
	});
	CopyListArr = $.grep(CopyListArr,function(currentValue,index){
	    return currentValue != "";
	});

	ToListArr.map(function(currentValue, index, arr){
		ToList+=currentValue;
		ToList+=';';
	});
	CopyListArr.map(function(currentValue, index, arr){
		CopyList+=currentValue;
		CopyList+=';';
	});

	var iThat = $(this);
	// var Page = iThat.parents("#mailSetting_classify").data("iiclassify").toString();
	$.ajax({
		type: "POST",
		url: "MailConfig",
		data: {
			Page: mailSettingState.setClassify,
			ToList: ToList,
			CopyList: CopyList
		},
		dataType: "text",
		beforeSend: function(XMLHttpRequest){
			iThat.css("cursor","not-allowed").prop("disabled","disabled");
		},
		complete: function(XMLHttpRequest, textStatus){
		    // if(textStatus=='success'){
		    // }else if(textStatus=='error'){
		    // }else if(textStatus=='timeout'){
		    //     var xmlhttp = window.XMLHttpRequest ? new window.XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHttp");  
		    //     xmlhttp.abort();
		    // }
		    iThat.css("cursor","pointer").prop("disabled",false);
		},
		success: function (data) {
		    $.MsgBox_Unload.Alert("提示", data);
		    if(data.indexOf("设置成功")>-1){
		    	if(mailSettingState.hasRequestDefaultMailObj[mailSettingState.getClassify] === undefined){
		    		mailSettingState.hasRequestDefaultMailObj[mailSettingState.getClassify] = {};
		    	}
		    	var item = mailSettingState.hasRequestDefaultMailObj[mailSettingState.getClassify];
		    	item.ToList = ToListArr;
		    	item.CopyList = CopyListArr;
		    	// mailSettingState.hasRequestDefaultMailToList = ToListArr;
		    	// mailSettingState.hasRequestDefaultMailCopyList = CopyListArr;
		    	$("#mailSetting_cancel").trigger("click");
		    }
		},
		error: function () {
		    $.MsgBox_Unload.Alert("提示", "服务器繁忙，请稍后重试！");
		}
	});
});