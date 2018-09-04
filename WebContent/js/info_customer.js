/**
 * Created by eoulu on 2017/3/29.
 */
$('.cover-color').height($(document).height()-80);

// ----------------------------------------搜索框----------------------------------------------
if($('input[name="selected"]:checked').val()=='singleSelect'){
    $('.select-content').css('margin-left','33%');
}else{
    $('.select-content').css('margin-left','23%');

}
function Check(selected) {
	 if (selected == "single") {
	        $('.select2').hide();
	        $('.select-content').css('margin-left','33%');
	    } else {
	        $('.select2').show();
	        $('.select-content').css('margin-left','23%');
	    }
}
function Search() {
    $('#search').val('search');
    $('#top_text_from').attr("action","GetCustomerInfo2");
    $('#top_text_from').submit();
}
function Cancel() {
    $('#search').val('cancel');
    $('input[name="Contect1"]').val('');
    $('input[name="Contect2"]').val('');
    $('#top_text_from').attr("action","Customer");
    $('#top_text_from').submit();
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

// --------------------------------------------添加信息-----------------------------------------------
function AddContract() {
	$('.info_add input[type="text"').val('');
    $('.cover-color').show();
    $('.info_add').show();
}
$('.infoAdd_close').click(function () {
    $('.cover-color').hide();
    $('.info_add').hide();
});
$('.info_add .search').mousedown(function () {
    $('.info_add select[name="module"]').show();

});
$('.info_add .search').change(function () {
    $('.info_add select[name="module"]').hide();

});

$(document).on("click", ".info_add select[name='module'] option", function () {
    $('.info_add select[name="module"]').hide();
    $('.info_add .search').val($('.info_add select[name="module"]').text());
});

$('#add_submit').click(function () {
    var area = $('.info_add input[name="area"]').val().trim();
    var areaHook = 0;
    for(var kk in ProvinceandCity){
        if(area == kk){
            areaHook = 1;
            break;
        }
    }
    if(areaHook == 0){
        $.MsgBox_Unload.Alert("省份填写错误提示及示例", "内蒙古自治区填内蒙古，香港特别行政区填香港");
        return false;
    }
    var id = $('.info_add input[name="id"]').val();
    var customer_name = $('.info_add input[name="customer_name"]').val();
    var EnglishName = $('.info_add input[name="EnglishName"]').val();
    /*var customer_classify = $('.info_add input[name="customer_classify"]').val();*/
    var customer_classify = "null";
    var contact = $('.info_add input[name="contact"]').val();
    var contact_info1 = $('.info_add input[name="contact_info1"]').val();
    var contact_info2 = $('.info_add input[name="contact_info2"]').val();
    var contact_address = $('.info_add input[name="contact_address"]').val();
    var zip_code = $('.info_add input[name="zip_code"]').val();
    var fax_number = $('.info_add input[name="fax_number"]').val();
    var email = $('.info_add input[name="email"]').val();
    /*var shorthand_coding = $('.info_add input[name="shorthand_coding"]').val();*/
    var shorthand_coding = "null";
    //新增的三项
    var Website = $('.info_add input[name="Website"]').val();
    var customer_department = $('.info_add input[name="customer_department"]').val();
    var Englishcustomer_department = $('.info_add input[name="Englishcustomer_department"]').val();
    var CustomerLevel = $('.info_add #CustomerLevel').val();
    var City = $('.info_add  input[name="City"]').val();
    $.ajax({
        type: 'post',
        url: 'CustomerOperate',
        data: {
            id: id,
            customer_name: customer_name,
            EnglishName: EnglishName,
            customer_classify: customer_classify,
            contact: contact,
            contact_info1: contact_info1,
            contact_info2: contact_info2,
            contact_address: contact_address,
            area: area,
            zip_code: zip_code,
            fax_number: fax_number,
            email: email,
            shorthand_coding: shorthand_coding,
            Website:Website,
            CustomerDepartment:customer_department,
            DepartmentEnglish:Englishcustomer_department,
            classify:'新增',
            CustomerLevel:CustomerLevel,
            City :City
        },
        dataType: 'json',
        success: function (data) {
        	console.log(data);
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
});
$('#add_cancel').click(function () {
    $('.cover-color').hide();
    $('.info_add').hide();
});

//-------------------------------------------修改-----------------------------------------------
$('.edit').click(function () {
    var tr = $(this).parent();
    var info_update = $('.info_update');
    info_update.find('input[name="id"]').val($(this).attr('value'));
    info_update.find('input[name="customer_name"]').val(tr.find('.CustomerName').html());
    info_update.find('input[name="EnglishName"]').val(tr.find('.EnglishName').html());
    info_update.find('input[name="customer_classify"]').val(tr.find('.CustomerClassify').html());
    info_update.find('input[name="contact"]').val(tr.find('.Contact').html());
    info_update.find('input[name="contact_info1"]').val(tr.find('.ContactInfo1').html());
    info_update.find('input[name="contact_info2"]').val(tr.find('.ContactInfo2').html());
    info_update.find('input[name="contact_address"]').val(tr.find('.ContactAddress').html());
    info_update.find('input[name="area"]').val(tr.find('.Area').html());
    info_update.find('input[name="zip_code"]').val(tr.find('.ZipCode').html());
    info_update.find('input[name="fax_number"]').val(tr.find('.FaxNumber').html());
    info_update.find('input[name="email"]').val(tr.find('.Email').html());
    info_update.find('input[name="shorthand_coding"]').val(tr.find('.ShorthandCoding').html());
    info_update.find('input[name="Website"]').val(tr.find('.Website').html());
    info_update.find('input[name="customer_department"]').val(tr.find('.CustomerDepartment').html());
    info_update.find('input[name="Englishcustomer_department"]').val(tr.find('.DepartmentEnglish').html());
    info_update.find('input[name="City"]').val(tr.find('.City').html());
    info_update.find('#CustomerLevel').val(tr.find('td.CustomerLevel').html());
    $('.cover-color').show();
    info_update.show();
    var id = $(this).attr('value');
    $('.info_update input[name="id"]').val(id);
});


$('#update_submit').click(function () {
    var area = $('.info_update input[name="area"]').val().trim();
    var areaHook = 0;
    for(var kk in ProvinceandCity){
        if(area == kk){
            areaHook = 1;
            break;
        }
    }
    if(areaHook == 0){
        $.MsgBox_Unload.Alert("省份填写错误提示及示例", "内蒙古自治区填内蒙古，香港特别行政区填香港");
        return false;
    }
    var id = $('.info_update input[name="id"]').val();
    var customer_name = $('.info_update input[name="customer_name"]').val();
    var EnglishName = $('.info_update input[name="EnglishName"]').val();
    /*var customer_classify = $('.info_update input[name="customer_classify"]').val();*/
    var customer_classify = "null";
    var contact = $('.info_update input[name="contact"]').val();
    var contact_info1 = $('.info_update input[name="contact_info1"]').val();
    var contact_info2 = $('.info_update input[name="contact_info2"]').val();
    var contact_address = $('.info_update input[name="contact_address"]').val();
    var zip_code = $('.info_update input[name="zip_code"]').val();
    var fax_number = $('.info_update input[name="fax_number"]').val();
    var email = $('.info_update input[name="email"]').val();
    /*var shorthand_coding = $('.info_update input[name="shorthand_coding"]').val();*/
    var shorthand_coding = "null";
    var Website = $('.info_update input[name="Website"]').val();
    var customer_department = $('.info_update input[name="customer_department"]').val();
    var Englishcustomer_department = $('.info_update input[name="Englishcustomer_department"]').val();
    var CustomerLevel = $('.info_update #CustomerLevel').val();
    var City = $('.info_update  input[name="City"]').val();
    $.ajax({
        type: 'post',
        url: 'CustomerOperate',
        data: {
            id: id,
            customer_name: customer_name,
            EnglishName:EnglishName,
            customer_classify: customer_classify,
            contact: contact,
            contact_info1: contact_info1,
            contact_info2: contact_info2,
            contact_address: contact_address,
            area: area,
            zip_code: zip_code,
            fax_number: fax_number,
            email: email,
            shorthand_coding: shorthand_coding,
            Website:Website,
            CustomerDepartment:customer_department,
            DepartmentEnglish:Englishcustomer_department,
            classify:'修改',
            CustomerLevel:CustomerLevel,
            City :City
        },
        dataType: 'json',
        success: function (data) {

        	console.log(data);
        	 data = eval("(" + data + ")");
        	console.log(data.message);
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
});
$('.update_close').click(function () {
    $('.cover-color').hide();
    $('.info_update').hide();
});
$('#update_cancel').click(function () {
    $('.cover-color').hide();
    $('.info_update').hide();
});

//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function () {
    window.location.reload();
});
var invoiceInfoObj = {};
var invoiceSaveObj = {};
// 开票表格内弹出
$(document).on("click",".invoiceInfo_td",function(){
    $(".cover-color").fadeIn(200);
    $(".invoice_tshow").fadeIn(200);
    $("#invoice_tableshow tbody tr td:nth-child(2)").each(function(){
        $(this).text("");
    });
    var ID = $(this).siblings("td.edit").attr("value");
    // 账号 开户行 发票收件人 开票抬头
    var Account="",DepositBank="",InvoiceRecepter,InvoiceTitle="";
    InvoiceRecepter = $(this).siblings("td.Contact").text();
    var LinkAddress = $(this).siblings("td.ContactAddress").text();
    var LinkTel = $(this).siblings("td.ContactInfo1").text();
    var LinkZipCode = "";
    // 注册地址
    var RegisterAddress = $(this).siblings("td.ContactAddress").text();
    var TaxPayerIdentityNO="";
    // 电话
    var Telephone = $(this).siblings("td.ContactInfo1").text();
    InvoiceRecepter = globalDataHandle(InvoiceRecepter,"");
    LinkAddress = globalDataHandle(LinkAddress,"");
    LinkTel = globalDataHandle(LinkTel,"");
    RegisterAddress = globalDataHandle(RegisterAddress,"");
    Telephone = globalDataHandle(Telephone,"");
    invoiceInfoObj = {};
    invoiceInfoObj.ID = ID;
    invoiceInfoObj.Account = Account;
    invoiceInfoObj.DepositBank = DepositBank;
    invoiceInfoObj.InvoiceRecepter = InvoiceRecepter;
    invoiceInfoObj.InvoiceTitle = InvoiceTitle;
    invoiceInfoObj.LinkAddress = LinkAddress;
    invoiceInfoObj.LinkTel = LinkTel;
    invoiceInfoObj.LinkZipCode = LinkZipCode;
    invoiceInfoObj.RegisterAddress = RegisterAddress;
    invoiceInfoObj.TaxPayerIdentityNO = TaxPayerIdentityNO;
    invoiceInfoObj.Telephone = Telephone;
    console.log(invoiceInfoObj);
    $.ajax({
        type:"GET",
        url:"TaxInfo",
        data:{
            ID:ID
        },
        dataType:"json",
        success:function(data){
            console.log(data);
            console.log(typeof data);
            if(data.length>1){
                for(var k in invoiceInfoObj){
                    if(data[1][k]==""||data[1][k]=="--"||data[1][k]==undefined||data[1][k]==null){
                        $("#invoice_tableshow tbody").find(".invo_"+k).text(invoiceInfoObj[k]);
                    }else{
                        $("#invoice_tableshow tbody").find(".invo_"+k).text(data[1][k]);
                    }
                }
            }else if(data.length==1){
                for(var k1 in invoiceInfoObj){
                    $("#invoice_tableshow tbody").find(".invo_"+k1).text(invoiceInfoObj[k1]);
                }
            }
        },
        error:function(){
            $.MsgBox_Unload.Alert("提示","网络繁忙，获取开票信息失败");
        }
    });
});

$(document).on("click",".invoice_tshow_tit_r, #invoice_tshow_close",function(){
    invoiceInfoObj = {};
    invoiceSaveObj = {};
    $(".invoice_tshow").fadeOut(200);
    $(".cover-color").fadeOut(200);
});

// 保存提交
$("#invoice_tshow_submit").on("click",function(){
    invoiceSaveObj.ID = invoiceInfoObj.ID;
    var InvoiceTitle = $(".invo_InvoiceTitle").text().trim();
    var TaxPayerIdentityNO = $(".invo_TaxPayerIdentityNO").text().trim();
    var RegisterAddress = $(".invo_RegisterAddress").text().trim();
    var Telephone = $(".invo_Telephone").text().trim();
    var DepositBank = $(".invo_DepositBank").text().trim();
    var Account = $(".invo_Account").text().trim();
    var InvoiceRecepter = $(".invo_InvoiceRecepter").text().trim();
    var LinkAddress = $(".invo_LinkAddress").text().trim();
    var LinkTel = $(".invo_LinkTel").text().trim();
    var LinkEmail = $(".invo_LinkZipCode").text().trim();
    invoiceSaveObj.InvoiceTitle = InvoiceTitle;
    invoiceSaveObj.TaxPayerIdentityNO = TaxPayerIdentityNO;
    invoiceSaveObj.RegisterAddress = RegisterAddress;
    invoiceSaveObj.Telephone = Telephone;
    invoiceSaveObj.DepositBank = DepositBank;
    invoiceSaveObj.Account = Account;
    invoiceSaveObj.InvoiceRecepter = InvoiceRecepter;
    invoiceSaveObj.LinkAddress = LinkAddress;
    invoiceSaveObj.LinkTel = LinkTel;
    invoiceSaveObj.LinkEmail = LinkEmail;
    $.ajax({
        type:"POST",
        url:"TaxInfo",
        data:invoiceSaveObj,
        dataType:"text",
        contentType:"application/x-www-form-urlencoded;charset=utf-8",
        success:function(data){
            $.MsgBox_Unload.Alert("提示",data);
        },
        error:function(){
            $.MsgBox_Unload.Alert("提示","网络繁忙!刷新重试");
        },
        complete:function(XMLHttpRequest,textStatus){
            if(textStatus=="success"){
                $("#invoice_tshow_close").trigger("click");
            }
        }
    });
});
// 文档加载完成
$(document).ready(function(){
    $("#invoice_tableshow tbody tr td:nth-child(2)").attr("contenteditable","true");
});