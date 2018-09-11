<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <!-- 为移动设备添加 viewport -->
        <meta name="viewport"
        content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
        <title>标准产品</title>
        <link rel="shortcut icon" href="image/eoulu.ico" />
        <link rel="bookmark" href="image/eoulu.ico" />
        <link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css">
        <!-- <link rel="stylesheet" type="text/css" href="plugins/webuploader/webuploader.css" /> -->
        <!-- delete -->
        <!-- <link rel="stylesheet" href="css/global/eouluCustom.css" type="text/css"> -->
        <!-- <link rel="stylesheet" href="css/StandardProduct.css" type="text/css"> -->
        <link rel="stylesheet" href="css/modules/serviced/StandardProduct-855edafc10.min.css" type="text/css">
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

            /*表格自定义*/
            .m_table.inormal .t3, .m_table.inormal .hastd_Title, .m_table.inormal .t4, .m_table.inormal .hastd_Type {
                min-width: 80px;
                max-width: 80px;
                width: 80px;
            }

            .m_table.inormal .t5, .m_table.inormal .hastd_Machine {
                min-width: 120px;
                max-width: 120px;
                width: 120px;
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
                                                      <li title="产品型号">产品型号</li>
                                                      <li title="产品名称">产品名称</li>
                                                      <li title="产品类型">产品类型</li>
                                                      <li title="适用机台">适用机台</li>
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
                                                      <li title="产品型号">产品型号</li>
                                                      <li title="产品名称">产品名称</li>
                                                      <li title="产品类型">产品类型</li>
                                                      <li title="适用机台">适用机台</li>
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
                                <div class="m_table inormal">
                                    <table class="eou-table-collapse">
                                        <thead>
                                            <tr>
                                                <th class="t1">序号</th>
                                                <th class="t2">产品型号</th>
                                                <th class="t3">产品名称</th>
                                                <th class="t4">产品类型</th>
                                                <th class="t5">适用机台</th>
                                                <th class="t6">产品说明书</th>
                                                <th class="t7">安装条件说明书</th>
                                                <th class="t8">组装测试说明书</th>
                                                <th class="t9">出厂检测报告</th>
                                                <th class="t10">文档完整性</th>
                                                <th class="t11">审核</th>
                                                <th class="t12">更新时间</th>
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
                            <div class="add_NonStandard_tit_l">添加标准产品</div>
                            <div class="add_NonStandard_tit_r">关闭</div>
                        </div>
                        <div class="add_NonStandard_body">
                            <div class="add_NonStandard_body_in">
                                <div class="add_NonStandard_body_tit">标准产品</div>
                                <div class="add_NonStandard_body_main">
                                    <div class="add_NonStandard_body_main_l">
                                        <div class="line_0">
                                            <div class="line_01">产品型号</div>
                                            <div class="line_02" style="position:relative">
                                                <input type="text" class="info_Model">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">产品名称</div>
                                            <div class="line_02">
                                                <input type="text" class="info_Title">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">产品类型</div>
                                            <div class="line_02">
                                            	<input type="text" class="info_Type">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">适用机台</div>
                                            <div class="line_02" style="position: relative;">
                                                <input type="text" class="info_Machine">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01 line001">产品<br/>说明书</div>
                                            <div class="line_02 line_relative">
                                                <input type="text" class="info_ProductInstructions" readonly="readonly" disabled="disabled"><input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" value="上传">
                                            </div>
                                        </div>
                                    </div>
                                    <!-- 右部 -->
                                    <div class="add_NonStandard_body_main_r">
                                        <div class="line_0">
                                            <div class="line_01 line001">安装条件<br/>说明书</div>
                                            <div class="line_02 line_relative">
                                                <input type="text" class="info_InstallInstructions" readonly="readonly" disabled="disabled"><input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" value="上传">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01 line001">组装测试<br/>说明书</div>
                                            <div class="line_02 line_relative">
                                                <input type="text" class="info_TestInstructions" readonly="readonly" disabled="disabled"><input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" value="上传">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01 line001">出厂检测<br/>报告</div>
                                            <div class="line_02 line_relative">
                                                <input type="text" class="info_CheckingReport" readonly="readonly" disabled="disabled"><input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" value="上传">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01 line001">文档<br/>完整性</div>
                                            <div class="line_02">
                                                <select id="" class="info_DocumentIntegrity">
                                                    <option value="" disabled>请选择</option>
                                                    <option value="是">是</option>
                                                    <option value="否">否</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">更新时间</div>
                                            <div class="line_02">
                                                <input type="date" class="info_UpdateTime">
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
                            <div class="update_NonStandard_tit_l">修改标准产品</div>
                            <div class="update_NonStandard_tit_r">关闭</div>
                        </div>
                        <div class="update_NonStandard_body">
                            <div class="update_NonStandard_body_in">
                                <div class="update_NonStandard_body_tit">标准产品</div>
                                <div class="update_NonStandard_body_main">
                                    <div class="update_NonStandard_body_main_l">
                                        <div class="line_0">
                                            <div class="line_01">产品型号</div>
                                            <div class="line_02" style="position:relative">
                                                <input type="text" class="info_Model">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">产品名称</div>
                                            <div class="line_02">
                                                <input type="text" class="info_Title">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">产品类型</div>
                                            <div class="line_02">
                                                <input type="text" class="info_Type">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">适用机台</div>
                                            <div class="line_02" style="position: relative;">
                                                <input type="text" class="info_Machine">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01 line001">产品<br/>说明书</div>
                                            <div class="line_02 line_relative">
                                                <input type="text" class="info_ProductInstructions" readonly="readonly" disabled="disabled"><input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" value="上传">
                                            </div>
                                        </div>
                                    </div>
                                    <!-- 右部 -->
                                    <div class="update_NonStandard_body_main_r">
                                        <div class="line_0">
                                            <div class="line_01 line001">安装条件<br/>说明书</div>
                                            <div class="line_02 line_relative">
                                                <input type="text" class="info_InstallInstructions" readonly="readonly" disabled="disabled"><input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" value="上传">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01 line001">组装测试<br/>说明书</div>
                                            <div class="line_02 line_relative">
                                                <input type="text" class="info_TestInstructions" readonly="readonly" disabled="disabled"><input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" value="上传">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01 line001">出厂检测<br/>报告</div>
                                            <div class="line_02 line_relative">
                                                <input type="text" class="info_CheckingReport" readonly="readonly" disabled="disabled"><input type="button" class="eou-button eou-button-radius eou-button-30 eou-button-w50" value="上传">
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01 line001">文档<br/>完整性</div>
                                            <div class="line_02">
                                                <select id="" class="info_DocumentIntegrity">
                                                    <option value="" disabled>请选择</option>
                                                    <option value="是">是</option>
                                                    <option value="否">否</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="line_0">
                                            <div class="line_01">更新时间</div>
                                            <div class="line_02">
                                                <input type="date" class="info_UpdateTime">
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

                    <!-- 发布框 -->
                    <!-- <div class="publish_div" style="display: none;">
                        <div class="publish_div_tit">发布提示信息</div>
                        <div class="publish_div_body"></div>
                        <div class="publish_div_foot">
                            <div class="publish_div_foot_in"></div>
                        </div>
                    </div> -->

                    <!-- 上传 -->
                    <div class="upload_bgcover"></div>
                    <div class="dropFileBox" style="display: none">
                        <div class="dropFile">
                            <div class="dropFileTit">
                                上传文件<span>关闭</span>
                            </div>
                            <div class="dropFileCon">
                                <div class="dropFileConMid">
                                    <div class="dropFileConMidTop">
                                        <input type="button" class="eou-button eou-button-radius eou-button-20 eou-button-w60 preUpload1" value="浏览..." title="选择文件">
                                        <input type="text" readonly="readonly" class="serFinRepUploadName" placeholder="未选择文件"><span class="isUpload"></span>
                                        <input type="file" id="serFinRepUpload">
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
                    </div>
                    <!-- webupload上传 -->
                    <!-- <div class="upload_wrapper">
                        <div class="upload_wrapper_tit">
                            <div class="upload_wrapper_tit_l">文件上传</div>
                            <div class="upload_wrapper_tit_r">关闭</div>
                        </div>
                        <div class="upload_wrapper_body">
                            <div id="uploader" class="wu-example"> -->
                                <!--用来存放文件信息-->
                                <!-- <div id="thelist" class="uploader-list"></div>
                                <div class="btns">
                                    <div id="picker">选择文件</div>
                                    <button id="ctlBtn" class="btn btn-default">开始上传</button>
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

        <script src="js/libs/bootstrap.min.js"></script>
        <!-- delete -->
        <!-- <script src="js/global/responseLoading.js"></script> -->
        <!-- <script src="js/StandardProduct.js"></script> -->
    </body>
</html>