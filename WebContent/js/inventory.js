/**
 * Created by eoulu on 2017/3/29.
 */

/*进入页面验证本地存储*/
// storage.clear(); // 将localStorage的所有内容清除
// storage.removeItem("a"); // 删除某个键值对
// storage.key(i); // 使用key()方法，向其中出入索引即可获取对应的键
var InventoryAllCustomerInfo;
if(!window.localStorage){
    alert("浏览器不支持localstorage");
}else{
    //主逻辑业务
    var storage = window.localStorage;
    var inow = Date.now();
    var InventoryAllCustomerInfoStr = storage.getItem("InventoryAllCustomerInfo");
    if(InventoryAllCustomerInfoStr == undefined){
        // 第一次存
        getInventoryAllCustomerInfo(function(res){
            var iObj = {};
            iObj.expires = inow + 2*60*1000;
            iObj.data = _.drop(res);
            // _.cloneDeep(a)
            var iStr = JSON.stringify(iObj);
            // storage["InventoryAllCustomerInfo"] = iStr;
            storage.setItem("InventoryAllCustomerInfo", iStr);
            buildCustomerInfoList(iObj.data);
        });
    }else{
        var iexpires = JSON.parse(InventoryAllCustomerInfoStr).expires;
        if(iexpires < inow){
            // 已超期
            getInventoryAllCustomerInfo(function(res){
                var iObj = {};
                iObj.expires = inow + 2*60*1000;
                iObj.data = _.drop(res);
                // _.cloneDeep(a)
                iStr = JSON.stringify(iObj);
                // storage["InventoryAllCustomerInfo"] = iStr;
                storage.setItem("InventoryAllCustomerInfo", iStr);
                buildCustomerInfoList(iObj.data);
            });
        }else{
            // 未超期
            var InventoryAllCustomerInfoObj = JSON.parse(InventoryAllCustomerInfoStr);
            buildCustomerInfoList(InventoryAllCustomerInfoObj.data);
        }
    }
}
function getInventoryAllCustomerInfo(fn){
    $.ajax({
        type: 'GET',
        url: 'InventoryQuery',
        data: {
            classify: 'Customer',
            content: ""
        },
        dataType:'json'
    }).then(function(res){
        fn && fn(res);
    },function(){
        $.MsgBox_Unload.Alert("最新客户信息获取提示", "网络繁忙！信息获取失败");
    });
}
function buildCustomerInfoList(data){
    InventoryAllCustomerInfo = _.map(data, function(value, index, collection){
        var item = {};
        item.label = value.ID + ".." + value.CustomerName + ":" + value.Contact;
        item.value = value.CustomerName + ":" + value.Contact;
        return item;
    });
}

var warehouseMapObj = {
	"苏州":"Suzhou",
	"合肥":"Hefei",
	"厦门":"Xiamen",
	"成都":"Chengdu",
	"香港":"Xianggang",
    "深圳":"Shenzhen",
    "北京":"Beijing",
    "石家庄":"Shijiazhuang"
};

var WarehouseHasSelArr = ["苏州","合肥","厦门","成都","香港","深圳","北京","石家庄"];
var WarehouseHasSelStr = '<select class="WarehouseHasSel"><option value="请选择">请选择</option>';
WarehouseHasSelArr.map(function(currentValue,index,arr){
    WarehouseHasSelStr+='<option value="'+currentValue+'">'+currentValue+'</option>';
});
WarehouseHasSelStr+='</select>';

function getWarehouseEn(item){
	for(var k in warehouseMapObj){
		if(k==item){
			return warehouseMapObj[k];
		}
	}
}

function getWarehouseCn(item){
    for(var k in warehouseMapObj){
        if(warehouseMapObj[k]==item){
            return k;
        }
    }
}

// var inWarehouse;
// var outWarehouse;
// var reserveWarehouse;
// var inWarehouse2;
// var outWarehouse2;
// var reserveWarehouse2;

// 入库信息表格内容渲染
function renderInventoryIn(data){
    $('.inventory_in .inventory_inPage tbody').empty();
    var tr = '';
    for (var i = 1; i < data.length; i++) {
        if(data[i].SourceSign=='1'){
            tr += '<tr>' +
            '<td style="display: none">' + data[i].StoreID + '</td>' +
            '<td style="display: none">' + data[i].SourceSign + '</td>' +
            '<td><input value="' + data[i].OperateDate + '"  type="date" name="operating_time"  disabled="disabled"></td>' +
            '<td><input value="' + data[i].Quantity + '"  type="text" name="quantity" disabled="disabled" style="width:55px;"></td>' +
            '<td><input value="' + data[i].ContractNo + '"  type="text" name="contract_no"  disabled="disabled" style="width:130px;"></td>' +
            '<td><input value="' + data[i].POUSD + '"  type="text" name="POAmount"  disabled="disabled" style="width:85px;"></td>' +
            '<td><input value="' + data[i].PORMB + '"  type="text" name="RMBPOAmount"  disabled="disabled" style="width:85px;"></td>' +
            '<td><input value="' + data[i].OperationCode + '" type="text" name="operation_code" disabled="disabled" style="width:130px;"></td>' +
            '<td class="td_Warehouse_in" style="width:130px;">'+getWarehouseCn(data[i].Warehouse)+'</td>' +
            '<td> <input name="inventory_in-update" value="修改" class="bToggle" type="button" >'+
            '<input name="update-save" value="保存" class="bToggle" type="button" style="display:none" >' +
            '<input name="delete-save" value="删除" type="button" disabled="diasabled"></td>' +
            '</tr>';
        }else{
            tr += '<tr>' +
            '<td style="display: none">' + data[i].StoreID + '</td>' +
            '<td style="display: none">' + data[i].SourceSign + '</td>' +
            '<td><input value="' + data[i].OperateDate + '"  type="date" name="operating_time"  disabled="disabled" ></td>' +
            '<td><input value="' + data[i].Quantity + '"  type="text" name="quantity" disabled="disabled" style="width:55px;"></td>' +
            '<td><input value="' + data[i].ContractNo + '"  type="text" name="contract_no"  disabled="disabled" style="width:130px;"></td>' +
            '<td><input value="' + data[i].POUSD + '"  type="text" name="POAmount"  disabled="disabled" style="width:85px;"></td>' +
            '<td><input value="' + data[i].PORMB + '"  type="text" name="RMBPOAmount"  disabled="disabled" style="width:85px;"></td>' +
            '<td><input value="' + data[i].OperationCode + '" type="text" name="operation_code" disabled="disabled" style="width:130px;"></td>' +
            '<td class="td_Warehouse_in" style="width:130px;">'+getWarehouseCn(data[i].Warehouse)+'</td>' +
            '<td> <input name="inventory_in-update" value="修改" class="bToggle" type="button" >'+
            '<input name="update-save" value="保存" class="bToggle" type="button" style="display:none" >' +  
            '<input name="delete-save" value="删除" class="bToggle" type="button" ></td>' +
            '</tr>';
        }
    }
    $('.inventory_in .inventory_inPage tbody').append(tr);
}

// 出库信息表格渲染
function renderInventoryOut(data){
    $('.inventory_out .inventory_outPage tbody').empty();
    var tr = '';
    for (var i = 1; i < data.length; i++) {
        if(data[i].SourceSign=='1'){
            tr += '<tr>' +
            '<td style="display: none">' + data[i].OutID + '</td>' +
            '<td style="display: none">' + data[i].SourceSign + '</td>' +
            '<td><input value="' + data[i].OperateDate + '"  type="date" name="operating_time"  disabled="disabled"></td>' +
            '<td><input value="' + data[i].Quantity + '"  type="text" name="quantity" disabled="disabled" style="width:55px;"></td>' +
            '<td><input value="' + data[i].ContractNo + '"  type="text" name="contract_no"  disabled="disabled" style="width:130px;"></td>' +
            '<td><input value="' + data[i].ContractUSD + '"  type="text" name="USDQuotes"  disabled="disabled" style="width:85px;"></td>' +
            '<td><input value="' + data[i].ContractRMB + '"  type="text" name="RMBQuotes"  disabled="disabled" style="width:85px;"></td>' +
            '<td><input value="' + data[i].OperationCode + '" type="text" name="operation_code" disabled="disabled" style="width:130px;"></td>' +
            '<td class="td_Warehouse_out" style="width:130px;">'+getWarehouseCn(data[i].Warehouse)+'</td>' +
            '<td> <input name="inventory_out_update" value="修改" class="bToggle" type="button" >'+
            '<input name="update_out_save" value="保存" class="bToggle" type="button" style="display:none" >' +
            '<input name="delete_out_save" value="删除" type="button" disabled="diasabled"></td>' +
            '</tr>';
        }else{
            tr += '<tr>' +
            '<td style="display: none">' + data[i].OutID + '</td>' +
            '<td style="display: none">' + data[i].SourceSign + '</td>' +
            '<td><input value="' + data[i].OperateDate + '"  type="date" name="operating_time"  disabled="disabled"></td>' +
            '<td><input value="' + data[i].Quantity + '"  type="text" name="quantity" disabled="disabled" style="width:55px;"></td>' +
            '<td><input value="' + data[i].ContractNo + '"  type="text" name="contract_no"  disabled="disabled" style="width:130px;"></td>' +
            '<td><input value="' + data[i].ContractUSD + '"  type="text" name="USDQuotes"  disabled="disabled" style="width:85px;"></td>' +
            '<td><input value="' + data[i].ContractRMB + '"  type="text" name="RMBQuotes"  disabled="disabled" style="width:85px;"></td>' +
            '<td><input value="' + data[i].OperationCode + '" type="text" name="operation_code" disabled="disabled" style="width:130px;"></td>' +
            '<td class="td_Warehouse_out" style="width:130px;">'+getWarehouseCn(data[i].Warehouse)+'</td>' +
            '<td> <input name="inventory_out_update" value="修改" class="bToggle" type="button" >'+
            '<input name="update_out_save" value="保存" class="bToggle" type="button" style="display:none" >' +  
            '<input name="delete_out_save" value="删除" class="bToggle" type="button" ></td>' +
            '</tr>';
        }
    }
    $('.inventory_out .inventory_outPage tbody').append(tr);
}

// 预定信息表格渲染qqq
function renderReserve(data){
    var Str = '';
    for(var i = 1;i<data.length;i++){
        Str += '<tr class="reserve_trN">'+
        '<td class="xuhao yuding_td1" value="'+data[i].CustomerOrder+'">'+i+'</td>'+
        '<td class="yuding_td2 OrderTime_td"><input type="date" value="'+data[i].OrderTime+'" disabled></td>'+
        '<td class="reserve_customer yuding_td3">'+
            '<input type="text" name="reserveCustomer" class="reserveCustomer" value="'+data[i].Customer+'" disabled readonly>'+
        '</td>'+
        '<td class="yuding_td4 ContractNO_td">'+data[i].ContractNO+'</td>'+
        '<td class="reserve_num yuding_td5">'+data[i].OrderQuantity +'</td>'+
        '<td class="reserve_Warehouse yuding_td6">'+getWarehouseCn(data[i].Warehouse)+'</td>'+
        '<td class="yuding_td7 EstimatedShippingTime_td"><input type="date" value="'+data[i].EstimatedShippingTime+'" disabled></td>'+
        '<td class="yuding_td8"><span class="edit fa fa-pencil">修改</span><span class="delete fa fa-trash">删除</span></td>'+
        '</tr>' ;
    }
    $(".table_content").empty().append(Str);
    $(".table_content>.reserve_trN input.reserveCustomer").each(function(i, el){
        new Awesomplete(el, {
            list: InventoryAllCustomerInfo,
            minChars: 1,
            maxItems: 18,
            autoFirst: true
        });
    });
}

$('.cover-color').height($(document).height() - 80);
// ----------------------------------------搜索框----------------------------------------------
function Check(selected) {
    if (selected == "singleSelect") {
        $('.select2').hide();
        $('.select-content').css('margin-left','33%');
    } else {
        $('.select2').show();
        $('.select-content').css('margin-left','23%');
    }
}
function Search() {
	if($("#type1 option:selected").text() == "库存" && $(".time2").val()){
		var time2Date = $(".time2").val().replace("-","").replace("-","");
		if(time2Date <= 20170631){
			alert("时间需大于2017-06-31！");
		}
		else{
			$('#search').val('search');
		    $('#top_text_from').submit();
		}
	}
	else if($("#type1 option:selected").text() != "库存"){
		$('#search').val('search');
	    $('#top_text_from').submit();
	}
}
function Cancel() {
	window.location.href="Inventory";
}

function exportinventory(){
	//添加ajax请求
	$.ajax({
		type : 'post',
		url : 'ExportInventory',
		async : true,
		data : {
			
		},
		//dataType : 'json',
		success : function(data){
			window.location.href = data;
			$('.exportinventory').hide();
		},
		error : function(e){
			console.log(e);
		}
	});
}

// 翻页组件按钮逻辑
function pageStyle(currentPage,pageCounts){
    if(pageCounts == 1){
        $("#fistPage, #upPage, #nextPage, #lastPage, #Gotojump").prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
    }else if(currentPage == 1){
        $("#fistPage, #upPage").prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
        $("#lastPage, #nextPage, #Gotojump").prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }else if(currentPage == pageCounts){
        $("#lastPage, #nextPage").prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
        $("#fistPage, #upPage, #Gotojump").prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }else{
        $("#lastPage, #nextPage, #fistPage, #upPage, #Gotojump").prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }
}

function scanInit(){
    $("span#currentPage").text("1");
    $("span#allPage").text("1");
    pageStyle(Number($('#currentPage').text()),Number($('#allPage').text()));
    $("#searchContent1").val("");
    $("input#jumpNumber").val("");
}

/*event handler*/
$(".export_cancel").click(function(){
	$('.exportinventory').hide();
});

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

$('#type1').click(function(){
	if($(this).val().indexOf('时间')>=0){
		$('#searchContent1').hide();
		$('.select1 .time').show();
		$('.select1 .time2').hide();
	}else if($(this).val().indexOf('库存')>=0){
		$('#searchContent1').hide();
		$('.select1 .time').hide();
		$('.select1 .time2').show();
	}else{
		$('#searchContent1').show();
		$('.select1 .time').hide();
		$('.select1 .time2').hide();
	}	
});

$('#type2').click(function(){
	if($(this).val().indexOf('时间')>=0){
		$('#searchContent2').hide();
		$('.select2 .time').show();
	}else{
		$('#searchContent2').show();
		$('.select2 .time').hide();
	}	
});
//-------------------------------------------修改-----------------------------------------------

$('.inventory_update_close').click(function () {
    $('.cover-color').hide();
    $('.inventory_update').hide();
});

$('#update_submit').click(function () {
    var inventory_update = $('.inventory_update');
    var equipment_id = inventory_update.find('input[name="equipment_id"]').val();
    var initial_quantity = $('.inventory_update input[name="initial_quantity"]').val();
    $.ajax({
        type: 'post',
        url: 'ModifyInitialQuantity',
        data: {
            equipment_id: equipment_id,
            initial_quantity: initial_quantity
        },
        dataType: 'json',
        success: function (data) {
            $.MsgBox.Alert('提示', '修改成功');
            $('.cover-color').hide();
            $('.inventory_update').hide();
        },
        error: function () {
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});
$('#update_cancel').click(function () {
    $('.cover-color').hide();
    $('.inventory_update').hide();
});

// 入库信息查看
$(document).on("click",'.inventory_in_show',function () {
	// var curWarehouseAdd = $(this).parent().siblings(".tabWarehouseAddress").text();
	// if(curWarehouseAdd=="总仓库"||curWarehouseAdd==""||curWarehouseAdd=="请选择苏州合肥厦门成都香港"){
	// 	$.MsgBox_Unload.Alert("提示","请先切换仓库！");
	// 	return false;
	// }else{
	// 	inWarehouse = curWarehouseAdd;
	// 	inWarehouse2 = curWarehouseAdd;
	// }
    var equipment_id = $(this).attr("value");
    var Model=$(this).parent().parent().find('td').eq(2).html();
    $('.inventory_in input[name="equipment_id"]').val(equipment_id);
    $('.inventory_in input[name="Model"]').val(Model);
    // var Warehouse;
    // if(inWarehouse2==undefined||inWarehouse2==""){
    // 	Warehouse = "";
    // }else{
    // 	Warehouse = getWarehouseEn(inWarehouse2);
    // }
    $.ajax({
        type: 'get',
        url: 'InventoryQuery',
        data: {
        	InventoryID: equipment_id,
            Types:'1'
            // Warehouse: Warehouse
        },
        dataType: 'json',
        success: function (data) {
            renderInventoryIn(data);
            $('.cover-color').show();
            $('.inventory_in').show();
        },
        error: function () {
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });

});
// 入库信息关闭
$('.inventory_in .inventory_in_close').click(function () {
    $('.cover-color').hide();
    $('.inventory_in').hide();
    $('.reserve_box').hide();
    $('.in_add_info').hide();
    $(".in_add_info tbody input").val("");
    // inWarehouse = "";
    // inWarehouse2 = "";
});
$('.reserve_box .inventory_in_close').click(function () {
    $('.cover-color').hide();
    $('.inventory_in').hide();
    $('.reserve_box').hide();
    $('.in_add_info').hide();
    $(".in_add_info tbody input").val("");
    // reserveWarehouse = "";
    // reserveWarehouse2 = "";
});

// --------------------------------------------修改入库信息页面-----------------------------------------------
$(document).on("click", "input[name='inventory_in-update']", function () {
    $(this).parent().find('input[name="update-save"]').show();
    if($(this).parent().parent().find('td:nth-child(2)').html()=='1'){
        $(this).parent().parent().find('input[name="operation_code"]').attr("disabled", false);
    }else{
        $(this).parent().parent().find('input').attr("disabled", false);
    }
    $(this).hide();
});
//--------------------------------------------保存修改入库信息页面-----------------------------------------------
$(document).on("click", "input[name='update-save']", function () {
    var tr = $(this).parent().parent();
    var id = tr.find('td').eq(0).html();
    var operating_time = tr.find('input[name="operating_time"]').val();
    var quantity = tr.find('input[name="quantity"]').val();
    var contract_no = tr.find('input[name="contract_no"]').val();
    var operation_code = tr.find('input[name="operation_code"]').val();
    var equipment_id = $('.inventory_in input[name="equipment_id"]').val();
    var Model = $('.inventory_in input[name="Model"]').val();
    var ContractUSD = tr.find('input[name="POAmount"]').val();
    var ContractRMB = tr.find('input[name="RMBPOAmount"]').val();
    // var Warehouse;
    // if(inWarehouse2==undefined||inWarehouse2==""){
    // 	Warehouse = "";
    // }else{
    // 	Warehouse = getWarehouseEn(inWarehouse2);
    // }
    var Warehouse = tr.find('.td_Warehouse_in').text();
    var WarehouseFlag = 0;
    for(var kk in warehouseMapObj){
        if(kk==Warehouse){
            WarehouseFlag = 1;
            break;
        }
    }
    if(WarehouseFlag == 0){
        $.MsgBox_Unload.Alert("提示", "仓库地址修改错误！");
        return false;
    }
    var WarehouseEn = getWarehouseEn(Warehouse);
    $.ajax({
        type: 'get',
        url: 'InventoryInfoOperate',
        data: {
        	Types: 1,
        	Quantity: quantity,
        	ContractNo: contract_no,
        	OperationCode: operation_code,
            OperateDate: operating_time,
            POUSD:ContractUSD,
            PORMB:ContractRMB,
            ID: id,
            InventoryID: equipment_id,
            Warehouse: WarehouseEn
        },
        dataType: 'json',
        success: function (data) {
        	if(data){
        		$.MsgBox.Alert("提示", "修改保存成功！");
        	}else{
        		$.MsgBox.Alert("提示", "修改保存失败！");
        	}
          
        },
        error: function () {
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});
var inventoryToday = globalGetToday();
//--------------------------------------------添加入库信息页面-----------------------------------------------
$('.inventory_in input[name="inventory_in_add"]').click(function () {
    $('.in_add_info').show();
    $(".in_add_info input[name='operating_time']").val(inventoryToday);
});

$('.inventory_in input[name="add_cancel"]').click(function () {
    $('.in_add_info').hide();
});
// --------------------------------------------提交添加入库信息-----------------------------------------------
$(document).on("click", ".inventory_in input[name='add_submit']", function () {
    var equipment_id = $('.inventory_in input[name="equipment_id"]').val();
    var inventory_in = $('.in_add_info');
    var operating_time = inventory_in.find('input[name="operating_time"]').val();
    var quantity = inventory_in.find('input[name="quantity"]').val();
    var contract_no = inventory_in.find('input[name="contract_no"]').val();
    var operation_code = inventory_in.find('input[name="operation_code"]').val();
    var Model = $('.inventory_in input[name="Model"]').val();
    //PO（USD）和 PO（RMB） 
    var POUSD = inventory_in.find('input[name="POAmount"]').val();
    var PORMB = inventory_in.find('input[name="RMBPOAmount"]').val();
    var Warehouse = getWarehouseEn(inventory_in.find('input.ware_house_inp').val().trim());
    $.ajax({
        type: 'get',
        url: 'InventoryInfoOperate',
        data: {
        	InventoryID:equipment_id,
        	Types:'1',
        	Quantity:quantity,
        	ContractNo: contract_no,
        	OperationCode: operation_code,
        	OperateDate:operating_time,
        	POUSD:POUSD,
        	PORMB:PORMB,
        	Warehouse: Warehouse
        },
        dataType: 'json',
        success: function (data) {
        	if(data){
        		 $.ajax({
                     type: 'get',
                     url: 'InventoryQuery',
                     data: {
                     	InventoryID: equipment_id,
         	            Types:'1',
         	            Warehouse: Warehouse
                     },
                     dataType: 'json',
                     success: function (data) {
                        renderInventoryIn(data);
                         $('.cover-color').show();
                         $('.inventory_in').show();
                         $.MsgBox.Alert("提示", "添加成功！");
                     },
                     error: function () {
                         $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
                     }
                 });
        	}else{
        		 $.MsgBox.Alert("入库信息提示", "在数据库存储成功，在表格添加失败！");
        	}
        },
        error: function () {
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});

//--------------------------------------------删除入库信息-----------------------------------------------
$(document).on("click", "input[name='delete-save']", function () {
    var tr = $(this).parent().parent();
    var id = tr.find('td').eq(0).html();
    var equipment_id = $('.inventory_in input[name="equipment_id"]').val();
    var Model = $('.inventory_in input[name="Model"]').val();
    var Warehouse = tr.find('.td_Warehouse_in').text();
    var WarehouseFlag = 0;
    for(var kk in warehouseMapObj){
        if(kk==Warehouse){
            WarehouseFlag = 1;
            break;
        }
    }
    if(WarehouseFlag == 0){
        $.MsgBox_Unload.Alert("提示", "仓库地址修改错误！");
        return false;
    }
    var WarehouseEn = getWarehouseEn(Warehouse);
    $.ajax({
        type: 'get',
        url: 'InventoryInfoOperate',
        data: {
            ID: id,
            classify:'delete',
            Types:'1',
            InventoryID: equipment_id,
            Warehouse: WarehouseEn
        },
        dataType: 'json',
        success: function (data) {
            $.ajax({
                type: 'get',
                url: 'GetInventoryInfo',
                data: {
                	equipment_id: equipment_id,
                	Model: Model,
                	type:'in'
                },
                dataType: 'json',
                success: function (data) {
                    $.MsgBox.Alert("提示", "删除成功！");
                    tr.remove();
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
// 显示出库信息
$(document).on("click",'.inventory_out_show',function () {
	// var curWarehouseAdd = $(this).parent().siblings(".tabWarehouseAddress").text();
	// if(curWarehouseAdd=="总仓库"||curWarehouseAdd==""||curWarehouseAdd=="请选择苏州合肥厦门成都香港"){
	// 	$.MsgBox_Unload.Alert("提示","请先切换仓库！");
	// 	return false;
	// }else{
	// 	outWarehouse = curWarehouseAdd;
	// 	outWarehouse2 = curWarehouseAdd;
	// }
    var equipment_id = $(this).attr("value");
    var Model=$(this).parent().parent().find('td').eq(2).html();
    $('.inventory_out input[name="equipment_id"]').val(equipment_id);
    $('.inventory_out input[name="Model"]').val(Model);
    // var Warehouse;
    // if(outWarehouse2==undefined||outWarehouse2==""){
    // 	Warehouse = "";
    // }else{
    // 	Warehouse = getWarehouseEn(outWarehouse2);
    // }
    $.ajax({
        type: 'get',
        url: 'InventoryQuery',
        data: {
        	InventoryID: equipment_id,
            Types:'2'
            // Warehouse: Warehouse
        },
        dataType: 'json',
        success: function (data) {
            renderInventoryOut(data);
            $('.cover-color').show();
            $('.inventory_out').show();
        },
        error: function () {
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");

        }
    });

});
$('.inventory_out_close').click(function () {
    $('.cover-color, .inventory_out, .out_add_info').hide();
    $(".out_add_info tbody input").val("");
    // outWarehouse = "";
    // outWarehouse2 = "";
});

// --------------------------------------------修改出库信息页面-----------------------------------------------
$(document).on("click", "input[name='inventory_out_update']", function () {
    $(this).parent().find('input[name="update_out_save"]').show();
    if($(this).parent().parent().find('td:nth-child(2)').html()=='1'){
        $(this).parent().parent().find('input[name="operation_code"]').attr("disabled", false);
    }else{
        $(this).parent().parent().find('input').attr("disabled", false);
    }
    $(this).hide();
});
//--------------------------------------------保存修改出库信息页面-----------------------------------------------
$(document).on("click", "input[name='update_out_save']", function () {
    var tr = $(this).parent().parent();
    var id = tr.find('td').eq(0).html();
    var operating_time = tr.find('input[name="operating_time"]').val();
    var quantity = tr.find('input[name="quantity"]').val();
    var contract_no = tr.find('input[name="contract_no"]').val();
    var operation_code = tr.find('input[name="operation_code"]').val();
    var equipment_id = $('.inventory_out input[name="equipment_id"]').val();
    var Model=$('.inventory_out input[name="Model"]').val();
    var ContractUSD = tr.find('input[name="USDQuotes"]').val();
    var ContractRMB = tr.find('input[name="RMBQuotes"]').val();
    var Warehouse = tr.find('.td_Warehouse_out').text();
    var WarehouseFlag = 0;
    for(var kk in warehouseMapObj){
        if(kk==Warehouse){
            WarehouseFlag = 1;
            break;
        }
    }
    if(WarehouseFlag == 0){
        $.MsgBox_Unload.Alert("提示", "仓库地址修改错误！");
        return false;
    }
    var WarehouseEn = getWarehouseEn(Warehouse);
    $.ajax({
        type: 'get',
        url: 'InventoryInfoOperate',
        data: {
        	Types: 2,
        	Quantity: quantity,
        	ContractNo: contract_no,
        	OperationCode: operation_code,
            OperateDate: operating_time,
            ContractUSD:ContractUSD,
            ContractRMB:ContractRMB,
            ID: id,
            InventoryID: equipment_id,
            Warehouse: WarehouseEn
        },
        dataType: 'json',
        success: function (data) {
        	if(data){
        		$.MsgBox.Alert("提示", "修改成功！");
        	}else{
        		$.MsgBox.Alert("提示", "修改失败！");
        	}
        },
        error: function () {
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});

//--------------------------------------------添加出库信息页面-----------------------------------------------
$('.inventory_out input[name="inventory_out_add"]').click(function () {
    $('.out_add_info').show();
    $(".out_add_info input[name='operating_time']").val(inventoryToday);
});

$('.inventory_out input[name="add_cancel"]').click(function () {
    $('.out_add_info').hide();
});
// --------------------------------------------提交添加出库信息-----------------------------------------------
$(document).on("click", ".inventory_out input[name='add_submit']", function () {
    var equipment_id = $('.inventory_out input[name="equipment_id"]').val();
    var inventory_out = $('.out_add_info');
    var operating_time = inventory_out.find('input[name="operating_time"]').val();
    var quantity = inventory_out.find('input[name="quantity"]').val();
    var contract_no = inventory_out.find('input[name="contract_no"]').val();
    var operation_code = inventory_out.find('input[name="operation_code"]').val();
    var Model=$('.inventory_out input[name="Model"]').val();
  //PO（USD）和 PO（RMB） 
    var POUSD = inventory_out.find('input[name="USDQuotes"]').val();
    var PORMB = inventory_out.find('input[name="RMBQuotes"]').val();
    var Warehouse = getWarehouseEn(inventory_out.find('input.ware_house_inp').val().trim());
    $.ajax({
        type: 'get',
        url: 'InventoryInfoOperate',
        data: {
        	InventoryID:equipment_id,
        	Types:'2',
        	Quantity:quantity,
        	ContractNo: contract_no,
        	OperationCode: operation_code,
        	OperateDate:operating_time,
        	ContractUSD:POUSD,
        	ContractRMB:PORMB,
        	Warehouse: Warehouse
        },
        dataType: 'json',
        success: function (data) {
            $.ajax({
                type: 'get',
                url: 'InventoryQuery',
                data: {
                	InventoryID: equipment_id,
                    Types:'2',
                    Warehouse: Warehouse
                },
                dataType: 'json',
                success: function (data) {
                    renderInventoryOut(data);
                    $('.cover-color').show();
                    $('.inventory_out').show();
                    $.MsgBox.Alert("提示", "添加成功！");
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

//--------------------------------------------删除出库信息-----------------------------------------------
$(document).on("click", "input[name='delete_out_save']", function () {
    var tr = $(this).parent().parent();
    var id = tr.find('td').eq(0).html();
    var equipment_id = $('.inventory_out input[name="equipment_id"]').val();
    var Model=$('.inventory_out input[name="Model"]').val();
    // var Warehouse;
    // if(outWarehouse2==undefined||outWarehouse2==""){
    // 	Warehouse = "";
    // }else{
    // 	Warehouse = getWarehouseEn(outWarehouse2);
    // }
    var Warehouse = tr.find('.td_Warehouse_out').text();
    var WarehouseFlag = 0;
    for(var kk in warehouseMapObj){
        if(kk==Warehouse){
            WarehouseFlag = 1;
            break;
        }
    }
    if(WarehouseFlag == 0){
        $.MsgBox_Unload.Alert("提示", "仓库地址修改错误！");
        return false;
    }
    var WarehouseEn = getWarehouseEn(Warehouse);
    $.ajax({
        type: 'get',
        url: 'InventoryInfoOperate',
        data: {
            ID: id,
            classify:'delete',
            Types:'2',
            InventoryID: equipment_id,
            Warehouse: WarehouseEn
        },
        dataType: 'json',
        success: function (data) {
            $.ajax({
                type: 'get',
                url: 'GetInventoryInfo',
                data: {
                	equipment_id: equipment_id,
                	Model:Model,
                	type:'out'
                },
                dataType: 'json',
                success: function (data) {
                    $.MsgBox.Alert("提示", "删除成功！");
                    tr.remove();
                    $('.cover-color').show();
                    $('.inventory_in').show();
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
//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function () {
    window.location.reload();
});

// //点击关闭刷新页面
// $(document).on("click", ".inventory_in_close", function () {
//     window.location.reload();
// });
// //点击关闭刷新页面
// $(document).on("click", ".inventory_out_close", function () {
//     window.location.reload();
// });
$(document).on("click", ".reserve_close", function () {
    window.location.reload();
});
$(document).on("click", ".add_cancel", function () {
    $(".cover-color, .reserve_box").fadeOut(200);
});

//预定信息显示
$(document).on("click",".Reserve_info",function(){
	// var curWarehouseAdd = $(this).parent().siblings(".tabWarehouseAddress").text();
	// if(curWarehouseAdd=="总仓库"||curWarehouseAdd==""||curWarehouseAdd=="请选择苏州合肥厦门成都香港"){
	// 	$.MsgBox_Unload.Alert("提示","请先切换仓库！");
	// 	return false;
	// }else{
	// 	reserveWarehouse = curWarehouseAdd;
	// 	reserveWarehouse2 = curWarehouseAdd;
	// }
    $("span.reserve_add").show(); 
	var ID = $(this).parent().parent().find('.inventory-edit').attr('value');
	$('.reserve_box .inventory_title').attr('ID',ID);
	// var Warehouse;
	// if(reserveWarehouse2==undefined||reserveWarehouse2==""){
	// 	Warehouse = "";
	// }else{
	// 	Warehouse = getWarehouseEn(reserveWarehouse2);
	// }
	$.ajax({
		type: 'get',
		url: 'InventoryQuery',
		data:{
			classify: 'CustomerOrder',
			InventoryID : ID
			// Warehouse: Warehouse
		},
		dataType: 'JSON',
		success:function(data){
			renderReserve(data);
            $(".cover-color, .reserve_box").show();
		},
		error:function(){
			$.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
		}
	});
});
//添加一条qqq
$(".reserve_add").click(function(){
	var tr_num = $(".table_content").children().length;
	var str = '';
	str = '<tr class="reserve_trN">'+
	'<td class="xuhao yuding_td1" value="">'+(tr_num+1)+'</td>'+
    '<td class="yuding_td2 OrderTime_td"><input type="date"></td>'+
	'<td class="reserve_customer yuding_td3" contenteditable="true" value="">'+
		'<input type="text" name="reserveCustomer" value="" class="reserveCustomer">'+
	'</td>'+
    '<td class="yuding_td4 ContractNO_td" contenteditable="true"></td>'+
	'<td class="reserve_num yuding_td5" contenteditable="true"></td>'+
    '<td class="reserve_Warehouse yuding_td6">'+WarehouseHasSelStr+'</td>'+
    '<td class="yuding_td7 EstimatedShippingTime_td"><input type="date"></td>'+
	'<td class="yuding_td8"><span class="edit fa fa-pencil" style="display:none;">修改</span><span class="delete fa fa-trash">删除</span></td>'+
	'</tr>' ;
	$(".table_content").append(str);
    $(this).hide();
    // $(".table_content>.reserve_trN:last").each(function(i, el){
    //     new Awesomplete(el, {
    //         list: PaymentRequestState.allMail,
    //         minChars: 2,
    //         maxItems: 12,
    //         autoFirst: true
    //     });
    // });
    new Awesomplete($(".table_content>.reserve_trN:last input.reserveCustomer").get(0), {
        list: InventoryAllCustomerInfo,
        minChars: 1,
        maxItems: 18,
        autoFirst: true
    });
});

// //添加中客户搜索
// $(document).on("input propertychange",".reserveCustomer",function(){
// 	var content = $(this).val();
// 	var $this = $(this);
// 	$.ajax({
// 		type: 'get',
// 		url: 'InventoryQuery',
// 		data: {
// 			classify: 'Customer',
// 			content: content
// 		},
// 		dataType: 'JSON',
// 		success: function(data){
// 			console.log(data[1]);
// 			console.log(data.length);
// 			var str = "";
//             for(var i = 1 ; i < data.length; i++){
//            	 str += '<li ID="'+data[i].ID+'" class="customerListli" Contact="'+data[i].Contact+'" ContactInfo1="'+data[i].ContactInfo1+'";>'+data[i].CustomerName +' : '+ data[i].Contact+'</li>';
//             }
//           $this.next(".customerList").empty().append(str).show();
// 		},
// 		error:function(){
// 			$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
// 		}
// 	});
// });
// $(document).on("click",function(){
// 	$(".customerList").hide();
// });
// $(document).on("click",".customerListli",function(){
// 	var currentCont = $(this).text();
// 	var ID= $(this).attr("ID");
// 	$(this).parent().prev().val(currentCont);
// 	$(this).parent().prev().attr("CustomerID",ID);
// 	$(".customerList").hide();
// });

//预定信息修改qqq
$(document).on("click",".edit",function(){
    var $parents = $(this).parent().parent();
    if($(this).text()=="修改"){
        $parents.find(".reserve_customer .reserveCustomer").attr({"disabled": false, "readonly": false});
        $parents.find("input[type='date']").attr({"disabled": false});
        $parents.find(".reserve_num, .yuding_td4").attr("contenteditable",true);
        $(this).text("保存");
    }else if($(this).text()=="保存"){
        var InventoryID = $('.reserve_box .inventory_title').attr('ID');
        // var CustomerID = $parents.find(".reserveCustomer").attr("customerid");
        // if(!CustomerID){
        //     $.MsgBox_Unload.Alert("新添加预定信息提示","未选择客户名称！");
        //     return false;
        // }
        var Customer = $parents.find("input.reserveCustomer").val().trim();
        if(Customer === "" || Customer == "--"){
            $.MsgBox_Unload.Alert("修改预定信息提示","未选择或填写客户名称！");
            return false;
        }
        var OrderTime = $parents.find(".OrderTime_td>input").val();
        if(OrderTime == ""){
            $.MsgBox_Unload.Alert("修改预定信息提示","未填写预定时间！");
            return false;
        }
        var EstimatedShippingTime = $parents.find(".EstimatedShippingTime_td>input").val();
        if(EstimatedShippingTime == ""){
            $.MsgBox_Unload.Alert("修改预定信息提示","未填写预计下单时间！");
            return false;
        }
        var ContractNO = $parents.find(".ContractNO_td").text().trim();

        var OrderQuantity = $parents.find(".reserve_num").text();
        var ID = $parents.find(".xuhao").attr("value");
        var Warehouse = $parents.find(".reserve_Warehouse").text();
        if(Warehouse=="" || Warehouse.indexOf("请选择")>-1 || Warehouse=="--"){
            $.MsgBox_Unload.Alert("修改预定信息提示","未选择仓库地址！");
            return false;
        }
        var WarehouseEn = getWarehouseEn(Warehouse);
        $.ajax({
            type:'POST',
            url:'InventoryInfoOperate',
            data:{
                InventoryID: InventoryID,
                // CustomerID: CustomerID,
                Customer: Customer,
                OrderTime: OrderTime,
                EstimatedShippingTime: EstimatedShippingTime,
                ContractNO: ContractNO,
                OrderQuantity: OrderQuantity,
                ID: ID,
                Warehouse: WarehouseEn
            },
            dataType:'json',
            success:function(data){
                if(data){
                    $.MsgBox.Alert("提示", "提交成功！");
                }else{
                    $.MsgBox.Alert("提示", "提交失败！");
                }
            },
            error:function(){
                 $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
            } 
        });
    }
});
//预定信息删除qqq
$(document).on("click",".delete",function(){
    if(!$(this).parent().siblings(".xuhao").attr("value")){
        $("span.reserve_add").show();
    }else{
        if($(this).siblings("span.edit").text()=="保存"){
            return false;
        }
        var InventoryID = $('.reserve_box .inventory_title').attr('ID');
        var OrderQuantity = $(this).parent().parent().find(".reserve_num").text();
        var ID = $(this).parent().parent().find(".xuhao").attr("value");
        var Warehouse = $(this).parent().parent().find(".reserve_Warehouse").text();
        if(Warehouse=="" || Warehouse.indexOf("请选择")>-1 || Warehouse=="--"){
            $.MsgBox_Unload.Alert("删除预定信息提示","未选择仓库地址！");
            return false;
        }
        var WarehouseEn = getWarehouseEn(Warehouse);
        $.ajax({
            type:'POST',
            url:'InventoryInfoOperate',
            data:{
                classify: "delete",
                InventoryID: InventoryID,
                // CustomerID: CustomerID,
                OrderQuantity: OrderQuantity,
                ID: ID,
                Warehouse: WarehouseEn
            },
            dataType:'json',
            success:function(data){
                if(data){
                    $.MsgBox.Alert("提示", "删除成功！");
                }else{
                    $.MsgBox.Alert("提示", "删除失败！");
                }
            },
            error:function(){
                 $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
            } 
        });
    }
	$(this).parent().parent().remove();
	var tr_num2 = $(".table_content").children().length;
	for(var i = 0;i<tr_num2;i++){
		$(".table_content").find(".xuhao").eq(i).text(i+1);
	}
});
//预定信息提交qqq
$(".reserve_box .add_submit").click(function(){
    var addCurTr = $(".table_content .reserve_trN:last");
    if(addCurTr.find(".xuhao").attr("value")){
        $.MsgBox_Unload.Alert("提交提示","请添加一条新的再提交！");
        return false;
    }
    var InventoryID = $('.reserve_box .inventory_title').attr('ID');
    // var CustomerID = addCurTr.find(".reserveCustomer").attr("customerid");
    // if(!CustomerID){
    //     $.MsgBox_Unload.Alert("新添加预定信息提示","未选择客户名称！");
    //     return false;
    // }
    var Customer = addCurTr.find("input.reserveCustomer").val().trim();
    if(Customer === "" || Customer == "--"){
        $.MsgBox_Unload.Alert("新添加预定信息提示","未选择或填写客户名称！");
        return false;
    }
    var OrderTime = addCurTr.find(".OrderTime_td>input").val();
    if(OrderTime == ""){
        $.MsgBox_Unload.Alert("新添加预定信息提示","未填写预定时间！");
        return false;
    }
    var EstimatedShippingTime = addCurTr.find(".EstimatedShippingTime_td>input").val();
    if(EstimatedShippingTime == ""){
        $.MsgBox_Unload.Alert("新添加预定信息提示","未填写预计下单时间！");
        return false;
    }
    var ContractNO = addCurTr.find(".ContractNO_td").text().trim();
    var OrderQuantity = addCurTr.find(".reserve_num").text();
    var ID = "0";
    var Warehouse = addCurTr.find(".reserve_Warehouse").text();
    if(Warehouse=="" || Warehouse.indexOf("请选择")>-1 || Warehouse=="--"){
        $.MsgBox_Unload.Alert("新添加预定信息提示","未选择仓库地址！");
        return false;
    }
    var WarehouseEn = getWarehouseEn(Warehouse);
    $.ajax({
        type:'POST',
        url:'InventoryInfoOperate',
        data:{
            InventoryID: InventoryID,
            // CustomerID: CustomerID,
            Customer: Customer,
            OrderTime: OrderTime,
            EstimatedShippingTime: EstimatedShippingTime,
            ContractNO: ContractNO,
            OrderQuantity: OrderQuantity,
            ID: ID,
            Warehouse: WarehouseEn
        },
        dataType:'json',
        success:function(data){
            if(data){
                $.MsgBox.Alert("提示", "提交成功！");
            }else{
                $.MsgBox.Alert("提示", "提交失败！");
            }
        },
        error:function(){
             $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        } 
    });
    /*
	var InventoryID = $('.reserve_box .inventory_title').attr('ID');
	var CustomerIDArr = [];
	var OrderQuantityArr = [];
	var idsArr = [];
    var WarehouseArr = [];
    var WarehouseFlag = 0;
	for(var i = 0 ;i<$(".table_content tr").length;i++){
		$(".table_content .xunhao").val();
	   var CustomerID= $(".table_content tr").eq(i).find("td.reserve_customer .reserveCustomer").attr("CustomerID");
	   var OrderQuantity= $(".table_content tr").eq(i).find("td.reserve_num").text();
	   if(!OrderQuantity){
		   OrderQuantity = '0';
	   }
		//ID分为新添加和修改
	   var id = $(".table_content tr").eq(i).find("td.xuhao").attr("value");
	   if(!id){
		  id = '0'; 
	   }
		CustomerIDArr.push(CustomerID);
		OrderQuantityArr.push(OrderQuantity);
		idsArr.push(id);
        var WarehouseInText = $(".table_content tr").eq(i).find("td.reserve_Warehouse").text();
        if(WarehouseInText=="" || WarehouseInText.indexOf("请选择")>-1 || WarehouseInText=="--"){
            WarehouseFlag = 1;
            WarehouseArr.push("");
        }else{
            WarehouseArr.push(getWarehouseEn(WarehouseInText));
        }
	}
	if($.inArray(undefined, CustomerIDArr)>-1){
		$.MsgBox_Unload.Alert("提示","有客户名称未选！");
		return false;
	}
    if(WarehouseFlag==1){
        $.MsgBox_Unload.Alert("提示","有仓库地址未选！");
        return false;
    }
	$.ajax({
		type:'post',
		url:'InventoryInfoOperate',
		data:{
			InventoryID:InventoryID,
			CustomerID :CustomerIDArr,
			OrderQuantity:OrderQuantityArr,
			ids:idsArr,
			Warehouse: WarehouseArr
		},
		dataType:'JSON',
		success:function(data){
			if(data){
				$.MsgBox.Alert("提示", "提交成功！");
			}else{
				$.MsgBox.Alert("提示", "提交失败！");
			}
		},
		error:function(){
			 $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
		}
		
	})
    */
});
//检索设备号
	$(".InitialQuantity").on("input propertychange",function(){
	var Con = $(".InitialQuantity").val();
	$.ajax({
		type:'post',
		url:'InventoryAdd',
		data:{
			content:Con
		},
		dataType:'JSON',
		success:function(data){
			var str = "";
            for(var i = 1 ; i < data.length; i++){
           	 str += '<li ID="'+data[i].CommodityID+'" class="equipmentListLi"  Supplier="'+data[i].Supplier+'" Description="'+data[i].Description+'" SupplierID="'+data[i].SupplierID+'";>'+data[i].Model+'</li>';
            }
            $(".equipmentList").empty();
           	 $(".equipmentList").append(str);
            $(".equipmentList").show();
		},
		error:function(){
			 $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
		}
	});
});
$(document).on("click",function(){
	$(".equipmentList").hide();
});
$(document).on("click",".equipmentListLi",function(){
	var currentCont = $(this).text();
	var ID= $(this).attr("ID");
	var Supplier= $(this).attr("Supplier");
	var SupplierID= $(this).attr("SupplierID");
	var Description= $(this).attr("Description");
	$(this).parent().prev().val(currentCont);
	$(this).parent().prev().attr("ID",ID);
	$(".contract_add .Name").val(Supplier);
	$(".contract_add .Remarks").val(Description);
	$(".equipmentList").hide();
});
// 仓库地址
$(".down_span").on("click",function(e){
    e.stopPropagation();
    $(this).siblings(".ware_house_sel").fadeIn(200);
});

$(".ware_house_sel option").on("click",function(e){
    e.stopPropagation();
    $(this).parent().siblings(".ware_house_inp").val($(this).parent(".ware_house_sel").val());
    $(this).parent(".ware_house_sel").fadeOut(200);
});

/*
$(".add_pncode").on("click",function(){
    $(".cover-color").fadeIn(200);
    $(".add_pncode_div").fadeIn(200);
});
// 新增条码号
$(document).on("input propertychange",'#add_pncode_div_inp',function(){
    if($(this).val()==""){
        return;
    }
    var addKeyword = $(this).val().trim();
    if(addKeyword == "" || addKeyword == "--"){
        $.MsgBox_Unload.Alert("提示", "model_number值为空或格式错误");
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
                $("#add_pncode_select").empty().append(str);
                $('#add_pncode_select').show();
            },
            error : function () {
                $.MsgBox_Unload.Alert("提示", "服务器繁忙，model_number获取有误！");
            }
        });
    }
});
*/

/*
// 点击其他关闭
$(document).on("click", function() {
    $('#add_pncode_select').fadeOut(200);
    $("#ware_house_sel").fadeOut(200);
    // $(".add_pncode_div_tit").attr("value","");
});
*/

/*
$(document).on("click", '#add_pncode_select option', function(e) {
    e.stopPropagation();
    $('#add_pncode_div_inp').val($('#add_pncode_select').val());
    $(".add_pncode_div_tit").attr("value",$(this).attr("text"));
    $('#add_pncode_select').hide();
});

// 新增条码号确定
$(".add_pncode_div_foot input").on("click",function(){
    var Type = "add";
    var CommodityID = $(".add_pncode_div_tit").attr("value");
    var PNCode = $("#add_pncode_div_sel").val().trim();
    if(PNCode==""||PNCode==undefined||PNCode==null){
        $.MsgBox_Unload.Alert("提示", "请先填写条码号");
        return false;
    }
    if(CommodityID==""||CommodityID==undefined||CommodityID==null){
        $.MsgBox_Unload.Alert("提示", "请先选择型号");
        return false;
    }
    $.ajax({
        type:"POST",
        url:"PNCode",
        data:{
            Type:Type,
            CommodityID:CommodityID,
            PNCode:PNCode
        },
        dataType:"text",
        success:function(data){
            if(data.indexOf("条形码已存在，是否覆盖？")>-1||data.indexOf("条形码已存在")>-1){
                $(".cover_bg").fadeIn(200);
                $(".publish_div").fadeIn(200);
            }else{
                $.MsgBox_Unload.Alert("新增条码号提示", data);
            }
        },
        error:function(){
            $.MsgBox_Unload.Alert("提示", "网络繁忙");
        }
    });
});
*/

/*
// 关闭
$(".add_pncode_div_tit_r").on("click",function(){
    $("#add_pncode_div_sel").val("");
    $("#add_pncode_div_inp").val("");
    $(".cover-color").fadeOut(200);
    $(".add_pncode_div").fadeOut(200);
    $(".add_pncode_div_tit").attr("value","");
}); 

// 覆盖事件
$("#publish_no").on("click",function(){
    $(".publish_div").fadeOut(200);
    $(".cover_bg").fadeOut(200);
});

$("#publish_yes").on("click",function(){
    var Type = "update";
    var CommodityID = $(".add_pncode_div_tit").attr("value");
    var PNCode = $("#add_pncode_div_sel").val().trim();
    $.ajax({
        type:"POST",
        url:"PNCode",
        data:{
            Type:Type,
            CommodityID:CommodityID,
            PNCode:PNCode
        },
        dataType:"text",
        success:function(data){
            console.log(typeof data);
            if(data=="true"){
                $.MsgBox_Unload.Alert("提示", "更新成功！");
                $("#publish_no").trigger("click");
            }else if(data=="false"){
                $.MsgBox_Unload.Alert("提示", "更新失败!");
            }
        },
        error:function(){
            $.MsgBox_Unload.Alert("提示", "网络繁忙");
        }
    });
});
*/

/*扫码*/
$(".scan_pncode").on("click",function(){
    $(".cover-color, .scan_pncode_div").fadeIn(200);
});

$(".scan_pncode_div_tit_r").on("click",function(){
    $("#scan_pncode_div_sel").val("");
    $(".cover-color, .scan_pncode_div").fadeOut(200);
 });

$(".scan_pncode_div_foot input").on("click",function(){
    var PNCode = $("#scan_pncode_div_sel").val().trim();
    if(PNCode==""||PNCode==undefined||PNCode==null){
        $.MsgBox_Unload.Alert("提示", "请先填写条码号");
        return false;
    }
    $.ajax({
        type:"GET",
        url:"PNCode",
        data:{
            PNCode:PNCode
        },
        dataType:"json",
        success:function(data){
            var str="";
            if(data.length>1){
                str+='<tr class="tbody_tr">'+
                    '<td class="inventory-edit tt1" value="'+data[1].ID+'" data-pncode="'+data[1].PNCode+'">1</td>'+
                    '<td class="td-Model tt2" value="'+data[1].CommodityID+'">'+data[1].Model+'</td>'+
                    '<td class="td-Description tt3" title="'+data[1].Description+'" style="max-width:260px">'+data[1].Description+'</td>'+
                    '<td class="td-InventoryQuantity tt4">'+data[1].InventoryQuantity+'</td>'+
                    '<td class="td-SellerPriceOne tt5">'+data[1].SellerPriceOne+'</td>'+
                    '<td class="td-ListPrice tt6">'+data[1].ListPrice+'</td>'+
                    '<td class="td-Supplier tt7">'+data[1].Supplier+'</td>'+
                    '<td class="tt8"><i class="fa fa-eye inventory_in_show" value="'+data[1].ID+'"></i></td>'+
                    '<td class="tt9"><i class="fa fa-eye inventory_out_show" value="'+data[1].ID+'"></i></td>'+
                    '<td class="tt10"><i class="fa fa-eye Reserve_info" value="'+data[1].ID+'"></i></td>'+
                    // '<td class="tabWarehouseAddress">总仓库</td>'+
                    '<td class="tt11">'+data[1].Suzhou+'</td>'+
                    '<td class="tt12">'+data[1].Xiamen+'</td>'+
                    '<td class="tt13">'+data[1].Shenzhen+'</td>'+
                    '<td class="tt14">'+data[1].Xianggang+'</td>'+
                    '<td class="tt16">'+data[1].Chengdu+'</td>'+
                    '<td class="tt17">'+data[1].Hefei+'</td>'+
                    '<td class="tt18">'+data[1].Beijing+'</td>'+
                    '<td class="tt19">'+data[1].Shijiazhuang+'</td>'+
                    '<td class="td-Remarks tt15" title="'+data[1].Remarks+'">'+data[1].Remarks+'</td>'+
                '</tr>';
            }
            $(".tbody_tr").remove();
            $("#table1 .bgcccTr").after(str);
            if(data.length<2){
                $.MsgBox_Unload.Alert("提示", "输入型号不完整或不存在");
            }
        },
        error:function(){
            $.MsgBox_Unload.Alert("提示", "网络繁忙");
        },
        complete: function(XMLHttpRequest, textStatus){
            if(textStatus=="success"){
                $(".scan_pncode_div_tit_r").trigger("click");
                scanInit();
            }
        }
    });
});

/*
// 仓库切换
var WarehouseAddressObj = {};
var WarehouseAddressArr = ["总仓库","苏州","合肥","厦门","成都","香港"];
var WarehouseAddressStr = '<select class="selWarehouseAddress">'+
                        '<option value="0">请选择</option>'+
                        '<option value="苏州">苏州</option>'+
                        '<option value="合肥">合肥</option>'+
                        '<option value="厦门">厦门</option>'+
                        '<option value="成都">成都</option>'+
                        '<option value="香港">香港</option>'+
                        '</select>';

$(document).on("click",".tabWarehouseAddress",function(e){
    e.stopPropagation();
    var inText = $(this).text().trim();
    if(WarehouseAddressArr.indexOf(inText)>-1){
        $(this).text("").html(WarehouseAddressStr);
    }
});

$(document).on("change",".selWarehouseAddress",function(){
  var ival = $(this).val();
  if(ival == 0){
    return false;
  }else{
    var that = $(this).parent();
    // tr
    var spthat = $(this).parent().parent();
    $(this).detach();
    that.html("").text(ival);
    var CommodityID = that.siblings(".td-Model").attr("value");
    var oldModel = that.siblings(".td-Model").text();
    var newival = getWarehouseEn(ival);
    $.ajax({
        type: "POST",
        url: "InventoryQuery",
        data: {
            // WarehouseAddress: newival,
            Warehouse: newival,
            CommodityID: CommodityID
        },
        dataType: "json",
        success: function(data){
            if(data.length>1){
                that.siblings(".inventory-edit").attr("value",data[1].ID);
                spthat.find("i.inventory_in_show").attr("value",data[1].ID);
                spthat.find("i.inventory_out_show").attr("value",data[1].ID);
                spthat.find("i.Reserve_info").attr("value",data[1].ID);
                that.siblings(".td-Model").text(data[1].Model);
                // that.siblings(".td-Model").attr("value",data[1].Model);
                that.siblings(".td-Description").text(data[1].Description);
                that.siblings(".td-Description").attr("title",data[1].Description);
                that.siblings(".td-InventoryQuantity").text(data[1].InventoryQuantity);
                that.siblings(".td-SellerPriceOne").text(data[1].SellerPriceOne);
                that.siblings(".td-ListPrice").text(data[1].ListPrice);
                that.siblings(".td-Supplier").text(data[1].Supplier);
                that.siblings(".td-Remarks").text(data[1].Remarks);
                that.siblings(".td-Remarks").attr("title",data[1].Remarks);
                // if(WarehouseAddressObj.hasOwnProperty(data[1].Model)){
                // }
                WarehouseAddressObj[data[1].Model] = data[1].WarehouseAddress;
            }else{
                if(WarehouseAddressObj.hasOwnProperty(oldModel)){
                    that.text(WarehouseAddressObj[oldModel]);
                }else{
                    that.text("总仓库");
                }
                $.MsgBox_Unload.Alert("选择此仓库提示", "无数据！已回退上个有数据的仓库");
            }
        },
        error: function(){
            $.MsgBox_Unload.Alert("提示", "网络繁忙！请刷新重试");
        }
    });
  }
});
*/

// 显示隐藏其他仓库
$(".tt14 i").click(function(){
    if($(this).is(".fa-plus-square")){
        $(".tt16, .tt17, .tt18, .tt19").show();
        $(this).removeClass("fa-plus-square").addClass("fa-minus-square");
    }else{
        $(".tt16, .tt17, .tt18, .tt19").hide();
        $(this).addClass("fa-plus-square").removeClass("fa-minus-square");
    }
});

// 仓库地址切换选择
/*
$(document).on("click",".td_Warehouse_in, .td_Warehouse_out",function(e){
    e.stopPropagation();
    var inText = $(this).text().trim();
    if(WarehouseHasSelArr.indexOf(inText)>-1 && $(this).parent().find("input[value='保存']").is(":visible")){
        $(this).text("").html(WarehouseHasSelStr);
    }
});
*/

$(document).on("click",".reserve_Warehouse",function(e){
    e.stopPropagation();
    var inText = $(this).text().trim();
    if(WarehouseHasSelArr.indexOf(inText)>-1 && !$(this).parent().find(".xuhao").attr("value")){
        $(this).text("").html(WarehouseHasSelStr);
    }
});

$(document).on("change",".WarehouseHasSel",function(){
    var ival = $(this).val();
    if(ival == "请选择"){
      return false;
    }else{
      var that = $(this).parent();
      $(this).detach();
      that.html("").text(ival);
    }
});

// 数量去除非数字
$(document).on("blur",".table_content .reserve_num",function(){
    var curVal = $(this).text().replace(/[^\d^\.]+/g,"");
    $(this).text(curVal);
});

/*修改最新进展*/
$(".change_latest_develop").click(function(){
    // var evt = new MouseEvent("click", {
    //     bubbles: true,
    //     cancelable: true,
    //     view: window,
    // });
    // var cb = $(".latest_develop input[type='checkbox']")[0]; //element to click on
    // cb.dispatchEvent(evt);
    if($(this).val()=="修改"){
        $(".latest_develop input[type='date']").show();
        $(this).val("提交");
    }else if($(this).val()=="提交"){
        var CheckTime = $(".latest_develop input[type='date']").val();
        if(!CheckTime){
            $.MsgBox_Unload.Alert("更新提示", "未选择时间！");
            return false;
        }
        var that = $(this);
        $.ajax({
            type: "GET",
            url: "InventoryInfoOperate",
            data: {
                classify: "check",
                CheckTime: CheckTime
            },
            dataType: "json"
        }).then(function(data){
            if(data==true){
                $.MsgBox_Unload.Alert("更新提示", "更新成功！");
                $(".latest_develop input[type='date']").hide();
                $(".latest_develop span").text(CheckTime);
                $(".latest_develop span").attr("value",CheckTime);
                that.val("修改");
            }else if(data==false){
                $.MsgBox_Unload.Alert("更新提示", "更新失败！");
            }
        },function(){
            $.MsgBox_Unload.Alert("更新提示", "网络繁忙，更新失败！");
        });
    }
});