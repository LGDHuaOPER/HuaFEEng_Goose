<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html  lang="en">
<head>
<meta charset="UTF-8">
<title>苏州伊欧陆系统集成有限公司</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="css/doc_make.css">
<link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" />
<link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">
</head>
<body>
	<%@include file="top.jsp"%>
	<div class="contain">
		<div class="content">
		<!-- 	=======================导航栏   开始 ================================== -->
			<div class="nav">
				<ul>
					<c:forEach var="authoritiy" items="${authorities }">
						<c:if test="${authoritiy=='Transport' }">
							<li><a href="Transport?ActualDelivery=no&column=DateOfSign&condition=All">物流页面</a></li>
						</c:if>
					</c:forEach>
					<c:forEach var="authoritiy" items="${authorities }">
						<c:if test="${authoritiy=='Price' }">
							<li><a href="Price?ActualDelivery=no&column=DateOfSign&condition=All">合同统计</a></li>
						</c:if>
					</c:forEach>
					<c:forEach var="authoritiy" items="${authorities }">
						<c:if test="${authoritiy=='Equipment' || authoritiy=='Customer'}">
							<c:set var="ziliaoku" value="1"></c:set>
						</c:if>
					</c:forEach>

					<c:if test="${ziliaoku==1 }">
						<li class="information"><a href="Equipment" >资料库</a>
							<ul class="info_list">
								<c:forEach var="authoritiy" items="${authorities }">
									<c:if test="${authoritiy=='Equipment' }">
										<li><a href="Equipment">产品型号表</a></li>
									</c:if>
								</c:forEach>

								<c:forEach var="authoritiy" items="${authorities }">
									<c:if test="${authoritiy=='Customer' }">
										<li><a href="Customer">客户信息表</a></li>
									</c:if>
								</c:forEach>
								<c:forEach var="authoritiy" items="${authorities }">
									<c:if test="${authoritiy=='Supplier' }">
										<li><a href="Supplier">供应商管理</a></li>
									</c:if>
								</c:forEach>
							</ul></li>
					</c:if>
					<c:forEach var="authoritiy" items="${authorities }">
									<c:if test="${authoritiy=='Requirement' }">
										<li><a href="Requirement">需求录入</a></li>
									</c:if>
					</c:forEach>
					 <c:forEach var="authoritiy" items="${authorities }">
                    	<c:if test="${authoritiy=='Inventory' }">
                        	<li><a href="Inventory">库存页面</a></li>
                    	</c:if>
                	</c:forEach>
                	<c:forEach var="authoritiy" items="${authorities }">
                    	<c:if test="${authoritiy=='Quotation' }">
                        	<li><a href="Quotation">报价单页面</a></li>
                    	</c:if>
                	</c:forEach>   
                	<c:forEach var="authoritiy" items="${authorities }">
                    	<c:if test="${authoritiy=='Document' }">
                        	<li  class="currentPage"><a href="Document">文档页面</a></li>
                    	</c:if>
                	</c:forEach> 
                	<c:forEach var="authoritiy" items="${authorities }">
                    	<c:if test="${authoritiy=='Quality' }">
                        	<li><a href="Quality">商务文档</a>
                        		<ul class="info_list">
								<c:forEach var="authoritiy" items="${authorities }">
									<c:if test="${authoritiy=='Quality' }">
										<li><a href="Quality">质量证明</a></li>
									</c:if>
								</c:forEach>
								<c:forEach var="authoritiy" items="${authorities }">
                    				<c:if test="${authoritiy=='QuantityWeight' }">
                        				<li><a href="QuantityWeight">数量重量</a></li>
                    				</c:if>
                				</c:forEach>
								<c:forEach var="authoritiy" items="${authorities }">
									<c:if test="${authoritiy=='TestReport' }">
										<li><a href="TestReport">测试报告</a></li>
									</c:if>
								</c:forEach>
							</ul>
                        	</li>
                    	</c:if>
                	</c:forEach>              	
				</ul>
			</div>
<!-- 	=======================导航栏   结束 ================================== -->
			<form id="top_text_from" name="top_text_from" method="post" action="http://localhost:8080/Logistics/Quotation">
				<input type="text" id="search" name="isSearch" value="search" style="display: none;">
				<div class="choose">
					<input type="button" value="模板导入" class="bToggle templateImport" >
					<input id="exportPDF" type="button" value="PDF导出" class="bToggle exportPDF">
				</div>
			</form>
			<!-- 主体部分 -->
			<div class="mainContent">
				<!-- 竖状导航栏 -->
				<div class="nav-bar">
					<ul class=contactList>
						<li class="sw_list">
							<div class="sw_list_title">商务部</div>
							<ul>	
							</ul>
						</li>
						<li class="wl_list">
							<div class="wl_list_title">物流部</div>
							<ul>
								<li>111</li>
								<li>222</li>
								<li>333</li>
							</ul>
						</li>
					</ul>
				</div>
				<!-- 右侧信息栏  显示-->
				
				<div id="view" class="news" style="font-family: initial;position:relative;">
					<!-- 页眉-->
				<div class="yemei" style="margin-bottom:20px;">
				<div style="width:100%;height:70px;">
					<div class="logo lf">
						<img src="image/EOULUlogo.png" style="width: 175px;
    height: 61px;">
					</div>
					<div class="rt" style="margin-top:40px;color: #000080;">EOULU Technology</div>
				</div>
					
					<hr>
				</div>
						<table cellpadding="0" cellspacing="0" border="1" style="    margin-bottom: 100px;" >
							<tr>
								<td colspan="6"  style="text-align: center;font-size: 30px;">COMMERCIAL INVOICE</td>
							</tr>
							<tr>
								<td colspan="3" class="pl">
									<p contenteditable="true" >
										Seller<br/>
										HK EOULU TRADING LIMITED<br/>
										ROOM 1501 GRAND MILLENNIUM PLAZA (LOWER BLOCK)<br/> 181 QUEEN'S ROAD CENTRAL, HONG KONG<br/>
										TEL: 00852-21527388<br/>
										FAX: 00852-35719160
									</p>
								</td>
								<td colspan="3" class="pl">
									Invoice No. and Date<br/>
									CONTRACT NO.:<span contenteditable="true" class="outlineNone"> GNHCHNHI17033111LXJ</span><br/>
									PO: <span contenteditable="true" class="outlineNone">910071239</span><br/>
									INVOICE NO. : <span contenteditable="true" class="outlineNone">E100913</span><br/>
									DC No.:<span contenteditable="true" class="outlineNone"> NA</span>
								</td>
							</tr>
							<tr>
								<td colspan="3" rowspan="2" class="pl">
									Applicant<br/>
									<span contenteditable="true" class="outlineNone">HiSilicon Technologies CO., LIMITED<br/></span>
									<span contenteditable="true" class="outlineNone">Huawei Base, Bantian,Longgang District, Shenzhen, P.R.China<br/></span>
									TEL:<span contenteditable="true" class="outlineNone"> +86 755 28780808<br/></span>
									FAX:<span contenteditable="true" class="outlineNone"> +86 755 28357515</span>
								</td>
								<td colspan="3" class="pl">The End User(if other than consignee)<br/>
									<span contenteditable="true" class="outlineNone">NA</span>
								</td>
							</tr>
							<tr>
								<td colspan="3" class="pl">Other reference<br/>
									<span contenteditable="true" class="outlineNone">NA</span></td>
							</tr>
							<tr>
								<td colspan="3" class="pl">departure date:<br/>
									<span contenteditable="true" class="outlineNone">NA</span></td></td>
								<td colspan="3" rowspan="3" class="pl" >
									<span contenteditable="true" class="outlineNone">NA</span></td>
							</tr>
							<tr>
								<td colspan="3" class="pl">Vessel/flight From <span contenteditable="true" class="outlineNone">HONGKONG</span></td>
							</tr>
							<tr>
								<td colspan="3" class="pl">To <span contenteditable="true" class="outlineNone">SHENZHEN</span></td>
							</tr>
							<tr style="text-align: center;">
								<td>Item</td>
								<td>Description</td>
								<td>Brand/Origin</td>
								<td>Qty</td>
								<td>Unit price(USD)</td>
								<td>Total amount(USD)</td>
							</tr>
							<tr style="text-align: center;">
								<td>1</td>
								<td>
									<span contenteditable="true" class="outlineNone">Summit12000B-M<br/>
									PROBE STATION PLATFORM,<br/>
									SEMI-AUTOMATI <br>
									WITH MICROCHAMBER<br/>
									</span>
								</td>
								<td>
									<span contenteditable="true" class="outlineNone">Cascade <br>Microtech<br/>
									/Germany</span>
								</td >
								<td><span contenteditable="true" class="outlineNone">1</span></td>
								<td><span contenteditable="true" class="outlineNone">239,088</span></td>
								<td><span contenteditable="true" class="outlineNone">239,088</span></td>
							</tr>
							<tr>
								<td colspan="5" style="text-align: right;">TOTAL AMOUNT (CIP <span contenteditable="true" class="outlineNone">SHENZHEN</span>) USD:</td>
								<td style="text-align: center;"><span contenteditable="true" class="outlineNone">239,088</span></td>
							</tr>
							<tr>
								<td colspan="6" class="pl">
									Bank details<br/>
					     			Bank name    HSBC HONGKONG<br/>
					    			Bank code    004<br/>
					     			Bank address  1 Queen’s Road Central, HongKong<br/>
					     			SWIFT Code  HSBCHKHHHKH<br/>
					     			Account name  HK EOULU TRADING LIMITED<br/>
					     			Account No   801-012469-838
								</td>
							</tr>
							<tr>
								<td colspan="6" class="pl">
								<span style="float: left;width: 50%;">Signature:</span>
								<span style="float: left;width: 50%;">
									Date:<div  contenteditable="true" style="display:inline-block;" class="outlineNone"></div>
								</span>
								</td>
							</tr>
						</table>
				
						<!-- 页脚 -->
					<div class="yejiao" style="margin-top: 20px; height: 63px;">
						<hr>
						<pre style="text-align: center;color: #000080;font-family: -webkit-body;">EOULU
Suzhou ● Shenzhen ● Beijing ● Shanghai ● HongKong
〡Phone: +86-512-62757360〡Web:www.eoulu.com〡Email:info@eoulu.com〡</pre>
						
					</div>
				</div>
				
			</div>
		</div>
</div>

	<!-- <div id="hiddenView" class="news" style="font-family: initial;position:absolute;top:0;left:0;display:none;"></div> -->
	<!-- ***************导入文件区域****************** -->
	<div class="MailBar_cover_color"></div>
 	<div class="MailBar" style="display:none;">
		<div class="operate_title">导入模板</div>
		<div class="MailBar_close">关闭</div>
		<div class="chooseSWOrWL">
			请选择部门：
			<input type="radio" name="chooseSW" id="chooseSW" value="商务部"  checked="checked"/>&nbsp;商务部
      		<input type="radio" name="chooseSW" id="chooseWL" value="物流部" />&nbsp;物流部
		</div>
		<input type="file" name="file" id="tem_fileToUpload"  value="导入模板" />
		<input type="button" name="button" value="导入" class="bToggle" id="Mail_Send">
	</div> 

</body>

<script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script>
<script src="js/ajaxfileupload.js" ></script>
<script src="js/msgbox.js"></script>
<script type="text/javascript" src="js/html2canvas.js"></script>  
<script type="text/javascript" src="js/jsPdf.debug.js"></script>

<script>
$(function(){
    $.ajax({
       type : 'get',
       url : 'GetDocumentName',
       dataType : 'json',
       success : function (data) {
       	console.log(data);
       	for(var i = 1; i < data.length ; i++){
       		var swStr ="<li>"+data[i].fileName +"</li>" ;
       		$(".sw_list ul").append(swStr)
       	}
       },
       error : function () {
           $.MsgBox.Alert("提示","服务器繁忙，稍后重试！");
       }
   }); 	
})
	
	




   var aa = ["这是一个"];
	$(".outlineNone").change(function(){
		console.log($(this).text());
		if(aa.indexOf($(this).text())>0){		
		}		
	})
	
	
	$(".templateImport").click(function(){
		$(".MailBar").show()
		$(".MailBar_cover_color").show();
	})
	$(".MailBar_close").click(function(){
		$(".MailBar_cover_color").hide();
		$(".MailBar").hide();
	})

		
		//点击打开文件选择器  
   
     //选择文件之后执行上传  
     $('#Mail_Send').on('click', function() { 
    	 var Name = $("#tem_fileToUpload").val().split("fakepath")[1].split("\\")[1];
    	 var theChoose = $(".chooseSWOrWL input[type='radio']:checked").val();
    	 if(theChoose == "商务部"){
    			Type=1;
    		}
    		else{
    			Type=2;
    		}
         $.ajaxFileUpload({  
            url:'DocumentModelUpload',  
            secureuri:false,  
            fileElementId:'tem_fileToUpload',//file标签的id  
            dataType: 'JSON',//返回数据的类型  
            data:{
            	name:Name,
            	type:Type
            },//一同上传的数据  
            success: function (data) {
            	console.log(theChoose)
            	data = eval("(" + data + ")");
            	console.log(data.indexOf("true"))
            	if(data.indexOf("true") >0){
            		$.MsgBox.Alert("提示", "上传成功！");	
               		$(".MailBar").hide();
               		$(".MailBar_cover_color").hide();
               		var swStr ="<li>"+Name +"</li>" ;
               		if(theChoose == "商务部"){
               			$(".sw_list ul").append(swStr)
               		}
               		else{
               			$(".wl_list ul").append(swStr)
               		}
            	}
            	else{
            		$.MsgBox.Alert("提示", "上传失败！");
            		var swStr ="<li>"+Name +"</li>" ;
               		if(theChoose == "商务部"){
               			$(".sw_list ul").append(swStr)
               		}
               		else{
               			$(".wl_list ul").append(swStr)
               		}
            	}
            },  
            error: function (e) {  
                alert("error");  
            }  
        });   
    });   
	
	//点击模板名称   加载内容
	$(document).on("click",".sw_list li,.wl_list li",function(){
		$(this).addClass("chooseTem").siblings().removeClass("chooseTem");
		$(this).parent().parent().siblings().find("ul li").removeClass("chooseTem");
		
		var name = $(this).text();
		console.log(name);
	     $.ajax({
	        type : 'get',
	        url : 'GetDocumentModel',
	        data : {
	        	name:name
	        },
	        dataType : 'json',
	        success : function (data) {
	        	console.log(data)
	        },
	        error : function () {
	            $.MsgBox.Alert("提示","服务器繁忙，稍后重试！");
	        }
	    }); 
		
	})
	//导出PDF
	/*$(".exportPDF").click(function(){
		var exportName = $(".chooseTem").text();
		   $.ajax({
	        type : 'get',
	        url : 'ExportPDF',
	        data : {
	        	name:exportName
	        },
	        dataType : 'json',
	        success : function (data) {
	        	console.log(data)
	        },
	        error : function () {
	            $.MsgBox.Alert("提示","服务器繁忙，稍后重试！");
	        }
    	}); 
		
	})*/
	document.getElementById("exportPDF").onclick=function(){
		html2canvas(document.getElementById("view"),{
			onrendered:function(canvas){
				var imgData=canvas.toDataURL('image/png',1);
				var doc=new jsPDF('p', 'mm','a4');
				doc.setFontSize(10);
				doc.addImage(imgData,'PNG',5,10,200,280);
				//doc.text('table',10,10);
				doc.save('view.pdf');
				
			}
		});
	}
	/*有隐藏部分的时候的代码
	document.getElementById("exportPDF").onclick=function(){
		var hiddenViewStr = $("#view").html();
		console.log(hiddenViewStr);
		$("#hiddenView").html(hiddenViewStr);
		var target = document.getElementById("hiddenView");
		var width = $('#hiddenView').width() ; 
		var height =  $('#hiddenView').height() ;
		var canvas = document.createElement("canvas");  
		    canvas.width = width * 2;  
		    canvas.height = height * 2;  
		    canvas.style.width = width + "px";  
		    canvas.style.height = height + "px";  
		var context = canvas.getContext("2d");//然后将画布缩放，将图像放大两倍画到画布上  
		    context.scale(2,2); 
	  html2canvas(target, {
		  canvas:canvas,
		    onrendered:function(canvas) {
   	
		        var pageData = canvas.toDataURL('image/png', 1.0);
		        var pdf = new jsPDF('', 'pt', 'a4');
		        pdf.addImage(pageData, 'PNG', 0, 0, 600, 841.89);
		        pdf.save('view.pdf');
		    }
		});
	} 
	不需要隐藏部分的代码
	
<<<<<<< .mine

||||||| .r1071
=======
	document.getElementById("exportPDF").onclick=function(){
		var target = document.getElementById("view");
		var width = $('#view').width() ; 
		var height =  $('#view').height() ;
		var canvas = document.createElement("canvas");  
		    canvas.width = width * 2;  
		    canvas.height = height * 2;  
		    canvas.style.width = width + "px";  
		    canvas.style.height = height + "px";  
		var context = canvas.getContext("2d");//然后将画布缩放，将图像放大两倍画到画布上  
		    context.scale(2,2); 
	  html2canvas(target, {
		  canvas:canvas,
		    onrendered:function(canvas) {
		    	
		        var pageData = canvas.toDataURL('image/png', 1.0);
		        var pdf = new jsPDF('', 'pt', 'a4');
		        pdf.addImage(pageData, 'PNG', 0, 0, 600, 841.89);
		        pdf.save('view.pdf');
		    }
		})
	} */


	

/*$(window).bind("load",function() {
	var footerHeight = 0,footerTop= 0,
	$footer= $(".yejiao");
    positionFooter();
		function positionFooter() {
		footerHeight= $footer.height();
		footerTop= ( $(window).scrollTop()+$(window).height()-footerHeight)+"px";
			if( ($(document.body).height()+footerHeight) < $(window).height()) {
				$footer.css({
					position:"absolute"
				}).stop().animate({
					top:footerTop
					});
				}else{
				$footer.css({
					position:"static"
				});
			}
		}
		$(window).scroll(positionFooter).resize(positionFooter);
	});*/
	

</script>
</html>