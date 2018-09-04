/**
 * Created by eoulu on 2017/3/29.
 */

$('.cover-color').height($(document).height()-80);


//--------------------------------------------报价信息-----------------------------------------------

$('.report-edit').click(function () {
       /* $.ajax({
            type: 'get',
            url: 'GetQuotationConf',
            data: {
                id: id
            },
            dataType: 'json',
            success: function (data) {
            	$('.contractPage tbody').html("");
                for(var i=1;i<data.length;i++){
                	  var tr='<tr>' +
                  	  '<td style="display: none">' + data[i].ID + '</td>' +
                      '<td>'+i+'</td>' +
                      '<td>'+data[i].Remarks+'</td>' +
                      '<td>'+data[i].Model+'</td>' +
                      '<td><input value=' + data[i].Counts + ' type="text" name="counts" style="width: 45px;" disabled="disabled"></td>' +
                      '<td><input value=' + data[i].UnitPrice + ' type="text" name="unit_price" style="width: 45px;" disabled="disabled"></td>' +
                      '<td>'+parseFloat(data[i].Counts)*parseFloat(data[i].UnitPrice)+'</td>' +
                      '</tr>';
                    $('.contractPage tbody').append(tr);
                }
                $('.cover-color').show();
                $('.report').show();
            },
            error: function () {
                $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
            }
        });*/
        var tr = $(this).parent().parent();
        var report_info = $('.report_info');
        report_info.find('input[name="id"]').val($(this).attr('value'));
        report_info.find('input[name="order_id"]').val($(this).attr('text'));
        report_info.find('input[name="Version"]').val(tr.find('td').eq(2).html());
        if(tr.find('td').eq(3).html()!=''){
        	report_info.find('select[name="QuotationType"]').find('option[value="' + tr.find('td').eq(3).html() + '"]').prop("selected", true);
        }
        report_info.find('input[name="QuotationDate"]').val(tr.find('td').eq(5).html());
        report_info.find('select[name="Customer"]').find('option:checked').prop("selected",false);
        report_info.find('select[name="Customer"]').find('option[value="' + tr.find('td').eq(6).html()+' : '+tr.find('td').eq(7).html()+ '"]').prop("selected", true);
        report_info.find('input[name="customer_search"]').val($('.report_info select[name="Customer"]').val()); 
        report_info.find('input[name="Contact"]').val(tr.find('td').eq(7).html());
        report_info.find('input[name="ContactInfo"]').val(tr.find('td').eq(8).html());
        report_info.find('input[name="fax_number"]').val(tr.find('td').eq(9).html());
        report_info.find('select[name="SalesRepresentative"]').find('option[text="' + tr.find('td').eq(10).html() + '"]').prop("selected", true);
        report_info.find('input[name="SalePhone"]').val(tr.find('td').eq(11).html());
        report_info.find('input[name="SaleEmail"]').val(tr.find('td').eq(12).html());
        report_info.find('input[name="LeadTime"]').val(tr.find('td').eq(13).html());
        report_info.find('select[name="Valid"]').find('option[value="' + tr.find('td').eq(14).html() + '"]').prop("selected", true);
        if(tr.find('td').eq(15).find('input').val()=='--'||tr.find('td').eq(15).find('input').val()==''){
        	report_info.find('select[name="paymentTerms"]').find('option[value=""]').prop("selected", true);
        }else{
        	report_info.find('select[name="paymentTerms"]').find('option[value="' + tr.find('td').eq(15).find('input').val() + '"]').prop("selected", true);
        }
        report_info.find('input[name="ExchangeRate"]').val(tr.find('td').eq(16).html());
        report_info.find('input[name="ChargeDuty"]').val(tr.find('td').eq(17).html());
        
       /*------------- 计算金额-------------*/
    	var total=0;
    	var rate=report_info.find('input[name="ExchangeRate"]').val();
    	var airport=1000;
        $('.applyCount tbody tr').each(function(){
        	$(this).find('td').eq(5).html(parseFloat($(this).find('td').eq(3).html()*parseFloat($(this).find('td').eq(4).html())));
        	total=total+parseFloat($(this).find('td').eq(5).html());
        })
        
        $('.totalCount tbody').find('tr').eq(0).find('td').eq(1).html(total);
        $('.totalCount tbody').find('tr').eq(1).find('td').eq(1).html(total*rate);
        $('.totalCount tbody').find('tr').eq(2).find('td').eq(1).html(total*rate+parseFloat(airport));
        
        $('.cover-color').show();
        $('.report').show();
});
$('.report_close').click(function () {
    $('.cover-color').hide();
    $('.report').hide();
});
$('#report_cancel').click(function () {
    $('.cover-color').hide();
    $('.report').hide();
});

// --------------------------------------------配置信息-----------------------------------------------

$('.contract-show').click(function () {
        var OrderID=$(this).attr("value");
        $('.contract #id').val(OrderID);
        $('.contract #type').val($(this).parent().parent().find('td').eq(3).html());
        if($(this).parent().parent().find('td').eq(15).find('input').val()=='--'){
            $('.contract #paymentTerms').val('');
        }else{
            $('.contract #paymentTerms').val($(this).parent().parent().find('td').eq(15).find('input').val());
        }
        $.ajax({
            type : 'get',
            url : 'GetQuotationConf',
            data : {
            	OrderID:OrderID
            },
            dataType : 'json',
            success : function (data) {
            	$('.contract .contractPage:nth-child(1) tbody').html("");
                for(var i=1;i<data.length;i++){
                    var tr='<tr>' +
                    	'<td style="display: none">' + data[i].ID + '</td>' +
                        '<td>'+i+'</td>' +
                    	'<td>'+data[i].Model+'</td>' +
                        '<td>'+data[i].Remarks+'</td>' +
                        '<td><input value=' + data[i].Number + ' type="text" name="counts" style="width: 45px;" disabled="disabled"></td>' +
                        '<td><input value=' + data[i].Price + ' type="text" name="unit_price" style="width: 45px;" disabled="disabled"></td>' +
                        '<td>'+parseFloat(data[i].Number)*parseFloat(data[i].Price)+'</td>' +
                        '<td> <input name="contract-update" value="修改" class="bToggle" type="button"  ><input name="update-save" value="保存" class="bToggle" type="button" style="display:none" ></td>' +
                        '</tr>';
                    $('.contract .contractPage:nth-child(1) tbody').append(tr);
                }
                $('.cover-color').show();
                $('.contract').show();
            },
            error : function () {
                 $.MsgBox.Alert("提示","服务器繁忙，稍后重试！");
            }
        });
});
$('.contract_close').click(function () {
    $('.cover-color').hide();
    $('.contract').hide();
});

//--------------------------------------------添加合同配置信息页面-----------------------------------------------

$('.apply_add_info input[name="equipment_search"]').keyup(function () {
    var model = $(this).val();
    $('.apply_add_info select[name="equipment_name"]').find('option').each(function () {
        if($(this).val().indexOf(model)!=-1){
            $(this).show();
        } else{
            $(this).hide();
        }
    });
    $('.apply_add_info select[name="equipment_name"]').show();

});

$(document).on("click", '.apply_add_info select[name="equipment_name"] option', function () {
    $('.apply_add_info select[name="equipment_name"]').hide();
    $('.apply_add_info input[name="equipment_search"]').val($('.apply_add_info select[name="equipment_name"]').val());
    $('.apply_add_info input[name="remarks"]').val($(this).attr('remarks'));
   
});
$('.apply_add_info input[name="counts"]').keyup(function(){
	var count=$('.apply_add_info input[name="counts"]').val();
	var price=$('.apply_add_info input[name="unit_price"]').val();
	if(count==''||count==null){
		count=0;
	}
	if(price==''||price==null){
		price=0;
	}
	var type = $('.contract #type').val();
    if(type.indexOf('人民币')!=-1){
    	$('.apply_add_info input[name="RMBQuotes"]').val(parseFloat(count)*parseFloat(price));
    	$('.apply_add_info input[name="USDQuotes"]').val(0);
    } else{
    	$('.apply_add_info input[name="RMBQuotes"]').val(0);
    	$('.apply_add_info input[name="USDQuotes"]').val(parseFloat(count)*parseFloat(price));
 
    }
});
$('.apply_add_info input[name="unit_price"]').keyup(function(){
	var count=$('.apply_add_info input[name="counts"]').val();
	var price=$('.apply_add_info input[name="unit_price"]').val();
	if(count==''||count==null){
		count=0;
	}
	if(price==''||price==null){
		price=0;
	}
	var type = $('.contract #type').val();
    if(type.indexOf('人民币')!=-1){
    	$('.apply_add_info input[name="RMBQuotes"]').val(parseFloat(count)*parseFloat(price));
    	$('.apply_add_info input[name="USDQuotes"]').val(0);
    } else{
    	$('.apply_add_info input[name="RMBQuotes"]').val(0);
    	$('.apply_add_info input[name="USDQuotes"]').val(parseFloat(count)*parseFloat(price));
 
    }
});
$('#contract_apply_add').click(function () {
    $('.apply_add_info').show();
    var type = $('.contract #type').val();
    if(type.indexOf('人民币')!=-1){
        $('.RMB').show();
        $('.USD').hide();
    } else{
    	$('.RMB').hide();
        $('.USD').show();
    }

});
$('#apply_add_cancel').click(function () {
    $('.apply_add_info').hide();
});
$('#apply_add_submit').click(function () {
    var EquipmentModel = $('.apply_add_info select[name="equipment_name"] option:checked').attr('text');
    var Number = $('.apply_add_info input[name="counts"]').val();
    var Price = $('.apply_add_info input[name="unit_price"]').val();
    var RMBQuotes=$('.apply_add_info input[name="RMBQuotes"]').val();
    var USDQuotes=$('.apply_add_info input[name="USDQuotes"]').val();
    var OrderID = $('.contract #id').val();
    var PaymentTerms = $('.contract #paymentTerms').val();
    $.ajax({
        type: 'get',
        url: 'QuotationConfOperate',
        data: {
        	OrderID: OrderID,
        	EquipmentModel: EquipmentModel,
        	Number: Number,
        	Price: Price,
        	RMBQuotes:RMBQuotes,
        	USDQuotes:USDQuotes,
        	PaymentTerms:PaymentTerms,
            classify:'新增'
        },
        dataType: 'json',
        success: function (data) {
        	data = eval("(" + data + ")");
        	if(data.message){
                $.MsgBox.Judge('提示', "添加成功！");
        		$.ajax({
                    type: 'get',
                    url: 'GetQuotationConf',
                    data: {
                    	OrderID: OrderID
                    },
                    dataType: 'json',
                    success: function (data) {
                    	$('.contract .contractPage:nth-child(1) tbody').html("");
                        for(var i=1;i<data.length;i++){
                        	  var tr='<tr>' +
                          	  '<td style="display: none">' + data[i].ID + '</td>' +
                              '<td>'+i+'</td>' +
                              '<td>'+data[i].Model+'</td>' +
                              '<td>'+data[i].Remarks+'</td>' +
                              '<td><input value=' + data[i].Number + ' type="text" name="counts" style="width: 45px;" disabled="disabled"></td>' +
                              '<td><input value=' + data[i].Price + ' type="text" name="unit_price" style="width: 45px;" disabled="disabled"></td>' +
                              '<td>'+parseFloat(data[i].Number)*parseFloat(data[i].Price)+'</td>' +
                              '<td> <input name="contract-update" value="修改" class="bToggle" type="button"  ><input name="update-save" value="保存" class="bToggle" type="button" style="display:none" ></td>' +
                              '</tr>';
                            $('.contract .contractPage:nth-child(1) tbody').append(tr);
                        }
                        $('.cover-color').show();
                        $('.contract').show();
                    },
                    error: function () {
                        $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
                    }
                });
       		 }else{
               $.MsgBox.Judge('提示', "添加失败！");
       		 }
        },
        error: function () {
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});
//--------------------------------------------修改合同配置信息页面-----------------------------------------------
$(document).on("click", "input[name='contract-update']", function () {
	$(this).parent().find('input[name="update-save"]').show()
	$(this).parent().parent().find('input[type="text"]').attr("disabled",false);
    $(this).hide();
});

//--------------------------------------------保存修改合同配置信息页面-----------------------------------------------
$(document).on("click", "input[name='update-save']", function () {
    var tr = $(this).parent().parent();
    var OrderID = $('.contract #id').val();
    var Number = tr.find('input[name="counts"]').val();
    var Price = tr.find('input[name="unit_price"]').val();
    var OrderInfoID=tr.find('td').eq(0).html();
    $.ajax({
        type: 'get',
        url: 'QuotationConfOperate',
        data: {
        	OrderID: OrderID,
        	OrderInfoID: OrderInfoID,
        	Number: Number,
        	Price: Price,
            classify:'修改'
        },
        dataType: 'json',
        success: function (data) {
        	data = eval("(" + data + ")");
        	if(data.message){
        		 $.ajax({
                     type: 'get',
                     url: 'GetQuotationConf',
                     data: {
                    	 OrderID: OrderID
                     },
                     dataType: 'json',
                     success: function (data) {
                     	$('.contract .contractPage:nth-child(1) tbody').html("");
                         for(var i=1;i<data.length;i++){
                         	  var tr='<tr>' +
                           	   '<td style="display: none">' + data[i].ID + '</td>' +
                               '<td>'+i+'</td>' +
                               '<td>'+data[i].Model+'</td>' +
                               '<td>'+data[i].Remarks+'</td>' +
                               '<td><input value=' + data[i].Number + ' type="text" name="counts" style="width: 45px;" disabled="disabled"></td>' +
                               '<td><input value=' + data[i].Price + ' type="text" name="unit_price" style="width: 45px;" disabled="disabled"></td>' +
                               '<td>'+parseFloat(data[i].Number)*parseFloat(data[i].Price)+'</td>' +
                               '<td> <input name="contract-update" value="修改" class="bToggle" type="button"  ><input name="update-save" value="保存" class="bToggle" type="button" style="display:none" ></td>' +
                               '</tr>';
                             $('.contract .contractPage:nth-child(1) tbody').append(tr);
                         }
                         $('.cover-color').show();
                         $('.contract').show();
                     },
                     error: function () {
                         $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
                     }
                 });
        		 }else{
                $.MsgBox.Alert('提示', "修改失败！");
        	}
           
        },
        error: function () {
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});

// --------------------------------------------添加信息-----------------------------------------------
function AddContract() {
	
	$('.info_add input[type="text"]:disabled').val('');
	$('.info_add input[type="date"]').val('');
	$('.info_add input[type="search"]').val('');
	$('.info_add select').each(function(){
		$(this).find('option:checked').prop("selected",false);
		$(this).find('option').filter(function(){return $(this).val()=="";}).prop("selected",true);
	});
	$('.info_add input[name="Version"]').val('1.0');
	$('.info_add input[name="ExchangeRate"]').val('6.9');
	$('.info_add input[name="ChargeDuty"]').val('1.17');
	
	var date=new Date();
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	var day = date.getDate();
	if (month < 10) {
	        month = "0" + month;
	}
	if (day < 10) {
	        day = "0" + day;
	}
	$(".info_add input[name='QuotationDate']").val(year+"-"+month+"-"+day);

    $('.cover-color').show();
    $('.info_add').show();
}
$('.infoAdd_close').click(function () {
    $('.cover-color').hide();
    $('.info_add').hide();
});
$('#add_cancel').click(function () {
    $('.cover-color').hide();
    $('.info_add').hide();
});

$('.info_add input[name="customer_search"]').keyup(function () {
    var model = $(this).val();
    $('.info_add select[name="Customer"]').find('option').each(function () {
        if($(this).val().indexOf(model)!=-1){
            $(this).show();
        } else{
            $(this).hide();
        }
    });
    $('.info_add select[name="Customer"]').show();

});
$('.info_add select[name="SalesRepresentative"]').change(function () {
    $('.info_add input[name="SalePhone"]').val($('.info_add select[name="SalesRepresentative"] option:selected').attr('Contact'));
    $('.info_add input[name="SaleEmail"]').val($('.info_add select[name="SalesRepresentative"] option:selected').attr('email'));

});
$(document).on("click", '.info_add select[name="Customer"] option', function () {
    $('.info_add select[name="Customer"]').hide();
    $('.info_add input[name="customer_search"]').val($('.info_add select[name="Customer"]').val());
    $('.info_add input[name="Contact"]').val($(this).attr('contact'));
    $('.info_add input[name="ContactInfo"]').val($(this).attr('contact_info'));
    $('.info_add input[name="fax_number"]').val($(this).attr('fax_number'));

});

$('#add_submit').click(function () {
    var Version = $('.info_add input[name="Version"]').val();
    var ContractType=$('.info_add select[name="QuotationType"] option:selected').val();
    var Customer=$('.info_add select[name="Customer"] option:selected').attr('CustomerName');
    var Contact = $('.info_add input[name="Contact"]').val();
    var ContactInfo = $('.info_add input[name="ContactInfo"]').val();
    var SalesRepresentative = $('.info_add select[name="SalesRepresentative"]').val();
    var Valid = $('.info_add select[name="Valid"]').val();
    var LeadTime = $('.info_add input[name="LeadTime"]').val();
    var PaymentTerms=$('.info_add select[name="paymentTerms"] option:checked').val();
    var ExchangeRate = $('.info_add input[name="ExchangeRate"]').val();
    var ChargeDuty = $('.info_add input[name="ChargeDuty"]').val();
    var QuotationDate = $('.info_add input[name="QuotationDate"]').val();
    var EmployeeNumber=$('.info_add select[name="SalesRepresentative"] option:checked').attr('EmployeeNumber');
    if(ExchangeRate==''){
    	$.MsgBox.Judge('提示', "汇率不能为空！");
    	if(ChargeDuty==''){
        	$.MsgBox.Judge('提示', "课税类别不能为空！");
    	}
    }else {
    	 $.ajax({
    	        type: 'get',
    	        url: 'QuotationOperate',
    	        data: {
    	        	Version: Version,
    	        	ContractType: ContractType,
    	        	Customer: Customer,
    	        	Contact: Contact,
    	        	ContactInfo: ContactInfo,
    	        	SalesRepresentative: SalesRepresentative,
    	        	Valid: Valid,
    	        	LeadTime: LeadTime,
    	        	PaymentTerms:PaymentTerms,
    	        	ExchangeRate: ExchangeRate,
    	        	ChargeDuty: ChargeDuty,
    	        	QuotationDate: QuotationDate,
    	        	EmployeeNumber: EmployeeNumber,
    	            classify:'新增'
    	        },
    	        dataType: 'json',
    	        success: function (data) {
    	        	data = eval("(" + data + ")");
    	        	if(data.message){
    	                $.MsgBox.Alert("提示", "添加成功！");
    	        	}else{
    	                $.MsgBox.Alert('提示', "添加失败！");
    	        	}
    	        },
    	        error: function () {
    	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
    	        }
    	    });
    }
  
});
//-------------------------------------修改------------------------------------------
$('.info_update input[name="customer_search"]').keyup(function () {
    var model = $(this).val();
    $('.info_update select[name="Customer"]').find('option').each(function () {
        if($(this).val().indexOf(model)!=-1){
            $(this).show();
        } else{
            $(this).hide();
        }
    });
    $('.info_update select[name="Customer"]').show();

});

$(document).on("click", '.info_update select[name="Customer"] option', function () {
    $('.info_update select[name="Customer"]').hide();
    $('.info_update input[name="customer_search"]').val($('.info_update select[name="Customer"]').val());
    $('.info_update input[name="Contact"]').val($(this).attr('contact'));
    $('.info_update input[name="ContactInfo"]').val($(this).attr('contact_info'));
    $('.info_update input[name="fax_number"]').val($(this).attr('fax_number'));

});
$('.info_update select[name="SalesRepresentative"]').change(function () {
    $('.info_update input[name="SalePhone"]').val($('.info_update select[name="SalesRepresentative"] option:selected').attr('Contact'));
    $('.info_update input[name="SaleEmail"]').val($('.info_update select[name="SalesRepresentative"] option:selected').attr('email'));

});
$('.edit').click(function () {
    var tr = $(this).parent().parent();
    var info_update = $('.info_update');
    info_update.find('input[name="id"]').val($(this).attr('value'));
    info_update.find('input[name="order_id"]').val($(this).attr('text'));
    info_update.find('input[name="Version"]').val(tr.find('td').eq(2).html());
    if(tr.find('td').eq(3).html()!=''){
        info_update.find('select[name="QuotationType"]').find('option[value="' + tr.find('td').eq(3).html() + '"]').prop("selected", true);
    }
    info_update.find('input[name="QuotationID"]').val(tr.find('td').eq(4).html());
    info_update.find('input[name="QuotationDate"]').val(tr.find('td').eq(5).html());
    info_update.find('select[name="Customer"]').find('option:checked').prop("selected",false);
    info_update.find('select[name="Customer"]').find('option[value="' + tr.find('td').eq(6).html()+' : '+tr.find('td').eq(7).html()+ '"]').prop("selected", true);
    info_update.find('input[name="customer_search"]').val($('.info_update select[name="Customer"]').val()); 
    info_update.find('input[name="Contact"]').val(tr.find('td').eq(7).html());
    info_update.find('input[name="ContactInfo"]').val(tr.find('td').eq(8).html());
    info_update.find('input[name="fax_number"]').val(tr.find('td').eq(9).html());
    info_update.find('select[name="SalesRepresentative"]').find('option[text="' + tr.find('td').eq(10).html() + '"]').prop("selected", true);
    info_update.find('input[name="SalePhone"]').val(tr.find('td').eq(11).html());
    info_update.find('input[name="SaleEmail"]').val(tr.find('td').eq(12).html());
    info_update.find('input[name="LeadTime"]').val(tr.find('td').eq(13).html());
    info_update.find('select[name="Valid"]').find('option[value="' + tr.find('td').eq(14).html() + '"]').prop("selected", true);
    if(tr.find('td').eq(15).find('input').val()=='--'||tr.find('td').eq(15).find('input').val()==''){
        info_update.find('select[name="paymentTerms"]').find('option[value=""]').prop("selected", true);
    }else{
        info_update.find('select[name="paymentTerms"]').find('option[value="' + tr.find('td').eq(15).find('input').val() + '"]').prop("selected", true);
    }
    info_update.find('input[name="ExchangeRate"]').val(tr.find('td').eq(16).html());
    info_update.find('input[name="ChargeDuty"]').val(tr.find('td').eq(17).html());
    $('.cover-color').show();
    info_update.show();
});


$('#update_submit').click(function () {
    var ID = $('.info_update input[name="id"]').val();
    var OrderID = $('.info_update input[name="order_id"]').val();
    var Version=$('.info_update input[name="Version"]').val();
    var ContractType = $('.info_update select[name="QuotationType"]').val();
    var QuotationID=$('.info_update input[name="QuotationID"]').val();
    var QuotationDate = $('.info_update input[name="QuotationDate"]').val();
    var Customer=$('.info_update select[name="Customer"] option:selected').attr('CustomerName');
    var Contact = $('.info_update input[name="Contact"]').val();
    var ContactInfo = $('.info_update input[name="ContactInfo"]').val();
    var fax_number = $('.info_update input[name="fax_number"]').val();
    var SalesRepresentative = $('.info_update select[name="SalesRepresentative"]').val();
    var LeadTime = $('.info_update input[name="LeadTime"]').val();
    var Valid = $('.info_update select[name="Valid"]').val();
    var PaymentTerms = $('.info_update select[name="paymentTerms"] option:checked').val();
    var ExchangeRate = $('.info_update input[name="ExchangeRate"]').val();
    var ChargeDuty = $('.info_update input[name="ChargeDuty"]').val();
    if(ExchangeRate==''){
    	$.MsgBox.Judge('提示', "汇率不能为空！");
    	if(ChargeDuty==''){
        	$.MsgBox.Judge('提示', "课税类别不能为空！");
    	}
    }else {
    	 $.ajax({
    	        type: 'post',
    	        url: 'QuotationOperate ',
    	        data: {
    	        	ID: ID,
    	        	OrderID: OrderID,
    	            Version: Version,
    	            ContractType: ContractType,
    	            QuotationID: QuotationID,
    	        	Customer: Customer,
    	        	Contact: Contact,
    	        	ContactInfo: ContactInfo,
    	        	SalesRepresentative: SalesRepresentative,
    	        	Valid: Valid,
    	        	LeadTime: LeadTime,
    	        	PaymentTerms:PaymentTerms,
    	        	ExchangeRate: ExchangeRate,
    	        	ChargeDuty: ChargeDuty,
    	        	QuotationDate: QuotationDate,
    	            classify:'修改'
    	        },
    	        dataType: 'json',
    	        success: function (data) {
    	        	data = eval("(" + data + ")");
    	        	if(data.message){
    	                $.MsgBox.Alert("提示", "修改成功！");
    	        	}else{
    	                $.MsgBox.Alert('提示', "修改失败！");
    	        	}
    	        },
    	        error: function () {
    	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
    	        }
    	    });
    }
   
});
$('.update_close').click(function () {
    $('.cover-color').hide();
    $('.info_update').hide();
});
$('#update_cancel').click(function () {
    $('.cover-color').hide();
    $('.info_update').hide();
});

//------------------------------隐藏列-------------------------------------
$('#fa-button1').bind('click',function(){
 $('#fa-button1').toggleClass('fa-minus-square');
 $('#table1 tr').find('td:eq(8)').toggle();
 $('#table1 tr').find('td:eq(9)').toggle();
});
$('#fa-button2').bind('click',function(){
 $('#fa-button2').toggleClass('fa-minus-square');
 $('#table1 tr').find('td:eq(11)').toggle();
 $('#table1 tr').find('td:eq(12)').toggle();
});

//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function () {
    window.location.reload();
});



