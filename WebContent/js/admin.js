/* created by LGDHuaFEEng at 2018/03/27 */
$(function () {
	// console.log("test");
    if($("div.m-info0 div.text").length){
        var showCurUserName = $("div.eou-container").siblings("span#userName").text();
        $("div.m-info0 div.text span").text(showCurUserName);
    }
//	alert(window.location.href);
	function showMember() {
        return $.ajax({
            type: 'get',
            url: 'User',
            data: {
                QueryType: "",
                CurrentPage: 1
            },
            dataType: 'json',
            success: function(response){
                console.log(typeof(response));
                console.log(response);
                console.log(response.data);
                console.log(response.currentPage);
                console.log(response.pageCounts);
                var currentPage =response.currentPage;
                var pageCounts = response.pageCounts;
                AjaxParam(response,currentPage,pageCounts);
                pageStyle(currentPage,pageCounts);
                checkboxFalse(".u-member-foot .u-member-allsel input");
            },
            error: function(){
                alert("网络错误");
            }
        });
    }
	// 用户管理入口
    $(".m-flex-l .u-img").on("click",function () {
        $(".m-info0").hide();
        $(".m-info2").hide();
        $(".admin-g-body").hide();
        $(".m-info1").show();
        $(".admin-g-body1").show();
       //  $(".m-pos .u-pos3").fadeTo(100,0);
       $(".m-pos .u-pos2").fadeTo(10,1);
       $(".m-pos .u-pos2 i").text("用户管理");

       // ajax请求 管理员界面进入用户管理
        showMember();
    });

    window.onload=function(){
        var model = $('.model').val();
        var isOpen = $('.isOpen').val();
        if(isOpen!=""&&isOpen!=null&&isOpen!=undefined){
            if(isOpen=="true"){
                $.MsgBox.Alert("提示", "操作成功！");

            }else{
                $.MsgBox.Alert("提示", "操作失败！");

            }
        }
    };

    // $(".m-info0").fadeTo(200,1);
    // $(".admin-g-body").fadeTo(200,1);
    //版权信息 位置
    //版权信息栏 位置
    // function copyPos(){
    //     if($("body").height() <= $(window).height()-6){
    //         document.getElementById("bodyBottom").style.top=$(window).height() -35 +"px";
    //     }
    //     else if($(window).height() > 617){
    //         document.getElementById("bodyBottom").style.top=document.body.clientHeight -35 +"px";
    //     }else{
    //         // document.getElementById("bodyBottom").style.top = $(document).height() + "px";
    //         bodyHeight = document.body.clientHeight + 5;
    //         document.getElementById("bodyBottom").style.top = bodyHeight + "px";
    //     }
    // }
    // copyPos();
    // $(window).resize(copyPos);

	if(window.location.href.indexOf("AdminUser")>=0){
		$(".m-flex-l .u-img").trigger("click");
        // setTimeout(copyPos,400);
	}else{
        $(".m-info0").fadeTo(100,1);
        $(".admin-g-body").fadeTo(100,1);
    }
	
	function checkboxFalse(ab){
		return $(ab).prop("checked",false);
	}

    var QueryType = "";
    var classify = "";
    var Content = "";
    var id = 0;
    var userManageTbody = $(".admin-g-body1 .u-member-table tbody");
    // alert($(window).height());
    $(".m-pos .u-pos1").on("click",function () {
        $(".admin-g-body1").fadeOut(10);
        $(".admin-g-body2").fadeOut(10);
        $(".m-info1").fadeOut(10);
        $(".m-info2").fadeOut(10);
        $(".m-info0").fadeIn(10);
        $(".m-info0").fadeTo(50,1);
        $(".admin-g-body").fadeIn(10);
        $(".admin-g-body").fadeTo(50,1);
        $(".m-pos .u-pos2").fadeTo(10,0);
        $(".m-pos .u-pos2 i").text("");
    });

    // $(".m-pos .u-pos2 i:contains('用户管理')").on("click",function () {
    //     $(".admin-g-body").fadeOut(100);
    //     $(".admin-g-body2").fadeOut(100);
    //     $(".admin-g-body1").fadeIn(100);
    // });
    //
    // $(".m-pos .u-pos2 i:contains('操作日志')").on("click",function () {
    //     $(".admin-g-body").fadeOut(100);
    //     $(".admin-g-body1").fadeOut(100);
    //     $(".admin-g-body2").fadeIn(100);
    // });

    // 用户管理ajax请求封装
    function memberAjax(CurrentPage) {
        return $.ajax({
            type: 'get',
            url: 'User',
            data: {
                QueryType: QueryType,
                Content: Content,
                CurrentPage: CurrentPage
            },
            dataType: 'json',
            success: function(data){
                console.log(typeof(data));
                console.log(data);
                var currentPage =data.currentPage;
                var pageCounts = data.pageCounts;
//                checkboxFalse(".u-member-foot .u-member-allsel input");
                AjaxParam(data,currentPage,pageCounts);
                pageStyle(currentPage,pageCounts);
                memSelAllF();
            },
            error: function(){
                $.MsgBox.Alert("提示","网络错误");
            }
        });
    }
    
    // 用户管理按条件搜索
    $(".m-info1 .u-search-body button").on("click",function () {
        QueryType = $(".m-info1 .u-search-body select").val();
        // alert(QueryType);
        Content = $(".m-info1 .u-search-body input").val().trim();
        // // 输入框内容判断
        // if(QueryType=="UserName"){
        //     if(Content==null || Content==""){
        //         $.MsgBox.Alert("提示","用户名为空!");
        //         return;
        //     }
        // }else if(QueryType=="Phone"){
        //     var phoneReg = /^0*(13|15)\d{9}$/;
        //     if(!phoneReg.test(Content)){
        //         $.MsgBox.Alert("提示","手机号输入格式有误!");
        //         return;
        //     }
        // }else if(QueryType=="Email"){
        //     var emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
        //     if(!emailReg.test(Content)){
        //         $.MsgBox.Alert("提示","邮箱输入格式有误!");
        //         return;
        //     }
        // }
        var CurrentPage = 1;
        // bug Temp处理
        // var CurrentPage = $("#currentPage").html();
        // 多加一行注释
        // alert(CurrentPage);
        // ajax请求 搜索
        if(Content==null || Content=="" || Content==undefined){
            $.MsgBox.Alert("提示","输入内容为空");
            QueryType = "";
            Content = "";
            showMember();
        }else{
            // alert(QueryType+" "+Content+" "+CurrentPage);
            memberAjax(CurrentPage);
        }
    });

    // 操作日志入口
    $(".m-flex-r .u-img").on("click",function () {
        $(".admin-g-body").fadeOut(10);
        $(".admin-g-body2").fadeIn(10);
        $(".m-info0").fadeOut(10);
        $(".m-info1").fadeOut(10);
        $(".m-info2").fadeIn(10);
        $(".m-pos .u-pos2").fadeTo(10,1);
        // $(".m-pos .u-pos3").fadeTo(100,1);
        $(".m-pos .u-pos2 i").text("操作日志");

        // ajax请求 操作日志
        $.ajax({
            type: 'get',
            url: 'OperationLog',
            dataType: 'json',
            success: function(data){
                console.log(typeof(data));
                console.log(data);
                var currentPage =data.currentPage;
                var pageCounts = data.pageCounts;
                AjaxParam1(data,currentPage,pageCounts);
                pageStyle1(currentPage,pageCounts);
                checkboxFalse(".m-info2 .u-select input");
                checkboxFalse(".admin-g-body2 thead input");
            },
            error: function(){
                $.MsgBox.Alert("提示","网络错误");
            }
        })
    });

    // 授权管理open
    $(document).on("click",".u-member-table .u-oper-1",function () {
       $(".cover-color").fadeIn(10);
       $(".m-authorize").show();
       var currentMem = $(this).parent().parent().siblings("td.usernameval").text();
       $(".m-authorize-top .current-mem").text(currentMem);
       $(".m-authorize-foot .selectall").prop("checked",false);
       // $("#userAuthority input[name='UserName']").val(currentMem);
       // $("#userAuthority").submit();
       // ajax 请求 授权管理显示
       $.ajax({
            type: 'get',
            url: 'Authority',
            data: {
                model: "用户管理",
                UserName:  currentMem
            },
            dataType: 'json',
            success: function(data){
                console.log(typeof(data));
                console.log(data);
                writeAuthority(data);
                $(".m-authorize").fadeTo(200,1);
                var arr_department = 0;
                $(".m-authorize-body tbody tr").each(function () {
                    var arr_depart = [];
                    $(this).find("td:eq(1) label").each(function () {
                        if(!$(this).find("input").prop("checked")){
                            arr_depart.push(1);
                        }
                    });
                    if(arr_depart.length == 0){
                        $(this).find("td:eq(0) input.department").prop("checked",true);
                    }
                    if(!$(this).find("td:eq(0) input.department").prop("checked")){
                        arr_department++;
                    }
                });
                if(arr_department == 0 || arr_department == "0"){
                    $(".m-authorize-foot input.selectall").prop("checked",true);
                }
                // $(".m-authorize-body tbody tr").each(function () {
                //     // $(this).find(".u-depart").val();
                // });
            },
            error: function(){
                $.MsgBox.Alert("提示","网络错误");
            }
        });
    });

    // 授权管理选择全部
    $(".u-selectall input").on("click",function () {
        $(".department").prop("checked",this.checked);
        $(".m-authorize-body .u-depart").prop("checked",this.checked);
//                var department = $(".department");
//                var is_checked = $(".u-selectall input").prop("checked");
//                for(var i = 0;i < department.length;i++){
//                    department.eq(i).prop("checked",is_checked);
//                }
    });
    $(document).on("click",".m-authorize .department",function () {
       $(this).parent().parent().siblings("td").find(".u-depart").prop("checked",this.checked);
        if(!$(this).prop("checked")){
            $(".u-selectall input").prop("checked",false);
        }else{
        	var is_checkTrAll = [];
        	$(".m-authorize .department").each(function(){
        		if(!$(this).prop("checked")){
        			is_checkTrAll.push(1);
        		}
        	});
        	if(is_checkTrAll.length==0){
        		$(".u-selectall input").prop("checked",true);
        	}
        }
    });
    $(document).on("click",".m-authorize .u-depart",function () {
        if(!$(this).prop("checked")){
            $(".u-selectall input").prop("checked",false);
            $(this).parent().parent().siblings("td").find(".department").prop("checked",false);
        }else{
        	var is_checkTrAll = [];
        	var is_checkTableAll = [];
        	$(this).parent().siblings().each(function(){
        		if(!$(this).children().prop("checked")){
        			is_checkTrAll.push(1);
        		}
        	});

        	if(is_checkTrAll.length==0){
        	    $(this).parent().parent().siblings("td").find(".department").prop("checked",true);
                $(".m-authorize-body .department").each(function () {
                    if(!$(this).prop("checked")){
                        is_checkTableAll.push(2);
                    }
                });
                // alert(is_checkTableAll.length);
                if(is_checkTableAll.length==0){
                    $(".u-selectall input").prop("checked",true);
                }
        	}
        }
    });

    // 授权管理提交
    $(".m-authorize-foot .u-button button:nth-child(1)").on("click",function () {
        var UserName = $(".m-authorize-top .current-mem").text();

        var hasSelAuthority = [];
        $(".m-authorize .m-authorize-body tbody .u-depart").each(function () {
            if($(this).prop("checked")){
                hasSelAuthority.push($(this).val());
            }
        });
        // alert(typeof hasSelAuthority);
        // 升序
        // x,y表示数组中的任意两个元素，若return > 0,则y前x;若reutrn < 0 ,则x前y后;当x=y时存在浏览器兼容。
        // x-y是按照从小到大排序，y-x是按照从大到小排序
        hasSelAuthority.sort(function (x,y) {
            return x-y;
        });
        var Authority = hasSelAuthority.join(",");
        // alert(typeof Authority);
        // alert(Authority);
        // ajax 请求 授权管理修改提交
        console.log(Authority);
        $.ajax({
            type: 'get',
            url: 'AuthorityOperate',
            data: {
                Authority: Authority,
                UserName: UserName
            },
            // dataType: 'json',
            dataType: 'text',
            success: function(data){
                // console.log(typeof(data));
                // console.log(data);
                d  = data.replace("\"","").replace("\"","");
                $.MsgBox.Alert("提示",d);
                $(".m-authorize-top .u-right").trigger("click");
                // writeAuthority(data);
                // $(".m-authorize-body tbody tr").each(function () {
                //     // $(this).find(".u-depart").val();
                // });
            },
            error: function(){
                $.MsgBox.Alert("提示","网络错误");
            }
        });

        // form 提交法
        // $("#useAuthorityOperate input[name='UserName']").val($(".m-authorize-top .current-mem").text());
        // alert($("#useAuthorityOperate input[name='UserName']").val());
        // var hasSelAuthority = [];
        // $(".m-authorize .m-authorize-body tbody .u-depart").each(function () {
        //     if($(this).prop("checked")){
        //         hasSelAuthority.push($(this).val());
        //     }
        // });
        // alert(hasSelAuthority);
        // hasSelAuthority.join(",");
        // alert(hasSelAuthority);
        // $("#useAuthorityOperate input[name='Authority']").val(hasSelAuthority);
        // alert($("#useAuthorityOperate input[name='Authority']").val());
        // $("#useAuthorityOperate").submit();
        // alert(555);
    });

    // 授权管理关闭
    function authorizeClose() {
        $(".cover-color").fadeOut(10);
        $(".m-authorize").fadeOut(10);
    }
    $(".m-authorize-top .u-right").on("click",function(e){
        e.stopPropagation();
        authorizeClose();
    });
    $(".m-authorize-foot .u-button button:nth-child(2)").on("click",function(e){
        e.stopPropagation();
        authorizeClose();
    });

    // 添加修改人员
    function addMemberOpen() {
        $(".cover-color").fadeIn(10);
        $(".m-add_member").fadeIn(10);
        $(".m-add_member-top .u-left").text("添加成员信息");
        $(".m-add_member-body-info input").val("");
        $("#userForm input[name='classify']").val("新增");
        $("#userForm input[name='id']").val("");
        classify = "新增";
    }
    $(".m-info1 .u-member-add button").on("click",addMemberOpen);
        // 点击修改，替换隐藏域信息
    $(document).on("click",".m-member .u-oper-2",function () {
        $(".cover-color").fadeIn(10);
        $(".m-add_member").fadeIn(10);
        $(".m-add_member-top .u-left").text("修改成员信息");
        $("#userForm input[name='classify']").val("修改");
        $("#userForm input[name='id']").val($(this).parent().parent().siblings(".mem-col1").find("input[name='id']").val());
        $(".m-add_member-body-info input[name='user_name']").val($(this).parent().parent().siblings(".usernameval").text());
        $(".m-add_member-body-info select[name='sex']").val($(this).parent().parent().siblings(".sexval").text()).prop("checked",true);
        $(".m-add_member-body-info input[name='phone']").val($(this).parent().parent().siblings(".telval").text());
        $(".m-add_member-body-info input[type='email']").val($(this).parent().parent().siblings(".emailval").text());
        $(".m-add_member-body-info select[name='Role']").val($(this).parent().parent().siblings(".roleval").text()).prop("checked",true);
        classify = "修改";
        id = $(this).parent().parent().siblings("td:nth-child(1)").find("input[name='id']").val();
    });

    // //  添加修改人员提交validate
    // function modifyValidate(){
    //     alert("验证");
    //     var email = $(".m-add_member-body-info input[type='email']").val().trim();
    //     var phone = $(".m-add_member-body-info input[name='phone']").val().trim();
    //     var phoneReg = /^0*(13|15|16|17|18|19)\d{9}$/;
    //     if(email == null || email == ""){
    //         $.MsgBox.Alert("提示","请输入邮箱！");
    //         return false;
    //     }else if(!phoneReg.test(phone)){
    //         $.MsgBox.Alert("提示","手机号输入格式有误!");
    //         return false;
    //     }else{
    //         return true;
    //     }
    // }

    // // 添加修改人员提交
    // $(".m-add_member-foot input[type='submit']").on("click",function () {
    //     // 表单验证
    //     var user_name = $(".m-add_member-body-info input[name='add_mem-name']").val().trim();
    //     var sex = $(".m-add_member-body-info select[name='add_mem-sex']").val();
    //     var email = $(".m-add_member-body-info input[type='email']").val().trim();
    //     var phone = $(".m-add_member-body-info input[name='add_mem-tel']").val().trim();
    //     var Role = $(".m-add_member-body-info select[name='add_mem-role']").val();
    //
    //     if(user_name==null || user_name==""){
    //         $.MsgBox.Alert("提示","用户名为空!请检查两端是否输入了空格");
    //         return;
    //     }
    //     var phoneReg = /^0*(13|15|16|17|18|19)\d{9}$/;
    //     if(!phoneReg.test(phone)){
    //         $.MsgBox.Alert("提示","手机号输入格式有误!");
    //         return;
    //     }
    //     var emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
    //     if(!emailReg.test(email)){
    //         $.MsgBox.Alert("提示","邮箱输入格式有误!");
    //         return;
    //     }
    //     alert(classify+id+user_name+sex+email+phone+Role);
    //     // ajax 请求 用户管理-添加修改
    //     if (classify=="新增"){
    //         alert("shiwo");
    //         $.ajax({
    //             type: 'get',
    //             url: 'AdminUser',
    //             data: {
    //                 classify: classify,
    //                 user_name: user_name,
    //                 sex: sex,
    //                 email: email,
    //                 phone: phone,
    //                 Role: Role
    //             },
    //             dataType: 'json',
    //             success: function(data){
    //                 console.log(typeof(data));
    //                 if(data.sign){
    //                     $.MsgBox.Alert("提示",data.message);
    //                 }else{
    //                     $.MsgBox.Alert("提示",data.message);
    //                 }
    //             },
    //             error: function(){
    //                 $.MsgBox.Alert("提示","网络错误");
    //             }
    //         });
    //     }else if(classify=="修改"){
    //         $.ajax({
    //             type: 'get',
    //             url: 'AdminUser',
    //             data: {
    //                 classify: classify,
    //                 id: id,
    //                 user_name: user_name,
    //                 sex: sex,
    //                 email: email,
    //                 phone: phone,
    //                 Role: Role
    //             },
    //             dataType: 'json',
    //             success: function(data){
    //                 console.log(typeof(data));
    //                 if(data.sign){
    //                     $.MsgBox.Alert(data.message);
    //                 }else{
    //                     $.MsgBox.Alert(data.message);
    //                 }
    //             },
    //             error: function(){
    //                 $.MsgBox.Alert("提示","当前网络错误");
    //             }
    //         });
    //     }
    //
    // });
   
//    $("#userForm").submit(function(){
//    });

    // 添加修改人员关闭
    function addMemberClose() {
        $(".cover-color").fadeOut(10);
        $(".m-add_member").fadeOut(10);
    }
    $(".m-add_member .u-right").on("click",addMemberClose);
    $(".m-add_member-foot button").on("click",addMemberClose);

    // 用户管理删除行
    $(document).on("click",".m-member .u-oper-3",function () {
    	id = $(this).parent().parent().siblings("td:nth-child(1)").find("input[name='id']").val();
    	$("#userFormDel input[name='id']").val(id);
        classify = "删除";
        $("#userFormDel").submit();
//        alert(id+classify);
//        // ajax请求 删除行
//        $.ajax({
//            type: 'get',
//            url: 'AdminUser',
//            data: {
//                classify: classify,
//                id: id
//            },
//            dataType: 'json',
//            success: function(data){
//                console.log(typeof(data));
//                if(data.sign){
//                    $.MsgBox.Alert("提示",data.message);
//                    $(this).parent().parent().parent().remove();
//                }else{
//                    $.MsgBox.Alert("提示",data.message);
//                }
//            },
//            error: function(){
//                $.MsgBox.Alert("提示","网络错误");
//            }
//        });
    });

    // 用户管理复选框
        // 选择当前页
    $(".u-member-foot .u-member-allsel input").on("click",function () {
        $(".u-member-table tbody input[type='checkbox']").prop("checked",this.checked);
        if($(this).prop("checked")){
            $(".admin-g-body1 tbody tr").addClass("trGrey");
        }else{
            $(".admin-g-body1 tbody tr").removeClass("trGrey");
        }
    });
        // 单独行复选框
    $(document).on("click",".admin-g-body1 tbody input[type='checkbox']",function () {
        var checkAllArr = [];
        var tr = $(".admin-g-body1 tbody tr").length;
        /*// 获取对象的key值
        var arr = Object.keys(tr);
        console.log(arr);
        // 获取长度
        console.log(arr.length);*/
        $(".admin-g-body1 tbody input[type='checkbox']").each(function () {
            if($(this).prop("checked")) {
                checkAllArr.length += 1;
            }
        });
        if(checkAllArr.length == tr){
            $(".u-member-foot .u-member-allsel input").prop("checked",true);
        }
        if(!$(this).prop("checked")){
            $(".u-member-foot .u-member-allsel input").prop("checked",false);
            $(this).parent().parent().removeClass("trGrey");
        }else{
            $(this).parent().parent().addClass("trGrey");
        }
    });



    // 用户管理批量删除
    $(".u-member-alldel button").on("click",function () {
        classify = "批量删除";
        var idList = [];
        $(".m-member tbody tr").each(function () {
            if($(this).find("input[type='checkbox']").prop('checked')){
                var a = $(this).find("input[name='id']").val();
                idList.push(a);
            }
        });
        if(idList.length == 0){
            return;
        }
        var idListInput = "";
        for(var abc = 0;abc<idList.length;abc++){
        	idListInput+='<input type="hidden" name="idList[]" value="'+idList[abc]+'">';
        }
        $("#userFormDelAll").append(idListInput);
        $("#userFormDelAll").submit();
//        alert(classify+idList[0]+idList[1]);
//        // ajax请求 用户管理-批量删除
//        $.ajax({
//            type: 'get',
//            url: 'AdminUser',
//            data: {
//                classify: classify,
//                idList: idList
//            },
//            dataType: 'json',
//            success: function(data){
//                console.log(typeof(data));
//                if(data.sign){
//                    $.MsgBox.Alert("提示",data.message);
//                    $(".m-member tbody tr").each(function () {
//                        if($(this).find("input[type='checkbox']").prop('checked')){
//                            $(this).remove();
//                        }
//                    });
//                    $(".u-member-foot .u-member-allsel input").prop("checked",false);
//                }else{
//                    $.MsgBox.Alert("提示",data.message);
//                }
//            },
//            error: function(){
//                $.MsgBox.Alert("提示","网络错误");
//            }
//        });
    });
    
    // 操作日志复选框
        // 导出全部复选框
    function btnExport(){
        var btnExportArr = [];
        $(".m-operation-table tbody tr").each(function(){
            if(!$(this).find(".oper-col1 input[type='checkbox']").prop("checked")){
                btnExportArr.push(3);
            }
        });
        var btnExportTr = $(".m-operation-table tbody tr").length;
        if (btnExportArr.length == btnExportTr){
            $(".u-export button").attr("disabled","disabled");
        }else{
            $(".u-export button").removeAttr("disabled");
        }
    }

    btnExport();

    $(".m-info2 .u-select input").on("click",function () {
       $(".admin-g-body2 table input[type='checkbox']").prop("checked",this.checked); 
       if(!$(this).prop("checked")){
        $(".m-operation-table tbody tr").removeClass("trGrey");
       }else{
        $(".m-operation-table tbody tr").addClass("trGrey");
       }
       btnExport();
    });
        // 选择当前页
    $(".admin-g-body2 thead input").on("click",function () {
        $(".admin-g-body2 tbody input[type='checkbox']").prop("checked",this.checked);
        if(!$(this).prop("checked")){
            $(".m-info2 .u-select input").prop("checked",false);
            $(".m-operation-table tbody tr").removeClass("trGrey");
        }else{
            $(".m-operation-table tbody tr").addClass("trGrey");
        }
        btnExport();
    });
    $(document).on("click",".admin-g-body2 tbody input[type='checkbox']",function () {
        var checkAllArr = [];
        var tr = $(".admin-g-body2 tbody tr").length;
        /*// 获取对象的key值
        var arr = Object.keys(tr);
        // console.log(arr);
        // 获取长度
        // console.log(arr.length);
         */        
        $(".m-operation-table tbody input[type='checkbox']").each(function () {
            if($(this).prop("checked")) {
                checkAllArr.length += 1;
            }
        });
        if(checkAllArr.length == tr){
            $(".admin-g-body2 thead input").prop("checked",true);
        }
        if(!$(this).prop("checked")){
            $(".m-info2 .u-select input").prop("checked",false);
            $(".admin-g-body2 thead input").prop("checked",false);
            $(this).parent().parent().removeClass("trGrey");
        }else{
            $(this).parent().parent().addClass("trGrey");
        }
        btnExport();
    });
    
    // 操作日志导出
    $(".m-info2 .u-export button").on("click",function () {
        // 选择性导出
        var idList = [];
        if(!$(".m-info2 .u-select input").prop("checked")){
            $(".m-operation tbody tr").each(function () {
                if($(this).find("input[type='checkbox']").prop('checked')){
                	var b = $(this).find("input[name='idOper']").val();
                    idList.push(b);
                } 
            });
            // alert(idList);
//            alert(idList.length);
            if(idList.length>0){
                //ajax请求 操作日志选择性导出
                $.ajax({
                    type: 'get',
                    url: 'DownloadLog',
                    data: {
                        idList: idList
                    },
                    // dataType: 'json',
                    success: function(data){
                        console.log(typeof(data));
                        window.location.href = data;
                    },
                    error: function(){
                        $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
                    }
                });
            }
        }else{
            //ajax请求 操作日志导出
            $.ajax({
                type: 'get',
                url: 'DownloadLog',
                // dataType: 'json',
                success: function(data){
                    console.log(typeof(data));
                    window.location.href = data;
                },
                error: function(){
                    $.MsgBox.Alert("提示", "服务器繁忙，请稍后重试！");
                }
            });
        }
    });
    
    function buttonDisabled(iClass) {
        return $(iClass).removeClass("buttonDisabled buttonAbled").addClass("buttonDisabled");
    }

    function buttonAbled(jClass) {
        return $(jClass).removeClass("buttonDisabled buttonAbled").addClass("buttonAbled");
    }

    //按钮样式函数封装
    function pageStyle(currentPage,pageCounts){
        if(pageCounts == 1){
            $("#fistPage").attr("disabled","disabled");
            $("#upPage").attr("disabled","disabled");
            $("#nextPage").attr("disabled","disabled");
            $("#lastPage").attr("disabled","disabled");
            buttonDisabled("#fistPage, #upPage, #nextPage, #lastPage");
        }else if(currentPage == 1){
            $("#fistPage").attr("disabled","disabled");
            $("#upPage").attr("disabled","disabled");
            $("#lastPage").attr("disabled",false);
            $("#nextPage").attr("disabled",false);
            buttonDisabled("#fistPage, #upPage");
            buttonAbled("#nextPage, #lastPage");
        }else if(currentPage == pageCounts){
            $("#lastPage").attr("disabled","disabled");
            $("#nextPage").attr("disabled","disabled");
            $("#fistPage").attr("disabled",false);
            $("#upPage").attr("disabled",false);
            buttonDisabled("#nextPage, #lastPage");
            buttonAbled("#fistPage, #upPage");
        }else{
            $("#lastPage, #nextPage, #fistPage, #upPage").attr("disabled",false);
            buttonAbled("#lastPage, #nextPage, #fistPage, #upPage");
        }

    }

    //ajax函数封装-用户管理
    function AjaxParam(response,currentPage,pageCounts){
        $(".admin-g-body1 .u-member-table tbody").empty();
        var str = '';
        for(var i = 1;i<response.data.length;i++){
            str +='<tr>'+
                '<td class="mem-col1"><input type="checkbox"><input type="hidden" name="id" value="'+response.data[i].ID+'"></td>'+
                '<td class="col2">'+parseInt((currentPage-1)*10 + i)+'</td>'+
                '<td class="usernameval">'+response.data[i].UserName+'</td>'+
                '<td class="sexval">'+response.data[i].Sex+'</td>'+
                '<td class="telval">'+response.data[i].Phone+'</td>'+
                '<td class="emailval">'+response.data[i].Email+'</td>'+
                '<td class="roleval">'+response.data[i].Role+'</td>'+
                '<td class="col8">'+response.data[i].CreateDate+'</td>'+
                '<td class="col9">'+response.data[i].LastLogin+'</td>'+
                '<td class="col10"><div class="u-oper"><span class="u-oper-1"><i></i>授权</span><span class="u-oper-2"><i></i>修改</span><span class="u-oper-3"><i></i>删除</span></div></td>'
                '</tr>';
            // str +='<tr>'+
            //     '<td class="col1">'+parseInt((currentPage-1)*6 + i)+'</td>'+
            //     '<td class="col2">'+data.logs[i].Account+'</td>'+
            //     '<td class="col3">'+data.logs[i].JspInfo+'</td>'+
            //     '<td class="col4">'+data.logs[i].Description+'</td>'+
            //     '<td class="col5">'+data.logs[i].Date+'</td>'+
            //     '<td class="col6">'+data.logs[i].Time+'</td>'+
            //     '<td class="col7">'+data.logs[i].IpInfo+'</td>'+
            //     '<td class="col8">'+data.logs[i].Location+'</td>'+
            //     '</tr>';
            console.log(response.data);
        }
        $("#currentPage").html(currentPage);
        $("#allPage").html(pageCounts);
        $(".admin-g-body1 .u-member-table tbody").append(str);
    }

    //按钮样式函数封装-操作日志
    function pageStyle1(currentPage,pageCounts){
        if(pageCounts == 1){
            $("#fistPage1").attr("disabled","disabled");
            $("#upPage1").attr("disabled","disabled");
            $("#nextPage1").attr("disabled","disabled");
            $("#lastPage1").attr("disabled","disabled");
            buttonDisabled("#fistPage1, #upPage1, #nextPage1, #lastPage1");
        }else if(currentPage == 1){
            $("#fistPage1").attr("disabled","disabled");
            $("#upPage1").attr("disabled","disabled");
            $("#lastPage1").attr("disabled",false);
            $("#nextPage1").attr("disabled",false);
            buttonDisabled("#fistPage1, #upPage1");
            buttonAbled("#nextPage1, #lastPage1");
        }else if(currentPage == pageCounts){
            $("#lastPage1").attr("disabled","disabled");
            $("#nextPage1").attr("disabled","disabled");
            $("#fistPage1").attr("disabled",false);
            $("#upPage1").attr("disabled",false);
            buttonDisabled("#nextPage1, #lastPage1");
            buttonAbled("#fistPage1, #upPage1");
        }else{
            $("#lastPage1, #nextPage1, #fistPage1, #upPage1").attr("disabled",false);
            buttonAbled("#lastPage1, #nextPage1, #fistPage1, #upPage1");
        }
    }

    //ajax函数封装-操作日志
    function AjaxParam1(data,currentPage,pageCounts){
        $(".admin-g-body2 .m-operation-table tbody").empty();
        var str = '';
        for(var i = 1;i<data.logs.length;i++){
            str +='<tr>'+
                '<td class="oper-col1"><input type="checkbox"><input type="hidden" name="idOper" value="'+data.logs[i].ID+'"></td>'+
                '<td class="col2">'+parseInt((currentPage-1)*10 + i)+'</td>'+
                '<td class="col3">'+data.logs[i].Account+'</td>'+
                '<td class="col4" title="'+data.logs[i].JspInfo+'">'+data.logs[i].JspInfo+'</td>'+
                '<td class="col5" title="'+data.logs[i].Description+'">'+data.logs[i].Description+'</td>'+
                '<td class="col6">'+data.logs[i].Date+'</td>'+
                '<td class="col7">'+data.logs[i].Time+'</td>'+
                '<td class="col8">'+data.logs[i].IpInfo+'</td>'+
                '<td class="col9">'+data.logs[i].Location+'</td>'+
                '</tr>';
            // str +='<tr>'+
            //     '<td class="col1">'+parseInt((currentPage-1)*6 + i)+'</td>'+
            //     '<td class="col2">'+data.logs[i].Account+'</td>'+
            //     '<td class="col3">'+data.logs[i].JspInfo+'</td>'+
            //     '<td class="col4">'+data.logs[i].Description+'</td>'+
            //     '<td class="col5">'+data.logs[i].Date+'</td>'+
            //     '<td class="col6">'+data.logs[i].Time+'</td>'+
            //     '<td class="col7">'+data.logs[i].IpInfo+'</td>'+
            //     '<td class="col8">'+data.logs[i].Location+'</td>'+
            //     '</tr>';
            console.log(data.logs);
        }
        $("#currentPage1").html(currentPage);
        $("#allPage1").html(pageCounts);
        $(".admin-g-body2 .m-operation-table tbody").append(str);
    }

    // 用户管理翻页
    // 分页条交互
    $("#fistPage").click(function(){
        var currentPage =1;
        memberAjax(currentPage);
        // $.ajax({
        //     type:'get',
        //     url:'User',
        //     data:{
        //
        //         CurrentPage:currentPage
        //     },
        //     dataType:'json',
        //     success:function(data){
        //         console.log(data);
        //         var currentPage =data.currentPage;
        //         var pageCounts = data.pageCounts;
        //         AjaxParam(data,currentPage,pageCounts);
        //         pageStyle(currentPage,pageCounts);
        //         memSelAllF();
        //     }
        // })
    });

    $("#lastPage").click(function(){
        var currentPage =$("#allPage").html();
        memberAjax(currentPage);
    });

    $("#upPage").click(function(){
        var currentPage = $("#currentPage").html();
        if(currentPage == 1){
            return;
        }else{
            currentPage--;
        }
        var pageCounts =$("#allPage").html();
        memberAjax(currentPage);
    });

    $("#nextPage").click(function(){
        var currentPage = $("#currentPage").html();
        var pageCounts =$("#allPage").html();
        if(currentPage == pageCounts){
            return;
        }else{
            currentPage++;
        }
        memberAjax(currentPage);
    });
    //跳页
    $("#Gotojump").click(function(){
        var currentPage = $("#jumpNumber").val().trim();
        var pageCounts =$("#allPage").html();
        if(currentPage == $("#currentPage").html() || currentPage <= 0 || currentPage>pageCounts){
            $("#jumpNumber").val('');
            return;
        }else{
            memberAjax(currentPage);
        }
    });


    // 操作日志翻页
    // 分页条交互
    $("#fistPage1").click(function(){
        var currentPage =1;
        $.ajax({
            type:'get',
            url:'OperationLog',
            data:{
                currentPage:currentPage
            },
            dataType:'json',
            success:function(data){
                console.log(data);
                var currentPage =data.currentPage;
                var pageCounts = data.pageCounts;
                AjaxParam1(data,currentPage,pageCounts);
                pageStyle1(currentPage,pageCounts);
                operSelAllF();
                operExportAllF();
            }
        })
    });

    $("#lastPage1").click(function(){
        var currentPage =$("#allPage1").html();
        $.ajax({
            type:'get',
            url:'OperationLog',
            data:{
                currentPage:currentPage
            },
            dataType:'json',
            success:function(data){
                console.log(data);
                var currentPage =data.currentPage;
                var pageCounts = data.pageCounts;
                AjaxParam1(data,currentPage,pageCounts);
                pageStyle1(currentPage,pageCounts);
                operSelAllF();
                operExportAllF();
            }
        })
    });

    $("#upPage1").click(function(){
        var currentPage = $("#currentPage1").html();
        if(currentPage == 1){
            return;
        }else{
            currentPage--;
        }
        var pageCounts =$("#allPage1").html();
        $.ajax({
            type:'get',
            url:'OperationLog',
            data:{
                currentPage:currentPage
            },
            dataType:'json',
            success:function(data){
                console.log(data);
                var currentPage =data.currentPage;
                var pageCounts = data.pageCounts;
                AjaxParam1(data,currentPage,pageCounts);
                pageStyle1(currentPage,pageCounts);
                operSelAllF();
                operExportAllF();
            }
        })
    });

    $("#nextPage1").click(function(){
        var currentPage = $("#currentPage1").html();
        var pageCounts =$("#allPage1").html();
        if(currentPage == pageCounts){
            return;
        }else{
            currentPage++;
        }
        $.ajax({
            type:'get',
            url:'OperationLog',
            data:{
                currentPage:currentPage
            },
            dataType:'json',
            success:function(data){
                console.log(data);
                var currentPage =data.currentPage;
                var pageCounts = data.pageCounts;
                AjaxParam1(data,currentPage,pageCounts);
                pageStyle1(currentPage,pageCounts);
                operSelAllF();
                operExportAllF();
            }
        });
    });
    //跳页
    $("#Gotojump1").click(function(){
        var currentPage = $("#jumpNumber1").val().trim();
        var pageCounts =$("#allPage1").html();
        if(currentPage == $("#currentPage1").html() || currentPage <= 0 || currentPage>pageCounts){
            $("#jumpNumber1").val('');
            return;
        }else{
            $.ajax({
                type:'get',
                url:'OperationLog',
                data:{
                    currentPage:currentPage
                },
                dataType:'json',
                success:function(data){
                    console.log(data);
                    var currentPage =data.currentPage;
                    var pageCounts = data.pageCounts;
                    AjaxParam1(data,currentPage,pageCounts);
                    pageStyle1(currentPage,pageCounts);
                    operSelAllF();
                    operExportAllF();
                }
            });
        }
    });
    function memSelAllF(){
    	return $(".u-member-foot .u-member-allsel input").prop("checked",false);
    }
    function operSelAllF(){
    	return $(".m-operation thead input").prop("checked",false);
    }
    function operExportAllF(){
    	return $(".m-info2 .u-select input").prop("checked",false);
    }
    
    function writeAuthority(data) {
        $(".m-authorize-body tbody input").prop("checked",false);
        $(".m-authorize-body tbody").empty();
        var str = '';
        var roleAuthority = [];
        var roleAuthorityName = [];
        for(var i = 1;i<data.roles.length;i++){
            str +='<tr>'+
                '<td><label><input type="checkbox" class="department">'+data.roles[i].Name+'</td><td>';
            roleAuthority = data.roles[i].Authority.split(",");
            roleAuthorityName = data.roles[i].AuthorityName.split(",");
            for(var j = 0;j<roleAuthority.length;j++){
                str += '<label><input type="checkbox" class="u-depart" value="'+roleAuthority[j]+'">'+roleAuthorityName[j]+'</label>';
            }
            str +='</td></tr>';
            
        }
        console.log("权限获取的字符串HTML");
        console.log(str);
        $(".m-authorize-body tbody").append(str);
        var HH = $(".m-authorize-body tbody").height();
        var H1 = $("div.m-authorize-body").height();
        var HDis = Math.abs(HH - H1);
        console.log("高度差值为"+HDis);
        var newH1 = H1+HDis;
        $("div.m-authorize-body").css("height",newH1+"px");
        var strAuthority = new Array();
        if(data.authorities[1].Authority == "--"){
            strAuthority.push(0);
        }
        strAuthority = data.authorities[1].Authority.split(",");
        $('.m-authorize tbody input.u-depart').each(function () {
            for(var ii=0;ii<strAuthority.length;ii++){
                if($(this).val()==strAuthority[ii]){
                    $(this).prop('checked',true);
                    break;
                }
            } 
        });
    }
});

$(".u-pagevisit button").click(function(){
    window.open("PageVisit");
});