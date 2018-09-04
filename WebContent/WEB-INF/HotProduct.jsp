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
<title>热销产品</title>
<link rel="shortcut icon" href="image/eoulu.ico" />
<link rel="bookmark" href="image/eoulu.ico" />
<!-- <link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css"> -->
<!-- <link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" /> -->
<link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css">
<!-- <link rel="stylesheet" type="text/css" href="css/libs/bootstrap-form-btn-res-icon-nav-navbar-advancedbtn-togtab.min.css"> -->
<!-- <link rel="stylesheet" type="text/css" href="css/salesStatistics.css"> -->
<link rel="stylesheet" type="text/css" href="css/HotProduct.css?iv=201808241502">
<link rel="stylesheet" type="text/css" href="css/global/global_table_style.css?iv=201808241502">
<style>
	.content {
		padding-bottom: 5px !important;
	}
	
	.g-nav-ul li {
		height: 80px !important;
		line-height: 80px !important;
	}

	.changeBox {
		width: 90%;
		margin: 0 auto;
	}

	html, body, .content {
		background-color: #eee !important;
	}

	hr {
		margin-top: 1px;
		margin-bottom: 1px;
	}

	/*bs组件 nav 去除项目样式*/
	ul.nav {
		clear: none !important;
		min-width: none !important;
		width: auto !important;
		height: auto !important;
		line-height: 1.42857143 !important;
		margin: 0 0 !important;
		background: transparent !important;
	}

	ul.nav li a {
		color: #337ab7 !important;
		cursor: pointer !important;
	}

	ul.nav li.active a {
		color: #555 !important;
		cursor: default !important;
	}
	/*bs组件 nav 去除项目样式 end*/

	.tab_wrapper>ul {
		width: 90% !important;
		margin: 0 auto !important;
	}

	.time_search_div, .classify_search_div {
		height: 34px;
		width: 90%;
		margin: 10px auto 15px auto;
		text-indent: 5px;
	}

	.classify_search_div {
		display: none;
	}

	.classify_search_div .dropdown-toggle {
		height: 34px;
	}

	ul.dropdown-menu {
		width: 100px;
		min-width: 100px;
		padding-left: 7px;
		padding-right: 8px;
	}

	ul.dropdown-menu>li:not(:last-child) {
		border-bottom: 1px solid #ddd;
	}

	ul.dropdown-menu li {
		padding-left: 2px;
		padding-top: 3px;
		padding-bottom: 3px;
		cursor: pointer;
	}

	.time_search_div_l {
		display: inline-block;
		width: 280px;
	}

	.time_search_div_l>input {
		cursor: pointer;
		background-color: #fff !important;
	}

	.time_search_div_r {
		display: inline-block;
		font-size: 20px;
	}

	.time_search_div_r>button>span {
		margin-right: 4px;
	}

	.content {
		min-width: 1111px;
	}

	#classify_search, .model_search {
		display: none;
	}

	/*可拖动的innerHtml*/
	.glyphicon.glyphicon-resize-horizontal {
		margin-left: -3px;
	    position: relative;
	}

	div.JCLRgrip {
		z-index: 2 !important;
	}

	/*覆盖样式*/
	.m-nav-div h5, .u-admin h5 {
		margin-top: 0px;
		margin-bottom: 0px;
	}

	/*自定义主页面表格*/
	#global_table_style tbody tr {
		height: 36px;
	}

	#global_table_style th:nth-child(1) {
		min-width: 60px;
		max-width: 60px;
		width: 6.0%;
	}

	#global_table_style th:nth-child(4) {
		min-width: 400px;
		max-width: 440px;
		width: 40%;
	}

	#global_table_style th>span, .gl_table_style th span.glyphicon {
		display: none;
	}

	th span.glyphicon {
		margin-left: 2px;
	}

	.gl_table_style th[class^="col_"] {
		cursor: pointer;
	}

	.gl_table_style td {
		max-width: 150px;
	}

	.gl_table_style th:nth-child(1), .gl_table_style td:nth-child(1) {
		max-width: 60px;
		min-width: 60px;
		width: 60px;
	}

	.gl_table_style th:nth-child(5), .gl_table_style td:nth-child(5), .gl_table_style th:nth-child(6), .gl_table_style td:nth-child(6), .gl_table_style th:nth-child(7), .gl_table_style td:nth-child(7) {
		max-width: 120px;
		min-width: 120px;
		width: 120px;
	}

	/*动画class*/
	#classify_search.runleft_animate, .model_search.runleft_animate {
		animation:runleft 1s infinite;
		-webkit-animation:runleft 1s infinite; /*Safari and Chrome*/
	}

	.model_search.runleft_animate1 {
		animation:runleftq 1s infinite;
		-webkit-animation:runleftq 1s infinite; /*Safari and Chrome*/
		animation-delay:1s;
		-webkit-animation-delay:1s;
	}

	#classify_search.runleft_animate2 {
		animation:runleftw 1s infinite;
		-webkit-animation:runleftw 1s infinite; /*Safari and Chrome*/
		animation-delay:1s;
		-webkit-animation-delay:1s;
	}

	@keyframes runleftq {
		from {width: 0px;}
		to {width: 100%;}
	}

	/*Safari and Chrome*/
	@-webkit-keyframes runleftq {
		from {width: 0px;}
		to {width: 100%;}
	}

	@keyframes runleftw {
		from {width: 0px;}
		to {width: 100%;}
	}

	/*Safari and Chrome*/
	@-webkit-keyframes runleftw {
		from {width: 0px;}
		to {width: 100%;}
	}

	@keyframes runleft {
		from {width: 100%;}
		to {width: 0px;}
	}

	/*Safari and Chrome*/
	@-webkit-keyframes runleft {
		from {width: 100%;}
		to {width: 0px;}
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
			<!-- 表格 -->
			<!-- <div class="hot_search">
		<input type="date" class="start_time">
		<input type="date" class="end_time">
		<span class="tongji">销量统计</span>
	</div> -->
			<!-- <form id="top_text_from" name="top_text_from" method="get" action="HotProduct">
				<div class="hot_search">
					<input type="date" name="StartTime" value="${StartTime}"
						class="start_time" id="start_time" > <input type="date" class="end_time"
						name="EndTime" value="${EndTime}"> <input type="button"
						value="销量统计" class="tongji" id="search" onclick="INSearch()">
				</div>
			</form> -->
			<input type="hidden" value="${StartTime}" class="hotpro_StartTime">
			<input type="hidden" value="${EndTime}" class="hotpro_EndTime">
			<input type="hidden" value="${classify}" class="hotpro_classify">
			<input type="hidden" value='${rada}' class="hotpro_rada">
			<div class="time_search_div">
				<div class="time_search_div_l">
					<input type="text" id="time_search_input" class="form-control" readonly="readonly">
				</div>
				<div class="time_search_div_r">
					<button type="button" class="btn btn-info"><span class="glyphicon glyphicon-usd" aria-hidden="true"></span>销量统计搜索</button>
				</div>
			</div>
			<div class="classify_search_div">
				<div class="form-inline">
					<div class="form-group">
						<div class="input-group">
							<!-- <span class="input-group-addon">字段</span> -->
						  	<div class="input-group-btn">
						    	<button type="button" class="btn btn-info" title="选择字段">选择字段</button>
						    	<button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						    	  	<span class="caret"></span>
						    	  	<span class="sr-only">选择字段</span>
						    	</button>
						    	<ul class="dropdown-menu">
			                        <!-- <li title="选择字段">选择字段</li> -->
			                        <li title="产品类型">产品类型</li>
			                        <li title="产品型号">产品型号</li>
			                    </ul>
						  	</div><!-- /btn-group -->
						  	<select class="form-control" id="classify_search">
						  		<option value="" disabled>请选择</option>
						  		<option value="探针台">探针台</option>
						  		<option value="射频探针">射频探针</option>
						  		<option value="直流探针">直流探针</option>
						  		<option value="线缆">线缆</option>
						  		<option value="定位器">定位器</option>
						  		<option value="校准片">校准片</option>
						  		<option value="其他">其他</option>
						  	</select>
						  	<input type="text" class="form-control model_search" aria-label="...">
						</div><!-- /input-group -->
					</div><!-- form-group -->
					<button type="button" class="btn btn-info classify_search_y">搜索</button>
					<button type="button" class="btn btn-info classify_search_n">取消</button>
				</div>
			</div>

			<div class="tab_wrapper">
				<ul class="nav nav-tabs" role="tablist">
				    <li role="presentation" class="active"><a href="#statistics_a" aria-controls="statistics_a" role="tab" data-toggle="tab">热销产品</a></li>
				    <li role="presentation"><a href="#radar_a" aria-controls="radar_a" role="tab" data-toggle="tab">雷达图</a></li>
				    <li role="presentation"><a href="#calendarSale_a" aria-controls="calendarSale_a" role="tab" data-toggle="tab">历年销量对比</a></li>
				</ul>

				<div class="tab-content">
			    	<div role="tabpanel" class="tab-pane fade in active" id="statistics_a">
			    		<!--热销产品  -->
			    		<div class="hot_content" id="global_table_style_wrapper" style="min-height: 450px;">
			    			<table class="table_title" id="global_table_style">
			    				<thead>
			    					<tr class="title">
			    						<th class="xuhao">序号</th>
			    						<th class="xinghao">产品型号<span class="glyphicon glyphicon-sort-by-attributes-alt" aria-hidden="true"></span></th>
			    						<th class="ProductType">产品类型</th>
			    						<th class="miaoshu">描述<span class="glyphicon glyphicon-sort-by-attributes-alt" aria-hidden="true"></span></th>
			    						<th class="shuliang">历史销量<span class="glyphicon glyphicon-sort-by-attributes-alt" aria-hidden="true"></span></th>
			    						<th class="liebiaojian">列表价<span class="glyphicon glyphicon-sort-by-attributes-alt" aria-hidden="true"></span></th>
			    						<th class="jiazhi">价值当量<span class="glyphicon glyphicon-sort-by-attributes-alt" aria-hidden="true"></span></th>
			    					</tr>
			    				</thead>
			    				<tbody>
			    					<c:forEach var="orderInfo" items="${sales}" varStatus="status">
			    						<c:if test="${status.index>0}">
			    							<tr class="tr_body">
			    								<td class="xuhao" value="${orderInfo['ID']}">${status.index+(currentPage-1)*10}</td>
			    								<%-- <td> <i class="fa fa-edit contract-edit" value="${orderInfo['ID']}"></i></td> --%>
			    								<td class="xinghao" title="${orderInfo['Model']}">${orderInfo['Model']}</td>
			    								<td class="ProductType" title="${orderInfo['ProductCategory']}">${orderInfo['ProductCategory']}</td>
			    								<td class="miaoshu" title="${orderInfo['Description']}">${orderInfo['Description']}</td>
			    								<td class="shuliang" title="${orderInfo['Quantity']}">${orderInfo['Quantity']}</td>
			    								<td class="liebiaojian" title="${orderInfo['CostPrice']}">${orderInfo['CostPrice']}</td>
			    								<td class="jiazhi" title="${orderInfo['TotalPrice']}">${orderInfo['TotalPrice']}</td>
			    							</tr>
			    						</c:if>
			    					</c:forEach>
			    				</tbody>
			    			</table>
			    		</div><!--  表格结束-->
			    		<div id="glbal_table_page_wrapper">
			    			<div id="glbal_table_page">
			    				<div class="pageInfo">
			    					当前是第&nbsp;<span id="currentPage">${currentPage}</span>&nbsp;页,&nbsp;总计&nbsp;<span
			    						id="allPage">${pageCounts}</span>页
			    				</div>
			    				<div class="changePage">
			    					<input type="button" class="btn btn-primary" value="首页" id="fistPage"
			    						name="fistPage" onclick="FistPage('${queryUrl}${classify}')">
			    					<input type="button" class="btn btn-primary" value="上一页" id="upPage"
			    						onclick="UpPage('${queryUrl}${currentPage-1}${classify}')">
			    					<input type="button" class="btn btn-primary" value="下一页" id="nextPage"
			    						onclick="NextPage('${queryUrl}${currentPage+1}${classify}')">
			    					跳到第 <input type="text" id="jumpNumber" name="jumpNumber"
			    						class="jumpNumber" style="width: 30px;height: 25px; color: #000"
			    						onkeyup="value=value.replace(/[^\d]/g,'') "> 页 <input
			    						type="button" class="btn btn-primary" value="GO" id="Gotojump"
			    						name="Gotojump" onclick="PageJump('${queryUrl}${classify}')">
			    					<input type="button" class="btn btn-primary" value="尾页" id="lastPage"
			    						name="lastPage"
			    						onclick="LastPage('${queryUrl}${pageCounts}${classify}')">
			    				</div>
			    			</div>
			    		</div><!-- glbal_table_page_wrapper end -->
			    	</div>
			    	<div role="tabpanel" class="tab-pane fade" id="radar_a">
			    		<!--雷达图  -->
			    		<div class="bgLarge">
			    			<div class="bgLargeText">
			    			<div class="bgLargeTextTit">友情提示</div>
			    			<div class="bgLargeTextCont"><span></span></div>
			    			</div>
			    		</div>
			    		<div class="RadarChart" style="min-width: 1220px;width: 90%;height: 454px; margin: 0 auto; background: #fff;clear: both;">
			    			<div class="m-lefttop">
			    				<div class="SelectBox" style="margin: 30px 0 30px 10px;">
			    					<select class="TypeSel">
			    						<option selected="selected" disabled="disabled"
			    							class="firstOption">产品类型</option>
			    						<option>探针台</option>
			    						<option>射频探针</option>
			    						<option>直流探针</option>
			    						<option>线缆</option>
			    						<option>定位器</option>
			    						<option>校准片</option>
			    						<option>其他</option>
			    					</select> <select class="CurrentTypeSel">
			    						<option class="firstOption">产品型号</option>
			    					</select> 
			    					<input type="button" value="添加" class="Applyadd bToggle"
			    						disabled="disabled"
			    						style="width: 50px; height: 30px; font-size: 16px; margin-left: 10px;">
			    					<input type="button" value="计算" class="Apply bToggle"
			    						disabled="disabled"
			    						style="width: 50px; height: 30px; font-size: 16px; margin-left: 10px;">
			    				</div>
			    				<div class="WeightsTabBox">
			    					<table class="WeightsTab" border="1" cellspacing="0"
			    					cellspadding="0">
			    					<tr class="WeightsTab_title"
			    						style="height: 30px; background: #bfbfbf;">
			    						<td class="">类名</td>
			    						<!-- <td class="" >产品</td> -->
			    						<td class="">单价</td>
			    						<td class="">历史销量</td>
			    						<td class="">当量</td>
			    						<td class="">分布</td>
			    						<td class="">出货</td>
			    					</tr>
			    					<tr class="WeightsTr" style="height: 30px;">
			    						<td class="SetWeights"><input type="button"
			    							class="SetWeightsBtn" value="设置权重"
			    							style="width: 88%; height: 21px;"></td>
			    						<!-- <td class="Weights_Name" contenteditable="true"></td> -->
			    						<td class="Weights_UnitPrice WeightsValue" contenteditable="true"></td>
			    						<td class="Weights_Num WeightsValue" contenteditable="true"></td>
			    						<td class="Weights_Equivalent WeightsValue"
			    							contenteditable="true"></td>
			    						<td class="Weights_Distributed WeightsValue"
			    							contenteditable="true"></td>
			    						<td class="Weights_Shipping WeightsValue" contenteditable="true"></td>
			    					</tr>
			    					</table>
			    				</div>
			    			</div>
			    			<!-- 雷达图渲染 -->
			    			<div class="RadarChart_right" id="RadarChart_right">
			    				<div style="width: 100%; height: calc(100% - 50px);" id="RadarMap"></div>
			    				<!-- <div style="width:100%;height:50px;line-height:50px;position:absolute;bottom:0;text-align:center;font-size:24px;color:#ff0000;">综合评定：<span class="TotalScore">100</span>分</div> -->
			    			</div>
			    			<div class="RadarChart_left">
			    				<div class="RadarChartTableBox">
			    					<table class="RadarChartTable" border="1" cellspacing="0"
			    						cellspadding="0">
			    						<tr class="RadarChartTable_title">
			    							<td class="">类名</td>
			    							<td class="">产品</td>
			    							<td class="display1">单价评分</td>
			    							<td class="display1">历史销量评分</td>
			    							<td class="display1">当量评分</td>
			    							<td class="display1">分布评分</td>
			    							<td class="display1">出货评分</td>
			    							<td class="sortTd" style="cursor: pointer;" title="排序">
												<button type="button" class="btn btn-default" style="background-color: rgba(249, 249, 249, 0.4);"><span class="glyphicon glyphicon-sort" aria-hidden="true"></span>综合评分</button>
			    								<!-- <span
			    								style="width: 60%; display: inline-block;">综合评分</span>
			    							<div style="width: 20%; display: inline-block;">
			    									<img src="image/sort.png">
			    							</div> -->
			    							</td>
			    							<td class="delAll" style="cursor: pointer;" title="撤销所有">
												<button type="button" class="btn btn-default" style="background-color: rgba(249, 249, 249, 0.4);"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span>撤销</button>
			    							<!-- <span
			    								style="width: 60%; display: inline-block;">撤销</span>
			    							<div style="width: 30%; display: inline-block;">
			    									<img src="image/delete.png"
			    										style="vertical-align: text-bottom;">
			    								</div> -->
			    							</td>
			    						</tr>
			    					</table>
			    				</div>
			    				<div style="height: 302px;max-height: 302px;overflow-y: auto;overflow-x: hidden;">
			    					<table class="RadarChartTableCont" border="1" cellspacing="0"
			    						cellspadding="0" id="RadarChartTableCont">
										<tbody></tbody>
			    					</table>
			    				</div>
			    			</div>
			    		</div><!-- RadarChart_div end -->
			    	</div>

			    	<!-- 历年销量对比 -->
					<div role="tabpanel" class="tab-pane fade" id="calendarSale_a">
						<!-- 表格开始 -->
						<div class="gl_table_style_wrapper" style="min-height: 450px;">
							<table class="gl_table_style">
								<thead>
									<tr>
										<th>序号</th>
										<th>产品类型</th>
										<th>产品型号</th>
										<th class="col_">历年总销量<span class="glyphicon glyphicon-sort-by-attributes-alt" aria-hidden="true"></span></th>
										<th class="col_Year_2016">2016年销量<span class="glyphicon glyphicon-sort-by-attributes-alt" aria-hidden="true"></span></th>
										<th class="col_Year_2017">2017年销量<span class="glyphicon glyphicon-sort-by-attributes-alt" aria-hidden="true"></span></th>
										<th class="col_Year_2018">2018年销量<span class="glyphicon glyphicon-sort-by-attributes-alt" aria-hidden="true"></span></th>
										<th>列表价</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</div>					
						<div class="gl_table_page_wrapper">
							<div class="gl_table_page">
								<div class="pageInfo">当前是第&nbsp;<span id="currentPage2"></span>&nbsp;页,&nbsp;总计&nbsp;<span id="allPage2"></span>页
								</div>
								<div class="changePage">
									<input type="button" class="btn btn-primary" value="首页" id="fistPage2">
									<input type="button" class="btn btn-primary" value="上一页" id="upPage2">
									<input type="button" class="btn btn-primary" value="下一页" id="nextPage2">
									跳到第 <input type="text" id="jumpNumber2" style="width: 30px;height: 25px; color: #000" onkeyup="value=value.replace(/[^\d]/g,'') "> 页 <input type="button" class="btn btn-primary" value="GO" id="Gotojump2">
									<input type="button" class="btn btn-primary" value="尾页" id="lastPage2">
								</div>
							</div>
						</div><!-- gl_table_page_wrapper end -->
					</div><!-- 历年销量对比end -->
			    	
		  		</div>
			</div>
			<!-- Nav tabs -->
			  <!-- <ul class="nav nav-tabs" role="tablist">
			    <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">Home</a></li>
			    <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">Profile</a></li>
			    <li role="presentation"><a href="#messages" aria-controls="messages" role="tab" data-toggle="tab">Messages</a></li>
			    <li role="presentation"><a href="#settings" aria-controls="settings" role="tab" data-toggle="tab">Settings</a></li>
			  </ul> -->

		  	<!-- Tab panes -->
		  	<!-- <div class="tab-content">
		    	<div role="tabpanel" class="tab-pane active" id="home">...</div>
		    	<div role="tabpanel" class="tab-pane" id="profile">...</div>
		    	<div role="tabpanel" class="tab-pane" id="messages">...</div>
		    	<div role="tabpanel" class="tab-pane" id="settings">...</div>
		  	</div> -->
			
		</div>
	</div>
	<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
	<!-- <script src="plugins/cookie/jquery.cookie.js"></script> -->
	<script src="js/libs/bootstrap.min.js"></script>
	<!-- <script src="js/libs/bootstrap/bootstrap-form-btn-res-icon-nav-navbar-advancedbtn-togtab.min.js"></script> -->
	<!-- <script src="js/echarts3.js" type="text/javascript" charset="utf-8"></script> -->
	<!-- <script src="plugins/echarts/map_can/echarts-all-min.js" type="text/javascript" charset="utf-8"></script> -->
	<script src="plugins/echarts/echarts-4.1.0.rc2.min.js" type="text/javascript" charset="utf-8"></script>
	<!-- <script src="js/msgbox_unload.js" charset="utf-8"></script> -->
	<script src="js/msgbox.js" charset="utf-8"></script>
	<!-- <script src="js/global/myFunction.js" charset="utf-8"></script> -->
	<script src="js/global/responseLoading.js" charset="utf-8"></script>
	<script src="plugins/laydate/laydate.js" charset="utf-8"></script>
	<script src="plugins/colResizable/colResizable-1.6.min.js"></script>
	<script src="js/HotProduct.js?iv=201808241805" charset="utf-8"></script>
	<!-- <script src="js/global/version-control.js" charset="utf-8"></script> -->
</body>

</html>