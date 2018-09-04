/**********变量与函数定义*************/
var classifyMapObj = {
	"Eoulu": "EOULU内部培训",
	"Formfactor": "原厂考核培训",
	"Experiment": "动手实验",
	"All": "所有考核"
};

var basisMapObj = {
	"year": "年度统计（单位：年）",
	"month": "月度统计（单位：月）",
	"week": "周统计"
};

// 年份月份下拉框
var date = new Date();
var iyear = date.getFullYear();
// var moonth = date.getMonth()+1;
// if(moonth<10){
//     moonth = "0"+moonth;
// }

// classifyMapObj
function getClassifyName(item){
	for(var k in classifyMapObj){
		if(item==k){
			return classifyMapObj[k];
		}
	}
}

// basisMapObj
function getBasisName(item){
	for(var k in basisMapObj){
		if(item==k){
			return basisMapObj[k];
		}
	}
}

function YMSel(){
	var str0 = '<option value="no" disabled selected style="display: none;">选择年份</option>';
	var gap = iyear - 2015;
	for(var ii = 0;ii<gap;ii++){
		var curY = 2016+ii;
		str0 += '<option value="'+curY+'">'+curY+'</option>';
	}
	$("#year_sel").empty().append(str0);
	// var str00 = '<option value="no" disabled selected style="display: none;">选择月份</option><option value="All">所有</option>';
	// for(var iii = 1;iii<13;iii++){
	// 	str00+= '<option value="'+iii+'">'+iii+'</option>';
	// }
	// $("#month_sel").empty().append(str00);
}

// 渲染员工统计数据
function renderStaffAssess(res,Classify){
	var myChart = echarts.init(document.getElementById(Classify+"_charts_cont"));
	// var { ...newOption } = baseChartOption;
	// var applyOption = $.extend(true, {}, baseChartOption, newOption);
	var AvgScoreArr = [];
	var ExaminerArr = [];
	var TimesArr = [];
	var curName = getClassifyName(Classify);
	res.map(function(currentValue,index,arr){
		if(index>0){
			var newAvgScore = currentValue.AvgScore;
			if(newAvgScore=="--"||newAvgScore==""){
				newAvgScore = 0;
			}
			AvgScoreArr.push(Number(newAvgScore).toFixed(2));
			ExaminerArr.push(currentValue.Examiner);
			TimesArr.push(currentValue.Times);
		}
	});
	var baseChartOption = {
			        title: {
			            text: '成员统计信息柱状图',
			            textStyle: {
			            	color:'#999',
			            	fontSize: 14
			            },
			            subtext: curName
			        },
			        tooltip: {
			            trigger: 'axis'
			        },
			        legend: {
			        	itemWidth: 20,
	        	        itemHeight: 10,
	        	        itemGap: 10,
	        	        right: '45%',
    	                textStyle: {
    	                    fontSize: 12,
    	                    color: '#999'
    	                },
    	                // backgroundColor: "rgb(0,0,0)",
			            data:[
			            	{
			            		name:'平均分',
			            		icon:'rect',
			            	},{
			            		name:'次数',
			            	}
			            ]
			        },
			        toolbox: {
			            show: true,
			            feature: {
			                mark: {show: true},
			                dataView: {show: true, readOnly: false},
			                magicType: {show: true, type: ['line', 'bar']},
			                restore: {show: true},
			                saveAsImage: {show: true}
			            }
			        },
			        calculable: true,
			        xAxis: [
			            {
			                type: 'category',
			                // data : cityArr,
			                data: ExaminerArr,
			                axisLabel: {  
			                    interval: 0,//横轴信息全部显示  
			                    rotate: 0,//-30度角倾斜显示 
			                    textStyle: {
			                    	color: 'rgba(0,0,0,0.9)',
			                    	// fontFamily: '微软雅黑,宋体,Arial, Verdana, sans-serif',
			        				fontSize: 14 // 让字体变大
			    				},
								fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
								// formatter:function(value)  
					   //          {  
					   //             return value.split("").join("\n");  
					   //          } 
					   			formatter: function(value){  
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
			        yAxis: [
			            {
			                type : 'value'
			            }
			        ],
			        series: [
			            {
			                name: '平均分',
			                type: 'bar',
			                // data:numberArr,
			                data: AvgScoreArr,
			                markPoint: {
			                    data: []
			                },
			                markLine: {
			                    data: []
			                },
			                // 头部显示数据
			                itemStyle: {
			                	normal: {
			                		color: "#5793f3",
			                		// color: function(params){
				                 //        // build a color map as your need.
				                 //        var colorList = [
				                 //          '#EE4775','#E3497F','#DD4B84','#D04B84','#CA4D91','#C04D91','#BF4E9A','#B84FA0','#B04FA0','#AA51AA','#A051AA','#9353BB','#8754C3','#8054C3','#7E56CA','#7056CA','#6F58D7','#685AE1','#605AE1','#5B5BE6','#505BE6','#4F5CF2','#4A5CF2','#455CF2','#405CA2','#3B5CA2','#305CA2'
				                 //        ];
				                 //        return colorList[(params.dataIndex)%(colorList.length)];
				                 //    },
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
			                		label: {
			                			show: true,
			                			position: 'top',
			                			textStyle: {
			                				color: 'rgba(0,0,0,0.7)',
			                				fontSize: 10
			                			},
			                			formatter: function(params){
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
			            // 次数
                        {
                            name: '次数',
                            type: 'bar',
                            // data:numberArr,
                            data: TimesArr,
                            markPoint: {
                                data: []
                            },
                            markLine: {
                                data: []
                            },
                            // 头部显示数据
                            itemStyle: {
                            	normal: {
                            		color: "#d14a61",
                            		// color: function(params){
            	                 //        // build a color map as your need.
            	                 //        var colorList = [
            	                 //          '#EE4775','#E3497F','#DD4B84','#D04B84','#CA4D91','#C04D91','#BF4E9A','#B84FA0','#B04FA0','#AA51AA','#A051AA','#9353BB','#8754C3','#8054C3','#7E56CA','#7056CA','#6F58D7','#685AE1','#605AE1','#5B5BE6','#505BE6','#4F5CF2','#4A5CF2','#455CF2','#405CA2','#3B5CA2','#305CA2'
            	                 //        ];
            	                 //        return colorList[(params.dataIndex)%(colorList.length)];
            	                 //    },
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
                            		label: {
                            			show: true,
                            			position: 'top',
                            			textStyle: {
                            				color: 'rgba(0,0,0,0.7)',
                            				fontSize: 10
                            			},
                            			formatter: function(params){
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
                        }
			        ]
				};
	myChart.setOption(baseChartOption);
	/*窗口自适应，关键代码*/  
    setTimeout(function(){
        window.onresize = function(){  
            myChart.resize();
        };
    },200);
	// if (baseChartOption && typeof baseChartOption === "object") {
	//     myChart.setOption(baseChartOption, false);
	//     window.onresize = myChart.resize({width:"auto",height:"auto"});
	// }
}

// 渲染时间统计数据
function renderTimeAssess(res,Basis){
	var myChart = echarts.init(document.getElementById(Basis+"_charts_cont"));
	// var { ...newOption } = baseChartOption;
	// var applyOption = $.extend(true, {}, baseChartOption, newOption);
	var AvgScoreArr = [];
	var XAxisArr = [];
	var TimesArr = [];
	// var quarter1 = [];
	// var quarter2 = [];
	// var quarter3 = [];
	// var quarter4 = [];
	// var scoreQuarter1 = [];
	// var scoreQuarter2 = [];
	// var scoreQuarter3 = [];
	// var scoreQuarter4 = [];
	// var timesQuarter1 = [];
	// var timesQuarter2 = [];
	// var timesQuarter3 = [];
	// var timesQuarter4 = [];
	var curName = getBasisName(Basis);
	res.map(function(currentValue,index,arr){
		if(index>0){
			var newAvgScore = currentValue.AvgScore;
			if(newAvgScore=="--"||newAvgScore==""){
				newAvgScore = 0;
			}

			if(Basis=="year"){
				if(currentValue.Years=="--"||currentValue.Years==""){
					return true;
				}
				XAxisArr.push(currentValue.Years);
				AvgScoreArr.push(Number(newAvgScore).toFixed(2));
				TimesArr.push(currentValue.Times);
			}else if(Basis=="month"){
				if(currentValue.Months=="--"||currentValue.Months==""){
					return true;
				}
				var monthVal = currentValue.Months.split("-");
				var newMonthVal = monthVal[0]+"年"+monthVal[1]+"月";
				XAxisArr.push(newMonthVal);
				AvgScoreArr.push(Number(newAvgScore).toFixed(2));
				TimesArr.push(currentValue.Times);
			}else if(Basis=="week"){
				if(currentValue.Weeks=="--"||currentValue.Weeks==""){
					return true;
				}
				var weekVal = currentValue.Weeks.split("-");
				var mm = Number(weekVal[1]);
				var newWeekVal = weekVal[0]+"年"+mm+"月"+weekVal[2]+"周";
				XAxisArr.push(newWeekVal);
				AvgScoreArr.push(Number(newAvgScore).toFixed(2));
				TimesArr.push(currentValue.Times);
				// if(mm>0 && mm < 4){
				// 	quarter1.push(newWeekVal);
				// 	scoreQuarter1.push(Number(newAvgScore).toFixed(2));
				// 	timesQuarter1.push(currentValue.Times);
				// }else if(mm>3 && mm < 7){
				// 	quarter2.push(newWeekVal);
				// 	scoreQuarter2.push(Number(newAvgScore).toFixed(2));
				// 	timesQuarter2.push(currentValue.Times);
				// }else if(mm>6 && mm < 10){
				// 	quarter3.push(newWeekVal);
				// 	scoreQuarter3.push(Number(newAvgScore).toFixed(2));
				// 	timesQuarter3.push(currentValue.Times);
				// }else if(mm>9 && mm < 13){
				// 	quarter4.push(newWeekVal);
				// 	scoreQuarter4.push(Number(newAvgScore).toFixed(2));
				// 	timesQuarter4.push(currentValue.Times);
				// }
			}
		}
	});
	// if(quarter1.length==0){
	// 	quarter1 = ["无"];
	// }
	// if(quarter2.length==0){
	// 	quarter2 = ["无"];
	// }
	// if(quarter3.length==0){
	// 	quarter3 = ["无"];
	// }
	// if(quarter4.length==0){
	// 	quarter4 = ["无"];
	// }
	// if(scoreQuarter1.length==0){
	// 	scoreQuarter1 = ["0"];
	// }
	// if(scoreQuarter2.length==0){
	// 	scoreQuarter2 = ["0"];
	// }
	// if(scoreQuarter3.length==0){
	// 	scoreQuarter3 = ["0"];
	// }
	// if(scoreQuarter4.length==0){
	// 	scoreQuarter4 = ["0"];
	// }
	// if(timesQuarter1.length==0){
	// 	timesQuarter1 = ["0"];
	// }
	// if(timesQuarter2.length==0){
	// 	timesQuarter2 = ["0"];
	// }
	// if(timesQuarter3.length==0){
	// 	timesQuarter3 = ["0"];
	// }
	// if(timesQuarter4.length==0){
	// 	timesQuarter4 = ["0"];
	// }

	var baseChartOption = {
			        title: {
			            text: '时间统计信息柱状图',
			            textStyle: {
			            	color:'#999',
			            	fontSize: 14
			            },
			            subtext: curName
			        },
			        tooltip: {
			            trigger: 'axis'
			        },
			        legend: {
			        	itemWidth: 20,
	        	        itemHeight: 10,
	        	        itemGap: 10,
	        	        right: '45%',
    	                textStyle: {
    	                    fontSize: 12,
    	                    color: '#999'
    	                },
    	                // backgroundColor: "rgb(0,0,0)",
			            data:[
			            	{
			            		name:'平均分',
			            		icon:'rect',
			            	},{
			            		name:'次数',
			            	}
			            ]
			        },
			        toolbox: {
			            show: true,
			            feature: {
			                mark: {show: true},
			                dataView: {show: true, readOnly: false},
			                magicType: {show: true, type: ['line', 'bar']},
			                restore: {show: true},
			                saveAsImage: {show: true}
			            }
			        },
			        calculable: true,
			        xAxis: [
			            {
			                type: 'category',
			                // data : cityArr,
			                data: XAxisArr,
			                axisLabel: {  
			                    interval: 0,//横轴信息全部显示  
			                    rotate: 0,//-30度角倾斜显示 
			                    textStyle: {
			                    	color: 'rgba(0,0,0,0.9)',
			                    	// fontFamily: '微软雅黑,宋体,Arial, Verdana, sans-serif',
			        				fontSize: 14 // 让字体变大
			    				},
								fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
								// formatter:function(value)  
					   //          {  
					   //             return value.split("").join("\n");  
					   //          } 
					   			formatter: function(value){  
			                        // debugger  
			                        var ret = "";//拼接加\n返回的类目项  
			                        var maxLength = 5;//每项显示文字个数  
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
			        yAxis: [
			            {
			                type : 'value'
			            }
			        ],
			        series: [
			            {
			                name: '平均分',
			                type: 'bar',
			                // data:numberArr,
			                data: AvgScoreArr,
			                markPoint: {
			                    data: []
			                },
			                markLine: {
			                    data: []
			                },
			                // 头部显示数据
			                itemStyle: {
			                	normal: {
			                		color: "#5793f3",
			                		// color: function(params){
				                 //        // build a color map as your need.
				                 //        var colorList = [
				                 //          '#EE4775','#E3497F','#DD4B84','#D04B84','#CA4D91','#C04D91','#BF4E9A','#B84FA0','#B04FA0','#AA51AA','#A051AA','#9353BB','#8754C3','#8054C3','#7E56CA','#7056CA','#6F58D7','#685AE1','#605AE1','#5B5BE6','#505BE6','#4F5CF2','#4A5CF2','#455CF2','#405CA2','#3B5CA2','#305CA2'
				                 //        ];
				                 //        return colorList[(params.dataIndex)%(colorList.length)];
				                 //    },
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
			                		label: {
			                			show: true,
			                			position: 'top',
			                			textStyle: {
			                				color: 'rgba(0,0,0,0.7)',
			                				fontSize: 10
			                			},
			                			formatter: function(params){
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
			            // 次数
                        {
                            name: '次数',
                            type: 'bar',
                            // data:numberArr,
                            data: TimesArr,
                            markPoint: {
                                data: []
                            },
                            markLine: {
                                data: []
                            },
                            // 头部显示数据
                            itemStyle: {
                            	normal: {
                            		color: "#d14a61",
                            		// color: function(params){
            	                 //        // build a color map as your need.
            	                 //        var colorList = [
            	                 //          '#EE4775','#E3497F','#DD4B84','#D04B84','#CA4D91','#C04D91','#BF4E9A','#B84FA0','#B04FA0','#AA51AA','#A051AA','#9353BB','#8754C3','#8054C3','#7E56CA','#7056CA','#6F58D7','#685AE1','#605AE1','#5B5BE6','#505BE6','#4F5CF2','#4A5CF2','#455CF2','#405CA2','#3B5CA2','#305CA2'
            	                 //        ];
            	                 //        return colorList[(params.dataIndex)%(colorList.length)];
            	                 //    },
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
                            		label: {
                            			show: true,
                            			position: 'top',
                            			textStyle: {
                            				color: 'rgba(0,0,0,0.7)',
                            				fontSize: 10
                            			},
                            			formatter: function(params){
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
                        }
			        ]
				};

	// 季度
	// var baseChartOptionQuarter = {
	// 				timeline: {
 //                        data: [1,2,3,4],
 //                        label: {
 //                            formatter: function(s) { return "第"+s+"季度"; }
 //                            },
 //                        autoPlay: false,
 //                        playInterval: 1000,
 //                        tooltip: {formatter: function(s) {return "第"+s.value+"季度"; }}
 //                    },
 //                    options: [
 //                    	{
	// 				        title: {
	// 				            text: '时间统计信息柱状图',
	// 				            textStyle: {
	// 				            	color:'#999',
	// 				            	fontSize: 14
	// 				            },
	// 				            subtext: curName
	// 				        },
	// 				        tooltip: {
	// 				            trigger: 'axis'
	// 				        },
	// 				        legend: {
	// 				        	itemWidth: 20,
	// 		        	        itemHeight: 10,
	// 		        	        itemGap: 10,
	// 		        	        right: '4%',
	// 	    	                textStyle: {
	// 	    	                    fontSize: 12,
	// 	    	                    color: '#999'
	// 	    	                },
	// 				            data:[
	// 				            	{
	// 				            		name:'平均分',
	// 				            		icon:'rect',
	// 				            	},{
	// 				            		name:'次数',
	// 				            	}
	// 				            ]
	// 				        },
	// 				        toolbox: {
	// 				            show: true,
	// 				            feature: {
	// 				                mark: {show: true},
	// 				                dataView: {show: true, readOnly: false},
	// 				                magicType: {show: true, type: ['line', 'bar']},
	// 				                restore: {show: true},
	// 				                saveAsImage: {show: true}
	// 				            }
	// 				        },
	// 				        calculable: true,
	// 				        xAxis: [
	// 				            {
	// 				                type: 'category',
	// 				                data: quarter1,
	// 				                axisLabel: {  
	// 				                    interval: 0,//横轴信息全部显示  
	// 				                    rotate: 0,//-30度角倾斜显示 
	// 				                    textStyle: {
	// 				                    	color: 'rgba(0,0,0,0.9)',
	// 				                    	// fontFamily: '微软雅黑,宋体,Arial, Verdana, sans-serif',
	// 				        				fontSize: 14 // 让字体变大
	// 				    				},
	// 									fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
	// 						   			formatter: function(value){  
	// 				                        // debugger  
	// 				                        var ret = "";//拼接加\n返回的类目项  
	// 				                        var maxLength = 5;//每项显示文字个数  
	// 				                        // var maxLength = 2;//每项显示文字个数  
	// 				                        var valLength = value.length;//X轴类目项的文字个数  
	// 				                        var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数  
	// 				                        if (rowN > 1)//如果类目项的文字大于3,  
	// 				                        {  
	// 				                            for (var i = 0; i < rowN; i++) {  
	// 				                                var temp = "";//每次截取的字符串  
	// 				                                var start = i * maxLength;//开始截取的位置  
	// 				                                var end = start + maxLength;//结束截取的位置  
	// 				                                //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧  
	// 				                                temp = value.substring(start, end) + "\n";  
	// 				                                ret += temp; //凭借最终的字符串  
	// 				                            }  
	// 				                            return ret;  
	// 				                        }  
	// 				                        else {  
	// 				                            return value;  
	// 				                        }  
	// 				                 	}
	// 				                },
	// 				                splitLine:{ 
	// 									show:false
	// 								}
	// 				            }
	// 				        ],
	// 				        yAxis: [
	// 				            {
	// 				                type : 'value'
	// 				            }
	// 				        ],
	// 				        series: [
	// 				            {
	// 				                name: '平均分',
	// 				                type: 'bar',
	// 				                data: scoreQuarter1,
	// 				                markPoint: {
	// 				                    data: []
	// 				                },
	// 				                markLine: {
	// 				                    data: []
	// 				                },
	// 				                // 头部显示数据
	// 				                itemStyle: {
	// 				                	normal: {
	// 				                		color: "#5793f3",
	// 					                    barBorderRadius: 4,  //柱状角成椭圆形
	// 				                		label: {
	// 				                			show: true,
	// 				                			position: 'top',
	// 				                			textStyle: {
	// 				                				color: 'rgba(0,0,0,0.7)',
	// 				                				fontSize: 10
	// 				                			},
	// 				                			formatter: function(params){
	// 				                			    if(params.value==0){
	// 				                			        return '';
	// 				                			    }else{
	// 				                			        return params.value;
	// 				                			    }
	// 				                			}
	// 				                		}
	// 				                	},
	// 				                	emphasis: {
	// 				                	    barBorderRadius: 7
	// 				                	},
	// 				                }
	// 				            },
	// 				            // 次数
	// 	                        {
	// 	                            name: '次数',
	// 	                            type: 'bar',
	// 	                            data: timesQuarter1,
	// 	                            markPoint: {
	// 	                                data: []
	// 	                            },
	// 	                            markLine: {
	// 	                                data: []
	// 	                            },
	// 	                            // 头部显示数据
	// 	                            itemStyle: {
	// 	                            	normal: {
	// 	                            		color: "#d14a61",
	// 	            	                    barBorderRadius: 4,  //柱状角成椭圆形
	// 	                            		label: {
	// 	                            			show: true,
	// 	                            			position: 'top',
	// 	                            			textStyle: {
	// 	                            				color: 'rgba(0,0,0,0.7)',
	// 	                            				fontSize: 10
	// 	                            			},
	// 	                            			formatter: function(params){
	// 	                            			    if(params.value==0){
	// 	                            			        return '';
	// 	                            			    }else{
	// 	                            			        return params.value;
	// 	                            			    }
	// 	                            			}
	// 	                            		}
	// 	                            	},
	// 	                            	emphasis: {
	// 	                            	    barBorderRadius: 7
	// 	                            	},
	// 	                            }
	// 	                        }
	// 				        ]
 //                    	},
 //                    	{
 //                    		series: [
 //                    			{data: scoreQuarter2},
 //                    			{data: timesQuarter2},
 //                    		],
 //                    		xAxis: [{data: quarter2}]
 //                    	},
 //                    	{
 //                    		series: [
 //                    			{data: scoreQuarter3},
 //                    			{data: timesQuarter3},
 //                    		],
 //                    		xAxis: [{data: quarter3}]
 //                    	},
 //                    	{
 //                    		series: [
 //                    			{data: scoreQuarter4},
 //                    			{data: timesQuarter4},
 //                    		],
 //                    		xAxis: [{data: quarter4}]
 //                    	}
 //                    ]
	// 			};


		// var baseChartOptionQuarter = {
		// 				baseOption: {
		// 				        timeline: {
		// 				            // y: 0,
		// 				            axisType: 'category',
		// 				            // realtime: false,
		// 				            // loop: false,
		// 				            autoPlay: true,
		// 				            // currentIndex: 2,
		// 				            playInterval: 1000,
		// 				            // controlStyle: {
		// 				            //     position: 'left'
		// 				            // },
		// 				            data: [
		// 				                {
		// 				                    value: '第1季度',
		// 				                    tooltip: {
		// 				                        formatter: '{b} GDP达到一个高度'
		// 				                    },
		// 				                    symbol: 'diamond',
		// 				                    symbolSize: 16
		// 				                },
		// 				                {
		// 				                    value: '第2季度',
		// 				                    tooltip: {
		// 				                        formatter: function (params) {
		// 				                            return params.name + 'GDP达到又一个高度';
		// 				                        }
		// 				                    },
		// 				                    symbol: 'diamond',
		// 				                    symbolSize: 16
		// 				                },
		// 				                {
		// 				                    value: '第3季度',
		// 				                    tooltip: {
		// 				                        formatter: function (params) {
		// 				                            return params.name + 'GDP达到又一个高度';
		// 				                        }
		// 				                    },
		// 				                    symbol: 'diamond',
		// 				                    symbolSize: 16
		// 				                },
		// 				                {
		// 				                    value: '第4季度',
		// 				                    tooltip: {
		// 				                        formatter: function (params) {
		// 				                            return params.name + 'GDP达到又一个高度';
		// 				                        }
		// 				                    },
		// 				                    symbol: 'diamond',
		// 				                    symbolSize: 16
		// 				                },
		// 				            ],
		// 				            label: {
		// 				                formatter : function(s) {
		// 				                    return s.value;
		// 				                }
		// 				            }
		// 				        },
						        
	 //        			        title: {
	 //        			            text: '时间统计信息柱状图',
	 //        			            textStyle: {
	 //        			            	color:'#999',
	 //        			            	fontSize: 14
	 //        			            },
	 //        			            subtext: curName
	 //        			        },
	 //        			        tooltip: {
	 //        			            trigger: 'axis'
	 //        			        },
	 //        			        legend: {
	 //        			        	itemWidth: 20,
	 //        	        	        itemHeight: 10,
	 //        	        	        itemGap: 10,
	 //        	        	        right: '4%',
	 //            	                textStyle: {
	 //            	                    fontSize: 12,
	 //            	                    color: '#999'
	 //            	                },
	 //        			            data:[
	 //        			            	{
	 //        			            		name:'平均分',
	 //        			            		icon:'rect',
	 //        			            	},{
	 //        			            		name:'次数',
	 //        			            	}
	 //        			            ]
	 //        			        },
	 //        			        toolbox: {
	 //        			            show: true,
	 //        			            feature: {
	 //        			                mark: {show: true},
	 //        			                dataView: {show: true, readOnly: false},
	 //        			                magicType: {show: true, type: ['line', 'bar']},
	 //        			                restore: {show: true},
	 //        			                saveAsImage: {show: true}
	 //        			            }
	 //        			        },
	 //        			        calculable: true,
	 //        			        xAxis: [
	 //        			            {
	 //        			                type: 'category',
	 //        			                data: quarter1,
	 //        			                axisLabel: {  
	 //        			                    interval: 0,//横轴信息全部显示  
	 //        			                    rotate: 0,//-30度角倾斜显示 
	 //        			                    textStyle: {
	 //        			                    	color: 'rgba(0,0,0,0.9)',
	 //        			                    	// fontFamily: '微软雅黑,宋体,Arial, Verdana, sans-serif',
	 //        			        				fontSize: 14 // 让字体变大
	 //        			    				},
	 //        								fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
	 //        					   			formatter: function(value){  
	 //        			                        // debugger  
	 //        			                        var ret = "";//拼接加\n返回的类目项  
	 //        			                        var maxLength = 5;//每项显示文字个数  
	 //        			                        // var maxLength = 2;//每项显示文字个数  
	 //        			                        var valLength = value.length;//X轴类目项的文字个数  
	 //        			                        var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数  
	 //        			                        if (rowN > 1)//如果类目项的文字大于3,  
	 //        			                        {  
	 //        			                            for (var i = 0; i < rowN; i++) {  
	 //        			                                var temp = "";//每次截取的字符串  
	 //        			                                var start = i * maxLength;//开始截取的位置  
	 //        			                                var end = start + maxLength;//结束截取的位置  
	 //        			                                //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧  
	 //        			                                temp = value.substring(start, end) + "\n";  
	 //        			                                ret += temp; //凭借最终的字符串  
	 //        			                            }  
	 //        			                            return ret;  
	 //        			                        }  
	 //        			                        else {  
	 //        			                            return value;  
	 //        			                        }  
	 //        			                 	}
	 //        			                },
	 //        			                splitLine:{ 
	 //        								show:false
	 //        							}
	 //        			            }
	 //        			        ],
	 //        			        yAxis: [
	 //        			            {
	 //        			                type : 'value'
	 //        			            }
	 //        			        ],
	 //        			        series: [
	 //        			            {
	 //        			                name: '平均分',
	 //        			                type: 'bar',
	 //        			                data: scoreQuarter1,
	 //        			                markPoint: {
	 //        			                    data: []
	 //        			                },
	 //        			                markLine: {
	 //        			                    data: []
	 //        			                },
	 //        			                // 头部显示数据
	 //        			                itemStyle: {
	 //        			                	normal: {
	 //        			                		color: "#5793f3",
	 //        				                    barBorderRadius: 4,  //柱状角成椭圆形
	 //        			                		label: {
	 //        			                			show: true,
	 //        			                			position: 'top',
	 //        			                			textStyle: {
	 //        			                				color: 'rgba(0,0,0,0.7)',
	 //        			                				fontSize: 10
	 //        			                			},
	 //        			                			formatter: function(params){
	 //        			                			    if(params.value==0){
	 //        			                			        return '';
	 //        			                			    }else{
	 //        			                			        return params.value;
	 //        			                			    }
	 //        			                			}
	 //        			                		}
	 //        			                	},
	 //        			                	emphasis: {
	 //        			                	    barBorderRadius: 7
	 //        			                	},
	 //        			                }
	 //        			            },
	 //        			            // 次数
	 //                                {
	 //                                    name: '次数',
	 //                                    type: 'bar',
	 //                                    data: timesQuarter1,
	 //                                    markPoint: {
	 //                                        data: []
	 //                                    },
	 //                                    markLine: {
	 //                                        data: []
	 //                                    },
	 //                                    // 头部显示数据
	 //                                    itemStyle: {
	 //                                    	normal: {
	 //                                    		color: "#d14a61",
	 //                    	                    barBorderRadius: 4,  //柱状角成椭圆形
	 //                                    		label: {
	 //                                    			show: true,
	 //                                    			position: 'top',
	 //                                    			textStyle: {
	 //                                    				color: 'rgba(0,0,0,0.7)',
	 //                                    				fontSize: 10
	 //                                    			},
	 //                                    			formatter: function(params){
	 //                                    			    if(params.value==0){
	 //                                    			        return '';
	 //                                    			    }else{
	 //                                    			        return params.value;
	 //                                    			    }
	 //                                    			}
	 //                                    		}
	 //                                    	},
	 //                                    	emphasis: {
	 //                                    	    barBorderRadius: 7
	 //                                    	},
	 //                                    }
	 //                                }
	 //        			        ]
		// 				    },

	 //                    options: [
	 //                    	{
		// 				        series: [
	 //                    			{data: scoreQuarter1},
	 //                    			{data: timesQuarter1},
	 //                    		],
	 //                    		xAxis: [{data: quarter1}]
	 //                    	},
	 //                    	{
	 //                    		series: [
	 //                    			{data: scoreQuarter2},
	 //                    			{data: timesQuarter2},
	 //                    		],
	 //                    		xAxis: [{data: quarter2}]
	 //                    	},
	 //                    	{
	 //                    		series: [
	 //                    			{data: scoreQuarter3},
	 //                    			{data: timesQuarter3},
	 //                    		],
	 //                    		xAxis: [{data: quarter3}]
	 //                    	},
	 //                    	{
	 //                    		series: [
	 //                    			{data: scoreQuarter4},
	 //                    			{data: timesQuarter4},
	 //                    		],
	 //                    		xAxis: [{data: quarter4}]
	 //                    	}
	 //                    ]
				        
		// 			};
	// console.log("qaz");
	// console.log(quarter1);
	// console.log(quarter2);
	// console.log(quarter3);
	// console.log(quarter4);
	// console.log(scoreQuarter1);
	// console.log(scoreQuarter2);
	// console.log(scoreQuarter3);
	// console.log(scoreQuarter4);
	// console.log(timesQuarter1);
	// console.log(timesQuarter2);
	// console.log(timesQuarter3);
	// console.log(timesQuarter4);
	// if(Basis=="year" || Basis=="month"){
	// 	myChart.setOption(baseChartOption);
	// }else if(Basis=="week"){
	// 	myChart.setOption(baseChartOptionQuarter);
	// }
	myChart.setOption(baseChartOption);
	/*窗口自适应，关键代码*/  
    setTimeout(function(){
        window.onresize = function(){  
            myChart.resize();
        };
    },200);
}


// 获取员工统计数据
function getStaffAssess(Classify){
	$.ajax({
		type: "GET",
		url: "AssessmentStatistics",
		data: {
			Type: "staff",
			Classify: Classify
		},
		dataType: "json",
		success: function(res){
			// var chart_str = '<div id="'+Classify+'_charts_cont"></div>';
			// $("."+Classify+"_chart").empty().append(chart_str);
			renderStaffAssess(res,Classify);
		},
		error: function(){
			$.MsgBox_Unload.Alert("提示","服务器繁忙！");
		},
		complete: function(XMLHttpRequest, textStatus){
			if(textStatus == "success"){

			}
		}
	});
}

// 获取时间统计数据
function getTimeAssessY(Basis){
	$.ajax({
		type: "GET",
		url: "AssessmentStatistics",
		data: {
			Type: "time",
			Basis: Basis
		},
		dataType: "json",
		success: function(res){
			// var chart_str = '<div id="'+Basis+'_charts_cont"></div>';
			// $("."+Basis+"_chart").empty().append(chart_str);
			renderTimeAssess(res,Basis);
		},
		error: function(){
			$.MsgBox_Unload.Alert("提示","服务器繁忙！");
		},
		complete: function(XMLHttpRequest, textStatus){
			if(textStatus == "success"){

			}
		}
	});
}

// 获取时间统计数据--月周
function getTimeAssessMW(Basis,year){
	$.ajax({
		type: "GET",
		url: "AssessmentStatistics",
		data: {
			Type: "time",
			Basis: Basis,
			Year: year
		},
		dataType: "json",
		success: function(res){
			// var chart_str = '<div id="'+Basis+'_charts_cont"></div>';
			// $("."+Basis+"_chart").empty().append(chart_str);
			renderTimeAssess(res,Basis);
		},
		error: function(){
			$.MsgBox_Unload.Alert("提示","服务器繁忙！");
		},
		complete: function(XMLHttpRequest, textStatus){
			if(textStatus == "success"){

			}
		}
	});
}

function Initializtion(){
	getStaffAssess("All");
}

/**************页面加载完成*****************/
$(function(){
	Initializtion();
	$(window).trigger("resize");
	YMSel();
	$("#year_sel").val(iyear);
	// $("#month_sel").val("All");
});


/***
* Events Listener
*/
$(document).on("click",".statistics_staff_tab",function(){
	$(this).attr("id","current_tab").siblings().attr("id","");
	$(".g_container_body_info_l").text("按成员统计信息");
	// 按钮
	$("#year_btn, #month_btn, #week_btn").hide();
	$("#All_btn, #Eoulu_btn, #Formfactor_btn, #Experiment_btn").show();
	$(".g_container_body_info_r [id$='_btn']").removeClass("btn-success").addClass("btn-info");
	$("#All_btn").removeClass("btn-info").addClass("btn-success");
	// 图表
	$("[class$='_chart']").hide();
	$(".All_chart").show();
	// 日期
	$(".g_container_info_r").hide();
	getStaffAssess("All");
});
$(document).on("click",".statistics_time_tab",function(){
	$(this).attr("id","current_tab").siblings().attr("id","");
	$(".g_container_body_info_l").text("按时间统计信息");
	$("#All_btn, #Eoulu_btn, #Formfactor_btn, #Experiment_btn").hide();
	$("#year_btn, #month_btn, #week_btn").show();
	$(".g_container_body_info_r [id$='_btn']").removeClass("btn-success").addClass("btn-info");
	$("#year_btn").removeClass("btn-info").addClass("btn-success");
	$("[class$='_chart']").hide();
	$(".year_chart").show();
	$(".g_container_info_r").hide();
	getTimeAssessY("year");
});

$(".g_container_body_info_r button").on("click",function(){
	var curId = $("#current_tab").attr("class");
	var classify = $(this).attr("id").replace("_btn","");
	$("[class$='_chart']").hide();
	$("."+classify+"_chart").show();
	if(curId == "statistics_staff_tab"){
		getStaffAssess(classify);
	}else if(curId == "statistics_time_tab"){
		if(classify == "month" || classify == "week"){
			$(".g_container_info_r").show();
			$("#year_sel").val(iyear);
			var yearVal = $("#year_sel").val();
			getTimeAssessMW(classify,yearVal);
		}else if(classify == "year"){
			$(".g_container_info_r").hide();
			getTimeAssessY(classify);
		}
	}
	$(".g_container_body_info_r [id$='_btn']").removeClass("btn-success").addClass("btn-info");
	$(this).removeClass("btn-info").addClass("btn-success");
});

$(window).on("resize",function(){
	var iw = $(".g_container_body_table").width();
	$('.g_container_body_table [id$="_charts_cont"]').css({"width": iw+"px"});
});

$("#year_sel").on("change",function(){
	var yearVal = $(this).val();
	var classify = $("button.btn-success").attr("id").replace("_btn","");
	if(classify == "month" || classify == "week"){
		getTimeAssessMW(classify,yearVal);
	}
});

/*
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
*/