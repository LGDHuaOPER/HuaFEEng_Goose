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
	<title>考核明细-数据统计</title>
	<link rel="shortcut icon" href="image/eoulu.ico" />
	<link rel="bookmark" href="image/eoulu.ico" />
	<link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css">
	<!-- <link rel="stylesheet" href="../css/global/eouluCustom.css" type="text/css"> -->
	<link rel="stylesheet" href="css/assessmentStatistics.css" type="text/css">
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
					<span class="statistics_staff_tab" id="current_tab">按成员统计</span>
					<span class="statistics_time_tab">按时间统计</span>
				</div>
				<div class="g_container_info_r">
					<form class="form-inline ym_sel_group">
					    <div class="form-group">
					        <label class="sr-only" for="exampleInputAmount">年份</label>
					        <div class="input-group">
					            <div class="input-group-addon">年份</div>
					            <select name="" id="year_sel" class="form-control">
					            	
					            </select>
					        </div>
					    </div>
					    <div class="form-group" style="display: none;">
					        <label class="sr-only" for="exampleInputAmount">月份</label>
					        <div class="input-group">
					            <div class="input-group-addon">月份</div>
					            <select name="" id="month_sel" class="form-control">
					            	
					            </select>
					        </div>
					    </div>
					    <button type="button" class="btn btn-info" id="task_freshen" style="display: none;">刷新</button>
					</form>
				</div>
			</div>
			<div class="g_container_body">
				<div class="g_container_body_in">
					<div class="g_container_body_info">
						<div class="g_container_body_info_l">按成员统计信息</div>
						<div class="g_container_body_info_r">
							<button type="button" class="btn btn-success" id="All_btn">所有考核</button>
							<button type="button" class="btn btn-info" id="Eoulu_btn">EOULU内部培训</button>
							<button type="button" class="btn btn-info" id="Formfactor_btn">原厂考核培训</button>
							<button type="button" class="btn btn-info" id="Experiment_btn">动手实验</button>
							<button type="button" class="btn btn-info" id="year_btn">年度统计</button>
							<button type="button" class="btn btn-info" id="month_btn">月度统计</button>
							<button type="button" class="btn btn-info" id="week_btn">周统计</button>
						</div>
					</div>
					<div class="g_container_body_table">
						<!-- <table class="statistics_qty_table">
							<thead>
								
							</thead>
							<tbody>
								
							</tbody>
							<tfoot>
								
							</tfoot>
						</table>

						<table class="statistics_score_table" style="display:none">
							<thead>
								
							</thead>
							<tbody>
								
							</tbody>
							<tfoot>
								
							</tfoot>
						</table> -->
						<div class="All_chart">
							<div id="All_charts_cont"></div>
						</div>
						<div class="Eoulu_chart">
							<div id="Eoulu_charts_cont"></div>
						</div>
						<div class="Formfactor_chart">
							<div id="Formfactor_charts_cont"></div>
						</div>
						<div class="Experiment_chart">
							<div id="Experiment_charts_cont"></div>
						</div>
						<div class="year_chart">
							<div id="year_charts_cont"></div>
						</div>
						<div class="month_chart">
							<div id="month_charts_cont"></div>
						</div>
						<div class="week_chart">
							<div id="week_charts_cont"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<script src="js/libs/jquery-3.3.1.min.js"></script>
<!-- <script src="js/libs/echarts-all.js"></script> -->
<script src="js/libs/echarts/echarts-4.1.0.rc2.min.js"></script>
<!-- <script src="https://cdn.bootcss.com/lodash.js/4.17.10/lodash.min.js"></script> -->
<script src="js/msgbox_unload.js"></script>
<script src="js/assessmentStatistics.js"></script>
</body>
</html>