<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>苏州伊欧陆系统集成有限公司</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="css/price_product.css">
<link rel="stylesheet" type="text/css"
	href="font-awesome-4.5.0/css/font-awesome.min.css">
</head>
<body>
	<%@include file="top.jsp"%>
	<div class="contain">
		<div class="content">

		<%@include file="nav.jsp"%>
			
			<form id="top_text_from" name="top_text_from" method="post" action="">
				<input type="text" id="search" name="isSearch" value="search"
					style="display: none;">
				<div class="choose">
					<input type="button" value="添加" class="bToggle"
						onclick="AddContract()">
				</div>
			</form>
			<table border="1" cellspacing="0" cellpadding="0" id="table1">
				<tr>
					<td>序号</td>
					<td>操作</td>
					<td>版本号</td>
					<td>类型</td>
					<td>报价单号</td>
					<td>报价日期</td>					
					<td>客户名称	</td>
					<td>联系人
					 <a title="显示与隐藏"><i class="fa fa-plus-square" id="fa-button1"></i></a>
					 </td>
					<td>联系方式</td>
					<td>联系人邮箱</td>
					<td>业务员
					<a title="显示与隐藏"><i class="fa fa-plus-square" id="fa-button2"></i></a>
					</td>
					<td>业务员电话</td>
					<td>业务员邮箱</td>
					<td>交货时间</td>
					<td>有效期</td>
					<td>支付方式</td>
					<td>汇率</td>
					<td>课税类别</td>					
					<td>配置信息</td>
					<td>生成报价单</td>
				</tr>
				<c:forEach items="${quotations}" var="quotation" varStatus="status">
					<c:if test="${status.index>0}">
						<tr>
							<td>${(currentPage-1)*10+status.index}</td>
							<td><i class="fa fa-edit edit" value="${quotation['ID']}" text="${quotation['OrderID']}"></i></td>
							<td>${quotation['Version']}</td>
							<td>${quotation['ContractType']}</td>
							<td>${quotation['QuotationID']}</td>
							<td>${quotation['QuotationDate']}</td>
							<td>${quotation['Customer']}</td>
							<td>${quotation['Contact']}</td>
							<td>${quotation['ContactInfo']}</td>
							<td>${quotation['ContactEmail']}</td>
							<td>${quotation['Name']}</td>
							<td>${quotation['SaleContact']}</td>
							<td>${quotation['Email']}</td>
							<td>${quotation['LeadTime']}</td>
							<td>${quotation['Valid']}</td>
							<td>${quotation['Condition']}
								<input type="hidden" value="${quotation['paymentID']}">
							</td>
							<td>${quotation['ExchangeRate']}</td>
							<td>${quotation['ChargeDuty']}</td>
							<td><i class="fa fa-eye contract-show" value="${quotation.ID}" ></i><input type="hidden" value=""></td>
							<td><i class="fa fa-edit report-edit" value="${quotation.ID}" ></i><input type="hidden" value=""></td>
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
							<tr>
								<td>报价版本号</td>
								<td>
								<input type="text" name="Version" value="1.0">
								</td>
								<td>类型</td>
								<td>
								<select name="QuotationType">
										<option value="报价单——美金（配件）">报价单——美金（配件）</option>
										<option value="报价单——美金（整机）">报价单——美金（整机）</option>
										<option value="报价单——人民币（配件）">报价单——人民币（配件）</option>
										<option value="报价单——人民币（整机）">报价单——人民币（整机）</option>
								</select>
								</td>
								<td>客户名称</td>
								<td>
									<div class="out_search">
										<input type="search" name="customer_search" class="customer_search">
										 <select name="Customer" multiple>
										 	<option value="" text="0" selected="selected"></option>
											<c:forEach var="customer" items="${customers}"
												varStatus="status">
												<c:if test="${status.index>0}">
													<option value="${customer.CustomerName } : ${customer.Contact }" CustomerName="${customer.CustomerName}" text='${customer.ID}' customer_id="${customer.ID}" contact="${customer.Contact}" fax_number="${customer.Email}" contact_info="${customer.ContactInfo1}">${customer.CustomerName }&nbsp;&nbsp;:${customer.Contact}</option>
												</c:if>
											</c:forEach>
											
										</select>
									</div>
								</td>
							</tr>
							<tr>
								<td>联系人</td>
								<td><input type="text" name="Contact" value="" disabled="disabled"></td>
								<td>联系方式</td>
								<td><input type="text" name="ContactInfo" value="" disabled="disabled"></td>
								<td>联系人邮箱</td>
								<td><input type="text" name="fax_number" disabled="disabled"></td>
							</tr>
							<tr>
								<td>业务员</td>
								<td><select name="SalesRepresentative">
										<option value=""></option>
										<c:forEach items="${sales}" var="sale" varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${sale.ID}" text="${sale.Name}" Contact="${sale.Contact}" email="${sale.Email}" EmployeeNumber="${sale.EmployeeNumber}">${sale.Name}</option>
											</c:if>
										</c:forEach>
								</select></td>
								<td>业务员电话</td>
								<td>
								<input type="text" name="SalePhone" value="" disabled="disabled"></td>								
								<td>业务员邮箱</td>
								<td><input type="text" name="SaleEmail" disabled="disabled">
								</td>
							</tr>
							<tr>
								<td>有效期</td>
								<td>
								<select name="Valid">
										<option value="In 30 days">In 30 days</option>
										<option value="In 10 days">In 10 days</option>
								</select>
								</td>
								<td>交货时间</td>
								<td>
								<input type="date" name="LeadTime" value="">
								</td>
									<td>Warranty</td>
								<td><textarea name="Warranty" disabled="disabled" value="12 months No warrant on consumable parts">12 months No warrant on consumable parts</textarea>
							</tr>
							<tr>
								<td>Payment</td>
								<td>
								<select name="paymentTerm">
										<c:forEach items="${paymentTerms}" var="paymentTerm">
											<option value="${paymentTerm.ID}">${paymentTerm.Condition}</option>
										</c:forEach>
								</select>
								</td>
								<td>Seller</td>
								<td><textarea name="Seller" disabled="disabled" value="HK EOULU TRADING LIMTED">HK EOULU TRADING LIMTED</textarea></td>
								<td>汇率</td>
								<td>
								<input type="text" name="ExchangeRate" value="6.9">
								</td>
							</tr>
							<tr>
								<td>课税类别</td>
								<td><input type="text" name="ChargeDuty" value="1.17"></td>
								<td>日期</td>
								<td><input type="date" name="QuotationDate"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="edit_btn">
					<form >
						<input type="button" value="提交" class="bToggle" id="add_submit">
						<input type="button" value="取消" class="bToggle" id="add_cancel">
					</form>
				</div>
			</div>
			<div class="info_update">
				<div class="title">修改信息</div>
				<div class="update_close">关闭</div>
				<input type="hidden" name="id">
				<input type="hidden" name="order_id">
				<input type="hidden" name="QuotationID">
				<div class="basic_info">
				<table border="1" cellspacing="0" cellpadding="0"
						class="contract_basic">
						<tbody>
							<tr>
								<td>报价版本号</td>
								<td>
								<input type="text" name="Version">
								</td>
								<td>类型</td>
								<td>
								<select name="QuotationType">
										<option value="报价单——美金（配件）">报价单——美金（配件）</option>
										<option value="报价单——美金（整机）">报价单——美金（整机）</option>
										<option value="报价单——人民币（配件）">报价单——人民币（配件）</option>
										<option value="报价单——人民币（整机）">报价单——人民币（整机）</option>
								</select>
								</td>
								<td>客户名称</td>
								<td>
									<div class="out_search">
										<input type="search" name="customer_search" class="customer_search">
										 <select name="Customer" multiple>
										 	<option value="" text="0" selected="selected"></option>
											<c:forEach var="customer" items="${customers}"
												varStatus="status">
												<c:if test="${status.index>0}">
													<option value="${customer.CustomerName } : ${customer.Contact }" text='${customer.ID}' CustomerName="${customer.CustomerName}" customer_id="${customer.ID}" contact="${customer.Contact}" fax_number="" contact_info="${customer.ContactInfo1}">${customer.CustomerName }&nbsp;:&nbsp;${customer.Contact }</option>
												</c:if>
											</c:forEach>
											
										</select>
									</div>
								</td>
							</tr>
							<tr>
								<td>联系人</td>
								<td><input type="text" name="Contact" value="" disabled="disabled"></td>
								<td>联系方式</td>
								<td><input type="text" name="ContactInfo" value="" disabled="disabled"></td>
								<td>联系人邮箱</td>
								<td><input type="text" name="fax_number" disabled="disabled"></td>
							</tr>
							<tr>
								<td>业务员</td>
								<td><select name="SalesRepresentative">
										<option value=""></option>
										<c:forEach items="${sales}" var="sale" varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${sale.ID}" text="${sale.Name}" Contact="${sale.Contact}" email="${sale.Email}" EmployeeNumber="${sale.EmployeeNumber}">${sale.Name}</option>
											</c:if>
										</c:forEach>
								</select></td>
								<td>业务员电话</td>
								<td>
								<input type="text" name="SalePhone" value="" disabled="disabled"></td>								
								<td>业务员邮箱</td>
								<td><input type="text" name="SaleEmail" disabled="disabled">
								</td>
							</tr>
							<tr>
								<td>有效期</td>
								<td>
								<select name="Valid">
										<option value="In 30 days">In 30 days</option>
										<option value="In 10 days">In 10 days</option>
								</select>
								</td>
								<td>交货时间</td>
								<td>
								<input type="date" name="LeadTime" value="">
								</td>
									<td>Warranty</td>
								<td><textarea name="Warranty" disabled="disabled" value="12 months No warrant on consumable parts">12 months No warrant on consumable parts</textarea>
							</tr>
							<tr>
								<td>Payment</td>
								<td>
								<select name="paymentTerm">
										<c:forEach items="${paymentTerms}" var="paymentTerm">
											<option value="${paymentTerm.ID}">${paymentTerm.Condition}</option>
										</c:forEach>
								</select>
								</td>
								<td>Seller</td>
								<td><textarea name="Seller" disabled="disabled" value="HK EOULU TRADING LIMTED">HK EOULU TRADING LIMTED</textarea></td>
								<td>汇率</td>
								<td>
								<input type="text" name="ExchangeRate" value="6.9">
								</td>
							</tr>
							<tr>
								<td>课税类别</td>
								<td><input type="text" name="ChargeDuty" value="1.17"></td>
								<td>日期</td>
								<td><input type="date" name="QuotationDate"></td>
							</tr>
						</tbody>
					</table>
					
				</div>
				<div class="edit_btn">
					<input type="button" value="提交" class="bToggle" id="update_submit">
					<input type="button" value="取消" class="bToggle" id="update_cancel">
				</div>
			</div>
			<div class="contract">
				<div class="contract_title">合同配置信息</div>
				<div class="contract_close">关闭</div>
				<input type="hidden" id="id" >
				<input type="hidden" id="type" >
				<input type="hidden" id="paymentTerms" >
				<div class="contarct_info">
				<table border="1" cellspacing="0" cellpadding="0" class="contractPage">
					<thead>
						<tr>
							<th>序号</th>
							<th>型号规格</th>							
							<th>商品名称</th>
							<th>数量</th>
							<th>单价</th>
							<th>金额</th>
							<th>修改</th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
				<table border="1" cellspacing="0" cellpadding="0" class="contractPage">
					<tbody>
						<tr>
							<td>Total USD</td>
							<td>0</td>
						</tr>
						<tr>
							<td>Shipment & Insurance USD</td>
							<td>0</td>
						</tr>
						<tr>
							<td>Final Total CIP China Main Airport USD</td>
							<td>0</td>
						</tr>
					</tbody>
				</table>
				</div>
				<div class="edit_btn">
					 <input type="button" value="添加" class="bToggle" id="contract_apply_add">
				</div>
				<div class="apply_add_info" style="display: none">
					<table border="1" cellspacing="0" cellpadding="0"
						class="contract_basic">
						<tbody>
							<tr>
								<td>型号规格</td>
								<td>
									<div class="out_search">
										<input type="search" name="equipment_search"
											class="equipment_search"> <select name="equipment_name"
											multiple>
											<c:forEach var="equipment" items="${equipments}"
												varStatus="status">
												<option value="0" text="0" selected="selected"></option>
												<c:if test="${status.index>0}">
													<option
														value="${equipment.Model}" text='${equipment.ID}'  remarks="${equipment.Remarks}" model="${equipment.Model}">${equipment.Model }
													</option> 
												</c:if> 
											</c:forEach>
										
										</select>
									</div>
								</td>
								<td>商品名称</td>
								<td><input type="text" name="remarks" value=""
									disabled="disabled"></td>
									<td>数量</td>
								<td><input type="text" name="counts" value="1"></td>
							</tr>
							<tr>
								<td>单价</td>
								<td><input type="text" name="unit_price" value="0"></td>
								<td class="RMB">合同金额（RMB）</td>
								<td class="RMB"><input type="text" name="RMBQuotes" value="0"></td>
								<td class="USD">合同金额（USD）</td>
								<td class="USD"><input type="text" name="USDQuotes" value="0"></td>
							</tr>
						
						</tbody>
					</table>
					<div class="edit_btn">
						<input type="button" value="提交" class="bToggle" id="apply_add_submit">
						<input type="button" value="取消" class="bToggle" id="apply_add_cancel">
					</div>
				</div>
			</div>
			<div class="report">
				<div class="report_title">生成报价单</div>
				<div class="report_close">关闭</div>
				<div class="report_info">
					<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
						<tbody>
							<tr>
								<td>报价版本号</td>
								<td>
								<input type="text" name="Version" disabled="disabled">
								</td>
								<td>类型</td>
								<td>
								<select name="QuotationType">
										<option value="报价单——美金（配件）">报价单——美金（配件）</option>
										<option value="报价单——美金（整机）">报价单——美金（整机）</option>
										<option value="报价单——人民币（配件）">报价单——人民币（配件）</option>
										<option value="报价单——人民币（整机）">报价单——人民币（整机）</option>
								</select>
								</td>
								<td>客户名称</td>
								<td>
									<div class="out_search">
										<input type="search" name="customer_search" class="customer_search" disabled="disabled">
										 <select name="Customer" multiple>
										 	<option value="" text="0" selected="selected"></option>
											<c:forEach var="customer" items="${customers}"
												varStatus="status">
												<c:if test="${status.index>0}">
													<option value="${customer.CustomerName } : ${customer.Contact }" text='${customer.ID}' CustomerName="${customer.CustomerName}" contact="${customer.Contact}" fax_number="" contact_info="${customer.ContactInfo1}">${customer.CustomerName }&nbsp;:&nbsp;${customer.Contact }</option>
												</c:if>
											</c:forEach>
											
										</select>
									</div>
								</td>
							</tr>
							<tr>
								<td>联系人</td>
								<td><input type="text" name="Contact" value="" disabled="disabled"></td>
								<td>联系方式</td>
								<td><input type="text" name="ContactInfo" value="" disabled="disabled"></td>
								<td>联系人邮箱</td>
								<td><input type="text" name="fax_number" disabled="disabled"></td>
							</tr>
							<tr>
								<td>业务员</td>
								<td><select name="SalesRepresentative" disabled="disabled">
										<option value="" ></option>
										<c:forEach items="${sales}" var="sale" varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${sale.ID}" text="${sale.Name}" Contact="${sale.Contact}" email="${sale.Email}" EmployeeNumber="${sale.EmployeeNumber}">${sale.Name}</option>
											</c:if>
										</c:forEach>
								</select></td>
								<td>业务员电话</td>
								<td>
								<input type="text" name="SalePhone" value="" disabled="disabled"></td>								
								<td>业务员邮箱</td>
								<td><input type="text" name="SaleEmail" disabled="disabled">
								</td>
							</tr>
							<tr>
								<td>有效期</td>
								<td>
								<select name="Valid">
										<option value="In 30 days">In 30 days</option>
										<option value="In 10 days">In 10 days</option>
								</select>
								</td>
								<td>交货时间</td>
								<td>
								<input type="date" name="LeadTime" value="" disabled="disabled">
								</td>
									<td>Warranty</td>
								<td><textarea name="Warranty" disabled="disabled" value="12 months No warrant on consumable parts">12 months No warrant on consumable parts</textarea>
							</tr>
							<tr>
								<td>Payment</td>
								<td>
								<select name="paymentTerm">
										<c:forEach items="${paymentTerms}" var="paymentTerm">
											<option value="${paymentTerm.ID}">${paymentTerm.Condition}</option>
										</c:forEach>
								</select>
								</td>
								<td>Seller</td>
								<td><textarea name="Seller" disabled="disabled" value="HK EOULU TRADING LIMTED">HK EOULU TRADING LIMTED</textarea></td>
								<td>汇率</td>
								<td>
								<input type="text" name="ExchangeRate" value="6.9" disabled="disabled">
								</td>
							</tr>
							<tr>
								<td>课税类别</td>
								<td><input type="text" name="ChargeDuty" value="1.17" disabled="disabled"></td>
								<td>日期</td>
								<td><input type="date" name="QuotationDate" disabled="disabled"></td>
							</tr>
						</tbody>
					</table>
					<div class="apply_title">配置信息</div>
					<table border="1" cellspacing="0" cellpadding="0" class="contractPage applyCount" style="width:96%;">
					<thead>
						<tr>
							<th>序号</th>
							<th>商品名称</th>
							<th>型号规格</th>
							<th>数量</th>
							<th>单价</th>
							<th>金额</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>1</td>
							<td>1</td>
							<td>3</td>
							<td>6</td>
							<td></td>
						</tr>
						<tr>
							<td>1</td>
							<td>1</td>
							<td>1</td>
							<td>1</td>
							<td>1</td>
							<td></td>
						</tr><tr>
							<td>1</td>
							<td>1</td>
							<td>1</td>
							<td>1</td>
							<td>1</td>
							<td></td>
						</tr><tr>
							<td>1</td>
							<td>1</td>
							<td>1</td>
							<td>1</td>
							<td>1</td>
							<td></td>
						</tr>
					</tbody>
				</table>
				<table border="1" cellspacing="0" cellpadding="0" class="contractPage totalCount" style="width:96%;">
					<tbody>
						<tr>
							<td>Total USD</td>
							<td>0</td>
						</tr>
						<tr>
							<td>Shipment & Insurance USD</td>
							<td>0</td>
						</tr>
						<tr>
							<td>Final Total CIP China Main Airport USD</td>
							<td>0</td>
						</tr>
					</tbody>
				</table>
				</div>
				<div class="edit_btn">
					<input type="hidden" name="id">
					<input type="hidden" name="order_id">
					 <input type="button" value="导出" class="bToggle" id="export">
				</div>
			</div>
			
			<c:set var="queryUrl"
				value="Quotation?currentPage="></c:set>
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
<script src="js/msgbox.js"></script>
<script src="js/price_product.js"></script>
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