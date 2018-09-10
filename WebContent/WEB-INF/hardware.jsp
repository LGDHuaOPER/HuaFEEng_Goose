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
<title>装机进展</title>
<style>
	.content .choose input[type="button"], .content .select-button input[type="button"] {
		width: auto !important;
		height: auto !important;
	    border: 1px solid transparent !important;
	}

	.content .select-content {
		padding-top: 5px !important;
	}

	.content .choose {
		margin-bottom: 5px !important;
	}

	.content {
		padding-bottom: 5px !important;
	}
	/*布局*/
	html,body{
	    width:100%;
	    height:100%;
	}

	#Transport_wrapper {
	    position:fixed;
	    overflow:auto;
	    width:100%;
	    height:100%;
	    box-sizing:border-box;
	}

	#Transport_sticker {
	    width:100%;
	    min-height:100%;
	    box-sizing:border-box;
	}

	#Transport_sticker-con {
	    padding-bottom:40px;
	    box-sizing:border-box;
	}

	#Transport_footer {
	    margin-top:-40px;
	}

	/*导航栏及搜索框各页面自定义*/
	.g-nav-ul li {
		height: 80px !important;
		line-height: 80px !important;
	}

	#eoulu-copy hr {
		margin-top: 1px !important;
		margin-bottom: 1px !important;
	}

	.content .select-button {
	    position: relative !important;
	    top: -2px !important;
	}
</style>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<!-- <link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css"> -->
<!-- <link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" /> -->
<link rel="stylesheet" type="text/css" href="css/libs/bootstrap-grid-form-btn-res-icon-tooltip-popover.min.css">
<link rel="stylesheet" type="text/css" href="css/global/global_table_style.css">
<link rel="stylesheet" type="text/css" href="css/hardware.css">
</head>
<body>
	<div id="Transport_wrapper">
	    <div id="Transport_sticker">
	        <div id="Transport_sticker-con">
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
							action="GetHardwareRoute">
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
										value="${fn:split('客户,装机时间,项目状态,负责人及进展',',')}"></c:set>
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
										<input type="button" value="搜索" class="btn btn-info" onclick="INSearch()"> 
										<input type="button" value="取消" class="btn btn-warning" onclick="Cancel()">
									</div>
							</div>
							<div class="choose">
								<input type="button" value="添加" class="btn btn-info" onclick="AddContract()">
							</div>
					 </form>
					<!-- 主页面表格 -->
					<div class="gl_table_style_wrapper">
						<table class="gl_table_style">
							<thead>
								<tr>
									<th>项目</th>
									<th style="display:none">修改</th>
									<th>客户 </th>
									<th>装机时间</th>
									<th class="progress_td">项目状态</th>
									<th>负责人及进展</th>
									<th style="display:none;">删除数据</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="orderInfo" items="${hardware}" varStatus="status">
									<c:if test="${status.index>0}">
										<tr>
											<td value="${orderInfo['ID']}" class="contract-edit" style="cursor:pointer">${status.index+(currentPage-1)*10}</td>
											<td style="display:none"><i class="fa fa-edit" value="${orderInfo['ID']}"></i></td>
											<td title="${orderInfo['Customer']}">${orderInfo['Customer']}</td>       <!--2  -->
											<td>${orderInfo['InstalledTime']}</td>   <!--3  -->
											<c:if test="${orderInfo['Status'] == 1}">
												<td class="progress_td">交付</td> 
											</c:if>
											<c:if test="${orderInfo['Status'] == 2}">
												<td class="progress_td">尾款</td> 
											</c:if>
											<c:if test="${orderInfo['Status'] == 3}">
												<td class="progress_td">完结</td> 
											</c:if>
											         <!--4  -->
											<td title="${orderInfo['ResponsibleAndProcess']}">${orderInfo['ResponsibleAndProcess']}</td>    <!--5  -->
											<td style="display:none;"><i class="fa fa-trash-o del"></i></td>   <!--6  -->
										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
					</div>
							
				 	 <c:choose>
						<c:when test="${queryType == 'common'}">
							<c:set var="queryUrl"
							value="Hardware?type1=${classify1 }&searchContent1=${parameter1}&selected=${str}&currentPage=">
							</c:set>
						</c:when>
						<c:otherwise>
							<c:set var="queryUrl"
							value="GetHardwareRoute?type1=${classify1 }&searchContent1=${parameter1}&type2=${classify2}&searchContent2=${parameter2}&selected=${queryType}&currentPage=">
							</c:set>
						</c:otherwise>
					</c:choose> 
						<div id="glbal_table_page_wrapper">
							<div id="global_table_page">
								<div class="pageInfo">
									当前是第&nbsp;<span id="currentPage">${currentPage}</span>&nbsp;页,&nbsp;总计&nbsp;<span
										id="allPage">${pageCounts}</span>页
								</div>
								<div class="changePage">
									<input type="button" class="btn btn-primary" value="首页" id="fistPage" name="fistPage" onclick="FistPage('${queryUrl}')">
									<input type="button" class="btn btn-primary" value="上一页" id="upPage" onclick="UpPage('${queryUrl}${currentPage-1 }')">
									<input type="button" class="btn btn-primary" value="下一页" id="nextPage" onclick="NextPage('${queryUrl}${currentPage+1 }')"> 跳到第 <input type="text" id="jumpNumber" name="jumpNumber" class="jumpNumber" style="width: 30px; color: #000" onkeyup="value=value.replace(/[^\d]/g,'') "> 页 <input type="button" class="btn btn-primary" value="GO" id="Gotojump" name="Gotojump" onclick="PageJump('${queryUrl}')">
									<input type="button" class="btn btn-primary" value="尾页" id="lastPage" name="lastPage" onclick="LastPage('${queryUrl}')">
								</div>
							</div>
						</div><!-- glbal_table_page_wrapper end -->
					</div>
				</div>
				<div class="MailBar_cover_color" style="display: none;"></div>
				
				<!-- 添加装机进展信息 -->
				<div class="contract_add" style="display: none;">
					<div class="contract_title">添加装机进展</div>
					<div class="contractAdd_close">关闭</div>
					<div class="basic_info">
						<div class="table_title">装机进展</div>
						<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
							<tbody>	
								<tr>
								    <td>装机时间</td>
									<td><input type="date" name="InstalledTime" value="" id="InstalledTime"></td>	
									<td>客户</td>
									<td><input type="text" name="Customer" value=""  id="Customer"></td>
								</tr>
								<tr>
									<td>项目状态</td>
									<td>
										<input type="text" name="Status" value="" style="position:absolute;width:162px;" id="Status">
											<select onchange="document.getElementById('Status').value=this.options[this.selectedIndex].text" class="StatusSelect">
												<option value="No" selected="selected">--请选择--</option>
												<option value="">交付</option>
												<option value="">尾款</option>
												<option value="">完结</option>
											</select>
									</td>
									<td>负责人及进展</td>
									<td><input type="text" name="ResponsibleAndProcess" value=""></td>
								</tr>
							</tbody>
						</table>		
					</div>
					<div class="edit_btn">
						<input type="button" value="提交" class="bToggle" id="add_submit">
						<input type="button" value="取消" class="bToggle" id="add_cancel">
					</div>
				</div>
				
				<!-- 修改装机进展信息 -->
				<div class="contract_update" style="display: none;">
					<div class="contract_title">修改装机进展</div>
					<div class="contractUpdate_close">关闭</div>
					<div class="basic_info">
						<div class="table_title">装机进展</div>
						<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
							<tbody>	
								<tr>
								    <td>装机时间</td>
									<td><input type="date" name="InstalledTime" value="" id="InstalledTime"></td>	
									<td>客户</td>
									<td><input type="text" name="Customer" value=""  id="Customer"></td>
								</tr>
								<tr>
									<td>项目状态</td>
									<td>
										<input type="text" name="Status" value="" style="position:absolute;width:162px;" id="Status1">
											<select onchange="document.getElementById('Status1').value=this.options[this.selectedIndex].text" class="StatusSelect">
												<option value="No" selected="selected">--请选择--</option>
												<option value="">交付</option>
												<option value="">尾款</option>
												<option value="">完结</option>
											</select>
									</td>
									<td>负责人及进展</td>
									<td><input type="text" name="ResponsibleAndProcess" value=""></td>
								</tr>
							</tbody>
						</table>		
					</div>
					<div class="edit_btn">
						<input type="button" value="提交" class="bToggle" id="update_submit">
						<input type="button" value="取消" class="bToggle" id="update_cancel">
					</div>
				</div>
				
			<!-- Transport_sticker-con结束 -->
			</div>
	    <!-- Transport_sticker结束 -->
	    </div>
	    <!-- Transport_footer -->
	    <div id="Transport_footer">
	        <div id="eoulu-copy-out" style="height:40px;width:calc(100% - 2px);">
	            <div style="width:100%;height:5px;"></div>
	            <div id="eoulu-copy" style="width:100%;height:35px;font-size:12px;color:#888;line-height:35px;z-index: 2;">
	                <hr style="height:1px;color:#999;width: calc(100% - 3px);" />
	                <div style="width:100%;text-align:center;display:inline-block;">Copyright  ©&nbsp;<span class="YEAR">2018</span>&nbsp<a href="http://www.eoulu.com/h-col-268.html" class="EHref" target="_blank" style="color:blue;">Eoulu</a> Tech. Co.,Ltd.</div>
	            </div>
	        </div>
	    </div>
	<!-- Transport_wrapper结束 -->
	</div>
</body>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/libs/bootstrap/bootstrap-grid-form-btn-res-icon-tooltip-popover.min.js"></script>
<script src="js/msgbox.js"></script>
<script src="js/hardware.js"></script>
</html>