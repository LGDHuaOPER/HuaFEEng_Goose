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
	<title>任务分配-数据统计</title>
	<link rel="shortcut icon" href="image/eoulu.ico" />
	<link rel="bookmark" href="image/eoulu.ico" />
	<link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css">
	<!-- <link rel="stylesheet" href="../css/global/eouluCustom.css" type="text/css"> -->
	<link rel="stylesheet" href="css/taskingstatistics.css" type="text/css">
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
		<c:forEach items="${Data}" var="dataitem" varStatus="status">
			<c:if test="${status.index>0}">
				<span class="task_data_span" style="display: none;" id="${status.index}" count="${dataitem.Count}" responsibleMan="${dataitem.ResponsibleMan}" month="${dataitem.Month}"></span>
			</c:if>
		</c:forEach>

		<div class="g_container">
			<div class="g_container_info">
				<div class="g_container_info_l">
					<span class="statistics_qty_tab current_tab">统计信息数量</span>
					<span class="statistics_score_tab">统计信息分数</span>
				</div>
				<div class="g_container_info_r">
					<form class="form-inline">
					    <div class="form-group">
					        <label class="sr-only" for="exampleInputAmount">年份</label>
					        <div class="input-group">
					            <div class="input-group-addon">年份</div>
					            <select name="" id="year_sel" class="form-control">
					            	
					            </select>
					        </div>
					    </div>
					    <div class="form-group">
					        <label class="sr-only" for="exampleInputAmount">月份</label>
					        <div class="input-group">
					            <div class="input-group-addon">月份</div>
					            <select name="" id="month_sel" class="form-control">
					            	
					            </select>
					        </div>
					    </div>
					    <button type="button" class="btn btn-info" id="task_freshen">刷新</button>
					</form>
				</div>
			</div>
			<div class="g_container_body">
				<div class="g_container_body_in">
					<div class="g_container_body_info">
						<div class="g_container_body_info_l">任务分配统计信息数量(个)</div>
						<div class="g_container_body_info_r">
							<button type="button" class="btn btn-info">视图</button>
						</div>
					</div>
					<div class="g_container_body_table">
						<table class="statistics_qty_table">
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
						</table>

						<div class="qty_chart_div" style="display:none;"></div>
						<div class="score_chart_div" style="display:none;"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
<script src="js/libs/jquery-3.3.1.min.js"></script>
<script src="js/libs/echarts-all.js"></script>
<script src="js/msgbox_unload.js"></script>
<script src="js/taskingstatistics.js"></script>
</body>
</html>