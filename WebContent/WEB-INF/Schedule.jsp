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
<title>员工行程</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="css/libs/daterangepicker.css" type="text/css">
<link rel="stylesheet" href="plugins/awesomplete/awesomplete_all-a2ac84f236.min.css" type="text/css" charset="utf-8">
<link rel="stylesheet" href="css/modules/serviced/schedule-0f26355e98.min.css" type="text/css">
<link rel="stylesheet" href="css/fullcalendar.css?iv=201809251756" type="text/css">
<style>
	.g-nav-ul li {
		box-sizing: content-box;
	}

	body, .contain {
		min-width: 1222px;
	}

	.bodyContent {
		/*display: flex;*/
		position: relative;
        width: 90%;
        min-width: 1100px;
        height: 705px;
        margin: 0 auto ;
        font-size: 16px;
        /*margin-bottom: -40px;*/
        /*overflow-x: auto;*/
	}

	#main-box {
		position:absolute;
		top:0;
		display: inline-block;
		/*flex-grow: 61;
		flex-basis: 325px;*/
		height: 600px;
		padding-right: 5px;
		width: 305px;
		margin-top:0;
		/*margin-right:3%;*/
	}

	.flex-right-con {
		position:relative;
		left:305px;
		display: inline-block;
		/*flex-grow: 180;*/
		/*min-width: 665px;*/
		width: calc(100% - 305px);
		overflow-x: auto;
	}

	.riliheight {
		/*width: calc(100% - 30px);*/
		border:10px solid #dbdbdb; 
		border-radius:10px;
		margin-top: 95px;
		width:300px;
		background-color:#dbdbdb;
	}

	.info-rili {
		/*display:inline-block; */
		font-size: 14px;
		height:54px;
		/*margin-left:360px;*/
		/*width: calc(100% - 362px);*/
		/*flex-grow: 481;*/
		width: 270px;
		min-width: 270px;
		position: absolute;
		top: 50px;
		right: 0;
		padding-left:5px;
	}

	.info-rili i {
        position: absolute; 
        bottom: 10px; 
        right: 7px; 
        top: auto; 
        cursor: pointer;
        display:inline-block;
        width:17px;
        height:16px;
        background: url("./image/rili.png") no-repeat center;;
     }
	
	#config-demo {
		width: 200px;
	}

	.trip-city, .trip-distance, .trip-cost-calc, .trip-staff-times {
	    width: 100%;
	    height: 500px;
	    background: rgba(255,255,255,0.9);
	    margin-top: 20px;
	}

	#main, #main2, #main3, #echartContainer1, #echartContainer2 {
		height: 460px;
		width: 96%;
		margin: 6px 2% 2px 2%;
	}

	/*.aa1:hover, .aa2:hover, .aa3:hover{
		cursor: pointer;
		color: #00aeef;
	}*/

	/*.reloadDiv a {
		text-decoration: none;
	}*/
	.m-add-schedule {
	    display: flex;
	}

	.m-add-schedule-l, .m-add-schedule-m, .m-add-schedule-r {
	    flex: 1;
	}

	.m-add-schedule-l, .m-add-schedule-m {
		border-right: 1px solid #999;
	}

	ul {
		margin-bottom: 0;
	}

	h5 {
		font-size:15px;
		margin-top: 0;
    	margin-bottom: 0;
	}

	h6 {
		margin-top: 0;
    	margin-bottom: 0;
	}

	div#eoulu-copy hr{
		margin-top: 0;
	    margin-bottom: 0;
	    border: none;
	    border-top: none;
	}

	.kalendae .k-days span.closed {
	    background:red;
	}

	.dateWeek_div {
		display:none;
	}

	.dateRange_div {
		display: inline-block;
	}
</style>
</head>
<body>
<div class="loading_div_g_div" style="position: fixed;top: 0;bottom: 0;left: 0;right: 0;z-index: 100;width: 100vw;height: 100vh;background-color: #5bc0de;filter:alpha(opacity=90);-moz-opacity:0.9;-khtml-opacity:0.9;opacity: 0.9;display: -webkit-flex;display: flex;justify-content: center;align-items: center;">
    <img src="image/loading/Spinner-1s-200px.gif" alt="loading。。。">
</div>

<!-- <div class="cover-all" style="position:absolute;top:0;left:0;width:100vw;height:100vh;filter:Alpha(opacity=99);background:rgba(250,250,250,0.99);opacity:0.99;z-index:888;"></div>
<div class="cover-all-img" style="width:200px;height:25px;position:absolute;top:40vh;left:calc(50vw - 100px);z-index:889;">
	<img src="image/response-loading.gif" alt="正在加载..." width="190" height="14" style="display:block">
</div> -->
<!-- 	头部开始 -->
<%@include file="top.jsp"%>
<!-- 	头部结束 -->
<div class="contain">
	<div class="content" style="padding-bottom: 1px">
		<!-- 	=======================导航栏   开始 ================================== -->
			<%@include file="nav.jsp"%>
		<div class="bodyContent">
				<!-- 员工行程左侧部分 -->
				<div class="clearfix" id='main-box'>
					<div class="left-fixed-div" style="width:300px;height:600px;">
			   			<div class="riliheight">
			       			<!-- 加上 日历的头部-->
			       			<div class="calendar_title">
			       				<div class="dateBox">
			       					<span class="circle circleL"></span>
			       					<span class="circle circleR"></span>
			       					<span class="dateBox_title">  </span>
			       					<span class="dateBox_day"></span>
			       				</div>
			       				<div class="dataBox_text">
			       					<span class="dataBox_textDay"></span>
			       					<span class="dataBox_textLunar"></span>
			       					<span class="dataBox_textYear"></span>
			       					<!-- <span class="dataBox_textStar">射手座</span> -->
			       					<span class="dataBox_textCompany">伊欧陆系统集成有限公司</span>
			       				</div>
			       			</div>
			       			<span class="backToday">返回今天</span>
			       			 <div id="calendar" class="main-body fl" style="width:100%;min-width:80%;clear:both;padding-bottom: 13px;border-radius: 10px;"></div>
			       				<!-- 日历下面部分搜索框 -->
			       			  <form id="top_text_from" name="top_text_from" method="get" action="ScheduleRoute">
								<input type="text" id="search" value="search" style="display: none;">
								<div class="select-content">
									<select class="classify" style="float:left;border:1px solid darkgrey;border-right:none;width:90px;height:31px">
											  		<option  value="员工姓名">员工姓名</option>
											  		<option value = "客户单位">客户单位</option>
											  		<option value="服务事项">服务事项</option>
											  		<option value="出发地">出发地</option>
											  		<option value="目的地">目的地</option>
											  		<option value="出发时间">出发时间</option>
											  		<option value="到达时间">到达时间</option>
											  		<option value="酒店信息">酒店信息</option>
									</select>
									<div class="select1" style="float:left;width:45%">
										<input type="text" id="searchContent1" name="parameter" value="${parameter}" style="width:80%;height:29px;border:1px solid darkgrey;border-left:none;float:left;">
										
									</div>
									<div class="select-button" style="float:left">
											<input type="button" value="搜索" class="bToggle" onclick="INSearch()">
									</div>
								</div> 
						   </form> 
			    		</div>
			    	</div>		
				</div>
			<div class="flex-right-con">

			
			<div style="height:100px;width:100%;position:relative;">
				<div class="changeBox" style="display:inline-block;position:relative;margin-top:50px;margin-left:-1px;z-index:3;background:url(image/bg11.png);width:533px;height:54px;line-height:50px;font-size:16px;">
					<span class="Domestic" style="display:inline-block;position:absolute;top:0;left:0;width:183px;height:46px;display:block;text-align:center;cursor:pointer;">表格数据</span>
					<span class="ExitOrEn" style="display:inline-block;position:absolute;top:0;left:175px;width:183px;height:46px;cursor:pointer;display:block;text-align:center;">城市数据</span>
					<span class="staffData" style="display:inline-block;position:absolute;top:0;left:350px;width:183px;height:46px;cursor:pointer;display:block;text-align:center;">员工数据</span>
				</div>
				<div class="info-rili reloadDiv" style="display:none;">
					<div style="position: absolute;top: -20px;height: 20px;">
						<label for="dateRange"><input type="radio" name="date_sel" id="dateRange" value="dateRange">时间段选择</label>&nbsp;
						<label for="dateWeek"><input type="radio" name="date_sel" id="dateWeek" value="dateWeek">按周选择</label>
					</div>
					<!-- <div style="display:inline-block;height:14px;margin-top:18px;margin-right:3px;line-height:14px;"><span class="aa1">统计分布</span>&nbsp;/&nbsp;<span class="aa2">城市分布</span>&nbsp;/&nbsp;<span class="aa3">距离统计</span>
					</div> -->
					<div class="dateWeek_div" style="position:relative;margin-top: 9px;">
						<input type="text" id="week_sel" class="form-control">
					</div>
					<div class="dateRange_div" style="position:relative;margin-top: 9px;">
						<input type="text" id="config-demo" class="form-control">
            			<i></i>
					</div>
					<span class="tongji_btn">刷新</span>
				</div>
			</div>	
			
				<!-- 员工行程表格 -->
				<div id="engineer-schedule-container" style="background:#fff;">
			        <div class="clearfix" style="width:100%;max-width:100%">
			            <div class="tc" style="margin: 20px 100px 15px;">工程师行程安排表</div>
			        </div>
			         <div class="choose" style="margin-top: -25px;margin-bottom:20px;margin-left:20px">
			            <input type="button" value="添加" class="bToggle" onclick="AddContract()">
			       <%--  <span>${authority}</span>  --%>
			            <c:if test="${authority}">
			           		 <input type="button" value="删除" class="bToggle deleteInfo">
			           	</c:if> 
						
			        </div>	
			        <table border="1" cellspacing="0" cellspadding="0" id="table1" style="width:96.5%;max-width:96.5% ;margin-left:2%;min-width: 760px;">
			            <tr style="width:100%;background:#bfbfbf" class="tbody_title">
			                <td class="xiugai1" style="width:5%;">序号</td>
			                <td style="width:8.3%;">行程日期</td>
			                <td style="width:8.3%;">员工姓名</td>
			                <td style="width:13.9%;max-width: 125px">客户单位</td>
			                <td style="width:14.4%;max-width: 130px;">服务事项</td>
			                <td style="width:8.3%;">交通工具</td>
			                <td class="hangban" style="display:none">车牌/车次/航班</td>
			                <td style="width:8.3%">出发地</td>
			                <td style="width:8.3%">目的地</td>
			                <td style="width:8.3%;">出发时间</td>
			                <td style="width:8.3%;">到达时间</td>
			                <td class="jiudian" style="display:none">酒店信息</td>
			                <td style="width:8.3%">差旅费</td>
			                <td style="display: none"></td>
			                <td style="display: none"></td>
			            </tr>
			            <span class="engineer" style="display:none">${engineer}</span>
			            <c:forEach var="orderInfo" items="${schedules}" varStatus="status">
			                <c:if test="${status.index>0}">
			                    <tr class="tbody_tr" sytle="background:#fff">
			                        <td class="xiugai" style="color:#4e4dad;width:5%" value="${orderInfo['ID']}">${status.index+(currentPage-1)*10}</td>
			                        <td title="${orderInfo['Date']}" class="DateTime" style="width:8.3%;cursor:default">${orderInfo['Date']}</td>
			                        <td class="Name td_Name" style="width:8.3%;">${orderInfo['Name']}</td>
			                        <td title="${orderInfo['CustomerUnit']}" class="td_CustomerUnit" style="width:13.9%;max-width: 125px;cursor:default">${orderInfo['CustomerUnit']}</td>
			                        <td title="${orderInfo['ServiceItem']}" class="td_ServiceItem" style="width:14.4%;max-width: 130px;cursor:default">${orderInfo['ServiceItem']}</td>
			                        <td style="width:8.3%;" class="td_TransportTool">${orderInfo['TransportTool']}</td>
			                        <td class="hangban td_TrainNumber" style="display:none">${orderInfo['TrainNumber']}</td>
		 							<td style="width:8.3%" class="td_Departure">${orderInfo['Departure']}</td>  <!--8  -->
		 							<td style="width:8.3%" class="td_Destination">${orderInfo['Destination']}</td>
		 							<td style="width:8.3%;" class="td_DepartureDate">${orderInfo['DepartureDate']}</td>
		 							<td style="width:8.3%;" class="td_DestinationDate">${orderInfo['DestinationDate']}</td>
			                        <td class="jiudian td_Hotel" style="display:none">${orderInfo['Hotel']}</td>
			                        <td style="width:8.3%" class="showTripCost"></td>
			                        <td style="display: none" class="showTripCost1 td_HotelExpense">${orderInfo['HotelExpense']}</td>
			                        <td style="display: none" class="showTripCost2 td_TrafficExpense">${orderInfo['TrafficExpense']}</td>
			                    </tr>
			                </c:if>
			            </c:forEach>
			        </table>
			
					 		<c:choose>
						<c:when test="${queryType =='common'}">
							<c:set var="queryUrl"
							value="Schedule?Date=${date}&currentPage=">
							</c:set>
						</c:when>
						  <c:otherwise>
							<c:set var="queryUrl"
							value="ScheduleRoute?classify=${classify}&parameter=${parameter}&currentPage=">
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
								name="Gotojump" onclick="PageJump('${queryUrl}')"> <input
								type="button" class="bToggle" value="尾页" id="lastPage"
								name="lastPage" onclick="LastPage('${queryUrl}')">
						</div>
					</div>
			    </div>
			    
			    
			    <!--地图  -->
			   	<div class="mapBox" style="display:none;">
			   		<p>工程师出差统计分布图</p>
			   		<div class="mapContent"></div>
			   	</div>
			    <div class="trip-city" style="display:none;">
			    	<div class="trip-city-tit">
			    		<div class="trip-city-tit-left">伊欧陆公司员工出差城市分布&nbsp;&nbsp;<span></span></div>
			    		<div class="trip-city-tit-right"></div>
			    	</div>
			    	<div class="trip-city-body"></div>
			    </div>

				<div class="trip-cost-calc" style="display:none;">
			    	<div class="trip-cost-calc-tit">伊欧陆公司员工差旅费统计</div>
			    	<div class="trip-cost-calc-body"></div>
			    </div>

				<div class="trip-staff-times" style="display:none;margin-top: 0;">
			    	<div class="trip-staff-times-tit">员工出差次数排行</div>
			    	<div class="trip-staff-times-body"></div>
			    </div>

			    <div class="trip-distance" style="display:none;">
			    	<div class="trip-distance-tit">伊欧陆公司员工出差距离统计</div>
			    	<div class="trip-distance-body"></div>
			    </div>
			</div>   
		</div>
	</div>
	
	<form id="help-form" method="get" action="Schedule" style="display:none;"> 
		<input  name="Date" class="bToggle" id="All_upload" >
	</form>
	
</div>
<!-- </div> -->
<div class="MailBar_cover_color" style="display: none;"></div>
<!-- 添加工程师行程信息 -->
<div class="contract_add" style="display: none;">
	<div class="contract_title">添加工程师行程信息
		<!--有权限的人显示保存按钮  -->
	    <c:forEach var="authoritiy" items="${authorities}">
	    	<c:if test="${authoritiy== 'Personnel'}">
	    	<button type="button" class="btn btn-default contractAdd_save" id="add_submit" title="保存" aria-label="Left Align">
	    		<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
	    	</button>
	    	</c:if>
	    </c:forEach>
	    <button type="button" class="btn btn-default contractAdd_close" id="add_cancel" title="取消" aria-label="Left Align">
	    	<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
	    </button>
	</div>
	<div class="contract_add_body">
		<div class="contract_add_body_in">
    		<fieldset><legend>工程师行程信息</legend>
    			<div class="container-fluid">
    				<div class="row">
    					<div class="col-md-6 col-lg-6">
    						<div class="form-horizontal">
    						    <!-- 一个form-group就是一个整体 -->
    						    <div class="form-group">
    						        <label for="add_info_Name" class="col-sm-3 control-label">员工姓名</label>
    						        <div class="col-sm-9">
    						            <select name="staffName_sel" id="add_info_Name" class="form-control"></select>
    						        </div>
    						    </div>
    						    <div class="form-group">
    						        <label for="add_info_Departure" class="col-sm-3 control-label">出发地</label>
    						        <div class="col-sm-9">
    						            <input type="text" class="form-control" id="add_info_Departure" placeholder="必选项">
    						        </div>
    						    </div>
    						    <div class="form-group">
    						        <label for="add_info_Destination" class="col-sm-3 control-label">目的地</label>
    						        <div class="col-sm-9">
    						            <input type="text" class="form-control" id="add_info_Destination" placeholder="必选项">
    						        </div>
    						    </div>
    						    <div class="form-group">
    						        <label for="add_info_DepartureDate" class="col-sm-3 control-label">出发时间</label>
    						        <div class="col-sm-9">
    						            <input type="time" class="form-control" id="add_info_DepartureDate">
    						        </div>
    						    </div>
    						    <div class="form-group">
    						        <label for="add_info_DestinationDate" class="col-sm-3 control-label">到达时间</label>
    						        <div class="col-sm-9">
    						            <input type="time" class="form-control" id="add_info_DestinationDate">
    						        </div>
    						    </div>
    						</div>
    					</div><!-- col-md-6 end -->
    					<div class="col-md-6 col-lg-6">
    						<div class="form-horizontal">
    						    <!-- 一个form-group就是一个整体 -->
    						    <div class="form-group">
    						        <label for="add_info_CustomerUnit" class="col-sm-3 control-label">客户单位</label>
    						        <div class="col-sm-9">
    						            <input type="text" class="form-control" id="add_info_CustomerUnit" placeholder="必填项">
    						        </div>
    						    </div>
    						    <div class="form-group">
    						        <label for="add_info_TransportTool" class="col-sm-3 control-label">交通方式</label>
    						        <div class="col-sm-9">
    						        	<select name="TransportTool_sel" class="form-control" id="add_info_TransportTool"></select>
    						        </div>
    						    </div>
    						    <div class="form-group">
    						        <label for="add_info_TrainNumber" class="col-sm-3 control-label" style="padding-top: 1px;line-height: 15px;">车牌/车<br>次/航班</label>
    						        <div class="col-sm-9">
    						            <input type="text" class="form-control" id="add_info_TrainNumber">
    						        </div>
    						    </div>
    						    <div class="form-group">
    						        <label for="add_info_TrafficExpense" class="col-sm-3 control-label">交通费用</label>
    						        <div class="col-sm-9">
    						        	<select name="TrafficExpense_sel" class="form-control" id="add_info_TrafficExpense"></select>
    						        </div>
    						    </div>
    						    <div class="form-group">
    						        <label for="add_info_HotelExpense" class="col-sm-3 control-label">住宿费用</label>
    						        <div class="col-sm-9">
    						        	<select name="HotelExpense_sel" class="form-control" id="add_info_HotelExpense"></select>
    						        </div>
    						    </div>
    						</div>
    					</div><!-- col-md-6 end -->
    				</div>
    			</div><!-- container-fluid end -->
    		</fieldset>
    		<fieldset><legend>服务事项</legend>
    		<div class="container-fluid">
    			<textarea id="add_info_ServiceItem" rows="3" class="form-control"></textarea>
    		</div>
    		</fieldset>
    		<fieldset><legend>酒店信息</legend>
    		<div class="container-fluid">
    			<textarea id="add_info_Hotel" rows="3" class="form-control"></textarea>
    		</div>
    		</fieldset>
		</div>
	</div>
</div>

<!-- 修改工程师行程信息 -->
<div class="contract_update" style="display: none;">
	<div class="contract_title">修改工程师行程信息
		<!--有权限的人显示保存按钮  -->
	    <c:forEach var="authoritiy" items="${authorities}">
	    	<c:if test="${authoritiy== 'Personnel'}">
	    	<button type="button" class="btn btn-default contractUpdate_save" id="update_submit" title="保存" aria-label="Left Align">
	    		<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
	    	</button>
	    	</c:if>
	    </c:forEach>
	    <button type="button" class="btn btn-default contractUpdate_close" id="update_cancel" title="取消" aria-label="Left Align">
	    	<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
	    </button>
	</div>
	<div class="contract_update_body">
		<div class="contract_update_body_in">
    		<fieldset><legend>工程师行程信息</legend>
    			<div class="container-fluid">
    				<div class="row">
    					<div class="col-md-6 col-lg-6">
    						<div class="form-horizontal">
    						    <!-- 一个form-group就是一个整体 -->
    						    <div class="form-group">
    						        <label for="update_info_Name" class="col-sm-3 control-label">员工姓名</label>
    						        <div class="col-sm-9">
    						            <select name="staffName_sel" id="update_info_Name" class="form-control"></select>
    						        </div>
    						    </div>
    						    <div class="form-group">
    						        <label for="update_info_Departure" class="col-sm-3 control-label">出发地</label>
    						        <div class="col-sm-9">
    						            <input type="text" class="form-control" id="update_info_Departure" placeholder="必选项">
    						        </div>
    						    </div>
    						    <div class="form-group">
    						        <label for="update_info_Destination" class="col-sm-3 control-label">目的地</label>
    						        <div class="col-sm-9">
    						            <input type="text" class="form-control" id="update_info_Destination" placeholder="必选项">
    						        </div>
    						    </div>
    						    <div class="form-group">
    						        <label for="update_info_DepartureDate" class="col-sm-3 control-label">出发时间</label>
    						        <div class="col-sm-9">
    						            <input type="time" class="form-control" id="update_info_DepartureDate">
    						        </div>
    						    </div>
    						    <div class="form-group">
    						        <label for="update_info_DestinationDate" class="col-sm-3 control-label">到达时间</label>
    						        <div class="col-sm-9">
    						            <input type="time" class="form-control" id="update_info_DestinationDate">
    						        </div>
    						    </div>
    						</div>
    					</div><!-- col-md-6 end -->
    					<div class="col-md-6 col-lg-6">
    						<div class="form-horizontal">
    						    <!-- 一个form-group就是一个整体 -->
    						    <div class="form-group">
    						        <label for="update_info_CustomerUnit" class="col-sm-3 control-label">客户单位</label>
    						        <div class="col-sm-9">
    						            <input type="text" class="form-control" id="update_info_CustomerUnit" placeholder="必填项">
    						        </div>
    						    </div>
    						    <div class="form-group">
    						        <label for="update_info_TransportTool" class="col-sm-3 control-label">交通方式</label>
    						        <div class="col-sm-9">
    						        	<select name="TransportTool_sel" class="form-control" id="update_info_TransportTool"></select>
    						        </div>
    						    </div>
    						    <div class="form-group">
    						        <label for="update_info_TrainNumber" class="col-sm-3 control-label" style="padding-top: 1px;line-height: 15px;">车牌/车<br>次/航班</label>
    						        <div class="col-sm-9">
    						            <input type="text" class="form-control" id="update_info_TrainNumber">
    						        </div>
    						    </div>
    						    <div class="form-group">
    						        <label for="update_info_TrafficExpense" class="col-sm-3 control-label">交通费用</label>
    						        <div class="col-sm-9">
    						        	<select name="TrafficExpense_sel" class="form-control" id="update_info_TrafficExpense"></select>
    						        </div>
    						    </div>
    						    <div class="form-group">
    						        <label for="update_info_HotelExpense" class="col-sm-3 control-label">住宿费用</label>
    						        <div class="col-sm-9">
    						        	<select name="HotelExpense_sel" class="form-control" id="update_info_HotelExpense"></select>
    						        </div>
    						    </div>
    						</div>
    					</div><!-- col-md-6 end -->
    				</div>
    			</div><!-- container-fluid end -->
    		</fieldset>
    		<fieldset><legend>服务事项</legend>
    		<div class="container-fluid">
    			<textarea id="update_info_ServiceItem" rows="3" class="form-control"></textarea>
    		</div>
    		</fieldset>
    		<fieldset><legend>酒店信息</legend>
    		<div class="container-fluid">
    			<textarea id="update_info_Hotel" rows="3" class="form-control"></textarea>
    		</div>
    		</fieldset>
		</div>
	</div>
</div>

<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZHnpqUp55qaTvkE27KGjUBYLcUfsaRTV"></script>
<!-- <script src="js/libs/echarts-4.1.0.min.js"></script> -->
<!-- <script src="js/libs/echarts-all.js"></script> -->
</body>
<script src="plugins/echarts/map_can/echarts-all-min.js"></script>
<script src="js/libs/integrationLibs/bootstrap-moment-daterangepicker-abd2349e95.min.js"></script>
<script src="js/libs/scrolltopcontrol.js"></script>
<!-- delete -->
<!-- <script src="js/fullcalendar.min.js"></script> -->
<!-- <script src="js/libs/kalendae.standalone_zh.js" type="text/javascript" charset="utf-8"></script> -->
<!-- <script src="plugins/awesomplete/awesomplete.min.js"></script> -->
<!-- <script src="js/msgbox.js"></script> -->
<!-- <script src="js/schedule.js"></script> -->
</html>