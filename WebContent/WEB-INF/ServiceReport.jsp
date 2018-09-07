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
        <title>服务完成报告</title>
        <!--[if lt IE 9]>
        <script type="text/javascript" src="js/libs/flashcanvas.min.js"></script>
        <![endif]-->
        <link rel="shortcut icon" href="image/eoulu.ico" />
        <link rel="bookmark" href="image/eoulu.ico" />
        <link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css">
        <!-- delete later -->
        <!-- <link rel="stylesheet" href="css/global/eouluCustom.css" type="text/css"> -->
        <!-- <link rel="stylesheet" href="css/ServiceReport.css" type="text/css"> -->
        <!-- <link rel="stylesheet" href="css/global/dispatchLoading.css" type="text/css"> -->
        <link rel="stylesheet" href="css/modules/serviced/ServiceReport-30eec74d24.min.css" type="text/css">
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
                                                      <li title="报告编号">报告编号</li>
                                                      <li title="项目">项目</li>
                                                      <li title="最终用户名称">最终用户名称</li>
                                                      <li title="最终用户姓名">最终用户姓名</li>
                                                      <li title="联系方式">联系方式</li>
                                                      <li title="业务员姓名">业务员姓名</li>
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
                                                      <li title="报告编号">报告编号</li>
                                                      <li title="项目">项目</li>
                                                      <li title="最终用户名称">最终用户名称</li>
                                                      <li title="最终用户姓名">最终用户姓名</li>
                                                      <li title="联系方式">联系方式</li>
                                                      <li title="业务员姓名">业务员姓名</li>
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
                                                <th class="t2">报告编号</th>
                                                <th class="t3">项目</th>
                                                <th class="t4">最终用户名称</th>
                                                <th class="t5">最终用户姓名</th>
                                                <th class="t6">联系方式</th>
                                                <th class="t7">业务员姓名</th>
                                                <th class="t8">报告预览</th>
                                                <!-- <th class="t9">合同号</th>
                                                <th class="t10">产品型号、版本号或SN号</th>
                                                <th class="t11">报告文件名</th> -->
                                                <th class="t12">删除</th>
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
                    <div class="add_NonStandard" style="display: none;">
                        <div class="add_NonStandard_tit">
                            <div class="add_NonStandard_tit_l">添加服务完成报告</div>
                            <div class="add_NonStandard_tit_r">关闭</div>
                        </div>
                        <div class="add_NonStandard_body">
                            <div class="add_NonStandard_body_in">
                                <div class="add_NonStandard_body_tit">服务完成报告</div>
                                <div class="add_NonStandard_body_main">
                                    <div class="add_NonStandard_body_main_l">
                                        <div class="line_0">
                                            <div class="line_01">报告编号</div>
                                            <div class="line_02" style="position:relative">
                                                <input type="text" class="info_Number">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">项目</div>
                                            <div class="line_02">
                                                <select class="info_Project">
                                                    <option value="" disabled="disabled">请选择</option>
                                                    <option value="EUCP">EUCP</option>
                                                    <option value="探针台">探针台</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">最终用户名称</div>
                                            <div class="line_02">
                                            	<input type="text" class="info_CustomerTitle">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">最终用户姓名</div>
                                            <div class="line_02">
                                                <input type="text" class="info_CustomerName">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">报告文件名</div>
                                            <div class="line_02" style="position: relative;" id="doc_div">
                                                <input type="text" class="info_FileName">
                                                <div class="doc_item">.doc</div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- 右部 -->
                                    <div class="add_NonStandard_body_main_r">
                                        <div class="line_0">
                                            <div class="line_01">联系方式</div>
                                            <div class="line_02">
                                                <input type="text" class="info_LinkInfo">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">业务员姓名</div>
                                            <div class="line_02">
                                                <!-- <select id="addStaffName" class="info_StaffName">
                                                    
                                                </select> -->
                                                <input type="text" class="info_StaffName">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">合同号</div>
                                            <div class="line_02">
                                                <input type="text" class="info_ContractNo">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01" style="line-height: 17px;">产品型号、<br/>版本或SN号</div>
                                            <div class="line_02">
                                                <input type="text" class="info_ProductVersion">
                                            </div>
                                        </div>
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
                            <div class="update_NonStandard_tit_l">修改服务完成报告</div>
                            <div class="update_NonStandard_tit_r">关闭</div>
                        </div>
                        <div class="update_NonStandard_body">
                            <div class="update_NonStandard_body_in">
                                <div class="update_NonStandard_body_tit">服务完成报告</div>
                                <div class="update_NonStandard_body_main">
                                    <div class="update_NonStandard_body_main_l">
                                        <div class="line_0">
                                            <div class="line_01">报告编号</div>
                                            <div class="line_02" style="position:relative">
                                                <input type="text" class="info_Number">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">项目</div>
                                            <div class="line_02">
                                                <select class="info_Project">
                                                    <option value="" disabled="disabled">请选择</option>
                                                    <option value="EUCP">EUCP</option>
                                                    <option value="探针台">探针台</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">最终用户名称</div>
                                            <div class="line_02">
                                                <input type="text" class="info_CustomerTitle">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">最终用户姓名</div>
                                            <div class="line_02">
                                                <input type="text" class="info_CustomerName">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">报告文件名</div>
                                            <div class="line_02" style="position: relative;" id="doc_div2">
                                                <input type="text" class="info_FileName">
                                                <div class="doc_item">.doc</div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- 右部 -->
                                    <div class="update_NonStandard_body_main_r">
                                        <div class="line_0">
                                            <div class="line_01">联系方式</div>
                                            <div class="line_02">
                                                <input type="text" class="info_LinkInfo">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">业务员姓名</div>
                                            <div class="line_02">
                                                <!-- <select id="updateStaffName" class="info_StaffName"> 
                                                </select> -->
                                                <input type="text" class="info_StaffName">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">合同号</div>
                                            <div class="line_02">
                                                <input type="text" class="info_ContractNo">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01" style="line-height: 17px;">产品型号、<br/>版本或SN号</div>
                                            <div class="line_02">
                                                <input type="text" class="info_ProductVersion">
                                            </div>
                                        </div>
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

                    <div class="cover_bg2"></div>
                    <div class="cover_bg3"></div>
                    <div class="serviceReport_div">
                        <div class="serviceReport_top">
                            <div class="serviceReport_top_l">服务完成报告模板</div>
                            <div class="serviceReport_top_save">保存</div>
                            <div class="serviceReport_top_down">下载</div>
                            <div class="serviceReport_top_r">关闭</div>
                        </div>
                        <div class="serviceReport_file_tit">
                            输入服务完成报告文件名
                        </div>
                        <div class="serviceReport_file">
                            <form class="form-inline">
                              <div class="form-group">
                                <label class="sr-only" for="input_down_file">输入服务完成报告文件名</label>
                                <div class="input-group">
                                  <input type="text" class="form-control" id="input_down_file" placeholder="输入文件名">
                                  <div class="input-group-addon">.doc</div>
                                </div>
                              </div>
                            </form>
                        </div>
                        <div class="serviceReport_table">
                            <table class="eou-table-collapse">
                                <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th>完成内容</th>
                                        <th>是否达成目标</th>
                                        <th>情况说明</th>
                                        <th>客户确认签字</th>
                                        <th>确认日期</th>
                                    </tr>
                                </thead>
                                <tbody>

                                </tbody>
                            </table>
                        </div>
                        <div class="serviceReport_body">
                            <input type="button" class="eou_new_btn eou_new_radius eou-button-30 eou-button-w80" id="add_serviceReport" value="添加一条">
                        </div>
                        <!-- <div class="serviceReport_foot">
                            <div class="serviceReport_foot_in">
                                <input type="button" class="eou_new_btn eou_new_radius eou-button-30 eou-button-w60" id="save_serviceReport" value="保存">
                                <input type="button" class="eou_new_btn eou_new_radius eou-button-30 eou-button-w60" id="down_serviceReport" value="下载">
                            </div>
                        </div> -->
                    </div>

                    <!-- 电子签名 -->
                    <div class="wrapper_sign">
                        <div class="wrapper_sign_tit">
                            <div class="wrapper_sign_tit_l">电子签名</div>
                            <div class="wrapper_sign_tit_r">关闭</div>
                        </div>
                        <div id="signature"></div>
                        <div class="signature_button">
                            <div class="signature_button_in">
                                <input type="button" class="eou_new_btn eou_new_radius eou-button-30 eou-button-w60" id="signature_OK" value="确定">
                                <input type="button" class="eou_new_btn eou_new_radius eou-button-30 eou-button-w60" id="signature_reset" value="重置">
                            </div>
                        </div>
                    </div>
                    <!-- 下载输入文件名 -->
                    <!-- <div class="download_filename">
                        <div class="download_filename_tit">
                            <div class="download_filename_tit_l">请输入服务完成报告文件名</div>
                            <div class="download_filename_tit_r">关闭</div>
                        </div>
                        <div class="download_filename_body">
                            <form class="form-inline">
                              <div class="form-group">
                                <label class="sr-only" for="input_down_file">输入服务完成报告文件名</label>
                                <div class="input-group">
                                  <input type="text" class="form-control" id="input_down_file" placeholder="输入文件名">
                                  <div class="input-group-addon">.doc</div>
                                </div>
                              </div>
                            </form>
                        </div>
                        <div class="download_filename_foot">
                            <input type="button" class="eou_new_btn eou_new_radius eou-button-30 eou-button-w60" id="download_filename_OK" value="确认">
                        </div>
                    </div> -->

                    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
                      <div class="modal-dialog" role="document">
                        <div class="modal-content">
                          <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="exampleModalLabel">确认删除吗？</h4>
                          </div>
                          <div class="modal-body">
                            <h5>是否确认删除这条服务完成报告记录？</h5>
                          </div>
                          <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">否</button>
                            <button type="button" class="btn btn-primary" id="delete_service_yes">是</button>
                          </div>
                        </div>
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
        <!-- delete -->
        <!-- <script src="js/libs/jSignature.min.js"></script> -->
        <!-- <script src="plugins/colResizable/colResizable-1.6.min.js"></script> -->
        <!-- <script src="js/ServiceReport.js"></script> -->
    </body>
</html>