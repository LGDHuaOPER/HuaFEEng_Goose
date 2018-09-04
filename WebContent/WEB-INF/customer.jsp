<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>客户信息</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" href="css/global/eouluCustom.css" type="text/css">
<link rel="stylesheet" type="text/css" href="css/info_customer.css">
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
			<%-- <form id="top_text_from" name="top_text_from" method="post" action="Customer">
				<div class="select-content">
					<div class="select">
						客户名称<input type="text" id="Contect1" name="content" value="${content }" style="margin-left: 5px; border:solid 1px #000;">
					</div>
					<div class="select-button">
						<input type="button" value="搜索" class="bToggle" onclick="Search()">
						<input type="button" value="取消" class="bToggle" onclick="Cancel()">
					</div>
				</div>
				
			</form> --%>
			
			
			<form id="top_text_from" name="top_text_from" method="post" action="GetCustomerInfo2">
				<input type="text" id="search" name="isSearch" value="search" style="display: none;">
				<input type="hidden" id="hideQuery"  value="${query}">
				<div class="select-content">
					<label> <c:choose>
							<c:when test="${query=='mix'}">
								<label><input type="radio" id="single"
									name="query" class="single" value="single"
									onclick="Check(this.value)">单一查询 </label>&nbsp;&nbsp;&nbsp;<label>
									<input type="radio" id="mix" name="query"
									value="mix" checked="checked" onclick="Check(this.value)">组合查询
								</label>&nbsp;&nbsp;&nbsp;<br>
							</c:when>
							<c:otherwise>
								<label><input type="radio" id="single" name="query" class="single" value="single"
									checked="checked" onclick="Check(this.value)">单一查询 </label>&nbsp;&nbsp;&nbsp;<label>
									<input type="radio" id="mix" name="query"
									value="mix" onclick="Check(this.value)">组合查询
								</label>&nbsp;&nbsp;&nbsp;<br>
							</c:otherwise>
						</c:choose> <c:set var="dropdown"
							value="${fn:split('客户名称,联系人,联系方式1,联系方式2,客户区域,电子邮箱',',')}"></c:set>
						<div class="select1">
							<select name="Type1" id="Type1">
								<c:forEach items="${dropdown }" var="dropdownList1" varStatus="status">
									<c:choose>
										<c:when test="${dropdownList1==Type1}">
											<option selected="selected">${dropdownList1}</option>
										</c:when>
										<c:otherwise>
											<option>${dropdownList1}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
							 <input type="text" id="Contect1" name="Contect1" value="${Contect1}">
						</div>
						
						<div class="select2" style="display: none">
							<select name="Type2" id="Type2">
								<c:forEach items="${dropdown }" var="dropdownList2"
									varStatus="status">
									<c:choose>
										<c:when test="${dropdownList2==Type2}">
											<option selected="selected">${dropdownList2}</option>
										</c:when>
										<c:otherwise>
											<option>${dropdownList2}</option>
										</c:otherwise>	
									</c:choose>
								</c:forEach>
							</select>
							<input type="text" id="Contect2" name="Contect2" value="${Contect2}">
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
				<tr style="background:#ccc;">
					<td style="width:4.3%;">序号</td>
					<td style="width:14.2%;">客户名称</td>
					<td style="width:6.0%;">客户部门</td>
					<td style="width:6.0%;">联系人</td>
					<td style="width:9.0%;">联系方式1</td>
					<td style="width:9.0%;">联系方式2</td>
					<td style="width:17.0%;">联系地址</td>
					<td style="width:14.0%;">电子邮箱</td>
					<!-- <td style="width:8.2%;">客户区域</td> -->
					<td style="width:4.0%;">省级</td>
					<td style="width:4.0%;">市级</td>
					<td style="width:6.9%;">客户等级</td>
					
					<td style="display:none;">客户类别</td>
					<td style="display:none;">邮政编码</td>
					<td style="display:none;">传真号码</td>
					<td style="display:none;">速记编码</td>
					<td style="display:none;">网站</td>
					<td style="display:none;">客户部门英文名称</td>
					<td style="display:none;">客户英文名称</td>
					<td style="width:6.9%;min-width: 70px;">开票信息</td>
				</tr>
				<c:forEach var="customer" items="${customers}" varStatus="status">
				<c:if test="${status.index>0 }">
				            <tr>
				                <td class="edit" value="${customer.ID}">${status.index+(currentPage-1)*10}</td>
				                <td class="CustomerName" title="${customer.CustomerName}">${customer.CustomerName}</td>
				                <td class="CustomerDepartment" title="${customer.CustomerDepartment}">${customer.CustomerDepartment}</td>
				                <td class="Contact" title="${customer.Contact}">${customer.Contact}</td> <!-- 4 -->
				                <td class="ContactInfo1" title="${customer.ContactInfo1}">${customer.ContactInfo1}</td>
				                <td class="ContactInfo2" title="${customer.ContactInfo2}">${customer.ContactInfo2}</td>
				                <td class="ContactAddress" title="${customer.ContactAddress}">${customer.ContactAddress}</td>
				                 <td class="Email" title="${customer.Email}">${customer.Email}</td>
				                 <td class="Area" title="${customer.Area}">${customer.Area}</td>
				                 <td class="City" title="${customer.City}">${customer.City}</td>
				                 <td class="CustomerLevel" title="${customer.CustomerLevel}">${customer.CustomerLevel}</td>
				                 
				                <td class="CustomerClassify" style="display:none;">${customer.CustomerClassify}</td>
				                <td class="ZipCode" style="display:none;">${customer.ZipCode}</td>
				                <td class="FaxNumber" style="display:none;">${customer.FaxNumber}</td>
				                <td class="ShorthandCoding" style="display:none;">${customer.ShorthandCoding}</td>
				                <td class="Website" style="display:none;">${customer.Website}</td>
				                <td class="DepartmentEnglish" style="display:none;">${customer.DepartmentEnglish}</td>
				                <td style="display:none;" class="EnglishName">${customer.EnglishName}</td>
				                <td class="invoiceInfo_td">开票信息</td>
				            </tr>
				</c:if>
				</c:forEach>
			</table>
			<div class="cover-color" style="display: none;"></div>
			<div class="info_add" style="display: none;">
				<div class="title">添加信息</div>
				<div class="infoAdd_close">关闭</div>
				<div class="basic_info">
						<input type="hidden" name="id">
						 <div class="table_title" style="color:#7dcbf4;margin:20px 0px;">客户信息</div>
			                <!-- 修改添加 -->
			                <div class="table_body" style="margin-top:15px;margin-bottom:60px;">
			                	<div class="table_col1" style="float:left;width:24%;min-width:24%;padding-right:3%;padding-left:2%;">
			                		<span style="float:left;margin-right:6%">客户名称</span>
			                        <span><input type="text" name="customer_name" class="input_css"></span>
			                        <br>
			                        <span style="float:left;margin-left: -7%;">客户英文名称</span>
			                        <span><input type="text" name="EnglishName" class="input_css"></span>
			                        <br>
			                        <span style="float:left;margin-right:6%">客户等级</span>
									 <span>
			                         	<select style="width:66%;height:25px;margin-bottom:20px;" name="CustomerLevel"  id="CustomerLevel">
			                        	 	<option text="请选择">请选择</option>
										 	<option value="A" >A</option>
											<option value="B" >B</option>
											<option value="C" >C</option>
										</select>
			                         </span>
									</span>
			                        <br>
			                        <span style="float:left;margin-right:19.3%">市级</span>
			                         <span><input type="text" name="City" class="input_css"></span>
			                        <br>
			                        <span style="float:left;margin-right:19.3%">省级</span>
			                         <span><input type="text" name="area" class="input_css" ></span>
			                        <br>
			                        
			                	</div>
			                	<div  class="table_col2" style="float:left;width:28%;min-width:28%;padding-left:5%;padding-right:3%;border-left:1px solid #dadada;border-right:1px solid #dadada;">
			                		<span style="float:left;margin-right:14%">联系人</span>
			                        <span><input type="text" name="contact"  class="input_css"></span>
			                        <br>
			                        <span style="float:left;margin-right:5%">联系方式1</span>
			                         <span><input type="text" name="contact_info1" class="input_css"></span>
			                        <br>
			                        <span style="float:left;margin-right:5%">联系方式2</span>
			                         <span><input type="text" name="contact_info2" class="input_css"></span>
			                        <br>
			                        <span style="float:left;margin-right:8%">联系地址</span>
			                         <span><input type="text" name="contact_address" class="input_css"></span>
			                        <br>
			                         <span style="float:left;margin-right:8%">客户部门</span>
			                         <span><input type="text" name="customer_department"  class="input_css"></span>
			                        <br>
			                       
			                    </div>
			                	<div  class="table_col3" style="float:left;width:28%;min-width:28%;padding-left:4%;">
			                		<span style="float:left;margin-right:5%">邮政编码</span>
			                        <span><input type="text" name="zip_code" class="input_css"></span>
			                        <br>
			                        <span style="float:left;margin-right:5%">传真号码</span>
			                         <span><input type="text" name="fax_number" class="input_css"></span>
			                        <br>
			                        <span style="float:left;margin-right:5%">电子邮箱</span>
			                         <span><input type="text" name="email" class="input_css"></span>
			                        <br>
			                        <span style="float:left;margin-right:16%">网址</span>
			                         <span><input type="text" name="Website" class="input_css"></span>
			                        <br>
			                        <span style="float:left;margin-left:-15%;">客户部门英文名称</span>
			                         <span><input type="text" name="Englishcustomer_department" class="input_css" ></span>
			                        <br>
			                        </div>
			                </div>
			        </div>
				<div class="edit_btn">
					<input type="button" value="提交" class="bToggle" id="add_submit">
					<input type="button" value="取消" class="bToggle" id="add_cancel">
				</div>
			</div>
			<div class="info_update" style="display: none;">
				<div class="title">修改信息</div>
				<div class="update_close">关闭</div>
				<div class="basic_info">
						<input type="hidden" name="id">
						 <div class="table_title" style="color:#7dcbf4;margin:20px 0px;">客户信息</div>
			                <!-- 修改添加 -->
			                <div class="table_body" style="margin-top:15px;margin-bottom:60px;">
			                	<div class="table_col1" style="float:left;width:24%;min-width:24%;padding-right:3%;padding-left:2%;">
			                		<span style="float:left;margin-right:6%">客户名称</span>
			                        <span><input type="text" name="customer_name" class="input_css"></span>
			                        <br>
			                        <span style="float:left;margin-left: -7%;">客户英文名称</span>
			                        <span><input type="text" name="EnglishName" class="input_css"></span>
			                        <br>
			                        <span style="float:left;margin-right:6%">客户等级</span>
									 <span>
			                         	<select style="width:66%;height:25px;margin-bottom:20px;" name="CustomerLevel"  id="CustomerLevel">
			                        	 	<option text="请选择">请选择</option>
										 	<option value="A" >A</option>
											<option value="B" >B</option>
											<option value="C" >C</option>
										</select>
			                         </span>
									</span>
			                        <br>
			                        <span style="float:left;margin-right:19.3%">市级</span>
			                         <span><input type="text" name="City" class="input_css"></span>
			                        <br>
			                         <span style="float:left;margin-right:19.3%">省级</span>
			                         <span><input type="text" name="area" class="input_css" ></span>
			                        <br>
			                	</div>
			                	<div  class="table_col2" style="float:left;width:28%;min-width:28%;padding-left:5%;padding-right:3%;border-left:1px solid #dadada;border-right:1px solid #dadada;">
			                		<span style="float:left;margin-right:14%">联系人</span>
			                        <span><input type="text" name="contact"  class="input_css"></span>
			                        <br>
			                        <span style="float:left;margin-right:5%">联系方式1</span>
			                         <span><input type="text" name="contact_info1" class="input_css"></span>
			                        <br>
			                        <span style="float:left;margin-right:5%">联系方式2</span>
			                         <span><input type="text" name="contact_info2" class="input_css"></span>
			                        <br>
			                        <span style="float:left;margin-right:8%">联系地址</span>
			                         <span><input type="text" name="contact_address" class="input_css"></span>
			                        <br>
			                         <span style="float:left;margin-right:8%">客户部门</span>
			                         <span><input type="text" name="customer_department"  class="input_css"></span>
			                        <br>			                    </div>
			                	<div  class="table_col3" style="float:left;width:28%;min-width:28%;padding-left:4%;">
			                		<span style="float:left;margin-right:5%">邮政编码</span>
			                        <span><input type="text" name="zip_code" class="input_css"></span>
			                        <br>
			                        <span style="float:left;margin-right:5%">传真号码</span>
			                         <span><input type="text" name="fax_number" class="input_css"></span>
			                        <br>
			                        <span style="float:left;margin-right:5%">电子邮箱</span>
			                         <span><input type="text" name="email" class="input_css"></span>
			                        <br>
			                        <span style="float:left;margin-right:16%">网址</span>
			                         <span><input type="text" name="Website" class="input_css"></span>
			                        <br>
			                        <span style="float:left;margin-left:-15%;">客户部门英文名称</span>
			                         <span><input type="text" name="Englishcustomer_department" class="input_css" ></span>
			                        <br>
			                        </div>
			                </div>
			        </div>
				<div class="edit_btn">
					<input type="button" value="提交" class="bToggle" id="update_submit">
					<input type="button" value="取消" class="bToggle" id="update_cancel">
				</div>
			</div>
			
			<!-- 开票信息表格内点击弹出 -->
			<!-- 弹出组件 -->
			<div class="invoice_tshow" style="display: none;">
			    <div class="invoice_tshow_tit">
			        <div class="invoice_tshow_tit_l">显示开票信息</div>
			        <div class="invoice_tshow_tit_r">关闭</div>
			    </div>
			    <div class="invoice_tshow_body">
			        <div class="invoice_tshow_body_in">
			            <div class="invoice_tshow_body_main">
							<table class="eou-table-collapse customer_info_table" id="invoice_tableshow">
								<tbody>
									<tr class="tr_01">
										<td>开票抬头：</td>
										<td class="invo_InvoiceTitle">请填写</td>
									</tr>
									<tr class="tr_02">
										<td>纳税人识别号：</td>
										<td class="invo_TaxPayerIdentityNO">请填写</td>
									</tr>
									<tr class="tr_03">
										<td>注册地址：</td>
										<td class="invo_RegisterAddress">请填写</td>
									</tr>
									<tr class="tr_04">
										<td>电话：</td>
										<td class="invo_Telephone">请填写</td>
									</tr>
									<tr class="tr_05">
										<td>开户行：</td>
										<td class="invo_DepositBank">请填写</td>
									</tr>
									<tr class="tr_06">
										<td>账号：</td>
										<td class="invo_Account">请填写</td>
									</tr>
									<tr class="tr_07">
										<td>发票收件人：</td>
										<td class="invo_InvoiceRecepter">请填写</td>
									</tr>
									<tr class="tr_08">
										<td>联系人地址：</td>
										<td class="invo_LinkAddress">请填写8</td>
									</tr>
									<tr class="tr_09">
										<td>联系人电话：</td>
										<td class="invo_LinkTel">请填写</td>
									</tr>
									<tr class="tr_010">
										<td>联系人邮编：</td>
										<td class="invo_LinkZipCode">请填写</td>
									</tr>
								</tbody>
							</table>
			            </div>
			        </div>
			    </div>
			    <div class="invoice_tshow_foot">
			        <div class="invoice_tshow_foot_in">
			            <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" id="invoice_tshow_submit" value="保存">
			            <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" id="invoice_tshow_close" value="取消">
			        </div>
			    </div>
			</div>

			<%-- <c:set var="queryUrl" value="Customer?content=${content}&currentPage="></c:set> --%>
			<c:choose>
				<c:when test="${query == null}">
					<c:set var="queryUrl"
					value="Customer?Type1=${Type1}&Contect1=${Contect1}&query=${query}&currentPage=">
					</c:set>
				</c:when>
				<c:otherwise>
					<c:set var="queryUrl"
					value="GetCustomerInfo2?Type1=${Type1}&Contect1=${Contect1}&Type2=${Type2}&Contect2=${Contect2}&query=${query}&currentPage=">
					</c:set>
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
</body>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/swiper-3.4.1.jquery.min.js" type="text/javascript" charset="utf-8"></script>
<!-- <script src="js/ajaxfileupload.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/global/ProvinceandCity.js" type="text/javascript" charset="utf-8"></script>
<script src="js/msgbox.js"></script>
<!-- <script src="js/msgbox_unload.js"></script> -->
<!-- <script src="js/global/myFunction.js"></script> -->
<script src="js/info_customer.js"></script>
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
		if($("#hideQuery").attr("value") == "mix"){
			$(".select2").show();
		}
		else{
			$(".select2").hide();
		}
	});
</script>
</html>

