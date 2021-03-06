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

var statusNumMap = {
  "交付": "1",
  "尾款": "2",
  "完结": "3",
};

// 翻页组件按钮逻辑
// flag 为按钮ID后缀  如 pageStyle(CurrentPage, pageCount, "2");
function pageStyle(currentPage, pageCounts, flag){
    flag = flag || "";
    if(pageCounts == 1){
        $("#fistPage"+flag+", #upPage"+flag+", #nextPage"+flag+", #lastPage"+flag+", #Gotojump"+flag).prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
    }else if(currentPage == 1){
        $("#fistPage"+flag+", #upPage"+flag).prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
        $("#lastPage"+flag+", #nextPage"+flag+", #Gotojump"+flag).prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }else if(currentPage == pageCounts){
        $("#lastPage"+flag+", #nextPage"+flag).prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
        $("#fistPage"+flag+", #upPage"+flag+", #Gotojump"+flag).prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }else{
        $("#lastPage"+flag+", #nextPage"+flag+", #fistPage"+flag+", #upPage"+flag+", #Gotojump"+flag).prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
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
    $('#search').val('cancel');
    $('input[name="searchContent1"]').val('');
    $('input[name="searchContent2"]').val('');
    $('#top_text_from').submit();
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

/*********************添加装机进展信息************************/
/* 添加装机进展信息 */
$(document).on("click","#contract_add_submit",function(){
      var Classify="Add";
      var Status;
      Status = _.find(statusNumMap, function(v, k, collection) { return k == $("#add_info_Status").val().trim(); });
      if(Status == undefined){
        Status = $("#add_info_Status").val().trim();
      }
      if(Status == "" || Status == "--"){
        $.MsgBox_Unload.Alert("提示","项目状态未填或未选！");
        return false;
      }
      var InstalledTime = $('#add_info_InstalledTime').val();
      var Customer = $('#add_info_Customer').val().trim();
      if(Customer == "" || Customer == "--"){
        $.MsgBox_Unload.Alert("提示","客户未填！");
        return false;
      }
      var ResponsibleAndProcess = $('#add_info_ResponsibleAndProcess').val().trim();
      var trs = $(".contract_add .appendp_p_div tbody>tr");
      if(trs.length == 0){
        $.MsgBox_Unload.Alert("提示","请至少添加一条详情！");
        return false;
      }
      if($(".contract_add .appendp_p_div tbody>tr:last").find(".progress_detail").text().trim() == ""){
        $.MsgBox_Unload.Alert("提示","最新进展未填写内容！");
        return false;
      }
      var CurrentProgressObj = [];
      trs.each(function(){
        var item = {};
        item.CurrentProgress = $(this).find(".progress_detail").text().trim();
        item.Date = $(this).find(".progress_detail_time>input").val();
        CurrentProgressObj.push(item);
      });
      var LatestProgressObj = [];
      LatestProgressObj.push(_.last(CurrentProgressObj));
      var LatestProgress = JSON.stringify(LatestProgressObj);
      var CurrentProgress = JSON.stringify(CurrentProgressObj);
      var iThat = $(this);
     $.ajax({
          // type: 'get',
          type: 'POST',
          url: 'HardwareOperate',
          data: {
              InstalledTime : InstalledTime,
              Customer : Customer,
              Status : Status,
              ResponsibleAndProcess : ResponsibleAndProcess,
              Classify : Classify,
              LatestProgress: LatestProgress,
              CurrentProgress: CurrentProgress
          },
          dataType: 'json',
          beforeSend: function(XMLHttpRequest){
              eouluGlobal.C_btnDisabled(iThat, true, "正在提交...");
          },
          success: function (data) {
              $.MsgBox.Alert('提示','添加成功');
              $("#contract_add_cancel").trigger("click");
          },
          error: function () {
              $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
          },
          complete: function(XMLHttpRequest, textStatus){
              if(textStatus=="success"){
              }
              eouluGlobal.C_btnAbled(iThat, true, "提交");
          }
      });   
  });
    
/* 修改装机进展信息 */
$(document).on("click",".contract-edit",function(){
    $(".MailBar_cover_color, .contract_update").slideDown(200);
    var tr = $(this).parent();
    $("[id^='update_info_']").each(function(){
        var subClassName = $(this).attr("id").replace("update_info_", "td_");
        var newVal = globalDataHandle(tr.find("."+subClassName).text(), "");
        $(this).val(newVal);
    });
    var ID = tr.find('td').eq(0).attr("value");
    $(".contract_update .contract_update_title").attr("value",ID);  //在修改页面保存当前行的ID信息
    var CurrentProgress = tr.find(".td_LatestProgress").data("currentprogress");
    // console.log(typeof CurrentProgress);
    // console.log(CurrentProgress);
    var str = '';
    _.forEach(CurrentProgress, function(value, index, collection) {
      if(index > 0){
        console.log("大于0");
        str+='<tr>'+
          '<td class="xuhao" value="'+value.ID+'"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span><span class="span_num">'+index+'</span></td>'+
          '<td class="progress_detail" contenteditable="true">'+value.CurrentProgress+'</td>'+
          '<td class="progress_detail_time"><input type="date" class="form-control" value="'+value.Date+'"></td>'+
        '</tr>';
      }
    });
    $(".contract_update .appendp_p_div tbody").empty().append(str);
});
    
/*  提交修改后的信息  */
$('#contract_update_submit').click(function () {
    var ID = $(".contract_update .contract_update_title").attr("value");
     var Classify = "Modify";
     var Status;
     Status = _.find(statusNumMap, function(v, k, collection) { return k == $("#update_info_Status").val().trim(); });
     if(Status == undefined){
       Status = $("#update_info_Status").val().trim();
     }
     if(Status == "" || Status == "--"){
       $.MsgBox_Unload.Alert("提示","项目状态未填或未选！");
       return false;
     }
     var InstalledTime = $('#update_info_InstalledTime').val();
     var Customer = $('#update_info_Customer').val().trim();
     if(Customer == "" || Customer == "--"){
       $.MsgBox_Unload.Alert("提示","客户未填！");
       return false;
     }
     var ResponsibleAndProcess = $('#update_info_ResponsibleAndProcess').val().trim();
     var trs = $(".contract_update .appendp_p_div tbody>tr");
     if(trs.length == 0){
       $.MsgBox_Unload.Alert("提示","请至少添加一条详情！");
       return false;
     }
     if($(".contract_update .appendp_p_div tbody>tr:last").find(".progress_detail").text().trim() == ""){
       $.MsgBox_Unload.Alert("提示","最新进展未填写内容！");
       return false;
     }
     var CurrentProgressObj = [];
     trs.each(function(){
       var item = {};
       item.CurrentProgress = $(this).find(".progress_detail").text().trim();
       item.Date = $(this).find(".progress_detail_time>input").val();
       CurrentProgressObj.push(item);
     });
     var LatestProgressObj = [];
     LatestProgressObj.push(_.last(CurrentProgressObj));
     var LatestProgress = JSON.stringify(LatestProgressObj);
     var CurrentProgress = JSON.stringify(CurrentProgressObj);
     var iThat = $(this);
    $.ajax({
         // type: 'get',
         type: 'POST',
         url: 'HardwareOperate',
         data: {
            ID: ID,
             InstalledTime : InstalledTime,
             Customer : Customer,
             Status : Status,
             ResponsibleAndProcess : ResponsibleAndProcess,
             Classify : Classify,
             LatestProgress: LatestProgress,
             CurrentProgress: CurrentProgress
         },
         dataType: 'json',
         beforeSend: function(XMLHttpRequest){
             eouluGlobal.C_btnDisabled(iThat, true, "正在提交...");
         },
         success: function (data) {
             $.MsgBox.Alert('提示','修改成功');
             $("#contract_update_cancel").trigger("click");
         },
         error: function () {
             $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
         },
         complete: function(XMLHttpRequest, textStatus){
             if(textStatus=="success"){
             }
             eouluGlobal.C_btnAbled(iThat, true, "提交");
         }
     });
}); 

//点击添加
function AddContract() {
    $("[id^='add_info_']").each(function(){
        $(this).val("");
        if($(this).is("#add_info_InstalledTime")){
            $(this).val(globalGetToday(false));
        }
    });
    $(".MailBar_cover_color, .contract_add").slideDown(200);
}

//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function () {
    window.location.reload();
});

//点击关闭弹窗
$(".contract_add_title>span.glyphicon, #contract_add_cancel").click(function(){
  $(".MailBar_cover_color, .contract_add").slideUp(200);
});

$(".contract_update_title>span.glyphicon, #contract_update_cancel").click(function(){
  $(".MailBar_cover_color, .contract_update").slideUp(200);
});

// 添加一条进展详情
$("span.add_line_p, span.update_line_p").click(function(){
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
$(document).on("click", ".appendp_p_div .xuhao>span.glyphicon", function(){
    var tbody = $(this).parents("tbody");
    $(this).parent().parent().remove();
    tbody.children("tr").each(function(i, el){
      $(el).find("span.span_num").text(i+1);
    });
});

/****************** 跳页 **********************/
function FistPage(arg) {
    if(arg.split('?')[0]=='GetHardwareRoute'){
        $('#search').val('search');
        $("#top_text_from").attr("action", arg + "1");
        $('#top_text_from').submit();
    }else{ 
        window.location.href = arg + "1";
    } 
}
function UpPage(arg) {
    if(arg.split('?')[0]=='GetHardwareRoute'){
         $('#search').val('search');
         $("#top_text_from").attr("action", arg);
          $('#top_text_from').submit();
    }else{ 
        window.location.href = arg;
    } 
}
function NextPage(arg) {
    if(arg.split('?')[0]=='GetHardwareRoute'){
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
    if(arg.split('?')[0]=='GetHardwareRoute'){
        $('#search').val('search');
        $("#top_text_from").attr("action", arg + jumpNumber);
        $('#top_text_from').submit();
    }else{ 
        window.location.href = arg + jumpNumber;
    } 
}
function LastPage(arg) {
    var jumpNumber = parseInt($('#allPage').html());
    if(arg.split('?')[0]=='GetHardwareRoute'){
        $('#search').val('search');
        $("#top_text_from").attr("action", arg + jumpNumber);
        $('#top_text_from').submit();
    }else{ 
        window.location.href = arg + jumpNumber;
    } 
}
$(function() {
    $("td.td_Status").each(function(){
      var iText = $(this).attr("value");
      switch(iText){
        case "1":
          $(this).text("交付");
          break;
        case "2":
          $(this).text("尾款");
          break;
        case "3":
          $(this).text("完结");
          break;
        default:
        $(this).text(iText);
      }
    });

    // 最新进展填充
    $("td.td_LatestProgress").each(function(){
      var LatestProgress = $(this).data("latestprogress");
      // console.log(typeof LatestProgress);
      // console.log(LatestProgress);
      // console.log(LatestProgress == "");
      if(typeof LatestProgress == "string"){
        if(LatestProgress == ""){
          $(this).text("未填写最新进展").attr("title", "未填写最新进展");
        }else{
          $(this).text(LatestProgress).attr("title", LatestProgress);
        }
      }else{
        var str = LatestProgress[0].Date+(LatestProgress[0].Date == "" ? "" : "：")+LatestProgress[0].CurrentProgress;
        $(this).text(str).attr("title", str);
      }
    });

    pageStyle(Number($('#currentPage').html()), Number($('#allPage').html()), "");
    var comboplete = new Awesomplete('.contract_add input.form-control.dropdown-input', {
        minChars: 0,
        list: ["交付", "尾款", "完结"]
    });
    $('.contract_add button.awesomplete_btn').click(function(){
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

    var comboplete2 = new Awesomplete('.contract_update input.form-control.dropdown-input', {
        minChars: 0,
        list: ["交付", "尾款", "完结"]
    });
    $('.contract_update button.awesomplete_btn').each(function(i, el){
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
});

/*********模板 删除*******/
  $(document).on("click",".del",function () {
      var thisList = $(this).parent().parent();
      var ID = thisList.find("td").eq(0).attr("value");
      $(".yejiao").attr("value",ID);
      console.log(ID);
      $.ajax({
          type : 'get',
          url : "QualityDelete",
          data : {  
            ID:ID
          },
          dataType : 'json',
          success : function (data) {                                       
            $.MsgBox.Alert('提示','删除成功');
                $('.MailBar_cover_color').hide();
                $('.hidePdf').hide();
          },
          error : function () {
              $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
          }
      });
       var  that = $(this);
       $.MsgBox.Confirm('提示','确认要删除吗？',function(){
           that.parents("tr").remove();
      });  
 });

