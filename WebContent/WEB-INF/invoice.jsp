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
<title>发票页面</title>
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
<link rel="stylesheet" type="text/css" href="css/invoice.css">
</head>
<body>
	<!-- 	头部开始 -->
	<%@include file="top.jsp"%>
	<!-- 	头部结束 -->
	<div class="contain">
		<div class="content">
		<!-- 	=======================导航栏   开始 ================================== -->
		<%@include file="nav.jsp"%>

			<!-- 查询功能 -->
			<div style="display: none">
				<input type="text" value="" name="query_type"> 
				<input type="text" value="" name="classify1"> 
				<input type="text" value="" name="classify2"> 
				<input type="text" value="" name="parameter1"> 
				<input type="text" value="" name="parameter2">
			</div>

			<form id="top_text_from" name="top_text_from" method="post"
				action="GetInvoiceRoute">
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
							value="${fn:split('合同名称,合同号,发票号,Applicant',',')}"></c:set>
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
					<input type="button" value="添加" class="bToggle"
						onclick="AddContract()">
<!-- 						导出功能                   开始                                                                       -->
<!-- 											 <input type="button" value="导出" -->
<!-- 											class="bToggle" id="export"> -->
										<!-- <input type="button" value="导入" class="bToggle" id="upload">  -->
											
<!-- 						导出功能                    结束                                                                     -->
				</div>

			</form>
			<table border="1" cellspacing="0" cellspadding="0" id="table1">
				<tr>
					<td>序号</td>
					<td  style="display:none;">修改</td>
					<td>客户名称 </td>
					<td>合同名称</td>
					<td>合同号</td>
					<td>发票号</td>
					<td style="width: 300px;">Applicant</td>
					<td>货物名称</td>
					<td style="width: 250px;max-width:250px">模板预览（可导出）</td>
					<!-- <td>导出pdf</td>	 -->
					<td style="display:none;">DC No</td>
					<td style="display:none;">Add</td>
					<td style="display:none;">The End User</td>
					<td style="display:none;">Other reference</td>
					<td style="display:none;">Departure date</td>
					<td style="display:none;">Vessel/flight	</td>
					<td style="display:none;">From</td>
					<td style="display:none;">To</td>
					<td style="display:none;">Packing</td>
					<td style="display:none;">Country of Origin</td>
					<td style="display:none;">Manufacturer</td>
					<td style="display:none;">Shipping Mark	</td>
					<td style="display:none;">Description of Goods</td>
					<td style="display:none;">Unit</td>
					<td style="display:none;">Qty</td>
					<td style="display:none;">Unit price(USD)</td>
					<td style="display:none;">Total amount(USD)</td>
					<td style="display:none;">Date</td>
					<td style="display:none;">HS&R </td>	
					<td style="display:none;">TelFax </td>
					<td style="display:none;">${invoiceCounts}</td><!--30 -->
				</tr>
				<c:forEach var="orderInfo" items="${invoice}" varStatus="status">
					<c:if test="${status.index>0}">
						<tr class="tbody_tr">
							<td value="${orderInfo['ID']}" class="contract-edit" style="cursor:pointer">${status.index+(currentPage-1)*10}</td>
							<td  style="display:none;"> <i class="fa fa-edit" value="${orderInfo['ID']}"></i></td>
							<td title="${orderInfo['CustomerName']}" style="width:160px;">${orderInfo['CustomerName']}</td>
							<td title="${orderInfo['ContractTitle']}" style="width:160px;">${orderInfo['ContractTitle']}</td>
							<td title="${orderInfo['ContractNO']}" style="width:100px;">${orderInfo['ContractNO']}</td>
							<td title="${orderInfo['InvoiceNO']}" style="width:100px;">${orderInfo['InvoiceNO']}</td>
							<td title="${orderInfo['Applicant']}" style="width:300px;">${orderInfo['Applicant']}</td>
							<td title="${orderInfo['Product']}" style="width:160px;">${orderInfo['Product']}</td>
							<td><i class="fa fa-eye contract-show" value="${orderInfo['ID']}"></i></td>
							<!-- <td><i class="fa fa-download " id="exportPDF"></i></td> -->
							<td style="display:none;">${orderInfo['DCNO']}</td>    <!--9  -->
							<td style="display:none;">${orderInfo['AddInfo']}</td>
							<td style="display:none;">${orderInfo['EndUser']}</td>
							<td style="display:none;">${orderInfo['OtherReference']}</td>
							<td style="display:none;">${orderInfo['DepartureDate']}</td><!--13  -->
							<td style="display:none;">${orderInfo['Vessel']}</td>
							<td style="display:none;">${orderInfo['Departure']}</td>
							<td style="display:none;">${orderInfo['Destination']}</td><!--16  -->
							<td style="display:none;">${orderInfo['Packing']}</td>
							<td style="display:none;">${orderInfo['Origin']}</td>
							<td style="display:none;">${orderInfo['Manufacturer']}</td>
							<td style="display:none;">${orderInfo['ShippingMark']}</td><!--20  -->
							<td style="display:none;">${orderInfo['Product']}</td>
							<td style="display:none;">${orderInfo['Unit']}</td>
							<td style="display:none;">${orderInfo['Qty']}</td><!--23  -->
							<td style="display:none;">${orderInfo['TotalUSDAmount']}</td>
							<td style="display:none;">${orderInfo['Date']}</td>
							<td style="display:none;">${orderInfo['PaymentRemark']}</td>
							<td style="display:none;">${orderInfo['Type']}</td>  <!--27  -->
							<td style="display:none;">${orderInfo['PONO']}</td>
							<td style="display:none;">${orderInfo['TelFax']}</td><!--29  -->
							<td style="display:none;">${orderInfo['UnitUSDPrice']}</td><!--30  -->
							<td style="display:none;">${orderInfo['NinePaid']}</td><!--31 -->
							<td style="display:none;">${orderInfo['TenPaid']}</td><!--32 -->
							<td style="display:none;">${orderInfo['AirPort']}</td><!--33 -->
							<td style="display:none;">${orderInfo['TotalAmount']}</td><!--34 -->
							<%-- <td style="display:none;">${orderInfo['itemID']}</td><!--  --> --%>
							
						</tr>
					</c:if>
				</c:forEach>
			</table>	
			
			
		<!-- 底部翻页 -->
		
		<c:choose>
				<c:when test="${queryType == 'common'}">
					<c:set var="queryUrl"
					value="Invoice?type1=${classify1 }&searchContent1=${parameter1}&selected=${str}&currentPage=">
					</c:set>
				</c:when>
				<c:otherwise>
					<c:set var="queryUrl"
					value="GetInvoiceRoute?type1=${classify1 }&searchContent1=${parameter1}&type2=${classify2}&searchContent2=${parameter2}&selected=${str}&currentPage=">
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
						name="fistPage" onclick="FistPage('${queryUrl}')"> <input
						type="button" class="bToggle" value="上一页" id="upPage"
						onclick="UpPage('${queryUrl}${currentPage-1 }')"> <input
						type="button" class="bToggle" value="下一页" id="nextPage"
						onclick="NextPage('${queryUrl}${currentPage+1 }')"> 跳到第 <input
						type="text" id="jumpNumber" name="jumpNumber" class="jumpNumber"
						style="width: 30px; color: #000"
						onblur="value=value.replace(/[^\d]/g,'') "> 页 <input
						type="button" class="bToggle" value="GO" id="Gotojump"
						name="Gotojump" onclick="PageJump('${queryUrl}')"> <input
						type="button" class="bToggle" value="尾页" id="lastPage"
						name="lastPage" onclick="LastPage('${queryUrl}')">
				</div>
			</div>
		</div>
	</div>
	<div class="MailBar_cover_color"></div>
	
	<!-- 添加发票信息 -->
	<div class="contract_add" style="display: none;">
		<div class="contract_title">添加发票信息</div>
		<div class="contractAdd_close">关闭</div>
		<div class="basic_info">
			<div class="table_title">发票基本信息</div>
			<table border="1" cellspacing="0" cellpadding="0"
				class="contract_basic">
				<tbody>
					<tr>
						<td>发票类型</td>
						<td><select name="area" class="InvoiceType">
							<option value="0" selected="selected">100%</option>
							<option value="1" >10%</option>
							<option value="2" >90%</option>
						</select></td>
						<td class="pono">PO NO.</td>
						<td class="pono"><input type="text" name="po_no" value=""  id="po_no"></td>
					</tr>
					<tr>	
						<td>CONTRACT NO.</td>
						<td><input type="text" name="contract_no" value=""  id="contract_no"></td>
						<td>INVOICE NO.</td>
						<td><input type="text" name="INVOICE_NO" value=""></td>
						<td>DC No.</td>
						<td><input type="text" name="DC_NO" value=""></td>
					</tr>
					<tr>
						<td>Applicant</td>
						<td>
							<input type="text" name="Applicant" value="" style="position:absolute;width:162px;" id="Applicant">
							<span>
								<select class="APPSelect">
									<option value="No"  selected="selected">--请选择--</option>
									<option value="100%">HOPE INVESTMENT DEVELOPMENT CORP., LTD.</option>
									<option value="10%" >CERNET CORP CERNET TOWER, TSINGHUA SCIENCE PARK </option>
									<option value="90%" >THE ORIENTAL SCIENTIFIC INSTRUMENT IMPORT AND EXPORT (GROUP) CORPORATION</option>
									<option value="10%" >HISILICON OPTOELECTRONICS CO., LIMITED </option>
									<option value="10%" >HISILICON TECHNOLOGIES CO., LIMITED </option>
									<option value="10%" >HUAWEI DEVICE(DONGGUAN)CO., LTD.</option>
									<option value="10%" >XIAMEN XINDECO LTD.</option>
									<option value="10%" >CHINA NATIONAL INSTRUMENTS IMPORT AND EXPORT (GROUP) CORPORATION</option>
								</select>
							</span>
							
						</td>
						<td class="hideTD">Add</td>
						<td class="hideTD">
							<input type="text" name="ADD" value="" style="position:absolute;width:162px;" id="ADD">
							<span>
								<select onchange="document.getElementById('ADD').value=this.options[this.selectedIndex].text"  class="ADDSelect">
									<option value="No" selected="selected">--请选择--</option>
									<option value="GERMANY">CETC MANSION, No.5 WULUTONG NORTH STREET, XICHENG DISTRICT, BEIJING, CHINA, 100120</option>
									<option value="USA" >BUILDING 8, NO.1 ZHONGGUANCUN EAST ROAD, HAIDIAN DISTRICT BEIJING 100084, P.R. CHINA</option>
									<option value="GERMANY">14/F, YINDU BUILDING, NO.67 FU CHENG ROAD,HAIDIAN DISTRICT, BEIJING </option>
									<option value="USA" >NO.999 GAOXIN ROAD,DONGHU NEW TECHNOLOGY DEVELOPMENT DISTRICT,WU HAN, HUBEI PROVINCE</option>
									<option value="GERMANY">HUAWEI BASE, BANTIAN, LONGGANG DISTRICT, SHENZHEN, P.R.CHINA</option>
									<option value="USA" >B2-5 OF NANFANG FACTORY, NO.2 OF XINCHENG ROAD, SONGSHAN LAKE SCIENCE AND TECHNOLOGY INDUSTRIAL ZONE, DONGGUAN, GUANGDONG, CHINA</option>
									<option value="GERMANY">7/F, XINDECO BLDG., NO.27,XINGLONG ROAD, HULI, XIAMEN, CHINA</option>
									<option value="USA" >RM615, INSTRIMPEX BUILDING, NO.6, XIZHIMENWAI STREET, BEIJING CHINA</option>
								</select>
							</span>
						</td>
						<td  class="hideTD">TEL/FAX</td>
						<td  class="hideTD">
							<input type="text" name="TEL" value="" style="position:absolute;width:162px;" id="TEL">
							<span>
								<select onchange="document.getElementById('TEL').value=this.options[this.selectedIndex].text" class="TELSelect">
									<option value="No" selected="selected">--请选择--</option>
									<option value="GERMANY">TEL:(+86)10 82209151&&FAX: (+86)10 82209359</option>
									<option value="USA" >TEL: +86-(0)21-64272922&&FAX: +86-(0)21-64277370</option>
									<option value="GERMANY">TEL: 86-10-68725599&&FAX: 86-10-68726610</option>
									<option value="USA" >TEL:0086-027-59266808&&FAX: 0086-027-59745721</option>
									<option value="GERMANY">TEL: +86 755 28780808&&FAX: +86 755 28357515</option>
									<option value="USA" >TEL: 0086-027-59266808&&FAX: 0086-027-59745721</option>
									<option value="GERMANY">TEL: 0086-592-5620655&&FAX: 0086-592-6021752</option>
									<option value="USA" >TEL: 0086-10-88316230/0086-13810416767&&FAX: 0086-10-88316233</option>
								</select>
							</span>	
						</td>
					</tr>
					<tr>
						<td>The End User</td>
						<td><input type="text" name="TheEndUser" value=""></td>
						<td>Other reference</td>
						<td><input type="text" name="OtherReference" value=""></td>
						<td>Departure date</td>
						<td><input type="date" name="Departure_date" value=""></td>
					</tr>
					<tr>
						<td>Vessel/flight </td>
						<td><input type="text" name="Vessel_flight" value=""></td>
						<td>From</td>
						<td>
							<input type="text" name="From" value="" style="position:absolute;width:162px;" id="From">
							<span>
								<select onchange="document.getElementById('From').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="100%" >GERMANY MAIN AIRPORT</option>
									<option value="10%" >HONG KONG MAIN AIRPORT</option>
									<option value="90%" >USA  MAIN AIRPORT</option>
									<option value="90%" >HONG KONG</option>
								</select>
							</span>
						</td>
						<td>To</td>
						<td>
							<input type="text" name="To" value="" style="position:absolute;width:162px;" id="To">
							<span>
								<select onchange="document.getElementById('To').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="100%" >BEIJING AIRPORT, CHINA</option>
									<option value="10%" >CHENGDU AIRPORT, CHINA</option>
									<option value="90%" >HONGKONG AIRPORT, CHINA</option>
									<option value="90%" >SHENZHEN AIRPORT, CHINA</option>
									<option value="90%" >SHANGHAI AIRPORT, CHINA</option>
									<option value="90%" >XIAMEN AIRPORT, CHINA</option>
									<option value="90%" >XIAMEN AIRPORT, CHINA</option>
									<option value="90%" >GUANGZHOU AIRPORT, CHINA</option>
								</select>
							</span>
						</td>
					</tr>
					<tr>
						<td>Packing</td>
						<td>
							<input type="text" name="Packing" value="" style="position:absolute;width:162px;" id="Packing">
							<span>
								<select onchange="document.getElementById('Packing').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="Pack" >STANDARD EXPORT PACKING SUITABLE FOR LONG-DISTANCE TRANSPORTATION</option>
								</select>
							</span>
						</td>
						<td>Country of Origin </td>
						<td>
							<input type="text" name="CountryofOrigin" value="" style="position:absolute;width:162px;" id="CountryofOrigin">
							<span>
								<select onchange="document.getElementById('CountryofOrigin').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="GERMANY">GERMANY</option>
									<option value="USA" >USA</option>
									<option value="HK" >HONG KONG</option>
								</select>
							</span>
							
						</td>
						<td>Manufacturer</td>
						<td>
							<input type="text" name="Manufacturer" value="" style="position:absolute;width:162px;" id="Manufacturer" >
							<span>
								<select onchange="document.getElementById('Manufacturer').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="CASCADE MICROTECH">CASCADE MICROTECH</option>
									<option value="EOULU">EOULU</option>
								</select>
							</span>
						</td>
					</tr>
					<tr>
						<td>Shipping Mark No.</td>
						<td><input type="text" name="ShippingMarkNo" value=""></td>
						<td>Shipping Mark ADD</td>
						<td><input type="text" name="ShippingMarkAdd" value=""></td>
					</tr>
					<tr class="all" >
						<td>CIP AIRPORT</td>
						<td><input type="text" name="AIRPORT" value=""></td>
						<td>TOTAL AMOUNT USD</td>
						<td><input type="text" name="USD" value=""  onblur = "this.value=outputmoney(this.value);" ></td>
						<td  class="TenPercent" style="display:none;" >10% VALUE PAID USD</td>
						<td  class="TenPercent" style="display:none;" ><input type="text" name="tenPaid" value=""  onblur="this.value=outputmoney(this.value);" ></td>
						<td  class="NinetyPercent" style="display:none;" > 90% VALUE PAID USD:</td>
						<td  class="NinetyPercent" style="display:none;" ><input type="text" name="ninePaid" value=""  onblur = "this.value=outputmoney(this.value);" ></td>
					</tr>
					<tr>
						<td>Date</td>
						<td><input type="date" name="installed_time" value=""></td>
						<td>HS No/Terms of delivery and payment/Remark</td>
						<td>
							<input type="text" name="remarks"  style="width:220px;height:22px;position:absolute;" id="remarks" value="" >
							<span >
								<select onchange="document.getElementById('remarks').value=this.options[this.selectedIndex].text" style="width:243px;" >
									<option value="No" selected="selected">--请选择--</option>
									<option value="NA">NA</option>
									<option value="Data"  style="word-break:break-all">100% LC,90PCT OF CONTRACT VALUE SHOULD BE PAID AGAINST THE REQUIRED DOCUMENTS;10PCT OF CONTRACT VALUE SHOULD BE PAID AGAINST MANUALLY SIGNED COMMERCIAL INVOICE AND CERTIFICATE OF ACCEPTANCE TEST SIGNED BY THE APPLICANT AND THE BENEFICIARY.</option>
								</select>
							</span>
						</td>
					</tr>
					<tr>
						<td>Item</td>
						<td><input type="text" name="Applicant" value="1"></td>
						<td><span style="color:red;">*</span>Description of Goods</td>
						<td class="GoodsTd">
							<input type="text" name="Goods" value="" style="position:absolute;width:162px;" id="Goods1">
							<span>
								<select class="GoodsSel">
									<option value="No" selected="selected">--请选择--</option>
									<option value="SUMMIT12000B-S">SUMMIT12000B-S_</option>
									<option value="EPS150LT">EPS150LT_</option>
									<option value="SUMMIT12000B-M">SUMMIT12000B-M_</option>
									<option value="SUMMIT11000B-M">SUMMIT11000B-M_</option>
									<option value="T200-STA-M">T200-STA-M_</option>	
									<option value="PA200DS-BR-011">PA200DS-BR-011_</option>
									<option value="PA200-BR-021">PA200-BR-021_</option>
									<option value="EPS150MMW">EPS150MMW_</option>
									<option value="CM300">CM300_</option>										
								</select>
							</span>
							
						</td>
						<td>Unit</td>
						<td  class="UnitTd">
							<input type="text" name="Unit" value="SET" style="position:absolute;width:162px;" id="Unit1">
							<span>
								<select  class="UnitSel" >
									<option value="No" selected="selected">--请选择--</option>
									<option value="SET">SET</option>
								</select>
							</span>
						</td>
					</tr>
					<tr>	
						<td><span style="color:red;">*</span>Qty</td>
						<td><input type="text" name="Qty" value="" class="QtyTd" ></td>
						<td><span style="color:red;">*</span>Unit price(USD)</td>
						<td><input type="text" name="UnitPrice" value=""   class="UnitPriceTd"  onblur = "this.value=outputmoney(this.value);" ></td>
						<td><span style="color:red;">*</span>Total amount(USD)</td>
						<td><input type="text" name="TotalAmount" value=""  class="TotalAmountTd"  onblur = "this.value=outputmoney(this.value);" ></td>
					</tr>
					
				</tbody>
			</table>
			<div class="table_title"> <input type="button" name="addItem" value="添加Item项" class="bToggle addItem"> </div>
		<!-- 	<table>
			
			</table> -->
		</div>
		<div class="edit_btn">
			<input type="button" value="提交" class="bToggle" id="add_submit">
			<input type="button" value="取消" class="bToggle" id="add_cancel">
		</div>
	</div>
	
	
	
	<!-- 修改发票信息 -->
	<div class="contract_update" style="display: none;">
		<div class="contract_title">修改发票信息</div>
		<div class="contractUpdate_close">关闭</div>
		<div class="basic_info">
			<div class="table_title">发票基本信息</div>
			<table border="1" cellspacing="0" cellpadding="0"
				class="contract_basic">
				<tbody>
					<tr>
						<td>发票类型</td>
						<td><select name="area" class="InvoiceType">
							<option value="0" selected="selected">100%</option>
							<option value="1" >10%</option>
							<option value="2" >90%</option>
						</select></td>
						<td class="pono">PO NO.</td>
						<td class="pono"><input type="text" name="po_no" value=""  id="po_no1"></td>
					</tr>
					<tr>	
						<td>CONTRACT NO.</td>
						<td><input type="text" name="contract_no" value=""  id="contract_no1"></td>
						<td>INVOICE NO.</td>
						<td><input type="text" name="INVOICE_NO" value=""></td>
						<td>DC No.</td>
						<td><input type="text" name="DC_NO" value=""></td>
					</tr>
					<tr>
						<td>Applicant</td>
						<td>
							<input type="text" name="Applicant" value="" style="position:absolute;width:162px;" id="Applicant1">
							<span>
								<select class="APPSelect">
									<option value="No"  selected="selected">--请选择--</option>
									<option value="100%">HOPE INVESTMENT DEVELOPMENT CORP., LTD.</option>
									<option value="10%" >CERNET CORP CERNET TOWER, TSINGHUA SCIENCE PARK </option>
									<option value="90%" >THE ORIENTAL SCIENTIFIC INSTRUMENT IMPORT AND EXPORT (GROUP) CORPORATION</option>
									<option value="10%" >HISILICON OPTOELECTRONICS CO., LIMITED </option>
									<option value="10%" >HISILICON TECHNOLOGIES CO., LIMITED </option>
									<option value="10%" >HUAWEI DEVICE(DONGGUAN)CO., LTD.</option>
									<option value="10%" >XIAMEN XINDECO LTD.</option>
									<option value="10%" >CHINA NATIONAL INSTRUMENTS IMPORT AND EXPORT (GROUP) CORPORATION</option>
								</select>
							</span>
							
						</td>
						<td class="hideTD">Add</td>
						<td class="hideTD">
							<input type="text" name="ADD" value="" style="position:absolute;width:162px;" id="ADD1">
							<span>
								<select onchange="document.getElementById('ADD1').value=this.options[this.selectedIndex].text"  class="ADDSelect">
									<option value="No" selected="selected">--请选择--</option>
									<option value="GERMANY">CETC MANSION, No.5 WULUTONG NORTH STREET, XICHENG DISTRICT, BEIJING, CHINA, 100120</option>
									<option value="USA" >BUILDING 8, NO.1 ZHONGGUANCUN EAST ROAD, HAIDIAN DISTRICT BEIJING 100084, P.R. CHINA</option>
									<option value="GERMANY">14/F, YINDU BUILDING, NO.67 FU CHENG ROAD,HAIDIAN DISTRICT, BEIJING </option>
									<option value="USA" >NO.999 GAOXIN ROAD,DONGHU NEW TECHNOLOGY DEVELOPMENT DISTRICT,WU HAN, HUBEI PROVINCE</option>
									<option value="GERMANY">HUAWEI BASE, BANTIAN, LONGGANG DISTRICT, SHENZHEN, P.R.CHINA</option>
									<option value="USA" >B2-5 OF NANFANG FACTORY, NO.2 OF XINCHENG ROAD, SONGSHAN LAKE SCIENCE AND TECHNOLOGY INDUSTRIAL ZONE, DONGGUAN, GUANGDONG, CHINA</option>
									<option value="GERMANY">7/F, XINDECO BLDG., NO.27,XINGLONG ROAD, HULI, XIAMEN, CHINA</option>
									<option value="USA" >RM615, INSTRIMPEX BUILDING, NO.6, XIZHIMENWAI STREET, BEIJING CHINA</option>
								</select>
							</span>
						</td>
						<td  class="hideTD">TEL/FAX</td>
						<td  class="hideTD">
							<input type="text" name="TEL" value="" style="position:absolute;width:162px;" id="TEL1">
							<span>
								<select onchange="document.getElementById('TEL1').value=this.options[this.selectedIndex].text" class="TELSelect">
									<option value="No" selected="selected">--请选择--</option>
									<option value="GERMANY">TEL:(+86)10 82209151&&FAX: (+86)10 82209359</option>
									<option value="USA" >TEL: +86-(0)21-64272922&&FAX: +86-(0)21-64277370</option>
									<option value="GERMANY">TEL: 86-10-68725599&&FAX: 86-10-68726610</option>
									<option value="USA" >TEL:0086-027-59266808&&FAX: 0086-027-59745721</option>
									<option value="GERMANY">TEL: +86 755 28780808&&FAX: +86 755 28357515</option>
									<option value="USA" >TEL: 0086-027-59266808&&FAX: 0086-027-59745721</option>
									<option value="GERMANY">TEL: 0086-592-5620655&&FAX: 0086-592-6021752</option>
									<option value="USA" >TEL: 0086-10-88316230/0086-13810416767&&FAX: 0086-10-88316233</option>
								</select>
							</span>	
						</td>
					</tr>
					<tr>
						<td>The End User</td>
						<td><input type="text" name="TheEndUser" value=""></td>
						<td>Other reference</td>
						<td><input type="text" name="OtherReference" value=""></td>
						<td>Departure date</td>
						<td><input type="date" name="Departure_date" value=""></td>
					</tr>
					<tr>
						<td>Vessel/flight </td>
						<td><input type="text" name="Vessel_flight" value=""></td>
						<td>From</td>
						<td>
							<input type="text" name="From" value="" style="position:absolute;width:162px;" id="From1">
							<span>
								<select onchange="document.getElementById('From1').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="100%" >GERMANY MAIN AIRPORT</option>
									<option value="10%" >HONG KONG MAIN AIRPORT</option>
									<option value="90%" >USA  MAIN AIRPORT</option>
									<option value="90%" >HONG KONG</option>
								</select>
							</span>
						</td>
						<td>To</td>
						<td>
							<input type="text" name="To" value="" style="position:absolute;width:162px;" id="To1">
							<span>
								<select onchange="document.getElementById('To1').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="100%" >BEIJING AIRPORT, CHINA</option>
									<option value="10%" >CHENGDU AIRPORT, CHINA</option>
									<option value="90%" >HONGKONG AIRPORT, CHINA</option>
									<option value="90%" >SHENZHEN AIRPORT, CHINA</option>
									<option value="90%" >SHANGHAI AIRPORT, CHINA</option>
									<option value="90%" >XIAMEN AIRPORT, CHINA</option>
									<option value="90%" >XIAMEN AIRPORT, CHINA</option>
									<option value="90%" >GUANGZHOU AIRPORT, CHINA</option>
								</select>
							</span>
						</td>
					</tr>
					<tr>
						<td>Packing</td>
						<td>
							<input type="text" name="Packing" value="" style="position:absolute;width:162px;" id="Packing1">
							<span>
								<select onchange="document.getElementById('Packing1').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="Pack" >STANDARD EXPORT PACKING SUITABLE FOR LONG-DISTANCE TRANSPORTATION</option>
								</select>
							</span>
						</td>
						<td>Country of Origin </td>
						<td>
							<input type="text" name="CountryofOrigin" value="" style="position:absolute;width:162px;" id="CountryofOrigin1">
							<span>
								<select onchange="document.getElementById('CountryofOrigin1').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="GERMANY">GERMANY</option>
									<option value="USA" >USA</option>
									<option value="HK" >HONG KONG</option>
								</select>
							</span>
							
						</td>
						<td>Manufacturer</td>
						<td>
							<input type="text" name="Manufacturer" value="" style="position:absolute;width:162px;" id="Manufacturer1" >
							<span>
								<select onchange="document.getElementById('Manufacturer1').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="CASCADE MICROTECH">CASCADE MICROTECH</option>
									<option value="EOULU">EOULU</option>
								</select>
							</span>
						</td>
					</tr>
					<tr>
						<td>Shipping Mark No.</td>
						<td><input type="text" name="ShippingMarkNo" value=""></td>
						<td>Shipping Mark ADD</td>
						<td><input type="text" name="ShippingMarkAdd" value=""></td>
					</tr>
					<tr class="all" >
						<td>CIP AIRPORT</td>
						<td><input type="text" name="AIRPORT" value=""></td>
						<td>TOTAL AMOUNT USD</td>
						<td><input type="text" name="USD" value=""  onblur = "this.value=outputmoney(this.value);" ></td>
						<td  class="TenPercent" style="display:none;" >10% VALUE PAID USD</td>
						<td  class="TenPercent" style="display:none;" ><input type="text" name="tenPaid" value=""  onblur = "this.value=outputmoney(this.value);" ></td>
						<td  class="NinetyPercent" style="display:none;" > 90% VALUE PAID USD:</td>
						<td  class="NinetyPercent" style="display:none;" ><input type="text" name="ninePaid" value=""  onblur = "this.value=outputmoney(this.value);" ></td>
					</tr>
					<tr class="theLast">
						<td>Date</td>
						<td><input type="date" name="installed_time" value=""></td>
						<td>HS No/Terms of delivery and payment/Remark</td>
						<td>
							<input type="text" name="remarks"  style="width:220px;height:22px;position:absolute;" id="remarks1" value="" >
							<span >
								<select onchange="document.getElementById('remarks1').value=this.options[this.selectedIndex].text" style="width:243px;" >
									<option value="No" selected="selected">--请选择--</option>
									<option value="NA">NA</option>
									<option value="Data"  style="word-break:break-all">100% LC,90PCT OF CONTRACT VALUE SHOULD BE PAID AGAINST THE REQUIRED DOCUMENTS;10PCT OF CONTRACT VALUE SHOULD BE PAID AGAINST MANUALLY SIGNED COMMERCIAL INVOICE AND CERTIFICATE OF ACCEPTANCE TEST SIGNED BY THE APPLICANT AND THE BENEFICIARY.</option>
								</select>
							</span>
						</td>
					</tr>
	<!-- 				<tr>
						<td>Item</td>
						<td><input type="text" name="Applicant" value="1"></td>
						<td>Description of Goods</td>
						<td class="GoodsTd">
							<input type="text" name="Goods" value="" style="position:absolute;width:162px;" id="Goods1">
							<span>
								<select class="GoodsSel">
									<option value="No" selected="selected">--请选择--</option>
									<option value="SUMMIT12000B-S">SUMMIT12000B-S_</option>
									<option value="EPS150LT">EPS150LT_</option>
									<option value="SUMMIT12000B-M">SUMMIT12000B-M_</option>
									<option value="SUMMIT11000B-M">SUMMIT11000B-M_</option>
									<option value="T200-STA-M">T200-STA-M_</option>	
									<option value="PA200DS-BR-011">PA200DS-BR-011_</option>
									<option value="PA200-BR-021">PA200-BR-021_</option>
									<option value="EPS150MMW">EPS150MMW_</option>
									<option value="CM300">CM300_</option>										
								</select>
							</span>
							
						</td>
						<td>Unit</td>
						<td  class="UnitTd">
							<input type="text" name="Unit" value="SET" style="position:absolute;width:162px;" id="Unit1">
							<span>
								<select  class="UnitSel" >
									<option value="No" selected="selected">--请选择--</option>
									<option value="SET">SET</option>
								</select>
							</span>
						</td>
					</tr>
					<tr>	
						<td>Qty</td>
						<td><input type="text" name="Qty" value="" class="QtyTd" ></td>
						<td>Unit price(USD)</td>
						<td><input type="text" name="UnitPrice" value=""   class="UnitPriceTd"  onblur = "this.value=outputmoney(this.value);" ></td>
						<td>Total amount(USD)</td>
						<td><input type="text" name="TotalAmount" value=""  class="TotalAmountTd"  onblur = "this.value=outputmoney(this.value);" ></td>
					</tr> -->
					
				</tbody>
			</table>
			<div class="table_title"> <input type="button" name="addItem" value="添加Item项" class="bToggle addItem"> </div>
		<!-- 	<table>	
			
			</table> -->
		</div>
		<div class="edit_btn">
			<input type="button" value="提交" class="bToggle" id="update_submit">
			<input type="button" value="取消" class="bToggle" id="update_cancel">
		</div>
	</div>
	
	<div class="hidePdf" style="display:none;">

				<!-- 右侧信息栏  显示-->
				
				<div id="view" class="news" style="font-family: initial;position:absolute;z-index:11;top:0;left:0;background:#fff;font-size:12px">
					<!-- 页眉-->
			<!-- 	<div class="yemei" style="margin-bottom:20px; margin-top: 20px;">
				<div style="width:100%;height:70px;">
					<div class="logo lf">
						<img src="image/EOULUlogo.png" style="width: 175px; height: 61px;">
					</div>
					<div class="rt" style="margin-top:40px;color: #000080;">EOULU Technology</div>
				</div>
					
					<hr>
				</div> -->
				<table cellpadding="0" cellspacing="0" border="1" style=" width:90%;   margin-bottom: 90px;"id="invoice_table" >
							<tr>
								<td colspan="6"  style="text-align: center;font-size: 30px;" class="titleTd">COMMERCIAL INVOICE</td>   <!--这里存储itemId  -->
							</tr>
							<tr>
								<td colspan="3" class="pl saveId" style="line-height: 21px;">  <!--这里存储Id  -->
									<p  >
										<b class="s14">Seller</b><br/>
										HK EOULU TRADING LIMITED<br/>
										ROOM 1501 GRAND MILLENNIUM PLAZA (LOWER BLOCK)<br/> 181 QUEEN'S ROAD CENTRAL, HONG KONG<br/>
										TEL: 00852-21527388<br/>
										FAX: 00852-35719160
									</p>
								</td>
								<td colspan="3" class="pl vtop"  style="line-height: 21px;">
									<b>Invoice No. and Date</b><br/>
									CONTRACT NO.:<span contenteditable="true" class="outlineNone ContractNO"> GNHCHNHI17033111LXJ</span><br/>
									PO: <span contenteditable="true" class="outlineNone PONO">910071239</span><br/>
									INVOICE NO. : <span contenteditable="true" class="outlineNone InvoiceNO">E100913</span><br/>
									DC No.:<span contenteditable="true" class="outlineNone DCNO"> NA</span>
								</td>
							</tr>
							<tr>
								<td colspan="3" rowspan="2" class="pl"  style="line-height: 21px;">
									<b class="s14">Applicant</b><br/>
									<span contenteditable="true" class="outlineNone App">HiSilicon Technologies CO., LIMITED</span><br/>
									ADD:<span contenteditable="true" class="outlineNone App_Add" >Huawei Base, Bantian,Longgang District, Shenzhen, P.R.China</span><br/>
									TEL:<span contenteditable="true" class="outlineNone App_TEL" > +86 755 28780808</span><br/>
									FAX:<span contenteditable="true" class="outlineNone App_FAX" > +86 755 28357515</span>
								</td>
								<td colspan="3" class="pl vtop">
									<b class="s14">The End User(if other than consignee)</b><br/>
									<span contenteditable="true" class="outlineNone endUser">NA</span>
								</td>
							</tr>
							<tr>
								<td colspan="3" class="pl vtop s14">Other reference<br/>
									<span contenteditable="true" class="outlineNone reference">NA</span></td>
							</tr>
							<tr>   
								<td colspan="3" class="pl dpDate" style="width:50%;line-height:25px;"><b class="s14">Departure date:</b><br/>				
									<p contenteditable="true" class="outlineNone tc DepartureDate">NA</p></td>									
								<td colspan="3" rowspan="3" class="pl vtop"  style="line-height:25px;">
								<dl>
									<dt class="b s14">HS No/Terms of delivery and payment/Remark</dt>
									<dd  contenteditable="true" class="outlineNone Remark" >NA</dd>
								</dl>
								<!-- <p class="b s14"></p>								
								<p contenteditable="true" class="outlineNone Remark" style="width:45%;">NA</p> -->
								</td>
							</tr>
							<tr>
								<td colspan="3" class="pl" style="line-height:25px;">
								<p style="width:100%;height:60%;">
								<span class="b s14" style="float: left;width: 50%;">Vessel/flight</span>
								<span class="lf b s14">From</span>  
								</p>
								<p contenteditable="true" class="outlineNone">
								<span style="float: left;width: 50%;" class="Vessel">SK5502</span>
								<span class="lf s12 VesselFrom">HONGKONG</span>
								</p>
								</td>
							</tr>
							<tr>
								<td colspan="3" class="pl s14" style="line-height:23px;"><b>To</b></br>
								 <p contenteditable="true" class="outlineNone s12 tc VesselTo">SHENZHEN</p></td>
							</tr>
							<tr style="text-align: center;" class="itemTol">
								<td>Item</td>
								<td>Description of Goods</td>
								<td>Unit</td>
								<td>Qty</td>
								<td>Unit price(USD)</td>
								<td>Total amount(USD)</td>
							</tr>
							<!-- <tr class="tc">
								<td>1</td>
								<td>
									<p contenteditable="true" class="outlineNone goodsName">Summit12000B-M<p>
									<p  contenteditable="true" class="goodsContent">PROBE STATION PLATFORM</p>
								</td>
								<td>
									<span contenteditable="true" class="outlineNone Unit">SET</span>
								</td >
								<td><span contenteditable="true" class="outlineNone Qty">1</span></td>
								<td><span contenteditable="true" class="outlineNone Unitprice">239,088</span></td>
								<td><span contenteditable="true" class="outlineNone TotalAmount">239,088</span></td>
							</tr> -->
							<tr>
								<td colspan="6" class="pl" style="line-height: 21px;">
									Packing:<span contenteditable="true" class="outlineNone Packing">  STANDARD EXPORT PACKING SUITABLE FOR LONG-DISTANCE TRANSPORTATION</span><br/>
									Country of Origin:<span contenteditable="true" class="outlineNone Origin">GERMANY</span><br/>
									Manufacturer:<span contenteditable="true" class="outlineNone Manufacturer"> CASCADE</span><br/>
									Shipping Mark::<span contenteditable="true" class="outlineNone Mark" style="text-decoration: underline;min-width:200px;display:inline-block;"> 16HJK065-MHK1236 </span><br/>
									<span style="width: 90px; height: 16px; display: inline-block;"></span><span contenteditable="true" class="outlineNone MarkAdd" style="display:inline-block;text-align:left;min-width:200px;">  BEIJING, CHINA</span>
								</td>
							</tr>
							<tr>
								<td class="b s14" colspan="5" style="text-align: right;">TOTAL AMOUNT (CIP <span contenteditable="true" class="outlineNone CIPADD">SHENZHEN</span>  AIRPORT) USD:</td>
								<td style="text-align: center;"><span contenteditable="true" class="outlineNone CIPUSD"></span></td>
							</tr>
							<tr style="display:none;" class="nineTr">
								<td class="b s14" colspan="5" style="text-align: right;">90% VALUE PAID USD:&nbsp;&nbsp;</td>
								<td style="text-align: center;"><span contenteditable="true" class="outlineNone ninePaid"></span></td>
							</tr>
							<tr style="display:none;" class="tenTr">
								<td class="b s14" colspan="5" style="text-align: right;">10% VALUE PAID USD:&nbsp;&nbsp;</td>
								<td style="text-align: center;"><span contenteditable="true" class="outlineNone tenPaid"></span></td>
							</tr>
							<tr>
								<td colspan="6" class="pl"  style="position:relative; line-height: 21px;" >
<pre style="font-family: -webkit-body;">Bank details
          Bank name              HSBC HONGKONG
          Bank code               004
          Bank address          1 Queen's Road Central, HongKong
          SWIFT Code            HSBCHKHHHKH
          Account name        HK EOULU TRADING LIMITED
          Account No            801-012469-838</pre>
          						 <img src="image/zhang2.png" contenteditable="true"  style="position:absolute;top:0;right:150px;display:inline-block;width:120px;height:120px;" class="InImg"/>
								</td>
							</tr>
							<tr style="width:50px;">
								<td colspan="6" class="pl">
								<p style="float:left;width: 10%;height:30px; margin-top: 15px;position:relative;">Signature:</p>
								<p style="width:60%;height:30px; position:relative;" contenteditable="true" class="outlineNone lf">
									 <img src="image/qm.jpg" style="position:absolute;top:0;left:0;display:inline-block;width:160px;height:45px;" class="InImg1"/>
								</p>
								<p style="float:left;width:30%;height:30px;margin-top: 15px;">
									Date:<span contenteditable="true" style="display:inline-block;" class="outlineNone invoiceDate">2017/02/03</span>
								</p>
								</td>
							</tr>
						</table>
				
						<!-- 页脚 -->
					<!-- <div class="yejiao" style="margin-top: 20px; height:80px;">
						<hr>
						<pre style="text-align: center;color: #000080;font-family: -webkit-body;">EOULU
Suzhou ● Shenzhen ● Beijing ● Shanghai ● HongKong
〡Phone: +86-512-62757360〡Web:www.eoulu.com〡Email:info@eoulu.com〡</pre>
						
					</div> -->
				</div>
			
			<!-- <input type="button" value="提交" class="bToggle" id="submit_n" style="position:absolute;z-index:11;top: 250px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
			 -->
			<input type="button" value="无章导出" class="bToggle ShowOrHide" id="exportPDF2" style="position:absolute;z-index:11;top: 200px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
			<input type="button" value="带章导出" class="bToggle ShowOrHide" id="exportPDF1" style="position:absolute;z-index:11;top: 150px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
			<input type="button" value="关闭" class="bToggle " id="contract_close1" style="position: absolute;z-index: 11;top: 100px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
	</div>
	<div id="ddddd">
		
	</div>
</body>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/msgbox.js"></script>
<!-- <script src="js/msgbox_unload.js"></script> -->
<!-- <script src="js/global/myFunction.js"></script> -->
<script src="js/global/responseLoading.js"></script>
<script src="js/invoice.js"></script>
<script type="text/javascript" src="js/html2canvas.js"></script>
<script type="text/javascript" src="js/jsPdf.debug.js"></script>
<script>
/* 修改发货通知 */
$(document).on("click",".contract-edit",function(){
	var tr=$(this).parent();
	$('.contract_update').find('input[name="po_no"]').val(tr.find('td').eq(28).text());
	$('.contract_update').find('input[name="contract_no"]').val(tr.find('td').eq(4).text());
	$('.contract_update').find('input[name="INVOICE_NO"]').val(tr.find('td').eq(5).text());
	$('.contract_update').find('input[name="DC_NO"]').val(tr.find('td').eq(9).text());
	$('.contract_update').find('input[name="Applicant"]').val(tr.find('td').eq(6).text());
	$('.contract_update').find('input[name="ADD"]').val(tr.find('td').eq(10).text());
	$('.contract_update').find('input[name="TEL"]').val(tr.find('td').eq(29).text());
	$('.contract_update').find('input[name="TheEndUser"]').val(tr.find('td').eq(11).text());
	$('.contract_update').find('input[name="OtherReference"]').val(tr.find('td').eq(12).text());
	$('.contract_update').find('input[name="Departure_date"]').val(tr.find('td').eq(13).text());//
	$('.contract_update').find('input[name="Vessel_flight"]').val(tr.find('td').eq(14).text());
	$('.contract_update').find('input[name="From"]').val(tr.find('td').eq(15).text()); //
	$('.contract_update').find('input[name="To"]').val(tr.find('td').eq(16).text());  //
	$('.contract_update').find('input[name="Packing"]').val(tr.find('td').eq(17).text());
	$('.contract_update').find('input[name="CountryofOrigin"]').val(tr.find('td').eq(18).text());
	$('.contract_update').find('input[name="Manufacturer"]').val(tr.find('td').eq(19).text());
	$('.contract_update').find('input[name="ShippingMarkNo"]').val(tr.find('td').eq(20).text().split("&&")[0]);
	$('.contract_update').find('input[name="ShippingMarkAdd"]').val(tr.find('td').eq(20).text().split("&&")[1]);
	$('.contract_update').find('input[name="AIRPORT"]').val(tr.find('td').eq(33).text());
	$('.contract_update').find('input[name="USD"]').val(Number(tr.find('td').eq(34).text()));  //
	$('.contract_update').find('input[name="installed_time"]').val(tr.find('td').eq(25).text());//
	$('.contract_update').find('input[name="remarks"]').val(tr.find('td').eq(26).text());
	
	$(".contract_update .hideTD").show();
	var  ID = tr.find('td').eq(0).attr("value");
	
	$(".contract_update .contract_title").attr("value",ID);  //在修改页面保存当前行的ID信息
	$(".contract_update .DIMTr,.contract_update .DIMTrNext").remove();
	$.ajax({
        type : 'get',
        url : 'GetOnlyInvoice',
        data : {
        	ID:ID
        },
        dataType : 'json',
        success : function (data) {
        	console.log(data)
        	var itemStr ="";
        	 for(var i = 1 ; i <data.length; i++ ){
        		 var GoodsCon = data[i].Goods;
        		 var Unitprice = fmoney(data[i].UnitUSDPrice,2)
        		 var TotalAmount = fmoney(data[i].TotalUSDAmount,2)
					
        			var sddStr = '<tr class="DIMTr DIMTrLen" value='+data[i].ID+'>'+
        			'<td>Item</td>'+
        			'<td><input type="text" name="Applicant" value="'+i+'"></td>'+
        			'<td><span style="color:red;">*</span>Description of Goods</td>'+
        			'<td class="GoodsTd">'+
        				'<input type="text" name="Goods" value="'+GoodsCon+'" style="position:absolute;width:162px;" class="GoodsCon">'+
        				'<span>'+
        				'	<select   class="GoodsSel" >'+
        						'<option value="No" selected="selected">--请选择--</option>'+
        						'<option value="SUMMIT12000B-S">SUMMIT12000B-S_</option>'+
        						'<option value="EPS150LT">EPS150LT_</option>'+
        						'<option value="SUMMIT12000B-M">SUMMIT12000B-M_</option>'+
        						'<option value="SUMMIT11000B-M">SUMMIT11000B-M_</option>'+
        						'<option value="T200-STA-M">T200-STA-M_</option>	'+
        						'<option value="PA200DS-BR-011">PA200DS-BR-011_</option>'+
        						'<option value="PA200-BR-021">PA200-BR-021_</option>'+
        						'<option value="EPS150MMW">EPS150MMW_</option>'+
        						'<option value="CM300">CM300_</option>	'+									
        					'</select>'+
        				'</span>'+
        			'</td>'+
        			'<td >Unit</td>'+
        			'<td class="UnitTd">'+
        				'<input type="text" name="Unit" value="'+data[i].Unit+'" style="position:absolute;width:162px;" class="UnitCon">'+
        				'<span>'+
        					'<select   class="UnitSel" >'+
        						'<option value="No" selected="selected">--请选择--</option>'+
        						'<option value="SET">SET</option>'+
        					'</select>'+
        				'</span>'+
        			'</td>'+
        		'</tr>'+
        		'<tr class="DIMTr">'+	
        		'<td><span style="color:red;">*</span>Qty</td>'+
        		'<td><input type="text" name="Qty" value="'+data[i].Qty+'"  class="QtyTd"></td>'+
        		'<td><span style="color:red;">*</span>Unit price(USD)</td>'+
        		'<td><input type="text" name="UnitPrice" value="'+Unitprice+'"  class="UnitPriceTd"   onblur = "this.value=outputmoney(this.value);"></td>'+
        		'<td><span style="color:red;">*</span>Total amount(USD)</td>'+
        		'<td><input type="text" name="TotalAmount" value="'+TotalAmount+'"  class="TotalAmountTd"   onblur = "this.value=outputmoney(this.value);"></td>'+
        	'</tr>';	
					
        	 $('.contract_update .contract_basic').append(sddStr);
        	} 
        	
        },
        error : function () {
        	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });  
    
   $('.MailBar_cover_color').show();
   $('.contract_update').show();
 });
 
/*  提交修改后的信息  */
$('#update_submit').click(function () {
	var area = $('.contract_update select[name="area"]').val(); 
	var PONO = $('.contract_update input[name="po_no"]').val();
	var ContractNO = $('.contract_update input[name="contract_no"]').val();
	var InvoiceNO = $('.contract_update input[name="INVOICE_NO"]').val();
	var DCNO = $('.contract_update input[name="DC_NO"]').val();
	var App = $('.contract_update input[name="Applicant"]').val();
	var App_Add = $('.contract_update input[name="ADD"]').val();
	var TelFax = $('.contract_update input[name="TEL"]').val();
	var TheEndUser = $('.contract_update input[name="TheEndUser"]').val();
	var OtherReference = $('.contract_update input[name="OtherReference"]').val();
	var DepartureDate = $('.contract_update input[name="Departure_date"]').val() ==""?"0000-00-00":$('.contract_update input[name="Departure_date"]').val();
	var Vessel = $('.contract_update input[name="Vessel_flight"]').val();
	var VesselFrom = $('.contract_update input[name="From"]').val();
	var VesselTo = $('.contract_update input[name="To"]').val();
	var Packing = $('.contract_update input[name="Packing"]').val();
	var Origin = $('.contract_update input[name="CountryofOrigin"]').val();
	var Manufacturer = $('.contract_update input[name="Manufacturer"]').val();
	var ShippingMark=$('.contract_update input[name="ShippingMarkNo"]').val() +"&&"+$('.contract_update input[name="ShippingMarkAdd"]').val();
	var AirPort = $('.contract_update input[name="AIRPORT"]').val();
	var USD = $('.contract_update input[name="USD"]').val();
	var installed_time = $('.contract_update input[name="installed_time"]').val() ==""?"0000-00-00":$('.contract_update input[name="installed_time"]').val();
	var remarks = $('.contract_update input[name="remarks"]').val();
 	// var ninePaid = $('.contract_update input[name="ninePaid"]').val() == "" ? 0 : $('.contract_update .ninePaid').val();
 	var ninePaid = $('.contract_update input[name="ninePaid"]').val() == "" ? 0 : $('.contract_update input[name="ninePaid"]').val();
	// var tenPaid = $('.contract_update input[name="tenPaid"]').val()== "" ? 0 : $('.contract_update .tenPaid').val();
	var tenPaid = $('.contract_update input[name="tenPaid"]').val()== "" ? 0 : $('.contract_update input[name="tenPaid"]').val();
	
	    var Goods = [];
	    var itemID = [];
	    var Qty=[];
	    var Unitprice=[];
	    var TotalAmount=[];
	    var Unit=[];
	    var Product="";
	    for(var i =0 ; i < $(".contract_update .DIMTrLen").length ; i++){
	    	var good=$(".contract_update .DIMTrLen").eq(i).find(".GoodsCon").val();
	    	Goods.push(good);
	    	Unit.push($(".contract_update .DIMTrLen").eq(i).find(".UnitCon").val())
	    	Qty.push($(".contract_update .DIMTrLen").eq(i).next().find(".QtyTd").val())
	    	var TotalAmountVal = $('.contract_update .DIMTrLen').eq(i).next().find(".TotalAmountTd").val();
	    	var UnitpriceVal = $('.contract_update .DIMTrLen').eq(i).next().find(".UnitPriceTd").val();
	    	if(TotalAmountVal.indexOf(",") >0){
	    		TotalAmount.push(parseFloat(TotalAmountVal.replace(/,/g,'')));
	    	}
	    	else{
	    		TotalAmount.push(parseFloat(TotalAmountVal));
	    	}
	    	if(UnitpriceVal.indexOf(",") >0){
	    		Unitprice.push(parseFloat(UnitpriceVal.replace(/,/g,'')));
	    	}
	    	else{
	    		Unitprice.push(parseFloat(UnitpriceVal));
	    	}
	    	var setItemID = $(".contract_update .DIMTrLen").eq(i).attr("value");
	    	if(!setItemID){setItemID=0}
	    	itemID.push(setItemID);
	    	Product = $(".contract_update .DIMTrLen").eq(0).find(".GoodsCon").val() ;
	    }
	    
	    var itemFlag = "yes";
	      if(itemID == "" || itemID == null){
	    	  itemFlag = "no";
	      }
	
	var  ID = $(".contract_update .contract_title").attr("value");
	//去空格处理
	
	PONO = Trim(PONO);
	ContractNO = Trim(ContractNO);
	InvoiceNO = Trim(InvoiceNO);
	DCNO = Trim(DCNO);
	
	  $.ajax({
	      // type : 'get',
	      type: 'POST',
	      url: 'ModifyInvoice',
	      data: {
	    	  ID:ID,
	    	  Type : area,
	    	  PONO : PONO,
              ContractNO : ContractNO,
              InvoiceNO : InvoiceNO,
              DCNO : DCNO,
              Applicant : App,
              Add : App_Add,
              TelFax:TelFax,
              EndUser : TheEndUser,
              OtherReference : OtherReference,
              DepartureDate : DepartureDate,
              Vessel : Vessel,
              Departure : VesselFrom,
              Destination : VesselTo,
              Packing : Packing,
              Origin : Origin,
              Manufacturer : Manufacturer,
              ShippingMark : ShippingMark,
              AirPort : AirPort,
              TotalAmount:USD,
              Date : installed_time,
              PaymentRemark : remarks,
              itemFlag:itemFlag,
              itemID:itemID,
              Goods :Goods,
              Qty:Qty,
              TotalUSDAmount : TotalAmount,
              Unit : Unit,
              UnitUSDPrice : Unitprice, 
              Product:Product,
              NinePaid:ninePaid,
	          TenPaid:tenPaid,               
	      },
	      dataType: 'text',
	      success: function (data) {
	      	if(data.indexOf("修改成功")>-1){
	      		$.MsgBox.Alert('提示','修改成功');
	      		$('.MailBar_cover_color').hide();
	      		$('.contract_update').hide();
	      	}else if(data.indexOf("修改失败")>-1){
	      		$.MsgBox_Unload.Alert("提示", "修改失败！请检查填写内容");
	      	}else{
	      		$.MsgBox_Unload.Alert("提示", "修改失败！非正常错误");
	      	}
	      },
	      error : function () {
	    	  $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	      }
	  }); 
})   

function FistPage(arg) {
	/* window.location.href = arg + "1"; */
	if(arg.split('?')[0]=='GetInvoiceRoute'){
		/* alert(arg.split('?')[0]); */
		 $('#search').val('search');
		 $("#top_text_from").attr("action", arg+ "1");
		  $('#top_text_from').submit();
	}else{ 
		
		window.location.href = arg+ "1";
	 } 
}
function UpPage(arg) {
	if(arg.split('?')[0]=='GetInvoiceRoute'){
		/* alert(arg.split('?')[0]); */
		 $('#search').val('search');
		 $("#top_text_from").attr("action", arg);
		  $('#top_text_from').submit();
	}else{ 
		
		window.location.href = arg;
	 } 
}
function NextPage(arg) {
	if(arg.split('?')[0]=='GetInvoiceRoute'){
		/*  alert(arg.split('?')[0]);  */
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
	if(arg.split('?')[0]=='GetInvoiceRoute'){
		 $('#search').val('search');
		 $("#top_text_from").attr("action", arg + jumpNumber);
		  $('#top_text_from').submit();
	}else{ 
		
		window.location.href = arg + jumpNumber;
	 } 
}
function LastPage(arg) {
	var jumpNumber = parseInt($('#allPage').html());
	if(arg.split('?')[0]=='GetInvoiceRoute'){
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

$(document).on("change",".GoodsSel",function(){

	$(this).parent().prev().val($(this).find("option:selected").text());
})
$(document).on("change",".UnitSel",function(){
	$(this).parent().prev().val($(this).find("option:selected").text());
})


var addStr ='<td><span style="color:red;">*</span>Description of Goods</td>'+
			'<td class="GoodsTd">'+
				'<input type="text" name="Goods" value="" style="position:absolute;width:162px;" class="GoodsCon">'+
				'<span>'+
				'	<select   class="GoodsSel" >'+
						'<option value="No" selected="selected">--请选择--</option>'+
						'<option value="SUMMIT12000B-S">SUMMIT12000B-S_</option>'+
						'<option value="EPS150LT">EPS150LT_</option>'+
						'<option value="SUMMIT12000B-M">SUMMIT12000B-M_</option>'+
						'<option value="SUMMIT11000B-M">SUMMIT11000B-M_</option>'+
						'<option value="T200-STA-M">T200-STA-M_</option>	'+
						'<option value="PA200DS-BR-011">PA200DS-BR-011_</option>'+
						'<option value="PA200-BR-021">PA200-BR-021_</option>'+
						'<option value="EPS150MMW">EPS150MMW_</option>'+
						'<option value="CM300">CM300_</option>	'+									
					'</select>'+
				'</span>'+
			'</td>'+
			'<td >Unit</td>'+
			'<td class="UnitTd">'+
				'<input type="text" name="Unit" value="SET" style="position:absolute;width:162px;"  class="UnitCon">'+
				'<span>'+
					'<select   class="UnitSel" >'+
						'<option value="No" selected="selected">--请选择--</option>'+
						'<option value="SET">SET</option>'+
					'</select>'+
				'</span>'+
			'</td>'+
			'</tr>'+
			'<tr  class="DIMTrNext">'+	
			'<td><span style="color:red;">*</span>Qty</td>'+
			'<td><input type="text" name="Qty" value=""  class="QtyTd"></td>'+
			'<td><span style="color:red;">*</span>Unit price(USD)</td>'+
			'<td><input type="text" name="UnitPrice" value="" class="UnitPriceTd" onblur = "this.value=outputmoney(this.value);"></td>'+
			'<td><span style="color:red;">*</span>Total amount(USD)</td>'+
			'<td><input type="text" name="TotalAmount" value="" class="TotalAmountTd" onblur = "this.value=outputmoney(this.value);"></td>'+
		'</tr>';

var addFlag = 1 ;
$(".contract_add .addItem").click(function(){
	addFlag++;	
	var AddStr = '<tr class="DIMTr DIMTrLen">'+
	'<td>Item</td>'+
	'<td><input type="text" name="Applicant" value="'+addFlag+'"></td>'+addStr;
	$(".contract_add .contract_basic").append(AddStr);
})
$(".contract_update .addItem").click(function(){
	var ItemLen = $(".contract_update .DIMTrLen").length+1;
	var AddStr = '<tr class="DIMTr DIMTrLen">'+
	'<td>Item</td>'+
	'<td><input type="text" name="Applicant" value="'+ItemLen+'"></td>'+addStr;
	$(".contract_update .contract_basic").append(AddStr);
})

$(document).on("click",".DelThisTr",function(){
	$(this).parent().parent().remove();
})

//页面提交
$(document).on("click","#submit_n",function(){
    /* var id=$('input[name="id"]').val(); */
    var PONO=$('.hidePdf .PONO').text();
    var ContractNO=$('.hidePdf .ContractNO').text();
    var InvoiceNO=$('.hidePdf .InvoiceNO').text();
    var DCNO=$('.hidePdf .DCNO').text();
    var App=$('.hidePdf .App').text();
    var App_Add=$('.hidePdf .App_Add').text();
    var App_TEL="TEL:" + $('.hidePdf .App_TEL').text();
    var App_FAX="FAX:" + $('.hidePdf .App_FAX').text();
    var endUser=$('.hidePdf .endUser').text();
    var reference=$('.hidePdf .reference').text();
    var DepartureDate=$('.hidePdf .DepartureDate').text()== "" ? "0000-00-00" : $('.hidePdf .DepartureDate').text();

    var Remark=$('.hidePdf .Remark').text();
    var Vessel=$('.hidePdf .Vessel').text();
    var VesselFrom=$('.hidePdf .VesselFrom').text();
    var VesselTo=$('.hidePdf .VesselTo').text();
   
    var CIPADD=$('.hidePdf .CIPADD').text();
    var Packing=$('.hidePdf .Packing').text();
    var Origin=$('.hidePdf .Origin').text();
    var Manufacturer=$('.hidePdf .Manufacturer').text();
    var Mark=$('.hidePdf .Mark').text();
    var MarkAdd=$('.hidePdf .MarkAdd').text();
    var CIPUSD=parseFloat($('.hidePdf .CIPUSD').text().replace(/,/g,''));
    var ninePaid=$('.hidePdf .ninePaid').text() == "" ? 0 : $('.hidePdf .ninePaid').text();
    var tenPaid=$('.hidePdf .tenPaid').text() == "" ? 0 : $('.hidePdf .tenPaid').text();
    var invoiceDate=$('.hidePdf .invoiceDate').text() ==""?"0000-00-00":$('.hidePdf .invoiceDate').text();
    var ID = $('.hidePdf .saveId').attr("val");

   console.log(CIPUSD);
    if($(".tenTr").css("display") == "none"&&$(".nineTr").css("display") == "none"){
    	var area = 0;
    }   
    else if($(".tenTr").css("display") == "none"&&$(".nineTr").css("display") != "none"){
    	var area = 2;
    }
    else if($(".tenTr").css("display") != "none"&&$(".nineTr").css("display") == "none"){
    	var area = 1;
    }
    var ShippingMark = Mark+"&&"+MarkAdd;
    var TelFax = App_TEL+"&&"+App_FAX;

    var Goods = [];
    var itemID = [];
    var Qty=[];
    var Unitprice=[];
    var TotalAmount=[];
    var Unit=[];
    var Product="";
    for(var i =0 ; i < $(".itemStr").length ; i++){
    	 console.log( $(".itemStr").length)
    	var goodsName=$(".itemStr").eq(i).find(".goodsName").text();
    	var goodsContent=$(".itemStr").eq(i).find(".goodsContent").text();
    	var good = goodsName+"_"+goodsContent;
    	Goods.push(good);
    	Unit.push($(".itemStr").eq(i).find(".Unit").text())
    	Qty.push($(".itemStr").eq(i).find(".Qty").text())
    	TotalAmount.push(parseFloat($('.itemStr').eq(i).find(".TotalAmount").text().replace(/,/g,'')));
    	Unitprice.push(parseFloat($('.itemStr').eq(i).find(".Unitprice").text().replace(/,/g,'')));
    	itemID.push($(".itemStr").eq(i).attr("value"))
    	Product = $(".itemStr").eq(0).find(".goodsName").text() +"_"+$(".itemStr").eq(i).find(".goodsContent").text();
    }
    var itemFlag = "yes";
      if(itemID == "" || itemID == null){
    	  itemFlag = "no";
      }
    	$.ajax({
            type : 'get',
            url : 'ModifyInvoice',
            data : {
            	ID:ID,
                ContractNO : ContractNO,
                AirPort : CIPADD,
                Applicant : App,
                Date : invoiceDate,
                DCNO : DCNO,
                Departure : VesselFrom,
                DepartureDate : DepartureDate,
                Destination : VesselTo,
                EndUser : endUser,
                InvoiceNO : InvoiceNO,
                Manufacturer : Manufacturer,
                Origin : Origin,
                OtherReference : reference,
                PaymentRemark : Remark,
                PONO : PONO,
                ShippingMark : ShippingMark,
                Type : area,
                Vessel : Vessel,
                Add : App_Add,
                TelFax:TelFax,
                Packing : Packing,
                itemFlag:itemFlag,
                itemID:itemID,
                Goods :Goods,
                Qty:Qty,
                TotalUSDAmount : TotalAmount,
                Unit : Unit,
                UnitUSDPrice : Unitprice, 
                Product:Product,
                NinePaid:ninePaid,
	            TenPaid:tenPaid,
	            TotalAmount:CIPUSD
            },
            dataType : 'json',
            success : function (data) {       	        	             	
            	$.MsgBox.Alert('提示','提交成功');
	            $('.MailBar_cover_color').hide();
	            $('.hidePdf').hide();
            },
            error : function () {
            	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
            }
        });
});

//点击添加
function AddContract() {
    $('.MailBar_cover_color').show();
    $('.contract_add .DIMTr,.contract_add .DIMTrNext').remove();
    $('.contract_add').show();
    $(".contract_add .hideTD").show();
};

$(function(){
	
	//--------------------------------------------模板预览页面-----------------------------------------------
	$('.contract-show').click(function () {
		$(".itemStr").remove();
		
		var ID = $(this).parent().parent().find("td").eq(0).attr("value");
    	$.ajax({
            type : 'get',
            url : 'GetOnlyInvoice',
            data : {
            	ID:ID
            },
            dataType : 'json',
            success : function (data) {
            	//console.log(data)
            	var itemStr ="";
            		 for(var i = 1 ; i < data.length; i++ ){
            		 var GoodsCon = data[i].Goods.split("_")[1] == undefined ? " " : data[i].Goods.split("_")[1];
            		 var Unitprice = fmoney(data[i].UnitUSDPrice,2)
            		 var TotalAmount = fmoney(data[i].TotalUSDAmount,2)
            		 itemStr +='<tr class="tc itemStr" style="line-height: 24px;" value='+data[i].ID+'>'+
							'<td>'+i+'</td>'+
							'<td>'+
								'<p contenteditable="true" class="outlineNone goodsName">'+data[i].Goods.split("_")[0]+'<p>'+
								'<p  contenteditable="true" class="goodsContent">'+GoodsCon+'</p>'+
							'</td>'+
							'<td>'+
								'<span contenteditable="true" class="outlineNone Unit">'+data[i].Unit+'</span>'+
							'</td >'+
							'<td><span contenteditable="true" class="outlineNone Qty">'+data[i].Qty+'</span></td>'+
							'<td><span contenteditable="true" class="outlineNone Unitprice">'+Unitprice+'</span></td>'+
							'<td><span contenteditable="true" class="outlineNone TotalAmount">'+TotalAmount+'</span></td>'+
						'</tr>';
            	} 
            	 $('.itemTol').after(itemStr);
            },
            error : function () {
            	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
            }
        });
	    $('.MailBar_cover_color').show();
	    var thisList = $(this).parent().parent().children();
	    $(".news .ContractNO").text("").text(thisList.eq(4).text());
	    $(".news .InvoiceNO").text("").text(thisList.eq(5).text());
	    $(".news .DCNO").text("").text(thisList.eq(9).text());
	    $(".news .App").text(thisList.eq(6).text());
	    $(".news .App_Add").text(thisList.eq(10).text());
	    $(".news .endUser").text(thisList.eq(11).text());
	    $(".news .reference").text(thisList.eq(12).text());
	    $(".news .DepartureDate").text(thisList.eq(13).text());
	    $(".news .Remark").text(thisList.eq(26).text());
	    $(".news .Vessel").text(thisList.eq(14).text());
	    $(".news .VesselFrom").text("").text(thisList.eq(15).text());
	    $(".news .VesselTo").text("").text(thisList.eq(16).text());
	    $(".news .goodsName").text("").text(thisList.eq(21).text().split("_")[0]);//
	    $(".news .goodsContent").text("").text(thisList.eq(21).text().split("_")[1]);//
	    $(".news .Unit").text("").text(thisList.eq(22).text());
	    $(".news .Qty").text("").text(thisList.eq(23).text());
	    $(".news .Unitprice").text("").text(thisList.eq(30).text());
	    $(".news .TotalAmount").text("").text(thisList.eq(24).text());
	    $(".news .Packing").text("").text(thisList.eq(17).text());
	    $(".news .Origin").text("").text(thisList.eq(18).text());
	    $(".news .Manufacturer").text("").text(thisList.eq(19).text());
	    $(".news .Mark").text("").text(thisList.eq(20).text().split("&&")[0]);//
	    $(".news .MarkAdd").text("").text(thisList.eq(20).text().split("&&")[1]);//
	    $(".news .CIPADD").text("").text(thisList.eq(33).text());//
	    $(".news .CIPUSD").text("").text(fmoney(Number(thisList.eq(34).text()),2));//
	    $(".news .invoiceDate").text("").text(thisList.eq(25).text());
	    
	    $(".news .saveId").attr("val",thisList.eq(0).attr("value"));
	    
	    //判断App_FAX是否存在
	    if(thisList.eq(29).text().split("&&").length == 1){
	    	 /* $(".TelFax").show(); */
			 $(".news .App_TEL").text(thisList.eq(29).text().split("&&")[0].replace("TEL:",""));
	    }else if(thisList.eq(29).text().split("&&").length == 2){
	    	 $(".TelFax").show();
			 $(".news .App_TEL").text(thisList.eq(29).text().split("&&")[0].replace("TEL:",""));
			 $(".news .App_FAX").text(thisList.eq(29).text().split("&&")[1].replace("FAX:",""));//
	    }else{
	    	$(".TelFax").hide();
	    }
	    if(thisList.eq(27).text() == 0){  //100%
	    	 $(".news .PONO").text("").text(thisList.eq(28).text());//
	    }
	    else if(thisList.eq(27).text() == 1){ //10%
	    	$(".news .PONO").hide();
	    	$(".news .tenTr").show();
	    	var tenPaid = fmoney(thisList.eq(32).text(),2)
	 	     $(".news .tenPaid").text("").text(tenPaid);//
	    }
	    else{
	    	$(".news .PONO").hide();
	    	$(".news .nineTr").show();
	    	var ninePaid = fmoney(thisList.eq(31).text(),2)
	 	    $(".news .ninePaid").text("").text(ninePaid);//
	    }
	    /* if(DepartureDate =="NA"){
		     
		 } */
	    
        $('.hidePdf').show();
	    $(".MarkAdd").css("width",$(".Mark").width())
	});
	$('#contract_close1').click(function () {
	    $('.MailBar_cover_color').hide();
	    $('.hidePdf').hide();
	});

	
	//点击关闭
	$('.contractAdd_close').click(function () {
	    $('.MailBar_cover_color').hide();
	    $('.contract_add').hide();
	});
	$('#update_cancel').click(function () {
	    $('.MailBar_cover_color').hide();
	    $('.contract_update').hide();
	});
	$('.contractUpdate_close').click(function () {
	    $('.MailBar_cover_color').hide();
	    $('.contract_update').hide();
	});
 $('.contractUpdate_close,.update_cancel').click(function () {
	    $('.MailBar_cover_color').hide();
	    $('.contract_update').hide();
	});

	//点击取消
	$('#add_cancel').click(function () {
		addFlag = 1
	    $('.MailBar_cover_color').hide();
	    $('.contract_add').hide();
	});
	//PO
	$(".InvoiceType").change(function(){
		if($(this).find("option:selected").val() == "0"){
			$(".pono").show();
			$(".TenPercent").hide();
			$(".NinetyPercent").hide();
		}
		else if($(this).find("option:selected").val() == "1"){
			$(".pono").hide();
			$(".TenPercent").show();
			$(".NinetyPercent").hide();
		}
		else{
			$(".pono").hide();
			$(".TenPercent").hide();
			$(".NinetyPercent").show();
		}
	})
	//Applicant联动添加时
	$(".contract_add .APPSelect").change(function(){
		var ApplicantVal = $(this).find("option:selected").text();
		$(".contract_add #Applicant").val(ApplicantVal);
		var AppIndex = $(".contract_add .APPSelect").find("option:selected").prop('index');
		$(".contract_add .hideTD").show();
		$(".contract_add .TELSelect").find("option").eq(AppIndex).attr("selected",true).siblings().attr("selected",false);
		$(".contract_add .ADDSelect").find("option").eq(AppIndex).attr("selected",true).siblings().attr("selected",false);
		$(".contract_add #TEL").val($(".contract_add .TELSelect").find("option:selected").text());
		$(".contract_add #ADD").val($(".contract_add .ADDSelect").find("option:selected").text());
	})
	
	//Applicant联动修改时
	$(".contract_update .APPSelect").change(function(){
		var ApplicantVal = $(this).find("option:selected").text();
		$(".contract_update #Applicant1").val(ApplicantVal);
		var AppIndex = $(".contract_update .APPSelect").find("option:selected").prop('index');
		$(".contract_update .hideTD").show();
		$(".contract_update .TELSelect").find("option").eq(AppIndex).attr("selected",true).siblings().attr("selected",false);
		$(".contract_update .ADDSelect").find("option").eq(AppIndex).attr("selected",true).siblings().attr("selected",false);
		$(".contract_update #TEL1").val($(".contract_update .TELSelect").find("option:selected").text());
		$(".contract_update #ADD1").val($(".contract_update .ADDSelect").find("option:selected").text());
	})
	
	
	/* 添加发票信息 */
	$(document).on("click","#add_submit",function(){
	    var area=$('.contract_add select[name="area"]').val();
	    var contract_no=$('.contract_add input[name="contract_no"]').val();
	    
	    var INVOICE_NO=$('.contract_add input[name="INVOICE_NO"]').val();
	    var DC_NO=$('.contract_add input[name="DC_NO"]').val();
	    var Applicant=$('.contract_add input[name="Applicant"]').val();
	    var ADD=$('.contract_add input[name="ADD"]').val();
	    var TheEndUser=$('.contract_add input[name="TheEndUser"]').val();
	    var OtherReference=$('.contract_add input[name="OtherReference"]').val();
	    
	    var Departure_date=$('.contract_add input[name="Departure_date"]').val()== "" ? "0000-00-00" :$('.contract_add input[name="Departure_date"]').val() ;
	    
	    var Vessel_flight=$('.contract_add input[name="Vessel_flight"]').val();
	    var From=$('.contract_add input[name="From"]').val();
	    var To=$('.contract_add input[name="To"]').val();
	    var Packing=$('.contract_add input[name="Packing"]').val();
	    var CountryofOrigin=$('.contract_add input[name="CountryofOrigin"]').val();
	    var Manufacturer=$('.contract_add input[name="Manufacturer"]').val();
	    var ShippingMark=$('.contract_add input[name="ShippingMarkNo"]').val() +"&&"+$('.contract_add input[name="ShippingMarkAdd"]').val();
	    var AIRPORT=$('.contract_add input[name="AIRPORT"]').val();
	    var TotalAmount=parseFloat($('.contract_add input[name="USD"]').val().replace(/,/g,''));
	     /* alert(TotalAmount)
	    alert(typeof(TotalAmount))   */
	   
	    if(isNaN(TotalAmount)){ 
	    	TotalAmount = '0';
	    }
	    /* if(TotalAmount = 'NaN'){
	    
	    	TotalAmount = '0';
	    } */
	    
	    var Date=$('.contract_add input[name="installed_time"]').val()== "" ? "0000-00-00" :$('.contract_add input[name="installed_time"]').val();
	    
	    var TelFax=$('.contract_add input[name="TEL"]').val();
	    var remarks = $('.contract_add input[name="remarks"]').val();
	 
	    var GoodsArr = [];
	    var UnitArr = [];
	    var QtyArr = [];
	    var TotalUSDAmountArr = [];
	    var UnitPriceArr = [];
	    
	    var Product = $(".contract_add .GoodsTd").eq(0).find('input').val();
	    for(var i = 0 ; i < $(".contract_add .GoodsTd").length ; i++){
	    	var str = $(".contract_add .GoodsTd").eq(i).find('input').val();
	    	GoodsArr.push(str);
	    }
		for(var i = 0 ; i < $(".contract_add .UnitTd").length ; i++){
			var str = $(".contract_add .UnitTd").eq(i).find('input').val();
			UnitArr.push(str);  	
		}
		for(var i = 0 ; i < $(".contract_add .QtyTd").length ; i++){
			var str = $(".contract_add .QtyTd").eq(i).val();
			QtyArr.push(str);
		}
		for(var i = 0 ; i < $(".contract_add .UnitPriceTd").length ; i++){
			var str = parseFloat($(".contract_add .UnitPriceTd").eq(i).val().replace(/,/g,''));
			UnitPriceArr.push(str);
		}
		for(var i = 0 ; i < $(".contract_add .TotalAmountTd").length ; i++){
			var str = parseFloat($(".contract_add .TotalAmountTd").eq(i).val().replace(/,/g,''));
			TotalUSDAmountArr.push(str);
		}
		 
		
	    if(area == 0){  //100%
	    	var PO_NO=$('.contract_add input[name="po_no"]').val();
	   		var ninePaid = 0;
	   		var tenPaid = 0;
	    }
	    else if(area == 1){ //10%
	    	var PO_NO="NA";
	   		// var tenPaid= $('.contract_add input[name="tenPaid"]').val();
	   		var tenPaid = parseFloat(Number($('.contract_add input[name="tenPaid"]').val().replace(/,/g,'')));
	   		var ninePaid = 0;
	    }
	    else{
	    	var PO_NO="NA";
	   		var ninePaid= parseFloat(Number($('.contract_add input[name="ninePaid"]').val().replace(/,/g,'')));
	   		var tenPaid = 0;
	    }
	   /*  alert(GoodsArr) */
	    if(GoodsArr ==''){
	    	/* alert(88) */
	    	 $.MsgBox_Unload.Alert("提示","货物名称为必填项！"); 
	    	 return; 
	    }
	    if(TotalUSDAmountArr == '' || UnitPriceArr == '' || QtyArr == ''){
	    	$.MsgBox_Unload.Alert("提示","数量和单价（美元）和总价（美元）为必填项！");
	    	return;
	    }
	    PO_NO = Trim(PO_NO);
	    contract_no = Trim(contract_no);
	    INVOICE_NO = Trim(INVOICE_NO);
	    DC_NO = Trim(DC_NO);
	    $.ajax({
	        // type : 'get',
	        type : 'POST',
	        url : 'InvoiceAdd',
	        data : {
	            ContractNO : contract_no,
	            AirPort : AIRPORT,
	            Applicant : Applicant,
	            Date : Date,
	            DCNO : DC_NO,
	            Departure : From,
	            DepartureDate : Departure_date,
	            Destination : To,
	            EndUser : TheEndUser,
	            InvoiceNO : INVOICE_NO,
	            Manufacturer : Manufacturer,
	            Origin : CountryofOrigin,
	            OtherReference : OtherReference,
	            PaymentRemark : remarks,
	            PONO : PO_NO,
	            ShippingMark : ShippingMark,
	            Type : area,
	            Vessel : Vessel_flight,
	            Add : ADD,
	            TelFax : TelFax,
	            Goods : GoodsArr,
	            TotalUSDAmount : TotalUSDAmountArr,
	            Unit : UnitArr,
	            UnitUSDPrice : UnitPriceArr,
	            Qty:QtyArr,
	            Packing : Packing,
	            NinePaid:ninePaid,
	            TenPaid:tenPaid,
	            TotalAmount:TotalAmount,
	            Product:Product
	        },
	        dataType: 'text',
	        success: function (data) {
	        	if(data.indexOf("添加成功")>-1){
	        		$.MsgBox.Alert('提示','添加成功');
	        		$('.MailBar_cover_color').hide();
	        		$('.contract_add').hide();
	        	}else if(data.indexOf("添加失败")>-1){
	        		$.MsgBox_Unload.Alert("提示", "添加失败！请检查填写内容");
	        	}else{
	        		$.MsgBox_Unload.Alert("提示", "添加失败！非正常错误");
	        	}
	        },
	        error: function () {
	        	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    });    
	    
    });
	
})	
	//点击确定刷新页面
	$(document).on("click", "#mb_btn_ok", function () {
	    window.location.reload();
	});
/* 	function fm(obj){
		var vb = obj.value.split('.');
		var val = vb[0].replace(/\D/,'');
		if(obj.value.length>0)obj.value = val.match(/\d{3}|\d{2}|\d/g).join(',')+(vb.length>1?'.'+vb[1]:'');
	} */
	function fmoney(s, n) //s:传入的float数字 ，n:希望返回小数点几位 
	{ 
		n = n > 0 && n <= 20 ? n : 2; 
		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
		var l = s.split(".")[0].split("").reverse(), 
		r = s.split(".")[1]; 
		t = ""; 
		for(i = 0; i < l.length; i ++ ) 
		{  
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
		} 
		return t.split("").reverse().join("") + "." + r; 
	} 
	function outputmoney(number) {
		number = number.replace(/\,/g, "");
		if(isNaN(number) || number == "")return "";
		number = Math.round(number * 100) / 100;
		    if (number < 0)
		        return '-' + outputdollars(Math.floor(Math.abs(number) - 0) + '') + outputcents(Math.abs(number) - 0);
		    else
		        return outputdollars(Math.floor(number - 0) + '') + outputcents(number - 0);
		} 
	//格式化金额
	function outputdollars(number) {
	    if (number.length <= 3)
	        return (number == '' ? '0' : number);
	    else {
	        var mod = number.length % 3;
	        var output = (mod == 0 ? '' : (number.substring(0, mod)));
	        for (i = 0; i < Math.floor(number.length / 3); i++) {
	            if ((mod == 0) && (i == 0))
	                output += number.substring(mod + 3 * i, mod + 3 * i + 3);
	            else
	                output += ',' + number.substring(mod + 3 * i, mod + 3 * i + 3);
	        }
	        return (output);
	    }
	}
	function outputcents(amount) {
	    amount = Math.round(((amount) - Math.floor(amount)) * 100);
	    return (amount < 10 ? '.0' + amount : '.' + amount);
	}
</script>
</html>