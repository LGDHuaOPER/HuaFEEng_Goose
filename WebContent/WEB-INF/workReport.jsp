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
        <title>工作汇报</title>
        <link rel="shortcut icon" href="image/eoulu.ico" />
        <link rel="bookmark" href="image/eoulu.ico" />
        <link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css">
        <!-- <link rel="stylesheet" href="css/global/eouluCustom.css" type="text/css"> -->
        <!-- <link rel="stylesheet" type="text/css" href="plugins/webuploader/webuploader.css" /> -->
        <link rel="stylesheet" href="css/modules/commerce/workReport.css" type="text/css">
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
                                        <input class="btn btn-info" type="button" value="添加">
                                        <!-- <input class="btn btn-info" type="button" value="全选">
                                        <input class="btn btn-info" type="button" value="导出">
                                        <input class="btn btn-info" type="button" value="通知列表"> -->
                                    </div>
                                    <div class="m_button_r" style="display: none;">
                                    	<div class="m_button_r_in">
                                    		<div class="m_button_r_in_l">
                                    			<div class="input-group">
                                    			  <span class="input-group-addon">年</span>
                                    			  <div class="btn-group">
                                    			    <button type="button" class="btn btn-primary" title="选择年">选择年</button>
                                    			    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    			      <span class="caret"></span>
                                    			      <span class="sr-only">选择年</span>
                                    			    </button>
                                    			    <ul class="dropdown-menu"><li title="选择年">选择年</li></ul>
                                    			  </div>
                                    			</div>
                                    		</div><!-- m_button_r_in_l end -->
                                    		<div class="m_button_r_in_m">
                                    			<div class="input-group">
                                    			  <span class="input-group-addon">月</span>
                                    			  <div class="btn-group">
                                    			    <button type="button" class="btn btn-primary" title="All">All</button>
                                    			    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    			      <span class="caret"></span>
                                    			      <span class="sr-only">选择月</span>
                                    			    </button>
                                    			    <ul class="dropdown-menu"><li title="All">All</li></ul>
                                    			  </div>
                                    			</div>
                                    		</div>
                                    		<div class="m_button_r_in_r">
                                    			<input type="button" class="btn btn-primary" value="搜索">
                                    			<input type="button" class="btn btn-warning" value="取消">
                                    		</div>
                                    	</div>
                                    </div>
                                </div>
                                <!-- 页面表格主体 -->
                                <div class="m_table">
                                    <table class="eou-table-collapse">
                                        <thead class="inormal">
                                            <tr>
                                                <th class="t1">序号</th>
                                                <th class="t2">部门</th>
                                                <th class="t3">姓名</th>
                                                <th class="t4">上午</th>
                                                <th class="t5">下午</th>
                                                <th class="t6">催促</th>
                                                <th class="t7">工作日志</th>
                                                <th class="t8">发送意见</th>
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
                            <div class="add_NonStandard_tit_l">添加工作汇报</div>
                            <div class="add_NonStandard_tit_r">
                            	<button type="button" class="btn btn-default" aria-label="Left Align"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></button>
                            </div>
                        </div>
                        <div class="add_NonStandard_body">
                            <div class="add_NonStandard_body_in">
                            	<fieldset><legend>工作汇报</legend>
                            		<div class="container-fluid">
                            			<div class="form-horizontal">
                            			    <div class="form-group">
                            			    	<label for="add_info_Department" class="col-sm-2 control-label">部门</label>
                            			    	<div class="col-sm-10">
                            			        	<input type="text" class="form-control" id="add_info_Department" placeholder="部门">
                            			        </div>
                            			    </div>
                            			    <div class="form-group">
                            			        <label for="add_info_Name" class="col-sm-2 control-label">姓名</label>
                            			        <div class="col-sm-10">
                            			        	<input type="text" class="form-control" id="add_info_Name" placeholder="姓名">
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
                                <fieldset><legend>添加工作内容</legend>
                                    <div class="form-group">
                                        <label for="add_info_Morning">上午<span class="wrapper_number_no">（你可以输入<span class="all_number_no">500</span>个字，现在剩余<span class="overplus_number_no">500</span>个）</span></label>
                                        <textarea class="form-control" rows="6" id="add_info_Morning" placeholder="添加上午的工作内容" maxlength="500"></textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="add_info_Afternoon">下午<span class="wrapper_number_no">（你可以输入<span class="all_number_no">500</span>个字，现在剩余<span class="overplus_number_no">500</span>个）</span></label>
                                        <textarea class="form-control" rows="6" id="add_info_Afternoon" placeholder="添加下午的工作内容" maxlength="500"></textarea>
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

                    <!-- 发布框 -->
                    <!-- <div class="publish_div" style="display: none;">
                        <div class="publish_div_tit">发布提示信息</div>
                        <div class="publish_div_body"></div>
                        <div class="publish_div_foot">
                            <div class="publish_div_foot_in"></div>
                        </div>
                    </div> -->

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

					<!-- 详情模态框 -->
					<div class="modal fade" id="workReportModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
					  <div class="modal-dialog" role="document">
					    <div class="modal-content">
					      <div class="modal-header">
					        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					        <h4 class="modal-title" id="exampleModalLabel"></h4>
					      </div>
					      <div class="modal-body">
					      	  <div class="modal_task_detail_Morning">
					      	  	  <div class="form-group">
                                      <label for="detail_Morning">上午<span class="wrapper_number_no">（你可以输入<span class="all_number_no">500</span>个字，现在剩余<span class="overplus_number_no">500</span>个）</span></label>
                                      <textarea class="form-control" id="detail_Morning" placeholder="填写上午的工作内容" rows="6"></textarea>
                                  </div>
					      	  </div>
					      	  <div class="modal_task_detail_Afternoon">
					      	  	  <div class="form-group">
                                      <label for="detail_Afternoon">下午<span class="wrapper_number_no">（你可以输入<span class="all_number_no">500</span>个字，现在剩余<span class="overplus_number_no">500</span>个）</span></label>
                                      <textarea class="form-control" id="detail_Afternoon" placeholder="填写下午的工作内容" rows="6"></textarea>
                                  </div>
					      	  </div>
					      </div>
					      <div class="modal-footer">
                               <button type="button" class="btn btn-success model_submit_Morning">保存</button>
                               <button type="button" class="btn btn-success model_submit_Afternoon">保存</button>
					           <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					      </div>
					    </div>
					  </div>
					</div><!-- 详情模态框end -->

					<!-- 工作日志模板 -->
					<div class="daily_log_temp">
						<div class="daily_log_temp_head"><span><img src="image/loading/Spinner-1s-50px.gif" alt="loading..."></span></div>
						<div class="daily_log_temp_body">
							<div class="daily_log_temp_body_tit"></div>
							<div class="daily_log_temp_body_body">
								<fieldset><legend>一、工作内容</legend>
									<div class="form-group">
									    <label for="update_info_Morning">上午<span class="wrapper_number_no">（你可以输入<span class="all_number_no">500</span>个字，现在剩余<span class="overplus_number_no">500</span>个）</span></label>
									    <textarea class="form-control" rows="6" id="update_info_Morning" placeholder="填写今日上午的工作内容" maxlength="500"></textarea>
									</div>
									<div class="form-group">
									    <label for="update_info_Afternoon">下午<span class="wrapper_number_no">（你可以输入<span class="all_number_no">500</span>个字，现在剩余<span class="overplus_number_no">500</span>个）</span></label>
                                        <textarea class="form-control" rows="6" id="update_info_Afternoon" placeholder="填写今日下午的工作内容" maxlength="500"></textarea>
									</div>
								</fieldset>
								<fieldset><legend>二、工作中的反思</legend>
									<div class="form-group">
									    <label for="update_info_Introspection">反思<span class="wrapper_number_no">（你可以输入<span class="all_number_no">500</span>个字，现在剩余<span class="overplus_number_no">500</span>个）</span></label>
									    <textarea class="form-control" rows="6" id="update_info_Introspection" placeholder="填写今日的工作反思" maxlength="500"></textarea>
									</div>
								</fieldset>
								<fieldset><legend>三、工作计划</legend>
                                   <div class="form-group">
                                        <label for="update_info_MorningPlan">上午<span class="wrapper_number_no">（你可以输入<span class="all_number_no">500</span>个字，现在剩余<span class="overplus_number_no">500</span>个）</span></label>
                                        <textarea class="form-control" rows="6" id="update_info_MorningPlan" placeholder="填写第二天上午的工作计划" maxlength="500"></textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="update_info_AfternoonPlan">下午<span class="wrapper_number_no">（你可以输入<span class="all_number_no">500</span>个字，现在剩余<span class="overplus_number_no">500</span>个）</span></label>
                                        <textarea class="form-control" rows="6" id="update_info_AfternoonPlan" placeholder="填写第二天下午的工作计划" maxlength="500"></textarea>
                                    </div>
								</fieldset>
							</div>
						</div>
						<div class="daily_log_temp_foot"><span><img src="image/loading/Spinner-1s-50px.gif" alt="loading..."></span></div>
					</div><!-- 工作日志模板end -->

					<!-- 邮件模板 -->
					<div class="mail_template">
						<div class="mail_template_tit">工作汇报邮件模板</div>
						<div class="mail_template_body">
							<fieldset><legend>发件人</legend>
								<div class="form-group">
								    <!-- <label for="exampleInputEmail1">上午</label> -->
                                    <input type="text" class="form-control" placeholder="选择发件人" readonly disabled>
								</div>
							</fieldset>
							<fieldset><legend>邮箱密码</legend>
								<div class="form-group has-feedback">
								    <!-- <label for="exampleInputEmail1">上午</label> -->
                                    <input type="password" class="form-control" id="inputSuccess2" placeholder="输入邮箱密码" autocomplete="new-password"><span class="glyphicon glyphicon-eye-open form-control-feedback" aria-hidden="true"></span>
								</div>
							</fieldset>
							<fieldset><legend>收件人</legend>
                                <div class="form-group has-feedback">
                                    <textarea class="form-control" rows="3" id="" placeholder="选择收件人" readonly></textarea><span class="glyphicon glyphicon-plus-sign form-control-feedback" aria-hidden="true"></span>
                                </div>
							</fieldset>
							<fieldset><legend>抄送人</legend>
								<div class="form-group has-feedback">
                                    <textarea class="form-control" rows="3" id="" placeholder="选择抄送人" readonly></textarea><span class="glyphicon glyphicon-plus-sign form-control-feedback" aria-hidden="true"></span>
                                </div>
							</fieldset>
							<fieldset><legend>主题</legend>
								<div class="form-group">
								    <!-- <label for="exampleInputEmail1">上午</label> -->
								    <input type="text" class="form-control" placeholder="填写主题" readonly disabled>
								</div>
							</fieldset>
						</div>
					</div><!-- 邮件模板end -->
					
					<!-- 操作导航栏 -->
					<nav class="navbar navbar-default operation_nav_bar">
					  	<div class="opera_wrapper">
				  			<div class="container-fluid">
                                <div class="navbar-header">
                                    <a class="navbar-brand" href="javascript:;">
                                        <button type="button" class="btn btn-info" aria-label="Left Align"><span class="glyphicon glyphicon-file" aria-hidden="true"></span>
                                        </button>
                                    </a>
                                </div>
                                <div class="collapse navbar-collapse">
    				  		    	<ul class="navbar-nav navbar-right">
    				  		      	<li>
    				  		      		<button type="button" class="btn btn-default" aria-label="Left Align"><span class="glyphicon glyphicon-backward" aria-hidden="true"></span>上一步
    				  		      		</button>
    				  		      	</li>
    				  		      	<li>
    				  		      		<button type="button" class="btn btn-default" aria-label="Left Align"><span class="glyphicon glyphicon-floppy-open" aria-hidden="true"></span>保存日志
    				  		      		</button>
    				  		      	</li>
    				  		      	<li>
    				  		      		<button type="button" class="btn btn-default" aria-label="Left Align"><span class="glyphicon glyphicon-send" aria-hidden="true"></span>发送
    				  		      		</button>
    				  		      	</li>
    				  		      	<li>
    				  		      		<button type="button" class="btn btn-default" aria-label="Left Align"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>取消
    				  		      		</button>
    				  		      	</li>
    				  		      	<li>
    				  		      		<div></div>
    				  		      	</li>
    				  		      	<li>
    				  		      		<button type="button" class="btn btn-default" aria-label="Left Align"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>关闭操作栏
    				  		      		</button>
    				  		      	</li>
    				  		    	</ul>
                                </div><!-- /.navbar-collapse -->
				  			</div><!-- /.container-fluid -->
					  	</div>
					</nav><!-- 操作导航栏end -->
					
                    <!-- 邮箱列表选择 -->
                    <div class="selectMailList_cover"></div>
                    <div class="selectMailList">
                        <div class="selectMailList_tit">选择收件人抄送人</div>
                        <div class="selectMailList_body">
                            <div class="selectMailList_body_l">
                                <fieldset><legend>电子邮件地址</legend>
                                    <div><ul></ul></div>
                                </fieldset>
                            </div>
                            <div class="selectMailList_body_m">
                                <div class="selectMailList_body_m_top">
                                    <button type="button" class="btn btn-default" aria-label="Left Align"><span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span></button>
                                    <button type="button" class="btn btn-default" aria-label="Left Align"><span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span></button>
                                </div>
                                <div class="selectMailList_body_m_bottom">
                                    <button type="button" class="btn btn-default" aria-label="Left Align"><span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span></button>
                                    <button type="button" class="btn btn-default" aria-label="Left Align"><span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span></button>
                                </div>
                            </div>
                            <div class="selectMailList_body_r">
                                <div class="selectMailList_body_r_to">
                                    <fieldset><legend>收件人</legend>
                                        <div><ul></ul></div>
                                    </fieldset>
                                </div>
                                <div class="selectMailList_body_r_cc">
                                    <fieldset><legend>抄送人</legend>
                                        <div><ul></ul></div>
                                    </fieldset>
                                </div>
                            </div>
                        </div>
                        <div class="selectMailList_foot">
                            <div class="selectMailList_foot_in">
                                <input type="button" class="btn btn-success" id="selectMailList_ok" value="确定">
                                <input type="button" class="btn btn-warning" id="selectMailList_no" value="取消">
                            </div>
                        </div>
                    </div><!-- 邮箱列表选择end -->

                    <!-- 发送意见弹窗 -->
                    <div class="suggestion_div">
                        <div class="suggestion_div_tit">
                            编写发送意见
                        </div>
                        <div class="suggestion_div_body">
                            <div class="suggestion_div_body_in">
                                <fieldset><legend>添加发送意见</legend>
                                    <div class="form-group">
                                        <!-- <label for="suggestion_textarea">上午</label> -->
                                        <textarea class="form-control" rows="6" id="suggestion_textarea" placeholder="添加发送意见"></textarea>
                                    </div>
                                </fieldset>
                            </div>
                        </div>
                        <div class="suggestion_div_foot">
                            <div class="suggestion_div_foot_in">
                                <input type="button" class="btn btn-success" id="suggestion_ok" value="发送">
                                <input type="button" class="btn btn-warning" id="suggestion_no" value="取消">
                            </div>
                        </div>
                    </div>
                    <!-- 发送意见弹窗end -->

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
        <!-- <script src="js/global/responseLoading.js"></script> -->
        <!-- <script src="js/msgbox_unload.js"></script> -->
        <!-- <script type="text/javascript" src="plugins/webuploader/webuploader.min.js"></script> -->
        <script src="js/modules/commerce/workReport.js"></script>
    </body>
</html>