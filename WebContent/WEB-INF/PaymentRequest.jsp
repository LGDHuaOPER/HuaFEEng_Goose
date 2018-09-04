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
        <title>付款申请</title>
        <link rel="shortcut icon" href="image/eoulu.ico" />
        <link rel="bookmark" href="image/eoulu.ico" />
        <link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css">
        <!-- <link rel="stylesheet" type="text/css" href="css/global/global_table_style.css"> -->
        <!-- <link rel="stylesheet" type="text/css" href="plugins/awesomplete/awesomplete.css"> -->
        <!-- <link rel="stylesheet" type="text/css" href="plugins/awesomplete/awesomplete.theme.css"> -->
        <link rel="stylesheet" href="css/modules/personnel/PaymentRequest-9c26a25e2c.min.css" type="text/css">
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
                                        <!-- showName showCategoryItem -->
                                        <thead class="inormal">
                                            <tr>
                                                <th class="t1">序号</th>
                                                <th class="t2">申请日期</th>
                                                <th class="t3">部门<span class="glyphicon glyphicon-plus" aria-hidden="true"></span></th>
                                                <th class="t4">姓名</th>
                                                <th class="t5">费用类型</th>
                                                <th class="t6">费用项目</th>
                                                <th class="t7"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>申请详情</th>
                                                <th class="t8">金额</th>
                                                <th class="t9">付款信息</th>
                                                <th class="t10">链接信息</th>
                                                <th class="t11">付款截止日期</th>
                                                <th class="t12">查看附件</th>
                                                <th class="t13">发送状态</th>
                                                <th class="t14">付款状态</th>
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
                            <div class="add_NonStandard_tit_l">添加付款申请</div>
                            <div class="add_NonStandard_tit_r">
                            	<button type="button" class="btn btn-default" aria-label="Left Align"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></button>
                            </div>
                        </div>
                        <div class="add_NonStandard_body">
                            <div class="add_NonStandard_body_in">
                            	<fieldset><legend>申请人信息</legend>
                            		<div class="container-fluid">
                            			<!-- <div class="form-horizontal">
                            			    <div class="form-group">
                            			    	<label for="add_info_Department" class="col-sm-2 control-label">部门</label>
                            			    	<div class="col-sm-10">
                            			        	<select name="payment_depart" class="form-control" id="add_info_Department"></select>
                            			        </div>
                            			    </div>
                            			    <div class="form-group">
                            			    	<label for="add_info_Department" class="col-sm-2 control-label">姓名</label>
                            			    	<div class="col-sm-10">
                            			        	<select name="search_Year" class="form-control"></select>
                            			        </div>
                            			    </div>
                            			</div> -->
                            			<div class="row">
                            			  	<div class="col-md-6 col-lg-6">
                            			  		<div class="form-horizontal">
                            			  		    <div class="form-group">
                            			  		    	<label for="add_info_Department" class="col-sm-3 control-label">部门</label>
                            			  		    	<div class="col-sm-9">
                            			  		        	<select name="payment_depart" class="form-control" id="add_info_Department"></select>
                            			  		        </div>
                            			  		    </div>
                            			  		</div>
                            			  	</div>
                            			  	<div class="col-md-6 col-lg-6">
                            			  		<div class="form-horizontal">
                            			  		    <div class="form-group">
                            			  		    	<label for="add_info_Applicant" class="col-sm-3 control-label">姓名</label>
                            			  		    	<div class="col-sm-9">
                            			  		        	<select class="form-control" id="add_info_Applicant"></select>
                            			  		        </div>
                            			  		    </div>
                            			  		</div>
                            			  	</div>
                            			</div>
                            		</div><!-- container-fluid end -->
                            	</fieldset>
                            	<fieldset><legend>基本信息</legend>
                            		<div class="container-fluid">
                            			<div class="row">
                            			  	<div class="col-md-6 col-lg-6">
                            			  		<div class="form-horizontal">
                            			  		    <div class="form-group">
                            			  		    	<label for="add_info_ExpenseCategory" class="col-sm-3 control-label">费用类型</label>
                            			  		    	<div class="col-sm-9">
                            			  		        	<select name="payment_ExpenseCategory" class="form-control" id="add_info_ExpenseCategory"></select>
                            			  		        </div>
                            			  		    </div>
                            			  		    <div class="form-group">
                            			  		    	<label for="add_info_ExpenseDetails" class="col-sm-3 control-label">申请详情</label>
                            			  		    	<div class="col-sm-9">
                            			  		        	<input type="text" class="form-control" maxlength="15" id="add_info_ExpenseDetails" placeholder="简明扼要概括，不超过15个字">
                            			  		        </div>
                            			  		    </div>
                            			  		    <div class="form-group">
                            			  		    	<label for="add_info_ApplicationDate" class="col-sm-3 control-label">申请日期</label>
                            			  		    	<div class="col-sm-9">
                            			  		        	<input type="date" class="form-control" id="add_info_ApplicationDate">
                            			  		        </div>
                            			  		    </div>
                            			  		</div>
                            			  	</div>
                            			  	<div class="col-md-6 col-lg-6">
                            			  		<div class="form-horizontal">
                            			  		    <div class="form-group">
                            			  		    	<label for="add_info_ExpenseItem" class="col-sm-3 control-label">费用项目</label>
                            			  		    	<div class="col-sm-9">
                            			  		        	<select class="form-control" id="add_info_ExpenseItem"></select>
                            			  		        </div>
                            			  		    </div>
                            			  		    <div class="form-group">
                            			  		    	<label for="add_info_Amount" class="col-sm-3 control-label">金额</label>
                            			  		    	<div class="col-sm-9">
                            			  		        	<input type="text" class="form-control" id="add_info_Amount">
                            			  		        </div>
                            			  		    </div>
                            			  		    <div class="form-group">
                            			  		    	<label for="add_info_PayDate" class="col-sm-3 control-label sm_label">付款截止日期</label>
                            			  		    	<div class="col-sm-9">
                            			  		        	<input type="date" class="form-control" id="add_info_PayDate">
                            			  		        </div>
                            			  		    </div>
                            			  		</div>
                            			  	</div>
                            			</div>
                            		</div><!-- container-fluid end -->
                            	</fieldset>
                            	<fieldset><legend>付款信息</legend>
                            		<div class="container-fluid">
                            			<div class="row">
                            			  	<div class="col-md-6 col-lg-6">
                            			  		<div class="form-horizontal">
                            			  		    <div class="form-group">
                            			  		    	<label for="add_info_Payee" class="col-sm-3 control-label">收款户名</label>
                            			  		    	<div class="col-sm-9">
                            			  		        	<input type="text" class="form-control" id="add_info_Payee">
                            			  		        </div>
                            			  		    </div>
                            			  		    <div class="form-group">
                            			  		    	<label for="add_info_Account" class="col-sm-3 control-label">账号</label>
                            			  		    	<div class="col-sm-9">
                            			  		        	<input type="text" class="form-control" id="add_info_Account">
                            			  		        </div>
                            			  		    </div>
                            			  		</div>
                            			  	</div>
                            			  	<div class="col-md-6 col-lg-6">
                            			  		<div class="form-horizontal">
                            			  		    <div class="form-group">
                            			  		    	<label for="add_info_DepositBank" class="col-sm-3 control-label">开户行</label>
                            			  		    	<div class="col-sm-9">
                            			  		        	<input type="text" class="form-control" id="add_info_DepositBank">
                            			  		        </div>
                            			  		    </div>
                            			  		    <div class="form-group">
                            			  		    	<label for="add_info_PaymentRemark" class="col-sm-3 control-label">备注</label>
                            			  		    	<div class="col-sm-9">
                            			  		        	<input type="text" class="form-control" id="add_info_PaymentRemark">
                            			  		        </div>
                            			  		    </div>
                            			  		</div>
                            			  	</div>
                            			</div>
                            		</div><!-- container-fluid end -->
                            	</fieldset>
                            	<fieldset><legend>链接信息</legend>
                            		<div class="container-fluid">
                            			<div class="row">
                            			  	<div class="col-md-6 col-lg-6">
                            			  		<div class="form-horizontal">
                            			  		    <div class="form-group">
                            			  		    	<label for="add_info_StoreName" class="col-sm-3 control-label">店家名称</label>
                            			  		    	<div class="col-sm-9">
                            			  		        	<input type="text" class="form-control" id="add_info_StoreName">
                            			  		        </div>
                            			  		    </div>
                            			  		    <div class="form-group">
                            			  		    	<label for="add_info_OrderNO" class="col-sm-3 control-label">订单号</label>
                            			  		    	<div class="col-sm-9">
                            			  		        	<input type="text" class="form-control" id="add_info_OrderNO">
                            			  		        </div>
                            			  		    </div>
                            			  		</div>
                            			  	</div>
                            			  	<div class="col-md-6 col-lg-6">
                            			  		<div class="form-horizontal">
                            			  		    <div class="form-group">
                            			  		    	<label for="add_info_Link" class="col-sm-3 control-label">链接</label>
                            			  		    	<div class="col-sm-9">
                            			  		        	<input type="text" class="form-control" id="add_info_Link">
                            			  		        </div>
                            			  		    </div>
                            			  		    <div class="form-group">
                            			  		    	<label for="add_info_LinkRemark" class="col-sm-3 control-label">备注</label>
                            			  		    	<div class="col-sm-9">
                            			  		        	<input type="text" class="form-control" id="add_info_LinkRemark">
                            			  		        </div>
                            			  		    </div>
                            			  		</div>
                            			  	</div>
                            			</div>
                            		</div><!-- container-fluid end -->
                            	</fieldset>
                                <fieldset><legend>附件上传</legend>
                                    <div class="form-group">
                                        <label for="add_file_Upload" class="trigger_click"><button type="button" class="btn btn-info"><span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span>&nbsp;选择文件</button></label>
                                        <input type="file" id="add_file_Upload" multiple="multiple" accept="application/msword, application/pdf, image/*, application/vnd.ms-powerpoint, text/plain, application/vnd.openxmlformats-officedocument.presentationml.presentation, application/vnd.openxmlformats-officedocument.wordprocessingml.document">
                                        <label class="add_info_upload"><button type="button" class="btn btn-info"><span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span>&nbsp;上传</button></label>
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
                            <div class="update_NonStandard_tit_l">修改付款申请</div>
                            <div class="update_NonStandard_tit_r">
                                <button type="button" class="btn btn-default" aria-label="Left Align"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></button>
                            </div>
                        </div>
                        <div class="update_NonStandard_body">
                            <div class="update_NonStandard_body_in">
                                <fieldset><legend>申请人信息</legend>
                                    <div class="container-fluid">
                                        <!-- <div class="form-horizontal">
                                            <div class="form-group">
                                                <label for="update_info_Department" class="col-sm-2 control-label">部门</label>
                                                <div class="col-sm-10">
                                                    <select name="payment_depart" class="form-control" id="update_info_Department"></select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="update_info_Department" class="col-sm-2 control-label">姓名</label>
                                                <div class="col-sm-10">
                                                    <select name="search_Year" class="form-control"></select>
                                                </div>
                                            </div>
                                        </div> -->
                                        <div class="row">
                                            <div class="col-md-6 col-lg-6">
                                                <div class="form-horizontal">
                                                    <div class="form-group">
                                                        <label for="update_info_Department" class="col-sm-3 control-label">部门</label>
                                                        <div class="col-sm-9">
                                                            <select name="payment_depart" class="form-control" id="update_info_Department"></select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6 col-lg-6">
                                                <div class="form-horizontal">
                                                    <div class="form-group">
                                                        <label for="update_info_Applicant" class="col-sm-3 control-label">姓名</label>
                                                        <div class="col-sm-9">
                                                            <select class="form-control" id="update_info_Applicant"></select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div><!-- container-fluid end -->
                                </fieldset>
                                <fieldset><legend>基本信息</legend>
                                    <div class="container-fluid">
                                        <div class="row">
                                            <div class="col-md-6 col-lg-6">
                                                <div class="form-horizontal">
                                                    <div class="form-group">
                                                        <label for="update_info_ExpenseCategory" class="col-sm-3 control-label">费用类型</label>
                                                        <div class="col-sm-9">
                                                            <select name="payment_ExpenseCategory" class="form-control" id="update_info_ExpenseCategory"></select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="update_info_ExpenseDetails" class="col-sm-3 control-label">申请详情</label>
                                                        <div class="col-sm-9">
                                                            <input type="text" class="form-control" maxlength="15" id="update_info_ExpenseDetails" placeholder="简明扼要概括，不超过15个字">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="update_info_ApplicationDate" class="col-sm-3 control-label">申请日期</label>
                                                        <div class="col-sm-9">
                                                            <input type="date" class="form-control" id="update_info_ApplicationDate">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6 col-lg-6">
                                                <div class="form-horizontal">
                                                    <div class="form-group">
                                                        <label for="update_info_ExpenseItem" class="col-sm-3 control-label">费用项目</label>
                                                        <div class="col-sm-9">
                                                            <select class="form-control" id="update_info_ExpenseItem"></select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="update_info_Amount" class="col-sm-3 control-label">金额</label>
                                                        <div class="col-sm-9">
                                                            <input type="text" class="form-control" id="update_info_Amount">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="update_info_PayDate" class="col-sm-3 control-label sm_label">付款截止日期</label>
                                                        <div class="col-sm-9">
                                                            <input type="date" class="form-control" id="update_info_PayDate">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div><!-- container-fluid end -->
                                </fieldset>
                                <fieldset><legend>付款信息</legend>
                                    <div class="container-fluid">
                                        <div class="row">
                                            <div class="col-md-6 col-lg-6">
                                                <div class="form-horizontal">
                                                    <div class="form-group">
                                                        <label for="update_info_Payee" class="col-sm-3 control-label">收款户名</label>
                                                        <div class="col-sm-9">
                                                            <input type="text" class="form-control" id="update_info_Payee">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="update_info_Account" class="col-sm-3 control-label">账号</label>
                                                        <div class="col-sm-9">
                                                            <input type="text" class="form-control" id="update_info_Account">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6 col-lg-6">
                                                <div class="form-horizontal">
                                                    <div class="form-group">
                                                        <label for="update_info_DepositBank" class="col-sm-3 control-label">开户行</label>
                                                        <div class="col-sm-9">
                                                            <input type="text" class="form-control" id="update_info_DepositBank">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="update_info_PaymentRemark" class="col-sm-3 control-label">备注</label>
                                                        <div class="col-sm-9">
                                                            <input type="text" class="form-control" id="update_info_PaymentRemark">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div><!-- container-fluid end -->
                                </fieldset>
                                <fieldset><legend>链接信息</legend>
                                    <div class="container-fluid">
                                        <div class="row">
                                            <div class="col-md-6 col-lg-6">
                                                <div class="form-horizontal">
                                                    <div class="form-group">
                                                        <label for="update_info_StoreName" class="col-sm-3 control-label">店家名称</label>
                                                        <div class="col-sm-9">
                                                            <input type="text" class="form-control" id="update_info_StoreName">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="update_info_OrderNO" class="col-sm-3 control-label">订单号</label>
                                                        <div class="col-sm-9">
                                                            <input type="text" class="form-control" id="update_info_OrderNO">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6 col-lg-6">
                                                <div class="form-horizontal">
                                                    <div class="form-group">
                                                        <label for="update_info_Link" class="col-sm-3 control-label">链接</label>
                                                        <div class="col-sm-9">
                                                            <input type="text" class="form-control" id="update_info_Link">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="update_info_LinkRemark" class="col-sm-3 control-label">备注</label>
                                                        <div class="col-sm-9">
                                                            <input type="text" class="form-control" id="update_info_LinkRemark">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div><!-- container-fluid end -->
                                </fieldset>
                                <fieldset><legend>附件上传</legend>
                                    <div class="form-group">
                                        <label for="update_file_Upload" class="trigger_click"><button type="button" class="btn btn-info"><span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span>&nbsp;选择文件</button></label>
                                        <input type="file" id="update_file_Upload" multiple="multiple" accept="application/msword, application/pdf, image/*, application/vnd.ms-powerpoint, text/plain, application/vnd.openxmlformats-officedocument.presentationml.presentation, application/vnd.openxmlformats-officedocument.wordprocessingml.document">
                                        <label class="update_info_upload"><button type="button" class="btn btn-info"><span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span>&nbsp;上传</button></label>
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

        			<!-- 邮件发送模板 -->
        			<div class="mail_cover_bg"></div>
        			<div class="mail_template">
        				<div class="mail_template_tit">
                            <div class="mail_template_tit_l">邮件模板</div>
                            <div class="mail_template_tit_r">
                                <button type="button" class="btn btn-default" aria-label="Left Align"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></button>
                            </div>            
                        </div>
        				<div class="mail_template_body">
        					<fieldset><legend>发件人</legend>
        						<div class="form-group">
        						    <!-- <label for="exampleInputEmail1">上午</label> -->
                                    <input type="text" class="form-control" placeholder="选择发件人" value="remind@eoulu.com" readonly disabled>
        						</div>
        					</fieldset>
        					<fieldset><legend>收件人</legend>
                                <!-- <div class="TO_wrapper"><div class="mail_item"><span class="mail_item_info">收件人1</span><input type="text" class="form-control" value="wangxiaoliang@eoulu.com"><span class="mail_item_add glyphicon glyphicon-plus" aria-hidden="true"></span><span class="mail_item_del glyphicon glyphicon-remove" aria-hidden="true"></span></div><div class="mail_item"><span class="mail_item_info">收件人2</span><input type="text" class="form-control" value="zhufei@eoulu.com"><span class="mail_item_add glyphicon glyphicon-plus" aria-hidden="true"></span><span class="mail_item_del glyphicon glyphicon-remove" aria-hidden="true"></span></div></div> -->
                                <div class="btn-group TO_radio_div" data-toggle="buttons">
                                    <label class="btn btn-info active">
                                        <input type="radio" name="TO_radio" id="TO_radio1" autocomplete="off" checked>王晓亮
                                    </label>
                                    <label class="btn btn-info">
                                        <input type="radio" name="TO_radio" id="TO_radio2" autocomplete="off">朱飞
                                    </label>
                                </div>
                                <div class="TO_wrapper"><div class="mail_item"><span class="mail_item_info">收件人</span><input type="text" class="form-control" value="wangxiaoliang@eoulu.com" readonly disabled><span class="mail_item_select glyphicon glyphicon-ok" aria-hidden="true"></span></div><div class="mail_item"><span class="mail_item_info">收件人</span><input type="text" class="form-control" value="zhufei@eoulu.com" readonly disabled><span class="mail_item_select glyphicon glyphicon-ok" aria-hidden="true"></span></div></div>
        					</fieldset>
        					<fieldset><legend>抄送人</legend>
        						<div class="CC_wrapper"><div class="mail_item"><span class="mail_item_info">抄送人1</span><input type="text" class="form-control" value="wangxiaoliang@eoulu.com"><span class="mail_item_add glyphicon glyphicon-plus" aria-hidden="true"></span><span class="mail_item_del glyphicon glyphicon-remove" aria-hidden="true"></span></div><div class="mail_item"><span class="mail_item_info">抄送人2</span><input type="text" class="form-control" value="luoxiaoxu@eoulu.com"><span class="mail_item_add glyphicon glyphicon-plus" aria-hidden="true"></span><span class="mail_item_del glyphicon glyphicon-remove" aria-hidden="true"></span></div><div class="mail_item"><span class="mail_item_info">抄送人3</span><input type="text" class="form-control" value="zhaona@eoulu.com"><span class="mail_item_add glyphicon glyphicon-plus" aria-hidden="true"></span><span class="mail_item_del glyphicon glyphicon-remove" aria-hidden="true"></span></div><div class="mail_item"><span class="mail_item_info">抄送人4</span><input type="text" class="form-control" value="gaona@eoulu.com"><span class="mail_item_add glyphicon glyphicon-plus" aria-hidden="true"></span><span class="mail_item_del glyphicon glyphicon-remove" aria-hidden="true"></span></div><div class="mail_item"><span class="mail_item_info">抄送人5</span><input type="text" class="form-control" value="zhufei@eoulu.com"><span class="mail_item_add glyphicon glyphicon-plus" aria-hidden="true"></span><span class="mail_item_del glyphicon glyphicon-remove" aria-hidden="true"></span></div><div class="mail_item"><span class="mail_item_info">抄送人6</span><input type="text" class="form-control" value="fanminmin@eoulu.com"><span class="mail_item_add glyphicon glyphicon-plus" aria-hidden="true"></span><span class="mail_item_del glyphicon glyphicon-remove" aria-hidden="true"></span></div></div>
        					</fieldset>
        					<fieldset><legend>主题</legend>
        						<div class="form-group">
        						    <input type="text" class="form-control" id="i_mail_Subject" placeholder="填写主题">
        						</div>
        					</fieldset>
        					<fieldset class="mail_con_field"><legend>内容</legend>
                                <div class="container-fluid">
                                    <!-- <div>您好！</div>
                                    <div>申请详情</div>
                                    <div class="row">
                                        <div class="col-md-6 col-lg-6">
                                            <div class="paymoney_detail_table_wrapper">
                                                <table>
                                                    <thead><tr><th colspan="2">付款信息</th></tr></thead>
                                                    <tbody>
                                                        <tr><td>收款户名：</td><td></td></tr>
                                                        <tr><td>账号：</td><td></td></tr>
                                                        <tr><td>开户行：</td><td></td></tr>
                                                        <tr><td>备注：</td><td></td></tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <div class="col-md-6 col-lg-6">
                                            <div class="link_detail_table_wrapper">
                                                <table>
                                                    <thead><tr><th colspan="2">链接信息</th></tr></thead>
                                                    <tbody>
                                                        <tr><td>店家名称：</td><td></td></tr>
                                                        <tr><td>订单号：</td><td></td></tr>
                                                        <tr><td>链接：</td><td></td></tr>
                                                        <tr><td>备注：</td><td></td></tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div>请协助尽快安排付款，非常感谢！</div> -->
                                </div><!-- container-fluid end -->
                            </fieldset>
                            <!-- <fieldset class="mail_con_field"><legend>内容</legend>
        						<div class="form-group">
        						    <textarea class="form-control" rows="5" id="i_mail_Content" placeholder="填写内容"></textarea>
        						</div>
        					</fieldset> -->
        					<!-- <fieldset><legend>上传附件<input type="file" id="BiddingDocumentUpload" accept="application/msword, application/pdf, image/*, application/vnd.ms-powerpoint, text/plain, application/vnd.openxmlformats-officedocument.presentationml.presentation, application/vnd.openxmlformats-officedocument.wordprocessingml.document">
                                <label for="BiddingDocumentUpload"><span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span></label><label class="upload_file_info"></label></legend>
        						<div class="form-group">
                                    <textarea class="form-control" rows="2" id="i_mail_Attachment" placeholder="点击图标上传附件" readonly></textarea>
        						</div>
        					</fieldset> -->
        				</div>
        				<div class="mail_template_foot">
        					<div class="mail_template_body_foot_in">
        						<input type="button" class="btn btn-success" id="mail_template_submit" value="发送">
        						<input type="button" class="btn btn-warning" id="mail_template_close" value="取消">
        					</div>
        				</div>
        			</div><!-- 邮件发送模板end -->

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
        <!--
        <script src="js/libs/bootstrap.min.js"></script>
        <script src="plugins/awesomplete/awesomplete.min.js"></script>-->
        <script src="js/modules/personnel/PaymentRequest-da05c85c2a.min.js"></script>
    </body>
</html>