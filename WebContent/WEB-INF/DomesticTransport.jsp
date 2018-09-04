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
<title>国内运输指令</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" />
<link rel="stylesheet" type="text/css" href="css/DomesticTransport.css">
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
				action="TransportDirective">
				<input type="text" id="search" name="isSearch" value="search"
					style="display: none;">
				<div class="select-content">
					 <label> <c:choose>
							<c:when test="${queryType=='mixSelect'}">
								<label><input type="radio" id="singleSelect"
									name="queryType" class="singleSelect" value="singleSelect"
									onclick="Check(this.value)">单一查询 </label>&nbsp;&nbsp;&nbsp;<label>
									<input type="radio" id="mixSelect" name="queryType"
									value="mixSelect" checked="checked" onclick="Check(this.value)">组合查询
								</label>&nbsp;&nbsp;&nbsp;<br>
							</c:when>
							<c:otherwise>
								<label><input type="radio" id="singleSelect"
									name="queryType" class="singleSelect" value="singleSelect"
									checked="checked" onclick="Check(this.value)">单一查询 </label>&nbsp;&nbsp;&nbsp;<label>
									<input type="radio" id="mixSelect" name="queryType"
									value="mixSelect" onclick="Check(this.value)">组合查询
								</label>&nbsp;&nbsp;&nbsp;<br>
							</c:otherwise>
						</c:choose> <c:set var="dropdown"
							value="${fn:split('合同号,运输商',',')}"></c:set>
						<div class="select1">
							<select name="type1" id="type1">
								<c:forEach items="${dropdown }" var="dropdownList1"
									varStatus="status">
									<c:choose>
										<c:when test="${dropdownList1==type1}">
											<option selected="selected">${type1}</option>
										</c:when>
										<c:otherwise>
											<option>${dropdownList1}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>


							</select>
							 <input type="text" id="searchContent1" name="searchContent1" value="${searchContent1}">

						</div>
						<div class="select2" style="display: none">
							<select name="type2" id="type2">
								<c:forEach items="${dropdown }" var="dropdownList2"
									varStatus="status">
									<c:choose>
										<c:when test="${dropdownList2==type2}">
											<option selected="selected">${type2}</option>
										</c:when>
										<c:otherwise>
											<option>${dropdownList2}</option>
										</c:otherwise>	
									</c:choose>
								</c:forEach>
							</select>
							<c:if test="${queryType =='mixSelect'}">
								<input type="text" id="searchContent2" name="searchContent2" value="${searchContent2}">
							</c:if>
							<c:if test="${queryType!='mixSelect'}">
								<input type="text" id="searchContent2" name="searchContent2" value="${searchContent2}">
							</c:if>

						</div>
						 <div class="select-button">
							<input type="button" value="搜索" class="bToggle" onclick="INSearch()"> 
							<input type="button" value="取消" class="bToggle" onclick="Cancel()">
						</div>
				</div>
		 </form> 
		 <div class="choose">
					<div class="changeBox" style="position:relative;top:17px;left:0;z-index:3;background:url(image/bg2.png);width:358px;height:50px;display:inline-block;line-height:50px;font-size:14px;">
						<span class="Domestic" style="position:absolute;top:0;left:0;width:183px;height:42px;display:block;text-align:center;cursor:pointer;">国内运输指令</span>
						<span class="ExitOrEn" style="position:absolute;top:0;left:175px;background:url(image/bg3.png);width:183px;height:42px;cursor:pointer;display:block;text-align:center;">进出口运输指令</span>
					</div>
					<div style=" transform: skew(20deg);display:inline-block;">
						<input type="button" value="添加" class="bToggle" onclick="AddContract()" >
					</div>
				</div>
		 	 <table border="1" cellspacing="0" cellspadding="0" id="table1">
				<tr  style="background: #bfbfbf;">
					<td style="width: 4.4%;">序号</td>
					<td style="display:none;">修改</td>
					<td style="width:40%;">合同号</td>
					<td style="width:13%;">提货日期</td>
					<td style="width:13.6%;">运输商</td>
					<td style="width:19%;">总计</td>
					<td style="width:10%;">邮件功能</td>
				</tr>
				
				 <c:forEach var="orderInfo" items="${data}" varStatus="status">
					<c:if test="${status.index>0}">
						<tr>
							<td value="${orderInfo['ID']}" class="contract-edit" title="点击可修改">${status.index+(currentPage-1)*10}</td>
							<td style="display:none;"> <i class="fa fa-edit " value="${orderInfo['ID']}"></i></td>
							<td>${orderInfo['ContractNO']}</td>
							<td>${orderInfo['PackingDate']}</td>
							<td>${orderInfo['Transporters']}</td>
							<td>共计 ${orderInfo['Total1']}个纸箱，${orderInfo['Total2']}个木箱，${orderInfo['Total3']}个收货地址</td>               <!-- 4 -->
							<td class="SendEmail" style="cursor:pointer;" flag="Unsent">${orderInfo['Status']}</td>
							<td style="display:none;">${orderInfo['Total1']}</td>     <!-- 6 -->
							<td style="display:none;">${orderInfo['Total2']}</td>  <!-- 7 -->
							<td style="display:none;">${orderInfo['Total3']}</td>  <!-- 8 -->
						</tr>
						
					</c:if>	
				</c:forEach>
			</table>
		
	 	 <c:choose>
				<c:when test="${queryType == 'common'}">
					<c:set var="queryUrl"
					value="TransportDirective?queryType=common&type1=${classify1 }&searchContent1=${parameter1}&selected=${str}&currentPage=">
					</c:set>
				</c:when>
				<c:when test="${queryType == 'singleSelect'}">
					<c:set var="queryUrl"
					value="TransportDirective?queryType=singleSelect&type1=${classify1 }&searchContent1=${parameter1}&selected=${str}&currentPage=">
					</c:set>
				</c:when>
				<c:otherwise>
					<c:set var="queryUrl"
					value="TransportDirective?queryType=mixSelect&type1=${classify1 }&searchContent1=${parameter1}&type2=${classify2}&searchContent2=${parameter2}&selected=${str}&currentPage=">
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
	<div class="SendEmailBox" >
		<div class="SendEmailBox_title">邮件发送</div>
		<div class="SendEmailBox_close">关闭</div>
			<div class="SendEmailCon">
				<table cellpadding="0" cellspacing="0" border="1"  style="width:90%;margin-left:5%;margin-top:40px;" class="SendEmailTab">
					<tr>
						<td>发件人</td>
						<td class="Sender"></td>
					</tr>
					<tr>
						<td>邮箱密码</td>
						<td style="position: relative;">
							<input type="password" name="password" style="display:none">
							<input type="password" name="password" class="EmailPwd contentNull" autocomplete="new-password">
							<span class="eyes_psw"></span>
						</td>
					</tr>
					<tr>
						<td>收件人</td>
						<td  contenteditable="true"  id="search_text"  class="contentNull"></td>
						<div id="auto_div"></div>  
					</tr>
					<tr>
						<td>抄送</td>
						 <td contenteditable="true" class="CC" id="CC">liuyanan@eoulu.com;jiangyaping@eoulu.com</td> 
						<!-- <td  contenteditable="true" class="CC contentNull" id="CC">wangning@eoulu.com；wangning@eoulu.com；wangning@eoulu.com；wangning@eoulu.com</td> -->
						<div id="CC_div"></div>
					</tr>
					<tr>
						<td>主题</td>
						<td  contenteditable="true" class="theme" ></td>
					</tr>
					<tr>
						<td>正文</td>
						<td >
							<div class="EmailCont"  contenteditable="true">
								<div   contenteditable="true">您好！</div></br>
								<div   contenteditable="true" class="EmailCon">我司有货物需要运输，请按照表格的时间和要求到我司提货，收到请回复，谢谢！</div></br>
								<div   contenteditable="true">注意事项：</div></br>
								<div   contenteditable="true">烦请尽快安排处理，并给出航班信息，谢谢！</div></br>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div class="other" style="width:100%;margin-bottom:30px;">
				<table cellpadding="0" cellspacing="0" border="1"  style="width:90%;margin-left:5%;" >
					<tr>
						<td  style="width: 15%;height:45px;line-height:45px;text-align:center;">附件</td>
						<td  class="annexName">
							<i class="fa fa-file-text-o annexNameIcon" ></i>
							<span  class="annexHref" >国内运输指令.pdf</span>
							<!-- <input type="file" name="EmailAnnex" id="file" value="上传附件" /> -->
						</td>
					</tr>
				</table>
			</div>
			
			<div class="edit_btn">
				<input type="button" value="发送" class="bToggle" id="SendEmail_submit">
				<input type="button" value="取消" class="bToggle" id="SendEmail_cancel">
			</div>
		<!-- 	<form action="TransportDirectiveEmail" method="post" style="display:none;" class="Email_form" id="Email_form" enctype="multipart/form-data">
				<input type="text" name="Password"  class="post_Password"/>
				<input type="text" name="Consignee" class="post_Consignee" />
				<input type="text" name="ID"  class="post_ID"/>
			</form>  -->
	</div>
	
	<!-- 添加运输保险指令信息 -->
	<div class="contract_add" style="display: none;">
		<div class="contract_title">添加国内运输指令</div>
		<div class="contractAdd_close">关闭</div>
		<div class="table_title"> <input type="button" name="addItem" value="添加" class="bToggle addItem"> </div>
		<div class="basic_info">
			<table  cellspacing="0" cellpadding="0" class="contract_header" style="width:100%;border:#00aeef 2px solid;height:30px;line-height:30px;margin-top:20px;">
				<tbody>	
					 <tr>
					    <th style="width:100%;">EOULU国内运输指令</th>
					</tr>
				</tbody>
			</table>
			<table border="2" cellspacing="0" cellpadding="0" class="contract_basic">
			      
				<tbody>	
				  <tr>
					    <th style="width:4.84%;">序号</th>
						<th style="width:9.95%;">合同</th>	
						<th style="width:9.95%;">尺寸（CM）</th>
						<th style="width:6.84%;">重量（KG）</th>
						<th style="width:6.84%;">数量（纸箱/木箱）</th>
						<th style="width:5.37%;">是否需要打木托</th>
						<th style="width:7.57%;">是否需要尾班车或者液压车派送</th>
						<th style="width:11.08%;">提货时间</th>
						<th style="width:11.08%;">提货信息</th>	
						<th style="width:11.08%;">收货信息</th>	
						<th style="width:7.7%;">运输商</th>	
						<th style="width:7.7%;">总计</th>	
					</tr>
				</tbody>
				
			</table>
			<table  cellspacing="0" cellpadding="0" class="contract_fotter" style="width:100%;border:#00aeef 2px solid;border-top:none;height:33px;line-height:33px;">
				<tbody>	
					 <tr>
					    <td  style="width:100%;text-align:right;">注：货物不能叠加放置，请合理安排符合货物尺寸的车辆</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="edit_btn">
			<input type="button" value="提交" class="bToggle" id="add_submit">
			<input type="button" value="取消" class="bToggle" id="add_cancel">
		</div>
	</div>
	
	<!-- 修改运输保险指令 -->
	<div class="contract_update" style="display: none;">
		<div class="contract_title">修改国内运输指令</div>
		<div class="contractUpdate_close">关闭</div>
		<div class="table_title"> <input type="button" name="addItem" value="添加" class="bToggle addItem"> </div>
		<div class="basic_info">
			<table  cellspacing="0" cellpadding="0" class="contract_header" style="width:100%;border:#00aeef 2px solid;height:30px;line-height:30px;margin-top:20px;">
				<tbody>	
					 <tr>
					    <th style="width:100%;">EOULU国内运输指令</th>
					</tr>
				</tbody>
			</table>
			<table border="2" cellspacing="0" cellpadding="0" class="contract_basic">
				<tbody>	
					 <tr>
					   <th style="width:4.84%;">序号</th>
						<th style="width:9.95%;">合同</th>	
						<th style="width:9.95%;">尺寸（CM）</th>
						<th style="width:6.84%;">重量（KG）</th>
						<th style="width:6.84%;">数量（纸箱/木箱）</th>
						<th style="width:5.37%;">是否需要打木托</th>
						<th style="width:7.57%;">是否需要尾班车或者液压车派送</th>
						<th style="width:11.08%;">提货时间</th>
						<th style="width:11.08%;">提货信息</th>	
						<th style="width:11.08%;">收货信息</th>	
						<th style="width:7.7%;">运输商</th>	
						<th style="width:7.7%;">总计</th>		
					</tr>
				</tbody>
			</table>
			<table  cellspacing="0" cellpadding="0" class="contract_fotter" style="width:100%;border:#00aeef 2px solid;border-top:none;height:30px;line-height:30px;">
				<tbody>	
					 <tr>
					    <td  style="width:100%;text-align:right;">注：货物不能叠加放置，请合理安排符合货物尺寸的车辆</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="edit_btn">
			<input type="button" value="提交" class="bToggle" id="update_submit">
			<input type="button" value="取消" class="bToggle" id="update_cancel">
		</div>
	</div>
	
	
	<div class="hidePdf" style="display:none;width:1300px;margin:0 auto;">
		<div class="hideData" style="display:none;"></div>
		<table  cellspacing="0" cellpadding="0" class="hidePdf_header" style="width:100%;border:#00aeef 2px solid;border-bottom:none;height:30px;line-height:30px;">
				<tbody>	
					 <tr>
					    <th style="width:100%;">EOULU国内运输指令</th>
					</tr>
				</tbody>
			</table>
			<table  cellspacing="0" cellpadding="0" class="hidePdf_basic" style="width:100%;">
				<tbody>	
					 <tr>
					    <th style="width:5%;height: 34px;border:solid 2px #00aeef;border-right:none;text-align: center;">序号</th>
						<th style="width:10%;height: 34px;border:solid 2px #00aeef;border-right:none;text-align: center;">合同</th>	
						<th style="width:10%;height: 34px;border:solid 2px #00aeef;border-right:none;text-align: center;">尺寸（CM）</th>
						<th style="width:5%;height: 34px;border:solid 2px #00aeef;border-right:none;text-align: center;">重量（KG）</th>
						<th style="width:5%;height: 34px;border:solid 2px #00aeef;border-right:none;text-align: center;">数量（纸箱/木箱）</th>
						<th style="width:5%;height: 34px;border:solid 2px #00aeef;border-right:none;text-align: center;">是否需要打木托</th>
						<th style="width:5%;height: 34px;border:solid 2px #00aeef;border-right:none;text-align: center;">是否需要卸货平台</th>
						<th style="width:5%;height: 34px;border:solid 2px #00aeef;border-right:none;text-align: center;">提货时间</th>
						<th style="width:20%;height: 34px;border:solid 2px #00aeef;border-right:none;text-align: center;">提货信息</th>	
						<th style="width:20%;height: 34px;border:solid 2px #00aeef;border-right:none;text-align: center;">收货信息</th>	
						
						<th style="width:10%;height: 34px;border:solid 2px #00aeef;text-align: center;">总计</th>	
					</tr>
				</tbody>
			</table>
			<table  cellspacing="0" cellpadding="0" class="hidePdf_fotter" style="width:100%;border:#00aeef 2px solid;border-top:none;height:30px;line-height:30px;">
				<tbody>	
					 <tr>
					    <td  style="width:100%;text-align:right;">注：货物不能叠加放置，请合理安排符合货物尺寸的车辆</td>
					</tr>
				</tbody>
			</table>
	</div>
</body>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/msgbox.js"></script>
<script src="js/msgbox_unload.js"></script>
<script src="plugins/cookie/jquery.cookie.js"></script>
<script src="js/insurance.js"></script> 
<script src="js/html2canvas.js"></script>
<script src="js/FileSaver.js"></script>
<script src="js/jquery.wordexport.js"></script>
<script>
var currentUserName = $("span#userName").text();
var pro = ["北京","天津","上海","重庆","河北","山西","辽宁","吉林","黑龙江","江苏","浙江","安徽","福建","江西","山东","河南","湖北","湖南","广东","海南","四川","贵州","云南","陕西","甘肃","青海","内蒙古","广西","西藏","宁夏","新疆维吾尔自治区","香港","澳门","台湾"];

var city = ["东城区","西城区","崇文区","宣武区","朝阳区","海淀区","丰台区","石景山区","房山区","通州区","顺义区","昌平区","大兴区","怀柔区","平谷区","门头沟区","密云县","延庆县","和平区","河东区","河西区","南开区","河北区","红桥区","东丽区","西青区","北辰区","津南区","武清区","宝坻区","滨海新区","静海县","宁河县","蓟县","黄浦区","卢湾区","徐汇区","长宁区","静安区","普陀区","闸北区","虹口区","杨浦区","闵行区","宝山区","嘉定区","浦东新区","金山区","松江区","青浦区","奉贤区","崇明县","渝中区","大渡口区","江北区","南岸区","北碚区","渝北区","巴南区","长寿区","双桥区","沙坪坝区","万盛区","万州区","涪陵区","黔江区","永川区","合川区","江津区","九龙坡区","南川区","綦江县","潼南县","荣昌县","璧山县","大足县","铜梁县","梁平县","开县","忠县","城口县","垫江县","武隆县","丰都县","奉节县","云阳县","巫溪县","巫山县","石柱土家族自治县","秀山土家族苗族自治县","酉阳土家族苗族自治县","彭水苗族土家族自治县","石家庄","唐山","秦皇岛","邯郸","邢台","保定","张家口","承德","沧州","廊坊","衡水","太原","大同","阳泉","长治","晋城","朔州","晋中","运城","忻州","临汾","吕梁","沈阳","大连","鞍山","抚顺","本溪","丹东","锦州","营口","阜新","辽阳","盘锦","铁岭","朝阳","葫芦岛","长春","吉林","四平","辽源","通化","白山","松原","白城","延边朝鲜族自治州","哈尔滨","齐齐哈尔","鹤岗","双鸭山","鸡西","大庆","伊春","牡丹江","佳木斯","七台河","黑河","绥化","大兴安岭","南京","苏州","无锡","常州","镇江","南通","泰州","扬州","盐城","连云港","徐州","淮安","宿迁","杭州","宁波","温州","嘉兴","湖州","绍兴","金华","衢州","舟山","台州","丽水","合肥","芜湖","蚌埠","淮南","马鞍山","淮北","铜陵","安庆","黄山","滁州","阜阳","宿州","巢湖","六安","亳州","池州","宣城","福州","厦门市","莆田","三明","泉州","漳州","南平","龙岩","宁德","南昌","景德镇","萍乡","九江","新余","鹰潭","赣州","吉安","宜春","抚州","上饶","济南","青岛","淄博","枣庄","东营","烟台","潍坊","济宁","泰安","威海","日照","莱芜","临沂","德州","聊城","滨州","菏泽","郑州","开封","洛阳","平顶山","安阳","鹤壁","新乡","焦作","濮阳","许昌","漯河","三门峡","南阳","商丘","信阳","周口","驻马店","武汉","黄石","十堰","荆州","宜昌","襄樊","鄂州","荆门","孝感","黄冈","咸宁","随州","恩施","长沙","株洲","湘潭","衡阳","邵阳","岳阳","常德","张家界","益阳","郴州","永州","怀化","娄底","湘西","广州","深圳","珠海","汕头","韶关","佛山","江门","湛江","茂名","肇庆","惠州","梅州","汕尾","河源","阳江","清远","东莞","中山","潮州","揭阳","云浮","海口","三亚","成都","自贡","攀枝花","泸州","德阳","绵阳","广元","遂宁","内江","乐山","南充","眉山","宜宾","广安","达州","雅安","巴中","资阳","阿坝","甘孜","凉山","贵阳","六盘水","遵义","安顺","铜仁","毕节","黔西南","黔东南","黔南","昆明","曲靖","玉溪","保山","昭通","丽江","普洱","临沧","德宏","怒江","迪庆","大理","楚雄","红河","文山","西双版纳","西安","铜川","宝鸡","咸阳","渭南","延安","汉中","榆林","安康","商洛","兰州","嘉峪关","金昌","白银","天水","武威","酒泉","张掖","庆阳","平凉","定西","陇南","临夏","甘南","西宁","海东","海北","海南","黄南","果洛","玉树","海西","呼和浩特","包头","乌海","赤峰","通辽","鄂尔多斯","呼伦贝尔","巴彦淖尔","乌兰察布","锡林郭勒盟","兴安盟","阿拉善盟","南宁","柳州","桂林","梧州","北海","防城港","钦州","贵港","玉林","百色","贺州","河池","来宾","崇左","拉萨","那曲","昌都","林芝","山南","日喀则","阿里","银川","石嘴山","吴忠","固原","中卫","乌鲁木齐","克拉玛依","吐鲁番","哈密","和田","阿克苏","喀什","克孜勒苏","巴音郭楞","昌吉","博尔塔拉","伊犁","塔城","阿勒泰","香港岛","九龙东","九龙西","新界东","新界西","澳门半岛","离岛","台北","高雄","基隆","新竹","台中","嘉义","台南市"];
 
 function ProOrCity(aa){
	for (var i = 0 ; i < pro.length;i++) {
		if(pro[i].indexOf(aa)>-1){
			return pro[i];
		}
	}
	for (var j = 0 ; j < city.length;j++) {
		if(city[j].indexOf(aa)>-1){
			return city[j];
		}
	}
	
 }
	var sender = '<%=request.getSession().getAttribute("email")%>';
	$(".SendEmail").click(function(){
		var curEmailPass = $.cookie(currentUserName+"DomesticTransportMailPass");
		if(curEmailPass==undefined||curEmailPass==null){
			$(".EmailPwd.contentNull").val("");
		}else{
			$(".EmailPwd.contentNull").val(curEmailPass);
		}
		$('.hidePdf .addList').remove();
	    var currentTr = $(this).parent();
		var ID = currentTr.find("td").eq(0).attr("value");
		var PTime = currentTr.find("td").eq(3).text();
		$("#SendEmail_submit").attr("time",PTime);
		$.ajax({
		    url: 'TransportDirectiveModel',
		    type: 'Get',
		    data: {
		    	ID : ID,
		    },
		    dataType : 'json',
			success : function (data) {
				console.log(data)
				var listStr = "";
				if(data.length>1){
					for(var i = 1 ; i<data.length;i++){
						listStr += '<tr class="addList">'+
					    '<td value="'+data[i].AddrID+'" class="firstChild">'+i+'</td>'+
						'<td contenteditable="true"  class="addList_contract">'+data[i].ContractNO+'</td>	'+
						'<td contenteditable="true"  class="addList_size">'+data[i].Size+'</td>'+
						'<td contenteditable="true"  class="KG">'+data[i].Weight+'</td>'+
						'<td contenteditable="true"  class="BoxNum">'+data[i].Quantity+'</td>'+
						'<td contenteditable="true"  class="IsWood">'+data[i].WoodenPallet+'</td>'+
						'<td contenteditable="true"  class="IsPlatform">'+data[i].UnloadPlat+'</td>'+
						'<td  class="pickupDate"><span style="display: block;width: 90px;">'+currentTr.find("td").eq(3).text()+'</span></td>'+
						'<td class="pickupTd">'+
							'公司：<span contenteditable="true" class="pickup_App">'+data[i].PickCompany+'</span></br>'+
							'地址：<span contenteditable="true" class="pickup_Add">'+data[i].PickAddress+'</span>	</br>'+
							'联系人：<span contenteditable="true" class="pickup_Name">'+data[i].PickContact+'</span>	</br>'+
							'手机：<span contenteditable="true" class="pickup_Tel">'+data[i].PickTel+'</span>	</br>'+
						'</td>	'+
						'<td class="ReceivingTd">'+
							'公司：<span contenteditable="true" class="Receiving_App">'+data[i].ReceivingCompany+'</span>		</br>'+
							'地址：<span contenteditable="true" class="Receiving_Add">'+data[i].ReceivingAddress+'</span>	</br>'+
							'联系人：<span contenteditable="true" class="Receiving_Name">'+data[i].ReceivingContact+'</span>	</br>'+
							'手机：<span contenteditable="true" class="Receiving_Tel">'+data[i].ReceivingTel+'</span>	</br>'+
						'</td>	'+
						'<td>共计 <span contenteditable="true"  class="Carton">'+currentTr.find("td").eq(7).text()+'</span>个纸箱， <span contenteditable="true"  class="Wooden">'+currentTr.find("td").eq(8).text()+'</span>个木箱， <span contenteditable="true"  class="AddNum">'+currentTr.find("td").eq(9).text()+'</span>个收货地址</td>'+	
					'</tr>';
					}
					
					$(".SendEmailCon").attr("value1", data[1].PickAddress);
					$(".SendEmailCon").attr("value2", data[1].ReceivingAddress);
				}else{
					$(".SendEmailCon").attr("value1", "");
					$(".SendEmailCon").attr("value2", "");
				}
				
				// console.log(ProOrCity($(".SendEmailCon").attr("value1").slice(0,2)))
				// console.log($(".SendEmailCon").attr("value2").slice(0,2))
				var startAdd = ProOrCity($(".SendEmailCon").attr("value1").slice(0,2));
				var toAdd = ProOrCity($(".SendEmailCon").attr("value2").slice(0,2));
				$(".SendEmailBox_title").attr("value",ID);
				var themeText = "Eoulu:"+currentTr.find("td").eq(3).text()+"从"+startAdd+"到"+toAdd+"的提货指令";
				$(".theme").text(themeText);
				var EmailContText = "我司有一票货物从"+startAdd+"运输到"+toAdd+"，提货时间为"+currentTr.find("td").eq(3).text()+"。";
				$(".EmailCon").text(EmailContText);
				
				$(".hidePdf .hidePdf_basic").append(listStr);
				$(".hidePdf .hidePdf_basic").rowspan(11);
	        },
	        error : function () {
	        	 
	        }
		}); 

		$(".contentNull").text("");
		$(".CC").text("liuyanan@eoulu.com;jiangyaping@eoulu.com;");
		//$(".CC").text("wangning@eoulu.com；wangning@eoulu.com；wangning@eoulu.com；wangning@eoulu.com；");
		$('.MailBar_cover_color').show();
		$(".MailBar_cover_color").css("height",$(".content").height()+160);
		$(".SendEmailBox").show();
		$(".SendEmailBox .Sender").text(sender);
		
	})
	//获取所有邮箱
	var test_list = [];
  	   $.ajax({
	        type : 'get',
	        url : 'GetAllEmail',
	        dataType : 'json',
	        success : function (data) {
	        	//console.log(data);
	        	for(var i = 1 ; i < data.length;i++){
	        		test_list.push(data[i].Email);
	        	}
	        	return test_list;
	        },
	        error : function () {
	        }
	    });  
  	
	old_value = $("#search_text").text();  
	var currentText = $("#search_text").text()
    $("#search_text").keyup(function () {
    	console.log($("#search_text").css("height"))
    	$("#auto_div").css("top",parseFloat($("#search_text").css("height"))+163+"px")
    	if($(this).text() !=""){
    		AutoComplete("auto_div", "search_text", test_list);  
    	}
    }); 
 	 $("#CC").keyup(function () {  
 		$("#auto_div").css("top",parseFloat($("#CC").css("height"))+163+"px")
    	if($(this).text() !=""){
    		AutoComplete("CC_div", "CC", test_list);  
    	}
    }); 
 	
 	// 邮件发送
 	$(document).on("click",".SendEmailBox #SendEmail_submit",function(){
 		var ID = $(".SendEmailBox_title").attr("value");
 		 console.log($(".EmailPwd").val());
	  	 console.log(ID);
	  	 console.log($("#search_text").text());
	  	 var time = $("#SendEmail_submit").attr("time");
	  	
	  	var address1 = ProOrCity($(".SendEmailCon").attr("value1").slice(0,2));
		var address2 = ProOrCity($(".SendEmailCon").attr("value2").slice(0,2));
		
	     $.ajax({
		    url: 'TransportDirectiveEmail',
		    type: 'POST',
		    data:{
		    	Consignee: $("#search_text").text(),
		    	Password: $(".EmailPwd").val(),
		    	time: time,
		    	address1:address1,
		    	address2:address2,
		    	ID: ID,
		    },
		    beforeSend: function(XMLHttpRequest){
		        $("#SendEmail_submit").attr("disabled","disabled");
		        $("#SendEmail_submit").css({
		        	"background":"#dddddd",
		        	"color":"#808080",
		        	"border":"none",
		        	"box-shadow":"0 0 0 0 #f8fcfd",
		        	"cursor":"not-allowed"
		        });
		    },
		    complete: function(XMLHttpRequest, textStatus){
		        $("#SendEmail_submit").attr("disabled",false);
		        $("#SendEmail_submit").css({
		          "background":"#00aeef",
		          "color":"#fff",
		          "border":"solid 1px #00aeef",
		          "box-shadow":"1px 2px 5px 0 #00aeef",
		          "cursor":"pointer"
		        });
		    },
		    // dataType:"json",
		    dataType:"text",
			success : function (data) {
				// console.log(data);
				// console.log(data == true)
				if(data.indexOf("发送成功")>-1){
					var EmailPassword = $(".EmailPwd.contentNull").val().trim();  
					$.cookie(currentUserName+"DomesticTransportMailPass", EmailPassword, { expires: 360 }); 
				}
				$.MsgBox.Alert("提示",data);
	        	// if(data == true){
	        	// 	$.MsgBox.Alert("提示", "发送成功！"); 
	        	// }else{
	        	// 	$.MsgBox_Unload.Alert("提示", "发送失败！请检查填写内容"); 
	        	// }
	        },
	        error : function () {
	        	$.MsgBox_Unload.Alert("提示", "发送失败！"); 
	        }
		});  

	})

	//合并总计
	jQuery.fn.rowspan = function(colIdx) { 
	    return this.each(function(){
	        var that;
	        $('tr', this).each(function(row) {
	            $('td:eq('+colIdx+')', this).filter(':visible').each(function(col) {
	                if (that!=null ) {
	                    rowspan = $(that).attr("rowSpan");
	                    if (rowspan == undefined) {
	                        $(that).attr("rowSpan",1);
	                        rowspan = $(that).attr("rowSpan"); }
	                    rowspan = Number(rowspan)+1;
	                    $(that).attr("rowSpan",rowspan);
	                    $(this).hide();
	                } else {
	                    that = this;
	                }
	            });
	        });
	    });
	}

	var addFlag = 0;
	var updateFlag = 0;
	//点击添加
	function AddContract() {
		addFlag = 0;
		$(".contract_add .addList").remove(); 
	    $('.MailBar_cover_color').show();
	    $(".MailBar_cover_color").css("height",$(".content").height()+160);
	    $('.contract_add').show();
	};
	//点击取消
	$('#add_cancel,.contractAdd_close').click(function () {
	    $('.MailBar_cover_color').hide();
	    $('.contract_add').hide();
	});	
	$('#update_cancel,.contractUpdate_close').click(function () {
	    $('.MailBar_cover_color').hide();
	    $('.contract_update').hide();
	});	
	$('#SendEmail_cancel,.SendEmailBox_close').click(function () {
	    $('.MailBar_cover_color').hide();
	    $('.SendEmailBox').hide();
	});	
	
	//修改狂 显示
	$(".contract-edit").click(function(){
		updateFlag = 0;
		$('.MailBar_cover_color').show();
		 $(".MailBar_cover_color").css("height",$(".content").height()+160);
	    $('.contract_update').show();
	    $('.contract_update .addList').remove();
	    var currentTr = $(this).parent();
		var ID = currentTr.find("td").eq(0).attr("value");
		console.log(ID)
		$(".contract_update .contract_title").attr("value",ID);
		$.ajax({
		    url: 'TransportDirectiveModel',
		    type: 'Get',
		    data: {
		    	ID : ID,
		    },
		    dataType: 'json',
			success: function (data) {
				var listStr = "";
				for(var i = 1 ; i<data.length;i++){
					if(data[i].WoodenPallet =="是"){
						var Woodstr = '<td contenteditable="true"  class="IsWood"><input type="radio" name="isWoodRadio'+i+'" class="isWoodRadio" value="是" checked="checked"/>是&nbsp;<input type="radio" name="isWoodRadio'+i+'" class="notWoodRadio" value="否" />否</td>';
					}
					else{
						var Woodstr = '<td contenteditable="true"  class="IsWood"><input type="radio" name="isWoodRadio'+i+'" class="isWoodRadio" value="是" />是&nbsp;<input type="radio" name="isWoodRadio'+i+'" class="notWoodRadio" value="否" checked="checked"/>否</td>';
					}
					if(data[i].UnloadPlat =="是"){
						var Platstr = '<td contenteditable="true"  class="IsPlatform"><input type="radio" name="isPlatRadio'+i+'" class="isPlatRadio" value="是"  checked="checked"/>是&nbsp;<input type="radio" name="isPlatRadio'+i+'" class="notPlatRadio" value="否" />否</td>';
					}
					else{
						var Platstr = '<td contenteditable="true"  class="IsPlatform"><input type="radio" name="isPlatRadio'+i+'" class="isPlatRadio" value="是" />是&nbsp;<input type="radio" name="isPlatRadio'+i+'" class="notPlatRadio" value="否"  checked="checked"/>否</td>';
					}
					var getDate;
					var getTime;
					if(currentTr.find("td").eq(3).text()==""||currentTr.find("td").eq(3).text()=="--"){
						getDate = "";
						getTime = "";
					}else{
						getDate = currentTr.find("td").eq(3).text().split(" ")[0];
						getTime = currentTr.find("td").eq(3).text().split(" ")[1].slice(0,5);
					}
					// console.log(currentTr.find("td").eq(3).text().split(" ")[1].slice(0,5));
					// var getDate = currentTr.find("td").eq(3).text().split(" ")[0];
					// var getTime = currentTr.find("td").eq(3).text().split(" ")[1].slice(0,5);
					listStr += '<tr class="addList">'+
				    '<td value="'+data[i].AddrID+'"  class="firstChild">'+i+'</td>'+
					'<td contenteditable="true"  class="addList_contract">'+data[i].ContractNO+'</td>	'+
					'<td contenteditable="true"  class="addList_size">'+data[i].Size+'</td>'+
					'<td contenteditable="true"  class="KG">'+data[i].Weight+'</td>'+
					'<td contenteditable="true"  class="BoxNum">'+data[i].Quantity+'</td>'+
					Woodstr+
					Platstr+ 
					'<td>'+
						'<input type="date"  class="pickupDate" value="'+getDate+'"/>'+
						'<input type="time"  class="pickupTime" value="'+getTime+'"/>'+	
					'</td>'+
					'<td class="pickupTd">'+
						'公司：<span contenteditable="true" class="pickup_App">'+data[i].PickCompany+'</span></br>'+
						'地址：<span contenteditable="true" class="pickup_Add">'+data[i].PickAddress+'</span>	</br>'+
						'联系人：<span contenteditable="true" class="pickup_Name">'+data[i].PickContact+'</span>	</br>'+
						'手机：<span contenteditable="true" class="pickup_Tel">'+data[i].PickTel+'</span>	</br>'+
					'</td>	'+
					'<td class="ReceivingTd">'+
						'公司：<span contenteditable="true" class="Receiving_App">'+data[i].ReceivingCompany+'</span>		</br>'+
						'地址：<span contenteditable="true" class="Receiving_Add">'+data[i].ReceivingAddress+'</span>	</br>'+
						'联系人：<span contenteditable="true" class="Receiving_Name">'+data[i].ReceivingContact+'</span>	</br>'+
						'手机：<span contenteditable="true" class="Receiving_Tel">'+data[i].ReceivingTel+'</span>	</br>'+
					'</td>	'+
					'<td contenteditable="true"  class="transporters">'+currentTr.find("td").eq(4).text()+'</td>'+
					'<td>共计 <span contenteditable="true"  class="Carton">'+currentTr.find("td").eq(7).text()+'</span>个纸箱， <span contenteditable="true"  class="Wooden">'+currentTr.find("td").eq(8).text()+'</span>个木箱， <span contenteditable="true"  class="AddNum">'+currentTr.find("td").eq(9).text()+'</span>个收货地址</td>'+	
				'</tr>';
				}
				$(".contract_update .basic_info .contract_basic").append(listStr);
				$(".contract_update .basic_info .contract_basic").rowspan(11);
	        },
	        error : function () {
	        	 $.MsgBox.Alert("提示", "服务器繁忙，请稍候！"); 
	        }
		});
		
	})
	//修改狂中提交
	$("#update_submit").click(function(){
		var ID=$(".contract_update .contract_title").attr("value");	
		/*  var PackingDate = $(".contract_update .pickupDate").eq(0).val();; */
		var oldPackingDate1 = $(".contract_update .pickupDate").eq(0).val();
		if(oldPackingDate1==""||oldPackingDate1=="--"||oldPackingDate1==null||oldPackingDate1==undefined){
			oldPackingDate1 = "0000-00-00";
		}
		var oldPackingDate2 = $(".contract_update .pickupTime").eq(0).val();
		if(oldPackingDate2==""||oldPackingDate2=="--"||oldPackingDate2==null||oldPackingDate2==undefined){
			oldPackingDate2 = "00:00";
		}
		var PackingDate = oldPackingDate1+" "+oldPackingDate2+":00";
		var transporters=$(".contract_update .addList").eq(0).find(".transporters").text();
		var Total1 = $(".contract_update .Carton").eq(0).text();
		var Total2 = $(".contract_update .Wooden").eq(0).text();
		var Total3 = $(".contract_update .AddNum").eq(0).text();
		// var ReceivingAddress=[]
		// var ReceivingCompany=[]
		// var ReceivingContact=[]
		// var ReceivingTel=[]
		// var AddrID=[]
		// var Size=[]
		// var Weight=[]
		// var Quantity=[]
		// var WoodenPallet=[]
		// var UnloadPlat=[]
		// var PickCompany=[]
		// var PickAddress=[]
		// var PickContact=[]
		// var PickTel=[]
		// var ContractNO =[];   
		var GoodsInfo = [];
		for(var i = 0 ; i < $(".contract_update .addList").length ; i++){
			var item = {};
			item.ReceivingAddress = $(".contract_update .addList").eq(i).find(".Receiving_Add").text();
			item.ReceivingCompany = $(".contract_update .addList").eq(i).find(".Receiving_App").text();
			item.ReceivingContact = $(".contract_update .addList").eq(i).find(".Receiving_Name").text();
			item.ReceivingTel = $(".contract_update .addList").eq(i).find(".Receiving_Tel").text();
			item.AddrID = $(".contract_update .addList").eq(i).find("td").eq(0).attr("value");
			item.Size = $(".contract_update .addList").eq(i).find(".addList_size").text();
			item.Weight = $(".contract_update .addList").eq(i).find(".KG").text();
			item.Quantity = $(".contract_update .addList").eq(i).find(".BoxNum").text();
			item.WoodenPallet = $(".contract_update .addList").eq(i).find(".IsWood input[type=radio]:checked").val();
			item.UnloadPlat = $(".contract_update .addList").eq(i).find(".IsPlatform input[type=radio]:checked").val();
			item.PickCompany = $(".contract_update .addList").eq(i).find(".pickup_App").text();
			item.PickAddress = $(".contract_update .addList").eq(i).find(".pickup_Add").text();
			item.PickContact = $(".contract_update .addList").eq(i).find(".pickup_Name").text();
			item.PickTel = $(".contract_update .addList").eq(i).find(".pickup_Tel").text();
			item.ContractNO = $(".contract_update .addList").eq(i).find(".addList_contract").text();
			GoodsInfo.push(item);
		} 
		var GoodsInfoStr = JSON.stringify(GoodsInfo);
		$.ajax({
			    url: 'TransportDirectiveOperate',
			    type: 'POST',
			    data: {
			    	// ReceivingAddress : ReceivingAddress,
			    	// ReceivingCompany : ReceivingCompany,
			    	// ReceivingContact : ReceivingContact,
			    	// ReceivingTel : ReceivingTel,
			    	// AddrID : AddrID,
			    	// Size : Size,
			    	// Weight : Weight,
			    	// Quantity : Quantity,
			    	// WoodenPallet : WoodenPallet,
			    	// UnloadPlat : UnloadPlat,
			    	PackingDate : PackingDate,
			    	// PickCompany : PickCompany,
			    	// PickAddress : PickAddress,
			    	// PickContact : PickContact,
			    	// PickTel : PickTel,
			    	transporters :transporters,
			    	// ContractNO : ContractNO,
			    	Total1 : Total1,
			    	Total2 : Total2,
			    	Total3 : Total3,
			    	ID : ID,
			    	GoodsInfo: GoodsInfoStr
			    },
				success: function (data) {
					console.log(data == "true")
		        	if(data == "true"){
		        		$.MsgBox.Alert("提示", "保存成功！"); 
		        	}else{
		        		$.MsgBox_Unload.Alert("提示", "保存失败！请检查表格填写内容"); 
		        	}
		        },
		        error: function () {
		        	 $.MsgBox_Unload.Alert("提示", "服务器繁忙！保存失败！"); 
		        }
			});
	})
	
	//修改狂中的添加
	$(".contract_update .table_title .addItem").click(function(){
		updateFlag++;
		var listNum = $(".contract_update .addList").length;
		listNum++;
		var listStr = "";
		listStr += '<tr class="addList">'+
	    '<td value="0" class="firstChild">'+listNum+'</td>'+
		'<td contenteditable="true"  class="addList_contract"></td>	'+
		'<td contenteditable="true"  class="addList_size"></td>'+
		'<td contenteditable="true"  class="KG"></td>'+
		'<td contenteditable="true"  class="BoxNum"></td>'+
		'<td contenteditable="true"  class="IsWood"><input type="radio" name="isWoodRadio'+updateFlag+'" class="isWoodRadio" value="是" checked="checked"/>是&nbsp;<input type="radio" name="isWoodRadio'+updateFlag+'" class="notWoodRadio" value="否" />否</td>'+
		'<td contenteditable="true"  class="IsPlatform"><input type="radio" name="isPlatRadio'+updateFlag+'" class="isPlatRadio" value="是" checked="checked"/>是&nbsp;<input type="radio" name="isPlatRadio'+updateFlag+'" class="notPlatRadio" value="否" />否</td>'+
		'<td>'+
			'<input type="date"  class="pickupDate" />'+
			'<input type="time"  class="pickupTime" />'+
		'</td>'+
		'<td class="pickupTd">'+
			'公司：<span contenteditable="true" class="pickup_App">苏州伊欧陆系统集成有限公司</span>	</br>'+
			'地址：<span contenteditable="true" class="pickup_Add">苏州工业园区星湖街218号生物纳米园A7楼305室</span>	</br>'+
			'联系人：<span contenteditable="true" class="pickup_Name"></span>	</br>'+
			'手机：<span contenteditable="true" class="pickup_Tel"></span>	</br>'+
		'</td>	'+
		'<td class="ReceivingTd">'+
			'公司：<span contenteditable="true" class="Receiving_App"></span>		</br>'+
			'地址：<span contenteditable="true" class="Receiving_Add"> </span>	</br>'+
			'联系人：<span contenteditable="true" class="Receiving_Name"></span>	</br>'+
			'手机：<span contenteditable="true" class="Receiving_Tel"></span>	</br>'+
		'</td>	'+
		'<td contenteditable="true"  class="transporters"></td>'+
		'<td>共计 <span contenteditable="true"  class="Carton"></span>个纸箱， <span contenteditable="true"  class="Wooden"></span>个木箱， <span contenteditable="true"  class="AddNum"></span>个收货地址</td>'+	
	'</tr>';
		
		$(".contract_update .basic_info .contract_basic").append(listStr);
		$(".contract_update .basic_info .contract_basic").rowspan(11);
	})
	
	//添加中的提交
	$("#add_submit").click(function(){
		var ID="0";	
		 // var PackingDate = $(".contract_add .pickupDate").eq(0).val() +" "+$(".contract_add .pickupTime").eq(0).val()+":00";
		 var oldPackingDate1 = $(".contract_add .pickupDate").eq(0).val();
		 if(oldPackingDate1==""||oldPackingDate1=="--"||oldPackingDate1==null||oldPackingDate1==undefined){
		 	oldPackingDate1 = "0000-00-00";
		 }
		 var oldPackingDate2 = $(".contract_add .pickupTime").eq(0).val();
		 if(oldPackingDate2==""||oldPackingDate2=="--"||oldPackingDate2==null||oldPackingDate2==undefined){
		 	oldPackingDate2 = "00:00";
		 }
		 var PackingDate = oldPackingDate1+" "+oldPackingDate2+":00";
		var transporters=$(".contract_add .addList").eq(0).find(".transporters").text();
		 console.log(PackingDate);
		var Total1 = $(".contract_add .Carton").text();
		var Total2 = $(".contract_add .Wooden").text();
		var Total3 = $(".contract_add .AddNum").text();
		var GoodsInfo = [];

		// var ReceivingAddress=[]
		// var ReceivingCompany=[]
		// var ReceivingContact=[]
		// var ReceivingTel=[]
		// var AddrID=[]
		// var Size=[]
		// var Weight=[]
		// var Quantity=[]
		// var WoodenPallet=[]
		// var UnloadPlat=[]
		// var PickCompany=[]
		// var PickAddress=[]
		// var PickContact=[]
		// var PickTel=[]
		// var ContractNO =[];   
		for(var i = 0 ; i < $(".contract_add .addList").length ; i++){
			var item = {};
			item.ReceivingAddress = $(".contract_add .addList").eq(i).find(".Receiving_Add").text();
			item.ReceivingCompany = $(".contract_add .addList").eq(i).find(".Receiving_App").text();
			item.ReceivingContact = $(".contract_add .addList").eq(i).find(".Receiving_Name").text();
			item.ReceivingTel = $(".contract_add .addList").eq(i).find(".Receiving_Tel").text();
			item.AddrID = 0;
			item.Size = $(".contract_add .addList").eq(i).find(".addList_size").text();
			item.Weight = $(".contract_add .addList").eq(i).find(".KG").text();
			item.Quantity = $(".contract_add .addList").eq(i).find(".BoxNum").text();
			item.WoodenPallet = $(".contract_add .addList").eq(i).find(".IsWood input[type=radio]:checked").val();
			item.UnloadPlat = $(".contract_add .addList").eq(i).find(".IsPlatform input[type=radio]:checked").val();
			item.PickCompany = $(".contract_add .addList").eq(i).find(".pickup_App").text();
			item.PickAddress = $(".contract_add .addList").eq(i).find(".pickup_Add").text();
			item.PickContact = $(".contract_add .addList").eq(i).find(".pickup_Name").text();
			item.PickTel = $(".contract_add .addList").eq(i).find(".pickup_Tel").text();
			item.ContractNO = $(".contract_add .addList").eq(i).find(".addList_contract").text();
			GoodsInfo.push(item);
		} 
		var GoodsInfoStr = JSON.stringify(GoodsInfo);
		$.ajax({
			    url: 'TransportDirectiveOperate',
			    type: 'POST',
			    data: {
			    	// ReceivingAddress : ReceivingAddress,
			    	// ReceivingCompany : ReceivingCompany,
			    	// ReceivingContact : ReceivingContact,
			    	// ReceivingTel : ReceivingTel,
			    	// AddrID : AddrID,
			    	// Size : Size,
			    	// Weight : Weight,
			    	// Quantity : Quantity,
			    	// WoodenPallet : WoodenPallet,
			    	// UnloadPlat : UnloadPlat,
			    	PackingDate : PackingDate,
			    	// PickCompany : PickCompany,
			    	// PickAddress : PickAddress,
			    	// PickContact : PickContact,
			    	// PickTel : PickTel,
			    	transporters :transporters,
			    	// ContractNO : ContractNO,
			    	Total1 : Total1,
			    	Total2 : Total2,
			    	Total3 : Total3,
			    	ID : ID,
			    	GoodsInfo: GoodsInfoStr
			    },
				success : function (data) {
					console.log(data == "true")
		        	if(data == "true"){
		        		 $.MsgBox.Alert("提示", "保存成功！"); 
		        	}else{
		        		$.MsgBox.Alert("提示", "保存失败！");
		        	}
		        },
		        error : function () {
		        	 $.MsgBox.Alert("提示", "服务器繁忙！保存失败！"); 
		        }
			});
		 
	})
	
	//添加狂中的添加
	$(".contract_add .table_title .addItem").click(function(){
		addFlag++;
		var listStr = "";
		listStr += '<tr class="addList">'+
	    '<td class="firstChild" >'+addFlag+'</td>'+
		'<td contenteditable="true"  class="addList_contract"></td>	'+
		'<td contenteditable="true"  class="addList_size"></td>'+
		'<td contenteditable="true"  class="KG"></td>'+
		'<td contenteditable="true"  class="BoxNum"></td>'+
		'<td contenteditable="true"  class="IsWood"><input type="radio" name="isWoodRadio'+addFlag+'" class="isWoodRadio" value="是" checked="checked"/>是&nbsp;<input type="radio" name="isWoodRadio'+addFlag+'" class="notWoodRadio" value="否" />否</td>'+
		'<td contenteditable="true"  class="IsPlatform"><input type="radio" name="isPlatRadio'+addFlag+'" class="isPlatRadio" value="是" checked="checked"/>是&nbsp;<input type="radio" name="isPlatRadio'+addFlag+'" class="notPlatRadio" value="否" />否</td>'+
		'<td>'+
			'<input type="date"  class="pickupDate" />'+	
			'<input type="time"  class="pickupTime" />'+	
		'</td>'+
		'<td class="pickupTd">'+
			'公司：<span contenteditable="true" class="pickup_App">苏州伊欧陆系统集成有限公司</span>	</br>'+
			'地址：<span contenteditable="true" class="pickup_Add">苏州工业园区星湖街218号生物纳米园A7楼305室</span>	</br>'+
			'联系人：<span contenteditable="true" class="pickup_Name"></span>	</br>'+
			'手机：<span contenteditable="true" class="pickup_Tel"></span>	</br>'+
		'</td>	'+
		'<td class="ReceivingTd">'+
			'公司：<span contenteditable="true" class="Receiving_App"></span>		</br>'+
			'地址：<span contenteditable="true" class="Receiving_Add"> </span>	</br>'+
			'联系人：<span contenteditable="true" class="Receiving_Name"></span>	</br>'+
			'手机：<span contenteditable="true" class="Receiving_Tel"></span>	</br>'+
		'</td>	'+
		'<td contenteditable="true"  class="transporters"></td>'+
		'<td>共计 <span contenteditable="true"  class="Carton"></span>个纸箱， <span contenteditable="true"  class="Wooden"></span>个木箱， <span contenteditable="true"  class="AddNum"></span>个收货地址</td>'+	
	'</tr>';
		
		$(".contract_add .contract_basic ").append(listStr);
		$(".contract_add .basic_info .contract_basic").rowspan(11);
	})
	
	//点击确定刷新页面
	$(document).on("click", "#mb_btn_ok", function () {
	    window.location.reload();
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
		$(".Domestic,.ExitOrEn").click(function(){
			 // if($(this).attr("Class") == "Domestic"){
				//  document.getElementById("ToTransportDirective").click();
			 // }
			 // else{
				//  document.getElementById("ToInsurance").click();
			 // }
		 	var eouluProjectHref = window.location.href;
		 	if(eouluProjectHref.indexOf("cfChicken8/")>-1 || eouluProjectHref.indexOf("Logistics/")>-1){
		 		eouluProjectHref = eouluProjectHref.indexOf("cfChicken8/")>-1?eouluProjectHref.split("cfChicken8/")[0]+"cfChicken8/":eouluProjectHref.split("Logistics/")[0]+"Logistics/";
		 	}
			 if($(this).attr("Class") == "Domestic"){
				 // document.getElementById("ToTransportDirective").click();
				 window.location.href = eouluProjectHref + "TransportDirective";
			 }
			 else{
				 // document.getElementById("ToInsurance").click();
				 window.location.href = eouluProjectHref + "Insurance";
			 }
	 	})
	 	
		//判断是否已发送，改变颜色
		for(var i = 1 ; i < $("#table1 tr").length; i++){
			 if($("#table1 tr").eq(i).find(".SendEmail").text()=="已发送"){
				 $("#table1 tr").eq(i).find(".SendEmail").css("color","green");
			 }
			 else{
				 $("#table1 tr").eq(i).find(".SendEmail").css("color","red");
			 }
			 var time = $("#table1 tr").eq(i).find("td").eq(3).text().slice(0,16);
			 $("#table1 tr").eq(i).find("td").eq(3).text(time);
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
		
	})
	
 /*************自动检索邮箱**********/
    var old_value = "";  
    var highlightindex = -1;   //高亮  
    //自动完成  
    function AutoComplete(auto, search, mylist) {  
        if ($("#" + search).val() != old_value || old_value == "") {  
        var autoNode = $("#" + auto);   //缓存对象（弹出框）  
        var list = new Array();  
        var n = 0;  
        old_value = $("#" + search).text();  
        if(old_value.indexOf(";") >0){
        	 old_value = old_value.split(";").pop();
        }
        for (i in mylist) {  
            if (mylist[i].split("@eoulu.com")[0].indexOf(old_value) >= 0) {  
                list[n++] = mylist[i];  
            }  
        }  
        if (list.length == 0) {  
            autoNode.hide();  
            return;  
        }  
        autoNode.empty();  //清空上次的记录  
        for (i in list) {  
            var wordNode = list[i];   //弹出框里的每一条内容  
            var newDivNode = $("<div>").attr("id", i);    //设置每个节点的id值  
            newDivNode.attr("style", "font:14px/25px arial;height:25px;padding:0 8px;cursor: pointer;");  
            newDivNode.html(wordNode).appendTo(autoNode);  //追加到弹出框  
            //鼠标移入高亮，移开不高亮  
            newDivNode.mouseover(function () {  
                if (highlightindex != -1) {        //原来高亮的节点要取消高亮（是-1就不需要了）  
                    autoNode.children("div").eq(highlightindex).css("background-color", "white");  
                }  
                //记录新的高亮节点索引  
                highlightindex = $(this).attr("id");  
                $(this).css("background-color", "#ebebeb");  
            });  
            newDivNode.mouseout(function () {  
                $(this).css("background-color", "white");  
            });  
            
            //鼠标点击文字上屏  
            newDivNode.click(function () {  
                    //取出高亮节点的文本内容  
                  var comText = autoNode.hide().children("div").eq(highlightindex).text();  
                    highlightindex = -1;  
                    //文本框中的内容变成高亮节点的内容  
                    if($("#" + search).text().indexOf(";")>0){
                    	var searchText = $("#" + search).text().split(";");
                    	searchText.pop();
                    	searchText = searchText.join(";");
                   		 $("#" + search).text(searchText+";"+comText+";");  
                    }
                    else{
                    	$("#" + search).text(comText+";");  
                    }
                })  
                if (list.length > 0) {    //如果返回值有内容就显示出来  
                    autoNode.show();  
                } else {               //服务器端无内容返回 那么隐藏弹出框  
                    autoNode.hide();  
                    //弹出框隐藏的同时，高亮节点索引值也变成-1  
                    highlightindex = -1;  
                }  
            }  
        }  
        
        //点击页面隐藏自动补全提示框  
        document.onclick = function (e) {  
            var e = e ? e : window.event;  
            var tar = e.srcElement || e.target;  
            if (tar.id != search) {  
                if ($("#" + auto).is(":visible")) {  
                    $("#" + auto).css("display", "none")  
                }  
            }  
        }  
    }  

    $(document).on("click",".eyes_psw",function(){
    	if ($(this).siblings("input.EmailPwd").attr("type") == "text") {
    	    $(this).siblings("input.EmailPwd").attr("type","password");  
    	}else{
    	  $(this).siblings("input.EmailPwd").attr("type","text"); 
    	} 
    });

    $(document).on("blur",".addList .KG",function(){
    	var curVal = $(this).text().replace(/[^\d^\.]+/g,"");
    	$(this).text(curVal);
    });
</script>
</html>
