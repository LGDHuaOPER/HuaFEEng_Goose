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
$(document).on("click","#add_submit",function(){
        var InstalledTime=$('.contract_add input[name="InstalledTime"]').val();
        var Customer=$('.contract_add input[name="Customer"]').val();
        var Status;
        var ResponsibleAndProcess=$('.contract_add input[name="ResponsibleAndProcess"]').val();
        var Classify="Add";
        
        if($('.contract_add input[name="Status"]').val()=="交付"){
            Status="1";
        }else if($('.contract_add input[name="Status"]').val()=="尾款"){
            Status="2";
        }else if($('.contract_add input[name="Status"]').val()=="完结"){
            Status="3";
        }

        //点击提交以后避免用户重复提交
    $("#add_submit").attr("disabled","disabled");
    $("#add_submit").css({
        "background":"#dddddd",
        "color":"#808080",
        "border":"none",
        "box-shadow":"0 0 0 0 #f8fcfd"
    });
    $.MsgBox.Prompt("提示", "冲锋鸡正在处理中......请等待");
        
       $.ajax({
            type : 'get',
            url : 'HardwareOperate',
            data : {
                InstalledTime : InstalledTime,
                Customer : Customer,
                Status : Status,
                ResponsibleAndProcess : ResponsibleAndProcess,
                Classify : Classify,
            },
            dataType : 'json',
            success : function (data) {
                console.log(data);
                $("#mb_box,#mb_con").remove();
                $.MsgBox.Alert('提示','添加成功');
                $('.MailBar_cover_color').hide();
                $('.contract_add').hide();
            },
            error : function () {
                $("#mb_box,#mb_con").remove();
                $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
            }
        }); 
                
    });
    
    
/* 修改装机进展信息 */
$(document).on("click",".contract-edit",function(){ 
    var tr=$(this).parent();
    $('.contract_update input[name="InstalledTime"]').val(tr.find('td').eq(3).text());
    $('.contract_update input[name="Customer"]').val(tr.find('td').eq(2).text());
    $('.contract_update input[name="Status"]').val(tr.find('td').eq(4).text());
    $('.contract_update input[name="ResponsibleAndProcess"]').val(tr.find('td').eq(5).text());
    var  ID = tr.find('td').eq(0).attr("value");
    $(".contract_update .contract_title").attr("value",ID);  //在修改页面保存当前行的ID信息
    $('.MailBar_cover_color').show();
    $('.contract_update').show();             
});
    
    /*  提交修改后的信息  */
    $('#update_submit').click(function () {
        var InstalledTime = $('.contract_update input[name="InstalledTime"]').val();
        var Customer = $('.contract_update input[name="Customer"]').val();
        var Status;
        var ResponsibleAndProcess = $('.contract_update input[name="ResponsibleAndProcess"]').val();
        var  ID = $(".contract_update .contract_title").attr("value");
        var Classify="Modify";
        
        if($('.contract_update input[name="Status"]').val()=="交付"){
            Status="1";
        }else if($('.contract_update input[name="Status"]').val()=="尾款"){
            Status="2";
        }else if($('.contract_update input[name="Status"]').val()=="完结"){
            Status="3";
        }
        
        //点击提交以后避免用户重复提交
        $("#update_submit").attr("disabled","disabled");
        $("#update_submit").css({
            "background":"#dddddd",
            "color":"#808080",
            "border":"none",
            "box-shadow":"0 0 0 0 #f8fcfd"
        });
        $.MsgBox.Prompt("提示", "冲锋鸡正在处理中......请等待");
          $.ajax({
              type : 'get',
              url : 'HardwareOperate',
              data : {
                  ID : ID,
                  InstalledTime : InstalledTime,
                  Customer : Customer,
                  Status : Status,
                  ResponsibleAndProcess : ResponsibleAndProcess,
                  Classify : Classify,
              },
              dataType : 'json',
              success : function (data) {
                  console.log(data);
                  $("#mb_box,#mb_con").remove();
                  $.MsgBox.Alert('提示','修改成功');
                  $('.MailBar_cover_color').hide();
                  $('.contract_add').hide();
              },
              error : function () {
                  $("#mb_box,#mb_con").remove();
                  $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
              }
          }); 
    }); 

//点击添加
function AddContract() {
    /*默认当前日期 */
    // var ddd = new Date();
    // var day =ddd.getDate();
    // if(ddd.getMonth()<10){
    // var month = "0"+(ddd.getMonth()+1); 
    // }
    // if(ddd.getDate()<10){
    //  day = "0"+ddd.getDate(); 
    // }
    // var datew = ddd.getFullYear()+"-"+month+"-"+day;
    // datew = datew.toString();
    var datew = globalGetToday(false);
    $("#Date").val(datew);
    $('.MailBar_cover_color').show();
    $('.contract_add').show();
}

//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function () {
    window.location.reload();
});

//点击关闭
$('.contractAdd_close').click(function () {
  $('.MailBar_cover_color').hide();
  $('.contract_add').hide();
});

$('.contractUpdate_close').click(function () {
  $('.MailBar_cover_color').hide();
  $('.contract_update').hide();
});
$('#contract_close1').click(function () {
  $('.MailBar_cover_color').hide();
  $('.hidePdf').hide();
});

//点击取消
$('#add_cancel').click(function () {
  $('.MailBar_cover_color').hide();
  $('.contract_add').hide();
});

$('#update_cancel').click(function () {
  $('.MailBar_cover_color').hide();
  $('.contract_update').hide();
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
    pageStyle(Number($('#currentPage').html()), Number($('#allPage').html()), "");
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

