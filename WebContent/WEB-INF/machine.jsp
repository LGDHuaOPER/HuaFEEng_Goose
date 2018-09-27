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
<title>机台统计</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="plugins/awesomplete/awesomplete_all-a2ac84f236.min.css" type="text/css">
<link rel="stylesheet" href="src/css/global/global_add_update_module.css" type="text/css">
<link rel="stylesheet" href="src/css/global/global_table_style.css" type="text/css">
<link rel="stylesheet" href="src/css/global/bsCompatible_cssSticker.css" type="text/css">
<link rel="stylesheet" href="src/css/machine.css" type="text/css">
<style>
	.content {
		padding-bottom: 5px !important;
	}

	#global_add_update_module_cover, .global_add_update_module_cover {
		top: -100px;
	}

	#global_add_update_module_add, #global_add_update_module_update, .global_add_update_module {
		top: 10px;
	}

	.form-control-feedback {
		z-index: 101;
		pointer-events: auto;
	}
</style>
</head>
<body>
<div id="bsCompatible_cssSticker_wrapper">
    <div id="bsCompatible_cssSticker_sticker">
        <div id="bsCompatible_cssSticker_sticker-con">
			<!-- 	头部开始 -->
			<%@include file="top.jsp"%>
			<!-- 	头部结束 -->
			<div class="contain">
				<div class="content">
				<!-- 	=======================导航栏   开始 ================================== -->
				<%@include file="nav.jsp"%>
		<!-- 	=======================导航栏   结束 ================================== -->
					<input type="hidden" id="queryType_input" value="${queryType}">
					<form id="top_text_from" name="top_text_from" method="post" action="GetMachineDetailsRoute">
						<input type="text" id="search" name="isSearch" value="search" style="display: none;">
						<div class="select-content">
							<label> <c:choose>
									<c:when test="${queryType=='mixSelect'}">
										<label><input type="radio" id="singleSelect" name="selected" class="singleSelect" value="singleSelect" onclick="Check(this.value)">单一查询 </label>&nbsp;&nbsp;&nbsp;
										<label><input type="radio" id="mixSelect" name="selected" value="mixSelect" checked="checked" onclick="Check(this.value)">组合查询 </label>&nbsp;&nbsp;&nbsp;<br>
									</c:when>
									<c:otherwise>
										<label><input type="radio" id="singleSelect" name="selected" class="singleSelect" value="singleSelect" checked="checked" onclick="Check(this.value)">单一查询 </label>&nbsp;&nbsp;&nbsp;
										<label><input type="radio" id="mixSelect" name="selected" value="mixSelect" onclick="Check(this.value)">组合查询 </label>&nbsp;&nbsp;&nbsp;<br>
									</c:otherwise>
								</c:choose> <c:set var="dropdown" value="${fn:split('客户单位,用户姓名,Model,SN,合同号,装机服务时间,项目状态,负责人',',')}"></c:set>
								<div class="select1">
									<select name="type1" id="type1">
										<c:forEach items="${dropdown}" var="dropdownList1" varStatus="status">
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
									 <input type="text" id="searchContent1" name="searchContent1" value="${parameter1}">
								</div>
								<div class="select2" style="display: none">
									<select name="type2" id="type2">
										<c:forEach items="${dropdown}" var="dropdownList2" varStatus="status">
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
									<c:if test="${queryType =='mixSelect'}">
										<input type="text" id="searchContent2" name="searchContent2" value="${parameter2}">
									</c:if>
									<c:if test="${queryType!='mixSelect'}">
										<input type="text" id="searchContent2" name="searchContent2" value="${parameter2}">
									</c:if>
								</div>
								<div class="select-button">
									<input type="button" value="搜索" class="btn btn-info" onclick="INSearch()"> 
									<input type="button" value="取消" class="btn btn-warning" onclick="Cancel()">
								</div>
						</div> 
						<div class="choose">
							<input type="button" value="添加" class="btn btn-info" data-targetmodule="add">
						</div>
				 </form>
				<!-- 主页面表格 -->
				<div id="global_table_style_wrapper">
					<table id="global_table_style">
						<thead>
							<tr>
								<th>序号</th>
								<th>客户单位</th>
								<th>用户</th>
								<th>MODEL</th>
								<th>SN</th>
								<th>合同号</th>
								<th>装机时间</th>
								<th>项目状态</th>
								<th>负责人</th>
								<th>最新进展</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="orderInfo" items="${machine}" varStatus="status">
								<c:if test="${status.index>0}">
									<tr>
										<td value="${orderInfo['ID']}" data-targetmodule="update">${status.index+(currentPage-1)*10}</td>
										<td title="${orderInfo['CustomerUnit']}" class="td_CustomerUnit">${orderInfo['CustomerUnit']}</td>
										<td title="${orderInfo['CustomerName']}" class="td_CustomerName">${orderInfo['CustomerName']}</td>
										<td title="${orderInfo['Model']}" class="td_Model">${orderInfo['Model']}</td>
										<td title="${orderInfo['SN']}" class="td_SN">${orderInfo['SN']}</td>
										<td title="${orderInfo['ContractNO']}" class="td_ContractNO">${orderInfo['ContractNO']}</td>
										<td title="${orderInfo['InstalledTime']}" class="td_InstalledTime">${orderInfo['InstalledTime']}</td>
										<td title="${orderInfo['Status']}" class="td_Status">项目状态</td>
										<td title="${orderInfo['Responsible']}" class="td_Responsible">负责人</td>
										<td title="${orderInfo['LatestProgress']}" class="td_LatestProgress">最新进展</td>
										<td class="td_CustomerID" title="${orderInfo['CustomerID']}" style="display:none;">${orderInfo['CustomerID']}</td>
										<td class="td_CurrentProgress" title='${orderInfo["CurrentProgress"]}' style="display:none;">${orderInfo['CurrentProgress']}</td>
									</tr>
								</c:if>
							</c:forEach> 
						</tbody>
					</table>
				</div>
				<!-- 主页面表格 end -->
				<!-- 添加修改模块 -->
				<div id="global_add_update_module_cover" style="display: none;"></div>
				<div class="global_add_update_module" id="global_add_update_module_add" data-classify="add" data-parents="addANDupdate">
					<div class="global_add_update_module_title">添加机台统计<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></div>
					<!-- <div class="global_add_update_module_news"></div> -->
					<div class="global_add_update_module_body">
						<div class="global_add_update_module_bodyin">
							<fieldset><legend>基本信息</legend>
								<div class="container-fluid">
									<div class="row">
										<div class="col-md-6 col-lg-6">
											<div class="form-horizontal">
											    <!-- 一个form-group就是一个整体 -->
											    <div class="form-group has-feedback">
											      	<label class="col-sm-3 control-label" for="add_infomation_CustomerUnit"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>客户单位</label>
											      	<div class="col-sm-9">
											      		<input type="text" class="form-control" id="add_infomation_CustomerUnit" aria-describedby="inputWarning2Status">
											      		<span class="glyphicon glyphicon-remove-circle form-control-feedback" aria-hidden="true"></span>
											      		<span id="inputWarning2Status" class="sr-only">(客户单位)</span>
											      		<select name="CustomerUnit_select" class="form-control" multiple></select>
											      	</div>
											    </div>
											    <div class="form-group">
											        <label for="add_infomation_Model" class="col-sm-3 control-label">MODEL</label>
											        <div class="col-sm-9">
											            <input type="text" id="add_infomation_Model" class="form-control">
											        </div>
											    </div>
											    <div class="form-group">
											        <label for="add_infomation_ContractNO" class="col-sm-3 control-label">合同号</label>
											        <div class="col-sm-9">
											            <input type="text" id="add_infomation_ContractNO" class="form-control">
											        </div>
											    </div>
											    <div class="form-group">
											        <label for="add_infomation_Status" class="col-sm-3 control-label">项目状态</label>
											        <div class="col-sm-9">
											        	<input type="text" id="add_infomation_Status" class="form-control dropdown-input" placeholder="填写或选择"><button type="button" class="btn btn-default awesomplete_btn" aria-label="Left Align"><span class="glyphicon glyphicon-menu-down" aria-hidden="true"></span></button>
											        </div>
											    </div>
											</div>
										</div><!-- col-md-6 end -->
										<div class="col-md-6 col-lg-6">
											<div class="form-horizontal">
											    <!-- 一个form-group就是一个整体 -->
											    <div class="form-group">
											        <label for="add_infomation_CustomerName" class="col-sm-3 control-label"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>用户</label>
											        <div class="col-sm-9">
											            <input type="text" id="add_infomation_CustomerName" class="form-control" readonly disabled data-customername="add">
											        </div>
											    </div>
											    <div class="form-group">
											        <label for="add_infomation_SN" class="col-sm-3 control-label">SN</label>
											        <div class="col-sm-9">
											            <input type="text" id="add_infomation_SN" class="form-control">
											        </div>
											    </div>
											    <div class="form-group">
											        <label for="add_infomation_InstalledTime" class="col-sm-3 control-label">装机时间</label>
											        <div class="col-sm-9">
											            <input type="date" id="add_infomation_InstalledTime" class="form-control">
											        </div>
											    </div>
											    <div class="form-group">
											        <label for="add_infomation_Responsible" class="col-sm-3 control-label">负责人</label>
											        <div class="col-sm-9">
											            <input type="text" id="add_infomation_Responsible" class="form-control">
											        </div>
											    </div>
											</div>
										</div><!-- col-md-6 end -->
									</div>
								</div><!-- container-fluid end -->
							</fieldset>
				    		<fieldset><legend>进展详情&nbsp;<span class="glyphicon glyphicon-plus-sign append_line" aria-hidden="true"></span></legend>
					    		<div class="container-fluid append_line_div">
					    			<table class="table table-bordered table-hover table-condensed">
					    				<thead>
					    					<tr>
					    						<th>序号</th>
					    						<th>进展详情</th>
					    						<th>时间</th>
					    					</tr>
					    				</thead>
					    				<tbody></tbody>
					    			</table>
					    		</div>
				    		</fieldset><!-- 进展详情 fieldset end -->
						</div><!-- global_add_update_module_bodyin end -->
					</div>
					<div class="global_add_update_module_foot">
						<div class="global_add_update_module_footin">
							<input type="button" value="提交" class="btn btn-success global_add_update_module_submit">
							<input type="button" value="取消" class="btn btn-warning global_add_update_module_cancel">
						</div>
					</div>
				</div>

				<div class="global_add_update_module" id="global_add_update_module_update" data-classify="update" data-parents="addANDupdate">
					<div class="global_add_update_module_title">修改机台统计<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></div>
					<!-- <div class="global_add_update_module_news"></div> -->
					<div class="global_add_update_module_body">
						<div class="global_add_update_module_bodyin">
							<fieldset><legend>基本信息</legend>
								<div class="container-fluid">
									<div class="row">
										<div class="col-md-6 col-lg-6">
											<div class="form-horizontal">
											    <!-- 一个form-group就是一个整体 -->
											    <div class="form-group has-feedback">
											      	<label class="col-sm-3 control-label" for="update_infomation_CustomerUnit"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>客户单位</label>
											      	<div class="col-sm-9">
											      		<input type="text" class="form-control" id="update_infomation_CustomerUnit" aria-describedby="inputWarning2Status">
											      		<span class="glyphicon glyphicon-remove-circle form-control-feedback" aria-hidden="true"></span>
											      		<span id="inputWarning2Status" class="sr-only">(客户单位)</span>
											      		<select name="CustomerUnit_select" class="form-control" multiple></select>
											      	</div>
											    </div>
											    <div class="form-group">
											        <label for="update_infomation_Model" class="col-sm-3 control-label">MODEL</label>
											        <div class="col-sm-9">
											            <input type="text" id="update_infomation_Model" class="form-control">
											        </div>
											    </div>
											    <div class="form-group">
											        <label for="update_infomation_ContractNO" class="col-sm-3 control-label">合同号</label>
											        <div class="col-sm-9">
											            <input type="text" id="update_infomation_ContractNO" class="form-control">
											        </div>
											    </div>
											    <div class="form-group">
											        <label for="update_infomation_Status" class="col-sm-3 control-label">项目状态</label>
											        <div class="col-sm-9">
											        	<input type="text" id="update_infomation_Status" class="form-control dropdown-input" placeholder="填写或选择"><button type="button" class="btn btn-default awesomplete_btn" aria-label="Left Align"><span class="glyphicon glyphicon-menu-down" aria-hidden="true"></span></button>
											        </div>
											    </div>
											</div>
										</div><!-- col-md-6 end -->
										<div class="col-md-6 col-lg-6">
											<div class="form-horizontal">
											    <!-- 一个form-group就是一个整体 -->
											    <div class="form-group">
											        <label for="update_infomation_CustomerName" class="col-sm-3 control-label"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>用户</label>
											        <div class="col-sm-9">
											            <input type="text" id="update_infomation_CustomerName" class="form-control" readonly disabled data-customername="update">
											        </div>
											    </div>
											    <div class="form-group">
											        <label for="update_infomation_SN" class="col-sm-3 control-label">SN</label>
											        <div class="col-sm-9">
											            <input type="text" id="update_infomation_SN" class="form-control">
											        </div>
											    </div>
											    <div class="form-group">
											        <label for="update_infomation_InstalledTime" class="col-sm-3 control-label">装机时间</label>
											        <div class="col-sm-9">
											            <input type="date" id="update_infomation_InstalledTime" class="form-control">
											        </div>
											    </div>
											    <div class="form-group">
											        <label for="update_infomation_Responsible" class="col-sm-3 control-label">负责人</label>
											        <div class="col-sm-9">
											            <input type="text" id="update_infomation_Responsible" class="form-control">
											        </div>
											    </div>
											</div>
										</div><!-- col-md-6 end -->
									</div>
								</div><!-- container-fluid end -->
							</fieldset>
				    		<fieldset><legend>进展详情&nbsp;<span class="glyphicon glyphicon-plus-sign append_line" aria-hidden="true"></span></legend>
					    		<div class="container-fluid append_line_div">
					    			<table class="table table-bordered table-hover table-condensed">
					    				<thead>
					    					<tr>
					    						<th>序号</th>
					    						<th>进展详情</th>
					    						<th>时间</th>
					    					</tr>
					    				</thead>
					    				<tbody></tbody>
					    			</table>
					    		</div>
				    		</fieldset><!-- 进展详情 fieldset end -->
						</div><!-- global_add_update_module_bodyin end -->
					</div>
					<div class="global_add_update_module_foot">
						<div class="global_add_update_module_footin">
							<input type="button" value="提交" class="btn btn-success global_add_update_module_submit">
							<input type="button" value="取消" class="btn btn-warning global_add_update_module_cancel">
						</div>
					</div>
				</div>
				<!-- 添加修改模块 end -->
					
					<!-- 添加合同 -->
					<div class="contract_add" style="display: none;">
						<div class="contract_title">添加机台统计</div>
						<div class="contractAdd_close">关闭</div>
						<div class="basic_info">
							<div class="table_title">机台统计</div>
							<table border="1" cellspacing="0" cellpadding="0"
								class="contract_basic">
								<tbody>
									<tr>
										<td class="addInsertId"><i class="relevance"></i>客户单位</td>
										<td>
										<!-- <input type="text" name="CustomerUnit" value=""> -->
										<div class="out_search">
												<input type="search" name="CustomerUnit" class="CustomerUnit">
													 <select name="CustomerUnit" multiple style="width: 248px;height: 100px;"></select>
											</div>
										</td>
										<td><i class="relevance"></i>用户姓名</td>
										<td>
										<div class="out_search">
												<input type="search" name="CustomerName" class="CustomerName" readonly="readonly">
											</div>
										</td>
									</tr>
									<tr>
										<td>Model</td>
										<td><input type="text" name="Model" value=""></td>
										<td>SN</td>
										<td><input type="text" name="SN" value=""></td>
									</tr>
									<tr>
										<td>合同号</td>
										<td><input type="text" name="ContractNO" value=""></td>
										<td>装机服务时间</td>
										<td><input type="date" name="InstalledTime" value=""></td>
									</tr>
									<tr>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="edit_btn">
							<input type="button" value="提交" class="bToggle" id="add_submit">
							<input type="button" value="取消" class="bToggle" id="add_cancel">
						</div>
					</div>
					<div class="contract_update" style="display: none;">
						<div class="contract_title">修改机台统计</div>
						<div class="contractUpdate_close">关闭</div>
						<div class="basic_info">
							<div class="table_title">机台统计</div>
							<table border="1" cellspacing="0" cellpadding="0"
								class="contract_basic">
								<tbody>
									<tr>
										<td class="updateInsertId"><i class="relevance"></i>客户单位</td>
										<td>
										<div class="out_search">
												<input type="search" name="CustomerUnit" class="CustomerUnit">
													 <select name="CustomerUnit" multiple style="width: 248px;height: 100px;"></select>
											</div>
										</td>
										<td><i class="relevance"></i>用户姓名</td>
										<td>
										<div class="out_search">
												<input type="search" name="CustomerName" class="CustomerName" readonly="readonly">
											</div>
										</td>
									</tr>
									<tr>
										<td>Model</td>
										<td><input type="text" name="Model" value=""></td>
										<td>SN</td>
										<td><input type="text" name="SN" value=""></td>
									</tr>
									<tr>
										<td>合同号</td>
										<td><input type="text" name="ContractNO" value=""></td>
										<td>装机服务时间</td>
										<td><input type="date" name="InstalledTime" value=""></td>
									</tr>
									<tr>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="edit_btn">
							<input type="button" value="提交" class="bToggle" id="update_submit">
							<input type="button" value="取消" class="bToggle" id="update_cancel">
						</div>
					</div>

					 	 <c:choose>
							<c:when test="${queryType == 'common'}">
								<c:set var="queryUrl" value="MachineDetails?type1=${classify1}&searchContent1=${parameter1}&selected=${str}&currentPage=">
								</c:set>
							</c:when>
							<c:otherwise>
								<c:set var="queryUrl" value="GetMachineDetailsRoute?type1=${classify1}&searchContent1=${parameter1}&type2=${classify2}&searchContent2=${parameter2}&selected=${queryType}&currentPage=">
								</c:set>
							</c:otherwise>
						</c:choose>

					<!-- 分页组件 -->
					<div id="glbal_table_page_wrapper">
						<div id="global_table_page">
							<div class="pageInfo">
								当前是第&nbsp;<span id="currentPage">${currentPage}</span>&nbsp;页,&nbsp;总计&nbsp;<span id="allPage">${pageCounts}</span>页
							</div>
							<div class="changePage">
								<input type="button" class="btn btn-primary" value="首页" id="fistPage" name="fistPage" onclick="FistPage('${queryUrl}')">
								<input type="button" class="btn btn-primary" value="上一页" id="upPage" onclick="UpPage('${queryUrl}${currentPage-1 }')">
								<input type="button" class="btn btn-primary" value="下一页" id="nextPage" onclick="NextPage('${queryUrl}${currentPage+1 }')"> 跳到第 <input type="text" id="jumpNumber" name="jumpNumber" class="jumpNumber" style="width: 30px; color: #000" onkeyup="value=value.replace(/[^\d]/g,'') "> 页 <input type="button" class="btn btn-primary" value="GO" id="Gotojump" name="Gotojump" onclick="PageJump('${queryUrl}')">
								<input type="button" class="btn btn-primary" value="尾页" id="lastPage" name="lastPage" onclick="LastPage('${queryUrl}')">
							</div>
						</div>
					</div>
					<!-- 分页组件 end -->
				</div>
			</div>
		</div><!-- bsCompatible_cssSticker_sticker-con结束 -->
    </div><!-- bsCompatible_cssSticker_sticker结束 -->
    <div id="bsCompatible_cssSticker_footer">
        <div id="eoulu-copy-out" style="height:40px;width:calc(100% - 2px);">
            <div style="width:100%;height:5px;"></div>
            <div id="eoulu-copy" style="width:100%;height:35px;font-size:12px;color:#888;line-height:35px;z-index: 2;">
                <hr style="height:1px;color:#999;width: calc(100% - 3px);" />
                <div style="width:100%;text-align:center;display:inline-block;">Copyright  ©&nbsp;<span class="YEAR">2018</span>&nbsp<a href="http://www.eoulu.com/h-col-268.html" class="EHref" target="_blank" style="color:blue;">Eoulu</a> Tech. Co.,Ltd.</div>
            </div>
        </div>
    </div><!-- bsCompatible_cssSticker_footer 结束 -->
</div><!-- bsCompatible_cssSticker_wrapper结束 -->
	<script src="js/libs/bootstrap.min.js"></script>
	<script src="plugins/awesomplete/awesomplete.min.js"></script>
</body>
<script src="src/js/msgbox.js"></script>
<script src="src/js/machine.js"></script>
</html>