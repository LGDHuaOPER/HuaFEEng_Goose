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
        <title>报销申请</title>
        <link rel="shortcut icon" href="image/eoulu.ico" />
        <link rel="bookmark" href="image/eoulu.ico" />
        <link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="css/global/eouluCustom.css" type="text/css">
        <!-- <link rel="stylesheet" type="text/css" href="plugins/webuploader/webuploader.css" /> -->
        <link rel="stylesheet" href="css/reimburse.css" type="text/css">
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
                            <span style="display: none;" id="reimburse_name">${name}</span>
                            <div class="g_body">
                                <div class="m_button">
                                    <div class="m_button_l">
                                        <input class="btn btn-info" type="button" value="添加">
                                        <input class="btn btn-info" type="button" value="全选">
                                        <input class="btn btn-info" type="button" value="导出">
                                        <input class="btn btn-info" type="button" value="通知列表">
                                    </div>
                                    <div class="m_button_r">
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
                                        <thead>
                                            <tr>
                                            	<th class="t0" rowspan="2"><input type="checkbox"></th>
                                                <th class="t1" rowspan="2">序号</th>
                                                <th class="t2" rowspan="2">部门</th>
                                                <th class="t3" rowspan="2">姓名</th>
                                                <th class="t4" rowspan="2">报销总金额</th>
                                                <th class="t5" rowspan="2">报销详情</th>
                                                <th class="t6" rowspan="2">提交日期</th>
                                                <th class="t7" colspan="4">报销附件</th>
                                                <th class="t8" rowspan="2">是否通过</th>
                                            </tr>
                                            <tr>
                                                <th class="t9">票据截图</th>
                                                <th class="t10">滴滴电子发票</th>
                                                <th class="t11">滴滴行程单</th>
                                                <th class="t12">其他</th>
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
                            <div class="add_NonStandard_tit_l">添加报销单</div>
                            <div class="add_NonStandard_tit_r">关闭</div>
                        </div>
                        <div class="add_NonStandard_body">
                            <div class="add_NonStandard_body_in">
                            	<fieldset class="add_NonStandard_personal"><legend>个人信息</legend>
                            		<div class="add_NonStandard_personal_div">
                            			<div class="add_NonStandard_personal_l">
                            				<div class="line_01"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>部门</div>
                            				<div class="line_02">
                            				    <select class="info_Department"></select>
                            				</div>
                            			</div>
                            			<div class="add_NonStandard_personal_r">
                            				<div class="line_01"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>姓名</div>
										    <div class="line_02">
										        <select class="info_Name"></select>
										    </div>
                            			</div>
                            		</div>
                            	</fieldset>
                            	<fieldset class="add_NonStandard_details"><legend>报销详情</legend>
									<div class="add_NonStandard_details_div">
										<table class="add_NonStandard_details_table table table-bordered table-hover table-condensed">
											<thead>
												<tr>
													<th>报销类型</th>
													<th>金额</th>
													<th>事由</th>
													<th>客户名</th>
													<th>往返城市</th>
													<th>往返时间</th>
													<th>添加</th>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>
									<!-- 出差地点、事由 -->
									<div class="add_NonStandard_details_address">
										<table class="add_NonStandard_details_address_table table table-bordered table-hover table-condensed">
											<tbody>
											</tbody>
											<tfoot>
												<tr>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td>总计</td>
													<td class="insert_detail_td_calc_days"></td>
													<td></td>
												</tr>
											</tfoot>
										</table>
									</div>
									<!-- 第一步提交 -->
									<div class="add_NonStandard_submit_1">
										<div class="add_NonStandard_submit_1_in">
											<input type="button" class="btn btn-success" id="NonStandard_addsubmit_1" value="信息提交">
										</div>
									</div>
									<!-- 报销附件 -->
									<blockquote>
										<h5>报销附件<span class="small_small">（点击右边图标打开上传界面）</span>&nbsp;<span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span></h5>
									</blockquote>
									<div class="add_NonStandard_details_files">
										<div class="line_0">
										    <div class="line_01">票据截图</div>
										    <div class="line_02 line_relative">
										        <input type="text" class="info_BillScreenshot" readonly="readonly" disabled="disabled"><input type="button" class="btn btn-primary" value="上传">
										    </div>
										</div>
										<div class="line_0">
										    <div class="line_01">滴滴电子发票</div>
										    <div class="line_02 line_relative">
										        <input type="text" class="info_ElectronicInvoice" readonly="readonly" disabled="disabled"><input type="button" class="btn btn-primary" value="上传">
										    </div>
										</div>
										<div class="line_0">
										    <div class="line_01">滴滴行程单</div>
										    <div class="line_02 line_relative">
										        <input type="text" class="info_TravelPaper" readonly="readonly" disabled="disabled"><input type="button" class="btn btn-primary" value="上传">
										    </div>
										</div>
										<div class="line_0">
										    <div class="line_01">其他</div>
										    <div class="line_02 line_relative">
										        <input type="text" class="info_Others" readonly="readonly" disabled="disabled"><input type="button" class="btn btn-primary" value="上传">
										    </div>
										</div>
									</div>
                            	</fieldset>
                            </div><!-- add_NonStandard_body_in end -->
                        </div>
                        <div class="add_NonStandard_foot">
                            <div class="add_NonStandard_foot_in">
                                <input type="button" class="btn btn-success" id="NonStandard_addsubmit" value="附件提交">
                                <input type="button" class="btn btn-warning" id="NonStandard_addclose" value="取消">
                            </div>
                        </div>
                    </div>

                    <!-- 修改 -->
                    <div class="update_NonStandard" style="display: none;">
                        <div class="update_NonStandard_tit">
                            <div class="update_NonStandard_tit_l">修改报销单</div>
                            <div class="update_NonStandard_tit_r">关闭</div>
                        </div>
                        <div class="update_NonStandard_body">
                            <div class="update_NonStandard_body_in">
                            	<fieldset class="update_NonStandard_personal"><legend>个人信息</legend>
                            		<div class="update_NonStandard_personal_div">
                            			<div class="update_NonStandard_personal_l">
                            				<div class="line_01"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>部门</div>
                            				<div class="line_02">
                            				    <select class="info_Department"></select>
                            				</div>
                            			</div>
                            			<div class="update_NonStandard_personal_r">
                            				<div class="line_01"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>姓名</div>
										    <div class="line_02">
										        <select class="info_Name"></select>
										    </div>
                            			</div>
                            		</div>
                            	</fieldset>
                            	<fieldset class="update_NonStandard_details"><legend>报销详情</legend>
									<div class="update_NonStandard_details_div">
										<table class="update_NonStandard_details_table table table-bordered table-hover table-condensed">
											<thead>
												<tr>
													<th>报销类型</th>
													<th>金额</th>
													<th>事由</th>
													<th>客户名</th>
													<th>往返城市</th>
													<th>往返时间</th>
													<th>添加</th>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>
									<!-- 出差地点、事由 -->
									<div class="update_NonStandard_details_address">
										<table class="update_NonStandard_details_address_table table table-bordered table-hover table-condensed">
											<tbody>
											</tbody>
											<tfoot>
												<tr>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td>总计</td>
													<td class="insert_detail_td_calc_days"></td>
													<td></td>
												</tr>
											</tfoot>
										</table>
									</div>
									<!-- 报销附件 -->
									<blockquote>
										<h5>报销附件<span class="small_small">（点击右边图标打开上传界面）</span>&nbsp;<span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span></h5>
									</blockquote>
									<div class="update_NonStandard_details_files">
										<div class="line_0">
										    <div class="line_01">票据截图</div>
										    <div class="line_02 line_relative">
										        <input type="text" class="info_BillScreenshot" readonly="readonly" disabled="disabled"><input type="button" class="btn btn-primary" value="上传">
										    </div>
										</div>
										<div class="line_0">
										    <div class="line_01">滴滴电子发票</div>
										    <div class="line_02 line_relative">
										        <input type="text" class="info_ElectronicInvoice" readonly="readonly" disabled="disabled"><input type="button" class="btn btn-primary" value="上传">
										    </div>
										</div>
										<div class="line_0">
										    <div class="line_01">滴滴行程单</div>
										    <div class="line_02 line_relative">
										        <input type="text" class="info_TravelPaper" readonly="readonly" disabled="disabled"><input type="button" class="btn btn-primary" value="上传">
										    </div>
										</div>
										<div class="line_0">
										    <div class="line_01">其他</div>
										    <div class="line_02 line_relative">
										        <input type="text" class="info_Others" readonly="readonly" disabled="disabled"><input type="button" class="btn btn-primary" value="上传">
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
					<!-- 报销详情模态框 -->
					<div class="modal fade" id="reimburseModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
					  <div class="modal-dialog modal-lg" role="document">
					    <div class="modal-content">
					      <div class="modal-header">
					        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					        <h4 class="modal-title" id="exampleModalLabel"></h4>
					      </div>
					      <div class="modal-body">
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-md-4 col-lg-4">
                                        <fieldset class="iParent" data-iclassify="a"><legend>滴滴电子发票</legend>
                                            <div class="form-group">
                                                <label for="add_file_Upload" class="trigger_click"><button type="button" class="btn btn-info btn-sm"><span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span>&nbsp;选择文件</button></label>
                                                <input type="file" id="add_file_Upload" multiple="multiple" accept="application/msword, application/pdf, image/*, application/vnd.ms-powerpoint, text/plain, application/vnd.openxmlformats-officedocument.presentationml.presentation, application/vnd.openxmlformats-officedocument.wordprocessingml.document">
                                                <label class="add_info_upload"><button type="button" class="btn btn-info btn-sm"><span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span>&nbsp;上传</button></label>
                                            </div>
                                            <div class="add_fileList_ul_wrapper">
                                                <div class="add_fileList_info">
                                                    上传总进度：
                                                    <div class="progress">
                                                        <div class="progress-bar progress-bar-info progress-bar-striped active" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em;">0%</div>
                                                    </div>
                                                </div>
                                                <ul class="list-group" id="add_fileList_ul">
                                                    <!-- <li class="list-group-item list-group-item-success"><span class="badge">14</span>Cras justo odio</li>
                                                    <li class="list-group-item list-group-item-danger"><span class="badge">14</span>Dapibus ac facilisis in</li>
                                                    <li class="list-group-item"><span class="badge">14</span>Morbi leo risus</li> -->
                                                </ul>
                                            </div>
                                        </fieldset>
                                    </div>
                                    <div class="col-md-4 col-lg-4">
                                        <fieldset class="iParent" data-iclassify="b"><legend>滴滴行程单</legend>
                                            <div class="form-group">
                                                <label for="add_file_Upload2" class="trigger_click"><button type="button" class="btn btn-info btn-sm"><span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span>&nbsp;选择文件</button></label>
                                                <input type="file" id="add_file_Upload2" multiple="multiple" accept="application/msword, application/pdf, image/*, application/vnd.ms-powerpoint, text/plain, application/vnd.openxmlformats-officedocument.presentationml.presentation, application/vnd.openxmlformats-officedocument.wordprocessingml.document">
                                                <label class="add_info_upload2"><button type="button" class="btn btn-info btn-sm"><span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span>&nbsp;上传</button></label>
                                            </div>
                                            <div class="add_fileList_ul_wrapper">
                                                <div class="add_fileList_info">
                                                    上传总进度：
                                                    <div class="progress">
                                                        <div class="progress-bar progress-bar-info progress-bar-striped active" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em;">0%</div>
                                                    </div>
                                                </div>
                                                <ul class="list-group" id="add_fileList_ul2">
                                                </ul>
                                            </div>
                                        </fieldset>
                                    </div>
                                    <div class="col-md-4 col-lg-4">
                                        <fieldset class="iParent" data-iclassify="c"><legend>其他</legend>
                                            <div class="form-group">
                                                <label for="add_file_Upload3" class="trigger_click"><button type="button" class="btn btn-info btn-sm"><span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span>&nbsp;选择文件</button></label>
                                                <input type="file" id="add_file_Upload3" multiple="multiple" accept="application/msword, application/pdf, image/*, application/vnd.ms-powerpoint, text/plain, application/vnd.openxmlformats-officedocument.presentationml.presentation, application/vnd.openxmlformats-officedocument.wordprocessingml.document">
                                                <label class="add_info_upload3"><button type="button" class="btn btn-info btn-sm"><span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span>&nbsp;上传</button></label>
                                            </div>
                                            <div class="add_fileList_ul_wrapper">
                                                <div class="add_fileList_info">
                                                    上传总进度：
                                                    <div class="progress">
                                                        <div class="progress-bar progress-bar-info progress-bar-striped active" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em;">0%</div>
                                                    </div>
                                                </div>
                                                <ul class="list-group" id="add_fileList_ul3">
                                                </ul>
                                            </div>
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
					      	<div class="modal-body_detail">
					      		<table class="table table-bordered table-hover table-condensed">
					      			<thead>
					      				<tr>
											<th>报销类型</th>
											<th>金额</th>
											<th>事由</th>
											<th>客户名</th>
											<th>往返城市</th>
                                            <th>往返时间</th>
											<th>报销附件<br>（票据截图）</th>
										</tr>
					      			</thead>
					      			<tbody></tbody>
					      		</table>
					      	</div>
					      	<div class="modal-body_travel">
					      		<table class="table table-bordered table-hover table-condensed">
					      			<tbody></tbody>
					      			<tfoot>
					      				<tr>
					      					<td></td>
					      					<td></td>
					      					<td></td>
					      					<td></td>
					      					<td></td>
					      					<td></td>
					      					<td>总计</td>
					      					<td class="insert_detail_calc_days"></td>
					      				</tr>
					      			</tfoot>
					      		</table>
					      	</div>
					      </div>
					      <div class="modal-footer">
                            <button type="button" class="btn btn-success">提交</button>
					        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
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

        <script src="js/libs/bootstrap.min.js"></script>
        <script src="js/global/responseLoading.js"></script>
        <script src="js/reimburse.js"></script>
    </body>
</html>