<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<!-- 为移动设备添加 viewport -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
<title>需求统计</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" />
<link rel="stylesheet" type="text/css"
	href="font-awesome-4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" href="./css/global/icon.css" type="text/css">
<link rel="stylesheet" type="text/css" href="css/requirement.css">
<style>
	.content {
		padding-bottom: 100px !important;
	}
</style>
</head>
<body>
	<%-- ${areasStatics }<br/> --%>
	<%@include file="top.jsp"%>
	<div class="contain">
		<div class="content">
		<%@include file="nav.jsp"%>	
			<div style="display: none">
				<input type="text" value="" name="query_type"> <input
					type="text" value="" name="classify1"> <input type="text"
					value="" name="classify2"> <input type="text" value=""
					name="parameter1"> <input type="text" value=""
					name="parameter2">
			</div>
			<!-- 新增功能框 -->
			<div class="prompt">
				<div class="prompt-content">
					<div class="prompt-top">
						<ul>
							<li class="prompt-top1"><!-- 18年度需求情况 --></li>
							<li class="recordbtn" data-value="DemandTab" style="font-size: 16px;margin-left: 35px;cursor:pointer;">&nbsp;详细记录</li>
							<li class="recordbtn" data-value="CityMap" style="font-size: 16px;margin-left: 35px;cursor:pointer;">&nbsp;地图数据</li>
						</ul>
					</div>
					<div class="prompt-middle">
						<div class="proClick">
							<ol>
								<li class="pC-li0" style="cursor: auto;">选择区域单独查看</li>
								<li class="pC-li1"><b class="fa fa-hand-o-right"></b>&nbsp;全部</li>
								<li class="pC-li2"><b class="fa fa-hand-o-right"></b>&nbsp;南方</li>
								<li class="pC-li3"><b class="fa fa-hand-o-right"></b>&nbsp;北方</li>
								<li class="pC-li4"><b class="fa fa-hand-o-right"></b>&nbsp;西南</li>
								<li class="pC-li5"><b class="fa fa-hand-o-right"></b>&nbsp;视图</li>
							</ol>
						</div>
						<!--三个区域全部展现-->
						<div class="proContent proTotal">
							<div class="proTitle">
								<ul>
									<li>区域</li>
									<li>1月</li>
									<li>2月</li>
									<li>3月</li>
									<li>4月</li>
									<li>5月</li>
									<li>6月</li>
									<li>7月</li>
									<li>8月</li>
									<li>9月</li>
									<li>10月</li>
									<li>11月</li>
									<li>12月</li>
								</ul>
							</div>
							<c:set var="length" value="12"></c:set>
							<ul class="proSouth">
								<li>南方</li>
								<c:forEach begin="1" end="12" step="1" varStatus="status">
									<c:choose>
										<c:when test="${areasStatics['南方'][status.index]!=null}">
											<li>${areasStatics['南方'][status.index].itemCounts}</li>
										</c:when>
										<c:otherwise>
											<li>0</li>
										</c:otherwise>
									</c:choose>
								</c:forEach>

							</ul>
							<ul class="proNorth">
								<li>北方</li>
								<c:forEach begin="1" end="12" step="1" varStatus="status">
									<c:choose>
										<c:when test="${areasStatics['北方'][status.index]!=null}">
											<li>${areasStatics['北方'][status.index].itemCounts}</li>
										</c:when>
										<c:otherwise>
											<li>0</li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</ul>
							<ul class="proSouthwest">
								<li>西南</li>
								<c:forEach begin="1" end="12" step="1" varStatus="status">
									<c:choose>
										<c:when test="${areasStatics['西南区'][status.index]!=null}">
											<li>${areasStatics['西南区'][status.index].itemCounts}</li>
										</c:when>
										<c:otherwise>
											<li>0</li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</ul>
							<ul class="proAll">
									<li>总计</li>
									<c:forEach begin="1" end="12" step="1" varStatus="status">
											<li class="proAllLi"></li>
									</c:forEach>
							</ul>
						</div>
						<!--选择单个区域展现-->
						<div class="proContent proSingal">
							<ul class="sinTitle">
								<li>区域</li>
								<li>1月</li>
								<li>2月</li>
								<li>3月</li>
								<li>4月</li>
								<li>5月</li>
								<li>6月</li>
								<li>7月</li>
								<li>8月</li>
								<li>9月</li>
								<li>10月</li>
								<li>11月</li>
								<li>12月</li>
							</ul>
							<ul class="sin sinSouth">
								<li>南方</li>
								<c:forEach begin="1" end="12" step="1" varStatus="status">
									<c:choose>
										<c:when test="${areasStatics['南方'][status.index]!=null}">
											<li>${areasStatics['南方'][status.index].itemCounts}</li>
										</c:when>
										<c:otherwise>
											<li>0</li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</ul>
							<ul class="sin sinNorth">
								<li>北方</li>
								<c:forEach begin="1" end="12" step="1" varStatus="status">
									<c:choose>
										<c:when test="${areasStatics['北方'][status.index]!=null}">
											<li>${areasStatics['北方'][status.index].itemCounts}</li>
										</c:when>
										<c:otherwise>
											<li>0</li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</ul>
							<ul class="sin sinSouthwest">
								<li>西南</li>
								<c:forEach begin="1" end="12" step="1" varStatus="status">
									<c:choose>
										<c:when test="${areasStatics['西南区'][status.index]!=null}">
											<li>${areasStatics['西南区'][status.index].itemCounts}</li>
										</c:when>
										<c:otherwise>
											<li>0</li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</ul>

						</div>
					</div>
				</div>
			</div>

			<form id="top_text_from" name="top_text_from" method="get" action="Requirement">
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
							value="${fn:split('客户名称,区域,需求来源,需求类别,客户等级,销售负责人,商务负责人,省份,联系人,联系方式,Ref.No,预计下单时间',',')}"></c:set>
						<div class="select1">
							<select name="type1" id="type1">
								<c:forEach items="${dropdown }" var="dropdownList1"
									varStatus="status">
									<%-- <span class="classify1" style="display:none">${dropdownList1}</span> --%>
									<c:choose>
										<c:when test="${dropdownList1==classify1}">
											
											<option selected="selected" value="${dropdownList1}">${dropdownList1}</option>
										</c:when>
										<c:otherwise>
											<option  value="${dropdownList1}">${dropdownList1}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>

							</select>
							<c:if test="${queryType=='common' || queryType=='mixSelect' }">
								<input type="text" id="searchContent1" name="searchContent1" value="${parameter1}">
							</c:if>
							<%-- <c:if test="${queryType!='common'}">
								<input type="text" id="searchContent1" name="searchContent1"
									value="${parameter1}">
							</c:if> --%>
						</div>
						<div class="select2" style="display: none">
							<select name="type2" id="type2">
								<c:forEach items="${dropdown }" var="dropdownList2"
									varStatus="status">
									<c:choose>
										<c:when test="${dropdownList2==classify2}">
											<option selected="selected" value="${dropdownList2}">${dropdownList2}</option>
										</c:when>
										<c:otherwise>
											<option  value="${dropdownList2}">${dropdownList2}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
							<c:if test="${queryType =='mixSelect'}">
								<input type="text" id="searchContent2" name="searchContent2" value="${parameter2}">
							</c:if>
							<c:if test="${queryType!='mixSelect'}">
								<input type="text" id="searchContent2" name="searchContent2"
									value="${parameter2}">
							</c:if>

						</div>
						<div class="select-button">
							<input type="button" value="搜索" class="bToggle"
								onclick="Search()"> <input type="button" value="取消"
								class="bToggle" onclick="Cancel()">
						</div>
				</div>
				 
				<div class="choose">
					<input type="button" value="添加" class="bToggle"  onclick="AddContract()" style="margin-right:2%">
					<c:if test="${authority=='ExportRequirement' }">
						<input type="button" value="导出" class="bToggle export_ex" >
					</c:if>
					<input type="button" value="导出" class="bToggle export_ex" >
				</div>
			</form>
			<table border="1" cellspacing="0" cellspadding="0" id="table1">
				<tr>
					<td>序号</td>
					<td style="display: none;">修改</td>
					<td>时间</td>
					<td>客户名称 <a title="显示与隐藏"><i class="fa fa-plus-square"
							id="fa-button1"></i> </a>
					</td>
					<td>联系人</td>
					<td>联系方式</td>
					<td>区域 <a title="显示与隐藏"><i class="fa fa-plus-square"
							id="fa-button2"></i> </a>
					</td>
					<td>品牌</td>
					<td>具体需求 <a title="显示与隐藏"><i class="fa fa-plus-square"
							id="fa-button3"></i> </a>
					</td>
					<td>需求类别</td>
					<td>需求来源</td>
					<td>是否报价</td>
					<td>销售负责人</td>
					<td>商务负责人</td>
					<td>成单率</td>
					<td>预计下单时间</td>
					<td>是否成单</td>
					<td>进度状况</td>
					<td>跟单计划 <a title="显示与隐藏"><i class="fa fa-plus-square"
							id="fa-button4"></i> </a>
					</td>
					<td>报价单号</td>
					<td>美金报价</td>
					<td>人民币报价</td>
					<td>省份</td>
					<td  style="display:none">客户等级</td>
					<c:forEach var="authoritiy" items="${authorities }">
					
                    	<c:if test="${authoritiy== 'PrepareEmail'}">
                    	<span id="Prepare" style="display:none;">${authoritiy== 'PrepareEmail'}</span>
							<td>报备状态</td>
                    	</c:if>
                	</c:forEach>
					<td>Ref.No</td>
					<td style="display:none;">Website</td>
					<td style="display:none;">邮件</td>
					<td style="display:none;">英文名称</td>
					<td style="display:none;">客户部门</td>
					<td style="display:none;">客户部门英文名称</td>
				</tr>
				<tr>
					<c:forEach items="${requirements}" var="requirement" varStatus="status">
						<c:if test="${status.index>0}">
							<tr>
								<td value="${requirement.ID}" class="xuhao edit">${(currentPage-1)*10+status.index}</td>
								<td style="display:none;"><i class="fa fa-edit" value="${requirement.ID}"></i></td>
								<td class="RequirementDate">${requirement.RequirementDate}</td>
								<td class="Company">${requirement.CustomerName}<input type="hidden" value="${requirement.CustomerID}"></td>
								<td class="Attn">${requirement.Contact}</td>
								<td class="Tel">${requirement.ContactInfo}</td>
								<td class="Area">${requirement.AreaName}</td>
								<td class="Brand">${requirement.Brand}</td>
								<td class="RequirementContent">${requirement.RequirementContent}</td>
								<td class="Classify">${requirement.Classify}</td>
								<td class="Source">${requirement.Source}</td>
								<td class="WhetherQuotes">${requirement.WhetherQuotes}</td>
								<td class="SalesMan">${requirement.SalesMan}</td>
								<td class="BusinessMan">${requirement.BusinessMan}</td>
								<td class="SingleProbability">${requirement.SingleProbability}<input type="hidden"
									value="${requirement.SingleProbabilityID}">
								</td>
								<td class="ExceptedPayTime">${requirement.ExceptedPayTime}</td>
								<td class="WhetherSingle">${requirement.WhetherSingle==1?"是":"否"}</td>
								<td class="ProgressStatus"><a title="${requirement.ProgressStatus}">${requirement.ProgressStatus}</a>
								</td>
								<td class="FollowPlan">${requirement.FollowPlan}</td>
								<td class="QuotationNumber">${requirement.QuotationNumber}</td>
								<td class="USDQuotes">${requirement.USDQuotes}</td>
								<td class="RMBQuotes">${requirement.RMBQuotes}</td>
								<td class="Province">${requirement.Province}</td>
								<td class="CustomerLevel" style="display:none">${requirement.CustomerLevel }</td> 
								<%-- <td>${requirement.Preparation }</td> --%>
								<c:forEach var="authoritiy" items="${authorities }">
			                    	<c:if test="${authoritiy== 'PrepareEmail'}">	
			                    		<td>
			                    		
								<%-- <input type="button" name="report" value="${requirement.Preparation}" id="report" class="bToggle"> --%>
									<c:if test="${requirement.Preparation == '已报备' }">
										<input type="button" name="report" value="已报备" id="report1" class="bToggle"  style="color: red;"> 
									</c:if>
									<c:if test="${requirement.Preparation == '未报备'}">
										<input type="button" name="report" value="未报备" id="report" class="bToggle report"> 
									</c:if>
									</td>
			                    	
			                    	</c:if>
			                	</c:forEach>
								<td class="RefNo">${requirement.RefNo}</td>
								<td class="Website" style="display:none;">${requirement.Website }</td>
								<td class="Email" style="display:none;">${requirement.Email}</td>
								<td class="EnglishName" style="display:none;">${requirement.EnglishName}</td>
								<td class="CustomerDepartment" style="display:none;">${requirement.CustomerDepartment}</td>
								<td class="DepartmentEnglish" style="display:none;">${requirement.DepartmentEnglish}</td>
							</tr>
						</c:if>
					</c:forEach>
			</table>
			<div class="cover-color" style="display: none;"></div>
			<!-- 		==============================	添加信息 ======================-->
			 <div class="info_add" style="display: none;">
				<div class="title">添加信息</div>
				<div class="infoAdd_close">关闭</div>
				<div class="basic_info">
				<input type="hidden" name="id">
					<div class="flexl">
						<div class="flexll">
							<div>时间</div>
							<div>客户名称</div>
							<div>省市</div>
							<div>具体需求</div>
							<div>需求类别</div>
							<div>区域</div>
							<div>进度状况</div>
						</div>
						<div class="flexlr">
							<div><input type="date" name="requirement_date"></div>
							<div>
								<div class="out_search">
										<input type="search" name="customer_search" class="customer_search"> 
										<select name="customer_name" class="add_customer_select" multiple style="width: 350px;">
										</select>
								</div>
							</div>
							<div>
								<input type="text" name="Province" disabled="disabled">
							</div>
							<div>
								<input type="text" name="requirement_content">
							</div>
							<div>
								<select name="requirement_classify">
										<c:forEach items="${classifys}" var="classify"
											varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${classify.ID}" text="${classify.Classify}">${classify.Classify}</option>
											</c:if>
										</c:forEach>
								</select>
							</div>
							<div><input type="text" name="area" disabled="disabled"></div>
							<div>
								<textarea placeholder="请输入" name="progress_status">
								</textarea>
								<!-- <input type="textarea" name="progress_status"> -->
							</div>
						</div>
					</div>
					<div class="flexm">
						<div class="flexml">
							<div>需求来源</div>
							<div>销售负责人</div>
							<div>商务负责人</div>
							<div>是否成单</div>
							<div>成单率</div>
							<div>品牌</div>
							<div>跟单计划</div>
						</div>
						<div class="flexmr">
							<div>
								<select name="demand_sources">
										<c:forEach items="${sources}" var="source" varStatus="status">
											<c:if test="${status.index>0 }">
												<option value="${source.ID}" text="${source.Source}">${source.Source}</option>
											</c:if>
										</c:forEach>
								</select>
							</div>
							<div>
								<select name="sales_man">
										<c:forEach items="${sales}" var="sale" varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${sale.ID}" text="${sale.Name}">${sale.Name}</option>
											</c:if>
										</c:forEach>
								</select>
							</div>
							<div>
								<select class="BusinessMan">
										<option value="请选择">请选择</option>
										<option value="赵娜">赵娜</option>
										<option value="方源媛">方源媛</option>
										<option value="赵文珍">赵文珍</option>
										<!-- <option value="蔡淑慧">蔡淑慧</option> -->
									</select>
							</div>
							<div>
								<select name="whether_single">
										<option value="1" text="是">是</option>
										<option value="0" text="否">否</option>
								</select>
							</div>
							<div>
								<select name="single_probability">
										<c:forEach var="probability" items="${probabilities}"
											varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${probability.ID}">${probability.Probability }</option>
											</c:if>
										</c:forEach>
								</select>
							</div>
							<div>
								<select name="brand">
										<c:forEach items="${brands}" var="brand" varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${brand.ID}" text="${brand.Brand}">${brand.Brand}</option>
											</c:if>
										</c:forEach>
								</select>
							</div>
							<div>
								<textarea name="follow_plan" placeholder="请输入"></textarea>
								<!-- <input type="text" name="follow_plan"> -->
							</div>
						</div>
					</div>
					<div class="flexr">
						<div class="flexrl">
							<div>Ref.No</div>
							<div>是否报价</div>
							<div>报价单号</div>
							<div>美金报价</div>
							<div>人民币报价</div>
							<div>预计下单时间</div>
						</div>
						<div class="flexrr">
							<div class="RefNo_td"><input type="text" name="RefNo"></div>
							<div>
								<select name="whether_quotes">
										<c:forEach items="${quotes}" var="quote" varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${quote.ID}" text="${quote.WhetherQuotes}">${quote.WhetherQuotes}</option>
											</c:if>
										</c:forEach>
								</select>
							</div>
							<div>
								<input type="text" name="quotation_number">
							</div>
							<div><input type="text" name="USD_quotes"></div>
							<div><input type="text" name="RMB_quotes"></div>
							<div><input type="date" name="excepted_pay_time"></div>
						</div>
					</div>
							<!-- 待用 -->
								<div style="display:none">客户等级</div>
						      	<div style="display:none">
									<input type="text" name="CustomerLevel" value="" style="position:absolute;width:162px;" id="CustomerLevel">
										<select onchange="document.getElementById('CustomerLevel').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="A" >A</option>
									<option value="B" >B</option>
									<option value="C" >C</option>
										</select>
								</div>
								<div style="display:none;">Website</div>
								<div style="display:none;"><input type="text" name="Website"></div>
				</div>
				<div class="edit_btn_add">
					<input type="button" value="提交" class="bToggle" id="add_submit">
					<input type="button" value="取消" class="bToggle" id="add_cancel">
				</div>
			</div>
			<!-- 修改信息 -->
			<div class="info_update" style="display: none;">
				<div class="title">修改信息</div>
				<div class="update_close">关闭</div>
				<div class="basic_info">
				<input type="hidden" name="id">
					<div class="flexl">
						<div class="flexll">
							<div>时间</div>
							<div>客户名称</div>
							<div>省市</div>
							<div>具体需求</div>
							<div>需求类别</div>
							<div>区域</div>
							<div>进度状况</div>
						</div>
						<div class="flexlr">
							<div><input type="date" name="requirement_date"></div>
							<div>
								<div class="out_search0">
									<input type="search" name="customer_search"
										class="customer_search">
									<select name="customer_name" multiple style="z-index:100;width: 350px;" class="update_customer_select">
									</select>
								</div>
							</div>
							<div style="position: relative;">
								<div class="isprovince">
									<div id="distpicker2" data-toggle="distpicker">
  									省:<select id="provinceval" data-province="-选择省 -"></select>
  									市:<select id="cityval" data-city="---选择市 ---"></select>
									</div>
								</div>
								<input id="changeProvince" type="text" name="Province" readonly="readonly">
								<span class="icon-province" title="点击修改省市"><i class="icon-circle-right"></i></span>
							</div>
							<div>
								<input type="text" name="requirement_content">
							</div>
							<div>
								<select name="requirement_classify">
										<option value="" text="未选择"></option>
										<c:forEach items="${classifys}" var="classify"
											varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${classify.ID}" text="${classify.Classify}">${classify.Classify}</option>
											</c:if>
										</c:forEach>
								</select>
							</div>
							<div><input type="text" name="area" disabled="disabled"></div>
							<div>
								<textarea placeholder="请输入" name="progress_status">
								</textarea>
								<!-- <input type="textarea" name="progress_status"> -->
							</div>
						</div>
					</div>
					<div class="flexm">
						<div class="flexml">
							<div>需求来源</div>
							<div>销售负责人</div>
							<div>商务负责人</div>
							<div>是否成单</div>
							<div>成单率</div>
							<div>品牌</div>
							<div>跟单计划</div>
						</div>
						<div class="flexmr">
							<div>
								<select name="demand_sources">
										<option value="" text="未选择"></option>
										<c:forEach items="${sources}" var="source" varStatus="status">
											<c:if test="${status.index>0 }">
												<option value="${source.ID}" text="${source.Source}">${source.Source}</option>
											</c:if>
										</c:forEach>
								</select>
							</div>
							<div>
								<select name="sales_man">
										<option value="" text="未选择"></option>
										<c:forEach items="${sales}" var="sale" varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${sale.ID}" text="${sale.Name}">${sale.Name}</option>
											</c:if>
										</c:forEach>
								</select>
							</div>
							<div>
								<select class="BusinessMan_edit">
										<option value="请选择">请选择</option>
										<option value="赵娜">赵娜</option>
										<option value="方源媛">方源媛</option>
										<option value="赵文珍">赵文珍</option>
										<!-- <option value="蔡淑慧">蔡淑慧</option> -->
								</select>
							</div>
							<div>
								<select name="whether_single">
										<option value="1">是</option>
										<option value="0">否</option>
								</select>
							</div>
							<div>
								<select name="single_probability">
										<option value="" text="未选择"></option>
										<c:forEach var="probability" items="${probabilities}"
											varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${probability.ID}">${probability.Probability }</option>
											</c:if>
										</c:forEach>
								</select>
							</div>
							<div>
								<select name="brand">
										<option value="" text="未选择"></option>
										<c:forEach items="${brands}" var="brand" varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${brand.ID}" text="${brand.Brand}">${brand.Brand}</option>
											</c:if>
										</c:forEach>
								</select>
							</div>
							<div>
								<textarea name="follow_plan" placeholder="请输入"></textarea>
								<!-- <input type="text" name="follow_plan"> -->
							</div>
						</div>
					</div>
					<div class="flexr">
						<div class="flexrl">
							<div>Ref.No</div>
							<div>是否报价</div>
							<div>报价单号</div>
							<div>美金报价</div>
							<div>人民币报价</div>
							<div>预计下单时间</div>
						</div>
						<div class="flexrr">
							<div class="RefNo_td"><input type="text" name="RefNo" value="" class="RefNo"></div>
							<div>
								<select name="whether_quotes">
										<option value="" text="未选择"></option>
										<c:forEach items="${quotes}" var="quote" varStatus="status">
											<c:if test="${status.index>0}">
												<option value="${quote.ID}" text="${quote.WhetherQuotes}">${quote.WhetherQuotes}</option>
											</c:if>
										</c:forEach>
								</select>
							</div>
							<div>
								<input type="text" name="quotation_number">
							</div>
							<div><input type="text" name="USD_quotes"></div>
							<div><input type="text" name="RMB_quotes"></div>
							<div><input type="date" name="excepted_pay_time"></div>
						</div>
					</div>
							<!-- 待用 -->
								<div style="display:none">客户等级</div>
						      	<div style="display:none">
									<input type="text" name="CustomerLevel" value="" style="position:absolute;width:162px;" id="CustomerLevel1">
										<select onchange="document.getElementById('CustomerLevel1').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="A" >A</option>
									<option value="B" >B</option>
									<option value="C" >C</option>
										</select>
								</div>
								<div style="display:none;">Website</div>
								<div style="display:none;"><input type="text" name="Website"></div>
				</div>
				<div class="edit_btn_update">
					<input type="button" value="提交" class="bToggle" id="update_submit">
					<input type="button" value="取消" class="bToggle" id="update_cancel">
				</div>
			</div>
			<!-- 添加通知备货信息 发送邮件 -->
			<!-- 是否报备 -->
			<div class="contract_send" style="display: none;">
				<div class="contract_title">确认报备</div>
				<div class="basic_info1">
					<!-- <div class="table_title" style="margin-bottom:30px;">是否确认报备</div> -->
					<div class="table_title">Aqency</div>
					<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
						<tbody>	
							<tr>
								<td>Ref.No</td>
								<td><input type="text" name="RefNo" value="" id="RefNo" ></td>
								<td>Date</td>
								<td><input type="text" name="Date" value=""  id="Date" ></td>
							</tr>
							<tr>
							    <td>Company</td>
								<td><input type="text" name="Company" value="" id="Company"  ></td>
								<td>Website</td>
								<td><input type="text" name="Website" value="" id="Website" ></td>
							</tr>
							<tr>
								<td>Attn</td>
								<td><input type="text" name="Attn" value=""  id="Attn"  ></td>	
								<td>Tel</td>
								<td><input type="text" name="Tel" value="" id="Tel"  ></td>
							</tr>
							<tr>
								<td>Email</td>
								<td><input type="text" name="Email" value=""  id="Email"  ></td>
								<td>borkerCompanyNameE</td>
								<td><input type="text" name="enborkerCompanyName" value=""  id="enborkerCompanyName"></td>
							</tr>
							<tr>
								<td>BorkerCompanyNameC</td>
								<td><input type="text" name="chborkerCompanyName" value=""  id="chborkerCompanyName"></td>
								<td>Borker Department</td>
								<td><input type="text" name="borkerDepartment" value=""  id="borkerDepartment"></td>
							</tr>
							<tr>
								<td>Borker EnglishName</td>
								<td><input type="text" name="borkerNameE" value=""  id="borkerNameE"></td>
								<td>Borker ChineseName</td>
								<td><input type="text" name="borkerNameC" value=""  id="borkerNameC"></td>
							</tr>
							<tr>
								<td>Borker Phone</td>
								<td><input type="text" name="borkerPhone" value=""  id="borkerPhone"></td>
								<td>Borker Email</td>
								<td><input type="text" name="borkerEmail" value=""  id="borkerEmail"></td>
							</tr>
							<tr>
								<td>Requirement</td>
								<td style="position: relative;"><input type="text" name="Requirement" value="" id="Requirement" readonly="readonly" disabled="disabled"><i class="fa fa-pencil choose_requirement_i" aria-hidden="true"></i></td>
								<td style="font-size: 14px;">Estimated Executiontime(Month)</td>
								<td><input type="text" class="report_Executiontime" value=""></td>
							</tr>
						</tbody>
					</table>
					<!-- 是否有最终用户 -->
					<!-- <div class="table_title">Enduser
						<a title="显示"><i class="fa fa-plus-square" id="fa-buttonEnd"></i></a>
						<a title="隐藏"><i class="fa fa-minus-square" id="fa-buttonHide"></i> </a>
					</div> -->
					<table border="1" cellspacing="0" cellpadding="0" class="contract_basic Table_End" style="display:none;">
						<tbody>	
							<tr>
							    <td>Company</td>
								<td><input type="text" name="EndUser" value="" id="EndUser"></td>
								<td>Attn</td>
								<td><input type="text" name="EndAttn" value=""  id="EndAttn"></td>	
							</tr>
							<tr>
								<td>Tel</td>
								<td><input type="text" name="EndTel" value="" id="EndTel"></td>
								<td>Email</td>
								<td><input type="text" name="EndEmail" value=""  id="EndEmail"></td>
							</tr>
							<tr>
								<td>Requirement</td>
								<td><input type="text" name="EndRequirement" value="" id="EndRequirement"></td>
							</tr>
						</tbody>
					</table>		
				</div>
				<div class="edit_btn">
					<input type="button" value="确认报备" class="bToggle" id="send_submit">
					<input type="button" value="取消" class="bToggle" id="send_cancel">
				</div>
			</div>
			<c:choose>
				<c:when test="${queryType == 'common'}">
					<c:set var="queryUrl"
					value="Requirement?type1=${classify1 }&searchContent1=${parameter1}&selected=${str}&currentPage=">
					</c:set>
				</c:when>
				<c:otherwise>
					<c:set var="queryUrl"
					value="Requirement?type1=${classify1 }&searchContent1=${parameter1}&type2=${classify2}&searchContent2=${parameter2}&selected=${str}&currentPage=">
					</c:set>
				</c:otherwise>
			</c:choose>
			<div id="page">
				<div class="pageInfo">
					当前是第&nbsp;<span id="currentPage">${currentPage}</span>&nbsp;页,&nbsp;总计&nbsp;<span
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
			<!-- id="page" end -->
			</div>
			<!-- requirement 弹出选择 -->
			<div class="bg_requirement"></div>
			<div class="choose_requirement">
				<div class="choose_requirement_tit">
					<div class="choose_requirement_tit_l">选择requirement</div>
					<div class="choose_requirement_tit_m">确定</div>
					<div class="choose_requirement_tit_r">关闭</div>
				</div>
				<div class="choose_requirement_body">
					<div class="choose_requirement_body_in">
						<div>
							<input type="radio" name="choose_requirement_radio" value="Accessories:">Accessories:<span contenteditable="true"></span>
						</div>
						<div>
							<input type="radio" name="choose_requirement_radio" value="Probe Station:">Probe Station:<span contenteditable="true"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
	<script src="js/swiper-3.4.1.jquery.min.js" type="text/javascript"
	charset="utf-8"></script>
	<!-- <script src="js/libs/jquery.cookie.js"></script> -->
	<!-- <script src="js/ajaxfileupload.js" type="text/javascript" charset="utf-8"></script> -->
	<script src="js/msgbox.js"></script>
	<!-- <script src="js/msgbox_unload.js"></script> -->
	<script src="js/libs/distpicker.data.min.js"></script>
	<script src="js/libs/distpicker.min.js"></script>
	<!-- <script src="js/global/myFunction.js"></script> -->
	<script src="js/requirement.js"></script>
	<script type="text/javascript">
	
	function FistPage(arg) {
		window.location.href = arg + "1";
	}
	function UpPage(arg) {
		window.location.href = arg;
	}
	function NextPage(arg) {
		window.location.href = arg;
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

	$(function() {
		var MyDate = new Date();
		var Y =  MyDate.getFullYear();
		var str = Y +"年度需求情况";
		$(".prompt-top1").html("").html(str);
		
		for(var i =1; i < $(".proSouth").find("li").length; i++){
			var totalNum = parseFloat($(".proSouth").find("li").eq(i).text()) +  parseFloat($(".proNorth").find("li").eq(i).text()) + parseFloat($(".proSouthwest").find("li").eq(i).text()) ;
			$(".proAllLi").eq(i-1).text(totalNum)
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
		if (<%=request.getAttribute("queryType").equals("common")%>) 
			{
				$('.select2').hide();
			} else {
				$('.select2').show();
			}
		$(".recordbtn").click(function(){
		    var curValue = $(this).data("value");
		    $.cookie("RequirementDetailsClassify", curValue, { expires: 30 });
		    window.open('RequirementDetails');
		});
	});
	</script>
</body>
</html>

