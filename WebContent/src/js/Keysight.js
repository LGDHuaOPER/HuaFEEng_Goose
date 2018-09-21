// 定义添加修改公有data对象
var addDataObj = {};
var updateDataObj = {};
var hasSearch = 0;
var keysight_currentpage = 1;
function pageStyle(currentPage,pageCounts){
    if(pageCounts == 1){
        $("#fistPage").attr("disabled","disabled");
        $("#upPage").attr("disabled","disabled");
        $("#nextPage").attr("disabled","disabled");
        $("#lastPage").attr("disabled","disabled");
        buttonDisabled("#fistPage, #upPage, #nextPage, #lastPage");
    }else if(currentPage == 1){
        $("#fistPage").attr("disabled","disabled");
        $("#upPage").attr("disabled","disabled");
        $("#lastPage").attr("disabled",false);
        $("#nextPage").attr("disabled",false);
        buttonDisabled("#fistPage, #upPage");
        buttonAbled("#nextPage, #lastPage");
    }else if(currentPage == pageCounts){
        $("#lastPage").attr("disabled","disabled");
        $("#nextPage").attr("disabled","disabled");
        $("#fistPage").attr("disabled",false);
        $("#upPage").attr("disabled",false);
        buttonDisabled("#nextPage, #lastPage");
        buttonAbled("#fistPage, #upPage");
    }else{
        $("#lastPage, #nextPage, #fistPage, #upPage").attr("disabled",false);
        buttonAbled("#lastPage, #nextPage, #fistPage, #upPage");
    }
}
function buttonDisabled(iClass) {
    return $(iClass).removeClass("buttonDisabled buttonAbled").addClass("buttonDisabled");
}

function buttonAbled(jClass) {
    return $(jClass).removeClass("buttonDisabled buttonAbled").addClass("buttonAbled");
}

function initTable(){
    var str = '';
    $.ajax({
        type:"GET",
        url:"Keysight",
        dataType:'json',
        data:{
            LoadType:"data",
            CurrentPage:1
        },
        success:function(res){
            var pageCounts = res.pageCount;
            for(var i = 1;i<res.data.length;i++){
            	// 处理字段
            	var handleCity = res.data[i].City;
            	var handleArea = res.data[i].Area;
            	var handleOCD = res.data[i].OpportunityCreateDate;
            	var handleFOD = res.data[i].OrderDate;
            	var handleAOBD = res.data[i].BookingDate;
            	if(handleCity=="--"||handleCity==""||handleCity==null||handleCity==undefined){
            		handleCity="";
            	}else{
            		handleCity = ConvertToPinYin(handleCity,true);
            	}

            	if(handleArea=="--"||handleArea==""||handleArea==null||handleArea==undefined){
            		handleArea="";
            	}else{
            		handleArea = ConvertToPinYin(handleArea,true);
            	}

            	if(handleOCD=="0000-00-00"||handleOCD=="--"||handleOCD==""||handleOCD==null||handleOCD==undefined){
            		handleOCD="";
            	}else{
            		handleOCD = globalDataCNToEN(handleOCD);
            	}

            	if(handleFOD=="0000-00-00"||handleFOD=="--"||handleFOD==""||handleFOD==null||handleFOD==undefined){
            		handleFOD="";
            	}else{
            		handleFOD = globalDataCNToEN(handleFOD);
            	}

            	if(handleAOBD=="0000-00-00"||handleAOBD=="--"||handleAOBD==""||handleAOBD==null||handleAOBD==undefined){
            		handleAOBD="";
            	}else{
            		handleAOBD = globalDataCNToEN(handleAOBD);
            	}

                str+='<tr>'+
                    '<td class="update_td" value="'+res.data[i].ID+'">'+parseInt((keysight_currentpage-1)*10 + i)+'</td>'+
                    '<td class="ocd" title="'+handleOCD+'">'+handleOCD+'</td>'+
                    '<td class="hastd_customername" title="'+res.data[i].CustomerName+'">'+res.data[i].CustomerName+'</td>'+
                    '<td class="hastd_city" title="'+handleCity+'">'+handleCity+'</td>'+
                    '<td class="hastd_deal_status" title="'+res.data[i].DealStatus+'">'+res.data[i].DealStatus+'</td>'+
                    '<td class="hastd_win_probability" title="'+res.data[i].WinProbability+'">'+res.data[i].WinProbability+'</td>'+
                    '<td class="hastd_keysight_FE_name" title="'+res.data[i].KeysightName+'">'+res.data[i].KeysightName+'</td>'+
                    '<td class="hastd_keysight_model_number" title="'+res.data[i].Model+'">'+res.data[i].Model+'</td>'+
                    '<td class="hastd_forecasted_order_date" title="'+handleFOD+'">'+handleFOD+'</td>'+
                    '<td class="hastd_customer_attn" title="'+res.data[i].Contact+'">'+res.data[i].Contact+'</td>'+
                    '<td class="hastd_status" title="'+res.data[i].Status+'">'+res.data[i].Status+'</td>'+
                    '<td class="hastd_state_province" style="display:none">'+handleArea+'</td>'+
                    '<td class="hastd_actual_order_booking_date" style="display:none">'+handleAOBD+'</td>'+
                    '<td class="CommodityID" style="display:none">'+res.data[i].CommodityID+'</td>'+
                    '<td class="hastd_customer_tel" style="display:none">'+res.data[i].ContactInfo1+'</td>'+
                    '<td class="hastd_country_code" style="display:none">'+res.data[i].CountryCode+'</td>'+
                    '<td class="hastd_currency_code" style="display:none">'+res.data[i].CurrencyCode+'</td>'+
                    '<td class="CustomerID" style="display:none">'+res.data[i].CustomerID+'</td>'+
                    '<td class="hastd_deal_id" style="display:none">'+res.data[i].DealID+'</td>'+
                    '<td class="hastd_customer_email" style="display:none">'+res.data[i].Email+'</td>'+
                    '<td class="hastd_p_c_kr" style="display:none">'+res.data[i].KeysightReseller+'</td>'+
                    '<td class="hastd_line" style="display:none">'+res.data[i].Line+'</td>'+
                    '<td class="hastd_partner_id" style="display:none">'+res.data[i].PartnerID+'</td>'+
                    '<td class="hastd_postal_code" style="display:none">'+res.data[i].PostalCode+'</td>'+
                    '<td class="hastd_qty" style="display:none">'+res.data[i].Qty+'</td>'+
                    '<td class="hastd_keysight_sales_order" style="display:none">'+res.data[i].SalesOrder+'</td>'+
                    '<td class="hastd_estimated_keysight_deal_value" style="display:none">'+res.data[i].DealValue+'</td>'+
                    '<td class="hastd_shipto_location" style="display:none">'+res.data[i].ShipToLocation+'</td>'+
                    '<td class="hastd_street_address" style="display:none">'+res.data[i].StreetAddress+'</td>'+
                '</tr>';
            }

            $(".m_table table tbody").empty().append(str);
            $(".m_page #currentPage").text(keysight_currentpage);
            $(".m_page #allPage").text(pageCounts);
            pageStyle(keysight_currentpage,pageCounts);
        },
        error:function(){
            $.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
        }
    });
}

// add搜索客户信息
$(document).on("keyup",'.add_keysight .info_customername',function(){
    if($(this).val()==""){
        return;
    }
    var addKeyword = $(this).val().trim();
    if(addKeyword == "" || addKeyword == "--"){
        $.MsgBox_Unload.Alert("提示", "CustomerName搜索值为空或格式错误");
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
                        str+='<option value="'+newData[i].CustomerName+'" text="'+newData[i].ID+'" contact="'+newData[i].Contact+'" contactinfo="'+newData[i].ContactInfo1+'" area="'+newData[i].Area+'" city="'+newData[i].City+'" email="'+newData[i].Email+'">'+newData[i].CustomerName+'&nbsp;:&nbsp;'+newData[i].Contact+'</option>';
                    }
                }
                $("#add_CustomerName").empty().append(str);
                $('#add_CustomerName').show();
            },
            error : function () {
                $.MsgBox_Unload.Alert("提示", "服务器繁忙，CustomerName获取有误！");
            }
        });
    }
});

// update搜索客户信息
$(document).on("keyup",'.update_keysight .info_customername',function(){
    if($(this).val()==""){
        return;
    }
    var addKeyword = $(this).val().trim();
    if(addKeyword == "" || addKeyword == "--"){
        $.MsgBox_Unload.Alert("提示", "CustomerName搜索值为空或格式错误");
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
                        str+='<option value="'+newData[i].CustomerName+'" text="'+newData[i].ID+'" contact="'+newData[i].Contact+'" contactinfo="'+newData[i].ContactInfo1+'" area="'+newData[i].Area+'" city="'+newData[i].City+'" email="'+newData[i].Email+'">'+newData[i].CustomerName+'&nbsp;:&nbsp;'+newData[i].Contact+'</option>';
                    }
                }
                $("#update_CustomerName").empty().append(str);
                $('#update_CustomerName').show();
            },
            error : function () {
                $.MsgBox_Unload.Alert("提示", "服务器繁忙，CustomerName获取有误！");
            }
        });
    }
});

// add搜索产品info_keysight_model_number
$(document).on("keyup",'.add_keysight .info_keysight_model_number',function(){
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
                $("#add_KeysightModelNumber").empty().append(str);
                $('#add_KeysightModelNumber').show();
            },
            error : function () {
                $.MsgBox_Unload.Alert("提示", "服务器繁忙，model_number获取有误！");
            }
        });
    }
});

// update搜索产品info_keysight_model_number
$(document).on("keyup",'.update_keysight .info_keysight_model_number',function(){
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
                $("#update_KeysightModelNumber").empty().append(str);
                $('#update_KeysightModelNumber').show();
            },
            error : function () {
                $.MsgBox_Unload.Alert("提示", "服务器繁忙，model_number获取有误！");
            }
        });
    }
});

$(document).on("click", function() {
    $('#add_CustomerName').hide();
    $('#update_CustomerName').hide();
    $('#add_KeysightModelNumber').hide();
    $('#update_KeysightModelNumber').hide();
    $(".add_keysight #add_ForecastedOrderDate").fadeOut(100);
    $(".add_keysight #add_ActualOrderBookingDate").fadeOut(100);
    $(".update_keysight #update_ForecastedOrderDate").fadeOut(100);
    $(".update_keysight #update_ActualOrderBookingDate").fadeOut(100);
});

$(document).on("click", '#add_CustomerName option', function(e){
    e.stopPropagation();
    $('.add_keysight .info_customername').val($('#add_CustomerName').val());
    addDataObj.CustomerID = $(this).attr("text");
    $('.add_keysight .info_customer_attn').val($(this).attr("contact"));
    $('.add_keysight .info_customer_tel').val($(this).attr("contactinfo"));
    $('.add_keysight .info_state_province').val($(this).attr("area"));
    $('.add_keysight .info_city').val($(this).attr("city"));
    $('.add_keysight .info_customer_email').val($(this).attr("email"));
    $('#add_CustomerName').hide();
});
$(document).on("click", '#update_CustomerName option', function(e){
    e.stopPropagation();
    $('.update_keysight .info_customername').val($('#update_CustomerName').val());
    updateDataObj.CustomerID = $(this).attr("text");
    $('.update_keysight .info_customer_attn').val($(this).attr("contact"));
    $('.update_keysight .info_customer_tel').val($(this).attr("contactinfo"));
    $('.update_keysight .info_state_province').val($(this).attr("area"));
    $('.update_keysight .info_city').val($(this).attr("city"));
    $('.update_keysight .info_customer_email').val($(this).attr("email"));
    $('#update_CustomerName').hide();
});

$(document).on("click", '#add_KeysightModelNumber option', function(e){
    e.stopPropagation();
    $('.add_keysight .info_keysight_model_number').val($('#add_KeysightModelNumber').val());
    addDataObj.CommodityID = $(this).attr("text");
    // $('.add_keysight .info_estimated_keysight_deal_value').val($(this).attr("sellerpriceone"));
    $('#add_KeysightModelNumber').hide();
});

$(document).on("click", '#update_KeysightModelNumber option', function(e){
    e.stopPropagation();
    $('.update_keysight .info_keysight_model_number').val($('#update_KeysightModelNumber').val());
    updateDataObj.CommodityID = $(this).attr("text");
    // $('.update_keysight .info_estimated_keysight_deal_value').val($(this).attr("sellerpriceone"));
    $('#update_KeysightModelNumber').hide();
});

// add模块日期格式转换
$("#add_ForecastedOrderDate, #add_ActualOrderBookingDate, #update_ForecastedOrderDate, #update_ActualOrderBookingDate").on("click",function(e){
	e.stopPropagation();
});
$(".add_keysight .info_forecasted_order_date").on("click",function(e){
    e.stopPropagation();
    $(".add_keysight #add_ForecastedOrderDate").fadeIn(200);
});

$(".add_keysight #add_ForecastedOrderDate").on("change",function(){
	var curCNVal = $(this).val();
	if(curCNVal==""||curCNVal==undefined||curCNVal==null){
		curCNVal="";
	}else{
		curCNVal=globalDataCNToEN(curCNVal);
	}
    $(".add_keysight .info_forecasted_order_date").val(curCNVal);
    $(".add_keysight #add_ForecastedOrderDate").fadeOut(200);
});

$(".add_keysight .info_actual_order_booking_date").on("click",function(e){
	e.stopPropagation();
    $(".add_keysight #add_ActualOrderBookingDate").fadeIn(200);
});

$(".add_keysight #add_ActualOrderBookingDate").on("change",function(){
	var curCNVal = $(this).val();
	if(curCNVal==""||curCNVal==undefined||curCNVal==null){
		curCNVal="";
	}else{
		curCNVal=globalDataCNToEN(curCNVal);
	}
    $(".add_keysight .info_actual_order_booking_date").val(curCNVal);
    $(".add_keysight #add_ActualOrderBookingDate").fadeOut(200);
});

// update模块日期格式转换
$(".update_keysight .info_forecasted_order_date").on("click",function(e){
	e.stopPropagation();
    $(".update_keysight #update_ForecastedOrderDate").fadeIn(200);
});

$(".update_keysight #update_ForecastedOrderDate").on("change",function(){
	var curCNVal = $(this).val();
	if(curCNVal==""||curCNVal==undefined||curCNVal==null){
		curCNVal="";
	}else{
		curCNVal=globalDataCNToEN(curCNVal);
	}
    $(".update_keysight .info_forecasted_order_date").val(curCNVal);
    $(".update_keysight #update_ForecastedOrderDate").fadeOut(200);
});

$(".update_keysight .info_actual_order_booking_date").on("click",function(e){
	e.stopPropagation();
    $(".update_keysight #update_ActualOrderBookingDate").fadeIn(200);
});

$(".update_keysight #update_ActualOrderBookingDate").on("change",function(){
	var curCNVal = $(this).val();
	if(curCNVal==""||curCNVal==undefined||curCNVal==null){
		curCNVal="";
	}else{
		curCNVal=globalDataCNToEN(curCNVal);
	}
    $(".update_keysight .info_actual_order_booking_date").val(curCNVal);
    $(".update_keysight #update_ActualOrderBookingDate").fadeOut(200);
});


// 搜索功能
$(".m_button_r_top label").on("click",function(){
    if($(this).children().val()=="singleSelect"){
        $(this).css("color","#333");
        $(this).siblings("label").css("color","#fff");
        $(".m_button_r_l div.input-group").fadeOut(100);
    }else{
        $(this).css("color","#333");
        $(this).siblings("label").css("color","#fff");
        $(".m_button_r_l div.input-group").fadeIn(100);
    }
});

function sosuoInit(){
    $("input[name='querytype'][value='singleSelect']").prop("checked","checked");
    $("input[name='querytype'][value='singleSelect']").parent().addClass("active").css("color","#333");
    $("input[name='querytype'][value='mixSelect']").prop("checked",false);
    $("input[name='querytype'][value='mixSelect']").parent().removeClass("active").css("color","#fff");
    $(".m_button_r_l .input-group").fadeOut(100);
    $(".m_button_r_l .input-group-btn button").html("选择<span class='caret'></span>");
    // $(".m_button_r_l .input-group-btn button").text("选择");
    $(".m_button_r_l .input-group-btn button").attr("title","");
    $(".m_button_r_l .input-group input").val("");
    $(".m_button_r_m .input-group-btn button").html("选择<span class='caret'></span>");
    $(".m_button_r_m .input-group-btn button").attr("title","");
    $(".m_button_r_m .input-group input").val("");
}

$(".m_button_r_l .dropdown-menu li").on("click",function(){
	var inText = $(this).text();
	var inTitle = $(this).attr("title");
	$(this).parent().siblings("button").text(inTitle);
	$(this).parent().siblings("button").attr("title",inText);
});
$(".m_button_r_m .dropdown-menu li").on("click",function(){
	var inText = $(this).text();
	var inTitle = $(this).attr("title");
	$(this).parent().siblings("button").text(inTitle);
	$(this).parent().siblings("button").attr("title",inText);
});

// 搜索渲染页面
function sousuoRender(res,pageCounts){
    hasSearch = 1;
	var str = '';
	for(var i = 1;i<res.data.length;i++){
		// 处理字段
		var handleCity = res.data[i].City;
		var handleArea = res.data[i].Area;
		var handleOCD = res.data[i].OpportunityCreateDate;
		var handleFOD = res.data[i].OrderDate;
		var handleAOBD = res.data[i].BookingDate;
		if(handleCity=="--"||handleCity==""||handleCity==null||handleCity==undefined){
			handleCity="";
		}else{
			handleCity = ConvertToPinYin(handleCity,true);
		}

		if(handleArea=="--"||handleArea==""||handleArea==null||handleArea==undefined){
			handleArea="";
		}else{
			handleArea = ConvertToPinYin(handleArea,true);
		}

		if(handleOCD=="0000-00-00"||handleOCD=="--"||handleOCD==""||handleOCD==null||handleOCD==undefined){
			handleOCD="";
		}else{
			handleOCD = globalDataCNToEN(handleOCD);
		}

		if(handleFOD=="0000-00-00"||handleFOD=="--"||handleFOD==""||handleFOD==null||handleFOD==undefined){
			handleFOD="";
		}else{
			handleFOD = globalDataCNToEN(handleFOD);
		}

		if(handleAOBD=="0000-00-00"||handleAOBD=="--"||handleAOBD==""||handleAOBD==null||handleAOBD==undefined){
			handleAOBD="";
		}else{
			handleAOBD = globalDataCNToEN(handleAOBD);
		}

	    str+='<tr>'+
	        '<td class="update_td" value="'+res.data[i].ID+'">'+parseInt((keysight_currentpage-1)*10 + i)+'</td>'+
	        '<td class="ocd" title="'+handleOCD+'">'+handleOCD+'</td>'+
	        '<td class="hastd_customername" title="'+res.data[i].CustomerName+'">'+res.data[i].CustomerName+'</td>'+
	        '<td class="hastd_city" title="'+handleCity+'">'+handleCity+'</td>'+
	        '<td class="hastd_deal_status" title="'+res.data[i].DealStatus+'">'+res.data[i].DealStatus+'</td>'+
	        '<td class="hastd_win_probability" title="'+res.data[i].WinProbability+'">'+res.data[i].WinProbability+'</td>'+
	        '<td class="hastd_keysight_FE_name" title="'+res.data[i].KeysightName+'">'+res.data[i].KeysightName+'</td>'+
	        '<td class="hastd_keysight_model_number" title="'+res.data[i].Model+'">'+res.data[i].Model+'</td>'+
	        '<td class="hastd_forecasted_order_date" title="'+handleFOD+'">'+handleFOD+'</td>'+
	        '<td class="hastd_customer_attn" title="'+res.data[i].Contact+'">'+res.data[i].Contact+'</td>'+
	        '<td class="hastd_status" title="'+res.data[i].Status+'">'+res.data[i].Status+'</td>'+
	        '<td class="hastd_state_province" style="display:none">'+handleArea+'</td>'+
	        '<td class="hastd_actual_order_booking_date" style="display:none">'+handleAOBD+'</td>'+
	        '<td class="CommodityID" style="display:none">'+res.data[i].CommodityID+'</td>'+
	        '<td class="hastd_customer_tel" style="display:none">'+res.data[i].ContactInfo1+'</td>'+
	        '<td class="hastd_country_code" style="display:none">'+res.data[i].CountryCode+'</td>'+
	        '<td class="hastd_currency_code" style="display:none">'+res.data[i].CurrencyCode+'</td>'+
	        '<td class="CustomerID" style="display:none">'+res.data[i].CustomerID+'</td>'+
	        '<td class="hastd_deal_id" style="display:none">'+res.data[i].DealID+'</td>'+
	        '<td class="hastd_customer_email" style="display:none">'+res.data[i].Email+'</td>'+
	        '<td class="hastd_p_c_kr" style="display:none">'+res.data[i].KeysightReseller+'</td>'+
	        '<td class="hastd_line" style="display:none">'+res.data[i].Line+'</td>'+
	        '<td class="hastd_partner_id" style="display:none">'+res.data[i].PartnerID+'</td>'+
	        '<td class="hastd_postal_code" style="display:none">'+res.data[i].PostalCode+'</td>'+
	        '<td class="hastd_qty" style="display:none">'+res.data[i].Qty+'</td>'+
	        '<td class="hastd_keysight_sales_order" style="display:none">'+res.data[i].SalesOrder+'</td>'+
	        '<td class="hastd_estimated_keysight_deal_value" style="display:none">'+res.data[i].DealValue+'</td>'+
	        '<td class="hastd_shipto_location" style="display:none">'+res.data[i].ShipToLocation+'</td>'+
	        '<td class="hastd_street_address" style="display:none">'+res.data[i].StreetAddress+'</td>'+
	    '</tr>';
	}
	$(".m_table table tbody").empty().append(str);
	$(".m_page #currentPage").text(keysight_currentpage);
	$(".m_page #allPage").text(pageCounts);
	pageStyle(keysight_currentpage,pageCounts);
}

// 搜索请求
$("#keysight_search").on("click",function(){
	var QueryType = $(".m_button_r_top input[name='querytype']:checked").val();
	var Column1 = $(".m_button_r_m .input-group-btn button").attr("title");
	var Column2 = $(".m_button_r_l .input-group-btn button").attr("title");
	var Content1 = $(".m_button_r_m input").val().trim();
	var Content2 = $(".m_button_r_l input").val().trim();
	if(QueryType==undefined||QueryType==""||QueryType==null||QueryType=="on"){
		$.MsgBox_Unload.Alert("提示","请选择搜索类别");
		return;
	}else if(QueryType=="singleSelect"){
		if(Column1=="Opportunity Create Date"){
			// var leng1 = globalStrHandleCount(/\//g,Content1);
			// if(leng1==2){
			// 	Content1 = globalDataENToCN(Content1);
			// }else{
			// 	var leng2 = globalStrHandleCount(/-/g,Content1);
			// 	if(leng2!=2){
			// 		$.MsgBox_Unload.Alert("提示","日期格式错误");
			// 		return;
			// 	}
			// }
			var leng1 = globalStrHandleCount(/\//g,Content1);
			var leng2 = globalStrHandleCount(/-/g,Content1);
			if(leng2==0&&leng1<3){
				Content1 = globalDataENToCN(Content1);
			}else if(leng1==0&&leng2<3){
				Content1 = Content1;
			}else{
				$.MsgBox_Unload.Alert("提示","日期格式错误");
				return;
			}
		}else if(Column1=="Forecasted Order Date"){
			var leng3 = globalStrHandleCount(/\//g,Content1);
			var leng4 = globalStrHandleCount(/-/g,Content1);
			if(leng4==0&&leng3<3){
				Content1 = globalDataENToCN(Content1);
			}else if(leng3==0&&leng4<3){
				Content1 = Content1;
			}else{
				$.MsgBox_Unload.Alert("提示","日期格式错误");
				return;
			}
		}
		if(Column1==undefined||Column1==""||Column1==null||Column1=="选择"){
			$.MsgBox_Unload.Alert("提示","请选择搜索字段");
			return;
		}else if(Content1==""||Content1==null||Content1==undefined){
			$.MsgBox_Unload.Alert("提示","搜索内容为空");
			return;
		}else{
			$.ajax({
				type:"GET",
				url:"Keysight",
				dataType:'json',
				data:{
				    LoadType:"data",
				    QueryType:QueryType,
				    Column1:Column1,
				    Content1:Content1,
				    CurrentPage:1
				},
				success:function(res){
					var pageCounts = res.pageCount;
					sousuoRender(res,pageCounts);
				},
				error:function(){
					$.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
				}
			});
		}
	}else if(QueryType=="mixSelect"){
		if(Column1=="Opportunity Create Date"){
			var leng10 = globalStrHandleCount(/\//g,Content1);
			var leng20 = globalStrHandleCount(/-/g,Content1);
			if(leng20==0&&leng10<3){
				Content1 = globalDataENToCN(Content1);
			}else if(leng10==0&&leng20<3){
				Content1 = Content1;
			}else{
				$.MsgBox_Unload.Alert("提示","日期格式错误");
				return;
			}
		}else if(Column1=="Forecasted Order Date"){
			var leng30 = globalStrHandleCount(/\//g,Content1);
			var leng40 = globalStrHandleCount(/-/g,Content1);
			if(leng40==0&&leng30<3){
				Content1 = globalDataENToCN(Content1);
			}else if(leng30==0&&leng40<3){
				Content1 = Content1;
			}else{
				$.MsgBox_Unload.Alert("提示","日期格式错误");
				return;
			}
		}else if(Column2=="Opportunity Create Date"){
			var leng5 = globalStrHandleCount(/\//g,Content2);
			var leng6 = globalStrHandleCount(/-/g,Content2);
			if(leng6==0&&leng5<3){
				Content2 = globalDataENToCN(Content2);
			}else if(leng5==0&&leng6<3){
				Content2 = Content2;
			}else{
				$.MsgBox_Unload.Alert("提示","日期格式错误");
				return;
			}
		}else if(Column2=="Forecasted Order Date"){
			var leng7 = globalStrHandleCount(/\//g,Content2);
			var leng8 = globalStrHandleCount(/-/g,Content2);
			if(leng8==0&&leng7<3){
				Content2 = globalDataENToCN(Content2);
			}else if(leng7==0&&leng8<3){
				Content2 = Content2;
			}else{
				$.MsgBox_Unload.Alert("提示","日期格式错误");
				return;
			}
		}

		var tempColumn1,tempColumn2,tempContent1,tempContent2;
		if(Column1==undefined||Column1==""||Column1==null||Column1=="选择"){
			tempColumn1=1;
		}
		if(Column2==undefined||Column2==""||Column2==null||Column2=="选择"){
			tempColumn2=1;
		}
		if(Content1==""||Content1==null||Content1==undefined){
			tempContent1=1;
		}
		if(Content2==""||Content2==null||Content2==undefined){
			tempContent2=1;
		}
		if(tempColumn1==1||tempColumn2==1){
			$.MsgBox_Unload.Alert("提示","请选择搜索字段");
			return;
		}else if(tempContent1==1||tempContent2==1){
			$.MsgBox_Unload.Alert("提示","搜索内容为空");
			return;
		}else{
			$.ajax({
				type:"GET",
				url:"Keysight",
				dataType:'json',
				data:{
				    LoadType:"data",
				    QueryType:QueryType,
				    Column1:Column1,
				    Content1:Content1,
				    Column2:Column2,
				    Content2:Content2,
				    CurrentPage:1
				},
				success:function(res){
					var pageCounts = res.pageCount;
					sousuoRender(res,pageCounts);
				},
				error:function(){
					$.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
				}
			});
		}
	}
});

// 取消搜索
$("#keysight_cancel").on("click",function(){
	hasSearch = 0;
	initTable();
	sosuoInit();
	addDataObj = {};
	updateDataObj = {};
	$("#jumpNumber").val("");
});


// 翻页功能
function goPageRender(currentPage){
    var str = '';
    $.ajax({
        type:"GET",
        url:"Keysight",
        dataType:'json',
        data:{
            LoadType:"data",
            CurrentPage:currentPage
        },
        success:function(res){
            var pageCounts = res.pageCount;
            // alert(currentPage);
            for(var i = 1;i<res.data.length;i++){
            	// 处理字段
            	var handleCity = res.data[i].City;
    			var handleArea = res.data[i].Area;
    			var handleOCD = res.data[i].OpportunityCreateDate;
    			var handleFOD = res.data[i].OrderDate;
    			var handleAOBD = res.data[i].BookingDate;
    			if(handleCity=="--"||handleCity==""||handleCity==null||handleCity==undefined){
    				handleCity="";
    			}else{
    				handleCity = ConvertToPinYin(handleCity,true);
    			}

    			if(handleArea=="--"||handleArea==""||handleArea==null||handleArea==undefined){
    				handleArea="";
    			}else{
    				handleArea = ConvertToPinYin(handleArea,true);
    			}

    			if(handleOCD=="0000-00-00"||handleOCD=="--"||handleOCD==""||handleOCD==null||handleOCD==undefined){
    				handleOCD="";
    			}else{
    				handleOCD = globalDataCNToEN(handleOCD);
    			}

    			if(handleFOD=="0000-00-00"||handleFOD=="--"||handleFOD==""||handleFOD==null||handleFOD==undefined){
    				handleFOD="";
    			}else{
    				handleFOD = globalDataCNToEN(handleFOD);
    			}

    			if(handleAOBD=="0000-00-00"||handleAOBD=="--"||handleAOBD==""||handleAOBD==null||handleAOBD==undefined){
    				handleAOBD="";
    			}else{
    				handleAOBD = globalDataCNToEN(handleAOBD);
    			}

                str+='<tr>'+
                    '<td class="update_td" value="'+res.data[i].ID+'">'+parseInt((currentPage-1)*10 + i)+'</td>'+
                    '<td class="ocd" title="'+handleOCD+'">'+handleOCD+'</td>'+
                    '<td class="hastd_customername" title="'+res.data[i].CustomerName+'">'+res.data[i].CustomerName+'</td>'+
                    '<td class="hastd_city" title="'+handleCity+'">'+handleCity+'</td>'+
                    '<td class="hastd_deal_status" title="'+res.data[i].DealStatus+'">'+res.data[i].DealStatus+'</td>'+
                    '<td class="hastd_win_probability" title="'+res.data[i].WinProbability+'">'+res.data[i].WinProbability+'</td>'+
                    '<td class="hastd_keysight_FE_name" title="'+res.data[i].KeysightName+'">'+res.data[i].KeysightName+'</td>'+
                    '<td class="hastd_keysight_model_number" title="'+res.data[i].Model+'">'+res.data[i].Model+'</td>'+
                    '<td class="hastd_forecasted_order_date" title="'+handleFOD+'">'+handleFOD+'</td>'+
                    '<td class="hastd_customer_attn" title="'+res.data[i].Contact+'">'+res.data[i].Contact+'</td>'+
                    '<td class="hastd_status" title="'+res.data[i].Status+'">'+res.data[i].Status+'</td>'+
                    '<td class="hastd_state_province" style="display:none">'+handleArea+'</td>'+
                    '<td class="hastd_actual_order_booking_date" style="display:none">'+handleAOBD+'</td>'+
                    '<td class="CommodityID" style="display:none">'+res.data[i].CommodityID+'</td>'+
                    '<td class="hastd_customer_tel" style="display:none">'+res.data[i].ContactInfo1+'</td>'+
                    '<td class="hastd_country_code" style="display:none">'+res.data[i].CountryCode+'</td>'+
                    '<td class="hastd_currency_code" style="display:none">'+res.data[i].CurrencyCode+'</td>'+
                    '<td class="CustomerID" style="display:none">'+res.data[i].CustomerID+'</td>'+
                    '<td class="hastd_deal_id" style="display:none">'+res.data[i].DealID+'</td>'+
                    '<td class="hastd_customer_email" style="display:none">'+res.data[i].Email+'</td>'+
                    '<td class="hastd_p_c_kr" style="display:none">'+res.data[i].KeysightReseller+'</td>'+
                    '<td class="hastd_line" style="display:none">'+res.data[i].Line+'</td>'+
                    '<td class="hastd_partner_id" style="display:none">'+res.data[i].PartnerID+'</td>'+
                    '<td class="hastd_postal_code" style="display:none">'+res.data[i].PostalCode+'</td>'+
                    '<td class="hastd_qty" style="display:none">'+res.data[i].Qty+'</td>'+
                    '<td class="hastd_keysight_sales_order" style="display:none">'+res.data[i].SalesOrder+'</td>'+
                    '<td class="hastd_estimated_keysight_deal_value" style="display:none">'+res.data[i].DealValue+'</td>'+
                    '<td class="hastd_shipto_location" style="display:none">'+res.data[i].ShipToLocation+'</td>'+
                    '<td class="hastd_street_address" style="display:none">'+res.data[i].StreetAddress+'</td>'+
                '</tr>';
            }

            $(".m_table table tbody").empty().append(str);
            $(".m_page #currentPage").text(currentPage);
            $(".m_page #allPage").text(pageCounts);
            pageStyle(currentPage,pageCounts);
        },
        error:function(){
            $.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
        }
    });
}

// 搜索翻页功能
// 加载函数
function searchDataInject(res,currentPage,pageCounts){
	var str = '';
	for(var i = 1;i<res.data.length;i++){
		// 处理字段
		var handleCity = res.data[i].City;
		var handleArea = res.data[i].Area;
		var handleOCD = res.data[i].OpportunityCreateDate;
		var handleFOD = res.data[i].OrderDate;
		var handleAOBD = res.data[i].BookingDate;
		if(handleCity=="--"||handleCity==""||handleCity==null||handleCity==undefined){
			handleCity="";
		}else{
			handleCity = ConvertToPinYin(handleCity,true);
		}

		if(handleArea=="--"||handleArea==""||handleArea==null||handleArea==undefined){
			handleArea="";
		}else{
			handleArea = ConvertToPinYin(handleArea,true);
		}

		if(handleOCD=="0000-00-00"||handleOCD=="--"||handleOCD==""||handleOCD==null||handleOCD==undefined){
			handleOCD="";
		}else{
			handleOCD = globalDataCNToEN(handleOCD);
		}

		if(handleFOD=="0000-00-00"||handleFOD=="--"||handleFOD==""||handleFOD==null||handleFOD==undefined){
			handleFOD="";
		}else{
			handleFOD = globalDataCNToEN(handleFOD);
		}

		if(handleAOBD=="0000-00-00"||handleAOBD=="--"||handleAOBD==""||handleAOBD==null||handleAOBD==undefined){
			handleAOBD="";
		}else{
			handleAOBD = globalDataCNToEN(handleAOBD);
		}

	    str+='<tr>'+
	        '<td class="update_td" value="'+res.data[i].ID+'">'+parseInt((currentPage-1)*10 + i)+'</td>'+
	        '<td class="ocd" title="'+handleOCD+'">'+handleOCD+'</td>'+
	        '<td class="hastd_customername" title="'+res.data[i].CustomerName+'">'+res.data[i].CustomerName+'</td>'+
	        '<td class="hastd_city" title="'+handleCity+'">'+handleCity+'</td>'+
	        '<td class="hastd_deal_status" title="'+res.data[i].DealStatus+'">'+res.data[i].DealStatus+'</td>'+
	        '<td class="hastd_win_probability" title="'+res.data[i].WinProbability+'">'+res.data[i].WinProbability+'</td>'+
	        '<td class="hastd_keysight_FE_name" title="'+res.data[i].KeysightName+'">'+res.data[i].KeysightName+'</td>'+
	        '<td class="hastd_keysight_model_number" title="'+res.data[i].Model+'">'+res.data[i].Model+'</td>'+
	        '<td class="hastd_forecasted_order_date" title="'+handleFOD+'">'+handleFOD+'</td>'+
	        '<td class="hastd_customer_attn" title="'+res.data[i].Contact+'">'+res.data[i].Contact+'</td>'+
	        '<td class="hastd_status" title="'+res.data[i].Status+'">'+res.data[i].Status+'</td>'+
	        '<td class="hastd_state_province" style="display:none">'+handleArea+'</td>'+
	        '<td class="hastd_actual_order_booking_date" style="display:none">'+handleAOBD+'</td>'+
	        '<td class="CommodityID" style="display:none">'+res.data[i].CommodityID+'</td>'+
	        '<td class="hastd_customer_tel" style="display:none">'+res.data[i].ContactInfo1+'</td>'+
	        '<td class="hastd_country_code" style="display:none">'+res.data[i].CountryCode+'</td>'+
	        '<td class="hastd_currency_code" style="display:none">'+res.data[i].CurrencyCode+'</td>'+
	        '<td class="CustomerID" style="display:none">'+res.data[i].CustomerID+'</td>'+
	        '<td class="hastd_deal_id" style="display:none">'+res.data[i].DealID+'</td>'+
	        '<td class="hastd_customer_email" style="display:none">'+res.data[i].Email+'</td>'+
	        '<td class="hastd_p_c_kr" style="display:none">'+res.data[i].KeysightReseller+'</td>'+
	        '<td class="hastd_line" style="display:none">'+res.data[i].Line+'</td>'+
	        '<td class="hastd_partner_id" style="display:none">'+res.data[i].PartnerID+'</td>'+
	        '<td class="hastd_postal_code" style="display:none">'+res.data[i].PostalCode+'</td>'+
	        '<td class="hastd_qty" style="display:none">'+res.data[i].Qty+'</td>'+
	        '<td class="hastd_keysight_sales_order" style="display:none">'+res.data[i].SalesOrder+'</td>'+
	        '<td class="hastd_estimated_keysight_deal_value" style="display:none">'+res.data[i].DealValue+'</td>'+
	        '<td class="hastd_shipto_location" style="display:none">'+res.data[i].ShipToLocation+'</td>'+
	        '<td class="hastd_street_address" style="display:none">'+res.data[i].StreetAddress+'</td>'+
	    '</tr>';
	}
	$(".m_table table tbody").empty().append(str);
	$(".m_page #currentPage").text(currentPage);
	$(".m_page #allPage").text(pageCounts);
	pageStyle(currentPage,pageCounts);
}


function searchGoPageRender(currentPage){
	var QueryType = $(".m_button_r_top input[name='querytype']:checked").val();
	var Column1 = $(".m_button_r_m .input-group-btn button").attr("title");
	var Column2 = $(".m_button_r_l .input-group-btn button").attr("title");
	var Content1 = $(".m_button_r_m input").val().trim();
	var Content2 = $(".m_button_r_l input").val().trim();
	if(QueryType==undefined||QueryType==""||QueryType==null||QueryType=="on"){
		$.MsgBox_Unload.Alert("提示","请选择搜索类别");
		return;
	}else if(QueryType=="singleSelect"){
		if(Column1==undefined||Column1==""||Column1==null||Column1=="选择"){
			$.MsgBox_Unload.Alert("提示","请选择搜索字段");
			return;
		}else if(Content1==""||Content1==null||Content1==undefined){
			$.MsgBox_Unload.Alert("提示","搜索内容为空");
			return;
		}else{
			$.ajax({
				type:"GET",
				url:"Keysight",
				dataType:'json',
				data:{
				    LoadType:"data",
				    QueryType:QueryType,
				    Column1:Column1,
				    Content1:Content1,
				    CurrentPage:currentPage
				},
				success:function(res){
					var pageCounts = res.pageCount;
					searchDataInject(res,currentPage,pageCounts);
				},
				error:function(){
					$.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
				}
			});
		}
	}else if(QueryType=="mixSelect"){
		var tempColumn1,tempColumn2,tempContent1,tempContent2;
		if(Column1==undefined||Column1==""||Column1==null||Column1=="选择"){
			tempColumn1=1;
		}
		if(Column2==undefined||Column2==""||Column2==null||Column2=="选择"){
			tempColumn2=1;
		}
		if(Content1==""||Content1==null||Content1==undefined){
			tempContent1=1;
		}
		if(Content2==""||Content2==null||Content2==undefined){
			tempContent2=1;
		}
		if(tempColumn1==1||tempColumn2==1){
			$.MsgBox_Unload.Alert("提示","请选择搜索字段");
			return;
		}else if(tempContent1==1||tempContent2==1){
			$.MsgBox_Unload.Alert("提示","搜索内容为空");
			return;
		}else{
			$.ajax({
				type:"GET",
				url:"Keysight",
				dataType:'json',
				data:{
				    LoadType:"data",
				    QueryType:QueryType,
				    Column1:Column1,
				    Content1:Content1,
				    Column2:Column2,
				    Content2:Content2,
				    CurrentPage:currentPage
				},
				success:function(res){
					var pageCounts = res.pageCount;
					searchDataInject(res,currentPage,pageCounts);
				},
				error:function(){
					$.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
				}
			});
		}
	}
}


$("#jumpNumber").keyup(function(){
    var newVal = $(this).val().replace(/[^\d]/g,'');
    $(this).val(newVal);
});

// 翻页
$("#fistPage").click(function(){
    var currentPage =1;
    if(hasSearch==0){
    	goPageRender(currentPage);
    }else if(hasSearch==1){
    	searchGoPageRender(currentPage);
    }
});

$("#lastPage").click(function(){
    var currentPage =Number($("#allPage").text());
    if(hasSearch==0){
    	goPageRender(currentPage);
    }else if(hasSearch==1){
    	searchGoPageRender(currentPage);
    }
});

$("#upPage").click(function(){
    var currentPage = Number($("#currentPage").text());
    if(currentPage == 1){
        return;
    }else{
        currentPage--;
        if(hasSearch==0){
        	goPageRender(currentPage);
        }else if(hasSearch==1){
        	searchGoPageRender(currentPage);
        }
    }
});

$("#nextPage").click(function(){
    var currentPage = Number($("#currentPage").text());
    var pageCounts = Number($("#allPage").text());
    if(currentPage == pageCounts){
        return;
    }else{
        currentPage++;
        if(hasSearch==0){
        	goPageRender(currentPage);
        }else if(hasSearch==1){
        	searchGoPageRender(currentPage);
        }
    }
});
//跳页
$("#Gotojump").click(function(){
    var currentPage = $("#jumpNumber").val().trim();
    var pageCounts = Number($("#allPage").text());
    var oldCurrentPage = Number($("#currentPage").text());
    if(currentPage == oldCurrentPage || currentPage <= 0 || currentPage>pageCounts){
        $("#jumpNumber").val('');
        return;
    }else{
        if(hasSearch==0){
        	goPageRender(currentPage);
        }else if(hasSearch==1){
        	searchGoPageRender(currentPage);
        }
    }
});

// 导出
$("#keysight_export").on("click",function(){
	var keysightHref = window.location.href;
	var newHref = keysightHref.indexOf(globalProjectName)>-1?keysightHref.split(globalProjectName)[0]+globalProjectName+"/":keysightHref.split("Logistics")[0]+"Logistics/";
	var exportHref = newHref+"ExportKeysightExcel";
	window.open(exportHref);
});

// 加载完成
$(function(){
    initTable();
    sosuoInit();
    // 默认值
    var defaultV = "486492";
    var defaultCountryCode = "CN";
    // 添加弹出关闭
    $(".m_button_l input[value='添加']").on("click",function(){
        $(".bg_cover").fadeIn(200);
        $(".add_keysight").fadeIn(200);
        // 初始化清空
        $(".add_keysight_body_in input").each(function(){
            $(this).val("");
        });
        $(".add_keysight_body_in select").each(function(){
            $(this).val("");
        });
        addDataObj = {};

        $(".add_keysight .info_partner_id").val(defaultV);
        $(".add_keysight .info_partner_id").on("focus",function() {  
            if ($(this).val() == defaultV) {  
                $(this).val("");  
            }  
        });  
        $(".add_keysight .info_partner_id").on("blur",function() {  
            if ($(this).val()== "") {  
                $(this).val(defaultV);
            }  
        });
        // Country Code 默认值
        $(".add_keysight .info_country_code").val(defaultCountryCode);
        $(".add_keysight .info_country_code").on("focus",function() {  
            if ($(this).val() == defaultCountryCode) {  
                $(this).val("");  
            }  
        });  
        $(".add_keysight .info_country_code").on("blur",function() {  
            if ($(this).val()== "") {  
                $(this).val(defaultCountryCode);
            }  
        });
    });

    $("#keysight_addclose, .add_keysight_tit_r").on("click",function(){
        addDataObj = {};
    	$(".add_keysight .info_partner_id").off("focus blur");
    	$(".add_keysight .info_country_code").off("focus blur");
        $(".add_keysight").fadeOut(200);
        $(".bg_cover").fadeOut(200);
    });

    // 修改弹出关闭
    $(document).on("click",".m_table tbody .update_td",function(){
    	updateDataObj.ID = $(this).attr("value");
    	if(updateDataObj.CustomerID==undefined||updateDataObj.CustomerID==null||updateDataObj.CustomerID==""){
    		updateDataObj.CustomerID=$(this).siblings("td.CustomerID").text();
    	}
    	if(updateDataObj.CommodityID==undefined||updateDataObj.CommodityID==null||updateDataObj.CommodityID==""){
    		updateDataObj.CommodityID=$(this).siblings("td.CommodityID").text();
    	}
        $(".bg_cover").fadeIn(200);
        $(".update_keysight").fadeIn(200);
        // 填充
        $(this).siblings("td").each(function(){
            if($(this).attr("class").indexOf("hastd_")>-1){
                var curClass = $(this).attr("class").replace("hastd_","info_");
                var curVal = $(this).text().trim();
                if(curVal == "--" || curVal == undefined || curVal == null){
                    curVal = "";
                }
                $(".update_keysight_body_in").find("."+curClass).val(curVal);
            }
        });
    });
    $("#keysight_updateclose, .update_keysight_tit_r").on("click",function(){
    	updateDataObj = {};
        $(".update_keysight").fadeOut(200);
        $(".bg_cover").fadeOut(200);
    });

    // 添加提交
    $("#keysight_addsubmit").on("click",function(){
        var OrderDate = $(".add_keysight .info_forecasted_order_date").val();
        var BookingDate = $(".add_keysight .info_actual_order_booking_date").val();
        var PartnerID = $(".add_keysight .info_partner_id").val();
        var DealID = $(".add_keysight .info_deal_id").val();
        var StreetAddress = $(".add_keysight .info_street_address").val();
        var PostalCode = $(".add_keysight .info_postal_code").val();
        var CountryCode = $(".add_keysight .info_country_code").val();
        var DealStatus = $(".add_keysight .info_deal_status").val();
        var WinProbability = $(".add_keysight .info_win_probability").val();
        var KeysightReseller = $(".add_keysight .info_p_c_kr").val();
        var ShipToLocation = $(".add_keysight .info_shipto_location").val();
        var KeysightName = $(".add_keysight .info_keysight_FE_name").val();
        var Line = $(".add_keysight .info_line").val();
        // var CommodityID = $(".add_keysight .info_line").val();
        var Qty = $(".add_keysight .info_qty").val();
        var SalesOrder = $(".add_keysight .info_keysight_sales_order").val();
        var CurrencyCode = $(".add_keysight .info_currency_code").val();
        var Status = $(".add_keysight .info_status").val();
        var DealValue = $('.add_keysight .info_estimated_keysight_deal_value').val();
        if(OrderDate==""||OrderDate==null||OrderDate==undefined||OrderDate=="00/00/0000"){
        	OrderDate="";
        }else{
        	OrderDate=globalDataENToCN(OrderDate);
        }
        if(BookingDate==""||BookingDate==null||BookingDate==undefined||BookingDate=="00/00/0000"){
        	BookingDate="";
        }else{
        	BookingDate=globalDataENToCN(BookingDate);
        }

        if(addDataObj.CustomerID == ""||addDataObj.CustomerID==undefined || addDataObj.CustomerID == null){
            $.MsgBox_Unload.Alert("提示","请选择CustomerName");
        }else{
            addDataObj.OrderDate = OrderDate;
            addDataObj.BookingDate = BookingDate;
            addDataObj.Type = "add";
            addDataObj.ID = "0";
            addDataObj.PartnerID = PartnerID;
            addDataObj.DealID = DealID;
            addDataObj.StreetAddress = StreetAddress;
            addDataObj.PostalCode = PostalCode;
            addDataObj.CountryCode = CountryCode;
            addDataObj.DealStatus = DealStatus;
            addDataObj.WinProbability = WinProbability;
            addDataObj.KeysightReseller = KeysightReseller;
            addDataObj.ShipToLocation = ShipToLocation;
            addDataObj.KeysightName = KeysightName;
            addDataObj.Line = Line;
            // addDataObj.CommodityID = CommodityID;
            addDataObj.Qty = Qty;
            addDataObj.SalesOrder = SalesOrder;
            addDataObj.CurrencyCode = CurrencyCode;
            addDataObj.Status = Status;
            addDataObj.DealValue = DealValue;

            $.ajax({
                type : 'POST',
                url : 'KeysightOperate',
                data: addDataObj,
                dataType: 'text',
                success:function(data){
                    console.log(data);
                    console.log(typeof data);
                    if(data=="true"){
                        $.MsgBox_Unload.Alert("提示","添加成功！");
                        $("#keysight_addclose").trigger("click");
                    }else{
                        $.MsgBox_Unload.Alert("提示","添加失败！请检查");
                    }
                },
                error:function(){
                    $.MsgBox_Unload.Alert("提示","服务器繁忙！");
                },
                complete: function(XMLHttpRequest, textStatus){
                    if(textStatus=="success"){
                        initTable();
                        hasSearch = 0;
                        sosuoInit();
                    }
                }
            });
        }
    });

    // 修改提交
    $("#keysight_updatesubmit").on("click",function(){
        var OrderDate = $(".update_keysight .info_forecasted_order_date").val();
        var BookingDate = $(".update_keysight .info_actual_order_booking_date").val();
        var PartnerID = $(".update_keysight .info_partner_id").val();
        var DealID = $(".update_keysight .info_deal_id").val();
        var StreetAddress = $(".update_keysight .info_street_address").val();
        var PostalCode = $(".update_keysight .info_postal_code").val();
        var CountryCode = $(".update_keysight .info_country_code").val();
        var DealStatus = $(".update_keysight .info_deal_status").val();
        var WinProbability = $(".update_keysight .info_win_probability").val();
        var KeysightReseller = $(".update_keysight .info_p_c_kr").val();
        var ShipToLocation = $(".update_keysight .info_shipto_location").val();
        var KeysightName = $(".update_keysight .info_keysight_FE_name").val();
        var Line = $(".update_keysight .info_line").val();
        // var CommodityID = $(".update_keysight .info_line").val();
        var Qty = $(".update_keysight .info_qty").val();
        var SalesOrder = $(".update_keysight .info_keysight_sales_order").val();
        var CurrencyCode = $(".update_keysight .info_currency_code").val();
        var Status = $(".update_keysight .info_status").val();
        var DealValue = $('.update_keysight .info_estimated_keysight_deal_value').val();
        if(OrderDate==""||OrderDate==null||OrderDate==undefined||OrderDate=="00/00/0000"){
        	OrderDate="";
        }else{
        	OrderDate=globalDataENToCN(OrderDate);
        }
        if(BookingDate==""||BookingDate==null||BookingDate==undefined||BookingDate=="00/00/0000"){
        	BookingDate="";
        }else{
        	BookingDate=globalDataENToCN(BookingDate);
        }

        if(updateDataObj.CustomerID == ""||updateDataObj.CustomerID==undefined || updateDataObj.CustomerID == null){
            $.MsgBox_Unload.Alert("提示","请选择CustomerName");
        }else if(updateDataObj.CommodityID == ""||updateDataObj.CommodityID==undefined || updateDataObj.CommodityID == null){
        	$.MsgBox_Unload.Alert("提示","请选择Model Number");
        }else{
            updateDataObj.OrderDate = OrderDate;
            updateDataObj.BookingDate = BookingDate;
            updateDataObj.Type = "update";
            // updateDataObj.ID = "0";
            updateDataObj.PartnerID = PartnerID;
            updateDataObj.DealID = DealID;
            updateDataObj.StreetAddress = StreetAddress;
            updateDataObj.PostalCode = PostalCode;
            updateDataObj.CountryCode = CountryCode;
            updateDataObj.DealStatus = DealStatus;
            updateDataObj.WinProbability = WinProbability;
            updateDataObj.KeysightReseller = KeysightReseller;
            updateDataObj.ShipToLocation = ShipToLocation;
            updateDataObj.KeysightName = KeysightName;
            updateDataObj.Line = Line;
            // updateDataObj.CommodityID = CommodityID;
            updateDataObj.Qty = Qty;
            updateDataObj.SalesOrder = SalesOrder;
            updateDataObj.CurrencyCode = CurrencyCode;
            updateDataObj.Status = Status;
            updateDataObj.DealValue = DealValue;

            $.ajax({
                type : 'POST',
                url : 'KeysightOperate',
                data: updateDataObj,
                dataType: 'text',
                success:function(data){
                    console.log(data);
                    console.log(typeof data);
                    if(data=="true"){
                        $.MsgBox_Unload.Alert("提示","修改成功！");
                        $("#keysight_updateclose").trigger("click");
                    }else{
                        $.MsgBox_Unload.Alert("提示","修改失败！请检查");
                    }
                },
                error:function(){
                    $.MsgBox_Unload.Alert("提示","服务器繁忙！");
                },
                complete: function(XMLHttpRequest, textStatus){
                    if(textStatus=="success"){
                        initTable();
                        hasSearch = 0;
                        sosuoInit();
                    }
                }
            });
        }
    });// 修改提交结束
    // dealValue失去焦点
    $(".add_keysight .info_estimated_keysight_deal_value").on("blur",function(){
        var reg = new RegExp("^[0-9]+(.[0-9]{1,2})?$");
        var curValue = $(this).val();
        if(!reg.test(curValue)){
            $(this).val("");
        } 
    });
    $(".update_keysight .info_estimated_keysight_deal_value").on("blur",function(){
        var reg = new RegExp("^[0-9]+(.[0-9]{1,2})?$");
        var curValue = $(this).val();
        if(!reg.test(curValue)){
            $(this).val("");
        } 
    });
    
});