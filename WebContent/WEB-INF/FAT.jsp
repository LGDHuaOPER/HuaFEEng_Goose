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
<title>FAT</title>
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
<link rel="stylesheet" type="text/css" href="css/FAT.css">
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
				action="GetAcceptanceRoute">
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
							value="${fn:split('客户名称,合同名称,合同号,货物名称',',')}"></c:set>
						<div class="select1">
							<select name="type1" id="type1">
								<c:forEach items="${dropdown}" var="dropdownList1"
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
								<c:forEach items="${dropdown}" var="dropdownList2"
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
					<td>货物名称</td>
					<td style="width: 145px;">模板预览（可导出）</td>
				</tr>
  				 <c:forEach var="orderInfo" items="${acceptance}" varStatus="status">
					<c:if test="${status.index>0}">
						<tr class="tbody_tr">
							<td value="${orderInfo['ID']}">${status.index+(currentPage-1)*10}</td>
							<td> <i class="fa fa-edit contract-edit" value="${orderInfo['ID']}"></i></td>
							<td title="${orderInfo['Customer']}">${orderInfo['Customer']}</td>
							<td title="${orderInfo['ContractTitle']}">${orderInfo['ContractTitle']}</td>
							<td title="${orderInfo['ContractNO']}">${orderInfo['ContractNO']}</td>
							<td title="${orderInfo['CommoditySpecifications']}">${orderInfo['CommoditySpecifications']}</td>
							<td><i class="fa fa-eye contract-show"></i></td>
							<td style="display:none;">${orderInfo['DCNO']}</td><!--9 -->
							<td style="display:none;">${orderInfo['CommoditySpecifications']}</td>    <!--10  -->
							<td style="display:none;">${orderInfo['Qty']}</td><!--11  -->
							<td style="display:none;">${orderInfo['Model']}</td><!--12  -->
							<td style="display:none;">${orderInfo['PriceTerm']}</td><!--13 -->
							<td style="display:none;">${orderInfo['Packing']}</td>    <!--14  -->
							<td style="display:none;">${orderInfo['TotalValue']}</td><!--15  -->
							<td style="display:none;">${orderInfo['Balance']}</td><!--16 -->
							<td style="display:none;">${orderInfo['Percent']}</td><!--17 -->
							<td style="display:none;">${orderInfo['EndUser']}</td><!--18  -->
							<td style="display:none;">${orderInfo['Beneficiary']}</td><!--19 -->
							<td style="display:none;">${orderInfo['Applicant']}</td><!--20 -->
						</tr>
					</c:if>
				</c:forEach> 
			</table>	
		
	 	 <c:choose>
				<c:when test="${queryType == 'common'}">
					<c:set var="queryUrl"
					value="Acceptance?type1=${classify1 }&searchContent1=${parameter1}&selected=${str}&currentPage=">
					</c:set>
				</c:when>
				<c:otherwise>
					<c:set var="queryUrl"
					value="GetAcceptanceRoute?type1=${classify1 }&searchContent1=${parameter1}&type2=${classify2}&searchContent2=${parameter2}&selected=${str}&currentPage=">
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
	
		<!-- -------------------------  添加FAT信息 ----------------------------------->
	<div class="contract_add">
		<div class="contract_title">添加FAT信息 </div>
		<div class="contractAdd_close">关闭</div>
		<div class="basic_info">
			<div class="table_title">FAT信息 </div>
			<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
				<tbody>
					<tr>	
						<td>CONTRACT NO.</td>
						<td><input type="text" name="contract_no" value=""  id="contract_no1"></td>
						<td>DC No.</td>
						<td><input type="text" name="DC_NO" value=""></td>
					</tr>
					<tr class="ModelTr">
						<td>GOODS</td>
						<td><input type="text" name="GOODS" value="" class="GOODS" ></td>
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
									<option value="SUMMIT12000B-S">1 SET</option>
								</select>
							</span>
						</td>
					</tr>
					<tr>	
						<td>Price Term</td>
						<td >
							<input type="text" name="PriceTerm" value="" style="position:absolute;width:162px;" id="PriceTerm1">
							<span>
								<select onchange="document.getElementById('PriceTerm1').value=this.options[this.selectedIndex].text">
								<option value="No" selected="selected">--请选择--</option>
									<option value="">BEIJING </option>
									<option value="">CHENGDU </option>
									<option value="">HONGKONG </option>
									<option value="">SHENZHEN </option>
									<option value="">SHANGHAI </option>
									<option value="">XIAMEN </option>
									<option value="">GUANGZHOU</option>
								</select>
							</span>
						</td>
						<td>Packing</td>
						<td >
							<input type="text" name="Packing" value="" style="position:absolute;width:162px;" id="Packing1">
							<span>
								<select onchange="document.getElementById('Packing1').value=this.options[this.selectedIndex].text">
								<option value="No" selected="selected">--请选择--</option>
									<option value="">STANDARD EXPORT PACKING SUITABLE FOR LONG-DISTANCE TRANSPORTATION </option>
									<option value="">TO BE PACKED IN NEW STRONG WOODEN CASES (CERTIFICATED BY IPPC) OR CARTONS </option>
								</select>
							</span>
						</td>
						<td>Total Value</td>
						<td><input type="text" name="TotalValue" value=""  onkeyup = "fm(this)" ></td>
					</tr>
					<tr>	
						<td>Percent</td>
						<td><input type="text" name="Percent" value=""    ></td>
					</tr>
					<tr>	
						<td>EndUser</td>
						<td >
							<input type="text" name="EndUser" value="" style="position:absolute;width:162px;" id="EndUser1">
							<span>
								<select onchange="document.getElementById('EndUser1').value=this.options[this.selectedIndex].text">
								<option value="No" selected="selected">--请选择--</option>
									<option value="">XIAMEN SANAN INTEGRATED CIRCUIT CO., LTD</option>
									<option value="">INSTITUTE OF MICROELECTRONICS OF THE CHINSES ACADEMY OF SCIENCES</option>
								</select>
							</span>
						</td>
						<td>Beneficiary</td>
						<td >
							<input type="text" name="Beneficiary" value="" style="position:absolute;width:162px;" id="Beneficiary1">
							<span>
								<select onchange="document.getElementById('Beneficiary1').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="">HK EOULU TRADING LIMITED</option>
								</select>
							</span>
						</td>
						<td>Applicant</td>
						<td >
							<input type="text" name="Applicant" value="" style="position:absolute;width:162px;" id="Applicant1">
							<span>
								<select onchange="document.getElementById('Applicant1').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="">XIAMEN XINDECO LTD.</option>
									<option value="">HOPE INVESTMENT DEVELOPMENT CORP., LTD.</option>
									<option value="">CERNET CORP CERNET TOWER, TSINGHUA SCIENCE PARK </option>
									<option value="">THE ORIENTAL SCIENTIFIC INSTRUMENT IMPORT AND EXPORT (GROUP) CORPORATION</option>
									<option value="">CHINA NATIONAL INSTRUMENTS IMPORT AND EXPORT (GROUP) CORPORATION</option>
								</select>
							</span>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="edit_btn">
			<input type="button" value="提交" class="bToggle" id="add_submit">
			<input type="button" value="取消" class="bToggle" id="add_cancel">
		</div>
	</div>
	
	<!-- -------------------------  修改FAT信息 ----------------------------------->
	<div class="contract_update">
	
		<div class="contract_title">修改FAT信息 </div>
		<div class="contractUpdate_close">关闭</div>
		<div class="basic_info">
			<div class="table_title">FAT信息 </div>
			<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
				<tbody>
					<tr>	
						<td>CONTRACT NO.</td>
						<td><input type="text" name="contract_no" value=""  id="contract_no"></td>
						<td>DC No.</td>
						<td><input type="text" name="DC_NO" value=""></td>
					</tr>
					<tr class="ModelTr">
						<td>GOODS</td>
						<td><input type="text" name="GOODS" value="" class="GOODS" ></td>
						<td>Model</td>
						<td class="ModelTd">
							<input type="text" name="Model" value="" style="position:absolute;width:162px;" id="Model">
							<span>
								<select onchange="document.getElementById('Model').value=this.options[this.selectedIndex].text">
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
							<input type="text" name="Qty" value="" style="position:absolute;width:162px;" id="Qty">
							<span>
								<select onchange="document.getElementById('Qty').value=this.options[this.selectedIndex].text">
								<option value="No" selected="selected">--请选择--</option>
									<option value="SUMMIT12000B-S">1 SET</option>
								</select>
							</span>
						</td>
					</tr>
					<tr>	
						<td>Price Term</td>
						<td >
							<input type="text" name="PriceTerm" value="" style="position:absolute;width:162px;" id="PriceTerm">
							<span>
								<select onchange="document.getElementById('PriceTerm').value=this.options[this.selectedIndex].text">
								<option value="No" selected="selected">--请选择--</option>
									<option value="">BEIJING </option>
									<option value="">CHENGDU </option>
									<option value="">HONGKONG </option>
									<option value="">SHENZHEN </option>
									<option value="">SHANGHAI </option>
									<option value="">XIAMEN </option>
									<option value="">GUANGZHOU</option>
								</select>
							</span>
						</td>
						<td>Packing</td>
						<td >
							<input type="text" name="Packing" value="" style="position:absolute;width:162px;" id="Packing">
							<span>
								<select onchange="document.getElementById('Packing').value=this.options[this.selectedIndex].text">
								<option value="No" selected="selected">--请选择--</option>
									<option value="">STANDARD EXPORT PACKING SUITABLE FOR LONG-DISTANCE TRANSPORTATION </option>
									<option value="">TO BE PACKED IN NEW STRONG WOODEN CASES (CERTIFICATED BY IPPC) OR CARTONS </option>
								</select>
							</span>
						</td>
						<td>Total Value</td>
						<td><input type="text" name="TotalValue" value=""  onkeyup = "fm(this)" ></td>
					</tr>
					<tr>	
						<td>Percent</td>
						<td><input type="text" name="Percent" value=""    ></td>
					</tr>
					<tr>	
						<td>EndUser</td>
						<td >
							<input type="text" name="EndUser" value="" style="position:absolute;width:162px;" id="EndUser">
							<span>
								<select onchange="document.getElementById('EndUser').value=this.options[this.selectedIndex].text">
								<option value="No" selected="selected">--请选择--</option>
									<option value="">XIAMEN SANAN INTEGRATED CIRCUIT CO., LTD</option>
									<option value="">INSTITUTE OF MICROELECTRONICS OF THE CHINSES ACADEMY OF SCIENCES</option>
								</select>
							</span>
						</td>
						<td>Beneficiary</td>
						<td >
							<input type="text" name="Beneficiary" value="" style="position:absolute;width:162px;" id="Beneficiary">
							<span>
								<select onchange="document.getElementById('Beneficiary').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="">HK EOULU TRADING LIMITED</option>
								</select>
							</span>
						</td>
						<td>Applicant</td>
						<td >
							<input type="text" name="Applicant" value="" style="position:absolute;width:162px;" id="Applicant">
							<span>
								<select onchange="document.getElementById('Applicant').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="">XIAMEN XINDECO LTD.</option>
									<option value="">HOPE INVESTMENT DEVELOPMENT CORP., LTD.</option>
									<option value="">CERNET CORP CERNET TOWER, TSINGHUA SCIENCE PARK </option>
									<option value="">THE ORIENTAL SCIENTIFIC INSTRUMENT IMPORT AND EXPORT (GROUP) CORPORATION</option>
									<option value="">CHINA NATIONAL INSTRUMENTS IMPORT AND EXPORT (GROUP) CORPORATION</option>
								</select>
							</span>
						</td>
					</tr>
				</tbody>
			</table>
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

    <div id="FATPdf">
    	 <p class=" m30b">- TO WHOM IT MAY CONCERN –</p>
	    <div class="b " style="width: 200px;">ACCEPTANCE CERTIFICATE </div>
	 	<div style="width: 195px;margin: -3px 0 15px 0;color: #000;">===================</div>
	 	
	 	<div>
	 		<span class="b" style="display:inline-block;">Contract no.:</span>
	 		<span class="ContractNO" contenteditable="true" style="display:inline-block;text-align:center;min-width: 120px;border-bottom: solid 1px #000;margin-top: -5px;">11</span>
	 	</div>	
	 	<div>
	 		<span class="b" style="display:inline-block;">DC.No.:</span>
	 		<span class="DCNO"  contenteditable="true" style="display:inline-block;text-align:center;min-width: 120px;border-bottom: solid 1px #000;">11</span>
	 	</div>
	 	<div class="GoodsBox">
	 		<ul class="lf"  style="width: 50%;padding: 0;">
	 			<li>COMMODITY AND SPECIFICATIONS</li>
	 			<li class="GOODS"  contenteditable="true">COMMODITY AND SPECIFICATIONS</li>
	 		</ul>
	 		<ul  class="lf">
	 			<li>QTY</li>
	 			<li class="QTY"  contenteditable="true">1 SET</li>
	 		</ul>
	 	</div>
	 	<div class="cl" style="margin-bottom: 20px;">
	 		<span style="display:inline-block;">MODEL:</span>
	 		<span class="Model"  contenteditable="true" style="display:inline-block;text-align:center;">11</span>
	 	</div>
	 	<div class="cl" style="margin-bottom: 20px;">
	 		<span class="b"  style="display:inline-block;">Price Term:</span>
	 		<span class="b"  style="display:inline-block;margin-left: 100px;">CIP</span>
	 		<span class=" PriceTerm"  contenteditable="true" style="display:inline-block;min-width: 70px;text-align:center;border-bottom: solid 1px #000;margin-left: 5px;">XIAMEN</span>
	 		<span class=""  style="display:inline-block;margin-left: 5px;">AIRPORT, CHINA</span>
	 	</div>
	 	<div class="cl" style="margin: 20px 0 40px;height: 40px;">
	 		<span class="b lf"  style="display:inline-block;">Packing:</span>
	 		<span class="Packing lf" contenteditable="true" style="display:inline-block;margin-left: 100px;max-width: 550px;">TO BE PACKED IN NEW STRONG WOODEN CASES (CERTIFICATED BY IPPC) OR CARTONS </span>
	 	</div>
	 	
		<div class="cl" style="">
	 		<span class="b " style="display:inline-block;">Total Shipped Goods Value:USD</span>
	 		<span class="TotalValue " contenteditable="true"  style="display:inline-block;min-width: 120px;border-bottom: solid 1px #000;text-align: center;">152,000</span>
	 	</div>
	 	<div class="cl" style="margin: 25px 0 45px;">
	 		<span class="b " style="display:inline-block;">Balance: </span>
	 		<span class="b"  style="display:inline-block;margin-left: 170px;">USD</span>
	 		<span class="Balance " contenteditable="true"  style="display:inline-block;min-width: 120px;border-bottom: solid 1px #000;text-align: center;">152,000</span>
	 	</div>
	 	<div class="cl" style="margin: 25px 0 45px;">
	 		<span class=" " style="display:inline-block;max-width: 70%;">We, the end-user's, the beneficiary and the applicant, herewith confirm that 
			the a.m. goods were accepted and the <span class="Percent" style="display:inline-block;margin: 0 3px;" contenteditable="true" >10</span>percent of invoice value are payable now.
			</span>
	 	</div>
		<div class="cl" style="margin-bottom: 50px;">
	 		<span class=" b" style="display:inline-block;width: 300px;">. . . . . . . . . . . . . . . . . . . . .</span></br>
	 		<span class=" " >signed and stamped by the end-user's:</span></br>
	 		<span class="  EndUser"  contenteditable="true" >XIAMEN SANAN INTEGRATED CIRCUIT CO., LTD</span>
	 	</div>
	 	<div class="cl" style="margin-bottom: 50px;">
	 		<span class=" " style="display:inline-block;width: 300px;">. . . . . . . . . . . . . . . . . . . . .</span></br>
	 		<span class=" " >signed and stamped by the beneficiary:</span></br>
	 		<span class=" Beneficiary"  contenteditable="true" >HK EOULU TRADING LIMITED</span>
	 	</div>
	 	<div class="cl" style="margin-bottom: 60px;">
	 		<span class=" b" style="display:inline-block;width: 300px;">. . . . . . . . . . . . . . . . . . . . .</span></br>
	 		<span class=" " >signed and stamped by the applicant:</span></br>
	 		<span class=" Applicant"  contenteditable="true" >XIAMEN XINDECO LTD.</span>
	 	</div>
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
<script src="js/FAT.js"></script>
<script type="text/javascript" src="js/html2canvas.js"></script>  
<script type="text/javascript" src="js/jsPdf.debug.js"></script>
<script>


/*********************添加发货通知 ************************/
/* 添加发货通知 */
$(document).on("click","#add_submit",function(){

/* 	添加功能：URL：AcceptanceAdd
	          前端需传给后台：ContractNO,DCNO,CommoditySpecifications,Qty,Model,PriceTerm,Packing,TotalValue,Balance,Percent,EndUser,Beneficiary,Applicant
	          后台返回：json类型的true或false */

	
	var ContractNO = $('.contract_add input[name="contract_no"]').val();
	var DCNO = $('.contract_add input[name="DC_NO"]').val();
	var CommoditySpecifications = $('.contract_add input[name="GOODS"]').val();
	var Qty = $('.contract_add input[name="Qty"]').val();
	var Model = $('.contract_add input[name="Model"]').val();
	var PriceTerm = $('.contract_add input[name="PriceTerm"]').val();
	var Packing = $('.contract_add input[name="Packing"]').val();
	var TotalValue = parseFloat($('.contract_add input[name="TotalValue"]').val().replace(/,/g,''));
	var Balance = TotalValue / 10;
	var Percent = $('.contract_add input[name="Percent"]').val();
	var EndUser = $('.contract_add input[name="EndUser"]').val();
	var Beneficiary = $('.contract_add input[name="Beneficiary"]').val();
	var Applicant = $('.contract_add input[name="Applicant"]').val();
	console.log(ContractNO);
    console.log(DCNO);
    console.log(CommoditySpecifications);
    console.log(Qty);
    console.log(Model);
    console.log(PriceTerm);
    console.log(Packing);
    console.log(Balance);
    console.log(Percent);
    console.log(EndUser);
    console.log(Beneficiary);
    console.log(Applicant);
 	   $.ajax({
	        type : 'get',
	        url : 'AcceptanceAdd',
	        data : {
	        	ContractNO : ContractNO,
	        	DCNO : DCNO,
	        	CommoditySpecifications : CommoditySpecifications,
	        	Qty : Qty,
	        	Model : Model,
	        	PriceTerm : PriceTerm,
	        	Packing : Packing,
	        	TotalValue: TotalValue,
	        	Balance: Balance,
	        	Percent : Percent,
	        	EndUser : EndUser,
	        	Beneficiary : Beneficiary,
	        	Applicant : Applicant,
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
	
    $('.contract_update input[name="contract_no"]').val(tr.find('td').eq(4).text());
	$('.contract_update input[name="DC_NO"]').val(tr.find('td').eq(7).text());
	$('.contract_update input[name="GOODS"]').val(tr.find('td').eq(8).text());
	$('.contract_update input[name="Qty"]').val(tr.find('td').eq(9).text());
	$('.contract_update input[name="Model"]').val(tr.find('td').eq(10).text());
	$('.contract_update input[name="PriceTerm"]').val(tr.find('td').eq(11).text());
	$('.contract_update input[name="Packing"]').val(tr.find('td').eq(12).text());
    $('.contract_update input[name="TotalValue"]').val(fmoney(tr.find('td').eq(13).text(),2));
    $('.contract_update input[name="Balance"]').val(fmoney(tr.find('td').eq(14).text(),2));
	$('.contract_update input[name="Percent"]').val(tr.find('td').eq(15).text());
	$('.contract_update input[name="EndUser"]').val(tr.find('td').eq(16).text());
	$('.contract_update input[name="Beneficiary"]').val(tr.find('td').eq(17).text());
	$('.contract_update input[name="Applicant"]').val(tr.find('td').eq(18).text());
	
	
	var  ID = tr.find('td').eq(0).attr("value");
	
	$(".contract_update .contract_title").attr("value",ID);  //在修改页面保存当前行的ID信息
	
	console.log(ID)
   $('.MailBar_cover_color').show();
   $('.contract_update').show();
	    	    
 });
   	
	/*  提交修改后的信息  */
	$('#update_submit').click(function () {
		/* 	修改货物模板功能：URL：ModifyAcceptance
	      前端需传给后台：ContractNO,DCNO,CommoditySpecifications,Qty,Model,PriceTerm,Packing,TotalValue,Balance,Percent,EndUser,Beneficiary,Applicant,ID
	      后台返回：json类型的true或false
	
	*/
	var TotalValue ;
		
		var ContractNO = $('.contract_update input[name="contract_no"]').val();
		var DCNO = $('.contract_update input[name="DC_NO"]').val();
		var CommoditySpecifications = $('.contract_update input[name="GOODS"]').val();
		var Qty = $('.contract_update input[name="Qty"]').val();
		var Model = $('.contract_update input[name="Model"]').val();
		var PriceTerm = $('.contract_update input[name="PriceTerm"]').val();
		var Packing = $('.contract_update input[name="Packing"]').val();
		if($('.contract_update input[name="TotalValue"]').val().indexOf(",")>0){
			TotalValue = parseFloat($('.contract_update input[name="TotalValue"]').val().replace(/,/g,''));
		}
		else{
			TotalValue = $('.contract_update input[name="TotalValue"]').val()
		}
		var Balance = TotalValue / 10;
		var Percent = $('.contract_update input[name="Percent"]').val();
		var EndUser = $('.contract_update input[name="EndUser"]').val();
		var Beneficiary = $('.contract_update input[name="Beneficiary"]').val();
		var Applicant = $('.contract_update input[name="Applicant"]').val();
		   var  ID = $(".contract_update .contract_title").attr("value");
		console.log(ContractNO);
		console.log(DCNO);
		console.log(CommoditySpecifications);
		console.log(Qty);
		console.log(Model);
		console.log(PriceTerm);
		console.log(Packing);
		console.log(TotalValue);
		console.log(Balance);
		console.log(Percent);
		console.log(EndUser);
		console.log(Beneficiary);
		console.log(Applicant);
		  $.ajax({
		      type : 'get',
		      url : 'ModifyAcceptance',
		      data : {
		    	  ID : ID,
		      	ContractNO : ContractNO,
		      	DCNO : DCNO,
		      	CommoditySpecifications : CommoditySpecifications,
		      	Qty : Qty,
		      	Model : Model,
		      	PriceTerm : PriceTerm,
		      	Packing : Packing,
		      	TotalValue: TotalValue,
		      	Balance: Balance,
		      	Percent : Percent,
		      	EndUser : EndUser,
		      	Beneficiary : Beneficiary,
		      	Applicant : Applicant,
		      },
		      dataType : 'json',
		      success : function (data) {
		      	
		          $.MsgBox.Alert('提示','修改成功');
		          $('.MailBar_cover_color').hide();
		          $('.contract_add').hide();
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
	    $(".news .ContractNO").text("").text(thisList.eq(4).text());
	    $(".news .DCNO").text("").text(thisList.eq(7).text());
	    $(".news .GOODS").text("").text(thisList.eq(8).text());
	    $(".news .QTY").text("").text(thisList.eq(9).text());
	    $(".news .Model").text("").text(thisList.eq(10).text());
	    $(".news .PriceTerm").text("").text(thisList.eq(11).text());
	    $(".news .Packing").text("").text(thisList.eq(12).text());
	    $(".news .TotalValue ").text("").text(fmoney(thisList.eq(13).text(),2));
	    $(".news .Balance ").text("").text(fmoney(thisList.eq(14).text(),2));
	    $(".news .Percent").text("").text(thisList.eq(15).text());
	    $(".news .EndUser").text("").text(thisList.eq(16).text());
	    $(".news .Beneficiary").text("").text(thisList.eq(17).text());
	    $(".news .Applicant").text("").text(thisList.eq(18).text());
	    
	    
		var  ID = thisList.eq(0).attr("value");
		console.log(ID)
		
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

/* 
 $(document).on("click","#submit_n",function(){
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
    var DCNO=  $(".hidePdf .DCNO").text();
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
	        pdf.save('FAT.pdf');
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