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
<title>开发项目管理</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<!-- <link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css"> -->
<!-- <link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" /> -->
<link rel="stylesheet" type="text/css" href="css/libs/bootstrap-grid-form-btn-res-icon-tooltip-popover.min.css">
<link rel="stylesheet" type="text/css" href="css/SoftwareProject.css?iv=201809291750">
<link rel="stylesheet" type="text/css" href="css/global/global_table_style.css">
<style>
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

	.clear_float {
	    height: 2px;
	    width: calc(100% - 2px);
	    clear: both;
	}

	.contain {
		min-width: 1100px !important;
	}

	.content {
		padding-bottom: 5px !important;
	}

	/*导航栏各页面自定义*/
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
		margin-top: 20px !important;
		width: auto !important;
		height: auto !important;
	    border: 1px solid transparent !important;
	}

	.changePage .btn {
		height: auto !important;
		width: auto !important;
	}

	/*自定义主页面表格*/
	#global_table_style td {
	    max-width: 120px;
	}

	#global_table_style th {
		height: 50px;
		font-size: 14px;
	}

	#global_table_style th:nth-child(2), #global_table_style th:nth-child(5), #global_table_style th:nth-child(6), #global_table_style th:nth-child(7), #global_table_style th:nth-child(8), #global_table_style th:nth-child(9), #global_table_style th:nth-child(10), #global_table_style th:nth-child(15) {
		min-width: 51px;
		max-width: 51px;
		width: 5.1%;
	}

	#global_table_style td:nth-child(2), #global_table_style td:nth-child(5), #global_table_style td:nth-child(6), #global_table_style td:nth-child(7), #global_table_style td:nth-child(8), #global_table_style td:nth-child(9), #global_table_style td:nth-child(10), #global_table_style td:nth-child(15) {
		min-width: 51px;
		max-width: 51px;
		width: 5.1%;
	}

	#global_table_style th:nth-child(1), #global_table_style td:nth-child(1) {
		min-width: 31px;
		max-width: 31px;
		width: 3.1%;
	}

	#global_table_style th {
		cursor: pointer;
	}

	#global_table_style th span {
		display: block;
	}

	#global_table_style th span.hide_span {
		display: none;
	}

</style>
</head>
<body>
	<div id="originfactory_wrapper">
		<div id="originfactory_sticker">
			<div id="originfactory_sticker-con">
				<!-- 	=======================头部   开始 ================================== -->
				<%@include file="top.jsp"%>
				<!-- 	=======================头部   结束 ================================== -->
				<div class="contain">
					<div class="content">
			<!-- 	=======================导航栏   开始 ================================== -->
					<%@include file="nav.jsp"%>
			<!-- 	=======================导航栏   结束 ================================== -->
						<div class="choose">
							<input type="button" value="添加" class="btn btn-info" onclick="AddContract()">
							<input type="button" value="导出" class="btn btn-info export" style="margin-left:20px">
							<input type="button" value="搜索框" class="btn btn-info search_all_box" style="margin-left:20px">
							<input type="button" value="取消搜索" class="btn btn-info cancel_search" style="margin-left:20px">
						</div>
						<div id="global_table_style_wrapper">
							<table id="global_table_style">
								<thead>
									<tr>
										<th>序号<span name="search_flag_span" class="hide_span glyphicon" aria-hidden="true" data-isearch="IndexID"></span></th>
										<th>产品<span name="search_flag_span" class="hide_span glyphicon" aria-hidden="true" data-isearch="Product"></span></th>
										<th>开发说明书<span name="search_flag_span" class="hide_span glyphicon" aria-hidden="true" data-isearch="Instructions"></span></th>
										<th>UI规范<span name="search_flag_span" class="hide_span glyphicon" aria-hidden="true" data-isearch="UISpecification"></span></th>
										<th>优先级<span name="search_flag_span" class="hide_span glyphicon" aria-hidden="true" data-isearch="Priority"></span></th>
										<th>提交人<span name="search_flag_span" class="hide_span glyphicon" aria-hidden="true" data-isearch="Submitter"></span></th>
										<th>前端<span name="search_flag_span" class="hide_span glyphicon" aria-hidden="true" data-isearch="Front"></span></th>
										<th>后台<span name="search_flag_span" class="hide_span glyphicon" aria-hidden="true" data-isearch="Back"></span></th>
										<th>UI设计<span name="search_flag_span" class="hide_span glyphicon" aria-hidden="true" data-isearch="UIDesigner"></span></th>
										<th>主导人<span name="search_flag_span" class="hide_span glyphicon" aria-hidden="true" data-isearch="Leader"></span></th>
										<th>提交时间<span name="search_flag_span" class="hide_span glyphicon" aria-hidden="true" data-isearch="SubmissionTime"></span></th>
										<th>规划时间<span name="search_flag_span" class="hide_span glyphicon" aria-hidden="true" data-isearch="PlanningTime"></span></th>
										<th>交付时间<span name="search_flag_span" class="hide_span glyphicon" aria-hidden="true" data-isearch="LeadTime"></span></th>
										<th>延期时间<span name="search_flag_span" class="hide_span glyphicon" aria-hidden="true" data-isearch="DelayTime"></span></th>
										<th>状态<span name="search_flag_span" class="hide_span glyphicon" aria-hidden="true" data-isearch="State"></span></th>
										<th>备注<span name="search_flag_span" class="hide_span glyphicon" aria-hidden="true" data-isearch="Remark"></span></th>
										<th>删除</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="orderInfo" items="${datas}" varStatus="status">
										<c:if test="${status.index>0}">
											<tr class="tbody_tr">
												<td value="${orderInfo['ID']}" class="contract-edit" style="cursor:pointer;">${orderInfo['IndexID']}</td>
												<td class="Product" >${orderInfo['Product']}</td>
												<td title="${orderInfo['Instructions']}" class="Instructions" style="cursor:pointer;"><a href="ProjectDocumentDownload?Classify=Instructions&ID=${orderInfo['ID']}">${orderInfo['Instructions']}</a></td>
												<td title="${orderInfo['UISpecification']}" class="UISpecification" style="cursor:pointer;"><a href="ProjectDocumentDownload?Classify=UISpecification&ID=${orderInfo['ID']}">${orderInfo['UISpecification']}</a></td>
												<td class="Priority" class="Priority">${orderInfo['Priority']}</td>
												<td class="GraduateInstitutions">${orderInfo['Submitter']}</td>
												<td class="Front">${orderInfo['Front']}</td>
												<td class="Back">${orderInfo['Back']}</td>
												<td class="UIDesigner">${orderInfo['UIDesigner']}</td>
												<td class="Leader">${orderInfo['Leader']}</td>
												<td class="SubmissionTime">${orderInfo['SubmissionTime']}</td>
												<td class="PlanningTime">${orderInfo['PlanningTime']}</td>
												<td class="LeadTime">${orderInfo['LeadTime']}</td>
												<c:choose>
													<c:when test="${orderInfo['DelayTime']>0}">
														<td class="DelayTime" style="color:red;">${orderInfo['DelayTime']}</td>
													</c:when>
													<c:otherwise>
														<td class="DelayTime">${orderInfo['DelayTime']}</td>
													</c:otherwise>
												</c:choose>
												<td class="State">${orderInfo['State']}</td>
												<td class="Remark" title="${orderInfo['Remark']}">${orderInfo['Remark']}</td>
												<td title="删除"><span class="glyphicon glyphicon-trash delete_span" aria-hidden="true" value="${orderInfo['ID']}"></span></td>
											</tr>
										</c:if>
									</c:forEach>
								</tbody>
							</table>
						</div>
							
						<div class="cover-color" style="display: none;"></div>
							<!-- 	=======================添加信息 ================================== -->
						        <div class="contract_add" style="display: none;">
						            <div class="contract_title">添加开发项目管理</div>
						            <div class="contractAdd_close"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></div>
						            <div class="basic_info">
						            	<div class="basic_info_in">
						            		<fieldset><legend>添加基本信息</legend>
						            			<div class="container-fluid">
						            			  <div class="row">
						            			    <div class="col-md-6 col-lg-6">
						            			    	<div class="row">
					            			    	      <div class="col-md-4 col-lg-4 field_div">
					            			    	        <div>产品</div>
					            			    	        <div>开发说明书</div>
					            			    	        <div>UI规范</div>
					            			    	        <div>优先级</div>
					            			    	        <div>状态</div>
					            			    	        <div>规划时间</div>
					            			    	      </div>
					            			    	      <div class="col-md-8 col-lg-8 form_div">
					            			    	        <div><select name="Product" id="" class="form-control"></select></div>
					    	                                <div>
			                        	                        <div class="add_fileBox Instructions" >
			                        								<span class="add_fileCont1"></span>
			                        								<span class="add_uploadText">上传</span>
			                        							    <input class="add_change add_agreement1" id="add_agreement1" type="file" multiple="multiple" name="fileName"/>
			                        							</div>
			                        							<span class="span_text"></span>
					    	                                </div>
					    	        						<!-- UI规范 -->
									                        <div>
			                        	                        <div class="add_fileBox UISpecification" >
			                        								<span class="add_fileCont2"></span>
			                        								<span class="add_uploadText">上传</span>
			                        							    <input class="add_change add_agreement2" id="add_agreement2" type="file" multiple="multiple" value="" name="fileName"/>
			                        							</div>
			                        							<span class="span_text"></span>
									                        </div>
															<div><select name="Priority" id="" class="form-control"></select></div>
															<div><select name="State" id="" class="form-control"></select></div>
															<div><input type="date" name="PlanningTime" value="" id="" class="form-control PlanningTime"></div>
					            			    	      </div>
					            			    	    </div>
						            			    </div>
						            			    <!-- 右边 -->
						            			    <div class="col-md-6 col-lg-6">
						            			    	<div class="row">
					            			    	      <div class="col-md-4 col-lg-4 field_div">
					            			    	        <div>主导人</div>
					            			    	        <div>前端</div>
					            			    	        <div>后台</div>
					            			    	        <div>UI设计</div>
					            			    	        <div>邮箱密码</div>
					            			    	      </div>
					            			    	      <div class="col-md-8 col-lg-8 form_div">
					            			    	        <div class="insert_staff"><select name="Leader" id="" class="form-control"></select></div>
					    	                                <div class="insert_staff"><select name="Front" id="" class="form-control"></select></div>
					    	                                <div class="insert_staff"><select name="Back" id="" class="form-control"></select></div>
					    	                                <div class="insert_staff"><select name="UIDesigner" id="" class="form-control"></select></div>
					    	                                <div class="has-feedback"><input type="password" class="form-control" autocomplete="new-password" name="ipassword"><span class="glyphicon glyphicon-eye-open form-control-feedback" aria-hidden="true"></span></div>
					            			    	      </div>
					            			    	    </div>
						            			    </div><!-- 右边end -->
						            			  </div>
						            			</div>
						            		</fieldset>
						            		<fieldset><legend>添加备注</legend>
						            			<div class="form-group">
						            			    <!-- <label for="exampleInputName2">Name</label> -->
						            			    <textarea class="form-control" rows="4" name="Remark"></textarea>
						            			</div>
						            		</fieldset>
						            	</div><!-- basic_info_in end -->

						        	</div><!-- basic_info end -->

									<div class="edit_btn">
										<div class="edit_btn_in">
											<input type="button" value="提交" class="btn btn-success" id="add_submit">
											<input type="button" value="取消" class="btn btn-warning" id="add_cancel">
										</div>
									</div>
								</div>

						<!-- 		==============================	修改信息 ======================-->
						      <div class="contract_update" style="display: none;">
						            <div class="contract_title">修改开发项目管理</div>
						            <div class="update_close"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></div>
						            <div class="basic_info">
						            	<span class="update_id" style="display:none"></span>
						            	<div class="basic_info_in">
						            		<fieldset><legend>修改基本信息</legend>
						            			<div class="container-fluid">
						            			  <div class="row">
						            			    <div class="col-md-6 col-lg-6">
						            			    	<div class="row">
					            			    	      <div class="col-md-4 col-lg-4 field_div">
					            			    	        <div>产品</div>
					            			    	        <div>开发说明书</div>
					            			    	        <div>UI规范</div>
					            			    	        <div>优先级</div>
					            			    	        <div>状态</div>
					            			    	        <div>规划时间</div>
					            			    	      </div>
					            			    	      <div class="col-md-8 col-lg-8 form_div">
					            			    	        <div><select name="Product" id="" class="form-control"></select></div>
					    	                                <div>
            	                                                <div class="update_fileBox Instructions" >
            	                        							<span class="update_fileCont1"></span>
            	                        							<span class="update_uploadText">上传</span>
            	                        						    <input class="update_change update_agreement1"  id="update_agreement1" type="file" multiple="multiple" name="fileName"/>
            	                        						</div>
			                        							<span class="span_text"></span>
					    	                                </div>
					    	        						<!-- UI规范 -->
									                        <div>
            	                                                <div class="update_fileBox UISpecification" >
            	                        							<span class="update_fileCont2"></span>
            	                        							<span class="update_uploadText">上传</span>
            	                        						    <input class="update_change update_agreement2" id="update_agreement2" type="file" multiple="multiple" name="fileName" />
            	                        						</div>
			                        							<span class="span_text"></span>
									                        </div>
															<div><select name="Priority" id="" class="form-control"></select></div>
															<div><select name="State" id="" class="form-control"></select></div>
															<div><input type="date" name="PlanningTime" value="" id="" class="form-control PlanningTime"></div>
					            			    	      </div>
					            			    	    </div>
						            			    </div>
						            			    <!-- 右边 -->
						            			    <div class="col-md-6 col-lg-6">
						            			    	<div class="row">
					            			    	      <div class="col-md-4 col-lg-4 field_div">
					            			    	        <div>主导人</div>
					            			    	        <div>前端</div>
					            			    	        <div>后台</div>
					            			    	        <div>UI设计</div>
					            			    	        <div>邮箱密码</div>
					            			    	      </div>
					            			    	      <div class="col-md-8 col-lg-8 form_div">
					            			    	        <div class="insert_staff"><select name="Leader" id="" class="form-control"></select></div>
					    	                                <div class="insert_staff"><select name="Front" id="" class="form-control"></select></div>
					    	                                <div class="insert_staff"><select name="Back" id="" class="form-control"></select></div>
					    	                                <div class="insert_staff"><select name="UIDesigner" id="" class="form-control"></select></div>
					    	                                <div class="has-feedback"><input type="password" class="form-control" autocomplete="new-password" name="ipassword"><span class="glyphicon glyphicon-eye-open form-control-feedback" aria-hidden="true"></span></div>
					            			    	      </div>
					            			    	    </div>
						            			    </div><!-- 右边end -->
						            			  </div>
						            			</div>
						            		</fieldset>
						            		<fieldset><legend>添加备注</legend>
						            			<div class="form-group">
						            			    <!-- <label for="exampleInputName2">Name</label> -->
						            			    <textarea class="form-control" rows="4" name="Remark"></textarea>
						            			</div>
						            		</fieldset>
						            	</div><!-- basic_info_in end -->
						                
						        </div><!-- basic_info end -->
								<div class="edit_btn">
									<div class="edit_btn_in">
										<input type="button" value="提交" class="btn btn-success" id="update_submit">
										<input type="button" value="取消" class="btn btn-warning" id="update_cancel">
									</div>
								</div>
							</div>

						<div id="page">
							<input type="hidden" id="search_i_currentPage" value="${currentPage}">
							<input type="hidden" id="search_i_pageCounts" value="${pageCounts}">
							<div class="pageInfo">
								当前是第&nbsp;<span id="currentPage">${currentPage}</span>&nbsp;页,&nbsp;总计&nbsp;<span
									id="allPage">${pageCounts}</span>页
							</div>
							<div class="changePage">
								<input type="button" class="btn btn-primary" value="首页" id="fistPage" name="fistPage" onclick="FistPage()">
								<input type="button" class="btn btn-primary" value="上一页" id="upPage" onclick="UpPage()">
								<input type="button" class="btn btn-primary" value="下一页" id="nextPage" onclick="NextPage()"> 跳到第 <input type="text" id="jumpNumber" name="jumpNumber" class="jumpNumber"
									style="width: 40px;height: 30px;color: #000" onkeyup="value=value.replace(/[^\d]/g,'')"> 页 <input type="button" class="btn btn-primary" value="GO" id="Gotojump" name="Gotojump" onclick="PageJump()">
								<input type="button" class="btn btn-primary" value="尾页" id="lastPage" name="lastPage" onclick="LastPage()">
							</div>
						</div>
						<div class="clear_float"></div>
					</div>
				</div>
				<!-- ***************编辑上传文件区域****************** -->
				<div class="MailBar_cover_color" style="display: none;"></div>
			 	<div class="MailBar" style="display:none;">
					<div class="operate_title">上传文件</div>
					<div class="MailBar_close">关闭</div>
			 			<!--不影响使用的代码  -->
						<!-- <input type="file" name="file" id="Mail_fileToUpload"   value="添加附件" />
						<!-- 改样式后的代码优化 -->
							<a href="" class="box">
								<input type="file" name="file" id="Mail_fileToUpload" value="添加附件" class="up"/>
								<span class="intitle">点击上传文档、压缩包</span>
								<span class="mask"></span>
							</a>
						<!-- 修改结束位置 -->
						<input type="button" name="button" value="上传" class="bToggle" id="Mail_Send">
				</div>

				<!-- 搜索框all -->
				<div class="search_all">
					<div class="search_all_tit">搜索并排序<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></div>
					<div class="search_all_body">
						<div class="search_all_bodyin">
				    		<fieldset><legend>选择搜索字段和内容（可多选）</legend>
				    			<div class="container-fluid">
				    				<div class="row">
				    					<div class="col-md-6 col-lg-6">
				    						<div class="form-horizontal">
				    						    <!-- 一个form-group就是一个整体 -->
				    						    <div class="form-group">
				    						        <label for="search_con_IndexID" class="col-sm-4 col-md-4 control-label"><input type="checkbox" name="search_checkbox">&nbsp;序号</label>
				    						        <div class="col-sm-8 col-md-8">
				    						            <input type="text" id="search_con_IndexID" class="form-control" disabled="disabled" onkeyup="value=value.replace(/[^\d]/g,'')">
				    						        </div>
				    						    </div>
				    						    <div class="form-group">
				    						        <label for="search_con_Product" class="col-sm-4 col-md-4 control-label"><input type="checkbox" name="search_checkbox">&nbsp;产品</label>
				    						        <div class="col-sm-8 col-md-8">
				    						        	<select name="Product" id="search_con_Product" class="form-control" disabled="disabled"></select>
				    						        </div>
				    						    </div>
				    						    <div class="form-group">
				    						        <label for="search_con_Instructions" class="col-sm-4 col-md-4 control-label"><input type="checkbox" name="search_checkbox">&nbsp;开发说明书</label>
				    						        <div class="col-sm-8 col-md-8">
				    						            <input type="text" id="search_con_Instructions" class="form-control" disabled="disabled">
				    						        </div>
				    						    </div>
				    						    <div class="form-group">
				    						        <label for="search_con_UISpecification" class="col-sm-4 col-md-4 control-label"><input type="checkbox" name="search_checkbox">&nbsp;UI规范</label>
				    						        <div class="col-sm-8 col-md-8">
				    						            <input type="text" id="search_con_UISpecification" class="form-control" disabled="disabled">
				    						        </div>
				    						    </div>
				    						    <div class="form-group">
				    						        <label for="search_con_SubmissionTime" class="col-sm-4 col-md-4 control-label"><input type="checkbox" name="search_checkbox">&nbsp;提交时间</label>
				    						        <div class="col-sm-8 col-md-8">
				    						            <input type="date" id="search_con_SubmissionTime" class="form-control" disabled="disabled">
				    						        </div>
				    						    </div>
				    						    <div class="form-group">
				    						        <label for="search_con_PlanningTime" class="col-sm-4 col-md-4 control-label"><input type="checkbox" name="search_checkbox">&nbsp;规划时间</label>
				    						        <div class="col-sm-8 col-md-8">
				    						            <input type="date" id="search_con_PlanningTime" class="form-control" disabled="disabled">
				    						        </div>
				    						    </div>
				    						    <div class="form-group">
				    						        <label for="search_con_LeadTime" class="col-sm-4 col-md-4 control-label"><input type="checkbox" name="search_checkbox">&nbsp;交付时间</label>
				    						        <div class="col-sm-8 col-md-8">
				    						            <input type="date" id="search_con_LeadTime" class="form-control" disabled="disabled">
				    						        </div>
				    						    </div>
				    						    <div class="form-group">
				    						        <label for="search_con_DelayTime" class="col-sm-4 col-md-4 control-label"><input type="checkbox" name="search_checkbox">&nbsp;延期时间</label>
				    						        <div class="col-sm-8 col-md-8">
				    						            <input type="date" id="search_con_DelayTime" class="form-control" disabled="disabled">
				    						        </div>
				    						    </div>
				    						</div>
				    					</div><!-- col-md-6 end -->
				    					<div class="col-md-6 col-lg-6">
				    						<div class="form-horizontal">
				    						    <!-- 一个form-group就是一个整体 -->
				    						    <div class="form-group">
				    						        <label for="search_con_Priority" class="col-sm-4 col-md-4 control-label"><input type="checkbox" name="search_checkbox">&nbsp;优先级</label>
				    						        <div class="col-sm-8 col-md-8">
				    						        	<select name="Priority" id="search_con_Priority" class="form-control" disabled="disabled"></select>
				    						        </div>
				    						    </div>
				    						    <div class="form-group">
				    						        <label for="search_con_Submitter" class="col-sm-4 col-md-4 control-label"><input type="checkbox" name="search_checkbox">&nbsp;提交人</label>
				    						        <div class="col-sm-8 col-md-8">
				    						        	<select id="search_con_Submitter" class="form-control insert_staff_sel" disabled="disabled"></select>
				    						        </div>
				    						    </div>
				    						    <div class="form-group">
				    						        <label for="search_con_Front" class="col-sm-4 col-md-4 control-label"><input type="checkbox" name="search_checkbox">&nbsp;前端</label>
				    						        <div class="col-sm-8 col-md-8">
				    						        	<select id="search_con_Front" class="form-control insert_staff_sel" disabled="disabled"></select>
				    						        </div>
				    						    </div>
				    						    <div class="form-group">
				    						        <label for="search_con_Back" class="col-sm-4 col-md-4 control-label"><input type="checkbox" name="search_checkbox">&nbsp;后台</label>
				    						        <div class="col-sm-8 col-md-8">
				    						        	<select id="search_con_Back" class="form-control insert_staff_sel" disabled="disabled"></select>
				    						        </div>
				    						    </div>
				    						    <div class="form-group">
				    						        <label for="search_con_UIDesigner" class="col-sm-4 col-md-4 control-label"><input type="checkbox" name="search_checkbox">&nbsp;UI设计</label>
				    						        <div class="col-sm-8 col-md-8">
				    						        	<select id="search_con_UIDesigner" class="form-control insert_staff_sel" disabled="disabled"></select>
				    						        </div>
				    						    </div>
				    						    <div class="form-group">
				    						        <label for="search_con_Leader" class="col-sm-4 col-md-4 control-label"><input type="checkbox" name="search_checkbox">&nbsp;主导人</label>
				    						        <div class="col-sm-8 col-md-8">
				    						        	<select id="search_con_Leader" class="form-control insert_staff_sel" disabled="disabled"></select>
				    						        </div>
				    						    </div>
				    						    <div class="form-group">
				    						        <label for="search_con_State" class="col-sm-4 col-md-4 control-label"><input type="checkbox" name="search_checkbox">&nbsp;状态</label>
				    						        <div class="col-sm-8 col-md-8">
				    						        	<select name="State" id="search_con_State" class="form-control" disabled="disabled"></select>
				    						        </div>
				    						    </div>
				    						    <div class="form-group">
				    						        <label for="search_con_Remark" class="col-sm-4 col-md-4 control-label"><input type="checkbox" name="search_checkbox">&nbsp;备注</label>
				    						        <div class="col-sm-8 col-md-8">
				    						            <input type="text" id="search_con_Remark" class="form-control" disabled="disabled">
				    						        </div>
				    						    </div>
				    						</div>
				    					</div><!-- col-md-6 end -->
				    				</div>
				    			</div><!-- container-fluid end -->
				    		</fieldset>
	    		    		<fieldset><legend>排序规则&nbsp;<label><input type="checkbox" class="isOrderd" checked>不排序（即按规划时间降序）</label></legend>
	    		    			<div class="container-fluid">
	    		    				<div class="row">
	    		    					<div class="col-md-6 col-lg-6">
	    		    						<div class="form-horizontal">
	    		    						    <!-- 一个form-group就是一个整体 -->
	    		    						    <div class="form-group">
	    		    						        <label for="VM_add_ProjectName" class="col-sm-4 col-md-4 control-label"><input type="radio" name="Order_radio" data-order="ASC">升序搜索</label>
	    		    						        <div class="col-sm-8 col-md-8">
	    		    						            <select name="Order_select" id="VM_add_ProjectName" class="form-control" disabled="disabled">
	    		    						            </select>
	    		    						        </div>
	    		    						    </div>
	    		    						</div>
	    		    					</div><!-- col-md-6 end -->
	    		    					<div class="col-md-6 col-lg-6">
	    		    						<div class="form-horizontal">
	    		    						    <!-- 一个form-group就是一个整体 -->
	    		    						    <div class="form-group">
	    		    						        <label for="VM_add_ProjectName2" class="col-sm-4 col-md-4 control-label"><input type="radio" name="Order_radio" data-order="DESC">降序搜索</label>
	    		    						        <div class="col-sm-8 col-md-8">
	    		    						            <select name="Order_select" id="VM_add_ProjectName2" class="form-control" disabled="disabled">
	    		    						            </select>
	    		    						        </div>
	    		    						    </div>
	    		    						</div>
	    		    					</div><!-- col-md-6 end -->
	    		    				</div>
	    		    			</div><!-- container-fluid end -->
	    		    		</fieldset>
						</div>
					</div>
					<div class="search_all_foot">
						<div class="search_all_footin">
							<input type="button" value="提交" class="btn btn-success" id="search_all_submit">
							<input type="button" value="取消" class="btn btn-warning" id="search_all_cancel">
						</div>
					</div>
				</div>
				<!-- 搜索框all end -->
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
<script src="js/libs/bootstrap/bootstrap-grid-form-btn-res-icon-tooltip-popover.min.js" type="text/javascript" charset="utf-8"></script>
<script src="plugins/cookie/jquery.cookie.js"></script>
<script src="js/ajaxfileupload.js" type="text/javascript" charset="utf-8"></script>
<script src="js/msgbox.js"></script>
<script src="js/SoftwareProject.js?iv=201809291808"></script>
</html>