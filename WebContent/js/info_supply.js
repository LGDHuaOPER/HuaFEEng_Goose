/**
 * Created by eoulu on 2017/3/29.
 */
var addSubmitObj = new Object();
addSubmitObj.id = null;
addSubmitObj.name = null;
addSubmitObj.classify = null;
addSubmitObj.Contact = null;
addSubmitObj.ContactInfo = null;
addSubmitObj.Address = null;
addSubmitObj.Email = null;
addSubmitObj.Product = null;
addSubmitObj.company = null;
addSubmitObj.account = null;
addSubmitObj.bank = null;
addSubmitObj.taxCode = null;
addSubmitObj.SWIFTCode = null;
var updateSubmitObj = new Object();
updateSubmitObj.id = null;
updateSubmitObj.name = null;
updateSubmitObj.classify = null;
updateSubmitObj.Contact = null;
updateSubmitObj.ContactInfo = null;
updateSubmitObj.Address = null;
updateSubmitObj.Email = null;
updateSubmitObj.Product = null;
updateSubmitObj.company = null;
updateSubmitObj.account = null;
updateSubmitObj.bank = null;
updateSubmitObj.taxCode = null;
updateSubmitObj.SWIFTCode = null;


$('.cover-color').height($(document).height()-80);
// ----------------------------------------搜索框----------------------------------------------
function Search() {
    $('#search').val('search');
    $('#top_text_from').submit();
}
function Cancel() {
    $('#search').val('cancel');
    $('input[name="searchContent1"]').val('');
    $('input[name="searchContent2"]').val('');
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
	$('.info_add input[type="text"]').val('');
    $('.cover-color').show();
    $('.info_add').show();
}
$('.infoAdd_close').click(function () {
    $('.cover-color').hide();
    $('.info_add').hide();
    for(var k in addSubmitObj){
        addSubmitObj[k] = null;
    }
});

$('#add_submit').click(function () {
    for(var kk in addSubmitObj){
        if(kk=="id"){
            addSubmitObj[kk] = "";
            continue;
        }
        if(kk=="classify"){
            addSubmitObj[kk] = "新增";
            continue;
        }
        addSubmitObj[kk] = $(".info_add").find(".info_"+kk).val();
    }
    // 表单验证
    for(var kkk in addSubmitObj){
        addSubmitObj[kkk] = globalDataHandle(addSubmitObj[kkk],"");
        if(kkk=="name"&&addSubmitObj[kkk]==""){
            $.MsgBox_Unload.Alert("提示","未填写供应商！");
            return false;
        }
    }
    $.ajax({
        type: 'get',
        url: 'SupplierOperate',
        data: addSubmitObj,
        dataType: 'text',
        success: function (data) {
            console.log(typeof data);
            if(data.indexOf("true")>-1){
                $.MsgBox.Alert("提示", "添加成功！");
            }else{
                $.MsgBox_Unload.Alert("提示", "添加失败！");
            }
        },
        error: function () {
            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});
$('#add_cancel').click(function () {
    $('.cover-color').hide();
    $('.info_add').hide();
    for(var k in addSubmitObj){
        addSubmitObj[k] = null;
    }
});

//-------------------------------------------修改-----------------------------------------------
$('.edit').click(function () {
    var that = $(this);
    var info_update = $('.info_update');
    // info_update.find('input[name="suppiler"]').val(tr.find('td').text());
    info_update.find("[class^='info_']").each(function(){
        var subClassName = $(this).attr("class").split(" ")[0].replace("info_","hastd_");
        var oldVal = that.siblings("."+subClassName).text();
        var newVal = globalDataHandle(oldVal,"");
        console.log(subClassName);
        console.log(oldVal);
        console.log(newVal);
        $(this).val(newVal);
    });
    $('.cover-color').show();
    info_update.show();
    var id = $(this).attr('value');
    updateSubmitObj.id = id;
    // $('.info_update input[name="id"]').val(id);
});


$('#update_submit').click(function () {
    for(var kk in updateSubmitObj){
        if(kk=="id"){
            continue;
        }
        if(kk=="classify"){
            updateSubmitObj[kk] = "修改";
            continue;
        }
        updateSubmitObj[kk] = $(".info_update").find(".info_"+kk).val();
    }
    // 表单验证
    for(var kkk in updateSubmitObj){
        updateSubmitObj[kkk] = globalDataHandle(updateSubmitObj[kkk],"");
        if(kkk=="name"&&updateSubmitObj[kkk]==""){
            $.MsgBox_Unload.Alert("提示","未填写供应商！");
            return false;
        }
    }
    $.ajax({
        type: 'post',
        url: 'SupplierOperate',
        data: updateSubmitObj,
        dataType: 'text',
        success: function (data) {
            console.log(typeof data);
            if(data.indexOf("true")>-1){
                $.MsgBox.Alert("提示", "修改成功！");
            }else{
                $.MsgBox_Unload.Alert("提示", "修改失败！");
            }
        },
        error: function () {
            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});
$('.update_close').click(function () {
    $('.cover-color').hide();
    $('.info_update').hide();
    for(var k in updateSubmitObj){
        updateSubmitObj[k] = null;
    }
});
$('#update_cancel').click(function () {
    $('.cover-color').hide();
    $('.info_update').hide();
    for(var k in updateSubmitObj){
        updateSubmitObj[k] = null;
    }
});

//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function () {
    window.location.reload();
});

// 显示银行信息
$(document).on("click",".showBankInfo i",function(){
    $(".cover_bankinfo").fadeIn(200);
    $(".bankinfo_div").fadeIn(200);
    var suppierName = $(this).parent().siblings(".hastd_name").text();
    var that = $(this).parent();
    if(suppierName=="--")suppierName="";
    $(".bankinfo_div_top_l").text(suppierName+"：银行信息");
    $(".bankinfo_div_body [class^='table_']").each(function(){
        var subClassName = $(this).attr("class").split(" ")[0].replace("table_","hastd_");
        var oldVal = that.siblings("."+subClassName).text();
        var newVal = globalDataHandle(oldVal,"");
        $(this).text(newVal);
    });
});

$(".bankinfo_div_top_r").on("click",function(){
    $(".cover_bankinfo").fadeOut(200);
    $(".bankinfo_div").fadeOut(200);
    $(".bankinfo_div_body [class^='table_']").text("");
});

// 搜索
$(".search_div .down_span").on("click",function(e){
    e.stopPropagation();
    $("#selectCon").show();
});

$("#selectCon option").on("click",function(e){
    e.stopPropagation();
    $(".search_Column").val($("#selectCon").val());
    $("#selectCon").hide();
});

$(document).on("click",function(){
    $("#selectCon").hide();
});

// 搜索提交
$(".search_submit").on("click",function(){
    var href1 = window.location.href.split("cfChicken8")[0]+"cfChicken8/Supplier";
    var Column1 = $(".search_Column").val();
    if(Column1==""||Column1=="请选择")return false;
    var Content1 = $(".search_Content").val();
    if(Content1==""||Content1==undefined)return false;
    window.location.href = href1+"?Column1="+Column1+"&Content1="+Content1;
});

$(".search_cancle").on("click",function(){
    var href1 = window.location.href.split("cfChicken8")[0]+"cfChicken8/Supplier";
    window.location.href = href1;
});