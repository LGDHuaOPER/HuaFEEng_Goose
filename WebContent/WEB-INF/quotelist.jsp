<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报价系统</title>
<link rel="shortcut icon" href="image/eoulu.ico" />
<link rel="bookmark" href="image/eoulu.ico" />
<link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" />
<link rel="stylesheet" type="text/css" href="css/quotelist.css">
</head>
<body>
	<!-- 	头部开始 -->
	<%@include file="top.jsp"%>
	<!-- 	头部结束 -->
	<div class="contain">
		<!-- 商品管理权限问题 -->
		<div style="display: none;" id="hideMerchandise">
			<c:forEach var="authoritiy" items="${authorities}" varStatus="status">
				<c:if test="${authoritiy=='Commodity'}">
					<span>是</span>
				</c:if>
			</c:forEach>
		</div>
	
		<!--成本价格等权限问题  -->
		<div style="display: none;" id="hideMarkDom">
			<c:forEach var="authoritiy" items="${authorities}" varStatus="status">
				<c:if test="${authoritiy=='ShowCostPrice'}">
					<span>是</span>
				</c:if>
			</c:forEach>
		</div>
		
		<div class="content">
			<!-- 	=======================导航栏   开始 ================================== -->
			<%@include file="nav.jsp"%>
				<div class="choose lf" style="margin-top: 50px;">
					<input type="button" value="添加" class="bToggle" onclick="AddContract()" style="margin: 0px 0 10px 0">
				</div>
				<!-- 新增的搜索功能 -->
				<!--地址写好后要修改-->
			<c:if test="${department=='商务部'}">
				<form id="top_text_from" name="top_text_from" method="get" action="QuotationSystem">
			</c:if>
			<c:if test="${department=='销售部'}">
				<form id="top_text_from" name="top_text_from" method="get" action="SalesQuotationSystem">
			</c:if>
				<input type="text" id="shuju" name="" value="${queryType}"
					style="display: none;">
				<div class="select-content">
					<label> <c:choose>
							<c:when test="${queryType=='MixSelect'}">
								<label><input type="radio" id="singleSelect"
									name="queryType" class="singleSelect" value="SingleSelect"
									onclick="Check(this.value)">单一查询 </label>&nbsp;&nbsp;&nbsp;<label>
									<input type="radio" id="mixSelect" name="queryType"
									value="MixSelect" checked="checked" onclick="Check(this.value)">组合查询
								</label>&nbsp;&nbsp;&nbsp;<br>
							</c:when>
							<c:otherwise>
								<label><input type="radio" id="singleSelect"
									name="queryType" class="singleSelect" value="SingleSelect"
									checked="checked" onclick="Check(this.value)">单一查询 </label>&nbsp;&nbsp;&nbsp;<label>
									<input type="radio" id="mixSelect" name="queryType"
									value="MixSelect" onclick="Check(this.value)">组合查询
								</label>&nbsp;&nbsp;&nbsp;<br>
							</c:otherwise>
						</c:choose><c:set var="dropdown"
								value="${fn:split('客户名称,联系人,销售,报价日期,报价单号,产品型号,备货请求',',')}"></c:set> 
						<div class="select1">
							<select name="classify1" id="type1">
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
							
							<c:if test="${queryType=='SingleSelect' || queryType=='MixSelect'}">
								 <c:choose>
									<c:when test="${ classify1 =='合同货期' || classify1 == '实际货期' || classify1== '预计货期' }">
										<input type="text" id="searchContent1" name="searchContent1"
											style="display: none;">
										<input type="date" name="" class="time startTime"
											style="margin-left: -5px; display: inline-block;" value="${start_time1 }">
										<span class="time" style="display: inline-block;">-</span>
										<input type="date" name="" class="time endTime"
											style="display: inline-block;" value="${end_time1 }">
									</c:when>
									<c:otherwise>
										<input type="text" id="searchContent1" name="param1" style="display: inline-block;" value="${param1}">
										<input type="date" name="" class="time startTime"  style="margin-left: -5px;">
										<span class="time">-</span>
										<input type="date" name="" class="time endTime">									
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${queryType!='SingleSelect' && queryType!='MixSelect'}">
								<input type="text" id="searchContent1" name="param1" style="display: inline-block;" value="${param1}">
								<input type="date" name="" class="time startTime" style="margin-left: -5px;">
								<span class="time">-</span>
								<input type="date" name="" class="time endTime">
							</c:if>
						</div>
					<c:if test="${queryType !='MixSelect'}">
						<div class="select2" style="display: none">
							<select name="classify2" id="type2">
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
							<c:if test="${queryType!='MixSelect'}">
								<input type="text" id="searchContent2" name="param2">
								<input type="date" name="" class="time startTime" style="margin-left: -5px;">
								<span class="time">-</span>
								<input type="date" name="" class="time endTime" >
							</c:if>
							<c:if test="${queryType=='MixSelect'}">
								<c:choose>
									<c:when test="${ classify2 =='合同货期' || classify2 == '实际货期' || classify2== '预计货期' }">
										<input type="text" id="searchContent2" name="param2"
											style="display: none;">
										<input type="date" name="" class="time startTime"
											style="margin-left: -5px; display: inline-block;" value="${start_time2 }">
										<span class="time" style="display: inline-block;">-</span>
										<input type="date" name="" class="time endTime"
											style="display: inline-block;" value="${end_time2 }">
									</c:when>
									<c:otherwise>
										<input type="text" id="searchContent2" name="param2" value="${param2}">
										<input type="date" name="" class="time startTime"  style="margin-left: -5px;">
										<span class="time">-</span>
										<input type="date" name="" class="time endTime">
									</c:otherwise>
								</c:choose>
							</c:if>
						</div>
					</c:if>
					<c:if test="${queryType =='MixSelect'}">
						<div class="select2" style="display: inline-block">
							<select name="classify2" id="type2">
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
							<c:if test="${queryType!='MixSelect'}">
								<input type="text" id="searchContent2" name="param2">
								<input type="date" name="" class="time startTime" style="margin-left: -5px;">
								<span class="time">-</span>
								<input type="date" name="" class="time endTime" >
							</c:if>
							<c:if test="${queryType=='MixSelect'}">
								<c:choose>
									<c:when test="${ classify2 =='合同货期' || classify2 == '实际货期' || classify2== '预计货期' }">
										<input type="text" id="searchContent2" name="param2"
											style="display: none;">
										<input type="date" name="" class="time startTime"
											style="margin-left: -5px; display: inline-block;" value="${start_time2 }">
										<span class="time" style="display: inline-block;">-</span>
										<input type="date" name="" class="time endTime"
											style="display: inline-block;" value="${end_time2 }">
									</c:when>
									<c:otherwise>
										<input type="text" id="searchContent2" name="param2" value="${param2}">
										<input type="date" name="" class="time startTime"  style="margin-left: -5px;">
										<span class="time">-</span>
										<input type="date" name="" class="time endTime">
									</c:otherwise>
								</c:choose>
							</c:if>
						</div>
					</c:if>
						<div class="select-button">
						<input type="text"  name="currentPage" style="display:none" value="1">
							<input type="button" value="搜索" class="bToggle"
								onclick="selectSearch()"> <input type="button" value="取消"
								class="bToggle" onclick="selectCancel()">
						</div>
				</div>
			<c:if test="${department=='商务部'}">
				</form>
			</c:if>
			<c:if test="${department=='销售部'}">
				</form>
			</c:if>
				
			<table id="table1" class="cf">
				<tr>
					<td style="min-width: 38px;width:3.8%;">序号</td>
					<!-- <td>修改</td> -->
					<!--删掉修改第2列改为报价日期eq(1)-->
					<td style="min-width: 68px;width:5%;" >报价日期</td>
					<td class="quote_customer">客户名称</td>
					<!--新增联系人eq(3)-->
					<td class="quote_contacts">联系人</td>
					<td style="min-width: 110px;max-width:130px;width:10%;">报价单号</td>
					<!--新增产品型号eq(5)-->
					<td class="quote_goodsModel">产品型号</td>
					<td style="min-width: 65px;width:5%;">销售</td>
					<td style="min-width: 60px;width:4%;">报价单</td>
					<td style="display:none;">USD整机</td>
					<td style="display:none;">RMB配件</td>
					<td style="display:none;">USD配件</td>
					<td style="min-width: 45px;width:3%;" class="sales_hide1">合同</td>
					<!-- 新增两列 -->
					<td style="min-width: 105px;width:6%;">报价金额RMB</td>
					<td style="min-width: 105px;width:6%;">报价金额USD</td>
					<td style="width: 145px;display:none;">RMB合同</td>
					<td style="width: 145px;display:none;" class="sales_hide2">备货请求</td>
					<td style="min-width: 70px;width: 4%;" class="sales_hide2">备货请求</td>
				 	<c:forEach var="authoritiy" items="${authorities}" varStatus="status">
						<c:if test="${authoritiy=='Commodity'}">
							<td style="min-width: 70px;" class="sales_hide3">下单(PO)</td>
						</c:if>
					</c:forEach> 

				 	<c:forEach var="authoritiy" items="${authorities}" varStatus="status">
						<c:if test="${authoritiy=='Commodity'}">
							<td style="min-width: 70px;" class="sales_hide4">发货通知</td>
						</c:if>
					</c:forEach>
					<td class="copy_quotelist" style="min-width: 45px;">复制报价单</td>
				</tr>
				
				<c:forEach var="orderInfo" items="${quotes}" varStatus="status">
					<c:if test="${status.index>0}">
						<tr class="tbody_tr">
							<td  style="max-width: 38px;" class=" fa-edit contract-edit" value="${orderInfo['ID']}" PO="" title="点击修改">${status.index+(currentPage-1)*10}</td>
								<!--删掉修改第2列改为报价日期eq(1)-->
							<td title="${orderInfo['Datesent']}">${orderInfo['Datesent']}</td>	<!-- 1 -->
							<td title="${orderInfo['CustomerCompany']}" class="quote_customer">${orderInfo['CustomerCompany']}</td><!-- 2 -->
							<!--新增联系人eq(3)——第4列，报价单号eq(4)-->
							<td title="${orderInfo['CustomerName']}" class="quote_contacts">${orderInfo['CustomerName']}</td><!-- 3 -->
							<!--2  -->
							<td title="${orderInfo['Number']}" style="max-width: 130px;">${orderInfo['Number']}</td><!-- 4 -->
							<!--新增产品型号eq(5)-->
							<td title="${orderInfo['Model']}" class="quote_goodsModel">${orderInfo['Model']}</td><!-- 5 -->
							<td>${orderInfo['StaffName']}</td><!-- 6 -->
							<!-- 报价单 -->
							<td><i class="fa fa-eye contract-show1"></i></td><!-- 7 -->
							<td style="display:none;"><i class="fa fa-eye contract-show2" ></i></td><!-- 8 -->
							<td style="display:none;"><i class="fa fa-eye contract-show3"></i></td><!-- 9 -->
							<td style="display:none;"><i class="fa fa-eye contract-show4"></i></td><!-- 10 -->
							<td style="display: none;">${orderInfo['CustomerCode']}</td><!--11  -->
							<td style="display: none;">${orderInfo['CustomerName']}</td><!--12  -->
							<td style="display: none;">${orderInfo['CustomerTel']}</td><!--13  -->
							<td style="display: none;" class="td_CustomerFax">${orderInfo['CustomerFax']}</td><!--14 -->
							<td style="display: none;">${orderInfo['LeadTime']}</td><!--15  -->
							<td style="display: none;">${orderInfo['Payment']}</td><!--16  -->
							<td style="display: none;">${orderInfo['StaffName']}</td><!--17  -->
							<td style="display: none;">${orderInfo['Department']}</td><!--18 -->
							<td style="display: none;" class="i-ShipmentCost">${orderInfo['ShipmentCost']}</td><!--19  -->
							<td style="display: none;">${orderInfo['DeliveryWay']}</td><!--20  -->
							<td style="display: none;" class="ExchangeRate">${orderInfo['ExchangeRate']}</td><!--21  -->
							<td style="display: none;">${orderInfo['Valid']}</td><!--22 -->
							<td style="display: none;">${orderInfo['Currency']}</td><!--23  -->
							<td style="display: none;" class="TaxCategories">${orderInfo['TaxCategories']}</td><!--24  -->
							<td style="display: none;">${orderInfo['Versions']}</td><!--25  -->
							<td style="display: none;">${orderInfo['Datesent']}</td><!--26 -->
							<td style="display: none;">${orderInfo['Number']}</td><!--27 -->
							<td style="display: none;">${orderInfo['StaffMail']}</td><!--28  -->
							<td style="display: none;">${orderInfo['StaffTel']}</td><!--29 -->
							<td style="display: none;">${orderInfo['CustomerMail']}</td><!--30 -->
							<!-- 合同 -->
							<td class="sales_hide1"><i class="fa fa-eye contract-show5"></i></td><!-- 31 -->
							<td style="display: none;"><i class="fa fa-eye contract-show6"></i></td><!-- 32 -->
							<td style="display: none;"><i class="fa fa-eye "></i></td><!-- 33 -->
							
							<!-- 报价金额RMB和USD -->
							<td>${orderInfo['RMBTotal']}</td>	
							<td class="i-USDTotal">${orderInfo['USDTotal']}</td>	
							<c:choose>
								<c:when test="${orderInfo['MailStatus']=='no'}">
								<td class="contract-show7 sales_hide2" style="color:red;cursor:pointer;max-width:77px;text-decoration: underline;">未备货</td>
								</c:when>
								<c:otherwise>
									<td class="contract-show7 sales_hide2" style="cursor:pointer;max-width:77px;font-size:14px" title="${orderInfo['MailStatus']}">${orderInfo['MailStatus']}</td><!-- 34 -->
								</c:otherwise>
							</c:choose>
							<c:forEach var="authoritiy" items="${authorities}" varStatus="status">
								<c:if test="${authoritiy=='Commodity'}">
									<td class="CascadePO sales_hide3" style="max-width: 70px;"><!-- 35 -->
										<c:if test="${orderInfo['CascadeCompleteStatus']=='yes'}">
											<p style=" cursor:pointer;color:green;" title="Cascade PO 已发送" >Cascade PO 已发送</p>
										</c:if>
										<c:if test="${orderInfo['CascadeStatus']=='yes'}">
											<p style=" cursor:pointer;color:green;" title="Cascade PO 已发送" >Cascade PO 已发送</p>
										</c:if>
										<c:if test="${orderInfo['OtherStatus']=='yes'}">
											<p style="cursor:pointer;color:green;" title="其他供应商 PO 已发送">其他供应商 PO 已发送</p>
										</c:if>
										<c:if test="${orderInfo['OtherRMBStatus']=='yes'}">
											<p style=" cursor:pointer;color:green;" title="其他供应商RMB PO 已发送">其他供应商RMB PO 已发送</p>
										</c:if>
										<c:if test="${orderInfo['CascadeStatus']=='no'&&orderInfo['OtherStatus']=='no'&&orderInfo['OtherRMBStatus']=='no'&&orderInfo['CascadeCompleteStatus']=='no'}">
											<p style="color:red; cursor:pointer;text-decoration: underline;" title="未发送">未发送</p>
										</c:if>
									</td>
								</c:if>
							</c:forEach>
							<c:forEach var="authoritiy" items="${authorities}" varStatus="status">
								<c:if test="${authoritiy=='Commodity'}">
									<td class="Shipnotice sales_hide4" style="max-width: 70px;"><!-- 35 -->
										<c:if test="${orderInfo['DeliveryAdvice']=='yes'}">
											<p style="cursor:pointer;color:green;">已发送</p>
										</c:if>
										<c:if test="${orderInfo['DeliveryAdvice']=='no'}">
											<p style="cursor:pointer;color:red;">未发送</p>
										</c:if>
									</td>
								</c:if>
							</c:forEach>
							<td class="copy_quotelist_td"><i class="fa fa-clone" aria-hidden="true"></i></td>
						</tr>
					</c:if>
				</c:forEach>
			</table>
			<c:if test="${department=='商务部'}">
				<c:set var="departmentApi" value="QuotationSystem"></c:set>
			</c:if>
			<c:if test="${department=='销售部'}">
				<c:set var="departmentApi" value="SalesQuotationSystem"></c:set>
			</c:if>
			<c:choose>
				<c:when test="${queryType == 'common'}">
					<c:set var="queryUrl"
						value="?type1=${classify1 }&searchContent1=${parameter1}&selected=${str}&currentPage=">
					</c:set>
				</c:when>
				<c:when test="${queryType == 'SingleSelect'}">
					<c:set var="queryUrl"
						value="?classify1=${classify1 }&param1=${param1}&queryType=${queryType}&currentPage=">
					</c:set>
				</c:when>
				<c:otherwise>
					<c:set var="queryUrl"
						value="?classify1=${classify1 }&param1=${param1}&classify2=${classify2}&param2=${param2}&queryType=${queryType}&currentPage=">
					</c:set>
				</c:otherwise>
			</c:choose>

			<div id="page">
				<div class="pageInfo">
					当前是第&nbsp;<span id="currentPage">${currentPage}</span>&nbsp;页,&nbsp;总计&nbsp;<span id="allPage">${pageCounts}</span>页
				</div>
				<div class="changePage">
					<input type="button" class="bToggle" value="首页" id="fistPage" name="fistPage" onclick="FistPage('${departmentApi}${queryUrl}')">
					<input type="button" class="bToggle" value="上一页" id="upPage" onclick="UpPage('${departmentApi}${queryUrl}${currentPage-1}')">
					<input type="button" class="bToggle" value="下一页" id="nextPage" onclick="NextPage('${departmentApi}${queryUrl}${currentPage+1}')"> 跳到第 <input type="text" id="jumpNumber" name="jumpNumber" class="jumpNumber" style="width: 30px; color: #000" onkeyup="value=value.replace(/[^\d]/g,'') "> 页 <input type="button" class="bToggle" value="GO" id="Gotojump" name="Gotojump" onclick="PageJump('${departmentApi}${queryUrl}')">
					<input type="button" class="bToggle" value="尾页" id="lastPage" name="lastPage" onclick="LastPage('${departmentApi}${queryUrl}')">
				</div>
			</div>
		</div>
	</div>
	<div class="MailBar_cover_color"></div>
	<div class="cover_color"></div>

	<!-- 添加报价需求信息 -->
	<div class="contract_add">
		<div class="contract_title">添加报价需求信息</div>
		<div class="contractAdd_close">关闭</div>
		<div class="basic_info">
			<div class="table_title">报价需求信息</div>
			<!-- 添加中的表格1 -->
			<table border="1" cellspacing="0" cellpadding="0"
				class="contract_basic" id="tableADD_one" class="tableADD_one">
				<tbody>
					<tr>
						<td>客户编号</td>
						<td><input type="text" name="CustomerCode" value=""
							id="CustomerCode"></td>
						<td style="text-align: left;"><i
							class="fa fa-search-plus CustomerInformation-search" value=""></i></td>
					</tr>
					<tr>
						<td>客户名称</td>
						<td><input type="text" name="CustomerCompany" value=""></td>
						<td style="display: none;">部门编号</td>
						<td style="display: none;"><input type="text"
							name="Department" value=""></td>
					</tr>

					<tr>
						<td>联络人</td>
						<td><input type="text" name="CustomerName" value=""></td>
						<td>联络电话</td>
						<td><input type="text" name="CustomerTel" value=""></td>
						<td>联络传真</td>
						<td><input type="text" name="CustomerFax" value=""></td>
					</tr>

					<tr>
						<td>货期</td>
						<td><input type="text" name="LeadTime" value=""
							style="width: 136px;">weeks</td>
						<td>结账方式</td>
						<td class="Payment"><input type="text" name="Payment"
							value="" style="position: absolute; width: 162px;" id="Payment"
							class="InfoPayment"> <select
							onchange="document.getElementById('Payment').value=this.options[this.selectedIndex].text"
							class="PaymentSelect">
								<option value="">请选择</option>
								<option value="GERMANY">100% TT in advance</option>
								<option value="USA">100% Irrevocable LC at sight, 90%
									paid against required documents, 10% after acceptance</option>
						</select></td>
					</tr>

					<tr>
						<td>业务员</td>
						<td><input type="text" name="StaffName" value=""></td>
						<td style="text-align: left;"><i
							class="fa fa-search-plus StaffInformation-search" value=""></i></td>
					</tr>

					<tr>
						<td>交货方式</td>
						<td class="DeliveryWay"><input type="text" name="DeliveryWay"
							value="" style="position: absolute; width: 162px;"
							id="DeliveryWay"> <select
							onchange="document.getElementById('DeliveryWay').value=this.options[this.selectedIndex].text">
								<option value="">请选择</option>
								<option value="">CIP China Main Airport</option>
								<option value="">EXW Germany</option>
								<option value="">EXW USA</option>
								<option value="">Including VAT & shipping & Insurance</option>
						</select></td>
						<td>汇率</td>
						<td class="ExchangeRate"><input type="text"
							name="ExchangeRate" value=""
							style="position: absolute; width: 162px;" id="ExchangeRate">
							<span> <select
								onchange="document.getElementById('ExchangeRate').value=this.options[this.selectedIndex].text">
									<option value="">请选择</option>
									<option value="">6.6</option>
							</select>
						</span></td>
						<td>有效期</td>
						<td class="Valid"><input type="text" name="Valid" value=""
							style="position: absolute; width: 162px;" id="Valid"> <select
							onchange="document.getElementById('Valid').value=this.options[this.selectedIndex].text">
								<option value="">请选择</option>
								<option value="">In 30 days</option>
								<option value="">In 10 days</option>
						</select></td>
					</tr>

					<tr>
						<td style="display:none;">交货货币</td>
						<td class="Currency"  style="display:none;"><input type="text" name="Currency"
							value="" style="position: absolute; width: 162px;" id="Currency">
							<select onchange="document.getElementById('Currency').value=this.options[this.selectedIndex].text">
								<option value="">请选择</option>
								<option value="RMB">RMB</option>
								<option value="USD">USD</option>
						</select></td>
						<td>课税类别</td>
						<td class="TaxCategories"><input type="text"
							name="TaxCategories" value=""
							style="position: absolute; width: 162px;" id="TaxCategories">
							<select onchange="document.getElementById('TaxCategories').value=this.options[this.selectedIndex].text">
								<option value="">请选择</option>
								<!-- <option value="">1.17</option> -->
								<option value="">1.16</option>
						</select></td>
						<td>报价版本号</td>
						<td><input type="text" name="Versions" value=""
							class="Versions"></td>
					</tr>
					<tr>
						<td>报价日期</td>
						<td><input type="text" value="" name="Datesent"
							class="Datesent" id="Datesent" disabled="disabled"></td>
						<td>报价单号</td>
						<td><input type="text" name="Number" value="" class="Number"
							id="Number" disabled="disabled"></td>
						<td>运输费用(USD)</td>
						<td><input type="text" name="ShipmentCost" value=""></td>
					</tr>
					<tr style="display: none;">
						<td>业务员邮件</td>
						<td><input type="text" name="StaffMail" value=""
							class="StaffMail"></td>
						<td>业务员电话</td>
						<td><input type="text" name="StaffTel" value=""
							class="StaffTel"></td>
						<td>联络人邮件</td>
						<td><input type="text" name="CustomerMail" value=""
							class="CustomerMail"></td>
					</tr>
				</tbody>
			</table>

			<!-- 添加中的表格2 -->
			<table border="1" cellspacing="0" cellspadding="0" id="tableADD"
				style="margin-top: 50px;" class="tableADD">
				<tr class="tableADDTit">
					<td>商品名称</td>
					<td>型号规格</td>
					<td>单位</td>
					<td>交货期</td>
					<td>数量</td>
					<td>单价(USD)</td>
					<td>金额(USD)</td>
					<td style="display: none;">成本单价</td>
					<td style="display: none;">预交日期</td>
					<td style="display: none;">订单号码</td>
					<td style="display: none;">明细备注</td>
					<td>Operate</td>
				</tr>

			</table>

			<div style="margin-top: 100px;"></div>

			<!-- 客户资料弹出框 -->
			<div class="CustomerInformation" style="display: none;">
				<div class="contract_title">客户资料</div>
				<div class="CustomerInformation_close">关闭</div>
				<div class="basic_info">
					<table border="1" cellspacing="0" cellpadding="0"
						class="contract_basic">
						<tbody>
							<tr>
								<td class="Customer-search"><input type="text"
									name="Customer-search" value=""
									style="position: absolute; width: 162px;" id="Customer-search">
									<select
									onchange="document.getElementById('Customer-search').value=this.options[this.selectedIndex].text">
										<option value="">请选择</option>
										<option value="CustomerCompany">客户名称</option>
										<option value="CustomerName">联系人</option>
								</select></td>
								<td><input type="text" name="param" value="" id="param"></td>
								<td style="width: 50%; text-align: left;"><i
									class="fa fa-search-plus CustomerInformation-edit" value=""></i></td>
							</tr>

							<table border="1" cellspacing="0" cellspadding="0" id="table3"
								style="margin-top: 50px;" class="CustomerTab">
								<tr class="CustomerTitle">
									<td>序号</td>
									<td>客户编号</td>
									<td>客户名称</td>
									<td>联络人</td>
									<td>联络电话一</td>
									<td>联络电话二</td>
									<td>联络传真</td>
									<td>电子邮箱</td>
								</tr>

							</table>
						</tbody>
					</table>
				</div>
			</div>

			<!-- 员工资料弹出框 -->
			<div class="StaffInformation" style="display: none;">
				<div class="contract_title">员工资料</div>
				<div class="StaffInformation_close">关闭</div>
				<div class="basic_info">
					<table border="1" cellspacing="0" cellpadding="0"
						class="contract_basic">
						<tbody>
							<tr>
								<td>员工名称</td>
								<td><input type="text" name="StaffName" value=""
									id="StaffName"></td>
								<td style="width: 50%; text-align: left;"><i
									class="fa fa-search-plus StaffInformation-edit" value=""></i></td>
							</tr>

							<table border="1" cellspacing="0" cellspadding="0" id="table3"
								style="margin-top: 50px;" class="StaffTab">
								<tr class="StaffTitle">
									<td>序号</td>
									<td>部门编号</td>
									<td>员工职务</td>
									<td>员工编号</td>
									<td>员工姓名</td>
									<td>入职日期</td>
									<td>员工联络方式</td>
									<td>是否离职</td>
									<td>邮箱</td>
								</tr>

							</table>
						</tbody>
					</table>
				</div>
			</div>

			<!-- 商品资料弹出框 -->
			<div class="CommodityInformation" style="display: none;">
				<div class="contract_title">商品资料</div>
				<div class="CommodityInformation_close">关闭</div>
				<div class="basic_info">
					<table border="1" cellspacing="0" cellpadding="0"
						class="contract_basic">
						<tbody>
							<tr>
								<td>型号</td>
								<td><input type="text" name="Model" value="" id="Model"></td>
								<td style="width: 20%; text-align: left;"><i class="fa fa-search-plus CommodityInformation-edit" value=""></i></td>
								<td>商品名称</td>
								<td><input type="text" name="good_name" value="" id="good_name"></td>
								<td style="width: 20%; text-align: left;"><i class="fa fa-search-plus CommodityInformation-edit" value=""></i></td>
							</tr>

							<table border="1" cellspacing="0" cellspadding="0" id="table3"
								style="margin-top: 50px;" class="CommodityTab">
								<tr class="CommodityTitle" style="font-weight: bold">
									<td>序号</td>
									<td>商品名称</td>
									<td>型号规格</td>
									<td>单位</td>
									<td>交货期</td>
									<td>商品产地</td>
									<c:forEach var="authoritiy" items="${authorities }" varStatus="status">
										<c:if test="${authoritiy=='ShowCostPrice' }">
											<td>成本单价</td>
											<td>折扣前成本</td>
										</c:if>
									</c:forEach>
									<td>商品售价一</td>
									<td>商品售价二</td>
									<td>商品售价三</td>
									<td>主要供应商</td>
									<td>报价时间</td>
								</tr>
							</table>
						</tbody>
					</table>
				</div>
			</div>

		</div>
		<div class="edit_btn">
			<input type="button" value="提交" class="bToggle" id="add_submit">
			<input type="button" value="取消" class="bToggle" id="add_cancel">
		</div>
	</div>

	<!-- 修改报价需求信息 -->
	<div class="contract_update">
		<div class="contract_title">修改报价需求信息</div>
		<div class="contractUpdate_close">关闭</div>
		<div class="basic_info">
			<div class="table_title">报价需求信息</div>
			<!--修改中的表格1-->
			<table border="1" cellspacing="0" cellpadding="0"
				class="contract_basic">
				<tbody>
					<tr>
						<td>客户编号</td>
						<td><input type="text" name="CustomerCode" value=""
							id="CustomerCode1"></td>
						<td style="text-align: left;"><i
							class="fa fa-search-plus CustomerInformation-search1" value=""></i></td>
					</tr>
					<tr>
						<td>客户名称</td>
						<td><input type="text" name="CustomerCompany" value=""></td>
						<td style="display: none;">部门编号</td>
						<td style="display: none;"><input type="text"
							name="Department" value=""></td>
					</tr>

					<tr>
						<td>联络人</td>
						<td><input type="text" name="CustomerName" value=""></td>
						<td>联络电话</td>
						<td><input type="text" name="CustomerTel" value=""></td>
						<td>联络传真</td>
						<td><input type="text" name="CustomerFax" value=""></td>
					</tr>

					<tr>
						<td>货期</td>
						<td><input type="text" name="LeadTime" value=""
							style="width: 136px;">weeks</td>
						<td>结账方式</td>
						<td class="Payment"><input type="text" name="Payment"
							value="" style="position: absolute; width: 162px;" id="Payment1"
							class="InfoPayment"> <select
							onchange="document.getElementById('Payment1').value=this.options[this.selectedIndex].text"
							class="PaymentSelect">
								<option value="">请选择</option>
								<option value="GERMANY">100% TT in advance</option>
								<option value="USA">100% Irrevocable LC at sight, 90%
									paid against required documents, 10% after acceptance</option>
						</select></td>
					</tr>

					<tr>
						<td>业务员</td>
						<td><input type="text" name="StaffName" value=""></td>
						<td style="text-align: left;"><i
							class="fa fa-search-plus StaffInformation-search1" value=""></i></td>
					</tr>

					<tr>
						<td>交货方式</td>
						<td class="DeliveryWay" ><input type="text" name="DeliveryWay"
							value="" style="position: absolute; width: 162px;"
							id="DeliveryWay1"> <select
							onchange="document.getElementById('DeliveryWay1').value=this.options[this.selectedIndex].text">
								<option value="">请选择</option>
								<option value="">CIP China Main Airport</option>
								<option value="">EXW Germany</option>
								<option value="">EXW USA</option>
								<option value="">Including VAT & shipping & Insurance</option>
						</select></td>
						<td>汇率</td>
						<td class="ExchangeRate"><input type="text"
							name="ExchangeRate" value=""
							style="position: absolute; width: 162px;" id="ExchangeRate1">
							<select
							onchange="document.getElementById('ExchangeRate1').value=this.options[this.selectedIndex].text">
								<option value="">请选择</option>
								<option value="">6.6</option>
						</select></td>
						<td>有效期</td>
						<td class="Valid"><input type="text" name="Valid" value=""
							style="position: absolute; width: 162px;" id="Valid1"> <select
							onchange="document.getElementById('Valid1').value=this.options[this.selectedIndex].text">
								<option value="">请选择</option>
								<option value="">In 30 days</option>
								<option value="">In 10 days</option>
						</select></td>
					</tr>

					<tr>
						<td style="display:none;">交货货币</td>
						<td class="Currency"  style="display:none;"><input type="text" name="Currency" value="" style="position: absolute; width: 162px;" id="Currency1">
							<span><select onchange="document.getElementById('Currency1').value=this.options[this.selectedIndex].text">
									<option value="">请选择</option>
									<option value="RMB">RMB</option>
									<option value="USD">USD</option>
									</select>
							</span>
						</td>
						<td>课税类别</td>
						<td class="TaxCategories"><input type="text"
							name="TaxCategories" value=""
							style="position: absolute; width: 162px;" id="TaxCategories1">
							<span><select
								onchange="document.getElementById('TaxCategories1').value=this.options[this.selectedIndex].text">
									<option value="">请选择</option>
									<option value="">1.17</option>
							</select>
						</span></td>
						<td>报价版本号</td>
						<td><input type="text" name="Versions" value=""
							class="Versions"></td>
					</tr>
					<tr>
						<td>报价日期</td>
						<td><input type="text" value="" name="Datesent"
							class="Datesent" id="Datesent" disabled="disabled"></td>
						<td>报价单号</td>
						<td><input type="text" name="Number" value="" class="Number"
							id="Number" disabled="disabled"></td>
						<td>运输费用(USD)</td>
						<td><input type="text" name="ShipmentCost" value=""></td>
					</tr>
					<tr style="display: none;">
						<td>业务员邮件</td>
						<td><input type="text" name="StaffMail" value=""
							class="StaffMail"></td>
						<td>业务员电话</td>
						<td><input type="text" name="StaffTel" value=""
							class="StaffTel"></td>
						<td>联络人邮件</td>
						<td><input type="text" name="CustomerMail" value=""
							class="CustomerMail"></td>
					</tr>
				</tbody>
			</table>

			<!-- 修改中的表格2 -->
			<table border="1" cellspacing="0" cellspadding="0" id="tableUpdate"
				style="margin-top: 50px;" class="tableUpdate">
				<tr class="tableUpdateTit">
					<td>商品名称</td>
					<td>型号规格</td>
					<td>单位</td>
					<td>交货期</td>
					<td>数量</td>
					<td>单价(USD)</td>
					<td>金额(USD)</td>
					<td style="display: none;">成本单价</td>
					<td style="display: none;">预交日期</td>
					<td style="display: none;">订单号码</td>
					<td style="display: none;">明细备注</td>
					<td>Operate</td>
				</tr>
			</table>
			<div style="margin-top: 100px;"></div>

			<!-- 客户资料弹出框 -->
			<div class="CustomerInformation" style="display: none;">
				<div class="contract_title">客户资料</div>
				<div class="CustomerInformation_close">关闭</div>
				<div class="basic_info">
					<table border="1" cellspacing="0" cellpadding="0"
						class="contract_basic">
						<tbody>
							<tr>
								<td class="Customer-search"><input type="text"
									name="Customer-search" value=""
									style="position: absolute; width: 162px;" id="Customer-search1">
									<select
									onchange="document.getElementById('Customer-search1').value=this.options[this.selectedIndex].text">
										<option value="">请选择</option>
										<option value="CustomerCompany">客户名称</option>
										<option value="CustomerName">联系人</option>
								</select></td>
								<td><input type="text" name="param" value="" id="param1"></td>
								<td style="width: 50%; text-align: left;"><i
									class="fa fa-search-plus CustomerInformation-edit1" value=""></i></td>
							</tr>

							<table border="1" cellspacing="0" cellspadding="0" id="table3" style="margin-top: 50px;" class="CustomerTab1">
								<tr class="CustomerTitle1">
									<td>序号</td>
									<td>客户编号</td>
									<td>客户名称</td>
									<td>联络人</td>
									<td>联络电话一</td>
									<td>联络电话二</td>
									<td>联络传真</td>
									<td>电子邮箱</td>
								</tr>
							</table>
						</tbody>
					</table>
				</div>
			</div>

			<!-- 员工资料弹出框 -->
			<div class="StaffInformation" style="display: none;">
				<div class="contract_title">员工资料</div>
				<div class="StaffInformation_close">关闭</div>
				<div class="basic_info">
					<table border="1" cellspacing="0" cellpadding="0"
						class="contract_basic">
						<tbody>
							<tr>
								<td>员工名称</td>
								<td><input type="text" name="StaffName" value=""
									id="StaffName1"></td>
								<td style="width: 50%; text-align: left;"><i
									class="fa fa-search-plus StaffInformation-edit1" value=""></i></td>
							</tr>

							<table border="1" cellspacing="0" cellspadding="0" id="table3"
								style="margin-top: 50px;" class="StaffTab1">
								<tr class="StaffTitle1">
									<td>序号</td>
									<td>部门编号</td>
									<td>员工职务</td>
									<td>员工编号</td>
									<td>员工姓名</td>
									<td>入职日期</td>
									<td>员工联络方式</td>
									<td>是否离职</td>
									<td>邮箱</td>
								</tr>
							</table>
						</tbody>
					</table>
				</div>
			</div>

			<!-- 商品资料弹出框 -->
			<div class="CommodityInformation" style="display: none;">
				<div class="contract_title">商品资料</div>
				<div class="CommodityInformation_close">关闭</div>
				<div class="basic_info">
					<table border="1" cellspacing="0" cellpadding="0"
						class="contract_basic">
						<tbody>
							<tr>
								<td>型号</td>
								<td><input type="text" name="Model" value="" id="Model1"></td>
								<td style="width: 20%; text-align: left;"><i
									class="fa fa-search-plus CommodityInformation-edit1" value=""></i></td>
								<td>商品名称</td>
								<td><input type="text" name="good_name1" value="" id="good_name1"></td>
								<td style="width: 20%; text-align: left;"><i class="fa fa-search-plus CommodityInformation-edit1" value=""></i></td>
							</tr>

							<table border="1" cellspacing="0" cellspadding="0" id="table3"
								style="margin-top: 50px;" class="CommodityTab1">
								<tr class="CommodityTitle1" style="font-weight: bold">
									<td>序号</td>
									<td>商品名称</td>
									<td>型号规格</td>
									<td>单位</td>
									<td>交货期</td>
									<td>商品产地</td>
									<c:forEach var="authoritiy" items="${authorities }"
										varStatus="status">
										<c:if test="${authoritiy=='ShowCostPrice' }">
											<td>成本单价</td>
											<td>折扣前成本</td>
										</c:if>
									</c:forEach>
									<td>商品售价一</td>
									<td>商品售价二</td>
									<td>商品售价三</td>
									<td>主要供应商</td>
									<td>报价时间</td>
								</tr>
								<!-- <tr class="clickThisUpdate">
								<td>1号</td>
								<td>2名称</td>
								<td>3规格</td>
								<td>4 </td>
								<td>5期</td>
								<td>6产地</td>
								<td>7单价</td>
								<td>8后成本</td>
								<td>9售价一</td>
								<td>0售价二</td>
								<td>-售价三</td>
								<td>=供应商</td>
							</tr> -->
							</table>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="edit_btn">
			<input type="button" value="提交" class="bToggle" id="update_submit">
			<input type="button" value="取消" class="bToggle" id="update_cancel">
		</div>
	</div>

	<div class="allHidePdf" style="display:none;">
		<!--     整机人民币版all -->
		<div class="hidePdf1" >
		
			<!-- RMB整机 -->
			<div id="view1" class="news" style="display:block;">
				<!-- 页眉-->
				<div class="yemei" style="margin-top: 20px;">
					<div style="width: 100%; height: 40px;">
						<!-- <div class="logo rt">
							<img src="image/EOULUlogo.png" style="width: 175px; height: 61px;">
						</div> -->
					</div>
					<!-- <hr> -->
				</div>
				<!--     整机人民币版 -->
				<div id="CompleteMachineChina">
					<!-- 文档中的页眉 -->
					<div class="" style="margin-bottom: 20px;">
						<img src="image/cpyemei.png" style="width: 90%; height: auto; margin-left: 5%;">
					</div>
					<div class="m30b">
						<table cellpadding="0" cellspacing="0" border="1" style="width: 90%; margin-bottom: 20px; border: 2px solid #00aeef;">
							<tr class="tc s12 b">
								<td>
									<p>
										<span class="Number1">Number:</span> <span class="Number">QUA024170321005</span>
									</p>
								</td>
								<td>
									<p>
										<span class="">Date Sent:</span> <span class="DateSent">2017/3/21</span>
									</p>
								</td>
								<td>
									<p>
										<span class="Versions1">Versions:</span> <span class="Versions">报价版本号</span>
									</p>
								</td>
							</tr>
						</table>
					</div>
	
					<div class="write-name lf m60b lh25 b" style="padding-left: 70px; position: relative;">
						<p class="s14">
							<span class="CustomerCompany">杭州士兰半导体</span>
						</p>
						<!-- <p class="s14">China (People’s Republic)</p> -->
						<!-- <br> <br> -->
						<p>
							<img src="image/people.png"
								style="width: 20px; margin-right: 5px;"><span
								class="CustomerName s10">Wang Shengrong</span>
						</p>
						<p>
							<img src="image/tel.png" style="width: 20px; margin-right: 5px;"><span
								class="CustomerTel s10">+86-21-61751348</span>
						</p>
						<p>
							<img src="image/email.png" style="width: 20px; margin-right: 5px;"><span
								class="CustomerMail s10">wangshengrong@hisilicon.com</span>
						</p>
	
					</div>
					<div class="write-name rt m60b lh25 b"
						style="position: relative; padding-right: 70px;">
						<p class="s14">苏州伊欧陆系统集成有限公司</p>
						<!-- <p class="s14">China</p> -->
						<!-- <br> <br> -->
						<p>
							<img src="image/people.png"
								style="width: 20px; margin-right: 5px;"><span
								class="StaffName s10">赵文珍</span>
						</p>
						<p>
							<img src="image/tel.png" style="width: 20px; margin-right: 5px;"><span
								class="StaffTel s10">86-18566664208</span>
						</p>
						<p>
							<img src="image/email.png" style="width: 20px; margin-right: 5px;"><span
								class="StaffMail s10">zhaowenzhen@eoulu.com</span>
						</p>
					</div>
	
					<div class="m60b tc s13">
						<table cellpadding="0" cellspacing="0" border="1" style="width: 90%; margin-bottom: 20px;" class="pdf_one_tab">
							<tr style="background: #e4e8eb;" class="ita">
								<td style="width: 5.17%;"><p>Item</p></td>
								<td style="width: 13.98%;"><p>Part Number</p></td>
								<td style="width: 64%;"><p>Description</p></td>
								<td style="display: none"><p>Each(RMB)</p></td>
								<td style="width: 5.31%;"><p>Qty</p></td>
								<td style="display: none"><p>Extended(RMB)</p></td>
								<td  style="width: 10.48%;">Operate</td>
							</tr>
							<!--  <tr>
			                <td><p contenteditable="true" class="Item">1</p></td>
			                <td><p contenteditable="true" class="PartNumber"></p></td>
			                <td><p contenteditable="true" class="Description"></p></td>
			                <td  style="display:none"><p contenteditable="true" class="EachRMB"></p></td>
			                <td><p contenteditable="true" class="Qty"></p></td>
			                <td  style="display:none"><p contenteditable="true" class="ExtendedRMB"></p></td>
			            </tr> -->
						</table>
					</div>
	
					<div class="m60b s13">
						<table cellpadding="0" cellspacing="0" border="1"
							style="width: 70%; margin-bottom: 20px; text-align: right; margin-right: 5%;">
							<tr>
								<td style="width: 80%;"><p class="pr10">Sub-Total RMB:</p></td>
								<td><p class="TotalRMB" contenteditable="true"></p></td>
							</tr>
							<tr>
								<td><p class="Gifts pr10" contenteditable="true"></p></td>
								<td><p class="GiftsTotal" contenteditable="true"></p></td>
							</tr>
							<tr>
								<td><p class="pr10">Shipment & Insurance RMB:</p></td>
								<td><p class="ShipmentInsuranceRMB"></p></td>
							</tr>
							<tr>
								<td><p class="pr10">Total Including VAT & shipping & Insurance RMB:</p></td>
								<td><p class="TotalIncludingVATRMB" ></p></td>
							</tr>
						</table>
					</div>
	
					<div class="m30">
						<p class="Comments b s15">Notes and Comments</p>
						<br> <br>
						<div class="notes s12 blue b">
							<p>
								(1)&nbsp;<span>Valid:</span><span class="Valid">In 30 days</span>
							</p>
							<p>
								(2)&nbsp;<span>Lead Time:</span><span class="LeadTime">16 weeks
									after received 100% payment</span>&nbsp;weeks
							</p>
							<p>
								(3)&nbsp;<span>Warranty: </span><span>12 months No warrant on
									consumable parts</span>
							</p>
							<p>
								(4)&nbsp;<span>Payment: </span><span class="Payment">100% TT in
									advance</span>
							</p>
							<p>
								(5)&nbsp;<span>Seller:</span><span>Suzhou Eoulu System Integration Co., Ltd</span>
							</p>
							<p>For more products, please visit <span class="tdline">www.eoulu.com</span></p>
							<p class="content_text">* 本货期不包含由于出口国政府或相关企业的审查造成的交期延迟，由于出口国政府或相关企业的审查，对货物 交期造成影响，  或造成货物扣押的情况，卖方不承担相关责任。</p>
						</div>
					</div>
	
				</div>
				<!-- 页脚 -->
				<div class="yejiao1 cf" style="margin-top: 20px; height: 214px;">
					<div class="" style="margin-bottom: 20px;">
						<img src="image/yejiaoChina.png"
							style="width: 90%; height: auto; margin-left: 5%;">
					</div>
	
				</div>
			</div>
			
			<!------------------USD整机-------------------------------->
			<div id="view2" class="news"  style="display:none;">
				<!-- 页眉-->
				<div class="yemei" style="margin-top: 20px;">
					<div style="width: 100%; height: 40px;">
						<!-- <div class="logo rt">
							<img src="image/EOULUlogo.png" style="width: 175px; height: 61px;">
						</div> -->
					</div>
					<!-- <hr> -->
				</div>
				<!--     整机美元版 -->
				<div id="CompleteMachineUSD">
					<!-- 文档中的页眉 -->
					<div class="" style="margin-bottom: 20px;">
						<img src="image/cpyemei.png" style="width: 90%; height: auto; margin-left: 5%;">
					</div>
	
					<div class="m30b">
						<table cellpadding="0" cellspacing="0" border="1" style="width: 90%; margin-bottom: 20px; border: 2px solid #00aeef;">
							<tr class="tc s12 b">
								<td>
									<p>
										<span class="Number1">Number:</span> <span class="Number">QUA024170321005</span>
									</p>
								</td>
								<td>
									<p>
										<span class="">Date Sent:</span> <span class="DateSent">2017/3/21</span>
									</p>
								</td>
								<td>
									<p>
										<span class="Versions1">Versions:</span> <span class="Versions">报价版本号</span>
									</p>
								</td>
							</tr>
						</table>
					</div>
	
					<div class="write-name lf m60b b" style="padding-left: 70px; position: relative;">
						<p class="s14">
							<span class="CustomerCompany">杭州士兰半导体</span>
						</p>
						<!-- <p class="s14">China (People’s Republic)</p>
						<br> <br> -->
						<p>
							<img src="image/people.png"
								style="width: 20px; margin-right: 5px;"><span
								class="CustomerName s10">Wang Shengrong</span>
						</p>
						<p>
							<img src="image/tel.png" style="width: 20px; margin-right: 5px;"><span
								class="CustomerTel s10">+86-21-61751348</span>
						</p>
						<p>
							<img src="image/email.png" style="width: 20px; margin-right: 5px;"><span
								class="CustomerMail s10">wangshengrong@hisilicon.com</span>
						</p>
	
					</div>
					<div class="write-name rt m60b b" style="position: relative; padding-right: 70px;">
						<p class="s14">HK EOULU TRADING LIMTED</p>
						<!-- <p class="s14">ROOM 1708 NAN FUNG TOWER</p>
						<br> <br> -->
						<p>
							<img src="image/people.png"
								style="width: 20px; margin-right: 5px;"><span
								class="StaffName s10">赵文珍</span>
						</p>
						<p>
							<img src="image/tel.png" style="width: 20px; margin-right: 5px;"><span
								class="StaffTel s10">86-18566664208</span>
						</p>
						<p>
							<img src="image/email.png" style="width: 20px; margin-right: 5px;"><span
								class="StaffMail s10">zhaowenzhen@eoulu.com</span>
						</p>
					</div>
	
					<div class="m60b tc b">
						<table cellpadding="0" cellspacing="0" border="1" style="width: 90%; margin-bottom: 20px;" class="pdf_two_tab">
							<tr style="background: #e4e8eb;" class="ita">
								<td style="width: 5.17%;"><p>Item</p></td>
								<td style="width: 13.98%;"><p>Part</p></td>
								<td style="width: 64%;"><p>Description</p></td>
								<td style="display: none;"><p>Each(USD)</p></td>
								<td style="width: 5.31%;"><p>Qty</p></td>
								<td style="display: none;"><p>Extended(USD)</p></td>
								<td style="width: 10.48%;">Operate</td>
							</tr>
						</table>
					</div>
	
					<div class="m60b b">
						<table cellpadding="0" cellspacing="0" border="1"
							style="width: 70%; margin-bottom: 20px; text-align: right; margin-right: 5%;">
							<tr>
								<td style="width: 80%;"><p class="pr10">Total USD:</p></td>
								<td><p class="TotalUSD" contenteditable="true"></p></td>
							</tr>
							<tr>
								<td><p class="Gifts pr10" contenteditable="true"></p></td>
								<td><p class="GiftsTotal" contenteditable="true"></p></td>
							</tr>
							<tr>
								<td><p class="pr10">Shipment & Insurance USD:</p></td>
								<td><p class="ShipmentInsuranceUSD" ></p></td>
							</tr>
							<tr>
								<td><p class="pr10">Final Total CIP China Main Airport USD:</p></td>
								<td><p class="FinalTotalCIPUSD"></p></td>
							</tr>
						</table>
					</div>
	
					<div class="s12">
						<ul
							style="margin-left: 7%; margin-bottom: 60px; line-height: 30px;"
							class="test">
							<li><span>All deliveries are approximate.</span></li>
							<li><span>17% VAT is not included in the above quotation.</span></li>
							<li><span>Credit term is subject to evaluation.</span></li>
							<li><span>Please include our quote reference when ordering.</span></li>
						</ul>
	
					<div class="m30">
						<p class="Comments b s15">Notes and Comments</p>
						<br> <br>
						<div class="notes s12 blue b">
							<p>
								(1)&nbsp;<span>Valid:</span><span class="Valid">In 30 days</span>
							</p>
							<p>
								(2)&nbsp;<span>Lead Time:</span><span class="LeadTime">16 weeks
									after received 100% payment</span>&nbsp;weeks
							</p>
							<p>
								(3)&nbsp;<span>Warranty: </span><span>12 months No warrant on
									consumable parts</span>
							</p>
							<p>
								(4)&nbsp;<span>Payment: </span><span class="Payment">100% TT in
									advance</span>
							</p>
							<p>
								(5)&nbsp;<span>Seller:</span><span>HK EOULU TRADING LIMTED</span>
							</p>
							<p>For more products, please visit <span class="tdline">www.eoulu.com</span></p>
							<p class="content_text">* 本货期不包含由于出口国政府或相关企业的审查造成的交期延迟，由于出口国政府或相关企业的审查，对货物 交期造成影响，  或造成货物扣押的情况，卖方不承担相关责任。</p>
						</div>
					</div>
					<br>
					
						<div class="content_text m30b pl7">&nbsp;&nbsp;This Quotation
							is valid only for the period indicated. All Products and Services
							quoted are subject to HK EOULU TRADING LIMITED terms and
							conditions of sale. A copy of HK EOULU TRADING LIMITED and
							conditions of sale is either attached or has been previously
							provided to you. Please contact us if you have not received a copy
							or require an additional copy. HK EOULU TRADING LIMITED expressly
							objects to any different or additional terms in your
							purchase/sales order documentation, unless agreed to in writing by
							HK EOULU TRADING LIMITED. Product and Service availability dates
							are estimated at the time of the quotation. Actual delivery dates
							or delivery windows will be specified at the time HK EOULU TRADING
							LIMITED acknowledges and accepts your purchase order. The above
							conditions shall apply to the fullest extent permitted by the law
							governing this transaction and shall not derogate from any
							statutory or legal rights you may have thereunder. This quote
							shall be subject to any other applicable HK EOULU TRADING LIMITED
							terms referenced herein and specifically applicable to the type of
							Products or Services being quoted. Commodities, technology or
							software exported from the United States or from other exporting
							countries will be subject to the U.S. Export Administration
							Regulations and all exporting countries’ export laws and
							regulations. Diversion contrary to U.S. law and the applicable
							export laws and regulations is prohibited.</div>
					</div>
	
				</div>
				<!-- 页脚 -->
				<div class="yejiao1 cf" style="margin-top: 20px; height: 214px;">
					<div class="" style="margin-bottom: 20px;">
						<img src="image/yejiaoUSD.png"
							style="width: 90%; height: auto; margin-left: 5%;">
					</div>
	
				</div>
			</div>
			
			<!------------------RMB配件-------------------------------->
			<div id="view3" class="news"  style="display:none;">
				<!-- 页眉-->
				<div class="yemei" style="margin-top: 20px;">
					<div style="width: 100%; height: 40px;">
						<!-- <div class="logo rt">
							<img src="image/EOULUlogo.png" style="width: 175px; height: 61px;">
						</div> -->
					</div>
				<!-- 	<hr> -->
				</div>
	
				<!--     配件人民币版 -->
				<div id="FittingChina">
					<!-- 文档中的页眉 -->
					<div class="" style="margin-bottom: 20px;">
						<img src="image/cpyemei.png"
							style="width: 90%; height: auto; margin-left: 5%;">
					</div>
	
					<div class="m30b b">
						<table cellpadding="0" cellspacing="0" border="1"
							style="width: 90%; margin-bottom: 20px; border: 2px solid #00aeef;">
							<tr class="tc s12">
								<td>
									<p>
										<span class="Number1">Number:</span> <span class="Number">QUA024170321005</span>
									</p>
								</td>
								<td>
									<p>
										<span class="">Date Sent:</span> <span class="DateSent">2017/3/21</span>
									</p>
								</td>
								<td>
									<p>
										<span class="Versions1">Versions:</span> <span class="Versions">报价版本号</span>
									</p>
								</td>
							</tr>
						</table>
					</div>
	
					<div class="write-name lf m60b b" style="padding-left: 70px; position: relative;">
						<p class="s14">
							<span class="CustomerCompany">杭州士兰半导体</span>
						</p>
						<!-- <p class="s14">China (People’s Republic)</p>
						<br> <br> -->
						<p>
							<img src="image/people.png"
								style="width: 20px; margin-right: 5px;"><span
								class="CustomerName s10">Wang Shengrong</span>
						</p>
						<p>
							<img src="image/tel.png" style="width: 20px; margin-right: 5px;"><span
								class="CustomerTel s10">+86-21-61751348</span>
						</p>
						<p>
							<img src="image/email.png" style="width: 20px; margin-right: 5px;"><span
								class="CustomerMail s10">wangshengrong@hisilicon.com</span>
						</p>
	
					</div>
					<div class="write-name rt m60b b" style="position: relative; padding-right: 70px;">
						<p class="s14">苏州伊欧陆系统集成有限公司</p>
						<!-- <p class="s14">China</p>
						<br> <br> -->
						<p>
							<img src="image/people.png" style="width: 20px; margin-right: 5px;"><span
								class="StaffName s10">赵文珍</span>
						</p>
						<p>
							<img src="image/tel.png" style="width: 20px; margin-right: 5px;"><span
								class="StaffTel s10">86-18566664208</span>
						</p>
						<p>
							<img src="image/email.png" style="width: 20px; margin-right: 5px;"><span
								class="StaffMail s10">zhaowenzhen@eoulu.com</span>
						</p>
					</div>
	
					<div class="m60b tc s13">
						<table cellpadding="0" cellspacing="0" border="1" style="width: 90%; margin-bottom: 20px;" class="pdf_three_tab">
							<tr style="background: #e4e8eb;" class="ita">
								<td style="width: 5.17%;"><p>Item</p></td>
								<td style="width: 12.58%;"><p>Part Number</p></td>
								<td style="width: 45%;"><p>Description</p></td>
								<td style="width: 10.48%;"><p>Each(RMB)</p></td>
								<td style="width: 5.17%;"><p>Qty</p></td>
								<td style="width: 10.48%;"><p>Extended(RMB)</p></td>
								<td style="width: 10.48%;min-width:75px;">Operate</td>
							</tr>
						</table>
					</div>
	
					<div class="m60b s13">
						<table cellpadding="0" cellspacing="0" border="1"
							style="width: 70%; margin-bottom: 20px; text-align: right; margin-right: 5%;">
							<tr>
								<td style="width: 80%;"><p class="pr10">Sub-Total RMB:</p></td>
								<td><p class="TotalRMB" contenteditable="true"></p></td>
							</tr>
							<tr>
								<td><p class="Gifts pr10" contenteditable="true"></p></td>
								<td><p class="GiftsTotal" contenteditable="true"></p></td>
							</tr>
							<tr>
								<td><p class="pr10">Shipment & Insurance RMB:</p></td>
								<td><p class="ShipmentInsuranceRMB" ></p></td>
							</tr>
							<tr>
								<td><p class="pr10">Total Including VAT & shipping & Insurance RMB:</p></td>
								<td><p class="TotalIncludingVATRMB"></p></td>
							</tr>
						</table>
					</div>
	
					<div class="m30">
						<p class="Comments b s15">Notes and Comments</p>
						<br> <br>
						<div class="notes s12 b blue">
							<p>
								(1)&nbsp;<span>Valid:</span><span class="Valid">In 30 days</span>
							</p>
							<p>
								(2)&nbsp;<span>Lead Time:</span><span class="LeadTime">16 weeks
									after received 100% payment</span>&nbsp;weeks
							</p>
							<p>
								(3)&nbsp;<span>Warranty: </span><span>12 months No warrant on
									consumable parts</span>
							</p>
							<p>
								(4)&nbsp;<span>Payment: </span><span class="Payment">100% TT in
									advance</span>
							</p>
							<p>
								(5)&nbsp;<span>Seller:</span><span>Suzhou Eoulu System Integration Co., Ltd</span>
							</p>
							<p>For more products, please visit <span class="tdline">www.eoulu.com</span></p>
							<p class="content_text">* 本货期不包含由于出口国政府或相关企业的审查造成的交期延迟，由于出口国政府或相关企业的审查，对货物 交期造成影响，  或造成货物扣押的情况，卖方不承担相关责任。</p>
						</div>
					</div>
				</div>
	
				<!-- 页脚 -->
				<div class="yejiao1 cf" style="margin-top: 20px; height: 214px;">
					<div class="" style="margin-bottom: 20px;">
						<img src="image/yejiaoChina.png"
							style="width: 90%; height: auto; margin-left: 5%;">
					</div>
				</div>
	
			</div>
			
			<!------------------USD配件-------------------------------->
			<div id="view4" class="news"  style="display:none;">
			<!-- 页眉-->
			<div class="yemei" style="margin-top: 20px;">
				<div style="width: 100%; height: 40px;">
					<!-- <div class="logo rt">
						<img src="image/EOULUlogo.png" style="width: 175px; height: 61px;">
					</div> -->
				</div>
			<!-- 	<hr> -->
			</div>
			<!--     配件美元版 -->
			<div id="FittingUSD">
				<!-- 文档中的页眉 -->
				<div class="" style="margin-bottom: 20px;">
					<img src="image/cpyemei.png"
						style="width: 90%; height: auto; margin-left: 5%;">
				</div>

				<div class="m30b b">
					<table cellpadding="0" cellspacing="0" border="1"
						style="width: 90%; margin-bottom: 20px; border: 2px solid #00aeef;">
						<tr class="tc s12">
							<td>
								<p>
									<span class="Number1">Number:</span> <span class="Number">QUA024170321005</span>
								</p>
							</td>
							<td>
								<p>
									<span class="">Date Sent:</span> <span class="DateSent">2017/3/21</span>
								</p>
							</td>
							<td>
								<p>
									<span class="Versions1">Versions:</span> <span class="Versions">报价版本号</span>
								</p>
							</td>
						</tr>
					</table>
				</div>

				<div class="write-name lf m60b b" style="padding-left: 70px; position: relative;">
					<p class="s14">
						<span class="CustomerCompany">杭州士兰半导体</span>
					</p>
					<!-- <p class="s14">China (People’s Republic)</p>
					<br> <br> -->
					<p>
						<img src="image/people.png" style="width: 20px; margin-right: 5px;"><span
							class="CustomerName s10">Wang Shengrong</span>
					</p>
					<p>
						<img src="image/tel.png" style="width: 20px; margin-right: 5px;"><span
							class="CustomerTel s10">+86-21-61751348</span>
					</p>
					<p>
						<img src="image/email.png" style="width: 20px; margin-right: 5px;"><span
							class="CustomerMail s10">wangshengrong@hisilicon.com</span>
					</p>

				</div>
				<div class="write-name rt m60b b" style="position: relative; padding-right: 70px;">
					<p class="s14">HK EOULU TRADING LIMTED</p>
					<!-- <p class="s14">ROOM 1708 NAN FUNG TOWER</p>
					<br> <br> -->
					<p>
						<img src="image/people.png"
							style="width: 20px; margin-right: 5px;"><span
							class="StaffName s10">赵文珍</span>
					</p>
					<p>
						<img src="image/tel.png" style="width: 20px; margin-right: 5px;"><span
							class="StaffTel s10">86-18566664208</span>
					</p>
					<p>
						<img src="image/email.png" style="width: 20px; margin-right: 5px;"><span
							class="StaffMail s10">zhaowenzhen@eoulu.com</span>
					</p>
				</div>


				<div class="m60b tc b">
					<table cellpadding="0" cellspacing="0" border="1"
						style="width: 90%; margin-bottom: 20px;" class="pdf_four_tab">
						<tr style="background: #e4e8eb;" class="ita">
							<td style="width: 5.17%;"><p>Item</p></td>
							<td style="width: 12.58%;"><p>Part</p></td>
							<td style="width: 45%;"><p>Description</p></td>
							<td style="width: 10.48%;"><p>Each(USD)</p></td>
							<td style="width: 5.17%;"><p>Qty</p></td>
							<td style="width: 10.48%;"><p>Extended(USD)</p></td>
							<td style="width: 10.48%;min-width:75px;">Operate</td>
						</tr>
					</table>
				</div>

				<div class="m60b b">
					<table cellpadding="0" cellspacing="0" border="1"
						style="width: 70%; margin-bottom: 20px; text-align: right; margin-right: 5%;">
						<tr>
							<td style="width: 80%;"><p class="pr10">Total USD:</p></td>
							<td><p class="TotalUSD" contenteditable="true"></p></td>
						</tr>
						<tr>
							<td><p class="Gifts pr10" contenteditable="true"></p></td>
							<td><p class="GiftsTotal" contenteditable="true"></p></td>
						</tr>
						<tr>
							<td><p class="pr10">Shipment & Insurance USD:</p></td>
							<td><p class="ShipmentInsuranceUSD" ></p></td>
						</tr>
						<tr>
							<td><p class="pr10">Final Total CIP China Main Airport USD:</p></td>
							<td><p class="FinalTotalCIPUSD" ></p></td>
						</tr>
					</table>
				</div>

				<div class="s12">
					<ul
						style="margin-left: 7%; margin-bottom: 60px; line-height: 30px;"
						class="test">
						<li><span>All deliveries are approximate.</span></li>
						<li><span>17% VAT is not included in the above quotation.</span></li>
						<li><span>Credit term is subject to evaluation.</span></li>
						<li><span>Please include our quote reference when ordering.</span></li>
					</ul>

				<div class="m30">
					<p class="Comments b s15">Notes and Comments</p>
					<br> <br>
					<div class="notes s12 b blue">
						<p>
							(1)&nbsp;<span>Valid:</span><span class="Valid">In 30 days</span>
						</p>
						<p>
							(2)&nbsp;<span>Lead Time:</span><span class="LeadTime">16 weeks
								after received 100% payment</span>&nbsp;weeks
						</p>
						<p>
							(3)&nbsp;<span>Warranty: </span><span>12 months No warrant on
								consumable parts</span>
						</p>
						<p>
							(4)&nbsp;<span>Payment: </span><span class="Payment">100% TT in
								advance</span>
						</p>
						<p>
							(5)&nbsp;<span>Seller:</span><span>HK EOULU TRADING LIMTED</span>
						</p>
						<p>For more products, please visit <span class="tdline">www.eoulu.com</span></p>
						<p class="content_text">* 本货期不包含由于出口国政府或相关企业的审查造成的交期延迟，由于出口国政府或相关企业的审查，对货物 交期造成影响，  或造成货物扣押的情况，卖方不承担相关责任。</p>
					</div>
				</div>
				<br>
				<div class="content_text m30b pl7">&nbsp;&nbsp;This Quotation
						is valid only for the period indicated. All Products and Services
						quoted are subject to HK EOULU TRADING LIMITED terms and
						conditions of sale. A copy of HK EOULU TRADING LIMITED and
						conditions of sale is either attached or has been previously
						provided to you. Please contact us if you have not received a copy
						or require an additional copy. HK EOULU TRADING LIMITED expressly
						objects to any different or additional terms in your
						purchase/sales order documentation, unless agreed to in writing by
						HK EOULU TRADING LIMITED. Product and Service availability dates
						are estimated at the time of the quotation. Actual delivery dates
						or delivery windows will be specified at the time HK EOULU TRADING
						LIMITED acknowledges and accepts your purchase order. The above
						conditions shall apply to the fullest extent permitted by the law
						governing this transaction and shall not derogate from any
						statutory or legal rights you may have thereunder. This quote
						shall be subject to any other applicable HK EOULU TRADING LIMITED
						terms referenced herein and specifically applicable to the type of
						Products or Services being quoted. Commodities, technology or
						software exported from the United States or from other exporting
						countries will be subject to the U.S. Export Administration
						Regulations and all exporting countries’ export laws and
						regulations. Diversion contrary to U.S. law and the applicable
						export laws and regulations is prohibited.</div>
				</div>

			</div>
			<!-- 页脚 -->
			<div class="yejiao1 cf" style="margin-top: 20px; height: 214px;">
				<div class="" style="margin-bottom: 20px;">
					<img src="image/yejiaoChina.png"
						style="width: 90%; height: auto; margin-left: 5%;">
				</div>
			</div>
		</div>
		<div id="EgoMachineView" class="news"  style="display:none;min-height:1200px;">
			 <table border="1" cellspacing="0" cellspadding="0"  class="EgoMachineTable" style="width:90%;margin:50px auto 0;">
			 	<tr style="text-align:center;height:50px;">
					<td style="width:30%;">Item</td>
					<td style="width:50%;" >Item Description</td>
					<td style="width:20%;" >Quantity</td>
					<td >Operate</td>
				</tr>
			 </table>
		</div>
		<div id="EgoAccessoriesView" class="news"  style="display:none;min-height:1200px;">
			<div style="width:90%;margin:50px auto 10px;text-align:center;font-size:18px;">华为配件采购报价单</div>
			 <table border="1" cellspacing="0" cellspadding="0"  class="EgoAccessoriesTable" style="width:90%;margin:0px auto 0;table-layout: fixed;">
			 	<tr style="text-align:center;height:50px;">
					<td style="width:37px;">序号</td>
					<td style="width:64px;" >部件编码</td>
					<td style="width:67px;" >Item描述</td>
					<td style="width:88px;" >型号</td>
					<td style="width:;">描述</td>
					<td style="width: 66px;" >单价USD</td>	
					<td style="width: 66px;" >折扣USD</td>	
					<td style="width:32px;" >数量</td>	
					<td style="width:32px;">总价</td>
					<td style="width:49px;" >折扣率</td>	
					<td style="width:81px;" >备注</td>	
					<td >Operate</td>
				</tr>
				
			 </table>
		</div>
		<!-- 成本对比表 -->
		<div id="costComparisonView" class="news" style="display:none;min-height:1200px;">
			<div style="width:90%;margin:50px auto 10px;text-align:center;font-size:18px;">成本对比表</div>
			 <table border="1" cellspacing="0" cellspadding="0"  class="costComparisonTable" style="width:90%;margin:0px auto 0;table-layout: fixed;">
				 <thead>
			 	 	<tr style="text-align:center;height:50px;">
			 			<th style="width:40px;">Item</th>
			 			<th style="width:120px;" >Part Number</th>
			 			<th style="width:auto;" >Description</th>
			 			<th style="width:40px;" >Qty</th>
			 			<th style="width:100px;">Unit Price</th>
			 			<th style="width:100px;" >Total Price</th>	
			 			<!-- <th style="width: 66px;" >折扣USD</th>	
			 			<th style="width:32px;" >数量</th>	
			 			<th style="width:32px;">总价</th>
			 			<th style="width:49px;" >折扣率</th>	
			 			<th style="width:81px;" >备注</th> -->
			 			<!-- <th style="display: none"></th>	
			 			<th style="display: none"></th>	 -->
			 			<!-- <th style="width:100px;">Operate</th> -->
			 		</tr>
				 </thead>
				<tbody>
				</tbody>
			 </table>
		</div>

			<div class="Sidebar_Price">
				<div class="RMBMachine isHover" id="RMBMachine">RMB整机</div>
				<div class="USDMachine" id="USDMachine">USD整机</div>
				<div class="RMBAccessories" id="RMBAccessories">RMB配件</div>
				<div class="USDAccessories" id="USDAccessories">USD配件</div>
				
				<div class="EgoMachine" id="EgoMachine" >华为Ego<br/>整机</div>
				<div class="EgoAccessories" id="EgoAccessories">华为Ego<br/>配件</div>
				<div class="costComparison" id="costComparison">成本对<br/>比表</div>
			</div>
			
			<input type="button" value="导出Excel" class="bToggle" id="exportEXCEL" style="position: absolute; z-index: 11; top: 150px; left: 75%; width: 92px; height: 30px; font-size: 19px;display:none;">
			<input type="button" value="导出Word" class="bToggle" id="exportWord1" style="position: absolute; z-index: 11; top: 150px; left: 75%; width: 92px; height: 30px; font-size: 19px;">
			<input type="button" value="导出PDF" class="bToggle" id="exportPDF1" style="position: absolute; z-index: 11; top: 200px; left: 75%; width: 92px; height: 30px; font-size: 19px;">
			<input type="button" value="关闭" class="bToggle " id="contract_close1" style="position: absolute; z-index: 11; top: 100px; left: 75%; width: 92px; height: 30px; font-size: 19px;">
			<input type="button" value="提交" class="bToggle" id="submit_n1" style="position: absolute; z-index: 11; top: 250px; left: 75%; width: 92px; height: 30px; font-size: 19px;">
		</div>
	
	</div>

	<!--     合同USD版all -->
	<div class="hidePdf5" style="display: none;">
		<!-------------合同USD版---------------------->
		<div id="view5" class="news" style="display:none;">
			<!-- 页眉-->
			<div class="yemei" style="margin-top: 20px;">
				<p class="m30b">香港伊欧陆贸易有限公司</p>
			</div>
			<!-- 合同页面美版 -->
			<div id="ContractUSD" class="s12">
				<div class="lf m30 m30b" style="padding-left: 10%; font-size: 29px;">
					<p>CONTRACT</p>
				</div>
				<div class=" cf rt pdfDate m30b" style="padding-right: 15%;">
					<p>
						Contract No.:<span contenteditable="true" class="ContractNO">HK-2017XXXXS1</span>
					</p>
					<p>
						Date:<span class="Date" contenteditable="true"></span>
					</p>
					<p>
						Versions:<span class="Version" contenteditable="true"></span>
					</p>
				</div>

				<div class="m30b">
					<table cellpadding="0" cellspacing="0" border="1"
						style="width: 90%; margin-bottom: 20px;">
						<tr>
							<td>
								<p>

									买方 :<span class="CustomerCompany" contenteditable="true"></span>
								<p>

									BUYER:<span class="CustomerCompany" contenteditable="true"></span>
								</p>
								<p>
									<!-- <span>Add:</span> -->
									Add:<span class="CustomerAdd" contenteditable="true"></span>
								</p>

							</td>
							<td style="min-width: 250px;">
								<p>
									电话 Phone No:<span class="CustomerTel" contenteditable="true"></span>
								</p>
								<p>
									传真 Fax No:<span class="CustomerFax" contenteditable="true"></span>
								</p>
							</td>

						</tr>

						<tr>
							<td>
								<p>卖方: 香港伊欧陆贸易有限公司</p>
								<p>SELLER: HK EOULU TRADING LIMITED</p>
								<p>ROOM 1708 NAN FUNG TOWER.,173 DES VOEUX ROAD C., HONGKONG</p>
							</td>
							<td style="min-width: 250px;">
								<p>电话 Phone No: 86-755-21627981</p>
								<p>传真 Fax No: 86-755-26975515</p>
							</td>

						</tr>
						<tr>
							<td colspan="2">
								<p>
									最终用户:<span class="CustomerCompany" contenteditable="true"></span>
								</p>
								<p>
									End User:<span class="CustomerCompany" contenteditable="true"></span>
								</p>
							</td>
						</tr>
					</table>
				</div>

				<div class="pl7 m30b">
					<p>经双方协商一致，按下述条款和条件, 卖方同意售与买方, 买方同意购自卖方, 下述商品 :</p>
					<p>Seller agrees to sell to Buyer and Buyer agrees to buy from
						Seller the undermentioned commodity pursuant to the</p>
					<p>terms and conditions as follows after negotiation between
						the parties:</p>
				</div>

				<div class="m60b tc tl">
					<table cellpadding="0" cellspacing="0" border="1"
						style="width: 90%; margin-bottom: 20px; text-align:center">
						<tr>
							<td rowspan="2"><p>序号</p></td>
							<td><p>商品名称,规格</p></td>
							<td><p>商品型号</p></td>
							<td><p>单位</p></td>
							<td><p>数量</p></td>
							<td><p>单价(美元)</p></td>
							<td><p>总价(美元)</p></td>
							<td rowspan="2"><p>Operate</p></td>
						</tr>
						<tr class="pdf_five_tab">
							<td><p>Name,specifications</p></td>
							<td><p>Model No.</p></td>
							<td><p>Unit</p></td>
							<td><p>Quantity</p></td>
							<td><p>Unit Price(USD)</p></td>
							<td><p>Total Price(USD)</p></td>
						</tr>

						<tr class="pdf_five_tab1">
							<td colspan="3">
								<p>
									<span>总值(大写)</span> <span class="TotalPriceBig"
										contenteditable="true"></span>
								</p>
							</td>
							<td colspan="5">
								<p>
									<span>Total CIP&nbsp;</span> <span class="AirPort"
										contenteditable="true"></span> <span>&nbsp; USD</span> <span
										class="TotalPrice" contenteditable="true"></span>
								</p>
							</td>
						</tr>

					</table>
					<div class="tl pl7">
						<input type="button" value="添加" class="bToggle" id="Contract_ADD"
							style="width: 60px; height: 30px; font-size: 19px;">
					</div>
				</div>

				<div class="m60b tl">
					<table cellpadding="0" cellspacing="0" border="1"
						style="width: 90%; margin-bottom: 20px;">
						<tr style="width: 5%;">
							<td class="tc"><p>2.</p></td>
							<td style="width: 35%;">
								<p>
									装运口岸:<span class="Shipment" contenteditable="true"></span>
								</p>
								<p>
									Port of Shipment:<span class="Shipment" contenteditable="true"></span>
								</p>
							</td>
							<td style="width: 5%;" class="tc"><p>3.</p></td>
							<td style="width: 55%;">
								<p>
									目的口岸:<span class="Destination" contenteditable="true"></span>
								</p>
								<p>
									Port of Destination:<span class="Destination"
										contenteditable="true"></span>
								</p>
							</td>
						</tr>

						<tr>
							<td class="tc"><p>4.</p></td>
							<td>
								<p>
									<span class="lf">装运期限:</span> <span class="ShipmentTime lf"
										contenteditable="true"
										style="border-bottom: 1px solid #000; width: 150px;"></span>。
									<span class="cl lf">卖方会在合同规定的货期内争取尽快发货，提前货期；若由于买方原因不能提前收货，请及时告知卖方。</span>
								</p>
								<p>
									<span class="lf">Time of Shipment:</span> <span
										class="ShipmentTime lf" contenteditable="true"
										style="border-bottom: 1px solid #000; width: 150px;"></span>。
									<span class="cl lf">The seller will strive for delivery
										ASAP, if buyer is not convenient to receive the goods in
										advance, please inform the seller timely.</span>
								</p>
							</td>
							<td class="tc"><p>5.</p></td>
							<td>
								<p>装运标记: 使用合同名字/xx, China</p>
								<p>
									Shipping Mark:<span class="ShippingMark tdline"
										contenteditable="true">HK-2017042801</span>
								</p>
								<p style="padding-left: 52px;">
									AirPort:<span class="AirPort" contenteditable="true">XXXAirport</span>
								</p>
							</td>
						</tr>

						<tr>
							<td class="tc"><p>6.</p></td>
							<td>
								<p>
									生产国别及制造厂商：<span class="Manufacturer" contenteditable="true"></span>
								</p>
								<p>
									Country of Origin & Manufacturer:<span class="Manufacturer"
										contenteditable="true"></span>
								</p>
							</td>
							<td class="tc"><p>7.</p></td>
							<td>
								<p>
									付款条件:<span class="Payment" contenteditable="true"></span>
								</p>
								<p>
									Payment Terms:<span class="Payment2" contenteditable="true"></span>&nbsp;within&nbsp;<span class="PayTime" contenteditable="true" style="border-bottom: 1px solid #000; width: 50px;display:inline-block;text-align:center"></span>&nbsp;week after contract signed
								</p>
							</td>
						</tr>
					</table>
				</div>

				<div class="content_text m30b pl7">
					<p>8.
						包装和运输标志：使用全新坚固、适宜长途海运/打包邮寄/空运、陆运和天气变化之木箱/纸箱包装，该包装必须能够经受防潮、防雨、防腐、防冻、防震、防漏。卖方必须对任何由于包装不妥造成货物的损坏、损失、费用支出及因未采取适当保护性措施所至的锈蚀负责并承担一切费用。包装中应包含全套的货物使用说明和服务指南。卖方须在包装上唛头。
					</p>
					<p>Packing and Shipping Mark: To be packed in new strong wooden
						case(s) or in carton(s), suitable for long distance ocean/parcel
						post/air and inland transportation, and change of climate, well
						protected against moisture, rain, corrosion, freeze and shocks.
						The Sellers shall be liable for any damage and loss of the
						commodity and expenses incurred on account of improper packing and
						for any rust attributable to inadequate or improper protective
						measures taken by the Sellers in regard to the packing.One full
						set of service and operation instructions concerned shall be
						enclosed in the case(s). The Sellers shall mark the shipping mark.
					</p>
				</div>
				<div class="content_text m30b pl7">
					<p>9. 单据：</p>
					<p>Documents:</p>
					<p>(1)
						卖方所提供的所有单据，包括随货所附的所有单据其中的合同编号、货物的品名、规格、型号、数量、价值（包括价值的标注方式）必须与合同完全一致。</p>
					<p>All the documents furnished by the Seller including contract
						numbers, names, specifications, quantities, and value (including
						the quotations of the values) of the goods in all the documents
						enclosed under the goods shall be in strict accordance with the
						Contract.</p>
					<p>(2) 空运：</p>
					<p>In case of air-freight:</p>
					<p>a. 一套正本空运提单</p>
					<p>Air waybill in 1 set origina.</p>
					<p>b. 100％的商业发票，一套正本，三套副本。注明合同号和装运唛头</p>
					<p>Commercial invoice covering 100% amount of the contract
						value in 1 set original and 3 sets copy, indicating contract
						number and shipping mark.</p>
					<p>c. 装箱单据一式三份，一份正本，二份副本。注明毛重、净重和尺寸</p>
					<p>Packing List, indicating gross weight, net weight and
						measurements in 1 set original and two sets copy</p>
					<p>d. 制造商出具的品质和数量证明正本一份。</p>
					<p>Certificate of Quality and Quantity issued by the
						manufacturer in 1 set original</p>
					<p>e. 对于CIF或CIP条款，全套保单，110%合同金额，涵盖所有运输风险和战争险</p>
					<p>In case of CIF/CIP, Full Set of Insurance Policy, covering
						Air Transportation 110% of invoice value against all risks and war
						risks.</p>
					<p>f. 由制造商出具的产地证明正本一份。</p>
					<p>Certificate of Origin issued by the manufacturer in 1 set
						original.</p>
					<p>g.
						针对木质包装，由卖方或厂商出具的木质熏蒸证明，1份正本和1份复印件。卖方出具“在出口国家的权威检疫部门已经做过熏蒸,
						并在每件木质包装上都标有IPPC标志”的证明；若货物包装全部为非木质，卖方出具非木质包装声明，1份复印件。</p>
					<p>IPPC declaration in 1 set original and 1 set copy issued by
						the seller or manufactory. Seller's Certificate certifying that
						the fumigation treatment has been made by authoritative Inspection
						& Quarantine bureau in export country, and IPPC indicated
						evidently on each wooden package; “For Wooden Packing material or
						Wooden Pallet” and/or Declaration of packing material in 1 copy
						issued by the seller or manufactory “For Non-Wood Packing material
						or Non-Wood pallet”.</p>
					<p>h. 如对外开立的是信用证（L/C），所提交的单据按信用证规定的条款执行。</p>
					<p>If L/C is issued, the receipts must follow the orders of
						L/C.</p>
				</div>
				<div class="content_text m30b pl7">
					<p>10．装运通知：卖方完成装运货物后，须立即以传真或信函通知买方合同号、品名、空运主运单号或海运提单号、发票金额、毛重、船名及启航日期等。如有易燃和危险品，亦应详述。</p>
					<p>Shipping Advice: The Sellers shall, immediately upon the
						completion of the loading of the goods, advise by fax/letter the
						Buyers of the Contract No., commodity, master Airway Bill
						No./Ocean Bill No., invoiced value, gross weight, name of vessel
						and date of departure etc. The details of inflammable and
						dangerous goods, if any, shall be also indicated.</p>
				</div>
				<div class="content_text m30b pl7">
					<p>11．装机安排：货物到达最终用户端2周后，我司安排工程师的时间为客户进行安装调试服务。</p>
					<p>12．品质证明：卖方保证本合同项下之商品是由最好的材料及一流工艺所制造、崭新的、未经使用的商品，完全符合合同规定的质量和规格。质保期为货物验收之日起算12个月，耗材不在质保期内。</p>
					<p>Guarantee of Quality: The Sellers guarantee that the
						commodity hereof is made of the best materials with first class
						workmanship, brand new and unused, and complies in all respects
						with the quality and specifications stipulated in this Contract.
						The guarantee period shall be 12 months counting from the date of
						final acceptance of the goods.</p>
				</div>
				<div class="content_text m30b pl7">
					<p>13．品质,数量/重量的异议与索赔：货物抵达目的港90天内，如发现质量、规格、数量与合同的约定不一致，除保险公司或船主声明负责的以外，买方根据中国检验检疫局出具的检验证书有权要求换货或要求赔偿，所需费用（如检验费、退回次品运输替换货品的运费、保险费、仓储费、装卸费等）由卖方承担。卖方应保证商品质量。从货物抵达目的港之日起12个月以内，若由于质次、工艺差或使用次品原料而导致货物在运行过程中损坏，买方应立即书面通知卖方，并附上中国检验检疫局出具的检验证书。</p>
					<p>此检验证明即为索赔基础。根据买方的索赔要求，卖方须负责立即排除缺陷，全部或部分更换商品或根据缺陷情况对商品作降价处理。如卖方收到上述索赔后一个月仍未答复买方，即视为卖方已接受该索赔要求。</p>
					<p>Quality, Quantity Discrepancy and Claim: Within 90 days
						after the arrival of the goods at destination, should the quality,
						specification, or quantity be found not in conformity with the
						stipulations of the contract except those claims for which the
						insurance company or the owners of the vessel are liable, the
						Buyers shall, on the strength of the Inspection Certificate issued
						by China Entry-Exit Inspection And Quarantine Bureau(CIQ), have
						the right to claim for replacement with new goods, or for
						compensation, and all the directly expenses (such as inspection
						charges, freight for returning the goods and for sending the
						replacement, insurance premium, storage and loading and unloading
						charges etc.) shall be borne by the Sellers. As regards quality,
						the Seller shall guarantee that if within 12 months from the date
						of arrival of the goods at destination, damages occur in the
						course of operation by reason of inferior quality, bad workmanship
						or the use of inferior materials, the Buyer shall immediately
						notify the Seller in writing and put forward a claim supported by
						Inspection Certificate issued by State Administration for
						Entry-Exit Inspection And Quarantine of the People’s Republic of
						China.</p>
					<p>The Certificate so issued shall be accepted as the base of a
						claim. The Seller, in accordance with the Buyer’s claim shall be
						responsible for the immediate elimination of the defect(s),
						complete or partial replacement of the commodity or shall
						devaluate the commodity according to the state of defect(s). The
						Buyer within one month after receipt of the aforesaid claim, the
						claim shall be reckoned as having been accepted by the Seller.</p>
				</div>
				<div class="content_text m30b pl7">
					<p>14．不可抗力：由于水灾、火灾、地震、干旱、战争或协议一方无法预见、控制、避免和克服的其他事件导致不能或暂时不能全部或部分履行本协议，该方不负责任。但是，受不可抗力事件影响的一方须尽快将发生的事件通知另一方，并在不可抗力事件发生15天内将有关机构出具的不可抗力事件的证明寄交对方。</p>
					<p>Force Majeure: Either party shall not be held responsible
						for failure or delay to perform all or any part of this agreement
						due to flood, fire, earthquake, draught, war or any other events
						which could not be predicted, controlled, avoided or overcome by
						the relative party. However, the party affected by the event of
						Force Majeure shall inform the other party of its occurrence in
						writing as soon as possible and thereafter send a certificate of
						the event issued by the relevant authorities to the other party
						within 15 days after its occurrence.</p>
				</div>
				<div class="content_text m30b pl7">
					<p>15．迟延交货及惩罚：</p>
					<p>(1)如果卖方没有按照合同规定的日期交货，除本合同第十三条所规定的不可抗力之外，如果卖方愿意支付罚金（由偿付行从货款中扣除），则买方应同意延期出运。罚金不超过迟延出运货物总价的5%。罚金比率为每七天0.5%，不足七天以七天计。如果卖方比合同规定的装船时间迟延十周，则买方有权解除合同。</p>
					<p>Late delivery and penalty: Should the Sellers fail to make
						delivery on time as stipulated in the Contract, with exception of
						Force Majeure causes specified in Clause 13 of this Contract, the
						Buyers shall agree to postpone the delivery on condition that the
						Sellers agree to pay a penalty which shall be deducted by the
						paying bank from the payment. The penalty, however, shall not
						exceed 5% of the total value of the goods involved in the late
						delivery. The rate of penalty is charged at 0.5% for every seven
						days, odd days less than seven days should be counted as seven
						days. In case the Sellers fail to make delivery ten weeks later
						than the time of shipment stipulated in the Contract, the Buyers
						shall have the right to cancel the contract and the Sellers.</p>
					<p>(2) 由于出口国政府或相关企业的审查，对货物交期造成影响，或造成货物扣押的情况，卖方不承担相关责任。</p>
					<p>Due to the examining of the exporting country government or
						related companies, the impact on the goods delivery, or cause the
						goods seized, seller assumes no responsibility.</p>
					<p>16．一致性：卖方在本合同项下开具的发票、装箱单和其他单据必须与本合同严格一致。卖方在本合同项下交付商品的规格、技术指标、数量和价格等也必须与合同、发票和装箱单严格一致。如卖方违反本规定，由此而产生的费用由卖方承担。</p>
					<p>Consistency: The Invoice, Packing List and other documents
						issued by the Seller under this contract must be strictly in
						conformity with the contract. Also, the commodities delivered by
						the Sellers under this contract, must be strictly in conformity
						with the contract Invoice and Packing List in terms of
						specification, technical indices, quantities and price. Should the
						Sellers fail to abide by this stipulation, the Sellers shall take
						their own responsibility for the related expense consequences
						arising thereof.</p>
					<p>17．违约责任：双方均应适当履行本合同项下的义务，如有违反，均视为违约，守约方可以向违约方要求支付违约金，并要求赔偿由此给守约方造成的损失。</p>
					<p>Liability for Breach of Contract: Both parties shall perform
						the duty of this agreement. If either party violate this
						agreement, it will be as default. The default party must pay the
						liquidated damages to the observant?party. And they must be
						responsible for the damage of the observant?party.</p>
					<p>18．税费:</p>
					<p>Taxes and Duties:</p>
					<p>18.1 卖方负责支付所有中国境外因执行本合同所产生的全部税费。</p>
					<p>All taxes arising outside of China in connection with the
						execution of this Contract shall be borne by the Sellers.</p>
					<p>17.2 由中华人民共和国政府根据现行税法向买方征收的与执行本合同相关的全部税费由买方承担。</p>
					<p>All taxes in connection with, and in the execution of this
						Contract to be levied by the Government of the P.R.C. on the
						Buyers in accordance with the tax laws in effect shall be borne by
						the Buyers.</p>
				</div>
				<div class="content_text m30b pl7">
					<p>19.
						仲裁：在履行协议过程中，如产生争议，双方应友好协商解决。若通过友好协商未能达成协议，则提交中国国际贸易促进委员会对外贸易仲裁委员会，根据该会仲裁程序暂行规定进行仲裁。该委员会决定是终局的，对双方均有约束力。仲裁费用，除另有规定外，由败诉一方负担。</p>
					<p>Arbitration：All disputes arising from the execution of this
						agreement shall be settled through friendly consultations. In case
						no settlement can be reached, the case in dispute shall then be
						submitted to the Foreign Trade Arbitration Commission of the China
						Council for the Promotion of International Trade for Arbitration
						in accordance with its Provisional Rules of Procedure. The
						decision made by this commission shall be regarded as final and
						binding upon both parties. Arbitration fees shall be borne by the
						losing party, unless otherwise awarded.</p>
				</div>
				<div class="content_text m30b pl7">
					<p>20．特殊规定：</p>
					<p>Special Provisions:</p>
					<p>本合同一式四份，买方持三份，卖方持一份。</p>
					<p>In witness thereof, this Contract is made out in four
						originals: the buyer keeps 3 originals and the seller keeps 1
						original after the signing of the contract.</p>
					<p>买方购货定单或承诺中的或任何其它文件中不同的或额外的条款或条件均不应对本合同有任何影响。只有上述或双方书面同意的条款、条件和保证适用于本合同。本合同和所有的附件构成完整的协议，任何对本协议的修改都应是书面的，而且应有双方授权代表的签字。</p>
					<p>No different or additional term or condition embodied in
						Buyer's purchase order or acceptance, or any other document shall
						have any effect to this Contract. The only terms, conditions and
						warranties applicable hereto are those as set out above or agreed
						to in writing by both parties. This contract and all appendixes
						constitute the entire agreement; any amendment to this agreement
						shall be in writing, signed by the duly authorized representatives
						of each of the parties.</p>
					<p>协议的任何一方违约或不履行此协议的实质性条款.条件或约定，则另一方有权自行暂时中止执行直至违约方不再违约或不履行义务。签约双方将通过所有合理途径予以合作，促使该违约或不履行能得以补救。</p>
					<p>In the event either party is in breach or default of any of
						the material terms, conditions or covenants of this agreement,
						then the other party shall have the right to, at his discretion,
						suspend the execution until such moment the breaching party is no
						longer in breach or default. Both parties shall cooperate in every
						reasonable way to facilitate the remedy of a breach or default.</p>
					<p>本合同用中文写成，译成英文。若有差异，以中文版本为准。</p>
					<p>This Contract is written in Chinese and translated into
						English. In case of any discrepancy, Chinese version shall
						control.</p>
				</div>
				<div class="m60b tl">
					<table cellpadding="0" cellspacing="0" border="1"
						style="width: 90%; margin-bottom: 20px;" class="pdf_two_tab">
						<tr>
							<td class="tc">
								<p>卖方</p>
								<p>Seller</p>
							</td>
							<td colspan="2">
								<p>For and on behalf of:</p>
								<p>HK EOULU TRADING LIMITED</p>
							</td>
							<td class="tc">
								<p>买方</p>
								<p>Buyer</p>
							</td>
							<td colspan="2">
								<p>For and on behalf of:</p>
								<p></p>
							</td>
						</tr>
						<tr>
							<td style="width: 5%;" class="tc"><p>签字</p></td>
							<td style="width: 10%;"><p>Signature :</p></td>
							<td style="width: 25%;"><p></p></td>
							<td style="width: 5%;" class="tc"><p>签字</p></td>
							<td style="width: 10%;"><p>Signature :</p></td>
							<td style="width: 35%;"><p></p></td>
						</tr>
						<tr>
							<td class="tc"><p>姓名</p></td>
							<td><p>Name :</p></td>
							<td><p></p></td>
							<td class="tc"><p>姓名</p></td>
							<td><p>Name :</p></td>
							<td><p></p></td>
						</tr>
						<tr>
							<td class="tc"><p>公章</p></td>
							<td><p>Seal:</p></td>
							<td><p></p></td>
							<td class="tc"><p>公章</p></td>
							<td><p>Seal:</p></td>
							<td><p></p></td>
						</tr>
						<tr>
							<td class="tc">
								<p>银行</p>
								<p>信息</p>
							</td>
							<td colspan="2">
								<p>Name：HK EOULU TRADING LIMITED</p>
								<p>Bank：香港汇丰银行/HSBC</p>
								<p>Acc. No.: 801-012469-838</p>
								<p>SWIFT Code: HSBCHKHHHKH</p>
							</td>
							<td></td>
							<td colspan="2"></td>
						</tr>

					</table>
				</div>

			</div>

			<!-- 页脚 -->
			<div class="yejiao cf" style="margin-top: 20px; height: 80px;">

			</div>
		</div>
		<!-------------合同RMB版---------------------->
		<div id="view6" class="news" style="display:none;">
			<!-- 页眉-->
			<div class="yemei" style="margin-top: 20px; " >
			</div>

			<!-- 合同页面中文版 -->
			<div id="ContractChina" class="s14">
				<div class="">
					<p class="rt f20 m30 m30b">
						<span>合同编号：</span><span class="ContractNO" contenteditable="true">SUZ-20140929S1</span>
					</p>
					<p class="cf tc f24 m20b">苏州伊欧陆系统集成有限公司合同</p>
					<p class="SignDate f16 m30b" style="padding-left: 30%">签订日期：
								<span contenteditable="true" class="SignDateYear" style="min-width: 20px;display: inline-block;"></span>年
								<span contenteditable="true" class="SignDateMonth" style="min-width: 20px;display: inline-block;"></span>月
								<span contenteditable="true" class="SignDateDay" style="min-width: 20px;display: inline-block;"></span>日
					</p>
					<div class="lf lines pl7" style="line-height: 30px;">
						<p>
							<span>甲&nbsp;方：</span><span class="CustomerCompany line"
								contenteditable="true"></span>
						</p>
						<p>
							<span>电&nbsp;话：</span><span class="CustomerTel line"
								contenteditable="true"></span>
						</p>
						<p>
							<span>传&nbsp;真：</span><span class="CustomerFax line"
								contenteditable="true"></span>
						</p>
						<p>
							<span>联系人：</span><span class="CustomerContact line"
								contenteditable="true"></span>
						</p>
					</div>
					<div class=" rt m30b lines"
						style="line-height: 30px; padding-right: 100px;">
						<p>
							<span>乙&nbsp;方：</span><span class="line">苏州伊欧陆系统集成有限公司</span>
						</p>
						<p>
							<span>电&nbsp;话：</span><span class="line">86-512-62757360</span>
						</p>
						<p>
							<span>传&nbsp;真：</span><span class="line">86-512-62757313</span>
						</p>
						<p>
							<span>联系人：</span><span class="SecondContact line"
								contenteditable="true"></span>
						</p>
					</div>
					<div class="cf lf pl7" style="line-height: 30px;">
						<p>甲乙双方经协商同意，按下述条款买卖下述产品：</p>
						<p>一、合同标的及价格：</p>
						<p>为满足项目所需，甲方同意从乙方购买，乙方同意向甲方提供下表所列的货物：</p>
					</div>
				</div>

				<div class="m30b cf">
					<table cellpadding="0" cellspacing="0" border="1"
						style="width: 90%; margin-bottom: 20px;">
						<tr class="tc pdf_six_tab">
							<td>编号</td>
							<td>产品型号</td>
							<td>描述</td>
							<td style="display: none;">单位</td>
							<td>数量</td>
							<td>单价(RMB)</td>
							<td>总价(RMB)</td>
							<td><p>Operate</p></td>
						</tr>
						<tr class="pdf_six_tab1">
							<td colspan="4">
								<p style="text-align: right;">含税总价(人民币):&nbsp;&nbsp;&nbsp;</p>
							</td>
							<td colspan="4">
								<p class="TotalPrice" contenteditable="true"></p>
							</td>
						</tr>
					</table>
					<div class="tl pl7">
						<input type="button" value="添加" class="bToggle" id="Contract_ADD1"
							style="width: 60px; height: 30px; font-size: 19px;">
					</div>
				</div>

				<div class="content_text m30b pl7">
					<p>
						<span class="lf">所涉及的合同总价（含税）为：人民币:&nbsp;</span><span
							class="TotalPrice line" contenteditable="true"
							style="width: auto;"></span><span class="lf">；大写金额：</span><span
							contenteditable="true" class="line TotalPriceBig"
							style="width: auto;"></span>。
					</p>
					<p class="cl">二、付款方式、供货和交货及发票：</p>
					<p>
						2.1、付款方式：合同生效3个工作日内甲方向乙方支付本合同项下<span class="tdline">100%</span>的预付款<span
							class="TotalPrice tdline" contenteditable="true"></span>元，即人民币<span
							class=" tdline TotalPriceBig" contenteditable="true"></span>；
					</p>
					<p>2.2、付款要求：甲方应使用电汇或转账支票等方式支付合同款项给乙方。</p>
					<p>乙方收款账户信息如下：</p>
					<p>
						公司名称：<span class="tdline">苏州伊欧陆系统集成有限公司 </span>；
					</p>
					<p>
						开户银行：<span class="tdline">中国建设银行股份有限公司苏州独墅湖高教区支行</span>；
					</p>
					<p>
						银行账号：<span class="tdline">32201988852052500168</span> ；
					</p>
					<p>2.3、供货：</p>
					<p>
						供货周期：乙方收到本合同预付款后<span class="LeadTime tdline">12</span>周给甲方发货。乙方会在合同规定的货期内争取尽快发货，提前货期；若由于甲方原因不能提前收货，请及时告知乙方；
					</p>
					<p>
						<span class="lf">收货联系人：</span><span class="CustomerContact line"
							contenteditable="true"></span><span class="lf">(电话：</span><span
							class="CustomerTel line" contenteditable="true"></span>)
					</p>
					<P class="cf">
						<span class="lf">交货地点：</span><span class="DeliveryPoint line"
							contenteditable="true"></span>
					</P>
					<p class="cf">2.4、质保期：到货验收后12个月，耗材除外。</p>
					<p>2.5、发票：乙方收到全款后给甲方提供17%税点的增值税专用发票。</p>
					<pre style="font-family: initial; font-size: 14px;">三、合同生效和合同保密：
3.1 本合同一式贰份，签字盖章后即时生效（包括传真签合同有效），双方各执壹份。
3.2 买卖双方必须对本合同的条款向任何第三方保密。

四、不可抗力：
4.1、合同生效后，如果乙方遇到人力不可抗拒事件，包括但不限于火灾、水灾、地震、台风、自然灾害以及由于原厂商在生产、装卸、运输过程中可能发生延迟，致使其向乙方交货延期或不能交货时，乙方应立即以邮件、电话或传真的方式通知甲方，本合同规定的乙方履约时间应自动延长。其延长的时间应相当于因人力不可抗拒事件直接或间接地使乙方不能履行本合同的时间。合同双方同意在上述情形为双方无法预见、无法避免、不可控制的事件。有责任提供不可抗拒事件发生证明的一方应在合理的时间内，用邮件或电传等形式将有关证明提供给对方。如果不履约的情况延续达15天以上，双方应立即协商修改合同。若从不可抗拒事件的发生之日起三十天内双方当事人未能取得双方满意的办法时，任何一方都可以终止履行本合同的未执行部分。

五、违约责任：
5.1、双方均应适当履行本合同项下的义务，如有违反，均视为违约，守约方可以向违约方要求支付违约金，并要求赔偿由此给守约方造成的损失。
5.2、乙方在规定期限内不能按时交货的，则每迟延一天，乙方向甲方支付延期交付货物总金额0.1%的违约金，但不超过合同金额的百分之五。如迟延交货超过10天，甲方有权解除合同且不免除乙方支付违约金的责任。
5.3、如甲方在规定期限内不能按时付款，则每迟延一天，甲方向乙方支付延期款项总金额0.1%的违约金，但不超过合同金额的百分之五。
5.4、如果一方违反本协议约定的保密事项，除立即采取有效措施予以改正外，还须向另一方支付合同总金额的10%违约金。
5.5、由于出口国政府或相关企业的审查，对货物交期造成影响，或造成货物扣押的情况，乙方不承担相关责任

六、争议和仲裁：
6.1、因解释﹑执行本合同所发生的和本合同有关的一切争议，双方应首先友好协商解决，如协商不成，双方将本合同争议提交中国国际经济贸易仲裁委员会进行仲裁，仲裁地点在深圳。
6.2、仲裁裁定是终局的对双方均有约束力。任何一方不得向法院或其他机构申请改变仲裁裁定。
6.3、仲裁费用由败方承担。
6.4、仲裁进行过程中，双方将继续执行合同，但仲裁部分除外。
				
				        </pre>
				</div>

				<div class="lf pl7" style="line-height: 40px;">
					<p contenteditable="true">甲方：</p>
					<p contenteditable="true">单位名称（章）</p>
					<p contenteditable="true">法定代表人：</p>
					<p>（或）授权代表：</p>
					<p contenteditable="true">日期： 年 月 日</p>
				</div>
				<div class="rt" style="line-height: 40px; padding-right: 10%">
					<p>
						乙方：<span class="tdline">苏州伊欧陆系统集成有限公司</span>
					</p>
					<p contenteditable="true">单位名称（章）</p>
					<p contenteditable="true">法定代表人：</p>
					<p>（或）授权代表：</p>
					<p contenteditable="true">日期： 年 月 日</p>
					<p>(本合同到此完)</p>
				</div>

			</div>

			<!-- 页脚 -->
			<div class="yejiao cf" style="margin-top: 20px; height: 80px;">

			</div>
		</div>
		
		<div class="Sidebar_Contract">
			<div class="USDContract isHover" id="USDContract">USD合同</div>
			<div class="RMBContract" id="RMBContract">RMB合同</div>
		</div>
		
		<input type="button" value="关闭" class="bToggle " id="contract_close5" style="position: absolute; z-index: 11; top: 100px; left: 75%; width: 92px; height: 30px; font-size: 19px;">
		<input type="button" value="提交" class="bToggle" id="submit_n5" style="position: absolute; z-index: 11; top: 250px; left: 75%; width: 92px; height: 30px; font-size: 19px;">
		<input type="button" value="导出Word" class="bToggle" id="exportWord5" style="position: absolute; z-index: 11; top: 150px; left: 75%; width: 92px; height: 30px; font-size: 19px;">
		<input type="button" value="导出PDF" class="bToggle" id="exportPDF5" style="position: absolute; z-index: 11; top: 200px; left: 75%; width: 92px; height: 30px; font-size: 19px;">
	</div>
	
	<!--     备货清单版all -->
	<div class="hidePdf7" style="display: none;">
		<div id="view7" class="news">
			<!-- 页眉-->
			<div class="yemei" style="margin-top: 20px;">
			</div>
			<!-- 备货清单页面 -->

			<div id="StockList" class="s14 tc">
				<!-- 发货清单 -->
				<div class="lf m30 m30b" style="padding-left: 10%; font-size: 29px;">
					<p>发货清单</p>
				</div>
				<div class="m30b StockList">
					<table cellpadding="0" cellspacing="0" border="1"
						style="width: 90%; margin-bottom: 20px;" class="pdf_seven_tab">
						<tr>
							<td colspan="6" contenteditable="true" class="Name">发货清单</td>
						</tr>
						<tr>
							<td style="width: 5%;">序号</td>
							<td style="width: 10%;">型号</td>
							<td style="width: 45%;">描述</td>
							<td style="width: 5%;">数量</td>
							<td style="width: 25%;">备注</td>
							<td style="width: 10%">删除</td>
						</tr>
					</table>
					<div class="tl pl7">
						<input type="button" value="添加" class="bToggle" id="send_ADD"
							style="width: 60px; height: 30px; font-size: 19px;">
					</div>
				</div>

				<!-- 包装要求 -->
				<div class="lf m30 m30b" style="padding-left: 10%; font-size: 29px;">
					<p>包装要求</p>
				</div>
				<div class="m30b tab1">
					<table cellpadding="0" cellspacing="0" border="1"
						style="width: 90%; margin-bottom: 20px;">
						<tr>
							<td colspan="4">包装要求</td>
						</tr>
						<tr>
							<td style="width: 10%;">序号</td>
							<td style="width: 40%;">合同信息</td>
							<td style="width: 25%;">要求</td>
							<td style="width: 25%;">选择</td>
						</tr>
						<tr>
							<td>1</td>
							<td rowspan="9">
								<p>
									<span>Contract No.:</span><span class="ContractNO" contenteditable="true"></span>
								</p>
								<p>
									<span>PO:</span><span class="PO" contenteditable="true"></span>
								</p>
								<p>
									<span>SO:</span><span class="SO" contenteditable="true"></span>
								</p>
							</td>
							<td>熏蒸木箱</td>
							<td class="Fumigation">
								<p>
									<label> <input type="radio" name="Fumigation" value="是">是
										<input type="radio" name="Fumigation" value="否">否
									</label>
								</p>
							</td>
						</tr>
						<tr>
							<td>2</td>
							<td>尺寸</td>
							<td class="Size">
								<p>
									<label> <input type="radio" name="Size" value="是">是
										<input type="radio" name="Size" value="否">否
									</label>
								</p>
							</td>
						</tr>
						<tr>
							<td>3</td>
							<td>重量</td>
							<td class="Weight">
								<p>
									<label> <input type="radio" name="Weight" class="yes"
										value="是">是 <input type="radio" name="Weight"
										class="no" value="否">否
									</label>
								</p>
							</td>
						</tr>
						<tr>
							<td>4</td>
							<td>产品图片</td>
							<td class="ProductImg">
								<p>
									<label> <input type="radio" name="ProductImg" value="是">是
										<input type="radio" name="ProductImg" value="否">否
									</label>
								</p>
							</td>
						</tr>
						<tr>
							<td>5</td>
							<td>铭牌图片</td>
							<td class="NamePlateImg">
								<p>
									<label> <input type="radio" name="NamePlateImg"
										value="是">是 <input type="radio" name="NamePlateImg"
										value="否">否
									</label>
								</p>
							</td>
						</tr>
						<tr>
							<td>6</td>
							<td>原产地信息</td>
							<td class="OriginInfo">
								<p>
									<label> <input type="radio" name="OriginInfo" value="是">是
										<input type="radio" name="OriginInfo" value="否">否
									</label>
								</p>
							</td>
						</tr>
						<tr>
							<td>7</td>
							<td>产品品牌</td>
							<td class="ProductName">
								<p>
									<label> <input type="radio" name="ProductName"
										value="是">是 <input type="radio" name="ProductName"
										value="否">否
									</label>
								</p>
							</td>
						</tr>
						<tr>
							<td>8</td>
							<td>包装箱数量</td>
							<td class="PackingQty">
								<p>
									<label> <input type="radio" name="PackingQty" value="是">是
										<input type="radio" name="PackingQty" value="否">否
									</label>
								</p>
							</td>
						</tr>
						<tr>
							<td>9</td>
							<td>唛头</td>
							<td class="ShippingMark">
								<p>
									<label> <input type="radio" name="ShippingMark"
										value="是">是 <input type="radio" name="ShippingMark"
										value="否">否
									</label>
								</p>
							</td>
						</tr>
						<tr class="tl">
							<td colspan="4">
								<p>备注：1、如需要物流部门提供以上信息，请进行勾选；</p>
								<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、如未勾选，物流部门将根据默认要求安排包装；</p>
								<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、PO号和SO号由物流部门填写，其他请商务完善。</p>
							</td>
						</tr>
					</table>
				</div>

				<!-- 运输要求 -->
				<div class="lf m30 m30b" style="padding-left: 10%; font-size: 29px;">
					<p>运输要求</p>
				</div>
				<div class="m30b tab2">
					<table cellpadding="0" cellspacing="0" border="1"
						style="width: 90%; margin-bottom: 20px;">
						<tr>
							<td colspan="5">运输要求</td>
						</tr>
						<tr>
							<td style="width: 10%;">序号</td>
							<td style="width: 40%;">合同信息</td>
							<td colspan="2" style="width: 25%;">选项</td>
							<td style="width: 25%;">信息</td>
						</tr>
						<tr>
							<td>1</td>
							<td rowspan="9">
								<p>
									<span>Contract No.:</span><span class="ContractNO" contenteditable="true"></span>
								</p>
								<p>
									<span>PO:</span><span class="PO" contenteditable="true"></span>
								</p>
								<p>
									<span>SO:</span><span class="SO" contenteditable="true"></span>
								</p>
							</td>
							<td rowspan="4">发货信息</td>
							<td>起运港</td>
							<td class="Departure" contenteditable="true"></td>
						</tr>
						<tr>
							<td>2</td>
							<td>目的港</td>
							<td class="Destination" contenteditable="true"></td>
						</tr>
						<tr>
							<td>3</td>
							<td>收货地址</td>
							<td class="Receiving" contenteditable="true"></td>
						</tr>
						<tr>
							<td>4</td>
							<td>是否接受分批发货</td>
							<td class="SplitShipment">
								<p>
									<label> <input type="radio" name="SplitShipment"
										value="是">是 <input type="radio" name="SplitShipment"
										value="否">否
									</label>
								</p>
							</td>
						</tr>
						<tr>
							<td>5</td>
							<td rowspan="3">运输方式</td>
							<td>空运</td>
							<td class="Airelift">
								<p>
									<label> <input type="radio" name="Airelift" value="是">是
										<input type="radio" name="Airelift" value="否">否
									</label>
								</p>
							</td>
						</tr>
						<tr>
							<td>6</td>
							<td>中港卡车</td>
							<td class="Truck">
								<p>
									<label> <input type="radio" name="Truck" value="是">是
										<input type="radio" name="Truck" value="否">否
									</label>
								</p>
							</td>
						</tr>
						<tr>
							<td>7</td>
							<td>快递</td>
							<td class="FastMail">
								<p>
									<label> <input type="radio" name="FastMail" value="是">是
										<input type="radio" name="FastMail" value="否">否
									</label>
								</p>
							</td>
						</tr>
						<tr>
							<td>8</td>
							<td rowspan="2">送货方式</td>
							<td>是否需要尾板车派送</td>
							<td class="TailCar">
								<p>
									<label> <input type="radio" name="TailCar" value="是">是
										<input type="radio" name="TailCar" value="否">否
									</label>
								</p>
							</td>
						</tr>
						<tr>
							<td>9</td>
							<td>是否需要卸货工具</td>
							<td class="Unloading">
								<p>
									<label> <input type="radio" name="Unloading" value="是">是
										<input type="radio" name="Unloading" value="否">否
									</label>
								</p>
							</td>
						</tr>
						<tr class="tl">
							<td colspan="5">
								<p>备注：1、空运，中港卡车，快递三者选其一；</p>
								<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、大型货物（超过100公斤）货物需要确认是否需要尾板车派送；</p>
								<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、PO号和SO号由物流部门填写，其他请商务完善。</p>
							</td>
						</tr>

					</table>
				</div>

			</div>

			<!-- 页脚 -->
			<div class="yejiao cf" style="margin-top: 20px; height: 80px;">

			</div>
		</div>
		<input type="button" value="关闭" class="bToggle " id="contract_close7" style="position: absolute; z-index: 11; top: 100px; left: 70%; width: 92px; height: 30px; font-size: 19px;">
		<input type="button" value="提交" class="bToggle" id="submit_n7" style="position: absolute; z-index: 11; top: 150px; left: 70%; width: 92px; height: 30px; font-size: 19px;">
		<input type="button" value="下载" class="bToggle" id="download7" style="position: absolute; z-index: 11; top: 200px; left: 70%; width: 92px; height: 30px; font-size: 19px;">
		<input type="button" value="备货" class="bToggle" id="Stocking" style="position: absolute; z-index: 11; top: 250px; left: 70%; width: 92px; height: 30px; font-size: 19px;">
		
		<input type="button" value="导出PDF" class="bToggle" id="exportPDF7" style="position: absolute; z-index: 11; top: 250px; left: 70%; width: 92px; height: 30px; font-size: 19px; display: none;">
	</div>

	<!--    CascadePO  -->
	<div class="CascadePOPdf" style="display: none;">
		<!--    CascadePO模板  -->
		<div id="view8" class="news" style="display:none;">
			<!-- 页眉-->
			<div class="yemei" style="margin-top: 20px;">
				<div style="width: 100%; height: 70px;">
					<div class="logo rt">
						<!-- <img src="image/EOULUlogo.png" style="width: 175px; height: 61px;"> -->
					</div>
				</div>
				<hr>
			</div>
			<!--内容  -->
			<div id="CascadePOCon" class="s12">
				<div class="lf m30 m30b" style="padding-left: 10%; font-size: 29px;">
					<p>Purchase Order</p>
				</div>
				<div class=" cf rt m30b" style="padding-right: 15%;">
					<p>
						Number: <span contenteditable="true" class="Number CascadePOConNumber"
							style="display: inline-block; min-width: 40px;"></span>
					</p>
					<p>
						Ref.No: <span contenteditable="true" class="RefNO"
							style="display: inline-block; min-width: 40px;"></span>
					</p>
					<p>
						Versions:<span contenteditable="true" class="Version"
							style="display: inline-block; min-width: 40px;"></span>
					</p>
					<!-- <p><span>Number: </span><span contenteditable="true" class="Number red">HK-2017XXXXS1</span></p>
			        <p><span>Versions: </span><span contenteditable="true" class="Version red"></span></p> -->
				</div>

				<div class="m30b">
					<table cellpadding="0" cellspacing="0" border="1"
						style="width: 90%; margin-bottom: 20px;" class="hasSecondTd">
						<tr>
							<td><b>Bill To</b></br>
								<p>Wang Xiaoliang</p>
								<p>Email: wangxiaoliang@eoulu.com</p>
								<p>Room 1501, Grand Millennium Plaza (Lower Block), 181
									Queen's Road Central, HONG KONG</p>
								<p>TEL:00852-21527388</p></td>
							<td><b>Vendor</b></br>
								<p>FormFactor Beaverton,Inc.</p>
								<p>9100 SW Gemini Drive</p>
								<p>Beaverton, OR 97008 USA</p>
								<p>TEL:1-503-601-1000</p>
								<p>E-mail:ApBeaverton@formfactor.com</p></td>
						</tr>
						<tr>
							<td class="ForwarderTd"><b title="点击右边下拉选择" style="display: inline-block;">Forwarder</b><span class="View8ForwarderSel"><i></i></span></br>
								<p class="ForwarderOne" contenteditable="true"></p>
								<p class="ForwarderTwo" contenteditable="true"></p>
								<p class="ForwarderThree" contenteditable="true"></p>
								<p class="ForwarderFour" contenteditable="true"></p>
							</td>
							<td class="ShipToTd"><b title="点击右边下拉选择">Ship to</b><span class="View8ShipToSel"><i></i></span></br>
								<p>
									<span>Company:</span><span contenteditable="true"
										class="ShipCompany"></span>
								</p>
								<p>
									<span>Addr:</span><span contenteditable="true" class="ShipAddr"></span>
								</p>
								<p>
									<span>Tel:</span><span contenteditable="true" class="ShipTel"></span>
								</p>
								<p>
									<span>Attn:</span><span contenteditable="true" class="ShipAttn"></span>
								</p></td>
						</tr>
						<tr>
							<td><b>End User</b></br>
								<p>
									<span>Company:</span><span contenteditable="true"
										class="EndCompany"></span>
								</p>
								<p>
									<span>Addr:</span><span contenteditable="true" class="EndAddr"></span>
								</p>
								<p>
									<span>Contact Person:</span><span contenteditable="true"
										class="ContactPerson"></span>
								</p>
								<p>
									<span>Tel:</span><span contenteditable="true" class="EndTel"></span>
								</p></td>
							<td><b>Others</b></br>
								<p>
									<span>Credit Term:</span><span>NET 60 days</span>
								</p>
								<p>
									<span>Delivery Term: FCA Factory,</span><span
										contenteditable="true" class="DeliveryTerm"></span>
								</p>
								<p class="mh25"></p>
								<p class="mh25"></p></td>
						</tr>
						<tr>
							<td colspan="2"><b>Shipping Instruction</b></br>
								<p style="margin-left: 25px;">1 Please do not put the
									original invoice with goods</p>
								<p style="margin-left: 25px;">
									<span>2 SHIPPING MARK:</span><span contenteditable="true"
										class="ShippingMark"></span>
								</p>
								<p style="margin-left: 25px;">
									<span>3 Indicating CONTRACT NO.: </span><span
										contenteditable="true" class="ContractNO"></span>
								</p>
								<p style="margin-left: 25px;">
									<span>4 Port of shipment:</span><span contenteditable="true"
										class="ShipmentPort"></span>
								</p></td>
						</tr>
					</table>
					<div class="m60b tc ContractUSDItem">
						<table cellpadding="0" cellspacing="0" border="1"
							style="width: 90%; margin-bottom: 20px;">
							<tr style="background: #e4e8eb;" id="ItemTit">
								<td><p>Item</p></td>
								<td><p>Part</p></td>
								<td><p>Description</p></td>
								<td><p>Qty</p></td>
								<td><p>Unit Price</p></td>
								<td><p>Extended Price</p></td>
								<td><p>删除</p></td>
							</tr>
							<tr class="SubTotalCon">
								<td colspan="5"><p 
										style="text-align: right; margin-right: 10px;">Sub-total</p></td>
								<td colspan="2"><p class="SubTotal" contenteditable="true"></p></td>
							</tr>
							<tr class="DiscountedCon">
								<td colspan="5"><p
										style="text-align: right; margin-right: 10px;">Discounted
										x%</p></td>
								<td colspan="2"><span contenteditable="true"
									class="Discounted ConflgBlur"
									style="min-width: 20px; display: inline-block;"></span></td>
							</tr>
							<tr class="FinalTotalCon">
								<td colspan="5"><p 
										style="text-align: right; margin-right: 10px;">Final Total</p></td>
								<td colspan="2"><p class="FinalTotal" contenteditable="true"></p></td>
							</tr>
							<tr class="ContactCon">
								<td colspan="7">
									<p>
										<span>If you have any questions about your order,
											please contact:</span><span contenteditable="true" class="Contact"></span>
									</p>
								</td>
							</tr>
							<tr class="EmailCon">
								<td colspan="7">
									<p>
										<span> E-Mail: </span><span contenteditable="true"
											class="Email"></span>
									</p>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>

			<!-- 页脚 -->
			<div class="yejiao cf" style="margin-top: 20px; height: 80px;">
				<hr>
				<pre
					style="text-align: center; color: #000080; font-family: -webkit-body;">EOULU
			Suzhou ● Shenzhen ● Beijing ● Shanghai ● HongKong
			〡Phone: +86-512-62757360〡Web:www.eoulu.com〡Email:info@eoulu.com〡</pre>

			</div>
		</div>
	    <!--    CascadePO整机模板  -->
		<div id="view89" class="news" style="display:none;">
			<!-- 页眉-->
			<div class="yemei" style="margin-top: 20px;">
				<div style="width: 100%; height: 70px;">
					<div class="logo rt">
						<!-- <img src="image/EOULUlogo.png" style="width: 175px; height: 61px;"> -->
					</div>
				</div>
				<hr>
			</div>
			<!--内容  -->
			<div id="CascadePOCon" class="s12">
				<div class="lf m30 m30b" style="padding-left: 10%; font-size: 29px;">
					<p>Purchase Order</p>
				</div>
				<div class=" cf rt m30b" style="padding-right: 15%;">
					<p>
						Number: <span contenteditable="true" class="Number CascadePOConNumber"
							style="display: inline-block; min-width: 40px;"></span>
					</p>
					<!-- <p>
						Ref.No: <span contenteditable="true" class="RefNO"
							style="display: inline-block; min-width: 40px;"></span>
					</p> -->
					<p>
						Versions:<span contenteditable="true" class="Version"
							style="display: inline-block; min-width: 40px;"></span>
					</p>
					<!-- <p><span>Number: </span><span contenteditable="true" class="Number red">HK-2017XXXXS1</span></p>
			        <p><span>Versions: </span><span contenteditable="true" class="Version red"></span></p> -->
				</div>

				<div class="m30b">
					<table cellpadding="0" cellspacing="0" border="1"
						style="width: 90%; margin-bottom: 20px;" class="hasSecondTd">
						<tr>
							<td><b>Bill </b></br>
								<p>Wang Xiaoliang</p>
								<p>Email: wangxiaoliang@eoulu.com</p>
								<p>Room 1501, Grand Millennium Plaza (Lower Block), 181
									Queen's Road Central, HONG KONG</p>
								<p>TEL:00852-21527388</p></td>
							<td><b>Vendor</b></br>
								<p>FormFactor Beaverton,Inc.</p>
								<p>9100 SW Gemini Drive</p>
								<p>Beaverton, OR 97008 USA</p>
								<p>TEL:1-503-601-1000</p>
								<p>E-mail:ApBeaverton@formfactor.com</p></td>
						</tr>
						<tr>
							<td><b>Forwarder</b></br>
								<p class="ForwarderOne" contenteditable="true"></p>
								<p class="ForwarderTwo" contenteditable="true"></p>
								<p class="ForwarderThree" contenteditable="true"></p>
								<p class="ForwarderFour" contenteditable="true"></p></td>
							<td><b>Ship to</b></br>
								<p>
									<span>Company:</span><span contenteditable="true"
										class="ShipCompany"></span>
								</p>
								<p>
									<span>Addr:</span><span contenteditable="true" class="ShipAddr"></span>
								</p>
								<p>
									<span>Tel:</span><span contenteditable="true" class="ShipTel"></span>
								</p>
								<p>
									<span>Attn:</span><span contenteditable="true" class="ShipAttn"></span>
								</p></td>
						</tr>
						<tr>
							<td><b>End User</b></br>
								<p>
									<span>Company:</span><span contenteditable="true"
										class="EndCompany"></span>
								</p>
								<p>
									<span>Addr:</span><span contenteditable="true" class="EndAddr"></span>
								</p>
								<p>
									<span>Contact Person:</span><span contenteditable="true"
										class="ContactPerson"></span>
								</p>
								<p>
									<span>Tel:</span><span contenteditable="true" class="EndTel"></span>
								</p></td>
							<td><b>Others</b></br>
								<p>
									<span>Credit Term:</span><span>NET 60 days</span>
								</p>
								<p>
									<span>Delivery Term: FCA Factory,</span><span
										contenteditable="true" class="DeliveryTerm"></span>
								</p>
								<p class="mh25"></p>
								<p class="mh25"></p></td>
						</tr>
						<tr>
							<td colspan="2"><b>Shipping Instruction</b></br>
								<p style="margin-left: 25px;">1 Please do not put the
									original invoice with goods</p>
								<p style="margin-left: 25px;">
									<span>2 SHIPPING MARK:</span><span contenteditable="true"
										class="ShippingMark"></span>
								</p>
								<p style="margin-left: 25px;">
									<span>3 Indicating CONTRACT NO.: </span><span
										contenteditable="true" class="ContractNO"></span>
								</p>
								<p style="margin-left: 25px;">
									<span>4 Port of shipment:</span><span contenteditable="true"
										class="ShipmentPort"></span>
								</p></td>
						</tr>
					</table>
					<div class="m60b tc ContractUSDItem">
						<table cellpadding="0" cellspacing="0" border="1"
							style="width: 90%; margin-bottom: 20px;">
							<tr style="background: #e4e8eb;" id="ItemTit">
								<td><p>Item</p></td>
								<td><p>Part</p></td>
								<td><p>Description</p></td>
								<td><p>Qty</p></td>
								<td style="display:none"><p>Unit Price</p></td>
								<td style="display:none"><p>Extended Price</p></td>
								<td><p>删除</p></td>
							</tr>
							<tr class="SubTotalCon">
								<td colspan="3"><p 
										style="text-align: right; margin-right: 10px;">Sub-total</p></td>
								<td colspan="2"><p class="SubTotal" contenteditable="true"></p></td>
							</tr>
							<tr class="DiscountedCon">
								<td colspan="3"><p 
										style="text-align: right; margin-right: 10px;">Discounted
										x%</p></td>
								<td colspan="2"><span contenteditable="true"
									class="Discounted ConflgBlur"
									style="min-width: 20px; display: inline-block;"></span></td>
							</tr>
							<tr class="FinalTotalCon">
								<td colspan="3"><p 
										style="text-align: right; margin-right: 10px;">Final Total</p></td>
								<td colspan="2"><p class="FinalTotal" contenteditable="true"></p></td>
							</tr>
							<tr class="ContactCon">
								<td colspan="5">
									<p>
										<span>If you have any questions about your order,
											please contact:</span><span contenteditable="true" class="Contact"></span>
									</p>
								</td>
							</tr>
							<tr class="EmailCon">
								<td colspan="5">
									<p>
										<span> E-Mail: </span><span contenteditable="true"
											class="Email"></span>
									</p>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>

			<!-- 页脚 -->
			<div class="yejiao cf" style="margin-top: 20px; height: 80px;">
				<hr>
				<pre
					style="text-align: center; color: #000080; font-family: -webkit-body;">EOULU
			Suzhou ● Shenzhen ● Beijing ● Shanghai ● HongKong
			〡Phone: +86-512-62757360〡Web:www.eoulu.com〡Email:info@eoulu.com〡</pre>

			</div>
		</div>
	    <!--    OtherPOPdf 模板  -->
		<div id="view9" class="news OtherPOPdf" style="display:none;">
			<!-- 页眉-->
			<div class="yemei" style="margin-top: 20px;">
				<div style="width: 100%; height: 70px;">
					<div class="logo rt">
						<!-- <img src="image/EOULUlogo.png" style="width: 175px; height: 61px;"> -->
					</div>
				</div>
				<hr>
			</div>
			<!--内容  -->
			<div id="OtherPOCon" class="s12">
				<div class="lf m30 m30b" style="padding-left: 10%; font-size: 29px;">
					<p>Purchase Order</p>
				</div>
				<div class=" cf rt pdfDate m30b" style="padding-right: 15%;">
					<p>
						<span>Number: </span><span contenteditable="true" class="Number OtherPOConNumber"
							style="display: inline-block; min-width: 40px;"></span>
					</p>
					<p>
						<span>Versions: </span><span contenteditable="true"
							class="Version" style="display: inline-block; min-width: 40px;"></span>
					</p>
				</div>
				<div class="m30b">
					<table cellpadding="0" cellspacing="0" border="1"
						style="width: 90%; margin-bottom: 20px;">
						<tr>
							<td style="width: 67%;"><b>Bill To</b></br>
								<p>Wang Xiaoliang</p>
								<p>Email: wangxiaoliang@eoulu.com</p>
								<p>Room 1501, Grand Millennium Plaza (Lower Block), 181
									Queen's Road</p>
								<p>Central, HONG KONG</p>
								<p>TEL:00852-21527388</p></td>
							<td><b>Vendor</b></br>
								<p contenteditable="true" class="VendorOne"></p>
								<p >
									<span>Email:</span><span contenteditable="true"
										class="VendorThree"></span>
								</p>
								<p contenteditable="true" class="VendorTwo"></p>
								<p>
									<span>TEL:</span><span contenteditable="true"
										class="VendorFour"></span>
								</p>
							<!-- 	<p class="mh25"></p>
								<p class="mh25"></p> --></td>
						</tr>
						<tr>
							<td><b>Forwarder</b></br>
								<p class="ForwarderOne" contenteditable="true"></p>
								<p class="ForwarderTwo" contenteditable="true"></p>
								<p class="ForwarderThree" contenteditable="true"></p>
								<p class="ForwarderFour" contenteditable="true"></p></td>
							<td><b>Ship to</b></br>
								<p class="ShipCompany" contenteditable="true"></p>
								<p class="ShipAddr" contenteditable="true"></p>
								<p class="ShipTel" contenteditable="true"></p>
								<p class="ShipAttn" contenteditable="true"></p></td>
						</tr>
						<tr>
							<td colspan="2"><b>Others</b></br>
								<p>
									<span>Credit Term:</span><span contenteditable="true"
										class="CreditTerm"></span>
								</p>
								<p>
									<span>Delivery Term: FCA Factory,</span><span
										contenteditable="true" class="DeliveryTerm"></span>
								</p></td>
						</tr>
						<tr>
							<td colspan="2"><b>Shipping Instruction</b></br>
								<p style="margin-left: 25px;">1 Please do not put the
									original invoice with goods</p>
								<p style="margin-left: 25px;">
									<span>2 SHIPPING MARK:</span><span contenteditable="true"
										class="ShippingMark"></span>
								</p>
								<p style="margin-left: 25px;">
									<span>3 Indicating CONTRACT NO.: </span><span
										contenteditable="true" class="ContractNO"></span>
								</p>
								<p style="margin-left: 25px;">
									<span>4 Port of shipment:</span><span contenteditable="true"
										class="ShipmentPort"></span>
								</p></td>
						</tr>
					</table>
					<div class="m60b tc ContractUSDItem">
						<table cellpadding="0" cellspacing="0" border="1"
							style="width: 90%; margin-bottom: 20px;" class="pdf_four_tab">
							<tr style="background: #e4e8eb;" id="ItemTit">
								<td><p>Item</p></td>
								<td><p>Part</p></td>
								<td><p>Description</p></td>
								<td><p>Qty</p></td>
								<td><p>Unit Price</p></td>
								<td><p>Extended Price</p></td>
								<td><p>删除</p></td>
							</tr>
							<tr class="SubTotalCon">
								<td colspan="5"><p  style="text-align: right; margin-right: 10px;">Sub-total</p></td>
								<td colspan="2"><p class="SubTotal" contenteditable="true"></p></td>
							</tr>
							<tr class="DiscountedCon">
								<td colspan="5"><p contenteditable="true" style="text-align: right; margin-right: 10px;">Discounted x%</p></td>
								<td colspan="2"><span contenteditable="true" class="Discounted ConflgBlur" style="min-width: 20px; display: inline-block;"></span></td>
							</tr>
							<tr class="FinalTotalCon">
								<td colspan="5"><p  style="text-align: right; margin-right: 10px;">Final Total</p></td>
								<td colspan="2"><p class="FinalTotal" contenteditable="true"></p></td>
							</tr>
							<tr class="ContactCon">
								<td colspan="7">
									<p>
										<span>If you have any questions about your order,
											please contact:</span><span contenteditable="true" class="Contact"></span>
									</p>
								</td>
							</tr>
							<tr class="EmailCon">
								<td colspan="7">
									<p>
										<span> E-Mail: </span><span contenteditable="true"
											class="Email"></span>
									</p>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>

			<!-- 页脚 -->
			<div class="yejiao cf" style="margin-top: 20px; height: 80px;">
				<hr>
				<pre
					style="text-align: center; color: #000080; font-family: -webkit-body;">EOULU
			Suzhou ● Shenzhen ● Beijing ● Shanghai ● HongKong
			〡Phone: +86-512-62757360〡Web:www.eoulu.com〡Email:info@eoulu.com〡</pre>

			</div>
		</div>
		
		<!--   OtherPORMB  模板  -->
		<div id="view10" class="news hidePdf10" style="display:none;">
			<!-- 页眉-->
			<div class="yemei" style="margin-top: 20px;">
				<div style="width: 100%; height: 70px;">
					<div class="logo rt">
						<!-- <img src="image/EOULUlogo.png" style="width: 175px; height: 61px;"> -->
					</div>
				</div>
				<hr>
			</div>
			<!--内容  -->
			<div id="OtherPORMB" class="s12">
				<div class="lf m30 m30b" style="padding-left: 10%; font-size: 29px;">
					<p>Purchase Order</p>
				</div>
				<div class=" cf rt pdfDate m30b" style="padding-right: 15%;">
					<p>
						<span>Number: </span><span contenteditable="true" class="Number OtherPORMBNumber" style="display: inline-block; min-width: 40px;"></span>
					</p>
					<p>
						<span>Versions: </span><span contenteditable="true" class="Version" style="display: inline-block; min-width: 40px;"></span>
					</p>
				</div>
				<div class="m30b">
					<table cellpadding="0" cellspacing="0" border="1"
						style="width: 90%; margin-bottom: 20px;">
						<tr>
							<td style="width: 67%;"><b>Bill To</b></br>
								<p>苏州伊欧陆系统集成有限公司</p>
								<p class="BillContact" contenteditable="true"></p>
								<p>邮箱:<span class="BillEmail" contenteditable="true"></span></p>
								<p>苏州工业园区星湖街218号生物纳米园A7楼305室</p>
								<p>电话:0512-62757360</p></td>
							<td><b>Vendor</b></br>
								<p contenteditable="true" class="VendorOne"></p>
								<p contenteditable="true" class="VendorTwo"></p>
								<p >
									<span>邮箱:</span><span contenteditable="true"
										class="VendorThree"></span>
								</p>
								<p contenteditable="true" class="VendorFour"></p>
								<p >
									<span>电话:</span><span contenteditable="true"
										class="VendorFive"></span>
								</p>
							</td>
						</tr>
						<tr>
							<td colspan="2"><b>Ship to</b></br>
								<p class="ShipCompany" contenteditable="true">公司：苏州伊欧陆系统集成有限公司</p>
								<p class="ShipAddr" contenteditable="true">地址：苏州工业园区星湖街218号生物纳米园A7楼305室</p>
								<p class="ShipTel">电话：<span class="ShipTelContent" contenteditable="true"></span></p>
								<p class="ShipAttn">联系人：<span class="ShipAttnContact" contenteditable="true"></span></p></td>

						</tr>
						<tr>
							<td colspan="2"><b>Others</b></br>
								<p>
									<span>Credit Term:</span><span contenteditable="true" class="CreditTerm"></span>
								</p>
								<p>
									<span>Delivery Term:</span><span contenteditable="true" class="DeliveryTerm"></span>
								</p></td>
						</tr>
						<tr>
							<td colspan="2"><b>Shipping Instruction</b></br>
								<p style="margin-left: 25px;">1. Please do not put the original invoice with goods</p>
								<p style="margin-left: 25px;">
									<span>2. SHIPPING MARK:</span><span contenteditable="true" class="ShippingMark"></span>
								</p>
								<p style="margin-left: 25px;">
									<span>3. Indicating CONTRACT NO.: </span><span contenteditable="true" class="ContractNO"></span>
								</p>
								<p style="margin-left: 25px;">
									<span>4. Port of shipment:</span><span contenteditable="true" class="ShipmentPort"></span>
								</p>
							</td>
						</tr>
					</table>
					<div class="m60b tc ContractRMBItem">
						<table cellpadding="0" cellspacing="0" border="1" style="width: 90%; margin-bottom: 20px;" class="pdf_four_tab">
							<tr style="background: #e4e8eb;" id="ItemTit">
								<td><p>Item</p></td>
								<td><p>Part</p></td>
								<td><p>Description</p></td>
								<td><p>Qty</p></td>
								<td><p>Unit Price</p></td>
								<td><p>Extended Price</p></td>
								<td><p>删除</p></td>
							</tr>
							<tr class="SubTotalCon">
								<td colspan="5"><p  style="text-align: right; margin-right: 10px;">Sub-total</p></td>
								<td colspan="2"><p class="SubTotal" contenteditable="true"></p></td>
							</tr>
							<tr class="DiscountedCon">
								<td colspan="5"><p contenteditable="true" style="text-align: right; margin-right: 10px;">Discounted x%</p></td>
								<td colspan="2"><span contenteditable="true" class="Discounted ConflgBlur" style="min-width: 20px; display: inline-block;"></span></td>
							</tr>
							<tr class="FinalTotalCon">
								<td colspan="5"><p contenteditable="true" style="text-align: right; margin-right: 10px;">Final Total</p></td>
								<td colspan="2"><p class="FinalTotal" contenteditable="true"></p></td>
							</tr>
							<tr class="ContactCon">
								<td colspan="7">
									<p>
										<span>If you have any questions about your order,please contact:</span><span contenteditable="true" class="Contact"></span>
									</p>
								</td>
							</tr>
							<tr class="EmailCon">
								<td colspan="7">
									<p>
										<span> E-Mail: </span><span contenteditable="true" class="Email"></span>
									</p>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>

			<!-- 页脚 -->
			<div class="yejiao cf" style="margin-top: 20px; height: 80px;">
				<hr>
				<pre
					style="text-align: center; color: #000080; font-family: -webkit-body;">EOULU
			Suzhou ● Shenzhen ● Beijing ● Shanghai ● HongKong
			〡Phone: +86-512-62757360〡Web:www.eoulu.com〡Email:info@eoulu.com〡</pre>

			</div>
		</div>
		
		<div class="Sidebar_PO">
			<div class="Sidebar_CascadePO isHover" id="Sidebar_CascadePO">Cascade PO 配件</div>
			<span class="ID1" style="display:none"></span>
			<span class="CompleteID" style="display:none"></span>
			<span class="POID1" style="display:none"></span>
			<div class="Sidebar_CascadePOMachine " id="Sidebar_CascadePOMachine">Cascade PO 整机</div>
			<span class="PartID" style="display:none"></span>
			<span class="POID2" style="display:none"></span>
			<div class="Sidebar_OtherPO" id="Sidebar_OtherPO">其他供应商USD PO</div>
			<div class="Sidebar_OtherPORMB" id="Sidebar_OtherPORMB">其他供应商RMB PO</div>
		</div>
		
		<input type="button" value="关闭" class="bToggle " id="CascadePO_close" style="position: absolute; z-index: 11; top: 100px; left: 75%; width: 92px; height: 30px; font-size: 19px;">
		<input type="button" value="提交" class="bToggle" id="CascadePO_submit" style="position: absolute; z-index: 11; top: 150px; left: 75%; width: 92px; height: 30px; font-size: 19px;">
		<input type="button" value="下载" class="bToggle" id="download8" style="position: absolute; z-index: 11; top: 200px; left: 75%; width: 92px; height: 30px; font-size: 19px;">
		<input type="button" value="发送" class="bToggle" id="PO_Send" style="position: absolute; z-index: 11; top: 250px; left: 75%; width: 92px; height: 30px; font-size: 19px;">
	</div>

	<!-- 添加通知备货信息 发送邮件 -->
	<div class="contract_send" >
		<div class="contract_title">通知备货</div>
		<div class="tc f24" style="margin: 20px;">是否发送备货清单</div>
		<div class="edit_btn">
			<input type="button" value="确定" class="bToggle" id="send_submit">
			<input type="button" value="取消" class="bToggle" id="send_cancel">
		</div>
	</div>
	<!-- CascadePO    发送-->
	<div class="contract_sendPO contract_sendPO1" style="display: none;">
		<div class="contract_title">PO邮箱模板</div>
		<div class="contract_close" style="font-size:16px">关闭</div>
		<div class="basic_info">
			<div class="table_title">邮件模板</div>
			<span class="ID1" style="display:none"></span>
			<span class="ID2" style="display:none"></span>
			<div class="contract_basic">
					<div class="Tolist" id="Tolist">
						<div class="Toman1 newToman">
							 <span class="Toman">收件人</span>
							 <input type="text" name="ToEmail" value="SalesOps-BV@formfactor.com" class="ToE_inp">
							 <span class="To_add"><i class="fa fa-plus-square" id="fa-button3"></i></span>
							 <span class= "To_del" style="margin-left:3px;" ><i class="fa fa-close" id="fa-button3"></i></span>
							 <span class= "To_del1 To_del" ><i class="fa fa-close" id="fa-button3"></i></span>
						</div>
						<!-- <div class="Toman2 newToman">
							<span class="Toman">收件人1</span>
							<input type="text" name="ToEmail" value="Wilson.Wu@formfactor.com" class="ToE_inp">
							<span class="To_add"><i class="fa fa-plus-square" id="fa-button3"></i></span>
							<span class= "To_del" style="margin-left:3px;" ><i class="fa fa-close" id="fa-button3"></i></span>
							<span class= "To_del1 To_del" ><i class="fa fa-close" id="fa-button3"></i></span>
						</div> -->
					</div>
					<div class="Copylist" id="Copylist">
						<div class="Copyman1 newCopyman">
							 <span class="Copyman">抄送人</span>
							 <input type="text" name="CopyTo" value="AChen3@formfactor.com" class="CopyE_inp">
						</div>
						<div class="Copyman2 newCopyman">
							 <span class="Copyman">抄送人1</span>
							 <input type="text" name="CopyTo" value="Wilson.wu@formfactor.com" class="CopyE_inp">
							 <span class= "Copy_del" style="margin-left:3px;" ><i class="fa fa-close" id="fa-button3"></i></span>
						</div>
						<div class="Copyman3 newCopyman">
							 <span class="Copyman">抄送人2</span>
							 <input type="text" name="CopyTo" value="jiangyaping@eoulu.com" class="CopyE_inp">
							 <span class= "Copy_del" style="margin-left:3px;" ><i class="fa fa-close" id="fa-button3"></i></span>
						</div>
						<div class="Copyman4 newCopyman">
							 <span class="Copyman">抄送人3</span>
							 <input type="text" name="CopyTo" value="zhaona@eoulu.com" class="CopyE_inp">
							 <!-- <span class="Copy_add"><i class="fa fa-plus-square" id="fa-button3"></i></span> -->
							 <span class= "Copy_del" style="margin-left:3px;" ><i class="fa fa-close" id="fa-button3"></i></span>
						</div>
						<div class="Copyman5 newCopyman">
							 <span class="Copyman">抄送人4</span>
							 <input type="text" name="CopyTo" value="zhaowenzhen@eoulu.com" class="CopyE_inp">
							 <span class="Copy_add"><i class="fa fa-plus-square" id="fa-button3"></i></span>
							 <span class= "Copy_del" style="margin-left:3px;" ><i class="fa fa-close" id="fa-button3"></i></span>
						</div>
					</div>
					
					<!-- <p class="Copylist" id="Copylist">
						<span  class="Copyman" >抄送人</span>
						<span  class="CopyEmailSty" style="margin-right:1.3%"><input type="text" name="CopyTo" value=""  id="CopyEmail"  class="CopyE_inp"></span>
						<span class="Copy_add"><i class="fa fa-plus-square" id="fa-button3"></i></span>	
					</p> -->
					<p>
						<span style="margin-right:4.8%;">主题</span>
						<span class="theme_inp"><span  value="" id="Theme"></span></span>
					</p>
					<p>
						<span class="ContentSty" style="margin-right:4.8%">内容</span>
						<span><textarea cols="112" rows="5" name="Content" value="" id="NickName">Hi, Ailin,
Please kindly refer to attached new PO.
Would you please help enter it and arrange a soonest ship date?
Thanks for your support！
						</textarea></span>
					</p>
					<!-- <p>
						<span style="margin-right:1.2%">邮箱密码</span>
						<span class="password_inp"><input type="text" name="Password" value="" id="Password" class="Password"></span>
						<span class="passeye" style="margin-left:1%"></span>
					</p> -->
			</div>		
		</div>
		<div class="edit_btn edit_btn1">
			<input type="button" value="发送邮件" class="bToggle" id="send_submit1">
			<input type="button" value="取消发送" class="bToggle" id="send_cancel1">
		</div>
	</div>
	<!-- CascadePO    发送-->
	<div class="contract_sendPO contract_sendPO2" style="display: none;">
		<div class="contract_title">通知发送</div>
		<div class="basic_info">
			<div class="table_title">是否可以发货</div>
			<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
				<tbody>	
					<tr>
					    <td>收件人</td>
						<td><input type="text" name="ToEmail" value="" id="ToEmail"></td>	
					</tr>
					<tr>
						<td>抄送</td>
						<td><input type="text" name="CopyTo" value=""  id="CopyTo"></td>
					</tr>
					<tr>
						<td>昵称</td>
						<td><input type="text" name="NickName" value="" id="NickName"></td>
					</tr>
				</tbody>
			</table>		
		</div>
		<div class="edit_btn edit_btn2">
			<input type="button" value="确定" class="bToggle" id="send_submit2">
			<input type="button" value="取消" class="bToggle" id="send_cancel2">
		</div>
	</div>
	
	<!--发货通知  模态框  -->
	<div class="ShipnoticeBox" style="display: none;">
		<div class="contract_title">发货通知</div>
		<div class="Shipnotice_close">关闭</div>
		<div class="ShipnoticeMsg">
			<ul class="Shipnotice_leftul">
				<li>
					<span class="Shipnotice_span"><b style="color:red;margin-top:20px;line-height:16px;">*</b>箱单</span>
					<form class="add_fileBox"  id="add_fileBox1"  method="post" target="myframe" action="QuoteDeliveryAdviceOperate" enctype="multipart/form-data" >
						<span class="add_fileCont"></span>
						<span class="add_uploadText">上传</span>
					    <input class="add_change add_file" type="file" multiple="multiple" name="fileName"/>
					    <input name="classify" value="ContractPath" type="hidden"/>
					</form>
				</li>
				<li>
					<span class="Shipnotice_span"><b style="color:red;line-height:16px;">*</b>PO</span>
					<input type="text" name="PONO" value="" >
				</li>
				<li>
					<span class="Shipnotice_span"><b style="color:red;line-height:16px;">*</b>SO</span>
					<input type="text" name="SONO" value="" >
				</li>
				<li>
					<span class="Shipnotice_span"><b style="color:red;line-height:16px;">*</b>原厂服务</span>
					<select class="OriginService" >
							<option value="是">是</option>
							<option value="否">否</option>
					</select>
				</li>
			</ul>
			<ul class="Shipnotice_rightul">
				<li>
					<span class="Shipnotice_span">公司名称</span>
					<input type="text" name="Company" value="" >
				</li>
				<li>
					<span class="Shipnotice_span">联系人</span>
					<input type="text" name="Contact" value="" >
				</li>
				<li>
					<span class="Shipnotice_span">Email</span>
					<input type="text" name="Email" value="" >
				</li>
				<li>
					<span class="Shipnotice_span">联系电话</span>
					<input type="text" name="LinkTel" value="" id="LinkTel">
				</li>
				<li>
					<span class="Shipnotice_span">装机地点</span>
					<input type="text" name="InstallPlace" value="" id="InstallPlace">
				</li>
			</ul>
			<div  class="Shipnotice_TabBox cf" >
				<table border="1" cellspacing="0" cellspadding="0" id="ShipnoticeTable"  style="width:100%;height:30px;text-align:center;border-color:#00aeef;">
					<tr class="tHead">
						<td class="Shipnotice_Ser" style="width:8.8%;height:30px;line-height:30px;outline:none;"><span class="fa fa-plus-square-o SerAdd" style="vertical-align: middle;display:none;"></span><span class="SerText">序号</span></td>
						<td style="width:19.85%;height:30px;line-height:30px;outline:none;">型号</td>
						<td style="width:37.26%;height:30px;line-height:30px;outline:none;">描述</td>
						<td style="width:8.71%;height:30px;line-height:30px;outline:none;">数量</td>
						<td style="width:15.71%;height:30px;line-height:30px;outline:none;">备注</td>
						<td style="width:9.52%;height:30px;line-height:30px;outline:none;">操作</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="btnBox">
			<input type="button" value="提交通知" class="bToggle" id="Shipnotice_submit">
			<input type="button" value="取消更改" class="bToggle" id="Shipnotice_cancel">
		</div>
	</div>
	<iframe style="display:none;" name="myframe"></iframe>  <!-- 表单提交 -->
	<img src="image/goTop.png" class="goTop"/>	
	<a  class="downLoad"><button class="new-btn-login" id="subBtn" type="button"></button></a>
	<div class="ForwarderSel" style="display: none;" id="">
		<div class="ForwarderSel-tit">
			<div class="ForwarderSel-tit-l">Forwarder选择</div>
			<div class="ForwarderSel-tit-r">关闭</div>
		</div>
		<div class="ForwarderSel-body">
			<div class="ForwarderSel-body-in">
				<select name="" id="ForwarderSelect">
					<option name="0" value="请选择Forwarder" checked>请选择Forwarder</option>
				</select>
			</div>
		</div>
	</div>
	<div class="ShipToSel" style="display: none;" id="">
		<div class="ShipToSel-tit">
			<div class="ShipToSel-tit-l">ShipTo选择</div>
			<div class="ShipToSel-tit-r">关闭</div>
		</div>
		<div class="ShipToSel-body">
			<div class="ShipToSel-body-in">
				<select name="" id="ShipToSelect">
					<option name="0" value="请选择ShipTo" checked>请选择ShipTo</option>
				</select>
			</div>
		</div>
	</div>
</body>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<!-- <script src="js/global/myFunction.js" type="text/javascript" charset="utf-8"></script> -->
<script type="text/javascript" src="js/FileSaver.js"></script>
<script type="text/javascript" src="js/jquery.wordexport.quotelist.js"></script>
<script src="js/msgbox.js"></script>
<!-- <script src="js/msgbox_unload.js"></script> -->
<script src="js/quotelist.js"></script>
<script src="js/quotelist_submit.js"></script>
<script type="text/javascript" src="js/html2canvas.js"></script>
<script type="text/javascript" src="js/jsPdf.debug.js"></script>
<script>

	/*********************添加报价单 ************************/
	var scorllFlag =  false;  //滚动标识  gotop显示
	var priceNum = 0;
	//点击添加
	function AddContract() {
		var ddd = new Date();
		var day = ddd.getDate();
		if (day < 10) {
			day = "0" + day;
		} else {
			day = day;
		}
		var month = ddd.getMonth() + 1;
		var datew = ddd.getFullYear() + "-" + month + "-" + day;
		$("#Datesent").val(datew);

		$.ajax({
			type : 'get',
			url : 'QuoteCurrentCount',
			data : {},
			dataType : 'json',
			success : function(data) {
				console.log(data);
				if (data < 10) {
					priceNum = "0" + data;
				} else {
					priceNum = data;
				}
			},
			error : function() {
				$.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
			}
		});
		$('.MailBar_cover_color').show();
		$('.contract_add').show();
		$(".MailBar_cover_color").css("height",$(".contain").height()+80);   
	};


	//点击确定刷新页面
	$(document).on("click", "#mb_btn_ok", function() {
		window.location.reload();
	});

	//点击关闭
	$('.contractAdd_close').click(function() {
		$('.MailBar_cover_color').hide();
		$('.contract_add').hide();
		$(".ShipnoticeBox").hide();
		$(".contract_add #tableADD_one input").val("");
	});
	// $('#update_cancel').click(function() {
	// 	$('.MailBar_cover_color').hide();
	// 	$('.contract_update').hide();
	// });
	// $('.contractUpdate_close').click(function() {
	// 	$('.MailBar_cover_color').hide();
	// 	$('.contract_update').hide();
	// });
	$('.contractMerch_close').click(function() {
		$('.MailBar_cover_color').hide();
		$('.merchandise_add').hide();
	});

	//备货取消
	$('#send_cancel').click(function() {
		$('.MailBar_cover_color').hide();
		$('.contract_send').hide();
		window.location.reload();
	});

	//点击取消
	$('#add_cancel').click(function() {
		$('.MailBar_cover_color').hide();
		$('.contract_add').hide();
		$(".contract_add #tableADD_one input").val("");
	});

	$('#merchandise_cancel').click(function() {
		$('.MailBar_cover_color').hide();
		$('.merchandise_add').hide();
	});

	$('#OtherPO_close').click(function() {
		$('.MailBar_cover_color').hide();
		$('.OtherPOPdf').hide();
	});

	//点击取消
	$('#CascadePO_close').click(function() {
		$(".ForwarderSel").fadeOut(200);
		$(".ShipToSel").fadeOut(200);
		$('.MailBar_cover_color').hide();
		$('.CascadePOPdf').hide();
	});
	/****************** 跳页 **********************/
	function FistPage(arg) {
		window.location.href = arg + "1";
	}
	function UpPage(arg) {
		window.location.href = arg;
	}
	function NextPage(arg) {
		var queryType = $(".leixing").val();
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

	function fm(obj) {
		var vb = obj.value.split('.');
		var val = vb[0].replace(/\D/, '');
		if (obj.value.length > 0)
			obj.value = val.match(/\d{3}|\d{2}|\d/g).join(',')
					+ (vb.length > 1 ? '.' + vb[1] : '');
	}
	function fmoney(s, n) //s:传入的float数字 ，n:希望返回小数点几位 
	{
		n = n > 0 && n <= 20 ? n : 2;
		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
		var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
		t = "";
		for (i = 0; i < l.length; i++) {
			t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
		}
		return t.split("").reverse().join("") + "." + r;
	}

	function fmoney2(s, n) //s:传入的float数字 ，n:希望返回小数点几位 
	{
		n = n > 0 && n <= 20 ? n : 2;
		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
		var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
		t = "";
		for (i = 0; i < l.length; i++) {
			t += l[i] + ((i + 1) % 4 == 0 && (i + 1) != l.length ? "," : "");
		}
		return t.split("").reverse().join("") + "." + r;
	}

function sales_quotation(){
	$(".sales_hide1, .sales_hide2, .sales_hide3, .sales_hide4, div#costComparison").hide();
	$("title").text("销售报价系统");
	$(".quote_customer").addClass("sales_customer").removeClass("quote_customer");
	$(".quote_goodsModel").addClass("sales_goodsModel").removeClass("quote_goodsModel");
	$(".quote_contacts").addClass("sales_contacts").removeClass("quote_contacts");
}

$(function(){
	//判断显示*************************************
	/* //进页面的时候截取地址栏地址 */
	//获取token
    function getUrlParam() {
        var args=new Object();
        var query=location.search.substring(1);//获取查询串
        var pairs=query.split("&");//在逗号处断开
        for(var i=0;i<pairs.length;i++)
        {
            var pos=pairs[i].indexOf('=');//查找name=value
            if(pos==-1)   continue;//如果没有找到就跳过
            var argname=pairs[i].substring(0,pos);//提取name
            var value=pairs[i].substring(pos+1);//提取value
            args[argname]=unescape(value);//存为属性
        }
        return args;
    } 
    var tokenArr = getUrlParam();
 	var token=tokenArr.token;
 	console.log(tokenArr)		
	
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

	if(window.location.href.indexOf("/SalesQuotationSystem")>-1){
		sales_quotation();
	}else{
		
	}

});

	//CascadePO 删除
	$(document).on("click",".CascadePOPdf #view8 .CascadePOItemBtn",function() {
				console.log($(this).parent().parent().parent().find("td").eq(0).attr("value"))
				var delID = $(this).parent().parent().parent().find("td").eq(0).attr("value");
				var that = $(this).parent().parent().parent();
				console.log(delID)
			
				/* $.ajax({
					type : 'get',
					url : "QuoteCascadeDelete",
					data : {
						ID : delID,
					},
					dataType : 'json',
					success : function(data) {
						console.log(data); */
						that.remove();
							//计算PO美元部分的总价
							 if($(".CascadePOPdf #view8 .CascadePOItemTr").length > 0 ){
								var totalNum = 0;
								for (var i = 0; i < $(".CascadePOPdf #view8 .CascadePOItemTr").length; i++) {
									totalNum += ($(".CascadePOPdf #view8 .CascadePOItemTr").eq(i).find(".Qty").text())* rmoney(fmoney($(".CascadePOPdf #view8 .CascadePOItemTr").eq(i).find(".UnitPrice").text()));
								}
							}else{
								var totalNum = 0;
								
							} 
							console.log("totalNum"+totalNum)
							$(".CascadePOPdf #view8 .SubTotal").text("").text(fmoney(totalNum.toFixed(2))); 
							$(".CascadePOPdf #view8 .FinalTotal").text("").text(fmoney(totalNum.toFixed(2))); 
			})

			//CascadePO 整机 删除
	$(document).on("click",".CascadePOPdf #view89 .CascadePOItemBtn",function() {
		/*  alert(999) ;
		 return; */
				console.log($(this).parent().parent().parent().find("td").eq(0).attr("value"))
				var delID = $(this).parent().parent().parent().find("td").eq(0).attr("value");
				var that = $(this).parent().parent().parent();
				console.log(delID)
			
				/* $.ajax({
					type : 'get',
					url : "QuoteCascadeDelete",
					data : {
						ID : delID,
					},
					dataType : 'json',
					success : function(data) {
						console.log(data); */
						that.remove();
							//计算PO美元部分的总价
							 if($(".CascadePOPdf #view89 .CascadePOItemTr").length > 0 ){
								var totalNum = 0;
								for (var i = 0; i < $(".CascadePOPdf #view89 .CascadePOItemTr").length; i++) {
									totalNum += ($(".CascadePOPdf #view89 .CascadePOItemTr").eq(i).find(".Qty").text())* rmoney(fmoney($(".CascadePOPdf  #view89 .CascadePOItemTr").eq(i).find(".UnitPrice").text()));
								}
							}else{
								var totalNum = 0;
								
							} 
							console.log("totalNum"+totalNum)
							$(".CascadePOPdf #view89 .SubTotal").text("").text(fmoney(totalNum.toFixed(2))); 
							$(".CascadePOPdf #view89 .FinalTotal").text("").text(fmoney(totalNum.toFixed(2))); 
			})
			
	//计算折扣后的值
	$(document).on("blur","#view8 .ConflgBlur",function() {
		/*  alert(12) */ 
				var FinalTotal = 0;
				var CascadePODiscounted = $("#view8 .Discounted").text();
				console.log(CascadePODiscounted)
				if(parseFloat(CascadePODiscounted)=="0" || parseFloat(CascadePODiscounted)=="" ){
					$("#view8  .FinalTotal").text("").text('$'+fmoney($("#view8  .SubTotal").text()));
				}else{
					FinalTotal = rmoney(fmoney($("#view8  .SubTotal").text()))* (parseFloat(CascadePODiscounted) / 100);
					console.log(FinalTotal)
					$("#view8  .FinalTotal").text("").text('$'+fmoney(FinalTotal));
				}
				$(this).text(parseFloat(CascadePODiscounted).toFixed(2)+"%");
			})	
			//计算折扣后的值
	
	$(document).on("blur","#view8 .SubTotal",function() {
		/*  alert(12) */ 
				var CascadePODiscounted = $("#view8  .Discounted").text();
				var FinalTotal = 0;
				var S = $("#view8  .SubTotal").text();
				if(S.indexOf('$')<0){
					var CascadePOSubTotal = rmoney($("#view8  .SubTotal").text())*1;
				}else{
					var CascadePOSubTotal = rmoney($("#view8  .SubTotal").text().substring(1))*1;
				}
				console.log(CascadePOSubTotal)
				if(parseFloat(CascadePODiscounted)=="0" || parseFloat(CascadePODiscounted)=="" ){
					$("#view8  .FinalTotal").text("").text('$'+fmoney(CascadePOSubTotal));
				}else{
					FinalTotal = rmoney(fmoney(CascadePOSubTotal))* (parseFloat(CascadePODiscounted) / 100);
					console.log(FinalTotal)
					$("#view8  .FinalTotal").text("").text('$'+fmoney(FinalTotal));
				}
				$(this).text('$'+fmoney(CascadePOSubTotal));
			})	
	//新添加的CascadePO整机	
	$(document).on("blur","#view89 .ConflgBlur",function() {
		 /* alert(34)  */
				var FinalTotal = 0;
				var CascadePODiscounted = $("#view89 .Discounted").text();
				console.log(CascadePODiscounted)
				if(parseFloat(CascadePODiscounted)=="0" || parseFloat(CascadePODiscounted)=="" ){
					$("#view89 .FinalTotal").text("").text('$'+fmoney($("#view89 .SubTotal").text()));
				}else{
					FinalTotal = rmoney(fmoney($("#view89 .SubTotal").text()))* (parseFloat(CascadePODiscounted) / 100);
					console.log(FinalTotal)
					$("#view89 .FinalTotal").text("").text('$'+fmoney(FinalTotal));
				}
				$(this).text(parseFloat(CascadePODiscounted).toFixed(2)+"%");
			})
	//计算折扣后的值
	
	$(document).on("blur","#view89 .SubTotal",function() {
		/*  alert(12) */ 
				var CascadePODiscounted = $("#view89  .Discounted").text();
				var FinalTotal = 0;
				var S = $("#view89  .SubTotal").text();
				if(S.indexOf('$')<0){
					var CascadePOSubTotal = rmoney($("#view89  .SubTotal").text())*1;
				}else{
					var CascadePOSubTotal = rmoney($("#view89  .SubTotal").text().substring(1))*1;
				}
				console.log(CascadePOSubTotal)
				if(parseFloat(CascadePODiscounted)=="0" || parseFloat(CascadePODiscounted)=="" ){
					$("#view89  .FinalTotal").text("").text('$'+fmoney(CascadePOSubTotal));
				}else{
					FinalTotal = rmoney(fmoney(CascadePOSubTotal))* (parseFloat(CascadePODiscounted) / 100);
					console.log(FinalTotal)
					$("#view89  .FinalTotal").text("").text('$'+fmoney(FinalTotal));
				}
				$(this).text('$'+fmoney(CascadePOSubTotal));
			});

	$(document).on("blur","#view9 .SubTotal",function() {
		/*  alert(12) */ 
				var CascadePODiscounted = $("#view9 .Discounted").text();
				var FinalTotal = 0;
				var S = $("#view9 .SubTotal").text();
				if(S.indexOf('$')<0){
					var CascadePOSubTotal = rmoney($("#view9 .SubTotal").text())*1;
				}else{
					var CascadePOSubTotal = rmoney($("#view9 .SubTotal").text().substring(1))*1;
				}
				console.log(CascadePOSubTotal)
				if(parseFloat(CascadePODiscounted)=="0" || parseFloat(CascadePODiscounted)=="" ){
					$("#view9 .FinalTotal").text("").text('$'+fmoney(CascadePOSubTotal));
				}else{
					FinalTotal = rmoney(fmoney(CascadePOSubTotal))* (parseFloat(CascadePODiscounted) / 100);
					console.log(FinalTotal)
					$("#view9 .FinalTotal").text("").text('$'+fmoney(FinalTotal));
				}
				$(this).text('$'+fmoney(CascadePOSubTotal));
			});
			
			
	$(document).on("blur",".OtherPOPdf .ConflgBlur",function() {
				var FinalTotal = 0;
				var OtherDiscounted = $(".OtherPOPdf .Discounted").text();
				if(parseFloat(OtherDiscounted) == 0 ||  parseFloat(OtherDiscounted) =="" ){
					$(".OtherPOPdf .FinalTotal").text("").text(fmoney($(".OtherPOPdf .SubTotal").text()));
				}else{
					FinalTotal = rmoney(fmoney($(".OtherPOPdf .SubTotal").text()))* (parseFloat(OtherDiscounted) / 100);
					$(".OtherPOPdf .FinalTotal").text("").text("$"+fmoney(FinalTotal));
				}
				$(this).text(parseFloat(OtherDiscounted).toFixed(2)+"%");
			});

	$(document).on("blur","#view10 .SubTotal",function() {
		/*  alert(12) */ 
				var CascadePODiscounted = $("#view10 .Discounted").text();
				var FinalTotal = 0;
				var S = $("#view10 .SubTotal").text();
				if(S.indexOf('￥')<0){
					var CascadePOSubTotal = rmoney($("#view10 .SubTotal").text())*1;
				}else{
					var CascadePOSubTotal = rmoney($("#view10 .SubTotal").text().substring(1))*1;
				}
				console.log(CascadePOSubTotal)
				if(parseFloat(CascadePODiscounted)=="0" || parseFloat(CascadePODiscounted)=="" ){
					$("#view10 .FinalTotal").text("").text('￥'+fmoney(CascadePOSubTotal));
				}else{
					FinalTotal = rmoney(fmoney(CascadePOSubTotal))* (parseFloat(CascadePODiscounted) / 100);
					console.log(FinalTotal)
					$("#view10 .FinalTotal").text("").text('￥'+fmoney(FinalTotal));
				}
				$(this).text('￥'+fmoney(CascadePOSubTotal));
			});

	$(document).on("blur",".hidePdf10 .ConflgBlur",function() {
				var FinalTotal = 0;
				var OtherDiscounted = $(".hidePdf10 .Discounted").text();
				if(parseFloat(OtherDiscounted) == 0 ||  parseFloat(OtherDiscounted) ==""){
					$(".hidePdf10 .FinalTotal").text("").text(fmoney($(".hidePdf10 .SubTotal").text()));
				}else{
					FinalTotal = rmoney(fmoney($(".hidePdf10 .SubTotal").text()))* (parseFloat(OtherDiscounted) / 100);
					$(".hidePdf10 .FinalTotal").text("").text("￥"+fmoney(FinalTotal));
				}
				$(this).text(parseFloat(OtherDiscounted).toFixed(2)+"%");
			});

	//计算总数
	$(document).on("blur","#view8 .ConfigBlur",function() {
		/* alert("进来1了") */
			var SubTotal = 0;
			var Discounted = $('#view8 .Discounted').text();
			console.log(parseFloat(Discounted));
			for (var i = 0; i < $('#view8 .CascadePOItemTr').length; i++) {
				var QtyNum = $('#view8 .CascadePOItemTr').eq(i).find(".Qty").text();
				var UnitPriceNum = rmoney(fmoney($('#view8 .CascadePOItemTr').eq(i).find(".UnitPrice").text()));
				var ExtendedPrice = parseFloat(QtyNum)* parseFloat(UnitPriceNum);
				$("#view8 .ExtendedPrice").eq(i).text("").text(fmoney(ExtendedPrice.toFixed(2)));
				SubTotal += ExtendedPrice;
			}
			$("#view8 .SubTotal").text("").text(fmoney(SubTotal.toFixed(2)));
			if(parseFloat(Discounted) == 0 || parseFloat(Discounted) == ''){
				/* alert(111) */
				$("#view8 .FinalTotal").text("").text(fmoney(SubTotal.toFixed(2)));
			}else{
				/* alert(222) */
				$("#view8 .FinalTotal").text("").text(fmoney(SubTotal * parseFloat(Discounted)/100));
			}
			$(this).text(parseFloat(Discounted).toFixed(2)+"%");
		})
		$(document).on("blur","#view89 .ConfigBlur",function() {
		/* alert("进来1了") */
			var SubTotal = 0;
			var Discounted = $('#view89 .Discounted').text();
			console.log(parseFloat(Discounted));
			for (var i = 0; i < $('#view89 .CascadePOItemTr').length; i++) {
				var QtyNum = $('#view89 .CascadePOItemTr').eq(i).find(".Qty").text();
				var UnitPriceNum = rmoney(fmoney($('#view89 .CascadePOItemTr').eq(i).find(".UnitPrice").text()));
				var ExtendedPrice = parseFloat(QtyNum)* parseFloat(UnitPriceNum);
				$("#view89 .ExtendedPrice").eq(i).text("").text(fmoney(ExtendedPrice.toFixed(2)));
				SubTotal += ExtendedPrice;
			}
			$("#view89 .SubTotal").text("").text(fmoney(SubTotal.toFixed(2)));
			if(parseFloat(Discounted) == 0 || parseFloat(Discounted) == ''){
				/* alert(111) */
				$("#view89 .FinalTotal").text("").text(fmoney(SubTotal.toFixed(2)));
			}else{
				/* alert(222) */
				$("#view89 .FinalTotal").text("").text(fmoney(SubTotal * parseFloat(Discounted)/100));
			}
			$(this).text(parseFloat(Discounted).toFixed(2)+"%");
		})
	$(document).on("blur","#view9 .ConfigBlur",function() {
		/* alert("进来2了") */
			var SubTotal = 0;
			var Discounted = $('#view9 .Discounted').text();
			for (var i = 0; i < $('#view9 .OtherPOItemTr').length; i++) {
				var QtyNum = $('#view9 .OtherPOItemTr').eq(i).find(".Qty").text();
				console.log(QtyNum)
				var UnitPriceNum = rmoney(fmoney($('#view9 .OtherPOItemTr').eq(i).find(".UnitPrice").text()));
				console.log(UnitPriceNum)
				var ExtendedPrice = parseFloat(QtyNum)* parseFloat(UnitPriceNum);
				console.log(ExtendedPrice)
				$("#view9 .ExtendedPrice").eq(i).text("").text(fmoney(ExtendedPrice.toFixed(2)));
				SubTotal += ExtendedPrice;
			}
			$("#view9 .SubTotal").text("").text(fmoney(SubTotal.toFixed(2)));
			if(parseFloat(Discounted) == 0 || parseFloat(Discounted) == ''){
				/* alert(333) */
				$("#view9 .FinalTotal").text("").text(fmoney(SubTotal.toFixed(2)));
			}else{
				/* alert(444) */
				$("#view9 .FinalTotal").text("").text(fmoney(SubTotal * parseFloat(Discounted)/100));
			}
			$(this).text(parseFloat(Discounted).toFixed(2)+"%");
		})
	$(document).on("blur",".hidePdf10 .ConfigBlur",function() {
		/* alert("进来3了") */
			var SubTotal = 0;
			var Discounted = $('.hidePdf10 .Discounted').text();
			for (var i = 0; i < $('.hidePdf10 .OtherPOItemTr').length; i++) {
				var QtyNum = $('.hidePdf10 .OtherPOItemTr').eq(i).find(".Qty").text();
				var UnitPriceNum = rmoney(fmoney($('.hidePdf10 .OtherPOItemTr').eq(i).find(".UnitPrice").text()));
				var ExtendedPrice = parseFloat(QtyNum)* parseFloat(UnitPriceNum);
				SubTotal += ExtendedPrice;
				
				$(".hidePdf10 .ExtendedPrice").eq(i).text("").text(fmoney(ExtendedPrice.toFixed(2)));
			}
			console.log(SubTotal);
			$(".hidePdf10 .SubTotal").text("").text(fmoney(SubTotal.toFixed(2)));
			if(parseFloat(Discounted) == 0 || parseFloat(Discounted) == ''){
				/* alert(555) */
				$(".hidePdf10 .FinalTotal").text("").text(fmoney(SubTotal.toFixed(2)));
			}else{
				/* alert(666) */
				$(".hidePdf10 .FinalTotal").text("").text(fmoney(SubTotal * parseFloat(Discounted)/100));
			}
			$(this).text(parseFloat(Discounted).toFixed(2)+"%");
		})
	/* 整机  合同 计算价格 */
		$(document).on("blur","#view1 .TotalRMB",function() {
			/* alert(80) */
			BlurChange($("#view1 .TotalRMB"),$("#view1 .GiftsTotal"),$("#view1 .ShipmentInsuranceRMB"),$("#view1 .TotalIncludingVATRMB"))
		})	
		$(document).on("blur","#view3 .TotalRMB",function() {
			BlurChange($("#view3 .TotalRMB"),$("#view3 .GiftsTotal"),$("#view3 .ShipmentInsuranceRMB"),$("#view3 .TotalIncludingVATRMB"))
		})
		$(document).on("blur","#view1 .GiftsTotal",function() {
			BlurChange($("#view1 .GiftsTotal"),$("#view1 .TotalRMB"),$("#view1 .ShipmentInsuranceRMB"),$("#view1 .TotalIncludingVATRMB"))
		})
		$(document).on("blur","#view2 .TotalUSD",function() {
			BlurChange($("#view2 .TotalUSD"),$("#view2 .GiftsTotal"),$("#view2 .ShipmentInsuranceUSD"),$("#view2 .FinalTotalCIPUSD"))
		})
		
		$(document).on("blur","#view4 .TotalUSD",function() {
			BlurChange($("#view4 .TotalUSD"),$("#view4 .GiftsTotal"),$("#view4 .ShipmentInsuranceUSD"),$("#view4 .FinalTotalCIPUSD"))
		})
		$(document).on("blur","#view2 .GiftsTotal",function() {
			BlurChange($("#view2 .GiftsTotal"),$("#view2 .TotalUSD"),$("#view2 .ShipmentInsuranceUSD"),$("#view2 .FinalTotalCIPUSD"))
		})
		$(document).on("blur","#view3 .GiftsTotal",function() {
			BlurChange($("#view3 .GiftsTotal"),$("#view3 .TotalRMB"),$("#view3 .ShipmentInsuranceRMB"),$("#view3 .TotalIncludingVATRMB"))
		})
		$(document).on("blur","#view4 .GiftsTotal",function() {
			BlurChange($("#view4 .GiftsTotal"),$("#view4 .TotalUSD"),$("#view4 .ShipmentInsuranceUSD"),$("#view4 .FinalTotalCIPUSD"))
		})
		function  BlurChange(blurDom,changeDom1,changeDom2,total){
			var changeDomText = 0;
			 
			if(blurDom.text() == "" || blurDom.text() == " "){
				 changeDomText = 0;
				 var totalText = changeDomText +parseFloat(changeDom1.text().indexOf(",") ? changeDom1.text().replace(/,/g,'') : changeDom1.text()) + parseFloat(changeDom2.text().indexOf(",") ? changeDom2.text().replace(/,/g,'') : changeDom2.text());
			}
			else if(changeDom1.text() == "" || changeDom1.text() == " "){
				changeDomText = 0;
				 var totalText = changeDomText +parseFloat(blurDom.text().indexOf(",") ? blurDom.text().replace(/,/g,'') : blurDom.text()) + parseFloat(changeDom2.text().indexOf(",") ? changeDom2.text().replace(/,/g,'') : changeDom2.text());
			}
			else{
				 var totalText = parseFloat(blurDom.text().indexOf(",") ? blurDom.text().replace(/,/g,'') : blurDom.text()) +parseFloat(changeDom1.text().indexOf(",") ? changeDom1.text().replace(/,/g,'') : changeDom1.text()) +parseFloat(changeDom2.text().indexOf(",") ? changeDom2.text().replace(/,/g,'') : changeDom2.text());
			}
				/* total.text(fmoney(totalText.toFixed(2))); */
			total.text(Math.round(totalText));
		}
//OtherPO文档显示
	//OtherPO 删除
	$(document).on("click","#view9 .OtherPOItemBtn",function() {
		console.log($(this).parent().parent().parent().find("td").eq(0).attr("value"))
		var delID = $(this).parent().parent().parent().find("td").eq(0).attr("value");
		var that = $(this).parent().parent().parent();
						
		/* $.ajax({
			type : 'get',
			url : "QuoteOtherDelete",
			data : {
				ID : delID,
			},
			dataType : 'json',
			success : function(data) {
				console.log(data); */
		that.remove();
			//计算other部分的总价
			 if($("#view9 .OtherPOItemTr").length > 0 ){
				 var totalNum = 0;
					for (var i = 0; i < $("#view9 .OtherPOItemTr").length; i++) {
						totalNum += ($("#view9 .OtherPOItemTr").eq(i).find(".Qty").text())* rmoney(fmoney($("#view9 .OtherPOItemTr").eq(i).find(".UnitPrice").text()));
					}
			}else{
				var totalNum = 0;
				
			} 
			console.log("totalNum"+totalNum)
			$("#view9 .SubTotal").text("").text(fmoney(totalNum.toFixed(2))); 
			$("#view9 .FinalTotal").text("").text(fmoney(totalNum.toFixed(2)));
	})
	
	downloadClick = function(node) {
         var event = new MouseEvent("click");
         node.dispatchEvent(event);
     }
     
	//小眼睛
	$(".contract_sendPO1 span.passeye").click(function(){
        if ($(".contract_sendPO1 .Password").attr("type") == "text") {
            $(".contract_sendPO1 .Password").attr("type","password");  
        }else{
          $(".contract_sendPO1 .Password").attr("type","text"); 
        }   
    });
     
</script>
</html>