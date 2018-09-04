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
	<title>报销申请-邮件通知列表</title>
	<link rel="shortcut icon" href="image/eoulu.ico" />
	<link rel="bookmark" href="image/eoulu.ico" />
	<link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css">
	<!-- <link rel="stylesheet" href="css/global/eouluCustom.css" type="text/css"> -->
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

	    /*布局*/
        html,body{
            width:100%;
            height:100%;
        }

        #transports_wrapper {
            position:fixed;
            overflow:auto;
            width:100%;
            height:100%;
            box-sizing:border-box;
        }

        #transports_sticker {
            width:100%;
            min-height:100%;
            box-sizing:border-box;
            position:relative;
        }

        #transports_sticker-con {
            padding-bottom:40px;
            box-sizing:border-box;

        }

        #transports_footer {
            margin-top:-40px;
        }

    /*自定义样式*/
        body {
        	background: #e4e8eb;
        	height: auto;
        }

        .g_container {
        	min-width: 1000px;
        	width: 90%;
        	margin: 0 auto;
        }

		/*页面导航栏与搜索*/
        .g_container_info {
        	background: #ddd;
        	opacity: 0.7;
        	width: 100%;
        	height: 50px;
        	margin-bottom: 10px;
        	margin-top: 10px;
        }

        .g_container_info_l {
        	display: inline-block;
        	vertical-align: top;
        	height: 50px;
        	line-height: 50px;
        	width: 255px;
        }

        .g_container_info_r {
        	display: inline-block;
        	padding-right: 10px;
        	vertical-align: top;
        	text-align: right;
        	height: 50px;
        	line-height: 50px;
        	width: calc(100% - 260px);
        }

        	/*ul胶囊切换*/
        .g_container_info_l ul {
            margin-top: 0;
            margin-bottom: 0;
        	padding-left: 0;
            list-style: none;
            line-height: 30px;
            font-size: 16px;
        }

        .g_container_info_l ul>li+li {
            margin-left: 4px;
        }

        .g_container_info_l ul>li {
            float: left;
            position: relative;
            display: block;
            cursor: pointer;
        }

        .g_container_info_l ul>li>span {
        	position: relative;
            display: block;
            padding: 10px 20px;
            border-radius: 4px;
            color: #337ab7;
            text-decoration: none;
            background-color: transparent;
        }

        .g_container_info_l ul>li.current_tab>span {
        	color: #fff;
        	background-color: #337ab7;
        }

        .g_container_info_l ul>li>span:hover {
        	background-color: #eee;
        	cursor: pointer;
        }
        	/*ul胶囊切换--end*/

        div.form-group {
        	max-width: 200px;
        }

		/*页面主体内容包裹*/
        /*表格*/
		.g_container_body {
			margin: 5px auto;
			min-width: 1000px;
			width: 100%;
			background-color: #fff;
		}

		.g_container_body_in {
			margin: 8px;
			border-radius: 5px;
			padding: 8px;
		}

		.g_container_body_info {
			height: 35px;
			line-height: 35px;
			color: #00aeef;
			text-indent: 10px;
		}

		.g_container_body_table {
			width: 100%;
            font-size: 15px;
		}

        .reimburse_mail_list_table {
    		min-width: 1000px;
    		width: 100%;
    		box-sizing: border-box;
    	    border-collapse: collapse;
    	    /*text-align: center;*/
    		font-size: 15px;
    		box-shadow: -3px 3px 10px 2px rgba(195,206,220,0.7), 3px 3px 10px 2px rgba(195,206,220,0.7);
        }

        .reimburse_mail_list_table tbody tr:nth-child(odd) {
        	height: 45px;
        	background: #fff;
        }

        .reimburse_mail_list_table tbody tr:nth-child(even) {
        	height: 45px;
        	background: rgba(195,206,220,1);
        }

        .reimburse_mail_list_table thead tr {
        	background: #b5b5b5;
        	height: 45px;
        }

		.reimburse_mail_list_table td:not(:first-child) {
			border-left: 1px solid #999;
		}

		.reimburse_mail_list_table td:not(:first-child):not(:last-child) {
			border-right: 1px solid #999;
		}

		.reimburse_mail_list_table th, .reimburse_mail_list_table td {
			text-align: center;
		}
		
        /*翻页*/
        .m_page {
        	margin-top: 10px;
        	/*display: table-cell;*/
        	width: 100%;
        	height: 32px;
        	vertical-align: middle;
        	text-align: right;
        	padding-top: 1px;
        	padding-right: 20px;
        }

        .pageInfo, .changePage {
        	display: inline-block;
        }

        .pageInfo {
            margin-right: 10px;
        }

	</style>
</head>
<body>
	<div id="transports_wrapper">
	    <div id="transports_sticker">
	        <div id="transports_sticker-con">
				<%@include file="top.jsp"%>
				<div class="container-fluid">

					<div class="g_container">
						<!-- 上方切换与搜索框 -->
						<div class="g_container_info">
						    <div class="g_container_info_l">
						        <ul>
						            <li class="current_tab" data-classify="reimburse_mail_list"><span>报销邮件通知列表</span></li>
						        </ul>
						    </div>
						    <div class="g_container_info_r" style="display: none;">
						        <form class="form-inline">
						            <div class="form-group">
						                <label class="sr-only" for="exampleInputAmount">年</label>
						                <div class="input-group">
						                    <div class="input-group-addon">年</div>
						                    <select id="year_select" class="form-control">
						                    </select>
						                </div>
						            </div>
						            <div class="form-group">
						                <label class="sr-only" for="exampleInputAmount">月</label>
						                <div class="input-group">
						                    <div class="input-group-addon">月</div>
						                    <select id="month_select" class="form-control">
						                    </select>
						                </div>
						            </div>
						            <button type="button" class="btn btn-info" id="reimburse_mail_freshen">刷新</button>
						        </form>
						    </div>
						</div>

						<!-- 主体 -->
						<div class="g_container_body">
							<div class="g_container_body_in">
								<div class="g_container_body_info">
									报销邮件通知列表
								</div>
								<div class="g_container_body_table">
									<table class="reimburse_mail_list_table">
										<thead>
											<tr>
												<th>序号</th>
												<th>月份</th>
												<th>发布时间</th>
												<th>状态</th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</div>
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
						</div><!-- 翻页结束 -->
					</div>
				</div>
	        <!-- transports_sticker-con结束 -->
	        </div>
	    <!-- transports_sticker结束 -->
	    </div>

	    <!-- transports_footer -->
	    <div id="transports_footer">
	        <div id="eoulu-copy-out" style="height:40px;width:calc(100% - 2px);">
	            <div style="width:100%;height:5px;"></div>
	            <div id="eoulu-copy" style="width:100%;height:35px;font-size:12px;color:#888;line-height:35px;z-index: 2;">
	                <hr style="height:1px;color:#999;width: calc(100% - 3px);" />
	                <div style="width:100%;text-align:center;display:inline-block;">Copyright  ©&nbsp;<span class="YEAR">2018</span>&nbsp<a href="http://www.eoulu.com/h-col-268.html" class="EHref" target="_blank" style="color:blue;">Eoulu</a> Tech. Co.,Ltd.</div>
	            </div>
	        </div>
	    </div>

	<!-- transports_wrapper结束 -->
	</div>
	<script>
		var ReimburseMailListState = new Object();
		ReimburseMailListState.hasSearch = false;
		// 翻页组件按钮逻辑
		function pageStyle(currentPage,pageCounts){
		    if(pageCounts == 1){
		        $("#fistPage, #upPage, #nextPage, #lastPage, #Gotojump").prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
		    }else if(currentPage == 1){
		        $("#fistPage, #upPage").prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
		        $("#lastPage, #nextPage, #Gotojump").prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
		    }else if(currentPage == pageCounts){
		        $("#lastPage, #nextPage").prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
		        $("#fistPage, #upPage, #Gotojump").prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
		    }else{
		        $("#lastPage, #nextPage, #fistPage, #upPage, #Gotojump").prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
		    }
		}

		// 获取数据
		function getPageData(Year, Month, CurrentPage){
			$.ajax({
				type: "GET",
				url: "ReimburseMailList",
				data: {
					LoadType: "data",
					Year: Year,
					Month: Month,
					CurrentPage: CurrentPage
				},
				dataType: "json"
			}).then(function(res){
				var data = res.datas;
				var pageCounts = res.pageCount;
				renderPageData(data, CurrentPage, pageCounts);
				if(!Year && !Month){
					ReimburseMailListState.hasSearch = false;
				}else{
					ReimburseMailListState.hasSearch = true;
				}
			},function(){
				$.MsgBox_Unload.Alert("提示","服务器繁忙！");
			});
		}

		// 渲染页面数据
		function renderPageData(data, CurrentPage, pageCounts){
			var str = '';
			if(data.length == 1){
				str+='<tr><td colspan="4">无数据......</td></tr>';
			}else if(data.length > 1){
				data.map(function(currentValue, index, arr){
					if(index > 0){
						str+='<tr>'+
							'<td class="update_td" data-iid="'+currentValue.ID+'">'+parseInt((CurrentPage-1)*10 + index)+'</td>'+
							'<td class="hastd_Month" title="'+currentValue.Month+'">'+currentValue.Month+'</td>'+
							'<td class="hastd_PublishDate" title="'+currentValue.PublishDate+'">'+currentValue.PublishDate+'</td>'+
							'<td class="hastd_State" title="'+currentValue.State+'">'+currentValue.State+'</td>'+
						'</tr>';
					}
				});
			}
			$(".reimburse_mail_list_table tbody").empty().append(str);
			$(".m_page #currentPage").text(CurrentPage);
			$(".m_page #allPage").text(pageCounts);
			pageStyle(CurrentPage, pageCounts);
		}

		// 翻页获取数据
		function getDataByGoPage(currentPage){
			var Year;
			var Month;
			if(!ReimburseMailListState.hasSearch){
				Year = undefined;
				Month = undefined;
			}else{
				Year = $(".m_button_r_in_l div.btn-group>button:nth-child(1)").attr("title");
				Month = $(".m_button_r_in_m div.btn-group>button:nth-child(1)").attr("title");
			}
			getPageData(Year, Month, currentPage);
		}


		$(function(){
			getPageData(undefined, undefined, 1);
		});

		// 翻页功能
		$("#jumpNumber").on("input propertychange",function(){
		    var newVal = $(this).val().replace(/[^\d]/g,'');
		    $(this).val(newVal);
		});

			// 翻页
		$("#fistPage").click(function(){
		    var currentPage =1;
		    getDataByGoPage(currentPage);
		});

		$("#lastPage").click(function(){
		    var currentPage =Number($("#allPage").text());
		    getDataByGoPage(currentPage);
		});

		$("#upPage").click(function(){
		    var currentPage = Number($("#currentPage").text());
		    if(currentPage == 1){
		        return;
		    }else{
		        currentPage--;
		        getDataByGoPage(currentPage);
		    }
		});

		$("#nextPage").click(function(){
		    var currentPage = Number($("#currentPage").text());
		    var pageCounts = Number($("#allPage").text());
		    if(currentPage == pageCounts){
		        return;
		    }else{
		        currentPage++;
		        getDataByGoPage(currentPage);
		    }
		});
			//跳页
		$("#Gotojump").click(function(){
		    var currentPage = $("#jumpNumber").val().trim();
		    var pageCounts = Number($("#allPage").text());
		    var oldCurrentPage = Number($("#currentPage").text());
		    if(currentPage == oldCurrentPage || currentPage <= 0 || currentPage>pageCounts){
		        $("#jumpNumber").val('');
		        return;
		    }else{
		        getDataByGoPage(currentPage);
		    }
		});
	</script>
</body>
</html>