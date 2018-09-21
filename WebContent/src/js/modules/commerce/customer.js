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

var ProvinceandCity = {北京:["东城区","西城区","崇文区","宣武区","朝阳区","海淀区","丰台区","石景山区","房山区","通州区","顺义区","昌平区","大兴区","怀柔区","平谷区","门头沟区","密云县","延庆县"],天津:["和平区","河东区","河西区","南开区","河北区","红桥区","东丽区","西青区","北辰区","津南区","武清区","宝坻区","滨海新区","静海县","宁河县","蓟县"],上海:["黄浦区","卢湾区","徐汇区","长宁区","静安区","普陀区","闸北区","虹口区","杨浦区","闵行区","宝山区","嘉定区","浦东新区","金山区","松江区","青浦区","奉贤区","崇明县"],重庆:["渝中区","大渡口区","江北区","南岸区","北碚区","渝北区","巴南区","长寿区","双桥区","沙坪坝区","万盛区","万州区","涪陵区","黔江区","永川区","合川区","江津区","九龙坡区","南川区","綦江县","潼南县","荣昌县","璧山县","大足县","铜梁县","梁平县","开县","忠县","城口县","垫江县","武隆县","丰都县","奉节县","云阳县","巫溪县","巫山县"],河北:["石家庄","唐山","秦皇岛","邯郸","邢台","保定","张家口","承德","沧州","廊坊","衡水"],山西:["太原","大同","阳泉","长治","晋城","朔州","晋中","运城","忻州","临汾","吕梁"],辽宁:["沈阳","大连","鞍山","抚顺","本溪","丹东","锦州","营口","阜新","辽阳","盘锦","铁岭","朝阳","葫芦岛"],吉林:["长春","吉林","四平","辽源","通化","白山","松原","白城","延边朝鲜族自治州"],黑龙江:["哈尔滨","齐齐哈尔","鹤岗","双鸭山","鸡西","大庆","伊春","牡丹江","佳木斯","七台河","黑河","绥化","大兴安岭"],江苏:["南京","苏州","无锡","常州","镇江","南通","泰州","扬州","盐城","连云港","徐州","淮安","宿迁"],浙江:["杭州","宁波","温州","嘉兴","湖州","绍兴","金华","衢州","舟山","台州","丽水"],安徽:["合肥","芜湖","蚌埠","淮南","马鞍山","淮北","铜陵","安庆","黄山","滁州","阜阳","宿州","巢湖","六安","亳州","池州","宣城"],福建:["福州","厦门","莆田","三明","泉州","漳州","南平","龙岩","宁德"],江西:["南昌","景德镇","萍乡","九江","新余","鹰潭","赣州","吉安","宜春","抚州","上饶"],山东:["济南","青岛","淄博","枣庄","东营","烟台","潍坊","济宁","泰安","威海","日照","莱芜","临沂","德州","聊城","滨州","菏泽"],河南:["郑州","开封","洛阳","平顶山","安阳","鹤壁","新乡","焦作","濮阳","许昌","漯河","三门峡","南阳","商丘","信阳","周口","驻马店"],湖北:["武汉","黄石","十堰","荆州","宜昌","襄樊","鄂州","荆门","孝感","黄冈","咸宁","随州","恩施"],湖南:["长沙","株洲","湘潭","衡阳","邵阳","岳阳","常德","张家界","益阳","郴州","永州","怀化","娄底","湘西"],广东:["广州","深圳","珠海","汕头","韶关","佛山","江门","湛江","茂名","肇庆","惠州","梅州","汕尾","河源","阳江","清远","东莞","中山","潮州","揭阳","云浮"],海南:["海口","三亚"],四川:["成都","自贡","攀枝花","泸州","德阳","绵阳","广元","遂宁","内江","乐山","南充","眉山","宜宾","广安","达州","雅安","巴中","资阳","阿坝","甘孜","凉山"],贵州:["贵阳","六盘水","遵义","安顺","铜仁","毕节","黔西南","黔东南","黔南"],云南:["昆明","曲靖","玉溪","保山","昭通","丽江","普洱","临沧","德宏","怒江","迪庆","大理","楚雄","红河","文山","西双版纳"],陕西:["西安","铜川","宝鸡","咸阳","渭南","延安","汉中","榆林","安康","商洛"],甘肃:["兰州","嘉峪关","金昌","白银","天水","武威","酒泉","张掖","庆阳","平凉","定西","陇南","临夏","甘南"],青海:["西宁","海东","海北","海南","黄南","果洛","玉树","海西"],内蒙古:["呼和浩特","包头","乌海","赤峰","通辽","鄂尔多斯","呼伦贝尔","巴彦淖尔","乌兰察布","锡林郭勒盟","兴安盟","阿拉善盟"],广西:["南宁","柳州","桂林","梧州","北海","防城港","钦州","贵港","玉林","百色","贺州","河池","来宾","崇左"],西藏:["拉萨","那曲","昌都","林芝","山南","日喀则","阿里"],宁夏:["银川","石嘴山","吴忠","固原","中卫"],新疆:["乌鲁木齐","克拉玛依","吐鲁番","哈密","和田","阿克苏","喀什","克孜勒苏","巴音郭楞","昌吉","博尔塔拉","伊犁","塔城","阿勒泰"],香港:["香港岛","九龙东","九龙西","新界东","新界西"],澳门:["澳门半岛","离岛"],台湾:["台北","高雄","基隆","新竹","台中","嘉义","台南"]};
/**
 * Created by eoulu on 2017/3/29.
 */
 
// 导出提交用的Data
var exportData = new Object();
exportData.CustomerName = null;
exportData.EnglishName = null;
exportData.Contact = null;
exportData.ContactInfo = null;
exportData.CustomerDepartment = null;
exportData.Email = null;
exportData.ContactAddress = null;

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

// ----------------------------------------搜索框----------------------------------------------
if($('input[name="selected"]:checked').val()=='singleSelect'){
    $('.select-content').css('margin-left','33%');
}else{
    $('.select-content').css('margin-left','23%');

}
function Check(selected) {
	 if (selected == "single") {
	        $('.select2').hide();
	        $('.select-content').css('margin-left','33%');
	    } else {
	        $('.select2').show();
	        $('.select-content').css('margin-left','23%');
	    }
}
function Search() {
    $('#search').val('search');
    $('#top_text_from').attr("action","GetCustomerInfo2");
    $('#top_text_from').submit();
}
function Cancel() {
    $('#search').val('cancel');
    $('input[name="Contect1"]').val('');
    $('input[name="Contect2"]').val('');
    $('#top_text_from').attr("action","Customer");
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

// --------------------------------------------添加信息-----------------------------------------------
function AddContract() {
	$('.info_add input[type="text"').val('');
    $('.cover-color').show();
    $('.info_add').show();
}
$('.infoAdd_close').click(function () {
    $('.cover-color').hide();
    $('.info_add').hide();
});
$('.info_add .search').mousedown(function () {
    $('.info_add select[name="module"]').show();

});
$('.info_add .search').change(function () {
    $('.info_add select[name="module"]').hide();

});

$(document).on("click", ".info_add select[name='module'] option", function () {
    $('.info_add select[name="module"]').hide();
    $('.info_add .search').val($('.info_add select[name="module"]').text());
});

$('#add_submit').click(function () {
    var area = $('.info_add input[name="area"]').val().trim();
    var areaHook = 0;
    for(var kk in ProvinceandCity){
        if(area == kk){
            areaHook = 1;
            break;
        }
    }
    if(areaHook == 0){
        $.MsgBox_Unload.Alert("省份填写错误提示及示例", "内蒙古自治区填内蒙古，香港特别行政区填香港");
        return false;
    }
    var id = $('.info_add input[name="id"]').val();
    var customer_name = $('.info_add input[name="customer_name"]').val();
    var EnglishName = $('.info_add input[name="EnglishName"]').val();
    /*var customer_classify = $('.info_add input[name="customer_classify"]').val();*/
    var customer_classify = "null";
    var contact = $('.info_add input[name="contact"]').val();
    var contact_info1 = $('.info_add input[name="contact_info1"]').val();
    var contact_info2 = $('.info_add input[name="contact_info2"]').val();
    var contact_address = $('.info_add input[name="contact_address"]').val();
    var zip_code = $('.info_add input[name="zip_code"]').val();
    var fax_number = $('.info_add input[name="fax_number"]').val();
    var email = $('.info_add input[name="email"]').val();
    /*var shorthand_coding = $('.info_add input[name="shorthand_coding"]').val();*/
    var shorthand_coding = "null";
    //新增的三项
    var Website = $('.info_add input[name="Website"]').val();
    var customer_department = $('.info_add input[name="customer_department"]').val();
    var Englishcustomer_department = $('.info_add input[name="Englishcustomer_department"]').val();
    var CustomerLevel = $('.info_add #CustomerLevel').val();
    var City = $('.info_add  input[name="City"]').val();
    $.ajax({
        type: 'post',
        url: 'CustomerOperate',
        data: {
            id: id,
            customer_name: customer_name,
            EnglishName: EnglishName,
            customer_classify: customer_classify,
            contact: contact,
            contact_info1: contact_info1,
            contact_info2: contact_info2,
            contact_address: contact_address,
            area: area,
            zip_code: zip_code,
            fax_number: fax_number,
            email: email,
            shorthand_coding: shorthand_coding,
            Website:Website,
            CustomerDepartment:customer_department,
            DepartmentEnglish:Englishcustomer_department,
            classify:'新增',
            CustomerLevel:CustomerLevel,
            City :City
        },
        dataType: 'json',
        success: function (data) {
        	console.log(data);
            data = eval("(" + data + ")");
        	if(data.message){
                $.MsgBox.Alert("提示", "添加成功！");
        	}else{
                $.MsgBox.Alert('提示', "添加失败！");
        	}
        },
        error: function () {
            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});
$('#add_cancel').click(function () {
    $('.cover-color').hide();
    $('.info_add').hide();
});

//-------------------------------------------修改-----------------------------------------------
$('.edit').click(function () {
    var tr = $(this).parent();
    var info_update = $('.info_update');
    info_update.find('input[name="id"]').val($(this).attr('value'));
    info_update.find('input[name="customer_name"]').val(tr.find('.CustomerName').html());
    info_update.find('input[name="EnglishName"]').val(tr.find('.EnglishName').html());
    info_update.find('input[name="customer_classify"]').val(tr.find('.CustomerClassify').html());
    info_update.find('input[name="contact"]').val(tr.find('.Contact').html());
    info_update.find('input[name="contact_info1"]').val(tr.find('.ContactInfo1').html());
    info_update.find('input[name="contact_info2"]').val(tr.find('.ContactInfo2').html());
    info_update.find('input[name="contact_address"]').val(tr.find('.ContactAddress').html());
    info_update.find('input[name="area"]').val(tr.find('.Area').html());
    info_update.find('input[name="zip_code"]').val(tr.find('.ZipCode').html());
    info_update.find('input[name="fax_number"]').val(tr.find('.FaxNumber').html());
    info_update.find('input[name="email"]').val(tr.find('.Email').html());
    info_update.find('input[name="shorthand_coding"]').val(tr.find('.ShorthandCoding').html());
    info_update.find('input[name="Website"]').val(tr.find('.Website').html());
    info_update.find('input[name="customer_department"]').val(tr.find('.CustomerDepartment').html());
    info_update.find('input[name="Englishcustomer_department"]').val(tr.find('.DepartmentEnglish').html());
    info_update.find('input[name="City"]').val(tr.find('.City').html());
    info_update.find('#CustomerLevel').val(tr.find('td.CustomerLevel').html());
    $('.cover-color').show();
    info_update.show();
    var id = $(this).attr('value');
    $('.info_update input[name="id"]').val(id);
});


$('#update_submit').click(function () {
    var area = $('.info_update input[name="area"]').val().trim();
    var areaHook = 0;
    for(var kk in ProvinceandCity){
        if(area == kk){
            areaHook = 1;
            break;
        }
    }
    if(areaHook == 0){
        $.MsgBox_Unload.Alert("省份填写错误提示及示例", "内蒙古自治区填内蒙古，香港特别行政区填香港");
        return false;
    }
    var id = $('.info_update input[name="id"]').val();
    var customer_name = $('.info_update input[name="customer_name"]').val();
    var EnglishName = $('.info_update input[name="EnglishName"]').val();
    /*var customer_classify = $('.info_update input[name="customer_classify"]').val();*/
    var customer_classify = "null";
    var contact = $('.info_update input[name="contact"]').val();
    var contact_info1 = $('.info_update input[name="contact_info1"]').val();
    var contact_info2 = $('.info_update input[name="contact_info2"]').val();
    var contact_address = $('.info_update input[name="contact_address"]').val();
    var zip_code = $('.info_update input[name="zip_code"]').val();
    var fax_number = $('.info_update input[name="fax_number"]').val();
    var email = $('.info_update input[name="email"]').val();
    /*var shorthand_coding = $('.info_update input[name="shorthand_coding"]').val();*/
    var shorthand_coding = "null";
    var Website = $('.info_update input[name="Website"]').val();
    var customer_department = $('.info_update input[name="customer_department"]').val();
    var Englishcustomer_department = $('.info_update input[name="Englishcustomer_department"]').val();
    var CustomerLevel = $('.info_update #CustomerLevel').val();
    var City = $('.info_update  input[name="City"]').val();
    $.ajax({
        type: 'post',
        url: 'CustomerOperate',
        data: {
            id: id,
            customer_name: customer_name,
            EnglishName:EnglishName,
            customer_classify: customer_classify,
            contact: contact,
            contact_info1: contact_info1,
            contact_info2: contact_info2,
            contact_address: contact_address,
            area: area,
            zip_code: zip_code,
            fax_number: fax_number,
            email: email,
            shorthand_coding: shorthand_coding,
            Website:Website,
            CustomerDepartment:customer_department,
            DepartmentEnglish:Englishcustomer_department,
            classify:'修改',
            CustomerLevel:CustomerLevel,
            City :City
        },
        dataType: 'json',
        success: function (data) {
        	data = eval("(" + data + ")");
        	console.log(data.message);
         	if(data.message){
                $.MsgBox.Alert("提示", "修改成功！");
         	}else{
                $.MsgBox_Unload.Alert('提示', "修改失败！");
         	}      
         },
        error: function () {
            $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });
});
$('.update_close, #update_cancel').click(function () {
    $('.cover-color, .info_update').hide();
});

//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function () {
    window.location.reload();
});
var invoiceInfoObj = {};
var invoiceSaveObj = {};
// 开票表格内弹出
$(document).on("click",".invoiceInfo_td>span",function(){
    $(".cover-color, .invoice_tshow").slideDown(200);
    $("#invoice_tableshow tbody tr td:nth-child(2)").each(function(){
        $(this).text("");
    });
    var that = $(this).parent();
    var ID = that.siblings("td.edit").attr("value");
    // 账号 开户行 发票收件人 开票抬头
    var Account="",DepositBank="",InvoiceRecepter,InvoiceTitle="";
    InvoiceRecepter = that.siblings("td.Contact").text();
    var LinkAddress = that.siblings("td.ContactAddress").text();
    var LinkTel = that.siblings("td.ContactInfo1").text();
    var LinkZipCode = "";
    // 注册地址
    var RegisterAddress = that.siblings("td.ContactAddress").text();
    var TaxPayerIdentityNO="";
    // 电话
    var Telephone = that.siblings("td.ContactInfo1").text();
    InvoiceRecepter = globalDataHandle(InvoiceRecepter,"");
    LinkAddress = globalDataHandle(LinkAddress,"");
    LinkTel = globalDataHandle(LinkTel,"");
    RegisterAddress = globalDataHandle(RegisterAddress,"");
    Telephone = globalDataHandle(Telephone,"");
    invoiceInfoObj = {};
    invoiceInfoObj.ID = ID;
    invoiceInfoObj.Account = Account;
    invoiceInfoObj.DepositBank = DepositBank;
    invoiceInfoObj.InvoiceRecepter = InvoiceRecepter;
    invoiceInfoObj.InvoiceTitle = InvoiceTitle;
    invoiceInfoObj.LinkAddress = LinkAddress;
    invoiceInfoObj.LinkTel = LinkTel;
    invoiceInfoObj.LinkZipCode = LinkZipCode;
    invoiceInfoObj.RegisterAddress = RegisterAddress;
    invoiceInfoObj.TaxPayerIdentityNO = TaxPayerIdentityNO;
    invoiceInfoObj.Telephone = Telephone;
    console.log(invoiceInfoObj);
    $.ajax({
        type: "GET",
        url: "TaxInfo",
        data: {
            ID: ID
        },
        dataType: "json",
        success: function(data){
            console.log(typeof data);
            if(data.length>1){
                for(var k in invoiceInfoObj){
                    if(data[1][k]==""||data[1][k]=="--"||data[1][k]==undefined||data[1][k]==null){
                        $("#invoice_tableshow tbody").find(".invo_"+k).text(invoiceInfoObj[k]);
                    }else{
                        $("#invoice_tableshow tbody").find(".invo_"+k).text(data[1][k]);
                    }
                }
            }else if(data.length==1){
                for(var k1 in invoiceInfoObj){
                    $("#invoice_tableshow tbody").find(".invo_"+k1).text(invoiceInfoObj[k1]);
                }
            }
        },
        error:function(){
            $.MsgBox_Unload.Alert("提示","网络繁忙，获取开票信息失败");
        }
    });
});

$(document).on("click",".invoice_tshow_tit_r, #invoice_tshow_close",function(){
    invoiceInfoObj = {};
    invoiceSaveObj = {};
    $(".invoice_tshow").fadeOut(200);
    $(".cover-color").fadeOut(200);
});

// 保存提交
$("#invoice_tshow_submit").on("click",function(){
    invoiceSaveObj.ID = invoiceInfoObj.ID;
    var InvoiceTitle = $(".invo_InvoiceTitle").text().trim();
    var TaxPayerIdentityNO = $(".invo_TaxPayerIdentityNO").text().trim();
    var RegisterAddress = $(".invo_RegisterAddress").text().trim();
    var Telephone = $(".invo_Telephone").text().trim();
    var DepositBank = $(".invo_DepositBank").text().trim();
    var Account = $(".invo_Account").text().trim();
    var InvoiceRecepter = $(".invo_InvoiceRecepter").text().trim();
    var LinkAddress = $(".invo_LinkAddress").text().trim();
    var LinkTel = $(".invo_LinkTel").text().trim();
    var LinkEmail = $(".invo_LinkZipCode").text().trim();
    invoiceSaveObj.InvoiceTitle = InvoiceTitle;
    invoiceSaveObj.TaxPayerIdentityNO = TaxPayerIdentityNO;
    invoiceSaveObj.RegisterAddress = RegisterAddress;
    invoiceSaveObj.Telephone = Telephone;
    invoiceSaveObj.DepositBank = DepositBank;
    invoiceSaveObj.Account = Account;
    invoiceSaveObj.InvoiceRecepter = InvoiceRecepter;
    invoiceSaveObj.LinkAddress = LinkAddress;
    invoiceSaveObj.LinkTel = LinkTel;
    invoiceSaveObj.LinkEmail = LinkEmail;
    $.ajax({
        type: "POST",
        url: "TaxInfo",
        data: invoiceSaveObj,
        dataType: "text",
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        success: function(data){
            $.MsgBox_Unload.Alert("提示",data);
        },
        error: function(){
            $.MsgBox_Unload.Alert("提示","网络繁忙!刷新重试");
        },
        complete: function(XMLHttpRequest,textStatus){
            if(textStatus=="success"){
                $("#invoice_tshow_close").trigger("click");
            }
        }
    });
});
// 文档加载完成
$(document).ready(function(){
    $("#invoice_tableshow tbody tr td:nth-child(2)").attr("contenteditable","true");
    pageStyle(Number($('#currentPage').html()), Number($('#allPage').html()), "");
    if($("#hideQuery").attr("value") == "mix"){
        $(".select2").show();
    }
    else{
        $(".select2").hide();
    }
});

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

/*2018-09-13更新*/
$(document).on("click", "td.export_td>span", function(){
    var tr = $(this).parent().parent();
    $(".export_cover, .export_div").slideDown(250);
    $(".export_div_body_in table td[class^='export_']").each(function(){
        var subClassName = $(this).attr("class").replace("export_", "");
        var oldText;
        if($(this).is(".export_ContactInfo")){
            oldText = globalDataHandle(tr.find(".ContactInfo1").text(), "") + (globalDataHandle(tr.find(".ContactInfo2").text(), "") == "" ? "" : ("/"+tr.find(".ContactInfo1").text()));
        }else{
            oldText = tr.find("td."+subClassName).text();
        }
        var newText = globalDataHandle(oldText,"");
        $(this).text(newText);
        if(newText == ""){
            $(this).parent().addClass("warning");
        }else{
            $(this).parent().removeClass("warning");
        }
        // 填充Data
        exportData[subClassName] = newText;
    });
});

$(".export_div_tit>span, #export_div_cancel").click(function(){
    $(".export_cover, .export_div").slideUp(250);
    for(var k in exportData){
        exportData[k] = null;
    }
});

// 导出提交
$("#export_div_submit").click(function(){
    $.ajax({
        type: "POST",
        url: "Customer",
        data: exportData
    }).then(function(data){
        window.open(data);
    },function(){
        $.MsgBox_Unload.Alert("提示","网络繁忙！");
    });
});