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
<title>原产地证明</title>
<style>
.tbody_tr td {
    padding: 0 3px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
</style>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" />
<link rel="stylesheet" type="text/css" href="css/origin.css">
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
				action="GetOriginRoute">
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
							value="${fn:split('客户名称,合同名称,合同号',',')}"></c:set>
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
		 	 <table border="1" cellspacing="0" cellspadding="0" id="table1">
				<tr>
					<td>序号</td>
					<td>修改</td>
					<td>客户名称 </td>
					<td>合同名称</td>
					<td>合同号</td>
					<td style="width: 145px;">模板预览（可导出）</td>
					<td style="display:none;">删除数据</td>
					
					<td style="display:none;">Date</td>
					<td style="display:none;">CONTRACT NO</td>
					<td style="display:none;">PURCHASE ORDER NO</td>
					<td style="display:none;">LCNO</td>
					<td style="display:none;">QUALITY</td>
					<td style="display:none;">MODEL NUMBER</td>
					<td style="display:none;">GOODS</td>
					<td style="display:none;">COUNTRY OF ORIGIN</td>
					<td style="display:none;">ManufactoryName</td>
					<td style="display:none;">ManufactoryAddress</td>
					<td style="display:none;">TEL</td>
				</tr>
				
				 <c:forEach var="orderInfo" items="${origin}" varStatus="status">
					<c:if test="${status.index>0}">
						<tr class="tbody_tr">
							<td value="${orderInfo['ID']}">${status.index+(currentPage-1)*10}</td>
							<td> <i class="fa fa-edit contract-edit" value="${orderInfo['ID']}"></i></td>
							<td title="${orderInfo['Customer']}">${orderInfo['Customer']}</td>
							<td title="${orderInfo['ContractTitle']}">${orderInfo['ContractTitle']}</td>
							<td title="${orderInfo['ContractNO']}">${orderInfo['ContractNO']}</td>
							<td><i class="fa fa-eye contract-show"></i></td>
							<td style="display:none;"><i class="fa fa-trash-o del"></i></td>
							<td style="display:none;">${orderInfo['Date']}</td>    <!--7  -->
							<td style="display:none;">${orderInfo['ContractNO']}</td><!--8  -->
							<td style="display:none;">${orderInfo['PurchaseOrderNO']}</td><!--9  -->
							<td style="display:none;">${orderInfo['LCNO']}</td><!--10 -->
							<td style="display:none;">${orderInfo['Quality']}</td><!--11  -->
							<td style="display:none;">${orderInfo['ModelNumber']}</td><!--12 -->
							<td style="display:none;">${orderInfo['Goods']}</td><!--13 -->
							<td style="display:none;">${orderInfo['OriginCountry']}</td><!--14 -->
							<td style="display:none;">${orderInfo['ManufactoryName']}</td><!--15 -->
							<td style="display:none;">${orderInfo['ManufactoryAddress']}</td><!--16 -->
							<td style="display:none;">${orderInfo['Tel']}</td><!--17 -->
						</tr>
						
					</c:if>
				</c:forEach>
			</table>
		
	 	 <c:choose>
				<c:when test="${queryType == 'common'}">
					<c:set var="queryUrl"
					value="Origin?type1=${classify1 }&searchContent1=${parameter1}&selected=${str}&currentPage=">
					</c:set>
				</c:when>
				<c:otherwise>
					<c:set var="queryUrl"
					value="GetOriginRoute?type1=${classify1 }&searchContent1=${parameter1}&type2=${classify2}&searchContent2=${parameter2}&selected=${str}&currentPage=">
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
	
		<!-- -------------------------  添加原产地证明信息 ----------------------------------->
	<div class="contract_add">
		<div class="contract_title">添加原产地证明</div>
		<div class="contractAdd_close">关闭</div>
		<div class="basic_info">
			<div class="table_title">原产地证明</div>
			<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
				<tbody>	
					<tr>
					    <td>DATE</td>
						<td><input type="date" name="Date" value="" id="Date"></td>	
						<td>CONTRACT NO.</td>
						<td><input type="text" name="ContractNO" value=""  id="contract_no"></td>
					</tr>
					<tr>
						
						<td>L/C NO.</td>
						<td><input type="text" name="LCNO" value="" ></td>
						<td>GOODS</td>
						<td><input type="text" name="Goods" value=""></td>
					</tr>
					<tr>
						<td>PURCHASE ORDER NO.</td>
						<td><input type="text" name="PurchaseOrderNO" value="" ></td>
						<td>ADDRESS</td>
						<td>
							<input type="text" name="ManufactoryAddress" value="" style="position:absolute;width:162px;" id="ManufactoryAddress">
							<span>
								<select onchange="document.getElementById('ManufactoryAddress').value=this.options[this.selectedIndex].text" class="AddressSelect">
									<option value="No"  selected="selected">--请选择--</option>
									<option>Address:SϋSS STR.1 01561 THIENDORF OT SACKA GERMANY</option>
									<option>Address:9100 SW Gemini Drive, Beaverton, OR 97008,U.S.A</option>
								</select>
							</span>
						</td>
					</tr>
					
					<tr>
						<td>TEL</td>
						<td>
							<input type="text" name="Tel" value="" style="position:absolute;width:162px;" id="Tel">
							<span>
								<select onchange="document.getElementById('Tel').value=this.options[this.selectedIndex].text" class="TelSelect">
									<option value="No" selected="selected">--请选择--</option>
									<option value="GERMANY">Tel: +49 35240-73-203</option>
									<option value="USA" >Tel: 1-503-601 1000</option>
								</select>
							</span>	
						</td>
						<td>COUNTRY OF ORIGIN</td>
						<td>
							<input type="text" name="OriginCountry" value="" style="position:absolute;width:162px;" id="OriginCountry">
							<span>
								<select onchange="document.getElementById('OriginCountry').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="GERMANY">GERMANY</option>
									<option value="USA" >USA</option>
									<option value="HK" >HONG KONG</option>
								</select>
							</span>
							
						</td>
					</tr>
					
					<tr>
						<td>QUALITY</td>
						<td>
							<input type="text" name="Quality" value="" style="position:absolute;width:162px;" id="Quality">
							<span>
								<select onchange="document.getElementById('Quality').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="GERMANY"> 1 SET</option>
								</select>
							</span>
						</td>
						<td>MANUFACTORY NAME</td>
						<td>
							<input type="text" name="ManufactoryName" value="" style="position:absolute;width:162px;" id="ManufactoryName">
							<span>
								<select onchange="document.getElementById('ManufactoryName').value=this.options[this.selectedIndex].text" class="ManufactoryNameSelect">
									<option value="No" selected="selected">--请选择--</option>
									<option value="Cascade Microtech, Inc">Cascade Microtech, Inc</option>
								</select>
							</span>	
						</td>
															
					</tr>
					<tr>
						<td>MODEL NUMBER</td>
						<td>
							<input type="text" name="ModelNumber" value="" style="position:absolute;width:162px;" id="ModelNumber">
							<span>
								<select onchange="document.getElementById('ModelNumber').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="SUMMIT12000B-S"> SUMMIT12000B-S</option>
									<option value="EPS150LT">EPS150LT</option>
									<option value="SUMMIT12000B-M"> SUMMIT12000B-M</option>
									<option value="SUMMIT11000B-M"> SUMMIT11000B-M</option>
									<option value="T200-STA-M"> T200-STA-M</option>
									<option value="PA200DS-BR-011"> PA200DS-BR-011</option>
									<option value="PA200-BR-021"> PA200-BR-021</option>
									<option value="EPS150MMW"> EPS150MMW</option>
									<option value="CM300">CM300</option>
								</select>
							</span>
						</td>
					</tr>
					
					
					
				</tbody>
			</table>		
		</div>
		<div class="edit_btn">
			<input type="button" value="提交" class="bToggle" id="add_submit">
			<input type="button" value="取消" class="bToggle" id="add_cancel">
		</div>
	</div>
	
	
	
	<!-- -------------------------  修改原产地证明信息 ----------------------------------->
	<div class="contract_update">
		<div class="contract_title">修改原产地证明</div>
		<div class="contractUpdate_close">关闭</div>
		<div class="basic_info">
			<div class="table_title">原产地证明</div>
			<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
				<tbody>	
					<tr>
					    <td>DATE</td>
						<td><input type="date" name="Date" value="" id="Date"></td>	
						<td>CONTRACT NO.</td>
						<td><input type="text" name="ContractNO" value=""  id="contract_no"></td>
					</tr>
					<tr>
						
						<td>L/C NO.</td>
						<td><input type="text" name="LCNO" value="" ></td>
						<td>GOODS</td>
						<td><input type="text" name="Goods" value=""></td>
					</tr>
					<tr>
						<td>PURCHASE ORDER NO.</td>
						<td><input type="text" name="PurchaseOrderNO" value="" ></td>
						<td>ADDRESS</td>
						<td>
							<input type="text" name="ManufactoryAddress" value="" style="position:absolute;width:162px;" id="ManufactoryAddress">
							<span>
								<select onchange="document.getElementById('ManufactoryAddress').value=this.options[this.selectedIndex].text" class="AddressSelect">
									<option value="No"  selected="selected">--请选择--</option>
									<option>Address:SϋSS STR.1 01561 THIENDORF OT SACKA GERMANY</option>
									<option>Address:9100 SW Gemini Drive, Beaverton, OR 97008,U.S.A</option>
								</select>
							</span>
						</td>
					</tr>
					
					<tr>
						<td>TEL</td>
						<td>
							<input type="text" name="Tel" value="" style="position:absolute;width:162px;" id="Tel">
							<span>
								<select onchange="document.getElementById('Tel').value=this.options[this.selectedIndex].text" class="TelSelect">
									<option value="No" selected="selected">--请选择--</option>
									<option value="GERMANY">Tel: +49 35240-73-203</option>
									<option value="USA" >Tel: 1-503-601 1000</option>
								</select>
							</span>	
						</td>
						<td>COUNTRY OF ORIGIN</td>
						<td>
							<input type="text" name="OriginCountry" value="" style="position:absolute;width:162px;" id="OriginCountry">
							<span>
								<select onchange="document.getElementById('OriginCountry').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="GERMANY">GERMANY</option>
									<option value="USA" >USA</option>
									<option value="HK" >HONG KONG</option>
								</select>
							</span>
							
						</td>
					</tr>
					
					<tr>
						<td>QUALITY</td>
						<td>
							<input type="text" name="Quality" value="" style="position:absolute;width:162px;" id="Quality">
							<span>
								<select onchange="document.getElementById('Quality').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="GERMANY"> 1 SET</option>
								</select>
							</span>
						</td>
						<td>MANUFACTORY NAME</td>
						<td>
							<input type="text" name="ManufactoryName" value="" style="position:absolute;width:162px;" id="ManufactoryName">
							<span>
								<select onchange="document.getElementById('ManufactoryName').value=this.options[this.selectedIndex].text" class="ManufactoryNameSelect">
									<option value="No" selected="selected">--请选择--</option>
									<option value="Cascade Microtech, Inc">Cascade Microtech, Inc</option>
								</select>
							</span>	
						</td>
															
					</tr>
					<tr>
						<td>MODEL NUMBER</td>
						<td>
							<input type="text" name="ModelNumber" value="" style="position:absolute;width:162px;" id="ModelNumber">
							<span>
								<select onchange="document.getElementById('ModelNumber').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="SUMMIT12000B-S"> SUMMIT12000B-S</option>
									<option value="EPS150LT">EPS150LT</option>
									<option value="SUMMIT12000B-M"> SUMMIT12000B-M</option>
									<option value="SUMMIT11000B-M"> SUMMIT11000B-M</option>
									<option value="T200-STA-M"> T200-STA-M</option>
									<option value="PA200DS-BR-011"> PA200DS-BR-011</option>
									<option value="PA200-BR-021"> PA200-BR-021</option>
									<option value="EPS150MMW"> EPS150MMW</option>
									<option value="CM300">CM300</option>
								</select>
							</span>
						</td>
					</tr>
					
					
					
				</tbody>
			</table>		
		</div>
		<div class="edit_btn">
			<input type="button" value="提交" class="bToggle" id="update_submit">
			<input type="button" value="取消" class="bToggle" id="update_cancel">
		</div>
	</div>
	
	
	
	
	
	
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

    <!--原产地证明文档-->
    <div id="table_Origin">
    <p class="f29 b tc m30">Cascade Microtech</p>
    <p class="tc m30">SϋSS STR.1 01561 THIENDORF OT SACKA GERMANY</p>
    <p class="tc">Tel: +49 35240-73-203</p>
    <p class="f18 b tc m30">Certificate of Origin</p>

    <table cellpadding="0" cellspacing="0" border="1" style=" width:100%;margin-bottom: 60px;margin-top: 10px;">
        <tr>
            <td colspan="3" class="pl">
                <p class="b">MANUFACTORY NAME AND ADRESS:</p>
                <p class="ManufactoryName">Cascade Microtech, Inc</p>
                <p class="ManufactoryAddress">Address: SϋSS STR.1 01561 THIENDORF OT SACKA GERMANY</p>
                <p class="Tel">Tel: +49 35240-73-203</p>
            </td>
        </tr>
        <tr>
            <td colspan="3" class="pl">
                <p><span class="b">GOODS:</span><span contenteditable="true" class="Goods">PROBE STATION</span></p>
                <p><span class="b">MODEL NUMBER:</span><span contenteditable="true" class="ModelNumber">SUMMIT11000B-M</span></p>
                <p><span class="b">QUALITY:</span><span contenteditable="true" class="Quality">1 SET</span></p>
            </td>
        </tr>
        <tr>
            <td class="pl">
                <p class="b">L/C NO.:</p>
                <span contenteditable="true" class="LCNO">36100LC1700172</span>
            </td>
            <td class="pl">
                <p class="b">CONTRACT NO.:</p>
                <span contenteditable="true" class="ContractNO">SACS-TD-1610-0250/REV.1</span>
            </td>
            <td class="pl">
                <p class="b">PURCHASE ORDER NO.:</p>
                <span contenteditable="true" class="PurchaseOrderNO">E20161121AS001</span>
            </td>
        </tr>
        <tr>
            <td colspan="3" class="pl">
                <p class="b">COUNTRY OF ORIGIN:</p>
                <p contenteditable="true" class="OriginCountry">GERMANY</p>
            </td>
        </tr>
    </table>

    <div style="display: inline-block;" class="cf lf m30b">Signature:</div>
    <div contenteditable="true" class="lf" style="border-bottom: 1px solid #000;width:150px;"></div>
    <p class="cf">CASCADE MICROTECH</p>
    <p >Date: <span contenteditable="true" class="Date">2017/3/14</span></p>



</div>

    <!-- 页脚 -->
    <div class="yejiao cf" style="margin-top: 20px; height:80px;">
        <hr>
        <pre style="text-align: center;color: #000080;font-family: -webkit-body;">EOULU
Suzhou ● Shenzhen ● Beijing ● Shanghai ● HongKong
〡Phone: +86-512-62757360〡Web:www.eoulu.com〡Email:info@eoulu.com〡</pre>

    </div>
</div>
			<!-- <input type="button" value="提交" class="bToggle" id="submit_n" style="position:absolute;z-index:11;top: 200px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
			 -->
			<input type="button" value="导出PDF" class="bToggle" id="exportPDF1" style="position:absolute;z-index:11;top: 150px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
			<input type="button" value="关闭" class="bToggle " id="contract_close1" style="position: absolute;z-index: 11;top: 100px; left: 70%;width: 92px;height: 30px;font-size: 19px;">
	</div>
</body>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/msgbox.js"></script>
<script src="js/origin.js"></script>
<script type="text/javascript" src="js/html2canvas.js"></script>  
<script type="text/javascript" src="js/jsPdf.debug.js"></script>
<script>

/*********************添加原产地信息************************/
/* 添加原产地信息 */
$(document).on("click","#add_submit",function(){
		var Date=$('.contract_add input[name="Date"]').val();
	    var ContractNO=$('.contract_add input[name="ContractNO"]').val();
	    var PurchaseOrderNO=$('.contract_add input[name="PurchaseOrderNO"]').val();
	    var LCNO=$('.contract_add input[name="LCNO"]').val();
	    var Quality=$('.contract_add input[name="Quality"]').val();
	    var ModelNumber=$('.contract_add input[name="ModelNumber"]').val();
	    var Goods=$('.contract_add input[name="Goods"]').val();
	    var OriginCountry=$('.contract_add input[name="OriginCountry"]').val();
	    var ManufactoryName=$('.contract_add input[name="ManufactoryName"]').val();
	    var ManufactoryAddress=$('.contract_add input[name="ManufactoryAddress"]').val();
	    var Tel=$('.contract_add input[name="Tel"]').val();
	    
	    console.log(Date);
	    console.log(ContractNO);
	    console.log(PurchaseOrderNO);
	    console.log(LCNO);
	    console.log(Quality);
	    console.log(ModelNumber);
	    console.log(Goods);
	    console.log(OriginCountry);
	    console.log(ManufactoryName);
	    console.log(ManufactoryAddress);
	    console.log(Tel);
	    
 	   $.ajax({
	        type : 'get',
	        url : 'OriginAdd',
	        data : {
	        	Date : Date,
	            ContractNO : ContractNO,
	            PurchaseOrderNO : PurchaseOrderNO,
	            LCNO : LCNO,
	            Quality : Quality,
	            ModelNumber : ModelNumber,
	            Goods : Goods,
	            OriginCountry :OriginCountry,
	            ManufactoryName : ManufactoryName,
	            ManufactoryAddress : ManufactoryAddress,
	            Tel : Tel,
	        },
	        dataType : 'json',
	        success : function (data) {
	        	
	            $.MsgBox.Alert('提示','添加成功');
	            $('.MailBar_cover_color').hide();
	            $('.contract_add').hide();
	        },
	        error : function () {
	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    }); 
	    	    
    });
    
    
/* 修改原产地信息 */
$(document).on("click",".contract-edit",function(){	
	var tr=$(this).parent().parent();
	
    $('.contract_update input[name="Date"]').val(tr.find('td').eq(7).text());
	$('.contract_update input[name="ContractNO"]').val(tr.find('td').eq(8).text());
	$('.contract_update input[name="PurchaseOrderNO"]').val(tr.find('td').eq(9).text());
	$('.contract_update input[name="LCNO"]').val(tr.find('td').eq(10).text());
	$('.contract_update input[name="Quality"]').val(tr.find('td').eq(11).text());
	$('.contract_update input[name="ModelNumber"]').val(tr.find('td').eq(12).text());
    $('.contract_update input[name="Goods"]').val(tr.find('td').eq(13).text());
	$('.contract_update input[name="OriginCountry"]').val(tr.find('td').eq(14).text());
	$('.contract_update input[name="ManufactoryName"]').val(tr.find('td').eq(15).text());
	$('.contract_update input[name="ManufactoryAddress"]').val(tr.find('td').eq(16).text());
    $('.contract_update input[name="Tel"]').val(tr.find('td').eq(17).text());
	
	
	var  ID = tr.find('td').eq(0).attr("value");
	
	$(".contract_update .contract_title").attr("value",ID);  //在修改页面保存当前行的ID信息
	
	console.log(ID)
   $('.MailBar_cover_color').show();
   $('.contract_update').show();
	    	    
 });
   	
	/*  提交修改后的信息  */
	$('#update_submit').click(function () {
		var Date = $('.contract_update input[name="Date"]').val();
		var ContractNO = $('.contract_update input[name="ContractNO"]').val();
		var PurchaseOrderNO = $('.contract_update input[name="PurchaseOrderNO"]').val();
		var LCNO = $('.contract_update input[name="LCNO"]').val();
		var Quality = $('.contract_update input[name="Quality"]').val();
		var ModelNumber = $('.contract_update input[name="ModelNumber"]').val();
		var Goods = $('.contract_update input[name="Goods"]').val();
		var OriginCountry = $('.contract_update input[name="OriginCountry"]').val();
		var ManufactoryName = $('.contract_update input[name="ManufactoryName"]').val();
		var ManufactoryAddress = $('.contract_update input[name="ManufactoryAddress"]').val();
		var Tel = $('.contract_update input[name="Tel"]').val();
		var  ID = $(".contract_update .contract_title").attr("value");
		
		  $.ajax({
		      type : 'get',
		      url : 'ModifyOrigin',
		      data : {
		    	  ID : ID,
		    	  Date : Date,
		          ContractNO : ContractNO,
		          PurchaseOrderNO : PurchaseOrderNO,
		          LCNO : LCNO,
		          Quality : Quality,
		          ModelNumber : ModelNumber,
		          Goods : Goods,
		          OriginCountry :OriginCountry,
		          ManufactoryName : ManufactoryName,
		          ManufactoryAddress : ManufactoryAddress,
		          Tel : Tel,
		      },
		      dataType : 'json',
		      success : function (data) {
		      	
		          $.MsgBox.Alert('提示','修改成功');
		          $('.MailBar_cover_color').hide();
		          $('.contract_add').hide();
		      },
		      error : function () {
		          $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
		      }
		  }); 
	})   
    
    
 /*  模板显示*/   
 $('.contract-show').click(function () {
		 
	    $('.MailBar_cover_color').show();
	    /* 获取隐藏信息 */
	     
         var thisList = $(this).parent().parent();
         var ID = thisList.find("td").eq(0).attr("value");
         $(".yejiao").attr("value",ID);
	     var Date = thisList.find("td").eq(7).text();
	     var ContractNO = thisList.find("td").eq(8).text();
	     var PurchaseOrderNO = thisList.find("td").eq(9).text();
	     var LCNO = thisList.find("td").eq(10).text();
	     var Quality = thisList.find("td").eq(11).text();
	     var ModelNumber = thisList.find("td").eq(12).text();
	     var Goods = thisList.find("td").eq(13).text();
	     var OriginCountry = thisList.find("td").eq(14).text();
	     var ManufactoryName = thisList.find("td").eq(15).text();
	     var ManufactoryAddress = thisList.find("td").eq(16).text();
	     var Tel = thisList.find("td").eq(17).text();     
	     
	     $(".news .Date").text("").text(Date); 
	     $(".news .ContractNO").text("").text(ContractNO); 
	     $(".news .PurchaseOrderNO").text("").text(PurchaseOrderNO); 
	     $(".news .LCNO").text("").text(LCNO);
	     $(".news .Quality").text("").text(Quality); 
	     $(".news .ModelNumber").text("").text(ModelNumber); 
	     $(".news .Goods").text("").text(Goods); 
	     $(".news .OriginCountry").text("").text(OriginCountry);
	     $(".news .ManufactoryName").text("").text(ManufactoryName); 
	     $(".news .ManufactoryAddress").text("").text(ManufactoryAddress); 
	     $(".news .Tel").text("").text(Tel);
	     
	     	console.log(Date);
		    console.log(ContractNO);
		    console.log(PurchaseOrderNO);
		    console.log(LCNO);
		    console.log(Quality);
		    console.log(ModelNumber);
		    console.log(Goods);
		    console.log(OriginCountry);
		    console.log(ManufactoryName);
		    console.log(ManufactoryAddress);
		    console.log(Tel);
	     	
	     //PONO是否存在
	      //if(PONO =="NA"){
	       /*  $(".PONO").hide(); */
	     //	$(".PONO_text").hide();
	     //}
	     
    $('.hidePdf').show(); 
 })
 
$('#contract_close1').click(function () {
    $('.MailBar_cover_color').hide();
    $('.hidePdf').hide();
});


/* ************************模板页面提交*************************** */


 /* $(document).on("click","#submit_n",function(){
	var ID=  $(".hidePdf .yejiao").attr("value");
    var Date=$('.hidePdf .Date').text();
    var ContractNO=$('.hidePdf .ContractNO').text();
    var PurchaseOrderNO=$('.hidePdf .PurchaseOrderNO').text();
    var LCNO=$('.hidePdf .LCNO').text();
    var Quality=$('.hidePdf .Quality').text();
    var ModelNumber=$('.hidePdf .ModelNumber').text();
    var Goods=$('.hidePdf .Goods').text();
    var OriginCountry=$('.hidePdf .OriginCountry').text();
    var ManufactoryName=$('.hidePdf .ManufactoryName').text();
    var ManufactoryAddress=$('.hidePdf .ManufactoryAddress').text();
    var Tel=$('.hidePdf .Tel').text();
    	$.ajax({
            type : 'get',
            url : "ModifyOrigin",
            data : {
            	ID:ID,
            	Date : Date,
	            ContractNO : ContractNO,
	            PurchaseOrderNO : PurchaseOrderNO,
	            LCNO : LCNO,
	            Quality : Quality,
	            ModelNumber : ModelNumber,
	            Goods : Goods,
	            OriginCountry :OriginCountry,
	            ManufactoryName : ManufactoryName,
	            ManufactoryAddress : ManufactoryAddress,
	            Tel : Tel,
            },
            dataType : 'json',
            success : function (data) {       	        	             	
            	$.MsgBox.Alert('提示','提交成功');
	            $('.MailBar_cover_color').hide();
	            $('.hidePdf').hide();
            },
            error : function () {
                $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
            }
        });
}); */


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
	$("#Date").val(datew);
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
$('#update_cancel').click(function () {
    $('.MailBar_cover_color').hide();
    $('.contract_update').hide();
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


document.getElementById("exportPDF1").onclick=function(){
	var target = document.getElementById("view");
	var width = $('#view').width() ; 
	var height =  $('#view').height() ;
	var canvas = document.createElement("canvas");  
	    canvas.width = width * 2;  
	    canvas.height = height * 2;  
	    canvas.style.width = width + "px";  
	    canvas.style.height = height + "px";  
	var context = canvas.getContext("2d");//然后将画布缩放，将图像放大两倍画到画布上  
	    context.scale(2,2); 
  html2canvas(target, {
	  canvas:canvas,
	    onrendered:function(canvas) {
	        var pageData = canvas.toDataURL('image/png', 1.0);
	        var pdf = new jsPDF('', 'pt', 'a4');
	        pdf.addImage(pageData, 'PNG', 0, 0, 600, 821.89);
	        pdf.save('Origin.pdf');
	    }
	})
}

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


/*********模板 删除*******/
  $(document).on("click",".del",function () {
	  var thisList = $(this).parent().parent();
      var ID = thisList.find("td").eq(0).attr("value");
      $(".yejiao").attr("value",ID);
	  console.log(ID);
	  $.ajax({
          type : 'get',
          url : "OriginDelete",
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