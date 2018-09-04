<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<!-- 为移动设备添加 viewport -->
	<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
	<title>管理员页面</title>
	<link rel="shortcut icon" href="image/eoulu.ico" />
	<link rel="bookmark" href="image/eoulu.ico" />
	<!-- <title>用户管理员界面</title> -->

	<link rel="stylesheet" type="text/css" href="./css/libs/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="./css/global/reset.css">
	<link rel="stylesheet" type="text/css" href="./css/libs/animate.min.css">
	<link rel="stylesheet" type="text/css" href="./css/global/responseFont.css">
	<link rel="stylesheet" type="text/css" href="css/admin.css">
	<link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">

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

		hr {
			margin-top: 1px;
			margin-bottom: 1px;
		}
	</style>
	<!--<script src="js/jquery-1.12.3.min.js" defer></script>-->
	<script src="./js/libs/jquery-3.3.1.min.js" defer></script>
	<script src="plugins/cookie/jquery.cookie.js" defer></script>
	<script src="js/msgbox.js" defer></script>
	<script src="./js/libs/bootstrap.min.js" defer></script>
	<script src="js/admin.js" defer></script>
	
</head>
<body>
<div id="NonStandard_wrapper">
    <div id="NonStandard_sticker">
        <div id="NonStandard_sticker-con">
	<%@include file="top.jsp"%>
	<input type="hidden" class="model" value="${model}">
	<input type="hidden" class="isOpen" value="${sign}">
	<input type="hidden" class="message" value="${message}">


	<div class="container-fluid admin-g">
	<div class="container-fluid admin-g-top">
	<div class="m-pos">
	<div class="text">
	位置：<span class="u-pos1">管理员</span><span class="u-pos2">&gt;&nbsp;&nbsp;<i></i></span>
	<!--<span class="u-pos3">&gt;&nbsp;&nbsp;操作日志</span>-->
	</div>
	</div>
	<!--管理员主界面显示的信息-->
	<div class="m-info m-info0">
	<div class="text">
	<span>Admin</span>您好，欢迎使用管理员操作
	</div>
	</div>
	<!--用户管理页面显示的info-->
	<div class="m-info m-info1">
	    <div class="m-inner-info">
	            <div class="u-search">
	                    <span class="u-search-icon"><img src="./image/admin/u-member-search.png" alt="" width="22" height="20"></span>
	                    <div class="u-search-body">
	                    <select name="search-member" id="">
	                            <option value="UserName">用户名</option>
	                            <option value="Phone">手机</option>
	                            <option value="Email">邮箱</option>
	                    </select>
	                    <input type="text" class="form-control">
                        <button type="button" class="btn btn-primary btn-custom">搜索</button>
	                    </div>
                </div>
	            <div class="u-member-add">
	                    <button type="button" class="btn btn-primary btn-custom"><img src="./image/admin/add.png" alt="" width="13" height="13">添加人员</button>
                </div>
	    </div>
	</div>
	<!--操作日志页面显示的info-->
	<div class="m-info m-info2">
		<div class="u-select"><input type="checkbox"></div>
		<div class="u-export"><button class="btn btn-primary">导出</button></div>
		<div class="u-pagevisit"><button class="btn btn-primary">页面访问统计</button></div>
	</div>
	</div>
	<div class="container-fluid admin-g-body">
	<div class="m-flex">
	<div class="m-flex-l">
	<div class="u-unit">
	<div class="u-su">
	<div class="u-text"><span>您可以快速进行成员管理</span></div>
	<div class="u-img"><img src="./image/admin/oper.png" alt="">
	<div class="u-mess">
	<div class="u-mess-text">成员管理</div>
	</div>
	</div>
	</div>
	</div>
	</div>
	<div class="m-flex-r">
	<div class="u-unit">
	<div class="u-su">
	<div class="u-text"><span>您可以快速进行操作日志的查询</span></div>
	<div class="u-img"><img src="./image/admin/mana.png" alt="">
	<div class="u-mess">
	<div class="u-mess-text">操作日志</div>
	</div>
	</div>
	</div>
	</div>
	</div>
	</div>
	</div>
	<!--用户管理-->
	<div class="container-fluid admin-g-body1">
	    <div class="m-member">
	    <form action="AdminUser" method="get" id="userFormDel">
	    	<input type="hidden" name="classify" value="删除">
			<input type="hidden" name="id" value="">
	    </form>
	    <form action="AdminUser" method="get" id="userFormDelAll">
	    	<input type="hidden" name="classify" value="批量删除">
	    </form>
		<form action="Authority" method="get" id="userAuthority">
			<input type="hidden" name="model" value="用户管理">
			<input type="hidden" name="UserName" value="">
		</form>
	    
	            <div class="u-member-table">
	<table border="1" cellspacing="0" cellpadding="0">
	<thead><tr>
	<th>选取</th>
	<th>序号</th>
	<th>用户名</th>
	<th>性别</th>
	<th>联系电话</th>
	<th>电子邮箱</th>
	<th>角色</th>
	<th>创建日期</th>
	<th>上次登录</th>
	<th>操作</th></tr></thead>
	<tbody>
	<%--<tr>--%>
	<%--<td><input type="checkbox">--%>
        <%--<input type="hidden" name="id" value="1">--%>
        <%--</td>--%>
	<%--<td>21</td>--%>
	<%--<td class="usernameval">31</td>--%>
	<%--<td class="sexval">男</td>--%>
	<%--<td class="telval">51</td>--%>
	<%--<td class="emailval">61</td>--%>
	<%--<td class="roleval">超级管理员</td>--%>
	<%--<td>81</td>--%>
	<%--<td>91</td>--%>
	<%--<td><div class="u-oper"><span class="u-oper-1"><i></i>授权</span><span class="u-oper-2"><i></i>修改</span><span class="u-oper-3"><i></i>删除</span></div></td>--%>
	<%--</tr>--%>
	<%--<tr>--%>
	<%--<td><input type="checkbox">--%>
        <%--<input type="hidden" name="id" value="46">--%>
        <%--</td>--%>
	<%--<td>1</td>--%>
	<%--<td class="usernameval">张三&#64;Eoulu.com</td>--%>
	<%--<td class="sexval">女</td>--%>
	<%--<td class="telval">123456789</td>--%>
	<%--<td class="emailval">张三&#64;Eoulu.com</td>--%>
	<%--<td class="roleval">管理员</td>--%>
	<%--<td>2018-03-02</td>--%>
	<%--<td>2018-03-02</td>--%>
	<%--<td>--%>
	<%--&lt;%&ndash;<input type="hidden" value="${userRole.Role}">&ndash;%&gt;--%>
	<%--<input type="hidden" value="">--%>
	<%--<div class="u-oper"><span class="u-oper-1"><i></i>授权</span><span--%>
	<%--class="u-oper-2"><i></i>修改</span><span class="u-oper-3"><i></i>删除</span>--%>
	<%--</div>--%>
	<%--</td>--%>
	<%--</tr>--%>
	</tbody>
	</table>
	            </div>
	            <div class="u-member-foot">
	                    <div class="u-member-allsel"><input type="checkbox"></div>
	                    <div class="u-member-alldel"><button type="button" class="btn btn-primary btn-custom"><img src="./image/admin/add.png" alt="" width="13" height="13">批量删除</button></div>
	                    <!--用户管理分页-->
                        <div id="page">
                                <div class="pageInfo">
                                当前是第&nbsp;<span id="currentPage"></span>&nbsp;页,&nbsp;总计&nbsp;<span
                                id="allPage"></span>页
                                </div>
                                <div class="changePage">
                                <input type="button" class="bToggle" value="首页" id="fistPage" name="fistPage">
                                <input type="button" class="bToggle" value="上一页" id="upPage">
                                <input type="button" class="bToggle" value="下一页" id="nextPage"> 跳到第
                                <input type="text" id="jumpNumber" name="jumpNumber" class="jumpNumber" style="width: 30px; color: #000"> 页
                                <input type="button" class="bToggle" value="GO" id="Gotojump" name="Gotojump">
                                <input type="button" class="bToggle" value="尾页" id="lastPage" name="lastPage">
                                </div>
                        </div>
                </div>
	    </div>
	</div>
	<!--操作日志-->
	<div class="container-fluid admin-g-body2">
		<div class="m-operation">
			<div class="m-operation-table">
				<table cellpadding="0" cellspacing="0">
					<thead>
					<tr>
					<th><input type="checkbox"></th>
					<th>序号</th>
					<th>操作账号</th>
					<th>页面</th>
					<th>操作记录</th>
					<th>日期</th>
					<th>时间</th>
					<th>IP地址</th>
					<th>地理位置</th>
					</tr>
					</thead>
					<tbody>
					<%--<tr>--%>
					<%--<td><input type="checkbox"><input type="hidden" name="idOper" value="100"></td>--%>
					<%--<td>1</td>--%>
					<%--<td>张三@Eoulu.com</td>--%>
					<%--<td>服务部-员工行程</td>--%>
					<%--<td>新增-合同号为上海华为的需求</td>--%>
					<%--<td>2018-03-02</td>--%>
					<%--<td>20:52:52</td>--%>
					<%--<td>49.92.115.104</td>--%>
					<%--<td>苏州</td>--%>
					<%--</tr>--%>
					<%--<tr>--%>
					<%--<td><input type="checkbox"></td>--%>
					<%--<td>2</td>--%>
					<%--<td>lisi@Eoulu.com</td>--%>
					<%--<td>服务部-员工行程</td>--%>
					<%--<td>新增-合同号为深圳华为的需求</td>--%>
					<%--<td>2018-03-02</td>--%>
					<%--<td>20:52:52</td>--%>
					<%--<td>49.92.115.104</td>--%>
					<%--<td>苏州</td>--%>
					<%--</tr>--%>
					<%--<tr>--%>
					<%--<td><input type="checkbox"></td>--%>
					<%--<td>3</td>--%>
					<%--<td>wangwu@Eoulu.com</td>--%>
					<%--<td>服务部-员工行程</td>--%>
					<%--<td>新增-合同号为杭州华为的需求</td>--%>
					<%--<td>2018-03-02</td>--%>
					<%--<td>20:52:52</td>--%>
					<%--<td>49.92.115.104</td>--%>
					<%--<td>杭州</td>--%>
					<%--</tr>--%>
					</tbody>
				</table>
			</div>
			<div class="m-operation-foot">
				<!--操作日志分页-->
				<div id="page1">
					<div class="pageInfo">
					当前是第&nbsp;<span id="currentPage1"></span>&nbsp;页,&nbsp;总计&nbsp;<span id="allPage1"></span>页
					</div>
					<div class="changePage">
						<input type="button" class="bToggle" value="首页" id="fistPage1" name="fistPage1">
						<input type="button" class="bToggle" value="上一页" id="upPage1">
						<input type="button" class="bToggle" value="下一页" id="nextPage1"> 跳到第
						<input type="text" id="jumpNumber1" name="jumpNumber1" class="jumpNumber" style="width: 30px; color: #000"> 页
						<input type="button" class="bToggle" value="GO" id="Gotojump1" name="Gotojump1">
						<input type="button" class="bToggle" value="尾页" id="lastPage1" name="lastPage1">
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="cover-color"></div>
	<!--授权管理页面-->
	<div class="m-authorize">
	<div class="m-authorize-top">
	<div class="u-left">授权管理-<span class="current-mem"></span></div>
	<div class="u-right">关闭</div>
	</div>
		<%--<form action="AuthorityOperate" method="get" id="useAuthorityOperate">--%>
			<%--<input type="hidden" name="UserName" value="">--%>
			<%--<input type="hidden" name="Authority" value="">--%>
	<div class="m-authorize-body">
	<table cellpadding="0" cellspacing="0">
	<tbody>

	<%--<tr>--%>
	<%--<td><label><input type="checkbox" class="department">物流页面</label></td>--%>
	<%--<td><label><input type="checkbox" class="u-depart" value="2">物流页面</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="50">库存采购</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="10">修改审核信息1</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="11">修改审核信息2</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="12">导出订单</label>--%>
	<%--</td>--%>
	<%--</tr>--%>
	<%--<tr>--%>
	<%--<td><label><input type="checkbox" class="department">合同统计</label></td>--%>
	<%--<td>--%>
	<%--<label><input type="checkbox" class="u-depart" value="4">合同统计</label>--%>
	<%--</td>--%>
	<%--</tr>--%>
	<%--<tr>--%>
	<%--<td><label><input type="checkbox" class="department">资料库</label></td>--%>
	<%--<td>--%>
	<%--<label><input type="checkbox" class="u-depart" value="5">产品型号表</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="6">客户信息表</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="8">供应商管理</label>--%>
	<%--</td>--%>
	<%--</tr>--%>
	<%--<tr>--%>
	<%--<td><label><input type="checkbox" class="department">需求录入</label></td>--%>
	<%--<td>--%>
	<%--<label><input type="checkbox" class="u-depart" value="3">需求录入</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="39">报备</label>--%>
	<%--</td>--%>
	<%--</tr>--%>
	<%--<tr>--%>
	<%--<td><label><input type="checkbox" class="department">库存页面</label></td>--%>
	<%--<td>--%>
	<%--<label><input type="checkbox" class="u-depart" value="9">库存页面</label>--%>
	<%--</td>--%>
	<%--</tr>--%>
	<%--<tr>--%>
	<%--<td><label><input type="checkbox" class="department">报价系统</label></td>--%>
	<%--<td>--%>
	<%--<label><input type="checkbox" class="u-depart" value="32">报价系统</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="34">商品管理</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="33">显示成本</label>--%>
	<%--</td>--%>
	<%--</tr>--%>
	<%--<tr>--%>
	<%--<td><label><input type="checkbox" class="department">发票页面</label></td>--%>
	<%--<td>--%>
	<%--<label><input type="checkbox" class="u-depart" value="15">发票页面</label>--%>
	<%--</td>--%>
	<%--</tr>--%>
	<%--<tr>--%>
	<%--<td><label><input type="checkbox" class="department">箱单页面</label></td>--%>
	<%--<td>--%>
	<%--<label><input type="checkbox" class="u-depart" value="16">箱单页面</label>--%>
	<%--</td>--%>
	<%--</tr>--%>
	<%--<tr>--%>
	<%--<td><label><input type="checkbox" class="department">商务部</label></td>--%>
	<%--<td>--%>
	<%--<label><input type="checkbox" class="u-depart" value="17">质量证明</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="18">数量重量</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="19">测试报告</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="20">熏蒸证明</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="21">原产地证明</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="22">验收报告</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="23">发货通知</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="24">FAT</label>--%>
	<%--</td>--%>
	<%--</tr>--%>
	<%--<tr>--%>
	<%--<td><label><input type="checkbox" class="department">物流部</label></td>--%>
	<%--<td>--%>
	<%--<label><input type="checkbox" class="u-depart" value="25">进出口运输指令</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="26">运输投保</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="38">国内运输指令</label>--%>
	<%--</td>--%>
	<%--</tr>--%>
	<%--<tr>--%>
	<%--<td><label><input type="checkbox" class="department">服务部</label></td>--%>
	<%--<td>--%>
	<%--<label><input type="checkbox" class="u-depart" value="27">机台统计</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="28">装机进展</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="29">售后维修</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="36">员工行程</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="37">例行拜访</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="42">拜访计划</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="51">删除行程</label>--%>
	<%--</td>--%>
	<%--</tr>--%>
	<%--<tr>--%>
	<%--<td><label><input type="checkbox" class="department">人事部</label></td>--%>
	<%--<td>--%>
	<%--<label><input type="checkbox" class="u-depart" value="46">员工信息</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="47">请假申请</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="48">所有请假记录</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="49">请假审批</label>--%>
	<%--</td>--%>
	<%--</tr>--%>
	<%--<tr>--%>
	<%--<td><label><input type="checkbox" class="department">软件部</label></td>--%>
	<%--<td>--%>
	<%--<label><input type="checkbox" class="u-depart" value="43">软件部文档</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="52">开发项目管理</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="53">软件产品管理</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="55">客户询价记录</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="54">软件产品管理操作</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="56">询价记录操作</label>--%>
	<%--</td>--%>
	<%--</tr>--%>
	<%--<tr>--%>
	<%--<td><label><input type="checkbox" class="department">销售统计</label></td>--%>
	<%--<td>--%>
	<%--<label><input type="checkbox" class="u-depart" value="30">销售统计</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="41">热销产品</label>--%>
	<%--</td>--%>
	<%--</tr>--%>
	<%--<tr>--%>
	<%--<td><label><input type="checkbox" class="department">文档上传</label></td>--%>
	<%--<td>--%>
	<%--<label><input type="checkbox" class="u-depart" value="31">文档上传</label>--%>
	<%--<label><input type="checkbox" class="u-depart" value="44">其他文档</label>--%>
	<%--</td>--%>
	<%--</tr>--%>
	<%--<tr>--%>
	<%--<td><label><input type="checkbox" class="department">FORMFACTOR</label></td>--%>
	<%--<td>--%>
	<%--<label><input type="checkbox" class="u-depart" value="35">FORMFACTOR</label>--%>
	<%--</td>--%>
	<%--</tr>--%>
	</tbody>
	</table>
	</div>
	<div class="m-authorize-foot">
		<div class="u-selectall"><label><input type="checkbox" class="selectall">选择所有</label></div>
		<div class="u-button">
			<button type="button" class="btn btn-primary btn-custom">提交</button>
			<button type="button" class="btn btn-primary btn-custom">取消</button>
		</div>
	</div>
			<%--</form>--%>
	</div>
	<!--添加修改成员-->
	<div class="m-add_member">
		<div class="m-add_member-top">
	<div class="u-left"></div>
	<div class="u-right">关闭</div>
	</div>
	<form action="AdminUser" method="get" id="userForm">
		<input type="hidden" name="classify" value="新增">
		<input type="hidden" name="id" value="">
		<div class="m-add_member-body">
	<div class="m-add_member-body-l">
	<div class="m-add_member-body-text">用户名</div>
	<div class="m-add_member-body-text">性别</div>
	<div class="m-add_member-body-text">联系电话</div>
	<div class="m-add_member-body-text">电子邮箱</div>
	<div class="m-add_member-body-text">角色</div>
	</div>
	<div class="m-add_member-body-r">
	<div class="m-add_member-body-info"><input type="text" name="user_name" required="required" placeholder="请输入用户名"></div>
	<div class="m-add_member-body-info">
	<select name="sex">
	<option value="男">男</option>
	<option value="女">女</option>
	</select>
	</div>
	<div class="m-add_member-body-info"><input type="text" name="phone" placeholder="请输入手机号"></div>
	<div class="m-add_member-body-info"><input type="email" name="email" placeholder="请输入邮箱"></div>
	<div class="m-add_member-body-info">
	<select name="Role">
	<option value="成员">成员</option>
	<option value="管理员">管理员</option>
	<option value="超级管理员">超级管理员</option>
	</select>
	</div>
	</div>
	</div>

		<div class="m-add_member-foot">
		<input type="submit" value="提交">
		<button type="button" class="btn btn-primary btn-custom">取消</button>
	</div>
	</form>
	</div>
	<!-- <div id="eoulu-copy" style="position:absolute;bottom:0;width:calc(100% - 30px);height:33px;font-size:12px;color:#888;line-height:35px;z-index: 2;"><div style="width:100%;text-align:center;display:inline-block;">Copyright  ©&nbsp;<span class="YEAR">2018</span>&nbsp<a href="http://www.eoulu.com/h-col-268.html" class="EHref" target="_blank" style="color:blue;">Eoulu</a> Tech. Co.,Ltd.</div></div>
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
	<script>
	//  添加修改人员提交validate
	// function modifyValidate(){
	// alert("验证");
	// 	var email = $(".m-add_member-body-info input[type='email']").val().trim();
	// 	var phone = $(".m-add_member-body-info input[name='phone']").val().trim();
	// 	var phoneReg = /^0*(13|15|16|17|18|19)\d{9}$/;
	// 	if(!phoneReg.test(phone)){
	// 		$.MsgBox.Alert("提示","手机号输入格式有误!");
	// 		return false;
	// 	}else if(email == null || email == ""){
	// 		$.MsgBox.Alert("提示","请输入邮箱！");
	// 		return false;
	// 	}else{
	// 		return true;
	// 	}
	// }
	</script>

</body>
</html>
