/**
 * Created by eoulu on 2017/3/29.
 */
// alert('MachineDetails');
$('.cover-color').height($(document).height()-80);

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
    /*$('#search').val('cancel');
    $('input[name="searchContent1"]').val('');
    $('input[name="searchContent2"]').val('');
    $('#top_text_from').submit();*/
	window.location.href='MachineDetails';
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

/*// ----------------------------------------搜索框----------------------------------------------
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
function selectSearch() {
	if($('.time').css("display") == "none" ){   //非货期查询
		if($(".select2").css("display") == "none" ){  
			if($("#searchContent1").val()!=""){
				$('#search').val('search');
			    $('#top_text_from').submit();
			}
		}
		else{               
			if($("#searchContent1").val()!=""&&$("#searchContent2").val()!=""){
				$('#search').val('search');
			    $('#top_text_from').submit();
			}
		}
	}
	else{              		   //货期查询   时间框
		if($('.select2').css("display") == "none" ){    //单一货期查询
			var startTimeDate = $(".select1 .startTime").val().replace("-","").replace("-","");
			var endTimeDate = $(".select1 .endTime").val().replace("-","").replace("-","");
			if(startTimeDate < endTimeDate){
				$('#search').val('search');
			    $('#top_text_from').submit();
			}
			else{
				alert("请正确输入时间！")
			}
		}
		else{						//组合货期查询
			var start1TimeDate = $(".select1 .startTime").val().replace("-","").replace("-","");
			var end1TimeDate = $(".select1 .endTime").val().replace("-","").replace("-","");
			var start2TimeDate = $(".select2 .startTime").val().replace("-","").replace("-","");
			var end2TimeDate = $(".select2 .endTime").val().replace("-","").replace("-","");
			console.log(start1TimeDate)
			console.log(start2TimeDate)
			if(start1TimeDate && !start2TimeDate){
				if(start1TimeDate < end1TimeDate ){
					$('#search').val('search');
				    $('#top_text_from').submit();
				}
				else{
					alert("请正确输入时间！")
				}
			}
			else if(!start1TimeDate && start2TimeDate){
				if(start2TimeDate < end2TimeDate){
					$('#search').val('search');
				    $('#top_text_from').submit();
				}
				else{
					alert("请正确输入时间！")
				}
			}
			else if(start1TimeDate && start2TimeDate){
				if(start1TimeDate < end1TimeDate && start2TimeDate < end2TimeDate){
					$('#search').val('search');
				    $('#top_text_from').submit();
				}
				else{
					alert("请正确输入时间！")
				}
			}
			
			
		}
	}
    
}
function selectCancel() {
    $('#search').val('cancel');
    $('input[name="searchContent1"]').val('');
    $('input[name="searchContent2"]').val('');
    $('input[name="start_time1"]').val('');
    $('input[name="end_time1"]').val('');
    $('input[name="start_time2"]').val('');
    $('input[name="end_time2"]').val('');
    $('#top_text_from').submit();
}
$('#searchContent1').keypress(function(event){ 
    $('#search').val('search');
    var keynum = (event.keyCode ? event.keyCode : event.which);    
    if(keynum == '13'){  
        $('#top_text_from').submit();
    }    
});   
$('#searchContent2').keypress(function(event){ 
    $('#search').val('search');
    var keynum = (event.keyCode ? event.keyCode : event.which);    
    if(keynum == '13'){  
            $('#top_text_from').submit();
    }    
}); 

$('#type1').click(function(){
	if($(this).val().indexOf('货期')>=0){
		$('#searchContent1').hide();
		$('.select1 .time').show();
	}else{
		$('#searchContent1').show();
		$('.select1 .time').hide();
	}	
});

$('#type2').click(function(){
	if($(this).val().indexOf('货期')>=0){
		$('#searchContent2').hide();
		$('.select2 .time').show();
	}else{
		$('#searchContent2').show();
		$('.select2 .time').hide();
	}	
});*/

// --------------------------------------------添加合同信息-----------------------------------------------
function AddContract() {
	$('.contract_add input[type="text"]').val('');
    $('.contract_add .CustomerUnit').val("");
	$('.contract_add input[type="date"]').val('');
	$('.contract_add select').each(function(){
	    $(this).find('option:checked').prop("selected",false);
	    $(this).find('option').filter(function(){return $(this).text()=='';}).prop("selected",true);
	 })
    $('.cover-color').show();
    $('.contract_add').show();
};
$('.contractAdd_close').click(function () {
    $('.cover-color').hide();
    $('.contract_add').hide();
});
$('.contract_add .search').mousedown(function () {
    $('.contract_add select[name="whether_to_pay"]').show();

});
$('.contract_add .search').change(function () {
    $('.contract_add select[name="whether_to_pay"]').hide();

});

$('#add_cancel').click(function () {
    $('.cover-color').hide();
    $('.contract_add').hide();
});
    
//-------------------------------------------修改机台信息-----------------------------------------------
$('.contract-edit').click(function () {
    var tr=$(this).parent();
    var contract_update=$('.contract_update');
    contract_update.find('.table_title').attr("ID",tr.find('td').eq(0).attr("value"));
    contract_update.find('input[name="CustomerUnit"]').val(tr.find('td').eq(2).text());
    contract_update.find('input[name="CustomerName"]').val(tr.find('td').eq(3).text());
    contract_update.find('input[name="Model"]').val(tr.find('td').eq(4).text());
    contract_update.find('input[name="SN"]').val(tr.find('td').eq(5).text());
    contract_update.find('input[name="ContractNO"]').val(tr.find('td').eq(6).text());
    contract_update.find('input[name="InstalledTime"]').val(tr.find('td').eq(7).text());
    // contract_update.find('input[name="CustomerID"]').val(tr.find('td.CustomerID').text());
    $(".contract_update td.updateInsertId").attr("value",tr.find('td.CustomerID').text());
    $('.cover-color').show();
    contract_update.show();
}) ;

$('.contractUpdate_close').click(function () {
    $('.cover-color').hide();
    $('.contract_update').hide();
});

$('.contract_update input[name="CustomerID"]').keyup(function () {
  
	var model = $(this).val();
  
    $('.contract_update select[name="customer_name"]').find('option').each(function () {
        if($(this).val().indexOf(model)!=-1){
            $(this).show();
        } else{
            $(this).hide();
        }
    });

    $('.contract_update select[name="customer_name"]').show();

});

$(document).on("click", '.contract_update select[name="customer_name"] option', function () {
    $('.contract_update select[name="customer_name"]').hide();
    $('.contract_update input[name="CustomerID"]').val($('.contract_update select[name="customer_name"]').val());
});

$(document).on("click", '.contract_update select[name="CustomerUnit"] option', function () {
    $('.contract_update select[name="CustomerUnit"]').hide();
    $('.contract_update input[name="CustomerUnit"]').val($('.contract_update select[name="CustomerUnit"]').val());
    $('.contract_update input.CustomerName').val($(this).attr("text"));
    $('.contract_update td.updateInsertId').attr("value",$(this).attr("id"));
});
//客户名字
$('.contract_update input[name="CustomerName"]').keyup(function () {
	  
	var model = $(this).val();
  
    $('.contract_update select[name="CustomerName"]').find('option').each(function () {
        if($(this).val().indexOf(model)!=-1){
            $(this).show();
        } else{
            $(this).hide();
        }
    });

    $('.contract_update select[name="CustomerName"]').show();

});

$(document).on("click", '.contract_update select[name="CustomerName"] option', function () {
    $('.contract_update select[name="CustomerName"]').hide();
    $('.contract_update input[name="CustomerName"]').val($('.contract_update select[name="CustomerName"]').val());
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
    console.log(CustomerID)
	var classify = "Modify";
	console.log(ID)
	console.log(CustomerUnit)
	console.log(CustomerName)
	console.log(Model)
	console.log(SN)
	console.log(ContractNO)
	console.log(InstalledTime)
	console.log(classify)
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
$('#update_cancel').click(function () {
    $('.cover-color').hide();
    $('.contract_update').hide();
});


//--------------------------------------------合同配置信息页面-----------------------------------------------
$('.contract-show').click(function () {
    var ContractNO=$(this).parent().parent().find("td").eq(6).text();
    $.ajax({
        type: 'get',
        url: 'GetMachineDetailsOther',
        data: {
        	ContractNO: ContractNO
        },
        dataType: 'json',
        success: function (data) {
        	console.log(data)
           $('.contract .contractPage tbody').html('');
            for (var i = 1; i < data.length; i++) {
                var tr = '<tr>' +
                    '<td style="display: none">' + data[i].ID + '</td>' +
                    '<td>' + data[i].OrderID + '</td>' +
                    '<td>' + data[i].EquipmentModel + '</td>' +
                    '<td>' + data[i].Remarks + '</td>' +
                    '<td><input value="' + data[i].Number + '" type="text" style="width: 45px;" disabled="disabled"></td>' +
                    '<td>' + data[i].Date + '</td>' +
                    '<td>' + data[i].ExceptDate + '</td>' +
                    '<td>' + data[i].DeliveryNumber + '</td>' +
                    '<td>' + data[i].Status + '</td>' +
                    '</tr>';
                $('.contract .contractPage tbody').append(tr);
            }
            $('.cover-color').show();
            $('.contract').show();
        },
        error: function () {
             $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
           
        }
    });

});
$('.contract_close').click(function () {
    $('.cover-color').hide();
    $('.contract').hide();
});
//选择客户
$('.contract_add input[name="CustomerID"]').keyup(function () {
	/*alert(5)*/
    var model = $(this).val();
   /* alert(model)*/
    $('.contract_add select[name="customer_name"]').find('option').each(function () {
        if($(this).val().indexOf(model)!=-1){
            $(this).show();
        } else{
            $(this).hide();
        }
    });

    $('.contract_add select[name="customer_name"]').show();

});

$(document).on("click", '.contract_add select[name="customer_name"] option', function () {
    $('.contract_add select[name="customer_name"]').hide();
    $('.contract_add input[name="CustomerID"]').val($('.contract_add select[name="customer_name"]').val());
});
//收起select[name=customer_name]
$(document).on("click", function () {
    $('.contract_add select[name="customer_name"]').hide();
    $('.contract_add select[name="CustomerUnit"]').hide();
    $('.contract_add select[name="CustomerName"]').hide();
    $('.contract_update select[name="customer_name"]').hide();
    $('.contract_update select[name="CustomerUnit"]').hide();
    $('.contract_update select[name="CustomerName"]').hide();
   
});

$(document).on("click", '.contract_add select[name="CustomerUnit"] option', function () {
    $('.contract_add select[name="CustomerUnit"]').hide();
    $('.contract_add input[name="CustomerUnit"]').val($('.contract_add select[name="CustomerUnit"]').val());
    $('.contract_add input.CustomerName').val($(this).attr("text"));
    $('.contract_add td.addInsertId').attr("value",$(this).attr("id"));
    
});
//用户名字
$('.contract_add input[name="CustomerName"]').keyup(function () {
	/*alert(5)*/
    var model = $(this).val();
   /* alert(model)*/
    $('.contract_add select[name="CustomerName"]').find('option').each(function () {
        if($(this).val().indexOf(model)!=-1){
            $(this).show();
        } else{
            $(this).hide();
        }
    });

    $('.contract_add select[name="CustomerName"]').show();

});
$(document).on("click", '.contract_add select[name="CustomerName"] option', function () {
    $('.contract_add select[name="CustomerName"]').hide();
    $('.contract_add input[name="CustomerName"]').val($('.contract_add select[name="CustomerName"]').val());
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

//--------------------------------------------拜访记录弹出-----------------------------------------------
$('.VisitRecord-show').click(function () {
    var ID=$(this).parent().parent().find("td.contract-edit").attr('value');
    $(".visitRecord .MachineDetailsID").text(ID);
 /*   alert( $(".visitRecord .MachineDetailsID").text())*/
   /* alert(ID)*/
    $.ajax({
        type: 'get',
        url: 'GetVisitPlan',
        data: {
        	ID: ID
        },
        dataType: 'json',
        success: function (data) {
        	console.log(data)
           $('.visitRecord .visitRecord_basic tbody').html('');
            for (var i = 1; i < data.length; i++) {
                var tr = '<tr>' +
                    '<td style="display: none" class="visit_id">' + data[i].ID + '</td>' +
                    '<td  class="visit_name">' + data[i].VisitName + '</td>' +
                    '<td  class="visit_time">' + data[i].VisitTime + '</td>' +
                    '<td  class="visit_engineer">' + data[i].Engineer + '</td>' +
                   /* '<td><input value="' + data[i].Number + '" type="text" style="width: 45px;" disabled="disabled"></td>' +*/
                    '<td  class="visit_details">' + data[i].Details + '</td>' +
                   /* '<td>' + data[i].ExceptDate + '</td>' +
                    '<td>' + data[i].DeliveryNumber + '</td>' +
                    '<td>' + data[i].Status + '</td>' +*/
                    '</tr>';
                $('.visitRecord .visitRecord_basic tbody').append(tr);
            }
            $('.cover-color').show();
            $('.visitRecord').show();
        },
        error: function () {
             $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
           
        }
    });

});

$(".add_visit").click(function(){
	
	var str = '<tr>'+
			  '<td style="display: none" class="visit_id">-1</td>' +
			  '<td class="visit_name"><input type="text" class="VisitName_add" placeholder="第X次拜访" style="border:none;text-align: center;font-size:16px;"></td>'+
			  '<td  class="visit_time"><input type="date" class="VisitTime_add"  style="border:none;text-align: center;font-size:16px"></td>'+
			  '<td  class="visit_engineer"><input type="text" class="Engineer_add"  style="border:none;text-align: center;font-size:16px"></td>'+
			  '<td  class="visit_details"><input type="text" class="Details_add"  style="border:none;text-align: center;font-size:16px"></td>'+
			  '</tr>';
/*	alert($('.visitRecord .visitRecord_basic tbody tr').length)*/
	if($('.visitRecord .visitRecord_basic tbody tr').length == 0){
		$('.visitRecord .visitRecord_basic tbody').append(str);
	}else{
		$('.visitRecord .visitRecord_basic tbody tr').last().after(str);
	}
	
	
	
})
$('.visitRecord_close').click(function () {
    $('.cover-color').hide();
    $('.visitRecord').hide();
});

$('#visit_cancel').click(function () {
    $('.cover-color').hide();
    $('.visitRecord').hide();
});
//添加的提交
//修改中提交
$("#visit_submit").click(function(){
	var MachineDetailsID=$(".visitRecord .MachineDetailsID").text();
	
	
	//5个数组形式
	var IDArr = [];
	var VisitName = [];
	var VisitTime = [];
	var Engineer = [];
	var Details = [];
	/*alert($(".visitRecord tbody tr").length )*/
	//长度大于1说明提交有内容
	if($(".visitRecord tbody tr").length > 0){
		for(var i = 0 ; i < $(".visitRecord tbody tr").length ; i++){
			var temp_id = $(".visitRecord tbody tr").eq(i).find(".visit_id").text();
			//等于-1说明是新加的记录，需要找到input的内容
			if(temp_id == '-1'){
				$(".visitRecord tbody tr").eq(i).find(".visit_name").text($(".visitRecord tbody tr").eq(i).find(".visit_name .VisitName_add").val())
				/*var temp_val3 = $(".visitRecord tbody tr").eq(i).find(".visit_name").text();
				alert(temp_val3)*/
				$(".visitRecord tbody tr").eq(i).find(".visit_time").text($(".visitRecord tbody tr").eq(i).find(".visit_time .VisitTime_add").val());
				/*var temp_val4 = $(".visitRecord tbody tr").eq(i).find(".visit_time").text();
				alert(temp_val4)*/
				$(".visitRecord tbody tr").eq(i).find(".visit_engineer").text($(".visitRecord tbody tr").eq(i).find(".visit_engineer .Engineer_add").val());
				/*var temp_val5 = $(".visitRecord tbody tr").eq(i).find(".visit_engineer").text();
				alert(temp_val5)*/
				$(".visitRecord tbody tr").eq(i).find(".visit_details").text($(".visitRecord tbody tr").eq(i).find(".visit_details .Details_add").val());
			/*	var temp_val6 = $(".visitRecord tbody tr").eq(i).find(".visit_details").text();
				alert(temp_val6)*/
			};
			IDArr.push($(".visitRecord tbody tr").eq(i).find(".visit_id").text());
			VisitName.push($(".visitRecord tbody tr").eq(i).find(".visit_name").text());
			VisitTime.push($(".visitRecord tbody tr").eq(i).find(".visit_time").text());
			Engineer.push($(".visitRecord tbody tr").eq(i).find(".visit_engineer").text());
			Details.push($(".visitRecord tbody tr").eq(i).find(".visit_details").text());
		} 
	}else{
		//没有内容的提交，传值都是空，不用做任何处理
		IDArr.push('');
		VisitName.push('');
		VisitTime.push('');
		Engineer.push('');
		Details.push('');
	}
	$.ajax({
		    url: 'VisitPlanAdd',
		    type: 'get',
		    data: {
		    	MachineDetailsID:MachineDetailsID,
		    	ID : IDArr,
		    	VisitName:VisitName,
		    	VisitTime:VisitTime,
		    	Engineer:Engineer,
		    	Details:Details
		    },
			success : function (data) {
				console.log(data == "true")
	        	if(data == "true"){
	        		 $.MsgBox.Alert("提示", "保存成功！"); 
	        	}
				 $('.cover-color').hide();
        		 $('.visitRecord').hide();
	        },
	        error : function () {
	        	 $.MsgBox.Alert("提示", "发送失败！"); 
	        }
		});
})
//--------------------------------------根据ABC等级拜访客户---------------------------------------------------
$(function(){
    // 添加机台
    $(document).on("keyup",".contract_add .CustomerUnit",function(){
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
                    var str='';
                    if(newData.length>1){
                        for(var i = 1;i<newData.length;i++){
                            str+='<option value="'+newData[i].CustomerName+'" text="'+newData[i].Contact+'" id="'+newData[i].ID+'">'+newData[i].CustomerName+'&nbsp;:&nbsp;'+newData[i].Contact+'</option>';
                        }
                    }
                    $(".contract_add select[name='CustomerUnit']").empty().append(str);
                    $('.contract_add select[name="CustomerUnit"]').show();
                },
                error : function () {
                    $.MsgBox_Unload.Alert("提示", "服务器繁忙，客户单位数据获取有误！");
                }
            });
        }
    });

    // 修改机台
    $(document).on("keyup",".contract_update .CustomerUnit",function(){
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
                    var str='';
                    if(newData.length>1){
                        for(var i = 1;i<newData.length;i++){
                            str+='<option value="'+newData[i].CustomerName+'" text="'+newData[i].Contact+'" id="'+newData[i].ID+'">'+newData[i].CustomerName+'&nbsp;:&nbsp;'+newData[i].Contact+'</option>';
                        }
                    }
                    $(".contract_update select[name='CustomerUnit']").empty().append(str);
                    $('.contract_update select[name="CustomerUnit"]').show();
                },
                error : function () {
                    $.MsgBox_Unload.Alert("提示", "服务器繁忙，客户单位数据获取有误！");
                }
            });
        }
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
	for(var  i= 1 ;i<$("#table1 tr").length;i++){

	 var tr=$("#table1 tr").eq(i);
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
})



