/**
 * Created by HuaOPER on 2018/3/20.
 */

var HotProductState = new Object();
HotProductState.calendarSaleProductCategory = "";
HotProductState.calendarSaleModel = "";
HotProductState.calendarSaleColumn = "";

var hotProductDataObj = {};

//startTime
 /*   var Y = new Date().getFullYear();
	var thetime = Y+'-01-01T00:00:00-04:00';
	var  T=new   Date(Date.parse(thetime));
	document.getElementById('start_time').valueAsDate = T;*/

// 翻页组件按钮逻辑
// flag 为按钮ID后缀  如 pageStyle(CurrentPage, pageCount, "2");
function pageStyle(currentPage, pageCounts, flag){
    flag = flag || "";
    if(pageCounts == 1){
        $("#fistPage"+flag+", #upPage"+flag+", #nextPage"+flag+", #lastPage"+flag+", #Gotojump"+flag).prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
    }else if(currentPage == 1){
        $("#fistPage"+flag+", #upPage"+flag).prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
        $("#lastPage"+flag+", #nextPage"+flag+", #Gotojump"+flag).prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }else if(currentPage == pageCounts){
        $("#lastPage"+flag+", #nextPage"+flag).prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
        $("#fistPage"+flag+", #upPage"+flag+", #Gotojump"+flag).prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }else{
        $("#lastPage"+flag+", #nextPage"+flag+", #fistPage"+flag+", #upPage"+flag+", #Gotojump"+flag).prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }
}

// 历年销量对比请求数据
function calendarSaleGetData(ProductCategory, Model, Column, CurrentPage){
    var immediate = false;

    var immediateFn = null;

    var searchObj = {
        ProductCategory: ProductCategory,
        Model: Model,
        Column: Column
    };

    var searchParam = $.param(searchObj);

    var expireFalseFn = function(resp){
        resp = resp || {};
        var idata = resp.data;
        var ipageCount = resp.pageCount;
        calendarSaleRenderDataTrue(idata, CurrentPage, ipageCount, ProductCategory, Model, Column);
    };

    var expireTrueFn = function(curTime){
        calendarSaleGetDataAjax(ProductCategory, Model, Column, CurrentPage, function(ires){
            var searchParamVal = hotProductDataObj[searchParam];
            searchParamVal[CurrentPage].expire = curTime + (1000*60*1);
            searchParamVal[CurrentPage].resp = $.extend(true, {}, ires);
        });
    };

    var pageNotExistFn = function(curTime){
        calendarSaleGetDataAjax(ProductCategory, Model, Column, CurrentPage, function(ires){
            var searchParamVal = hotProductDataObj[searchParam];
            searchParamVal[CurrentPage] = {};
            searchParamVal[CurrentPage].searchObj = $.extend(true, {}, searchObj);
            searchParamVal[CurrentPage].expire = curTime + (1000*60*1);
            searchParamVal[CurrentPage].resp = $.extend(true, {}, ires);
        });
    };

    var searchParamNotExistFn = function(curTime){
        calendarSaleGetDataAjax(ProductCategory, Model, Column, CurrentPage, function(ires){
            hotProductDataObj[searchParam] = {};
            var searchParamVal = hotProductDataObj[searchParam];
            searchParamVal[CurrentPage] = {};
            searchParamVal[CurrentPage].searchObj = $.extend(true, {}, searchObj);
            searchParamVal[CurrentPage].expire = curTime + (1000*60*1);
            searchParamVal[CurrentPage].resp = $.extend(true, {}, ires);
        });
    };

    globalInertLoadNoSearch(immediate, immediateFn, hotProductDataObj, searchParam, CurrentPage, expireFalseFn, expireTrueFn, pageNotExistFn, searchParamNotExistFn);
}

function calendarSaleGetDataAjax(ProductCategory, Model, Column, CurrentPage, fn) {
    // if(ProductCategory == "999" || ProductCategory == "9999"){
    //     $.Response_Load.Before("数据加载提示","数据正在传输......",200);
    // }
    $.ajax({
        type: "POST",
        url: "HotProduct",
        data: {
            ProductCategory: ProductCategory,
            Model: Model,
            Column: Column,
            CurrentPage: CurrentPage
        },
        dataType: "json"
    }).then(function(res){
        var data = res.data;
        var pageCount = res.pageCount;
        calendarSaleRenderDataTrue(data, CurrentPage, pageCount, ProductCategory, Model, Column);
        fn && fn(res);
    },function(){
        CoverShow("服务器繁忙！");
    }).always(function(){
        // if(ProductCategory == "999" || ProductCategory == "9999"){
        //     $.Response_Load.After("数据加载完成！",300);
        // }
    });
}

// 渲染
function calendarSaleRenderData(data, CurrentPage, pageCount){
    var str = '';
    if(data.length < 2){
        str+='<tr><td colspan="8">无数据...</td></tr>';
    }else{
        data.map(function(currentValue, index, arr){
            if(index > 0){
                str+='<tr>'+
                        '<td class="hastd_xuhao">'+parseInt((CurrentPage-1)*10 + index)+'</td>'+
                        '<td class="hastd_ProductCategory" title="'+currentValue.ProductCategory+'">'+currentValue.ProductCategory+'</td>'+
                        '<td class="hastd_Model" title="'+currentValue.Model+'">'+currentValue.Model+'</td>'+
                        '<td class="hastd_Sum" title="'+currentValue.Sum+'">'+currentValue.Sum+'</td>'+
                        '<td class="hastd_Year_2016" title="'+currentValue.Year_2016+'">'+currentValue.Year_2016+'</td>'+
                        '<td class="hastd_Year_2017" title="'+currentValue.Year_2017+'">'+currentValue.Year_2017+'</td>'+
                        '<td class="hastd_Year_2018" title="'+currentValue.Year_2018+'">'+currentValue.Year_2018+'</td>'+
                        '<td class="hastd_UnitPrice" title="'+currentValue.UnitPrice+'">'+currentValue.UnitPrice+'</td>'+
                    '</tr>';
            }
        });
    }
    $("#calendarSale_a table tbody").empty().append(str);
    // 表格列宽调整
    $(".gl_table_style").siblings("div.JCLRgrips").remove();
    setTimeout(function(){
        $(".gl_table_style").colResizable({
            gripInnerHtml: '<span class="glyphicon glyphicon-resize-horizontal" aria-hidden="true"></span>',
            partialRefresh: true,
            postbackSafe: false,
            minWidth: 60
        });
    },100);

    $("#calendarSale_a #currentPage2").text(CurrentPage);
    $("#calendarSale_a #allPage2").text(pageCount);
    pageStyle(CurrentPage, pageCount, "2");
}

// 渲染并做下一步动作
function calendarSaleRenderDataTrue(data, CurrentPage, pageCount, ProductCategory, Model, Column){
    calendarSaleRenderData(data, CurrentPage, pageCount);
    $("#classify_search").val(ProductCategory);
    $("input.model_search").val(Model);
    // 历年销量对比排序图标显隐
    // if(Column == ""){
    //     $(".gl_table_style th span.glyphicon").hide();
    // }else{
    //     $(".gl_table_style th.col_"+Column+" span.glyphicon").show();
    // }
    $(".gl_table_style th:not(.col_"+Column+") span.glyphicon").fadeOut(50, function(){
        $(".gl_table_style th.col_"+Column+" span.glyphicon").fadeIn(50);
    });
    HotProductState.calendarSaleProductCategory = ProductCategory;
    HotProductState.calendarSaleModel = Model;
    HotProductState.calendarSaleColumn = Column;
}

// 搜索初始化
function sousuoInit(){
    $(".classify_search_div div.input-group-btn>button:eq(0)").attr("title", "选择字段").text("选择字段");
    $(".classify_search_div #classify_search, .classify_search_div .model_search").fadeOut(50,function(){
        $(this).val("");
    });
}

// 翻页函数入口
function getDataByGoPage(currentPage){
    var ProductCategory = HotProductState.calendarSaleProductCategory;
    var Model = HotProductState.calendarSaleModel;
    var Column = HotProductState.calendarSaleColumn;
    calendarSaleGetData(ProductCategory, Model, Column, currentPage);
}


    // 翻页
$("#fistPage2").click(function(){
    var currentPage =1;
    getDataByGoPage(currentPage);
});

$("#lastPage2").click(function(){
    var currentPage =Number($("#allPage2").text());
    getDataByGoPage(currentPage);
});

$("#upPage2").click(function(){
    var currentPage = Number($("#currentPage2").text());
    if(currentPage == 1){
        return;
    }else{
        currentPage--;
        getDataByGoPage(currentPage);
    }
});

$("#nextPage2").click(function(){
    var currentPage = Number($("#currentPage2").text());
    var pageCounts = Number($("#allPage2").text());
    if(currentPage == pageCounts){
        return;
    }else{
        currentPage++;
        getDataByGoPage(currentPage);
    }
});
    //跳页
$("#Gotojump2").click(function(){
    var currentPage = $("#jumpNumber2").val().trim();
    var pageCounts = Number($("#allPage2").text());
    var oldCurrentPage = Number($("#currentPage2").text());
    if(currentPage == oldCurrentPage || currentPage <= 0 || currentPage>pageCounts){
        $("#jumpNumber2").val('');
        return;
    }else{
        getDataByGoPage(currentPage);
    }
});


var isClick;

// 销量统计 雷达图 搜索
$(".time_search_div_r>button").click(function(){
    var iText = $("#time_search_input").val();
    if(!iText) return false;
    var iTextArr = iText.split(" ~ ");
    if(iTextArr.length == 0 || iTextArr.length == 1) return false;
    var searchStartTime = iTextArr[0].replace("年","-").replace("月","-").replace("日","");
    var searchEndTime = iTextArr[1].replace("年","-").replace("月","-").replace("日","");
    if($(this).children("span").hasClass("glyphicon-usd")){
        var searchClassify = $(".hotpro_classify").val();
        $.MsgBox.Remind("提示", "数据加载中......");
        window.location.href = 'HotProduct?StartTime='+ searchStartTime +'&EndTime='+ searchEndTime +'&classify='+searchClassify+'&currentPage=1';
    }else if($(this).children("span").hasClass("glyphicon-stats")){
        // var methods = "POST";
        // var iurl = "OriginalData";
        // var FName = null;
        // var data = {
        //     StartTime: searchStartTime,
        //     endTime: searchEndTime
        // };
        // globalUrlDownloadFile(methods, iurl, FName, data);
        $.ajax({
            type: "POST",
            url: "OriginalData",
            data: {
                StartTime: searchStartTime,
                endTime: searchEndTime
            },
            dataType: "text",
            success: function(data){
                if(data.indexOf("设置成功")>-1){
                    CoverShow("雷达图日期设置成功！");
                    var item = {};
                    item.startTime = searchStartTime;
                    item.endTime = searchEndTime;
                    var itemStr = JSON.stringify(item);
                    $(".hotpro_rada").val(itemStr);
                }else{
                    CoverShow("雷达图日期设置失败！");
                }
            },
            error: function(){
                CoverShow("网络繁忙！");
            }
        });
    }
});

// 历年销量搜索字段点击
$(document).on("click",".classify_search_div ul.dropdown-menu>li",function(){
    var iText = $(this).text();
    var iTitle = $(this).attr("title");
    $(this).parent().siblings("button:nth-child(1)").attr("title",iTitle).text(iText);
    if(iText == "产品型号"){
        // $(".classify_search_div #classify_search").removeClass("runleft_animate2").addClass("runleft_animate");
        // $(".classify_search_div .model_search").removeClass("runleft_animate").addClass("runleft_animate1").val("");
        $(".classify_search_div #classify_search").fadeOut(50,function(){
            $(".classify_search_div .model_search").fadeIn(50).val("");
        });
    }else if(iText == "产品类型"){
        // $(".classify_search_div .model_search").removeClass("runleft_animate1").addClass("runleft_animate");
        // $(".classify_search_div #classify_search").removeClass("runleft_animate").addClass("runleft_animate2").val("");
        $(".classify_search_div .model_search").fadeOut(50,function(){
            $(".classify_search_div #classify_search").fadeIn(50).val("");
        });
    }
});

/****************** 跳页 **********************/
var StartTime = $(".hotpro_StartTime").val();
var EndTime = $(".hotpro_EndTime").val();
var allPage = $("#allPage").html();

//点击价值当量刷新页面按照价值当量排序
$(".title .jiazhi").click(function(){
    window.location.href = 'HotProduct?StartTime='+ StartTime +'&EndTime='+ EndTime +'&classify=TotalPrice&currentPage=1';
});
//点击数量刷新页面按照数量排序
$(".title .shuliang").click(function(){
    window.location.href = 'HotProduct?StartTime='+ StartTime +'&EndTime='+ EndTime +'&currentPage=1';
});
//点击价值当量刷新页面按照价值当量排序
$(".title .xinghao").click(function(){
    window.location.href = 'HotProduct?StartTime='+ StartTime +'&EndTime='+ EndTime +'&classify=Model&currentPage=1';
});
//点击价值当量刷新页面按照价值当量排序
$(".title .miaoshu").click(function(){
    window.location.href = 'HotProduct?StartTime='+ StartTime +'&EndTime='+ EndTime +'&classify=Description&currentPage=1';
});
//点击价值当量刷新页面按照价值当量排序
$(".title .liebiaojian").click(function(){
    window.location.href = 'HotProduct?StartTime='+ StartTime +'&EndTime='+ EndTime +'&classify=CostPrice&currentPage=1';
});

function FistPage(arg) {
    var arg1 = arg.replace(/[^0-9]/ig,"");
    var classify = arg.replace(arg1, '');
    window.location.href = 'HotProduct?StartTime='+ StartTime +'&EndTime='+ EndTime +'&classify='+ classify +'&currentPage=1';
}
function UpPage(arg) {
    var arg1 = arg.replace(/[^0-9]/ig,"");
    var classify = arg.replace(arg1, '');
    window.location.href = 'HotProduct?StartTime='+StartTime +'&EndTime='+EndTime +'&classify='+ classify +'&currentPage='+arg1;
}
function NextPage(arg) {
    var arg1 = arg.replace(/[^0-9]/ig,"");
    var classify = arg.replace(arg1, '');
    window.location.href = 'HotProduct?StartTime='+StartTime +'&EndTime='+EndTime +'&classify='+ classify +'&currentPage='+arg1;
}
function PageJump(arg) {
    var arg1 = arg.replace(/[^0-9]/ig,"");
    var classify = arg.replace(arg1, '');
    var jumpNumber = document.getElementById("jumpNumber").value;
    if (jumpNumber == null || jumpNumber == 0) {
        jumpNumber = $('#currentPage').html();
    } else if (jumpNumber > parseInt($('#allPage').html())) {
        jumpNumber = $('#allPage').html();
    }
    window.location.href = 'HotProduct?StartTime='+StartTime +'&EndTime='+EndTime +'&classify='+ classify +'&currentPage='+jumpNumber;
}
function LastPage(arg) {
    var arg1 = arg.replace(/[^0-9]/ig,"");
    var classify = arg.replace(arg1, '');
    window.location.href = 'HotProduct?StartTime='+StartTime +'&EndTime='+EndTime +'&classify='+ classify +'&currentPage='+arg1;
}

var ModelArr = [];//防止添加重复型号

var option = { //可以去官网上根据每个案例不同的option去写各种图形
	/*backgroundColor: 'rgba(0,0,0,0.2)',*/
    tooltip: {   //提示框，鼠标悬浮交互时的信息提示
        /* show:true,
         trigger: 'axis'  */
    },
    legend: {
        type: 'scroll',
        orient: 'horizontal',
        bottom: 20,
        textStyle: {
            color: '#fff',
            fontSize: 12
        }
    },
    calculable : true,
    radar: {    //极坐标
        indicator: [
            {text: '单价',max: 100},
            {text: '历史销量',max: 100},
            {text: '价值当量',max: 100},
            {text: '客户分布',max: 100},
            {text: '出货量',max: 100},
        ],
        shape: 'circle',
        splitNumber: 5,
        name:{
            show: true, // 是否显示工艺等文字
            formatter: null, // 工艺等文字的显示形式
            textStyle: {
                color:'rgba(255,255,255,0.9)', // 工艺等文字颜色
                fontWeight: 'bold',
                fontSize: 16,
            }
        },
        splitLine : {
            show : true,
            lineStyle: {
                color: [
                    'rgba(0, 0, 0, 0.5)','rgba(0, 0, 0, 0.5)', 
                    'rgba(0, 0, 0, 0.5)','rgba(0, 0, 0, 0.5)', 
                    'rgba(0, 0, 0, 0.5)'
                ]
            }
        },
        axisLine: {
            symbol: ['none', 'arrow'],
            lineStyle: {
                color: 'rgba(0, 0, 0, 0.6)'
            }
        }
    },
    series: [{         // 驱动图表生成的数据内容数组，数组中每一项为一个系列的选项及数据
        name: '产品热销',
        type: 'radar',
        symbol: "none",
        data: [{
            value: [],      //外部加载，也可以通过ajax去加载外部数据。
        }]
    }]
};

// 升序降序 true为升序
var sortIndex = -1;
function orderasc(flag) {
    var isortIndex;
    if(sortIndex == -1){
        $(".RadarChartTableBox .sortTd span").removeClass("glyphicon-sort").addClass("glyphicon-sort-by-attributes");
        isortIndex = -1;
    }else{
        $(".RadarChartTableBox .sortTd span").toggleClass("glyphicon-sort-by-attributes glyphicon-sort-by-attributes-alt");
        isortIndex = 7;
    }
    var tableObject = $('#RadarChartTableCont'); //获取id为tableSort的table对象
    // var tbHead = tableObject.children('thead'); //获取table对象下的thead
    // var tbHeadTh = tbHead.find('tr th'); //获取thead下的tr下的th
    var tbBody = tableObject.children('tbody'); //获取table对象下的tbody
    var tbBodyTr = tbBody.find('tr'); //获取tbody下的tr
    var index = 7;
    globalSortTable(isortIndex, tbBodyTr, "number", index, "ASC", "#RadarChartTableCont tbody", function(){
        $.Response_Load.After("数据排序完成！",300);
        sortIndex = index;
    });


    // 旧方法2
    // var trs = $("#RadarChartTableCont tbody>tr");
    // console.log(trs);
    // for(var i = 0; i < trs.length - 1; i++) {
    //     for(var j = 0; j < trs.length - i - 1; j++) {
    //         var tr1 = trs[j];
    //         var tr2 = trs[j+1];
    //         console.log(tr1);
    //         console.log(tr2);
    //         var td1i = parseFloat($(tr1).find("td.Radar_Total").text());
    //         var td2i = parseFloat($(tr2).find("td.Radar_Total").text());
    //         console.log(td1i);
    //         console.log(td2i);
    //         if(flag) {
    //             if(td1i > td2i) {
    //                 var k = j+1;
    //                 var m = j+2;
    //                 $("#RadarChartTableCont tbody>tr:nth-child("+m+")").insertBefore("#RadarChartTableCont tbody>tr:nth-child("+k+")");
    //             }
    //         } else {
    //             if(td1i < td2i) {
    //                 var kk = j+1;
    //                 var mm = j+2;
    //                 $("#RadarChartTableCont tbody>tr:nth-child("+mm+")").insertBefore("#RadarChartTableCont tbody>tr:nth-child("+kk+")");
    //                 // $(tr2).insertBefore(tr1);
    //                 // tr2.insertBefore(tr1);
    //             }
    //         }
    //     }
    // }
    // $.Response_Load.After("数据排序完成！",300);

    // 旧方法1
    // var tbody = document.getElementById("RadarChartTableCont");
    // var trs = tbody.children[0].children;
    // console.log(tbody.children[0]);
    // console.log(trs);
    // for(var i = 0; i < trs.length - 1; i++) {
    //     for(var j = 0; j < trs.length - i - 1; j++) {
    //         var tr1 = trs[j];
    //         var tr2 = trs[j + 1];
    //         var td1i = parseFloat(tr1.children[7].innerHTML);
    //         var td2i = parseFloat(tr2.children[7].innerHTML);
    //         if(flag) {
    //             if(td1i > td2i) {
    //                 tbody.children[0].insertBefore(tr2, tr1);
    //             }
    //         } else {
    //             if(td1i < td2i) {
    //                 tbody.children[0].insertBefore(tr2, tr1);
    //             }
    //         }
    //     }
    // }
    // $.Response_Load.After("数据排序完成！",300);
}


// 加载完成
$(function() {
    // 表格列宽调整
    setTimeout(function(){
        $("#global_table_style").colResizable({
            gripInnerHtml: '<span class="glyphicon glyphicon-resize-horizontal" aria-hidden="true"></span>',
            partialRefresh: true,
            postbackSafe: true,
            minWidth: 60
        });
    },100);

    pageStyle(Number($('#currentPage').text()),Number($('#allPage').text()));
    // 日期范围选择框
    var iiStartTime = $(".hotpro_StartTime").val();
    var iiEndTime = $(".hotpro_EndTime").val();
    var curYearMD;
    if(!iiStartTime || iiStartTime == "--" || iiStartTime == "0000-00-00"){
        curYearMD = (new Date()).getFullYear() + "年" +"01月01日";
    }else{
        var curYearMDArr = iiStartTime.split("-");
        curYearMD = curYearMDArr[0] + "年" + curYearMDArr[1] + "月" + curYearMDArr[2] + "日";
    }
    var iTodayTrue;
    if(!iiEndTime || iiEndTime == "--" || iiEndTime == "0000-00-00"){
        iTodayTrue = globalGetToday(true);
    }else{
        var iTodayTrueArr = iiEndTime.split("-");
        iTodayTrue = iTodayTrueArr[0] + "年" + iTodayTrueArr[1] + "月" + iTodayTrueArr[2] + "日";
    }
    var iToday = globalGetToday();
    /*layui date*/
    //自定义重要日
    // laydate.render({
    //   elem: '#test18'
    //   ,mark: {
    //     '0-10-14': '生日'
    //     ,'0-12-31': '跨年' //每年的日期
    //     ,'0-0-10': '工资' //每月某天
    //     ,'0-0-15': '月中'
    //     ,'2017-8-15': '' //如果为空字符，则默认显示数字+徽章
    //     ,'2099-10-14': '呵呵'
    //   }
    //   ,done: function(value, date){
    //     if(date.year === 2017 && date.month === 8 && date.date === 15){ //点击2017年8月15日，弹出提示语
    //       alert('这一天是：中国人民抗日战争胜利72周年');
    //     }
    //   }
    // }); 
    //执行一个laydate实例
    laydate.render({
        elem: '#time_search_input', //指定元素
        // range: true, //范围
        // range: '→', //范围
        range: '~', //范围
        format: 'yyyy年MM月dd日',
        calendar: true, //开启公历节日
        min: '2016-01-01',
        // min: '2016年01月01日', // error disabled
        max: iToday,
        // ready: function(){
        //     ins22.hint('日期可选值设定在 <br> 2016年01月01日 到 '+iTodayTrue);
        // },
        value: curYearMD+' ~ '+iTodayTrue, // 默认值
        theme: 'grid',
        // theme: '#393D49'
    });

    // 雷达图初始化
    var myChart = echarts.init(document.getElementById('RadarMap'));
    option.series[0].data[0].value=[50,50,50,50,50];  // 加载数据到data中
    option.series[0].data[0].name ='Demo';
    myChart.setOption(option, true);   //为echarts对象加载数据
    $(window).resize(function() {//这是能够让图表自适应的代码
        myChart.resize();
    });

    // 热销产品排序图标显隐
    var iSearchClassify = $(".hotpro_classify").val();
    switch(iSearchClassify)
    {
        case "Model":
            $("#global_table_style thead .xinghao>span").fadeIn(200);
            break;
        case "Description":
            $("#global_table_style thead .miaoshu>span").fadeIn(200);
            break;
        case "CostPrice":
            $("#global_table_style thead .liebiaojian>span").fadeIn(200);
            break;
        case "TotalPrice":
            $("#global_table_style thead .jiazhi>span").fadeIn(200);
            break;
        default:
            $("#global_table_style thead .shuliang>span").fadeIn(200);
    }

    // 热销产品和雷达图切换
    $('.tab_wrapper>ul>li>a').click(function(e) {
      e.preventDefault();
      $(this).tab('show');
    });
    $('.tab_wrapper>ul>li>a').on('shown.bs.tab', function (e) {
        console.log(e.target); // newly activated tab
        console.log(e.relatedTarget); // previous active tab
        console.log($(e.target));
        if($(e.target).text() == "热销产品"){
            $(".classify_search_div").slideUp(100,function(){
                $(".time_search_div").slideDown(100);
            });
            $(".time_search_div_r>button").html('<span class="glyphicon glyphicon-usd" aria-hidden="true"></span>销量统计搜索');
            laydate.render({
                elem: '#time_search_input', //指定元素
                // range: true, //范围
                // range: '→', //范围
                range: '~', //范围
                format: 'yyyy年MM月dd日',
                calendar: true, //开启公历节日
                min: '2016-01-01',
                // min: '2016年01月01日', // error disabled
                max: iToday,
                // ready: function(){
                //     ins22.hint('日期可选值设定在 <br> 2016年01月01日 到 '+iTodayTrue);
                // },
                value: curYearMD+' ~ '+iTodayTrue, // 默认值
                theme: 'grid',
                // theme: '#393D49'
            });
        }else if($(e.target).text() == "雷达图"){
            $(".classify_search_div").slideUp(100,function(){
                $(".time_search_div").slideDown(100);
            });
            $.ajax({
                type: 'get',
                url: "SalesWeight",
                dataType: 'json',
                success: function (data) {
                    console.log(data);
                    $(".SetWeightsBtn").attr("ID",data[1].ID);
                    $(".Weights_UnitPrice").text((data[1].UnitPrice*100).toFixed(0)+"%");
                    $(".Weights_Num").text((data[1].Quantity*100).toFixed(0)+"%");
                    $(".Weights_Equivalent").text((data[1].Value*100).toFixed(0)+"%");
                    $(".Weights_Distributed").text((data[1].Client*100).toFixed(0)+"%");
                    $(".Weights_Shipping").text((data[1].Shipping*100).toFixed(0)+"%");
                },
                error: function () {
                }
            });
            myChart.resize();
            $(".time_search_div_r>button").html('<span class="glyphicon glyphicon-stats" aria-hidden="true"></span>雷达图搜索');
            // 填充时间段
            var hotpro_rada = $(".hotpro_rada").val();
            var hotpro_radaObj = JSON.parse(hotpro_rada);
            var radarcurYearMD = hotpro_radaObj.startTime == "" ? ((new Date()).getFullYear() + "年" +"01月01日") : (hotpro_radaObj.startTime.split("-")[0]+"年"+hotpro_radaObj.startTime.split("-")[1]+"月"+hotpro_radaObj.startTime.split("-")[2]+"日");
            var radariTodayTrue = hotpro_radaObj.endTime == "" ? globalGetToday(true) : (hotpro_radaObj.endTime.split("-")[0]+"年"+hotpro_radaObj.endTime.split("-")[1]+"月"+hotpro_radaObj.endTime.split("-")[2]+"日");
            laydate.render({
                elem: '#time_search_input', //指定元素
                // range: true, //范围
                // range: '→', //范围
                range: '~', //范围
                format: 'yyyy年MM月dd日',
                calendar: true, //开启公历节日
                min: '2016-01-01',
                // min: '2016年01月01日', // error disabled
                max: iToday,
                // ready: function(){
                //     ins22.hint('日期可选值设定在 <br> 2016年01月01日 到 '+iTodayTrue);
                // },
                value: radarcurYearMD+' ~ '+radariTodayTrue, // 默认值
                theme: 'grid',
                // theme: '#393D49'
            });
        }else if($(e.target).text() == "历年销量对比"){
            $(".time_search_div").slideUp(100,function(){
                $(".classify_search_div").slideDown(100);
                sousuoInit();
            });
            calendarSaleGetData("", "", "", 1);
        }
    });
    

    /*??????*/
    var allType;
    $(".TypeSel").on("change",function(){
        $(".Applyadd, .Apply").attr("disabled",false);
        var ProductCategory = $(".TypeSel").val();
        $.ajax({
            type : 'get',
            url : "GetModelByCategory",
            data:{
                ProductCategory : ProductCategory
            },
            contentType: "application/json;charset=utf-8",
            dataType : 'json',
            beforeSend: function(XMLHttpRequest){
                $.Response_Load.Before("获取产品型号列表提示","正在获取产品型号列表......",200);
            },
            complete: function(XMLHttpRequest, textStatus){
                if(textStatus=='success'){
                    $.Response_Load.After("获取产品列表完成！",200);
                }
            },
            success : function (data) {
                console.log(data);
                allType = data;
                var str="<option class='allPro'>全部产品型号</option><option selected='selected'   class='firstOption' >All</option>";
                for(var i = 0 ; i < data.length;i++){
                    str += "<option>"+data[i]+"</option>";
                }
                $(".CurrentTypeSel").empty().append(str);
            },
            error : function () {
            }
        });
    });
    //应用
    $(".Applyadd").click(function () {
        sortIndex = -1;
        $(".RadarChartTableBox .sortTd span").removeClass("glyphicon-sort-by-attributes glyphicon-sort-by-attributes-alt").addClass("glyphicon-sort");
    	isClick = false;
        var totalNum = 0;
        for(var i = 0 ; i < $(".WeightsValue").length;i++){
            totalNum += parseFloat($(".WeightsValue").eq(i).text());
        }
        var model = $(".TypeSel").val();
        var Model = "";//全部选择时的参数
        var cols="";
        $(".RadarChartTableCont tbody>tr").each(function(){
        var text = $(this).children("td:first").text();
        var textisAll = $(this).children("td.Radar_product").text();
            if (textisAll!="All") {
                cols+=text+",";
            }
        });
        cols=cols.length>0?cols.substring(0,cols.length-1):"";
        // alert(cols);
        if($(".allPro").is(":checked") && cols.indexOf(model)>=0){
            CoverShow("该类名已有型号在表，先删除非All型号再添加！");
        }else{
            if(ModelArr.indexOf($(".CurrentTypeSel").val()) < 0 && ModelArr.indexOf($(".TypeSel").val()+$(".CurrentTypeSel").val()) < 0){
            if(totalNum == 100){
            	var display1 = $(".display1");
            	for(var dis = 0;dis<display1.length;dis++){
            		  var s = $(".display1").eq(dis).text();
            		  if(s.indexOf("评分")>-1){
            			  $(".display1").eq(dis).text(s.substring(0,s.length-2));}
            	}
                if($(".CurrentTypeSel option").is(":checked")){
                    var flag = "false";
                    var ProductCategory = $(".CurrentTypeSel").val();
                    if(ProductCategory == '全部产品型号') {
                        ProductCategory= $(".TypeSel").val();
                        Model = "All";
                    }
                    else if(ProductCategory == "All"){
                        ProductCategory= $(".TypeSel").val();
                        /*Model = "";*/
                    }
                    else{
                        Model = ProductCategory;
                    }
                }
                
                if($(".TypeSel").val() =="其他" && $(".CurrentTypeSel").val() == "All"){console.log("qita")}
                else{
                    //将类型或型号存入数组 验证重复
                    var CurrentTypeSel = $(".CurrentTypeSel").val();
                    if($(".CurrentTypeSel").val() == "All" ){CurrentTypeSel = $(".TypeSel").val()+$(".CurrentTypeSel").val()}
                    else if($(".CurrentTypeSel").val() == "全部产品型号"){CurrentTypeSel = $(".TypeSel").val()+"全部产品型号"};
                    ModelArr.push(CurrentTypeSel);

                    $.ajax({
                        type: 'get',
                        url: "OriginalData",
                        data: {
                            ProductCategory: ProductCategory,
                            /*flag :flag,*/
                            Model: Model
                        },
                        dataType : 'json',

                        // test
                        cache: false,
                        beforeSend: function(XMLHttpRequest){
                            $.Response_Load.Before("数据添加提示","数据正在传输......",200);
                        },
                        complete: function(XMLHttpRequest, textStatus){
                            if(textStatus=='success'){
                                $.Response_Load.After("数据传输完成！",300);
                            }
                        },

                        success: function (data) {
                            // 判断是否为全部添加
                            if(Array.isArray(data)){
                                if(data.length>4 ){
                                    var TotalScore = 0;
                                    var str= '<tr class="RadarTr" >'+
                                        '<td class="Radar_Name"  title="'+model+'">'+model+'</td>'+
                                        '<td class="Radar_product" title="'+$(".CurrentTypeSel").val()+'">'+$(".CurrentTypeSel").val()+'</td>'+
                                        '<td class="Radar_UnitPrice">'+(data[0]*1).toFixed(1)+'</td>'+
                                        '<td class="Radar_Num" >'+(data[1]*1).toFixed(1)+'</td>'+
                                        '<td class="Radar_Equivalent" >'+(data[2]*1).toFixed(1)+'</td>'+
                                        '<td class="Radar_Distributed" >'+(data[3]*1).toFixed(1)+'</td>'+
                                        '<td class="Radar_Shipping" >'+(data[4]*1).toFixed(1)+'</td>'+
                                        '<td class="Radar_Total" >'+TotalScore.toFixed(2)+'</td>'+
                                        '<td class="Radar_Del" style="cursor:pointer;"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></td>'+
                                        '</tr>';
                                    $(".RadarChartTableCont tbody").append(str);
                                    if($(".RadarTr").length >10){
                                        $(".RadarChartTableBox").css({"paddingRight":"17px"});
                                        $(".RadarChartTable, .RadarChartTableCont").addClass("over_td");
                                    }
                                }
                                else{
                                    CoverShow("数据获取有误！");
                                }
                            }
                            else{  // 全部添加
                                ModelArr = allType;
                                ModelArr.push($(".TypeSel").val()+$(".CurrentTypeSel").val());
                                var dataArr = [];
                                for(var n in data){
                                    var str="";
                                    var TotalScore = 0;
                                    str = '<tr class="RadarTr" style="height: 30px;">'+
                                        '<td class="Radar_Name"  title="'+model+'">'+model+'</td>'+
                                        '<td class="Radar_product" title="'+n+'">'+n+'</td>'+
                                        '<td class="Radar_UnitPrice">'+(data[n][0]*1).toFixed(1)+'</td>'+
                                        '<td class="Radar_Num" >'+(data[n][1]*1).toFixed(1)+'</td>'+
                                        '<td class="Radar_Equivalent" >'+(data[n][2]*1).toFixed(1)+'</td>'+
                                        '<td class="Radar_Distributed" >'+(data[n][3]*1).toFixed(1)+'</td>'+
                                        '<td class="Radar_Shipping" >'+(data[n][4]*1).toFixed(1)+'</td>'+
                                        '<td class="Radar_Total" >'+TotalScore.toFixed(2)+'</td>'+
                                        '<td class="Radar_Del" style="cursor:pointer;"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></td>'+
                                        '</tr>';
                                    $(".RadarChartTableCont tbody").append(str);
                                    if($(".RadarTr").length >10){
                                        $(".RadarChartTableBox").css({"paddingRight":"17px"});
                                        $(".RadarChartTable, .RadarChartTableCont").addClass("over_td");
                                    }
                                }
                            }
                        },

                        error : function () {
                        }
                    });
                }
            }
            else{
                CoverShow("权重和不为1！");
            }
            }
            else{
            CoverShow("该型号已添加！");
            }
        }
    });

    // 计算
    $(".Apply").click(function () {
    	sortIndex = -1;
        $(".RadarChartTableBox .sortTd span").removeClass("glyphicon-sort-by-attributes glyphicon-sort-by-attributes-alt").addClass("glyphicon-sort");
        var totalNum = 0;
        for(var i = 0 ; i < $(".WeightsValue").length;i++){
            totalNum += parseFloat($(".WeightsValue").eq(i).text());
        }
        var model = $(".TypeSel").val();
        var Model = "";//全部选择时的参数
        var calcitem;
        var ModelList = [];
        if(totalNum == 100){
            if($(".RadarTr").length>1){
                // $(".bgLarge").fadeIn(200);
                // $(".bgLargeTextCont span").text("正在收集类名和产品型号......");
                $.Response_Load.Before("计算数据提示","正在收集类名和产品型号......",200);
                setTimeout(function(){calcApply(ModelList, calcitem);},300);
            }
            else{
                CoverShow("数目小于2！");
            }
        }
        else{
            CoverShow("权重和不为1！");
        }
    });

    // 历年销量对比搜索
    $(".classify_search_y").click(function(){
        var ProductCategory, Model, Column;
        var isearch = $(".classify_search_div .input-group-btn>button:eq(0)").text();
        if(isearch == "产品型号"){
            ProductCategory = "";
            Model = $("input.model_search").val().trim();
            if(!Model){
                CoverShow("请填写型号！");
                return false;
            }
        }else if(isearch == "产品类型"){
            ProductCategory = $("#classify_search").val();
            Model = "";
            if(!ProductCategory){
                CoverShow("请选择产品类型！");
                return false;
            }
        }else{
            CoverShow("请选择搜索字段！");
            return false;
        }
        Column = $(".gl_table_style th span.glyphicon:visible").length ? $(".gl_table_style th span.glyphicon:visible").parent().attr("class").replace("col_", "") : "";
        calendarSaleGetData(ProductCategory, Model, Column, 1);
    });
    $(".classify_search_n").click(function(){
        sousuoInit();
        calendarSaleGetData("", "", "", 1);
    });

    // 历年销量对比，字段排序
    $(document).on("click",'.gl_table_style th[class^="col_"]',function(){
        var ProductCategory = HotProductState.calendarSaleProductCategory;
        var Model = HotProductState.calendarSaleModel;
        var Column = $(this).attr("class").replace("col_", "");
        var currentPage = Number($("span#currentPage2").text());
        calendarSaleGetData(ProductCategory, Model, Column, currentPage);
    });

/*doc onload end*/  
});

// 设置权重
$(".SetWeightsBtn").click(function(){
    var WeightsValue = 0;
    for(var i = 0;i <  $(".WeightsValue").length; i++){
        WeightsValue += parseFloat($(".WeightsValue").eq(i).text());
    }
    if(WeightsValue == 100 ){
        var ID = $(this).attr("ID");
        // 浮点数传回后台
        var UnitPrice =  parseFloat($(".WeightsValue").eq(0).text())/100;
        var Quantity =  parseFloat($(".WeightsValue").eq(1).text())/100;
        var Value =  parseFloat($(".WeightsValue").eq(2).text())/100;
        var Client =  parseFloat($(".WeightsValue").eq(3).text())/100;
        var Shipping =  parseFloat($(".WeightsValue").eq(4).text())/100;
        $.ajax({
            type : 'get',
            url : "SaveSaleWeight",
            data : {
                ID : ID,
                UnitPrice :UnitPrice,
                Quantity :Quantity,
                Value :Value,
                Client :Client,
                Shipping :Shipping,
            },
            dataType : 'json',
            success : function (data) {
                if(data){
                    $.MsgBox_Unload.Alert('提示',"设置成功");
                }
            },
            error : function () {
                CoverShow("权重和不为1！");
            }
        });
    }
    else{
        CoverShow("权重和不为1！");
    }
});

$(document).on("click",".Radar_Del",function(){
    $.Response_Load.Before("删除单行数据提示","正在删除数据并绘制雷达图......",200);
    var that = $(this);
    setTimeout(function(){delUI(that)},300);
});

$(document).on("click",".delAll",function(){       //删除所有
    $(".RadarChartTableBox").css({"paddingRight":"0px"});
    $(".RadarChartTable, .RadarChartTableCont").removeClass("over_td");
    ModelArr = [];
    $(".RadarTr").remove();
    var myChart = echarts.init(document.getElementById('RadarMap'));
    var legend={left : "center",top : "bottom", data:[]};
    option.legend=legend;
    option.series[0].data = [];
    myChart.setOption(option);
});

var sortmark = true;  //降序
$(document).on("click",".sortTd",function(){       //排序
    if($("#RadarChartTableCont tbody>tr").length){
        $.Response_Load.Before("排序提示","数据正在排序......",200);
        sortmark =! sortmark;
        setTimeout(function(){orderasc(sortmark);},300);
    }else{
        CoverShow("表格数据为空，请添加！");
    }
});
function CoverShow(Msg){
    $.MsgBox_Unload.Alert('提示',Msg);
}

function delUI(that) {
    if($(".RadarTr").length <12){
        $(".RadarChartTableBox").css({"paddingRight":"0px"});
        $(".RadarChartTable, .RadarChartTableCont").removeClass("over_td");
    }
    var Radar_product = that.parent().find(".Radar_product").text();
    if(Radar_product == "All"){Radar_product = that.parent().find(".Radar_Name").text()+"All";}
    ModelArr.splice($.inArray(Radar_product,ModelArr),1);
    var Radar_Total = that.parent().find(".Radar_Total").text();
    that.parent().remove();
    if (isClick){
        //获取数据 加载图形
        var dataArr = [];
        var legendArr = [];
        for(var j= 0 ; j < $(".RadarTr").length;j++){
            var dataArrObj = {};
            var optionArr1 = [];
            optionArr1.push($(".RadarTr").eq(j).find(".Radar_UnitPrice").text());
            optionArr1.push($(".RadarTr").eq(j).find(".Radar_Num").text());
            optionArr1.push($(".RadarTr").eq(j).find(".Radar_Equivalent").text());
            optionArr1.push($(".RadarTr").eq(j).find(".Radar_Distributed").text());
            optionArr1.push($(".RadarTr").eq(j).find(".Radar_Shipping").text());
            dataArrObj.value = optionArr1;
            if($(".RadarTr").eq(j).find(".Radar_product").text() == "All"){
                dataArrObj.name = $(".RadarTr").eq(j).find(".Radar_Name").text()+"-"+ $(".RadarTr").eq(j).find(".Radar_product").text();
                legendArr.push($(".RadarTr").eq(j).find(".Radar_Name").text()+"-"+ $(".RadarTr").eq(j).find(".Radar_product").text());
            }
            else{
                dataArrObj.name = $(".RadarTr").eq(j).find(".Radar_product").text();
                legendArr.push($(".RadarTr").eq(j).find(".Radar_product").text());
            }
            dataArr.push(dataArrObj);
        }
        var myChart = echarts.init(document.getElementById('RadarMap'));
        var legend={left : "center",top : "bottom", data:legendArr};
        option.legend=legend;
        option.series[0].data = dataArr;
        myChart.setOption(option);
    }
    if($(".RadarTr").length==0){
        ModelArr = [];
        var myChart0 = echarts.init(document.getElementById('RadarMap'));
        var legend0={left : "center",top : "bottom", data:[]};
        option.legend=legend0;
        option.series[0].data = [];
        myChart0.setOption(option);
    }
    // $(".bgLargeTextCont span").text("删除单行数据成功！");
    // $(".bgLarge").delay(400).fadeOut(300);
    $.Response_Load.After("删除单行数据成功！",300);
}

function calcApply(ModelList, calcitem) {
    isClick = true;
    var display2 = $(".display1");
    for(var dis = 0;dis<display2.length;dis++){
        var s = $(".display1").eq(dis).text();
        if(s.indexOf("评分")==-1){
            $(".display1").eq(dis).text(s+"评分");
        }
    }
    for(var ii = 0;ii<$(".RadarTr").length;ii++){
        if($(".RadarTr").eq(ii).find(".Radar_product").text()=="All"){
            calcitem = $(".RadarTr").eq(ii).find(".Radar_Name").text();
        }
        else{
            calcitem = $(".RadarTr").eq(ii).find(".Radar_product").text();
        }
        ModelList.push(calcitem);
    }
    $.ajax({
        type : 'post',
        url : "Normalize",
        data : {
            ModelList:ModelList
        },
        //data: ModelList,
        // JSON.stringify(ModelList)
        //traditional: true,//后台可接收数组
        //contentType: "application/json;charset=utf-8",
        dataType : 'json',
        headers: {'Content-Type':'application/x-www-form-urlencoded;charset:UTF-8'},
        beforeSend: function(XMLHttpRequest){
            $.Response_Load.Loading("收集已完成，数据正在传输......");
        },
        complete: function(XMLHttpRequest, textStatus){
            if(textStatus=='success'){
                $.Response_Load.After("数据计算完成！",200);
            }
        },
        success : function (data) {
            var dataArr = [];
            var indexArr = [];
            for(var n in data){
                var TotalScore = 0;
                for(var i = 0; i < 5; i++){
                    var FinalScore =data[n][i]*parseFloat($(".WeightsValue").eq(i).text());
                    TotalScore += FinalScore;
                }
                indexArr.push(n);
                var jj = indexArr.length-1;
                $(".RadarTr").eq(jj).find(".Radar_UnitPrice").text((data[n][0]*100).toFixed(1));
                $(".RadarTr").eq(jj).find(".Radar_Num").text((data[n][1]*100).toFixed(1));
                $(".RadarTr").eq(jj).find(".Radar_Equivalent").text((data[n][2]*100).toFixed(1));
                $(".RadarTr").eq(jj).find(".Radar_Distributed").text((data[n][3]*100).toFixed(1));
                $(".RadarTr").eq(jj).find(".Radar_Shipping").text((data[n][4]*100).toFixed(1));
                $(".RadarTr").eq(jj).find(".Radar_Total").text(TotalScore.toFixed(2));
                
                if($(".RadarTr").length >10){
                    $(".RadarChartTableBox").css({"paddingRight":"17px"});
                    $(".RadarChartTable, .RadarChartTableCont").addClass("over_td");
                }
            }

            //获取数据 加载图形
            var legendArr = [];
            for(var j= 0 ; j < $(".RadarTr").length;j++){
                var dataArrObj = {};
                var optionArr1 = [];
                optionArr1.push($(".RadarTr").eq(j).find(".Radar_UnitPrice").text());
                optionArr1.push($(".RadarTr").eq(j).find(".Radar_Num").text());
                optionArr1.push($(".RadarTr").eq(j).find(".Radar_Equivalent").text());
                optionArr1.push($(".RadarTr").eq(j).find(".Radar_Distributed").text());
                optionArr1.push($(".RadarTr").eq(j).find(".Radar_Shipping").text());
                dataArrObj.value = optionArr1;

                if($(".RadarTr").eq(j).find(".Radar_product").text() == "All"){
                    dataArrObj.name = $(".RadarTr").eq(j).find(".Radar_Name").text()+"-"+ $(".RadarTr").eq(j).find(".Radar_product").text()
                    legendArr.push($(".RadarTr").eq(j).find(".Radar_Name").text()+"-"+ $(".RadarTr").eq(j).find(".Radar_product").text());
                }
                else{
                    dataArrObj.name = $(".RadarTr").eq(j).find(".Radar_product").text();
                    legendArr.push($(".RadarTr").eq(j).find(".Radar_product").text());
                }

                dataArr.push(dataArrObj);
            }
            var myChart = echarts.init(document.getElementById('RadarMap'));
            var legend={left : "center",top : "bottom", data:legendArr};
            option.legend=legend;
            option.series[0].data = dataArr;
            myChart.setOption(option);

        },
        error : function () {
        }
    });
}