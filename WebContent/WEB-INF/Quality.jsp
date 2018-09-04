<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>质量证明</title>
<style>
.tbody_tr td {
    padding: 0 3px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
</style>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" />
<link rel="stylesheet" type="text/css" href="css/quality.css">
</head>
<body>
	<!-- 	头部开始 -->
	<%@include file="top.jsp"%>
	<!-- 	头部结束 -->
	<div class="contain">
		<div class="content">
		<!-- 	=======================导航栏   开始 ================================== -->
			<%@include file="nav.jsp"%>

			<!-- 查询功能 -->
			 <div style="display: none">
				<input type="text" value="" name="query_type"> 
				<input type="text" value="" name="classify1"> 
				<input type="text" value="" name="classify2"> 
				<input type="text" value="" name="parameter1"> 
				<input type="text" value="" name="parameter2">
			  </div>  

 			<form id="top_text_from" name="top_text_from" method="post"
				action="GetQualityRoute">
				<input type="text" id="search" name="isSearch" value="search"
					style="display: none;">
				<div class="select-content">
					 <label> <c:choose>
							<c:when test="${queryType=='mixSelect'}">
								<label><input type="radio" id="singleSelect"
									name="selected" class="singleSelect" value="singleSelect"
									onclick="Check(this.value)">单一查询 </label>&nbsp;&nbsp;&nbsp;<label>
									<input type="radio" id="mixSelect" name="selected"
									value="mixSelect" checked="checked" onclick="Check(this.value)">组合查询
								</label>&nbsp;&nbsp;&nbsp;<br>
							</c:when>
							<c:otherwise>
								<label><input type="radio" id="singleSelect"
									name="selected" class="singleSelect" value="singleSelect"
									checked="checked" onclick="Check(this.value)">单一查询 </label>&nbsp;&nbsp;&nbsp;<label>
									<input type="radio" id="mixSelect" name="selected"
									value="mixSelect" onclick="Check(this.value)">组合查询
								</label>&nbsp;&nbsp;&nbsp;<br>
							</c:otherwise>
						</c:choose> <c:set var="dropdown"
							value="${fn:split('客户名称,合同名称,合同号',',')}"></c:set>
						<div class="select1">
							<select name="type1" id="type1">
								<c:forEach items="${dropdown }" var="dropdownList1"
									varStatus="status">
									<c:choose>
										<c:when test="${dropdownList1==classify1}">
											<option selected="selected">${dropdownList1}</option>
										</c:when>
										<c:otherwise>
											<option>${dropdownList1}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>


							</select>
							 <input type="text" id="searchContent1" name="searchContent1" value="${parameter1}">

						</div>
						<div class="select2" style="display: none">
							<select name="type2" id="type2">
								<c:forEach items="${dropdown }" var="dropdownList2"
									varStatus="status">
									<c:choose>
										<c:when test="${dropdownList2==classify2}">
											<option selected="selected">${dropdownList2}</option>
										</c:when>
										<c:otherwise>
											<option>${dropdownList2}</option>
										</c:otherwise>	
									</c:choose>
								</c:forEach>
							</select>
							<c:if test="${queryType =='mixSelect'}">
								<input type="text" id="searchContent2" name="searchContent2" value="${parameter2}">
							</c:if>
							<c:if test="${queryType!='mixSelect'}">
								<input type="text" id="searchContent2" name="searchContent2" value="${parameter2}">
							</c:if>

						</div>
						 <div class="select-button">
							<input type="button" value="搜索" class="bToggle" onclick="INSearch()"> 
							<input type="button" value="取消" class="bToggle" onclick="Cancel()">
						</div>
				</div>
				<div class="choose">
					<input type="button" value="添加" class="bToggle" onclick="AddContract()">
				</div>

		 </form>  
			<table border="1" cellspacing="0" cellspadding="0" id="table1">
				<tr>
					<td>序号</td>
					<td>修改</td>
					<td>客户名称 </td>
					<td>合同名称</td>
					<td>合同号</td>
					<td style="width: 145px;">模板预览（可导出）</td>
					<td style="display:none;">删除数据</td>
				</tr>
				
				 <c:forEach var="orderInfo" items="${quality}" varStatus="status">
					<c:if test="${status.index>0}">
						<tr class="tbody_tr">
							<td value="${orderInfo['ID']}">${status.index+(currentPage-1)*10}</td>
							<td> <i class="fa fa-edit contract-edit" value="${orderInfo['ID']}"></i></td>
							<td title="${orderInfo['Customer']}">${orderInfo['Customer']}</td>
							<td title="${orderInfo['ContractTitle']}">${orderInfo['ContractTitle']}</td>
							<td>${orderInfo['ContractNO']}</td>
							<td><i class="fa fa-eye contract-show"></i></td>
							<td style="display:none;"><i class="fa fa-trash-o del"></i></td>
							<td style="display:none;">${orderInfo['Date']}</td>    <!--7  -->
							<td style="display:none;">${orderInfo['ContractNO']}</td><!--8  -->
							<td style="display:none;">${orderInfo['PONO']}</td><!--9  -->
							<td style="display:none;">${orderInfo['DCNO']}</td><!--10 -->
						</tr>
					</c:if>
				</c:forEach>
			</table>	
		
	 	 <c:choose>
				<c:when test="${queryType == 'common'}">
					<c:set var="queryUrl"
					value="Quality?type1=${classify1 }&searchContent1=${parameter1}&selected=${str}&currentPage=">
					</c:set>
				</c:when>
				<c:otherwise>
					<c:set var="queryUrl"
					value="GetQualityRoute?type1=${classify1 }&searchContent1=${parameter1}&type2=${classify2}&searchContent2=${parameter2}&selected=${str}&currentPage=">
					</c:set>
				</c:otherwise>
			
			</c:choose> 
						
 			<div id="page">
				<div class="pageInfo">
					当前是第&nbsp;<span id="currentPage">${currentPage}</span>&nbsp;页,&nbsp;总计&nbsp;<span
						id="allPage">${pageCounts}</span>页
				</div>
				<div class="changePage">
					<input type="button" class="bToggle" value="首页" id="fistPage"
						name="fistPage" onclick="FistPage('${queryUrl}')"> <input
						type="button" class="bToggle" value="上一页" id="upPage"
						onclick="UpPage('${queryUrl}${currentPage-1 }')"> <input
						type="button" class="bToggle" value="下一页" id="nextPage"
						onclick="NextPage('${queryUrl}${currentPage+1 }')"> 跳到第 <input
						type="text" id="jumpNumber" name="jumpNumber" class="jumpNumber"
						style="width: 30px; color: #000"
						onkeyup="value=value.replace(/[^\d]/g,'') "> 页 <input
						type="button" class="bToggle" value="GO" id="Gotojump"
						name="Gotojump" onclick="PageJump('${queryUrl}')"> <input
						type="button" class="bToggle" value="尾页" id="lastPage"
						name="lastPage" onclick="LastPage('${queryUrl}')">
				</div>
			</div>  
		</div>
	</div>
	<div class="MailBar_cover_color"></div>
	
		<!-- -------------------------  添加质量证明信息 ----------------------------------->
	<div class="contract_add">
		<div class="contract_title">添加质量证明信息</div>
		<div class="contractAdd_close">关闭</div>
		<div class="basic_info">
			<div class="table_title">质量证明信息</div>
			<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
				<tbody>	
					<tr>
					    <td>DATE</td>
						<td><input type="date" name="Date" value="" id="Date"></td>	
						<td>CONTRACT NO.</td>
						<td><input type="text" name="ContractNO" value=""  id="contract_no"></td>
					</tr>
					<tr>
						
						<td>PO NO.</td>
						<td><input type="text" name="PONO" value="" ></td>
						<td>DC No.</td>
						<td><input type="text" name="DCNO" value=""></td>
					</tr>
				</tbody>
			</table>		
		</div>
		<div class="edit_btn">
			<input type="button" value="提交" class="bToggle" id="add_submit">
			<input type="button" value="取消" class="bToggle" id="add_cancel">
		</div>
	</div>
	
	
	
	<!-- -------------------------  修改质量证明信息 ----------------------------------->
	<div class="contract_update">
		<div class="contract_title">修改质量证明信息</div>
		<div class="contractUpdate_close">关闭</div>
		<div class="basic_info">
			<div class="table_title">质量证明信息</div>
			<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
				<tbody>	
					<tr>
					    <td>DATE</td>
						<td><input type="date" name="Date" value="" id="Date"></td>	
						<td>CONTRACT NO.</td>
						<td><input type="text" name="ContractNO" value=""  id="contract_no"></td>
					</tr>
					<tr>
						
						<td>PO NO.</td>
						<td><input type="text" name="PONO" value="" ></td>
						<td>DC No.</td>
						<td><input type="text" name="DCNO" value=""></td>
					</tr>
				</tbody>
			</table>		
		</div>
		<div class="edit_btn">
			<input type="button" value="提交" class="bToggle" id="update_submit">
			<input type="button" value="取消" class="bToggle" id="update_cancel">
		</div>
	</div>
	
	
	<div class="hidePdf" style="display:none;">
			<div id="view" class="news" style="font-family: initial;position:absolute;z-index:11;top:0;left:0;background:#fff;font-size:12px">
    <!-- 页眉-->
    <div class="yemei" style="margin-bottom:20px; margin-top: 20px;">
        <div style="width:100%;height:70px;">
            <div class="logo lf" >
                <img src="image/EOULUlogo.png" style="width: 175px; height: 61px;">
            </div>
            <div class="rt" style="margin-top:40px;color: #000080;">EOULU Technology</div>
        </div>

        <hr>
    </div>

    <!--质量证明文档-->
    <div id="table_Quality">
    <p class="f16 b m60b">CERTIFICATE OF QUALITY</p>
    <p class="m30b">Dear Sir/Madam</p>
    <div class="content_text m30b">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;In accordance with the following captioned letter of credit (DC No.:
        <span contenteditable="true" class="DCNO"></span>) requirements, we hereby certify that the above captioned
        shipment was manufactured and tested in accordance with Cascade Microtech,
        Inc. standard procedures and the quality is equal to the requirements of Contract :
        No.<span contenteditable="true" class="ContractNO"></span>
        <span style="text-decoration:none;"class="PONOText">and purchase order (PO No.:
        <span contenteditable="true" class="PONO"></span>)</span>.       
    </div>
    <p class="m30b lf" style="display: inline-block;width: 70%">Certified by:<span contenteditable="true"></span> </p>
    <p >Date: <span contenteditable="true" class="Date"></span></p>

    <div style="display: inline-block;" class="cf lf m30b">Signature:</div>
    <div contenteditable="true" class="lf" style="border-bottom: 1px solid #000;width:150px;"></div>
    
    <p class="cf">CASCADE MICROTECH</p>
    </div>

    <!-- 页脚 -->
    <div class="yejiao cf" style="margin-top: 20px; height:80px;">
        <hr>
        <pre style="text-align: center;color: #000080;font-family: -webkit-body;">EOULU
Suzhou ● Shenzhen ● Beijing ● Shanghai ● HongKong
〡Phone: +86-512-62757360〡Web:www.eoulu.com〡Email:info@eoulu.com〡</pre>

    </div>
</div>
			<!-- <input type="button" value="提交" class="bToggle" id="submit_n" style="position:absolute;z-index:11;top: 200px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
			 -->
			<input type="button" value="导出PDF" class="bToggle" id="exportPDF1" style="position:absolute;z-index:11;top: 150px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
			<input type="button" value="关闭" class="bToggle " id="contract_close1" style="position: absolute;z-index: 11;top: 100px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
	</div>
</body>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/msgbox.js"></script>
<script src="js/quality.js"></script>
<script type="text/javascript" src="js/html2canvas.js"></script>  
<script type="text/javascript" src="js/jsPdf.debug.js"></script>
<script>

/*********************添加质量证明信息************************/
/* 添加质量证明信息 */
$(document).on("click","#add_submit",function(){
		console.log("111")
		var Date=$('.contract_add input[name="Date"]').val();
	    var ContractNO=$('.contract_add input[name="ContractNO"]').val();
	    var PONO=$('.contract_add input[name="PONO"]').val() == "" ? "NA":$('.contract_add input[name="PONO"]').val();
	    var DCNO=$('.contract_add input[name="DCNO"]').val();
 	   $.ajax({
	        type : 'get',
	        url : 'QualityAdd',
	        data : {
	        	Date : Date,
	            ContractNO : ContractNO,
	            PONO : PONO,
	            DCNO : DCNO,
	        },
	        dataType : 'json',
	        success : function (data) {
	        	if(data){
	        		 $.MsgBox.Alert('提示','添加成功');
	 	            $('.MailBar_cover_color').hide();
	 	            $('.contract_add').hide();
	        	}else{
	        		 $.MsgBox.Alert("提示", "添加失败，稍后重试！");
	        	}
	           
	        },
	        error : function () {
	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    }); 
	    	    
    });
    
    
/* 修改质量证明信息 */
$(document).on("click",".contract-edit",function(){	
	var tr=$(this).parent().parent();
	
    $('.contract_update input[name="Date"]').val(tr.find('td').eq(7).text());
	$('.contract_update input[name="ContractNO"]').val(tr.find('td').eq(8).text());
	$('.contract_update input[name="PONO"]').val(tr.find('td').eq(9).text());
	$('.contract_update input[name="DCNO"]').val(tr.find('td').eq(10).text());
	
	var  ID = tr.find('td').eq(0).attr("value");
	
	$(".contract_update .contract_title").attr("value",ID);  //在修改页面保存当前行的ID信息
	
	console.log(ID)
   $('.MailBar_cover_color').show();
   $('.contract_update').show();
	    	    
 });
   	
	/*  提交修改后的信息  */
	$('#update_submit').click(function () {
		var Date = $('.contract_update input[name="Date"]').val();
		var ContractNO = $('.contract_update input[name="ContractNO"]').val();
		var PONO = $('.contract_update input[name="PONO"]').val();
		var DCNO = $('.contract_update input[name="DCNO"]').val();
		var  ID = $(".contract_update .contract_title").attr("value");
		console.log(Date);
		console.log(ContractNO);
		console.log(PONO);
		console.log(DCNO);
		console.log(ID);
		  $.ajax({
		      type : 'get',
		      url : 'ModifyQuality',
		      data : {
		    	  ID : ID,
		      	ContractNO : ContractNO,
		      	DCNO : DCNO,
		      	PONO : PONO,
		      	Date : Date,
		      },
		      dataType : 'json',
		      success : function (data) {
		      	
		          $.MsgBox.Alert('提示','修改成功');
		          $('.MailBar_cover_color').hide();
		          $('.contract_add').hide();
		      },
		      error : function () {
		          $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
		      }
		  }); 
	})   
    
 /*  模板显示*/   
 $('.contract-show').click(function () {
		 
	    $('.MailBar_cover_color').show();
	    /* 获取隐藏信息 */
	     
         var thisList = $(this).parent().parent();
         var ID = thisList.find("td").eq(0).attr("value");
         $(".yejiao").attr("value",ID);
	     var Date = thisList.find("td").eq(7).text();
	     var ContractNO = thisList.find("td").eq(8).text();
	     var PONO = thisList.find("td").eq(9).text();
	     var DCNO = thisList.find("td").eq(10).text();
	     $(".news .DCNO").text("").text(DCNO); 
	     $(".news .ContractNO").text("").text(ContractNO); 
	     $(".news .Date").text("").text(Date); 
	     $(".news .PONO").text("").text(PONO); 
	     
	      //PONO是否存在
	      if(PONO =="NA"){
	       /*  $(".PONO").hide(); */
	      $(".PONOText").hide();
	     }
	     //else{
	     //	$(".PONO").show();
	      //  $(".news .PONO").text(PONO);
	     	 /* var PONOText = "and purchase order (PO No.:"+$(".PONO").text()+")"; */ 
	     //	$(".news .PONOText").html('and purchase order (PO No.:'+$('<span contenteditable="true" class="PONO"></span>')+')' )
	    // } 
	         
    $('.hidePdf').show(); 
 })

 
$('#contract_close1').click(function () {
    $('.MailBar_cover_color').hide();
    $('.hidePdf').hide();
});


/* ************************模板页面提交*************************** */

 /* 
 $(document).on("click","#submit_n",function(){
	var ID=  $(".hidePdf .yejiao").attr("value");
    var Date=$('.hidePdf .Date').text();
    var ContractNO=$('.hidePdf .ContractNO').text();
     //var PONO=$('.hidePdf .PONO_text').text().split(":")[1].split(")")[0]; 
    var PONO=$('.hidePdf .PONO').text();
    var DCNO=$('.hidePdf .DCNO').text();
    	$.ajax({
            type : 'get',
            url : "ModifyQuality",
            data : {
            	ID:ID,
            	Date : Date,
            	ContractNO : ContractNO,
            	DCNO : DCNO,
            	PONO : PONO,
            },
            dataType : 'json',
            success : function (data) {       	        	             	
            	$.MsgBox.Alert('提示','提交成功');
	            $('.MailBar_cover_color').hide();
	            $('.hidePdf').hide();
            },
            error : function () {
                $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
            }
        });
}); 
*/

//点击添加
function AddContract() {
	/*默认当前日期 */
	var ddd = new Date();
	var day =ddd.getDate();
	if(ddd.getMonth()<10){
	var month = "0"+(ddd.getMonth()+1); 
	}
	if(ddd.getDate()<10){
	 day = "0"+ddd.getDate(); 
	}
	var datew = ddd.getFullYear()+"-"+month+"-"+day;
	datew = datew.toString();
	$("#Date").val(datew);
    $('.MailBar_cover_color').show();
    $('.contract_add').show();
};


//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function () {
    window.location.reload();
});


//点击关闭
$('.contractAdd_close').click(function () {
  $('.MailBar_cover_color').hide();
  $('.contract_add').hide();
});

$('.contractUpdate_close').click(function () {
  $('.MailBar_cover_color').hide();
  $('.contract_update').hide();
});
$('#contract_close1').click(function () {
  $('.MailBar_cover_color').hide();
  $('.hidePdf').hide();
});

//点击取消
$('#add_cancel').click(function () {
  $('.MailBar_cover_color').hide();
  $('.contract_add').hide();
});

$('#update_cancel').click(function () {
  $('.MailBar_cover_color').hide();
  $('.contract_update').hide();
});


document.getElementById("exportPDF1").onclick=function(){
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
	        pdf.addImage(pageData, 'PNG', 0, 0, 600, 821.89);
	        pdf.save('Quality.pdf');
	    }
	})
}

/****************** 跳页 **********************/

function FistPage(arg) {
	window.location.href = arg + "1";
	
}
function UpPage(arg) {
	window.location.href = arg;
}
function NextPage(arg) {
	
	window.location.href = arg;
}
function PageJump(arg) {
	var jumpNumber = document.getElementById("jumpNumber").value;
	if (jumpNumber == null || jumpNumber == 0) {
		jumpNumber = $('#currentPage').html();
	} else if (jumpNumber > parseInt($('#allPage').html())) {
		jumpNumber = $('#allPage').html();
	}
	window.location.href = arg + jumpNumber;
}
function LastPage(arg) {
	var jumpNumber = parseInt($('#allPage').html());
	window.location.href = arg + jumpNumber;
}
$(function() {
	if ($('#currentPage').html() == 1) {
		$('#fistPage').attr('disabled', 'true');
		$('#fistPage').removeClass('bToggle');
		$('#upPage').attr('disabled', 'true');
		$('#upPage').removeClass('bToggle');
	}
	if ($('#allPage').html() == $('#currentPage').html()) {
		$('#lastPage').attr('disabled', 'true');
		$('#lastPage').removeClass('bToggle');
		$('#nextPage').attr('disabled', 'true');
		$('#nextPage').removeClass('bToggle');
	}
	
})


/*********模板 删除*******/
  $(document).on("click",".del",function () {
	  var thisList = $(this).parent().parent();
      var ID = thisList.find("td").eq(0).attr("value");
      $(".yejiao").attr("value",ID);
	  console.log(ID);
	  $.ajax({
          type : 'get',
          url : "QualityDelete",
          data : {	
          	ID:ID
          },
          
          dataType : 'json',
          success : function (data) {       	        	             	
          	$.MsgBox.Alert('提示','删除成功');
	            $('.MailBar_cover_color').hide();
	            $('.hidePdf').hide();
          },
          error : function () {
              $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
          }
      });
 	   var  that = $(this);
	   $.MsgBox.Confirm('提示','确认要删除吗？',function(){
		   that.parents("tr").remove();
	  });  
	    /*  article_del($(this)) */
 })
 /*  function article_del(obj){
     var res = confirm('确认要删除吗？');
     if(res == true)
     {
         obj.parents("tr").siblings().each(function(i){
             $(this).find("td").eq(0).text(i+1)
         }) 

         obj.parents("tr").remove();
     }

 } */



</script>
</html>