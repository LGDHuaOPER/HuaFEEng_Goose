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
<title>商品管理</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" />
<link rel="stylesheet" type="text/css" href="css/MerchandiseControl.css">
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

 			<form id="top_text_from" name="top_text_from" method="get" action="Commodity">
				<input type="text" id="search" name="isSearch" value="search"
					style="display: none;">
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
							value="${fn:split('型号规格,过期价格,商品名称',',')}"></c:set>
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
						<!-- 第二个搜索框 -->
					<c:if test="${queryType !='mixSelect'}">
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
							<input type="text" id="searchContent2" name="searchContent2" value="${parameter2}">
						</div>
					</c:if>
					<c:if test="${queryType =='mixSelect'}">
						<div class="select2" style="display: inline-block">
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
							<input type="text" id="searchContent2" name="searchContent2" value="${parameter2}">
						</div>
					</c:if>
						 <div class="select-button">
							<input type="button" value="搜索" class="bToggle" onclick="INSearch()"> 
							<input type="button" value="取消" class="bToggle" onclick="Cancel()">
						</div>
				</div>
				<div class="choose">
					<input type="button" value="添加" class="bToggle" onclick="AddContract()">
				</div>

		 </form>
				
			<table border="1" cellspacing="0" cellspadding="0" id="table1">
				<tr>
					<td style="width:4%;min-width:40px" >序号</td>
					<!-- <td>修改</td> -->
					<td style="width:15%">商品名称 </td>
					<td>型号规格</td>
					<td style="width:5%; min-width:70px;display:none;">Item</td>
					<td style="min-width:81px; display:none;">Item<br />Description</td>
					<td style="width:4%;min-width:50px">单位</td>
					<td>交货期</td>
					<td style="width:6%;min-width:66px">商品产地 </td>
					<!--新增  -->
					<td style="width:6%;min-width:66px">报价时间 </td>
					<td style="width:6%;min-width:66px">成本单价</td>
					<td style="width:7%;min-width:80px">折扣前成本</td>
					<td style="width:7%;min-width:65px">商品售价一</td>
					<td style="width:7%;min-width:65px">商品售价二</td>
					<td style="width:7%;min-width:65px">商品售价三</td>
					<td style="width:7%;min-width:80px">主要供应商</td>
					<td style="display:none;">删除数据</td>
				</tr>
				 <c:forEach var="orderInfo" items="${commodity}" varStatus="status">
					<c:if test="${status.index>0}">
						<tr class="tbody_tr">
							
							<td  class="xuhao_td  fa-edit contract-edit" value="${orderInfo['ID']}">${status.index+(currentPage-1)*10}</td>
							<!-- <td></td> -->
							<td  title="${orderInfo['CommodityName']}" style="width:15%;max-width:130px" class="CommodityName">${orderInfo['CommodityName']}</td>       <!--2  -->
							<td  title="${orderInfo['Model']}" class="Model" style="max-width:130px">${orderInfo['Model']}</td>   <!--3  -->
							
							<td  title="${orderInfo['Item']}"  class="hideItem"  style="display:none;">${orderInfo['Item']}</td>       <!--2  -->
							<td  title="${orderInfo['ItemDescription']}"  class="ItemDescriptionTd hideDescription"  style="display:none;">${orderInfo['ItemDescription']}</td>   <!--3  -->
							
							<td  class="Unit">${orderInfo['Unit']}</td>    <!--4  -->
							<td  class="DeliveryPeriod">${orderInfo['DeliveryPeriod']}</td>       <!--5  -->
							<td  class="ProducingArea">${orderInfo['ProducingArea']}</td>   <!--6  -->
							<td  title="${orderInfo['QuoteTime']}" class="QuotationTime">${orderInfo['QuoteTime']}</td>   <!--新增  -->
							<td  class="CostPrice">${orderInfo['CostPrice']}</td>    <!--7  -->
							<td  class="DiscountCost">${orderInfo['DiscountCost']}</td>       <!--8  -->
							<td  class="SellerPriceOne">${orderInfo['UnitPrice']}</td>   <!--9  -->
							<td  class="SellerPriceTwo">${orderInfo['SellerPriceTwo']}</td>    <!--10  -->
							<td  class="SellerPriceThree">${orderInfo['SellerPriceThree']}</td>       <!--11 -->
							<td title="${orderInfo['Supplier']}" class="Supplier"  style="width:7%;max-width:80px">${orderInfo['Supplier']}</td>   <!--12  -->
							<td style="display:none;"  class="ProductCategory">${orderInfo['ProductCategory']}</td>
							<%-- <td style="display:none;"  class="del"><i class="fa fa-trash-o del"></i></td>  
							
							<td style="display:none;"  class="SupplierID">${orderInfo['SupplierID']}</td>
							<td style="display:none;"  class="caigouxinghao">${orderInfo['PurchaseModel']}</td>
							<td style="display:none;"  class="lishicaigou">${orderInfo['PurchaseLink']}</td>
							<td style="display:none;"  class="caigouMan">${orderInfo['PurchaseContact']}</td>
							<td style="display:none;"  class="baojiaTime">${orderInfo['QuoteTime']}</td>
							<td style="display:none;"  class="caigouEmail">${orderInfo['PurchaseEmail']}</td>
							<td style="display:none;"  class="fukuantiaojian">${orderInfo['PaymentCondition']}</td>
							<td style="display:none;"  class="baojiaDate">${orderInfo['ValidTime']}</td>
							<td style="display:none;"  class="shoujianMan">${orderInfo['Consignee']}</td>
							<td style="display:none;"  class="chaosongMan">${orderInfo['CCList']}</td>
							<td style="display:none;"  class="zhuti">${orderInfo['Subject']}</td>
							<td style="display:none;"  class="neirong">${orderInfo['Content']}</td>
							<td style="display:none;"  class="MailID">${orderInfo['MailID']}</td> --%>
						</tr>
					</c:if>
				</c:forEach>
			</table>
		
	 	 <c:choose>
				<c:when test="${queryType == 'common'}">
					<c:set var="queryUrl"
					value="Commodity?type1=${classify1}&searchContent1=${parameter1}&selected=${str}&currentPage=">
					</c:set>
				</c:when>
				<c:otherwise>
					<c:set var="queryUrl"
					value="Commodity?type1=${classify1}&searchContent1=${parameter1}&type2=${classify2}&searchContent2=${parameter2}&selected=${str}&currentPage=">
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
	
		<!-- -------------------------  添加装机进展信息 ----------------------------------->
	    <div class="contract_add">
		<div class="contract_title">添加商品管理</div>
		<div class="contractAdd_close">关闭</div>
		<div class="basic_info">
			<!-- <div class="table_title">商品管理</div> -->
			<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
				<tbody>	
					<tr class="table_title_tr">
						<td class="table_title">商品管理</td>
					</tr>
					<tr>
						<td>商品名称</td>
						<td><input type="text" name="CommodityName" value=""  id="CommodityName"></td>
						<td>型号规格</td>
						<td><input type="text" name="Model" value=""  id="Model"></td>
						<td>单位</td>
						<td><input type="text" name="Unit" value=""  id="Unit"></td>
					</tr>
					<tr>
						<td>交货期</td>
						<td class="DeliveryPeriod"><input type="text" name="DeliveryPeriod" value="" style="position: absolute; width: 162px;" id="DeliveryPeriod">
							<span> <select onchange="document.getElementById('DeliveryPeriod').value=this.options[this.selectedIndex].text">
									<option value="" selected="selected">请选择</option>
									<option value="">4 weeks</option>
									<option value="">6 weeks</option>
									<option value="">8 weeks</option>
									<option value="">10 weeks</option>
									<option value="">12 weeks</option>
									<option value="">12-16 weeks</option>
									</select>
							</span>
						</td>
						
						<!-- <td><input type="date" name="DeliveryPeriod" value="" id="DeliveryPeriod"></td> -->
						<td>商品产地</td>
						<td><input type="text" name="ProducingArea" value="" id="ProducingArea"></td>
						<td>成本单价</td>
						<td><input type="text" name="CostPrice" value="" id="CostPrice"></td>
					</tr>
					<tr>
						<td>折扣前成本</td>
						<td><input type="text" name="DiscountCost" value="" id="DiscountCost"></td>
						<td>商品售价一</td>
						<td><input type="text" name="SellerPriceOne" value="" id="SellerPriceOne"></td>
						<td>商品售价二</td>
						<td><input type="text" name="SellerPriceTwo" value="" id="SellerPriceTwo"></td>
					</tr>
					<tr>
						<td>商品售价三</td>
						<td><input type="text" name="SellerPriceThree" value="" id="SellerPriceThree"></td>
						<td>主要供应商</td>
						<td><input type="text" name="Supplier" value="" id="Supplier"></td>
						<td>产品类型</td>
						<td >
					 		<input type="text" name="ProductType" readonly="readonly" value="" style="position:absolute;width:160px;height:22px;"  id="ProductType_add" class="ProductType">
						  	<span>
								<select onchange="document.getElementById('ProductType_add').value=this.options[this.selectedIndex].text" class="ProductTypeSel" style="width: 183px;">
									<option value="No" selected="selected">请选择</option>
									<option value="GERMANY">探针台</option>
									<option value="USA" >射频探针</option>
									<option value="USA" >直流探针</option>
									<option value="GERMANY">线缆</option>
									<option value="USA" >定位器</option>
									<option value="USA" >校准片</option>
									<option value="USA" >其他</option>
								</select>
							  </span>
						</td>	
					</tr>
					
					
					
					<tr class="table_title_tr">
						<td class="table_title">供应商管理</td>
					</tr>
					<tr>
						<td>采购型号</td>
						<td><input type="text" name="caigouxinghao" value="" id="caigouxinghao"></td>
						<td>历史采购链接</td>
						<td><input type="text" name="lishicaigou" value="" id="lishicaigou"></td>
						<td>采购联系人</td>
						<td><input type="text" name="caigouMan" value="" id="caigouMan"></td>
					</tr>
					
					<tr>
						<td>报价有效期</td>
						<td><input type="date" name="baojiaDate" value="" id="baojiaDate"></td>
						<td>采购邮箱</td>
						<td><input type="text" name="caigouEmail" value="" id="caigouEmail"></td>
						<td>付款条件</td>
						<td><input type="text" name="fukuantiaojian" value="" id="fukuantiaojian"></td>
					</tr>
					
					<tr>
						<td>报价时间</td>
						<td><input type="date" name="baojiaTime" value="" id="baojiaTime"></td>
						<td>Item</td>
						<td><input type="text" value="" class="ItemTd"></td>
						<td>Item Description</td>
						<td><input type="text" value="" class="DescriptionTd"></td>
					</tr>
					<tr>
						<td>历史采购记录文件</td>
						<td><span class="upfile_span">上传</span><span class="downfile_span">下载</span><span class="showfile_span">预览</span></td>
					</tr>
					<tr class="table_title_tr">
						<td class="table_title">邮件模板<i class="Email_show fa-plus-square "></i></td>
					</tr>
					<tr class="email-content">
						<td>收件人</td>
						<td><input type="text" name="shoujianMan" value="" id="shoujianMan"></td>
						<td>抄送人</td>
						<td><input type="text" name="chaosongMan" value="" id="chaosongMan"></td>
						<td>抄送人1</td>
						<td><input type="text" name="chaosongMan1" value="" id="chaosongMan1"></td>
					</tr>
					
					<tr class="email-content ">
						<td>抄送人2</td>
						<td><input type="text" name="chaosongMan2" value="" id="chaosongMan2"></td><td>主题</td>
						<td><input type="text" name="zhuti" value="" id="zhuti"></td>
						<td >邮箱密码</td>
						<td><input type="text" name="EmailPassword " placeholder="请输入你的邮箱密码" id="EmailPassword" value="" style="width:80%;height:22px;background:#fff"><span class="passeye" style="margin-left:1%"></span></td>
						
					</tr>
					
					<tr class="email-content ">
						<td>内容</td>
						<td>
<textarea type="text" name="neirong" value="" id="neirong" style="width:350%;height:100px;" contenteditable="true">您好！
我司有XXXXX需要询价，烦请尽快给出最新价格。
顺颂商祺！
</textarea></td>
					<td><span class="sendEmail_span">发送邮件</span></td>
					</tr>
				</tbody>
			</table>		
		</div>
		<div class="edit_btn">
			<input type="button"  value="提交" class="bToggle" id="add_submit">
			<input type="button"   value="取消" class="bToggle" id="add_cancel">
		</div>
	</div> 
	<!-- 文件上传 -->
	<div class="MailBar_cover_color_file_add MailBar_cover_color_file"></div>
 	<div class="MailBar_add MailBar" style="display:none;">
 		
		<div class="operate_title">上传文件</div>
		<div class="MailBar_close">关闭</div>
		
 			<!--不影响使用的代码  -->
			 <input type="file" name="file" id="Mail_fileToUpload1"    value="添加附件" />
			
			<input type="button" name="button" value="上传" class="bToggle" id="Mail_Send1">
		</div>
	
	
	<!-- -------------------------  修改商品管理信息 --------------------------------- -->
	 <div class="contract_update">
		<div class="contract_title">修改商品管理</div>
		<div class="contractUpdate_close">关闭</div>
		<div class="basic_info">
			
			<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
				<tbody>	
					<tr class="table_title_tr">
						<td class="table_title">商品管理</td>
					</tr>
					<tr>
						<td>商品名称</td>
						<td><input type="text" name="CommodityName" value=""  id="CommodityName"></td>
						<td>型号规格</td>
						<td><input type="text" name="Model" value=""  id="Model"></td>
						<td>单位</td>
						<td><input type="text" name="Unit" value=""  id="Unit"></td>
					</tr>
					<tr>
						<td>交货期</td>
						<td class="DeliveryPeriod_update"><input type="text" name="DeliveryPeriod_update" value="" style="position: absolute; width: 162px;" id="DeliveryPeriod_update">
						<span>
							 <select onchange="document.getElementById('DeliveryPeriod_update').value=this.options[this.selectedIndex].text">
									<option value="">请选择</option>
									<option value="">4 weeks</option>
									<option value="">6 weeks</option>
									<option value="">8 weeks</option>
									<option value="">10 weeks</option>
									<option value="">12 weeks</option>
									<option value="">12-16 weeks</option>
									</select>
							</span>
						</td>
						
						
						<td>商品产地</td>
						<td><input type="text" name="ProducingArea" value="" id="ProducingArea"></td>
						<td>成本单价</td>
						<td><input type="text" name="CostPrice" value="" id="CostPrice"></td>
					</tr>
					<tr>
						<td>折扣前成本</td>
						<td><input type="text" name="DiscountCost" value="" id="DiscountCost"></td>
						<td>商品售价一</td>
						<td><input type="text" name="SellerPriceOne" value="" id="SellerPriceOne"></td>
						<td>商品售价二</td>
						<td><input type="text" name="SellerPriceTwo" value="" id="SellerPriceTwo"></td>
					</tr>
					<tr>
						<td>商品售价三</td>
						<td><input type="text" name="SellerPriceThree" value="" id="SellerPriceThree"></td>
						<td>主要供应商</td>
						<td><input type="text" name="Supplier" value="" id="Supplier"></td>
						<td>产品类型</td>
						<td >
					 		<input type="text" name="ProductType_update" value="" style="position:absolute;width:160px;height:22px;"  id="ProductType_update" class="ProductType_update">
						  	<span>
								<select onchange="document.getElementById('ProductType_update').value=this.options[this.selectedIndex].text" class="ProductTypeSel" style="width: 183px;">
									<option value="No">请选择</option>
									<option value="GERMANY">探针台</option>
									<option value="USA" >射频探针</option>
									<option value="USA" >直流探针</option>
									<option value="GERMANY">线缆</option>
									<option value="USA" >定位器</option>
									<option value="USA" >校准片</option>
									<option value="USA" >其他</option>
								</select>
							  </span>
						</td>	
					</tr>
					
					
					
					<tr class="table_title_tr">
						<td class="table_title">供应商管理</td>
						<td id="SupplierID" style="display:none">供应商管理ID</td>
					</tr>
					<tr>
						<td>采购型号</td>
						<td><input type="text" name="caigouxinghao" value="" id="caigouxinghao"></td>
						<td>历史采购链接</td>
						<td><input type="text" name="lishicaigou" value="" id="lishicaigou"></td>
						<td>采购联系人</td>
						<td><input type="text" name="caigouMan" value="" id="caigouMan"></td>
					</tr>
					
					<tr>
						<td>报价有效期</td>
						<td><input type="date" name="baojiaDate" value="" id="baojiaDate"></td>
						<td>采购邮箱</td>
						<td><input type="text" name="caigouEmail" value="" id="caigouEmail"></td>
						<td>付款条件</td>
						<td><input type="text" name="fukuantiaojian" value="" id="fukuantiaojian"></td>
					</tr>
					
					<tr>
						<td>报价时间</td>
						<td><input type="date" name="baojiaTime" value="" id="baojiaTime"></td>
						<td>Item</td>
						<td><input type="text" value="" class="ItemTd"></td>
						<td>Item Description</td>
						<td><input type="text" value="" class="DescriptionTd"></td>
					</tr>
					<tr>
						<td>历史采购记录文件</td>
						<td><span class="upfile_span">上传</span><span class="downfile_span">下载</span><span class="showfile_span">预览</span></td>
					</tr>
					
					<tr class="table_title_tr">
						<td class="table_title">邮件模板<i class="Email_show fa-plus-square "></i></td>
						<td id="MailID" style="display:none">邮件ID</td>
					</tr>
					<tr class="email-content">
						<td>收件人</td>
						<td><input type="text" name="shoujianMan" value="" id="shoujianMan"></td>
						<td>抄送人</td>
						<td><input type="text" name="chaosongMan" value="" id="chaosongMan"></td>
						<td>抄送人1</td>
						<td><input type="text" name="chaosongMan1" value="" id="chaosongMan1"></td>
					</tr>
					
					<tr class="email-content ">
						<td>抄送人2</td>
						<td><input type="text" name="chaosongMan2" value="" id="chaosongMan2"></td>
						<td>主题</td>
						<td><input type="text" name="zhuti" value="" id="zhuti"></td>
						<td >邮箱密码</td>
						<td><input type="text" name="EmailPassword " placeholder="请输入你的邮箱密码" id="EmailPassword" value="" style="width:80%;height:22px;background:#fff"><span class="passeye" style="margin-left:1%"></span></td>
						
					</tr>
					
					<tr class="email-content ">
						<td>内容</td>
						<td>
<textarea type="text" name="neirong" value="" id="neirong" style="width:340%;height:100px;" contenteditable="true">您好！
我司有XXXXX需要询价，烦请尽快给出最新价格。
顺颂商祺！
</textarea></td>
					<td><span class="sendEmail_span" style="left: 81%;">发送邮件</span></td>
					</tr>
				</tbody>
			</table>		
		</div>
		<div class="edit_btn">
			<span value="提交" class="bToggle" id="update_submit">提交</span>
			<span value="取消" class="bToggle" id="update_cancel">取消</span>
		</div>
	 </div> 
 <!-- 文件上传 -->
	<div class="MailBar_cover_color_file_update  MailBar_cover_color_file"></div>
 	<div class="MailBar_update MailBar" style="display:none;">
 		
		<div class="operate_title">上传文件</div>
		<div class="MailBar_close">关闭</div>
		
 			<!--不影响使用的代码  -->
			 <input type="file" name="file" id="Mail_fileToUpload"   value="添加附件" />
			
			<input type="button" name="button" value="上传" class="bToggle" id="Mail_Send">
		</div>
	
</body>
<script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script>
<script src="plugins/cookie/jquery.cookie.js"></script>
<script src="js/msgbox.js"></script>
<script src="js/msgbox_unload.js"></script>
<script src="js/ajaxfileupload.js" ></script>
<script src="js/MerchandiseControl.js"></script>
<script>
//小眼睛
$(".contract_add span.passeye").click(function(){

		        if ($(".contract_add #EmailPassword").attr("type") == "text") {
		            $(".contract_add #EmailPassword")[0].type = "password";  
		        }else{
		          $(".contract_add #EmailPassword")[0].type = "text"; 
		        }   
		    });
$(".contract_update span.passeye").click(function(){
		        if ($(".contract_update #EmailPassword").attr("type") == "text") {
		            $(".contract_update #EmailPassword")[0].type = "password";  
		        }else{
		          $(".contract_update #EmailPassword")[0].type = "text"; 
		        }   
		    });

/*********************添加装机进展信息************************/
/* 添加商品管理信息 */
 $(document).on("click","#add_submit",function(){
		console.log("111")
		var CommodityName=$('.contract_add input[name="CommodityName"]').val();
	    var Model=$('.contract_add input[name="Model"]').val();
	    var Unit=$('.contract_add input[name="Unit"]').val();
	    var DeliveryPeriod=$('.contract_add input[name="DeliveryPeriod"]').val();
	    var ProducingArea=$('.contract_add input[name="ProducingArea"]').val();
	    var CostPrice=$('.contract_add input[name="CostPrice"]').val();
	    var DiscountCost=$('.contract_add input[name="DiscountCost"]').val();
	    var SellerPriceOne=$('.contract_add input[name="SellerPriceOne"]').val();
	    var SellerPriceTwo=$('.contract_add input[name="SellerPriceTwo"]').val();
	    var SellerPriceThree=$('.contract_add input[name="SellerPriceThree"]').val();
	    var Supplier=$('.contract_add input[name="Supplier"]').val();
	    var ID="0";
		var ProductCategory = $('.contract_add input[name="ProductType"]').val();
		var Item = $('.contract_add .ItemTd').val();
		var ItemDescription = $('.contract_add .DescriptionTd').val();
		
		console.log(ProductCategory)
	    console.log(CommodityName);
		console.log(Model);
		console.log(Unit);
		console.log(DeliveryPeriod);
		console.log(ProducingArea);
		console.log(CostPrice);
		console.log(DiscountCost);
		console.log(SellerPriceOne);
		console.log(SellerPriceTwo);
		console.log(SellerPriceThree);
		console.log(Supplier);
		console.log("Item=="+Item);
		console.log("ItemDescription=="+ItemDescription);
		console.log("ID"+ID);
		if($("#ProductType_add").val() == ""){
			 $.MsgBox_Unload.Alert('提示','请选择产品类型！');
			 return;
		}
		/* 供应商管理 */
		var caigouxinghao = $(".contract_add #caigouxinghao").val();
		var lishicaigou = $(".contract_add #lishicaigou").val();
		var caigouMan = $(".contract_add #caigouMan").val();
		var baojiaDate = $(".contract_add #baojiaDate").val();
		var caigouEmail = $(".contract_add #caigouEmail").val();
		var fukuantiaojian = $(".contract_add #fukuantiaojian").val();
		var baojiaTime = $(".contract_add #baojiaTime").val();
		
		/* 邮件模板 */
		var shoujianMan = $(".contract_add #shoujianMan").val();
		var chaosongMan = $(".contract_add #chaosongMan").val();
		var chaosongMan1 = $(".contract_add #chaosongMan1").val();
		var chaosongMan2 = $(".contract_add #chaosongMan2").val();
		if(chaosongMan != '' && chaosongMan1 != '' && chaosongMan2 != '' ){
			var CCList = chaosongMan +','+ chaosongMan1 +','+ chaosongMan2 ;
		}else if(chaosongMan != '' && chaosongMan1 != '' && chaosongMan2 == '' ){
			var CCList = chaosongMan +','+ chaosongMan1;
		}else if(chaosongMan != '' && chaosongMan1 == '' && chaosongMan2 == ''){
			var CCList = chaosongMan +','+ chaosongMan2;
		}else if(chaosongMan == '' && chaosongMan1 == '' && chaosongMan2 == ''){
			var CCList ='';
		}
		var zhuti = $(".contract_add #zhuti").val();
		var neirong = $(".contract_add #neirong").val();
		var Password = $(".contract_add #EmailPassword").val();
		if(CommodityName == ''){
			 $.MsgBox_Unload.Alert("提示", "商品名称必填！");
			 return;
		}else if(Model == ''){
			 $.MsgBox_Unload.Alert("提示", "型号规格必填！");
			 return;
		}
 	   $.ajax({
	        type : 'get',
	        url : 'QuoteCommodityOperate',
	        data : {
	        	ID : ID,
	        	CommodityName : CommodityName,
	        	Model : Model,
	        	Unit : Unit,
	        	DeliveryPeriod : DeliveryPeriod,
	        	ProducingArea : ProducingArea,
	        	CostPrice : CostPrice,
	        	DiscountCost : DiscountCost,
	        	SellerPriceOne : SellerPriceOne,
	        	SellerPriceTwo : SellerPriceTwo,
	        	SellerPriceThree : SellerPriceThree,
	        	Supplier : Supplier,
	        	ProductCategory :ProductCategory,
	        	
	        	SupplierID : 0,
	        	PurchaseModel :caigouxinghao,
	        	PurchaseLink :lishicaigou,
	        	PurchaseContact :caigouMan,
	        	QuoteTime :baojiaTime,
	        	PurchaseEmail :caigouEmail,
	        	PaymentCondition :fukuantiaojian,
	        	ValidTime :baojiaDate,
	        	
	        	
				MailID:0,
				Consignee : shoujianMan ,
				CCList : CCList ,
				Subject : zhuti ,
				Content : neirong
	        },
	        dataType : 'json',
	        success : function (data) {
	        	console.log(data);
	        	
		            $('.MailBar_cover_color').hide();
		            $('.contract_add').hide();
		            $.MsgBox.Alert('提示',data);
	        	/* if(data){
		            $.MsgBox.Alert('提示','添加成功');
		            $('.MailBar_cover_color').hide();
		            $('.contract_add').hide();
	        	}else{
	        		 $.MsgBox.Alert('提示','添加失败请稍候再试！');
	        	} */
	        },
	        error : function () {
	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    }); 
	    	    
    }); 
    
    
/* 修改商品管理信息 */
 $(document).on("click",".contract-edit",function(){	
	var tr=$(this).parent();
	var  ID = tr.find('td.xuhao_td').attr("value");

    $('.contract_update input[name="CommodityName"]').val(tr.find('td.CommodityName').text());
	$('.contract_update input[name="Model"]').val(tr.find('td.Model').text());
	$('.contract_update input[name="Unit"]').val(tr.find('td.Unit').text());
	$('.contract_update input[name="DeliveryPeriod_update"]').val(tr.find('td.DeliveryPeriod').text());
	$('.contract_update input[name="ProducingArea"]').val(tr.find('td.ProducingArea').text());
	$('.contract_update input[name="CostPrice"]').val(tr.find('td.CostPrice').text());
	$('.contract_update input[name="DiscountCost"]').val(tr.find('td.DiscountCost').text());
	$('.contract_update input[name="SellerPriceOne"]').val(tr.find('td.SellerPriceOne').text());
	$('.contract_update input[name="SellerPriceTwo"]').val(tr.find('td.SellerPriceTwo').text());
	$('.contract_update input[name="SellerPriceThree"]').val(tr.find('td.SellerPriceThree').text());
	$('.contract_update input[name="Supplier"]').val(tr.find('td.Supplier').text());
	$('.contract_update input[name="ProductType_update"]').val(tr.find('td.ProductCategory').text());
	
	$(".contract_update .contract_title").attr("value",ID);  //在修改页面保存当前行的ID信息
	$('.contract_update .ItemTd').val(tr.find('td.hideItem').text());
	$('.contract_update .DescriptionTd').val(tr.find('td.hideDescription').text());
	
	
	
	/* 供应商管理和邮件模板 */
	$(".contract_update #baojiaTime").val(tr.find('td.QuotationTime').text());
	  $.ajax({
			type:'get',
			url:'GetCommodityOtherInfo',
			data:{
				ID : ID,
			},
			dataType : 'json',
			success : function (data) {
	      	console.log(data);
	      	$(".contract_update #shoujianMan").val(data[1].Consignee);
	      	$(".contract_update #neirong").text(data[1].Content);
	      	$(".contract_update #MailID").text();
	      	$(".contract_update #fukuantiaojian").val(data[1].PaymentCondition);
	      	$(".contract_update #caigouMan").val(data[1].PurchaseContact);
	      	$(".contract_update #caigouEmail").val(data[1].PurchaseEmail);
	      	$(".contract_update #lishicaigou").val(data[1].PurchaseLink);
	      	$(".contract_update #caigouxinghao").val(data[1].PurchaseModel);
	      	$(".contract_update #zhuti").val(data[1].Subject);
	      	$(".contract_update #SupplierID").text(data[1].SupplierID);
	      	$(".contract_update #baojiaDate").val(data[1].ValidTime);
	      	var chaosong =data[1].CCList;
	    	if(chaosong == '' || chaosong == '--'){
	    		$(".contract_update #chaosongMan").val('');
	    		$(".contract_update #chaosongMan1").val('');
	    		$(".contract_update #chaosongMan2").val('');
	    	}else if(chaosong != '' && chaosong != '--'  && chaosong.match(',') == null){
	    		$(".contract_update #chaosongMan").val(chaosong);
	    		$(".contract_update #chaosongMan1").val('');
	    		$(".contract_update #chaosongMan2").val('');
	    	}else {
	    		var chaosongArr = tr.find('td.chaosongMan').text().split(',');
	    		if(chaosongArr.length == 2){
	    			$(".contract_update #chaosongMan").val(chaosongArr[0]);
	    			$(".contract_update #chaosongMan1").val(chaosongArr[1]);
	    			$(".contract_update #chaosongMan2").val('');
	    		}else if(chaosongArr.length == 3){
	    			$(".contract_update #chaosongMan").val(chaosongArr[0]);
	    			$(".contract_update #chaosongMan1").val(chaosongArr[1]);
	    			$(".contract_update #chaosongMan2").val(chaosongArr[2]);
	    		}
	    	}
	      },
	      error : function () {
	          $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	      }
		})
	//邮件部分的隐藏
	$(".email-content").hide();
    $('.MailBar_cover_color').show(); 
    $('.contract_update .Email_show').removeClass('fa-minus-square');
   $('.contract_update').show(); 
   
  var xuhao = $(this).text();
  <%-- <td style="display:none;"  class="del"><i class="fa fa-trash-o del"></i></td>  
	<td style="display:none;"  class="ProductCategory">${orderInfo['ProductCategory']}</td>
	<td style="display:none;"  class="SupplierID">${orderInfo['SupplierID']}</td>
	<td style="display:none;"  class="caigouxinghao">${orderInfo['PurchaseModel']}</td>
	<td style="display:none;"  class="lishicaigou">${orderInfo['PurchaseLink']}</td>
	<td style="display:none;"  class="caigouMan">${orderInfo['PurchaseContact']}</td>
	<td style="display:none;"  class="baojiaTime">${orderInfo['QuoteTime']}</td>
	<td style="display:none;"  class="caigouEmail">${orderInfo['PurchaseEmail']}</td>
	<td style="display:none;"  class="fukuantiaojian">${orderInfo['PaymentCondition']}</td>
	<td style="display:none;"  class="baojiaDate">${orderInfo['ValidTime']}</td>
	<td style="display:none;"  class="shoujianMan">${orderInfo['Consignee']}</td>
	<td style="display:none;"  class="chaosongMan">${orderInfo['CCList']}</td>
	<td style="display:none;"  class="zhuti">${orderInfo['Subject']}</td>
	<td style="display:none;"  class="neirong">${orderInfo['Content']}</td>
	<td style="display:none;"  class="MailID">${orderInfo['MailID']}</td> --%>

/*   所有的input不可修改 */
 /*   $(".contract_update input").attr("disabled",true);
  $(".contract_update input").css({ */
	  
	  /*  "background":"#fff" , */
/* 	   "border":"none"
  });  */
  /* alert(xuhao) */
  /* 
  $("#table1 tr").eq($(this).text()).after($(".contract_update"));
  $('.contract_update').show();  */  	    
 }); 
   	
	/*  提交修改后的信息  */
 	$('#update_submit').click(function () {
 		
 		var  ID = $(".contract_update .contract_title").attr("value");
		var CommodityName = $('.contract_update input[name="CommodityName"]').val();
		var Model = $('.contract_update input[name="Model"]').val();
		var Unit = $('.contract_update input[name="Unit"]').val();
		var DeliveryPeriod = $('.contract_update input[name="DeliveryPeriod_update"]').val();
		var ProducingArea = $('.contract_update input[name="ProducingArea"]').val();
		var CostPrice = $('.contract_update input[name="CostPrice"]').val();
		var DiscountCost = $('.contract_update input[name="DiscountCost"]').val();
		var SellerPriceOne = $('.contract_update input[name="SellerPriceOne"]').val();
		var SellerPriceTwo = $('.contract_update input[name="SellerPriceTwo"]').val();
		var SellerPriceThree = $('.contract_update input[name="SellerPriceThree"]').val();
		var Supplier = $('.contract_update input[name="Supplier"]').val();
		var ProductCategory = $('.contract_update input[name="ProductType_update"]').val();
		var Item = $('.contract_update .ItemTd').val();
		var ItemDescription = $('.contract_update .DescriptionTd').val();
		
		console.log("ID"+ID);
		console.log(CommodityName);
		console.log(Model);
		console.log(Unit);
		console.log(DeliveryPeriod);
		console.log(ProducingArea);
		console.log(CostPrice);
		console.log(DiscountCost);
		console.log(SellerPriceOne);
		console.log(SellerPriceTwo);
		console.log(SellerPriceThree);
		console.log(Supplier);console.log(Supplier);
		console.log(ProductCategory);
		console.log("Item=="+Item);
		console.log("ItemDescription=="+ItemDescription);
		
		if(CommodityName == ''){
			 $.MsgBox_Unload.Alert("提示", "商品名称必填！");
			 return;
		}else if(Model == ''){
			 $.MsgBox_Unload.Alert("提示", "型号规格必填！");
			 return;
		}
		
		var ProductType = $('.contract_update input[name="ProductType"]').val();
		if(ProductType == '--'){
			ProductType = '';
		}
		var caigouxinghao = $(".contract_update #caigouxinghao").val();
		if(caigouxinghao == '--'){
			caigouxinghao = '';
		}
		var lishicaigou = $(".contract_update #lishicaigou").val();
		if(lishicaigou == '--'){
			lishicaigou = '';
		}
		var caigouMan = $(".contract_update #caigouMan").val();
		if(caigouMan == '--'){
			caigouMan = '';
		}
		var baojiaDate = $(".contract_update #baojiaDate").val();
		if(baojiaDate == '--'){
			baojiaDate = '';
		}
		var caigouEmail = $(".contract_update #caigouEmail").val();
		if(caigouEmail == '--'){
			caigouEmail = '';
		}
		var fukuantiaojian = $(".contract_update #fukuantiaojian").val();
		if(fukuantiaojian == '--'){
			fukuantiaojian = '';
		}
		var baojiaTime = $(".contract_update #baojiaTime").val();
		if(baojiaTime == '--'){
			baojiaTime = '';
		}
		var shoujianMan = $(".contract_update #shoujianMan").val();
		if(shoujianMan == '--'){
			shoujianMan = '';
		}
		var chaosongMan = $(".contract_update #chaosongMan").val();
		if(chaosongMan == '--'){
			chaosongMan = '';
		}
		var chaosongMan1 = $(".contract_update #chaosongMan1").val();
		if(chaosongMan1 == '--'){
			chaosongMan1 = '';
		}
		var chaosongMan2 = $(".contract_update #chaosongMan2").val();
		if(chaosongMan2 == '--'){
			chaosongMan2 = '';
		}
		if(chaosongMan != '' && chaosongMan1 != '' && chaosongMan2 != '' ){
			var CCList = chaosongMan +','+ chaosongMan1 +','+ chaosongMan2 ;
		}else if(chaosongMan != '' && chaosongMan1 != '' && chaosongMan2 == '' ){
			var CCList = chaosongMan +','+ chaosongMan1;
		}else if(chaosongMan != '' && chaosongMan1 == '' && chaosongMan2 == ''){
			var CCList = chaosongMan +','+ chaosongMan2;
		}else if(chaosongMan == '' && chaosongMan1 == '' && chaosongMan2 == ''){
			var CCList ='';
		}
		var zhuti = $(".contract_update #zhuti").val();
		if(zhuti == '--'){
			zhuti = '';
		}
		
		 var neirong = $(".contract_update #neirong").val();
			if(neirong == '--'){
				neirong = '';
			}
		 /*alert(neirong) */
		var SupplierID = $("#SupplierID").text();
		if(SupplierID == '--'||"供应商管理ID"){
			SupplierID = 0;
		}
		var MailID = $("#MailID").text();
		if(MailID == '--'||"邮件ID"){
			MailID = 0;
		}
		
		console.log("SupplierID=="+SupplierID)
		  $.ajax({
		      type : 'get',
		      url : 'QuoteCommodityOperate',
		      data : {
		    	  	ID : ID,
		    	  	CommodityName : CommodityName,
		        	Model : Model,
		        	Unit : Unit,
		        	DeliveryPeriod : DeliveryPeriod,
		        	ProducingArea : ProducingArea,
		        	CostPrice : CostPrice,
		        	DiscountCost : DiscountCost,
		        	SellerPriceOne : SellerPriceOne,
		        	SellerPriceTwo : SellerPriceTwo,
		        	SellerPriceThree : SellerPriceThree,
		        	Supplier : Supplier,
		        	ProductCategory :ProductCategory,
		        	
		        	
		        	PurchaseModel : caigouxinghao,
		        	PurchaseLink : lishicaigou,
		        	PurchaseContact : caigouMan,
		        	QuoteTime : baojiaTime,
		        	PurchaseEmail : caigouEmail,
		        	PaymentCondition : fukuantiaojian,
		        	ValidTime : baojiaDate,
		        	Consignee : shoujianMan,
		        	CCList: CCList,
		        	Subject : zhuti ,
		        	Content : neirong,
		        	MailID : MailID,
		        	SupplierID : SupplierID,
		        	Item :Item,
		        	ItemDescription :ItemDescription
		      },
		      dataType : 'json',
		      success : function (data) {
		    	  console.log(data);
		    	  $.MsgBox.Alert('提示',data);
		          $('.MailBar_cover_color').hide();
		          $('.contract_add').hide();
		      	/* if(data){
			          $.MsgBox.Alert('提示','修改成功');
			          $('.MailBar_cover_color').hide();
			          $('.contract_add').hide();
		      	}else{
		      		 $.MsgBox.Alert('提示','修改失败请稍候再试！');
		      	} */
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
	if(ddd.getMonth()<10){
	var month = "0"+(ddd.getMonth()+1); 
	}
	if(ddd.getDate()<10){
	 day = "0"+ddd.getDate(); 
	}
	var datew = ddd.getFullYear()+"-"+month+"-"+day;
	datew = datew.toString();
	
	/* 邮件部分的显示 */
	$(".contract_add .email-content").hide();
	
	$("#Date").val(datew);
    $('.MailBar_cover_color').show();
    $('.contract_add .Email_show').removeClass('fa-minus-square');
    $('.contract_add').show();
};

	$('.contract_add .Email_show').bind('click',function(){
		 $('.contract_add .Email_show').toggleClass('fa-minus-square');
		 $(".contract_add .email-content").toggle();
		});
	
	$('.contract_update .Email_show').bind('click',function(){
		 $('.contract_update .Email_show').toggleClass('fa-minus-square');
		 $(".contract_update .email-content").toggle();
		});


/* 点击确定刷新页面 */
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