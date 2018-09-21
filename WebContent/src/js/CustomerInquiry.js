
function Search() {
    $('#search_box').submit();
}
//页面表格操作

//发起服务
$(document).on("click",".InitiateService",function(){
	if($(this).text() == "未发起"){
		var that = $(this);
		var ID=  that.parent().find(".toggleedit").attr("value");
		$.MsgBox.Confirm("提示", "确认发起服务?",function(){
			$.ajax({
		        type : 'POST',
		        url : 'CustomerInquiryPreview',
		        data : {
		        	ID:ID,
		        },
		        dataType : 'json',
		        success : function (data) {  
		        	console.log(data);          
		        },
		        error : function () {
		            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
		        }
			});
		})
	}
})

// //报价预览
// $(document).on("click",".PreviewTd",function(){
// 	$(".PreviewBox").show();
// 	var ID = $(this).parent().find(".toggleedit").attr("value");
// 	var that = $(this);
// 	$.ajax({
//         type : 'GET',
//         url : 'CustomerInquiryPreview',
//         data : {
//         	ID:ID,
//         	classify:"Preview"
//         },
//         dataType : 'json',
//         success : function (data) {            
//              console.log(data);
//              $(".PreviewBox  .PreviewContent").remove();
//              var modelList ="";
//      		 var remarkList ="";
//      		 var priceList="";
//      		 var quantityList="";
//      		 var totalPrice = 0;
// 	        	for(var i = 1 ; i < data.length;i++ ){
// 	        		modelList+='<li ID="'+data[i].ID+'">'+data[i].Model+'</li>';
// 	        		remarkList+='<li>'+data[i].Remarks+'</li>';
// 	        		priceList+='<li>'+data[i].Price+'</li>';
// 	        		quantityList+='<li>'+data[i].Quantity+'</li>';
// 	        		totalPrice += parseFloat(data[i].Price.replace(",",""))*parseFloat(data[i].Quantity);
// 	        	} 
// 	        	var str ='<tr class="PreviewContent">'+
// 				'<td class=""><span >1</span></td>'+
// 				'<td >'+that.parent().find(".tbody_CustomerName").text()+'</td>'+
// 				'<td>'+
// 					'<ul>'+modelList+'</ul>'+
// 				'</td>'+
// 				'<td>'+
// 					'<ul>'+remarkList+'</ul>'+
// 				'</td>'+
// 				'<td style="display:none;">'+
// 					'<ul>'+priceList+'</ul>'+
// 				'</td>'+
// 				'<td>'+
// 					'<ul>'+quantityList+'</ul>'+
// 				'</td>'+
// 				'<td >'+totalPrice+'</td>'+
// 				'<td >'+that.parent().find(".tbody_Type").text()+'</td>'+
// 				'<td >'+that.parent().find(".tbody_QuoteTime").text()+'</td>'+
// 			'</tr>';
// 	        	$(".PreviewBox  #ShipnoticeTable").append(str);
	        	
//         },
//         error : function () {
//             $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
//         }
//     });
// 	$(".MailBar_cover_color").css("min-height",$(window).height()+30);
// 	$(".MailBar_cover_color").css("height",$(".ShipnoticeBox").height()+130);
// 	$(".MailBar_cover_color").show();	
// })

//报价预览
$(document).on("click",".PreviewTd",function(){
	$(".PreviewBox").show();
	var ID = $(this).parent().find(".toggleedit").attr("value");
	var that = $(this);
//	$.ajax({
//        type : 'GET',
//        url : 'CustomerInquiryPreview',
//        data : {
//        	ID:ID,
//        	classify:"Preview"
//        },
//        dataType : 'json',
//        success : function (data) {            
//             console.log(data);
//             $(".PreviewBox  .PreviewContent").remove();
//             var modelList ="";
//     		 var remarkList ="";
//     		 var priceList="";
//     		 var quantityList="";
//     		 var totalPrice = 0;
//	        	for(var i = 1 ; i < data.length;i++ ){
//	        		modelList+='<li ID="'+data[i].ID+'">'+data[i].Model+'</li>';
//	        		remarkList+='<li>'+data[i].Remarks+'</li>';
//	        		priceList+='<li>'+data[i].Price+'</li>';
//	        		quantityList+='<li>'+data[i].Quantity+'</li>';
//	        		totalPrice += parseFloat(data[i].Price.replace(",",""))*parseFloat(data[i].Quantity);
//	        	} 
//	        	var str ='<tr class="PreviewContent">'+
//				'<td class=""><span >1</span></td>'+
//				'<td >'+that.parent().find(".tbody_CustomerName").text()+'</td>'+
//				'<td>'+
//					'<ul>'+modelList+'</ul>'+
//				'</td>'+
//				'<td>'+
//					'<ul>'+remarkList+'</ul>'+
//				'</td>'+
//				'<td style="display:none;">'+
//					'<ul>'+priceList+'</ul>'+
//				'</td>'+
//				'<td>'+
//					'<ul>'+quantityList+'</ul>'+
//				'</td>'+
//				'<td >'+totalPrice+'</td>'+
//				'<td >'+that.parent().find(".tbody_Type").text()+'</td>'+
//				'<td >'+that.parent().find(".tbody_QuoteTime").text()+'</td>'+
//			'</tr>';
//	        	$(".PreviewBox  #ShipnoticeTable").append(str);
//	        	
//        },
//        error : function () {
//            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
//        }
//    });
	$.ajax({
        type : 'get',
        url : 'CustomerInquiryPreview',
        data : {
        	ID : ID,
        	classify:"Modify"
        },
        dataType : 'json',
        success : function (data) {
        	console.log(data)
        	 $(".PreviewBox  .PreviewContent").remove();
        	 var modelList ="";
     		 var remarkList ="";
     		 var priceList="";
     		 var quantityList="";
     		var totalPrice = 0;
        	for(var i = 1 ; i < data.length;i++ ){
        		if(data[i].IsMain =="yes"){    //有序号
	        		modelList+='<li ID="'+data[i].ID+'">'+data[i].Model+'</li>';
	        		remarkList+='<li>'+data[i].Remarks+'</li>';
	        		priceList+='<li>'+data[i].Price+'</li>';
	        		quantityList+='<li>'+data[i].Quantity+'</li>';
        		}
        		totalPrice += parseFloat(data[i].Price.replace(",",""))*parseFloat(data[i].Quantity); 
        	} 
        	var str ='<tr class="PreviewContent">'+
			'<td class=""><span >1</span></td>'+
			'<td >'+that.parent().find(".tbody_CustomerName").text()+'</td>'+
			'<td>'+
				'<ul>'+modelList+'</ul>'+
			'</td>'+
			'<td>'+
				'<ul>'+remarkList+'</ul>'+
			'</td>'+
			'<td style="display:none;">'+
				'<ul>'+priceList+'</ul>'+
			'</td>'+
			'<td>'+
				'<ul>'+quantityList+'</ul>'+
			'</td>'+
			'<td >'+totalPrice+'</td>'+
			'<td >'+that.parent().find(".tbody_Type").text()+'</td>'+
			'<td >'+that.parent().find(".tbody_QuoteTime").text()+'</td>'+
		'</tr>';
        	$(".PreviewBox  #ShipnoticeTable").append(str);
        },
        error : function () {
        	  $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
        }
    }); 
	$(".MailBar_cover_color").css("min-height",$(window).height()+30);
	$(".MailBar_cover_color").css("height",$(".ShipnoticeBox").height()+130);
	$(".MailBar_cover_color").show();	
})

//添加   修改  客户单位   检索
$(".add_Customer,.modify_Customer").on("blur",function(){
	var curVal = $(this).val();
	if($(this).is('.addHook')){
    	if(curVal==""|| curVal.trim() ==""){
    		$(".ShipnoticeBox input[name='CustomerName'], .ShipnoticeBox .callnum").val("");
    		$(this).val("");
    		$(this).removeAttr("id");
    	}
    }
    else{
    	if(curVal==""|| curVal.trim() ==""){
    		$(".InquiryModifyBox input[name='CustomerName'], .InquiryModifyBox .callnum").val("");
    		$(this).val("");
    		$(this).removeAttr("id");
    	}
    }
});

$(".add_Customer,.modify_Customer").on("input propertychange",function(){
	var Number = $(this).val();
	var that = $(this);
	$.ajax({
        type : 'POST',
        url : 'CustomerInquiry',
        data : {
        	Content:Number,
        	classify:"Customer"
        },
        dataType : 'json',
        success : function (data) {            
             console.log(data);
             var str = "";
             for(var i = 1 ; i < data.length ; i++){
            	 str += "<li ID="+data[i].ID+" ContactInfo1='"+data[i].ContactInfo1+"'  Contact='"+data[i].Contact+"' class='addListLi'>"+data[i].CustomerName+"&nbsp;:&nbsp;"+data[i].Contact+"</li>";
             }
             if(that.is('.addHook')){
            	 $(".ShipnoticeBox .Customerlist").empty();
            	 $(".ShipnoticeBox .Customerlist").append(str);
            	 $(".ShipnoticeBox .Customerlist").show();
             }
             else{
            	 $(".InquiryModifyBox .Customerlist").empty();
            	 $(".InquiryModifyBox .Customerlist").append(str);
            	 $(".InquiryModifyBox .Customerlist").show();
             }
        },
        error : function () {
            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
	$(".Customerlist").show();
});


//询价型号  检索
$(document).on("input propertychange",".ShipnoticeBox .ModelInput,.InquiryModifyBox .ModelInput,.AddModelInput",function(){
	var Number = $(this).val();
	var that  = $(this);
	$.ajax({
        type : 'POST',
        url : 'CustomerInquiry',
        data : {
        	Content:Number,
        	classify:"SoftwareProduct"
        },
        dataType : 'json',
        success : function (data) {            
             console.log(data);
             var str = "";
             for(var i = 1 ; i < data.length ; i++){
            	 str += "<li ID="+data[i].ID+" PackageName='"+data[i].PackageName+"' Price='"+data[i].Price+"' Remarks='"+data[i].Remarks+"' Model='"+data[i].Model+"' class='addModelList'>"+data[i].Model+"</li>";
             }
             $(".ModelList,.ModelListUl").empty();
             if(that.attr("class") == "AddModelInput" ){
            	 that.next().append(str);
            	 that.next().show();
             }
             else{
            	 $(".ModelList").append(str);
            	 $(".ModelList").show();
             }
        },
        error : function () {
            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});
//修改、添加中的组件 型号检索
$(document).on("input propertychange",".AddPackageNameInput,.AddRemarksInput",function(){
	var Number = $(this).val();
	var that  = $(this);
	if(that.attr("class") == "AddPackageNameInput"){
		var column = "PackageName"
	}
	else{
		var column = "Remarks"
	}
	$.ajax({
        type : 'POST',
        url : 'CustomerInquiry',
        data : {
        	Content:Number,
        	classify:"SoftwareProduct",
        	column :column
        },
        dataType : 'json',
        success : function (data) {            
             console.log(data);
             var str = "";
             for(var i = 1 ; i < data.length ; i++){
            	 if(that.attr("class") == "AddPackageNameInput"){
        			var content = data[i].PackageName
        		}
        		else{
        			var content = data[i].Remarks
        		}
				str += "<li ID="+data[i].ID+" PackageName='"+data[i].PackageName+"' Price='"+data[i].Price+"' Remarks='"+data[i].Remarks+"' Model='"+data[i].Model+"' class='addModelList'>"+conten+"</li>";
            }
             $(".PackageNameListUl,.RemarksListUl").empty();
        	 that.next().append(str);
        	 that.next().show();
        },
        error : function () {
            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});


$(document).on("click",function(){
	$(".Customerlist").hide();
	$(".addModelList").hide();
	$(".ModelListUl").hide();
})
//客户信息自动关联
$(document).on("click",".addListLi",function(){
	var currentCont = $(this).text();
	var ID= $(this).attr("ID");
	$(this).parent().prev().val(currentCont);
	$(this).parent().prev().attr("ID",ID);
	$(this).parent().parent().parent().next().find('input[name="CustomerName"]').val($(this).attr("Contact"));
	$(this).parent().parent().parent().next().next().find('.callnum').val($(this).attr("ContactInfo1"));
	$(".Customerlist").hide();
})

//询价型号 
$(document).on("click",".ModelList .addModelList",function(){
	var currentCont = $(this).text();
	var that = $(this).parent().parent().find(".ModelInput");
	that.val(currentCont);
	that.attr("ID",$(this).attr("ID"));
	that.attr("PackageName",$(this).attr("PackageName"));
	that.attr("Price",$(this).attr("Price"));
	that.attr("Remarks",$(this).attr("Remarks"));
	
	$(".addModelList").hide();
})

//表格   型号   
$(document).on("click",".ModelListUl .addModelList",function(){
	var currentCont = $(this).text();
	var that = $(this).parent().parent();
	that.find(".AddModelInput").val(currentCont);
	that.parent().attr("ID",$(this).attr("ID"));
	that.prev().text($(this).attr("PackageName"));
	that.next().text($(this).attr("Remarks"));
	that.next().next().text($(this).attr("Price"));
	$(".ModelListUl").hide();
})

$(document).on("click",".PackageNameListUl .addModelList",function(){
	var currentCont = $(this).text();
	var that = $(this).parent().parent();
	that.find(".AddPackageNameInput").val(currentCont);                                   
	that.parent().attr("ID",$(this).attr("ID"));
	that.next().text($(this).attr("Model"));
	that.next().next().text($(this).attr("Remarks"));
	that.next().next().next().text($(this).attr("Price"));
	$(".PackageNameListUl").hide();
})

$(document).on("click",".RemarksListUl .addModelList",function(){
	var currentCont = $(this).text();
	var that = $(this).parent().parent();
	that.find(".AddRemarksInput").val(currentCont);
	that.parent().attr("ID",$(this).attr("ID"));
	that.prev().text($(this).attr("Model"));
	that.prev().prev().text($(this).attr("PackageName"));
	that.next().text($(this).attr("Price"));
	$(".RemarksListUl").hide();
})
var Serial = 0;
//询价型号   添加
$(document).on("click",".add_Model",function(){
	var that = $(this).parent().parent().find(".ModelInput");
	var Model = that.val();
	var ID = that.attr("ID");
	if(ID){
		var PackageName = that.attr("PackageName");
		var Price = that.attr("Price");
		var Remarks = that.attr("Remarks");
		Serial = $(this).parents(".ShipnoticeMsg").find("#ShipnoticeTable .haveSerial").length;
		Serial++;
		var str='<tbody class="nohead haveSer'+Serial+'"><tr class="SerAddTR" ID='+ID+'>'+
			'<td class="Shipnotice_Ser"><span class="fa fa-plus-square-o SerAdd" ></span><span class="SerText haveSerial">'+Serial+'</span></td>'+
			'<td class="ADD_PackageName" style="height:30px;line-height:30px;outline:none;">'+PackageName+'</td>'+
			'<td class="ADD_Model" style="height:30px;line-height:30px;outline:none;">'+Model+'</td>'+
			'<td class="ADD_Remarks" style="height:30px;line-height:30px;outline:none;">'+Remarks+'</td>'+
			'<td style="outline:none;" class="ADD_Price"  contenteditable="true" >'+Price+'</td>'+
			'<td style="height:30px;line-height:30px;outline:none;" class="ADD_Quantity"><input  type="number" min="0" value="1" class="QuantityInput" style="width: 100%;height:30px;line-height:30px;font-size:17px;border: none;"></td>'+
			'<td   class="Shipnotice_Oper" contenteditable="true">'+
				'<span class="fa fa-long-arrow-up OperUp" ></span>'+
				'<span class="fa fa-institution OperDel Serial_del" ></span>'+
				'<span class="fa fa-long-arrow-down OperDown" ></span>'+
				'</td>'+
		'</tr></tbody>'
		$(this).parents(".ShipnoticeMsg").find("#ShipnoticeTable").append(str);
	}
	else{
		$.MsgBox_Unload.Alert("提示", "请选择询价型号！");
	}
})

//点修改功能时
 $(document).on("click",".chooseBtn",function(){
		var tr=$(this).parent();
		$('.ShipnoticeBox input[type="text"]').val("");
		$(".add_fileCont").text(""); 
		$(".ShipnoticeBox .SerAddTR").remove();
	   $('.MailBar_cover_color').show();
	   $('.ShipnoticeBox').show();
	 }); 

 
//添加  提交
$(document).on("click","#Shipnotice_submit",function(){
	var Len = $(".ShipnoticeBox #ShipnoticeTable tr.SerAddTR").length;
	if(Len == 0){
		$.MsgBox_Unload.Alert("提示", "请至少添加一条记录！");
		return;
	}
	var CustomerID = $('.ShipnoticeBox .add_Customer').attr("ID");
	var PreSalesTable = $('.ShipnoticeBox .add_fileCont').text();
	var TotalPrice = 0;
	
	var  RecordID = [];   //型号记录的ID
	var  SequenceNO = [];  //序号
	var  Quantity = [];  //数量
	var  ProductID = []; //产品管理型号信息的ID
	var  IsMain = [];  //序号标志
	
	var serFlag = 0;
	for(var i = 1 ; i < $('.ShipnoticeBox #ShipnoticeTable tr').length ; i++){
		var thisTr =$('.ShipnoticeBox #ShipnoticeTable tr').eq(i); 
		if(thisTr.find(".SerText").text() == ""){    //无序号
			IsMain.push("");
		}
		else{					//有序号
			IsMain.push("yes");
			serFlag = thisTr.find(".SerText").text();
		}
		TotalPrice +=  parseFloat(thisTr.find(".ADD_Price").text().replace(",","")) *thisTr.find(".QuantityInput").val();
		SequenceNO.push(serFlag);
		Quantity.push(thisTr.find(".QuantityInput").val());
		ProductID.push(thisTr.attr("ID"));
		RecordID.push(0);
	}
	TotalPrice = TotalPrice.toFixed(2);
	   $.ajax({
	        type : 'get',
	        url : 'CustomerInquiryOperate',
	        data : {
	        	ID : 0,
	        	CustomerID : CustomerID,
	        	PreSalesTable : PreSalesTable,
	        	TotalPrice : TotalPrice,
	        	RecordID : RecordID,
	        	SequenceNO : SequenceNO,
	        	Quantity : Quantity,
	        	ProductID: ProductID,
	        	IsMain : IsMain
	        },
	        success : function (data) {
	        	console.log(data);
	        	if(data.indexOf("添加成功")>-1){
	        		$.MsgBox.Alert('提示',data,function(){
		            	$('.MailBar_cover_color').hide();
				        $('.ShipnoticeBox').hide();
		            });
	        	}else{
	        		$.MsgBox_Unload.Alert("提示",data);
	        	}
	        },
	        error : function () {
	            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    }); 
	    	    
   });

	//修改  提交
	$('#Modify_submit').click(function () {
		var Len = $(".InquiryModifyBox #ShipnoticeTable tr.SerAddTR").length;
		if(Len == 0){
			$.MsgBox_Unload.Alert("提示", "请至少添加一条记录！");
			return;
		}
		var CustomerID = $('.InquiryModifyBox .modify_Customer').attr("ID");
		var PreSalesTable = $('.InquiryModifyBox .add_fileCont').text();
		var TotalPrice = 0;
		
		var  RecordID = [];   //型号记录的ID
		var  SequenceNO = [];  //序号
		var  Quantity = [];  //数量
		var  ProductID = []; //产品管理型号信息的ID
		var  IsMain = [];  //序号标志
		
		var serFlag = 0;
		for(var i = 1 ; i < $('.InquiryModifyBox #ShipnoticeTable tr').length ; i++){
			var thisTr =$('.InquiryModifyBox #ShipnoticeTable tr').eq(i); 
			if(thisTr.find(".SerText").text() == ""){    //无序号
				IsMain.push("");
			}
			else{					//有序号
				IsMain.push("yes");
				serFlag = thisTr.find(".SerText").text();
			}
			if(thisTr.attr("RecordID")){    //无序号
				RecordID.push(thisTr.attr("RecordID"));
			}
			else{
				RecordID.push(0);
			}
			TotalPrice +=  parseFloat(thisTr.find(".ADD_Price").text().replace(",","")) *thisTr.find(".QuantityInput").val();
			SequenceNO.push(serFlag);
			Quantity.push(thisTr.find(".QuantityInput").val());
			ProductID.push(thisTr.attr("ID"));
		}
		TotalPrice = TotalPrice.toFixed(2);
	   $.ajax({
	        type : 'get',
	        url : 'CustomerInquiryOperate',
	        data : {
	        	ID : $(".InquiryModifyBox").attr('ID'),
	        	CustomerID : CustomerID,
	        	PreSalesTable : PreSalesTable,
	        	TotalPrice : TotalPrice,
	        	RecordID : RecordID,
	        	SequenceNO : SequenceNO,
	        	Quantity : Quantity,
	        	ProductID: ProductID,
	        	IsMain : IsMain
	        },
	        success : function (data) {
	        	console.log(data);
	        	if(data.indexOf("修改成功")>-1){
		        	$.MsgBox.Alert('提示',data,function(){
	            	    $('.MailBar_cover_color').hide();
			            $('.InquiryModifyBox').hide();
		            });
		        }else{
		        	$.MsgBox_Unload.Alert("提示",data);
		        }
	        },
	        error : function () {
	            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    }); 
	})  

 	$(document).on("click",".contract-edit",function(){
		var ID = $(this).attr('value');
		$(".InquiryModifyBox").attr('ID',ID);
		var thisTr =  $(this).parent();
		$(".InquiryModifyBox .modify_Customer").val(thisTr.find(".tbody_CustomerName").text());
		$(".InquiryModifyBox .modify_Customer").attr("ID",thisTr.find(".tbody_CustomerID").text());
		$(".InquiryModifyBox input[name='CustomerName']").val(thisTr.find(".tbody_Contact").text());
		$(".InquiryModifyBox .callnum").val(thisTr.find(".tbody_ContactInfo1").text());
		$(".InquiryModifyBox .add_fileCont").text(thisTr.find(".tbody_PreSalesTable").text());
		$(".InquiryModifyBox .ModelInput").val("");
		
 		$.ajax({
	        type : 'get',
	        url : 'CustomerInquiryPreview',
	        data : {
	        	ID : ID,
	        	classify:"Modify"
	        },
	        dataType : 'json',
	        success : function (data) {
	        	console.log(data)
	        	$(".InquiryModifyBox  #ShipnoticeTable tbody.nohead").remove();
	        	var SequenceNOMark ;
	        	for(var i = 1 ; i < data.length;i++ ){
	        		if(data[i].IsMain =="yes"){    //有序号
	        			SequenceNOMark = data[i].SequenceNO;
	        			var str='<tbody  class="nohead haveSer'+data[i].SequenceNO+'"><tr class="SerAddTR" RecordID="'+data[i].ID+'" ID="'+data[i].ProductID+'">'+
		        			'<td class="Shipnotice_Ser"><span class="fa fa-plus-square-o SerAdd" ></span><span class="SerText haveSerial">'+data[i].SequenceNO+'</span></td>'+
		        			'<td class="ADD_PackageName" style="height:30px;line-height:30px;outline:none;">'+data[i].PackageName+'</td>'+
			    			'<td class="ADD_Model" style="height:30px;line-height:30px;outline:none;">'+data[i].Model+'</td>'+
			    			'<td class="ADD_Remarks" style="height:30px;line-height:30px;outline:none;">'+data[i].Remarks+'</td>'+
			    			'<td style="outline:none;" class="ADD_Price"  contenteditable="true" >'+data[i].Price+'</td>'+
			    			'<td style="height:30px;line-height:30px;outline:none;" class="ADD_Quantity"><input  type="number" min="0" value="'+data[i].Quantity+'" class="QuantityInput" style="width: 100%;height:30px;line-height:30px;font-size:17px;border: none;"></td>'+
			    			'<td   class="Shipnotice_Oper" contenteditable="true"><span class="fa fa-long-arrow-up OperUp" ></span>'+
			    			'	<span class="fa fa-institution OperDel Serial_del" ></span>'+
			    			'	<span class="fa fa-long-arrow-down OperDown" ></span></span></td>'+
		        		'</tr></tbody>';
	        			$(".InquiryModifyBox .ShipnoticeMsg #ShipnoticeTable").append(str);
	        		}
	        		else{
	        			var str='<tr class="SerAddTR" RecordID="'+data[i].ID+'" ID="'+data[i].ProductID+'">'+
		        			'<td class="Shipnotice_Ser"><span class="fa fa-plus-square-o SerAdd noSerial" ></span></td>'+
		        			'<td class="ADD_PackageName" style="height:30px;line-height:30px;outline:none;">'+data[i].PackageName+'</td>'+
			    			'<td class="ADD_Model" style="height:30px;line-height:30px;outline:none;">'+data[i].Model+'</td>'+
			    			'<td class="ADD_Remarks" style="height:30px;line-height:30px;outline:none;">'+data[i].Remarks+'</td>'+
			    			'<td style="outline:none;" class="ADD_Price"  contenteditable="true" >'+data[i].Price+'</td>'+
			    			'<td style="height:30px;line-height:30px;outline:none;" class="ADD_Quantity"><input  type="number" min="0" value="'+data[i].Quantity+'" class="QuantityInput" style="width: 100%;height:30px;line-height:30px;font-size:17px;border: none;"></td>'+
			    			'<td   class="Shipnotice_Oper" contenteditable="true"><span class="fa fa-institution OperDel" ></span></td>'+
		        		'</tr>'
			    			$(".InquiryModifyBox .ShipnoticeMsg #ShipnoticeTable").find("tbody.haveSer"+SequenceNOMark).append(str);
	        		}
	        	} 
	        },
	        error : function () {
	            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！",function(){
	            	 $('.MailBar_cover_color').hide();
			          $('.contract_add').hide();
	            });
	        }
	    }); 
		
		$(".MailBar_cover_color").css("min-height",$(window).height()+30);
		$(".MailBar_cover_color").css("height",$(".InquiryModifyBox").height()+130);
		$(".InquiryModifyBox").show();
		$(".MailBar_cover_color").show();	
	})
		//模态框  关闭
		$('.Shipnotice_close,#Shipnotice_cancel,.PreviewBox_close,#Modify_cancel').click(function() {
			$('.MailBar_cover_color').hide();
			$(".ShipnoticeBox").hide();
			$(".PreviewBox").hide();
			$(".InquiryModifyBox").hide();
		});
	    $(document).on("mouseover",".Shipnotice_Ser",function(){
	    	$(this).find(".SerAdd").toggle();
	    	$(this).find(".SerText").css("marginLeft","4px");
	    })
	    $(document).on("mouseout",".Shipnotice_Ser",function(){
	    	$(this).find(".SerAdd").toggle();
	    	$(this).find(".SerText").css("marginLeft","0px");
	    })
	    
	    $(document).on("click",".SerAdd",function(){
	    	var str='<tr class="SerAddTR">'+
			    	'<td class="Shipnotice_Ser"><span class="fa fa-plus-square-o SerAdd" ></span><span class="SerText noSerial"></span></td>'+
					'<td class="ADD_PackageName" style="height:30px;line-height:30px;outline:none;"   >'+
					'<input  type="text" class="AddPackageNameInput" style="width: 100%;height:30px;line-height:30px;font-size:17px;border: none;text-align: center;"><ul class="PackageNameListUl"></ul>'+
					'</td>'+
					'<td class="ADD_Model" style="height:30px;line-height:30px;outline:none;" >'+
					'<input  type="text" class="AddModelInput" style="width: 100%;height:30px;line-height:30px;font-size:17px;border: none;text-align: center;"><ul class="ModelListUl"></ul>'+
					'</td>'+
					'<td class="ADD_Remarks" style="height:30px;line-height:30px;outline:none;"  >'+
					'<input  type="text" class="AddRemarksInput" style="width: 100%;height:30px;line-height:30px;font-size:17px;border: none;text-align: center;"><ul class="RemarksListUl"></ul>'+
					'</td>'+
					'<td style="outline:none;" class="ADD_Price"  ></td>'+
					'<td style="height:30px;line-height:30px;outline:none;" class="ADD_Quantity"><input  type="number" min="0"  value="1"  class="QuantityInput" style="width: 100%;height:30px;line-height:30px;font-size:17px;border: none;"></td>'+
					'<td   class="Shipnotice_Oper" contenteditable="true">'+
					'<span class="fa fa-institution OperDel" ></span>'+
					'</td>'+
				'</tr>'
			$(this).parents("tbody").append(str);
	    })
	    $(document).on("click",".OperDel",function(){
	    	if($(this).hasClass("Serial_del")){  //带序号 删除
	    		Serial = $(this).parents(".Shipnotice_TabBox").find("tbody.nohead").length;
		    	Serial --;
		    	 $(this).parent().parent().parent().remove();
	    	}
	    	else{              //不带序号 删除
		    	$(this).parent().parent().remove();
	    	}
	    	for(var i = 0 ; i < $(".nohead").length ; i++){
	    		$(".nohead").eq(i).find(".haveSerial").text(i+1);
	    	}
	    })
		$(document).on("click",".OperUp",function(){
            var prevDom = $(this).parents("tbody.nohead").prev().attr("class");
            if(prevDom.indexOf("nohead") >= 0){
            	console.log("."+prevDom.split(" ")[1])
            	$(this).parents("tbody.nohead").insertBefore("."+prevDom.split(" ")[1]);
            }
            for(var i = 0 ; i < $(".nohead").length ; i++){
 	    		$(".nohead").eq(i).find(".haveSerial").text(i+1);
 	    	}
        });
        $(document).on("click",".OperDown",function(){
             
             var prevDom = $(this).parents("tbody.nohead").next().attr("class");
             if(prevDom.indexOf("nohead") >= 0){
             	$(this).parents("tbody.nohead").insertAfter("."+prevDom.split(" ")[1]);
             }
             for(var i = 0 ; i < $(".nohead").length ; i++){
  	    		$(".nohead").eq(i).find(".haveSerial").text(i+1);
  	    	}
        });
		
        
     // 售前调查表     上传
        $(document).on("change",".ShipnoticeBox .add_file",function(){
            var filePath=$(this).val().replace("C:\\fakepath\\","");
        	$(".ShipnoticeBox .add_fileCont").html(filePath);
        	$("#add_fileBox1").submit();
        })
         $(document).on("change",".InquiryModifyBox .add_file",function(){
            var filePath=$(this).val().replace("C:\\fakepath\\","");
        	$(".InquiryModifyBox .add_fileCont").html(filePath);
        	$("#add_fileBox2").submit();
        })
        
        $(".exportBtn").click(function(){
        	$.ajax({
    	        type : 'get',
    	        url : 'CustomerInquiryExport',
    	        success : function (data) {
    	        	console.log(data)
    	        window.location.href = data;
    	        },
    	        error : function () {
    	            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！",function(){
    	            	 $('.MailBar_cover_color').hide();
    	            });
    	        }
        	})
        })

        // 页面表格    下载文件
        $(document).on("click",".tbody_PreSalesTable",function(){
        	if($(this).text() != "" && $(this).text() != "--"){
        		var ID = $(this).parent().find(".toggleedit").attr("value");
            	console.log(ID)
            	$.ajax({
        	        type : 'post',
        	        url : 'CustomerInquiryExport',
        	        data : {
        	        	ID:ID
        	        },
        	        success : function (data) {
        	        	console.log(data)
        	        	window.location.href = data;
        	        },
        	        error : function () {
        	            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！",function(){
        	            	 $('.MailBar_cover_color').hide();
        	            });
        	        }
            	})
        	}
        })
        
        
        