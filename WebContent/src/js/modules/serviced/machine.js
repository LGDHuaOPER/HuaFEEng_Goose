(function () {
  $.MsgBox = {
  Alert: function (title, msg, callback) {
    GenerateHtml("alert", title, msg);
    btnOk(callback); //alert只是弹出消息，因此没必要用到回调函数callback
    btnNo();
  },
  Judge: function (title, msg, callback) {
	    GenerateHtml("judge", title, msg);
	    btnJug(callback); //alert只是弹出消息，因此没必要用到回调函数callback
	    btnNo();
	  },
    Confirm: function (title, msg, callback) {
      GenerateHtml("confirm", title, msg);
      btnOk(callback);
      btnNo();
    },
    Prompt: function (title, msg, callback) {
        GenerateHtml("prompt", title, msg);
        btnOk(callback);
        btnNo();
      },
     Remind : function (title, msg, callback) {
         GenerateHtml("remind", title, msg);
         btnOk(callback);
         btnNo();
       }
  };
 
  //生成Html
  var GenerateHtml = function (type, title, msg) {
 
    var _html = "";
 
    _html += '<div id="mb_box"></div><div id="mb_con"><span id="mb_tit">' + title + '</span>';
    _html += '<a id="mb_ico"></a><div id="mb_msg">' + msg + '</div><div id="mb_btnbox">';
 
    if (type == "alert") {
      _html += '<input id="mb_btn_ok" type="button" value="确定" />';
    }
    if (type == "judge") {
        _html += '<input id="mb_jug_ok" type="button" value="确定" />';
      }
    if (type == "confirm") {
      _html += '<input id="mb_btn_ok" type="button" value="是" />';
      _html += '<input id="mb_btn_no" type="button" value="否" />';
    }
    if (type == "prompt") {
      _html += '<span style="display:inline-block;width:100%;height:27px;background:#00aeef"></span>';
    }

    if (type == "remind") {
      _html += '<span style="display:inline-block;width:100%;height:27px;background:#fff;margin-top: -1px;"></span>';
    }
    _html += '</div></div>';
 
    //必须先将_html添加到body，再设置Css样式
    $("body").append(_html); GenerateCss();
  };
 
  //生成Css
  var GenerateCss = function () {
 
    $("#mb_box").css({ width: '100%', height: '100%', zIndex: '99999', position: 'fixed',
      filter: 'Alpha(opacity=60)', backgroundColor: 'rgba(10%,20%,30%,0.6)', top: '0', left: '0', opacity: '0.6'
    });
 
    $("#mb_con").css({ zIndex: '999999', width: '355px',height:'189px', position: 'fixed',
      backgroundColor: 'White', borderRadius: '',border:'1px solid #fff'
    });
 
    $("#mb_tit").css({ display: 'block', fontSize: '16px', color: '#444', padding: '2px 15px',
      backgroundColor: '#00aeef', borderRadius: '',
      borderBottom: '', fontWeight: 'normal',
      fontFamily:'microsoft yahei'
    });
 
    $("#mb_msg").css({ height: '60px', lineHeight: '125px',
      fontSize: '13px',textAlign:'center',fontSize:'16px', fontFamily:'microsoft yahei'
    });
    $("#mb_msg p").css({ height: '30px', lineHeight: '30px',width:'350px',textOverflow:'ellipsis',whiteSpace:'nowrap',overflow:'hidden'
      });

 
//  $("#mb_ico").css({ display: 'block', position: 'absolute', right: '10px', top: '9px',
//    border: '1px solid Gray', width: '18px', height: '18px', textAlign: 'center',
//    lineHeight: '16px', cursor: 'pointer', borderRadius: '12px', fontFamily: '微软雅黑'
//  });
 
    $("#mb_btnbox").css({ margin: '75px 0 0', textAlign: 'center' ,background:'#00aeef',paddingTop:'1px'});
    $("#mb_btn_ok,#mb_jug_ok,#mb_btn_no").css({ width: '60px', height: '28px', color: '#000', border: '1px solid #fff',borderRadius:'3px',background:'#00aeef',fontSize:'14px', fontFamily:'microsoft yahei' });
    $("#mb_btn_ok,#mb_jug_ok").css({ backgroundColor: '#00aeef' });
    $("#mb_btn_no").css({ backgroundColor: '#00aeef', marginLeft: '20px' });
 
 
    //右上角关闭按钮hover样式
    $("#mb_ico").hover(function () {
      $(this).css({ backgroundColor: 'Red', color: 'White' });
    }, function () {
      $(this).css({ backgroundColor: '#DDD', color: 'black' });
    });
 
    var _widht = document.documentElement.clientWidth; //屏幕宽
    var _height = document.documentElement.clientHeight; //屏幕高
 
    var boxWidth = $("#mb_con").width();
    var boxHeight = $("#mb_con").height();
 
    //让提示框居中
    $("#mb_con").css({ top: (_height - boxHeight) / 2 + "px", left: (_widht - boxWidth) / 2 + "px" });
  };
 
 
  //确定按钮事件
  var btnOk = function (callback) {
    $("#mb_btn_ok").click(function () {
      $("#mb_box,#mb_con").remove();
      if (typeof (callback) == 'function') {
        callback();
      }
    });
  };
 
  //判断按钮事件
  var btnJug = function (callback) {
    $("#mb_jug_ok").click(function () {
      $("#mb_box,#mb_con").remove();
      if (typeof (callback) == 'function') {
        callback();
      }
    });
  };
  //取消按钮事件
  var btnNo = function () {
    $("#mb_btn_no,#mb_ico").click(function () {
      $("#mb_box,#mb_con").remove();
    });
  }
})();

/**
 * Created by eoulu on 2017/3/29.
 */
// 翻页组件按钮逻辑
// flag 为按钮ID后缀  如 pageStyle(CurrentPage, pageCount, "2");
function pageStyle(currentPage, pageCounts, flag) {
    flag = flag || "";
    if (pageCounts == 1) {
        $("#fistPage" + flag + ", #upPage" + flag + ", #nextPage" + flag + ", #lastPage" + flag + ", #Gotojump" + flag).prop("disabled", "disabled").removeClass("btn-primary").addClass("btn-default");
    } else if (currentPage == 1) {
        $("#fistPage" + flag + ", #upPage" + flag).prop("disabled", "disabled").removeClass("btn-primary").addClass("btn-default");
        $("#lastPage" + flag + ", #nextPage" + flag + ", #Gotojump" + flag).prop("disabled", false).removeClass("btn-default").addClass("btn-primary");
    } else if (currentPage == pageCounts) {
        $("#lastPage" + flag + ", #nextPage" + flag).prop("disabled", "disabled").removeClass("btn-primary").addClass("btn-default");
        $("#fistPage" + flag + ", #upPage" + flag + ", #Gotojump" + flag).prop("disabled", false).removeClass("btn-default").addClass("btn-primary");
    } else {
        $("#lastPage" + flag + ", #nextPage" + flag + ", #fistPage" + flag + ", #upPage" + flag + ", #Gotojump" + flag).prop("disabled", false).removeClass("btn-default").addClass("btn-primary");
    }
}

if($('input[name="selected"]:checked').val()=='singleSelect'){
    $('.select-content').css('margin-left','33%');
}else{
    $('.select-content').css('margin-left','23%');

}
function Check(selected) {
	 if (selected == "singleSelect") {
        $('.select2').hide();
        $('.select-content').css('margin-left','33%');
    } else {
        $('.select2').show();
        $('.select-content').css('margin-left','23%');
    }
}

function INSearch() {
    $('#search').val('search');
    $('#top_text_from').submit();
}
function Cancel() {
	window.location.href= 'MachineDetails';
}
$('#searchContent1').keypress(function (event) {
    $('#search').val('search');
    var keynum = (event.keyCode ? event.keyCode : event.which);
    if (keynum == '13') {
        $('#top_text_from').submit();
    }
});
$('#searchContent2').keypress(function (event) {
    $('#search').val('search');
    var keynum = (event.keyCode ? event.keyCode : event.which);
    if (keynum == '13') {
        $('#top_text_from').submit();
    }
});

//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function () {
    window.location.reload();
});

/*原script标签*/
/****************** 跳页 **********************/
function FistPage(arg) {
    if(arg.split('?')[0]=='GetMachineDetailsRoute'){
        $('#search').val('search');
        $("#top_text_from").attr("action", arg+ "1");
        $('#top_text_from').submit();
    }else{ 
        window.location.href = arg+ "1";
     } 
}
function UpPage(arg) {
    if(arg.split('?')[0]=='GetMachineDetailsRoute'){
         $('#search').val('search');
         $("#top_text_from").attr("action", arg);
          $('#top_text_from').submit();
    }else{ 
        window.location.href = arg;
     } 
}
function NextPage(arg) {
    if(arg.split('?')[0]=='GetMachineDetailsRoute'){
         $('#search').val('search');
         $("#top_text_from").attr("action", arg);
          $('#top_text_from').submit();
    }else{ 
        window.location.href = arg;
     } 
}
function PageJump(arg) {
    var jumpNumber = document.getElementById("jumpNumber").value;
    if (jumpNumber == null || jumpNumber == 0) {
        jumpNumber = $('#currentPage').html();
    } else if (jumpNumber > parseInt($('#allPage').html())) {
        jumpNumber = $('#allPage').html();
    }
    if(arg.split('?')[0]=='GetMachineDetailsRoute'){
         $('#search').val('search');
         $("#top_text_from").attr("action", arg + jumpNumber);
          $('#top_text_from').submit();
    }else{ 
        
        window.location.href = arg + jumpNumber;
     } 
}
function LastPage(arg) {
    var jumpNumber = parseInt($('#allPage').html());
    if(arg.split('?')[0]=='GetMachineDetailsRoute'){
         $('#search').val('search');
         $("#top_text_from").attr("action", arg + jumpNumber);
          $('#top_text_from').submit();
    }else{ 
        window.location.href = arg + jumpNumber;
     } 
}
/*原script标签 end*/

/*2018-09-27更新*/
var MachineDetailsState = new Object();
MachineDetailsState.statusNumMap = {
    "交付": "1",
    "尾款": "2",
    "完结": "3",
};
MachineDetailsState.addSubmitObj = new Object();
MachineDetailsState.addSubmitObj.CustomerID = null;
MachineDetailsState.addSubmitObj.classify = null;
MachineDetailsState.addSubmitObj.Model = null;
MachineDetailsState.addSubmitObj.SN = null;
MachineDetailsState.addSubmitObj.ContractNO = null;
MachineDetailsState.addSubmitObj.InstalledTime = null;
MachineDetailsState.addSubmitObj.Status = null;
MachineDetailsState.addSubmitObj.Responsible = null;
MachineDetailsState.addSubmitObj.CurrentProgress = null;
MachineDetailsState.addSubmitObj.LatestProgress = null;

MachineDetailsState.updateSubmitObj = new Object();
MachineDetailsState.updateSubmitObj.ID = null;
MachineDetailsState.updateSubmitObj.CustomerID = null;
MachineDetailsState.updateSubmitObj.classify = null;
MachineDetailsState.updateSubmitObj.Model = null;
MachineDetailsState.updateSubmitObj.SN = null;
MachineDetailsState.updateSubmitObj.ContractNO = null;
MachineDetailsState.updateSubmitObj.InstalledTime = null;
MachineDetailsState.updateSubmitObj.Status = null;
MachineDetailsState.updateSubmitObj.Responsible = null;
MachineDetailsState.updateSubmitObj.CurrentProgress = null;
MachineDetailsState.updateSubmitObj.LatestProgress = null;

MachineDetailsState.modelExpiredObj = {
    "2": ["EPS150", "MPS150"],
    "3": ["SUMMIT", "T200", "PA200"],
    "4": ["CM300"]
};

$(function(){
    pageStyle(Number($('#currentPage').html()), Number($('#allPage').html()), "");
    if($("#queryType_input").val() == "common" || $("#queryType_input").val() == "singleSelect"){
        $('.select2').hide();
    }else {
        $('.select2').show();
    }

    /* awesomplete */
    var comboplete = new Awesomplete('#global_add_update_module_add input.form-control.dropdown-input', {
        minChars: 0,
        list: ["交付", "尾款", "完结"]
    });
    $('#global_add_update_module_add button.awesomplete_btn').click(function(){
        if (comboplete.ul.childNodes.length === 0) {
            comboplete.minChars = 0;
            comboplete.evaluate();
        }
        else if (comboplete.ul.hasAttribute('hidden')) {
            comboplete.open();
        }
        else {
            comboplete.close();
        }
    });

    var comboplete2 = new Awesomplete('#global_add_update_module_update input.form-control.dropdown-input', {
        minChars: 0,
        list: ["交付", "尾款", "完结"]
    });
    $('#global_add_update_module_update button.awesomplete_btn').each(function(i, el){
        el.addEventListener("click", function() {
            if (comboplete2.ul.childNodes.length === 0) {
              comboplete2.minChars = 0;
              comboplete2.evaluate();
            }
            else if (comboplete2.ul.hasAttribute('hidden')) {
              comboplete2.open();
            }
            else {
              comboplete2.close();
            }
        });
    });

    // 判断装机时间
    $("td.td_Status").each(function(){
        var iVal = $(this).data("ivalue");
        if(iVal == null || iVal == "" || iVal == "--"){
            iVal = "未选择状态";
        }else if(iVal == "1"){
            iVal = _.findKey(MachineDetailsState.statusNumMap, function(v, k, col) { return v == iVal; });
            var iInstalledTime = $(this).siblings(".td_InstalledTime").text();
            var iModel = $(this).siblings(".td_Model").text();
            // var days = _.findKey(MachineDetailsState.modelExpiredObj, function(v, k, col) {
            //     return _.find(v, function(vv, kk, coll) { return iModel.indexOf(vv) > -1; });
            // });
            var days = _.findKey(MachineDetailsState.modelExpiredObj, function(v, k, col) {
                return !_.isNil(_.find(v, function(vv, kk, coll) { return iModel.indexOf(vv) > -1; }));
            });
            if(globalDateDataHandle(iInstalledTime, "") === ""){
                $(this).addClass("cell_yellow").siblings(".td_InstalledTime").addClass("cell_yellow").parent().addClass("line_font_yellow");
            }else{
                if(globalCalcTimeDiff(globalGetToday(false), iInstalledTime, true).replace("天", "") > (days - 1)){
                    $(this).addClass("cell_yellow").siblings(".td_InstalledTime").addClass("cell_yellow").parent().addClass("line_font_yellow");
                }
            }
        }else{
            iVal = _.findKey(MachineDetailsState.statusNumMap, function(v, k, col) { return v == iVal; });
        }
        $(this).text(iVal).attr("title", iVal);
    });

    // 负责人、最新进展
    $("td.td_Responsible").each(function(){
        var iVal = $(this).attr("title");
        if(iVal == null || iVal == "" || iVal == "--"){
            iVal = "未填写";
        }
        $(this).text(iVal);
    });
    $("td.td_LatestProgress").each(function(){
        var iVal = $(this).data("ivalue");
        var iText, iDate;
        if(iVal == null || iVal == "" || iVal == "--"){
            iText = "未填写";
            iDate = "未填写";
        }else if(typeof iVal == "object"){
            iText = iVal[0].CurrentProgress;
            iDate = iVal[0].Date;      
        }else if(typeof iVal == "string"){
            iText = JSON.parse(iVal)[0].CurrentProgress;
            iDate = JSON.parse(iVal)[0].Date;
        }
        $(this).text(iText).attr("title", "详情："+iText+"  时间："+iDate);
    });

    /*//根据装机时间和拜访时间标红
    function fun_submit(visit_time,t){
        var date1 = new Date(visit_time);
        var date2 = new Date(date1);
        date2.setDate(date1.getDate()+t);
        var times = date2.getFullYear()+"-"+(date2.getMonth()+1)+"-"+date2.getDate();
        return times;
    }
    function changeRed(thetime,tr){
        var d=new Date(Date.parse(thetime.replace(/-/g,"/")));
        //今天时间，标准
        var curDate=new Date();
        var da=d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate();
        var cur=curDate.getFullYear() + '-' + (curDate.getMonth() + 1) + '-' + curDate.getDate(); 
        if(d >curDate){
        }else{
            tr.find(".CustomerUnit").css('color','red');
        }
    }

    for(var  i= 1 ;i<$("#global_table_style tr").length;i++){
         var tr=$("#global_table_style tr").eq(i);
         var CustomerLevel =tr.find(".CustomerLevel").text();
            var InstalledTime =tr.find(".InstalledTime").text();
            var VisitTime = tr.find(".VisitTime").text();
            if(VisitTime == '' || VisitTime == '--' ){
                if(InstalledTime != '' && InstalledTime != '--' ){
                    var visit_time = InstalledTime;
                }else{
                    var visit_time = '';
                    return;
                }
            }else{
                var  visit_time  = VisitTime ;
            }
            var VisitTime_A = fun_submit(visit_time,75);
            var VisitTime_B = fun_submit(visit_time,150);
            var VisitTime_C = fun_submit(visit_time,300);
        if(CustomerLevel == 'A'){
                //最后一次拜访时间——要判断的时间
            changeRed(VisitTime_A,tr);
        }else if(CustomerLevel == 'B'){
            changeRed(VisitTime_B,tr);   
        }else if(CustomerLevel == 'C'){
            changeRed(VisitTime_C,tr);      
        }
    }*/
});

/*添加修改究极整合*/
$(document).on("click", "[data-targetmodule]", function(){
    var targetmodule = $(this).data("targetmodule");
    if(targetmodule == "add"){
        $("#global_add_update_module_cover, #global_add_update_module_add").slideDown(200);
        $("[id^='add_infomation_']").each(function(){
            $(this).val("");
        });
        $("#global_add_update_module_add div.append_line_div tbody").empty();
    }else if(targetmodule == "update"){
        $("#global_add_update_module_cover, #global_add_update_module_update").slideDown(200);
        MachineDetailsState.updateSubmitObj.ID = $(this).attr("value");
        var tr = $(this).parent(), str = "";
        MachineDetailsState.updateSubmitObj.CustomerID = tr.find(".td_CustomerID").text();
        $("[id^='update_infomation_']").each(function(){
            var subClassName = $(this).attr("id").replace("update_infomation_", "td_");
            if($(this).is("#update_infomation_Status")){
                var newVal = globalDateDataHandle(tr.find("."+subClassName).data("ivalue"), "");
                var iiStatus = _.findKey(MachineDetailsState.statusNumMap, function(v, k, collection) { return v == newVal.toString(); });
                if(_.isNil(iiStatus)){
                    iiStatus = "";
                }
                $(this).val(iiStatus);
                return true;
            }
            $(this).val(globalDateDataHandle(tr.find("."+subClassName).attr("title"), ""));
        });
        var iCurrentProgress = tr.find(".td_CurrentProgress").text();
        if(_.isNil(iCurrentProgress) || iCurrentProgress == ""){
            str = "";
        }else{
            JSON.parse(tr.find(".td_CurrentProgress").text()).map(function(v, i, arr){
                if(i > 0){
                  str+='<tr>'+
                    '<td class="xuhao" value="'+v.ID+'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span><span class="span_num">'+i+'</span></td>'+
                    '<td class="progress_detail" contenteditable="true">'+v.CurrentProgress+'</td>'+
                    '<td class="progress_detail_time"><input type="date" class="form-control" value="'+v.Date+'"></td>'+
                  '</tr>';
                }
            });
        }
        $("#global_add_update_module_update div.append_line_div tbody").empty().append(str);
    }
});
// 关闭
$(document).on("click", ".global_add_update_module_title>span.glyphicon, .global_add_update_module_cancel", function(){
    var parents = $(this).parents("[data-parents='addANDupdate']");
    $("#global_add_update_module_cover").slideUp(200);
    parents.slideUp(200);
    if(parents.data("classify") == "add"){
        for(var k in MachineDetailsState.addSubmitObj){
            MachineDetailsState.addSubmitObj[k] = null;
        }
    }else if(parents.data("classify") == "update"){
        for(var kk in MachineDetailsState.updateSubmitObj){
            MachineDetailsState.updateSubmitObj[kk] = null;
        }
    }
});
// 提交
$(document).on("click", ".global_add_update_module_submit", function(){
    var parents = $(this).parents("[data-parents='addANDupdate']");
    var iThat = $(this);
    var submitData = {};
    var imessage;
    if(parents.data("classify") == "add"){
        for(var kk in MachineDetailsState.addSubmitObj){
            if(kk=="classify"){
                MachineDetailsState.addSubmitObj[kk] = "Add";
                continue;
            }
            if(kk=="CustomerID" || kk=="CurrentProgress" || kk=="LatestProgress"){
                continue;
            }
            if(kk=="Status"){
                MachineDetailsState.addSubmitObj[kk] = _.find(MachineDetailsState.statusNumMap, function(v, k, collection) { return k == $("#add_infomation_Status").val().trim(); });
                continue;
            }
            MachineDetailsState.addSubmitObj[kk] = $("#add_infomation_"+kk).val();
        }
        // 表单验证
        for(var kkk in MachineDetailsState.addSubmitObj){
            MachineDetailsState.addSubmitObj[kkk] = globalDataHandle(MachineDetailsState.addSubmitObj[kkk], "").toString().trim();
            if(kkk=="CustomerID"&&MachineDetailsState.addSubmitObj[kkk]==""){
                $.MsgBox_Unload.Alert("提示","未选择客户单位！");
                return false;
            }
            if(kkk=="Status"&&MachineDetailsState.addSubmitObj[kkk]==""){
                $.MsgBox_Unload.Alert("提示","项目状态未填或错误！");
                return false;
            }
        }

        var trs = $("#global_add_update_module_add .append_line_div tbody>tr");
        if(trs.length == 0){
          $.MsgBox_Unload.Alert("提示","请至少添加一条详情！");
          return false;
        }
        if($("#global_add_update_module_add .append_line_div tbody>tr:last").find(".progress_detail").text().trim() == ""){
          $.MsgBox_Unload.Alert("提示","最新进展未填写内容！");
          return false;
        }
        eouluGlobal.C_btnDisabled(iThat, true, "正在提交...");
        var CurrentProgressObj = [];
        trs.each(function(){
          var item = {};
          item.CurrentProgress = $(this).find(".progress_detail").text().trim();
          item.Date = $(this).find(".progress_detail_time>input").val();
          CurrentProgressObj.push(item);
        });
        var LatestProgressObj = [];
        LatestProgressObj.push(_.last(CurrentProgressObj));
        MachineDetailsState.addSubmitObj.LatestProgress = JSON.stringify(LatestProgressObj);
        MachineDetailsState.addSubmitObj.CurrentProgress = JSON.stringify(CurrentProgressObj);

        submitData = MachineDetailsState.addSubmitObj;
        imessage = "添加";
    }else if(parents.data("classify") == "update"){
        for(var jj in MachineDetailsState.updateSubmitObj){
            if(jj=="classify"){
                MachineDetailsState.updateSubmitObj[jj] = "Modify";
                continue;
            }
            if(jj=="ID" || jj=="CustomerID" || jj=="CurrentProgress" || jj=="LatestProgress"){
                continue;
            }
            if(jj=="Status"){
                MachineDetailsState.updateSubmitObj[jj] = _.find(MachineDetailsState.statusNumMap, function(v, k, collection) { return k == $("#update_infomation_Status").val().trim(); });
                continue;
            }
            MachineDetailsState.updateSubmitObj[jj] = $("#update_infomation_"+jj).val();
        }
        // 表单验证
        for(var jjj in MachineDetailsState.updateSubmitObj){
            MachineDetailsState.updateSubmitObj[jjj] = globalDataHandle(MachineDetailsState.updateSubmitObj[jjj], "").toString().trim();
            if(jjj=="CustomerID"&&MachineDetailsState.updateSubmitObj[jjj]==""){
                $.MsgBox_Unload.Alert("提示","未选择客户单位！");
                return false;
            }
            if(jjj=="Status"&&MachineDetailsState.updateSubmitObj[jjj]==""){
                $.MsgBox_Unload.Alert("提示","项目状态未填或错误！");
                return false;
            }
        }

        var trs2 = $("#global_add_update_module_update .append_line_div tbody>tr");
        if(trs2.length == 0){
          $.MsgBox_Unload.Alert("提示","请至少添加一条详情！");
          return false;
        }
        if($("#global_add_update_module_update .append_line_div tbody>tr:last").find(".progress_detail").text().trim() == ""){
          $.MsgBox_Unload.Alert("提示","最新进展未填写内容！");
          return false;
        }
        eouluGlobal.C_btnDisabled(iThat, true, "正在提交...");
        var CurrentProgressObj2 = [];
        trs2.each(function(){
          var item = {};
          item.CurrentProgress = $(this).find(".progress_detail").text().trim();
          item.Date = $(this).find(".progress_detail_time>input").val();
          CurrentProgressObj2.push(item);
        });
        var LatestProgressObj2 = [];
        LatestProgressObj2.push(_.last(CurrentProgressObj2));
        MachineDetailsState.updateSubmitObj.LatestProgress = JSON.stringify(LatestProgressObj2);
        MachineDetailsState.updateSubmitObj.CurrentProgress = JSON.stringify(CurrentProgressObj2);

        submitData = MachineDetailsState.updateSubmitObj;
        imessage = "修改";
    }

    $.ajax({
        type: "POST",
        url: "MachineDetailsOperate",
        data: submitData,
        dataType: "json"
    }).then(function(data){
        $.MsgBox.Alert("提示", imessage+"成功！");
    },function(){
        $.MsgBox_Unload.Alert("提示","服务器繁忙！");
    }).always(function(){
        eouluGlobal.C_btnAbled(iThat, true, "提交");
    });
});
/*添加修改究极整合 end*/
// 添加一条进展详情
$(".global_add_update_module span.append_line").click(function(){
    var tbody = $(this).parent().next().find("tbody");
    var len = tbody.children("tr").length + 1;
    var str = '<tr>'+
        '<td class="xuhao"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span><span class="span_num">'+len+'</span></td>'+
        '<td class="progress_detail" contenteditable="true"></td>'+
        '<td class="progress_detail_time"><input type="date" class="form-control" value="'+globalGetToday(false)+'"></td>'+
      '</tr>';
    tbody.append(str);
});

// 删除一条进展详情
$(document).on("click", "div.append_line_div .xuhao>span.glyphicon", function(){
    var tbody = $(this).parents("tbody");
    $(this).parent().parent().remove();
    tbody.children("tr").each(function(i, el){
      $(el).find("span.span_num").text(i+1);
    });
});

// 客户搜索
function addSearchCustomer(){
    var add = $("#add_infomation_CustomerUnit").val();
    if(add == "") return false;
    var CustomerName = add.trim();
    if(CustomerName == "" || CustomerName == "--"){
        $.MsgBox_Unload.Alert("提示", "客户单位搜索值为空或格式错误！");
        return false;
    }
    eouluGlobal.C_getCustomerUnitInfo(CustomerName, function(data){
        if(data.length > 1){
            var str = '';
            data.map(function(v, i, arr){
                if(i > 0){
                    str+='<option data-iid="'+v.ID+'" value="'+v.CustomerName+'" data-contact="'+v.Contact+'">'+v.CustomerName+'&nbsp;:&nbsp;'+v.Contact+'</option>';
                }
            });
            $("#add_infomation_CustomerUnit").siblings("select[name='CustomerUnit_select']").empty().append(str).slideDown(150);
        }
    },function(){
        $.MsgBox_Unload.Alert("提示", "网络繁忙！");
    },null);
}
function updateSearchCustomer(){
    var update = $("#update_infomation_CustomerUnit").val();
    if(update == "") return false;
    var CustomerName = update.trim();
    if(CustomerName == "" || CustomerName == "--"){
        $.MsgBox_Unload.Alert("提示", "客户单位搜索值为空或格式错误！");
        return false;
    }
    eouluGlobal.C_getCustomerUnitInfo(CustomerName, function(data){
        if(data.length > 1){
            var str = '';
            data.map(function(v, i, arr){
                if(i > 0){
                    str+='<option data-iid="'+v.ID+'" value="'+v.CustomerName+'" data-contact="'+v.Contact+'">'+v.CustomerName+'&nbsp;:&nbsp;'+v.Contact+'</option>';
                }
            });
            $("#update_infomation_CustomerUnit").siblings("select[name='CustomerUnit_select']").empty().append(str).slideDown(150);
        }
    },function(){
        $.MsgBox_Unload.Alert("提示", "网络繁忙！");
    },null);
}

$("#add_infomation_CustomerUnit").on("input propertychange", _.debounce(addSearchCustomer, 350, { 'maxWait': 1000 }));
$("#update_infomation_CustomerUnit").on("input propertychange", _.debounce(updateSearchCustomer, 350, { 'maxWait': 1000 }));

/*选择客户*/
$(document).on("click", "select[name='CustomerUnit_select']>option", function(e){
    e.stopPropagation();
    $(this).parent().siblings("input[type='text']").val($(this).val()).parents("[data-parents='addANDupdate']").find("input[data-customername]").val($(this).data("contact"));
    $(this).parent().slideUp(150);
    var iclassify = $(this).parents("[data-parents='addANDupdate']").data("classify");
    var CustomerID = $(this).data("iid");
    if(iclassify == "add"){
        MachineDetailsState.addSubmitObj.CustomerID = CustomerID;
    }else if(iclassify == "update"){
        MachineDetailsState.updateSubmitObj.CustomerID = CustomerID;
    }
});
// 客户搜索清空
$("div.form-group.has-feedback>div.col-sm-9>.form-control-feedback").click(function(){
    $(this).prev().val("").siblings("select[name='CustomerUnit_select']").slideUp(150);
    var iclassify = $(this).parents("[data-parents='addANDupdate']").data("classify");
    if(iclassify == "add"){
        MachineDetailsState.addSubmitObj.CustomerID = null;
        $("#add_infomation_CustomerName").val("");
    }else if(iclassify == "update"){
        MachineDetailsState.updateSubmitObj.CustomerID = null;
        $("#update_infomation_CustomerName").val("");
    }
});
// 点击document收起select
$(document).on("click", function(){
    $("select[name='CustomerUnit_select']").slideUp(150);
});
/*2018-09-27更新 end*/