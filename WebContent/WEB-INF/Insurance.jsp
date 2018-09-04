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
<title>进出口运输指令</title>
<style>
.tbody_tr td {
    padding: 0 3px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
#table1 td {
    height: 38px !important;
}

.eyes_psw {
    position: absolute;
    right: 20px;
    top: 13px;
    display: inline-block;
    font: normal normal normal 14px/1 FontAwesome;
    font-size: 14px;
    text-rendering: auto;
}

.eyes_psw:before {
    content: "\f06e";
}
</style>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" />
<link rel="stylesheet" type="text/css" href="css/insurance.css">
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
				action="GetInsuranceRoute">
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
							value="${fn:split('客户名称,合同名称,合同号,SONO,PONO,信用证号',',')}"></c:set>
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
					<div class="changeBox" style="position:relative;top:17px;left:0;z-index:3;background:url(image/bg1.png);width:358px;height:50px;display:inline-block;line-height:50px;font-size:14px;">
						<span class="Domestic" style="position:absolute;top:0;left:0;background:url(image/bg3.png);width:183px;height:42px;display:block;text-align:center;cursor:pointer;">国内运输指令</span>
						<span class="ExitOrEn" style="position:absolute;top:0;left:175px;width:183px;height:42px;cursor:pointer;display:block;text-align:center;">进出口运输指令</span>
					</div>
					<div style=" transform: skew(20deg);display:inline-block;">
						<!-- <input type="button" value="添加" class="bToggle addData" onclick="AddContract()" > -->
						<input type="button" value="添加" class="bToggle addData"  >
					</div>
					
				</div>

		 </form> 
		 	 <table border="1" cellspacing="0" cellspadding="0" id="table1">
				<tr style="background: #bfbfbf;">
					<td style="width: 4.4%;">序号</td>
					<td style="display:none;">修改</td>
					<td style="width: 20.3%;">客户名称 </td>
					<td style="width: 18.5%;">合同名称</td>
					<td style="width: 5.5%;">SONO</td>
					<td style="width: 12.21%;">PONO</td>
					<td style="width: 10.19%;">合同号</td>
					<td style="width: 12.91%;">提货日期</td>
					<td style="width: 7.41%;">信用证号</td>
					<td style="width: 145px;display:none;">模板预览（可导出）</td>
					<td style="display:none;">删除数据</td>
					<td style="display:none;">Name</td>
					<td style="display:none;">ADD</td>
					<td style="display:none;">ATT</td>
					<td style="display:none;">Tel</td>
					<td style="display:none;">ConsigneeName</td>
					<td style="display:none;">ConsigneeADD</td>
					<td style="display:none;">ConsigneeATTN</td>
					<td style="display:none;">ConsigneeTel</td>
					<td style="display:none;">Shipment</td>
					<td style="display:none;">Destination</td>
					<td style="display:none;">Model</td>
					<td style="display:none;">Description</td>
					<td style="display:none;">Qty</td>
					<td style="display:none;">Size</td>
					<td style="display:none;">ShippingMarkNO</td>
					<td style="display:none;">ShippingMarkNOADD</td>
					<td style="display:none;">InvoiceUSD</td>
					<td style="display:none;">InsuranceUSD</td>
					<td style="display:none;">FinalADD</td>
					<td style="display:none;">Insured</td>
					<td style="display:none;">Currency</td>
					<td style="display:none;">Address</td>
					<td style="width: 8.58%;">邮件功能</td>
				</tr>
				 <c:forEach var="orderInfo" items="${insurance}" varStatus="status">
					<c:if test="${status.index>0}">
						<tr class="tbody_tr">
							<td value="${orderInfo['ID']}" class="contract-show">${status.index+(currentPage-1)*10}</td>
							<td style="display:none;"> <i class="fa fa-edit contract-edit" value="${orderInfo['ID']}"></i></td>
							<td title="${orderInfo['Customer']}">${orderInfo['Customer']}</td>
							<td title="${orderInfo['ContractTitle']}">${orderInfo['ContractTitle']}</td>
							<td title="${orderInfo['SONO']}">${orderInfo['SONO']}</td>               <!-- 4 -->
							<td title="${orderInfo['PONO']}">${orderInfo['PONO']}</td>				<!-- 5 -->
							<td title="${orderInfo['ContractNO']}">${orderInfo['ContractNO']}</td>			<!-- 6 -->
							<td title="${orderInfo['TakingDate']}">${orderInfo['TakingDate']}</td>			<!-- 7 -->
							<td>${orderInfo['DCNO']}</td>				<!-- 8 -->
							<td style="display:none;"><i class="fa fa-eye contract-show"></i></td>
							<td style="display:none;"><i class="fa fa-trash-o del"></i></td>
							<td style="display:none;">${orderInfo['Name']}</td>    <!--11  -->
							<td style="display:none;">${orderInfo['PackingAddress']}</td>     <!--12  -->
							<td style="display:none;">${orderInfo['Applicant']}</td>     <!--13  -->
							<td style="display:none;">${orderInfo['Tel']}</td>     <!--14  -->
							<td style="display:none;">${orderInfo['ConsigneeName']}</td><!--15  -->
							<td style="display:none;">${orderInfo['ConsigneeADD']}</td><!--16 -->
							<td style="display:none;">${orderInfo['ConsigneeATTN']}</td><!--17 -->
							<td style="display:none;">${orderInfo['ConsigneeTel']}</td><!--18 -->
							<td style="display:none;">${orderInfo['Shipment']}</td><!--19 -->
							<td style="display:none;">${orderInfo['Destination']}</td><!--20 -->
							<td style="display:none;">${orderInfo['Model']}</td>    <!--21  -->
							<td style="display:none;">${orderInfo['Description']}</td>    <!--22  -->
							<td style="display:none;">${orderInfo['Qty']}</td>       <!--23  -->
							<td style="display:none;">${orderInfo['Size']}</td>      <!--24  -->
							<td style="display:none;">${orderInfo['ShippingMarkNO']}</td><!--25  -->
							<td style="display:none;">${orderInfo['ShippingMarkADD']}</td><!--26 -->
							<td style="display:none;">${orderInfo['InvoiceUSD']}</td> <!--27 -->
							<td style="display:none;">${orderInfo['InsuranceUSD']}</td><!--28   无 -->
							<td style="display:none;">${orderInfo['FinalADD']}</td><!--29 -->
							<td style="display:none;">${orderInfo['Insured']}</td><!--30 -->
							<td style="display:none;">${orderInfo['Currency']}</td><!--31 -->
							<td style="display:none;">${orderInfo['Address']}</td><!--32 -->
							<td style="display:none;">${orderInfo['InWarehouse']}</td><!--33 -->
							<td style="display:none;">${orderInfo['WaybillNum']}</td><!--34 -->
							<!-- <td class="SendEmail" style="cursor:pointer;">发送</td> -->
							<td class="SendEmail" style="cursor:pointer;" flag="Unsent">${orderInfo['IsSend']}</td>
						</tr>
					</c:if>	
				</c:forEach>
			</table>
			
	 	 <c:choose>
				<c:when test="${queryType == 'common'}">
					<c:set var="queryUrl"
					value="Insurance?type1=${classify1 }&searchContent1=${parameter1}&selected=${queryType}&currentPage=">
					</c:set>
				</c:when>
				<c:otherwise>
					<c:set var="queryUrl"
					value="GetInsuranceRoute?type1=${classify1 }&searchContent1=${parameter1}&type2=${classify2}&searchContent2=${parameter2}&selected=${queryType}&currentPage=">
					</c:set>
				</c:otherwise>
			
			</c:choose> 
						
 			<div id="page">
				<div class="pageInfo">
					当前是第&nbsp;<span id="currentPage">${currentPage}</span>&nbsp;页,&nbsp;总计&nbsp;<span
						id="allPage">${pageCounts}</span>页
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
						onkeyup="value=value.replace(/[^\d]/g,'') " autocomplete="off"> 页 <input
						type="button" class="bToggle" value="GO" id="Gotojump"
						name="Gotojump" onclick="PageJump('${queryUrl}')"> <input
						type="button" class="bToggle" value="尾页" id="lastPage"
						name="lastPage" onclick="LastPage('${queryUrl}')">
				</div>
			</div>  
		</div>
	</div>
	<div class="MailBar_cover_color"></div>
	<div class="SendEmailBox" >
		<div class="SendEmailBox_title">邮件发送</div>
		<div class="SendEmailBox_close">关闭</div>
			<div class="SendEmailCon">
				<table cellpadding="0" cellspacing="0" border="1"  style="width:90%;margin-left:5%;margin-top:40px;" class="SendEmailTab">
					<tr>
						<td>发件人</td>
						<td class="Sender"></td>
					</tr>
					<tr>
						<td>邮箱密码</td>
						<td style="position: relative;">
							<input type="password" name="password" style="display:none">
							<input type="password" name="password" class="EmailPwd contentNull" autocomplete="new-password">
							<span class="eyes_psw"></span>
						</td>
					</tr>
					<tr>
						<td>收件人</td>
						<td  contenteditable="true"  id="search_text"  class="contentNull"></td>
						<div id="auto_div"></div>  
					</tr>
					<tr>
						<td>抄送</td>
						<!-- <td  contenteditable="true" class="CC">liuyanan@eoulu.com；jiangyaping@eoulu.com；zhudanni@eoulu.com；caishuhui@eoulu.com</td> -->
						<td  contenteditable="true" class="CC contentNull" id="CC">liuyanan@eoulu.com;jiangyaping@eoulu.com</td>
						<div id="CC_div"></div>
					</tr>
					<tr>
						<td>主题</td>
						<td  contenteditable="true" class="theme contentNull" ></td>
					</tr>
					<tr>
						<td>正文</td>
						<td class="EmailContent">
							<div   contenteditable="true">您好！</div></br>
							<div   contenteditable="true" class="EmailCon"></div></br>
							<div   contenteditable="true">注意事项：</div></br>
							<div   contenteditable="true">烦请尽快安排处理，并给出航班信息，谢谢！</div></br>
						</td>
					</tr>
				</table>
			</div>
			<div class="other" style="width:100%;margin-bottom:30px;">
				<table cellpadding="0" cellspacing="0" border="1"  style="width:90%;margin-left:5%;" >
					<tr>
						<td  style="width: 15%;height:45px;line-height:45px;text-align:center;">附件</td>
						<td  class="annexName">
							<i class="fa fa-file-text-o annexNameIcon" ></i>
							<!-- <span  class="annexHref" >Insurance.doc</span> -->
							<span  class="annexHref" >shipping instruction.doc</span>
							
							<!-- <input type="file" name="EmailAnnex" id="file" value="上传附件" /> -->
						</td>
					</tr>
				</table>
			</div>
			<div class="edit_btn">
			<input type="button" value="发送" class="bToggle" id="SendEmail_submit">
			<input type="button" value="取消" class="bToggle" id="SendEmail_cancel">
		</div>
	</div>
	<!--添加的模板  -->
	<div class="hidePdf" style="display:none;height:auto;">
	<div id="view" class="news" style="position:absolute;z-index:11;top:0;left:0;background:#fff;font-size:12px">

		    <!--运输保险指令文档-->
		    <div id="table_Insurance" class="InsuranceID">
		    <table cellpadding="0" cellspacing="0" border="1" style=" width:100%;">
		        <tr>
		            <td style="text-align:right;">
		               <span >SO号：</span>
		            </td>
		            <td style="width: 65%;text-align:center;">
		                <p contenteditable="true" class="SONO tc"></p>
		            </td>
		        </tr>
		        <tr>
		            <td style="text-align:right;">
		                <span>PO号：</span>
		            </td>
		            <td style="text-align:center;" >
		                <p contenteditable="true" class="PONO tc"></p>
		            </td>
		        </tr>
		         <tr>
		            <td style="text-align:right;">
		                <span>入仓号：</span>
		            </td>
		            <td style="text-align:center;" >
		                <p contenteditable="true" class="InWarehouse tc"></p>
		            </td>
		        </tr>
		        <tr>
		            <td style="text-align:right;">
		                <span  class="red">合同号：</span>
		            </td>
		            <td style="text-align:center;" >
		                <p contenteditable="true" class="ContractNO tc" ></p>
		            </td>
		        </tr>
		        <tr>
		            <td style="text-align:right;">
		                <span>信用证号：</span>
		            </td>
		            <td style="text-align:center;" >
		                <p contenteditable="true" class="DCNO tc"></p>
		            </td>
		        </tr>
		        <tr>
		            <td style="text-align:right;">
		                <span>运单号：</span>
		            </td>
		            <td style="text-align:center;" >
		                <p contenteditable="true" class="Waybill tc"></p>
		            </td>
		        </tr>
		        <tr>
		            <td style="text-align:right;">
		                <span>提货日期：</span>
		            </td>
		            <td style="text-align:center;" >
		                <input type="date"  class="pickupDate" />
						<input type="time"  class="pickupTime" />
		            </td>
		        </tr>
		        <!-- 添加时显示的下拉框 -->
		        <tr>
		            <td style="text-align:right;">
		                <span>提货地址：</span>
		            </td>
		            <td  style="line-height: 27px;" class="editTiADD editDom">
			            <div>
			          	  NAME：<input type="text" name=Name value="" id="Name1">
							<span >
								<select  class="NameSelect">
									<option value="No"  selected="selected">--请选择--</option>
									<option value="USA">美国地址：FORMFACTOR BEAVERTON,INC.</option>
									<option value="Germany">德国地址：FORMFACTOR GMBH.</option>
									<option value="HK">香港货仓1：AWOT GLOBAL EXPRESS (HK) LTD</option>
									<option value="HK2">香港货仓2：SHING FAT LOGISTICS LTD. </option>
								</select>
							</span>
			            </div>
			           	<div>
							ADD:<input type="text" name="ADD" value=""  id="ADD1">
							<span>
								<select onchange="document.getElementById('ADD1').value=this.options[this.selectedIndex].text" class="ADDSelect">
									<option value="No"  selected="selected">--请选择--</option>
									<option value="USA">9100 SW GEMINI DRIVE BEAVERTON, OR 97008 USA</option>
									<option value="Germany">Sϋss STR.1 01561 THIENDORF OT SACKA GERMANY</option>
									<option value="HK">UNIT 256,AIRPORT FREIGHT FORWARDING CENTRE,2 CHUN WAN ROAD,CHEK LAP KOK,HONG KONG</option>
									<option value="HK2">RM. A, 24/F, GOODMAN DYNAMIC CENTRE, 188 YEUNG UK ROAD, TSUENWAN,N.T.HONGKONG,CHINA</option>
								</select>
							</span>
						</div>
						<div>	
							ATT:<input type="text" name="ATT" value="" id="ATT1">
							<span>
								<select onchange="document.getElementById('ATT1').value=this.options[this.selectedIndex].text" class="ATTSelect">
									<option value="No"  selected="selected">--请选择--</option>
									<option value="USA">APBEAVERTON@FORMFACTOR.COM</option>
									<option value="Germany">APBEAVERTON@FORMFACTOR.COM</option>
									<option value="HK">HANG</option>
									<option value="HK2">MAX LI</option>
									
								</select>
							</span>
						</div>	
						<div>
							TEL:<input type="text" name="Tel" value=""  id="Tel1">
								<span>
									<select onchange="document.getElementById('Tel1').value=this.options[this.selectedIndex].text" class="TelSelect">
										<option value="No"  selected="selected">--请选择--</option>
										<option value="USA">1-503-601-1000</option>
										<option value="Germany">+49 35240 73 390</option>
										<option value="HK">00852-22443069</option>
										<option value="HK2">00852-6334 7018 </option>
									</select>
							</span><br />
						</div>			
						
		            </td>
		           
		        </tr>
		        <tr>
		            <td style="text-align:right;">
		                <span  class="red">Shipper：</span>
		            </td>
		            <td>
		                <p class="b">HK EOULU TRADING LIMITED</p>
		                <p>ADD：Room 1501, Grand Millennium Plaza (Lower Block), 181 Queen's Road Central, HONG KONG</p>
		                <p>ATTN：WANG XIAOLIANG</p>
		                <p>TEL：00852-21527388/21527399 </p>
		
		            </td>
		        </tr>
		        <tr>
		            <td style="text-align:right;">
		                <span  class="red">Consignee：</span>
		            </td>
		            <td style="line-height: 28px;">
		                <p contenteditable="true" class="ConsigneeName">ACCELINK TECHNOLOGIES CO.,LTD.</p>
		               ADD:<p contenteditable="true" class="ConsigneeADD" style="display:inline-block;"></p><br />
		               ATTN：<p contenteditable="true" class="ConsigneeATTN" style="display:inline-block;"></p><br />
		               TEL： <p contenteditable="true" class="ConsigneeTel" style="display:inline-block;"></p>
		            </td>
		        </tr>
		        <tr>
		            <td style="text-align:right;">
		                <span  class="red">Notifying applicant ：</span>
		            </td>
		            <td style="line-height: 28px;">
		                <p contenteditable="true" class="Applicant ConsigneeName">ACCELINK TECHNOLOGIES CO.,LTD.</p>
		                ADD：<p contenteditable="true" class="ApplicantADD ConsigneeADD" style="display:inline-block;"></p><br />
		                ATTN：<p contenteditable="true" class="ApplicantATTN ConsigneeATTN" style="display:inline-block;"></p><br />
		                TEL：<p contenteditable="true" class="ApplicantTel ConsigneeTel" style="display:inline-block;"></p>
		            </td>
		        </tr>
		        <tr>
		            <td style="text-align:right;">
		                <span  class="red">起运港（保单相同）：</span>
		            </td>
		           	<td class="editDom">
		           	<div>
		           		SHIPMENT：<input type="text" name="Shipment" value=""  id="Shipment" style="left:90px;width: 170px;">
						<span>
							<select onchange="document.getElementById('Shipment').value=this.options[this.selectedIndex].text" class="Shipmentelect" style="left:90px;width: 195px;">
								<option value="No"  selected="selected">--请选择--</option>
								<option value="HK">HK</option>
								<option value="USA">USA</option>
								<option value="Germany">Germany</option>
							</select>
						</span>
					</div>
					</td>
		        </tr>
		        <tr>
		            <td style="text-align:right;">
		                <span  class="red">目的港：</span>
		            </td>
		            <td>
		                <p contenteditable="true" class="Destination">WUHAN,P.R.CHINA</p>
		            </td>
		        </tr>
		        <tr class="ModelTd">
		            <td style="text-align:right;">
		                <span  class="red">货物信息（保单相同）：</span>
		            </td>
		            <td class="ModelList" style="line-height: 34px;"></td>
		        </tr>
		        <tr>
		            <td style="text-align:right;">
		                <span  class="red">shipping mark（保单相同）：</span>
		            </td>
		            <td>
		                <p contenteditable="true" class="ShippingMarkNO"> 4300000987</p>
		                <p style="border: 1px dashed #000;margin-left: 10px;width: 70px;"></p>
		                <p contenteditable="true" class="ShippingMarkADD"> WUHAN,P.R.CHINA</p>
		            </td>
		        </tr>
		        <tr>
		            <td style="text-align:right;">
		                <span  class="red">运单要求：</span>
		            </td>
		            <td style="font-size: 12px;line-height: 30px;" class="red">
		                <p>1. 运单中一定要有 'FREIGHT PREPAID' INDICATING FREIGHT AMOUNT</p>
		                <p>2. Consignee & Notifying applicant要依照指令写</p>
		                <p>3. 运单上应该列明：合同号，Shipping mark，货物型号，描述，数量，尺寸，重量</p>
		                <p>4. 运单右下角关于shipper或agent签名空白处加一句“ on behalf of the shipper"</p>
		                <p>5. 运单右下角关于carrier或agent处空白地加一句”as agent of the carrier 写什么航空公司”。</p>
		
		            </td>
		        </tr>
		
		    </table>
		
		        <p class="m30b changeP"  style="margin-top: 10px;display:none;" >*以上红字标识的项目需全部在运单中体现，且需跟保单完全一致。</p>
		        <p style="margin-bottom: 10px;display:none;">除去运单中的相同项外，保单的其他要求：</p>
		    <table cellpadding="0" cellspacing="0" border="1" style=" width:100%;display:none;">
		        <tr>
		            <td style="text-align:right;">
		                <span>发票金额：</span>
		            </td>
		            <td style="width: 65%;text-align:center;">
		                <p contenteditable="true" class="InvoiceUSD tc">USD160000.00</p>
		            </td>
		        </tr>
		        <tr>
		            <td style="text-align:right;">
		                <span>保险金额：</span>
		            </td>
		            <td style="text-align:center;" >
		                <p contenteditable="true" class="InsuranceUSD tc">USD176000.00</p>
		            </td>
		        </tr>
		        <tr>
		            <td style="text-align:right;">
		                <span>最终目的地：</span>
		            </td>
		            <td style="text-align:center;" >
		                <p contenteditable="true" class="FinalADD tc">88 YOUKEYUAN ROAD,HONGSHAN DISTRICT
		                    WUHAN,HUBEI PROV.,430074,P.R.CHINA</p>
		            </td>
		        </tr>
		        <tr>
		            <td style="text-align:right;">
		                <span>保险责任至：</span>
		            </td>
		            <td style="text-align:center;" >
		                <p contenteditable="true" class="Insured tc">WUHAN WAREHOUSE</p>
		            </td>
		        </tr>
		        <tr>
		            <td style="text-align:right;">
		                <span>赔付币种：</span>
		            </td>
		            <td style="text-align:center;" >
		                <p contenteditable="true" class="Currency tc">美金</p>
		            </td>
		        </tr>
		        <tr>
		            <td style="text-align:right;">
		                <span>赔付地点：</span>
		            </td>
		            <td style="text-align:center;" >
		                <p contenteditable="true" class="Address tc">武汉</p>
		            </td>
		        </tr>
		        <tr>
		            <td style="text-align:right;">
		                <span>货物尺寸：</span>
		            </td>
		            <td>
		                <p>（保单运单需一致）</p>
		            </td>
		        </tr>
		        <tr>
		            <td style="text-align:right;">
		                <span>投保人：</span>
		            </td>
		            <td>
		                <p>深圳伊欧陆微电子系统有限公司</p>
		            </td>
		        </tr>
		        <tr>
		            <td style="text-align:right;">
		                <span>被保险人：</span>
		            </td>
		            <td>
		               <p>HK EOULU TRADING LIMITED</p>
		            </td>
		        </tr>
		        <tr>
		            <td style="text-align:right;">
		                <span>保单注意事项：</span>
		            </td>
		            <td>
		               <p style="font-size: 12px;">包装尺寸、开航日期、包装、尺寸、运输工具、起运港由运输商视实情填写,保单运单必须一致。
		                   且所有列出的项必须都在保单中体现出。保单的所有项都应有英文翻译。保单一定要涵盖航空一切险、战争险/邮包一切险、战争险（邮包）
		               </p>
		            </td>
		        </tr>
		    	</table>
			</div>
		</div>

			<!--修改模板  -->
			<div id="view" class="news_update" style="position:absolute;z-index:11;top:0;left:0;background:#fff;font-size:12px;">
			
			    <!--运输保险指令文档-->
			    <div id="table_Insurance" class="InsuranceID">
			    <table cellpadding="0" cellspacing="0" border="1" style=" width:100%;">
			        <tr>
			            <td style="text-align:right;">
			               <span >SO号：</span>
			            </td>
			            <td style="width: 65%;text-align:center;">
			                <p contenteditable="true" class="SONO tc"></p>
			            </td>
			        </tr>
			        <tr>
			            <td style="text-align:right;">
			                <span>PO号：</span>
			            </td>
			            <td style="text-align:center;" >
			                <p contenteditable="true" class="PONO tc"></p>
			            </td>
			        </tr>
			         <tr>
			            <td style="text-align:right;">
			                <span>入仓号：</span>
			            </td>
			            <td style="text-align:center;" >
			                <p contenteditable="true" class="InWarehouse tc"></p>
			            </td>
			        </tr>
			        <tr>
			            <td style="text-align:right;">
			                <span  class="red">合同号：</span>
			            </td>
			            <td style="text-align:center;" >
			                <p contenteditable="true" class="ContractNO tc" ></p>
			            </td>
			        </tr>
			        <tr>
			            <td style="text-align:right;">
			                <span>信用证号：</span>
			            </td>
			            <td style="text-align:center;" >
			                <p contenteditable="true" class="DCNO tc"></p>
			            </td>
			        </tr>
			        <tr>
			            <td style="text-align:right;">
			                <span>运单号：</span>
			            </td>
			            <td style="text-align:center;" >
			                <p contenteditable="true" class="Waybill tc"></p>
			            </td>
			        </tr>
			        <tr>
			            <td style="text-align:right;">
			                <span>提货日期：</span>
			            </td>
			            <td style="text-align:center;" >
			                <p contenteditable="true" class="TakingDate tc" ></p>
			                
			            </td>
			        </tr>
			        <!--修改（预览）时显示的文本框  -->
			        <tr>
			            <td style="text-align:right;">
			                <span>提货地址：</span>
			            </td>
			            <td  style="line-height: 27px;" class="tiADD" >
			                <p contenteditable="true" class="Name b"></p>
			                <p contenteditable="true" class="ADD"></p>
			                <p contenteditable="true" class="ATT"></p>
			                <p contenteditable="true" class="Tel"></p> 
			            </td>
			        </tr>
			        <tr>
			            <td style="text-align:right;">
			                <span  class="red">Shipper：</span>
			            </td>
			            <td>
			                <p class="b">HK EOULU TRADING LIMITED</p>
			                <p>ADD：Room 1501, Grand Millennium Plaza (Lower Block), 181 Queen's Road Central, HONG KONG</p>
			                <p>ATTN：WANG XIAOLIANG</p>
			                <p>TEL：00852-21527388/21527399 </p>
			
			            </td>
			        </tr>
			        <tr>
			            <td style="text-align:right;">
			                <span  class="red">Consignee：</span>
			            </td>
			            <td style="line-height: 28px;">
			                <p contenteditable="true" class="ConsigneeName">ACCELINK TECHNOLOGIES CO.,LTD.</p>
			               ADD:<p contenteditable="true" class="ConsigneeADD" style="display:inline-block;"></p><br />
			               ATTN：<p contenteditable="true" class="ConsigneeATTN" style="display:inline-block;"></p><br />
			               TEL： <p contenteditable="true" class="ConsigneeTel" style="display:inline-block;"></p>
			            </td>
			        </tr>
			        <tr>
			            <td style="text-align:right;">
			                <span  class="red">Notifying applicant ：</span>
			            </td>
			            <td style="line-height: 28px;">
			                <p contenteditable="true" class="Applicant ConsigneeName">ACCELINK TECHNOLOGIES CO.,LTD.</p>
			                ADD：<p contenteditable="true" class="ApplicantADD ConsigneeADD" style="display:inline-block;"></p><br />
			                ATTN：<p contenteditable="true" class="ApplicantATTN ConsigneeATTN" style="display:inline-block;"></p><br />
			                TEL：<p contenteditable="true" class="ApplicantTel ConsigneeTel" style="display:inline-block;"></p>
			            </td>
			        </tr>
			        <tr>
			            <td style="text-align:right;">
			                <span  class="red">起运港（保单相同）：</span>
			            </td>
			           <td >
			                <p contenteditable="true" class="Shipment"></p>
			            </td>
			        </tr>
			        <tr>
			            <td style="text-align:right;">
			                <span  class="red">目的港：</span>
			            </td>
			            <td>
			                <p contenteditable="true" class="Destination">WUHAN,P.R.CHINA</p>
			            </td>
			        </tr>
			        <tr class="ModelTd">
			            <td style="text-align:right;">
			                <span  class="red">货物信息（保单相同）：</span>
			            </td>
			            <td class="ModelList" style="line-height: 34px;"></td>
			        </tr>
			        <tr>
			            <td style="text-align:right;">
			                <span  class="red">shipping mark（保单相同）：</span>
			            </td>
			            <td>
			                <p contenteditable="true" class="ShippingMarkNO"> 4300000987</p>
			                <p style="border: 1px dashed #000;margin-left: 10px;width: 70px;"></p>
			                <p contenteditable="true" class="ShippingMarkADD"> WUHAN,P.R.CHINA</p>
			            </td>
			        </tr>
			        <tr>
			            <td style="text-align:right;">
			                <span  class="red">运单要求：</span>
			            </td>
			            <td style="font-size: 12px;line-height: 30px;" class="red">
			                <p>1. 运单中一定要有 'FREIGHT PREPAID' INDICATING FREIGHT AMOUNT</p>
			                <p>2. Consignee & Notifying applicant要依照指令写</p>
			                <p>3. 运单上应该列明：合同号，Shipping mark，货物型号，描述，数量，尺寸，重量</p>
			                <p>4. 运单右下角关于shipper或agent签名空白处加一句“ on behalf of the shipper"</p>
			                <p>5. 运单右下角关于carrier或agent处空白地加一句”as agent of the carrier 写什么航空公司”。</p>
			
			            </td>
			        </tr>
			
			    </table>
			
			        <p class="m30b changeP"  style="margin-top: 10px;display:none;" >*以上红字标识的项目需全部在运单中体现，且需跟保单完全一致。</p>
			        <p style="margin-bottom: 10px;display:none;">除去运单中的相同项外，保单的其他要求：</p>
			    <table cellpadding="0" cellspacing="0" border="1" style=" width:100%;display:none; ">
			        <tr>
			            <td style="text-align:right;">
			                <span>发票金额：</span>
			            </td>
			            <td style="width: 65%;text-align:center;">
			                <p contenteditable="true" class="InvoiceUSD tc">USD160000.00</p>
			            </td>
			        </tr>
			        <tr>
			            <td style="text-align:right;">
			                <span>保险金额：</span>
			            </td>
			            <td style="text-align:center;" >
			                <p contenteditable="true" class="InsuranceUSD tc">USD176000.00</p>
			            </td>
			        </tr>
			        <tr>
			            <td style="text-align:right;">
			                <span>最终目的地：</span>
			            </td>
			            <td style="text-align:center;" >
			                <p contenteditable="true" class="FinalADD tc">88 YOUKEYUAN ROAD,HONGSHAN DISTRICT
			                    WUHAN,HUBEI PROV.,430074,P.R.CHINA</p>
			            </td>
			        </tr>
			        <tr>
			            <td style="text-align:right;">
			                <span>保险责任至：</span>
			            </td>
			            <td style="text-align:center;" >
			                <p contenteditable="true" class="Insured tc">WUHAN WAREHOUSE</p>
			            </td>
			        </tr>
			        <tr>
			            <td style="text-align:right;">
			                <span>赔付币种：</span>
			            </td>
			            <td style="text-align:center;" >
			                <p contenteditable="true" class="Currency tc">美金</p>
			            </td>
			        </tr>
			        <tr>
			            <td style="text-align:right;">
			                <span>赔付地点：</span>
			            </td>
			            <td style="text-align:center;" >
			                <p contenteditable="true" class="Address tc">武汉</p>
			            </td>
			        </tr>
			        <tr>
			            <td style="text-align:right;">
			                <span>货物尺寸：</span>
			            </td>
			            <td>
			                <p>（保单运单需一致）</p>
			            </td>
			        </tr>
			        <tr>
			            <td style="text-align:right;">
			                <span>投保人：</span>
			            </td>
			            <td>
			                <p>深圳伊欧陆微电子系统有限公司</p>
			            </td>
			        </tr>
			        <tr>
			            <td style="text-align:right;">
			                <span>被保险人：</span>
			            </td>
			            <td>
			               <p>HK EOULU TRADING LIMITED</p>
			            </td>
			        </tr>
			        <tr>
			            <td style="text-align:right;">
			                <span>保单注意事项：</span>
			            </td>
			            <td>
			               <p style="font-size: 12px;">包装尺寸、开航日期、包装、尺寸、运输工具、起运港由运输商视实情填写,保单运单必须一致。
			                   且所有列出的项必须都在保单中体现出。保单的所有项都应有英文翻译。保单一定要涵盖航空一切险、战争险/邮包一切险、战争险（邮包）
			               </p>
			            </td>
			        </tr>
			    	</table>
				</div>
			</div>
			<input type="button" value="添加货物" class="bToggle addItem" id="addModelData" style="position:fixed;z-index:11;top: 200px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
			<input type="button" value="发送" class="bToggle " id="sendCurrent" style="display:none;position:fixed;z-index:11;top: 300px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
			 <input type="button" value="提交" class="bToggle editDom" id="submit_n" style="position:fixed;z-index:11;top: 150px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
			 <input type="button" value="提交" class="bToggle noteditDom" id="updateSubmit" style="position:fixed;z-index:11;top: 150px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
			<input type="button" value="下载" class="bToggle noteditDom" id="exportPDF1" style="position:fixed;z-index:11;top: 250px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
			<input type="button" value="关闭" class="bToggle " id="contract_close1" style="position: fixed;z-index: 11;top: 100px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
	</div>
</body>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/msgbox.js"></script>
<!-- <script src="js/msgbox_unload.js"></script> -->
<script src="js/insurance.js"></script>
<script src="js/FileSaver.js"></script>
<script src="js/jquery.wordexport.js"></script>
<!-- <script type="text/javascript" src="js/html2canvas.js"></script>  
<script type="text/javascript" src="js/jsPdf.debug.js"></script> -->
<script>
	$(".addData").click(function(){
		/*  新加的清空货物的代码（一行）*/
		$(".hidePdf .ModelTd .ModelList").empty();
		$(".hidePdf .editDom").show();
		$(".hidePdf .noteditDom").hide();
		$(".hidePdf .news").show();
		$(".hidePdf .news_update").hide();
		
	     $(".news .SONO").text(""); 
	     $(".news .PONO").text(""); 
	     $(".news .ContractNO").text(""); 
	     $(".news .TakingDate").text("");
	     $(".news .DCNO").text(""); 
	     $(".news .ConsigneeName").text(""); 
	     $(".news .ConsigneeADD").text(""); 
	     $(".news .ConsigneeATTN").text(""); 
	     $(".news .ConsigneeTel").text(""); 
	     $(".news .Shipment").text("");
	     $(".news .Destination").text(""); 
	     $(".news .Model").text(""); 
	     $(".news .Description").text(""); 
	     $(".news .Qty").text("");
	     $(".news .Size").text(""); 
	     $(".news .ShippingMarkNO").text(""); 
	     $(".news .ShippingMarkADD").text(""); 
	     $(".news .InvoiceUSD").text(""); 
	     $(".news .InsuranceUSD").text("");
	     $(".news .FinalADD").text(""); 
	     $(".news .Insured").text("");
	     $(".news .Currency").text(""); 
	     $(".news .Address").text("");
	     $(".news .InsuranceUSD").text("");
	     $(".news .Model").text(""); 
	     $(".news .Description").text("");
	     $(".news .Qty").text(""); 
	     $(".news .Size").text("");
	     
	     $(".news .InWarehouse ").text(""); 
	     $(".news .Waybill").text("");
	     
	     $(".news #Name1").val("");
	     $(".news #ADD1").val("");
	     $(".news #ATT1").val("");
	     $(".news #Tel1").val("");
	     $(".news #Shipment").val("");
	     $(".news .pickupDate").val("");
	     $(".news .pickupTime").val("");

		 $('.MailBar_cover_color').show();
		$('.hidePdf').show(); 
		 $(".MailBar_cover_color").css("height",$(".news").height()+30);
	})

	var mailBlob = "";
	var sender = '<%=request.getSession().getAttribute("email")%>';
	/*发送邮件  */
	$(".SendEmail").click(function(){
		 var thisList = $(this).parent();
		var ID = thisList.find("td").eq(0).attr("value");
		$("#SendEmail_submit").attr("value1",ID);
		var themeText = "Eoulu:"+thisList.find("td").eq(7).text()+"从"+thisList.find('td').eq(19).text()+"到"+thisList.find('td').eq(20).text()+"的提货指令-SO#"+thisList.find("td").eq(4).text();
		$(".theme").text(themeText);
		  var startAdd = thisList.find("td").eq(19).text();
			var EmailCon = "我司有一票货物从"+thisList.find('td').eq(19).text()+"运输到"+thisList.find("td").eq(20).text()+"，提货时间为"+thisList.find("td").eq(7).text()+"。";
			$(".EmailCon").text(EmailCon);
		$(".CC").text("liuyanan@eoulu.com;jiangyaping@eoulu.com;");
		
		$('.MailBar_cover_color').show();
		$(".SendEmailBox").show();
		$(".SendEmailBox .Sender").text(sender);
		
		 $(".hidePdf .editDom").hide();
		 $(".hidePdf .noteditDom").show();
		    $('.MailBar_cover_color').show();
		    $(".MailBar_cover_color").css("height",$(".content").height()+160); 
		    /* 获取隐藏信息 */
		     
	         var ID = thisList.find("td").eq(0).attr("value");
	         $(".hidePdf .InsuranceID").attr("value",ID);//暂时存放ID
		     var SONO = thisList.find("td").eq(4).text();
		     var PONO = thisList.find("td").eq(5).text();
		     var ContractNO = thisList.find("td").eq(6).text();
		     var TakingDate = thisList.find("td").eq(7).text();
		     var DCNO = thisList.find("td").eq(8).text() ;
		     var Name = thisList.find("td").eq(11).text();
		     var ADD = thisList.find("td").eq(12).text();
		     var ATT = thisList.find("td").eq(13).text();
		     var Tel = thisList.find("td").eq(14).text();
		     var ConsigneeName = thisList.find("td").eq(15).text();
		     var ConsigneeADD = thisList.find("td").eq(16).text();
		     var ConsigneeATTN = thisList.find("td").eq(17).text();
		     var ConsigneeTel = thisList.find("td").eq(18).text();
		     var Shipment = thisList.find("td").eq(19).text();
		     var Destination = thisList.find("td").eq(20).text();
		     var Model = thisList.find("td").eq(21).text();
		     var Description = thisList.find("td").eq(22).text();
		     var Qty = thisList.find("td").eq(23).text();
		     var Size = thisList.find("td").eq(24).text();
		     var ShippingMarkNO = thisList.find("td").eq(6).text();
		     var ShippingMarkADD = thisList.find("td").eq(20).text();
		     var InvoiceUSD = thisList.find("td").eq(27).text();
		     var InsuranceUSD = thisList.find("td").eq(28).text();
		     var FinalADD = thisList.find("td").eq(29).text();
		     var Insured = thisList.find("td").eq(30).text();
		     var Currency = thisList.find("td").eq(31).text();
		     var Address = thisList.find("td").eq(32).text();
		     
		     var InWarehouse  =   thisList.find("td").eq(33).text(); 
			 var WaybillNum   =   thisList.find("td").eq(34).text();
		     
		     
		     $(".news_update .SONO").text("").text(SONO); 
		     $(".news_update .PONO").text("").text(PONO); 
		     $(".news_update .ContractNO").text("").text(ContractNO); 
		     $(".news_update .TakingDate").text("").text(TakingDate);
		     $(".news_update .DCNO").text("").text(DCNO); 
		     $(".news_update .ConsigneeName").text("").text(ConsigneeName); 
		     $(".news_update .ConsigneeADD").text("").text(ConsigneeADD); 
		     $(".news_update .ConsigneeATTN").text("").text(ConsigneeATTN); 
		     $(".news_update .ConsigneeTel").text("").text(ConsigneeTel); 
		     $(".news_update .Shipment").text("").text(Shipment);
		     $(".news_update .Destination").text("").text(Destination); 
		     $(".news_update .Model").text("").text(Model); 
		     $(".news_update .Description").text("").text(Description); 
		     $(".news_update .Qty").text("").text(Qty);
		     $(".news_update .Size").text("").text(Size); 
		     $(".news_update .ShippingMarkNO").text("").text(ShippingMarkNO); 
		     $(".news_update .ShippingMarkADD").text("").text(ShippingMarkADD); 
		     $(".news_update .InvoiceUSD").text("").text(InvoiceUSD); 
		     $(".news_update .InsuranceUSD").text("").text(InsuranceUSD);
		     $(".news_update .FinalADD").text("").text(FinalADD); 
		     $(".news_update .Insured").text("").text(Insured);
		     $(".news_update .Currency").text("").text(Currency); 
		     $(".news_update .Address").text("").text(Address);
		     $(".news_update .InWarehouse ").text("").text(InWarehouse); 
		     $(".news_update .Waybill").text("").text(WaybillNum);
		 	console.log("ID"+ID)
		 	
		 	$(".news_update .ModelTd .ModelList").empty();
		 	   $.ajax({
		 	        type : 'get',
		 	        url : 'GetDeliveryAddress',
		 	        data : {
		 	        	ID : ID,
		 	        },
		 	        dataType : 'json',
		 	        success : function (data) {
		 	        	
		 	        	console.log(data)
	 	 	        	 $(".news_update .Name").text("").text(data[1].Name); 
			 	   	     $(".news_update .ADD").text("").text(data[1].Address); 
			 	   	     $(".news_update .ATT").text("").text(data[1].ATT);
			 	   	     $(".news_update .Tel").text("").text(data[1].Tel); 
		 	        	
			 	   	       	
		 	        	 for(var i = 2; i < data.length;i++){
		 	        		/*	for(var i = 5; i <  data.length;i++){ */
		        			var str = "";
	 		        		str+=''+
	 		                   '<p><a>Model：</a><a contenteditable="true" class="Model">'+data[i].Model+'</a></p>'+
	 		                  '<p><a>Description：</a><a contenteditable="true" class="Description">'+data[i].Description+'</a></p>'+
	 		                  '<p><a>Qty：</a><a contenteditable="true" class="Qty">'+data[i].Qty+'</a></p>'+
	 		                  '<p><a>Size：</a><a contenteditable="true" class="Size">'+data[i].Size+'</a></p>';
	 		        		$(".news_update .ModelTd .ModelList").append(str)
		 	        	
		 	        	} 
		 	        	 /* mailBlob =  $(".hidePdf").wordExport("Insurance","Preview");  */ 
		 	        	  mailBlob =  $(".hidePdf .news_update").wordExport("shipping instruction","Preview");  
		 	        	
		 	        },
		 	        error : function () {
		 	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！"); 
		 	        }
		 	    })
		 	 
	})
	$(document).on("click",".SendEmailBox #SendEmail_submit",function(){
		console.log(mailBlob);
		var EmailCont = $(".SendEmailBox .EmailContent").html(); 
		console.log(EmailCont)
	   var formData = new FormData();
	  /*  formData.append('Insurance', mailBlob,'Insurance.doc'); */
	  formData.append('Insurance', mailBlob,'shipping instruction.doc');
	   formData.append('Consignee', $("#search_text").text());
	   formData.append('CopyList', $(".CC").text());
	   formData.append('Subject', $(".theme").text());
	   formData.append('Content', EmailCont);
	   formData.append('Password', $(".EmailPwd").val());
	   formData.append('ID', $("#SendEmail_submit").attr("value1"));
	   console.log(formData)
	   $.ajax({
		    url: 'SendInsuranceEmail',
		    type: 'POST',
		    cache: false,
		    data: formData,
		    processData: false,
		    contentType: false,
		    beforeSend: function(XMLHttpRequest){
		        $("#SendEmail_submit").attr("disabled","disabled");
		        $("#SendEmail_submit").css({
		        	"background":"#dddddd",
		        	"color":"#808080",
		        	"border":"none",
		        	"box-shadow":"0 0 0 0 #f8fcfd",
		        	"cursor":"not-allowed"
		        });
		    },
		    complete: function(XMLHttpRequest, textStatus){
		        $("#SendEmail_submit").attr("disabled",false);
		        $("#SendEmail_submit").css({
		          "background":"#00aeef",
		          "color":"#fff",
		          "border":"solid 1px #00aeef",
		          "box-shadow":"1px 2px 5px 0 #00aeef",
		          "cursor":"pointer"
		        });
		    },
			success : function (data) {
				console.log(data == "true")
	        	if(data == "true"){
	        		$.MsgBox.Alert("提示", "发送成功！"); 
	        	}else{
	        		$.MsgBox_Unload.Alert("提示", "发送失败！请检查填写内容");
	        	}
	        },
	        error : function () {
	        	$.MsgBox_Unload.Alert("提示", "发送失败！");
	        }
		});
	
	})
	$(document).on("click",".annexHref a",function(){
		$(this).attr("href",window.URL.createObjectURL(mailBlob));
		$(this).attr("download","shipping instruction");
		var clickFlag = true;
		/* if(clickFlag){
			window.URL.revokeObjectURL($(this).attr("href"));
		} */
		/*  window.URL.revokeObjectURL($(this).attr("href")); */
	})

	var test_list = [];
  	   $.ajax({
	        type : 'get',
	        url : 'GetAllEmail',
	        dataType : 'json',
	        success : function (data) {
	        	//console.log(data);
	        	for(var i = 1 ; i < data.length;i++){
	        		test_list.push(data[i].Email);
	        	}
	        	return test_list;
	        },
	        error : function () {
	        }
	    });  
  	
	old_value = $("#search_text").text();  
	var currentText = $("#search_text").text()
    $("#search_text").keyup(function () {
    	console.log($("#search_text").css("height"))
    	$("#auto_div").css("top",parseFloat($("#search_text").css("height"))+163+"px")
    	if($(this).text() !=""){
    		AutoComplete("auto_div", "search_text", test_list);  
    	}
    }); 
 	 $("#CC").keyup(function () {  
 		$("#auto_div").css("top",parseFloat($("#CC").css("height"))+163+"px")
    	if($(this).text() !=""){
    		AutoComplete("CC_div", "CC", test_list);  
    	}
    }); 
 	 
 	 /*************自动检索邮箱**********/
    var old_value = "";  
    var highlightindex = -1;   //高亮  
    //自动完成  
    function AutoComplete(auto, search, mylist) {  
        if ($("#" + search).val() != old_value || old_value == "") {  
        var autoNode = $("#" + auto);   //缓存对象（弹出框）  
        var list = new Array();  
        var n = 0;  
        old_value = $("#" + search).text();  
        if(old_value.indexOf(";") >0){
        	 old_value = old_value.split(";").pop();
        }
        for (i in mylist) {  
            if (mylist[i].split("@eoulu.com")[0].indexOf(old_value) >= 0) {  
                list[n++] = mylist[i];  
            }  
        }  
        if (list.length == 0) {  
            autoNode.hide();  
            return;  
        }  
        autoNode.empty();  //清空上次的记录  
        for (i in list) {  
            var wordNode = list[i];   //弹出框里的每一条内容  
            var newDivNode = $("<div>").attr("id", i);    //设置每个节点的id值  
            newDivNode.attr("style", "font:14px/25px arial;height:25px;padding:0 8px;cursor: pointer;");  
            newDivNode.html(wordNode).appendTo(autoNode);  //追加到弹出框  
            //鼠标移入高亮，移开不高亮  
            newDivNode.mouseover(function () {  
                if (highlightindex != -1) {        //原来高亮的节点要取消高亮（是-1就不需要了）  
                    autoNode.children("div").eq(highlightindex).css("background-color", "white");  
                }  
                //记录新的高亮节点索引  
                highlightindex = $(this).attr("id");  
                $(this).css("background-color", "#ebebeb");  
            });  
            newDivNode.mouseout(function () {  
                $(this).css("background-color", "white");  
            });  
            
            //鼠标点击文字上屏  
            newDivNode.click(function () {  
                    //取出高亮节点的文本内容  
                  var comText = autoNode.hide().children("div").eq(highlightindex).text();  
                    highlightindex = -1;  
                    //文本框中的内容变成高亮节点的内容  
                    if($("#" + search).text().indexOf(";")>0){
                    	var searchText = $("#" + search).text().split(";");
                    	searchText.pop();
                    	searchText = searchText.join(";");
                   		 $("#" + search).text(searchText+";"+comText+";");  
                    }
                    else{
                    	$("#" + search).text(comText+";");  
                    }
                   
                })  
                if (list.length > 0) {    //如果返回值有内容就显示出来  
                    autoNode.show();  
                } else {               //服务器端无内容返回 那么隐藏弹出框  
                    autoNode.hide();  
                    //弹出框隐藏的同时，高亮节点索引值也变成-1  
                    highlightindex = -1;  
                }  
            }  
        }  
        
        //点击页面隐藏自动补全提示框  
        document.onclick = function (e) {  
            var e = e ? e : window.event;  
            var tar = e.srcElement || e.target;  
            if (tar.id != search) {  
                if ($("#" + auto).is(":visible")) {  
                    $("#" + auto).css("display", "none")  
                }  
            }  
        }  
    }  

/*********************添加运输保险指令信息************************/
/* 添加运输保险指令信息 */
$(document).on("click","#submit_n",function(){
    var SONO= ($(".news .SONO").text() == "" ? "NA": $(".news .SONO").text()); 
	 var PONO=($(".news .PONO").text()== "" ? "NA": $(".news .PONO").text()); 
	 var ContractNO= $(".news .ContractNO").text(); 
	 var TakingDate= $(".news .pickupDate").val()+" "+$(".news .pickupTime").val()+":00.0";
	 var DCNO= ($(".news .DCNO").text()== "" ? "NA": $(".news .DCNO").text()); 
	 var ConsigneeName=$(".news .ConsigneeName").eq(0).text(); 
	 var ConsigneeADD=$(".news .ConsigneeADD").eq(0).text(); 
	 var ConsigneeATTN=$(".news .ConsigneeATTN").eq(0).text(); 
	 var ConsigneeTel=$(".news .ConsigneeTel").eq(0).text(); 
	 var Shipment=$(".news #Shipment").val();
	 var Destination=$(".news .Destination").text(); 
	 var ShippingMarkADD= $(".news .Description").text(); 
    var ShippingMarkNO=$(".news .ShippingMarkNO").text(); 
    var ShippingMarkADD=$(".news .ShippingMarkADD").text(); 
/*     var InvoiceUSD= $(".news .InvoiceUSD").text(); 
    var FinalADD=$(".news .FinalADD").text(); 
    var Insured= $(".news .Insured").text();
    var Currency=$(".news .Currency").text(); 
    var Address= $(".news .Address").text(); */
    var InvoiceUSD= 1; 
    var FinalADD="NA"; 
    var Insured= "NA";
    var Currency="NA"; 
    var Address= "NA";
    
	var InWarehouse  =  ($(".news .InWarehouse").text()== "" ? "NA": $(".news .InWarehouse").text());  
	var WaybillNum   =  ($(".news .Waybill").text()== "" ? "NA": $(".news .Waybill").text());  
	    var Model=[];
	    var Description=[];
	    var Qty=[];
	    var Size=[];
	    var Name=[];
	    var ADD= new Array();
	    var ATT=[];
	    var Tel=[];
		if($(".news .ModelList .ModelTr").length != 0){
			sizeExist = "yes";
			for(var i = 0 ; i < $(".news .ModelTr").length ; i++){
				Model.push($(".news .ModelTr").eq(i).find('.Model').text());
				Description.push($(".news .ModelTr").eq(i).find('.Description').text());
				Qty.push($(".news .ModelTr").eq(i).find('.Qty').text());
				Size.push($(".news .ModelTr").eq(i).find('.Size').text());
			}
		}
		else{
			sizeExist = "no"
		}
	    
	    if($('.news input[name="Name"]').val() != ""){
	    	isExist = "yes";
	    	Name.push($('.news input[name="Name"]').val());
	    	Tel.push($('.news input[name="Tel"]').val());
	    	ATT.push($('.news input[name="ATT"]').val());
	    	ADD.push($('.news input[name="ADD"]').val());
	    }
	    else{
	    	isExist = "no";
	    }

  	   $.ajax({
	        type : 'get',
	        url : 'InsuranceAdd',
	        data : {
	        	isExist : isExist,
	        	isExistSize : sizeExist,
	        	SONO :SONO,
	        	PONO :PONO,
	        	ContractNO :ContractNO,
	        	TakingDate :TakingDate,
	        	DCNO : DCNO,
	        	Name : Name,
	        	PackingAddress : ADD,
	        	Applicant : ATT,
	        	Tel : Tel,
	        	ConsigneeName : ConsigneeName,
	        	ConsigneeADD : ConsigneeADD,
	        	ConsigneeATTN : ConsigneeATTN,
	        	ConsigneeTel : ConsigneeTel,
	        	Shipment : Shipment,
	        	Destination : Destination,
	        	Model : Model,
	        	Description : Description,
	        	Qty : Qty,
	        	Size : Size,
	        	ShippingMarkNO : ShippingMarkNO,
	        	ShippingMarkADD : ShippingMarkADD,
	        	InvoiceUSD : InvoiceUSD,
	        	FinalADD : FinalADD,
	        	Insured : Insured,
	        	Currency : Currency,
	        	Address : Address,
	        	InWarehouse : InWarehouse,
	        	WaybillNum : WaybillNum,
	        },
	        dataType : 'json',
	        success : function (data) {
	        	console.log(data);
	        	if(data == true){
	        		$.MsgBox.Alert('提示','添加成功');
	        		$('.MailBar_cover_color').hide();
	        		$('.contract_add').hide();
	        	}else{
	        		$.MsgBox_Unload.Alert('提示','添加失败，请检查！');
	        	}
	        },
	        error : function () {
	            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    });   
	    	    
    });
    
/************ 修改运输保险指令的提交**************/
$(document).on("click","#updateSubmit",function(){
	 	var ID =  $(".hidePdf .news_update .InsuranceID").attr("value");//暂时存放ID
	 	 var SONO= ($(".news_update .SONO").text() == "" ? "NA": $(".news_update .SONO").text()); 
		 var PONO=($(".news_update .PONO").text()== "" ? "NA": $(".news_update .PONO").text()); 
		 var ContractNO= $(".news_update .ContractNO").text(); 
		 var TakingDate= $(".news_update .TakingDate").text()+":00.0";
		 var DCNO= ($(".news_update .DCNO").text()== "" ? "NA": $(".news_update .DCNO").text()); 
		 var ConsigneeName=$(".news_update .ConsigneeName").eq(0).text(); 
		 var ConsigneeADD=$(".news_update .ConsigneeADD").eq(0).text(); 
		 var ConsigneeATTN=$(".news_update .ConsigneeATTN").eq(0).text(); 
		 var ConsigneeTel=$(".news_update .ConsigneeTel").eq(0).text(); 
		 var Shipment=$(".news_update .Shipment").eq(0).text();
		 var Destination=$(".news_update .Destination").text(); 
		 var ShippingMarkADD= $(".news_update .Description").text(); 
	    var ShippingMarkNO=$(".news_update .ShippingMarkNO").text(); 
	    var ShippingMarkADD=$(".news_update .ShippingMarkADD").text(); 
/* 	    var InvoiceUSD= $(".news_update .InvoiceUSD").text(); 
	    var FinalADD=$(".news_update .FinalADD").text(); 
	    var Insured= $(".news_update .Insured").text();
	    var Currency=$(".news_update .Currency").text(); 
	    var Address= $(".news_update .Address").text(); */
	    var InvoiceUSD= 1; 
	    var FinalADD="NA"; 
	    var Insured= "NA";
	    var Currency="NA"; 
	    var Address= "NA";
		var InWarehouse  =  ($(".news_update .InWarehouse").text()== "" ? "NA": $(".news_update .InWarehouse").text());  
		var WaybillNum   =  ($(".news_update .Waybill").text()== "" ? "NA": $(".news_update .Waybill").text());  
		    
	    /* 货物信息 */
	    var isExistSize;
	    var Model=[];
	    var Description=[];
	    var Qty=[];
	    var Size=[];
	    var InfoID=[];
	    /* 提货信息 */
	    var isExist;
	    var Name=[];
	    var ADD= [];
	    var ATT=[];
	    var Tel=[];
	    var AddressID=[];
	    
		if($(".news_update .ModelTr").length != 0){
			isExistSize = "yes";
			for(var i = 0 ; i < $(".news_update .ModelTr").length ; i++){
				// alert($(".news .ModelTr").eq(i).find('.Model').text())
				Model.push($(".news_update .ModelTr").eq(i).find('.Model').text());
				Description.push($(".news_update .ModelTr").eq(i).find('.Description').text());
				Qty.push($(".news_update .ModelTr").eq(i).find('.Qty').text());
				Size.push($(".news_update .ModelTr").eq(i).find('.Size').text() ==""?"" :$(".news_update .ModelTr").eq(i).find('.Size').text());
				
			/* 	修改获取infoId */
				if($(".news_update .ModelTr").eq(i).find('.InfoID').text()){
					InfoID.push($(".news_update .ModelTr").eq(i).find('.InfoID').text());
				}
				else{
					InfoID.push(0);
				}
			}
		}
		else{
			isExistSize = "no"
		}
	    if($('.news_update .Name').text() != ""){
	    	isExist = "yes";
	    	Name.push($('.news_update .Name').text());
	    	Tel.push($('.news_update .Tel').text());
	    	ATT.push($('.news_update .ATT').text());
	    	ADD.push($('.news_update .ADD').text());
	    	AddressID.push($('.news_update .ADD').attr("AddressID")); 
	    }
	    else{
	    	isExist = "no";
	    }
	    
	   $.ajax({
	        type : 'get',
	        url : 'ModifyInsurance',
	        data : {
	        	ID : ID,
	        	AddressID : AddressID, 
	        	InfoID : InfoID,
	        	isExist : isExist,
	        	isExistSize : isExistSize,
	        	SONO :SONO,
	        	PONO :PONO,
	        	ContractNO :ContractNO,
	        	TakingDate :TakingDate,
	        	DCNO : DCNO,
	        	Name : Name,
	        	PackingAddress : ADD,
	        	Applicant : ATT,
	        	Tel : Tel,
	        	ConsigneeName : ConsigneeName,
	        	ConsigneeADD : ConsigneeADD,
	        	ConsigneeATTN : ConsigneeATTN,
	        	ConsigneeTel : ConsigneeTel,
	        	Shipment : Shipment,
	        	Destination : Destination,
	        	Model : Model,
	        	Description : Description,
	        	Qty : Qty,
	        	Size : Size,
	        	ShippingMarkNO : ShippingMarkNO,
	        	ShippingMarkADD : ShippingMarkADD,
	        	InvoiceUSD : InvoiceUSD,
	        	FinalADD : FinalADD,
	        	Insured : Insured,
	        	Currency : Currency,
	        	Address : Address,
	        	InWarehouse : InWarehouse,
	        	WaybillNum : WaybillNum,
	        },
	        dataType : 'json',
	        success : function (data) {
	        	console.log(data);
	        	console.log(typeof data);
	        	if (data == true) {
	        		$.MsgBox.Alert('提示','修改成功');
	        		$('.MailBar_cover_color').hide();
	            	$('.contract_update').hide();
	        	}else{
	        		$.MsgBox_Unload.Alert('提示','修改失败，请检查！');
	        	}
	        },
	        error : function () {
	            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    });   
	    	    
 });
$(document).on("click",".contract_add .DelThisTr",function(){
	$(this).parent().parent().remove();
})  
 
 /***********  模板显示****************/   
 $('.contract-show').click(function () {
		 $(".hidePdf .editDom").hide();
		$(".hidePdf .noteditDom").show();
		$(".hidePdf .news_update").show();
		$(".hidePdf .news").hide();
	    /* 获取隐藏信息 */
	     
         var thisList = $(this).parent();
         var ID = thisList.find("td").eq(0).attr("value");
         $(".hidePdf .news_update .InsuranceID").attr("value",ID);//暂时存放ID
	     var SONO = thisList.find("td").eq(4).text();
	     var PONO = thisList.find("td").eq(5).text();
	     var ContractNO = thisList.find("td").eq(6).text();
	     var TakingDate = thisList.find("td").eq(7).text().slice(0,16);
	     var DCNO = thisList.find("td").eq(8).text() ;
	     var Name = thisList.find("td").eq(11).text();
	     var ADD = thisList.find("td").eq(12).text();
	     var ATT = thisList.find("td").eq(13).text();
	     var Tel = thisList.find("td").eq(14).text();
	     var ConsigneeName = thisList.find("td").eq(15).text();
	     var ConsigneeADD = thisList.find("td").eq(16).text();
	     var ConsigneeATTN = thisList.find("td").eq(17).text();
	     var ConsigneeTel = thisList.find("td").eq(18).text();
	     var Shipment = thisList.find("td").eq(19).text();
	     var Destination = thisList.find("td").eq(20).text();
	     var Model = thisList.find("td").eq(21).text();
	     var Description = thisList.find("td").eq(22).text();
	     var Qty = thisList.find("td").eq(23).text();
	     var Size = thisList.find("td").eq(24).text();
	     var ShippingMarkNO = thisList.find("td").eq(6).text();
	     var ShippingMarkADD = thisList.find("td").eq(20).text();
	     var InvoiceUSD = thisList.find("td").eq(27).text();
	     var InsuranceUSD = thisList.find("td").eq(28).text();
	     var FinalADD = thisList.find("td").eq(29).text();
	     var Insured = thisList.find("td").eq(30).text();
	     var Currency = thisList.find("td").eq(31).text();
	     var Address = thisList.find("td").eq(32).text();
	     
	     var InWarehouse  =   thisList.find("td").eq(33).text(); 
		 var WaybillNum   =   thisList.find("td").eq(34).text();
	     
	     $(".news_update .SONO").text("").text(SONO); 
	     $(".news_update .PONO").text("").text(PONO); 
	     $(".news_update .ContractNO").text("").text(ContractNO); 
	     $(".news_update .TakingDate").text("").text(TakingDate);
	     $(".news_update .DCNO").text("").text(DCNO); 
	     $(".news_update .ConsigneeName").text("").text(ConsigneeName); 
	     $(".news_update .ConsigneeADD").text("").text(ConsigneeADD); 
	     $(".news_update .ConsigneeATTN").text("").text(ConsigneeATTN); 
	     $(".news_update .ConsigneeTel").text("").text(ConsigneeTel); 
	     $(".news_update .Shipment").text("").text(Shipment);
	     $(".news_update .Destination").text("").text(Destination); 
	     $(".news_update .Model").text("").text(Model); 
	     $(".news_update .Description").text("").text(Description); 
	     $(".news_update .Qty").text("").text(Qty);
	     $(".news_update .Size").text("").text(Size); 
	     $(".news_update .ShippingMarkNO").text("").text(ShippingMarkNO); 
	     $(".news_update .ShippingMarkADD").text("").text(ShippingMarkADD); 
	     $(".news_update .InvoiceUSD").text("").text(InvoiceUSD); 
	     $(".news_update .InsuranceUSD").text("").text(InsuranceUSD);
	     $(".news_update .FinalADD").text("").text(FinalADD); 
	     $(".news_update .Insured").text("").text(Insured);
	     $(".news_update .Currency").text("").text(Currency); 
	     $(".news_update .Address").text("").text(Address);
	     $(".news_update .InWarehouse ").text("").text(InWarehouse); 
	     $(".news_update .Waybill").text("").text(WaybillNum);
	 	console.log("ID"+ID)
	 	
	 	$(".news_update .ModelTd .ModelList").empty();
	 	   $.ajax({
	 	        type : 'get',
	 	        url : 'GetDeliveryAddress',
	 	        data : {
	 	        	ID : ID,
	 	        },
	 	        dataType : 'json',
	 	        success : function (data) {
	 	        	
	 	        	console.log(data)
 	 	        	 $(".news_update .Name").text("").text(data[1].Name); 
		 	   	     $(".news_update .ADD").text("").text(data[1].Address); 
		 	   	 $(".news_update .ADD").attr("AddressID",data[1].AddressID); 
		 	   	     $(".news_update .ATT").text("").text(data[1].ATT);
		 	   	     $(".news_update .Tel").text("").text(data[1].Tel); 
		 	   	 
	 	        	 for(var i = 2; i < data.length;i++){
	 	        	
	        			var str = "";
 		        		str+='<div class="ModelTr">'+
 		        		   '<p  style="display:none"><a>InfoID：</a><a contenteditable="true" class="InfoID">'+data[i].InfoID+'</a></p>'+
 		                   '<p><a>Model：</a><a contenteditable="true" class="Model">'+data[i].Model+'</a></p>'+
 		                  '<p><a>Description：</a><a contenteditable="true" class="Description">'+data[i].Description+'</a></p>'+
 		                  '<p><a>Qty：</a><a contenteditable="true" class="Qty">'+data[i].Qty+'</a></p>'+
 		                  '<p><a>Size：</a><a contenteditable="true" class="Size">'+data[i].Size+'</a></p></div>';
 		        		$(".news_update .ModelTd .ModelList").append(str)
 		        		 $(".MailBar_cover_color").css("height",$(".news_update").height()+30);
	 	        	} 
	 	        	
	 	        },
	 	        error : function () {
	 	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！"); 
	 	        }
	 	    })
	 	 
	    $('.hidePdf').show(); 
	    $('.MailBar_cover_color').show();  
 })

$('#contract_close1').click(function () {
    $('.MailBar_cover_color').hide();
    $('.hidePdf').hide();
});

//点击添加
function AddContract() {
	$(".ModelTr").not(".FirstModelTr").remove();

    $('.MailBar_cover_color').show();
    $('.contract_add').show();
};

//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function () {
    window.location.reload();
});

//点击关闭
$('.contractAdd_close').click(function () {
    $('.MailBar_cover_color').hide();
    $('.contract_add').hide();
});

$('.contractUpdate_close').click(function () {
    $('.MailBar_cover_color').hide();
    $('.contract_update').hide();
});
$('#contract_close1').click(function () {
    $('.MailBar_cover_color').hide();
    $('.hidePdf').hide();
});

//点击取消
$('#add_cancel').click(function () {
    $('.MailBar_cover_color').hide();
    $('.contract_add').hide();
});

$('#update_cancel').click(function () {
    $('.MailBar_cover_color').hide();
    $('.contract_update').hide();
});
$("#SendEmail_cancel,.SendEmailBox_close").click(function(){
	 $('.MailBar_cover_color').hide();
     $('.SendEmailBox').hide();
})

  /******************************导出*********************************/
document.getElementById("exportPDF1").onclick=function(){
	$(".news_update").wordExport("shipping instruction","download");
}
/****************** 跳页 **********************/

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

// function globalCopyPos3(){
// 	if($("body").height() <= $(window).height()){
// 		var globalNewH = $(window).height() -30;
// 		$("#bodyBottom").css("top",globalNewH+"px");
// 	}else{
// 	 	var globalNewH2 = $(document.body).height() - 30;
// 	 	$("#bodyBottom").css("top",globalNewH2+"px");
// 	}
// }

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
	 
	//判断是否已发送，改变颜色
	for(var i = 1 ; i < $("#table1 tr").length; i++){
		 if($("#table1 tr").eq(i).find(".SendEmail").text()=="已发送"){
			 $("#table1 tr").eq(i).find(".SendEmail").css("color","green");
		 }
		 else{
			 $("#table1 tr").eq(i).find(".SendEmail").text("未发送")
			 $("#table1 tr").eq(i).find(".SendEmail").css("color","red");
		 }
		 var time = $("#table1 tr").eq(i).find("td").eq(7).text().slice(0,16);
		 $("#table1 tr").eq(i).find("td").eq(7).text(time);
	}
	
	
	 $(".Domestic,.ExitOrEn").click(function(){
	 	var eouluProjectHref = window.location.href;
	 	if(eouluProjectHref.indexOf("cfChicken8/")>-1 || eouluProjectHref.indexOf("Logistics/")>-1){
	 		eouluProjectHref = eouluProjectHref.indexOf("cfChicken8/")>-1?eouluProjectHref.split("cfChicken8/")[0]+"cfChicken8/":eouluProjectHref.split("Logistics/")[0]+"Logistics/";
	 	}
		 if($(this).attr("Class") == "Domestic"){
			 // document.getElementById("ToTransportDirective").click();
			 window.location.href = eouluProjectHref + "TransportDirective";
		 }
		 else{
			 // document.getElementById("ToInsurance").click();
			 window.location.href = eouluProjectHref + "Insurance";
		 }
	 })

	//  globalCopyPos3();
	// $(window).on("resize",globalCopyPos3);
});


/*********模板 删除*******/
 $(document).on("dblclick",".ModelTr",function () {
	 var msg = "确定删除Model:"+$(this).find(".Model").text();
	 var that =  $(this);
	 if($("#submit_n").css("display") != "none"){  //添加
		 that.remove();
	 }
	 else{
		 $.MsgBox_Unload.Confirm("提示",msg,function(){
			var InfoID = that.find(".InfoID").text();
			$.ajax({
		        type : 'get',
		        url : 'DeleteGoodsInfo',
		        data : {
		        	ID : InfoID
		        },
		        dataType : 'json',
		        success : function (data) {
		        	 that.remove();
		        },
		        error : function () {
		        }
		    });	
		 });
	 }
	 
});
$(document).on("click"," .addItem",function(){
	var str ='<div class="ModelTr"><p><a>Model：</a><a contenteditable="true" class="Model"></a></p>'+
       '<p><a>Description：</a><a contenteditable="true" class="Description"></a></p>'+
       '<p><a>Qty：</a><a contenteditable="true" class="Qty"></a></p>'+
       '<p><a>Size：</a><a contenteditable="true" class="Size"></a></p></div>';
	
	 if($("#submit_n").css("display") != "none"){  //添加
		 $(".news .ModelTd .ModelList").append(str)
	 }
	 else{
		 $(".news_update .ModelTd .ModelList").append(str)
	 }
})


$("#table_Insurance .ContractNO").on("blur",function(){
		$("#table_Insurance .ShippingMarkNO").text($(this).text())
})
$("#table_Insurance .Destination").on("blur",function(){
		$("#table_Insurance .ShippingMarkADD").text($(this).text())
})
 
$("#table_Insurance .ConsigneeName,#table_Insurance .ConsigneeADD,#table_Insurance .ConsigneeATTN,#table_Insurance .ConsigneeTel").on("blur",function(){
		var currentClass = $(this).attr("class");
		$("#table_Insurance ."+currentClass).text($(this).text())
});
$(document).on("click",".eyes_psw",function(){
	if ($(this).siblings("input.EmailPwd").attr("type") == "text") {
	    $(this).siblings("input.EmailPwd").attr("type","password");  
	}else{
	  $(this).siblings("input.EmailPwd").attr("type","text"); 
	} 
});
//模板信息联动
$(".news .NameSelect").on("change",function(){
	var sel_index = $(this).get(0).selectedIndex;
	$("#Name1").val(this.options[sel_index].text);
	$("#ADD1").val($(".news .ADDSelect option").eq(sel_index).text());
	$("#ATT1").val($(".news .ATTSelect option").eq(sel_index).text());
	$("#Tel1").val($(".news .TelSelect option").eq(sel_index).text());
});

</script>
</html>
