

$('.cover-color').height($(window).height());

// ----------------------------------------搜索框----------------------------------------------

function Search() {
    $('#search_box').submit();
}

// --------------------------------------------添加信息-----------------------------------------------
function AddContract() {
	$('.info_add input[type="text"').val('');
    $('.cover-color').show();
    $('.info_add').show();
}
$('.infoAdd_close').click(function () {
    $('.cover-color').hide();
    $('.info_add').hide();
    $('.info_update').hide();
});

$('#add_submit').click(function () {
    var PackageName = $('.info_add input[name="PackageName"]').val();
    var Model = $('.info_add input[name="Model"]').val();
    var Count = $('.info_add input[name="Count"]').val();
    var HourlyWage = $('.info_add input[name="HourlyWage"]').val();
    var PackageClassify = $('.info_add input[name="PackageClassify"]').val();
    var Cycle = $('.info_add input[name="Cycle"]').val();
    var Brand = $('.info_add input[name="Brand"]').val();
    var PremiumIndex = $('.info_add input[name="PremiumIndex"]').val();
    var MaintenanceIndex = $('.info_add input[name="MaintenanceIndex"]').val();
    var TransportAllowance = $('.info_add input[name="TransportAllowance"]').val();
    var AccommodationAllowance = $('.info_add input[name="AccommodationAllowance"]').val();
    var MissionAllowance = $('.info_add input[name="MissionAllowance"]').val();
    var Remarks = $('.info_add .Remarks').val();
    $.ajax({
        type: 'GET',
        url: 'SoftwareProductOperate',
        data: {
        	PackageName: PackageName,
        	Model: Model,
        	Count: Count,
        	HourlyWage: HourlyWage,
        	PackageClassify: PackageClassify,
        	Cycle: Cycle,
        	Brand: Brand,
        	PremiumIndex: PremiumIndex,
        	MaintenanceIndex: MaintenanceIndex,
        	TransportAllowance: TransportAllowance,
        	AccommodationAllowance: AccommodationAllowance,
        	MissionAllowance: MissionAllowance,
        	Remarks: Remarks,
        },
        dataType: 'json',
        success: function (data) {
        	console.log(data)
        	if(data){
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
$(document).on("click",".edit",function () {
    var tr = $(this).parent();
    var info_update = $('.info_update');
    info_update.find('input[name="id"]').val($(this).attr('value'));
    info_update.find('input[name="Model"]').val(tr.find('.Model').html());
    info_update.find('input[name="PackageName"]').val(tr.find('.PackageName').html());
    info_update.find('input[name="PackageClassify"]').val(tr.find('.PackageClassify').html());
    info_update.find('input[name="Brand"]').val(tr.find('.Brand').html());
    info_update.find('.Remarks').val(tr.find('.Remarks').html());
    info_update.find('input[name="Count"]').val(tr.find('.Count').html());
    info_update.find('input[name="HourlyWage"]').val(tr.find('.HourlyWage').html());
    info_update.find('input[name="Cycle"]').val(tr.find('.Cycle').html());
    info_update.find('input[name="PremiumIndex"]').val(tr.find('.PremiumIndex').html());
    info_update.find('input[name="MaintenanceIndex"]').val(tr.find('.MaintenanceIndex').html());
    info_update.find('input[name="TransportAllowance"]').val(tr.find('.TransportAllowance').html());
    info_update.find('input[name="AccommodationAllowance"]').val(tr.find('.AccommodationAllowance').html());
    info_update.find('input[name="MissionAllowance"]').val(tr.find('.MissionAllowance').html());
    $('.cover-color').show();
    info_update.show();
    var id = $(this).attr('value');
    $('.info_update .ProductMsgTitle').attr("ID",id);
});


$('#update_submit').click(function () {
    var id = $('.info_update .ProductMsgTitle').attr("ID");
    var PackageName = $('.info_update input[name="PackageName"]').val();
    var Model = $('.info_update input[name="Model"]').val();
    var Count = $('.info_update input[name="Count"]').val();
    var HourlyWage = $('.info_update input[name="HourlyWage"]').val();
    var PackageClassify = $('.info_update input[name="PackageClassify"]').val();
    var Cycle = $('.info_update input[name="Cycle"]').val();
    var Brand = $('.info_update input[name="Brand"]').val();
    var PremiumIndex = $('.info_update input[name="PremiumIndex"]').val();
    var MaintenanceIndex = $('.info_update input[name="MaintenanceIndex"]').val();
    var TransportAllowance = $('.info_update input[name="TransportAllowance"]').val();
    var AccommodationAllowance = $('.info_update input[name="AccommodationAllowance"]').val();
    var MissionAllowance = $('.info_update input[name="MissionAllowance"]').val();
    var Remarks = $('.info_update .Remarks').val();
    $.ajax({
        type: 'post',
        url: 'SoftwareProductOperate',
        data: {
            ID: id,
            PackageName: PackageName,
        	Model: Model,
        	Count: Count,
        	HourlyWage: HourlyWage,
        	PackageClassify: PackageClassify,
        	Cycle: Cycle,
        	Brand: Brand,
        	PremiumIndex: PremiumIndex,
        	MaintenanceIndex: MaintenanceIndex,
        	TransportAllowance: TransportAllowance,
        	AccommodationAllowance: AccommodationAllowance,
        	MissionAllowance: MissionAllowance,
        	Remarks: Remarks,
        },
        dataType: 'json',
        success: function (data) {
        	console.log(data)
        	if(data){
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
