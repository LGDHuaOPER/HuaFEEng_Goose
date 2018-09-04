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
