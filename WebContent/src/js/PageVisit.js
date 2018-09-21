/*变量函数定义*/
var visitPageData = {
	"物流部": {},
	"商务部": {},
	"服务部": {},
	"软件部": {},
	"人事部": {},
	"销售部": {},
	"实验室": {}
};
var pageArr1 = [];
var pageArr2 = [];
var pageArr3 = [];
var pageArr4 = [];
var pageArr5 = [];
var pageArr6 = [];
var pageArr7 = [];
var timesArr1 = [];
var timesArr2 = [];
var timesArr3 = [];
var timesArr4 = [];
var timesArr5 = [];
var timesArr6 = [];
var timesArr7 = [];
var barOption = {
                title: {
                    text: '',
                    textStyle: {
                    	color:'#999',
                    	fontSize: 14
                    },
                    subtext: ''
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data:['','']
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
                        type : 'category',
                        data : [],
                        axisLabel:{  
                            interval: 0,//横轴信息全部显示  
                            rotate: 0,//-30度角倾斜显示 
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
                                 var maxLength = 2;//每项显示文字个数  
                                 if(regAllEn.test(value)){
                                 	maxLength = 5;
                                 }else if(value.toString().length>4){
									maxLength = 3;
                                 }
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
                        splitLine: { 
							show: false
						}
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        name: '次数',
                        type: 'bar',
                        // data:numberArr,
                        data:[],
                        markPoint: {
                            data: []
                        },
                        markLine: {
                            data: []
                        },
                        // 头部显示数据
                        itemStyle: {
                        	normal: {
                        		color: function(params) {
    		                        // build a color map as your need.
    		                        // var colorList = [
    		                        //   '#EE4775','#E3497F','#DD4B84','#D04B84','#CA4D91','#C04D91','#BF4E9A','#B84FA0','#B04FA0','#AA51AA','#A051AA','#9353BB','#8754C3','#8054C3','#7E56CA','#7056CA','#6F58D7','#685AE1','#605AE1','#5B5BE6','#505BE6','#4F5CF2','#4A5CF2','#455CF2','#405CA2','#3B5CA2','#305CA2'
    		                        // ];
    		                        return globalColorList[(params.dataIndex)%(globalColorList.length)];
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
                        		label: {
                        			show: true,
		                			position: 'top',
		                			textStyle: {
		                				color: 'rgba(0,0,0,0.7)',
		                				fontSize: 11
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
                  //       label: {
		                // 	normal:{
		                // 		show:true,
		                // 		position:'top',
		                // 		textStyle: {
		                // 			color:'black'
		                // 		}
		                // 	}
		                // }
                    },
                ]
    		};

function renderEcharts(iindex,pageArr,timesArr,text,subText){
	$(window).trigger("resize");
	var ichart = echarts.init(document.getElementById('bar_statis_'+iindex));
	barOption.xAxis[0].data = pageArr;
	barOption.series[0].data = timesArr;
	barOption.title.text = text;
	barOption.title.subtext = subText;
	if (barOption && typeof barOption === "object") {
	    ichart.setOption(barOption, true);
	}
	/*窗口自适应，关键代码*/  
	setTimeout(function(){
	    window.onresize = function(){  
	        ichart.resize();
	    };
	},150);
}

/*页面加载完成*/
$(function(){
	$.ajax({
		type: "GET",
		url: "PageVisit",
		data: {
			LoadType: "data"
		},
		dataType: "json"
	}).then(function(data){
		if(data.length == 1){
			$.MsgBox_Unload.Alert("提示", "无数据或后台正在保存数据！");
		}else if(data.length > 1){
			data.map(function(currentValue,index,arr){
				if(index>0){
					for(var kk in visitPageData){
						if(kk == currentValue.Department){
							var ipage = currentValue.PageName;
							visitPageData[kk][ipage] = currentValue.VisitingTime;
							break;
						}
					}
				}
			});

			$.each(visitPageData, function(name,value){
				$.each(value, function(name1,value1){
					if(name == "物流部"){
						pageArr1.push(name1);
						timesArr1.push(value1);
					}else if(name == "商务部"){
						pageArr2.push(name1);
						timesArr2.push(value1);
					}else if(name == "服务部"){
						pageArr3.push(name1);
						timesArr3.push(value1);
					}else if(name == "软件部"){
						pageArr4.push(name1);
						timesArr4.push(value1);
					}else if(name == "人事部"){
						pageArr5.push(name1);
						timesArr5.push(value1);
					}else if(name == "销售部"){
						pageArr6.push(name1);
						timesArr6.push(value1);
					}else if(name == "实验室"){
						pageArr7.push(name1);
						timesArr7.push(value1);
					}
				});
			});


			for(var kkk in visitPageData){
				if(kkk == "物流部"){
					renderEcharts("0",pageArr1,timesArr1,"物流部","页面访问次数统计");
				}else if(kkk == "商务部"){
					renderEcharts("1",pageArr2,timesArr2,"商务部","页面访问次数统计");
				}else if(kkk == "服务部"){
					renderEcharts("2",pageArr3,timesArr3,"服务部","页面访问次数统计");
				}else if(kkk == "软件部"){
					renderEcharts("3",pageArr4,timesArr4,"软件部","页面访问次数统计");
				}else if(kkk == "人事部"){
					renderEcharts("4",pageArr5,timesArr5,"人事部","页面访问次数统计");
				}else if(kkk == "销售部"){
					renderEcharts("5",pageArr6,timesArr6,"销售部","页面访问次数统计");
				}else if(kkk == "实验室"){
					renderEcharts("6",pageArr7,timesArr7,"实验室","页面访问次数统计");
				}
			}
		}
	},function(){
		$.MsgBox_Unload.Alert("提示", "服务器繁忙，请稍后重试！");
	});
});

/*事件监听*/
// 窗口resize
$(window).on("resize",function(){
	var iw = $(".bar_statistics_wrapper").width() - 10;
	$("div[class$='_statistics_wrapper'] div[class$='_cont']").css({"width": iw+"px"});
});