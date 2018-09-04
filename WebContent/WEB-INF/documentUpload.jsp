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
<title>文档上传</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" />
<link rel="stylesheet" type="text/css" href="css/libs/bootstrap-progressBar.min.css" />
<link rel="stylesheet" type="text/css" href="plugins/webuploader/webuploader.css" />
<link rel="stylesheet" type="text/css" href="css/documentUpload.css">
<style>
	#bodyBottom {
		z-index: 11 !important;
	}
</style>
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
				<!-- <input type="text" value="" name="classify1"> 
				<input type="text" value="" name="classify2"> 
				<input type="text" value="" name="parameter1"> 
				<input type="text" value="" name="parameter2"> -->
			  </div> 
			<!-- 整个下部分的显示区域  -->
			 <div class="document">
			 <!-- 文档部分 -->
			        <div class="catalog lf">
				       	<div id="catalog_list">
				        	 <!--1、分类信息-->
				            <ul id="catalog_first" class="lf">
				            <c:set var="authoritiy" />
				            <c:if test="${otherDocument}">
				                <li class="loader_Document">
				                    <a href="DocumentUpload?Area=south&catalog=Manual&Year=2018&Type=Cascade&queryType=common">装机文档</a>
				                    <!-- 弹出框容器 -->
				                    <div id="sub-cate-box">
				                        <!-- 1、子分类内容 -->
				                        <div id="sub-cate-items" class="lf">
				                            <ul>
				                                <li class="title MV_CFCs"><a href="DocumentUpload?Area=south&catalog=Manual&Year=2018&Type=Cascade&queryType=common">装机实施手册</a></li>
				                                <li class="title MV_report_daily"><a href="DocumentUpload?Area=south&catalog=Log&Year=2018&Type=Cascade&queryType=common">装机报告和装机日志</a></li>
				                                <li class="title MV_picture"><a href="DocumentUpload?Area=south&catalog=Image&Year=2018&Type=Cascade&queryType=common">装机图片</a></li>
				                                <li class="title MV_picture"><a href="DocumentUpload?Area=south&catalog=Environment&Year=2018&Type=Cascade&queryType=common">装机环境确认表</a></li>
				                            </ul>
				                        </div>
				                    </div>
				                </li>
				                <li class="serve"><a href="DocumentUpload?Area=south&catalog=Achieve&Year=2018&Type=Cascade&queryType=common">服务请求书和服务完成报告</a></li>
				                <li class="templet">
				                    <a href="DocumentUpload?Area=south&catalog=Original&Year=2018&Type=Cascade&queryType=common">原厂文档资料</a>
				                    <!-- 弹出框容器 -->
				                    <div id="sub-cate-box2">
				                    <span style="display:none" class="otype">${type}</span>	
				                        <!-- 1、子分类内容 -->
				                        <div id="sub-cate-items2" class="lf">
				                            <ul>
				                                <li class="title"><a href="DocumentUpload?Area=south&catalog=Original&Year=2018&Type=Cascade&queryType=common">Cascade</a></li>
				                                <li class="title"><a href="DocumentUpload?Area=south&catalog=Original&Year=2018&Type=Keysight&queryType=common">Keysight</a></li>
				                                <li class="title"><a href="DocumentUpload?Area=south&catalog=Original&Year=2018&Type=EOULU&queryType=common">EOULU</a></li>
				                                <li class="title"><a href="DocumentUpload?Area=south&catalog=Original&Year=2018&Type=Maury&queryType=common">Maury</a></li>
				                                <li class="title"><a href="DocumentUpload?Area=south&catalog=Original&Year=2018&Type=Other&queryType=common">其他</a></li>
				                            </ul>
				                        </div>
				                    </div>
				                </li>
				                <li class="form"><a href="DocumentUpload?Area=south&catalog=Sales&Year=2018&Type=Sales&queryType=common">EOULU销售人员标准表格</a></li>
				               </c:if>
				             <!-- <li class="software"><a href="DocumentUpload?Area=south&catalog=Software&Year=2018&Type=Software&queryType=common">软件部文档</a>
				               -->		
				              <%-- <c:if test="${software}">
				            	<li class="software"><a href="DocumentUpload?Area=south&catalog=Software&Year=2018&Type=Software&queryType=common">软件部文档</a>
				             </c:if> --%>
				             <c:if test="${otherDocument}">
				              	<li class="visitReport"><a href="DocumentUpload?&catalog=VisitReport&Year=2018&currentPage=1&queryType=common">拜访报告</a>	
				                	<!-- 弹出框容器 -->
				                    <div id="sub-cate-box3">
				                        <!-- 1、子分类内容 -->
				                        <div id="sub-cate-items3" class="lf">
				                            <ul>
				                                <li class="title software_actualize"><a href="DocumentUpload?Area=south&catalog=Software&Year=2018&Type=Software&queryType=common">EUCP软件实施管理文档</a></li>
				                            </ul>
				                        </div>
				                    </div>
				                </li>
			                	<li class="visitReport"><a href="DocumentUpload?&catalog=InstallationPackage&Year=2018&currentPage=1&queryType=common">软件安装</a>	
			                  </li>
				             </c:if>
				            </ul>
				        </div>
			        </div>
			        <!-- 显示装机实施手册部分 -->
			        <div class="show rt" id="show_MV_CFCs" style="min-width: 430px;position: relative;">
	        			<div class="changeBox" style="position:relative;top:-53px;left:0;z-index:3;background:url(image/wendang1.png);width:533px;height:50px;display:none;line-height:50px;font-size:14px;">
							<span class="Norms" style="position:absolute;top:0;left:0;background:url(image/bg3.png);width:183px;height:42px;display:block;text-align:center;cursor:pointer;">软件开发规范</span>
							<span class="CommonProblem" style="position:absolute;top:0;left:175px;background:url(image/bg3.png);width:183px;height:42px;display:block;text-align:center;cursor:pointer;">软件开发常见问题库</span>
							<span class="Manage" ID="currentTab"  style="position:absolute;top:0;left:350px;background:url(image/bg2.png);width:183px;height:42px;cursor:pointer;display:block;text-align:center;">软件项目实施进度</span>
						</div>
			        	<div id="area">
					        <c:choose>
					        	<c:when test="${catalog !='VisitReport' && catalog !='InstallationPackage'}">
					                <ul>
					                    <li class="south"><a href="DocumentUpload?Area=south&catalog=Manual&Year=2018&Type=Cascade&queryType=common" target="_self" class="current">南方</a></li>
					                    <li class="north"><a href="DocumentUpload?Area=north&catalog=Manual&Year=2018&Type=Cascade&queryType=common" >北方</a></li>
					                    <li class="southwest"><a href="DocumentUpload?Area=southwest&catalog=Manual&Year=2018&Type=Cascade&queryType=common">西南方</a></li>
					                </ul>
					            </c:when>
					         </c:choose>
			          	</div>
			          	<span id="ManualArea" style="display:none;">${area}</span>
			            <span id="catalog" style="display:none;">${catalog}</span>
			            <span id="ManualAreaYear" style="display:none;">${year}</span>
			            <span id="ManualAreaType" style="display:none;">${type}</span>
			            <!-- 装机实施手册 ——南方-->
			            <div class="show_area south_area">
			            	<div id="year_area" style="display:none;" >
						           <c:choose>
						        	<c:when test="${area=='south' }">
						        		<ul>
						                    <li class="">南方年份：</li>
						                    <li class="year2014"><a href="DocumentUpload?Area=south&catalog=Log&Year=2014&Type=Cascade&queryType=common" class="current">2014</a></li>
						                    <li class="year2015"><a href="DocumentUpload?Area=south&catalog=Log&Year=2015&Type=Cascade&queryType=common">2015</a></li>
						                    <li class="year2016"><a href="DocumentUpload?Area=south&catalog=Log&Year=2016&Type=Cascade&queryType=common">2016</a></li>
						                    <li class="year2017"><a href="DocumentUpload?Area=south&catalog=Log&Year=2017&Type=Cascade&queryType=common">2017</a></li>
						                    <li class="year2018"><a href="DocumentUpload?Area=south&catalog=Log&Year=2018&Type=Cascade&queryType=common">2018</a></li>
						                </ul>
						        	</c:when>
						        	<c:when test="${area=='north' }">
						        		<ul>
						                    <li class="">北方年份：</li>
						                    <li class="year2014"><a href="DocumentUpload?Area=north&catalog=Log&Year=2014&Type=Cascade&queryType=common" class="current">2014</a></li>
						                    <li class="year2015"><a href="DocumentUpload?Area=north&catalog=Log&Year=2015&Type=Cascade&queryType=common">2015</a></li>
						                    <li class="year2016"><a href="DocumentUpload?Area=north&catalog=Log&Year=2016&Type=Cascade&queryType=common">2016</a></li>
						                    <li class="year2017"><a href="DocumentUpload?Area=north&catalog=Log&Year=2017&Type=Cascade&queryType=common">2017</a></li>
						                    <li class="year2018"><a href="DocumentUpload?Area=north&catalog=Log&Year=2018&Type=Cascade&queryType=common">2018</a></li>
						                </ul>
						        	</c:when>
						        	<c:when test="${area=='southwest' }">
						        		<ul>
						                    <li class="">西南方年份：</li>
						                    <li class="year2014"><a href="DocumentUpload?Area=southwest&catalog=Log&Year=2014&Type=Cascade&queryType=common" class="current">2014</a></li>
						                    <li class="year2015"><a href="DocumentUpload?Area=southwest&catalog=Log&Year=2015&Type=Cascade&queryType=common">2015</a></li>
						                    <li class="year2016"><a href="DocumentUpload?Area=southwest&catalog=Log&Year=2016&Type=Cascade&queryType=common">2016</a></li>
						                    <li class="year2017"><a href="DocumentUpload?Area=southwest&catalog=Log&Year=2017&Type=Cascade&queryType=common">2017</a></li>
						                    <li class="year2018"><a href="DocumentUpload?Area=southwest&catalog=Log&Year=2018&Type=Cascade&queryType=common">2018</a></li>
						                </ul>
						        	</c:when>
						        </c:choose>
				            </div>
				            <!-- 页面新增的年份搜索部分 -->
				            <%-- <span style="display:none" class="catalog">${catalog}</span> --%>
				            <c:if test="${catalog=='VisitReport'}">
				            	<div class="timeSearch">
					            	<c:if test="${year =='ALL'}">
						            	<span class="searchtitle">年份：</span>
						            	<span class="allTime bg">全部</span>
						            	<div class="searchYear">
						            		<span class="turnLeft"></span>
						            		<span class="year1">2013</span>
						            		<span class="year2">2014</span>
						            		<span style="display:none" class="syear">${year}</span>
						            		<span class="year3">2015</span>
						            		<span class="turnRight"></span>
						            	</div>
					            	</c:if>
					            	<c:if test="${year !='ALL'}">
					            		<span class="searchtitle">年份：</span>
						            	<span class="allTime">全部</span>
						            	<div class="searchYear">
						            		<span class="turnLeft"></span>
						            		<span class="year1">${year+1}</span>
						            		<span class="year2 yearBg">${year}</span>
						            		<span style="display:none" class="syear">${year}</span>
						            		<span class="year3">${year-1}</span>
						            		<span class="turnRight"></span>
						            	</div>
					            	</c:if>
				           		 </div>
				            </c:if>
				            <c:if test="${catalog =='VisitReport' }">
					            <form style=" float:right;min-width: 138px;">
					             	<div class="choose" style="margin-left:0;margin-right:0;">
										<input type="button" class="bToggle writeEmail" value="上传" >
										<input type="button" class="bToggle All_upload" id="All_upload" value="批量上传" style="margin-right:0;">
									</div>
								</form> 
					            <form action="DocumentUpload" method="post" id="search_box"  style="position: absolute;clear:both;    right: 27%;">
						            	<%-- <input  style="display:none" name="Year" value="${year}"> --%>
						            	<%-- <input style="display:none" name="catalog" value="${catalog}" class="catalog"> --%>
					            	<input type="text" placeholder="输入关键字查询" class="tsearch" name="content" value="" style="min-width:150px;right: 27%" ><span class="ysearch" style="right: 27%"></span>
					            	<span style="display:none" class="scatalog">${catalog}</span>
					            	<span style="display:none" class="scontent">${content}</span>
				            	</form>
			            	</c:if>
			            	<c:if test="${catalog !='VisitReport' }">
								<form style="width: 18%; float:left;min-width: 160px;">
					             	<div class="choose" style="margin-left: 0;">
										<input type="button" class="bToggle writeEmail" value="上传" >
										<input type="button" class="bToggle All_upload" id="All_upload" value="批量上传" style="margin-left:6px;">
									</div>
								</form> 
								<form action="DocumentUpload" method="post" id="search_box" style="position: relative;">
					            	<%-- <input  style="display:none" name="Year" value="${year}"> --%>
					            	<%-- <input style="display:none" name="catalog" value="${catalog}" class="catalog"> --%>
					            	<span class="ysearch" style="width:2%;"></span><input type="text" placeholder="输入关键字查询" class="tsearch" name="content" value="" >
					            	</form>
					            	<span style="display:none" class="scatalog">${catalog}</span>
					            	<span style="display:none" class="sarea">${area}</span> 
					            	<span style="display:none" class="scontent">${content}</span>
							</c:if>
							<div class="bigFileUploadBtn">
								<input type="button" class="bToggle" value="软件上传" >
							</div>
							<table border="1" cellspacing="0" cellspadding="0" id="table1">
									<tr>
										<td style="width:15%;">序号</td>
										<td style="display: none;">文件类型</td>
										<td style="width:65%;cursor:pointer;"  title="排序"  class="TitleName">文件名称 </td>
										<td style="width:20%;">下载</td>
										<td style="display:none;">删除数据</td>
									</tr>
									 <c:forEach var="orderInfo" items="${manual}" varStatus="status">
										<c:if test="${status.index>0}">
											<tr>
												<td class="Serial" value="${orderInfo['ID']}">${status.index+(currentPage-1)*10}</td>
											    <td style="display: none;"><i class="fa fa-file-zip-o contract-edit" value="${orderInfo['ID']}"></i></td>
												<td class="ManualWest" title="${orderInfo['FileName']}">${orderInfo['FileName']}</td>
												<td><span class="export"></span></td>
												<td style="display:none;"></td>
											</tr>
										</c:if>
									</c:forEach> 
							</table> 
							<!--ajax  常见问题文档  -->
							<table cellspacing="0" cellspadding="0" id="table2" style="display:none;clear: both;min-height:150px;">
								<!-- <div style="width:100%;;height:100%;font-size:25px;color:#00aeef;text-align:center;line-height:150px;">无上传文件！</div> -->
							</table>
						 	<c:choose>
									<c:when test="${queryType == 'common'}">
										<c:set var="queryUrl"
										value="DocumentUpload?Area=${area}&queryType=${queryType}&catalog=${catalog}&Year=${year}&Type=${type}&currentPage=">
										</c:set>
									</c:when>
									 <c:when test="${catalog == 'VisitReport'}">
										<c:set var="queryUrl"
										value="DocumentUpload?queryType=${queryType}&catalog=${catalog}&Year=${year}&Type=${type}&content=${content}&currentPage=">
										</c:set>
									</c:when>
									<c:when test="${catalog == 'Manual' || catalog =='Achieve'}">
										<c:set var="queryUrl"
										value="DocumentUpload?Area=${area}&queryType=${queryType}&catalog=${catalog}&content=${content}&Type=${type}&currentPage=">
										</c:set>
									</c:when>
									<c:when test="${catalog == 'Log'}">
										<c:set var="queryUrl"
										value="DocumentUpload?Area=${area}&queryType=${queryType}&catalog=${catalog}&Year=${year}&content=${content}&Type=${type}&currentPage=">
										</c:set>
									</c:when>
									<c:when test="${catalog == 'Environment'}">
										<c:set var="queryUrl"
										value="DocumentUpload?Area=${area}&queryType=${queryType}&catalog=${catalog}&Year=${year}&content=${content}&Type=${type}&currentPage=">
										</c:set>
									</c:when>
									<c:when test="${catalog == 'InstallationPackage'}">
										<c:set var="queryUrl"
										value="DocumentUpload?queryType=${queryType}&catalog=${catalog}&content=${content}&Type=${type}&currentPage=">
										</c:set>
									</c:when>
									<c:otherwise>
										<c:set var="queryUrl"
										value="DocumentUpload?queryType=${queryType}&catalog=${catalog}&Type=${type}&content=${content}&currentPage=">
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
										name="Gotojump" onclick="PageJump('${queryUrl}')" > <input
										type="button" class="bToggle" value="尾页" id="lastPage"
										name="lastPage" onclick="LastPage('${queryUrl}')" >
								</div> 
							</div> 
						</div>
					</div>
			  </div>     
		</div>
	</div>
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
			<!-- <input type="file" name="file" id="Mail_fileToUpload"   value="添加附件" />
			<!-- 改样式后的代码优化 -->
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
					<input type="file" name="file" id="Mail_fileToUpload" value="添加附件"  class="up"/>
					<span class="intitle">点击上传文档、压缩包</span>
					<span class="mask"></span>
				</a>
				<div class="NameFormat">文件名格式：XX问题分析报告-YYYYMMDD-作者.doc</div>
				<div class="PwdCont">邮箱密码：<input type="password" name="password" style="display:none"><input type="password" name="password" class="PwdInput"></div>
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
			<form enctype="multipart/form-data" id="f">
				<!--不影响使用的代码  -->
	   		<!--<input type="file" name="file" id="Mail_fileToUpload" multiple="multiple"  value="添加附件" /> -->
	   			<!-- 改样式后的代码优化 -->
				<a href="" class="box">
					<input type="file" name="file" id="Mail_fileToUpload2" multiple="multiple"  value="添加附件"  class="up"/>
					<span class="intitle">点击上传文档、压缩包</span>
					<span class="mask"></span>
				</a>
				<!-- 修改结束位置 -->
				<div class="NameFormat">文件名格式：XX问题分析报告-YYYYMMDD-作者.doc</div>
	   			<div class="PwdCont">邮箱密码：<input type="password" name="password" style="display:none"><input type="password" name="password" class="PwdInput"></div>
				<input type="button" name="button" value="批量上传" class="bToggle" id="All_Send" > 
     			<input type="text" name="Area" value="${area}" class="bToggle" id="All_Send2" style="display:none;">
     			<input type="text" name="Year" value="${year}" class="bToggle" id="All_Send3" style="display:none;">
     			<input type="text" name="Type" value="${type}" class="bToggle" id="All_Send4" style="display:none;">
     			<input type="text" name="Time" value="" class="bToggle" id="All_Send5" style="display:none;">
     			<!-- <input type="text" name="type" value="" class="bToggle" id="All_Send6" style="display:none;"> -->
     			<input type="text" name="DocuType" value="" class="bToggle" id="All_Send6" style="display:none;">
			</form>
	</div>  
	<a target="_blank" id="WindowOpen"></a>
	<!-- 分片上传 -->
	<div class="upload_bgcover"></div>
	<div class="upload_wrapper">
		<div class="upload_wrapper_tit">
			<div class="upload_wrapper_tit_l">文件上传</div>
			<div class="upload_wrapper_tit_r">关闭</div>
		</div>
		<div class="upload_wrapper_body">
			<div id="uploader" class="wu-example">
			    <!--用来存放文件信息-->
			    <div id="thelist" class="uploader-list"></div>
			    <div class="btns">
			        <div id="picker">选择文件</div>
			        <button id="ctlBtn" class="btn btn-default">开始上传</button>
			    </div>
			</div>
		</div>
	</div>
</body>
<!-- <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script> -->
<script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script>
<script src="plugins/cookie/jquery.cookie.js"></script>
<script src="js/ajaxfileupload.js" ></script>
<script src="js/msgbox.js"></script>
<script src="js/msgbox_unload.js"></script>
<script type="text/javascript" src="plugins/webuploader/webuploader.min.js"></script>
<script src="js/documentUpload.js"></script>
<script>
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

/****************** 跳页 **********************/
function FistPage(arg) {
	var content = $(".tsearch").val();
	if($(".changeBox").css("display")!="none"){
		searchOrjump(1,content,sortFlag);
	}
	else{
		window.location.href = arg + "1";
	}
}
function UpPage(arg) {
	var content = $(".tsearch").val();
	if($(".changeBox").css("display")!="none"){
		var page = parseFloat($("#currentPage").text())-1;
		searchOrjump(page,content,sortFlag);
	}
	else{
		window.location.href = arg;
	}
}
function NextPage(arg) {
	var content = $(".tsearch").val();
	if($(".changeBox").css("display")!="none"){
		var page = parseFloat($("#currentPage").text())+1;
		searchOrjump(page,content,sortFlag);
	}
	else{
		window.location.href = arg;
	}
	
}
function PageJump(arg) {
	var content = $(".tsearch").val();
	var jumpNumber = document.getElementById("jumpNumber").value;
	if (jumpNumber == null || jumpNumber == 0) {
		jumpNumber = $('#currentPage').html();
	} else if (jumpNumber > parseInt($('#allPage').html())) {
		jumpNumber = $('#allPage').html();
	}
	if($(".changeBox").css("display")!="none"){
		searchOrjump(jumpNumber,content,sortFlag);
	}
	else{
		window.location.href = arg + jumpNumber;
	}
}
function LastPage(arg) {
	var content = $(".tsearch").val();
	var jumpNumber = parseInt($('#allPage').html());
	if($(".changeBox").css("display")!="none"){
		searchOrjump(jumpNumber,content,sortFlag);
	}
	else{
		window.location.href = arg + jumpNumber;
	}
}
var sortFlag = false;
//跳页或者搜索执行的ajax 函数            当前页              内容               排序          搜索状态
function searchOrjump(currentPage,content,flag){
	var queryType = $("#show_MV_CFCs").attr("searchFlag");
	console.log("queryType=="+queryType)
	if(!queryType){queryType  = "common"}
	if(flag){
		console.log("jiinini")
		if($("#currentTab").attr("class")=="Norms"){  //开发规范
			var data={
					Type:"Specification",
					currentPage:currentPage,
					queryType :queryType,
					column:"FileName",
					Reorder : "asc",
					content:content,
			}
		}
		else if($("#currentTab").attr("class")=="CommonProblem"){  //问题
			var data={
					Type:"CommonProblem",
					currentPage:currentPage,
					queryType :queryType,
					column:"FileName",
					Reorder : "asc",
					content:content,
			}
		}
		else{
			var data={
					Type:"Software",
					currentPage:currentPage,
					queryType :queryType,
					column:"FileName",
					Reorder : "asc",
					content:content,
			}
		}
	}
	else{    //不排序
		console.log("sww22222")
		if($("#currentTab").attr("class")=="Norms"){  //开发规范
			var data={
					Type:"Specification",
					currentPage:currentPage,
					/* queryType:"SingleSelect", */
					queryType : queryType,
					content:content,
			}
		}
		else if($("#currentTab").attr("class")=="CommonProblem"){  //问题
			var data={
					Type:"CommonProblem",
					currentPage:currentPage,
					queryType : queryType,
					content:content,
			}
		}
		else{
			var data={
					Type:"Software",
					currentPage:currentPage,
					queryType : queryType,
					content:content,
			}
		}
	}
	 $.ajax({
 	        type : 'get',
 	        url : 'Software',
 	        data:data,
 	        dataType:"json",
 	        success : function (data) {
 	        	console.log(data)
 	        	$("#show_MV_CFCs").attr("searchFlag",data.queryType);
 	        	if(sessionStorage.getItem("tab")){
 					var currentTab =  sessionStorage.getItem("tab");
 					if(currentTab == "Norms"){  //开发规范
 						$("#table1").show();
 		 	        	$("#table1").empty();
 		 	        	$("#table2").hide();
 		 	        	var str = '<tr>'+
							'<td style="width:15%;">序号</td>'+
							'<td style="display:none;">文件类型</td>'+
							'<td style="width:65%;cursor:pointer;" title="排序" class="TitleName">文件名称 </td>'+
							'<td style="width:20%;">下载</td>'+
							'<td style="display:none;"></td>'+
						'</tr>';
 		 	        	if( data.datas.length> 1){
 		 	        		for(var i = 1; i < data.datas.length;i++){
 		 	        			str += 
 		 	 	        			'<tr>'+
 		 								'<td class="Serial" value="'+data.datas[i].ID+'">'+((currentPage-1)*10+i)+'</td>'+
 		 								'<td style="display:none;"><i class="fa fa-file-zip-o contract-edit" ></i></td>'+
 		 								'<td class="ManualWest">'+data.datas[i].FileName+'</td>'+       
 		 								'<td><span  class="export"></span></td>'+
 		 								'<td style="display:none;"></td>'+
 		 							'</tr>'
 			 	        	}
 		 	        	}
 		 	        	$(".pageInfo #currentPage").text(data.currentPage);
 		 	        	$(".pageInfo #allPage").text(data.pageCounts);
 		 	        	$("#table1").append(str);
 		 	        	jumpDisable();
 					}
 					else{   //常见问题
 						$("#table1").hide();
 		 	        	$("#table2").empty();
 		 	        	$("#table2").show();
 		 	        	var str = '';
 		 	          	if( data.datas.length> 1){
 		 	        		for(var i = 1; i < data.datas.length;i++){
 		 	        			var ProblemCont_name = data.datas[i].FileName.split("-")[0];
 		 	        			var ProblemCont_Author = data.datas[i].FileName.split("-")[2].split(".")[0];
 		 	        			str += 
 		 	        			'<tr style="width:97%;padding:0 1.5%;">'+
 									'<td >'+
 										'<p style="cursor:pointer;" title="排序" class="ProblemNum Serial" value="'+data.datas[i].ID+'">'+((currentPage-1)*10+i)+'</p>'+
 										'<ol  class="ProblemCont">'+
 											'<li class="ProblemCont_name" >'+ProblemCont_name+'</li>'+
 											'<li class="ProblemCont_cont" >'+data.datas[i].Content+'</li>'+
 											'<li class="ProblemCont_Marking">'+
 												'<div class="ProblemCont_Author" >作者：'+ProblemCont_Author+'</div>'+
 												'<div class="ProblemCont_Time">'+data.datas[i].OperatingTime.slice(0,16)+'</div>'+			
 											'</li>'+
 										'</ol>'+
 									'</td>'+
 								'</tr>'
 			 	        	}
 		 	        	}
 		 	          	$(".pageInfo #currentPage").text(data.currentPage);
 		 	        	$(".pageInfo #allPage").text(data.pageCounts);
 		 	        	$("#table2").append(str);
 		 	        	jumpDisable();
 					}
 				}
 	        	else{    //实施文档
 	        		$("#table1").show();
 	 	        	$("#table1").empty();
 	 	        	$("#table2").hide();
 	 	        	var str = '<tr>'+
 								'<td style="width:15%;">序号</td>'+
 								'<td style="display:none;">文件类型</td>'+
 								'<td style="width:65%;cursor:pointer;"  title="排序"  class="TitleName">文件名称 </td>'+
 								'<td style="width:20%;">下载</td>'+
 								'<td style="display:none;"></td>'+
 							'</tr>';
 	 	        	for(var i = 1; i < data.datas.length;i++){
 	 	        		str += 
 	 	        			'<tr>'+
 								'<td class="Serial" value="'+data.datas[i].ID+'">'+((currentPage-1)*10+i)+'</td>'+
 								'<td style="display:none;"><i class="fa fa-file-zip-o contract-edit" ></i></td>'+
 								'<td class="ManualWest">'+data.datas[i].FileName+'</td>'+       
 								'<td><span class="export"></span></td>'+
								'<td style="display:none;"></td>'+
 							'</tr>'
 		 	        	}
 	 	        	$(".pageInfo #currentPage").text(data.currentPage);
 	 	        	$(".pageInfo #allPage").text(data.pageCounts);
 	 	        	$("#table1").append(str);
 	 	        	jumpDisable();
 	        	}
 	        	
 	        },
 	        error : function () {
 	        }
 	    })
	}

	function jumpDisable(){
		$("#fistPage,#lastPage,#upPage,#nextPage").attr('disabled', false);
		$("#fistPage,#lastPage,#upPage,#nextPage").addClass('bToggle');
	   	if ($('#currentPage').html() == 1) {
   			$('#fistPage').attr('disabled', 'true');
   			$('#fistPage').removeClass('bToggle');
   			$('#upPage').attr('disabled', 'true');
   			$('#upPage').removeClass('bToggle');
   		}
   		if (Number($('#allPage').html()) == Number($('#currentPage').html())) {
   			$('#lastPage').attr('disabled', 'true');
   			$('#lastPage').removeClass('bToggle');
   			$('#nextPage').attr('disabled', 'true');
   			$('#nextPage').removeClass('bToggle');
   		}
	}

$(function(){
	var curcatalog = $("span#catalog").text();
	if(curcatalog == "InstallationPackage"){
		$("div.choose").hide();
		$(".bigFileUploadBtn").css({"display":"inline-block"});
	}else{
		$("div.choose").css({"display":"inline-block"});
		$(".bigFileUploadBtn").hide();
	}
	
	$(".bigFileUploadBtn input").click(function(){
		$(".upload_wrapper").show();
		$(".upload_bgcover").show();
		var ih = $("#picker").height();
		var iw = $("#picker").width();
		$("input.webuploader-element-invisible").parent().css({"width":iw+"px","height":ih+"px"});
		$("input.webuploader-element-invisible").siblings("label").css({"width":iw+"px","height":ih+"px"});
	});
	$(".upload_wrapper_tit_r").click(function(){
		$(".upload_wrapper").hide();
		$(".upload_bgcover").hide();
	});
});
// 分片上传
var $list = $('#thelist'),
    $btn = $('#ctlBtn'),
    state = 'pending';
var uploader = WebUploader.create({
    // swf文件路径
    swf: 'plugins/webuploader/Uploader.swf',
    // 文件接收服务端。
    server: 'BigFileUpload',
    // server: 'http://webuploader.duapp.com/server/fileupload.php',
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: '#picker',
    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
    resize: false,
    // 是否允许在文件传输时提前把下一个文件准备好
    prepareNextFile: true,
    // 是否要分片处理大文件上传
    chunked: true,
    // 分片大小
    chunkSize: 10485760,
    // 如果某个分片由于网络问题出错，允许自动重传多少次
    chunkRetry: 1,
    // 上传并发数
    threads: 3,
    // 文件上传请求的参数表，每次发送都会发送此对象中的参数
    formData: {
    	md5: '',
    	catalog: 'InstallationPackage'
    },
    // 设置文件上传域的name
    fileVal: 'file',
    method: 'POST',
    // 是否以二进制的流的方式发送文件，这样整个上传内容php://input都为文件内容， 其他参数在$_GET数组中
    sendAsBinary: false,
    // 去重， 根据文件名字、文件大小和最后修改时间来生成hash Key
    // duplicate: undefined
});

// 当有文件被添加进队列的时候
uploader.on( 'fileQueued', function( file ) {
    $list.append( '<div id="' + file.id + '" class="item">' +
        '<h4 class="uploader_info">' + file.name + '</h4>' +
        '<p class="state">等待上传...</p>' +
    '</div>' );
    var _file = $("#" + file.id);
    uploader.md5File( file )
    // 及时显示进度
        .progress(function(percentage) {
            //console.log('Percentage:', percentage);
            _file.find("p").html("准备中:"+ percentage * 100 + "%");
        })
        // 完成
        .then(function(val) {
            uploader.options.formData.md5 = val;
            _file.find("p").html("准备完成,等待上传.");
        });
    console.log(uploader.getStats());
});

// 文件上传过程中创建进度条实时显示。
uploader.on( 'uploadProgress', function( file, percentage ) {
    var $li = $( '#'+file.id ),
        $percent = $li.find('.progress .progress-bar');
    // 避免重复创建
    if ( !$percent.length ) {
        $percent = $('<div class="progress progress-striped active">' +
          '<div class="progress-bar" role="progressbar" style="width: 0%">0%' +
          '</div>' +
        '</div>').appendTo( $li ).find('.progress-bar');
    }
    $li.find('p.state').text('上传中');
    $percent.css( 'width', percentage * 100 + '%' );
    $percent.text((Math.floor(percentage * 1000))/10 + '%');
});

uploader.on( 'uploadSuccess', function( file ) {
    $( '#'+file.id ).find('p.state').text('已上传');
    $.MsgBox.Alert("提示", "上传成功！");
});

uploader.on( 'uploadError', function( file ) {
    $( '#'+file.id ).find('p.state').text('上传出错');
});

uploader.on( 'uploadComplete', function( file ) {
    $( '#'+file.id ).find('.progress').fadeOut();
});

uploader.on( 'all', function( type ) {
    if ( type === 'startUpload' ) {
        state = 'uploading';
    } else if ( type === 'stopUpload' ) {
        state = 'paused';
    } else if ( type === 'uploadFinished' ) {
        state = 'done';
    }

    if ( state === 'uploading' ) {
        $btn.text('暂停上传');
    } else {
        $btn.text('开始上传');
    }
});

$btn.on( 'click', function() {
    if ( state === 'uploading' ) {
        uploader.stop(true);
    } else {
        uploader.upload();
    }
});
</script>
</html>