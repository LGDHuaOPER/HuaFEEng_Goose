/**
 * Created by eoulu on 2017/3/29.
 */
$('.cover-color').height($(document).height()-80);
var supplierName=$('#supplierName').val();
if(supplierName==''||supplierName==null||supplierName=='15'){
	$('#searchContent2').find('option').filter(function(){return $(this).text()=="";}).prop("selected",true);
}else {
	$('#searchContent2').find('option[value="'+supplierName+'"]').prop("selected",true);
}

//----------------------------------------搜索框----------------------------------------------

function Search() {
    $('#search').val('search');
    $('#top_text_from').submit();
}
function Cancel() {
    $('#search').val('cancel');
    $('input[name="model"]').val('');
    $('select[name="supplier"]').find('option').filter(function(){return $(this).text()=="";}).prop("selected",true);
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

//--------------------------------------------添加信息-----------------------------------------------
function AddContract() {
	 $('.info_add input[type="text"').val('');
	 $('.info_add textarea').val('');
	 $('.info_add select').each(function(){
	    $(this).find('option').filter(function(){return $(this).text()=="";}).prop("selected",true);
	  })
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
	var id = $('.info_add input[name="id"]').val();
	var module = $('.info_add input[name="module"]').val();
	var equipment_unit = $('.info_add input[name="equipment_unit"]').val();
	var delivery_time = $('.info_add input[name="delivery_time"]').val();
	var source_area = $('.info_add input[name="source_area"]').val();
	var item_code = $('.info_add input[name="item_code"]').val();
	var commodity_category = $('.info_add input[name="commodity_category"]').val();
	var suppiler = $('.info_add select[name="suppiler"]').val();
	var remarks = $('.info_add textarea[name="remarks"]').val();

	$.ajax({
		type: 'get',
		url: 'EquipmentOperate',
		data: {
			id: id,
			model: module,
			equipment_unit: equipment_unit,
			delivery_time: delivery_time,
			source_area: source_area,
			item_code: item_code,
			commodity_category: commodity_category,
			suppiler: suppiler,
			remarks: remarks,
			classify:'新增'
		},
		dataType: 'json',
		success: function (data) {
//			alert(data);
//			 data = eval("(" + data + ")");
			 $.MsgBox.Alert("提示", data);
//	        	if(data.message){
//	                $.MsgBox.Alert("提示", "添加成功！");
//	        	}else{
//	                $.MsgBox.Alert('提示', "添加失败！");
//	        	}		
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
	var tr = $(this).parent().parent();
	var info_update = $('.info_update');
	info_update.find('input[name="id"]').val($(this).attr('value'));
	info_update.find('input[name="module"]').val(tr.find('td').eq(2).html());
	info_update.find('textarea[name="remarks"]').val(tr.find('td').eq(3).html());
	info_update.find('input[name="equipment_unit"]').val(tr.find('td').eq(4).html());
	info_update.find('input[name="delivery_time"]').val(tr.find('td').eq(5).html());
	info_update.find('input[name="source_area"]').val(tr.find('td').eq(6).html());
	info_update.find('input[name="item_code"]').val(tr.find('td').eq(7).html());
	info_update.find('input[name="commodity_category"]').val(tr.find('td').eq(8).html());
	if(tr.find('td').eq(9).html()=='--'||tr.find('td').eq(9).html()==''){
		info_update.find('select[name="suppiler"]').find('option[text=""]').prop("selected", true);
	}else{
		info_update.find('select[name="suppiler"]').find('option[text="' + tr.find('td').eq(9).html() + '"]').prop("selected", true);
	}
	$('.cover-color').show();
	info_update.show();
	var id = $(this).attr('value');
	$('.info_update input[name="id"]').val(id);
});


$('#update_submit').click(function () {
	var id = $('.info_update input[name="id"]').val();
	var model = $('.info_update input[name="module"]').val();
	var equipment_unit = $('.info_update input[name="equipment_unit"]').val();
	var delivery_time = $('.info_update input[name="delivery_time"]').val();
	var source_area = $('.info_update input[name="source_area"]').val();
	var item_code = $('.info_update input[name="item_code"]').val();
	var commodity_category = $('.info_update input[name="commodity_category"]').val();
	var suppiler = $('.info_update select[name="suppiler"]').val();
	var remarks = $('.info_update textarea[name="remarks"]').val();
	
	$.ajax({
		type: 'get',
		url: 'EquipmentOperate',
		data: {
			id: id,
			model: model,
			equipment_unit: equipment_unit,
			delivery_time: delivery_time,
			source_area: source_area,
			item_code: item_code,
			commodity_category: commodity_category,
			supplier: suppiler,
			remarks: remarks,
			classify:'修改'
		},
		dataType: 'json',
		success: function (data) {
//			alert(data);
//			 data = eval("(" + data + ")");
			 $.MsgBox.Alert("提示", data);
//	         	if(data.message){
//	                 $.MsgBox.Alert("提示", "修改成功！");
//	         	}else{
//	                 $.MsgBox.Alert('提示', "修改失败！");
//	         		}      
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

