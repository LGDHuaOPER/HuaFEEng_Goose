/**
 * Created by eoulu on 2017/3/29.
 */

function pageStyle(){
		if($("#allPage").html()==1){
			$('#fistPage').attr('disabled', 'true');
			$('#lastPage').attr('disabled', 'true');
			$('#upPage').attr('disabled', 'true');
			$('#nextPage').attr('disabled', 'true');
			$("#fistPage,#lastPage,#upPage,#nextPage").removeClass("bToggle");
		}else if($('#currentPage').html() == 1){
			$('#fistPage').attr('disabled', 'true');
			$('#fistPage').removeClass('bToggle');
			$('#upPage').attr('disabled', 'true');
			$('#upPage').removeClass('bToggle');
		}else if($('#allPage').html() == $('#currentPage').html()){
			$('#lastPage').attr('disabled', 'true');
			$('#lastPage').removeClass('bToggle');
			$('#nextPage').attr('disabled', 'true');
			$('#nextPage').removeClass('bToggle');
		}
	}

function pageResponse(){
	var pageToDocH = $(".wrap-table").offset().top;
	var currentH = $("#table1").height();
	$(".wrap-table").css("height",currentH+"px");
}

$('.cover-color').height($(document).height()-80);

// ----------------------------------------搜索框----------------------------------------------
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
});

// --------------------------------------------添加合同信息-----------------------------------------------
function AddContract() {
	$('.contract_add input[type="text"]').val('');
	$('.contract_add input[type="date"]').val('');
	$('.contract_add input[type="search]"').val('');
	$('.contract_add textarea').val('');
	$('.contract_add select').each(function(){
	$(this).find('option:checked').prop("selected",false);
	$(this).find('option').filter(function(){return $(this).text()=="";}).prop("selected",true);
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

$(document).on("click", ".contract_add select[name='whether_to_pay'] option", function () {
    $('.contract_add select[name="whether_to_pay"]').hide();
    $('.contract_add .search').val($('.contract_add select[name="whether_to_pay"]').val());
});


$('.contract_add .pay_search').mousedown(function () {
    $('.contract_add select[name="payment_terms"]').show();

});
$('.contract_add .pay_search').change(function () {
    $('.contract_add select[name="payment_terms"]').hide();

});

$(document).on("click", ".contract_add select[name='payment_terms'] option", function () {
    $('.contract_add select[name="payment_terms"]').hide();
    $('.contract_add .pay_search').val($('.contract_add select[name="payment_terms"] option:selected').text());
});

// 添加合同提交
$('#add_submit').click(function () {
    var id=$('.contract_add input[name="order_id"]').val();
    var customer=$('.contract_add input[name="customer"]').val();
    var area=$('.contract_add select[name="area"]').val();
    var contract_no=$('.contract_add input[name="contract_no"]').val();
    var contract_title=$('.contract_add input[name="contract_title"]').val();
    var sales_representative=$('.contract_add select[name="sales_representative"]').val();
    var contract_category=$('.contract_add select[name="contract_category"]').val();
    var contact=$('.contract_add input[name="contact"]').val();
    var contact_info=$('.contract_add input[name="contact_info"]').val();
    var status=$('.contract_add select[name="status"]').val();
    var cargo_period=$('.contract_add input[name="cargo_period"]').val();
    var expected_delivery_period=$('.contract_add input[name="expected_delivery_period"]').val();
    var actual_delivery=$('.contract_add input[name="actual_delivery"]').val();
    var installed_time=$('.contract_add input[name="installed_time"]').val();
    var installed_site=$('.contract_add input[name="installed_site"]').val();
    var date_of_sign=$('.contract_add input[name="date_of_sign"]').val();
    var expected_receipt_date=$('.contract_add input[name="expected_receipt_date"]').val();
    var remarks=$('.contract_add textarea[name="remarks"]').val();
    var usd_quotes=$('.contract_add input[name="usd_quotes"]').val();
    var rmb_quotes=$('.contract_add input[name="rmb_quotes"]').val();
    var payment_terms=$('.contract_add .pay_search').val();
    var whether_to_pay=$('.contract_add .search').val();
    var whether_to_invoice=$('.contract_add select[name="whether_to_invoice"]').val();
    var duty_free=$('.contract_add select[name="duty_free"]').val();
    var whether_to_pay_remarks=$('.contract_add textarea[name="whether_to_pay_remarks"]').val();
    var whether_to_invoice_remarks=$('.contract_add textarea[name="whether_to_invoice_remarks"]').val();
    var duty_free_remarks=$('.contract_add textarea[name="duty_free_remarks"]').val();
    var receipt_date1=$('.contract_add input[name="receipt_date1"]').val();
    var receipt_amount1=$('.contract_add input[name="receipt_amount1"]').val();
    var receipt_date2=$('.contract_add input[name="receipt_date2"]').val();
    var receipt_amount2=$('.contract_add input[name="receipt_amount2"]').val();
    var receipt_date3=$('.contract_add input[name="receipt_date3"]').val();
    var receipt_amount3=$('.contract_add input[name="receipt_amount3"]').val();
    var billing_date=$('.contract_add input[name="billing_date"]').val();

    $.ajax({
        type : 'get',
        url : 'OrderAdd',
        data : {
        	// id:id,
            customer : customer,
            area : area,
            contract_no : contract_no,
            contract_title : contract_title,
            sales_representative : sales_representative,
            contact : contact,
            contact_info : contact_info,
            contract_category : contract_category,
            status : status,
            cargo_period : cargo_period,
            actual_delivery : actual_delivery,
            expected_delivery_period : expected_delivery_period,
            installed_time : installed_time,
            installed_site : installed_site,
            date_of_sign : date_of_sign,
            expected_receipt_date:expected_receipt_date,
            remarks : remarks,
            usd_quotes : usd_quotes,
            rmb_quotes : rmb_quotes,
            payment_terms : payment_terms,
            whether_to_pay : whether_to_pay,
            whether_to_invoice : whether_to_invoice,
            duty_free : duty_free,
            whether_to_pay_remarks : whether_to_pay_remarks,
            whether_to_invoice_remarks : whether_to_invoice_remarks,
            duty_free_remarks : duty_free_remarks,
            receipt_date1 : receipt_date1,
            receipt_amount1 : receipt_amount1,
            receipt_date2 : receipt_date2,
            receipt_amount2 : receipt_amount2,
            receipt_date3 : receipt_date3,
            receipt_amount3 : receipt_amount3,
            billing_date : billing_date,
            PageType :1
        },
        dataType : 'json',
        success : function (data) {   
//        	 alert(222);
             data = eval("(" + data + ")");
             $.MsgBox.Alert('提示', data.message);
             $('.cover-color').hide();
             $('.contract_add').hide();
           
        },
        error : function () {
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});
$('#add_cancel').click(function () {
    $('.cover-color').hide();
    $('.contract_add').hide();
});

//点击关闭
$('#send_cancel').click(function () {
    $('.cover-color').hide();
    $('.contract_send').hide();
});
$('.contractSend_close').click(function () {
    $('.cover-color').hide();
    $('.contract_send').hide();
});
    
//-------------------------------------------修改合同-----------------------------------------------
$(document).on("click",'.contract-edit',function () {
    var tr=$(this).parent();
    var contract_update=$('.contract_update');
	    // contract_update.find('input[name="order_id"]').val($(this).attr('value'));
	    // contract_update.find('input[name="order_id"]').val(tr.find('td').eq(0).html());
	    contract_update.find('input[name="order_id"]').val($(this).attr("value"));
	    contract_update.find('input[name="customer"]').val(tr.find('td').eq(1).html());
	    contract_update.find('input[name="contact"]').val(tr.find('td').eq(2).html());
	    contract_update.find('input[name="contact_info"]').val(tr.find('td').eq(3).html());
        var oldArea = tr.find("td.area_td").text();
        oldArea = globalDataHandle(oldArea,"0");
        if(oldArea=="西南区"){
            oldArea =1;
        }else if(oldArea=="南方"){
            oldArea =2;
        }else if(oldArea=="北方"){
            oldArea =3;
        }
        contract_update.find('select[name="area"]').val(oldArea);
        // 合同类型
        var oldcontractcategory = tr.find("td.contractcategory_td").text();
        oldcontractcategory = globalDataHandle(oldcontractcategory,"0");
        if(oldcontractcategory=="机台"){
            oldcontractcategory =1;
        }else if(oldcontractcategory=="配件"){
            oldcontractcategory =2;
        }else if(oldcontractcategory=="系统"){
            oldcontractcategory =3;
        }else if(oldcontractcategory=="仪表"){
            oldcontractcategory =4;
        }else if(oldcontractcategory=="软件"){
            oldcontractcategory =5;
        }else if(oldcontractcategory=="定制"){
            oldcontractcategory =6;
        }else{
            oldcontractcategory =0;
        }
        contract_update.find('select[name="contract_category"]').val(oldcontractcategory);

        // 装机时间
        var oldInstalledTime = tr.find("td.installedtime_td").text();
        oldInstalledTime = globalDateDataHandle(oldInstalledTime,"");
        contract_update.find('input[name="installed_time"]').val(oldInstalledTime);
        // 装机地点
        var oldInstalledSite = tr.find("td.installedsite_td").text();
        oldInstalledSite = globalDateDataHandle(oldInstalledSite,"");
        contract_update.find('input[name="installed_site"]').val(oldInstalledSite);
        // 备注
        var oldRemarks = tr.find("td.remarks_td").text();
        oldRemarks = globalDataHandle(oldRemarks,"");
        contract_update.find('textarea[name="remarks"]').val(oldRemarks);

	    contract_update.find('input[name="contract_title"]').val(tr.find('td').eq(4).html());
	    contract_update.find('input[name="contract_no"]').val(tr.find('td').eq(5).html());
	    
	    contract_update.find('select[name="sales_representative"]').find('option[text="' + tr.find('td').eq(6).html() + '"]').prop("selected", true);
	    contract_update.find('input[name="date_of_sign"]').val(tr.find('td').eq(7).html());
	    contract_update.find('input[name="cargo_period"]').val(tr.find('td').eq(8).html());
	    // contract_update.find('input[name="actual_delivery"]').val(tr.find('td').eq(12).html());
	    // contract_update.find('input[name="expected_delivery_period"]').val(tr.find('td').eq(13).html());
	    contract_update.find('input[name="expected_receipt_date"]').val(tr.find('td').eq(9).html());
	    // contract_update.find('select[name="status"]').find('option[text="' + tr.find('td').eq(15).html() + '"]').prop("selected", true);
        var actualPayDate = globalDateDataHandle(tr.find('td.actual_pay_time').text(),"");
        contract_update.find('input[name="actual_pay_date"]').val(actualPayDate);
	    var order_id = contract_update.find('input[name="order_id"]').val();
	    $('.cover-color').show();
	    contract_update.show();
    $.ajax({
        type : 'get',
        url : 'GetQuotes',
        data : {
            order_id:order_id
        },
        dataType : 'json',
        success : function (data) {
            // alert(111);
            if(data.length>1){
                contract_update.find('input[name="usd_quotes"]').val(data[1].USDQuotes);
                contract_update.find('input[name="rmb_quotes"]').val(data[1].RMBQuotes);
                contract_update.find('.pay_search').val(contract_update.find('select[name="payment_terms"]').val(data[1].PaymentTermsID));
                contract_update.find('select[name="payment_terms"] option:checked').prop("selected",false);
                if(data[1].PaymentTermsID=='--'){
                    contract_update.find('select[name="payment_terms"]').find('option').filter(function(){return $(this).text()=="";}).prop("selected",true);
                }else{
                    contract_update.find('select[name="payment_terms"]').find('option[value="'+data[1].PaymentTermsID+'"]').prop("selected",true);
                }
                contract_update.find('input[name="receipt_date1"]').val(data[1].ReceiptDate1);
                contract_update.find('input[name="receipt_amount1"]').val(data[1].ReceiptAmount1);
                contract_update.find('input[name="receipt_date2"]').val(data[1].ReceiptDate2);
                contract_update.find('input[name="receipt_amount2"]').val(data[1].ReceiptAmount2);
                contract_update.find('input[name="receipt_date3"]').val(data[1].ReceiptDate3);
                contract_update.find('input[name="receipt_amount3"]').val(data[1].ReceiptAmount3);
                contract_update.find('input[name="billing_date"]').val(data[1].BillingDate);
                contract_update.find('input[name="pay_search"]').val($('.contract_update select[name="payment_terms"] option:selected').text());
                contract_update.find('input[name="search"]').val(data[1].WhetherToPay);
                contract_update.find('select[name="whether_to_pay"] option:checked').prop("selected",false);
                contract_update.find('select[name="whether_to_pay"]').find('option[value="'+data[1].WhetherToPay+'"]').prop("selected",true);
                contract_update.find('select[name="duty_free"]').find('option[text="'+data[1].DutyFree+'"]').prop("selected",true);
                contract_update.find('select[name="whether_to_invoice"]').find('option[value="'+data[1].WhetherToInvoice+'"]').prop("selected",true);
                contract_update.find('textarea[name="whether_to_pay_remarks"]').val(data[1].WhetherToPayRemarks);
                contract_update.find('textarea[name="whether_to_invoice_remarks"]').val(data[1].WhetherToInvoiceRemarks);
                contract_update.find('textarea[name="duty_free_remarks"]').val(data[1].DutyFreeRemarks);
            }else if(data.length==1){
                contract_update.find('input[name="usd_quotes"]').val("");
                contract_update.find('input[name="rmb_quotes"]').val("");
                contract_update.find('.pay_search').val("");
                contract_update.find('select[name="payment_terms"] option:checked').prop("selected",false);
                contract_update.find('input[name="receipt_date1"]').val("");
                contract_update.find('input[name="receipt_amount1"]').val("");
                contract_update.find('input[name="receipt_date2"]').val("");
                contract_update.find('input[name="receipt_amount2"]').val("");
                contract_update.find('input[name="receipt_date3"]').val("");
                contract_update.find('input[name="receipt_amount3"]').val("");
                contract_update.find('input[name="billing_date"]').val("");
                contract_update.find('input[name="pay_search"]').val("");
                contract_update.find('input[name="search"]').val("");
                contract_update.find('select[name="whether_to_pay"] option:checked').prop("selected",false);
                contract_update.find('select[name="duty_free"] option:checked').prop("selected",false);
                contract_update.find('select[name="whether_to_invoice"] option:checked').prop("selected",false);
                contract_update.find('textarea[name="whether_to_pay_remarks"]').val("");
                contract_update.find('textarea[name="whether_to_invoice_remarks"]').val("");
                contract_update.find('textarea[name="duty_free_remarks"]').val("");
            }
        },
        error : function () {
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
}) ;
$('.contractUpdate_close').click(function () {
    $('.cover-color').hide();
    $('.contract_update').hide();
});

$('.contract_update .search').mousedown(function () {
    $('.contract_update select[name="whether_to_pay"]').show();

});
$('.contract_update .search').change(function () {
    $('.contract_update select[name="whether_to_pay"]').hide();

});


$(document).on("click", ".contract_update select[name='whether_to_pay'] option", function () {
    $('.contract_update select[name="whether_to_pay"]').hide();
    $('.contract_update .search').val($('.contract_update select[name="whether_to_pay"]').val());
});

$('.contract_update .pay_search').mousedown(function () {
    $('.contract_update select[name="payment_terms"]').show();

});
$('.contract_update .pay_search').change(function () {
    $('.contract_update select[name="payment_terms"]').hide();

});

$(document).on("click", ".contract_update select[name='payment_terms'] option", function () {
    $('.contract_update select[name="payment_terms"]').hide();
    $('.contract_update .pay_search').val($('.contract_update select[name="payment_terms"] option:selected').text());
});

$('#update_submit').click(function () {
    var contract_update=$('.contract_update');
	var order_id=contract_update.find('input[name="order_id"]').val();
    var customer=$('.contract_update input[name="customer"]').val();
    var area=$('.contract_update select[name="area"]').val();
    var contract_no=$('.contract_update input[name="contract_no"]').val();
    var contract_title=$('.contract_update input[name="contract_title"]').val();
    var contract_category=$('.contract_update select[name="contract_category"]').val();
    var sales_representative=$('.contract_update select[name="sales_representative"]').val();
    var contact=$('.contract_update input[name="contact"]').val();
    var contact_info=$('.contract_update input[name="contact_info"]').val();
    var status=$('.contract_update select[name="status"]').val();
    var cargo_period=$('.contract_update input[name="cargo_period"]').val();
    var actual_delivery=$('.contract_update input[name="actual_delivery"]').val();
    var expected_delivery_period=$('.contract_update input[name="expected_delivery_period"]').val();
    var installed_time=$('.contract_update input[name="installed_time"]').val();
    var installed_site=$('.contract_update input[name="installed_site"]').val();
    var date_of_sign=$('.contract_update input[name="date_of_sign"]').val();
    var expected_receipt_date=$('.contract_update input[name="expected_receipt_date"]').val();
    var remarks=$('.contract_update textarea[name="remarks"]').val();
    var usd_quotes=$('.contract_update input[name="usd_quotes"]').val();
    var rmb_quotes=$('.contract_update input[name="rmb_quotes"]').val();
    var payment_terms=$('.contract_update .pay_search').val();
    var whether_to_pay=$('.contract_update .search').val();
    var whether_to_invoice=$('.contract_update select[name="whether_to_invoice"]').val();
    var duty_free=$('.contract_update select[name="duty_free"]').val();
    var whether_to_pay_remarks=$('.contract_update textarea[name="whether_to_pay_remarks"]').val();
    var whether_to_invoice_remarks=$('.contract_update textarea[name="whether_to_invoice_remarks"]').val();
    var duty_free_remarks=$('.contract_update textarea[name="duty_free_remarks"]').val();
    var receipt_date1=$('.contract_update input[name="receipt_date1"]').val();
    var receipt_amount1=$('.contract_update input[name="receipt_amount1"]').val();
    var receipt_date2=$('.contract_update input[name="receipt_date2"]').val();
    var receipt_amount2=$('.contract_update input[name="receipt_amount2"]').val();
    var receipt_date3=$('.contract_update input[name="receipt_date3"]').val();
    var receipt_amount3=$('.contract_update input[name="receipt_amount3"]').val();
    var billing_date=$('.contract_update input[name="billing_date"]').val();
    var ActualPaymentTime = $('.contract_update input[name="actual_pay_date"]').val();

    $.ajax({
        type : 'post',
        url : 'ModifyOrder',
        data : {
            id:order_id,
            customer : customer,
            area : area,
            contract_no : contract_no,
            contract_title : contract_title,
            sales_representative : sales_representative,
            contact : contact,
            contact_info : contact_info,
            contract_category : contract_category,
            status : status,
            cargo_period : cargo_period,
            actual_delivery : actual_delivery,
            expected_delivery_period : expected_delivery_period,
            installed_time : installed_time,
            installed_site : installed_site,
            date_of_sign : date_of_sign,
            expected_receipt_date:expected_receipt_date,
            remarks : remarks,
            usd_quotes : usd_quotes,
            rmb_quotes : rmb_quotes,
            payment_terms : payment_terms,
            whether_to_pay : whether_to_pay,
            whether_to_invoice : whether_to_invoice,
            duty_free : duty_free,
            whether_to_pay_remarks : whether_to_pay_remarks,
            whether_to_invoice_remarks : whether_to_invoice_remarks,
            duty_free_remarks : duty_free_remarks,
            receipt_date1 : receipt_date1,
            receipt_amount1 : receipt_amount1,
            receipt_date2 : receipt_date2,
            receipt_amount2 : receipt_amount2,
            receipt_date3 : receipt_date3,
            receipt_amount3 : receipt_amount3,
            billing_date : billing_date,
            PageType: 1,
            ActualPaymentTime: ActualPaymentTime
        },
        dataType : 'json',
        success : function (data) {
        	console.log(typeof data);
            if(data.indexOf("修改失败")>-1){
                $.MsgBox.Alert('提示','修改失败');
            }else{
                $.MsgBox.Alert('提示','修改成功');
            }
            $('.cover-color').hide();
            $('.contract_update').hide();
        },
        error : function () {
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
    var order_id = $(this).attr("value");
    $('#order_id').val(order_id);
    var line=$(this).parent().parent();
    $.ajax({
        type: 'get',
        url: 'GetOrderInfo',
        data: {
            order_id: order_id
        },
        dataType: 'json',
        success: function (data) {
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
                    '<td> <input name="contract-update" value="修改" class="bToggle" type="button"  ><input name="update-save" value="保存" class="bToggle" type="button" style="display:none" ></td>' +
                    '<td> <input name="contract-delete" value="删除" class="bToggle" type="button"  ></td>' +
                    '</tr>';
                $('.contract .contractPage tbody').append(tr);
            }
            if(line.find('td').eq(1).html()=='该订单已完结'){
            	$('.contract .contractPage thead tr').find('th').eq(8).hide();
            	$('.contract .contractPage thead tr').find('th').eq(9).hide();
            	$('.contract .contractPage tbody tr').find('td').eq(9).hide();
            	$('.contract .contractPage tbody tr').find('td').eq(10).hide();
            	$('.contract .edit_btn').hide();
            }else{
            	$('.contract .contractPage thead tr').find('th').eq(8).show();
            	$('.contract .contractPage thead tr').find('th').eq(9).show();
            	$('.contract .contractPage tbody tr').find('td').eq(9).show();
            	$('.contract .contractPage tbody tr').find('td').eq(10).show();
            	$('.contract .edit_btn').show();
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
    $('input[name="search"]').attr("placeholder","输入型号按回车搜索");
    $('input[name="search"]').val("");
    $('input[name="equipment_counts"]').val("");
    $("#select").empty();
    $('.cover-color').hide();
    $('.contract').hide();
});

// --------------------------------------------修改合同配置信息页面-----------------------------------------------
$(document).on("click", "input[name='contract-update']", function () {
	$(this).parent().find('input[name="update-save"]').show()
	$(this).parent().parent().find('input[type="text"]').attr("disabled",false);
    $(this).hide();
});

//--------------------------------------------保存修改合同配置信息页面-----------------------------------------------
$(document).on("click", "input[name='update-save']", function () {
    var tr = $(this).parent().parent();
    var id = tr.find('td').eq(0).html();
    var equipment_counts = tr.find('input[type="text"]').val();
	var order_id = $('#order_id').val();
    $.ajax({
        type: 'get',
        url: 'OrderInfoModify',
        data: {
            id: id,
            equipment_counts: equipment_counts
        },
        dataType: 'json',
        success: function (data) {
            $.ajax({
                type: 'get',
                url: 'GetOrderInfo',
                data: {
                    order_id: order_id
                },
                dataType: 'json',
                success: function (data) {
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
                            '<td> <input name="contract-update" value="修改" class="bToggle" type="button"  ><input name="update-save" value="保存" class="bToggle" type="button" style="display:none" ></td>' +
                            '<td> <input name="contract-delete" value="删除" class="bToggle" type="button" ></td>' +
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
        },
        error: function () {
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});
// --------------------------------------------删除合同配置信息页面-----------------------------------------------
$(document).on("click", "input[name='contract-delete']", function () {
    var tr = $(this).parent().parent();
    var id = tr.find('td').eq(0).html();
	var order_id = $('#order_id').val();
    $.ajax({
        type: 'get',
        url: 'OrderInfoRemove',
        data: {
            order_info_id: id
        },
        dataType: 'json',
        success: function (data) {
            $.ajax({
                type: 'get',
                url: 'GetOrderInfo',
                data: {
                    order_id: order_id
                },
                dataType: 'json',
                success: function (data) {
                    $('.contract .contractPage tbody').html('');
                    for (var i = 1; i < data.length; i++) {
                        var tr = '<tr>' +
                            '<td style="display: none">' + data[i].ID + '</td>' +
                            '<td>' + data[i].OrderID + '</td>' +
                            '<td>' + data[i].EquipmentModel + '</td>' +
                            '<td>' + data[i].Remarks + '</td>' +
                            '<td><input value="' + data[i].Number + '" type="text" style="width: 45px;"></td>' +
                            '<td>' + data[i].Date + '</td>' +
                            '<td>' + data[i].ExceptDate + '</td>' +
                            '<td>' + data[i].DeliveryNumber + '</td>' +
                            '<td>' + data[i].Status + '</td>' +
                            '<td> <input name="contract-update" value="修改" class="bToggle" type="button"  ><input name="update-save" value="保存" class="bToggle" type="button" style="display:none" ></td>' +
                            '<td> <input name="contract-delete" value="删除" class="bToggle" type="button" ></td>' +
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
        },
        error: function () {
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});


//--------------------------------------------添加合同配置信息页面-----------------------------------------------
$('#contract_apply_add').click(function () {
//    var model = $(this).attr('text');
//    $.ajax({
//        type: 'get',
//        url: 'GetEquipment',
//        data: {
//            model: model
//        },
//        dataType: 'json',
//        success: function (data) {
//            $('#select').html('');
//            for (var i = 1; i < data.length; i++) {
//                var option = '<option text='+data[i].ID+' value='+data[i].Model+'>' +data[i].Model+ '</option>';
//                $('#select').append(option);
//            }
            $('.apply_add_info').show();
//
//        },
//        error: function () {
//            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
//        }
//    });
});


// --------------------------------------------提交添加合同配置信息-----------------------------------------------
$(document).on("click", "input[name='add_submit']", function () {
	var order_id = $('#order_id').val();
	 var model=$('#select').children('option:selected').attr('text');
	 var equipment_counts=$('input[name=equipment_counts]').val();
    $.ajax({
        type: 'get',
        url: 'OrderInfoAndLogisticsAdd',
        data: {
            order_id: order_id,
            model: model,
            equipment_counts: equipment_counts
        },
        dataType: 'json',
        success: function (data) {
            $.ajax({
                type: 'get',
                url: 'GetOrderInfo',
                data: {
                    order_id: order_id
                },
                dataType: 'json',
                success: function (data) {
                    $('.contract .contractPage tbody').html('');
                    for (var i = 1; i < data.length; i++) {
                        var tr = '<tr>' +
                            '<td style="display: none">' + data[i].ID + '</td>' +
                            '<td>' + data[i].OrderID + '</td>' +
                            '<td>' + data[i].EquipmentModel + '</td>' +
                            '<td>' + data[i].Remarks + '</td>' +
                            '<td><input value="' + data[i].Number + '" type="text" style="width: 45px;"></td>' +
                            '<td>' + data[i].Date + '</td>' +
                            '<td>' + data[i].ExceptDate + '</td>' +
                            '<td>' + data[i].DeliveryNumber + '</td>' +
                            '<td>' + data[i].Status + '</td>' +
                            '<td> <input name="contract-update" value="修改" class="bToggle" type="button" text=order_id></td>' +
                            '<td> <input name="contract-delete" value="删除" class="bToggle" type="button" text=order_id></td>' +
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
        },
        error: function () {
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});

//--------------------------------------------删除合同信息-----------------------------------------------
$('.delete_but').click(function () {
    var order_id=$(this).attr('value');
    var current=$(this).parent().parent();
    $.ajax({
        type: 'get',
        url: 'OrderDelete',
        data: {
            order_id: order_id
        },
        dataType: 'json',
        success: function (data) {
//            window.location.href='http://www.baidu.com';
        	data = eval("("+data+")");
        	if(data.message=="success")
        	current.remove();
        	else{
        		$.MsgBox.Alert("提示", "删除失败");	
        	}
        	
        },
        error: function () {
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});

//--------------------------------------------合同明细-----------------------------------------------
$('.supply-show').click(function () {
    var order_id = $(this).attr("value");
    $.ajax({
        type: 'get',
        url: 'GetQuotes',
        data: {
            order_id: order_id
        },
        dataType: 'json',
        success: function (data) {
            $('.supply_detail .contractPage tbody').html('');
            for (var i = 1; i < data.length; i++) {
                var tr = '<tr>' +
                    '<td>' + data[i].USDQuotes + '</td>' +
                    '<td>' + data[i].RMBQuotes + '</td>' +
                    '<td>' + data[i].PaymentTerms + '</td>' +
                    '<td>' + data[i].WhetherToPay + '<br>('+data[i].WhetherToPayRemarks+')</td>' +
                    '<td>' + data[i].ReceiptDate1 + '</td>' +
                    '<td>' + data[i].ReceiptAmount1 + '</td>' +
                    '<td>' + data[i].ReceiptDate2 + '</td>' +
                    '<td>' + data[i].ReceiptAmount2 + '</td>' +
                    '<td>' + data[i].ReceiptDate3 + '</td>' +
                    '<td>' + data[i].ReceiptAmount3 + '</td>' +
                    '<td>' + (data[i].WhetherToInvoice=="0"?"否":"是") + '<br>('+data[i].WhetherToInvoiceRemarks+')</td>' +
                    '<td>' + data[i].BillingDate + '</td>' +
                    '<td>' + data[i].DutyFree + '<br>('+data[i].DutyFreeRemarks+')</td>' +
                    '</tr>';
                $('.supply_detail .contractPage tbody').append(tr);
            }
            $('.cover-color').show();
            $('.supply_detail').show();
        },
        error: function () {
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});
$('.supply_detail_close').click(function () {
    $('.cover-color').hide();
    $('.supply_detail').hide();
});

//----------------------------------------上传文件--------------------------------
$('#upload').click(function () {
    $('.cover-color2').show();
    $('.upload').show();
    $('.list').hide();
});

$('.upload_close').click(function () {
    $('.cover-color2').hide();
    $('.upload').hide();
	$('.file_content .error').html('');
	$('#file_name').val('');
	$('#fileField').val('');
	var order_id = $('#order_id').val();
    $.ajax({
        type: 'get',
        url: 'GetOrderInfo',
        data: {
            order_id: order_id
        },
        dataType: 'json',
        success: function (data) {
            $('.contract .contractPage tbody').html('');
            for (var i = 1; i < data.length; i++) {
                var tr = '<tr>' +
                    '<td style="display: none">' + data[i].ID + '</td>' +
                    '<td>' + data[i].OrderID + '</td>' +
                    '<td>' + data[i].EquipmentModel + '</td>' +
                    '<td>' + data[i].Remarks + '</td>' +
                    '<td><input value="' + data[i].Number + '" type="text" style="width: 45px;"></td>' +
                    '<td>' + data[i].Date + '</td>' +
                    '<td>' + data[i].ExceptDate + '</td>' +
                    '<td>' + data[i].DeliveryNumber + '</td>' +
                    '<td>' + data[i].Status + '</td>' +
                    '<td> <input name="contract-update" value="修改" class="bToggle" type="button" text=order_id></td>' +
                    '<td> <input name="contract-delete" value="删除" class="bToggle" type="button" text=order_id></td>' +
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

$('#open').click(function () {
	var order_id = $('#order_id').val();
	if(	$('#fileField').val()==''){
    	$('.file_content .error').html('请选择上传的文件！');
	}else{
		$.ajaxFileUpload({
    		type: 'post',
            url: 'AutoInputOrder', //用于文件上传的服务器端请求地址
            data: {
                id: order_id
            },
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: 'fileField', //文件上传域的ID
            dataType: 'script', //返回值类型 一般设置为json
            success: function (data)  //服务器成功响应处理函数
            {
            	$('.list ul').html('');
                data = eval("(" + data + ")");
                if(data=='success'){
                	$('.file_content .error').html('上传成功！')
                }else {
                	$('.file_content .error').html('部分文件上传失败！');
                	$('.list ul').append('<li>上传失败产品型号</li>');
                	var data=data.split(",");
                	for(i=0;i<data.length;i++){
                    	$('.list ul').append('<li>'+data[i]+'</li>');
                	}
                	$('.list').show();
                }
            },
            error: function (e)//服务器响应失败处理函数
            {
                $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
            }
        })
	}
    
});

function openFile() {
    var fileName = "";
    fileName = $('#fileField').val().split("\\").pop();
    $('#file_name').val(fileName);

}

function openFile1() {
    $('.upFileText .error1').html('');
    var fileName1 = "";
    fileName1 = $('#input_file').val().split("\\").pop();
    $('.input_text').val(fileName1);
    $("input[name='isPdfUpload']").val(0);
}

// 采购合同上传
$('#uploadCon').click(function () {
	var order_id = $('#order_id').val();
	// alert($('#input_file').val());
    var fileName4 = $('#input_file').val().split("\\").pop();
    // alert(fileName4);
    console.log("fileName4:"+fileName4);
	if(	$('#input_file').val()==''){
		$('.upFileText .error1').html('请选择上传的文件！');
	}else{
		$.ajaxFileUpload({
    		type: 'post',
            url: 'OrderUpload', //用于文件上传的服务器端请求地址
            data: {
                classify: 'ContractPath',
                fileName: fileName4
        // id: order_id
            },
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: 'input_file', //文件上传域的ID
            // dataType: 'script', //返回值类型 一般设置为json
            dataType: 'text', //返回值类型 一般设置为json
            success: function (data)  //服务器成功响应处理函数
            {
                console.log(data);
                if(data){
                    $("input[name='isPdfUpload']").val(1);
                    $('.upFileText .error1').html('上传成功！');
                }else{
                    $('.upFileText .error1').html('文件上传失败！');
                    $("input[name='isPdfUpload']").val(0);
                }
                // var str = $(data).find("body").text();//获取返回的字符串
                // var json = $.parseJSON(str);//把字符串转化为json对象
                // console.log(json)
//            	$('.list ul').html('');
//                 console.log(data);
//                 data = JSON.parse(data);
//                 // data = eval("(" + data + ")");
//                 console.log(data);
//                 console.log(typeof data);
//                 console.log(data.message);
//                 console.log(data['message']);
                // data = JSON.parse(data);
//                 if(data['message']){
//                 	$('.upFileText .error1').html('上传成功！');
//                 }else {
//                 	$('.upFileText .error1').html('文件上传失败！');
// //                	$('.list ul').append('<li>上传失败产品型号</li>');
// //                	var data=data.split(",");
// //                	for(i=0;i<data.length;i++){
// //                    	$('.list ul').append('<li>'+data[i]+'</li>');
// //                	}
// //                	$('.list').show();
//                 }
            },
            error: function (e)//服务器响应失败处理函数
            {
                $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
            }
        })
	}
    
});


//--------------------------------------------导出-----------------------------------------------------
$('#export').click(function () {
    var query_type=$('input[name="query_type"]').val();
    var classify1=$('input[name="classify1"]').val();
    var classify2=$('input[name="classify2"]').val();
    var parameter1=$('input[name="parameter1"]').val();
    var parameter2=$('input[name="parameter2"]').val();
    $.ajax({
        type: 'get',
        url: 'ExportOrder',
        data: {
            query_type: query_type,
            classify1: classify1,
            classify2: classify2,
            parameter1: parameter1,
            parameter2: parameter2
        },
       // dataType: 'json',
        success: function (data) {
        	window.location.href=data;
        },
        error: function () {
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});

//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function () {
    window.location.reload();
});


//------------------------------隐藏列-------------------------------------
function abcd(){
	$('#fa-button1').toggleClass('fa-minus-square');
	$('#table1 tr').find('td:eq(2)').toggle();
	$('#table1 tr').find('td:eq(3)').toggle();
}
$('#fa-button1').bind('click',function(){
	pageResponse();
	setTimeout(abcd,50);
 // $('#table1 tr').find('td:eq(5)').toggle();
 // $('#table1 tr').find('td:eq(9)').toggle();
 // $('#table1 tr').find('td:eq(19)').toggle();
});
$('#fa-button2').bind('click',function(){
	pageResponse();
 $('#fa-button2').toggleClass('fa-minus-square');
 $('#table1 tr').find('td:eq(5)').toggle();
});
$('#fa-button3').bind('click',function(){
 $('#fa-button3').toggleClass('fa-minus-square');
 $('#table1 tr').find('td:eq(16)').toggle();
});

/**************新增窗口部分******************************/
$('.number').stop().animate({'top':'65','fontSize':'28px'},400);
$('.number2').stop().animate({'top':'65','fontSize':'28px'},400);

var timer = setTimeout(function(){
    $('.add-div-top').fadeOut();
    $(".prompt-content").slideDown(1000);
    $(".proClick").slideDown(1000);
    $(".proTotal").fadeIn(400);
    $(".prompt-alert").css("visibility","visible");

},3000);
$(".pC-li1").click(function(){
    $(".proTotal").fadeIn(400);
    $(".proSingal").fadeOut(400);
});
$(".pC-li2").click(function(){
    $(".proTotal").fadeOut(400);
    $(".proSingal").fadeIn(400);
    $(".proSingal .sinSouth").fadeIn(400);
    $(".proSingal .sinNorth").fadeOut(400);
    $(".proSingal .sinSouthwest").fadeOut(400);

});
$(".pC-li3").click(function(){
    $(".proTotal").fadeOut(400);
    $(".proSingal").fadeIn(400);
    $(".proSingal .sinSouth").fadeOut(400);
    $(".proSingal .sinNorth").fadeIn(400);
    $(".proSingal .sinSouthwest").fadeOut(400);
});

$(".pC-li4").click(function(){
    $(".proTotal").fadeOut(400);
    $(".proSingal").fadeIn(400);
    $(".proSingal .sinSouth").fadeOut(400);
    $(".proSingal .sinNorth").fadeOut(400);
    $(".proSingal .sinSouthwest").fadeIn(400);
});

$(".pC-li5").click(function(){
    window.open('Statistics');
    /*window.location.href = 'Statistics';*/
});

//-------------------------------------------------判断数据统计区域图片和鼓励字体内容--------------------------------------------------
function showJudge(data){
	var text,npic,spic,wpic;
	var pic=[];
	if(data>1){
		text='棒棒哒！';
		npic='5.gif';
		spic='7.gif';
		wpic='2.gif';

	}else if(data>0.5){
		text='继续努力！';
		npic='13.gif';
		spic='14.gif';
		wpic='15.gif';
	}else{
		text='加油！';
		npic='6.gif';
		spic='11.gif';
		wpic='9.gif';
	}
	pic=[npic,spic,wpic];
	return {text:text,pic:pic};
}

var south=parseFloat($('.proSouth').find('b').eq(1).html())/parseFloat(($('.proSouth').find('b').eq(0).html()));
var north=parseFloat($('.proNorth').find('b').eq(1).html())/parseFloat(($('.proNorth').find('b').eq(0).html()));
var southwest=parseFloat($('.proSouthwest').find('b').eq(1).html())/parseFloat(($('.proSouthwest').find('b').eq(0).html()));
// Picture(south,north,southwest);

// function Picture(south,north,southwest){
// 	$('.prompt-alert').html('<div class="swiper-wrapper">'+
// 			'<div class="swiper-slide">'+
// 			'<span>南方'+showJudge(south).text+'</span>'+
// 			'<img src="image/'+showJudge(south).pic[1]+'"/>'+
// 			'</div>'+
// 			'<div class="swiper-slide">'+
// 			'<span>北方'+showJudge(north).text+'</span>'+
// 			'<img src="image/'+showJudge(north).pic[0]+'"/>'+
// 			'</div>'+
// 			'<div class="swiper-slide">'+
// 			'<span>西南'+showJudge(southwest).text+'</span>'+
// 			'<img src="image/'+showJudge(southwest).pic[2]+'"/>'+
// 			'</div>'+
// 			'</div>');


// var mySwiper = new Swiper ('.prompt-alert', {
// autoplay: 3000,
// loop: true,
// autoplayDisableOnInteraction : false
// });
// }



//--------------------------------------------点击    预计货期   触发事件-----------------------------------------------------

//合同货期
$("#table1 tr").children().eq(8).find('select').on("change",function(){
var ActualDelivery  = window.location.href.split("ActualDelivery=")[1].split("&")[0];
var AllNoSend=$('.AllNoSend').val();
var OtherNoSend=$('.OtherNoSend').val();
var currentVal = $(this).val();
if(currentVal == "AllNoSend"){
	$.ajax({
        type: 'get',
        url: 'Price',
        data: {
        	condition:'AllNoSend',
            column:"CargoPeriod",
            ActualDelivery:"yes"
        },
        success:function (data) {
        	currentHref = window.location.href.split("ActualDelivery=")[0];
        	window.location.href = currentHref+"ActualDelivery=yes&column=CargoPeriod&condition=AllNoSend" ;
        }
    })
}
else if(currentVal == "OtherNoSend"){
		$.ajax({
	        type: 'get',
	        url: 'Price',
	        data: {
	        	condition:'OtherNoSend',
	            column:"CargoPeriod",
	            ActualDelivery:"yes"
	        },
	        success:function (data) {
	        	currentHref = window.location.href.split("ActualDelivery=")[0];
	        	window.location.href = currentHref+"ActualDelivery=yes&column=CargoPeriod&condition=OtherNoSend" ;
	        }
	    })
	}
else if(currentVal == "All"){
	$.ajax({
        type: 'get',
        url: 'Price',
        data: {
        	condition:'All',
            column:"CargoPeriod",
            ActualDelivery:"no"
        },
        success:function (data) {
        	currentHref = window.location.href.split("ActualDelivery=")[0];
        	window.location.href = currentHref+"ActualDelivery=no&column=CargoPeriod&condition=All" ;
        }
    })
}
})
//实际货期
$("#table1 tr").children().eq(12).find('select').on("change",function(){
var ActualDelivery  = window.location.href.split("ActualDelivery=")[1].split("&")[0];
var AllNoSend=$('.AllNoSend').val();
var OtherNoSend=$('.OtherNoSend').val();
var currentVal = $(this).val();
if(currentVal == "AllNoSend"){
	$.ajax({
        type: 'get',
        url: 'Price',
        data: {
        	condition:'AllNoSend',
            column:"DateOfSign",
            ActualDelivery:"yes"
        },
        success:function (data) {
        	currentHref = window.location.href.split("ActualDelivery=")[0];
        	window.location.href = currentHref+"ActualDelivery=yes&column=DateOfSign&condition=AllNoSend" ;
        }
    })
}
else if(currentVal == "OtherNoSend"){
		$.ajax({
	        type: 'get',
	        url: 'Price',
	        data: {
	        	condition:'OtherNoSend',
	            column:"DateOfSign",
	            ActualDelivery:"yes"
	        },
	        success:function (data) {
	        	currentHref = window.location.href.split("ActualDelivery=")[0];
	        	window.location.href = currentHref+"ActualDelivery=yes&column=DateOfSign&condition=OtherNoSend" ;
	        }
	    })
	}
else if(currentVal == "All"){
	$.ajax({
        type: 'get',
        url: 'Price',
        data: {
        	condition:'All',
            column:"DateOfSign",
            ActualDelivery:"no"
        },
        success:function (data) {
        	currentHref = window.location.href.split("ActualDelivery=")[0];
        	window.location.href = currentHref+"ActualDelivery=no&column=DateOfSign&condition=All" ;
        }
    })
}
})
//预计货期    ExpectedDeliveryPeriod
$("#table1 tr").children().eq(13).find('select').on("change",function(){
var ActualDelivery  = window.location.href.split("ActualDelivery=")[1].split("&")[0];
var AllNoSend=$('.AllNoSend').val();
var OtherNoSend=$('.OtherNoSend').val();
var currentVal = $(this).val();
if(currentVal == "AllNoSend"){
	$.ajax({
        type: 'get',
        url: 'Price',
        data: {
        	condition:'AllNoSend',
            column:"ExpectedDeliveryPeriod",
            ActualDelivery:"yes"
        },
        success:function (data) {
        	currentHref = window.location.href.split("ActualDelivery=")[0];
        	window.location.href = currentHref+"ActualDelivery=yes&column=ExpectedDeliveryPeriod&condition=AllNoSend" ;
        	
        }
    })
}
else if(currentVal == "OtherNoSend"){
		$.ajax({
	        type: 'get',
	        url: 'Price',
	        data: {
	        	condition:'OtherNoSend',
	            column:"ExpectedDeliveryPeriod",
	            ActualDelivery:"yes"
	        },
	        success:function (data) {
	        	currentHref = window.location.href.split("ActualDelivery=")[0];
	        	window.location.href = currentHref+"ActualDelivery=yes&column=ExpectedDeliveryPeriod&condition=OtherNoSend" ;
	        }
	    })
	}
else if(currentVal == "All"){
	$.ajax({
        type: 'get',
        url: 'Price',
        data: {
        	condition:'All',
            column:"ExpectedDeliveryPeriod",
            ActualDelivery:"no"
        },
        success:function (data) {
        	currentHref = window.location.href.split("ActualDelivery=")[0];
        	window.location.href = currentHref+"ActualDelivery=no&column=ExpectedDeliveryPeriod&condition=All" ;
        	
        }
    })
}

})


	function isSendChange(text1,value){
	var PriceId = text1.childNodes[1].getAttribute("value") ;
	var ConsigneeContacts = text1.getElementsByClassName("ContactTD")[0].innerText;
	
	var ConsigneeTel = text1.getElementsByClassName("ContactInfoTD")[0].innerText;
	
	var ContractNo = text1.getElementsByClassName("ContractNoTD")[0].innerText;
	var ContractTitle = text1.getElementsByClassName("ContractTitleTD")[0].innerText;
		if(value == "是"){
			console.log("发送")
			$('.cover-color').show();
		    $('.contract_send').show();
		    $('.contract_send').find('input[name="ConsigneeContacts"]').val(ConsigneeContacts);
		    $('.contract_send').find('input[name="ConsigneeTel"]').val(ConsigneeTel);
		    
		    
		  //点击确认按钮
		    $(document).on("click","#send_submit",function(){
		    	console.log("发送1")
		    	/*var PriceId=$('.contract_send input[name="PriceId"]').val();
		        var ContractNo=$('.contract_send input[name="ContractNo"]').val();
		        var ContractTitle=$('.contract_send input[name="ContractTitle"]').val();*/
		    	var ConsigneeCompany=$('.contract_send input[name="ConsigneeCompany"]').val();
		        var ConsigneeAddress=$('.contract_send input[name="ConsigneeAddress"]').val();
		        var ConsigneeContacts=$('.contract_send input[name="ConsigneeContacts"]').val();
		        var ConsigneeTel=$('.contract_send input[name="ConsigneeTel"]').val();
		        console.log("isSend"+value)
		        console.log("PriceId"+PriceId)
		        console.log("ContractNo"+ContractNo)
		        console.log("ContractTitle"+ContractTitle)
		        console.log("ConsigneeCompany"+ConsigneeCompany)
		        console.log("ConsigneeAddress"+ConsigneeAddress)
		        console.log("ConsigneeContacts"+ConsigneeContacts)
		        console.log("ConsigneeTel"+ConsigneeTel)
		    	   $.ajax({
		            type : 'get',
		            url : 'SendToLogistics',
		            data : {
		    		    isSend:value,
		    			id:PriceId,
		    			ContractNo:ContractNo,
		    			ContractTitle:ContractTitle,
		                ConsigneeCompany : ConsigneeCompany,
		                ConsigneeAddress : ConsigneeAddress,
		                ConsigneeContacts : ConsigneeContacts,
		                ConsigneeTel : ConsigneeTel,
		            },
		            dataType : 'json',
		            success : function (data) {
		            	
		                $.MsgBox.Alert('提示','确认发货成功');
		                $('.cover_color').hide();
		                $('.contract_send').hide();
		            },
		            error : function () {
		                $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
		            }
		        }); 
		        	    
		    });

		}else{
			$.ajax({
		        type: 'get',
		        url: 'SendToLogistics',
		        data: {
		        	id:PriceId,
		        	isSend:value
		        },
		        success:function (data) {
		        	console.log("success")
		        }
		    })
		}
	}




$(function(){

	pageStyle();
	//判断是否发货栏 状态   
	for(var i = 1; i < $("#table1 tr").length ; i++){
		var tdLength =  $("#table1 tr").eq(i).children().length;
		if( $("#table1 tr").eq(i).children().eq(20).text() == "否"){
			var str = '<label><input type="radio" name="'+i+'isSend" onclick="isSendChange(this.parentNode.parentNode.parentNode,this.value)" value="是">是 </label>'+
			'<label><input type="radio"  name="'+i+'isSend" checked="checked" onclick="isSendChange(this.parentNode.parentNode.parentNode,this.value)" value="否">否</label>';
			$("#table1 tr").eq(i).children().eq(20).html(str);
		}
		else{
			var str = '<label><input type="radio" name="'+i+'isSend"  checked="checked" onclick="isSendChange(this.parentNode.parentNode.parentNode,this.value)" value="是">是 </label>'+
			'<label><input type="radio"  name="'+i+'isSend" onclick="isSendChange(this.parentNode.parentNode.parentNode,this.value)" value="否">否</label>';
			$("#table1 tr").eq(i).children().eq(20).html(str);
		}
	}

var CurrentHref;
if(window.location.href.indexOf("StockPurchasing")>-1){
    CurrentHref = window.location.href.split("column=")[1].split("&condition=");
    console.log(CurrentHref[0]+"=="+CurrentHref[1]);
    if(CurrentHref[0] == "CargoPeriod"){   //合同货期
        $("#table1 tr").children().eq(8).find('select').find("option[value='"+CurrentHref[1]+"']").attr("selected",true).siblings().attr("selected",false);
    }
    else if(CurrentHref[0] == "ExpectedDeliveryPeriod"){ //预计货期
        $("#table1 tr").children().eq(13).find('select').find("option[value='"+CurrentHref[1]+"']").attr("selected",true).siblings().attr("selected",false);
    }
    else if(CurrentHref[0] == "DateOfSign"){        //实际货期
        $("#table1 tr").children().eq(12).find('select').find("option[value='"+CurrentHref[1]+"']").attr("selected",true).siblings().attr("selected",false);
    }
}

    alertResponse(".contract-purchase",750,860);
    alertResponse(".email-send",750,860);
    $(window).on("resize",function () {
        alertResponse(".contract-purchase",750,860);
        alertResponse(".email-send",750,860);
    });

    function ajaxConInfo(ID,Supplier) {
        // alert(ID);
        return $.ajax({
            type : 'get',
            url : 'PurchaseInfo',
            data : {
                ID: ID,
                Supplier: Supplier
            },
            dataType : 'json',
            success : function (data) {
                var product = $(".contract-purchase tbody tr:eq(0) td:eq(1)");
                var Currency = $(".contract-purchase tbody tr:eq(0) td:eq(2)>select");
                var money = $(".contract-purchase tbody tr:eq(0) td:eq(3)");
                var use = $(".contract-purchase tbody tr:eq(0) td:eq(4)");
                var company = $(".contract-purchase tbody tr:eq(0) td:eq(5) span");
                var account = $(".contract-purchase tbody tr:eq(1) td:eq(0) span");
                var bank = $(".contract-purchase tbody tr:eq(2) td:eq(0) span");
                product.text("");
            	Currency.val("RMB");
            	money.text("");
            	use.text("");
            	company.text("");
            	account.text("");
            	bank.text("");
                if(data.length == 2){
                	$("#textfield").val("");
                }else if(data.length == 3){
                    var Company1 = data[1].Company;
                    var Account1 = data[1].Account;
                    var Bank1= data[1].Bank;
                    $("#textfield").val("");
                    company.text(Company1);
                    account.text(Account1);
                    bank.text(Bank1);
                }else if(data.length == 4){
                    var Product2 = data[3].Product;
                    var Currency2 = data[3].Currency;
                    var Money2 = data[3].Money;
                    var Use2 = data[3].UseFor;
                    var Account2 = data[1].Account;
                    var Bank2= data[1].Bank;
                    var Company2 = data[1].Company;
                    var ContractPath2 = data[3].ContractPath;
                    
                    company.text(Company2);
                    account.text(Account2);
                    bank.text(Bank2);
                    product.text(Product2);
                    Currency.val(Currency2);
                    money.text(Money2);
                    use.text(Use2);
                    money.trigger("blur");

                    if(ContractPath2=="--"){
                    	$("#textfield").val("");
                    }else{
                    	$("#textfield").val(ContractPath2);
                    }
                }
            },
            error : function () {
                $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
            },
            complete:function(XMLHttpRequest,textStatus){  
                if($("#textfield").val()==null || $("#textfield").val()=="" || $("#textfield").val()==undefined){
                    $("input[name='isPdfUpload']").val(0);
                }else{
                    $("input[name='isPdfUpload']").val(1);
                }
            }
        });
    }

    // 采购合同显示与银行信息显示
    $(document).on("click",".isTransport",function () {
        $("input[name='isPdfUpload']").val(0);
        console.log("采购合同显示与银行信息显示"+$("input[name='isPdfUpload']").val());
        $("#input_file").val("");
        console.log("采购合同显示与银行信息显示,file文本域的值"+$("#input_file").val());
        var Supplier = $(this).siblings("td:eq(1)").text();
        $(".contract-purchase .table1 .supplier").text(Supplier);
        var ID = $(this).siblings("td.contract-edit").attr("value");
        $("input[name='saveContractId']").val(ID);
        $('.upFileText .error1').html('');
        $(".contract-purchase tbody tr:eq(0) td:eq(5) span").text("");
        $(".contract-purchase tbody tr:eq(1) td:eq(0) span").text("");
        $(".contract-purchase tbody tr:eq(2) td:eq(0) span").text("");
        if($(this).attr("title") == "邮件未发送"){
            alertGroupOpen(".cover-color3",".contract-purchase",100);
            $(".shicong1").attr("disabled",false);
            $(".shicong1").css("cursor","pointer");
            $("#contract-purchase-upload").attr("disabled",false);
            $("#contract-purchase-upload").css("cursor","pointer");
            ajaxConInfo(ID,Supplier);
        }else{
            alertGroupOpen(".cover-color3",".contract-purchase",100);
            $(".shicong1").attr("disabled","disabled");
            $(".shicong1").css("cursor","not-allowed");
            $("#contract-purchase-upload").attr("disabled",false);
            $("#contract-purchase-upload").css("cursor","pointer");
            ajaxConInfo(ID,Supplier);
        }
    });
    // 点击发送邮件
    $(document).on("click",".contract-purchase .u-body .i-foot input",function () {
        // var fileName2 = $('#input_file').val().split("\\").pop();
        var fileName2 = $('#textfield').val();
        var isPdfUpload = $("input[name='isPdfUpload']").val();
        var reg = new RegExp(/^[0-9]+(.[0-9]{2})?$/);
        // 金额
        var cc = $(this).parent().siblings("div.i-body").find("tbody tr:eq(0) td:eq(3)").text();
        // 银行信息
        var a1 = $(this).parent().siblings("div.i-body").find("tbody tr:eq(0) td:eq(5) span").text();
        var b1 = $(this).parent().siblings("div.i-body").find("tbody tr:eq(1) td:eq(0) span").text();
        var c1 = $(this).parent().siblings("div.i-body").find("tbody tr:eq(2) td:eq(0) span").text();
        if(isPdfUpload == 0 || isPdfUpload == "0"){
            $.MsgBox_Unload.Alert("提示", "请上传合同或PO");
        }else if(a1 == null || a1 == "" || b1 == null || b1 == "" || c1 == null || c1 == ""){
            $.MsgBox_Unload.Alert('提示',"请检查银行信息是否填写完整！");
        }else{
            alertGroupOpen(".cover-color3",".email-send",100);
            var aa = $(this).parent().siblings("div.i-body").find("tbody tr:eq(0) td:eq(4)").text();
            var bb = $(this).parent().siblings("div.i-body").find("tbody tr:eq(0) td:eq(1)").text();
            var Currency = $(this).parent().siblings("div.i-body").find("tbody tr:eq(0) td:eq(2)>select").val();
            // 供应商
            $(".email-send tbody tr:eq(1) td:eq(0)").text($(this).parent().siblings("div.i-body").find("tbody tr:eq(0) td:eq(0)").text());
            // 产品
            $(".email-send tbody tr:eq(1) td:eq(1)").text(bb);
            // 币种
            $(".email-send tbody tr:eq(1) td:eq(2)").text(Currency);
            $(".email-send tbody tr:eq(1) td:eq(3)").text(cc);
            // 用途
            $(".email-send tbody tr:eq(1) td:eq(4)").text(aa);
            $(".email-send tbody tr:eq(1) td:eq(5)").text("公司："+a1);
            $(".email-send tbody tr:eq(2) td:eq(0)").text("账号："+b1);
            $(".email-send tbody tr:eq(3) td:eq(0)").text("开户行："+c1);
            $(".i-row4 input").val("Eoulu：申请付款-"+aa+"+"+bb);
            $(".email-send .email-text p:eq(1) span").text(fileName2);
        }

    });
    $(document).on("click",".contract-purchase .top i, .contract-purchase .u-foot input:nth-child(2)",function () {
        alertGroupClose(".cover-color3",".contract-purchase",100);
    });
    $(document).on("click",".email-send .u-top i, .email-send .u-foot input:nth-child(2)",function () {
        alertGroupClose(".cover-color3",".email-send",100);
    });

// ajax 提交合同与银行信息
    $("#contract-purchase-upload").on("click",function(){
        setTimeout(submitConBankInfo,50);
    });
    
    function submitConBankInfo(){
        var OrderID = $("input[name='saveContractId']").val();
        var ContractPath;
        var isPdfUpload = $("input[name='isPdfUpload']").val();
        if(isPdfUpload == 0 || isPdfUpload == "0"){
            ContractPath = "";
        }else{
            ContractPath = $("#textfield").val();
        }
        console.log("提交合同与银行信息"+ContractPath);
        var Supplier = $(".contract-purchase tbody tr:eq(0) td:eq(0)").text();
        var product = $(".contract-purchase tbody tr:eq(0) td:eq(1)").text();
        var Currency = $(".contract-purchase tbody tr:eq(0) td:eq(2)>select").val();
        var Money = $(".contract-purchase tbody tr:eq(0) td:eq(3)").text();
        var Use = $(".contract-purchase tbody tr:eq(0) td:eq(4)").text();
        var Company = $(".contract-purchase tbody tr:eq(0) td:eq(5) span").text();
        var Account = $(".contract-purchase tbody tr:eq(1) td:eq(0) span").text();
        var Bank = $(".contract-purchase tbody tr:eq(2) td:eq(0) span").text();
        var reg = new RegExp(/^[0-9]+(.[0-9]{2})?$/); 
        if(Company == null || Company == "" || Account == null || Account == "" || Bank == null || Bank == ""){
            $.MsgBox_Unload.Alert('提示',"请检查银行信息是否填写完整！");
        }else{
            $.ajax({
                type : 'get',
                url : 'SubmitPurchaseInfo',
                data : {
                    OrderID: OrderID,
                    ContractPath: ContractPath,
                    Supplier: Supplier,
                    product: product,
                    Currency: Currency,
                    Money: Money.replace(/,/g, ""),
                    Use: Use,
                    Company: Company,
                    Account: Account,
                    Bank: Bank
                },
                // dataType : 'json',
                dataType : 'text',
                success : function (data) {
                    console.log(data);
                    $.MsgBox_Unload.Alert('提示',data);
                    $('.cover_color').hide();
                    $('.contract_send').hide();
                },
                error : function () {
                    $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
                }
            });
        }
    }
    
    // ajax 提交发送邮件
    $("#email-send-upload").on("click",function(){
    	// var Contract = $('#input_file').val().split("\\").pop();
        var ID = $("input[name='saveContractId']").val();
    	var Contract = $('#textfield').val();
    	var To = $(".email-send .i-row1 input").val();
    	var CopyTo1 = $(".email-send .u-right input[name='CopyTo1']").val();
    	var CopyTo2 = $(".email-send .u-right input[name='CopyTo2']").val();
    	var CopyTo3 = $(".email-send .u-right input[name='CopyTo3']").val();
    	var Subject = $(".email-send .i-row4 input").val();
    	// var fileName3 = $('#input_file').val().split("\\").pop();
    	var fileName3 = $('#textfield').val();
    	var a = $(".email-send tbody tr:eq(1) td:eq(0)").text();
        var b = $(".email-send tbody tr:eq(1) td:eq(1)").text();
    	var Currency1 = $(".email-send tbody tr:eq(1) td:eq(2)").text();
    	var c = $(".email-send tbody tr:eq(1) td:eq(3)").text();
    	var d = $(".email-send tbody tr:eq(1) td:eq(4)").text();
    	var e = $(".email-send tbody tr:eq(1) td:eq(5)").text();
    	// var ee = $(".email-send tbody tr:eq(1) td:eq(4)").text().replace(/公司：/, "");
    	var ee = $(".contract-purchase tbody tr:eq(0) td:eq(5) span").text();
    	var f = $(".email-send tbody tr:eq(2) td:eq(0)").text();
    	// var ff = $(".email-send tbody tr:eq(2) td:eq(0)").text().replace(/账号：/, "");
    	var ff = $(".contract-purchase tbody tr:eq(1) td:eq(0) span").text();
    	var g = $(".email-send tbody tr:eq(3) td:eq(0)").text();
    	// var gg = $(".email-send tbody tr:eq(3) td:eq(0)").text().replace(/开户行：/, "");
    	var gg = $(".contract-purchase tbody tr:eq(2) td:eq(0) span").text();
    	var Input = '<p style="line-height: 22px;font-size:14px;">晓亮姐，您好！</p>'+
		'<p style="line-height: 22px;font-size:14px;">附件：<span>'+fileName3+'</span>，是采购合同</p>'+
		'<p style="line-height: 22px;font-size:14px;">烦请您根据以下银行信息安排付款，谢谢晓亮姐！</p>'+
		'<p style="line-height: 22px;font-size:14px;">如有任何问题，请随时告知，非常感谢！</p>'+
		'<table style="border-spacing: 0;height: 120px;border: none;box-sizing: border-box;border-collapse: collapse;text-align: center;vertical-align: middle;">'+
		'<thead><tr><th style="box-sizing: border-box;border-collapse: collapse;border: 1px solid #00aeef;height: 30px;width: 190px;">供应商</th><th style="box-sizing: border-box;border-collapse: collapse;border: 1px solid #00aeef;height: 30px;width: 78px;">产品</th><th style="box-sizing: border-box;border-collapse: collapse;border: 1px solid #00aeef;height: 30px;width: 78px;">币种</th><th style="box-sizing: border-box;border-collapse: collapse;border: 1px solid #00aeef;height: 30px;width: 78px;">金额</th><th style="box-sizing: border-box;border-collapse: collapse;border: 1px solid #00aeef;height: 30px;width: 115px;">用途</th><th style="box-sizing: border-box;border-collapse: collapse;border: 1px solid #00aeef;height: 30px;width: 212px;">银行信息</th></tr><thead>'+
		'<tbody><tr><td rowspan="3" style="box-sizing: border-box;border-collapse: collapse;border: 1px solid #00aeef;height: 30px;">'+a+'</td><td rowspan="3" style="box-sizing: border-box;border-collapse: collapse;border: 1px solid #00aeef;height: 30px;">'+b+'</td><td rowspan="3" style="box-sizing: border-box;border-collapse: collapse;border: 1px solid #00aeef;height: 30px;">'+Currency1+'</td><td rowspan="3" style="box-sizing: border-box;border-collapse: collapse;border: 1px solid #00aeef;height: 30px;">'+c+'</td><td rowspan="3" style="box-sizing: border-box;border-collapse: collapse;border: 1px solid #00aeef;height: 30px;">'+d+'</td><td style="box-sizing: border-box;border-collapse: collapse;border: 1px solid #00aeef;height: 30px;">'+e+'</td></tr>'+
		'<tr><td style="box-sizing: border-box;border-collapse: collapse;border: 1px solid #00aeef;height: 30px;">'+f+'</td></tr><tr><td style="box-sizing: border-box;border-collapse: collapse;border: 1px solid #00aeef;height: 30px;">'+g+'</td></tr></tbody></table>';

        $.ajax({
            type : 'get',
            url : 'SubmitPurchaseInfo',
            data : {
                OrderID: ID,
                ContractPath: fileName3,
                Supplier: a,
                product: b,
                Currency: Currency1,
                Money: c.replace(/,/g, ""),
                Use: d,
                Company: ee,
                Account: ff,
                Bank: gg
            },
            // dataType : 'json',
            dataType : 'text',
            beforeSend: function(XMLHttpRequest){
                $("#email-send-upload").attr("disabled","disabled");
                $("#email-send-upload").css({
                    "background":"#dddddd",
                    "color":"#808080",
                    "border":"none",
                    "box-shadow":"0 0 0 0 #f8fcfd",
                    "cursor":"not-allowed"
                });
            },
            success : function (data) {
                // 内部保存信息
                $.ajax({
                    type : 'get',
                    url : 'PurchasingContract',
                    data : {
                        ID: ID,
                        Contract: Contract,
                        To: To,
                        CopyTo1: CopyTo1,
                        CopyTo2: CopyTo2,
                        CopyTo3: CopyTo3,
                        Subject: Subject,
                        Input: Input
                    },
        //    		dataType : 'json',
                    dataType : 'text',
                    success : function (data) {
                        $.MsgBox.Alert('提示',data);
        //    			$('.cover_color').hide();
        //    			$('.contract_send').hide();
                    },
                    error : function () {
                        $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
                    }
                });
            },
            error : function () {
                $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
            },
            complete: function(XMLHttpRequest, textStatus){
                $("#email-send-upload").attr("disabled",false);
                $("#email-send-upload").css({
                  "background":"#00aeef",
                  "color":"#fff",
                  "border":"solid 1px #00aeef",
                  "box-shadow":"1px 2px 5px 0 #00aeef",
                  "cursor":"pointer"
                });
            }
        });

    });
    
    pageResponse();
    $(window).on("resize",function(){
    	pageResponse();
    });
});

$('.purchase_export').click(function() {
    var query_type = $('input[name="query_type"]').val();
    console.log(query_type);
    var ActualDelivery = window.location.href.indexOf("StockPurchasing")>-1?window.location.href.split("ActualDelivery=")[1].split("&")[0]:"";
    var column = window.location.href.indexOf("StockPurchasing")>-1?window.location.href.split("column=")[1].split("&")[0]:"CargoPeriod";
    // var condition = window.location.href.indexOf("StockPurchasing")>-1?window.location.href.split("condition=")[1].split("&")[0]:"";
    var condition = $("#condition_sel").val();
    var classify1 = $('input[name="classify1"]').val();
    var classify2 = $('input[name="classify2"]').val();
    var parameter1 = $('input[name="parameter1"]').val();
    var parameter2 = $('input[name="parameter2"]').val();
    // 传参处理
    if (query_type == 'singleSelect' && (classify1 == '合同货期' || classify1 == '实际货期' || classify1 == '预计货期')) {
        parameter1 = $('input[name="start_time1"]').val();
        parameter2 = $('input[name="end_time1"]').val();
    }
    if(ActualDelivery==""){
        switch(condition)
        {
            case "All":
            ActualDelivery = "no";
            break;
            case "AllNoSend":
            ActualDelivery = "yes";
            break;
            case "OtherNoSend":
            ActualDelivery = "yes";
            break;
            default:
            ActualDelivery = "no";
        }
    }
        $(".purchase_export").attr("disabled","disabled");
        $(".purchase_export").css({
            "background":"#ddd",
            "color":"rgba(0,0,0,0.7)",
            "border":"none",
            "cursor":"not-allowed"
        });
        $.ajax({
            type : 'get',
            url : 'ExportOrder',
            data : {
                Type: 1,
                query_type : query_type,
                classify1 : classify1,
                classify2 : classify2,
                parameter1 : parameter1,
                parameter2 : parameter2,
                ActualDelivery : ActualDelivery,
                column : column,
                condition : condition
            },
            beforeSend: function(XMLHttpRequest){
                $.Response_Load.Before("导出提示","正在传输数据......",200);
            },
            complete: function(XMLHttpRequest, textStatus){
                if(textStatus=='success'){
                    $.Response_Load.After("导出完成！",300);
                }
            },
            // dataType: 'json',
            success : function(data) {
                console.log(data);
                window.location.href = data;
                $(".purchase_export").attr("disabled",false);
                $(".purchase_export").css({
                    "background":"#00aeef",
                    "color":"#fff",
                    "border":"none",
                    "box-shadow":"1px 2px 5px 0 #00aeef",
                    "cursor":"pointer"
                });
            },
            error : function() {
                $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
            }
        });
});

$(".purchase_export").hover(function(){
    $(this).css({
        "background": "#fff",
        "color": "#00aeef"
    });
}, function(){
    $(this).css({
        "background": "#00aeef",
        "color": "#fff"
    });
}); 

// add搜索产品
$(document).on("keyup",'input[name="search"]',function(e){
  var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
  if(eCode == 13){
      //自己写判断操作
    if($(this).val()==""){
        return;
    }
    var addKeyword = $(this).val().trim();
    if(addKeyword == "" || addKeyword == "--"){
        $.MsgBox_Unload.Alert("提示", "model_number搜索值为空或格式错误");
        return;
    }else{
        var Key = $(this).val();
        $.ajax({
            type : 'GET',
            url : 'GetCommodityModel',
            data: {
                Key: Key
            },
            success : function (data) { 
                var newData = JSON.parse(data);
                console.log(newData);
                var str='';
                if(newData.length>1){
                    for(var i = 1;i<newData.length;i++){
                        str+='<option value="'+newData[i].Model+'" text="'+newData[i].ID+'" sellerpriceone="'+newData[i].SellerPriceOne+'">'+newData[i].Model+'&nbsp;:&nbsp;'+newData[i].SellerPriceOne+'</option>';
                    }
                }
                $("#select").empty().append(str);
                $('#select').show();
            },
            error : function () {
                $.MsgBox_Unload.Alert("提示", "服务器繁忙，model_number获取有误！");
            }
        });
    }
  }else{
    if($(this).val()==""){
      $(this).attr("placeholder","输入型号按回车搜索");
    }
  }
});

$(document).on("focus", "input[name='search']", function () {
    $(this).attr("placeholder","");
});

$(document).on("click", "#select option", function () {
    $('#select').hide();
    $('input[name="search"]').val($('#select').val());
});

// add搜索供应商
$(document).on("input propertychange",'.contract_add input[name="customer"]',function(){
    if($(this).val()==""){
        return;
    }
    var addKeyword = $(this).val().trim();
    if(addKeyword == "" || addKeyword == "--"){
        $.MsgBox_Unload.Alert("提示", "搜索值为空或格式错误");
        return;
    }else{
        var CustomerName = $(this).val();
        $.ajax({
            type : 'GET',
            url : 'GetSupplier',
            data: {
                key: CustomerName
            },
            success : function (data) { 
                var newData = JSON.parse(data);
                console.log(newData);
                var str='';
                if(newData.length>1){
                    for(var i = 1;i<newData.length;i++){
                        str+='<option value="'+newData[i].Name+'" text="'+newData[i].ID+'" contact="'+newData[i].Contact+'" contactinfo="'+newData[i].ContactInfo+'">'+newData[i].Name+'&nbsp;:&nbsp;'+newData[i].Contact+'</option>';
                    }
                }
                $("#addSuppier").empty().append(str);
                $('#addSuppier').fadeIn(200);
            },
            error : function () {
                $.MsgBox_Unload.Alert("提示", "服务器繁忙，客户名称获取有误！");
            }
        });
    }
});

// update搜索供应商
$(document).on("input propertychange",'.contract_update input[name="customer"]',function(){
    if($(this).val()==""){
        return;
    }
    var addKeyword = $(this).val().trim();
    if(addKeyword == "" || addKeyword == "--"){
        $.MsgBox_Unload.Alert("提示", "搜索值为空或格式错误");
        return;
    }else{
        var CustomerName = $(this).val();
        $.ajax({
            type : 'GET',
            url : 'GetSupplier',
            data: {
                key: CustomerName
            },
            success : function (data) { 
                var newData = JSON.parse(data);
                console.log(newData);
                var str='';
                if(newData.length>1){
                    for(var i = 1;i<newData.length;i++){
                        str+='<option value="'+newData[i].Name+'" text="'+newData[i].ID+'" contact="'+newData[i].Contact+'" contactinfo="'+newData[i].ContactInfo+'">'+newData[i].Name+'&nbsp;:&nbsp;'+newData[i].Contact+'</option>';
                    }
                }
                $("#updateSuppier").empty().append(str);
                $('#updateSuppier').fadeIn(200);
            },
            error : function () {
                $.MsgBox_Unload.Alert("提示", "服务器繁忙，客户名称获取有误！");
            }
        });
    }
});

// 点击其他关闭
$(document).on("click", function() {
    $('#addSuppier').fadeOut(200);
    $('#updateSuppier').fadeOut(200);
});

$(document).on("click", '#addSuppier option', function(e) {
    e.stopPropagation();
    $('.contract_add input[name="customer"]').val($('#addSuppier').val());
    $('.contract_add input[name="contact"]').val($(this).attr("contact"));
    $('.contract_add input[name="contact_info"]').val($(this).attr("contactinfo"));
    $('#addSuppier').hide();
});
$(document).on("click", '#updateSuppier option', function(e) {
    e.stopPropagation();
    $('.contract_update input[name="customer"]').val($('#updateSuppier').val());
    $('.contract_update input[name="contact"]').val($(this).attr("contact"));
    $('.contract_update input[name="contact_info"]').val($(this).attr("contactinfo"));
    $('#updateSuppier').hide();
});

$(".preview_download input[value='预览']").on("click",function(e){
    e.stopPropagation();
    var isPdfUpload = $("input[name='isPdfUpload']").val();
    if(isPdfUpload == 0 || isPdfUpload == "0"){
        $.MsgBox_Unload.Alert("提示", "请上传合同或PO");
        return false;
    }
    var fileName = $("#textfield").val();
    var baseHref = window.location.href.split("cfChicken8")[0]+"LogisticsFile/File/";
    // window.location.href = baseHref+fileName;
    window.open(baseHref+fileName);
});

$(".preview_download input[value='下载']").on("click",function(e){
    var isPdfUpload = $("input[name='isPdfUpload']").val();
    if(isPdfUpload == 0 || isPdfUpload == "0"){
        $.MsgBox_Unload.Alert("提示", "请上传合同或PO");
        return false;
    }
    var fileName = $("#textfield").val();
    // if ( e && e.preventDefault ){
    //     e.preventDefault();
    // }else{
    //     window.event.returnValue = false;
    // }
    var body9 = $(document.body),
        form9 = $("<form method='post'><input type='text' name='FileName' value='"+fileName+"'></form>");
    form9.attr({"action":"StockPurchasing"});
    form9.appendTo(document.body);
    form9.submit();
    document.body.removeChild(form9[0]);
    // $.ajax({
    //     type: "POST",
    //     url: "StockPurchasing",
    //     data: {
    //         FileName: fileName
    //     },
    //     success: function(data){
    //         console.log(typeof data);
    //         if(data.indexOf("文件已被删除")>-1){
    //             $.MsgBox_Unload.Alert("提示", "文件已被删除！");
    //         }else{
    //             window.location.href = data;
    //         }
    //     },
    //     error: function(){
    //         $.MsgBox_Unload.Alert("提示", "网络繁忙！");
    //     }
    // });
});

// 金额处理input propertychange
$(document).on("blur", "td.money_td", function(){
    var newVal = $(this).text().replace(/[^\d^.]/g,'');
    var newValArr;
    if(newVal.indexOf(".")>-1){
        newValArr = newVal.split(".");
        $(this).text(ecDo.formatText(newValArr[0], 3, ",") + "." + Number(newValArr[1].substring(0,2)).toString());
    }else{
        newValArr = [];
        newValArr.push(Number(newVal).toString());
        $(this).text(ecDo.formatText(newValArr[0], 3, ","));
    }
    // document.getElementsByClassName("money_td")[0].setSelectionRange($(this).text().length, $(this).text().length);
    // setFocus.call($(this).get(0));
});

function setFocus() {
    var range = this.createTextRange(); //建立文本选区
    range.moveStart('character', this.value.length); //选区的起点移到最后去
    range.collapse(true);
    range.select();
}