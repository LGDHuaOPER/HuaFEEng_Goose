// colResizable 1.6 - a jQuery plugin by Alvaro Prieto Lauroba http://www.bacubacu.com/colresizable/

!function(t){var e,i=t(document),r=t("head"),o=null,s={},d=0,n="id",a="px",l="JColResizer",c="JCLRFlex",f=parseInt,h=Math,p=navigator.userAgent.indexOf("Trident/4.0")>0;try{e=sessionStorage}catch(g){}r.append("<style type='text/css'>  .JColResizer{table-layout:fixed;} .JColResizer > tbody > tr > td, .JColResizer > tbody > tr > th{overflow:hidden;padding-left:0!important; padding-right:0!important;}  .JCLRgrips{ height:0px; position:relative;} .JCLRgrip{margin-left:-5px; position:absolute; z-index:5; } .JCLRgrip .JColResizer{position:absolute;background-color:red;filter:alpha(opacity=1);opacity:0;width:10px;height:100%;cursor: e-resize;top:0px} .JCLRLastGrip{position:absolute; width:1px; } .JCLRgripDrag{ border-left:1px dotted black;	} .JCLRFlex{width:auto!important;} .JCLRgrip.JCLRdisabledGrip .JColResizer{cursor:default; display:none;}</style>");var u=function(e,i){var o=t(e);if(o.opt=i,o.mode=i.resizeMode,o.dc=o.opt.disabledColumns,o.opt.disable)return w(o);var a=o.id=o.attr(n)||l+d++;o.p=o.opt.postbackSafe,!o.is("table")||s[a]&&!o.opt.partialRefresh||("e-resize"!==o.opt.hoverCursor&&r.append("<style type='text/css'>.JCLRgrip .JColResizer:hover{cursor:"+o.opt.hoverCursor+"!important}</style>"),o.addClass(l).attr(n,a).before('<div class="JCLRgrips"/>'),o.g=[],o.c=[],o.w=o.width(),o.gc=o.prev(),o.f=o.opt.fixed,i.marginLeft&&o.gc.css("marginLeft",i.marginLeft),i.marginRight&&o.gc.css("marginRight",i.marginRight),o.cs=f(p?e.cellSpacing||e.currentStyle.borderSpacing:o.css("border-spacing"))||2,o.b=f(p?e.border||e.currentStyle.borderLeftWidth:o.css("border-left-width"))||1,s[a]=o,v(o))},w=function(t){var e=t.attr(n),t=s[e];t&&t.is("table")&&(t.removeClass(l+" "+c).gc.remove(),delete s[e])},v=function(i){var r=i.find(">thead>tr:first>th,>thead>tr:first>td");r.length||(r=i.find(">tbody>tr:first>th,>tr:first>th,>tbody>tr:first>td, >tr:first>td")),r=r.filter(":visible"),i.cg=i.find("col"),i.ln=r.length,i.p&&e&&e[i.id]&&m(i,r),r.each(function(e){var r=t(this),o=-1!=i.dc.indexOf(e),s=t(i.gc.append('<div class="JCLRgrip"></div>')[0].lastChild);s.append(o?"":i.opt.gripInnerHtml).append('<div class="'+l+'"></div>'),e==i.ln-1&&(s.addClass("JCLRLastGrip"),i.f&&s.html("")),s.bind("touchstart mousedown",J),o?s.addClass("JCLRdisabledGrip"):s.removeClass("JCLRdisabledGrip").bind("touchstart mousedown",J),s.t=i,s.i=e,s.c=r,r.w=r.width(),i.g.push(s),i.c.push(r),r.width(r.w).removeAttr("width"),s.data(l,{i:e,t:i.attr(n),last:e==i.ln-1})}),i.cg.removeAttr("width"),i.find("td, th").not(r).not("table th, table td").each(function(){t(this).removeAttr("width")}),i.f||i.removeAttr("width").addClass(c),C(i)},m=function(t,i){var r,o,s=0,d=0,n=[];if(i){if(t.cg.removeAttr("width"),t.opt.flush)return void(e[t.id]="");for(r=e[t.id].split(";"),o=r[t.ln+1],!t.f&&o&&(t.width(o*=1),t.opt.overflow&&(t.css("min-width",o+a),t.w=o));d<t.ln;d++)n.push(100*r[d]/r[t.ln]+"%"),i.eq(d).css("width",n[d]);for(d=0;d<t.ln;d++)t.cg.eq(d).css("width",n[d])}else{for(e[t.id]="";d<t.c.length;d++)r=t.c[d].width(),e[t.id]+=r+";",s+=r;e[t.id]+=s,t.f||(e[t.id]+=";"+t.width())}},C=function(t){t.gc.width(t.w);for(var e=0;e<t.ln;e++){var i=t.c[e];t.g[e].css({left:i.offset().left-t.offset().left+i.outerWidth(!1)+t.cs/2+a,height:t.opt.headerOnly?t.c[0].outerHeight(!1):t.outerHeight(!1)})}},b=function(t,e,i){var r=o.x-o.l,s=t.c[e],d=t.c[e+1],n=s.w+r,l=d.w-r;s.width(n+a),t.cg.eq(e).width(n+a),t.f?(d.width(l+a),t.cg.eq(e+1).width(l+a)):t.opt.overflow&&t.css("min-width",t.w+r),i&&(s.w=n,d.w=t.f?l:d.w)},R=function(e){var i=t.map(e.c,function(t){return t.width()});e.width(e.w=e.width()).removeClass(c),t.each(e.c,function(t,e){e.width(i[t]).w=i[t]}),e.addClass(c)},x=function(t){if(o){var e=o.t,i=t.originalEvent.touches,r=i?i[0].pageX:t.pageX,s=r-o.ox+o.l,d=e.opt.minWidth,n=o.i,l=1.5*e.cs+d+e.b,c=n==e.ln-1,f=n?e.g[n-1].position().left+e.cs+d:l,p=e.f?n==e.ln-1?e.w-l:e.g[n+1].position().left-e.cs-d:1/0;if(s=h.max(f,h.min(p,s)),o.x=s,o.css("left",s+a),c){var g=e.c[o.i];o.w=g.w+s-o.l}if(e.opt.liveDrag){c?(g.width(o.w),!e.f&&e.opt.overflow?e.css("min-width",e.w+s-o.l):e.w=e.width()):b(e,n),C(e);var u=e.opt.onDrag;u&&(t.currentTarget=e[0],u(t))}return!1}},y=function(r){if(i.unbind("touchend."+l+" mouseup."+l).unbind("touchmove."+l+" mousemove."+l),t("head :last-child").remove(),o){if(o.removeClass(o.t.opt.draggingClass),o.x-o.l!=0){var s=o.t,d=s.opt.onResize,n=o.i,a=n==s.ln-1,c=s.g[n].c;a?(c.width(o.w),c.w=o.w):b(s,n,!0),s.f||R(s),C(s),d&&(r.currentTarget=s[0],d(r)),s.p&&e&&m(s)}o=null}},J=function(e){var d=t(this).data(l),n=s[d.t],a=n.g[d.i],c=e.originalEvent.touches;if(a.ox=c?c[0].pageX:e.pageX,a.l=a.position().left,a.x=a.l,i.bind("touchmove."+l+" mousemove."+l,x).bind("touchend."+l+" mouseup."+l,y),r.append("<style type='text/css'>*{cursor:"+n.opt.dragCursor+"!important}</style>"),a.addClass(n.opt.draggingClass),o=a,n.c[d.i].l)for(var f,h=0;h<n.ln;h++)f=n.c[h],f.l=!1,f.w=f.width();return!1},L=function(){for(var t in s)if(s.hasOwnProperty(t)){t=s[t];var i,r=0;if(t.removeClass(l),t.f){for(t.w=t.width(),i=0;i<t.ln;i++)r+=t.c[i].w;for(i=0;i<t.ln;i++)t.c[i].css("width",h.round(1e3*t.c[i].w/r)/10+"%").l=!0}else R(t),"flex"==t.mode&&t.p&&e&&m(t);C(t.addClass(l))}};t(window).bind("resize."+l,L),t.fn.extend({colResizable:function(e){var i={resizeMode:"fit",draggingClass:"JCLRgripDrag",gripInnerHtml:"",liveDrag:!1,minWidth:15,headerOnly:!1,hoverCursor:"e-resize",dragCursor:"e-resize",postbackSafe:!1,flush:!1,marginLeft:null,marginRight:null,disable:!1,partialRefresh:!1,disabledColumns:[],onDrag:null,onResize:null},e=t.extend(i,e);switch(e.fixed=!0,e.overflow=!1,e.resizeMode){case"flex":e.fixed=!1;break;case"overflow":e.fixed=!1,e.overflow=!0}return this.each(function(){u(this,e)})}})}(jQuery);
/*****函数定义与变量定义*****/
var LabPageMainDataObj = {};

var addSubmitObj = new Object();
addSubmitObj.Type = null;
// addSubmitObj.CommodityID = null;
addSubmitObj.Model = null;
addSubmitObj.Description = null;
addSubmitObj.Number = null;
addSubmitObj.Laboratory = null;
addSubmitObj.Picture = null;
addSubmitObj.Document = null;
var updateSubmitObj = new Object();
updateSubmitObj.ID = null;
updateSubmitObj.Type = null;
// updateSubmitObj.CommodityID = null;
updateSubmitObj.Model = null;
updateSubmitObj.Description = null;
updateSubmitObj.Number = null;
updateSubmitObj.Laboratory = null;
updateSubmitObj.Picture = null;
updateSubmitObj.Document = null;
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
LabState.deleteFileID = "0";
LabState.deleteFileLiDOM = null;
LabState.deleteFileNeedRefresh = false;

var thisPageHref = eouluGlobal.S_getCurPageHref();

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

// 获取图片文件blob文件流
// function getImageBlob(){

// }

// 插入缩略图
function insertThumbnails(Laboratory){
	$(".m_table table[data-itable='"+Laboratory+"'] td.hastd_Picture").each(function(){
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
function tableRender(Laboratory, icurPage, pageCounts, data){
	var str = '';
	for(var i =1;i<data.length;i++){
		var iNO = parseInt((icurPage-1)*10 + i);
		var iCADdrawingsStr = "";
		var iCADdrawings = globalDataHandle(data[i].Document, "");
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
			iCADdrawingsStr+="<a tabindex='0' class='btn btn-info' role='button' data-container='body' data-toggle='popover' data-trigger='focus' data-placement='left' title='第"+iNO+"条记录--应用文档' data-content='"+iCADconStr+"'>显示更多...</a>";
		}

	    str+='<tr>'+
	            '<td class="update_td" data-iid="'+data[i].ID+'" data-commodityid="'+data[i].CommodityID+'">'+parseInt((icurPage-1)*10 + i)+'</td>'+
	            '<td class="hastd_Model" title="'+data[i].Model+'">'+data[i].Model+'</td>'+
	            '<td class="hastd_Description" title="'+data[i].Description+'">'+data[i].Description+'</td>'+
	            '<td class="hastd_Number" title="'+data[i].Number+'">'+data[i].Number+'</td>'+
	            '<td class="hastd_Laboratory" title="'+data[i].Laboratory+'">'+data[i].Laboratory+'</td>'+
	            '<td class="hastd_Picture" title="'+data[i].Picture+'"><img src="image/loading/Spinner-1s-50px.gif" alt="产品图片"></td>'+
	            '<td class="detailed_part" title="详细配置"><span class="glyphicon glyphicon-eye-open"></span></td>'+
	            '<td class="Document_part" data-ivalue="'+data[i].Document+'">'+iCADdrawingsStr+'</td>'+
	            '<td class="UpdateTime_part">'+data[i].UpdateTime+'</td>'+
	        '</tr>';
	}
	$(".m_table table[data-itable='"+Laboratory+"'] tbody").empty().append(str);
	$(".m_page #currentPage").text(icurPage);
	$(".m_page #allPage").text(pageCounts);
	$("td.Document_part>a").popover({
		title: "请预览",
		html: true
	});
	resizeTableCol(Laboratory);
	pageStyle(icurPage, pageCounts, "");
	insertThumbnails(Laboratory);
}

// 表格渲染ajax请求
function tableRenderAjax(Laboratory, icurPage, fn){
	$.ajax({
		type: "GET",
		url: "Lab",
		data: {
			Laboratory: Laboratory,
			LoadType: "data",
			CurrentPage: icurPage
		},
		dataType: 'json',
		success: function(res){
			var pageCounts = res.pageCount;
			var data = res.data;
			tableRender(Laboratory, icurPage, pageCounts, data);
			fn && fn(res);
		},
		error:function(){
			$.MsgBox_Unload.Alert("提示","服务器繁忙！");
		},
		complete: function(XMLHttpRequest, textStatus){
		    
		}
	});
}

// 获取主页面数据整体函数
function getPageMainData(immediate, Laboratory, icurPage){
	var searchObj = {
	    Laboratory: Laboratory,
	    icurPage: icurPage
	};

	var searchParam = $.param(searchObj);

	var immediateFn = function(curTime){
		tableRenderAjax(Laboratory, icurPage, function(ires){
			LabPageMainDataObj[searchParam] = {};
			var searchParamVal = LabPageMainDataObj[searchParam];
			searchParamVal[icurPage] = {};
			searchParamVal[icurPage].searchObj = $.extend(true, {}, searchObj);
			searchParamVal[icurPage].expire = curTime + (1000*60*2);
			searchParamVal[icurPage].resp = $.extend(true, {}, ires);
		});
	};

	var expireFalseFn = function(resp){
	    resp = resp || {};
	    var pageCounts = resp.pageCount;
	    var data = resp.data;
	    tableRender(Laboratory, icurPage, pageCounts, data);
	};

	var expireTrueFn = function(curTime){
		tableRenderAjax(Laboratory, icurPage, function(ires){
			var searchParamVal = LabPageMainDataObj[searchParam];
			searchParamVal[icurPage].expire = curTime + (1000*60*2);
			searchParamVal[icurPage].resp = $.extend(true, {}, ires);
		});
	};

	var pageNotExistFn = function(curTime){
		tableRenderAjax(Laboratory, icurPage, function(ires){
			var searchParamVal = LabPageMainDataObj[searchParam];
			searchParamVal[icurPage] = {};
			searchParamVal[icurPage].searchObj = $.extend(true, {}, searchObj);
			searchParamVal[icurPage].expire = curTime + (1000*60*2);
			searchParamVal[icurPage].resp = $.extend(true, {}, ires);
		});
	};

	var searchParamNotExistFn = function(curTime){
		tableRenderAjax(Laboratory, icurPage, function(ires){
			LabPageMainDataObj[searchParam] = {};
			var searchParamVal = LabPageMainDataObj[searchParam];
			searchParamVal[icurPage] = {};
			searchParamVal[icurPage].searchObj = $.extend(true, {}, searchObj);
			searchParamVal[icurPage].expire = curTime + (1000*60*2);
			searchParamVal[icurPage].resp = $.extend(true, {}, ires);
		});
	};

	globalInertLoadNoSearch(immediate, immediateFn, LabPageMainDataObj, searchParam, icurPage, expireFalseFn, expireTrueFn, pageNotExistFn, searchParamNotExistFn);
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
    formData.append("Category","labPicture");
    formData.append("file",$("#serFinRepUpload")[0].files[0]);//append()里面的第一个参数file对应permission/upload里面的参数file         
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
                    // console.log("进度条宽度："+newWidth);
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

//上传文件
function uploadFiles2(classify, Folder, iThat){                                               
    var formData = new FormData();
    formData.enctype="multipart/form-data";
    formData.append("Folder",Folder);
    // formData.append("ID",ID);
    var fileList = LabState.uploadFileList;
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
                    $("div."+classify+"_fileList_info>div:nth-child(1)>div.progress-bar").attr("aria-valuenow",newWidth).css("width",percent).text(percent);
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
        				LiStr+='<li class="list-group-item list-group-item-success" title="'+currentValue.FileName+'"><span class="badge uploaded">删除</span><span class="badge">成功</span>'+currentValue.FileName+'</li>';
        				successNO++;
        			}else if(currentValue.Message.indexOf("失败")>-1){
        				LiStr+='<li class="list-group-item list-group-item-danger" title="'+currentValue.FileName+'"><span class="badge">失败</span>'+currentValue.FileName+'</li>';
        			}else{
        				LiStr+='<li class="list-group-item list-group-item-warning" title="'+currentValue.FileName+'"><span class="badge">文件已存在</span>'+currentValue.FileName+'</li>';
        			}
        		});
        		$("#"+classify+"_fileList_ul>li>span.noUpload:contains('删除')").parent().remove();
        		$("#"+classify+"_fileList_ul").append(LiStr);
        		var successFloat = (successNO/(data.length)).toFixed(3);
        		var successValueNow = successFloat*100;
        		var successPercent = successValueNow+"%";
        		$("div."+classify+"_fileList_info>div:nth-child(2)>div.progress-bar").attr("aria-valuenow",successValueNow).css("width",successPercent).text(successPercent);
        		LabState.uploadFileNo = 0;
        		for(var kk in LabState.uploadFileList){
        			delete LabState.uploadFileList[kk];
        		}
        		$("#"+classify+"_file_Upload").val("");
        	}else if(data.length == 0){
        		$.MsgBox_Unload.Alert("上传提示","文件读取至服务器失败！");
        	}
        },
        error: function(){
        	$("div."+classify+"_fileList_info>div>div.progress-bar").prop("aria-valuenow","0").css("width","0%").text("0%");
            $.MsgBox_Unload.Alert("上传提示","网络繁忙！上传失败！");
        },
		beforeSend: function(XMLHttpRequest){
            eouluGlobal.C_btnDisabled(iThat, false);
        },
		complete: function(XMLHttpRequest, textStatus){
		    if(textStatus=="success"){
		    }
		    eouluGlobal.C_btnAbled(iThat, false);
		}
    });                             
}


function resizeTableCol(laboratory){
	// 表格列宽调整
	setTimeout(function(){
		$(".m_table .JCLRgrips").remove();
		$(".m_table table[data-itable='"+laboratory+"']").colResizable({
		    gripInnerHtml: '<span class="glyphicon glyphicon-resize-horizontal" aria-hidden="true"></span>',
		    partialRefresh: true,
		    minWidth: 62
		});
	}, 50);
}

/*****页面加载完成*****/
$(function(){
	if(thisPageHref == "Lab"){
		getPageMainData(true, "苏州", 1);
	}else if(thisPageHref == "AllLab"){
		getPageMainData(true, "all", 1);
	}
	
	// tableRenderAjax("苏州", 1);
	// 插入实验室
	var LaboratoryStr = '<option value="" disabled="disabled">请选择</option>';
	LaboratoryArr.map(function(currentValue,index,arr){
		LaboratoryStr+='<option value="'+currentValue+'">'+currentValue+'</option>';
	});
	$("select[name='Laboratory_sel']").empty().append(LaboratoryStr);
});


/*****
* event listener
*****/
// 打开
$(".m_button_l input[value='添加']").on("click",function(){
	$(".bg_cover, .add_NonStandard").slideDown(300);
	$(".add_NonStandard_body_in [id^='add_info_']").each(function(){
		// if($(this).prop("tagName")=="SELECT"||$(this).prop("tagName")=="select"){
		// 	$(this).val("0");
		// }else{
		// 	$(this).val("");
		// }
		if($(this).is("#add_info_Laboratory")){
			if(thisPageHref == "Lab"){
				$(this).val($("div.tab_wrapper>ul>li.active").data("laboratory"));
			}else if(thisPageHref == "AllLab"){
				$(this).val("");
			}
		}else{
			$(this).val("");
		}
		if($(this).is("#add_info_Picture")){
			$(this).attr("title","");
		}
	});
});

$(document).on("click",".update_td",function(){
	$(".bg_cover, .update_NonStandard").slideDown(300);
	// jQuery.data($(this), "customerId");
	updateSubmitObj.ID = Number($(this).data("iid")).toString();
	LabState.deleteFileID = Number($(this).data("iid")).toString();
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
	// 回显文件列表
	var iAttachment = $(this).siblings(".Document_part").data("ivalue").toString();
	var iAttachmentArr = iAttachment.split("::");
	iAttachmentArr = $.grep(iAttachmentArr,function(currentValue,index){
	    return currentValue != "";
	});
	var iAttachmentStr = '';
	iAttachmentArr.map(function(currentValue, index, arr){
		iAttachmentStr+='<li class="list-group-item list-group-item-info" title="'+currentValue+'"><span class="badge uploaded">删除</span><span class="badge">已上传</span>'+currentValue+'</li>';
	});
	$("#update_fileList_ul").empty().append(iAttachmentStr);
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

	LabState.deleteFileLiDOM = null;
});

$("#NonStandard_updateclose, .update_NonStandard_tit_r").on("click",function(){
	$(".bg_cover, .update_NonStandard").slideUp(300);

	if(LabState.deleteFileNeedRefresh){
		var iLaboratory = $("#update_info_Laboratory").val();
		if($("div.tab_wrapper>ul>li.active").data("laboratory") == iLaboratory){
			getPageMainData(true, iLaboratory, 1);
		}else{
			if(thisPageHref == "Lab"){
				$("div.tab_wrapper>ul>li[data-laboratory='"+iLaboratory+"']>a").trigger("click");
			}else if(thisPageHref == "AllLab"){
				getPageMainData(true, "all", 1);
			}
		}
		LabState.deleteFileNeedRefresh = false;
	}

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

	LabState.deleteFileLiDOM = null;
});

// 翻页功能
$("#jumpNumber").on("input propertychange",function(){
    var newVal = $(this).val().replace(/[^\d]/g,'');
    $(this).val(newVal);
});

	// 翻页
$("#fistPage").click(function(){
	getPageMainData(false, $("div.tab_wrapper>ul>li.active").data("laboratory"), 1);
    // tableRenderAjax($("div.tab_wrapper>ul>li.active").data("laboratory"), 1);
});

$("#lastPage").click(function(){
    var currentPage = Number($("#allPage").text());
    getPageMainData(false, $("div.tab_wrapper>ul>li.active").data("laboratory"), currentPage);
});

$("#upPage").click(function(){
    var currentPage = Number($("#currentPage").text());
    if(currentPage == 1){
        return;
    }else{
        currentPage--;
        getPageMainData(false, $("div.tab_wrapper>ul>li.active").data("laboratory"), currentPage);
    }
});

$("#nextPage").click(function(){
    var currentPage = Number($("#currentPage").text());
    var pageCounts = Number($("#allPage").text());
    if(currentPage == pageCounts){
        return;
    }else{
        currentPage++;
        getPageMainData(false, $("div.tab_wrapper>ul>li.active").data("laboratory"), currentPage);
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
        getPageMainData(false, $("div.tab_wrapper>ul>li.active").data("laboratory"), currentPage);
    }
});

// 添加提交
$("#NonStandard_addsubmit").on("click",function(){
	for(var kk in addSubmitObj){
		if(kk=="Type"){
			addSubmitObj[kk] = "add";
			continue;
		}
		if(kk=="Document"){
			continue;
		}
		addSubmitObj[kk] = $("#add_info_"+kk).val();
	}
	// 表单验证
	for(var kkk in addSubmitObj){
		addSubmitObj[kkk] = globalDataHandle(addSubmitObj[kkk],"").trim();
		if(kkk=="Model" && addSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择系统名称！");
			return false;
		}
		if(kkk=="Laboratory" && addSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择实验室！");
			return false;
		}
	}
	// 文件信息保存
	var fileArray = [];
	$("#add_fileList_ul>li.list-group-item-success").each(function(){
		fileArray.push($(this).attr("title"));
	});
	var newfileArray = globalArrStrUnique(fileArray);
	var fileStr = newfileArray.length == 0 ? "" : (newfileArray.join("::")+"::");
	addSubmitObj.Document = fileStr;

	console.log("add我执行了吗");
	console.log(addSubmitObj);
	var iLaboratory = addSubmitObj.Laboratory;
	var iThat = $(this);
	$.ajax({
		type: "POST",
		url: "Lab",
		dataType: 'text',
		data: addSubmitObj,
		beforeSend: function(XMLHttpRequest){
		    eouluGlobal.C_btnDisabled(iThat, true, "正在提交...");
		},
		success: function(data){
			// console.log(typeof data);
			if(data=="true"){
				$.MsgBox_Unload.Alert("提示","添加成功！");
				$("#NonStandard_addclose").trigger("click");
				if($("div.tab_wrapper>ul>li.active").data("laboratory") == iLaboratory){
					getPageMainData(true, iLaboratory, 1);
					// tableRenderAjax(iLaboratory, 1);
				}else{
					if(thisPageHref == "Lab"){
						$("div.tab_wrapper>ul>li[data-laboratory='"+iLaboratory+"']>a").trigger("click");
					}else if(thisPageHref == "AllLab"){
						getPageMainData(true, "all", 1);
					}
				}
			}else if(data=="false"){
				$.MsgBox_Unload.Alert("提示","添加失败！");
			}
		},
		error:function(){
			$.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
		},
		complete: function(XMLHttpRequest, textStatus){
		    if(textStatus=="success"){
		    }
		    eouluGlobal.C_btnAbled(iThat, true, "提交");
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
		if(kk=="Document"){
			continue;
		}
		updateSubmitObj[kk] = $("#update_info_"+kk).val();
	}
	// 表单验证
	for(var kkk in updateSubmitObj){
		updateSubmitObj[kkk] = globalDataHandle(updateSubmitObj[kkk],"").trim();
		if(kkk=="Model" && updateSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择系统名称！");
			return false;
		}
		if(kkk=="Laboratory" && updateSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择实验室！");
			return false;
		}
	}
	// 文件信息保存
	var fileArray = [];
	$("#update_fileList_ul>li.list-group-item-success, #update_fileList_ul>li.list-group-item-info").each(function(){
		fileArray.push($(this).attr("title"));
	});
	var newfileArray = globalArrStrUnique(fileArray);
	var fileStr = newfileArray.length == 0 ? "" : (newfileArray.join("::")+"::");
	updateSubmitObj.Document = fileStr;

	console.log("update我执行了吗");
	console.log(updateSubmitObj);
	var iLaboratory = updateSubmitObj.Laboratory;
	$.ajax({
		type: "POST",
		url: "Lab",
		dataType: 'text',
		data: updateSubmitObj,
		success: function(data){
			// console.log(typeof data);
			if(data=="true"){
				$.MsgBox_Unload.Alert("提示","修改成功！");
				if($("div.tab_wrapper>ul>li.active").data("laboratory") == iLaboratory){
					getPageMainData(true, iLaboratory, 1);
				}else{
					if(thisPageHref == "Lab"){
						$("div.tab_wrapper>ul>li[data-laboratory='"+iLaboratory+"']>a").trigger("click");
					}else if(thisPageHref == "AllLab"){
						getPageMainData(true, "all", 1);
					}
				}
				LabState.deleteFileNeedRefresh = false;
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
	$(this).parent().css({"-webkit-filter": "brightness(0.8)","filter": "brightness(0.8)"});
});
$(document).on("mouseleave",".m_table tbody td",function(){
	$(this).parent().css({"-webkit-filter": "brightness(1)","filter": "brightness(1)"});
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
		$(".serviceReport_div, .cover_bg2").slideDown(200);
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
                    	// console.log("1");
                    	img.width = 760;
                    }
                    if(img.height>1000){
                    	// console.log("2");
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

// 点击浏览切换文件
$("#add_file_Upload, #update_file_Upload").on("change",function(){
	var hideShowDOM;
	var insertDOM;
	var curFileInput;
	var classify;
	if($(this).is("#add_file_Upload")){
		curFileInput = $("#add_file_Upload");
		hideShowDOM = $(".add_fileList_info");
		insertDOM = $("#add_fileList_ul");
		classify = "add";
	}else if($(this).is("#update_file_Upload")){
		curFileInput = $("#update_file_Upload");
		hideShowDOM = $(".update_fileList_info");
		insertDOM = $("#update_fileList_ul");
		classify = "update";
	}
	if(!hideShowDOM.is(':visible')){
		hideShowDOM.slideDown(150);
	}
	// 保存file，渲染列表
	// console.log($(this));
	// console.log($(this)[0]);
	// console.log($(this)[0].files);
	var curFileList = $(this)[0].files;
	var curFileListStr = '';
	$.each(curFileList, function(iname, ivalue){
		LabState.uploadFileNo++;
		curFileListStr+='<li class="list-group-item" title="'+ivalue.name+'" value="'+LabState.uploadFileNo+'"><span class="badge noUpload">删除</span><span class="badge">待上传</span>'+ivalue.name+'</li>';
		LabState.uploadFileList[LabState.uploadFileNo] = ivalue;
	});
	insertDOM.append(curFileListStr);
	$("div."+classify+"_fileList_info>div>div.progress-bar").prop("aria-valuenow","0").css("width","0%").text("0%");
});

// 添加修改文件删除
$(document).on("click","[id$='_fileList_ul']>li>span.noUpload:contains('删除')",function(){
	var iValue = $(this).parent().attr("value");
	delete LabState.uploadFileList[iValue];
	var emptyFileInput;
	if($(this).parents("#add_fileList_ul").length){
		emptyFileInput = $("#add_file_Upload");
	}else if($(this).parents("#update_fileList_ul").length){
		emptyFileInput = $("#update_file_Upload");
	}
	emptyFileInput.val("");
	$(this).parent().remove();
});

// 添加修改文件上传
$(".add_label_upload>button, .update_label_upload>button").click(function(){
	if(Object.keys(LabState.uploadFileList).length == 0){
		$.MsgBox_Unload.Alert("上传提示","请选择文件！");
		return false;
	}
	var classify;
	if($(this).parent().is(".add_label_upload")){
		classify = "add";
	}else if($(this).parent().is(".update_label_upload")){
		classify = "update";
	}
	var Folder = "LabDocument";
	var iThat = $(this);
	uploadFiles2(classify, Folder, iThat);
});

// 添加修改和预览里的文件下载
$(document).on("click","li.list-group-item-success, li.list-group-item-info, ol>li, span.td_download_file, .warning_alert_wrapper_h4>a",function(){
	var fileName = $(this).attr("title");
	if(!fileName){
		$.MsgBox_Unload.Alert("下载提示","无数据或文件已被删除！");
		return false;
	}
	var downloadURL = globalHostName+'/LogisticsFile/File/LabDocument/'+fileName;
	window.open(downloadURL);
});

// 分页标签切换
$('.tab_wrapper>ul>li>a').click(function(e) {
  	e.preventDefault();
  	$(this).tab('show');
});
$('.tab_wrapper>ul>li>a').on('shown.bs.tab', function (e) {
    // console.log(e.target); // newly activated tab
    // console.log(e.relatedTarget); // previous active tab
    // console.log($(e.target));
    var laboratory = $(e.target).parent().data("laboratory").toString();
    getPageMainData(true, laboratory, 1);
    // tableRenderAjax(laboratory, 1);
});

/*2018-09-21更新*/
//删除文件时弹出
$(document).on("click", "span.badge.uploaded", function(e){
	e.stopPropagation();
	if($(this).parent().is(".list-group-item-success")){
		LabState.deleteFileID = "0";
	}else if($(this).parent().is(".list-group-item-info")){
		LabState.deleteFileID = updateSubmitObj.ID;
	}
	LabState.deleteFileLiDOM = $(this).parent();
	var fileName = $(this).parent().attr("title");
	var str = '<div class="alert alert-warning alert-dismissible fade in" role="alert" id="warning_alert_wrapper_alert">'+
	                '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'+
	                '<h4>确定删除</h4>'+
	                '<h4 class="warning_alert_wrapper_h4"><a class="alert-link" title="'+fileName+'">'+fileName+'</a></h4>'+
	                '<h4>吗？</h4>'+
	                '<p>文件删除成功后无需再次点提交按钮！</p>'+
	                '<p>'+
	                    '<button type="button" class="btn btn-warning" id="yes_delete_file">是，删除</button>'+
	                    '<button type="button" class="btn btn-default" id="no_delete_file">否，点错了</button>'+
	                '</p>'+
	            '</div>';
	$(".warning_alert_wrapper, .warning_alert_wrapper_cover").slideDown(150).children("div").empty().append(str);
});
$(document).on("click", "#warning_alert_wrapper_alert>button", function(){
	$(".warning_alert_wrapper, .warning_alert_wrapper_cover").slideUp(150);
});
$(document).on("click", "#no_delete_file", function(){
	$("#warning_alert_wrapper_alert").alert('close');
	$(".warning_alert_wrapper, .warning_alert_wrapper_cover").slideUp(150);
});
// 删除
$(document).on("click", "#yes_delete_file", function(){
	var iThat = $(this);
	$.ajax({
		type: "GET",
		url: "DeleteLabDocument",
		data: {
			ID: LabState.deleteFileID,
			FileName: iThat.parent().siblings(".warning_alert_wrapper_h4").children("a").attr("title")
		}
	}).then(function(data){
		if(data == "true"){
			$.MsgBox_Unload.Alert("删除提示","删除成功！");
			$("#no_delete_file").trigger("click");
			if(LabState.deleteFileLiDOM.is(".list-group-item-info")){
				LabState.deleteFileNeedRefresh = true;
			}
			LabState.deleteFileLiDOM.remove();
			LabState.deleteFileLiDOM = null;
		}else if(data == "false"){
			$.MsgBox_Unload.Alert("删除提示","删除失败或出错！");
		}else{
			$.MsgBox_Unload.Alert("删除提示", data);
		}
	},function(){
		$.MsgBox_Unload.Alert("删除提示","服务器繁忙！");
	});
});