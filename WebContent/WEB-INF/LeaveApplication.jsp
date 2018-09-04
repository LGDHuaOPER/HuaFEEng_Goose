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
<title>请假申请</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" />
<link rel="stylesheet" type="text/css" href="css/LeaveApplication.css">
<style>
	.content .choose {
		position: relative !important;
		width: 90% !important;
	}

	.search_div {
		position: absolute;
		right: 2px;
		display: inline-block;
	}
</style>
</head>
<body>
	<!-- 	头部开始 -->
	<%@include file="top.jsp"%>
	<!-- 	头部结束 -->
	<div class="contain">
		<div class="content">
		<%@include file="nav.jsp"%>
			<!-- <form id="top_text_from" name="top_text_from" method="post"
				action="GetMachineDetailsRoute"> -->
				<div class="choose">
					<input type="button" value="添加" class="bToggle" onclick="AddContract()">
					<input type="button" value="下载" class="bToggle down"  style="margin-left:20px;display:none">
					<input type="button" value="导出" class="bToggle"  style="margin-left:20px;">
					<div class="search_div">
						<span>
							<div>&nbsp;年份</div><select name="" id="search_Y">
								<!-- <option value="All">All</option> -->
							</select>
						</span>
						<span>
							<div>&nbsp;月份</div><select name="" id="search_M">
								<option value="All">All</option>
							</select>
						</span>
						<input type="button" class="bToggle" value="搜索">
						<input type="button" class="bToggle" value="取消">
					</div>
				</div>
				<span id="LeaveApp_currentPage" style="display: none;">${currentPage}</span>
				<span id="LeaveApp_year" style="display: none;">${year}</span>
				<span id="LeaveApp_month" style="display: none;">${month}</span>
			<!--  </form>   -->
		 
			<table border="1" cellspacing="0" cellspadding="0" id="table1">
				<tr style="background:#bfbfbf">
					<td>序号</td>
					<td>部门</td>
					<td>姓名 </td>
					<td>请假类别</td>
					<td>请假事由说明</td>
					<td>请假开始时间</td>
					<td>请假截止时间</td>
					<td>预览</td>
					<td class="approval">是否通过</td>
					<td >销假日期</td>
					<td style="display:none">收件人</td>
					<td style="display:none">密码</td>
					<td style="display:none">邮件内容1</td>
				</tr>
				
				<c:forEach var="orderInfo" items="${datas}" varStatus="status">
					<c:if test="${status.index>0}">
						<tr style="background:#fff;" class="tbody_tr">
							<td value="${orderInfo['ID']}" class="preview" style="cursor:pointer;width:5%;max-width:60px">${status.index+(currentPage-1)*10}</td>
							<td class="Department" style="width:8%">${orderInfo['Department']}</td>
							<td class="RealName" style="max-width:170px;width:9%">${orderInfo['RealName']}</td>
							<td title="${orderInfo['Classify']}"  class="Classify"  style="width:10%;max-width:120px">${orderInfo['Classify']}</td>
							<td title="${orderInfo['Reason']}"  class="GraduateInstitutions" style="width:26%">${orderInfo['Reason']}</td>
							<td class="StartTime" style="width:12%">${orderInfo['StartTime']}</td>
							<td class="EndTime" style="width:12%">${orderInfo['EndTime']}</td>
							<td class="preview2" style="width:5%;cursor:pointer">
							<i class="fa fa-eye contract-show"></i>
							</td>
							<%-- <span>${orderInfo['Review']}</span> --%>
							<c:if test="${orderInfo['Review'] == '等待审批'}">
								<td class="Review Review1" style="width:14%;cursor:pointer">${orderInfo['Review']}</td>
							</c:if>
							
							<c:if test="${orderInfo['Review'] == '审批通过'}">
								<td class="Review Review2" style="width:14%;color:green">${orderInfo['Review']}</td>
							</c:if>
							<c:if test="${orderInfo['Review'] == '审批不通过'}">
								<td title="${orderInfo['FailedReason']}" class="Review Review3" style="width:14%;color:red">${orderInfo['Review']}(${orderInfo['FailedReason']})</td>
							</c:if>
							<c:choose>
								<c:when test="${orderInfo['Eliminate'] == '未销假'}">
									<td class="Eliminate Eliminate1" style="width:14%;min-width:100px;cursor:pointer;color:red"><input type="button" class="Eliminate1_btn" value="${orderInfo['Eliminate']}"></td>
								</c:when>
								<c:otherwise>
									<td class="Eliminate" style="width:14%;min-width:100px;color:green">${orderInfo['Eliminate']}</td>
								</c:otherwise>
							</c:choose>
								<td class="ToList" style="width:12%;display:none">${orderInfo['ToList']}</td>
								<td class="Password" style="width:12%;display:none">${orderInfo['Password']}</td>
								<td class="MailContentText" style="width:12%;display:none">${orderInfo['MailContentText']}</td>
								
						</tr>
					</c:if>
				</c:forEach> 
			</table>	
			<div class="cover-color"></div>
			 <!-- -------------------------  添加请假信息 ----------------------------------->
			        <div class="contract_add">
			            <div class="contract_title">添加请假信息</div>
			            <div class="contractAdd_close">关闭</div>
			            <div class="basic_info" style="padding-left:5%">
			                <div class="table_title">请假信息</div>
			                <!-- 修改添加 -->
			                <div class="table_body" style="margin-top:15px;margin-bottom:60px;">
			                	<div class="table_col1" style="float:left;width:24%;min-width:24%;padding-right:0%;">
			                		 <span style="float:left;margin-right:9%">部门</span>
			                         <span>
			                         	<select style="width:66%;height:25px;margin-bottom:20px;" name="Department"  id="Department">
			                        	 <option text="请选择">请选择</option>
										  <option text="人事部">人事部</option>
										  <option text="销售部">销售部</option>
										  <option text="商务部">商务部</option>
										  <option text="服务部">服务部</option>
										  <option text="物流部">物流部</option>
										  <option text="市场部">市场部</option>
										  <option text="财务部">财务部</option>
										  <option text="软件部">软件部</option>
										  <option text="硬件部">硬件部</option>
										  <option text="厦门办事处">厦门办事处</option>
										</select>
			                         </span>
			                         <br>
			                        <span style="float:left;margin-right:9%">姓名</span>
			                         
			                          <!-- <input type="text" name="RealName" value="" id="RealName" class="input_css RealName"  oninput="myFunction()">  -->
			                          <div style="display:inline-block;" class="RealNameBox" >
										<input type="search" class="RealName" style="height:25px"> 
										<ul class="RealNamelist">
										</ul>
										</div>
			                        <br>
			                	</div>
			                	<div  class="table_col2" style="float:left;width:28%;min-width:28%;padding-left:5%;padding-right:0%;padding-bottom:30px;border-left:1px solid #ccc;border-right:1px solid #ccc;">
			                		<span style="float:left;margin-right:5%">起始时间</span>
			                        <span><input type="datetime-local" name="start_time" value="" id="start_time" class="input_css start_time" placeholder="xxxx-xx-xx xx:xx"></span>
			                        <br>
			                        <span style="float:left;margin-right:5%">截止时间</span>
			                         <span><input type="datetime-local" name="end_time" value="" id="end_time" class="input_css end_time" placeholder="xxxx-xx-xx xx:xx"></span>
			                        <br>
			                        
			                    </div>
			                	<div  class="table_col3" style="float:left;width:30%;min-width:28%;padding-left:4%;">
			                		<span style="float:left;margin-right:5%">请假类别</span>
			                         <span>
			                         	<select style="width:66%;height:25px;margin-bottom:20px;" name="Classify"  id="Classify">
			                        	 <option text="请选择">请选择</option>
										  <option  text="事假">事假</option>
										  <option  text="病假">病假</option>
										  <option  text="婚假">婚假</option>
										  <option  text="产假">产假</option>
										  <option  text="年休假">年休假</option>
										  <option  text="丧假">丧假</option>
										  <option  text="其他">其他</option>
										</select>
			                         </span>
			                         <br>
			                        <span style="float:left;margin-right:5%">请假事由</span>
			                         <span><textarea cols="28" rows="4" class="Reason"></textarea></span>
			                        <br>
			                        </div>
			                </div>
			                <div class="table_title" style="margin-top:133px;margin-bottom:15px">邮件模板</div>
			                <p style="margin-bottom:15px;float:left" >收件人
			                <!-- <input type="text" class="ToList" style="margin-left:1%;margin-right:8.7%;width:15%;height:22px" value=""> -->
			                 <div style="display:inline-block;margin-left:1.1% ;margin-right:8%" class="ToBox" >
										<input type="search" class="To" style="height:25px"> 
										<ul class="Tolist">
										</ul>
										</div>
			                       	邮箱密码<input type="text" placeholder="请输入申请人邮箱密码" class="Password" style="margin-left:1.5%;height:22px;width:18%;" value=""><span class="passeye" style="margin-left:1%"></span> </p><br>
			                <p><span style="position:absolute;top:308px;">正文</span>
							<textarea type="text" name="MailContent" value="" class="MailContent" style="width:82%;height:100px;margin-left:5%;font-family: Microsoft YaHei;font-size: 14px;" contenteditable="true">XXX，您好！
此处填写请假邮件正文的内容。
祝好！
</textarea></p>
			        </div>
			         	
					  <div class="edit_btn"  style="margin-top:70px">
							<input type="button" value="提交" class="bToggle" id="add_submit">
							<input type="button" value="取消" class="bToggle" id="add_cancel" >
						</div>
				
				</div>
				<!-- 		==============================	审核 ======================-->
				<div class="contract_update">
			            <div class="contract_title">审批请假信息</div>
			            <div class="update_close" style="font-size:16px">关闭</div>
			            <div class="basic_info">
			                <div class="table_title">请选择是否通过请假信息</div>
			                <span class="update_id" style="display:none"></span>
			            </div> 
			            <div class="pass">
			            <input type="radio" name="passYN" value="通过" class="ok">通过
			            <input type="radio" name="passYN" value="不通过" class="no">不通过
			            </div>
			             <p class="reason">原因：<input type="text" placeholder="不通过请填写原因" class="ReasonContent" style="display:inline-block;width:72%;height:20px;margin-left:5.5%"></p> 
					  	
					  	<p style="font-size:18px;margin-left:10%">邮箱密码:<input type="text" placeholder="请输入发件人邮箱密码" class="Password2" style="margin-left:1.5%;margin-top:17px;height:22px;width:33%;"><span class="passeye" style="margin-left:1%"></span></p>
					  	<div class="edit_btn">
							<input type="button" value="提交" class="bToggle" id="update_submit">
							<input type="button" value="取消" class="bToggle" id="update_cancel" style="margin-left:20%">
						</div>
				
				</div>
			<!-- 		==============================	修改请假信息 ======================-->
			       <div class="contract_update2">
			            <div class="contract_title">修改请假信息</div>
			            <div class="update_close" style="font-size:16px">关闭</div>
			            <div class="basic_info" style="padding-left:5%">
			                <div class="table_title">请假信息</div>
			                <span class="update_id" style="display:none"></span>
			             <div class="table_body" style="margin-top:15px;margin-bottom:60px;">
			                	<div class="table_col1" style="float:left;width:24%;min-width:24%;padding-right:0%;">
			                		 <span style="float:left;margin-right:9%">部门</span>
			                         <span>
			                         	<select style="width:66%;height:25px;margin-bottom:20px;" name="Department"  id="Department">
			                        	 <option text="请选择">请选择</option>
										  <option text="人事部">人事部</option>
										  <option text="销售部">销售部</option>
										  <option text="商务部">商务部</option>
										  <option text="服务部">服务部</option>
										  <option text="物流部">物流部</option>
										  <option text="市场部">市场部</option>
										  <option text="财务部">财务部</option>
										  <option text="软件部">软件部</option>
										  <option text="硬件部">硬件部</option>
										  <option text="厦门办事处">厦门办事处</option>
										</select>
			                         </span>
			                         <br>
			                        <span style="float:left;margin-right:9%">姓名</span>
			                       <!--   <span><input type="text" name="RealName" value="" id="RealName" class="input_css RealName"></span> -->
			                       <div style="display:inline-block;" class="RealNameBox" >
										<input type="search" class="RealName" style="height:25px"> 
										<ul class="RealNamelist">
										</ul>
								  </div>
			                        <br>
			                         
			                	</div>
			                	<div  class="table_col2" style="float:left;width:28%;min-width:28%;padding-left:5%;padding-right:0%;padding-bottom:30px;border-left:1px solid #ccc;border-right:1px solid #ccc;">
			                		<span style="float:left;margin-right:5%">起始时间</span>
			                        <span><input type="datetime-local" name="start_time" value="" id="start_time" class="input_css start_time" placeholder="xxxx-xx-xx xx:xx"></span>
			                        <br>
			                        <span style="float:left;margin-right:5%">截止时间</span>
			                         <span><input type="datetime-local" name="end_time" value="" id="end_time" class="input_css end_time" placeholder="xxxx-xx-xx xx:xx"></span>
			                        <br>
			                        
			                    </div>
			                	<div  class="table_col3" style="float:left;width:30%;min-width:28%;padding-left:4%;">
			                		<span style="float:left;margin-right:5%">请假类别</span>
			                         <span>
			                         	<select style="width:66%;height:25px;margin-bottom:20px;" name="Classify"  id="Classify">
			                        	 <option text="请选择">请选择</option>
										  <option  text="事假">事假</option>
										  <option  text="病假">病假</option>
										  <option  text="婚假">婚假</option>
										  <option  text="产假">产假</option>
										  <option  text="年休假">年休假</option>
										  <option  text="丧假">丧假</option>
										  <option  text="其他">其他</option>
										</select>
			                         </span>
			                         <br>
			                        <span style="float:left;margin-right:5%">请假事由</span>
			                         <span><textarea cols="28" rows="4" class="Reason"></textarea></span>
			                        <br>
			                        </div>
			                </div>
			                <div class="table_title" style="margin-top:133px;margin-bottom:15px">邮件模板</div>
			                <p style="margin-bottom:15px;float:left" >收件人
			               <!--  <input type="text" class="ToList" style="margin-left:1%;margin-right:8.7%;width:15%;height:22px" value=""> -->
			                        <div style="display:inline-block;margin-left:1.1% ;margin-right:8%" class="ToBox" >
										<input type="search" class="To" style="height:25px"> 
										<ul class="Tolist">
										</ul>
										</div>
			                       	邮箱密码<input type="text" placeholder="请输入申请人邮箱密码" class="Password" style="margin-left:1.5%;height:22px;width:18%;" value=""><span class="passeye" style="margin-left:1%"></span> </p><br>
			                <p><span style="position:absolute;top:308px;">正文</span>
							<textarea type="text" name="MailContent" value="" class="MailContent" style="width:82%;height:100px;margin-left:5%;font-family: Microsoft YaHei;font-size: 14px;" contenteditable="true">XXX，您好！
此处填写请假邮件正文的内容。
祝好！
</textarea></p>
			        </div>
					  <div class="edit_btn" style="margin-top:70px">
							<input type="button" value="提交" class="bToggle" id="update_submit2">
							<input type="button" value="取消" class="bToggle" id="update_cancel2">
						</div>
				
				</div> 

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
						name="lastPage" onclick="LastPage('${pageCounts}')">
				</div>
			</div>
		</div>
	</div>
	<!-- 		==============================	预览 ======================-->
	<div class="MailBar_cover_color"></div>
	<div class="hidePdf" style="display:none;">
			<div id="view" class="news" style="font-family: initial;position:absolute;z-index:11;top:0;left:0;background:#fff;font-size:12px">
    <!-- 页眉-->
   <div class="yemei" style="margin-bottom:20px; margin-top: 20px;">
        <div style="width:100%;height:70px;">
            <div class="logo lf" >
                <img src="image/EOULUlogo.png" style="width: 175px; height: 61px;">
            </div>
            <div class="rt" style="margin-top:40px;color: #000080;">EOULU Technology</div>
        </div>

        <hr>
    </div> 

    <!--尺寸部分-->
    <div id="table_PackSize" style="display:none;" class="table_PackSize">
        <p class="f16" >
           <h2 style="text-align:center;" class="">请假申请单</h2>
           <span class="ReviewContent" style="display:none"></span>
           <span class="Eliminate" style="display:none"></span>
           <span class="classify_add" style="display:none"></span>
            <span class="sT_add" style="display:none"></span>
             <span class="eT_add" style="display:none"></span>
        </p>
        <p><br></p>
        <table cellpadding="0" cellspacing="0" border="1" style=" width:100%; height:80px;margin-bottom: 50px;" class="tc lf">
            <tr class="b" style="height:42px">
                <td colspan="3" style="width:10%">部门</td>
                <td colspan="3" style="width:30%"><p class="Department"></p></td>
                 <td colspan="3" style="width:10%">姓名</td>
                  <td colspan="3" style="width:30%"><p  class="RealName"></p></td>
            </tr>
            <tr style="height:46px" class="type">
                <td colspan="10"><span style="font-weight:bold;float:left;margin-top:0px;margin-left:2%;">请假类别：</span>
                <span style="margin-left:-60px" class="fa fa-square-o">年休假</span>
                <span style="margin-left:30px" class="fa fa-square-o">事假</span>
                <span style="margin-left:30px" class="fa fa-square-o">病假</span>
                <span style="margin-left:30px" class="fa fa-square-o">婚假</span>
                <span style="margin-left:30px" class="fa fa-square-o">产假</span>
                <span style="margin-left:30px" class="fa fa-square-o">丧假</span>
                <span style="margin-left:30px" class="fa fa-square-o">其他</span></td>
            </tr>
            <tr style="height:115px">
                <td colspan="10"><span style="font-weight:bold;float:left;margin-top:-32px;margin-left:2%;">请假事由说明：</span>
                <span  class="GraduateInstitutions"></span></td>
            </tr>
            <tr style="height:45px">
                <td colspan="10"><span style="font-weight:bold;float:left;margin-top:0px;margin-left:2%;">请假时间：</span>
                <span style="position:relative;left:-4%;top:0px" > 自 </span>
                <span style="position:relative;left:-4%;top:0px" class="year1">2000</span>
                <span style="position:relative;left:-4%;top:0px"> 年 </span>
                <span style="position:relative;left:-4%;top:0px" class="month1">00</span>
                <span style="position:relative;left:-4%;top:0px"> 月 </span>
                <span style="position:relative;left:-4%;top:0px" class="day1">00</span>
                <span style="position:relative;left:-4%;top:0px"> 日</span>
                <span style="position:relative;left:-4%;top:0px" class="hour1"> 09:00</span>
                <span style="position:relative;left:-4%;top:0px"> 至 </span>
                <span style="position:relative;left:-4%;top:0px" class="year2">2000</span>
                <span style="position:relative;left:-4%;top:0px"> 年 </span>
                <span style="position:relative;left:-4%;top:0px" class="month2">00</span>
                <span style="position:relative;left:-4%;top:0px"> 月 </span>
                <span style="position:relative;left:-4%;top:0px" class="day2">00</span>
                <span style="position:relative;left:-4%;top:0px"> 日  </span>
                <span style="position:relative;left:-4%;top:0px" class="hour2">18:00</span>
                <span style="position:relative;left:-2%;top:0px">  止 </span>
                <span style="position:relative;left:0%;top:0px">  共请假</span>
                <span style="position:relative;left:0%;top:0px" class="T">2天5小时</span>
                </td>
            </tr>
             <tr style="height:115px">
                <td colspan="10"><span style="font-weight:bold;float:left;margin-top:-32px;margin-left:2%;">部门经理意见：</span>
                <span style="float:left;margin-left:25%;margin-top:-28px" class="fa fa-square-o ok">同意</span>
                <span style="float:left;margin-left:40%;margin-top:-28px" class="fa fa-square-o no">不同意</span>
                <span style="float:left;margin-left:51%;margin-top:-32px">（请述明理由）</span>
                <span style="float:left;margin-left:2%" class="reasonText"><!-- 地球离开你不能运转，你不能走，哼 --> </span>
                <span  style="font-weight:bold;float:right;right:19%;position:relative;top:36px">日期：</span>
                 <span  style="font-weight:bold;float:right;right:35%;position:relative;top:36px">经理签字：</span>
                </td>
            </tr>
             <tr style="height:95px">
                <td colspan="10"><span style="font-weight:bold;float:left;margin-top:-32px;margin-left:2%;">  实际请假时间确认（如有变更）：</span>
                </td>
            </tr>
             <tr style="height:95px">
                <td colspan="10"><span style="font-weight:bold;float:left;margin-top:-16px;margin-left:2%;">  销假日期：</span>
                <br>
                <span style="float:left;margin-left:2%" class="EliminateDate "></span>
                
                <span style="font-weight:bold;float:right;right:15%;position:relative;top:17px" class="RealName">李某某</span>
                <span style="font-weight:bold;float:right;right:19%;position:relative;top:17px">请假人签字:</span>
                </td>
            </tr>
        </table>

        <p class="zhushi" >注：
        </p>
        <p  class="zhushi">
1、请假时间在三天以内的（不包括三天），需提前两天向部门领导申请，并报给人事处留档；如请假时间超过三天以上（包括三天），需提前三天向部门领导及人事部门申请，并由人事部门留档。</p>
        <p  class="zhushi">
2、此请假申请时间规定不包括特殊紧急事宜，例如丧事、突发的疾病等。</p>
     
    </div>
	<!--配置部分-->
    <div id="table_Config" style="display:none;">
        <p class="f16">
            Packing List
            
        </p>
        <p><br></p>

        <table cellpadding="0" cellspacing="0" border="1" style=" width:40%; height:80px;margin-bottom: 20px;" class="tc lf">
            <tr class="b">
                <td colspan="3" >DATE</td>
                <td>PACKING LIST NO.</td>
            </tr>
            <tr>
                <td colspan="3"><p contenteditable="true" class="Date">2017/7/21</p></td>
                <td><p contenteditable="true" class="PackingListNO"></p></td>
            </tr>
        </table>

        <div style="height: 190px;" class="cf">
            <div class="table2 lf">
                <div>
                    <p class="FT b pl10">FROM:</p>
                </div>
                <div style="padding-left: 10px;">
                    <P class="FromApp"></P>
                    <P class="FromAdd MaxW"></P>
                    Tel :<P class="FromATel" style="display:inline-block;"> </P></br>
                    Fax :<P class="FromAFax" style="display:inline-block;"> </P>
                </div>
            </div>
            <div class="table2 rt">
                <div>
                    <p class="FT b pl10">TO:</p>
                </div>
                <div style="padding-left: 10px;padding-top: 10px;">
                    <P contenteditable="true" class="ToApp"></P>
                    <P contenteditable="true" class="ToAdd MaxW"> </p>
                    <p contenteditable="true" class="ToAtt" style="display:none;"></P>
                    <p>Contact:<span contenteditable="true" class="ToContact"></span></p>
                    <p>TEL:<span contenteditable="true" class="ToTEL"></span></p>
                    <p>FAX:<span contenteditable="true" class="ToFAX"></span></p>
                </div>
            </div>
        </div>

    </div>

    <!-- 页脚 -->
   <div class="yejiao cf" style="margin-top: 20px; height:80px;">
        <hr>
        <pre style="text-align: center;color: #000080;font-family: -webkit-body;">EOULU
Suzhou ● Shenzhen ● Beijing ● Shanghai ● HongKong
〡Phone: +86-512-62757360〡Web:www.eoulu.com〡Email:info@eoulu.com〡</pre>

    </div>
</div>
			<!-- <input type="button" value="提交" class="bToggle" id="submit_n" style="position:absolute;z-index:11;top: 250px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
			 -->
			<input type="button" value="导出" class="bToggle ShowOrHide" id="exportPDF1" style="position:absolute;z-index:11;top: 150px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
			<input type="button" value="关闭" class="bToggle " id="contract_close1" style="position: absolute;z-index: 11;top: 100px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
	</div>
	
</body>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/swiper-3.4.1.jquery.min.js" type="text/javascript" charset="utf-8"></script>
<!-- <script src="js/ajaxfileupload.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/msgbox.js"></script>
<!-- <script src="js/msgbox_unload.js"></script> -->
<script src="js/LeaveApplication.js"></script>
<script type="text/javascript">
var LeaveApp_year = $("#LeaveApp_year").text();
var LeaveApp_month = $("#LeaveApp_month").text();
var baseUrl = window.location.href.split("cfChicken8")[0]+"cfChicken8/";
//小眼睛
$(".contract_add span.passeye").click(function(){
    if ($(".contract_add .Password").attr("type") == "text") {
        $(".contract_add .Password")[0].type = "password";  
    }else{
      $(".contract_add .Password")[0].type = "text"; 
    }   
});
$(".contract_update2 span.passeye").click(function(){
    if ($(".contract_update2 .Password").attr("type") == "text") {
        $(".contract_update2 .Password")[0].type = "password";  
    }else{
      $(".contract_update2 .Password")[0].type = "text"; 
    }   
});
$(".contract_update .passeye").click(function(){
    if ($(".contract_update .Password2").attr("type") == "text") {
        $(".contract_update .Password2")[0].type = "password";  
    }else{
      $(".contract_update .Password2")[0].type = "text"; 
    }   
});
/****************** 跳页 **********************/
function FistPage(arg) {
	if(!LeaveApp_year || !LeaveApp_month){
		window.location.href = 'LeaveApplication?currentPage=1';
	}else{
		window.location.href = 'LeaveApplication?currentPage=1&Year='+LeaveApp_year+'&Month='+LeaveApp_month;
	}
}
function UpPage(arg) {
	if(!LeaveApp_year || !LeaveApp_month){
		window.location.href = 'LeaveApplication?currentPage='+arg;
	}else{
		window.location.href = 'LeaveApplication?currentPage='+arg+'&Year='+LeaveApp_year+'&Month='+LeaveApp_month;
	}
}
function NextPage(arg) {
	if(!LeaveApp_year || !LeaveApp_month){
		window.location.href = 'LeaveApplication?currentPage='+arg;
	}else{
		window.location.href = 'LeaveApplication?currentPage='+arg+'&Year='+LeaveApp_year+'&Month='+LeaveApp_month;
	}
}
function PageJump(arg) {
	var jumpNumber = document.getElementById("jumpNumber").value;
	if (jumpNumber == null || jumpNumber == 0) {
		jumpNumber = $('#currentPage').html();
	} else if (jumpNumber > parseInt($('#allPage').html())) {
		jumpNumber = $('#allPage').html();
	}
	if(!LeaveApp_year || !LeaveApp_month){
		window.location.href = 'LeaveApplication?currentPage='+jumpNumber;
	}else{
		window.location.href = 'LeaveApplication?currentPage='+jumpNumber+'&Year='+LeaveApp_year+'&Month='+LeaveApp_month;
	}
}
function LastPage(arg) {
	if(!LeaveApp_year || !LeaveApp_month){
		window.location.href = 'LeaveApplication?currentPage='+arg;
	}else{
		window.location.href = 'LeaveApplication?currentPage='+arg+'&Year='+LeaveApp_year+'&Month='+LeaveApp_month;
	}
	/* var jumpNumber = parseInt($('#allPage').html()); */
}

function getDataByYM(YY, MM){
	if(!YY){
		YY = new Date().getFullYear().toString();
	}
	if(!MM){
		MM = "All";
	}
	var LeaveApp_currentPage = $("#LeaveApp_currentPage").text();
	if(!LeaveApp_currentPage){
		LeaveApp_currentPage = 1;
	}
	var baseUrl = window.location.href.split(globalProjectName+"/")[1];
	var Url;
	if(baseUrl.indexOf("currentPage")>-1){
		Url = 'LeaveApplication?currentPage='+LeaveApp_currentPage+'&Year='+YY+'&Month='+MM;
	}else{
		Url = 'LeaveApplication?Year='+YY+'&Month='+MM;
	}
	window.location.href = Url;
}

$(".search_div input[value='搜索']").click(function(){
	var YY = $("select#search_Y").val();
	var MM = $("select#search_M").val();
	getDataByYM(YY, MM);
});

$(".search_div input[value='取消']").click(function(){
	window.location.assign(baseUrl+"LeaveApplication");
});

$(function() {
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
	var curY = new Date().getFullYear();
	var ii = 2016;
	do{
	    var str1 = '<option value="'+ii+'">'+ii+'年</option>';
	    $("select#search_Y").append(str1);
	    ii++;
	}while (ii < curY+1); 
	
	var iii = 1;
	do{
		if(iii<10){
			iii = "0"+iii;
		}
	    var str2 = '<option value="'+iii+'">'+iii+'月</option>';
	    $("select#search_M").append(str2);
	    iii = Number(iii);
	    iii++;
	}while (iii < 13);

	var curUrl = window.location.href;
	var YYYY;
	var MMMM;
	if(curUrl.indexOf("Year")>-1){
		YYYY = curUrl.split("Year=")[1].indexOf("&")>-1 ? curUrl.split("Year=")[1].split("&")[0]:curUrl.split("Year=")[1];
	}
	if(curUrl.indexOf("Month")>-1){
		MMMM = curUrl.split("Month=")[1].indexOf("&")>-1 ? curUrl.split("Month=")[1].split("&")[0]:curUrl.split("Month=")[1];
	}
	$("select#search_Y").val(YYYY);
	$("select#search_M").val(MMMM);

	// var MMM = new Date().getMonth() < 10 ? "0"+new Date().getMonth():new Date().getMonth();
	// getDataByYM(curY, MMM);
});

$(".choose input[value='导出']").click(function(){
	var iYear = $("select#search_Y").val();
	var iMonth = $("select#search_M").val();
	if(!iYear) iYear = "";
	if(!iMonth) iMonth = "";
	$.ajax({
	    type: 'POST',
	    url: 'LeaveApplicationExport',
	    data: {
	        Year: iYear,
	        Month: iMonth
	    },
	    dataType: 'text',
	    beforeSend: function(XMLHttpRequest){
	        $(".choose input[value='导出']").css("cursor","not-allowed").prop("disabled","disabled");
	    },
	    complete: function(XMLHttpRequest, textStatus){
	        if(textStatus=='success'){
	        }else if(textStatus=='error'){
	        }else if(textStatus=='timeout'){
	            var xmlhttp = window.XMLHttpRequest ? new window.XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHttp");  
	            xmlhttp.abort();
	        }
	        $(".choose input[value='导出']").css("cursor","pointer").prop("disabled",false);
	    },
	    success: function (data) {
	    	if(data == "false"){
	    		$.MsgBox_Unload.Alert("提示", "导出失败！");
	    	}else{
		        window.location.assign(baseUrl+data);
		        // window.open(baseUrl+data);
	    	}
	    },
	    error: function () {
	        $.MsgBox_Unload.Alert("提示", "服务器繁忙，请稍后重试！");
	    }
	  });
});

</script>
</html>