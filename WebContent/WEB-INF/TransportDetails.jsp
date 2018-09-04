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
	<title>物流统计-详细记录</title>
	<link rel="shortcut icon" href="image/eoulu.ico" />
	<link rel="bookmark" href="image/eoulu.ico" />
	<link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css">
	<link rel="stylesheet" href="css/global/eouluCustom.css" type="text/css">
	<link rel="stylesheet" href="css/TransportDetails.css" type="text/css">
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

	    /*布局*/
        html,body{
            width:100%;
            height:100%;
        }

        #transports_wrapper {
            position:fixed;
            overflow:auto;
            width:100%;
            height:100%;
            box-sizing:border-box;
        }

        #transports_sticker {
            width:100%;
            min-height:100%;
            box-sizing:border-box;
            position:relative;
        }

        #transports_sticker-con {
            padding-bottom:40px;
            box-sizing:border-box;

        }

        #transports_footer {
            margin-top:-40px;
        }
	</style>
</head>
<body>
	<div id="transports_wrapper">
	    <div id="transports_sticker">
	        <div id="transports_sticker-con">
				<%@include file="top.jsp"%>
				<div class="container-fluid">

					<div class="g_container">
						<!-- 切换选项卡 -->
						<div class="g_container_info">
							<span class="AllNoSend_tab current_tab">所有未发货</span>
							<span class="OtherNoSend_tab">未包括等待客户付款</span>
							<span class="Overdue_tab">当前已逾期</span>
							<span class="OverdueRisk_tab">存在逾期风险</span>
							<span class="ShippedMonth_tab">本月已发</span>
							<span class="transportdetail_tab" style="display: none;">详细记录</span>
						</div>

						<!-- 年月日 -->
						<div class="g_container_info" style="display: none;">
							<div class="g_container_info_l">
								
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
						<!-- 主体 -->
						<div class="g_container_body">
							<div class="g_container_body_in">
								<div class="g_container_body_info">
									<div class="g_container_body_info_l"></div>
									<div class="g_container_body_info_r">
										<button type="button" class="btn btn-info" style="display:none">视图</button>
									</div>
								</div>
								<div class="g_container_body_table">
									<table class="AllNoSend_table" style="display:none">
										<thead>
											<tr>
												<th>序号</th>
												<th>区域</th>
												<th>客户名称</th>
												<th>合同名称</th>
												<th>合同号</th>
												<th>合同签订日期</th>
												<th>合同货期</th>
												<th>预计货期</th>
												<th>实际货期</th>
											</tr>
										</thead>
										<tbody>
											
										</tbody>
									</table>

									<table class="OtherNoSend_table" style="display:none">
										<thead>
											<tr>
												<th>序号</th>
												<th>区域</th>
												<th>客户名称</th>
												<th>合同名称</th>
												<th>合同号</th>
												<th>合同签订日期</th>
												<th>合同货期</th>
												<th>预计货期</th>
												<th>实际货期</th>
											</tr>
										</thead>
										<tbody>
											
										</tbody>
									</table>

									<table class="Overdue_table" style="display:none">
										<thead>
											<tr>
												<th>序号</th>
												<th>区域</th>
												<th>客户名称</th>
												<th>合同名称</th>
												<th>合同号</th>
												<th>合同签订日期</th>
												<th>合同货期</th>
												<th>预计货期</th>
												<th>实际货期</th>
											</tr>
										</thead>
										<tbody>
											
										</tbody>
									</table>

									<table class="OverdueRisk_table" style="display:none">
										<thead>
											<tr>
												<th>序号</th>
												<th>区域</th>
												<th>客户名称</th>
												<th>合同名称</th>
												<th>合同号</th>
												<th>合同签订日期</th>
												<th>合同货期</th>
												<th>预计货期</th>
												<th>实际货期</th>
											</tr>
										</thead>
										<tbody>
											
										</tbody>
									</table>

									<table class="ShippedMonth_table" style="display:none">
										<thead>
											<tr>
												<th>序号</th>
												<th>区域</th>
												<th>客户名称</th>
												<th>合同名称</th>
												<th>合同号</th>
												<th>合同签订日期</th>
												<th>合同货期</th>
												<th>预计货期</th>
												<th>实际货期</th>
											</tr>
										</thead>
										<tbody>
											
										</tbody>
									</table>

									<table class="transportdetail_table" style="display:none">
										<thead>
											<tr>
												<th>月份</th>
												<th>所有未发货</th>
												<th>未包括等待客户付款</th>
												<th>已逾期</th>
												<th>存在逾期风险</th>
												<th>本月已发</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>1</td>
												<td>2</td>
												<td>3</td>
												<td>4</td>
												<td>5</td>
												<td>6</td>
											</tr>
										</tbody>
										<tfoot>
											<tr>
												<td>总计</td>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
											</tr>
										</tfoot>
									</table>

									<div class="transportdetail_div" style="display:none;"></div>
								</div>
							</div>
						</div>

						<!-- 翻页 -->
						<div class="m_page">
					        <div class="pageInfo">当前是第&nbsp;<span id="currentPage">1</span>&nbsp;页,&nbsp;总计&nbsp;<span id="allPage"></span>页</div>
					        <div class="changePage">
					            <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" value="首页" id="fistPage" name="fistPage">
					            <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" value="上一页" id="upPage">
					            <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" value="下一页" id="nextPage"> 跳到第 <input
					                type="text" id="jumpNumber" name="jumpNumber" class="jumpNumber"
					                style="width: 30px; color: #000"> 页 
					            <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" value="GO" id="Gotojump"
					                name="Gotojump">
					            <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" value="尾页" id="lastPage" name="lastPage">
					        </div>
						</div>
					</div>
				</div>
	        <!-- transports_sticker-con结束 -->
	        </div>
	    <!-- transports_sticker结束 -->
	    </div>

	    <!-- transports_footer -->
	    <div id="transports_footer">
	        <div id="eoulu-copy-out" style="height:40px;width:calc(100% - 2px);">
	            <div style="width:100%;height:5px;"></div>
	            <div id="eoulu-copy" style="width:100%;height:35px;font-size:12px;color:#888;line-height:35px;z-index: 2;">
	                <hr style="height:1px;color:#999;width: calc(100% - 3px);" />
	                <div style="width:100%;text-align:center;display:inline-block;">Copyright  ©&nbsp;<span class="YEAR">2018</span>&nbsp<a href="http://www.eoulu.com/h-col-268.html" class="EHref" target="_blank" style="color:blue;">Eoulu</a> Tech. Co.,Ltd.</div>
	            </div>
	        </div>
	    </div>

	<!-- transports_wrapper结束 -->
	</div>
<!-- <script src="js/libs/jquery-3.3.1.min.js"></script> -->
<!-- <script src="js/libs/echarts-all.js"></script> -->
<!-- <script src="js/msgbox_unload.js"></script> -->
<!-- <script src="js/libs/jquery.cookie.js"></script> -->
<!-- <script src="js/global/myFunction.js"></script> -->
<script src="js/TransportDetails.js"></script>
</body>
</html>