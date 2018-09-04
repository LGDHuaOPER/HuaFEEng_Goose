<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>需求录入-详细记录</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="css/requirementStatics.css"/>
</head>
<body>
	<!-- 	头部开始 -->
	<%@include file="top.jsp" %>
	<!-- 	头部结束 -->
	<div class="public-top">
        <div class="nav" style="background: #00aeef;height:40px">
            <ul>
                <li class="currentView" id="DemandTab">
                    <a>需求统计信息表格</a>
                </li>
                <li  id="DemandGraph">
                    <a>需求统计信息图</a>
                </li>
                <li  id="CityMap">
                    <a>城市数据</a>
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
				</div>
				<span class="spanD">年份</span>
				<div class="searchBoxContentM">
				</div>
				<span class="spanM">月份</span>
				<form action="Statistics" method="post">
				  <input type="text" name="Year" class="inpY" value="" style="display:none"/>
				  <input type="text" name="Month" class="inpM" value="" style="display:none"/>
				  <input type="button" value="搜索"  class="sousuo" />
				  <input type="button" value="导出"  class="export_part" />
				  <input type="button" value="导出全部"  class="export_all" />
				</form>
			</div> 
        </div>

	</div>
	<div class="pContent">
    <!--信息表格  -->
    	<div class="PriceDataBox">
			<table border="1" cellspacing="0" cellpadding="0" class="PriceDataTable" >
				<thead>
					<tr class="tab_header">
						<th  rowspan="2" style="width:5.5%;">月份</th>
						<th style="width:31.5%;">北方区域</th>
						<th style="width:31.5%;">南方区域</th>
						<th style="width:31.5%;">西南区域</th>
						<th  rowspan="2" style="width:5.5%;">月总计</th>
					</tr>
					<tr class="tab_header">
						<th style="background: #fff">需求</th>
						<th style="background: #fff">需求</th>
						<th style="background: #fff">需求</th>
					</tr>
				</thead>
				<tbody></tbody>
				<tfoot>
					<td>小计</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tfoot>
			</table>
		</div>
		
		 <!--信息图  -->
	 	<div class="PriceDataCanvas" id="PriceDataCanvas">
	 		<div class="CanvasBox" id="CanvasBox"></div>
	 		 <div class="changeTypeBox">
		 		<select  class="changeType">
				 	<option value="柱状图" >柱状图</option>
					<option value="折线图" >折线图</option>
					<option value="省级" >柱状图-省级</option>
					<option value="市级" >柱状图-市级</option>
				</select>
			 </div>
		</div>
		<!-- 城市数据 -->
		<div class="cityMapBox">
			<div class="cityMapBoxTop"></div>
			<div class="cityMapBoxbody"></div>
		</div>
	</div>
</body>
<script src="js/libs/echarts/echarts-all-min.js" type="text/javascript" charset="utf-8"></script>
<!-- <script src="js/echarts.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/libs/jquery.cookie.js"></script>
<!-- <script src="js/china.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/msgbox_unload.js"></script>
<script type="text/javascript">
var Province = "";
var myChart;
//需求信息  柱状
function Demand_bar(dom,data,xAxisData,title) {
    dom.innerHTML = '';
    if (myChart != null && myChart != "" && myChart != undefined) {
        myChart.dispose();
    }
    myChart = echarts.init(dom);
   var option = {
    	    title : {
    	        text: title,
    	    },
    	    tooltip : {
    	        trigger: 'axis'
    	    },
    	    toolbox: {
    	        show : true,
    	    },
    	    xAxis : [
    	        { 
    	            data : xAxisData,
    	            axisLabel:{ //调整x轴的lable  
    	                textStyle:{
    	                    fontSize:16 // 让字体变大
    	                },
    	            }
    	        }
    	    ],
    	    yAxis : [
    	        {
    	        	 name: '需求',
    	        }
    	    ],
    	    series : [
    	        {
    	            type:'bar',
    	            data:data,		//柱状图数据
    	            barWidth : 100, //柱状图宽度
    	            itemStyle: {
	   	                normal: {
	   	                    // color:function(d){return "#"+Math.floor(Math.random()*(256*256*256-1)).toString(16);}
                    		color: function(params) {
		                        // build a color map as your need.
		                        var colorList = ['#CE1717','#FFD200','#7ECEF4'];
		                        return colorList[(params.dataIndex)%(colorList.length)];
		                    },
		                    barBorderRadius: 4,  //柱状角成椭圆形
                    		label:{
                    			show:true,
	                			position:'top',
	                			textStyle: {
	                				color:'rgba(0,0,0,0.8)',
	                				fontSize:13
	                			},
	                			formatter:function(params){
	                			    if(params.value==0){
	                			        return '';
	                			    }else{
	                			        return params.value;
	                			        }
	                			}
                    		}
	   	                },
    	            },
    	        }
    	    ],
    	    // label: {
    	    //     normal: {
    	    //         show: true,
    	    //         position: 'top',
    	    //     }
    	    //  }
    	};
    myChart.setOption(option);
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
        window.onresize = myChart.resize;
    }
}
//需求信息  柱状 省 市
function Demand_ProCity(dom,data,xAxisData,title,ProOrCity) {
    dom.innerHTML = '';
    if (myChart != null && myChart != "" && myChart != undefined) {
        myChart.dispose();
    }
    myChart = echarts.init(dom);
   var option = {
    	    title : {
    	        text: title,
    	    },
    	    tooltip : {
    	        trigger: 'axis'
    	    },
    	    toolbox: {
    	        show : true,
    	    },
    	    grid : {
    	    	y2 :140
    	    },
    	    xAxis : [
    	        { 
    	            data : xAxisData,
    	            axisLabel:{ //调整x轴的lable  
    	                textStyle:{
    	                    fontSize:14 // 让字体变大
    	                },
    	                interval:0,
    	                formatter:function(value)  
                        {  
                            return value.split("").join("\n");  
                        }  
    	            }
    	        }
    	    ],
    	    yAxis : [
    	        {
    	        	 name: '需求',
    	        }
    	    ],
    	    series : [
    	        {
    	            type:'bar',
    	            data:data,		//柱状图数据
    	            // barWidth : 100, //柱状图宽度
    	            itemStyle: {
	   	                normal: {
	   	                    // color:function(d){return "#"+Math.floor(Math.random()*(256*256*256-1)).toString(16);}
                    		color: function(params) {
		                        // build a color map as your need.
		                        var colorList = [
		                          '#DE3656','#D6395B','#C93F64','#C0436C','#B64873','#A35283','#9D5588','#885F98','#7468A6','#6C6BA6','#5F72B6','#5A74B9','#5077BE','#4B7BC5','#467DC9','#4081CE','#3A83D3','#3183DA','#2A8BDF','#248EE3','#1E91E8','#1993EC','#1396F0','#0F98F4','#0B9AF7','#069CFA','#019FFF'
		                        ];
		                        return colorList[(params.dataIndex)%(colorList.length)];
		                    },
		                    barBorderRadius: 4,  //柱状角成椭圆形
                    		label:{
                    			show:true,
	                			position:'top',
	                			textStyle: {
	                				color:'rgba(0,0,0,0.8)',
	                				fontSize:13
	                			},
	                			formatter:function(params){
	                			    if(params.value==0){
	                			        return '';
	                			    }else{
	                			        return params.value;
	                			        }
	                			}
                    		}
	   	                },
    	            },
    	        }
    	    ],
    	    // label: {
    	    //     normal: {
    	    //         show: true,
    	    //         position: 'top',
    	    //     }
    	    //  }
    	};
   if(ProOrCity =="Pro"){
	   	myChart.on('click', function(param) {
	   		 Province = param.name;
	   		var title = $(".selD option:selected").text()+"年"+$(".selM option:selected").text().replace("月","")+"月"+Province+"需求信息";
			var callback = function(data) {
				//console.log(data);
			 var dataArr = [];
			 var xAxisData = [];
			 if(data.areasStatics.length < 2){
				 $.MsgBox_Unload.Alert("提示", "没有对应市级信息");
			 }
			 else{
				 for(var j =1; j < data.areasStatics.length; j++){
					 xAxisData.push(data.areasStatics[j].City);
					 dataArr.push(data.areasStatics[j].itemCounts);
				 } 
			 }
			 console.log(xAxisData)
			 xAxisData = ObjSort(xAxisData,dataArr);    //X轴排序
			dataArr.sort(function(a,b){return a-b});	//柱子数据排序
			var dom = document.getElementById("CanvasBox");
			 Demand_ProCity(dom,dataArr,xAxisData,title);  
			};
			$(".changeType option").attr("selected",false);
			$(".changeType option[value='市级']").attr("selected","selected");
			CityAjax(callback,Province);
	   	})
   }

    myChart.setOption(option);
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
        window.onresize = myChart.resize;
    }
}
//需求信息  折线绘图函数
function Demand_line(dom,data,title) {
    dom.innerHTML = '';
    if (myChart != null && myChart != "" && myChart != undefined) {
        myChart.dispose();
    }
    myChart = echarts.init(dom);
   var option = {
		    title: {
		        text: title,
		        textStyle:{color:'#00aeef'},
		        subtext:'需求',
		        subtextStyle:{
		        	color:'#000',
		        	fontWeight: 'bolder',
		        	paddingTop:'30'
		        }
		    },
		    tooltip: {
		        trigger: 'axis'
		    },
		    legend: {
		        top:'10%',
		        data:[  {name:'北方',icon:'rect'},
		                {name:'南方',icon:'rect'},
		                {name:'西南',icon:'rect'}]
		    },
		    grid: {
		        left: '1%',
		        right: '4%',
		        bottom: '3%',
		        top:'100',
		        containLabel: true
		    },
		    xAxis: {
		        type: 'category',
		        axisTick: {show: false},
		      	axisLine:{
		      		lineStyle:{color:'#ccc',width:1}
		      	},
		      	axisLabel:{
		      		show: true,
		      		textStyle:{color:'#000'}
		      	},
		      	nameTextStyle:{fontWeight:'bolder'},
		        boundaryGap: true,
		        data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
		    },
		    yAxis: {
		        type: 'value',
		        axisLine: {show: false},
		        nameTextStyle:{fontWeight:'bolder'},
		        axisTick: {show: false}
		    },
		    series: [
		        {
		            name:'北方',
		            type:'line',
		            // stack: '总量',
		            data:data[0]
		        },
		        {
		            name:'南方',
		            type:'line',
		            // stack: '总量',
		            data:data[1]
		        },
		        {
		            name:'西南',
		            type:'line',
		            // stack: '总量',
		            data:data[2]
		        }
		        
		    ]
		}
    myChart.setOption(option);
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
        window.onresize = myChart.resize;
    }
}
//柱状图 排序   a--X轴数据   b--柱子数据
function ObjSort(a,b){
	var arr2 = [];
	for (var i = 0; i < b.length; i++) {
		arr2.push(b[i]);
	}
	b.sort(function(a,b){return a-b});
	var c = [];
	var d = [];
	for (var i = 0; i < b.length; i++) {
		for (var j = 0; j < arr2.length; j++) {
			if(b[i] ==arr2[j]){
				arr2.splice(j,1,"as");
				c.push(j);
				break;
			}
		}
	}
	for(var i = 0; i < c.length; i++){
		d.push(a[c[i]]);
	}
	return d;
}
function DemandAjax(callback){
	var Year = $(".searchBoxContentD option:selected").html();
	var Month = $(".searchBoxContentM option:selected").html();	
	Month = Month.substring(0,Month.length-1);
	console.log(Month)
	if(Month == "所"){
		Month = 'All' ;
	}else if(Month < 10 ){
		Month = '0'+ Month;
	}else{
		Month = Month;
	}
	$.ajax({
		type : 'get',
		url : 'RequirementDetails',
		data : {
			Year : Year,
			Month : Month,
			QueryType : "Select"
		},
		dataType: 'json',
		success :callback,
		error : function(e) {
			console.log(e);
		}
	});
}

function ProAjax(callback){
	var Year = $(".searchBoxContentD option:selected").html();
	var Month = $(".searchBoxContentM option:selected").html();	
	Month = Month.substring(0,Month.length-1);
	console.log(Month)
	if(Month == "所"){
		Month = 'All' ;
	}else if(Month < 10 ){
		Month = '0'+ Month;
	}else{
		Month = Month;
	}
	$.ajax({
		type : 'get',
		url : 'RequirementDetailsProvince',
		data : {
			Year : Year,
			Month : Month,
		},
		dataType: 'json',
		success :callback,
		error : function(e) {
			console.log(e);
		}
	});
	
}

function CityAjax(callback,Province){
	var Year = $(".searchBoxContentD option:selected").html();
	var Month = $(".searchBoxContentM option:selected").html();	
	Month = Month.substring(0,Month.length-1);
	console.log(Month)
	if(Month == "所"){
		Month = 'All' ;
	}else if(Month < 10 ){
		Month = '0'+ Month;
	}else{
		Month = Month;
	}
	$.ajax({
		type : 'get',
		url : 'RequirementDetailsCity',
		data : {
			Year : Year,
			Month : Month,
			Province :Province
	},
		dataType: 'json',
		success :callback,
		error : function(e) {
			console.log(e);
		}
	});
	
}

function calcSubSum(){
	$(".requCalcTd").each(function(){
		var trr = $(this).parent();
		var sumNum = 0;
		for(var i = 1;i<trr.children("td").length-1;i++){
			var tempNum = Number(trr.children("td").eq(i).text());
			sumNum += tempNum;
		}
		$(this).text(sumNum.toFixed(0));
	});
	$(".PriceDataBox .PriceDataTable tfoot td").each(function(){
		var index1 = $(this).index();
		if(index1>0){
			var curTrLen = $(".PriceDataBox .PriceDataTable tbody tr").length;
			var sumNum = 0;
			$(".PriceDataBox .PriceDataTable tbody tr").each(function(){
				var tempNum2 = Number($(this).find("td").eq(index1).text());
				sumNum += tempNum2;
			});
			$(this).text(sumNum.toFixed(0));
		}
	});
}

// 城市数据ajax请求
function getCityMapData(Year,Month){
	$.ajax({
		type: "POST",
		url: "RequirementDetailsProvince",
		data: {
			Year: Year,
			Month: Month
		},
		dataType: "json"
	}).then(function(res){
		var YearText = res.Year;
		var MonthText = res.Month;
		var data = res.Statistics;
		var CityValueArr = [];
		var maxValue;
		var noDataArr = [{name:'江苏',value:0}];
		if(data.length>1){
			data.map(function(currentValue,index,arr){
				if(index>0){
					if(currentValue.NewProvince == ""||currentValue.NewProvince == "--"){
						return true;
					}
					var item = {};
					item.name = currentValue.NewProvince;
					item.value = currentValue.Count;
					CityValueArr.push(item);
				}
			});
			maxValue = CityValueArr[0].value;
			$(".cityMapBoxTop").text(YearText+"年"+MonthText+"月城市数据");
			var str = '<div id="cityMapBoxCon"></div>';
			$(".cityMapBoxbody").empty().append(str);
			renderCityMapData(CityValueArr,maxValue);
		}else{
			$(".cityMapBoxTop").text("无城市数据");
			var str = '<div id="cityMapBoxCon"></div>';
			$(".cityMapBoxbody").empty().append(str);
			renderCityMapData(noDataArr,6);
			$.MsgBox_Unload.Alert("提示", "所选时间无需求城市数据！");
		}
	},function(){
		$.MsgBox_Unload.Alert("提示", "网络繁忙！");
	});
}

function renderCityMapData(CityValueArr,maxValue){
	console.log(CityValueArr);
	console.log(maxValue);
	var splitValue = Math.floor(Number(maxValue)/3);
	var cityChart = echarts.init(document.getElementById('cityMapBoxCon'));
	var cityOption = {
        tooltip: {
            trigger: 'item',
            formatter: '{b}'
        },
        series: [
            {
                name: '中国',
                type: 'map',
                mapType: 'china',
                roam: false,
                selectedMode : 'multiple',
                itemStyle:{
                    normal:{label:{show:true}},
                    emphasis:{label:{show:true}},
                },
                data: CityValueArr
            }
        ],
        dataRange: {
                 x: 'left',
                 y: 'bottom',
                 splitList: [
                     // {start: 1500},
                     // {start: 900, end: 1500},
                     {start: splitValue*3},
                     {start: splitValue*2, end: splitValue*3},
                     {start: splitValue, end: splitValue*2},
                     {start: 1, end: splitValue},
                     {end: 1}
                 ],
                 color: ['#c00000','#ff0000','#ff9600','#ffff00','#fff']
             }
             
		};
		cityChart.setOption(cityOption);
}

$(function(){
	var year = '<%request.getAttribute("Year");%>';
	var month = '<%request.getAttribute("Month");%>';
	
	<% Map<String, List<Map<String, Object>>> map = ( Map<String, List<Map<String, Object>>>)request.getAttribute("areasStatics");
		List<Map<String, Object>> southList = map.get("南方");
		%>
		var southMap = [];//南方区的12个月的数组
		<%
		for(int j=1;j<southList.size();j++){
			Map<String, Object> temp = southList.get(j);
			%>
			southMap.push('<%=temp.get("itemCounts").toString()%>');
			<%
			
		}
		List<Map<String, Object>> wethList = map.get("西南区");
		%>
		var wethMap = [];//西南区的12个月的数组
		<%
		for(int j=1;j<wethList.size();j++){
			Map<String, Object> temp = wethList.get(j);
			%>
			wethMap.push('<%=temp.get("itemCounts").toString()%>');
			<%
			
		}
		List<Map<String, Object>> NorthList = map.get("北方");
		%>
		var NorthMap = [];//北方区的12个月的数组
		<%
		for(int j=1;j<NorthList.size();j++){
			Map<String, Object> temp = NorthList.get(j);
			%>
			NorthMap.push('<%=temp.get("itemCounts").toString()%>');
			<%
			
		}

	%>
	console.log(NorthMap);
	//初始进入页面加载数据
	var str="";
	for(var i = 0;i<NorthMap.length;i++){
		str+="<tr class='tab_data'>"+
			"<td>"+(i+1)+"</td>"+
			"<td>"+NorthMap[i]+"</td>"+
			"<td>"+southMap[i]+"</td>"+
			"<td>"+wethMap[i]+"</td>"+
			"<td class='requCalcTd'></td>"+
		"</tr>"
	}
	$(".PriceDataBox .PriceDataTable tbody").append(str);
	calcSubSum();
	
	//加载月份与年份
	var myDate = new Date();
	var currentYear =myDate.getFullYear();
	var currentMonth = myDate.getMonth()+1;
	console.log(currentYear);
	console.log(currentMonth);
	var Year = parseInt($(".Year").html());
	var Month = parseInt($(".Month").html());
	if(Month == 'All'){
		Month = "所有";
	}else{
		Month = parseInt(Month)+"月";
	}
	//年份的下拉框
	var LD = currentYear - 2016;
		$(".searchBoxContentD").empty();
		var strD = '';
		var str1 = '';
		for(var i = 0;i<=LD;i++){
			  str1 += '<option value ="'+(2016+i)+'" class="opd" text="'+(2016+i)+'">'+(2016+i)+'</option>'
		}
		strD = '<select class="selD">'+
		  str1
		+'</select>' 
	 $(".searchBoxContentD").append(strD);
		$(".searchBoxContentD").find('option[text="' + Year + '"]').prop("selected", true);
		//月份的下拉框	
		var str2 = '';
		var strM = '';
		for(var i = 1;i<=12;i++){
			var ii;
			if(i<10){
				ii = "0"+i;
			}else{
				ii = i;
			}
			str2 += '<option value ="'+(ii)+'" class="opm" text="'+(i)+'月">'+(i)+'月</option>'
		}
		strM = '<select class="selM">'+
		   '<option value ="All" class="opm" text="所有">所有</option>'+
		     str2
		   +'</select>' 
		$(".searchBoxContentM").empty();
		$(".searchBoxContentM").append(strM);
		$(".searchBoxContentM").find('option[text="' + Month + '"]').prop("selected", true);

		//搜索事件
		$(".sousuo").click(function(){
			var id = $(".currentView").attr("id");
			
			if(id == "DemandTab"){
				var callback = function(data) {
					console.log(data);
					$(".PriceDataTable .tab_data").remove();
					var str_search="";
					if($(".selM option:selected").text() == "所有"){
						 for(var i = 1;i<data.areasStatics.南方.length;i++){
							str_search+="<tr class='tab_data'>"+
								"<td>"+(i)+"</td>"+
								"<td>"+data.areasStatics.北方[i].itemCounts+"</td>"+
								"<td>"+data.areasStatics.南方[i].itemCounts+"</td>"+
								"<td>"+data.areasStatics.西南区[i].itemCounts+"</td>"+
								"<td class='requCalcTd'></td>"+
							"</tr>"
						}
					}
					else{
						var index = parseFloat($(".selM option:selected").text().replace("月",""));
						str_search+="<tr class='tab_data'>"+
							"<td>"+(index)+"</td>"+
							"<td>"+data.areasStatics.北方[index].itemCounts+"</td>"+
							"<td>"+data.areasStatics.南方[index].itemCounts+"</td>"+
							"<td>"+data.areasStatics.西南区[index].itemCounts+"</td>"+
							"<td class='requCalcTd'></td>"+
						"</tr>"
					}
					$(".PriceDataBox .PriceDataTable tbody").append(str_search);
					calcSubSum();
				};
				DemandAjax(callback);
			}else if(id == "DemandGraph"){
				var selected_text =  $(".changeType option:selected").text();
				if(selected_text == "柱状图"){
					var title = $(".selD option:selected").text()+"年"+$(".selM option:selected").text().replace("月","")+"月份需求信息";
					var callback = function(data) {
					 var dataArr = [];
					 var data_n = 0;
					 var data_b = 0;
					 var data_x = 0;
					 for(var i = 1;i<data.areasStatics.南方.length;i++){
						 data_b += parseFloat(data.areasStatics.北方[i].itemCounts);
						 data_n += parseFloat(data.areasStatics.南方[i].itemCounts);
						 data_x += parseFloat(data.areasStatics.西南区[i].itemCounts);
					}
					 dataArr.push(data_b); dataArr.push(data_n); dataArr.push(data_x);
						var dom = document.getElementById("CanvasBox");
						var xAxisData = ['北方','南方','西南'];
						xAxisData = ObjSort(xAxisData,dataArr);    //X轴排序
						dataArr.sort(function(a,b){return a-b});	//柱子数据排序
						Demand_bar(dom,dataArr,xAxisData,title); 
					};
					DemandAjax(callback);
				}
				else if(selected_text == "柱状图-省级"){
					var title = $(".selD option:selected").text()+"年"+$(".selM option:selected").text().replace("月","")+"月省级需求信息";
					var callback = function(data) {
						console.log(data);
					 var dataArr = [];
					 var xAxisData = [];
					 for(var i in data.areasStatics){
						 var dataNum = 0;
						 if(data.areasStatics[i].length >= 2){
							 for(var j =1; j < data.areasStatics[i].length; j++){
								 if(data.areasStatics[i][j] != undefined){
									 dataNum += parseFloat(data.areasStatics[i][j].itemCounts);
								 }
							 } 
							 if(dataNum!=0){
								 dataArr.push(dataNum);
								 xAxisData.push(i);
							 }
						 }
					 }
					 xAxisData = ObjSort(xAxisData,dataArr);    //X轴排序
					dataArr.sort(function(a,b){return a-b});	//柱子数据排序
						var dom = document.getElementById("CanvasBox");
					 Demand_ProCity(dom,dataArr,xAxisData,title,"Pro"); 
					};
					ProAjax(callback);
				}
				else if(selected_text == "柱状图-市级"){
					 Province = "";
			   		var title = $(".selD option:selected").text()+"年"+$(".selM option:selected").text().replace("月","")+"月市级需求信息";
					var callback = function(data) {
						//console.log(data);
					 var dataArr = [];
					 var xAxisData = [];
					 if(data.areasStatics.length < 2){
						 $.MsgBox_Unload.Alert("提示", "没有对应市级信息");
					 }
					 else{
						 for(var j =1; j < data.areasStatics.length; j++){
							 xAxisData.push(data.areasStatics[j].City);
							 dataArr.push(data.areasStatics[j].itemCounts);
						 } 
					 }
					 xAxisData = ObjSort(xAxisData,dataArr);    //X轴排序
					dataArr.sort(function(a,b){return a-b});	//柱子数据排序
					var dom = document.getElementById("CanvasBox");
					 Demand_ProCity(dom,dataArr,xAxisData,title,"");  
					};
					$(".changeType option[value='市级']").attr("selected","selected");
					CityAjax(callback,Province);
				}
				else if(selected_text == "折线图"){
					var title = $(".selD option:selected").text()+"年所有月份需求信息";
					var callback = function(data) {
						 var dataArr = [];
						 var data_n = [];
						 var data_b = [];
						 var data_x = [];
						 for(var i = 1;i<data.areasStatics.南方.length;i++){
							 data_b.push(data.areasStatics.北方[i].itemCounts);
							 data_n.push(data.areasStatics.南方[i].itemCounts);
							 data_x.push(data.areasStatics.西南区[i].itemCounts);
						}
						dataArr.push(data_b); dataArr.push(data_n); dataArr.push(data_x);
					  	var dom = document.getElementById("CanvasBox");
					  	Demand_line(dom,dataArr,title) 
					};
					var Year = $(".searchBoxContentD option:selected").html();
					var Month = 'All' ;
					$.ajax({
						type : 'get',
						url : 'RequirementDetails',
						data : {
							Year : Year,
							Month : Month,
							QueryType : "Select"
						},
						dataType: 'json',
						success :callback,
						error : function(e) {
							console.log(e);
						}
					});
				}
			}else if(id == "CityMap"){
				var selYear = $(".selD").val();
				var selMonth = $(".selM").val();
				getCityMapData(selYear,selMonth);
			}
		})
		
		//点击 表格与图形切换
		$(".nav ul li").click(function(){
			var id = $(this).attr("id");
			$(this).addClass("currentView").siblings().removeClass("currentView");
			if(id == "DemandTab"){
				$(".PriceDataBox").show();
				$(".PriceDataCanvas").hide();
				$(".cityMapBox").hide();
				var callback = function(data) {
					console.log(data);
					$(".PriceDataTable .tab_data").remove();
					
					var str_search="";
					if($(".selM option:selected").text() == "所有"){
						
						 for(var i = 1;i<data.areasStatics.南方.length;i++){
							str_search+="<tr class='tab_data'>"+
								"<td>"+(i)+"</td>"+
								"<td>"+data.areasStatics.北方[i].itemCounts+"</td>"+
								"<td>"+data.areasStatics.南方[i].itemCounts+"</td>"+
								"<td>"+data.areasStatics.西南区[i].itemCounts+"</td>"+
								"<td class='requCalcTd'></td>"+
							"</tr>"
						}
					}
					else{
						var index = parseFloat($(".selM option:selected").text().replace("月",""));
						str_search+="<tr class='tab_data'>"+
							"<td>"+(index)+"</td>"+
							"<td>"+data.areasStatics.北方[index].itemCounts+"</td>"+
							"<td>"+data.areasStatics.南方[index].itemCounts+"</td>"+
							"<td>"+data.areasStatics.西南区[index].itemCounts+"</td>"+
							"<td class='requCalcTd'></td>"+
						"</tr>"
					}
					$(".PriceDataBox .PriceDataTable tbody").append(str_search);
					calcSubSum();
				};
				DemandAjax(callback);
				
			}else if(id == "DemandGraph"){
				$(".PriceDataBox").hide();
				$(".cityMapBox").hide();
				$(".PriceDataCanvas").show();
				var selected_text =  $(".changeType option:selected").text();
				
				if(selected_text == "柱状图"){
					var title = $(".selD option:selected").text()+"年"+$(".selM option:selected").text().replace("月","")+"月份需求信息";
					var callback = function(data) {
						 
					 var dataArr = [];
					 var data_n = 0;
					 var data_b = 0;
					 var data_x = 0;
					 for(var i = 1;i<data.areasStatics.南方.length;i++){
						 data_b += parseFloat(data.areasStatics.北方[i].itemCounts);
						 data_n += parseFloat(data.areasStatics.南方[i].itemCounts);
						 data_x += parseFloat(data.areasStatics.西南区[i].itemCounts);
					}
					 dataArr.push(data_b); dataArr.push(data_n); dataArr.push(data_x);
						var dom = document.getElementById("CanvasBox");
						 var xAxisData = ['北方','南方','西南'];
						 xAxisData = ObjSort(xAxisData,dataArr);    //X轴排序
						dataArr.sort(function(a,b){return a-b});	//柱子数据排序
						Demand_bar(dom,dataArr,xAxisData,title); 
					};
					DemandAjax(callback);
				}
				else if(selected_text == "柱状图-省级"){
					var title = $(".selD option:selected").text()+"年"+$(".selM option:selected").text().replace("月","")+"月省级需求信息";
					var callback = function(data) {
						console.log(data);
					 var dataArr = [];
					 var xAxisData = [];
					 for(var i in data.areasStatics){
						 var dataNum = 0;
						 if(data.areasStatics[i].length >= 2){
							 for(var j =1; j < data.areasStatics[i].length; j++){
								 if(data.areasStatics[i][j] != undefined){
									 dataNum += parseFloat(data.areasStatics[i][j].itemCounts);
								 }
							 } 
							 if(dataNum!=0){
								 dataArr.push(dataNum);
								 xAxisData.push(i);
							 }
							 
						 }
					 }
					 xAxisData = ObjSort(xAxisData,dataArr);    //X轴排序
					dataArr.sort(function(a,b){return a-b});	//柱子数据排序
						var dom = document.getElementById("CanvasBox");
					 Demand_ProCity(dom,dataArr,xAxisData,title,"Pro"); 
					};
					ProAjax(callback);
				}
				else if(selected_text == "柱状图-市级"){
					var title = $(".selD option:selected").text()+"年"+$(".selM option:selected").text().replace("月","")+"月市级需求信息";
					var callback = function(data) {
						//console.log(data);
					 var dataArr = [];
					 var xAxisData = [];
					 if(data.areasStatics.length < 2){
						 $.MsgBox_Unload.Alert("提示", "没有对应市级信息");
					 }
					 else{
						 for(var j =1; j < data.areasStatics.length; j++){
							 xAxisData.push(data.areasStatics[j].City);
							 dataArr.push(data.areasStatics[j].itemCounts);
						 } 
					 }
					 xAxisData = ObjSort(xAxisData,dataArr);    //X轴排序
					dataArr.sort(function(a,b){return a-b});	//柱子数据排序
					var dom = document.getElementById("CanvasBox");
					 Demand_ProCity(dom,dataArr,xAxisData,title,"");  
					};
					$(".changeType option[value='市级']").attr("selected","selected");
					CityAjax(callback,Province);
				}
				else if(selected_text == "折线图"){
					var title = $(".selD option:selected").text()+"年所有月份需求信息";
					var callback = function(data) {
							 var dataArr = [];
							 var data_n = [];
							 var data_b = [];
							 var data_x = [];
							 for(var i = 1;i<data.areasStatics.南方.length;i++){
								 data_b.push(data.areasStatics.北方[i].itemCounts);
								 data_n.push(data.areasStatics.南方[i].itemCounts);
								 data_x.push(data.areasStatics.西南区[i].itemCounts);
							}
							 dataArr.push(data_b); dataArr.push(data_n); dataArr.push(data_x);
						  var dom = document.getElementById("CanvasBox");
						  Demand_line(dom,dataArr,title) 
					};
					
					var Year = $(".searchBoxContentD option:selected").html();
					var Month = 'All' ;
					$.ajax({
						type : 'get',
						url : 'RequirementDetails',
						data : {
							Year : Year,
							Month : Month,
							QueryType : "Select"
						},
						dataType: 'json',
						success :callback,
						error : function(e) {
							console.log(e);
						}
					});
				}
			}else if(id == "CityMap"){
				$(".PriceDataBox").hide();
				$(".PriceDataCanvas").hide();
				$(".cityMapBox").show();
				// getCityMapData(null,null);
				$(".selD").val(currentYear);
				$(".selM").val("All");
				getCityMapData(undefined,undefined);
			}
		})
		
		$(".changeType").change(function(){
			var selected_text =  $(".changeType option:selected").text();
			if(selected_text == "柱状图"){
				var title = $(".selD option:selected").text()+"年"+$(".selM option:selected").text().replace("月","")+"月份需求信息";
				var callback = function(data) {
					 if(data.areasStatics.南方.length == 2){
						 var  dataArr = [data.areasStatics.北方[1].itemCounts,data.areasStatics.南方[1].itemCounts,data.areasStatics.西南区[1].itemCounts];
					 }
					 else{
						 var dataArr = [];
						 var data_n = 0;
						 var data_b = 0;
						 var data_x = 0;
						 for(var i = 1;i<data.areasStatics.南方.length;i++){
							 data_b += parseFloat(data.areasStatics.北方[i].itemCounts);
							 data_n += parseFloat(data.areasStatics.南方[i].itemCounts);
							 data_x += parseFloat(data.areasStatics.西南区[i].itemCounts);
						}
						 dataArr.push(data_b); dataArr.push(data_n); dataArr.push(data_x);
						 
					 }
					  var dom = document.getElementById("CanvasBox");
					  var xAxisData = ['北方','南方','西南'];
					  xAxisData = ObjSort(xAxisData,dataArr);    //X轴排序
					 dataArr.sort(function(a,b){return a-b});	//柱子数据排序
					Demand_bar(dom,dataArr,xAxisData,title);  
				};
				DemandAjax(callback);
			}
			else if(selected_text == "柱状图-省级"){
				var title = $(".selD option:selected").text()+"年"+$(".selM option:selected").text().replace("月","")+"月省级需求信息";
				var callback = function(data) {
					console.log(data);
				 var dataArr = [];
				 var xAxisData = [];
				 for(var i in data.areasStatics){
					 var dataNum = 0;
					 if(data.areasStatics[i].length >= 2){
						 for(var j =1; j < data.areasStatics[i].length; j++){
							 if(data.areasStatics[i][j] != undefined){
								 dataNum += parseFloat(data.areasStatics[i][j].itemCounts);
							 }
						 } 
						 if(dataNum!=0){
							 dataArr.push(dataNum);
							 xAxisData.push(i);
						 }
						 
					 }
				 }
				 console.log(xAxisData);
				 console.log(dataArr);
				 xAxisData = ObjSort(xAxisData,dataArr);    //X轴排序
				 dataArr.sort(function(a,b){return a-b});	//柱子数据排序
				 var dom = document.getElementById("CanvasBox");
				 Demand_ProCity(dom,dataArr,xAxisData,title,"Pro"); 
				};
				ProAjax(callback);
			}
			else if(selected_text == "柱状图-市级"){
				Province = "";
		   		var title = $(".selD option:selected").text()+"年"+$(".selM option:selected").text().replace("月","")+"月市级需求信息";
				var callback = function(data) {
					//console.log(data);
				 var dataArr = [];
				 var xAxisData = [];
				 if(data.areasStatics.length < 2){
					 $.MsgBox_Unload.Alert("提示", "没有对应市级信息");
				 }
				 else{
					 for(var j =1; j < data.areasStatics.length; j++){
						 xAxisData.push(data.areasStatics[j].City);
						 dataArr.push(data.areasStatics[j].itemCounts);
					 } 
				 }
				 //console.log(xAxisData)
				 xAxisData = ObjSort(xAxisData,dataArr);    //X轴排序
				dataArr.sort(function(a,b){return a-b});	//柱子数据排序
				var dom = document.getElementById("CanvasBox");
				 Demand_ProCity(dom,dataArr,xAxisData,title,"");  
				};
				$(".changeType option[value='市级']").attr("selected","selected");
				CityAjax(callback,Province);
			}
			else if(selected_text == "折线图"){
				var title = $(".selD option:selected").text()+"年所有月份需求信息";
				var callback = function(data) {
						 var dataArr = [];
						 var data_n = [];
						 var data_b = [];
						 var data_x = [];
						 for(var i = 1;i<data.areasStatics.南方.length;i++){
							 data_b.push(data.areasStatics.北方[i].itemCounts);
							 data_n.push(data.areasStatics.南方[i].itemCounts);
							 data_x.push(data.areasStatics.西南区[i].itemCounts);
						}
						 dataArr.push(data_b); dataArr.push(data_n); dataArr.push(data_x);
					  var dom = document.getElementById("CanvasBox");
					  Demand_line(dom,dataArr,title) 
				};
				
				var Year = $(".searchBoxContentD option:selected").html();
				var Month = 'All' ;
				$.ajax({
					type : 'get',
					url : 'RequirementDetails',
					data : {
						Year : Year,
						Month : Month,
						QueryType : "Select"
					},
					dataType: 'json',
					success :callback,
					error : function(e) {
						console.log(e);
						
					}
				});
			}
		})
	// 页面加载时的判断
	var curClassify = $.cookie("RequirementDetailsClassify");
	if(curClassify==undefined||curClassify==""){
		curClassify = "DemandTab";
	}
	$("#"+curClassify).trigger("click");
});
//导出
$(".export_part").click(function(){
	var Y = $(".searchBoxContentD option:selected").html();
	var M = $(".searchBoxContentM option:selected").html();
	var year = new Date().getFullYear();
	 if(Y == year && M == '所有'){
		 $.ajax({
		        type : 'post',
		        url : 'RequirementDetails',
		        data:{
		        	classify:'Export'
		        },
		        success : function (data) {
		        	console.log(data)
		        window.location.href = data;
		        },
		        error : function () {
		            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
		        }
			}) 
	 }else if(Y != year && M == '所有'){
		 $.ajax({
		        type : 'post',
		        url : 'RequirementDetails',
		        data:{
		        	classify:'Export',
		        	QueryType:'select',
		        	Year:Y
		        },
		        success : function (data) {
		        	console.log(data)
		        window.location.href = data;
		        },
		        error : function () {
		            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
		        }
			}) 
	 }else if(M != '所有'){
		 var Index = M.indexOf('月');
		 M = M.substring(0,Index);
		 $.ajax({
		        type : 'post',
		        url : 'RequirementDetails',
		        data:{
		        	classify:'Export',
		        	QueryType:'select',
		        	Year:Y,
		        	Month:M
		        },
		        success : function (data) {
		        	console.log(data)
		        window.location.href = data;
		        },
		        error : function () {
		            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
		        }
			})
		 
	 }
	
})
$(".export_all").click(function(){
	 $.ajax({
	        type : 'post',
	        url : 'RequirementDetails',
	        data:{
	        	classify:'ExportAll'
	        },
	        success : function (data) {
	        	console.log(data)
	        window.location.href = data;
	        },
	        error : function () {
	            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
	        }
		})
})
</script>
</html>