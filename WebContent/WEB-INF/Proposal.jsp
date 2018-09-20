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
<title>国内货物运输保单</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" />
<link rel="stylesheet" type="text/css" href="css/Proposal.css">
<style>
	.content {
		padding-bottom: 100px !important;
	}
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

			<!-- 查询功能 -->
			 <div style="display: none">
				<input type="text" value="" name="query_type"> 
				<input type="text" value="" name="classify1"> 
				<input type="text" value="" name="classify2"> 
				<input type="text" value="" name="parameter1"> 
				<input type="text" value="" name="parameter2">
			  </div>  

 			<form id="top_text_from" name="top_text_from" method="post">
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
							<input type="button" value="搜索" class="bToggle" onclick="ChangeSearch()"> 
							<input type="button" value="取消" class="bToggle" onclick="ChangeCancel()">
						</div>
				</div>
				<div class="choose">
					<input type="button" value="添加" class="bToggle addData" >
				</div>

		 </form> 
		 	 <table border="1" cellspacing="0" cellspadding="0" id="table1">
				<tr style="background: #bfbfbf;">
					<td  style="width:10%;">序号</td>
					<td style="display:none;">修改</td>
					<td  style="width:30%;">客户名称 </td>
					<td  style="width:30%;">合同名称</td>
					<td  style="width:30%;">合同号</td>
					<td style="width: 145px; display:none;">模板预览（可导出）</td>
					<td style="display:none;">删除数据</td>
					
					<td style="display:none;">Name</td>
					<td style="display:none;">ADD</td>
					<td style="display:none;">ATT</td>
					<td style="display:none;">Tel</td>
					<td style="display:none;">ConsigneeName</td>
					<td style="display:none;">ConsigneeADD</td>
					<td style="display:none;">ConsigneeATTN</td>
					<td style="display:none;">ConsigneeTel</td>
					<td style="display:none;">Shipment</td>
					<td style="display:none;">Destination</td>
					<td style="display:none;">Model</td>
					<td style="display:none;">Description</td>
					<td style="display:none;">Qty</td>
					<td style="display:none;">Size</td>
					<td style="display:none;">ShippingMarkNO</td>
					<td style="display:none;">ShippingMarkNOADD</td>
					<td style="display:none;">InvoiceUSD</td>
					<td style="display:none;">InsuranceUSD</td>
					<td style="display:none;">FinalADD</td>
					<td style="display:none;">Insured</td>
					<td style="display:none;">Currency</td>
					<td style="display:none;">Address</td>
					
				</tr>
				 <c:forEach var="orderInfo" items="${proposal}" varStatus="status">
					<c:if test="${status.index>0}">
						<tr>
							<td value="${orderInfo['ID']}" class="contract-show" title="修改或预览">${status.index+(currentPage-1)*10}</td>
							<td style="display:none;"> <i class="fa fa-edit contract-edit" value="${orderInfo['ID']}"></i></td>
							<td>${orderInfo['Customer']}</td>
							<td>${orderInfo['ContractTitle']}</td>
							<td>${orderInfo['ContractNO']}</td>			<!-- 4 -->
							<td style="display:none;"><i class="fa fa-eye contract-show"></i></td>
							<td style="display:none;"><i class="fa fa-trash-o del"></i></td>
							<td style="display:none;">${orderInfo['InvoiceNO']}</td>    <!--7  -->
							<td style="display:none;">${orderInfo['ContractNO']}</td>     <!--8  -->
							<td style="display:none;">${orderInfo['GoodsNO']}</td>     <!--9  -->
							<td style="display:none;">${orderInfo['DCNO']}</td>     <!--10  -->
							<td style="display:none;">${orderInfo['StartDate']}</td><!--11  -->
							<td style="display:none;">${orderInfo['InvoiceAmount']}</td><!--12 -->
							<td style="display:none;">${orderInfo['Currency']}</td><!--13 -->
							<td style="display:none;">${orderInfo['Inchoat']}</td><!--14 -->
							<td style="display:none;">${orderInfo['Pass']}</td><!--15 -->
							<td style="display:none;">${orderInfo['Destination']}</td><!--16 -->
							<td style="display:none;">${orderInfo['ShippingMark']}</td>    <!--17  -->
							<td style="display:none;">${orderInfo['ShippingMark']}</td>    <!--18  -->
							<td style="display:none;">${orderInfo['PackingNumber']}</td>       <!--19  -->
							<td style="display:none;">${orderInfo['ApplicantDate']}</td> <!--20 -->
							<td style="display:none;">${orderInfo['InsuranceUSD']}</td> <!--21 -->

							<td style="display:none;">${orderInfo['InsuredName']}</td><!--22 -->
							<td style="display:none;">${orderInfo['PayableAt']}</td><!--23 -->
							<td style="display:none;">${orderInfo['Indemnity']}</td>    <!--24  -->
							<td style="display:none;">${orderInfo['ToPort']}</td>    <!--25  -->
							<td style="display:none;">${orderInfo['TransUtil']}</td>       <!--26  -->
							<td style="display:none;">${orderInfo['BLNO']}</td> <!--27 -->
							<td style="display:none;">${orderInfo['InsuranceType']}</td> <!--28 -->
							<td style="display:none;">${orderInfo['Transport']}</td> <!--29 -->
						</tr>
						
					</c:if>	
				</c:forEach>
			</table>
		
	 	 <c:choose>
				<c:when test="${queryType == 'common'}">
					<c:set var="queryUrl"
					value="Proposal?type1=${classify1 }&searchContent1=${parameter1}&selected=${str}&currentPage=">
					</c:set>
				</c:when>
				<c:otherwise>
					<c:set var="queryUrl"
					value="GetProposalRoute?type1=${classify1 }&searchContent1=${parameter1}&type2=${classify2}&searchContent2=${parameter2}&selected=${str}&currentPage=">
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
	<div id="cover_color" style="z-index:111;"></div>
	<div class="SendEmailBox" >
		<div class="SendEmailBox_title">邮件发送</div>
		<div class="SendEmailBox_close">关闭</div>
			<div class="SendEmailCon">
				<table cellpadding="0" cellspacing="0" border="1"  style="width:90%;margin-left:5%;margin-top:40px;" class="SendEmailTab">
					<tr>
						<td  >发件人</td>
						<td class="Sender"></td>
					</tr>
					<tr>
						<td >邮箱密码</td>
						<td>
							<input type="password" name="password" style="display:none">
							<input type="password" name="password" class="EmailPwd contentNull" />
						</td>
					</tr>
					<tr>
						<td>收件人</td>
						<td contenteditable="true"  id="search_text"  class="contentNull"></td>
						<div id="auto_div"></div>  
					</tr>
					<tr>
						<td>抄送</td>
						<!-- <td  contenteditable="true" class="CC">liuyanan@eoulu.com；jiangyaping@eoulu.com；zhudanni@eoulu.com；caishuhui@eoulu.com</td> -->
						<td contenteditable="true" class="CC contentNull" id="CC">wangning@eoulu.com；wangning@eoulu.com；wangning@eoulu.com；wangning@eoulu.com；</td>
						<div id="CC_div"></div>
					</tr>
					<tr>
						<td>主题</td>
						<td  contenteditable="true" class="theme contentNull" ></td>
					</tr>
					<tr>
						<td>正文</td>
						<td class="EmailContent">
							<div contenteditable="true">您好！</div></br>
							<div contenteditable="true" class="EmailCon"></div>
							<div contenteditable="true">烦请尽快投保,并反馈投保结果。</div>
							<div contenteditable="true">感谢您的支持,如有疑问,请随时联系！</div></br>
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
							<span  class="annexHref" >Proposal.doc</span>
							<!-- <input type="file" name="EmailAnnex" id="file" value="上传附件" /> -->
						</td>
					</tr>
				</table>
			</div>
			<div class="edit_btn">
			<input type="button" value="发送" class="bToggle" id="SendEmail_submit">
			<input type="button" value="取消" class="bToggle" id="SendEmail_cancel">
		</div>
	</div>
	
	<div class="hidePdf" style="display:none;">
			<div id="view" class="news" style="font-family: initial;position:absolute;z-index:11;top:0;left:0;background:#fff;font-size:12px">
    <!--国内货物运输险投保单文档  邮包-->
<div id="table_Proposal" style="position:relative;">
    <p class="tc b" style="font-size:18px;">国内货物运输险投保单</p>
    <div class="content_text b" style="margin-bottom: 20px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 尊敬的客户，感谢您在我公司投保！请您仔细阅读保险条款，尤其是黑体字标注部分的条款内容，并听取保险公司业务人员的说明，
        如对保险公司业务人员的说明不明白或有异议的，请在填写本投保单之前向保险公司业务人员进行询问，如未询问，视同已经对条款内容完全理解并无异议.</div>
    <table cellpadding="0" cellspacing="0" border="1" style=" width:100%;line-height:30px;" id="pageOne">
        <tr>
            <td colspan="2" style="width: 25%"><span>投保人</span></td>
            <td colspan="2" class="b" style="width: 25%"><span>深圳伊欧陆微电子系统有限公司</span></td>
            <td style="width: 10%"><span>联系人及电话</span></td>
            <td colspan="2" style="width: 40%"><span class="c7">王小姐：18611350832</span> </td>
        </tr>
        <tr>
            <td colspan="2"><span>投保人地址及邮编</span></td>
            <td colspan="5"><p class="c7 tc">苏州工业园区星湖街218号生物纳米园A7楼305室</p></td>
        </tr>
        <tr>
            <td colspan="2"><span>被保险人</span></td>
            <td colspan="5"><span>苏州伊欧陆系统集成有限公司</span></td>
        </tr>
        <tr>
            <td colspan="2"><span>运输方式</span></td>
            <td><span class="togParcel">邮包</span></td>
            <td><span>船名/车牌号/航班</span></td>
            <td><span class="c7">飞机、全封闭式卡车</span></td>
            <td><span>发票号</span></td>
            <td><p contenteditable="true" class="InvoiceNO addDataNull placeholderDom" placeholder="发票号" ></p></td>
        </tr>
        <tr>
            <td colspan="2"><span>启运日期</span></td>
            <td><p contenteditable="true" class="StartDate addDataNull placeholderDom" placeholder="2018-01-05"></p></td>
            <td><span>货物运单号</span></td>
            <td><p contenteditable="true" class="GoodsNO addDataNull placeholderDom" placeholder="运单号" ></p></td>
            <td><span>发票金额与币种　</span></td>
       		 <td><p> <span class="placeholderDom Currency addDataNull" placeholder="币种" contenteditable="true" ></span>&nbsp;&nbsp;<span class="placeholderDom InvoiceAmount addDataNull" placeholder="金额" contenteditable="true"></span>
        </tr>
        <tr>
            <td colspan="2"><span>合同号</span></td>
            <td colspan="2"><p contenteditable="true" class="ContractNO addDataNull placeholderDom" placeholder="合同号" ></p></td>
            <td><span>信用证号</span></td>
            <td colspan="2"><p contenteditable="true" class="DCNO addDataNull placeholderDom" placeholder="信用证号" >NA</p></td>
        </tr>
        <tr>
            <td colspan="2"><span>运输路线</span></td>
            <td colspan="3"><p>自&nbsp;:&nbsp;<span contenteditable="true" class="Inchoat addDataNull placeholderDom" placeholder="XX" style="display: inline-block;"></span>
            &nbsp;&nbsp;经&nbsp;：&nbsp;<span contenteditable="true" class="Pass addDataNull placeholderDom" placeholder="XX"  style="display: inline-block;"></span></br>
            &nbsp;&nbsp;至&nbsp;：&nbsp;<span contenteditable="true" class="Destination addDataNull placeholderDom" placeholder="XX"  style="display: inline-block;"></span></p></td>
            <td>加成系数</td>
            <td>10%</td>
        </tr>
        <tr>
            <td colspan="7" style="text-align: center;"><span class="b">明细表</span></td>
        </tr>
        <tr class="tc">
            <td colspan="2"><span >唛头</span></td>
            <td colspan="2"><span>包装及数量</span></td>
            <td colspan="2"><span>保险货物名称</span></td>
            <td><span>保险金额</span> </td>
        </tr>
        <tr class="tc">
            <td colspan="2"><p contenteditable="true" class="ShippingMarkNO addDataNull placeholderDom" placeholder="唛头号" style="display:block;text-decoration: underline;"></p><p contenteditable="true" class="ShippingMarkADD addDataNull placeholderDom" placeholder="唛头地址" ></p></td>
            <td colspan="2">
           	<!--  <p contenteditable="true" class="PackingNumber addDataNull placeholderDom" placeholder="包装及数量" ></p> -->
         		 <input type="text" name="PackingNumber" value="" style="position:absolute;width:120px;"  id="PackingNumber_in" class="PackingNumber addinputNull">
			  	<span>
					<select onchange="document.getElementById('PackingNumber_in').value=this.options[this.selectedIndex].text" class="PackingNumberSel" style="width: 140px;">
						<option value="No" selected="selected">--请选择--</option>
						<option value="GERMANY">CARTONS & WOODERN PACKAGES</option>
						<option value="USA" >CARTONS PACKAGES</option>
						<option value="USA" >WOODERN CASE  PACKAGES</option>
					</select>
				  </span>
            </td>
          <!--   <td colspan="2" class="DescriptionNameTd" style="line-height:17px;height: 150px;"></td> -->
           <td colspan="2" class="DescriptionNameTd" style="line-height:17px;height: 150px;text-align: center;"><pre style='width: 100%;height: 100%;outline:none;font-family: "Arial","microsoft yahei";' contenteditable="true"></pre></td>
            <td><p contenteditable="true" class="InsuranceUSD addDataNull placeholderDom" placeholder="金额" ></p></td>
        </tr>
        <tr>
            <td colspan="7">
                <pre>请如实告知下列情况(如“是”在[  ]中打“ √ ”，可多项选择; 如“否”, 则可不选择):</pre>
                <pre>1. 货物种类      ：      散装货[ √ ]      裸装货 [  ]       二手设备／旧货物[  ]     舱面货[  ]      危险货物[  ] </pre>
                <pre>2. 集装箱货物    ：      普通[ √ ]        开顶[  ]          框架[  ]                 平板[  ]        冷藏[  ]</pre>
                <pre>3. 非集装箱货物  ：      全封闭[ √ ]      半封闭[  ]        平板车[  ] </pre>
                <pre>4. 特殊运输要求  ：      防震[  ]        防倾斜[ √ ]        防尘[  ]</pre>
                <pre>5. 其他          ：      是否是进口转运最后一程内陆运输[  ]　       是否有签署放弃或减轻向承运人追偿的运输合同[  ]</pre>
            </td>
        </tr>
        <tr>
            <td colspan="3" style="width: 45%">
                <pre>投保险别：公路运输险</pre>
                <pre>主险条款</pre>
                <pre class="c7">《邮包一切险》、附加战争险（邮包）PICC</pre>
                <pre class="c7">除外条款： </pre>
                <p class="c7">1 锈蚀、氧化、变色除外条款：不承保任何原因所致的锈蚀、氧化、褪色。</p>
                <p class="c7">2 电子设备功能紊乱条款：不承保任何原因所致的机械、电器、电子设备功能紊乱。</p>
                <p class="c7">3 神秘失踪除外条款：不承保无明显盗窃痕迹、无明确原因及无确切责任方的货物丢失风险。 </p>
                <p class="c7">4 开箱后损失除外条款：所保货物运输途中若未发生保险责任范围内的事故和/或抵达目的地后外包装完好无损，则开箱后发现的货损为除外责任。</p>
                <p class="c7">5 超限运输除外条款：不承担保险货物在运输工具超限（“超限”按照国家有关法律法规、规章、规范性文件等规定认定）运输行为下所遭受的损失赔偿责任</p>
                <p class="c7">6 无人看管货物除外条款：不承保车辆无人看管下之货物。</p>

            </td>
             <td colspan="4" style="width: 55%;vertical-align: baseline; ">
                <p><span>免赔条件：</span><span class="c7">每次事故免赔：盗抢损失每次事故绝对免赔额为人民币3000元或损失金额的15%，以高者为准；</span></p>
                <p class="c7">其他事故绝对免赔额为人民币2000元或损失金额的15%，以高者为准。</p>
            </td>
           </tr>
        <tr>
            <td colspan="7">
                <p>特别约定：</p>
                <p class="c7">1 保险责任至目的地仓库自动终止； </p>
                <p class="c7">2 在被保险人不是实际承运人的情况下，被保险人必须将有关业务委托给有资质的并具备合格驾驶证、行驶证及营运证的货运代理公司或承运公司运输，否则保险人不负责赔偿造成的任何损失、费用及责任。</p>
            </td>
        </tr>
        <tr>
            <td colspan="7" style="position:relative;">
                <p class="b">投保人声明：</p>
                <p class="b" style="margin-bottom: 45px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;上述各项内容填写属实，如非本投保人亲笔填写亦属于在本投保人授权下的行为。本人已收到并详细阅读投保险种对应的贵公司保险条款，尤其是黑体字部分的条款内容；并对保险公司就保险条款内容，
                    特别是免除保险公司责任的条款的提示和说明完全理解，没有异议，申请投保。</p>
                <p style="display: inline-block; margin-left: 30%;" class="lf"><span> 投保人签名（盖章）：</span><img src="image/IMG_1.jpg"></p>
                <p style="display: inline-block;position:absolute;bottom:0px; right: 10%;" class="ApplicantDate rt">日期：
                <span contenteditable="true" class="ApplicantYear">2016</span> &nbsp;&nbsp; 年&nbsp;&nbsp;<span contenteditable="true" class="ApplicantMonth">8</span>&nbsp;&nbsp; 月&nbsp;&nbsp; <span contenteditable="true" class="ApplicantDay">22</span>
                  &nbsp;&nbsp;  日</p>
            </td>
        </tr>
   <!--  </table>
    <table cellpadding="0" cellspacing="0" border="1" style=" width:100%;margin-bottom: 10px;"> -->
        <tr>
            <td colspan="7" class="b tc">以下内容为保险公司人员填写</td>
        </tr>
        <tr>
            <td colspan="2"><span>业务员</span></td>
            <td><span>宝安营业二部吴丽萍</span></td>
            <td><span>业务员联系电话</span></td>
            <td colspan="3"><span>33230322</span></td>
        </tr>
        <tr>
            <td colspan="2"><span>承保费率 </span></td>
            <td><span>0.035%</span></td>
            <td><span>保险费</span></td>
            <td colspan="3"><span></span></td>
        </tr>
        <tr>
            <td colspan="7">
                <p class="m30b" style="margin-bottom: 25px;">核保人意见：</p>
                <p style="display: inline-block; margin-left: 30%;" class="lf"><span>核保人签章： </span></p>
                <p style="display: inline-block; margin-right: 10%;" class="rt">日期：&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日
                </p>
            </td>
        </tr>
    </table>
</div>
<div id="table_Proposal_Air" style="position:relative;display:none;">
    <p class="tc b" style="font-size:18px;">进出口货物运输保险投保单</p>
    <p class="tc b" style="font-size:18px;font-family: arial;">Application Form For I/E Cargo Insurance</p>
    <div class="content_text b" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 尊敬的客户，感谢您在我公司投保！请您仔细阅读保险条款，尤其是黑体字标注部分的条款内容，并听取保险公司业务人员的说明，如对保险公司业务人员的说明不明白或有异议的，请在填写本投保单之前向保险公司业务人员进行询问，如未询问，视同已经对条款内容完全理解并无异议.</div>
    <div class="content_text b" style="font-family: arial;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Dear Customer, thanks for your submission! Please read through the terms and condition, especially the bold type parts, and listen to our producer’s instruction. When you have any objection or wondering at her/his instruction, please enquiry from her/him before you fulfills the application form. If no enquiry, it is deemed to fully understand the terms and conditions without objection.</div>
    
    <table cellpadding="0" cellspacing="0" border="1" style=" width:100%;line-height:30px;" id="pageOne">
        <tr>
            <td colspan="3" ><span>投保人Applicant:</span></td>
            <td colspan="9" class="b tc" ><span>深圳伊欧陆微电子系统有限公司</span></td>
        </tr>
        <tr>
            <td colspan="3"><span>被保险人 Insured:</span></td>
            <td colspan="9" style="text-align: center;">
          	  <!-- <p class="c7 tc InsuredName" contenteditable="true">苏州工业园区星湖街218号生物纳米园A7楼305室</p> -->
          	  <input type="text" name="InsuredName" value="" style="position:absolute;width:200px;"  id="InsuredName" class="InsuredName addinputNull">
			  <span>
				<select onchange="document.getElementById('InsuredName').value=this.options[this.selectedIndex].text" class="InsuredNameSel" style="width: 223px;">
					<option value="No" selected="selected">--请选择--</option>
					<option value="GERMANY">苏州伊欧陆系统集成有限公司</option>
					<option value="USA" >HK EOULU TRADING LIMITED</option>
				</select>
			  </span>
            </td>
        </tr>
        <tr style="    line-height: 24px;">
            <td colspan="4"><span>发票号 Invoice No.:</span><br /><span class="placeholderDom InvoiceNO addDataNull" placeholder="发票号" contenteditable="true"></span> </td>
            <td colspan="4"><span>提单号 B/L No.: </span><br /><span class="placeholderDom BLNO  addDataNull" placeholder="提单号" contenteditable="true"></span> </td>
            <td colspan="4"><span>合同号 Contract No.: </span><br /><span class="placeholderDom ContractNO  addDataNull" placeholder="合同号" contenteditable="true"></span> </td>
        </tr>
        
         <tr style=" line-height: 24px;">
            <td colspan="4" ><span>信用证号 L/C No.:</span><br /><span class="placeholderDom DCNO  addDataNull" placeholder="信用证号" contenteditable="true"></span> </td>
            <td colspan="4" ><span>发票金额 Invoice Amount:　 </span><br /><span class="placeholderDom Currency  addDataNull" placeholder="币种" contenteditable="true"></span>&nbsp;&nbsp;<span class="placeholderDom InvoiceAmount addDataNull" placeholder="金额" contenteditable="true"></span></td>
            <td colspan="4" ><span>加成系数 Plus: </span><br /><span >10</span> </td>
        </tr>
        
       	<tr class="tc" style="    line-height: 18px;">
            <td colspan="3"><span>唛头</span><br /><span>Marks & No.</span> </td>
            <td colspan="3"><span>包装及数量</span><br /><span>Packing & Quantity</span> </td>
            <td colspan="3"><span>保险货物名称</span><br /><span>Description of Goods</span> </td>
            <td colspan="3"><span>保险金额</span><br /><span>Amount Insured</span> </td>
        </tr>
        <tr class="tc">
            <td colspan="3"><p contenteditable="true" class="ShippingMarkNO addDataNull placeholderDom " placeholder="唛头号" style="display:block;text-decoration: underline;"></p><p contenteditable="true" class="ShippingMarkADD addDataNull placeholderDom" placeholder="唛头地址" ></p></td>
            <td colspan="3">
           		<!--  <p contenteditable="true" class="PackingNumber addDataNull placeholderDom " placeholder="包装及数量" ></p> -->
           		 <input type="text" name="PackingNumber" value="" style="position:absolute;width:120px;"  id="PackingNumber_inout" class="PackingNumber addinputNull">
			  	<span>
					<select onchange="document.getElementById('PackingNumber_inout').value=this.options[this.selectedIndex].text" class="PackingNumberSel" style="width: 140px;">
						<option value="No" selected="selected">--请选择--</option>
						<option value="GERMANY">CARTONS & WOODERN PACKAGES</option>
						<option value="USA" >CARTONS PACKAGES</option>
						<option value="USA" >WOODERN CASE  PACKAGES</option>
					</select>
				  </span>
            </td>
            <!-- <td colspan="3" class="DescriptionNameTd" style="line-height:17px;height: 150px;"></td> -->
            <td colspan="3" class="DescriptionNameTd" style="line-height:17px;height: 144px;text-align: center;padding-top:6px;"><pre style='width: 100%;height: 100%;outline:none;font-family: "Arial","microsoft yahei";' contenteditable="true"></pre></td>
            <td colspan="3"><p contenteditable="true" class="InsuranceUSD addDataNull placeholderDom" placeholder="金额" ></p></td>
        </tr>
        
        <tr>
            <td colspan="4" style=" line-height: 18px;"><span>开航日期Sailing Date:</span><br /><span class="placeholderDom StartDate   addDataNull" placeholder="2018-01-01" contenteditable="true"></span> </td>
            <td colspan="4" style=" line-height: 18px;"><span>运输工具(海运请注明船名及航次)</span><br /><span>Conveyance (Pls  sea):</span><br /><span>航空   BY AIR AND TRUCK</span><br /><span class="placeholderDom TransportTool  addDataNull" placeholder="运输工具" contenteditable="true"></span></td>
            <td colspan="4" style=" line-height: 18px;"><span>如涉及转运需注明转载工具</span><br /><span >If transhipment: </span><br /><span class="placeholderDom TransUtil  addDataNull" placeholder="转载工具" contenteditable="true"></span></td>
        </tr>
        
         <tr >
            <td colspan="3"><span>自From: </span><br />
           		<!--  <span class="placeholderDom Inchoat  addDataNull" placeholder="From" contenteditable="true"></span> -->
           		 <input type="text" name="Inchoat" value="" style="position:absolute;width:85px;"  id="From_inout" class="Inchoat addinputNull">
			  	<span>
					<select onchange="document.getElementById('From_inout').value=this.options[this.selectedIndex].text" class="InchoatSel" style="width: 105px;">
						<option value="No" selected="selected">--请选择--</option>
						<option value="GERMANY">GERMANY</option>
						<option value="USA" >USA</option>
						<option value="USA" >HONGKONG</option>
					</select>
				  </span>
             </td>
            <td colspan="3"><span>经Via: </span><br /><span class="placeholderDom Pass  addDataNull" placeholder="经Via" contenteditable="true"></span></td>
            <td colspan="3"><span>至目的港To Port: </span><br />
            	<!-- <span class="placeholderDom ToPort  addDataNull" placeholder="目的港" contenteditable="true"></span> -->
            	<input type="text" name="ToPort" value="" style="position:absolute;width:90px;"  id="ToPort_inout" class="ToPort addinputNull">
			  	<span>
					<select onchange="document.getElementById('ToPort_inout').value=this.options[this.selectedIndex].text" class="ToPortSel" style="width: 110px;">
						<option value="No" selected="selected">--请选择--</option>
						<option value="GERMANY">HONGKONG</option>
						<option value="USA" >XIAMEN</option>
						<option value="USA" >SUZHOU</option>
						<option value="GERMANY">SHENZHEN</option>
						<option value="USA" >WUHAN</option>
						<option value="USA" >GUANGZHOU</option>
						<option value="USA" >BEIJING</option>
					</select>
				  </span>
            </td>
            <td colspan="3"><span>最终目的地 Final Destination: </span><br /><span class="placeholderDom Destination  addDataNull" placeholder="Destination" contenteditable="true"></span></td>
        </tr>
        <tr>
            <td colspan="8"><span>赔款偿付地点Claims Payable At:</span><br />
            	<!-- <span class="placeholderDom PayableAt  addDataNull" placeholder="赔款偿付地点" contenteditable="true"></span>  -->
            	<input type="text" name="PayableAt" value="" style="position:absolute;width:90px;"  id="PayableAt_inout" class="PayableAt addinputNull">
			  	<span>
					<select onchange="document.getElementById('PayableAt_inout').value=this.options[this.selectedIndex].text" class="PayableAtSel" style="width: 110px;">
						<option value="No" selected="selected">--请选择--</option>
						<option value="GERMANY">HONGKONG</option>
						<option value="USA" >XIAMEN</option>
						<option value="USA" >SUZHOU</option>
						<option value="GERMANY">SHENZHEN</option>
						<option value="USA" >WUHAN</option>
						<option value="USA" >GUANGZHOU</option>
						<option value="USA" >BEIJING</option>
					</select>
				  </span>
            </td>
            <td colspan="4"><span>赔付币种 Indemnity in:</span><br />
            <!-- 	<span class="placeholderDom Indemnity  addDataNull" placeholder="赔付币种" contenteditable="true"></span>  -->
	            <input type="text" name="Indemnity" value="" style="position:absolute;width:90px;"  id="Indemnity_inout" class="Indemnity addinputNull">
				  <span>
					<select onchange="document.getElementById('Indemnity_inout').value=this.options[this.selectedIndex].text" class="IndemnitySel" style="width: 110px;">
						<option value="No" selected="selected">--请选择--</option>
						<option value="GERMANY">USD</option>
						<option value="USA" >RMB</option>
					</select>
				  </span>
            </td>
        </tr>
         <tr>
            <td colspan="12">
                <pre>请如实告知下列情况(如“是”在[  ]中打“ √ ”，可多项选择):</pre>
                <pre>1. 货物种类      ：  散装货[ √ ]    裸装货 [  ]        二手设备／旧货物[  ]   舱面货[  ]    危险货物[  ]    纸箱包装[ √ ]
    Goods            Bulk Cargo   Cargo without package  Secondhand Cargo      Deck Cargo     Dangerous Cargo</pre>
                <pre>2. 集装箱货物    ：  普通[ √ ]        开顶[  ]          框架[  ]         平板[  ]       冷藏[  ]
    Container        Ordinary          Open              Frame            Flat           Refrigerator </pre>
                <pre>3. 特殊运输要求  ：  防震[  ]        防倾斜[ √ ]        防尘[  ]
  Special transit    Shake-proof     Lean-proof          Dust-proof
  requirement          	</pre>
				<pre>4. 船舶资料     :   船籍____    船龄____     船级____          吨位 ____    船舶安全证书 [  ]
  Vessel info.      Registry    Age          Classification    Tonnage      ISM coded</pre>
                <pre>5. 其他   :  是否是进口转运最后一程内陆运输[  ]　            是否有签署放弃或减轻向承运人追偿的运输合同[  ]
  Others  Inland delivery after arrival at the destination port[  ]  Waive or mitigate the subrogation to the forwarder[  ]</pre>
            </td>
        </tr>
        <tr>
            <td colspan="8" rowspan="2">
                <pre style="line-height: 20px;">投保险别Please Indicate The Conditions &/or Special Coverage:
[  ] 海洋运输货物保险条款之平安险/ Ocean Marine Cargo Clauses (F.P.A.) 
[  ] 海洋运输货物保险条款之水渍险/ Ocean Marine Cargo Clauses (W.A.) 
[  ] 海洋运输货物保险条款之一切险/ Ocean Marine Cargo Clauses (All Risks) 
[  ] 协会货运条款(C)- 1/1/82 / Institute Cargo Clauses (C)-1/1/82
[  ] 协会货运条款(B)- 1/1/82 / Institute Cargo Clauses (B)-1/1/82 
[  ] 协会货运条款(A)- 1/1/82 / Institute Cargo Clauses (A)-1/1/82

其他others
条款:
</pre>
                <pre style="line-height: 12px;"  class="c7 AirTog">《航空运输货物保险条款》之航空一切险，附加航空运输货物战争险 </pre>
<pre style="line-height: 20px;"  class="c7">除外条款
1 锈蚀、氧化、变色除外条款：不承保任何原因所致的锈蚀、氧化、褪色。 
2 电子设备功能紊乱条款：不承保任何原因所致的机械、电器、电子设备功能紊乱。 
3 神秘失踪除外条款：不承保无明显盗窃痕迹、无明确原因及无确切责任方的货物丢
失风险。 
4开箱后损失除外条款：所保货物运输途中若未发生保险责任范围内的事故和/或抵
达目的地后外包装完好无损，则开箱后发现的货损为除外责任。 
5超限运输除外条款：不承担保险货物在运输工具超限（“超限”按照国家有关法律法
规、规章、规范性文件等规定认定）运输行为下所遭受的损失赔偿责任。 
6无人看管货物除外条款：不承保车辆无人看管下之货物。

</pre>

            </td>
             <td colspan="4" style="width: 55%;vertical-align: baseline; ">
                <p><span>免赔 Deductible:</span><br /><pre class="c7">盗抢损失每次事故绝对免赔额为人民币3000元或损失金
额的10%，以高者为准；其他事故绝对免赔额为人民币
2000元或损失金额的10%，以高者为准。</pre></p>

            </td>
        </tr>
        <!--<tr>-->
		<tr>
			
			<td colspan="12" style="vertical-align: baseline;line-heigt:25px;">
				<p><span>信用证条款或特别声明</span><br /><span>L/C Terms or Special Remarks</span></p>
			</td>
       </tr>
       <tr>
			<td colspan="12">
				<pre style="margin-top: 6px;">特别约定 Special Agreement：</pre>				
				<pre class="c7">1 保险责任至目的地仓库自动终止； 
2 在被保险人不是实际承运人的情况下，被保险人必须将有关业务委托给有资质的并具备合格驾驶证、行驶证及营运证的货运代理
公司或承运公司运输，否则保险人不负责赔偿造成的任何损失、费用及责任。

</pre>				
			</td>
       </tr>
       <tr>
			<td colspan="12" style="position: relative;">
				<pre style="margin-top: 6px;font-weight: bold;line-height: 18px;">投保人声明Applicant Declaration：	
	上述各项内容填写属实，如非本投保人亲笔填写亦属于在本投保人授权下的行为。本人已收到并详细阅读投保险种对应的贵
公司保险条款，尤其是黑体字部分的条款内容；并对保险公司就保险条款内容，特别是免除保险公司责任的条款的提示和说明完全理
解，没有异议，申请投保。
</pre>				
			<p style="margin-top: 6px;font-weight: bold;line-height: 18px;text-decoration: underline;text-indent: 50px;">I / We declare the information given above to be correct. The above information was written by me or  sb. 
			fully authorized by me.  I / We fully understand the terms, conditions and clauses especially the bold type and 
			exclusions without objection, and apply for the insurance.
			
			</p>			
			<p style=" margin-left: 40%;" ><span> 投保人签名（盖章）：</span><img src="image/IMG_1.jpg" style="margin: -17px 0 -40px 56px;"></p>
			<p style=" margin-left: 40%;" ><span> Applicant's Signature and stamp	</p>
	        <p style="margin-left: 40%;" class="ApplicantDate ">日期Date：
	        <span contenteditable="true" class="ApplicantYear">2016</span> &nbsp;&nbsp;&nbsp;&nbsp; 年/Y &nbsp;&nbsp;<span contenteditable="true" class="ApplicantMonth">8</span>&nbsp;&nbsp;&nbsp;&nbsp;  月/M&nbsp;&nbsp; <span contenteditable="true" class="ApplicantDay">22</span>
	          &nbsp;&nbsp;&nbsp;&nbsp;  日/D </p>
			</td>
       </tr>
       <tr>
            <td colspan="12">
                <p  style="font-weight: bold;text-align: center;">以下内容为保险公司人员填写 The following content, for insurer Filling Only</p>
            </td>
        </tr>
       <tr>
            <td colspan="5">
                <p  >业务员及联系电话 Producer & Telephone</p>
            </td>
            <td colspan="7">
                <p  >　宝安营业三部  陈婉姗</p>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <p >承保费率 Rate</p>
            </td>
            <td colspan="4">
                <p  >0.035%</p>
            </td>
            <td colspan="2">
                <p >保险费Premium</p>
            </td>
            <td colspan="4">
                <p ></p>
            </td>
        </tr>
       <tr>
            <td colspan="12">
                <p class="m30b" style="margin-bottom: 40px;">核保人意见Underwriter’s opinion：</p>
                <p style="display: inline-block; margin-left: 36%;" class="lf"><span>核保人签章Check by：　</span></p>
                <p style="display: inline-block;" class="rt"> 日期Date：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年/Y
                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月/M&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日/D
                </p>
            </td>
        </tr>
    </table>
</div>
 			 <div class="Sidebar_Price">
				<div class="DomesticParcel isHover" id="DomesticParcel">国内货物运输险投保单（邮包）</div>
				<div class="DomesticAir" id="DomesticAir">国内货物运输险投保单（陆运）</div>
				<div class="InOutParcel" id="InOutParcel">进出口货物运输保险投保单（邮包）</div>
				<div class="InOutAir" id="InOutAir">进出口货物运输保险投保单（航空一切险）</div>
			</div>
</div>
			<input type="button" value="发送" class="bToggle noteditDom SendEmail" id="sendEmail" style="position:fixed;z-index:11;top: 200px; left: 75%;width: 92px;height: 30px;font-size: 19px;">
			<input type="button" value="提交" class="bToggle" id="submit_n" style="position:fixed;z-index:11;top: 150px; left: 75%;width: 92px;height: 30px;font-size: 19px;">
			 <input type="button" value="提交" class="bToggle" id="Add_submit" style="position:fixed;z-index:11;top: 150px; left: 75%;width: 92px;height: 30px;font-size: 19px;">
			 <!-- <input type="button" value="添加货物" class="bToggle" id="addItem" style="position:absolute;z-index:11;top: 200px; left: 75%;width: 92px;height: 30px;font-size: 19px;"> -->
			<input type="button" value="导出PDF" class="bToggle noteditDom" id="exportPDF1" style="position:fixed;z-index:11;top: 250px; left: 75%;width: 92px;height: 30px;font-size: 19px;">
			<input type="button" value="关闭" class="bToggle " id="contract_close1" style="position: fixed;z-index: 11;top: 100px; left: 75%;width: 92px;height: 30px;font-size: 19px;">
	</div>
</body>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/msgbox.js"></script>
<!-- <script src="js/msgbox_unload.js"></script> -->
<script src="js/insurance.js"></script>
<script src="js/FileSaver.js"></script>
<script src="js/jquery.wordexport.js"></script>
<script type="text/javascript" src="js/html2canvas.js"></script>  
<script type="text/javascript" src="js/jsPdf.debug.js"></script>
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
/*点击添加按钮显示添加模板  */
$(".addData").click(function(){
	var mydate = new Date();
	   $(".ApplicantYear").text(mydate.getFullYear() )
	   $(".ApplicantMonth").text((mydate.getMonth()+1)<10 ? "0"+(mydate.getMonth()+1) :(mydate.getMonth()+1))
	    $(".ApplicantDay").text((mydate.getDate())<10 ? "0"+(mydate.getDate()) :(mydate.getDate()))
	$(".hidePdf .editDom").show();
	$(".hidePdf .noteditDom").hide();
	//提交按钮
	$(".hidePdf #submit_n").hide();
	$(".hidePdf #Add_submit").show();
	$(".Sidebar_Price div").css("pointerEvents","auto");
	/* $("#table_Proposal").attr("value",ID); */
	$('.hidePdf .addDataNull').text("");
	$('.hidePdf .addinputNull').val("");
	$('.hidePdf .DescriptionNameTd pre').empty();
	 $('.MailBar_cover_color').show();
	 $('.hidePdf').show(); 
	 $(".MailBar_cover_color").css("height",$("#view").height()+30);
	 $("#Add_submit").css("pointerEvents","auto");
});

$(".Sidebar_Price div").click(function(){
	$(this).addClass("isHover").siblings().removeClass("isHover");
	var currentId = $(this).attr("id");
	if(currentId == "DomesticParcel"){
		$("#table_Proposal").show();
		$("#table_Proposal_Air").hide();
		$(".hidePdf #table_Proposal .togParcel").text("邮包");
	}
	else if(currentId == "DomesticAir"){
		$("#table_Proposal").show();
		$("#table_Proposal_Air").hide();
		$(".hidePdf #table_Proposal .togParcel").text("陆运");
	}
	else if(currentId == "InOutParcel"){
		$("#table_Proposal_Air").show();
		$("#table_Proposal").hide();
		$(".hidePdf #table_Proposal_Air .AirTog").text("《邮包一切险》、附加战争险（邮包）PICC");
		
	}
	else{
		$("#table_Proposal_Air").show();
		$("#table_Proposal").hide();
		$(".hidePdf #table_Proposal_Air .AirTog").text("《航空运输货物保险条款》之航空一切险，附加航空运输货物战争险 ");
	}
})

/*********************添加运单信息   提交************************/
/* ************************模板页面提交*************************** */

 $(document).on("click","#submit_n,#Add_submit",function(){
	
	var InsuranceType = $(".Sidebar_Price .isHover").attr("ID");
	var InfoID = [];
	if(InsuranceType == "DomesticParcel" || InsuranceType =="DomesticAir"){
		var InvoiceNO=$('#table_Proposal .InvoiceNO').eq(0).text();
		var ContractNO=$('#table_Proposal .ContractNO').eq(0).text();
		var GoodsNO=$('#table_Proposal .GoodsNO').eq(0).text();
		var DCNO=$('#table_Proposal .DCNO').eq(0).text();
		var StartDate=$('#table_Proposal .StartDate').eq(0).text();
		var Inchoat=$('#table_Proposal .Inchoat').eq(0).text();
		var Pass=$('#table_Proposal .Pass').eq(0).text();
		var Destination=$('#table_Proposal .Destination').eq(0).text();
		var PackingNumber=$('#table_Proposal .PackingNumber').eq(0).val();
		var ApplicantDate=$('#table_Proposal .ApplicantYear').eq(0).text()+"-"+$('#table_Proposal .ApplicantMonth').eq(0).text()+"-"+$('#table_Proposal .ApplicantDay').eq(0).text();
		var ShippingMark=$('#table_Proposal .ShippingMarkNO').eq(0).text()+"&&"+$('#table_Proposal .ShippingMarkADD').eq(0).text();
		var InvoiceAmount=$('#table_Proposal .InvoiceAmount').eq(0).text().trim();
		var Currency=$('#table_Proposal .Currency').eq(0).text(); //币种
		
		//新增字段 
		var InsuredName="苏州伊欧陆系统集成有限公司";
		var PayableAt="NA";
		var Indemnity="NA";
		var ToPort="NA";
		var TransUtil="NA";
		var BLNO="NA";
		var Transport="NA"; //币种
	    var Model=[];
		if($("#table_Proposal .DescriptionNameTd pre").html()!=""){
			sizeExist = "yes";
			//for(var i = 0 ; i < $("#table_Proposal .DescriptionNameTd .DescriptionName").length ; i++){
				Model.push( $("#table_Proposal .DescriptionNameTd pre").html());
				InfoID.push($("#table_Proposal .DescriptionNameTd").attr("value")); 
			//}
		}
		else{
			sizeExist = "no"
		}
	}
	else{
		var InvoiceNO=$('#table_Proposal_Air .InvoiceNO').text();
		var ContractNO=$('#table_Proposal_Air .ContractNO').text();
		var GoodsNO=$('#table_Proposal_Air .GoodsNO').text();
		var DCNO=$('#table_Proposal_Air .DCNO').text();
		var StartDate=$('#table_Proposal_Air .StartDate').text();
		var Inchoat=$('#table_Proposal_Air .Inchoat').val();
		var Pass=$('#table_Proposal_Air .Pass').text();
		var Destination=$('#table_Proposal_Air .Destination').text();
		var PackingNumber=$('#table_Proposal_Air .PackingNumber').val();
		var ApplicantDate=$('#table_Proposal_Air .ApplicantYear').text()+"-"+$('#table_Proposal_Air .ApplicantMonth').text()+"-"+$('#table_Proposal_Air .ApplicantDay').text();
		var ShippingMark=$('#table_Proposal_Air .ShippingMarkNO').text()+"&&"+$('#table_Proposal_Air .ShippingMarkADD').text();
		var InvoiceAmount=$('#table_Proposal_Air .InvoiceAmount').text().trim();
		var Currency=$('#table_Proposal_Air .Currency').text(); //币种
		var Transport=$('#table_Proposal_Air .TransportTool').eq(0).text(); //币种
		//新增字段 
		var InsuredName=$('#table_Proposal_Air .InsuredName').val();
		var PayableAt=$('#table_Proposal_Air .PayableAt').val();
		var Indemnity=$('#table_Proposal_Air .Indemnity').val();
		var ToPort=$('#table_Proposal_Air .ToPort').val();
		var TransUtil=$('#table_Proposal_Air .TransUtil').text();
		var BLNO=$('#table_Proposal_Air .BLNO').text();
		var Model=[];
		if($("#table_Proposal_Air .DescriptionNameTd pre").html()!=""){
			sizeExist = "yes";
			//for(var i = 0 ; i < $("#table_Proposal .DescriptionNameTd .DescriptionName").length ; i++){
				Model.push( $("#table_Proposal_Air .DescriptionNameTd pre").html());
				 InfoID.push($("#table_Proposal_Air .DescriptionNameTd").attr("value")); 
			//}
		}
		else{
			sizeExist = "no"
		}
	}
	
	// 表单验证
	if(InvoiceAmount === null || InvoiceAmount === undefined || InvoiceAmount == ""){
		$.MsgBox_Unload.Alert("发票金额提示", "未填！请在币种后面一格填写");
		return false;
	}

	//8个固定字段 
	var AdditionFactor = 1 ;
	var Address = 1 ;
	var Applicant = 1 ;
	var Contacts = 1 ;
	var ContactsTel = 1 ;
	var ShipFlight = 1 ;
	var ShippingType = 1 ;
	var ZipCode = 1 ;
	if($(this).attr("ID") == "Add_submit"){   //添加中的提交
		var currentURL = "ProposalAdd";
		
		var data = {
	    	isExist : sizeExist,
        	InvoiceNO :InvoiceNO,
        	ContractNO :ContractNO,
        	GoodsNO :GoodsNO,
        	DCNO :DCNO,
        	StartDate : StartDate,
        	InvoiceAmount : InvoiceAmount,
        	Currency : Currency,
        	Inchoat : Inchoat,
        	Pass : Pass,
        	Destination : Destination,
        	ShippingMark : ShippingMark,
        	PackingNumber : PackingNumber,
        	ApplicantDate : ApplicantDate,
        	Model : Model,
        	AdditionFactor : AdditionFactor,
        	Address :Address,
        	Applicant :Applicant,
        	Contacts :Contacts,
        	ShipFlight :ShipFlight,
        	ShippingType : ShippingType,
        	ZipCode : ZipCode,
        	ContactsTel : ContactsTel,
    		InsuredName: InsuredName,
    		PayableAt: PayableAt,
    		Indemnity: Indemnity,
    		ToPort: ToPort,
    		TransUtil: TransUtil,
    		BLNO: BLNO,
    		InsuranceType : InsuranceType,
    		Transport :Transport
		}
		var alertMsg = "添加成功！"
	}
	else{
		var ID=  $(".hidePdf").attr("value");
		var currentURL = "ModifyProposal";
		var data = {
				ID : ID,
				InfoID :InfoID,
		    	isExist : sizeExist,
	        	InvoiceNO :InvoiceNO,
	        	ContractNO :ContractNO,
	        	GoodsNO :GoodsNO,
	        	DCNO :DCNO,
	        	StartDate : StartDate,
	        	InvoiceAmount : InvoiceAmount,
	        	Currency : Currency,
	        	Inchoat : Inchoat,
	        	Pass : Pass,
	        	Destination : Destination,
	        	ShippingMark : ShippingMark,
	        	PackingNumber : PackingNumber,
	        	ApplicantDate : ApplicantDate,
	        	Model : Model,
	        	AdditionFactor : AdditionFactor,
	        	Address :Address,
	        	Applicant :Applicant,
	        	Contacts :Contacts,
	        	ShipFlight :ShipFlight,
	        	ShippingType : ShippingType,
	        	ZipCode : ZipCode,
	        	ContactsTel : ContactsTel,
	    		InsuredName: InsuredName,
	    		PayableAt: PayableAt,
	    		Indemnity: Indemnity,
	    		ToPort: ToPort,
	    		TransUtil: TransUtil,
	    		BLNO: BLNO,
	    		InsuranceType : InsuranceType,
	    		Transport :Transport
			}
		var alertMsg = "修改成功！"
	}
	    $.ajax({
        type : 'get',
        url : currentURL,
        data : data,	
        dataType : 'json',
        success : function (data) {
            $.MsgBox.Alert('提示',alertMsg);
            $('.MailBar_cover_color').hide();
            $('.contract_add').hide();
        },
        error : function () {
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });  
	
}); 


$(document).on("click",".hidePdf #addItem",function(){
	var str = '<p contenteditable="true" class="DescriptionName placeholderDom" style="display:block;" placeholder="货物名称" ></p>';
	$(".hidePdf .DescriptionNameTd").append(str);
})

   

$(document).on("click",".contract_add .DelThisTr",function(){
	$(this).parent().parent().remove();
})  


$(document).on("click",".contract_update .DelThisTr",function(){
	var InfoID = $(this).parent().parent().attr("value");
	$(this).parent().parent().remove();
	$.ajax({
        type : 'get',
        url : 'ProposalGoodsDelete',
        data : {
        	InfoID : InfoID
        },
        dataType : 'json',
        success : function (data) {
        	 $.MsgBox.Alert("提示", "删除成功！");
        },
        error : function () {
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
})
    
    
$(document).on("blur",".hidePdf .ContractNO",function(){
	var ContractNO = $(this).text();
	if($(".hidePdf").attr("conId")){
		console.warn(1);
		if($(".hidePdf").attr("conId") != ContractNO){
			console.warn(2);
			$.ajax({
		        type : 'get',
		        url : 'SearchContractNO',
		        data : {
		        	ContractNO : ContractNO
		        },
		        dataType : 'json',
		        success : function (data) {
		        	if(data){
		        		$.MsgBox_Unload.Alert("提示", "合同号重复！");
		        		$("#submit_n,#Add_submit").css("pointerEvents","none");
		        	}
		        	else{
		        		$("#submit_n,#Add_submit").css("pointerEvents","auto");
		        	}
		        },
		        error : function () {
		            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
		        }
		    });
		}
	}else{
		console.warn(3);
		$.ajax({
	        type : 'get',
	        url : 'SearchContractNO',
	        data : {
	        	ContractNO : ContractNO
	        },
	        dataType : 'json',
	        success : function (data) {
	        	if(data){
	        		$.MsgBox_Unload.Alert("提示", "合同号重复！");
	        		$("#submit_n,#Add_submit").css("pointerEvents","none");
	        	}
	        	else{
	        		$("#submit_n,#Add_submit").css("pointerEvents","auto");
	        	}
	        },
	        error : function () {
	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    });
	}
})

 /***********  点击序号模板显示****************/   
 $('.contract-show').click(function () {
	//提交按钮
	$(".hidePdf .editDom, .hidePdf #Add_submit").hide();
	$(".hidePdf .noteditDom, .hidePdf #submit_n, .MailBar_cover_color").show();
	    /* 获取隐藏信息 */
         var thisList = $(this).parent();
         var ID = thisList.find("td").eq(0).attr("value");
         $(".hidePdf").attr("value",ID);
         var currenDomText = thisList.find("td").eq(28).text();
         /* var currenDom = (currenDomText ==( "" ||"--") ? "DomesticParcel" :thisList.find("td").eq(28).text()); */
         if(currenDomText ==( "" ||"--")){
        	 var currenDom = "DomesticParcel";
         }
         else{
        	 var currenDom = currenDomText; 
         }
         console.log(currenDom);
 		$(".Sidebar_Price").find("."+currenDom).click();
		$(".Sidebar_Price").find("."+currenDom).siblings().css("pointerEvents","none"); 
		
		 $(".hidePdf").attr("conId",thisList.find("td").eq(8).text());
	     var InvoiceNO = thisList.find("td").eq(7).text();
	     var ContractNO = thisList.find("td").eq(8).text();
	     var GoodsNO = thisList.find("td").eq(9).text();
	     var DCNO = thisList.find("td").eq(10).text();
	     var StartDate = thisList.find("td").eq(11).text();
	     var InvoiceAmount = thisList.find("td").eq(12).text();
	     var Currency = thisList.find("td").eq(13).text();
	     var Inchoat = thisList.find("td").eq(14).text();
	     var Pass = thisList.find("td").eq(15).text();
	     var Destination = thisList.find("td").eq(16).text();
	     var ShippingMark = thisList.find("td").eq(17).text();
	     var PackingNumber = thisList.find("td").eq(19).text();
	     var ApplicantDate = thisList.find("td").eq(20).text();
	     var InsuranceUSD = Currency+""+thisList.find("td").eq(21).text();
	     var ShippingMarkNO = ShippingMark.split("&&")[0];
	     var ShippingMarkADD = ShippingMark.split("&&")[1];
	     
	     var InsuredName = thisList.find("td").eq(22).text();
	     var PayableAt = thisList.find("td").eq(23).text();
	     var Indemnity = thisList.find("td").eq(24).text();
	     var ToPort = thisList.find("td").eq(25).text();
	     var TransUtil = thisList.find("td").eq(26).text();
	     var BLNO = thisList.find("td").eq(27).text();
	     var Transport = thisList.find("td").eq(29).text();
	     
	     var ApplicantYear = ApplicantDate.split("-")[0];
         var ApplicantMonth = ApplicantDate.split("-")[1];
         var ApplicantDay = ApplicantDate.split("-")[2];
         
         if(thisList.find("td").eq(28).text() == "DomesticParcel" ||thisList.find("td").eq(28).text() == "DomesticAir" ||thisList.find("td").eq(28).text() == "--"){
        	 $("#table_Proposal .InvoiceNO").text("").text(InvoiceNO); 
    	     $("#table_Proposal .ContractNO").text("").text(ContractNO); 
    	     $("#table_Proposal .GoodsNO").text("").text(GoodsNO); 
    	     $("#table_Proposal .DCNO").text("").text(DCNO);
    	     $("#table_Proposal .StartDate").text("").text(StartDate); 
    	     $("#table_Proposal .InvoiceAmount").text("").text(InvoiceAmount); 
    	     $("#table_Proposal .Currency").text("").text(Currency); 
    	     $("#table_Proposal .Inchoat").text("").text(Inchoat); 
    	     $("#table_Proposal .Pass").text("").text(Pass); 
    	     $("#table_Proposal .Destination").text("").text(Destination);
    	     $("#table_Proposal .ShippingMarkNO").text("").text(ShippingMarkNO); 
    	     $("#table_Proposal .ShippingMarkADD").text("").text(ShippingMarkADD); 
    	     $("#table_Proposal .PackingNumber").val("").val(PackingNumber); 
    	     $("#table_Proposal .ApplicantYear").text("").text(ApplicantYear);
    	     $("#table_Proposal .ApplicantMonth").text("").text(ApplicantMonth);
    	     $("#table_Proposal .ApplicantDay").text("").text(ApplicantDay);
    	     $("#table_Proposal .InsuranceUSD").text("").text(InsuranceUSD);
    	     $.ajax({
 	 	        type : 'get',
 	 	        url : 'ProposalGoods',
 	 	        data : {
 	 	        	ID : ID,
 	 	        },
 	 	        dataType : 'json',
 	 	        success : function (data) {
 	 	        	console.log(data)
 	 	        	 $("#table_Proposal .DescriptionNameTd pre").html("").html(data[1].Model);
 	 	        	 $("#table_Proposal .DescriptionNameTd").attr("value",data[1].ID)
 	 	        	/*    	 $($.parseHTML("#table_Proposal .DescriptionNameTd pre")).html("").html(data[1].Model);
  	 	        	$($.parseHTML("#table_Proposal .DescriptionNameTd pre")).html("").attr("value",data[1].ID) */
 	 	        },
 	 	        error : function () {
 	 	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！"); 
 	 	        }
 	 	    })  
         }
         else{
        	 $("#table_Proposal_Air .InvoiceNO").text("").text(InvoiceNO); 
    	     $("#table_Proposal_Air .ContractNO").text("").text(ContractNO); 
    	     $("#table_Proposal_Air .GoodsNO").text("").text(GoodsNO); 
    	     $("#table_Proposal_Air .DCNO").text("").text(DCNO);
    	     $("#table_Proposal_Air .StartDate").text("").text(StartDate); 
    	     $("#table_Proposal_Air .InvoiceAmount").text("").text(InvoiceAmount); 
    	     $("#table_Proposal_Air .Currency").text("").text(Currency); 
    	     $("#table_Proposal_Air .Inchoat").val("").val(Inchoat); 
    	     $("#table_Proposal_Air .Pass").text("").text(Pass); 
    	     $("#table_Proposal_Air .Destination").text("").text(Destination);
    	     $("#table_Proposal_Air .ShippingMarkNO").text("").text(ShippingMarkNO); 
    	     $("#table_Proposal_Air .ShippingMarkADD").text("").text(ShippingMarkADD); 
    	     $("#table_Proposal_Air .PackingNumber").val("").val(PackingNumber); 
    	     $("#table_Proposal_Air .ApplicantYear").text("").text(ApplicantYear);
    	     $("#table_Proposal_Air .ApplicantMonth").text("").text(ApplicantMonth);
    	     $("#table_Proposal_Air .ApplicantDay").text("").text(ApplicantDay);
    	     $("#table_Proposal_Air .InsuranceUSD").text("").text(InsuranceUSD);
    	     
    	     $("#table_Proposal_Air .InsuredName").val("").val(InsuredName); 
    	     $("#table_Proposal_Air .PayableAt").val("").val(PayableAt);
    	     $("#table_Proposal_Air .Indemnity").val("").val(Indemnity);
    	     $("#table_Proposal_Air .ToPort").val("").val(ToPort);
    	     $("#table_Proposal_Air .TransUtil").text("").text(TransUtil);
    	     $("#table_Proposal_Air .BLNO").text("").text(BLNO);
    	     $("#table_Proposal_Air .TransportTool").text("").text(Transport);
    	     $.ajax({
  	 	        type : 'get',
  	 	        url : 'ProposalGoods',
  	 	        data : {
  	 	        	ID : ID,
  	 	        },
  	 	        dataType : 'json',
  	 	        success : function (data) {
  	 	        	 $("#table_Proposal_Air .DescriptionNameTd pre").html("").html(data[1].Model);
 	 	        	 $("#table_Proposal_Air .DescriptionNameTd").attr("value",data[1].ID);
  	 	        },
  	 	        error : function () {
  	 	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！"); 
  	 	        }
  	 	    })  
         }
	   
    $('.hidePdf').show(); 
 	$(".MailBar_cover_color").css("height",$("#view").height()+30);
 	$("#submit_n").css("pointerEvents","auto");
});

$('#contract_close1').click(function () {
    $('.MailBar_cover_color').hide();
    $('.hidePdf').hide();
});

//点击添加
function AddContract() {
	$(".ModelTr").not(".FirstModelTr").remove();
    $('.MailBar_cover_color').show();
    $('.contract_add').show();
};


//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function () {
    window.location.reload();
});

//点击关闭
$('#contract_close1').click(function () {
    $('.MailBar_cover_color').hide();
    $('.hidePdf').hide();
});

$("#exportPDF1").click(function(){
	var ID = $(".hidePdf").attr("value");
  	$.ajax({
        type : 'get',
        url : 'DownloadInsurancePolicy',
        data : {
        	ID : ID,
        },
        dataType : 'json',
        success : function (data) {
        	window.location.href = data;
        },
        error : function () {
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！"); 
        }
    })   
})


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
	/* var mydate = new Date();
	   $(".ApplicantYear").text(mydate.getFullYear() )
	   $(".ApplicantMonth").text((mydate.getMonth()+1)<10 ? "0"+(mydate.getMonth()+1) :(mydate.getMonth()+1))
	    $(".ApplicantDay").text((mydate.getDate())<10 ? "0"+(mydate.getDate()) :(mydate.getDate())) */
});
	function fmoney(s, n) //s:传入的float数字 ，n:希望返回小数点几位 
	{ 
		n = n > 0 && n <= 20 ? n : 2; 
		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
		var l = s.split(".")[0].split("").reverse(), 
		r = s.split(".")[1]; 
		t = ""; 
		for(i = 0; i < l.length; i ++ ) 
		{ 
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
		} 
		return t.split("").reverse().join("") + "." + r; 
	} 
	function outputmoney(number) {
		number = number.replace(/\,/g, "");
		if(isNaN(number) || number == "")return "";
		number = Math.round(number * 100) / 100;
		    if (number < 0)
		        return '-' + outputdollars(Math.floor(Math.abs(number) - 0) + '') + outputcents(Math.abs(number) - 0);
		    else
		        return outputdollars(Math.floor(number - 0) + '') + outputcents(number - 0);
		} 
	//格式化金额
	function outputdollars(number) {
	    if (number.length <= 3)
	        return (number == '' ? '0' : number);
	    else {
	        var mod = number.length % 3;
	        var output = (mod == 0 ? '' : (number.substring(0, mod)));
	        for (i = 0; i < Math.floor(number.length / 3); i++) {
	            if ((mod == 0) && (i == 0))
	                output += number.substring(mod + 3 * i, mod + 3 * i + 3);
	            else
	                output += ',' + number.substring(mod + 3 * i, mod + 3 * i + 3);
	        }
	        return (output);
	    }
	}
	function outputcents(amount) {
	    amount = Math.round(((amount) - Math.floor(amount)) * 100);
	    return (amount < 10 ? '.0' + amount : '.' + amount);
	}
	//var mailBlob = "";
	var sender = '<%=request.getSession().getAttribute("email")%>';
	/*发送邮件  */
	$(".SendEmail").click(function(){
		var ID =  $(".hidePdf").attr("value");
		$("#SendEmail_submit").attr("value1",ID);
		var themeText = "EOULU:"+$(".isHover").text();
		$(".theme").text(themeText);
			var EmailCon = "附件："+$(".isHover").text()+"是一票货的投保单，请查收。";
			$(".EmailCon").text(EmailCon);
		$(".CC").text("liuyanan@eoulu.com；zhudanni@eoulu.com；jiangyaping@eoulu.com；");
		
		$(".SendEmailBox").show();
		$(".SendEmailBox .Sender").text(sender);
		
		/*  $(".hidePdf .editDom").hide();
		 $(".hidePdf .noteditDom").show(); */
		    $('#cover_color').show();
		 $("#cover_color").css("height",$("#view").height()+30);
		 //	mailBlob =  $("#view").wordExport("Proposal","Preview"); 
	 	 
	})
	$(document).on("click",".SendEmailBox #SendEmail_submit",function(){
		var EmailCont = $(".SendEmailBox .EmailContent").html(); 
		console.log(EmailCont)
	   var formData = new FormData();
	  // formData.append('Proposal', mailBlob,'Proposal.doc');
	   formData.append('Consignee', $("#search_text").text());
	   formData.append('CopyList', $(".CC").text());
	   formData.append('Subject', $(".theme").text());
	   formData.append('Content', EmailCont);
	   formData.append('Password', $(".EmailPwd").val());
	   formData.append('ID', $("#SendEmail_submit").attr("value1"));
	   $.ajax({
		    url: 'ProposalEmail',
		    type: 'POST',
		    cache: false,
		    data: formData,
		    processData: false,
		    contentType: false,
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
			success: function(data) {
				console.log(data == "true");
	        	if(data == "true"){
	        		$.MsgBox.Alert("提示", "发送成功！"); 
	        	}else if(data == "false"){
	        		$.MsgBox_Unload.Alert("提示", "发送失败！请检查填写内容");
	        	}else{
	        		$.MsgBox_Unload.Alert("提示", data);
	        	}
	        },
	        error: function () {
	        	 $.MsgBox_Unload.Alert("提示", "网络繁忙！发送失败！"); 
	        }
		});
	
	})

	var test_list = [];
  	   $.ajax({
	        type : 'get',
	        url : 'GetAllEmail',
	        dataType : 'json',
	        success : function (data) {
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
    	$("#auto_div").css("top",parseFloat($("#search_text").css("height"))+163+"px");
    	if($(this).text() !=""){
    		AutoComplete("auto_div", "search_text", test_list);  
    	}
    }); 
 	 $("#CC").keyup(function () {  
 		$("#auto_div").css("top",parseFloat($("#CC").css("height"))+163+"px");
    	if($(this).text() !=""){
    		AutoComplete("CC_div", "CC", test_list);  
    	}
    }); 
 	   $("#SendEmail_cancel,.SendEmailBox_close").click(function(){
 		   	 $('#cover_color').hide();
 		        $('.SendEmailBox').hide();
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
        if(old_value.indexOf("；") >0){
        	 old_value = old_value.split("；").pop();
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
                    if($("#" + search).text().indexOf("；")>0){
                    	var searchText = $("#" + search).text().split("；");
                    	searchText.pop();
                    	searchText = searchText.join("；");
                   		 $("#" + search).text(searchText+"；"+comText+"；");  
                    }
                    else{
                    	$("#" + search).text(comText+"；");  
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
    function ChangeCancel() {
        $('#search').val('cancel');
        $('input[name="searchContent1"]').val('');
        $('input[name="searchContent2"]').val('');
        $('#top_text_from').attr("action","Proposal");
        $('#top_text_from').submit();
    }
    function ChangeSearch() {
        $('#search').val('search');
        $('#top_text_from').attr("action","GetProposalRoute");
        $('#top_text_from').submit();
    }
</script>
</html>
