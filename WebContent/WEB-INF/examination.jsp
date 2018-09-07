<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        <title>考核明细</title>
        <link rel="shortcut icon" href="image/eoulu.ico" />
        <link rel="bookmark" href="image/eoulu.ico" />
        <link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css">
        <!-- delete -->
        <!-- <link rel="stylesheet" href="css/global/dispatchLoading.css" type="text/css"> -->
        <!-- <link rel="stylesheet" href="css/libs/bootstrap-multiselect.css" type="text/css"> -->
        <!-- <link rel="stylesheet" href="css/global/eouluCustom.css" type="text/css"> -->
        <!-- <link rel="stylesheet" href="css/examination.css" type="text/css"> -->
        <link rel="stylesheet" href="css/modules/personnel/examination-3a846d3f0d.min.css" type="text/css">
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

            #NonStandard_wrapper {
                position:fixed;
                overflow:auto;
                width:100%;
                height:100%;
                box-sizing:border-box;
            }

            #NonStandard_sticker {
                width:100%;
                min-height:100%;
                box-sizing:border-box;
            }

            #NonStandard_sticker-con {
                padding-bottom:40px;
                box-sizing:border-box;
            }

            #NonStandard_footer {
                margin-top:-40px;
            }

        </style>
    </head>
    <body>
        <div class="loading_div_g_div" style="position: fixed;top: 0;bottom: 0;left: 0;right: 0;z-index: 100;width: 100vw;height: 100vh;background-color: #5bc0de;filter:alpha(opacity=90);-moz-opacity:0.9;-khtml-opacity:0.9;opacity: 0.9;display: -webkit-flex;display: flex;justify-content: center;align-items: center;">
            <img src="image/loading/Spinner-1s-200px.gif" alt="loading。。。">
        </div>
        <div id="NonStandard_wrapper">
            <div id="NonStandard_sticker">
                <div id="NonStandard_sticker-con">
                    <%@include file="top.jsp"%>
                    <div class="container-fluid">
                        <div class="g_container">
                            <%@include file="nav.jsp"%>
                            <div class="g_body">
                                <!-- 上方切换与搜索框 -->
                                <div class="g_container_info">
                                    <div class="g_container_info_l">
                                        <ul>
                                            <li class="current_tab" data-classify="exam_statistics_tab"><span>考核数据统计</span></li>
                                            <li data-classify="exam_detail_tab"><span>考核详细信息</span></li>
                                        </ul>
                                        <!-- <span class="exam_statistics_tab" id="current_tab">考核数据统计</span>
                                        <span class="exam_detail_tab">考核详细信息</span> -->
                                    </div>
                                    <div class="g_container_info_r">
                                        <!-- <form class="form-inline exam_statistics_form">
                                            <div class="form-group">
                                                <label class="sr-only" for="exampleInputAmount">科目</label>
                                                <div class="input-group">
                                                    <div class="input-group-addon">科目</div>
                                                    <select name="subject_select" id="exam_statistics_subject" class="form-control">
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="sr-only" for="exampleInputAmount">试题</label>
                                                <div class="input-group">
                                                    <div class="input-group-addon">试题</div>
                                                    <select name="paper_select" id="exam_statistics_paper" class="form-control">
                                                    </select>
                                                </div>
                                            </div>
                                            <button type="button" class="btn btn-info" id="exam_statistics_freshen">刷新</button>
                                        </form> -->

                                        <form class="form-inline exam_detail_form">
                                            <div class="form-group">
                                                <label class="sr-only" for="exampleInputAmount">科目</label>
                                                <div class="input-group">
                                                    <div class="input-group-addon">科目</div>
                                                    <select name="subject_select" id="exam_detail_subject" class="form-control">
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="sr-only" for="exampleInputAmount">试题</label>
                                                <div class="input-group">
                                                    <div class="input-group-addon">试题</div>
                                                    <select name="paper_select" id="exam_detail_paper" class="form-control">
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group form_sel_depart" style="display: none;">
                                                <label class="sr-only" for="exampleInputAmount">部门</label>
                                                <div class="input-group">
                                                    <div class="input-group-addon">部门</div>
                                                    <select name="" id="exam_detail_department" class="form-control">
                                                    </select>
                                                </div>
                                            </div>
                                            <button type="button" class="btn btn-info" id="exam_detail_freshen">刷新</button>
                                        </form>
                                    </div>
                                </div>

                                <!-- 考核数据统计body -->
                                <div class="exam_statistics_content">
                                    <div class="exam_statistics_chart">
                                        <div class="exam_statistics_chart_tit"></div>
                                        <div class="exam_statistics_chart_cont" id="exam_statis_chart"></div>
                                    </div>
                                </div>
                                <!-- 考核数据统计body结束 -->

                                <!-- 考核详细信息body -->
                                <div class="exam_detail_content">
                                    <div class="exam_btn_div">
                                        <input class="btn btn-info" type="button" value="添加">
                                        <input class="btn btn-info InScore" type="button" value="录入分数">
                                        <!-- <input class="btn btn-info InState" type="button" value="更改状态"> -->
                                    </div>
                                    
                                    <div class="presentation_div">
                                        <ul>
                                            <li class="active" data-classify="exam_detail_more_presentation"><span>详细信息</span></li>
                                            <li data-classify="exam_detail_notice_presentation"><span>考核通知</span></li>
                                        </ul>
                                    </div>

                                    <!-- 页面表格主体 -->
                                    <div class="m_table exam_detail_more_detable">
                                        <table class="eou-table-collapse">
                                            <thead>
                                                <tr>
                                                    <th class="tt1">顺序号</th>
                                                    <th class="tt2">部门</th>
                                                    <th class="tt3">姓名</th>
                                                    <th class="tt4">考核分数</th>
                                                    <th class="tt5">考核时间</th>
                                                    <!-- <th class="tt6">考试确认状态</th> -->
                                                </tr>
                                            </thead>
                                            <tbody></tbody>
                                        </table>
                                    </div>
                                    
                                    <!-- 动手实验考核明细统计页面表格 -->
                                    <!-- <div class="m_table3">
                                        <div class="fixed_table_div">
                                            <table class="eou-table-collapse">
                                                <thead>
                                                    <tr>
                                                        <th>序号</th>
                                                        <th>科目</th>
                                                        <th>编号</th>
                                                        <th>名称</th>
                                                        <th>时间</th>
                                                    </tr>
                                                </thead>
                                                <tbody></tbody>
                                            </table>
                                        </div>
                                        <div class="scroll_table_div">
                                            <table class="eou-table-collapse">
                                                <thead>
                                                </thead>
                                                <tbody></tbody>
                                            </table>
                                        </div>
                                    </div> -->
                                    <!-- 考核通知统计页面表格 -->
                                    <div class="m_table4 exam_detail_notice_detable">
                                        <table class="eou-table-collapse">
                                            <thead>
                                                <th>顺序号</th>
                                                <th>科目</th>
                                                <th>编号</th>
                                                <!-- <th>名称</th> -->
                                                <th>时间</th>
                                                <th>部门</th>
                                                <th>考核人员</th>
                                                <th>发布时间</th>
                                                <th>发布</th>
                                            </thead>
                                            <tbody></tbody>
                                        </table>
                                    </div>

                                    <!-- 页面表格结束 -->
                                    <!-- 翻页 -->
                                    <div class="m_page">
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
                                    </div><!-- 翻页结束 -->
                                </div><!-- 考核详细信息body结束 -->
                            </div>
                        </div>
                    </div>

                    <!-- 弹出组件 -->
                    <div class="bg_cover" style="display: none;"></div>
                    <!-- 添加 -->
                    <div class="add_NonStandard" style="display: none;">
                        <div class="add_NonStandard_tit">
                            <div class="add_NonStandard_tit_l">添加课程</div>
                            <div class="add_NonStandard_tit_r">关闭</div>
                        </div>
                        <div class="add_NonStandard_body">
                            <div class="add_NonStandard_body_in">
                                <div class="add_NonStandard_body_tit">课程详情</div>
                                <div class="add_NonStandard_body_main">
                                    <div class="add_NonStandard_body_main_l">
                                        <div class="line_0">
                                            <div class="line_01">序号</div>
                                            <div class="line_02">
                                                <input type="text" class="info_SerialNumber">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">科目</div>
                                            <div class="line_02">
                                                <input type="text" class="info_Subject">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">编号</div>
                                            <div class="line_02">
                                            	<input type="text" class="info_Number">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">时间</div>
                                            <div class="line_02">
                                                <input type="date" class="info_Time">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- <div class="textarea-div">
                                    <span>文件上传</span>
                                    <input type="text" class="info_FilePath" readonly="readonly" disabled="disabled">
                                	<input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w80" value="文件上传" id="FilePath1">
                                </div>
                                <div class="textarea-div">
                                    <span class="remarkSpan">备&nbsp;&nbsp;注</span>
                                    <textarea cols="80" rows="5" class="info_Remarks eou-bstr-input"></textarea>
                                </div> -->
                            </div>
                        </div>
                        <div class="add_NonStandard_foot">
                            <div class="add_NonStandard_foot_in">
                                <input type="button" class="eou_new_btn eou_new_radius eou-button-30 eou-button-w60" id="NonStandard_addsubmit" value="提交">
                                <input type="button" class="eou_new_btn eou_new_radius eou-button-30 eou-button-w60" id="NonStandard_addclose" value="取消">
                            </div>
                        </div>
                    </div>
                    <!-- 添加结束 -->
                    <!-- 修改 -->
                    <div class="update_NonStandard" style="display: none;">
                        <div class="update_NonStandard_tit">
                            <div class="update_NonStandard_tit_l">修改课程</div>
                            <div class="update_NonStandard_tit_r">关闭</div>
                        </div>
                        <div class="update_NonStandard_body">
                            <div class="update_NonStandard_body_in">
                                <div class="update_NonStandard_body_tit">课程详情</div>
                                <div class="update_NonStandard_body_main">
                                    <div class="update_NonStandard_body_main_l">
                                        <div class="line_0">
                                            <div class="line_01">序号</div>
                                            <div class="line_02">
                                                <input type="text" class="info_SerialNumber">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">科目</div>
                                            <div class="line_02">
                                                <input type="text" class="info_Subject">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">编号</div>
                                            <div class="line_02">
                                                <input type="text" class="info_Number">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">时间</div>
                                            <div class="line_02">
                                                <input type="date" class="info_Time">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- <div class="textarea-div">
                                    <span>文件上传</span>
                                    <input type="text" class="info_FilePath" readonly="readonly" disabled="disabled">
                                    <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w80" value="文件上传" id="FilePath1">
                                </div>
                                <div class="textarea-div">
                                    <span class="remarkSpan">备&nbsp;&nbsp;注</span>
                                    <textarea cols="80" rows="5" class="info_Remarks eou-bstr-input"></textarea>
                                </div> -->
                            </div>
                        </div>
                        <div class="update_NonStandard_foot">
                            <div class="update_NonStandard_foot_in">
                                <input type="button" class="eou_new_btn eou_new_radius eou-button-30 eou-button-w60" id="NonStandard_updatesubmit" value="提交">
                                <input type="button" class="eou_new_btn eou_new_radius eou-button-30 eou-button-w60" id="NonStandard_updateclose" value="取消">
                            </div>
                        </div>
                    </div>

                    <!-- 发布 -->
                    <div class="publish_NonStandard" style="display: none;">
                        <div class="publish_NonStandard_tit">
                            <div class="publish_NonStandard_tit_l">发布</div>
                            <div class="publish_NonStandard_tit_r">关闭</div>
                        </div>
                        <div class="publish_NonStandard_body">
                            <div class="publish_NonStandard_body_in">
                                <div class="publish_NonStandard_body_tit">选择收件人发布</div>
                                <div class="publish_NonStandard_body_main">
                                    <div class="publish_NonStandard_body_main_l">
                                        <ul id="publish_Candidate">
                                        </ul>
                                    </div>
                                </div>
                                <!-- <div class="textarea-div">
                                    <span>文件上传</span>
                                    <input type="text" class="info_FilePath" readonly="readonly" disabled="disabled">
                                    <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w80" value="文件上传" id="FilePath1">
                                </div>
                                <div class="textarea-div">
                                    <span class="remarkSpan">备&nbsp;&nbsp;注</span>
                                    <textarea cols="80" rows="5" class="info_Remarks eou-bstr-input"></textarea>
                                </div> -->
                            </div>
                        </div>
                        <div class="publish_NonStandard_foot">
                            <div class="publish_NonStandard_foot_in">
                                <input type="button" class="eou_new_btn eou_new_radius eou-button-30 eou-button-w60" id="NonStandard_publishSave" value="发布">
                                <input type="button" class="eou_new_btn eou_new_radius eou-button-30 eou-button-w60" id="NonStandard_publishSend" value="取消">
                            </div>
                        </div>
                    </div>
                    
                    <!-- 发布框 -->
                    <!-- <div class="publish_div" style="display: none;">
                        <div class="publish_div_tit">发布提示信息</div>
                        <div class="publish_div_body"></div>
                        <div class="publish_div_foot">
                            <div class="publish_div_foot_in"></div>
                        </div>
                    </div>
                    <div class="cover_bg2"></div> -->
                    <!-- 上传 -->
                    <!-- <div class="dropFileBox" style="display: none">
                        <div class="dropFile">
                            <div class="dropFileTit">
                                上传原厂报价单<span>关闭</span>
                            </div>
                            <div class="dropFileCon">
                                <div class="dropFileConMid">
                                    <div class="dropFileConMidTop">
                                        <input type="button" class="eou-button eou-button-radius eou-button-20 eou-button-w60 preUpload1" value="浏览..." title="选择文件">
                                        <input type="text" readonly="readonly" class="serFinRepUploadName" placeholder="未选择文件"><span class="isUpload"></span>
                                        <input type="file" id="serFinRepUpload" accept="application/pdf">
                                    </div>
                                    <div class="dropFileConMidMid">
                                        <div class="progressOut"></div>
                                        <div class="progressIn">0%</div>
                                    </div>
                                    <div class="dropFileConMidbot">
                                        <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60 dropUp2" value="上传">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div> -->

                    <!-- 更改考核人员 -->
                    <!-- <div class="alterExaminer">
                        <div class="alterExaminer_top">
                            <div class="alterExaminer_top_l">更改考核人员</div>
                            <div class="alterExaminer_top_r">关闭</div>
                        </div>
                        <div class="alterExaminer_body">
                            <div class="alterExaminer_body_top">
                                <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w120" value="切换到删除人员">
                            </div>
                            <div class="alterExaminer_body_mid">请输入人员姓名提交</div>
                        </div>
                        <div class="alterExaminer_foot">
                            <select id="alterExaminer_select" multiple="multiple">
                            </select>
                        </div>
                        <div class="alterExaminer_foot2">
                            <input type="text" class="add_alterExaminer eou-bstr-input">
                        </div>
                        <div class="alterExaminer_submit">
                            <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w100" value="添加人员提交">
                        </div>
                    </div> -->

                    <!-- 部门选择 -->
                    <div class="department_sele" style="display:none">
                        <div class="department_sele_top">
                            <div class="department_sele_top_l">部门选择</div>
                            <div class="department_sele_top_r">确定并关闭</div>
                        </div>
                        <div class="department_sele_body">
                            <select id="department_select" multiple="multiple">
                            </select>
                        </div>
                    </div>

                    <!-- 人员选择 -->
                    <div class="examiner_sel" style="display:none">
                        <div class="examiner_sel_top">
                            <div class="examiner_sel_top_l">人员选择</div>
                            <div class="examiner_sel_top_r">确定并关闭</div>
                        </div>
                        <div class="examiner_sel_body">
                            <select id="examiner_select" multiple="multiple">
                            </select>
                        </div>
                    </div>
                <!-- NonStandard_sticker-con结束 -->
                </div>
            <!-- NonStandard_sticker结束 -->
            </div>

            <!-- NonStandard_footer -->
            <div id="NonStandard_footer">
                <div id="eoulu-copy-out" style="height:40px;width:calc(100% - 2px);">
                    <div style="width:100%;height:5px;"></div>
                    <div id="eoulu-copy" style="width:100%;height:35px;font-size:12px;color:#888;line-height:35px;z-index: 2;">
                        <hr style="height:1px;color:#999;width: calc(100% - 3px);" />
                        <div style="width:100%;text-align:center;display:inline-block;">Copyright  ©&nbsp;<span class="YEAR">2018</span>&nbsp<a href="http://www.eoulu.com/h-col-268.html" class="EHref" target="_blank" style="color:blue;">Eoulu</a> Tech. Co.,Ltd.</div>
                    </div>
                </div>
            </div>
        <!-- NonStandard_wrapper结束 -->
        </div>
        <script src="js/libs/bootstrap.min.js"></script>
        <script src="js/libs/echarts/echarts-4.1.0.rc2.min.js"></script>
        <!-- delete -->
        <!-- <script src="js/libs/bootstrap-multiselect.js"></script> -->
        <!-- <script src="plugins/echarts/theme/eoulu_chart_1.js"></script> -->
        <!-- <script src="js/examination.js"></script> -->
    </body>
</html>