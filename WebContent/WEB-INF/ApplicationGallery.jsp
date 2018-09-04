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
        <title>研发图库</title>
        <link rel="shortcut icon" href="image/eoulu.ico" />
        <link rel="bookmark" href="image/eoulu.ico" />
        <link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="css/modules/serviced/ApplicationGallery-48a2d648b2.min.css" type="text/css">
        <!-- <link rel="stylesheet" type="text/css" href="css/global/global_table_style.css">
        <link rel="stylesheet" href="css/modules/serviced/ApplicationGallery.css" type="text/css">
        <link rel="stylesheet" href="css/global/add_update_section.css" type="text/css">
        <link rel="stylesheet" href="css/global/eoulu_ul_reset.css" type="text/css"> -->
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
                                    <div class="row">
                                        <div class="col-md-4 col-lg-4">
                                            <span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
                                        </div>
                                        <div class="col-md-8 col-lg-8">
                                            <div class="search_in">
                                                <!-- 上部分 -->
                                                <div class="btn-group" data-toggle="buttons">
                                                  <label class="btn btn-info">
                                                    <input type="radio" name="querytype" autocomplete="off" value="singleSelect">单一查询
                                                  </label>
                                                  <label class="btn btn-info">
                                                    <input type="radio" name="querytype" autocomplete="off" value="mixSelect">组合查询
                                                  </label>
                                                </div>
                                                <!-- 下部分 -->
                                                <div class="form-inline">
                                                    <div class="form-group singleSelect_search">
                                                        <div class="input-group">
                                                            <!-- <span class="input-group-addon">字段</span> -->
                                                            <div class="input-group-btn">
                                                                <button type="button" class="btn btn-info" title="选择字段">选择字段</button>
                                                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                                    <span class="caret"></span>
                                                                    <span class="sr-only">选择字段</span>
                                                                </button>
                                                                <ul class="dropdown-menu">
                                                                </ul>
                                                            </div><!-- /btn-group -->
                                                            <input type="text" class="form-control" aria-label="...">
                                                            <input type="date" class="form-control" aria-label="...">
                                                            <select class="form-control">
                                                                <option value="" disabled>请选择</option>
                                                                <option value="在用">在用</option>
                                                                <option value="停用">停用</option>
                                                            </select>
                                                        </div><!-- /input-group -->
                                                    </div><!-- form-group -->

                                                    <div class="form-group mixSelect_search" style="display: none;">
                                                        <div class="input-group">
                                                            <!-- <span class="input-group-addon">字段</span> -->
                                                            <div class="input-group-btn">
                                                                <button type="button" class="btn btn-info" title="选择字段">选择字段</button>
                                                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                                    <span class="caret"></span>
                                                                    <span class="sr-only">选择字段</span>
                                                                </button>
                                                                <ul class="dropdown-menu">
                                                                </ul>
                                                            </div><!-- /btn-group -->
                                                            <input type="text" class="form-control" aria-label="...">
                                                            <input type="date" class="form-control" aria-label="...">
                                                            <select class="form-control">
                                                                <option value="" disabled>请选择</option>
                                                                <option value="在用">在用</option>
                                                                <option value="停用">停用</option>
                                                            </select>
                                                        </div><!-- /input-group -->
                                                    </div><!-- form-group -->
                                                    <button type="button" class="btn btn-success classify_search_y">搜索</button>
                                                    <button type="button" class="btn btn-warning classify_search_n">取消</button>
                                                </div><!-- form-inline end -->
                                            </div>
                                        </div>
                                    </div>
                                </div><!-- m_button end -->

                                <!-- 分页标签主体 -->
                                <div class="m_pagination">
                                    <div class="tab_wrapper">
                                        <ul class="nav nav-tabs" role="tablist">
                                            <li role="presentation" class="active"><a href="#scheme_a" aria-controls="scheme_a" role="tab" data-toggle="tab" data-idiagram="scheme">方案图</a></li>
                                            <li role="presentation"><a href="#standard_a" aria-controls="standard_a" role="tab" data-toggle="tab" data-idiagram="standard">标准产品图</a></li>
                                            <li role="presentation"><a href="#customization_a" aria-controls="customization_a" role="tab" data-toggle="tab" data-idiagram="customization">定制产品图</a></li>
                                        </ul>

                                        <div class="tab-content">
                                            <!-- 方案图 -->
                                            <div role="tabpanel" class="tab-pane fade in active" id="scheme_a">
                                                <table id="global_table_style">
                                                    <thead>
                                                        <tr>
                                                            <th class="t1">序号</th>
                                                            <th class="t2">应用类型</th>
                                                            <th class="t3">客户名称</th>
                                                            <th class="t4">方案名称</th>
                                                            <th class="t5">机台型号</th>
                                                            <th class="t6">制定时间</th>
                                                            <th class="t7">CAD图纸</th>
                                                            <th class="t8">PDF图纸</th>
                                                            <th class="t9">备注说明</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    </tbody>
                                                </table>
                                                <div id="glbal_table_page">
                                                    <div class="pageInfo">
                                                        当前是第&nbsp;<span id="currentPage">${currentPage}</span>&nbsp;页,&nbsp;总计&nbsp;<span id="allPage">${pageCounts}</span>页
                                                    </div>
                                                    <div class="changePage">
                                                        <input type="button" class="btn btn-primary" value="首页" id="fistPage">
                                                        <input type="button" class="btn btn-primary" value="上一页" id="upPage">
                                                        <input type="button" class="btn btn-primary" value="下一页" id="nextPage">
                                                        跳到第 <input type="text" id="jumpNumber" name="jumpNumber"
                                                            class="jumpNumber" style="width: 30px;height: 25px; color: #000"
                                                            onkeyup="value=value.replace(/[^\d]/g,'') "> 页 <input
                                                            type="button" class="btn btn-primary" value="GO" id="Gotojump">
                                                        <input type="button" class="btn btn-primary" value="尾页" id="lastPage">
                                                    </div>
                                                </div>
                                            </div><!-- 方案图 end-->


                                            <!-- 标准产品图 -->
                                            <div role="tabpanel" class="tab-pane fade" id="standard_a">
                                                <!-- 表格开始 -->
                                                <table class="gl_table_style">
                                                    <thead>
                                                        <tr>
                                                            <th class="tt1">序号</th>
                                                            <th class="tt2">产品名称</th>
                                                            <th class="tt3">产品类型</th>
                                                            <th class="tt4">适用机台</th>
                                                            <th class="tt5">版本号</th>
                                                            <th class="tt6">设计时间</th>
                                                            <th class="tt7">使用情况</th>
                                                            <th class="tt8">CAD图纸</th>
                                                            <th class="tt9">PDF图纸</th>
                                                            <th class="tt10">备注说明</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody></tbody>
                                                </table>
                                                <div class="gl_table_page">
                                                    <div class="pageInfo">当前是第&nbsp;<span id="currentPage2"></span>&nbsp;页,&nbsp;总计&nbsp;<span id="allPage2"></span>页
                                                    </div>
                                                    <div class="changePage">
                                                        <input type="button" class="btn btn-primary" value="首页" id="fistPage2">
                                                        <input type="button" class="btn btn-primary" value="上一页" id="upPage2">
                                                        <input type="button" class="btn btn-primary" value="下一页" id="nextPage2">
                                                        跳到第 <input type="text" id="jumpNumber2" style="width: 30px;height: 25px; color: #000" onkeyup="value=value.replace(/[^\d]/g,'') "> 页 <input type="button" class="btn btn-primary" value="GO" id="Gotojump2">
                                                        <input type="button" class="btn btn-primary" value="尾页" id="lastPage2">
                                                    </div>
                                                </div>
                                            </div><!-- 标准产品图 end -->
                                                

                                            <!-- 定制产品图 -->
                                            <div role="tabpanel" class="tab-pane fade" id="customization_a">
                                                <!-- 表格开始 -->
                                                <table class="gl_table_style">
                                                    <thead>
                                                        <tr>
                                                            <th class="ttt1">序号</th>
                                                            <th class="ttt2">产品名称</th>
                                                            <th class="ttt3">产品类型</th>
                                                            <th class="ttt4">适用机台</th>
                                                            <th class="ttt5">版本号</th>
                                                            <th class="ttt6">设计时间</th>
                                                            <th class="ttt7">使用情况</th>
                                                            <th class="ttt8">CAD图纸</th>
                                                            <th class="ttt9">PDF图纸</th>
                                                            <th class="ttt10">备注说明</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody></tbody>
                                                </table>
                                                <div class="gl_table_page">
                                                    <div class="pageInfo">当前是第&nbsp;<span id="currentPage3"></span>&nbsp;页,&nbsp;总计&nbsp;<span id="allPage3"></span>页
                                                    </div>
                                                    <div class="changePage">
                                                        <input type="button" class="btn btn-primary" value="首页" id="fistPage3">
                                                        <input type="button" class="btn btn-primary" value="上一页" id="upPage3">
                                                        <input type="button" class="btn btn-primary" value="下一页" id="nextPage3">
                                                        跳到第 <input type="text" id="jumpNumber3" style="width: 30px;height: 25px; color: #000" onkeyup="value=value.replace(/[^\d]/g,'') "> 页 <input type="button" class="btn btn-primary" value="GO" id="Gotojump3">
                                                        <input type="button" class="btn btn-primary" value="尾页" id="lastPage3">
                                                    </div>
                                                </div>
                                                <!-- 表格结束 -->
                                            </div><!-- 定制产品图 end -->
                                        </div><!-- tab-content end -->
                                    </div><!-- tab_wrapper end -->
                                </div><!-- 分页标签主体end -->
                                
                            </div><!-- g_body end  -->
                        </div>
                    </div>

                    <!-- 弹出组件 -->
                    <div class="bg_cover" style="display: none;"></div>
                    <div class="add_wrapper_scheme"></div>
                    <div class="update_wrapper_scheme"></div>
                    <div class="add_wrapper_standard"></div>
                    <div class="update_wrapper_standard"></div>
                    <div class="add_wrapper_customization"></div>
                    <div class="update_wrapper_customization"></div>
                    <!-- 弹出组件 end -->

                    <!-- 上传 -->
                    <!-- <div class="upload_bgcover"></div>
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
                                        <input type="file" id="serFinRepUpload" multiple="multiple" accept="application/msword, application/pdf, image/*">
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
        <div class="loading_div_g_div">
            <img src="image/loading/Spinner-1s-200px.gif" alt="loading。。。">
        </div>
        <script src="js/libs/bootstrap.min.js"></script>
        <!-- <script src="js/modules/commerce/ApplicationGallery.js"></script> -->
        <!-- <script src="js/global/dispatchScript.js"></script> -->
    </body>
</html>