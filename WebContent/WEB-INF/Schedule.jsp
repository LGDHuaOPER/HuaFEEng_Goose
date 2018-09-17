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
<link rel="stylesheet" href="./css/libs/bootstrap.min.css">
<link rel="stylesheet" type="text/css" media="all" href="./css/libs/daterangepicker.css" />
<!-- <link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css"> -->
<!-- <link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" /> -->
<link rel="stylesheet" href="css/libs/kalendae_pc.css" type="text/css" charset="utf-8">
<link rel="stylesheet" type="text/css" href="css/schedule.css">
<link rel="stylesheet" href="css/fullcalendar.css"/>
<link rel="stylesheet" href="css/fullcalendar.print.css"/>
<!-- <link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet"> -->

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

	#main {
		 height:450px;
		 width:96%;
		 margin: 7px 2%;
	}

	.trip-city, .trip-distance, .trip-cost-calc, .trip-staff-times {
    width: 100%;
    height: 500px;
    background: rgba(255,255,255,0.9);
    margin-top: 20px;
	}

	#main2, #main3, #echartContainer1, #echartContainer2 {
		height:420px;
		width:96%;
		margin: 10px 2%;
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
<div class="cover-all" style="position:absolute;top:0;left:0;width:100vw;height:100vh;filter:Alpha(opacity=99);background:rgba(250,250,250,0.99);opacity:0.99;z-index:888;"></div>
<div class="cover-all-img" style="width:200px;height:25px;position:absolute;top:40vh;left:calc(50vw - 100px);z-index:889;">
	<img src="image/response-loading.gif" alt="正在加载..." width="190" height="14" style="display:block">
</div>
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
			                       
			                        <td title="${orderInfo['Date']}" class="DateTime" style="width:8.3%;cursor:default">${orderInfo['Date']}</td>     <!--2  -->
			                        <td class="Name" style="width:8.3%;">${orderInfo['Name']}</td>
			                        <td title="${orderInfo['CustomerUnit']}" style="width:13.9%;max-width: 125px;cursor:default">${orderInfo['CustomerUnit']}</td>
			                        <td title="${orderInfo['ServiceItem']}"  style="width:14.4%;max-width: 130px;cursor:default">${orderInfo['ServiceItem']}</td>
			                        <td style="width:8.3%;">${orderInfo['TransportTool']}</td>
			                        <td  class="hangban" style="display:none">${orderInfo['TrainNumber']}</td>
		 							<td style="width:8.3%" >${orderInfo['Departure']}</td>  <!--8  -->
		 							<td style="width:8.3%">${orderInfo['Destination']}</td>
		 							<td style="width:8.3%;">${orderInfo['DepartureDate']}</td>
		 							<td style="width:8.3%;">${orderInfo['DestinationDate']}</td>
			                        <td  class="jiudian" style="display:none">${orderInfo['Hotel']}</td>
			                        <td style="width:8.3%" class="showTripCost"></td>
			                        <td style="display: none" class="showTripCost1">${orderInfo['HotelExpense']}</td>
			                        <td style="display: none" class="showTripCost2">${orderInfo['TrafficExpense']}</td>
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
			   	<div class="mapBox" style="display:none;" >
			   		<p style="padding-top:10px;height:30px;line-height:26px;font-size:18px;color:#00aeef;text-align:left;text-indent:40px;">工程师出差统计分布图</p>
			   		
			   		<div class="mapContent" style="width:100%;height:470px;margin-top: -10px"></div>
			   	</div>
			    <div class="trip-city" style="display:none;">
			    	<div class="trip-city-tit" style="display:flex;margin-top:20px;padding-top:10px;height:40px;">
			    		<div class="trip-city-tit-left" style="flex:3;text-indent: 40px;color: #00aeef;font-size: 18px">伊欧陆公司员工出差城市分布&nbsp;&nbsp;<span></span></div>
			    		<div class="trip-city-tit-right" style="flex:1;padding-right: 1%"></div>
			    		
			    	</div>
			    	<div class="trip-city-body"></div>
			    </div>

				<div class="trip-cost-calc" style="display:none;">
			    	<div class="trip-cost-calc-tit" style="padding-top:20px;height:40px;text-indent: 40px;color: #00aeef;font-size: 18px;">伊欧陆公司员工差旅费统计</div>
			    	<div class="trip-cost-calc-body"></div>
			    </div>

				<div class="trip-staff-times" style="display:none;margin-top: 0;">
			    	<div class="trip-staff-times-tit" style="padding-top:20px;height:40px;text-indent: 40px;color: #00aeef;font-size: 18px;">员工出差次数排行</div>
			    	<div class="trip-staff-times-body"></div>
			    </div>

			    <div class="trip-distance" style="display:none;">
			    	<div class="trip-distance-tit" style="padding-top:20px;height:40px;text-indent: 40px;color: #00aeef;font-size: 18px;">伊欧陆公司员工出差距离统计</div>
			    	<div class="trip-distance-body"></div>
			    </div>

			</div>   
		</div>
	</div>
	
	<form id="help-form" method="get" action="Schedule" style="display:none;" > 
		<input  name="Date" class="bToggle" id="All_upload" >
	</form>
	
</div>
<!-- </div> -->
<div class="MailBar_cover_color" style="display: none;"></div>
<!-- 添加工程师行程信息 -->
<div class="contract_add" style="display: none;">
    <div class="contract_title">添加工程师行程信息</div>
    <!--有权限的人显示保存按钮  -->
    <c:forEach var="authoritiy" items="${authorities}">
                    	<c:if test="${authoritiy== 'Personnel'}">
                    	<div class="contractAdd_save" id="add_submit">保存</div>
                    	</c:if>
    </c:forEach>
   <!--  <div class="contractAdd_save" id="add_submit">保存</div> -->
    <div class="contractAdd_close" id="add_cancel">取消</div>
    <div class="basic_info">
        <div class="table_title">工程师行程信息<span style="margin-left: 30%;height：20px;line-height: 20px;"><button class="btn btn-danger mybtn1" style="height：20px;line-height: 20px;display: none;">关闭地图</button></span><input type="hidden" id="tempDistance1" value=""></div>
        <!-- 修改添加 -->
		<div class="m-add-schedule">
			<div class="m-add-schedule-l">
				<span style="float:left;margin-right:8%">员工姓名</span>
		        <span class="engineer"><input style="width:62%;height:25px;margin-bottom:50px;border-radius:3px;" type="text" name="Name" value="" id="Name"></span>
		        <br>
		        <span style="float:left;margin-right:8%;">客户单位</span>
		        <span><textarea rows="6" cols="23"  name="CustomerUnit" value="" id="CustomerUnit"></textarea></span>
			</div>
			<div class="m-add-schedule-m">
        		<span style="margin-right:19%;margin-bottom:30px;margin-left:4%;">出发地</span>
        		<div id="city_1"  name="StartAdd" style="display:inline-block;margin-bottom:30px;">
					<input class="startArea" type="text" style="width:130px;">
				</div>
                <br>
        		<span style="margin-right:19%;margin-bottom:30px;margin-left:4%">目的地</span>
        		<div id="city_2"  name="EndAdd" style="display:inline-block;margin-bottom:30px;">
					<input class="arriveArea" type="text" style="width:130px;">
				</div>
                <br>
        		<span style="margin-right:15%;margin-bottom:30px;margin-left:4%">出发时间</span>
                <span><input style="width:44%;height:25px;margin-bottom:30px;" type="time" name="StartTime" placeholder="00:00" id="StartTime"></span>
                <br>
        		<span style="margin-right:15%;margin-bottom:30px;margin-left:4%">到达时间</span>
                <span><input style="width:44%;height:25px;margin-bottom:30px;" type="time" name="EndTime" placeholder="00:00" id="EndTime"></span>
                <br>
        		<span style="margin-right:15%;margin-bottom:30px;margin-left:4%">交通方式</span>
                <span>
                	<select style="width:45%;height:25px;margin-bottom:30px;" name="Traffic1"  id="Traffic1">
                	  <option text="请选择" value="0">请选择</option>
					  <option  text="飞机" value="1">飞机</option>
					  <option  text="铁路" value="2">铁路</option>
					  <option  text="自驾" value="3">自驾</option>
					  <option  text="打车" value="4">打车</option>
					  <option  text="其他" value="5">其他</option>
					  <!-- <option  text="步行">步行</option>
					  <option  text="其他">其他</option> -->
					</select>
                </span>
                <br>
        		<span style="margin-right:2%;margin-bottom:30px;margin-left:4%">车牌/车次/航班</span>
                <span><input style="width:44%;height:25px;margin-bottom:30px;" type="text" name="TrainNO" value="" id="TrainNO"></span>
                <br>
			</div>
			<div class="m-add-schedule-r">
				 <span style="float:left;margin-right:4%;margin-left:4%;">服务事项</span>
		         <span><textarea  style="margin-bottom:20px;" rows="4" cols="23"  name="Service" value="" id="Service"></textarea></span>
		         <br>
				 <span style="float:left;margin-right:4%;margin-left:4%;">酒店信息</span>
		         <span><textarea rows="4" cols="23"  name="HotelMessage" value="" id="HotelMessage"></textarea></span>
		         <div style="width:100%;height:20px;line-height:20px;font-size:14px;margin-top:25px;margin-left:4%;">
		         	住宿费用&nbsp;&nbsp;&nbsp;<select name="stayCost" id="stayCost" style="width:135px;text-align:center;padding-bottom:3px;">
		         		<option value="">请选择住宿费用</option>
		         	</select>&nbsp;<span style="font-size: 12px;color: red">*</span>
		         </div>
		         <div style="width:100%;height:20px;line-height:20px;font-size:14px;margin-top:25px;margin-left:4%;">
		         	交通费用&nbsp;&nbsp;&nbsp;<select name="travelCost" id="travelCost" style="width:135px;text-align:center;padding-bottom:3px;"><option value="">请选择交通方式</option></select>&nbsp;<span style="font-size: 12px;color: red">*</span>
		         </div>
			</div>
		</div>
    </div>
</div>

<!-- 修改工程师信息 -->
<div class="contract_update" style="display: none;">
    <div class="contract_title">修改工程师行程信息</div>
    <!--有权限的人显示保存按钮  -->
    <c:forEach var="authoritiy" items="${authorities }">
                    	<c:if test="${authoritiy== 'Personnel'}">
                    	<div class="contractUpdate_save" id="update_submit">保存</div>
                    	</c:if>
    </c:forEach>
    <!-- <div class="contractUpdate_save" id="update_submit">保存</div> -->
    <div class="contractUpdate_close" id="update_cancel">取消</div>
    <div class="basic_info">
        <div class="table_title">工程师行程信息<span style="margin-left: 30%;height：20px;line-height: 20px;"><button class="btn btn-danger mybtn2" style="height：20px;line-height: 20px;display: none;">关闭地图</button></span><input type="hidden" id="tempDistance2" value=""></div>
        <!-- 修改添加 -->
		<div class="m-add-schedule">
        	<div class="m-add-schedule-l">
				<span style="float:left;margin-right:8%">员工姓名</span>
		        <span class="engineer"><input style="width:62%;height:25px;margin-bottom:50px;border-radius:3px;"type="text" name="Name" value="" id="Name"></span>
		        <br>
		        <span style="float:left;margin-right:8%;">客户单位</span>
		        <span><textarea rows="6" cols="23"  name="CustomerUnit" value="" id="CustomerUnit"></textarea></span>
        	</div>
        	<div class="m-add-schedule-m">
        		<span style="margin-right:19%;margin-bottom:30px;margin-left:4%;">出发地</span>
        		<div id="city_3"  name="StartAdd" style="display:inline-block;margin-bottom:30px;">
					<input class="startArea2" type="text" style="width:130px;">
				</div>
                <br>
        		<span style="margin-right:19%;margin-bottom:30px;margin-left:4%">目的地</span>
        		<div id="city_4"  name="EndAdd" style="display:inline-block;margin-bottom:30px;">
					<input class="arriveArea2" type="text" style="width:130px;">
				</div>
                <br>
        		<span style="margin-right:15%;margin-bottom:30px;margin-left:4%;">出发时间</span>
                <span><input style="width:44%;height:25px;margin-bottom:30px;" type="time" name="StartTime" placeholder="00:00" id="StartTime"></span>
                <br>
        		<span style="margin-right:15%;margin-bottom:30px;margin-left:4%">到达时间</span>
                <span><input style="width:44%;height:25px;margin-bottom:30px;" type="time" name="EndTime" placeholder="00:00" id="EndTime"></span>
                <br>
        		<span style="margin-right:15%;margin-bottom:30px;margin-left:4%">交通方式</span>
                <span>
                	<select style="width:45%;height:25px;margin-bottom:30px;" name="Traffic"  id="Traffic">
                	  <option text="请选择" value="0">请选择</option>
					  <option  text="飞机" value="1">飞机</option>
					  <option  text="铁路" value="2">铁路</option>
					  <option  text="自驾" value="3">自驾</option>
					  <option  text="打车" value="4">打车</option>
					  <option  text="其他" value="5">其他</option>
					</select>
				</span>
                <br>
        		<span style="margin-right:2%;margin-bottom:30px;margin-left:4%">车牌/车次/航班</span>
                <span><input style="width:44%;height:25px;margin-bottom:30px;" type="text" name="TrainNO" value="" id="TrainNO"></span>
                <br>
        	</div>
        	<div class="m-add-schedule-r">
				 <span style="float:left;margin-right:4%;margin-left:4%;">服务事项</span>
		         <span><textarea  style="margin-bottom:20px;" rows="4" cols="23"  name="Service" value="" id="Service"></textarea></span>
		         <br>
				 <span style="float:left;margin-right:4%;margin-left:4%;">酒店信息</span>
		         <span><textarea rows="4" cols="23"  name="HotelMessage" value="" id="HotelMessage"></textarea></span>
		         <div style="width:100%;height:20px;line-height:20px;font-size:14px;margin-top:25px;margin-left:4%;">
		         	住宿费用&nbsp;&nbsp;&nbsp;<select name="stayCost2" id="stayCost2" style="width:135px;">
		         		<option value="">请选择住宿费用</option>
		         	</select>&nbsp;<span style="font-size: 12px;color: red">*</span>
		         </div>
		         <div style="width:100%;height:20px;line-height:20px;font-size:14px;margin-top:25px;margin-left:4%;">
		         	交通费用&nbsp;&nbsp;&nbsp;<select name="travelCost2" id="travelCost2" style="width:135px;"><option value="">请选择交通方式</option></select>&nbsp;<span style="font-size: 12px;color: red">*</span>
		         </div>
        	</div>
        </div>

    </div>
   <!--  <div class="edit_btn">
        <input type="button" value="保存" class="bToggle" id="update_submit">
        <input type="button" value="取消" class="bToggle" id="update_cancel">
    </div> -->
</div>

<div id="allmap" style="width:100%;height:65%;display:none;position:absolute;left:0;bottom:0;overflow: hidden;font-family: '微软雅黑';z-index: 22;"></div>
<!-- <button id="top" display="none">top</button> -->
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZHnpqUp55qaTvkE27KGjUBYLcUfsaRTV"></script>
<!-- <script src="js/jquery-1.12.3.min.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/fullcalendar.min.js"></script>
<!-- <script src="js/libs/echarts-4.1.0.min.js"></script> -->
<!-- <script src="js/libs/echarts-all.js"></script> -->
<script src="plugins/echarts/map_can/echarts-all-min.js"></script>
</body>
<!-- <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script> -->
<script type="text/javascript" src="js/libs/bootstrap.min.js"></script>
<script type="text/javascript" src="js/libs/moment.min.js"></script>
<script type="text/javascript" src="js/libs/daterangepicker.js"></script>
<script src="js/libs/kalendae.standalone_zh.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="js/libs/scrolltopcontrol.js"></script>
<!-- <script type="text/javascript" src="js/global/myFunction.js"></script> -->
<script type="text/javascript" src="js/global/responseLoading.js"></script>
<script src="js/msgbox.js"></script>
<!-- <script src="js/msgbox_unload.js"></script> -->
<script src="js/schedule.js"></script>
<!-- <script src="js/select.js"></script> -->
<script>
$.Response_Load.Before("系统提示","页面加载中......",100);
var k4 = new Kalendae.Input('week_sel', {
    months: 1,
    mode: 'week',
    format: "YYYY-MM-DD",
    rangeDelimiter: "至",
    side:"bottom",
	disableDate:true,
    selected: Kalendae.moment(),
    subscribe: {
        'change': function (date) {
            // console.log(this.getSelected());
            $(".k-btn-close").trigger("click");
        }}
});
$(document).on("change","input[name='date_sel']",function(){
	if($("input[name='date_sel']:checked").val()=="dateWeek"){
		$(".dateRange_div").css({"display":"none"});
		$(".dateWeek_div").css({"display":"inline-block"});
	}else if($("input[name='date_sel']:checked").val()=="dateRange"){
		$(".dateWeek_div").css({"display":"none"});
		$(".dateRange_div").css({"display":"inline-block"});
	}
});

var schedule_startTime;
var schedule_endTime;

function switchDate(){
	if($("input[name='date_sel']:checked").val()=="dateRange"){
		var start_time0 = $(".calendar.left .input-mini").val();
		var end_time0 = $(".calendar.right .input-mini").val();
		if(start_time0 == "" || start_time0 == null){
			schedule_startTime = new Date().getFullYear()+'-01-01';
		}else{
			schedule_startTime = start_time0;
		}
		if(end_time0 == "" || end_time0 == null){
			var D0 = new Date();
			var yy0=D0.getFullYear(); 
			var mm0=D0.getMonth()+1; 
			if (mm0<10){
			mm0 = "0"+mm0;
			}
			var dd0=D0.getDate();
			if (dd0 < 10){
				dd0 = "0" + dd0;
			}
			schedule_endTime = yy0+'-'+mm0+'-'+dd0;
		}else{
			schedule_endTime = end_time0;
		}
	}else if($("input[name='date_sel']:checked").val()=="dateWeek"){
		if($("#week_sel").val().indexOf("至")>-1){
			schedule_startTime = $("#week_sel").val().split("至")[0];
			schedule_endTime = $("#week_sel").val().split("至")[1];
		}else if($("#week_sel").val().indexOf("至") == -1){
			schedule_startTime = $("#week_sel").val().trim();
			var wD = new Date();
			var wy = wD.getFullYear(); 
			var wm = wD.getMonth()+1; 
			if (wm<10){
			wm = "0"+wm;
			}
			var wd = wD.getDate();
			if (wd < 10){
				wd = "0" + wd;
			}
			schedule_endTime = wy+'-'+wm+'-'+wd;
		}
	}
}

// $(document).scrollTop(0);
// $(window).scrollTop(0);
var tempDistance ; 
$(function(){

	// 计算修改了一个出发地或目的地
	function alterDistance1(address1,address2){
	  var url1 = 'https://api.map.baidu.com/geocoder/v2/?ak=ZHnpqUp55qaTvkE27KGjUBYLcUfsaRTV&output=json&address=' + encodeURIComponent(address1);
	  var url2 = 'https://api.map.baidu.com/geocoder/v2/?ak=ZHnpqUp55qaTvkE27KGjUBYLcUfsaRTV&output=json&address=' + encodeURIComponent(address2);
	        //根据地点名称获取经纬度信息
	          var distance;
	          $.ajax({
	              type: "POST",
	              url: url1,
	              dataType: "JSONP",
	              success: function (data) {
	                console.log(data);
	              if (parseInt(data.status) == 0) {
	                var lng1= data.result.location.lng;
	                var lat1= data.result.location.lat;
	                console.log(lng1)
	                console.log(lat1)
	                $.ajax({
	                   type: "POST",
	                   url: url2,
	                   dataType: "JSONP",
	                   success: function (data) {
	                    if (parseInt(data.status) == 0) {
	                      var lng2= data.result.location.lng;
	                      var lat2= data.result.location.lat;
	                    }
	                    var map = new BMap.Map();
	                var pointA = new BMap.Point(lng1,lat1);  
	          var pointB = new BMap.Point(lng2,lat2);  
	          distance = (map.getDistance(pointA,pointB)/1000).toFixed(2);
	          // $("#allmap").text(distance);
	          // return distance;
	          $("#tempDistance2").val(distance);
	                   }
	              });
	              }
	            } 
	        });
	}

	function initMap(box,dom,item,saveDis){
		// 百度地图API功能  
		var map = new BMap.Map(box, {enableMapClick: false}); //创建百度地图实例，这里的allmap是地图容器的id  
		var point = new BMap.Point(120.737582, 31.261794); //创建一个点对象，这里的参数是地图上的经纬度  
		var marker = new BMap.Marker(point); // 创建标注
		map.centerAndZoom(point, 12); //这里是将地图的中心移动到我们刚才创建的点；这里的12是地图的缩放界别；数值越大，地图看的越细  
		map.enableDragging();
		map.enableScrollWheelZoom();
		map.clearOverlays();
		var geoc = new BMap.Geocoder();
		//切换城市
		var size = new BMap.Size(10, 20);
		map.addControl(new BMap.CityListControl({
			anchor: BMAP_ANCHOR_TOP_LEFT,
			offset: size,
		}));
		//点击地图选址    出现标注点
		map.addEventListener("click", showInfo);
		function showInfo(e) {
			x = e.point.lng; //获取鼠标当前点击的经纬度
			y = e.point.lat;
			if(x != "" && y != "") {
				map.removeOverlay(marker);
				var point1 = new BMap.Point(x, y);
				map.centerAndZoom(point1);
				marker = new BMap.Marker(point1); // 创建新的标注
				map.addOverlay(marker); //将标注添加到地图上
			}
			var pt = e.point;
			console.log(pt);
			if($(item).attr("lngAndlat")){
				var pt0 = {};
				var lng0 = $(item).attr("lngAndlat").split(",")[0];
				// alert(typeof lng0);
				lng0 = lng0*1;
				// alert(typeof lng0);
				var lat0 = $(item).attr("lngAndlat").split(",")[1];
				lat0 = lat0*1;
				pt0.lng = lng0;
				pt0.lat = lat0;
				console.log("pt0的坐标");
				console.log(pt0);
				tempDistance = (map.getDistance(pt,pt0)/1000).toFixed(2);
				// alert(tempDistance);
				$(saveDis).val(tempDistance);
			}
			geoc.getLocation(pt, function(rs) {
				var addComp = rs.addressComponents;
//				var text = addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber;
				var text = addComp.province + "," + addComp.city ;
				// dom.text(text);    // 将选点的地址信息放置元素中
				dom.val(text);    // 将选点的地址信息放置元素中
				dom.removeAttr("lngAndlat"); // 存储坐标点信息
				dom.attr("lngAndlat", pt.lng + "," + pt.lat);

				if($(item).val() && ($(item).val().indexOf(",") == -1)){
					var address1 = $(item).val().trim();
					var address2 = addComp.city;
					alterDistance1(address1,address2);
				}
			});
		}
	}

	$(document).on("click",".startArea",function(){
		$(".mybtn1").fadeIn(100);
		$("#allmap").fadeIn(100);
		initMap("allmap",$(".startArea"),".arriveArea","#tempDistance1");
	});

	$(document).on("click",".arriveArea",function(){
		$(".mybtn1").fadeIn(100);
		$("#allmap").fadeIn(100);
		initMap("allmap",$(".arriveArea"),".startArea","#tempDistance1");
	});
	
	$(document).on("click",".startArea2",function(){
		$(".mybtn2").fadeIn(100);
		$("#allmap").fadeIn(100);
		initMap("allmap",$(".startArea2"),".arriveArea2","#tempDistance2");
	});

	$(document).on("click",".arriveArea2",function(){
		$(".mybtn2").fadeIn(100);
		$("#allmap").fadeIn(100);
		initMap("allmap",$(".arriveArea2"),".startArea2","#tempDistance2");
	});

	//	map.getDistance(pointA,pointB)/1000).toFixed(2);   //获取两地距离 pointA  pointB分别为两地坐标点
	
	$(".mybtn1").on("click",function(){
		// (map.getDistance($(".startArea").attr("lngAndlat"),$(".arriveArea").attr("lngAndlat"))/1000).toFixed(2);
		$("#allmap").fadeOut(100);
		$(".mybtn1").fadeOut(100);
	});
	$(".mybtn2").on("click",function(){
		$("#allmap").fadeOut(100);
		$(".mybtn2").fadeOut(100);
	});

	function calcExpense(){
		if($(".tbody_tr").length){
			$(".tbody_tr").each(function(){
				var a = $(this).find(".showTripCost1").text();
				var b = $(this).find(".showTripCost2").text();
				if(a==""||a=="--"||a==null||a==undefined){
					a=0;
				}
				if(b==""||b=="--"||b==null||b==undefined){
					b=0;
				}
				$(this).find(".showTripCost").text(a*1+b*1);
			});
		}
	}
	calcExpense();
});

var city = {北京:["东城区","西城区","崇文区","宣武区","朝阳区","海淀区","丰台区","石景山区","房山区","通州区","顺义区","昌平区","大兴区","怀柔区","平谷区","门头沟区","密云县","延庆县"],天津:["和平区","河东区","河西区","南开区","河北区","红桥区","东丽区","西青区","北辰区","津南区","武清区","宝坻区","滨海新区","静海县","宁河县","蓟县"],上海:["黄浦区","卢湾区","徐汇区","长宁区","静安区","普陀区","闸北区","虹口区","杨浦区","闵行区","宝山区","嘉定区","浦东新区","金山区","松江区","青浦区","奉贤区","崇明县"],重庆:["渝中区","大渡口区","江北区","南岸区","北碚区","渝北区","巴南区","长寿区","双桥区","沙坪坝区","万盛区","万州区","涪陵区","黔江区","永川区","合川区","江津区","九龙坡区","南川区","綦江县","潼南县","荣昌县","璧山县","大足县","铜梁县","梁平县","开县","忠县","城口县","垫江县","武隆县","丰都县","奉节县","云阳县","巫溪县","巫山县"],河北:["石家庄","唐山","秦皇岛","邯郸","邢台","保定","张家口","承德","沧州","廊坊","衡水"],山西:["太原","大同","阳泉","长治","晋城","朔州","晋中","运城","忻州","临汾","吕梁"],辽宁:["沈阳","大连","鞍山","抚顺","本溪","丹东","锦州","营口","阜新","辽阳","盘锦","铁岭","朝阳","葫芦岛"],吉林:["长春","吉林","四平","辽源","通化","白山","松原","白城","延边朝鲜族自治州"],黑龙江:["哈尔滨","齐齐哈尔","鹤岗","双鸭山","鸡西","大庆","伊春","牡丹江","佳木斯","七台河","黑河","绥化","大兴安岭"],江苏:["南京","苏州","无锡","常州","镇江","南通","泰州","扬州","盐城","连云港","徐州","淮安","宿迁"],浙江:["杭州","宁波","温州","嘉兴","湖州","绍兴","金华","衢州","舟山","台州","丽水"],安徽:["合肥","芜湖","蚌埠","淮南","马鞍山","淮北","铜陵","安庆","黄山","滁州","阜阳","宿州","巢湖","六安","亳州","池州","宣城"],福建:["福州","厦门","莆田","三明","泉州","漳州","南平","龙岩","宁德"],江西:["南昌","景德镇","萍乡","九江","新余","鹰潭","赣州","吉安","宜春","抚州","上饶"],山东:["济南","青岛","淄博","枣庄","东营","烟台","潍坊","济宁","泰安","威海","日照","莱芜","临沂","德州","聊城","滨州","菏泽"],河南:["郑州","开封","洛阳","平顶山","安阳","鹤壁","新乡","焦作","濮阳","许昌","漯河","三门峡","南阳","商丘","信阳","周口","驻马店"],湖北:["武汉","黄石","十堰","荆州","宜昌","襄樊","鄂州","荆门","孝感","黄冈","咸宁","随州","恩施"],湖南:["长沙","株洲","湘潭","衡阳","邵阳","岳阳","常德","张家界","益阳","郴州","永州","怀化","娄底","湘西"],广东:["广州","深圳","珠海","汕头","韶关","佛山","江门","湛江","茂名","肇庆","惠州","梅州","汕尾","河源","阳江","清远","东莞","中山","潮州","揭阳","云浮"],海南:["海口","三亚"],四川:["成都","自贡","攀枝花","泸州","德阳","绵阳","广元","遂宁","内江","乐山","南充","眉山","宜宾","广安","达州","雅安","巴中","资阳","阿坝","甘孜","凉山"],贵州:["贵阳","六盘水","遵义","安顺","铜仁","毕节","黔西南","黔东南","黔南"],云南:["昆明","曲靖","玉溪","保山","昭通","丽江","普洱","临沧","德宏","怒江","迪庆","大理","楚雄","红河","文山","西双版纳"],陕西:["西安","铜川","宝鸡","咸阳","渭南","延安","汉中","榆林","安康","商洛"],甘肃:["兰州","嘉峪关","金昌","白银","天水","武威","酒泉","张掖","庆阳","平凉","定西","陇南","临夏","甘南"],青海:["西宁","海东","海北","海南","黄南","果洛","玉树","海西"],内蒙古:["呼和浩特","包头","乌海","赤峰","通辽","鄂尔多斯","呼伦贝尔","巴彦淖尔","乌兰察布","锡林郭勒盟","兴安盟","阿拉善盟"],广西:["南宁","柳州","桂林","梧州","北海","防城港","钦州","贵港","玉林","百色","贺州","河池","来宾","崇左"],西藏:["拉萨","那曲","昌都","林芝","山南","日喀则","阿里"],宁夏:["银川","石嘴山","吴忠","固原","中卫"],新疆:["乌鲁木齐","克拉玛依","吐鲁番","哈密","和田","阿克苏","喀什","克孜勒苏","巴音郭楞","昌吉","博尔塔拉","伊犁","塔城","阿勒泰"],香港:["香港岛","九龙东","九龙西","新界东","新界西"],澳门:["澳门半岛","离岛"],台湾:["台北","高雄","基隆","新竹","台中","嘉义","台南"]};
function province(a){
	for(var i in city){
	   	if(city[i].indexOf(a) > -1 ){
			return i;
	   	}
    }
}

var myChart1,myChart2,myChart3,myChart4,myChart5;


// 函数封装
function showMap(start_time,end_time){
	$.ajax({
		type: 'get',
	    url: 'ScheduleProvince',
	    data:{
	    	StartTime : start_time,
	    	EndTime : end_time,
	    	Name:""
	    },
	    success:function(data){
	    	console.log("出差省份分布");
	    	console.log(data);
	    	console.log(typeof data);
	    	data = JSON.parse(data);
	    	console.log(data);
	    	console.log(typeof(data));
	    	var newArrProvince = [];
	    	var newArrNo = [];
	    	if(data.length>1){
	    		for(var i = 1;i<data.length-1;i++){
	    			if(data[i].province!=""&&data[i].province!="--"){
	    				newArrProvince.push(data[i].province);
	    			}
	    			if(data[i].times!=""&&data[i].times!="--"){
	    				newArrNo.push(Number(data[i].times));
	    			}
	    		}
	    		console.log(newArrProvince);
	    		console.log(newArrNo);
	    		$(".mapContent").html('');
	    		var str = '<div id="main"></div>';
	    		$(".mapContent").append(str);
	    		provMap(newArrProvince,newArrNo);
	    	}else{
	    		$.MsgBox_Unload.Alert("提示","该时间段无出差省份记录");
	    	}
	    	
	    },
	    error:function(){
	    	alert("网络错误")
	    }
	});
	
	
// $.ajax({
// 	        type: 'get',
// 	        url: 'ScheduleByTime',
// 	        dataType : 'json', 
// 	        data:{
// 	         	StartTime : start_time,
// 	            EndTime : end_time,
// 	            Name:""
// 	            },
// 	         success: function(result){
// 	            console.log(222222222222)
// 	            console.log(result);
	            		    	
// 		    	/* {"常州市":"1","苏州市":"8","徐州市":"1","大连市":"15","北京市":"1","厦门市":"13","盐城市":"1","南京市":"1"} */
	            		    	
// 		    	console.log("heheheheheh")
// 		    	console.log(result);
// 				var cityArr = [];
// 				var numberArr = [];
// 				var allcityArr = [];
// 				var allnumberArr = [];
// 	            for(var k in result){
// 		    		console.log('123'+k+result[k])
// 		    		//如果k 为空或者异常，去掉数组中的该项
// 		    		if(k == ''){
// 		    			/* alert(k)
// 		    			alert(result[k]) */
// 		    			delete result[k];
// 		    		}
//     		    		console.log("删除城市名为空的后")
//     		    		console.log('666'+k+result[k])
//     		    		console.log("hhh")
//         		    	console.log(result.toString)
// 		    		if(result[k] == 0 || result[k] == ''){
// 		    			delete result[k];
// 		    			console.log("再次删除城市对应的次数为空或者0的后")
// 		    			console.log('777'+k+result[k])
// 		    		}
// 		            if(k.lastIndexOf('市') == k.length-1 && k.lastIndexOf('市') != -1){
//     		    		var city = k.substring(0,k.length-1);
//     		    		var prov = province(city);
//     		    		console.log("传进来的带上市，插件要求不带处理去掉")
//     		    		console.log(prov)
//     		    		console.log(result);
//     		    		if(!prov && city != "北京" && city != "上海" && city != "重庆" && city != "天津" && city != "香港" && city != "澳门"){
//         		    		delete result[k];
//     		    		}
// 	    				var number = city + '市';
// 		            }else{
// 		    			var city = k;
// 		    			var prov = province(city);
// 		    			console.log("传进来的没有带市")
// 		    			console.log(prov)
//     		    		console.log(result);
// 		    			if(!prov && city != "北京" && city != "上海" && city != "重庆" && city != "天津" && city != "香港" && city != "澳门"){
//         		    		delete result[k];
//     		    		}
// 	    				var number = city;
// 		    		}
//     		    		console.log("最终输出的城市数组")
//     		    		console.log(result)
//     		    		console.log('888'+k+result[k])
// 	            }
	            		    	
// 	    		//对处理后的或者无需处理的result操作
// 	    		for(var k in result){
// 		    		if(k.lastIndexOf('市') == k.length-1 && k.lastIndexOf('市') != -1){
//     		    		var city = k.substring(0,k.length-1);
//     		    		var prov = province(city);
//     		    		console.log(prov)
// 	    				var number = city + '市';
// 		    		}else{
// 		    			var city = k;
// 		    			var prov = province(city);
// 		    			console.log(prov)
// 		    			console.log('999'+k+result[k])
// 	    				var number = city;
// 		    		}
	    		
// 	    		//获取省份以后，和用户点击的省份做比较，省份一样的保留city到新的数组中，保留对应的result[k]
	        		    				
// 					allcityArr.push(city);
// 					allnumberArr.push(result[number])
	        		
// 		            $(".mapContent").html('');
// 		            var str = '<div id="main"></div>';
// 	    			$(".mapContent").append(str);
// 	    			provMap(provArr);
// 		    	        BarMap(Name,allcityArr,allnumberArr);  			    	
		            		    			
// 		    	}
// 		    },
// 		    error:function(){}
// 	    });

}

//地图模块
function provMap(newArrProvince,newArrNo){
	var dataArr = [];
	for(var i = 0;i<newArrProvince.length;i++){
		// console.log(provArr[i]); 
		var json = {};
		if(newArrProvince[i].indexOf("内蒙古")>-1){
			json.name = "内蒙古";
		}else if(newArrProvince[i].indexOf("自治区")>-1){
			json.name = newArrProvince[i].substring(0,2);
		}else{
			json.name = newArrProvince[i].substring(0,newArrProvince[i].length - 1);
		}
		json.value = newArrNo[i];
		dataArr.push(json);
	}
	console.log("出差统计分布，去除后缀的省");
	console.log(dataArr);
	console.log(typeof(dataArr));
	 myChart1 = echarts.init(document.getElementById('main'));
      var ecConfig = echarts.config;  
var zrEvent = zrender.tool.event;
var curIndx = 0;
var mapType = [
];
fun(echarts);
function fun(ec){
              // 基于准备好的dom，初始化echarts图表
              myChart1 = ec.init(document.getElementById("main")); 
              	console.log("基于准备好的dom，初始化echarts图表");
            	 console.log(dataArr);
            	 
            var option = {
                         tooltip : {
                             trigger: 'item',
                             formatter: '{b}'
                         },
                         series : [
                             {
                                 name: '中国',
                                 type: 'map',
                                 mapType: 'china',
                                 roam: false,
                                 selectedMode : 'multiple',
                                 itemStyle:{
                                     normal:{label:{show:true}},
                                     emphasis:{label:{show:true}},
                                 },
                                 data:dataArr
                                 // data:newArrProvince
                             }
                         ],
                         dataRange: {
                                 x: 'left',
                                 y: 'bottom',
                                 splitList: [
                                     // {start: 1500},
                                     // {start: 900, end: 1500},
                                     {start: 300},
                                     {start: 150, end: 300},
                                     {start: 50, end: 150},
                                     {start: 1, end: 50},
                                     {end: 1}
                                 ],
                                 color: ['#c00000','#ff0000','#ff9600','#ffff00','#fff']
                             },
                             // roamController: {
                             //         show: true,
                             //         x: 'right',
                             //         mapTypeControl: {
                             //             'china': true
                             //         }
                             //     }
            };
            	 

			// update
			// var option = {
			//     tooltip: {
			//         trigger: 'item',
			//         formatter: '{b}'
			//     },
			//     visualMap: {
			//         seriesIndex: 0,
			//         min: 0,
			//         max: 500,
			//         left: 'left',
			//         top: 'bottom',
			//         text: ['高','低'],           // 文本，默认为数值文本
			//         calculable: true
			//         // controller:{
			//         // 	inRange:{
			//         // 		color:[rgb(255,255,255),rgb(255,255,0),rgb(255,128,0),rgb(255,0,0),rgb(116,0,0)]
			//         // 	}
			//         // }
			//     },
			//     grid: {
			//         height: 200,
			//         width: 8,
			//         right: 80,
			//         bottom: 10
			//     },
			//     xAxis: {
			//         type: 'category',
			//         data: [],
			//         splitNumber: 1,
			//         show: false
			//     },
			//     yAxis: {
			//         position: 'right',
			//         min: 0,
			//         max: 20,
			//         splitNumber: 20,
			//         inverse: true,
			//         axisLabel: {
			//             show: true
			//         },
			//         axisLine: {
			//             show: false  
			//         },
			//         splitLine: {
			//             show: false
			//         },
			//         axisTick: {
			//             show: false
			//         },
			//         data: []
			//     },
			//     series: [
			//         {
			//             zlevel: 1,
			//             name: '中国',
			//             type: 'map',
			//             mapType: 'china',
			//             selectedMode : 'multiple',
			//             roam: true,
			//             left: 0,
			//             right: '15%',
			//             label: {
			//                 normal: {
			//                     show: true
			//                 },
			//                 emphasis: {
			//                     show: true
			//                 }
			//             },
			//             data:dataArr
			//             // [
			//             //     {name: '北京',value: randomData() },
			//             //     {name: '天津',value: randomData() },
			//             //     {name: '上海',value: randomData() },
			//             //     {name: '重庆',value: randomData() },
			//             //     {name: '河北',value: randomData() },
			//             //     {name: '河南',value: randomData() },
			//             //     {name: '云南',value: randomData() },
			//             //     {name: '辽宁',value: randomData() },
			//             //     {name: '黑龙江',value: randomData() },
			//             //     {name: '湖南',value: randomData() },
			//             //     {name: '安徽',value: randomData() },
			//             //     {name: '山东',value: randomData() },
			//             //     {name: '新疆',value: randomData() },
			//             //     {name: '江苏',value: randomData() },
			//             //     {name: '浙江',value: randomData() },
			//             //     {name: '江西',value: randomData() },
			//             //     {name: '湖北',value: randomData() },
			//             //     {name: '广西',value: randomData() },
			//             //     {name: '甘肃',value: randomData() },
			//             //     {name: '山西',value: randomData() },
			//             //     {name: '内蒙古',value: randomData() },
			//             //     {name: '陕西',value: randomData() },
			//             //     {name: '吉林',value: randomData() },
			//             //     {name: '福建',value: randomData() },
			//             //     {name: '贵州',value: randomData() },
			//             //     {name: '广东',value: randomData() },
			//             //     {name: '青海',value: randomData() },
			//             //     {name: '西藏',value: randomData() },
			//             //     {name: '四川',value: randomData() },
			//             //     {name: '宁夏',value: randomData() },
			//             //     {name: '海南',value: randomData() },
			//             //     {name: '台湾',value: randomData() },
			//             //     {name: '香港',value: randomData() },
			//             //     {name: '澳门',value: randomData() }
			//             // ]
			//         },
			//         {
			//             zlevel: 2,
			//             name: '地图指示',
			//             type: 'bar',
			//             barWidth: 5,
			//             itemStyle: {
			//                 normal: {
			//                     color: undefined,
			//                     shadowColor: 'rgba(0, 0, 0, 0.1)',
			//                     shadowBlur: 10
			//                 }
			//             },
			//             data: [20]
			//         }
			//     ]
			// };


			/**
			 * 根据值获取线性渐变颜色
			 * @param  {String} start 起始颜色
			 * @param  {String} end   结束颜色
			 * @param  {Number} max   最多分成多少分
			 * @param  {Number} val   渐变取值
			 * @return {String}       颜色
			 */
			// function getGradientColor (start, end, max, val) {
			//     var rgb = /#((?:[0-9]|[a-fA-F]){2})((?:[0-9]|[a-fA-F]){2})((?:[0-9]|[a-fA-F]){2})/;
			//     var sM = start.match(rgb);
			//     var eM = end.match(rgb);
			//     var err = '';
			//     max = max || 1
			//     val = val || 0
			//     if (sM === null) {
			//         err = 'start';
			//     }
			//     if (eM === null) {
			//         err = 'end';
			//     }
			//     if (err.length > 0) {
			//         throw new Error('Invalid ' + err + ' color format, required hex color');	
			//     }
			//     var sR = parseInt(sM[1], 16),
			//         sG = parseInt(sM[2], 16),
			//         sB = parseInt(sM[3], 16);
			//     var eR = parseInt(eM[1], 16),
			//         eG = parseInt(eM[2], 16),
			//         eB = parseInt(eM[3], 16);
			//     var p = val / max;
			//     var gR = Math.round(sR + (eR - sR) * p).toString(16),
			//         gG = Math.round(sG + (eG - sG) * p).toString(16),
			//         gB = Math.round(sB + (eB - sB) * p).toString(16);
			//     return '#' + gR + gG + gB;
			// }

			// setTimeout(function() {
			//     var TOPN = 25
			    
			//     var option = myChart.getOption()
			//     // 修改top
			//     option.grid[0].height = TOPN * 20
			//     option.yAxis[0].max = TOPN
			//     option.yAxis[0].splitNumber = TOPN
			//     option.series[1].data[0] = TOPN
			//     // 排序
			//     var data = option.series[0].data.sort(function(a, b) {
			//         return b.value - a.value
			//     })
			    
			//     var maxValue = data[0].value,
			//         minValue = data.length > TOPN ? data[TOPN - 1].value : data[data.length - 1].value
			    
			//     var s = option.visualMap[0].controller.inRange.color[0],
			//         e = option.visualMap[0].controller.inRange.color.slice(-1)[0]
			//     var sColor = getGradientColor(s, e, maxValue, minValue)
			//     var eColor = getGradientColor(s, e, maxValue, maxValue)
			    
			//     option.series[1].itemStyle.normal.color = new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
			//         offset: 1,
			//         color: sColor
			//     }, {
			//         offset: 0,
			//         color: eColor
			//     }])
			    
			//     // yAxis
			//     var newYAxisArr = []
			//     echarts.util.each(data, function(item, i) {
			//         if (i >= TOPN) {
			//             return false
			//         }
			//         var c = getGradientColor(sColor, eColor, maxValue, item.value)
			//         newYAxisArr.push({
			//             value: item.name,
			//             textStyle: {
			//                 color: c
			//             }
			//         })
			//     })
			//     option.yAxis[0].data = newYAxisArr
			//     option.yAxis[0].axisLabel.formatter = (function(data) {
			//         return function(value, i) {
			//             if (!value) return ''
			//             return value + ' ' + data[i].value
			//         }
			//     })(data)
			//     myChart.setOption(option)
			// }, 0);

              // 为echarts对象加载数据 
              myChart1.setOption(option); 
             	//地图模块回调
	             // myChart.on('click', function (parmas) {
	            	//  console.log(parmas);
	            	//  console.log(parmas.name);
	            	//  var $tongji = $(".tongji_btn");
	            	//  var start_time = $(".start_time").val();
	            	//  var end_time = $(".end_time").val();
	            	//  var Name = $(".tongji .staffName").find('option:selected').attr('value');
	            	//  if(Name == '所有人'){
	            	// 	 Name = '';
	            	//  }
	            	   
	            	// });
    }
}

// 根据时间段显示地图
function showRangeTimeMap(){
	var re = /^(\d{4})-(\d{2})-(\d{2})$/ ;
	showMap(schedule_startTime,schedule_endTime);
}

// 距离统计
function ajaxDistanceStatistics(startTime,endTime){
	return $.ajax({
	        type: 'get',
	        url: 'DistanceStatistics',
	        dataType : 'json', 
	        data:{
	         	startTime: startTime,
	         	endTime: endTime
	            },
	        success:function(data){
	        	console.log("这里是距离统计");
	        	console.log(data);
	        	console.log(typeof data);
	        	var new2CityName = [];
	        	var new2CityTimes = [];
	        	if(data.length>1){
    		        	for(var i=1;i<data.length;i++){
    		        		new2CityName.push(data[i].Name);
    		        		if(data[i].SumDistance==""||data[i].SumDistance=="--"||data[i].SumDistance==null){
    		        			data[i].SumDistance=0;
    		        		}
    		        		var tempSumDistance = data[i].SumDistance;
    		        		var intDistance = (tempSumDistance*1).toFixed(0);
    		        		new2CityTimes.push(intDistance);
    		        	}
    		        	console.log("最新的");
    		        	console.log(new2CityName);
    		        	console.log(new2CityTimes);

    		        	// for (var key in data) {
    		        	//     console.log(key);     //获取key值
    		        	//     console.log(data[key]); //获取对应的value值
    		        	//     new2CityName.push(key);
    		        	//     if(data[key]=="--"||data[key]==""||data[key]==null){
    		        	//     	data[key]=0;
    		        	//     }
    		        	//     new2CityTimes.push(Number(data[key]));
    		        	//     console.log("新的")
    		        	//     console.log(key);     //获取key值
    		        	//     console.log(data[key]); //获取对应的value值
    		        	// }
    		        	$(".trip-distance-body").html('');
    			    	var str = '<div id="main3"></div>';
    			    	$(".trip-distance-body").append(str);
    			    	MapDistanceStatistics(new2CityName,new2CityTimes);
    			    }else{
    			    	$.MsgBox_Unload.Alert("提示","该时间段无距离统计记录");
    			    }
	        	
	        },
	        error:function(){
	        	$.MsgBox.Alert("提示","网络繁忙，请重新加载");
	        }
	    });
}

function showDistanceStatistics(){
	ajaxDistanceStatistics(schedule_startTime,schedule_endTime);
}

function MapDistanceStatistics(new2CityName,new2CityTimes){
	myChart2 = echarts.init(document.getElementById('main3'));
        var option = {
                title : {
                    text: '单位：km',
                    textStyle: {
                    	color:'#999',
                    	fontSize: 14
                    }
                    // subtext: Name
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['','']
                },
                
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        // data : cityArr,
                        data : new2CityName,
                        axisLabel:{  
                            interval:0,//横轴信息全部显示  
                            rotate:0,//-30度角倾斜显示 
                            textStyle:{
                            	color: 'rgba(0,0,0,0.9)',
                            	// fontFamily: '微软雅黑,宋体,Arial, Verdana, sans-serif',
                				fontSize:14 // 让字体变大
            				},
							fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
							// formatter:function(value)  
				   //          {  
				   //             return value.split("").join("\n");  
				   //          } 
				   			formatter:function(value)  
                             {  
                                 // debugger  
                                 var ret = "";//拼接加\n返回的类目项  
                                 var maxLength = 1;//每项显示文字个数  
                                 // var maxLength = 2;//每项显示文字个数  
                                 var valLength = value.length;//X轴类目项的文字个数  
                                 var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数  
                                 if (rowN > 1)//如果类目项的文字大于3,  
                                 {  
                                     for (var i = 0; i < rowN; i++) {  
                                         var temp = "";//每次截取的字符串  
                                         var start = i * maxLength;//开始截取的位置  
                                         var end = start + maxLength;//结束截取的位置  
                                         //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧  
                                         temp = value.substring(start, end) + "\n";  
                                         ret += temp; //凭借最终的字符串  
                                     }  
                                     return ret;  
                                 }  
                                 else {  
                                     return value;  
                                 }  
                             }
                        },
                        splitLine:{ 
							show:false
						}
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'距离',
                        type:'bar',
                        // data:numberArr,
                        data:new2CityTimes,
                        markPoint : {
                            data : []
                        },
                        markLine : {
                            data : []
                        },
                        // 头部显示数据
                        itemStyle:{
                        	normal:{
                        		color: function(params) {
    		                        // build a color map as your need.
    		                        var colorList = [
    		                          '#EE4775','#E3497F','#DD4B84','#D04B84','#CA4D91','#C04D91','#BF4E9A','#B84FA0','#B04FA0','#AA51AA','#A051AA','#9353BB','#8754C3','#8054C3','#7E56CA','#7056CA','#6F58D7','#685AE1','#605AE1','#5B5BE6','#505BE6','#4F5CF2','#4A5CF2','#455CF2','#405CA2','#3B5CA2','#305CA2'
    		                        ];
    		                        return colorList[(params.dataIndex)%(colorList.length)];
    		                    },
    		                    // 
    		                    // color: function(params) {
    		                    //     // build a color map as your need.
    		                    //     var colorList = [
    		                    //       ['#EE4775','#E3497F','#DD4B84'],
    		                    //       ['#CA4D91','#BF4E9A','#B84FA0'],
    		                    //       ['#B84FA0','#AA51AA','#9353BB'],
    		                    //       ['#8754C3','#7E56CA','#6F58D7'],
    		                    //       ['#625AE1','#5B5BE6','#4B5CF2']
    		                    //     ];
    		                    //     return new echarts.graphic.LinearGradient(0, 0, 0, 1,[
    		                    //     	{offset: 0, color: colorList[params.dataIndex][0]},
    		                    //     	{offset: 0.5, color: colorList[params.dataIndex][1]},
    		                    //     	{offset: 1, color: colorList[params.dataIndex][2]}
    		                    //     	]);
    		                    // },
    		                   
    		                    barBorderRadius: 4,  //柱状角成椭圆形
                        		label:{
                        			show:true,
		                			position:'top',
		                			textStyle: {
		                				color:'rgba(0,0,0,0.7)',
		                				fontSize:10
		                			},
		                			formatter:function(params){
		                			    if(params.value==0){
		                			        return '';
		                			    }else{
		                			        return params.value;
		                			        }
		                			}
                        		}
                        	},
                        	emphasis: {
                        	    barBorderRadius: 7
                        	},
                        }
                  //       label: {
		                // 	normal:{
		                // 		show:true,
		                // 		position:'top',
		                // 		textStyle: {
		                // 			color:'black'
		                // 		}
		                // 	}
		                // }
                    },
               
                 
                ]
    	};     
   	myChart2.setOption(option);
}


// 柱状图ajax
function barGraphAjax(start_time,end_time,Name) {
	return $.ajax({
	        type: 'get',
	        url: 'ScheduleByTime',
	        dataType : 'json', 
	        data:{
	         	StartTime : start_time,
	            EndTime : end_time,
	            Name:Name
	            },
	        success: function(result){
	            console.log("#员工按姓名出差原始数据");
	            console.log(result);
	            console.log(typeof result);
	            // 兼容老浏览器
	            if (!Object.keys) {
	                Object.keys = function (obj) {
	                    var keys = [],
	                        k;
	                    for (k in obj) {
	                        if (Object.prototype.hasOwnProperty.call(obj, k)) {
	                            keys.push(k);
	                        }
	                    }
	                    return keys;
	                };
	            }
	            var cityArr = [];
				var numberArr = [];
				var allcityArr = [];
				var allnumberArr = [];
				var allcityArrnull = ["苏州"];
				var allnumberArrnull = ["0"];
	            if(Object.keys(result).length){
		            for(var k in result){
			    		if(k == ''){
			    			delete result[k];
			    		}
			    		if(result[k] == 0 || result[k] == ''){
			    			delete result[k];
			    		}
			    		// 去市及直辖市处理
			            if(k.lastIndexOf('市') == k.length-1 && k.lastIndexOf('市') != -1){
	    		    		var city = k.substring(0,k.length-1);
	    		    		var prov = province(city);
	    		    		if(!prov && city != "北京" && city != "上海" && city != "重庆" && city != "天津" && city != "香港" && city != "澳门"){
	        		    		delete result[k];
	    		    		}
		    				var number = city + '市';
			            }else{
			            	// 不带市
			    			var city = k;
			    			var prov = province(city);
			    			if(!prov && city != "北京" && city != "上海" && city != "重庆" && city != "天津" && city != "香港" && city != "澳门"){
	        		    		delete result[k];
	    		    		}
		    				var number = city;
			    		}
		            }
		            console.log("最终输出的城市数组");
	    		    console.log(result);
		    		//对处理后的或者无需处理的result操作
		    		for(var k in result){
			    		if(k.lastIndexOf('市') == k.length-1 && k.lastIndexOf('市') != -1){
	    		    		var city = k.substring(0,k.length-1);
	    		    		var prov = province(city);
		    				var number = city + '市';
			    		}else{
			    			var city = k;
			    			var prov = province(city);
		    				var number = city;
			    		}
		    		
		    		//获取省份以后，和用户点击的省份做比较，省份一样的保留city到新的数组中，保留对应的result[k]
						allcityArr.push(city);
						allnumberArr.push(result[number])
			    	}
			    	console.log("@员工按姓名出差城市分布，处理后的数据");
			    	console.log(allcityArr);
			    	console.log(allnumberArr);
			    	$(".trip-city-body").html('');
			    	var str = '<div id="main2"></div>';
			    	$(".trip-city-body").append(str);
			    	// BarMap(Name,cityArr,numberArr);
			    	BarMap(Name,allcityArr,allnumberArr);
	            }else{
	            	BarMap(Name,allcityArrnull,allnumberArrnull);
	            	$.MsgBox_Unload.Alert("提示","该员工此时间段无出差记录");
	            }  	
		    },
		    error:function(){
		    	console.log("***员工出差城市次数统计获取失败了***");
		    }
	    });
}

function BarMap(Name,cityArr,numberArr){
	
    myChart3 = echarts.init(document.getElementById('main2'));
     function map(){
    	 
            var option = {
                title : {
                    text: '单位：次数',
                    subtext: Name,
                    textStyle: {
                    	color:'#999',
                    	// color:'rgba(0,0,0,0.8)',
                    	fontSize:14
                    }
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['','']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        data : cityArr,
                        axisLabel:{  
                            interval:0,//横轴信息全部显示  
                            rotate:0,//-30度角倾斜显示 
                            textStyle:{
                            	color: 'rgba(0,0,0,0.9)',
                            	// fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
                				fontSize:14 // 让字体变大
            				},
							fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
							formatter:function(value)  
				            {  
				               return value.split("").join("\n");  
				            }  
                       	},
                       	splitLine:{ 
							show:false
						}
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'次数',
                        type:'bar',
                        data:numberArr,
                        markPoint : {
                            data : []
                        },
                        markLine : {
                            data : []
                        },
	                    // 头部显示数据
	                    itemStyle:{
	                    	normal:{
	                    		// color: function(params) {
			                    //     // build a color map as your need.
			                    //     var colorList = [
			                    //       '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
			                    //        '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
			                    //        '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
			                    //     ];
			                    //     return colorList[params.dataIndex]
			                    // },
                        		color: function(params) {
    		                        // build a color map as your need.
    		                        var colorList = [
    		                          '#EE4775','#E3497F','#DD4B84','#D04B84','#CA4D91','#C04D91','#BF4E9A','#B84FA0','#B04FA0','#AA51AA','#A051AA','#9353BB','#8754C3','#8054C3','#7E56CA','#7056CA','#6F58D7','#685AE1','#605AE1','#5B5BE6','#505BE6','#4F5CF2','#4A5CF2','#455CF2','#405CF2','#3B5CA2','#305CA2'
    		                        ];
    		                        // return colorList[params.dataIndex];
    		                        return colorList[(params.dataIndex)%(colorList.length)];
    		                    },
    		                    barBorderRadius: 5,
	                    		label:{
	                    			show:true,
		                			position:'top',
		                			textStyle: {
		                				color:'rgba(0,0,0,0.7)',
		                				fontSize:10
		                			},
		                			formatter:function(params){
		                			    if(params.value==0){
		                			        return '';
		                			    }else{
		                			        return params.value;
		                			        }
		                			}
	                    		}
	                    	}
	                    }
                    },
                 
                ]
    		};     
    myChart3.setOption(option);
    }
    map();
}

// 员工出差城市分布显示
function showTripCity(){
	var Name = $(".trip-city-tit-right .staffName").val();
	if (Name == "所有人"){
		Name = "";
	}
	$(".trip-city-tit-left span").text(Name);
	barGraphAjax(schedule_startTime,schedule_endTime,Name);
}


// *****第三个选项卡
// 员工出差次数排行
// echartContainer1:员工出差次数排行
function echartsStaffTripTimes(StaffTripTimes_Name,StaffTripTimes_times){
	myChart4 = echarts.init(document.getElementById('echartContainer1'));
	var option = {
	        title : {
	            text: '单位：次数',
	            // subtext: Name,
	            textStyle: {
	            	color:'#999',
	            	fontSize:14
	            }
	        },
	        tooltip : {
	            trigger: 'axis'
	        },
	        legend: {
	            data:['','']
	        },
	        toolbox: {
	            show : true,
	            feature : {
	                mark : {show: true},
	                dataView : {show: true, readOnly: false},
	                magicType : {show: true, type: ['line', 'bar']},
	                restore : {show: true},
	                saveAsImage : {show: true}
	            }
	        },
	        calculable : true,
	        xAxis : [
	            {
	                type : 'category',
	                data : StaffTripTimes_Name,
	                axisLabel:{  
	                    interval:0,//横轴信息全部显示  
	                    rotate:0,//-30度角倾斜显示 
	                    textStyle:{
                            color: 'rgba(0,0,0,0.9)',
                            fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
                			fontSize:14 // 让字体变大
            			},  
						// fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
						formatter:function(value)  
			            {  
			               return value.split("").join("\n");  
			            }
	                },
	                splitLine:{ 
						show:false
					}
	            }
	        ],
	        yAxis : [
	            {
	                type : 'value'
	            }
	        ],
	        series : [
	            {
	                name:'次数',
	                type:'bar',
	                data:StaffTripTimes_times,
	                markPoint : {
	                    data : []
	                },
	                markLine : {
	                    data : []
	                },
	                // 头部显示数据
	                itemStyle:{
	                	normal:{
                    		color: function(params) {
		                        // build a color map as your need.
		                        var colorList = [
		                          '#EE4775','#E3497F','#DD4B84','#D04B84','#CA4D91','#C04D91','#BF4E9A','#B84FA0','#B04FA0','#AA51AA','#A051AA','#9353BB','#8754C3','#8054C3','#7E56CA','#7056CA','#6F58D7','#685AE1','#605AE1','#5B5BE6','#505BE6','#4F5CF2','#4A5CF2','#455CF2','#405CF2','#3B5CA2','#305CA2'
		                        ];
		                        return colorList[(params.dataIndex)%(colorList.length)];
		                    },
		                    barBorderRadius: 5,
	                		label:{
	                			show:true,
	                			position:'top',
	                			textStyle: {
	                				color:'rgba(0,0,0,0.7)',
	                				fontSize:10
	                			},
	                			formatter:function(params){
	                			    if(params.value==0){
	                			        return '';
	                			    }else{
	                			        return params.value;
	                			        }
	                			}
	                		}
	                	}
	                }
	            },
	         
	        ]
	};
	myChart4.setOption(option);
}

function StaffTripTimesAjax(start_time,end_time){
	return $.ajax({
		type: 'get',
	    url: 'FrequenceStatistics',
	    dataType : 'json', 
	    data:{
	        startTime : start_time,
	        endTime : end_time
	        // Name:Name
	    },
	    success: function(data){
	    	console.log("@@@员工出差次数排行");
	    	console.log(typeof data);
	    	console.log(data);
	    	var StaffTripTimes_Name = [];
	    	var StaffTripTimes_times = [];
	    	if(data.length>1){
	    		for (var i = 1;i<data.length;i++){
	    			if(data[i].Name != null && data[i].Name != ""){
	    				StaffTripTimes_Name.push(data[i].Name);
	    			}
	    			if(data[i].times != null && data[i].times != ""){
	    				StaffTripTimes_times.push(Number(data[i].times));
	    			}
	    		}
	    		if(StaffTripTimes_Name.length != StaffTripTimes_times.length){
	    			$.MsgBox.Alert("提示","网络异常，请重新加载");
	    		}else{
	    			console.log("员工出差次数排行处理后数据");
	    			console.log(StaffTripTimes_Name);
	    			console.log(StaffTripTimes_times);
	    			$(".trip-staff-times-body").html('');
		    	    var str = '<div id="echartContainer1"></div>';
		    	    $(".trip-staff-times-body").append(str);
	    			echartsStaffTripTimes(StaffTripTimes_Name,StaffTripTimes_times);
	    		}
	    	}else{
	    		$.MsgBox_Unload.Alert("提示","该时间段无出差次数排行");
	    	}
	    	
	    },
	    error: function(){
	    	$.MsgBox_Unload.Alert("提示","网络出错，出差次数排行绘制失败");
	    }
	});
}

function showStaffTripTimes(){
	StaffTripTimesAjax(schedule_startTime,schedule_endTime);
}

// 所有员工差旅费统计
// echartContainer2 所有员工差旅费统计
// 
function echartsExpenseStatistics(ExpenseStatistics_Destination,ExpenseStatistics_Expense){
	myChart5 = echarts.init(document.getElementById('echartContainer2'));
	var option = {
	        title : {
	            text: '单位：元',
	            // subtext: Name,
	            textStyle: {
	            	color:'#999',
	            	fontSize:14
	            }
	        },
	        tooltip : {
	            trigger: 'axis'
	        },
	        legend: {
	            data:['','']
	        },
	        toolbox: {
	            show : true,
	            feature : {
	                mark : {show: true},
	                dataView : {show: true, readOnly: false},
	                magicType : {show: true, type: ['line', 'bar']},
	                restore : {show: true},
	                saveAsImage : {show: true}
	            }
	        },
	        calculable : true,
	        xAxis : [
	            {
	                type : 'category',
	                data : ExpenseStatistics_Destination,
	                axisLabel:{  
	                    interval:0,//横轴信息全部显示  
	                    rotate:0,//-30度角倾斜显示 
	                    textStyle:{
                            color: 'rgba(0,0,0,0.9)',
                            // fontFamily: 'sans-serif',
                			fontSize:14 // 让字体变大
            			},
            			fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
            			formatter:function(value)  
                        {  
                           return value.split("").join("\n");  
                        }  
	                },
	                splitLine:{ 
						show:false
					}
	            }
	        ],
	        yAxis : [
	            {
	                type : 'value'
	            }
	        ],
	        series : [
	            {
	                name:'差旅费',
	                type:'bar',
	                data:ExpenseStatistics_Expense,
	                markPoint : {
	                    data : []
	                },
	                markLine : {
	                    data : []
	                },
	                // 头部显示数据
	                itemStyle:{
	                	normal:{
                    		color: function(params) {
		                        // build a color map as your need.
		                        var colorList = [
		                          '#EE4775','#E3497F','#DD4B84','#D04B84','#CA4D91','#C04D91','#BF4E9A','#B84FA0','#B04FA0','#AA51AA','#A051AA','#9353BB','#8754C3','#8054C3','#7E56CA','#7056CA','#6F58D7','#685AE1','#605AE1','#5B5BE6','#505BE6','#4F5CF2','#4A5CF2','#455CF2','#405CF2','#3B5CA2','#305CA2'
		                        ];
		                        return colorList[(params.dataIndex)%(colorList.length)];
		                    },
		                    barBorderRadius: 5,
	                		label:{
	                			show:true,
	                			position:'top',
	                			textStyle: {
	                				color:'rgba(0,0,0,0.7)',
	                				fontSize:10
	                			},
	                			formatter:function(params){
	                			    if(params.value==0){
	                			        return '';
	                			    }else{
	                			        return params.value;
	                			        }
	                			}
	                		}
	                	}
	                }
	            },
	         
	        ]
	};
	myChart5.setOption(option);
}

function ExpenseStatisticsAjax(start_time,end_time){
	return $.ajax({
		type: 'get',
	    url: 'ExpenseStatistics',
	    dataType : 'json', 
	    data:{
	        startTime : start_time,
	        endTime : end_time
	        // Name:Name
	    },
	    success: function(data){
	    	console.log("#费用统计原始数据");
	    	console.log(data);
	    	console.log(typeof data);
	    	var ExpenseStatistics_Destination = [];
	    	var ExpenseStatistics_Expense = [];
	    	var ExpenseStatistics_Expensenull = ["苏州市"];
	    	var ExpenseStatistics_Expensenull = ["0"];
	    	if(data.length > 1){
	    		for(i=1;i<data.length;i++){
	    			var city = data[i].Destination;
	    			var expense = data[i].Expense;
	    			if(city.lastIndexOf('市') == city.length-1 && city.lastIndexOf('市') != -1){
	    				city = city.substring(0,city.length-1);
			    		// if(city != "北京" && city != "上海" && city != "重庆" && city != "天津" && city != "香港" && city != "澳门"){
	    		  //   		data.splice(i, 1);
			    		// }

	    			}else if(city.lastIndexOf('县') == city.length-1 && city.lastIndexOf('县') != -1){
	    				city = city.substring(0,city.length-1);
	    			}

	    			// 再次处理
	    			if(city.lastIndexOf('市') == city.length-1 && city.lastIndexOf('市') != -1){
	    				city = city.substring(0,city.length-1);
	    			}else if(city.lastIndexOf('县') == city.length-1 && city.lastIndexOf('县') != -1){
	    				city = city.substring(0,city.length-1);
	    			}

	    			// 费用处理
	    			if(expense == null || expense == "" || expense == "--"){
	    				expense = "0";
	    			}
	    			ExpenseStatistics_Destination.push(city);
	    			ExpenseStatistics_Expense.push(expense);
	    		}
	    		if(ExpenseStatistics_Destination.length != ExpenseStatistics_Expense.length){
	    			$.MsgBox.Alert("提示","网络异常，请重新加载");
	    		}else{
	    			console.log("差旅费用处理后数据");
	    			console.log(ExpenseStatistics_Destination);
	    			console.log(ExpenseStatistics_Expense);
	    			$(".trip-cost-calc-body").html('');
		    	    var str = '<div id="echartContainer2"></div>';
		    	    $(".trip-cost-calc-body").append(str);
	    			echartsExpenseStatistics(ExpenseStatistics_Destination,ExpenseStatistics_Expense);
	    		}
	    		
	    	}else{
	    		echartsExpenseStatistics(ExpenseStatistics_Destinationnull,ExpenseStatistics_Expensenull);
	    		$.MsgBox_Unload.Alert("提示","该时间段无费用数据");
	    	}
	    },
	    error: function(){
	    	$.MsgBox_Unload.Alert("提示","网络出错，费用统计绘制失败");
	    }
	});
}

function showExpenseStatistics(){
	ExpenseStatisticsAjax(schedule_startTime,schedule_endTime);
}

// // 图表自适应
// $(window).resize(function() { //这是能够让图表自适应的代码
//     myChart1.resize();
//     myChart2.resize();
//     myChart3.resize();
//     myChart4.resize();
//     myChart5.resize();
// });


//选项卡
var cruurntCardIndex;
// function calcBodyBottomH(){
// 	var newH = $("")
// }
// var aa1H = 50;
// var aa2H = 750;
// var aa3H = 1280;
// $(".aa1").on("click",function(){
//   $(window).scrollTop(aa1H);
//   // $(window).stop(true).animate({scrollTop: aa1H}, 500);
// });
// $(".aa2").on("click",function(){
//   $(window).scrollTop(aa2H);
// });
// $(".aa3").on("click",function(){
//   $(window).scrollTop(aa3H);
// });

$(".changeBox .Domestic").click(function(){
	$(".changeBox").css("background","url(image/bg11.png)");
	$(".bodyContent").css("height","705px");
	var bodyContentH = $(".bodyContent").height()+190;
	$("body.nav-body").css("height",bodyContentH+"px");
	$(".reloadDiv").fadeOut(50);
	$(".mapBox").fadeOut(50);
	$(".trip-city").fadeOut(50);
	$(".trip-cost-calc").fadeOut(50);
	$(".trip-staff-times").fadeOut(50);
	$(".trip-distance").fadeOut(50);
	$("#engineer-schedule-container").fadeIn(50);
	 // $(".ExitOrEn").css("background","url(image/bg3.png)");
	 // $(".Domestic").css("background","none");
	 // calcBodyBottomH();
});
$(".changeBox .ExitOrEn").click(function(){
	cruurntCardIndex = 2;
	$(".changeBox").css("background","url(image/bg22.png)");
	$(".bodyContent").css("height","1640px");
	var bodyContentH = $(".bodyContent").height()+190;
	$("body.nav-body").css("height",bodyContentH+"px");
	// aa1H = 50;
	// aa2H = $(".trip-city").offset().top - 50;
	// aa3H = $(".trip-distance").offset().top - 50;
		 // var Y = new Date().getFullYear();
			// var thetime = Y+'-01-01T00:00:00-04:00';
			// var  sT=new   Date(Date.parse(thetime));
			// document.getElementById('start_time').valueAsDate = sT;
			// document.getElementById('end_time').valueAsDate = new Date(); 
	$("#engineer-schedule-container").hide();
	$(".trip-staff-times").fadeOut(50);
	$(".trip-distance").fadeOut(50);
	$(".reloadDiv").fadeIn(50);
	$(".mapBox").fadeIn(50);
	$(".trip-city").fadeIn(50);
	$(".trip-cost-calc").fadeIn(50);
	// calcBodyBottomH();
	var engineer = $("span.engineer").html();
	var engineerOb = engineer.split(',')
		var len = engineerOb.length;
		var str1 = '<select class="staffName" style="width: 90%;height:27px;border-radius:5px;border:1px solid #999">'+
					'<option value="所有人">所有人</option>';
		var str2 = '';
		var str3 = '';
		var str4 = '</select>'
		for(var i = 1;i<len-1;i++){
				str2 += '<option value="'+engineerOb[i].substring(12,engineerOb[i].length-1)+'">'+engineerOb[i].substring(12,engineerOb[i].length-1)+'</option>';
		}
		str3 += '<option value="'+engineerOb[len-1].substring(12,engineerOb[len-1].length-2)+'">'+engineerOb[len-1].substring(12,engineerOb[len-1].length-2)+'</option>';
		Str = str1+str2+str3+str4;
		// $(".tongji .nameContent").remove();
		$(".trip-city-tit-right .staffName").remove();
		$(".trip-city-tit-right").append(Str);
		
	 // $(".Domestic").css("background","url(image/bg3.png)");
	 // $(".ExitOrEn").css("background","none");  	
	switchDate();
	showRangeTimeMap();
	showTripCity();
	showExpenseStatistics();
});

$(".changeBox .staffData").on("click",function(){
	cruurntCardIndex = 3;
	$(".changeBox").css("background","url(image/bg33.png)");
	$(".bodyContent").css("height","1120px");
	var bodyContentH = $(".bodyContent").height()+190;
	$("body.nav-body").css("height",bodyContentH+"px");
	$("#engineer-schedule-container").hide();
	$(".mapBox").fadeOut(50);
	$(".trip-city").fadeOut(50);
	$(".trip-cost-calc").fadeOut(50);
	$(".reloadDiv").fadeIn(50);
	$(".trip-staff-times").fadeIn(50);
	$(".trip-distance").fadeIn(50);
	// calcBodyBottomH();
	switchDate();
	showStaffTripTimes();
	showDistanceStatistics();
});

//点击统计按钮，发送请求***
$(".tongji_btn").click(function(){
	switchDate();
	if(cruurntCardIndex == 2){
		// alert("2");
		showRangeTimeMap();
		showTripCity();
		showExpenseStatistics();
	}else if(cruurntCardIndex == 3){
		// alert("3");
		showStaffTripTimes();
		showDistanceStatistics();
	}
	// showRangeTimeMap();
	// showTripCity();
	// showDistanceStatistics();
});

$(document).on("change",".trip-city-tit-right .staffName",function(){
	switchDate();
	showTripCity();
});

var currentDate;
$(function(){
	var searchDate = '<%=request.getAttribute("allDate")%>';
	console.log(searchDate);
	if(searchDate != null && searchDate != "[{col1=Date}]"){
		searchDate = searchDate.replace(/ /g,"").replace(/{Date=/g,"").replace(/}/g,"").replace("{","").replace("[","").replace("]","").split(",").unique();
	} 
	console.log(searchDate);
	var currentHref = window.location.href;
	var myDate = new Date();
	if(currentHref.indexOf("-")>0){
		currentDate = currentHref.split("?Date=")[1];
		 if(currentDate.indexOf("&")>-1)
		 {
			 currentDate = currentDate.split("&")[0]
		 }
	}
	else{
		var getDate = myDate.getDate();
		getDate < 10 ? (getDate = "0"+getDate):(getDate = getDate);
		currentDate = myDate.getFullYear()+"-"+ (myDate.getMonth()+1)+"-"+getDate;
	}
    /* 日历插件 */
    $('#calendar').fullCalendar({
        header: {
            left: '',
            center: 'prevYear,prev,title,next,nextYear',
            right: ''
        },
        buttonText: {
            prev: '<', // ‹
            next: '>', // ›
            prevYear: '<<', // «
            nextYear: '>>', // »
        },
        titleFormat: {
            month: "yyyy年MM月",
            week: '周ddd'
        },
        locale: "zh-cn",
        weekMode :5,
        firstDay:1,
        editable: false,
        timeFormat: 'H:mm',
        axisFormat: 'H:mm',
        minTime: '7',
        maxTime:'19',
        allDaySlot:false,
        dayNamesShort:['日','一','二','三','四','五','六'],
        events: eventsList(),
        dayClick: function(date, jsEvent, view) {
           //执行表格切换时候的数据函数
            var theDate = $.fullCalendar.formatDate(date,'yyyy-MM-dd')
           /*  alert('当天事件为:' + theDate);  */
            var Date=theDate;
            $('.table1 .DateTime').attr("value",Date);//暂存日期
            currentDate = theDate;
           
            $("#help-form #All_upload").val(Date);
            $("#help-form").submit(); 
            
        },
        eventClick:function(event, jsEvent, view) {
        	
        },

    });
    //改变日历的宽高
    function get_height() {
        $('table.fc-border-separate tbody tr').width($('.fc-day.fc-六.fc-widget-content.fc-future').width())

    }
    
    //计算时间差值
    if(currentHref.indexOf("?Date=") >0){
        var myDate = new Date();
        var currentYear = currentHref.split("?Date=")[1].split("-")[0];
    	 var year = parseFloat(myDate.getFullYear());
    	if(currentYear - year > 0){
    		var nextYear = document.getElementsByClassName('fc-button-nextYear')[0];
    		for(var i = 0 ;i < (currentYear - year) ; i++){
    			nextYear.click();
    		}
    	}
    	else if(currentYear - year < 0){
    		var lastYear = document.getElementsByClassName('fc-button-prevYear')[0];
    		for(var i = 0 ;i < (year - currentYear) ; i++){
    			lastYear.click();
    		}
    	}
    	 var currentMonth = currentHref.split("?Date=")[1].split("-")[1];
    	 var month = parseFloat(myDate.getMonth())+1;
    	 if(currentMonth -month > 0){
    			var nextMoth = document.getElementsByClassName('fc-button-next')[0];
    			for(var i = 0 ;i < (currentMonth - month) ; i++){
    				nextMoth.click();
    			}
    		}
    		else if(currentMonth - month < 0){
    			var lastMonth = document.getElementsByClassName('fc-button-prev')[0];
    			for(var i = 0 ;i < (month - currentMonth) ; i++){
    				lastMonth.click();
    			}
    		}
    }
	for(var i = 0 ;i < $(".fc-border-separate .fc-day").length ; i++){
		//console.log(searchDate)
	 	if(typeof(searchDate)=="object"){
	 		for(var j = 1;j <searchDate.length ; j++ ){
	 		/* 	console.log($(".fc-border-separate .fc-day").eq(i).attr("data-date"))
	 		   console.log(searchDate[j]) */
	 			if($(".fc-border-separate .fc-day").eq(i).attr("data-date") == searchDate[j]){
	 				var str='<div class="fc_flag" style="width:8px;height:8px;border-radius: 50%;background:red;float: right;margin-top: 0px;"></div>';
	 				$(".fc-border-separate .fc-day").eq(i).find(".fc-day-content").after(str);
	 			}
	 		}
	 	}
    	if($(".fc-border-separate .fc-day").eq(i).attr("data-date") == currentDate){
    		$(".fc-border-separate .fc-day").eq(i).addClass('fc-state-highlight');
    		$(".fc-border-separate .fc-day").eq(i).siblings().removeClass('fc-state-highlight');
    	}
    }
    $(".fc-button").click(function(){
    	for(var i = 0 ;i < $(".fc-border-separate .fc-day").length ; i++){
    		//console.log(searchDate)
    	 	if(typeof(searchDate)=="object"){
    	 		for(var j = 1;j <searchDate.length ; j++ ){
    	 			if($(".fc-border-separate .fc-day").eq(i).attr("data-date") == searchDate[j]){
    	 				var str='<div class="fc_flag" style="width:8px;height:8px;border-radius: 50%;background:red;float: right;margin-top: 10px;"></div>';
    	 				$(".fc-border-separate .fc-day").eq(i).find(".fc-day-content").after(str);
    	 			}
    	 		}
    	 	}
        }
    })
    
  //改变日历的颜色
    $('body').on('click', '.fc-day', function () {
             var obj = $(this), date = obj.data('date');
             $('.fc-state-highlight').removeClass('fc-state-highlight');
             obj.addClass('fc-state-highlight');
     });
    
    get_height();

    $('#calendar').on('click', 'span.fc-button', function () {
        get_height();
    });
   
   
    //日历上的显示数据
    function eventsList(){
        var data = [
           /* {
                title: ' ',
                start: "2017-12-04",
            } */
        ];
        return  data;
    }
    
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
	
	
	//日历的标题
	
	//http://localhost:8080/cfChicken8/Schedule?Date=2018-04-01&currentPage=1
	//http://localhost:8080/cfChicken8/Schedule
	//http://localhost:8080/cfChicken8/Schedule?Date=2018-04-17
	var D=new Date(); 
	var ww=D.getDate();
	var Href = window.location.href;
	var i = Href.indexOf('?Date');
	if(i == '-1'){
		var numberT = ww;
		var DateT = new Date();
	}else{
		var Href1 = Href.substring(i+1); // "Date=2018-04-01&currentPage=1"
		Href1 = Href1.indexOf("&")>-1?Href1.split("&")[0]:Href1;
		var numberT = Href1.substring(13);
		if(numberT<10){
			numberT = numberT.substring(1);
		}
		var numberDate = Href1.substring(5) ;
		var DateT = new Date(Date.parse(numberDate));
	}
	
	/*  var today = $(".fc-state-highlight").html();  */
	/* var D=new Date(); 
	var yy=D.getFullYear(); 
	var mm=D.getMonth()+1; 
	var ww=D.getDate(); 
	if(mm<10){
        var month = "0"+mm;
    }
    else{
    	var month =mm;
    }	  */
/* 	var time = yy + "年" + month + "月"; */
	
	var time = $(".fc-header-title h2").html();
	$(".dateBox_title").html(time);
	$(".dateBox_day").html(numberT);
	//日历上显示今天星期几
	function myFunction(DateT)
	{
		var d = DateT;
		var weekday=new Array(7);
		weekday[0]="星期日";
		weekday[1]="星期一";
		weekday[2]="星期二";
		weekday[3]="星期三";
		weekday[4]="星期四";
		weekday[5]="星期五";
		weekday[6]="星期六";
		/* var x = document.getElementById("demo");
		x.innerHTML=weekday[d.getDay()]; */
		$(".dataBox_textDay").html(weekday[d.getDay()]);
	}
	myFunction(DateT);
	
	//日历标题上显示农历
	/*获取当前农历*/
	function showCal(DateT){ 
	var D=DateT; 
	var yy=D.getFullYear(); 
	var mm=D.getMonth()+1; 
	var dd=D.getDate(); 
	var ww=D.getDay(); 
	var ss=parseInt(D.getTime() / 1000); 
	if (yy<100) yy="19"+yy; 
	return GetLunarDay(yy,mm,dd); 
	} 
	 
	//定义全局变量 
	var CalendarData=new Array(100); 
	var madd=new Array(12); 
	var tgString="甲乙丙丁戊己庚辛壬癸"; 
	var dzString="子丑寅卯辰巳午未申酉戌亥"; 
	var numString="一二三四五六七八九十"; 
	var monString="正二三四五六七八九十冬腊"; 
	var weekString="日一二三四五六"; 
	var sx="鼠牛虎兔龙蛇马羊猴鸡狗猪"; 
	var cYear,cMonth,cDay,TheDate; 
	CalendarData = new Array(0xA4B,0x5164B,0x6A5,0x6D4,0x415B5,0x2B6,0x957,0x2092F,0x497,0x60C96,0xD4A,0xEA5,0x50DA9,0x5AD,0x2B6,0x3126E, 0x92E,0x7192D,0xC95,0xD4A,0x61B4A,0xB55,0x56A,0x4155B, 0x25D,0x92D,0x2192B,0xA95,0x71695,0x6CA,0xB55,0x50AB5,0x4DA,0xA5B,0x30A57,0x52B,0x8152A,0xE95,0x6AA,0x615AA,0xAB5,0x4B6,0x414AE,0xA57,0x526,0x31D26,0xD95,0x70B55,0x56A,0x96D,0x5095D,0x4AD,0xA4D,0x41A4D,0xD25,0x81AA5,0xB54,0xB6A,0x612DA,0x95B,0x49B,0x41497,0xA4B,0xA164B, 0x6A5,0x6D4,0x615B4,0xAB6,0x957,0x5092F,0x497,0x64B, 0x30D4A,0xEA5,0x80D65,0x5AC,0xAB6,0x5126D,0x92E,0xC96,0x41A95,0xD4A,0xDA5,0x20B55,0x56A,0x7155B,0x25D,0x92D,0x5192B,0xA95,0xB4A,0x416AA,0xAD5,0x90AB5,0x4BA,0xA5B, 0x60A57,0x52B,0xA93,0x40E95); 
	madd[0]=0; 
	madd[1]=31; 
	madd[2]=59; 
	madd[3]=90; 
	madd[4]=120; 
	madd[5]=151; 
	madd[6]=181; 
	madd[7]=212; 
	madd[8]=243; 
	madd[9]=273; 
	madd[10]=304; 
	madd[11]=334; 
	 
	function GetBit(m,n){ 
	return (m>>n)&1; 
	} 
	//农历转换 
	function e2c(){ 
	TheDate= (arguments.length!=3) ? new Date() : new Date(arguments[0],arguments[1],arguments[2]); 
	var total,m,n,k; 
	var isEnd=false; 
	var tmp=TheDate.getYear(); 
	if(tmp<1900){ 
	tmp+=1900; 
	} 
	total=(tmp-1921)*365+Math.floor((tmp-1921)/4)+madd[TheDate.getMonth()]+TheDate.getDate()-38; 
	 
	if(TheDate.getYear()%4==0&&TheDate.getMonth()>1) { 
	total++; 
	} 
	for(m=0;;m++){ 
	k=(CalendarData[m]<0xfff)?11:12; 
	for(n=k;n>=0;n--){ 
	if(total<=29+GetBit(CalendarData[m],n)){ 
	isEnd=true; break; 
	} 
	total=total-29-GetBit(CalendarData[m],n); 
	} 
	if(isEnd) break; 
	} 
	cYear=1921 + m; 
	cMonth=k-n+1; 
	cDay=total; 
	if(k==12){ 
	if(cMonth==Math.floor(CalendarData[m]/0x10000)+1){ 
	cMonth=1-cMonth; 
	} 
	if(cMonth>Math.floor(CalendarData[m]/0x10000)+1){ 
	cMonth--; 
	} 
	} 
	} 
	 
	function GetcDateString(){ 
		var tmp=""; 
		var tmpother = "";
		/*显示农历年：（ 如：甲午(马)年 ）*/
		tmp+=tgString.charAt((cYear-4)%10); 
		tmp+=dzString.charAt((cYear-4)%12); 
		tmp+="("; 
		tmp+=sx.charAt((cYear-4)%12); 
		tmp+=")年 ";
		if(cMonth<1){ 
		tmp+="(闰)"; 
		tmpother+=monString.charAt(-cMonth-1); 
		}else{ 
		tmpother+=monString.charAt(cMonth-1); 
		} 
		tmpother+="月"; 
		tmpother+=(cDay<11)?"初":((cDay<20)?"十":((cDay<30)?"廿":"三十")); 
		if (cDay%10!=0||cDay==10){ 
		tmpother+=numString.charAt((cDay-1)%10); 
		} 

		var Tmp = tmp+'好'+tmpother;
		return Tmp; 
		} 
		 
		function GetLunarDay(solarYear,solarMonth,solarDay){ 
		//solarYear = solarYear<1900?(1900+solarYear):solarYear; 
		if(solarYear<1921 || solarYear>2020){ 
		return ""; 
		}else{ 
		solarMonth = (parseInt(solarMonth)>0) ? (solarMonth-1) : 11; 
		e2c(solarYear,solarMonth,solarDay); 
		return GetcDateString(); 
		} 
		}	
				var calendar = showCal(DateT); 
				var index =  calendar.indexOf("好");
				/* alert(index); */
				var Y = calendar.substring(0,index);
				var M = '农历 '+calendar.substring(index+1);
				$(".dataBox_textLunar").html(M);
				$(".dataBox_textYear").html(Y);
				
				
	//星座
				/* function getAstro(m,d){
					  return "魔羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯".substr(m*2-(d<"102223444433".charAt(m-1)- -19)*2,2);
					}
					//下面写一个测试函数
					function test(m,d){
					document.writeln(m+"月"+d+"日 "+getAstro(m,d));
					}
			$(".dataBox_textStar").html(getAstro(m,d)) */
})



 /****************** 跳页 **********************/

function FistPage(arg) {
		window.location.href = arg + "1";
}
function UpPage(arg) {
		window.location.href = arg ;
}
function NextPage(arg) {
		window.location.href = arg ;
}
function PageJump(arg) {
	
	var jumpNumber = document.getElementById("jumpNumber").value;
	if (jumpNumber == null || jumpNumber == 0) {
		jumpNumber = $('#currentPage').html();
	} else if (jumpNumber > parseInt($('#allPage').html())) {
		jumpNumber = $('#allPage').html();
	}
		window.location.href = arg + jumpNumber;
}
function LastPage(arg) {
	var jumpNumber = parseInt($('#allPage').html());
		window.location.href = arg + jumpNumber;
}
//数组去重
Array.prototype.unique =function(){
	 var res = [];
	 var json = {};
	 for(var i = 0; i < this.length; i++){
	  if(!json[this[i]]){
	   res.push(this[i]);
	   json[this[i]] = 1;
	  }
	 }
	return res;
}


    
// //省市联动
// $(function(){
// 				$("#city_1").citySelect({prov:"江苏", city:"苏州"}); 
// 				$("#city_2").citySelect({prov:"江苏", city:"苏州"}); 
// 			});

// 滚动响应		
$(function(){
	// alert("11");
	$(window).scrollTop(0);
	// $("#top").trigger("click");
	var top0 = $("#main-box").offset().top;
	var left0 = $("#main-box").offset().left-1;
	$(window).scroll(function(){
		var s = $(window).scrollTop();
		if(s>=top0){
			// var left1 = $(".flex-right-con").offset().left - 300
			$(".left-fixed-div").css({'position':'fixed','left':left0+"px",'top':'0'});
		}else{
			$(".left-fixed-div").css({'position':'static'});
		}
	});
	$(".cover-all").fadeOut(30);
	$(".cover-all-img").fadeOut(30);
	$("input[name='date_sel'][value='dateRange']").prop("checked","checked");
	$.Response_Load.After("页面加载完成！",200);
});	
			
</script>

</html>