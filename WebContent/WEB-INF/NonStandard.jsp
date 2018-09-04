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
        <title>服务任务分配</title>
        <link rel="shortcut icon" href="image/eoulu.ico" />
        <link rel="bookmark" href="image/eoulu.ico" />
        <link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="css/global/eouluCustom.css" type="text/css">
        <link rel="stylesheet" href="css/NonStandard.css" type="text/css">
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
                                    <div class="m_button_r">
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
                                                      <li title="项目名称">项目名称</li>
                                                      <li title="客户名称">客户名称</li>
                                                      <li title="负责人">负责人</li>
                                                      <li title="项目阶段">项目阶段</li>
                                                      <li title="发布时间">发布时间</li>
                                                      <li title="阶段预计完成时间">阶段预计完成时间</li>
                                                      <li title="项目预计完成时间">项目预计完成时间</li>
                                                      <li title="阶段实际完成时间">阶段实际完成时间</li>
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
                                                      <li title="项目名称">项目名称</li>
                                                      <li title="客户名称">客户名称</li>
                                                      <li title="负责人">负责人</li>
                                                      <li title="项目阶段">项目阶段</li>
                                                      <li title="发布时间">发布时间</li>
                                                      <li title="阶段预计完成时间">阶段预计完成时间</li>
                                                      <li title="项目预计完成时间">项目预计完成时间</li>
                                                      <li title="阶段实际完成时间">阶段实际完成时间</li>
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
                                                <th class="t2">客户名称</th>
                                                <th class="t3">项目名称</th>
                                                <th class="t4">项目阶段</th>
                                                <th class="t5">负责人</th>
                                                <th class="t6">发布时间</th>
                                                <th class="t7" title="阶段预计完成时间">阶段预计时间</th>
                                                <th class="t8" title="项目预计完成时间">项目预计时间</th>
                                                <th class="t9">项目进展</th>
                                                <th class="t10" title="阶段实际完成时间">阶段实际时间</th>
                                                <th class="t11">发布</th>
                                                <th style="display:none">发布人</th>
                                                <th class="t12">删除</th>
                                                <!-- <th style="display:none">BookingDate</th> -->
                                                
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <!-- <tr>
                                                <td class="update_td" value>1</td>
                                                <td class="ocd">OpportunityCreateDate</td>
                                                <td class="hastd_customername">CustomerName</td>
                                                <td class="hastd_city">City</td>
                                                <td class="hastd_deal_status">DealStatus</td>
                                                <td class="hastd_win_probability">WinProbability</td>
                                                <td class="hastd_keysight_FE_name">KeysightName</td>
                                                <td class="hastd_keysight_model_number">Model</td>
                                                <td class="hastd_forecasted_order_date">OrderDate</td>
                                                <td class="hastd_customer_attn">Contact</td>
                                                <td class="hastd_status">Status</td>
                                                <td class="hastd_state_province" style="display:none">Area</td>
                                                <td class="hastd_actual_order_booking_date" style="display:none">BookingDate</td>
                                                <td class="CommodityID" style="display:none">CommodityID</td>
                                                <td class="hastd_customer_tel" style="display:none">ContactInfo1</td>
                                                <td class="hastd_country_code" style="display:none">CountryCode</td>
                                                <td class="hastd_currency_code" style="display:none">CurrencyCode</td>
                                                <td class="CustomerID" style="display:none">CustomerID</td>
                                                <td class="hastd_deal_id" style="display:none">DealID</td>
                                                <td class="hastd_customer_email" style="display:none">Email</td>
                                                <td class="hastd_p_c_kr" style="display:none">KeysightReseller</td>
                                                <td class="hastd_line" style="display:none">Line</td>
                                                <td class="hastd_partner_id" style="display:none">PartnerID</td>
                                                <td class="hastd_postal_code" style="display:none">PostalCode</td>
                                                <td class="hastd_qty" style="display:none">Qty</td>
                                                <td class="hastd_keysight_sales_order" style="display:none">SalesOrder</td>
                                                <td class="hastd_estimated_keysight_deal_value" style="display:none">SellerPriceOne</td>
                                                <td class="hastd_shipto_location" style="display:none">ShipToLocation</td>
                                                <td class="hastd_street_address" style="display:none">StreetAddress</td>
                                            </tr> -->
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
                    <div class="add_NonStandard" style="display: none;">
                        <div class="add_NonStandard_tit">
                            <div class="add_NonStandard_tit_l">添加服务任务分配</div>
                            <div class="add_NonStandard_tit_r">关闭</div>
                        </div>
                        <div class="add_NonStandard_body">
                            <div class="add_NonStandard_body_in">
                                <div class="add_NonStandard_body_tit">服务任务分配</div>
                                <div class="add_NonStandard_body_main">
                                    <div class="add_NonStandard_body_main_l">
                                        <div class="line_0">
                                            <div class="line_01"><i class="eou-relevance"></i>客户名称</div>
                                            <div class="line_02" style="position:relative">
                                                <input type="text" class="info_CustomerName">
                                                <select name="" id="add_CustomerName" multiple style="width:40vw;display:none">
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>项目名称</div>
                                            <div class="line_02" style="position:relative">
                                                <input type="text" class="info_ProjectName" maxlength="20">
                                                <select name="" class="ProjectName_select" multiple style="width:25vw;display:none">
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">项目阶段</div>
                                            <div class="line_02">
                                            	<select class="info_ProjectStage">
                                            		<option value="">请选择</option>
                                            		<option value="方案评估">方案评估</option>
                                            		<option value="设计绘图">设计绘图</option>
                                            		<option value="加工制作">加工制作</option>
                                            		<option value="组装调试">组装调试</option>
                                            		<option value="交付实施">交付实施</option>
                                            	</select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">发布人</div>
                                            <div class="line_02">
                                                <input type="text" class="info_Issuer">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">负责人</div>
                                            <div class="line_02">
                                                <input type="text" class="info_ResponsibleMan">
                                            </div>
                                        </div>
                                        
                                    </div>
                                    <!-- 右部 -->
                                    <div class="add_NonStandard_body_main_r">
                                        <div class="line_0">
                                            <div class="line_01">发布时间</div>
                                            <div class="line_02">
                                                <input type="date" class="info_PublishDate">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01" style="position: relative;"><i class="require_i">*</i>阶段预计完成时间</div>
                                            <div class="line_02">
                                                <input type="date" class="info_StageExpectedDate">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">项目预计完成时间</div>
                                            <div class="line_02">
                                                <input type="date" class="info_ProjectExpectedDate">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">阶段实际完成时间</div>
                                            <div class="line_02">
                                                <input type="date" class="info_StageActualDate">
                                            </div>
                                        </div>
                                    <!-- 右部结束   -->
                                    </div>
                                </div>
                                <div class="textarea-div">
                                	<span>项目进展</span>
                                	<textarea cols="80" rows="5" class="info_ProjectProgress eou-bstr-input"></textarea>
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
                            <div class="update_NonStandard_tit_l">修改服务任务分配</div>
                            <div class="update_NonStandard_tit_r">关闭</div>
                        </div>
                        <div class="update_NonStandard_body">
                            <div class="update_NonStandard_body_in">
                                <div class="update_NonStandard_body_tit">服务任务分配</div>
                                <div class="update_NonStandard_body_main">
                                    <div class="update_NonStandard_body_main_l">
                                        <div class="line_0">
                                            <div class="line_01"><i class="eou-relevance"></i>客户名称</div>
                                            <div class="line_02" style="position:relative">
                                                <input type="text" class="info_CustomerName">
                                                <select name="" id="update_CustomerName" multiple style="width:40vw;display:none">
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>项目名称</div>
                                            <div class="line_02" style="position: relative;">
                                                <input type="text" class="info_ProjectName" maxlength="20">
                                                <select name="" class="ProjectName_select" multiple style="width:25vw;display:none">
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">项目阶段</div>
                                            <div class="line_02">
                                            	<select class="info_ProjectStage">
                                            		<option value="">请选择</option>
                                            		<option value="方案评估">方案评估</option>
                                            		<option value="设计绘图">设计绘图</option>
                                            		<option value="加工制作">加工制作</option>
                                            		<option value="组装调试">组装调试</option>
                                            		<option value="交付实施">交付实施</option>
                                            	</select>
                                                <!-- <input type="text" class="info_ProjectStage"> -->
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">发布人</div>
                                            <div class="line_02">
                                                <input type="text" class="info_Issuer">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">负责人</div>
                                            <div class="line_02">
                                                <input type="text" class="info_ResponsibleMan">
                                            </div>
                                        </div>
                                        
                                    </div>
                                    <!-- 右部 -->
                                    <div class="update_NonStandard_body_main_r">
                                        <div class="line_0">
                                            <div class="line_01">发布时间</div>
                                            <div class="line_02">
                                                <input type="date" class="info_PublishDate">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01" style="position: relative;"><i class="require_i">*</i>阶段预计完成时间</div>
                                            <div class="line_02">
                                                <input type="date" class="info_StageExpectedDate">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">项目预计完成时间</div>
                                            <div class="line_02">
                                                <input type="date" class="info_ProjectExpectedDate">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">阶段实际完成时间</div>
                                            <div class="line_02">
                                                <input type="date" class="info_StageActualDate">
                                            </div>
                                        </div>
                                    <!-- 右部结束   -->
                                    </div>
                                </div>
                                <div class="textarea-div">
                                	<span>项目进展</span>
                                	<textarea cols="80" rows="5" class="info_ProjectProgress eou-bstr-input"></textarea>
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
                    <!-- 发布框 -->
                    <div class="publish_div" style="display: none;">
                        <div class="publish_div_tit">发布提示信息</div>
                        <div class="publish_div_body"></div>
                        <div class="publish_div_foot">
                            <div class="publish_div_foot_in"></div>
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

        <!-- <script src="js/libs/jquery-3.3.1.min.js"></script> -->
        <script src="js/libs/bootstrap.min.js"></script>
        <!-- <script src="js/global/myFunction.js"></script> -->
        <!-- <script src="js/msgbox_unload.js"></script> -->
        <script src="js/NonStandard.js"></script>
    </body>
</html>