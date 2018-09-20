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
<title>员工信息</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<!-- <link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css"> -->
<link rel="stylesheet" href="css/libs/bootstrap-grid-form-btn-res-icon-tooltip-popover.min.css" type="text/css">
<link rel="stylesheet" href="css/global/eouluCustom.css" type="text/css">
<link rel="stylesheet" type="text/css" href="css/StaffInfo.css?iv=201809201122">
</head>
<body>
	<div id="originfactory_wrapper">
		<div id="originfactory_sticker">
			<div id="originfactory_sticker-con">
				<!-- 	头部开始 -->
				<%@include file="top.jsp"%>
				<!-- 	头部结束 -->
				<div class="contain">
					<div class="content">
					<!-- 	=======================导航栏   开始 ================================== -->
					<%@include file="nav.jsp"%>
			<!-- 	=======================导航栏   结束 ================================== -->
				<form id="top_text_from" name="top_text_from" method="GET" action="StaffInfo">
							<!-- <input type="text" id="search" name="isSearch" value="search"
								style="display: none;"> -->
							<div class="select-content">
								<label> <c:choose>
										<c:when test="${queryType=='maxQuery'}">
											<label><input type="radio" id="singleSelect"
												name="queryType" class="singleSelect" value="singleQuery"
												onclick="Check(this.value)">单一查询 </label>&nbsp;&nbsp;&nbsp;<label>
												<input type="radio" id="mixSelect" name="queryType"
												value="maxQuery" checked="checked" onclick="Check(this.value)">组合查询
											</label>&nbsp;&nbsp;&nbsp;<br>
										</c:when>
										<c:otherwise>
											<label><input type="radio" id="singleSelect"
												name="queryType" class="singleSelect" value="singleQuery"
												checked="checked" onclick="Check(this.value)">单一查询 </label>&nbsp;&nbsp;&nbsp;<label>
												<input type="radio" id="mixSelect" name="queryType"
												value="maxQuery" onclick="Check(this.value)">组合查询
											</label>&nbsp;&nbsp;&nbsp;<br>
										</c:otherwise>
									</c:choose> <c:set var="dropdown" value="${fn:split('姓名,所属部门,职位,工作地',',')}"></c:set>
									<div class="select1">
										<select name="field1" id="type1">
											<c:forEach items="${dropdown }" var="dropdownList1"
												varStatus="status">
												<c:choose>
													<c:when test="${dropdownList1==field1}">
														<option selected="selected">${dropdownList1}</option>
													</c:when>
													<c:otherwise>
														<option>${dropdownList1}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
										 <input type="text" id="searchContent1" name="content1" value="${content1}">
									</div>
									<!-- 第二个搜索框 -->
								<c:if test="${queryType !='maxQuery'}">
									<div class="select2" style="display: none">
										<select name="field2" id="type2">
											<c:forEach items="${dropdown }" var="dropdownList2"
												varStatus="status">
												<c:choose>
													<c:when test="${dropdownList2==field2}">
														<option selected="selected">${dropdownList2}</option>
													</c:when>
													<c:otherwise>
														<option>${dropdownList2}</option>
													</c:otherwise>	
												</c:choose>
											</c:forEach>
										</select>
											<input type="text" id="searchContent2" name="content2" value="${content2}">
									</div>
									</c:if>
									<c:if test="${queryType =='maxQuery'}">
									<div class="select2" style="display: inline-block">
										<select name="field2" id="type2">
											<c:forEach items="${dropdown }" var="dropdownList2" varStatus="status">
												<c:choose>
													<c:when test="${dropdownList2==field2}">
														<option selected="selected">${dropdownList2}</option>
													</c:when>
													<c:otherwise>
														<option>${dropdownList2}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
											<input type="text" id="searchContent2" name="content2" value="${content2}">
									</div>
								</c:if>
									<div class="select-button">
										<input type="button" value="搜索" class="bToggle" onclick="INSearch()"> 
										<input type="button" value="取消" class="bToggle" onclick="Cancel()">
									</div>
							</div> 
							<div class="choose">
								<input type="button" value="添加" class="bToggle" onclick="AddContract()">
								<input type="button" value="导出" class="bToggle export" style="margin-left:20px">
							</div>
					 </form>  
						<!-- <form id="top_text_from" name="top_text_from" method="post"
							action="GetMachineDetailsRoute"> -->
							<!-- <div class="choose">
								<input type="button" value="添加" class="bToggle" onclick="AddContract()">
								<input type="button" value="导出" class="bToggle export" style="margin-left:20px">
							</div> -->
						<!--  </form>   -->
					<%--  <span>${queryType}</span><span>${content1}</span><span>${content2}</span> --%>
					<div class="wrap_table">
						<table id="table1">
							<tr style="background:#bfbfbf">
								<td class="t11">序号</td>
								<td class="t12">姓名<span class="glyphicon glyphicon-plus" aria-hidden="true" id="more_info"></span></td>
								<td class="t13">联系手机 </td>
								<td class="t14">籍贯</td>
								<td class="t15">毕业院校</td>
								<td class="t16">政治面貌</td>
								<td class="t17">入职时间</td>
								<td class="t18">职位</td>
								<td class="t19">所属部门</td>
								<td class="t20">工作地</td>
								<td style="display:none" class="td_IDCard t21">身份证号</td>
								<td style="display:none">民族</td>
								<td style="display:none">专业</td>
								<td style="display:none">家庭住址</td>
								<td style="display:none">员工编码</td>
								<td style="display:none">公司邮箱</td>
								<td style="display:none" class="td_Passport t22">护照号</td>
								<td style="display:none">学历</td>
								<td style="display:none">性别</td>
								<td style="display:none" class="td_AccountNumber t23">银行卡号</td>
								<td style="display:none" class="td_DepositBank t24">开户行</td>
								<td style="display:none" class="td_reminded t25">定时提醒</td>
								<td style="display:none" class="td_Remarks t26">备注</td>
								<td style="display:none" class="td_IDCardFile t27">身份证照片</td>
								<td style="display:none" class="td_PassportFile t28">护照照片</td>
							</tr>
							
							<c:forEach var="orderInfo" items="${datas}" varStatus="status">
								<c:if test="${status.index>0}">
									<tr style="background:#fff" class="tbody_tr">
										<td value="${orderInfo['ID']}" class="contract-edit t11" style="cursor:pointer;width:60px;max-width:60px">${status.index+(currentPage-1)*10}</td>
										<td class="StaffName t12" >${orderInfo['StaffName']}</td>
										<td class="LinkTel t13" style="max-width:170px">${orderInfo['LinkTel']}</td>
										<td title="${orderInfo['NativePlace']}"  class="NativePlace t14"  style="width:120px;max-width:120px">${orderInfo['NativePlace']}</td>
										<td title="${orderInfo['GraduateInstitutions']}"  class="GraduateInstitutions t15">${orderInfo['GraduateInstitutions']}</td>
										<td class="PoliticalStatus t16">${orderInfo['PoliticalStatus']}</td>
										<td class="EntryDate t17">${orderInfo['EntryDate']}</td>
										<td class="Job t18">${orderInfo['Job']}</td>
										<td class="Department t19" style="width:115px;max-width:115px">${orderInfo['Department']}</td>
										<td class="WorkPlace t20">${orderInfo['WorkPlace']}</td>
										<td style="display:none" class="IDCard t21" >${orderInfo['IDCard']}</td>
										<td style="display:none" class="Nation" >${orderInfo['Nation']}</td>
										<td style="display:none" class="Major" >${orderInfo['Major']}</td>
										<td style="display:none" class="DetailAddress" >${orderInfo['DetailAddress']}</td>
										<td style="display:none" class="StaffCode" >${orderInfo['StaffCode']}</td>
										<td style="display:none" class="Other" >${orderInfo['StaffMail']}</td>
										<td style="display:none" class="Passport t22" >${orderInfo['Passport']}</td>
										<td style="display:none" class="EducationalBackground" >${orderInfo['EducationalBackground']}</td>
										<td style="display:none" class="Gender" >${orderInfo['Gender']}</td>
										<td style="display:none" title="${orderInfo['AccountNumber']}" class="AccountNumber t23">${orderInfo['AccountNumber']}</td>
										<td style="display:none" title="${orderInfo['DepositBank']}" class="DepositBank t24">${orderInfo['DepositBank']}</td>
										<td style="display:none" class="reminded t25"></td>
										<td style="display:none" title="${orderInfo['Remarks']}" class="Remarks t26">${orderInfo['Remarks']}</td>
										<td style="display:none" class="IDCardFile t27" title="${orderInfo['IDCardFile']}"></td>
										<td style="display:none" class="PassportFile t28" title="${orderInfo['PassportFile']}"></td>
									</tr>
								</c:if>
							</c:forEach> 
						</table>
					</div>
						<div class="cover-color"></div>
						<div class="cover-color2"></div>
						<!-- 添加工程师行程信息 -->
						        <div class="contract_add" style="display: none;">
						            <div class="contract_title">添加员工信息</div>
						            <div class="contractAdd_close">关闭</div>
						            <div class="basic_info">
						                <div class="table_title">员工信息</div>
						                <!-- 修改添加 -->
						                <div class="table_body">
						                	<div class="table_col1" style="float:left;width:24%;min-width:24%;padding-right:5%;">
						                		<span style="float:left;margin-right:5%;">员工姓名</span>
						                        <!-- <span><input type="text" name="StaffName" value="" id="StaffName" class="input_css StaffName"></span> -->
						                         <div style="display:inline-block;" class="RealNameBox" >
													<input type="search" class="RealName" style="width:100%;height:25px;"> 
													<ul class="RealNamelist">
													</ul>
												</div>
												<span style="width:100%;height:20px;display:inline-block"></span>
						                        <br>
						                        <span style="float:left;margin-right:5%">身份证号</span>
						                         <span><input type="text" name="IDCard" value="" id="IDCard" class="input_css IDCard"></span>
						                        <br>
						                         <span style="float:left;margin-right:10%">护照号</span>
						                         <span><input type="text" name="Passport" value="" id="Passport" class="input_css Passport"></span>
						                        <br>
						                        <span style="float:left;margin-right:16%">民族</span>
						                         <span><input type="text" name="Nation" value="" id="Nation" class="input_css Nation"></span>
						                        <br>
						                        <span style="float:left;margin-right:16%">籍贯</span>
						                         <span><input type="text" name="NativePlace" value="" id="NativePlace" class="input_css NativePlace"></span>
						                        <br>
						                        <span style="float:left;margin-right:5%">政治面貌</span>
						                         <span><input type="text" name="PoliticalStatus" value="" id="PoliticalStatus" class="input_css PoliticalStatus"></span>
						                        <br>
						                        <span style="float:left;margin-right:5%">银行卡号</span>
						                        <span><input type="text" id="add_AccountNumber" class="input_css"></span>
						                        <br>
						                	</div>
						                	<div class="table_col2" style="float:left;width:28%;min-width:28%;padding-left:5%;padding-right:3%;border-left:1px solid #dadada;border-right:1px solid #dadada;">
						                		<span style="float:left;margin-right:5%">联系手机</span>
						                        <span><input type="text" name="LinkTel" value="" id="LinkTel" class="input_css LinkTel"></span>
						                        <br>
						                        <span style="float:left;margin-right:5%">毕业院校</span>
						                         <span><input type="text" name="GraduateInstitutions" value="" id="GraduateInstitutions" class="input_css GraduateInstitutions"></span>
						                        <br>
						                        <span style="float:left;margin-right:14%">专业</span>
						                         <span><input type="text" name="Major" value="" id="Major" class="input_css Major"></span>
						                        <br>
						                        <span style="float:left;margin-right:14%">学历</span>
						                        <!--  <span><input type="text" name="EducationalBackground" value="" id="EducationalBackground" class="input_css EducationalBackground"></span> -->
						                       <span>
						                         	<select style="width:66%;height:25px;margin-bottom:20px;" name="EducationalBackground"  id="EducationalBackground">
						                        	 <option text="请选择">请选择</option>
													  <option text="博士">博士</option>
													  <option text="硕士">硕士</option>
													  <option text="本科">本科</option>
													  <option text="专科">专科</option>
													  <option text="高中">高中</option>
													  <option text="初中">初中</option>
													</select>
						                         </span>
						                        <br>
						                        <span style="float:left;margin-right:5%">家庭住址</span>
						                         <span><input type="text" name="DetailAddress" value="" id="DetailAddress" class="input_css DetailAddress"></span>
						                        <br>
						                        <span style="float:left;margin-right:9.5%">工作地</span>
						                         <span><input type="text" name="WorkPlace" value="" id="WorkPlace" class="input_css WorkPlace"></span>
						                        <br>
						                        <span style="float:left;margin-right:9.5%">开户行</span>
						                        <span><input type="text" id="add_DepositBank" class="input_css"></span>
						                        <br>
						                    </div>
						                	<div  class="table_col3" style="float:left;width:28%;min-width:28%;padding-left:4%;">
						                		<span style="float:left;margin-right:14%">性别</span>
						                        <span>
						                         	<select style="width:66%;height:25px;margin-bottom:20px;" name="Gender"  id="Gender">
						                        	 <option text="请选择">请选择</option>
													  <option  text="男">男</option>
													  <option  text="女">女</option>
													</select>
						                         </span>
						                        <br>
						                		<span style="float:left;margin-right:5%">入职时间</span>
						                        <span><input type="date" name="EntryDate" value="" id="EntryDate" class="input_css EntryDate"></span>
						                        <br>
						                        <span style="float:left;margin-right:14%">职位</span>
						                         <span><input type="text" name="Job" value="" id="Job" class="input_css Job"></span>
						                        <br>
						                        <span style="float:left;margin-right:5%">所属部门</span>
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
													  <option text="标准服务部">标准服务部</option>
													  <option text="应用部">应用部</option>
													  <option text="研发部">研发部</option>
													  <option text="厦门办事处">厦门办事处</option>
													</select>
						                         </span>
						                        <br>
						                        <span style="float:left;margin-right:5%">员工编码</span>
						                         <span><input type="text" name="StaffCode" value="" id="StaffCode" class="input_css StaffCode"></span>
						                        <br>
						                        <span style="float:left;margin-right:5%">公司邮箱</span>
						                         <span><input type="email" name="Other" value="" id="Other" class="input_css Other"></span>
						                        <br>
						                        <span style="float:left;margin-right:14%">备注</span>
						                        <span><input type="text" id="add_Remarks" class="input_css"></span>
						                        <br>
						                        </div>
						                </div>
						                <div class="add_idcard_div">
						                	<div class="flex_1">身份证</div>
						                	<div class="flex_2">
						                		<input type="text" readonly="readonly" disabled="disabled">
						                	</div>
						                	<div class="flex_3">
						                		<input type="button" class="bToggle" value="上传">
						                	</div>
						                </div>
						                <div class="add_passport_div">
						                	<div class="flex_1">护照</div>
						                	<div class="flex_2">
						                		<input type="text" readonly="readonly" disabled="disabled">
						                	</div>
						                	<div class="flex_3">
						                		<input type="button" class="bToggle" value="上传">
						                	</div>
						                </div>
						        	</div>
								  	<div class="edit_btn">
										<input type="button" value="保存员工信息" class="bToggle" id="add_submit" style="width: 110px;height:30px;">
										<!-- <input type="button" value="取消" class="bToggle" id="add_cancel"> -->
									</div>
								</div>
						<!-- 修改信息 -->
						     	<div class="contract_update" style="display: none;">
						            <div class="contract_title">修改员工信息</div>
						            <div class="update_close" style="font-size:16px">关闭</div>
						            <div class="basic_info">
						                <div class="table_title">员工信息</div>
						                <span class="update_id" style="display:none"></span>
						                <!-- 修改添加 -->
						                <div class="table_body">
						                	<div class="table_col1" style="float:left;width:24%;min-width:24%;padding-right:5%;">
						                		<span style="float:left;margin-right:5%">员工姓名</span>
						                        <!-- <span><input type="text" name="StaffName" value="" id="StaffName" class="input_css StaffName"></span> -->
						                        <div style="display:inline-block;" class="RealNameBox" >
													<input type="search" class="RealName" style="width:100%;height:25px;"> 
													<ul class="RealNamelist">
													</ul>
												</div>
												<span style="width:100%;height:20px;display:inline-block"></span>
						                        <br>
						                        <span style="float:left;margin-right:5%">身份证号</span>
						                         <span><input type="text" name="IDCard" value="" id="IDCard" class="input_css IDCard"></span>
						                        <br>
						                         <span style="float:left;margin-right:10%">护照号</span>
						                         <span><input type="text" name="Passport" value="" id="Passport" class="input_css Passport"></span>
						                        <br>
						                        <span style="float:left;margin-right:16%">民族</span>
						                         <span><input type="text" name="Nation" value="" id="Nation" class="input_css Nation"></span>
						                        <br>
						                        <span style="float:left;margin-right:16%">籍贯</span>
						                         <span><input type="text" name="NativePlace" value="" id="NativePlace" class="input_css NativePlace"></span>
						                        <br>
						                        <span style="float:left;margin-right:5%">政治面貌</span>
						                         <span><input type="text" name="PoliticalStatus" value="" id="PoliticalStatus" class="input_css PoliticalStatus"></span>
						                        <br>
						                        <span style="float:left;margin-right:5%">银行卡号</span>
						                        <span><input type="text" id="update_AccountNumber" class="input_css"></span>
						                        <br>
						                	</div>
						                	<div  class="table_col2" style="float:left;width:28%;min-width:28%;padding-left:5%;padding-right:3%;border-left:1px solid #dadada;border-right:1px solid #dadada;">
						                		<span style="float:left;margin-right:5%">联系手机</span>
						                        <span><input type="text" name="LinkTel" value="" id="LinkTel" class="input_css LinkTel"></span>
						                        <br>
						                        <span style="float:left;margin-right:5%">毕业院校</span>
						                         <span><input type="text" name="GraduateInstitutions" value="" id="GraduateInstitutions" class="input_css GraduateInstitutions"></span>
						                        <br>
						                        <span style="float:left;margin-right:14%">专业</span>
						                         <span><input type="text" name="Major" value="" id="Major" class="input_css Major"></span>
						                        <br>
						                        <span style="float:left;margin-right:14%">学历</span>
						                        <!--  <span><input type="text" name="EducationalBackground" value="" id="EducationalBackground" class="input_css EducationalBackground"></span> -->
						                        <span>
						                         	<select style="width:66%;height:25px;margin-bottom:20px;" name="EducationalBackground"  id="EducationalBackground">
						                        	 <option text="请选择">请选择</option>
													  <option  text="博士">博士</option>
													  <option  text="硕士">硕士</option>
													  <option  text="本科">本科</option>
													  <option  text="专科">专科</option>
													  <option  text="高中">高中</option>
													  <option  text="初中">初中</option>
													</select>
						                         </span>
						                        <br>
						                        <span style="float:left;margin-right:5%">家庭住址</span>
						                         <span><input type="text" name="DetailAddress" value="" id="DetailAddress" class="input_css DetailAddress"></span>
						                        <br>
						                        <span style="float:left;margin-right:9.5%">工作地</span>
						                         <span><input type="text" name="WorkPlace" value="" id="WorkPlace" class="input_css WorkPlace"></span>
						                        <br>
						                        <span style="float:left;margin-right:9.5%">开户行</span>
						                        <span><input type="text" id="update_DepositBank" class="input_css"></span>
						                        <br>
						                    </div>
						                	<div  class="table_col3" style="float:left;width:28%;min-width:28%;padding-left:4%;">
						                		<span style="float:left;margin-right:14%">性别</span>
						                        <span>
						                         	<select style="width:66%;height:25px;margin-bottom:20px;" name="Gender"  id="Gender">
						                        	 <option text="请选择">请选择</option>
													  <option  text="男">男</option>
													  <option  text="女">女</option>
													</select>
						                         </span>
						                        <br>
						                		<span style="float:left;margin-right:5%">入职时间</span>
						                        <span><input type="date" name="EntryDate" value="" id="EntryDate" class="input_css EntryDate"></span>
						                        <br>
						                        <span style="float:left;margin-right:14%">职位</span>
						                         <span><input type="text" name="Job" value="" id="Job" class="input_css Job"></span>
						                        <br>
						                        <span style="float:left;margin-right:5%">所属部门</span>
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
													  <option text="标准服务部">标准服务部</option>
													  <option text="应用部">应用部</option>
													  <option text="研发部">研发部</option>
													  <option text="厦门办事处">厦门办事处</option>
													</select>
						                         </span>
						                        <br>
						                        <span style="float:left;margin-right:5%">员工编码</span>
						                         <span><input type="text" name="StaffCode" value="" id="StaffCode" class="input_css StaffCode"></span>
						                        <br>
						                        <span style="float:left;margin-right:5%">公司邮箱</span>
						                         <span><input type="email" name="Other" value="" id="Other" class="input_css Other"></span>
						                        <br>
						                        <span style="float:left;margin-right:14%">备注</span>
						                        <span><input type="text" id="update_Remarks" class="input_css"></span>
						                        <br>
						                        </div>
						                </div>
						                <div class="update_idcard_div">
						                	<div class="flex_1">身份证</div>
						                	<div class="flex_2">
						                		<input type="text" readonly="readonly" disabled="disabled">
						                	</div>
						                	<div class="flex_3">
						                		<input type="button" class="bToggle" value="上传">
						                	</div>
						                </div>
						                <div class="update_passport_div">
						                	<div class="flex_1">护照</div>
						                	<div class="flex_2">
						                		<input type="text" readonly="readonly" disabled="disabled">
						                	</div>
						                	<div class="flex_3">
						                		<input type="button" class="bToggle" value="上传">
						                	</div>
						                </div>
						        	</div>
								  	<div class="edit_btn">
										<input type="button" value="修改员工信息" class="bToggle" id="update_submit" style="width: 110px;height:30px;">
										<input type="button" value="删除员工信息" class="bToggle" id="update_cancel" style="width: 110px;height:30px;background:#ef9800;border: solid 1px #ef9800;box-shadow: 1px 2px 5px 0 #ef9800;">
									</div>
								</div>
						<c:choose>
							<c:when test="${queryType == 'singleQuery'}">
								<c:set var="queryUrl"
								value="StaffInfo?queryType=${queryType}&field1=${field1}&content1=${content1}&currentPage=">
								</c:set>
							</c:when>
							<c:when test="${queryType == 'maxQuery'}">
								<c:set var="queryUrl" value="StaffInfo?queryType=${queryType}&field1=${field1}&content1=${content1}&field1=${field2}&content1=${content2}&currentPage=">
								</c:set>
							</c:when>
							<c:otherwise>
								<c:set var="queryUrl" value="StaffInfo?currentPage=">
								</c:set>
							</c:otherwise>
						</c:choose>
						 	
						<div id="page">
							<div class="pageInfo">
								当前是第&nbsp;<span id="currentPage">${currentPage}</span>&nbsp;页,&nbsp;总计&nbsp;<span id="allPage">${pageCounts}</span>页
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
									name="lastPage" onclick="LastPage('${queryUrl}${pageCounts}')">
							</div>
						</div>
						<div class="clear_float"></div>
						<!-- 上传组件 -->
						<div class="dropFileBox" style="display: none">
						    <div class="dropFile">
						        <div class="dropFileTit">
						            上传文件<span>关闭</span>
						        </div>
						        <div class="dropFileCon">
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
						    </div>
						</div>
					</div>
				</div>
			<!-- originfactory_sticker-con结束 -->
			</div>
		<!-- originfactory_sticker结束 -->
		</div>
		<!-- originfactory_footer -->
		<div id="originfactory_footer">
			<div id="eoulu-copy-out" style="height:40px;width:calc(100% - 2px);">
				<div style="width:100%;height:5px;"></div>
				<div id="eoulu-copy" style="width:100%;height:35px;font-size:12px;color:#888;line-height:35px;z-index: 2;">
					<hr style="height:1px;color:#999;width: calc(100% - 3px);" />
					<div style="width:100%;text-align:center;display:inline-block;">Copyright  ©&nbsp;<span class="YEAR">2018</span>&nbsp<a href="http://www.eoulu.com/h-col-268.html" class="EHref" target="_blank" style="color:blue;">Eoulu</a> Tech. Co.,Ltd.</div>
				</div>
			</div>
		</div>
	<!-- originfactor_wrapper结束	 -->
	</div>
</body>
<script src="js/libs/bootstrap/bootstrap-grid-form-btn-res-icon-tooltip-popover.min.js"></script>
<script src="js/msgbox.js"></script>
<script src="js/StaffInfo.js?iv=201809201122"></script>
<script type="text/javascript">
/****************** 跳页 **********************/
function FistPage(arg) {
	/* window.location.href = 'StaffInfo?currentPage=1'; */
	window.location.href = arg+1;
}
function UpPage(arg) {
	/* window.location.href = 'StaffInfo?currentPage='+arg; */
	window.location.href = arg;
}
function NextPage(arg) {
	/* window.location.href = 'StaffInfo?currentPage='+arg; */
	window.location.href = arg;
}
function PageJump(arg) {
	var jumpNumber = document.getElementById("jumpNumber").value;
	if (jumpNumber == null || jumpNumber == 0) {
		jumpNumber = $('#currentPage').html();
	} else if (jumpNumber > parseInt($('#allPage').html())) {
		jumpNumber = $('#allPage').html();
	}
/* 	window.location.href = 'StaffInfo?currentPage='+jumpNumber; */
	window.location.href = arg+jumpNumber;
}
function LastPage(arg) {
	/* var jumpNumber = parseInt($('#allPage').html()); */
	/* window.location.href = 'StaffInfo?currentPage='+arg; */
	window.location.href = arg;
}

// remindMap
var remindedMap = {};
for(var tt = 0;tt<100;tt++){
	if(tt==0){
		remindedMap[tt] = "";
		continue;
	}
	if(tt%4 != 0){
		remindedMap[tt] = "已入职"+tt*3+"个月";
	}else{
		var numY = tt/4;
		remindedMap[tt] = "已入职"+numY+"年";
	}
}
function getRemindedMapText(item){
	for(var k in remindedMap){
		if(k==item){
			return remindedMap[k];
		}
	}
}

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

	// 文件IDCardFile t27PassportFile t28
	$(".IDCardFile.t27, .PassportFile.t28").each(function(){
		var curFilePath = $(this).attr("title");
		if (curFilePath==""||curFilePath=="--") return true;
		var FilePathArr = curFilePath.split(";");
		var str = '';
		FilePathArr = $.grep(FilePathArr,function(currentValue,index){
			return currentValue != "";
		});
		var len = FilePathArr.length - 1;
		FilePathArr.map(function(currentValue,index,arr){
			if(index<len){
				str+='<span class="downFile_span" title="'+currentValue+'">'+currentValue+'</span><br/>';
			}else{
				str+='<span class="downFile_span" title="'+currentValue+'">'+currentValue+'</span>';
			}
		});
		/* 当参考
		if(curFilePath.charAt(0)==";"){
			var newFilePathArr = [ ...FilePathArr];
			newFilePathArr = newFilePathArr.slice(1);
			newFilePathArr.map(function(currentValue,index,arr){
				if(currentValue != ""){
					var len = arr.length - 1;
					if(index<len){
						str+='<span class="downFile_span">'+currentValue+'</span><br/>';
					}else{
						str+='<span class="downFile_span">'+currentValue+'</span>';
					}
				}
			});
		}else{
			FilePathArr.map(function(currentValue,index,arr){
				if(currentValue != ""){
					var len = arr.length - 1;
					if(index<len){
						str+='<span class="downFile_span">'+currentValue+'</span><br/>';
					}else{
						str+='<span class="downFile_span">'+currentValue+'</span>';
					}
				}
			});
		}
		*/
		$(this).html(str);
	});
	$(document).on("click",".downFile_span",function(e){
		var fileName = $(this).attr("title");
		var form11 = $("<form method='post'><input type='text' name='FileName' value='"+fileName+"'></form>");
		form11.attr({"action":"StaffInfo"});
		form11.appendTo(document.body);
		form11.submit();
		document.body.removeChild(form11[0]);
		// var baseHref = window.location.href.split("cfChicken8")[0]+"LogisticsFile/File/";
		// e.stopPropagation();
		// window.location.href = baseHref+fileName;
	});

	// 定时提醒reminded t25
	$(".reminded.t25").each(function(){
		var inCompanyTime = $(this).siblings(".EntryDate.t17").text();
		if(inCompanyTime==""||inCompanyTime=="--") return true;
		var remarkText = $(this).siblings(".Remarks.t26").text();
		if(remarkText==""||remarkText=="--"){
			var gapTime = parseInt(globalCalcTimeDiff(inCompanyTime, globalGetToday()));
			var gapMY = Math.floor(gapTime/90);
			var gapMYText = getRemindedMapText(gapMY);
			$(this).text(gapMYText).attr({"value": gapTime, "title": gapMYText}).addClass("remindInText");
		}
	});
});
</script>
</html>