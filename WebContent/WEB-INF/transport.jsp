	<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="UTF-8">
<title>物流统计</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<!-- <link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css"> -->
<link rel="stylesheet" type="text/css" href="css/libs/bootstrap-grid-form-button-res-icon-list.min.css">
<link rel="stylesheet" type="text/css" href="css/global/global_table_style.css">
<link rel="stylesheet" type="text/css" href="css/transport.css">
<style>
	.content {
		padding-bottom: 10px !important;
	}

	/*布局*/
	html,body{
	    width:100%;
	    height:100%;
	}

	#Transport_wrapper {
	    position:fixed;
	    overflow:auto;
	    width:100%;
	    height:100%;
	    box-sizing:border-box;
	}

	#Transport_sticker {
	    width:100%;
	    min-height:100%;
	    box-sizing:border-box;
	}

	#Transport_sticker-con {
	    padding-bottom:40px;
	    box-sizing:border-box;
	}

	#Transport_footer {
	    margin-top:-40px;
	}

	.clear_float {
		height: 2px;
		width: calc(100% - 2px);
		clear:both;
	}

	.content .select-content {
		padding-top: 1px !important;
	}

	.content .choose {
		margin-bottom: 1px !important;
	}

	.content .select-content {
		margin-bottom: 1px;
	}

	.content .choose input[type="button"] {
		width: auto !important;
		height: auto !important;
		border: 1px solid transparent !important;
		margin: 30px 0 1px 0 !important;
	}

	.content .select-button input[type="button"] {
		width: auto !important;
		height: auto !important;
		border: 1px solid transparent !important;
		margin-top: -5px !important;
	}

	.g-nav-ul li {
		height: 80px !important;
		line-height: 80px !important;
	}

	hr {
	    margin-top: 1px;
	    margin-bottom: 1px;
	}

	/*表格自定义*/
	.gl_table_style_wrapper {
		overflow-x: scroll;
		overflow-y: hidden;
	}

	#hetonghuoqiSel, #shijihuoqiSel, #yujihuoqiSel {
		font-size: 12px;
	}

	span.glyphicon {
		cursor: pointer;
	}

	.gl_table_style th {
		font-weight: 550px;
	}

	.gl_table_style th:nth-child(1), .gl_table_style td:nth-child(1) {
		max-width: 50px !important;
		min-width: 50px !important;
		width: 50px;
	}

	.gl_table_style th:nth-child(2), .gl_table_style td:nth-child(2) {
		max-width: 40px !important;
		min-width: 40px !important;
		width: 40px;
	}

	.gl_table_style th:nth-child(5), .gl_table_style td:nth-child(5) {
		max-width: 90px !important;
		min-width: 90px !important;
		width: 90px;
	}

	.D3_D2_th, .D3_D4_th, .D4_D2_th, .D3_D1_th, .D3_D2, .D3_D4, .D4_D2, .D3_D1 {
		max-width: 140px;
		min-width: 140px;
		width: 140px;
		display: none;
	}

	.D3_D2, .D3_D4, .D4_D2, .D3_D1 {
		color: #fff;
		opacity: 0;
	}

	.hetonghuoqi_th, .shijihuoqi_th, .yujihuoqi_th {
		max-width: 108px;
		min-width: 108px;
		width: 108px;
	}

	.dontConpress {
		max-width: 90px;
		min-width: 90px;
		width: 90px;
	}

</style>
</head>
<body>
	<div class="loading_div_g_div" style="position: fixed;top: 0;bottom: 0;left: 0;right: 0;z-index: 100;width: 100vw;height: 100vh;background-color: #5bc0de;filter:alpha(opacity=90);-moz-opacity:0.9;-khtml-opacity:0.9;opacity: 0.9;display: -webkit-flex;display: flex;justify-content: center;align-items: center;">
	    <img src="image/loading/Spinner-1s-200px.gif" alt="loading。。。">
	</div>
	<div id="Transport_wrapper">
	    <div id="Transport_sticker">
	        <div id="Transport_sticker-con">
				<!-- 	头部开始 -->

				<%@include file="top.jsp"%>
				<!-- 	头部结束 -->
				<div class="contain">
				<c:forEach var="authority" items="${authorities}" varStatus="status" >
				<c:if test="${authority=='OrderInfoReview1'}">
					<input type="hidden" id="review_name1" value="OrderInfoReview1">
				</c:if>	
				</c:forEach>
				
				<c:forEach var="authority" items="${authorities}" varStatus="status" >
				<c:if test="${authority=='OrderInfoReview2'}">
					<input type="hidden" id="review_name2" value="OrderInfoReview2">
				</c:if>	
				</c:forEach>
				
					<div class="content">
						<!-- 导航栏   开始 -->
						<%@include file="nav.jsp"%>
						<!-- 导航栏   结束 -->
						<div style="display: none">
							<c:if test="${queryType=='common'}">
								<input type="text" value="" name="classify1">
								<input type="text" value="" name="classify2">
								<input type="text" value="" name="parameter1">
								<input type="text" value="" name="parameter2">
							</c:if>
							<c:if test="${queryType=='singleSelect'}">
								<input type="text" value="${classify1 }" name="classify1">
								<input type="text" value="$" name="classify2">
								<input type="text" value="${parameter1 }" name="parameter1">
								<input type="text" value="" name="parameter2">
							</c:if>
							<c:if test="${queryType=='mixSelect'}">
								<input type="text" value="${classify1 }" name="classify1">
								<input type="text" value="${classify2 }" name="classify2">
								<input type="text" value="${parameter1 }" name="parameter1">
								<input type="text" value="${parameter2 }" name="parameter2">
							</c:if>
							<input type="text" value="${queryType}" name="query_type" id="transport_queryType">
						</div>
						<div class="transport_calc">
						<span style="display: none;" class="transport_calc_data" data-idata='${Statistics}'>${Statistics}</span>
							<table class="transport_calc_table">
								<thead>
									<tr>
										<th>所有未发货</th>
										<th>未包括等待客户付款</th>
										<th>当前已逾期</th>
										<th>存在逾期风险</th>
										<th>本月已发</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="AllNoSend_td"></td>
										<td class="OtherNoSend_td"></td>
										<td class="Overdue_td"></td>
										<td class="OverdueRisk_td"></td>
										<td class="ShippedMonth_td"></td>
									</tr>
								</tbody>
								<tfoot style="display: none;">
									<tr>
										<td colspan="5"><span class="transportdetail_td">详细记录&nbsp;</span><span class="triangle_right"></span></td>
									</tr>
								</tfoot>
							</table>
						</div>
						
						<form id="top_text_from" name="top_text_from" method="post" action="GetOrderRoute">
							<input type="text" id="search" name="isSearch" value="search" style="display: none;">
							<div class="select-content">
								<label> <c:choose>
										<c:when test="${queryType=='mixSelect'}">
											<label><input type="radio" id="singleSelect" name="selected" class="singleSelect" value="singleSelect" onclick="Check(this.value)">单一查询 </label>&nbsp;&nbsp;&nbsp;
											<label><input type="radio" id="mixSelect" name="selected" value="mixSelect" checked="checked" onclick="Check(this.value)">组合查询</label>&nbsp;&nbsp;&nbsp;<br>
										</c:when>
										<c:otherwise>
											<label><input type="radio" id="singleSelect" name="selected" class="singleSelect" value="singleSelect" checked="checked" onclick="Check(this.value)">单一查询 </label>&nbsp;&nbsp;&nbsp;
											<label><input type="radio" id="mixSelect" name="selected" value="mixSelect" onclick="Check(this.value)">组合查询</label>&nbsp;&nbsp;&nbsp;<br>
										</c:otherwise>
									</c:choose> <c:set var="dropdown" value="${fn:split('销售代表,合同号,客户名称,区域,合同名称,联系人,联系方式,合同签订日期,合同货期,实际货期,预计货期,合同状态,装机时间,装机地点,备注,PO(只能单一搜索),SO(只能单一搜索),供应商(只能单一搜索),合同类型,',',')}"></c:set>
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
										
										<c:if test="${queryType=='singleSelect' || queryType=='mixSelect'}">
											 <c:choose>
												<c:when test="${ classify1 =='合同货期' || classify1 == '实际货期' || classify1== '预计货期' }">
													<input type="text" id="searchContent1" name="searchContent1"
														style="display: none;">
													<input type="date" name="start_time1" class="time startTime"
														style="margin-left: -5px; display: inline-block;" value="${start_time1 }">
													<span class="time" style="display: inline-block;">-</span>
													<input type="date" name="end_time1" class="time endTime"
														style="display: inline-block;" value="${end_time1 }">
												</c:when>
												<c:otherwise>
													<input type="text" id="searchContent1" name="searchContent1" style="display: inline-block;" value="${parameter1}">
													<input type="date" name="start_time1" class="time startTime"  style="margin-left: -5px;">
													<span class="time">-</span>
													<input type="date" name="end_time1" class="time endTime">									
												</c:otherwise>
											</c:choose>
										</c:if>
										<c:if test="${queryType=='common'}">
											<input type="text" id="searchContent1" name="searchContent1" style="display: inline-block;" value="${searchContent1}">
											<input type="date" name="start_time1" class="time startTime" style="margin-left: -5px;">
											<span class="time">-</span>
											<input type="date" name="end_time1" class="time endTime">
										</c:if>
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
										<c:if test="${queryType!='mixSelect'}">
											<input type="text" id="searchContent2" name="searchContent2">
											<input type="date" name="start_time2" class="time startTime" style="margin-left: -5px;">
											<span class="time">-</span>
											<input type="date" name="end_time2" class="time endTime" >
										</c:if>
										<c:if test="${queryType=='mixSelect'}">
											<c:choose>
												<c:when test="${ classify2 =='合同货期' || classify2 == '实际货期' || classify2== '预计货期' }">
													<input type="text" id="searchContent2" name="searchContent2"
														style="display: none;">
													<input type="date" name="start_time2" class="time startTime"
														style="margin-left: -5px; display: inline-block;" value="${start_time2 }">
													<span class="time" style="display: inline-block;">-</span>
													<input type="date" name="end_time2" class="time endTime"
														style="display: inline-block;" value="${end_time2 }">
												</c:when>
												<c:otherwise>
													<input type="text" id="searchContent2" name="searchContent2" value="${parameter2}">
													<input type="date" name="start_time2" class="time startTime"  style="margin-left: -5px;">
													<span class="time">-</span>
													<input type="date" name="end_time2" class="time endTime">
												</c:otherwise>
											</c:choose>
										</c:if>
									</div>
									<div class="select-button">
										<input type="button" value="搜索" class="btn btn-info" onclick="selectSearch()">
										<input type="button" value="取消" class="btn btn-warning" onclick="selectCancel()">
									</div>
							</div>
						            
							<div class="choose">
								<c:forEach var="authoritiy" items="${authorities}">
									<c:if test="${authoritiy=='ExportOrder'}">
										<input type="button" value="导出" class="btn btn-info" id="export"> 
									</c:if>
								</c:forEach>
							</div>
						</form>

					<!-- 主页面表格开始 -->
					<div class="gl_table_style_wrapper">
						<table class="gl_table_style" id="table1">
							<thead>
								<tr>
									<th>序号</th>
									<th>操作</th>
									<th>合同地区</th>
									<th>合同名称</th>
									<th>合同号</th>
									<th class="dontConpress">合同类型<span title="显示与隐藏" class="glyphicon glyphicon-plus" aria-hidden="true" id="fa-button1"></span></th>
									<th class="Customer_th">客户名称</th>
									<th class="Contact_th">联系人</th>
									<th class="ContactInfo_th">联系方式</th>
									<th class="SalesRepresentative_th">销售代表</th>
									<th class="DateOfSign_th">合同签订日期</th>
									<th style="cursor:pointer;" id="Date" class="hetonghuoqi_th">合同货期<br>
										<select style="width: 80px;" id="hetonghuoqiSel">
											<option value="All" class="All">所有订单</option>
											<option value="AllNoSend" class="AllNoSend">未发货订单</option>
											<option value="OtherNoSend" class="OtherNoSend">不包括等待客户付款的未发货订单</option>
										</select>
									</th>
									<th style="cursor:pointer;" class="shijihuoqi_th">实际货期<br>
										<select style="width: 80px;" id="shijihuoqiSel">
											<option value="All" class="All">所有订单</option>
											<option value="AllNoSend" class="AllNoSend">未发货订单</option>
											<option value="OtherNoSend" class="OtherNoSend">不包括等待客户付款的未发货订单</option>
										</select>
									</th>
									<th style="cursor:pointer;" class="yujihuoqi_th">预计货期<br>
										<select style="width: 80px;" id="yujihuoqiSel">
											<option value="All" class="All">所有订单</option>
											<option value="AllNoSend" class="AllNosend">未发货订单</option>
											<option value="OtherNoSend" class="OtherNosend">不包括等待客户付款的未发货订单</option>
										</select>
									</th>
									<th>预计收款日期</th>
									<th class="dontConpress">合同状态<span title="显示与隐藏" class="glyphicon glyphicon-plus" aria-hidden="true" id="fa-button2"></span></th>
									<th class="InstalledTimeSite_th">装机时间和地点</th>
									<th class="Remarks_th">备注</th>
									<th>合同配置</th>
									<th>供应商明细</th>
									<th>合同明细</th>
									<th class="dontConpress">PO总额<span title="显示与隐藏" class="glyphicon glyphicon-plus" aria-hidden="true" id="fa-button3"></span></th>
									<th class="D3_D2_th">实际货期-合同货期</th>
									<th class="D3_D4_th">实际货期-预计货期</th>
									<th class="D4_D2_th">预计货期-合同货期</th>
									<th class="D3_D1_th">实际货期-<br>合同签订日期</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="orderInfo" items="${orders}" varStatus="status">
									<c:if test="${status.index>0}">
										<c:choose>
										<c:when test="${orderInfo['Review']=='0'}">
										<tr class="change">
										</c:when>
										<c:otherwise>
										<tr>
										</c:otherwise>
										</c:choose>
											<td>${status.index+(currentPage-1)*10}</td>
										<c:choose>
											<c:when test="${orderInfo['Status']=='已完结'}">
												<td>该订单已完结</td>
											</c:when>
											<c:otherwise>
												<td><span class="glyphicon glyphicon-edit operate-edit" aria-hidden="true" value="${orderInfo['ID']}"></span></td>
											</c:otherwise>
										</c:choose>
											<td>${orderInfo['Area']}</td>
											<td title="${orderInfo['ContractTitle']}">${orderInfo['ContractTitle']}</td>
											<td title="${orderInfo['ContractNo']}">${orderInfo['ContractNo']}</td>
											<td class="dontConpress">${orderInfo['ContractCategory']}</td>							
											<td title="${orderInfo['Customer']}">${orderInfo['Customer']}</td>
											<td title="${orderInfo['Contact']}">${orderInfo['Contact']}</td>
											<td title="${orderInfo['ContactInfo']}">${orderInfo['ContactInfo']}</td>
											<td>${orderInfo['SalesRepresentative']}</td>
											<td>${orderInfo['DateOfSign']}</td>
											<td>${orderInfo['CargoPeriod']}</td>
											<td class="ActualDelivery_td">${orderInfo['ActualDelivery']}</td>
											<c:choose>
												<c:when test="${orderInfo['ExpectedDeliveryPeriod']=='--'}">
													<td class="ExpectedDeliveryPeriod_td" title="${orderInfo['ExpectedDeliveryPeriod']}">${orderInfo['ExpectedDeliveryPeriod']}</td>
												</c:when>
												<c:otherwise>
												<fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy-MM-dd" var="date2"/>
												<fmt:parseDate value="${orderInfo['ExpectedDeliveryPeriod']}" var="date1" pattern="yyyy-MM-dd"/> 
												 <c:set var="interval" value="${ now.time - date1.time }" /> 
													<c:choose>
														<c:when test="${ interval < -604800000 || (orderInfo['Status']!='已备货' && orderInfo['Status']!='已通知备货' && orderInfo['Status'] !='无')}">
															<td class="ExpectedDeliveryPeriod_td" title="${orderInfo['ExpectedDeliveryPeriod']}">${orderInfo['ExpectedDeliveryPeriod']}</td>
														</c:when>
														<c:otherwise>
															<td class="change ExpectedDeliveryPeriod_td" title="${orderInfo['ExpectedDeliveryPeriod']}"><span>${orderInfo['ExpectedDeliveryPeriod']}</span><br/>【需要尽快发货】</td>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
											<td>${orderInfo['ExpectedReceiptDate']}</td>
											<td class="dontConpress">${orderInfo['Status']}</td>
											<td title="${orderInfo['InstalledTime']}${orderInfo['InstalledSite']}">${orderInfo['InstalledTime']}${orderInfo['InstalledSite']}</td>
											<td title="${orderInfo['Remarks']}">${orderInfo['Remarks']}</td>
											<td><span class="glyphicon glyphicon-eye-open contract-show" aria-hidden="true" value="${orderInfo['ID']}"></span></td>
											<td><span class="glyphicon glyphicon-eye-open supply-show" aria-hidden="true" value="${orderInfo['ID']}"></span></td>
											<td><span class="glyphicon glyphicon-eye-open supply-detail-show" aria-hidden="true" value="${orderInfo['ID']}"></span></td>
											<td class="dontConpress"><span class="glyphicon glyphicon-eye-open supply-po-show" aria-hidden="true" value="${orderInfo['ID']}"></span></td>
											<td class="D3_D2" value="${orderInfo['D3_D2']}">${orderInfo['D3_D2']}</td>
											<td class="D3_D4" value="${orderInfo['D3_D4']}">${orderInfo['D3_D4']}</td>
											<td class="D4_D2" value="${orderInfo['D4_D2']}">${orderInfo['D4_D2']}</td>
											<td class="D3_D1" value="${orderInfo['D3_D1']}">${orderInfo['D3_D1']}</td>
										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<!-- 主页面表格结束 -->

						<div class="cover-color" style="display: none;"></div>
						<div class="operate" style="display: none;">
							<div class="operate_title">修改订单</div>
							<div class="operate_close">关闭</div>
							<div class="update_date">
								<input type="hidden" name="order_id">
								<input type="hidden" id="order_type">
								 实际货期<input type="date" name="actual_delivery" disabled="disabled">
								预计货期<input type="date" name="expected_delivery_period"
									disabled="disabled"> <input type="button"
									name="update_date" class="bToggle" value="修改"> <input
									type="button" name="submit_date" class="bToggle" value="保存"
									style="display: none">
			 					<input type="button" class="bToggle writeEmail" value="订单尺寸信息" style="margin-left:20px;">
							</div>
							<div class="update_date">
								<input type="hidden" name="order_id"> 合同状态
								<select name="status" disabled="disabled">
													<c:forEach var="ContractStatus" items="${ContractStatus}"
														varStatus="status">
														<c:if test="${status.index>0}">
															<option value="${ContractStatus.ID}" text="${ContractStatus.Status }">${ContractStatus.Status }</option>
														</c:if>
													</c:forEach>
								</select>
								<input type="button"
									name="update_state" class="bToggle" value="修改"> <input
									type="button" name="submit_state" class="bToggle" value="保存"
									style="display: none">

							</div>
							<div class="top_table">
								<table border="1" cellspacing="0" cellpadding="0"
									class="operatePage">
									<thead>
										<tr>
											<th>供货商</th>
											<th>PO-NO</th>
											<th>SO-NO</th>
											<th>PO金额（USD）</th>
											<th>PO金额（RMB）</th>
											<th>工厂货期</th>
											<th>型号</th>
											<th>描述</th>
											<th>实需数量</th>
											<th>实际采购数量</th>
											<th>库存数量</th>
											<th>实际货期</th>
											<th>预计货期</th>
											<th>货运单号</th>
											<th>订单状态</th>
											<th>预计付款时间</th>
											<th>实际付款时间</th>
											<th>审核人（1）</th>
											<th>审核人（2）</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>

									</tbody>
								</table>
							</div>
							<div class="bottom_table" style="display: none">
								<table border="1" cellspacing="0" cellpadding="0" class="editPage">
									<tbody>
										<tr style="display: none">
											<td>orderInfoID</td>
											<td><input type="text" name="id"></td>
											<td>订单ID</td>
											<td><input type="text" name="order_id"></td>
										</tr>
										<tr>
											<td>供应商</td>
											<td><select name="supplier">
													<c:forEach items="${suppliers }" var="supplier"
														varStatus="status">

														<c:if test="${status.index>0 }">
															<option text="${supplier['Name']}"
																value="${supplier['ID']}">${supplier['Name'] }</option>
														</c:if>

													</c:forEach>

											</select></td>
											<td>PO_NO</td>
											<td><input type="text" name="po_no"></td>
											<td>SO_NO</td>
											<td><input type="text" name="so_no"></td>

										</tr>
										<tr>
											<td>PO金额(USD)</td>
											<td><input type="text" name="po_amount"></td>
											<td>PO金额(RMB)</td>
											<td><input type="text" name="rmb_po_amount"></td>
											<td>实际货期</td>
											<td><input type="date" name="date"></td>
											
										</tr>
										<tr>
											<td>实际采购数量</td>
											<td><input type="text" name="logistics_number"></td>
											<td style="display:none;">库存数量</td>
											<td  style="display:none;"><input type="text" name="InventoryQuantity"></td>
											
											<td>工厂货期</td>
											<td><input type="date" name="factory_shipment"></td>
										</tr>
										<tr>
										    <td><span class='usd'>预计货期(USD)</span><span class='rmb' style="display:none">预计货期(RMB)</span></td>
											<td><input type="date" name="except_date" disabled="disabled"></td>
											<td>货运单号</td>
											<td><input type="text" name="delivery_number"></td>
											<td>订单状态</td>
											<td><select name="status">
													<c:forEach items="${order_status }" var="order_statu"
														varStatus="status">
														<c:if test="${status.index>0 }">
															<option text="${order_statu['Status'] }"
																value="${order_statu['ID'] }">${order_statu['Status'] }</option>
														</c:if>

													</c:forEach>
											</select></td>
										</tr>
										<tr>
										
											<td>预计付款时间</td>
											<td><input type="date" name="estimated_payment_time"></td>
											<td>实际付款时间</td>
											<td><input type="date" name="actual_payment_time"></td>
											
										</tr>
									</tbody>
								</table>
								<div class="edit_btn">
									<input type="button" value="提交" class="bToggle" id="edit_submit">
									<input type="button" value="取消" class="bToggle" id="edit_cancel">
								</div>
							</div>
							<div class="check_table" style="display: none">
								<table border="1" cellspacing="0" cellpadding="0" class="editPage">
									<tbody>
										<tr style="display: none">
											<td>orderInfoID</td>
											<td><input type="text" name="id"></td>
											<td>订单ID</td>
											<td><input type="text" name="order_id"></td>
										</tr>
										<tr>
											<td>审核一</td>
											<td><select name="review1">
													<option  value="0">未审核</option>
													<option  value="2">审核通过</option>
													<option  value="1">审核不通过</option>

											</select></td>
											<td>审核二</td>
											<td><select name="review2">
													<option  value="0">未审核</option>
													<option  value="2">审核通过</option>
													<option  value="1">审核不通过</option>

											</select></td>
										
										</tr>
						
									</tbody>
								</table>
								<div class="edit_btn">
									<input type="button" value="提交" class="bToggle" id="check_submit">
									<input type="button" value="取消" class="bToggle" id="check_cancel">
								</div>
							</div>
						
						</div>

						<div class="contract" style="display: none;">
							<div class="contract_title">合同配置信息</div>
							<div class="contract_close">关闭</div>
							<div class="out_table">
							<table border="1" cellspacing="0" cellpadding="0"
								class="contractPage">
								<thead>
									<tr>
										<th>订单号</th>
										<th>型号</th>
										<th>描述</th>
										<th>实需数量</th>
										<th>实际采购数量</th>
										<th>货期</th>
										<th>预计货期</th>
										<th>货运单号</th>
										<th>订单状态</th>
									</tr>
								</thead>
								<tbody>

								</tbody>
							</table>
							</div>
						
						</div>
						<div class="info" style="display: none;">
							<div class="info_title">供应商详细信息</div>
							<div class="info_close">关闭</div>
							<div class="out_table">
							<table border="1" cellspacing="0" cellpadding="0" class="infoPage">
								<thead>
									<tr>
										<th>供应商</th>
										<th>PO_NO</th>
										<th>SO_NO</th>
										<th>PO金额</th>
										<th>工厂货期</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
							</div>
						</div>
						<div class="supply-detail" style="display: none;">
							<div class="detail_title">合同详细信息</div>
							<div class="supply_detail_close">关闭</div>
							<table border="1" cellspacing="0" cellpadding="0" class="detailPage">
								<thead>
									<tr>
										<th>是否付款</th>
										<th>是否办理免税，信用证</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
						<div class="info_po" style="display: none;">
							<div class="info_title">PO信息统计</div>
							<div class="po_close">关闭</div>
							<div class="table_po">
							<table border="1" cellspacing="0" cellpadding="0" class="poPage">
								<thead>
									<tr>
										<th>PO</th>
										<th>RMB总额</th>
										<th>USD总额</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
							</div>
						</div>
						<c:choose>
							<c:when test="${queryType=='common' }">
								<c:set var="queryUrl" value="Transport?currentPage="></c:set>
							</c:when>
							<c:when test="${queryType=='singleSelect' }">
								<c:choose>
									<c:when test="${ classify1 =='合同货期' || classify1 == '实际货期' || classify1== '预计货期' }">
										<c:set var="queryUrl" value="GetOrderByPageOne?type1=${classify1}&searchContent1=${parameter1}&start_time1=${start_time1}&end_time1=${end_time1}&currentPage="></c:set>
									</c:when>
									<c:otherwise>
										<c:set var="queryUrl" value="GetOrderByPageOne?type1=${classify1}&searchContent1=${parameter1}&currentPage="></c:set>
									</c:otherwise>
								</c:choose>
							</c:when>

							<c:when test="${queryType=='mixSelect' }">
								<c:choose>
									<c:when test="${ classify1 =='合同货期' || classify1 == '实际货期' || classify1== '预计货期' || classify2 =='合同货期' || classify2 == '实际货期' || classify2== '预计货期'}">
										<c:set var="queryUrl" value="GetOrderByPageTwo?type1=${classify1}&searchContent1=${parameter1}&start_time1=${start_time1}&end_time1=${end_time1}&type2=${classify2}&searchContent2=${parameter2}&start_time2=${start_time2}&end_time2=${end_time2}&currentPage=">
										</c:set>
									</c:when>
									<c:otherwise>
										<c:set var="queryUrl" value="GetOrderByPageTwo?type1=${classify1}&searchContent1=${parameter1}&type2=${classify2}&searchContent2=${parameter2}&currentPage="></c:set>
									</c:otherwise>
								</c:choose>
							</c:when>
						</c:choose>
						<div class="gl_table_page_wrapper">
							<div class="gl_table_page">
								<div class="pageInfo">
									当前是第&nbsp;<span id="currentPage">${currentPage}</span>&nbsp;页,&nbsp;总计&nbsp;<span
										id="allPage">${pageCounts}</span>页
								</div>
								<div class="changePage">
									<input type="button" class="btn btn-primary" value="首页" id="fistPage" name="fistPage" onclick="FistPage('${queryUrl}')">
									<input type="button" class="btn btn-primary" value="上一页" id="upPage" onclick="UpPage('${queryUrl}${currentPage-1 }')">
									<input type="button" class="btn btn-primary" value="下一页" id="nextPage" onclick="NextPage('${queryUrl}${currentPage+1 }')"> 跳到第 <input type="text" id="jumpNumber" name="jumpNumber" class="jumpNumber" style="width: 30px; color: #000"
										onkeyup="value=value.replace(/[^\d]/g,'') "> 页 <input type="button" class="btn btn-primary" value="GO" id="Gotojump" name="Gotojump" onclick="PageJump('${queryUrl}')">
									<input type="button" class="btn btn-primary" value="尾页" id="lastPage" name="lastPage" onclick="LastPage('${queryUrl}')">
								</div>
							</div>
						</div><!-- gl_table_page_wrapper end -->
					</div>
				</div>

				<!-- ***************编辑邮件区域****************** -->
				<div class="MailBar_cover_color" style="display: none;"></div>
			 	<div class=" MailBar" style="display:none;">
					<div class="operate_title">发邮件</div>
					<div class="MailBar_close">关闭</div>
					<textarea class="MailBar_content" wrap="hard" cols="100" style="display:none;">
					</textarea>
						<input type="file" name="file" id="Mail_fileToUpload" value="" />
						<input type="button" name="button" value="发送" class="bToggle" id="Mail_Send">
				</div>
			<!-- Transport_sticker-con结束 -->
			</div>
	    <!-- Transport_sticker结束 -->
	    </div>

	    <!-- Transport_footer -->
	    <div id="Transport_footer">
	        <div id="eoulu-copy-out" style="height:40px;width:calc(100% - 2px);">
	            <div style="width:100%;height:5px;"></div>
	            <div id="eoulu-copy" style="width:100%;height:35px;font-size:12px;color:#888;line-height:35px;z-index: 2;">
	                <hr style="height:1px;color:#999;width: calc(100% - 3px);" />
	                <div style="width:100%;text-align:center;display:inline-block;">Copyright  ©&nbsp;<span class="YEAR">2018</span>&nbsp<a href="http://www.eoulu.com/h-col-268.html" class="EHref" target="_blank" style="color:blue;">Eoulu</a> Tech. Co.,Ltd.</div>
	            </div>
	        </div>
	    </div>
	<!-- Transport_wrapper结束 -->
	</div>
</body>
<script src="js/libs/bootstrap/bootstrap-grid-form-button-res-icon-list.min.js"></script>
<!-- delete -->
<!-- <script src="js/msgbox.js"></script> -->
<!-- <script src="js/global/responseLoading.js"></script> -->
<!-- <script src="js/transport.js"></script> -->
</html>