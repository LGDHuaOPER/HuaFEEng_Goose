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
        <title>设备清单</title>
        <link rel="shortcut icon" href="image/eoulu.ico" />
        <link rel="bookmark" href="image/eoulu.ico" />
        <link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="css/modules/laboratory/Lab-ecd0227fc6.min.css" type="text/css">
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
                margin-top: 2px;
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

            /*背景遮罩层覆盖样式*/
            .bg_cover, .cover_bg2, .cover_bg3, .upload_bgcover, .showPic_bgcover {
                width: calc(100vw - 20px);
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
                                        <input type="button" class="btn btn-info" value="添加">
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
                                                <input type="button" class="btn btn-info" id="NonStandard_search" value="搜索">
                                                <input type="button" class="btn btn-warning" id="NonStandard_cancel" value="取消">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- 页面表格主体 -->
                                <div class="m_table">
                                    <div class="tab_wrapper">
                                        <!-- Nav tabs -->
                                        <ul class="nav nav-tabs" role="tablist">
                                            <li role="presentation" class="active" data-laboratory="苏州"><a href="#suzhou_a" aria-controls="suzhou_a" role="tab" data-toggle="tab">苏州</a></li>
                                            <li role="presentation" data-laboratory="厦门"><a href="#xiameng_a" aria-controls="xiameng_a" role="tab" data-toggle="tab">厦门</a></li>
                                            <li role="presentation" data-laboratory="深圳"><a href="#shenzhen_a" aria-controls="shenzhen_a" role="tab" data-toggle="tab">深圳</a></li>
                                            <li role="presentation" data-laboratory="北京"><a href="#beijing_a" aria-controls="beijing_a" role="tab" data-toggle="tab">北京</a></li>
                                            <li role="presentation" data-laboratory="石家庄"><a href="#shijiazhuang_a" aria-controls="shijiazhuang_a" role="tab" data-toggle="tab">石家庄</a></li>
                                            <li role="presentation" data-laboratory="成都"><a href="#chengdu_a" aria-controls="chengdu_a" role="tab" data-toggle="tab">成都</a></li>
                                        </ul><!-- Nav tabs end -->

                                        <!-- Tab panes -->
                                        <div class="tab-content">
                                            <div role="tabpanel" class="tab-pane fade in active" id="suzhou_a">
                                                <table data-itable="苏州">
                                                    <thead>
                                                        <tr>
                                                            <th class="t1">序号</th>
                                                            <th class="t2">系统名称</th>
                                                            <th class="t3">系统描述</th>
                                                            <th class="t4">数量</th>
                                                            <th class="t5 t_hide">实验室</th>
                                                            <th class="t6">产品图片</th>
                                                            <th class="t7">详细配置</th>
                                                            <th class="t8">应用文档</th>
                                                            <th class="t9">更新时间</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div role="tabpanel" class="tab-pane fade" id="xiameng_a">
                                                <table data-itable="厦门">
                                                    <thead>
                                                        <tr>
                                                            <th class="t1">序号</th>
                                                            <th class="t2">系统名称</th>
                                                            <th class="t3">系统描述</th>
                                                            <th class="t4">数量</th>
                                                            <th class="t5 t_hide">实验室</th>
                                                            <th class="t6">产品图片</th>
                                                            <th class="t7">详细配置</th>
                                                            <th class="t8">应用文档</th>
                                                            <th class="t9">更新时间</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    </tbody>
                                                </table>
                                            </div><!-- xiameng_a end -->
                                            <div role="tabpanel" class="tab-pane fade" id="shenzhen_a">
                                                <table data-itable="深圳">
                                                    <thead>
                                                        <tr>
                                                            <th class="t1">序号</th>
                                                            <th class="t2">系统名称</th>
                                                            <th class="t3">系统描述</th>
                                                            <th class="t4">数量</th>
                                                            <th class="t5 t_hide">实验室</th>
                                                            <th class="t6">产品图片</th>
                                                            <th class="t7">详细配置</th>
                                                            <th class="t8">应用文档</th>
                                                            <th class="t9">更新时间</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div role="tabpanel" class="tab-pane fade" id="beijing_a">
                                                <table data-itable="北京">
                                                    <thead>
                                                        <tr>
                                                            <th class="t1">序号</th>
                                                            <th class="t2">系统名称</th>
                                                            <th class="t3">系统描述</th>
                                                            <th class="t4">数量</th>
                                                            <th class="t5 t_hide">实验室</th>
                                                            <th class="t6">产品图片</th>
                                                            <th class="t7">详细配置</th>
                                                            <th class="t8">应用文档</th>
                                                            <th class="t9">更新时间</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div role="tabpanel" class="tab-pane fade" id="shijiazhuang_a">
                                                <table data-itable="石家庄">
                                                    <thead>
                                                        <tr>
                                                            <th class="t1">序号</th>
                                                            <th class="t2">系统名称</th>
                                                            <th class="t3">系统描述</th>
                                                            <th class="t4">数量</th>
                                                            <th class="t5 t_hide">实验室</th>
                                                            <th class="t6">产品图片</th>
                                                            <th class="t7">详细配置</th>
                                                            <th class="t8">应用文档</th>
                                                            <th class="t9">更新时间</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    </tbody>
                                                </table>
                                            </div><!-- shijiazhuang_a end -->
                                            <div role="tabpanel" class="tab-pane fade" id="chengdu_a">
                                                <table data-itable="成都">
                                                    <thead>
                                                        <tr>
                                                            <th class="t1">序号</th>
                                                            <th class="t2">系统名称</th>
                                                            <th class="t3">系统描述</th>
                                                            <th class="t4">数量</th>
                                                            <th class="t5 t_hide">实验室</th>
                                                            <th class="t6">产品图片</th>
                                                            <th class="t7">详细配置</th>
                                                            <th class="t8">应用文档</th>
                                                            <th class="t9">更新时间</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div><!-- Tab panes end-->
                                    </div><!-- tab_wrapper end -->
                                </div>
                                <!-- 翻页 -->
                                <div class="m_page">
                                    <div class="pageInfo">当前是第&nbsp;<span id="currentPage">1</span>&nbsp;页,&nbsp;总计&nbsp;<span id="allPage"></span>页</div>
                                    <div class="changePage">
                                        <input type="button" class="btn btn-primary" value="首页" id="fistPage" name="fistPage">
                                        <input type="button" class="btn btn-primary" value="上一页" id="upPage">
                                        <input type="button" class="btn btn-primary" value="下一页" id="nextPage"> 跳到第 <input type="text" id="jumpNumber" name="jumpNumber" class="jumpNumber" style="width: 30px; color: #000"> 页 <input type="button" class="btn btn-primary" value="GO" id="Gotojump" name="Gotojump">
                                        <input type="button" class="btn btn-primary" value="尾页" id="lastPage" name="lastPage">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 弹出组件 -->
                    <div class="bg_cover" style="display: none;"></div>
                    <div class="add_NonStandard" style="display: none;">
                        <div class="add_NonStandard_tit">
                            <div class="add_NonStandard_tit_l">添加设备清单</div>
                            <div class="add_NonStandard_tit_r">关闭</div>
                        </div>
                        <div class="add_NonStandard_body">
                            <div class="add_NonStandard_body_in">
                                <fieldset><legend>基本信息</legend>
                                    <div class="container-fluid">
                                        <div class="row">
                                            <div class="col-md-6 col-lg-6">
                                                <div class="form-horizontal">
                                                    <div class="form-group">
                                                        <label class="col-sm-3 control-label" for="add_info_Model"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>系统名称</label>
                                                        <div class="col-sm-9">
                                                            <input type="text" class="form-control" id="add_info_Model" placeholder="必填项">
                                                            <select class="form-control" id="add_Commodity" multiple>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-sm-3 control-label" for="add_info_Description"><span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span>系统描述</label>
                                                        <div class="col-sm-9">
                                                            <input type="text" class="form-control" id="add_info_Description">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-sm-3 control-label">产品图片</label>
                                                        <div class="col-sm-9 relative_div">
                                                            <input type="text" class="form-control" id="add_info_Picture" readonly="readonly" disabled="disabled"><input type="button" class="btn btn-info add_upload_Picture" value="上传">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6 col-lg-6">
                                                <div class="form-horizontal">
                                                    <div class="form-group">
                                                        <label for="add_info_Number" class="col-sm-3 control-label">数量</label>
                                                        <div class="col-sm-9">
                                                            <input type="text" class="form-control" id="add_info_Number">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="add_info_Laboratory" class="col-sm-3 control-label">实验室</label>
                                                        <div class="col-sm-9">
                                                            <select id="add_info_Laboratory" class="form-control" name="Laboratory_sel"></select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div><!-- container-fluid end -->
                                </fieldset>
                                <fieldset><legend>应用文档</legend>
                                    <div class="form-group">
                                        <label for="add_file_Upload" class="trigger_click"><button type="button" class="btn btn-info"><span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span>&nbsp;选择文件</button></label>
                                        <input type="file" id="add_file_Upload" multiple="multiple" accept="application/msword, application/pdf, image/*, application/vnd.ms-powerpoint, text/plain, application/vnd.openxmlformats-officedocument.presentationml.presentation, application/vnd.openxmlformats-officedocument.wordprocessingml.document">
                                        <label class="add_label_upload"><button type="button" class="btn btn-info"><span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span>&nbsp;上传</button></label>
                                    </div>
                                    <div class="add_fileList_ul_wrapper">
                                        <div class="add_fileList_info">
                                            上传总进度：
                                            <div class="progress">
                                                <div class="progress-bar progress-bar-info progress-bar-striped active" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em;">0%</div>
                                            </div>
                                            上传成功数占比：
                                            <div class="progress">
                                                <div class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em;">0%</div>
                                            </div>  
                                        </div>
                                        <ul class="list-group" id="add_fileList_ul">
                                            <!-- <li class="list-group-item list-group-item-success"><span class="badge">14</span>Cras justo odio</li>
                                            <li class="list-group-item list-group-item-danger"><span class="badge">14</span>Dapibus ac facilisis in</li>
                                            <li class="list-group-item"><span class="badge">14</span>Morbi leo risus</li> -->
                                        </ul>
                                    </div>
                                </fieldset>
                            </div><!-- add_NonStandard_body_in end -->
                        </div>
                        <div class="add_NonStandard_foot">
                            <div class="add_NonStandard_foot_in">
                                <input type="button" class="btn btn-success" id="NonStandard_addsubmit" value="提交">
                                <input type="button" class="btn btn-warning" id="NonStandard_addclose" value="取消">
                            </div>
                        </div>
                    </div>

                    <!-- 修改 -->
                    <div class="update_NonStandard" style="display: none;">
                        <div class="update_NonStandard_tit">
                            <div class="update_NonStandard_tit_l">修改设备清单</div>
                            <div class="update_NonStandard_tit_r">关闭</div>
                        </div>
                        <div class="update_NonStandard_body">
                            <div class="update_NonStandard_body_in">
                                <fieldset><legend>基本信息</legend>
                                    <div class="container-fluid">
                                        <div class="row">
                                            <div class="col-md-6 col-lg-6">
                                                <div class="form-horizontal">
                                                    <div class="form-group">
                                                        <label class="col-sm-3 control-label" for="update_info_Model"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>系统名称</label>
                                                        <div class="col-sm-9">
                                                            <input type="text" class="form-control" id="update_info_Model" placeholder="必填项">
                                                            <select class="form-control" id="update_Commodity" multiple>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-sm-3 control-label" for="update_info_Description"><span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span>系统描述</label>
                                                        <div class="col-sm-9">
                                                            <input type="text" class="form-control" id="update_info_Description">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-sm-3 control-label">产品图片</label>
                                                        <div class="col-sm-9 relative_div">
                                                            <input type="text" class="form-control" id="update_info_Picture" readonly="readonly" disabled="disabled"><input type="button" class="btn btn-info update_upload_Picture" value="上传">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6 col-lg-6">
                                                <div class="form-horizontal">
                                                    <div class="form-group">
                                                        <label for="update_info_Number" class="col-sm-3 control-label">数量</label>
                                                        <div class="col-sm-9">
                                                            <input type="text" class="form-control" id="update_info_Number">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="update_info_Laboratory" class="col-sm-3 control-label">实验室</label>
                                                        <div class="col-sm-9">
                                                            <select id="update_info_Laboratory" class="form-control" name="Laboratory_sel"></select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div><!-- container-fluid end -->
                                </fieldset>
                                <fieldset><legend>应用文档</legend>
                                    <div class="form-group">
                                        <label for="update_file_Upload" class="trigger_click"><button type="button" class="btn btn-info"><span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span>&nbsp;选择文件</button></label>
                                        <input type="file" id="update_file_Upload" multiple="multiple" accept="application/msword, application/pdf, image/*, application/vnd.ms-powerpoint, text/plain, application/vnd.openxmlformats-officedocument.presentationml.presentation, application/vnd.openxmlformats-officedocument.wordprocessingml.document">
                                        <label class="update_label_upload"><button type="button" class="btn btn-info"><span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span>&nbsp;上传</button></label>
                                    </div>
                                    <div class="update_fileList_ul_wrapper">
                                        <div class="update_fileList_info">
                                            上传总进度：
                                            <div class="progress">
                                                <div class="progress-bar progress-bar-info progress-bar-striped active" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em;">0%</div>
                                            </div>
                                            上传成功数占比：
                                            <div class="progress">
                                                <div class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em;">0%</div>
                                            </div>  
                                        </div>
                                        <ul class="list-group" id="update_fileList_ul">
                                            <!-- <li class="list-group-item list-group-item-success"><span class="badge">14</span>Cras justo odio</li>
                                            <li class="list-group-item list-group-item-danger"><span class="badge">14</span>Dapibus ac facilisis in</li>
                                            <li class="list-group-item"><span class="badge">14</span>Morbi leo risus</li> -->
                                        </ul>
                                    </div>
                                </fieldset>
                            </div><!-- update_NonStandard_body_in end -->
                        </div>
                        <div class="update_NonStandard_foot">
                            <div class="update_NonStandard_foot_in">
                                <input type="button" class="btn btn-success" id="NonStandard_updatesubmit" value="提交">
                                <input type="button" class="btn btn-warning" id="NonStandard_updateclose" value="取消">
                            </div>
                        </div>
                    </div><!-- 修改结束 -->

					<!-- 详细配置 -->
                    <div class="cover_bg2"></div>
                    <div class="serviceReport_div">
                        <div class="serviceReport_top">
                            <div class="serviceReport_top_l">详细配置</div>
                            <div class="serviceReport_top_save">保存</div>
                            <div class="serviceReport_top_down">导出</div>
                            <div class="serviceReport_top_r">关闭</div>
                        </div>
                        <div class="serviceReport_table">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Item</th>
                                        <th>Part Number</th>
                                        <th>Description</th>
                                        <th>Qty</th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                        <div class="serviceReport_body">
                            <input type="button" class="btn btn-primary" id="add_serviceReport" value="添加一条">
                        </div>
                    </div>

                    <!-- 配置搜索 -->
                    <div class="cover_bg3"></div>
                    <div class="part_search_div">
                    	<div class="part_search_top">
                    		<div class="part_search_top_l"></div>
                    		<div class="part_search_top_r">关闭</div>
                    	</div>
                    	<div class="part_search_body">
                    		<div class="part_search_body_in">
                    			<input type="text" id="part_search_input">
                    			<select id="part_search_select" multiple style="display:none">
                                </select>
                    		</div>
                    	</div>
                    </div>
                    
                    <!-- 显示图片 -->
                    <div class="showPic_bgcover"></div>
                    <div class="showPic">
                    	<div class="showPic_top">
                    		<div class="showPic_top_l"></div>
                    		<div class="showPic_top_m">
                    			<span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
                    		</div>
                    		<div class="showPic_top_r">
                    			<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
                    		</div>
                    	</div>
                    	<div class="showPic_body">
                    		<div class="showPic_body_in"></div>
                    	</div>
                    </div>

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
                                        <input type="button" class="btn btn-info preUpload1" value="浏览..." title="选择文件">
                                        <input type="text" readonly="readonly" class="serFinRepUploadName" placeholder="未选择文件"><span class="isUpload"></span>
                                        <!-- accept="image/gif, image/jpeg" -->
                                        <input type="file" id="serFinRepUpload" accept="image/*">
                                    </div>
                                    <div class="dropFileConMidMid">
                                        <div class="progressOut"></div>
                                        <div class="progressIn">0%</div>
                                    </div>
                                    <div class="dropFileConMidbot">
                                        <input type="button" class="btn btn-info dropUp2" value="上传">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 警告框 -->
                    <div class="warning_alert_wrapper_cover"></div>
                    <div class="warning_alert_wrapper">
                        <div class="container-fluid">
                            
                        </div>
                    </div>
                    <!-- 警告框结束 -->

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
        <!-- <script src="plugins/colResizable/colResizable-1.6.min.js"></script> -->
        <!-- <script src="js/Lab.js"></script> -->
    </body>
</html>