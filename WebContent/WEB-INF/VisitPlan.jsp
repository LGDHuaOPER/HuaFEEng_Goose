<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>拜访计划</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" />
<link rel="stylesheet" type="text/css" href="css/VisitPlan.css">
</head>

<body>
	<!-- 	头部开始 -->
	<%@include file="top.jsp"%>
	<!-- 	头部结束 -->
	<div class="contain">
		<div class="content">
		<!-- 	=======================导航栏   开始 ================================== -->
			<%@include file="nav.jsp"%>
		<!-- 	=======================导航栏   结束  ================================== -->	
			
			<form id="top_text_from" name="top_text_from" method="post"
				action="VisitPlan">	
				<!-- 	=======================搜索   ================================== -->
			<div class="choose">
				<div class="search">
					<form action="" method="post" id="search_box">
					            	<input type="text" placeholder="输入关键字查询" class="tsearch" name="content" value=""><span class="ysearch"></span>
					</form>
					            	<span style="display:none" class="scatalog">${catalog}</span>
					            	<span style="display:none" class="scontent">${Content}</span>
				</div>	
			</div>
			 </form>  
				<!-- 	=======================表格  ================================== -->	
				<table border="1" cellspacing="0" cellspadding="0" id="table1" >
				<tr  style="background:#bfbfbf">
					<td>序号</td>
					<td>客户单位</td>
					<td>用户姓名</td>
					<td>MODEL</td>
					<td>SN</td>
					<td>客户等级</td>
					<td>拜访记录</td>
					<td style="display:none">装机时间</td>
					<td style="display:none">最后一次拜访时间</td>
					
				</tr>
				 <c:forEach var="orderInfo" items="${VisitPlans}" varStatus="status">
					<c:if test="${status.index>0}">
						<tr class="tbody_tr"  style="background:#fff">
							<td class="xunhao"> <a class="contract-edit" value="${orderInfo['ID']}" style="color:#000;">${status.index+(Page-1)*10}</a></td>
							<td class="ContractNO" style="display:none">${orderInfo['ContractNO']}</td>
							<td title="${orderInfo['CustomerName']}" class="CustomerName" style="max-width:300px">${orderInfo['CustomerName']}</td>       <!--1  -->
							<td>${orderInfo['Contact']}</td>    <!--2  -->
							<td  title="${orderInfo['Model']}">${orderInfo['Model']}</td>   <!--3  -->
							<td  title="${orderInfo['SN']}">${orderInfo['SN']}</td>	 <!--4  -->
							<td class="CustomerLevel">${orderInfo['CustomerLevel']}</td>	 <!--5  -->
							<td><i class="fa fa-eye VisitRecord-show" ></i></td>
							<td style="display:none" class="InstalledTime">${orderInfo['InstalledTime']}</td>
							<td style="display:none" class="VisitTime">${orderInfo['VisitTime']}</td>
						</tr>
					</c:if>
				</c:forEach>
			</table>
			<div class="cover-color"></div>

			<!-- -------------------------  添加拜访记录 ----------------------------------->
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
			 <c:choose>
					<c:when test="${queryType == 'common'}">
						<c:set var="queryUrl"
						value="VisitPlan?queryType=${queryType}&Page=">
						</c:set>
					</c:when>
					<c:otherwise>
						<c:set var="queryUrl"
						value="VisitPlan?queryType=${queryType}&Content=${Content}&Page=">
						</c:set>
					</c:otherwise>
				
				</c:choose> 
						
 			<div id="page">
				<div class="pageInfo">
					当前是第&nbsp;<span id="currentPage">${Page}</span>&nbsp;页,&nbsp;总计&nbsp;<span
						id="allPage">${AllPage}</span>页
				</div>
				<div class="changePage">
					<input type="button" class="bToggle" value="首页" id="fistPage"
						name="fistPage" onclick="FistPage('${queryUrl}')"> <input
						type="button" class="bToggle" value="上一页" id="upPage"
						onclick="UpPage('${queryUrl}${Page-1 }')"> <input
						type="button" class="bToggle" value="下一页" id="nextPage"
						onclick="NextPage('${queryUrl}${Page+1 }')"> 跳到第 <input
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
				
		</div>
	</div>
	
	<script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script>
<!-- <script src="js/swiper-3.4.1.jquery.min.js" type="text/javascript"
	charset="utf-8"></script> -->
<script src="js/ajaxfileupload.js" type="text/javascript"
	charset="utf-8"></script>
<script src="js/msgbox.js"></script>
<script src="js/VisitPlan.js"></script>
	<script>
	function FistPage(arg) {
		/* window.location.href = arg + "1"; */
		if(arg.split('?')[0]=='VisitPlan'){
			 $('#search').val('search');
			 $("#top_text_from").attr("action", arg+ "1");
			  $('#top_text_from').submit();
		}else{ 
			
			window.location.href = arg+ "1";
		 } 
	}
	function UpPage(arg) {
		if(arg.split('?')[0]=='VisitPlan'){
			 $('#search').val('search');
			 $("#top_text_from").attr("action", arg);
			  $('#top_text_from').submit();
		}else{ 
			
			window.location.href = arg;
		 } 
	}
	function NextPage(arg) {
		/* alert(3) */
		if(arg.split('?')[0]=='VisitPlan'){
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
		if(arg.split('?')[0]=='VisitPlan'){
			 $('#search').val('search');
			 $("#top_text_from").attr("action", arg + jumpNumber);
			  $('#top_text_from').submit();
		}else{ 
			
			window.location.href = arg + jumpNumber;
		 } 
	}
	function LastPage(arg) {
		var jumpNumber = parseInt($('#allPage').html());
		if(arg.split('?')[0]=='VisitPlan'){
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
</body>
</html>