<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>信息统计</title>
    <link rel="stylesheet" type="text/css" href="css/views.css">
    <link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">
</head>
<style type="text/css">
		.PriceDataTable{
			margin: 0 auto;
		    width: 90%;
		    height: auto;
		    background: #e4e8eb;
		    color: #000;
		    box-sizing: border-box;
		    border-collapse: collapse;
		}
		.PriceDataTable th,.PriceDataTable td{
			text-align: center;
		    height: 48px;
		    min-width: 50px;
		    max-width: 150px;
		    word-break: break-all;
		    font-size: 15px;
		    color: #000;
		    font-weight: normal;
		    border: 1px solid #00aeef;
		}
		.searchBox{
		 /* margin-top:20px;
		 padding-left:90px */
		display: inline-block;
		width: calc(45% + 35px);
		height:50px;
		margin-top:10px;
		margin-left: calc(55% - 400px);
		}
		.searchBoxContentD{
			float:left;
			width:55px;
		}
		.searchBoxContentD .selD{
			float:left;
			width:60px;
			border:1px solid #333;
		}
		.searchBoxContentD .selD .opd{
			text-align:center;
		}
		.searchBoxContentM{
			float:left;
			margin-top:-4px;
		}
		.searchBoxContentM .selM{
		border:1px solid #333;
		}
		.spanD,.spanM{
			float:left;
			width:55px;
			height:17px;
			line-height:17px;
			background-color:#00aeef;
			border:1px solid #333;
			color:#fff;
			margin-right:20px;
			font-size:14px;
			text-align:center;
		}
		.sousuo{
			float:left;
			width:55px;
			height:20px;
			background-color:#00aeef;
			border:1px solid #333;
			color:#fff;
			margin-right:10px;
			margin-left:10px;
			font-size:14px;
			text-align:center;
			cursor:pointer;
			line-height:20px;
		}

		.statistics-export {
			position: relative;
			top: -6px;
			display: inline-block;
			height: 50px;
			margin: auto 5px auto 10px;
			padding-top: 0;
		}

		.statistics-export span {
			box-sizing: border-box;
			height: 20px;
			line-height: 20px;
			margin-left: 5px;
			margin-top: 0;
			padding: 2px 5px;
			font-size: 12px;
			border: 1px solid rgba(0,0,0,0.8);
			color: #fff;
		}

		.statistics-export span:hover {
			cursor: pointer;
		}
	</style>
<body>
<!-- 	头部开始 -->
	
	<%@include file="top.jsp" %>
<!-- 	头部结束 -->
<div class="public-top">
        <div class="nav" style="background: #00aeef;height:40px;">
            <ul style="float:left">
                <li class="currentView">
                    <a >合同信息</a>
                </li>
                <li>
                    <a >销售信息</a>
                </li>
                <li >
                    <a >个人成单率</a>
                </li>
               <li>
                    <a>月信息</a>
                </li>
            </ul>
            <!-- 时间选择框 -->
		<div class="searchBox">
	
			<div style="display:none">
				<span class="currentYear" style="dispaly:none">${currentYear}</span>	
				<span class="currentMonth" style="dispaly:none">${currentMonth}</span>
				<span class="Year" style="dispaly:none">${Year}</span>	
				<span class="Month" style="dispaly:none">${Month}</span>
			</div>	
			<div class="searchBoxContentD">
				<!-- <select>
		  		<option value ="">2016</option>
		  		<option value ="">2017</option>
		  		<option value="">2018</option>
				</select> -->
			</div>
			<span class="spanD">年份</span>
			<div class="searchBoxContentM">
				<!-- <select>
		 		 <option value ="">2016</option>
		  		<option value ="">2017</option>
		 		 <option value="">2018</option>
				</select> -->
			</div>
			<span class="spanM">月份</span>
			<form action="Statistics" method="get">
	  			<input type="text" name="Year" class="inpY" value="" style="display:none"/>
	  			<input type="text" name="Month" class="inpM" value="" style="display:none"/>
	  			<input type="button" value="搜索"  class="sousuo" onclick="Sousuo()" />
			</form>
			<div class="statistics-export" style="display: none;">
			<c:forEach var="authority" items="${authorities}">
            	<c:if test="${authority=='ExportStatistics'}">
        			<span class="export-current">导出</span>
        			<span class="export-all">导出全部</span>
                </c:if>
            </c:forEach>
			</div>
		</div> 
          
        </div>

   <!--  <div class="switch">
        <a class="sales">销售信息</a>
        <a class="sales"><b class="fa fa-hand-o-right">&nbsp;<span>销售信息</span></b></a>
    </div> -->
</div>
<div class="pContent">




    <!--柱状图-->
    <div class="pContentleft1">
        <div class="pC-left">
            <ul>
               <!--  <li><img src="image/gsbg4.gif"/></li>
                <li><img src="image/gsbg8.gif"/></li>
                <li><img src="image/gsbg3.gif"/></li> -->
            </ul>
        </div>
        <div id="picture"></div>
    </div>
    <!--地图-->
    <div class="pContentleft2">
        <div id="picture1"></div>
        <div id="picture2" style="display: none"></div>
    </div>
    <div class="pC-right">
<!--         <input type="button" id="btn" value="地图"/> -->
        <p style="margin-top:70% ;">总目标：<%=request.getAttribute("targetValue") %>M</p>
        <p>总剩余：<%= Float.parseFloat(request.getAttribute("targetValue").toString())-Float.parseFloat(request.getAttribute("completeValue").toString()) %>M</p>
    </div>
    
    
    
    <!--月信息表格  -->
    	<div class="PriceDataBox">
		<table class="PriceDataTable" >
			<thead>
				<tr>
					<th colspan="1" rowspan="2">
						<div id="tabTitle">
							<span class="tabTitle_one"></span>
							<!--<span class="tabTitle_two">区域</span>-->
						</div>
					</th>
					<th colspan="3" >北方区域</th>
					<th colspan="3" >南方区域</th>
					<th colspan="3" >西南区域</th>
				</tr>
				<tr>
					<td>月目标(M)</td>
					<td>月完成(M)</td>
					<td>月剩余(M)</td>
					<td>月目标(M)</td>
					<td>月完成(M)</td>
					<td>月剩余(M)</td>
					<td>月目标(M)</td>
					<td>月完成(M)</td>
					<td>月剩余(M)</td>
				</tr>
			</thead>
			<tbody></tbody>
			<tfoot></tfoot>
		</table>
	</div>
</div>

</body>
<script type="text/javascript">

$(".sousuo").click(function(){
	var Year = $(".searchBoxContentD option:selected").html();
	var Month = $(".searchBoxContentM option:selected").html();	
	Month = Month.substring(0,Month.length-1);
	
	if(Month == "所"){
		Month = 'All' ;
	}else if(Month < 10 ){
		Month = '0'+ Month;
	}else{
		Month = Month;
	}
	/* $(".searchBoxContentD option:selected").html(Year);
	$(".searchBoxContentM option:selected").html(Month); */
	 window.location.href = 'Statistics?Year='+ Year +'&Month='+ Month;
})

var areaData = <%=request.getAttribute("area")%>;
var salesData = <%=request.getAttribute("sales")%>;
var chengdanlvData = <%=request.getAttribute("salesStatics")%>;
var AreaStatisticsPerMonth = [];
console.log("areaData");
console.log(areaData);

<%List<Map<String, List<Map<String, Object>>>> AreaStatisticsPerMonth = (List<Map<String, List<Map<String, Object>>>>)request.getAttribute("AreaStatisticsPerMonth"); 
	int i=0;
for(Map<String, List<Map<String, Object>>> map:AreaStatisticsPerMonth){
	i++;
		%>
		var map = [];
		<%
		System.out.println("hehele:"+map);

			String key = "StatisticsByAreaPer";
			if(i<10){
				key = key+"0"+i;
			}else{
				key = key+i;
			}
			System.out.println(key);
			%>
			<%
			List<Map<String, Object>> hehe = (List<Map<String, Object>>)map.get(key);
			System.out.println(hehe);
		for(Map<String, Object> map2:hehe){
			%>
			var map2 = [];
			map2.push('<%=map2.get("AreaName")%>');
			map2.push('<%=map2.get("CompletValue")%>');
			map2.push('<%=map2.get("ID")%>');
			map2.push('<%=map2.get("RNumbers")%>');
			map2.push('<%=map2.get("TargetValue")%>');
			map.push(map2);
			<%
			System.out.println(map2);
		}
			
		
		%>
		AreaStatisticsPerMonth.push(map);
		<%
		
	}
%> 
$(function(){
	
	for(var i = 0; i < AreaStatisticsPerMonth.length;i++){
		var td3 = (AreaStatisticsPerMonth[i][1][4]*1000 -AreaStatisticsPerMonth[i][1][1]*1000)  / 1000;
		var td6 = (AreaStatisticsPerMonth[i][2][4]*1000-AreaStatisticsPerMonth[i][2][1]*1000)  / 1000;
		var td9 = (AreaStatisticsPerMonth[i][3][4]*1000 -AreaStatisticsPerMonth[i][3][1]*1000)  / 1000;
		/* if(td3 < 0){
			td3 == 0
		}		
		if(td6 < 0){
			td6 == 0
		}
		if(td9 < 0){
			td9 == 0
		} */
		var str ='<tr>'+
				'<td>'+(i+1)+'</td>'+
				'<td>'+AreaStatisticsPerMonth[i][1][4] *1000 / 1000+'</td>'+
				'<td>'+AreaStatisticsPerMonth[i][1][1] *1000 / 1000+'</td>'+
				'<td>'+td3+'</td>'+
				'<td>'+AreaStatisticsPerMonth[i][2][4] *1000 / 1000+'</td>'+
				'<td>'+AreaStatisticsPerMonth[i][2][1] *1000 / 1000+'</td>'+
				'<td>'+td6+'</td>'+
				'<td>'+AreaStatisticsPerMonth[i][3][4] *1000 / 1000+'</td>'+
				'<td>'+AreaStatisticsPerMonth[i][3][1] *1000 / 1000+'</td>'+
				'<td>'+td9+'</td>'+
			'</tr>';
		 $(".PriceDataTable tbody").append(str); 
		
	}
	var colNum = $(".PriceDataTable tbody tr:nth-child(1)").find("td").length;
	var tfooeStr = "<tr><td>合计</td>"
	for(var ii = 1;ii<colNum;ii++){
		var isum = 0;
		$(".PriceDataTable tbody tr").each(function(){
			isum+=Number($(this).find("td").eq(ii).text());
		});
		tfooeStr+="<td>"+isum.toFixed(2)+"</td>";
	}
	tfooeStr+="</tr>";
	$(".PriceDataTable tfoot").empty().append(tfooeStr);
	var MonthTdH = (parseFloat($(".pContentleft1").height()) - 30) / 17 + "px";
	$(".PriceDataTable th").css("height",MonthTdH);
	$(".PriceDataTable td").css("height",MonthTdH);
})

</script>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/echarts.js" type="text/javascript" charset="utf-8"></script>
<script src="js/china.js" type="text/javascript" charset="utf-8"></script>
<!--<script src="js/zhu.js" type="text/javascript" charset="utf-8"></script>-->
<script src="js/statistics.js" type="text/javascript" charset="utf-8"></script>
</html>
