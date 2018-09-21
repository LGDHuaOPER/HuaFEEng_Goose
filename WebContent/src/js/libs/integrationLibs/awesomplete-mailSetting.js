// Awesomplete - Lea Verou - MIT license
!function(){function t(t){var e=Array.isArray(t)?{label:t[0],value:t[1]}:"object"==typeof t&&"label"in t&&"value"in t?t:{label:t,value:t};this.label=e.label||e.value,this.value=e.value}function e(t,e,i){for(var n in e){var s=e[n],r=t.input.getAttribute("data-"+n.toLowerCase());"number"==typeof s?t[n]=parseInt(r):!1===s?t[n]=null!==r:s instanceof Function?t[n]=null:t[n]=r,t[n]||0===t[n]||(t[n]=n in i?i[n]:s)}}function i(t,e){return"string"==typeof t?(e||document).querySelector(t):t||null}function n(t,e){return o.call((e||document).querySelectorAll(t))}function s(){n("input.awesomplete").forEach(function(t){new r(t)})}var r=function(t,n){var s=this;Awesomplete.count=(Awesomplete.count||0)+1,this.count=Awesomplete.count,this.isOpened=!1,this.input=i(t),this.input.setAttribute("autocomplete","off"),this.input.setAttribute("aria-owns","awesomplete_list_"+this.count),this.input.setAttribute("role","combobox"),n=n||{},e(this,{minChars:2,maxItems:10,autoFirst:!1,data:r.DATA,filter:r.FILTER_CONTAINS,sort:!1!==n.sort&&r.SORT_BYLENGTH,item:r.ITEM,replace:r.REPLACE},n),this.index=-1,this.container=i.create("div",{className:"awesomplete",around:t}),this.ul=i.create("ul",{hidden:"hidden",role:"listbox",id:"awesomplete_list_"+this.count,inside:this.container}),this.status=i.create("span",{className:"visually-hidden",role:"status","aria-live":"assertive","aria-atomic":!0,inside:this.container,textContent:0!=this.minChars?"Type "+this.minChars+" or more characters for results.":"Begin typing for results."}),this._events={input:{input:this.evaluate.bind(this),blur:this.close.bind(this,{reason:"blur"}),keydown:function(t){var e=t.keyCode;s.opened&&(13===e&&s.selected?(t.preventDefault(),s.select()):27===e?s.close({reason:"esc"}):38!==e&&40!==e||(t.preventDefault(),s[38===e?"previous":"next"]()))}},form:{submit:this.close.bind(this,{reason:"submit"})},ul:{mousedown:function(t){t.preventDefault()},click:function(t){var e=t.target;if(e!==this){for(;e&&!/li/i.test(e.nodeName);)e=e.parentNode;e&&0===t.button&&(t.preventDefault(),s.select(e,t.target))}}}},i.bind(this.input,this._events.input),i.bind(this.input.form,this._events.form),i.bind(this.ul,this._events.ul),this.input.hasAttribute("list")?(this.list="#"+this.input.getAttribute("list"),this.input.removeAttribute("list")):this.list=this.input.getAttribute("data-list")||n.list||[],r.all.push(this)};r.prototype={set list(t){if(Array.isArray(t))this._list=t;else if("string"==typeof t&&t.indexOf(",")>-1)this._list=t.split(/\s*,\s*/);else if((t=i(t))&&t.children){var e=[];o.apply(t.children).forEach(function(t){if(!t.disabled){var i=t.textContent.trim(),n=t.value||i,s=t.label||i;""!==n&&e.push({label:s,value:n})}}),this._list=e}document.activeElement===this.input&&this.evaluate()},get selected(){return this.index>-1},get opened(){return this.isOpened},close:function(t){this.opened&&(this.ul.setAttribute("hidden",""),this.isOpened=!1,this.index=-1,this.status.setAttribute("hidden",""),i.fire(this.input,"awesomplete-close",t||{}))},open:function(){this.ul.removeAttribute("hidden"),this.isOpened=!0,this.status.removeAttribute("hidden"),this.autoFirst&&-1===this.index&&this.goto(0),i.fire(this.input,"awesomplete-open")},destroy:function(){i.unbind(this.input,this._events.input),i.unbind(this.input.form,this._events.form);var t=this.container.parentNode;t.insertBefore(this.input,this.container),t.removeChild(this.container),this.input.removeAttribute("autocomplete"),this.input.removeAttribute("aria-autocomplete");var e=r.all.indexOf(this);-1!==e&&r.all.splice(e,1)},next:function(){var t=this.ul.children.length;this.goto(this.index<t-1?this.index+1:t?0:-1)},previous:function(){var t=this.ul.children.length,e=this.index-1;this.goto(this.selected&&-1!==e?e:t-1)},goto:function(t){var e=this.ul.children;this.selected&&e[this.index].setAttribute("aria-selected","false"),this.index=t,t>-1&&e.length>0&&(e[t].setAttribute("aria-selected","true"),this.status.textContent=e[t].textContent+", list item "+(t+1)+" of "+e.length,this.input.setAttribute("aria-activedescendant",this.ul.id+"_item_"+this.index),this.ul.scrollTop=e[t].offsetTop-this.ul.clientHeight+e[t].clientHeight,i.fire(this.input,"awesomplete-highlight",{text:this.suggestions[this.index]}))},select:function(t,e){if(t?this.index=i.siblingIndex(t):t=this.ul.children[this.index],t){var n=this.suggestions[this.index];i.fire(this.input,"awesomplete-select",{text:n,origin:e||t})&&(this.replace(n),this.close({reason:"select"}),i.fire(this.input,"awesomplete-selectcomplete",{text:n}))}},evaluate:function(){var e=this,i=this.input.value;i.length>=this.minChars&&this._list&&this._list.length>0?(this.index=-1,this.ul.innerHTML="",this.suggestions=this._list.map(function(n){return new t(e.data(n,i))}).filter(function(t){return e.filter(t,i)}),!1!==this.sort&&(this.suggestions=this.suggestions.sort(this.sort)),this.suggestions=this.suggestions.slice(0,this.maxItems),this.suggestions.forEach(function(t,n){e.ul.appendChild(e.item(t,i,n))}),0===this.ul.children.length?(this.status.textContent="No results found",this.close({reason:"nomatches"})):(this.open(),this.status.textContent=this.ul.children.length+" results found")):(this.close({reason:"nomatches"}),this.status.textContent="No results found")}},r.all=[],r.FILTER_CONTAINS=function(t,e){return RegExp(i.regExpEscape(e.trim()),"i").test(t)},r.FILTER_STARTSWITH=function(t,e){return RegExp("^"+i.regExpEscape(e.trim()),"i").test(t)},r.SORT_BYLENGTH=function(t,e){return t.length!==e.length?t.length-e.length:t<e?-1:1},r.ITEM=function(t,e,n){return i.create("li",{innerHTML:""===e.trim()?t:t.replace(RegExp(i.regExpEscape(e.trim()),"gi"),"<mark>$&</mark>"),"aria-selected":"false",id:"awesomplete_list_"+this.count+"_item_"+n})},r.REPLACE=function(t){this.input.value=t.value},r.DATA=function(t){return t},Object.defineProperty(t.prototype=Object.create(String.prototype),"length",{get:function(){return this.label.length}}),t.prototype.toString=t.prototype.valueOf=function(){return""+this.label};var o=Array.prototype.slice;i.create=function(t,e){var n=document.createElement(t);for(var s in e){var r=e[s];if("inside"===s)i(r).appendChild(n);else if("around"===s){var o=i(r);o.parentNode.insertBefore(n,o),n.appendChild(o)}else s in n?n[s]=r:n.setAttribute(s,r)}return n},i.bind=function(t,e){if(t)for(var i in e){var n=e[i];i.split(/\s+/).forEach(function(e){t.addEventListener(e,n)})}},i.unbind=function(t,e){if(t)for(var i in e){var n=e[i];i.split(/\s+/).forEach(function(e){t.removeEventListener(e,n)})}},i.fire=function(t,e,i){var n=document.createEvent("HTMLEvents");n.initEvent(e,!0,!0);for(var s in i)n[s]=i[s];return t.dispatchEvent(n)},i.regExpEscape=function(t){return t.replace(/[-\\^$*+?.()|[\]{}]/g,"\\$&")},i.siblingIndex=function(t){for(var e=0;t=t.previousElementSibling;e++);return e},"undefined"!=typeof Document&&("loading"!==document.readyState?s():document.addEventListener("DOMContentLoaded",s)),r.$=i,r.$$=n,"undefined"!=typeof self&&(self.Awesomplete=r),"object"==typeof module&&module.exports&&(module.exports=r)}();
//# sourceMappingURL=awesomplete.min.js.map

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