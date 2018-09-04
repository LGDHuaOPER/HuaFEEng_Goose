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
<title>例行拜访</title>
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
<link rel="stylesheet" type="text/css" href="css/visit.css">
<style>
 	

</style>
</head>
<body>
	<!-- 	头部开始 -->
	<%@include file="top.jsp"%>
	<!-- 	头部结束 -->
	<div class="contain">
		<div class="content">
		<!-- 	=======================导航栏   开始 ================================== -->
			<%@include file="nav.jsp"%>
			

			
				<div class="choose m30">
					<input type="button" value="添加" class="bToggle" onclick="AddContract()" style="margin: 0px 0 -30px 0">
				</div>
				
				<div class="tc m30b">
					售后拜访记录
				</div>
 
			<table border="1" cellspacing="0" cellspadding="0" id="table1">
				<tr>
					<td>序号</td>
					<td>添加日期</td>
					<td>客户单位名称</td>
					<td>机台型号和SN号</td>
					<td>仪表型号</td>
					<td>最终用户姓名</td>
					<td>拜访日期</td>
					<td>拜访工程师</td>
					<td style="display:none;">删除数据</td>
				</tr>
				
				<!-- <tr>
					<td><a class="contract-edit">1</a></td>
					<td>11111</td>
					<td>客户11111名称</td>
					<td>11111</td>
					<td>11111</td>
					<td>11111</td>
					<td>11111</td>
					<td>11111</td>
					<td style="display:none;">删除数据</td>
				</tr> -->
				
				 <c:forEach var="orderInfo" items="${visit}" varStatus="status">
					<c:if test="${status.index>0}">
						<tr class="tbody_tr">
							<td class="contract-edit" value="${orderInfo['ID']}" style="color:blue;cursor:pointer">${status.index+(currentPage-1)*10}</td>
							<td >${orderInfo['AddDate']}</td>       <!--1  -->
							<td title="${orderInfo['CustomerUnit']}">${orderInfo['CustomerUnit']}</td>    <!--2  -->
							<td title="${orderInfo['ModelAndSN']}">${orderInfo['ModelAndSN']}</td>   <!--3  -->
							<td title="${orderInfo['Instrument']}">${orderInfo['Instrument']}</td>	 <!--4  -->
							<td>${orderInfo['EndUser']}</td>	 <!--5  -->
							<td>${orderInfo['VisitDate']}</td>	 <!--6  -->
							<td>${orderInfo['ToEngineer']}</td>	 <!--7  -->
							<td style="display:none;">${orderInfo['EndTel']}</td>	 <!--8  -->
							<td style="display:none;">${orderInfo['CheckContent']}</td>	 <!--8  -->
							<td style="display:none;">${orderInfo['Exist']}</td>	 <!--8  -->
							<td style="display:none;">${orderInfo['Project']}</td>	 <!--8  -->
							<td style="display:none;">${orderInfo['CheckSituation']}</td>	 <!--8  -->
							<td style="display:none;">${orderInfo['Evolve']}</td>	 <!--8  -->
							<td style="display:none;">${orderInfo['EvolveAnother']}</td>	 <!--8  -->
							<td style="display:none;"><i class="fa fa-trash-o del"></i></td>   <!--8  -->
						</tr>
					</c:if>
				</c:forEach>
			</table>	
		
	 	 <c:choose>
				<c:when test="${queryType == 'common'}">
					<c:set var="queryUrl"
					value="RoutineVisit?type1=${classify1 }&searchContent1=${parameter1}&selected=${str}&currentPage=">
					</c:set>
				</c:when>
				<c:otherwise>
					<c:set var="queryUrl"
					value="GetHardwareRoute?type1=${classify1 }&searchContent1=${parameter1}&type2=${classify2}&searchContent2=${parameter2}&selected=${str}&currentPage=">
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
	<div class="MailBar_cover_color" style="display: none;"></div>
	
	
	<!--   售后拜访模板all -->
	<div class="hidePdf" style="display: none;">
		<div id="view" class="news" style="font-family: initial; position: absolute; z-index: 11; top: 0; left: 0; background: #fff; font-size: 12px">
			<!-- 页眉-->
			<div class="yemei" style="margin-top: 20px;"></div>
			<!-- 售后拜访页面 -->

			<div id="StockList" class="s14 tl">
				<!-- 发货清单 -->
				<div class="tc m30 m30b f18" style="padding-left: 10%;">
					<b>售后拜访报告(硬件)</b>
				</div>
				<div class="m30b StockList">
					<table cellpadding="0" cellspacing="0" border="1" style="width: 90%; margin-bottom: 20px;">
						<tr>
							<td style="width: 50%">
								<p>
									客户单位名称:<span class="CustomerUnit" contenteditable="true"></span>
								</p>
							</td>
							<td style="width: 50%">
								<p>
									机台型号和SN号:<span class="ModelAndSN" contenteditable="true"></span>
								</p>
							</td>
						</tr>
						
						<tr>
							<td>
								<p>
									最终用户姓名:<span class="EndUser" contenteditable="true"></span>
								</p>
								<p>
									最终用户联系电话:<span class="EndTel" contenteditable="true"></span>
								</p>
							</td>
							<td>
								<p>
									仪表型号:<span class="Instrument" contenteditable="true"></span>
								</p>
							</td>
						</tr>
						
						<tr>
							<td>
								<p>
									拜访工程师:<span class="ToEngineer" contenteditable="true"></span>
								</p>
							</td>
							<td>
								<p>
									拜访日期:<span class="VisitDate" contenteditable="true"></span>
								</p>
							</td>
						</tr>
						
					</table>
					

				</div>

				<div class="m30b">
					<table cellpadding="0" cellspacing="0" border="1" style="width: 90%; margin-bottom: 20px;" class="pdf_seven_tab">
						<tr>
							<td colspan="4">
								<b>检查内容:</b>
								<p class="CheckContent" contenteditable="true"></p>
							</td>
						</tr>
						<tr>
							<td  style="width: 16%;">项目</td>
							<td  style="width: 45%;">检查情况</td>
							<td  style="width: 34%;">进展</td>
							<td  style="width: 5%;">删除</td>
						</tr>
						
						<tr class="pdf_one_tr">
							<td class="Project" contenteditable="true">1 </td>
							<td><p class="CheckSituation" contenteditable="true"></p></td>
							<td>
								<input type="radio"  class="Evolve" value="1" name="r_sel1" />现场完成维修<br>
								<input type="radio"  class="EvolveAnother"  value="2" name="r_sel1" />现场无法完成维修，需总部讨论，请参考结论
							</td>
							<td><input type="button" class="add_del4" style="width:90%;height:90%;" value="删除"></td>
						</tr>
						<tr class="pdf_one_tr">
							<td class="Project" contenteditable="true">2</td>
							<td><p class="CheckSituation" contenteditable="true"></p></td>
							<td>
								<input type="radio"  class="Evolve" value="1" name="r_sel2" />现场完成维修<br>
								<input type="radio"  class="EvolveAnother"  value="2" name="r_sel2" />现场无法完成维修，需总部讨论，请参考结论
							</td>
							<td><input type="button" class="add_del4" style="width:90%;height:90%;" value="删除"></td>
						</tr>
						<tr class="pdf_one_tr">
							<td class="Project"  contenteditable="true">3</td>
							<td><p class="CheckSituation" contenteditable="true"></p></td>
							<td>
								<input type="radio"  class="Evolve" value="1" name="r_sel3"/>现场完成维修<br>
								<input type="radio"  class="EvolveAnother"  value="2" name="r_sel3" />现场无法完成维修，需总部讨论，请参考结论
							</td>
							<td><input type="button" class="add_del4" style="width:90%;height:90%;" value="删除"></td>
						</tr>		
					</table>
					<div class="tl pl7">
						<input type="button" value="添加" class="bToggle" id="send_ADD" style="width: 60px; height: 30px; font-size: 19px;">
					</div>
					
				</div>
			<div class="ConclusionTab">
					<table cellpadding="0" cellspacing="0" border="1" style="width: 90%; margin-bottom: 20px;">
						<tr>
							<td >
								结论：
							</td>
						</tr>
						<tr>
							<td >
								<p  contenteditable="true" class="Conclusion"  style="min-height:100px;">
								</p>
							</td>
						</tr>
						</table>
				</div>
			</div>

			<!-- 页脚 -->
			<div class="yejiao cf" style="margin-top: 20px; height: 80px;"></div>
		</div>
		<input type="button" value="关闭" class="bToggle " id="contract_close" style="position: absolute; z-index: 11; top: 100px; left: 70%; width: 92px; height: 30px; font-size: 19px;">
		<input type="button" value="保存" class="bToggle" id="submit_n7" style="position: absolute; z-index: 11; top: 150px; left: 70%; width: 92px; height: 30px; font-size: 19px;">
		<input type="button" value="导出PDF" class="bToggle" id="exportPDF7" style="position: absolute; z-index: 11; top: 250px; left: 70%; width: 92px; height: 30px; font-size: 19px; display: none;">
	</div>
	
	<!--   售后拜访模板添加all -->
	<div class="hidePdf1" style="display: none;">
		<div id="view" class="news" style="font-family: initial; position: absolute; z-index: 11; top: 0; left: 0; background: #fff; font-size: 12px">
			<!-- 页眉-->
			<div class="yemei" style="margin-top: 20px;"></div>
			<!-- 售后拜访页面 -->

			<div id="StockList" class="s14 tl">
				<!-- 发货清单 -->
				<div class="tc m30 m30b f18" style="padding-left: 10%;">
					<b>售后拜访报告(硬件)</b>
				</div>
				<div class="m30b StockList">
					<table cellpadding="0" cellspacing="0" border="1" style="width: 90%; margin-bottom: 20px;">
						<tr>
							<td style="width: 50%">
								<p>
									客户单位名称:<span class="CustomerUnit" contenteditable="true"></span>
								</p>
							</td>
							<td style="width: 50%">
								<p>
									机台型号和SN号:<span class="ModelAndSN" contenteditable="true"></span>
								</p>
							</td>
						</tr>
						
						<tr>
							<td>
								<p>
									最终用户姓名:<span class="EndUser" contenteditable="true"></span>
								</p>
								<p>
									最终用户联系电话:<span class="EndTel" contenteditable="true"></span>
								</p>
							</td>
							<td>
								<p>
									仪表型号:<span class="Instrument" contenteditable="true"></span>
								</p>
							</td>
						</tr>
						
						<tr>
							<td>
								<p>
									拜访工程师:<span class="ToEngineer" contenteditable="true"></span>
								</p>
							</td>
							<td>
								<p>
									拜访日期:<span class="VisitDate" contenteditable="true"></span>
								</p>
							</td>
						</tr>
						
					</table>
					

				</div>

				<div class="m30b">
					<table cellpadding="0" cellspacing="0" border="1" style="width: 90%; margin-bottom: 20px;" class="pdf_seven_tab">
						<tr>
							<td colspan="4">
								<b>检查内容:</b>
								<p class="CheckContent" contenteditable="true"></p>
							</td>
						</tr>
						<tr>
							<td  style="width: 16%;">项目</td>
							<td  style="width: 45%;">检查情况</td>
							<td  style="width: 34%;">进展</td>
							<td  style="width: 5%;">删除</td>
						</tr>
						
						<tr class="pdf_one_tr">
							<td class="Project"  contenteditable="true">1</td>
							<td><p class="CheckSituation" contenteditable="true"></p></td>
							<td>
								<input type="radio"  class="Evolve" value="1" name="r_sel1" />现场完成维修<br>
								<input type="radio"  class="EvolveAnother"  value="2" name="r_sel1" />现场无法完成维修，需总部讨论，请参考结论
							</td>
							<td><input type="button" class="add_del4" style="width:90%;height:90%;" value="删除"></td>
						</tr>
						<tr class="pdf_one_tr">
							<td class="Project"  contenteditable="true">2</td>
							<td><p class="CheckSituation" contenteditable="true"></p></td>
							<td>
								<input type="radio"  class="Evolve" value="1" name="r_sel2" />现场完成维修<br>
								<input type="radio"  class="EvolveAnother"  value="2" name="r_sel2" />现场无法完成维修，需总部讨论，请参考结论
							</td>
							<td><input type="button" class="add_del4" style="width:90%;height:90%;" value="删除"></td>
						</tr>
						<tr class="pdf_one_tr">
							<td class="Project"  contenteditable="true">3</td>
							<td><p class="CheckSituation" contenteditable="true"></p></td>
							<td>
								<input type="radio"  class="Evolve" value="1" name="r_sel3"/>现场完成维修<br>
								<input type="radio"  class="EvolveAnother"  value="2" name="r_sel3" />现场无法完成维修，需总部讨论，请参考结论
							</td>
							<td><input type="button" class="add_del4" style="width:90%;height:90%;" value="删除"></td>
						</tr>		
					</table>
					<div class="tl pl7">
						<input type="button" value="添加" class="bToggle" id="send_ADD1" style="width: 60px; height: 30px; font-size: 19px;">
					</div>
				</div>
				<div class="ConclusionTab">
					<table cellpadding="0" cellspacing="0" border="1" style="width: 90%; margin-bottom: 20px;">
						<tr>
							<td >
								结论：
							</td>
						</tr>
						<tr>
							<td>
								<p  contenteditable="true" class="Conclusion"  style="min-height:100px;">
								</p>
							</td>
						</tr>
					</table>
				</div>

			</div>

			<!-- 页脚 -->
			<div class="yejiao cf" style="margin-top: 20px; height: 80px;"></div>
		</div>
		<input type="button" value="关闭" class="bToggle " id="contract_close1" style="position: absolute; z-index: 11; top: 100px; left: 70%; width: 92px; height: 30px; font-size: 19px;">
		<input type="button" value="保存" class="bToggle" id="submit_n1" style="position: absolute; z-index: 11; top: 150px; left: 70%; width: 92px; height: 30px; font-size: 19px;">
		<input type="button" value="导出PDF" class="bToggle" id="exportPDF1" style="position: absolute; z-index: 11; top: 250px; left: 70%; width: 92px; height: 30px; font-size: 19px; display: none;">
	</div>		

</body>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/msgbox.js"></script>
<script src="js/visit.js"></script>
<script>

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
    $('.hidePdf1').show();
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
$('#contract_close').click(function () {
  $('.MailBar_cover_color').hide();
  $('.hidePdf').hide();
});
$('#contract_close1').click(function () {
	  $.MsgBox.Confirm('提示','数据未保存，是否离开页面？',function(){
		  $('.MailBar_cover_color').hide();
		  $('.hidePdf1').hide();
	  })
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