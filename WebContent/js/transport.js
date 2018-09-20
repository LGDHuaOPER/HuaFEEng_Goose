/**
 * Created by eoulu on 2017/3/29.
 */

 // 翻页组件按钮逻辑
 // flag 为按钮ID后缀  如 pageStyle(CurrentPage, pageCount, "2");
 function pageStyle(currentPage, pageCounts, flag){
     flag = flag || "";
     if(pageCounts == 1){
         $("#fistPage"+flag+", #upPage"+flag+", #nextPage"+flag+", #lastPage"+flag+", #Gotojump"+flag).prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
     }else if(currentPage == 1){
         $("#fistPage"+flag+", #upPage"+flag).prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
         $("#lastPage"+flag+", #nextPage"+flag+", #Gotojump"+flag).prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
     }else if(currentPage == pageCounts){
         $("#lastPage"+flag+", #nextPage"+flag).prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
         $("#fistPage"+flag+", #upPage"+flag+", #Gotojump"+flag).prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
     }else{
         $("#lastPage"+flag+", #nextPage"+flag+", #fistPage"+flag+", #upPage"+flag+", #Gotojump"+flag).prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
     }
 }

$('.cover-color').height($(document).height() - 80);

// ------------------------------------------搜索----------------------------------------
if ($('input[name="selected"]:checked').val() == 'singleSelect') {
	$('.select-content').css('margin-left', '33%');
} else {
	$('.select-content').css('margin-left', '23%');

}
function Check(selected) {
	if (selected == "singleSelect") {
		$('.select2').hide();
		$('.select-content').css('margin-left', '33%');
	} else {
		$('.select2').show();
		$('.select-content').css('margin-left', '23%');
	}
}

function selectSearch() {
	if ($('.select1 .time').css("display") == "none"
			&& $('.select2 .time').css("display") == "none") { // 非货期查询
		console.log("ininnin")
		if ($(".select2").css("display") == "none") {
			if ($("#searchContent1").val() != "") {
				$('#search').val('search');
				var newUrl = document.getElementById("top_text_from").action+"?ActualDelivery=no&column=DateOfSign&condition=All";
				// console.log(newUrl);
				$('#top_text_from').attr('action', newUrl);
				$('#top_text_from').submit();
			}
		} else {
			if ($("#searchContent1").val() != ""
					&& $("#searchContent2").val() != "") {
				$('#search').val('search');
				var newUrl = document.getElementById("top_text_from").action
						+ "?ActualDelivery=no&column=DateOfSign&condition=All";
				$('#top_text_from').attr('action', newUrl);
				$('#top_text_from').submit();
			}
		}
	} else { // 货期查询 时间框
		if ($('.select2').css("display") == "none") { // 单一货期查询
			var startTimeDate = $(".select1 .startTime").val().replace("-", "")
					.replace("-", "");
			var endTimeDate = $(".select1 .endTime").val().replace("-", "")
					.replace("-", "");
			if (startTimeDate < endTimeDate) {
				$('#search').val('search');
				var newUrl = document.getElementById("top_text_from").action
						+ "?ActualDelivery=no&column=DateOfSign&condition=All";
				$('#top_text_from').attr('action', newUrl);
				$('#top_text_from').submit();
			} else {
				alert("请正确输入时间！");
			}
		} else if ($('.select1 .time').css("display") == "none"
				&& $('.select2 .time').css("display") != "none") {
			var startTimeDate = $(".select2 .startTime").val().replace("-", "")
					.replace("-", "");
			var endTimeDate = $(".select2 .endTime").val().replace("-", "")
					.replace("-", "");
			if (startTimeDate < endTimeDate) {
				$('#search').val('search');
				var newUrl = document.getElementById("top_text_from").action
						+ "?ActualDelivery=no&column=DateOfSign&condition=All";
				$('#top_text_from').attr('action', newUrl);
				$('#top_text_from').submit();
			} else {
				alert("请正确输入时间！");
			}
		} else if ($('.select1 .time').css("display") != "none"
				&& $('.select2 .time').css("display") == "none") {
			var startTimeDate = $(".select1 .startTime").val().replace("-", "")
					.replace("-", "");
			var endTimeDate = $(".select1 .endTime").val().replace("-", "")
					.replace("-", "");
			if (startTimeDate < endTimeDate) {
				$('#search').val('search');
				var newUrl = document.getElementById("top_text_from").action
						+ "?ActualDelivery=no&column=DateOfSign&condition=All";
				$('#top_text_from').attr('action', newUrl);
				$('#top_text_from').submit();
			} else {
				alert("请正确输入时间！");
			}
		} else { // 组合货期查询
			var start1TimeDate = $(".select1 .startTime").val()
					.replace("-", "").replace("-", "");
			var end1TimeDate = $(".select1 .endTime").val().replace("-", "")
					.replace("-", "");
			var start2TimeDate = $(".select2 .startTime").val()
					.replace("-", "").replace("-", "");
			var end2TimeDate = $(".select2 .endTime").val().replace("-", "")
					.replace("-", "");
			if (start1TimeDate < end1TimeDate && start2TimeDate < end2TimeDate) {
				$('#search').val('search');
				var newUrl = document.getElementById("top_text_from").action + "?ActualDelivery=no&column=DateOfSign&condition=All";
				$('#top_text_from').attr('action', newUrl);
				$('#top_text_from').submit();
			} else {
				alert("请正确输入时间！");
			}
		}
	}

}

function selectCancel() {
	// 2018/07/26 注释
	// $('#search').val('cancel');
	// $('input[name="searchContent1"]').val('');
	// $('input[name="searchContent2"]').val('');
	// $('input[name="start_time1"]').val('');
	// $('input[name="end_time1"]').val('');
	// $('input[name="start_time2"]').val('');
	// $('input[name="end_time2"]').val('');
	// $('#top_text_from').submit();
	window.location.assign(globalBaseUrl+"/Transport?ActualDelivery=no&column=DateOfSign&condition=All");
}

$('#searchContent1').keypress(function(event) {
	$('#search').val('search');
	var keynum = (event.keyCode ? event.keyCode : event.which);
	if (keynum == '13') {
		$('#top_text_from').submit();
	}
});
$('#searchContent2').keypress(function(event) {
	$('#search').val('search');
	var keynum = (event.keyCode ? event.keyCode : event.which);
	if (keynum == '13') {
		$('#top_text_from').submit();
	}
});

$('#type1').click(function() {
	if ($(this).val().indexOf('货期') >= 0) {
		$('#searchContent1').hide();
		$('.select1 .time').show();
	} else {
		$('#searchContent1').show();
		$('.select1 .time').hide();
	}
});

$('#type2').click(function() {
	if ($(this).val().indexOf('货期') >= 0) {
		$('#searchContent2').hide();
		$('.select2 .time').show();
	} else {
		$('#searchContent2').show();
		$('.select2 .time').hide();
	}
});
// 分页
// function FistPage() {
// 	window.location.href = "../GetSelectDataListPage?currentpage=1";
// }
// function UpPage() {
// 	var jumpNumber = parseInt($('#currentPage').html()) - 1;
// 	window.location.href = "../GetSelectDataListPage?currentpage=" + jumpNumber;
// }
// function NextPage() {
// 	var jumpNumber = parseInt($('#currentPage').html()) + 1;
// 	window.location.href = "../GetSelectDataListPage?currentpage=" + jumpNumber;
// }
// function PageJump() {
// 	var jumpNumber = document.getElementById("jumpNumber").value;
// 	if (jumpNumber == null || jumpNumber == 0) {
// 		jumpNumber = $('#currentPage').html();
// 	} else if (jumpNumber > parseInt($('#allPage').html())) {
// 		jumpNumber = $('#allPage').html();
// 	}
// 	window.location.href = "../GetSelectDataListPage?currentpage=" + jumpNumber;
// }
// function LastPage() {
// 	var jumpNumber = parseInt($('#allPage').html());
// 	window.location.href = "../GetSelectDataListPage?currentpage=" + jumpNumber;
// }

function FistPage(arg) {
	var currentHref = window.location.href.split("ActualDelivery=")[1];
	window.location.href = arg + "1"+"&ActualDelivery="+currentHref;
}
function UpPage(arg) {
	var currentHref = window.location.href.split("ActualDelivery=")[1];
	window.location.href = arg+"&ActualDelivery="+currentHref;
}
function NextPage(arg) {
	var currentHref = window.location.href.split("ActualDelivery=")[1];
	console.log(currentHref);
	window.location.href = arg+"&ActualDelivery="+currentHref;
}
function PageJump(arg) {
	var currentHref = window.location.href.split("ActualDelivery=")[1];
	var jumpNumber = document.getElementById("jumpNumber").value;
	if (jumpNumber == null || jumpNumber == 0) {
		jumpNumber = $('#currentPage').html();
	} else if (jumpNumber > parseInt($('#allPage').html())) {
		jumpNumber = $('#allPage').html();
	}
	window.location.href = arg + jumpNumber+"&ActualDelivery="+currentHref;
}
function LastPage(arg) {
	var currentHref = window.location.href.split("ActualDelivery=")[1];
	var jumpNumber = parseInt($('#allPage').html());
	window.location.href = arg + jumpNumber+"&ActualDelivery="+currentHref;
}
/*page onload*/
$(function() {
	pageStyle(Number($('#currentPage').text()),Number($('#allPage').text()));
	var transport_queryType = $("#transport_queryType").val();
	if (transport_queryType == "singleSelect" || transport_queryType == "common"){
	    $('.select2').hide();
	}else{
	    $('.select2').show();
	}
	// 显隐新增字段 20180904
	// 
	$(".D3_D2, .D3_D1").each(function(){
		var V1 = $(this).siblings(".ActualDelivery_td").text();
		if(V1 != "" && V1 != "--" && V1 != "0000-00-00"){
			var inText = $(this).text();
			var preText1,preText2,preText3;
			if($(this).is(".D3_D2")){
				preText1 = "逾期";
				preText2 = "提前";
				preText3 = "正常发货";
			}else if($(this).is(".D3_D4")){
				preText1 = "延迟";
				preText2 = "提前";
				preText3 = "正常发货";
			}else if($(this).is(".D4_D2")){
				preText1 = "预计延迟";
				preText2 = "预计提前";
				preText3 = "正常发货";
			}else if($(this).is(".D3_D1")){
				preText1 = "";
				preText2 = "提前";
				preText3 = "当天发货";
			}
			var replaceText;
			if(inText == 0){
				replaceText = preText3;
			}else if(inText > 0){
				replaceText = preText1+inText+"天";
			}else if(inText < 0){
				replaceText = preText2+Math.abs(inText)+"天";
			}
			$(this).text(replaceText).css({"color":"#000","opacity":"1"});
		}
	});
	// 实际货期-预计货期
	$(".D3_D4").each(function(){
		var V1 = $(this).siblings(".ActualDelivery_td").text();
		var V2 = $(this).siblings(".ExpectedDeliveryPeriod_td").attr("title");
		if(V1 != "" && V1 != "--" && V1 != "0000-00-00" && V2 != "" && V2 != "--" && V2 != "0000-00-00"){
			var inText = $(this).text();
			var preText1,preText2,preText3;
			if($(this).is(".D3_D2")){
				preText1 = "逾期";
				preText2 = "提前";
				preText3 = "正常发货";
			}else if($(this).is(".D3_D4")){
				preText1 = "延迟";
				preText2 = "提前";
				preText3 = "正常发货";
			}else if($(this).is(".D4_D2")){
				preText1 = "预计延迟";
				preText2 = "预计提前";
				preText3 = "正常发货";
			}else if($(this).is(".D3_D1")){
				preText1 = "";
				preText2 = "提前";
				preText3 = "当天发货";
			}
			var replaceText;
			if(inText == 0){
				replaceText = preText3;
			}else if(inText > 0){
				replaceText = preText1+inText+"天";
			}else if(inText < 0){
				replaceText = preText2+Math.abs(inText)+"天";
			}
			$(this).text(replaceText).css({"color":"#000","opacity":"1"});
		}
	});
	// 预计货期-合同货期
	$(".D4_D2").each(function(){
		var V2 = $(this).siblings(".ExpectedDeliveryPeriod_td").attr("title");
		if(V2 != "" && V2 != "--" && V2 != "0000-00-00"){
			var inText = $(this).text();
			var preText1,preText2,preText3;
			if($(this).is(".D3_D2")){
				preText1 = "逾期";
				preText2 = "提前";
				preText3 = "正常发货";
			}else if($(this).is(".D3_D4")){
				preText1 = "延迟";
				preText2 = "提前";
				preText3 = "正常发货";
			}else if($(this).is(".D4_D2")){
				preText1 = "预计延迟";
				preText2 = "预计提前";
				preText3 = "正常发货";
			}else if($(this).is(".D3_D1")){
				preText1 = "";
				preText2 = "提前";
				preText3 = "当天发货";
			}
			var replaceText;
			if(inText == 0){
				replaceText = preText3;
			}else if(inText > 0){
				replaceText = preText1+inText+"天";
			}else if(inText < 0){
				replaceText = preText2+Math.abs(inText)+"天";
			}
			$(this).text(replaceText).css({"color":"#000","opacity":"1"});
		}
	});
});

// --------------------------------------------导出-----------------------------------------------------
$('#export')
		.click(
				function() {
					var query_type = $('input[name="query_type"]').val();
					console.log(query_type);
					var ActualDelivery = window.location.href
							.split("ActualDelivery=")[1].split("&")[0];
					var column = window.location.href.split("column=")[1]
							.split("&")[0];
					var condition = window.location.href.split("condition=")[1]
							.split("&")[0];
					var classify1 = $('input[name="classify1"]').val();
					var classify2 = $('input[name="classify2"]').val();
					var parameter1 = $('input[name="parameter1"]').val();
					var parameter2 = $('input[name="parameter2"]').val();
					if (query_type == 'singleSelect' && (classify1 == '合同货期' || classify1 == '实际货期' || classify1 == '预计货期')) {
						parameter1 = $('input[name="start_time1"]').val();
						parameter2 = $('input[name="end_time1"]').val();
						
						
						
					} 
//					if (query_type == 'mixSelect') {
//						var start_time1 = $('input[name="start_time1"]').val();
//						var end_time1 = $('input[name="end_time1"]').val();
//						var start_time2 = $('input[name="start_time2"]').val();
//						var end_time2 = $('input[name="end_time2"]').val();
//						$.ajax({
//							type : 'get',
//							url : 'ExportOrder',
//							data : {
//								query_type : query_type,
//								classify1 : classify1,
//								classify2 : classify2,
//								parameter1 : parameter1,
//								parameter2 : parameter2,
//								ActualDelivery : ActualDelivery,
//								column : column,
//								condition : condition,
//								start_time1:start_time1,
//								end_time1:end_time1,
//								start_time2:start_time2,
//								end_time2:end_time2
//							},
//							// dataType: 'json',
//							success : function(data) {
//								window.location.href = data;
//							},
//							error : function() {
//								$.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
//							}
//						});
//						
//					}
                        $("#export").attr("disabled","disabled");
    					$("#export").css({
    						"background":"#ddd",
    						"color":"rgba(0,0,0,0.7)",
    						"border":"none",
    						"cursor":"not-allowed"
    					});
						$.ajax({
							type : 'get',
							url : 'ExportOrder',
							data : {
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
								$("#export").attr("disabled",false);
								$("#export").css({
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

/*-----------------------------------------供应商明细----------------------------------------*/
$('.supply-show').click(
		function() {
			var order_id = $(this).attr("value");
			$.ajax({
				type : 'get',
				url : 'GetLogistic',
				data : {
					order_id : order_id
				},
				dataType : 'json',
				success : function(data) {
					$('.info .infoPage tbody').html("");
					for (var i = 1; i < data.length; i++) {
						var tr = '<tr>' + '<td>' + data[i].Supplier + '</td>'
								+ '<td>' + data[i].PONO + '</td>' + '<td>'
								+ data[i].SONO + '</td>' + '<td>'
								+ data[i].POAmount + '</td>' + '<td>'
								+ data[i].FactoryShipment + '</td>' +

								'</tr>';
						$('.info .infoPage tbody').append(tr);
					}
					$('.cover-color').show();
					$('.info').show();
				},
				error : function() {
					alert("defeat");
					$.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
				}
			});

		});
$('.info_close').click(function() {
	$('.cover-color').hide();
	$('.info').hide();
});

/*-----------------------------------------合同配置----------------------------------------*/
$('.contract-show').click(
		function() {
			var order_id = $(this).attr("value");

			$.ajax({
				type : 'get',
				url : 'GetOrderInfo',
				data : {
					order_id : order_id
				},
				dataType : 'json',
				success : function(data) {

					$('.contractPage tbody').html("");
					for (var i = 1; i < data.length; i++) {
						var tr = '<tr>' + '<td>' + data[i].OrderID + '</td>'
								+ '<td>' + data[i].EquipmentModel + '</td>'
								+ '<td>' + data[i].Remarks + '</td>' + '<td>'
								+ data[i].Number + '</td>' + '<td>'
								+ data[i].LogisticsNumber + '</td>' + '<td>'
								+ data[i].Date + '</td>' + '<td>'
								+ data[i].ExceptDate + '</td>' + '<td>'
								+ data[i].DeliveryNumber + '</td>' + '<td>'
								+ data[i].Status + '</td>' + '</tr>';
						$('.contractPage tbody').append(tr);
					}
					$('.cover-color').show();
					$('.contract').show();
				},
				error : function() {
					$.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
				}
			});
		});
$('.contract_close').click(function() {
	$('.cover-color').hide();
	$('.contract').hide();
});

/*-----------------------------------------合同明细----------------------------------------*/
$('.supply-detail-show').click(
		function() {
			var order_id = $(this).attr("value");

			$.ajax({
				type : 'get',
				url : 'GetQuotes',
				data : {
					order_id : order_id
				},
				dataType : 'json',
				success : function(data) {
					$('.detailPage tbody').html("");
					for (var i = 1; i < data.length; i++) {
						var tr = '<tr>' + '<td>' + data[i].WhetherToPay
								+ '</td>' + '<td>' + data[i].DutyFree + '</td>'
								+ '</tr>';
						$('.detailPage tbody').append(tr);
					}
					$('.cover-color').show();
					$('.supply-detail').show();
				},
				error : function() {
					$.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
				}
			});
		});
$('.supply_detail_close').click(function() {
	$('.cover-color').hide();
	$('.supply-detail').hide();
});

/*-----------------------------------------PO总额----------------------------------------*/
$('.supply-po-show').click(
		function() {
			var order_id = $(this).attr("value");
			$.ajax({
				type : 'get',
				url : 'GetPOStatics',
				data : {
					order_id : order_id
				},
				dataType : 'json',
				success : function(data) {
					$('.info_po .poPage tbody').html("");
					for (var i = 1; i < data.length; i++) {
						var tr = '<tr>' + '<td>' + data[i].PONO + '</td>'
								+ '<td>' + data[i].RMB + '</td>' + '<td>'
								+ data[i].USD + '</td>' + '</tr>';
						if (data[i].PONO != '--' && data[i].PONO != '') {
							$('.info_po .poPage tbody').append(tr);
						}
					}
					$('.cover-color').show();
					$('.info_po').show();
				},
				error : function() {
					$.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
				}
			});

		});
$('.po_close').click(function() {
	$('.cover-color').hide();
	$('.info_po').hide();
});
var ContractNo, ContractTitle;

// -------------------------------------操作------------------------------------------
$('.operate-edit').click(
    function() {
        var user_id = $('#userID').val();
        var check1 = $('#review_name1').val();
        var check2 = $('#review_name2').val();
        var order_id = $(this).attr("value");
        ContractNo = $(this).parent().parent().find('td').eq(4)
            .text();
        ContractTitle = $(this).parent().parent().find('td').eq(3)
            .text();
        $('.operate .update_date input[name="order_id"]').val(
            order_id);
        $('.operate .update_date input[name="actual_delivery"]')
            .val(
                $(this).parent().parent().find('td').eq(12)
                .html());
        if ($(this).parent().parent().find('td').eq(13).attr(
                "class") == "change") {
            $(
                    '.operate .update_date input[name="expected_delivery_period"]')
                .val(
                    $(this).parent().parent().find('td')
                    .eq(13).find("span").html())
        } else {
            $('.operate .update_date input[name="expected_delivery_period"]').val($(this).parent().parent().find('td').eq(13).html());
        }
        $('.operate .update_date select[name="status"]').find('option[text="' + $(this).parent().parent().find('td').eq(15).html() + '"]').prop("selected",true);

        $.ajax({
            type: 'get',
            url: 'GetOrderType',
            data: {
                order_id: order_id
            },
            dataType: 'json',
            success: function(data) {
                data = eval("(" + data + ")");
                $('#order_type').val(data.Type);
                $
                    .ajax({
                        type: 'get',
                        url: 'GetOrderInfoAndLogisc',
                        data: {
                            order_id: order_id
                        },
                        dataType: 'json',
                        success: function(data) {
                            $('.operatePage tbody')
                                .html("");
                            var tr;
                            for (var i = 1; i < data.length; i++) {
                                var FactoryShipment = (data[i].FactoryShipment == "0000-00-00" ? "--" : data[i].FactoryShipment);
                                var review_state1, review_state1;
                                if (data[i].Review1 == '0') {
                                    review_state1 = '<td>未审核<span style="display:none">' + data[i].Review1 + '</span></td>';
                                } else if (data[i].Review1 == '1') {
                                    review_state1 = '<td>不通过<br>（' + data[i].ReviewName1 + '）<span style="display:none">' + data[i].Review1 + '</span></td>';
                                } else {
                                    review_state1 = '<td>通过<br>（' + data[i].ReviewName1 + '）<span style="display:none">' + data[i].Review1 + '</span></td>';
                                }
                                if (data[i].Review2 == '0') {
                                    review_state2 = '<td>未审核<span style="display:none">' + data[i].Review2 + '</span></td>';
                                } else if (data[i].Review2 == '1') {
                                    review_state2 = '<td>不通过<br>（' + data[i].ReviewName2 + '）<span style="display:none">' + data[i].Review2 + '</span></td>';
                                } else {
                                    review_state2 = '<td>通过<br>（' + data[i].ReviewName2 + '）<span style="display:none">' + data[i].Review2 + '</span></td>';
                                }
                                if (data[i].Review1 == '1' || data[i].Review2 == '1') {
                                    if (check1 == "OrderInfoReview1" || check2 == "OrderInfoReview2") {
                                        tr = '<tr class="change current_line">' + '<td style="display: none">' + data[i].ID + '</td>' + '<td style="display: none">' + data[i].OrderID + '</td>' + '<td>' + data[i].Name + '</td>' + '<td>' + data[i].PONO + '</td>' + '<td>' + data[i].SONO + '</td>' + '<td>' + data[i].POAmount + '</td>' + '<td>' + data[i].RMBPOAmount + '</td>' + '<td>' + FactoryShipment + '</td>' + '<td>' + data[i].EquipmentModel + '</td>' + '<td>' + data[i].Remarks + '</td>' + '<td>' + data[i].Number + '</td>' + '<td>' + data[i].LogisticsNumber + '</td>' + '<td>' + data[i].InventoryQuantity + '</td>' + '<td>' + data[i].Date + '</td>' + '<td>' + data[i].ExceptDate + '</td>' + '<td>' + data[i].DeliveryNumber + '</td>' + '<td>' + data[i].Status + '</td>' + '<td>' + data[i].EstimatedPaymentTime + '</td>' + '<td>' + data[i].ActualPaymentTime + '</td>' + review_state1 + review_state2 + '<td style="background-color:white;"> <input type="button" name="operate-update" value="修改" class="bToggle"><br>' + '<input type="button" name="operate-check" value="审核" class="bToggle"></td>' + '</tr>';
                                    } else {
                                        tr = '<tr class="change current_line">' + '<td style="display: none">' + data[i].ID + '</td>' + '<td style="display: none">' + data[i].OrderID + '</td>' + '<td>' + data[i].Name + '</td>' + '<td>' + data[i].PONO + '</td>' + '<td>' + data[i].SONO + '</td>' + '<td>' + data[i].POAmount + '</td>' + '<td>' + data[i].RMBPOAmount + '</td>' + '<td>' + FactoryShipment + '</td>' + '<td>' + data[i].EquipmentModel + '</td>' + '<td>' + data[i].Remarks + '</td>' + '<td>' + data[i].Number + '</td>' + '<td>' + data[i].LogisticsNumber + '</td>' + '<td>' + data[i].InventoryQuantity + '</td>' + '<td>' + data[i].Date + '</td>' + '<td>' + data[i].ExceptDate + '</td>' + '<td>' + data[i].DeliveryNumber + '</td>' + '<td>' + data[i].Status + '</td>' + '<td>' + data[i].EstimatedPaymentTime + '</td>' + '<td>' + data[i].ActualPaymentTime + '</td>' + review_state1 + review_state2 +

                                            '<td style="background-color:white;"> <input type="button" name="operate-update" value="修改" class="bToggle"><br></td>' + '</tr>';
                                    }

                                } else {
                                    if (check1 == "OrderInfoReview1" || check2 == "OrderInfoReview2") {
                                        tr = '<tr>' + '<td style="display: none">' + data[i].ID + '</td>' + '<td style="display: none">' + data[i].OrderID + '</td>' + '<td>' + data[i].Name + '</td>' + '<td>' + data[i].PONO + '</td>' + '<td>' + data[i].SONO + '</td>' + '<td>' + data[i].POAmount + '</td>' + '<td>' + data[i].RMBPOAmount + '</td>' + '<td>' + FactoryShipment + '</td>' + '<td>' + data[i].EquipmentModel + '</td>' + '<td>' + data[i].Remarks + '</td>' + '<td>' + data[i].Number + '</td>' + '<td>' + data[i].LogisticsNumber + '</td>' + '<td>' + data[i].InventoryQuantity + '</td>' + '<td>' + data[i].Date + '</td>' + '<td>' + data[i].ExceptDate + '</td>' + '<td>' + data[i].DeliveryNumber + '</td>' + '<td>' + data[i].Status + '</td>' + '<td>' + data[i].EstimatedPaymentTime + '</td>' + '<td>' + data[i].ActualPaymentTime + '</td>' + review_state1 + review_state2 + '<td> <input type="button" name="operate-update" value="修改" class="bToggle"><br>' + '<input type="button" name="operate-check" value="审核" class="bToggle"></td>' + '</tr>';
                                    } else {
                                        tr = '<tr>' + '<td style="display: none">' + data[i].ID + '</td>' + '<td style="display: none">' + data[i].OrderID + '</td>' + '<td>' + data[i].Name + '</td>' + '<td>' + data[i].PONO + '</td>' + '<td>' + data[i].SONO + '</td>' + '<td>' + data[i].POAmount + '</td>' + '<td>' + data[i].RMBPOAmount + '</td>' + '<td>' + FactoryShipment + '</td>' + '<td>' + data[i].EquipmentModel + '</td>' + '<td>' + data[i].Remarks + '</td>' + '<td>' + data[i].Number + '</td>' + '<td>' + data[i].LogisticsNumber + '</td>' + '<td>' + data[i].InventoryQuantity + '</td>' + '<td>' + data[i].Date + '</td>' + '<td>' + data[i].ExceptDate + '</td>' + '<td>' + data[i].DeliveryNumber + '</td>' + '<td>' + data[i].Status + '</td>' + '<td>' + data[i].EstimatedPaymentTime + '</td>' + '<td>' + data[i].ActualPaymentTime + '</td>' + review_state1 + review_state2 + '<td> <input type="button" name="operate-update" value="修改" class="bToggle"></td>' + '</tr>';
                                    }

                                }
                                $('.operatePage tbody')
                                    .append(tr);
                            }
                            $('.cover-color').show();
                            $('.operate').show();
                        },
                        error: function() {
                            $.MsgBox.Alert("提示",
                                "服务器繁忙，稍后重试！");
                        }
                    });
            },
            error: function() {
                $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
            }
        });

    }
);

// -------------------------------------点击修改日期------------------------------------------

$(document).on("click", "input[name='update_date']", function() {
	$(this).siblings().show();
	$(this).parent().find("input[type='date']").removeAttr("disabled");
	$(this).hide();
});
$(document)
    .on(
        "click",
        "input[name='submit_date']",
        function() {
            var current = $(this);
            var order_id = $(
                    '.operate .update_date input[name="order_id"]')
                .val();
            var actual_delivery = $(
                    '.operate .update_date input[name="actual_delivery"]')
                .val();
            var expected_delivery_period = $(
                    '.operate .update_date input[name="expected_delivery_period"]')
                .val();

            $.ajax({
                type: 'get',
                url: 'ModifyOrderLogisticsDate',
                data: {
                    order_id: order_id,
                    actual_delivery: actual_delivery,
                    expected_delivery_period: expected_delivery_period
                },
                dataType: 'json',
                success: function(data) {
                    var tr = $('#table1').find(
                            '.operate-edit[value="' + order_id + '"]')
                        .parent().parent();
                    tr.find('td').eq(11).html(data[1].ActualDelivery);
                    tr.find('td').eq(12).html(
                        data[1].ExpectedDeliveryPeriod);
                    current.parent().find(
                        "input[name='actual_delivery']").val(
                        data[1].ActualDelivery);
                    current.parent().find(
                            "input[name='expected_delivery_period']")
                        .val(data[1].ExpectedDeliveryPeriod);
                    current.siblings().show();
                    current.parent().find("input[type='date']").attr(
                        "disabled", true);
                    current.hide();
                },
                error: function() {
                    $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
                }
            });

        });


// -------------------------------------点击修改合同状态------------------------------------------

$(document).on("click", "input[name='update_state']", function() {
	$(this).siblings().show();
	$(this).parent().find("select[name='status']").removeAttr("disabled");
	$(this).hide();
});
$(document)
    .on(
        "click",
        "input[name='submit_state']",
        function() {
            var current = $(this);
            var order_id = $(
                    '.operate .update_date input[name="order_id"]')
                .val();
            var status = $(
                    '.operate .update_date select[name="status"]')
                .val();

            $.ajax({
                type: 'get',
                url: 'ModifyContractStatus',
                data: {
                    order_id: order_id,
                    status: status
                },
                dataType: 'json',
                success: function(data) {
                    var tr = $('#table1').find(
                            '.operate-edit[value="' + order_id + '"]')
                        .parent().parent();
                    tr.find('td').eq(13).html(data[1].Status);
                    current.parent().find("input[name='status']").val(
                        data[1].Status);
                    current.siblings().show();
                    current.parent().find("select[name='status']")
                        .attr("disabled", true);
                    current.hide();
                },
                error: function() {
                    $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
                }
            });

        });


var operateMark; // 点击修改某条记录 判断是第一条或者其他
var operateMarkSet = [];
// -------------------------------------点击修改某条记录------------------------------------------
$(document).on("click", "input[name='operate-update']", function() {
    var tr = $(this).parent().parent();
    var trIndex = tr.index();
    if (trIndex == 0) {
        operateMark = 0.1;
    } else {
        operateMark = trIndex;
    }
    var bottom_table = $('.bottom_table');
    tr.parent().find("input[name='operate-update']").addClass(
        'bToggle');
    tr.parent().find("input[name='operate-update']")
        .removeAttr("disabled");
    tr.parent().find("input[name='operate-check']").addClass(
        'bToggle');
    tr.parent().find("input[name='operate-check']").removeAttr(
        "disabled");
    $(this).attr('disabled', 'true');
    $(this).removeClass('bToggle');
    bottom_table.find('input[name="id"]').val(
        tr.find('td').eq(0).html());
    bottom_table.find('input[name="order_id"]').val(
        tr.find('td').eq(1).html());
    if (tr.find('td').eq(2).html() != '' && tr.find('td').eq(2).html() != '--') {
        var iival = tr.find('td').eq(2).html();
        // bottom_table.find('select[name="supplier"]').val();
        bottom_table.find('select[name="supplier"]').find(
            'option[text="' + iival + '"]').prop("selected", true);
    } else {
        bottom_table.find('select[name="supplier"]').find(
            'option[text=""]').prop("selected", true);
    }
    bottom_table.find('input[name="po_no"]').val(
        tr.find('td').eq(3).html());
    bottom_table.find('input[name="so_no"]').val(
        tr.find('td').eq(4).html());
    bottom_table.find('input[name="po_amount"]').val(
        tr.find('td').eq(5).html());
    bottom_table.find('input[name="rmb_po_amount"]').val(
        tr.find('td').eq(6).html());
    bottom_table.find('input[name="factory_shipment"]').val(
        tr.find('td').eq(7).html());
    bottom_table.find('input[name="logistics_number"]').val(
        tr.find('td').eq(11).html());
    bottom_table.find('input[name="stock_number"]').val(
        tr.find('td').eq(12).html());
    bottom_table.find('input[name="date"]').val(
        tr.find('td').eq(13).html());
    bottom_table.find('input[name="except_date"]').val(
        tr.find('td').eq(14).html());
    bottom_table.find('input[name="delivery_number"]').val(
        tr.find('td').eq(15).html());

    if (tr.find('td').eq(16).html() != '' && tr.find('td').eq(16).html() != '--') {
        bottom_table.find('select[name="status"]').find(
            'option[text=' + tr.find('td').eq(16).html() + ']').prop("selected", true);
    } else {
        bottom_table.find('select[name="status"]').find(
            'option[text=""]').prop("selected", true);
    }
    bottom_table.find('input[name="estimated_payment_time"]')
        .val(tr.find('td').eq(17).html());
    bottom_table.find('input[name="actual_payment_time"]').val(
        tr.find('td').eq(18).html());
    bottom_table.show();
    $('.check_table').hide();

});


// -------------------------------------点击审核某条记录------------------------------------------
$(document)
    .on(
        "click",
        "input[name='operate-check']",
        function() {
            var user_id = $('#userID').val();
            var check1 = $('#review_name1').val();
            var check2 = $('#review_name2').val();
            var tr = $(this).parent().parent();
            var check_table = $('.check_table');
            tr.parent().find("input[name='operate-update']").addClass(
                'bToggle');
            tr.parent().find("input[name='operate-update']")
                .removeAttr("disabled");
            tr.parent().find("input[name='operate-check']").addClass(
                'bToggle');
            tr.parent().find("input[name='operate-check']").removeAttr(
                "disabled");
            $(this).attr('disabled', 'true');
            $(this).removeClass('bToggle');
            check_table.find('input[name="id"]').val(
                tr.find('td').eq(0).html());
            check_table.find('input[name="order_id"]').val(
                tr.find('td').eq(1).html());
            check_table.find('select[name="review1"]').find(
                'option[value=' + tr.find('td').eq(19).find('span').html() + ']').prop("selected", true);
            check_table.find('select[name="review2"]').find(
                'option[value=' + tr.find('td').eq(20).find('span').html() + ']').prop("selected", true);
            $('select[name="review1"]').removeAttr("disabled");
            $('select[name="review2"]').removeAttr("disabled");
            if (check1 == "OrderInfoReview1") {
                $('select[name="review2"]').attr('disabled', 'true');

            } else if (check2 == "OrderInfoReview2") {
                $('select[name="review1"]').attr('disabled', 'true');
            } else {
                $('select[name="review1"]').attr('disabled', 'true');
                $('select[name="review2"]').attr('disabled', 'true');
            }
            check_table.show();
            $('.bottom_table').hide();

        });

$('.operate_close').click(function() {
	operateMarkSet = [];
	$('.cover-color, .operate, .bottom_table, .check_table').hide();
	window.location.reload();
});

$('#edit_cancel').click(
		function() {
			$('.bottom_table').hide();
			$(this).parent().parent().parent().find(
					"input[name='operate-update']").addClass('bToggle');
			$(this).parent().parent().parent().find(
					"input[name='operate-update']").removeAttr("disabled");
		});

$('#check_cancel').click(
		function() {
			$('.check_table').hide();
			$(this).parent().parent().parent().find(
					"input[name='operate-check']").addClass('bToggle');
			$(this).parent().parent().parent().find(
					"input[name='operate-check']").removeAttr("disabled");
		});

// 判断价格信息是否为空
$('.editPage input[name="rmb_po_amount"]').blur(
    function() {
        if ($(this).val() != '' && $(this).val() != 0 && $(this).val() != '--') {
            if ($('.editPage input[name="po_amount"]').val() != '' && $('.editPage input[name="po_amount"]').val() != 0) {
                $('.editPage input[name="po_amount"]').val(0);
            }
        }
    });

$('.editPage input[name="po_amount"]')
    .blur(
        function() {
            if ($(this).val() != '' && $(this).val() != 0 && $(this).val() != '--') {
                if ($('.editPage input[name="rmb_po_amount"]').val() != '' && $('.editPage input[name="rmb_po_amount"]')
                    .val() != 0) {
                    $('.editPage input[name="rmb_po_amount"]').val(0);
                }
            }
        });


// 动态计算日期
function GetDateStr(AddDayCount) {
	var dd = new Date($('.editPage input[name="factory_shipment"]').val()
			.replace(/\-/g, "\/"));

	dd.setDate(dd.getDate() + AddDayCount);
	var y = dd.getFullYear();
	var m = (dd.getMonth() + 1) < 10 ? "0" + (dd.getMonth() + 1) : (dd
			.getMonth() + 1);
	var d = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate();
	return y + "-" + m + "-" + d;
}
$(document).on("blur", '.editPage input[name="factory_shipment"]', function() {
	if ($('#order_type').val() == 'RMB') {
		$('.usd').hide();
		$('.rmb').show();
		$('.editPage input[name="except_date"]').val(GetDateStr(15));
	} else {
		$('.usd').show();
		$('.rmb').hide();
		$('.editPage input[name="except_date"]').val(GetDateStr(7));
	}
});

// -------------------------------------提交修改内容------------------------------------------
$(document).on("click", "#edit_submit", function() {
            var columns = [];
            var user_id = $('#userID').val();
            var check1 = $('#review_name1').val();
            var check2 = $('#review_name2').val();

            var order_id = $('.bottom_table input[name="order_id"]')
                .val() == "" ? "--" : $(
                    '.bottom_table input[name="order_id"]').val();
            var supplier = $('.bottom_table select[name="supplier"]')
                .val();
            var supplierText = $(
                    '.bottom_table select[name="supplier"] option:checked')
                .text();
            var po_no = $('.bottom_table input[name="po_no"]').val() == "" ? "--" : $('.bottom_table input[name="po_no"]').val();
            var so_no = $('.bottom_table input[name="so_no"]').val() == "" ? "--" : $('.bottom_table input[name="so_no"]').val();
            var po_amount = $('.bottom_table input[name="po_amount"]').val();
            if(po_amount == "" || po_amount == "--"){
            	po_amount = "0";
            }
            var rmb_po_amount = $('.bottom_table input[name="rmb_po_amount"]').val();
            if(rmb_po_amount == "" || rmb_po_amount == "--"){
            	rmb_po_amount = "0";
            }
            var factory_shipment = $('.bottom_table input[name="factory_shipment"]').val() == "" ? "--" : $('.bottom_table input[name="factory_shipment"]').val();
            var logistics_number = $('.bottom_table input[name="logistics_number"]').val() == "" ? "--" : $('.bottom_table input[name="logistics_number"]').val();
            /*
             * var stock_number=$('.bottom_table
             * input[name="stock_number"]').val() == "" ? "--"
             * :$('.bottom_table input[name="stock_number"]').val();
             */
            var date = $('.bottom_table input[name="date"]').val();
            var except_date = $('.bottom_table input[name="except_date"]').val();
            var delivery_number = $('.bottom_table input[name="delivery_number"]').val() == "" ? "--" : $('.bottom_table input[name="delivery_number"]').val();
            var status = $('.bottom_table select[name="status"]').val();
            var statusText = $('.bottom_table select[name="status"] option:checked').text();
            var estimated_payment_time = $(
                    '.bottom_table input[name="estimated_payment_time"]')
                .val();
            var actual_payment_time = $(
                    '.bottom_table input[name="actual_payment_time"]')
                .val();

            var IsChangeIndex = (operateMark == 0.1 ? 1 : operateMark);

            var changeDom = $(".operatePage tr").eq(IsChangeIndex).children();
            changeDom.eq(2).text() != (supplierText == "" ? "" : supplierText) ? columns.push("Supplier") : null;
            changeDom.eq(3).text() != po_no ? columns.push("PONO") : null;
            changeDom.eq(4).text() != so_no ? columns.push("SONO") : null;
            changeDom.eq(5).text() != po_amount ? columns
                .push("POAmount") : null;
            changeDom.eq(6).text() != rmb_po_amount ? columns
                .push("RMBPOAmount") : null;
            changeDom.eq(7).text() != factory_shipment ? columns
                .push("FactoryShipment") : null;
            changeDom.eq(11).text() != logistics_number ? columns
                .push("LogisticsNumber") : null;
            changeDom.eq(13).text() != (date == "" ? "--" : date) ? columns
                .push("Date") : null;
            changeDom.eq(14).text() != (except_date == "" ? "--" : except_date) ? columns.push("ExceptDate") : null;
            changeDom.eq(15).text() != delivery_number ? columns
                .push("DeliveryNumber") : null;
                /*00000*/
            changeDom.eq(16).text() != (statusText == "" ? "--" : statusText) ? columns.push("Status") : null;
            changeDom.eq(17).text() != (estimated_payment_time == "" ? "--" : estimated_payment_time) ? columns
                .push("EstimatedPaymentTime") : null;
            changeDom.eq(18).text() != (actual_payment_time == "" ? "--" : actual_payment_time) ? columns
                .push("ActualPaymentTime") : null;
            columns = columns.unique();
            var dataSet = new Object();

            if (operateMark == 0.1) // 第一条数据修改
            {
                var edit_submit_url = "ModifyOrderInfo";
                var operatePageLength = $(".operatePage tbody tr").length;
                var id = "";
                for (var i = 0; i < operatePageLength; i++) {
                    id += $(".operatePage tbody tr").eq(i).find('td')
                        .eq(0).text() + ",";
                }
                dataSet.id = id;
                dataSet.order_id = order_id;
                dataSet.columns = columns;
                $.inArray("Supplier", columns) >= 0 ? (dataSet.supplier = supplier) : null;
                $.inArray("PONO", columns) >= 0 ? (dataSet.po_no = po_no) : null;
                $.inArray("SONO", columns) >= 0 ? (dataSet.so_no = so_no) : null;
                $.inArray("POAmount", columns) >= 0 ? (dataSet.po_amount = po_amount) : null;
                $.inArray("RMBPOAmount", columns) >= 0 ? (dataSet.rmb_po_amount = rmb_po_amount) : null;
                $.inArray("FactoryShipment", columns) >= 0 ? (dataSet.factory_shipment = factory_shipment) : null;
                $.inArray("LogisticsNumber", columns) >= 0 ? (dataSet.logistics_number = logistics_number) : null;
                $.inArray("Date", columns) >= 0 ? (dataSet.date = date) : null;
                $.inArray("ExceptDate", columns) >= 0 ? (dataSet.except_date = except_date) : null;
                $.inArray("DeliveryNumber", columns) >= 0 ? (dataSet.delivery_number = delivery_number) : null;
                $.inArray("Status", columns) >= 0 ? (dataSet.status = status) : null;
                $.inArray("EstimatedPaymentTime", columns) >= 0 ? (dataSet.estimated_payment_time = estimated_payment_time) : null;
                $.inArray("ActualPaymentTime", columns) >= 0 ? (dataSet.actual_payment_time = actual_payment_time) : null;
            } else // 不是第一条数据修改
            {
                var edit_submit_url = "ModifyOrderInfoAlone";
                var id = $('.bottom_table input[name="id"]').val();
                dataSet = {
                    id: id,
                    order_id: order_id,
                    supplier: supplier,
                    po_no: po_no,
                    so_no: so_no,
                    po_amount: po_amount,
                    rmb_po_amount: rmb_po_amount,
                    factory_shipment: factory_shipment,
                    logistics_number: logistics_number,
                    date: date,
                    except_date: except_date,
                    delivery_number: delivery_number,
                    status: status,
                    estimated_payment_time: estimated_payment_time,
                    actual_payment_time: actual_payment_time
                }
            }

            console.log(dataSet);

            $.ajax({
                    type: 'get',
                    url: edit_submit_url,
                    /*
                     * data : { id : id, order_id : order_id,
                     * supplier : supplier, po_no : po_no, so_no :
                     * so_no, po_amount : po_amount, rmb_po_amount :
                     * rmb_po_amount, factory_shipment :
                     * factory_shipment, logistics_number :
                     * logistics_number, stock_number:stock_number,
                     * date : date, except_date : except_date,
                     * delivery_number : delivery_number, status :
                     * status, estimated_payment_time :
                     * estimated_payment_time, actual_payment_time :
                     * actual_payment_time },
                     */
                    data: dataSet,
                    dataType: 'json',
                    success: function(data) {
                        data = eval("(" + data + ")");
                        if (data.message) {
                            $.MsgBox.Alert("提示", "修改成功！");
                            $.ajax({
                                    type: 'get',
                                    url: 'GetOrderInfoAndLogisc',
                                    data: {
                                        order_id: order_id
                                    },
                                    dataType: 'json',
                                    success: function(data) {

                                        $('.operatePage tbody').html("");
                                        var tr;
                                        for (var i = 1; i < data.length; i++) {
                                            var notFirstVal = "";
                                            if (operateMark == i - 1 || operateMark == 0.1) {
                                                operateMarkSet.push(operateMark);
                                            }
                                            notFirstVal = '<input type="button" name="operate-update" value="修改"  class="bToggle">'

                                            var FactoryShipment = (data[i].FactoryShipment == "0000-00-00" ? "--" : data[i].FactoryShipment);
                                            var review_state1, review_state1;
                                            if (data[i].Review1 == '0') {
                                                review_state1 = '<td>未审核<span style="display:none">' + data[i].Review1 + '</span></td>';
                                            } else if (data[i].Review1 == '1') {
                                                review_state1 = '<td>不通过<br>（' + data[i].ReviewName1 + '）<span style="display:none">' + data[i].Review1 + '</span></td>';
                                            } else {
                                                review_state1 = '<td>通过<br>（' + data[i].ReviewName1 + '）<span style="display:none">' + data[i].Review1 + '</span></td>';
                                            }
                                            if (data[i].Review2 == '0') {
                                                review_state2 = '<td>未审核<span style="display:none">' + data[i].Review2 + '</span></td>';
                                            } else if (data[i].Review2 == '1') {
                                                review_state2 = '<td>不通过<br>（' + data[i].ReviewName2 + '）<span style="display:none">' + data[i].Review2 + '</span></td>';
                                            } else {
                                                review_state2 = '<td>通过<br>（' + data[i].ReviewName2 + '）<span style="display:none">' + data[i].Review2 + '</span></td>';
                                            }
                                            if (data[i].Review1 == '1' || data[i].Review2 == '1') {
                                                if (check1 == "OrderInfoReview1" || check2 == "OrderInfoReview2") {
                                                    tr = '<tr class="change current_line">' + '<td style="display: none">' + data[i].ID + '</td>' + '<td style="display: none">' + data[i].OrderID + '</td>' + '<td>' + data[i].Name + '</td>' + '<td>' + data[i].PONO + '</td>' + '<td>' + data[i].SONO + '</td>' + '<td>' + data[i].POAmount + '</td>' + '<td>' + data[i].RMBPOAmount + '</td>' + '<td>' + FactoryShipment + '</td>' + '<td>' + data[i].EquipmentModel + '</td>' + '<td>' + data[i].Remarks + '</td>' + '<td>' + data[i].Number + '</td>' + '<td>' + data[i].LogisticsNumber + '</td>' + '<td>' + data[i].InventoryQuantity + '</td>' + '<td>' + data[i].Date + '</td>' + '<td>' + data[i].ExceptDate + '</td>' + '<td>' + data[i].DeliveryNumber + '</td>' + '<td>' + data[i].Status + '</td>' + '<td>' + data[i].EstimatedPaymentTime + '</td>' + '<td>' + data[i].ActualPaymentTime + '</td>' + review_state1 + review_state2 + '<td style="background-color:white;">' + notFirstVal + '<br><input type="button" name="operate-check" value="审核" class="bToggle"></td>' + '</tr>';
                                                } else {
                                                    tr = '<tr class="change current_line">' + '<td style="display: none">' + data[i].ID + '</td>' + '<td style="display: none">' + data[i].OrderID + '</td>' + '<td>' + data[i].Name + '</td>' + '<td>' + data[i].PONO + '</td>' + '<td>' + data[i].SONO + '</td>' + '<td>' + data[i].POAmount + '</td>' + '<td>' + data[i].RMBPOAmount + '</td>' + '<td>' + FactoryShipment + '</td>' + '<td>' + data[i].EquipmentModel + '</td>' + '<td>' + data[i].Remarks + '</td>' + '<td>' + data[i].Number + '</td>' + '<td>' + data[i].LogisticsNumber + '</td>' + '<td>' + data[i].InventoryQuantity + '</td>' + '<td>' + data[i].Date + '</td>' + '<td>' + data[i].ExceptDate + '</td>' + '<td>' + data[i].DeliveryNumber + '</td>' + '<td>' + data[i].Status + '</td>' + '<td>' + data[i].EstimatedPaymentTime + '</td>' + '<td>' + data[i].ActualPaymentTime + '</td>' + review_state1 + review_state2 + '<td style="background-color:white;">' + notFirstVal + '<br></td>' + '</tr>';
                                                }

                                            } else {

                                                if (check1 == "OrderInfoReview1" || check2 == "OrderInfoReview2") {
                                                    tr = '<tr>' + '<td style="display: none">' + data[i].ID + '</td>' + '<td style="display: none">' + data[i].OrderID + '</td>' + '<td>' + data[i].Name + '</td>' + '<td>' + data[i].PONO + '</td>' + '<td>' + data[i].SONO + '</td>' + '<td>' + data[i].POAmount + '</td>' + '<td>' + data[i].RMBPOAmount + '</td>' + '<td>' + FactoryShipment + '</td>' + '<td>' + data[i].EquipmentModel + '</td>' + '<td>' + data[i].Remarks + '</td>' + '<td>' + data[i].Number + '</td>' + '<td>' + data[i].LogisticsNumber + '</td>' + '<td>' + data[i].InventoryQuantity + '</td>' + '<td>' + data[i].Date + '</td>' + '<td>' + data[i].ExceptDate + '</td>' + '<td>' + data[i].DeliveryNumber + '</td>' + '<td>' + data[i].Status + '</td>' + '<td>' + data[i].EstimatedPaymentTime + '</td>' + '<td>' + data[i].ActualPaymentTime + '</td>' + review_state1 + review_state2 + '<td> ' + notFirstVal + '<br>' + '<input type="button" name="operate-check" value="审核" class="bToggle"></td>' + '</tr>';
                                                } else {
                                                    tr = '<tr>' + '<td style="display: none">' + data[i].ID + '</td>' + '<td style="display: none">' + data[i].OrderID + '</td>' + '<td>' + data[i].Name + '</td>' + '<td>' + data[i].PONO + '</td>' + '<td>' + data[i].SONO + '</td>' + '<td>' + data[i].POAmount + '</td>' + '<td>' + data[i].RMBPOAmount + '</td>' + '<td>' + FactoryShipment + '</td>' + '<td>' + data[i].EquipmentModel + '</td>' + '<td>' + data[i].Remarks + '</td>' + '<td>' + data[i].Number + '</td>' + '<td>' + data[i].LogisticsNumber + '</td>' + '<td>' + data[i].InventoryQuantity + '</td>' + '<td>' + data[i].Date + '</td>' + '<td>' + data[i].ExceptDate + '</td>' + '<td>' + data[i].DeliveryNumber + '</td>' + '<td>' + data[i].Status + '</td>' + '<td>' + data[i].EstimatedPaymentTime + '</td>' + '<td>' + data[i].ActualPaymentTime + '</td>' + review_state1 + review_state2 + '<td> ' + notFirstVal + '</td>' + '</tr>';
                                                }

                                            }
                                            $('.operatePage tbody').append(tr);
                                        }
                                        $('.cover-color, .operate').show();
                                        // for(var m = 1; m <
                                        // operateMarkSet.length;
                                        // m++){
                                        // $(".operatePage
                                        // tr").eq(operateMarkSet[m]).children().eq(21).find('input[name="operate-update"]').css("background","red");
                                        // }

                                    },
                                    error: function() {
                                        $.MsgBox.Alert("提示",
                                            "服务器繁忙，稍后重试！");
                                    },
                                    complete: function() {
                                        operateMarkSet = operateMarkSet.unique();
                                        for (var m = 0; m < operateMarkSet.length; m++) {
                                            if (operateMarkSet[m] == 0.1) {
                                                $(".operatePage tr")
                                                    .eq(1)
                                                    .children()
                                                    .eq(21)
                                                    .find('input[name="operate-update"]')
                                                    .css("background", "red");
                                            } else {
                                                $(".operatePage tbody tr")
                                                    .eq(operateMarkSet[m])
                                                    .children()
                                                    .eq(21)
                                                    .find('input[name="operate-update"]')
                                                    .css("background", "red");
                                            }

                                        }
                                    }
                                });

                        } else {
                            $.MsgBox.Alert('提示', "修改失败！");
                        }
                    },
                    error: function() {
                        $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
                        $('.bottom_table').hide();
                    }
                });
        });

// -------------------------------------提交审核内容------------------------------------------
$(document).on("click", "#check_submit", function() {
            var user_id = $('#userID').val();
            var check1 = $('#review_name1').val();
            var check2 = $('#review_name2').val();
            var id = $('.check_table input[name="id"]').val();
            var order_id = $('.check_table input[name="order_id"]')
                .val();
            var review1 = $('.check_table select[name="review1"]')
                .val();
            var review2 = $('.check_table select[name="review2"]')
                .val();
            if (check1 == "OrderInfoReview1") {
                $
                    .ajax({
                        type: 'get',
                        url: 'OrderInfoReview1',
                        data: {
                            user_id: user_id,
                            order_info_id: id,
                            review1: review1,
                            review_name1: user_id
                        },
                        dataType: 'json',
                        success: function(data) {
                            data = eval("(" + data + ")");
                            if (data.message) {
                                $.MsgBox.Alert("提示", "审核成功！");
                                $
                                    .ajax({
                                        type: 'get',
                                        url: 'GetOrderInfoAndLogisc',
                                        data: {
                                            order_id: order_id
                                        },
                                        dataType: 'json',
                                        success: function(data) {
                                            $(
                                                    '.operatePage tbody')
                                                .html("");
                                            var tr;
                                            for (var i = 1; i < data.length; i++) {
                                                var review_state1, review_state1;
                                                if (data[i].Review1 == '0') {
                                                    review_state1 = '<td>未审核<span style="display:none">' + data[i].Review1 + '</span></td>';
                                                } else if (data[i].Review1 == '1') {
                                                    review_state1 = '<td>不通过<br>（' + data[i].ReviewName1 + '）<span style="display:none">' + data[i].Review1 + '</span></td>';
                                                } else {
                                                    review_state1 = '<td>通过<br>（' + data[i].ReviewName1 + '）<span style="display:none">' + data[i].Review1 + '</span></td>';
                                                }
                                                if (data[i].Review2 == '0') {
                                                    review_state2 = '<td>未审核<span style="display:none">' + data[i].Review2 + '</span></td>';
                                                } else if (data[i].Review2 == '1') {
                                                    review_state2 = '<td>不通过<br>（' + data[i].ReviewName2 + '）<span style="display:none">' + data[i].Review2 + '</span></td>';
                                                } else {
                                                    review_state2 = '<td>通过<br>（' + data[i].ReviewName2 + '）<span style="display:none">' + data[i].Review2 + '</span></td>';
                                                }
                                                if (data[i].Review1 == '1' || data[i].Review2 == '1') {
                                                    if (check1 == "OrderInfoReview1" || check2 == "OrderInfoReview2") {
                                                        tr = '<tr class="change current_line">' + '<td style="display: none">' + data[i].ID + '</td>' + '<td style="display: none">' + data[i].OrderID + '</td>' + '<td>' + data[i].Name + '</td>' + '<td>' + data[i].PONO + '</td>' + '<td>' + data[i].SONO + '</td>' + '<td>' + data[i].POAmount + '</td>' + '<td>' + data[i].RMBPOAmount + '</td>' + '<td>' + data[i].FactoryShipment + '</td>' + '<td>' + data[i].EquipmentModel + '</td>' + '<td>' + data[i].Remarks + '</td>' + '<td>' + data[i].Number + '</td>' + '<td>' + data[i].LogisticsNumber + '</td>' + '<td>' + data[i].StockNumber + '</td>' + '<td>' + data[i].Date + '</td>' + '<td>' + data[i].ExceptDate + '</td>' + '<td>' + data[i].DeliveryNumber + '</td>' + '<td>' + data[i].Status + '</td>' + '<td>' + data[i].EstimatedPaymentTime + '</td>' + '<td>' + data[i].ActualPaymentTime + '</td>' + review_state1 + review_state2 + '<td style="background-color:white;"> <input type="button" name="operate-update" value="修改" class="bToggle"><br>' + '<input type="button" name="operate-check" value="审核" class="bToggle"></td>' + '</tr>';
                                                    } else {
                                                        tr = '<tr class="change current_line">' + '<td style="display: none">' + data[i].ID + '</td>' + '<td style="display: none">' + data[i].OrderID + '</td>' + '<td>' + data[i].Name + '</td>' + '<td>' + data[i].PONO + '</td>' + '<td>' + data[i].SONO + '</td>' + '<td>' + data[i].POAmount + '</td>' + '<td>' + data[i].RMBPOAmount + '</td>' + '<td>' + data[i].FactoryShipment + '</td>' + '<td>' + data[i].EquipmentModel + '</td>' + '<td>' + data[i].Remarks + '</td>' + '<td>' + data[i].Number + '</td>' + '<td>' + data[i].LogisticsNumber + '</td>' + '<td>' + data[i].StockNumber + '</td>' + '<td>' + data[i].Date + '</td>' + '<td>' + data[i].ExceptDate + '</td>' + '<td>' + data[i].DeliveryNumber + '</td>' + '<td>' + data[i].Status + '</td>' + '<td>' + data[i].EstimatedPaymentTime + '</td>' + '<td>' + data[i].ActualPaymentTime + '</td>' + review_state1 + review_state2 +

                                                            '<td style="background-color:white;"> <input type="button" name="operate-update" value="修改" class="bToggle"><br></td>' + '</tr>';
                                                    }

                                                } else {
                                                    if (check1 == "OrderInfoReview1" || check2 == "OrderInfoReview2") {
                                                        tr = '<tr>' + '<td style="display: none">' + data[i].ID + '</td>' + '<td style="display: none">' + data[i].OrderID + '</td>' + '<td>' + data[i].Name + '</td>' + '<td>' + data[i].PONO + '</td>' + '<td>' + data[i].SONO + '</td>' + '<td>' + data[i].POAmount + '</td>' + '<td>' + data[i].RMBPOAmount + '</td>' + '<td>' + data[i].FactoryShipment + '</td>' + '<td>' + data[i].EquipmentModel + '</td>' + '<td>' + data[i].Remarks + '</td>' + '<td>' + data[i].Number + '</td>' + '<td>' + data[i].LogisticsNumber + '</td>' + '<td>' + data[i].StockNumber + '</td>' + '<td>' + data[i].Date + '</td>' + '<td>' + data[i].ExceptDate + '</td>' + '<td>' + data[i].DeliveryNumber + '</td>' + '<td>' + data[i].Status + '</td>' + '<td>' + data[i].EstimatedPaymentTime + '</td>' + '<td>' + data[i].ActualPaymentTime + '</td>' + review_state1 + review_state2 + '<td> <input type="button" name="operate-update" value="修改" class="bToggle"><br>' + '<input type="button" name="operate-check" value="审核" class="bToggle"></td>' + '</tr>';
                                                    } else {
                                                        tr = '<tr>' + '<td style="display: none">' + data[i].ID + '</td>' + '<td style="display: none">' + data[i].OrderID + '</td>' + '<td>' + data[i].Name + '</td>' + '<td>' + data[i].PONO + '</td>' + '<td>' + data[i].SONO + '</td>' + '<td>' + data[i].POAmount + '</td>' + '<td>' + data[i].RMBPOAmount + '</td>' + '<td>' + data[i].FactoryShipment + '</td>' + '<td>' + data[i].EquipmentModel + '</td>' + '<td>' + data[i].Remarks + '</td>' + '<td>' + data[i].Number + '</td>' + '<td>' + data[i].LogisticsNumber + '</td>' + '<td>' + data[i].StockNumber + '</td>' + '<td>' + data[i].Date + '</td>' + '<td>' + data[i].ExceptDate + '</td>' + '<td>' + data[i].DeliveryNumber + '</td>' + '<td>' + data[i].Status + '</td>' + '<td>' + data[i].EstimatedPaymentTime + '</td>' + '<td>' + data[i].ActualPaymentTime + '</td>' + review_state1 + review_state2 + '<td> <input type="button" name="operate-update" value="修改" class="bToggle"></td>' + '</tr>';
                                                    }

                                                }
                                                $(
                                                        '.operatePage tbody')
                                                    .append(
                                                        tr);
                                            }
                                            $('.cover-color')
                                                .show();
                                            $('.operate')
                                                .show();
                                        },
                                        error: function() {
                                            $.MsgBox
                                                .Alert(
                                                    "提示",
                                                    "服务器繁忙，稍后重试！");
                                        }
                                    });

                            } else {
                                $.MsgBox.Alert('提示', "修改失败！");
                            }
                        },
                        error: function() {
                            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
                            $('.bottom_table').hide();
                        }
                    });
            } else if (check2 == "OrderInfoReview2") {
                $
                    .ajax({
                        type: 'get',
                        url: 'OrderInfoReview2',
                        data: {
                            user_id: user_id,
                            order_info_id: id,
                            review2: review2,
                            review_name2: user_id
                        },
                        dataType: 'json',
                        success: function(data) {
                            data = eval("(" + data + ")");
                            if (data.message) {
                                $.MsgBox.Alert("提示", "审核成功！");
                                $
                                    .ajax({
                                        type: 'get',
                                        url: 'GetOrderInfoAndLogisc',
                                        data: {
                                            order_id: order_id
                                        },
                                        dataType: 'json',
                                        success: function(data) {
                                            $(
                                                    '.operatePage tbody')
                                                .html("");
                                            var tr;
                                            for (var i = 1; i < data.length; i++) {
                                                var review_state1, review_state1;
                                                if (data[i].Review1 == '0') {
                                                    review_state1 = '<td>未审核<span style="display:none">' + data[i].Review1 + '</span></td>';
                                                } else if (data[i].Review1 == '1') {
                                                    review_state1 = '<td>不通过<br>（' + data[i].ReviewName1 + '）<span style="display:none">' + data[i].Review1 + '</span></td>';
                                                } else {
                                                    review_state1 = '<td>通过<br>（' + data[i].ReviewName1 + '）<span style="display:none">' + data[i].Review1 + '</span></td>';
                                                }
                                                if (data[i].Review2 == '0') {
                                                    review_state2 = '<td>未审核<span style="display:none">' + data[i].Review2 + '</span></td>';
                                                } else if (data[i].Review2 == '1') {
                                                    review_state2 = '<td>不通过<br>（' + data[i].ReviewName2 + '）<span style="display:none">' + data[i].Review2 + '</span></td>';
                                                } else {
                                                    review_state2 = '<td>通过<br>（' + data[i].ReviewName2 + '）<span style="display:none">' + data[i].Review2 + '</span></td>';
                                                }
                                                if (data[i].Review1 == '1' || data[i].Review2 == '1') {
                                                    if (check1 == "OrderInfoReview1" || check2 == "OrderInfoReview2") {
                                                        tr = '<tr class="change current_line">' + '<td style="display: none">' + data[i].ID + '</td>' + '<td style="display: none">' + data[i].OrderID + '</td>' + '<td>' + data[i].Name + '</td>' + '<td>' + data[i].PONO + '</td>' + '<td>' + data[i].SONO + '</td>' + '<td>' + data[i].POAmount + '</td>' + '<td>' + data[i].RMBPOAmount + '</td>' + '<td>' + data[i].FactoryShipment + '</td>' + '<td>' + data[i].EquipmentModel + '</td>' + '<td>' + data[i].Remarks + '</td>' + '<td>' + data[i].Number + '</td>' + '<td>' + data[i].LogisticsNumber + '</td>' + '<td>' + data[i].StockNumber + '</td>' + '<td>' + data[i].Date + '</td>' + '<td>' + data[i].ExceptDate + '</td>' + '<td>' + data[i].DeliveryNumber + '</td>' + '<td>' + data[i].Status + '</td>' + '<td>' + data[i].EstimatedPaymentTime + '</td>' + '<td>' + data[i].ActualPaymentTime + '</td>' + review_state1 + review_state2 + '<td style="background-color:white;"> <input type="button" name="operate-update" value="修改" class="bToggle"><br>' + '<input type="button" name="operate-check" value="审核" class="bToggle"></td>' + '</tr>';
                                                    } else {
                                                        tr = '<tr class="change current_line">' + '<td style="display: none">' + data[i].ID + '</td>' + '<td style="display: none">' + data[i].OrderID + '</td>' + '<td>' + data[i].Name + '</td>' + '<td>' + data[i].PONO + '</td>' + '<td>' + data[i].SONO + '</td>' + '<td>' + data[i].POAmount + '</td>' + '<td>' + data[i].RMBPOAmount + '</td>' + '<td>' + data[i].FactoryShipment + '</td>' + '<td>' + data[i].EquipmentModel + '</td>' + '<td>' + data[i].Remarks + '</td>' + '<td>' + data[i].Number + '</td>' + '<td>' + data[i].LogisticsNumber + '</td>' + '<td>' + data[i].StockNumber + '</td>' + '<td>' + data[i].Date + '</td>' + '<td>' + data[i].ExceptDate + '</td>' + '<td>' + data[i].DeliveryNumber + '</td>' + '<td>' + data[i].Status + '</td>' + '<td>' + data[i].EstimatedPaymentTime + '</td>' + '<td>' + data[i].ActualPaymentTime + '</td>' + review_state1 + review_state2 +

                                                            '<td style="background-color:white;"> <input type="button" name="operate-update" value="修改" class="bToggle"><br></td>' + '</tr>';
                                                    }

                                                } else {
                                                    if (check1 == "OrderInfoReview1" || check2 == "OrderInfoReview2") {
                                                        tr = '<tr>' + '<td style="display: none">' + data[i].ID + '</td>' + '<td style="display: none">' + data[i].OrderID + '</td>' + '<td>' + data[i].Name + '</td>' + '<td>' + data[i].PONO + '</td>' + '<td>' + data[i].SONO + '</td>' + '<td>' + data[i].POAmount + '</td>' + '<td>' + data[i].RMBPOAmount + '</td>' + '<td>' + data[i].FactoryShipment + '</td>' + '<td>' + data[i].EquipmentModel + '</td>' + '<td>' + data[i].Remarks + '</td>' + '<td>' + data[i].Number + '</td>' + '<td>' + data[i].LogisticsNumber + '</td>' + '<td>' + data[i].StockNumber + '</td>' + '<td>' + data[i].Date + '</td>' + '<td>' + data[i].ExceptDate + '</td>' + '<td>' + data[i].DeliveryNumber + '</td>' + '<td>' + data[i].Status + '</td>' + '<td>' + data[i].EstimatedPaymentTime + '</td>' + '<td>' + data[i].ActualPaymentTime + '</td>' + review_state1 + review_state2 + '<td> <input type="button" name="operate-update" value="修改" class="bToggle"><br>' + '<input type="button" name="operate-check" value="审核" class="bToggle"></td>' + '</tr>';
                                                    } else {
                                                        tr = '<tr>' + '<td style="display: none">' + data[i].ID + '</td>' + '<td style="display: none">' + data[i].OrderID + '</td>' + '<td>' + data[i].Name + '</td>' + '<td>' + data[i].PONO + '</td>' + '<td>' + data[i].SONO + '</td>' + '<td>' + data[i].POAmount + '</td>' + '<td>' + data[i].RMBPOAmount + '</td>' + '<td>' + data[i].FactoryShipment + '</td>' + '<td>' + data[i].EquipmentModel + '</td>' + '<td>' + data[i].Remarks + '</td>' + '<td>' + data[i].Number + '</td>' + '<td>' + data[i].LogisticsNumber + '</td>' + '<td>' + data[i].StockNumber + '</td>' + '<td>' + data[i].Date + '</td>' + '<td>' + data[i].ExceptDate + '</td>' + '<td>' + data[i].DeliveryNumber + '</td>' + '<td>' + data[i].Status + '</td>' + '<td>' + data[i].EstimatedPaymentTime + '</td>' + '<td>' + data[i].ActualPaymentTime + '</td>' + review_state1 + review_state2 + '<td> <input type="button" name="operate-update" value="修改" class="bToggle"></td>' + '</tr>';
                                                    }

                                                }
                                                $(
                                                        '.operatePage tbody')
                                                    .append(
                                                        tr);
                                            }
                                            $('.cover-color')
                                                .show();
                                            $('.operate')
                                                .show();

                                        },
                                        error: function() {
                                            $.MsgBox
                                                .Alert(
                                                    "提示",
                                                    "服务器繁忙，稍后重试！");
                                        }
                                    });

                            } else {
                                $.MsgBox.Alert('提示', "修改失败！");
                            }
                        },
                        error: function() {
                            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
                            $('.bottom_table').hide();
                        }
                    });
            } else {
                $.MsgBox.Alert("提示", "没有修改权限！");
            }

        });


// ------------------------------隐藏列-------------------------------------
$('#fa-button1').on('click', function() {
	$(this).toggleClass('glyphicon-plus glyphicon-minus');
	$('#table1 tbody tr').find('td:eq(6)').toggle();
	$('#table1 tbody tr').find('td:eq(7)').toggle();
	$('#table1 tbody tr').find('td:eq(8)').toggle();
	$('#table1 tbody tr').find('td:eq(9)').toggle();
	$('#table1 tbody tr').find('td:eq(10)').toggle();
	$(".Customer_th, .Contact_th, .ContactInfo_th, .SalesRepresentative_th, .DateOfSign_th").toggle();
});
$('#fa-button2').on('click', function() {
	$(this).toggleClass('glyphicon-plus glyphicon-minus');
	$('#table1 tbody tr').find('td:eq(16)').toggle();
	$('#table1 tbody tr').find('td:eq(17)').toggle();
	$(".InstalledTimeSite_th, .Remarks_th").toggle();
});
$('#fa-button3').on('click', function() {
	$(this).toggleClass('glyphicon-plus glyphicon-minus');
	$(".D3_D2_th, .D3_D4_th, .D4_D2_th, .D3_D1_th, .D3_D2, .D3_D4, .D4_D2, .D3_D1").toggle();
});

// --------------------------------------------点击 预计货期
// 触发事件-----------------------------------------------------

// 合同货期
$("#hetonghuoqiSel").on("change", function() {
					var ActualDelivery = window.location.href.split("ActualDelivery=")[1].split("&")[0];
					var url = window.location.href.split("/")[4].split("?")[0];
					var AllNoSend = $('.AllNoSend').val();
					var OtherNoSend = $('.OtherNoSend').val();
					var currentVal = $(this).val();
					if (currentVal == "AllNoSend") {
						if (url == "GetOrderRoute") {
							var newUrl = document.getElementById("top_text_from").action+ "?ActualDelivery=yes&column=CargoPeriod&condition=AllNoSend";
							$('#top_text_from').attr('action', newUrl);
							$('#top_text_from').submit();
						} else {
							$.ajax({
								type : 'get',
								url : 'Transport',
								data : {
									condition : 'AllNoSend',
									column : "CargoPeriod",
									ActualDelivery : "yes"
								},
								success : function(data) {
									var oldUrlParam = ecDo.getUrlPrmt(window.location.href);
									oldUrlParam.currentPage = "1";
									oldUrlParam.ActualDelivery = "yes";
									oldUrlParam.column = "CargoPeriod";
									oldUrlParam.condition = "AllNoSend";
									eouluGlobal.S_settingURLParam(oldUrlParam, false, false, false);
									$("#hetonghuoqiSel").val(currentVal);
									// currentHref = window.location.href.split("ActualDelivery=")[0];
									// window.location.href = currentHref+ "ActualDelivery=yes&column=CargoPeriod&condition=AllNoSend";
									// $("#table1 tr")
									// 		.children()
									// 		.eq(11)
									// 		.find('select')
									// 		.find(
									// 				"option[value='"
									// 						+ currentVal
									// 						+ "']")
									// 		.attr("selected", true)
									// 		.siblings().attr(
									// 				"selected", false);
									
								}
							});
						}

					} else if (currentVal == "OtherNoSend") {
						if (url == "GetOrderRoute") {
							var newUrl = document.getElementById("top_text_from").action+ "?ActualDelivery=yes&column=CargoPeriod&condition=OtherNoSend";
							$('#top_text_from').attr('action', newUrl);
							$('#top_text_from').submit();
						} else {
							$.ajax({
								type : 'get',
								url : 'Transport',
								data : {
									condition : 'OtherNoSend',
									column : "CargoPeriod",
									ActualDelivery : "yes"
								},
								success : function(data) {
									var oldUrlParam = ecDo.getUrlPrmt(window.location.href);
									oldUrlParam.currentPage = "1";
									oldUrlParam.ActualDelivery = "yes";
									oldUrlParam.column = "CargoPeriod";
									oldUrlParam.condition = "OtherNoSend";
									eouluGlobal.S_settingURLParam(oldUrlParam, false, false, false);
									$("#hetonghuoqiSel").val(currentVal);
								}
							});
						}

					} else if (currentVal == "All") {
						if (url == "GetOrderRoute") {
							var newUrl = document.getElementById("top_text_from").action + "?ActualDelivery=no&column=CargoPeriod&condition=All";
							$('#top_text_from').attr('action', newUrl);
							$('#top_text_from').submit();
						} else {
							$.ajax({
								type : 'get',
								url : 'Transport',
								data : {
									condition : 'All',
									column : "CargoPeriod",
									ActualDelivery : "no"
								},
								success : function(data) {
									var oldUrlParam = ecDo.getUrlPrmt(window.location.href);
									oldUrlParam.currentPage = "1";
									oldUrlParam.ActualDelivery = "no";
									oldUrlParam.column = "CargoPeriod";
									oldUrlParam.condition = "All";
									eouluGlobal.S_settingURLParam(oldUrlParam, false, false, false);
									$("#hetonghuoqiSel").val(currentVal);
								}
							});
						}

					}
				})
// 实际货期
$("#shijihuoqiSel").on("change", function() {
					var ActualDelivery = window.location.href.split("ActualDelivery=")[1].split("&")[0];
					var url = window.location.href.split("/")[4].split("?")[0];
					var AllNoSend = $('.AllNoSend').val();
					var OtherNoSend = $('.OtherNoSend').val();
					var currentVal = $(this).val();
					if (currentVal == "AllNoSend") {
						if (url == "GetOrderRoute") {
							var newUrl = document
									.getElementById("top_text_from").action
									+ "?ActualDelivery=yes&column=DateOfSign&condition=AllNoSend";
							$('#top_text_from').attr('action', newUrl);
							$('#top_text_from').submit();
						} else {
							$.ajax({
								type : 'get',
								url : 'Transport',
								data : {
									condition : 'AllNoSend',
									column : "DateOfSign",
									ActualDelivery : "yes"
								},
								success : function(data) {
									var oldUrlParam = ecDo.getUrlPrmt(window.location.href);
									oldUrlParam.currentPage = "1";
									oldUrlParam.ActualDelivery = "yes";
									oldUrlParam.column = "DateOfSign";
									oldUrlParam.condition = "AllNoSend";
									eouluGlobal.S_settingURLParam(oldUrlParam, false, false, false);
									$("#shijihuoqiSel").val(currentVal);
								}
							});
						}

					} else if (currentVal == "OtherNoSend") {
						if (url == "GetOrderRoute") {
							var newUrl = document
									.getElementById("top_text_from").action
									+ "?ActualDelivery=yes&column=DateOfSign&condition=OtherNoSend";
							$('#top_text_from').attr('action', newUrl);
							$('#top_text_from').submit();
						} else {
							$.ajax({
								type : 'get',
								url : 'Transport',
								data : {
									condition : 'OtherNoSend',
									column : "DateOfSign",
									ActualDelivery : "yes"
								},
								success : function(data) {
									var oldUrlParam = ecDo.getUrlPrmt(window.location.href);
									oldUrlParam.currentPage = "1";
									oldUrlParam.ActualDelivery = "yes";
									oldUrlParam.column = "DateOfSign";
									oldUrlParam.condition = "OtherNoSend";
									eouluGlobal.S_settingURLParam(oldUrlParam, false, false, false);
									$("#shijihuoqiSel").val(currentVal);
								}
							});
						}

					} else if (currentVal == "All") {
						if (url == "GetOrderRoute") {
							var newUrl = document
									.getElementById("top_text_from").action
									+ "?ActualDelivery=no&column=DateOfSign&condition=All";
							$('#top_text_from').attr('action', newUrl);
							$('#top_text_from').submit();
						} else {
							$.ajax({
								type : 'get',
								url : 'Transport',
								data : {
									condition : 'All',
									column : "DateOfSign",
									ActualDelivery : "no"
								},
								success : function(data) {
									var oldUrlParam = ecDo.getUrlPrmt(window.location.href);
									oldUrlParam.currentPage = "1";
									oldUrlParam.ActualDelivery = "no";
									oldUrlParam.column = "DateOfSign";
									oldUrlParam.condition = "All";
									eouluGlobal.S_settingURLParam(oldUrlParam, false, false, false);
									$("#shijihuoqiSel").val(currentVal);
								}
							});
						}

					}
				})
// 预计货期 ExpectedDeliveryPeriod
$("#yujihuoqiSel").on("change", function() {
					var ActualDelivery = window.location.href.split("ActualDelivery=")[1].split("&")[0];
					var url = window.location.href.split("/")[4].split("?")[0];
					var AllNoSend = $('.AllNoSend').val();
					var OtherNoSend = $('.OtherNoSend').val();
					var currentVal = $(this).val();
					if (currentVal == "AllNoSend") {
						if (url == "GetOrderRoute") {
							var newUrl = document
									.getElementById("top_text_from").action
									+ "?ActualDelivery=yes&column=ExpectedDeliveryPeriod&condition=AllNoSend";
							$('#top_text_from').attr('action', newUrl);
							$('#top_text_from').submit();
						} else {
							$.ajax({
								type : 'get',
								url : 'Transport',
								data : {
									condition : 'AllNoSend',
									column : "ExpectedDeliveryPeriod",
									ActualDelivery : "yes"
								},
								success : function(data) {
									var oldUrlParam = ecDo.getUrlPrmt(window.location.href);
									oldUrlParam.currentPage = "1";
									oldUrlParam.ActualDelivery = "yes";
									oldUrlParam.column = "ExpectedDeliveryPeriod";
									oldUrlParam.condition = "AllNoSend";
									eouluGlobal.S_settingURLParam(oldUrlParam, false, false, false);
									$("#yujihuoqiSel").val(currentVal);
								}
							});
						}

					} else if (currentVal == "OtherNoSend") {
						if (url == "GetOrderRoute") {
							var newUrl = document
									.getElementById("top_text_from").action
									+ "?ActualDelivery=yes&column=ExpectedDeliveryPeriod&condition=OtherNoSend";
							$('#top_text_from').attr('action', newUrl);
							$('#top_text_from').submit();
						} else {
							$.ajax({
								type : 'get',
								url : 'Transport',
								data : {
									condition : 'OtherNoSend',
									column : "ExpectedDeliveryPeriod",
									ActualDelivery : "yes"
								},
								success : function(data) {
									var oldUrlParam = ecDo.getUrlPrmt(window.location.href);
									oldUrlParam.currentPage = "1";
									oldUrlParam.ActualDelivery = "yes";
									oldUrlParam.column = "ExpectedDeliveryPeriod";
									oldUrlParam.condition = "OtherNoSend";
									eouluGlobal.S_settingURLParam(oldUrlParam, false, false, false);
									$("#yujihuoqiSel").val(currentVal);
								}
							});
						}

					} else if (currentVal == "All") {
						if (url == "GetOrderRoute") {
							var newUrl = document
									.getElementById("top_text_from").action
									+ "?ActualDelivery=no&column=ExpectedDeliveryPeriod&condition=All";
							$('#top_text_from').attr('action', newUrl);
							$('#top_text_from').submit();
						} else {
							$.ajax({
								type : 'get',
								url : 'Transport',
								data : {
									condition : 'All',
									column : "ExpectedDeliveryPeriod",
									ActualDelivery : "no"
								},
								success : function(data) {
									var oldUrlParam = ecDo.getUrlPrmt(window.location.href);
									oldUrlParam.currentPage = "1";
									oldUrlParam.ActualDelivery = "no";
									oldUrlParam.column = "ExpectedDeliveryPeriod";
									oldUrlParam.condition = "All";
									eouluGlobal.S_settingURLParam(oldUrlParam, false, false, false);
									$("#yujihuoqiSel").val(currentVal);
								}
							});
						}

					}

				});

// 点击发送邮件
$(".writeEmail").click(function() {
	$(".MailBar").show()
	$(".MailBar_cover_color").show();
})
$(".MailBar_close").click(function() {
	$(".MailBar_cover_color").hide();
	$(".MailBar").hide();
})

// 点击打开文件选择器
var EmailCont = $(".MailBar_content").val().replace(/\n|\r\n/g, "<br>");
// 选择文件之后执行上传
$('#Mail_Send').on('click', function() {
	console.log(ContractNo + "====" + ContractTitle)
	// $.ajaxFileUpload({
	// 	url : 'SendToBusiness',
	// 	secureuri : false,
	// 	fileElementId : 'Mail_fileToUpload',// file标签的id
	// 	dataType : 'JSON',// 返回数据的类型
	// 	data : {
	// 		"ContractNo" : ContractNo,
	// 		Content : EmailCont,
	// 		"ContractTitle" : ContractTitle
	// 	},// 一同上传的数据
	// 	success : function(data) {
	// 		$.MsgBox.Alert("提示", "发送成功！");
	// 		$(".MailBar").hide();
	// 		$(".MailBar_cover_color").hide();
	// 	},
	// 	error : function(e) {
	// 		alert("error");
	// 	}
	// });
	var FileData = $("#Mail_fileToUpload")[0].files;
	if(FileData.length == 0){
		$.MsgBox_Unload.Alert("提示","请选择文件！");
		return false;
	}
	var iThat = $(this);
	var formData = new FormData();
	formData.enctype="multipart/form-data";
	formData.append("ContractNo",ContractNo);
	formData.append("ContractTitle",ContractTitle);
	formData.append("Content",EmailCont);
	// var fileList = BiddingDocumentState.uploadFileList;
	// $.each(fileList, function(iname, ivalue){
	//     formData.append("file",ivalue);
	// });
	formData.append("file",FileData[0]);//append()里面的第一个参数file对应permission/upload里面的参数file  

	var option = {
		type: "POST",
		accept: "text/html; charset=UTF-8",
		// accept: "application/json; charset=utf-8",
		url: "SendToBusiness",
		dataType: "text"
		// dataType: "json"
	};
	var progressFn = null;
	var successFn = function(data){
		console.log(typeof data);
		if(data.indexOf("true")>-1){
			$.MsgBox.Alert("提示", "发送成功！");
			$(".MailBar").hide();
			$(".MailBar_cover_color").hide();
		}else{
			$.MsgBox_Unload.Alert("提示","发送失败！");
		}
	};
	var errorFn = function(){
		$.MsgBox_Unload.Alert("提示","服务器繁忙！");
	};
	var beforeSendFn = function(XMLHttpRequest){
		iThat.css("cursor","not-allowed").prop("disabled","disabled");
	};
	var completeFn = function(XMLHttpRequest, textStatus){
		iThat.css("cursor","pointer").prop("disabled",false);
	};
	globalUploadFiles(formData, option, progressFn, successFn, errorFn, beforeSendFn, completeFn);
});

$(function() {
	var hrefParamObj = ecDo.getUrlPrmt(window.location.href);
	var columnParam = hrefParamObj.column;
	var conditionParam = hrefParamObj.condition;
	var $sel;
	if(columnParam == "CargoPeriod"){
		$sel = $("#hetonghuoqiSel");
	}else if(columnParam == "ExpectedDeliveryPeriod"){
		$sel = $("#yujihuoqiSel");
	}else if(columnParam == "DateOfSign"){
		$sel = $("#shijihuoqiSel");
	}
	$sel.val(conditionParam);

	// if (CurrentHref[0] == "CargoPeriod") { // 合同货期
	// 	$("#table1 tr").children().eq(11).find('select').find(
	// 			"option[value='" + CurrentHref[1] + "']")
	// 			.attr("selected", true).siblings().attr("selected", false);
	// } else if (CurrentHref[0] == "ExpectedDeliveryPeriod") { // 预计货期
	// 	$("#table1 tr").children().eq(13).find('select').find(
	// 			"option[value='" + CurrentHref[1] + "']")
	// 			.attr("selected", true).siblings().attr("selected", false);
	// } else if (CurrentHref[0] == "DateOfSign") { // 实际货期
	// 	$("#table1 tr").children().eq(12).find('select').find(
	// 			"option[value='" + CurrentHref[1] + "']")
	// 			.attr("selected", true).siblings().attr("selected", false);
	// }

	// 取计算数据
	var transportData = $(".transport_calc_data").data("idata");
	// console.log(typeof transportData);
	for(var kk in transportData){
		var intext;
		if(transportData[kk]==""||transportData[kk]=="--"||transportData[kk]==undefined||transportData[kk]==null){
			intext = 0;
		}else{
			intext = transportData[kk];
		}
		$(".transport_calc_table").find("."+kk+"_td").text(intext);
	}

});
// 数组去重
Array.prototype.unique = function() {
	var res = [];
	var json = {};
	for (var i = 0; i < this.length; i++) {
		if (!json[this[i]]) {
			res.push(this[i]);
			json[this[i]] = 1;
		}
	}
	return res;
}

$(".transport_calc_table [class$='_td']").on("click",function(e){
	e.stopPropagation();
	// window.open("Requirement#123");
	var CAT = $(this).attr("class").replace("_td","");
	$.cookie("transportCAT", CAT, { expires: 360 });
	window.open("TransportDetail");
	// alert($.cookie("transportCAT"));
});

