/**
 * Created by eoulu on 2017/4/20.
 */

//时间下拉框
$(function(){
	
var curLocation = window.location.href;
var curLocationBool = curLocation.indexOf("?Year=")>-1?true:false;
var currentYear = parseInt($(".currentYear").html());
var currentMonth = parseInt($(".currentMonth").html());
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
		str1 += '<option value ="" class="opd" text="'+(2016+i)+'">'+(2016+i)+'</option>';
	}
	console.log(str1);
	strD = '<select  class="selD">'+str1+'</select>';
    $(".searchBoxContentD").append(strD);
    if(curLocationBool==false){
        $(".searchBoxContentD").find('option[text="' + currentYear + '"]').prop("selected", true);
    }else{
        var trueY = curLocation.split("?Year=")[1].split("&Month")[0];
        $(".searchBoxContentD").find('option[text="' + trueY + '"]').prop("selected", true);
    }
	
//月份的下拉框	 刚进来默认是2016年所以显示12个月
	var str2 = '';
	var strM = '';
	for(var i = 1;i<=12;i++){
		  str2 += '<option value ="" class="opm" text="'+(i)+'月">'+(i)+'月</option>'
	}
	strM = '<select class="selM">'+
	   '<option value ="" class="opm" text="所有">所有</option>'+
	     str2
	   +'</select>' 
	$(".searchBoxContentM").empty();
	$(".searchBoxContentM").append(strM);
	$(".searchBoxContentM").find('option[text="' + Month + '"]').prop("selected", true);
})
/*var LM = currentMonth;
//测试完注释
var LM = 4;
var str2 = '';
var strM = '';
	var mydate = $(".searchBoxContentD option:selected").html();
	alert(mydate);
	if(mydate == currentYear){
		for(var i = 1;i<=LM;i++){
			  str2 += '<option value ="" class="opm">'+(i)+'月</option>'
		}
		console.log(str2);
	}else{
		alert(444)
		for(var i = 1;i<=12;i++){
			  str2 += '<option value ="" class="opm">'+(i)+'月</option>'
		}
		console.log(str2);
	}
	strM = '<select class="selM">'+
		   '<option value ="" class="opm">所有</option>'+
	  str2
	'</select>' 
	  $(".searchBoxContentM").empty('');
	 $(".searchBoxContentM").append(strM);*/	

var currentYear = parseInt($(".currentYear").html());
var currentMonth = parseInt($(".currentMonth").html());
var str3 = '';
var LM = currentMonth;
$(".searchBoxContentD .selD").blur(function(){
	var mydate = $(".searchBoxContentD option:selected").html();
	$(".searchBoxContentM .selM").remove();
	if(mydate == currentYear){
		for(var i = 1;i<=LM;i++){
			  str3 += '<option value ="" class="opm" text="'+(i)+'月">'+(i)+'月</option>'
		}
		console.log(str3);
	}else{
		for(var i = 1;i<=12;i++){
			  str3 += '<option value ="" class="opm" text="'+(i)+'月">'+(i)+'月</option>'
		}
		console.log(str3);
	}
	strM = '<select class="selM">'+
		   '<option value ="" class="opm" text="所有">所有</option>'+
	  str3
	  + '</select>'
	  $(".searchBoxContentM .selM").remove();
	 $(".searchBoxContentM").append(strM);	  
})

//sousuo
var btnNum = 0;
$("#btn").click(function () {
	//
    if (btnNum == 0) {
        $(this).val('柱状图');
        $(".pContentleft1").fadeOut(500);
        $(".pContentleft2").fadeIn(500);
        btnNum = 1;
        var dom = document.getElementById("picture1");
        var realData=[[10, 11, 5],[2, 3, 1]];
        Area(dom,realData);
    }
    else if (btnNum == 1) {
        $(this).val('地图');
        $(".pContentleft2").fadeOut(500);
        $(".pContentleft1").fadeIn(500);
        $('body').css("background", "#fff");
        $(".public-top").css("color", "#000");
        btnNum = 0;
    }
});

$('.nav ul li').click(function () {
    if($(this).find('a').html()=='销售信息'){
        $(".statistics-export").hide();
    	$(this).addClass('currentView');
    	$(this).siblings('li').removeClass('currentView');
    	$(".pContentleft1").fadeOut(500);
        $(".pContentleft2").fadeIn(500);
        $('#picture2').show();
        $('#picture1').hide();
        $('.pC-right').hide();
        $(".PriceDataTable").hide();
        var dom = document.getElementById("picture2");
        var realData=new Array(2);
        realData=[salesData[0],dealData(salesData[1])]
        Sales(dom,realData);
    }else if($(this).find('a').html()=='合同信息'){
        $(".statistics-export").hide();
    	 $(this).addClass('currentView');
    	 $(this).siblings('li').removeClass('currentView');
    	 $(".pContentleft1").fadeIn(500);
         $(".pContentleft2").fadeOut(500);
         $('#picture2').hide();
         $('#picture1').show();
         $('.pC-right').show();
         $(".PriceDataTable").hide();
    }else if($(this).find('a').html()=='个人成单率'){
        $(".statistics-export").hide();
    	$(this).addClass('currentView');
    	$(this).siblings('li').removeClass('currentView');
    	$(".pContentleft1").fadeOut(500);
        $(".pContentleft2").fadeIn(500);
        $('#picture2').show();
        $('#picture1').hide();
        $('.pC-right').hide();
        $(".PriceDataTable").hide();
	     var dom = document.getElementById("picture2");
	     var realData=new Array(2);
	     realData=[chengdanlvData[0],dealData(chengdanlvData[1])]
	     Person(dom,realData);
    }
    else if($(this).find('a').html()=='月信息'){
    	console.log("in");
        $(".statistics-export").show();
    	$(this).addClass('currentView');
    	$(this).siblings('li').removeClass('currentView');
    	$(".pContentleft1").fadeOut(500);
        $(".pContentleft2").fadeOut(500);
        $('#picture2').hide();
        $('#picture1').hide();
        $('.pC-right').hide();
        $(".PriceDataTable").show();
    }
});

/*$('.sales').click(function () {
    if($(this).find('span').html()=='销售信息'){
        $(this).find('span').html('合同信息');
        $(".pContentleft1").fadeOut(500);
        $(".pContentleft2").fadeIn(500);
        $('#picture2').show();
        $('#picture1').hide();
        $('.pC-right').hide();
        var dom = document.getElementById("picture2");
        var realData=salesData;;
        Sales(dom,realData);
    }else{
        $(this).find('span').html('销售信息');
        $(".pContentleft1").fadeIn(500);
        $(".pContentleft2").fadeOut(500);
        $('#picture2').hide();
        $('#picture1').show();
        $('.pC-right').show();
    }
});*/
function Contact(dom,data) {
    var rest=[];
        for (var i = 0; i < data[0].length; i++) {
            rest[i] = (data[0][i]-data[1][i]).toFixed(2);
            if(rest[i]<0){
                rest[i]=0;
            }
        }
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        title : {
            text: '合同签订情况'
        },
        legend: {
            data: ['未完成', '已完成','总目标']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                // 'stack', 
                magicType: {show: true, type: ['line', 'bar', 'tiled']},
                restore: {show: true}
                //		            saveAsImage : {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'value',
                name: '业绩',
                nameTextStyle:{
                    fontFamily:'微软雅黑'
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        fontSize: 18
                    },
                    formatter: '{value} M'
                }
            }
        ],
        yAxis: [
            {
                type: 'category',
                name: '区域',
                data: ['北方', '南方', '西南'],
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: 'red',
                        fontSize: 26
                    },
                }
            }
        ],
        series: [
            {
                name: '未完成',
                type: 'bar',
                // stack: '总量',
                tiled: '总量',
                itemStyle: {normal: {label: 
                    {show: true, position: 'right',
                    textStyle: {
                        color:'rgba(0,0,0,0.9)',
                        fontSize:10
                    }
                    }
                    }
                },
                data: rest
            },
            {
                name: '已完成',
                type: 'bar',
                // stack: '总量',
                tiled: '总量',
                itemStyle: {normal: {label: 
                    {show: true, position: 'right',
                    textStyle: {
                        color:'rgba(0,0,0,0.9)',
                        fontSize:10
                    }
                    }
                    }
                },
                data: data[1]
            },{
                name: '总目标',
                type: 'bar',
                // stack: '总量',
                tiled: '总量',
                itemStyle: {normal: {label: 
                    {show: true, position: 'right',
                    textStyle: {
                        color:'rgba(0,0,0,0.9)',
                        fontSize:10
                    }
                    }
                    }
                },
                data: data[0]
            }
        ]
    };

    myChart.setOption(option);
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
        window.onresize = myChart.resize;
    }
}
function Sales(dom,data) {
    dom.innerHTML = '';
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        title : {
            text: '销售情况'
        },
        legend: {
            data: ['销售量']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                data: data[0],
                name: '销售人员'
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '业绩',
                nameTextStyle:{
                    fontFamily:'微软雅黑'
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: 'red',
                        fontSize: 26
                    },
                    formatter: '{value} M'
                }
            }
        ],
        series: [
           {
                name: '销售量',
                type: 'bar',
                itemStyle: {normal: {label: {show: true}}},
                data: data[1]
            }
        ]
    };
    myChart.setOption(option);
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
        window.onresize = myChart.resize;
    }
}
function Area(dom,data){
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    option = {
        tooltip: {
            trigger: 'item',
            formatter: function (params) {
                var MAP_VALUE_DIC = {
                    '1': '10M',
                    '2': '2M',
                    '4': '3M'
                };
                if (params.seriesType) {
                    return params.name + ': ' + MAP_VALUE_DIC[params.value];
                } else {
                    return params.name;
                }
            }
        },

        visualMap: {
            type: 'piecewise',
            splitNumber: 3,
            piecewise: [

                {
                    value: 1,
                    label: '北方',
                    color: 'orangered'
                }, {
                    value: 2,
                    label: '南方',
                    color: 'red'
                }, {
                    value: 4,
                    label: '西南',
                    color: 'lightskyblue'
                }

            ],
            min: 0,
            max: 4,
            itemWidth: 40,
            itemHeight: 40,
            align: 'left',
            left: 'left',
            top: 'middle',
            calculable: true,
            show: true,
            seriesIndex: 0,
            inRange: {
                color: ['#0092d7', '#92dce0', '#4ca8a1'],
                symbolSize: [30, 100]
            },
            formatter: function (value) {
                if (value > 2 && value < 4) {
                    return '西南（已完成目标：80%）';
                } else if (value > 1 && value < 2) {
                    return '南方（已完成目标：85%）';
                } else {
                    return '北方（已完成目标：86%）';
                }
            },
            textStyle: {
                color: 'darkcyan',
                fontSize: 20
            }
        },
        series: [{
            name: '中国',
            type: 'map',
            mapType: 'china',

            label: {
                normal: {
                    show: false
                },
                emphasis: {
                    show: false
                }
            },
            data: []

        }, {
            name: '中国',
            type: 'map',
            mapType: 'china',
            selectedMode: 'multiple',
            label: {
                normal: {
                    show: false
                },
                emphasis: {
                    show: true
                }
            },
            data: [],
            markLine: {
                smooth: true,
                symbol: ['none', 'circle'],
                symbolSize: 1,
                itemStyle: {
                    normal: {
                        color: '#c23531',
                        borderWidth: 1,
                        borderColor: 'rgba(30,144,255,0.5)'
                    }
                }

            }
        }, {
            name: '目标',
            type: 'map',
            mapType: 'china',
            label: {
                normal: {
                    show: false
                },
                emphasis: {
                    show: true
                }
            },

            data: [
                {
                name: '重庆',
                value: 4
            }, 
                {
                name: '云南',
                value: 4
            }, {
                name: '辽宁',
                value: 4
            }, {
                name: '黑龙江',
                value: 4
            }, {
                name: '广西',
                value: 4
            }, {
                name: '甘肃',
                value: 4
            }, {
                name: '陕西',
                value: 4
            }, {
                name: '吉林',
                value: 4
            }, {
                name: '贵州',
                value: 4
            }, {
                name: '新疆',
                value: 4
            }, {
                name: '青海',
                value: 4
            }, {
                name: '宁夏',
                value: 4
            }, {
                name: '台湾',
                value: 2
            }, {
                name: '香港',
                value: 2
            }, {
                name: '上海',
                value: 2
            }, {
                name: '安徽',
                value: 4
            }, {
                name: '江苏',
                value: 2
            }, {
                name: '浙江',
                value: 2
            }, {
                name: '北京',
                value: 1
            }, {
                name: '天津',
                value: 1
            }, {
                name: '河北',
                value: 1
            }, {
                name: '河南',
                value: 4
            }, {
                name: '内蒙古',
                value: 4
            }, {
                name: '湖南',
                value: 2
            }, {
                name: '山东',
                value: 4
            }, {
                name: '江西',
                value: 2
            }, {
                name: '湖北',
                value: 2
            }, {
                name: '福建',
                value: 2
            }, {
                name: '广东',
                value: 2
            }, {
                name: '西藏',
                value: 4
            }, {
                name: '四川',
                value: 4
            }, {
                name: '山西',
                value: 2
            }, {
                name: '海南',
                value: 2
            }]
        }]
    };
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
        window.onresize = myChart.resize;
    }
}

//个人成单率
function Person(dom,data) {
    dom.innerHTML = '';
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        title : {
            text: '个人成单率'
        },
        legend: {
            data: ['成单率']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                data: data[0],
                name: '销售人员'             
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '成单率',
                nameTextStyle:{
                    fontFamily:'微软雅黑'
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: 'red',
                        fontSize: 26
                    },
                    formatter: '{value} %'
                }
            }
        ],
        series: [
           {
                name: '成单率',
                type: 'bar',
                itemStyle: {normal: {label: {show: true}}},
                data: data[1]
            }
        ]
    };
    myChart.setOption(option);
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
        window.onresize = myChart.resize;
    }
}
/*保留2位小数*/
function dealData(data){
	var allData=[];
	for(var i=0;i<data.length;i++){
		data[i].toFixed(2);
		allData.push(data[i].toFixed(2))
    }
	return allData;
}

/*判断左边显示图形*/
function Judge(data){
	for(var i=0;i<data[0].length;i++){
		if(i==0){
			if(data[1][i]/data[0][i]>=1){
				$('.pC-left ul').append('<li><img src="image/gsbg4.gif"/></li>')
			}else if(data[1][i]/data[0][i]>=0.5){
				$('.pC-left ul').append('<li><img src="image/gsbg8.gif"/></li>')

			}else{
				$('.pC-left ul').append('<li><img src="image/gsbg3.gif"/></li>')
			}
		}else if(i==1){
			if(data[1][i]/data[0][i]>=1){
				$('.pC-left ul').append('<li><img src="image/20.gif"/></li>')
			}else if(data[1][i]/data[0][i]>=0.5){
				$('.pC-left ul').append('<li><img src="image/10.gif"/></li>')

			}else{
				$('.pC-left ul').append('<li><img src="image/9.gif"/></li>')
			}
		}
		else if(i==2){
			if(data[1][i]/data[0][i]>=1){
				$('.pC-left ul').append('<li><img src="image/22.gif"/></li>')
			}else if(data[1][i]/data[0][i]>=0.5){
				$('.pC-left ul').append('<li><img src="image/5.gif"/></li>')

			}else{
				$('.pC-left ul').append('<li><img src="image/jy.gif"/></li>')
			}
		}
	}
}

//初始化页面
$(function(){
	//合同
    var dom = document.getElementById("picture");
//    var realData=[[10, 11, 5],[2, 10, 3]];
    var realData=new Array(2);
    realData=[dealData(areaData[0]),dealData(areaData[1])]
    Judge(areaData)
    Contact(dom,realData);

    // 导出月信息
    $(document).on("click",".export-current",function(){
        var year = $(".searchBoxContentD option:selected").html().toString();
        // alert(year);
        window.location.href = 'ExportStatistics?year='+ year;
    });
    $(document).on("click",".export-all",function(){
        window.location.href = 'ExportStatistics?year=All';
    });
});