/*****这里定义函数和全局变量*****/
// key：cookie值；value：参数值；
var TransportObj = {
	"AllNoSend":"AllNoSend",
	"OtherNoSend":"OtherNoSend",
	"Overdue":"Overdue",
	"OverdueRisk":"OverdueRisk",
	"ShippedMonth":"ShippedMonth",
	"transportdetail":""
};

var TransportTABInfo = {
	"AllNoSend":"所有未发货统计信息",
	"OtherNoSend":"未包括等待客户付款统计信息",
	"Overdue":"当前已逾期统计信息",
	"OverdueRisk":"存在逾期风险统计信息",
	"ShippedMonth":"本月已发统计信息",
	"":"详细记录统计信息"
};

var tabParam;

var date = new Date();
var year = date.getFullYear();
// 表格渲染
function tableRender(currentPage,pageCounts,transportCAT,data){
	var str = '';
	for(var i =1;i<data.length;i++){
	    str+='<tr>'+
	            '<td>'+parseInt((currentPage-1)*10 + i)+'</td>'+
	            '<td class="Area" title="'+data[i].Area+'">'+data[i].Area+'</td>'+
	            '<td class="Customer" title="'+data[i].Customer+'">'+data[i].Customer+'</td>'+
	            '<td class="ContractTitle" title="'+data[i].ContractTitle+'">'+data[i].ContractTitle+'</td>'+
	            '<td class="ContractNo" title="'+data[i].ContractNo+'">'+data[i].ContractNo+'</td>'+
	            '<td class="DateOfSign" title="'+data[i].DateOfSign+'">'+data[i].DateOfSign+'</td>'+
	            '<td class="CargoPeriod" title="'+data[i].CargoPeriod+'">'+data[i].CargoPeriod+'</td>'+
	            '<td class="ExpectedDeliveryPeriod" title="'+data[i].ExpectedDeliveryPeriod+'">'+data[i].ExpectedDeliveryPeriod+'</td>'+
	            '<td class="ActualDelivery" title="'+data[i].ActualDelivery+'">'+data[i].ActualDelivery+'</td>'+
	        '</tr>';
	}
	$(".g_container_body_table ."+transportCAT+"_table tbody").empty().append(str);
	$(".g_container_body_table [class$='_table']").hide();
	$(".g_container_body_table ."+transportCAT+"_table").show();
	$(".m_page #currentPage").text(currentPage);
	$(".m_page #allPage").text(pageCounts);
}

// 翻页函数
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

// 表格渲染ajax 
function tableRenderAjax(param,icurPage){
	$.ajax({
		type:"POST",
		url:"TransportDetail",
		data:{
			transportCAT:param,
			currentPage:icurPage
		},
		dataType:'json',
		success:function(res){
			console.log(typeof res);
			var pageCounts = res.pageCount;
			var transportCAT = res.transportCAT;
			var data = res.transportDetail;
			tableRender(icurPage,pageCounts,transportCAT,data);
			pageStyle(icurPage,pageCounts);
			tabParam = transportCAT;
		},
		error:function(){
			$.MsgBox_Unload.Alert("提示","服务器繁忙！");
		},
		complete: function(XMLHttpRequest, textStatus){
		    var newInfo1 = transportInfo();
		    $(".g_container_body_info_l").text(newInfo1);
		    tdDateHandle(".DateOfSign, .CargoPeriod, .ExpectedDeliveryPeriod, .ActualDelivery","","#000");
		}
	});
}

// 年月日
function YMSel(){
	var str0 = '<option value="no" disabled selected style="display: none;">选择年份</option>';
	var gap = year - 2015;
	for(var ii = 0;ii<gap;ii++){
		var curY = 2016+ii;
		str0 += '<option value="'+curY+'">'+curY+'</option>';
	}
	$("#year_sel").empty().append(str0);
	var str00 = '<option value="no" disabled selected style="display: none;">选择月份</option><option value="All">所有</option>';
	for(var iii = 1;iii<13;iii++){
		str00+= '<option value="'+iii+'">'+iii+'</option>';
	}
	$("#month_sel").empty().append(str00);
}

// 信息查询填充
function transportInfo(){
	var jj = "";
	for(var kk in TransportTABInfo){
		if(kk == tabParam){
			jj = TransportTABInfo[kk];
		}
	}
	return jj;
}

/*****页面加载完毕*****/
$(function(){
	var curTransport = $.cookie("transportCAT");
	if(curTransport==undefined||curTransport==""){
		curTransport = "AllNoSend";
	}
	var paraTransport;
	for(var k in TransportObj){
		if(k==curTransport){
			paraTransport = TransportObj[k];
			tabParam = TransportObj[k];
		}
	}
	console.log(curTransport);
	console.log(paraTransport);
	$(".g_container_info ."+curTransport+"_tab").addClass("current_tab").siblings().removeClass("current_tab");
	var newInfo = transportInfo();
	$(".g_container_body_info_l").text(newInfo);
	// 年份月份下拉框
	YMSel();
	$("#year_sel").val(year);
	$("#month_sel").val("All");

	tableRenderAjax(paraTransport,1);
});



/*****这里绑定事件*****/
$(".g_container_info span[class$='_tab']").on("click",function(){
	$(this).addClass("current_tab").siblings().removeClass("current_tab");
	var curTransportParaArr = $(this).attr("class").split(" ");
	var curTransportPara;
	curTransportParaArr.map(function(currentValue,index,arr){
		if(currentValue=="current_tab"){
			return;
		}
		curTransportPara = currentValue.replace("_tab","");
	});
	console.log(curTransportPara);
	tableRenderAjax(curTransportPara,1);
	
});

// 翻页与跳页
$("#jumpNumber").on("input propertychange",function(){
    var newVal = $(this).val().replace(/[^\d]/g,'');
    $(this).val(newVal);
});

// 翻页
$("#fistPage").click(function(){
    var currentPage =1;
    tableRenderAjax(tabParam,currentPage);
});

$("#lastPage").click(function(){
    var currentPage =Number($("#allPage").text());
    tableRenderAjax(tabParam,currentPage);
});

$("#upPage").click(function(){
    var currentPage = Number($("#currentPage").text());
    if(currentPage == 1){
        return;
    }else{
        currentPage--;
    }
    tableRenderAjax(tabParam,currentPage);
});

$("#nextPage").click(function(){
    var currentPage = Number($("#currentPage").text());
    var pageCounts = Number($("#allPage").text());
    if(currentPage == pageCounts){
        return;
    }else{
        currentPage++;
    }
    tableRenderAjax(tabParam,currentPage);
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
        tableRenderAjax(tabParam,currentPage);
    }
});