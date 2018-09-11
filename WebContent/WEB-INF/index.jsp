<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<!-- 为移动设备添加 viewport -->
	<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
	<title>个人中心</title>
	<link rel="shortcut icon" href="./image/eoulu.ico"/>
	<link rel="bookmark" href="./image/eoulu.ico"/>
	<!--<link rel="stylesheet" type="text/css" href="css/animate.min.css">-->
	<!--<link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">-->
	<link rel="stylesheet" type="text/css" href="./css/libs/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="./css/global/reset.css">
	<link rel="stylesheet" href="./css/global/icon.css" type="text/css">
	<link href='http://cdn.webfont.youziku.com/webfonts/nomal/115512/19650/5b4ed741f629d808cc21eae4.css' rel='stylesheet' type='text/css' />
	<link rel="stylesheet" href="./css/index.css" type="text/css">
    
	<!--<script type="text/javascript" src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.js"></script>-->
	<script src="./js/libs/jquery-3.3.1.min.js" defer></script>
    <script src="plugins/cookie/jquery.cookie.js" defer></script>
	<!-- <script src="./js/libs/lodash.min.js" defer></script> -->
	<script src="./js/libs/jquery.easing.1.3.js" defer></script>
	<script src="./js/libs/bootstrap.min.js" defer></script>
	<script src="./js/index.js?iv=201808291121" defer></script>
</head>
<body class="eou-index-body">
	<%@include file="top.jsp"%>
	<%--原文内容--%>
	<input type="hidden" class="model" value="${model}">
	<input type="hidden" class="isOpen" value="${sign}">
	<input type="hidden" class="message" value="${message}">
	<input type="hidden" name="user_id" value="${userID}">
    <input type="hidden" name="user_name" value="${user}">
	<!-- <input type="hidden" name="eouluauthority" value='${authorities}'> -->

    <div class="container-fluid index-g" style="margin-top: -3px;position: relative;">
        <div class="container-fluid index-g-top">
            <div class="m-h3">
                <span class="icon1"><i class="icon-circle-right"></i></span>
                <h3>最常访问：</h3>
            </div>
            <div class="m-usual">
                <div class="u-usual u-u1"><div class="u-us"><a></a><img src="" alt=""></div></div>
                <div class="u-usual u-u2"><div class="u-us"><a></a><img src="" alt=""></div></div>
                <div class="u-usual u-u3"><div class="u-us"><a></a><img src="" alt=""></div></div>
                <div class="u-usual u-u4"><div class="u-us"><a></a><img src="" alt=""></div></div>
                <div class="u-usual u-u5"><div class="u-us"><a></a><img src="" alt=""></div></div>
            </div>
        </div>
        <div class="container-fluid index-g-body">
            <div class="m-h2">
                <span class="icon2"><i class="icon-circle-right"></i></span>
                <h2>页面入口：</h2>
                <span class="infoma">请选择页面进行浏览</span>
                <!-- <span class="icon3" title="请选择页面进行浏览"><i class="icon-information-outline"></i></span> -->
                <button type="button" class="btn btn-danger" disabled="disabled" style="display: none;">洗牌(2)</button>
                <div class="new">
                	<c:forEach var="authority" items="${authorities}" varStatus="status" >
                	<c:if test="${authority=='Schedule'}">
                    <div class="u-usual"><div class="u-us"><a href="Schedule">员工行程</a> <img src="" alt=""></div></div>
                    </c:if>
                    </c:forEach>
                </div>
            </div>
            <div class="m-enter">
                <div class="container-fluid m-img">

                <c:forEach var="authority" items="${authorities}" varStatus="status">
                    <c:if test="${status.index>0}">
                    <span class="eouluAuthoritySpan" title="${authority}"></span>
                    </c:if>
                </c:forEach>

                <c:forEach var="authoritiy" items="${authorities}">
                    <c:if test="${authoritiy=='SoftwareDocument' || authoritiy=='SoftwareProject' || authoritiy=='CustomerInquiry' || authoritiy=='SoftwareImplementation' || authoritiy=='SoftwareProduct'}"><c:set var="ruanjianbuz" value="1"></c:set></c:if>

                    <c:if test="${authoritiy=='QuotationSystem' || authoritiy=='Quality' || authoritiy=='Invoice' || authoritiy=='PackingList' || authoritiy=='Customer' || authoritiy=='QuantityWeight' || authoritiy=='TestReport' || authoritiy=='Fumigation' || authoritiy=='Origin' || authoritiy=='Shipment' || authoritiy=='Receiving' || authoritiy=='Acceptance' || authoritiy=='OriginalQuotation' || authoritiy=='WorkReport' || authoritiy=='BiddingDocument'}"><c:set var="shangwubuz" value="1"></c:set></c:if>

                    <c:if test="${authoritiy=='StockPurchasing' || authoritiy=='Inventory' || authoritiy=='OriginFactory' || authoritiy=='Insurance' || authoritiy=='Proposal' || authoritiy=='PackingList' || authoritiy=='Supplier' || authoritiy=='Commodity'}"><c:set var="wuliubuz" value="1"></c:set></c:if>

                    <c:if test="${authoritiy=='MachineDetails' || authoritiy=='Hardware' || authoritiy=='AfterSale' || authoritiy=='ServiceReport' || authoritiy=='StandardProduct' || authoritiy=='DocumentUpload' || authoritiy=='ApplicationGallery'}"><c:set var="fuwubuz" value="1"></c:set></c:if>

                    <c:if test="${authoritiy=='SalesStatistics' || authoritiy=='HotProduct'}"><c:set var="xiaoshoutongjiz" value="1"></c:set></c:if>

                    <c:if test="${authoritiy=='StaffInfo' || authoritiy=='LeaveApplication' || authoritiy=='Tasks' || authoritiy=='ExaminationDetails' || authoritiy=='Reimburse' || authoritiy=='PaymentRequest'}"><c:set var="renshibuz" value="1"></c:set></c:if>

                    <c:if test="${authoritiy=='Requirement' || authoritiy=='Keysight'}"><c:set var="xuqiutongjiz" value="1"></c:set></c:if>

                    <c:if test="${authoritiy=='SalesQuotationSystem'}"><c:set var="xiaoshoubuz" value="1"></c:set></c:if>

                    <c:if test="${authoritiy=='Lab'}"><c:set var="shiyanshiz" value="1"></c:set></c:if>
                </c:forEach>

                    <c:if test="${ruanjianbuz==1}">
                    <div class="u-img"><div class="m-authority hasSubTit" value="SoftwareProduct"><a href="SoftwareProduct">软件部</a> <img src="" alt=""></div></div>
                    </c:if>

                    <c:if test="${shangwubuz==1}">
                    <div class="u-img"><div class="m-authority hasSubTit" value="QuotationSystem"><a href="QuotationSystem">商务部</a> <img src="" alt=""></div></div>
                    </c:if>

                    <c:if test="${fuwubuz==1}">
                    <div class="u-img"><div class="m-authority hasSubTit" value="ServiceReport"><a href="ServiceReport">服务部</a> <img src="" alt=""></div></div>
                    </c:if>

                    <c:if test="${wuliubuz==1}">
                    <div class="u-img"><div class="m-authority hasSubTit" value="Inventory"><a href="Inventory">物流部</a> <img src="" alt=""></div></div>
                    </c:if>
                    
                    <c:forEach var="authoritiy" items="${authorities}">
                    <c:if test="${authoritiy=='Transport'}">
                    <div class="u-img"><div class="m-authority"><a href="Transport?ActualDelivery=no&column=DateOfSign&condition=All">物流统计</a> <img src="" alt=""></div></div>
                    </c:if>
                    </c:forEach>
                    
                    <c:forEach var="authoritiy" items="${authorities}">
                    <c:if test="${authoritiy=='Price'}">
                    <div class="u-img"><div class="m-authority"><a href="Price?ActualDelivery=no&column=DateOfSign&condition=All">合同统计</a> <img src="" alt=""></div></div>
                    </c:if>
                    </c:forEach>
                    
                    <c:if test="${xiaoshoubuz==1}">
                    <div class="u-img"><div class="m-authority hasSubTit" value="SalesQuotationSystem"><a href="SalesQuotationSystem">销售部</a> <img src="" alt=""></div></div>
                    </c:if>

                    <c:if test="${renshibuz==1}">
                    <div class="u-img"><div class="m-authority hasSubTit" value="LeaveApplication"><a href="LeaveApplication">人事部</a> <img src="" alt=""></div></div>
                    </c:if>

                    <c:if test="${xiaoshoutongjiz==1}">
                    <div class="u-img"><div class="m-authority hasSubTit" value="SalesStatistics"><a href="SalesStatistics">销售统计</a> <img src="" alt=""></div></div>
                    </c:if>
                    
                    <c:if test="${xuqiutongjiz==1}">
                    <div class="u-img"><div class="m-authority hasSubTit" value="Requirement"><a href="Requirement">需求统计</a> <img src="" alt=""></div></div>
                    </c:if>

                    <c:if test="${shiyanshiz==1}">
                    <div class="u-img"><div class="m-authority hasSubTit" value="Lab"><a href="Lab">实验室</a><img src="" alt=""></div></div>
                    </c:if>
                </div>
            </div>
        </div>
        <div id="eoulu-copy" style="width:calc(100% - 2px);height:35px;font-size:12px;color:#fff;line-height:35px;z-index: 2;position: absolute;bottom: 5px;"><div style="width:100%;text-align:center;display:inline-block;">Copyright  ©&nbsp;<span class="YEAR">2018</span>&nbsp<a href="http://www.eoulu.com/h-col-268.html" class="EHref" target="_blank" style="color:blue;">Eoulu</a> Tech. Co.,Ltd.</div></div>
    </div>
    <script>
    var eouluAuthority = {
        "SoftwareProduct":["SoftwareDocument","SoftwareProject","SoftwareProduct","CustomerInquiry","SoftwareImplementation"],
        "QuotationSystem":["QuotationSystem","Quality","QuantityWeight","TestReport","Fumigation","Origin","Shipment","Receiving","Acceptance","Invoice","PackingList","Customer","OriginalQuotation","WorkReport","BiddingDocument"],
        "Inventory":["StockPurchasing","Inventory","OriginFactory","Insurance","Proposal","PackingList","Supplier","Commodity"],
        "ServiceReport":["MachineDetails","Hardware","AfterSale","ServiceReport","StandardProduct","DocumentUpload","ApplicationGallery"],
        "SalesStatistics":["SalesStatistics","HotProduct"],
        "LeaveApplication":["LeaveApplication","StaffInfo","Tasks","ExaminationDetails","Reimburse","PaymentRequest"],
        "Requirement":["Requirement","Keysight"],
        "SalesQuotationSystem":["SalesQuotationSystem"]
    }

    $(function(){
        if($("div.eou-container-m").length){
            $("div.eou-container-m").css("opacity",0);
            $("div.eou-container-m").css("visibility","hidden");
        }
        var eouluAuthoritySpan = [];
        $(".eouluAuthoritySpan").each(function(){
            eouluAuthoritySpan.push($(this).attr("title"));
        });
        // console.log("拥有的权限");
        // console.log(eouluAuthoritySpan);
    	var software =
    		<%=request.getSession().getAttribute("software")%>
    	;
    	var otherDocument =
    		<%=request.getSession().getAttribute("otherDocument")%>
    	;

        function authorityMap(a){
            for(var i in eouluAuthority){
                if(eouluAuthority[i].indexOf(a) > -1 ){
                    return i;
                }
            }
        }
        var hasInsertHref = [];
        var iii=1;
        var jjj=1;
        var kkk=1;
        // 麻将动态添加href
        eouluAuthoritySpan.map(function(currentValue,index,arr){
            // console.log("不管是否默认都执行了"+jjj+"次");
            jjj++;
            var majiangCurrent = authorityMap(currentValue);
            // console.log("currentValue为："+currentValue);
            // console.log(majiangCurrent);
            if(hasInsertHref.indexOf(majiangCurrent)>-1){
                // console.log("数组中已有相同默认链接"+kkk+"次");
                kkk++;
                return;
            }
            // console.log("执行了"+iii+"次");
            iii++;
            if(majiangCurrent == currentValue){
                hasInsertHref.push(majiangCurrent);
            }
            if(currentValue=="StockPurchasing"){
                currentValue="StockPurchasing?ActualDelivery=no&column=DateOfSign&condition=All";
            }
            if(currentValue=="Tasks"){
                currentValue="Tasking";
            }
            if(currentValue == "NonStandardProject"){
                currentValue = "NonStandard";
            }
            if(currentValue == "ExaminationDetails"){
                currentValue = "AssessmentStatistics";
            }
            if(currentValue == "DocumentUpload"){
                currentValue = "DocumentUpload?queryType=common&Area=south&catalog=Manual&Year=2018&Type=Cascade";
                //software 和otherDocument说明可以访问
                if(software && !otherDocument){
                    currentValue = "DocumentUpload?Area=south&catalog=Software&Year=2018&Type=Software&queryType=common";
                }
            }
            $("div.hasSubTit[value="+majiangCurrent+"]").children("a").attr("href",currentValue);
        });
        // console.log("hasInsertHref的值为："+hasInsertHref);
    	var a = 'no';
    	var b = 'no';
        var c = 'no';
        var d = 'no';
    	var e = 'no';
    	var aCount = 0;
    	var bCount = 0;
        var cCount = 0;
        var dCount = 0;
    	var eCount = 0;
    	<% List<Map<String,Object>> ls  = (List<Map<String,Object>>)request.getAttribute("datas");
    	for(int i=1;i<ls.size();i++){
    		Map<String,Object> map = ls.get(i);
    		if(i==1){
    	%>
    	 a = '<%=map.get("JspName").toString()%>';
    	 aCount = '<%=map.get("AccessCount").toString()%>';
    	<%
    		}
    		if(i==2){
    			%>
    			 b = '<%=map.get("JspName").toString()%>';
    			 bCount = '<%=map.get("AccessCount").toString()%>';
    			<%
    		}
    		
    		if(i==3){
    			%>
    			 c = '<%=map.get("JspName").toString()%>';
    			 cCount = '<%=map.get("AccessCount").toString()%>';
    			<%
    		}

            if(i==4){
                %>
                 d = '<%=map.get("JspName").toString()%>';
                 dCount = '<%=map.get("AccessCount").toString()%>';
                <%
            }

            if(i==5){
                %>
                 e = '<%=map.get("JspName").toString()%>';
                 eCount = '<%=map.get("AccessCount").toString()%>';
                <%
            }
    	}
    	%>
    	console.log(a+":"+aCount+","+b+":"+bCount+","+c+":"+cCount+","+d+":"+dCount+","+e+":"+eCount);
    	function staticDepart() {}
        staticDepart.department = ["员工行程","软件部","商务部","物流统计","箱单制作","发票制作","资料库","FORMFACTOR","物流部","合同统计","需求统计","库存页面","服务部","销售统计","人事部","文档管理","报价系统","库存采购","运输指令","运输保险","箱单制作","热销产品","商品管理","质量证明","数量重量","测试报告","熏蒸证明","原产地证明","发货通知","验收报告","FAT","客户信息表","机台统计","装机进展","售后维修","员工行程","软件部文档","开发项目管理","软件产品管理","客户询价记录","软件实施管理","员工信息","请假申请","软件实施记录","人事任务分配","Keysight","原厂报价单","考核明细","服务完成报告","供应商管理","标准产品","销售报价系统","设备清单","报销申请","工作汇报","招标文件","付款申请","研发图库"];
        staticDepart.departhref = ["Schedule","SoftwareProduct","QuotationSystem","Transport?ActualDelivery=no&column=DateOfSign&condition=All","PackingList","Invoice","Equipment","OriginFactory","Inventory","Price?ActualDelivery=no&column=DateOfSign&condition=All","Requirement","Inventory","MachineDetails","SalesStatistics","LeaveApplication","DocumentUpload?queryType=common&Area=south&catalog=Manual&Year=2018&Type=Cascade","QuotationSystem","StockPurchasing?ActualDelivery=no&column=DateOfSign&condition=All","Insurance","Proposal","PackingList","HotProduct","Commodity","Quality","QuantityWeight","TestReport","Fumigation","Origin","Shipment","Receiving","Acceptance","Customer","MachineDetails","Hardware","AfterSale","Schedule","SoftwareDocument","SoftwareProject","SoftwareProduct","CustomerInquiry","SoftwareImplementation","StaffInfo","LeaveApplication","SoftwareImplementation","Tasking","Keysight","OriginalQuotation","AssessmentStatistics","ServiceReport","Supplier","StandardProduct","SalesQuotationSystem","Lab","Reimburse","WorkReport","BiddingDocument","PaymentRequest","ApplicationGallery"];
        if (a!='no'){
        	var firstpos = staticDepart.department.indexOf(a);
        	if(firstpos!= -1){
        		    $(".m-usual .u-u1 a").attr("href",staticDepart.departhref[firstpos]);
        			$(".m-usual .u-u1 a").html(a);
        			$(".u-usual.u-u1").css("opacity",1);
        	}
    	}
    	if (b!='no'){
    		var secpos = staticDepart.department.indexOf(b);
        	if(secpos!= -1){
        		    $(".m-usual .u-u2 a").attr("href",staticDepart.departhref[secpos]);
        			$(".m-usual .u-u2 a").html(b);
        			$(".u-usual.u-u2").css("opacity",1);
        	}
    	}
    	if (c!='no'){
    		var thirdpos = staticDepart.department.indexOf(c);
        	if(thirdpos!= -1){
        		    $(".m-usual .u-u3 a").attr("href",staticDepart.departhref[thirdpos]);
        			$(".m-usual .u-u3 a").html(c);
        			$(".u-usual.u-u3").css("opacity",1);
        	}
    	}
        if (d!='no'){
            var forthpos = staticDepart.department.indexOf(d);
            if(forthpos!= -1){
                    $(".m-usual .u-u4 a").attr("href",staticDepart.departhref[forthpos]);
                    $(".m-usual .u-u4 a").html(d);
                    $(".u-usual.u-u4").css("opacity",1);
            }
        }
        if (e!='no'){
            var fifthpos = staticDepart.department.indexOf(e);
            if(fifthpos!= -1){
                    $(".m-usual .u-u5 a").attr("href",staticDepart.departhref[fifthpos]);
                    $(".m-usual .u-u5 a").html(e);
                    $(".u-usual.u-u5").css("opacity",1);
            }
        }

        function indexCopyResponse(){
            if($("body.eou-index-body").height() - $(window).height()>10){
                // alert("我执行了");
                var oldHeight = $("body.eou-index-body").height();
                var newBodyH = oldHeight + 35;
                var oldDivH = $("div.index-g").height();
                var newDivH = oldDivH + 35;
                $("body").css("height",newBodyH+"px");
                $("div.index-g").css("height",newDivH+"px");
            }else if($("body.eou-index-body").height() - $(window).height()>0){
                var oldHeight2 = $("body.eou-index-body").height();
                var newBodyH2 = oldHeight2 + 15;
                var oldDivH2 = $("div.index-g").height();
                var newDivH2 = oldDivH2 + 15;
                $("body").css("height",newBodyH2+"px");
                $("div.index-g").css("height",newDivH2+"px");
            }
        }
        indexCopyResponse();
        // $(window).on("resize",indexCopyResponse);
        $(document).on("click",".u-us a[href='Tasking']",function(e){
            if ( e && e.preventDefault ){
                e.preventDefault();
            }else{
                window.event.returnValue = false;
            }
            var body1 = $(document.body),
                form1 = $("<form method='post'></form>");
            form1.attr({"action":"Tasking"});
            form1.appendTo(document.body);
            form1.submit();
            document.body.removeChild(form1[0]);
        });
        $(document).on("click",".m-authority a[href='Tasking']",function(e){
            if ( e && e.preventDefault ){
                e.preventDefault();
            }else{
                window.event.returnValue = false;
            }
            var body2 = $(document.body),
                form2 = $("<form method='post'></form>");
            form2.attr({"action":"Tasking"});
            form2.appendTo(document.body);
            form2.submit();
            document.body.removeChild(form2[0]);
        });
    });
    </script>
</body>

</html>