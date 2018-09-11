<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache" >
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0"> 
<title>top</title>
<!-- <link rel="stylesheet" href="css/libs/lava_lamp_demo.css" type="text/css"> -->
<link rel="stylesheet" href="css/modules/sharing/top-e70d2a18ef.min.css" type="text/css">
</head>
<body>
	<div class="eou-container">
		<div class="eou-container-l">
			<a href="Index"><img src="image/newT2.png" style="display:inline-block;width: 170px;margin: 5px 20px;"></a>
		</div>
		<div class="eou-container-m" style="visibility:hidden;opacity: 0;">
			<div class="g-nav">
			    <ul class="g-nav-ul lava_with_border">
					<c:forEach var="authoritiy" items="${authorities}">
						<c:if test="${authoritiy=='Supplier' || authoritiy=='Commodity'}"><c:set var="ziliaoku1" value="1"></c:set></c:if>

						<c:if test="${authoritiy=='Transport' || authoritiy=='StockPurchasing' || authoritiy=='Inventory' || authoritiy=='OriginFactory' || authoritiy=='Insurance' || authoritiy=='Proposal' || authoritiy=='PackingList' || authoritiy=='Supplier' || authoritiy=='Commodity'}"><c:set var="wuliubu" value="1"></c:set></c:if>

						<c:if test="${authoritiy=='Insurance' || authoritiy=='Proposal' || authoritiy=='PackingList'}"><c:set var="wendangzhizuo1" value="1"></c:set></c:if>

						<c:if test="${authoritiy=='Price' || authoritiy=='Requirement' || authoritiy=='SalesStatistics' || authoritiy=='QuotationSystem' || authoritiy=='Quality' || authoritiy=='Invoice' || authoritiy=='PackingList' || authoritiy=='Customer' || authoritiy=='HotProduct' || authoritiy=='QuantityWeight' || authoritiy=='TestReport' || authoritiy=='Fumigation' || authoritiy=='Origin' || authoritiy=='Shipment' || authoritiy=='Receiving' || authoritiy=='Acceptance' || authoritiy=='Customer' || authoritiy=='WorkReport' || authoritiy=='BiddingDocument'}"><c:set var="shangwubu" value="1"></c:set></c:if>

						<c:if test="${authoritiy=='BiddingDocument'}"><c:set var="shangwubuwendangguanli" value="1"></c:set></c:if>

						<c:if test="${authoritiy=='Quality' || authoritiy=='QuantityWeight' || authoritiy=='TestReport' || authoritiy=='QuotationSystem' || authoritiy=='Fumigation' || authoritiy=='Origin' || authoritiy=='Shipment' || authoritiy=='Receiving' || authoritiy=='Acceptance' || authoritiy=='Invoice' || authoritiy=='PackingList'}"><c:set var="wendangzhizuo2" value="1"></c:set></c:if>

						<c:if test="${authoritiy=='SalesStatistics' || authoritiy=='HotProduct'}"><c:set var="xiaoshoutongji" value="1"></c:set></c:if>
	
						<c:if test="${authoritiy=='MachineDetails' || authoritiy=='Hardware' || authoritiy=='AfterSale'}"><c:set var="biaozhunfuwu" value="1"></c:set></c:if>

						<c:if test="${authoritiy=='StandardProduct'}"><c:set var="yingyongfuwu" value="1"></c:set></c:if>
					
						<c:if test="${authoritiy=='ApplicationGallery'}"><c:set var="yingyongyanfa" value="1"></c:set></c:if>

						<c:if test="${authoritiy=='Schedule'}"><c:set var="yuangongxingcheng" value="1"></c:set></c:if>
						
						<c:if test="${authoritiy=='ServiceReport'}"><c:set var="fuwubuwendangzhizuo" value="1"></c:set></c:if>

						<c:if test="${authoritiy=='DocumentUpload'}"><c:set var="fuwubuwendangguanli" value="1"></c:set></c:if>

						<c:if test="${authoritiy=='MachineDetails' || authoritiy=='Hardware' || authoritiy=='AfterSale' || authoritiy=='Schedule' || authoritiy=='RoutineVisit' || authoritiy=='NonStandardProject' || authoritiy=='ServiceReport' || authoritiy=='StandardProduct' || authoritiy=='DocumentUpload' || authoritiy=='ApplicationGallery'}"><c:set var="fuwubu" value="1"></c:set></c:if>

						<c:if test="${authoritiy=='SoftwareDocument' || authoritiy=='SoftwareProject' || authoritiy=='CustomerInquiry' || authoritiy=='SoftwareImplementation' || authoritiy=='SoftwareProduct'}"><c:set var="ruanjianbu" value="1"></c:set></c:if>

						<c:if test="${authoritiy=='StaffInfo' || authoritiy=='LeaveApplication' || authoritiy=='Tasks' || authoritiy=='ExaminationDetails' || authoritiy=='Reimburse' || authoritiy=='PaymentRequest'}"><c:set var="renshibu" value="1"></c:set></c:if>

						<c:if test="${authoritiy=='Requirement' || authoritiy=='Keysight'}"><c:set var="xuqiutongji" value="1"></c:set></c:if>

						<c:if test="${authoritiy=='Admin' }"><c:set var="admin0" value="1"></c:set></c:if>
						
						<c:if test="${authoritiy=='Customer' || authoritiy=='OriginalQuotation'}"><c:set var="shangwubuziliaoku" value="1"></c:set></c:if>

						<c:if test="${authoritiy=='Lab'}"><c:set var="shiyanshi" value="1"></c:set></c:if>
					</c:forEach>
					
                    <c:if test="${wuliubu==1 }">
			        <li class="m-li Inventory0"><a href="Inventory">物流部</a><span></span>
			            <div class="m-nav-div0">
			                <div class="m-nav-div">
			                <c:forEach var="authoritiy" items="${authorities }">
			                	<c:if test="${authoritiy=='Transport'}">
			                    <h5><a href="Transport?ActualDelivery=no&column=DateOfSign&condition=All">物流统计</a></h5>
			                    </c:if>
			                </c:forEach>
			                <c:forEach var="authoritiy" items="${authorities }">
			                    <c:if test="${authoritiy=='StockPurchasing'}">
			                    <h5><a href="StockPurchasing?ActualDelivery=no&column=DateOfSign&condition=All">库存采购</a></h5>
			                    </c:if>
			                </c:forEach>
			                <c:forEach var="authoritiy" items="${authorities }">
			                    <c:if test="${authoritiy=='Inventory'}">
			                    <h5 class="Inventory"><a href="Inventory">库存页面</a></h5>
			                    </c:if>
			                </c:forEach>
			                <c:forEach var="authoritiy" items="${authorities }">
			                    <c:if test="${authoritiy=='OriginFactory'}">
			                    <h5 class="OriginFactory"><a href="OriginFactory" style="font-size: 13px">FORMFACTOR</a></h5>
			                    </c:if>
			                </c:forEach>
			                <c:if test="${wendangzhizuo1==1 }">
			                <div class="subTit subInsurance">
			                    <h5><a href="Insurance">文档制作</a></h5>
			                    	<c:forEach var="authoritiy" items="${authorities }">
										<c:if test="${authoritiy=='Insurance' }">
										<h6 class="Insurance"><a href="Insurance">运输指令</a></h6>
										</c:if>
										<c:if test="${authoritiy=='Proposal' }">
										<h6><a href="Proposal">运输保险</a></h6>
										</c:if>
										<c:if test="${authoritiy=='PackingList' }">
										<h6><a href="PackingList">箱单制作</a></h6>
										</c:if>
									</c:forEach>
			                </div>
			                </c:if>
			                <c:if test="${ziliaoku1==1 }">
			                <div class="subTit subSupplier">
			                    <h5><a href="Supplier">资料库</a></h5>
			                    	<c:forEach var="authoritiy" items="${authorities }">
										<c:if test="${authoritiy=='Supplier' }">
										<h6 class="Supplier"><a href="Supplier">供应商管理</a></h6>
										</c:if>
										<c:if test="${authoritiy=='Commodity' }">
			                        	<h6 class="Commodity"><a href="Commodity">商品管理</a></h6>
			                    		</c:if>
									</c:forEach>
							</div>
			                </c:if>
			                </div>
			            </div>
			        </li>
			        </c:if>

					<c:if test="${shangwubu==1}">
			        <li class="m-li QuotationSystem0"><a href="QuotationSystem">商务部</a><span></span>
			            <div class="m-nav-div0">
			                <div class="m-nav-div">
			                <c:forEach var="authoritiy" items="${authorities }">
			                	<c:if test="${authoritiy=='Price'}">
			                    <h5 class="Price"><a href="Price?ActualDelivery=no&column=DateOfSign&condition=All">合同统计</a></h5>
			                    </c:if>
		                    	<c:if test="${authoritiy=='WorkReport'}">
		                        <h5 class="WorkReport"><a href="WorkReport">工作汇报</a></h5>
		                        </c:if>
			                </c:forEach>
        	                    <c:if test="${xuqiutongji==1}">
        	                    <div class="subTit subRequirement">
        	                    <h5><a href="Requirement">需求统计</a></h5>
        							<c:forEach var="authoritiy" items="${authorities }">
        								<c:if test="${authoritiy=='Requirement' }">
        								<h6 class="Requirement"><a href="Requirement">需求统计</a></h6>
        								</c:if>
        								<c:if test="${authoritiy=='Keysight' }">
        	                        	<h6 class="Keysight"><a href="Keysight">Keysight</a></h6>
        	                    		</c:if>
        							</c:forEach>
        	                    </div>
        	                    </c:if>
			                    <c:if test="${xiaoshoutongji==1 }">
			                    <div class="subTit subSalesStatistics">
			                    <h5><a href="SalesStatistics">销售统计</a></h5>
									<c:forEach var="authoritiy" items="${authorities }">
										<c:if test="${authoritiy=='SalesStatistics' }">
										<h6 class="SalesStatistics"><a href="SalesStatistics">销售统计</a></h6>
										</c:if>
										<c:if test="${authoritiy=='HotProduct' }">
			                        	<h6 class="HotProduct"><a href="HotProduct">热销产品</a></h6>
			                    		</c:if>
									</c:forEach>
			                    </div>
			                    </c:if>
			                <c:forEach var="authoritiy" items="${authorities }">
			                    <c:if test="${authoritiy=='QuotationSystem' }">
			                    <h5 class="QuotationSystem"><a href="QuotationSystem">报价系统</a></h5>
			                    </c:if>
			                </c:forEach>
								<c:if test="${wendangzhizuo2==1}">
								<div class="subTit subQuality">
			                    <h5 class="Quality"><a href="Quality">文档制作</a></h5>
			                    	<c:forEach var="authoritiy" items="${authorities }">
										<c:if test="${authoritiy=='Quality' }">
											<h6 class="Quality"><a href="Quality">质量证明</a></h6>
										</c:if>
	                    				<c:if test="${authoritiy=='QuantityWeight' }">
	                        				<h6 class="QuantityWeight"><a href="QuantityWeight">数量重量</a></h6>
	                    				</c:if>
										<c:if test="${authoritiy=='TestReport' }">
											<h6 class="TestReport"><a href="TestReport">测试报告</a></h6>
										</c:if>
										<c:if test="${authoritiy=='Fumigation' }">
											<h6 class="Fumigation"><a href="Fumigation">熏蒸证明</a></h6>
										</c:if>
										<c:if test="${authoritiy=='Origin' }">
											<h6 class="Origin"><a href="Origin">原产地证明</a></h6>
										</c:if>
										<c:if test="${authoritiy=='Shipment' }">
											<h6 class="Shipment"><a href="Shipment">发货通知</a></h6>
										</c:if>
										<c:if test="${authoritiy=='Receiving'}">
											<h6 class="Receiving"><a href="Receiving">验收报告</a></h6>
										</c:if>
										<c:if test="${authoritiy=='Acceptance'}">
											<h6 class="Acceptance"><a href="Acceptance">FAT</a></h6>
										</c:if>
										<c:if test="${authoritiy=='Invoice' }">
											<h6 class="Invoice"><a href="Invoice">发票制作</a></h6>
										</c:if>
										<c:if test="${authoritiy=='PackingList' }">
											<h6 class="PackingList"><a href="PackingList">箱单制作</a></h6>
										</c:if>
									</c:forEach>
			                    </div>
			                    </c:if>
			                    <c:if test="${shangwubuziliaoku==1}">
			                    <div class="subTit subOriginalQuotation">
			                    <h5 class="OriginalQuotation"><a href="OriginalQuotation">资料库</a></h5>
									<c:forEach var="authoritiy" items="${authorities}">
										<c:if test="${authoritiy=='Customer'}">
										<h6><a href="Customer">客户信息表</a></h6>
										</c:if>
										<c:if test="${authoritiy=='OriginalQuotation' }">
										<h6 class="OriginalQuotation"><a href="OriginalQuotation">原厂报价单</a></h6>
										</c:if>
									</c:forEach>
			                    </div>
			                    </c:if>
    			                <c:if test="${shangwubuwendangguanli==1}">
            	                    <div class="subTit">
            	                    <h5 class="BiddingDocument"><a href="BiddingDocument">文档管理</a></h5>
            							<c:forEach var="authoritiy" items="${authorities}">
            								<c:if test="${authoritiy=='BiddingDocument'}">
            								<h6 class="BiddingDocument"><a href="BiddingDocument">招标文件</a></h6>
            								</c:if>
            							</c:forEach>
            	                    </div>
    			                </c:if>
			                </div>
			            </div>
			        </li>
					</c:if>
					
                    <c:if test="${fuwubu==1}">
			        <li class="m-li MachineDetails0"><a href="ServiceReport">服务部</a><span></span>
			        	<div class="m-nav-div0">
			                <div class="m-nav-div">
			                <c:if test="${biaozhunfuwu==1}">
        	                    <div class="subTit subHardware">
        	                    <h5 class="Hardware"><a href="Hardware">标准服务</a></h5>
        							<c:forEach var="authoritiy" items="${authorities}">
        								<c:if test="${authoritiy=='MachineDetails'}">
        								<h6 class="MachineDetails"><a href="MachineDetails">机台统计</a></h6>
        								</c:if>
        								<c:if test="${authoritiy=='Hardware'}">
        	                        	<h6 class="Hardware"><a href="Hardware">装机进展</a></h6>
        	                    		</c:if>
        	                    		<c:if test="${authoritiy=='AfterSale'}">
        	                        	<h6 class="AfterSale"><a href="AfterSale">售后维修</a></h6>
        	                    		</c:if>
        							</c:forEach>
        	                    </div>
			                </c:if>
			                <c:if test="${yingyongfuwu==1}">
        	                    <div class="subTit">
        	                    <h5 class="StandardProduct"><a href="StandardProduct">应用服务</a></h5>
        							<c:forEach var="authoritiy" items="${authorities}">
        								<c:if test="${authoritiy=='StandardProduct'}">
        								<h6 class="StandardProduct"><a href="StandardProduct">标准产品</a></h6>
        								</c:if>
        							</c:forEach>
        	                    </div>
			                </c:if>
							<c:if test="${yingyongyanfa==1}">
        	                    <div class="subTit subApplicationGallery">
        	                    <h5 class="ApplicationGallery"><a href="ApplicationGallery">应用研发</a></h5>
        							<c:forEach var="authoritiy" items="${authorities}">
        								<c:if test="${authoritiy=='ApplicationGallery'}">
        								<h6 class="ApplicationGallery"><a href="ApplicationGallery">研发图库</a></h6>
        								</c:if>
        							</c:forEach>
        	                    </div>
			                </c:if>
			                <c:if test="${yuangongxingcheng==1}">
        	                    <h5 class="Schedule"><a href="Schedule">员工行程</a></h5>
			                </c:if>
			                <c:if test="${fuwubuwendangzhizuo==1}">
        	                    <div class="subTit">
        	                    <h5 class="ServiceReport"><a href="ServiceReport">文档制作</a></h5>
        							<c:forEach var="authoritiy" items="${authorities}">
        								<c:if test="${authoritiy=='ServiceReport'}">
        								<h6 class="ServiceReport"><a href="ServiceReport">服务完成报告</a></h6>
        								</c:if>
        							</c:forEach>
        	                    </div>
			                </c:if>
			                <c:if test="${fuwubuwendangguanli==1}">
        	                    <h5 class="DocumentUpload"><a href="DocumentUpload?queryType=common&Area=south&catalog=Manual&Year=2018&Type=Cascade">文档管理</a></h5>
			                </c:if>

			                </div>
			            </div>            				
			        </li>
			        </c:if>

			        <c:if test="${ruanjianbu==1 }">
			        <li class="m-li SoftwareProject0"><a href="SoftwareProject">软件部</a><span></span>
						<div class="m-nav-div0">
					        <div class="m-nav-div" style="overflow:hidden;">
					        <c:forEach var="authoritiy" items="${authorities }">
					        	<c:if test="${authoritiy=='SoftwareDocument'}">
					            <h5 class="SoftwareDocument"><a href="SoftwareDocument">软件部文档</a></h5>
					            </c:if>
					            <c:if test="${authoritiy=='SoftwareProject'}">
					            <h5 class="SoftwareProject"><a href="SoftwareProject" style="font-size:14px;">开发项目管理</a></h5>
					            </c:if>
					            <c:if test="${authoritiy=='SoftwareProduct'}">
					            <h5 class="SoftwareProduct"><a href="SoftwareProduct" style="font-size:14px;">软件产品管理</a></h5>
					            </c:if>
					            <c:if test="${authoritiy=='CustomerInquiry'}">
					            <h5 class="CustomerInquiry"><a href="CustomerInquiry" style="font-size:14px;">客户询价记录</a></h5>
					            </c:if>
					            <c:if test="${authoritiy=='SoftwareImplementation'}">
					            <h5 class="SoftwareImplementation"><a href="SoftwareImplementation" style="font-size:14px;">软件实施管理</a></h5>
					            </c:if>
					        </c:forEach>
					        </div>
					    </div> 
			        </li>
			        </c:if>

                	<c:if test="${renshibu==1}">
			        <li class="m-li LeaveApplication0"><a href="LeaveApplication">人事部</a><span></span>
						<div class="m-nav-div0">
					        <div class="m-nav-div" style="overflow:hidden;">
					        <c:forEach var="authoritiy" items="${authorities }">
					        	<c:if test="${authoritiy=='StaffInfo'}">
					            <h5 class="StaffInfo"><a href="StaffInfo">员工信息</a></h5>
					            </c:if>
					            <c:if test="${authoritiy=='LeaveApplication'}">
					            <h5 class="LeaveApplication"><a href="LeaveApplication">请假申请</a></h5>
					            </c:if>
					            <c:if test="${authoritiy=='Tasks'}">
					            <h5 class="Tasks"><a href="Tasking" class="post_tasking">人事任务分配</a></h5>
					            </c:if>
					            <c:if test="${authoritiy=='ExaminationDetails'}">
					            <h5 class="AssessmentStatistics"><a href="AssessmentStatistics">考核明细</a></h5>
					            </c:if>
					            <c:if test="${authoritiy=='Reimburse'}">
					            <h5 class="Reimburse"><a href="Reimburse">报销申请</a></h5>
					            </c:if>
					            <c:if test="${authoritiy=='PaymentRequest'}">
					            <h5 class="PaymentRequest"><a href="PaymentRequest">付款申请</a></h5>
					            </c:if>
					        </c:forEach>
					        <h5 class="PageVisit"><a href="PageVisit">页面统计</a></h5>
					        </div>
					    </div> 
			        </li>
			        </c:if>

                	<c:forEach var="authoritiy" items="${authorities}">
	                    <c:if test="${authoritiy=='SalesQuotationSystem'}">
				        <li class="m-li SalesQuotationSystem0"><a href="SalesQuotationSystem">销售部</a><span></span>
							<div class="m-nav-div0">
						        <div class="m-nav-div" style="overflow:hidden;">
						        <c:forEach var="authoritiy" items="${authorities}">
						        	<c:if test="${authoritiy=='SalesQuotationSystem'}">
						            <h5 class="SalesQuotationSystem"><a href="SalesQuotationSystem">销售报价系统</a></h5>
						            </c:if>
						        </c:forEach>
						        </div>
						    </div>
				        </li>
				        </c:if>
                	</c:forEach>

                	<c:if test="${shiyanshi==1}">
			        <li class="m-li Lab0"><a href="Lab">实验室</a><span></span>
						<div class="m-nav-div0">
					        <div class="m-nav-div" style="overflow:hidden;">
					        <c:forEach var="authoritiy" items="${authorities}">
					        	<c:if test="${authoritiy=='Lab'}">
					            <h5 class="Lab"><a href="Lab">设备清单</a></h5>
					            </c:if>
					        </c:forEach>
					        </div>
					    </div> 
			        </li>
			        </c:if>
			    </ul>
			</div>

		</div>
		<div class="eou-container-r">
			<div class="m-admin">
				<span class="u-span"><%=request.getSession().getAttribute("user") %></span><i class="u-i-1"><img src="image/admin/arrow-down.png" alt="" width="10" height="7"></i>
			</div>
			<div class="u-admin">
				<h5><i class="u-i-2"><img src="image/admin/u-i-2.png" alt=""></i><a href="AlterPass" style="font-weight: 700">修改密码</a></h5>
				<c:if test="${admin0==1}">
			    <h5 class="hasui3" id="GAdmin_flag"><i class="u-i-3"><img src="image/admin/u-i-3.png" alt=""></i><a href="Admin" style="font-weight: 700">管理员</a></h5>
			    </c:if>
			    <input type="hidden" id="userID" value='<%=request.getSession().getAttribute("userID") %>'>
			    <h5><i class="u-i-4"><img src="image/admin/u-i-4.png" alt=""></i><a id="" href="Logout?user=${user}"
			name="exit" style="font-weight: 700">安全退出</a></h5>
			</div>
		</div>
	</div>
	<span id="userName" style="display:none;"><%=request.getSession().getAttribute("user") %></span>
	<span id="email" style="display:none;"><%=request.getSession().getAttribute("email") %></span>
	<c:forEach var="authoritiy" items="${authorities}">
		<c:if test="${authoritiy=='Publish'}"><span id="Publish_has_span" style="display: none;">是</span></c:if>
		<c:if test="${authoritiy=='AddPNCode'}"><span id="inventory_addpncode" style="display: none;">是</span></c:if>
		<c:if test="${authoritiy=='StandardProductReview'}"><span id="StandardProduct_check" style="display: none;">是</span></c:if>
		<c:if test="${authoritiy=='DeleteServiceReport'}"><span id="DeleteServiceReport_span" style="display: none;">是</span></c:if>
		<c:if test="${authoritiy=='SendComments'}"><span id="SendComments_has_span" style="display: none;">是</span></c:if>
	</c:forEach>
</body>
<!-- <script src="js/libs/jquery-3.3.1.min.js" type="text/javascript" charset="utf-8"></script> -->
<!-- <script src="plugins/ecdo/ec-do-1.1.4.min.js"></script> -->
<!-- <script src="plugins/cookie/jquery.cookie.js"></script> -->
<!-- <script src="plugins/imageResizeTool/imageResizeTool.min.js"></script> -->
<!-- <script src="js/msgbox_unload.js"></script> -->
<script src="js/libs/lodash.min.js"></script>
<script src="js/modules/sharing/top-dbec435863.min.js"></script>
<script src="js/global/myFunction.js?iv=201809111822" type="text/javascript" charset="utf-8"></script>
<script>
var ServiceUpdate =<%=request.getSession().getAttribute("startTime") %>

// version Flag函数
function versionControlFlag2(str){
	var versionControlFlag = true;
	globalVersionExceptFileName.map(function(currentValue, index, arr){
		if(str.indexOf(currentValue) > -1){
			versionControlFlag = false;
			return false;
		}
	});
	return versionControlFlag;
}

var vControlExceptHrefArr = ["PaymentRequest","top"];
var curVControlExceptHref = window.location.href.split(globalProjectName+"/")[1].indexOf("?") > -1 ? window.location.href.split(globalProjectName+"/")[1].split("?")[0] : window.location.href.split(globalProjectName+"/")[1];
// 加版本号控制函数
(function(){
	if(vControlExceptHrefArr.indexOf(curVControlExceptHref) > -1){
		console.warn("我跳过了版本控制函数");
		return false;
	}
	$("link").each(function(){
		if($(this).attr("href")){
			var oldHref = $(this).attr("href");
			if(versionControlFlag2(oldHref)){
				var headval;
				if(oldHref.indexOf("?")>-1){
					headval = $(this).attr("href")+"&v="+ServiceUpdate;
				}else{
					headval = $(this).attr("href")+"?v="+ServiceUpdate;
				}
			    $(this).attr("href",headval);
			}
		}
	});
	$("script").each(function(){
		if($(this).attr("src")){
			var oldSrc = $(this).attr("src");
			if(oldSrc.indexOf("?")>-1) return true;
			if(versionControlFlag2(oldSrc)){
				 var headval=$(this).attr("src")+"?v="+ServiceUpdate;
			     $(this).attr("src",headval);
			} 
		}
	});
})();

var YEAR_copy = new Date().getFullYear(); 
$(".YEAR").text(YEAR_copy);
var UserName = '<%=request.getSession().getAttribute("user") %>';
var EMAIL = '<%=request.getSession().getAttribute("email") %>';
// var date1 = new Date();
// 		date1.setTime(date1.getTime() + (2 * 60 * 1000));
// 		$.cookie('the_cookie1', 'the_value1', { expires: date1, path: '/' });

function checkHref2Cookie(){
	var href_ = window.location.href.split("cfChicken8/")[1];
	if(href_.indexOf("?")>0){
	    href_ = href_.split("?")[0];
	}
	var statisDepart;
	var statisPage;
	$.each(globalDepart2PageObj, function(name,value){
		$.each(value, function(name1,value1){
			if(value1 == href_){
				statisDepart = name;
				statisPage = name1.replace(/#/g,"");
			}
		});
	});
	console.warn(statisDepart);
	console.warn(statisPage);
	if(statisDepart){
		var encodeStatisPage = escape(statisPage);
		if(!$.cookie('Visited'+encodeStatisPage)){
			$.ajax({
				type: "POST",
				url: "PageVisit",
				data: {
					PageName: statisPage,
					Department: statisDepart
				},
				dataType: "text"
			}).then(function(data){
				if(data.indexOf("更新成功")>-1){
					var date1 = new Date();
					date1.setTime(date1.getTime() + (20 * 60 * 1000));
					$.cookie('Visited'+encodeStatisPage, 'true', { expires: date1, path: '/' });
				}else{
					console.warn(statisPage+"更新PageVisit失败！");
				}
			},function(){
				console.warn(statisPage+"请求PageVisit失败！");
			});
			
		}else{
			console.warn(statisPage+"=="+encodeStatisPage+"==有了");
		}
	}else{
		console.warn(href_+"不在映射表里");
	}
}

$(function(){
	if($("h5.hasui3").length){
		$("h5.hasui3").css("border-bottom","1px solid rgba(204,204,204,0.8)");
	}else{
		$(".eou-container-r .u-admin").css("height","67px");
	}	
	
	if($(".currentPage").children().length >1){
		//console.log($(".currentPage").children().length )
		var currentLi = $(".currentPage").children().eq(1).children();
		// var href = window.location.href.split("Logistics/")[1];
		var href = window.location.href.split("cfChicken8/")[1];
		for(var i = 0 ;i<currentLi.length;i++){
			//console.log(href);
			var day_addr = href.substr(-2);
			//console.log(day_addr);
			$("[html=day_addr]")
			if(href == currentLi.eq(i).find("a").attr("href")){
				//console.log(currentLi.eq(i).text())
				$(".currentPage").children().eq(0).text("").text(currentLi.eq(i).text())
			}
		}
	}
});

$(function(){
	var href1 = window.location.href.split("cfChicken8/")[1];
	// var href1 = window.location.href.split("Logistics/")[1];
	if(href1.indexOf("?")>0){
	    href1 = href1.split("?")[0];
	}
	if(href1 != "Index" && href1 != "Login"){
		$("div.eou-container-m").css("opacity",1);
		$("div.eou-container-m").css("visibility","visible");
	}
	if(href1 == "Inventory" ||href1 == "Transport" ||href1 == "StockPurchasing"||href1 == "Inventory"||href1 == "OriginFactory"||href1 == "Insurance"||href1 == "Proposal"||href1 == "PackingList" || href1 =="Supplier" || href1 =="Commodity"){
	    href1 = "Inventory0";
	}
	else if(href1 == "QuotationSystem" || href1 == "Price" ||  href1 == "Requirement" ||  href1 == "SalesStatistics" ||  href1 == "HotProduct" || href1 == "Quality" || href1 == "QuantityWeight" || href1 == "TestReport" || href1 == "Fumigation" || href1 == "Origin" || href1 == "Shipment" || href1 == "Receiving" || href1 == "Acceptance" || href1 == "Customer" || href1 == "Invoice" || href1 == "Keysight" || href1 == "OriginalQuotation" || href1 == "WorkReport" || href1 == "BiddingDocument"){
	    href1 = "QuotationSystem0";
	}
	else if(href1 == "MachineDetails" || href1 == "Hardware" || href1 == "AfterSale" || href1 == "Schedule" || href1 == "ServiceReport" || href1 == "StandardProduct" || href1 == "DocumentUpload" || href1 == "ApplicationGallery"){
	    href1 = "MachineDetails0";
	}
	else if(href1 == "SoftwareDocument" || href1 == "SoftwareProject" || href1 == "SoftwareProduct" || href1 == "CustomerInquiry"|| href1 == "SoftwareImplementation"){
	    href1 = "SoftwareProject0";
	}
	else if(href1 == "StaffInfo" || href1 == "LeaveApplication" || href1 == "Tasking" || href1 == "AssessmentStatistics" || href1 == "Reimburse" || href1 == "PaymentRequest"){
	    href1 = "LeaveApplication0";
	}
	else if(href1 == "SalesQuotationSystem"){
	    href1 = "SalesQuotationSystem0";
	}
	else if(href1 == "Lab"){
		href1 = "Lab0";
	}
	else if(href1 == "Index" || href1 == "Login"){
		$("div.eou-container-m").css("opacity",0);
		$("div.eou-container-m").css("visibility","hidden");
	}else{
		href1 = "00";
	}
	/* else if(href1 == "Quotation" || href1 == "QuotationSystem" || href1 == "Commodity"){
	    href1 ="quotationTit";
	}
	else if(href1 == "SalesStatistics" || href1 == "HotProduct"){
	    href1 ="SalesTit";
	}
	else if(href1 == "Transport" || href1 == "StockPurchasing"){
	    href1 ="Transport";
	} */
	if(href1 != "00"){
		$(".g-nav .g-nav-ul").find("."+href1+"").addClass("current").siblings().removeClass("current");
	}
	
	/* $(document).on("click",".Nav_More",function(){
	    //$(".Nav_More").parent().find(".listLi").toggleClass("isHidden");
	    $(".Nav_More").parent().toggleClass("navHidden");
	}) */
	
	/* if(href1== "SoftwareTit" ||href1== "LogisticsTit" ||href1== "BusinessTit" ||href1== "serTit" ||href1== "StaffTit" ||href1== "SalesTit"  ||href1== "DocumentUpload" ||href1== "OriginFactory" ){
	    $(".navRight").toggleClass("navHidden");
	    $(".navLeft").toggleClass("navHidden");
	} */
});


$(function(){
    <%-- var software = <%=request.getAttribute("software")%>;
    var otherDocument = <%=request.getAttribute("otherDocument")%>;
     alert(software);
    alert(otherDocument)  --%>
    
    var software =<%=request.getSession().getAttribute("software")%>;
    // alert(software);
    var otherDocument =<%=request.getSession().getAttribute("otherDocument")%>;
    // alert(otherDocument);

    //software 和otherDocument说明可以访问
    if(software && !otherDocument){
        $("li.MachineDetails0 h5.DocumentUpload").children("a").attr("href","DocumentUpload?Area=south&catalog=Software&Year=2018&Type=Software&queryType=common");
    }

    // $(".g-nav-ul").lavaLamp({
    //     fx: "backout", //缓动类型
    //     speed: 700, //缓动时间
    //     // click: function(event, menuItem) {
    //     //     return false; //单击触发事件
    //     // }
    // });

    // $(document).on("mouseover",".g-nav-ul .m-li",function(){
    //     $(this).addClass("m-li-hover");
    //     $(this).find(".m-nav-div").fadeIn(100);
    // });
  
    globalSubnavResponse(".g-nav div.subTit.subInsurance","h6","h6.Insurance","Insurance");
    globalSubnavResponse(".g-nav div.subTit.subSupplier","h6","h6.Supplier","Supplier");
    globalSubnavResponse(".g-nav div.subTit.subSalesStatistics","h6","h6.SalesStatistics","SalesStatistics");
    globalSubnavResponse(".g-nav div.subTit.subRequirement","h6","h6.Requirement","Requirement");
    globalSubnavResponse(".g-nav div.subTit.subQuality","h6","h6.Quality","Quality");
    globalSubnavResponse(".g-nav div.subTit.subOriginalQuotation","h6","h6.OriginalQuotation","OriginalQuotation");
    globalSubnavResponse(".g-nav div.subTit.subHardware","h6","h6.Hardware","Hardware");
    globalSubnavResponse(".g-nav div.subTit.subApplicationGallery","h6","h6.ApplicationGallery","ApplicationGallery");

    globalNavResponse(".m-li.Inventory0","h5","h5.Inventory","Inventory");
    globalNavResponse(".m-li.QuotationSystem0","h5","h5.QuotationSystem","QuotationSystem");
    globalNavResponse(".m-li.MachineDetails0","h5","h5.ServiceReport","ServiceReport");
    globalNavResponse(".m-li.SoftwareProject0","h5","h5.SoftwareProject","SoftwareProject");
    globalNavResponse(".m-li.LeaveApplication0","h5","h5.LeaveApplication","LeaveApplication");

	var originFLen = $("li.Inventory0").find("h5").length;

	if(originFLen == 1 && ($("li.Inventory0").has("h5.OriginFactory")).length){
		$("li.Inventory0>a").attr("href","OriginFactory");
	}

    $(".g-nav-ul .m-li").mouseover(function(){
        $(this).addClass("nav-li-hover");
        $(this).find(".m-nav-div0").show();
        var curW = $(this).find(".m-nav-div0").width();
        $(this).find(".m-nav-div .subTit h6").css("left",curW+"px");
        var subsubTitLen = $(this).find(".m-nav-div .subTit").length;
        if(subsubTitLen>0){
        	$(this).find(".m-nav-div .subTit").each(function(){
        		var that = $(this);
        		var i = 0;
        		$(this).find("h6").each(function(){
        			var subtop = i*35;
        			i++;
        			$(this).css("top",subtop+"px");
        			$(this).css("z-index",101);
        		});
        		// var index = $(this).find("h6").index();
        		// var subtop = index*30;
        		// $(this).find("h6").eq(index).css("top",subtop+"px");
        	});
        }
    });

    // $(document).on("mouseout",".g-nav-ul .m-li",function(){
    //     $(this).removeClass("m-li-hover");
    //     $(this).find(".m-nav-div").fadeOut(100);
    // });

    $(".g-nav-ul .m-li").mouseout(function(){
        $(this).removeClass("nav-li-hover");
        $(this).find(".m-nav-div0").hide();
    });

    $(".m-admin").click(function(e){
    	$(".u-admin").slideToggle();
    	$(document).one("click",function(){
			$(".u-admin").slideUp();
		});
		e.stopPropagation();
    });

    $(".m-nav-div .subTit").mouseover(function(){
    	$(this).addClass("activeh5");
    });

    $(".m-nav-div .subTit").mouseout(function(){
    	$(this).removeClass("activeh5");
    });
    // 全局post跳转页面
    $.extend({
        StandardPost:function(url,args){
            var body = $(document.body),
                form = $("<form method='post'></form>"),
                input;
            form.attr({"action":url});
            if(args != undefined){
                $.each(args,function(key,value){
                    input = $("<input type='hidden'>");
                    input.attr({"name":key});
                    input.val(value);
                    form.append(input);
                });
            }
            form.appendTo(document.body);
            form.submit();
            document.body.removeChild(form[0]);
        }
    });
    $(document).on("click",".post_tasking",function(e){
    	if ( e && e.preventDefault ){
    		e.preventDefault();
    	}else{
    		window.event.returnValue = false;
    	}
    	var body = $(document.body),
    	    form = $("<form method='post'></form>");
    	form.attr({"action":"Tasking"});
    	form.appendTo(document.body);
    	form.submit();
    	document.body.removeChild(form[0]);
    });

    setTimeout(checkHref2Cookie, 100);

    // $.getScript("js/global/version-control.js");
});
</script>
<script src="js/global/dispatchScript-2b3aa5cac9.min.js" type="text/javascript" charset="utf-8"></script>
<!-- <script src="js/global/dispatchScript.js" type="text/javascript" charset="utf-8"></script> -->
</html>