/**
 * Created by eoulu on 2017/3/29.
 */
var ContractObj = {};
var mailAjaxObj = {};
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
  $(".contract_add input.customer_search").val("");
	$('.contract_add select').each(function(){
	$(this).find('option:checked').prop("selected",false);
	$(this).find('option').filter(function(){return $(this).text()=="";}).prop("selected",true);
	  });
    $('.cover-color').show();
    $('.contract_add').show();
}
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
    if($('.contract_add select[name="whether_to_pay"]').val() == '已付款'){
    	var date = new Date();
    	var Y = date.getFullYear();
    	var M = date.getMonth()+1;
    	var D = date.getDate();
    	if(0<M<=9){
    		var m = '0'+ M;
    	}else{
    		var m = M;
    	}
    	if(0<D<=9){
    		var d = '0'+ D;
    	}else{
    		var d = D;
    	}
    	var time  = Y +'-'+ m +'-'+d;
    	$(".contract_add .pay_date").val(time);
    }
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

$('#add_submit').click(function () {
   /* var id=$('.contract_add input[name="order_id"]').val();*/
    var customer=$('.contract_add input[name="customer_search"]').val();
    var customerID=$('.contract_add .customerID_span').text();
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
    var ContractPath = $('.contract_add .add_fileCont').val();
    var TechnologyPath = $('.contract_add .add_fileCont1').val();
    var QuoteNumber = $('.contract_add .add_quotation').attr("id");
    var QuoteN = $('.contract_add .add_quotation').val();
   /* (QuoteN =="--" ||  QuoteN ==" ")? QuoteNumber=0 :QuoteNumber = QuoteNumber;*/
    if(QuoteN == '--' ||  QuoteN == ''){
    	QuoteNumber=0;
    }
   
    //新增2个mb10 
    var pay_date  = $('.contract_add .pay_date').val();
    var tracking_no = $('.contract_add .tracking_no').val();
    var quotation_Content = $(".add_quotation").val();
    
    function Checkquotation(){
       	 $.ajax({
   		        type : 'get',
   		        url : 'OrderAdd',
   		        data : {
   		        	id:0,
   		        	customerID:customerID,
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
   		            PageType :0,
   		            ContractPath : ContractPath,
   		            TechnologyPath : TechnologyPath,
   		            QuoteNumber : QuoteNumber,
   		            pay_date:pay_date,
   		            tracking_no:tracking_no
   		        },
   		        dataType : 'json',
   		        success : function (data) { 
   		        	console.log(data)
   		             data = eval("(" + data + ")");
   		             $.MsgBox.Alert('提示', data.message);
   		             $('.cover-color').hide();
   		             $('.contract_add').hide();
   		           
   		        },
   		        error : function () {
   		            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
   		        }
   		    });
       } 
    
    
    
    if(quotation_Content == ''|| quotation_Content == '--'){
    	Checkquotation();
    }else{
    	$.ajax({
    		type:'get',
    		url:'QueryQuoteNumber',
    		data:{
    			QuoteNumber:quotation_Content
    		},
    		success:function(data){
    			if(data == 'false'){
    				$.MsgBox_Unload.Alert("提示", "报价单号填写错误！"); 
    				return;
    			}else{
    				Checkquotation();
    			}
    		},
    		error:function(){
    			 $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
    		}
    	})
    } 

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
$('.contract-edit').click(function () {
    var tr=$(this).parent();
    ContractObj.contractName = tr.find("td.ContractTitleTD").text();
    ContractObj.contractNo = tr.find("td.ContractNoTD").text();
    ContractObj.ID = $(this).attr("value");
    ContractObj.Customer = tr.find("td.t1_Customer").text();
    ContractObj.Contact = tr.find("td.ContactTD").text();
    ContractObj.Number = tr.find("td.t1_qNumber").text();
    mailAjaxObj.ID = $(this).attr("value");
    mailAjaxObj.CustomerName = tr.find("td.t1_Customer").text();
    mailAjaxObj.ContractNO = tr.find("td.ContractNoTD").text();
    mailAjaxObj.ContractName = tr.find("td.ContractTitleTD").text();
    var contract_update=$('.contract_update');
	    contract_update.find('span.order_id').text($(this).attr("value"));
	    contract_update.find('input[name="customer_search"]').val(tr.find('td.t1_Customer').html());
	    contract_update.find('span.customerID_span').text(tr.find('td.customerID_tr').html());
	    contract_update.find('input[name="contact"]').val(tr.find('td.ContactTD').html());
	    contract_update.find('input[name="contact_info"]').val(tr.find('td.ContactInfoTD').html());
	    contract_update.find('select[name="area"]').find('option[text="' + tr.find('td.t1_Area').html() + '"]').prop("selected", true);
	    contract_update.find('input[name="contract_title"]').val(tr.find('td.ContractTitleTD').html());
	    contract_update.find('input[name="contract_no"]').val(tr.find('td.ContractNoTD').html());
	    if(tr.find('td.t1_ContractCategory').html()==""||tr.find('td.t1_ContractCategory').html()=="--"){
		    contract_update.find('select[name="contract_category"]').find('option[text=""]').prop("selected", true);

	    }else{
		    contract_update.find('select[name="contract_category"]').find('option[text="' + tr.find('td.t1_ContractCategory').html() + '"]').prop("selected", true);

	    }
	    contract_update.find('select[name="sales_representative"]').find('option[text="' + tr.find('td.t1_SalesRepresentative').html() + '"]').attr("selected", true);
	    contract_update.find('input[name="date_of_sign"]').val( tr.find('td.t1_DateOfSign').html());
	    contract_update.find('input[name="cargo_period"]').val(tr.find('td.t1_CargoPeriod').html());
	    contract_update.find('input[name="actual_delivery"]').val(tr.find('td.t1_ActualDelivery').html());
	    contract_update.find('input[name="expected_delivery_period"]').val(tr.find('td.t1_ExpectedDeliveryPeriod').html());   
	    contract_update.find('input[name="expected_receipt_date"]').val(tr.find('td.t1_ExpectedReceiptDate').html());
	    contract_update.find('select[name="status"]').find('option[text="' + tr.find('td.t1_Status').html() + '"]').prop("selected", true);
	    
	    contract_update.find(".update_quotation").val(tr.find('td.t1_qNumber').html());
	    contract_update.find(".update_quotation").attr("id",tr.find('td.t1_qNumber').attr("ID"));
	    //新增
	    contract_update.find(".pay_date").val(tr.find('td.pay_date').html());
	    contract_update.find(".tracking_no").val(tr.find('td.tracking_no').html());
	    if(tr.find('td.t1_InstalledTime').html().slice(0, 2)=='--'){
	    	contract_update.find('input[name="installed_time"]').val('');
	 	    contract_update.find('input[name="installed_site"]').val(tr.find('td.t1_InstalledTime').html().slice(2));
	    }else{
	    	contract_update.find('input[name="installed_time"]').val(tr.find('td.t1_InstalledTime').html().slice(0, 10));
	 	    contract_update.find('input[name="installed_site"]').val(tr.find('td.t1_InstalledTime').html().slice(10));
	    }
	    contract_update.find('textarea[name="remarks"]').val(tr.find('td.t1_Remarks .t1_RemarksCon').html());
	    contract_update.find('.update_fileCont,.update_fileCont1').val("");
	    if($(".tab_ContractPath")){contract_update.find(".update_fileCont").val(tr.find('td.t1_Remarks .tab_ContractPath').val());}
	    if($(".tab_TechnologyPath")){contract_update.find(".update_fileCont1").val(tr.find('td.t1_Remarks .tab_TechnologyPath').val());}
	  
	    var order_id = $(this).attr("value");
	    $('.cover-color').show();
	    contract_update.show();
      mailAjaxObj.Contract = tr.find('td.t1_Remarks .tab_ContractPath').val()==undefined?"":tr.find('td.t1_Remarks .tab_ContractPath').val();
    $.ajax({
        type : 'get',
        url : 'GetQuotes',
        data : {
            order_id:order_id
          
        },
        dataType : 'json',
        success : function (data) {
        	//xinzeng
          if(data.length>1){
              contract_update.find('.pay_date').val(data[1].PayDate);
              contract_update.find('.tracking_no').val(data[1].TrackingNo);
              contract_update.find('input[name="usd_quotes"]').val(data[1].USDQuotes);
              contract_update.find('input[name="rmb_quotes"]').val(data[1].RMBQuotes);
              contract_update.find('.pay_search').val(contract_update.find('select[name="payment_terms"]').val(data[1].PaymentTermsID));
              contract_update.find('select[name="payment_terms"] option:checked').prop("selected", false);
              if (data[1].PaymentTermsID == '--') {
                  contract_update.find('select[name="payment_terms"]').find('option').filter(function() {
                      return $(this).text() == "";
                  }).prop("selected", true);
              } else {
                  contract_update.find('select[name="payment_terms"]').find('option[value="' + data[1].PaymentTermsID + '"]').prop("selected", true);
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
              contract_update.find('select[name="whether_to_pay"] option:checked').prop("selected", false);
              contract_update.find('select[name="whether_to_pay"]').find('option[value="' + data[1].WhetherToPay + '"]').prop("selected", true);
              contract_update.find('select[name="duty_free"]').find('option[text="' + data[1].DutyFree + '"]').prop("selected", true);
              contract_update.find('select[name="whether_to_invoice"]').find('option[value="' + data[1].WhetherToInvoice + '"]').prop("selected", true);
              contract_update.find('textarea[name="whether_to_pay_remarks"]').val(data[1].WhetherToPayRemarks);
              contract_update.find('textarea[name="whether_to_invoice_remarks"]').val(data[1].WhetherToInvoiceRemarks);
              contract_update.find('textarea[name="duty_free_remarks"]').val(data[1].DutyFreeRemarks);
            }else{
              contract_update.find('select[name="payment_terms"] option:checked').prop("selected", false);
              contract_update.find('input[name="pay_search"]').val($('.contract_update select[name="payment_terms"] option:selected').text());
              contract_update.find('select[name="whether_to_pay"] option:checked').prop("selected", false);
            }
        },
        error : function () {
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});
$('.contractUpdate_close').click(function () {
    ContractObj = {};
    mailAjaxObj = {};
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
	var order_id=contract_update.find('.order_id').text();
    var customer=$('.contract_update input[name="customer_search"]').val();
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
    var ContractPath = $('.contract_update .update_fileCont').val();
    var TechnologyPath = $('.contract_update .update_fileCont1').val();
    var QuoteNumber = $('.contract_update .update_quotation').attr("id");
    var QuoteN = $('.contract_update .update_quotation').val();
   /* (QuoteN =="--" ||  QuoteN ==" ")? QuoteNumber=0 :QuoteNumber = QuoteNumber;*/
    if(QuoteN == '--' ||  QuoteN == ''){
    	QuoteNumber=0;
    }
  //新增2个mb10 
    var pay_date  = $('.contract_update .pay_date').val();
    var tracking_no = $('.contract_update .tracking_no').val();
    var customerID=$('.contract_update .customerID_span').text();
    
    var quotation_Content = $(".update_quotation").val();
    if(quotation_Content == ''|| quotation_Content == '--'){
    	Checkquotation_Update();
    }else{
    	$.ajax({
    		type:'get',
    		url:'QueryQuoteNumber',
    		data:{
    			QuoteNumber:quotation_Content
    		},
    		success:function(data){
    			if(data == 'false'){
    				$.MsgBox_Unload.Alert("提示", "报价单号填写错误！"); 
    				return;
    			}else{
    				Checkquotation_Update();
    			}
    		},
    		error:function(){
    			 $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
    		}
    	});
    }
   
	function Checkquotation_Update(){
		 $.ajax({
		        type : 'post',
		        url : 'ModifyOrder',
		        data : {
		            id:order_id,
		            customerID:customerID,
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
                // PageType: 1,
		            PageType: 0,
		            ContractPath : ContractPath,
		            TechnologyPath : TechnologyPath,
		            QuoteNumber : QuoteNumber,
		            pay_date:pay_date,
		            tracking_no:tracking_no
		        },
		        dataType : 'json',
		        success : function (data) {
              console.log(data);
		        	console.log(typeof data);
                $.MsgBox.Alert('提示','修改成功');
		            // $.MsgBox.Alert('提示',data);
		            $('.cover-color').hide();
		            $('.contract_update').hide();
		        },
		        error : function () {
		            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
		        }
		    });
	}		   

});
$('#update_cancel').click(function () {
    ContractObj = {};
    mailAjaxObj = {};
    $('.cover-color').hide();
    $('.contract_update').hide();
});


//--------------------------------------------合同配置信息页面-----------------------------------------------
$('.contract-show').click(function () {
    var order_id = $(this).attr("value");
    $('#order_id').val(order_id);
    //存储当前条报价单ID  头部
    $('#order_id').attr("QuoteNumber", $(this).attr("QuoteNumber"));
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
                    '<td>' + i + '</td>' +
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
            	$('.contract .contractPage thead tr').find('th').eq(9).hide();
            	$('.contract .contractPage thead tr').find('th').eq(10).hide();
            	$('.contract .contractPage tbody tr').find('td').eq(10).hide();
            	$('.contract .contractPage tbody tr').find('td').eq(11).hide();
            	$('.contract .edit_btn').hide();
            }else{
            	$('.contract .contractPage thead tr').find('th').eq(9).show();
            	$('.contract .contractPage thead tr').find('th').eq(10).show();
            	$('.contract .contractPage tbody tr').find('td').eq(10).show();
            	$('.contract .contractPage tbody tr').find('td').eq(11).show();
            	$('.contract .edit_btn').show();
            }
            $('.contract input[name="search"]').val("");
            $('.contract input[name="equipment_counts"]').val("");
            $(".contract .apply_add_info").hide();
            $('.cover-color, .contract').show();
        },
        error: function () {
             $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});
$('.contract_close').click(function () {
    $('input[name="search"]').attr("placeholder","输入型号按回车搜索");
    $("#select").empty();
    $('.cover-color, .contract').hide();
});

// --------------------------------------------修改合同配置信息页面-----------------------------------------------
$(document).on("click", "input[name='contract-update']", function () {
	$(this).parent().find('input[name="update-save"]').show();
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
                            '<td>' + i + '</td>' +
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
                    $('.cover-color, .contract').show();
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
                            '<td>' + i + '</td>' +
                            '<td>' + data[i].EquipmentModel + '</td>' +
                            '<td>' + data[i].Remarks + '</td>' +
                            '<td><input value="' + data[i].Number + '" type="text" style="width: 45px;" disabled></td>' +
                            '<td>' + data[i].Date + '</td>' +
                            '<td>' + data[i].ExceptDate + '</td>' +
                            '<td>' + data[i].DeliveryNumber + '</td>' +
                            '<td>' + data[i].Status + '</td>' +
                            '<td> <input name="contract-update" value="修改" class="bToggle" type="button"  ><input name="update-save" value="保存" class="bToggle" type="button" style="display:none" ></td>' +
                            '<td> <input name="contract-delete" value="删除" class="bToggle" type="button" ></td>' +
                            '</tr>';
                        $('.contract .contractPage tbody').append(tr);
                    }
                    $('.cover-color, .contract').show();
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


// 提交添加合同配置信息
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
                            '<td>' + i + '</td>' +
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
                    '<td>' + i + '</td>' +
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
            $('.cover-color, .contract').show();
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
$('#fa-button1').bind('click',function(){
 $('#fa-button1').toggleClass('fa-minus-square');
 $('#table1 tr').find('td.ContactTD,td.tog_ContactTD').toggle();
 $('#table1 tr').find('td.ContactInfoTD,td.tog_ContactInfoTD').toggle();
 $('#table1 tr').find('td.t1_Area,td.tog_Area').toggle();
 $('#table1 tr').find('td.t1_SalesRepresentative,td.tog_SalesRepresentative').toggle();
 $('#table1 tr').find('td.t1_Details,td.tog_Details').toggle();
});
$('#fa-button2').bind('click',function(){
 $('#fa-button2').toggleClass('fa-minus-square');
 $('#table1 tr').find('td.ContractNoTD,td#contractNum').toggle();
});
$('#fa-button3').bind('click',function(){
 $('#fa-button3').toggleClass('fa-minus-square');
 $('#table1 tr').find('td.t1_InstalledTime,td.tog_InstalledTime').toggle();
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
});
//--------------------------------------------添加合同配置信息页面-----------------------------------------------
$('#contract_apply_add').click(function() {
	$('.apply_add_info').show();
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
Picture(south,north,southwest);

function Picture(south,north,southwest){
	$('.prompt-alert').html('<div class="swiper-wrapper">'+
			'<div class="swiper-slide">'+
			'<span>南方'+showJudge(south).text+'</span>'+
			'<img src="image/'+showJudge(south).pic[1]+'"/>'+
			'</div>'+
			'<div class="swiper-slide">'+
			'<span>北方'+showJudge(north).text+'</span>'+
			'<img src="image/'+showJudge(north).pic[0]+'"/>'+
			'</div>'+
			'<div class="swiper-slide">'+
			'<span>西南'+showJudge(southwest).text+'</span>'+
			'<img src="image/'+showJudge(southwest).pic[2]+'"/>'+
			'</div>'+
			'</div>');

var mySwiper = new Swiper ('.prompt-alert', {
autoplay: 3000,
loop: true,
autoplayDisableOnInteraction : false
});
}

//--------------------------------------------点击    预计货期   触发事件-----------------------------------------------------

//合同货期
$("#table1 tr .title_contract").find('select').on("change",function(){
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
    });
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
$("#table1 tr .title_actual").find('select').on("change",function(){
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
$("#table1 tr .title_Expected").find('select').on("change",function(){
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
			$('.cover-color').show();
		    $('.contract_send').show();
		    $('.contract_send').find('input[name="ConsigneeContacts"]').val(ConsigneeContacts);
		    $('.contract_send').find('input[name="ConsigneeTel"]').val(ConsigneeTel);
		    
		  //点击确认按钮
		    $(document).on("click","#send_submit",function(){
		    	/*var PriceId=$('.contract_send input[name="PriceId"]').val();
		        var ContractNo=$('.contract_send input[name="ContractNo"]').val();
		        var ContractTitle=$('.contract_send input[name="ContractTitle"]').val();*/
		    	var ConsigneeCompany=$('.contract_send input[name="ConsigneeCompany"]').val();
		        var ConsigneeAddress=$('.contract_send input[name="ConsigneeAddress"]').val();
		        var ConsigneeContacts=$('.contract_send input[name="ConsigneeContacts"]').val();
		        var ConsigneeTel=$('.contract_send input[name="ConsigneeTel"]').val();
		        
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
	//判断是否发货栏 状态   
	for(var i = 1; i < $("#table1 tr").length ; i++){
		var tdLength =  $("#table1 tr").eq(i).children().length;
		if( $("#table1 tr").eq(i).find(".t1_isSend").text() == "否"){
			var str = '<label><input type="radio" name="'+i+'isSend" onclick="isSendChange(this.parentNode.parentNode.parentNode,this.value)" value="是">是 </label>'+
			'<label><input type="radio"  name="'+i+'isSend" checked="checked" onclick="isSendChange(this.parentNode.parentNode.parentNode,this.value)" value="否">否</label>';
			$("#table1 tr").eq(i).find(".t1_isSend").html(str);
		}
		else{
			var str = '<label><input type="radio" name="'+i+'isSend"  checked="checked" onclick="isSendChange(this.parentNode.parentNode.parentNode,this.value)" value="是">是 </label>'+
			'<label><input type="radio"  name="'+i+'isSend" onclick="isSendChange(this.parentNode.parentNode.parentNode,this.value)" value="否">否</label>';
			$("#table1 tr").eq(i).find(".t1_isSend").html(str);
		}
	}

if(window.location.href.indexOf("column=")>-1 && window.location.href.indexOf("&condition=")>-1){
  var CurrentHref = window.location.href.split("column=")[1].split("&condition=");
  if(CurrentHref[0] == "CargoPeriod"){   //合同货期
    $("#table1 tr .title_contract").find('select').find("option[value='"+CurrentHref[1]+"']").attr("selected",true).siblings().attr("selected",false);
  }
  else if(CurrentHref[0] == "ExpectedDeliveryPeriod"){ //预计货期
    $("#table1 tr .title_Expected").find('select').find("option[value='"+CurrentHref[1]+"']").attr("selected",true).siblings().attr("selected",false);
  }
  else if(CurrentHref[0] == "DateOfSign"){    //实际货期
    $("#table1 tr .title_actual").find('select').find("option[value='"+CurrentHref[1]+"']").attr("selected",true).siblings().attr("selected",false);
  }
}

});


/* 实时汇率的显示 */
$(".rateLeft").click(function(){
	$(".rateRight").toggle();
});
$(document).bind('click', function(e) {  
                var e = e || window.event; //浏览器兼容性   
                var elem = e.target || e.srcElement;  
                while (elem) { //循环判断至跟节点，防止点击的是div子元素   
                    if (elem.id && elem.id == 'exchangeRate') {  
                        return;  
                    }  
                    elem = elem.parentNode;  
                }  
                $('.rateRight').css('display', 'none'); //点击的不是div或其子元素   
            });
$(".rate_icon").click(function(){
	$(".number1").val("");
	/*alert($(".number1").attr("placeholder"))*/
	if($(".part2_right").html() == '(美元)USD'){
		$(".part2_right").html("").html("(人民币)CNY");
		$(".part3_right").html("").html("(美元)USD");
		$.ajax({
			url:'http://api.fixer.io/latest?base=CNY',
      dataType: 'jsonp',
      crossDomain: true,
			success:function(data){
				console.log(data);
				console.log(data.rates.USD);
				$(".part3_left").html(data.rates.USD);
			},
			error:function(){
        // alert("错误在这里");
				// $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        $.MsgBox_Unload.Alert("提示", "服务器繁忙，汇率请求失败！"); 
			}
		});
	
	}else{
		$(".part2_right").html("").html("(美元)USD");
		$(".part3_right").html("").html("(人民币)CNY");
		$.ajax({
			url:'http://api.fixer.io/latest?base=USD',
      dataType: 'jsonp',
      crossDomain: true,
			success:function(data){
				$(".part3_left").html(data.rates.CNY);
			},
			error:function(){
        // alert("错误在这里2");
				// $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        $.MsgBox_Unload.Alert("提示", "服务器繁忙，汇率请求失败！"); 
			}
		});
	}
	
});
function aa(e){
	var number1 = $(".number1").val();
	var reg  = "^[0-9]*$";
	if(!number1.match(reg)){
		$(".part3_left").html('0');
		/*$(".rateRight").css("display","block");
			$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");*/
			return;
}
	if($(".part2_right").html() == '(美元)USD'){
		$.ajax({
			url:'http://api.fixer.io/latest?base=USD',
      dataType: 'jsonp',
      crossDomain: true,
			success:function(data){
				var r = data.rates.CNY;
				var N = number1 * r ;
				$(".part3_left").html(N);
			},
			error:function(){
        // alert("错误在这里3");
				// $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        $.MsgBox_Unload.Alert("提示", "服务器繁忙，汇率请求失败！"); 
			}
		});
}else{
	$.ajax({
		url:'http://api.fixer.io/latest?base=CNY',
    dataType: 'jsonp',
    crossDomain: true,
		success:function(data){
			var r = data.rates.USD;
			var N = number1 * r ;
			$(".part3_left").html(N);
		},
		error:function(){
      // alert("错误在这里4");
			// $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
      $.MsgBox_Unload.Alert("提示", "服务器繁忙，汇率请求失败！"); 
		}
	});
}
}

//添加   报价单内容 检索
$(".add_quotation,.update_quotation").on("input propertychange",function(){
	var Number = $(this).val();
	console.log(Number);
	$.ajax({
        type : 'get',
        url : 'GetAllQuoteNumber',
        data : {
        	Number:Number,
        },
        dataType : 'json',
        success : function (data) {            
             var str = "";
             for(var i = 1 ; i < data.length ; i++){
            	 str += "<li ID="+data[i].ID+" class='addListLi'>"+data[i].Number+"</li>";
             }
             $(".quotationlist").empty();
             if($(this).attr("class") == "add_quotation"){
            	 $(".quotationlist").append(str);
             }
             else{
            	 $(".quotationlist").append(str);
             }
             $(".quotationlist").show();
        },
        error : function () {
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});
$(document).on("click",function(){
	$(".quotationlist").hide();
});
$(document).on("click",".addListLi",function(){
	var currentCont = $(this).text();
	
	var ID= $(this).attr("ID");
	$(this).parent().prev().val(currentCont);
	$(this).parent().prev().attr("ID",ID);
	$(".quotationlist").hide();
});


$(function(){
	$.ajax({
		url:'http://api.fixer.io/latest?base=USD',
    dataType: 'jsonp',
    crossDomain: true,
    // jsonp: "callBack",//服务端用于接收callback调用的function名的参数
		success:function(data){
			$(".part1_three").html(data.rates.CNY);
			$(".part3_left").html(data.rates.CNY);
		},
		error:function(){
      // alert("错误在这里5");
			// $.MsgBox_Unload.Alert("提示", "服务器繁忙，汇率请求失败！");
      console.log("http://api.fixer.io/latest?base=USD--请求错误");
		}
	});
});
// 合同文件 上传
$(document).on("change",".add_file,.update_file",function(){
    var filePath=$(this).val().replace("C:\\fakepath\\","");
    if($(this).attr("class").indexOf("add_file") > 0){
    	$(".add_fileCont").val(filePath);
    	$("#add_fileBox1").submit();
//    	return false;
    }
    else{
    	$(".update_fileCont").val(filePath);
    	$("#update_fileBox1").submit();
//    	return false;
    }
});

// 技术协议  上传   
$(document).on("change",".add_agreement,.update_agreement",function(){
    var filePath=$(this).val().replace("C:\\fakepath\\","");
    if($(this).attr("class").indexOf("add_agreement") > 0){
    	 $(".add_fileCont1").val(filePath);
    	 $("#add_fileBox2").submit();
//    	 return false;
    }
    else{
    	$(".update_fileCont1").val(filePath);
    	$("#update_fileBox2").submit();
//    	return false;
    }
   
});

//合同配置 匹配报价单
$("#MatchQuotation").click(function(){
	var OrderID =$('#order_id').val();
	var QuoteID =$('#order_id').attr("QuoteNumber");
	//console.log(QuoteID)
	//console.log(OrderID)
	if(OrderID =="--"||OrderID =="" ||QuoteID=="--" || QuoteID ==""){
		$.MsgBox_Unload.Alert("提示", "无报价单号，请添加！");
	}
	else{
		$.ajax({
	        type : 'get',
	        url : 'MatchQuotation',
	        data : {
	        	QuoteID:QuoteID,
	        	OrderID:OrderID,
	        },
	        dataType : 'json',
	        success : function (data) {            
	             if(data != "null"){
	            	 $('.contract .contractPage tbody').html('');
	            	 for (var i = 1; i < data.length; i++) {
	                     var tr = '<tr>' +
	                         '<td style="display: none">' + data[i].ID + '</td>' +
                           '<td>' + data[i].OrderID + '</td>' +
	                         '<td>' + i + '</td>' +
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
	             }
	          
	        },
	        error : function () {
	            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    });
	}
});

$(document).on("click",".tab_href",function(){
	var url = window.location.href;
	if(url.indexOf("8080")!=-1){
		url = "http://localhost:8080/LogisticsFile/File/";
	}
	if(url.indexOf("8085")!=-1){
		url = "http://58.210.123.22:8085/LogisticsFile/File/";
	}
	var href = url+$(this).val();
	window.location.href = href;
});
//关联
// $('.contract_add input[name="customer_search"]').keyup(function () {
//     var model = $(this).val();
//     $('.contract_add select[name="customer_name"]').find('option').each(function () {
//         if($(this).val().indexOf(model)!=-1){
//             $(this).show();
//         } else{
//             $(this).hide();
//         }
//     });

//     $('.contract_add select[name="customer_name"]').show();

// });

$(document).on("keyup",'.contract_add input[name="customer_search"]',function(){
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
                        str+='<option value="'+newData[i].CustomerName+'" text="'+newData[i].ID+'" contact="'+newData[i].Contact+'" ContactInfo="'+newData[i].ContactInfo1+'">'+newData[i].CustomerName+'&nbsp;:&nbsp;'+newData[i].Contact+'</option>';
                    }
                }
                $(".contract_add select[name='customer_name']").empty().append(str);
                $('.contract_add select[name="customer_name"]').show();
            },
            error : function () {
                $.MsgBox_Unload.Alert("提示", "服务器繁忙，客户单位数据获取有误！");
            }
        });
    }
});

$(document).on("click", function () {
    $('.contract_add select[name="customer_name"]').hide();
    $('.contract_update select[name="customer_name"]').hide();
});

$(document).on("click", '.contract_add select[name="customer_name"] option', function () {
    $('.contract_add select[name="customer_name"]').hide();
    $('.contract_add input[name="customer_search"]').val($('.contract_add select[name="customer_name"]').val());
    $('.contract_add input[name="customer_search"]').attr("ID",$(this).attr("text"));
    var CustomerID = $(this).attr("text");
    $(".contract_add .customerID_span").text(CustomerID);
    var Contact = $(this).attr('contact');
    var ContactInfo = $(this).attr('ContactInfo');
    $(".contract_add input[name='contact']").val(Contact);
    $(".contract_add input[name='contact_info']").val(ContactInfo);
});

// $('.contract_update input[name="customer_search"]').keyup(function () {
//     var model = $(this).val();
//     $('.contract_update select[name="customer_name"]').find('option').each(function () {
//         if($(this).val().indexOf(model)!=-1){
//             $(this).show();
//         } else{
//             $(this).hide();
//         }
//     });
//     $('.contract_update select[name="customer_name"]').show();

// });
// 
$(document).on("keyup",'.contract_update input[name="customer_search"]',function(){
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
                        str+='<option value="'+newData[i].CustomerName+'" text="'+newData[i].ID+'" contact="'+newData[i].Contact+'" ContactInfo="'+newData[i].ContactInfo1+'">'+newData[i].CustomerName+'&nbsp;:&nbsp;'+newData[i].Contact+'</option>';
                    }
                }
                $(".contract_update select[name='customer_name']").empty().append(str);
                $('.contract_update select[name="customer_name"]').show();
            },
            error : function () {
                $.MsgBox_Unload.Alert("提示", "服务器繁忙，客户单位数据获取有误！");
            }
        });
    }
});

$(document).on("click", '.contract_update select[name="customer_name"] option', function () {
    $('.contract_update select[name="customer_name"]').hide();
    $('.contract_update input[name="customer_search"]').val($('.contract_update select[name="customer_name"]').val());
    $('.contract_update input[name="customer_search"]').attr("ID",$(this).attr("text"));
    var CustomerID = $(this).attr("text");
    $(".contract_update .customerID_span").text(CustomerID);
    var Contact = $(this).attr('contact');
    var ContactInfo = $(this).attr('ContactInfo');
    $(".contract_update input[name='contact']").val(Contact);
    $(".contract_update input[name='contact_info']").val(ContactInfo);
});

// 开票资料新需求

$("#invoice_open").on("click",function(){
  if(ContractObj.Number==undefined||ContractObj.Number==""||ContractObj.Number=="--"){
    $.MsgBox_Unload.Alert("提示","请先填写报价单号并提交");
    $("#invoice_topen_close").trigger("click");
    return;
  }
  var ID = ContractObj.ID;
  $(".canedit_td").each(function(){
    $(this).text("");
  });
  $(".addTrItem").each(function(){
    /*detach() 方法移除被选元素，包括所有文本和子节点。
    /*这个方法会保留 jQuery 对象中的匹配的元素，因而可以在将来再使用这些匹配的元素。
    /*detach() 会保留所有绑定的事件、附加的数据，这一点与 remove() 不同。*/
    $(this).remove();
  });
  $.ajax({
    type:"GET",
    url:"BillCharge",
    data:{
      ID:ID
    },
    success:function(data){
      data = JSON.parse(data);
      console.log(typeof data);
      if(data.length>1){
        // 备注
        var iStr1 = ContractObj.contractNo;
        $(".tr_09>.td_col8").text(iStr1);
        var emptyStr = 0;
        var invoiceDataObj = data[1];
        console.log("invoiceDataObj");
        console.log(invoiceDataObj);
        for(var ii in invoiceDataObj){
          if(ii=="Goods"){
            continue;
          }
          if(invoiceDataObj[ii]==""||invoiceDataObj[ii]=="--"){
            emptyStr++;
          }
        }
        /*上部与下部基本信息填充*/
        // 证明第一次填
        if(emptyStr==12){
        var Customer = ContractObj.Customer;
        var Contact = ContractObj.Contact;
          $.ajax({
            type:"GET",
            url:"TaxInfo",
            data:{
              Customer:Customer,
              Contact:Contact
            },
            success:function(res){
              res = JSON.parse(res);
              console.log("第一次填写，请求TaxInfo+get");
              console.log(typeof res);
              console.log(res);
              if(res.length>1){
                var emptyStr1 = 0;
                var customerDataObj = res[1];
                for(var iii in customerDataObj){
                  if(customerDataObj[iii]==""||customerDataObj[iii]=="--"){
                    emptyStr1++;
                  }
                }
                if(emptyStr1==10){
                  $.MsgBox_Unload.Alert("提示","请先去客户信息表完善开票信息");
                  $("#invoice_topen_close").trigger("click");
                  // setTimeout(function(){
                  //   window.open("Customer");
                  // },1500);
                  window.open("Customer");
                }else{
                  // 上部信息填充
                  for(var k in customerDataObj){
                    $("#invoice_tableopen tbody").find(".price_"+k).text(customerDataObj[k]);
                  }
                  // 快递地址
                  var istrw = "<span class='span_1' tabindex='0' hidefocus='true'>"+customerDataObj.InvoiceRecepter+"</span>&nbsp;&nbsp;<span class='span_2' tabindex='0' hidefocus='true'>"+customerDataObj.LinkTel+"</span><br><span class='span_3' tabindex='0' hidefocus='true'>"+customerDataObj.LinkAddress+"</span>";
                  $(".tr_010>.iaddress").html(istrw);
                }
              }else{
                $.MsgBox_Unload.Alert("提示","无此客户信息");
                $("#invoice_topen_close").trigger("click");
              }
            },
            error:function(){
              $.MsgBox_Unload.Alert("提示","网络繁忙，获取默认信息失败");
            }
          });
        }else{
          // 保存过了，回显
          for(var kk in invoiceDataObj){
            if(kk=="Goods"||kk=="SumOfQuantity"||kk=="SumOfTaxPrice"||kk=="InvoiceRecepter"||kk=="LinkTel"||kk=="LinkAddress"){
              continue;
            }
            $("#invoice_tableopen tbody").find(".price_"+kk).text(invoiceDataObj[kk]);
          }
          // 快递地址
          var istr2 = "<span class='span_1'>"+invoiceDataObj.InvoiceRecepter+"</span>&nbsp;&nbsp;<span class='span_2'>"+invoiceDataObj.LinkTel+"</span><br><span class='span_3'>"+invoiceDataObj.LinkAddress+"</span>";
          $(".tr_010>.iaddress").html(istr2);
          /*上部与下部基本信息填充 end*/
        }
        // Goods回显
        if(invoiceDataObj.Goods.length==1){
          // alert("goods长度是1");
          var order_id = ContractObj.ID;
          $.ajax({
            type: 'get',
            url: 'GetOrderInfo',
            data: {
                order_id: order_id
            },
            dataType: 'json',
            success:function(resp){
              console.log("resp");
              console.log(resp);
              console.log(typeof resp);
              var trItemStr = '';
              if(resp.length==1){
                $.MsgBox_Unload.Alert("提示","请先匹配报价单");
                $("#invoice_topen_close").trigger("click");
              }else if(resp.length>1){
                for(var ll=1;ll<resp.length;ll++){
                  var qw = globalDataHandle(resp[ll].Number,0);
                  var we = globalDataHandle(resp[ll].UnitPrice,0.00);
                  trItemStr+='<tr class="tr_07 addTrItem" value="'+resp[ll].ID+'">'+
                      '<td class="td_col1 canedit_td"></td>'+
                      '<td class="td_col1 canedit_td">'+resp[ll].EquipmentModel+'</td>'+
                      '<td class="td_col1 canedit_td"></td>'+
                      '<td class="td_col1 canedit_td">'+qw+'</td>'+
                      '<td class="td_col1 canedit_td">'+Number(we).toFixed(2)+'</td>'+
                      '<td class="td_col1 canedit_td"></td>'+
                      '<td class="td_col1 canedit_td"><select class="tax_rate_sel">'+
                      '<option value="0">请选择</option>'+
                      '<option value="16%">16%</option>'+
                      '<option value="6%">6%</option>'+
                      '</select></td>'+
                      '<td class="td_col1 canedit_td"></td>'+
                      '<td class="td_col1 canedit_td"></td>'+
                      '<td class="td_col1"><input type="button" class="eou-button eou-button-30 eou-button-w50 del_tr_item" value="删除"></td>'+
                    '</tr>';
                }
                $(".tr_06").after(trItemStr);
              }
            },
            error:function(){
              $.MsgBox_Unload.Alert("提示","网络繁忙，获取合同配置信息失败");
            },
            complete:function(XMLHttpRequest,textStatus){
              $("#invoice_tableopen .canedit_td").each(function(){
                $(this).attr("contenteditable","true");
              });
              calcPriceTax();
              calcTableItem();
            }
          });
        }else{
          // 填充Goods
          // alert("goods长度是2");
          var hasGoodsData = invoiceDataObj.Goods;
          var goodsStr = '';
          hasGoodsData.map(function(currentValue,index,arr){
            if(index==0){
              return;
            }else{
              var aa = globalDataHandle(currentValue.GoodsTaxName,"");
              var bb = globalDataHandle(currentValue.TypeSpecification,"");
              var cc = globalDataHandle(currentValue.MeasurementUnit,"");
              var dd = globalDataHandle(currentValue.Quantity,0);
              var ee = globalDataHandle(currentValue.UnitPrice,0.00);
              var ff = globalDataHandle(currentValue.SumOfMoney,0.00);
              var hh = globalDataHandle(currentValue.TaxRate,0);
              var iiiii = globalDataHandle(currentValue.TaxAmount,0.00);
              var jj = globalDataHandle(currentValue.TotalPriceTax,0.00);
              goodsStr+='<tr class="tr_07 addTrItem" value="'+currentValue.OrderInfoID+'">'+
                    '<td class="td_col1 canedit_td">'+aa+'</td>'+
                    '<td class="td_col1 canedit_td">'+bb+'</td>'+
                    '<td class="td_col1 canedit_td">'+cc+'</td>'+
                    '<td class="td_col1 canedit_td">'+dd+'</td>'+
                    '<td class="td_col1 canedit_td">'+ee+'</td>'+
                    '<td class="td_col1 canedit_td">'+ff+'</td>'+
                    '<td class="td_col1 canedit_td">'+((hh)*100).toFixed(0)+'%</td>'+
                    '<td class="td_col1 canedit_td">'+iiiii+'</td>'+
                    '<td class="td_col1 canedit_td">'+jj+'</td>'+
                    '<td class="td_col1"><input type="button" class="eou-button eou-button-30 eou-button-w50 del_tr_item" value="删除"></td>'+
                  '</tr>';
            }
          });
          $(".tr_06").after(goodsStr);
          calcTableItem();
        }

      }else{
        $.MsgBox_Unload.Alert("提示","获取开票委托单信息失败");
      }
    },
    error:function(){
      $.MsgBox_Unload.Alert("提示","网络繁忙!刷新重试");
    },
    complete:function(XMLHttpRequest,textStatus){
      $("#invoice_tableopen .canedit_td").each(function(){
        $(this).prop("contenteditable","true");
      });
    }
  });
  $(".invoice_topen").fadeIn(200);
  $(".invoice_topen_bg").fadeIn(200);
});

$(".invoice_topen_tit_r, #invoice_topen_close").on("click",function(){
  $(".invoice_topen_bg").fadeOut(200);
  $(".invoice_topen").fadeOut(200);
});

// 保存信息，弹出发票备注
$("#invoice_topen_submit").on("click",function(){
  var qtyIsEmpty = 0;
  var unitIsEmpty = 0;
  var priceTaxIsEmpty = 0;
  var taxRateIsEmpty = 0;
  $(".addTrItem").each(function(){
    if($(this).children("td").eq(3).text().trim()==""){
      qtyIsEmpty++;
    }
    if($(this).children("td").eq(4).text().trim()==""){
      unitIsEmpty++;
    }
    if($(this).children("td").eq(8).text().trim()==""){
      priceTaxIsEmpty++;
    }
    if($(this).children("td").eq(6).text()=="请选择16%6%"){
      taxRateIsEmpty++;
    }
  });
  if(qtyIsEmpty!=0){
    $.MsgBox_Unload.Alert("提示","有数量为空");
    return;
  }else if(unitIsEmpty!=0){
    $.MsgBox_Unload.Alert("提示","有单价为空");
    return;
  }else if(priceTaxIsEmpty!=0){
    $.MsgBox_Unload.Alert("提示","有价税合计为空");
    return;
  }else if(taxRateIsEmpty!=0){
    $.MsgBox_Unload.Alert("提示","有税率未选");
    return;
  }else {
    var InvoiceTitle = $(".price_InvoiceTitle").text().trim();
    var TaxPayerIdentityNO = $(".price_TaxPayerIdentityNO").text().trim();
    var RegisterAddress = $(".price_RegisterAddress").text().trim();
    var Telephone = $(".price_Telephone").text().trim();
    var DepositBank = $(".price_DepositBank").text().trim();
    var Account = $(".price_Account").text().trim();
    var InvoiceRecepter = $(".iaddress .span_1").text().trim();
    var LinkTel = $(".iaddress .span_2").text().trim();
    var LinkAddress = $(".iaddress .span_3").text().trim();
    var LinkZipCode = $(".price_LinkZipCode").text().trim();
    var ID = Number(ContractObj.ID);
    var SumOfQuantity = Number($(".tr_08").children("td").eq(3).text().trim());
    var SumOfTaxPrice = parseFloat($(".tr_08").children("td").eq(8).text().trim()).toFixed(2);
    var Goods = [];
    var Goods2 = [];
    $(".addTrItem").each(function(){
      var iitem = {};
      var iitem2 = {};
      iitem.OrderInfoID = $(this).attr("value");
      iitem.GoodsTaxName = $(this).children("td").eq(0).text().trim();
      iitem.MeasurementUnit = $(this).children("td").eq(2).text().trim();
      iitem.TypeSpecification = $(this).children("td").eq(1).text().trim();
      iitem.Quantity = Number($(this).children("td").eq(3).text().trim());
      iitem.UnitPrice = Number($(this).children("td").eq(4).text().trim()).toFixed(2);
      iitem.SumOfMoney = Number($(this).children("td").eq(5).text().trim()).toFixed(2);
      iitem.TaxRate = (parseFloat($(this).children("td").eq(6).text().trim())/100).toFixed(2);
      iitem.TaxAmount = Number($(this).children("td").eq(7).text().trim()).toFixed(2);
      iitem.TotalPriceTax = Number($(this).children("td").eq(8).text().trim()).toFixed(2);
      Goods.push(iitem);
      // 邮件用
      iitem2.OrderInfoID = $(this).attr("value");
      iitem2.GoodsTaxName = $(this).children("td").eq(0).text().trim();
      iitem2.MeasurementUnit = $(this).children("td").eq(2).text().trim();
      iitem2.TypeSpecification = $(this).children("td").eq(1).text().trim();
      iitem2.Quantity = Number($(this).children("td").eq(3).text().trim());
      iitem2.UnitPrice = Number($(this).children("td").eq(4).text().trim()).toFixed(2);
      iitem2.SumOfMoney = Number($(this).children("td").eq(5).text().trim()).toFixed(2);
      iitem2.TaxRate = parseFloat($(this).children("td").eq(6).text().trim())+"%";
      iitem2.TaxAmount = Number($(this).children("td").eq(7).text().trim()).toFixed(2);
      iitem2.TotalPriceTax = Number($(this).children("td").eq(8).text().trim()).toFixed(2);
      Goods2.push(iitem2);
    });
    Goods = JSON.stringify(Goods);
    Goods2 = JSON.stringify(Goods2);
    mailAjaxObj.InvoiceTitle = InvoiceTitle;
    mailAjaxObj.TaxPayerIdentityNO = TaxPayerIdentityNO;
    mailAjaxObj.RegisterAddress = RegisterAddress;
    mailAjaxObj.Telephone = Telephone;
    mailAjaxObj.DepositBank = DepositBank;
    mailAjaxObj.Account = Account;
    mailAjaxObj.InvoiceRecepter = InvoiceRecepter;
    mailAjaxObj.LinkAddress = LinkAddress;
    mailAjaxObj.LinkTel = LinkTel;
    mailAjaxObj.LinkZipCode = LinkZipCode;
    mailAjaxObj.SumOfQuantity = SumOfQuantity;
    mailAjaxObj.SumOfTaxPrice = SumOfTaxPrice;
    mailAjaxObj.Goods = Goods2;
    // ajax保存部分
    $.ajax({
      type: 'POST',
      url: 'BillCharge',
      data: {
          InvoiceTitle: InvoiceTitle,
          TaxPayerIdentityNO: TaxPayerIdentityNO,
          RegisterAddress: RegisterAddress,
          Telephone: Telephone,
          DepositBank: DepositBank,
          Account: Account,
          InvoiceRecepter: InvoiceRecepter,
          LinkAddress: LinkAddress,
          LinkTel: LinkTel,
          LinkZipCode: LinkZipCode,
          ID: ID,
          SumOfQuantity: SumOfQuantity,
          SumOfTaxPrice: SumOfTaxPrice,
          Goods: Goods
      },
      contentType:"application/x-www-form-urlencoded;charset=utf-8",
      dataType: 'text',
      success:function(data){
        console.log(data);
        console.log(typeof data);
        if(data=="保存成功"){
          $.MsgBox_Unload.Alert("提示","保存成功，请选择发票类型和填写备注");
          $(".invoice_topen_bg").fadeIn(200);
          $(".mail_select").fadeIn(200);
          $(".invoice_topen").fadeOut(200);
        }else if(data=="保存失败"){
          $.MsgBox_Unload.Alert("提示","保存失败！请检查");
        }
      },
      error:function(){
        $.MsgBox_Unload.Alert("提示","网络繁忙!");
      },
      complete:function(XMLHttpRequest,textStatus){
        // if(textStatus=="success"){
          
        // }
      }
    });
  }
});

$(".mail_select_tit_r").on("click",function(){
  $(".mail_select").fadeOut(200);
  $(".invoice_topen_bg").fadeOut(200);
});
// 弹出邮件模板
$(".mail_select_foot>input").on("click",function(){
  ContractObj.invoiceClassify = $("#mail_select_sel").val();
  ContractObj.invoiceRemark = $("#mail_select_inp").val()==""?"无":$("#mail_select_inp").val();
  $(".mail_select").fadeOut(200);
  $(".invoice_mail").fadeIn(200);
  var str1 = 'Eoulu：开票申请-';
  str1+=ContractObj.contractNo;
  str1+='-';
  str1+=ContractObj.contractName;
  $("#invoice_mail_subject").val(str1);
  var str2 = '晓亮姐，您好！\n';
  str2+='附件：开票资料-'+ContractObj.contractNo+'-'+ContractObj.contractName+'.rar，是整理的'+ContractObj.contractName+'的开票资料，请您查收。\n';
  str2+='此发票需开具'+ContractObj.invoiceClassify+'。\n';
  str2+='备注：'+ContractObj.invoiceRemark+'。\n';
  str2+='如有疑问请随时联系，谢谢晓亮姐！';
  $("#invoice_mail_content").val(str2);
});

$(function(){
  leftAlertResp(".invoice_topen");
  leftAlertResp(".invoice_mail");
  // globalRespTextarea(["#invoice_mail_content"],6.92);
  $("#invoice_tableopen .canedit_td").attr("contenteditable","true");
  var thisDate = globalGetToday();
  var dateArr = thisDate.split("-");
  var dateStr = dateArr[0]+"年"+dateArr[1]+"月"+dateArr[2]+"日";
  $(".tr_012 td:nth-child(1)").text(dateStr);
});

$(window).on("resize",function(){
  leftAlertResp(".invoice_topen");
  leftAlertResp(".invoice_mail");
  // globalRespTextarea(["#invoice_mail_content"],6.92);
});

// 邮件模板关闭
$(".invoice_mail_tit_r, #invoice_mail_close").on("click",function(){
  $(".invoice_mail").fadeOut(200);
  $(".invoice_topen_bg").fadeOut(200);
});

// 发送邮件
$("#invoice_mail_submit").on("click",function(){
  var str10 = 'Eoulu：开票申请-';
  str10+=ContractObj.contractNo;
  str10+='-';
  str10+=ContractObj.contractName;
  var str9 = '<div style="font-size:14px;font-family:Arial,Microsoft YaHei,STSong;color:#000"><span>晓亮姐，您好！</span><br><br>';
  str9+='<span style="line-height:15px">附件：开票资料-'+ContractObj.contractNo+'-'+ContractObj.contractName+'.rar，是整理的'+ContractObj.contractName+'的开票资料，请您查收。</span><br><br>';
  str9+='<span style="line-height:15px">此发票需开具'+ContractObj.invoiceClassify+'。</span><br><br>';
  str9+='<span style="line-height:15px">备注：'+ContractObj.invoiceRemark+'。</span><br><br>';
  str9+='<span style="line-height:15px">如有疑问请随时联系，谢谢晓亮姐！</span></div>';
  // $("#Price_footer").append(str9);
  mailAjaxObj.Subject = str10;
  mailAjaxObj.Content = str9;
  $.ajax({
    type: 'POST',
    url: 'SendApplicationforInvoice',
    data: mailAjaxObj,
    dataType: 'text',
    contentType:"application/x-www-form-urlencoded;charset=utf-8",
    beforeSend: function(XMLHttpRequest){
        $("#invoice_mail_submit").attr("disabled","disabled");
        $("#invoice_mail_submit").css({
          "background":"#dddddd",
          "color":"#808080",
          "border":"none",
          "box-shadow":"0 0 0 0 #f8fcfd",
          "cursor":"not-allowed"
        });
    },
    success:function(data){
      console.log(data);
      $.MsgBox_Unload.Alert("提示",data);
      if(data=="发送成功"){
        $("#invoice_mail_close").trigger("click");
      }
    },
    error:function(){
      $.MsgBox_Unload.Alert("提示","网络繁忙！");
    },
    complete: function(XMLHttpRequest, textStatus){
        $("#invoice_mail_submit").attr("disabled",false);
        $("#invoice_mail_submit").css({
          "background":"#00aeef",
          "color":"#fff",
          "border":"solid 1px #00aeef",
          "box-shadow":"1px 2px 5px 0 #00aeef",
          "cursor":"pointer"
        });
    },
  });
});

// 删除
$(document).on("click",".del_tr_item",function(e){
  e.stopPropagation();
  $(this).parent().parent().remove();
  calcTableItem();
});

// 计算函数
function calcTableItem(){
  var qtyNum = 0;
  var taxAndPrice = 0;
  $(".addTrItem").each(function(){
    qtyNum+=Number($(this).children("td").eq(3).text());
    taxAndPrice+=Number($(this).children("td").eq(8).text());
  });
  taxAndPrice = taxAndPrice.toFixed(2);
  $(".tr_08").children("td").eq(3).text(qtyNum);
  $(".tr_08").children("td").eq(8).text(taxAndPrice);
}

// 计算价税合计
function calcPriceTax(){
  $(".addTrItem").each(function(){
    var qty = Number($(this).children("td").eq(3).text().trim());
    var priceUnit = Number($(this).children("td").eq(4).text().trim());
    var sum = (qty*priceUnit).toFixed(2);
    $(this).children("td").eq(8).text(sum);
  });
}

// 税率选择
$(document).on("change",".tax_rate_sel",function(){
  var ival = $(this).val();
  if(ival == 0){
    return;
  }else{
    var that = $(this).parent();
    $(this).detach();
    that.html("").text(ival);
  }
});

// 绑定焦点移除事件
$(document).on("blur",".addTrItem td:nth-child(4)",function(){
  // if($(this).text().trim()=="")return;
  var curVal = Number($(this).text());
  var isInt = globalIsInteger3(curVal);
  if(!isInt){
    $(this).text("0");
    $(this).siblings("td:nth-child(9)").text("0.00");
  }else{
    calcPriceTax();
  }
  calcTableItem();
});

$(document).on("blur",".addTrItem td:nth-child(5)",function(){
  var curVal = Number($(this).text());
  if(isNaN(curVal)){
    $(this).text("0.00");
    $(this).siblings("td:nth-child(9)").text("0.00");
  }else{
    $(this).text(curVal.toFixed(2));
    calcPriceTax();
  }
  calcTableItem();
});

$(document).on("blur",".addTrItem td:nth-child(9)",function(){
  var curVal = Number($(this).text());
  if(isNaN(curVal)){
    $(this).text("0.00");
  }else{
    $(this).text(curVal.toFixed(2));
  }
  calcTableItem();
});

$(document).on("blur",".addTrItem td:nth-child(6)",function(){
  var curVal = Number($(this).text());
  if(isNaN(curVal)){
    $(this).text("0.00");
  }else{
    $(this).text(curVal.toFixed(2));
  }
});

$(document).on("blur",".addTrItem td:nth-child(8)",function(){
  var curVal = Number($(this).text());
  if(isNaN(curVal)){
    $(this).text("0.00");
  }else{
    $(this).text(curVal.toFixed(2));
  }
});
// 税率
$(document).on("click",".addTrItem td:nth-child(7)",function(e){
  e.stopPropagation();
  if($(this).text()=="6%"||$(this).text()=="16%"){
    $(this).text("");
    var str11 = '<select class="tax_rate_sel">'+
                    '<option value="0">请选择</option>'+
                    '<option value="16%">16%</option>'+
                    '<option value="6%">6%</option>'+
                    '</select>';
    $(this).html(str11);
  }
  // var curVal = Number($(this).text());
  // if(isNaN(curVal)){
  //   $(this).text("0.00");
  // }else{
  //   $(this).text(curVal.toFixed(2));
  // }
});

// 合计行
$(document).on("blur",".tr_08 td:nth-child(4)",function(){
  var curVal = Number($(this).text());
  var isInt = globalIsInteger3(curVal);
  if(!isInt){
    $.MsgBox_Unload.Alert("提示","不是整数！");
    $(this).text("0");
  }
});

$(document).on("blur",".tr_08 td:nth-child(9)",function(){
  var curVal = Number($(this).text());
  if(isNaN(curVal)){
    $(this).text("0.00");
  }else{
    $(this).text(curVal.toFixed(2));
  }
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

// 导出excel
$(".export_Excel").click(function(){
    $.ajax({
        type: "POST",
        url: "Price",
        success: function(data){
          window.open(data);
        },
        error: function(){
            $.MsgBox_Unload.Alert("服务器繁忙提示", "Excel下载失败！");
        }
    });
    // globalUrlDownloadFile("POST", "Price", "", {});
});