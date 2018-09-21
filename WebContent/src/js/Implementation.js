var dafaultMenuItem = Highcharts.getOptions().exporting.buttons.contextButton.menuItems;
// 全局配置，对当前页面的所有图表有效
Highcharts.setOptions({
    lang:{
       contextButtonTitle:"图表导出菜单",
       decimalPoint:".",
       downloadJPEG:"下载JPEG图片",
       downloadPDF:"下载PDF文件",
       downloadPNG:"下载PNG文件",
       downloadSVG:"下载SVG文件",
       drillUpText:"返回 {series.name}",
       loading:"加载中",
       months:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
       noData:"没有数据",
       numericSymbols: [ "千" , "兆" , "G" , "T" , "P" , "E"],
       printChart:"打印图表",
       resetZoom:"恢复缩放",
       resetZoomTitle:"恢复图表",
       // shortMonths: [ "Jan" , "Feb" , "Mar" , "Apr" , "May" , "Jun" , "Jul" , "Aug" , "Sep" , "Oct" , "Nov" , "Dec"],
       shortMonths:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
       thousandsSep:",",
       weekdays: ["星期天","星期一","星期二","星期三","星期四","星期五","星期六"]
    },
    global: {
       timezoneOffset: -8 * 60  // +8 时区修正方法
    },
    exporting: {
        buttons: {
                contextButton: {
                        // 自定义导出菜单项目及顺序
                        menuItems: [
                                dafaultMenuItem[0],
                                {
                                    separator: true
                                },
                                dafaultMenuItem[2],
                                dafaultMenuItem[3],
                                dafaultMenuItem[5],
                                {
                                    separator: true
                                },
                                {
                                    text: '回到首页',
                                    onclick: function() {
                                        // window.location.href= 'https://www.hcharts.cn';
                                        var baseHref = window.location.href.split("cfChicken8")[0]+"cfChicken8/";
                                        window.location.href= baseHref+"Index";
                                    }
                                }
                        ]
                }
        }
    }
});
$('.cover-color').height($(window).height());

// 定义全局字符串
var globalStr1 = '<tr class="ServiceContentTr"><td class="SerCon_Ser"><span class="glyphicon glyphicon-remove SerDel" aria-hidden="true" title="删除" style="display: none;"></span><span class="SerText" style="margin-left: 0px;">';
// 中间+length
var globalStr2 = '</span></td><td><select name="" id=""><option value="0" checked>请选</option><option value="P1">P1</option><option value="P2">P2</option><option value="P3">P3</option><option value="P4">P4</option><option value="P5">P5</option></select></td><td contenteditable="true"></td><td><input type="date" class="exceDate"></td><td style="font-size: 13px"><select name="" id=""><option value="0" checked>请选</option><option value="最高">最高</option><option value="高">高</option><option value="中">中</option><option value="低">低</option><option value="最低">最低</option></select></td><td contenteditable="true"></td><td contenteditable="true"></td><td contenteditable="true"></td><td style="font-size:12px;">';
// 中间+登记时间
var globalStr3 = '</td><td class="serFinTime"></td><td style="font-size: 13px" class="dragTd">(未上传，点击上传)</td></tr>';

// PDF页头页尾
var pdfTitle="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAA0gAAABMCAIAAADY/AAHAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA3FpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNS1jMDE0IDc5LjE1MTQ4MSwgMjAxMy8wMy8xMy0xMjowOToxNSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo5Yjc4MmI1Ni1mODI4LTIzNGItOGMxYy1iY2RhMzMxNmVhOWUiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6Njc4MEQyMjJBRTM1MTFFNzk5RDhCRDUwRDlDRDQ3QzIiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6Njc4MEQyMjFBRTM1MTFFNzk5RDhCRDUwRDlDRDQ3QzIiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIChXaW5kb3dzKSI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOmI3NTk1MWFhLTIxNGQtZjg0NS04ZGRkLTY0YjM3YWIxNDU1NSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo5Yjc4MmI1Ni1mODI4LTIzNGItOGMxYy1iY2RhMzMxNmVhOWUiLz4gPC9y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";
var pdfFoot = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAA0gAAABMCAIAAADY/AAHAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA3FpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNS1jMDE0IDc5LjE1MTQ4MSwgMjAxMy8wMy8xMy0xMjowOToxNSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo5Yjc4MmI1Ni1mODI4LTIzNGItOGMxYy1iY2RhMzMxNmVhOWUiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6MUQ5NDI0RjJBRTM3MTFFN0E2MUJGNEJDNjZGRDAwQ0MiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6MUQ5NDI0RjFBRTM3MTFFN0E2MUJGNEJDNjZGRDAwQ0MiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIChXaW5kb3dzKSI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjJkNWY2YjU3LTE0MTQtM2I0Ni1hZmUzLWUzZmQ1NDEyNjhlZiIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo5Yjc4MmI1Ni1mODI4LTIzNGItOGMxYy1iY2RhMzMxNmVhOWUiLz4gPC9y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";

// 格式化字符串
function formatString(value,inum){  
     // debugger  
     var ret = "";//拼接加\n返回的类目项  
     var maxLength = inum;//每项显示文字个数  
     // var maxLength = 2;//每项显示文字个数  
     var valLength = value.length;//X轴类目项的文字个数  
     var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数  
     if (rowN > 1)//如果类目项的文字大于3,  
     {  
         for (var i = 0; i < rowN; i++) {  
             var temp = "";//每次截取的字符串  
             var start = i * maxLength;//开始截取的位置  
             var end = start + maxLength;//结束截取的位置  
             //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧  
             temp = value.substring(start, end) + "<br/>";  
             ret += temp; //凭借最终的字符串  
         }  
         return ret;  
     }  
     else {  
         return value;  
     }  
}

// 计算百分比
function calcPercent(startTime,endTime){
    var str1;
    var todayMESL = new Date(globalGetToday()).getTime();
    if(startTime>todayMESL||startTime==todayMESL){
        str1 = "0%";
    }else if(endTime<todayMESL||todayMESL==endTime){
        str1 = "99%";
    }else{
        var denominator = Math.abs((endTime - startTime)/(1000*60*60*24));
        var numerator = Math.abs((todayMESL - startTime)/(1000*60*60*24));
        str1 = Math.floor((numerator/denominator)*100)+"%";
    }
    return str1;
}

function Search() {
    $("#search_box input[name='currentPage']").val(1);
    $('#search_box').submit();
}

function DateDifference(date,num){
	var  oDate1  =  new  Date(date);
	var  intValue  =  num  *  (24  *  3600  *  1000) + oDate1.getTime();  
	var  endDate  =  new  Date  (intValue); 
	var endDate1 = endDate.getFullYear()+"-"+ ((endDate.getMonth()+1)<10?"0"+(endDate.getMonth()+1) : (endDate.getMonth()+1))+"-"+ (endDate.getDate()<10?"0"+endDate.getDate():endDate.getDate());
	return endDate1;
}

// 添加记录
$(".addBtnDiv>input[value='添加']").on("click",function(){
	$(".addImplementationBox").css({top:"225px",height:"0"}).animate({height:"450px",top:"0"},700).css("display","block");
    $(".addImplementationOut").css("display","block");
    $(".cover-color").css("min-height",$(window).height()+15);
    $(".cover-color").show();
    $(".addImplementationBox").find("input:not([type='button'])").val("");
    $(".addImplSpan").attr("id","");
    
    $.ajax({
        type : 'GET',
        url : 'GetStaffApplicationName',
        success : function (data) { 
            console.log(typeof data);
            console.log(data);
            $("div.saveSoftwareStaff").text(data);
        },
        error : function () {
            $.MsgBox_Unload.Alert("提示", "服务器繁忙，工程师数据获取有误！");
        }
    });
});

// 添加记录工程师搜索
$(document).on("keyup",".addEnginerrInput",function(){
    if($(this).val()==""){
        return;
    }
    var addKeyword = $(this).val().trim();
    if(addKeyword == "" || addKeyword == "--"){
        $.MsgBox_Unload.Alert("提示", "工程师搜索值为空或格式错误！");
        return;
    }else{
        var oldData = $("div.saveSoftwareStaff").text();
        if(oldData != "--" && oldData != ""){
            var newData = JSON.parse(oldData);
            console.log("处理过后的");
            console.log(newData);
            var addEngSel = $("select.addEngineer");
            var str = '';
            newData.map(function(currentValue,index,arr){
                if(index>0){
                    var item = currentValue.StaffName;
                    if(item.indexOf(addKeyword)>-1){
                        str += '<option value="'+item+'">'+item+'</option>';
                    }
                }
            });
            addEngSel.empty();
            addEngSel.append(str);
            addEngSel.fadeIn(50);
        }
    }
});

// 添加工程师select option
$(document).on("click","select.addEngineer option",function(){
    $(".addEnginerrInput").val($(this).attr("value"));
    $("select.addEngineer").fadeOut(50);
});

$(document).on("keyup",'#addImplCusNameInput',function(){
    if($(this).val()==""){
        return;
    }
    var addKeyword = $(this).val().trim();
    if(addKeyword == "" || addKeyword == "--"){
        $.MsgBox_Unload.Alert("提示", "客户单位搜索值为空或格式错误！");
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
                        str+='<option value="'+newData[i].CustomerName+'" id="'+newData[i].ID+'" contact="'+newData[i].Contact+'">'+newData[i].CustomerName+'&nbsp;:&nbsp;'+newData[i].Contact+'</option>';
                    }
                }
                $("#addImplCusName").empty().append(str);
                $('#addImplCusName').show();
            },
            error : function () {
                $.MsgBox_Unload.Alert("提示", "服务器繁忙，客户单位数据获取有误！");
            }
        });
    }
});

$(document).on("click", '#addImplCusName option', function () {
    $('#addImplCusNameInput').val($('#addImplCusName').val());
    $('#addImplCusNameInput').attr("title",$('#addImplCusName').val());
    $(".addImplementationBox .Contact").val($(this).attr("contact"));
    $(".addImplSpan").attr("id",$(this).attr("id"));
    $('#addImplCusName').hide();
});

$(document).on("click", function () {
    $('#addImplCusName').hide();
    $('#alterImplCusName').hide();
});

// 添加记录提交
$(document).on("click","#modify_submit2",function(){
    var CustomerID = $(".addImplSpan").attr("id");
    if(CustomerID==""||CustomerID==undefined){
        $.MsgBox_Unload.Alert("提示", "未选择客户单位或姓名！");
        return;
    }
    var Machine = $(".addImplementationBox .Machine").val();
    var SoftwareVersion = $(".addImplementationBox .SoftwareVersion").val();
    var Type = $(".addImplementationBox .Type").val();
    var InstallTime = $(".addImplementationBox .InstallTime").val();
    var ContinueTime = $(".addImplementationBox .ContinueTime").val();
    // var Engineer = $(".addImplementationBox .addEngineer").val();
    var Engineer = $(".addImplementationBox .addEnginerrInput").val();
    // if (Engineer==0||Engineer=="0") {
    //     $.MsgBox_Unload.Alert("提示", "未选择工程师！");
    //     return;
    // }
    $.ajax({
        type : 'POST',
        url : 'SoftwareImplementation',
        data : {
            CustomerID: CustomerID,
            classify:"Add",
            Machine:Machine,
            SoftwareVersion:SoftwareVersion,
            Type:Type,
            InstallTime:InstallTime,
            ContinueTime:ContinueTime,
            Engineer:Engineer,
        },
        success : function (data) { 
            console.log(typeof data);
            console.log(data);
             $.MsgBox.Alert("提示", data);
        },
        error : function () {
            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});

// 修改记录搜索
$(document).on("keyup",'#alterImplCusNameInput',function(){
    if($(this).val()==""){
        return;
    }
    var addKeyword = $(this).val().trim();
    if(addKeyword == "" || addKeyword == "--"){
        $.MsgBox_Unload.Alert("提示", "客户单位搜索值为空或格式错误！");
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
                        str+='<option value="'+newData[i].CustomerName+'" id="'+newData[i].ID+'" contact="'+newData[i].Contact+'">'+newData[i].CustomerName+'&nbsp;:&nbsp;'+newData[i].Contact+'</option>';
                    }
                }
                $("#alterImplCusName").empty().append(str);
                $('#alterImplCusName').show();
            },
            error : function () {
                $.MsgBox_Unload.Alert("提示", "服务器繁忙，客户单位数据获取有误！");
            }
        });
    }
});

$(document).on("click", '#alterImplCusName option', function () {
    $('#alterImplCusNameInput').val($('#alterImplCusName').val());
    $('#alterImplCusNameInput').attr("title",$('#alterImplCusName').val());
    $(".ModifyBox .Contact").val($(this).attr("contact"));
    // $(".addImplSpan").attr("id",$(this).attr("id"));
    $('#alterImplCusName').hide();
});

//详细记录
//修改软件实施记录
$(document).on("click",".toggleedit",function(){
	$(".ModifyBox").slideDown(400);
	var tableCustomerName = $(this).parent().find(".CustomerName").text();
	var tableContact = $(this).parent().find(".Contact ").text();
	if(tableCustomerName=="--" || tableCustomerName == "" || tableCustomerName == null){
		tableCustomerName = "(无)";
	}
	if(tableContact=="--" || tableContact == "" || tableContact == null){
		tableContact = "(无)";
	}
	$(".ModifyBox .ModalBox_title").text("修改软件实施记录  - "+tableCustomerName+" - "+tableContact);

	var ID = $(this).parent().find(".toggleedit").attr("value");
	var currentTr = $(this).parent();
	var InquifyID = $(this).parent().find("td.alterInquiryID").text();
	if(InquifyID==""||InquifyID=="--"||InquifyID==null||InquifyID==undefined){
		$(".ModifyBox input[name='CustomerName']").prop("disabled",false);
	}else{
		$(".ModifyBox input[name='CustomerName']").prop("disabled","disabled");
	}
	$(".ModifyBox").attr("ID",ID);
	$(".ModifyBox input[name='CustomerName']").val(currentTr.find(".CustomerName").text());
	$(".ModifyBox .Contact").val(currentTr.find(".Contact").text());
	$(".ModifyBox .Machine").val(currentTr.find(".Machine").text());
	$(".ModifyBox .SoftwareVersion").val(currentTr.find(".SoftwareVersion").text());
	var type = currentTr.find(".Type").text();
	$(".ModifyBox .Type").find("option[value='"+type+"']").prop("selected","selected");
	$(".ModifyBox .InstallTime").val(currentTr.find(".InstallTime").text());
	// var ContinueTime = DateDifference($(".ModifyBox .InstallTime").val(),parseFloat(currentTr.find(".ContinueTime").text()));
	var ContinueTime = currentTr.find(".ContinueTime").text();
	$(".ModifyBox .ContinueTime").val(ContinueTime);
    var EngineerVal = currentTr.find("td.Engineer").text();
    console.log("主表格工程师"+EngineerVal);
    var newEngineerVal = globalDataHandle(EngineerVal,"");
    console.log("主表格工程师处理后的值"+newEngineerVal);
    $(".alterEnginerrInput").val(newEngineerVal);
    
        $.ajax({
            type : 'GET',
            url : 'GetStaffApplicationName',
            success : function (data) { 
                console.log(typeof data);
                console.log(data);
                $("div.saveSoftwareStaff").text(data);
            },
            error : function () {
                $.MsgBox_Unload.Alert("提示", "服务器繁忙，工程师数据获取有误！");
            }
        }); 
	
	$(".cover-color").css("min-height",$(window).height()+15);
	$(".cover-color").css("height",$(".ModifyBox").height()+130);
	$(".cover-color").show();
});

// 修改工程师搜索
$(document).on("keyup",".alterEnginerrInput",function(){
    if($(this).val()==""){
        return;
    }
    var addKeyword = $(this).val().trim();
    if(addKeyword == "" || addKeyword == "--"){
        $.MsgBox_Unload.Alert("提示", "工程师搜索值为空或格式错误！");
        return;
    }else{
        var oldData = $("div.saveSoftwareStaff").text();
        if(oldData != "--" && oldData != ""){
            var newData = JSON.parse(oldData);
            console.log("处理过后的");
            console.log(newData);
            var addEngSel = $("select.alterEngineer");
            var str = '';
            newData.map(function(currentValue,index,arr){
                if(index>0){
                    var item = currentValue.StaffName;
                    if(item.indexOf(addKeyword)>-1){
                        str += '<option value="'+item+'">'+item+'</option>';
                    }
                }
            });
            addEngSel.empty();
            addEngSel.append(str);
            addEngSel.fadeIn(50);
        }
    }
});

// 添加工程师select option
$(document).on("click","select.alterEngineer option",function(){
    $(".alterEnginerrInput").val($(this).attr("value"));
    $("select.alterEngineer").fadeOut(50);
});

//修改 保存
$("#modify_submit").click(function(){
	var ID = $(".ModifyBox").attr("ID");
	var Machine = $(".ModifyBox .Machine").val();
	var SoftwareVersion = $(".ModifyBox .SoftwareVersion").val();
	var Type = $(".ModifyBox .Type").val();
	var InstallTime = $(".ModifyBox .InstallTime").val();
	var ContinueTime = $(".ModifyBox .ContinueTime").val();
    // var Engineer = $(".ModifyBox .alterEngineer").val();
	var Engineer = $(".ModifyBox .alterEnginerrInput").val();
	if(InstallTime == ""){
		 $.MsgBox_Unload.Alert("提示", "请选择装机时间！");
		 return;
	}
	if(ContinueTime == ""){
		 $.MsgBox_Unload.Alert("提示", "请选择续期时间！");
		 return;
	}
	// else{
	// 	ContinueTime = (Math.abs(new  Date(ContinueTime)-new  Date(InstallTime))/(1000*60*60*24))+"天";
	// }
	$.ajax({
        type : 'POST',
        url : 'SoftwareImplementation',
        data : {
        	ID:ID,
        	classify:"Modify",
        	Machine:Machine,
        	SoftwareVersion:SoftwareVersion,
        	Type:Type,
        	InstallTime:InstallTime,
        	ContinueTime:ContinueTime,
        	Engineer:Engineer,
        },
        success : function (data) { 
        	console.log(data);
        	 $.MsgBox.Alert("提示", data);
        },
        error : function () {
            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});
//详细记录
$(document).on("click",".record",function(){
	$(".ModalBox").slideDown(400);
	var ID = $(this).parent().find(".toggleedit").attr("value");
	var tableCustomerName = $(this).parent().find(".CustomerName").text();
	var tableContact = $(this).parent().find(".Contact ").text();
	if(tableCustomerName=="--" || tableCustomerName == "" || tableCustomerName == null){
		tableCustomerName = "(无)";
	}
	if(tableContact=="--" || tableContact == "" || tableContact == null){
		tableContact = "(无)";
	}
	$(".ModalBox .ModalBox_title").text("软件实施详细记录  - "+tableCustomerName+" - "+tableContact);
	$(".ModalBox").attr("ID",ID);
	$(".ModalBox  input").not(".bToggle").val("");
	$(".ModalBox .ModalBoxMsg .add_fileCont").val("");
	$(".ModalBox  select option").prop("selected",false);
	// $(".ModalBox .P1").addClass("active").siblings().removeClass("active");
	$(".ServiceContentTr").remove();
	var that = $(this);
	$.ajax({
        type : 'GET',
        url : 'SoftwareImplementationOperate',
        data : {
        	ID:ID,
        	// classify:"Preview"
        	classify:"Modify"
        },
        dataType : 'json',
        success : function (data) { 
        	console.log(data);
        	if(data.datas.length>1){
        		var datas = data.datas[1];
        		var MachineCodeVal = datas.MachineCode;
        		var SNVal = datas.SN;
        		var RegistrationCodeVal = datas.RegistrationCode;
        		var EmailVal = datas.Email;
        		var ContactInfoVal = datas.ContactInfo1;
        		var newMachineCodeVal = globalDataHandle(MachineCodeVal,"");
        		var newSNVal = globalDataHandle(SNVal,"");
        		var newRegistrationCodeVal = globalDataHandle(RegistrationCodeVal,"");
        		var newEmailVal = globalDataHandle(EmailVal,"");
        		var newContactInfoVal = globalDataHandle(ContactInfoVal,"");
        		
            	$(".ModalBox input[name='MachineCode']").val(newMachineCodeVal);
            	$(".ModalBox input[name='SN']").val(newSNVal);
            	$(".ModalBox input[name='RegistrationCode']").val(newRegistrationCodeVal);
            	$(".ModalBox input[name='Email']").val(newEmailVal);
            	$(".ModalBox input[name='ContactInfo']").val(newContactInfoVal);
            	
            	var InstallationReport = datas.InstallationReport;
            	var ImplementHandbook = datas.ImplementHandbook;
            	var TechnologyProtocol = datas.TechnologyProtocol;
              var PreSalesTable = datas.PreSalesTable;
            	var ScriptBackup = datas.ScriptBackup;
            	if(InstallationReport==""||InstallationReport=="--"||InstallationReport==undefined){
            		InstallationReport="未上传";
            	}
            	if(ImplementHandbook==""||ImplementHandbook=="--"||ImplementHandbook==undefined){
            		ImplementHandbook="未上传";
            	}
            	if(TechnologyProtocol==""||TechnologyProtocol=="--"||TechnologyProtocol==undefined){
            		TechnologyProtocol="未上传";
            	}
            	if(PreSalesTable==""||PreSalesTable=="--"||PreSalesTable==undefined){
            		PreSalesTable="未上传";
            	}
              if(ScriptBackup==""||ScriptBackup=="--"||ScriptBackup==undefined){
                ScriptBackup="未上传";
              }
            	$(".ModalBox input.PreSalesTable").val(PreSalesTable);
            	$(".ModalBox input.InstallationReport").val(InstallationReport);
            	$(".ModalBox input.ImplementHandbook").val(ImplementHandbook);
              $(".ModalBox input.TechnologyProtocol").val(TechnologyProtocol);
            	$(".ModalBox input.ScriptBackup").val(ScriptBackup);
            	$(".ModalBox input.PreSalesTable").attr("title",PreSalesTable);
            	$(".ModalBox input.InstallationReport").attr("title",InstallationReport);
            	$(".ModalBox input.ImplementHandbook").attr("title",ImplementHandbook);
              $(".ModalBox input.TechnologyProtocol").attr("title",TechnologyProtocol);
            	$(".ModalBox input.ScriptBackup").attr("title",ScriptBackup);
        	}
        	if(data.services.length>1){
        		for(var i = 1 ; i  < data.services.length ; i++){
        			var servicesData = data.services[i];
        			var CompleteTime = servicesData.CompleteTime.split(" ")[0];
        			if(CompleteTime =='--'||CompleteTime==''||CompleteTime=='0000-00-00'){
        				CompleteTime ='';
        			}
        			var RegisterTime = servicesData.RegisterTime;
        			if(RegisterTime == '--'||RegisterTime==''||RegisterTime=='0000-00-00'){
        				RegisterTime ='';
        			}
        			var str = globalStr1+i+globalStr2+RegisterTime+globalStr3;
        			
        			$("#ServiceContentTable tbody").append(str);
        			var j = i-1;
        			var curSerTr = $("#ServiceContentTable tbody tr").eq(j);
                    curSerTr.find("td.serFinTime").text(CompleteTime);
        			var ServiceItem = servicesData.ServiceItem;
        			var Content = servicesData.Content;
        			var ServiceID = servicesData.ServiceID;
        			var Description = servicesData.Description;
        			var ExpectedDate = servicesData.ExpectedDate;
        			var Priority = servicesData.Priority;
        			var RequirmentClassify = servicesData.RequirmentClassify;
        			var ResponsibleMan = servicesData.ResponsibleMan;
        			var ServiceReport = servicesData.ServiceReport;

        			var ServiceItemSel = curSerTr.find("td:nth-child(2) select");
        			var ContentTd = curSerTr.find("td:nth-child(3)");
        			var ServiceIDTd = curSerTr.find("td:nth-child(1)");
        			var DescriptionTd = curSerTr.find("td:nth-child(8)");
        			var ExpectedDateInput = curSerTr.find("td:nth-child(4) input");
        			var PrioritySel = curSerTr.find("td:nth-child(5) select");
        			var RequirmentClassifyTd = curSerTr.find("td:nth-child(7)");
        			var ResponsibleManTd = curSerTr.find("td:nth-child(6)");
        			var ServiceReportTd = curSerTr.find("td:nth-child(11)");

        			if(ServiceItem==""||ServiceItem=="--"||ServiceItem==null||ServiceItem==undefined){
        				ServiceItemSel.val("0");
        			}else if(ServiceItem!="P1"&&ServiceItem!="P2"&&ServiceItem!="P3"&&ServiceItem!="P4"&&ServiceItem!="P5"){
        				ServiceItemSel.val("0");
        			}else{
        				ServiceItemSel.val(ServiceItem);
        			}
        			var newContent = globalDataHandle(Content,"");
        			ContentTd.text(newContent);
                    ContentTd.attr("title",newContent);
        			ServiceIDTd.attr("id",ServiceID);
        			var newDescription = globalDataHandle(Description,"");
        			DescriptionTd.text(newDescription);
                    DescriptionTd.attr("title",newDescription);
        			if(ExpectedDate=="--"||ExpectedDate==""||ExpectedDate=="0000-00-00"){
        				ExpectedDate='';
        			}
        			ExpectedDateInput.val(ExpectedDate);
        			var newPriority = globalDataHandle(Priority,"0");
        			if(newPriority!="最高" && newPriority!="高" && newPriority!="中" && newPriority!="低" && newPriority!="最低"){
        				newPriority="0";
        			}
        			PrioritySel.val(newPriority);
        			var newRequirmentClassify = globalDataHandle(RequirmentClassify,"");
        			RequirmentClassifyTd.text(newRequirmentClassify);
        			var newResponsibleMan = globalDataHandle(ResponsibleMan,"");
        			ResponsibleManTd.text(newResponsibleMan);
        			// 服务报告
        			var newServiceReport = globalDataHandle(ServiceReport,"");
        			var str11;
        			if(newServiceReport==""){
        				str11 = "(未上传，点击上传)";
        			}else{
        				str11 = newServiceReport+'<span class="serRepDown" title="下载"><img src="image/download.png" height="20" width="20"></span>';
        			}
        			ServiceReportTd.html(str11);
        		}
        	}
        },
        error : function () {
            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
	$(".cover-color").css("min-height",$(window).height()+30);
	$(".cover-color").css("height",$(".ModalBox").height()+130);
	$(".cover-color").show();	
});
//详细记录  提交
$("#ModalBox_submit").click(function(){
	var ID = $(".ModalBox").attr("ID");
	console.log("详细记录提交的id为"+ID);
	// var ProgressStatus = $(".ModalBox .active").text();
	// var Appointment = $(".ModalBox .Appointment").val();
	// var Implementation = $(".ModalBox .Implementation").val();
	// var Remarks = $(".ModalBox .Remarks").val();
	var MachineCode = $(".ModalBox input[name='MachineCode']").val();
	var SN = $(".ModalBox input[name='SN']").val();
	var RegistrationCode = $(".ModalBox input[name='RegistrationCode']").val();
	var Email = $(".ModalBox input[name='Email']").val();
	// var ServiceReport = $(".ModalBox .ServiceReport").text();
	
	var InstallationReport = $(".ModalBox .InstallationReport").val()=='未上传'?"":$(".ModalBox .InstallationReport").val();
	var ImplementHandbook = $(".ModalBox .ImplementHandbook").val()=='未上传'?"":$(".ModalBox .ImplementHandbook").val();
	var TechnologyProtocol = $(".ModalBox .TechnologyProtocol").val()=='未上传'?"":$(".ModalBox .TechnologyProtocol").val();
  var PreSalesTable = $(".ModalBox .PreSalesTable").val()=='未上传'?"":$(".ModalBox .PreSalesTable").val();
	var ScriptBackup = $(".ModalBox .ScriptBackup").val()=='未上传'?"":$(".ModalBox .ScriptBackup").val();
	var ImplementationID= [];
	var ServiceItem= [];
	var Content= [];
	var ExpectedDate = [];
	var Priority = []; // 优先级
	var ResponsibleMan = [];
	var RequirmentClassify = [];
	var Description = [];

	var CompleteTime = [];
	var ServiceReport = [];
	for(var i = 0 ; i < $(".ServiceContentTr").length ; i++){
		var curSerConTr = $(".ServiceContentTr").eq(i);
		if(curSerConTr.find("td:nth-child(1)").attr("ID")){
			ImplementationID.push(curSerConTr.find("td:nth-child(1)").attr("ID"));
		}
		else{
			ImplementationID.push(0);
		}

		if(curSerConTr.find("td:nth-child(2) select").val()==0||curSerConTr.find("td:nth-child(2) select").val()=="0"){
			$.MsgBox_Unload.Alert("提示", "有服务项未选");
            return false;
		}else{
			ServiceItem.push(curSerConTr.find("td:nth-child(2) select").val());
		}

		var ContentVal = curSerConTr.find("td:nth-child(3)").text()=='--'?"":curSerConTr.find("td:nth-child(3)").text();
		Content.push(ContentVal);

		ExpectedDate.push(curSerConTr.find("td:nth-child(4) .exceDate").val()); 

		if(curSerConTr.find("td:nth-child(5) select").val()==0||curSerConTr.find("td:nth-child(5) select").val()=="0"){
			$.MsgBox_Unload.Alert("提示", "有优先级未选");
            return false;
		}else{
			Priority.push(curSerConTr.find("td:nth-child(5) select").val());
		}
		
		var ResponsibleManVal = curSerConTr.find("td:nth-child(6)").text()=='--'?"":curSerConTr.find("td:nth-child(6)").text();
		ResponsibleMan.push(ResponsibleManVal);

		var RequirmentClassifyVal = curSerConTr.find("td:nth-child(7)").text()=='--'?"":curSerConTr.find("td:nth-child(7)").text();
		RequirmentClassify.push(RequirmentClassifyVal);

		var DescriptionVal = curSerConTr.find("td:nth-child(8)").text()=='--'?"":curSerConTr.find("td:nth-child(8)").text();
		Description.push(DescriptionVal);
		
		CompleteTime.push(curSerConTr.find("td:nth-child(10)").text());

		var ServiceReportVal = curSerConTr.find("td:nth-child(11)").text()=='(未上传，点击上传)'?"":curSerConTr.find("td:nth-child(11)").text();
		ServiceReport.push(ServiceReportVal);
	}
	
	$.ajax({
        type : 'POST',
        url : 'SoftwareImplementation',
        data : {
        	classify : "Preview",
        	ID : ID,
        	// ProgressStatus : ProgressStatus,
        	// Appointment : Appointment,
        	// Implementation : Implementation,
        	// Remarks : Remarks,
        	MachineCode : MachineCode,
        	SN : SN,
        	RegistrationCode : RegistrationCode,
        	Email : Email,
        	// ServiceReport : ServiceReport,
        	InstallationReport : InstallationReport,
        	ImplementHandbook : ImplementHandbook,
        	TechnologyProtocol : TechnologyProtocol,
          PreSalesTable: PreSalesTable,
          ScriptBackup: ScriptBackup,
        	ImplementationID: ImplementationID,
        	ServiceItem: ServiceItem,
        	Content: Content,
        	ExpectedDate: ExpectedDate,
        	Priority: Priority,
        	ResponsibleMan: ResponsibleMan,
        	RequirmentClassify: RequirmentClassify,
        	Description: Description,
        	CompleteTime: CompleteTime,
        	ServiceReport: ServiceReport
        },
        beforeSend: function(XMLHttpRequest){
            $("#ModalBox_submit").attr("disabled","disabled");
            $("#ModalBox_submit").css({
                "background":"#dddddd",
                "color":"#808080",
                "border":"none",
                "box-shadow":"0 0 0 0 #f8fcfd",
                "cursor":"not-allowed"
            });
        },
        complete: function(XMLHttpRequest, textStatus){
            $("#ModalBox_submit").attr("disabled",false);
            $("#ModalBox_submit").css({
                "background":"#00aeef",
                "color":"#fff",
                "border":"solid 1px #00aeef",
                "box-shadow":"1px 2px 5px 0 #00aeef",
                "cursor":"pointer"
            });
        },
        success : function (data) { 
        	console.log(data);
        	 $.MsgBox.Alert("提示", data);
        },
        error : function () {
            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});

//详细记录 文件上传
 $(document).on("change",".ModalBox .add_file",function(){
    var filePath=$(this).val().replace("C:\\fakepath\\","");
    console.log(filePath);
	// $(this).prev().prev().prev().html(filePath);
	$(this).prev().prev().prev().val(filePath);
	$(this).prev().prev().prev().attr("title",filePath);
	$(this).parent().submit();
});

$('.ModalBox_close,#ModalBox_cancel,#modify_cancel').click(function(){
	$(".cover-color").hide();
	$(".ModalBox,.ModifyBox").slideUp(400);
});

$(".ModalBox_close2, #modify_cancel2").on("click",function(){
	$(".addImplementationOut").slideUp(400);
	$(".cover-color").hide();
});

//进度 P1-5 切换
$(".ScheduleBoxList1 li").click(function(){
	$(this).addClass("active").siblings().removeClass("active");
});
$(".ScheduleBoxList1 li").hover(function(){
	$(this).addClass("lihover").siblings().removeClass("lihover");
},function(){
	$(this).removeClass("lihover");
});

//服务内容等登记 添加
$(".SerConBtn").click(function(){
	var newDate = new Date();
	var Month = newDate.getMonth()+1;
	Month = Month < 10 ?"0" + Month :Month;
	var Day = newDate.getDate();
	Day = Day < 10 ?"0" + Day :Day;
	var RegistTime = newDate.getFullYear() +"-"+ Month +"-"+Day;
	var length =  $("#ServiceContentTable tr").not(".tHead").length +1;
	var str = globalStr1+length+globalStr2+RegistTime+globalStr3;

	$("#ServiceContentTable tbody").append(str);
	// $("#ServiceContentTable tbody tr:nth-child(1) td:nth-child(5) select").val("中");
});
//服务内容等登记 删除 排序
$(document).on("click",".SerCon_Ser .SerDel",function(){
	if($(this).prop("disabled")=="disabled"){
		return;
	}
	$(this).parents(".ServiceContentTr").remove();
	for(var i = 0 ; i < $("#ServiceContentTable .ServiceContentTr").length ; i++){
		$("#ServiceContentTable .ServiceContentTr").eq(i).find(".SerText").text(i+1);
	}
});
//服务内容登记 delete显示
   $(document).on("mouseover",".SerCon_Ser",function(){
    	$(this).find(".SerDel").toggle();
    	$(this).find(".SerText").css("marginLeft","3px");
    });
    $(document).on("mouseout",".SerCon_Ser",function(){
    	$(this).find(".SerDel").toggle();
    	$(this).find(".SerText").css("marginLeft","0px");
    });
	    
//下载
$(".add_download").click(function(){
	var filepath = $(this).prev().prev().val();
	if(filepath=="未上传"){
		$.MsgBox_Unload.Alert("下载文件提示","没有文件可下载！");
	}else{
		// var url = window.location.href.split("Logistics")[0];
		// window.location.href = url+"LogisticsFile/File/"+filepath;
		// console.log(url+"LogisticsFile/File/"+filepath);
		var url = window.location.href.split(globalProjectName)[0];
		window.location.href = url+"LogisticsFile/File/"+filepath;
		console.log(url+"LogisticsFile/File/"+filepath);
	}
});
    
//排序
$("#table1 .firstTr th").click(function(){
    console.log($(this).text());
	console.log($(this).attr("class"));
    if($(this).attr("class")==null || $(this).attr("class")==undefined || $(this).attr("class")==""){
        return;
    }else if($(this).attr("class")=="ID"){
        return;
    }else{
        $("#search_box input[name='column']").attr("value",$(this).attr("class"));
        if($("#search_box input[name='order']").val() == "desc" || $("#search_box input[name='order']").val() == "DESC"){
            $("#search_box input[name='order']").attr("value","asc");
        }
        else{
            $("#search_box input[name='order']").attr("value","desc");
        }
        $('#search_box').submit();
    }
});

//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function () {
    window.location.reload();
});

$(function(){
	$(document).on("click",".add_fileCont",function(){
		if($(this).val()=='未上传'){
			$.MsgBox_Unload.Alert("下载文件提示","没有文件可下载！");
		}else{
			var url = window.location.href.split(globalProjectName)[0];
			window.location.href = url+"LogisticsFile/File/"+$(this).val();
			console.log(url+"LogisticsFile/File/"+$(this).val());
		}
	});
	
	function dragUploadError(){
		$("div.fileName1").text("上传失败");
    $("input.dropUp1[value='切换到文件上传']").attr("disabled",false);
	}
	// 定义状态值
	var serFinRepHasDragUpload = 0;
	var serFinRepHasChangeUpload = 0;
	var idSel = function(id){return document.getElementById(id);};
	$(document).on("click",".dragTd",function(){
		$(".SerDel").prop("disabled","disabled");
		$(".dropFileBox").attr("id",$(this).siblings("td.SerCon_Ser").find("span.SerText").text());
		serFinRepHasDragUpload = 0;
		serFinRepHasChangeUpload = 0;
		$(".cover-color").show();
		$(".dropFile").css({top:"200px",height:"0"}).animate({height:"400px",top:"0"},700).css("display","block");
    	$(".dropFileBox").css("display","block");
    	$(".dropFileConTop").slideDown(600);
    	$("div.fileName1").text("");
		$('div.uploadInfo').text("将文件拖拽至此区域，即可上传！");
		$("#dropFileConTop").css("background","#fff");
		$("input.dropUp1").val("切换到文件上传");
		$("input.dropUp1[value='切换到文件上传']").attr("disabled",false);
		// init change上传
		$(".dropFileConMid").css("display","none");
		$(".serFinRepUploadName").val("");
		$(".isUpload").text("");
		$("#serFinRepUpload").val("");
		$(".progressIn").css("width","30px").text("0%");
	});

	var dropFileConTop = idSel("dropFileConTop");
	dropFileConTop.ondragover = function(e)
	{
		e.preventDefault();
		$('div.uploadInfo').text("释放鼠标，立即上传！");
		this.style.background = "#eee";
		return false;
	};
	
	dropFileConTop.ondragenter = function(e)
	{
		e.preventDefault();
		$('div.uploadInfo').text("上传进度：0%");
		this.style.background = "#999";
		return false;
	};

	dropFileConTop.ondragleave = function(e)
	{
		e.preventDefault();
		$('div.uploadInfo').text("将文件拖拽至此区域，即可上传！");
		this.style.background = "#fff";
		return false;
	};

	dropFileConTop.ondrop = function(e)
	{
		e.preventDefault();
		var file = e.dataTransfer.files;
		// 如果没有文件
		if(file.length < 1){
		    return false;
		}else if(file.length > 1){
			$("div.fileName1").text("");
			$('div.uploadInfo').text("将文件拖拽至此区域，即可上传！");
			$("#dropFileConTop").css("background","#fff");
		    $.MsgBox_Unload.Alert("上传提示","每次只能上传一个文件！");
		    return false;
		}
		var fileObj = file[0]; // 获取当前的文件对象
		var name = file[0].name;
		$("div.fileName1").text("已上传："+name);
		var fd = new FormData(); // 定义fromdata对象
      	fd.append("file",fileObj); // 传给后台的参数 “file” 参数名 fileObj 文件对象
      	var xhr = new XMLHttpRequest();
	    xhr.upload.addEventListener("progress",uploadProgress,false); // 传输进度
	    xhr.addEventListener("load",uploadComplete,false);// 传输成功
	    xhr.addEventListener("error",uploadFailed,false); //传输中出现错误
	    xhr.addEventListener("abort",uploadCanceled,false); //传输被用户取消
	      // xhr.addEventListener("loadstart", " 我是函数名 ",false); // 传输开始
	      // xhr.addEventListener("loadEnd", " 我是函数名 ",false); // 传输结束，但是不知道是否成功
	      
	      // xhr.onreadystatechange=function(){
       //      if(xhr.readyState==4){
       //        if(xhr.status==200){
       //            console.log(typeof xhr.responseText);
       //            console.log(xhr.responseText);
       //        }
       //      }
       //    };

	    xhr.open("POST", "SoftwareImplementationOperate", true); // 给后台提交的路径
	    xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest', 'Content-Type', 'multipart/form-data;');
	    xhr.send(fd);
		return false;
	};
	
	// 传输进度的方法 进度条显示
    function uploadProgress(evt) {
        if (evt.lengthComputable) {
          //evt.total 需要传输的总字符，evt.loaded 是已经上传的字节
          // if evt.lengthComputable 不为真  evt.total 等于 0
            var percentComplete = Math.floor(evt.loaded * 100 / evt.total);
            // 上传的进度状态
            $('div.uploadInfo').text("已上传："+percentComplete+"%");
        }
        else {//大小无法计算
            $('div.uploadInfo').text("大小无法计算");
        }
    }
    //传输成功的方法
    function uploadComplete(evt){
      //服务器返回的数据
      // var message = JSON.parse(evt.target.responseText);
      // var message = evt.target.responseText;
      // if(message.code == "success"){ // 后台返回的code值
      //   alert("上传成功！");
      // }else if(message.code == "fail"){
      //   alert("上传失败！");
      // }
        if(evt.target.responseText!="" && evt.target.responseText!="--" && evt.target.responseText!=null && evt.target.responseText !=undefined){
        	serFinRepHasDragUpload = 1;
        	$("input.dropUp1[value='切换到文件上传']").attr("disabled","disabled");
      	    $.MsgBox_Unload.Alert("上传提示","上传成功，请关闭上传框");

        }else{
        	serFinRepHasDragUpload = 0;
        	dragUploadError();
        }
    }
    // 传输中出现错误
    function uploadFailed(evt){
        serFinRepHasDragUpload = 0;
        dragUploadError();
        $.MsgBox_Unload.Alert("上传提示","上传出错");
    }
    // 上传由用户或者浏览器取消链接
    function uploadCanceled(evt){
		serFinRepHasDragUpload = 0;
        dragUploadError();
        $.MsgBox_Unload.Alert("上传提示","上传由用户或浏览器取消连接");
    }

    // 切换至文件上传
    $(".dropUp1").on("click",function(){
    	if($(this).val()=="切换到文件上传"){
    		$(".dropFileConMid").css({top:"0",height:"0",opacity:"0"}).animate({height:"250px",top:"0",opacity:"1"},600).css("display","block");
    		$(".dropFileConTop").slideUp(600);
    		$(this).val("切换到拖拽上传");
    	}else if($(this).val()=="切换到拖拽上传"){
    		$(".dropFileConMid").delay(200).hide(0);
    		$(".dropFileConMid").css({top:"0",height:"250px",opacity:"1"}).animate({height:"0",top:"0",opacity:"0"},600);
    		$(".dropFileConTop").slideDown(600);
    		$(this).val("切换到文件上传");
    	}
    });

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
            url: "SoftwareImplementationOperate",
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false, // 告诉jQuery不要去设置Content-Type请求头
            cache: false,
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
            	console.log("文件change上传响应值"+data); 
            	$("span.isUpload").text("上传成功！");   
            	$("input.dropUp1[value='切换到拖拽上传']").attr("disabled","disabled");
            	serFinRepHasChangeUpload = 1;
                console.log("上传成功!!!!");                        
            },
            error:function(){
            	$("input.dropUp1[value='切换到拖拽上传']").attr("disabled",false);
            	serFinRepHasChangeUpload = 0;
                $.MsgBox_Unload.Alert("上传提示","上传失败！");
            }
        });                             
    }

    // 点击浏览
    $("#serFinRepUpload").on("change",function(){
        $("span.isUpload").text("");
        $(".progressIn").css("width","30px");
        $(".progressIn").text("0%");
    	serFinRepHasChangeUpload = 0;
    	console.log("文件上传改变值"+$(this).val());
    	var newFileName1 = $(this).val().indexOf("\\fakepath\\")>-1?$(this).val().split("\\fakepath\\")[1]:$(this).val().split("\\").pop();
    	console.log("赋给input的值"+newFileName1);
    	$("input.serFinRepUploadName").val(newFileName1);
    });

    $("input.dropUp2").on("click",function(){
    	var judgeFile = $("input.serFinRepUploadName").val();
    	var judgeFile2 = $("#serFinRepUpload").val();
    	if(judgeFile && judgeFile2){
    		uploadFiles();
    	}else{
    		$.MsgBox_Unload.Alert("上传提示","请检查是否选择或更换了文件");
    	}
    });

    // 关闭上传
    $(".dropFileTit span").on("click",function(){
    	var serRepDownStr = '<span class="serRepDown" title="下载"><img src="image/download.png" height="20" width="20"></span>';
    	var uploadFileId = $(".dropFileBox").attr("id");
    	var closeDrapFile1;
    	var closeChangeFile1 = $("input.serFinRepUploadName").val();
    	var closeChangeFile2 = $("#serFinRepUpload").val();
    	if($(".fileName1").text().indexOf("已上传：")==0){
    		closeDrapFile1 = $(".fileName1").text().split("已上传：")[1];
    	}
    	if(serFinRepHasDragUpload==1 && closeDrapFile1!="" && closeDrapFile1!=null && closeDrapFile1!=undefined){
    		$("span.SerText:contains("+uploadFileId+")").parent().siblings("td.dragTd").html(closeDrapFile1+serRepDownStr);
    		var todayTime = globalGetToday();
    		$("span.SerText:contains("+uploadFileId+")").parent().siblings("td:nth-child(10)").text(todayTime);
    	}

    	if(serFinRepHasChangeUpload==1 && closeChangeFile1 && closeChangeFile2){
    		$("span.SerText:contains("+uploadFileId+")").parent().siblings("td.dragTd").html(closeChangeFile1+serRepDownStr);
    		var todayTime2 = globalGetToday();
    		$("span.SerText:contains("+uploadFileId+")").parent().siblings("td:nth-child(10)").text(todayTime2);
    	}
		$(".dropFileBox").slideUp(400);
		$(".SerDel").prop("disabled",false);
	});

	// 服务报告点击下载
	$(document).on("click","span.serRepDown",function(e){
		e.stopPropagation();
        var fileName = $(this).parent().text();
        var baseUrl = window.location.href.split(globalProjectName)[0];
        window.open(baseUrl+"LogisticsFile/File/"+fileName);
		console.log("服务报告下载"+baseUrl+"LogisticsFile/File/"+fileName);
	});

});

// 甘特图弹出
var quarterDayGap = 6*60*60*1000;
var curCustomerName = "";
$(document).on("click","#table1 .ProgressStatus",function(){
    if($(this).text()==""||$(this).text()=="--"){
        $.MsgBox_Unload.Alert("提示","无数据！");
        return false;
    }
    var curID = $(this).siblings(".editTd").attr("value");
    curCustomerName = $(this).siblings(".CustomerName").text().trim();
    var curPercent = $(this).text();
    $.ajax({
        type: "GET",
        url: "SoftwareImplementationOperate",
        data: {
            ID: curID,
            classify: "Modify"
        },
        dataType: "json"
    }).then(function(data){
        if(data.services.length<2){
            $.MsgBox_Unload.Alert("提示","无数据！");
        }else{
            var StartTimeArr = [];
            var EndTimeArr = [];
            var seriesData = [];
            var item = {};
                item.name = curCustomerName+"软件实施总进度";
            var seriesArr = [];
            var percentObj = {};
            // debugger;
            data.services.map(function(currentValue,index,arr){
                if(index>0){
                    // 期望时间
                    var iExpectedDate;
                    if(currentValue.ExpectedDate==""||currentValue.ExpectedDate=="--"){
                        iExpectedDate = new Date(globalGetToday()).getTime();
                    }else{
                        iExpectedDate = new Date(currentValue.ExpectedDate).getTime();
                    }
                    // 注册时间
                    var iRegisterTime;
                    if(currentValue.RegisterTime==""||currentValue.RegisterTime=="--"){
                        iRegisterTime = new Date("2017-07-01").getTime();
                    }else{
                        iRegisterTime = new Date(currentValue.RegisterTime).getTime();
                    }
                    // 完成时间
                    var iCompleteTime = currentValue.CompleteTime;
                    EndTimeArr.push(iExpectedDate);
                    StartTimeArr.push(iRegisterTime);
                    // 绘图所需数据
                    var item1 = {};
                    var shortTaskName = formatString(currentValue.Content.substring(0,20),7);
                    item1.taskName = shortTaskName;
                    item1.start = iRegisterTime - quarterDayGap;
                    item1.end = iExpectedDate + quarterDayGap*2;
                    var itemInPercent;
                    if(iCompleteTime==""||iCompleteTime=="--"){
                        item1.completed = {
                                amount: globalToPoint(calcPercent(iRegisterTime,iExpectedDate)),
                                fill: '#90ed7d'
                            };
                        itemInPercent = calcPercent(iRegisterTime,iExpectedDate);
                    }else{
                        item1.completed = {
                                amount: 1.0,
                                fill: '#90ed7d'
                            };
                        itemInPercent = "100%";
                    }
                    item1.parent = "master_gantt";
                    seriesArr.push(item1);
                    percentObj[shortTaskName] = itemInPercent;
                }
            });
            StartTimeArr.sort(ascCompare);
            EndTimeArr.sort(descCompare);
            var item2 = {};
            item2.taskName = formatString(curCustomerName+"总进度",8);
            item2.start = StartTimeArr[0] - quarterDayGap;
            item2.end = EndTimeArr[0] + quarterDayGap*2;
            item2.completed = {
                    amount: globalToPoint(curPercent),
                    fill: '#90ed7d'
                };
            item2.id = "master_gantt";
            percentObj[item2.taskName] = curPercent;
            seriesArr.unshift(item2);
            item.data = seriesArr;
            seriesData.push(item);
            console.log(seriesData);
            console.log(StartTimeArr);
            console.log(EndTimeArr);

            $("#gantt_container").empty();
            $(".gantt_Wrapper").slideDown(300);
            $(".cover-color").slideDown(300);
            Highcharts.ganttChart('gantt_container', {
                title: {
                    text: curCustomerName+"实施进度甘特图"
                },
                // xAxis: {
                //     currentDateIndicator: true,
                //     min: today - 3 * day,
                //     max: today + 18 * day
                // },
                xAxis: [{
                    grid: true,
                    type: 'datetime',
                    opposite: true,
                    // tickInterval: 1000 * 60 * 60 * 24 * 30, // Month
                    tickInterval: 1000 * 60 * 60 * 24, // Day
                    labels: {
                        format: '{value:%d}',
                        style: {
                            fontSize: '10px'
                        }
                    },
                    min: StartTimeArr[0] - quarterDayGap*4,
                    max: EndTimeArr[0] + quarterDayGap*4,
                    currentDateIndicator: true
                }, {
                    grid: true,
                    type: 'datetime',
                    opposite: true,
                    tickInterval: 1000 * 60 * 60 * 24 * 30, // Month
                    labels: {
                        format: '{value:%b}',
                        style: {
                            fontSize: '14px'
                        }
                    },
                    linkedTo: 0
                },{
                    grid: true,
                    type: 'datetime',
                    opposite: true,
                    tickInterval: 1000 * 60 * 60 * 24 * 365, // Year
                    labels: {
                        format: '{value:%Y}',
                        style: {
                            fontSize: '15px'
                        }
                    },
                    linkedTo: 0
                }],
                /*
                plotOptions: {
                    gantt: {
                        pathfinder: {
                            type: 'simpleConnect'
                        }
                    }
                },
                */
                tooltip: {
                    // xDateFormat: '%Y-%m-%d',
                    // shared: true
                    useHTML: true,
                    formatter: function() {
                        var intaskName = this.taskName.replace(/<br\/>/g,"");
                        var createTime = getYMDByMSEL(this.x + quarterDayGap);
                        var ipercent = "0%";
                        for(var kk in percentObj){
                            if(kk == this.taskName){
                                ipercent = percentObj[kk];
                            }
                        }
                        return '&nbsp;创建时间：<b>' + createTime +'</b><br/>&nbsp;内容：<b>' + intaskName + '</b><br/>&nbsp;已完成：<b>'+ipercent+'</b>';
                    }
                },
                series: seriesData
            });
        }
    },
    function(){
        $.MsgBox_Unload.Alert("提示","服务器繁忙，稍后重试！");
    });
    
});

// 关闭甘特图
$(".gantt_Wrapper_top_r").click(function(){
    $(".gantt_Wrapper").slideUp(300);
    $(".cover-color").slideUp(300);
});

// 导出PDF
$(".gantt_Wrapper_top_m").click(function(){
    // html2canvas(
    //     document.getElementById("gantt_container"),
    //     {
    //         dpi: 172,//导出pdf清晰度
    //         onrendered: function (canvas) {
    //             var contentWidth = canvas.width;
    //             var contentHeight = canvas.height;

    //             //一页pdf显示html页面生成的canvas高度;
    //             var pageHeight = contentWidth / 592.28 * 841.89;
    //             //未生成pdf的html页面高度
    //             var leftHeight = contentHeight;
    //             //pdf页面偏移
    //             var position = 0;
    //             //html页面生成的canvas在pdf中图片的宽高（a4纸的尺寸[595.28,841.89]）
    //             var imgWidth = 595.28;
    //             var imgHeight = 592.28 / contentWidth * contentHeight;

    //             var pageData = canvas.toDataURL('image/jpeg', 1.0);
    //             var pdf = new jsPDF('', 'pt', 'a4');

    //             //有两个高度需要区分，一个是html页面的实际高度，和生成pdf的页面高度(841.89)
    //             //当内容未超过pdf一页显示的范围，无需分页
    //             if (leftHeight < pageHeight) {
    //                 pdf.addImage(pageData, 'JPEG', 0, 0, imgWidth, imgHeight);
    //             } else {
    //                 while (leftHeight > 0) {
    //                     pdf.addImage(pageData, 'JPEG', 0, position, imgWidth, imgHeight);
    //                     leftHeight -= pageHeight;
    //                     position -= 841.89;
    //                     //避免添加空白页
    //                     if (leftHeight > 0) {
    //                         pdf.addPage();
    //                     }
    //                 }
    //             }
    //             pdf.save(curCustomerName+'实施进度甘特图.pdf');
    //         },
    //         //背景设为白色（默认为黑色）
    //         background: "#fff"  
    //     });
    
    // var target = document.getElementById(gantt_container);
    $.Response_Load.Before("导出提示","正在传输数据......",200);
    setTimeout(function(){
        var width = $("#gantt_container").width() ; 
        var height =  $("#gantt_container").height() ;
        var canvas = document.createElement("canvas");  
            canvas.width = width * 2.2;  
            canvas.height = height * 3;  
            canvas.style.width = width + "px";  
            canvas.style.height = height + "px";  
        var context = canvas.getContext("2d");//然后将画布缩放，将图像放大两倍画到画布上  
            context.scale(2,2);   
            html2canvas(document.getElementById("gantt_container"),{
                canvas:canvas,
                  onrendered:function(canvas) {
                      var contentWidth = canvas.width;
                      var contentHeight = canvas.height;
                      //一页pdf显示html页面生成的canvas高度;
                      var pageHeight = contentWidth / 592.28 *650;
                      //未生成pdf的html页面高度
                      var leftHeight = contentHeight;
                      //页面偏移
                      var position = 0;
                      //a4纸的尺寸[595.28,841.89]，html页面生成的canvas在pdf中图片的宽高
                      var imgWidth = 595.28;
                      var imgHeight = 595.28/contentWidth * contentHeight;
                      var pageData = canvas.toDataURL('image/png', 1.0);
                      // var pageData = canvas.toDataURL('image/jpeg', 1.0);
                      var pdf = new jsPDF('', 'pt', 'a4');

                      //有两个高度需要区分，一个是html页面的实际高度，和生成pdf的页面高度(841.89)
                      //当内容未超过pdf一页显示的范围，无需分页
                      if (leftHeight < pageHeight) {
                          pdf.addImage(pageData, 'PNG', 0, position+80, 600, imgHeight);
                          // pdf.addImage(pageData, 'JPEG', 0, position+80, 600, imgHeight);
                          pdf.addImage(pdfTitle, 'JPG', 0, 0, 600, 70);  
                          pdf.addImage(pdfFoot, 'JPG', 0, 781.89, 600, 60); 
                          // pdf.addImage(pageData, 'PNG', 0, 0, imgWidth, imgHeight); 
                      } else {
                          while(leftHeight > 0) {
                                  // pdf.addImage(pageData, 'PNG', 0, position, imgWidth, imgHeight);
                                  // leftHeight -= pageHeight;
                                  // position -= 715;
                              pdf.addImage(pageData, 'PNG', 0, position+80, 600, imgHeight);
                              // pdf.addImage(pageData, 'JPEG', 0, position+80, 600, imgHeight);
                              pdf.addImage(pdfTitle, 'JPG', 0, 0, 600, 70); 
                              pdf.addImage(pdfFoot, 'JPG', 0, 781.89, 600, 60);  
                              leftHeight -= pageHeight;
                              position -= 713;

                              //避免添加空白页
                              if(leftHeight > 0) {
                                pdf.addPage();
                              }
                          }
                      }
                      pdf.save(curCustomerName+'实施进度甘特图.pdf');
                  },
                  //背景设为白色（默认为黑色）
                  background: "#fff"
            }).then(function(canvas){
                $.Response_Load.After("导出完成！",400);
            });
    },50);


});