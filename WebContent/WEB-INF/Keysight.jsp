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
        <title>Keysight funnel list</title>
        <link rel="shortcut icon" href="image/eoulu.ico" />
        <link rel="bookmark" href="image/eoulu.ico" />
        <link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="css/global/eouluCustom.css" type="text/css">
        <link rel="stylesheet" href="css/Keysight.css" type="text/css">
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

            #Keysight_wrapper {
                position:fixed;
                overflow:auto;
                width:100%;
                height:100%;
                box-sizing:border-box;
            }

            #Keysight_sticker {
                width:100%;
                min-height:100%;
                box-sizing:border-box;
            }

            #Keysight_sticker-con {
                padding-bottom:40px;
                box-sizing:border-box;
            }

            #Keysight_footer {
                margin-top:-40px;
            }

        </style>
    </head>
    <body>
        <div id="Keysight_wrapper">
            <div id="Keysight_sticker">
                <div id="Keysight_sticker-con">
                    <%@include file="top.jsp"%>
                    <div class="container-fluid">
                        <div class="g_container">
                            <%@include file="nav.jsp"%>
                            <div class="g_body">
                                <div class="m_button">
                                    <div class="m_button_l">
                                        <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" value="添加"><input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" id="keysight_export" value="导出">
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
                                                      <li title="Create Date">Opportunity Create Date</li>
                                                      <li title="CustomerName">CustomerName</li>
                                                      <li title="Deal Status">Deal Status</li>
                                                      <li title="K.M.Number-Option">Keysight Model Number-Option.</li>
                                                      <li title="Order Date">Forecasted Order Date</li>
                                                      <li title="Customer Attn">Customer Attn</li>
                                                      <li title="City">City</li>
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
                                                      <li title="Create Date">Opportunity Create Date</li>
                                                      <li title="CustomerName">CustomerName</li>
                                                      <li title="Deal Status">Deal Status</li>
                                                      <li title="K.M.Number-Option">Keysight Model Number-Option.</li>
                                                      <li title="Order Date">Forecasted Order Date</li>
                                                      <li title="Customer Attn">Customer Attn</li>
                                                      <li title="City">City</li>
                                                    </ul>
                                                  </div><!-- /btn-group -->
                                                  <input type="text" class="form-control" aria-label="...">
                                                </div><!-- /input-group -->
                                            </div>
                                            <div class="m_button_r_r">
                                                <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" id="keysight_search" value="搜索">
                                                <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" id="keysight_cancel" value="取消">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- 页面表格主体 -->
                                <div class="m_table">
                                    <table class="eou-table-collapse">
                                        <thead>
                                            <tr>
                                                <th class="t1" rowspan="2">序号</th>
                                                <th class="t2" style="border-bottom: none;">Opportunity</th>
                                                <th class="t3" rowspan="2">CustomerName</th>
                                                <th class="t4" rowspan="2">City</th>
                                                <th class="t5" rowspan="2">Deal Status</th>
                                                <th class="t6" rowspan="2">Win Probability</th>
                                                <th class="t7" rowspan="2">Keysight FE Name</th>
                                                <th class="t8" rowspan="2">Keysight Model Number</th>
                                                <th class="t9" style="border-bottom: none;">Forecasted</th>
                                                <th class="t10" rowspan="2">Customer Attn</th>
                                                <th class="t11" rowspan="2">Status</th>
                                                <th rowspan="2" style="display:none">Area</th>
                                                <th rowspan="2" style="display:none">BookingDate</th>
                                                <th rowspan="2" style="display:none">CommodityID</th>
                                                <th rowspan="2" style="display:none">ContactInfo1</th>
                                                <th rowspan="2" style="display:none">CountryCode</th>
                                                <th rowspan="2" style="display:none">CurrencyCode</th>
                                                <th rowspan="2" style="display:none">CustomerID</th>
                                                <th rowspan="2" style="display:none">DealID</th>
                                                <th rowspan="2" style="display:none">Email</th>
                                                <th rowspan="2" style="display:none">KeysightReseller</th>
                                                <th rowspan="2" style="display:none">Line</th>
                                                <th rowspan="2" style="display:none">PartnerID</th>
                                                <th rowspan="2" style="display:none">PostalCode</th>
                                                <th rowspan="2" style="display:none">Qty</th>
                                                <th rowspan="2" style="display:none">SalesOrder</th>
                                                <th rowspan="2" style="display:none">SellerPriceOne</th>
                                                <th rowspan="2" style="display:none">ShipToLocation</th>
                                                <th rowspan="2" style="display:none">StreetAddress</th>
                                            </tr>
                                            <tr>
                                                <!-- <th class="t1">序号</th> -->
                                                <th class="t2" style="border-top: none;">Create Date</th>
                                                <!-- <th class="t3">具体描述</th> -->
                                                <!-- <th class="t4">发布人</th> -->
                                                <!-- <th class="t5">负责人</th> -->
                                                <!-- <th class="t6">进度状况</th> -->
                                                <!-- <th class="t7">发布时间</th> -->
                                                <!-- <th class="t8">任务期限时间</th> -->
                                                <th class="t9" style="border-top: none;">Order Date</th>
                                                <!-- <th class="t10">是否完成</th> -->
                                                <!-- <th class="t11">编号</th> -->
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
                    <div class="add_keysight" style="display: none;">
                        <div class="add_keysight_tit">
                            <div class="add_keysight_tit_l">添加Keysight funnel list</div>
                            <div class="add_keysight_tit_r">关闭</div>
                        </div>
                        <div class="add_keysight_body">
                            <div class="add_keysight_body_in">
                                <div class="add_keysight_body_tit">Keysight funnel list</div>
                                <div class="add_keysight_body_main">
                                    <div class="add_keysight_body_main_l">
                                        <div class="line_0">
                                            <div class="line_01">Partner ID</div>
                                            <div class="line_02">
                                                <input type="text" class="info_partner_id" value="486492">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">Deal ID</div>
                                            <div class="line_02">
                                                <input type="text" class="info_deal_id">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01"><i class="eou-relevance"></i>CustomerName</div>
                                            <div class="line_02" style="position:relative">
                                                <input type="text" class="info_customername">
                                                <select name="" id="add_CustomerName" multiple style="width:40vw;display:none">
                                                    
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01"><i class="eou-relevance"></i>City</div>
                                            <div class="line_02">
                                                <input type="text" class="info_city" readonly="readonly" disabled="disabled">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01"><i class="eou-relevance"></i>State/Province</div>
                                            <div class="line_02">
                                                <input type="text" class="info_state_province" readonly="readonly" disabled="disabled">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01"><i class="eou-relevance"></i>Customer Attn</div>
                                            <div class="line_02">
                                                <input type="text" class="info_customer_attn" readonly="readonly" disabled="disabled">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01"><i class="eou-relevance"></i>Customer Tel</div>
                                            <div class="line_02">
                                                <input type="text" class="info_customer_tel" readonly="readonly" disabled="disabled">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01"><i class="eou-relevance"></i>Customer Email</div>
                                            <div class="line_02">
                                                <input type="text" class="info_customer_email" readonly="readonly" disabled="disabled">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">Postal Code</div>
                                            <div class="line_02">
                                                <input type="text" class="info_postal_code">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="add_keysight_body_main_m">
                                        <div class="line_0">
                                            <div class="line_01">% Win Probability</div>
                                            <div class="line_02">
                                                <select name="" id="addWinProbability" class="info_win_probability">
                                                    <option value="0">0</option>
                                                    <option value="25">25</option>
                                                    <option value="50">50</option>
                                                    <option value="75">75</option>
                                                    <option value="100">100</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">Ship-To Location</div>
                                            <div class="line_02">
                                                <input type="text" class="info_shipto_location">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">Keysight FE Name</div>
                                            <div class="line_02">
                                                <input type="text" class="info_keysight_FE_name">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01 line001">Keysight Sales Order#</div>
                                            <div class="line_02">
                                                <input type="text" class="info_keysight_sales_order">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01 line001"><i class="eou-relevance"></i>Keysight Model Number</div>
                                            <div class="line_02" style="position: relative;">
                                                <input type="text" class="info_keysight_model_number">
                                                <select name="" id="add_KeysightModelNumber" multiple style="width:40vw;display:none">
                                                    
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01 line001">Estimated Keysight Deal Value</div>
                                            <div class="line_02">
                                                <input type="text" class="info_estimated_keysight_deal_value">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01 line001">Forecasted Order Date</div>
                                            <div class="line_02" style="position:relative">
                                                <input type="text" class="info_forecasted_order_date" readonly="readonly">
                                                <input type="date" id="add_ForecastedOrderDate" style="display: none;">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">Line #</div>
                                            <div class="line_02">
                                                <input type="text" class="info_line">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">Qty</div>
                                            <div class="line_02">
                                                <input type="text" class="info_qty">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="add_keysight_body_main_r">
                                        <div class="line_0">
                                            <div class="line_01 line001">Actual Order Booking Date</div>
                                            <div class="line_02" style="position:relative;">
                                                <input type="text" class="info_actual_order_booking_date" readonly="readonly">
                                                <input type="date" id="add_ActualOrderBookingDate" style="display: none;">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">Street Address</div>
                                            <div class="line_02">
                                                <input type="text" class="info_street_address">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">Currency Code</div>
                                            <div class="line_02">
                                                <select name="" id="" class="info_currency_code">
                                                    <option value="USD">USD</option>
                                                    <option value="CNY">CNY</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">Country Code</div>
                                            <div class="line_02">
                                                <input type="text" class="info_country_code">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">Status</div>
                                            <div class="line_02">
                                                <input type="text" class="info_status">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">Deal Status</div>
                                            <div class="line_02">
                                                <select name="" id="" class="info_deal_status">
                                                    <option value="Open">Open</option>
                                                    <option value="Won">Won</option>
                                                    <option value="Lost">Lost</option>
                                                    <option value="Cancelled">Cancelled</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01 line001">(Partner/Customer or Keysight Reseller)</div>
                                            <div class="line_02">
                                                <select name="" id="" class="info_p_c_kr">
                                                    <option value="Partner">Partner</option>
                                                    <option value="Customer">Customer</option>
                                                    <option value="Keysight Reseller">Keysight Reseller</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="add_keysight_foot">
                            <div class="add_keysight_foot_in">
                                <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" id="keysight_addsubmit" value="提交">
                                <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" id="keysight_addclose" value="取消">
                            </div>
                        </div>
                    </div>

                    <!-- 修改 -->
                    <div class="update_keysight" style="display: none;">
                        <div class="update_keysight_tit">
                            <div class="update_keysight_tit_l">修改Keysight funnel list</div>
                            <div class="update_keysight_tit_r">关闭</div>
                        </div>
                        <div class="update_keysight_body">
                            <div class="update_keysight_body_in">
                                <div class="update_keysight_body_tit">Keysight funnel list</div>
                                <div class="update_keysight_body_main">
                                    <div class="update_keysight_body_main_l">
                                        <div class="line_0">
                                            <div class="line_01">Partner ID</div>
                                            <div class="line_02">
                                                <input type="text" class="info_partner_id">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">Deal ID</div>
                                            <div class="line_02">
                                                <input type="text" class="info_deal_id">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01"><i class="eou-relevance"></i>CustomerName</div>
                                            <div class="line_02" style="position: relative;">
                                                <input type="text" class="info_customername">
                                                <select name="" id="update_CustomerName" multiple style="width:40vw;display:none">
                                                    
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01"><i class="eou-relevance"></i>City</div>
                                            <div class="line_02">
                                                <input type="text" class="info_city" readonly="readonly" disabled="disabled">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01"><i class="eou-relevance"></i>State/Province</div>
                                            <div class="line_02">
                                                <input type="text" class="info_state_province" readonly="readonly" disabled="disabled">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01"><i class="eou-relevance"></i>Customer Attn</div>
                                            <div class="line_02">
                                                <input type="text" class="info_customer_attn" readonly="readonly" disabled="disabled">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01"><i class="eou-relevance"></i>Customer Tel</div>
                                            <div class="line_02">
                                                <input type="text" class="info_customer_tel" readonly="readonly" disabled="disabled">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01"><i class="eou-relevance"></i>Customer Email</div>
                                            <div class="line_02">
                                                <input type="text" class="info_customer_email" readonly="readonly" disabled="disabled">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">Postal Code</div>
                                            <div class="line_02">
                                                <input type="text" class="info_postal_code">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="update_keysight_body_main_m">
                                        <div class="line_0">
                                            <div class="line_01">% Win Probability</div>
                                            <div class="line_02">
                                                <select name="" id="updateWinProbability" class="info_win_probability">
                                                    <option value="0">0</option>
                                                    <option value="25">25</option>
                                                    <option value="50">50</option>
                                                    <option value="75">75</option>
                                                    <option value="100">100</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">Ship-To Location</div>
                                            <div class="line_02">
                                                <input type="text" class="info_shipto_location">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">Keysight FE Name</div>
                                            <div class="line_02">
                                                <input type="text" class="info_keysight_FE_name">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01 line001">Keysight Sales Order#</div>
                                            <div class="line_02">
                                                <input type="text" class="info_keysight_sales_order">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01 line001"><i class="eou-relevance"></i>Keysight Model Number</div>
                                            <div class="line_02" style="position: relative;">
                                                <input type="text" class="info_keysight_model_number">
                                                <select name="" id="update_KeysightModelNumber" multiple style="width:40vw;display:none">
                                                    
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01 line001">Estimated Keysight Deal Value</div>
                                            <div class="line_02">
                                                <input type="text" class="info_estimated_keysight_deal_value">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01 line001">Forecasted Order Date</div>
                                            <div class="line_02" style="position: relative;">
                                                <input type="text" class="info_forecasted_order_date" readonly="readonly">
                                                <input type="date" id="update_ForecastedOrderDate" style="display: none;">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">Line #</div>
                                            <div class="line_02">
                                                <input type="text" class="info_line">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">Qty</div>
                                            <div class="line_02">
                                                <input type="text" class="info_qty">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="update_keysight_body_main_r">
                                        <div class="line_0">
                                            <div class="line_01 line001">Actual Order Booking Date</div>
                                            <div class="line_02" style="position: relative;">
                                                <input type="text" class="info_actual_order_booking_date" readonly="readonly">
                                                <input type="date" id="update_ActualOrderBookingDate" style="display: none;">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">Street Address</div>
                                            <div class="line_02">
                                                <input type="text" class="info_street_address">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">Currency Code</div>
                                            <div class="line_02">
                                                <select name="" id="" class="info_currency_code">
                                                    <option value="USD">USD</option>
                                                    <option value="CNY">CNY</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">Country Code</div>
                                            <div class="line_02">
                                                <input type="text" class="info_country_code">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">Status</div>
                                            <div class="line_02">
                                                <input type="text" class="info_status">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">Deal Status</div>
                                            <div class="line_02">
                                                <select name="" id="" class="info_deal_status">
                                                    <option value="Open">Open</option>
                                                    <option value="Won">Won</option>
                                                    <option value="Lost">Lost</option>
                                                    <option value="Cancelled">Cancelled</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01 line001">(Partner/Customer or Keysight Reseller)</div>
                                            <div class="line_02">
                                                <select name="" id="" class="info_p_c_kr">
                                                    <option value="Partner">Partner</option>
                                                    <option value="Customer">Customer</option>
                                                    <option value="Keysight Reseller">Keysight Reseller</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="update_keysight_foot">
                            <div class="update_keysight_foot_in">
                                <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" id="keysight_updatesubmit" value="提交">
                                <input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w60" id="keysight_updateclose" value="取消">
                            </div>
                        </div>
                    </div>
                <!-- Keysight_sticker-con结束 -->
                </div>
            <!-- Keysight_sticker结束 -->
            </div>

            <!-- Keysight_footer -->
            <div id="Keysight_footer">
                <div id="eoulu-copy-out" style="height:40px;width:calc(100% - 2px);">
                    <div style="width:100%;height:5px;"></div>
                    <div id="eoulu-copy" style="width:100%;height:35px;font-size:12px;color:#888;line-height:35px;z-index: 2;">
                        <hr style="height:1px;color:#999;width: calc(100% - 3px);" />
                        <div style="width:100%;text-align:center;display:inline-block;">Copyright  ©&nbsp;<span class="YEAR">2018</span>&nbsp<a href="http://www.eoulu.com/h-col-268.html" class="EHref" target="_blank" style="color:blue;">Eoulu</a> Tech. Co.,Ltd.</div>
                    </div>
                </div>
            </div>
        <!-- Keysight_wrapper结束 -->
        </div>

        <!-- <script src="js/libs/jquery-3.3.1.min.js"></script> -->
        <script src="js/libs/bootstrap.min.js"></script>
        <script src="js/libs/ConvertToPYPolyphone.js"></script>
        <!-- <script src="js/global/myFunction.js"></script> -->
        <!-- <script src="js/msgbox_unload.js"></script> -->
        <script src="js/Keysight.js"></script>
    </body>
</html>