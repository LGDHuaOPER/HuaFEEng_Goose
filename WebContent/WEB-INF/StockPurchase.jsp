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
<title>库存采购</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" />
<link rel="stylesheet" href="css/global/eouluCustom.css" type="text/css">
<link rel="stylesheet" type="text/css" href="css/StockPurchase.css">
<style>
	.content .choose {
		margin-bottom: 15px !important;
	}

	.content .choose .purchase_export {
	    background: #00aeef;
	    border: solid 1px #00aeef;
	    color: #fff;
	    cursor: pointer;
	    box-shadow: 1px 2px 5px 0 #00aeef;
	    padding: 0;
	    text-align: center;
	    vertical-align: middle;
	    width: 50px;
	    height: 32px;
	}

	#table1 .format_date {
	    color: #fff;
	}

	#addSuppier, #updateSuppier {
		position: absolute;
		top: 30px;
		left: 23px;
	}

	.preview_download {
		display: inline-block;
		position: relative;
		left: 70px;
	}

	.preview_download input {
		height: 25px;
	}

	.preview_download input[value='预览'] {
		margin-right: 20px;
	}

</style>
</head>
<body>
	<!-- 	头部开始 -->
	<%@include file="top.jsp"%>
	<div class="cover-color3"></div>
	<!-- 	头部结束 -->
	<div class="contain">
		<div class="content">
		<!-- 	=======================导航栏   开始 ================================== -->

		<%@include file="nav.jsp"%>
			
<!-- 	=======================导航栏   结束 ================================== -->
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
				<input type="text" value="${queryType }" name="query_type">
			</div>

			<form id="top_text_from" name="top_text_from" method="post"
				action="PriceRoute">
				<input type="text" id="search" name="isSearch" value="search"
					style="display: none;">
				<input type="text"  name="PageType" value="1" style="display: none;">	
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
							value="${fn:split('采购人员,合同号,供应商名称,合同名称,联系人,联系方式,合同签订日期,合同货期,型号',',')}"></c:set>
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
							<input type="button" value="搜索" class="bToggle"
								onclick="selectSearch()"> <input type="button" value="取消"
								class="bToggle" onclick="selectCancel()">
						</div>
				</div>
				<div class="choose">
					<div class="choose_in">
						<input type="button" value="添加" class="bToggle" style="margin: 50px 0 0 0;display: inline-block;" onclick="AddContract()">
						<input type="button" class="purchase_export" style="margin: -4px 0 0 20px;display: inline-block;" value="导出">
					</div>
				</div>

			</form>
			<div class="wrap-table" style="height:330px;">
			<table border="1" cellspacing="0" cellspadding="0" id="table1">
				<tr>
					<td>序号</td>
					<%--<td>修改</td>--%>
					<!--<td>删除</td>-->
					<td>供应商名称 <a title="显示与隐藏"><i class="fa fa-plus-square" id="fa-button1"></i></a></td>
					<td>联系人</td>
					<td>联系方式</td>
					<%--<td>合同地区</td>--%>
					<td id="contractName">合同名称 <a title="显示与隐藏"><i class="fa fa-plus-square" id="fa-button2"></i></a>
					</td>
					<td id="contractNum">合同号</td>
					<%--<td>合同类型</td>--%>
					<td>采购人员</td>
					<td>合同签订日期</td>
					<td style="cursor:pointer" title="升序排列">合同货期
						<select style="width: 80px;" id="condition_sel">
							<option value="All" class="All">所有订单</option>
							<option value="AllNoSend" class="AllNoSend">未发货订单</option>
							<option value="OtherNoSend" class="OtherNoSend">不包括等待客户付款的未发货订单</option>
						</select>
					</td>
					<%--<td style="cursor:pointer;" title="显示未发货的合同"> 实际货期--%>
						<%--<select style="width: 80px;">--%>
							<%--<option value="All" class="All">所有订单</option>--%>
							<%--<option value="AllNoSend" class="AllNoSend">未发货订单</option>--%>
							<%--<option value="OtherNoSend" class="OtherNoSend">不包括等待客户付款的未发货订单</option>--%>
						<%--</select>--%>
					<%--</td>--%>
					<%--<td style="cursor:pointer;" title="升序排列">预计货期--%>
						<%--<select style="width: 80px;">--%>
							<%--<option value="All" class="All">所有订单</option>--%>
							<%--<option value="AllNoSend" class="AllNosend">未发货订单</option>--%>
							<%--<option value="OtherNoSend" class="OtherNosend">不包括等待客户付款的未发货订单</option>--%>
						<%--</select>--%>
					<%--</td>--%>
					<td>预计付款时间</td>
					<%--<td>合同状态 <a title="显示与隐藏"><i class="fa fa-plus-square"--%>
							<%--id="fa-button3"></i></a>--%>
					<%--</td>--%>
					<%--<td>装机时间和地点</td>--%>
					<%--<td>备注</td>--%>
					<td>合同配置</td>
					<%--<td>合同明细</td>--%>
					<%--<td>是否可以发货</td>--%>
					<%--<td>是否付款</td>--%>
					<td>采购合同</td>
					<td style="display: none;">合同地区</td>
					<td style="display: none;">合同类型</td>
					<td style="display: none;">合同类型</td>
					<td style="display: none;">合同类型</td>
					<td style="display: none;">装机时间</td>
					<td style="display: none;">装机地点</td>
					<td style="display: none;">备注</td>
					<td class="actual_pay_time_th">实际付款时间</td>
				</tr>

				<c:forEach var="orderInfo" items="${orders}" varStatus="status">
					<c:if test="${status.index>0}">
						<tr>
							<%--<td value="${orderInfo['ID']}">${status.index+(currentPage-1)*10}</td><!-- 0 -->--%>
							<c:choose>
								<c:when test="${orderInfo['Status']=='已完结'}">
									<td title="该订单已完结">${status.index+(currentPage-1)*10}</td>     <!-- 1 -->
								</c:when>
								<c:otherwise>
									<td class="contract-edit" value="${orderInfo['ID']}">${status.index+(currentPage-1)*10}</td>  <!-- 2 -->
								</c:otherwise>
							</c:choose>
							<td title="${orderInfo['Customer']}">${orderInfo['Customer']}</td>  <!-- 3 -->
							<td class="ContactTD">${orderInfo['Contact']}</td>   <!-- 4 -->
							<td class="ContactInfoTD" title="${orderInfo['ContactInfo']}">${orderInfo['ContactInfo']}</td>  <!-- 5 -->
							<%--<td>${orderInfo['Area']}</td>      <!-- 6 -->--%>
							<td class="ContractTitleTD" title="${orderInfo['ContractTitle']}">${orderInfo['ContractTitle']}</td>   <!-- 7 -->
							<td class="ContractNoTD" title="${orderInfo['ContractNo']}">${orderInfo['ContractNo']}</td>    <!-- 8-->
							<%--<td>${orderInfo['ContractCategory']}</td>  <!-- 9 -->--%>
							<td>${orderInfo['SalesRepresentative']}</td>   <!-- 10 -->
							<td class="format_date">${orderInfo['DateOfSign']}</td>   <!-- 11 -->
							<td class="format_date">${orderInfo['CargoPeriod']}</td>   <!-- 12 -->
							<%--<td>${orderInfo['ActualDelivery']}</td>   <!-- 13 -->--%>
							<%--<c:choose>--%>
								<%--<c:when test="${orderInfo['ExpectedDeliveryPeriod'] == '--'}">--%>
									<%--<td>${orderInfo['ExpectedDeliveryPeriod']}</td>   <!-- 14 -->--%>
								<%--</c:when>--%>
								<%--<c:otherwise>--%>
								<%--<fmt:formatDate value="${now}" type="both" dateStyle="long" var="date2" pattern="yyyy-MM-dd"/>--%>
								<%--<fmt:parseDate value="${orderInfo['ExpectedDeliveryPeriod']}" var="date1" pattern="yyyy-MM-dd"/>--%>
								<%--<c:set var="interval" value="${now.time - date1.time }"/>--%>
									<%--<c:choose>--%>
										<%--<c:when test="${interval < -604800000 || (orderInfo['Status']!='已备货' && orderInfo['Status']!='已通知备货' && orderInfo['Status'] !='无' && orderInfo['Status'] !='--')}">--%>
											<%--<td>${orderInfo['ExpectedDeliveryPeriod']}</td>   <!-- 15 -->--%>
										<%--</c:when>--%>
										<%--<c:otherwise>--%>
											<%--<td class="change">${orderInfo['ExpectedDeliveryPeriod']}<br/>【需要尽快发货】</td>  <!-- 16 -->--%>
										<%--</c:otherwise>--%>
									<%--</c:choose>--%>
								<%--</c:otherwise>--%>
							<%--</c:choose>--%>
							<td class="format_date">${orderInfo['ExpectedReceiptDate']}</td>   <!-- 17 -->
							<%--<td>${orderInfo['Status']}</td>                 <!-- 18 -->--%>
							<%--<td>${orderInfo['InstalledTime']}${orderInfo['InstalledSite']}</td>   <!-- 19 -->--%>
							<%--<td>${orderInfo['Remarks']}</td>             <!-- 20 -->--%>
							<td><i class="fa fa-eye contract-show"
								value="${orderInfo['ID']}"></i></td>     <!-- 21 -->
							<%--<td><i class="fa fa-eye supply-show"--%>
								<%--value="${orderInfo['ID']}"></i></td>     <!-- 22 -->--%>
						    <%--<td>${orderInfo['isSend']}</td>              <!-- 23 -->--%>
						   	<%--<td>${orderInfo['WhetherToPay']}</td>        <!-- 24 -->--%>

							<c:choose>
								<c:when test="${orderInfo['PurchaseMail']=='已发送'}">
									<td title="邮件已发送" class="isTransport">已发送</td>     <!-- 1 -->
								</c:when>
								<c:otherwise>
									<td title="邮件未发送" class="isTransport noTransport" style="color:red;">未发送</td>  <!-- 2 -->
								</c:otherwise>
							</c:choose>
							<td class="area_td" style="display: none;">${orderInfo['Area']}</td>
							<td class="contractcategory_td" style="display: none;">${orderInfo['ContractCategory']}</td>
							<td class="actualdelivery_td" style="display: none;">${orderInfo['ActualDelivery']}</td>
							<td class="status_td" style="display: none;">${orderInfo['Status']}</td>
							<td class="installedtime_td" style="display: none;">${orderInfo['InstalledTime']}</td>
							<td class="installedsite_td" style="display: none;">${orderInfo['InstalledSite']}</td>
							<td class="remarks_td" style="display: none;">${orderInfo['Remarks']}</td>
						    <%--<td class="isTransport noTransport" value="1">未发送</td>--%>
						    <td class="actual_pay_time format_date">${orderInfo['ActualPaymentTime']}</td>
						</tr>
					</c:if>
				</c:forEach>
			</table>
			</div>
			<div class="cover-color" style="display: none;"></div>
			<div class="cover-color2" style="display: none;"></div>
			<div class="upload" style="display: none;">
				<div class="contract_title">上传文件</div>
				<div class="upload_close">关闭</div>
				<div class="file_content">
					<a class="file"> <input type="file" name="file" id="fileField"
						size="28" onchange="openFile()" /> <span>&nbsp;选择文件&nbsp;</span>
					</a> <input id="file_name" type="text"><br>
					<span class='error'></span>
				</div>
				<input type="button" name="upload" id="open" class="bToggle" value="上传" />
				<div class='list'>
					<ul>
					
					</ul>
				</div>
			</div>
			<%--采购合同弹出--%>
			<div class="contract-purchase" style="display: none;">
				<input type="hidden" name="saveContractId" val="">
				<div class="top">
					<span>采购合同</span>
					<span><i>关闭</i></span>
				</div>
				<div class="u-body">
					<div class="i-top">
						<div class="title">采购合同</div>
	<%--<input type="file" name="pdf" accept="application/pdf">--%>
						<div class="upFileText"><span>采购合同</span>
							<input type="hidden" name="isPdfUpload" value="0">
							<input type="text"   class="input_text" id="textfield"  >
							<input type="button" class="input_sub"  value="浏览..." >
							<input type="button" class="input_sub" id="uploadCon" value=" 上 传 " >
							<!-- <input type="file"   class="input_file" contenteditable="false"  onchange="document.getElementById('textfield').value=this.value"> -->
							<input type="file" name="fileName" id="input_file" class="input_file" contenteditable="false"  onchange="openFile1()" accept="application/pdf">
							<span class='error1'></span>
							<span class="preview_download">
								<input type="button" class="eou-button eou-button-radius eou-button-w50" value="预览" title="预览并下载">
								<input type="button" class="eou-button eou-button-radius eou-button-w50" value="下载">
							</span>
						</div>
					</div>
					<div class="i-body">
						<div class="title">供货商银行信息</div>
						<div class="table1">
							<table>
								<thead>
									<tr><th>供应商</th>
										<th>产品</th>
										<th>金额</th>
										<th>用途</th>
										<th>银行信息</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td rowspan="3" class="h88 supplier"></td>
										<td rowspan="3" class="h88 hasUn" contenteditable="true"></td>
										<td rowspan="3" class="h88 hasUn" contenteditable="true"></td>
										<td rowspan="3" class="h88 hasUn" contenteditable="true"></td>
										<td>公司：<span class="hasUn" contenteditable="true"></span></td>
									</tr>
									<tr>

										<td>账号：<span class="hasUn" contenteditable="true"></span></td>
									</tr>
									<tr>

										<td>开户行：<span class="hasUn" contenteditable="true"></span></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="i-foot"><input type="button" class="shicong1" value="发送邮件"></div>
				</div>
				<div class="u-foot">
					<input type="button" id="contract-purchase-upload" value="提交">
					<input type="button" value="取消">
				</div>
			</div>
			<%--发送邮件弹窗--%>
			<div class="email-send" style="display: none;">
				<div class="u-top">
					<span>邮件模板</span>
					<span><i>关闭</i></span>
				</div>
				<div class="u-body">
					<div class="u-left">
						<div>收件人</div>
						<div>抄送人</div>
						<div>抄送人</div>
						<div>邮件主题</div>

					</div>
					<div class="u-right">
						<div class="i-row i-row1"><input type="email" name="" value="wangxiaoliang@eoulu.com"></div>
						<div class="i-row i-row2"><input type="email" name="CopyTo1" value="zhaona@eoulu.com"><div style="margin-left:216px;"><span style="font-size:16px;">抄送人</span><input type="email" name="CopyTo2" value="jiangyaping@eoulu.com"></div></div>
						<div class="i-row i-row3"><input type="email" name="CopyTo3" value="zhudanni@eoulu.com"></div>
						<div class="i-row i-row4"><input type="text" name="" style="width:694px"></div>

					</div>
					<div style="min-height:228px">
					<div class="email-tit">邮件正文</div>
					<div class="email-text">
						<p contenteditable="true">晓亮姐，您好！</p>
						<p contenteditable="true">附件：<span></span>，是采购合同</p>
						<p contenteditable="true">烦请您根据以下银行信息安排付款，谢谢晓亮姐！</p>
						<p contenteditable="true">如有任何问题，请随时告知，非常感谢！</p>
						<table>
							<tbody>
								<tr>
									<td>供应商</td>
									<td>产品</td>
									<td>金额</td>
									<td>用途</td>
									<td>银行信息</td>
								</tr>
								<tr>
									<td rowspan="3" class="h88"></td>
									<td rowspan="3" class="h88"></td>
									<td rowspan="3" class="h88"></td>
									<td rowspan="3" class="h88"></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
								</tr>
								<tr>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
					</div>
				</div>
				<div class="u-foot">
					<input type="button" id="email-send-upload" value="发送">
					<input type="button" value="取消">
				</div>
			</div>
			<!-- 添加合同 -->
			<div class="contract_add" style="display: none;">
				<div class="contract_title">添加合同</div>
				<div class="contractAdd_close">关闭</div>
				<div class="basic_info">
					<div class="table_title">合同基本信息</div>
					<table border="1" cellspacing="0" cellpadding="0"
						class="contract_basic">
						<tbody>
							<tr>
								<input type="hidden" name="order_id">
								<td><i class="eou-relevance"></i>供应商名称</td>
								<td style="position: relative;"><input type="text" name="customer" value="">
								<select id="addSuppier" multiple style="width:40vw;display:none;height: 120px;"></select>
								</td>
								<td>合同地区</td>
								<td><select name="area">
										<c:forEach var="area" items="${Areas}" varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${area.ID}">${area.AreaName}</option>
											</c:if>
										</c:forEach>
								</select></td>
								<td>合同号</td>
								<td><input type="text" name="contract_no" value=""></td>
							</tr>
							<tr>
								<td>合同名称</td>
								<td><input type="text" name="contract_title" value=""></td>
								<td>采购人员</td>
								<td><select name="sales_representative">
										<c:forEach var="SaleRep" items="${SalesRep}"
											varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${SaleRep.ID}">${SaleRep.Name }</option>
											</c:if>
										</c:forEach>
								</select></td>
								<td>合同类型</td>
								<td><select name="contract_category">
										<c:forEach var="contractCategory" items="${contract_category}"
											varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${contractCategory.ID}"
													text="${contractCategory.Classify }">${contractCategory.Classify }</option>
											</c:if>
										</c:forEach>
								</select></td>

							</tr>
							<tr>
								<td><i class="eou-relevance"></i>联系人</td>
								<td><input type="text" name="contact" value=""></td>
								<td><i class="eou-relevance"></i>联系方式</td>
								<td><input type="text" name="contact_info" value=""></td>
								<%-- <td>合同状态</td>
								<td><select name="status">
										<c:forEach var="ContractStatus" items="${ContractStatus}"
											varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${ContractStatus.ID}">${ContractStatus.Status }</option>
											</c:if>
										</c:forEach>
								</select></td> --%>
								<td>合同货期</td>
								<td><input type="date" name="cargo_period" value=""></td>

							</tr>
							<tr>
								<td>装机地点</td>
								<td><input type="text" name="installed_site" value=""></td>
								<td>合同签订日期</td>
								<td><input type="date" name="date_of_sign" value=""></td>
								<td>预计付款时间</td>
								<td><input type="date" name="expected_receipt_date" value=""></td>
							</tr>
							<tr>
								<td>装机时间</td>
								<td><input type="date" name="installed_time" value=""></td>
								<td>备注</td>
								<td><textarea name="remarks" cols="23" rows="3"
										value="备注信息"></textarea></td>
							</tr>
						</tbody>
					</table>
					<div class="table_title">合同明细</div>
					<table border="1" cellspacing="0" cellpadding="0"
						class="contract_basic">
						<tbody>
							<tr>
								<td>合同金额（USD）</td>
								<td><input type="text" name="usd_quotes" value="0"></td>
								<td>合同金额（RMB）</td>
								<td><input type="text" name="rmb_quotes" value="0"></td>
								<td>付款条件</td>
								<td>
									<div class="out_search">
										<input type="search" name="pay_search" class="pay_search">
										<select name="payment_terms" multiple>
											<c:forEach var="PayTerm" items="${PayTerms}"
												varStatus="status">
												<c:if test="${status.index>0}">
													<option value="${PayTerm.ID}">${PayTerm.Condition }</option>
												</c:if>
											</c:forEach>
										</select>
									</div>

								</td>
							</tr>
							<tr>
								<td>收款日期1</td>
								<td><input type="date" name="receipt_date1" value=""></td>
								
								<td>收款日期2</td>
								<td><input type="date" name="receipt_date2" value="">
								</td>
								<td>收款日期3</td>
								<td><input type="date" name="receipt_date3" value=""></td>
							</tr><tr>
							 <td>收款金额1</td>
								<td><input type="text" name="receipt_amount1" value="0"></td>
								<td>收款金额2</td>
								<td><input type="text" name="receipt_amount2" value="0"></td>
							
								<td>收款金额3</td>
								<td><input type="text" name="receipt_amount3" value="0"></td>
							</tr>
							<tr>
								<td>是否付款</td>
								<td>
									<div class="out_search">
										<input type="search" name="search" class="search"> <select
											name="whether_to_pay" multiple>
											<c:forEach var="whetherToPay" items="${whether_to_pay}"
												varStatus="status">
												<c:if test="${status.index>0}">
													<option value="${whetherToPay.Status}">${whetherToPay.Status }</option>
												</c:if>
											</c:forEach>
										</select>
									</div> 
									<textarea name="whether_to_pay_remarks" cols="23" rows="2"
										style="margin-top: 5px;" placeholder="备注信息"></textarea>

								</td>
								<td>是否开具发票</td>
								<td>
								<div class="out_search">
								<select name="whether_to_invoice" >
										<option value="1">是</option>
										<option value="0">否</option>
								</select> 
								</div>
								<textarea name="whether_to_invoice_remarks" cols="23" rows="2"
										style="margin-top: 5px;" placeholder="备注信息"></textarea></td>
										<td>开票日期</td>
								<td><input type="date" name="billing_date" value=""></td>
							</tr>
							<tr>
								<td>是否办理免税，信用证</td>
								<td>
								<div class="out_search">
								<select name="duty_free" >
										<c:forEach var="Duty" items="${DutyFree}" varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${Duty.ID}">${Duty.Status }</option>
											</c:if>
										</c:forEach>
								</select>
								</div>
								 <textarea name="duty_free_remarks" cols="23" rows="2"
										style="margin-top: 5px;" placeholder="备注信息"></textarea></td>
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
				<div class="contract_title">修改合同信息</div>
				<div class="contractUpdate_close">关闭</div>
				<div class="basic_info">
					<div class="table_title">合同基本信息</div>
					<table border="1" cellspacing="0" cellpadding="0"
						class="contract_basic">
						<tbody>
							<tr>

								<input type="hidden" name="order_id">

								<td><i class="eou-relevance"></i>供应商名称</td>
								<td style="position: relative;"><input type="text" name="customer" value="">
								<select id="updateSuppier" multiple style="width:40vw;display:none;height: 120px;"></select>
								</td>
								<td>合同地区</td>
								<td><select name="area">
										<c:forEach var="area" items="${Areas}" varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${area.ID}" text="${area.AreaName}">${area.AreaName}</option>
											</c:if>
										</c:forEach>
								</select></td>
								<td>合同号</td>
								<td><input type="text" name="contract_no" value=""></td>
							</tr>
							<tr>
								<td>合同名称</td>
								<td><input type="text" name="contract_title" value=""></td>
								<td>采购人员</td>
								<td><select name="sales_representative">
										<c:forEach var="SaleRep" items="${SalesRep}"
											varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${SaleRep.ID}" text="${SaleRep.Name}">${SaleRep.Name }</option>
											</c:if>
										</c:forEach>
								</select></td>
								<td>合同类型</td>
								<td><select name="contract_category">
										<c:forEach var="contractCategory" items="${contract_category}"
											varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${contractCategory.ID}"
													text="${contractCategory.Classify}">${contractCategory.Classify}</option>
											</c:if>
										</c:forEach>
								</select></td>

							</tr>
							<tr>
								<td><i class="eou-relevance"></i>联系人</td>
								<td><input type="text" name="contact" value=""></td>
								<td><i class="eou-relevance"></i>联系方式</td>
								<td><input type="text" name="contact_info" value=""></td>
								<%-- <td>合同状态</td>
								<td><select name="status">
										<c:forEach var="ContractStatus" items="${ContractStatus}"
											varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${ContractStatus.ID}">${ContractStatus.Status }</option>
											</c:if>
										</c:forEach>
								</select></td> --%>
								<td>合同货期</td>
								<td><input type="date" name="cargo_period" value=""></td>

							</tr>
							<tr>
								<td>装机地点</td>
								<td><input type="text" name="installed_site" value=""></td>
								<td>合同签订日期</td>
								<td><input type="date" name="date_of_sign" value=""></td>
								<td>预计付款时间</td>
								<td><input type="date" name="expected_receipt_date" value=""></td>
							</tr>
							<tr>
								<td>装机时间</td>
								<td><input type="date" name="installed_time" value=""></td>
								<td>备注</td>
								<td><textarea name="remarks" cols="23" rows="3"
										value="备注信息"></textarea></td>
								<td>实际付款时间</td>
								<td><input type="date" name="actual_pay_date"></td>
							</tr>
						</tbody>
					</table>
					<div class="table_title">合同明细</div>
					<table border="1" cellspacing="0" cellpadding="0"
						class="contract_basic">
						<tbody>
							<tr>
								<td>合同金额（USD）</td>
								<td><input type="text" name="usd_quotes" value="0"></td>
								<td>合同金额（RMB）</td>
								<td><input type="text" name="rmb_quotes" value="0"></td>
								<td>付款条件</td>
								<td>
									<div class="out_search">
										<input type="search" name="pay_search" class="pay_search">
										<select name="payment_terms" multiple>
											<c:forEach var="PayTerm" items="${PayTerms}"
												varStatus="status">
												<c:if test="${status.index>0}">
													<option value="${PayTerm.ID}">${PayTerm.Condition }</option>
												</c:if>
											</c:forEach>
										</select>
									</div>

								</td>
							</tr>
							<tr>
								<td>收款日期1</td>
								<td><input type="date" name="receipt_date1" value=""></td>
								
								<td>收款日期2</td>
								<td><input type="date" name="receipt_date2" value="">
								</td>
								<td>收款日期3</td>
								<td><input type="date" name="receipt_date3" value=""></td>
							</tr><tr>
							 <td>收款金额1</td>
								<td><input type="text" name="receipt_amount1" value="0"></td>
								<td>收款金额2</td>
								<td><input type="text" name="receipt_amount2" value="0"></td>
							
								<td>收款金额3</td>
								<td><input type="text" name="receipt_amount3" value="0"></td>
							</tr>
							<tr>
								<td>是否付款</td>
								<td>
									<div class="out_search">
										<input type="search" name="search" class="search" > <select
											name="whether_to_pay" multiple>
											<c:forEach var="whetherToPay" items="${whether_to_pay}"
												varStatus="status">
												<c:if test="${status.index>0}">
													<option value="${whetherToPay.Status}">${whetherToPay.Status }</option>
												</c:if>
											</c:forEach>
										</select>
									</div> 
									<textarea name="whether_to_pay_remarks" cols="23" rows="2"
										style="margin-top: 5px;" placeholder="备注信息"></textarea>

								</td>
								<td>是否开具发票</td>
								<td>
								<div class="out_search">
								<select name="whether_to_invoice" >
										<option value="1">是</option>
										<option value="0">否</option>
								</select> 
								</div>
								<textarea name="whether_to_invoice_remarks" cols="23" rows="2"
										style="margin-top: 5px;" placeholder="备注信息"></textarea></td>
										<td>开票日期</td>
								<td><input type="date" name="billing_date" value=""></td>
							</tr>
							<tr>
								<td>是否办理免税，信用证</td>
								<td>
								<div class="out_search">
								<select name="duty_free" >
										<c:forEach var="Duty" items="${DutyFree}" varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${Duty.ID}" text="${Duty.Status}">${Duty.Status }</option>
											</c:if>
										</c:forEach>
								</select>
								</div>
								 <textarea name="duty_free_remarks" cols="23" rows="2"
										style="margin-top: 5px;" placeholder="备注信息"></textarea></td>
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
								<th>修改</th>
								<th>删除</th>
							</tr>
						</thead>
						<tbody>

						</tbody>
					</table>
				</div>
				<div class="edit_btn">
					<input type="text" id="order_id" style="display: none"> 
					<input type="button" value="添加" class="bToggle" id="contract_apply_add">
						<input type="button" value="导入" class="bToggle" id="upload"> 

				</div>
				<div class="apply_add_info" style="display: none">
					<div class="apply_left">
						产品型号 <input type="search" name="search" title="输入型号按回车完成搜索" placeholder="输入型号按回车搜索"><select id="select" multiple style="width: 220px;height: 150px;">
						</select>
					</div>
					<div class="apply_right">
						数量&nbsp;&nbsp;&nbsp;<input type="text" name="equipment_counts">
						&nbsp;&nbsp;&nbsp;<input type="button" name="add_submit"
							value="提交" class="bToggle" text="">
					</div>
				</div>
			</div>
			<div class="supply_detail" style="display: none;">
				<div class="contract_title">合同明细</div>
				<div class="supply_detail_close">关闭</div>
				<div class="supply_detail_info">
					<table border="1" cellspacing="0" cellpadding="0"
						class="contractPage">
						<thead>
							<tr>
								<th>合同金额（USD）</th>
								<th>合同金额（RMB）</th>
								<th>付款条件</th>
								<th>是否付款</th>
								<th>收款日期1</th>
								<th>收款金额1</th>
								<th>收款日期2</th>
								<th>收款金额2</th>
								<th>收款日期3</th>
								<th>收款金额3</th>
								<th>是否开具发票</th>
								<th>开票日期</th>
								<th>是否办理免税，信用证</th>

							</tr>
						</thead>
						<tbody>

						</tbody>
					</table>
				</div>
			</div>

			<c:choose>
				<c:when test="${queryType=='common' }">
					<c:set var="queryUrl" value="StockPurchasing?currentPage="></c:set>
				</c:when>
				<c:when test="${queryType=='singleSelect' }">
					<c:choose>
						<c:when test="${ classify1 =='合同货期' || classify1 == '实际货期' || classify1== '预计货期' }">
							<c:set var="queryUrl" value="GetOrderByPageOneInPrice?PageType=1&type1=${classify1}&searchContent1=${parameter1}&start_time1=${start_time1}&end_time1=${end_time1}&currentPage="></c:set>
						</c:when>
						<c:otherwise>
							<c:set var="queryUrl" value="GetOrderByPageOneInPrice?PageType=1&type1=${classify1}&searchContent1=${parameter1}&currentPage="></c:set>
						</c:otherwise>
					</c:choose>
				</c:when>

				<c:when test="${queryType=='mixSelect' }">
					<c:choose>
						<c:when test="${ classify1 =='合同货期' || classify1 == '实际货期' || classify1== '预计货期' || classify2 =='合同货期' || classify2 == '实际货期' || classify2== '预计货期'}">
							<c:set var="queryUrl" 
							value="GetOrderByPageOneInPrice?PageType=1&type1=${classify1}&searchContent1=${parameter1}&start_time1=${start_time1}&end_time1=${end_time1}&type2=${classify2}&searchContent2=${parameter2}&start_time2=${start_time2}&end_time2=${end_time2}&currentPage=">
							</c:set>
						</c:when>
						<c:otherwise>
							<c:set var="queryUrl"
							value="GetOrderByPageOneInPrice?PageType=1&type1=${classify1}&searchContent1=${parameter1}&type2=${classify2}&searchContent2=${parameter2}&currentPage="></c:set>
						</c:otherwise>
					</c:choose>
				</c:when>
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
	<div class="contract_send" style="display: none;">
		<div class="contract_title">确认发货</div>
		<div class="contractSend_close">关闭</div>
		<div class="basic_info">
			<div class="table_title">是否可以发货</div>
			<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
				<tbody>	
					<tr>
					    <td>公司名称</td>
						<td><input type="text" name="ConsigneeCompany" value="" id="ConsigneeCompany"></td>	
						<td>公司地址</td>
						<td><input type="text" name="ConsigneeAddress" value=""  id="ConsigneeAddress"></td>
					</tr>
					<tr>
						
						<td>联系人</td>
						<td><input type="text" name="ConsigneeContacts" value="" ></td>
						<td>电话</td>
						<td><input type="text" name="ConsigneeTel" value=""></td>
					</tr>
					<tr style="display:none">
						
						<td>序号</td>
						<td><input type="text" name="PriceId" value="" ></td>
						<td>合同号</td>
						<td><input type="text" name="ContractNo" value=""></td>
						<td>合同类型</td>
						<td><input type="text" name="ContractTitle" value=""></td>
					</tr>
				</tbody>
			</table>		
		</div>
		<div class="edit_btn">
			<input type="button" value="提交" class="bToggle" id="send_submit">
			<input type="button" value="取消" class="bToggle" id="send_cancel">
		</div>
	</div>
	
</body>
<script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script>
<script src="js/swiper-3.4.1.jquery.min.js" type="text/javascript" charset="utf-8"></script>
<script src="js/ajaxfileupload.js" type="text/javascript" charset="utf-8"></script>
<script src="plugins/cookie/jquery.cookie.js"></script>
<script src="js/msgbox.js"></script>
<script src="js/msgbox_unload.js"></script>
<!-- <script src="js/global/myFunction.js"></script> -->
<script src="js/global/responseLoading.js"></script>
<script src="js/StockPurchase.js"></script>
<script type="text/javascript">
	function FistPage(arg) {
		var currentHref = window.location.href.split("ActualDelivery=")[1];
		window.location.href = arg + "1"+"&ActualDelivery="+currentHref;
	}
	function UpPage(arg) {
		var currentHref = window.location.href.split("ActualDelivery=")[1];
		window.location.href = arg+"&ActualDelivery="+currentHref;
	}
	function NextPage(arg) {
		var currentHref = window.location.href.split("ActualDelivery=")[1];
		window.location.href = arg+"&ActualDelivery="+currentHref;
	}
	function PageJump(arg) {
		var currentHref = window.location.href.split("ActualDelivery=")[1];
		var jumpNumber = document.getElementById("jumpNumber").value;
		if (jumpNumber == null || jumpNumber == 0) {
			jumpNumber = $('#currentPage').html();
		} else if (jumpNumber > parseInt($('#allPage').html())) {
			jumpNumber = $('#allPage').html();
		}
		window.location.href = arg + jumpNumber+"&ActualDelivery="+currentHref;
	}
	function LastPage(arg) {
		var currentHref = window.location.href.split("ActualDelivery=")[1];
		var jumpNumber = parseInt($('#allPage').html());
		window.location.href = arg + jumpNumber+"&ActualDelivery="+currentHref;
	}
	
	$(function() {
		// 
		var MyDate = new Date();
		var Y =  MyDate.getFullYear();
		var str = Y +"年度总目标值：";
		/* alert(str) */
		$(".prompt-content1").html("").html(str);
		pageStyle();
		
		/* if ($('#currentPage').html() == 1) {
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
		} */
		if (
<%=request.getAttribute("queryType").equals("singleSelect")%>
	||
<%=request.getAttribute("queryType").equals("common")%>
	) {
			$('.select2').hide();
		} else {
			$('.select2').show();
		}

		// 日期格式化
		tdDateHandle("td.format_date","","#000");
	});
	
        var html = document.documentElement;
        var htmlw = html.clientWidth;
        html.style.fontSize = htmlw / 192 + 'px';
        $(".prompt-top").css("lineHeight",35/parseFloat($(".prompt-top ul li").css("fontSize")));
    window.onresize = function(){ 
	 	var html = document.documentElement;
        var htmlw = html.clientWidth;
        console.log(htmlw)
        html.style.fontSize = htmlw / 192 + 'px';
        $(".prompt-top").css("lineHeight",35/parseFloat($(".prompt-top ul li").css("fontSize")));
    }
    </script>
</html>