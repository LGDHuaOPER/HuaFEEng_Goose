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
<title>FORMFACTOR</title>
<style>
.tbody_tr td {
    padding: 0 3px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.clear_float {
	height: 2px;
	width: calc(100% - 2px);
	clear:both;
}

.content {
	padding-bottom: 5px !important;
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

.reset_cookie {
	margin-left: 20px;
	display: inline-block;
    padding: 2px 6px;
    font-size: 14px;
    font-weight: 400;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    cursor: pointer;
    background-color: #d9534f;
    background-image: none;
    border: 1px solid transparent;
    border-radius: 4px;
    border-color: #d43f3a;
	height: 25px;
	line-height: 22px;
	width: 200px;
	color: #fff;
	-webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
}

</style>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css">
<link rel="stylesheet" type="text/css" href="css/global/eouluCustom.css">
<link rel="stylesheet" type="text/css" href="css/OriginFactory.css">
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
					<form id="top_text_from" name="top_text_from" method="post" action="OriginFactorySearch">
						<input type="text" id="shuju" name="" value="${queryType}"
							style="display: none;">
						<div class="select-content">
							<label> <c:choose>
									<c:when test="${queryType=='mix'}">
										<label><input type="radio" id="singleSelect"
											name="queryType" class="singleSelect" value="single"
											onclick="Check(this.value)">单一查询 </label>&nbsp;&nbsp;&nbsp;<label>
											<input type="radio" id="mixSelect" name="queryType"
											value="mix" checked="checked" onclick="Check(this.value)">组合查询
										</label>&nbsp;&nbsp;&nbsp;<br>
									</c:when>
									<c:otherwise>
										<label><input type="radio" id="singleSelect"
											name="queryType" class="singleSelect" value="single"
											checked="checked" onclick="Check(this.value)">单一查询 </label>&nbsp;&nbsp;&nbsp;<label>
											<input type="radio" id="mixSelect" name="queryType"
											value="mix" onclick="Check(this.value)">组合查询
										</label>&nbsp;&nbsp;&nbsp;<br>
									</c:otherwise>
								</c:choose><c:set var="dropdown"
										value="${fn:split('PO,SO,Factory Date,Delay Date',',')}"></c:set>
								<!-- 第一个搜索框 -->		
								<div class="select1">
									<select name="type1" id="type1">
										<c:forEach items="${dropdown }" var="dropdownList1"
											varStatus="status">
											<c:choose>
												<c:when test="${dropdownList1==type1}">
													<option selected="selected">${dropdownList1}</option>
												</c:when>
												<c:otherwise>
													<option>${dropdownList1}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>

									</select>
												<input type="text" id="searchContent1" name="content1" style="display: inline-block;" value="${content1}">
												<input type="date" name="" class="time startTime"  style="margin-left: -5px;">
												<span class="time">-</span>
												<input type="date" name="" class="time endTime">	
								</div>
								<!-- 第二个搜索框 -->
							<c:if test="${queryType !='mix'}">
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
										<input type="text" id="searchContent2" name="content2" value="${content2}">
										<input type="date" name="" class="time startTime" style="margin-left: -5px;">
										<span class="time">-</span>
										<input type="date" name="" class="time endTime" >
								</div>
							</c:if>
							<c:if test="${queryType =='mix'}">
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
									
									<input type="text" id="searchContent2" name="content2" value="${content2}">
									<input type="date" name="" class="time startTime"  style="margin-left: -5px;">
									<span class="time">-</span>
									<input type="date" name="" class="time endTime">
									
								</div>
							</c:if>
								<div class="select-button">
								<input type="text" name="currentPage" id="OriginFactorySearch_curpage" style="display:none" value="1">
									<input type="button" value="搜索" class="bToggle" onclick="selectSearch()"> <input type="button" value="取消" class="bToggle" onclick="selectCancel()">
								</div>
						</div>
					</form>	

				  <div class="m60b"></div> 
					<table border="1" cellspacing="0" cellspadding="0" id="table1" style="margin-top:10px">
						<tr class="table1-th">
							<!-- 0 -->
							<td>序号</td>
							<!-- 1 -->
							<td  style="display:none">修改</td>
							<!-- 2 -->
							<td>PO </td>
							<!-- 3 -->
							<td style="width: 9%;">SO</td>
							<!-- 4 -->
							<td>SOA</td>
							<!-- 5 -->
							<td>BAFA</td>
							<!-- 6 -->
							<td style="width:95px">Target Date</td>
							<!-- 7 -->
							<td style="width:95px">Factory Date</td>
							<!-- 8 -->
							<td>Tracking NO.</td>
							<!-- <td>延迟货期（Delay Date）</td>5 -->
							<!-- 9 -->
							<td style="width:95px;display: none;">Delay Date <a title="显示与隐藏"><i class="fa fa-plus-square"
									id="fa-button1"></i> </a>
							</td>
							<!-- 10 -->
							<td style="display:none">Delay Reason</td>
							<!-- 11 -->
							<td style="width: 100px;display:none">Delay Info</td>
							<!-- 12 -->
							<td style="display:none;">删除数据</td>
							<!-- 13 -->
							<td style="display:none">ShippingInstruction</td>
							<!-- 14 -->
							<td style="display:none">PaymentLC</td>
							<!-- 15 -->
							<td style="display:none">DutyExemption</td>
							<!-- update -->
							<!-- 16 -->
							<td>Invoice No 
							<a title="显示与隐藏"><i class="fa fa-plus-square"
									id="fa-button2"></i> </a>
							</td>
							<!-- 17 -->
							<td style="display:none">Invoice file</td>

							<!-- 18 -->
							<td style="display:none">Payment Date</td>
							<!-- 19 -->
							<c:forEach var="authoritiy" items="${authorities }">
								<c:if test="${authoritiy=='UrgeNotice'}">
									<td>Push Ship Date</td>
									<!-- 20 -->
									<td>Push Tracking</td>
									<!-- 21 -->
									<td>Inform</td>
								</c:if>
							</c:forEach>
						</tr>
						 <c:forEach var="orderInfo" items="${POInfo}" varStatus="status">
							<c:if test="${status.index>0}">
								<tr class="tbody_tr">
									<td value="${orderInfo['ID']}" class="contract-edit" style="cursor:pointer">${status.index+(currentPage-1)*10}</td><!-- 0 --><!-- 0-->
									<td  style="display:none"> <i class="contract-edit" value="${orderInfo['ID']}"></i></td><!-- 1 --><!-- 1-->
									<td class="PO" title="${orderInfo['PO']}">${orderInfo['PO']}</td><!-- 2 --><!-- 2-->
									<td class="SO"  title="${orderInfo['SO']}">${orderInfo['SO']}</td><!-- 3 --><!-- 3-->
									<c:if test="${orderInfo['SOA']=='YES'}">
										<td class="SOA">
										<span class="SOA">${orderInfo['SOA']}</span>
										<span class="down_file down_filesoa"  title="点击预览并下载"></span>
										</td><!-- 新增 --><!-- 4--> 
										
									</c:if>
									<c:if test="${orderInfo['SOA']!='YES'}">
										<td class="SOA">
										<span class="SOA">${orderInfo['SOA']}</span>
										
										</td><!-- 新增 --><!-- 4--> 
										
									</c:if>
									<c:if test="${orderInfo['BAFA']=='YES'}">
									 	<td class="BAFA">
									 	<span class="BAFA">${orderInfo['BAFA']}</span>
									 	<span class="down_file down_filebafa" title="点击预览并下载"></span>
									 	</td><!-- 新增 --><!-- 5--> 
										
									</c:if>
									<c:if test="${orderInfo['BAFA']!='YES'}">
									 	<td class="BAFA">
									 	<span class="BAFA">${orderInfo['BAFA']}</span>
									 	</td><!-- 新增 --><!-- 5--> 
									</c:if>
									<td class="TargetDate">${orderInfo['TargetDate']}</td><!-- 新增 --><!-- 6-->
									<td class="FactoryPeriod">${orderInfo['FactoryPeriod']}</td><!-- 4 --><!-- 7-->
									<td class="TrackingNO"   title="${orderInfo['TrackingNO']}">${orderInfo['TrackingNO']}</td><!-- 新增 --><!-- 8-->
									<td class="DelayPeriod" style="display: none;">${orderInfo['DelayPeriod']}</td><!-- 5 --><!-- 9-->
									<td class="DelayReason" style="display:none"   title="${orderInfo['DelayReason']}">${orderInfo['DelayReason']}</td><!-- 6 --><!-- 10-->
									<td style="width: 100px;display:none">${orderInfo['Type']}</td><!-- 7 --><!-- 11-->
									<td style="display:none;"><i class="fa fa-trash-o del"></i></td><!-- 8 --><!-- 12-->
									<td style="display:none">${orderInfo['ShippingInstruction']}</td><!-- 8 --><!-- 13-->
									<td style="display:none">${orderInfo['PaymentLC']}</td><!-- 8 --><!-- 14-->
									<td style="display:none">${orderInfo['DutyExemption']}</td><!-- 8 --><!-- 15-->
									<!-- update -->
									<!-- 16 -->
									<td class="td_invoiceNo123">${orderInfo['InvoiceNo1']}<br>${orderInfo['InvoiceNo2']}<br>${orderInfo['InvoiceNo3']}</td>
									<!-- 17 -->
									<td style="display: none;">
										<c:choose>
											<c:when test='${orderInfo["InvoiceFile1"] == "" || orderInfo["InvoiceFile1"] == "--"}'><div class="InvoiceFile1_td">无</div></c:when>
											<c:otherwise><div class="InvoiceFile1_td" title="${orderInfo['InvoiceFile1']}">${orderInfo['InvoiceFile1']}</div></c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test='${orderInfo["InvoiceFile2"] == "" || orderInfo["InvoiceFile2"] == "--"}'><div class="InvoiceFile2_td">无</div></c:when>
											<c:otherwise><div class="InvoiceFile2_td" title="${orderInfo['InvoiceFile2']}">${orderInfo['InvoiceFile2']}</div></c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test='${orderInfo["InvoiceFile3"] == "" || orderInfo["InvoiceFile3"] == "--"}'><div class="InvoiceFile3_td">无</div></c:when>
											<c:otherwise><div class="InvoiceFile3_td" title="${orderInfo['InvoiceFile3']}">${orderInfo['InvoiceFile3']}</div></c:otherwise>
										</c:choose>
									</td>
										
									<!-- 18 -->
									<td style="display:none" class="EstimatePaymentDate_td">${orderInfo['EstimatePaymentDate']}</td>
									<!-- 19 -->
									<c:forEach var="authoritiy" items="${authorities }">
										<c:if test="${authoritiy=='UrgeNotice'}">
											<td class="PSD-email-td">${orderInfo["PushShipDate"]}</td>
										<!-- 20 -->
											<td class="PT-email-td">${orderInfo["PushTracking"]}</td>
										<!-- 21 -->
											<td class="Inform" title="${orderInfo['Inform']}">${orderInfo["Inform"]}</td>
										</c:if>
									</c:forEach>
								</tr>
							</c:if>
						</c:forEach>
					</table>	
				
			 	 <%-- <c:choose>
						<c:when test="${queryType == 'common'}">
							<c:set var="queryUrl"
							value="Quality?type1=${classify1 }&searchContent1=${parameter1}&selected=${str}&currentPage=">
							</c:set>
						</c:when>
						<c:otherwise>
							<c:set var="queryUrl"
							value="GetQualityRoute?type1=${classify1 }&searchContent1=${parameter1}&type2=${classify2}&searchContent2=${parameter2}&selected=${str}&currentPage=">
							</c:set>
						</c:otherwise>
					
					</c:choose> --%> 
								
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
								name="lastPage" onclick="LastPage('${queryUrl}${pageCounts}')">
						</div>
					</div>
					<div class="clear_float"></div>
				</div>
			</div>
			<div class="MailBar_cover_color" style="display: none;"></div>
			
			<!-- 添加延迟通知信息 -->
			<div class="contract_send" style="background: white; position: absolute; top: 30%; left: 50%; width: 30%; height: 150px; display: none; border: none; z-index: 1;">
				<div class="contract_title">延迟通知</div>
				<div class="tc f24" style="margin: 20px;">是否确定延迟</div>
				<div class="edit_btn">
					<input type="button" value="确定" class="bToggle" id="send_submit">
					<input type="button" value="取消" class="bToggle" id="send_cancel">
				</div>
			</div>
			
			<!-- 修改原厂页面信息 -->
			<div class="contract_update" style="display:none;">
				<div class="contract_title">修改原厂页面信息</div>
				<div class="contractUpdate_close">关闭</div>
				<div class="basic_info">
					<div class="table_title">原厂页面信息<span style="display: none;"><button title="如果总是提示密码未通过或错误，可点击此按钮后再次输入密码发送" class="reset_cookie">邮箱密码验证恢复初始设置</button></span></div>
					<!-- <span class="idinfo"></span> -->
					<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
						<tbody>	
							<tr>
								<td>PO NO.</td>
								<td><input type="text" name="PO" value="" ></td>
								<td>SO NO.</td>
								<td><input type="text" name="SO" value=""></td>
								<td>Factory Date</td>
								<td><input type="date" name="FactoryPeriod" value=""  id="FactoryPeriod"></td>
							</tr>
							<tr>
								<td>Delay Date</td>
								<td><input type="date" name="DelayPeriod" value="" id="DelayPeriod"></td>
								<td>Delay Reason</td>
								<td><input type="text" name="DelayReason" value="" id="DelayReason"></td>
								<td>SOA</td>
								<!-- <td><input type="text" name="SOA" value="" id="SOA"></td> -->
								<td>
									<select name="SOA" value="" id="SOA">
										  <option value ="YES">YES</option>
										  <option value ="NO">NO</option>
									</select>
									<span class="up_file up_filesoa">上传</span>
									<!-- <span ><input type="file"  class="up_file up_filesoa"></span> -->
									<!-- <span class="down_file down_filesoa">下载</span>	 -->
								</td>
								
							</tr>
							<tr>
								<td style="margin-right:10px">BAFA</td>
								<!-- <td><input type="text" name="BAFA" value="" id="BAFA"></td> -->
								<td>
									<select name="BAFA" value="" id="BAFA">
										  <option value ="YES">YES</option>
										  <option value ="NO">NO</option>
									</select>
									<span class="up_file up_filebafa">上传</span>
									<!-- <span class="upfile_par"><input type="file"  class="up_file up_filebafa"></span> -->
									<!-- <span class="down_file down_filebafa">下载</span> -->		
								</td>
								<td>Target Date</td>
								<td><input type="date" name="TargetDate" value="" id="TargetDate"></td>
								<td>Tracking NO.</td>
								<td><input type="text" value="" id="TrackingNO" style="background:#fff"></td>
							</tr>
							<tr>
								<td>Payment/LC</td>
								<!-- <td><input type="text" name="PaymentLc" value="" id="PaymentLc"></td> -->
								<td>
									<select name="PaymentLC" value="" id="PaymentLC">
										  <option value ="YES">YES</option>
										  <option value ="NO">NO</option>
									</select>	
								</td>
								<td>Duty Exemption</td>
								<!-- <td><input type="date" name="duty" value="" id="duty"></td> -->
								<td>
									<select name="DutyExemption" value="" id="DutyExemption">
										  <option value ="YES">YES</option>
										  <option value ="NO">NO</option>
									</select>	
								</td>
								<td >Email Password</td>
								 <td><input type="password" name="EmailPassword" placeholder="请输入你的邮箱密码" id="EmailPassword" value="" style="width:65%;height:22px;background:#fff" autocomplete="new-password"><span class="passeye" style="margin-left:1%"></span></td>
							</tr>
							<tr>
								<td style="display:none"><span class="DelayInfo"></span></td>
							</tr>
							<tr>
								<td>Invoice No.1</td>
								<td><input type="text" value="" id="InvoiceNo" style="background:#fff"></td>
								<td>Invoice File1</td>
								<td>
									<input type="text" name="" id="InvoiceFile" style="background:#fff" readonly="readonly">
									<span class="up_file up_file-invoice">上传</span>
								</td>
								<td title="Estimate Payment Date">Payment Date</td>
								<td><input type="date" id="EstimatePaymentDate"></td>
							</tr>
							<tr>
								<td>Invoice No.2</td>
								<td><input type="text" value="" id="InvoiceNo2" style="background:#fff"></td>
								<td>Invoice File2</td>
								<td>
									<input type="text" name="" id="InvoiceFile2" style="background:#fff" readonly="readonly">
									<span class="up_file up_file-invoice2">上传</span>
								</td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>Invoice No.3</td>
								<td><input type="text" value="" id="InvoiceNo3" style="background:#fff"></td>
								<td>Invoice File3</td>
								<td>
									<input type="text" name="" id="InvoiceFile3" style="background:#fff" readonly="readonly">
									<span class="up_file up_file-invoice3">上传</span>
								</td>
								<td></td>
								<td></td>
							</tr>
						</tbody>
					</table>	
					<!-- <p class='pr'><em class='pa'>ShippingInstruction</em><em></em><textarea  name="ShippingInstruction" value="" id='ShippingInstruction' style='height:200px;width:700px;'></textarea></p> -->
								 <span style="margin-right:24px;height:200px;display:block;vetical-align:top;float:left;">Shipping Instruction</span>
								<textarea name="ShippingInstruction" value="" id='ShippingInstruction' style="width:77%;height:200px;font-family:Arial;font-size:14px;"></textarea>	
				</div>
				<div class="edit_btn">
					<input type="button" value="保存" class="bToggle" id="update_submit" style="width:90px;">
					<input type="button" value="取消" class="bToggle" id="update_cancel">
				</div>
			</div>
			
			<!-- 文件上传 -->
			<div class="MailBar_cover_color_file" style="display:none;"></div>
		 	<div class="MailBar" style="display:none;">
		 		
				<div class="operate_title">上传文件</div>
				<div class="MailBar_close">关闭</div>
				
		 			<!--不影响使用的代码  -->
					<input type="file" name="file" id="Mail_fileToUpload">
					<input type="button" name="button" value="上传" class="bToggle" id="Mail_Send">
			</div>
			<!-- PSD-email-tem -->
			<div class="PSD-email-tem" style="display: none;">
				<div class="PSD-email-tem-tit">
					<div class="PSD-email-tem-tit-l">Push Ship Date邮箱模板</div>
					<div class="PSD-email-tem-tit-r">关闭</div>
				</div>
				<div class="PSD-email-tem-body">
					<div class="m-title">邮件模板</div>
					<div class="m-recipient">
						<div class="u-recipient">
							<div class="u-recipient-div" style="visibility:visible;"><span class="i-recipient">收件人</span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span></div>
							<div class="u-recipient-div" style="visibility:hidden;opacity:0"><span class="i-recipient">收件人1</span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>
							<div class="u-recipient-div" style="visibility:hidden;opacity:0"><span class="i-recipient">收件人2</span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>
						</div>
					</div>
					<div class="m-CC">
						<div class="u-CC">
							<div class="u-CC-div" style="visibility:visible;"><span class="i-CC">抄送人</span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span></div>
							<div class="u-CC-div" style="visibility:visible;"><span class="i-CC">抄送人1</span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>
							<div class="u-CC-div" style="visibility:visible;"><span class="i-CC">抄送人2</span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>
						</div>
					</div>
					<div class="m-subject">
						<span class="u-subject">主题</span><input type="text">
					</div>
					<div class="m-content">
						<span class="u-content">内容</span><div>
							<p>Hi, Ailin,</p><p class="i-content wrap-p" contenteditable="true"></p><p>Thanks for your support.</p>
						</div>
					</div>
				</div>
				<div class="PSD-email-tem-foot">
					<div class="PSD-email-tem-foot-in">
						<input type="button" class="eou-button eou-button-30 eou-button-w80" value="发送邮件" style="margin-right: 30px;" id="PSD-do">
						<input type="button" class="eou-button eou-button-30 eou-button-w80" value="取消发送" id="PSD-can">
					</div>
				</div>
			</div>
			<!-- PT-email-tem -->
			<div class="PT-email-tem" style="display: none;">
				<div class="PT-email-tem-tit">
					<div class="PT-email-tem-tit-l">Push Tracking邮箱模板</div>
					<div class="PT-email-tem-tit-r">关闭</div>
				</div>
				<div class="PT-email-tem-body">
					<div class="m-title2">邮件模板</div>
					<div class="m-recipient2">
						<div class="u-recipient2">
							<div class="u-recipient-div2" style="visibility:visible;"><span class="i-recipient2">收件人</span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span></div>
							<div class="u-recipient-div2" style="visibility:hidden;opacity:0"><span class="i-recipient2">收件人1</span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>
							<div class="u-recipient-div2" style="visibility:hidden;opacity:0"><span class="i-recipient2">收件人2</span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>
						</div>
					</div>
					<div class="m-CC2">
						<div class="u-CC2">
							<div class="u-CC-div2" style="visibility:visible;"><span class="i-CC2">抄送人</span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span></div>
							<div class="u-CC-div2" style="visibility:visible;"><span class="i-CC2">抄送人1</span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>
							<div class="u-CC-div2" style="visibility:visible;"><span class="i-CC2">抄送人2</span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>
						</div>
					</div>
					<div class="m-subject2">
						<span class="u-subject2">主题</span><input type="text">
					</div>
					<div class="m-content2">
						<span class="u-content2">内容</span><div>
							<p>Hi, Ailin,</p><p class="i-content2 wrap-p2" contenteditable="true"></p><p>Thanks for your support.</p>
						</div>
					</div>
				</div>
				<div class="PT-email-tem-foot">
					<div class="PT-email-tem-foot-in">
						<input type="button" class="eou-button eou-button-30 eou-button-w80" value="发送邮件" style="margin-right: 30px;" id="PT-do">
						<input type="button" class="eou-button eou-button-30 eou-button-w80" value="取消发送" id="PT-can">
					</div>
				</div>
			</div>
			<!-- Inform -->
			<div class="InformSel" style="display: none;">
				<div class="InformSel-tit">
					<div class="InformSel-tit-l">仓库地址选择</div>
					<div class="InformSel-tit-r">关闭</div>
				</div>
				<div class="InformSel-body">
					<div class="InformSel-body-in">
						<select name="" id="InformSel-body-sel">
							<option name="0" value="请选择仓库地址" checked>请选择仓库地址</option>
							<option name="1" value="HK EOULU TRADING LIMITED">HK EOULU TRADING LIMITED</option>
							<option name="2" value="Awot Global Express (HK) Ltd">Awot Global Express (HK) Ltd</option>
							<option name="3" value="Shing Fat Logistics Ltd">Shing Fat Logistics Ltd</option>
							<option name="4" value="Other">Other</option>
						</select>
					</div>
				</div>
			</div>
			<!-- Inform-email-tem -->
			<div class="Inform-email-tem" style="display: none;">
				<div class="Inform-email-tem-tit">
					<div class="Inform-email-tem-tit-l">收货通知邮箱模板</div>
					<div class="Inform-email-tem-tit-r">关闭</div>
				</div>
				<div class="Inform-email-tem-body">
					<div class="m-title3">邮件模板<span>如需更改仓库请点击右侧按钮<button class="eou-button eou-button-radius eou-button-20 eou-button-w60">更改仓库</button></span></div>
					<div class="m-recipient3">
						<div class="u-recipient3">
							<div class="u-recipient-div3" style="visibility:visible;"><span class="i-recipient3">收件人</span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span></div>
							<div class="u-recipient-div3" style="visibility:visible;"><span class="i-recipient3">收件人1</span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>
							<div class="u-recipient-div3" style="visibility:visible;"><span class="i-recipient3">收件人2</span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>
						</div>
					</div>
					<div class="m-CC3">
						<div class="u-CC3">
							<div class="u-CC-div3" style="visibility:visible;"><span class="i-CC3">抄送人</span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span></div>
							<div class="u-CC-div3" style="visibility:visible;"><span class="i-CC3">抄送人1</span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>
							<div class="u-CC-div3" style="visibility:visible;"><span class="i-CC3">抄送人2</span><input type="text"><span class="i-add"><i class="fa fa-plus-square"></i></span><span class="i-del"><i class="fa fa-close"></i></span></div>
						</div>
					</div>
					<div class="m-subject3">
						<span class="u-subject3">主题</span><input type="text">
					</div>
					<div class="m-content3">
						<span class="u-content3">内容</span><div>
							<p>您好！</p><p class="i-content3 wrap-p3" contenteditable="true"></p><p>烦请仓库签收后提供签收单和随货单据扫描件，非常感谢！</p>
						</div>
					</div>
				</div>
				<div class="Inform-email-tem-foot">
					<div class="Inform-email-tem-foot-in">
						<input type="button" class="eou-button eou-button-30 eou-button-w80" value="发送邮件" style="margin-right: 30px;" id="Inform-do">
						<input type="button" class="eou-button eou-button-30 eou-button-w80" value="取消发送" id="Inform-can">
					</div>
				</div>
			</div>
			<!-- 保存发送分离 -->
			<div class="bg_cover2"></div>
			<div class="publish_div" style="display: none;">
			    <div class="publish_div_tit">发布提示信息</div>
			    <div class="publish_div_body"></div>
			    <div class="publish_div_foot">
			        <div class="publish_div_foot_in"></div>
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
<script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script>
<script src="js/msgbox.js"></script>
<script src="js/ajaxfileupload.js" ></script>
<script src="js/msgbox_unload.js"></script>
<!-- <script src="js/libs/jquery.cookie.js"></script> -->
<script src="plugins/cookie/jquery.cookie.js"></script>
<script src="js/libs/jquery.base64.js"></script>
<!-- <script src="js/global/myFunction.js"></script> -->
<script src="js/OriginFactory.js"></script>
<!-- <script type="text/javascript" src="js/html2canvas.js"></script> -->
<!-- <script type="text/javascript" src="js/jsPdf.debug.js"></script> -->
<script>
var email_data = new Object();
email_data.ID = null;
email_data.PO = null;
email_data.SO = null;
email_data.FactoryPeriod = null;
email_data.DelayPeriod = null;
email_data.DelayReason = null;
email_data.SOA = null;
email_data.BAFA = null;
email_data.TargetDate = null;
email_data.TrackingNO = null;
email_data.PaymentLC = null;
email_data.DutyExemption = null;
email_data.pwd = null;
email_data.DI = null;
email_data.DelayInfo = null;
email_data.ShippingInstruction = null;
// InvoiceNo: InvoiceNo,
// InvoiceFile: InvoiceFile,
email_data.EstimatePaymentDate = null;
var currentUserName = $("span#userName").text();
//保存邮箱密码 
function saveMailPass(){ 
	var EmailPassword = $("#EmailPassword").val().trim();  
	$.cookie(currentUserName+"MailPass", $.base64.encode(EmailPassword), { expires: 360 }); 
} 
function unsaveMailPass(){ 
	$.cookie(currentUserName+"MailPass", "", { expires: -1 }); 
}
function trueRemMP(){
	$.cookie(currentUserName+"remMP", "true", { expires: 360 });
}
function falseRemMP(){
	$.cookie(currentUserName+"remMP", "false", { expires: -1 }); 
}

// // 清除cookie
// function clearEmailCookie(){
// 	if($.cookie(currentUserName+"has_clear_cookie") != "true"){
// 		unsaveMailPass();
// 		$.cookie(currentUserName+"has_send", "false", { expires: -1 });
// 		$.cookie(currentUserName+"has_clear_cookie", "true", { expires: 360 });
// 	}
// }

// function clearAllCookie(){
// 	unsaveMailPass();
// 	$.cookie(currentUserName+"has_send", "false", { expires: -1 });
// 	$.cookie(currentUserName+"has_clear_cookie", "false", { expires: -1 });
// }

function saveSuccess(){
	$(".bg_cover2").fadeIn(200);
	$(".publish_div").fadeIn(200);
	$(".publish_div_body").text("保存成功，是否发送邮件？");
	var str1 = '<input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" id="publish_yes" value="是">'+
	'<input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" id="publish_no" value="否">';
	$(".publish_div_foot_in").html(str1);
}

function saveError(){
	$(".bg_cover2").fadeIn(200);
	$(".publish_div").fadeIn(200);
	$(".publish_div_body").text("保存失败，请刷新或等待重试！");
	var str2 = '<input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" id="has_publish" value="好的">';
	$(".publish_div_foot_in").html(str2);
}

function mailObjClean(){
	for(var k in email_data){
		email_data[k] = null;
	}
}

function closeMailSend(){
	$(".bg_cover2").fadeOut(200);
	$(".publish_div").fadeOut(200);
}

//小眼睛
 $("span.passeye").click(function(){
 				// $("#EmailPassword").removeAttr("autocomplete");
		        if ($("#EmailPassword").attr("type") == "text") {
		            $("#EmailPassword")[0].type = "password";  
		        }else{
		          $("#EmailPassword")[0].type = "text"; 
		        } 
		        // saveMailPass();
		    });

//------------------------------隐藏列-------------------------------------
// $('#fa-button1').bind('click',function(){
//  $('#fa-button1').toggleClass('fa-minus-square');
//  $('#table1 tr').find('td:eq(10)').toggle();
//  $('#table1 tr').find('td:eq(11)').toggle(); 
//  if($("#fa-button1").is(".fa-minus-square") && $("#fa-button2").is(".fa-minus-square")){
//  	$("#table1 tr").css("font-size","15px");
//  }else{
//  	$("#table1 tr").css("font-size","16px");
//  }
// });

$('#fa-button2').bind('click',function(){
 $('#fa-button2').toggleClass('fa-minus-square');
 $('#table1 tr').find('td:eq(17)').toggle();
 // $('#table1 tr').find('td:eq(18)').toggle(); 
 if($("#fa-button2").is(".fa-minus-square")){
 	$("#table1 tr").css("font-size","15px");
 }else{
 	$("#table1 tr").css("font-size","16px");
 }
});

/*********************添加质量证明信息************************/
/* 修改质量证明信息 */
$(document).on("click",".contract-edit",function(){	
	$("#EmailPassword").val("");
	if ($.cookie(currentUserName+"remMP") == "true") { 
		$("#EmailPassword").removeAttr("autocomplete");
		$("#EmailPassword").val($.base64.decode($.cookie(currentUserName+"MailPass"))); 
		$("#EmailPassword").attr("autocomplete","new-password");
	} 
	
	var tr=$(this).parent();
	var aPO = tr.find('td').eq(2).text().trim();
	var aSO = tr.find('td').eq(3).text().trim();
	var aFactoryPeriod = tr.find('td').eq(7).text().trim();
	aFactoryPeriod = aFactoryPeriod=="0000-00-00"?"":aFactoryPeriod;
	var aDelayPeriod = tr.find('td').eq(9).text().trim();
	aDelayPeriod = aDelayPeriod=="0000-00-00"?"":aDelayPeriod;
	var aDelayReason = tr.find('td').eq(10).text().trim();
	var aTargetDate = tr.find('td').eq(6).text().trim();
	aTargetDate = aTargetDate=="0000-00-00"?"":aTargetDate;
	var aTrackingNO = tr.find('td').eq(8).text().trim();

	// 发票No
	var InvoiceNo123 = tr.find(".td_invoiceNo123").html();
	var aInvoiceNo = InvoiceNo123.split("<br>")[0];
	var aInvoiceNo2 = InvoiceNo123.split("<br>")[1];
	var aInvoiceNo3 = InvoiceNo123.split("<br>")[2];

	var aEstimatePaymentDate = tr.find('td.EstimatePaymentDate_td').text().trim();
	aEstimatePaymentDate = aEstimatePaymentDate=="0000-00-00"?"":aEstimatePaymentDate;
	var aShippingInstruction = tr.find('td').eq(13).text().trim();

	var bPO = globalDataHandle(aPO,"");
	var bSO = globalDataHandle(aSO,"");
	var bFactoryPeriod = globalDataHandle(aFactoryPeriod,"");
	var bDelayPeriod = globalDataHandle(aDelayPeriod,"");
	var bDelayReason = globalDataHandle(aDelayReason,"");
	var bTargetDate = globalDataHandle(aTargetDate,"");
	var bTrackingNO = globalDataHandle(aTrackingNO,"");
	var bInvoiceNo = globalDataHandle(aInvoiceNo,"");
	var bInvoiceNo2 = globalDataHandle(aInvoiceNo2,"");
	var bInvoiceNo3 = globalDataHandle(aInvoiceNo3,"");
	var bEstimatePaymentDate = globalDataHandle(aEstimatePaymentDate,"");
	var bShippingInstruction = globalDataHandle(aShippingInstruction,"");

    $('.contract_update input[name="PO"]').val(bPO);
	$('.contract_update input[name="SO"]').val(bSO);
	$('.contract_update input[name="FactoryPeriod"]').val(bFactoryPeriod);
	$('.contract_update input[name="DelayPeriod"]').val(bDelayPeriod);
	$('.contract_update input[name="DelayReason"]').val(bDelayReason);
	$('.contract_update .idinfo').text(tr.find('td').eq(0).val());
	/* radio */
	/* alert(tr.find('td').eq(11).text()) */
	/* alert(tr.find('td').eq(11).find('input[type="radio"]:checked').val()); */
	var DI = tr.find('td').eq(11).find('input[type="radio"]:checked').val();
	 $('.contract_update .DelayInfo').text(DI); 
	/* 新增的代码部分 */
	
	/* $('.contract_update input[name="SOA"]').val(tr.find('td').eq(4).text()); */
	/* $('.contract_update input[name="BAFA"]').val(tr.find('td').eq(5).text()); */
	/* var SOAyn = tr.find('td').eq(4).text(); */
	/* console.log($('.contract_update select#SOA option[value="'+SOAyn+'"]')) */
	 $('.contract_update select#SOA option[value ="'+ tr.find('td').eq(4).find("span.SOA").text() +'"]').attr('selected',true); 
	/*  $('.contract_update select[name="SOA"]').find('option[value ="' + SOAyn + '"]').attr("selected", true); */
	 $('.contract_update select#BAFA option[value ="'+ tr.find('td').eq(5).find("span.BAFA").text() +'"]').attr('selected',true); 
	$('.contract_update input[name="TargetDate"]').val(bTargetDate);
	$('.contract_update #TrackingNO').val(bTrackingNO);
	$("#InvoiceNo").val(bInvoiceNo);
	$("#InvoiceNo2").val(bInvoiceNo2);
	$("#InvoiceNo3").val(bInvoiceNo3);
	$("#EstimatePaymentDate").val(bEstimatePaymentDate);
	// 发票文件
	var InvoiceFileVal1 = tr.find(".InvoiceFile1_td").text();
	var InvoiceFileVal2 = tr.find(".InvoiceFile2_td").text();
	var InvoiceFileVal3 = tr.find(".InvoiceFile3_td").text();
	if(InvoiceFileVal1 == "无"){
		InvoiceFileVal1="";
	}
	if(InvoiceFileVal2 == "无"){
		InvoiceFileVal2="";
	}
	if(InvoiceFileVal3 == "无"){
		InvoiceFileVal3="";
	}
	$("#InvoiceFile").val(InvoiceFileVal1);
	$("#InvoiceFile2").val(InvoiceFileVal2);
	$("#InvoiceFile3").val(InvoiceFileVal3);

	$('.contract_update textarea[name="ShippingInstruction"]').text(bShippingInstruction);
	/* $('.contract_update input[name="PaymentLC"]').val(tr.find('td').eq(14).text());
	$('.contract_update input[name="DutyExemption"]').val(tr.find('td').eq(15).text()); */
	 $('.contract_update select#PaymentLC option[value ="'+ tr.find('td').eq(14).text() +'"]').attr('selected',true); 
	 $('.contract_update select#DutyExemption option[value ="'+ tr.find('td').eq(15).text() +'"]').attr('selected',true); 
	var  ID = tr.find('td').eq(0).attr("value");
	
	$(".contract_update .contract_title").attr("value",ID);  //在修改页面保存当前行的ID信息
	
	console.log(ID)
   $('.MailBar_cover_color').show();
   $('.contract_update').show();   	    
 });
   	
	/*  提交修改后的信息  */
	$('#update_submit').click(function () {
		var pwd = $('.contract_update input#EmailPassword').val().trim();
		if(pwd == '' || pwd == null){
			 $.MsgBox_Unload.Alert('提示','请输入并检查你的邮箱密码');
			 return;
		}
		var PO = $('.contract_update input[name="PO"]').val().trim();
		var SO = $('.contract_update input[name="SO"]').val().trim();
		var FactoryPeriod = $('.contract_update input[name="FactoryPeriod"]').val();
		var DelayPeriod = $('.contract_update input[name="DelayPeriod"]').val();
		var DelayReason = $('.contract_update input[name="DelayReason"]').val();
		var  ID = $(".contract_update .contract_title").attr("value");
		/* 新增的代码部分 */
		var SOA = $('.contract_update select#SOA option:selected').text();
		/* alert(SOA) */
		var BAFA = $('.contract_update select#BAFA option:selected').text();
		/* alert(BAFA) */
		var TargetDate = $('.contract_update input[name="TargetDate"]').val();
		var TrackingNO = $('.contract_update #TrackingNO').val();
		var PaymentLC = $('.contract_update select#PaymentLC option:selected').text();
		/* alert(PaymentLC) */
		var DutyExemption = $('.contract_update select#DutyExemption option:selected').text();
		var ShippingInstruction = $('.contract_update textarea[name="ShippingInstruction"]').val();
		
		var DI =  $('.contract_update .DelayInfo').text();
		var DelayInfo = $("span.DelayInfo").text();
		var InvoiceNo1 = $("#InvoiceNo").val().trim();
		var EstimatePaymentDate = $("#EstimatePaymentDate").val();
		var InvoiceFile1 = $("#InvoiceFile").val();
		var InvoiceNo2 = $("#InvoiceNo2").val().trim();
		var InvoiceFile2 = $("#InvoiceFile2").val();
		var InvoiceNo3 = $("#InvoiceNo3").val().trim();
		var InvoiceFile3 = $("#InvoiceFile3").val();
		  $.ajax({
		      type : 'get',
		      url : 'OriginFactoryOperate',
		      data : {
		    	  ID : ID,
		    	  PO : PO,
		    	  SO : SO,
		    	  FactoryPeriod : FactoryPeriod,
		    	  DelayPeriod : DelayPeriod,
		    	  DelayReason : DelayReason,
		    	  SOA : SOA,
		    	  BAFA : BAFA,
		    	  TargetDate : TargetDate,
		    	  TrackingNO : TrackingNO,
		    	  PaymentLC : PaymentLC,
		    	  DutyExemption : DutyExemption,
		    	  pwd : pwd,
		    	  DI : DI ,
		    	  DelayInfo:DelayInfo,
		    	  ShippingInstruction : ShippingInstruction,
		    	  InvoiceNo1: InvoiceNo1,
		    	  InvoiceFile1: InvoiceFile1,
		    	  InvoiceNo2: InvoiceNo2,
		    	  InvoiceFile2: InvoiceFile2,
		    	  InvoiceNo3: InvoiceNo3,
		    	  InvoiceFile3: InvoiceFile3,
		    	  EstimatePaymentDate: EstimatePaymentDate
		      },
		      dataType: "text",
		      beforeSend: function(XMLHttpRequest){
		          $("#update_submit").attr("disabled","disabled");
		          $("#update_submit").css({
		          	"background":"#dddddd",
		          	"color":"#808080",
		          	"border":"none",
		          	"box-shadow":"0 0 0 0 #f8fcfd",
		          	"cursor":"not-allowed"
		          });
		      },
		        complete: function(XMLHttpRequest, textStatus){
		            $("#update_submit").attr("disabled",false);
		            $("#update_submit").css({
		          	  "background":"#00aeef",
		          	  "color":"#fff",
		          	  "border":"solid 1px #00aeef",
		          	  "box-shadow":"1px 2px 5px 0 #00aeef",
		          	  "cursor":"pointer"
		            });
		        },
		        success : function (data) {
		        	if(data.indexOf("保存成功")>-1){
		        		email_data.ID = ID;
		        		email_data.PO = PO;
		        		email_data.SO = SO;
		        		email_data.FactoryPeriod = FactoryPeriod;
		        		email_data.DelayPeriod = DelayPeriod;
		        		email_data.DelayReason = DelayReason;
		        		email_data.SOA = SOA;
		        		email_data.BAFA = BAFA;
		        		email_data.TargetDate = TargetDate;
		        		email_data.TrackingNO = TrackingNO;
		        		email_data.PaymentLC = PaymentLC;
		        		email_data.DutyExemption = DutyExemption;
		        		email_data.pwd = pwd;
		        		email_data.DI = DI;
		        		email_data.DelayInfo = DelayInfo;
		        		email_data.ShippingInstruction = ShippingInstruction;
		        		// InvoiceNo: InvoiceNo,
		        		// InvoiceFile: InvoiceFile,
		        		email_data.EstimatePaymentDate = EstimatePaymentDate;
		        		saveSuccess();
		        	}else{
		        		saveError();
		        		mailObjClean();
		        	}
		        },
		        error: function () {
			    	mailObjClean();
		            $.MsgBox_Unload.Alert("提示","服务器繁忙！");
		        }
		    }); 
	})   
    
//点击添加
function AddContract() {
	/*默认当前日期 */
	var ddd = new Date();
	var day =ddd.getDate();
	if(ddd.getMonth()<10){
	var month = "0"+(ddd.getMonth()+1); 
	}
	if(ddd.getDate()<10){
	 day = "0"+ddd.getDate(); 
	}
	var datew = ddd.getFullYear()+"-"+month+"-"+day;
	datew = datew.toString();
	$("#Date").val(datew);
    $('.MailBar_cover_color').show();
    $('.contract_add').show();
};

//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function () {
    window.location.reload();
});

//点击关闭
$('.contractUpdate_close').click(function () {
  $('.MailBar_cover_color').hide();
  $('.contract_update').hide();
  mailObjClean();
  window.location.reload();
});
$('#contract_close1').click(function () {
  $('.MailBar_cover_color').hide();
  $('.hidePdf').hide();
});

//点击取消
$('#update_cancel').click(function () {
  $('.MailBar_cover_color').hide();
  $('.contract_update').hide();
  mailObjClean();
  window.location.reload();
});

//延迟通知取消
$('#send_cancel').click(function() {
	$('.MailBar_cover_color').hide();
	$('.contract_send').hide();
});

/****************** 跳页 **********************/
var OriginFactoryCurHref = eouluGlobal.S_getCurPageHref();
function FistPage(arg) {
	if(OriginFactoryCurHref == "OriginFactory"){
		window.location.href = 'OriginFactory?currentPage=1';
	}else if(OriginFactoryCurHref == "OriginFactorySearch"){
		$("#OriginFactorySearch_curpage").val("1");
		$('#top_text_from').submit();
	}
}
function UpPage(arg) {
	if(OriginFactoryCurHref == "OriginFactory"){
		window.location.href = 'OriginFactory?currentPage='+arg;
	}else if(OriginFactoryCurHref == "OriginFactorySearch"){
		$("#OriginFactorySearch_curpage").val(Number($("span#currentPage").text()) - 1);
		$('#top_text_from').submit();
	}
}
function NextPage(arg) {
	if(OriginFactoryCurHref == "OriginFactory"){
		window.location.href = 'OriginFactory?currentPage='+arg;
	}else if(OriginFactoryCurHref == "OriginFactorySearch"){
		$("#OriginFactorySearch_curpage").val(Number($("span#currentPage").text()) + 1);
		$('#top_text_from').submit();
	}
}
function PageJump(arg) {
	var jumpNumber = document.getElementById("jumpNumber").value;
	if (jumpNumber == null || jumpNumber == 0) {
		jumpNumber = $('#currentPage').html();
	} else if (jumpNumber > parseInt($('#allPage').html())) {
		jumpNumber = $('#allPage').html();
	}
	if(OriginFactoryCurHref == "OriginFactory"){
		window.location.href = 'OriginFactory?currentPage='+jumpNumber;
	}else if(OriginFactoryCurHref == "OriginFactorySearch"){
		$("#OriginFactorySearch_curpage").val(jumpNumber);
		$('#top_text_from').submit();
	}
}
function LastPage(arg) {
	if(OriginFactoryCurHref == "OriginFactory"){
		window.location.href = 'OriginFactory?currentPage='+arg;
	}else if(OriginFactoryCurHref == "OriginFactorySearch"){
		$("#OriginFactorySearch_curpage").val($("span#allPage").text() * 1);
		$('#top_text_from').submit();
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
	// clearEmailCookie();
	// $(".reset_cookie").on("click",function(){
	// 	clearAllCookie();
	// 	$(".reset_cookie").fadeOut(200);
	// });
});

/*********模板 删除*******/
  $(document).on("click",".del",function () {
	  var thisList = $(this).parent().parent();
      var ID = thisList.find("td").eq(0).attr("value");
      $(".yejiao").attr("value",ID);
	  console.log(ID);
	  $.ajax({
          type : 'get',
          url : "QualityDelete",
          data : {	
          	ID:ID
          },
          
          dataType : 'json',
          success : function (data) {  
        	  if(data){
        		  $.MsgBox.Alert('提示','删除成功');
  	            $('.MailBar_cover_color').hide();
  	            $('.hidePdf').hide();
        	  }else{
        		  $.MsgBox.Alert("提示", "删除失败，稍后重试！");
        	  }
          	
          },
          error : function () {
              $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
          }
      });
 	   var  that = $(this);
	   $.MsgBox.Confirm('提示','确认要删除吗？',function(){
		   that.parents("tr").remove();
	  });  
 })

$(document).on("focus","#EmailPassword",function(){
	$(".contract_update tbody input[type='text']").each(function(){
		if($(this).val()=="" && $(this).attr("id")!="InvoiceFile" && $(this).attr("id")!="InvoiceFile2" && $(this).attr("id")!="InvoiceFile3"){
			$(this).attr("readonly","readonly");
		}
	});
});
$(document).on("blur","#EmailPassword",function(){
	$(".contract_update tbody input[type='text']").each(function(){
		if($(this).attr("readonly")=="readonly" && $(this).attr("id")!="InvoiceFile" && $(this).attr("id")!="InvoiceFile2" && $(this).attr("id")!="InvoiceFile3"){
			$(this).removeAttr("readonly");
		}
	});
	$("#TrackingNO").removeAttr("readonly");
});

// 是 否 好的
$(document).on("click","#publish_no, #has_publish",function(){
    $(".publish_div").fadeOut(200);
    $(".bg_cover2").fadeOut(200);
});
$(document).on("click","#publish_yes",function(){
    $.ajax({
        type: 'POST',
        url: 'OriginFactoryOperate',
        data: email_data,
        contentType:"application/x-www-form-urlencoded;charset=utf-8",
        dataType: "text",
        beforeSend: function(XMLHttpRequest){
            $("#publish_yes").attr("disabled","disabled");
            $("#publish_yes").css({
            	"cursor":"not-allowed"
            });
        },
        complete: function(XMLHttpRequest, textStatus){
            $("#publish_yes").attr("disabled",false);
            $("#publish_yes").css({
          	  "cursor":"pointer"
            });
        },
        success: function(data){
	      	console.log(typeof data);
	      	if(data.indexOf("发送成功")>-1){
	      		closeMailSend();
	      		$.MsgBox_Unload.Alert("提示","发送成功！");
	      		trueRemMP();
	      		saveMailPass();
	      	}else{
	      		falseRemMP();
	      		unsaveMailPass();
	      		$.MsgBox_Unload.Alert("提示",data);
	      	}
        },
        error: function () {
      	    falseRemMP();
	      	unsaveMailPass();
            $.MsgBox_Unload.Alert("提示","服务器繁忙");
        }
    });
});

</script>
</html>
