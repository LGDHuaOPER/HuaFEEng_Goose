<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>软件实施管理</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="css/libs/bootstrap-grid-form-button-res-icon-list.min.css">
<link rel="stylesheet" type="text/css" href="plugins/awesomplete/awesomplete.css">
<link rel="stylesheet" type="text/css" href="plugins/awesomplete/awesomplete.theme.css">
<link rel="stylesheet" type="text/css" href="css/modules/software/mailSetting.css">
<link rel="stylesheet" type="text/css" href="css/Implementation.css?iv=201808231405">
<link rel="stylesheet" type="text/css" href="css/global/global_table_style.css">
<!-- <link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css"> -->
<!-- <link rel="stylesheet" type="text/css" href="css/global/eouluCustom.css"> -->
<style>
	.clear_float {
		height: 2px;
		width: calc(100% - 2px);
		clear:both;
	}

	.content {
		padding-bottom: 5px !important;
	}

	/*sticker布局*/
	html,body{
	    width:100%;
	    height:100%;
	}

	#implementation_wrapper {
	    position:fixed;
	    overflow:auto;
	    width:100%;
	    height:100%;
	    box-sizing:border-box;
	}

	#implementation_sticker {
	    width:100%;
	    min-height:100%;
	    box-sizing:border-box;
	}

	#implementation_sticker-con {
	    padding-bottom:40px;
	    box-sizing:border-box;
	}

	#implementation_footer {
	    margin-top:-40px;
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

	.ModalBox img {
		vertical-align: top;
	}

	.gl_table_style td, .gl_table_style th{
		max-width: 150px;
	}
</style>
</head>
<body>
<div id="implementation_wrapper">
	<div id="implementation_sticker">
		<div id="implementation_sticker-con">
			<%@include file="top.jsp"%>
			<div class="contain">
				<div class="content">
					<%@include file="nav.jsp"%>
					<div style="display: none">
						<input type="text" value="" name="query_type"> <input
							type="text" value="" name="classify1"> <input type="text"
							value="" name="classify2"> <input type="text" value=""
							name="parameter1"> <input type="text" value=""
							name="parameter2">
					</div>
					<div class="hideAuthorities" style="display:none;">
						<c:forEach var="authoritiy" items="${authorities}">
		                   	<c:if test="${authoritiy =='SoftwareImplementationOperate'}">
								  <div style="display:none;">${authoritiy}</div>
		                   	</c:if>
		               	</c:forEach> 
		           	</div>
		            <div class="saveSoftwareStaff" style="display: none;"></div>
						 <form action="SoftwareImplementation" method="GET" id="search_box"  style="position: absolute;clear:both;right: 5%;top: 60px;">
			            	<input type="text" placeholder="输入关键字查询" class="tsearch" name="content"  style="" value="${content}">
			            	<input type="text"  name="currentPage"  style="display:none;" value="${currentPage}">
			            	<input type="text"  name="order"  style="display:none;" value="${order}">
			            	<input type="text"  name="column"  style="display:none;" value="${column}">
			            	<input type="text"  name="queryType"  style="display:none;" value="select">
			            	<span class="iiisearch" style="right: 27%" onclick="Search()"><span class="glyphicon glyphicon-search"></span></span>
			            	<!-- <span class="ysearch" style="right: 27%" onclick="Search()"></span> -->
		            	</form>
		           	<div class="addBtnDiv">
		           		<input type="button" class="btn btn-info" value="添加">
		           		<input type="button" class="btn btn-info mailSetting_open" value="邮箱设置">
		           	</div>
		           	<!-- 表格开始 -->
		           	<div class="gl_table_style_wrapper">
		           		<table class="gl_table_style" id="table1">
		           			<thead>
		           				<tr class="firstTr">
		           					<th style="width:4.02%;" class="ID">序号</th>
		           					<th style="width:13.77%;" class="CustomerName">客户单位</th>
		           					<th style="width:6.06%;" class="Contact">客户名称</th>
		           					<th style="width:9.35%;" class="Machine">机台</th>
		           					<th style="width:11.81%;" class="SoftwareVersion" >软件版本</th>
		           					<th style="width:4.42%;" class="Type">类型</th>
		           					<th style="width:8.12%;" class="InstallTime">装机时间</th>
		           					<th style="width:7.28%;" class="ContinueTime">续期时间</th>
		           					<th style="width:17.8%;" class="progressStatus">进度</th>
		           					<th style="width:5.24%;" class="Engineer">工程师</th>
		           					<th style="width:6.06%;">详细记录</th>
		           					<th style="display: none"></th>
		           					<th style="display: none"></th>
		           					<th style="display: none"></th>
		           					<th style="display: none"></th>
		           					<th style="display: none">InquiryID</th>
		           					<th style="width: 6%" class="AreaName">客户区域</th>
		           				</tr>
		           			</thead>
		           			<tbody>
		           				<c:forEach var="orderInfo" items="${datas}" varStatus="status">
		           					<c:if test="${status.index>0}">
		           						<tr>
		           						    <td  class="editTd" value="${orderInfo['ID']}">${status.index+(currentPage-1)*10}</td><!-- 0 -->
		           							<td class="CustomerName overhid" title="${orderInfo['CustomerName']}">${orderInfo['CustomerName']}</td> 
		           							<td class="Contact" title="${orderInfo['Contact']}">${orderInfo['Contact']}</td> 
		           							<td class="Machine" title="${orderInfo['Machine']}">${orderInfo['Machine']}</td> 
		           							<td class="SoftwareVersion" title="${orderInfo['SoftwareVersion']}">${orderInfo['SoftwareVersion']}</td> 
		           							<td class="Type">${orderInfo['Type']}</td> 
		           							<td class="InstallTime">${orderInfo['InstallTime']}</td> 
		           							<td class="ContinueTime">${orderInfo['ContinueTime']}</td>
		           							<td class="ProgressStatus" title="${orderInfo['ProgressPercent']}">${orderInfo['ProgressPercent']}</td> 
		           							<td class="Engineer">${orderInfo['Engineer']}</td> 
		           							<td class="record"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></td>
		           							<td class="Progress" style="display:none;" value="${orderInfo}">${orderInfo['completeTime']}</td>
		           							<td class="Appointment" style="display:none;">${orderInfo['Appointment']}</td>
		           							<td class="Implementation" style="display:none;">${orderInfo['Implementation']}</td>
		           							<td class="Remarks" style="display:none;">${orderInfo['Remarks']}</td>
		           							<td class="alterInquiryID" style="display: none">${orderInfo['InquiryID']}</td>
		           							<td class="AreaNameTd">${orderInfo['AreaName']}</td>
		           						</tr>
		           					</c:if>
		           				</c:forEach>
		           			</tbody>
		           		</table>
		           	</div>
		           	<!-- 表格结束 -->
					
					<!--详细记录  模态框  -->
					<div class="ModalBox" style="display: none">
						<div class="ModalBox_title">软件实施详细管理</div>
						<div class="ModalBox_close">关闭</div>
						<div class="ModalBoxTitle" >软件实施详细管理记录</div>
						<div class="ModalBoxMsg">
							<div class="ModalBoxMsgList">
								<ul class="ModalBoxMsg_ul1">
									<li>
										<span class="ModalBoxMsg_span">机器码</span>
										<input type="text" name="MachineCode" value="" >
									</li>
									<li>
										<span class="ModalBoxMsg_span">SN</span>
										<input type="text" name="SN" value="" >
									</li>
									<li>
										<span class="ModalBoxMsg_span">注册码</span>
										<input type="text" name="RegistrationCode" value="" >
									</li>
								</ul>
								<ul class="ModalBoxMsg_ul2">
									<li>
										<span class="ModalBoxMsg_span">电子邮箱</span>
										<input type="text" name="Email" value="" >
									</li>
									<li>
										<span class="ModalBoxMsg_span">联系电话</span>
										<input type="text" name="ContactInfo" value=""  disabled="disabled">
									</li>
								</ul>
								<ul class="ModalBoxMsg_ul3">
									<li>
										<span class="ModalBoxMsg_span">售前调查表</span>
										<!-- <input type="text" name=PreSalesTable value="" readonly="readonly" > -->
										<form class="add_fileBox"  id="add_fileBox10"  method="post" target="myframe" action="SoftwareImplementationOperate" enctype="multipart/form-data" >
											<input class="add_fileCont PreSalesTable" readonly="readonly" />
											<span class="add_uploadText" title="上传"><img src="image/upload.png" height="20" width="18"></span>
											<span class="add_download" title="下载"><img src="image/download.png" height="20" width="18"></span>
										    <input class="add_change add_file" type="file" multiple="multiple" name="fileName"/>
										</form>
									</li>

									<!-- update -->
									<li>
										<span class="ModalBoxMsg_span">技术协议</span>
										<form class="add_fileBox"  id="add_fileBox4"  method="post" target="myframe" action="SoftwareImplementationOperate" enctype="multipart/form-data" >
											<input class="add_fileCont TechnologyProtocol" readonly="readonly" />
											<span class="add_uploadText" title="上传"><img src="image/upload.png" height="20" width="18"></span>
											<span class="add_download" title="下载"><img src="image/download.png" height="20" width="18"></span>
										    <input class="add_change add_file" type="file" multiple="multiple" name="fileName"/>
										</form>
									</li>
								</ul>
								<ul class="ModalBoxMsg_ul4">
									<li>
										<span class="ModalBoxMsg_span">装机报告</span>
										<form class="add_fileBox"  id="add_fileBox2"  method="post" target="myframe" action="SoftwareImplementationOperate" enctype="multipart/form-data" >
											<input class="add_fileCont InstallationReport" readonly="readonly"><!-- </span> -->
											<span class="add_uploadText" title="上传"><img src="image/upload.png" height="20" width="18"></span>
											<span class="add_download" title="下载"><img src="image/download.png" height="20" width="18"></span>
										    <input class="add_change add_file" type="file" multiple="multiple" name="fileName"/>
										</form>
									</li>
									<li>
										<span class="ModalBoxMsg_span">实施手册</span>
										<form class="add_fileBox"  id="add_fileBox3"  method="post" target="myframe" action="SoftwareImplementationOperate" enctype="multipart/form-data" >
											<input class="add_fileCont ImplementHandbook" readonly="readonly"><!-- </span> -->
											<span class="add_uploadText" title="上传"><img src="image/upload.png" height="20" width="18"></span>
											<span class="add_download" title="下载"><img src="image/download.png" height="20" width="18"></span>
										    <input class="add_change add_file" type="file" multiple="multiple" name="fileName"/>
										</form>
									</li>
									<li>
										<span class="ModalBoxMsg_span">应用脚本</span>
										<form class="add_fileBox"  id="add_fileBox5" method="post" target="myframe" action="SoftwareImplementationOperate" enctype="multipart/form-data" >
											<input class="add_fileCont ScriptBackup" readonly="readonly"><!-- </span> -->
											<span class="add_uploadText" title="上传"><img src="image/upload.png" height="20" width="18"></span>
											<span class="add_download" title="下载"><img src="image/download.png" height="20" width="18"></span>
											<!-- accept="application/x-zip-compressed,aplication/zip,application/octet-stream,application/x-rar-compressed" -->
										    <input class="add_change add_file" type="file" multiple="multiple" name="fileName" accept=".zip,.rar,.7z,.arj,.iso,.jar"/>
										</form>
									</li>
								</ul>
							</div>
							
							<div class="ServiceContentBox">
								<div class="ServiceContent_title"><span>服务内容登记</span>	<input type="button" value="添加" class="SerConBtn bToggle"></div>
								<div class="impl-add-table">
								<table cellspacing="0" cellspadding="0" id="ServiceContentTable" class="eou-table-collapse">
								<thead>
									<tr class="tHead" style="font-size:14px;font-weight: bold;background: rgba(235,235,228,0.8);height: 25px;">
										<th class="SerCon_Ser" style="width:3.9%;">序列</th>
										<th style="width:5.5%;">服务项</th>
										<th style="width:19.5%;">内容</th>
										<th style="width:7.5%;">期望时间</th>
										<th style="width:5.2%;">优先级</th>
										<th style="width:4.9%;">责任人</th>
										<th style="width:6.1%;">需求类型</th>
										<th style="width:17.3%;">Eoulu描述</th>
										<th style="width:7.1%;">登记时间</th>
										<th style="width:7.1%;">完成时间</th>
										<th style="width:16.1%;">服务完成报告</th>
									</tr>
								</thead>
								<tbody>
									
								</tbody>
									
								</table>
								</div>
							</div>
						</div>
						<div class="btnBox">
							<input type="button" value="保存" class="bToggle" id="ModalBox_submit">
							<input type="button" value="取消" class="bToggle" id="ModalBox_cancel">
						</div>
					</div>
					
					<!-- 添加模态框	 -->
					<div class="addImplementationOut" style="display: none">
					<div class="addImplementationBox">
						<div class="ModalBox_title">添加软件实施记录</div>
						<div class="ModalBox_close2">关闭</div>
						<div class="ModalBoxTitle" >添加软件实施记录</div>
						<div class="ShipnoticeMsg">
							<ul class="Shipnotice_leftul">
								<li style="position: relative;">
									<span class="Shipnotice_span addImplSpan" >客户单位</span>
									<input type="text" name="CustomerName" value="" id="addImplCusNameInput">
									<select multiple name="" id="addImplCusName" style="width:400px;height:100px;position: absolute;top: 24px;left: 28%;z-index:100;font-size: 12px;display: none;">
									</select>
								</li>
								<li>
									<span class="Shipnotice_span">客户姓名</span>
									<input type="text" class="Contact" value="" disabled="disabled">
								</li>
								<li>
									<span class="Shipnotice_span">机台</span>
									<input type="text" class="Machine" value="">
								</li>
								<li>
									<span class="Shipnotice_span">软件版本</span>
									<input type="text" class="SoftwareVersion" value="">
								</li>
							</ul>
							<ul class="Shipnotice_rightul">
								<li>
									<span class="Shipnotice_span">类型</span>
									<select class="Type">
										<option value="试用">试用</option>
										<option value="合同">合同</option>
									</select>
								</li>
								<li>
									<span class="Shipnotice_span">装机时间</span>
									<input type="date" class="InstallTime" value="">
								</li>
								<li>
									<span class="Shipnotice_span">续期时间</span>
									<input type="date" class="ContinueTime" value="">
								</li>
								<li>
									<span class="Shipnotice_span">工程师</span>
									<!-- <input type="text" class="Engineer addImplEngInput" value=""> -->
									<input type="text" class="addEnginerrInput">
									<select class="addEngineer" multiple style="width: 248px;height: 100px;"></select>
								</li>
							</ul>
						</div>
						<div class="btnBox">
							<input type="button" value="保存" class="bToggle" id="modify_submit2">
							<input type="button" value="取消" class="bToggle" id="modify_cancel2">
						</div>
					</div>
					</div>
					
					<!--修改  模态框  -->
					<div class="ModifyBox" style="display: none">
						<div class="ModalBox_title">修改软件实施记录</div>
						<div class="ModalBox_close">关闭</div>
						<div class="ModalBoxTitle" >修改软件实施记录</div>
						<div class="ShipnoticeMsg">
							<ul class="Shipnotice_leftul">
								<li style="position:relative;">
									<span class="Shipnotice_span">客户单位</span>
									<input type="text" name="CustomerName" value="" disabled="disabled" id="alterImplCusNameInput">
									<select multiple name="" id="alterImplCusName" style="width:400px;height:100px;position: absolute;top: 24px;left: 28%;z-index:100;font-size: 12px;display: none;">
									</select>
								</li>
								<li>
									<span class="Shipnotice_span">客户姓名</span>
									<input type="text" class="Contact" value="" disabled="disabled">
								</li>
								<li>
									<span class="Shipnotice_span">机台</span>
									<input type="text" class="Machine" value="">
								</li>
								<li>
									<span class="Shipnotice_span">软件版本</span>
									<input type="text" class="SoftwareVersion" value="">
								</li>
							</ul>
							<ul class="Shipnotice_rightul">
								<li>
									<span class="Shipnotice_span">类型</span>
									<select class="Type">
										<option value="试用">试用</option>
										<option value="合同">合同</option>
									</select>
								</li>
								<li>
									<span class="Shipnotice_span">装机时间</span>
									<input type="date" class="InstallTime" value="">
								</li>
								<li>
									<span class="Shipnotice_span">续期时间</span>
									<input type="date" class="ContinueTime" value="">
								</li>
								<li>
									<span class="Shipnotice_span">工程师</span>
									<!-- <input type="text" class="Engineer" value=""> -->
									<input type="text" class="alterEnginerrInput">
									<select class="alterEngineer" multiple style="width: 248px;height: 100px;"></select>
								</li>
							</ul>
						</div>
						<div class="btnBox">
							<input type="button" value="保存" class="bToggle" id="modify_submit">
							<input type="button" value="取消" class="bToggle" id="modify_cancel">
						</div>
					</div>
					
					<iframe style="display:none;" name="myframe"></iframe>  <!-- 表单提交 -->
					
					<c:set var="queryUrl" value="SoftwareImplementation?currentPage="></c:set>
					
					<div class="gl_table_page_wrapper">
						<div class="gl_table_page">
							<div class="pageInfo">当前是第&nbsp;<span id="currentPage">${currentPage}</span>&nbsp;页,&nbsp;总计&nbsp;<span id="allPage">${pageCounts}</span>页
							</div>
							<div class="changePage">
								<input type="button" class="btn btn-primary" value="首页" id="fistPage" name="fistPage" onclick="FistPage('${queryUrl}')">
								<input type="button" class="btn btn-primary" value="上一页" id="upPage" onclick="UpPage('${queryUrl}${currentPage-1 }')">
								<input type="button" class="btn btn-primary" value="下一页" id="nextPage" onclick="NextPage('${queryUrl}${currentPage+1 }')"> 跳到第 <input type="text" id="jumpNumber" name="jumpNumber" class="jumpNumber"
									style="width: 30px; color: #000" onkeyup="value=value.replace(/[^\d]/g,'') "> 页 <input type="button" class="btn btn-primary" value="GO" id="Gotojump" name="Gotojump" onclick="PageJump('${queryUrl}')">
								<input type="button" class="btn btn-primary" value="尾页" id="lastPage" name="lastPage" onclick="LastPage('${queryUrl}')">
							</div>
						</div>
					</div>

					<div class="clear_float"></div>
				</div>
			</div>
			<div class="dropFileBox" style="display: none">
				<div class="dropFile">
					<div class="dropFileTit">
						上传服务完成报告<span>关闭</span>
					</div>
					<div class="dropFileCon">
						<div class="dropFileConTop" id="dropFileConTop">
							<div class="fileName1" style="font-size:15px;height:60px;line-height:70px"></div>
							<div class="uploadInfo" style="height:190px;line-height:190px">将文件拖拽至此区域，即可上传！</div>
						</div>
						<div class="dropFileConMid">
							<div class="dropFileConMidTop">
								<input type="button" class="eou-button eou-button-radius eou-button-20 eou-button-w60 preUpload1" value="浏览..." title="选择文件">
								<input type="text" readonly="readonly" class="serFinRepUploadName" placeholder="未选择文件"><span class="isUpload"></span>
								<input type="file" id="serFinRepUpload">
							</div>
							<div class="dropFileConMidMid">
								<div class="progressOut"></div>
								<div class="progressIn">0%</div>
							</div>
							<div class="dropFileConMidbot">
								<input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60 dropUp2" value="上传">
							</div>
						</div>
					</div>
					<div class="toggleFileUp"><input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w100 dropUp1" value="切换到文件上传"></div>
				</div>
			</div>
				<div class="cover-color" style="display: none"></div>
				<!-- 甘特图 -->
				<div class="gantt_Wrapper">
					<div class="gantt_Wrapper_top">
						<div class="gantt_Wrapper_top_l">软件实施管理进度甘特图</div>
						<div class="gantt_Wrapper_top_m">导出PDF</div>
						<div class="gantt_Wrapper_top_r">关闭</div>
					</div>
					<div class="gantt_Wrapper_body">
						<div id="gantt_container"></div>
					</div>
					<!-- <div class="gantt_Wrapper_foot" >
						<input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w80" value="导出PDF">
					</div> -->
				</div>

				<div class="mailSetting_div_cover"></div>
				<div class="mailSetting_div" id="mailSetting_classify" data-iiclassify="implementation">
					<!-- 下面是动态插入内容 -->
					<!-- 上面是动态插入内容 -->
				</div>

		<!-- implementation_sticker-con结束 -->
		</div>
	<!-- implementation_sticker结束 -->
	</div>
	<!-- implementation_footer -->
	<div id="implementation_footer">
		<div id="eoulu-copy-out" style="height:40px;width:calc(100% - 2px);">
			<div style="width:100%;height:5px;"></div>
			<div id="eoulu-copy" style="width:100%;height:35px;font-size:12px;color:#888;line-height:35px;z-index: 2;">
				<hr style="height:1px;color:#999;width: calc(100% - 3px);" />
				<div style="width:100%;text-align:center;display:inline-block;">Copyright  ©&nbsp;<span class="YEAR">2018</span>&nbsp<a href="http://www.eoulu.com/h-col-268.html" class="EHref" target="_blank" style="color:blue;">Eoulu</a> Tech. Co.,Ltd.</div>
			</div>
		</div>
	</div>
<!-- implementation_wrapper结束 -->
</div>
</body>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<!-- <script src="js/libs/jquery-3.3.1.min.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/libs/bootstrap/bootstrap-grid-form-button-res-icon-list.min.js"></script>
<script src="js/msgbox.js"></script>
<!-- <script src="js/msgbox_unload.js"></script> -->
<!-- <script src="js/global/myFunction.js"></script> -->
<script src="js/global/responseLoading.js"></script>
<script src="plugins/highcharts/highcharts-gantt.src.js"></script>
<script src="plugins/highcharts/exporting.js"></script>
<script src="plugins/highcharts/offline-exporting.js"></script>
<!-- 需要保存导出功能模块文件是在 highcharts.js 之后引入 -->
<!-- <script src="http://cdn.hcharts.cn/highcharts/modules/exporting.js"></script> -->
<!-- 客户端导出功能模块为可选选项 -->
<!-- <script src="http://cdn.hcharts.cn/highcharts/modules/offline-exporting.js"></script> -->
<script src="plugins/export2pdf/html2canvas.min.js"></script>
<script src="plugins/export2pdf/jspdf.min.js"></script>
<script src="plugins/awesomplete/awesomplete.min.js"></script>
<script src="js/modules/software/mailSetting.js"></script>
<script src="js/Implementation.js?iv=201808231406"></script>
<script type="text/javascript">
	function FistPage(arg) {
		var orderPara = $("#search_box input[name='order']").val();
		var columnPara = $("#search_box input[name='column']").val();
		window.location.href = arg + "1&order="+orderPara+"&column="+columnPara;
	}
	function UpPage(arg) {
		var orderPara = $("#search_box input[name='order']").val();
		var columnPara = $("#search_box input[name='column']").val();
		window.location.href = arg + "&order="+orderPara+"&column="+columnPara;
	}
	function NextPage(arg) {
		var orderPara = $("#search_box input[name='order']").val();
		var columnPara = $("#search_box input[name='column']").val();
		window.location.href = arg + "&order="+orderPara+"&column="+columnPara;
	}
	function PageJump(arg) {
		var orderPara = $("#search_box input[name='order']").val();
		var columnPara = $("#search_box input[name='column']").val();
		var jumpNumber = document.getElementById("jumpNumber").value;
		if (jumpNumber == null || jumpNumber == 0) {
			jumpNumber = $('#currentPage').html();
		} else if (jumpNumber > parseInt($('#allPage').html())) {
			jumpNumber = $('#allPage').html();
		}
		window.location.href = arg + jumpNumber + "&order="+orderPara+"&column="+columnPara;
	}
	function LastPage(arg) {
		var orderPara = $("#search_box input[name='order']").val();
		var columnPara = $("#search_box input[name='column']").val();
		var jumpNumber = parseInt($('#allPage').html());
		window.location.href = arg + jumpNumber + "&order="+orderPara+"&column="+columnPara;
	}

	// 翻页组件按钮逻辑
	// flag 为按钮ID后缀  如 pageStyle(CurrentPage, pageCount, "2");
	function pageStyle(currentPage, pageCounts, flag){
	    flag = flag || "";
	    if(pageCounts == 1){
	        $("#fistPage"+flag+", #upPage"+flag+", #nextPage"+flag+", #lastPage"+flag+", #Gotojump"+flag).prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
	    }else if(currentPage == 1){
	        $("#fistPage"+flag+", #upPage"+flag).prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
	        $("#lastPage"+flag+", #nextPage"+flag+", #Gotojump"+flag).prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
	    }else if(currentPage == pageCounts){
	        $("#lastPage"+flag+", #nextPage"+flag).prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
	        $("#fistPage"+flag+", #upPage"+flag+", #Gotojump"+flag).prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
	    }else{
	        $("#lastPage"+flag+", #nextPage"+flag+", #fistPage"+flag+", #upPage"+flag+", #Gotojump"+flag).prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
	    }
	}

	$(function() {
		if($(".hideAuthorities").children().length != 0){
			$(".editTd").addClass("toggleedit");
			$("#ModalBox_submit").show();
		}
		else{
			$(".editTd").removeClass("toggleedit");
			$("#ModalBox_submit").hide();
		}
		
		$("#table1 .Machine, #table1 .SoftwareVersion, #table1 .Type, #table1 .Engineer, #table1 .CustomerName, #table1 .Contact, #table1 .AreaNameTd").each(function(){
			var aa = $(this).text();
			if(aa==""||aa=="--"||aa=="0"||aa==undefined){
				aa='';
			}
			$(this).text(aa);
		});

		$("#table1 .InstallTime, #table1 .ContinueTime").each(function(){
			var aa = $(this).text();
			if(aa==""||aa=="--"||aa=="0000-00-00"||aa==undefined){
				aa='';
			}
			$(this).text(aa);
		});

		pageStyle(Number($('#currentPage').html()), Number($('#allPage').html()), "");

		$("#table1 .Progress").each(function(){
			var curText = $(this).text();
			if(curText==""||curText=="--"||curText=="0000-00-00"||curText==null||curText==undefined){
				$(this).siblings("td.ProgressStatus").css("color","rgba(255,0,0,0.8)");
			}else{
				$(this).siblings("td.ProgressStatus").css("color","#000");
			}
		});

		$("#table1 .ProgressStatus").each(function(){
			var curText = $(this).text();
			if(curText==""||curText=="--"||curText==null||curText==undefined){
				$(this).text("0%");
			}else{
				$(this).text(curText+"%");
			}
			var that = $(this);
			setTimeout(function(){
			    that.css({
			        "opacity":"1"
			    });
			},50);
		});

		var iorder = $("input[name='order']").val();
		var icolumn = $("input[name='column']").val();
		if(icolumn!=""&&icolumn!="--"){
			if(iorder.toUpperCase()=="DESC"){
				$("#table1 .firstTr th."+icolumn).append('<span class="glyphicon glyphicon-sort-by-attributes-alt" aria-hidden="true"></span>');
			}else if(iorder.toUpperCase()=="ASC"){
				$("#table1 .firstTr th."+icolumn).append('<span class="glyphicon glyphicon-sort-by-attributes" aria-hidden="true"></span>');
			}
		}

	});
</script>
</html>

