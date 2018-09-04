var baseUrl = window.location.href.split("cfChicken8")[0]+"cfChicken8/";
//查询搜索框的内容
if($('input[name="selected"]:checked').val()=='singleSelect'){
    $('.select-content').css('margin-left','33%');
}else{
    $('.select-content').css('margin-left','23%');

}
function Check(selected) {
	 if (selected == "singleSelect") {
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
    // $('input[name="searchContent1"]').val('');
    // $('input[name="searchContent2"]').val('');
    // $('#top_text_from').submit();
    window.location.href = "Invoice";
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


//PDF导出分页  判断封闭
var pdfTitle="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAA0gAAABMCAIAAADY/AAHAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA3FpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNS1jMDE0IDc5LjE1MTQ4MSwgMjAxMy8wMy8xMy0xMjowOToxNSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo5Yjc4MmI1Ni1mODI4LTIzNGItOGMxYy1iY2RhMzMxNmVhOWUiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6OUU5QkI4RkNBRDYzMTFFN0E4RDVFQUY5NTNEQ0QxOEQiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6OUU5QkI4RkJBRDYzMTFFN0E4RDVFQUY5NTNEQ0QxOEQiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIChXaW5kb3dzKSI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOmM5NWJjYWIyLWZkNzMtNjA0ZC05YTEzLWFjODUyN2UzNjg3MyIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo5Yjc4MmI1Ni1mODI4LTIzNGItOGMxYy1iY2RhMzMxNmVhOWUiLz4gPC9y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";
var pdfFoot = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAA0gAAABMCAIAAADY/AAHAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA3FpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNS1jMDE0IDc5LjE1MTQ4MSwgMjAxMy8wMy8xMy0xMjowOToxNSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo5Yjc4MmI1Ni1mODI4LTIzNGItOGMxYy1iY2RhMzMxNmVhOWUiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6MEMxRUE5QTJBRDVGMTFFN0I5MUQ4MjM2MzU5MDkyOTciIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6MEMxRUE5QTFBRDVGMTFFN0I5MUQ4MjM2MzU5MDkyOTciIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIChXaW5kb3dzKSI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOmVhNDkxMDlkLTY2ODEtZjQ0YS1hNDBkLTZkM2M1OTNjOGE0ZSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo5Yjc4MmI1Ni1mODI4LTIzNGItOGMxYy1iY2RhMzMxNmVhOWUiLz4gPC9y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";
 $(".ShowOrHide").on("click",function(){
    var isExist = "true";
	 if($(this).attr("ID") == "exportPDF2"){
			$(".InImg").hide();
			$(".InImg1").hide();
            isExist = "false";
	}
	 
	if($('#invoice_table .itemStr').length ==4){
		$('#invoice_table .itemStr').css("lineHeight","18px");
	}
	else if($('#invoice_table .itemStr').length > 4){
		$('#invoice_table .itemStr').css("lineHeight","27px");
		$('#invoice_table .dpDate').css("lineHeight","23px");
	}
    /*
    var ContractNO = $(".news .ContractNO").text();
    var PONO = $(".news .PONO").text();
    var InvoiceNO = $(".news .InvoiceNO").text();
    var DCNO = $(".news .DCNO").text();
    var Applicant = $(".news .App").text();
    var AddInfo = $(".news .App_Add").text();
    var Tel = $(".news .App_TEL").text();
    var Fax = $(".news .App_FAX").text();
    var EndUser = $(".news .endUser").text();
    var OtherReference = $(".news .reference").text();
    var DepartureDate = $(".news .DepartureDate").text().trim()=="--"?"":$(".news .DepartureDate").text().trim();
    var Vessel = $(".news .Vessel").text();
    var Departure = $(".news .VesselFrom").text();
    var Destination = $(".news .VesselTo").text();
    var PaymentRemark = $(".news .Remark").text();
    var GoodsInfo = [];
    $(".news .itemStr").each(function(){
        var item = {};
        item.Model = $(this).find(".goodsName").text();
        item.Description = $(this).find(".goodsContent").text();
        item.Qty = $(this).find(".Qty").text();
        item.Unit = $(this).find(".Unit").text();
        item.UnitUSDPrice = $(this).find(".Unitprice").text();
        item.TotalUSDAmount = $(this).find(".TotalAmount").text();
        GoodsInfo.push(item);
    });
    var GoodsInfoStr = JSON.stringify(GoodsInfo);
    var Packing = $(".news .Packing").text();
    var Origin = $(".news .Origin").text();
    var Manufacturer = $(".news .Manufacturer").text();
    var ShippingMark = $(".news .Mark").text()+"&&"+$(".news .MarkAdd").text();
    var TotalAmount = $(".news .CIPUSD").text();
    var Date1 = $(".news .invoiceDate").text().trim()=="--"?"":$(".news .invoiceDate").text().trim();
    var AirPort = $(".news .CIPADD").text();
      $.ajax({
            type : 'POST',
            url : 'DownloadInvoice',
            data : {
                ContractNO : ContractNO,
                PONO : PONO,
                InvoiceNO : InvoiceNO,
                DCNO : DCNO,
                Applicant: Applicant,
                AddInfo: AddInfo,
                Tel : Tel,
                Fax : Fax,
                EndUser : EndUser,
                OtherReference : OtherReference,
                DepartureDate : DepartureDate,
                Vessel : Vessel,
                Departure : Departure,
                Destination : Destination,
                PaymentRemark : PaymentRemark,
                GoodsInfo : GoodsInfoStr,
                Packing : Packing,
                Origin : Origin,
                Manufacturer : Manufacturer,
                ShippingMark : ShippingMark,
                TotalAmount : TotalAmount,
                Date : Date1,
                AirPort: AirPort,
                isExist: isExist
            },
            beforeSend: function(XMLHttpRequest){
                $.Response_Load.Before("导出提示","正在传输数据......",200);
            },
            complete: function(XMLHttpRequest, textStatus){
                if(textStatus=='success'){
                    $.Response_Load.After("导出完成！",300);
                }else if(textStatus=='error'){
                    $.Response_Load.After("导出失败！",300);
                }else if(textStatus=='timeout'){
                    var xmlhttp = window.XMLHttpRequest ? new window.XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHttp");  
                    xmlhttp.abort();
                    $.Response_Load.After("连接超时！",300);
                }
            },
            success: function (data) {
                console.log(data);
                window.open(baseUrl+data);
            },
            error: function () {
                $.MsgBox_Unload.Alert("提示", "服务器繁忙，请稍后重试！");
            }
        });
    */
	var InvoiceNO = $(".news .InvoiceNO").text();
	var target = document.getElementById("invoice_table");
	var width = $('#invoice_table').width() ; 
	var height =  $('#invoice_table').height() ;
    
	var canvas = document.createElement("canvas");  
	    canvas.width = width * 2.2;  
	    canvas.height = height * 2.1;  
	    canvas.style.width = width + "px";  
	    canvas.style.height = height + "px";  
	var context = canvas.getContext("2d");//然后将画布缩放，将图像放大两倍画到画布上  
	    context.scale(2,2);   
	    html2canvas(target, {
			canvas:canvas,
			  onrendered:function(canvas) {

			      var contentWidth = canvas.width;
			      var contentHeight = canvas.height;

			      //一页pdf显示html页面生成的canvas高度;
			      var pageHeight = contentWidth / 592.28 *650;
			      //未生成pdf的html页面高度
			      var leftHeight = contentHeight;
			      //页面偏移
			      var position = 0;
			      //a4纸的尺寸[595.28,841.89]，html页面生成的canvas在pdf中图片的宽高
			      var imgWidth = 595.28;
			      var imgHeight = 600/contentWidth * contentHeight;
			      console.log(contentHeight);
			      console.log(contentWidth);
				console.log(imgHeight);
			      var pageData = canvas.toDataURL('image/png', 1.0);
			      var pdf = new jsPDF('', 'pt', 'a4');

			      //有两个高度需要区分，一个是html页面的实际高度，和生成pdf的页面高度(841.89)
			      //当内容未超过pdf一页显示的范围，无需分页
			      if (leftHeight < pageHeight) {
			    	  pdf.addImage(pageData, 'PNG', 0,position+80, 600, imgHeight);
			          pdf.addImage(pdfTitle, 'JPG', 0, 0, 600,70);  
			          pdf.addImage(pdfFoot, 'JPG', 0, 781.89, 600,60);  
			      } 
			      else {
			          while(leftHeight > 0) {
			        		  pdf.addImage(pageData, 'PNG', 0,position+80, 600, imgHeight);
	    			          pdf.addImage(pdfTitle, 'JPG', 0, 0, 600,70); 
	    			          pdf.addImage(pdfFoot, 'JPG', 0, 781.89, 600,60);  
				              leftHeight -= pageHeight;
				              position -= 715;
			        	 
			              //避免添加空白页
			              if(leftHeight > 0) {
			                pdf.addPage();
			              }
			          }
			      }

			      pdf.save('Invoice:'+InvoiceNO+'.pdf');
			      $(".InImg").show();
			  	  $(".InImg1").show();
			  }
		});
});



