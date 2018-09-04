<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>产品型号</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="css/info_product.css">
<link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" />
<link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">
</head>
<body>
	<%@include file="top.jsp"%>
	<div class="contain">
		<div class="content">
			<input type="hidden" value="${supplierName}" id='supplierName'/>
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
			<form id="top_text_from" name="top_text_from" method="post"
				action="Equipment">
				<div class="select-content">
					<div class="select">
						设备型号<input type="text" id="searchContent1" name="model" value="${modelName }" style="margin-left: 5px; border:solid 1px #000;">
					</div>
					<input type="hidden" name="query_type" value="mix">
					<div class="select">
						供应商 <select id="searchContent2"
							name="supplier"  style="margin-left: 5px; border:solid 1px #000;">
							<c:forEach items="${suppliers }" var="supplier"
								varStatus="status">
								<c:if test="${status.index>0}">
									<c:choose>
										<c:when test="${supplier.ID==supplierName }">
											<option value="${supplier.ID }" selected="selected">${supplier.Name}</option>
										</c:when>
										<c:otherwise>
											<option value="${supplier.ID }">${supplier.Name}</option>
										</c:otherwise>
									</c:choose>
								</c:if>
							</c:forEach>
						</select>
					</div>
					<div class="select-button">
						<input type="button" value="搜索" class="bToggle" onclick="Search()">
						<input type="button" value="取消" class="bToggle" onclick="Cancel()">
					</div>
				</div>
				<div class="choose">
					<input type="button" value="添加" class="bToggle"
						onclick="AddContract()">
				</div>
			</form>
			<table border="1" cellspacing="0" cellspadding="0" id="table1">
				<tr>
					<td>序号</td>
					<td>修改</td>
					<td>设备型号</td>
					<td>描述</td>
					<td>单位</td>
					<td>交货期</td>
					<td>商品产地</td>
					<td>商品编号</td>
					<td>商品类别</td>
					<td>供应商</td>
				</tr>
				<!-- 				<tr> -->
				<!-- 					<td>1</td> -->
				<!-- 					<td><i class="fa fa-edit edit" value="1111"></i></td> -->
				<!-- 					<td>3</td> -->
				<!-- 					<td>4</td> -->
				<!-- 					<td>5</td> -->
				<!-- 					<td>6</td> -->
				<!-- 					<td>7</td> -->
				<!-- 					<td>8</td> -->
				<!-- 					<td>9</td> -->
				<!-- 					<td>10</td> -->
				<!-- 				</tr> -->
				<c:forEach items="${equipments}" var="equipment" varStatus="status">

					<c:if test="${status.index>0}">
						<tr>
							<td>${status.index+(currentPage-1)*10}</td>
							<td><i class="fa fa-edit edit" value="${equipment.ID }"></i></td>
							<td>${equipment.Model }</td>
							<td>${equipment.Remarks }</td>
							<td>${equipment.EquipmentUnit }</td>
							<td>${equipment.DeliveryTime }</td>
							<td>${equipment.SourceArea }</td>
							<td>${equipment.ItemCode }</td>
							<td>${equipment.CommodityCategory }</td>
							<td>${equipment.Supplier }</td>
						</tr>
					</c:if>

				</c:forEach>
			</table>
			<div class="cover-color"></div>
			<div class="info_add">
				<div class="title">添加信息</div>
				<div class="infoAdd_close">关闭</div>
				<div class="basic_info">
					<table border="1" cellspacing="0" cellpadding="0"
						class="contract_basic">
						<tbody>
							<input type="hidden" name="id">
							<tr>
								<td>设备型号</td>
								<td><input type="text" name="module"></td>
								<td>单位</td>
								<td><input type="text" name="equipment_unit"></td>
								<td>交货期</td>
								<td><input type="text" name="delivery_time"></td>
							</tr>
							<tr>
								<td>商品产地</td>
								<td><input type="text" name="source_area"></td>
								<td>商品编号</td>
								<td><input type="text" name="item_code"></td>
								<td>商品类别</td>
								<td><input type="text" name="commodity_category"></td>
							</tr>
							<tr>
								<td>供应商</td>
								<td><select name="suppiler">
										<c:forEach items="${suppliers }" var="supplier"
											varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${supplier.ID }">${supplier.Name }</option>
											</c:if>
										</c:forEach>
								</select></td>
								<td>描述</td>
								<td><textarea name="remarks" cols="23" rows="3"
										style="resize: none"></textarea></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="edit_btn">
					<input type="button" value="提交" class="bToggle" id="add_submit">
					<input type="button" value="取消" class="bToggle" id="add_cancel">
				</div>
			</div>
			<div class="info_update">
				<div class="title">修改信息</div>
				<div class="update_close">关闭</div>
				<div class="basic_info">
					<table border="1" cellspacing="0" cellpadding="0"
						class="contract_basic">
						<tbody>
							<input type="hidden" name="id">
							<tr>
								<td>设备型号</td>
								<td><input type="text" name="module"></td>

								<td>单位</td>
								<td><input type="text" name="equipment_unit"></td>
								<td>交货期</td>
								<td><input type="text" name="delivery_time"></td>
							</tr>
							<tr>
								<td>商品产地</td>
								<td><input type="text" name="source_area"></td>
								<td>商品编号</td>
								<td><input type="text" name="item_code"></td>
								<td>商品类别</td>
								<td>
								<input type="text" name="commodity_category">
								</td>
							</tr>
							<tr>
								<td>供应商</td>
								<td><select name="suppiler">
										<c:forEach items="${suppliers }" var="supplier"
											varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${supplier.ID }" text="${supplier.Name }" >${supplier.Name }</option>
											</c:if>
										</c:forEach>
								</select></td>
								<td>描述</td>
								<td><textarea name="remarks" cols="23" rows="3"
										style="resize: none"></textarea></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="edit_btn">
					<input type="button" value="提交" class="bToggle" id="update_submit">
					<input type="button" value="取消" class="bToggle" id="update_cancel">
				</div>
			</div>
			<c:choose>
				<c:when test="${queryType=='common'}">
					<c:set var="queryUrl"
						value="Equipment?query_type=${queryType}&currentPage="></c:set>
				</c:when>
				<c:when test="${queryType=='mix'}">
					<c:set var="queryUrl"
						value="Equipment?query_type=${queryType}&supplier=${supplierName }&model=${modelName }&currentPage="></c:set>
				</c:when>
			</c:choose>
			<div id="page">
				<div class="pageInfo">
					当前是第&nbsp;<span id="currentPage">${currentPage}</span>&nbsp;页,&nbsp;总计&nbsp;<span
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
</body>
<script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script>
<script src="js/swiper-3.4.1.jquery.min.js" type="text/javascript"
	charset="utf-8"></script>
<script src="js/ajaxfileupload.js" type="text/javascript"
	charset="utf-8"></script>
<script src="js/msgbox.js"></script>
<script src="js/info_product.js"></script>
<script type="text/javascript">
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
		
	});
</script>
</html>

