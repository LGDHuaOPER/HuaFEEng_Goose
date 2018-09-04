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
<title>发货通知</title>
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
<link rel="stylesheet" type="text/css" href="css/shipment.css">
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
				action="GetShipmentRoute">
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
							value="${fn:split('客户名称,合同名称,合同号',',')}"></c:set>
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
					<input type="button" value="添加" class="bToggle" onclick="AddContract()">
				</div>

		 </form>  
			<table border="1" cellspacing="0" cellspadding="0" id="table1">
				<tr>
					<td>序号</td>
					<td >修改</td>
					<td>客户名称 </td>
					<td>合同名称</td>
					<td>合同号</td>
					<td style="width: 145px;">模板预览（可导出）</td>
				</tr>
			<%-- 			<tr>
						<td value="${orderInfo['ID']}">1</td>
						<td> <i class="fa fa-edit contract-edit" value="${orderInfo['ID']}"></i></td>
						<td >123</td>
						<td>123</td>
						<td>123</td>
						<td><i class="fa fa-eye contract-show"></i></td>
					</tr> --%>
 				 <c:forEach var="orderInfo" items="${shipment}" varStatus="status">
					<c:if test="${status.index>0}">
						<tr class="tbody_tr">
							<td value="${orderInfo['ID']}">${status.index+(currentPage-1)*10}</td>
							<td> <i class="fa fa-edit contract-edit" value="${orderInfo['ID']}"></i></td>
							<td title="${orderInfo['Customer']}">${orderInfo['Customer']}</td>
							<td title="${orderInfo['ContractTitle']}">${orderInfo['ContractTitle']}</td>
							<td title="${orderInfo['ContractNO']}">${orderInfo['ContractNO']}</td>
							<td><i class="fa fa-eye contract-show"></i></td>
							<td style="display:none;">${orderInfo['InfoAPP']}</td>    <!--6  -->
							<td style="display:none;">${orderInfo['InfoADD']}</td><!--7  -->
							<td style="display:none;">${orderInfo['InfoTel']}</td><!--8  -->
							<td style="display:none;">${orderInfo['DCNO']}</td><!--9 -->
							<td style="display:none;">${orderInfo['AirWaybillNO']}</td>    <!--10  -->
							<td style="display:none;">${orderInfo['FlightNO']}</td><!--11  -->
							<td style="display:none;">${orderInfo['GoodsDescription']}</td><!--12  -->
							<td style="display:none;">${orderInfo['Qty']}</td><!--13 -->
							<td style="display:none;">${orderInfo['Total']}</td>    <!--14  -->
							<td style="display:none;">${orderInfo['Value']}</td><!--15  -->
							<td style="display:none;">${orderInfo['CarrierName']}</td><!--16 -->
							<td style="display:none;">${orderInfo['DepartAirport']}</td><!--17 -->
							<td style="display:none;">${orderInfo['DestAirport']}</td><!--18  -->
							<td style="display:none;">${orderInfo['Date']}</td><!--19 -->
							<td style="display:none;">${orderInfo['DepartureDate']}</td><!--20 -->
							<td style="display:none;">${orderInfo['DIM']}</td><!--21 -->
							<td style="display:none;">${orderInfo['GrossWeight']}</td><!--22 -->
						</tr>
					</c:if>
				</c:forEach> 
			</table>	
		
	 	 <c:choose>
				<c:when test="${queryType == 'common'}">
					<c:set var="queryUrl"
					value="Shipment?type1=${classify1 }&searchContent1=${parameter1}&selected=${str}&currentPage=">
					</c:set>
				</c:when>
				<c:otherwise>
					<c:set var="queryUrl"
					value="GetShipmentRoute?type1=${classify1 }&searchContent1=${parameter1}&type2=${classify2}&searchContent2=${parameter2}&selected=${str}&currentPage=">
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
						onkeyup="value=value.replace(/[^\d]/g,'') "> 页 <input
						type="button" class="bToggle" value="GO" id="Gotojump"
						name="Gotojump" onclick="PageJump('${queryUrl}')"> <input
						type="button" class="bToggle" value="尾页" id="lastPage"
						name="lastPage" onclick="LastPage('${queryUrl}')">
				</div>
			</div>  
		</div>
	</div>
	<div class="MailBar_cover_color"></div>
	
		<!-- -------------------------  添加发货通知信息 ----------------------------------->
	<div class="contract_add">
		<div class="contract_title">添加发货通知信息 </div>
		<div class="contractAdd_close">关闭</div>
		<div class="basic_info">
			<div class="table_title">发货通知信息 </div>
			<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
				<tbody>
					<tr>	
						<td>CONTRACT NO.</td>
						<td><input type="text" name="contract_no" value=""  id="contract_no"></td>
						<td>DC No.</td>
						<td><input type="text" name="DC_NO" value=""></td>
					</tr>
					<tr>
						<td>Applicant</td>
						<td>
							<input type="text" name="Applicant" value="" style="position:absolute;width:162px;" id="Applicant" class="InfoApp">
							<span>
								<select class="APPSelect">
									
									<option value="100%">HOPE INVESTMENT DEVELOPMENT CORP., LTD.</option>
									<option value="10%" >CERNET CORP CERNET TOWER, TSINGHUA SCIENCE PARK </option>
									<option value="90%" >THE ORIENTAL SCIENTIFIC INSTRUMENT IMPORT AND EXPORT (GROUP) CORPORATION</option>
									<option value="10%" >XIAMEN XINDECO LTD.</option>
									<option value="10%" >CHINA NATIONAL INSTRUMENTS IMPORT AND EXPORT (GROUP) CORPORATION</option>
								</select>
							</span>
							
						</td>
						<td class="hideTD">Add</td>
						<td class="hideTD">
							<input type="text" name="ADD" value="" style="position:absolute;width:162px;" id="ADD"  class="InfoAdd">
							<span>
								<select onchange="document.getElementById('ADD').value=this.options[this.selectedIndex].text"  class="ADDSelect">
									<option value="GERMANY">CETC MANSION, No.5 WULUTONG NORTH STREET, XICHENG DISTRICT, BEIJING, CHINA, 100120</option>
									<option value="USA" >BUILDING 8, NO.1 ZHONGGUANCUN EAST ROAD, HAIDIAN DISTRICT BEIJING 100084, P.R. CHINA</option>
									<option value="GERMANY">14/F, YINDU BUILDING, NO.67 FU CHENG ROAD,HAIDIAN DISTRICT, BEIJING </option>
									<option value="USA" >7/F, XINDECO BLDG., NO.27,XINGLONG ROAD, HULI, XIAMEN, CHINA</option>
									<option value="USA" >RM615, INSTRIMPEX BUILDING, NO.6, XIZHIMENWAI STREET, BEIJING CHINA</option>
								</select>
							</span>
						</td>
						<td  class="hideTD">TEL/FAX</td>
						<td  class="hideTD">
							<input type="text" name="TEL" value="" style="position:absolute;width:162px;" id="TEL"  class="InfoTel">
							<span>
								<select onchange="document.getElementById('TEL').value=this.options[this.selectedIndex].text" class="TELSelect">
									<option value="GERMANY">TEL:(+86)10 82209151&&FAX: (+86)10 82209359</option>
									<option value="USA" >TEL: +86-(0)21-64272922&&FAX: +86-(0)21-64277370</option>
									<option value="GERMANY">TEL: 86-10-68725599&&FAX: 86-10-68726610</option>
									<option value="USA" >TEL:0086-592-5620655&&FAX: 0086-592-6021752</option>
									<option value="USA" >TEL: 0086-10-88316230/0086-13810416767&&FAX: 0086-10-88316233</option>
								</select>
							</span>	
						</td>
					</tr>
					<tr>
						<td>Air Waybill NO</td>
						<td><input type="text" name="AirWaybillNO" value=""></td>
						<td>Flight NO</td>
						<td><input type="text" name="FlightNO" value=""></td>
						<td>Departure date</td>
						<td><input type="date" name="DepartureDate" value=""></td>
					</tr>
					
					<tr>
						<td>Depart Airport</td>
						<td>
							<input type="text" name="DepartAirport" value="" style="position:absolute;width:162px;" id="DepartAirport">
							<span>
								<select onchange="document.getElementById('DepartAirport').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="Pack" >GERMANY</option>
									<option value="Pack" >USA</option>
									<option value="Pack" >HONGKONG</option>
								</select>
							</span>
						</td>
						<td>DEST Airport</td>
						<td>
							<input type="text" name="DestAirport" value="" style="position:absolute;width:162px;" id="DestAirport">
							<span>
								<select onchange="document.getElementById('DestAirport').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="GERMANY">BEIJING</option>
									<option value="USA" >CHENGDU</option>
									<option value="HK" >HONGKONG</option>
									<option value="HK" >SHENZHEN</option>
									<option value="HK" >SHANGHAI</option>
									<option value="HK" >XIAMEN </option>
									<option value="HK" >GUANGZHOU </option>
								</select>
							</span>
						</td>
					</tr>
					<tr class="ModelTr">
						<td>Model</td>
						<td class="ModelTd">
							<input type="text" name="Model" value="" style="position:absolute;width:162px;" id="Model">
							<span>
								<select onchange="document.getElementById('Model').value=this.options[this.selectedIndex].text">
									<option value="SUMMIT12000B-S">SUMMIT12000B-S</option>
									<option value="EPS150LT">EPS150LT</option>
									<option value="SUMMIT12000B-M">SUMMIT12000B-M</option>
									<option value="SUMMIT11000B-M">SUMMIT11000B-M</option>
									<option value="T200-STA-M">T200-STA-M</option>	
									<option value="PA200DS-BR-011">PA200DS-BR-011</option>
									<option value="PA200-BR-021">PA200-BR-021</option>
									<option value="EPS150MMW">EPS150MMW</option>
									<option value="CM300">CM300</option>										
								</select>
							</span>
							
						</td>
						<td>Qty</td>
						<td  class="QtyTd">
							<input type="text" name="Qty" value="" style="position:absolute;width:162px;" id="Qty">
							<span>
								<select onchange="document.getElementById('Qty').value=this.options[this.selectedIndex].text">
									<option value="SUMMIT12000B-S">1</option>
									<option value="EPS150LT">2</option>
									<option value="SUMMIT12000B-M">3</option>
								</select>
							</span>
						</td>
						
					</tr>
					<tr>	
						<td>Total</td>
						<td><input type="text" name="Total" value="" class="TotalTd" ></td>
						<td>Value</td>
						<td><input type="text" name="Value" value=""  onkeyup = "fm(this)" ></td>
					</tr>
					<tr>	
						<td>Name of Carrier</td>
						<td><input type="text" name="CarrierName" value=""    ></td>
						<td>Date</td>
						<td><input type="date" name="Date" value=""></td>
					</tr>
				</tbody>
			</table>
			<div class="table_title"> <input type="button" name="addItem" value="添加尺寸信息" class="bToggle addItem"> </div>		
		</div>
		<div class="edit_btn">
			<input type="button" value="提交" class="bToggle" id="add_submit">
			<input type="button" value="取消" class="bToggle" id="add_cancel">
		</div>
	</div>
	
	<!-- -------------------------  修改发货通知信息 ----------------------------------->
	<div class="contract_update">
		<div class="contract_title">修改发货通知信息 </div>
		<div class="contractUpdate_close">关闭</div>
		<div class="basic_info">
			<div class="table_title">发货通知信息 </div>
			<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
				<tbody>
					<tr>	
						<td>CONTRACT NO.</td>
						<td><input type="text" name="contract_no" value=""  id="contract_no1"></td>
						<td>DC No.</td>
						<td><input type="text" name="DC_NO" value=""></td>
					</tr>
					<tr>
						<td>Applicant</td>
						<td>
							<input type="text" name="Applicant" value="" style="position:absolute;width:162px;" id="Applicant1" class="InfoApp">
							<span>
								<select class="APPSelect1">
									<option value="100%">HOPE INVESTMENT DEVELOPMENT CORP., LTD.</option>
									<option value="10%" >CERNET CORP CERNET TOWER, TSINGHUA SCIENCE PARK </option>
									<option value="90%" >THE ORIENTAL SCIENTIFIC INSTRUMENT IMPORT AND EXPORT (GROUP) CORPORATION</option>
									<option value="10%" >XIAMEN XINDECO LTD.</option>
									<option value="10%" >CHINA NATIONAL INSTRUMENTS IMPORT AND EXPORT (GROUP) CORPORATION</option>
								</select>
							</span>
							
						</td>
						<td class="hideTD">Add</td>
						<td class="hideTD">
							<input type="text" name="ADD" value="" style="position:absolute;width:162px;" id="ADD1"  class="InfoAdd">
							<span>
								<select onchange="document.getElementById('ADD1').value=this.options[this.selectedIndex].text"  class="ADDSelect">
									<option value="GERMANY">CETC MANSION, No.5 WULUTONG NORTH STREET, XICHENG DISTRICT, BEIJING, CHINA, 100120</option>
									<option value="USA" >BUILDING 8, NO.1 ZHONGGUANCUN EAST ROAD, HAIDIAN DISTRICT BEIJING 100084, P.R. CHINA</option>
									<option value="GERMANY">14/F, YINDU BUILDING, NO.67 FU CHENG ROAD,HAIDIAN DISTRICT, BEIJING </option>
									<option value="USA" >7/F, XINDECO BLDG., NO.27,XINGLONG ROAD, HULI, XIAMEN, CHINA</option>
									<option value="USA" >RM615, INSTRIMPEX BUILDING, NO.6, XIZHIMENWAI STREET, BEIJING CHINA</option>
								</select>
							</span>
						</td>
						<td  class="hideTD">TEL/FAX</td>
						<td  class="hideTD">
							<input type="text" name="TEL" value="" style="position:absolute;width:162px;" id="TEL1"  class="InfoTel">
							<span>
								<select onchange="document.getElementById('TEL1').value=this.options[this.selectedIndex].text" class="TELSelect">
									<option value="GERMANY">TEL:(+86)10 82209151&&FAX: (+86)10 82209359</option>
									<option value="USA" >TEL: +86-(0)21-64272922&&FAX: +86-(0)21-64277370</option>
									<option value="GERMANY">TEL: 86-10-68725599&&FAX: 86-10-68726610</option>
									<option value="USA" >TEL:0086-592-5620655&&FAX: 0086-592-6021752</option>
									<option value="USA" >TEL: 0086-10-88316230/0086-13810416767&&FAX: 0086-10-88316233</option>
								</select>
							</span>	
						</td>
					</tr>
					<tr>
						<td>Air Waybill NO</td>
						<td><input type="text" name="AirWaybillNO" value=""></td>
						<td>Flight NO</td>
						<td><input type="text" name="FlightNO" value=""></td>
						<td>Departure date</td>
						<td><input type="date" name="DepartureDate" value=""></td>
					</tr>
					
					<tr>
						<td>Depart Airport</td>
						<td>
							<input type="text" name="DepartAirport" value="" style="position:absolute;width:162px;" id="DepartAirport1">
							<span>
								<select onchange="document.getElementById('DepartAirport1').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="Pack" >GERMANY</option>
									<option value="Pack" >USA</option>
									<option value="Pack" >HONGKONG</option>
								</select>
							</span>
						</td>
						<td>DEST Airport</td>
						<td>
							<input type="text" name="DestAirport" value="" style="position:absolute;width:162px;" id="DestAirport1">
							<span>
								<select onchange="document.getElementById('DestAirport1').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="GERMANY">BEIJING</option>
									<option value="USA" >CHENGDU</option>
									<option value="HK" >HONGKONG</option>
									<option value="HK" >SHENZHEN</option>
									<option value="HK" >SHANGHAI</option>
									<option value="HK" >XIAMEN </option>
									<option value="HK" >GUANGZHOU </option>
								</select>
							</span>
						</td>
					</tr>
					<tr class="ModelTr">
						<td>Model</td>
						<td class="ModelTd">
							<input type="text" name="Model" value="" style="position:absolute;width:162px;" id="Model1">
							<span>
								<select onchange="document.getElementById('Model1').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="SUMMIT12000B-S">SUMMIT12000B-S</option>
									<option value="EPS150LT">EPS150LT</option>
									<option value="SUMMIT12000B-M">SUMMIT12000B-M</option>
									<option value="SUMMIT11000B-M">SUMMIT11000B-M</option>
									<option value="T200-STA-M">T200-STA-M</option>	
									<option value="PA200DS-BR-011">PA200DS-BR-011</option>
									<option value="PA200-BR-021">PA200-BR-021</option>
									<option value="EPS150MMW">EPS150MMW</option>
									<option value="CM300">CM300</option>										
								</select>
							</span>
							
						</td>
						<td>Qty</td>
						<td  class="QtyTd">
							<input type="text" name="Qty" value="" style="position:absolute;width:162px;" id="Qty1">
							<span>
								<select onchange="document.getElementById('Qty1').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="SUMMIT12000B-S">1</option>
									<option value="EPS150LT">2</option>
									<option value="SUMMIT12000B-M">3</option>
								</select>
							</span>
						</td>
						
					</tr>
					<tr>	
						<td>Total</td>
						<td><input type="text" name="Total" value="" class="TotalTd" ></td>
						<td>Value</td>
						<td><input type="text" name="Value" value=""  onkeyup = "fm(this)" ></td>
					</tr>
					<tr>	
						<td>Name of Carrier</td>
						<td><input type="text" name="CarrierName" value=""    ></td>
						<td>Date</td>
						<td><input type="date" name="Date" value=""></td>
					</tr>
				</tbody>
			</table>
			<div class="table_title"> <input type="button" name="addItem" value="添加尺寸信息" class="bToggle addItem"> </div>		
		</div>
		<div class="edit_btn">
			<input type="button" value="提交" class="bToggle" id="update_submit">
			<input type="button" value="取消" class="bToggle" id="update_cancel">
		</div>
	</div>
	
	<div class="hidePdf" style="display:none;">
			<div id="view" class="news" style="font-family: initial;position:absolute;z-index:11;top:0;left:0;background:#fff;font-size:12px">
    <!-- 页眉-->
    <div class="yemei" style="margin-bottom:20px; margin-top: 20px;">
        <div style="width:100%;height:70px;">
            <div class="logo lf" >
                <img src="image/EOULUlogo.png" style="width: 175px; height: 61px;">
            </div>
            <div class="rt" style="margin-top:40px;color: #000080;">EOULU Technology</div>
        </div>

        <hr>
    </div>

    <div id="Shipment">
    <p class="f16 b m60b">FAX OF PARTICULARS OF SHIPMENT</p>

    <div style="display: inline-block;" class="lf m30b TO">To:</div>
    <div contenteditable="true" class="lf InfoApp" style="border-bottom: 1px solid #000;min-width: 150px;">XIAMEN XINDECO LTD. </div>

    <div class="content_text m30b cf">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The goods under this L/C ( DC No.  <span contenteditable="true" class="DCNO">36100LC1700172 </span>) has been shipped out <span contenteditable="true" class="Date">2017.03.08</span> .
        The following is the information about this shipment:
    </div>

    <div>
        <table cellpadding="0" cellspacing="0" border="1" style=" width:90%;margin-bottom: 20px;">
            <tr>
                <td>
                    <p>Shipper Info:</p>
                    <P>HK EOULU TRADING LIMITED</P>
                    <P>ROOM 1501, GRAND MILLENNIUM PLAZA
                        (LOWER BLOCK), 181 QUEEN’S ROAD
                        CENTRAL, HONG KONG</P>
                    <P>Tel : 00852-21527388 </P>
                    <P>Fax: 00852-35719160</P>

                </td>
                <td>
                    <p>Applicant Info: </p>
                    <P contenteditable="true" class="InfoApp">HK EOULU TRADING LIMITED</P>
                    <P contenteditable="true" class="InfoAdd" >ROOM 1501, GRAND MILLENNIUM PLAZA
                        (LOWER BLOCK), 181 QUEEN’S ROAD
                        CENTRAL, HONG KONG</P>
                    <P contenteditable="true" class="InfoTel">Tel : 00852-21527388 </P>
                    <P contenteditable="true" class="InfoFax">Fax: 00852-35719160</P>
                </td>
            </tr>

            <tr>
                <td>
                    <p>DC No.: </p>
                    <p contenteditable="true" class="DCNO">36100LC1700172</p>
                </td>
                <td>
                    <p>Contract No.: </p>
                    <p contenteditable="true" class="ContractN0">SACS-TD-1610-0250/REV.1</p>
                </td>
            </tr>

            <tr>
                <td>
                    <p>Air Waybill No:</p>
                    <p contenteditable="true" class="AirWayNO">160 35289844</p>
                </td>
                <td>
                    <p>Flight No.: </p>
                    <p contenteditable="true" class="FlightN0">CX288, KA616</p>
                </td>
            </tr>

            <tr >
                <td  rowspan="3">
                    <p>Description of goods:</p>
                    
					<div class="DimBox" >
						<p>
	                        <span class="noneLine">Model </span>
	                        <span class="noneLine">QTY</span>
	                    </p>
	                    <p class="ModelBox">
	                        <span contenteditable="true" class="Model">SUMMIT11000B-M</span>
	                        <span contenteditable="true"  class="Qty">1</span>
	                    </p>
				<!-- 		 <p>
	                        <span  class="noneLine"> DIM; </span>
	                        <span  class="noneLine" >Gross Weight</span>
	                    </p>
	                    <p>
	                        <span contenteditable="true" class="DIM">80x60x95CM</span>
	                        <span contenteditable="true"  class="GrossWeight">  60KG </span>
	                    </p>
	                     <p>
	                        <span  class="noneLine"> DIM; </span>
	                        <span  class="noneLine" >Gross Weight</span>
	                    </p>
	                    <p>
	                        <span contenteditable="true" class="DIM">80x60x95CM</span>
	                        <span contenteditable="true"  class="GrossWeight">  60KG </span>
	                    </p> -->
					</div>
                    <p class=""><span class="noneLine">Total</span><span class="Total"  contenteditable="true" style="margin: 0 3px;">2 </span><span class="noneLine"> packages</span></p>
                    <p  class=""><span class="noneLine">Value: $</span><span class="Value" contenteditable="true">152,000 </span></p>
                </td>
                <td>
                    <p>Departure Date: </p>
                    <p contenteditable="true" class="DepartureDate"></p>
                </td>
            </tr>
            <tr>
                <td>
                    <p>Depart Airport:  </p>
                    <p class="">
                        <span contenteditable="true" class="DepartAirport"></span>
                        <span class="noneLine">AIRPORT</span>
                    </p>
                </td>
            </tr>
            <tr>   
                <td>
                    <p>DEST Airport:  </p>
                    <p class="">
                        <span contenteditable="true" class="DestAirport"></span>
                        <span class="noneLine"> AIRPORT CHINA</span>
                    </p>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <p>
                        <span class="noneLine">Name of Carrier:</span>
                        <span contenteditable="true" class="noneLine CarrierName" ></span>
                    </p>
                </td>
            </tr>


        </table>


    </div>

    <div style="display: inline-block;" class="cf lf m30b">Signature:</div>
    <div contenteditable="true" class="lf" style="border-bottom: 1px solid #000;min-width:150px;"></div>
    <p class="cf" >HK EOULU TRADING LIMITED</p>
    <p >Date: <span contenteditable="true" class="Date" style="text-decoration: none;display: inline-block;min-width: 90px;border-bottom: solid 1px #000;"></span></p>
</div>

    <!-- 页脚 -->
    <div class="yejiao cf" style="margin-top: 20px; height:80px;">
        <hr>
        <pre style="text-align: center;color: #000080;font-family: -webkit-body;">EOULU
Suzhou ● Shenzhen ● Beijing ● Shanghai ● HongKong
〡Phone: +86-512-62757360〡Web:www.eoulu.com〡Email:info@eoulu.com〡</pre>

    </div>
</div>
			<!-- <input type="button" value="提交" class="bToggle" id="submit_n" style="position:absolute;z-index:11;top: 200px; left: 70%;width: 92px;height: 30px;font-size: 19px;"> -->
			<input type="button" value="导出PDF" class="bToggle" id="exportPDF1" style="position:absolute;z-index:11;top: 150px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
			<input type="button" value="关闭" class="bToggle " id="contract_close1" style="position: absolute;z-index: 11;top: 100px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
	</div>
</body>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/msgbox.js"></script>
 <script src="js/shipment.js"></script>
<script type="text/javascript" src="js/html2canvas.js"></script>  
<script type="text/javascript" src="js/jsPdf.debug.js"></script>
<script>

//Applicant联动
$(".APPSelect").change(function(){
	var ApplicantVal = $(this).find("option:selected").text();
	$("#Applicant").val(ApplicantVal);
	var AppIndex = $(".APPSelect").find("option:selected").prop('index');
	$(" .contract_add .hideTD").show();
	$(".contract_add .TELSelect").find("option").eq(AppIndex).prop("selected",true).siblings().prop("selected",false);
	$(".contract_add  .ADDSelect").find("option").eq(AppIndex).prop("selected",true).siblings().prop("selected",false);
	$("#TEL").val($(".contract_add .TELSelect").find("option:selected").text());
	$("#ADD").val($(".contract_add .ADDSelect").find("option:selected").text());
})
//Applicant联动
$(".APPSelect1").change(function(){
	var ApplicantVal = $(this).find("option:selected").text();
	$("#Applicant1").val(ApplicantVal);
	var AppIndex = $(".APPSelect1").find("option:selected").prop('index');
	$(".contract_update .hideTD").show();
	$(".contract_update .TELSelect").find("option").eq(AppIndex).attr("selected",true).siblings().attr("selected",false);
	$(".contract_update .ADDSelect").find("option").eq(AppIndex).attr("selected",true).siblings().attr("selected",false);
	$("#TEL1").val($(".contract_update .TELSelect").find("option:selected").text());
	$("#ADD1").val($(".contract_update .ADDSelect").find("option:selected").text());
})

$(".contract_add .addItem").click(function(){
	var sddStr = '<tr class="DIMTr">'+
		'<td>DIM;</td>'+
		'<td><input type="text" name="DIM" value="" class="DIM" ></td>'+
		'<td>Gross Weight</td>'+
		'<td><input type="text" name="GrossWeight"  class="GrossWeight"  value="" ></td>'+
		'<td><input type="button" name="DelThisTr"  class="DelThisTr bToggle"  value="删除本行尺寸信息" ></td>'+
	'</tr>';
	$(".contract_add .contract_basic").append(sddStr);
})
$(".contract_update .addItem").click(function(){
	var sddStr = '<tr class="DIMTr">'+
		'<td>DIM;</td>'+
		'<td><input type="text" name="DIM" value="" class="DIMUpdate" ></td>'+
		'<td>Gross Weight</td>'+
		'<td><input type="text" name="GrossWeight"  class="GrossWeightUpdate"  value="" ></td>'+
		'<td><input type="button" name="DelThisTr"  class="DelThisTr bToggle"  value="删除本行尺寸信息" ></td>'+
	'</tr>';
	$(".contract_update .contract_basic").append(sddStr);
})
$(document).on("click",".DelThisTr",function(){
	$(this).parent().parent().remove();
})
/*********************添加发货通知 ************************/
/* 添加发货通知 */
$(document).on("click","#add_submit",function(){
		
	var sizeExist;
	var DIM =[];
	var GrossWeight =[];
	if($(".DIMTr").length != 0){
		sizeExist = "yes";
		for(var i = 0 ; i < $(".DIMTr").length ; i++){
			DIM.push($(".DIMTr").eq(i).find('input[name="DIM"]').val());
			GrossWeight.push($(".DIMTr").eq(i).find('input[name="GrossWeight"]').val());
		}
	}
	else{
		sizeExist = "no"
	}
	
	var InfoApp = $('.contract_add input[name="Applicant"]').val();
	var InfoAdd = $('.contract_add input[name="ADD"]').val();
	var InfoTel = $('.contract_add input[name="TEL"]').val();
	var DCNO = $('.contract_add input[name="DC_NO"]').val();
	var AirWaybillNO = $('.contract_add input[name="AirWaybillNO"]').val();
	var ContractNO = $('.contract_add input[name="contract_no"]').val();
	var FlightNO = $('.contract_add input[name="FlightNO"]').val();
	var GoodsDescription = $('.contract_add input[name="Model"]').val();
	var Qty = $('.contract_add input[name="Qty"]').val();
	var Total = $('.contract_add input[name="Total"]').val();
	/* var ShipmentValue = fmoney($('.contract_add input[name="Value"]').val()); */
	var ShipmentValue = parseFloat($('.contract_add input[name="Value"]').val().replace(/,/g,''));
	var CarrierName = $('.contract_add input[name="CarrierName"]').val();
	var DepartureDate = $('.contract_add input[name="DepartureDate"]').val();
	var DepartAirport = $('.contract_add input[name="DepartAirport"]').val();
	var DestAirport = $('.contract_add input[name="DestAirport"]').val();
	var Date = $('.contract_add input[name="Date"]').val();
 	   $.ajax({
	        type : 'get',
	        url : 'ShipmentAdd',
	        data : {
	        	InfoAPP : InfoApp,
	        	InfoADD : InfoAdd,
	        	InfoTel : InfoTel,
	            DCNO : DCNO,
	            AirWaybillNO : AirWaybillNO,
	            ContractNO : ContractNO,
	            FlightNO : FlightNO,
	            GoodsDescription: GoodsDescription,
	            Qty : Qty,
	            Total : Total,
	            ShipmentValue : ShipmentValue,
	            CarrierName : CarrierName,
	            DepartureDate : DepartureDate,
	            DepartAirport : DepartAirport,
	            DestAirport : DestAirport,
	            Date : Date,
	            DIM : DIM,
	            GrossWeight : GrossWeight,
	            sizeExist : sizeExist
	        },
	        dataType : 'json',
	        success : function (data) {
	        	
	            $.MsgBox.Alert('提示','添加成功');
	            $('.MailBar_cover_color').hide();
	            $('.contract_add').hide();
	        },
	        error : function () {
	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    }); 
	    	    
    });
    
/* 修改发货通知 */
$(document).on("click",".contract-edit",function(){
	
	var tr=$(this).parent().parent();
	$('.contract_update').find('input[name="Applicant"]').val(tr.find('td').eq(6).text());
	$('.contract_update').find('input[name="ADD"]').val(tr.find('td').eq(7).text());
	$('.contract_update').find('input[name="TEL"]').val(tr.find('td').eq(8).text());
	$('.contract_update').find('input[name="DC_NO"]').val(tr.find('td').eq(9).text());
	$('.contract_update').find('input[name="AirWaybillNO"]').val(tr.find('td').eq(10).text());
	$('.contract_update').find('input[name="contract_no"]').val(tr.find('td').eq(4).text());
	$('.contract_update').find('input[name="FlightNO"]').val(tr.find('td').eq(11).text());
	$('.contract_update').find('input[name="Model"]').val(tr.find('td').eq(12).text());
	$('.contract_update').find('input[name="CarrierName"]').val(tr.find('td').eq(16).text());
	$('.contract_update').find('input[name="DepartureDate"]').val(tr.find('td').eq(20).text());
	$('.contract_update').find('input[name="Qty"]').val(tr.find('td').eq(13).text());
	$('.contract_update').find('input[name="Total"]').val(tr.find('td').eq(14).text());
	$('.contract_update').find('input[name="Value"]').val(tr.find('td').eq(15).text());
	$('.contract_update').find('input[name="DepartAirport"]').val(tr.find('td').eq(17).text());
	$('.contract_update').find('input[name="DestAirport"]').val(tr.find('td').eq(18).text());
	$('.contract_update').find('input[name="Date"]').val(tr.find('td').eq(19).text());
	
	var  ID = tr.find('td').eq(0).attr("value");
	
	$(".contract_update .contract_title").attr("value",ID);  //在修改页面保存当前行的ID信息
	$(".DIMTr").remove();
	console.log(ID)
	 $.ajax({
        type : 'get',
        url : 'GetShipmentSize',
        data : {
        	ID : ID,
        },
        dataType : 'json',
        success : function (data) {
        	console.log(data)
             for(var i = 1 ; i <data.length; i++ ){
				var sddStr = '<tr class="DIMTr">'+
					'<td>DIM;</td>'+
					'<td><input type="text" name="DIM" value="'+data[i].DIM+'" class="DIMUpdate" ></td>'+
					'<td>Gross Weight</td>'+
					'<td><input type="text" name="GrossWeight"  class="GrossWeightUpdate"  value="'+data[i].GrossWeight+'" ></td>'+
					'<td><input type="button" name="DelThisTr"  class="DelThisTr bToggle"  value="删除本行尺寸信息" ></td>'+
				'</tr>';
				$(".contract_update .contract_basic").append(sddStr);
			} 
        },
        error : function () {
            $.MsgBox.Alert("提示", "获取尺寸信息错误！");
        }
    }); 
   $('.MailBar_cover_color').show();
   $('.contract_update').show();
	    	    
 });
   	
	/*  提交修改后的信息  */
	$('#update_submit').click(function () {
		/* InfoAPP、InfoADD、InfoTel、DCNO、AirWaybillNO、ContractNO、FlightNO、GoodsDescription、Qty、
		Total、ShipmentValue、CarrierName、DepartureDate、DepartAirport、DestAirport、Date、ID  */   
	    var DIM =[];
	    var GrossWeight =[];
	    var sizeExist;
	    if($(".DIMUpdate").length != 0){
			sizeExist = "yes";
			for(var i = 0 ; i < $(".DIMUpdate").length ; i++){
				DIM.push($(".DIMUpdate").eq(i).val());
				GrossWeight.push($(".GrossWeightUpdate").eq(i).val());
			}
		}
		else{
			sizeExist = "no"
		}
	    
	    var InfoAPP= $('.contract_update').find('input[name="Applicant"]').val();
	    var InfoADD= $('.contract_update').find('input[name="ADD"]').val();
	    var InfoTel=$('.contract_update').find('input[name="TEL"]').val();
	    var DCNO= $('.contract_update').find('input[name="DC_NO"]').val();
	    var AirWaybillNO=$('.contract_update').find('input[name="AirWaybillNO"]').val();
	    var ContractNO=$('.contract_update').find('input[name="contract_no"]').val();
	    var FlightNO= $('.contract_update').find('input[name="FlightNO"]').val();
	    var GoodsDescription= $('.contract_update').find('input[name="Model"]').val();
	    var CarrierName= $('.contract_update').find('input[name="CarrierName"]').val();
	    var DepartureDate= $('.contract_update').find('input[name="DepartureDate"]').val();
		var Qty=$('.contract_update').find('input[name="Qty"]').val();
		var Total= $('.contract_update').find('input[name="Total"]').val();
		var ShipmentValue = parseFloat($('.contract_update').find('input[name="Value"]').val().replace(/,/g,''));
		var DepartAirport= $('.contract_update').find('input[name="DepartAirport"]').val();
		 var DestAirport=  $('.contract_update').find('input[name="DestAirport"]').val();
		 var Date= $('.contract_update').find('input[name="Date"]').val();
	    
	    
	    var  ID = $(".contract_update .contract_title").attr("value");
	
	    	$.ajax({
	            type : 'get',
	            url : "ModifyShipment",
	            data : {
	            	ID:ID,
	            	InfoAPP : InfoAPP,
	            	InfoADD : InfoADD,
	            	InfoTel : InfoTel,
	            	DCNO : DCNO,
	            	AirWaybillNO : AirWaybillNO,
	            	ContractNO : ContractNO,
	            	FlightNO : FlightNO,
	            	GoodsDescription : GoodsDescription,
	            	Qty : Qty,
	            	Total : Total,
	            	ShipmentValue : ShipmentValue,
	            	CarrierName : CarrierName,
	            	DepartureDate : DepartureDate,
	            	DepartAirport : DepartAirport,
	            	DestAirport : DestAirport,
	            	Date : Date,
	            	DIM : DIM,
	 	            GrossWeight : GrossWeight,
	 	            sizeExist : sizeExist
	            },
	            dataType : 'json',
	            success : function (data) {       	        	             	
	            	$.MsgBox.Alert('提示','修改成功');
		            $('.MailBar_cover_color').hide();
		            $('.hidePdf').hide();
	            },
	            error : function () {
	                $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	            }
	        });
	})  
	
    
 /*  模板显示*/   
 $('.contract-show').click(function () {
	    $('.MailBar_cover_color').show();
	    
	    var ID = $(this).parent().parent().find("td").eq(0).attr("value");
		$(".yejiao").attr("value",ID);
	    /* 获取隐藏信息 */
							
	    var thisList = $(this).parent().parent().children();
	    $(".news .InfoApp").text("").text(thisList.eq(6).text());
	    $(".news .InfoAdd").text("").text(thisList.eq(7).text());
	    $(".news .DCNO").text("").text(thisList.eq(9).text());
	    $(".news .ContractN0").text("").text(thisList.eq(4).text());
	    $(".news .AirWayNO").text("").text(thisList.eq(10).text());
	    $(".news .FlightN0").text("").text(thisList.eq(11).text());
	    $(".news .DepartureDate").text("").text(thisList.eq(20).text());
	    $(".news .DepartAirport").text("").text(thisList.eq(17).text());
	    $(".news .DestAirport").text("").text(thisList.eq(18).text());
	    $(".news .Total").text("").text(thisList.eq(14).text());
	    $(".news .Value").text("").text(fmoney(thisList.eq(15).text()));
	    $(".news .CarrierName").text("").text(thisList.eq(16).text());
	    $(".news .Date").text("").text(thisList.eq(19).text());
	    $(".news .Model").text("").text(thisList.eq(12).text());
	    $(".news .Qty").text("").text(thisList.eq(13).text());
	    //TEL&&FAX
	    var TELFAX = thisList.eq(8).text().split("&&");
	    if(TELFAX[0] == ""){
	    	$(".news .InfoTel").hide();
	    }
	    else{
	    	$(".news .InfoTel").text("").text(TELFAX[0]);
	    }
	    if(TELFAX[1] == ""){
	    	$(".news .InfoFax").hide();
	    }
	    else{
	    	$(".news .InfoFax").text("").text(TELFAX[1]);
	    }
	    
	    //DIM
	    $(".ModelBox").nextAll().remove();
	    
		var  ID = thisList.eq(0).attr("value");
		console.log(ID)
		 $.ajax({
	        type : 'get',
	        url : 'GetShipmentSize',
	        data : {
	        	ID : ID,
	        },
	        dataType : 'json',
	        success : function (data) {
	        	console.log(data.length)
	        	if(data.length > 0){
		   	    	 for(var i = 1 ; i < data.length; i++){
		   	    		 var DIMStr = '<p>'+
		                       ' <span  class="noneLine"> DIM; </span>'+
		                       ' <span  class="noneLine" >Gross Weight</span>'+
		                    '</p>'+
		                    '<p>'+
		                       ' <span contenteditable="true" class="DIMTd">'+ data[i].DIM+'</span>'+
		                        '<span contenteditable="true"  class="GrossWeightTd">'+ data[i].GrossWeight+'</span>'+
		                   ' </p>';
		   	    		$(".DimBox").append(DIMStr) 
		   	 	    }
		   	    }
	        },
	        error : function () {
	            $.MsgBox.Alert("提示", "获取尺寸信息错误！");
	        }
	    });
    $('.hidePdf').show(); 
 })
 
$('#contract_close1').click(function () {
    $('.MailBar_cover_color').hide();
    $('.hidePdf').hide();
});
 $('.contractUpdate_close,.update_cancel').click(function () {
	    $('.MailBar_cover_color').hide();
	    $('.contract_update').hide();
	});

/* ************************模板页面提交*************************** */


 /* $(document).on("click","#submit_n",function(){
	
    var DIM =[];
    var GrossWeight =[];
    var sizeExist;
    if($(".DIMTd").length != 0){
		sizeExist = "yes";
		for(var i = 0 ; i < $(".DIMTd").length ; i++){
			DIM.push($(".DIMTd").eq(i).text());
			GrossWeight.push($(".DIMTd").eq(i).next().text());
		}
	}
	else{
		sizeExist = "no"
	}
    
    
    var ID=  $(".hidePdf .yejiao").attr("value");
    var InfoAPP=  $(".hidePdf .InfoApp").eq(0).text();
    var InfoADD=  $(".hidePdf .InfoAdd").text();
    var InfoTel=  $(".hidePdf .InfoTel").text()+"&&"+$(".hidePdf .InfoFax").text();
    var DCNO=  $(".hidePdf .DCNO").eq(0).text();
    var AirWaybillNO=  $(".hidePdf .AirWayNO").text();
    var ContractNO=  $(".hidePdf .ContractN0").text();
    var FlightNO=  $(".hidePdf .FlightN0").text();
    var GoodsDescription=  $(".hidePdf .Model").text();
    var Qty=  $(".hidePdf .Qty").text();
    var Total=  $(".hidePdf .Total").text();
    var ShipmentValue=  fmoney($(".hidePdf .Value").text());
    var CarrierName=  $(".hidePdf .CarrierName").text();
    var DepartureDate=  $(".hidePdf .DepartureDate").text();
    var DepartAirport=  $(".hidePdf .DepartAirport").text();
    var DestAirport=  $(".hidePdf .DestAirport").text();
    var Date=  $(".hidePdf .Date").eq(0).text();
    
    console.log(ID);
    console.log(InfoAPP);
    console.log(InfoADD);
    console.log(InfoTel);
    console.log(DCNO);
    console.log(AirWaybillNO);
    console.log(ContractNO);
    console.log(FlightNO);
    console.log(GoodsDescription);
    console.log(Qty);
    console.log(Total);
    console.log(ShipmentValue);
    console.log(CarrierName);
    console.log(DepartureDate);
    console.log(DepartAirport);
    console.log(Date);
    console.log(DestAirport);
    console.log(DIM);
    console.log(GrossWeight);
    console.log(sizeExist);
    	$.ajax({
            type : 'get',
            url : "ModifyShipment",
            data : {
            	ID:ID,
            	InfoAPP : InfoAPP,
            	InfoADD : InfoADD,
            	InfoTel : InfoTel,
            	DCNO : DCNO,
            	AirWaybillNO : AirWaybillNO,
            	ContractNO : ContractNO,
            	FlightNO : FlightNO,
            	GoodsDescription : GoodsDescription,
            	Qty : Qty,
            	Total : Total,
            	ShipmentValue : ShipmentValue,
            	CarrierName : CarrierName,
            	DepartureDate : DepartureDate,
            	DepartAirport : DepartAirport,
            	DestAirport : DestAirport,
            	Date : Date,
            	DIM : DIM,
 	            GrossWeight : GrossWeight,
 	            sizeExist : sizeExist
            },
            dataType : 'json',
            success : function (data) {       	        	             	
            	$.MsgBox.Alert('提示','提交成功');
	            $('.MailBar_cover_color').hide();
	            $('.hidePdf').hide();
            },
            error : function () {
                $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
            }
        });
});
 */


//点击添加
function AddContract() {
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
$('#update_cancel').click(function () {
    $('.MailBar_cover_color').hide();
    $('.contract_update').hide();
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


document.getElementById("exportPDF1").onclick=function(){
	var target = document.getElementById("view");
	var width = $('#view').width() ; 
	var height =  $('#view').height() ;
	var canvas = document.createElement("canvas");  
	    canvas.width = width * 2;  
	    canvas.height = height * 2;  
	    canvas.style.width = width + "px";  
	    canvas.style.height = height + "px";  
	var context = canvas.getContext("2d");//然后将画布缩放，将图像放大两倍画到画布上  
	    context.scale(2,2); 
  html2canvas(target, {
	  canvas:canvas,
	    onrendered:function(canvas) {
	        var pageData = canvas.toDataURL('image/png', 1.0);
	        var pdf = new jsPDF('', 'pt', 'a4');
	        pdf.addImage(pageData, 'PNG', 0, 0, 600, 821.89);
	        pdf.save('FAX OF Particulars Of Shipment.pdf');
	    }
	})
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

function fm(obj){
	var vb = obj.value.split('.');
	var val = vb[0].replace(/\D/,'');
	if(obj.value.length>0)obj.value = val.match(/\d{3}|\d{2}|\d/g).join(',')+(vb.length>1?'.'+vb[1]:'');
}
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
	
})

</script>
</html>