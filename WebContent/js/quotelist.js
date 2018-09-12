/*进入页面验证本地存储*/
// storage.clear(); // 将localStorage的所有内容清除
// storage.removeItem("a"); // 删除某个键值对
// storage.key(i); // 使用key()方法，向其中出入索引即可获取对应的键
var QuotelistAllStaffInfo;
if(!window.localStorage){
    alert("浏览器不支持localstorage");
}else{
    //主逻辑业务
    var storage = window.localStorage;
    var inow = Date.now();
    var QuotelistAllStaffInfoStr = storage.getItem("QuotelistAllStaffInfo");
    if(QuotelistAllStaffInfoStr == undefined){
        // 第一次存
        getQuotelistAllStaffInfo(function(res){
            var iObj = {};
            iObj.expires = inow + 5*60*1000;
            iObj.data = _.drop(res);
            console.log("iObj.data");
            console.log(iObj.data);
            QuotelistAllStaffInfo = iObj.data;
            // _.cloneDeep(a)
            var iStr = JSON.stringify(iObj);
            // storage["InventoryAllCustomerInfo"] = iStr;
            storage.setItem("QuotelistAllStaffInfo", iStr);
        });
    }else{
        var iexpires = JSON.parse(QuotelistAllStaffInfoStr).expires;
        if(iexpires < inow){
            // 已超期
            getQuotelistAllStaffInfo(function(res){
                var iObj = {};
                iObj.expires = inow + 5*60*1000;
                iObj.data = _.drop(res);
                QuotelistAllStaffInfo = iObj.data;
                // _.cloneDeep(a)
                var iStr = JSON.stringify(iObj);
                // storage["InventoryAllCustomerInfo"] = iStr;
                storage.setItem("QuotelistAllStaffInfo", iStr);
            });
        }else{
            // 未超期
            var QuotelistAllStaffInfoObj = JSON.parse(QuotelistAllStaffInfoStr);
            QuotelistAllStaffInfo = QuotelistAllStaffInfoObj.data;
        }
    }
}
function getQuotelistAllStaffInfo(fn){
	$.ajax({
		type: "GET",
		url: "GetStaffInfo",
		data: {
			Type: "common"
		},
		dataType: "json"
	}).then(function(res){
		fn && fn(res);
	},function(){
		$.MsgBox_Unload.Alert("员工信息获取提示", "网络繁忙！信息获取失败");
	});
}
function getLocalStaffCode(iname){
	var findItem = _.find(QuotelistAllStaffInfo, function(o) { return o.StaffName == iname; });
	var StaffCode = findItem.StaffCode;
	return StaffCode;
}


// 修改里删除用的报价单记录的ID
var globalQuoteID;
// 报价单的fax
var td_CustomerFax;

//其他供应商 PORMB模板显示

	//其他供应商P0RMB 删除
	$(document).on("click","#view10 .OtherPOItemBtn",function() {
		var delID = $(this).parent().parent().parent().find("td").eq(0).attr("value");
		var that = $(this).parent().parent().parent();
				that.remove();
				//其他供应商P0RMB计算总和
					var totalNum = 0;
					if($("#view10 .OtherPOItemTr").length > 0 ){
						console.log($("#view10 .OtherPOItemTr").length);
						totalNum = 0;
						for (var i = 0; i < $("#view10 .OtherPOItemTr").length; i++) {
							totalNum += ($("#view10 .OtherPOItemTr").eq(i).find(".Qty").text())* rmoney(fmoney($("#view10 .OtherPOItemTr").eq(i).find(".UnitPrice").text()));
							console.log(totalNum);
						}
						
					}else{
						totalNum = 0;
					} 
					console.log("totalNum"+totalNum);
					$("#view10 .SubTotal").text("").text(fmoney(totalNum.toFixed(2))); 
					var Discounted = $("#view10 .Discounted").text();
					/*alert(Discounted );*/
					if(parseFloat(Discounted) != 0 || parseFloat(Discounted) == ''){
						$("#view10 .FinalTotal").text("").text(fmoney(totalNum.toFixed(2)));
					}else{
						$("#view10 .FinalTotal").text("").text(fmoney(totalNum.toFixed(2) * (parseFloat(Discounted) / 100)));
					}		
	});

//添加部分
//点击客户编号搜索时，弹出客户资料
$('.CustomerInformation-search').click(function () {
	var Type="common";
	$(".CustomerStr_Add").remove();
	 $.ajax({
	        type : 'get',
	        url : 'GetCustomerInfo',
	        data : {
	        	Type : Type
	        },
	        dataType : 'json',
	        success : function (data) {
	        	console.log(data);
	        	// var CustomerStr ="";
	        	 for(var i = 1 ; i <data.length; i++ ){
	        			var CustomerStr = 
		        			'<tr class="CustomerStr_Add">'+
		        			'<td value='+data[i].ID+'>'+i+'</td>'+
		        			'<td>'+data[i].CustomerCode+'</td>'+
		        			'<td>'+data[i].CustomerCompany+'</td>'+
		        			'<td>'+data[i].CustomerName+'</td>'+
							'<td>'+data[i].CustomerTel+'</td>'+
							'<td>'+data[i].CustomerTelTwo+'</td>'+
							'<td>'+data[i].CustomerFax+'</td>'+
							'<td>'+data[i].CustomerMail+'</td>'+
							'</tr>';
	        	 $('.CustomerTitle').after(CustomerStr);
	        	} 
	        },
	        error : function () {
	            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    });     	    
    $('.CustomerInformation').show();
    $('.StaffInformation').hide();
    $('.CommodityInformation').hide();
});
//点击客户资料搜索时，实现根据客户名称，联系人完成搜索
$('.CustomerInformation-edit').click(function(){
	var Type;
	var param=$('.contract_basic #param').val();
	if( param == ""){
		Type="common";
	}else{
	    Type="singleSelect";
	}
	 $('.CustomerData').remove();
	if($('.contract_basic #Customer-search').val() == "客户名称"){
		classify="客户名称";
	}else{
		classify="联系人";
	}
		$.ajax({
	        type : 'get',
	        url : 'GetCustomerInfo',
	        data : {
	        	Type : Type,
	        	classify : classify,
	        	param : param
	        },
	        dataType : 'json',
	        success : function (data) {
	        	$(".CustomerTab").find("tr").not(".CustomerTitle").remove();
	        	 for(var i = 1 ; i <data.length; i++ ){
	        			var CustomerStr = 
		        			'<tr class="CustomerData">'+
		        			'<td value='+data[i].ID+'>'+i+'</td>'+
		        			'<td>'+data[i].CustomerCode+'</td>'+
		        			'<td>'+data[i].CustomerCompany+'</td>'+
		        			'<td>'+data[i].CustomerName+'</td>'+
							'<td>'+data[i].CustomerTel+'</td>'+
							'<td>'+data[i].CustomerTelTwo+'</td>'+
							'<td>'+data[i].CustomerFax+'</td>'+
							'<td>'+data[i].CustomerMail+'</td>'+
							'</tr>';
	        	 	$('.CustomerTitle').after(CustomerStr);
	        	}
	        },
	        error : function () {
	            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    }); 
	$('.CustomerInformation').show();
    $('.StaffInformation').hide();
    $('.CommodityInformation').hide();
});


//获取添加中客户资料中的信息放在表格1中,双击实现
$(document).on("click",".CustomerStr_Add",function(){
	var tr=$(this);
	var ID = tr.find('td').eq(0).attr("value");
	$(".contract_add .contract_title").attr("value",ID);  //在修改页面保存当前行的ID信息	
	$('.contract_add').find('input[name="CustomerCode"]').val(tr.find('td').eq(1).text());
	$('.contract_add').find('input[name="CustomerCompany"]').val(tr.find('td').eq(2).text());
	$('.contract_add').find('input[name="CustomerName"]').val(tr.find('td').eq(3).text());
	$('.contract_add').find('input[name="CustomerTel"]').val(tr.find('td').eq(4).text());
	$('.contract_add').find('input[name="CustomerFax"]').val(tr.find('td').eq(6).text());
	$('.contract_add').find('input[name="CustomerMail"]').val(tr.find('td').eq(7).text());
	$('.MailBar_cover_color, .contract_add').show();
});

//获取修改中客户资料中的信息放在表格1中,双击实现
$(document).on("click",".CustomerStr_Update",function(){
	var tr=$(this);
	var ID = tr.find('td').eq(0).attr("value");
	// $(".contract_update .contract_title").attr("value",ID);  //在修改页面保存当前行的ID信息	
	$('.contract_update').find('input[name="CustomerCode"]').val(tr.find('td').eq(1).text());
	$('.contract_update').find('input[name="CustomerCompany"]').val(tr.find('td').eq(2).text());
	$('.contract_update').find('input[name="CustomerName"]').val(tr.find('td').eq(3).text());
	$('.contract_update').find('input[name="CustomerTel"]').val(tr.find('td').eq(4).text());
	$('.contract_update').find('input[name="CustomerFax"]').val(tr.find('td').eq(6).text());
	$('.contract_update').find('input[name="CustomerMail"]').val(tr.find('td').eq(7).text());
	$('.MailBar_cover_color, .contract_update').show();
});

////////客户资料的搜索放参数
//获取添加中客户资料中搜索时把信息放在表格1中,双击实现
$(document).on("click",".CustomerData",function(){
	var tr=$(this);
	var  ID = tr.find('td').eq(0).attr("value");
	$(".contract_add .contract_title").attr("value",ID);  //在修改页面保存当前行的ID信息	
	$('.contract_add').find('input[name="CustomerCode"]').val(tr.find('td').eq(1).text());
	$('.contract_add').find('input[name="CustomerCompany"]').val(tr.find('td').eq(2).text());
	$('.contract_add').find('input[name="CustomerName"]').val(tr.find('td').eq(3).text());
	$('.contract_add').find('input[name="CustomerTel"]').val(tr.find('td').eq(4).text());
	$('.contract_add').find('input[name="CustomerFax"]').val(tr.find('td').eq(6).text());
	$('.contract_add').find('input[name="CustomerMail"]').val(tr.find('td').eq(7).text());
    $('.MailBar_cover_color, .contract_add').show();
});

////////客户资料的搜索放参数
//获取修改中客户资料中搜索时把信息放在表格1中,双击实现
$(document).on("click",".CustomerData1",function(){
	var tr=$(this);
	var  ID = tr.find('td').eq(0).attr("value");
	$(".contract_update .contract_title").attr("value",ID);  //在修改页面保存当前行的ID信息	
	$('.contract_update').find('input[name="CustomerCode"]').val(tr.find('td').eq(1).text());
	$('.contract_update').find('input[name="CustomerCompany"]').val(tr.find('td').eq(2).text());
	$('.contract_update').find('input[name="CustomerName"]').val(tr.find('td').eq(3).text());
	$('.contract_update').find('input[name="CustomerTel"]').val(tr.find('td').eq(4).text());
	$('.contract_update').find('input[name="CustomerFax"]').val(tr.find('td').eq(6).text());
	$('.contract_update').find('input[name="CustomerMail"]').val(tr.find('td').eq(7).text());
	$('.MailBar_cover_color, .contract_update').show();
});

//关闭
$('.CustomerInformation_close').click(function () {
    $('.CustomerInformation').hide();
});

//点击业务员搜索时，弹出员工资料
$('.StaffInformation-search').click(function () {
	var Type="common";
	$(".StaffStr_Add").remove();
	 $.ajax({
	        type : 'get',
	        url : 'GetStaffInfo',
	        data : {
	        	Type : Type,
	        },
	        dataType : 'json',
	        success : function (data) {
	        	 for(var i = 1 ; i <data.length; i++ ){
	        			var StaffStr = 
		        			'<tr class="StaffStr_Add">'+
		        			'<td value='+data[i].ID+'>'+i+'</td>'+
		        			'<td>'+data[i].DepartmentCode+'</td>'+
		        			'<td>'+data[i].Job+'</td>'+
		        			'<td>'+data[i].StaffCode+'</td>'+
							'<td>'+data[i].StaffName+'</td>'+
							'<td>'+data[i].EntryDate+'</td>'+
							'<td>'+data[i].LinkTel+'</td>'+
							'<td>'+data[i].Quit+'</td>'+
							'<td>'+data[i].StaffMail+'</td>'+
							'</tr>';
	        	 $('.StaffTitle').after(StaffStr);
	        	} 
	        },
	        error : function () {
	            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    }); 
    $('.StaffInformation').show();
    $('.CommodityInformation').hide();
    $('.CustomerInformation').hide();
});

//点击员工资料搜索时，实现根据员工名称完成搜索
$('.StaffInformation-edit').click(function(){
	var Type;
	var param=$('.contract_basic #StaffName').val();
	if( param == ""){
		Type="common";
	}else{
	    Type="singleSelect";
	}
	 $('.StaffData').remove();
	 var classify="员工名称";
		$.ajax({
	        type : 'get',
	        url : 'GetStaffInfo',
	        data : {
	        	Type : Type,
	        	classify : classify,
	        	param : param 
	        },
	        dataType : 'json',
	        success : function (data) {
	        	$(".StaffTab").find("tr").not(".StaffTitle").remove();
	        	console.log(data);
	        	 for(var i = 1 ; i <data.length; i++ ){
	        			var StaffStr = 
		        			'<tr class="StaffData">'+
		        			'<td value='+data[i].ID+'>'+i+'</td>'+
		        			'<td>'+data[i].DepartmentCode+'</td>'+
		        			'<td>'+data[i].Job+'</td>'+
		        			'<td>'+data[i].StaffCode+'</td>'+
							'<td>'+data[i].StaffName+'</td>'+
							'<td>'+data[i].EntryDate+'</td>'+
							'<td>'+data[i].LinkTel+'</td>'+
							'<td>'+data[i].Quit+'</td>'+
							'<td>'+data[i].StaffMail+'</td>'+
							'</tr>';
	        	 $('.StaffTitle').after(StaffStr);
	        	} 
	        },
	        error : function () {
	            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    }); 
		$('.StaffInformation').show();
	    $('.CommodityInformation').hide();
	    $('.CustomerInformation').hide();
});

//获取添加中员工资料中的信息放在表格1中,双击实现
$(document).on("click",".StaffStr_Add",function(){
	var tr=$(this);
	var  ID = tr.find('td').eq(0).attr("value");
	$(".contract_add .contract_title").attr("value",ID);  //在修改页面保存当前行的ID信息	
	$('.contract_add').find('input[name="Department"]').val(tr.find('td').eq(1).text());
	$('.contract_add').find('input[name="StaffName"]').val(tr.find('td').eq(4).text());
	$('.contract_add').find('input[name="StaffTel"]').val(tr.find('td').eq(6).text());
	$('.contract_add').find('input[name="StaffMail"]').val(tr.find('td').eq(8).text());
	//获取不变的日期yyyymmdd
	var DatesentADD = $('.contract_add input[name="Datesent"]').val().replace(/\-/g, "");
 	//获取员工编号
	var EmpNO=tr.find('td').eq(3).text();
	/*自动生成报价单号  */
 	var NumberStr = "QU"+EmpNO+ DatesentADD+"-"+priceNum;
	$('.contract_add').find('input[name="Number"]').val(NumberStr);
	 $('.MailBar_cover_color').show();
	   $('.contract_add').show();
});

//获取修改中员工资料中的信息放在表格1中,双击实现
$(document).on("click",".StaffStr_Update",function(){
	var tr=$(this);
	var  ID = tr.find('td').eq(0).attr("value");
	$('.contract_update').find('input[name="Department"]').val(tr.find('td').eq(1).text());
	$('.contract_update').find('input[name="StaffName"]').val(tr.find('td').eq(4).text());
	$('.contract_update').find('input[name="StaffTel"]').val(tr.find('td').eq(6).text());
	$('.contract_update').find('input[name="StaffMail"]').val(tr.find('td').eq(8).text());
	//获取不变的日期yyyymmdd
	var DatesentADD = $('.contract_update input[name="Datesent"]').val().replace(/\-/g, "");
	var EmpNO=tr.find('td').eq(3).text();
	/*console.log("EmpNO"+EmpNO);*/
	var data = $(".contract_update #Number").val().split("-")[1];
 	var NumberStr = "QU"+EmpNO+DatesentADD+"-"+data;
 	/*console.log("NumberStr"+NumberStr);*/
	$('.contract_update').find('input[name="Number"]').val(NumberStr);
	 $('.MailBar_cover_color').show();
	   $('.contract_update').show();
});

///////员工资料搜索中放元素
//获取添加中员工资料中的员工名称进行搜索时，把信息放在表格1中,双击实现
$(document).on("click",".StaffData",function(){
	var tr=$(this);
	var  ID = tr.find('td').eq(0).attr("value");
	$(".contract_add .contract_title").attr("value",ID);  //在修改页面保存当前行的ID信息	
	$('.contract_add').find('input[name="Department"]').val(tr.find('td').eq(1).text());
	$('.contract_add').find('input[name="StaffName"]').val(tr.find('td').eq(4).text());
	$('.contract_add').find('input[name="StaffTel"]').val(tr.find('td').eq(6).text());
	$('.contract_add').find('input[name="StaffMail"]').val(tr.find('td').eq(8).text());
	//获取日期格式为yyyymmdd
	var DatesentADD = $('.contract_add input[name="Datesent"]').val().replace(/\-/g, "");
 	//获取员工编号
	var EmpNO=tr.find('td').eq(3).text();
 	var NumberStr = "QU"+EmpNO+DatesentADD+"-"+priceNum;
	$('.contract_add').find('input[name="Number"]').val(NumberStr);
	$('.MailBar_cover_color, .contract_add').show();
});

//获取修改中员工资料中的员工名称进行搜索时，把信息放在表格1中,双击实现
$(document).on("click",".StaffData1",function(){
	var tr=$(this);
	var ID = tr.find('td').eq(0).attr("value");
	$(".contract_update .contract_title").attr("value",ID);  //在修改页面保存当前行的ID信息	
	$('.contract_update').find('input[name="Department"]').val(tr.find('td').eq(1).text());
	$('.contract_update').find('input[name="StaffName"]').val(tr.find('td').eq(4).text());
	$('.contract_update').find('input[name="StaffTel"]').val(tr.find('td').eq(6).text());
	$('.contract_update').find('input[name="StaffMail"]').val(tr.find('td').eq(8).text());
	//获取日期格式为yyyymmdd
	var DatesentADD = $('.contract_update input[name="Datesent"]').val().replace(/\-/g, "");
 	//获取员工编号
	var EmpNO=tr.find('td').eq(3).text();
	var data = $(".contract_update #Number").val().split("-")[1];
 	var NumberStr = "QU"+EmpNO+DatesentADD+"-"+data;
	$('.contract_update').find('input[name="Number"]').val(NumberStr);
	$('.MailBar_cover_color, .contract_update').show();
});

//关闭
$('.StaffInformation_close').click(function () {
    $('.StaffInformation').hide();
});

//点击添加表格2中任意td时，弹出商品资料
$(' .tableADD .tableADDTit').click(function () {
    $('.StaffInformation').hide();
    $('.CommodityInformation').show();
    $('.CustomerInformation').hide();
});

//点击添加中商品资料时,实现根据型号完成搜索
$('.CommodityInformation-edit').click(function () {
		var classify;
		var param;
		var Type;
		if($(this).parent().prev().children("input").attr("id")=="Model"){
			classify="型号";
			param = $('.contract_basic #Model').val().trim();
		}else if($(this).parent().prev().children("input").attr("id")=="good_name"){
			classify="商品名称";
			param = $('.contract_basic #good_name').val().trim();
		}
		
		if(param == ""){
			Type = "common";
		}else{
		    Type = "singleSelect";
		}
		$(".clickThis").remove();
		$.ajax({
	        type : 'get',
	        url : 'GetCommodityInfo',
	        data : {
	        	Type : Type,
	        	classify : classify,
	        	param : param 
	        },
	        dataType : 'json',
	        success : function (data) {
	        	$(".CommodityTab").find("tr").not(".CommodityTitle").remove();
	        	if(data.length == 1){
	        		$.MsgBox_Unload.Alert("搜索提示", "无相应数据！");
	        	}else if(data.length > 1){
		        	 for(var i = 1 ; i <data.length; i++ ){
		        	 	var CommodityStr = '';
		        	 	var QuoteTime = data[i].QuoteTime;
		        	 	QuoteTime = QuoteTime == '0000-00-00'?"":QuoteTime;
		        	 	var QuoteTimeNew = globalDataHandle(QuoteTime,"");
		        		 //判断搜索中成本价格有没有权限
		        		 if($("#hideMarkDom span").text() =="是"){
		        			 CommodityStr = 
				        			'<tr class="clickThis">'+
				        			'<td value='+data[i].ID+'>'+i+'</td>'+
				        			'<td>'+data[i].CommodityName+'</td>'+
				        			'<td>'+data[i].Model+'</td>'+
				        			'<td>'+data[i].Unit+'</td>'+
									'<td>'+data[i].DeliveryPeriod+'</td>'+
									'<td>'+data[i].ProducingArea+'</td>'+
									'<td>'+data[i].CostPrice+'</td>'+
									'<td>'+data[i].DiscountCost+'</td>'+
									'<td>'+data[i].UnitPrice+'</td>'+
									'<td>'+data[i].SellerPriceTwo+'</td>'+
									'<td>'+data[i].SellerPriceThree+'</td>'+
									'<td>'+data[i].Supplier+'</td>'+
									'<td>'+QuoteTimeNew+'</td>'+
									'</tr>';
		 	        	}else{
		 	        		CommodityStr = 
			        			'<tr class="clickThis">'+
			        			'<td value='+data[i].ID+'>'+i+'</td>'+
			        			'<td>'+data[i].CommodityName+'</td>'+
			        			'<td>'+data[i].Model+'</td>'+
			        			'<td>'+data[i].Unit+'</td>'+
								'<td>'+data[i].DeliveryPeriod+'</td>'+
								'<td>'+data[i].ProducingArea+'</td>'+
								'<td style="display:none;">'+data[i].CostPrice+'</td>'+
								'<td style="display:none;">'+data[i].DiscountCost+'</td>'+
								'<td>'+data[i].UnitPrice+'</td>'+
								'<td>'+data[i].SellerPriceTwo+'</td>'+
								'<td>'+data[i].SellerPriceThree+'</td>'+
								'<td>'+data[i].Supplier+'</td>'+
								'<td>'+QuoteTimeNew+'</td>'+
								'</tr>';
		 	        	}
		        	    $('.CommodityTitle').after(CommodityStr);
		        	} 
	        	}
	        },
	        error : function () {
	            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    });
    $('.StaffInformation').hide();
    $('.CommodityInformation').show();
    $('.CustomerInformation').hide();
});

//获取添加商品中的信息放在表格2中,双击实现
$(document).on("click",".clickThis td",function(){
	var TD = $(this).parent().find("td");
	var insertID = TD.eq(0).attr("value");
	var unit_price = Number(TD.eq(8).text());
	var clickFlag = 0;
	$(".contract_add .tableADD .ModelTr td:nth-child(1)").each(function(){
		if($(this).attr("value")==insertID){
			var oldqty = Number($(this).parent().find("span.QtySpan").text());
			$(this).parent().find("span.QtySpan").text(oldqty+1);
			$(this).parent().children("td").eq(6).text((oldqty+1)*unit_price);
			clickFlag = 1;
			return true;
		}
	});
	if(clickFlag == 1){
		return false;
	}
	var tdStr =
		'<tr class="ModelTr">'+
			'<td value="'+TD.eq(0).attr("value")+'">'+TD.eq(1).text()+'</td>'+
			'<td>'+TD.eq(2).text()+'</td>'+
			'<td>'+TD.eq(3).text()+' </td>'+
			'<td>'+TD.eq(4).text()+'</td>'+
			'<td><span contenteditable="true" value="1" class="QtySpan">1</span></td>'+
			'<td>'+TD.eq(8).text()+'</td>'+
			'<td>'+parseFloat(1*TD.eq(8).text()).toFixed(2)+'</td>'+
			'<td style="display:none;">'+TD.eq(6).text()+'</td>'+ 
			'<td style="display:none;"></td>'+
			'<td style="display:none;"></td>'+
			'<td style="display:none;"></td>'+
			/*'<td><input type="button" class="add_del" style="width:90%;height:90%;" value="删除"></td>'+*/
			'<td  class="AddOper" >'+
				'<span class="fa fa-long-arrow-up OperUp" ></span>'+
				'<span class="fa fa-trash OperDel Serial_del" ></span>'+
				'<span class="fa fa-long-arrow-down OperDown" ></span>'+
			'</td>'+
		'</tr>';
	$(".contract_add .tableADD").append(tdStr);
});

//删除添加中的商品信息
$(document).on("click",".AddOper .Serial_del",function(){
	$(this).parent().parent().remove();
});

//删除修改中的商品信息
$(document).on("click",".ModifyOper .Serial_del",function(){
 	var CommodityID = $(this).parent().parent().find(".id").attr("value");
	var that = $(this).parent().parent();
	var Model = that.find("td.id").text();
	var Commodity = that.find("td").eq(0).attr("value");
	$.ajax({
        type : 'get',
        url : 'QuoteCommodityDelete',
        data : {
        	CommodityID : CommodityID,
        	QuoteID: globalQuoteID,
        	Model: Model,
        	Commodity: Commodity
        },
        dataType : 'json',
        success : function (data) {
        	that.remove();
        	 /*$.MsgBox.Alert("提示", "删除成功！");*/
        },
        error : function () {
            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});

//修改修改修改
//点击修改部分客户编号搜索时，弹出客户资料
$('.CustomerInformation-search1').click(function () {
	var Type="common";
	$(".CustomerStr_Update").remove();
	 $.ajax({
	        type : 'get',
	        url : 'GetCustomerInfo',
	        data : {
	        	Type : Type
	        },
	        dataType : 'json',
	        success : function (data) {
	        	console.log(data);
	        	 for(var i = 1 ; i <data.length; i++ ){
	        			var CustomerStr = 
		        			'<tr class="CustomerStr_Update">'+
		        			'<td value='+data[i].ID+'>'+i+'</td>'+
		        			'<td>'+data[i].CustomerCode+'</td>'+
		        			'<td>'+data[i].CustomerCompany+'</td>'+
		        			'<td>'+data[i].CustomerName+'</td>'+
							'<td>'+data[i].CustomerTel+'</td>'+
							'<td>'+data[i].CustomerTelTwo+'</td>'+
							'<td>'+data[i].CustomerFax+'</td>'+
							'<td>'+data[i].CustomerMail+'</td>'+
							'</tr>';
	        	 $('.CustomerTitle1').after(CustomerStr);
	        	} 
	        },
	        error : function () {
	            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    }); 
    $('.CustomerInformation').show();
    $('.StaffInformation').hide();
    $('.CommodityInformation').hide();
});

//点击修改部分客户资料搜索时，实现根据客户名称，联系人完成搜索
$('.CustomerInformation-edit1').click(function(){
	var Type;
	var param=$('.contract_basic #param1').val();
	if( param == ""){
		Type="common";
	}else{
	    Type="singleSelect";
	}
	 $('.CustomerData1').remove();
	 var Customer_search1=$('.contract_basic #Customer-search1').val();
	if(Customer_search1 == "客户名称"){
		classify="客户名称";
	}else{
		classify="联系人";
	}
		$.ajax({
	        type : 'get',
	        url : 'GetCustomerInfo',
	        data : {
	        	Type : Type,
	        	classify : classify,
	        	param : param 
	        },
	        dataType : 'json',
	        success : function (data) {
	        	$(".CustomerTab1").find("tr").not(".CustomerTitle1").remove();
	        	console.log(data);
	        	 for(var i = 1 ; i <data.length; i++ ){
	        			var CustomerStr = 
		        			'<tr class="CustomerData1">'+
		        			'<td value='+data[i].ID+'>'+i+'</td>'+
		        			'<td>'+data[i].CustomerCode+'</td>'+
		        			'<td>'+data[i].CustomerCompany+'</td>'+
		        			'<td>'+data[i].CustomerName+'</td>'+
							'<td>'+data[i].CustomerTel+'</td>'+
							'<td>'+data[i].CustomerTelTwo+'</td>'+
							'<td>'+data[i].CustomerFax+'</td>'+
							'<td>'+data[i].CustomerMail+'</td>'+
							'</tr>';
	        	 $('.CustomerTitle1').after(CustomerStr);
	        	} 
	        },
	        error : function () {
	            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    }); 
	$('.CustomerInformation').show();
    $('.StaffInformation').hide();
    $('.CommodityInformation').hide();
});

//点击修改部分业务员搜索时，弹出员工资料
$('.StaffInformation-search1').click(function () {
	var Type="common";
	$(".StaffStr_Update").remove();
	 $.ajax({
	        type : 'get',
	        url : 'GetStaffInfo',
	        data : {
	        	Type : Type,
	        	
	        },
	        dataType : 'json',
	        success : function (data) {
	        	console.log(data);
	        	 for(var i = 1 ; i <data.length; i++ ){
	        			var StaffStr = 
		        			'<tr class="StaffStr_Update">'+
		        			'<td value='+data[i].ID+'>'+i+'</td>'+
		        			'<td>'+data[i].DepartmentCode+'</td>'+
		        			'<td>'+data[i].Job+'</td>'+
		        			'<td>'+data[i].StaffCode+'</td>'+
							'<td>'+data[i].StaffName+'</td>'+
							'<td>'+data[i].EntryDate+'</td>'+
							'<td>'+data[i].LinkTel+'</td>'+
							'<td>'+data[i].Quit+'</td>'+
							'<td>'+data[i].StaffMail+'</td>'+
							'</tr>';
	        	 $('.StaffTitle1').after(StaffStr);
	        	} 
	        },
	        error : function () {
	            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    }); 
    $('.StaffInformation').show();
    $('.CommodityInformation').hide();
    $('.CustomerInformation').hide();
    
});

//点击修改部分员工资料搜索时，实现根据员工名称完成搜索
$('.StaffInformation-edit1').click(function(){
	var Type;
	var param=$('.contract_basic #StaffName1').val();
	if( param == ""){
		Type="common";
	}else{
	    Type="singleSelect";
	}
	 $('.StaffData1').remove();
	 var classify="员工名称";
		$.ajax({
	        type : 'get',
	        url : 'GetStaffInfo',
	        data : {
	        	Type : Type,
	        	classify : classify,
	        	param : param 
	        },
	        dataType : 'json',
	        success : function (data) {
	        	$(".StaffTab1").find("tr").not(".StaffTitle1").remove();
	        	 for(var i = 1 ; i <data.length; i++ ){
	        			var StaffStr = 
		        			'<tr class="StaffData1">'+
		        			'<td value='+data[i].ID+'>'+i+'</td>'+
		        			'<td>'+data[i].DepartmentCode+'</td>'+
		        			'<td>'+data[i].Job+'</td>'+
		        			'<td>'+data[i].StaffCode+'</td>'+
							'<td>'+data[i].StaffName+'</td>'+
							'<td>'+data[i].EntryDate+'</td>'+
							'<td>'+data[i].LinkTel+'</td>'+
							'<td>'+data[i].Quit+'</td>'+
							'<td>'+data[i].StaffMail+'</td>'+
							'</tr>';
	        	 $('.StaffTitle1').after(StaffStr);
	        	} 
	        },
	        error : function () {
	            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    }); 
		$('.StaffInformation').show();
	    $('.CommodityInformation').hide();
	    $('.CustomerInformation').hide();
});

//修改部分的表格2
//点击修改表格2中任意td时，弹出商品资料
$('.tableUpdate .tableUpdateTit').click(function () {
    $('.StaffInformation').hide();
    $('.CommodityInformation').show();
    $('.CustomerInformation').hide();
});

//点击修改中的商品资料时,实现根据型号完成搜索
$('.CommodityInformation-edit1').click(function () {
	var classify;
	var param;
	var Type;
	if($(this).parent().prev().children("input").attr("id")=="Model1"){
		classify="型号";
		param = $('.contract_basic #Model1').val().trim();
	}else if($(this).parent().prev().children("input").attr("id")=="good_name1"){
		classify="商品名称";
		param = $('.contract_basic #good_name1').val().trim();
	}
	
	if(param == ""){
		Type = "common";
	}else{
	    Type = "singleSelect";
	}
		$(".clickThisUpdate1").remove();
		
		$.ajax({
	        type : 'get',
	        url : 'GetCommodityInfo',
	        data : {
	        	Type : Type,
	        	classify : classify,
	        	param : param 
	        },
	        dataType : 'json',
	        success : function (data) {
	        	$(".CommodityTab1").find("tr").not(".CommodityTitle1").remove();
	        	if(data.length == 1){
	        		$.MsgBox_Unload.Alert("搜索提示", "无相应数据！");
	        	}else if(data.length > 1){
		        	 for(var i = 1 ; i <data.length; i++ ){
		        	 	var CommodityStr = '';
		        		var QuoteTime = data[i].QuoteTime;
		        		QuoteTime = QuoteTime == '0000-00-00'?"":QuoteTime;
		        		var QuoteTimeNew = globalDataHandle(QuoteTime,"");
		        		//判断修改搜做中成本价格是否有权限
		        		 if($("#hideMarkDom span").text() =="是"){
		        			CommodityStr = 
			        			'<tr class="clickThisUpdate1">'+
			        			'<td value='+data[i].ID+'>'+i+'</td>'+
			        			'<td>'+data[i].CommodityName+'</td>'+
			        			'<td>'+data[i].Model+'</td>'+
			        			'<td>'+data[i].Unit+'</td>'+
			        			'<td>'+data[i].DeliveryPeriod+'</td>'+
								'<td>'+data[i].ProducingArea+'</td>'+
								'<td>'+data[i].CostPrice+'</td>'+
								'<td>'+data[i].DiscountCost+'</td>'+
								'<td>'+data[i].UnitPrice+'</td>'+
								'<td>'+data[i].SellerPriceTwo+'</td>'+
								'<td>'+data[i].SellerPriceThree+'</td>'+
								'<td>'+data[i].Supplier+'</td>'+
								'<td>'+QuoteTimeNew+'</td>'+
								'</tr>';
		        		 }else{
		        			 CommodityStr = 
				        			'<tr class="clickThisUpdate1">'+
				        			'<td value='+data[i].ID+'>'+i+'</td>'+
				        			'<td>'+data[i].CommodityName+'</td>'+
				        			'<td>'+data[i].Model+'</td>'+
				        			'<td>'+data[i].Unit+'</td>'+
				        			'<td>'+data[i].DeliveryPeriod+'</td>'+
									'<td>'+data[i].ProducingArea+'</td>'+
									'<td style="display:none;">'+data[i].CostPrice+'</td>'+
									'<td style="display:none;">'+data[i].DiscountCost+'</td>'+
									'<td>'+data[i].UnitPrice+'</td>'+
									'<td>'+data[i].SellerPriceTwo+'</td>'+
									'<td>'+data[i].SellerPriceThree+'</td>'+
									'<td>'+data[i].Supplier+'</td>'+
									'<td>'+QuoteTimeNew+'</td>'+
									'</tr>';
		        		 }
		        	 $('.CommodityTitle1').after(CommodityStr);
		        	} 
	        	}
	        },
	        error : function () {
	            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    });
    $('.StaffInformation').hide();
    $('.CommodityInformation').show();
    $('.CustomerInformation').hide();
});

//获取修改中商品的信息放在表格2中,双击实现
$(document).on("click",".clickThisUpdate td",function(){
	var TD =  $(this).parent().find("td");
	var tdStr =
		'<tr class="ModelTr">'+
			'<td value="'+TD.eq(0).attr("value")+'">'+TD.eq(1).text()+'</td>'+
			'<td>'+TD.eq(2).text()+'</td>'+
			'<td>'+TD.eq(3).text()+' </td>'+
			'<td>'+TD.eq(4).text()+'</td>'+
			'<td><span contenteditable="true" value="1" class="QtySpan">1</span></td>'+
			'<td>'+TD.eq(8).text()+'</td>'+
			'<td>'+parseFloat(1*TD.eq(8).text()).toFixed(2)+'</td>'+
			'<td style="display:none;">'+TD.eq(6).text()+'</td>'+ 
			'<td style="display:none;"></td>'+
			'<td style="display:none;"></td>'+
			'<td style="display:none;"></td>'+
			'<td><input type="button" class="update_del" style="width:90%;height:90%;" value="删除"></td>'+
		'</tr>';
	$(".contract_update .tableUpdate").append(tdStr);
});

///////////商品信息搜索获得参数
//获取修改中商品的信息根据型号搜索时，获取的数据放在表格2中,双击实现
$(document).on("click",".clickThisUpdate1 td",function(){
	var TD = $(this).parent().find("td");
	var insertID = TD.eq(0).attr("value");
	var unit_price = Number(TD.eq(8).text());
	var clickFlag = 0;
	$(".contract_update .tableUpdate .ModelTr td:nth-child(1)").each(function(){
		if($(this).attr("value")==insertID){
			var oldqty = Number($(this).parent().find("span.QtySpan").text());
			$(this).parent().find("span.QtySpan").text(oldqty+1);
			$(this).parent().children("td").eq(6).text((oldqty+1)*unit_price);
			clickFlag = 1;
			return true;
		}
	});
	if(clickFlag == 1){
		return false;
	}
	var tdStr =
		'<tr class="ModelTr">'+
			'<td value="'+TD.eq(0).attr("value")+'">'+TD.eq(1).text()+'</td>'+
			'<td>'+TD.eq(2).text()+'</td>'+
			'<td>'+TD.eq(3).text()+' </td>'+
			'<td>'+TD.eq(4).text()+'</td>'+
			'<td><span contenteditable="true" value="1" class="QtySpan">1</span></td>'+
			'<td>'+TD.eq(8).text()+'</td>'+
			'<td>'+parseFloat(1*TD.eq(8).text()).toFixed(2)+'</td>'+
			'<td style="display:none;">'+TD.eq(6).text()+'</td>'+ 
			'<td style="display:none;"></td>'+
			'<td style="display:none;"></td>'+
			'<td style="display:none;"></td>'+
			'<td><input type="button" class="update_del" style="width:90%;height:90%;" value="删除"></td>'+
		'</tr>';
	$(".contract_update .tableUpdate").append(tdStr);
});

$(document).on("click",".update_del",function(){
	$(this).parent().parent().remove();
});

//点击商品资料关闭时
$('.CommodityInformation_close').click(function () {
    $('.CommodityInformation').hide();
});

//文档的关闭点击事件
$('#contract_close1').click(function() {
	scorllFlag = false; 
	td_CustomerFax = undefined;
	$('.MailBar_cover_color').hide();
	$('.allHidePdf').hide();
});
$('#contract_close5').click(function () {
	scorllFlag =  false; 
    $('.MailBar_cover_color').hide();
    $('.hidePdf5').hide();
});
$('#contract_close7').click(function () {
    $('.MailBar_cover_color').hide();
    $('.hidePdf7').hide();
});
$('#OtherPORMB_close').click(function () {
    $('.MailBar_cover_color').hide();
    $('#view10').hide();
});

//报价单的添加功能（点击保存时）：
 $(document).on("click","#merchandise_submit",function(){
	/*var Type="Add";*/
	var CommodityName = $('.merchandise_add input[name="CommodityName"]').val();
	var Model = $('.merchandise_add input[name="Model"]').val();
	var Unit = $('.merchandise_add input[name="Unit"]').val();
	var DeliveryPeriod = $('.merchandise_add input[name="DeliveryPeriod"]').val();
	var ProducingArea = $('.merchandise_add input[name="ProducingArea"]').val();
	var CostPrice = $('.merchandise_add input[name="CostPrice"]').val();
	var DiscountCost = $('.merchandise_add input[name="DiscountCost"]').val();
	var SellerPriceOne = $('.merchandise_add input[name="SellerPriceOne"]').val();
	var SellerPriceTwo = $('.merchandise_add input[name="SellerPriceTwo"]').val();
	var SellerPriceThree = $('.merchandise_add input[name="SellerPriceThree"]').val();
	var Supplier = $('.merchandise_add input[name="Supplier"]').val();

 	   $.ajax({
	        type : 'get',
	        url : 'QuoteCommodityAdd',
	        data : {
	        	CommodityName : CommodityName,
	        	Model : Model,
	        	Unit : Unit,
	        	DeliveryPeriod : DeliveryPeriod,
	        	ProducingArea : ProducingArea,
	        	CostPrice : CostPrice,
	        	DiscountCost : DiscountCost,
	        	SellerPriceOne: SellerPriceOne,
	        	SellerPriceTwo : SellerPriceTwo,
	        	SellerPriceThree : SellerPriceThree,
	        	Supplier : Supplier
	        },
	        dataType : 'json',
	        success : function (data) {
	        	if(data){
	        		 $.MsgBox.Alert('提示','添加成功');
	 	            $('.MailBar_cover_color').hide();
	 	            $('.merchandise_add').hide();
	        	}else{
	        		 $.MsgBox_Unload.Alert("提示", "添加失败，稍后重试！");
	        	}
	           
	        },
	        error : function () {
	            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    }); 
	    	    
    }); 

//点修改功能时
 $(document).on("click",".contract-edit",function(){
		$(".CommodityInformation").hide();
		var tr=$(this).parent();
		$('.contract_update').find('input[name="CustomerCode"]').val(tr.find('td').eq(11).text());
		$('.contract_update').find('input[name="CustomerCompany"]').val(tr.find('td').eq(2).text());
		$('.contract_update').find('input[name="CustomerName"]').val(tr.find('td').eq(3).text());
		$('.contract_update').find('input[name="CustomerTel"]').val(tr.find('td').eq(13).text());
		$('.contract_update').find('input[name="CustomerFax"]').val(tr.find('td').eq(14).text());
		$('.contract_update').find('input[name="LeadTime"]').val(tr.find('td').eq(15).text());
		$('.contract_update').find('input[name="Payment"]').val(tr.find('td').eq(16).text());
		$('.contract_update').find('input[name="StaffName"]').val(tr.find('td').eq(17).text());
		$('.contract_update').find('input[name="Department"]').val(tr.find('td').eq(18).text());
		$('.contract_update').find('input[name="ShipmentCost"]').val(tr.find('td').eq(19).text());
		$('.contract_update').find('input[name="DeliveryWay"]').val(tr.find('td').eq(20).text());
		$('.contract_update').find('input[name="ExchangeRate"]').val(tr.find('td').eq(21).text());
		$('.contract_update').find('input[name="Valid"]').val(tr.find('td').eq(22).text());
		$('.contract_update').find('input[name="Currency"]').val(tr.find('td').eq(23).text());
		$('.contract_update').find('input[name="TaxCategories"]').val(tr.find('td').eq(24).text());
		$('.contract_update').find('input[name="Versions"]').val(tr.find('td').eq(25).text());
		$('.contract_update').find('input[name="Datesent"]').val(tr.find('td').eq(26).text());
		$('.contract_update').find('input[name="Number"]').val(tr.find('td').eq(27).text());
		$('.contract_update').find('input[name="StaffMail"]').val(tr.find('td').eq(28).text());
		$('.contract_update').find('input[name="StaffTel"]').val(tr.find('td').eq(29).text());
		$('.contract_update').find('input[name="CustomerMail"]').val(tr.find('td').eq(30).text());
		
		var ID = tr.find('td.contract-edit').attr("value");
		globalQuoteID = ID;
		$(".contract_update .contract_title").attr("value",ID);  //在修改页面保存当前行的ID信息
		$(".contract_update .ModelTr").remove();
		$.ajax({
	            type : 'get',
	            url : "QuoteCommodity",
	            data : {
	            	ID:ID,
	            },
	            dataType : 'json',
	            success : function (data) { 
	            	console.log(data);
	            	for(var i = 1 ; i < data[0].item.length ; i++){
	            		var tdStr =
		            		'<tr class="ModelTr">'+
		            			'<td value="'+data[0].item[i].Commodity+'">'+data[0].item[i].Description+'</td>'+
		            			'<td class="id" value="'+data[0].item[i].CommodityID+'">'+data[0].item[i].Model+'</td>'+
		            			'<td>'+data[0].item[i].Unit+' </td>'+
		            			'<td>'+data[0].item[i].DeliveryPeriod+'</td>'+
		            			'<td><span contenteditable="true" class="QtySpan">'+data[0].item[i].Qty+'</span></td>'+
		            			'<td>'+data[0].item[i].UnitPrice+'</td>'+
		            			'<td>'+parseFloat(data[0].item[i].UnitPrice*data[0].item[i].Qty).toFixed(2)+'</td>'+
		            			'<td style="display:none;">'+data[0].item[i].CostPrice+'</td>'+ 
		            			'<td style="display:none;"></td>'+
		            			'<td style="display:none;"></td>'+
		            			'<td style="display:none;"></td>'+
		            			/*'<td><input type="button" class="update_del1" style="width:90%;height:90%;" value="删除"></td>'+*/
		            			'<td  class="ModifyOper" >'+
									'<span class="fa fa-long-arrow-up OperUp" ></span>'+
									'<span class="fa fa-trash OperDel Serial_del" ></span>'+
									'<span class="fa fa-long-arrow-down OperDown" ></span>'+
								'</td>'+
		            		'</tr>';
		            		$(".contract_update .tableUpdate").append(tdStr);
	            	}
	            	  	
	            },
	            error : function () {
	                $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	            }
	        });
		 
	   $('.MailBar_cover_color').show();
	   $('.contract_update').show();
	 }); 
 
 //修改报价单的功能（点击提交时）：
 $('#update_submit').click(function () {
	 	var that = $(this);
 		eouluGlobal.C_btnDisabled(that, true, "正在提交...");
		
	    var Type="Modify";
	 	var  ID = $(".contract_update .contract_title").attr("value");
	    var CustomerCode= $('.contract_update').find('input[name="CustomerCode"]').val();
	    var CustomerCompany= $('.contract_update').find('input[name="CustomerCompany"]').val();
	    var CustomerName=$('.contract_update').find('input[name="CustomerName"]').val();
	    var CustomerTel= $('.contract_update').find('input[name="CustomerTel"]').val();
	    var CustomerFax=$('.contract_update').find('input[name="CustomerFax"]').val();
	    var LeadTime=$('.contract_update').find('input[name="LeadTime"]').val();
	    var Payment= $('.contract_update').find('input[name="Payment"]').val();
	    var StaffName= $('.contract_update').find('input[name="StaffName"]').val();
	    var Department= $('.contract_update').find('input[name="Department"]').val();
	    var ShipmentCost= $('.contract_update').find('input[name="ShipmentCost"]').val();
		var DeliveryWay=$('.contract_update').find('input[name="DeliveryWay"]').val();
		var ExchangeRate= $('.contract_update').find('input[name="ExchangeRate"]').val();
		var Valid= $('.contract_update').find('input[name="Valid"]').val();
		var Currency= $('.contract_update').find('input[name="Currency"]').val();
		var TaxCategories=  $('.contract_update').find('input[name="TaxCategories"]').val();
		var Versions= $('.contract_update').find('input[name="Versions"]').val();
		var Datesent=  $('.contract_update').find('input[name="Datesent"]').val();
		var Number= $('.contract_update').find('input[name="Number"]').val();
		var StaffMail= $('.contract_update').find('input[name="StaffMail"]').val();
		var StaffTel=  $('.contract_update').find('input[name="StaffTel"]').val();
		var CustomerMail= $('.contract_update').find('input[name="CustomerMail"]').val();
		var Exist;
	    var Commodity = [];
	    var Description = [];
	    var PaymentDate = [];
	    var OrderNO = []; 
	    var Qty = [];
	    var CommodityID =[];
	    
	    if($(".contract_update .ModelTr").length != 0){
			Exist = "yes";
			for(var i = 0 ; i < $(".contract_update .ModelTr").length ; i++){
				Commodity.push($(".contract_update .ModelTr").eq(i).find('td').eq(0).attr("value"));
				Description.push($(".contract_update .ModelTr").eq(i).find('td').eq(10).text());
				PaymentDate.push($(".contract_update .ModelTr").eq(i).find('td').eq(8).text());
				OrderNO.push($(".contract_update .ModelTr").eq(i).find('td').eq(9).text());
				Qty.push($(".contract_update .ModelTr").eq(i).find('td').eq(4).find('.QtySpan').text());
				if($(".contract_update .ModelTr").eq(i).find('td').eq(1).attr("value")){
					CommodityID.push($(".contract_update .ModelTr").eq(i).find('td').eq(1).attr("value"));
				}
				else{
					CommodityID.push(0);
				}
				
			}
		}
		else{
			Exist = "no";
		}
    	$.ajax({
            type : 'get',
            url : "QuotationSystemOperate",
            data : {
            	ID:ID,
            	Type : Type,
	        	CustomerCode : CustomerCode,
	        	CustomerCompany : CustomerCompany,
	        	CustomerName : CustomerName,
	        	CustomerTel : CustomerTel,
	        	CustomerFax : CustomerFax,
	        	LeadTime : LeadTime,
	        	Payment : Payment,
	        	StaffName: StaffName,
	        	Department : Department,
	        	ShipmentCost : ShipmentCost,
	        	DeliveryWay : DeliveryWay,
	        	ExchangeRate : ExchangeRate,
	        	Valid : Valid,
	        	Currency : Currency,
	        	TaxCategories : TaxCategories,
	        	Versions : Versions,
	        	Datesent : Datesent,
	            Number : Number,
	            StaffMail : StaffMail,
	            StaffTel : StaffTel,
	            CustomerMail : CustomerMail,
	            Exist : Exist,
	            Commodity : Commodity,
	            Description : Description,
	            PaymentDate : PaymentDate,
	            OrderNO : OrderNO,
	            CommodityID : CommodityID,
	            Qty : Qty
            },
            dataType : 'json',
            success : function (data) {   
            	if(data){
                	$.MsgBox.Alert('提示','修改成功');
    	            $('.MailBar_cover_color').hide();
    	            $('.hidePdf').hide();
            	}else{
            		$.MsgBox_Unload.Alert("提示", "修改失败，稍后重试！");
            	}
            },
            error : function () {
                $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
            },
            complete: function(XMLHttpRequest, textStatus){
                eouluGlobal.C_btnAbled(that, true, "提交");
            }
        });
	});

$('.contractUpdate_close, #update_cancel').click(function() {
	$('.MailBar_cover_color').hide();
	$('.contract_update').hide();
	globalQuoteID = null;
});

 //合同页面的阿拉伯数字转换为大写的汉字
	 function changeMoneyToChinese(money){  
         var cnNums = new Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖"); //汉字的数字  
         var cnIntRadice = new Array("","拾","佰","仟"); //基本单位  
         var cnIntUnits = new Array("","万","亿","兆"); //对应整数部分扩展单位  
         var cnDecUnits = new Array("角","分","毫","厘"); //对应小数部分单位  
         var cnIntLast = "元"; //整型完以后的单位  
         var maxNum = 999999999999999.9999; //最大处理的数字  
         var IntegerNum; //金额整数部分  
         var DecimalNum; //金额小数部分  
         var ChineseStr=""; //输出的中文金额字符串  
         var parts; //分离金额后用的数组，预定义  
         if( money == "" ){  
             return "";  
         }  
         money = parseFloat(money);  
         if( money >= maxNum ){  
             $.alert('超出最大处理数字');  
             return "";  
         }  
         if( money == 0 ){  
             ChineseStr = cnNums[0]+cnIntLast  
             return ChineseStr;  
         }  
         money = money.toString(); //转换为字符串  
         if( money.indexOf(".") == -1 ){  
             IntegerNum = money;  
             DecimalNum = '';  
         }else{  
             parts = money.split(".");  
             IntegerNum = parts[0];  
             DecimalNum = parts[1].substr(0,4);  
         }  
         if( parseInt(IntegerNum,10) > 0 ){//获取整型部分转换  
             zeroCount = 0;  
             IntLen = IntegerNum.length;  
             for( i=0;i<IntLen;i++ ){  
                 n = IntegerNum.substr(i,1);  
                 p = IntLen - i - 1;  
                 q = p / 4;  
                 m = p % 4;  
                 if( n == "0" ){  
                     zeroCount++;  
                 }else{  
                     if( zeroCount > 0 ){  
                         ChineseStr += cnNums[0];  
                     }  
                     zeroCount = 0; //归零  
                     ChineseStr += cnNums[parseInt(n)]+cnIntRadice[m];  
                 }  
                 if( m==0 && zeroCount<4 ){  
                     ChineseStr += cnIntUnits[q];  
                 }  
             }  
             ChineseStr += cnIntLast;  
         }  
         if( DecimalNum!= '' ){//小数部分  
             decLen = DecimalNum.length;  
             for( i=0; i<decLen; i++ ){  
                 n = DecimalNum.substr(i,1);  
                 if( n != '0' ){  
                     ChineseStr += cnNums[Number(n)]+cnDecUnits[i];  
                 }  
             }  
         }  
         if( ChineseStr == '' ){  
             ChineseStr += cnNums[0]+cnIntLast;  
         }  
         return ChineseStr;  
     }  
//科学计数法
var fmoney=function(s, n) {
    n = n > 0 && n <= 20 ? n : 2;
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
    var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
    t = "";
    for (i = 0; i < l.length; i++) {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    }
    return t.split("").reverse().join("") + "." + r;
};
 
//返回正常的数
 var rmoney=function(s) {  
    return parseFloat(s.replace(/[^\d\.-]/g, ""));  
};
 
if($('input[name="selected"]:checked').val()=='SingleSelect'){
    $('.select-content').css('margin-left','33%');
}else{
    $('.select-content').css('margin-left','23%');

}
function Check(selected){
	 if (selected == "SingleSelect") {
	        $('.select2').hide();
	        $('.select-content').css('margin-left','33%');
	    } else {
	        $('.select2').show();
	        $('.select-content').css('margin-left','23%');
	    }
}

function INSearch() {
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

//报价单的添加商品管理的功能（点击提交时）：
$(document).on("click","#add_submit",function(){
	var CustomerCompany = $('.contract_add input[name="CustomerCompany"]').val();
	var ShipmentCost = $('.contract_add input[name="ShipmentCost"]').val();
	var StaffName = $('.contract_add input[name="StaffName"]').val();
	if(CustomerCompany == '' || ShipmentCost == '' || StaffName ==''){
		 $.MsgBox_Unload.Alert('提示','客户名称，业务员，运输费用(USD)为必填项！');
		 return false;
	}
 	var that = $(this);
 	eouluGlobal.C_btnDisabled(that, true, "正在提交...");
	
	var Type="Add";
	var CustomerCode = $('.contract_add input[name="CustomerCode"]').val();
	var CustomerName = $('.contract_add input[name="CustomerName"]').val();
	var CustomerTel = $('.contract_add input[name="CustomerTel"]').val();
	var CustomerFax = $('.contract_add input[name="CustomerFax"]').val();
	var LeadTime = $('.contract_add input[name="LeadTime"]').val();
	var Payment = $('.contract_add input[name="Payment"]').val();
	var Department = $('.contract_add input[name="Department"]').val();
	var DeliveryWay = $('.contract_add input[name="DeliveryWay"]').val();
	var ExchangeRate = $('.contract_add input[name="ExchangeRate"]').val();
	var Valid = $('.contract_add input[name="Valid"]').val();
	var Currency = $('.contract_add input[name="Currency"]').val();
	var TaxCategories = $('.contract_add input[name="TaxCategories"]').val();
	var Versions = $('.contract_add input[name="Versions"]').val();
	var Datesent = $('.contract_add input[name="Datesent"]').val();
	var iNumber = $('.contract_add input[name="Number"]').val();
	var StaffMail = $('.contract_add input[name="StaffMail"]').val();
	var StaffTel = $('.contract_add input[name="StaffTel"]').val();
	var CustomerMail = $('.contract_add input[name="CustomerMail"]').val();
	
	var Exist;
   var Commodity = [];
   var Description = [];
   var PaymentDate = [];
   var OrderNO = [];
   var Qty = [];
   if($(".contract_add .ModelTr").length != 0){
		Exist = "yes";
		for(var i = 0 ; i < $(".contract_add .ModelTr").length ; i++){
			Commodity.push($(".contract_add .ModelTr").eq(i).find('td').eq(0).attr("value"));
			Description.push($(".contract_add .ModelTr").eq(i).find('td').eq(10).text());
			PaymentDate.push($(".contract_add .ModelTr").eq(i).find('td').eq(8).text());
			OrderNO.push($(".contract_add .ModelTr").eq(i).find('td').eq(9).text());
			Qty.push($(".contract_add .ModelTr").eq(i).find('td').eq(4).find('.QtySpan').text());
		}
	}
	else{
		Exist = "no";
	}

	   $.ajax({
	        type : 'get',
	        url : 'QuotationSystemOperate',
	        data : {
	        	Type : Type,
	        	CustomerCode : CustomerCode,
	        	CustomerCompany : CustomerCompany,
	        	CustomerName : CustomerName,
	        	CustomerTel : CustomerTel,
	        	CustomerFax : CustomerFax,
	        	LeadTime : LeadTime,
	        	Payment : Payment,
	        	StaffName: StaffName,
	        	Department : Department,
	        	ShipmentCost : ShipmentCost,
	        	DeliveryWay : DeliveryWay,
	        	ExchangeRate : ExchangeRate,
	        	Valid : Valid,
	        	Currency : Currency,
	        	TaxCategories : TaxCategories,
	        	Versions : Versions,
	        	Datesent : Datesent,
	            Number : iNumber,
	            StaffMail : StaffMail,
	            StaffTel : StaffTel,
	            CustomerMail : CustomerMail,
	            Exist : Exist,
	            Commodity : Commodity,
	            Description : Description,
	            PaymentDate : PaymentDate,
	            OrderNO : OrderNO,
	            Qty : Qty
	        },
	        dataType : 'json',
	        success : function (data) {
	        	if(data){
		            $.MsgBox.Alert('提示','添加成功');
		            $('.MailBar_cover_color, .contract_add').hide();
	        	}else{
	        		$.MsgBox_Unload.Alert("提示", "添加失败，稍后重试！");
	        	}
	        },
	        error : function () {
	            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	        },
	        complete: function(XMLHttpRequest, textStatus){
	            eouluGlobal.C_btnAbled(that, true, "提交");
	        }
	    }); 	    
   });
	$(document).on("scroll",function(){
		if(scorllFlag){
			var sc=$(window).scrollTop();
			if(sc>200){
			    $(".goTop").css("display","block");
			}
			else{
			   $(".goTop").css("display","none");
			}
		}
	});
	$(".goTop").click(function(){
	   var sc=$(window).scrollTop();
	   $('body,html').animate({scrollTop:0},200);
	});
	//初次加载  整机 配件模板
	$('.contract-show1').click(function () {
		td_CustomerFax = $(this).parent().parent().find(".td_CustomerFax").text();
		console.log($(this).parent().parent().index());
		$(".allHidePdf").attr("value",$(this).parent().parent().index());
	    $('.MailBar_cover_color').show();
	    $(".allHidePdf input[type='button']").show();
	    $("#exportEXCEL").hide();
	    $("#submit_n1").css("top","250px");
	    
	    var ID = $(this).parent().parent().find("td").eq(0).attr("value");
	    console.log(ID);
		$("#view1 .yejiao1").attr("value",ID);
		var Type="CompleteRMB";
	    /* 获取隐藏信息 */
	    var thisList = $(this).parent().parent().children();
	    $("#view1 .CustomerCompany").text("").text(thisList.eq(2).text());
	    $("#view1 .Number").text("").text(thisList.eq(4).text());
	    $("#view1 .DateSent").text("").text(thisList.eq(26).text());
	    $("#view1 .Versions").text("").text(thisList.eq(25).text());
	    $("#view1 .CustomerName").text("").text(thisList.eq(12).text());
	    $("#view1 .CustomerTel").text("").text(thisList.eq(13).text());
	    $("#view1 .LeadTime").text("").text(thisList.eq(15).text());
	    $("#view1 .Payment").text("").text(thisList.eq(16).text());
	    $("#view1 .StaffName").text("").text(thisList.eq(17).text());
	    $("#view1 .Valid").text("").text(thisList.eq(22).text());
	    $("#view1 .StaffMail").text("").text(thisList.eq(28).text());
	    $("#view1 .StaffTel").text("").text(thisList.eq(29).text());
	    $("#view1 .CustomerMail").text("").text(thisList.eq(30).text());
	    $("#view1 .ShipmentInsuranceRMB").html("").html(thisList.eq(19).text());
		$("#view1 .pdf_one_tr").remove();
		$.ajax({
	            type : 'get',
	            url : "QuoteCommodity",
	            data : {
	            	ID:ID,
	            	Type : Type
	            },
	            dataType : 'json',
	            success : function (data) {  
        	        //第一个集合
    				var itemStr="";
    				if(data[0].item.length > 1){
    					for(var i = 1 ; i < data[0].item.length; i++){
		            		 itemStr += '<tr class="pdf_one_tr">'+
				                '<td class="Item" value="'+data[0].item[i].CommodityID+'">'+i+'</td>'+
				                '<td class="PartNumber">'+data[0].item[i].Model+'</td>'+
				                '<td class="Description tl">'+data[0].item[i].Description+'</td>'+
				               ' <td  style="display:none" class="EachRMB" contenteditable="true">'+data[0].item[i].UnitPrice+'</td>'+
				               ' <td class="Qty" contenteditable="true">'+data[0].item[i].Qty+'</td>'+
				               ' <td  style="display:none" class="ExtendedRMB" contenteditable="true">'+Math.round(parseFloat(data[0].item[i].UnitPrice*data[0].item[i].Qty))+'</td>'+
				                '<td  class="Oper" >'+
									'<span class="fa fa-long-arrow-up OperUp" ></span>'+
									'<span class="fa fa-trash OperDel Serial_del" ></span>'+
									'<span class="fa fa-long-arrow-down OperDown" ></span>'+
								'</td>'+
				               '</tr>';
		            		}
						$("#view1 .pdf_one_tab").append(itemStr);
    				}	
    				else{
    					$("#view1 .pdf_one_tr").remove();
    				}
	            				
    				var totalNum = 0;
	        		for(var j = 0; j< $("#view1 .pdf_one_tr").length ; j++){
	        			totalNum += parseFloat($("#view1 .pdf_one_tr").eq(j).find(".ExtendedRMB").text());
	        		  }	
        			var Shipmenting=Math.round(parseFloat(thisList.eq(19).text()* thisList.eq(21).text()));
    				$("#view1 .ShipmentInsuranceRMB").text(Shipmenting);
    				totalNum = Math.round(totalNum * thisList.eq(21).text()*thisList.eq(24).text()*1.07*1.03);
			        //第二个集合
	            	if(data[0].price.length > 1){
	            		for(var ii = 1 ; ii < data[0].price.length; ii++){
		            		//放第二个集合的序号
		            		$("#view1 .yemei").attr("value",data[0].price[ii].ID);
		            		$("#view1 .Gifts").text(" ").text(data[0].price[ii].Gifts);
		            		data[0].price[ii].GiftsTotal==0.00?$("#view1 .GiftsTotal").text(" "):$("#view1 .GiftsTotal").text(Math.round(data[0].price[ii].GiftsTotal));
		            		$("#view1 .TotalRMB").text(Math.round(data[0].price[ii].SubTotal));
	            		}
	            	}
	            	else{
	            		$("#view1 .TotalRMB").text(totalNum);
	            		$("#view1 .Gifts").text(" ");
	            		$("#view1 .GiftsTotal").text(" ");
	            	}   	
	            	var GiftsTotal= $('#view1 .GiftsTotal').text();
						(GiftsTotal == " ")?(GiftsTotal=0 ):(GiftsTotal = GiftsTotal);
					var TotalIncluding = Math.round(parseFloat($("#view1 .TotalRMB").text().replace(/,/g,'')) +  parseFloat(Shipmenting)+parseFloat(GiftsTotal));
	            	
	        		if(TotalIncluding == "NaN"){
	        			TotalIncluding = 0;
	        		}
	        		 
	        		$("#view1 .TotalIncludingVATRMB").text("").text(TotalIncluding);
	        		
	            },
	            error : function () {
	                $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	            }
	        });
		scorllFlag = !scorllFlag;
		 $('.allHidePdf').show(); 
         $('#view1').show();
         $('#view2,#view3,#view4,#EgoMachineView,#EgoAccessoriesView, #costComparisonView').hide(); 
         $("#RMBMachine").addClass("isHover").siblings().removeClass("isHover");
         // $(".MailBar_cover_color").css("height",$("#view1").height()+130);
	});
	
	//整机 配件      切换模板 加载**************************************************
	$(".Sidebar_Price div").click(function(){
		$(this).addClass("isHover").siblings().removeClass("isHover");
		console.log($(this).attr("id"));
		var currentId = $(this).attr("id");
		var currentTr = $("#table1").find("tr").eq($(".allHidePdf").attr("value"));
		var thisList =currentTr.children(); 
		var ID = currentTr.find("td").eq(0).attr("value");
	    console.log(ID);
	    
	    $(".allHidePdf input[type='button']").show();
	    $("#exportEXCEL").hide();
	    $("#submit_n1").css("top","250px");
	    
		if(currentId=="RMBMachine"){
			// $(".MailBar_cover_color").css("height",$("#view1").height()+130);
			$("#view1 .yejiao1").attr("value",ID);
			var Type="CompleteRMB";
		    /* 获取隐藏信息 */
		    $("#view1 .CustomerCompany").text("").text(thisList.eq(2).text());
		    $("#view1 .Number").text("").text(thisList.eq(4).text());
		    $("#view1 .DateSent").text("").text(thisList.eq(26).text());
		    $("#view1 .Versions").text("").text(thisList.eq(25).text());
		    $("#view1 .CustomerName").text("").text(thisList.eq(12).text());
		    $("#view1 .CustomerTel").text("").text(thisList.eq(13).text());
		    $("#view1 .LeadTime").text("").text(thisList.eq(15).text());
		    $("#view1 .Payment").text("").text(thisList.eq(16).text());
		    $("#view1 .StaffName").text("").text(thisList.eq(17).text());
		    $("#view1 .Valid").text("").text(thisList.eq(22).text());
		    $("#view1 .StaffMail").text("").text(thisList.eq(28).text());
		    $("#view1 .StaffTel").text("").text(thisList.eq(29).text());
		    $("#view1 .CustomerMail").text("").text(thisList.eq(30).text());
			$("#view1 .pdf_one_tr").remove();
			$.ajax({
		            type : 'get',
		            url : "QuoteCommodity",
		            data : {
		            	ID:ID,
		            	Type : Type
		            },
		            dataType : 'json',
		            success : function (data) {  
	            	    //第一个集合
        				var itemStr="";
        				if(data[0].item.length > 1){
        					for(var i = 1 ; i < data[0].item.length; i++){
			            		 itemStr += '<tr class="pdf_one_tr">'+
					                '<td class="Item" value="'+data[0].item[i].CommodityID+'">'+i+'</td>'+
					                '<td class="PartNumber">'+data[0].item[i].Model+'</td>'+
					                '<td class="Description tl">'+data[0].item[i].Description+'</td>'+
					               ' <td  style="display:none" class="EachRMB" contenteditable="true">'+data[0].item[i].UnitPrice+'</td>'+
					               ' <td class="Qty" contenteditable="true">'+data[0].item[i].Qty+'</td>'+
					               ' <td  style="display:none" class="ExtendedRMB" contenteditable="true">'+parseFloat(data[0].item[i].UnitPrice*data[0].item[i].Qty).toFixed(2)+'</td>'+
					               '<td  class="Oper" >'+
										'<span class="fa fa-long-arrow-up OperUp" ></span>'+
										'<span class="fa fa-trash OperDel Serial_del" ></span>'+
										'<span class="fa fa-long-arrow-down OperDown" ></span>'+
									'</td>'+
					               '</tr>';
			            		}
    						$("#view1 .pdf_one_tab").append(itemStr);
        				}	
        				else{
        					$("#view1 .pdf_one_tr").remove();
        				}
		            				
	    				var totalNum = 0;
		        		for(var j = 0; j< $("#view1 .pdf_one_tr").length ; j++){
		        			totalNum += Math.round(parseFloat($("#view1 .pdf_one_tr").eq(j).find(".ExtendedRMB").text()));
		        		  }	
		        		var Shipmenting=Math.round(parseFloat(thisList.eq(19).text()* thisList.eq(21).text()));
        				$("#view1 .ShipmentInsuranceRMB").text("").text(Shipmenting);
        				totalNum = Math.round(totalNum * thisList.eq(21).text()*thisList.eq(24).text()*1.07*1.03);
				        //第二个集合
		            	if(data[0].price.length > 1){
		            		for(var ii = 1 ; ii < data[0].price.length; ii++){
			            		//放第二个集合的序号
			            		$("#view1 .yemei").attr("value",data[0].price[ii].ID);
			            		$("#view1 .Gifts").text(data[0].price[ii].Gifts);
			            		data[0].price[ii].GiftsTotal==0.00?$("#view1 .GiftsTotal").text(" "):$("#view1 .GiftsTotal").text(Math.round(data[0].price[ii].GiftsTotal));
			            		$("#view1 .TotalRMB").text(Math.round(data[0].price[ii].SubTotal));
			            		$("#view1 .TotalIncludingVATRMB").text(Math.round(data[0].price[ii].FinalTotal));
		            		}
		            	}
		            	else{
		            		$("#view1 .TotalRMB").text("").text(totalNum);
		            		$("#view1 .Gifts").text(" ");
		            		$("#view1 .GiftsTotal").text(" ");
    						var TotalIncluding = Math.round(parseFloat($("#view1 .TotalRMB").text().replace(/,/g,'')) +  parseFloat(Shipmenting));
    		            	
    		        		if(TotalIncluding == "NaN"){
    		        			TotalIncluding = 0;
    		        		}
    		        		$("#view1 .TotalIncludingVATRMB").text(TotalIncluding);
		            	}   	
		            },
		            error : function () {
		                $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
		            }
		        });
			 $('#view1').show();
			 $('#view3,#view2,#view4,#EgoMachineView,#EgoAccessoriesView,#costComparisonView').hide();
		}
		else if(currentId=="USDMachine"){
			// $(".MailBar_cover_color").css("height",$("#view2").height()+130);
			$("#view2 .yejiao1").attr("value",ID);
			var Type="CompleteUSD";
		    /* 获取隐藏信息 */
		    $("#view2 .CustomerCompany").text("").text(thisList.eq(2).text());
		    $("#view2 .Number").text("").text(thisList.eq(4).text());
		    $("#view2 .DateSent").text("").text(thisList.eq(26).text());
		    $("#view2 .Versions").text("").text(thisList.eq(25).text());
		    $("#view2 .CustomerName").text("").text(thisList.eq(12).text());
		    $("#view2 .CustomerTel").text("").text(thisList.eq(13).text());
		    $("#view2 .LeadTime").text("").text(thisList.eq(15).text());
		    $("#view2 .Payment").text("").text(thisList.eq(16).text());
		    $("#view2 .StaffName").text("").text(thisList.eq(17).text());
		    $("#view2 .Valid").text("").text(thisList.eq(22).text());
		    $("#view2 .StaffMail").text("").text(thisList.eq(28).text());
		    $("#view2 .StaffTel").text("").text(thisList.eq(29).text());
		    $("#view2 .CustomerMail").text("").text(thisList.eq(30).text());
		    
			$("#view2 .pdf_one_tr").remove();
			$.ajax({
		            type : 'get',
		            url : "QuoteCommodity",
		            data : {
		            	ID:ID,
		            	Type : Type
		            },
		            dataType : 'json',
		            success : function (data) {  
		            	console.log(data);
		            		//第一个集合
			            	var itemStr="";
			            	if(data[0].item.length > 1){
			            		for(var i = 1 ; i < data[0].item.length; i++){
				            		itemStr += '<tr class="pdf_one_tr">'+
						                '<td class="Item" value="'+data[0].item[i].CommodityID+'">'+i+'</td>'+
						                '<td class="PartNumber">'+data[0].item[i].Model+'</td>'+
						                '<td class="Description tl">'+data[0].item[i].Description+'</td>'+
						               ' <td  class="EachRMB" style="display:none;" contenteditable="true">'+data[0].item[i].UnitPrice+'</td>'+
						               ' <td class="Qty" contenteditable="true">'+data[0].item[i].Qty+'</td>'+
						               ' <td  class="ExtendedUSD" style="display:none;" contenteditable="true">'+parseFloat(data[0].item[i].UnitPrice*data[0].item[i].Qty).toFixed(2)+'</td>'+
						               '<td  class="Oper" >'+
											'<span class="fa fa-long-arrow-up OperUp" ></span>'+
											'<span class="fa fa-trash OperDel Serial_del" ></span>'+
											'<span class="fa fa-long-arrow-down OperDown" ></span>'+
										'</td>'+	
						               '</tr>';
				            	}
							$("#view2 .pdf_two_tab").append(itemStr);
			            	}
			            	else{
	        					$("#view2 .pdf_one_tr").remove();
	        				}
			            	var totalNum = 0;
			        		for(var i = 0; i< $("#view2 .pdf_one_tr").length ; i++){
			        			totalNum += Math.round(parseFloat($("#view2 .pdf_one_tr").eq(i).find(".ExtendedUSD").text()));
			        		}
							var Shipmenting=Math.round(parseFloat(thisList.eq(19).text()));
							 $("#view2 .ShipmentInsuranceUSD").text("").text(Shipmenting);
				            	//第二个集合
			            	if(data[0].price.length > 1){
			            		for(var i = 1 ; i < data[0].price.length; i++){
				            		$("#view2 .yemei").attr("value",data[0].price[i].ID);
				            		$("#view2 .Gifts").text(" ").text(data[0].price[i].Gifts);
				            		data[0].price[i].GiftsTotal==0.00?$("#view2 .GiftsTotal").text(" "):$("#view2 .GiftsTotal").text(" ").text(Math.round(data[0].price[i].GiftsTotal));
				            		$("#view2 .TotalUSD").text(Math.round(data[0].price[i].SubTotal));
				            		$("#view2 .FinalTotalCIPUSD").text(Math.round(data[0].price[i].FinalTotal));
			            		}
			            	}
			            	else{
			            		$("#view2 .TotalUSD").text("").text(totalNum);
			            		$("#view2 .Gifts").text(" ");
			            		$("#view2 .GiftsTotal").text(" ");
			            		var TotalIncluding = Math.round(parseFloat($("#view2 .TotalUSD").text().replace(/,/g,'')) +  parseFloat(Shipmenting));
								$("#view2 .FinalTotalCIPUSD").text("").text(TotalIncluding);
			            	} 
		            },
		            error : function () {
		                $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
		            }
		        });
			 $('#view2').show(); 
			 $('#view1,#view4,#view3,#EgoMachineView,#EgoAccessoriesView,#costComparisonView').hide();
		}
		else if(currentId=="RMBAccessories"){
			// $(".MailBar_cover_color").css("height",$("#view3").height()+130);
			$("#view3 .yejiao1").attr("value",ID);
			var Type="PartsRMB";
		    /* 获取隐藏信息 */
		    $("#view3 .CustomerCompany").text("").text(thisList.eq(2).text());
		    $("#view3 .Number").text("").text(thisList.eq(4).text());
		    $("#view3 .DateSent").text("").text(thisList.eq(26).text());
		    $("#view3 .Versions").text("").text(thisList.eq(25).text());
		    $("#view3 .CustomerName").text("").text(thisList.eq(12).text());
		    $("#view3 .CustomerTel").text("").text(thisList.eq(13).text());
		    $("#view3 .LeadTime").text("").text(thisList.eq(15).text());
		    $("#view3 .Payment").text("").text(thisList.eq(16).text());
		    $("#view3 .StaffName").text("").text(thisList.eq(17).text());
		    $("#view3 .Valid").text("").text(thisList.eq(22).text());
		    $("#view3 .StaffMail").text("").text(thisList.eq(28).text());
		    $("#view3 .StaffTel").text("").text(thisList.eq(29).text());
		    $("#view3 .CustomerMail").text("").text(thisList.eq(30).text());
		   
			$("#view3 .pdf_one_tr").remove();
			$.ajax({
		            type : 'get',
		            url : "QuoteCommodity",
		            data : {
		            	ID:ID,
		            	Type : Type
		            },
		            dataType : 'json',
		            success : function (data) {  
		            	console.log(data);
		            	
		            	var itemStr="";
		            	if(data[0].item.length > 1){
		            		
		            		for(var i = 1 ; i < data[0].item.length; i++){
		            			var data_EachRMB = Math.round(data[0].item[i].UnitPrice* thisList.eq(21).text()* thisList.eq(24).text()*1.07*1.03);
		            			var data_ExtendedRMB = Math.round(parseFloat(data[0].item[i].UnitPrice*data[0].item[i].Qty).toFixed(2)* thisList.eq(21).text()* thisList.eq(24).text()*1.07*1.03);
	            				var EachRMBStr = '<td class="EachRMB" contenteditable="true" value="'+data_EachRMB+'">'+data_EachRMB+'</td>';
	            				var ExtendedUSDStr = '<td class="ExtendedRMB" contenteditable="true" value="'+data_ExtendedRMB+'">'+data_ExtendedRMB+'</td>';

	            				itemStr += '<tr class="pdf_one_tr">'+
					                '<td class="Item" value="'+data[0].item[i].CommodityID+'">'+i+'</td>'+
					                '<td class="PartNumber">'+data[0].item[i].Model+'</td>'+
					                '<td class="Description tl">'+data[0].item[i].Description+'</td>';
					            var iUnitPriceRMB = data[0].item[i].UnitPriceRMB;
					            var iExtendedRMB = data[0].item[i].ExtendedRMB;
					            if(iUnitPriceRMB==""||iUnitPriceRMB=="--"||iUnitPriceRMB==undefined){
					            	itemStr+=EachRMBStr;
					            }else{
					            	itemStr+='<td class="EachRMB" contenteditable="true" value="'+Math.round(iUnitPriceRMB)+'">'+Math.round(iUnitPriceRMB)+'</td>';
					            }
					            itemStr+='<td class="Qty" contenteditable="true" value="'+data[0].item[i].Qty+'">'+data[0].item[i].Qty+'</td>';
					            if(iExtendedRMB==""||iExtendedRMB=="--"||iExtendedRMB==undefined){
					            	itemStr+=ExtendedUSDStr;
					            }else{
					            	itemStr+='<td class="ExtendedRMB" contenteditable="true" value="'+Math.round(iExtendedRMB)+'">'+Math.round(iExtendedRMB)+'</td>';
					            }
					            itemStr+='<td  class="Oper" >'+
										'<span class="fa fa-long-arrow-up OperUp" ></span>'+
										'<span class="fa fa-trash OperDel Serial_del" ></span>'+
										'<span class="fa fa-long-arrow-down OperDown" ></span>'+
									'</td>'+
					               '</tr>';
			            	}
			            	$("#view3 .pdf_three_tab").append(itemStr);
		            	}
		            	else{
	    					$("#view3 .pdf_one_tr").remove();
	    				}

		            	var totalNum = 0;
		        		for(var iii = 0; iii< $("#view3 .pdf_one_tr").length ; iii++){
		        			totalNum += Math.round($("#view3 .pdf_one_tr").eq(iii).find(".ExtendedRMB").text());
		        		}

		            	var Shipmenting=Math.round(parseFloat(thisList.eq(19).text()* thisList.eq(21).text()));
		        		$("#view3 .ShipmentInsuranceRMB").text("").text(Shipmenting);

		            	//第二个集合
		            	if(data[0].price.length > 1){
		            		for(var ii = 1 ; ii < data[0].price.length; ii++){
			            		$("#view3 .yemei").attr("value",data[0].price[ii].ID);
			            		$("#view3 .Gifts").text(" ").text(data[0].price[ii].Gifts);
			            		data[0].price[ii].GiftsTotal==0.00?$("#view3 .GiftsTotal").text(" "):$("#view3 .GiftsTotal").text(Math.round(data[0].price[i].GiftsTotal));
			            		$("#view3 .TotalRMB").text(Math.round(data[0].price[ii].SubTotal));
			            		$("#view3 .TotalIncludingVATRMB").text(Math.round(data[0].price[ii].FinalTotal));
			            	}
		            	}
		            	else{
		            		$("#view3 .Gifts").text(" ");
		            		$("#view3 .GiftsTotal").text(" ");
			        		var TotalIncluding;
							TotalIncluding = Math.round(parseFloat(totalNum) +  parseFloat(Shipmenting));
							   
			        		if(TotalIncluding == "NaN"){
			        			TotalIncluding = 0;
			        		}
			        		 $("#view3 .TotalRMB").text("").text(totalNum);
			        		 $("#view3 .TotalIncludingVATRMB").text("").text(TotalIncluding);
		            	} 
		            },
		            error : function () {
		            	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
		            }
		        });
			 $('#view3').show(); 
			 $('#view1,#view2,#view4,#EgoMachineView,#EgoAccessoriesView,#costComparisonView').hide();
		}
		else if(currentId=="USDAccessories"){
			// $(".MailBar_cover_color").css("height",$("#view4").height()+130);
			$("#view4 .yejiao1").attr("value",ID);
			var Type = "PartsUSD";
		    /* 获取隐藏信息 */
		    $("#view4 .CustomerCompany").text("").text(thisList.eq(2).text());
		    $("#view4 .Number").text("").text(thisList.eq(4).text());
		    $("#view4 .DateSent").text("").text(thisList.eq(26).text());
		    $("#view4 .Versions").text("").text(thisList.eq(25).text());
		    $("#view4 .CustomerName").text("").text(thisList.eq(12).text());
		    $("#view4 .CustomerTel").text("").text(thisList.eq(13).text());
		    $("#view4 .LeadTime").text("").text(thisList.eq(15).text());
		    $("#view4 .Payment").text("").text(thisList.eq(16).text());
		    $("#view4 .StaffName").text("").text(thisList.eq(17).text());
		    $("#view4 .Valid").text("").text(thisList.eq(22).text());
		    $("#view4 .StaffMail").text("").text(thisList.eq(28).text());
		    $("#view4 .StaffTel").text("").text(thisList.eq(29).text());
		    $("#view4 .CustomerMail").text("").text(thisList.eq(30).text());
			$("#view4 .pdf_one_tr").remove();
			$.ajax({
		            type : 'get',
		            url : "QuoteCommodity",
		            data : {
		            	ID:ID,
		            	Type : Type
		            },
		            dataType : 'json',
		            success : function (data) {  
		            	console.log(data);
		            	var itemStr="";
		            	if(data[0].item.length > 1){
		            		for(var i = 1 ; i < data[0].item.length; i++){
	            				var EachRMBStr = ' <td class="EachRMB" contenteditable="true" value="'+Math.round(data[0].item[i].UnitPrice)+'">'+Math.round(data[0].item[i].UnitPrice)+'</td>';
	            				var ExtendedUSDStr = ' <td class="ExtendedUSD" contenteditable="true" value="'+Math.round(parseFloat(data[0].item[i].UnitPrice*data[0].item[i].Qty))+'">'+Math.round(parseFloat(data[0].item[i].UnitPrice*data[0].item[i].Qty))+'</td>';
		            			
			            		itemStr += '<tr class="pdf_one_tr">'+
					                '<td class="Item" value="'+data[0].item[i].CommodityID+'">'+i+'</td>'+
					                '<td class="PartNumber">'+data[0].item[i].Model+'</td>'+
					                '<td class="Description tl">'+data[0].item[i].Description+'</td>';

					                var iUnitPriceUSD = data[0].item[i].UnitPriceUSD;
					                var iExtendedUSD = data[0].item[i].ExtendedUSD;
					                if(iUnitPriceUSD==""||iUnitPriceUSD=="--"||iUnitPriceUSD==undefined){
					                	itemStr+=EachRMBStr;
					                }else{
					                	itemStr+='<td class="EachRMB" contenteditable="true" value="'+Math.round(iUnitPriceUSD)+'">'+Math.round(iUnitPriceUSD)+'</td>';
					                }
					                itemStr+='<td class="Qty" contenteditable="true" value="'+data[0].item[i].Qty+'">'+data[0].item[i].Qty+'</td>';
					                if(iExtendedUSD==""||iExtendedUSD=="--"||iExtendedUSD==undefined){
					                	itemStr+=ExtendedUSDStr;
					                }else{
					                	itemStr+='<td class="ExtendedUSD" contenteditable="true" value="'+Math.round(iExtendedUSD)+'">'+Math.round(iExtendedUSD)+'</td>';
					                }
					                itemStr+='<td  class="Oper" >'+
										'<span class="fa fa-long-arrow-up OperUp" ></span>'+
										'<span class="fa fa-trash OperDel Serial_del" ></span>'+
										'<span class="fa fa-long-arrow-down OperDown" ></span>'+
									'</td>'+
					               '</tr>';
			            	}
			            	$("#view4 .pdf_four_tab").append(itemStr);
		            	}
		            	else{
	    					$("#view4 .pdf_one_tr").remove();
	    					
	    				}
		            	
		            	var totalNum = 0;
		        		for(var i = 0; i< $("#view4 .pdf_one_tr").length ; i++){
		        			totalNum += Math.round($("#view4 .pdf_one_tr").eq(i).find(".ExtendedUSD").text());
		        		}
		            	
		            	var ShipmentInsurance=Math.round(parseFloat(thisList.eq(19).text()));
						$("#view4 .ShipmentInsuranceUSD").text("").text(ShipmentInsurance);

		            	//第二个集合
		            	if(data[0].price.length > 1){
		            		for(var i = 1 ; i < data[0].price.length; i++){
			            		$("#view4 .yemei").attr("value",data[0].price[i].ID);
			            		$("#view4 .Gifts").text(" ").text(data[0].price[i].Gifts);
			            		data[0].price[i].GiftsTotal==0.00?$("#view4 .GiftsTotal").text(" "):$("#view4 .GiftsTotal").text(" ").text(Math.round(data[0].price[i].GiftsTotal));
			            		$("#view4 .TotalUSD").text("").text(Math.round(data[0].price[i].SubTotal));
			            		$("#view4 .FinalTotalCIPUSD").text("").text(Math.round(data[0].price[i].FinalTotal));
			            	}
		            	}
		            	else{
		            		$("#view4 .Gifts").text(" ");
		            		$("#view4 .GiftsTotal").text(" ");
    						var TotalIncluding = Math.round(parseFloat(totalNum) + parseFloat(thisList.eq(19).text()));
    		        		if(TotalIncluding == "NaN"){
    		        			TotalIncluding = 0;
    		        		}
    		        		$("#view4 .TotalUSD").text("").text(totalNum);
    		        		$("#view4 .FinalTotalCIPUSD").text("").text(TotalIncluding);
		            	}   
		            },
		            error : function () {
		            	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
		            }
		        });
			
			 $('#view4').show(); 
			 $('#view1,#view2,#view3,#EgoMachineView,#EgoAccessoriesView,#costComparisonView').hide();
		}
		else if(currentId == "EgoMachine"){   //华为整机模板
			//console.log(ID)
			$(".EgoMachineTable .EgoMachineTr").remove();
			 $("#exportEXCEL").show();
			 $("#exportWord1,#exportPDF1,#submit_n1").hide();
			/*$(".MailBar_cover_color").css("height",$(window).height());*/
			 $.ajax({
		            type : 'get',
		            url : "QuoteSystemHuaEgo",
		            data : {
		            	QuoteID:ID,
		            	ExcelType : "Complete"
		            },
		            dataType : 'json',
		            success : function (data) {  
		            	console.log(data);
		            	var itemStr="";
		            	for(var n = 1 ; n < data.length ; n++ ){
		            		itemStr+="<tr class='EgoMachineTr'>"+
							"<td style='text-align:center;'>"+ data[n].Item+"</td>"+
							"<td >"+ data[n].ItemDescription+"</td>"+
							"<td style='text-align:center;' >"+ data[n].Qty+"</td>"+
							'<td  class="Oper" >'+
								'<span class="fa fa-long-arrow-up OperUp" ></span>'+
								'<span class="fa fa-trash OperDel Serial_del" ></span>'+
								'<span class="fa fa-long-arrow-down OperDown" ></span>'+
							'</td>'+
							"</tr>";
		            	}
		            	$(".EgoMachineTable").append(itemStr);
		            	var sc=$(window).scrollTop();
		         	   $('body,html').animate({scrollTop:0},200);
		            },
		            error : function () {
		            	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
		            }
		        });
			
			 
			$('#EgoMachineView').show(); 
			$('#view1,#view2,#view3,#view4,#EgoAccessoriesView,#costComparisonView').hide();
		}
		else if(currentId == "EgoAccessories"){
			$(".EgoAccessoriesTable .EgoAccessoriesTr").remove();
			 $("#exportEXCEL").show();
			 $("#submit_n1").css("top","200px");
			 $("#exportWord1,#exportPDF1").hide();
			 $.ajax({
		            type : 'get',
		            url : "QuoteSystemHuaEgo",
		            data : {
		            	QuoteID:ID,
		            	ExcelType : "Parts"
		            },
		            dataType : 'json',
		            success : function (data) {  
		            	console.log(data);
		            	var itemStr="";
		            	for(var n = 1 ; n < data.length ; n++ ){
		            		var UnitUSD,DiscountedUSD,TotalPrice,DiscountedPercent;
		            		(data[n].UnitUSD =="--" || data[n].UnitUSD =="" ) ? (UnitUSD = 0):( UnitUSD = Math.round(data[n].UnitUSD));
		            		(data[n].DiscountedUSD =="--" || data[n].DiscountedUSD =="" ) ? (DiscountedUSD = 0):( DiscountedUSD = Math.round(data[n].DiscountedUSD));
		            		(data[n].TotalPrice =="--" || data[n].TotalPrice =="" ) ? (TotalPrice = 0):( TotalPrice = data[n].TotalPrice);
		            		(data[n].DiscountedPercent =="--" || data[n].DiscountedPercent =="" ) ? (DiscountedPercent = 0):( DiscountedPercent = Math.round(data[n].DiscountedPercent));
		            		itemStr+='<tr class="EgoAccessoriesTr">'+
		            		'<td style="text-align:center" ID='+ data[n].ID+'  class="Parts_ID">'+ n+'</td>'+
							'<td style="" title='+data[n].Item+' >'+ data[n].Item+'</td>'+
							'<td style=""  title='+data[n].ItemDescription+'>'+ data[n].ItemDescription+'</td>'+
							'<td style="" title='+data[n].Model+'>'+ data[n].Model+'</td>'+
							'<td style=""  title='+data[n].CommodityName+'>'+ data[n].CommodityName+'</td>'+	
							'<td style="text-align:center" contenteditable="true" class="Parts_UnitUSD">'+UnitUSD+'</td>'+	
							'<td style="text-align:center;" contenteditable="true" class="Parts_DiscountedUSD">'+ DiscountedUSD+'</td>'+
							'<td style="text-align:center;" >'+ data[n].Qty+'</td>'+
							'<td style="text-align:center;" contenteditable="true" class="Parts_TotalPrice">'+ TotalPrice+'</td>'+
							'<td style="text-align:center;" contenteditable="true" class="Parts_DiscountedPercent">'+ DiscountedPercent+'</td>'+
							'<td style="" contenteditable="true" class="Parts_Remarks" >'+ data[n].Remarks+'</td>'+
							'<td  class="Oper" >'+
								'<span class="fa fa-long-arrow-up OperUp" ></span>'+
								'<span class="fa fa-trash OperDel Serial_del" ></span>'+
								'<span class="fa fa-long-arrow-down OperDown" ></span>'+
							'</td>'+
							'</tr>';
		            	}
		            	$(".EgoAccessoriesTable").append(itemStr);
//		            	var sc=$(window).scrollTop();
//		         	   $('body,html').animate({scrollTop:0},200);
		            },
		            error : function () {
		            	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
		            }
		        });
			/*$(".MailBar_cover_color").css("height",$(window).height());*/
			$('#EgoAccessoriesView').show(); 
			$('#view1,#view2,#view3,#view4,#EgoMachineView,#costComparisonView').hide();
		}
		// 成本对比表
		else if(currentId == "costComparison"){
			$(".costComparisonTable tbody").empty();
			 $("#exportEXCEL").show();
			 // $("#submit_n1").css("top","200px");
			 $("#exportWord1,#exportPDF1,#submit_n1").hide();
			 $.ajax({
			 	type : 'get',
			 	url : "QuoteCommodity",
			 	data : {
			 		ID:ID,
			 	},
			 	dataType : 'json',
			 	success: function(data){
			 		console.log(data);
			 		var strNew = '';
			 		if(data.length>0){
			 			var Len = data[0].item.length;
			 			var item = data[0].item;
			 			var costPriceTotal = 0;
			 			console.log(Len);
			 			for(var ii=1;ii<Len;ii++){
			 				var CostPrice = item[ii].CostPrice;
			 				var Qty = item[ii].Qty;
			 				var CostPriceNew = globalDataHandle(CostPrice,0);
			 				var QtyNew = globalDataHandle(Qty,0);
			 				strNew+='<tr class="costComparisonTr">'+
			 				'<td ID="'+item[ii].CommodityID+'">'+ii+'</td>'+
			 				'<td title="'+item[ii].Model+'">'+item[ii].Model+'</td>'+
			 				'<td title="'+item[ii].Description+'">'+item[ii].Description+'</td>'+
			 				'<td>'+QtyNew+'</td>'+
			 				'<td>'+(CostPriceNew*1).toFixed(2)+'</td>'+
			 				'<td>'+((QtyNew*1)*(CostPriceNew*1)).toFixed(2)+'</td>'+
			 				'</tr>';
			 				costPriceTotal+=(QtyNew*1)*(CostPriceNew*1);
			 			}
			 			var ShipmentCost = currentTr.find("td.i-ShipmentCost").text().trim();
			 			var ShipmentCostNew = globalDataHandle(ShipmentCost,0);
			 			var USDTotal = currentTr.find("td.i-USDTotal").text().trim();
			 			var USDTotalNew = globalDataHandle(USDTotal,0);
			 			var Profit = USDTotalNew - costPriceTotal;
			 			var ProfitRate = USDTotalNew/costPriceTotal;
			 			strNew+='<tr class="m-ShipmentCost m-r"><td colspan="5">运费USD:</td><td>$&nbsp;'+fmoney(ShipmentCostNew*1)+'</td></tr>'+
			 			'<tr class="m-CostPrice m-r"><td colspan="5">当前成本USD:</td><td>$&nbsp;'+fmoney(costPriceTotal)+'</td></tr>'+
			 			'<tr class="m-USDTotal m-r"><td colspan="5">目前报价USD:</td><td>$&nbsp;'+fmoney(USDTotalNew)+'</td></tr>'+
			 			'<tr class="m-Profit m-r"><td colspan="5">总利润USD:</td><td>$&nbsp;'+fmoney(Profit)+'</td></tr>'+
			 			'<tr class="m-Profit-rate m-r"><td colspan="5">利润率:</td><td>'+(ProfitRate*1).toFixed(2)+'</td></tr>';
			 		}
			 		$(".costComparisonTable tbody").empty();
			 		$(".costComparisonTable tbody").append(strNew);
			 	},
			 	error: function(){
			 		$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
			 	}
			 });

			$('#costComparisonView').show(); 
			$('#view1,#view2,#view3,#view4,#EgoMachineView,#EgoAccessoriesView').hide();
		}
	})
	
	//整机 配件 模板  提交
		 $(document).on("click","#submit_n1",function(){
			var submitView = $(".allHidePdf .isHover").attr("ID");
			console.log(submitView)
			if(submitView == "RMBMachine"){
				var Type="CompleteRMB";
			    var SubTotal=parseFloat($('#view1 .TotalRMB').text().replace(/,/g,''));
			    var Gifts=$('#view1 .Gifts').text();
			    var GiftsTotal=$('#view1 .GiftsTotal').text();
			    var FinalTotal =parseFloat( $("#view1 .TotalIncludingVATRMB").text().replace(/,/g,''));
			    //当前行大table中的ID
			    var QuoteID = $("#view1 .yejiao1").attr("value");
			    var ID;
			    if($('#view1 .yemei').attr("value")){
			    	ID=$('#view1 .yemei').attr("value");
		    	}else{
		    		ID=0;
		    	}
		    	var ModelJson = [];
		    	$("#view1 .pdf_one_tr").each(function(){
		    		var item = {};
		    		item.ID = $(this).find(".Item").attr("value");
		    		item.Qty = $(this).find(".Qty").text();
					ModelJson.push(item);
		    	});
		    	var ModelJsonStr = JSON.stringify(ModelJson);
			    
		    	$.ajax({
		            type : 'POST',
		            url : 'QuoteSystemModelOperate',
		            data : {
		            	ID:ID,
		            	Type : Type,
		            	SubTotal : SubTotal,
		            	Gifts : Gifts,
		            	GiftsTotal : GiftsTotal,
		            	FinalTotal : FinalTotal,
		            	QuoteID : QuoteID,
		            	ModelJson: ModelJsonStr
		            },
		            dataType : 'text',
		            success : function (data) { 
		            	if(data=="true"){
		            		$.MsgBox.Alert('提示','提交成功');
				            $('.MailBar_cover_color').hide();
				            $('.allHidePdf').hide();
				            scorllFlag =  false; 
		            	}else{
		            		$.MsgBox_Unload.Alert("提示", "提交失败，稍后重试！");
		            	}
		            	
		            },
		            error : function () {
		            	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
		            }
		        }); 
			}
			else if(submitView == "USDMachine"){
				var Type="CompleteUSD";
			    var SubTotal=parseFloat($('#view2 .TotalUSD').text().replace(/,/g,''));
			    var Gifts=$('#view2 .Gifts').text();
			    var GiftsTotal=$('#view2 .GiftsTotal').text();
			    var FinalTotal =parseFloat( $("#view2 .FinalTotalCIPUSD").text().replace(/,/g,''));
			    var QuoteID = $("#view2 .yejiao1").attr("value");
			    
			    if($('#view2 .yemei').attr("value")){
			    	var ID=$('#view2 .yemei').attr("value");
		    	}else{
		    		var ID=0;
		    	}
    	    	var ModelJson = [];
    	    	$("#view2 .pdf_one_tr").each(function(){
    	    		var item = {};
    	    		item.ID = $(this).find(".Item").attr("value");
    	    		item.Qty = $(this).find(".Qty").text();
    				ModelJson.push(item);
    	    	});
    	    	var ModelJsonStr = JSON.stringify(ModelJson);
		    	$.ajax({
		            type : 'POST',
		            url : 'QuoteSystemModelOperate',
		            data : {
		            	ID:ID,
		            	Type : Type,
		            	SubTotal : SubTotal,
		            	Gifts : Gifts,
		            	GiftsTotal : GiftsTotal,
		            	FinalTotal : FinalTotal,
		            	QuoteID : QuoteID,
		            	ModelJson: ModelJsonStr
		            },
		            dataType : 'text',
		            success : function (data) {       	        	             	
    	            	if(data=="true"){
    	            		$.MsgBox.Alert('提示','提交成功');
    			            $('.MailBar_cover_color').hide();
    			            $('.allHidePdf').hide();
    			            scorllFlag =  false; 
    	            	}else{
    	            		$.MsgBox_Unload.Alert("提示", "提交失败，稍后重试！");
    	            	}
		            },
		            error : function () {
		            	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
		            }
		        });
			}
			else if(submitView == "RMBAccessories"){
				var Type="PartsRMB";
				var SubTotal= parseFloat($('#view3 .TotalRMB').text().replace(/,/g,''));
				var FinalTotal =parseFloat( $("#view3 .TotalIncludingVATRMB").text().replace(/,/g,''));
			    var Gifts=$('#view3 .Gifts').text();
			    var GiftsTotal=$('#view3 .GiftsTotal').text();
			    var QuoteID = $("#view3 .yejiao1").attr("value");
			    
			    if($('#view3 .yemei').attr("value")){
			    	var ID=$('#view3 .yemei').attr("value");
		    	}else{
		    		var ID=0;
		    	}
	        	var ModelJson = [];
	        	$("#view3 .pdf_one_tr").each(function(){
	        		var item = {};
	        		item.ID = $(this).find(".Item").attr("value");
	        		item.Qty = $(this).find(".Qty").text();
	        		item.NewUnitPrice = $(this).find(".EachRMB").text();
	        		item.NewExtended = $(this).find(".ExtendedRMB").text();
	    			ModelJson.push(item);
	        	});
	        	var ModelJsonStr = JSON.stringify(ModelJson);
		    	$.ajax({
		            type : 'POST',
		            url : 'QuoteSystemModelOperate',
		            data : {
		            	ID:ID,
		            	Type : Type,
		            	SubTotal : SubTotal,
		            	Gifts : Gifts,
		            	GiftsTotal : GiftsTotal,
		            	FinalTotal : FinalTotal,
		            	QuoteID : QuoteID,
		            	ModelJson: ModelJsonStr
		            },
		            dataType : 'text',
		            success : function (data) {       	        	             	
    	            	if(data=="true"){
    	            		$.MsgBox.Alert('提示','提交成功');
    			            $('.MailBar_cover_color').hide();
    			            $('.allHidePdf').hide();
    			            scorllFlag =  false; 
    	            	}else{
    	            		$.MsgBox_Unload.Alert("提示", "提交失败，稍后重试！");
    	            	} 
		            },
		            error : function () {
		            	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
		            }
		        });
			}
			else if(submitView=="USDAccessories"){
				var Type="PartsUSD";
			    var SubTotal= parseFloat($('#view4 .TotalUSD').text().replace(/,/g,''));
				
				var FinalTotal =parseFloat( $("#view4 .FinalTotalCIPUSD").text().replace(/,/g,''));
			    
			    var Gifts=$('#view4 .Gifts').text();
			    var GiftsTotal=$('#view4 .GiftsTotal').text();
			    var QuoteID = $("#view4 .yejiao1").attr("value");
			    
			    if($('#view4 .yemei').attr("value")){
			    	var ID=$('#view4 .yemei').attr("value");
		    	}else{
		    		var ID=0;
		    	}
    	    	var ModelJson = [];
    	    	$("#view4 .pdf_one_tr").each(function(){
    	    		var item = {};
    	    		item.ID = $(this).find(".Item").attr("value");
    	    		item.Qty = $(this).find(".Qty").text();
    	    		item.NewUnitPrice = $(this).find(".EachRMB").text();
    	    		item.NewExtended = $(this).find(".ExtendedUSD").text();
    				ModelJson.push(item);
    	    	});
    	    	var ModelJsonStr = JSON.stringify(ModelJson);
		    	$.ajax({
		            type : 'POST',
		            url : 'QuoteSystemModelOperate',
		            data : {
		            	ID:ID,
		            	Type : Type,
		            	SubTotal : SubTotal,
		            	Gifts : Gifts,
		            	GiftsTotal : GiftsTotal,
		            	FinalTotal : FinalTotal,
		            	QuoteID : QuoteID,
		            	ModelJson: ModelJsonStr
		            },
		            dataType : 'text',
		            success : function (data) {       	        	             	
    	            	if(data=="true"){
    	            		$.MsgBox.Alert('提示','提交成功');
    			            $('.MailBar_cover_color').hide();
    			            $('.allHidePdf').hide();
    			            scorllFlag =  false; 
    	            	}else{
    	            		$.MsgBox_Unload.Alert("提示", "提交失败，稍后重试！");
    	            	}
		            },
		            error : function () {
		            	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
		            }
		        });
			}
			else if(submitView=="EgoAccessories"){
				var ID = [];var UnitUSD = [];var DiscountedUSD = [];var TotalPrice = [];var DiscountedPercent = [];var Remarks = [];
				for(var n = 0 ; n < $("#EgoAccessoriesView .EgoAccessoriesTr").length ; n++){
					ID.push($("#EgoAccessoriesView .EgoAccessoriesTr").eq(n).find(".Parts_ID").attr("ID"));
					UnitUSD.push($("#EgoAccessoriesView .EgoAccessoriesTr").eq(n).find(".Parts_UnitUSD").text());
					DiscountedUSD.push($("#EgoAccessoriesView .EgoAccessoriesTr").eq(n).find(".Parts_DiscountedUSD").text());
					TotalPrice.push($("#EgoAccessoriesView .EgoAccessoriesTr").eq(n).find(".Parts_TotalPrice").text());
					DiscountedPercent.push($("#EgoAccessoriesView .EgoAccessoriesTr").eq(n).find(".Parts_DiscountedPercent").text());
					Remarks.push($("#EgoAccessoriesView .EgoAccessoriesTr").eq(n).find(".Parts_Remarks").text());
				}
				
				$.ajax({
		            type : 'get',
		            url : 'QuoteSystemHuaEgoOperate',
		            data : {
		            	ID:ID,
		            	UnitUSD : UnitUSD,
		            	DiscountedUSD : DiscountedUSD,
		            	TotalPrice : TotalPrice,
		            	DiscountedPercent : DiscountedPercent,
		            	Remarks : Remarks,
		            },
		            dataType : 'json',
		            success : function (data) {       	        	             	
		            	$.MsgBox.Alert('提示','提交成功');
			            $('.MailBar_cover_color').hide();
			            $('.allHidePdf').hide();
			            scorllFlag =  false; 
		            },
		            error : function () {
		            	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
		            }
		        });
			}
	});
	//整机 配件 模板  导出
	 $(document).on("click","#exportPDF1,#exportWord1",function(){
			var exportView = $(".allHidePdf .isHover").attr("ID");
			console.log(exportView)
			if($(this).attr("id") =="exportWord1" ){
				var Type = "word";
			}		
			else{
				var Type = "PDF";
			}
			if(exportView == "RMBMachine"){
				 var Versions=$('#view1 .Versions').text();
				 var Datesent=$('#view1 .DateSent').text();
				 var Number=$('#view1 .Number').text();
				 var CustomerCompany=$('#view1 .CustomerCompany').text();
				 var CustomerName=$('#view1 .CustomerName').text();
				 var CustomerTel=$('#view1 .CustomerTel').text();
				 var CustomerMail=$('#view1 .CustomerMail').text();
				 var StaffName=$('#view1 .StaffName').text();
				 var StaffTel=$('#view1 .StaffTel').text();
				 var StaffMail=$('#view1 .StaffMail').text();
				 var Valid=$('#view1 .Valid').text();
				 var Payment=$('#view1 .Payment').text();
				 var LeadTime=$('#view1 .LeadTime').text();
				 var SubTotal=$('#view1 .TotalRMB').text();
				 var Gift =$('#view1 .Gifts').text();
				 var GiftTotal=$('#view1 .GiftsTotal').text();
				 var ShipmentCost=$('#view1 .ShipmentInsuranceRMB').text(); 
				 var FinalTotal=$('#view1 .TotalIncludingVATRMB').text();
				 var Parts =[];var Description =[];var Qty =[];
				 for(var i = 0;i < $('#view1 .pdf_one_tr').length;i++){
					 Parts.push($('#view1 .pdf_one_tr').eq(i).find(".PartNumber").text());
					 Description.push($('#view1 .pdf_one_tr').eq(i).find(".Description").text());
					 Qty.push($('#view1 .pdf_one_tr').eq(i).find(".Qty").text());
				}
				var Fax = td_CustomerFax;
				var ajaxData = {
						Type : Type,
						Versions:Versions,
		            	Datesent : Datesent,
		            	Number : Number,
		            	CustomerCompany : CustomerCompany,
		            	CustomerName : CustomerName,
		            	CustomerTel : CustomerTel,
		            	CustomerMail : CustomerMail,
		            	StaffName:StaffName,
		            	StaffTel : StaffTel,
		            	StaffMail : StaffMail,
		            	LeadTime : LeadTime,
		            	Payment : Payment,
		            	Valid : Valid,
		            	SubTotal : SubTotal,
		            	Gift:Gift,
		            	GiftTotal : GiftTotal,
		            	ShipmentCost : ShipmentCost,
		            	FinalTotal : FinalTotal,
		            	Parts : Parts,
		            	Description : Description,
		            	Qty : Qty,
		            	Fax: Fax
				}; 
				console.log(ajaxData);
				var ajaxURL  =  'DownloadWordCompleteRMB';
			}
			else if(exportView == "USDMachine"){
				
				 var Versions=$('#view2 .Versions').text();
				 var Datesent=$('#view2 .DateSent').text();
				 var Number=$('#view2 .Number').text();
				 var CustomerCompany=$('#view2 .CustomerCompany').text();
				 var CustomerName=$('#view2 .CustomerName').text();
				 var CustomerTel=$('#view2 .CustomerTel').text();
				 var CustomerMail=$('#view2 .CustomerMail').text();
				 var StaffName=$('#view2 .StaffName').text();
				 var StaffTel=$('#view2 .StaffTel').text();
				 var StaffMail=$('#view2 .StaffMail').text();
				 var Valid=$('#view2 .Valid').text();
				 var Payment=$('#view2 .Payment').text();
				 var LeadTime=$('#view2 .LeadTime').text();
				 var SubTotal=$('#view2 .TotalUSD').text();
				 var Gift =$('#view2 .Gifts').text();
				 var GiftTotal=$('#view2 .GiftsTotal').text();
				 var ShipmentCost=$('#view2 .ShipmentInsuranceUSD').text(); 
				 var FinalTotal=$('#view2 .FinalTotalCIPUSD').text();
				
				 var Parts =[];var Description =[];var Qty =[];
				 for(var i = 0;i < $('#view2 .pdf_one_tr').length;i++){
					 Parts.push($('#view2 .pdf_one_tr').eq(i).find(".PartNumber").text());
					 Description.push($('#view2 .pdf_one_tr').eq(i).find(".Description").text());
					 Qty.push($('#view2 .pdf_one_tr').eq(i).find(".Qty").text());
				 }
				 var Fax = td_CustomerFax;
				var ajaxData = {
						Type : Type,
						Versions:Versions,
		            	Datesent : Datesent,
		            	Number : Number,
		            	CustomerCompany : CustomerCompany,
		            	CustomerName : CustomerName,
		            	CustomerTel : CustomerTel,
		            	CustomerMail : CustomerMail,
		            	StaffName:StaffName,
		            	StaffTel : StaffTel,
		            	StaffMail : StaffMail,
		            	LeadTime : LeadTime,
		            	Payment : Payment,
		            	Valid : Valid,
		            	SubTotal : SubTotal,
		            	Gift:Gift,
		            	GiftTotal : GiftTotal,
		            	ShipmentCost : ShipmentCost,
		            	FinalTotal : FinalTotal,
		            	Parts : Parts,
		            	Description : Description,
		            	Qty : Qty,
		            	Fax: Fax
				}; 
				console.log(ajaxData);
				var ajaxURL  =  'DownloadWordCompleteUSD';
			}
			else if(exportView == "RMBAccessories"){
				 var Versions=$('#view3 .Versions').text();
				 var Datesent=$('#view3 .DateSent').text();
				 var Number=$('#view3 .Number').text();
				 var CustomerCompany=$('#view3 .CustomerCompany').text();
				 var CustomerName=$('#view3 .CustomerName').text();
				 var CustomerTel=$('#view3 .CustomerTel').text();
				 var CustomerMail=$('#view3 .CustomerMail').text();
				 var StaffName=$('#view3 .StaffName').text();
				 var StaffTel=$('#view3 .StaffTel').text();
				 var StaffMail=$('#view3 .StaffMail').text();
				 var Valid=$('#view3 .Valid').text();
				 var Payment=$('#view3 .Payment').text();
				 var LeadTime=$('#view3 .LeadTime').text();
				 var SubTotal=$('#view3 .TotalRMB').text();
				 var Gift =$('#view3 .Gifts').text();
				 var GiftTotal=$('#view3 .GiftsTotal').text();
				 var ShipmentCost=$('#view3 .ShipmentInsuranceRMB').text(); 
				 var FinalTotal=$('#view3 .TotalIncludingVATRMB').text();
				 var Parts =[];var Description =[];var Qty =[];var Each =[];var Extended =[];
				 for(var i = 0;i < $('#view3 .pdf_one_tr').length;i++){
					 Parts.push($('#view3 .pdf_one_tr').eq(i).find(".PartNumber").text());
					 Description.push($('#view3 .pdf_one_tr').eq(i).find(".Description").text());
					 Qty.push($('#view3 .pdf_one_tr').eq(i).find(".Qty").text());
					 Each.push($('#view3 .pdf_one_tr').eq(i).find(".EachRMB").text());
					 Extended.push($('#view3 .pdf_one_tr').eq(i).find(".ExtendedRMB").text());
				 }
				 var Fax = td_CustomerFax;
				var ajaxData = {
						Type : Type,
						Versions:Versions,
		            	Datesent : Datesent,
		            	Number : Number,
		            	CustomerCompany : CustomerCompany,
		            	CustomerName : CustomerName,
		            	CustomerTel : CustomerTel,
		            	CustomerMail : CustomerMail,
		            	StaffName:StaffName,
		            	StaffTel : StaffTel,
		            	StaffMail : StaffMail,
		            	LeadTime : LeadTime,
		            	Payment : Payment,
		            	Valid : Valid,
		            	SubTotal : SubTotal,
		            	Gift:Gift,
		            	GiftTotal : GiftTotal,
		            	ShipmentCost : ShipmentCost,
		            	FinalTotal : FinalTotal,
		            	Parts : Parts,
		            	Description : Description,
		            	Qty : Qty,
		            	Each : Each,
		            	Extended : Extended,
		            	Fax: Fax
		            };
				console.log(ajaxData);
				var ajaxURL  =  'DownloadWordPartsRMB';
			}
			else{
				 var Versions=$('#view4 .Versions').text();
				 var Datesent=$('#view4 .DateSent').text();
				 var Number=$('#view4 .Number').text();
				 var CustomerCompany=$('#view4 .CustomerCompany').text();
				 var CustomerName=$('#view4 .CustomerName').text();
				 var CustomerTel=$('#view4 .CustomerTel').text();
				 var CustomerMail=$('#view4 .CustomerMail').text();
				 var StaffName=$('#view4 .StaffName').text();
				 var StaffTel=$('#view4 .StaffTel').text();
				 var StaffMail=$('#view4 .StaffMail').text();
				 var Valid=$('#view4 .Valid').text();
				 var Payment=$('#view4 .Payment').text();
				 var LeadTime=$('#view4 .LeadTime').text();
				 var SubTotal=$('#view4 .TotalUSD').text();
				 var Gift =$('#view4 .Gifts').text();
				 var GiftTotal=$('#view4 .GiftsTotal').text();
				 var ShipmentCost=$('#view4 .ShipmentInsuranceUSD').text(); 
				 var FinalTotal=$('#view4 .FinalTotalCIPUSD').text();
				 var Parts =[];var Description =[];var Qty =[];var Each =[];var Extended =[];
				 for(var i = 0;i < $('#view4 .pdf_one_tr').length;i++){
					 Parts.push($('#view4 .pdf_one_tr').eq(i).find(".PartNumber").text());
					 Description.push($('#view4 .pdf_one_tr').eq(i).find(".Description").text());
					 Qty.push($('#view4 .pdf_one_tr').eq(i).find(".Qty").text());
					 Each.push($('#view4 .pdf_one_tr').eq(i).find(".EachRMB").text());
					 Extended.push($('#view4 .pdf_one_tr').eq(i).find(".ExtendedUSD").text());
				 }
				 var Fax = td_CustomerFax;
				var ajaxData = {
						Type : Type,
						Versions:Versions,
		            	Datesent : Datesent,
		            	Number : Number,
		            	CustomerCompany : CustomerCompany,
		            	CustomerName : CustomerName,
		            	CustomerTel : CustomerTel,
		            	CustomerMail : CustomerMail,
		            	StaffName:StaffName,
		            	StaffTel : StaffTel,
		            	StaffMail : StaffMail,
		            	LeadTime : LeadTime,
		            	Payment : Payment,
		            	Valid : Valid,
		            	SubTotal : SubTotal,
		            	Gift:Gift,
		            	GiftTotal : GiftTotal,
		            	ShipmentCost : ShipmentCost,
		            	FinalTotal : FinalTotal,
		            	Parts : Parts,
		            	Description : Description,
		            	Qty : Qty,
		            	Each : Each,
		            	Extended : Extended,
		            	Fax: Fax
				}; 
				console.log(ajaxData);
				var ajaxURL  =  'DownloadWordPartsUSD';
			}
			$.ajax({
	            type : 'get',
	            url  : ajaxURL,
	            data : ajaxData,
	            success : function (data) {       	        	             	
	            	window.location.href = data;
	            },
	            error : function () {
	            	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	            }
	        });
	 });
	
	//*****************************************整机 配件 模板  结束********************************************************
	
	//  初次加载  模板 显示
	$('.contract-show5').click(function () {
		/*$("#view5 .ShipmentTime").text();*/
		    $(".hidePdf5").attr("value",$(this).parent().parent().index());
		    var ID = $(this).parent().parent().find("td").eq(0).attr("value");
		    var thisList=$(this).parent().parent();
		    
		    $('#view5 .yemei').attr("value",ID);
			var Type="USDContract";
				$("#view5 .pdf_one_tr").remove();
				$.ajax({
			            type : 'get',
			            url : "GetContractModel",
			            data : {
			            	Type : Type,
			            	ID : ID,
			            },
			            dataType : 'json',
			            success : function (data) {  
			            	console.log(data);
			            	/*var CommodityName = data[0].USDCommoditys[1].CommodityName;
			            	if(CommodityName){
			            		CommodityName = CommodityName;
			            	}else{
			            		CommodityName = '';
			            	}
			            	console.log(CommodityName);*/
			            	var PayTime = data[0].USDContract[1].PayTime;
			            	console.log(PayTime);
			            	var USDlen=data[0].USDContract.length;
			            	console.log(USDlen);
			            	if(USDlen == 1){
			            		  $("#view5 .ContractNO").text(" ");
			            	      $("#view5 .Date").text(" ");
			            	      $("#view5 .Version").text(" ");
			            	      $("#view5 .CustomerCompany").text(" ");
			            	      $("#view5 .CustomerTel").text(" ");
			            	      $("#view5 .CustomerFax").text(" ");
			            	      $("#view5 .CustomerContact").text(" ");
			            	      $("#view5 .TotalPrice").text(" ");
			            	      $("#view5 .TotalPrice").text(" ");
			            	      $("#view5 .Payment").text(" ");
			            	      $("#view5 .Payment2").text("");
			            	      $("#view5 .PayTime").text("");
			            	      $("#view5 .Shipment").text(" ");
			            	      $("#view5 .Destination").text(" ");
			            	      $("#view5 .ShipmentTime").text(" ");
			            	      $("#view5 .ShippingMark").text(" ");
			            	      $("#view5 .AirPort").text(" ");
			            	      $("#view5 .Manufacturer").text(" ");
			            	      $("#view5 .CustomerAdd").text(" ");
			            	      $("#view5 .yejiao").attr("value",'');
			            	}else{
			            	      $("#view5 .ContractNO").text(" ").text(data[0].USDContract[1].ContractNO);
			            	      $("#view5 .Date").text(" ").text(data[0].USDContract[1].Date);
			            	      $("#view5 .Version").text(" ").text(data[0].USDContract[1].Version);
			            	      $("#view5 .CustomerCompany").text(" ").text(data[0].USDContract[1].CustomerCompany);
			            	      $("#view5 .CustomerTel").text(" ").text(data[0].USDContract[1].CustomerTel);
			            	      $("#view5 .CustomerFax").text(" ").text(data[0].USDContract[1].CustomerFax);
			            	      $("#view5 .CustomerContact").text(" ").text(data[0].USDContract[1].CustomerContact);
			            	      $("#view5 .TotalPrice").text(" ").text(data[0].USDContract[1].TotalPrice);
			            	      $("#view5 .TotalPrice").text(" ").text(data[0].USDContract[1].TotalPrice);
			            	      $("#view5 .Payment").text(" ").text(data[0].USDContract[1].Payment);
			            	      var Payment2 = data[0].USDContract[1].Payment;
			            	      console.log(Payment2);
			            	      var PayTime = data[0].USDContract[1].PayTime;
			            	      console.log(PayTime);
			            	      $("#view5 .Payment2").text("").text(Payment2);
			            	      console.log($(".PayTime"));
			            	      
			            	      $("#view5 .PayTime").text("").text(PayTime);
			            	      $("#view5 .Shipment").text(" ").text(data[0].USDContract[1].Shipment);
			            	      $("#view5 .Destination").text(" ").text(data[0].USDContract[1].Destination);
			            	      $("#view5 .ShipmentTime").text(" ").text(data[0].USDContract[1].ShipmentTime);
			            	      $("#view5 .ShippingMark").text(" ").text(data[0].USDContract[1].ShippingMark);
			            	      $("#view5 .AirPort").text(" ").text(data[0].USDContract[1].AirPort);
			            	      $("#view5 .Manufacturer").text(" ").text(data[0].USDContract[1].Manufacturer);
			            	      $("#view5 .CustomerAdd").text(" ").text(data[0].USDContract[1].CustomerAdd);
			            	      $("#view5 .yejiao").attr("value",data[0].USDContract[1].ID);

			            	}
			            	var itemStr ="";
			            	for(var i = 1 ; i <data[0].USDCommoditys.length; i++ ){
			            		 itemStr += 
			            			'<tr class="pdf_one_tr">'+
						                '<td class="id" value="'+data[0].USDCommoditys[i].ID+'">'+"1-"+i+'</td>'+
						                '<td class="Model" contenteditable="true">'+data[0].USDCommoditys[i].Remark+'</td>'+
						                '<td class="Remark" contenteditable="true">'+data[0].USDCommoditys[i].CommodityModel+'</td>'+
						               ' <td  class="Unit" contenteditable="true">'+data[0].USDCommoditys[i].Unit+'</td>'+
						               ' <td class="Quantity" contenteditable="true">'+data[0].USDCommoditys[i].Quantity+'</td>'+
						               ' <td class="UnitPrice" contenteditable="true">'+fmoney(data[0].USDCommoditys[i].UnitPrice)+'</td>'+
						               ' <td  class="SumPrice" contenteditable="true">'+fmoney(parseFloat(data[0].USDCommoditys[i].UnitPrice*data[0].USDCommoditys[i].Quantity).toFixed(2))+'</td>'+
						               '<td><input type="button" class="add_del2" style="width:90%;height:90%;" value="删除"></td>'+
						            '</tr>';
			            		$("#view5 .QuoteID").text("").html(data[0].USDCommoditys[i].QuoteID);
			            	}
			            	$("#view5 .pdf_five_tab").after(itemStr);
			            	
			            	//计算合同美元部分的总价
			            	var totalNum = 0;
			        		for(var i = 0; i< $("#view5 .pdf_one_tr").length ; i++){
			        			totalNum += rmoney(fmoney($("#view5 .pdf_one_tr").eq(i).find(".SumPrice").text()));
			        		}
			        		 $("#view5 .TotalPrice").text("").text(fmoney(totalNum));
			        		 //$("#view5 .TotalPriceBig").text(" ").text(changeMoneyToChinese(totalNum.toFixed(2)));
			        		 $("#view5 .TotalPriceBig").text(" ").text(changeMoneyToChinese($(".pdf_five_tab1 .TotalPrice").text().replace(/,/g,'')));
			            },
			            error : function () {
			                $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
			            }
			        });
			
				scorllFlag = !scorllFlag;
				 $('.hidePdf5').show(); 
		         $('#view5').show();
		         $('#view6').hide(); 
		         $("#USDContract").addClass("isHover").siblings().removeClass("isHover");
		         // $(".MailBar_cover_color").css("height",$("#view5").height()+130);
				 $(".MailBar_cover_color").show();
	 })	
	 
	$(".Sidebar_Contract div").click(function(){
		$(this).addClass("isHover").siblings().removeClass("isHover");
		console.log($(this).attr("id"));
		var currentId = $(this).attr("id");
		var currentTr = $("#table1").find("tr").eq($(".hidePdf5").attr("value"));
		var thisList =currentTr.children(); 
		var ID = currentTr.find("td").eq(0).attr("value");
	    console.log(ID);
	    if(currentId =="USDContract"){
	    	// $(".MailBar_cover_color").css("height",$("#view5").height()+130);
	    	$('#view5 .yemei').attr("value",ID);
			var Type="USDContract";
			$("#view5 .pdf_one_tr").remove();
			$.ajax({
	            type : 'get',
	            url : "GetContractModel",
	            data : {
	            	Type : Type,
	            	ID : ID,
	            },
	            dataType : 'json',
	            success : function (data) {  
	            	console.log(data);
	            	var USDlen=data[0].USDContract.length;
	            	if(USDlen == 1){
	            		  $("#view5 .ContractNO").text(" ");
	            	      $("#view5 .Date").text(" ");
	            	      $("#view5 .Version").text(" ");
	            	      $("#view5 .CustomerCompany").text(" ");
	            	      $("#view5 .CustomerTel").text(" ");
	            	      $("#view5 .CustomerFax").text(" ");
	            	      $("#view5 .CustomerContact").text(" ");
	            	      $("#view5 .TotalPrice").text(" ");
	            	      $("#view5 .TotalPrice").text(" ");
	            	      $("#view5 .Payment").text(" ");
	            	      $("#view5 .Payment2").text("");
	            	      $("#view5 .PayTime").text("");
	            	      $("#view5 .Shipment").text(" ");
	            	      $("#view5 .Destination").text(" ");
	            	      $("#view5 .ShipmentTime").text(" ");
	            	      $("#view5 .ShippingMark").text(" ");
	            	      $("#view5 .AirPort").text(" ");
	            	      $("#view5 .Manufacturer").text(" ");
	            	      $("#view5 .CustomerAdd").text(" ");
	            	      $("#view5 .yejiao").attr("value",'');
	            	}else{
	            	      $("#view5 .ContractNO").text(" ").text(data[0].USDContract[1].ContractNO);
	            	      $("#view5 .Date").text(" ").text(data[0].USDContract[1].Date);
	            	      $("#view5 .Version").text(" ").text(data[0].USDContract[1].Version);
	            	      $("#view5 .CustomerCompany").text(" ").text(data[0].USDContract[1].CustomerCompany);
	            	      $("#view5 .CustomerTel").text(" ").text(data[0].USDContract[1].CustomerTel);
	            	      $("#view5 .CustomerFax").text(" ").text(data[0].USDContract[1].CustomerFax);
	            	      $("#view5 .CustomerContact").text(" ").text(data[0].USDContract[1].CustomerContact);
	            	      $("#view5 .TotalPrice").text(" ").text(data[0].USDContract[1].TotalPrice);
	            	      $("#view5 .TotalPrice").text(" ").text(data[0].USDContract[1].TotalPrice);
	            	      $("#view5 .Payment").text(" ").text(data[0].USDContract[1].Payment);
	            	      var Payment2 = data[0].USDContract[1].Payment;
	            	      console.log(Payment2);
	            	      var PayTime = data[0].USDContract[1].PayTime;
	            	      console.log(PayTime);
	            	      $("#view5 .Payment2").text("").text(Payment2);
	            	      $("#view5 .PayTime").text("").text(data[0].USDContract[1].PayTime);
	            	      $("#view5 .Shipment").text(" ").text(data[0].USDContract[1].Shipment);
	            	      $("#view5 .Destination").text(" ").text(data[0].USDContract[1].Destination);
	            	      $("#view5 .ShipmentTime").text(" ").text(data[0].USDContract[1].ShipmentTime);
	            	      $("#view5 .ShippingMark").text(" ").text(data[0].USDContract[1].ShippingMark);
	            	      $("#view5 .AirPort").text(" ").text(data[0].USDContract[1].AirPort);
	            	      $("#view5 .Manufacturer").text(" ").text(data[0].USDContract[1].Manufacturer);
	            	      $("#view5 .CustomerAdd").text(" ").text(data[0].USDContract[1].CustomerAdd);
	            	      $("#view5 .yejiao").attr("value",data[0].USDContract[1].ID);

	            	}
	            	var itemStr ="";
	            	for(var i = 1 ; i <data[0].USDCommoditys.length; i++ ){
	            		 itemStr += 
	            			'<tr class="pdf_one_tr">'+
				                '<td class="id" value="'+data[0].USDCommoditys[i].ID+'">'+"1-"+i+'</td>'+
				                '<td class="Model" contenteditable="true">'+data[0].USDCommoditys[i].CommodityModel+'</td>'+
				                '<td class="Remark" contenteditable="true">'+data[0].USDCommoditys[i].Remark+'</td>'+
				               ' <td  class="Unit" contenteditable="true">'+data[0].USDCommoditys[i].Unit+'</td>'+
				               ' <td class="Quantity" contenteditable="true">'+data[0].USDCommoditys[i].Quantity+'</td>'+
				               ' <td class="UnitPrice" contenteditable="true">'+fmoney(data[0].USDCommoditys[i].UnitPrice)+'</td>'+
				               ' <td  class="SumPrice" contenteditable="true">'+fmoney(parseFloat(data[0].USDCommoditys[i].UnitPrice*data[0].USDCommoditys[i].Quantity).toFixed(2))+'</td>'+
				               '<td><input type="button" class="add_del2" style="width:90%;height:90%;" value="删除"></td>'+
				            '</tr>';
	            		$("#view5 .QuoteID").text("").html(data[0].USDCommoditys[i].QuoteID);
	            	}
	            	$("#view5 .pdf_five_tab").after(itemStr);
	            	
	            	//计算合同美元部分的总价
	            	var totalNum = 0;
	        		for(var i = 0; i< $("#view5 .pdf_one_tr").length ; i++){
	        			totalNum += rmoney(fmoney($("#view5 .pdf_one_tr").eq(i).find(".SumPrice").text()));
	        		}
	        		 $("#view5 .TotalPrice").text("").text(fmoney(totalNum));
	        		 //$("#view5 .TotalPriceBig").text(" ").text(changeMoneyToChinese(totalNum.toFixed(2)));
	        		 $("#view5 .TotalPriceBig").text(" ").text(changeMoneyToChinese($(".pdf_five_tab1 .TotalPrice").text().replace(/,/g,'')));
	            },
	            error : function () {
	                $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	            }
	        });
			 $('#view5').show();
	         $('#view6').hide(); 
	    }
	    else{
	    	// $(".MailBar_cover_color").css("height",$("#view6").height()+130);
	    	$("#view6 .yemei").attr("value",ID);
			var Type="RMBContract";
		$("#view6 .pdf_one_tr").remove();
		$.ajax({
	            type : 'get',
	            url : "GetContractModel",
	            data : {
	            	Type : Type,
	            	ID : ID,
	            },
	            dataType : 'json',
	            success : function (data) {  
	            	console.log("data");
	            	console.log(data);
	            	var RMBlen=data[0].RMBContract.length
	            	for(var i = 1 ; i <RMBlen; i++ ){
	            	      $("#view6 .ContractNO").text("").text(data[0].RMBContract[i].ContractNO);
	            	      var SignDate=data[0].RMBContract[i].SignDate;
	            	      var SignDateYear=SignDate.split("-")[0];
	            	      var SignDateMonth=SignDate.split("-")[1];
	            	      var SignDateDay=SignDate.split("-")[2];
	            	      console.log(SignDate)
	            	      console.log(SignDateYear)
	            	      console.log(SignDateMonth)
	            	      console.log(SignDateDay)
	            	      
	            	      $("#view6 .SignDateYear").text("").text(SignDateYear);
	            	      $("#view6 .SignDateMonth").text("").text(SignDateMonth);
	            	      $("#view6 .SignDateDay").text("").text(SignDateDay);
	            	      $("#view6 .CustomerCompany").text("").text(data[0].RMBContract[i].CustomerCompany);
	            	      $("#view6 .CustomerTel").text("").text(data[0].RMBContract[i].CustomerTel);
	            	      $("#view6 .CustomerFax").text("").text(data[0].RMBContract[i].CustomerFax);
	            	      $("#view6 .CustomerContact").text("").text(data[0].RMBContract[i].CustomerContact);
	            	      $("#view6 .SecondContact").text("").text(data[0].RMBContract[i].SecondContact);
	            	      $("#view6 .TotalPrice").text("").text(data[0].RMBContract[i].TotalPrice);
	            	      $("#view6 .Payment").text("").text(data[0].RMBContract[i].Payment);
	            	      $("#view6 .Payment2").text("").text(data[0].RMBContract[i].Payment);
	            	      $("#view6 .LeadTime").text("").text(data[0].RMBContract[i].LeadTime);
	            	      $("#view6 .DeliveryPoint").text("").text(data[0].RMBContract[i].DeliveryPoint);
	            	      $("#view6 .yejiao").attr("value",data[0].RMBContract[i].ID);
	            	}
	            	var itemStr="";
	            	/*alert(currentTr.find("td.ExchangeRate").text())
	            	alert(currentTr.find("td.TaxCategories").text())*/
	            	for(var i = 1 ; i <data[0].RMBCommoditys.length; i++ ){
	            		itemStr  += 
	            			'<tr class="pdf_one_tr">'+
				                '<td class="id" value="'+data[0].RMBCommoditys[i].ID+'">'+i+'</td>'+
				                '<td class="CommodityModel" contenteditable="true">'+data[0].RMBCommoditys[i].CommodityModel+'</td>'+
				                '<td class="Remark" contenteditable="true">'+data[0].RMBCommoditys[i].Remark+'</td>'+
				               ' <td class="Quantity" contenteditable="true">'+data[0].RMBCommoditys[i].Quantity+'</td>'+
				               ' <td  class="Unit" contenteditable="true" style="display:none;">'+data[0].RMBCommoditys[i].Unit+'</td>'+

				               ' <td class="UnitPrice" contenteditable="true">'+fmoney(data[0].RMBCommoditys[i].UnitPrice *currentTr.find("td.ExchangeRate").text()*currentTr.find("td.TaxCategories").text()*1.07*1.03)+'</td>'+
				              ' <td  class="SumPrice" contenteditable="true">'+fmoney(parseFloat(data[0].RMBCommoditys[i].UnitPrice *currentTr.find("td.ExchangeRate").text()*currentTr.find("td.TaxCategories").text()*1.07*1.03*data[0].RMBCommoditys[i].Quantity).toFixed(2))+'</td>'+

				               '<td><input type="button" class="add_del3" style="width:90%;height:90%;" value="删除"></td>'+
				            '</tr>';
	            		$("#view6 .QuoteID").text("").html(data[0].RMBCommoditys[i].QuoteID);
	            	}
	            	$("#view6 .pdf_six_tab").after(itemStr);
	            	//计算合同RMB部分的总价
	            	var totalNum = 0;
	        		for(var i = 0; i< $("#view6 .pdf_one_tr").length ; i++){
	        			totalNum += rmoney(fmoney($("#view6 .pdf_one_tr").eq(i).find(".SumPrice").text()));
	        		}
	        		 $("#view6 .TotalPrice").text("").text(fmoney(totalNum));
	        		 $("#view6 .TotalPriceBig").text(" ").text(changeMoneyToChinese($(".pdf_six_tab1 .TotalPrice").text().replace(/,/g,'')));
	            },
	            error : function () {
	                $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	            }
	        });
			$('#view6').show(); 
			$('#view5').hide(); 
	    }
	})
	//*********************合同模板  删除与添加*******************************************
	//添加USD合同的第二个表格的信息
 	$('#ContractUSD #Contract_ADD').click(function(){
 		var addFlag = $("#view5 .pdf_one_tr").length;
 		addFlag++;
 		itemStr = 
			'<tr class="pdf_one_tr">'+
			    '<td class="id" value="">'+"1-"+addFlag+'</td>'+
                '<td class="Model" contenteditable="true"></td>'+
                '<td class="Remark" contenteditable="true"></td>'+
               ' <td  class="Unit" contenteditable="true"></td>'+
               ' <td class="Quantity" contenteditable="true"></td>'+
               ' <td class="UnitPrice" contenteditable="true"></td>'+
               ' <td  class="SumPrice" contenteditable="true"></td>'+
               '<td><input type="button" class="add_del2" style="width:90%;height:90%;" value="删除"></td>'+
            '</tr>';
 		$("#view5 .pdf_five_tab1").before(itemStr);
 	})
	//合同USD模板 删除
	 $(document).on("click","#view5 .add_del2",function(){
		  var Type="USDContract";
		  var USDID = $(this).parent().parent().find(".id").attr("value");
		  var that=$(this).parent().parent();
		  if(USDID== ""){
				that.remove();
		  }
		  else{
			        	that.remove();
			        	//计算合同美元部分的总价
//		        		 删除某一项成功之后需要重新进行遍历，依次改变序号的赋值，并重新计算美元总价格
			        	if($("#view5 .pdf_one_tr").length >0){
			        		var totalNum = 0;
			        		for(var i = 0; i< $("#view5 .pdf_one_tr").length ; i++){
			        			totalNum += rmoney(fmoney($("#view5 .pdf_one_tr").eq(i).find(".SumPrice").text()));
//			        			要在这里重新对序号项赋值，清空原来，再次append***********************************
			        		}
			        	}else{
			        		var totalNum = 0;
			        	}
		        		 $("#view5 .TotalPrice").text("").text(fmoney(totalNum));
		        		 $("#view5 .TotalPriceBig").text(" ").text(changeMoneyToChinese(totalNum.toFixed(2)));
		        		 $.MsgBox_Unload.Alert("提示", "删除成功！");

		   }
     })
     //添加RMB合同的第二个表格的信息
 	$('#ContractChina #Contract_ADD1').click(function(){
 		var addFlag = $("#view6 .pdf_one_tr").length;
 		addFlag++;
 		itemStr = 
 			'<tr class="pdf_one_tr">'+
 			 '<td class="id" value="">'+addFlag+'</td>'+
            '<td class="CommodityModel" contenteditable="true"></td>'+
            '<td class="Remark" contenteditable="true"></td>'+
           ' <td class="Quantity" contenteditable="true"></td>'+
           ' <td  class="Unit" contenteditable="true" style="display:none;"></td>'+
           ' <td class="UnitPrice" contenteditable="true"></td>'+
           ' <td  class="SumPrice" contenteditable="true"></td>'+
           '<td><input type="button" class="add_del3" style="width:90%;height:90%;" value="删除"></td>'+
        '</tr>';
 		$("#view6 .pdf_six_tab1").before(itemStr);
 	 })
 	 	//合同RMB模板 删除
	 $(document).on("click","#view6 .add_del3",function(){
	  var Type="RMBContract";
	  var ID = $(this).parent().parent().find(".id").attr("value");
		var that=$(this).parent().parent();
	        	 that.remove();
	        	//计算合同RMB部分的总价
	        	 if($("#view6 .pdf_one_tr").length>0){
	        		  var totalNum = 0;
		        		for(var i = 0; i< $("#view6 .pdf_one_tr").length ; i++){
		        			totalNum += rmoney(fmoney($("#view6 .pdf_one_tr").eq(i).find(".SumPrice").text()));
		        		}
	        	 }else{
	        		 var totalNum = 0;
	        	 }
        		$("#view6 .TotalPrice").text("").text(fmoney(totalNum));
        		$("#view6 .TotalPriceBig").text(" ").text(changeMoneyToChinese(totalNum));
        		$.MsgBox_Unload.Alert("提示", "删除成功！");
     })
     
     //*********************合同模板  提交*******************************************
      
	//合同USD模板 提交
	 $(document).on("click","#submit_n5",function(){
		 $("#submit_n5").attr("disabled",false);
			$("#submit_n5").css({
				"background":"#dddddd",
				"color":"#808080",
				"border":"none",
				"box-shadow":"0 0 0 0 #f8fcfd"
			});
		var submitView = $(".hidePdf5 .isHover").attr("ID");
		console.log(submitView)
//		如果是美金合同
		if(submitView == "USDContract"){
			var Type="USDContract";
		    var ContractNO=$('#view5 .ContractNO').text();
		    var Date=$('#view5 .Date').text();
		    var Version=$('#view5 .Version').text();
		    var CustomerCompany=$('#view5 .CustomerCompany').eq(0).text();
		    var CustomerTel=$('#view5 .CustomerTel').text();
		    var CustomerFax=$('#view5 .CustomerFax').text();
		    var CustomerContact=$('#view5 .CustomerContact').eq(0).text();
		    var TotalPrice=rmoney($('#view5 .TotalPrice').text());
		    var Payment=$('#view5 .Payment').eq(0).text();
		    var PayTime=$('#view5 .PayTime').eq(0).text();
		    console.log(PayTime);
		    
		    var LeadTime=$('#view5 .LeadTime').text();
		    var AirPort=$('#view5 .AirPort').eq(1).text();
		    var CustomerAdd=$('#view5 .CustomerAdd').text();
		    var Destination=$('#view5 .Destination').eq(0).text();
		    var Manufacturer=$('#view5 .Manufacturer').eq(0).text();
		    var Shipment=$('#view5 .Shipment').eq(0).text();
		    var ShipmentTime=$('#view5 .ShipmentTime').eq(0).text();
		    var ShippingMark=$('#view5 .ShippingMark').text();
		    var ID=$('#view5 .yejiao').attr("value");
		    var QuoteID = $('#view5 .yemei').attr("value");
		    
		    if($('#view5 .yejiao').attr("value")){
		    	var ID=$('#view5 .yejiao').attr("value");
	    	}else{
	    		var ID=0;
	    	}
		    
		    if($('#view5 .yemei').attr("value")){
		    	 var QuoteID = $('#view5 .yemei').attr("value");
	    	}else{
	    		 var QuoteID=0;
	    	}
		    var Exist;
		    var USDID = [];
		    var Remark=[];
		    var Model=[];
		    var Unit=[];
		    var UnitPrice=[];
		    var Quantity=[];
		    if($("#view5 .pdf_one_tr").length != 0){
				Exist = "yes";
				for(var i =0 ; i < $("#view5 .pdf_one_tr").length ; i++){
			    	if($("#view5 .pdf_one_tr").eq(i).find("td").eq(0).attr("value")){
			    		for(var i = 0;i<$("#view5 .pdf_one_tr").length;i++){
//*************************************************************************************改
				    		USDID.push($("#view5 .pdf_one_tr").eq(i).find("td").eq(0).attr("value"));
				    		Remark.push($("#view5 .pdf_one_tr").eq(i).find(".Remark").text())
					    	Model.push($("#view5 .pdf_one_tr").eq(i).find(".Model").text())
					    	Unit.push($("#view5 .pdf_one_tr").eq(i).find(".Unit").text())
					    	Quantity.push($("#view5 .pdf_one_tr").eq(i).find(".Quantity").text())
					    	UnitPrice.push(rmoney($('#view5 .pdf_one_tr').eq(i).find(".UnitPrice").text().replace(/,/g,'')));
			    		}
			    	}else{
			    		USDID.push(0);
			    		Remark.push(0);
			    		Model.push(0);
			    		Unit.push(0);
			    		Quantity.push(0);
			    		UnitPrice.push(0);
			    	}
			    	console.log(USDID);
			    	console.log(Remark);
			    	console.log(Model);
			    	console.log(Unit);
			    	console.log(Quantity);
			    	console.log(UnitPrice);
			    	
			    }
			}
			else{
				Exist = "no";
			}
	    	$.ajax({
	            type : 'get',
	            url : 'ModifyContractModel',
	            data : {
	            	ID:ID,
	            	Type : Type,
	                ContractNO : ContractNO,
	                Date : Date,
	                CustomerCompany : CustomerCompany,
	                CustomerTel : CustomerTel,
	                CustomerFax : CustomerFax,
	                CustomerContact : CustomerContact,
	                TotalPrice : TotalPrice,
	                Payment : Payment,
	                PayTime : PayTime,
	                LeadTime : LeadTime,
	                AirPort : AirPort,
	                CustomerAdd : CustomerAdd,
	                Destination : Destination,
	                Manufacturer : Manufacturer,
	                Shipment : Shipment,
	                ShipmentTime : ShipmentTime,
	                ShippingMark : ShippingMark,
	                Version : Version,
	                QuoteID : QuoteID,
	                Exist : Exist,
	                USDID : USDID,
	                Remark :Model ,
	                Model : Remark,
	                Unit : Unit,
	                UnitPrice : UnitPrice,
	                Quantity : Quantity
	            },
	            dataType : 'json',
	            success : function (data) {       	        	             	
	            	$.MsgBox.Alert('提示','提交成功');
		            $('.MailBar_cover_color').hide();
		            $('.hidePdf5').hide();
		            scorllFlag =  false; 
	            },
	            error : function () {
	                $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	            }
	        });
		}
		else{
			var Type="RMBContract";
		    var ContractNO=$('#view6 .ContractNO').text();
		    var SignDate=$('#view6 .SignDateYear').text()+"-"+$('#view6 .SignDateMonth').text()+"-"+$('#view6 .SignDateDay').text();
		    var CustomerCompany=$('#view6 .CustomerCompany').text();
		    var CustomerTel=$('#view6 .CustomerTel').eq(0).text();
		    var CustomerFax=$('#view6 .CustomerFax').text();
		    var CustomerContact=$('#view6 .CustomerContact').eq(0).text();
		    var SecondContact=$('#view6 .SecondContact').text();
		    var TotalPrice=rmoney($('#view6 .TotalPrice').text());
		    var Payment=$('#view6 .Payment').text();
		    var LeadTime=$('#view6 .LeadTime').text();
		    var DeliveryPoint=$('#view6 .DeliveryPoint').text();
		    var ID=$('#view6 .yejiao').attr("value");
		    var QuoteID = $('#view6 .yemei').attr("value");
		    
		    var Exist;
		    var RMBID = [];
		    var Remark=[];
		    var Model=[];
		    var Unit=[];
		    var UnitPrice=[];
		    var Quantity=[];
		    console.log("SignDate"+SignDate);
		    
		    if($("#view6 .pdf_one_tr").length != 0){
				Exist = "yes";
				for(var i =0 ; i < $("#view6 .pdf_one_tr").length ; i++){
			    	 console.log( $("#view6 .pdf_one_tr").length)
			    	Remark.push($("#view6 .pdf_one_tr").eq(i).find(".Remark").text())
			    	Model.push($("#view6 .pdf_one_tr").eq(i).find(".CommodityModel").text())
			    	Unit.push($("#view6 .pdf_one_tr").eq(i).find(".Unit").text())
			    	Quantity.push($("#view6 .pdf_one_tr").eq(i).find(".Quantity").text())
			    	UnitPrice.push(rmoney($('#view6 .pdf_one_tr').eq(i).find(".UnitPrice").text().replace(/,/g,'')));
			    	if($("#view6 .pdf_one_tr").eq(i).find("td").eq(0).attr("value")){
			    		RMBID.push($("#view6 .pdf_one_tr").eq(i).find("td").eq(0).attr("value"));
			    	}else{
			    		RMBID.push(0);
			    	}
			    }
			}
			else{
				Exist = "no";
			}
	    	$.ajax({
	            type : 'get',
	            url : 'ModifyContractModel',
	            data : {
	            	ID:ID,
	            	Type : Type,
	                ContractNO : ContractNO,
	                SignDate : SignDate,
	                CustomerCompany : CustomerCompany,
	                CustomerTel : CustomerTel,
	                CustomerFax : CustomerFax,
	                CustomerContact : CustomerContact,
	                SecondContact : SecondContact,
	                TotalPrice : TotalPrice,
	                Payment : Payment,
	                LeadTime : LeadTime,
	                DeliveryPoint : DeliveryPoint,
	                QuoteID : QuoteID,
	                Exist : Exist,
	                RMBID : RMBID,
	                Remark : Remark,
	                Model : Model,
	                Unit : Unit,
	                UnitPrice : UnitPrice,
	                Quantity : Quantity
	            },
	            dataType : 'json',
	            success : function (data) {       	        	             	
	            	$.MsgBox.Alert('提示','提交成功');
		            $('.MailBar_cover_color').hide();
		            $('.hidePdf5').hide();
	            },
	            error : function () {
	                $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	            }
	        });
		}
	});
	//合同模板  导出
	 $(document).on("click","#exportPDF5,#exportWord5",function(){
			var exportView = $(".hidePdf5 .isHover").attr("ID");
			console.log(exportView)
			if($(this).attr("id") =="exportWord5" ){
				var Type = "word";
			}		
			else{
				var Type = "PDF";
			}
			if(exportView == "RMBContract"){
				 var ContractNO=$('#view6 .ContractNO').eq(0).text();
				 var Year=$('#view6 .SignDateYear').eq(0).text();
				 var Month=$('#view6 .SignDateMonth').eq(0).text();
				 var Day=$('#view6 .SignDateDay').eq(0).text();
				 var CustomerCompany=$('#view6 .CustomerCompany').eq(0).text();
				 var CustomerTel=$('#view6 .CustomerTel').eq(0).text();
				 var CustomerFax=$('#view6 .CustomerFax').eq(0).text();
				 var CustomerContact=$('#view6 .CustomerContact').eq(0).text();
				 var SecondContact=$('#view6 .SecondContact ').eq(0).text();
				 var Total=$('#view6 .TotalPrice').eq(0).text();
				 var BigTotal=$('#view6 .TotalPriceBig').eq(0).text();
				 var Address=$('#view6 .DeliveryPoint ').eq(0).text();
				 var Parts =[];var Description =[];var Qty =[];var Each =[];var Extended =[];
				 for(var i = 0;i < $('#view6 .pdf_one_tr').length;i++){
					 Parts.push($('#view6 .pdf_one_tr').eq(i).find(".CommodityModel").text());
					 Description.push($('#view6 .pdf_one_tr').eq(i).find(".Remark").text());
					 Qty.push($('#view6 .pdf_one_tr').eq(i).find(".Quantity").text());
					 Each.push($('#view6 .pdf_one_tr').eq(i).find(".UnitPrice").text());
					 Extended.push($('#view6 .pdf_one_tr').eq(i).find(".SumPrice").text());
				 }
				var ajaxData = {
						Type :Type,
						ContractNO:ContractNO,
						Year : Year,
						Month : Month,
						Day : Day,
						CustomerCompany : CustomerCompany,
		            	CustomerTel : CustomerTel,
		            	CustomerFax : CustomerFax,
		            	CustomerContact:CustomerContact,
		            	SecondContact : SecondContact,
		            	Total : Total,
		            	BigTotal : BigTotal,
		            	Address : Address,
		            	Parts : Parts,
		            	Description : Description,
		            	Qty : Qty,
		            	Each : Each,
		            	Extended :Extended
				}; 
				console.log(ajaxData);
				var ajaxURL  =  'DownloadRMBContract';
			}
			else{
				var ContractNO=$('#view5 .ContractNO').text();
				 var Date=$('#view5 .Date').text();
				 var Versions=$('#view5 .Version').text();
				 var Buyer=$('#view5 .CustomerCompany').eq(0).text();
				 var Buyer2=$('#view5 .CustomerCompany').eq(1).text();
				 var Add=$('#view5 .CustomerAdd').text();
				 var Tel=$('#view5 .CustomerTel').text();
				 var Fax=$('#view5 .CustomerFax').text();
				 var EndUser=$('#view5 .CustomerCompany ').eq(0).text();
				 var EndUser2=$('#view5 .CustomerCompany').eq(1).text();
				 var BigTotal=$('#view5 .TotalPriceBig').eq(0).text();
				 var CIP =$('#view5 .AirPort ').eq(0).text();
				 var Total=$('#view5 .TotalPrice').eq(0).text();
				 var Shipment =$('#view5 .Shipment').eq(0).text();
				 var Shipment2=$('#view5 .Shipment').eq(1).text();
				 var Destination =$('#view5 .Destination').eq(0).text();
				 var Destination2=$('#view5 .Destination').eq(1).text();
				 var ShipmentTime=$('#view5 .ShipmentTime').eq(0).text();
				 var ShipmentTime2=$('#view5 .ShipmentTime').eq(1).text();
				 var ShippingMark  =$('#view5 .ShippingMark ').text();
				 var Manufacturer=$('#view5 .Manufacturer').eq(0).text();
				 var Manufacturer2=$('#view5 .Manufacturer').eq(1).text();
				 var Payment=$('#view5 .Payment').eq(0).text();
//				 view5是美金合同，需要截取**********************************************
				 var Payment2  =$('#view5 .Payment2').eq(1).text();
				 var PayTime  =$('#view5 .PayTime').text();
				 var Parts =[];var Description =[];var Qty =[];var Each =[];var Extended =[];var unit =[];
				 for(var i = 0;i < $('#view5 .pdf_one_tr').length;i++){
					 Parts.push($('#view5 .pdf_one_tr').eq(i).find(".Model").text());
					 Description.push($('#view5 .pdf_one_tr').eq(i).find(".Remark").text());
					 Qty.push($('#view5 .pdf_one_tr').eq(i).find(".Quantity").text());
					 Each.push($('#view5 .pdf_one_tr').eq(i).find(".UnitPrice").text());
					 Extended.push($('#view5 .pdf_one_tr').eq(i).find(".SumPrice").text());
					 unit.push($('#view5 .pdf_one_tr').eq(i).find(".Unit").text());
				 }
				var ajaxData = {
						Type :Type,
						ContractNO:ContractNO,
						Date : Date,
						Versions : Versions,
						Buyer : Buyer,
						Buyer2 : Buyer2,
						Add : Add,
						Tel : Tel,
						Fax:Fax,
						EndUser : EndUser,
						EndUser2 : EndUser2,
						BigTotal : BigTotal,
						CIP : CIP,
						Total : Total,
						Shipment : Shipment,
						Shipment2 : Shipment2,
						Destination : Destination,
						Destination2 :Destination2,
						
						ShipmentTime : ShipmentTime,
						ShipmentTime2 : ShipmentTime2,
						ShippingMark : ShippingMark,
						Manufacturer :Manufacturer,
						Manufacturer2 : Manufacturer2,
						Payment : Payment,
						PayTime : PayTime,
						Parts : Parts,
		            	Description : Description,
		            	Qty : Qty,
		            	Each : Each,
		            	Extended :Extended,
		            	unit : unit
				}; 
				console.log(ajaxData);
				var ajaxURL  =  'DownloadUSDContract';
			}
			$.ajax({
	            type : 'get',
	            url  : ajaxURL,
	            data : ajaxData,
	            success : function (data) {
	            	console.log(data);
	            	window.location.href = data;
	            },
	            error : function () {
	            	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	            }
	        });
	 });
	//*********************************合同模板  结束*************************************************
	 
    //***********************备货清单  操作    开始***************************** 
	//备货清单模板 显示
	$('.contract-show7').click(function () {
	    var ID = $(this).parent().find("td").eq(0).attr("value");//即表格中当前条的ID
	    $('.hidePdf7 .yemei').attr("value",ID);
	    $(".hidePdf7 .ContractNO").text(" ");
		console.log(ID);
		var thisList=$(this).parent();
		$(".hidePdf7 .pdf_one_tr").remove();
		$.ajax({
	            type : 'get',
	            url : "QuoteDelivery",
	            data : {
	            	QuoteID : ID,
	            },
	            dataType : 'json',
	            success : function (data) { 
	            	console.log(data);
	            	//第一个集合
	            	for(var i = 1 ; i <data[0].Request.length; i++ ){
	            		  $(".hidePdf7 .Name").text(" ").text(data[0].Request[i].Name);
	            		  $(".hidePdf7 .ContractNO").text(" ").text(data[0].Request[i].ContractNO);
	            		  $(".hidePdf7 .PO").text(" ").text(data[0].Request[i].PO);
	            	      $(".hidePdf7 .SO").text(" ").text(data[0].Request[i].SO);
	            	      $(".hidePdf7 .Departure").text(" ").text(data[0].Request[i].Departure);
	            	      $(".hidePdf7 .Destination").text(" ").text(data[0].Request[i].Destination);
	            	      $(".hidePdf7 .Receiving").text(" ").text(data[0].Request[i].Receiving);
	            	      $(".hidePdf7 .yejiao").attr("value",data[0].Request[i].ID);
	            	      var obj ={"Fumigation":data[0].Request[i].Fumigation,
		            	    		  "Size":data[0].Request[i].Size,
		            	    		  "Weight":data[0].Request[i].Weight,
		            	    		  "ProductImg":data[0].Request[i].ProductImg,
		            	    		  "NamePlateImg":data[0].Request[i].NamePlateImg,
		            	    		  "OriginInfo":data[0].Request[i].OriginInfo,
		            	    		  "ProductName":data[0].Request[i].ProductName,
		            	    		  "PackingQty":data[0].Request[i].PackingQty,
		            	    		  "ShippingMark":data[0].Request[i].ShippingMark,
		            	    		  "SplitShipment":data[0].Request[i].SplitShipment,
		            	    		  "Airelift":data[0].Request[i].Airelift,
		            	    		  "Truck":data[0].Request[i].Truck,
		            	    		  "FastMail":data[0].Request[i].FastMail,
		            	    		  "TailCar":data[0].Request[i].TailCar,
		            	    		  "Unloading":data[0].Request[i].Unloading
	            	     };
	                      for (var key in obj){
	                          $(".hidePdf7 input[name="+key+"][value="+obj[key]+"]").prop('checked',true);
	                      }
	            	}
	            	//第二个集合
	            	var len=data[0].Delivery.length;
	            	addFlag=len;
	            	var itemStr ="";
	            	for(var i = 1 ; i <len; i++ ){
	            		 itemStr += 
	            			'<tr class="pdf_one_tr">'+
				                '<td class="id" value="'+data[0].Delivery[i].ID+'">'+i+'</td>'+
				                '<td class="Model" contenteditable="true">'+data[0].Delivery[i].Model+'</td>'+
				                '<td class="Description" contenteditable="true">'+data[0].Delivery[i].Description+'</td>'+
				               ' <td  class="Quantity" contenteditable="true">'+data[0].Delivery[i].Quantity+'</td>'+
				               ' <td class="Remarks" contenteditable="true">'+data[0].Delivery[i].Remarks+'</td>'+
				               '<td><input type="button" class="add_del4" style="width:90%;height:90%;" value="删除"></td>'+
				            '</tr>';
	            		$(".hidePdf7 .QuoteID").text("").html(data[0].Delivery[i].QuoteID);
	            	}
	            	$(".hidePdf7 .pdf_seven_tab").append(itemStr);
	            	
	            	//第三个集合
	            	var len2=data[0].NotInPO.length, prev_len = len;
        			addFlag +=len2;
	            	var itemStr ="";
	            	for(var i = 1 ; i <len2; i++ ){
	            		 itemStr += 
	            			'<tr class="pdf_one_tr">'+
				                '<td class="id" value="'+data[0].NotInPO[i].ID+'">'+prev_len+'</td>'+
				                '<td class="Model" contenteditable="true">'+data[0].NotInPO[i].Model+'</td>'+
				                '<td class="Description" contenteditable="true">'+data[0].NotInPO[i].Description+'</td>'+
				               ' <td  class="Quantity" contenteditable="true">'+data[0].NotInPO[i].Quantity+'</td>'+
				               ' <td class="Remarks" contenteditable="true">'+data[0].NotInPO[i].Remarks+'</td>'+
				               '<td><input type="button" class="add_del4" style="width:90%;height:90%;" value="删除"></td>'+
				            '</tr>';
	            		$(".hidePdf7 .QuoteID").text("").html(data[0].NotInPO[i].QuoteID);
	            		prev_len++;
	            	}
	            	//判断是否有数据
	            	if(len2>1){
	            		$(".hidePdf7 .pdf_seven_tab").append(itemStr);
            		}
	            },
	            error : function () {
	            	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	            }
	        });
		console.log($("#view7").height())
		$('.MailBar_cover_color').show();
		$('.hidePdf7').show(); 
		// $(".MailBar_cover_color").css("height",$("#view7").height()+130);
	 })	

		//备货清单模板 提交
		 $(document).on("click","#submit_n7",function(){
			var Type="Modify";
			var Name=$('.hidePdf7 .Name').text();
		    var ContractNO=$('.hidePdf7 .tab1 .ContractNO').text();
		    var PO=$('.hidePdf7 .PO').eq(0).text();
		    var SO=$('.hidePdf7 .SO').eq(0).text();
		    var Fumigation=$(".hidePdf7 input[name='Fumigation']:checked").val();
		    var Size=$(".hidePdf7 input[name='Size']:checked").val();
		    var Weight=$(".hidePdf7 input[name='Weight']:checked").val();
		    var ProductImg=$(".hidePdf7 input[name='ProductImg']:checked").val();
		    var NamePlateImg=$(".hidePdf7 input[name='NamePlateImg']:checked").val();
		    var OriginInfo=$(".hidePdf7 input[name='OriginInfo']:checked").val();
		    var ProductName=$(".hidePdf7 input[name='ProductName']:checked").val();
		    var PackingQty=$(".hidePdf7 input[name='PackingQty']:checked").val();
		    var ShippingMark=$(".hidePdf7 input[name='ShippingMark']:checked").val();
		    var Departure=$('.hidePdf7 .Departure').text();
		    var Destination=$('.hidePdf7 .Destination').text();
		    var Receiving=$('.hidePdf7 .Receiving').text();
		    var SplitShipment=$(".hidePdf7 input[name='SplitShipment']:checked").val();
		    var Airelift=$(".hidePdf7 input[name='Airelift']:checked").val();
		    var Truck=$(".hidePdf7 input[name='Truck']:checked").val();
		    var FastMail=$(".hidePdf7 input[name='FastMail']:checked").val();
		    var TailCar=$(".hidePdf7 input[name='TailCar']:checked").val();
		    var Unloading=$(".hidePdf7 input[name='Unloading']:checked").val();
		    if(ContractNO == '' || PO == '' || SO == '' ){
		    	alert("请填写包装和运输的相关信息！");
		    	return;
		    }
		    
		    var PO=$('.hidePdf7 .PO').eq(0).text();
		    var SO=$('.hidePdf7 .SO').eq(0).text();
		    var Fumigation=$(".hidePdf7 input[name='Fumigation']:checked").val();
		    var Size=$(".hidePdf7 input[name='Size']:checked").val();
		    var Weight=$(".hidePdf7 input[name='Weight']:checked").val();
		    var ProductImg=$(".hidePdf7 input[name='ProductImg']:checked").val();
		    var NamePlateImg=$(".hidePdf7 input[name='NamePlateImg']:checked").val();
		    var OriginInfo=$(".hidePdf7 input[name='OriginInfo']:checked").val();
		    var ProductName=$(".hidePdf7 input[name='ProductName']:checked").val();
		    var PackingQty=$(".hidePdf7 input[name='PackingQty']:checked").val();
		    var ShippingMark=$(".hidePdf7 input[name='ShippingMark']:checked").val();
		    var Departure=$('.hidePdf7 .Departure').text();
		    var Destination=$('.hidePdf7 .Destination').text();
		    var Receiving=$('.hidePdf7 .Receiving').text();
		    var SplitShipment=$(".hidePdf7 input[name='SplitShipment']:checked").val();
		    var Airelift=$(".hidePdf7 input[name='Airelift']:checked").val();
		    var Truck=$(".hidePdf7 input[name='Truck']:checked").val();
		    var FastMail=$(".hidePdf7 input[name='FastMail']:checked").val();
		    var TailCar=$(".hidePdf7 input[name='TailCar']:checked").val();
		    var Unloading=$(".hidePdf7 input[name='Unloading']:checked").val();
		    var QuoteID = $('.hidePdf7 .yemei').attr("value");
		    
		    if($('.hidePdf7 .yejiao').attr("value")){
		    	var ID=$('.hidePdf7 .yejiao').attr("value");
			}
			else{
				var ID="0";
			}
		    var Exist;
		    var DeliveryID = [];
		    var Remarks=[];
		    var Model=[];
		    var Description=[];
		    var Quantity=[];
		    
		    if($(".hidePdf7 .pdf_one_tr").length != 0){
				Exist = "yes";
				for(var i =0 ; i < $(".hidePdf7 .pdf_one_tr").length ; i++){
			        console.log( $(".hidePdf7 .pdf_one_tr").length)
			    	Remarks.push($(".hidePdf7 .pdf_one_tr").eq(i).find(".Remarks").text())
			    	Model.push($(".hidePdf7 .pdf_one_tr").eq(i).find(".Model").text())
			    	Description.push($(".hidePdf7 .pdf_one_tr").eq(i).find(".Description").text())
			    	Quantity.push($(".hidePdf7 .pdf_one_tr").eq(i).find(".Quantity").text())
			    	if($(".hidePdf7 .pdf_one_tr").eq(i).find("td").eq(0).attr("value")){
						DeliveryID.push($(".hidePdf7 .pdf_one_tr").eq(i).find("td").eq(0).attr("value"));
					}
					else{
						DeliveryID.push(0);
					}
			    }
			}
			else{
				Exist = "no";
			}
	    	$.ajax({
	            type : 'get',
	            url : 'QuoteDeliveryOperate',
	            data : {
	            	ID:ID,
	            	Type : Type,
	            	Name : Name,
	                ContractNO : ContractNO,
	                PO:PO,
	                SO : SO,
	                Fumigation : Fumigation,
	                Size : Size,
	                Weight : Weight,
	                ProductImg : ProductImg,
	                NamePlateImg : NamePlateImg,
	                OriginInfo : OriginInfo,
	                ProductName : ProductName,
	                PackingQty : PackingQty,
	                ShippingMark : ShippingMark,
	                Departure : Departure,
	                Destination : Destination,
	                Receiving : Receiving,
	                SplitShipment : SplitShipment,
	                Airelift : Airelift,
	                Truck : Truck,
	                FastMail : FastMail,
	                TailCar : TailCar,
	                Unloading : Unloading,
	                QuoteID : QuoteID,
	                Exist : Exist,
	                DeliveryID : DeliveryID,
	                Remarks : Remarks,
	                Model : Model,
	                Description : Description,
	                Quantity : Quantity
	            },
	            dataType : 'json',
	            success : function (data) { 
	            	console.log(data)
	            	if(data==true){
	            		$.MsgBox.Alert('提示','提交成功');
			            $('.MailBar_cover_color').hide();
			            $('.hidePdf7').hide();
	            	}else{
	            		$.MsgBox_Unload.Alert("提示", "提交失败！");
	            	}
	            },
	            error : function () {
	            	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	            }
	        });
		});
		//添加发货清单的信息
		$('.StockList #send_ADD').click(function(){
			var addFlag = $(".hidePdf7 .pdf_one_tr").length;
			console.log(addFlag);
			addFlag++;
			var itemStr=
			'<tr class="pdf_one_tr">'+
	        '<td class="id" value="">'+addFlag+'</td>'+
	        '<td class="Model" contenteditable="true"></td>'+
	        '<td class="Description" contenteditable="true"></td>'+
	       ' <td  class="Quantity" contenteditable="true"></td>'+
	       ' <td class="Remarks" contenteditable="true"></td>'+
	       '<td><input type="button" class="add_del4" style="width:90%;height:90%;" value="删除"></td>'+
	    '</tr>';
			$(".hidePdf7 .pdf_seven_tab").append(itemStr);
		})
		//备货清单模板 删除
		$(document).on("click",".hidePdf7 .add_del4",function(){
		  var ID = $(this).parent().parent().find(".id").attr("value");
		  var that=$(this).parent().parent();
			console.log("ID"+ID)
		        	that.remove();
		        	$.MsgBox_Unload.Alert("提示", "删除成功！");
	     })

	     //备货清单模板下载  
	 	$('#download7').click(function () {
	 		var QuoteID = $('.hidePdf7 .yemei').attr("value");
	 	    $.ajax({
	 	        type: 'get',
	 	        url: 'DownloadDelivery',
	 	        data: {
	 	        	QuoteID:QuoteID
	 	        },
	 	        success: function (data) {
	 	        	window.location.href=data;
	 	        },
	 	        error: function () {
	 	        	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	 	        }
	 	    });
	 	});
     $("#Stocking").click(function(){
    	   var Type="Modify";
			var Name=$('.hidePdf7 .Name').text();
		    var ContractNO=$('.hidePdf7 .tab1 .ContractNO').text();
		    var PO=$('.hidePdf7 .PO').eq(0).text();
		    var SO=$('.hidePdf7 .SO').eq(0).text();
		    var Fumigation=$(".hidePdf7 input[name='Fumigation']:checked").val();
		    var Size=$(".hidePdf7 input[name='Size']:checked").val();
		    var Weight=$(".hidePdf7 input[name='Weight']:checked").val();
		    var ProductImg=$(".hidePdf7 input[name='ProductImg']:checked").val();
		    var NamePlateImg=$(".hidePdf7 input[name='NamePlateImg']:checked").val();
		    var OriginInfo=$(".hidePdf7 input[name='OriginInfo']:checked").val();
		    var ProductName=$(".hidePdf7 input[name='ProductName']:checked").val();
		    var PackingQty=$(".hidePdf7 input[name='PackingQty']:checked").val();
		    var ShippingMark=$(".hidePdf7 input[name='ShippingMark']:checked").val();
		    var Departure=$('.hidePdf7 .Departure').text();
		    var Destination=$('.hidePdf7 .Destination').text();
		    var Receiving=$('.hidePdf7 .Receiving').text();
		    var SplitShipment=$(".hidePdf7 input[name='SplitShipment']:checked").val();
		    var Airelift=$(".hidePdf7 input[name='Airelift']:checked").val();
		    var Truck=$(".hidePdf7 input[name='Truck']:checked").val();
		    var FastMail=$(".hidePdf7 input[name='FastMail']:checked").val();
		    var TailCar=$(".hidePdf7 input[name='TailCar']:checked").val();
		    var Unloading=$(".hidePdf7 input[name='Unloading']:checked").val();
		    if(ContractNO == '' || PO == '' || SO == '' ){
		    	$.MsgBox_Unload.Alert("提示","请填写包装和运输的相关信息并提交！");
		    	return;
		    }
    	 
    	 var id = $('.hidePdf7 .yemei').attr("value");
    	 
    	 var ConfirmText = "";
    	 $.ajax({
	           type : 'POST',
	           url : 'SendEmailLogisticsDelivery',
	           data : {
	           	QuoteID:id,
	           },
	           success : function (data) {
	              console.log(data);
	              console.log(typeof data);
	              if(data == ""){
	            	  ConfirmText = "是否发送备货清单"
	              }
	              else{
	            	  ConfirmText = data
	              }
	         	 $.MsgBox_Unload.Confirm("提示",ConfirmText,function(){
	    	   	  	 $.ajax({
	    		           type : 'get',
	    		           url : 'SendEmailLogisticsDelivery',
	    		           data : {
	    		           	QuoteID:id,
	    		           	Status : "yes",
	    		           },
	    		           dataType : 'json',
	    		           success : function (data) {
	    		           		console.log(data);
	    		           		console.log(typeof data);
	    		           		if(data==true){
	    		                	$.MsgBox.Alert('提示','发送成功!');
	    		           		}else{
	    		        	   		$.MsgBox_Unload.Alert("提示", "发送失败！");
	    		           		}
	    		           },
	    		           error : function () {
	    		        	   $.MsgBox_Unload.Alert("提示", "发送失败！");
	    		           }
	    	         }); 
	        	 });
	              
	           },
	           error : function () {
	        	   $.MsgBox_Unload.Alert("提示", "发送失败！");
	           }
        }); 

     })
      //***********************备货清单  操作    结束*****************************
     
     //*********************************下单 PO   整合************************************
    //第一次  加载模板  显示
	$('.CascadePO').click(function() {
		$(".CascadePOPdf").attr("value",$(this).parent().index());
		 var ID = $(this).parent().find("td").eq(0).attr("value");
		 $('#view8 .yemei').attr("currentID",ID);
		 var reg = /\.com/;
		 if(UserName.match(reg)){
			 UserName = UserName.substring(0,UserName.indexOf("@"));
		 }
		console.log('111'+UserName)
		$("#download8,#send_submit1").removeClass("disabled");
		 $("#view8 .Contact").text(UserName); 
		 $("#view8 .Email").text(EMAIL); 
		 var thisList = $(this).parent();
		 $(".CascadePOItemTr").remove();
		 $.ajax({
			type : 'get',
			url : "QuoteCascadePO",
			data : {
				quoteID : ID,
				Type:'Parts'
			},
			dataType : 'json',
			success : function(data) {
				console.log("初次加载PO");
				console.log(data);
				$(".CompleteID").text(data[0].CompleteID);
				
				var itemStr = "";
				if(data[0].part.length >1){
					for (var i = 1; i < data[0].part.length; i++) {
						console.log("inini")
						itemStr += 
							'<tr class="CascadePOItemTr">'
								+ '<td value="'+data[0].part[i].ID+'"><p contenteditable="true" class="Item" >'
								+ i
								+ '</p></td>'
								+ '<td><p contenteditable="true" class="Part">'
								+ data[0].part[i].Model
								+ '</p></td>'
								+ '<td><p contenteditable="true" class="Description">'
								+ data[0].part[i].Description
								+ '</p></td>'
								+ '<td><p contenteditable="true" class="Qty ConfigBlur">'
								+ data[0].part[i].Quantity
								+ '</p></td>'
								+ '<td><p contenteditable="true" class="UnitPrice ConfigBlur">'
								+ fmoney(data[0].part[i].UnitPrice)
								+ '</p></td>'
								+ '<td><p  class="ExtendedPrice">'
								+ fmoney(data[0].part[i].ExtendedPrice)
								+ '</p></td>'
								+ '<td><p><input type="button" value="删除"  class="CascadePOItemBtn"/></p></td>'
								+ '</tr>';
					}
					$("#CascadePOCon  #ItemTit").after(itemStr);
				}
				else{
					$("#CascadePOCon  .CascadePOItemTr").remove();
				}
				//计算PO美元部分的总价
				var totalNum = 0;
				for (var i = 0; i < $("#view8 .CascadePOItemTr").length; i++) {
					totalNum += ($("#view8 .CascadePOItemTr").eq(i).find(".Qty").text())* rmoney(fmoney($("#view8 .CascadePOItemTr").eq(i).find(".UnitPrice").text()));
				}
				if(data[0].POInfo.length >1){
					$(".POID1").text(data[0].POInfo[1].ID);
					var tempTotalNum0 = data[0].POInfo[1].SubTotal;
					var totalNum0 = globalDataHandle(tempTotalNum0,0);
					totalNum0 = totalNum0*1;
					totalNum0 = totalNum0 == 0?totalNum:totalNum0;
					console.log("初次加载PO，totalNum0");
					console.log(totalNum0);
					console.log(totalNum);
					$("#view8 .SubTotal").text("").text("$"+fmoney(totalNum0));
				}else{
					$(".POID1").text(0);
					$("#view8 .SubTotal").text("").text('$'+fmoney((totalNum*1).toFixed(2)));
				}
				if (data[0].POInfo.length > 1) {
					for (var i = 1; i < data[0].POInfo.length; i++) {
						$("#view8 .Number").text("").text(data[0].POInfo[i].Number);
						$("#view8 .RefNO").text("").text(data[0].POInfo[i].RefNO);
						$("#view8 .Version").text("").text(data[0].POInfo[i].Version);
						$("#view8 .ForwarderOne").text("").text(data[0].POInfo[i].ForwarderOne);
						$("#view8 .ForwarderTwo").text("").text(data[0].POInfo[i].ForwarderTwo);
						$("#view8 .ForwarderThree").text("").text(data[0].POInfo[i].ForwarderThree);
						$("#view8 .ForwarderFour").text("").text(data[0].POInfo[i].ForwarderFour);
						$("#view8 .ShipCompany").text("").text(data[0].POInfo[i].ShipCompany);
						$("#view8 .ShipAddr").text("").text(data[0].POInfo[i].ShipAddr);
						$("#view8 .ShipTel").text("").text(data[0].POInfo[i].ShipTel);
						$("#view8 .ShipAttn").text("").text(data[0].POInfo[i].ShipAttn);
						$("#view8 .EndCompany").text("").text(data[0].POInfo[i].EndCompany);
						$("#view8 .EndAddr").text("").text(data[0].POInfo[i].EndAddr);
						$("#view8 .ContactPerson").text("").text(data[0].POInfo[i].ContactPerson);
						$("#view8 .EndTel").text("").text(data[0].POInfo[i].EndTel);
						$("#view8 .DeliveryTerm").text("").text(data[0].POInfo[i].DeliveryTerm);
						$("#view8 .ShippingMark").text("").text(data[0].POInfo[i].ShippingMark);
						$("#view8 .ContractNO").text("").text(data[0].POInfo[i].ContractNO);
						$("#view8 .ShipmentPort").text("").text(data[0].POInfo[i].ShipmentPort);
						//$("#view8 .SubTotal").text("").text(fmoney(data[0].POInfo[i].SubTotal));
						var Discounted = data[0].POInfo[i].Discounted;
						var tempTotalNum = data[0].POInfo[i].SubTotal;
						var totalNum2 = globalDataHandle(tempTotalNum,0);
						totalNum2 = totalNum2*1;
						totalNum2 = totalNum2 == 0?totalNum:totalNum2;
						if(Discounted== '--' || Discounted == ''){
			 				 Discounted = '0.00%';
		 				}
						$("#view8 .Discounted").text("").text(Discounted);
						
						if(parseFloat(Discounted) == 0 || parseFloat(Discounted) == ''){
							$("#view8 .FinalTotal").text("").text('$'+fmoney((totalNum2*1).toFixed(2)));
						}else{
							var FinalTotal = '$'+(fmoney((totalNum*1).toFixed(2) * (parseFloat(Discounted) / 100)));
							$("#view8 .FinalTotal").text("").text(FinalTotal);
						}
						$("#view8 .yemei").attr("value",data[0].POInfo[i].ID);
					}
				} else {
					$("#view8 .yemei").attr("value", 0);
					$("#view8 .Discounted").text("").text("0.00%");
					$("#view8 .FinalTotal").text("").text('$'+fmoney(totalNum.toFixed(2))); 
					$("#view8 .Number").text("");
					$("#view8 .RefNO").text("");
					$("#view8 .Version").text("");
					$("#view8 .ForwarderOne").text("");
					$("#view8 .ForwarderTwo").text("");
					$("#view8 .ForwarderThree").text("");
					$("#view8 .ForwarderFour").text("");
					$("#view8 .ShipCompany").text("");
					$("#view8 .ShipAddr").text("");
					$("#view8 .ShipTel").text("");
					$("#view8 .ShipAttn").text("");
					$("#view8 .EndCompany").text("");
					$("#view8 .EndAddr").text("");
					$("#view8 .ContactPerson").text("");
					$("#view8 .EndTel").text("");
					$("#view8 .DeliveryTerm").text("");
					$("#view8 .ShippingMark").text("");
					$("#view8 .ContractNO").text("");
					$("#view8 .ShipmentPort").text("");
				}
				if(data[0].part.length < 1){
					$("#view8 .Discounted").text("").text("0.00%");
				}
			},
			error : function() {
				$.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
			}
		});
		
		scorllFlag = !scorllFlag;
		$('.CascadePOPdf').show();
		$('#view8').show();
        $('#view89,#view9,#view10').hide();
        $("#Sidebar_CascadePO").addClass("isHover").siblings().removeClass("isHover");
        // $(".MailBar_cover_color").css("height",$("#view8").height()+130);
		$(".MailBar_cover_color").show();
	})
 
     //整机 配件      切换模板 加载
	$(".Sidebar_PO div").click(function(){
		POForwShipClose();
		$(this).addClass("isHover").siblings().removeClass("isHover");
		var currentId = $(this).attr("id");
		var currentTr = $("#table1").find("tr").eq($(".CascadePOPdf").attr("value"));
		var thisList =currentTr.children(); 
		var ID = currentTr.find("td").eq(0).attr("value");
	    if(currentId =="Sidebar_CascadePO"){
	    	$('#view8 .yemei').attr("currentID",ID);
	    	var reg = /\.com/;
			 if(UserName.match(reg)){
				 UserName = UserName.substring(0,UserName.indexOf("@"));
			 }
			$("#view8 .Contact").text("").text(UserName); 
			$("#view8 .Email").text("").text(EMAIL);
	    	$(".CascadePOItemTr").remove();
			 $.ajax({
				type : 'get',
				url : "QuoteCascadePO",
				data : {
					quoteID : ID,
					Type:'Parts'
				},
				dataType : 'json',
				success : function(data) {
					console.log(data);
					$(".CompleteID").text(data[0].CompleteID);
					
					var itemStr = "";
					if(data[0].part.length >1){
						for (var i = 1; i < data[0].part.length; i++) {
							console.log("inini")
							itemStr += 
								'<tr class="CascadePOItemTr">'
									+ '<td value="'+data[0].part[i].ID+'"><p contenteditable="true" class="Item" >'
									+ i
									+ '</p></td>'
									+ '<td><p contenteditable="true" class="Part">'
									+ data[0].part[i].Model
									+ '</p></td>'
									+ '<td><p contenteditable="true" class="Description">'
									+ data[0].part[i].Description
									+ '</p></td>'
									+ '<td><p contenteditable="true" class="Qty ConfigBlur">'
									+ data[0].part[i].Quantity
									+ '</p></td>'
									+ '<td><p contenteditable="true" class="UnitPrice ConfigBlur">'
									+ fmoney(data[0].part[i].UnitPrice)
									+ '</p></td>'
									+ '<td><p  class="ExtendedPrice">'
									+ fmoney(data[0].part[i].ExtendedPrice)
									+ '</p></td>'
									+ '<td><p><input type="button" value="删除"  class="CascadePOItemBtn"/></p></td>'
									+ '</tr>';
						}
						$("#CascadePOCon #ItemTit").after(itemStr);
					}
					else{
						$("#CascadePOCon .CascadePOItemTr").remove();
					}
					//计算PO美元部分的总价
					var totalNum = 0;
					for (var i = 0; i < $("#view8 .CascadePOItemTr").length; i++) {
						totalNum += ($("#view8 .CascadePOItemTr").eq(i).find(".Qty").text())* rmoney(fmoney($("#view8 .CascadePOItemTr").eq(i).find(".UnitPrice").text()));
					}
					
					if(data[0].POInfo.length>1){
						$(".POID1").text(data[0].POID);
						var tempTotalNum0 = data[0].POInfo[1].SubTotal;
						var totalNum0 = globalDataHandle(tempTotalNum0,0);
						totalNum0 = totalNum0*1;
						if(totalNum0 == 0 || totalNum0 == "0"){
							$("#view8 .SubTotal").text("").text('$'+fmoney((totalNum*1)));
						}else{
							$("#view8 .SubTotal").text("").text("$"+fmoney(totalNum0));
						}
					}else{
						$(".POID1").text(0);
						$("#view8 .SubTotal").text("").text('$'+fmoney((totalNum*1).toFixed(2)));
					}

					if (data[0].POInfo.length > 1) {
						for (var i = 1; i < data[0].POInfo.length; i++) {
							$("#view8 .Number").text("").text(data[0].POInfo[i].Number);
							$("#view8 .Version").text("").text(data[0].POInfo[i].Version);
							$("#view8 .ForwarderOne").text("").text(data[0].POInfo[i].ForwarderOne);
							$("#view8 .ForwarderTwo").text("").text(data[0].POInfo[i].ForwarderTwo);
							$("#view8 .ForwarderThree").text("").text(data[0].POInfo[i].ForwarderThree);
							$("#view8 .ForwarderFour").text("").text(data[0].POInfo[i].ForwarderFour);
							$("#view8 .ShipCompany").text("").text(data[0].POInfo[i].ShipCompany);
							$("#view8 .ShipAddr").text("").text(data[0].POInfo[i].ShipAddr);
							$("#view8 .ShipTel").text("").text(data[0].POInfo[i].ShipTel);
							$("#view8 .ShipAttn").text("").text(data[0].POInfo[i].ShipAttn);
							$("#view8 .EndCompany").text("").text(data[0].POInfo[i].EndCompany);
							$("#view8 .EndAddr").text("").text(data[0].POInfo[i].EndAddr);
							$("#view8 .ContactPerson").text("").text(data[0].POInfo[i].ContactPerson);
							$("#view8 .EndTel").text("").text(data[0].POInfo[i].EndTel);
							$("#view8 .DeliveryTerm").text("").text(data[0].POInfo[i].DeliveryTerm);
							$("#view8 .ShippingMark").text("").text(data[0].POInfo[i].ShippingMark);
							$("#view8 .ContractNO").text("").text(data[0].POInfo[i].ContractNO);
							$("#view8 .ShipmentPort").text("").text(data[0].POInfo[i].ShipmentPort);
							//$("#view8 .SubTotal").text("").text(fmoney(data[0].POInfo[i].SubTotal));
							var Discounted = data[0].POInfo[i].Discounted;
							var tempTotalNum = data[0].POInfo[i].SubTotal;
							var totalNum2 = globalDataHandle(tempTotalNum,0);
							totalNum2 = totalNum2*1;
							totalNum2 = totalNum2 == 0?totalNum:totalNum2;
							if(Discounted == '--'|| Discounted == ''){
								Discounted = '0.00%';
							}
							$("#view8 .Discounted").text("").text(Discounted);
							
							if(parseFloat(Discounted) == 0 || parseFloat(Discounted) == ''){
								$("#view8 .FinalTotal").text("").text('$'+fmoney((totalNum2*1).toFixed(2)));
							}else{
								var FinalTotal = '$'+(fmoney((totalNum2*1).toFixed(2) * (parseFloat(Discounted) / 100)));
								$("#view8 .FinalTotal").text("").text(FinalTotal);
							}
							
							$("#view8 .yemei").attr("value",data[0].POInfo[i].ID);
						}
					} else {
						$("#view8 .yemei").attr("value", 0);
						$("#view8 .Discounted").text("").text("0.00%");
						$("#view8 .FinalTotal").text("").text('$'+fmoney((totalNum*1).toFixed(2))); 
						$("#view8 .Number").text("");
						$("#view8 .RefNO").text("");
						$("#view8 .Version").text("");
						$("#view8 .ForwarderOne").text("");
						$("#view8 .ForwarderTwo").text("");
						$("#view8 .ForwarderThree").text("");
						$("#view8 .ForwarderFour").text("");
						$("#view8 .ShipCompany").text("");
						$("#view8 .ShipAddr").text("");
						$("#view8 .ShipTel").text("");
						$("#view8 .ShipAttn").text("");
						$("#view8 .EndCompany").text("");
						$("#view8 .EndAddr").text("");
						$("#view8 .ContactPerson").text("");
						$("#view8 .EndTel").text("");
						$("#view8 .DeliveryTerm").text("");
						$("#view8 .ShippingMark").text("");
						$("#view8 .ContractNO").text("");
						$("#view8 .ShipmentPort").text("");
					}
					if(data[0].part.length < 1){
						$("#view8 .Discounted").text("").text("0.00%");
					}
				},
				error : function() {
					$.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
				}
			});
			$('#view8').show();
	        $('#view89,#view9,#view10').hide();
	        $("#Sidebar_CascadePO").addClass("isHover").siblings().removeClass("isHover");
	        // $(".MailBar_cover_color").css("height",$("#view8").height()+130);
	    }
	    else if(currentId =="Sidebar_CascadePOMachine"){
		    	$('#view89 .yemei').attr("currentID",ID);
		    	var reg = /\.com/;
				 if(UserName.match(reg)){
					 UserName = UserName.substring(0,UserName.indexOf("@"));
				 }
				$("#view89 .Contact").text("").text(UserName); 
				$("#view89 .Email").text("").text(EMAIL);
		    	$(".CascadePOItemTr").remove();
				 $.ajax({
					type : 'get',
					url : "QuoteCascadePO",
					data : {
						quoteID : ID,
						Type:'Complete'
					},
					dataType : 'json',
					success : function(data) {
						console.log(data);
						$(".PartID").text(data[0].PartID);
						
						var itemStr = "";
						if(data[0].part.length >1){
							for (var i = 1; i < data[0].part.length; i++) {
								console.log("inini")
								itemStr += 
									'<tr class="CascadePOItemTr">'
										+ '<td value="'+data[0].part[i].ID+'"><p contenteditable="true" class="Item" >'
										+ i
										+ '</p></td>'
										+ '<td><p contenteditable="true" class="Part">'
										+ data[0].part[i].Model
										+ '</p></td>'
										+ '<td><p contenteditable="true" class="Description">'
										+ data[0].part[i].Description
										+ '</p></td>'
										+ '<td><p contenteditable="true" class="Qty ConfigBlur">'
										+ data[0].part[i].Quantity
										+ '</p></td>'
										+ '<td style="display:none"><p contenteditable="true" class="UnitPrice ConfigBlur">'
										+ fmoney(data[0].part[i].UnitPrice)
										+ '</p></td>'
										+ '<td style="display:none"><p  class="ExtendedPrice">'
										+ fmoney(data[0].part[i].ExtendedPrice)
										+ '</p></td>'
										+ '<td><p><input type="button" value="删除"  class="CascadePOItemBtn"/></p></td>'
										+ '</tr>';
							}
							$("#CascadePOCon  #ItemTit").after(itemStr);
						}
						else{
							$("#CascadePOCon  .CascadePOItemTr").remove();
						}
						//计算PO美元部分的总价
						var totalNum = 0;
						for (var i = 0; i < $("#view89 .CascadePOItemTr").length; i++) {
							totalNum += ($("#view89 .CascadePOItemTr").eq(i).find(".Qty").text())* rmoney(fmoney($("#view89 .CascadePOItemTr").eq(i).find(".UnitPrice").text()));
						}
						totalNum = totalNum*1;

						if(data[0].POInfo.length >1){
							$(".POID2").text(data[0].POInfo[1].ID);
							var tempTotalNum0 = data[0].POInfo[1].SubTotal;
							var totalNum0 = globalDataHandle(tempTotalNum0,0);
							totalNum0 = totalNum0*1;
							if(totalNum0 == 0 || totalNum0 == "0"){
								$("#view89 .SubTotal").text("").text('$'+fmoney((totalNum*1)));
							}else{
								$("#view89 .SubTotal").text("").text("$"+fmoney(totalNum0));
							}
						}else{
							$(".POID2").text(0);
							$("#view89 .SubTotal").text("").text('$'+fmoney(totalNum.toFixed(2)));
						}

						if (data[0].POInfo.length > 1) {
							for (var i = 1; i < data[0].POInfo.length; i++) {
								$("#view89 .Number").text("").text(data[0].POInfo[i].Number);
								$("#view89 .Version").text("").text(data[0].POInfo[i].Version);
								$("#view89 .ForwarderOne").text("").text(data[0].POInfo[i].ForwarderOne);
								$("#view89 .ForwarderTwo").text("").text(data[0].POInfo[i].ForwarderTwo);
								$("#view89 .ForwarderThree").text("").text(data[0].POInfo[i].ForwarderThree);
								$("#view89 .ForwarderFour").text("").text(data[0].POInfo[i].ForwarderFour);
								$("#view89 .ShipCompany").text("").text(data[0].POInfo[i].ShipCompany);
								$("#view89 .ShipAddr").text("").text(data[0].POInfo[i].ShipAddr);
								$("#view89 .ShipTel").text("").text(data[0].POInfo[i].ShipTel);
								$("#view89 .ShipAttn").text("").text(data[0].POInfo[i].ShipAttn);
								$("#view89 .EndCompany").text("").text(data[0].POInfo[i].EndCompany);
								$("#view89 .EndAddr").text("").text(data[0].POInfo[i].EndAddr);
								$("#view89 .ContactPerson").text("").text(data[0].POInfo[i].ContactPerson);
								$("#view89 .EndTel").text("").text(data[0].POInfo[i].EndTel);
								$("#view89 .DeliveryTerm").text("").text(data[0].POInfo[i].DeliveryTerm);
								$("#view89 .ShippingMark").text("").text(data[0].POInfo[i].ShippingMark);
								$("#view89 .ContractNO").text("").text(data[0].POInfo[i].ContractNO);
								$("#view89 .ShipmentPort").text("").text(data[0].POInfo[i].ShipmentPort);
								//$("#view8 .SubTotal").text("").text(fmoney(data[0].POInfo[i].SubTotal));
								var Discounted = data[0].POInfo[i].Discounted;
								var tempTotalNum = data[0].POInfo[i].SubTotal;
								var totalNum2 = globalDataHandle(tempTotalNum,0);
								totalNum2 = totalNum2*1;
								totalNum2 = totalNum2 == 0?totalNum:totalNum2;
								if(Discounted == '--' || Discounted == '' ){
									Discounted = '0.00%';
								}
								$("#view89 .Discounted").text("").text(Discounted);
								if(parseFloat(Discounted) == 0 || parseFloat(Discounted) == ''){
									$("#view89 .FinalTotal").text("").text('$'+fmoney(totalNum2.toFixed(2)));
								}else{
									var FinalTotal = '$'+(fmoney(totalNum2.toFixed(2) * (parseFloat(Discounted) / 100)));
									$("#view89 .FinalTotal").text("").text(FinalTotal);
								}
								
								$("#view89 .yemei").attr("value",data[0].POInfo[i].ID);
							}
						} else {
							$("#view89 .yemei").attr("value", 0);
							$("#view89 .Discounted").text("").text("0.00%");
							$("#view89 .FinalTotal").text("").text('$'+fmoney(totalNum.toFixed(2))); 
							$("#view89 .Number").text("");
							$("#view89 .RefNO").text("");
							$("#view89 .Version").text("");
							$("#view89 .ForwarderOne").text("");
							$("#view89 .ForwarderTwo").text("");
							$("#view89 .ForwarderThree").text("");
							$("#view89 .ForwarderFour").text("");
							$("#view89 .ShipCompany").text("");
							$("#view89 .ShipAddr").text("");
							$("#view89 .ShipTel").text("");
							$("#view89 .ShipAttn").text("");
							$("#view89 .EndCompany").text("");
							$("#view89 .EndAddr").text("");
							$("#view89 .ContactPerson").text("");
							$("#view89 .EndTel").text("");
							$("#view89 .DeliveryTerm").text("");
							$("#view89 .ShippingMark").text("");
							$("#view89 .ContractNO").text("");
							$("#view89 .ShipmentPort").text("");
						}
						if(data[0].part.length < 1){
							$("#view89 .Discounted").text("").text("0.00%");
						}
					},
					error : function() {
						$.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
					}
				});
				$('#view89').show();
		        $('#view8,#view9,#view10').hide();
		        $("#Sidebar_CascadePOMachine").addClass("isHover").siblings().removeClass("isHover");
		        // $(".MailBar_cover_color").css("height",$("#view8").height()+130);
	    }
	    else if(currentId =="Sidebar_OtherPO"){
			$('#view9 .yemei').attr("currentID", ID);
			//显示用户名和密码
			var reg = /\.com/;
			 if(UserName.match(reg)){
				 UserName = UserName.substring(0,UserName.indexOf("@"));
			 }
			 $("#view9 .Contact").text("").text(UserName); 
			 $("#view9 .Email").text("").text(EMAIL); 
			$(".OtherPOItemTr").remove();
			$.ajax({
				type : 'get',
				url : "QuoteOtherPO",
				data : {
					quoteID : ID,
				},
				dataType : 'json',
				success : function(data) {
					console.log(data);
					var itemStr = "";
					if(data[0].part.length >1){
						for (var i = 1; i < data[0].part.length; i++) {
							itemStr += '<tr class="OtherPOItemTr">'
									+ '<td value="'+data[0].part[i].ID+'"><p contenteditable="true" class="Item" >'
									+ i
									+ '</p></td>'
									+ '<td><p contenteditable="true" class="Part">'
									+ data[0].part[i].Model
									+ '</p></td>'
									+ '<td><p contenteditable="true" class="Description">'
									+ data[0].part[i].Description
									+ '</p></td>'
									+ '<td><p contenteditable="true" class="Qty ConfigBlur">'
									+ data[0].part[i].Quantity
									+ '</p></td>'
									+ '<td><p contenteditable="true" class="UnitPrice">'
									+ fmoney(data[0].part[i].UnitPrice)
									+ '</p></td>'
									+ '<td><p  class="ExtendedPrice">'
									+ fmoney(data[0].part[i].ExtendedPrice)
									+ '</p></td>'
									+ '<td><p><input type="button" value="删除"  class="OtherPOItemBtn"/></p></td>'
									+ '</tr>';
						}
						$("#OtherPOCon  #ItemTit").after(itemStr);
					}
					else{
						$("#OtherPOCon  .OtherPOItemTr").remove()
					}
					//计算合同美元部分的总价
					var totalNum = 0;
					for (var i = 0; i < $("#view9 .OtherPOItemTr").length; i++) {
						totalNum += ($("#view9 .OtherPOItemTr").eq(i).find(".Qty").text())* rmoney(fmoney($("#view9 .OtherPOItemTr").eq(i).find(".UnitPrice").text()));
					}
					totalNum = totalNum*1;
					if(data[0].POInfo.length >1){
						var tempTotalNum0 = data[0].POInfo[1].SubTotal;
						var totalNum0 = globalDataHandle(tempTotalNum0,0);
						totalNum0 = totalNum0*1;
						if(totalNum0 == 0 || totalNum0 == "0"){
							$("#view9 .SubTotal").text("").text('$'+fmoney((totalNum*1)));
						}else{
							$("#view9 .SubTotal").text("").text("$"+fmoney(totalNum0));
						}
					}else{
						$("#view9 .SubTotal").text("").text("$"+fmoney(totalNum.toFixed(2)));
					}

					if (data[0].POInfo.length > 1) {
						for (var i = 1; i < data[0].POInfo.length; i++) {
							$("#view9 .Number").text("").text(data[0].POInfo[i].Number);
							$("#view9 .Version").text("").text(data[0].POInfo[i].Version);
							$("#view9 .ForwarderOne").text("").text(data[0].POInfo[i].ForwarderOne);
							$("#view9 .ForwarderTwo").text("").text(data[0].POInfo[i].ForwarderTwo);
							$("#view9 .ForwarderThree").text("").text(data[0].POInfo[i].ForwarderThree);
							$("#view9 .ForwarderFour").text("").text(data[0].POInfo[i].ForwarderFour);
							$("#view9 .ShipCompany").text("").text(data[0].POInfo[i].ShipCompany);
							$("#view9 .ShipAddr").text("").text(data[0].POInfo[i].ShipAddr);
							$("#view9 .ShipTel").text("").text(data[0].POInfo[i].ShipTel);
							$("#view9 .ShipAttn").text("").text(data[0].POInfo[i].ShipAttn);
							$("#view9 .VendorOne").text("").text(data[0].POInfo[i].VendorOne);
							$("#view9 .VendorTwo").text("").text(data[0].POInfo[i].VendorTwo);
							$("#view9 .VendorThree").text("").text(data[0].POInfo[i].VendorThree);
							$("#view9 .VendorFour").text("").text(data[0].POInfo[i].VendorFour);
							$("#view9 .CreditTerm").text("").text(data[0].POInfo[i].CreditTerm);
							$("#view9 .DeliveryTerm").text("").text(data[0].POInfo[i].DeliveryTerm);
							$("#view9 .ShippingMark").text("").text(data[0].POInfo[i].ShippingMark);
							$("#view9 .ContractNO").text("").text(data[0].POInfo[i].ContractNO);
							$("#view9 .ShipmentPort").text("").text(data[0].POInfo[i].ShipmentPort);
							//$("#view9 .SubTotal").text("").text(fmoney(data[0].POInfo[i].SubTotal));
							
							var Discounted = data[0].POInfo[i].Discounted;
							if(Discounted == '--' || Discounted == '' ){
									Discounted = '0.00%';
								}
							$("#view9 .Discounted").text("").text(Discounted);
							var tempTotalNum = data[0].POInfo[i].SubTotal;
							var totalNum2 = globalDataHandle(tempTotalNum,0);
							totalNum2 = totalNum2*1;
							totalNum2 = totalNum2 == 0?totalNum:totalNum2;
							if(parseFloat(Discounted) == 0 || parseFloat(Discounted) == ''){
								$("#view9 .FinalTotal").text("").text("$"+fmoney(totalNum2.toFixed(2)));
							}else{
								$("#view9 .FinalTotal").text("").text("$"+fmoney(totalNum2.toFixed(2) * (parseFloat(Discounted) / 100)));
							}
							$("#view9 .yemei").attr("value",data[0].POInfo[i].ID);
							console.log("an"+ parseFloat(data[0].POInfo[i].Discounted));
						}
					} else {
						$("#view9 .yemei").attr("value", 0);
						$("#view9 .Discounted").text("").text("0.00%");
						$("#view9 .FinalTotal").text("").text("$"+fmoney(totalNum.toFixed(2)));
						
						$("#view9 .Number").text("");
						$("#view9 .Version").text("");
						$("#view9 .ForwarderOne").text("");
						$("#view9 .ForwarderTwo").text("");
						$("#view9 .ForwarderThree").text("");
						$("#view9 .ForwarderFour").text("");
						$("#view9 .ShipCompany").text("");
						$("#view9 .ShipAddr").text("");
						$("#view9 .ShipTel").text("");
						$("#view9 .ShipAttn").text("");
						$("#view9 .VendorOne").text("");
						$("#view9 .VendorTwo").text("");
						$("#view9 .VendorThree").text("");
						$("#view9 .VendorFour").text("");
						$("#view9 .CreditTerm").text("");
						$("#view9 .DeliveryTerm").text("");
						$("#view9 .ShippingMark").text("");
						$("#view9 .ContractNO").text("");
						$("#view9 .ShipmentPort").text("");
					}
				},
				error : function() {
					$.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
				}
			});
			$('#view9').show();
	        $('#view8,#view89,#view10').hide();
	        $("#Sidebar_OtherPO").addClass("isHover").siblings().removeClass("isHover");
	        // $(".MailBar_cover_color").css("height",$("#view9").height()+130);
	    }
	    else{
			 $('#view10 .yemei').attr("currentID",ID);
			//显示用户名和密码
			 var reg = /\.com/;
			 if(UserName.match(reg)){
				 UserName = UserName.substring(0,UserName.indexOf("@"));
			 }
			 $("#view10 .Contact").text("").text(UserName); 
			 $("#view10 .Email").text("").text(EMAIL); 
			$(".OtherPOItemTr").remove();
			//获取当前行的tr 的索引值
			$.ajax({
				type : 'get',
				url : "QuoteOtherRMBPO",
				data : {
					QuoteID : ID,
				},
				dataType : 'json',
				success : function(data) {
					console.log(data);
					var itemStr = "";
					if(data[0].part.length >1){
						for (var i = 1; i < data[0].part.length; i++) {
							data[0].part[i].ExtendedPrice.indexOf(",")>0?(data[0].part[i].ExtendedPrice = data[0].part[i].ExtendedPrice.replace(/,/g,"")):(data[0].part[i].ExtendedPrice = data[0].part[i].ExtendedPrice);
							data[0].part[i].UnitPrice.indexOf(",")>0?(data[0].part[i].UnitPrice = data[0].part[i].UnitPrice.replace(/,/g,"")):(data[0].part[i].UnitPrice = data[0].part[i].UnitPrice);
							itemStr += '<tr class="OtherPOItemTr">'+ 
								'<td value="'+data[0].part[i].ID+'"><p contenteditable="true" class="Item" >'+ i+'</p></td>'+ 
								'<td><p contenteditable="true" class="Part">'+ data[0].part[i].Model+ '</p></td>'+ 
								'<td><p contenteditable="true" class="Description">'+ data[0].part[i].Description+'</p></td>'+ 
								'<td><p contenteditable="true" class="Qty ConfigBlur">'+ data[0].part[i].Quantity+'</p></td>'+ 
								'<td><p contenteditable="true" class="UnitPrice">'+ data[0].part[i].UnitPrice+'</p></td>'+ 
								'<td><p  class="ExtendedPrice">'+ data[0].part[i].ExtendedPrice +'</p></td>'+
								'<td><p><input type="button" value="删除"  class="OtherPOItemBtn"/></p></td>'+ 
								'</tr>';
						}
						$("#OtherPORMB  #ItemTit").after(itemStr);
					}
					else{
						$("#OtherPORMB  .OtherPOItemTr").remove();
					}
					var totalNum = 0;
					for (var i = 0; i < $("#view10 .OtherPOItemTr").length; i++) {
						totalNum += ($("#view10 .OtherPOItemTr").eq(i).find(".Qty").text())* rmoney(fmoney($("#view10 .OtherPOItemTr").eq(i).find(".UnitPrice").text()));
					}
					totalNum = totalNum*1;
					if(data[0].POInfo.length >1){
						var tempTotalNum0 = data[0].POInfo[1].SubTotal;
						var totalNum0 = globalDataHandle(tempTotalNum0,0);
						totalNum0 = totalNum0*1;
						if(totalNum0 == 0 || totalNum0 == "0"){
							$("#view10 .SubTotal").text("").text('￥'+fmoney((totalNum*1)));
						}else{
							$("#view10 .SubTotal").text("").text("￥"+fmoney(totalNum0));
						}
					}else{
						$("#view10 .SubTotal").text("").text("￥"+fmoney(totalNum.toFixed(2)));
					}

					if (data[0].POInfo.length > 1) {
						for (var i = 1; i < data[0].POInfo.length; i++) {
							$("#view10 .BillContact").text("").text(data[0].POInfo[i].BillContact);
							$("#view10 .BillEmail").text("").text(data[0].POInfo[i].BillEmail);
							
							$("#view10 .Number").text("").text(data[0].POInfo[i].Number);
							$("#view10 .Version").text("").text(data[0].POInfo[i].Version);
							$("#view10 .ForwarderOne").text("").text(data[0].POInfo[i].ForwarderOne);
							$("#view10 .ForwarderTwo").text("").text(data[0].POInfo[i].ForwarderTwo);
							$("#view10 .ForwarderThree").text("").text(data[0].POInfo[i].ForwarderThree);
							$("#view10 .ForwarderFour").text("").text(data[0].POInfo[i].ForwarderFour);

							$("#view10 .ShipTelContent").text("").text(data[0].POInfo[i].ShipTel);
						    $("#view10 .ShipAttnContact").text("").text(data[0].POInfo[i].ShipAttn);
							$("#view10 .VendorOne").text("").text(data[0].POInfo[i].VendorOne);
							$("#view10 .VendorTwo").text("").text(data[0].POInfo[i].VendorTwo);
							$("#view10 .VendorThree").text("").text(data[0].POInfo[i].VendorThree);
							$("#view10 .VendorFour").text("").text(data[0].POInfo[i].VendorFour);
							$("#view10 .VendorFive").text("").text(data[0].POInfo[i].VendorFive);
							$("#view10 .CreditTerm").text("").text(data[0].POInfo[i].CreditTerm);
							$("#view10 .DeliveryTerm").text("").text(data[0].POInfo[i].DeliveryTerm);
							$("#view10 .ShippingMark").text("").text(data[0].POInfo[i].ShippingMark);
							$("#view10 .ContractNO").text("").text(data[0].POInfo[i].ContractNO);
							$("#view10 .ShipmentPort").text("").text(data[0].POInfo[i].ShipmentPort);
								
								var Discounted = data[0].POInfo[i].Discounted;
								if(Discounted == '--' || Discounted == '' ){
										Discounted = '0.00%';
									}
								$("#view10 .Discounted").text("").text(Discounted);
								var tempTotalNum = data[0].POInfo[i].SubTotal;
								var totalNum2 = globalDataHandle(tempTotalNum,0);
								totalNum2 = totalNum2*1;
								if(parseFloat(Discounted) == 0 || parseFloat(Discounted) == ''){
									if(totalNum2 == 0){
										$("#view10 .FinalTotal").text("").text("￥"+fmoney(totalNum.toFixed(2)));
									}else{
										$("#view10 .FinalTotal").text("").text("￥"+fmoney(totalNum2.toFixed(2)));
									}
								}else{
									if(totalNum2 == 0){
										$("#view10 .FinalTotal").text("").text("￥"+fmoney(totalNum.toFixed(2) * (parseFloat(Discounted) / 100)));
									}else{
										$("#view10 .FinalTotal").text("").text("￥"+fmoney(totalNum2.toFixed(2) * (parseFloat(Discounted) / 100)));
									}
								}
								
							$("#view10 .yemei").attr("value",data[0].POInfo[i].ID);
							console.log("an"+ parseFloat(data[0].POInfo[i].Discounted));
						}
					} else {
						$("#view10 .yemei").attr("value", 0);
						$("#view10 .Discounted").text("").text("0.00%");
						$("#view10 .FinalTotal").text("").text("￥"+fmoney(totalNum.toFixed(2)));
						$("#view10 .Number").text("");
						$("#view10 .Version").text("");
						$("#view10 .ForwarderOne").text("");
						$("#view10 .ForwarderTwo").text("");
						$("#view10 .ForwarderThree").text("");
						$("#view10 .ForwarderFour").text("");

						$("#view10 .ShipTelContent").text("");
						$("#view10 .ShipAttnContact").text("");
						$("#view10 .VendorOne").text("");
						$("#view10 .VendorTwo").text("");
						$("#view10 .VendorThree").text("");
						$("#view10 .VendorFour").text("");
						$("#view10 .VendorFive").text("");
						$("#view10 .CreditTerm").text("");
						$("#view10 .DeliveryTerm").text("");
						$("#view10 .ShippingMark").text("");
						$("#view10 .ContractNO").text("");
						$("#view10 .ShipmentPort").text("");
					}
				},
				error : function() {
					$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
				}
			});
			$('#view10').show();
	        $('#view8,#view89,#view9').hide();
	        $("#Sidebar_OtherPORMB").addClass("isHover").siblings().removeClass("isHover");
	        // $(".MailBar_cover_color").css("height",$("#view10").height()+130);
	    }
	})
     //PO      切换模板     提交
	//CascadePO_submit（点击提交时三种情况if判断了）：*************************需要增加参数POID传过去***********************￥￥￥￥￥￥￥￥￥￥￥￥￥￥
		$('#CascadePO_submit').click(function() {
			 $("#CascadePO_submit").attr("disabled",false);
				$("#CascadePO_submit").css({
					"background":"#dddddd",
					"color":"#808080",
					"border":"none",
					"box-shadow":"0 0 0 0 #f8fcfd"
				});
				var CompleteID = $(".CompleteID").text();
				var PartID = $(".PartID").text()
			var submitView = $(".CascadePOPdf .isHover").attr("ID");
			if(submitView == "Sidebar_CascadePO"){
				var Number = $('#view8 .Number').text();
				var RefNO = $('#view8 .RefNO').text();
				var Version = $('#view8 .Version').text();
				var ForwarderOne = $('#view8 .ForwarderOne').text();
				var ForwarderTwo = $('#view8 .ForwarderTwo').text();
				var ForwarderThree = $('#view8 .ForwarderThree').text();
				var ForwarderFour = $('#view8 .ForwarderFour').text();
				var ShipCompany = $('#view8 .ShipCompany').text();
				var ShipAddr = $('#view8 .ShipAddr').text();
				var ShipTel = $('#view8 .ShipTel').text();
				var ShipAttn = $('#view8 .ShipAttn').text();
				var EndCompany = $('#view8 .EndCompany').text();
				var EndAddr = $('#view8 .EndAddr').text();
				var ContactPerson = $('#view8 .ContactPerson').text();
				var EndTel = $('#view8 .EndTel').text();
				var DeliveryTerm = $('#view8 .DeliveryTerm').text();
				var ShippingMark = $('#view8 .ShippingMark').text();
				var ContractNO = $('#view8 .ContractNO').text();
				var ShipmentPort = $('#view8 .ShipmentPort').text();
				var Contact = $('#view8 .Contact').text();
				var Email = $('#view8 .Email').text();
				var SubTotal = $('#view8 .SubTotal').text();
				SubTotal = SubTotal.substring(1);
				var SubTotal = rmoney(SubTotal);
				var Discounted = parseFloat($('#view8 .Discounted').text())/100;
				var FinalTotal = $('#view8 .FinalTotal').text();
				FinalTotal = FinalTotal.substring(1);
				var FinalTotal = rmoney($('#view8 .FinalTotal').text());
				//对序号进行判断
				if ($("#view8 .yemei").attr("value")) {
					var ID = $("#view8 .yemei").attr("value");
				} else {
					var ID = 0;
				}
				var QuoteID= $('#view8 .yemei').attr("currentID");

				var Exist;
				var POID = [];
				if ($('#view8 .CascadePOItemTr').length > 0) {
					Exist = "yes";
					for (var i = 0; i < $('#view8 .CascadePOItemTr').length; i++) {
						POID.push($('#view8 .CascadePOItemTr').eq(i).find("td").eq(0).attr("value"));
					}
				} else {
					Exist = "no";
				}
				$.ajax({
					type : 'get',
					url : "QuoteCascadeOperate",
					data : {
						Type:'Parts',
						ID : ID,
						Number : Number,
						RefNO:RefNO,
						Version : Version,
						ForwarderOne : ForwarderOne,
						ForwarderTwo : ForwarderTwo,
						ForwarderThree : ForwarderThree,
						ForwarderFour : ForwarderFour,
						ShipCompany : ShipCompany,
						ShipAddr : ShipAddr,
						ShipTel : ShipTel,
						ShipAttn : ShipAttn,
						EndCompany : EndCompany,
						EndAddr : EndAddr,
						EndTel : EndTel,
						ContactPerson : ContactPerson,
						DeliveryTerm : DeliveryTerm,
						ShippingMark : ShippingMark,
						ContractNO : ContractNO,
						ShipmentPort : ShipmentPort,
						Contact : Contact,
						Email : Email,
						SubTotal : SubTotal,
						FinalTotal : FinalTotal,
						POID : POID,
						Exist : Exist,
						QuoteID : QuoteID,
						Discounted:Discounted,
						CompleteID:CompleteID
					},
					dataType : 'json',
					success : function(data) {
						console.log(data);
						$.MsgBox.Alert("提示", "提交成功！");
					},
					error : function() {
						$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
					}
				});
			}
			else if(submitView == "Sidebar_CascadePOMachine"){
				var Number = $('#view89 .Number').text();
				var RefNO = $('#view89 .RefNO').text();
				var Version = $('#view89 .Version').text();
				var ForwarderOne = $('#view89 .ForwarderOne').text();
				var ForwarderTwo = $('#view89 .ForwarderTwo').text();
				var ForwarderThree = $('#view89 .ForwarderThree').text();
				var ForwarderFour = $('#view89 .ForwarderFour').text();
				var ShipCompany = $('#view89 .ShipCompany').text();
				var ShipAddr = $('#view89 .ShipAddr').text();
				var ShipTel = $('#view89 .ShipTel').text();
				var ShipAttn = $('#view89 .ShipAttn').text();
				var EndCompany = $('#view89 .EndCompany').text();
				var EndAddr = $('#view89 .EndAddr').text();
				var ContactPerson = $('#view89 .ContactPerson').text();
				var EndTel = $('#view89 .EndTel').text();
				var DeliveryTerm = $('#view89 .DeliveryTerm').text();
				var ShippingMark = $('#view89 .ShippingMark').text();
				var ContractNO = $('#view89 .ContractNO').text();
				var ShipmentPort = $('#view89 .ShipmentPort').text();
				var Contact = $('#view89 .Contact').text();
				var Email = $('#view89 .Email').text();
				var SubTotal = $('#view89 .SubTotal').text();
				SubTotal = SubTotal.substring(1);
				var SubTotal = rmoney(SubTotal);
				var Discounted = parseFloat($('#view89 .Discounted').text())/100;
				var FinalTotal = $('#view89 .FinalTotal').text();
				FinalTotal = FinalTotal.substring(1);
				var FinalTotal = rmoney(FinalTotal);
				//对序号进行判断
				if ($("#view89 .yemei").attr("value")) {
					var ID = $("#view89 .yemei").attr("value");
				} else {
					var ID = 0;
				}
				var QuoteID= $('#view89 .yemei').attr("currentID");

				var Exist;
				var POID = [];
				if ($('#view89 .CascadePOItemTr').length > 0) {
					Exist = "yes";
					for (var i = 0; i < $('#view89 .CascadePOItemTr').length; i++) {
						POID.push($('#view89 .CascadePOItemTr').eq(i).find("td").eq(0).attr("value"));
					}
				} else {
					Exist = "no";
				}
				$.ajax({
					type : 'get',
					url : "QuoteCascadeOperate",
					data : {
						Type:'Complete',
						ID : ID,
						Number : Number,
						RefNO:RefNO,
						Version : Version,
						ForwarderOne : ForwarderOne,
						ForwarderTwo : ForwarderTwo,
						ForwarderThree : ForwarderThree,
						ForwarderFour : ForwarderFour,
						ShipCompany : ShipCompany,
						ShipAddr : ShipAddr,
						ShipTel : ShipTel,
						ShipAttn : ShipAttn,
						EndCompany : EndCompany,
						EndAddr : EndAddr,
						EndTel : EndTel,
						ContactPerson : ContactPerson,
						DeliveryTerm : DeliveryTerm,
						ShippingMark : ShippingMark,
						ContractNO : ContractNO,
						ShipmentPort : ShipmentPort,
						Contact : Contact,
						Email : Email,
						SubTotal : SubTotal,
						FinalTotal : FinalTotal,
						POID : POID,
						Exist : Exist,
						QuoteID : QuoteID,
						Discounted:Discounted,
						PartID:PartID
					},
					dataType : 'json',
					success : function(data) {
						console.log(data);
						$.MsgBox.Alert("提示", "提交成功！");
					},
					error : function() {
						$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
					}
				});
			}
			else if(submitView == "Sidebar_OtherPO"){
				var Number = $('#view9 .Number').text();
				var Version = $('#view9 .Version').text();
				var ForwarderOne = $('#view9 .ForwarderOne').text();
				var ForwarderTwo = $('#view9 .ForwarderTwo').text();
				var ForwarderThree = $('#view9 .ForwarderThree').text();
				var ForwarderFour = $('#view9 .ForwarderFour').text();
				var ShipCompany = $('#view9 .ShipCompany').text();
				var ShipAddr = $('#view9 .ShipAddr').text();
				var ShipTel = $('#view9 .ShipTel').text();
				var ShipAttn = $('#view9 .ShipAttn').text();
				var VendorOne = $('#view9 .VendorOne').text();
				var VendorTwo = $('#view9 .VendorTwo').text();
				var VendorThree = $('#view9 .VendorThree').text();
				var VendorFour = $('#view9 .VendorFour').text();
				var CreditTerm = $('#view9 .CreditTerm').text();
				var DeliveryTerm = $('#view9 .DeliveryTerm').text();
				var ShippingMark = $('#view9 .ShippingMark').text();
				var ContractNO = $('#view9 .ContractNO').text();
				var ShipmentPort = $('#view9 .ShipmentPort').text();
				var Contact = $('#view9 .Contact').text();
				var Email = $('#view9 .Email').text();
				var SubTotal = rmoney($('#view9 .SubTotal').text());
				var Discounted = parseFloat($('#view9 .Discounted').text())/100;
				var FinalTotal = rmoney($('#view9 .FinalTotal').text());
				//对序号进行判断
				if ($("#view9 .yemei").attr("value")) {
					var ID = $("#view9 .yemei").attr("value");
				} else {
					var ID = 0;
				}
				var QuoteID= $('#view9 .yemei').attr("currentID");
				var Exist;
				var POID = [];
				if ($('#view9 .OtherPOItemTr').length > 0) {
					Exist = "yes";
					for (var i = 0; i < $('#view9 .OtherPOItemTr').length; i++) {
						POID.push($('#view9 .OtherPOItemTr').eq(i).find("td").eq(0).attr("value"));
					}
				} else {
					Exist = "no";
				}
				$.ajax({
					type : 'get',
					url : "QuoteOtherOperate",
					data : {
						ID : ID,
						Number : Number,
						Version : Version,
						ForwarderOne : ForwarderOne,
						ForwarderTwo : ForwarderTwo,
						ForwarderThree : ForwarderThree,
						ForwarderFour : ForwarderFour,
						ShipCompany : ShipCompany,
						ShipAddr : ShipAddr,
						ShipTel : ShipTel,
						ShipAttn : ShipAttn,
						VendorOne : VendorOne,
						VendorTwo : VendorTwo,
						VendorThree : VendorThree,
						VendorFour:VendorFour,
						CreditTerm : CreditTerm,
						DeliveryTerm : DeliveryTerm,
						ShippingMark : ShippingMark,
						ContractNO : ContractNO,
						ShipmentPort : ShipmentPort,
						Contact : Contact,
						Email : Email,
						SubTotal : SubTotal,
						Discounted : Discounted,
						FinalTotal : FinalTotal,
						POID : POID,
						Exist : Exist,
						QuoteID : QuoteID
					},
					dataType : 'json',
					success : function(data) {
						console.log(data);
						$.MsgBox.Alert("提示", "提交成功！");
					},
					error : function() {
						$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
					}
				});
			}
			else if(submitView == "Sidebar_OtherPORMB"){
				var Number = $('#view10 .Number').text();
				var Version = $('#view10 .Version').text();
				var ForwarderOne = $('#view10 .ForwarderOne').text();
				var ForwarderTwo = $('#view10 .ForwarderTwo').text();
				var ForwarderThree = $('#view10 .ForwarderThree').text();
				var ForwarderFour = $('#view10 .ForwarderFour').text();
				var BillContact = $('#view10 .BillContact').text();
				var BillEmail = $('#view10 .BillEmail').text();
				var ShipTel = $('#view10 .ShipTelContent').text();
				var ShipAttn = $('#view10 .ShipAttnContact').text();
				var VendorOne = $('#view10 .VendorOne').text();
				var VendorTwo = $('#view10 .VendorTwo').text();
				var VendorThree = $('#view10 .VendorThree').text();
				var VendorFour = $('#view10 .VendorFour').text();
				var VendorFive = $('#view10 .VendorFive').text();
				var CreditTerm = $('#view10 .CreditTerm').text();
				var DeliveryTerm = $('#view10 .DeliveryTerm').text();
				var ShippingMark = $('#view10 .ShippingMark').text();
				var ContractNO = $('#view10 .ContractNO').text();
				var ShipmentPort = $('#view10 .ShipmentPort').text();
				var Contact = $('#view10 .Contact').text();
				var Email = $('#view10 .Email').text();
				var SubTotal = rmoney($('#view10 .SubTotal').text());
				var Discounted = parseFloat($('#view10 .Discounted').text())/100;
				var FinalTotal = rmoney($('#view10 .FinalTotal').text());
				
				var POID = [];
				if ($('#view10 .OtherPOItemTr').length > 0) {
					Exist = "yes";
					for (var i = 0; i < $('#view10 .OtherPOItemTr').length; i++) {
						POID.push($('#view10 .OtherPOItemTr').eq(i).find("td").eq(0).attr("value"));
					}
				} else {
					Exist = "no";
				}
				
				//对序号进行判断
				if ($("#view10 .yemei").attr("value")) {
					var ID = $("#view10 .yemei").attr("value");
				} else {
					var ID = 0;
				}
				var QuoteID = $("#view10 .yemei").attr("currentID");
				$.ajax({
					type : 'get',
					url : "QuoteOtherRMBOperate",
					data : {
						ID : ID,
						Number : Number,
						Version : Version,
						ForwarderOne : ForwarderOne,
						ForwarderTwo : ForwarderTwo,
						ForwarderThree : ForwarderThree,
						ForwarderFour : ForwarderFour,/*
						ShipCompany : ShipCompany,
						ShipAddr : ShipAddr,
						ShipTel : ShipTel,
						ShipAttn : ShipAttn,*/
						BillContact:BillContact,
						BillEmail:BillEmail,
						ShipTel:ShipTel,
						ShipAttn:ShipAttn,
						VendorOne : VendorOne,
						VendorTwo : VendorTwo,
						VendorThree : VendorThree,
						VendorFour:VendorFour,
						VendorFive:VendorFive,
						CreditTerm : CreditTerm,
						DeliveryTerm : DeliveryTerm,
						ShippingMark : ShippingMark,
						ContractNO : ContractNO,
						ShipmentPort : ShipmentPort,
						Contact : Contact,
						Email : Email,
						SubTotal : SubTotal,
						Discounted : Discounted,
						FinalTotal : FinalTotal,
						QuoteID : QuoteID,
						POID:POID
					},
					dataType : 'json',
					success : function(data) {
						console.log(data);
						$.MsgBox.Alert("提示", "提交成功！");
					},
					error : function() {
						$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
					}
				});
			}
		})
		
			var Tnormal = '<div class="Toman1 newToman">'+
					'<span class="Toman">收件人</span>'+
					' <input type="text" name="ToEmail" value="SalesOps-BV@formfactor.com" class="ToE_inp">'+
					'<span class="To_add"><i class="fa fa-plus-square" id="fa-button3"></i></span>'+
					'<span class= "To_del" style="margin-left:3px;" ><i class="fa fa-close" id="fa-button3"></i></span>'+
				'</div>';
			var Cnormal =	'<div class="Copyman1 newCopyman">'+
				' <span class="Copyman">抄送人</span>'+
				 '<input type="text" name="CopyTo" value="AChen3@formfactor.com" class="CopyE_inp">'+
			'</div>'+
			'<div class="Copyman2 newCopyman">'+
				' <span class="Copyman">抄送人1</span>'+
				 '<input type="text" name="CopyTo" value="Wilson.wu@formfactor.com" class="CopyE_inp">'+
				' <span class= "Copy_del" style="margin-left:3px;" ><i class="fa fa-close" id="fa-button3"></i></span>'+
			'</div>'+
			'<div class="Copyman3 newCopyman">'+
				 '<span class="Copyman">抄送人2</span>'+
				 '<input type="text" name="CopyTo" value="jiangyaping@eoulu.com" class="CopyE_inp">'+
				 '<span class= "Copy_del" style="margin-left:3px;" ><i class="fa fa-close" id="fa-button3"></i></span>'+
			'</div>'+
			'<div class="Copyman4 newCopyman">'+
				' <span class="Copyman">抄送人3</span>'+
				' <input type="text" name="CopyTo" value="zhaona@eoulu.com" class="CopyE_inp">'+
				' <span class= "Copy_del" style="margin-left:3px;" ><i class="fa fa-close" id="fa-button3"></i></span>'+
			'</div>'+
			'<div class="Copyman5 newCopyman">'+
				' <span class="Copyman">抄送人4</span>'+
				' <input type="text" name="CopyTo" value="zhaowenzhen@eoulu.com" class="CopyE_inp">'+
				'<span class="Copy_add"><i class="fa fa-plus-square" id="fa-button3"></i></span>'+
				' <span class= "Copy_del" style="margin-left:3px;" ><i class="fa fa-close" id="fa-button3"></i></span>'+
			'</div>';
		//PO  发送  整合
	$('#PO_Send').click(function() {
		var submitView = $(".CascadePOPdf .isHover").attr("ID");
		if(submitView == "Sidebar_CascadePO"){
			var POID = $(".POID1").text();
			var CascadePOConNumber = $("#view8 .CascadePOConNumber").text();
			$(".table_title").attr("value",CascadePOConNumber);
			$.ajax({
				type:"POST",
				url:"SendCascadeEmail",
				dataType:"JSON",
				data:{
					POID:POID
				},
				success:function(data){
					console.log(data.length)
					if(data.length <= 1){

						var submitView = $(".CascadePOPdf .isHover").attr("ID");
						$(".contract_sendPO1 #ToEmail").text(" ");
						$(".contract_sendPO1 #CopyTo").text(" ");
						$(".contract_sendPO1 .newCopyman").remove();
						$(".contract_sendPO1 .newToman").remove();
						
							$(".Tolist").append(Tnormal);$(".Copylist").append(Cnormal);
						
							$(".contract_sendPO1 .contract_title").attr("flag","Sidebar_CascadePO");
							var EndCompany =$("#view8 .EndCompany").text();
							if(!EndCompany){EndCompany = '无'}
							var CascadePOConNumber = $("#view8 .CascadePOConNumber").text();
							if(!CascadePOConNumber){CascadePOConNumber = '无'}
							$(".contract_sendPO1 #Theme").text('').text('Eoulu：New PO of '+EndCompany+' '+CascadePOConNumber);
							$(".cover_color").css("height",$("#view8").height()+130);
							$(".cover_color").show();
							$(".contract_sendPO1").show();
						
						
					}else if(data.length > 1){
						$(".ID1").text(data[1].ID);
						
						//收件人抄送人邮件内容不为空根据内容回显
						var submitView = $(".CascadePOPdf .isHover").attr("ID");
						$(".contract_sendPO1 #ToEmail").text(" ");
						$(".contract_sendPO1 #CopyTo").text(" ");
						
						$(".contract_sendPO1 .newCopyman").remove();
						$(".contract_sendPO1 .newToman").remove();
						
							$(".contract_sendPO1 .contract_title").attr("flag","Sidebar_CascadePO");
							console.log(data[1].Content)
							var Content = data[1].Content1;
							/*var Content = data[1].Content;*/
							$(".contract_sendPO1 #NickName").val('').val(Content);
							//——————————————————收件人——————————————————
							var Recepter = data[1].Recepter;
							console.log(Recepter);
							var Recepter_Arr = Recepter.split(';');
							console.log(Recepter_Arr)
							if(Recepter_Arr.length <= 2){
								var str2 = '<div class="Toman'+(0)+' newToman">'+
									'<span class="Toman">收件人</span>'+
									'<input type="text" name="ToEmail" value="'+Recepter_Arr[0]+'" class="ToE_inp">'+
									'<span class="To_add"><i class="fa fa-plus-square" id="fa-button3"></i></span>'+
								'</div>';
								$(".Tolist").append(str2);
							}else{
								for(var i = 0;i<Recepter_Arr.length -1;i++){
									
									if(i == (Recepter_Arr.length-2)&& i != 0){
										var str2 = '<div class="Toman'+(i)+' newToman">'+
											'<span class="Toman">收件人'+(i)+'</span>'+
											'<input type="text" name="ToEmail" value="'+Recepter_Arr[i]+'" class="ToE_inp">'+
											'<span class="To_add"><i class="fa fa-plus-square" id="fa-button3"></i></span>'+
											'<span class= "To_del" style="margin-left:3px;" ><i class="fa fa-close" id="fa-button3"></i></span>'+
										'</div>';
									}else if(i == 0){
										var str2 = '<div class="Toman'+(i)+' newToman">'+
											'<span class="Toman">收件人</span>'+
											'<input type="text" name="ToEmail" value="'+Recepter_Arr[i]+'" class="ToE_inp">'+
										'</div>';
									}
									else{
										var str2 = '<div class="Toman'+(i)+' newToman">'+
											'<span class="Toman">收件人'+(i)+'</span>'+
											'<input type="text" name="ToEmail" value="'+Recepter_Arr[i]+'" class="ToE_inp">'+
											'<span class= "To_del" style="margin-left:3px;" ><i class="fa fa-close" id="fa-button3"></i></span>'+
										'</div>';
									}
									$(".Tolist").append(str2);
								}		
							}
							
							//——————————————————抄送人——————————————————
							var CopyList = data[1].CopyList;

							var CopyList_Arr = CopyList.split(';');
							console.log(CopyList_Arr.length);
							if(CopyList_Arr.length <= 2){
								var str1 = '<div class="Copyman'+(0)+' newCopyman">'+
									'<span class="Copyman">抄送人</span>'+
									'<input type="text" name="CopyTo" value="'+CopyList_Arr[0]+'" class="CopyE_inp">'+
									'<span class="Copy_add"><i class="fa fa-plus-square" id="fa-button3"></i></span>'+
								'</div>';
								$(".Copylist").append(str1);
							}else{
								for(var i = 0;i<CopyList_Arr.length-1;i++){
									if(i == (CopyList_Arr.length-2) && i != 0){
										var str1 = '<div class="Copyman'+(i)+' newCopyman">'+
											'<span class="Copyman">抄送人'+(i)+'</span>'+
											'<input type="text" name="CopyTo" value="'+CopyList_Arr[i]+'" class="CopyE_inp">'+
											'<span class="Copy_add"><i class="fa fa-plus-square" id="fa-button3"></i></span>'+
											'<span class= "Copy_del" style="margin-left:3px;" ><i class="fa fa-close" id="fa-button3"></i></span>'+
										'</div>';
									}
									else if(i == 0){
											var str1 = '<div class="Copyman'+(i)+' newCopyman">'+
											'<span class="Copyman">抄送人</span>'+
											'<input type="text" name="CopyTo" value="'+CopyList_Arr[i]+'" class="CopyE_inp">'+
										'</div>';
									}
									else{
										var str1 = '<div class="Copyman'+(i)+' newCopyman">'+
											'<span class="Copyman">抄送人'+(i)+'</span>'+
											'<input type="text" name="CopyTo" value="'+CopyList_Arr[i]+'" class="CopyE_inp">'+
											'<span class= "Copy_del" style="margin-left:3px;" ><i class="fa fa-close" id="fa-button3"></i></span>'+
										'</div>';
									}
									$(".Copylist").append(str1);
								}
							}
								
							var EndCompany =$("#view8 .EndCompany").text();
							if(!EndCompany){EndCompany = '无'}
							var CascadePOConNumber = $("#view8 .CascadePOConNumber").text();
							if(!CascadePOConNumber){CascadePOConNumber = '无'}
							$(".contract_sendPO1 #Theme").text('').text('Eoulu：New PO of '+EndCompany+' '+CascadePOConNumber);
							$(".cover_color").css("height",$("#view8").height()+130);
							$(".cover_color").show();
							$(".contract_sendPO1").show();
					}
				},
				error:function(){
					
				}
			})
		}else if(submitView == "Sidebar_CascadePOMachine"){
			var POID = $(".POID2").text();
			
			var CascadePOConNumber = $("#view89 .CascadePOConNumber").text();
			$(".contract_title").attr("value",CascadePOConNumber);
			$(".contract_sendPO1 .contract_title").attr("flag","Sidebar_CascadePOMachine");
			$.ajax({
				type:"POST",
				url:"SendCascadeEmail",
				dataType:"JSON",
				data:{
					POID:POID
				},
				success:function(data){
					console.log(data.length)
					if(data.length == 1){

						var submitView = $(".CascadePOPdf .isHover").attr("ID");
						$(".contract_sendPO1 #ToEmail").text(" ");
						$(".contract_sendPO1 #CopyTo").text(" ");
						$(".contract_sendPO1 .newCopyman").remove();
						$(".contract_sendPO1 .newToman").remove();
						
							$(".Tolist").append(Tnormal);$(".Copylist").append(Cnormal);
						
							$(".contract_sendPO1 .contract_title").attr("flag","Sidebar_CascadePOMachine");
							var EndCompany =$("#view89 .EndCompany").text();
							if(!EndCompany){EndCompany = '无'}
							var CascadePOConNumber = $("#view89 .CascadePOConNumber").text();
							if(!CascadePOConNumber){CascadePOConNumber = '无'}
							$(".contract_sendPO1 #Theme").text('').text('Eoulu：New PO of '+EndCompany+' '+CascadePOConNumber);
							$(".cover_color").css("height",$("#view89").height()+130);
							$(".cover_color").show();
							$(".contract_sendPO1").show();
						}else{
							$(".ID2").text(data[1].ID);
							//收件人抄送人邮件内容不为空根据内容回显
							var submitView = $(".CascadePOPdf .isHover").attr("ID");
							$(".contract_sendPO1 #ToEmail").text(" ");
							$(".contract_sendPO1 #CopyTo").text(" ");
							$(".contract_sendPO1 .newCopyman").remove();
							$(".contract_sendPO1 .newToman").remove();
							
								$(".contract_sendPO1 .contract_title").attr("flag","Sidebar_CascadePOMachine");
								console.log(data[1].Content)
								var Content = data[1].Content1;
								//——————————————————收件人——————————————————
								var Recepter = data[1].Recepter;
								console.log(Recepter);
								var Recepter_Arr = Recepter.split(';');
								console.log(Recepter_Arr)
								if(Recepter_Arr.length <= 2){
									var str2 = '<div class="Toman'+(0)+' newToman">'+
										'<span class="Toman">收件人</span>'+
										'<input type="text" name="ToEmail" value="'+Recepter_Arr[0]+'" class="ToE_inp">'+
										'<span class="To_add"><i class="fa fa-plus-square" id="fa-button3"></i></span>'+
									'</div>';
									$(".Tolist").append(str2);
								}else{
									for(var i = 0;i<Recepter_Arr.length -1;i++){
										
										if(i == (Recepter_Arr.length-2)&& i != 0){
											var str2 = '<div class="Toman'+(i)+' newToman">'+
												'<span class="Toman">收件人'+(i)+'</span>'+
												'<input type="text" name="ToEmail" value="'+Recepter_Arr[i]+'" class="ToE_inp">'+
												'<span class="To_add"><i class="fa fa-plus-square" id="fa-button3"></i></span>'+
												'<span class= "To_del" style="margin-left:3px;" ><i class="fa fa-close" id="fa-button3"></i></span>'+
											'</div>';
										}else if(i == 0){
											var str2 = '<div class="Toman'+(i)+' newToman">'+
												'<span class="Toman">收件人</span>'+
												'<input type="text" name="ToEmail" value="'+Recepter_Arr[i]+'" class="ToE_inp">'+
											'</div>';
										}
										else{
											var str2 = '<div class="Toman'+(i)+' newToman">'+
												'<span class="Toman">收件人'+(i)+'</span>'+
												'<input type="text" name="ToEmail" value="'+Recepter_Arr[i]+'" class="ToE_inp">'+
												'<span class= "To_del" style="margin-left:3px;" ><i class="fa fa-close" id="fa-button3"></i></span>'+
											'</div>';
										}
										$(".Tolist").append(str2);
									}		
								}
								
								//——————————————————抄送人——————————————————
								var CopyList = data[1].CopyList;

								var CopyList_Arr = CopyList.split(';');
								console.log(CopyList_Arr.length);
								if(CopyList_Arr.length <= 2){
									var str1 = '<div class="Copyman'+(0)+' newCopyman">'+
										'<span class="Copyman">抄送人</span>'+
										'<input type="text" name="CopyTo" value="'+CopyList_Arr[0]+'" class="CopyE_inp">'+
										'<span class="Copy_add"><i class="fa fa-plus-square" id="fa-button3"></i></span>'+
									'</div>';
									$(".Copylist").append(str1);
								}else{
									for(var i = 0;i<CopyList_Arr.length-1;i++){
										if(i == (CopyList_Arr.length-2) && i != 0){
											var str1 = '<div class="Copyman'+(i)+' newCopyman">'+
												'<span class="Copyman">抄送人'+(i)+'</span>'+
												'<input type="text" name="CopyTo" value="'+CopyList_Arr[i]+'" class="CopyE_inp">'+
												'<span class="Copy_add"><i class="fa fa-plus-square" id="fa-button3"></i></span>'+
												'<span class= "Copy_del" style="margin-left:3px;" ><i class="fa fa-close" id="fa-button3"></i></span>'+
											'</div>';
										}
										else if(i == 0){
												var str1 = '<div class="Copyman'+(i)+' newCopyman">'+
												'<span class="Copyman">抄送人</span>'+
												'<input type="text" name="CopyTo" value="'+CopyList_Arr[i]+'" class="CopyE_inp">'+
											'</div>';
										}
										else{
											var str1 = '<div class="Copyman'+(i)+' newCopyman">'+
												'<span class="Copyman">抄送人'+(i)+'</span>'+
												'<input type="text" name="CopyTo" value="'+CopyList_Arr[i]+'" class="CopyE_inp">'+
												'<span class= "Copy_del" style="margin-left:3px;" ><i class="fa fa-close" id="fa-button3"></i></span>'+
											'</div>';
										}
										$(".Copylist").append(str1);
									}
								}
								var EndCompany =$("#view8 .EndCompany").text();
								if(!EndCompany){EndCompany = '无'}
								var CascadePOConNumber = $("#view8 .CascadePOConNumber").text();
								if(!CascadePOConNumber){CascadePOConNumber = '无'}
								$(".contract_sendPO1 #Theme").text('').text('Eoulu：New PO of '+EndCompany+' '+CascadePOConNumber);
								$(".cover_color").css("height",$("#view8").height()+130);
								$(".cover_color").show();
								$(".contract_sendPO1").show();
						}
					},
					error:function(){
						
					}
				})
			
		}
		else if(submitView == "Sidebar_OtherPO"){
			$(".contract_sendPO2 .contract_title").text('其他供应商USD PO 通知发送');
			$(".contract_sendPO2 .contract_title").attr("flag","Sidebar_OtherPO");
			$(".cover_color").css("height",$("#view9").height()+130);
			$(".cover_color").show();
			$(".contract_sendPO2").show();
		}
		else{
			$(".contract_sendPO2 .contract_title").text('其他供应商RMB PO 通知发送');
			$(".contract_sendPO2 .contract_title").attr("flag","Sidebar_OtherPORMB");
			$(".cover_color").css("height",$("#view10").height()+130);
			$(".cover_color").show();
			$(".contract_sendPO2").show();
		}	
	})
		//邮件取消1
		$('.edit_btn1 #send_cancel1').click(function() {
			$('.cover_color').hide();
			$('.contract_sendPO1').hide();
		});
		$('.edit_btn2 #send_cancel2').click(function() {
			$('.cover_color').hide();
			$('.contract_sendPO2').hide();
		});
	   //邮件关闭
			$('.contract_sendPO1 .contract_close').click(function() {
				$('.cover_color').hide();
				$('.contract_sendPO1').hide();
			});
	     // 邮件发送
	    $(document).on("click","#send_submit1",function(){
	    	var submitFlag = $(".contract_sendPO1 .contract_title").attr("flag");
	    	
	    	if(submitFlag == "Sidebar_CascadePO"){
	    		var ID = $(".ID1").text();
	    		if(ID == ""){
	    			ID = 0;
	    		}else{
	    			ID = ID.substring(0,ID.length/2);
	    		}
	    		var POID1 = $(".POID1").text();
	    		var CascadePOConNumber = $(".table_title").attr("value");
		    	if(POID1 == 0 || CascadePOConNumber == ''  || CascadePOConNumber == '--' ){
		    		$.MsgBox_Unload.Alert("提示", "请检查是否生成Cascade PO！");
		    		return;
		    	}
	    		$(this).addClass("disabled");
		    	var $this = $(this);
	    		var CascadeStatus="yes";
		    	var Type="Cascade";
		    	var ToEmail=$('.contract_sendPO1 input[name="ToEmail"]').val();
		    	var CopyTo=$('.contract_sendPO1 input[name="CopyTo"]').val();
		    	var NickName=$('.contract_sendPO1 input[name="NickName"]').val();
		    	var Contact = $('#view8 .Contact').text();
				var Email = $('#view8 .Email').text();
				var QuoteID = $('#view8 .yemei').attr("currentID");
				
				var CopyList ="";
				var Recepter ="";
				for(var j = 0; j<$(".Tolist div").length; j++){
					if($(".Tolist div").eq(j).find(".ToE_inp").val() !=""){
						Recepter+=$(".Tolist div").eq(j).find(".ToE_inp").val()+";";
					}
				}
				console.log($(".Copylist .newCopyman").length)
				for(var n = 0; n<$(".Copylist div").length; n++){
					
					if($(".Copylist div").eq(n).find(".CopyE_inp").val() !=""){
						CopyList+=$(".Copylist div").eq(n).find(".CopyE_inp").val()+";";
					}
				}
            	var Content1 = $('.contract_sendPO1 #NickName').val();
				var Content = Content1.replace(/\n/g,"<br>");
				Content = '<span style="font-family:Arial;font-size:14px;line-height:30px;">'+Content+'</span>';
				console.log(Content);
	    	    $.ajax({
		            type: 'get',
		            url: 'SendCascadeEmail',
		            data: {
		            	FileType:'Parts',
		            	QuoteID:QuoteID,
		            	Type:Type,
		            	CascadeStatus:CascadeStatus,
		            	ToEmail:ToEmail,
		            	CopyTo:CopyTo,
		            	NickName:NickName,
		            	Contact:Contact,
		            	Email:Email,
		            	Recepter:Recepter,
		            	CopyList:CopyList,
		            	Content:Content,
		            	Content1:Content1,
		            	ID:ID
		            },
		            dataType: "JSON",
		            success: function (data) {
		            	$this.removeClass("disabled");
		            	$.MsgBox.Alert('提示',data);
		            	$('.cover_color').hide();
		                $('.contract_sendPO1').hide();
		            },
		            error: function () {
		            	$this.removeClass("disabled");
		            	$.MsgBox_Unload.Alert("提示", "发送失败！");
		            }
	           }); 
	    	}
	    	else if(submitFlag == "Sidebar_CascadePOMachine"){
	    		var ID = $(".ID2").text();
	    		if(ID == ""){
	    			ID = 0;
	    		}
	    		var POID2 = $(".POID2").text();
	    		var CascadePOConNumber = $(".contract_title").attr("value");
		    	if(POID2 == 0 || CascadePOConNumber == '' || CascadePOConNumber == '--' ){
		    		$.MsgBox_Unload.Alert("提示", "请检查是否生成Cascade PO！");
		    		return;
		    	}
		    	$(this).addClass("disabled");
		    	var $this = $(this);
	    		var CascadeStatus="yes";
		    	var Type="Cascade";
		    	var ToEmail=$('.contract_sendPO1 input[name="ToEmail"]').val();
		    	var CopyTo=$('.contract_sendPO1 input[name="CopyTo"]').val();
		    	var NickName=$('.contract_sendPO1 input[name="NickName"]').val();
		    	var Contact = $('#view89 .Contact').text();
				var Email = $('#view89 .Email').text();
				var QuoteID = $('#view89 .yemei').attr("currentID");
				var  CopyList ="";var  Recepter ="";
				for(var j = 0; j<$(".Tolist div").length; j++){
					if($(".Tolist div").eq(j).find(".ToE_inp").val() !=""){
						Recepter+=$(".Tolist div").eq(j).find(".ToE_inp").val()+";";
					}
				}
				console.log($(".Copylist .newCopyman").length)
				for(var n = 0; n<$(".Copylist div").length; n++){
					
					if($(".Copylist div").eq(n).find(".CopyE_inp").val() !=""){
						CopyList+=$(".Copylist div").eq(n).find(".CopyE_inp").val()+";";
					}
				}
				var Content1 = $('.contract_sendPO1 #NickName').val();
				var Content = Content1.replace(/\n/g,"<br>");
				Content = '<span style="font-family:Arial;font-size:14px;line-height:30px;">'+Content+'</span>';
	    	    $.ajax({
		            type : 'get',
		            url : 'SendCascadeEmail',
		            data : {
		            	FileType:'Complete',
		            	QuoteID:QuoteID,
		            	Type:Type,
		            	CascadeStatus:CascadeStatus,
		            	ToEmail:ToEmail,
		            	CopyTo:CopyTo,
		            	NickName:NickName,
		            	Contact:Contact,
		            	Email:Email,
		            	Recepter:Recepter,
		            	CopyList:CopyList,
		            	Content:Content,
		            	Content1:Content1,
		            	ID:ID
		            },
		            dataType : 'json',
		            success : function (data) {
		            	$this.removeClass("disabled");
		            	$.MsgBox.Alert('提示',data);
		                $('.cover_color').hide();
		                $('.contract_sendPO1').hide();
		            },
		            error : function () {
		            	$this.removeClass("disabled");
		            	$.MsgBox_Unload.Alert("提示", "发送失败！");
		            }
	           }); 
	    	}
	    })
	    
	     // 邮件发送
	    $(document).on("click","#send_submit2",function(){
	    	var submitFlag = $(".contract_sendPO2 .contract_title").attr("flag");
	    	$(this).addClass("disabled");
	    	$this = $(this);
	    	 if(submitFlag == "Sidebar_OtherPO"){
		    	var Type= "OtherSupplier";
		    	var ToEmail=$('.contract_sendPO2 input[name="ToEmail"]').val();
		    	var CopyTo=$('.contract_sendPO2 input[name="CopyTo"]').val();
		    	var NickName=$('.contract_sendPO2 input[name="NickName"]').val();
		    	var Contact = $('#view9 .Contact').text();
				var Email = $('#view9 .Email').text();
				var QuoteID = $('#view9 .yemei').attr("currentID");
				var OtherStatus="yes";
		    	   $.ajax({
		            type : 'get',
		            url : 'SendOtherEmail',
		            data : {
		            	QuoteID:QuoteID,
		            	Type:Type,
		            	OtherStatus:OtherStatus,
		            	ToEmail:ToEmail,
		            	CopyTo:CopyTo,
		            	NickName:NickName,
		            	Contact:Contact,
		            	Email:Email
		            },
		            dataType : 'json',
		            success : function (data) {
		            	$this.removeClass("disabled");
		            	if(data==true){
		            		$.MsgBox.Alert('提示','发送成功!');
			                $('.cover_color').hide();
			                $('.contract_sendPO2').hide();
		            	}else{
		            		$.MsgBox_Unload.Alert("提示", "请检查是否生成  其他供应商 PO！");
		            	}
		                
		            },
		            error : function () {
		            	$this.removeClass("disabled");
		            	$.MsgBox_Unload.Alert("提示", "发送失败！");
		            }
		        }); 
	    	}
	 
	    	else{
	    		var Type="OtherSupplierRMB";
		    	var ToEmail=$('.contract_sendPO2 input[name="ToEmail"]').val();
		    	var CopyTo=$('.contract_sendPO2 input[name="CopyTo"]').val();
		    	var NickName=$('.contract_sendPO2 input[name="NickName"]').val();
		    	var Contact = $('#view10 .Contact').text();
				var Email = $('#view10 .Email').text();
				var QuoteID = $('#view10 .yemei').attr("currentID");
				var OtherRMBStatus="yes";
		    	   $.ajax({
		            type : 'get',
		            url : 'SendOtherRMBEmail',
		            data : {
		            	QuoteID:QuoteID,
		            	Type:Type,
		            	OtherRMBStatus:OtherRMBStatus,
		            	ToEmail:ToEmail,
		            	CopyTo: CopyTo,
		            	NickName:NickName,
		            	Contact:Contact,
		            	Email:Email
		            },
		            dataType : 'json',
		            success : function (data) {
		            	$this.removeClass("disabled");
		            	console.log(data)
		            	if(data==true){
		            		$.MsgBox.Alert('提示','发送成功!');
			                $('.cover_color').hide();
			                $('.contract_sendPO2').hide();
		            	}else{
		            		$.MsgBox_Unload.Alert("提示", "请检查是否生成 其他供应商  RMB PO！");
		            	}
		            },
		            error : function () {
		            	$this.removeClass("disabled");
		            	$.MsgBox_Unload.Alert("提示", "发送失败！");
		            }
		        }); 
	    	}
	    })
	    
	    //PO模板下载整合
     	$("#download8").click(function() {
     		$(this).addClass("disabled");
     		var $this = $(this);
     		var submitView = $(".CascadePOPdf .isHover").attr("ID");
			if(submitView == "Sidebar_CascadePO"){
				var quoteID= $('#view8 .yemei').attr("currentID");
				var parts = 'Parts';
				var downloadURL = "DownloadCascadePO";
			}
			else if(submitView == "Sidebar_CascadePOMachine"){
				var quoteID= $('#view89 .yemei').attr("currentID");
				var parts = 'Complete';
				var downloadURL = "DownloadCascadePO";
			}
			else if(submitView == "Sidebar_OtherPO"){
				var quoteID= $('#view9 .yemei').attr("currentID");
				var parts = '';
				var downloadURL = "DownloadOtherPO";
			}
			else{
				var quoteID= $('#view10 .yemei').attr("currentID");
				var parts = '';
				var downloadURL = "DownloadOtherRMBPO";
			}
			console.log(quoteID);
			console.log(downloadURL);
			$.ajax({
				type : 'get',
				url : downloadURL,
				data : {
					QuoteID : quoteID,
					Type:parts
				},
				success : function(data) {
					console.log(data);
					window.location.href = data;
					$this.removeClass("disabled");
				},
				error : function() {
					$.MsgBox_Unload.Alert("提示", "下载失败！");
					$this.removeClass("disabled");
				}
			});
		})

		//华为Ego  导出Excel
		$("#exportEXCEL").click(function(){
			var currentTr = $("#table1").find("tr").eq($(".allHidePdf").attr("value"));
			var QuoteID = currentTr.find("td").eq(0).attr("value");
			var submitView = $(".allHidePdf .isHover").attr("ID");
			if(submitView == "EgoMachine"){
				var ExcelType = "Complete";
			}
			else{
				var ExcelType = "Parts";
			}
			console.log(QuoteID);
			if(submitView=="costComparison"){
				var curHref = window.location.href;
				var curHref1;
				if(curHref.indexOf("/SalesQuotationSystem")>-1){
					curHref1 = curHref.split("SalesQuotationSystem")[0]+"ExportCostExcel";
				}else{
					curHref1 = curHref.split("QuotationSystem")[0]+"ExportCostExcel";
				}
				curHref1 = curHref1+"?ID="+QuoteID;
				window.location.href = curHref1;
			}else{
				$.ajax({
					type : 'get',
					url : "ExportHuaEgoExcel",
					data : {
						QuoteID : QuoteID,
						ExcelType : ExcelType
					},
					success : function(data) {
						console.log(data);
						window.location.href = data;
					},
					error : function() {
						$.MsgBox_Unload.Alert("提示", "导出失败！");
					}
				});
			}
		})
		
		//发货通知
		$(".Shipnotice").click(function(){
			var QuoteID = $(this).parent().children().eq(0).attr("value");
			$(".ShipnoticeBox .contract_title").attr("ID",QuoteID);
			$(".Shipnotice_leftul input,.Shipnotice_rightul input").val("");
			$(".add_fileCont").text("");
			$.ajax({
				type : 'get',
				url : "QuoteDeliveryAdvice",
				data : {
					QuoteID : QuoteID,
				},
				dataType : 'json',
				success : function(data) {
					console.log(data);
					if(data.advice.length == 1){
						$(".Shipnotice_leftul input,.Shipnotice_rightul input").val("");
						$(".add_fileCont").text("");
					}
					else{
						$(".add_fileCont").text(data.advice[1].PackingFile);
						$(".ShipnoticeBox input[name='PONO']").val(data.advice[1].PONO);
						$(".ShipnoticeBox input[name='SONO']").val(data.advice[1].SONO);
						$(".ShipnoticeBox input[name='Company']").val(data.advice[1].Company);
						$(".ShipnoticeBox input[name='Contact']").val(data.advice[1].Contact);
						$(".ShipnoticeBox input[name='Email']").val(data.advice[1].Email);
						$(".ShipnoticeBox input[name='LinkTel']").val(data.advice[1].LinkTel);
						$(".ShipnoticeBox input[name='InstallPlace']").val(data.advice[1].InstallPlace);
						$(".ShipnoticeBox .OriginService").find("option[value='"+data.advice[1].OriginService +"']").attr("selected","selected");
						$(".ShipnoticeBox .contract_title").attr("modifyID",data.advice[1].ID);
					};
					$("#ShipnoticeTable .SerAddTR").remove();
					
					var str = "";
					for(var i = 1 ; i < data.commodity.length ; i++){
						if(data.commodity[i].ID){ var id = data.commodity[i].ID}
						else{
							var id = 0 
						}
						str +='<tr class="SerAddTR">'+
						'<td class="Shipnotice_Ser" ID="'+id+'"><span class="fa fa-plus-square-o SerAdd" ></span><span class="SerText">'+i+'</span></td>'+
						'<td contenteditable="true"  class="Shipnotice_Model" style="width:19.85%;height:30px;line-height:30px;outline:none;">'+data.commodity[i].Model+'</td>'+
						'<td contenteditable="true" class="Shipnotice_Des" style="width:37.26%;height:30px;line-height:30px;outline:none;">'+data.commodity[i].Description+'</td>'+
						'<td style="width:8.71%;height:30px;line-height:30px;outline:none;" class="Shipnotice_Num"><input  type="number" min="0" class="ShipNumInput" value="'+data.commodity[i].Quantity+'"  style="width: 100%;height:30px;line-height:30px;font-size:17px;border: none;"></td>'+
						'<td style="width:15.71%;height:30px;line-height:30px;outline:none;" class="Shipnotice_Rem"  contenteditable="true" >'+data.commodity[i].Remarks+'</td>'+
						'<td  style="width:9.52%;height:30px;line-height:30px;outline:none;" class="Shipnotice_Oper" contenteditable="true">'+
							'<span class="fa fa-long-arrow-up OperUp" ></span>'+
						'	<span class="fa fa-trash OperDel" ></span>'+
						'	<span class="fa fa-long-arrow-down OperDown" ></span>'+
						'</td>'+
					'</tr>'
					};
					$("#ShipnoticeTable .tHead").after(str);
				},
				error : function() {
					$.MsgBox_Unload.Alert("提示", "服务器繁忙，请稍候！");
				}
			});
			
			
			 // $(".MailBar_cover_color").css("min-height",$(window).height()+30);
			 // $(".MailBar_cover_color").css("height",$(".ShipnoticeBox").height()+130);
			$(".ShipnoticeBox").show();
			$(".MailBar_cover_color").show();	
		})
		
		$("#Shipnotice_submit").click(function(){
			var QuoteID = $(".ShipnoticeBox .contract_title").attr("ID");
			var PackingFile  = $(".ShipnoticeBox .add_fileCont").text();
			var PONO  = $(".ShipnoticeBox input[name='PONO']").val();
			var SONO  = $(".ShipnoticeBox input[name='SONO']").val();
			var OriginService  = $(".ShipnoticeBox .OriginService").val();
			var Company  = $(".ShipnoticeBox input[name='Company']").val();
			var Contact  = $(".ShipnoticeBox input[name='Contact']").val();
			var Email  = $(".ShipnoticeBox input[name='Email']").val();
			var LinkTel  = $(".ShipnoticeBox input[name='LinkTel']").val();
			var InstallPlace  = $(".ShipnoticeBox input[name='InstallPlace']").val();
			var DeliveryID =[];var Model =[];var Description =[];var Quantity =[];var Remarks =[];
			for(var i = 0 ; i < $(".SerAddTR").length ; i++){
				var ID = $(".SerAddTR").eq(i).find(".Shipnotice_Ser").attr("ID");
				if(!ID){
					DeliveryID.push(0);
				}
				else{
					DeliveryID.push(ID);
				}
				var ModelCon = $(".SerAddTR").eq(i).find(".Shipnotice_Model").text();
				var DescriptionCon = $(".SerAddTR").eq(i).find(".Shipnotice_Des").text();
				var QuantityCon = $(".SerAddTR").eq(i).find(".ShipNumInput").val();
				var RemarksCon = $(".SerAddTR").eq(i).find(".Shipnotice_Rem").text();
				Model.push(ModelCon);
				Description.push(DescriptionCon);
				Quantity.push(QuantityCon);
				Remarks.push(RemarksCon);
			}
			//modifyId 修改后的ID
			var modifyId = $(".ShipnoticeBox .contract_title").attr("modifyID");
			if(!modifyId){
				modifyId = 0;
			}
			var str1= "";
			for(var i = 0 ; i < $(".SerAddTR").length ; i++){
				str1 +='<tr class="SerAddTR">'+
				'<td class="Shipnotice_Ser"><span class="fa fa-plus-square-o SerAdd" ></span><span class="SerText">'+$(".SerAddTR").eq(i).find(".SerText").text()+'</span></td>'+
				'<td contenteditable="true"  class="Shipnotice_Model" style="width:19.85%;height:30px;line-height:30px;outline:none;">'+$(".SerAddTR").eq(i).find(".Shipnotice_Model").text()+'</td>'+
				'<td contenteditable="true" class="Shipnotice_Des" style="width:37.26%;height:30px;line-height:30px;outline:none;">'+$(".SerAddTR").eq(i).find(".Shipnotice_Des").text()+'</td>'+
				'<td style="width:8.71%;height:30px;line-height:30px;outline:none;" class="Shipnotice_Num">'+$(".SerAddTR").eq(i).find(".ShipNumInput").val()+'</td>'+
				'<td style="width:15.71%;height:30px;line-height:30px;outline:none;" class="Shipnotice_Rem"  contenteditable="true" >'+$(".SerAddTR").eq(i).find(".Shipnotice_Rem").text()+'</td>'+
			'</tr>'
					
	    	}
			var Table = '<table border="1" cellspacing="0" cellspadding="0" id="ShipnoticeTable"  style="width:100%;height:30px;text-align:center;border-color:#00aeef;">'+
			'<tr class="tHead">'+
			'<td class="Shipnotice_Ser" style="width:8.8%;height:30px;line-height:30px;outline:none;"><span class="fa fa-plus-square-o SerAdd" style="vertical-align: middle;display:none;"></span><span class="SerText">序号</span></td>'+
			'<td style="width:19.85%;height:30px;line-height:30px;outline:none;">型号</td>'+
			'<td style="width:37.26%;height:30px;line-height:30px;outline:none;">描述</td>'+
			'<td style="width:8.71%;height:30px;line-height:30px;outline:none;">数量</td>'+
			'<td style="width:15.71%;height:30px;line-height:30px;outline:none;">备注</td>'+
		'</tr>'+str1+
	'</table>'
		
			if(PackingFile == "" || PackingFile == "--"){
				$.MsgBox_Unload.Alert("提示", "请上传箱单文件！");
			}
			else if(PONO == "" || PONO == "--"){
				$.MsgBox_Unload.Alert("提示", "请填写PONO！");
			}else if(SONO == "" || SONO == "--"){
				$.MsgBox_Unload.Alert("提示", "请填写SONO！");
			}
			else{
				$.ajax({
					type : 'POST',
					// url : "QuoteDeliveryAdviceOperate",
					url : "QuoteDeliveryAdvice",
					data : {
						ID : modifyId,
						QuoteID : QuoteID,
						PackingFile : PackingFile,
						PONO : PONO,
						SONO : SONO,
						OriginService : OriginService,
						Company : Company,
						Contact : Contact,
						Email : Email,
						LinkTel : LinkTel,
						InstallPlace : InstallPlace,
						DeliveryID : DeliveryID,
						Model : Model,
						Description : Description,
						Quantity : Quantity,
						Remarks : Remarks,
						Table :Table
					},
					success : function(data) {
						console.log(data);
						$.MsgBox.Alert("提示", data);
					},
					error : function() {
						$.MsgBox_Unload.Alert("提示", "服务器繁忙，请稍候！");
					}
				});
			}
	    })
		
		//发货通知框  关闭
		$('.Shipnotice_close,#Shipnotice_cancel').click(function() {
			$('.MailBar_cover_color').hide();
			$(".ShipnoticeBox").hide();
		});
	    $(document).on("mouseover",".Shipnotice_Ser",function(){
	    	$(this).find(".SerAdd").toggle();
	    	$(this).find(".SerText").css("marginLeft","4px");
	    })
	    $(document).on("mouseout",".Shipnotice_Ser",function(){
	    	$(this).find(".SerAdd").toggle();
	    	$(this).find(".SerText").css("marginLeft","0px");
	    })
	    var Serial = 0;
	    $(document).on("click",".SerAdd",function(){
	    	Serial++
	    	var str='<tr class="SerAddTR">'+
					'<td class="Shipnotice_Ser"><span class="fa fa-plus-square-o SerAdd" ></span><span class="SerText">'+Serial+'</span></td>'+
					'<td contenteditable="true"  class="Shipnotice_Model" style="width:19.85%;height:30px;line-height:30px;outline:none;"></td>'+
					'<td contenteditable="true" class="Shipnotice_Des" style="width:37.26%;height:30px;line-height:30px;outline:none;"></td>'+
					'<td style="width:8.71%;height:30px;line-height:30px;outline:none;" class="Shipnotice_Num"><input  type="number" min="0" class="ShipNumInput" style="width: 100%;height:30px;line-height:30px;font-size:17px;border: none;"></td>'+
					'<td style="width:15.71%;height:30px;line-height:30px;outline:none;" class="Shipnotice_Rem"  contenteditable="true" ></td>'+
					'<td  style="width:9.52%;height:30px;line-height:30px;outline:none;" class="Shipnotice_Oper" contenteditable="true">'+
						'<span class="fa fa-long-arrow-up OperUp" ></span>'+
					'	<span class="fa fa-trash OperDel" ></span>'+
					'	<span class="fa fa-long-arrow-down OperDown" ></span>'+
					'</td>'+
				'</tr>'
	    	$(this).parent().parent().after(str);
	    	for(var i = 0 ; i < $(".SerAddTR").length ; i++){
	    		$(".SerAddTR .SerText").eq(i).text(i+1);
	    	}
	    })
	    $(document).on("click",".Shipnotice_Oper .OperDel",function(){
	    	Serial--;
	    	$(this).parent().parent().remove();
	    	console.log($(".SerAddTR").length)
	    	for(var i = 0 ; i < $(".SerAddTR").length ; i++){
	    		$(".SerAddTR .SerText").eq(i).text(i+1);
	    	}
	    })
		$(document).on("click",".Shipnotice_Oper .OperUp",function(){
				var preTr = $(this).parent().parent().prev("tr").not(".tHead");
	            $(this).parent().parent().insertBefore(preTr);
	            for(var i = 0 ; i < $(".SerAddTR").length ; i++){
		    		$(".SerAddTR .SerText").eq(i).text(i+1);
		    	}
        });
        $(document).on("click",".Shipnotice_Oper .OperDown",function(){
            var preTr = $(this).parent().parent().next("tr");
            $(this).parent().parent().insertAfter(preTr);
            for(var i = 0 ; i < $(".SerAddTR").length ; i++){
	    		$(".SerAddTR .SerText").eq(i).text(i+1);
	    	}
        });
		
        //报价单 模板  上、下、删除操作
	    $(document).on("click",".Oper .OperDel",function(){
	    	var tabletrLen = $(this).parent().parent().parent().parent();//
	    	$(this).parent().parent().remove();
	    	 console.log(tabletrLen.find("tr").not(".ita").length )
	    	for(var i = 0 ; i < tabletrLen.find("tr").not(".ita").length ; i++){
	    		tabletrLen.find("tr").not(".ita").eq(i).find(".Item").text(i+1);
	    	}
	    	
	    })
		$(document).on("click",".Oper .OperUp",function(){
			var preTr = $(this).parent().parent().prev("tr").not(".ita");
            $(this).parent().parent().insertBefore(preTr);
            var tabletrLen = $(this).parent().parent().parent().parent().find("tr").not(".ita");
            console.log(preTr)
	    	for(var i = 0 ; i < tabletrLen.length ; i++){
	    		tabletrLen.eq(i).find(".Item").text(i+1);
	    	}
        });
        $(document).on("click",".Oper .OperDown",function(){
            var preTr = $(this).parent().parent().next("tr");
            $(this).parent().parent().insertAfter(preTr);
            var tabletrLen = $(this).parent().parent().parent().parent().find("tr").not(".ita");
            console.log(tabletrLen.length)
	    	for(var i = 0 ; i < tabletrLen.length ; i++){
	    		tabletrLen.eq(i).find(".Item").text(i+1);
	    	}
        });
        
        //商品管理
    	$(document).on("click",".ModifyOper .OperUp,.AddOper .OperUp",function(){
			var preTr = $(this).parent().parent().prev("tr").not(".tableUpdateTit,.tableADDTit");
            $(this).parent().parent().insertBefore(preTr);
            //var tabletrLen = $(this).parent().parent().parent().parent().find("tr").not(".ita");
        });
        $(document).on("click",".ModifyOper .OperDown,.AddOper .OperDown",function(){
            var preTr = $(this).parent().parent().next("tr");
            $(this).parent().parent().insertAfter(preTr);
        });
        
     // 箱单     上传
        $(document).on("change",".add_file",function(){
            var filePath=$(this).val().replace("C:\\fakepath\\","");
        	$(".add_fileCont").html(filePath);
        	$("#add_fileBox1").submit();
        })
// ----------------------------------------搜索框----------------------------------------------
if($('input[name="selected"]:checked').val()=='singleSelect'){
    $('.select-content').css('margin-left','33%');
}else{
    $('.select-content').css('margin-left','23%');

}
function Check(selected) {
	 if (selected == "SingleSelect") {
	        $('.select2').hide();
	        $('.select-content').css('margin-left','33%');
	    } else {
	        $('.select2').show();
	        $('.select-content').css('margin-left','23%');
	    }
}

function selectSearch() {
	if ($('.select1 .time').css("display") == "none" && $('.select2 .time').css("display") == "none") { 
	// 非货期查询
		if ($(".select2").css("display") == "none") {
			if ($("#searchContent1").val() != "") {
				$('#search').val('search');
				// $('#top_text_from').submit();
				var base_url;
				if(window.location.href.indexOf("/SalesQuotationSystem")>-1){
					base_url = window.location.href.split("SalesQuotationSystem")[0]+"SalesQuotationSystem?queryType=SingleSelect";
				}else{
					base_url = window.location.href.split("QuotationSystem")[0]+"QuotationSystem?queryType=SingleSelect";
				}
				// var currentPage = $("span#currentPage").text();
				var currentPage = 1;
				var classify1 = $("#type1").val();
				var param1 = $("#searchContent1").val().trim();
				if(param1==""||param1==undefined){
					alert("搜索内容为空");
					return false;
				}
				window.location.href = base_url+"&currentPage="+currentPage+"&classify1="+classify1+"&param1="+param1;
			}
		} else {
			if ($("#searchContent1").val() != ""
					&& $("#searchContent2").val() != "") {
				$('#search').val('search');
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
				var newUrl = document.getElementById("top_text_from").action
						+ "?ActualDelivery=no&column=DateOfSign&condition=All";
				$('#top_text_from').attr('action', newUrl);
				$('#top_text_from').submit();
			} else {
				alert("请正确输入时间！");
			}
		}
	}
}

function selectCancel() {
    $('input[name="searchContent1"]').val('');
    $('input[name="searchContent2"]').val('');
    $('input[name="start_time1"]').val('');
    $('input[name="end_time1"]').val('');
    $('input[name="start_time2"]').val('');
    $('input[name="end_time2"]').val('');
    if(window.location.href.indexOf("/SalesQuotationSystem")>-1){
    	window.location.href="SalesQuotationSystem";
    }else{
    	window.location.href="QuotationSystem";
    }
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

function AttrDisabled(dom){
	dom.attr("disabled","disabled");
}
function RemoveAttr(dom){
	dom.attr("disabled",false);
}

//邮件部分
$(document).on("click",".To_add .fa-plus-square",function(){
	var len = $(".Tolist div").length;
	
	var str = '<div class="Toman'+(len+1)+' newToman">'+
				'<span class="Toman">收件人'+len+'</span>'+
				'<input type="text" name="ToEmail" value="" class="ToE_inp">'+
				'<span class="To_add"><i class="fa fa-plus-square" id="fa-button3"></i></span>'+
				'<span class= "To_del" style="margin-left:3px;" ><i class="fa fa-close" id="fa-button3"></i></span>'+
			'</div>';
	$(this).parent().parent().after(str);
	$(this).parent().remove();
	
})
$(document).on("click",".To_del .fa-close",function(){
	$(this).parent().parent().remove();
	for(var i = 1 ; i < $(".Tolist div").length ; i++){
		$(".Tolist div").eq(i).find(".Toman").text("收件人"+i);
		if( i == ($(".Tolist div").length - 1)){
			$(".Tolist div").eq(i).find(".To_add").remove();
			var str = '<span class="To_add"><i class="fa fa-plus-square" id="fa-button3"></i></span>';
			$(".Tolist div").eq(i).find(".ToE_inp").after(str)
		}
	}
	if($(".Tolist div").length == 1){
		var str = '<span class="To_add"><i class="fa fa-plus-square" id="fa-button3"></i></span>';
		$(".Tolist div .To_del").remove();
		$(".Tolist div").eq(0).find(".ToE_inp").after(str);
	}
})

$(document).on("click",".Copy_add .fa-plus-square",function(){
	
	var len = parseFloat($(".Copylist div").length);
	
	var str = '<div class="Copyman'+(len+1)+'  newCopyman">'+
				'<span class="Copyman">收件人'+len+'</span>'+
				'<input type="text" name="CopyTo" value="" class="CopyE_inp">'+
				'<span class="Copy_add"><i class="fa fa-plus-square" id="fa-button3"></i></span>'+
				'<span class= "Copy_del"  style="margin-left:3px;"><i class="fa fa-close" id="fa-button3"></i></span>'+
			'</div>';
	$(this).parent().parent().after(str);
	$(this).parent().remove();
	
})
$(document).on("click",".Copy_del .fa-close",function(){
	$(this).parent().parent().remove();
	for(var i = 1 ; i < $(".Copylist div").length ; i++){
		console.log(i)
		$(".Copylist div").eq(i).find(".Copyman").text("抄送人"+i);
		if( i == ($(".Copylist div").length - 1)){
			$(".Copylist div").eq(i).find(".Copy_add").remove();
			var str = '<span class="Copy_add"><i class="fa fa-plus-square" id="fa-button3"></i></span>';
			$(".Copylist div").eq(i).find(".CopyE_inp").after(str)
		}
	}
	if($(".Copylist div").length == 1){
		var str = '<span class="Copy_add"><i class="fa fa-plus-square" id="fa-button3"></i></span>';
		$(".Copylist div .Copy_del").remove();
		$(".Copylist div").eq(0).find(".CopyE_inp").after(str);
	}
	
});

// Cascade PO配件下拉选择
$(document).on("click",".View8ForwarderSel",function(e){
	e.stopPropagation();
	$("div.ForwarderSel").attr("id",$(this).attr("class"));
	var mapJsonObjId = $("div.ForwarderSel").attr("id");
	var mapJsonObj;
	var optionStr = '<option name="0" value="请选择Forwarder" checked>请选择Forwarder</option>';
	console.log(mapJsonObjId);
	switch(mapJsonObjId)
	{
		case "View8ForwarderSel":
		  mapJsonObj = globalQuoCasPOPartForwarder;
		  break;
		default:
		  mapJsonObj = {};
	}
	for(var k in mapJsonObj){
		optionStr+='<option value="'+k+'">'+k+'</option>';
	}
	var mX = e.pageX +5;
	var mY = e.pageY - 10;
	$(".ForwarderSel").css({
		"top":mY+"px",
		"left":mX+"px"
	});
	$(".ForwarderSel").fadeIn(200);
	$("#ForwarderSelect").empty().append(optionStr);
	$(".MailBar_cover_color").fadeIn(200);
});
$(document).on("click",".View8ShipToSel",function(e){
	e.stopPropagation();
	$("div.ShipToSel").attr("id",$(this).attr("class"));
	var mapJsonObjId = $("div.ShipToSel").attr("id");
	var mapJsonObj;
	var optionStr = '<option name="0" value="请选择ShipTo" checked>请选择ShipTo</option>';
	console.log(mapJsonObjId);
	switch(mapJsonObjId)
	{
		case "View8ShipToSel":
		  mapJsonObj = globalQuoCasPOPartShipTo;
		  break;
		default:
		  mapJsonObj = {};
	}
	for(var k in mapJsonObj){
		optionStr+='<option value="'+k+'">'+k+'</option>';
	}

	var mX = e.pageX + 5;
	var mY = e.pageY - 10;
	$(".ShipToSel").css({
		"top":mY+"px",
		"left":mX+"px"
	});
	$(".ShipToSel").fadeIn(200);
	$("#ShipToSelect").empty().append(optionStr);
	$(".MailBar_cover_color").fadeIn(200);
});

$(".ForwarderSel-tit-r").on("click",function(){
	$(".ForwarderSel").fadeOut(200);
});

$(".ShipToSel-tit-r").on("click",function(){
	$(".ShipToSel").fadeOut(200);
});

$(document).on("change","#ForwarderSelect",function(){
	var curVal = $(this).val();
	var mapJSONObjId = $(".ForwarderSel").attr("id");
	var mapJSONObj;
	switch(mapJSONObjId)
	{
		case "View8ForwarderSel":
		  mapJSONObj = globalQuoCasPOPartForwarder;
		  break;
		default:
		  mapJSONObj = {};
	}
	var viewId = "view"+mapJSONObjId.replace(/[^\d]+/g,"");
	$("#"+viewId).find(".ForwarderTd p").text("");
	for(var k in mapJSONObj){
		var objVal = mapJSONObj[k];
		var objValLen = mapJSONObj[k].length;
		if(k==curVal){
			for(var ii = 0;ii<objValLen;ii++){
				var textIn = globalJSONKeyVal(objVal[ii],":");
				$("#"+viewId).find(".ForwarderTd p").eq(ii+1).text(textIn);
			}
			$("#"+viewId).find(".ForwarderTd p").eq(0).text(k);
		}
	}
	$(".ForwarderSel").fadeOut(200);
});

$(document).on("change","#ShipToSelect",function(){
	var curVal = $(this).val();
	var mapJSONObjId = $(".ShipToSel").attr("id");
	var mapJSONObj;
	switch(mapJSONObjId)
	{
		case "View8ShipToSel":
		  mapJSONObj = globalQuoCasPOPartShipTo;
		  break;
		default:
		  mapJSONObj = {};
	}
	var viewId = "view"+mapJSONObjId.replace(/[^\d]+/g,"");
	$("#"+viewId).find(".ShipToTd p span:nth-child(2)").text("");
	for(var k in mapJSONObj){
		var objVal = mapJSONObj[k];
		if(k==curVal){
			for(var kk in objVal){
				var spanClass = "Ship"+kk;
				$("#"+viewId).find(".ShipToTd span."+spanClass).text(objVal[kk]);
			}
		}
	}
	$(".ShipToSel").fadeOut(200);
});

function POForwShipClose(){
	$(".ForwarderSel").fadeOut(200);
	$(".ShipToSel").fadeOut(200);
}

// 复制报价单点击切换
var copy_quotelist_sel = '<select>'+
							'<option value="0">请选择</option>'+
							'<option value="1">复制客户和配置信息</option>'+
							'<option value="2">仅复制客户信息</option>'+
						'</select>';
$(document).on("click", ".copy_quotelist_td>i", function(){
	$(this).parent().empty().append(copy_quotelist_sel);
});
$(document).on("click", ".copy_quotelist_td>select", function(){
	var iindex = $(".copy_quotelist_td").index($(this).parent());
	$(".copy_quotelist_td:not(:eq("+iindex+"))").empty().append('<i class="fa fa-clone" aria-hidden="true"></i>');
});
$(document).on("click", ".copy_quotelist>.fa-reply", function(){
	$(".copy_quotelist_td").empty().append('<i class="fa fa-clone" aria-hidden="true"></i>');
});
$(document).on("change", ".copy_quotelist_td>select", function(){
	var iVal = $(this).val();
	var tr=$(this).parent().parent();
	if(iVal == "0") return false;
	/*公共部分*/
	$(".CustomerInformation, .StaffInformation, .CommodityInformation").hide();
	$('.contract_add').find('input[name="CustomerCode"]').val(tr.find('td').eq(11).text());
	$('.contract_add').find('input[name="CustomerCompany"]').val(tr.find('td').eq(2).text());
	$('.contract_add').find('input[name="CustomerName"]').val(tr.find('td').eq(3).text());
	$('.contract_add').find('input[name="CustomerTel"]').val(tr.find('td').eq(13).text());
	$('.contract_add').find('input[name="CustomerFax"]').val(tr.find('td').eq(14).text());
	$('.contract_add').find('input[name="LeadTime"]').val(tr.find('td').eq(15).text());
	$('.contract_add').find('input[name="Payment"]').val(tr.find('td').eq(16).text());
	$('.contract_add').find('input[name="StaffName"]').val(tr.find('td').eq(17).text());
	$('.contract_add').find('input[name="Department"]').val(tr.find('td').eq(18).text());
	$('.contract_add').find('input[name="ShipmentCost"]').val(tr.find('td').eq(19).text());
	$('.contract_add').find('input[name="DeliveryWay"]').val(tr.find('td').eq(20).text());
	$('.contract_add').find('input[name="ExchangeRate"]').val(tr.find('td').eq(21).text());
	$('.contract_add').find('input[name="Valid"]').val(tr.find('td').eq(22).text());
	$('.contract_add').find('input[name="Currency"]').val(tr.find('td').eq(23).text());
	$('.contract_add').find('input[name="TaxCategories"]').val(tr.find('td').eq(24).text());
	$('.contract_add').find('input[name="Versions"]').val(tr.find('td').eq(25).text());
	$('.contract_add').find('input[name="Datesent"]').val(tr.find('td').eq(26).text());
	// $('.contract_add').find('input[name="Number"]').val(tr.find('td').eq(27).text());
	$('.contract_add').find('input[name="StaffMail"]').val(tr.find('td').eq(28).text());
	$('.contract_add').find('input[name="StaffTel"]').val(tr.find('td').eq(29).text());
	$('.contract_add').find('input[name="CustomerMail"]').val(tr.find('td').eq(30).text());
	// var  ID = tr.find('td.contract-edit').attr("value");
	// $(".contract_update .contract_title").attr("value",ID);
	$(".contract_add .ModelTr").remove();
	$.ajax({
		type: 'get',
		url: 'QuoteCurrentCount',
		data: {},
		dataType: 'json',
		success: function(data) {
			if (data < 10) {
				priceNum = "0" + data;
			} else {
				priceNum = data;
			}
			var staffCode;
			var iname = $(".contract_add input[name='StaffName']").val().trim();
			if(iname == "" || iname == "--"){
				staffCode = "";
			}else{
				staffCode = getLocalStaffCode(iname)
			}
			$(".contract_add #Datesent").val(globalGetToday(false));
			$(".contract_add #Number").val("QU"+staffCode+globalGetToday(false).replace(/\-/g, "")+"-"+priceNum);
			

			if(iVal == "1"){
				var ID = tr.find('td.contract-edit').attr("value");
				$.ajax({
		            type: 'get',
		            url: "QuoteCommodity",
		            data: {
		            	ID: ID,
		            },
		            dataType: 'json',
		            success: function (data) { 
		            	for(var i = 1 ; i < data[0].item.length ; i++){
		            		var tdStr =
			            		'<tr class="ModelTr">'+
			            			'<td value="'+data[0].item[i].Commodity+'">'+data[0].item[i].Description+'</td>'+
			            			'<td class="id" value="'+data[0].item[i].CommodityID+'">'+data[0].item[i].Model+'</td>'+
			            			'<td>'+data[0].item[i].Unit+' </td>'+
			            			'<td>'+data[0].item[i].DeliveryPeriod+'</td>'+
			            			'<td><span contenteditable="true" class="QtySpan">'+data[0].item[i].Qty+'</span></td>'+
			            			'<td>'+data[0].item[i].UnitPrice+'</td>'+
			            			'<td>'+parseFloat(data[0].item[i].UnitPrice*data[0].item[i].Qty).toFixed(2)+'</td>'+
			            			'<td style="display:none;">'+data[0].item[i].CostPrice+'</td>'+ 
			            			'<td style="display:none;"></td>'+
			            			'<td style="display:none;"></td>'+
			            			'<td style="display:none;"></td>'+
			            			/*'<td><input type="button" class="update_del1" style="width:90%;height:90%;" value="删除"></td>'+*/
			            			'<td  class="ModifyOper" >'+
										'<span class="fa fa-long-arrow-up OperUp" ></span>'+
										'<span class="fa fa-trash OperDel Serial_del" ></span>'+
										'<span class="fa fa-long-arrow-down OperDown" ></span>'+
									'</td>'+
			            		'</tr>';
			            		$(".contract_add .tableADD").append(tdStr);
		            	} 	
		            },
		            error: function () {
		                $.MsgBox_Unload.Alert("服务器繁忙提示", "获取配置信息失败！");
		            },
		            complete: function(XMLHttpRequest, textStatus){
		                $('.MailBar_cover_color, .contract_add').slideDown(200);
		            }
			    });
			}else if(iVal == "2"){
				$('.MailBar_cover_color, .contract_add').slideDown(200);
			}

		},
		error: function() {
			$.MsgBox.Alert("服务器繁忙提示", "QuoteCurrentCount，稍后重试！");
		}
	});
	/*公共部分结束*/
});

// RMB配件blur事件
$(document).on("blur","#view3 .EachRMB",function(){
	var oldVal = $(this).attr("value");
	var curVal = $(this).text().replace(/[^\d^\.]+/g,"");
	$(this).text(Math.floor(curVal*100)/100);
	$(this).siblings(".ExtendedRMB").text(Number($(this).text())*Number($(this).siblings(".Qty").text()));
	if($(this).text()!=oldVal){
		var totalNum = 0;
		for(var i = 0; i< $("#view3 .pdf_one_tr").length ; i++){
			totalNum += Math.round($("#view3 .pdf_one_tr").eq(i).find(".ExtendedRMB").text());
		}
		$("#view3 .TotalRMB").text(totalNum).trigger("blur");
	}
	$(this).attr("value",$(this).text());
});
$(document).on("blur","#view3 .Qty",function(){
	var oldVal = $(this).attr("value");
	var curVal = $(this).text().replace(/[^\d]+/g,"");
	if(curVal==""){
		$(this).text("0");
	}else{
		$(this).text(curVal);
	}
	$(this).siblings(".ExtendedRMB").text(Number($(this).text())*Number($(this).siblings(".EachRMB").text()));
	if($(this).text()!=oldVal){
		var totalNum = 0;
		for(var i = 0; i< $("#view3 .pdf_one_tr").length ; i++){
			totalNum += Math.round($("#view3 .pdf_one_tr").eq(i).find(".ExtendedRMB").text());
		}
		$("#view3 .TotalRMB").text(totalNum).trigger("blur");
	}
	$(this).attr("value",$(this).text());
});

// USD配件blur事件
$(document).on("blur","#view4 .EachRMB",function(){
	var oldVal = $(this).attr("value");
	var curVal = $(this).text().replace(/[^\d^\.]+/g,"");
	$(this).text(Math.floor(curVal*100)/100);
	$(this).siblings(".ExtendedUSD").text(Number($(this).text())*Number($(this).siblings(".Qty").text()));
	if($(this).text()!=oldVal){
		var totalNum = 0;
		for(var i = 0; i< $("#view4 .pdf_one_tr").length ; i++){
			totalNum += Math.round($("#view4 .pdf_one_tr").eq(i).find(".ExtendedUSD").text());
		}
		$("#view4 .TotalUSD").text(totalNum).trigger("blur");
	}
	$(this).attr("value",$(this).text());
});
$(document).on("blur","#view4 .Qty",function(){
	var oldVal = $(this).attr("value");
	var curVal = $(this).text().replace(/[^\d]+/g,"");
	if(curVal==""){
		$(this).text("0");
	}else{
		$(this).text(curVal);
	}
	$(this).siblings(".ExtendedUSD").text(Number($(this).text())*Number($(this).siblings(".EachRMB").text()));
	if($(this).text()!=oldVal){
		var totalNum = 0;
		for(var i = 0; i< $("#view4 .pdf_one_tr").length ; i++){
			totalNum += Math.round($("#view4 .pdf_one_tr").eq(i).find(".ExtendedUSD").text());
		}
		$("#view4 .TotalUSD").text(totalNum).trigger("blur");
	}
	$(this).attr("value",$(this).text());
});