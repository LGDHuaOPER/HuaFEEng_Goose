<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>软件产品管理</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="css/SoftwareProduct.css">
<link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" />
<link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">
</head>
<body>
	<%@include file="top.jsp"%>
	<div class="contain">
		<div class="content">
		<!-- 	=======================导航栏   开始 ================================== -->

			<%@include file="nav.jsp"%>
<!-- 	=======================导航栏   结束 ================================== -->
			<div style="display: none">
				<input type="text" value="" name="query_type"> <input
					type="text" value="" name="classify1"> <input type="text"
					value="" name="classify2"> <input type="text" value=""
					name="parameter1"> <input type="text" value=""
					name="parameter2">
			</div>
			
				 <form action="SoftwareProduct" method="GET" id="search_box"  style="position: absolute;clear:both;right: 5%;top: 80px;">
	            	<input type="text" placeholder="输入关键字查询" class="tsearch" name="content"  style="" value="${content}">
	            	<input type="text"  name="currentPage"  style="display:none;" value="${currentPage}">
	            	<input type="text"  name="queryType"  style="display:none;" value="select">
	            	<span class="ysearch" style="right: 27%" onclick="Search()"></span>
            	</form>
            	<div class="choose">
					<c:forEach var="authoritiy" items="${authorities}">
	                   	<c:if test="${authoritiy=='SoftwareProductOperate'}">
							  <input type="button" value="添加" class="bToggle chooseBtn" onclick="AddContract()">
	                   	</c:if>
	               	</c:forEach>
			    </div>
			
			
			<table border="1" cellspacing="0" cellspadding="0" id="table1">
				<tr style="background:#bfbfbf;">
					<td style="width:4.9%;">序号</td>
					<td style="width:16.33%;">型号</td>
					<td style="width:16.33%;">名称</td>
					<td style="width:16.33%;">类别</td>
					<td style="width:16.33%;">品牌</td>
					<td style="width:12.25%;">单价</td>
					<td style="width:17.48%;">备注</td>
					
				</tr>
				<c:forEach var="orderInfo" items="${datas}" varStatus="status">
					<c:if test="${status.index>0}">
						<tr>
							<td  class="toggleedit" value="${orderInfo['ID']}">${status.index+(currentPage-1)*10}</td><!-- 0 -->
							<td class="Model overhid" title="${orderInfo['Model']}">${orderInfo['Model']}</td> 
							<td  class="PackageName overhid" title="${orderInfo['PackageName']}">${orderInfo['PackageName']}</td>   
							<td class="PackageClassify">${orderInfo['PackageClassify']}</td>  
							<td class="Brand">${orderInfo['Brand']}</td>     
							<td class="t1_UnitPrice">${orderInfo['Price']}</td>   
							<td class="Remarks">${orderInfo['Remarks']}</td>    
							<td class="Count hide">${orderInfo['Count']}</td> 
							<td class="HourlyWage hide">${orderInfo['HourlyWage']}</td> 
							<td class="Cycle hide">${orderInfo['Cycle']}</td> 
							<td class="PremiumIndex hide">${orderInfo['PremiumIndex']}</td> 
							<td class="MaintenanceIndex hide">${orderInfo['MaintenanceIndex']}</td> 
							<td class="TransportAllowance hide">${orderInfo['TransportAllowance']}</td> 
							<td class="AccommodationAllowance hide">${orderInfo['AccommodationAllowance']}</td> 
							<td class="MissionAllowance hide">${orderInfo['MissionAllowance']}</td> 
							
						</tr>
					</c:if>
				</c:forEach>
			</table>
		
			<div class="info_add" style="display: none;">
				<div class="title">添加软件产品管理</div>
				<div class="infoAdd_close">关闭</div>
				<div class="ProductMsgTitle">软件产品管理</div>
				<div class="ProductMsg">
					<ul class="ProductMsg_leftul">
						<li>
							<span class="ProductMsg_span">组件名称</span>
							<input type="text" name="PackageName" value="" >
						</li>
						<li>
							<span class="ProductMsg_span">型号</span>
							<input type="text" name="Model" value="" >
						</li>
						<li>
							<span class="ProductMsg_span">组件类别</span>
							<input type="text" name="PackageClassify" value="" >
						</li>
						<li>
							<span class="ProductMsg_span">品牌</span>
							<input type="text" name="Brand" value="" >
						</li>
						<li>
							<span class="ProductMsg_span">维护指数</span>
							<input type="text" name="MaintenanceIndex"  onkeyup="value=value.replace(/[^\d\.]/g,'')">
						</li>
					</ul>
					<ul class="ProductMsg_middleul">
						<li>
							<span class="ProductMsg_span">人数</span>
							<input type="text" name="Count" onkeyup="value=value.replace(/[^\d\.]/g,'')" >
						</li>
						<li>
							<span class="ProductMsg_span">时薪</span>
							<input type="text" name="HourlyWage" onkeyup="value=value.replace(/[^\d\.]/g,'')">
						</li>
						<li>
							<span class="ProductMsg_span">周期(小时)</span>
							<input type="text" name="Cycle" onkeyup="value=value.replace(/[^\d\.]/g,'')">
						</li>
						<li>
							<span class="ProductMsg_span">溢价指数</span>
							<input type="text" name="PremiumIndex" onkeyup="value=value.replace(/[^\d\.]/g,'')" id="PremiumIndex">
						</li>
					</ul>
					<ul class="ProductMsg_rightul">
						<li>
							<span class="ProductMsg_span">交通补贴</span>
							<input type="text" name="TransportAllowance" onkeyup="value=value.replace(/[^\d\.]/g,'')" >
						</li>
						<li>
							<span class="ProductMsg_span">食宿补贴</span>
							<input type="text" name="AccommodationAllowance" onkeyup="value=value.replace(/[^\d\.]/g,'')">
						</li>
						<li>
							<span class="ProductMsg_span">出差补贴</span>
							<input type="text" name="MissionAllowance" onkeyup="value=value.replace(/[^\d\.]/g,'')" >
						</li>
						<li>
							<span class="ProductMsg_span RemarksSpan">备注</span>
							<!-- <input type="text" name="Remarks" value="" id="Remarks"> -->
							<textarea class="Remarks"></textarea>
						</li>
					</ul>
				</div>
				<div class="edit_btn">
					<input type="button" value="提交" class="bToggle" id="add_submit">
					<input type="button" value="取消" class="bToggle" id="add_cancel">
				</div>
			</div>
			<div class="info_update" style="display: none;">
				<div class="title">修改软件产品管理</div>
				<div class="infoAdd_close">关闭</div>
				<div class="ProductMsgTitle">软件产品管理</div>
				<div class="ProductMsg">
					<ul class="ProductMsg_leftul">
						<li>
							<span class="ProductMsg_span">组件名称</span>
							<input type="text" name="PackageName" value="" >
						</li>
						<li>
							<span class="ProductMsg_span">型号</span>
							<input type="text" name="Model" value="" >
						</li>
						<li>
							<span class="ProductMsg_span">组件类别</span>
							<input type="text" name="PackageClassify" value="" >
						</li>
						<li>
							<span class="ProductMsg_span">品牌</span>
							<input type="text" name="Brand" value="" >
						</li>
						<li>
							<span class="ProductMsg_span">维护指数</span>
							<input type="text" name="MaintenanceIndex"  onkeyup="value=value.replace(/[^\d\.]/g,'')">
						</li>
					</ul>
					<ul class="ProductMsg_middleul">
						<li>
							<span class="ProductMsg_span">人数</span>
							<input type="text" name="Count" onkeyup="value=value.replace(/[^\d\.]/g,'')" >
						</li>
						<li>
							<span class="ProductMsg_span">时薪</span>
							<input type="text" name="HourlyWage" onkeyup="value=value.replace(/[^\d\.]/g,'')">
						</li>
						<li>
							<span class="ProductMsg_span">周期(小时)</span>
							<input type="text" name="Cycle" onkeyup="value=value.replace(/[^\d\.]/g,'')">
						</li>
						<li>
							<span class="ProductMsg_span">溢价指数</span>
							<input type="text" name="PremiumIndex" onkeyup="value=value.replace(/[^\d\.]/g,'')" id="PremiumIndex">
						</li>
					</ul>
					<ul class="ProductMsg_rightul">
						<li>
							<span class="ProductMsg_span">交通补贴</span>
							<input type="text" name="TransportAllowance" onkeyup="value=value.replace(/[^\d\.]/g,'')" >
						</li>
						<li>
							<span class="ProductMsg_span">食宿补贴</span>
							<input type="text" name="AccommodationAllowance" onkeyup="value=value.replace(/[^\d\.]/g,'')">
						</li>
						<li>
							<span class="ProductMsg_span">出差补贴</span>
							<input type="text" name="MissionAllowance" onkeyup="value=value.replace(/[^\d\.]/g,'')" >
						</li>
						<li>
							<span class="ProductMsg_span RemarksSpan">备注</span>
							<!-- <input type="text" name="Remarks" value="" id="Remarks"> -->
							<textarea class="Remarks"></textarea>
						</li>
					</ul>
				</div>
				<div class="edit_btn">
					<input type="button" value="提交" class="bToggle" id="update_submit">
					<input type="button" value="取消" class="bToggle" id="update_cancel">
				</div>
			</div>
			
			
			<c:choose>
				<c:when test="${queryType == common}">
					<c:set var="queryUrl" value="SoftwareProduct?queryType=${queryType}&currentPage="></c:set>
				</c:when>
				<c:otherwise>
					<c:set var="queryUrl"
					value="SoftwareProduct?queryType=${queryType}&content=${content}&currentPage="></c:set>
				</c:otherwise>
			</c:choose>
			<div id="page">
				<div class="pageInfo">
					当前是第&nbsp;<span id="currentPage">${currentPage}</span>&nbsp;页,&nbsp;总计&nbsp;<span
						id="allPage">${pageCounts }</span>页
				</div>
				<div class="changePage">
					<input type="button" class="bToggle" value="首页" id="fistPage"
						name="fistPage" onclick="FistPage('${queryUrl}')"> <input type="button"
						class="bToggle" value="上一页" id="upPage" onclick="UpPage('${queryUrl}${currentPage-1 }')">

					<input type="button" class="bToggle" value="下一页" id="nextPage"
						onclick="NextPage('${queryUrl}${currentPage+1 }')"> 跳到第 <input type="text"
						id="jumpNumber" name="jumpNumber" class="jumpNumber"
						style="width: 30px; color: #000"
						onkeyup="value=value.replace(/[^\d]/g,'') "> 页 <input
						type="button" class="bToggle" value="GO" id="Gotojump"
						name="Gotojump" onclick="PageJump('${queryUrl}')"> <input type="button"
						class="bToggle" value="尾页" id="lastPage" name="lastPage"
						onclick="LastPage('${queryUrl}')">
				</div>
			</div>
		</div>
	</div>
		<div class="cover-color" style="display: none;"></div>
</body>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/swiper-3.4.1.jquery.min.js" type="text/javascript" charset="utf-8"></script>
<script src="js/msgbox.js"></script>
<script src="js/SoftwareProduct.js"></script>
<script type="text/javascript">
	if($(".choose").children().length >0){
		$(".toggleedit").addClass("edit")
	}
	else{
		$(".toggleedit").removeClass("edit")
	}


	function FistPage(arg) {
		window.location.href = arg + "1";
	}
	function UpPage(arg) {
		window.location.href = arg;
	}
	function NextPage(arg) {
		// alert(arg)
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
		if($("#hideQuery").attr("value") == "mix"){
			$(".select2").show();
		}
		else{
			$(".select2").hide();
		}
	});
</script>
</html>

