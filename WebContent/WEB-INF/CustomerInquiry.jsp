
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
<title>客户询价记录</title>

<link rel="shortcut icon" href="image/eoulu.ico" />
<link rel="bookmark" href="image/eoulu.ico" />
<link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="css/CustomerInquiry.css">
</head>

<body>
	<!-- 	头部开始 -->
	<%@include file="top.jsp"%>
	<!-- 	头部结束 -->
	<div class="contain">
		<div class="content">
			<!-- 	=======================导航栏   开始 ================================== -->
			<%@include file="nav.jsp"%>
				<!-- 搜索功能 -->
				 <form action="CustomerInquiry" method="GET" id="search_box"  style="position: absolute;clear:both;right: 5%;top: 80px;">
	            	<input type="text" placeholder="输入关键字查询" class="tsearch" name="Content"  style="" value="${Content}">
	            	<input type="text"  name="currentPage"  style="display:none;" value="${currentPage}">
	            	<input type="text"  name="queryType"  style="display:none;" value="select">
	            	<span class="ysearch" style="right: 27%" onclick="Search()"></span>
            	</form>
            	<div class="choose" style="height:75px;">
					<c:forEach var="authoritiy" items="${authorities}">
	                   	<c:if test="${authoritiy=='CustomerInquiryOperate'}">
							  <input type="button" value="添加" class="bToggle chooseBtn">
	                   	</c:if>
	               	</c:forEach>
	               	  <input type="button" value="导出" class="bToggle exportBtn" >
			    </div>	
				
			<table border="1" cellspacing="0" cellspadding="0" id="table1" class="cf">
				<tr class="table1_title">
					<td style="=width:4.9%;">序号</td>
					<td style="width:21.34%;" >客户名称</td>
					<td style="width:8.21%;" >客户姓名</td>
					<td style="width:12.72%;" >联系手机</td>
					<td style="width:20.83%;" >售前调查表</td>
					<td style="width:7.3%;">报价预览</td>
					<td style="width:12.31%;">报价时间</td>
					<td  style="width:12.31%;">发起服务</td>
				</tr>
				<c:forEach var="orderInfo" items="${datas}" varStatus="status">
					<c:if test="${status.index>0}">
						<tr class="tbody_tr">
							<td  style="max-width: 41px;" class="toggleedit" value="${orderInfo['ID']}"  title="点击修改">${status.index+(currentPage-1)*10}</td>
							<td title="${orderInfo['CustomerName']}" class="tbody_CustomerName">${orderInfo['CustomerName']}</td>
							<td title="${orderInfo['Contact']}"  class="tbody_Contact">${orderInfo['Contact']}</td>
							<td class="tbody_ContactInfo1">${orderInfo['ContactInfo1']}</td>
							<td title="${orderInfo['PreSalesTable']}"  class="tbody_PreSalesTable" style="cursor:pointer;">${orderInfo['PreSalesTable']}</td>
							<td class="PreviewTd"><i class=" fa fa-eye"></i></td>
							<td class="tbody_QuoteTime">${orderInfo['QuoteTime']}</td>
							<c:choose>
								<c:when test="${orderInfo['ServiceTime']=='--'}">
									<td class="InitiateService">未发起</td>
								</c:when>
								<c:otherwise>
									<td class="InitiateService">${orderInfo['ServiceTime']}</td>
								</c:otherwise>
							</c:choose>
							
							<td style="display:none;">${orderInfo['TotalPrice']}</td>
							<td style="display:none;"  class="tbody_Type">${orderInfo['Type']}</td>
							<td style="display:none;" >${orderInfo['SequenceNO']}</td>
							<td style="display:none;" >${orderInfo['Quantity']}</td>
							<td style="display:none;" >${orderInfo['Model']}</td>
							<td style="display:none;" >${orderInfo['PackageName']}</td>
							<td style="display:none;" >${orderInfo['Remarks']}</td>
							<td style="display:none;" >${orderInfo['Price']}</td>
							<td style="display:none;"  class="tbody_CustomerID">${orderInfo['CustomerID']}</td>
						</tr>
					</c:if>
				</c:forEach>
			</table>
			
			<c:set var="queryUrl"
				value="CustomerInquiry?Content=${Content}&currentPage=">
			</c:set>

			<div id="page">
				<div class="pageInfo"> 当前是第&nbsp;<span id="currentPage">${currentPage}</span>&nbsp;页,&nbsp;总计&nbsp;<span id="allPage">${pageCounts}</span>页
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
	<div class="MailBar_cover_color" style="display: none;"></div>
	<div class="cover_color" style="display: none;"></div>

	

	<!--添加  模态框  -->
	<div class="ShipnoticeBox" style="display: none;">
		<div class="contract_title">添加客户询价记录</div>
		<div class="Shipnotice_close">关闭</div>
		<div class="CustomerMsgTitle" >客户询价记录</div>
		<div class="ShipnoticeMsg">
			<ul class="Shipnotice_leftul">
				<li>
					<span class="Shipnotice_span">客户单位</span>
					<div style="display:inline-block;" class="CustomerBox">
						<input type="text" class="add_Customer addHook"> 
						<ul class="Customerlist">
						</ul>
					</div>
				</li>
				<li>
					<span class="Shipnotice_span">客户姓名</span>
					<input type="text" name="CustomerName" value="" disabled="disabled">
				</li>
				<li>
					<span class="Shipnotice_span">联系手机</span>
					<input type="text" class="callnum" value="" disabled="disabled">
				</li>
			</ul>
			<ul class="Shipnotice_rightul">
				<li>
					<span class="Shipnotice_span">售前调查表</span>
					<form class="add_fileBox"  id="add_fileBox1"  method="post" target="myframe" action="CustomerInquiryOperate" enctype="multipart/form-data" >
						<span class="add_fileCont"></span>
						<span class="add_uploadText">上传</span>
					    <input class="add_change add_file" type="file" multiple="multiple" name="fileName"/>
					</form>
				</li>
				<li>
					<span class="Shipnotice_span">询价型号</span>
					<div class="modelBox">
						<input class="ModelInput"  type="text" />
						<span class="add_Model">添加</span>
						<ul class="ModelList">
						</ul>
					</div>
				</li>
			</ul>
			<div  class="Shipnotice_TabBox cf" >
				<table border="1" cellspacing="0" cellspadding="0" id="ShipnoticeTable"  style="width:100%;height:30px;text-align:center;border-color:#00aeef;">
					<tr class="tHead">
						<td class="Shipnotice_Ser" style="width:8.89%;height:30px;line-height:30px;outline:none;"><span class="">序号</span></td>
						<td style="width:18.98%;height:30px;line-height:30px;outline:none;">组件名称</td>
						<td style="width:17.78%;height:30px;line-height:30px;outline:none;">型号</td>
						<td style="width:19.55%;height:30px;line-height:30px;outline:none;">备注</td>
						<td style="width:15.58%;height:30px;line-height:30px;outline:none;">单价</td>
						<td style="width:9.6%;height:30px;line-height:30px;outline:none;">数量</td>
						<td  style="width:9.6%;height:30px;line-height:30px;outline:none;">操作</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="btnBox">
			<input type="button" value="保存" class="bToggle" id="Shipnotice_submit">
			<input type="button" value="取消" class="bToggle" id="Shipnotice_cancel">
		</div>
	</div>
	
	
	<!--修改  模态框  -->
	<div class="InquiryModifyBox" style="display: none;">
		<div class="contract_title">修改客户询价记录</div>
		<div class="Shipnotice_close">关闭</div>
		<div class="CustomerMsgTitle" >客户询价记录</div>
		<div class="ShipnoticeMsg">
			<ul class="Shipnotice_leftul">
				<li>
					<span class="Shipnotice_span">客户单位</span>
					<div style="display:inline-block;" class="CustomerBox">
						<input type="text" class="modify_Customer" > 
						<ul class="Customerlist">
						</ul>
					</div>
				</li>
				<li>
					<span class="Shipnotice_span">客户姓名</span>
					<input type="text" name="CustomerName" value="" disabled="disabled">
				</li>
				<li>
					<span class="Shipnotice_span">联系手机</span>
					<input type="text" class="callnum" value="" disabled="disabled">
				</li>
				
				
			</ul>
			<ul class="Shipnotice_rightul">
				<li>
					<span class="Shipnotice_span">售前调查表</span>
					<form class="add_fileBox"  id="add_fileBox2"  method="post" target="myframe" action="CustomerInquiryOperate" enctype="multipart/form-data" >
						<span class="add_fileCont"></span>
						<span class="add_uploadText">上传</span>
					    <input class="add_change add_file" type="file" multiple="multiple" name="fileName"/>
					</form>
				</li>
				<li>
					<span class="Shipnotice_span">询价型号</span>
					<div class="modelBox">
						<input class="ModelInput"  type="text" />
						<span class="add_Model">添加</span>
						<ul class="ModelList">
						</ul>
					</div>
				</li>
			</ul>
			<div  class="Shipnotice_TabBox cf" >
				<table border="1" cellspacing="0" cellspadding="0" id="ShipnoticeTable"  style="width:100%;height:30px;text-align:center;border-color:#00aeef;">
					<tr class="tHead">
						<td class="Shipnotice_Ser" style="width:8.89%;height:30px;line-height:30px;outline:none;"><span class="">序号</span></td>
						<td style="width:18.98%;height:30px;line-height:30px;outline:none;">组件名称</td>
						<td style="width:17.78%;height:30px;line-height:30px;outline:none;">型号</td>
						<td style="width:19.55%;height:30px;line-height:30px;outline:none;">备注</td>
						<td style="width:15.58%;height:30px;line-height:30px;outline:none;">单价</td>
						<td style="width:9.6%;height:30px;line-height:30px;outline:none;">数量</td>
						<td  style="width:9.6%;height:30px;line-height:30px;outline:none;">操作</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="btnBox">
			<input type="button" value="保存" class="bToggle" id="Modify_submit">
			<input type="button" value="取消" class="bToggle" id="Modify_cancel">
		</div>
	</div>
	<iframe style="display:none;" name="myframe"></iframe>  <!-- 表单提交 -->
	<div class="PreviewBox" style="display: none;">
		<div class="PreviewBox_title">客户询价记录</div>
		<div class="PreviewBox_close">关闭</div>
		<table border="1" cellspacing="0" cellspadding="0" id="ShipnoticeTable"  style="width:96%;text-align:center;border-color:#00aeef;margin:20px auto 0; ">
			<tr class="tHead">
				<td class="" style="width:7.32%;"><span class="">序号</span></td>
				<td style="width:14%;">客户</td>
				<td style="width:20%;">型号</td>
				<td style="width:23.5%;">描述</td>
				<td style="width:;display:none;">价格</td>
				<td  style="width:8%;">数量</td>
				<td style="width:7.2%;">总价</td>
				<td style="width:7%;">类型</td>
				<td  style="width:9.9%;">报价日期</td>
			</tr>
		</table>
		
	</div>
</body>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/msgbox.js"></script>
<!-- <script src="js/msgbox_unload.js"></script> -->
<script src="js/CustomerInquiry.js"></script>
<script>
	if($(".choose").children().length >1){
		$(".toggleedit").addClass("contract-edit")
	}
	else{
		$(".toggleedit").removeClass("contract-edit")
	}
	//点击确定刷新页面
	$(document).on("click", "#mb_btn_ok", function() {
		window.location.reload();
	});

	//点击关闭
	$('.contractAdd_close').click(function() {
		$('.MailBar_cover_color').hide();
		$('.contract_add').hide();
		$(".ShipnoticeBox").hide();
	});
	$('#update_cancel').click(function() {
		$('.MailBar_cover_color').hide();
		$('.contract_update').hide();
	});
	$('.contractUpdate_close').click(function() {
		$('.MailBar_cover_color').hide();
		$('.contract_update').hide();
	});

	//点击取消
	$('#add_cancel').click(function() {
		$('.MailBar_cover_color').hide();
		$('.contract_add').hide();
	});

	/****************** 跳页 **********************/

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

	function fm(obj) {
		var vb = obj.value.split('.');
		var val = vb[0].replace(/\D/, '');
		if (obj.value.length > 0)
			obj.value = val.match(/\d{3}|\d{2}|\d/g).join(',')
					+ (vb.length > 1 ? '.' + vb[1] : '');
	}
	function fmoney(s, n) //s:传入的float数字 ，n:希望返回小数点几位 
	{
		n = n > 0 && n <= 20 ? n : 2;
		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
		var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
		t = "";
		for (i = 0; i < l.length; i++) {
			t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
		}
		return t.split("").reverse().join("") + "." + r;
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

	})


</script>
</html>