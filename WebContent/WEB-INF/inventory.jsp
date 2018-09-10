<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" http-equiv="content-type" content="text/html">
<title>库存页面</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="css/libs/bootstrap-buttons.min.css">
<link rel="stylesheet" type="text/css" href="css/global/eouluCustom.css">
<link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="plugins/awesomplete/awesomplete.css">
<link rel="stylesheet" type="text/css" href="plugins/awesomplete/awesomplete.theme.css">
<link rel="stylesheet" type="text/css" href="css/inventory.css?iv=201809101748">
<!-- <link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css"> -->
<style>
	.tbody_tr td {
	    padding: 0 3px;
	    overflow: hidden;
	    text-overflow: ellipsis;
	    white-space: nowrap;
	}

	#ware_house_sel {
		position: absolute;
		left:50%;
		top: 30px;
		margin-left: -50px;
	}

	#ware_house_sel option {
		padding-left: 5px;
		padding-top: 5px;
	}

	.ware_house_sel {
		position: absolute;
		left:50%;
		top: 30px;
		margin-left: -50px;
	}

	.ware_house_sel option {
		padding-left: 5px;
		padding-top: 5px;
	}

	/*布局*/
	html,body{
	    width:100%;
	    height:100%;
	}

	#originfactory_wrapper {
	    position:fixed;
	    overflow:auto;
	    width:100%;
	    height:100%;
	    box-sizing:border-box;
	}

	#originfactory_sticker {
	    width:100%;
	    min-height:100%;
	    box-sizing:border-box;
	}

	#originfactory_sticker-con {
	    padding-bottom:40px;
	    box-sizing:border-box;
	}

	#originfactory_footer {
	    margin-top:-40px;
	}

	.clear_float {
	    height: 2px;
	    width: calc(100% - 2px);
	    clear: both;
	}

	.content {
		padding-bottom: 5px !important;
	}

	.down_span {
		position: absolute;
		display: inline-block;
		width: 0;
		height: 0;
		border-left: 5px solid transparent;
	 	border-right: 5px solid transparent;
		border-top: 10px solid #00aeef;
		transform: translate(-15px,8px);
		-ms-transform: translate(-15px,8px);
		-webkit-transform: translate(-15px,8px);
	}

	/*发票备注*/
	.add_pncode_div {
	    position: absolute;
	    top: 100px;
	    left: calc(50% - 300px);
	    width: 600px;
	    background:#fff;
	    font-size: 16px;
	    z-index: 60;
	}

	.add_pncode_div_tit {
	    position: relative;
	    height: 30px;
	    background: #00aeef;
	    color: #fff;
	}

	.add_pncode_div_tit_l {
	    height: 30px;
	    line-height: 30px;
	    text-indent: 20px;
	    width: 540px;
	}

	.add_pncode_div_tit_r {
	    position: absolute;
	    top: 0;
	    right: 20px;
	    height: 30px;
	    line-height: 30px;
	    cursor: pointer;
	}

	.add_pncode_div_body {
	    margin: 20px;
	}

	#add_pncode_div_sel, #add_pncode_div_inp {
	    display: block;
	    width: 400px;
	    height: 45px;
	    margin: 10px auto;
	}

	.add_pncode_div_foot input {
	    display: block;
	    margin: 10px auto;
	}

	.cover_bg {
		width: 100vw;
		height: 100vh;
		background-color: rgba(10%,20%,30%,0.7);
		position: absolute;
		top: 0;
		left: 0;
		display: none;
		overflow: hidden;
		z-index: 63;
	}

	.publish_div {
		position: absolute;
		top: 200px;
		left: 50%;
		z-index: 65;
		height: 130px;
		width: 220px;
		margin-left: -110px;
		font-size: 16px;
		background: #fff;
	}

	.publish_div_tit {
		height: 30px;
		line-height: 30px;
		text-indent: 10px;
		background: #00aeef;
	}

	.publish_div_body {
		height: 50px;
		line-height: 50px;
		color: rgba(255,0,0,0.8);
		text-align: center;
	}

	.publish_div_foot {
		height: 45px;
		line-height: 45px;
		text-align: center;
	}

	.publish_div_foot_in {
		font-size: 15px;
		margin: 0 auto;
		width: 160px;
		height: 45px;
		line-height: 45px;
		text-align: center;
	}

	#publish_yes {
		margin-right: 10px;
	}

	/*扫码*/
	.scan_pncode_div {
	    position: absolute;
	    top: 100px;
	    left: calc(50% - 300px);
	    width: 600px;
	    background:#fff;
	    font-size: 16px;
	    z-index: 60;
	}

	.scan_pncode_div_tit {
	    position: relative;
	    height: 30px;
	    background: #00aeef;
	    color: #fff;
	}

	.scan_pncode_div_tit_l {
	    height: 30px;
	    line-height: 30px;
	    text-indent: 20px;
	    width: 540px;
	}

	.scan_pncode_div_tit_r {
	    position: absolute;
	    top: 0;
	    right: 20px;
	    height: 30px;
	    line-height: 30px;
	    cursor: pointer;
	}

	.scan_pncode_div_body {
	    margin: 20px;
	}

	#scan_pncode_div_sel, #scan_pncode_div_inp {
	    display: block;
	    width: 400px;
	    height: 45px;
	    margin: 10px auto;
	}

	.scan_pncode_div_foot input {
	    display: block;
	    margin: 10px auto;
	}

	#table1 .tabWarehouseAddress {
		cursor: pointer;
		color: #00aeef;
	}

	#table1 .tbody_tr {
		background-color: #f8f8f8;
	}

	.hightTolow {
		display: none;
	}

	/*导航栏各页面自定义*/
	.g-nav-ul li {
		height: 80px !important;
		line-height: 80px !important;
	}

	hr {
		margin-top: 1px;
		margin-bottom: 1px;
	}

	.u-admin img {
		vertical-align: top;
	}
</style>
</head>
<body>
	<div id="originfactory_wrapper">
		<div id="originfactory_sticker">
			<div id="originfactory_sticker-con">
				<!-- 头部开始 -->
				<%@include file="top.jsp"%>
				<!-- 头部结束 -->
				<div class="contain">
					<div class="content">
						<%@include file="nav.jsp"%>
						<div style="display: none">
							<input type="text" value="${classify}" name="classify1"> <input
								type="text" value="" name="classify2"> <input type="text" value="${content}" name="parameter1"> <input type="text" value="" name="parameter2">
						</div>

						<form id="top_text_from" name="top_text_from" method="post" action="Inventory">
							<input type="text" id="search" name="isSearch" value="search" style="display: none;">
							<div class="select-content" style="padding-top: 30px !important;">
								<label style="display: none"> <input type="radio" id="singleSelect" name="selected" class="singleSelect" value="singleSelect" checked="checked" onclick="Check(this.value)">单一查询
								</label>
								<!-- &nbsp;&nbsp;&nbsp; 
								<label>
								<input type="radio" id="mixSelect" name="selected" value="mixSelect" onclick="Check(this.value)">组合查询</label>&nbsp;&nbsp;&nbsp;<br>  -->
								<c:set var="dropdown"
									value="${fn:split('型号,供应商,入库时间,入库编码,出库编码,出库时间,合同号,列表价,列表总价,备注',',')}"></c:set>

								<div class="select1">

								<select name="classify" id="type1">
									<c:forEach items="${dropdown}" var="dropList" varStatus="status">
										<c:choose>
											<c:when test="${dropList==classify}">
												<option value='${dropList}' selected="selected">${dropList}</option>
											</c:when>
											<c:otherwise>
												<option value='${dropList}'>${dropList}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
								<c:choose>
									<c:when test="${condition=='入库时间' || condition=='出库时间' }">
										<input type="text" id="searchContent1" name="content" style="display: none;">
										<input type="date" name="start_time1" class="time" style="margin-left: -5px; display: inline-block;" value="${start_time1}">
										<span class="time" style="display: inline-block;">-</span>
										<input type="date" name="end_time1" class="time" style="display: inline-block;" value="${end_time1 }">
											<input type="date" name="inventory_time1" class="time2" style="display: none;" />
									</c:when>
									<%-- <c:when test="${condition=='库存' }">
										<input type="text" id="searchContent1" name="searchContent1"
											style="display: none;">
										<input type="date" name="start_time1" class="time"
											style="margin-left: -5px; display: none;">
										<span class="time" style="display: none;">-</span>
										<input type="date" name="end_time1" class="time"
											style="display: none;">
											<input type="date" value="${inventory_time1 }" name="inventory_time1" class="time2" style="display: inline-block;" />
									</c:when> --%>
									<c:otherwise>
										<input type="text" id="searchContent1" name="content" style="display: inline-block;" value="${content}">
										<input type="date" name="start_time1" class="time" style="margin-left: -5px;">
										<span class="time">-</span>
										<input type="date" name="end_time1" class="time">
										<input type="date" name="inventory_time1" class="time2" style="display: none;" />
									</c:otherwise>
								</c:choose>
								<%-- </c:if> --%>
								<!--            单一查询的初始值         结束                               -->
							</div>
							<!-- <div class="select2" style="display: none">
								<select name="type2" id="type2">
								<option value='型号'>型号</option>
										<option value="品牌">品牌</option>
										<option value="入库时间">入库时间</option>
										<option value="采购合同号">采购合同号</option>
										<option value="入库编码">入库编码</option>
										<option value="出库编码">出库编码</option>
										<option value="出库时间">出库时间</option>
										<option value="合同号">合同号</option>
								</select>
								<input type="text" id="searchContent2" name="searchContent2">
								<input type="date" name="start_time2" class="time2" style="margin-left: -5px;">
									<span class="time">-</span>
									<input type="date" name="end_time2" class="time2" >
								</div>  -->
							<div class="select-button">
								<input type="button" value="搜索" class="btn btn-info" onclick="Search()" style="margin-right:5px">
								<input type="button" value="取消" class="btn btn-info" onclick="Cancel()" style="margin-right:5px">
								<input type="button" value="导出库存" class="btn btn-info" onclick="exportinventory()" style="width:80px;margin-right:5px">
								<input type="button" value="入库申请" class="btn btn-info" onclick="" style="width:80px;margin-right:5px">
								<input type="button" value="出库申请" class="btn btn-info" onclick="" style="width:80px;">
							</div>
					</div>
					<div class="choose">
						<input type="button" value="添加" class="btn btn-info" onclick="AddContract()" style="margin-right:5px;margin-bottom:0px">
						<input type="button" value="新增条码号" class="btn btn-info add_pncode" style="width: 100px;margin-left: 10px;margin-right: 10px;display: none;">
						<input type="button" value="扫码" class="btn btn-info scan_pncode" style="margin-bottom:0px;">
						<div class="latest_develop">
							<div class="eou_new_well">最新盘点：<span value="${latestInventory}">${latestInventory}</span><input type="date" style="display: none;">
							</div>
						</div>
						<input type="button" value="修改" class="btn btn-primary btn-xs change_latest_develop" style="margin-bottom:0px;">
					</div>
					
					</form>
					<div class="wrapper_table">
						<table id="table1">
							<tr class="bgcccTr">
								<td class="tt1">序号</td>
								<td class="tt2">型号</td>
								<td class="tt3">描述</td>
								<td class="hasSort tt4" value="InventoryQuantity">库存数量<i class="fa fa-sort-amount-desc hightTolow" aria-hidden="true"></i></td>
								<td class="hasSort tt5" value="SellerPriceOne">列表价<i class="fa fa-sort-amount-desc hightTolow" aria-hidden="true"></i></td>
								<td class="hasSort tt6" value="ListPrice">列表总价<i class="fa fa-sort-amount-desc hightTolow" aria-hidden="true"></i></td>
								<td class="hasSort tt7" value="Supplier">供应商<i class="fa fa-sort-amount-desc hightTolow" aria-hidden="true"></i></td>
								<td class="tt8">入库信息</td>
								<td class="tt9">出库信息</td>
								<td class="tt10">预定信息</td>
								<td class="tt11">苏州</td>
								<td class="tt12">厦门</td>
								<td class="tt13">深圳</td>
								<td class="tt14">香港<i class="fa fa-plus-square"></i></td>
								<td class="tt16">成都</td>
								<td class="tt17">合肥</td>
								<td class="tt18">北京</td>
								<td class="tt19">石家庄</td>
								<td class="tt15">备注</td>
							</tr>
							<c:forEach items="${inventories}" var="inventory" varStatus="status">
								<c:if test="${status.index>0}">
									<tr class="tbody_tr">
										<td class="inventory-edit tt1" value="${inventory.ID}" data-pncode="${inventory.PNCode}">${(currentPage-1)*10+status.index}</td>
										<td class="td-Model tt2" value="${inventory.CommodityID}" title="${inventory.Model}">${inventory.Model}</td>
										<td class="td-Description tt3" title='${inventory.Description}'>${inventory.Description}</td>
										<td class="td-InventoryQuantity tt4">${inventory.InventoryQuantity}</td>
										<!--  列表价-->
										<td class="td-SellerPriceOne tt5">${inventory.SellerPriceOne}</td>
										<td class="td-ListPrice tt6">${inventory.ListPrice}</td>
										<td class="td-Supplier tt7" title="${inventory.Supplier}">${inventory.Supplier}</td>
										<%-- <td>${inventory.InitialQuantity}</td> --%>
										<td class="tt8"><i class="fa fa-eye inventory_in_show"
											value="${inventory.ID}"></i></td>
										<td class="tt9"><i class="fa fa-eye inventory_out_show"
											value="${inventory.ID}"></i></td>
											<!-- 预定+备注 -->
										<td class="tt10"><i class="fa fa-eye Reserve_info"
											value="${inventory.ID}"></i></td>
										<!-- <td class="tabWarehouseAddress">总仓库</td> -->
										<td class="tt11">${inventory.Suzhou}</td>
										<td class="tt12">${inventory.Xiamen}</td>
										<td class="tt13">${inventory.Shenzhen}</td>
										<td class="tt14">${inventory.Xianggang}</td>
										<td class="tt16">${inventory.Chengdu}</td>
										<td class="tt17">${inventory.Hefei}</td>
										<td class="tt18">${inventory.Beijing}</td>
										<td class="tt19">${inventory.Shijiazhuang}</td>
										<td class="td-Remarks reserve_num tt15" title="${inventory.Remarks}">${inventory.Remarks}</td>
									</tr>
								</c:if>
							</c:forEach>
						</table>
					</div>
					
					<div class="cover-color"></div>
					
					<div class="inventory_in">
						<input type="hidden" name="equipment_id">
						<input type="hidden" name="Model">
						<div class="inventory_title">入库信息</div>
						<div class="inventory_in_close">关闭</div>
						<div class="inventory_in_info">
							<table class="inventory_inPage">
								<thead>
									<tr>
										<th>入库时间</th>
										<th>入库数量</th>
										<th>采购合同号</th>
										<th>PO(USD)</th>
										<th>PO(RMB)</th>
										<th>入库编码</th>
										<th>仓库地址</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>

								</tbody>
							</table>
						</div>
						<div class="edit_btn" style="text-align: left;">
							<input type="button" value="添加" class="bToggle" name="inventory_in_add">
						</div>
						<div class="in_add_info" style="display: none">
							<div class="basic_info">
								<table class="inventory_basic">
									<tbody>
										<tr>
											<td>入库时间</td>
											<td><input type="date" name="operating_time"></td>
											<td>入库数量</td>
											<td><input type="text" name="quantity"></td>
										</tr>
										<tr>
											<td>采购合同号</td>
											<td><input type="text" name="contract_no"></td>
											<td>入库编码</td>
											<td><input type="text" name="operation_code"></td>
										</tr>
										<tr>
											<td>PO（USD）</td>
											<td><input type="text" name="POAmount" value="" class="POAmount"></td>
											<td>PO（RMB）</td>
											<td><input type="text" name="RMBPOAmount" value="" class="RMBPOAmount"></td>
										</tr>
										<tr>
											<td>仓库地址</td>
											<td style="position: relative;"><input type="text" class="ware_house_inp" value=""><span class="down_span"></span>
											<select class="ware_house_sel" multiple style="width:100px;height:120px;display:none">
												<option value="苏州">苏州</option>
												<option value="合肥">合肥</option>
												<option value="厦门">厦门</option>
												<option value="成都">成都</option>
												<option value="香港">香港</option>
												<option value="深圳">深圳</option>
												<option value="北京">北京</option>
												<option value="石家庄">石家庄</option>
											</select>
											</td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
									</tbody>
								</table>
							</div>
							<div class="edit_btn">
								<input type="button" value="提交" class="bToggle" name="add_submit">
								<input type="button" value="取消" class="bToggle" name="add_cancel">
							</div>
						</div>
					</div>
					
					<div class="exportinventory" style="display: none;">
						<div class="inventory_title">库存信息</div>
						<div class="inventory_in_close">关闭</div>
						
						<span class="chooseTime" style="position:absolute;top:36%;left:9%;">请选择时间：</span>
						<input type="date" class="inventory_startTime" style="position:absolute;top:52%;left:9%;">
						<input type="date" class="inventory_endTime" style="position:absolute;top:52%;left:54%;">
						<div class="edit_btn">
							<input type="button" value="导出" class="bToggle export_submit" name="export_submit" onclick="StartOrEndTime()">
							<input type="button" value="取消" class="bToggle export_cancel" name="export_cancel">
						</div>
					</div>
					
					<div class="inventory_out" style="display: none;">
						<input type="hidden" name="equipment_id">
						<input type="hidden" name="Model">
						<div class="inventory_title">出库信息</div>
						<div class="inventory_out_close">关闭</div>
						<div class="inventory_out_info">
							<table class="inventory_outPage">
								<thead>
									<tr>
										<th>出库时间</th>
										<th>出库数量</th>
										<th>销售合同号</th>
										<th>合同金额（USD）</th>
										<th>合同金额（RMB）</th>							
										<th>出库编码</th>
										<th>仓库地址</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>

								</tbody>
							</table>
							
						</div>
						<div class="edit_btn" style="text-align: left;">
							<input type="button" value="添加" class="bToggle"
								name="inventory_out_add">
						</div>
						<div class="out_add_info" style="display: none">
							<div class="basic_info">
								<table class="inventory_basic">
									<tbody>
										<tr>
											<td>出库时间</td>
											<td><input type="date" name="operating_time"></td>
											<td>出库数量</td>
											<td><input type="text" name="quantity"></td>
										</tr>
										<tr>
											<td>销售合同号</td>
											<td><input type="text" name="contract_no"></td>
											<td>出库编码</td>
											<td><input type="text" name="operation_code"></td>
										</tr>
										<tr>
											<td>合同金额（USD）</td>
											<td><input type="text" name="USDQuotes" value="" class="USDQuotes"></td>
											<td>合同金额（RMB）</td>
											<td><input type="text" name="RMBQuotes" value="" class="RMBQuotes"></td>
										</tr>
										<tr>
											<td>仓库地址</td>
											<td style="position: relative;"><input type="text" class="ware_house_inp" value=""><span class="down_span"></span>
											<select class="ware_house_sel" multiple style="width:100px;height:120px;display:none">
												<option value="苏州">苏州</option>
												<option value="合肥">合肥</option>
												<option value="厦门">厦门</option>
												<option value="成都">成都</option>
												<option value="香港">香港</option>
												<option value="深圳">深圳</option>
												<option value="北京">北京</option>
												<option value="石家庄">石家庄</option>
											</select>
											</td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
									</tbody>
								</table>
							</div>
							<div class="edit_btn">
								<input type="button" value="提交" class="bToggle" name="add_submit">
								<input type="button" value="取消" class="bToggle" name="add_cancel">
							</div>
						</div>
					</div>
					<!--预定信息  -->
					<div class="reserve_box" style="display: none;z-index:999">
						<div class="inventory_title" >预定信息</div>
						<div class="inventory_in_close">关闭</div>
							<div class="basic_info">
								<div class="table_title" style="margin-bottom:20px">客户预定<span class="fa fa-plus-circle reserve_add"></span></div>
								<table class="reserve_basic">
									<thead>
										<tr>
											<th class="yuding_th1">序号</th>
											<th class="yuding_th2">预定时间</th>
											<th class="yuding_th3">客户名称</th>
											<th class="yuding_th4">合同号</th>
											<th class="yuding_th5">预定数量</th>
											<th class="yuding_th6">仓库地址</th>
											<th class="yuding_th7">预计发货时间</th>
											<th class="yuding_th8">操作</th>
										</tr>
									</thead>
									<tbody class="table_content">
									</tbody>
								</table>
							</div>
							<div class="edit_btn">
								<input type="button" value="提交" class="bToggle bToggle_reserve add_submit" name="add_submit">
								<input type="button" value="取消" class="bToggle bToggle_reserve add_cancel"  name="add_cancel">
							</div>
						</div>
						<c:set var="queryUrl" value="Inventory?classify=${classify}&column=${column}&currentPage="></c:set>
					<%-- <c:choose>
						<c:when test="${queryType=='singleSelect' }">
							<c:set var="queryUrl"
										value="Inventory?classify=${classify}&content=${content}&currentPage="></c:set>
						</c:when>
						<c:otherwise>
							<c:set var="queryUrl" value="Inventory?currentPage="></c:set>
						</c:otherwise>
					</c:choose> --%>
					<div class="page_div">
						<div class="page_div_l">
							<div class="eou_new_well" value="${operationTime}">最新操作时间：${operationTime}</div>
							<!-- <input type="text" readonly="readonly" disabled="disabled" class="eou-bstr-input"> -->
						</div>
						<div class="page_div_r">
							<div class="pageInfo">
								当前是第&nbsp;<span id="currentPage">${currentPage}</span>&nbsp;页,&nbsp;总计&nbsp;<span id="allPage">${pageCounts}</span>页
							</div>
							<div class="changePage">
								<input type="button" class="btn btn-primary" value="首页" id="fistPage" name="fistPage" onclick="FistPage('${queryUrl}')">
								<input type="button" class="btn btn-primary" value="上一页" id="upPage" onclick="UpPage('${queryUrl}${currentPage-1}')">
								<input type="button" class="btn btn-primary" value="下一页" id="nextPage" onclick="NextPage('${queryUrl}${currentPage+1}')"> 跳到第 <input type="text" id="jumpNumber" name="jumpNumber" class="jumpNumber" style="width: 30px;height: 30px;color: #000;" onkeyup="value=value.replace(/[^\d]/g,'') "> 页 <input type="button" class="btn btn-primary" value="GO" id="Gotojump" name="Gotojump" onclick="PageJump('${queryUrl}')">
								<input type="button" class="btn btn-primary" value="尾页" id="lastPage" name="lastPage" onclick="LastPage('${queryUrl}')">
							</div>
						</div>
					</div>
					<!-- <div class="clear_float"></div> -->
					<!-- 添加库存 -->
					<div class="contract_add" style="display: none;">
						<div class="contract_title">添加库存</div>
						<div class="contractAdd_close">关闭</div>
						<div class="basic_info">
								<div class="table_title">基本信息</div>
								<table class="contract_basic">
									<tbody>
										<tr>
											<td>设备型号</td>
											<td  style="display:inline-block;margin-top: 15px;margin-left: 13%;">
												<input type="text" name="InitialQuantity" value="" class="InitialQuantity eou-bstr-input">
												<ul class="equipmentList">
												</ul>
											</td>
											<td>供应商</td>
											<td><input type="text" name="Name" value="" class="Name eou-bstr-input" disabled="disabled"></td>
											<td>描述</td>
											<td><input type="text" name="Remarks" value="" class="Remarks eou-bstr-input" disabled="disabled"></td>
										</tr>
										<tr style="display: none;">
											<td>仓库地址</td>
											<td style="position: relative;"><input type="text" class="ware_house_inp" value=""><span class="down_span"></span>
											<select id="ware_house_sel" multiple style="width:100px;height:120px;display:none">
											<option value="苏州">苏州</option>
											<option value="合肥">合肥</option>
											<option value="厦门">厦门</option>
											<option value="成都">成都</option>
											<option value="香港">香港</option>
											</select>
											</td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td>条码号</td>
											<td><input type="text" class="add_td_PNCode eou-bstr-input">
											</td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
									</tbody>
								</table>
								<div style="margin-top:20px;margin-left:1%">
									<span style="text-indent:10px;display:inline-block;margin-right:6%;vertical-align: top;" >备注</span>
									<textarea class="remark eou-bstr-input" cols="108" rows="6" ></textarea>
								</div>
								<!-- <table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
									<tbody>
										<tr>
											<td class="tdTitle" style="text-align:left">入库信息</td>
										</tr>
										<tr>
											<td>入库时间</td>
											<td><input type="date" name="OperatingTime" value=""  class="OperatingTime"></td>
											<td>入库数量</td>
											<td><input type="text" name="Quantity" value=""  class="Quantity"></td>
											<td>采购合同号</td>
											<td><input type="text" name="ContractNo1" value=""  class="ContractNo1"></td>
										</tr>
										<tr>	
											<td>入库编码</td>
											<td><input type="text" name="OperationCode" value=""  class="OperationCode"></td>
											<td>PO（USD）</td>
											<td><input type="text" name="POAmount" value="" class="POAmount"></td>
											<td>PO（RMB）</td>
											<td><input type="text" name="RMBPOAmount" value="" class="RMBPOAmount"></td>
										</tr>
										<tr>
											<td class="tdTitle" style="text-align:left">出库信息</td>
										</tr>
										<tr>
											<td>出库时间</td>
											<td><input type="date" name="OperatingTime2" value=""  class="OperatingTime2"></td>
											<td>出库数量</td>
											<td><input type="text" name="Quantity2" value=""  class="Quantity2"></td>
											<td>销售合同号</td>
											<td><input type="text" name="ContractNo2" value=""  class="ContractNo2"></td>
										</tr>
										<tr>
											<td>出库编码</td>
											<td><input type="text" name="OperationCode2" value=""  class="OperationCode2"></td>
											<td>合同金额（USD）</td>
											<td><input type="text" name="USDQuotes" value="" class="USDQuotes"></td>
											<td>合同金额（RMB）</td>
											<td><input type="text" name="RMBQuotes" value="" class="RMBQuotes"></td>
										</tr>
									</tbody>
								</table> -->
						</div>
						<div class="edit_btn">
							<input type="button" value="提交" class="bToggle" id="add_submit">
							<input type="button" value="取消" class="bToggle" id="add_cancel">
						</div>
					</div>

				</div>
				</div>
				<div class="add_pncode_div" style="display: none;">
					<div class="add_pncode_div_tit">
						<div class="add_pncode_div_tit_l">新增条码号</div>
						<div class="add_pncode_div_tit_r">关闭</div>
					</div>
					<div class="add_pncode_div_body">
					条码号：
						<input type="text" id="add_pncode_div_sel" class="eou-bstr-input">
					型号：
						<div style="position: relative;">
							<input type="text" id="add_pncode_div_inp" class="eou-bstr-input">
							<select id="add_pncode_select" multiple style="width: 400px;position: absolute;height: 100px;display: none;left: 50%;top:50px;margin-left: -200px;"></select>
						</div>
					</div>
					<div class="add_pncode_div_foot" >
						<input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" value="确定">
					</div>
				</div>
				<div class="cover_bg"></div>
				<!-- 覆盖框 -->
				<div class="publish_div" style="display: none;">
				    <div class="publish_div_tit">覆盖提示信息</div>
				    <div class="publish_div_body">条形码已存在，是否覆盖？</div>
				    <div class="publish_div_foot">
				        <div class="publish_div_foot_in">
				        	<input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" id="publish_yes" value="是"><input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" id="publish_no" value="否">
				        </div>
				    </div>
				</div>
				<!-- 扫码 -->
				<div class="scan_pncode_div" style="display: none;">
					<div class="scan_pncode_div_tit">
						<div class="scan_pncode_div_tit_l">扫码</div>
						<div class="scan_pncode_div_tit_r">关闭</div>
					</div>
					<div class="scan_pncode_div_body">
					条码号：
						<input type="text" id="scan_pncode_div_sel" class="eou-bstr-input">
					</div>
					<div class="scan_pncode_div_foot" >
						<input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" value="确定">
					</div>
				</div>
				<!-- 修改 -->
				<div class="update_div" style="display: none;">
					<div class="update_div_top">
						<div class="update_div_top_l">修改库存</div>
						<div class="update_div_top_r">关闭</div>
					</div>
					<div class="update_div_body">
						<div class="line_0">
						    <div class="line_01">PNCode</div>
						    <div class="line_02">
						        <input type="text" class="info_PNCode eou-bstr-input">
						    </div>
						</div>
						<div class="line_0" style="height: 130px;">
						    <div class="line_01">备注</div>
						    <div class="line_02">
						    	<textarea cols="30" rows="6" class="info_remakes eou-bstr-input"></textarea>
						    </div>
						</div>
					</div>
					<div class="update_div_foot">
						<div class="add_NonStandard_foot_in">
						    <input type="button" class="eou_new_btn eou_new_radius eou-button-30 eou-button-w60" id="NonStandard_addsubmit" value="提交">
						    <input type="button" class="eou_new_btn eou_new_radius eou-button-30 eou-button-w60" id="NonStandard_addclose" value="取消">
						</div>
					</div>
				</div>
			<!-- originfactory_sticker-con结束 -->
			</div>
		<!-- originfactory_sticker结束 -->
		</div>
		<!-- originfactory_footer -->
		<div id="originfactory_footer">
			<div id="eoulu-copy-out" style="height:40px;width:calc(100% - 2px);">
				<div style="width:100%;height:5px;"></div>
				<div id="eoulu-copy" style="width:100%;height:35px;font-size:12px;color:#888;line-height:35px;z-index: 2;">
					<hr style="height:1px;color:#999;width: calc(100% - 3px);" />
					<div style="width:100%;text-align:center;display:inline-block;">Copyright  ©&nbsp;<span class="YEAR">2018</span>&nbsp<a href="http://www.eoulu.com/h-col-268.html" class="EHref" target="_blank" style="color:blue;">Eoulu</a> Tech. Co.,Ltd.</div>
				</div>
			</div>
		</div>
	<!-- originfactor_wrapper结束	 -->
	</div>
</body>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<!-- <script src="js/swiper-3.4.1.jquery.min.js" type="text/javascript" charset="utf-8"></script> -->
<!-- <script src="js/ajaxfileupload.js" type="text/javascript" charset="utf-8"></script> -->
<script src="plugins/awesomplete/awesomplete.min.js"></script>
<script src="js/msgbox.js"></script>
<script src="js/inventory.js?iv=201809101748"></script>
<script type="text/javascript">
	var classify_ = $("input[name='classify1']").val();
	var content_ = $("input[name='parameter1']").val();
	function AddContract() {
		$(".contract_add").show();
		$('.cover-color').show();
	}
	$('.contractAdd_close,#add_cancel').click(function () {
		$(".contract_add td input").val("");
 	    $(".contract_add textarea").val("");
	    $('.cover-color').hide();
	    $('.contract_add').hide();
	});
	
	// --------------------------------------------提交添加合同配置信息-----------------------------------------------
	$(document).on("click", "#add_submit", function () {
		var CommodityID = $(".contract_add .InitialQuantity").attr("ID");
		var Remarks = $(".contract_add .remark").val();
		// var WarehouseAddress = $(".ware_house_inp").val().trim();
		if(!CommodityID){
			 $.MsgBox_Unload.Alert("提示", '设备型号填写错误！');
			 return;
		}
		var PNCode = $(".add_td_PNCode").val().trim();
		$.ajax({
			type:'get',
			url:'InventoryAdd',
			dataType:'text',
			data:{
				Type: "add",
				CommodityID: CommodityID,
				Remarks: Remarks,
				PNCode: PNCode
				/*WarehouseAddress:WarehouseAddress*/
			},
			success:function(data){
				$.MsgBox.Alert("提示", data);
				// if(data){
				// 	 $.MsgBox.Alert("提示", '添加成功！');
					 
				// }else{
				// 	$.MsgBox.Alert("提示", '添加失败！');
					
				// }
				$(".contract_add td input").val("");
	        	$(".contract_add  textarea").val("");
	        	$('.cover-color').hide();
	       	    $('.contract_add').hide();	
			},
			error:function(){
				$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
			}
		})
	});

	$(document).on("click",".inventory-edit",function(){
		$('.cover-color').show();
	    $('.update_div').show();
	    var id = $(this).attr("value");
	    var pncode = $(this).data("pncode").toString();
	    var remarks = $(this).siblings(".td-Remarks").text();
	    var xinghao = $(this).siblings(".td-Model").text();
	    $(".update_div").attr("value",id);
	    $(".info_PNCode").val(pncode);
	    $(".info_remakes").val(remarks);
	    $(".update_div_top_l").text("修改库存："+xinghao);
	});

	$("#NonStandard_addsubmit").on("click",function(){
		var ID = $(".update_div").attr("value");
		var Remarks = $(".info_remakes").val();
		var PNCode = $(".info_PNCode").val();
		$.ajax({
			type:'get',
			url:'InventoryAdd',
			dataType:'text',
			data:{
				Type: "update",
				// CommodityID: CommodityID,
				Remarks: Remarks,
				PNCode: PNCode,
				ID: ID
				/*WarehouseAddress:WarehouseAddress*/
			},
			success:function(data){
				$.MsgBox.Alert("提示", data);
	        	$('.cover-color').hide();
			    $('.update_div').hide();	
			},
			error:function(){
				$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
			}
		});
	});

	$(".update_div_top_r, #NonStandard_addclose").on("click",function(){
		$('.cover-color').hide();
		$('.update_div').hide();
		$(".update_div").attr("value","");
	    $(".info_PNCode").val("");
	    $(".info_remakes").val("");
	});

	function FistPage(arg) {
		if(classify_ == "入库时间" || classify_ == "出库时间"){
			var start_time1 = $("input[name='start_time1']").val();
			var end_time1 = $("input[name='end_time1']").val();
			window.location.href = arg + "1"+"&start_time1="+start_time1+"&end_time1="+end_time1;
		}else{
			var content = $("input#searchContent1").val().trim();
			window.location.href = arg + "1"+"&content="+content;
		}
	}
	function UpPage(arg) {
		if(classify_ == "入库时间" || classify_ == "出库时间"){
			var start_time1 = $("input[name='start_time1']").val();
			var end_time1 = $("input[name='end_time1']").val();
			window.location.href = arg+"&start_time1="+start_time1+"&end_time1="+end_time1;
		}else{
			var content = $("input#searchContent1").val().trim();
			window.location.href = arg+"&content="+content;
		}
	}
	function NextPage(arg) {
		if(classify_ == "入库时间" || classify_ == "出库时间"){
			var start_time1 = $("input[name='start_time1']").val();
			var end_time1 = $("input[name='end_time1']").val();
			window.location.href = arg+"&start_time1="+start_time1+"&end_time1="+end_time1;
		}else{
			var content = $("input#searchContent1").val().trim();
			window.location.href = arg+"&content="+content;
		}
	}
	function PageJump(arg) {
		var jumpNumber = document.getElementById("jumpNumber").value;
		if (jumpNumber == null || jumpNumber == 0) {
			jumpNumber = $('#currentPage').html();
		} else if (jumpNumber > parseInt($('#allPage').html())) {
			jumpNumber = $('#allPage').html();
		}
		if(classify_ == "入库时间" || classify_ == "出库时间"){
			var start_time1 = $("input[name='start_time1']").val();
			var end_time1 = $("input[name='end_time1']").val();
			window.location.href = arg +jumpNumber+"&start_time1="+start_time1+"&end_time1="+end_time1;
		}else{
			var content = $("input#searchContent1").val().trim();
			window.location.href = arg +jumpNumber+"&content="+content;
		}
	}
	function LastPage(arg) {
		var jumpNumber = parseInt($('#allPage').html());
		if(classify_ == "入库时间" || classify_ == "出库时间"){
			var start_time1 = $("input[name='start_time1']").val();
			var end_time1 = $("input[name='end_time1']").val();
			window.location.href = arg +jumpNumber+"&start_time1="+start_time1+"&end_time1="+end_time1;
		}else{
			var content = $("input#searchContent1").val().trim();
			window.location.href = arg +jumpNumber+"&content="+content;
		}
		// window.location.href = arg + jumpNumber;
	}

	function IsAddPnCode(){
	    if($("#inventory_addpncode").length>0){
	        $(".add_pncode").show();
	    }else{
	        $(".add_pncode").hide();
	    }
	}

	$(function() {
		// if ($('#currentPage').html() == 1) {
		// 	$('#fistPage').attr('disabled', 'true');
		// 	$('#fistPage').removeClass('bToggle');
		// 	$('#upPage').attr('disabled', 'true');
		// 	$('#upPage').removeClass('bToggle');
		// }
		// if ($('#allPage').html() == $('#currentPage').html()) {
		// 	$('#lastPage').attr('disabled', 'true');
		// 	$('#lastPage').removeClass('bToggle');
		// 	$('#nextPage').attr('disabled', 'true');
		// 	$('#nextPage').removeClass('bToggle');
		// }
		pageStyle(Number($('#currentPage').text()),Number($('#allPage').text()));
		$(".hasSort").css({"cursor":"pointer"});
		if(window.location.href.indexOf("column")>-1){
			var sortVal = window.location.href.split("column=")[1];
			var sortValue;
			if(sortVal.indexOf("&")>-1){
				sortValue = sortVal.split("&")[0];
			}else{
				sortValue = sortVal;
			}
			console.log(sortValue);
			if(sortValue=="")sortValue="InventoryQuantity";
			$(".hasSort i").hide();
			$(".hasSort[value='"+sortValue+"'] i").show();
		}else{
			$(".hasSort[value='InventoryQuantity'] i").show();
		}
		// IsAddPnCode();
		var classify = $("input[name='classify1']").val();
		var content = $("input[name='parameter1']").val();
		if(classify == "入库时间" || classify == "出库时间"){
			$("#searchContent1").hide().val("");
			$(".time").show();
			$("input.time[name='start_time1']").val(content.split(";")[0]);
			$("input.time[name='end_time1']").val(content.split(";")[1])
		}
		if($(".latest_develop span").text()==""){
			$(".latest_develop span").text("暂无")
		}
	});
	$(document).on("click",".hasSort",function(){
		// $(this).children("i").show();
		var column = $(this).attr("value");
		var classify = $("input[name='classify1']").val();
		var content = $("input[name='parameter1']").val();
		var baseHref = window.location.href.split("cfChicken8")[0]+"cfChicken8/Inventory";
		window.location.href = baseHref+"?classify="+classify+"&content="+content+"&column="+column+"&currentPage=1";
	});
	$(document).on("click",".hightTolow",function(e){
		e.stopPropagation();
		$(this).parent().trigger("click");
	});
</script>
</html>

