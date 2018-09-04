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
<title>机台统计</title>
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
<link rel="stylesheet" type="text/css" href="css/machine.css">
</head>
<body>
	<!-- 	头部开始 -->
	<%@include file="top.jsp"%>
	<!-- 	头部结束 -->
	<div class="contain">
		<div class="content">
		<!-- 	=======================导航栏   开始 ================================== -->
		<%@include file="nav.jsp"%>


<!-- 	=======================导航栏   结束 ================================== -->


			<form id="top_text_from" name="top_text_from" method="post"
				action="GetMachineDetailsRoute">
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
							value="${fn:split('客户单位,用户姓名,Model,SN,合同号,装机服务时间',',')}"></c:set>
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
				<tr style="background:#ccc">
					<td>序号</td>
					<td style="display:none">修改</td>
					<td>客户单位 </td>
					<td>用户姓名</td>
					<td>Model</td>
					<td>SN</td>
					<td>合同号</td>
					<td>装机服务时间</td>
					<td>合同配置</td>
					<td style="display:none">选择客户</td>
					<td>客户等级</td>
					<td>拜访记录</td>
					<td style="display:none">装机时间</td>
					<td style="display:none">最后一次拜访时间</td>
				</tr>
				
				<c:forEach var="orderInfo" items="${machine}" varStatus="status">
					<c:if test="${status.index>0}">
						<tr  class="tbody_tr"  style="background:#fff">
							<td value="${orderInfo['ID']}"  class="contract-edit" style="cursor:pointer">${status.index+(currentPage-1)*10}</td>
							<td  style="display:none"> <i class="fa fa-edit" value="${orderInfo['ID']}"></i></td>
							<td title="${orderInfo['CustomerUnit']}" class="CustomerUnit">${orderInfo['CustomerUnit']}</td>
							<td>${orderInfo['CustomerName']}</td>
							<td title="${orderInfo['Model']}">${orderInfo['Model']}</td>
							<td title="${orderInfo['SN']}">${orderInfo['SN']}</td>
							<td title="${orderInfo['ContractNO']}">${orderInfo['ContractNO']}</td>
							<td>${orderInfo['InstalledTime']}</td>
							<td><i class="fa fa-eye contract-show" value="${orderInfo['ID']}"></i></td>
							<td class="CustomerID"  title="${orderInfo['CustomerID']}" style="width:150px;display:none;">${orderInfo['CustomerID']}</td>
							<td class="CustomerLevel">${orderInfo['CustomerLevel']}</td>	 <!--5  -->
							<td><i class="fa fa-eye VisitRecord-show" ></i></td>
							<td style="display:none" class="InstalledTime">${orderInfo['InstalledTime']}</td>
							<td style="display:none" class="VisitTime">${orderInfo['VisitTime']}</td>
						</tr>
					</c:if>
				</c:forEach> 
			</table>	
			<div class="cover-color" style="display: none;"></div>
			<div class="cover-color2" style="display: none;"></div>
			<!-- 添加拜访记录 -->
			<div class="visitRecord" style="display:none">
				<div class="visitRecord_title">拜访记录</div>
				<div class="visitRecord_close">关闭</div>
				<div class="basic_info">
					<div class="choose2">
						<input type="button" value="添加" class="bToggle add_visit" >
						<span class="MachineDetailsID" style="display:none"></span>
					</div>
					<table border="1" cellspacing="0" cellpadding="0"
						class="visitRecord_basic">
						<thead>
							<tr>
								<th>拜访频率</th>
								<th>拜访时间</th>
								<th>工程师</th>
								<th>备注</th>
							</tr>
						</thead>
						<tbody>
							
						</tbody>
					</table>
				</div>
				<div class="edit_btn">
					<input type="button" value="提交" class="bToggle" id="visit_submit">
					<input type="button" value="取消" class="bToggle" id="visit_cancel">
				</div>
			</div>

			<!-- 添加合同 -->
			<div class="contract_add" style="display: none;">
				<div class="contract_title">添加机台统计</div>
				<div class="contractAdd_close">关闭</div>
				<div class="basic_info">
					<div class="table_title">机台统计</div>
					<table border="1" cellspacing="0" cellpadding="0"
						class="contract_basic">
						<tbody>
							<tr>
								<td class="addInsertId"><i class="relevance"></i>客户单位</td>
								<td>
								<!-- <input type="text" name="CustomerUnit" value=""> -->
								<div class="out_search">
										<input type="search" name="CustomerUnit" class="CustomerUnit">
											 <select name="CustomerUnit" multiple style="width: 248px;height: 100px;"></select>
									</div>
								</td>
								<td><i class="relevance"></i>用户姓名</td>
								<td>
								<div class="out_search">
										<input type="search" name="CustomerName" class="CustomerName" readonly="readonly">
									</div>
								</td>
							</tr>
							<tr>
								<td>Model</td>
								<td><input type="text" name="Model" value=""></td>
								<td>SN</td>
								<td><input type="text" name="SN" value=""></td>
							</tr>
							<tr>
								<td>合同号</td>
								<td><input type="text" name="ContractNO" value=""></td>
								<td>装机服务时间</td>
								<td><input type="date" name="InstalledTime" value=""></td>
							</tr>
							<tr>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="edit_btn">
					<input type="button" value="提交" class="bToggle" id="add_submit">
					<input type="button" value="取消" class="bToggle" id="add_cancel">
				</div>
			</div>
			<div class="contract_update" style="display: none;">
				<div class="contract_title">修改机台统计</div>
				<div class="contractUpdate_close">关闭</div>
				<div class="basic_info">
					<div class="table_title">机台统计</div>
					<table border="1" cellspacing="0" cellpadding="0"
						class="contract_basic">
						<tbody>
							<tr>
								<td class="updateInsertId"><i class="relevance"></i>客户单位</td>
								<td>
								<div class="out_search">
										<input type="search" name="CustomerUnit" class="CustomerUnit">
											 <select name="CustomerUnit" multiple style="width: 248px;height: 100px;"></select>
									</div>
								</td>
								<td><i class="relevance"></i>用户姓名</td>
								<td>
								<div class="out_search">
										<input type="search" name="CustomerName" class="CustomerName" readonly="readonly">
									</div>
								</td>
							</tr>
							<tr>
								<td>Model</td>
								<td><input type="text" name="Model" value=""></td>
								<td>SN</td>
								<td><input type="text" name="SN" value=""></td>
							</tr>
							<tr>
								<td>合同号</td>
								<td><input type="text" name="ContractNO" value=""></td>
								<td>装机服务时间</td>
								<td><input type="date" name="InstalledTime" value=""></td>
							</tr>
							<tr>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="edit_btn">
					<input type="button" value="提交" class="bToggle" id="update_submit">
					<input type="button" value="取消" class="bToggle" id="update_cancel">
				</div>
			</div>

			<div class="contract" style="display: none;">
				<div class="contract_title">合同配置信息</div>
				<div class="contract_close">关闭</div>
				<div class="contarct_info">
					<table border="1" cellspacing="0" cellpadding="0"
						class="contractPage">
						<thead>
							<tr>
								<th>订单号</th>
								<th>型号</th>
								<th>描述</th>
								<th>数量</th>
								<th>货期</th>
								<th>预计货期</th>
								<th>货运单号</th>
								<th>订单状态</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
			</div>
			
			 	 <c:choose>
					<c:when test="${queryType == 'common'}">
						<c:set var="queryUrl"
						value="MachineDetails?type1=${classify1 }&searchContent1=${parameter1}&selected=${str}&currentPage=">
						</c:set>
					</c:when>
					<c:otherwise>
						<c:set var="queryUrl"
						value="GetMachineDetailsRoute?type1=${classify1 }&searchContent1=${parameter1}&type2=${classify2}&searchContent2=${parameter2}&selected=${queryType}&currentPage=">
						</c:set>
					</c:otherwise>
				
				</c:choose> 
			

			<div id="page">
				<div class="pageInfo">
					当前是第&nbsp;<span id="currentPage">${currentPage }</span>&nbsp;页,&nbsp;总计&nbsp;<span
						id="allPage">${pageCounts }</span>页
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
	<div class="MailBar_cover_color" style="display: none;"></div>

	<!-- 添加是否可以发货信息 -->
	
</body>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/swiper-3.4.1.jquery.min.js" type="text/javascript"
	charset="utf-8"></script>
<!-- <script src="js/ajaxfileupload.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/msgbox.js"></script>
<!-- <script src="js/msgbox_unload.js"></script> -->
<script src="js/machine.js"></script>
<script type="text/javascript">
/****************** 跳页 **********************/

function FistPage(arg) {
	/* window.location.href = arg + "1"; */
	if(arg.split('?')[0]=='GetMachineDetailsRoute'){
		 $('#search').val('search');
		 $("#top_text_from").attr("action", arg+ "1");
		  $('#top_text_from').submit();
	}else{ 
		
		window.location.href = arg+ "1";
	 } 
}
function UpPage(arg) {
	if(arg.split('?')[0]=='GetMachineDetailsRoute'){
		 $('#search').val('search');
		 $("#top_text_from").attr("action", arg);
		  $('#top_text_from').submit();
	}else{ 
		
		window.location.href = arg;
	 } 
}
function NextPage(arg) {
	if(arg.split('?')[0]=='GetMachineDetailsRoute'){
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
	/* window.location.href = arg + jumpNumber; */
	if(arg.split('?')[0]=='GetMachineDetailsRoute'){
		 $('#search').val('search');
		 $("#top_text_from").attr("action", arg + jumpNumber);
		  $('#top_text_from').submit();
	}else{ 
		
		window.location.href = arg + jumpNumber;
	 } 
}
function LastPage(arg) {
	var jumpNumber = parseInt($('#allPage').html());
	if(arg.split('?')[0]=='GetMachineDetailsRoute'){
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
	if (<%=request.getAttribute("queryType").equals("common")%>) 
	{
		$('.select2').hide();
	} 
	else if(<%=request.getAttribute("queryType").equals("singleSelect")%>){
		$('.select2').hide();
	}
	else {
		$('.select2').show();
	}
	

});
	
</script>
</html>