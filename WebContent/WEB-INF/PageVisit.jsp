<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<!-- 为移动设备添加 viewport -->
	<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
	<title>页面访问-数据统计</title>
	<link rel="shortcut icon" href="image/eoulu.ico" />
	<link rel="bookmark" href="image/eoulu.ico" />
	<link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css">
	<!-- <link rel="stylesheet" href="../css/global/eouluCustom.css" type="text/css"> -->
	<link rel="stylesheet" href="css/PageVisit.css" type="text/css">
	<style>
	    .u-admin a {
	        vertical-align: top !important;
	    }

	    .u-admin .u-i-2, .u-admin .u-i-3, .u-admin .u-i-4 {
	        vertical-align: top !important;
	    }

	    .lava_with_border li a {
	        vertical-align: top !important;
	    }

	    h5, h6 {
	        margin-top: 0px;
	        margin-bottom: 0px;
	    }

	    hr {
	        margin-top: 0px;
	        margin-bottom: 0px;
	        border: 0;
	        border-top: 1px solid #999;
	    }
	</style>
</head>
<body>
	<%@include file="top.jsp"%>
	<div class="container-fluid">
		<div class="g_container">
			<div class="g_container_info">
				<div class="g_container_info_l">
					<ul>
					    <li class="current_tab" data-classify="bar_statistics_tab"><span>柱状图统计</span></li>
					    <li data-classify="pie_statistics_tab"><span>饼状图统计</span></li>
					    <!-- <li data-classify="exam_detail_tab1"><span>考核详细信息1</span></li> -->
					</ul>
				</div>
			</div>
			<div class="g_container_body">
				<div class="g_container_body_in">
					<div class="bar_statistics_wrapper">
						<div class="bar_statistics_wrapper_all">
							<div class="bar_statistics_wrapper_all_title">全部页面</div>
							<div class="bar_statistics_wrapper_all_cont" id="bar_statis_all"></div>
						</div>
						<div class="bar_statistics_wrapper_0">
							<div class="bar_statistics_wrapper_0_title">物流部</div>
							<div class="bar_statistics_wrapper_0_cont" id="bar_statis_0"></div>
						</div>
						<div class="bar_statistics_wrapper_1">
							<div class="bar_statistics_wrapper_1_title">商务部</div>
							<div class="bar_statistics_wrapper_1_cont" id="bar_statis_1"></div>
						</div>
						<div class="bar_statistics_wrapper_2">
							<div class="bar_statistics_wrapper_2_title">服务部</div>
							<div class="bar_statistics_wrapper_2_cont" id="bar_statis_2"></div>
						</div>
						<div class="bar_statistics_wrapper_3">
							<div class="bar_statistics_wrapper_3_title">软件部</div>
							<div class="bar_statistics_wrapper_3_cont" id="bar_statis_3"></div>
						</div>
						<div class="bar_statistics_wrapper_4">
							<div class="bar_statistics_wrapper_4_title">人事部</div>
							<div class="bar_statistics_wrapper_4_cont" id="bar_statis_4"></div>
						</div>
						<div class="bar_statistics_wrapper_5">
							<div class="bar_statistics_wrapper_5_title">销售部</div>
							<div class="bar_statistics_wrapper_5_cont" id="bar_statis_5"></div>
						</div>
						<div class="bar_statistics_wrapper_6">
							<div class="bar_statistics_wrapper_6_title">实验室</div>
							<div class="bar_statistics_wrapper_6_cont" id="bar_statis_6"></div>
						</div>
					</div><!-- bar_statistics_wrapper end -->
					<div class="pie_statistics_wrapper"></div><!-- pie_statistics_wrapper end -->
				</div>
			</div>
		</div>
	</div>
<!-- <script src="js/libs/jquery-3.3.1.min.js"></script> -->
<!-- <script src="js/libs/echarts-all.js"></script> -->
<script src="js/libs/echarts/echarts-4.1.0.rc2.min.js"></script>
<!-- <script src="https://cdn.bootcss.com/lodash.js/4.17.10/lodash.min.js"></script> -->
<script src="js/msgbox_unload.js"></script>
<script src="js/PageVisit.js"></script>
</body>
</html>