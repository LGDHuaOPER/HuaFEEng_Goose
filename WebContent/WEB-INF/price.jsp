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
<title>合同统计</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" />
<link rel="stylesheet" href="css/global/eouluCustom.css" type="text/css">
<link rel="stylesheet" type="text/css" href="css/price.css?iv=201809121309">
<style>
	.content {
		padding-bottom: 10px !important;
	}
	/*布局*/
	html,body{
	    width:100%;
	    height:100%;
	}

	#Price_wrapper {
	    position:fixed;
	    overflow:auto;
	    width:100%;
	    height:100%;
	    box-sizing:border-box;
	}

	#Price_sticker {
	    width:100%;
	    min-height:100%;
	    box-sizing:border-box;
	}

	#Price_sticker-con {
	    padding-bottom:40px;
	    box-sizing:border-box;
	}

	#Price_footer {
	    margin-top:-40px;
	}

	.clear_float {
		height: 2px;
		width: calc(100% - 2px);
		clear:both;
	}
</style>
</head>
<body>
<div id="Price_wrapper">
    <div id="Price_sticker">
        <div id="Price_sticker-con">
			<!-- 	头部开始 -->
			<%@include file="top.jsp"%>
			<!-- 	头部结束 -->
			<div class="contain">
				<div class="content">
				<!-- 	=======================导航栏   开始 ================================== -->
				<%@include file="nav.jsp"%>
					
		<!-- 	=======================导航栏   结束 ================================== -->
		<!-- 	=======================实时汇率  开始 ================================== -->
		<!-- <div class="exchangeRate" id="exchangeRate">
		<div class="rateRight" id="rateRight">
			<div class="Rleft">
				<p class="part1">
					<span class="part1_one">1</span>
					<span class="part1_two">United States Dollar</span>
					<span class="part1_one">=</span>
					<span class="part1_three part1_one"></span>
					<span class="part1_two">RMB</span>
				</p>
				<div class="rateBox">
					<div class="rateBox_L">
						<div class="part2">
							<div class="part2_left"><input type="text" placeholder="1" value="" class="number1" oninput="aa(event)" onporpertychange="aa(event)" ></div>
							<div class="part2_right">(美元)USD</div>
						</div>
						<div class="part3">
							<div class="part3_left"></div>
							<div class="part3_right">(人民币)CNY</div>
						</div>
					</div>	
					<div class="rateBox_R">
						<span class="rate_icon"><img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACwAAAAqCAYAAADI3bkcAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA39pVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMTQyIDc5LjE2MDkyNCwgMjAxNy8wNy8xMy0wMTowNjozOSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDplNzNkYmFmMi01NmY5LTk2NDQtODJjNC0zZWVmZDlhNGU0ZmIiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6QkNCNDY0QjZEQkUwMTFFN0E3QTFFMTMwRkU4RDM1MTQiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6QkNCNDY0QjVEQkUwMTFFN0E3QTFFMTMwRkU4RDM1MTQiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIChXaW5kb3dzKSI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOmY3NWI0M2I3LTE2MjUtZWM0ZC1hZDBlLTY0M2NlZjRhNzk4MSIgc3RSZWY6ZG9jdW1lbnRJRD0iYWRvYmU6ZG9jaWQ6cGhvdG9zaG9wOjZjNTU0NWVhLTI2NGItNGU0OS1iYzIyLWFlZjAxZjc5MGEzMyIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PjBxcFIAAAGSSURBVHja7Jk9SwNBEIbfi1cYMGIjpEkroqWx0yKFFn5EsLG20tKzUtRKxUrPVhv/gUUiNoJBtJHYimIXLASxiSYRCbjOsHtEiZUYnMUdeLjh3eIe9vZ2D85LhgqmJomAGCQ6IKMqRJHYIY44iJmBDSJHZATJwriwU944wicmiBXIL3a85BlehD0VsHDaIuE0CycsEk7EYFk5YSf874Vn+oCHBQ3339X8gOY3ym/1jAyngLUh3V8/Auf3gpdEqhPYH6ebeBruORMpHKdnd0Dff13tjYx7zuK+QOHtEaC/uznnjMfEreGlU810L7CV0dlyATi8FfrSPb/p62u9kXEf5W4fdsJO2Ak7YTuFf3xw7I0B2Z6v2e6oJndHn5PHgJI0w8EJcPPUnHPGY0rakqjRMTubB8qfjlruOavVha7hUhmYo0f/rjTccyZyDUd1VgLWL4A2T/etLi8ZKmXTLuH2YSfshP9A+MUi3woLX1kkXGTh0CLhkIX5l9KmBbLsmI9eulViiigQVUGSVeOUNY74EGAASBRe6DcYzugAAAAASUVORK5CYII="></span>
					</div>
				</div>
			</div>
			汇率折线图部分 
			<div class="Rright"></div>
		</div>
		<div class="rateLeft"><span class="rateText">实时汇率</span></div>

		</div> -->
		<!-- 	=======================实时汇率  结束 ================================== -->
					<div style="display: none">
						<c:if test="${queryType=='common'}">
							<input type="text" value="" name="classify1">
							<input type="text" value="" name="classify2">
							<input type="text" value="" name="parameter1">
							<input type="text" value="" name="parameter2">
						</c:if>
						<c:if test="${queryType=='singleSelect'}">
							<input type="text" value="${classify1 }" name="classify1">
							<input type="text" value="$" name="classify2">
							<input type="text" value="${parameter1 }" name="parameter1">
							<input type="text" value="" name="parameter2">
						</c:if>
						<c:if test="${queryType=='mixSelect'}">
							<input type="text" value="${classify1 }" name="classify1">
							<input type="text" value="${classify2 }" name="classify2">
							<input type="text" value="${parameter1 }" name="parameter1">
							<input type="text" value="${parameter2 }" name="parameter2">
						</c:if>
						<input type="text" value="${queryType }" name="query_type">
					</div>
					<div class="prompt">
						<!-- 新增窗口右侧提示效果 -->
						<div class="prompt-alert">
							<div class="swiper-wrapper">
								<div class="swiper-slide">
									<span>南方棒棒哒！</span> <img src="image/you1.png" />
								</div>
								<div class="swiper-slide">
									<span>北方加油！</span> <img src="image/jy.gif" />
								</div>
								<div class="swiper-slide">
									<span>西南棒棒哒！</span> <img src="image/80.gif" />
								</div>
							</div>
						</div>

					<c:set var="totalTargetValue" value="${AreaStatisticsByLastYear[1]['TargetValue']+AreaStatisticsByLastYear[2]['TargetValue']+AreaStatisticsByLastYear[3]['TargetValue']}"></c:set>
					<c:set var="totalCompleteValue" value="${AreaStatisticsByYear[1]['CompletValue']+AreaStatisticsByYear[2]['CompletValue']+AreaStatisticsByYear[3]['CompletValue']  }"></c:set>
					<c:set var="totalSurplusValue" value="${(totalTargetValue-totalCompleteValue)>0?(totalTargetValue-totalCompleteValue):0}"></c:set>
						<div class="add-div-top">
							<span class="span span1">总目标值</span>
							<div class="number">${totalTargetValue}M</div>
							<span class="span span2">剩余目标值</span>
							<div class="number number2">
								<fmt:formatNumber type="number" value="${totalSurplusValue}" pattern="#0.00" />M
							</div>
						</div>
						<div class="prompt-content">
							<div class="prompt-top">
								<ul>
									<li class="prompt-top1"><b class="prompt-content1"></b>
									<!-- 18年度总目标值： --><b>${totalTargetValue}M</b></li>
									<li class="prompt-top2">剩余目标值：<b><fmt:formatNumber type="number" value="${totalSurplusValue}" pattern="#0.00" />M</b></li>
									<li class="prompt-top3">单位:USD</li>
								</ul>
								<ul class="absolute_ul"><li class="pC-li5 jilu"><b class=" fa-hand-o-right"></b>&nbsp;详细记录</li></ul>
							</div>
							<div class="prompt-middle">
								<div class="proClick">
									<ol>
										<li class="pC-li0" style="cursor: auto;">选择区域单独查看</li>
										<li class="pC-li1"><b class="fa fa-hand-o-right"></b>&nbsp;全部</li>
										<li class="pC-li2"><b class="fa fa-hand-o-right"></b>&nbsp;南方</li>
										<li class="pC-li3"><b class="fa fa-hand-o-right"></b>&nbsp;北方</li>
										<li class="pC-li4"><b class="fa fa-hand-o-right"></b>&nbsp;西南</li>
										<!-- <li class="pC-li5 jilu"><b class=" fa-hand-o-right"></b>&nbsp;详细记录</li> -->
									</ol>
								</div>
								<!--三个区域全部展现-->
								<div class="proContent proTotal">
									<div class="proTitle">
										<ul>
											<li>区域</li>
											<li>总目标</li>
											<li>本年已完成</li>
											<li>总目标剩余</li>
											<li>月目标</li>
											<li>月完成</li>
											<li>月剩余</li>
										</ul>
									</div>
									<ul class="proSouth">
										<li>南方</li>
										<li><fmt:formatNumber type="number"
												value="${AreaStatisticsByLastYear[2]['TargetValue']/1>=0?AreaStatisticsByLastYear[2]['TargetValue']/1:0 }"
												pattern="#0.00" /> M</li>
										<li><fmt:formatNumber type="number"
												value="${AreaStatisticsByYear[2]['CompletValue']/1>=0?AreaStatisticsByYear[2]['CompletValue']/1:0 }"
												pattern="#0.00" /> M</li>
										<li><fmt:formatNumber type="number"
												value="${(AreaStatisticsByLastYear[2]['TargetValue']-AreaStatisticsByYear[2]['CompletValue'])/1>=0?(AreaStatisticsByLastYear[2]['TargetValue']-AreaStatisticsByYear[2]['CompletValue'])/1:0 }"
												pattern="#0.00" /> M</li>
										<li><b><fmt:formatNumber type="number"
													value="${AreaStatisticsByMonthOld[2]['TargetValue']/1>0?AreaStatisticsByMonthOld[2]['TargetValue']/1:0 }"
													pattern="#0.00" /></b> M</li>
										<li><b><fmt:formatNumber type="number"
													value="${AreaStatisticsByMonth[2]['CompletValue']/1}"
													pattern="#0.00" /></b> M</li>
										<li><b><fmt:formatNumber type="number"
													value="${(AreaStatisticsByMonthOld[2]['TargetValue']-AreaStatisticsByMonth[2]['CompletValue'])/1>=0?(AreaStatisticsByMonthOld[2]['TargetValue']-AreaStatisticsByMonth[2]['CompletValue'])/1:0 }"
													pattern="#0.00" /></b> M</li>

										<%-- 								<li><fmt:formatNumber type="number" value="" pattern="#.00" /> M</li> --%>
										<%-- 								<li><b>${AreaStatisticsByMonth[2]['TargetValue']/1>0?AreaStatisticsByMonth[2]['TargetValue']/1:0 }</b>M</li> --%>
										<%-- 								<li><b>${AreaStatisticsByMonth[2]['CompletValue']/1}</b>M</li> --%>
										<%-- 								<li><b>${(AreaStatisticsByMonth[2]['TargetValue']-AreaStatisticsByMonth[2]['CompletValue'])/1>=0?(AreaStatisticsByMonth[2]['TargetValue']-AreaStatisticsByMonth[2]['CompletValue'])/1:0 }</b>M</li> --%>
									</ul>
									<ul class="proNorth">
										<li>北方</li>
										<li><fmt:formatNumber type="number"
												value="${AreaStatisticsByLastYear[1]['TargetValue']/1>=0?AreaStatisticsByLastYear[1]['TargetValue']/1:0 }"
												pattern="#0.00" /> M</li>
										<li><fmt:formatNumber type="number"
												value="${AreaStatisticsByYear[1]['CompletValue']/1>=0?AreaStatisticsByYear[1]['CompletValue']/1:0 }"
												pattern="#0.00" /> M</li>
										<li><fmt:formatNumber type="number"
												value="${(AreaStatisticsByLastYear[1]['TargetValue']-AreaStatisticsByYear[1]['CompletValue'])/1>=0?(AreaStatisticsByLastYear[1]['TargetValue']-AreaStatisticsByYear[1]['CompletValue'])/1:0 }"
												pattern="#0.00" /> M</li>
										<li><b><fmt:formatNumber type="number"
													value="${AreaStatisticsByMonthOld[1]['TargetValue']/1>0?AreaStatisticsByMonthOld[1]['TargetValue']/1:0 }"
													pattern="#0.00" /></b> M</li>
										<li><b><fmt:formatNumber type="number"
													value="${AreaStatisticsByMonth[1]['CompletValue']/1}"
													pattern="#0.00" /></b> M</li>
										<li><b><fmt:formatNumber type="number"
													value="${(AreaStatisticsByMonthOld[1]['TargetValue']-AreaStatisticsByMonth[1]['CompletValue'])/1>=0?(AreaStatisticsByMonthOld[1]['TargetValue']-AreaStatisticsByMonth[1]['CompletValue'])/1:0 }"
													pattern="#0.00" /></b> M</li>
									</ul>
									<ul class="proSouthwest">
										<li>西南</li>
										<li><fmt:formatNumber type="number"
												value="${AreaStatisticsByLastYear[3]['TargetValue']/1>=0?AreaStatisticsByLastYear[3]['TargetValue']/1:0 }"
												pattern="#0.00" /> M</li>
										<li><fmt:formatNumber type="number"
												value="${AreaStatisticsByYear[3]['CompletValue']/1>=0?AreaStatisticsByYear[3]['CompletValue']/1:0 }"
												pattern="#0.00" /> M</li>
										<li><fmt:formatNumber type="number"
												value="${(AreaStatisticsByLastYear[3]['TargetValue']-AreaStatisticsByYear[3]['CompletValue'])/1>=0?(AreaStatisticsByLastYear[3]['TargetValue']-AreaStatisticsByYear[3]['CompletValue'])/1:0 }"
												pattern="#0.00" /> M</li>
										<li><b><fmt:formatNumber type="number"
													value="${AreaStatisticsByMonthOld[3]['TargetValue']/1>0?AreaStatisticsByMonthOld[3]['TargetValue']/1:0 }"
													pattern="#0.00" /></b> M</li>
										<li><b><fmt:formatNumber type="number"
													value="${AreaStatisticsByMonth[3]['CompletValue']/1}"
													pattern="#0.00" /></b> M</li>
										<li><b><fmt:formatNumber type="number"
													value="${(AreaStatisticsByMonthOld[3]['TargetValue']-AreaStatisticsByMonth[3]['CompletValue'])/1>=0?(AreaStatisticsByMonthOld[3]['TargetValue']-AreaStatisticsByMonth[3]['CompletValue'])/1:0 }"
													pattern="#0.00" /></b> M</li>
									</ul>
								</div>
								<!--选择单个区域展现-->
								<div class="proContent proSingal">
									<ul class="sinTitle">
										<li>区域</li>
										<li>总目标</li>
										<li>本年已完成</li>
										<li>总目标剩余</li>
										<li>月目标</li>
										<li>月完成</li>
										<li>月剩余</li>
									</ul>
									<ul class="sin sinSouth">
										<li>南方</li>
										<li><fmt:formatNumber type="number"
												value="${AreaStatisticsByYear[2]['TargetValue']/1>=0?AreaStatisticsByYear[2]['TargetValue']/1:0 }"
												pattern="#0.00" /> M</li>
										<li><fmt:formatNumber type="number"
												value="${AreaStatisticsByYear[2]['CompletValue']/1>=0?AreaStatisticsByYear[2]['CompletValue']/1:0 }"
												pattern="#0.00" /> M</li>
										<li><fmt:formatNumber type="number"
												value="${(AreaStatisticsByYear[2]['TargetValue']-AreaStatisticsByYear[2]['CompletValue'])/1>=0?(AreaStatisticsByYear[2]['TargetValue']-AreaStatisticsByYear[2]['CompletValue'])/1:0 }"
												pattern="#0.00" /> M</li>
										<li><b><fmt:formatNumber type="number"
													value="${AreaStatisticsByMonth[2]['TargetValue']/1>0?AreaStatisticsByMonth[2]['TargetValue']/1:0 }"
													pattern="#0.00" /></b> M</li>
										<li><b><fmt:formatNumber type="number"
													value="${AreaStatisticsByMonth[2]['CompletValue']/1}"
													pattern="#0.00" /></b> M</li>
										<li><b><fmt:formatNumber type="number"
													value="${(AreaStatisticsByMonth[2]['TargetValue']-AreaStatisticsByMonth[2]['CompletValue'])/1>=0?(AreaStatisticsByMonth[2]['TargetValue']-AreaStatisticsByMonth[2]['CompletValue'])/1:0 }"
													pattern="#0.00" /></b> M</li>
									</ul>
									<ul class="sin sinNorth">
										<li>北方</li>
										<li><fmt:formatNumber type="number"
												value="${AreaStatisticsByYear[1]['TargetValue']/1>=0?AreaStatisticsByYear[1]['TargetValue']/1:0 }"
												pattern="#0.00" /> M</li>
										<li><fmt:formatNumber type="number"
												value="${AreaStatisticsByYear[1]['CompletValue']/1>=0?AreaStatisticsByYear[1]['CompletValue']/1:0 }"
												pattern="#0.00" /> M</li>
										<li><fmt:formatNumber type="number"
												value="${(AreaStatisticsByYear[1]['TargetValue']-AreaStatisticsByYear[1]['CompletValue'])/1>=0?(AreaStatisticsByYear[1]['TargetValue']-AreaStatisticsByYear[1]['CompletValue'])/1:0 }"
												pattern="#0.00" /> M</li>
										<li><b><fmt:formatNumber type="number"
													value="${AreaStatisticsByMonth[1]['TargetValue']/1>0?AreaStatisticsByMonth[1]['TargetValue']/1:0 }"
													pattern="#0.00" /></b> M</li>
										<li><b><fmt:formatNumber type="number"
													value="${AreaStatisticsByMonth[1]['CompletValue']/1}"
													pattern="#0.00" /></b> M</li>
										<li><b><fmt:formatNumber type="number"
													value="${(AreaStatisticsByMonth[1]['TargetValue']-AreaStatisticsByMonth[1]['CompletValue'])/1>=0?(AreaStatisticsByMonth[1]['TargetValue']-AreaStatisticsByMonth[1]['CompletValue'])/1:0 }"
													pattern="#0.00" /></b> M</li>
									</ul>
									<ul class="sin sinSouthwest">
										<li>西南</li>
										<li><fmt:formatNumber type="number"
												value="${AreaStatisticsByYear[3]['TargetValue']/1>=0?AreaStatisticsByYear[3]['TargetValue']/1:0 }"
												pattern="#0.00" /> M</li>
										<li><fmt:formatNumber type="number"
												value="${AreaStatisticsByYear[3]['CompletValue']/1>=0?AreaStatisticsByYear[3]['CompletValue']/1:0 }"
												pattern="#0.00" /> M</li>
										<li><fmt:formatNumber type="number"
												value="${(AreaStatisticsByYear[3]['TargetValue']-AreaStatisticsByYear[3]['CompletValue'])/1>=0?(AreaStatisticsByYear[3]['TargetValue']-AreaStatisticsByYear[3]['CompletValue'])/1:0 }"
												pattern="#0.00" /> M</li>
										<li><b><fmt:formatNumber type="number"
													value="${AreaStatisticsByMonth[3]['TargetValue']/1>0?AreaStatisticsByMonth[3]['TargetValue']/1:0 }"
													pattern="#0.00" /></b> M</li>
										<li><b><fmt:formatNumber type="number"
													value="${AreaStatisticsByMonth[3]['CompletValue']/1}"
													pattern="#0.00" /></b> M</li>
										<li><b><fmt:formatNumber type="number"
													value="${(AreaStatisticsByMonth[3]['TargetValue']-AreaStatisticsByMonth[3]['CompletValue'])/1>=0?(AreaStatisticsByMonth[3]['TargetValue']-AreaStatisticsByMonth[3]['CompletValue'])/1:0 }"
													pattern="#0.00" /></b> M</li>
									</ul>

								</div>
							</div>
						</div>

					</div>

					<form id="top_text_from" name="top_text_from" method="post"
						action="PriceRoute">
						<input type="text" id="search" name="isSearch" value="search"
							style="display: none;">
							<input type="text"  name="PageType" value="0" style="display: none;">	
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
									value="${fn:split('销售代表,合同号,客户名称,区域,合同名称,联系人,联系方式,合同签订日期,合同货期,实际货期,预计货期,合同状态,装机时间,装机地点,型号,备注,合同类型',',')}"></c:set>
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
									<c:if test="${queryType=='singleSelect' || queryType=='mixSelect'}">
										 <c:choose>
											<c:when test="${ classify1 =='合同货期' || classify1 == '实际货期' || classify1== '预计货期' }">
												<input type="text" id="searchContent1" name="searchContent1"
													style="display: none;">
												<input type="date" name="start_time1" class="time startTime"
													style="margin-left: -5px; display: inline-block;" value="${start_time1 }">
												<span class="time" style="display: inline-block;">-</span>
												<input type="date" name="end_time1" class="time endTime"
													style="display: inline-block;" value="${end_time1 }">
											</c:when>
											<c:otherwise>
												<input type="text" id="searchContent1" name="searchContent1" style="display: inline-block;" value="${parameter1}">
												<input type="date" name="start_time1" class="time startTime"  style="margin-left: -5px;">
												<span class="time">-</span>
												<input type="date" name="end_time1" class="time endTime">
											</c:otherwise>
										</c:choose>
									</c:if>
									<c:if test="${queryType=='common'}">
										<input type="text" id="searchContent1" name="searchContent1" style="display: inline-block;" value="${searchContent1}">
										<input type="date" name="start_time1" class="time startTime" style="margin-left: -5px;">
										<span class="time">-</span>
										<input type="date" name="end_time1" class="time endTime">
									</c:if>
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
									<c:if test="${queryType!='mixSelect'}">
										<input type="text" id="searchContent2" name="searchContent2">
										<input type="date" name="start_time2" class="time startTime" style="margin-left: -5px;">
										<span class="time">-</span>
										<input type="date" name="end_time2" class="time endTime" >
									</c:if>
									<c:if test="${queryType=='mixSelect'}">
										<c:choose>
											<c:when test="${ classify2 =='合同货期' || classify2 == '实际货期' || classify2== '预计货期' }">
												<input type="text" id="searchContent2" name="searchContent2"
													style="display: none;">
												<input type="date" name="start_time2" class="time startTime"
													style="margin-left: -5px; display: inline-block;" value="${start_time2 }">
												<span class="time" style="display: inline-block;">-</span>
												<input type="date" name="end_time2" class="time endTime"
													style="display: inline-block;" value="${end_time2 }">
											</c:when>
											<c:otherwise>
												<input type="text" id="searchContent2" name="searchContent2" value="${parameter2}">
												<input type="date" name="start_time2" class="time startTime"  style="margin-left: -5px;">
												<span class="time">-</span>
												<input type="date" name="end_time2" class="time endTime">
											</c:otherwise>
										</c:choose>
									</c:if>
								</div>
								<div class="select-button">
									<input type="button" value="搜索" class="bToggle" onclick="selectSearch()"> <input type="button" value="取消" class="bToggle" onclick="selectCancel()">
								</div>
						</div>
						<div class="choose">
							<input type="button" value="添加" class="bToggle" onclick="AddContract()">
							<input type="button" value="导出Excel" class="bToggle export_Excel">
						</div>
					</form>

					<table border="1" cellspacing="0" cellspadding="0" id="table1">

						<tr>
							<td>序号</td>
							<!-- <td>修改</td> -->
							<!--<td>删除</td>-->
							<td>客户名称 <a title="显示与隐藏"><i class="fa fa-plus-square"
									id="fa-button1"></i></a>
							</td>
							<td class="tog_ContactTD">联系人</td>
							<td class="tog_ContactInfoTD">联系方式</td>
							<td class="tog_Area">合同地区</td>
							<td id="contractName">合同名称 <a title="显示与隐藏"><i class="fa fa-plus-square" id="fa-button2"></i></a></td>
							<td id="contractNum">合同号</td>
							<td style="display:none;">合同类型</td>
							<td class="tog_SalesRepresentative">销售代表</td>
							<td>合同签订日期</td>
							<td style="cursor:pointer;" title="升序排列" class="title_contract">合同货期
								<select style="width: 80px;">
									<option value="All" class="All">所有订单</option>
									<option value="AllNoSend" class="AllNoSend">未发货订单</option>
									<option value="OtherNoSend" class="OtherNoSend">不包括等待客户付款的未发货订单</option>
								</select>
							</td>
							<td style="cursor:pointer;" title="显示未发货的合同" class="title_actual"> 实际货期
								<select style="width: 80px;">
									<option value="All" class="All">所有订单</option>
									<option value="AllNoSend" class="AllNoSend">未发货订单</option>
									<option value="OtherNoSend" class="OtherNoSend">不包括等待客户付款的未发货订单</option>
								</select>
							</td>
							<td style="cursor:pointer;" title="升序排列" class="title_Expected">预计货期
								<select style="width: 80px;">
									<option value="All" class="All">所有订单</option>
									<option value="AllNoSend" class="AllNosend">未发货订单</option>
									<option value="OtherNoSend" class="OtherNosend">不包括等待客户付款的未发货订单</option>
								</select>
							</td>
							<td>预计收款日期</td>
							<td>合同状态 <a title="显示与隐藏"><i class="fa fa-plus-square"
									id="fa-button3"></i></a>
							</td>
							<td class="tog_InstalledTime">装机时间和地点</td>
							<td>备注</td>
							<td>合同配置</td>
							<td class="tog_Details">合同明细</td>
							<td  style="display:none;">是否可以发货</td>
							<td>是否付款</td>
							<!-- <td  style="display:none;" >付款时间</td>
							<td  style="display:none;">发票单号</td> -->
							<td class="" style="display:none"></td>
							
						</tr>

						<c:forEach var="orderInfo" items="${orders}" varStatus="status">
							<c:if test="${status.index>0}">
								<tr>
									<td  class="contract-edit" value="${orderInfo['ID']}" style="cursor:pointer">${status.index+(currentPage-1)*10}</td><!-- 0 -->
									<td class="t1_Customer overhid" title="${orderInfo['Customer']}">${orderInfo['Customer']}</td> 
									<td  class="ContactTD">${orderInfo['Contact']}</td>   
									<td class="ContactInfoTD">${orderInfo['ContactInfo']}</td>  
									<td class="t1_Area">${orderInfo['Area']}</td>     
									<td class="ContractTitleTD overhid" title="${orderInfo['ContractTitle']}">${orderInfo['ContractTitle']}</td>   
									<td class="ContractNoTD">${orderInfo['ContractNo']}</td>    
									<td class="t1_ContractCategory"  style="display:none;">${orderInfo['ContractCategory']}</td>  
									<td class="t1_SalesRepresentative">${orderInfo['SalesRepresentative']}</td>   
									<td class="t1_DateOfSign">${orderInfo['DateOfSign']}</td>   
									<td class="t1_CargoPeriod">${orderInfo['CargoPeriod']}</td>   
									<td class="t1_ActualDelivery">${orderInfo['ActualDelivery']}</td>   
									<c:choose>
										<c:when test="${orderInfo['ExpectedDeliveryPeriod'] == '--'}">
											<td class="t1_ExpectedDeliveryPeriod">${orderInfo['ExpectedDeliveryPeriod']}</td>   
										</c:when>
										<c:otherwise>
										<fmt:formatDate value="${now}" type="both" dateStyle="long" var="date2" pattern="yyyy-MM-dd"/>
										<fmt:parseDate value="${orderInfo['ExpectedDeliveryPeriod']}" var="date1" pattern="yyyy-MM-dd"/>
										<c:set var="interval" value="${now.time - date1.time }"/>
											<c:choose>
												<c:when test="${interval < -604800000 || (orderInfo['Status']!='已备货' && orderInfo['Status']!='已通知备货' && orderInfo['Status'] !='无' && orderInfo['Status'] !='--')}">
													<td class="t1_ExpectedDeliveryPeriod">${orderInfo['ExpectedDeliveryPeriod']}</td>   
												</c:when>
												<c:otherwise>
													<td class="change t1_ExpectedDeliveryPeriod">${orderInfo['ExpectedDeliveryPeriod']}<br/>【需要尽快发货】</td>  
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
									<td class="t1_ExpectedReceiptDate">${orderInfo['ExpectedReceiptDate']}</td>  
									<td class="t1_Status">${orderInfo['Status']}</td>                
									<td class="t1_InstalledTime">${orderInfo['InstalledTime']}${orderInfo['InstalledSite']}</td>   
									<td class="t1_Remarks">
										<span class="t1_RemarksCon overhid" title="${orderInfo['Remarks']}">${orderInfo['Remarks']}</span><br/>
										 <c:choose>
											<c:when test="${orderInfo['ContractPath']=='' || orderInfo['ContractPath']=='--'}">
												  合同文件:未上传<br/>
											</c:when>
											<c:otherwise>
												  <div class="filespan ">合同文件:<input class="tab_href tab_ContractPath overhid" title="${orderInfo['ContractPath']}" value="${orderInfo['ContractPath']}"/><%-- ${orderInfo['ContractPath']}</span>  --%> </div><br/>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${orderInfo['TechnologyPath']=='' || orderInfo['TechnologyPath']=='--'}">
												  技术协议:未上传<br/>
											</c:when>
											<c:otherwise>
												  <div class="filespan ">技术协议:<input class="tab_href tab_TechnologyPath overhid" title="${orderInfo['TechnologyPath']}" value="${orderInfo['TechnologyPath']}"/><%-- ${orderInfo['TechnologyPath']}</span> --%> </div> 
											</c:otherwise>
										</c:choose>
									</td>             
									<td><i class="fa fa-eye contract-show" value="${orderInfo['ID']}" QuoteNumber="${orderInfo['QuoteNumber']}"></i></td>    
									<td class="t1_Details"><i class="fa fa-eye supply-show" value="${orderInfo['ID']}"></i></td>     
								    <td class="t1_isSend"  style="display:none;">${orderInfo['isSend']}</td>             
								   	<td class="t1_WhetherToPay">${orderInfo['WhetherToPay']}</td>        
								    <td class="t1_qNumber" style="display:none;" ID="${orderInfo['QuoteNumber']}">${orderInfo['Number']}</td>  
								   <%--  <td class="pay_date"  style="display:none;">${orderInfo['pay_date']}</td>
								    <td class="tracking_no "  style="display:none;">${orderInfo['tracking_no']}</td> --%>
								    <td class="customerID_tr" style="display:none">${orderInfo['CustomerID']}</td>
								</tr>
							</c:if>
						</c:forEach>
					</table>
					<div class="cover-color" style="display: none;"></div>
					<div class="cover-color2" style="display: none;"></div>
					<div class="upload" style="display: none;">
						<div class="contract_title">上传文件</div>
						<div class="upload_close">关闭</div>
						<div class="file_content">
							<a class="file"><input type="file" name="file" id="fileField" size="28" onchange="openFile()" /> <span>&nbsp;选择文件&nbsp;</span></a>
							<input id="file_name" type="text"><br><span class='error'></span>
						</div>
						<input type="button" name="upload" id="open" class="bToggle" value="上传" />
						<div class='list'>
							<ul>
							</ul>
						</div>
					</div>

					<!-- 添加合同 -->
					<div class="contract_add" style="display: none;">
						<div class="contract_title">添加合同</div>
						<div class="contractAdd_close">关闭</div>
						   <div class="basic_info" >
					                <div class="table_title" style="margin-bottom:20px">合同基本信息</div>
					                <!-- 修改添加 -->
					                
					                	<div class="table_col1  table_col" style="padding-right:0%">
					                		 <span class="L_sty"><i class="relevance"></i>客户名称</span>
					                		 <span class="customerID_span" style="display:none"></span>
					                         <!-- <span ><input class="mb10" type="text" name="customer" value=""  class="customer"></span> -->
					                         <div class="L_styinp">
						                         <div class="out_search out_searchCus" style="margin-bottom: 0;left:0;top:0;">
													<input type="search" name="customer_search" class="customer_search"> 
													<select name="customer_name" multiple style="width:40vw;">
													</select>
												</div>
					                         </div>
					                       	 <span class="L_sty"><i class="relevance"></i>联系人</span>
					                         <span><input  class="mb10" type="text" name="contact" value="" class="contact" disabled="disabled"></span> 
					                          <span class="L_sty"><i class="relevance"></i>联系方式</span>
					                         <span><input  class="mb10" type="text" name="contact_info" value="" class="contact_info" disabled="disabled"></span>
					                          <span class="L_sty">销售代表</span>
					                         <span>
						                         <select name="sales_representative" class="mb10 sales_representative"  style="width:164px;height:24px">
													<c:forEach var="SaleRep" items="${SalesRep}"
														varStatus="status">
														<c:if test="${status.index>0}">
															<option value="${SaleRep.ID}" text="${SaleRep.Name }">${SaleRep.Name }</option>
														</c:if>
													</c:forEach>
												</select>
											</span>
					                	</div>
					                	<div  class="table_col2" style="float:left;width:30%;min-width:24%;padding-left:4%;padding-right:0%;margin-bottom:10px;border-left:1px solid #ccc;border-right:1px solid #ccc;">
					                         <span class="L_sty">合同名称</span>
					                         <span><input  class="mb10 " type="text" name="contract_title" value=""></span>

					                         <span class="L_sty">合同号</span>
					                         <span><input  class="mb10 contract_no" type="text" name="contract_no" value=""></span>
					                         <span class="L_sty">合同类型</span>
					                         <span>
						                         <select name="contract_category" class="mb10 contract_category"  style="width:164px;height:24px">
													<c:forEach var="contractCategory" items="${contract_category}" varStatus="status">
														<c:if test="${status.index>0}">
															<option value="${contractCategory.ID}" text="${contractCategory.Classify }">${contractCategory.Classify}</option>
														</c:if>
													</c:forEach>
												</select>
					                         </span>
					                         <span class="L_sty">装机地点</span>
					                         <span><input  class="mb10 installed_site" type="text" name="installed_site" value=""></span>
					                    </div>
					                	<div  class="table_col3" style="float:left;width:30%;min-width:24%;padding-left:5%;">
					                         <span class="L_sty">合同货期</span>
					                         <span><input  class="mb10 cargo_period" type="date" name="cargo_period" value=""></span>
					                         <span class="L_sty">合同签订日期</span>
					                         <span><input  class="mb10 date_of_sign" type="date" name="date_of_sign" value=""></span>
					                          <span class="L_sty">预计收款日期</span>
					                         <span><input  class="mb10  expected_receipt_date" type="date" name="expected_receipt_date" value=""></span>
					                          <span class="L_sty">装机时间</span>
					                         <span><input  class="mb10 installed_time" type="date" name="installed_time" value=""></span>
					               		 </div>
					               	
					               	 <div style="float:left;width:100%;margin-bottom:20px">
					               	 	<span style="float:left;width:9%">备注</span><span style="float:left;width:90%"><textarea id="responseTa" cols="117" rows="3" class="remarks" name="remarks"></textarea></span>
					               	 </div>
					               </div>		 
					                <div class="table_title" style="float:left;width:100%;height:20px;margin-left:30px;margin-bottom:15px">合同明细</div>
					                 <div class="table_col4" style="float:left;width:30%;min-width:24%;padding-left:3%;padding-right:0%;">
					                		 <span class="L_sty">合同金额(USD)</span>
					                         <span><input  class="mb10 usd_quotes" type="text" name="usd_quotes" value=""></span>
					                       	  <span class="L_sty">合同金额(RMB)</span>
					                         <span><input  class="mb10 rmb_quotes" type="text" name="rmb_quotes" value=""></span> 
					                         <span class="L_sty">付款条件</span>
					                         <span>
					                         		<div class="out_search">
														<input type="search" name="pay_search" class="pay_search">
														<select name="payment_terms" multiple>
															<c:forEach var="PayTerm" items="${PayTerms}"
																varStatus="status">
																<c:if test="${status.index>0}">
																	<option value="${PayTerm.ID}">${PayTerm.Condition }</option>
																</c:if>
															</c:forEach>
														</select>
													</div>
					                         </span>
					                          <span class="L_sty">对应报价单</span>
						                         <div style="display:inline-block;margin-bottom:15px" class="quotationBox">
													<input type="text" class="add_quotation"  style="width:160px;height:20px;"> 
													<ul class="quotationlist">
													</ul>
												</div>
					                          <span class="L_sty">合同文件</span>
					                         <span>
					                         	<form class="add_fileBox"  id="add_fileBox1"  method="post" target="myframe" action="OrderUpload" enctype="multipart/form-data" >
												<input class="add_fileCont"/><!-- </span> -->
												<span class="add_uploadText">上传</span>
											    <input class="add_change add_file" type="file" multiple="multiple" name="fileName"/>
											    <input name="classify" value="ContractPath" type="hidden"/>
												</form>
					                         </span>
					                         <span class="L_sty">技术协议</span>
					                         <span>
					                         	<form class="add_fileBox"  id="add_fileBox2"  method="post" target="myframe" action="OrderUpload" enctype="multipart/form-data" >
												<input class="add_fileCont1" /><!-- </span> -->
												<span class="add_uploadText">上传</span>
											    <input class="add_change add_agreement" type="file" multiple="multiple" name="fileName"/>
											    <input name="classify" value="TechnologyPath" type="hidden"/>
												</form>
					                         </span>
					                	</div>
					                	<div class="table_col5" style="float:left;width:30%;min-width:24%;padding-left:3%;padding-right:0%;margin-bottom:10px;border-left:1px solid #ccc;border-right:1px solid #ccc;">
					                         <span class="L_sty">收款日期1</span>
					                         <span><input  class="mb10 receipt_date1" type="date" name="receipt_date1" value=""></span>
					                          <span class="L_sty">收款金额1</span>
					                         <span><input  class="mb10 receipt_amount1" type="text" name="receipt_amount1" value=""></span>
					                        <span class="L_sty">收款日期2</span>
					                         <span><input  class="mb10 receipt_date2" type="date" name="receipt_date2" value=""></span>
					                         <span class="L_sty">收款金额2</span>
					                         <span><input  class="mb10 receipt_amount2" type="text" name="receipt_amount2" value=""></span>
					                           <span class="L_sty">收款日期3</span>
					                         <span><input  class="mb10 receipt_date3" type="date" name="receipt_date3" value=""></span>
					                          <span class="L_sty">收款金额3</span>
					                         <span><input  class="mb10 receipt_amount3" type="text" name="receipt_amount3" value=""></span>
					                    </div>
					                	<div  class="table_col6" style="float:left;width:28%;min-width:24%;padding-left:4%;">
					                         <span class="L_sty" >是否付款</span>
					                         <span>
													<div class="out_search"  style="display:inline-block;width:163px;height:24px;font-size:14px;margin-bottom:14px">
														<input type="search" name="search" class="search" style="left:0px"> 
														<select name="whether_to_pay" multiple >
															<c:forEach var="whetherToPay" items="${whether_to_pay}"
																varStatus="status">
																<c:if test="${status.index>0}">
																	<option value="${whetherToPay.Status}">${whetherToPay.Status }</option>
																</c:if>
															</c:forEach>
														</select>
													</div> 
					                         <span class="L_sty">付款时间</span>
					                         <span><input  class="mb10 pay_date" type="date" name="pay_date" value=""></span>
					                        <span class="L_sty">是否开具发票</span>
					                         <span>
						                         <div class="out_search" style="width:160px;left:23px">
													<select name="whether_to_invoice"  style="width:164px;height:24px;margin-top:-15px">
															<option value="1">是</option>
															<option value="0">否</option>
													</select> 
												</div>
					                         </span>
					                        <span class="L_sty">发票快递单号</span>
					                         <span><input  class="mb10 tracking_no" type="text" name="tracking_no" value=""></span>
					                         <span class="L_sty">开票日期</span>
					                         <span><input  class="mb10 billing_date" type="date" name="billing_date" value=""></span>
					               	</div> 
					               	<div style="float:left;width:100%;margin-bottom:20px">
					               	 	<span style="float:left;width:8%;margin-left:3%;margin-right:1%">是否办理免税信用证</span><span style="float:left;width:88%"><textarea id="responseTa00" cols="115" rows="3" class="duty_free_remarks" name="duty_free_remarks"></textarea></span>
					               	 </div>
							  <div class="edit_btn"  style="float:left;width:100%;margin-bottom:15px">
									<input type="button" value="提交" class="bToggle btn" id="add_submit" >
									<input type="button" value="取消" class="bToggle btn" id="add_cancel" >
								</div>
						</div>
					<!--  修改   -->
					<div class="contract_update" style="display: none;">
						<div class="contract_title">修改合同信息</div>
						<div class="contractUpdate_close">关闭</div>
						<div class="basic_info" >
							<span style="display:none" class="order_id"></span>
					                <div class="table_title" style="margin-bottom:20px">合同基本信息</div>
					                <!-- 修改添加 -->
					                	<div class="table_col1  table_col" style="padding-right:0%">
					                		 <span class="L_sty"><i class="relevance"></i>客户名称</span>
					                		 <span class="customerID_span" style="display:none"></span>
					                         <!-- <span ><input class="mb10" type="text" name="customer" value=""  class="customer"></span> -->
					                         <div class="L_styinp">
						                         <div class="out_search out_searchCus" style="margin-bottom: 0;left:0;top:0;">
													<input type="search" name="customer_search" class="customer_search"> 
													<select name="customer_name" multiple style="width:40vw;">
													</select>
												</div>
					                         </div>
					                       	 <span class="L_sty"><i class="relevance"></i>联系人</span>
					                         <span><input  class="mb10" type="text" name="contact" value="" class="contact" disabled="disabled"></span> 
					                          <span class="L_sty"><i class="relevance"></i>联系方式</span>
					                         <span><input  class="mb10" type="text" name="contact_info" value="" class="contact_info"  disabled="disabled"></span>
					                          <span class="L_sty">销售代表</span>
					                         <span>
						                         <select name="sales_representative" class="mb10 sales_representative"  style="width:164px;height:24px">
													<c:forEach var="SaleRep" items="${SalesRep}"
														varStatus="status">
														<c:if test="${status.index>0}">
															<option value="${SaleRep.ID}" text="${SaleRep.Name }">${SaleRep.Name }</option>
														</c:if>
													</c:forEach>
												</select>
											</span>
					                	</div>
					                	<div  class="table_col2" style="float:left;width:30%;min-width:24%;padding-left:4%;padding-right:0%;margin-bottom:10px;border-left:1px solid #ccc;border-right:1px solid #ccc;">
					                         <span class="L_sty">合同名称</span>
					                         <span><input  class="mb10 " type="text" name="contract_title" value=""></span>
		                   					  <span class="L_sty">合同号</span>
					                         <span><input  class="mb10 contract_no" type="text" name="contract_no" value=""></span>
					                         <span class="L_sty">合同类型</span>
					                         <span>
						                         <select name="contract_category" class="mb10 contract_category"  style="width:164px;height:24px">
													<c:forEach var="contractCategory" items="${contract_category}" varStatus="status">
														<c:if test="${status.index>0}">
															<option value="${contractCategory.ID}"
																text="${contractCategory.Classify }">${contractCategory.Classify }</option>
														</c:if>
													</c:forEach>
												</select>
					                         </span>
					                         <span class="L_sty">装机地点</span>
					                         <span><input  class="mb10 installed_site" type="text" name="installed_site" value=""></span>
					                    </div>
					                	<div  class="table_col3" style="float:left;width:30%;min-width:24%;padding-left:5%;">
					                         <span class="L_sty">合同货期</span>
					                         <span><input  class="mb10 cargo_period" type="date" name="cargo_period" value=""></span>
					                         <span class="L_sty">合同签订日期</span>
					                         <span><input  class="mb10 date_of_sign" type="date" name="date_of_sign" value=""></span>
					                          <span class="L_sty">预计收款日期</span>
					                         <span><input  class="mb10  expected_receipt_date" type="date" name="expected_receipt_date" value=""></span>
					                          <span class="L_sty">装机时间</span>
					                         <span><input  class="mb10 installed_time" type="date" name="installed_time" value=""></span>
					               		 </div>
					               	
					               	 <div style="float:left;width:100%;margin-bottom:20px">
					               	 	<span style="float:left;width:9%">备注</span><span style="float:left;width:90%"><textarea id="responseTa02" cols="117" rows="3" class="remarks" name="remarks"></textarea></span>
					               	 </div>
					               </div>		 
					                <div class="table_title" style="float:left;width:100%;height:20px;margin-left:30px;margin-bottom:15px">合同明细</div>
					                 <div class="table_col4" style="float:left;width:30%;min-width:24%;padding-left:3%;padding-right:0%;">
					                		 <span class="L_sty">合同金额(USD)</span>
					                         <span><input  class="mb10 usd_quotes" type="text" name="usd_quotes" value=""></span>
					                       	  <span class="L_sty">合同金额(RMB)</span>
					                         <span><input  class="mb10 rmb_quotes" type="text" name="rmb_quotes" value=""></span> 
					                         <span class="L_sty">付款条件</span>
					                         <span>
					                         		<div class="out_search">
														<input type="search" name="pay_search" class="pay_search">
														<select name="payment_terms" multiple>
															<c:forEach var="PayTerm" items="${PayTerms}"
																varStatus="status">
																<c:if test="${status.index>0}">
																	<option value="${PayTerm.ID}">${PayTerm.Condition }</option>
																</c:if>
															</c:forEach>
														</select>
													</div>
					                         </span>
					                          <span class="L_sty">对应报价单</span>
					                          <div style="display:inline-block;margin-bottom:15px" class="quotationBox">
													<input type="text" class="update_quotation"  style="width:160px;height:20px;"> 
													<ul class="quotationlist">
													</ul>
												</div>
					                          <span class="L_sty">合同文件</span>
					                         <span>
					                         	<form class="update_fileBox"  id="update_fileBox1"  method="post" target="myframe" action="OrderUpload" enctype="multipart/form-data" >
												<input class="update_fileCont"/><!-- </span> -->
												<span class="update_uploadText">上传</span>
											    <input class="update_change update_file" type="file" multiple="multiple" name="fileName"/>
											    <input name="classify" value="ContractPath" type="hidden"/>
												</form>
					                         </span>
					                         <span class="L_sty">技术协议</span>
					                         <span>
					                         	<form class="update_fileBox"  id="update_fileBox2"  method="post" target="myframe" action="OrderUpload" enctype="multipart/form-data" >
												<input class="update_fileCont1" /><!-- </span> -->
												<span class="update_uploadText">上传</span>
											    <input class="update_change update_agreement" type="file" multiple="multiple" name="fileName"/>
											    <input name="classify" value="TechnologyPath" type="hidden"/>
												</form>
					                         </span>
					                	</div>
					                	<div  class="table_col5" style="float:left;width:30%;min-width:24%;padding-left:3%;padding-right:0%;margin-bottom:10px;border-left:1px solid #ccc;border-right:1px solid #ccc;">
					                         <span class="L_sty">收款日期1</span>
					                         <span><input  class="mb10 receipt_date1" type="date" name="receipt_date1" value=""></span>
					                          <span class="L_sty">收款金额1</span>
					                         <span><input  class="mb10 receipt_amount1" type="text" name="receipt_amount1" value=""></span>
					                        <span class="L_sty">收款日期2</span>
					                         <span><input  class="mb10 receipt_date2" type="date" name="receipt_date2" value=""></span>
					                         <span class="L_sty">收款金额2</span>
					                         <span><input  class="mb10 receipt_amount2" type="text" name="receipt_amount2" value=""></span>
					                           <span class="L_sty">收款日期3</span>
					                         <span><input  class="mb10 receipt_date3" type="date" name="receipt_date3" value=""></span>
					                          <span class="L_sty">收款金额3</span>
					                         <span><input  class="mb10 receipt_amount3" type="text" name="receipt_amount3" value=""></span>
					                    </div>
					                	<div  class="table_col6" style="float:left;width:28%;min-width:24%;padding-left:4%;">
					                         <span class="L_sty" >是否付款</span>
					                         <span>
													<div class="out_search"  style="display:inline-block;width:163px;height:24px;font-size:14px;margin-bottom:14px">
														<input type="search" name="search" class="search" style="left:0px"> 
														<select name="whether_to_pay" multiple >
															<c:forEach var="whetherToPay" items="${whether_to_pay}"
																varStatus="status">
																<c:if test="${status.index>0}">
																	<option value="${whetherToPay.Status}">${whetherToPay.Status }</option>
																</c:if>
															</c:forEach>
														</select>
													</div> 
					                         <span class="L_sty">付款时间</span>
					                         <span><input  class="mb10 pay_date" type="date" name="pay_date" value=""></span>
					                        <span class="L_sty">是否开具发票</span>
					                         <span>
						                         <div class="out_search" style="width:160px;left:23px">
													<select name="whether_to_invoice"  style="width:164px;height:24px;margin-top:-15px">
															<option value="1">是</option>
															<option value="0">否</option>
													</select> 
												</div>
					                         </span>
					                        <span class="L_sty">发票快递单号</span>
					                         <span><input  class="mb10 tracking_no" type="text" name="tracking_no" value=""></span>
					                         <span class="L_sty">开票日期</span>
					                         <span><input  class="mb10 billing_date" type="date" name="billing_date" value=""></span>
					                         <span class="L_sty"><input type="button" id="invoice_open" class="eou-button eou-button-radius eou-button-30 eou-button-w80" value="申请开票"></span>
					               	</div> 
					               	<div style="float:left;width:100%;margin-bottom:20px">
					               	 	<span style="float:left;width:8%;margin-left:3%;margin-right:1%">是否办理免税信用证</span><span style="float:left;width:88%"><textarea id="responseTa01" cols="115" rows="3" class="duty_free_remarks" name="duty_free_remarks"></textarea></span>
					               	 </div>
							  <div class="edit_btn"  style="float:left;width:100%;margin-bottom:15px">
									<input type="button" value="提交" class="bToggle btn" id="update_submit" >
									<input type="button" value="取消" class="bToggle btn" id="update_cancel" >
								</div>
						</div>
					<iframe style="display:none;" name="myframe"></iframe>  <!-- 表单提交 -->

					<div class="contract" style="display: none;">
						<div class="contract_title">合同配置信息</div>
						<div class="contract_close">关闭</div>
						<div class="contarct_info">
							<table border="1" cellspacing="0" cellpadding="0" class="contractPage">
								<thead>
									<tr>
										<th>订单号</th>
										<th>序号</th>
										<th>型号</th>
										<th>描述</th>
										<th>数量</th>
										<th>货期</th>
										<th>预计货期</th>
										<th>货运单号</th>
										<th>订单状态</th>
										<th>修改</th>
										<th>删除</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
						<div class="edit_btn">
							<input type="text" id="order_id" style="display: none"> 
							<input type="button" value="添加" class="bToggle" id="contract_apply_add" title="点击后在产品型号框输入型号按回车完成搜索">
							<!-- <input type="button" value="导入" class="bToggle" id="upload"> -->
							<input type="button" value="匹配报价单" class="bToggle" id="MatchQuotation" style="width: 80px;">
						</div>
						<div class="apply_add_info" style="display: none">
							<div class="apply_left">
								产品型号 <input type="search" name="search" title="输入型号按回车完成搜索" placeholder="输入型号按回车搜索">
								<select id="select" multiple style="width: 220px;height: 150px;">
								</select>
							</div>
							<div class="apply_right">
								数量&nbsp;&nbsp;&nbsp;<input type="text" name="equipment_counts">
								&nbsp;&nbsp;&nbsp;<input type="button" name="add_submit"
									value="提交" class="bToggle" text="">
							</div>
						</div>
					</div>
					<div class="supply_detail" style="display: none;">
						<div class="contract_title">合同明细</div>
						<div class="supply_detail_close">关闭</div>
						<div class="supply_detail_info">
							<table border="1" cellspacing="0" cellpadding="0"
								class="contractPage">
								<thead>
									<tr>
										<th>合同金额（USD）</th>
										<th>合同金额（RMB）</th>
										<th>付款条件</th>
										<th>是否付款</th>
										<th>收款日期1</th>
										<th>收款金额1</th>
										<th>收款日期2</th>
										<th>收款金额2</th>
										<th>收款日期3</th>
										<th>收款金额3</th>
										<th>是否开具发票</th>
										<th>开票日期</th>
										<th>是否办理免税，信用证</th>
									</tr>
								</thead>
								<tbody>

								</tbody>
							</table>
						</div>
					</div>

					<c:choose>
						<c:when test="${queryType=='common' }">
							<c:set var="queryUrl" value="Price?currentPage="></c:set>
						</c:when>
						<c:when test="${queryType=='singleSelect' }">
							<c:choose>
								<c:when test="${ classify1 =='合同货期' || classify1 == '实际货期' || classify1== '预计货期' }">
									<c:set var="queryUrl" value="GetOrderByPageOneInPrice?PageType=0&type1=${classify1}&searchContent1=${parameter1}&start_time1=${start_time1}&end_time1=${end_time1}&currentPage="></c:set>
								</c:when>
								<c:otherwise>
									<c:set var="queryUrl" value="GetOrderByPageOneInPrice?PageType=0&type1=${classify1}&searchContent1=${parameter1}&currentPage="></c:set>
								</c:otherwise>
							</c:choose>
						</c:when>

						<c:when test="${queryType=='mixSelect' }">
							<c:choose>
								<c:when test="${ classify1 =='合同货期' || classify1 == '实际货期' || classify1== '预计货期' || classify2 =='合同货期' || classify2 == '实际货期' || classify2== '预计货期'}">
									<c:set var="queryUrl" 
									value="GetOrderByPageTwoInPrice?PageType=0&type1=${classify1}&searchContent1=${parameter1}&start_time1=${start_time1}&end_time1=${end_time1}&type2=${classify2}&searchContent2=${parameter2}&start_time2=${start_time2}&end_time2=${end_time2}&currentPage=">
									</c:set>
								</c:when>
								<c:otherwise>
									<c:set var="queryUrl"
									value="GetOrderByPageTwoInPrice?PageType=0&type1=${classify1}&searchContent1=${parameter1}&type2=${classify2}&searchContent2=${parameter2}&currentPage="></c:set>
								</c:otherwise>
							</c:choose>
						</c:when>
					</c:choose>

					<div id="page">
						<div class="pageInfo">
							当前是第&nbsp;<span id="currentPage">${currentPage }</span>&nbsp;页,&nbsp;总计&nbsp;<span
								id="allPage">${pageCounts }</span>页
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
								name="Gotojump" onclick="PageJump('${queryUrl}')"> <input
								type="button" class="bToggle" value="尾页" id="lastPage"
								name="lastPage" onclick="LastPage('${queryUrl}')">
						</div>
					</div>
					<div class="clear_float"></div>
				</div>
			</div>
			<div class="MailBar_cover_color" style="display: none;"></div>
			
			<!-- 添加是否可以发货信息 -->
			<div class="contract_send" style="display: none;">
				<div class="contract_title">确认发货</div>
				<div class="contractSend_close">关闭</div>
				<div class="basic_info">
					<div class="table_title">是否可以发货</div>
					<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
						<tbody>	
							<tr>
							    <td>公司名称</td>
								<td><input type="text" name="ConsigneeCompany" value="" id="ConsigneeCompany"></td>	
								<td>公司地址</td>
								<td><input type="text" name="ConsigneeAddress" value=""  id="ConsigneeAddress"></td>
							</tr>
							<tr>
								
								<td>联系人</td>
								<td><input type="text" name="ConsigneeContacts" value="" ></td>
								<td>电话</td>
								<td><input type="text" name="ConsigneeTel" value=""></td>
							</tr>
							<tr style="display:none">
								
								<td>序号</td>
								<td><input type="text" name="PriceId" value="" ></td>
								<td>合同号</td>
								<td><input type="text" name="ContractNo" value=""></td>
								<td>合同类型</td>
								<td><input type="text" name="ContractTitle" value=""></td>
							</tr>
						</tbody>
					</table>		
				</div>
				<div class="edit_btn">
					<input type="button" value="提交" class="bToggle" id="send_submit">
					<input type="button" value="取消" class="bToggle" id="send_cancel">
				</div>
			</div>
			
			<!-- 弹出组件 -->
			<!-- 申请开票 -->
			<div class="invoice_topen_bg" style="display: none;"></div>
			<div class="invoice_topen" style="display: none;">
			    <div class="invoice_topen_tit">
			        <div class="invoice_topen_tit_l">开票委托单</div>
			        <div class="invoice_topen_tit_r">关闭</div>
			    </div>
			    <div class="invoice_topen_body">
			        <div class="invoice_topen_body_in">
			            <div class="invoice_topen_body_main">
							<table class="eou-table-collapse customer_info_table" id="invoice_tableopen">
								<tbody>
									<tr class="tr_01">
										<td colspan="2" class="td_col2">购货方企业名称</td>
										<td colspan="3" class="td_col3 canedit_td price_InvoiceTitle"></td>
										<td colspan="2" class="td_col2">电话</td>
										<td colspan="3" class="td_col3 canedit_td price_Telephone"></td>
									</tr>
									<tr class="tr_02">
										<td colspan="2" class="td_col2">购货方经营地址</td>
										<td colspan="3" class="td_col3 canedit_td price_RegisterAddress"></td>
										<td colspan="2" class="td_col2">邮编</td>
										<td colspan="3" class="td_col3 canedit_td price_LinkZipCode"></td>
									</tr>
									<tr class="tr_03">
										<td colspan="2" class="td_col2">购货方税务登记号</td>
										<td colspan="8" class="td_col8 canedit_td price_TaxPayerIdentityNO"></td>
									</tr>
									<tr class="tr_04">
										<td colspan="2" rowspan="2" class="td_col2">购货方开户银行及帐号</td>
										<td colspan="8" class="td_col8 canedit_td price_DepositBank" style="border-bottom: none"></td>
									</tr>
									<tr class="tr_0401">
										<td colspan="8" class="td_col8 canedit_td price_Account" style="border-top: none"></td>
									</tr>
									<tr class="tr_05">
										<td colspan="10" class="td_col10">销货内容</td>
									</tr>
									<tr class="tr_06">
										<td class="td_col1">货物名称或税劳务名称</td>
										<td class="td_col1">规格型号</td>
										<td class="td_col1">计量单位</td>
										<td class="td_col1">数量</td>
										<td class="td_col1">单价（含税）</td>
										<td class="td_col1">金额</td>
										<td class="td_col1" title="选填6%或16%">税率(%)</td>
										<td class="td_col1">税额</td>
										<td class="td_col1">价税合计</td>
										<td class="td_col1">删除</td>
									</tr>
									<tr class="tr_07 addTrItem">
										<td class="td_col1 canedit_td"></td>
										<td class="td_col1 canedit_td"></td>
										<td class="td_col1 canedit_td"></td>
										<td class="td_col1 canedit_td"></td>
										<td class="td_col1 canedit_td"></td>
										<td class="td_col1 canedit_td"></td>
										<td class="td_col1 canedit_td"></td>
										<td class="td_col1 canedit_td"></td>
										<td class="td_col1 canedit_td"></td>
										<td class="td_col1"><input type="button" class="eou-button eou-button-30 eou-button-w50 del_tr_item" value="删除"></td>
									</tr>
									<tr class="tr_08">
										<td class="td_col1">合计</td>
										<td class="td_col1"></td>
										<td class="td_col1"></td>
										<td class="td_col1 canedit_td"></td>
										<td class="td_col1"></td>
										<td class="td_col1"></td>
										<td class="td_col1"></td>
										<td class="td_col1"></td>
										<td class="td_col1 canedit_td"></td>
										<td class="td_col1"></td>
									</tr>
									<tr class="tr_09">
										<td colspan="2" class="td_col2">备注</td>
										<td colspan="8" class="td_col8 canedit_td"></td>
									</tr>
									<tr class="tr_010">
										<td class="td_col1">取票方式</td>
										<td colspan="2" class="td_col2">1、自取&nbsp;&nbsp;2、快递</td>
										<td class="td_col1">2</td>
										<td class="td_col1">快递地址</td>
										<td colspan="5" class="td_col5 canedit_td iaddress"></td>
									</tr>
									<tr class="tr_011">
										<td colspan="2" class="td_col2" style="border-right: none;">经办人签字：</td>
										<td colspan="3" class="td_col3" style="border-left: none;"></td>
										<td  colspan="2" class="td_col2" style="border-right: none;">防伪税控主机审核人签名：</td>
										<td colspan="3" class="td_col3" style="border-left: none;"></td>
									</tr>
									<tr class="tr_012">
										<td colspan="5" class="td_col5"></td>
										<td colspan="5" class="td_col5">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日</td>
									</tr>
								</tbody>
							</table>
			            </div>
			        </div>
			    </div>
			    <div class="invoice_topen_foot">
			        <div class="invoice_topen_foot_in">
			            <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w100" id="invoice_topen_submit" value="确认并发送">
			            <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" id="invoice_topen_close" value="取消">
			        </div>
			    </div>
			</div>
			<div class="mail_select" style="display: none;">
				<div class="mail_select_tit">
					<div class="mail_select_tit_l">发票与备注</div>
					<div class="mail_select_tit_r">关闭</div>
				</div>
				<div class="mail_select_body">
				选择发票：
					<select id="mail_select_sel" class="eou-bstr-input">
						<option value="增值税专用发票">增值税专用发票</option>
						<option value="普通发票">普通发票</option>
					</select>
				填写备注：
					<input type="text" id="mail_select_inp" class="eou-bstr-input">
				</div>
				<div class="mail_select_foot" >
					<input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" value="确定">
				</div>
			</div>
			<div class="invoice_mail" style="display:none;">
				<div class="invoice_mail_tit">
					<div class="invoice_mail_tit_l">开票资料邮件模板</div>
			        <div class="invoice_mail_tit_r">关闭</div>
				</div>
				<div class="invoice_mail_body">
					<div class="invoice_mail_body_in">
						<div class="invoice_mail_body_to">
							<div class="mail_left">收件人</div>
							<div class="mail_right">
								<input type="text" class="eou-bstr-input" value="wangxiaoliang@eoulu.com">
							</div>
						</div>
						<div class="invoice_mail_body_cc">
							<div class="mail_left">抄送人</div>
							<div class="mail_right">
								<div class="mail_right_l">
									<input type="text" class="eou-bstr-input" value="zhaowenzhen@eoulu.com">
								</div>
								<div class="mail_right_m">
									抄送人2<input type="text" class="eou-bstr-input" value="zhaona@eoulu.com">
								</div>
								<div class="mail_right_r">
									抄送人3<input type="text" class="eou-bstr-input" value="fangyuanyuan@eoulu.com">
								</div>
							</div>
						</div>
						<div class="invoice_mail_body_subject">
							<div class="mail_left">主题</div>
							<div class="mail_right">
								<input type="text" class="eou-bstr-input" id="invoice_mail_subject">
							</div>
						</div>
						<div class="invoice_mail_body_con">
							<div class="mail_left">正文</div>
							<div class="mail_right">
								<textarea id="invoice_mail_content" cols="130" rows="7"></textarea>
							</div>
						</div>
					</div>
				</div>
				<div class="invoice_mail_foot">
					<div class="invoice_mail_foot_in">
			            <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" id="invoice_mail_submit" value="发送">
			            <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" id="invoice_mail_close" value="取消">
			        </div>
				</div>
			</div>
		<!-- Price_sticker-con结束 -->
		</div>
    <!-- Price_sticker结束 -->
    </div>

    <!-- Price_footer -->
    <div id="Price_footer">
        <div id="eoulu-copy-out" style="height:40px;width:calc(100% - 2px);">
            <div style="width:100%;height:5px;"></div>
            <div id="eoulu-copy" style="width:100%;height:35px;font-size:12px;color:#888;line-height:35px;z-index: 2;">
                <hr style="height:1px;color:#999;width: calc(100% - 3px);" />
                <div style="width:100%;text-align:center;display:inline-block;">Copyright  ©&nbsp;<span class="YEAR">2018</span>&nbsp<a href="http://www.eoulu.com/h-col-268.html" class="EHref" target="_blank" style="color:blue;">Eoulu</a> Tech. Co.,Ltd.</div>
            </div>
        </div>
    </div>
<!-- Price_wrapper结束 -->
</div>
	
</body>
<script src="js/libs/integrationLibs/jquery-cookie-ajaxfile-77692a8173.min.js"></script>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<!-- <script src="plugins/cookie/jquery.cookie.js"></script> -->
<script src="js/swiper-3.4.1.jquery.min.js" type="text/javascript" charset="utf-8"></script>
<!-- <script src="js/ajaxfileupload.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/msgbox.js"></script>
<script src="js/msgbox_unload.js"></script>
<!-- <script src="js/global/myFunction.js"></script> -->
<script src="js/price.js?iv=201809121749"></script>
<!-- <script src="js/global/version-control.js?iv=201808240937" charset="utf-8"></script> -->
<script type="text/javascript">
// 全局响应式textarea
    function globalRespTa(){
        var curW = $(window).width();
        var colS = Math.round(curW/11.68);
        $("#responseTa").attr("cols",colS);
        $("#responseTa00").attr("cols",colS);
        $("#responseTa01").attr("cols",colS);
        $("#responseTa02").attr("cols",colS);
    }
    $(window).on("resize",function(){
    	globalRespTa();
    });
    globalRespTa();
	function FistPage(arg) {
		var currentHref = window.location.href.split("ActualDelivery=")[1];
		window.location.href = arg + "1"+"&ActualDelivery="+currentHref;
	}
	function UpPage(arg) {
		var currentHref = window.location.href.split("ActualDelivery=")[1];
		window.location.href = arg+"&ActualDelivery="+currentHref;
	}
	function NextPage(arg) {
		var currentHref = window.location.href.split("ActualDelivery=")[1];
		window.location.href = arg+"&ActualDelivery="+currentHref;
	}
	function PageJump(arg) {
		var currentHref = window.location.href.split("ActualDelivery=")[1];
		var jumpNumber = document.getElementById("jumpNumber").value;
		if (jumpNumber == null || jumpNumber == 0) {
			jumpNumber = $('#currentPage').html();
		} else if (jumpNumber > parseInt($('#allPage').html())) {
			jumpNumber = $('#allPage').html();
		}
		window.location.href = arg + jumpNumber+"&ActualDelivery="+currentHref;
	}
	function LastPage(arg) {
		var currentHref = window.location.href.split("ActualDelivery=")[1];
		var jumpNumber = parseInt($('#allPage').html());
		window.location.href = arg + jumpNumber+"&ActualDelivery="+currentHref;
	}

$(function() {
		$('.cover-color').height($("#Price_sticker-con").height());
		$('.invoice_topen_bg').height($("#Price_sticker-con").height());
		var MyDate = new Date();
		var Y =  MyDate.getFullYear();
		var str = Y +"年度总目标值：";
		$(".prompt-content1").html("").html(str);
		
		if ($('#currentPage').html() == 1) {
			$('#fistPage').attr('disabled', 'true');
			$('#fistPage').removeClass('bToggle');
			$('#upPage').attr('disabled', 'true');
			$('#upPage').removeClass('bToggle');
		}
		if ($('#allPage').html() == $('#currentPage').html()) {
			$('#lastPage').attr('disabled', 'true');
			$('#lastPage').removeClass('bToggle');
			$('#nextPage').attr('disabled', 'true');
			$('#nextPage').removeClass('bToggle');
		}
	if (
<%=request.getAttribute("queryType").equals("singleSelect")%>
	||
<%=request.getAttribute("queryType").equals("common")%>
	) {
		$('.select2').hide();
	} else {
		$('.select2').show();
	}

	// 未付款闪烁
	// $(".t1_WhetherToPay:contains('未付款')")
	$(".t1_WhetherToPay:contains(未付款)").each(function(){
		var startDay = $(this).siblings(".t1_ExpectedReceiptDate").text();
		if(startDay == "" || startDay == "--"){
			startDay = globalGetToday(false);
		}
		var diffDays = parseFloat(globalCalcTimeDiff(startDay, globalGetToday(false), true));
		diffDays <= 7 ? $(this).addClass("change") : null;
	});

});
	
        var html = document.documentElement;
        var htmlw = html.clientWidth;
        html.style.fontSize = htmlw / 192 + 'px';
        $(".prompt-top").css("lineHeight",35/parseFloat($(".prompt-top ul li").css("fontSize")));
    window.onresize = function(){ 
	 	var html = document.documentElement;
        var htmlw = html.clientWidth;
        // console.log(htmlw)
        html.style.fontSize = htmlw / 192 + 'px';
        $(".prompt-top").css("lineHeight",35/parseFloat($(".prompt-top ul li").css("fontSize")));
    }
    </script>
</html>