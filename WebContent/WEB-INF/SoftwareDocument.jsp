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
<title>软件部文档</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<!-- <link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css"> -->
<!-- <link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" /> -->
<!-- <link rel="stylesheet" type="text/css" href="css/libs/bootstrap-grid-form-button-res-icon-list.min.css"> -->
<link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="css/extends/integrationLibs/awesomplete-mailSetting-a439ec29a7.min.css" type="text/css">

<!-- <link rel="stylesheet" type="text/css" href="css/global/global_table_style.css">
<link rel="stylesheet" type="text/css" href="css/SoftwareDocument.css">
<link rel="stylesheet" href="css/global/eoulu_ul_reset.css" type="text/css">
<link rel="stylesheet" href="css/modules/software/time_line.css" type="text/css"> -->
<link rel="stylesheet" href="css/modules/software/SoftwareDocument-0dd1cea1d7.min.css" type="text/css">
<style>
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

	.clear_float {
		height: 2px;
		width: calc(100% - 2px);
		clear:both;
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

	div.g-nav h5, div.g-nav h6, div.u-admin h5 {
		margin-top: 0px;
		margin-bottom: 0px;
	}

	.content .choose {
		display: inline-block;
		margin-bottom: 10px !important;
	}

	.content .choose input[type="button"] {
		margin-top: 1px !important;
		width: auto !important;
		height: auto !important;
	}

	/*.content .select-button {
		position: relative;
		top: -5px;
	}

	.content .select-button input[type="button"] {
		width: auto !important;
		height: auto !important;
		border-color: #46b8da;
		border: 1px solid transparent !important;
	}*/

	.content .choose input+input {
		margin-left: 10px !important;
	}

	/*自动填充带来的问题，兼容样式*/
	div.awesomplete {
		display: block;
	}

	/*表格自定义*/
	.gl_table_style th:nth-child(1) {
		max-width: 50px;
		min-width: 50px;
		width: 50px;
	}

	.gl_table_style th:nth-child(2), .gl_table_style th:nth-child(4) {
		min-width: 150px;
		width: 20%;
	}

	.gl_table_style th:nth-child(3), .gl_table_style td:nth-child(3) {
		max-width: 980px;
	}

	/*ul自定义*/
	.VersionManagement_div_tit_r ul {
	    min-width: 500px !important;
	}

	.VersionManagement_div_tit_r ul li a{
	    text-align: center;
	    border-radius: 5px;
	}

	.VersionManagement_div_tit_r ul li.actived a, .VersionManagement_div_tit_r ul li.active a:hover, .VersionManagement_div_tit_r ul li.active a:focus {
	    background: #448bea;
	    color: #fff !important;
	}

	/*邮箱设置框自定义*/
	.mailSetting_div_cover {
		z-index: 101;
	}

	.mailSetting_div {
		z-index: 102;
	}

	.cc_list_cont li, .to_list_cont li {
		font-size: 14px;
	}

	.appendp_p_div textarea+textarea {
		margin-top: 3px;
	}

	/*loading图*/
	.loading_div_g_div {
		position: fixed;
		top: 0;
		bottom: 0;
		left: 0;
		right: 0;
		z-index: 100;
		width: 100vw;
		height: 100vh;
		background-color: #5bc0de;

		filter:alpha(opacity=90); /* IE */  
		-moz-opacity:0.9; /* 老版Mozilla */  
		-khtml-opacity:0.9; /* 老版Safari */  
		opacity: 0.9; /* 支持opacity的浏览器*/ 

	    /*text-align: center;*/
	    /*水平垂直居中，父元素设置*/
	    display: -webkit-flex;
	    display: flex;
	    	/*设置弹性盒子元素在主轴（横轴）的对齐方式。*/
	    justify-content: center;
		/*
		* align-items, align-self 
	设置弹性盒子元素在垂直方向上（纵轴）的对齐方式。其中align-items属性用于弹性容器，而align-self用于弹性项目。
		align-self 属性定义flex子项单独在侧轴（纵轴）方向上的对齐方式。
		align-self 属性可重写灵活容器的 align-items 属性。
		 */
	    align-items: center;
	}
	/*loading图end*/

	.i-content .main .year .list ul li .more {
		font-size: 13px;
	}

	@media screen and (max-width: 1366px) {
	    .gl_table_style th:nth-child(3), .gl_table_style td:nth-child(3) {
	    	max-width: 680px;
	    }
	}

	@media screen and (max-width: 1280px) {
	    .gl_table_style th:nth-child(3), .gl_table_style td:nth-child(3) {
	    	max-width: 640px;
	    }
	}
</style>
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
						<%@include file="nav.jsp"%>
						<div class="show" id="show_MV_CFCs">
							<div class="show_in">
								<!-- 分页标签主体 -->
								<div class="m_pagination">
								    <div class="tab_wrapper changeBox">
								        <ul class="nav nav-tabs" role="tablist">
								            <li role="presentation" class="Norms" data-iidiagram="Norms"><a href="#Norms_a" aria-controls="Norms_a" role="tab" data-toggle="tab" data-idiagram="Norms">软件开发规范</a></li>
								            <li role="presentation" class="CommonProblem" data-iidiagram="CommonProblem"><a href="#CommonProblem_a" aria-controls="CommonProblem_a" role="tab" data-toggle="tab" data-idiagram="CommonProblem">软件开发常见问题库</a></li>
								            <li role="presentation" ID="currentTab" class="Manage active" data-iidiagram="Manage"><a href="#Manage_a" aria-controls="Manage_a" role="tab" data-toggle="tab" data-idiagram="Manage">软件项目实施进度</a></li>
								            <li role="presentation" data-iidiagram="VersionManagement"><a href="#VersionManagement_a" aria-controls="VersionManagement_a" role="tab" data-toggle="tab" data-idiagram="VersionManagement">产品发布记录</a></li>
								        </ul>
										<div class="container-fluid relative_div">
											<input type="button" class="btn btn-info writeEmail" value="上传">
											<input type="button" class="btn btn-info All_upload" id="All_upload" value="批量上传">
											<input type="button" class="btn btn-info mailSetting_open" value="邮箱设置">
									       	<form action="DocumentUpload" method="post" id="search_box">
								            	<input type="text" placeholder="输入关键字查询" class="tsearch" name="content" value="" style="min-width:180px;right: 27%" ><span class="iiisearch" style="right: 27%"><span class="glyphicon glyphicon-search"></span></span>
								            	<span style="display:none" class="scatalog">${catalog}</span>
								            	<span style="display:none" class="scontent">${content}</span>
										    </form>
										</div>
								        <div class="tab-content">
								            <!-- 软件开发规范 -->
								            <div role="tabpanel" class="tab-pane fade" id="Norms_a">
								            	<table id="table1" class="gl_table_style">
								            		<thead>
								            			<tr>
								            				<th>序号</th>
								            				<th>文件类型</th>
								            				<th class="TitleName">文件名称&nbsp;<span class="glyphicon glyphicon-sort-by-attributes" aria-hidden="true"></span></th>
								            				<th>下载</th>
								            				<th style="display:none;">删除数据</th>
								            			</tr>
								            		</thead>
								            		<tbody>
								            			 <c:forEach var="orderInfo" items="${manual}" varStatus="status">
								            				<c:if test="${status.index>0}">
								            					<tr>
								            						<td class="Serial" value="${orderInfo['ID']}">${status.index+(currentPage-1)*10}</td>
								            					    <td><span class="glyphicon glyphicon-file" aria-hidden="true" value="${orderInfo['ID']}"></span></td>
								            						<td  class="ManualWest" title="${orderInfo['FileName']}">${orderInfo['FileName']}</td>
								            						<td><span class="glyphicon glyphicon-save export" aria-hidden="true"></span></td>
								            					</tr>
								            				</c:if>
								            			</c:forEach> 
								            		</tbody>
								            	</table>
								            </div><!-- 软件开发规范 end-->

								            <!-- 软件开发常见问题库 -->
								            <div role="tabpanel" class="tab-pane fade" id="CommonProblem_a">
								                <!-- 表格开始 -->
								                <!--ajax  常见问题文档  -->
								                <table cellspacing="0" cellspadding="0" id="table2" style="min-height:150px;">
								                </table>
								            </div><!-- 软件开发常见问题库 end -->
								                

								            <!-- 软件项目实施进度 -->
								            <div role="tabpanel" class="tab-pane fade in active" id="Manage_a">
								                <!-- 表格开始 -->
								                <table id="table3" class="gl_table_style">
								            		<thead>
								            			<tr>
								            				<th>序号</th>
								            				<th>文件类型</th>
								            				<th class="TitleName">文件名称&nbsp;<span class="glyphicon glyphicon-sort-by-attributes" aria-hidden="true"></span></th>
								            				<th>下载</th>
								            				<th style="display:none;">删除数据</th>
								            			</tr>
								            		</thead>
								            		<tbody>
								            			 <c:forEach var="orderInfo" items="${manual}" varStatus="status">
								            				<c:if test="${status.index>0}">
								            					<tr>
								            						<td class="Serial" value="${orderInfo['ID']}">${status.index+(currentPage-1)*10}</td>
								            					    <td><span class="glyphicon glyphicon-file" aria-hidden="true" value="${orderInfo['ID']}"></span></td>
								            						<td  class="ManualWest" title="${orderInfo['FileName']}">${orderInfo['FileName']}</td>
								            						<td><span class="glyphicon glyphicon-save export" aria-hidden="true"></span></td>
								            					</tr>
								            				</c:if>
								            			</c:forEach> 
								            		</tbody>
								            	</table>
								                <!-- 表格结束 -->
								            </div><!-- 软件项目实施进度 end -->

											<!-- 产品发布记录 -->
											<div role="tabpanel" class="tab-pane fade" id="VersionManagement_a">
												<div class="VersionManagement_div">
													<div class="VersionManagement_div_tit">
														<div class="VersionManagement_div_tit_l">
															<div class="VersionManagement_div_tit_add">产品发布记录<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></div>
															<div class="VersionManagement_div_tit_setting"><select id="projectname_select" class="form-control"><option value="futureC_T1">futureC T1</option>
															<option value="futureD_T1">futureD T1</option>
															<option value="cfChicken_T1">冲锋鹅 T1</option>
															<option value="cfChicken_T2">冲锋鹅 T2</option>
															<option value="EUCP_T1">EUCP T1</option>
															</select>&nbsp;<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyNpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMTQwIDc5LjE2MDQ1MSwgMjAxNy8wNS8wNi0wMTowODoyMSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIChNYWNpbnRvc2gpIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOkY2NzAxQTZGQTM5NTExRThCQTFERDU3NTMwNDhCREIzIiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOkY2NzAxQTcwQTM5NTExRThCQTFERDU3NTMwNDhCREIzIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6RjY3MDFBNkRBMzk1MTFFOEJBMURENTc1MzA0OEJEQjMiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6RjY3MDFBNkVBMzk1MTFFOEJBMURENTc1MzA0OEJEQjMiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz4a+uraAAACiElEQVR42uyXTUhUURiG74SSposK+qEoodpFkIm0aFeYhihKugqkjQtdtIqsXERI9EPgIlIqCSHMjZRpA7OqXRtJhSBqU+SirQn2LzM93/AeuFzuNKP3NrOwAw/nzr1n5n3Pd777nTOJTCbjlbJt8ErcSm6grG/yp/VNcB0OF0FzHi7dbNuYchEw8WSRxD3pJJl4UzYCcE1GfsMbWIBP/0C4BvbCISiXbsoM1PpCU68BHTBVyK8SyrxjmG0r3YSEZ6RT65YgoXEfIK1BNrg1jmkHxNPS8Zyu/y14B11xmggR75JOztdwLC4TYeIs11ghdcAG9ep6TSYC4tZ6wsT/VojuhZg4WaB4Q0C8F/H7a6mEwwETk3A8j7g9fxYQH45Siv0mKuF5LhMST2qcp+9Ncb8y6l6Q1wQiJ6yoQIVuTavovIWPPO+IuhnlNKGZT/vCbhnfAhehCnbAYBy7YZiJG+pdmGdhq17l07Ad5iwyGC0L3Q1X+Xq7hBqSaF/g+WOSbon+kW955lR2B7juj2rAmViBW7AZMiqrL2EEkf30o/AZzmjTqdeSHIMXcRxIHsA2OOjbSzo1+1Mwrggd4J7V/nMasy/OE9GK6vqyPj9k9tX0T6BZW/p77u1WrniKSuQl8DfL+D0q31Z+u5nxoAy4HLhCdxTuwHm4HKcBOw98QaRHM67j2g4IjfAVXukEtGjijP2lI2B8BtQW1VvStcMmff6uXFgw8Vx1wP0x2BnBgM12QMc6u74Nd/XMEvOqb6zTyeomLjz98Zr+iL48GpYoq2jV6l1iboFv4OK+C86qas4SlTpbgn5tInazu0gn47R0s0uQUtbOF0ncdJrd/4LE//+G697AHwEGAHrKzjKxiXhoAAAAAElFTkSuQmCC" alt="邮箱设置" class="mailSet_open"></div>
														</div>
														<div class="VersionManagement_div_tit_r">
															<ul class="nav nav-tabs" role="tablist">
															    <li role="presentation" class="active actived" data-projectname="futureC_T1"><a href="#futureC_T1_a" aria-controls="futureC_T1_a" role="tab" data-toggle="tab" data-iprojectname="futureC_T1"><img src="" alt="futureC T1" data-isrc="image/softwareDocument/futureC_T1_windows.png"><br>futureC T1</a></li>
															    <li role="presentation" data-projectname="futureD_T1"><a href="#futureD_T1_a" aria-controls="futureD_T1_a" role="tab" data-toggle="tab" data-iprojectname="futureD_T1"><img src="" alt="futureD T1" data-isrc="image/softwareDocument/futureD_T1_windows.png"><br>futureD T1</a></li>
															    <li role="presentation" data-projectname="cfChicken_T1"><a href="#cfChicken_T1_a" aria-controls="cfChicken_T1_a" role="tab" data-toggle="tab" data-iprojectname="cfChicken_T1"><img src="" alt="冲锋鹅 T1" data-isrc="image/softwareDocument/cfChicken_T1_windows.png"><br>冲锋鹅 T1</a></li>
															    <li role="presentation" data-projectname="cfChicken_T2"><a href="#cfChicken_T2_a" aria-controls="cfChicken_T2_a" role="tab" data-toggle="tab" data-iprojectname="cfChicken_T2"><img src="" alt="冲锋鹅 T2" data-isrc="image/softwareDocument/cfChicken_T2_windows.png"><br>冲锋鹅 T2</a></li>
															    <li role="presentation" data-projectname="EUCP_T1"><a href="#EUCP_T1_a" aria-controls="EUCP_T1_a" role="tab" data-toggle="tab" data-iprojectname="EUCP_T1"><img src="" alt="EUCP T1" data-isrc="image/softwareDocument/EUCP_T1_windows.png"><br>EUCP T1</a></li>
															</ul>
														</div>
													</div>
													<div class="VersionManagement_div_body">
														<div class="tab-content">
															<div role="tabpanel" class="tab-pane fade in active" id="futureC_T1_a"></div>
															<div role="tabpanel" class="tab-pane fade" id="futureD_T1_a"></div>
															<div role="tabpanel" class="tab-pane fade" id="cfChicken_T1_a"></div>
															<div role="tabpanel" class="tab-pane fade" id="cfChicken_T2_a"></div>
															<div role="tabpanel" class="tab-pane fade" id="EUCP_T1_a"></div>
														</div>
													</div>
												</div>
											</div><!-- 产品发布记录 end -->

								        </div><!-- tab-content end -->

										<c:set var="queryUrl" value="SoftwareDocument?queryType=${queryType}&catalog=${catalog}&Type=${type}&content=${content}&currentPage=">
										</c:set>
							 			<div id="page">
											<div class="pageInfo">
												当前是第&nbsp;<span id="currentPage">${currentPage}</span>&nbsp;页,&nbsp;总计&nbsp;<span id="allPage">${pageCounts}</span>页
											</div>
											<div class="changePage">
												<input type="button" class="btn btn-primary" value="首页" id="fistPage" name="fistPage" onclick="FistPage('${queryUrl}')">
												<input type="button" class="btn btn-primary" value="上一页" id="upPage" onclick="UpPage('${queryUrl}${currentPage-1 }')">
												<input type="button" class="btn btn-primary" value="下一页" id="nextPage" onclick="NextPage('${queryUrl}${currentPage+1 }')"> 跳到第 <span id="jumpNumber" name="jumpNumber" class="jumpNumber" style="display:inline-block;min-width: 30px;height:24px; color: #000;border:1px solid  rgb(84, 84, 84);background-color:#fff;text-align:center;line-heigth:24px" contenteditable="true" onkeyup="change()"></span> 页 <input type="button" class="btn btn-primary" value="GO" id="Gotojump" name="Gotojump" onclick="PageJump('${queryUrl}')" >
												<input type="button" class="btn btn-primary" value="尾页" id="lastPage" name="lastPage" onclick="LastPage('${queryUrl}')" >
											</div> 
										</div> 
										<div class="clear_float"></div>

								    </div><!-- tab_wrapper end -->
								</div><!-- 分页标签主体end -->
							</div><!-- show_in end -->

						</div>
					</div>     
				</div>
				<!-- </div> -->
				<!-- ***************编辑上传文件区域****************** -->
				<div class="MailBar_cover_color"></div>
			 	<div class="MailBar" style="display:none;">
					<div class="operate_title">上传文件</div>
					<div class="MailBar_close">关闭</div>
					<!-- 根据年份上传 -->
					<c:if test="${catalog=='VisitReport'}">
				 		<span class="uptitle">选择年份:</span>
				 		<select class="upyear">
				 			<option value="2010">2010</option>	
				 			<option value="2011">2011</option>	
				 			<option value="2012">2012</option>	
				 			<option value="2013">2013</option>
				 			<option value="2014">2014</option>
				 			<option value="2015">2015</option>	
				 			<option value="2016">2016</option>	
				 			<option value="2017">2017</option>	
				 			<option value="2018">2018</option>
				 		</select>
			 		</c:if>
			 			<!--不影响使用的代码  -->
							<div class="SoftType" style="display:none">
									<span class="uptitle">文档类型:</span>
									<select class="uptype">
							 			<option value="技术攻关">技术攻关</option>	
							 			<option value="技术分享">技术分享</option>	
							 			<option value="技术更新">技术更新</option>	
							 			<option value="BUG修复">BUG修复</option>
							 		</select>
							</div>
							<a href="" class="box">
								<input type="file" name="file" id="Mail_fileToUpload"   value="添加附件"  class="up"/>
								<span class="intitle">点击上传文档、压缩包</span>
								<span class="mask"></span>
							</a>
							<div class="NameFormat">文件名格式：XX问题分析报告-YYYYMMDD-作者.doc</div>
							<div class="PwdCont">邮箱密码：<input type="password" name="password"  class="PwdInput"></div>
						<!-- 修改结束位置 -->
						<input type="button" name="button" value="上传" class="bToggle" id="Mail_Send">
					</div>
				<!-- ***************编辑批量上传文件区域****************** -->
				<div class="MailBar_cover_color"></div>
			 	<div class="MailBar_All" style="display:none;">
					<div class="operate_title">上传文件</div>
					<div class="MailBar_close">关闭</div>
					<!-- 根据年份上传 -->
					<c:if test="${catalog=='VisitReport'}">
				 		<span class="uptitle">选择年份:</span>
				 		<select class="upyear">
				 			<option value="2010">2010</option>	
				 			<option value="2011">2011</option>	
				 			<option value="2012">2012</option>	
				 			<option value="2013">2013</option>
				 			<option value="2014">2014</option>
				 			<option value="2015">2015</option>	
				 			<option value="2016">2016</option>	
				 			<option value="2017">2017</option>	
				 			<option value="2018">2018</option>	
				 		</select>
				 	</c:if>
						<form  enctype="multipart/form-data" id="f">
							<!--不影响使用的代码  -->
				   		<!-- 	<input type="file" name="file" id="Mail_fileToUpload" multiple="multiple"  value="添加附件" /> -->
				   			<div class="SoftType">
									<span class="uptitle">文档类型:</span>
									<select class="uptype">
							 			<option value="技术攻关">技术攻关</option>	
							 			<option value="技术分享">技术分享</option>	
							 			<option value="技术更新">技术更新</option>	
							 			<option value="BUG修复">BUG修复</option>
							 		</select>
							</div>
				   			<!-- 改样式后的代码优化 -->
							<a href="" class="box">
								<input type="file" name="file" id="Mail_fileToUpload" multiple="multiple"  value="添加附件"  class="up"/>
								<span class="intitle">点击上传文档、压缩包</span>
								<span class="mask"></span>
							</a>
							<!-- 修改结束位置 -->
							<div class="NameFormat">文件名格式：XX问题分析报告-YYYYMMDD-作者.doc</div>
				   			<div class="PwdCont">邮箱密码：<input type="password" name="password" style="display:none"><input type="password" name="password"  class="PwdInput"></div>
							<input type="button" name="button" value="批量上传" class="bToggle" id="All_Send" > 
			     			<input type="text" name="Area" value="${area}" class="bToggle" id="All_Send2" style="display:none;" >
			     			<input type="text" name="Year" value="${year}" class="bToggle" id="All_Send3" style="display:none;">
			     			<input type="text" name="Type" value="${type}" class="bToggle" id="All_Send4" style="display:none;" >
			     			<input type="text" name="Time" value="" class="bToggle" id="All_Send5" style="display:none;">
						</form>
						
				</div>  
				<a target="_blank" id="WindowOpen"></a>
				<div class="mailSetting_div_cover"></div>
				<div class="mailSetting_div" id="mailSetting_classify" data-iiclassify="documentUpload">
					<!-- 下面是动态插入内容 -->
					<!-- 上面是动态插入内容 -->
				</div>

				<!-- 添加产品发布记录 -->
				<div class="MailBar_cover_color22"></div>
				<div class="VersionManagement">
					<div class="VersionManagement_tit">
						添加产品发布记录<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
					</div>
					<div class="VersionManagement_body">
						<div class="VersionManagement_body_in">
				    		<fieldset><legend>基本信息</legend>
				    			<div class="container-fluid">
				    				<div class="row">
				    					<div class="col-md-6 col-lg-6">
				    						<div class="form-horizontal">
				    						    <!-- 一个form-group就是一个整体 -->
				    						    <div class="form-group">
				    						        <label for="VM_add_ProjectName" class="col-sm-3 control-label">项目名称</label>
				    						        <div class="col-sm-9">
				    						            <select id="VM_add_ProjectName" class="form-control">
				    						            	<option value="" disabled>请选择</option>
						    						        <option value="futureC_T1">futureC T1</option>
															<option value="futureD_T1">futureD T1</option>
															<option value="cfChicken_T1">冲锋鹅 T1</option>
															<option value="cfChicken_T2">冲锋鹅 T2</option>
															<option value="EUCP_T1">EUCP T1</option>
				    						            </select>
				    						        </div>
				    						    </div>
				    						    <div class="form-group">
				    						        <label for="VM_add_BoardingTime" class="col-sm-3 control-label">登记时间</label>
				    						        <div class="col-sm-9">
				    						            <input type="date" class="form-control" id="VM_add_BoardingTime">
				    						        </div>
				    						    </div>
				    						    <div class="form-group">
				    						        <label for="VM_add_password" class="col-sm-3 control-label">邮箱密码</label>
				    						        <div class="col-sm-9">
				    						            <input type="password" class="form-control" id="VM_add_password"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
				    						        </div>
				    						    </div>
				    						</div>
				    					</div><!-- col-md-6 end -->
				    					<div class="col-md-6 col-lg-6">
				    						<div class="form-horizontal">
				    						    <!-- 一个form-group就是一个整体 -->
				    						    <div class="form-group">
				    						        <label for="VM_add_Version" class="col-sm-3 control-label">版本号</label>
				    						        <div class="col-sm-9">
				    						            <input type="text" class="form-control" id="VM_add_Version" placeholder="必填项">
				    						        </div>
				    						    </div>
				    						    <div class="form-group">
				    						        <label for="VM_add_Registrant" class="col-sm-3 control-label">登记人</label>
				    						        <div class="col-sm-9">
				    						            <input type="text" class="form-control" id="VM_add_Registrant">
				    						        </div>
				    						    </div>
				    						</div>
				    					</div><!-- col-md-6 end -->
				    				</div>
				    			</div><!-- container-fluid end -->
				    		</fieldset>
				    		<fieldset><legend>更新内容&nbsp;<span class="glyphicon glyphicon-plus-sign add_line_p" aria-hidden="true"></span></legend>
				    		<div class="container-fluid appendp_p_div">
				    		</div>
				    		</fieldset>
						</div>
					</div>
					<div class="VersionManagement_foot">
						<div class="VersionManagement_foot_in">
							<input type="button" value="提交" class="btn btn-success" id="VersionManagement_submit">
							<input type="button" value="取消" class="btn btn-warning" id="VersionManagement_cancel">
						</div>
					</div>
				</div>

				<div class="gotoPageTop">
					<span class="glyphicon glyphicon-chevron-up" aria-hidden="true"></span>
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
	<div class="loading_div_g_div">
	    <img src="image/loading/Spinner-1s-200px.gif" alt="loading。。。">
	</div>
</body>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<!-- <script
  src="https://code.jquery.com/jquery-1.12.4.min.js"
  integrity="sha256-ZosEbRLbNQzLpnKIkEdrPv7lOy9C27hHQ+Xp8a4MxAQ="
  crossorigin="anonymous"></script> -->
<script src="js/libs/integrationLibs/jquery-cookie-ajaxfile-77692a8173.min.js"></script>
<script src="js/libs/bootstrap.min.js"></script>

<!-- delete -->
<!-- <script src="js/libs/integrationLibs/msgbox_all-56b86b3095.min.js"></script> -->
<!-- <script src="js/libs/integrationLibs/awesomplete-mailSetting-ddfa04e4f1.min.js"></script> -->
<!-- <script src="js/global/responseLoading.js"></script> -->
<!-- <script src="js/SoftwareDocument.js"></script> -->
</html>