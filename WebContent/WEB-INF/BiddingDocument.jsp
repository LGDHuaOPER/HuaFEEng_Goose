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
        <title>招标文件</title>
        <link rel="shortcut icon" href="image/eoulu.ico" />
        <link rel="bookmark" href="image/eoulu.ico" />
        <!-- <link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css"> -->
        <link rel="stylesheet" href="css/libs/bootstrap-grid-form-btn-res-icon-badge-progress-list-advancedbtn-dropdown.min.css" type="text/css">
        <!-- <link rel="stylesheet" href="css/global/eouluCustom.css" type="text/css"> -->
        <!-- <link rel="stylesheet" type="text/css" href="plugins/webuploader/webuploader.css" /> -->
        <link rel="stylesheet" type="text/css" href="css/global/global_table_style.css">
        <link rel="stylesheet" href="css/modules/commerce/BiddingDocument.css" type="text/css">
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
                                <span id="workReportName" style="display: none;">${name}</span>
                                <div class="m_button">
                                    <div class="m_button_l">
                                        <input class="btn btn-info" type="button" value="上传">
                                        <!-- <input class="btn btn-info" type="button" value="全选">
                                        <input class="btn btn-info" type="button" value="导出">
                                        <input class="btn btn-info" type="button" value="通知列表"> -->
                                    </div>
                                    <div class="m_button_r">
                                    	<div class="m_button_r_in">
                                    		<div class="m_button_r_in_l">
                                    			<div class="input-group">
                                    				<span class="input-group-addon">字段</span>
                                    			  	<div class="input-group-btn">
                                    			    	<button type="button" class="btn btn-primary" title="选择字段">选择字段</button>
                                    			    	<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    			    	  	<span class="caret"></span>
                                    			    	  	<span class="sr-only">选择字段</span>
                                    			    	</button>
                                    			    	<ul class="dropdown-menu">
                                                            <li title="选择字段">选择字段</li>
                                                            <li title="文件名称">文件名称</li>
                                                            <li title="年份">年份</li>
                                                            <li title="编写人">编写人</li>
                                                            <li title="评分">评分</li>
                                                        </ul>
                                    			  	</div><!-- /btn-group -->
                                    			  	<input type="text" class="form-control in_search" aria-label="...">
                                                    <select id="search_year" class="form-control" name="search_Year"></select>
                                    			</div><!-- /input-group -->
                                    		</div><!-- m_button_r_in_l end -->
                                    		<div class="m_button_r_in_r">
                                    			<input type="button" class="btn btn-primary" value="搜索">
                                    			<input type="button" class="btn btn-warning" value="取消">
                                    		</div>
                                    	</div>
                                    </div>
                                </div>
                                <!-- 页面表格主体 -->
                                <div class="m_table">
                                    <table id="global_table_style">
                                        <thead class="inormal">
                                            <tr>
                                                <th class="t1">序号</th>
                                                <th class="t2">文件名称</th>
                                                <th class="t3">年份</th>
                                                <th class="t4">编写人</th>
                                                <th class="t5">评分</th>
                                                <th class="t6">下载</th>
                                            </tr>
                                        </thead>
                                        <tbody class="inormal">
                                        </tbody>
                                    </table>
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
                            <div class="add_NonStandard_tit_l">添加招标文件</div>
                            <div class="add_NonStandard_tit_r">
                            	<button type="button" class="btn btn-default" aria-label="Left Align"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></button>
                            </div>
                        </div>
                        <div class="add_NonStandard_body">
                            <div class="add_NonStandard_body_in">
                            	<fieldset><legend>招标文件信息</legend>
                            		<div class="container-fluid">
                            			<div class="form-horizontal">
                            			    <div class="form-group">
                            			    	<label class="col-sm-2 control-label">年份</label>
                            			    	<div class="col-sm-10">
                            			        	<select name="search_Year" class="form-control"></select>
                            			        </div>
                            			    </div>
                            			</div>
                            			<!-- <div class="row">
                            			  	<div class="col-md-2 col-lg-2">.col-md-4</div>
                            			  	<div class="col-md-4 col-lg-4">.col-md-4</div>
                            			  	<div class="col-md-2 col-lg-2">.col-md-4</div>
                            			  	<div class="col-md-4 col-lg-4">.col-md-4</div>
                            			</div> -->
                            		</div>
                            	</fieldset>
                                <fieldset><legend>招标文件上传</legend>
                                    <div class="form-group">
                                        <label>选择文件</label>
                                        <input type="file" id="BiddingDocumentUpload" multiple="multiple" accept="application/msword, application/pdf, image/*, application/vnd.ms-powerpoint, text/plain, application/vnd.openxmlformats-officedocument.presentationml.presentation, application/vnd.openxmlformats-officedocument.wordprocessingml.document">
                                        <label for="BiddingDocumentUpload"><span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span></label>
                                    </div>
                                    <div class="fileList_ul_wrapper">
                                    	<div class="fileList_info">
                                            上传总进度：
                                            <div class="progress">
                                                <div class="progress-bar progress-bar-info progress-bar-striped active" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em;">0%</div>
                                            </div>
                                            上传成功数占比：
                                            <div class="progress">
                                                <div class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em;">0%</div>
                                            </div>  
                                        </div>
                                    	<ul class="list-group" id="fileList_ul">
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
                            <div class="update_NonStandard_tit_l">修改招标文件信息</div>
                            <div class="update_NonStandard_tit_r">
                            	<button type="button" class="btn btn-default" aria-label="Left Align"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></button>
                            </div>
                        </div>
                        <div class="update_NonStandard_body">
                            <div class="update_NonStandard_body_in">
                            	<fieldset><legend>招标文件信息</legend>
                            		<div class="container-fluid">
                            			<div class="form-horizontal">
                            			    <div class="form-group">
                            			    	<label class="col-sm-2 control-label">年份</label>
                            			    	<div class="col-sm-10">
                            			        	<select name="search_Year" class="form-control"></select>
                            			        </div>
                            			    </div>
                            			    <div class="form-group">
                            			    	<label class="col-sm-2 control-label">编写人</label>
                            			    	<div class="col-sm-10">
                            			        	<select name="update_info_Submitter" class="form-control"></select>
                            			        </div>
                            			    </div>
                            			    <div class="form-group">
                            			    	<label class="col-sm-2 control-label">评分</label>
                            			    	<div class="col-sm-10">
                                                    <input type="text" name="update_info_Score" class="form-control">
                            			        </div>
                            			    </div>
                            			</div>
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
                    </div>
                    <!-- 修改end -->

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

        <!-- <script src="js/libs/jquery-3.3.1.min.js"></script> -->
        <!-- <script src="js/libs/bootstrap.min.js"></script> -->
        <script src="js/libs/bootstrap/bootstrap-grid-form-btn-res-icon-badge-progress-list-advancedbtn-dropdown.min.js"></script>
        <!-- <script src="js/global/myFunction.js"></script> -->
        <!-- <script src="js/global/responseLoading.js"></script> -->
        <!-- <script src="js/msgbox_unload.js"></script> -->
        <!-- <script type="text/javascript" src="plugins/webuploader/webuploader.min.js"></script> -->
        <script src="js/modules/commerce/BiddingDocument.js"></script>
    </body>
</html>