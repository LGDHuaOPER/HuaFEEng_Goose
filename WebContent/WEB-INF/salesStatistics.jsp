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
<title>销售统计</title>
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
<link rel="stylesheet" type="text/css" href="css/salesStatistics.css">
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
				action="SalesStatisticsRoute">
				<input type="text" id="search" name="isSearch" value="search"
					style="display: none;">
				<div class="select-content">
							 <label style="display: none"> <input type="radio"
									id="singleSelect" name="selected" class="singleSelect"
									value="singleSelect" checked="checked" onclick="Check(this.value)">单一查询
								</label>
					
							
						<c:set var="dropdown"
							value="${fn:split('型号',',')}"></c:set>
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
						 <div class="select-button">
							<input type="button" value="搜索" class="bToggle" onclick="INSearch()"> 
							<input type="button" value="取消" class="bToggle" onclick="Cancel()">
						</div>
				</div>
				

		 </form>  
			<table border="1" cellspacing="0" cellspadding="0" id="table1">
				<tr>
					<td style="width:100px">序号</td>
					<td>型号</td>
					<td>描述 </td>
					<td>数量</td>
					<td style="display:none;">删除数据</td>
				</tr>
				
				 <c:forEach var="orderInfo" items="${sales}" varStatus="status">
					<c:if test="${status.index>0}">
						<tr class="tbody_tr">
							<td value="${orderInfo['ID']}">${status.index+(currentPage-1)*10}</td>
							<%-- <td> <i class="fa fa-edit contract-edit" value="${orderInfo['ID']}"></i></td> --%>
							<td title="${orderInfo['Model']}">${orderInfo['Model']}</td>       <!--1  -->
							<td title="${orderInfo['Description']}">${orderInfo['Description']}</td>   <!--2  -->
							<td>${orderInfo['Quantity']}</td>    <!--3  -->
							<td style="display:none;"><i class="fa fa-trash-o del"></i></td>   <!--4 -->
						</tr>
					</c:if>
				</c:forEach>
			</table>
			
	 	 <c:choose>
				<c:when test="${queryType == 'common'}">
					<c:set var="queryUrl"
					value="SalesStatistics?type1=${classify1}&searchContent1=${parameter1}&selected=${selected}&currentPage=">
					</c:set>
				</c:when>
				<c:otherwise>
					<c:set var="queryUrl"
					value="SalesStatisticsRoute?type1=${classify1}&searchContent1=${parameter1}&selected=${selected}&currentPage=">
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
						onclick="UpPage('${queryUrl}${currentPage-1}')"> <input
						type="button" class="bToggle" value="下一页" id="nextPage"
						onclick="NextPage('${queryUrl}${currentPage+1}')"> 跳到第 <input
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
<!-- 	<div class="MailBar_cover_color"></div> -->
	
		<!-- -------------------------  添加装机进展信息 ----------------------------------->
	<!-- <div class="contract_add">
		<div class="contract_title">添加装机进展</div>
		<div class="contractAdd_close">关闭</div>
		<div class="basic_info">
			<div class="table_title">装机进展</div>
			<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
				<tbody>	
					<tr>
					    <td>装机时间</td>
						<td><input type="date" name="InstalledTime" value="" id="InstalledTime"></td>	
						<td>客户</td>
						<td><input type="text" name="Customer" value=""  id="Customer"></td>
					</tr>
					<tr>
						<td>项目状态</td>
						<td>
							<input type="text" name="Status" value="" style="position:absolute;width:162px;" id="Status">
								<select onchange="document.getElementById('Status').value=this.options[this.selectedIndex].text" class="StatusSelect">
									<option value="No" selected="selected">--请选择--</option>
									<option value="">交付</option>
									<option value="">尾款</option>
									<option value="">完结</option>
								</select>
						</td>
						<td>负责人及进展</td>
						<td><input type="text" name="ResponsibleAndProcess" value=""></td>
					</tr>
				</tbody>
			</table>		
		</div>
		<div class="edit_btn">
			<input type="button" value="提交" class="bToggle" id="add_submit">
			<input type="button" value="取消" class="bToggle" id="add_cancel">
		</div>
	</div> -->
	
	
	
	<!-- -------------------------  修改装机进展信息 --------------------------------- -->
	<!-- <div class="contract_update">
		<div class="contract_title">修改装机进展</div>
		<div class="contractUpdate_close">关闭</div>
		<div class="basic_info">
			<div class="table_title">装机进展</div>
			<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
				<tbody>	
					<tr>
					    <td>装机时间</td>
						<td><input type="date" name="InstalledTime" value="" id="InstalledTime"></td>	
						<td>客户</td>
						<td><input type="text" name="Customer" value=""  id="Customer"></td>
					</tr>
					<tr>
						<td>项目状态</td>
						<td>
							<input type="text" name="Status" value="" style="position:absolute;width:162px;" id="Status1">
								<select onchange="document.getElementById('Status1').value=this.options[this.selectedIndex].text" class="StatusSelect">
									<option value="No" selected="selected">--请选择--</option>
									<option value="">交付</option>
									<option value="">尾款</option>
									<option value="">完结</option>
								</select>
						</td>
						<td>负责人及进展</td>
						<td><input type="text" name="ResponsibleAndProcess" value=""></td>
					</tr>
				</tbody>
			</table>		
		</div>
		<div class="edit_btn">
			<input type="button" value="提交" class="bToggle" id="update_submit">
			<input type="button" value="取消" class="bToggle" id="update_cancel">
		</div>
	</div>  -->

</body>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/msgbox.js"></script>
<script src="js/salesStatistics.js"></script>
<script>

/* var tr=$(this).parent().parent();

var Model= tr.find('td').eq(1).text();
var Description= tr.find('td').eq(2).text();
var Quantity= tr.find('td').eq(3).text();

console.log("Model"+Model)
console.log("Description"+Description)
console.log("Quantity"+Quantity) */

/*********************添加装机进展信息************************/
/* 添加装机进展信息 */
/* $(document).on("click","#add_submit",function(){
		console.log("111")
		var InstalledTime=$('.contract_add input[name="InstalledTime"]').val();
	    var Customer=$('.contract_add input[name="Customer"]').val();
	    var ResponsibleAndProcess=$('.contract_add input[name="ResponsibleAndProcess"]').val();
	    var Classify="Add";
	    
	    
	    console.log(InstalledTime);
		console.log(Customer);
		console.log(Status);
		console.log(ResponsibleAndProcess);
		console.log(Classify);
 	   $.ajax({
	        type : 'get',
	        url : 'HardwareOperate',
	        data : {
	        	InstalledTime : InstalledTime,
	        	Customer : Customer,
	        	Status : Status,
	        	ResponsibleAndProcess : ResponsibleAndProcess,
	        	Classify : Classify,
	        },
	        dataType : 'json',
	        success : function (data) {
	        	console.log(data);
	            $.MsgBox.Alert('提示','添加成功');
	            $('.MailBar_cover_color').hide();
	            $('.contract_add').hide();
	        },
	        error : function () {
	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    }); 
	    	    
    }); */
    
    
/* 修改装机进展信息 */
/* $(document).on("click",".contract-edit",function(){	
	var tr=$(this).parent().parent();
	
    $('.contract_update input[name="InstalledTime"]').val(tr.find('td').eq(3).text());
	$('.contract_update input[name="Customer"]').val(tr.find('td').eq(2).text());
	$('.contract_update input[name="Status"]').val(tr.find('td').eq(4).text());
	$('.contract_update input[name="ResponsibleAndProcess"]').val(tr.find('td').eq(5).text());
	
	var  ID = tr.find('td').eq(0).attr("value");
	
	$(".contract_update .contract_title").attr("value",ID);  //在修改页面保存当前行的ID信息
	
	console.log("ID"+ID)
   $('.MailBar_cover_color').show();
   $('.contract_update').show();
	    	    
 }); */
   	
	/*  提交修改后的信息  */
/* 	$('#update_submit').click(function () {
		var InstalledTime = $('.contract_update input[name="InstalledTime"]').val();
		var Customer = $('.contract_update input[name="Customer"]').val();
		var Status;
		var ResponsibleAndProcess = $('.contract_update input[name="ResponsibleAndProcess"]').val();
		var  ID = $(".contract_update .contract_title").attr("value");
		var Classify="Modify";
		
		
		if($('.contract_update input[name="Status"]').val()=="交付"){
			Status="1";
		}else if($('.contract_update input[name="Status"]').val()=="尾款"){
			Status="2";
		}else if($('.contract_update input[name="Status"]').val()=="完结"){
			Status="3";
		}
		
		
		console.log(InstalledTime);
		console.log(Customer);
		console.log(Status);
		console.log(ResponsibleAndProcess);
		console.log("ID"+ID);
		console.log(Classify);
		  $.ajax({
		      type : 'get',
		      url : 'HardwareOperate',
		      data : {
		    	  ID : ID,
		    	  InstalledTime : InstalledTime,
		    	  Customer : Customer,
		    	  Status : Status,
		    	  ResponsibleAndProcess : ResponsibleAndProcess,
		    	  Classify : Classify,
		      },
		      dataType : 'json',
		      success : function (data) {
		    	  console.log(data);
		      	
		          $.MsgBox.Alert('提示','修改成功');
		          $('.MailBar_cover_color').hide();
		          $('.contract_add').hide();
		      },
		      error : function () {
		          $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
		      }
		  }); 
	})  */  

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

/****************** 跳页 **********************/

function FistPage(arg) {
	/* window.location.href = arg + "1"; */
	if(arg.split('?')[0]=='SalesStatisticsRoute'){
		/* alert(arg.split('?')[0]); */
		 $('#search').val('search');
		 $("#top_text_from").attr("action", arg+ "1");
		  $('#top_text_from').submit();
	}else{ 
		
		window.location.href = arg+ "1";
	 } 
}
function UpPage(arg) {
	if(arg.split('?')[0]=='SalesStatisticsRoute'){
		/* alert(arg.split('?')[0]); */
		 $('#search').val('search');
		 $("#top_text_from").attr("action", arg);
		  $('#top_text_from').submit();
	}else{ 
		
		window.location.href = arg;
	 } 
}
function NextPage(arg) {
	if(arg.split('?')[0]=='SalesStatisticsRoute'){
		 $('#search').val('search');
		 $("#top_text_from").attr("action", arg);
		  $('#top_text_from').submit();
	}else{ 
		
		window.location.href = arg;
	 } 
	
}
function PageJump(arg) {
	var jumpNumber = document.getElementById("jumpNumber").value;
	if (jumpNumber == null || jumpNumber == 0) {
		jumpNumber = $('#currentPage').html();
	} else if (jumpNumber > parseInt($('#allPage').html())) {
		jumpNumber = $('#allPage').html();
	}
/* 	window.location.href = arg + jumpNumber; */
	if(arg.split('?')[0]=='SalesStatisticsRoute'){
		/* alert(arg.split('?')[0]); */
		 $('#search').val('search');
		 $("#top_text_from").attr("action", arg + jumpNumber);
		  $('#top_text_from').submit();
	}else{ 
		
		window.location.href = arg + jumpNumber;
	 } 
}
function LastPage(arg) {
	var jumpNumber = parseInt($('#allPage').html());
	/* window.location.href = arg + jumpNumber; */
	if(arg.split('?')[0]=='SalesStatisticsRoute'){
		/* alert(arg.split('?')[0]); */
		 $('#search').val('search');
		 $("#top_text_from").attr("action", arg + jumpNumber);
		  $('#top_text_from').submit();
	}else{ 
		
		window.location.href = arg + jumpNumber;
	 } 
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

</script>
</html>