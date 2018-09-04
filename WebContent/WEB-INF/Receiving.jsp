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
<title>验收报告</title>
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
<link rel="stylesheet" type="text/css" href="css/receiving.css">
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
				action="GetReceivingRoute">
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
					
					<td style="display:none;">TestDate</td>
					<td style="display:none;">ConfirmDate</td>
					<td style="display:none;">GuaranteeDate</td>
					<td style="display:none;">CONTRACT NO</td>
					<td style="display:none;">PONO</td>
					<td style="display:none;">ENGINEER</td>
					<td style="display:none;">MODEL</td>
					<td style="display:none;">WARRANTY</td>
					<td style="display:none;">SENDER</td>
					<td style="display:none;">RECEPTOR</td>
				</tr>
				 <c:forEach var="orderInfo" items="${receiving}" varStatus="status">
					<c:if test="${status.index>0}">
						<tr class="tbody_tr">
							<td value="${orderInfo['ID']}">${status.index+(currentPage-1)*10}</td>
							<td> <i class="fa fa-edit contract-edit" value="${orderInfo['ID']}"></i></td>
							<td title="${orderInfo['Customer']}">${orderInfo['Customer']}</td>
							<td title="${orderInfo['ContractTitle']}">${orderInfo['ContractTitle']}</td>
							<td title="${orderInfo['ContractNO']}">${orderInfo['ContractNO']}</td>
							<td><i class="fa fa-eye contract-show"></i></td>
							<td style="display:none;"><i class="fa fa-trash-o del"></i></td>
							<td style="display:none;">${orderInfo['TestDate']}</td>    <!--7  -->
							<td style="display:none;">${orderInfo['ConfirmDate']}</td>    <!--8  -->
							<td style="display:none;">${orderInfo['GuaranteeDate']}</td>    <!--9  -->
							<td style="display:none;">${orderInfo['ContractNO']}</td><!--10  -->
							<td style="display:none;">${orderInfo['PONO']}</td><!--11  -->
							<td style="display:none;">${orderInfo['Engineer']}</td><!--12 -->
							<td style="display:none;">${orderInfo['Model']}</td><!--13 -->
							<td style="display:none;">${orderInfo['Warranty']}</td><!--14 -->
							<td style="display:none;">${orderInfo['Sender']}</td><!--15 -->
							<td style="display:none;">${orderInfo['Receptor']}</td><!--16 -->
						</tr>
						
					</c:if>
				</c:forEach>
			</table>
		
	 	 <c:choose>
				<c:when test="${queryType == 'common'}">
					<c:set var="queryUrl"
					value="Receiving?type1=${classify1 }&searchContent1=${parameter1}&selected=${str}&currentPage=">
					</c:set>
				</c:when>
				<c:otherwise>
					<c:set var="queryUrl"
					value="GetReceivingRoute?type1=${classify1 }&searchContent1=${parameter1}&type2=${classify2}&searchContent2=${parameter2}&selected=${queryType}&currentPage=">
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
	
		<!-- -------------------------  添加验收报告信息 ----------------------------------->
	<div class="contract_add">
		<div class="contract_title">添加验收报告证明</div>
		<div class="contractAdd_close">关闭</div>
		<div class="basic_info">
			<div class="table_title">验收报告证明</div>
			<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
				<tbody>	
					<tr>
					    <td>TestDate</td>
						<td><input type="date" name="TestDate" value="" id="TestDate"></td>	
						<td>CONTRACT NO.</td>
						<td><input type="text" name="ContractNO" value=""  id="contract_no"></td>
					</tr>
					<tr>
						<td>CONFIRM DATE</td>
						<td><input type="date" name="ConfirmDate" value="" id="ConfirmDate"></td>
						<td>GUARANTEE DATE</td>
						<td><input type="date" name="GuaranteeDate" value="" id="GuaranteeDate"></td>
					</tr>
					<tr>
						
						<td>PONO.</td>
						<td><input type="text" name="PONO" value="" ></td>
						<td>ENGINEER</td>
						<td>
							<input type="text" name="Engineer" value="" style="position:absolute;width:162px;" id="Engineer">
							<span>
								<select onchange="document.getElementById('Engineer').value=this.options[this.selectedIndex].text" class="EngineerSelect">
									<option value="No"  selected="selected">--请选择--</option>
									<option>张海洋</option>
									<option>佘超群</option>
									<option>刘伟</option>
									<option>肖宇</option>
									<option>刘渊</option>
									<option>黄渊源</option>
									<option>宋代鳌</option>
									<option>高振鹏</option>
								</select>
							</span>
						</td>
					</tr>
					
					<tr>
						

						<td>MODEL</td>
						<td>
							<input type="text" name="Model" value="" style="position:absolute;width:162px;" id="Model">
							<span>
								<select onchange="document.getElementById('Model').value=this.options[this.selectedIndex].text">
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
					
					<tr>
						<td>SENDER</td>
						<td>
							<input type="text" name="Sender" value="" style="position:absolute;width:162px;" id="Sender">
							<span>
								<select onchange="document.getElementById('Sender').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value=""> 河南仕佳光子科技股份有限公司</option>
								</select>
							</span>
						</td>
						<td>RECEPTOR</td>
						<td>
							<input type="text" name="Receptor" value="" style="position:absolute;width:162px;" id="Receptor">
							<span>
								<select onchange="document.getElementById('Receptor').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="">香港伊欧陆贸易有限公司</option>
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
	
	
	
	
	<!-- -------------------------  修改验收报告信息 ----------------------------------->
	<div class="contract_update">
		<div class="contract_title">修改验收报告证明</div>
		<div class="contractUpdate_close">关闭</div>
		<div class="basic_info">
			<div class="table_title">验收报告证明</div>
			<table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
				<tbody>	
					<tr>
					    <td>TestDate</td>
						<td><input type="date" name="TestDate" value="" id="TestDate"></td>	
						<td>CONTRACT NO.</td>
						<td><input type="text" name="ContractNO" value=""  id="contract_no"></td>
					</tr>
					<tr>
						<td>CONFIRM DATE</td>
						<td><input type="date" name="ConfirmDate" value="" id="ConfirmDate"></td>
						<td>GUARANTEE DATE</td>
						<td><input type="date" name="GuaranteeDate" value="" id="GuaranteeDate"></td>
					</tr>
					<tr>
						
						<td>PONO.</td>
						<td><input type="text" name="PONO" value="" ></td>
						<td>ENGINEER</td>
						<td>
							<input type="text" name="Engineer" value="" style="position:absolute;width:162px;" id="Engineer">
							<span>
								<select onchange="document.getElementById('Engineer').value=this.options[this.selectedIndex].text" class="EngineerSelect">
									<option value="No"  selected="selected">--请选择--</option>
									<option>张海洋</option>
									<option>佘超群</option>
									<option>刘伟</option>
									<option>肖宇</option>
									<option>刘渊</option>
									<option>黄渊源</option>
									<option>宋代鳌</option>
									<option>高振鹏</option>
								</select>
							</span>
						</td>
					</tr>
					
					<tr>
						<td>WARRANTY</td>
						<td>
							<input type="text" name="Warranty" value="" style="position:absolute;width:162px;" id="Warranty">
							<span>
								<select onchange="document.getElementById('Warranty').value=this.options[this.selectedIndex].text" class="WarrantySelect">
									<option value="No" selected="selected">--请选择--</option>
									<option value="12">12</option>
									<option value="24">24</option>
									<option value="36">36</option>
								</select>
							</span>	
						</td>
						<td>MODEL</td>
						<td>
							<input type="text" name="Model" value="" style="position:absolute;width:162px;" id="Model">
							<span>
								<select onchange="document.getElementById('Model').value=this.options[this.selectedIndex].text">
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
					
					<tr>
						<td>SENDER</td>
						<td>
							<input type="text" name="Sender" value="" style="position:absolute;width:162px;" id="Sender">
							<span>
								<select onchange="document.getElementById('Sender').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value=""> 河南仕佳光子科技股份有限公司</option>
								</select>
							</span>
						</td>
						<td>RECEPTOR</td>
						<td>
							<input type="text" name="Receptor" value="" style="position:absolute;width:162px;" id="Receptor">
							<span>
								<select onchange="document.getElementById('Receptor').value=this.options[this.selectedIndex].text">
									<option value="No" selected="selected">--请选择--</option>
									<option value="">香港伊欧陆贸易有限公司</option>
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

    <!--验收报告证明文档-->
    
  <div id="table_Report">

    <h1>验收报告</h1>

    <div class="content_text m30b">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;乙方已于<p style="display: inline-block;" class="TestDate">
        <span contenteditable="true" class="TestYear">2016</span>年<span contenteditable="true" class="TestMonth">8</span> 月 <span contenteditable="true" class="TestDay">22</span>
    </p> 日，根据合同（订单）
    <span contenteditable="true" class="ContractNO">EHKN-2016012501</span>
    	<span class="PONOText" style="text-decoration: none;">(PO No.:
        	<span contenteditable="true" class="PONO">31312414</span>)
    	</span>
          的约定，安排工程师
    <span contenteditable="true" class="Engineer">刘伟、佘超群</span>为甲方进行<span contenteditable="true" class="Model"> Summit 11000B-M</span> 的安装调试，并经甲乙双方于
        <p style="display: inline-block;" class="ConfirmDate">
            <span contenteditable="true" class="ConfirmYear">2016</span> 年<span contenteditable="true" class="ConfirmMonth">11</span>月 <span contenteditable="true" class="ConfirmDay">28</span>
        </p>
        日共同确认，该系统运行正常，符合验收标准。
    </div>
    <div class="content_text m30b">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;甲乙双方共同确认<span contenteditable="true" class="Model">Summit 11000B-M</span>的安装调试已经验收合格。质保期为
        <p style="display: inline-block;" class="GuaranteeDate">
            <span contenteditable="true" class="GuaranteeYear">2016</span> 年<span contenteditable="true" class="GuaranteeMonth">11</span>月 <span contenteditable="true" class="GuaranteeDay">28</span>
        </p>
        日验收合格之日起<span contenteditable="true" class="Warranty">36</span>个月，耗材除外。
    </div>

    <div class="main_null"></div>

    <div class="lf" style="padding-left: 30px;display: inline-block;width: 55%">
        <p>甲方:<span></span></p><br>
        <p contenteditable="true" class="Sender">河南仕佳光子科技股份有限公司	 </p><br><br>
        <p>验收人:<span contenteditable="true" style="text-decoration: underline"></span></p><br>
        <p>日期:<span contenteditable="true">&nbsp;&nbsp; 年&nbsp;&nbsp; 月&nbsp;&nbsp; 日</span></p>
    </div>

    <div class="">
        <p>乙方:<span></span></p><br>
        <p contenteditable="true" class="Receptor">香港伊欧陆贸易有限公司 </p><br><br>
        <p>授权代表：<span contenteditable="true" style="text-decoration: underline"></span></p><br>
        <p>日期:<span contenteditable="true">&nbsp;&nbsp; 年&nbsp;&nbsp; 月&nbsp;&nbsp; 日</span></p>
    </div>

    <div class="main_null cf"></div>

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
<script src="js/receiving.js"></script>
<script type="text/javascript" src="js/html2canvas.js"></script>  
<script type="text/javascript" src="js/jsPdf.debug.js"></script>
<script>

/*********************添加验收报告信息************************/
/* 添加验收报告信息 */
$(document).on("click","#add_submit",function(){
		var TestDate=$('.contract_add input[name="TestDate"]').val();
	    var ConfirmDate=$('.contract_add input[name="ConfirmDate"]').val();
	    var GuaranteeDate=$('.contract_add input[name="GuaranteeDate"]').val();
	    var ContractNO=$('.contract_add input[name="ContractNO"]').val();
	    var PONO=$('.contract_add input[name="PONO"]').val() == "" ? "NA":$('.contract_add input[name="PONO"]').val();
	    var Engineer=$('.contract_add input[name="Engineer"]').val();
	    var Model=$('.contract_add input[name="Model"]').val();
	    var Warranty=$('.contract_add input[name="Warranty"]').val();
	    var Sender=$('.contract_add input[name="Sender"]').val();
	    var Receptor=$('.contract_add input[name="Receptor"]').val();
	    
	    console.log(TestDate);
	    console.log(ConfirmDate);
	    console.log(GuaranteeDate);
	    console.log(ContractNO);
	    console.log(PONO);
	    console.log(Engineer);
	    console.log(Model);
	    console.log(Warranty);
	    console.log(Sender);
	    console.log(Receptor);
	    
	    
 	   $.ajax({
	        type : 'get',
	        url : 'ReceivingAdd',
	        data : {
	        	TestDate : TestDate,
	        	ConfirmDate : ConfirmDate,
	        	GuaranteeDate : GuaranteeDate,
	            ContractNO : ContractNO,
	            PONO : PONO,
	            Engineer : Engineer,
	            Model : Model,
	            Warranty : Warranty,
	            Sender : Sender,
	            Receptor :Receptor,
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
    
    
/* 修改验收报告信息 */
$(document).on("click",".contract-edit",function(){	
	var tr=$(this).parent().parent();
	
    $('.contract_update input[name="TestDate"]').val(tr.find('td').eq(7).text());
	$('.contract_update input[name="ConfirmDate"]').val(tr.find('td').eq(8).text());
	$('.contract_update input[name="GuaranteeDate"]').val(tr.find('td').eq(9).text());
	$('.contract_update input[name="ContractNO"]').val(tr.find('td').eq(10).text());
	$('.contract_update input[name="PONO"]').val(tr.find('td').eq(11).text());
	$('.contract_update input[name="Engineer"]').val(tr.find('td').eq(12).text());
	$('.contract_update input[name="Model"]').val(tr.find('td').eq(13).text());
	$('.contract_update input[name="Warranty"]').val(tr.find('td').eq(14).text());
	$('.contract_update input[name="Sender"]').val(tr.find('td').eq(15).text());
	$('.contract_update input[name="Receptor"]').val(tr.find('td').eq(16).text());
	
	
	var  ID = tr.find('td').eq(0).attr("value");
	
	$(".contract_update .contract_title").attr("value",ID);  //在修改页面保存当前行的ID信息
	
	console.log(ID)
   $('.MailBar_cover_color').show();
   $('.contract_update').show();
	    	    
 });
   	
	/*  提交修改后的信息  */
	$('#update_submit').click(function () {
		var TestDate = $('.contract_update input[name="TestDate"]').val();
		var ConfirmDate = $('.contract_update input[name="ConfirmDate"]').val();
		var GuaranteeDate = $('.contract_update input[name="GuaranteeDate"]').val();
		var ContractNO = $('.contract_update input[name="ContractNO"]').val();
		var PONO = $('.contract_update input[name="PONO"]').val();
		var Engineer = $('.contract_update input[name="Engineer"]').val();
		var Model = $('.contract_update input[name="Model"]').val();
		var Warranty = $('.contract_update input[name="Warranty"]').val();
		var Sender = $('.contract_update input[name="Sender"]').val();
		var Receptor = $('.contract_update input[name="Receptor"]').val();
		var  ID = $(".contract_update .contract_title").attr("value");
		  $.ajax({
		      type : 'get',
		      url : 'ModifyReceiving',
		      data : {
		    	    ID : ID,
		        	TestDate : TestDate,
		        	ConfirmDate : ConfirmDate,
		        	GuaranteeDate : GuaranteeDate,
		            ContractNO : ContractNO,
		            PONO : PONO,
		            Engineer : Engineer,
		            Model : Model,
		            Warranty : Warranty,
		            Sender : Sender,
		            Receptor :Receptor,
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
	     var TestDate = thisList.find("td").eq(7).text();
	     var ConfirmDate = thisList.find("td").eq(8).text();
	     var GuaranteeDate = thisList.find("td").eq(9).text();
	     var ContractNO = thisList.find("td").eq(10).text();
	     var PONO = thisList.find("td").eq(11).text();
	     var Engineer = thisList.find("td").eq(12).text();
	     var Model = thisList.find("td").eq(13).text();
	     var Warranty = thisList.find("td").eq(14).text();
	     var Sender = thisList.find("td").eq(15).text();
	     var Receptor = thisList.find("td").eq(16).text();
	     
	    // $(".news .TestDate").text("").text(TestDate); 
	    // $(".news .ConfirmDate").text("").text(ConfirmDate); 
	    // $(".news .GuaranteeDate").text("").text(GuaranteeDate); 
	     $(".news .ContractNO").text("").text(ContractNO);
	     $(".news .PONO").text("").text(PONO); 
	     $(".news .Engineer").text("").text(Engineer); 
	     $(".news .Model").text("").text(Model); 
	     $(".news .Warranty").text("").text(Warranty);
	     $(".news .Sender").text("").text(Sender); 
	     $(".news .Receptor").text("").text(Receptor); 
	     
	     	console.log(TestDate);
		 	console.log(ConfirmDate);
		    console.log(GuaranteeDate);
		    console.log(ContractNO);
		    console.log(PONO);
		    console.log(Engineer);
		    console.log(Model);
		    console.log(Warranty);
		    console.log(Sender);
		    console.log(Receptor);
		    
		    /* 日期分开获取 */
		    if(TestDate.split("-").length == 3){
		    	console.log("111111")
		    	 $(".TestDate").show();
				 $(".news .TestYear").text(TestDate.split("-")[0]);
				 $(".news .TestMonth").text(TestDate.split("-")[1]);
				  $(".news .TestDay").text(TestDate.split("-")[2]);
		    }
	     	
		    if(ConfirmDate.split("-").length == 3){
		    	console.log("22222")
		    	 $(".ConfirmDate").show();
				 $(".news .ConfirmYear").text(ConfirmDate.split("-")[0]);
				 $(".news .ConfirmMonth").text(ConfirmDate.split("-")[1]);
				 $(".news .ConfirmDay").text(ConfirmDate.split("-")[2]);
		    }
		    
		    if(GuaranteeDate.split("-").length == 3){
		    	console.log("33333")
		    	 $(".GuaranteeDate").show();
				 $(".news .GuaranteeYear").text(GuaranteeDate.split("-")[0]);
				 $(".news .GuaranteeMonth").text(GuaranteeDate.split("-")[1]);
				 $(".news .GuaranteeDay").text(GuaranteeDate.split("-")[2]);
		    }
	     //PONO是否存在
	       if(PONO =="NA"){
	       /*  $(".PONO").hide(); */
	     	$(".PONOText").hide();
	     } 
	     
    $('.hidePdf').show(); 
 })

 
$('#contract_close1').click(function () {
    $('.MailBar_cover_color').hide();
    $('.hidePdf').hide();
});

/* ************************模板页面提交*************************** */

/* 
 $(document).on("click","#submit_n",function(){
	var ID=  $(".hidePdf .yejiao").attr("value");
	
	
    var TestYear=$('.hidePdf .TestYear').text();
    var TestMonth=$('.hidePdf .TestMonth').text();
    var TestDay=$('.hidePdf .TestDay').text();
    var TestDate=TestYear+"-"+TestMonth+"-"+TestDay;
    
    var ConfirmYear=$('.hidePdf .ConfirmYear').text();
    var ConfirmMonth=$('.hidePdf .ConfirmMonth').text();
    var ConfirmDay=$('.hidePdf .ConfirmDay').text();
    var ConfirmDate=ConfirmYear+"-"+ConfirmMonth+"-"+ConfirmDay;
    
    var GuaranteeYear=$('.hidePdf .GuaranteeYear').text();
    var GuaranteeMonth=$('.hidePdf .GuaranteeMonth').text();
    var GuaranteeDay=$('.hidePdf .GuaranteeDay').text();
    var GuaranteeDate=GuaranteeYear+"-"+GuaranteeMonth+"-"+GuaranteeDay;
    
    var ContractNO=$('.hidePdf .ContractNO').text();
    var PONO=$('.hidePdf .PONO').text();
    var Engineer=$('.hidePdf .Engineer').text();
    var Model=$('.hidePdf .Model').text();
    var Warranty=$('.hidePdf .Warranty').text();
    var Sender=$('.hidePdf .Sender').text();
    var Receptor=$('.hidePdf .Receptor').text();
    	$.ajax({
            type : 'get',
            url : "ModifyReceiving",
            data : {
            	ID:ID,
            	TestDate : TestDate,
	        	ConfirmDate : ConfirmDate,
	        	GuaranteeDate : GuaranteeDate,
	            ContractNO : ContractNO,
	            PONO : PONO,
	            Engineer : Engineer,
	            Model : Model,
	            Warranty : Warranty,
	            Sender : Sender,
	            Receptor :Receptor,
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
	        pdf.save('Receiving.pdf');
	    }
	})
}

/****************** 跳页 **********************/

function FistPage(arg) {
	/* window.location.href = arg + "1"; */
	if(arg.split('?')[0]=='GetReceivingRoute'){
		 $('#search').val('search');
		 $("#top_text_from").attr("action", arg + "1");
		  $('#top_text_from').submit();
	}else{ 
		
		window.location.href = arg + "1";
	 } 
}
function UpPage(arg) {
	if(arg.split('?')[0]=='GetReceivingRoute'){
		 $('#search').val('search');
		 $("#top_text_from").attr("action", arg);
		  $('#top_text_from').submit();
	}else{ 
		
		window.location.href = arg;
	 } 
}
function NextPage(arg) {
	
	if(arg.split('?')[0]=='GetReceivingRoute'){
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
	if(arg.split('?')[0]=='GetReceivingRoute'){
		 $('#search').val('search');
		 $("#top_text_from").attr("action", arg + jumpNumber);
		  $('#top_text_from').submit();
	}else{ 
		
		window.location.href = arg + jumpNumber;
	 } 
}
function LastPage(arg) {
	var jumpNumber = parseInt($('#allPage').html());
	if(arg.split('?')[0]=='GetReceivingRoute'){
		 $('#search').val('search');
		 $("#top_text_from").attr("action", arg + jumpNumber);
		  $('#top_text_from').submit();
	}else{ 
		
		window.location.href = arg + jumpNumber;
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
	
})

/*********模板 删除*******/
  $(document).on("click",".del",function () {
	  var thisList = $(this).parent().parent();
      var ID = thisList.find("td").eq(0).attr("value");
      $(".yejiao").attr("value",ID);
	  console.log(ID);
	  $.ajax({
          type : 'get',
          url : "ReceivingDelete",
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
