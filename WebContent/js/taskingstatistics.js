var qtyscoreYM = {
	"Year":"2018",
	"Month":"All"
};

function findYMSel(){
	$("#year_sel").val(qtyscoreYM.Year);
	var monthSel;
	if(qtyscoreYM.Month!="All"){
		monthSel = Number(qtyscoreYM.Month);
	}else{
		monthSel = "All";
	}
	$("#month_sel").val(monthSel);
}

$(function(){
	// 进入页面渲染表格
	function initQtyTable(){
		var ArrObj = [];
		$(".task_data_span").each(function(){
			var JsonObj = {};
			JsonObj.ResponsibleMan = $(this).attr("responsibleMan");
			JsonObj.Month = $(this).attr("month");
			JsonObj.Count = $(this).attr("count");
			ArrObj.push(JsonObj);
		});
		console.log(ArrObj);
		
		var qtyManCountObj = {};
		ArrObj.map(function(currentValue,index,arr){
			var curName = currentValue.ResponsibleMan;
			if(qtyManCountObj[curName]==undefined){
				var item1 = {};
				item1[currentValue.Month] = currentValue.Count;
				qtyManCountObj[curName] = item1;
			}else{
				var kkkkk = qtyManCountObj[curName];
				kkkkk[currentValue.Month] = currentValue.Count;
			}
		});
		console.log("加载时qty表格去重之后");
		console.log(qtyManCountObj);

		var Len = 0;
		for(var jkl in qtyManCountObj){
			Len++;
		}
		// 表头
		var qty_table_th_str = '<tr>'+
									'<th style="text-align: right;border-bottom: none;padding-right:9px;padding-top:3px;">姓名</th>';
		var qty_tbody_twelve_str = '';
		var qty_tfoot_str = '<tr><td>总计</td>';
		for(var hj in qtyManCountObj){
			qty_table_th_str+='<th rowspan="2">'+hj+'</th>';
			qty_tfoot_str+='<td></td>';
		}
		qty_table_th_str+='</tr>'+
								'<tr>'+
									'<th style="text-align: left;border-top: none;padding-left:9px;padding-bottom:3px;">月份</th>'+
								'</tr>';
		$(".statistics_qty_table thead").empty().append(qty_table_th_str);

		// 表体
		for(var ww = 1;ww<13;ww++){
			qty_tbody_twelve_str+='<tr><td>'+ww+'月</td>';
			for(var ee = 0;ee<Len;ee++){
				qty_tbody_twelve_str+='<td>0</td>';
			}
			qty_tbody_twelve_str+='</tr>';
		}
		$(".statistics_qty_table tbody").empty().append(qty_tbody_twelve_str);

		// 表尾
		qty_tfoot_str+='</tr>';
		$(".statistics_qty_table tfoot").empty().append(qty_tfoot_str);

		// 动态添加数据
		var name_index = 0;
		for(var jk in qtyManCountObj){
			name_index++;
			var inObj = qtyManCountObj[jk];
			for(var kl in inObj){
				var mVal = Number(kl) - 1;
				$(".statistics_qty_table tbody tr").eq(mVal).find("td").eq(name_index).text(inObj[kl]);
			}
		}

		calcSumTable(".statistics_qty_table");
		var chart_str1 = '<div id="qty_chart_cont"></div>';
		$(".qty_chart_div").html("").append(chart_str1);
		qtyChartRender(ArrObj);
	}
	initQtyTable();
	
	// 刷新渲染表格--数量
	function qtyTableRender(dataArr){
		console.log(dataArr);
		var qtyManCountObj = {};
		dataArr.map(function(currentValue,index,arr){
			var curName = currentValue.ResponsibleMan;
			if(qtyManCountObj[curName]==undefined){
				var item1 = {};
				item1[currentValue.Month] = currentValue.Count;
				qtyManCountObj[curName] = item1;
			}else{
				var kkkkk = qtyManCountObj[curName];
				kkkkk[currentValue.Month] = currentValue.Count;
			}
		});
		console.log("qty表格去重之后");
		console.log(qtyManCountObj);

		var Len = 0;
		for(var jkl in qtyManCountObj){
			Len++;
		}
		// 表头
		var qty_table_th_str = '<tr>'+
									'<th style="text-align: right;border-bottom: none;padding-right:9px;padding-top:3px;">姓名</th>';
		var qty_tbody_twelve_str = '';
		var qty_tfoot_str = '<tr><td>总计</td>';
		for(var hj in qtyManCountObj){
			qty_table_th_str+='<th rowspan="2">'+hj+'</th>';
			qty_tfoot_str+='<td></td>';
		}
		qty_table_th_str+='</tr>'+
								'<tr>'+
									'<th style="text-align: left;border-top: none;padding-left:9px;padding-bottom:3px;">月份</th>'+
								'</tr>';
		$(".statistics_qty_table thead").empty().append(qty_table_th_str);

		// 表体
		for(var ww = 1;ww<13;ww++){
			qty_tbody_twelve_str+='<tr><td>'+ww+'月</td>';
			for(var ee = 0;ee<Len;ee++){
				qty_tbody_twelve_str+='<td>0</td>';
			}
			qty_tbody_twelve_str+='</tr>';
		}
		$(".statistics_qty_table tbody").empty().append(qty_tbody_twelve_str);

		// 表尾
		qty_tfoot_str+='</tr>';
		$(".statistics_qty_table tfoot").empty().append(qty_tfoot_str);

		// 动态添加数据
		var name_index = 0;
		for(var jk in qtyManCountObj){
			name_index++;
			var inObj = qtyManCountObj[jk];
			for(var kl in inObj){
				var mVal = Number(kl) - 1;
				$(".statistics_qty_table tbody tr").eq(mVal).find("td").eq(name_index).text(inObj[kl]);
			}
		}
		calcSumTable(".statistics_qty_table");
	}

	// 刷新渲染图表--数量
	function qtyChartRender(dataArr){
		var myChart = echarts.init(document.getElementById('qty_chart_cont'));
		var ResponsibleManArr = [];
		var CountArr = [];
		var tempMan = [];
		var tempCount = [];
		dataArr.map(function(currentValue,index,arr){
			ResponsibleManArr.push(currentValue.ResponsibleMan);
			CountArr.push(currentValue.Count);
		});
		ResponsibleManArr.map(function(currentValue,index,arr){
			if(tempMan.indexOf(currentValue)==-1){
				tempMan.push(currentValue);
				tempCount.push(CountArr[index]);
			}else{
				var hasIndex = tempMan.indexOf(currentValue);
				var tempVal = Number(tempCount[hasIndex]);
				tempCount[hasIndex] = tempVal + Number(CountArr[index]);
			}
		});
		console.log("去重之后");
		console.log(tempMan);
		console.log(tempCount);
        var option = {
                title : {
                    text: '任务分配统计数量柱状图',
                    textStyle: {
                    	color:'#999',
                    	fontSize: 14
                    }
                    // subtext: Name
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['','']
                },
                
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable:true,
                xAxis:[
	                    {
	                        type : 'category',
	                        // data : cityArr,
	                        data : tempMan,
	                        axisLabel:{  
	                            interval:0,//横轴信息全部显示  
	                            rotate:0,//-30度角倾斜显示 
	                            textStyle:{
	                            	color: 'rgba(0,0,0,0.9)',
	                            	// fontFamily: '微软雅黑,宋体,Arial, Verdana, sans-serif',
	                				fontSize:14 // 让字体变大
	            				},
								fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
								// formatter:function(value)  
					   //          {  
					   //             return value.split("").join("\n");  
					   //          } 
					   			formatter:function(value)  
	                             {  
	                                 // debugger  
	                                 var ret = "";//拼接加\n返回的类目项  
	                                 var maxLength = 1;//每项显示文字个数  
	                                 // var maxLength = 2;//每项显示文字个数  
	                                 var valLength = value.length;//X轴类目项的文字个数  
	                                 var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数  
	                                 if (rowN > 1)//如果类目项的文字大于3,  
	                                 {  
	                                     for (var i = 0; i < rowN; i++) {  
	                                         var temp = "";//每次截取的字符串  
	                                         var start = i * maxLength;//开始截取的位置  
	                                         var end = start + maxLength;//结束截取的位置  
	                                         //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧  
	                                         temp = value.substring(start, end) + "\n";  
	                                         ret += temp; //凭借最终的字符串  
	                                     }  
	                                     return ret;  
	                                 }  
	                                 else {  
	                                     return value;  
	                                 }  
	                             }
	                        },
	                        splitLine:{ 
								show:false
							}
	                    }
	                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'个数',
                        type:'bar',
                        // data:numberArr,
                        data:tempCount,
                        markPoint : {
                            data : []
                        },
                        markLine : {
                            data : []
                        },
                        // 头部显示数据
                        itemStyle:{
                        	normal:{
                        		color: function(params) {
    		                        // build a color map as your need.
    		                        var colorList = [
    		                          '#EE4775','#E3497F','#DD4B84','#D04B84','#CA4D91','#C04D91','#BF4E9A','#B84FA0','#B04FA0','#AA51AA','#A051AA','#9353BB','#8754C3','#8054C3','#7E56CA','#7056CA','#6F58D7','#685AE1','#605AE1','#5B5BE6','#505BE6','#4F5CF2','#4A5CF2','#455CF2','#405CA2','#3B5CA2','#305CA2'
    		                        ];
    		                        return colorList[(params.dataIndex)%(colorList.length)];
    		                    },
    		                    // 
    		                    // color: function(params) {
    		                    //     // build a color map as your need.
    		                    //     var colorList = [
    		                    //       ['#EE4775','#E3497F','#DD4B84'],
    		                    //       ['#CA4D91','#BF4E9A','#B84FA0'],
    		                    //       ['#B84FA0','#AA51AA','#9353BB'],
    		                    //       ['#8754C3','#7E56CA','#6F58D7'],
    		                    //       ['#625AE1','#5B5BE6','#4B5CF2']
    		                    //     ];
    		                    //     return new echarts.graphic.LinearGradient(0, 0, 0, 1,[
    		                    //     	{offset: 0, color: colorList[params.dataIndex][0]},
    		                    //     	{offset: 0.5, color: colorList[params.dataIndex][1]},
    		                    //     	{offset: 1, color: colorList[params.dataIndex][2]}
    		                    //     	]);
    		                    // },
    		                   
    		                    barBorderRadius: 4,  //柱状角成椭圆形
                        		label:{
                        			show:true,
		                			position:'top',
		                			textStyle: {
		                				color:'rgba(0,0,0,0.7)',
		                				fontSize:10
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
                        	emphasis: {
                        	    barBorderRadius: 7
                        	},
                        }
                    },
                ]
    		};     
   		myChart.setOption(option);
	}

	// 刷新渲染表格--分数
	function scoreTableRender(dataArr2){
		console.log(dataArr2);
		var scoreManCountObj = {};
		dataArr2.map(function(currentValue,index,arr){
			var curName = currentValue.ResponsibleMan;
			if(scoreManCountObj[curName]==undefined){
				var item1 = {};
				item1[currentValue.Month] = currentValue.Score;
				scoreManCountObj[curName] = item1;
			}else{
				var kkkkk = scoreManCountObj[curName];
				kkkkk[currentValue.Month] = currentValue.Score;
			}
		});
		console.log("score表格去重之后");
		console.log(scoreManCountObj);

		var Len = 0;
		for(var jkl in scoreManCountObj){
			Len++;
		}
		// 表头
		var score_table_th_str = '<tr>'+
									'<th style="text-align: right;border-bottom: none;padding-right:9px;padding-top:3px;">姓名</th>';
		var score_tbody_twelve_str = '';
		var score_tfoot_str = '<tr><td>总计</td>';
		for(var hj in scoreManCountObj){
			score_table_th_str+='<th rowspan="2">'+hj+'</th>';
			score_tfoot_str+='<td></td>';
		}
		score_table_th_str+='</tr>'+
								'<tr>'+
									'<th style="text-align: left;border-top: none;padding-left:9px;padding-bottom:3px;">月份</th>'+
								'</tr>';
		$(".statistics_score_table thead").empty().append(score_table_th_str);

		// 表体
		for(var ww = 1;ww<13;ww++){
			score_tbody_twelve_str+='<tr><td>'+ww+'月</td>';
			for(var ee = 0;ee<Len;ee++){
				score_tbody_twelve_str+='<td>0</td>';
			}
			score_tbody_twelve_str+='</tr>';
		}
		$(".statistics_score_table tbody").empty().append(score_tbody_twelve_str);

		// 表尾
		score_tfoot_str+='</tr>';
		$(".statistics_score_table tfoot").empty().append(score_tfoot_str);

		// 动态添加数据
		var name_index = 0;
		for(var jk in scoreManCountObj){
			name_index++;
			var inObj = scoreManCountObj[jk];
			for(var kl in inObj){
				var mVal = Number(kl) - 1;
				var in_td_val = Number(inObj[kl]).toFixed(1);
				$(".statistics_score_table tbody tr").eq(mVal).find("td").eq(name_index).text(in_td_val);
			}
		}
		calcSumTable(".statistics_score_table");
	}

	// 刷新渲染图表--分数
	function scoreChartRender(dataArr2){
		var myChart2 = echarts.init(document.getElementById('score_chart_cont'));
		var ResponsibleManArr = [];
		var ScoreArr = [];
		var tempMan = [];
		var tempScore = [];
		var ManScoreObj = {};
		dataArr2.map(function(currentValue,index,arr){
			ResponsibleManArr.push(currentValue.ResponsibleMan);
			ScoreArr.push((currentValue.Score*1).toFixed(0));
		});
		ResponsibleManArr.map(function(currentValue,index,arr){
			if(tempMan.indexOf(currentValue)==-1){
				tempMan.push(currentValue);
				tempScore.push(ScoreArr[index]);
			}else{
				var hasIndex = tempMan.indexOf(currentValue);
				var tempVal = Number(tempScore[hasIndex]);
				tempScore[hasIndex] = tempVal + Number(ScoreArr[index]);
				if(ManScoreObj[currentValue] == undefined){
					ManScoreObj[currentValue] = 2;
				}else{
					var oldVal = Number(ManScoreObj.currentValue);
					ManScoreObj[currentValue] = oldVal+1;
				}
			}
		});
		tempMan.map(function(currentValue,index,arr){
			for(var kk in ManScoreObj){
				if(kk == currentValue){
					var oldVal1 = Number(tempScore[index]);
					var num1 = Number(ManScoreObj[kk]);
					var perVal = (oldVal1/num1).toFixed(1);
					tempScore[index] = perVal;
				}
			}
		});
		console.log("任务分配统计分数柱状图数据去重");
		console.log(tempMan);
		console.log(tempScore);
		console.log(ManScoreObj);
        var option = {
                title : {
                    text: '任务分配统计分数柱状图',
                    textStyle: {
                    	color:'#999',
                    	fontSize: 14
                    }
                    // subtext: Name
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['','']
                },
                
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable:true,
                xAxis:[
	                    {
	                        type : 'category',
	                        // data : cityArr,
	                        data : tempMan,
	                        axisLabel:{  
	                            interval:0,//横轴信息全部显示  
	                            rotate:0,//-30度角倾斜显示 
	                            textStyle:{
	                            	color: 'rgba(0,0,0,0.9)',
	                            	// fontFamily: '微软雅黑,宋体,Arial, Verdana, sans-serif',
	                				fontSize:14 // 让字体变大
	            				},
								fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
								// formatter:function(value)  
					   //          {  
					   //             return value.split("").join("\n");  
					   //          } 
					   			formatter:function(value)  
	                             {  
	                                 // debugger  
	                                 var ret = "";//拼接加\n返回的类目项  
	                                 var maxLength = 1;//每项显示文字个数  
	                                 // var maxLength = 2;//每项显示文字个数  
	                                 var valLength = value.length;//X轴类目项的文字个数  
	                                 var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数  
	                                 if (rowN > 1)//如果类目项的文字大于3,  
	                                 {  
	                                     for (var i = 0; i < rowN; i++) {  
	                                         var temp = "";//每次截取的字符串  
	                                         var start = i * maxLength;//开始截取的位置  
	                                         var end = start + maxLength;//结束截取的位置  
	                                         //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧  
	                                         temp = value.substring(start, end) + "\n";  
	                                         ret += temp; //凭借最终的字符串  
	                                     }  
	                                     return ret;  
	                                 }  
	                                 else {  
	                                     return value;  
	                                 }  
	                             }
	                        },
	                        splitLine:{ 
								show:false
							}
	                    }
	                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'个数',
                        type:'bar',
                        // data:numberArr,
                        data:tempScore,
                        markPoint : {
                            data : []
                        },
                        markLine : {
                            data : []
                        },
                        // 头部显示数据
                        itemStyle:{
                        	normal:{
                        		color: function(params) {
    		                        // build a color map as your need.
    		                        var colorList = [
    		                          '#EE4775','#E3497F','#DD4B84','#D04B84','#CA4D91','#C04D91','#BF4E9A','#B84FA0','#B04FA0','#AA51AA','#A051AA','#9353BB','#8754C3','#8054C3','#7E56CA','#7056CA','#6F58D7','#685AE1','#605AE1','#5B5BE6','#505BE6','#4F5CF2','#4A5CF2','#455CF2','#405CA2','#3B5CA2','#305CA2'
    		                        ];
    		                        return colorList[(params.dataIndex)%(colorList.length)];
    		                    },
    		                    // 
    		                    // color: function(params) {
    		                    //     // build a color map as your need.
    		                    //     var colorList = [
    		                    //       ['#EE4775','#E3497F','#DD4B84'],
    		                    //       ['#CA4D91','#BF4E9A','#B84FA0'],
    		                    //       ['#B84FA0','#AA51AA','#9353BB'],
    		                    //       ['#8754C3','#7E56CA','#6F58D7'],
    		                    //       ['#625AE1','#5B5BE6','#4B5CF2']
    		                    //     ];
    		                    //     return new echarts.graphic.LinearGradient(0, 0, 0, 1,[
    		                    //     	{offset: 0, color: colorList[params.dataIndex][0]},
    		                    //     	{offset: 0.5, color: colorList[params.dataIndex][1]},
    		                    //     	{offset: 1, color: colorList[params.dataIndex][2]}
    		                    //     	]);
    		                    // },
    		                   
    		                    barBorderRadius: 4,  //柱状角成椭圆形
                        		label:{
                        			show:true,
		                			position:'top',
		                			textStyle: {
		                				color:'rgba(0,0,0,0.7)',
		                				fontSize:10
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
                        	emphasis: {
                        	    barBorderRadius: 7
                        	},
                        }
                    },
                ]
    		};     
   		myChart2.setOption(option);
	}


	function calcSumTable(item){
		$(item).find("tfoot tr td:not(:nth-child(1))").each(function(){
			var index = $(this).index();
			var sum = 0;
			$(item).find("tbody tr").each(function(){
				sum+=($(this).find("td:eq("+index+")").text())*1;
			});
			$(this).text(sum);
		});
	}
	// calcSumTable(".statistics_qty_table");

	// 绑定事件
	$(document).on("click",".statistics_qty_tab",function(){
		var curVal = $(".g_container_body_info_r button").text();
		if(curVal == "视图"){
			$(".score_chart_div").hide();
			$(".qty_chart_div").hide();
			$(".statistics_score_table").hide();
			$(".statistics_qty_table").fadeIn(200);
			$(".g_container_body_info_l").fadeIn(100);
			$(".g_container_body_info_l").text("任务分配统计信息数量(个)");
			calcSumTable(".statistics_qty_table");
		}else if(curVal == "表格"){
			$(".statistics_score_table").hide();
			$(".statistics_qty_table").hide();
			$(".score_chart_div").hide();
			$(".qty_chart_div").fadeIn(200);
			$(".g_container_body_info_l").fadeOut(100);
		}
		$(this).addClass("current_tab").siblings().removeClass("current_tab");
		tableChatrAjax();
	});
	$(document).on("click",".statistics_score_tab",function(){
		var curVal = $(".g_container_body_info_r button").text();
		if(curVal == "视图"){
			$(".score_chart_div").hide();
			$(".qty_chart_div").hide();
			$(".statistics_qty_table").hide();
			$(".statistics_score_table").fadeIn(200);
			$(".g_container_body_info_l").fadeIn(100);
			$(".g_container_body_info_l").text("任务分配统计信息分数(分)");
			calcSumTable(".statistics_score_table");
		}else if(curVal == "表格"){
			$(".statistics_score_table").hide();
			$(".statistics_qty_table").hide();
			$(".qty_chart_div").hide();
			$(".score_chart_div").fadeIn(200);
			$(".g_container_body_info_l").fadeOut(100);
		}
		$(this).addClass("current_tab").siblings().removeClass("current_tab");
		tableChatrAjax();
	});

	// 柱状图事件
	$(document).on("click",".g_container_body_info_r button",function(){
		if($(this).text()=="视图"){
			$(".statistics_qty_table").hide();
			$(".statistics_score_table").hide();
			$(".g_container_body_info_l").fadeOut(100);
			var currentVal_1 = $("span.current_tab").text();
			if(currentVal_1 == "统计信息数量"){
				$(".score_chart_div").hide();
				$(".qty_chart_div").fadeIn(200);
			}else if(currentVal_1 == "统计信息分数"){
				$(".qty_chart_div").hide();
				$(".score_chart_div").fadeIn(200);
			}
			$(this).text("表格");
		}else if($(this).text()=="表格"){
			$(".score_chart_div").hide();
			$(".qty_chart_div").hide();
			$(".g_container_body_info_l").fadeIn(100);
			var currentVal_2 = $("span.current_tab").text();
			if(currentVal_2 == "统计信息数量"){
				$(".statistics_score_table").hide();
				$(".statistics_qty_table").fadeIn(200);
				$(".g_container_body_info_l").text("任务分配统计信息数量(个)");
			}else if(currentVal_2 == "统计信息分数"){
				$(".statistics_qty_table").hide();
				$(".statistics_score_table").fadeIn(200);
				$(".g_container_body_info_l").text("任务分配统计信息分数(分)");
			}
			$(this).text("视图");
		}
		tableChatrAjax();
	});

	// 年份月份下拉框
	var date = new Date();
	var year = date.getFullYear();
	// var moonth = date.getMonth()+1;
	// if(moonth<10){
	//     moonth = "0"+moonth;
	// }
	function YMSel(){
		var str0 = '<option value="no" disabled selected style="display: none;">选择年份</option>';
		var gap = year - 2015;
		for(var ii = 0;ii<gap;ii++){
			var curY = 2016+ii;
			str0 += '<option value="'+curY+'">'+curY+'</option>';
		}
		$("#year_sel").empty().append(str0);
		var str00 = '<option value="no" disabled selected style="display: none;">选择月份</option><option value="All">所有</option>';
		for(var iii = 1;iii<13;iii++){
			str00+= '<option value="'+iii+'">'+iii+'</option>';
		}
		$("#month_sel").empty().append(str00);
	}
	YMSel();
	$("#year_sel").val(year);
	$("#month_sel").val("All");

	// 刷新按钮
	$("#task_freshen").on("click",function(){
		tableChatrAjax();
	});

	function tableChatrAjax(){
		var Type;
		var Year = $("#year_sel").val();
		var Month = $("#month_sel").val();
		var currentVal = $("span.current_tab").text();
		if(Year=="no"||Year==null||Year==undefined){
			$.MsgBox_Unload.Alert("提示","请选择年份");
			return;
		}else if(Month=="no"||Month==null||Month==undefined){
			$.MsgBox_Unload.Alert("提示","请选择月份");
			return;
		}else{
			if(Number(Month)<10){
				Month = "0"+Month;
			}
			if(currentVal == "统计信息数量"){
				Type = "Amount";
				$.ajax({
					type:'GET',
			        url:'TaskStatistics',
			        dataType:'json',
			        data:{
			        	Type:Type,
			        	Year:Year,
			        	Month:Month,
			        	QueryType:"Specific" 
			        },
			        success:function(res){
			        	console.log("主体ajax--数量");
			        	console.log(typeof res);
			        	console.log(res);
			        	if(res.Data.length>1){
			        		var dataArr = res.Data.slice(1);
			        		console.log(dataArr);
			        		qtyTableRender(dataArr);
			        		var chart_str1 = '<div id="qty_chart_cont"></div>';
			        		$(".qty_chart_div").html("").append(chart_str1);
			        		qtyChartRender(dataArr);
			        		var YearHook = res.Year;
			        		var MonthHook = res.Month;
			        		qtyscoreYM.Year = YearHook;
			        		qtyscoreYM.Month = MonthHook;
			        		console.log(qtyscoreYM);
			        	}else{
			        		$.MsgBox_Unload.Alert("提示","该时间段无统计数据");
			        		findYMSel();
			        	}
			        },
			        error:function(){
			        	$.MsgBox_Unload.Alert("提示","服务器繁忙！");
			        }
				});
			}else if(currentVal == "统计信息分数"){
				Type = "Review";
				$.ajax({
					type:'GET',
			        url:'TaskStatistics',
			        dataType:'json',
			        data:{
			        	Type:Type,
			        	Year:Year,
			        	Month:Month,
			        	QueryType:"Specific" 
			        },
			        success:function(res){
			        	console.log(typeof res);
			        	if(res.Data.length>1){
			        		var dataArr2 = res.Data.slice(1);
			        		console.log(dataArr2);
			        		scoreTableRender(dataArr2);
			        		var chart_str2 = '<div id="score_chart_cont"></div>';
			        		$(".score_chart_div").html("").append(chart_str2);
			        		scoreChartRender(dataArr2);
			        		var YearHook1 = res.Year;
			        		var MonthHook1 = res.Month;
			        		qtyscoreYM.Year = YearHook1;
			        		qtyscoreYM.Month = MonthHook1;
			        		console.log(qtyscoreYM);
			        	}else{
			        		$.MsgBox_Unload.Alert("提示","该时间段无统计数据");
			        		findYMSel();
			        	}
			        },
			        error:function(){
			        	$.MsgBox_Unload.Alert("提示","服务器繁忙！");
			        }
				});
			}
		}
	}
});