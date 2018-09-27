/**
 * Created by eoulu on 2017/3/29.
 */
// 翻页组件按钮逻辑
// flag 为按钮ID后缀  如 pageStyle(CurrentPage, pageCount, "2");
function pageStyle(currentPage, pageCounts, flag) {
    flag = flag || "";
    if (pageCounts == 1) {
        $("#fistPage" + flag + ", #upPage" + flag + ", #nextPage" + flag + ", #lastPage" + flag + ", #Gotojump" + flag).prop("disabled", "disabled").removeClass("btn-primary").addClass("btn-default");
    } else if (currentPage == 1) {
        $("#fistPage" + flag + ", #upPage" + flag).prop("disabled", "disabled").removeClass("btn-primary").addClass("btn-default");
        $("#lastPage" + flag + ", #nextPage" + flag + ", #Gotojump" + flag).prop("disabled", false).removeClass("btn-default").addClass("btn-primary");
    } else if (currentPage == pageCounts) {
        $("#lastPage" + flag + ", #nextPage" + flag).prop("disabled", "disabled").removeClass("btn-primary").addClass("btn-default");
        $("#fistPage" + flag + ", #upPage" + flag + ", #Gotojump" + flag).prop("disabled", false).removeClass("btn-default").addClass("btn-primary");
    } else {
        $("#lastPage" + flag + ", #nextPage" + flag + ", #fistPage" + flag + ", #upPage" + flag + ", #Gotojump" + flag).prop("disabled", false).removeClass("btn-default").addClass("btn-primary");
    }
}

if($('input[name="selected"]:checked').val()=='singleSelect'){
    $('.select-content').css('margin-left','33%');
}else{
    $('.select-content').css('margin-left','23%');

}
function Check(selected) {
	 if (selected == "singleSelect") {
        $('.select2').hide();
        $('.select-content').css('margin-left','33%');
    } else {
        $('.select2').show();
        $('.select-content').css('margin-left','23%');
    }
}

function INSearch() {
    $('#search').val('search');
    $('#top_text_from').submit();
}
function Cancel() {
	window.location.href= 'MachineDetails';
}
$('#searchContent1').keypress(function (event) {
    $('#search').val('search');
    var keynum = (event.keyCode ? event.keyCode : event.which);
    if (keynum == '13') {
        $('#top_text_from').submit();
    }
});
$('#searchContent2').keypress(function (event) {
    $('#search').val('search');
    var keynum = (event.keyCode ? event.keyCode : event.which);
    if (keynum == '13') {
        $('#top_text_from').submit();
    }
});

//-------------------------------------------提交修改机台信息-----------------------------------------------
$('#update_submit').click(function () {
	var ID = $('.contract_update .table_title').attr("ID");
    var CustomerUnit = $('.contract_update input[name="CustomerUnit"]').val();
	var CustomerName = $('.contract_update input[name="CustomerName"]').val();
	var Model = $('.contract_update input[name="Model"]').val();
	var SN = $('.contract_update input[name="SN"]').val();
	var ContractNO = $('.contract_update input[name="ContractNO"]').val();
	var InstalledTime = $('.contract_update input[name="InstalledTime"]').val();
	/*var CustomerID = $('.contract_update input[name="CustomerID"]').text();*/
	var CustomerID=$('.contract_update td.updateInsertId').attr('value');
	var classify = "Modify";
//点击提交以后避免用户重复提交
$("#update_submit").attr("disabled","disabled");
$("#update_submit").css({
	"background":"#dddddd",
	"color":"#808080",
	"border":"none",
	"box-shadow":"0 0 0 0 #f8fcfd"
});
$.MsgBox.Prompt("提示", "冲锋鸡正在处理中......请等待");
    $.ajax({
        type : 'get',
        url : 'MachineDetailsOperate',
        data : {
        	ID : ID ,
        	CustomerUnit: CustomerUnit,
        	CustomerName: CustomerName,
        	Model: Model,
        	SN: SN,
        	ContractNO: ContractNO,
        	InstalledTime: InstalledTime,
        	classify: classify,
        	CustomerID:CustomerID
        },
        dataType : 'json',
        success : function (data) {
        	$("#mb_box,#mb_con").remove();
            $.MsgBox.Alert('提示','修改成功');
            $('.cover-color').hide();
            $('.contract_update').hide();
        },
        error : function () {
        	$("#mb_box,#mb_con").remove();
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});


// --------------------------------------------提交添加机台信息-----------------------------------------------
$(document).on("click", "#add_submit", function () {
	var CustomerUnit = $('.contract_add input[name="CustomerUnit"]').val();
	var CustomerName = $('.contract_add input[name="CustomerName"]').val();
	var Model = $('.contract_add input[name="Model"]').val();
	var SN = $('.contract_add input[name="SN"]').val();
	var ContractNO = $('.contract_add input[name="ContractNO"]').val();
	var InstalledTime = $('.contract_add input[name="InstalledTime"]').val();
	/*var CustomerID = $('.contract_add input[name="CustomerID"]').val();*/
	var CustomerID=$('.contract_add td.addInsertId').attr('value');
    console.log(CustomerID);
/*	 var CustomerID = $(".customerID").text();*/
	 /*alert(CustomerID);*/
	var classify = "Add";
	console.log(CustomerUnit)
	console.log(CustomerName)
	console.log(Model)
	console.log(SN)
	console.log(ContractNO)
	console.log(InstalledTime)
	console.log(classify)
	
	//点击提交以后避免用户重复提交
$("#add_submit").attr("disabled","disabled");
$("#add_submit").css({
	"background":"#dddddd",
	"color":"#808080",
	"border":"none",
	"box-shadow":"0 0 0 0 #f8fcfd"
});
$.MsgBox.Prompt("提示", "冲锋鸡正在处理中......请等待");
	
    $.ajax({
        type: 'get',
        url: 'MachineDetailsOperate',
        data: {
        	CustomerUnit: CustomerUnit,
        	CustomerName: CustomerName,
        	Model: Model,
        	SN: SN,
        	ContractNO: ContractNO,
        	InstalledTime: InstalledTime,
        	classify: classify,
        	CustomerID:CustomerID
        },
        dataType: 'json',
        success: function (data) {
            console.log(data);
            console.log(typeof data);
            // var newData = JSON.parse(data);
            console.log(data.indexOf("message:true"));
            if(data.indexOf("message:true")>-1){
                $("#mb_box,#mb_con").remove();
                $.MsgBox.Alert("提示", "添加成功！");
            }
        },
        error: function () {
        	$("#mb_box,#mb_con").remove();
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});


//--------------------------------------------删除合同信息-----------------------------------------------

//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function () {
    window.location.reload();
});

$(function(){
    pageStyle(Number($('#currentPage').html()), Number($('#allPage').html()), "");
	if($("#queryType_input").val() == "common" || $("#queryType_input").val() == "singleSelect"){
        $('.select2').hide();
    }else {
        $('.select2').show();
    }

    /* awesomplete */
    var comboplete = new Awesomplete('#global_add_update_module_add input.form-control.dropdown-input', {
        minChars: 0,
        list: ["交付", "尾款", "完结"]
    });
    $('#global_add_update_module_add button.awesomplete_btn').click(function(){
        if (comboplete.ul.childNodes.length === 0) {
            comboplete.minChars = 0;
            comboplete.evaluate();
        }
        else if (comboplete.ul.hasAttribute('hidden')) {
            comboplete.open();
        }
        else {
            comboplete.close();
        }
    });

    var comboplete2 = new Awesomplete('#global_add_update_module_update input.form-control.dropdown-input', {
        minChars: 0,
        list: ["交付", "尾款", "完结"]
    });
    $('#global_add_update_module_update button.awesomplete_btn').each(function(i, el){
        el.addEventListener("click", function() {
            if (comboplete2.ul.childNodes.length === 0) {
              comboplete2.minChars = 0;
              comboplete2.evaluate();
            }
            else if (comboplete2.ul.hasAttribute('hidden')) {
              comboplete2.open();
            }
            else {
              comboplete2.close();
            }
        });
    });

	//根据装机时间和拜访时间标红
	function fun_submit(visit_time,t){
        var date1 = new Date(visit_time);
        var date2 = new Date(date1);
        date2.setDate(date1.getDate()+t);
        var times = date2.getFullYear()+"-"+(date2.getMonth()+1)+"-"+date2.getDate();
        return times;
    }
	function changeRed(thetime,tr){
			var   d=new   Date(Date.parse(thetime.replace(/-/g,"/")));
			//今天时间，标准
			var   curDate=new   Date();
		    var da=d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate();
		    var cur=curDate.getFullYear() + '-' + (curDate.getMonth() + 1) + '-' + curDate.getDate(); 
			if(d >curDate){
				/*tr.find(".CustomerName").css('color','green');*/
				}else{
					/*alert("时间到了你要去拜访了");*/
					tr.find(".CustomerUnit").css('color','red');
				}
		}
	for(var  i= 1 ;i<$("#global_table_style tr").length;i++){

	 var tr=$("#global_table_style tr").eq(i);
	 var CustomerLevel =tr.find(".CustomerLevel").text();
		var InstalledTime =tr.find(".InstalledTime").text();
		var VisitTime = tr.find(".VisitTime").text();
		/*alert(CustomerLevel);*/
		if(VisitTime == '' || VisitTime == '--' ){
			if(InstalledTime != '' && InstalledTime != '--' ){
				var	visit_time = InstalledTime;
			}else{
				var	visit_time = '';
				return;
			}
		}else{
			var  visit_time  = VisitTime ;
		}
		/*alert(visit_time);*/
		var VisitTime_A = fun_submit(visit_time,75);
		var VisitTime_B = fun_submit(visit_time,150);
		var VisitTime_C = fun_submit(visit_time,300);
		/*alert(VisitTime_A)*/
	if(CustomerLevel == 'A'){
			//最后一次拜访时间——要判断的时间
			
			changeRed(VisitTime_A,tr);
	}else if(CustomerLevel == 'B'){
		changeRed(VisitTime_B,tr);
				
	}else if(CustomerLevel == 'C'){
		changeRed(VisitTime_C,tr);		
	}
}
});

/*原script标签*/
/****************** 跳页 **********************/
function FistPage(arg) {
    if(arg.split('?')[0]=='GetMachineDetailsRoute'){
        $('#search').val('search');
        $("#top_text_from").attr("action", arg+ "1");
        $('#top_text_from').submit();
    }else{ 
        window.location.href = arg+ "1";
     } 
}
function UpPage(arg) {
    if(arg.split('?')[0]=='GetMachineDetailsRoute'){
         $('#search').val('search');
         $("#top_text_from").attr("action", arg);
          $('#top_text_from').submit();
    }else{ 
        window.location.href = arg;
     } 
}
function NextPage(arg) {
    if(arg.split('?')[0]=='GetMachineDetailsRoute'){
         $('#search').val('search');
         $("#top_text_from").attr("action", arg);
          $('#top_text_from').submit();
    }else{ 
        window.location.href = arg;
     } 
}
function PageJump(arg) {
    var jumpNumber = document.getElementById("jumpNumber").value;
    if (jumpNumber == null || jumpNumber == 0) {
        jumpNumber = $('#currentPage').html();
    } else if (jumpNumber > parseInt($('#allPage').html())) {
        jumpNumber = $('#allPage').html();
    }
    /* window.location.href = arg + jumpNumber; */
    if(arg.split('?')[0]=='GetMachineDetailsRoute'){
         $('#search').val('search');
         $("#top_text_from").attr("action", arg + jumpNumber);
          $('#top_text_from').submit();
    }else{ 
        
        window.location.href = arg + jumpNumber;
     } 
}
function LastPage(arg) {
    var jumpNumber = parseInt($('#allPage').html());
    if(arg.split('?')[0]=='GetMachineDetailsRoute'){
         $('#search').val('search');
         $("#top_text_from").attr("action", arg + jumpNumber);
          $('#top_text_from').submit();
    }else{ 
        window.location.href = arg + jumpNumber;
     } 
}
/*原script标签 end*/

/*2018-09-27更新*/
var MachineDetailsState = new Object();
MachineDetailsState.statusNumMap = {
    "交付": "1",
    "尾款": "2",
    "完结": "3",
};
MachineDetailsState.addSubmitObj = new Object();
MachineDetailsState.addSubmitObj.CustomerID = null;
MachineDetailsState.addSubmitObj.classify = null;
MachineDetailsState.addSubmitObj.Model = null;
MachineDetailsState.addSubmitObj.SN = null;
MachineDetailsState.addSubmitObj.ContractNO = null;
MachineDetailsState.addSubmitObj.InstalledTime = null;
MachineDetailsState.addSubmitObj.Status = null;
MachineDetailsState.addSubmitObj.Responsible = null;
MachineDetailsState.addSubmitObj.CurrentProgress = null;
MachineDetailsState.addSubmitObj.LatestProgress = null;

MachineDetailsState.updateSubmitObj = new Object();
MachineDetailsState.updateSubmitObj.ID = null;
MachineDetailsState.updateSubmitObj.CustomerID = null;
MachineDetailsState.updateSubmitObj.classify = null;
MachineDetailsState.updateSubmitObj.Model = null;
MachineDetailsState.updateSubmitObj.SN = null;
MachineDetailsState.updateSubmitObj.ContractNO = null;
MachineDetailsState.updateSubmitObj.InstalledTime = null;
MachineDetailsState.updateSubmitObj.Status = null;
MachineDetailsState.updateSubmitObj.Responsible = null;
MachineDetailsState.updateSubmitObj.CurrentProgress = null;
MachineDetailsState.updateSubmitObj.LatestProgress = null;

/*添加修改究极整合*/
$(document).on("click", "[data-targetmodule]", function(){
    var targetmodule = $(this).data("targetmodule");
    if(targetmodule == "add"){
        $("#global_add_update_module_cover, #global_add_update_module_add").slideDown(200);
        $("[id^='add_infomation_']").each(function(){
            $(this).val("");
        });
        $("#global_add_update_module_add div.append_line_div tbody").empty();
    }else if(targetmodule == "update"){
        $("#global_add_update_module_cover, #global_add_update_module_update").slideDown(200);
        var tr = $(this).parent(), str = "";
        $("[id^='update_infomation_']").each(function(){
            var subClassName = $(this).attr("id").replace("update_infomation_", "td_");
            $(this).val(globalDateDataHandle(tr.find("."+subClassName).attr("title"), ""));
        });
        var iCurrentProgress = tr.find(".td_CurrentProgress").text();
        console.warn(iCurrentProgress);
        $("#global_add_update_module_update div.append_line_div tbody").empty().append(str);
    }
});
// 关闭
$(document).on("click", ".global_add_update_module_title>span.glyphicon, .global_add_update_module_cancel", function(){
    var parents = $(this).parents("[data-parents='addANDupdate']");
    $("#global_add_update_module_cover").slideUp(200);
    parents.slideUp(200);
    if(parents.data("classify") == "add"){
        for(var k in MachineDetailsState.addSubmitObj){
            MachineDetailsState.addSubmitObj[k] = null;
        }
    }else if(parents.data("classify") == "update"){
        for(var kk in MachineDetailsState.updateSubmitObj){
            MachineDetailsState.updateSubmitObj[kk] = null;
        }
    }
});
// 提交
$(document).on("click", ".global_add_update_module_submit", function(){
    var parents = $(this).parents("[data-parents='addANDupdate']");
    var iThat = $(this);
    if(parents.data("classify") == "add"){
        for(var kk in MachineDetailsState.addSubmitObj){
            if(kk=="classify"){
                MachineDetailsState.addSubmitObj[kk] = "Add";
                continue;
            }
            if(kk=="CustomerID" || kk=="CurrentProgress" || kk=="LatestProgress"){
                continue;
            }
            MachineDetailsState.addSubmitObj[kk] = $("#add_infomation_"+kk).val();
        }
        // 表单验证
        for(var kkk in MachineDetailsState.addSubmitObj){
            MachineDetailsState.addSubmitObj[kkk] = globalDataHandle(MachineDetailsState.addSubmitObj[kkk],"").trim();
            if(kkk=="CustomerID"&&MachineDetailsState.addSubmitObj[kkk]==""){
                $.MsgBox_Unload.Alert("提示","未选择客户单位！");
                return false;
            }
        }
    }else if(parents.data("classify") == "update"){
        
    }
});
/*添加修改究极整合 end*/
// 添加一条进展详情
$(".global_add_update_module span.append_line").click(function(){
    var tbody = $(this).parent().next().find("tbody");
    var len = tbody.children("tr").length + 1;
    var str = '<tr>'+
        '<td class="xuhao"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span><span class="span_num">'+len+'</span></td>'+
        '<td class="progress_detail" contenteditable="true"></td>'+
        '<td class="progress_detail_time"><input type="date" class="form-control" value="'+globalGetToday(false)+'"></td>'+
      '</tr>';
    tbody.append(str);
});

// 删除一条进展详情
$(document).on("click", "div.append_line_div .xuhao>span.glyphicon", function(){
    var tbody = $(this).parents("tbody");
    $(this).parent().parent().remove();
    tbody.children("tr").each(function(i, el){
      $(el).find("span.span_num").text(i+1);
    });
});

// 客户搜索
function addSearchCustomer(){
    var add = $("#add_infomation_CustomerUnit").val();
    if(add == "") return false;
    var CustomerName = add.trim();
    if(CustomerName == "" || CustomerName == "--"){
        $.MsgBox_Unload.Alert("提示", "客户单位搜索值为空或格式错误！");
        return false;
    }
    eouluGlobal.C_getCustomerUnitInfo(CustomerName, function(data){
        if(data.length > 1){
            var str = '';
            data.map(function(v, i, arr){
                if(i > 0){
                    str+='<option data-iid="'+v.ID+'" value="'+v.CustomerName+'" data-contact="'+v.Contact+'">'+v.CustomerName+'&nbsp;:&nbsp;'+v.Contact+'</option>';
                }
            });
            $("#add_infomation_CustomerUnit").siblings("select[name='CustomerUnit_select']").empty().append(str).slideDown(150);
        }
    },function(){
        $.MsgBox_Unload.Alert("提示", "网络繁忙！");
    },null);
}
function updateSearchCustomer(){
    var update = $("#update_infomation_CustomerUnit").val();
    if(update == "") return false;
    var CustomerName = update.trim();
    if(CustomerName == "" || CustomerName == "--"){
        $.MsgBox_Unload.Alert("提示", "客户单位搜索值为空或格式错误！");
        return false;
    }
    eouluGlobal.C_getCustomerUnitInfo(CustomerName, function(data){
        if(data.length > 1){
            var str = '';
            data.map(function(v, i, arr){
                if(i > 0){
                    str+='<option data-iid="'+v.ID+'" value="'+v.CustomerName+'" data-contact="'+v.Contact+'">'+v.CustomerName+'&nbsp;:&nbsp;'+v.Contact+'</option>';
                }
            });
            $("#update_infomation_CustomerUnit").siblings("select[name='CustomerUnit_select']").empty().append(str).slideDown(150);
        }
    },function(){
        $.MsgBox_Unload.Alert("提示", "网络繁忙！");
    },null);
}

$("#add_infomation_CustomerUnit").on("input propertychange", _.debounce(addSearchCustomer, 350, { 'maxWait': 1000 }));
$("#update_infomation_CustomerUnit").on("input propertychange", _.debounce(updateSearchCustomer, 350, { 'maxWait': 1000 }));


$(document).on("click", "select[name='CustomerUnit_select']>option", function(e){
    e.stopPropagation();
    $(this).parent().siblings("input[type='text']").val($(this).val()).parents("[data-parents='addANDupdate']").find("input[data-customername]").val($(this).data("contact"));
    $(this).parent().slideUp(150);
});
// 客户搜索清空
$("div.form-group.has-feedback>div.col-sm-9>.form-control-feedback").click(function(){
    $(this).prev().val("").siblings("select[name='CustomerUnit_select']").slideUp(150);
});
// 点击document收起select
$(document).on("click", function(){
    $("select[name='CustomerUnit_select']").slideUp(150);
});
/*2018-09-27更新 end*/