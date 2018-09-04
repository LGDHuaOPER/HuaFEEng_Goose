<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>物流系统</title>
</head>
<body>
<form action="ModifyOrder" method="post">
ID：<input type="text" name="id" value="5">
客户名称：<input type="text" name="customer" value="zhangkai">

合同号：<input type="text" name="contract_no" value="TSQ20170330">

合同名称：<input type="text" name="contract_title" value="测试公司合同">
联系人：<input type="text" name="contact" value="大侠">

联系方式：<input type="text" name="contact_info" value="123456885">
合同签订日期：<input type="text" name="date_of_sign" value="2017-03-21">
产品货期：<input type="text" name="cargo_period" value="2017-03-21">

实际货期：<input type="text" name="actual_delivery" value="2017-03-21">
预计货期：<input type="text" name="expected_delivery_period" value="2017-03-21">

装机时间：<input type="text" name="installed_time" value="2017-03-22">
装机地点：<input type="text" name="installed_site" value="成都">
备注：<input type="text" name="remarks" value="这是备注信息">

<input type="text" name="rmb_quotes" value="100">
<input type="text" name="usd_quotes" value="900">

2：<input type="text" name="whether_to_pay" value="已付款">
3：<input type="text" name="whether_to_invoice" value="是">
<br>

=============<br>

合同地区：<select name="area">
<c:forEach var="area" items="${Areas}" varStatus="status">
<c:if test="${status.index>0}">
<option value="${area.ID}">${area.AreaName}</option>
</c:if>
</c:forEach>
</select><br>


合同状态：<select name="status">
<c:forEach var="ContractStatus" items="${ContractStatus}" varStatus="status">
<c:if test="${status.index>0}">
<option value="${ContractStatus.ID}">${ContractStatus.Status }</option>
</c:if>
</c:forEach>
</select><br>


<!-- 是否付款：<select> -->
<%-- <c:forEach var="equipment" items="${equipments}" varStatus="status"> --%>
<%-- <c:if test="${status.index>0}"> --%>
<%-- <option value="${equipment.ID}">${equipment.DeviceName } : ${equipment.Model }</option> --%>
<%-- </c:if> --%>
<%-- </c:forEach> --%>
<!-- </select><br> -->
<input name="whether_to_pay" value="1">

付款条件：<select name="payment_terms">
<c:forEach var="PayTerm" items="${PayTerms}" varStatus="status">
<c:if test="${status.index>0}">
<option value="${PayTerm.ID}">${PayTerm.Condition }</option>
</c:if>
</c:forEach>
</select><br>


是否办理免税信用证：<select name="duty_free">
<c:forEach var="Duty" items="${DutyFree}" varStatus="status">
<c:if test="${status.index>0}">
<option value="${Duty.ID}">${Duty.Status }</option>
</c:if>
</c:forEach>
</select><br>


销售代表：<select name="sales_representative">
<c:forEach var="SaleRep" items="${SalesRep}" varStatus="status">
<c:if test="${status.index>0}">
<option value="${SaleRep.ID}">${SaleRep.Name }</option>
</c:if>
</c:forEach>
     </select><br> <br>

============<br>

<input type="submit" value="提交">
</form>



=========================订单添加   开始===================================<br>
<form action="ModifyOrder" method="post">
ID：<input type="text" name="id" value="5">
客户名称：<input type="text" name="customer" value="zhangkai">

合同号：<input type="text" name="contract_no" value="TSQ20170330">

合同名称：<input type="text" name="contract_title" value="测试公司合同">
联系人：<input type="text" name="contact" value="大侠">

联系方式：<input type="text" name="contact_info" value="123456885">
合同签订日期：<input type="text" name="date_of_sign" value="2017-03-21">
产品货期：<input type="text" name="cargo_period" value="2017-03-21">

实际货期：<input type="text" name="actual_delivery" value="2017-03-21">
预计货期：<input type="text" name="expected_delivery_period" value="2017-03-21">

装机时间：<input type="text" name="installed_time" value="2017-03-22">
装机地点：<input type="text" name="installed_site" value="成都">
备注：<input type="text" name="remarks" value="这是备注信息">



2：<input type="text" name="whether_to_pay" value="已付款">
3：<input type="text" name="whether_to_invoice" value="1">
<br>

=============<br>

合同地区：<select name="area">
<c:forEach var="area" items="${Areas}" varStatus="status">
<c:if test="${status.index>0}">
<option value="${area.ID}">${area.AreaName}</option>
</c:if>
</c:forEach>
</select><br>


合同状态：<select name="status">
<c:forEach var="ContractStatus" items="${ContractStatus}" varStatus="status">
<c:if test="${status.index>0}">
<option value="${ContractStatus.ID}">${ContractStatus.Status }</option>
</c:if>
</c:forEach>
</select><br>


<!-- 是否付款：<select> -->
<%-- <c:forEach var="equipment" items="${equipments}" varStatus="status"> --%>
<%-- <c:if test="${status.index>0}"> --%>
<%-- <option value="${equipment.ID}">${equipment.DeviceName } : ${equipment.Model }</option> --%>
<%-- </c:if> --%>
<%-- </c:forEach> --%>
<!-- </select><br> -->


付款条件：<select name="payment_terms">
<c:forEach var="PayTerm" items="${PayTerms}" varStatus="status">
<c:if test="${status.index>0}">
<option value="${PayTerm.ID}">${PayTerm.Condition }</option>
</c:if>
</c:forEach>
</select><br>


是否办理免税信用证：<select name="duty_free">
<c:forEach var="Duty" items="${DutyFree}" varStatus="status">
<c:if test="${status.index>0}">
<option value="${Duty.ID}">${Duty.Status }</option>
</c:if>
</c:forEach>
</select><br>


销售代表：<select name="sales_representative">
<c:forEach var="SaleRep" items="${SalesRep}" varStatus="status">
<c:if test="${status.index>0}">
<option value="${SaleRep.ID}">${SaleRep.Name }</option>
</c:if>
</c:forEach>
     </select><br> <br>

============<br>

<input type="submit" value="提交">
</form>
===============================订单添加   结束==================================================<br>


<!-- <!-- ===============================无参搜索  开始==================================================<br> --> -->
<!-- <table border="1px solid gray"> -->
<!-- <tr> -->
<!-- <td>客户名称</td> -->
<!-- <td>合同地区</td> -->
<!-- <td>合同号</td> -->
<!-- <td>合同名称</td> -->
<!-- <td>销售代表</td> -->
<!-- <td>联系人</td> -->
<!-- <td>联系方式</td> -->
<!-- <td>合同签订日期</td> -->
<!-- <td>合同货期</td> -->
<!-- <td>实际货期</td> -->
<!-- <td>预计货期</td> -->
<!-- <td>合同状态</td> -->
<!-- <td>装机时间和地点</td> -->
<!-- <td>备注</td> -->
<!-- </tr> -->
<%-- <c:forEach var="orderInfo" items="${orders }" varStatus="status" > --%>
<%-- <c:if test="${status.index>0}"> --%>
<!-- <tr> -->
<%-- <td>${orderInfo.ID}</td> --%>
<%-- <td>${orderInfo.Area}</td> --%>
<%-- <td>${orderInfo.ContractNo}</td> --%>
<%-- <td>${orderInfo.ContractTitle}</td> --%>
<%-- <td>${orderInfo.SalesRepresentative}</td> --%>
<%-- <td>${orderInfo.Contact}</td> --%>
<%-- <td>${orderInfo.ContactInfo}</td> --%>
<%-- <td>${orderInfo.DateOfSign}</td> --%>
<%-- <td>${orderInfo.CargoPeriod}</td> --%>
<%-- <td>${orderInfo.ActualDelivery}</td> --%>
<%-- <td>${orderInfo.ExpectedDeliveryPeriod}</td> --%>
<%-- <td>${orderInfo.Status}</td> --%>
<%-- <td>${orderInfo.InstalledTime}${orderInfo.InstalledSite}</td> --%>
<%-- <td>${orderInfo.Remarks}</td> --%>
<!-- </tr> -->
<%-- </c:if> --%>
<%-- </c:forEach> --%>
<!-- </table> -->


<%-- <c:if test="${currentPage>1 }"> --%>
<%-- <a href="Price?currentPage=${currentPage-1 }">上一页</a><br> --%>
<%-- </c:if> --%>

<%-- 当前页<a>${currentPage }</a><br> --%>

<%-- <c:if test="${currentPage<pageCounts }"> --%>
<%-- <a href="Price?currentPage=${currentPage+1 }">下一页</a><br> --%>
<%-- </c:if> --%>
<!-- ===============================无参搜索  结束==================================================<br> -->


<!-- ===============================有参搜索  开始==================================================<br> -->

<!--       <span id="sou">&nbsp;搜索</span> -->
<!--        <select id="zu1"> -->
<!--       <option value='salesman_name'>销售代表</option> -->
<!--       <option value="model_no">产品型号</option> -->
<!--       <option value="customer_name">客户名称</option> -->
<!--       <option value="contract_area">合同地区</option> -->
<!--       <option value="contract_name">合同名称</option> -->
<!--       <option value="contract_time">合同签订日期</option> -->
<!--       <option value="install_time_area">装机时间和地点</option> -->
<!--       <option value="supplier_name">供应商</option> -->
<!--       <option value="supplier_time">工厂货期</option> -->
<!--       <option value="goods_time">产品货期</option> -->
<!--       </select><input type="text" id="v1" > -->
<!--       <select id="zu2"> -->
<!--       <option value='salesman_name'>销售代表</option> -->
<!--       <option value="model_no">产品型号</option> -->
<!--       <option value="customer_name">客户名称</option> -->
<!--       <option value="contract_area">合同地区</option> -->
<!--       <option value="contract_name">合同名称</option> -->
<!--       <option value="contract_time">合同签订日期</option> -->
<!--       <option value="install_time_area">装机时间和地点</option> -->
<!--       <option value="supplier_name">供应商</option> -->
<!--       <option value="supplier_time">工厂货期</option> -->
<!--       <option value="goods_time">产品货期</option> -->
<!--       </select><input type="text" id="v2"><span id="zuhe">组合查询</span> -->

<!-- <table border="1px solid gray"> -->
<!-- <tr> -->
<!-- <td>客户名称</td> -->
<!-- <td>合同地区</td> -->
<!-- <td>合同号</td> -->
<!-- <td>合同名称</td> -->
<!-- <td>销售代表</td> -->
<!-- <td>联系人</td> -->
<!-- <td>联系方式</td> -->
<!-- <td>合同签订日期</td> -->
<!-- <td>合同货期</td> -->
<!-- <td>实际货期</td> -->
<!-- <td>预计货期</td> -->
<!-- <td>合同状态</td> -->
<!-- <td>装机时间和地点</td> -->
<!-- <td>备注</td> -->
<!-- </tr> -->
<%-- <c:forEach var="orderInfo" items="${orders }" varStatus="status" > --%>
<%-- <c:if test="${status.index>0}"> --%>
<!-- <tr> -->
<%-- <td>${orderInfo['ID']}</td> --%>
<%-- <td>${orderInfo['Area']}</td> --%>
<%-- <td>${orderInfo['ContractNo']}</td> --%>
<%-- <td>${orderInfo['ContractTitle']}</td> --%>
<%-- <td>${orderInfo['SalesRepresentative']}</td> --%>
<%-- <td>${orderInfo['Contact']}</td> --%>
<%-- <td>${orderInfo['ContactInfo']}</td> --%>
<%-- <td>${orderInfo['DateOfSign']}</td> --%>
<%-- <td>${orderInfo['CargoPeriod']}</td> --%>
<%-- <td>${orderInfo['ActualDelivery']}</td> --%>
<%-- <td>${orderInfo['ExpectedDeliveryPeriod']}</td> --%>
<%-- <td>${orderInfo['Status']}</td> --%>
<%-- <td>${orderInfo['InstalledTime']}${orderInfo['InstalledSite']}</td> --%>
<%-- <td>${orderInfo['Remarks']}</td> --%>
<!-- </tr> -->
<%-- </c:if> --%>
<%-- </c:forEach> --%>
<!-- </table> -->


<%-- <c:if test="${currentPage>1 }"> --%>
<%-- <a href="GetOrderByPageOne?classify=销售代表&parameter=张&currentPage=${currentPage-1 }">上一页</a><br> --%>
<%-- </c:if> --%>

<%-- 当前页<a>${currentPage }</a><br> --%>

<%-- <c:if test="${currentPage<pageCounts }"> --%>
<%-- <a href="GetOrderByPageOne?classify=销售代表&parameter=张&currentPage=${currentPage+1 }">下一页</a><br> --%>
<%-- </c:if> --%>
<!-- ===============================有参搜索  结束==================================================<br> -->


<!-- 设备型号：<select> -->
<%-- <c:forEach var="equipment" items="${equipments}" varStatus="status"> --%>
<%-- <c:if test="${status.index>0}"> --%>
<%-- <option value="${equipment.ID}">${equipment.DeviceName } : ${equipment.Model }</option> --%>
<%-- </c:if> --%>
<%-- </c:forEach> --%>
<!-- </select><br> -->



<!-- 合同地区：<select> -->
<%-- <c:forEach var="area" items="${Areas}" varStatus="status"> --%>
<%-- <c:if test="${status.index>0}"> --%>
<%-- <option value="${area.ID}">${area.AreaName}</option> --%>
<%-- </c:if> --%>
<%-- </c:forEach> --%>
<!-- </select><br> -->


<!-- 合同状态：<select> -->
<%-- <c:forEach var="ContractStatus" items="${ContractStatus}" varStatus="status"> --%>
<%-- <c:if test="${status.index>0}"> --%>
<%-- <option value="${ContractStatus.ID}">${ContractStatus.Status }</option> --%>
<%-- </c:if> --%>
<%-- </c:forEach> --%>
<!-- </select><br> -->


<!-- <!-- 是否付款：<select> --> -->
<%-- <%-- <c:forEach var="equipment" items="${equipments}" varStatus="status"> --%> --%>
<%-- <%-- <c:if test="${status.index>0}"> --%> --%>
<%-- <%-- <option value="${equipment.ID}">${equipment.DeviceName } : ${equipment.Model }</option> --%> --%>
<%-- <%-- </c:if> --%> --%>
<%-- <%-- </c:forEach> --%> --%>
<!-- <!-- </select><br> --> -->


<!-- 付款条件：<select> -->
<%-- <c:forEach var="PayTerm" items="${PayTerms}" varStatus="status"> --%>
<%-- <c:if test="${status.index>0}"> --%>
<%-- <option value="${PayTerm.ID}">${PayTerm.Condition }</option> --%>
<%-- </c:if> --%>
<%-- </c:forEach> --%>
<!-- </select><br> -->


<!-- 是否办理免税信用证：<select> -->
<%-- <c:forEach var="Duty" items="${DutyFree}" varStatus="status"> --%>
<%-- <c:if test="${status.index>0}"> --%>
<%-- <option value="${Duty.ID}">${Duty.Status }</option> --%>
<%-- </c:if> --%>
<%-- </c:forEach> --%>
<!-- </select><br> -->


<!-- 销售代表：<select> -->
<%-- <c:forEach var="SaleRep" items="${SalesRep}" varStatus="status"> --%>
<%-- <c:if test="${status.index>0}"> --%>
<%-- <option value="${SaleRep.ID}">${SaleRep.Name }</option> --%>
<%-- </c:if> --%>
<%-- </c:forEach> --%>
<!-- </select><br> -->


</body>
</html>