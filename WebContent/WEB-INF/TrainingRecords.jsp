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
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
        <title>培训反馈</title>
        <link rel="shortcut icon" href="image/eoulu.ico" />
        <link rel="bookmark" href="image/eoulu.ico" />
        <link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="css/global/eouluCustom.css" type="text/css">
        <link rel="stylesheet" href="css/TrainingRecords.css" type="text/css">
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
        <div id="NonStandard_wrapper">
            <div id="NonStandard_sticker">
                <div id="NonStandard_sticker-con">
                    <%@include file="top.jsp"%>
                    <div class="container-fluid">
                        <div class="g_container">
                            <%@include file="nav.jsp"%>
                            <div class="g_body">
                                <div class="m_button">
                                    <div class="m_button_l">
                                        <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" value="添加"><!-- <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" id="keysight_export" value="导出"> -->
                                    </div>
                                    <div class="m_button_r" style="display: none;">
                                        <div class="m_button_r_top">
                                            <div class="btn-group" data-toggle="buttons">
                                              <label class="btn btn-info">
                                                <input type="radio" name="querytype" autocomplete="off" value="singleSelect">单一查询
                                              </label>
                                              <label class="btn btn-info">
                                                <input type="radio" name="querytype" autocomplete="off" value="mixSelect">组合查询
                                              </label>
                                            </div>
                                        </div>
                                        <div class="m_button_r_buttom">
                                            <div class="m_button_r_l">
                                                <div class="input-group" style="display:none;">
                                                  <div class="input-group-btn">
                                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">选择<span class="caret"></span></button>
                                                    <ul class="dropdown-menu">
                                                      <li title="客户名称">客户名称</li>
                                                      <li title="联系人">联系人</li>
                                                      <li title="机台型号">机台型号</li>
                                                      <li title="chuck类型">chuck类型</li>
                                                      <li title="温度要求">温度要求</li>
                                                      <li title="显微镜">显微镜</li>
                                                    </ul>
                                                  </div><!-- /btn-group -->
                                                  <input type="text" class="form-control" aria-label="...">
                                                </div><!-- /input-group -->
                                            </div>
                                            <div class="m_button_r_m">
                                                <div class="input-group">
                                                  <div class="input-group-btn">
                                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">选择<span class="caret"></span></button>
                                                    <ul class="dropdown-menu">
                                                      <li title="客户名称">客户名称</li>
                                                      <li title="联系人">联系人</li>
                                                      <li title="机台型号">机台型号</li>
                                                      <li title="chuck类型">chuck类型</li>
                                                      <li title="温度要求">温度要求</li>
                                                      <li title="显微镜">显微镜</li>
                                                    </ul>
                                                  </div><!-- /btn-group -->
                                                  <input type="text" class="form-control" aria-label="...">
                                                </div><!-- /input-group -->
                                            </div>
                                            <div class="m_button_r_r">
                                                <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" id="NonStandard_search" value="搜索">
                                                <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" id="NonStandard_cancel" value="取消">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- 页面表格主体 -->
                                <div class="m_table">
                                    <table class="eou-table-collapse">
                                        <thead>
                                            <tr>
                                                <th class="t1">序号</th>
                                                <th class="t2">员工姓名</th>
                                                <th class="t3">部门</th>
                                                <th class="t4">职位</th>
                                                <th class="t5">培训期数</th>
                                                <th class="t6">更新时间</th>
                                                <th class="t7">培训记录</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            
                                        </tbody>
                                    </table>
                                </div>
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
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 弹出组件 -->
                    <div class="bg_cover" style="display: none;"></div>
                    <div class="bg_cover2" style="display: none;"></div>
                    <div class="add_NonStandard" style="display: none;">
                        <div class="add_NonStandard_tit">
                            <div class="add_NonStandard_tit_l">添加信息</div>
                            <div class="add_NonStandard_tit_r">关闭</div>
                        </div>
                        <div class="add_NonStandard_body">
                            <div class="add_NonStandard_body_in">
                                <div class="add_NonStandard_body_tit">基本信息</div>
                                <div class="add_NonStandard_body_main">
                                    <div class="add_NonStandard_body_main_l">
                                        <div class="line_0">
                                            <div class="line_01"><i class="eou-relevance"></i>姓名</div>
                                            <div class="line_02" style="position:relative">
                                                <input type="text" class="info_StaffName">
                                                <select name="" id="add_StaffName" multiple style="width:40vw;display:none">
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01"><i class="eou-relevance"></i>部门</div>
                                            <div class="line_02">
                                                <input type="text" class="info_Department" readonly="readonly" disabled="disabled">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01"><i class="eou-relevance"></i>职位</div>
                                            <div class="line_02">
                                            	<input type="text" class="info_Job" readonly="readonly" disabled="disabled">
                                            </div>
                                        </div>
                                    </div>
                                    <!-- 右部 -->
                                    <div class="add_NonStandard_body_main_r">
                                        <div class="line_0">
                                            <div class="line_01">培训期数</div>
                                            <div class="line_02">
                                                <input type="number" class="info_TrainingPeriods" min="0" max="100" />
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">更新时间</div>
                                            <div class="line_02">
                                                <input type="date" class="info_RefreshTime">
                                            </div>
                                        </div>
                                        <!-- <div class="line_0">
                                            <div class="line_01">价格</div>
                                            <div class="line_02">
                                                <input type="text" class="info_QuoteTotal">
                                            </div>
                                        </div> -->
                                    <!-- 右部结束   -->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="add_NonStandard_foot">
                            <div class="add_NonStandard_foot_in">
                                <input type="button" class="eou_new_btn eou_new_radius eou-button-30 eou-button-w60" id="NonStandard_addsubmit" value="提交">
                                <input type="button" class="eou_new_btn eou_new_radius eou-button-30 eou-button-w60" id="NonStandard_addclose" value="取消">
                            </div>
                        </div>
                    </div>

                    <!-- 修改 -->
                    <div class="update_NonStandard" style="display: none;">
                        <div class="update_NonStandard_tit">
                            <div class="update_NonStandard_tit_l">修改信息</div>
                            <div class="update_NonStandard_tit_r">关闭</div>
                        </div>
                        <div class="update_NonStandard_body">
                            <div class="update_NonStandard_body_in">
                                <div class="update_NonStandard_body_tit">基本信息</div>
                                <div class="update_NonStandard_body_main">
                                    <div class="update_NonStandard_body_main_l">
                                        <div class="line_0">
                                            <div class="line_01"><i class="eou-relevance"></i>姓名</div>
                                            <div class="line_02" style="position:relative">
                                                <input type="text" class="info_StaffName">
                                                <select name="" id="update_StaffName" multiple style="width:40vw;display:none">
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01"><i class="eou-relevance"></i>部门</div>
                                            <div class="line_02">
                                                <input type="text" class="info_Department" readonly="readonly" disabled="disabled">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01"><i class="eou-relevance"></i>职位</div>
                                            <div class="line_02">
                                                <input type="text" class="info_Job" readonly="readonly" disabled="disabled">
                                            </div>
                                        </div>
                                    </div>
                                    <!-- 右部 -->
                                    <div class="update_NonStandard_body_main_r">
                                        <div class="line_0">
                                            <div class="line_01">培训期数</div>
                                            <div class="line_02">
                                                <input type="number" class="info_TrainingPeriods" min="0" max="100" />
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">更新时间</div>
                                            <div class="line_02">
                                                <input type="date" class="info_RefreshTime">
                                            </div>
                                        </div>
                                        <!-- <div class="line_0">
                                            <div class="line_01">价格</div>
                                            <div class="line_02">
                                                <input type="text" class="info_QuoteTotal">
                                            </div>
                                        </div> -->
                                    <!-- 右部结束   -->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="update_NonStandard_foot">
                            <div class="update_NonStandard_foot_in">
                                <input type="button" class="eou_new_btn eou_new_radius eou-button-30 eou-button-w60" id="NonStandard_updatesubmit" value="提交">
                                <input type="button" class="eou_new_btn eou_new_radius eou-button-30 eou-button-w60" id="NonStandard_updateclose" value="取消">
                            </div>
                        </div>
                    </div>
					<!-- 培训记录查阅 -->
					<div class="assessRecord">
                        <div class="assessRecord_top">
                            <div class="assessRecord_top_l"></div>
                            <div class="assessRecord_top_r">关闭</div>
                        </div>
                        <div class="assessRecord_body">
                            <table class="assessRecordTable eou-table-collapse">
                                <thead>
                                    <tr>
                                        <th colspan="10">EOULU培训课程总表&员工培训记录</th>
                                    </tr>
                                    <tr class="readDataTr">
                                        <th colspan="2"></th>
                                        <th colspan="2"></th>
                                        <th colspan="6"></th>
                                    </tr>
                                    <tr>
                                        <th>编号</th>
                                        <th>项目</th>
                                        <th colspan="2">内容</th>
                                        <th>时间计划</th>
                                        <th>是否参加</th>
                                        <th>培训时间</th>
                                        <th>考核形式</th>
                                        <th>是否通过</th>
                                        <th>培训反馈</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <!-- 1 -->
                                    <tr>
                                        <td rowspan="4">1</td>
                                        <td rowspan="4">人事培训</td>
                                        <td>1-1</td>
                                        <td>公司简介&员工手册</td>
                                        <td rowspan="4">入职第1天</td>
                                        <td rowspan="4"></td>
                                        <td rowspan="4"></td>
                                        <td rowspan="4">实操考评</td>
                                        <td rowspan="4"></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>1-2</td>
                                        <td>销售人员规章制度</td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>1-3</td>
                                        <td>职场礼仪</td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>1-4</td>
                                        <td>文档规范管理</td>
                                        <td></td>
                                    </tr>
                                <!-- 2 -->
                                    <tr>
                                        <td rowspan="5">2</td>
                                        <td rowspan="5">商务培训</td>
                                        <td>2-1</td>
                                        <td>报备</td>
                                        <td rowspan="5">入职第1天</td>
                                        <td rowspan="5"></td>
                                        <td rowspan="5"></td>
                                        <td rowspan="5">笔试</td>
                                        <td rowspan="5"></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>2-2</td>
                                        <td>询价处理</td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>2-3</td>
                                        <td>客户信息处理</td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>2-4</td>
                                        <td>合同</td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>2-5</td>
                                        <td>单证处理</td>
                                        <td></td>
                                    </tr>
                                <!-- 3 -->
                                    <tr>
                                        <td rowspan="3">3</td>
                                        <td rowspan="3">售后服务培训</td>
                                        <td>3-1</td>
                                        <td>装机售后服务</td>
                                        <td rowspan="3">入职第1天</td>
                                        <td rowspan="3"></td>
                                        <td rowspan="3"></td>
                                        <td rowspan="3">作业</td>
                                        <td rowspan="3"></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>3-2</td>
                                        <td>服务的注意事项</td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>3-3</td>
                                        <td>写作能力培训</td>
                                        <td></td>
                                    </tr>
                                <!-- 4 -->
                                    <tr>
                                        <td rowspan="2">4</td>
                                        <td rowspan="2">产品培训</td>
                                        <td>4-1</td>
                                        <td>产品介绍及学习方法</td>
                                        <td>入职第2天</td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>4-2</td>
                                        <td>EPS150系列产品彩页翻译</td>
                                        <td>入职第1周</td>
                                        <td></td>
                                        <td></td>
                                        <td>笔试</td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                <!-- 5 -->
                                    <tr>
                                        <td>5</td>
                                        <td>售前调查表培训</td>
                                        <td>5-1</td>
                                        <td>探针台售前调查表</td>
                                        <td>入职第2天</td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                <!-- 6 -->
                                    <tr>
                                        <td rowspan="3">6</td>
                                        <td rowspan="3">EUCP培训</td>
                                        <td>6-1</td>
                                        <td>EUCP产品&使用培训</td>
                                        <td>入职第2天</td>
                                        <td></td>
                                        <td></td>
                                        <td>笔试</td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>6-2</td>
                                        <td>EUCP手册学习</td>
                                        <td>自学</td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>6-3</td>
                                        <td>EUCP操作安装课</td>
                                        <td>入职4周内</td>
                                        <td></td>
                                        <td></td>
                                        <td>实操考评</td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                <!-- 7 -->
                                    <tr>
                                        <td>7</td>
                                        <td>销售培训</td>
                                        <td>7-1</td>
                                        <td>销售指南&销售文档书写规范</td>
                                        <td>入职第1天</td>
                                        <td></td>
                                        <td></td>
                                        <td>自学</td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                <!-- 8 -->
                                    <tr>
                                        <td rowspan="2">8</td>
                                        <td rowspan="2">能力培训</td>
                                        <td>8-1</td>
                                        <td>表达能力培训</td>
                                        <td rowspan="2">入职第3天</td>
                                        <td></td>
                                        <td></td>
                                        <td>演练</td>
                                        <td rowspan="2"></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>8-2</td>
                                        <td>Sales basics</td>
                                        <td></td>
                                        <td></td>
                                        <td>自学</td>
                                        <td></td>
                                    </tr>
                                <!-- 9 -->
                                    <tr>
                                        <td rowspan="2">9</td>
                                        <td rowspan="2">产品进深培训</td>
                                        <td>9-1</td>
                                        <td>Summit机台产品彩页翻译</td>
                                        <td rowspan="2">入职第1-2周</td>
                                        <td></td>
                                        <td></td>
                                        <td rowspan="2">笔试</td>
                                        <td rowspan="2"></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>9-2</td>
                                        <td>探针选型资料学习</td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                <!-- 10 -->
                                    <tr>
                                        <td rowspan="4">10</td>
                                        <td rowspan="4">软件培训</td>
                                        <td>10-1</td>
                                        <td>WEB安装环境搭建</td>
                                        <td rowspan="4">入职第1天</td>
                                        <td></td>
                                        <td></td>
                                        <td rowspan="4">实操考评</td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>10-2</td>
                                        <td>编程工具使用</td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>10-3</td>
                                        <td>编码管理规范</td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>10-4</td>
                                        <td>Java规范</td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                <!-- 11 -->
                                    <tr>
                                        <td rowspan="7">11</td>
                                        <td rowspan="7">装机实操课培训</td>
                                        <td>11-1</td>
                                        <td>装机规范培训课</td>
                                        <td>入职第2天</td>
                                        <td></td>
                                        <td></td>
                                        <td rowspan="7">实操考评</td>
                                        <td rowspan="7"></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>11-2</td>
                                        <td>EPS系列机台之装机流程培训</td>
                                        <td rowspan="3">入职第1周</td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>11-2</td>
                                        <td>EPS系列机台之限时装机培训</td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>11-3</td>
                                        <td>EPS系列机台之限时装机培训</td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>11-4</td>
                                        <td>Summit系列机台之装机流程培训</td>
                                        <td rowspan="3">入职第2周</td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>11-5</td>
                                        <td>Summit系列机台之限时装机培训</td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>11-6</td>
                                        <td>Summit系列机台之限时装机培训</td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                <!-- 12 -->
                                    <tr>
                                        <td>12</td>
                                        <td>深入学习</td>
                                        <td>12-1</td>
                                        <td>推荐书籍</td>
                                        <td>入职第1年</td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
						<div class="assessRecord_foot">
                            <input type="button" class="eou_new_btn eou_new_radius eou-button-30 eou-button-w60" id="assessRecord_submit" value="提交">         
                        </div>
					</div>
					
                    <!-- 发布框 -->
                    <!-- <div class="publish_div" style="display: none;">
                        <div class="publish_div_tit">发布提示信息</div>
                        <div class="publish_div_body"></div>
                        <div class="publish_div_foot">
                            <div class="publish_div_foot_in"></div>
                        </div>
                    </div> -->
                    <!-- <div class="cover_bg2"></div> -->
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

        <!-- <script src="js/libs/jquery-3.3.1.min.js"></script> -->
        <script src="js/libs/bootstrap.min.js"></script>
        <script src="js/msgbox_unload.js"></script>
        <script src="js/global/myFunction.js"></script>
        <script src="js/TrainingRecords.js"></script>
    </body>
</html>