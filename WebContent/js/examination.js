/*****函数定义与变量定义*****/
var multiSelectOption = {
		disableIfEmpty: true,
		disabledText: '未选择',
		delimiterText: '; ',
		enableFiltering: true,
        filterPlaceholder: '搜索',
		includeResetOption: true,
		includeResetDivider: true,
		includeSelectAllOption: true,
        enableClickableOptGroups: true,
        enableCollapsibleOptGroups: true,
        collapseOptGroupsByDefault: true,
        // selectAllJustVisible: false,
		// selectAllText: '选择所有人员!',
		resetText: "重置",
		maxHeight: 250,
		buttonWidth: '360px',
        numberDisplayed: 4,
		onChange: function(option, checked, select) {
        },
        onDropdownHide: function(event) {
        },
        optionClass: function(element) {
            var value = $(element).index();
            if (value%2 == 0) {
                return 'even';
            }
            else {
                return 'odd';
            }
        },
	};

// echarts配置
// var departmentIcons = {
//     '人事部': 'image/index/department0.png',
//     '商务部': 'image/index/department1.png',
//     '市场部': 'image/index/department2.png'
// };

var seriesLabel = {
    normal: {
        show: true,
        textBorderColor: '#333',
        textBorderWidth: 2
    }
};

var examChartOption = {
    title: {
        text: '',
        textStyle: {
            color:'#888',
            fontSize: 15
        },
        subtext: ''
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    legend: {
        data: ['已完成', '未完成']
    },
    grid: {
        left: 100
    },
    toolbox: {
        show: true,
        feature: {
            // mark: {show: true},
            dataView: {show: true, readOnly: false},
            magicType: {show: true, type: ['line', 'bar']},
            // magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
            restore: {show: true},
            saveAsImage: {show: true},
            // brush: {type: ['rect', 'polygon', 'lineX', 'lineY', 'keep', 'clear']}
        }
    },
    xAxis: {
        type: 'value',
        name: '人数',
        minInterval : 1,
        axisLabel: {
            textStyle: {
                color: 'rgba(0,0,0,0.9)',
                // fontFamily: '微软雅黑,宋体,Arial, Verdana, sans-serif',
                fontSize: 16 // 让字体变大
            },
            formatter: '{value}'
        }
    },
    yAxis: {
        type: 'category',
        inverse: true,
        data: [],
        axisLabel: {
            textStyle: {
                color: 'rgba(0,0,0,0.9)',
                // fontFamily: '微软雅黑,宋体,Arial, Verdana, sans-serif',
                fontSize: 13 // 让字体变大
            },
            formatter: function (value) {
                // return '{' + value + '| }\n{value|' + value + '}';
                var ret = "";//拼接加\n返回的类目项  
                var maxLength = 3;//每项显示文字个数  
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
            },
            margin: 5,
            // rich: {
            //     value: {
            //         lineHeight: 30,
            //         align: 'center'
            //     },
            //     "人事部": {
            //         height: 40,
            //         align: 'center',
            //         backgroundColor: {
            //             image: departmentIcons.Sunny
            //         }
            //     },
            //     "商务部": {
            //         height: 40,
            //         align: 'center',
            //         backgroundColor: {
            //             image: departmentIcons.Cloudy
            //         }
            //     },
            //     "市场部": {
            //         height: 40,
            //         align: 'center',
            //         backgroundColor: {
            //             image: departmentIcons.Showers
            //         }
            //     }
            // }
        }
    },
    series: [
        {
            name: '已完成',
            type: 'bar',
            data: [],
            label: seriesLabel,
            itemStyle: {
                normal: {
                    // color:function(d){return "#"+Math.floor(Math.random()*(256*256*256-1)).toString(16);}
                    color: function(params) {
                        // console.log(params);
                        // build a color map as your need.
                        // var colorList = [
                        //   '#DE3656','#D6395B','#C93F64','#C0436C','#B64873','#A35283','#9D5588','#885F98','#7468A6','#6C6BA6','#5F72B6','#5A74B9','#5077BE','#4B7BC5','#467DC9','#4081CE','#3A83D3','#3183DA','#2A8BDF','#248EE3','#1E91E8','#1993EC','#1396F0','#0F98F4','#0B9AF7','#069CFA','#019FFF'
                        // ];
                        if(params.name == "汇总"){
                            return "#C93F64";
                        }else{
                            return "#3fb1e3";
                        }
                        // return colorList[(params.dataIndex)%(colorList.length)];
                    },
                    barBorderRadius: 3,  //柱状角成椭圆形
                    // label:{
                    //     show:true,
                    //     position:'top',
                    //     textStyle: {
                    //         color:'rgba(0,0,0,0.8)',
                    //         fontSize:13
                    //     },
                    //     formatter:function(params){
                    //         if(params.value==0){
                    //             return '';
                    //         }else{
                    //             return params.value;
                    //             }
                    //     }
                    // }
                },
            },
            // markPoint: {
            //     symbolSize: 1,
            //     symbolOffset: [0, '50%'],
            //     label: {
            //        normal: {
            //             formatter: '{a|{a}\n}{b|{b} }{c|{c}}',
            //             backgroundColor: 'rgb(242,242,242)',
            //             borderColor: '#aaa',
            //             borderWidth: 1,
            //             borderRadius: 4,
            //             padding: [4, 10],
            //             lineHeight: 26,
            //             // shadowBlur: 5,
            //             // shadowColor: '#000',
            //             // shadowOffsetX: 0,
            //             // shadowOffsetY: 1,
            //             position: 'right',
            //             distance: 20,
            //             rich: {
            //                 a: {
            //                     align: 'center',
            //                     color: '#fff',
            //                     fontSize: 18,
            //                     textShadowBlur: 2,
            //                     textShadowColor: '#000',
            //                     textShadowOffsetX: 0,
            //                     textShadowOffsetY: 1,
            //                     textBorderColor: '#333',
            //                     textBorderWidth: 2
            //                 },
            //                 b: {
            //                      color: '#333'
            //                 },
            //                 c: {
            //                     color: '#ff8811',
            //                     textBorderColor: '#000',
            //                     textBorderWidth: 1,
            //                     fontSize: 22
            //                 }
            //             }
            //        }
            //     }
            //     // data: [
            //     //     {type: 'max', name: 'max days: '},
            //     //     {type: 'min', name: 'min days: '}
            //     // ]
            // }
        },
        {
            name: '未完成',
            type: 'bar',
            label: seriesLabel,
            data: [],
            itemStyle: {
                normal: {
                    color: function(params) {
                        if(params.name == "汇总"){
                            return "#4B7BC5";
                        }else{
                            return "#6be6c1";
                        }
                    },
                    barBorderRadius: 3,  //柱状角成椭圆形
                },
            },
        }
    ]
};


var addSubmitObj = new Object();
addSubmitObj.Item = null;
addSubmitObj.Type = null;
addSubmitObj.ID = null;
addSubmitObj.SerialNumber = null;
addSubmitObj.Subject = null;
addSubmitObj.Number = null;
// addSubmitObj.Title = null;
addSubmitObj.Time = null;
// addSubmitObj.Classify = null;
var updateSubmitObj = new Object();
updateSubmitObj.Item = null;
updateSubmitObj.Type = null;
updateSubmitObj.ID = null;
updateSubmitObj.SerialNumber = null;
updateSubmitObj.Subject = null;
updateSubmitObj.Number = null;
// updateSubmitObj.Title = null;
updateSubmitObj.Time = null;
// updateSubmitObj.Classify = null;

var initEchartsHook = 0;
var ichart;
var subjectArr = [];
// 分页切换标志
var presentSwitchClassify;
// 更改状态标志
var changeState = 0;
// 定义部门与考试者的Obj
var department2ExamierObj = {};
var curNoticeIndex;
// 遮罩层宽高
function setCoverWH(){
	var ww= $("#NonStandard_sticker").width();
	var hh= $("#NonStandard_sticker").height();
	$(".bg_cover, .cover_bg2").css({"width":ww+"px","height":hh+"px"});
}

// 分数可录入属性init
function scoreContenteditable(flag){
    $(".exam_detail_more_detable .hastd_details_Score").prop("contenteditable",flag);
}

function pageStyle(currentPage,pageCounts){
    if(pageCounts == 1){
        $("#fistPage").attr("disabled","disabled");
        $("#upPage").attr("disabled","disabled");
        $("#nextPage").attr("disabled","disabled");
        $("#lastPage").attr("disabled","disabled");
        buttonDisabled("#fistPage, #upPage, #nextPage, #lastPage");
    }else if(currentPage == 1){
        $("#fistPage").attr("disabled","disabled");
        $("#upPage").attr("disabled","disabled");
        $("#lastPage").attr("disabled",false);
        $("#nextPage").attr("disabled",false);
        buttonDisabled("#fistPage, #upPage");
        buttonAbled("#nextPage, #lastPage");
    }else if(currentPage == pageCounts){
        $("#lastPage").attr("disabled","disabled");
        $("#nextPage").attr("disabled","disabled");
        $("#fistPage").attr("disabled",false);
        $("#upPage").attr("disabled",false);
        buttonDisabled("#nextPage, #lastPage");
        buttonAbled("#fistPage, #upPage");
    }else{
        $("#lastPage, #nextPage, #fistPage, #upPage").attr("disabled",false);
        buttonAbled("#lastPage, #nextPage, #fistPage, #upPage");
    }
}
function buttonDisabled(iClass) {
    return $(iClass).removeClass("buttonDisabled buttonAbled").addClass("buttonDisabled");
}
function buttonAbled(jClass) {
    return $(jClass).removeClass("buttonDisabled buttonAbled").addClass("buttonAbled");
}

// 根据科目返回试题
function getQuesBySubject(Subject, fn, fn2){
    $.ajax({
        type: "GET",
        url: "ExaminationDetails",
        data: {
            Type: "number",
            // Type: "select",
            Subject: Subject
        },
        dataType: "json"
    }).then(function(data, textStatus, jqXHR){
        fn && fn(data);
    },function(jqXHR, textStatus, errorThrown){
        $.MsgBox_Unload.Alert("提示","该科目无相应试题数据！");
    }).always(function(){
        fn2 && fn2();
    });
}

function setChartsContH(DepartmentArr){
    var iLen = DepartmentArr.length;
    var iH = iLen * 65 + 60;
    $("#exam_statis_chart").css("height",iH+"px");
    var iiH = iH + 50;
    $(".exam_statistics_chart").css("height",iiH+"px");
}

// echarts init
function initEcharts(DepartmentArr){
    setChartsContH(DepartmentArr);
	$(window).trigger("resize");
	ichart = echarts.init(document.getElementById('exam_statis_chart'), 'eoulu_chart_1');
    initEchartsHook = 1;
}

function renderEcharts(DepartmentArr,FinishedArr,NotFinishedArr,text,subText){
    setChartsContH(DepartmentArr);
    ichart.resize();
    examChartOption.yAxis.data = DepartmentArr;
    examChartOption.series[0].data = FinishedArr;
    examChartOption.series[1].data = NotFinishedArr;
    examChartOption.title.text = text;
    examChartOption.title.subtext = subText;
    if (examChartOption && typeof examChartOption === "object") {
        ichart.setOption(examChartOption, true);
    }
    ichart.off('click');
    ichart.on('click', function (params) {
        // 控制台打印数据的名称
        console.log(params.name);
        console.log(text);
        console.log(subText);
        if(params.name == "汇总"){
            return false;
        }
        $("#exam_detail_department").val(params.name);
        $(".g_container_info_l li:nth-child(2)").trigger("click");
    });
    /*窗口自适应，关键代码*/  
    setTimeout(function(){
        window.onresize = function(){  
            ichart.resize();
        };
    },150);
    $(".exam_statistics_chart_tit").text(text+" "+subText+" 数据统计图表");
}

// 图表请求数据
function chartGetData(searchNumber,text,subText){
    $.ajax({
        type: "GET",
        url: "AssessmentStatistics",
        data: {
            LoadType: "data",
            Number: searchNumber
        },
        dataType: "json"
    }).then(function(res){
        if(res.length==1){
            $.MsgBox_Unload.Alert("提示","无相应数据！");
        }else{
            var DepartmentArr = ["汇总"];
            var FinishedArr = [];
            var NotFinishedArr = [];
            res.map(function(currentValue,index,arr){
                if(index>0){
                    DepartmentArr.push(currentValue.Department);
                    FinishedArr.push(currentValue.Finished);
                    NotFinishedArr.push(currentValue.NotFinished);
                }
            });
            var sum1 = 0;
            var sum2 = 0;
            FinishedArr.map(function(currentValue,index,arr){
                sum1+=Number(currentValue);
            });
            NotFinishedArr.map(function(currentValue,index,arr){
                sum2+=Number(currentValue);
            });
            FinishedArr.unshift(sum1);
            NotFinishedArr.unshift(sum2);
            if(initEchartsHook == 0){
                initEcharts(DepartmentArr);
            }
            renderEcharts(DepartmentArr,FinishedArr,NotFinishedArr,text,subText);
        }
    },function(){
        $.MsgBox_Unload.Alert("统计图提示","网络繁忙！请求数据失败");
    }).always(function(){
    });
}

// 数据统计导航栏初始化
function examStatisInit(){
    $.ajax({
        type: "GET",
        url: "ExaminationDetails",
        data: {
            Type: "subject"
        },
        dataType: "json",
        success: function(data){
            var tempArr = [];
            data.map(function(currentValue,index,arr){
                if(index > 0){
                    tempArr.push(currentValue.Subject);
                }
            });
            subjectArr = globalArrStrUnique(tempArr);
            if(subjectArr.length>0){
                var str1 = '';
                subjectArr.map(function(currentValue,index,arr){
                    str1+='<option value="'+currentValue+'">'+currentValue.replace(/^《/,"").replace(/》$/,"")+'</option>';
                });
                $("select[name='subject_select']").empty().append(str1);
            }else{
                $.MsgBox_Unload.Alert("提示","科目列表长度为0！");
            }
        },
        error: function(){
            $.MsgBox_Unload.Alert("提示","服务器繁忙！获取科目列表有误");
        },
        complete: function(XMLHttpRequest, textStatus){
            if(textStatus=='success'){
                var subject = $("#exam_detail_subject").val();
                getQuesBySubject(subject,function(data){
                    var str = '';
                    data.map(function(currentValue, index, arr){
                        if(index > 0){
                            str+='<option value="'+currentValue.Number+'">'+currentValue.Number+'</option>';
                        }
                    });
                    $("#exam_detail_paper").empty().append(str);
                    $("#exam_detail_freshen").trigger("click");
                },function(){
                    // always回调
                });
            }else if(textStatus=='error'){
            }else if(textStatus=='timeout'){
                var xmlhttp = window.XMLHttpRequest ? new window.XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHttp");  
                xmlhttp.abort();
            }
        }
    });
}

// 详细信息页面 科目 试题 部门 搜素获取数据
function getDetailBySearch(icurPage){
    var iNumber = $("#exam_detail_paper").val();
    var Department = $("#exam_detail_department").val();
    $.ajax({
        type: "GET",
        url: "ExaminationDetails",
        data: {
            Type: "details",
            CurrentPage: icurPage,
            Number: iNumber,
            Department: Department
        },
        dataType: "json",
        success: function(res){
            var data = res.data;
            var pageCounts = res.pageCount;
            renderDetailBySearch(data,icurPage,pageCounts,Department);
            pageStyle(icurPage,pageCounts);
            tdDateHandle("td.hastd_details_Time","","#000");
        },
        error: function(){
            $.MsgBox_Unload.Alert("提示","服务器繁忙！");
        },
        complete: function(XMLHttpRequest, textStatus){
            if(textStatus=='success'){
                setCoverWH();
            }
        }
    });
}

// 详细信息页面 科目 试题 部门 搜素后渲染数据
function renderDetailBySearch(data,icurPage,pageCounts,Department){
    var str1;
    if(data.length == 1){
        var visibleLen = $(".exam_detail_more_detable thead tr:nth-child(1) th:visible").length;
        str1 = '<tr><td colspan="'+visibleLen+'" style="text-align:center;">无数据......</td></tr>';
    }else if(data.length > 1){
        data.map(function(currentValue,index,arr){
            if(index>0){
                str1+='<tr>'+
                        '<td data-id="'+currentValue.ScoreID+'" data-classify="'+currentValue.Classify+'" data-serialnumber="'+currentValue.SerialNumber+'">'+parseInt((parseInt(icurPage)-1)*10+index)+'</td>'+
                        '<td class="hastd_details_department">'+Department+'</td>'+
                        '<td class="hastd_details_Examiner">'+currentValue.Examiner+'</td>';
                // var inState = currentValue.State.toLowerCase();
                var inScore = currentValue.Score;
                if(inScore=="P"){
                    str1+='<td class="hastd_details_Score" title="P"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span></td>';
                }else if(inScore==="" || inScore=="--"){
                    str1+='<td class="hastd_details_Score">待批阅</td>';
                }else{
                    str1+='<td class="hastd_details_Score" title="'+inScore+'">'+inScore+'</td>';
                }
                str1+='<td class="hastd_details_Time">'+currentValue.Time+'</td>'+
                    '</tr>';
            }
        });
    }
    $(".exam_detail_more_detable tbody").empty().append(str1);
    $(".m_page #currentPage").text(icurPage);
    $(".m_page #allPage").text(pageCounts);
}

// 获取考核通知数据
function getAssessNotice(icurPage){
    $.ajax({
        type: "GET",
        url: "AssessmentNotice",
        data: {
            CurrentPage: icurPage
        },
        dataType: "json",
        success: function(res){
            var data = res.data;
            var pageCounts = res.pageCount;
            renderAssessNotice(data,icurPage,pageCounts);
            pageStyle(icurPage,pageCounts);
            tdDateHandle("td.notice_Time","","#000");
        },
        error: function(){
            $.MsgBox_Unload.Alert("提示","服务器繁忙！");
        },
        complete: function(XMLHttpRequest, textStatus){
            if(textStatus=='success'){
                setCoverWH();
            }
        }
    });
}

// 渲染考核通知数据
function renderAssessNotice(data,icurPage,pageCounts){
    var str = '';
    for(var i = 1;i<data.length;i++){
        var examineridarr = data[i].ExaminersID;
        var examineridstr;
        if(examineridarr.length==0){
            examineridstr = "";
        }else{
            examineridstr = examineridarr.join(",");
        }
        str+='<tr>'+
            '<td class="update_td_notice" data-id="'+data[i].ID+'" data-classify="'+data[i].Classify+'" data-serialnumber="'+data[i].SerialNumber+'">'+parseInt((parseInt(icurPage)-1)*10+i)+'</td>'+
            '<td class="notice_Subject" title="'+data[i].Subject+'">'+data[i].Subject+'</td>'+
            '<td class="notice_Number" title="'+data[i].Number+'">'+data[i].Number+'</td>'+
            '<td class="notice_Time">'+data[i].Time+'</td>'+
            '<td class="notice_departments" title="'+data[i].Department+'">'+data[i].Department+'</td>'+
            '<td class="notice_Examiners" title="'+data[i].Examiners+'" data-examineridstr="'+examineridstr+'">'+data[i].Examiners+'</td>'+
            '<td class="notice_PublishDate">'+data[i].PublishDate+'</td>'+
            '<td class="notice_Status"><input type="button" class="examiner_publish_save" value="保存"><input type="button" value="'+data[i].Status+'"></td>'+
            '</tr>';
    }
    $(".m_table4 tbody").empty().append(str);
    $(".m_page #currentPage").text(icurPage);
    $(".m_page #allPage").text(pageCounts);
}

// 跳页、翻页获取数据
function getGoPage(currentPage){
    if(presentSwitchClassify == "detail"){
        $(".exam_btn_div .InScore").val("录入分数");
        getDetailBySearch(currentPage);
    }else if(presentSwitchClassify == "notice"){
        getAssessNotice(currentPage);
    }
}

function departmentInit(){
    var departStr = '';
    var departmentOptStr = '';
    globalAllDepartArr.map(function(currentValue,index,arr){
        departStr+='<option value="'+currentValue+'">'+currentValue+'</option>';
        departmentOptStr+='<option value="'+currentValue+'">'+currentValue+'</option>';
    });
    $("#exam_detail_department").empty().append(departStr);
    $("#department_select").empty().append(departmentOptStr);
}

/* 以后参考
// 渲染科目、编号、分数
function renderSubjectScore(icurPage,Classify,pageCounts,data){
	var item = getClassItem(Classify);
	var personLen = $(item).find(".scroll_table_div thead th").length;
	var str = '';
	var str2 = '';
	var SerialNumberArr = [];
	var NumberArr = [];
	var hasRowspan = [];
	for(var i = 1;i<data.length;i++){
		SerialNumberArr.push(data[i].SerialNumber);
		NumberArr.push(data[i].Number);
	}
	var SerialNumberCount = globalGetArrCounts(SerialNumberArr);
	var NumberCount = globalGetArrCounts(NumberArr);
	console.log(SerialNumberCount);
	console.log(NumberCount);
	for(var ii = 1;ii<data.length;ii++){
		var iSerialNumber = data[ii].SerialNumber;
		var curSerNoCount = SerialNumberCount[data[ii].SerialNumber];
		if($.inArray(iSerialNumber, hasRowspan)==-1){
			str+='<tr>'+
					'<td rowspan="'+curSerNoCount+'">'+iSerialNumber+'</td>'+
					'<td rowspan="'+curSerNoCount+'">'+data[ii].Subject+'</td>'+
					'<td class="over_width_1 hastd_Number" data-id="'+data[ii].ID+'" data-serialnumber="'+iSerialNumber+'" data-subject="'+data[ii].Subject+'">'+data[ii].Number+'</td>'+
					'<td class="over_width_2 hastd_Title">'+data[ii].Title+'</td>'+
					'<td class="over_width_3 hastd_Time">'+data[ii].Time+'</td>'+
				'</tr>';
			hasRowspan.push(iSerialNumber);
		}else if($.inArray(iSerialNumber, hasRowspan)>-1){
			str+='<tr>'+
					'<td class="over_width_1 hastd_Number" data-id="'+data[ii].ID+'" data-serialnumber="'+iSerialNumber+'" data-subject="'+data[ii].Subject+'">'+data[ii].Number+'</td>'+
					'<td class="over_width_2 hastd_Title">'+data[ii].Title+'</td>'+
					'<td class="over_width_3 hastd_Time">'+data[ii].Time+'</td>'+
				'</tr>';
		}
		
		str2+='<tr>';
		for(var q = 0;q<personLen;q++){
			str2+='<td><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></td>';
		}
		str2+="</tr>";
	}
	$(item).find(".fixed_table_div tbody").empty().append(str);
	$(item).find(".scroll_table_div tbody").empty().append(str2);
	for(var iii = 1;iii<data.length;iii++){
		var ScoreInfoObj = JSON.parse(data[iii].ScoreInfo);
		if(ScoreInfoObj.length>1){
			ScoreInfoObj.map(function(currentValue, index, arr){
				if(index>0){
					var examinerId = currentValue.ExaminerID;
					var curTr = $(item).find(".scroll_table_div tbody tr").eq(iii-1);
					var curExaminerIndex = $(item).find(".scroll_table_div th[data-id='"+examinerId+"']").index();
					var State = currentValue.State; 
					var Score = currentValue.Score; 
					if(State=="待批阅"){
						curTr.find("td").eq(curExaminerIndex).empty().text(State);
					}else{
						curTr.find("td").eq(curExaminerIndex).empty().text(Score);
					}
					curTr.find("td").eq(curExaminerIndex).data("id",currentValue.ID);
				}
			});
		}
	}
	$(".m_page #currentPage").text(icurPage);
	$(".m_page #allPage").text(pageCounts);
}
*/

// select获取考核人员
// function getSelAssessPerson(item,flag,ExaminerIDArr){
// 	$.ajax({
// 		type: "GET",
// 		url: "ExaminationDetails",
// 		data:{
// 			LoadType: "examiner"
// 		},
// 		dataType: "json",
// 		success: function(data){
// 			console.log(typeof data);
// 			// [ ...PersonArr ] = data;
// 			var str = '';
// 			for(var i = 1; i<data.length; i++){
// 				str+='<option data-examiner="'+data[i].Examiner+'" value="'+data[i].ID+'">'+data[i].Examiner+'</option>';
// 			}
// 			$(item).empty().append(str);
// 		},
// 		error: function(){
// 			$.MsgBox_Unload.Alert("提示","服务器繁忙！");
// 		},
// 		complete: function(XMLHttpRequest, textStatus){
// 			if(textStatus=='success'){
// 	    		$(item).multiselect('destroy').multiselect(multiSelectOption);
// 	    		if(flag==true){
// 	    			$(item).multiselect('select', ExaminerIDArr);
// 	    		}
// 			}
// 		}
// 	});
// }

/*****页面加载完成*****/
$(function(){
    examStatisInit();
	setCoverWH();
    departmentInit();
    multiSelectOption.selectAllText = '选择所有部门！';
    // $("#department_select").multiselect('destroy').multiselect(multiSelectOption);
    $("#department_select").multiselect(multiSelectOption);

    multiSelectOption.selectAllText = '选择所有人员！';
    $('#examiner_select').multiselect(multiSelectOption);
    var selectOptionsData = [];
    var iIndex = 0;
    var myFunc = function(data){
        if(data.length > 1){
            department2ExamierObj[globalAllDepartArr[iIndex]] = [];
            var item = {};
            item.label = globalAllDepartArr[iIndex];
            item.value = globalAllDepartArr[iIndex];
            item.children = [];
            data.map(function(currentValue,index,arr){
                if(index > 0){
                    var iitem = {};
                    iitem["label"] = currentValue.StaffName;
                    // iitem["text"] = currentValue.StaffName+":";
                    iitem["value"] = currentValue.ID;
                    iitem["select"] = false;
                    iitem["disabled"] = true;
                    iitem["attributes"] = {
                        "examiner": currentValue.StaffName
                    };
                    item.children.push(iitem);
                    var iiitem = {};
                    iiitem.ID = currentValue.ID;
                    iiitem.StaffName = currentValue.StaffName;
                    department2ExamierObj[globalAllDepartArr[iIndex]].push(iiitem);
                }
            });
            selectOptionsData.push(item);
        }
        iIndex++;
        if(iIndex < globalAllDepartArr.length){
            $.when(
                $.ajax({
                    type: 'GET',
                    url: 'GetStaffInfo',
                    data: {
                        Type: "common",
                        Department: globalAllDepartArr[iIndex]
                    },
                    dataType: "json"
                })
                ).done(myFunc);
        }else if(iIndex == globalAllDepartArr.length){
            // console.log(selectOptionsData);
            $('#examiner_select').multiselect('dataprovider', selectOptionsData);
        }
    };
    $.when(
        $.ajax({
            type: 'GET',
            url: 'GetStaffInfo',
            data: {
                Type: "common",
                Department: globalAllDepartArr[iIndex]
            },
            dataType: "json"
        })
        ).then(myFunc, function(){
        $.MsgBox_Unload.Alert("获取员工姓名提示","初始化失败！");
    });

    //
    // var testArr = [];
    // var promise = new Promise(function(resolve){
    //     for(var i = 0; i<10; i++){
    //         var test1 = new Promise(function(resolve){
    //             setTimeout(function(){
    //                 return resolve(i);
    //             },100);
    //         });
    //         Promise.all([test1]).then(function([result1]){
    //             testArr.push(result1);
    //             if(i === 9){
    //                 resolve();
    //             }
    //         });
    //     }
    // });
    // promise.then(function(){
    //     console.log(testArr);
    //     console.log(testArr.length);
    // });
});


/*****
* event listener
*****/
// add
$(".exam_btn_div input[value='添加']").on("click",function(){
	$(".add_NonStandard_body_in [class^='info_']").each(function(){
		$(this).val("");
	});
	$(".bg_cover, .add_NonStandard").slideDown(350);
});
// update
$(document).on("click",".update_td_notice",function(){
	// jQuery.data($(this), "customerId");
	updateSubmitObj.ID = Number($(this).data("id")).toString();
	var iserialnumber = $(this).data("serialnumber");
	var that = $(this);
	$(".update_NonStandard_body_in [class^='info_']").each(function(){
		var subClassName = $(this).attr("class").split(" ")[0].replace("info_","notice_");
		var oldVal;
		switch(subClassName)
		{
		    case "notice_SerialNumber":
		    	oldVal = iserialnumber;
		        break;
		    default:
		    	oldVal = that.siblings("."+subClassName).text();
		}
		var newVal = globalDataHandle(oldVal,"");
		$(this).val(newVal);
	});
	$(".bg_cover, .update_NonStandard").slideDown(350);
});

// 录入分数
$(".exam_btn_div .InScore").on("click",function(){
    if($(".exam_detail_more_detable tbody tr").length == 1 && $(".exam_detail_more_detable tbody>tr>td").length == 1){
        return false;
    }
	if($(this).val()=="录入分数"){
		scoreContenteditable("true");
		$(this).val("提交分数");
	}else if($(this).val()=="提交分数"){
		var that = $(this);
		var iflag;
		var ScoreJson = [];
		var currentPage = Number($("span#currentPage").text());
		$(".exam_detail_more_detable .hastd_details_Score").each(function(){
			// if($(this).html().indexOf("span")>-1){
			// 	return true;
			// }
			if($(this).text().trim()=="待批阅"){
                return true;
            }
			var item = {};
			item.Score = $(this).attr("title");
			item.ScoreID = $(this).siblings("td:nth-child(1)").data("id");
			ScoreJson.push(item);
		});
		ScoreJson = JSON.stringify(ScoreJson);
		$.ajax({
			type: "POST",
			url: "ExaminationDetails",
			data: {
				Item: "score",
				ScoreJson: ScoreJson
			},
			dataType: "text",
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            success: function(data){
                if(data=="保存成功"){
                	$.MsgBox_Unload.Alert("提示","修改分数成功！");
                	iflag = 1;
                }else if(data=="部分分数保存失败"){
                	$.MsgBox_Unload.Alert("提示","部分分数保存失败！");
                	iflag = 0;
                }else{
                    $.MsgBox_Unload.Alert("提示","修改分数失败！");
                    iflag = 0;
                }
            },
            error: function(){
                $.MsgBox_Unload.Alert("提示","服务器繁忙！");
            },
            complete: function(XMLHttpRequest, textStatus){
			    if(textStatus=="success"){
			    	if(iflag == 1){
                        getDetailBySearch(currentPage);
			    		scoreContenteditable("false");
                        that.val("录入分数");
			    	}
			    }
            }
		});
	}
});

// 更改状态 & 提交状态
// $(".exam_btn_div .InState").click(function(){
//     if($(".exam_detail_more_detable tbody tr").length == 1 && $(".exam_detail_more_detable tbody>tr>td").length == 1){
//         return false;
//     }
//     if($(this).val() == "更改状态"){
//         changeState = 1;
//         $(this).val("提交状态");
//     }else if($(this).val() == "提交状态"){
//         var iflag = 0;
//         var ConfirmJson = [];
//         var currentPage = Number($("span#currentPage").text());
//         $(".exam_detail_more_detable .hastd_details_State").each(function(){
//             var item = {};
//             item.State = $(this).data("state");
//             item.ScoreID = $(this).siblings("td:nth-child(1)").data("id");
//             ConfirmJson.push(item);
//         });
//         ConfirmJsonStr = JSON.stringify(ConfirmJson);
//         $.ajax({
//             type: "POST",
//             url: "ExaminationDetails",
//             data: {
//                 Item: "confirm",
//                 ConfirmJson: ConfirmJsonStr
//             },
//             dataType: "text",
//             contentType: "application/x-www-form-urlencoded;charset=utf-8",
//             success: function(data){
//                 if(data=="保存成功"){
//                     $.MsgBox_Unload.Alert("提示","修改状态成功！");
//                     iflag = 1;
//                 }else{
//                     $.MsgBox_Unload.Alert("提示",data);
//                     iflag = 0;
//                 }
//             },
//             error: function(){
//                 $.MsgBox_Unload.Alert("提示","服务器繁忙！");
//             },
//             complete: function(XMLHttpRequest, textStatus){
//                 if(textStatus=="success"){
//                     if(iflag == 1){
//                         getDetailBySearch(currentPage);
//                         buttonInit();
//                     }
//                 }
//             }
//         });
//     }
// });

// 点击改变状态
// $(document).on("click",".hastd_details_State",function(){
//     if(changeState == 0){
//         return false;
//     }
//     if($(this).data("state") == "no"){
//         $(this).data("state", "yes");
//     }else if($(this).data("state") == "yes"){
//         $(this).data("state", "no");
//     }
//     $(this).children("span.glyphicon ").toggleClass("glyphicon-remove glyphicon-ok");
// });

// 关闭
  // 添加
$("#NonStandard_addclose, .add_NonStandard_tit_r").on("click",function(){
	for(var k in addSubmitObj){
		addSubmitObj[k] = null;
	}
	$(".bg_cover, .add_NonStandard").slideUp(350);
});
  // update
$("#NonStandard_updateclose, .update_NonStandard_tit_r").on("click",function(){
	for(var k in updateSubmitObj){
		updateSubmitObj[k] = null;
	}
	$(".bg_cover, .update_NonStandard").slideUp(350);
});

// 翻页功能
$("#jumpNumber").on("input propertychange",function(){
    var newVal = $(this).val().replace(/[^\d]/g,'');
    $(this).val(newVal);
});

	// 翻页
$("#fistPage").click(function(){
    var currentPage =1;
    getGoPage(currentPage);
    // if(hasSearch==0){
    // 	tableRenderAjax(currentPage);
    // }else if(hasSearch==1){
    // 	searchGetParam(currentPage);
    // }
});

$("#lastPage").click(function(){
    var currentPage =Number($("#allPage").text());
    getGoPage(currentPage);
});

$("#upPage").click(function(){
    var currentPage = Number($("#currentPage").text());
    if(currentPage == 1){
        return;
    }else{
        currentPage--;
        getGoPage(currentPage);
    }
});

$("#nextPage").click(function(){
    var currentPage = Number($("#currentPage").text());
    var pageCounts = Number($("#allPage").text());
    if(currentPage == pageCounts){
        return;
    }else{
        currentPage++;
        getGoPage(currentPage);
    }
});
	//跳页
$("#Gotojump").click(function(){
    var currentPage = $("#jumpNumber").val().trim();
    var pageCounts = Number($("#allPage").text());
    var oldCurrentPage = Number($("#currentPage").text());
    if(currentPage == oldCurrentPage || currentPage <= 0 || currentPage>pageCounts){
        $("#jumpNumber").val('');
        return;
    }else{
        getGoPage(currentPage);
    }
});

// 添加提交
$("#NonStandard_addsubmit").on("click",function(){
	var SerialNoHook = $(".add_NonStandard .info_SerialNumber").val().trim();
	var NoHook = $(".add_NonStandard .info_Number").val().trim();
    if(SerialNoHook == "" || NoHook == ""){
        $.MsgBox_Unload.Alert("提示","序号与编号不能为空");
        return false;
    }
	if(NoHook.indexOf(SerialNoHook)!=0){
		$.MsgBox_Unload.Alert("提示","序号与编号不匹配");
		return false;
	}
	for(var kk in addSubmitObj){
		if(kk=="Item"){
			addSubmitObj[kk] = "examination";
			continue;
		}
		if(kk=="Type"){
			addSubmitObj[kk] = "add";
			continue;
		}
		if(kk=="ID"){
			addSubmitObj[kk] = "0";
			continue;
		}
		addSubmitObj[kk] = $(".add_NonStandard").find(".info_"+kk).val();
	}
	// 表单验证
	for(var kkk in addSubmitObj){
		addSubmitObj[kkk] = globalDataHandle(addSubmitObj[kkk],"").trim();
		// if(kkk=="Classify"&&addSubmitObj[kkk]==""){
		// 	$.MsgBox_Unload.Alert("提示","考核明细切换出错！");
		// 	return false;
		// }
	}
	console.log("add我执行了吗");
	console.log(addSubmitObj);
    // var pageCounts = Number($("#allPage").text());
	$.ajax({
		type: "POST",
		url: "ExaminationDetails",
		dataType: 'text',
		data: addSubmitObj,
		contentType: "application/x-www-form-urlencoded;charset=utf-8",
		success: function(data){
			if(data=="true"){
				$.MsgBox_Unload.Alert("提示","添加成功！");
				$("#NonStandard_addclose").trigger("click");
			}else if(data=="false"){
				$.MsgBox_Unload.Alert("提示","添加失败！");
			}
		},
		error:function(){
			$.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
		},
		complete: function(XMLHttpRequest, textStatus){
		    if(textStatus=="success"){
                // getAssessNotice(pageCounts);
				getAssessNotice(1);
		    }
		}
	});
});

// 修改提交
$("#NonStandard_updatesubmit").on("click",function(){
	var SerialNoHook = $(".update_NonStandard .info_SerialNumber").val().trim();
	var NoHook = $(".update_NonStandard .info_Number").val().trim();
    if(SerialNoHook == "" || NoHook == ""){
        $.MsgBox_Unload.Alert("提示","序号与编号不能为空");
        return false;
    }
	if(NoHook.indexOf(SerialNoHook)!=0){
		$.MsgBox_Unload.Alert("提示","序号与编号不匹配");
		return false;
	}
	for(var kk in updateSubmitObj){
		if(kk=="Item"){
			updateSubmitObj[kk] = "examination";
			continue;
		}
		if(kk=="Type"){
			updateSubmitObj[kk] = "update";
			continue;
		}
		if(kk=="ID"){
			continue;
		}
		updateSubmitObj[kk] = $(".update_NonStandard").find(".info_"+kk).val();
	}
	// 表单验证
	for(var kkk in updateSubmitObj){
		updateSubmitObj[kkk] = globalDataHandle(updateSubmitObj[kkk],"").trim();
	}
	var iicurrentPage = Number($("span#currentPage").text());
	console.log("update我执行了吗");
	console.log(updateSubmitObj);
	$.ajax({
		type: "POST",
		url: "ExaminationDetails",
		dataType: 'text',
		data: updateSubmitObj,
		contentType: "application/x-www-form-urlencoded;charset=utf-8",
		success: function(data){
			console.log(typeof data);
			if(data=="true"){
				$.MsgBox_Unload.Alert("提示","修改成功！");
				$("#NonStandard_updateclose").trigger("click");
			}else if(data=="false"){
				$.MsgBox_Unload.Alert("提示","修改失败！");
			}
		},
		error: function(){
			$.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
		},
		complete: function(XMLHttpRequest, textStatus){
		    if(textStatus=="success"){
				getAssessNotice(iicurrentPage);
		    }
		}
	});
});

// 发布按钮事件
$(document).on("click",".notice_Status input",function(){
    if($(this).val()=="保存"){
        var email_curPage = Number($(".pageInfo #currentPage").text());
        var SubjectID = $(this).parent().siblings("td:nth-child(1)").data("id");
        var currentTr = $(".m_table4 tbody tr").find("td:nth-child(1)[data-id='"+SubjectID+"']").parent();
        var Subject = currentTr.find(".notice_Subject").text();
        var iNumber = currentTr.find(".notice_Number").text();
        var Time = currentTr.find(".notice_Time").text();
        var ExaminerIDStr = currentTr.find(".notice_Examiners").data("examineridstr").toString();
        var ExaminersStr = currentTr.find(".notice_Examiners").text();
        var Examiners = [];
        var ExaminerID = [];
        if(ExaminersStr==""){
            Examiners = [];
        }else{
            Examiners = ExaminersStr.split("、");
        }
        if(ExaminerIDStr==""){
            ExaminerID = [];
        }else{
            ExaminerID = ExaminerIDStr.split(",");
        }
        if(Examiners.length==0||ExaminerID.length==0){
            $.MsgBox_Unload.Alert("提示","考核人员未选");
            return;
        }else if(Examiners.length!=ExaminerID.length){
            $.MsgBox_Unload.Alert("提示","考核人员选择操作失误");
            return;
        }else{
            var Department = currentTr.find(".notice_departments").text();
            $.ajax({
                type: 'POST',
                url: 'AssessmentNotice',
                dataType: 'text',
                data: {
                    Type: "save",
                    SubjectID: SubjectID,
                    Examiners: Examiners,
                    ExaminerID: ExaminerID,
                    Subject: Subject,
                    Number: iNumber,
                    Time: Time,
                    Department: Department
                },
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                success: function(data){
                    $.MsgBox_Unload.Alert("提示",data);
                },
                error: function(){
                    $.MsgBox_Unload.Alert("提示","服务器繁忙！");
                },
                complete: function(XMLHttpRequest, textStatus){
                    if(textStatus=="success"){
                        getAssessNotice(email_curPage);
                    }
                }
            });
        }
    }else{
        var examinerNameStr = $(this).parent().siblings(".notice_Examiners").text();
        if(examinerNameStr == "" || examinerNameStr == null){
            $.MsgBox_Unload.Alert("提示","考核人员未选");
            return false;
        }else{
            var examinerNameArr = examinerNameStr.split("、");
            var str11 = '';
            examinerNameArr.map(function(currentValue,index,arr){
                str11+='<li title="'+currentValue+'"><input type="checkbox">'+currentValue+'</li>';
            });
            $("#publish_Candidate").empty().append(str11);
        }
        $(".bg_cover, .publish_NonStandard").slideDown(200);
        $(".publish_NonStandard").attr("value",$(this).parent().siblings("td:nth-child(1)").data("id"));
    }
});

// 发布框关闭
$(document).on("click","#NonStandard_publishSend, .publish_NonStandard_tit_r",function(){
    $(".publish_NonStandard, .bg_cover").slideUp(200);
});

// 发布
$(document).on("click","#NonStandard_publishSave",function(){
    var email_curPage = 1;
    var SubjectID = $(".publish_NonStandard").attr("value");
    var currentTr = $(".m_table4 tbody tr").find("td:nth-child(1)[data-id='"+SubjectID+"']").parent();
    var Subject = currentTr.find(".notice_Subject").text();
    var iNumber = currentTr.find(".notice_Number").text();
    var Time = currentTr.find(".notice_Time").text();
    var ExaminerIDStr = currentTr.find(".notice_Examiners").data("examineridstr").toString();
    var ExaminersStr = currentTr.find(".notice_Examiners").text();
    var Examiners = [];
    var ExaminerID = [];
    if(ExaminersStr==""){
    	Examiners = [];
    }else{
    	Examiners = ExaminersStr.split("、");
    }
    if(ExaminerIDStr==""){
    	ExaminerID = [];
    }else{
    	ExaminerID = ExaminerIDStr.split(",");
    }
    var RecipientArr = [];
    var RecipientStr;
    $("#publish_Candidate li").each(function(){
        // console.log($(this).find("input").prop("checked"));
        if($(this).find("input").prop("checked")){
            RecipientArr.push($(this).attr("title"));
        }
    });
    if(RecipientArr.length == 0){
        $.MsgBox_Unload.Alert("提示","未选择收件人");
        return false;
    }else{
        RecipientStr = RecipientArr.join(";");
    }
    if(Examiners.length==0||ExaminerID.length==0){
        $.MsgBox_Unload.Alert("提示","考核人员未选");
        $("#NonStandard_publishSend").trigger("click");
        return;
    }else if(Examiners.length!=ExaminerID.length){
        $.MsgBox_Unload.Alert("提示","考核人员选择操作失误");
        $("#NonStandard_publishSend").trigger("click");
        return;
    }else{
        var Department = currentTr.find(".notice_departments").text();
        $.ajax({
            type: 'POST',
            url: 'AssessmentNotice',
            dataType: 'text',
            data: {
                Type: "send",
            	SubjectID: SubjectID,
            	Examiners: Examiners,
            	ExaminerID: ExaminerID,
            	Subject: Subject,
            	Number: iNumber,
            	Time: Time,
                Department: Department,
                Recipient: RecipientStr
            },
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            success: function(data){
                $.MsgBox_Unload.Alert("提示",data);
            },
            error: function(){
                $.MsgBox_Unload.Alert("提示","服务器繁忙！");
            },
            beforeSend: function(XMLHttpRequest){
                $("#NonStandard_publishSave").css({"cursor":"not-allowed"});
                $("#NonStandard_publishSave").prop("disabled",true);
            },
            complete: function(XMLHttpRequest, textStatus){
            	$("#NonStandard_publishSave").css({"cursor":"pointer"});
            	$("#NonStandard_publishSave").prop("disabled",false);
			    if(textStatus=="success"){
                    getAssessNotice(email_curPage);
	    			$("#NonStandard_publishSend").trigger("click");
			    }
            }
        });
    }
});

// 考核通知选部门
$(document).on("click",".notice_departments",function(){
    curNoticeIndex = $(this).parent("tr").index();
    var iDepartmentStr = $(this).attr("title");
    var iDepartmentArr;
    if(iDepartmentStr==undefined || iDepartmentStr=="" || iDepartmentStr=="--"){
        iDepartmentArr = [];
    }else{
        iDepartmentArr = iDepartmentStr.split("、");
    }
    // false表示所有不选中
    $('#department_select').multiselect('deselectAll', false);
    $('#department_select').multiselect('updateButtonText');
    $("#department_select").multiselect('select', iDepartmentArr);
    $(".cover_bg2, .department_sele").slideDown(300);
});

// 考核通知选部门关闭
$(".department_sele_top_r").click(function(){
    var departmentSelect = [];
    $("#department_select option:selected").each(function () {  
        departmentSelect.push($(this).val());
    });
    var curTr = $(".exam_detail_notice_detable tbody tr").eq(curNoticeIndex);
    var departmentSelectStr = departmentSelect.join("、");
    curTr.find(".notice_departments").text(departmentSelectStr).attr("title",departmentSelectStr);
    $(".cover_bg2, .department_sele").slideUp(300);
});

// 考核通知选人
$(document).on("click",".notice_Examiners",function(){
    var curDepartStr = $(this).siblings(".notice_departments").text();
    if(curDepartStr=="" || curDepartStr=="--"){
        $.MsgBox_Unload.Alert("提示","请先选择部门！");
        return false;
    }
    var curDepartArr = curDepartStr.split("、");
    // 给abled的考核人员disabled false
    var hasAbledDepart = [];
    var hasDisabledDepart = [];
    for(var iii in department2ExamierObj){
        if(curDepartArr.indexOf(iii) > -1){
            department2ExamierObj[iii].map(function(currentValue,index,arr){
                hasAbledDepart.push(currentValue.ID);
            });
        }else{
            hasDisabledDepart.push(iii);
        }
    }
    // 初始化，都不可点击
    $('option', $('#examiner_select')).prop('disabled', true);
    $('option', $('#examiner_select')).each(function(element) {
        $(this).removeAttr('selected').prop('selected', false);
    });
    // 初始化，部门都可点击
    $(".examiner_sel .multiselect-item.multiselect-group input").prop("disabled",false);
    // 可用部门的成员可选
    hasAbledDepart.map(function(currentValue,index,arr){
        $('option[value="'+currentValue+'"]', $('#examiner_select')).prop('disabled', false);
    });
    $('#examiner_select').multiselect('refresh');
    // 全部取消disabled部门的默认选中
    // $(".examiner_sel .multiselect-item.multiselect-group input").removeAttr('checked').prop("checked",false);
    // 保存记录ID
    var subjectid = $(this).siblings("td:nth-child(1)").data("id");
    $(".examiner_sel").attr("value",subjectid);
    var ExaminerIDArr;
    if($(this).data("examineridstr")==undefined || $(this).data("examineridstr")==""){
        ExaminerIDArr = [];
    }else{
        ExaminerIDArr = $(this).data("examineridstr").toString().split(",");
    }
    // 选中回显的考核人员
    $("#examiner_select").multiselect('select', ExaminerIDArr);
    // 把disabled的部门的默认点击取消
    hasDisabledDepart.map(function(currentValue,index,arr){
        // $(".examiner_sel .multiselect-item.multiselect-group input[value='"+currentValue+"']").trigger("click");
        $(".examiner_sel .multiselect-item.multiselect-group input[value='"+currentValue+"']").prop("disabled",true).removeAttr('checked').prop("checked",false).parents("li.multiselect-item.multiselect-group.active").removeClass("active");
    });
    $(".cover_bg2, .examiner_sel").slideDown(300);

    /*
    * 2018/07/25 注释
    var curDepartStr = $(this).siblings(".notice_departments").text();
    if(curDepartStr=="" || curDepartStr=="--"){
        $.MsgBox_Unload.Alert("提示","请先选择部门！");
        return false;
    }
    var curDepartArr = curDepartStr.split("、");
    // 初始化，都不可点击
    $('option', $('#examiner_select')).prop('disabled', true);
    $('option', $('#examiner_select')).each(function(element) {
        $(this).removeAttr('selected').prop('selected', false);
    });
    $('#examiner_select').multiselect('refresh');
    // 全部取消disabled的默认选中
    $(".examiner_sel .multiselect-item.multiselect-group input").trigger("click");
    // 给abled的考核人员disabled false
    var hasAbledDepart = [];
    var hasDisabledDepart = [];
    for(var iii in department2ExamierObj){
        if(curDepartArr.indexOf(iii) > -1){
            department2ExamierObj[iii].map(function(currentValue,index,arr){
                hasAbledDepart.push(currentValue.ID);
            });
        }else{
            hasDisabledDepart.push(iii);
        }
    }
    hasAbledDepart.map(function(currentValue,index,arr){
        $('option[value="'+currentValue+'"]', $('#examiner_select')).prop('disabled', false);
    });
    $('#examiner_select').multiselect('refresh');
    // 保存记录ID
    var subjectid = $(this).siblings("td:nth-child(1)").data("id");
    $(".examiner_sel").attr("value",subjectid);
    var ExaminerIDArr;
    if($(this).data("examineridstr")==undefined || $(this).data("examineridstr")==""){
        ExaminerIDArr = [];
    }else{
        ExaminerIDArr = $(this).data("examineridstr").toString().split(",");
    }
    // 选中回显的考核人员
    $("#examiner_select").multiselect('select', ExaminerIDArr);
    // 把disabled的部门的默认点击取消
    hasDisabledDepart.map(function(currentValue,index,arr){
        $(".examiner_sel .multiselect-item.multiselect-group input[value='"+currentValue+"']").trigger("click");
    });
    $(".cover_bg2, .examiner_sel").slideDown(300);
    */

    /*
    var ii = 0;
    var iiLen = curDepartArr.length;
    var IDARRAY = [];
    var STAFFARRAY =[];

    var fn = function(data){
        data.map(function(currentValue,index,arr){
            if(index>0){
                IDARRAY.push(currentValue.ID);
                STAFFARRAY.push(currentValue.StaffName);
            }
        });
        ii++;
        if(ii < iiLen){
            globalGetStaffAllInfoByDepart(curDepartArr[ii],fn);
        }else if(ii == iiLen){
            // console.log(IDARRAY);
            // console.log(STAFFARRAY);
            var str = '';
            for(var i = 0; i<IDARRAY.length; i++){
                str+='<option data-examiner="'+STAFFARRAY[i]+'" value="'+IDARRAY[i]+'">'+STAFFARRAY[i]+'</option>';
            }
            $('#examiner_select').empty().append(str);
            multiSelectOption.selectAllText = '选择所有人员！';
            $('#examiner_select').multiselect('destroy').multiselect(multiSelectOption);
            $("#examiner_select").multiselect('select', ExaminerIDArr);
            $(".cover_bg2, .examiner_sel").slideDown(300);
        }
    };
    globalGetStaffAllInfoByDepart(curDepartArr[ii],fn);
    */
	// $('#examiner_select').multiselect('deselectAll', false);
    // $('#examiner_select').multiselect('updateButtonText');
});
// 关闭
$(".examiner_sel_top_r").on("click",function(){
	var ExaminerIDArr = [];
	var ExaminersArr = [];
	$("#examiner_select option:selected").each(function () {  
	    ExaminerIDArr.push($(this).val());
	    ExaminersArr.push($(this).data("examiner"));
	});
	var ExaminerIDStr = ExaminerIDArr.join(",");
	var ExaminersStr = ExaminersArr.join("、");
	var curSubjectId = $(".examiner_sel").attr("value");
	var notice_Examiners = $(".m_table4 tbody tr").find("td:nth-child(1)[data-id='"+curSubjectId+"']").parent().find(".notice_Examiners");
	notice_Examiners.data("examineridstr",ExaminerIDStr);
	notice_Examiners.text(ExaminersStr);
	notice_Examiners.attr("title",ExaminersStr);
	console.log("考核通知选人关闭");
	console.log(notice_Examiners.data("examineridstr"));
	console.log(typeof notice_Examiners.data("examineridstr"));
	$(".cover_bg2, .examiner_sel").slideUp(300);
});

// 录入分数事件
$(document).on("focus",".exam_detail_more_detable .hastd_details_Score",function(){
    if($(this).attr("title") == "P"){
        $(this).text("");
        return false;
    }
	var curVal = $(this).text().trim();
	if(curVal=="待批阅"){
		$(this).text("");
		return false;
	}

});
$(document).on("blur",".exam_detail_more_detable .hastd_details_Score",function(){
	var curVal = $(this).text().trim();
	// if(curVal=="待批阅"){
	// 	$(this).text("待批阅");
	// 	return false;
	// }
    if(curVal=="P" || curVal=="p"){
        $(this).html('<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>');
        $(this).attr("title","P");
        return false;
    }
    var reg = new RegExp("^(\\d|[1-9]\\d|[1][0-1]\\d|120)$");
	// var reg = new RegExp("^(\\d|[1-9]\\d|100)$");
	if(!reg.test(curVal)){
		$(this).text("待批阅");
	}else{
        $(this).text(curVal);
        $(this).attr("title",curVal);
	}
});

// 导航切换
$(".g_container_info_l li").click(function(){
    setCoverWH();
	$(this).addClass("current_tab").siblings("li").removeClass("current_tab");
	// var searchClassify = $(this).data("classify").replace("_tab","_form");
	// if(searchClassify=="exam_statistics_form"){
 //        $("form.exam_detail_form").fadeIn(200);
 //        $(".form_sel_depart").hide();
 //    }else if(searchClassify=="exam_detail_form"){
 //        $(".form_sel_depart").show();
 //    }
	var bodyClassify = $(this).data("classify").replace("_tab","_content");
	$("div[class$='_content']:not(."+bodyClassify+")").slideUp(250,"swing",function(){
		$("div."+bodyClassify).slideDown(300);
        if(bodyClassify == "exam_statistics_content"){
            $("form.exam_detail_form").fadeIn(100);
            $(".form_sel_depart").fadeOut(50,function(){
                examStatisInit();
            });
        }else if(bodyClassify == "exam_detail_content"){
            $(".form_sel_depart").fadeIn(50,function(){
                $(".presentation_div ul li:nth-child(1)").trigger("click");
            });
        }
	});
});

// 分页切换
$(".presentation_div ul>li").click(function(){
    setCoverWH();
    $(this).addClass("active").siblings("li").removeClass("active");
    var presentationClassify = $(this).data("classify").replace("_presentation","_detable");
    $("div[class$='_detable']:not(."+presentationClassify+")").slideUp(250,"swing",function(){
        $("div."+presentationClassify).slideDown(250);
        if(presentationClassify == "exam_detail_more_detable"){
            presentSwitchClassify = "detail";
            $(".exam_btn_div input[value='添加']").fadeOut(125, function(){
                $("form.exam_detail_form, .exam_btn_div .InScore").fadeIn(125, function(){
                    // 请求数据
                    $("#exam_detail_freshen").trigger("click");
                });
                $(".exam_btn_div .InScore").val("录入分数");
            });
        }else if(presentationClassify == "exam_detail_notice_detable"){
            getAssessNotice(1);
            presentSwitchClassify = "notice";
            $("form.exam_detail_form, .exam_btn_div .InScore").fadeOut(125, function(){
                $(".exam_btn_div input[value='添加']").fadeIn(125);
            });
        }
    });
});

// 窗口resize
$(window).on("resize",function(){
	var iw = $(".exam_statistics_chart").width();
	$('#exam_statis_chart').css({"width": iw+"px"});
});

// 科目切换事件
$("select[name='subject_select']").on("change",function(){
    var subject = $(this).val();
    var paperClassify = $(this).attr("id").replace("_subject","_paper");
    getQuesBySubject(subject,function(data){
        var str = '';
        data.map(function(currentValue, index, arr){
            if(index > 0){
                str+='<option value="'+currentValue.Number+'" data-tit="'+currentValue.Title+'">'+currentValue.Number+'&nbsp;&nbsp;'+currentValue.Title.substring(0,10)+'</option>';
            }
        });
        $("#"+paperClassify).empty().append(str);
    },function(){
        // always回调
    });
});

// 数据统计页面刷新 & 详细信息页面搜索刷新
$("#exam_detail_freshen").click(function(){
    if($(".form_sel_depart").is(':visible')){
        getDetailBySearch(1);
    }else{
        var iiSubject = $("#exam_detail_subject").val();
        var searchNumber = $("#exam_detail_paper").val();
        var text = iiSubject;
        var subText = searchNumber;
        chartGetData(searchNumber,text,subText);
    }
    $(".exam_btn_div .InScore").val("录入分数");
});