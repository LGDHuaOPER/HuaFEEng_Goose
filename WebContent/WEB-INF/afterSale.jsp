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
<title>售后维修</title>
<style>
.tbody_tr td {
    padding: 0 3px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

h5, h6 {
	margin-top: 0 !important;
	margin-bottom: 0 !important;
}

.g-nav-ul li {
	box-sizing: content-box !important;
}

div#eoulu-copy hr{
	margin-top: 0;
    margin-bottom: 0;
    border: none;
    border-top: none;
}
</style>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" />
<link rel="stylesheet" type="text/css" href="css/libs/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="css/global/eouluCustom.css" />
<link rel="stylesheet" type="text/css" href="css/afterSale.css">
</head>
<body>
	<!-- 	头部开始 -->
	<%@include file="top.jsp"%>
	<!-- 	头部结束 -->
	<div class="contain">
		<div class="content">
		<!-- 	=======================导航栏   开始 ================================== -->
		<%@include file="nav.jsp"%>
		

			<!-- 查询功能 -->
			 <div style="display: none">
				<input type="text" value="" name="query_type"> 
				<input type="text" value="" name="classify1"> 
				<input type="text" value="" name="classify2"> 
				<input type="text" value="" name="parameter1"> 
				<input type="text" value="" name="parameter2">
			  </div>  

 			<form id="top_text_from" name="top_text_from" method="post"
				action="GetAfterSaleRoute">
				<input type="text" id="search" name="isSearch" value="search"
					style="display: none;">
				<div class="select-content">
					 <label style="margin-bottom: 0;"> <c:choose>
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
								value="${fn:split('客户单位,服务项目,姓名,目前进展,项目状态,服务完成时间',',')}"></c:set>
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
							 <input type="text" id="searchContent1" name="searchContent1" value="${parameter1}">

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
							<c:if test="${queryType =='mixSelect'}">
								<input type="text" id="searchContent2" name="searchContent2" value="${parameter2}">
							</c:if>
							<c:if test="${queryType!='mixSelect'}">
								<input type="text" id="searchContent2" name="searchContent2" value="${parameter2}">
							</c:if>

						</div>
						 <div class="select-button">
							<input type="button" value="搜索" class="bToggle" onclick="INSearch()"> 
							<input type="button" value="取消" class="bToggle" onclick="Cancel()">
						</div>
				</div>
				<div class="choose">
					<input type="button" value="添加" class="bToggle" onclick="AddContract()">
				</div>

		 </form>  
		<div style="min-width:1000px;width: 90%;margin: 0 auto;">
			<table border="1" cellspacing="0" cellspadding="0" id="table1">
				<tr>
					<td>序号</td>
					<td style="display:none">修改</td>
					<td>客户单位 </td>
					<td>姓名 </td>
					<td>服务项目</td>
					<td>目前进展</td>
					<td>项目状态</td>
					<td>服务完成时间</td>
					<td style="display:none;">删除数据</td>
					<td style="display:none;">进展源数据</td>
				</tr>
				 <c:forEach var="orderInfo" items="${afterSale}" varStatus="status">
					<c:if test="${status.index>0}">
						<tr class="tbody_tr">
							<td value="${orderInfo['ID']}" class="contract-edit" style="cursor:pointer">${status.index+(currentPage-1)*10}</td>
							<td  style="display:none"> <i class="fa fa-edit" value="${orderInfo['ID']}"></i></td>
							<td title="${orderInfo['CustomerUnit']}">${orderInfo['CustomerUnit']}</td>       <!--2  -->
							<td>${orderInfo['CustomerName']}</td>   <!--3  -->
							<td title="${orderInfo['ServiceProject']}">${orderInfo['ServiceProject']}</td>   <!--4  -->
							<td class="lastProgress" title=""></td>   <!--5  -->
							<c:if test="${orderInfo['ProjectStatus'] == 1}">
								<td>待解决</td> 
							</c:if>
							<c:if test="${orderInfo['ProjectStatus'] == 2}">
								<td>已完结</td> 
							</c:if>   <!--6  -->
							<td>${orderInfo['EndDate']}</td>   <!--7  -->        
							<td style="display:none;"><i class="fa fa-trash-o del"></i></td>   <!--6  -->
							<td style="display:none;" class="progressData" title='${orderInfo["CurrentProgress"]}'>${orderInfo['CurrentProgress']}</td>
						</tr>
					</c:if>
				</c:forEach>
			</table>	
		</div>
	 	 <c:choose>
				<c:when test="${queryType == 'common'}">
					<c:set var="queryUrl"
					value="AfterSale?type1=${classify1 }&searchContent1=${parameter1}&selected=${str}&currentPage=">
					</c:set>
				</c:when>
				<c:otherwise>
					<c:set var="queryUrl"
					value="GetAfterSaleRoute?type1=${classify1 }&searchContent1=${parameter1}&type2=${classify2}&searchContent2=${parameter2}&selected=${queryType}&currentPage=">
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
	</div>
	<div class="MailBar_cover_color"></div>
	
		<!-- 添加售后维修信息 -->
	<div class="contract_add" style="font-size:16px;">
		<div class="contract_title">添加售后维修</div>
		<div class="contractAdd_close">关闭</div>
		<div class="basic_info">
			<div class="table_title">售后维修</div>
			<div class="m-add-top">
				<div class="flex-l">
					<div class="row" style="margin-bottom: 18px;">
						<div class="col-md-3"><i class="eou-relevance"></i>客户单位</div>
						<div class="col-md-6 col-md-offset-1" style="position: relative;">
						<input type="text" name="CustomerUnit" value="" id="CustomerUnit" class="form-control">
							<select multiple name="" id="addCandidate" style="width:300px;height:80px;display: none;position: absolute;top: 24px;left: 15px;z-index:100;font-size: 12px;">
							</select>
						</div>
					</div>
					<div class="row">
						<div class="col-md-3"><i class="eou-relevance"></i>姓名</div>
						<div class="col-md-6 col-md-offset-1"><input type="text" name="CustomerName" value=""  readonly="readonly" id="CustomerName" class="form-control"></div>
					</div>
				</div>
				<div class="flex-r">
					<div class="row" style="margin-bottom: 10px;">
						<div class="col-md-3 col-md-offset-1 expect1">项目状态</div>
						<div class="col-md-6 col-md-offset-1">
							<!-- <input type="text" name="ProjectStatus" value="" style="position:absolute;width:162px;" id="ProjectStatus">
								<select onchange="document.getElementById('ProjectStatus').value=this.options[this.selectedIndex].text" class="ProjectStatusSelect">
									<option value="No" selected="selected">--请选择--</option>
									<option value="">待解决</option>
									<option value="">已完结</option>
								</select> -->
								<select name="" id="addProjectStatus" class="ProjectStatusSelect form-control">
									<option value="0" selected="selected">--请选择--</option>
									<option value="1">待解决</option>
									<option value="2">已完结</option>
								</select>
						</div>
					</div>
					<div class="row">
						<div class="col-md-3 col-md-offset-1 expect">服务完成时间</div>
						<div class="col-md-6 col-md-offset-1"><input type="date" name="EndDate" value="" class="form-control"></div>
					</div>
				</div>
			</div>
			<div class="m-add-mid" style="margin-top:20px;">
				<div class="row">
					<div class="col-md-2" style="height:34px;line-height:34px;">服务项目</div>
					<div class="col-md-9" style="padding-left:20px;"><input type="text" name="ServiceProject" value=""  id="ServiceProject" class="form-control"></div>
				</div>
				<div class="row" style="margin-top:18px;">
					<div class="col-md-2 eou-color" style="height:22px;line-height:22px;">进展详情</div>
					<div class="col-md-2" style="padding-left:20px;height:22px;">
						<button type="button" id="u-add-btn" class="btn eou-button eou-button-20 eou-button-w60">添加</button>
					</div>
				</div>
			</div>
			<div class="m-add-table" style="margin-top:20px;max-height:200px;overflow:auto">
				<table class="table table-striped table-hover table-condensed">
					<thead><tr>
					<th>序号</th>
					<th>进展详情</th>
					<th>时间</th>
					</tr></thead>
					<tbody>
						<!-- <tr>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td></td>
							<td></td>
						</tr> -->
					</tbody>
				</table>
			</div>
						<!-- <td>目前进展</td>
						<td><input type="text" name="CurrentProgress" value=""  id="CurrentProgress"></td> -->			
		</div>
		<div class="edit_btn">
			<input type="button" value="提交" class="bToggle" id="add_submit">
			<input type="button" value="取消" class="bToggle" id="add_cancel">
		</div>
	</div>
	
	<!-- 修改售后维修信息 -->
	<div class="contract_update">
		<div class="contract_title">修改售后维修</div>
		<div class="contractUpdate_close">关闭</div>
		<div class="basic_info">
			<div class="table_title">售后维修</div>

			<div class="m-update-top">
				<div class="flex-l">
					<div class="row" style="margin-bottom: 18px;">
						<div class="col-md-3"><i class="eou-relevance"></i>客户单位</div>
						<div class="col-md-6 col-md-offset-1"><input type="text" name="CustomerUnit" value="" id="CustomerUnit2" class="form-control" style="font-size: 14px;">
							<select multiple name="" id="addCandidate2" style="width:300px;height:80px;display: none;position: absolute;top: 24px;left: 15px;z-index:100;font-size: 12px;">
							</select>
						</div>
					</div>
					<div class="row">
						<div class="col-md-3"><i class="eou-relevance"></i>姓名</div>
						<div class="col-md-6 col-md-offset-1"><input type="text" name="CustomerName" value=""  readonly="readonly" id="CustomerName2" class="form-control"></div>
					</div>
				</div>
				<div class="flex-r">
					<div class="row" style="margin-bottom: 10px;">
						<div class="col-md-3 col-md-offset-1 expect1">项目状态</div>
						<div class="col-md-6 col-md-offset-1">
							<!-- <input type="text" name="ProjectStatus" value="" style="position:absolute;width:162px;" id="ProjectStatus">
								<select onchange="document.getElementById('ProjectStatus').value=this.options[this.selectedIndex].text" class="ProjectStatusSelect">
									<option value="No" selected="selected">--请选择--</option>
									<option value="">待解决</option>
									<option value="">已完结</option>
								</select> -->
								<select name="" id="updateProjectStatus" class="ProjectStatusSelect form-control">
									<option value="0" selected="selected">--请选择--</option>
									<option value="1">待解决</option>
									<option value="2">已完结</option>
								</select>
						</div>
					</div>
					<div class="row">
						<div class="col-md-3 col-md-offset-1 expect">服务完成时间</div>
						<div class="col-md-6 col-md-offset-1"><input type="date" name="EndDate" value="" class="form-control"></div>
					</div>
				</div>
			</div>
			<div class="m-update-mid" style="margin-top:20px;">
				<div class="row">
					<div class="col-md-2" style="height:34px;line-height:34px;">服务项目</div>
					<div class="col-md-9" style="padding-left:20px;"><input type="text" name="ServiceProject" value=""  id="ServiceProject2" class="form-control"></div>
				</div>
				<div class="row" style="margin-top:18px;">
					<div class="col-md-2 eou-color" style="height:22px;line-height:22px;">进展详情</div>
					<div class="col-md-2" style="padding-left:20px;height:22px;">
						<button type="button" id="u-update-btn" class="btn eou-button eou-button-20 eou-button-w60">添加</button>
					</div>
				</div>
			</div>
			<div class="m-update-table" style="margin-top:20px;max-height:200px;overflow:auto">
				<table class="table table-striped table-hover table-condensed">
					<thead><tr>
					<th>序号</th>
					<th>进展详情</th>
					<th>时间</th>
					</tr></thead>
					<tbody>
						<!-- <tr>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td></td>
							<td></td>
						</tr> -->
					</tbody>
				</table>
			</div>

			<!-- <table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
				<tbody>	
					<tr>
					    <td>客户单位</td>
						<td><input type="text" name="CustomerUnit" value="" id="CustomerUnit"></td>	
						<td>姓名</td>
						<td><input type="text" name="CustomerName" value=""  id="CustomerName"></td>
						<td>服务项目</td>
						<td><input type="text" name="ServiceProject" value=""  id="ServiceProject"></td>
					</tr>
					<tr>
						<td>目前进展</td>
						<td><input type="text" name="CurrentProgress" value=""  id="CurrentProgress"></td>
						<td>项目状态</td>
						<td>
							<input type="text" name="ProjectStatus" value="" style="position:absolute;width:162px;" id="ProjectStatus1">
								<select onchange="document.getElementById('ProjectStatus1').value=this.options[this.selectedIndex].text" class="ProjectStatusSelect">
									<option value="No" selected="selected">--请选择--</option>
									<option value="">待解决</option>
									<option value="">已完结</option>
								</select>
						</td>
						<td>服务完成时间</td>
						<td><input type="date" name="EndDate" value=""></td>
					</tr>
				</tbody>
			</table> -->		
		</div>
		<div class="edit_btn">
			<input type="button" value="提交" class="bToggle" id="update_submit">
			<input type="button" value="取消" class="bToggle" id="update_cancel">
		</div>
	</div>

</body>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/msgbox.js"></script>
<!-- <script src="js/msgbox_unload.js"></script> -->
<!-- <script src="js/global/myFunction.js"></script> -->
<script src="js/afterSale.js"></script>
<script>
// function globalCopyPos10(){
//     $("#bodyBottom").css("z-index",11);
//     if($("body").height() <= $(window).height()){
//         var globalNewH = $(window).height() -30;
//         $("#bodyBottom").css("top",globalNewH+"px");
//     }else{
//         var globalNewH2 = $(document.body).height() - 30;
//         $("#bodyBottom").css("top",globalNewH2+"px");
//     }
// }
// globalCopyPos10();
/*********************添加售后维修信息************************/
$(window).on("resize",function(){
	alertResponse(".contract_add",500,800);
	alertResponse(".contract_update",500,800);
});

/* 添加售后维修信息 */
$(document).on("click","#add_submit",function(){
		console.log("111")
		var CustomerUnit=$('.contract_add input[name="CustomerUnit"]').val();
	    var CustomerName=$('.contract_add input[name="CustomerName"]').val();
	    /* var Status=$('.contract_add input[name="Status"]').val() == "" ? "NA":$('.contract_add input[name="Status"]').val(); */
	  /* var Status = $('.contract_add input[name="Status"]').val()=="交付"?"1":$('.contract_add input[name="Status"]').val()=="尾款"?"2":"3"; */
	    var ServiceProject=$('.contract_add input[name="ServiceProject"]').val();
	    // var CurrentProgress=$('.contract_add input[name="CurrentProgress"]').val();
	    // var ProjectStatus;
	    var ProjectStatus = $("#addProjectStatus").val();
	    if(ProjectStatus==0 || ProjectStatus=="0"){
	    	$.MsgBox_Unload.Alert("提示","请选择项目状态");
	    	return;
	    }
	    var EndDate=$('.contract_add input[name="EndDate"]').val();
	    var Classify="Add";
	    
	    var CurrentProgress = [];
		var addSubProTr = $(".m-add-table tbody tr");
		var addSubProTrLen = addSubProTr.length;
		if(addSubProTrLen<1){
			$.MsgBox_Unload.Alert("提示","至少有一条进展才可提交！");
	    	return;
		}
		for (var i =0;i<addSubProTrLen;i++){
			var addSubProTrCur = addSubProTr.eq(i);
			var item = {};
			item.CurrentProgress = addSubProTrCur.find("td:nth-child(2)").text().trim();
			item.Date = addSubProTrCur.find("input[type='date']").val();
			CurrentProgress.push(item);
		}

		CurrentProgress = JSON.stringify(CurrentProgress);
    	var LatestProgress = addSubProTr.eq(addSubProTrLen-1).find("td:nth-child(2)").text().trim();
    	if(LatestProgress == "" || LatestProgress == "--" || LatestProgress == null || LatestProgress == undefined){
    		$.MsgBox_Unload.Alert("添加提交提示","最新进展详情为空或格式错误");
        	return;
    	}
	    
 	   $.ajax({
	        type : 'get',
	        url : 'AfterSaleOperate',
	        data : {
	        	CustomerUnit : CustomerUnit,
	        	CustomerName : CustomerName,
	        	ServiceProject : ServiceProject,
	        	CurrentProgress : CurrentProgress,
	        	ProjectStatus : ProjectStatus,
	        	EndDate : EndDate,
	        	Classify : Classify,
	        	LatestProgress: LatestProgress
	        },
	        dataType : 'json',
	        success : function (data) {
	        	console.log(data);
	            $.MsgBox.Alert('提示','添加成功');
	            $('.MailBar_cover_color').hide();
	            $('.contract_add').hide();
	        },
	        error : function () {
	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    }); 
    });
    
    
/* 修改售后维修信息 */
$(document).on("click",".contract-edit",function(){	
	var tr=$(this).parent();
    $('.contract_update input[name="CustomerUnit"]').val(tr.find('td').eq(2).text());
	$('.contract_update input[name="CustomerName"]').val(tr.find('td').eq(3).text());
	$('.contract_update input[name="ServiceProject"]').val(tr.find('td').eq(4).text());
	$('.contract_update input[name="CurrentProgress"]').val(tr.find('td').eq(5).text());
	// $('.contract_update input[name="ProjectStatus"]').val(tr.find('td').eq(6).text());
	var ProjectStatus = ["--请选择--","待解决","已完结"];
	var ProjectStatusIndex = $.inArray(tr.find('td').eq(6).text().trim(),ProjectStatus);
	// alert(ProjectStatusIndex);
	$("#updateProjectStatus").val(ProjectStatusIndex);
	$('.contract_update input[name="EndDate"]').val(tr.find('td').eq(7).text());
	var  ID = tr.find('td.contract-edit').attr("value");
	
	$(".contract_update .contract_title").attr("value",ID);  
	//在修改页面保存当前行的ID信息
	console.log("ID"+ID);
	$(".m-update-table tbody").empty();
	var updateProgressData = tr.find(".progressData").attr("title");
	var updateProgressJson = JSON.parse(updateProgressData);
	var updateProgressLen = updateProgressJson.length;
	var updateProgressStr1 = '<tr><td><img src="image/Undo.png" style="height:14px;">&nbsp;<span>';
	var updateProgressStr2 = '</span></td><td contenteditable="true"></td><td><input type="date" class="form-control" style="height:30px;width:95%;"></td></tr>';
	if(updateProgressLen>1){
		for(var i = 1;i < updateProgressLen;i++){
			var updateProgressStr = updateProgressStr1+i+updateProgressStr2;
			$(".m-update-table tbody").append(updateProgressStr);
			var j = i-1;
			var currentUpdateTr = $(".m-update-table tbody tr").eq(j);
			currentUpdateTr.find("td:nth-child(2)").text(updateProgressJson[i].CurrentProgress);
			currentUpdateTr.find("td:nth-child(2)").attr("title",updateProgressJson[i].CurrentProgress);
			currentUpdateTr.find("td:nth-child(2)").attr("progressId",updateProgressJson[i].ID);
			currentUpdateTr.find("input[type='date']").val(updateProgressJson[i].Date);
		}
	}
   $('.MailBar_cover_color').show();
   $('.contract_update').show();
 });
   	
	/*  提交修改后的信息  */
	$('#update_submit').click(function () {
		var CustomerUnit = $('.contract_update input[name="CustomerUnit"]').val();
		var CustomerName = $('.contract_update input[name="CustomerName"]').val();
		var ServiceProject = $('.contract_update input[name="ServiceProject"]').val();
		// var CurrentProgress = $('.contract_update input[name="CurrentProgress"]').val();
		var ProjectStatus = $("#updateProjectStatus").val();
		if(ProjectStatus==0 || ProjectStatus=="0"){
	    	$.MsgBox_Unload.Alert("提示","请选择项目状态");
	    	return;
	    }
		/* var Status = $('.contract_update input[name="Status"]').val()=="交付"?"1":$('.contract_update input[name="Status"]').val()=="尾款"?"2":"3"; */
		var EndDate = $('.contract_update input[name="EndDate"]').val();
		var  ID = $(".contract_update .contract_title").attr("value");
		var Classify="Modify";

		var CurrentProgress = [];
		var updateSubProTr = $(".m-update-table tbody tr");
		var updateSubProTrLen = updateSubProTr.length;
		if(updateSubProTrLen<1){
			$.MsgBox_Unload.Alert("提示","至少有一条进展才可提交！");
	    	return;
		}
		for (var i =0;i<updateSubProTrLen;i++){
			var updateSubProTrCur = updateSubProTr.eq(i);
			var item = {};
			item.CurrentProgress = updateSubProTrCur.find("td:nth-child(2)").text().trim();
			item.Date = updateSubProTrCur.find("input[type='date']").val();
			CurrentProgress.push(item);
		}

		CurrentProgress = JSON.stringify(CurrentProgress);
		var LatestProgress = updateSubProTr.eq(updateSubProTrLen-1).find("td:nth-child(2)").text().trim();
		if(LatestProgress == "" || LatestProgress == "--" || LatestProgress == null || LatestProgress == undefined){
			$.MsgBox_Unload.Alert("修改提交提示","最新进展详情为空或格式错误");
	    	return;
		}
		
		  $.ajax({
		      type : 'get',
		      url : 'AfterSaleOperate',
		      data : {
		    	  ID : ID,
		    	  CustomerUnit : CustomerUnit,
		          CustomerName : CustomerName,
		          ServiceProject : ServiceProject,
		          CurrentProgress : CurrentProgress,
		          ProjectStatus : ProjectStatus,
		          EndDate : EndDate,
		          Classify : Classify,
		          LatestProgress: LatestProgress
		      },
		      dataType : 'json',
		      success : function (data) {
		      		var imessage;
					if(data == true){
						imessage = "修改成功";
						$.MsgBox.Alert('提示', imessage);
						return false;
					}else if(data == false){
						imessage = "修改失败";
					}else{
						imessage = data;
					} 	
		          $.MsgBox_Unload.Alert('提示', imessage);
		      },
		      error : function () {
		          $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
		      }
		  }); 
	})   

//点击添加
function AddContract() {
	/*默认当前日期 */
	var ddd = new Date();
	var day =ddd.getDate();
	var month = ddd.getMonth()+1;
	var day = ddd.getDate();
	if(month<10){
		month = "0"+month;
	}
	if(day<10){
	 day = "0"+day; 
	}
	var datew = ddd.getFullYear()+"-"+month+"-"+day;
	datew = datew.toString();
	$("#Date").val(datew);
	var str = '<tr><td><img src="image/Undo.png" style="height:14px;">&nbsp;<span>1</span></td><td contenteditable="true"></td><td><input type="date" class="form-control" style="height:30px;width:95%;"></td></tr>';
	$(".m-add-table tbody").empty();
	$(".m-add-table tbody").append(str);
	var addToday = globalGetToday();
	// alert(addToday);
	$(".m-add-table tbody input[type='date']").val(addToday);
    $('.MailBar_cover_color').show();
    $('.contract_add').show();
};


//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function () {
    window.location.reload();
});

//点击关闭
$('.contractAdd_close').click(function () {
  $('.MailBar_cover_color').hide();
  $('.contract_add').hide();
});

$('.contractUpdate_close').click(function () {
  $('.MailBar_cover_color').hide();
  $('.contract_update').hide();
});
$('#contract_close1').click(function () {
  $('.MailBar_cover_color').hide();
  $('.hidePdf').hide();
});

//点击取消
$('#add_cancel').click(function () {
  $('.MailBar_cover_color').hide();
  $('.contract_add').hide();
});

$('#update_cancel').click(function () {
  $('.MailBar_cover_color').hide();
  $('.contract_update').hide();
});

/****************** 跳页 **********************/

function FistPage(arg) {
	/* window.location.href = arg + "1"; */
	if(arg.split('?')[0]=='GetAfterSaleRoute'){
		 $('#search').val('search');
		 $("#top_text_from").attr("action", arg + "1");
		  $('#top_text_from').submit();
	}else{ 
		
		window.location.href = arg + "1";
	 } 
}
function UpPage(arg) {
	if(arg.split('?')[0]=='GetAfterSaleRoute'){
		 $('#search').val('search');
		 $("#top_text_from").attr("action", arg);
		  $('#top_text_from').submit();
	}else{ 
		
		window.location.href = arg;
	 } 
}
function NextPage(arg) {
	if(arg.split('?')[0]=='GetAfterSaleRoute'){
		 $('#search').val('search');
		 $("#top_text_from").attr("action", arg);
		  $('#top_text_from').submit();
	}else{ 
		
		window.location.href = arg;
	 } 
}
function PageJump(arg) {
	var jumpNumber = document.getElementById("jumpNumber").value;
	if (jumpNumber == null || jumpNumber == 0) {
		jumpNumber = $('#currentPage').html();
	} else if (jumpNumber > parseInt($('#allPage').html())) {
		jumpNumber = $('#allPage').html();
	}
	/* window.location.href = arg + jumpNumber; */
	if(arg.split('?')[0]=='GetAfterSaleRoute'){
		 $('#search').val('search');
		 $("#top_text_from").attr("action", arg + jumpNumber);
		  $('#top_text_from').submit();
	}else{ 
		
		window.location.href = arg + jumpNumber;
	 } 
}
function LastPage(arg) {
	var jumpNumber = parseInt($('#allPage').html());
	if(arg.split('?')[0]=='GetAfterSaleRoute'){
		 $('#search').val('search');
		 $("#top_text_from").attr("action", arg + jumpNumber);
		  $('#top_text_from').submit();
	}else{ 
		
		window.location.href = arg + jumpNumber;
	 } 
}
$(function() {
	// globalCopyPos10();
	// $(window).on("resize",globalCopyPos10);
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

	// <option edata="${customer}" value="${customer.CustomerName}" contact="${customer.Contact}">${customer.CustomerName}:${customer.Contact}</option>
	$("#CustomerUnit").on("input propertychange",function(){
		if($(this).val()==""){
		    return;
		}
		var addKeyword = $(this).val().trim();
		if(addKeyword == "" || addKeyword == "--"){
		    $.MsgBox_Unload.Alert("提示", "客户搜索值为空或格式错误");
		    return;
		}else{
		    var CustomerName = $(this).val();
		    $.ajax({
		        type : 'GET',
		        url : 'GetCustomer',
		        data: {
		            CustomerName: CustomerName
		        },
		        success : function (data) { 
		            var newData = JSON.parse(data);
		            console.log(newData);
		            var str='';
		            if(newData.length>1){
		                for(var i = 1;i<newData.length;i++){
		                    str+='<option value="'+newData[i].CustomerName+'" text="'+newData[i].ID+'" contact="'+newData[i].Contact+'" contactinfo="'+newData[i].ContactInfo1+'" area="'+newData[i].Area+'" city="'+newData[i].City+'" email="'+newData[i].Email+'">'+newData[i].CustomerName+'&nbsp;:&nbsp;'+newData[i].Contact+'</option>';
		                }
		            }
		            $("#addCandidate").empty().append(str);
		            $('#addCandidate').fadeIn(200);
		        },
		        error : function () {
		            $.MsgBox_Unload.Alert("提示", "服务器繁忙，客户名称获取有误！");
		        }
		    });
		}
	});

	$(document).on("click", '#addCandidate option', function () {
	    $('#CustomerUnit').val($('#addCandidate').val());
	    $("#CustomerName").val($(this).attr("contact"));
	    $('#addCandidate').fadeOut(200);
	});

	$("#CustomerUnit2").on("input propertychange",function(){
		if($(this).val()==""){
		    return;
		}
		var addKeyword = $(this).val().trim();
		if(addKeyword == "" || addKeyword == "--"){
		    $.MsgBox_Unload.Alert("提示", "客户搜索值为空或格式错误");
		    return;
		}else{
		    var CustomerName = $(this).val();
		    $.ajax({
		        type : 'GET',
		        url : 'GetCustomer',
		        data: {
		            CustomerName: CustomerName
		        },
		        success : function (data) { 
		            var newData = JSON.parse(data);
		            console.log(newData);
		            var str='';
		            if(newData.length>1){
		                for(var i = 1;i<newData.length;i++){
		                    str+='<option value="'+newData[i].CustomerName+'" text="'+newData[i].ID+'" contact="'+newData[i].Contact+'" contactinfo="'+newData[i].ContactInfo1+'" area="'+newData[i].Area+'" city="'+newData[i].City+'" email="'+newData[i].Email+'">'+newData[i].CustomerName+'&nbsp;:&nbsp;'+newData[i].Contact+'</option>';
		                }
		            }
		            $("#addCandidate2").empty().append(str);
		            $('#addCandidate2').fadeIn(200);
		        },
		        error : function () {
		            $.MsgBox_Unload.Alert("提示", "服务器繁忙，客户名称获取有误！");
		        }
		    });
		}
	});


	$(document).on("click", '#addCandidate2 option', function () {
	    $('#CustomerUnit2').val($('#addCandidate2').val());
	    $("#CustomerName2").val($(this).attr("contact"));
	    $('#addCandidate2').fadeOut(200);
	});

})

/*********模板 删除*******/
  $(document).on("click",".del",function () {
	  var thisList = $(this).parent().parent();
      var ID = thisList.find("td").eq(0).attr("value");
      $(".yejiao").attr("value",ID);
	  console.log(ID);
	  $.ajax({
          type : 'get',
          url : "QualityDelete",
          data : {	
          	ID:ID
          },
          
          dataType : 'json',
          success : function (data) {       	        	             	
          	$.MsgBox.Alert('提示','删除成功');
	            $('.MailBar_cover_color').hide();
	            $('.hidePdf').hide();
          },
          error : function () {
              $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
          }
      });
 	   var  that = $(this);
	   $.MsgBox.Confirm('提示','确认要删除吗？',function(){
		   that.parents("tr").remove();
	  });  
	   
 })

</script>
</html>