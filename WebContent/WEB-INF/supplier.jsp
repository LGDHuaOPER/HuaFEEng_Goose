<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>供应商管理</title>
    <link rel="shortcut icon" href="image/eoulu.ico"/>
	<link rel="bookmark" href="image/eoulu.ico"/>
    <link rel="stylesheet" type="text/css" href="css/info_supply.css">
    <link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css"/>
    <link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">
    <style>
        .clear_float {
            height: 2px;
            width: calc(100% - 2px);
            clear:both;
        }

        .content {
            padding-bottom: 5px !important;
        }

        .content .choose {
            margin-right: 5%;
        }

        /*布局*/
        html,body{
            width:100%;
            height:100%;
        }

        #originfactory_wrapper {
            position:fixed;
            overflow:auto;
            width:100%;
            height:100%;
            box-sizing:border-box;
        }

        #originfactory_sticker {
            width:100%;
            min-height:100%;
            box-sizing:border-box;
        }

        #originfactory_sticker-con {
            padding-bottom:40px;
            box-sizing:border-box;
        }

        #originfactory_footer {
            margin-top:-40px;
        }

        .info_add input, .info_update input {
            width: 240px;
            font-size: 14px;
        }

        .down_span {
            position: absolute;
            display: inline-block;
            width: 0;
            height: 0;
            border-left: 5px solid transparent;
            border-right: 5px solid transparent;
            border-top: 10px solid #00aeef;
            transform: translate(-15px,10px);
            -ms-transform: translate(-15px,10px);
            -webkit-transform: translate(-15px,10px);
        }

        #selectCon {
            position: absolute;
            right: 320px;
            top: 35px;
        }

        #selectCon option {
            padding-top: 5px;
            padding-bottom: 5px;
            padding-left: 5px;
        }
    
        .search_Column {
            width: 100px;
            height: 30px;
            line-height: 30px;
        }

        .search_Content {
            width: 200px;
            height: 30px;
            line-height: 30px;
        }
    </style>
</head>
<body>
    <div id="originfactory_wrapper">
        <div id="originfactory_sticker">
            <div id="originfactory_sticker-con">
                <%@include file="top.jsp"%>
                <div class="contain">
                    <div class="content">
                       <!-- 	=======================导航栏   开始 ================================== -->

                       <%@include file="nav.jsp"%>
                			
                <!-- 	=======================导航栏   结束 ================================== -->
                        <div style="display: none">
                            <input type="text" value="" name="query_type">
                            <input type="text" value="" name="classify1">
                            <input type="text" value="" name="classify2">
                            <input type="text" value="" name="parameter1">
                            <input type="text" value="" name="parameter2">
                        </div>
                        <form id="top_text_from" name="top_text_from" method="post" action="">
                            <!--<div class="select-content">-->
                                <!--<div class="select" >-->
                                        <!--供应商-->
                                    <!--<select  id="searchContent1"  name="searchContent1">-->
                                        <!--<option value="">1</option>-->
                                        <!--<option value="100">2</option>-->
                                        <!--<option value="">3</option>-->
                                    <!--</select>-->
                                <!--</div>-->
                                <!--<div class="select-button">-->
                                    <!--<input type="button" value="搜索" class="bToggle" onclick="Search()">-->
                                    <!--<input type="button" value="取消" class="bToggle" onclick="Cancel()">-->
                                <!--</div>-->
                            <!--</div>-->
                            <div class="choose" style="position: relative;">
                                <input type="button" value="添加" class="bToggle" onclick="AddContract()" style="margin: 0px 0 10px 0;">
                                <div class="search_div">
                                    <input type="text" placeholder="请选择" readonly="readonly" class="search_Column"><span class="down_span"></span>
                                    <select multiple style="width:100px;height:120px;display:none" id="selectCon">
                                        <option value="供应商名称">供应商名称</option>
                                        <option value="联系人">联系人</option>
                                        <option value="产品">产品</option>
                                    </select>
                                    <input type="text" class="search_Content">
                                    <input type="button" value="搜索" class="bToggle search_submit" style="margin: 0px 0 10px 0;">
                                    <input type="button" value="取消" class="bToggle search_cancle" style="margin: 0px 0 10px 0;">
                                </div>
                            </div>
                        </form>
                        <table border="1" cellspacing="0" cellspadding="0" id="table1">
                            <thead>
                            <tr>
                                <td>序号</td>
                                <td>供应商名称</td>
                                <td>联系人</td>
                                <td>联系方式</td>
                                <td>联系地址</td>
                                <td>电子邮箱</td>
                                <td>银行信息</td>
                                <td>产品</td>
                                <td style="display: none;">开户名称</td>
                                <td style="display: none;">账号</td>
                                <td style="display: none;">开户行</td>
                                <td style="display: none;">税号</td>
                                <td style="display: none;">SWIFT Code</td>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${suppliers}" var="supplier" varStatus="status">
                            <c:if test="${status.index>0}">
                             <tr>
                                <td class="edit" value="${supplier.ID }">${(currentPage-1)*10+status.index}</td>
                                <td class="hastd_name">${supplier.Name}</td>
                                <td class="hastd_Contact">${supplier.Contact}</td>
                                <td class="hastd_ContactInfo">${supplier.ContactInfo}</td>
                                <td class="hastd_Address">${supplier.Address}</td>
                                <td class="hastd_Email">${supplier.Email}</td>
                                <td class="showBankInfo"><i class="fa fa-eye"></i></td>
                                <td class="hastd_Product">${supplier.Product}</td>
                                <td class="hastd_company" style="display: none;">${supplier.Company}</td>
                                <td class="hastd_account" style="display: none;">${supplier.Account}</td>
                                <td class="hastd_bank" style="display: none;">${supplier.Bank}</td>
                                <td class="hastd_taxCode" style="display: none;">${supplier.TaxCode}</td>
                                <td class="hastd_SWIFTCode" style="display: none;">${supplier.SWIFTCode}</td>
                            </tr>
                            </c:if>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div class="cover-color"></div>
                        <div class="info_add">
                            <div class="title">添加信息</div>
                            <div class="infoAdd_close">关闭</div>
                            <div class="basic_info">
                                <table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
                                    <tbody>
                                    <!-- <input type="hidden" name="id"> -->
                                    <tr>
                                        <td>
                                            供应商名称
                                        </td>
                                        <td>
                                            <input type="text" class="info_name">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            联系人
                                        </td>
                                        <td>
                                            <input type="text" class="info_Contact">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            联系方式
                                        </td>
                                        <td>
                                            <input type="text" class="info_ContactInfo">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            联系地址
                                        </td>
                                        <td>
                                            <input type="text" class="info_Address">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            电子邮箱
                                        </td>
                                        <td>
                                            <input type="text" class="info_Email">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            产品
                                        </td>
                                        <td>
                                            <input type="text" class="info_Product">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            银行信息：
                                        </td>
                                        <td>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            开户名称
                                        </td>
                                        <td>
                                            <input type="text" class="info_company">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            开户行
                                        </td>
                                        <td>
                                            <input type="text" class="info_bank">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            账号
                                        </td>
                                        <td>
                                            <input type="text" class="info_account">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            税号
                                        </td>
                                        <td>
                                            <input type="text" class="info_taxCode">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            SWIFT Code
                                        </td>
                                        <td>
                                            <input type="text" class="info_SWIFTCode">
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="edit_btn">
                                <input type="button" value="提交" class="bToggle" id="add_submit">
                                <input type="button" value="取消" class="bToggle" id="add_cancel">
                            </div>
                        </div>
                        <div class="info_update">
                            <div class="title">
                                修改信息
                            </div>
                            <div class="update_close">关闭</div>
                            <div class="basic_info">
                                <table border="1" cellspacing="0" cellpadding="0" class="contract_basic">
                                    <tbody>
                                    <!-- <input type="hidden" name="id"> -->
                                    <tr>
                                        <td>
                                            供应商名称
                                        </td>
                                        <td>
                                            <input type="text" class="info_name">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            联系人
                                        </td>
                                        <td>
                                            <input type="text" class="info_Contact">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            联系方式
                                        </td>
                                        <td>
                                            <input type="text" class="info_ContactInfo">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            联系地址
                                        </td>
                                        <td>
                                            <input type="text" class="info_Address">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            电子邮箱
                                        </td>
                                        <td>
                                            <input type="text" class="info_Email">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            产品
                                        </td>
                                        <td>
                                            <input type="text" class="info_Product">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            银行信息：
                                        </td>
                                        <td>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            开户名称
                                        </td>
                                        <td>
                                            <input type="text" class="info_company">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            开户行
                                        </td>
                                        <td>
                                            <input type="text" class="info_bank">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            账号
                                        </td>
                                        <td>
                                            <input type="text" class="info_account">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            税号
                                        </td>
                                        <td>
                                            <input type="text" class="info_taxCode">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            SWIFT Code
                                        </td>
                                        <td>
                                            <input type="text" class="info_SWIFTCode">
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="edit_btn">
                                <input type="button" value="提交" class="bToggle" id="update_submit">
                                <input type="button" value="取消" class="bToggle" id="update_cancel">
                            </div>
                        </div>
                        
                        <c:set var="queryUrl" value="Supplier?currentPage="></c:set>
                        
                       <div id="page">
            				<div class="pageInfo">
            					当前是第&nbsp;<span id="currentPage">${currentPage}</span>&nbsp;页,&nbsp;总计&nbsp;<span
            						id="allPage">${pageCounts}</span>页
            				</div>
            				<div class="changePage">
            					<input type="button" class="bToggle" value="首页" id="fistPage"
            						name="fistPage" onclick="FistPage('${queryUrl}')"> <input
            						type="button" class="bToggle" value="上一页" id="upPage"
            						onclick="UpPage('${queryUrl}${currentPage-1 }')"> <input
            						type="button" class="bToggle" value="下一页" id="nextPage"
            						onclick="NextPage('${queryUrl}${currentPage+1 }')"> 跳到第 <input
            						type="text" id="jumpNumber" name="jumpNumber" class="jumpNumber"
            						style="width: 30px; color: #000"
            						onkeyup="value=value.replace(/[^\d]/g,'') "> 页 <input
            						type="button" class="bToggle" value="GO" id="Gotojump"
            						name="Gotojump" onclick="PageJump('${queryUrl}')"> <input
            						type="button" class="bToggle" value="尾页" id="lastPage"
            						name="lastPage" onclick="LastPage('${queryUrl}')">
            				</div>
            			</div>
                        <div class="clear_float"></div>
                    </div>
                </div>
                <div class="cover_bankinfo" style="display: none;"></div>
                <div class="bankinfo_div" style="display: none;">
                    <div class="bankinfo_div_top">
                        <div class="bankinfo_div_top_l"></div>
                        <div class="bankinfo_div_top_r">关闭</div>
                    </div>
                    <div class="bankinfo_div_body">
                        <table>
                            <tbody>
                                <tr>
                                    <td>开户名称</td>
                                    <td class="table_company"></td>
                                </tr>
                                <tr>
                                    <td>账号</td>
                                    <td class="table_account"></td>
                                </tr>
                                <tr>
                                    <td>开户行</td>
                                    <td class="table_bank"></td>
                                </tr>
                                <tr>
                                    <td>税号</td>
                                    <td class="table_taxCode"></td>
                                </tr>
                                <tr>
                                    <td>SWIFTCode</td>
                                    <td class="table_SWIFTCode"></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            <!-- originfactory_sticker-con结束 -->
            </div>
        <!-- originfactory_sticker结束 -->
        </div>
        <!-- originfactory_footer -->
        <div id="originfactory_footer">
            <div id="eoulu-copy-out" style="height:40px;width:calc(100% - 2px);">
                <div style="width:100%;height:5px;"></div>
                <div id="eoulu-copy" style="width:100%;height:35px;font-size:12px;color:#888;line-height:35px;z-index: 2;">
                    <hr style="height:1px;color:#999;width: calc(100% - 3px);" />
                    <div style="width:100%;text-align:center;display:inline-block;">Copyright  ©&nbsp;<span class="YEAR">2018</span>&nbsp<a href="http://www.eoulu.com/h-col-268.html" class="EHref" target="_blank" style="color:blue;">Eoulu</a> Tech. Co.,Ltd.</div>
                </div>
            </div>
        </div>
    <!-- originfactor_wrapper结束  -->
    </div>
</body>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/swiper-3.4.1.jquery.min.js" type="text/javascript" charset="utf-8"></script>
<!-- <script src="js/ajaxfileupload.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/msgbox.js"></script>
<!-- <script src="js/msgbox_unload.js"></script> -->
<!-- <script src="js/global/myFunction.js"></script> -->
<script src="js/info_supply.js"></script>
<script type="text/javascript">
	function FistPage(arg) {
		window.location.href = arg + "1";
	}
	function UpPage(arg) {
		window.location.href = arg;
	}
	function NextPage(arg) {
		window.location.href = arg;
	}
	function PageJump(arg) {
		var jumpNumber = document.getElementById("jumpNumber").value;
		if (jumpNumber == null || jumpNumber == 0) {
			jumpNumber = $('#currentPage').html();
		} else if (jumpNumber > parseInt($('#allPage').html())) {
			jumpNumber = $('#allPage').html();
		}
		window.location.href = arg + jumpNumber;
	}
	function LastPage(arg) {
		var jumpNumber = parseInt($('#allPage').html());
		window.location.href = arg + jumpNumber;
	}
	$(function() {
        // globalCopyPos10();
        // $(window).on("resize",globalCopyPos10);
		if ($('#currentPage').html() == 1) {
			$('#fistPage').attr('disabled', 'true');
			$('#fistPage').removeClass('bToggle');
			$('#upPage').attr('disabled', 'true');
			$('#upPage').removeClass('bToggle');
		}
		if ($('#allPage').html() == $('#currentPage').html()) {
			$('#lastPage').attr('disabled', 'true');
			$('#lastPage').removeClass('bToggle');
			$('#nextPage').attr('disabled', 'true');
			$('#nextPage').removeClass('bToggle');
		}

        if(window.location.href.indexOf("Column1=")>-1){
            var hrefnew = window.location.href.split("?Column1=")[1];
            var Column1 = decodeURI(hrefnew.split("&")[0]);
            var Content1 = decodeURI(hrefnew.split("&Content1=")[1]);
            $(".search_Column").val(Column1);
            $(".search_Content").val(Content1);
        }
	});
</script>
</html>