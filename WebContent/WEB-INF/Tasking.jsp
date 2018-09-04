<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <!-- 为移动设备添加 viewport -->
        <meta name="viewport"
        content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
        <title>人事任务分配</title>
        <link rel="shortcut icon" href="image/eoulu.ico" />
        <link rel="bookmark" href="image/eoulu.ico" />
        <link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="css/global/eouluCustom.css" type="text/css">
        <link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="css/Tasking.css" type="text/css">
        <style>
            .u-admin a {
                vertical-align: top !important;
            }

            .u-admin .u-i-2, .u-admin .u-i-3, .u-admin .u-i-4 {
                vertical-align: top !important;
            }

            .lava_with_border li a {
                vertical-align: top !important;
            }

            h5, h6 {
                margin-top: 0px;
                margin-bottom: 0px;
            }

            hr {
                margin-top: 0px;
                margin-bottom: 0px;
                border: 0;
                border-top: 1px solid #999;
            }

            .nav-container-in {
                width: 100% !important;
            }

            .g-nav-ul li {
                box-sizing: content-box;
            }

            /*布局*/
            html,body{
                width:100%;
                height:100%;
            }

            #tasking_wrapper {
                position:fixed;
                overflow:auto;
                width:100%;
                height:100%;
                box-sizing:border-box;
            }

            #tasking_sticker {
                width:100%;
                min-height:100%;
                box-sizing:border-box;
                position:relative;
            }

            #tasking_sticker-con {
                padding-bottom:40px;
                box-sizing:border-box;

            }

            #tasking_footer {
                margin-top:-40px;
            }
        </style>
    </head>
    <body>
        <div id="tasking_wrapper">
            <div id="tasking_sticker">
                <div id="tasking_sticker-con">
                    <%@include file="top.jsp"%>
                    <div class="container-fluid">
                        <div class="g_container">
                            <%@include file="nav.jsp"%>
                            <div class="g_body">
                                <div class="m_button">
                                    <div class="m_button_l">
                                        <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" value="添加">
                                    </div>
                                    <div class="m_button_r"><input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w100" value="数据统计"></div>
                                </div>
                                <!-- 页面表格主体 -->
                                <div class="m_table">
                                    <table class="eou-table-collapse">
                                        <thead>
                                            <tr>
                                                <th class="t1">序号</th>
                                                <th class="t2">任务名称</th>
                                                <th class="t3">具体描述<span><i class="fa fa-plus-square" id="fa_button1"></i></span></th>
                                                <th style="display: none;" class="none_td1">类别</th>
                                                <th style="display: none;" class="none_td2">评分</th>
                                                <th class="t4">发布人</th>
                                                <th class="t5">负责人</th>
                                                <th class="t6">进度状况</th>
                                                <th class="t7">发布时间</th>
                                                <th class="t8">任务期限时间</th>
                                                <th class="t9">任务完成时间</th>
                                                <th class="t10">是否完成</th>
                                                <th class="t11">编号</th>
                                                <th class="t12">发布</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <!-- <tr>
                                                <td class="update_td">1</td>
                                                <td class="task_Title_td">三国</td>
                                                <td class="task_des_td">三国</td>
                                                <td style="display:none;" class="task_category_td">三国</td>
                                                <td style="display:none;" class="task_Review_td">三国</td>
                                                <td class="task_releaseman_td">三国</td>
                                                <td class="task_dutyman_td">三国</td>
                                                <td class="task_progress_td">三国</td>
                                                <td class="task_PublishedDate_td">三国</td>
                                                <td class="task_LimitDate_td">三国</td>
                                                <td class="task_CompleteDate_td">三国</td>
                                                <td class="task_IsCompleted_td">三国</td>
                                                <td class="task_no_td">三国</td>
                                                <td class="publish_td">三国</td>
                                            </tr> -->
                                        </tbody>
                                    </table>
                                </div>
                                <!-- 翻页 -->
                                <div class="m_page">
                                    <div class="u_page">
                                        <div class="pageInfo">当前是第&nbsp;<span id="currentPage">1</span>&nbsp;页,&nbsp;总计&nbsp;<span id="allPage"></span>页</div>
                                        <div class="changePage">
                                            <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" value="首页" id="fistPage" name="fistPage">
                                            <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" value="上一页" id="upPage">
                                            <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" value="下一页" id="nextPage"> 跳到第 <input
                                                type="text" id="jumpNumber" name="jumpNumber" class="jumpNumber"
                                                style="width: 30px; color: #000"> 页 
                                            <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" value="GO" id="Gotojump"
                                                name="Gotojump">
                                            <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" value="尾页" id="lastPage" name="lastPage">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 弹出组件 -->
                    <div class="bg_cover" style="display: none;"></div>
                    <div class="add_tasking" style="display: none;">
                        <div class="add_tasking_tit">
                            <div class="add_tasking_tit_l">添加任务分配</div>
                            <div class="add_tasking_tit_r">关闭</div>
                        </div>
                        <div class="add_tasking_body">
                            <div class="add_tasking_body_in">
                                <div class="add_tasking_body_tit">任务分配</div>
                                <div class="add_tasking_body_main">
                                    <div class="add_tasking_body_main_l">
                                        <div class="line_0">
                                            <div class="line_01">任务名称</div>
                                            <div class="line_02">
                                                <input type="text" class="task_Title" maxlength="10">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">具体描述</div>
                                            <div class="line_02 tips_out" style="position: relative;">
                                                <span class="plus_icon"><i></i></span><input type="text" class="task_des" readonly="readonly">
                                                <div class="tips_body">
                                                    <div class="tips_body_tit"></div>
                                                    <div class="tips_body_in">
                                                        
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">类别</div>
                                            <div class="line_02">
                                                <select name="" id="" class="task_category">
                                                    <option value="0">请选择类别</option>
                                                    <option value="招聘">招聘</option>
                                                    <option value="行政">行政</option>
                                                    <option value="培训">培训</option>
                                                    <option value="员工关系">员工关系</option>
                                                    <option value="文档">文档</option>
                                                    <option value="外出活动">外出活动</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">进度状况</div>
                                            <div class="line_02 tips_out" style="position: relative;">
                                                <span class="plus_icon"><i></i></span><input type="text" class="task_progress" readonly="readonly">
                                                <div class="tips_body">
                                                    <div class="tips_body_tit"></div>
                                                    <div class="tips_body_in">
                                                        
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="add_tasking_body_main_m">
                                        <div class="line_0">
                                            <div class="line_01">发布人</div>
                                            <div class="line_02">
                                                <select name="" id="" class="task_releaseman">
                                                    
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">负责人</div>
                                            <div class="line_02">
                                                <select name="" id="" class="task_dutyman">
                                                    
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">编号</div>
                                            <div class="line_02">
                                                <input type="text" class="task_no">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">是否完成</div>
                                            <div class="line_02">
                                                <select name="" id="" class="task_IsCompleted">
                                                    <option value="0">请选择</option>
                                                    <option value="否">否</option>
                                                    <option value="是">是</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="add_tasking_body_main_r">
                                        <div class="line_0">
                                            <div class="line_01">发布时间</div>
                                            <div class="line_02">
                                                <input type="date" class="task_PublishedDate">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">任务期限时间</div>
                                            <div class="line_02">
                                                <input type="date" class="task_LimitDate">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">任务完成时间</div>
                                            <div class="line_02">
                                                <input type="date" class="task_CompleteDate">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="add_tasking_foot">
                            <div class="add_tasking_foot_in">
                                <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" id="tasking_addsubmit" value="提交">
                                <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" id="tasking_addclose" value="取消">
                            </div>
                        </div>
                    </div>

                    <!-- 修改 -->
                    <div class="update_tasking" style="display: none;">
                        <div class="update_tasking_tit">
                            <div class="update_tasking_tit_l">修改任务分配</div>
                            <div class="update_tasking_tit_r">关闭</div>
                        </div>
                        <div class="update_tasking_body">
                            <div class="update_tasking_body_in">
                                <div class="update_tasking_body_tit">任务分配</div>
                                <div class="update_tasking_body_main">
                                    <div class="update_tasking_body_main_l">
                                        <div class="line_0">
                                            <div class="line_01">任务名称</div>
                                            <div class="line_02">
                                                <input type="text" class="task_Title" maxlength="10">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">具体描述</div>
                                            <div class="line_02 tips_out" style="position: relative;">
                                                <span class="plus_icon"><i></i></span><input type="text" class="task_des" readonly="readonly">
                                                <div class="tips_body">
                                                    <div class="tips_body_tit"></div>
                                                    <div class="tips_body_in">
                                                        
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">类别</div>
                                            <div class="line_02">
                                                <select name="" id="" class="task_category">
                                                    <option value="0">请选择类别</option>
                                                    <option value="招聘">招聘</option>
                                                    <option value="行政">行政</option>
                                                    <option value="培训">培训</option>
                                                    <option value="员工关系">员工关系</option>
                                                    <option value="文档">文档</option>
                                                    <option value="外出活动">外出活动</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">进度状况</div>
                                            <div class="line_02 tips_out" style="position: relative;">
                                                <span class="plus_icon"><i></i></span><input type="text" class="task_progress" readonly="readonly">
                                                <div class="tips_body">
                                                    <div class="tips_body_tit"></div>
                                                    <div class="tips_body_in">
                                                        
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="update_tasking_body_main_m">
                                        <div class="line_0">
                                            <div class="line_01">发布人</div>
                                            <div class="line_02">
                                                <select name="" id="" class="task_releaseman">
                                                    
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">负责人</div>
                                            <div class="line_02">
                                                <select name="" id="" class="task_dutyman">
                                                    
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">编号</div>
                                            <div class="line_02">
                                                <input type="text" class="task_no">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">是否完成</div>
                                            <div class="line_02">
                                                <select name="" id="" class="task_IsCompleted">
                                                    <option value="否">否</option>
                                                    <option value="是">是</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="update_tasking_body_main_r">
                                        <div class="line_0">
                                            <div class="line_01">评分</div>
                                            <div class="line_02">
                                                <select name="" id="" class="task_Review">
                                                    <option value="0">请选择评分</option>
                                                    <option value="60">60</option>
                                                    <option value="70">70</option>
                                                    <option value="80">80</option>
                                                    <option value="90">90</option>
                                                    <option value="100">100</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">发布时间</div>
                                            <div class="line_02">
                                                <input type="date" class="task_PublishedDate">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">任务期限时间</div>
                                            <div class="line_02">
                                                <input type="date" class="task_LimitDate">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">任务完成时间</div>
                                            <div class="line_02">
                                                <input type="date" class="task_CompleteDate">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="update_tasking_foot">
                            <div class="update_tasking_foot_in">
                                <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" id="tasking_updatesubmit" value="提交">
                                <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" id="tasking_updateclose" value="取消">
                            </div>
                        </div>
                    </div>

                    <!-- 发布框 -->
                    <div class="publish_div" style="display: none;">
                        <div class="publish_div_tit">发布提示信息</div>
                        <div class="publish_div_body"></div>
                        <div class="publish_div_foot">
                            <div class="publish_div_foot_in"></div>
                        </div>
                    </div>
                <!-- tasking_sticker-con结束 -->
                </div>
            <!-- tasking_sticker结束 -->
            </div>

            <!-- tasking_footer -->
            <div id="tasking_footer">
                <div id="eoulu-copy-out" style="height:40px;width:calc(100% - 2px);">
                    <div style="width:100%;height:5px;"></div>
                    <div id="eoulu-copy" style="width:100%;height:35px;font-size:12px;color:#888;line-height:35px;z-index: 2;">
                        <hr style="height:1px;color:#999;width: calc(100% - 3px);" />
                        <div style="width:100%;text-align:center;display:inline-block;">Copyright  ©&nbsp;<span class="YEAR">2018</span>&nbsp<a href="http://www.eoulu.com/h-col-268.html" class="EHref" target="_blank" style="color:blue;">Eoulu</a> Tech. Co.,Ltd.</div>
                    </div>
                </div>
            </div>

        <!-- tasking_wrapper结束 -->
        </div>
    <!-- <script src="js/libs/jquery-3.3.1.min.js"></script> -->
    <!-- <script src="js/global/myFunction.js"></script> -->
    <!-- <script src="js/msgbox_unload.js"></script> -->
    <script>
        // 是否有发布权限Publish_has_span
        function IsPublish(){
            if($("#Publish_has_span").length>0){
                $(".m_table thead .t12").show();
                $(".m_table tbody .publish_td").show();
            }else{
                $(".m_table thead .t12").hide();
                $(".m_table tbody .publish_td").hide();
            }
        }

        function goPageTdNone(){
            $('#fa_button1').removeClass('fa-minus-square');
            $('.m_table thead').find('.none_td1').hide();
            $('.m_table thead').find('.none_td2').hide();
        }

        var task_currentpage = 1;
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

        // 发送状态样式
        function publishStatus(){
            $(".m_table .publish_td input").each(function(){
                if($(this).val()=="已发布"){
                    $(this).css({"background-color":"#31b0d5","color":"#333"});
                }else if($(this).val()=="待发布"){
                    $(this).css({"background-color":"#00aeef","color":"#fff"});
                }
            });
        }

        // 加载刷新表格
        function taskingTableShowAjax(){
            var str = "";
            $.ajax({
                type:'GET',
                url:'Tasking',
                dataType:'json',
                data:{
                    CurrentPage:1
                },
                success: function(data){
                    console.log(data);
                    console.log(typeof data);
                    console.log(data.data);
                    console.log(typeof data.data);
                    var pageCounts = data.pageCount;
                    for(var i =1;i<data.data.length;i++){
                        str+='<tr>'+
                                '<td class="update_td" value="'+data.data[i].ID+'">'+parseInt((task_currentpage-1)*10 + i)+'</td>'+
                                '<td class="task_Title_td" title="'+data.data[i].Title+'">'+data.data[i].Title+'</td>'+
                                '<td class="task_des_td" title="'+data.data[i].Description+'">'+data.data[i].Description+'</td>'+
                                '<td style="display:none;" class="task_category_td" title="'+data.data[i].Classify+'">'+data.data[i].Classify+'</td>'+
                                '<td style="display:none;" class="task_Review_td">'+data.data[i].Review+'</td>'+
                                '<td class="task_releaseman_td">'+data.data[i].Publisher+'</td>'+
                                '<td class="task_dutyman_td">'+data.data[i].ResponsibleMan+'</td>'+
                                '<td class="task_progress_td" title="'+data.data[i].Progress+'">'+data.data[i].Progress+'</td>'+
                                '<td class="task_PublishedDate_td">'+data.data[i].PublishedDate+'</td>'+
                                '<td class="task_LimitDate_td">'+data.data[i].LimitDate+'</td>'+
                                '<td class="task_CompleteDate_td">'+data.data[i].CompleteDate+'</td>'+
                                '<td class="task_IsCompleted_td">'+data.data[i].IsCompleted+'</td>'+
                                '<td class="task_no_td" title="'+data.data[i].RefNO+'">'+data.data[i].RefNO+'</td>'+
                                '<td class="publish_td"><input type="button" value="'+data.data[i].IsPublished+'"></td>'+
                            '</tr>';
                    }
                    
                    $(".m_table table tbody").empty().append(str);
                    $(".m_page #currentPage").text(task_currentpage);
                    $(".m_page #allPage").text(pageCounts);
                    pageStyle(task_currentpage,pageCounts);
                },
                error: function(){
                    $.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
                },
                complete: function(XMLHttpRequest, textStatus){
                    publishStatus();
                    goPageTdNone();
                    IsPublish();
                }
            });
        }

        // 翻页刷新表格
        function taskingTableGoAjax(goCurrentPage){
            var str = "";
            $.ajax({
                type:'GET',
                url:'Tasking',
                dataType:'json',
                data:{
                    CurrentPage:goCurrentPage
                },
                success: function(data){
                    console.log("翻页了");
                    console.log(data);
                    console.log(typeof data);
                    console.log(data.data);
                    console.log(typeof data.data);
                    var pageCounts = data.pageCount;
                    for(var i =1;i<data.data.length;i++){
                        str+='<tr>'+
                                '<td class="update_td" value="'+data.data[i].ID+'">'+parseInt((goCurrentPage-1)*10 + i)+'</td>'+
                                '<td class="task_Title_td" title="'+data.data[i].Title+'">'+data.data[i].Title+'</td>'+
                                '<td class="task_des_td" title="'+data.data[i].Description+'">'+data.data[i].Description+'</td>'+
                                '<td style="display:none;" class="task_category_td" title="'+data.data[i].Classify+'">'+data.data[i].Classify+'</td>'+
                                '<td style="display:none;" class="task_Review_td">'+data.data[i].Review+'</td>'+
                                '<td class="task_releaseman_td">'+data.data[i].Publisher+'</td>'+
                                '<td class="task_dutyman_td">'+data.data[i].ResponsibleMan+'</td>'+
                                '<td class="task_progress_td" title="'+data.data[i].Progress+'">'+data.data[i].Progress+'</td>'+
                                '<td class="task_PublishedDate_td">'+data.data[i].PublishedDate+'</td>'+
                                '<td class="task_LimitDate_td">'+data.data[i].LimitDate+'</td>'+
                                '<td class="task_CompleteDate_td">'+data.data[i].CompleteDate+'</td>'+
                                '<td class="task_IsCompleted_td">'+data.data[i].IsCompleted+'</td>'+
                                '<td class="task_no_td" title="'+data.data[i].RefNO+'">'+data.data[i].RefNO+'</td>'+
                                '<td class="publish_td"><input type="button" value="'+data.data[i].IsPublished+'"></td>'+
                            '</tr>';
                    }
                    
                    $(".m_table table tbody").empty().append(str);
                    $(".m_page #currentPage").text(goCurrentPage);
                    $(".m_page #allPage").text(pageCounts);
                    pageStyle(goCurrentPage,pageCounts);
                },
                error: function(){
                    $.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
                },
                complete: function(XMLHttpRequest, textStatus){
                    publishStatus();
                    goPageTdNone();
                    IsPublish();
                }
            });
        }

        $(document).ready(function(){
            // 页面加载显示表格
            taskingTableShowAjax();
            // var currentPage = $(".pageInfo #currentPage").text();
            // var pageCounts = $(".pageInfo #allPage").text();
            // pageStyle(currentPage,pageCounts);
            // 添加弹出关闭
            $(".m_button_l input").on("click",function(){
                $(".bg_cover").fadeIn(200);
                $(".add_tasking").fadeIn(200);
                $.ajax({
                    type:'GET',
                    url:'GetStaffInfo',
                    dataType:'json',
                    data:{
                        Department:"人事部",
                        Type:"common"
                    },
                    success: function(data){
                        console.log(typeof data);
                        $(".add_tasking .task_releaseman, .add_tasking .task_dutyman").empty();
                        if(data.length>1){
                            var str = '<option value="0">请选择</option>';
                            for(var i = 1;i<data.length;i++){
                                str+='<option value="'+data[i].StaffName+'">'+data[i].StaffName+'</option>';
                            }
                            $(".add_tasking .task_releaseman").append(str);
                            $(".add_tasking .task_dutyman").append(str);
                        }
                    },
                    error: function(){
                        $.MsgBox_Unload.Alert("提示","网络繁忙，人事部员工获取失败");
                    },
                    complete: function(XMLHttpRequest, textStatus){
                        $(".add_tasking input").each(function(){
                            if($(this).attr("id")=="tasking_addsubmit" || $(this).attr("id")=="tasking_addclose"){
                                return;
                            }else{
                                $(this).val("");
                            }
                        });
                        $(".add_tasking select").each(function(){
                            $(this).val("0");
                        });
                    }
                });
            });
            $("#tasking_addclose, .add_tasking_tit_r").on("click",function(){
                $(".add_tasking").fadeOut(200);
                $(".bg_cover").fadeOut(200);
            });

            // 添加提交，再次加载表格数据
            $(document).on("click","#tasking_addsubmit",function(){
                var ID = 0;
                var PublishedDate = $(".add_tasking .task_PublishedDate").val();
                var LimitDate = $(".add_tasking .task_LimitDate").val();
                var CompleteDate = $(".add_tasking .task_CompleteDate").val();
                var Title = $(".add_tasking .task_Title").val();
                var Description = $(".add_tasking .task_des").val();
                var Classify = $(".add_tasking .task_category").val();
                var Publisher = $(".add_tasking .task_releaseman").val();
                var ResponsibleMan = $(".add_tasking .task_dutyman").val();
                var Review = 0;
                var Progress = $(".add_tasking .task_progress").val();
                var IsCompleted = $(".add_tasking .task_IsCompleted").val();
                var RefNo = $(".add_tasking .task_no").val();
                if(Classify == "0" || Classify == undefined){
                    $.MsgBox_Unload.Alert("提示","请选择类别");
                }else if(Publisher == "0" || Publisher == undefined){
                    $.MsgBox_Unload.Alert("提示","请选择发布人");
                }else if(ResponsibleMan == "0" || ResponsibleMan == undefined){
                    $.MsgBox_Unload.Alert("提示","请选择责任人");
                }else if(IsCompleted == "0" || IsCompleted == undefined){
                    $.MsgBox_Unload.Alert("提示","请选择'是否完成'");
                }else{
                    $.ajax({
                        type:'GET',
                        url:'TaskOperate',
                        dataType:'text',
                        data:{
                            Type: "add",
                            ID: ID,
                            PublishedDate: PublishedDate,
                            LimitDate: LimitDate,
                            CompleteDate: CompleteDate,
                            Title: Title,
                            Description: Description,
                            Classify: Classify,
                            Publisher: Publisher,
                            ResponsibleMan: ResponsibleMan,
                            Review: Review,
                            Progress: Progress,
                            IsCompleted: IsCompleted,
                            RefNo: RefNo
                        },
                        success: function(data){
                            console.log(data);
                            console.log(typeof data);
                            if(data == "true"){
                                $.MsgBox_Unload.Alert("提示","添加成功！");
                                $(".add_tasking").fadeOut(200);
                                $(".bg_cover").fadeOut(200);
                            }else{
                                $.MsgBox_Unload.Alert("提示","添加失败！请检查");
                            }

                        },
                        error: function(){
                            $.MsgBox_Unload.Alert("提示","服务器繁忙！");
                        },
                        complete: function(XMLHttpRequest, textStatus){
                            taskingTableShowAjax();
                            // $("#mb_btn_ok_unload").trigger("click");
                        }
                    });
                }
                
            });

            // 修改弹出关闭
            $(document).on("click",".update_td",function(){
                $(".bg_cover").fadeIn(200);
                $(".update_tasking").fadeIn(200);
                var that = $(this);
                $.ajax({
                    type:'GET',
                    url:'GetStaffInfo',
                    dataType:'json',
                    data:{
                        Department:"人事部",
                        Type:"common"
                    },
                    success: function(data){
                        console.log(typeof data);
                        $(".update_tasking .task_releaseman, .update_tasking .task_dutyman").empty();
                        if(data.length>1){
                            var str = '<option value="0">请选择</option>';
                            for(var i = 1;i<data.length;i++){
                                str+='<option value="'+data[i].StaffName+'">'+data[i].StaffName+'</option>';
                            }
                            $(".update_tasking .task_releaseman").append(str);
                            $(".update_tasking .task_dutyman").append(str);
                        }
                    },
                    error: function(){
                        $.MsgBox_Unload.Alert("提示","网络繁忙，人事部员工获取失败");
                    },
                    complete: function(XMLHttpRequest, textStatus){
                        that.siblings("td").each(function(){
                            if($(this).attr("class").indexOf("task")>-1){
                                var curClass = $(this).attr("class").replace("_td","");
                                var curVal = $(this).text().trim();
                                if(curVal == "--" || curVal == "0000-00-00"){
                                    curVal = "";
                                }
                                $(".update_tasking").find("."+curClass).val(curVal);
                            }
                        });
                    }
                });
                $(".update_tasking").attr("value",$(this).attr("value"));
            });
            $("#tasking_updateclose, .update_tasking_tit_r").on("click",function(){
                $(".update_tasking").fadeOut(200);
                $(".bg_cover").fadeOut(200);
            });

            // 修改提交，刷新表格
            $(document).on("click","#tasking_updatesubmit",function(){
                var ID = $(".update_tasking").attr("value");
                var PublishedDate = $(".update_tasking .task_PublishedDate").val();
                var LimitDate = $(".update_tasking .task_LimitDate").val();
                var CompleteDate = $(".update_tasking .task_CompleteDate").val();
                var Title = $(".update_tasking .task_Title").val();
                var Description = $(".update_tasking .task_des").val();
                var Classify = $(".update_tasking .task_category").val();
                var Publisher = $(".update_tasking .task_releaseman").val();
                var ResponsibleMan = $(".update_tasking .task_dutyman").val();
                var Review = $(".update_tasking .task_Review").val();
                var Progress = $(".update_tasking .task_progress").val();
                var IsCompleted = $(".update_tasking .task_IsCompleted").val();
                var RefNo = $(".update_tasking .task_no").val();
                if(Classify == "0" || Classify == undefined){
                    $.MsgBox_Unload.Alert("提示","请选择类别");
                }else if(Publisher == "0" || Publisher == undefined){
                    $.MsgBox_Unload.Alert("提示","请选择发布人");
                }else if(ResponsibleMan == "0" || ResponsibleMan == undefined){
                    $.MsgBox_Unload.Alert("提示","请选择责任人");
                }else if(IsCompleted == "0" || IsCompleted == undefined){
                    $.MsgBox_Unload.Alert("提示","请选择'是否完成'");
                }else{
                    $.ajax({
                        type:'GET',
                        url:'TaskOperate',
                        dataType:'text',
                        data:{
                            Type: "update",
                            ID: ID,
                            PublishedDate: PublishedDate,
                            LimitDate: LimitDate,
                            CompleteDate: CompleteDate,
                            Title: Title,
                            Description: Description,
                            Classify: Classify,
                            Publisher: Publisher,
                            ResponsibleMan: ResponsibleMan,
                            Review: Review,
                            Progress: Progress,
                            IsCompleted: IsCompleted,
                            RefNo: RefNo
                        },
                        success: function(data){
                            console.log(data);
                            console.log(typeof data);
                            if(data == "true"){
                                $.MsgBox_Unload.Alert("提示","修改成功！");
                                $(".update_tasking").fadeOut(200);
                                $(".bg_cover").fadeOut(200);
                            }else{
                                $.MsgBox_Unload.Alert("提示","修改失败！请检查");
                            }
                        },
                        error: function(){
                            $.MsgBox_Unload.Alert("提示","服务器繁忙！");
                        },
                        complete: function(XMLHttpRequest, textStatus){
                            taskingTableShowAjax();
                            // $("#mb_btn_ok_unload").trigger("click");
                        }
                    });
                }
                
            });

            $("#jumpNumber").keyup(function(){
                var newVal = $(this).val().replace(/[^\d]/g,'');
                $(this).val(newVal);
            });

            // 翻页
            $("#fistPage").click(function(){
                var currentPage =1;
                taskingTableGoAjax(currentPage);
                goPageTdNone();
            });

            $("#lastPage").click(function(){
                var currentPage =Number($("#allPage").text());
                taskingTableGoAjax(currentPage);
                goPageTdNone();
            });

            $("#upPage").click(function(){
                var currentPage = Number($("#currentPage").text());
                if(currentPage == 1){
                    return;
                }else{
                    currentPage--;
                }
                taskingTableGoAjax(currentPage);
                goPageTdNone();
            });

            $("#nextPage").click(function(){
                var currentPage = Number($("#currentPage").text());
                var pageCounts = Number($("#allPage").text());
                if(currentPage == pageCounts){
                    return;
                }else{
                    currentPage++;
                }
                taskingTableGoAjax(currentPage);
                goPageTdNone();
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
                    taskingTableGoAjax(currentPage);
                    goPageTdNone();
                }
            });


            // 发布按钮事件
            $(document).on("click",".publish_td input",function(){
                $(".bg_cover").fadeIn(200);
                $(".publish_div").fadeIn(200);
                $(".publish_div").attr("value",$(this).parent().siblings("td.update_td").attr("value"));
                if($(this).val()=="待发布"){
                    $(".publish_div_body").text("是否发布？");
                    var str1 = '<input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" id="publish_yes" value="是">'+
                    '<input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" id="publish_no" value="否">';
                    $(".publish_div_foot_in").html(str1);
                }else if($(this).val()=="已发布"){
                    $(".publish_div_body").text("您已发布过！");
                    var str2 = '<input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" id="has_publish" value="好的">';
                    $(".publish_div_foot_in").html(str2);
                }
            });

            // 是 否 好的
            $(document).on("click","#publish_no, #has_publish",function(){
                $(".publish_div").fadeOut(200);
                $(".bg_cover").fadeOut(200);
            });
            $(document).on("click","#publish_yes",function(){
                var email_curPage = Number($(".pageInfo #currentPage").text());
                var email_data = {};
                var ID = $(".publish_div").attr("value");
                var currentTr = $(".m_table tbody tr").find("td.update_td[value='"+ID+"']").parent();
                var PublishedDate = currentTr.find(".task_PublishedDate_td").text();
                var LimitDate = currentTr.find(".task_LimitDate_td").text();
                var Title = currentTr.find(".task_Title_td").text();
                var Description = currentTr.find(".task_des_td").text();
                var ResponsibleMan = currentTr.find(".task_dutyman_td").text();
                if(PublishedDate == "--"||PublishedDate==""||PublishedDate=="0000-00-00"){
                    $.MsgBox_Unload.Alert("提示","发布日期为空，请检查");
                    return;
                }else if(LimitDate == "--"||LimitDate==""||LimitDate=="0000-00-00"){
                    $.MsgBox_Unload.Alert("提示","期限日期为空，请检查");
                    return;
                }else{
                    email_data.ID = ID;
                    email_data.PublishedDate = PublishedDate;
                    email_data.LimitDate = LimitDate;
                    email_data.Title = Title;
                    email_data.Description = Description;
                    email_data.ResponsibleMan = ResponsibleMan;
                    $.ajax({
                        type:'POST',
                        url:'TaskOperate',
                        dataType:'text',
                        data:email_data,
                        contentType:"application/x-www-form-urlencoded;charset=utf-8",
                        success: function(data){
                            console.log(data);
                            console.log(typeof data);
                            $.MsgBox_Unload.Alert("提示",data);
                        },
                        error: function(){
                            $.MsgBox_Unload.Alert("提示","服务器繁忙！");
                        },
                        complete: function(XMLHttpRequest, textStatus){
                            // taskingTableShowAjax();
                            $(".publish_div").fadeOut(200);
                            $(".bg_cover").fadeOut(200);
                            taskingTableGoAjax(email_curPage);
                        }
                    });
                }
            });

            // 隐藏列显隐
            $(document).on("click","#fa_button1",function(){
                $('#fa_button1').toggleClass('fa-minus-square');
                $('.m_table thead').find('.none_td1').toggle();
                $('.m_table thead').find('.none_td2').toggle();
                $('.m_table tbody').find('.task_category_td').toggle();
                $('.m_table tbody').find('.task_Review_td').toggle();
            });

            $(".m_button_r input").on("click",function(e){
                if ( e && e.preventDefault ){
                    e.preventDefault();
                }else{
                    window.event.returnValue = false;
                }
                // var curStaHref = window.location.href;
                window.open("TaskStatistics");
            });

            IsPublish();
        });

        $(document).on("click",".tips_out input",function(e){
            e.stopPropagation();
            $(this).siblings("span.plus_icon").children("i").toggleClass("close_icon");
            $(this).siblings(".tips_body").toggleClass("tips_body_wh");
            $(this).siblings(".tips_body").children(".tips_body_tit").toggleClass("tips_body_tit_wh");
            $(this).siblings(".tips_body").children(".tips_body_in").toggleClass("tips_body_in_wh");
            if($(this).siblings(".plus_icon").children("i").hasClass("close_icon")){
                var curInpVal = $(this).val();
                var that = $(this);
                setTimeout(function(){
                    // var curInpVal0 = curInpVal;
                    that.siblings(".tips_body").children(".tips_body_tit").text("点击左侧即可保存所填");
                    var str1 = '<textarea cols="34" rows="6"></textarea>'
                    that.siblings(".tips_body").children(".tips_body_in").html(str1);
                    that.siblings(".tips_body").children(".tips_body_in").children("textarea").val(curInpVal);
                },700);
            }else{
                var info = $(this).siblings(".tips_body").children(".tips_body_in").children("textarea").val();
                if(info==" "){
                    $(this).val("");
                }else{
                    if(info.trim()!=""&&info!=undefined){
                        $(this).val(info.trim());
                        console.log($(this).val());
                    }
                }
                var that1 = $(this);
                setTimeout(function(){
                    that1.siblings(".tips_body").children(".tips_body_tit").text("");
                    that1.siblings(".tips_body").children(".tips_body_in").empty();
                },400);
            }
        });
        $(document).on("click","span.plus_icon",function(e){
            e.stopPropagation();
            $(this).siblings("input").trigger("click");
        });

        $(document).on("click",".task_des_td",function(e){
            e.stopPropagation();
            var ll = e.pageX;
            var tt = e.pageY;
            var valtd = $(this).text();
            $(this).toggleClass("tips_span_hook");
            if(valtd==""||valtd=="--"||valtd==undefined){
                $(".tips_span").text("").css({"left":ll+"px","top":tt+"px","opacity": "0","visibility": "hidden"});
                return;
            }else{
                var str = '<span class="tips_span"></span>';
                if($(".tips_span").length==0){
                    $("#tasking_sticker").append(str);
                }
                if($(this).hasClass("tips_span_hook")){
                    $(".task_des_td").removeClass("tips_span_hook");
                    $(this).addClass("tips_span_hook");
                    $(".tips_span").text(valtd).css({"left":ll+"px","top":tt+"px","opacity": "1","visibility": "visible"});
                }else{
                    $(".task_des_td").removeClass("tips_span_hook");
                    $(".tips_span").text("").css({"left":ll+"px","top":tt+"px","opacity": "0","visibility": "hidden"});
                }
            }
        });

        $(document).on("click",".task_progress_td",function(e){
            e.stopPropagation();
            var ll = e.pageX;
            var tt = e.pageY;
            var valtd = $(this).text();
            $(this).toggleClass("tips_span_hook1");
            if(valtd==""||valtd=="--"||valtd==undefined){
                $(".tips_span1").text("").css({"left":ll+"px","top":tt+"px","opacity": "0","visibility": "hidden"});
                return;
            }else{
                var str = '<span class="tips_span1"></span>';
                if($(".tips_span1").length==0){
                    $("#tasking_sticker").append(str);
                }
                if($(this).hasClass("tips_span_hook1")){
                    $(".task_progress_td").removeClass("tips_span_hook1");
                    $(this).addClass("tips_span_hook1");
                    $(".tips_span1").text(valtd).css({"left":ll+"px","top":tt+"px","opacity": "1","visibility": "visible"});
                }else{
                    $(".task_progress_td").removeClass("tips_span_hook1");
                    $(".tips_span1").text("").css({"left":ll+"px","top":tt+"px","opacity": "0","visibility": "hidden"});
                }
            }
        });
    </script>
    </body>
</html>