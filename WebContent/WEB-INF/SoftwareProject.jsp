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
<link rel="stylesheet" type="text/css" href="css/SoftwareProject.css">
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
		min-width: 55px;
		max-width: 55px;
		width: 5.5%;
	}

	#global_table_style td:nth-child(2), #global_table_style td:nth-child(5), #global_table_style td:nth-child(6), #global_table_style td:nth-child(7), #global_table_style td:nth-child(8), #global_table_style td:nth-child(9), #global_table_style td:nth-child(10), #global_table_style td:nth-child(15) {
		min-width: 55px;
		max-width: 55px;
		width: 5.5%;
	}

	#global_table_style th:nth-child(1), #global_table_style td:nth-child(1) {
		min-width: 33px;
		max-width: 33px;
		width: 3.3%;
	}

	#global_table_style th {
		cursor: pointer;
	}

	#global_table_style th a {
		display: block;
	}

	/*可拖动的innerHtml*/
	.glyphicon.glyphicon-resize-horizontal {
		margin-left: -3px;
	    position: relative;
	}

	div.JCLRgrip {
		z-index: 2 !important;
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
							<input type="button" value="取消搜索" class="btn btn-info cancel_search" style="margin-left:20px">
						</div>
						<div id="global_table_style_wrapper">
							<table id="global_table_style">
								<thead>
									<tr>
										<th>序号<a tabindex="0" class="btn btn-sm btn-info" role="button" data-toggle="popover" data-trigger="click" data-container="body" data-placement="bottom" title="对序号操作"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a></th>
										<th>产品<a tabindex="0" class="btn btn-sm btn-info" role="button" data-toggle="popover" data-trigger="click" data-container="body" data-placement="bottom" title="对产品操作"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a></th>
										<th>开发说明书<a tabindex="0" class="btn btn-sm btn-info" role="button" data-toggle="popover" data-trigger="click" data-container="body" data-placement="bottom" title="对开发说明书操作"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a></th>
										<th>UI规范<a tabindex="0" class="btn btn-sm btn-info" role="button" data-toggle="popover" data-trigger="click" data-container="body" data-placement="bottom" title="对UI规范操作"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a></th>
										<th>优先级<a tabindex="0" class="btn btn-sm btn-info" role="button" data-toggle="popover" data-trigger="click" data-container="body" data-placement="bottom" title="对优先级操作"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a></th>
										<th>提交人<a tabindex="0" class="btn btn-sm btn-info" role="button" data-toggle="popover" data-trigger="click" data-container="body" data-placement="bottom" title="对提交人操作"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a></th>
										<th>前端<a tabindex="0" class="btn btn-sm btn-info" role="button" data-toggle="popover" data-trigger="click" data-container="body" data-placement="bottom" title="对前端操作"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a></th>
										<th>后台<a tabindex="0" class="btn btn-sm btn-info" role="button" data-toggle="popover" data-trigger="click" data-container="body" data-placement="bottom" title="对后台操作"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a></th>
										<th>UI设计<a tabindex="0" class="btn btn-sm btn-info" role="button" data-toggle="popover" data-trigger="click" data-container="body" data-placement="bottom" title="对UI设计操作"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a></th>
										<th>主导人<a tabindex="0" class="btn btn-sm btn-info" role="button" data-toggle="popover" data-trigger="click" data-container="body" data-placement="bottom" title="对主导人操作"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a></th>
										<th>提交时间<a tabindex="0" class="btn btn-sm btn-info" role="button" data-toggle="popover" data-trigger="click" data-container="body" data-placement="bottom" title="对提交时间操作"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a></th>
										<th>规划时间<a tabindex="0" class="btn btn-sm btn-info" role="button" data-toggle="popover" data-trigger="click" data-container="body" data-placement="bottom" title="对规划时间操作"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a></th>
										<th>交付时间<a tabindex="0" class="btn btn-sm btn-info" role="button" data-toggle="popover" data-trigger="click" data-container="body" data-placement="bottom" title="对交付时间操作"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a></th>
										<th>延期时间<a tabindex="0" class="btn btn-sm btn-info" role="button" data-toggle="popover" data-trigger="click" data-container="body" data-placement="bottom" title="对延期时间操作"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a></th>
										<th>状态<a tabindex="0" class="btn btn-sm btn-info" role="button" data-toggle="popover" data-trigger="click" data-container="body" data-placement="bottom" title="对状态操作"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a></th>
										<th>备注<a tabindex="0" class="btn btn-sm btn-info" role="button" data-toggle="popover" data-trigger="click" data-container="body" data-placement="bottom" title="对备注操作"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="orderInfo" items="${datas}" varStatus="status">
										<c:if test="${status.index>0}">
											<tr class="tbody_tr">
												<td value="${orderInfo['ID']}" class="contract-edit" style="cursor:pointer;">${orderInfo['IndexID']}</td>
												<td class="Product" >${orderInfo['Product']}</td>
												<td title="${orderInfo['Instructions']}" class="Instructions" style="cursor:pointer;"><a href="ProjectDocumentDownload?Classify=Instructions&ID=${orderInfo['ID']}" style="text-decoration:none;color:#000">${orderInfo['Instructions']}</a></td>
												<td title="${orderInfo['UISpecification']}" class="UISpecification" style="cursor:pointer;"><a href="ProjectDocumentDownload?Classify=UISpecification&ID=${orderInfo['ID']}" style="text-decoration:none;color:#000">${orderInfo['UISpecification']}</a></td>
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
												<td class="Remark" title="${orderInfo['Remark']}" >${orderInfo['Remark']}</td>
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

										
						                <!-- 修改添加 -->
						                <!-- <div class="table_body" style="margin-top:15px;margin-bottom:60px;">
						                	<div class="table_col1" style="float:left;width:24%;min-width:24%;padding-right:5%;">
						                		<span style="float:left;margin-right:22%">产品</span>
						                        <span>
						                        <select style="width:66%;height:25px;margin-bottom:20px;" name="Product"  id="Product">
						                        	 <option text="请选择">请选择</option>
													  <option  text="IT系统">IT系统</option>
													  <option  text="EUCP">EUCP</option>
													  <option  text="futureC">futureC</option>
													  <option  text="futureD">futureD</option>
													</select>
						                        </span>
						                        <br>
						                        <span style="float:left;margin-right:5%">开发说明书</span>
						                        
						                        <div class="add_fileBox Instructions" >
													<span class="add_fileCont1"></span>
													<span class="add_uploadText">上传</span>
												    <input class="add_change add_agreement1" id="add_agreement1" type="file" multiple="multiple" name="fileName"/>
												</div>
						                        <br>
						                         <span style="float:left;margin-right:16%">UI规范</span>
						                        
						                         <div class="add_fileBox UISpecification" >
													<span class="add_fileCont2"></span>
													<span class="add_uploadText">上传</span>
												    <input class="add_change add_agreement2"  id="add_agreement2" type="file" multiple="multiple" value="" name="fileName"/>
												</div>
												
						                          <br>
						                        <span style="float:left;margin-right:16%">优先级</span>
						                         <span>
						                         <select style="width:66%;height:25px;margin-bottom:20px;" name="Priority"  id="Priority">
						                        	 <option text="请选择">请选择</option>
													  <option  text="最高">最高</option>
													  <option  text="高">高</option>
													  <option  text="普通">普通</option>
													  <option  text="低">低</option>
													  <option  text="最低">最低</option>
													</select>
						                         </span>
						                        <br>
						                        <span style="float:left;margin-right:21%">状态</span>
						                         <span>
						                         <select style="width:66%;height:25px;margin-bottom:20px;" name="State"  id="State">
						                        	 <option text="请选择">请选择</option>
													  <option  text="完成">完成</option>
													  <option  text="未完成">未完成</option>
													  <option  text="进行中">进行中</option>
													</select>
						                         
						                         </span>
						                        <br>
						                        
						                	</div>
						                	<div  class="table_col2" style="float:left;width:28%;min-width:28%;padding-left:5%;padding-right:3%;padding-bottom:25px;border-left:1px solid #dadada;border-right:1px solid #dadada;">
						                		<span style="float:left;margin-right:18%">前端</span>
						                        <span  class="staff Front">
						                        	<select style="width:66%;height:25px;margin-bottom:20px;" name="Front"  id="Front">
						                        	 <option text="请选择">请选择</option>
													  <option  text=""></option>
													  <option  text=""></option>
													  <option  text=""></option>
													</select>
						                        </span>
						                        <br>
						                        <span style="float:left;margin-right:18%">后台</span>
						                         <span  class="staff Back">
						                         	<select style="width:66%;height:25px;margin-bottom:20px;" name="Back"  id="Back">
						                        	 <option text="请选择">请选择</option>
													  <option  text=""></option>
													  <option  text=""></option>
													  <option  text=""></option>
													</select>
						                         </span>
						                        <br>
						                        <span style="float:left;margin-right:14%">UI设计</span>
						                         <span class="staff UIDesigner">
						                         	<select style="width:66%;height:25px;margin-bottom:20px;" name="UIDesigne"  id="UIDesigne">
						                        	 <option text="请选择">请选择</option>
													  <option  text=""></option>
													  <option  text=""></option>
													  <option  text=""></option>
													</select>
						                         </span>
						                        <br>
						                        <span style="float:left;margin-right:14%">主导人</span>
						                         <span class="staff Leader">
						                         	<select style="width:66%;height:25px;margin-bottom:20px;" name="Leader"  id="Leader">
						                        	 <option text="请选择">请选择</option>
													  <option  text=""></option>
													  <option  text=""></option>
													  <option  text=""></option>
													</select>
						                         </span>
						                        <br>
						                        <span style="float:left;margin-right:10%">邮箱密码</span>
						                         <span>
						                         <input type="text" name="Password" value="" id="Password" class="input_css Password" style="width:56%">
						                         <span class="passeye" style="margin-left:1%"></span>
						                         </span>
						                        <br>
						                    </div>
						                	<div  class="table_col3" style="float:left;width:28%;min-width:28%;padding-left:4%;">
						                		<span style="float:left;margin-right:5%">规划时间</span>
						                        <span>
						                         	<input type="date" name="PlanningTime" value="" id="PlanningTime" class="input_css PlanningTime">
						                         </span>
						                        <br>
						                		<span style="float:left;margin-right:5%">交付时间</span>
						                        <span><input type="date" name="LeadTime" value="" id="LeadTime" class="input_css LeadTime"></span>
						                        <br>
						                        <span style="float:left;margin-right:14%">备注</span>
						                         <span>
						                        <textarea style="margin-bottom:50px;" rows="6" cols="26" name="Remark" value="" id="Remark"></textarea>
						                         </span>
						                        <br>
						                        
						                    </div>
						                </div> -->

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


						                <!-- <div class="table_title">开发项目管理</div> -->
						                <!-- <span class="update_id" style="display:none"></span> -->
						                <!-- 修改添加 -->
						                <!-- <div class="table_body" style="margin-top:15px;margin-bottom:60px;">
						                	<div class="table_col1" style="float:left;width:24%;min-width:24%;padding-right:5%;">
						                		<span style="float:left;margin-right:22%">产品</span>
						                        <span>
						                        <select style="width:66%;height:25px;margin-bottom:20px;" name="Product"  id="Product">
						                        	 <option text="请选择">请选择</option>
													  <option  text="IT系统">IT系统</option>
													  <option  text="EUCP">EUCP</option>
													  <option  text="futureC">futureC</option>
													  <option  text="futureD">futureD</option>
													</select>
						                        </span>
						                        <br>
						                        <span style="float:left;margin-right:6%">开发说明书</span>
						                        <div class="update_fileBox Instructions" >
													<span class="update_fileCont1"></span>
													<span class="update_uploadText">上传</span>
												    <input class="update_change update_agreement1"  id="update_agreement1" type="file" multiple="multiple" name="fileName"/>
												</div>
												
						                        <br>
						                         <span style="float:left;margin-right:16%">UI规范</span>
						                         <div class="update_fileBox UISpecification" >
													<span class="update_fileCont2"></span>
													<span class="update_uploadText">上传</span>
												    <input class="update_change update_agreement2" id="update_agreement2" type="file" multiple="multiple" name="fileName" />
												</div>
												
						                        
						                          <br>
						                        <span style="float:left;margin-right:16%">优先级</span>
						                         <span>
						                         <select style="width:66%;height:25px;margin-bottom:20px;" name="Priority"  id="Priority">
						                        	 <option text="请选择">请选择</option>
													  <option  text="最高">最高</option>
													  <option  text="高">高</option>
													  <option  text="普通">普通</option>
													  <option  text="低">低</option>
													  <option  text="最低">最低</option>
													</select>
						                         </span>
						                        <br>
						                        <span style="float:left;margin-right:21%">状态</span>
						                         <span>
						                         <select style="width:66%;height:25px;margin-bottom:20px;" name="State"  id="State">
						                        	 <option text="请选择">请选择</option>
													  <option  text="完成">完成</option>
													  <option  text="未完成">未完成</option>
													  <option  text="进行中">进行中</option>
													</select>
						                         
						                         </span>
						                        <br>
						                	</div>
						                	<div  class="table_col2" style="float:left;width:28%;min-width:28%;padding-left:5%;padding-right:3%;padding-bottom:25px;border-left:1px solid #dadada;border-right:1px solid #dadada;">
						                		<span style="float:left;margin-right:18%">前端</span>
						                        <span  class="staff Front">
						                        	<select style="width:66%;height:25px;margin-bottom:20px;" name="Front"  id="Front">
						                        	 <option text="请选择">请选择</option>
													  <option  text=""></option>
													  <option  text=""></option>
													  <option  text=""></option>
													</select>
						                        </span>
						                        <br>
						                        <span style="float:left;margin-right:18%">后台</span>
						                         <span  class="staff Back">
						                         	<select style="width:66%;height:25px;margin-bottom:20px;" name="Back"  id="Back">
						                        	 <option text="请选择">请选择</option>
													  <option  text=""></option>
													  <option  text=""></option>
													  <option  text=""></option>
													</select>
						                         </span>
						                        <br>
						                        <span style="float:left;margin-right:14%">UI设计</span>
						                         <span class="staff UIDesigner">
						                         	<select style="width:66%;height:25px;margin-bottom:20px;" name="UIDesigne"  id="UIDesigne">
						                        	 <option text="请选择">请选择</option>
													  <option  text=""></option>
													  <option  text=""></option>
													  <option  text=""></option>
													</select>
						                         </span>
						                        <br>
						                        <span style="float:left;margin-right:14%">主导人</span>
						                         <span class="staff Leader">
						                         	<select style="width:66%;height:25px;margin-bottom:20px;" name="Leader"  id="Leader">
						                        	 <option text="请选择">请选择</option>
													  <option  text=""></option>
													  <option  text=""></option>
													  <option  text=""></option>
													</select>
						                         </span>
						                        <br>
						                        <span style="float:left;margin-right:10%">邮箱密码</span>
						                         <span>
						                         <input type="text" name="Password" value="" id="Password2" class="input_css Password2" style="width:56%">
						                         <span class="passeye" style="margin-left:1%"></span>
						                         </span>
						                        <br>
						                    </div>
						                	<div class="table_col3" style="float:left;width:28%;min-width:28%;padding-left:4%;">
						                		<span style="float:left;margin-right:5%">规划时间</span>
						                        <span>
						                         	<input type="date" name="PlanningTime" value="" id="PlanningTime" class="input_css PlanningTime">
						                         </span>
						                        <br>
						                		<span style="float:left;margin-right:5%">交付时间</span>
						                        <span><input type="date" name="LeadTime" value="" id="LeadTime" class="input_css LeadTime"></span>
						                        <br>
						                        <span style="float:left;margin-right:14%">备注</span>
						                         <span>
						                        <textarea style="margin-bottom:50px;" rows="6" cols="26" name="Remark" value="" id="Remark"></textarea>
						                         </span>
						                        <br>
						                        </div>
						                </div> -->
						                
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
							<input type="hidden" id="search_i_column" value="${column}">
							<input type="hidden" id="search_i_content" value="${content}">
							<input type="hidden" id="search_i_order" value="${order}">
							<div class="pageInfo">
								当前是第&nbsp;<span id="currentPage">${currentPage}</span>&nbsp;页,&nbsp;总计&nbsp;<span
									id="allPage">${pageCounts}</span>页
							</div>
							<div class="changePage">
								<input type="button" class="btn btn-primary" value="首页" id="fistPage" name="fistPage" onclick="FistPage()">
								<input type="button" class="btn btn-primary" value="上一页" id="upPage" onclick="UpPage()">
								<input type="button" class="btn btn-primary" value="下一页" id="nextPage" onclick="NextPage()"> 跳到第 <input type="text" id="jumpNumber" name="jumpNumber" class="jumpNumber"
									style="width: 40px;height: 30px;color: #000" onkeyup="value=value.replace(/[^\d]/g,'') "> 页 <input type="button" class="btn btn-primary" value="GO" id="Gotojump" name="Gotojump" onclick="PageJump()">
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
								<input type="file" name="file" id="Mail_fileToUpload"   value="添加附件"  class="up"/>
								<span class="intitle">点击上传文档、压缩包</span>
								<span class="mask"></span>
							</a>
						<!-- 修改结束位置 -->
						<input type="button" name="button" value="上传" class="bToggle" id="Mail_Send">
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
<script src="js/libs/bootstrap/bootstrap-grid-form-btn-res-icon-tooltip-popover.min.js" type="text/javascript" charset="utf-8"></script>
<script src="plugins/cookie/jquery.cookie.js"></script>
<!-- <script src="js/swiper-3.4.1.jquery.min.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/ajaxfileupload.js" type="text/javascript" charset="utf-8"></script>
<script src="js/msgbox.js"></script>
<script src="plugins/colResizable/colResizable-1.6.min.js"></script>
<script src="js/SoftwareProject.js"></script>
<script type="text/javascript">
/****************** 跳页 **********************/
var iicurrentPage = $("#search_i_currentPage").val();
var iipageCounts = $("#search_i_pageCounts").val();
var iicolumn = $("#search_i_column").val();
var iicontent = $("#search_i_content").val();
var iiorder = $("#search_i_order").val();

function FistPage() {
	window.location.href = 'SoftwareProject?currentPage=1&Column='+iicolumn+'&Content='+iicontent+'&Order='+iiorder;
}
function UpPage(arg) {
	var currentPage = Number(iicurrentPage) -1;
	window.location.href = 'SoftwareProject?currentPage='+currentPage+'&Column='+iicolumn+'&Content='+iicontent+'&Order='+iiorder;
}
function NextPage(arg) {
	var currentPage = Number(iicurrentPage) +1;
	window.location.href = 'SoftwareProject?currentPage='+currentPage+'&Column='+iicolumn+'&Content='+iicontent+'&Order='+iiorder;
}
function PageJump(arg) {
	var jumpNumber = document.getElementById("jumpNumber").value;
	if (jumpNumber == null || jumpNumber == 0) {
		jumpNumber = $('#currentPage').html();
	} else if (jumpNumber > parseInt($('#allPage').html())) {
		jumpNumber = $('#allPage').html();
	}
	window.location.href = 'SoftwareProject?currentPage='+jumpNumber+'&Column='+iicolumn+'&Content='+iicontent+'&Order='+iiorder;
}
function LastPage(arg) {
	var currentPage = Number(iipageCounts);
	window.location.href = 'SoftwareProject?currentPage='+currentPage+'&Column='+iicolumn+'&Content='+iicontent+'&Order='+iiorder;
}
</script>
</html>