<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>箱单制作</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<!-- <link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css"> -->
<!-- <link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" /> -->
<link rel="stylesheet" type="text/css" href="css/libs/bootstrap-buttons-popovers-forms-icons.min.css">
<link rel="stylesheet" type="text/css" href="css/packingList.css">
<link rel="stylesheet" type="text/css" href="css/global/global_table_style.css">
<style>
	.content .select-content {
		padding-top: 1px !important;
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

	.content {
		padding-bottom: 5px !important;
	}

	/*导航栏及搜索框各页面自定义*/
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

	.content .choose {
		margin-bottom: 10px !important;
	}

	.content .choose input[type="button"] {
		margin-top: 25px !important;
		width: auto !important;
		height: auto !important;
	}

	.content .select-button {
		position: relative;
		top: -5px;
	}

	.content .select-button input[type="button"] {
		width: auto !important;
		height: auto !important;
		border-color: #46b8da;
		border: 1px solid transparent !important;
	}

	#global_table_style [data-toggle="popover"] {
		padding: 3px 7px;
	}

	/*可拖动的innerHtml*/
	.glyphicon.glyphicon-resize-horizontal {
		margin-left: -3px;
	    position: relative;
	}

	div.JCLRgrip {
		z-index: 2 !important;
	}

	/*页面表格自定义*/
	#global_table_style td {
	    max-width: 120px;
	}

	#global_table_style th:nth-child(1) {
		min-width: 45px;
		max-width: 45px;
		width: 4.5%;
	}

	#global_table_style th:nth-child(9) {
		min-width: 142px;
		max-width: 142px;
		width: 142px;
	}

	#global_table_style th:nth-child(10) {
		min-width: 80px;
		max-width: 80px;
		width: 80px;
	}
</style>
</head>
<body>
	<div id="originfactory_wrapper">
		<div id="originfactory_sticker">
			<div id="originfactory_sticker-con">
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

			 			<form id="top_text_from" name="top_text_from" method="post" action="GetPackingListRoute">
							<input type="text" id="search" name="isSearch" value="search" style="display: none;">
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
									</c:choose> 
									<c:set var="dropdown" value="${fn:split('客户名称,合同名称,合同号,箱单号,Applicant',',')}"></c:set>
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
										<!-- 第二个搜索框 -->
								<c:if test="${queryType !='mixSelect'}">
									<div class="select2" style="display: none">
										<select name="type2" id="type2">
											<c:forEach items="${dropdown }" var="dropdownList2"
												varStatus="status">
												<c:choose>
													<c:when test="${dropdownList2==type2}">
														<option selected="selected">${dropdownList2}</option>
													</c:when>
													<c:otherwise>
														<option>${dropdownList2}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
										<input type="text" id="searchContent2" name="searchContent2" value="${parameter2}">
									</div>
								</c:if>
								<c:if test="${queryType =='mixSelect'}">
									<div class="select2" style="display: inline-block">
										<select name="type2" id="type2">
											<c:forEach items="${dropdown }" var="dropdownList2"
												varStatus="status">
												<c:choose>
													<c:when test="${dropdownList2==type2}">
														<option selected="selected">${dropdownList2}</option>
													</c:when>
													<c:otherwise>
														<option>${dropdownList2}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
										<input type="text" id="searchContent2" name="searchContent2" value="${parameter2}">
									</div>
								</c:if>
									<div class="select-button">
										<input type="button" value="搜索" class="btn btn-info" onclick="INSearch()"> 
										<input type="button" value="取消" class="btn btn-warning" onclick="Cancel()">
									</div>
							</div> 
							<div class="choose">
								<input type="button" value="添加" class="btn btn-info" onclick="AddContract()">
							</div>

					 </form>
					<div id="global_table_style_wrapper">
						<table id="global_table_style">
							<thead>
								<tr>
									<th>序号</th>
									<th style="display:none;">修改</th>
									<th>客户名称 </th>
									<th>合同名称</th>
									<th>合同号</th>
									<th>箱单号</th>
									<th style="width: 300px;">Applicant</th>
									<th>货物名称</th>
									<th style="width: 170px;min-width:170px">模板预览（可导出）</th>
									<th>发货通知</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="orderInfo" items="${packingList}" varStatus="status">
									<c:if test="${status.index>0}">
										<tr>
											<td value="${orderInfo['ID']}" class="contract-edit" style="cursor:pointer">${status.index+(currentPage-1)*10}</td>
											<td style="display:none;"><i class="fa fa-edit" value="${orderInfo['ID']}"></i></td>
											<td title="${orderInfo['CustomerName']}" class="CustomerName_td">${orderInfo['CustomerName']}</td>
											<td title="${orderInfo['ContractTitle']}">${orderInfo['ContractTitle']}</td>
											<td title="${orderInfo['ContractNO']}" class="ContractNO_td">${orderInfo['ContractNO']}</td>
											<td title="${orderInfo['PackingListNO']}">${orderInfo['PackingListNO']}</td>
											<td title="${orderInfo['ToAPP']}">${orderInfo['ToAPP']}</td>
											<td title="${orderInfo['Model']}" class="Model_td">${orderInfo['Model']}</td>
											<td>
												<input type="button" class="btn btn-info btn-xs contract-show" value="配置模板" >
												<input type="button" class="btn btn-info btn-xs contract-show" value="尺寸模板" >
											</td>
											<!-- <td><i class="fa fa-download " id="exportPDF"></i></td> -->
											<td style="display:none;">${orderInfo['Date']}</td>    <!--9  -->
											<td style="display:none;">${orderInfo['PackingListNO']}</td>
											<td style="display:none;">${orderInfo['FromAPP']}</td>
											<td style="display:none;">${orderInfo['FromADD']}</td>
											<td style="display:none;">${orderInfo['FromTel']}</td><!--13  -->
											<td style="display:none;">${orderInfo['ToAPP']}</td>
											<td style="display:none;">${orderInfo['ToADD']}</td>
											<td style="display:none;">${orderInfo['ToATT']}</td><!--16  -->
											<td style="display:none;">${orderInfo['ToTel']}</td>    <!--17  -->
											<td style="display:none;">${orderInfo['ContractNO']}</td>
											<td style="display:none;" class="PONO">${orderInfo['PONO']}</td>
											<td style="display:none;">${orderInfo['Packing']}</td>
											<td style="display:none;">${orderInfo['ShipVia']}</td><!--21 -->
											<td style="display:none;">${orderInfo['DCNO']}</td>
											<td style="display:none;" class="PONOAll">${orderInfo['PONOAll']}</td><!--23  -->
											<td style="display:none;">${orderInfo['Model']}</td><!--24  -->
											<td style="display:none;">${orderInfo['Origin']}</td>    <!--25  -->
											
											<td style="display:none;">${orderInfo['ShippingMark']}</td>
											<td style="display:none;">${orderInfo['PackingCondition']}</td>
											<td style="display:none;">${orderInfo['Sender']}</td>
											<td style="display:none;">${orderInfo['Recepter']}</td><!--29  -->
											<td style="display:none;">${orderInfo['Dimension']}</td>
											<td style="display:none;">${orderInfo['GrossWeight']}</td>
											<td style="display:none;">${orderInfo['NetWeight']}</td><!--32  -->
											<td style="display:none;">${orderInfo['TotalGrossWeight']}</td>
											<td style="display:none;">${orderInfo['TotalNetWeight']}</td><!--34  -->
											<td style="display:none;">${orderInfo['Quantity']}</td><!--35  -->
											<td style="display:none;">${orderInfo['ToContact']}</td><!--36  -->
								 		<c:choose>
											<c:when test="${orderInfo['TrackingNO'] == '--'}">
												<td class="popover_td"><a tabindex="0" class="btn btn-sm btn-info" role="button" data-toggle="popover" data-trigger="click" data-container="body" data-placement="left" title="对第${status.index+(currentPage-1)*10}条记录操作">未发送</a></td>
											</c:when>
											<c:when test="${orderInfo['TrackingNO'] == ''}">
												<td class="popover_td">未填运单号</td>
											</c:when>
											<c:otherwise>
												<td class="popover_td">${orderInfo['TrackingNO']}</td>
											</c:otherwise>
										</c:choose>
										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
					</div>	
					
				 		<c:choose>
							<c:when test="${queryType == 'common'}">
								<c:set var="queryUrl"
								value="PackingList?type1=${classify1 }&searchContent1=${parameter1}&selected=${str}&currentPage=">
								</c:set>
							</c:when>
							<c:otherwise>
								<c:set var="queryUrl"
								value="GetPackingListRoute?type1=${classify1 }&searchContent1=${parameter1}&type2=${classify2}&searchContent2=${parameter2}&selected=${queryType}&currentPage=">
								</c:set>
							</c:otherwise>
						</c:choose> 
								
					<div id="glbal_table_page_wrapper">
			 			<div id="glbal_table_page">
							<div class="pageInfo">
								当前是第&nbsp;<span id="currentPage">${currentPage}</span>&nbsp;页,&nbsp;总计&nbsp;<span
									id="allPage">${pageCounts}</span>页
							</div>
							<div class="changePage">
								<input type="button" class="btn btn-primary" value="首页" id="fistPage" name="fistPage" onclick="FistPage('${queryUrl}')">
								<input type="button" class="btn btn-primary" value="上一页" id="upPage" onclick="UpPage('${queryUrl}${currentPage-1 }')">
								<input type="button" class="btn btn-primary" value="下一页" id="nextPage" onclick="NextPage('${queryUrl}${currentPage+1 }')"> 跳到第 <input type="text" id="jumpNumber" name="jumpNumber" class="jumpNumber" style="width: 30px;height: 25px;" onkeyup="value=value.replace(/[^\d]/g,'') "> 页 <input type="button" class="btn btn-primary" value="GO" id="Gotojump" name="Gotojump" onclick="PageJump('${queryUrl}')">
								<input type="button" class="btn btn-primary" value="尾页" id="lastPage" name="lastPage" onclick="LastPage('${queryUrl}')">
							</div>
						</div>
					</div><!-- glbal_table_page_wrapper end   -->
			 		
					</div>
				</div>
				<div class="MailBar_cover_color"></div>
				
				<!-- 添加箱单信息 -->
				<div class="contract_add">
					<div class="contract_title">添加箱单信息</div>
					<div class="contractAdd_close">关闭</div>
					<div class="basic_info">
						<div class="table_title">箱单基本信息</div>
						<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
							<tbody>
								<tr>
									<td>DATE</td>
									<td><input type="date" name="Date" value="" id="Date"></td>
									<td >PACKING LIST NO.</td>
									<td ><input type="text" name="PackingListNO" value="" id="PackingListNO"></td>
									<td>PACKLNG</td>
									<td>
										<input type="text" name="Packing" value="" style="position:absolute;width:162px;" id="Packing">
										<span>
											<select onchange="document.getElementById('Packing').value=this.options[this.selectedIndex].text">
												<option value="No" selected="selected">--请选择--</option>
												<option value="Pack" >BOX</option>
												<option value="Pack" >WOODEN CASES</option>
											</select>
										</span>
									</td>
									<td>CONTACT</td>
									<td ><input type="text" name="ToContact" value=""  id="ToContact"></td>
								</tr>
								<tr>
									<td>FROM</td>
									<td>
										<input type="text" name="FromApp" value="" style="position:absolute;width:162px;" id="FromApp">
										<span>
											<select class="FromAppSelect">
												<option value="No"  selected="selected">--请选择--</option>
												<option value="100%">HK EOULU TRADING LIMITED</option>
												<option value="10%" >苏州伊欧陆系统集成有限公司</option>
											</select>
										</span>
										
									</td>
									<td class="hideFromTD">FROM ADD</td>
									<td class="hideFromTD">
										<input type="text" name="FromAdd" value="" style="position:absolute;width:162px;" id="FromAdd">
										<span>
											<select onchange="document.getElementById('FromAdd').value=this.options[this.selectedIndex].text"  class="FromAddSelect">
												<option value="No" selected="selected">--请选择--</option>
												<option value="GERMANY">ROOM 1501, GRAND MILLENNIUM PLAZA (LOWER BLOCK), 181 QUEEN’S ROAD CENTRAL, HONG KONG</option>
												<option value="USA" >苏州工业园区星湖街218号生物纳米园A7楼305室</option>
											</select>
										</span>
									</td>
									<td  class="hideFromTD">FROM TEL/FAX</td>
									<td  class="hideFromTD">
										<input type="text" name="FromTel" value="" style="position:absolute;width:162px;" id="FromTel">
										<span>
											<select onchange="document.getElementById('FromTel').value=this.options[this.selectedIndex].text" class="FromTelSelect">
												<option value="No" selected="selected">--请选择--</option>
												<option value="GERMANY">TEL:00852-21527388&&FAX:00852-35719160</option>
												<option value="USA" >TEL:+86-512-62757360&&FAX:+86-512-62757313</option>
											</select>
										</span>	
									</td>
								</tr>
								
								<tr>
									<td>TO</td>
									<td>
										<input type="text" name="ToApp" value="" style="position:absolute;width:162px;" id="ToApp">
										<span>
											<select class="ToAppSelect">
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
									<td class="hideToTD">TO ADD</td>
									<td class="hideToTD">
										<input type="text" name="ToAdd" value="" style="position:absolute;width:162px;" id="ToAdd">
										<span>
											<select onchange="document.getElementById('ToAdd').value=this.options[this.selectedIndex].text"  class="ToAddSelect">
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
									<td  class="hideToTD">TO TEL/FAX</td>
									<td  class="hideToTD">
										<input type="text" name="ToTel" value="" style="position:absolute;width:162px;" id="ToTel">
										<span>
											<select onchange="document.getElementById('ToTel').value=this.options[this.selectedIndex].text" class="ToTelSelect">
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
									</td></br>
									<td class="hideToTD">TO ATT</td>
									<td class="hideToTD"><input type="text" name="ToAtt" value=""  id="ToAtt"></td>
								</tr>
								
								<tr>	
									<td>CONTRACT NO.</td>
									<td><input type="text" name="contract_no" value=""  id="contract_no"></td>
									<td>PO NO.</td>
									<td><input type="text" name="PONO" value="" ></td>
									<td>DC NO.</td>
									<td><input type="text" name="DC_NO" value=""></td>
									<td>SHIP VIA</td>
									<td>
										<input type="text" name="ShipVia" value="" style="position:absolute;width:162px;" id="ShipVia">
										<span>
											<select onchange="document.getElementById('ShipVia').value=this.options[this.selectedIndex].text">
												<option value="No" selected="selected">--请选择--</option>
												<option value="GERMANY">EXP</option>
												<option value="USA" >AWOT</option>
											</select>
										</span>
										
									</td>
								</tr>
								<tr>
									<td style="font-size:20px;font-weight:bold;">配置信息</td>
								</tr>
								<tr>
									<td>发货方</td>
									<td>
										<input type="text" name="Sender" value="" style="position:absolute;width:162px;" id="Sender">
										<span>
											<select onchange="document.getElementById('Sender').value=this.options[this.selectedIndex].text">
												<option value="No" selected="selected">--请选择--</option>
												<option value="GERMANY">HK EOULU TRADING LIMITED </option>
												<option value="USA" >苏州伊欧陆系统集成有限公司</option>
											</select>
										</span>
										
									</td>
									<td>收货方</td>
									<td><input type="text" name="Recepter" value="" ></td>
								</tr>
								<tr>
									<td style="font-size:20px;font-weight:bold;">尺寸信息</td>
								</tr>
								
								<tr>
									<td>COUNTRY OF ORIGIN</td>
									<td>
										<input type="text" name="Origin" value="" style="position:absolute;width:162px;" id="Origin">
										<span>
											<select onchange="document.getElementById('Origin').value=this.options[this.selectedIndex].text">
												<option value="No" selected="selected">--请选择--</option>
												<option value="GERMANY">GERMANY</option>
												<option value="USA" >USA</option>
												<option value="HK" >HONG KONG</option>
											</select>
										</span>
										
									</td>
								</tr>
								<tr>	
									<td>SHIPPING MARK NO.</td>
									<td><input type="text" name="ShippingMarkNo" value=""></td>
									<td>SHIPPING MARK ADD</td>
									<td><input type="text" name="ShippingMarkAdd" value=""></td>
									<td>PACKING CONDITION</td>
									<td>
										<input type="text" name="PackingCondition" value="" style="position:absolute;width:162px;" id="PackingCondition">
										<span>
											<select onchange="document.getElementById('PackingCondition').value=this.options[this.selectedIndex].text">
												<option value="No" selected="selected">--请选择--</option>
												<option value="GERMANY">STANDARD EXPORT PACKING SUITABLE FOR LONG-DISTANCE TRANSPORTATION</option>
												<option value="USA" >TO BE PACKED IN NEW STRONG WOODEN CASES (CERTIFICATED BY IPPC) OR CARTONS</option>
											</select>
										</span>
										
									</td>
								</tr>
							</tbody>
						</table>
						<div class="table_title"> <input type="button" name="addItem" value="添加尺寸信息" class="bToggle addItem"> </div>
						<div class="table_title"> <input type="button" name="addModel" value="添加货物信息" class="bToggle addModel"> </div>
					<!-- 	<table>
							
						
						</table> -->
					</div>
					<div class="edit_btn">
						<input type="button" value="合同匹配" class="bToggle" id="add_search">
						<input type="button" value="提交" class="bToggle" id="add_submit">
						<input type="button" value="取消" class="bToggle" id="add_cancel">
					</div>
				</div>
				
				<!-- 修改箱单信息 -->
				<div class="contract_update">
					<div class="contract_title">修改箱单信息</div>
					<div class="contractUpdate_close">关闭</div>
					<div class="basic_info">
						<div class="table_title">箱单基本信息</div>
						<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
							<tbody>
								<tr>
									<td>DATE</td>
									<td><input type="date" name="Date" value="" id="Date1"></td>
									<td >PACKING LIST NO.</td>
									<td ><input type="text" name="PackingListNO" value="" id="PackingListNO1"></td>
									<td>PACKING</td>
									<td>
										<input type="text" name="Packing" value="" style="position:absolute;width:162px;" id="Packing1">
										<span>
											<select onchange="document.getElementById('Packing1').value=this.options[this.selectedIndex].text">
												<option value="No" selected="selected">--请选择--</option>
												<option value="Pack" >BOX</option>
												<option value="Pack" >WOODEN CASES</option>
											</select>
										</span>
									</td>
									<td>CONTACT</td>
									<td ><input type="text" name="ToContact" value=""  id="ToContact1"></td>
								</tr>
								<tr>
									<td>FROM</td>
									<td>
										<input type="text" name="FromApp" value="" style="position:absolute;width:162px;" id="FromApp1">
										<span>
											<select class="FromAppSelect">
												<option value="No"  selected="selected">--请选择--</option>
												<option value="100%">HK EOULU TRADING LIMITED</option>
												<option value="10%" >苏州伊欧陆系统集成有限公司</option>
											</select>
										</span>
										
									</td>
									<td class="hideFromTD">FROM ADD</td>
									<td class="hideFromTD">
										<input type="text" name="FromAdd" value="" style="position:absolute;width:162px;" id="FromAdd1">
										<span>
											<select onchange="document.getElementById('FromAdd1').value=this.options[this.selectedIndex].text"  class="FromAddSelect">
												<option value="No" selected="selected">--请选择--</option>
												<option value="GERMANY">ROOM 1501, GRAND MILLENNIUM PLAZA (LOWER BLOCK), 181 QUEEN’S ROAD CENTRAL, HONG KONG</option>
												<option value="USA" >苏州工业园区星湖街218号生物纳米园A7楼305室</option>
											</select>
										</span>
									</td>
									<td  class="hideFromTD">FROM TEL/FAX</td>
									<td  class="hideFromTD">
										<input type="text" name="FromTel" value="" style="position:absolute;width:162px;" id="FromTel1">
										<span>
											<select onchange="document.getElementById('FromTel1').value=this.options[this.selectedIndex].text" class="FromTelSelect">
												<option value="No" selected="selected">--请选择--</option>
												<option value="GERMANY">TEL:00852-21527388&&FAX:00852-35719160</option>
												<option value="USA" >TEL:+86-512-62757360&&FAX:+86-512-62757313</option>
											</select>
										</span>	
									</td>
								</tr>
								
								<tr>
									<td>TO</td>
									<td>
										<input type="text" name="ToApp" value="" style="position:absolute;width:162px;" id="ToApp1">
										<span>
											<select class="ToAppSelect">
												<option value="No" selected="selected">--请选择--</option>
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
									<td class="hideToTD">TO ADD</td>
									<td class="hideToTD">
										<input type="text" name="ToAdd" value="" style="position:absolute;width:162px;" id="ToAdd1">
										<span>
											<select onchange="document.getElementById('ToAdd1').value=this.options[this.selectedIndex].text"  class="ToAddSelect">
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
									<td  class="hideToTD">TO TEL/FAX</td>
									<td  class="hideToTD">
										<input type="text" name="ToTel" value="" style="position:absolute;width:162px;" id="ToTel1">
										<span>
											<select onchange="document.getElementById('ToTel1').value=this.options[this.selectedIndex].text" class="ToTelSelect">
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
									</td></br>
									<td class="hideToTD">TO ATT</td>
									<td class="hideToTD"><input type="text" name="ToAtt" value=""  id="ToAtt1"></td>
								</tr>
								
								
								<tr>	
									<td>CONTRACT NO.</td>
									<td><input type="text" name="contract_no" value=""  id="contract_no1"></td>
									<td>PO NO.</td>
									<td><input type="text" name="PONO" value="" ></td>
									<td>DC NO.</td>
									<td><input type="text" name="DC_NO" value=""></td>
									<td>SHIP VIA</td>
									<td>
										<input type="text" name="ShipVia" value="" style="position:absolute;width:162px;" id="ShipVia1">
										<span>
											<select onchange="document.getElementById('ShipVia1').value=this.options[this.selectedIndex].text">
												<option value="No" selected="selected">--请选择--</option>
												<option value="GERMANY">EXP</option>
												<option value="USA" >AWOT</option>
											</select>
										</span>
										
									</td>
								</tr>
								<tr  class="addItemCont">
									<td style="font-size:20px;font-weight:bold;">配置信息</td>
								</tr>
								<tr>
									<td>发货方</td>
									<td>
										<input type="text" name="Sender" value="" style="position:absolute;width:162px;" id="Sender1">
										<span>
											<select onchange="document.getElementById('Sender1').value=this.options[this.selectedIndex].text">
												<option value="No" selected="selected">--请选择--</option>
												<option value="GERMANY">HK EOULU TRADING LIMITED </option>
												<option value="USA" >苏州伊欧陆系统集成有限公司</option>
											</select>
										</span>
										
									</td>
									<td>收货方</td>
									<td><input type="text" name="Recepter" value="" ></td>
								</tr>
								<tr>
									<td style="font-size:20px;font-weight:bold;">尺寸信息</td>
								</tr>
								
								<tr>
									<td>COUNTRY OF ORIGIN</td>
									<td>
										<input type="text" name="Origin" value="" style="position:absolute;width:162px;" id="Origin1">
										<span>
											<select onchange="document.getElementById('Origin1').value=this.options[this.selectedIndex].text">
												<option value="No" selected="selected">--请选择--</option>
												<option value="GERMANY">GERMANY</option>
												<option value="USA" >USA</option>
												<option value="HK" >HONG KONG</option>
											</select>
										</span>
										
									</td>
								</tr>
								<tr>	
									<td>SHIPPING MARK NO.</td>
									<td><input type="text" name="ShippingMarkNo" value=""></td>
									<td>SHIPPING MARK ADD</td>
									<td><input type="text" name="ShippingMarkAdd" value=""></td>
									<td>PACKING CONDITION</td>
									<td>
										<input type="text" name="PackingCondition" value="" style="position:absolute;width:162px;" id="PackingCondition1">
										<span>
											<select onchange="document.getElementById('PackingCondition1').value=this.options[this.selectedIndex].text">
												<option value="No" selected="selected">--请选择--</option>
												<option value="GERMANY">STANDARD EXPORT PACKING SUITABLE FOR LONG-DISTANCE TRANSPORTATION</option>
												<option value="USA" >TO BE PACKED IN NEW STRONG WOODEN CASES (CERTIFICATED BY IPPC) OR CARTONS</option>
											</select>
										</span>
										
									</td>
								</tr>
							</tbody>
						</table>
						<div class="table_title"> <input type="button" name="addItem" value="添加尺寸信息" class="bToggle addItem"> </div>
						<div class="table_title"> <input type="button" name="addModel" value="添加货物信息" class="bToggle addModel"> </div>
					<!-- 	<table>
							
						</table> -->
					</div>
					<div class="edit_btn">
					<input type="button" value="合同匹配" class="bToggle" id="update_search">
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

			    <!--尺寸部分-->
			    <div id="table_PackSize" style="display:none;">
			        <p class="f16">
			            Packing List
			        </p>
			        <p><br></p>
			        <table cellpadding="0" cellspacing="0" border="1" style=" width:40%; height:80px;margin-bottom: 20px;" class="tc lf">
			            <tr class="b">
			                <td colspan="3" >DATE</td>
			                <td>PACKING LIST NO.</td>
			            </tr>
			            <tr>
			                <td colspan="3"><p contenteditable="true" class="Date">2017/07/17</p></td>
			                <td><p contenteditable="true" class="PackingListNO">PL20170717-1</p></td>
			            </tr>
			        </table>

			        <div style="height: 190px;" class="cf">
			            <div class="table2 lf">
			                <div>
			                    <p class="FT b pl10">FROM:</p>
			                </div>
			                <div style="padding-left: 10px;">
			                    <P contenteditable="true" class="FromApp"></P>
			                    <P contenteditable="true" class="FromAdd MaxW" ></P>
			                    Tel :<P contenteditable="true" class="FromATel" style="display:inline-block;"> </P></br>
			                    Fax: <P contenteditable="true" class="FromAFax" style="display:inline-block;"></P>

			                </div>
			            </div>
			            <div class="table2 rt">
			                <div>
			                    <p class="FT b pl10">TO:</p>
			                </div>
			                <div style="padding-left: 10px;padding-top: 10px;">
			                    <P contenteditable="true" class="ToApp"></P>
			                    <P contenteditable="true" class="ToAdd MaxW"></p>
			                    <p contenteditable="true" class="ToAtt" style="display:none;"></P>
			                    <p>Contact:<span contenteditable="true" class="ToContact"  style="display:inline-block;"></span></p>
			                    <p>TEL:<span contenteditable="true" class="ToTEL"  style="display:inline-block;"></span></p>
			                    <p>FAX:<span contenteditable="true" class="ToFAX"  style="display:inline-block;"></span></p>
			                </div>
			            </div>
			        </div>
			        
			        <table cellpadding="0" cellspacing="0" border="1" style=" width:100%;margin-bottom: 20px;" class="tc goodsDataTable" >
			            <tr>
			                <td colspan="2" contenteditable="true" class="Co_PONO">CONTRACT NO.</td>
			                <td colspan="2">PACKING</td>
			                <td >SHIP VIA</td>
			                <td >DC NO.</td>
			            </tr>
			            <tr>
			                <td colspan="2"><p contenteditable="true" class="ContractNO"></P> <P contenteditable="true" class="PoNO" style=" word-break: break-word;"></p> </td>
			                <td colspan="2"><p contenteditable="true" class="Packing"></p>
			                <!-- <p p contenteditable="true"></p> -->
			                </td>
			                <td><p contenteditable="true" class="ShipVia">AWOT</p></td>
			                <td><p contenteditable="true" class="DCNO">NA</p></td>
			            </tr>
			            <tr class="b  ">
			                <td><p>Package </p><p>NO.</p></td>
			                <td  colspan="2">Dimension(CM)</td>
			                <td><p>GROSS </p><p>WEIGHT(KG)</p></td>
			                <td><p>NET </p><p>WEIGHT(KG)</p></td>
			                <td>Quantity</td>
			            </tr>
			            <tbody class="itemTol" > </tbody>

			            <tr><td colspan="6" class="TOTALGROSSTD"><p class="rt b " style="margin-right: 7px;">TOTAL GROSS WEIGHT(KG):<span contenteditable="true" class="TotalGrossWeight">920</span></p></td></tr>
			            <tr><td colspan="6" class="TOTALNETTD"><p class="rt b " style="margin-right: 7px;">TOTAL NET WEIGHT(KG):<span contenteditable="true"  class="TotalNetW">825</span></p></td></tr>
			            <tr style="text-align: left;">
			                <td colspan="6" class="pl10" style="position:relative;line-height: 25px;">
			                    <p class="b GOODSTitle">DESCRIPTION OF GOODS:</p>
			                   <!--  <p contenteditable="true" class="Description">PROBE STATION PLATFORM, SEMI-AUTOMATI WITH MICROCHAMBER</p>
			                    MODEL:<span contenteditable="true" class="Model"> Summit12000B-M</span><br>
			                    QTY:<span contenteditable="true" class="Qty"> 1 SET</span><br> -->
			                    Country of Origin:<span contenteditable="true" class="Origin"> Germany</span><br>
			                    SHIPPING MARK:<span contenteditable="true" style="text-decoration: underline;display:inline-block;" class="ShippingMark">  GNHCHNEP17030611LXP  </span><br>
			                    <p style="width: 117px;display: inline-block;"></p><span contenteditable="true" class="MarkAdd tc" style="display:inline-block;"> SHENZHEN</span><br>
			                    PACKING CONDITION:<span contenteditable="true" class="PackingCondition"> WOODEN CASE AND BOX</span>
			                </td>
			            </tr>

			        </table>
			        <p style="height: 40px;margin-top: 10px;position:relative; margin-bottom: 15px;">Signature:<span contenteditable="true" style="text-decoration: underline;position:relative;" class="Signature">
			        	 <!-- <img src="image/qm.png" style="position:absolute;top:-13px;left:15px;display:inline-block;width:165px;height:50px;" class="InImg1"/> -->
			        	 <img src="image/zhang2.png"  style="position:absolute;top:-35px;left:350px;width:120px;height:120px;" class="InImg HongKong"/>
			        	 <!-- <img src="image/zhangSuZhou.png"  style="position:absolute;top:-15px;left:350px;width:100px;height:100px; class="InImg SuZhou"/> -->
			        </span></p>
			        Date:<span class="Date" contenteditable="true">2017/09/13</span>

			    </div>
				<!--配置部分-->
			    <div id="table_Config" style="display:none;">
			        <p class="f16">
			            Packing List
			        </p>
			        <p><br></p>

			        <table cellpadding="0" cellspacing="0" border="1" style=" width:40%; height:80px;margin-bottom: 20px;" class="tc lf">
			            <tr class="b">
			                <td colspan="3" >DATE</td>
			                <td>PACKING LIST NO.</td>
			            </tr>
			            <tr>
			                <td colspan="3"><p contenteditable="true" class="Date">2017/7/21</p></td>
			                <td><p contenteditable="true" class="PackingListNO"></p></td>
			            </tr>
			        </table>

			        <div style="height: 190px;" class="cf">
			            <div class="table2 lf">
			                <div>
			                    <p class="FT b pl10">FROM:</p>
			                </div>
			                <div style="padding-left: 10px;">
			                    <P class="FromApp"></P>
			                    <P class="FromAdd MaxW"></P>
			                    Tel :<P class="FromATel" style="display:inline-block;"> </P></br>
			                    Fax :<P class="FromAFax" style="display:inline-block;"> </P>
			                </div>
			            </div>
			            <div class="table2 rt">
			                <div>
			                    <p class="FT b pl10">TO:</p>
			                </div>
			                <div style="padding-left: 10px;padding-top: 10px;">
			                    <P contenteditable="true" class="ToApp"></P>
			                    <P contenteditable="true" class="ToAdd MaxW"> </p>
			                    <p contenteditable="true" class="ToAtt" style="display:none;"></P>
			                    <p>Contact:<span contenteditable="true" class="ToContact"></span></p>
			                    <p>TEL:<span contenteditable="true" class="ToTEL"></span></p>
			                    <p>FAX:<span contenteditable="true" class="ToFAX"></span></p>
			                </div>
			            </div>
			        </div>

			        <table cellpadding="0" cellspacing="0" border="1" style=" width:100%;   margin-bottom: 20px;" class="tc configTable" >
			            <tr>
			                <td colspan="3" contenteditable="true" class="Co_PONO">CONTRACT /PO NO.</td>
			                <td colspan="2">PACKING</td>
			                <td >SHIP VIA</td>
			                <td >DC NO.</td>
			            </tr>
			            <tr>
			                <td colspan="3"><p contenteditable="true" class="ContractNO"></P> <P contenteditable="true" class="PoNO">910071246 </p> </td>
			                <td colspan="2"><p contenteditable="true" class="Packing"></p></td>
			                <td ><p contenteditable="true" class="ShipVia"></p></td>
			                <td><p contenteditable="true" class="DCNO"></p></td>
			            </tr>
			            <tr class="b ItemTableTitle" >
			                <td><p>Item</p></td>
			                <td colspan="2">Model</td>
			                <td colspan="3"><p>Description </p></td>
			                <td><p>Quantity</p></td>
			             <!--    <td class="RemoveCurremtItem"><p>Remove</p></td> -->
			            </tr>
			           <tbody class="configItem" ></tbody>

			        </table>
					<div class="addItemBtn" style="width:50px;height:36px;margin-top:-5px;">
						<input type="button" id="addBtn" class="bToggle" value="添加"  style="width:50px;height:26px;"/>
					</div>
			        <div class="write-name lf" style="padding-left: 30px;position: relative;">
			            <p contenteditable="true" class="Sender">香港伊欧陆贸易有限公司 </p><br><br>
			            <p style="position: absolute;top: 55px;">签字:<span contenteditable="true" style="text-decoration: underline;position: relative;">
			            	<img src="image/qm.png" style="position:absolute;top:-10px;left:10px;display:inline-block;width:160px;height:45px;" class="InImg1"/>
			            </span></p><br>
			            <p style="position: absolute;top: 115px;">日期:<span contenteditable="true" class="YMR">&nbsp; 年&nbsp; 月&nbsp; 日</span></p>
			           	<img src="image/zhang2.png" contenteditable="true"  style="position:absolute;top:5px;left:145px;width:120px;height:120px;" class="InImg HongKong"/>
			           <!--  <img src="image/zhangSuZhou.png" contenteditable="true"  style="position:absolute;top:-20px;left:340px;width:120px;height:120px;" class="InImg SuZhou"/> -->
			        </div>
			        <div class="write-name rt"  style="position: relative;">
			            <p contenteditable="true" class="Recepter">华为技术有限公司 </p><br><br>
			            <p style="position: absolute;top: 55px;">签字:<span contenteditable="true" style="text-decoration: underline"></span></p><br>
			            <p style="position: absolute;top: 115px;">日期:<span contenteditable="true">&nbsp; 年&nbsp; 月&nbsp; 日</span></p>
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
						<input type="button" value="无章导出" class="bToggle ShowOrHide" id="exportPDF2" style="position:absolute;z-index:11;top: 200px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
						<input type="button" value="带章导出" class="bToggle ShowOrHide" id="exportPDF1" style="position:absolute;z-index:11;top: 150px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
						<input type="button" value="关闭" class="bToggle " id="contract_close1" style="position: absolute;z-index: 11;top: 100px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
				</div>

			<!-- 邮件发送模板 -->
			<div class="mail_cover_bg"></div>
			<div class="mail_template">
				<div class="mail_template_tit">邮件模板</div>
				<div class="mail_template_body">
					<fieldset><legend>发件人</legend>
						<div class="form-group">
						    <!-- <label for="exampleInputEmail1">上午</label> -->
                            <input type="text" class="form-control" placeholder="选择发件人" value="remind@eoulu.com" readonly disabled>
						</div>
					</fieldset>
					<fieldset><legend>收件人</legend>
                        <div class="TO_wrapper"><div class="mail_item"><span class="mail_item_info">收件人1</span><input type="text" class="form-control" value="zhaona@eoulu.com"><span class="mail_item_add glyphicon glyphicon-plus" aria-hidden="true"></span><span class="mail_item_del glyphicon glyphicon-remove" aria-hidden="true"></span></div><div class="mail_item"><span class="mail_item_info">收件人2</span><input type="text" class="form-control" value="fangyuanyuan@eoulu.com"><span class="mail_item_add glyphicon glyphicon-plus" aria-hidden="true"></span><span class="mail_item_del glyphicon glyphicon-remove" aria-hidden="true"></span></div></div>
					</fieldset>
					<fieldset><legend>抄送人</legend>
						<div class="CC_wrapper"><div class="mail_item"><span class="mail_item_info">抄送人1</span><input type="text" class="form-control" value="liuyanan@eoulu.com"><span class="mail_item_add glyphicon glyphicon-plus" aria-hidden="true"></span><span class="mail_item_del glyphicon glyphicon-remove" aria-hidden="true"></span></div><div class="mail_item"><span class="mail_item_info">抄送人2</span><input type="text" class="form-control" value="jiangyaping@eoulu.com"><span class="mail_item_add glyphicon glyphicon-plus" aria-hidden="true"></span><span class="mail_item_del glyphicon glyphicon-remove" aria-hidden="true"></span></div><div class="mail_item"><span class="mail_item_info">抄送人3</span><input type="text" class="form-control" value="zhaowenzhen@eoulu.com"><span class="mail_item_add glyphicon glyphicon-plus" aria-hidden="true"></span><span class="mail_item_del glyphicon glyphicon-remove" aria-hidden="true"></span></div></div>
					</fieldset>
					<fieldset><legend>主题</legend>
						<div class="form-group">
						    <!-- <label for="exampleInputEmail1">上午</label> -->
						    <input type="text" class="form-control" id="i_mail_Subject" placeholder="填写主题">
						</div>
					</fieldset>
					<fieldset><legend>内容</legend>
						<div class="form-group">
						    <!-- <label for="exampleInputEmail1">上午</label> -->
						    <textarea class="form-control" rows="5" id="i_mail_Content" placeholder="填写内容"></textarea>
						</div>
					</fieldset>
					<fieldset><legend>上传附件<input type="file" id="BiddingDocumentUpload" accept="application/msword, application/pdf, image/*, application/vnd.ms-powerpoint, text/plain, application/vnd.openxmlformats-officedocument.presentationml.presentation, application/vnd.openxmlformats-officedocument.wordprocessingml.document">
                        <label for="BiddingDocumentUpload"><span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span></label><label class="upload_file_info"></label></legend>
						<div class="form-group">
                            <textarea class="form-control" rows="2" id="i_mail_Attachment" placeholder="点击图标上传附件" readonly></textarea>
						</div>
					</fieldset>
				</div>
				<div class="mail_template_foot">
					<div class="mail_template_body_foot_in">
						<input type="button" class="btn btn-success" id="mail_template_submit" value="发送">
						<input type="button" class="btn btn-warning" id="mail_template_close" value="取消">
					</div>
				</div>
			</div>
			<!-- 邮件发送模板end -->

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
<script src="js/libs/bootstrap/bootstrap-buttons-popovers-forms-icons.min.js" type="text/javascript" charset="utf-8"></script>
<script src="js/msgbox.js"></script>
<!-- <script src="js/msgbox_unload.js"></script> -->
<!-- <script src="js/global/myFunction.js"></script> -->
<script src="js/global/responseLoading.js"></script>
<script src="js/packlist.js?iv=201809120958"></script>
<!-- <script type="text/javascript" src="js/html2canvas.js"></script> -->
<!-- <script type="text/javascript" src="js/jsPdf.debug.js"></script> -->
<script>
// 翻页组件按钮逻辑
function pageStyle(currentPage,pageCounts){
    if(pageCounts == 1){
        $("#fistPage, #upPage, #nextPage, #lastPage, #Gotojump").prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
    }else if(currentPage == 1){
        $("#fistPage, #upPage").prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
        $("#lastPage, #nextPage, #Gotojump").prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }else if(currentPage == pageCounts){
        $("#lastPage, #nextPage").prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
        $("#fistPage, #upPage, #Gotojump").prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }else{
        $("#lastPage, #nextPage, #fistPage, #upPage, #Gotojump").prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }
}

$(".contract_add #add_search").click(function(){
	var contractNo = $(".contract_add #contract_no").val();
	var PONO = $(".contract_add input[name='PONO']").val();
	$.ajax({
        type : 'get',
        url : "GetContractConfigure",
        data : {
        	ContractNO : contractNo,
        	PONOAll   :  PONO
        },
        dataType : 'json',
        success : function (data) {  
           	 if(data){
           		$.MsgBox_Unload.Alert('提示','合同匹配成功！');
           	} 
           	 else{
           		$.MsgBox_Unload.Alert('提示','无匹配合同！');
           	 }
        },
        error : function () {
           
        }
    });
});

$(".contract_update #update_search").click(function(){
	var contractNo = $(".contract_update #contract_no1").val();
	var PONO; 
	$(".contract_update input[name='PONO']").val()==""?PONO="NA" :PONO=$(".contract_update input[name='PONO']").val();
	$.ajax({
        type : 'get',
        url : "GetContractConfigure",
        data : {
        	ContractNO : contractNo,
        	PONOAll   :  PONO
        },
        dataType : 'json',
        success : function (data) {  
        	 if(data){
	           		$.MsgBox_Unload.Alert('提示','合同匹配成功！');
	           	} 
	           	 else{
	           		$.MsgBox_Unload.Alert('提示','无匹配合同！');
	           	 }
        },
        error : function () {
           
        }
    });
});

var todayCounts = <%=request.getAttribute("todayCounts")%>;  //箱单号的第几条
console.log(todayCounts);
//点击添加
function AddContract(){
	$("#Date").val(globalGetToday(false));
	/*自动生成想单号  */
	todayCounts = todayCounts+1;
	var packingListNoStr = "PL"+ ddd.getFullYear()+month+day+"-"+todayCounts;
	$("#PackingListNO").val(packingListNoStr);
    $('.MailBar_cover_color').show();
    $('.contract_add').show();
};

var addFlag = 1;

//添加里的item尺寸
$(".contract_add .addItem").click(function(){
	addFlag++;
	var sddStr =  '<tr class="PackageTr">'+
		'<td>PACKAGE NO.</td>'+
		'<td><input type="text" name="PackageNO" value="'+addFlag+'" class="Package"></td>'+
	'</tr>'+ 
	'<tr class="addSizeTr">'+
		'<td>DIMENSION(CM)</td>'+
		'<td class="Dimension"><input type="text" name="Dimension" value=""></td>'+
		'<td>GROSS WEIGHT(KG)</td>'+
		'<td class="GrossWeight"><input type="text" name="GrossWeight" value=""></td>'+
		'<td>NET WEIGHT(KG)</td>'+
		'<td class="NetWeight"><input type="text" name="NetWeight" value=""></td>'+
		'<td>QUANTITY</td>'+
		'<td class="Quantity"><input type="text" name="Quantity" value=""></td>'+
		'<td><input type="button" name="DelitemThisTr"  class="DelitemThisTr bToggle"  value="删除本行尺寸信息" ></td>'+
	'</tr>';
	$(".contract_add .contract_basic").append(sddStr);
})

//修改里的item尺寸
$(".contract_update .addItem").click(function(){
	addFlag++;
	var sddStr =  '<tr class="PackageTr">'+
		'<td>PACKAGE NO.</td>'+
		'<td><input type="text" name="PackageNO" value="'+addFlag+'" class="PackageUpdate"></td>'+
	'</tr>'+ 
	'<tr class="addSizeTr">'+
		'<td>DIMENSION(CM)</td>'+
		'<td class="Dimension"><input type="text" name="Dimension" value=""></td>'+
		'<td>GROSS WEIGHT(KG)</td>'+
		'<td class="GrossWeight"><input type="text" name="GrossWeight" value=""></td>'+
		'<td>NET WEIGHT(KG)</td>'+
		'<td class="NetWeight"><input type="text" name="NetWeight" value=""></td>'+
		'<td>QUANTITY</td>'+
		'<td class="Quantity"><input type="text" name="Quantity" value=""></td>'+
		'<td><input type="button" name="DelitemThisTr"  class="DelitemThisTr bToggle"  value="删除本行尺寸信息" ></td>'+
	'</tr>';
	$(".contract_update .contract_basic").append(sddStr);
})

//添加里的Model尺寸
$(".contract_add .addModel").click(function(){
	var sddStr1 = '<tr class="ModelTr">'+
		'<td>DESCRIPTION OF GOODS</td>'+
		'<td><input type="text" name="Description" value="" ></td>'+
		'<td>MODEL</td>'+
		'<td class="ModelTd"><input type="text" name="Model" value="" class="Model"></td>'+
		'<td>QTY</td>'+
		'<td><input type="text" name="Qty" value="" style="width:162px;" id="Qty">'+
		'</td>'+
		'<td><input type="button" name="DelgoodsThisTr"  class="DelgoodsThisTr bToggle"  value="删除本行货物信息" ></td>'+
	'</tr>';
	$(".contract_add .contract_basic").append(sddStr1);
})

//修改里的model尺寸
$(".contract_update .addModel").click(function(){
	var sddStr1 = '<tr class="ModelTr">'+
	'<td>DESCRIPTION OF GOODS</td>'+
	'<td><input type="text" name="Description" value=""></td>'+
	'<td>MODEL</td>'+
	'<td class="ModelTd"><input type="text" name="Model" value="" class="ModelUpdate"></td>'+
	'<td>QTY</td>'+
	'<td><input type="text" name="Qty" value="" style="width:162px;" id="Qty1">'+
	'</td>'+
	'<td><input type="button" name="DelgoodsThisTr"  class="DelgoodsThisTr bToggle"  value="删除本行货物信息" ></td>'+
'</tr>';
	$(".contract_update .contract_basic").append(sddStr1);
});

$(document).on("click",".DelgoodsThisTr",function(){
	$(this).parent().parent().remove();
})

$(document).on("click",".DelitemThisTr",function(){
	$(this).parent().parent().remove();
})
$(document).on("click",".DelsizeThisTr",function(){
	$(this).parent().parent().remove();
});

/* 修改箱单页面信息 */
$(document).on("click",".contract-edit",function(){	
	var tr=$(this).parent();
	
    $('.contract_update input[name="Date"]').val(tr.find('td').eq(9).text());
	$('.contract_update input[name="PackingListNO"]').val(tr.find('td').eq(10).text());
	$('.contract_update input[name="Packing"]').val(tr.find('td').eq(20).text());
	$('.contract_update input[name="FromApp"]').val(tr.find('td').eq(11).text());
	$('.contract_update input[name="FromAdd"]').val(tr.find('td').eq(12).text());
	$('.contract_update input[name="FromTel"]').val(tr.find('td').eq(13).text());
	$('.contract_update input[name="ToApp"]').val(tr.find('td').eq(14).text());
	$('.contract_update input[name="ToAdd"]').val(tr.find('td').eq(15).text());
	$('.contract_update input[name="ToAtt"]').val(tr.find('td').eq(16).text());
	$('.contract_update input[name="ToTel"]').val(tr.find('td').eq(17).text());
	$('.contract_update input[name="contract_no"]').val(tr.find('td').eq(18).text());
	$('.contract_update input[name="PONO"]').val(tr.find('td').eq(23).text());
	$('.contract_update input[name="DC_NO"]').val(tr.find('td').eq(22).text());
	$('.contract_update input[name="ShipVia"]').val(tr.find('td').eq(21).text());
	$('.contract_update input[name="Sender"]').val(tr.find('td').eq(28).text());
	$('.contract_update input[name="Recepter"]').val(tr.find('td').eq(29).text());
 
	$('.contract_update input[name="Origin"]').val(tr.find('td').eq(25).text());
	$('.contract_update input[name="ShippingMarkNo"]').val(tr.find('td').eq(26).text().split("&&")[0]);
	$('.contract_update input[name="ShippingMarkAdd"]').val(tr.find('td').eq(26).text().split("&&")[1]);
	$('.contract_update input[name="PackingCondition"]').val(tr.find('td').eq(27).text());
	$('.contract_update input[name="ToContact"]').val(tr.find('td').eq(36).text());
	
	var  ID = tr.find('td').eq(0).attr("value");
	
	var ContractNO = tr.find('td').eq(18).text();
	
	$(".contract_update .contract_title").attr("value",ID);  //在修改页面保存当前行的ID信息
	
	$.ajax({
        type : 'get',
        url : 'GetPackingOther',
        data : {
        	ID : ID,
        },
        dataType : 'json',
        success : function (data) {
        	$(".contract_update .addItemTr").remove();
        	$(".contract_update .PackageTr").remove();
        	$(".contract_update .addSizeTr").remove();
        	$(".contract_update .ModelTr").remove();
        	
        	 var data=data[0];
        	 
        	 /* 尺寸 */
        	 for(var i = 1; i < data.size.length ; i++){
        			
     			   var sddStr =  '<tr class="PackageTr" >'+
            			'<td>PACKAGE NO.</td>'+
            			'<td><input type="text" name="PackageNO" value="'+i+'" class="PackageUpdate"></td>'+
            		'</tr>'+ 
            		'<tr class="addSizeTr"  value="'+data.size[i].SizeID+'">'+
            			'<td>DIMENSION(CM)</td>'+
            			'<td class="Dimension"><input type="text" name="Dimension" value="'+data.size[i].Dimension+'"></td>'+
            			'<td>GROSS WEIGHT(KG)</td>'+
            			'<td class="GrossWeight"><input type="text" name="GrossWeight" value="'+data.size[i].GrossWeight+'"></td>'+
            			'<td>NET WEIGHT(KG)</td>'+
            			'<td class="NetWeight"><input type="text" name="NetWeight" value="'+data.size[i].NetWeight+'"></td>'+
            			'<td>QUANTITY</td>'+
            			'<td class="Quantity"><input type="text" name="Quantity" value="'+data.size[i].Quantity+'"></td>'+
            			'<td><input type="button" name="DelsizeThisTr"  class="DelsizeThisTr bToggle"  value="删除本行尺寸信息" ></td>'+
            		'</tr>';
				$(".contract_update .contract_basic").append(sddStr); 
        	 }
        	 
        	 /* 配置 */
			 for(var i = 1; i < data.item.length ; i++){
				 var sddStr1 = '<tr class="addItemTr" value="'+data.item[i].itemID+'">'+
                 '<td>DESCRIPTION OF GOODS</td>'+
				'<td><input type="text" name="Description" value="'+data.item[i].itemDescription+'"></td>'+
				'<td>MODEL</td>'+
				'<td class="ModelTd"><input type="text" name="Model" value="'+data.item[i].Goods+'" class="ModelUpdate"></td>'+
				'<td>QTY</td>'+
				'<td><input type="text" name="Qty" value="'+data.item[i].Quantity+'" style="width:162px;" id="Qty1">'+
				'</td>'+
				'<td><input type="button" name="DelitemThisTr"  class="DelitemThisTr bToggle"  value="删除本行配置信息" ></td>'+
				'</tr>';
				$(".contract_update .addItemCont").after(sddStr1);
        	 }
        	 /*尺寸 ----  货物  */
			for(var i = 1; i < data.goods.length ; i++){
				var sddStr1 = '<tr class="ModelTr" value="'+data.goods[i].goodsID+'">'+
                    '<td>DESCRIPTION OF GOODS</td>'+
					'<td><input type="text" name="Description" value="'+data.goods[i].goodsDescription+'"></td>'+
					'<td>MODEL</td>'+
					'<td class="ModelTd"><input type="text" name="Model" value="'+data.goods[i].Model+'" class="ModelUpdate"></td>'+
					'<td>QTY</td>'+
					'<td><input type="text" name="Qty" value="'+data.goods[i].Qty+'" style="width:162px;" id="Qty1">'+
					'</td>'+
					'<td><input type="button" name="DelgoodsThisTr"  class="DelgoodsThisTr bToggle"  value="删除本行货物信息" ></td>'+
				'</tr>';
				$(".contract_update .contract_basic").append(sddStr1);
        	 }
        },
        error : function () {
            $.MsgBox.Alert("提示", "获取尺寸信息错误！");
        }
    }); 

   $('.MailBar_cover_color, .contract_update').show();
 });
 
   	
	/*  提交修改后的信息  */
	$('#update_submit').click(function () {
		var Date=$('.contract_update input[name="Date"]').val() =="" ? "0000-00-00":$('.contract_update input[name="Date"]').val();
	    var PackingListNO=$('.contract_update input[name="PackingListNO"]').val();
	    var FromAPP=$('.contract_update input[name="FromApp"]').val();
	    var FromADD=$('.contract_update input[name="FromAdd"]').val();
	    var FromTel=$('.contract_update input[name="FromTel"]').val();
	    var ToAPP=$('.contract_update input[name="ToApp"]').val();
	    var ToADD=$('.contract_update input[name="ToAdd"]').val();
	    var ToATT=$('.contract_update input[name="ToAtt"]').val() == "" ? "NA":$('.contract_update input[name="ToAtt"]').val();
	    var ToTel=$('.contract_update input[name="ToTel"]').val();
	    var ContractNO=$('.contract_update input[name="contract_no"]').val();
	    var PONOAll=$('.contract_update input[name="PONO"]').val() == "" ? "NA":$('.contract_update input[name="PONO"]').val();
	    var Packing=$('.contract_update input[name="Packing"]').val();
	    var ShipVia=$('.contract_update input[name="ShipVia"]').val();
	    var DCNO=$('.contract_update input[name="DC_NO"]').val();
	    var Origin=$('.contract_update input[name="Origin"]').val()== "" ? "NA":$('.contract_update input[name="Origin"]').val();
	    var ShippingMark = $('.contract_update input[name="ShippingMarkNo"]').val() +"&&"+$('.contract_update input[name="ShippingMarkAdd"]').val();
	    var PackingCondition=$('.contract_update input[name="PackingCondition"]').val() == "" ? "NA":$('.contract_update input[name="PackingCondition"]').val();
	    var Sender=$('.contract_update input[name="Sender"]').val() == "" ? "NA":$('.contract_update input[name="Sender"]').val();
	    var Recepter=$('.contract_update input[name="Recepter"]').val() == ""? "NA" : $('.contract_update input[name="Recepter"]').val();
	    var ToContact=$('.contract_update input[name="ToContact"]').val();
	    console.log(PackingCondition);
	    
	    /* 配置信息 */
	    var isExistItem;
	    var Goods = [];
	    var ItemDescription = [];
	    var ItemQuantity = [];
	    var itemID = [];
	    
	    if($(".contract_update .addItemTr").length != 0){
	    	isExistItem = "yes";
			for(var i = 0 ; i < $(".contract_update .addItemTr").length ; i++){
				Goods.push($(".contract_update .addItemTr").eq(i).find('input[name="Model"]').val());
				ItemDescription.push($(".contract_update .addItemTr").eq(i).find('input[name="Description"]').val());
				ItemQuantity.push($(".contract_update .addItemTr").eq(i).find('input[name="Qty"]').val());
				
				if($(".contract_update .addItemTr").eq(i).attr("value")){
					itemID.push($(".contract_update .addItemTr").eq(i).attr("value"));
				}
				else{
					itemID.push(0);
				}
			}
		}
		else{
			isExistItem = "no"
		}
	    
	    /* 尺寸信息 */
	    var isExistSize;
	    var Dimension = [];
	    var GrossWeight = [];
	    var NetWeight = [];
	    var Quantity = [];
	    var sizeID = [];
	    var TotalGrossWeight = 0;
	    var TotalNetWeight = 0;
	    
	    if($(".contract_update .addSizeTr").length != 0){
	    	isExistSize = "yes";
			for(var i = 0 ; i < $(".contract_update .addSizeTr").length ; i++){
				Dimension.push($(".contract_update .addSizeTr").eq(i).find('input[name="Dimension"]').val());
				GrossWeight.push($(".contract_update .addSizeTr").eq(i).find('input[name="GrossWeight"]').val());
				NetWeight.push($(".contract_update .addSizeTr").eq(i).find('input[name="NetWeight"]').val());
				Quantity.push($(".contract_update .addSizeTr").eq(i).find('input[name="Quantity"]').val());
				
				TotalGrossWeight += $(".contract_update .addSizeTr").eq(i).find('input[name="GrossWeight"]').val() * $(".contract_update .addSizeTr").eq(i).find('input[name="Quantity"]').val();
		    	TotalNetWeight += $(".contract_update .addSizeTr").eq(i).find('input[name="NetWeight"]').val() * $(".contract_update .addSizeTr").eq(i).find('input[name="Quantity"]').val();
				
				if($(".contract_update .addSizeTr").eq(i).attr("value")){
					sizeID.push($(".contract_update .addSizeTr").eq(i).attr("value"));
				}
				else{
					sizeID.push(0);
				}
			}
			console.log('size:'+sizeID);
		}
		else{
			isExistSize = "no"
		}
	    
	    /*尺寸-- 货物---内容  */
	     var isExistGoods;
	    var goodsModel = [];
	    var Description = [];
	    var Qty = [];
	    var goodsID = []; 	
	    if($(".contract_update .ModelTr").length != 0){
	    	isExistGoods = "yes";
			for(var i = 0 ; i < $(".contract_update .ModelTr").length ; i++){
				Description.push($(".contract_update .ModelTr").eq(i).find('input[name="Description"]').val());
				goodsModel.push($(".contract_update .ModelTr").eq(i).find('input[name="Model"]').val());
				Qty.push($(".contract_update .ModelTr").eq(i).find('input[name="Qty"]').val());
				
				if($(".contract_update .ModelTr").eq(i).attr("value")){
					goodsID.push($(".contract_update .ModelTr").eq(i).attr("value"));
				}
				else{
					goodsID.push(0);
				}
			}
		}
		else{
			isExistGoods = "no"
		}
	    var PONO;
	    if(PONOAll.indexOf(",")>0){
	    	PONO = PONOAll.split(",")[0]; 
	    }
	    else{
	    	PONO = PONOAll; 
	    }
	    
	    var PONOAll=$(".contract_update").eq(0).find('input[name="PONO"]').val().split(",")[0]; 
	    
	   var Model=$(".contract_update .ModelTr").eq(0).find('input[name="Model"]').val();

	   var  ID = $(".contract_update .contract_title").attr("value");
		
		    $.ajax({
			      type : 'get',
			      url : 'ModifyPackingList',
			      data : {
			    	   ID:ID,
			    	     Model : Model,
			    	     Date : Date,
		    		  PackingListNO : PackingListNO,
		    		  FromAPP : FromAPP, 
		    		   FromADD : FromADD,
			           FromTel : FromTel,
			           ToAPP : ToAPP,
		            ToADD : ToADD,
		            ToATT : ToATT,
		            ToTel : ToTel,
		            ContractNO : ContractNO,
		            PONO : PONO,
		            PONOAll : PONOAll,
		            Packing : Packing,
		            ShipVia : ShipVia,
		            DCNO : DCNO,
		            TotalGrossWeight : TotalGrossWeight,
		            TotalNetWeight : TotalNetWeight,
		            Origin : Origin,
		            ShippingMark : ShippingMark,
		            PackingCondition : PackingCondition,
		            Sender : Sender,
		            Recepter : Recepter,
		            isExistItem : isExistItem,
		            isExistSize : isExistSize,
		            isExistGoods : isExistGoods,
		            itemID:itemID,
		            Goods :Goods,
		            ItemDescription : ItemDescription,
		            ItemQuantity : ItemQuantity,
		            Dimension :Dimension,
		            GrossWeight : GrossWeight, 
		            NetWeight : NetWeight,
		            Quantity : Quantity,
		            sizeID : sizeID,
		            goodsModel : goodsModel,
		            Description : Description,
		            Qty : Qty,
		            goodsID : goodsID, 
		            ToContact : ToContact
			      },
			      dataType : 'json',
			      success : function (data) {
			          $.MsgBox.Alert('提示','修改成功');
			          $('.MailBar_cover_color').hide();
			          $('.contract_add').hide();
			      },
			      error : function () {
			    	  $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
			      }
			  }); 
		});    
    


//--------------------------------------------模板预览页面-----------------------------------------------
$('.contract-show').click(function () {
	$('.MailBar_cover_color').show();
	  var ID = $(this).parent().parent().find("td").eq(0).attr("value");
		$(".yejiao").attr("value",ID);
	if($(this).val() =="配置模板"){
		$(".configTr").remove();
		$("#table_PackSize").hide();
		$("#table_Config").show(); 
		$(".logo").attr("value","配置模板");
		var ContractNO = $(this).parent().parent().find("td").eq(4).text();
		console.log(ID);
		 	$.ajax({
		        type : 'get',
		        url : 'GetPackingItem',
		        data : {
		        	ID:ID,
		        	ContractNO:ContractNO
		        },
		        dataType : 'json',
		        success : function (data) {
		        	console.log(data)
		        	  var itemStr ="";
		        	/*  for(var i = 1 ; i <data.length; i++ ){ */
		        		 for(var i = 1 ; i <data.length; i++ ){
		        		 itemStr +='<tr class="configTr" value="'+data[i].ID+'">'+
		                    ' <td  class="Item PackageNO">'+i+'</td>'+
		                     '<td colspan="2" contenteditable="true" class="Goods">'+data[i].Goods+'</td>'+
		                     '<td colspan="3" contenteditable="true" class="ItemDescription">'+data[i].itemDescription+'</td>'+
		                     '<td contenteditable="true" class="Quantity">'+data[i].Quantity+'</td>'+
		                 '</tr>';
		        	} 
		        		 
		        	 $('.configItem').append(itemStr);
		        },
		        error : function () {
		        	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
		        }
		    });  
	}
	else{
		$(".sizeTr").remove();
		$(".addModel1").remove();
		$("#table_PackSize").show();
		$("#table_Config").hide();
		$(".logo").attr("value","尺寸模板");
		 var thisList = $(this).parent().parent().children();
		 var str = "";
		 $("#table_PackSize span.Date").text(thisList.eq(9).text());
	 	$.ajax({
		        type : 'get',
		        url : 'GetGoodsSizeById',
		        data : {
		        	ID:ID,
		        },
		        dataType : 'json',
		        success : function (data) {
		        	console.log(data)
		        	var itemStr ="";
	        			 for(var i = 1 ; i <data[0].size.length; i++ ){
		        		 itemStr +='<tr class="sizeTr" value="'+data[0].size[i].SizeID+'">'+
		                      '<td  class="PackageNO">'+i+'</td>'+ 
		                     /* '<td contenteditable="true" class="PackageNO">'+data[0].size[i].ID+'</td>'+ */
		                     '<td contenteditable="true" class="Dimension" colspan="2">'+data[0].size[i].Dimension+'</td>'+
		                     '<td contenteditable="true" class="Gross ConfigBlur">'+data[0].size[i].GrossWeight+'</td>'+
		                     '<td contenteditable="true" class="Net ConfigBlur">'+data[0].size[i].NetWeight+'</td>'+
		                     '<td contenteditable="true" class="Quantity ConfigBlur">'+data[0].size[i].Quantity+'</td>'+
		                 '</tr>';
		        		} 
	        			 // console.log("1"+itemStr);
	        		 var modelStr ="";
		        		 for(var i = 1 ; i <data[0].goods.length; i++ ){
		        		 modelStr +='<div class="addModel1"  value="'+data[0].goods[i].goodsID+'"><p contenteditable="true" class="Description">'+data[0].goods[i].goodsDescription+'</p>'+
		                     'MODEL:<span contenteditable="true" class="Model">'+data[0].goods[i].Model+'</span><br>'+
		                     'QTY:<span contenteditable="true" class="Qty">'+data[0].goods[i].Qty+'</span><br></div>';
		        		} 
		        		 // console.log("2"+modelStr);
		        	 $('.GOODSTitle').after(modelStr);
		        	 $('.itemTol').append(itemStr);  
		        },
		        error : function () {
		        	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
		        }
		    });  
	}
	
    var thisList = $(this).parent().parent().children('td');
    $(".news p.Date").text("").text(thisList.eq(9).text());
    var dateArr = thisList.eq(9).text().split("-");
    $("#table_Config span.YMR").text(dateArr[0]+"年"+dateArr[1]+"月"+dateArr[2]+"日");
    $(".news .PackingListNO").text("").text(thisList.eq(10).text());
    $(".news .FromApp").text("").text(thisList.eq(11).text());
    $(".news .FromAdd").text(thisList.eq(12).text());
    $(".news .ToApp").text(thisList.eq(14).text());
    $(".news .ToAdd").text(thisList.eq(15).text());
    $(".news .ContractNO").text("").text(thisList.eq(18).text());
    $(".news .PoNO").text("").text($(this).parent().parent().find('.PONO').text());
    $(".news .Packing").text("").text(thisList.eq(20).text());//f
    $(".news .ShipVia").text("").text(thisList.eq(21).text());//
    $(".news .DCNO").text("").text(thisList.eq(22).text());
    $(".news .PONOAll").text("").text(thisList.eq(23).text()); 
    $(".news .Description").text("").text(thisList.eq(23).text());
    $(".news .Model").text("").text(thisList.eq(24).text());
    $(".news .Origin").text("").text(thisList.eq(25).text());

    $(".news .ShippingMark").text("").text(thisList.eq(26).text().split("&&")[0]);
    $(".news .MarkAdd").text("").text(thisList.eq(26).text().split("&&")[1]);
    $(".news .PackingCondition").text("").text(thisList.eq(27).text());
    $(".news .Sender").text("").text(thisList.eq(28).text());//
    $(".news .Recepter").text("").text(thisList.eq(29).text());//
 
    $(".news .TotalGrossWeight").text("").text(thisList.eq(33).text());
    $(".news .TotalNetW").text("").text(thisList.eq(34).text());
    $(".news .ToContact").text("").text(thisList.eq(36).text());
   
  //公章显示问题：
  console.log("HK"+$('.hidePdf .FromApp').eq(0).text());
    var FromApp=$('.hidePdf .FromApp').eq(0).text();
    if(FromApp=="苏州伊欧陆系统集成有限公司"){
    	$(".hidePdf").find('.InImg').attr('src','image/zhangSuZhou.png');  
    }else{
    	$(".hidePdf").find('.InImg').attr('src','image/zhang2.png'); 
    }

    
    //判断FromAFax是否存在
    console.log("testfron:"+thisList.eq(13).text().split("&&").length);
    if(thisList.eq(13).text().split("&&").length == 1){
    	console.log("1")
    	 $(".FromATel").show();
		 $(".news .FromATel").text(thisList.eq(13).text().split("&&")[0].replace("TEL:",""));
    }else if(thisList.eq(13).text().split("&&").length == 2){
    	console.log("2")
    	 $(".FromATel").show();
		 $(".news .FromATel").text(thisList.eq(13).text().split("&&")[0].replace("TEL:",""));
		 $(".news .FromAFax").text(thisList.eq(13).text().split("&&")[1].replace("FAX:",""));//
    }else{
    	console.log("3")
    	$(".FromATel").hide();
    }
    
	//判断ToFAX是否存在
	console.log("testto:"+thisList.eq(17).text().split("&&").length);
    if(thisList.eq(17).text().split("&&").length == 1){
    	console.log("1")
   	 	 $(".ToTEL").show();
		 $(".news .ToTEL").text(thisList.eq(17).text().split("&&")[0].replace("TEL:",""));
   }else if(thisList.eq(17).text().split("&&").length == 2){
	   console.log("2")
   	 $(".FromATel").show();
		 $(".news .ToTEL").text(thisList.eq(17).text().split("&&")[0].replace("TEL:",""));
		 $(".news .ToFAX").text(thisList.eq(17).text().split("&&")[1].replace("FAX:",""));
   }else{
	   console.log("3")
   		$(".ToTEL").hide();
   }
   
    //pono是否存在
    // if($(this).parent().parent().find('.PONO').text() =="NA"|| $(this).parent().parent().find('.PONO').text() ==""||$(this).parent().parent().find('.PONO').text() ==" "){
    // 	$(".news .PoNO").hide();
    // 	$(".Co_PONO").text("CONTRACT NO.")
    // }
    // else{
    // 	$(".news .PoNO").show();
    // 	$(".news .PoNO").text($(this).parent().parent().find('.PONO').text());
    // 	$(".Co_PONO").text("CONTRACT /PO NO.")
    // }
    if($(this).parent().parent().find('.PONOAll').text() =="NA"|| $(this).parent().parent().find('.PONOAll').text() ==""||$(this).parent().parent().find('.PONOAll').text() ==" "){
        	$(".news .PoNO").hide();
        	$(".Co_PONO").text("CONTRACT NO.");
        	// 12345
        	$(".news .PoNO").text("NA");
    }else{
    	$(".news .PoNO").show();
    	$(".news .PoNO").text($(this).parent().parent().find('.PONOAll').text());
    	$(".Co_PONO").text("CONTRACT /PO NO.")
    }
    
    //ATT是否存在
	 if(thisList.eq(16).text() =="NA"){
	 	$(".ToATT").hide();
	 }
	 else{
	 	$(".ToATT").show();
	 	$(".news .ToATT").text(thisList.eq(16).text());
	 }
    $('.hidePdf').show();
    $(".MarkAdd").css("width",$(".ShippingMark").width()) 
});

$('#contract_close1').click(function () {
    $('.MailBar_cover_color').hide();
    $('.hidePdf').hide();
});


/*********************添加箱单信息************************/
/* 添加箱单信息 */
	$(document).on("click","#add_submit",function(){
		var Date=$('.contract_add input[name="Date"]').val() =="" ? "0000-00-00":$('.contract_add input[name="Date"]').val();
	    var PackingListNO=$('.contract_add input[name="PackingListNO"]').val();
	    var FromAPP=$('.contract_add input[name="FromApp"]').val();
	    var FromADD=$('.contract_add input[name="FromAdd"]').val();
	    var FromTel=$('.contract_add input[name="FromTel"]').val();
	    var ToAPP=$('.contract_add input[name="ToApp"]').val();
	    var ToADD=$('.contract_add input[name="ToAdd"]').val();
	    var ToATT=$('.contract_add input[name="ToAtt"]').val() == "" ? "NA":$('.contract_add input[name="ToAtt"]').val();
	    var ToTel=$('.contract_add input[name="ToTel"]').val();
	    var ContractNO=$('.contract_add input[name="contract_no"]').val();
	    var PONO=$('.contract_add input[name="PONO"]').val() == "" ? "NA":$('.contract_add input[name="PONO"]').val();
	    var Packing=$('.contract_add input[name="Packing"]').val();
	    var ShipVia=$('.contract_add input[name="ShipVia"]').val();
	    var DCNO=$('.contract_add input[name="DC_NO"]').val();ToContact
	    var Origin=$('.contract_add input[name="Origin"]').val()== "" ? "NA":$('.contract_add input[name="Origin"]').val();
	    var ShippingMark = $('.contract_add input[name="ShippingMarkNo"]').val() +"&&"+$('.contract_add input[name="ShippingMarkAdd"]').val();
	    var PackingCondtion=$('.contract_add input[name="PackingCondition"]').val() == "" ? "NA":$('.contract_add input[name="PackingCondition"]').val();
	    var Sender=$('.contract_add input[name="Sender"]').val() == "" ? "NA":$('.contract_add input[name="Sender"]').val();
	    var Recepter=$('.contract_add input[name="Recepter"]').val() == ""? "NA" : $('.contract_add input[name="Recepter"]').val();
	    var ToContact=$('.contract_add input[name="ToContact"]').val();
	   
	    /* 尺寸信息 */
	    var goodsExist;
	    var Dimension = [];
	    var GrossWeight = [];
	    var NetWeight = [];
	    var Quantity = [];
	    var goodsModel = [];
	    var Description = [];
	    var Qty = [];
	    var id=[];
	    
	    var PONOAll=$(".contract_add").eq(0).find('input[name="PONO"]').val().split(",")[0]; 
	    if($(".contract_add .ModelTr").length != 0){
			goodsExist = "yes";
			for(var i = 0 ; i < $(".contract_add .ModelTr").length ; i++){
				Description.push($(".contract_add .ModelTr").eq(i).find('input[name="Description"]').val());
				goodsModel.push($(".contract_add .ModelTr").eq(i).find('input[name="Model"]').val());
				Qty.push($(".contract_add .ModelTr").eq(i).find('input[name="Qty"]').val());
			}
			var Model=$(".contract_add .ModelTr").eq(0).find('input[name="Model"]').val().split(",")[0];
		}
		else{
			goodsExist = "no"
		}
	    
	    var TotalGrossWeight = 0;
	    var TotalNetWeight = 0;
	    if(addFlag > 0){             //有尺寸信息
	    	for(var i = 0 ; i < $(".contract_add .Dimension").length; i++){
		    	var Dimensionstr = $(".contract_add .Dimension").eq(i).find('input').val();
		    	var GrossWeightstr = $(".contract_add .GrossWeight").eq(i).find('input').val();
		    	var Quantitystr = $(".contract_add .Quantity").eq(i).find('input').val();
		    	var NetWeightstr = $(".contract_add .NetWeight").eq(i).find('input').val();
		    	var idstr = $(".contract_add .Package").eq(i).val();
		    	Dimension.push(Dimensionstr);
		    	GrossWeight.push(GrossWeightstr); 
		    	Quantity.push(Quantitystr); 
		    	NetWeight.push(NetWeightstr); 
		    	id.push(idstr);
		    	
		    	TotalGrossWeight += GrossWeightstr * Quantitystr;
		    	TotalNetWeight += NetWeightstr * Quantitystr;
		    }
	    }
		var isExistSize ;
		var isNull ;

		if(id.length == "0"){
			isExistSize = "no"
		}
		else{
			isExistSize = "yes"
		}
		
		if( Qty=="" || Description == "" || goodsModel == ""){
			alert("请检查是否填写货物信息");
			return;
		}
	   $.ajax({
	        // type : 'get',
	        type : 'POST',
	        url : 'PackingListAdd',
	        data : {
	        	Model:Model,
	        	Date : Date,
	        	PackingListNO : PackingListNO,
	        	FromAPP : FromAPP,
	        	FromADD : FromADD,
	        	FromTel : FromTel,
	            ToAPP : ToAPP,
	            ToADD : ToADD,
	            ToATT : ToATT,
	            ToTel : ToTel,
	            ContractNO : ContractNO,
	            PONO : PONOAll,
	            PONOAll : PONO,
	            Packing : Packing,
	            ShipVia : ShipVia,
	            DCNO : DCNO,
	            TotalGrossWeight : TotalGrossWeight,
	            TotalNetWeight : TotalNetWeight,
	            Origin : Origin,
	            Qty : Qty,
	            ShippingMark : ShippingMark,
	            PackingCondition : PackingCondtion,
	            Sender : Sender,
	            Recepter : Recepter,
	            isExistSize : isExistSize,
	            isExistGoods : goodsExist,
	            Description : Description,
	            goodsModel : goodsModel,
	            Origin : Origin,
	            id:id,
	            Dimension :Dimension,
	            GrossWeight : GrossWeight,
	            NetWeight : NetWeight,
	            Quantity : Quantity,
	            ToContact : ToContact
	          
	        },
	        dataType: 'json',
	        success: function (data) {
	            $.MsgBox.Alert('提示','添加成功');
	            $('.MailBar_cover_color').hide();
	            $('.contract_add').hide();
	        },
	        error : function () {
	        	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	        },
	        beforeSend: function(XMLHttpRequest){
                $("#add_submit").css("cursor","not-allowed").prop("disabled","disabled");
            },
            complete: function(XMLHttpRequest, textStatus){
                $("#add_submit").css("cursor","pointer").prop("disabled",false);
            }
	    });
    });
		
		/* ************删除***************** */
 $(document).on("click",".DelgoodsThisTr",function(){
	  var goodsID = $(this).parent().parent().attr("value"); 
	$(this).parent().parent().remove();
	console.log(goodsID)
	$.ajax({
        type : 'get',
        url : 'DeletePackingGoods',
        data : {
        	goodsID : goodsID
        },
        dataType : 'json',
        success : function (data) {
        	$.MsgBox_Unload.Alert("提示", "删除成功！");
        },
        error : function () {
        	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});

$(document).on("click"," .DelitemThisTr",function(){
	 var itemID = $(this).parent().parent().attr("value");  
	$(this).parent().parent().remove();
	console.log(itemID)
	$.ajax({
        type : 'get',
        url : 'DeletePackingItem',
        data : {
        	itemID : itemID
        },
        dataType : 'json',
        success : function (data) {
        	$.MsgBox_Unload.Alert("提示", "删除成功！");
        },
        error : function () {
        	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});

$(document).on("click"," .DelsizeThisTr",function(){
	var sizeID = $(this).parent().parent().attr("value"); 
	$(this).parent().parent().remove();
	console.log(sizeID)
	$.ajax({
        type : 'get',
        url : 'DeletePackingSize',
        data : {
        	sizeID : sizeID
        },
        dataType : 'json',
        success : function (data) {
        	$.MsgBox_Unload.Alert("提示", "删除成功！");
        },
        error : function () {
        	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});

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

//点击取消
$('#add_cancel').click(function () {
    $('.MailBar_cover_color').hide();
    $('.contract_add').hide();
});

//Applicant联动
$(".contract_add .FromAppSelect").change(function(){
	var ApplicantVal = $(this).find("option:selected").text();
	$(".contract_add #FromApp").val(ApplicantVal);
	var AppIndex = $(".FromAppSelect").find("option:selected").prop('index');
	$(".contract_add .hideFromTD").show();
	$(".contract_add .FromTelSelect").find("option").eq(AppIndex).attr("selected",true).siblings().attr("selected",false);
	$(".contract_add .FromAddSelect").find("option").eq(AppIndex).attr("selected",true).siblings().attr("selected",false);
	$(".contract_add #FromTel").val($(".contract_add .FromTelSelect").find("option:selected").text());
	$(".contract_add #FromAdd").val($(".contract_add .FromAddSelect").find("option:selected").text());
});
$(".contract_add .ToAppSelect").change(function(){
	var ApplicantVal = $(this).find("option:selected").text();
	$(".contract_add #ToApp").val(ApplicantVal);
	var AppIndex = $(".contract_add .ToAppSelect").find("option:selected").prop('index');
	$(".contract_add .hideToTD").show();
	$(".contract_add .ToTelSelect").find("option").eq(AppIndex).attr("selected",true).siblings().attr("selected",false);
	$(".contract_add .ToAddSelect").find("option").eq(AppIndex).attr("selected",true).siblings().attr("selected",false);
	$(".contract_add #ToTel").val($(".contract_add .ToTelSelect").find("option:selected").text());
	$(".contract_add #ToAdd").val($(".contract_add .ToAddSelect").find("option:selected").text());
});

//Applicant联动，修改部分的联动
$(".contract_update .FromAppSelect").change(function(){
	var ApplicantVal = $(this).find("option:selected").text();
	$(".contract_update #FromApp1").val(ApplicantVal);
	var AppIndex = $(".contract_update .FromAppSelect").find("option:selected").prop('index');
	$(".contract_update .hideFromTD").show();
	$(".contract_update .FromTelSelect").find("option").eq(AppIndex).attr("selected",true).siblings().attr("selected",false);
	$(".contract_update .FromAddSelect").find("option").eq(AppIndex).attr("selected",true).siblings().attr("selected",false);
	$(".contract_update #FromTel1").val($(".contract_update .FromTelSelect").find("option:selected").text());
	$(".contract_update #FromAdd1").val($(".contract_update .FromAddSelect").find("option:selected").text());
});
$(".contract_update .ToAppSelect").change(function(){
	var ApplicantVal = $(this).find("option:selected").text();
	$(".contract_update #ToApp1").val(ApplicantVal);
	var AppIndex = $(".contract_update .ToAppSelect").find("option:selected").prop('index');
	$(".contract_update .hideToTD").show();
	$(".contract_update .ToTelSelect").find("option").eq(AppIndex).attr("selected",true).siblings().attr("selected",false);
	$(".contract_update .ToAddSelect").find("option").eq(AppIndex).attr("selected",true).siblings().attr("selected",false);
	$(".contract_update #ToTel1").val($(".contract_update .ToTelSelect").find("option:selected").text());
	$(".contract_update #ToAdd1").val($(".contract_update .ToAddSelect").find("option:selected").text());
});

$(document).on("blur",".ConfigBlur",function(){
	var TOTALGROSS = 0;
	var TOTALNET = 0; 
	for(var i = 0 ; i <  $(".itemTol .sizeTr").length; i++){
		var GrossText = $(".itemTol .sizeTr").eq(i).find(".Gross").text();
		var NetText = $(".itemTol .sizeTr").eq(i).find(".Net").text();
		var QuantityText = $(".itemTol .sizeTr").eq(i).find(".Quantity").text();
		TOTALGROSS = (TOTALGROSS + GrossText * QuantityText);
		TOTALNET = (TOTALNET + NetText * QuantityText);
	}
	$(".TotalGrossWeight").text("").text(TOTALGROSS);
	$(".TotalNetW").text("").text(TOTALNET);
});


/****************** 跳页 **********************/
function FistPage(arg) {
	if(arg.split('?')[0]=='GetPackingListRoute'){
		$('#search').val('search');
		$("#top_text_from").attr("action", arg + "1");
		$('#top_text_from').submit();
	}else{ 
		window.location.href = arg + "1";
	} 
}
function UpPage(arg) {
	if(arg.split('?')[0]=='GetPackingListRoute'){
		$('#search').val('search');
		$("#top_text_from").attr("action", arg);
		$('#top_text_from').submit();
	}else{ 
		window.location.href = arg;
	} 
}
function NextPage(arg) {
	if(arg.split('?')[0]=='GetPackingListRoute'){
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
	if(arg.split('?')[0]=='GetPackingListRoute'){
		 $('#search').val('search');
		 $("#top_text_from").attr("action", arg + jumpNumber);
		  $('#top_text_from').submit();
	}else{ 
		
		window.location.href = arg + jumpNumber;
	 } 
}
function LastPage(arg) {
	var jumpNumber = parseInt($('#allPage').html());
	if(arg.split('?')[0]=='GetPackingListRoute'){
		 $('#search').val('search');
		 $("#top_text_from").attr("action", arg + jumpNumber);
		 $('#top_text_from').submit();
	}else{ 
		window.location.href = arg + jumpNumber;
	} 
}
$(function() {
	pageStyle(Number($('#currentPage').text()),Number($('#allPage').text()));
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

	// popover
	$('#global_table_style [data-toggle="popover"]').popover({
		title: "请操作",
		content: '<fieldset><legend>物流信息</legend>'+
			'<div class="container-fluid"><div class="form-group"><label for="i_mail_LogisticsCompany">物流公司</label><input type="text" class="form-control" id="i_mail_LogisticsCompany" placeholder="物流公司"></div>'+
			'<div class="form-group"><label for="i_mail_TrackingNO">运单号</label><input type="text" class="form-control" id="i_mail_TrackingNO" placeholder="运单号"></div>'+
			'<div style="text-align:right"><input type="button" class="btn btn-success ReimburseApplication_y" value="邮件发送" style="margin-right:5px;"><input type="button" class="btn btn-warning ReimburseApplication_n" value="关闭"></div>'+
			'</div></fieldset>',
		html: true
	});

});
$(document).on("dblclick","#table_Config .PackageNO",function(){
		$(this).parent().remove();	
		for(var i =0 ; i <$("#table_Config .configItem .configTr").length;i++ ){
			$("#table_Config .configItem .configTr").eq(i).find(".PackageNO").text(i+1);
		}
	})

$(".addItemBtn #addBtn").click(function(){
	var itemLength = $("#table_Config .configItem .configTr").length;
	var itemStr ="";
	 itemStr +='<tr class="configTr" >'+
         ' <td contenteditable="true" class="Item PackageNO" >'+(itemLength+1)+'</td>'+
          '<td colspan="2" contenteditable="true" class="Goods"></td>'+
          '<td colspan="3" contenteditable="true" class="ItemDescription"></td>'+
          '<td contenteditable="true" class="Quantity"></td>'+
       '</tr>';
	 $('.configItem').append(itemStr);
});

</script>
</html>