/*保存省市数据*/
/*进入页面验证本地存储*/
// storage.clear(); // 将localStorage的所有内容清除
// storage.removeItem("a"); // 删除某个键值对
// storage.key(i); // 使用key()方法，向其中出入索引即可获取对应的键
var schedule_provinceANDcity;
if(!window.localStorage){
    alert("浏览器不支持localstorage");
}else{
    // 主逻辑业务
    var storage = window.localStorage;
    var inow = Date.now();
    var schedule_provinceANDcityStr = storage.getItem("schedule_provinceANDcity");
    if(schedule_provinceANDcityStr == undefined){
        // 第一次存
        getprovinceANDcityJSON();
    }else{
        if(eouluGlobal.S_getPageAllConfig()["Schedule"].provinceANDcityRefreshFlag){
            getprovinceANDcityJSON();
        }else{
            var iexpires = JSON.parse(schedule_provinceANDcityStr).expires;
            if(iexpires < inow){
                // 已超期
                getprovinceANDcityJSON();
            }else{
                // 未超期
                schedule_provinceANDcity = JSON.parse(schedule_provinceANDcityStr).data;
            }
        }
    }
}

/*get 省市JSON*/
function getprovinceANDcityJSON(){
    jQuery.getJSON("html/modules/serviced/provinceANDcity.json", undefined, function(data, status, xhr){
        if(status == "success"){
            var iObj = {};
            iObj.expires = inow + 30*24*60*60*1000;
            iObj.data = _.cloneDeep(data);
            console.log("iObj.data");
            console.log(iObj.data);
            schedule_provinceANDcity = _.cloneDeep(data);
            // _.cloneDeep(a)
            var iStr = JSON.stringify(iObj);
            // storage["InventoryAllCustomerInfo"] = iStr;
            storage.setItem("schedule_provinceANDcity", iStr);
        }else{
            $.MsgBox_Unload.Alert("提示","获取中国省市数据失败！");
        }
    });
}

/*查找城市*/
function searchCity(city){
    if(ScheduleState.provinceANDcityStr === null){
        return undefined;
    }
    return _.find(ScheduleState.provinceANDcityStr, function(o) { return o.value == city; });
}

var ScheduleState = new Object();
ScheduleState.addSubmitObj = new Object();
ScheduleState.addSubmitObj.Name = null;
ScheduleState.addSubmitObj.CustomerUnit = null;
ScheduleState.addSubmitObj.ServiceItem = null;
ScheduleState.addSubmitObj.TransportTool = null;
ScheduleState.addSubmitObj.TrainNumber = null;
ScheduleState.addSubmitObj.Hotel = null;
ScheduleState.addSubmitObj.OperateType = null;
ScheduleState.addSubmitObj.Departure = null;
ScheduleState.addSubmitObj.Destination = null;
ScheduleState.addSubmitObj.DepartureDate = null;
ScheduleState.addSubmitObj.DestinationDate = null;
ScheduleState.addSubmitObj.HotelExpense = null;
ScheduleState.addSubmitObj.TrafficExpense = null;
ScheduleState.addSubmitObj.TravelDistance = null;
ScheduleState.addSubmitObj.Date = null;

ScheduleState.updateSubmitObj = new Object();
ScheduleState.updateSubmitObj.ID = null;
ScheduleState.updateSubmitObj.Name = null;
ScheduleState.updateSubmitObj.CustomerUnit = null;
ScheduleState.updateSubmitObj.ServiceItem = null;
ScheduleState.updateSubmitObj.TransportTool = null;
ScheduleState.updateSubmitObj.TrainNumber = null;
ScheduleState.updateSubmitObj.Hotel = null;
ScheduleState.updateSubmitObj.OperateType = null;
ScheduleState.updateSubmitObj.Departure = null;
ScheduleState.updateSubmitObj.Destination = null;
ScheduleState.updateSubmitObj.DepartureDate = null;
ScheduleState.updateSubmitObj.DestinationDate = null;
ScheduleState.updateSubmitObj.HotelExpense = null;
ScheduleState.updateSubmitObj.TrafficExpense = null;
ScheduleState.updateSubmitObj.TravelDistance = null;
ScheduleState.updateSubmitObj.Date = null;

ScheduleState.provinceANDcityStr = null;
ScheduleState.addprovinceANDcityFlag = false;
ScheduleState.updateprovinceANDcityFlag = false;

/*添加修改自动填充 init*/
function addupdateAwesomplete(classify, dom){
    if(ScheduleState.provinceANDcityStr === null){
        var str = [];
        schedule_provinceANDcity.provinces.map(function(v, i, arr){
            v.citys.map(function(vv, ii, array){
                var item = {};
                item.label = v.provinceName + "," + vv.citysName;
                item.value = vv.citysName;
                str.push(item);
            });
        });
        ScheduleState.provinceANDcityStr = _.cloneDeep(str);
    }
    if(classify == "add"){
        if(!ScheduleState.addprovinceANDcityFlag){
            dom.each(function(i, el){
                new Awesomplete(el, {
                    list: ScheduleState.provinceANDcityStr,
                    minChars: 1,
                    maxItems: 20,
                    autoFirst: true
                });
            });
            ScheduleState.addprovinceANDcityFlag = true;
        }
    }else if(classify == "update"){
        if(!ScheduleState.updateprovinceANDcityFlag){
            dom.each(function(i, el){
                new Awesomplete(el, {
                    list: ScheduleState.provinceANDcityStr,
                    minChars: 1,
                    maxItems: 20,
                    autoFirst: true
                });
            });
            ScheduleState.updateprovinceANDcityFlag = true;
        }
    }
}

/* 添加提交 */
$(document).on("click","#add_submit",function(){
    var iThat = $(this);
    for(var k in ScheduleState.addSubmitObj){
        if(k=="OperateType"){
            ScheduleState.addSubmitObj[k] = "Add";
            continue;
        }
        if(k=="Date"){
            ScheduleState.addSubmitObj[k] = currentDate;
            continue;
        }
        if(k=="TravelDistance"){
            continue;
        }
        ScheduleState.addSubmitObj[k] = $("#add_info_"+k).val();
    }
    // 表单验证
    var Reg = new RegExp( '[0-9]{2}:[0-9]{2}');
    for(var kk in ScheduleState.addSubmitObj){
        ScheduleState.addSubmitObj[kk] = globalDataHandle(ScheduleState.addSubmitObj[kk], "").trim();
        if(kk == "CustomerUnit" && ScheduleState.addSubmitObj[kk] == ""){
            $.MsgBox_Unload.Alert("提示","客户单位必填！");
            return false;
        }
        if(kk == "HotelExpense" && (ScheduleState.addSubmitObj[kk] == "" || ScheduleState.addSubmitObj[kk] == "请选择住宿费用")){
            $.MsgBox_Unload.Alert("提示","未选择住宿费用！");
            return false;
        }
        if(kk == "TrafficExpense" && (ScheduleState.addSubmitObj[kk] == "" || ScheduleState.addSubmitObj[kk] == "请选择交通方式")){
            $.MsgBox_Unload.Alert("提示","未选择交通费用！");
            return false;
        }
        if(kk == "Departure" && ScheduleState.addSubmitObj[kk] == ""){
            $.MsgBox_Unload.Alert("提示","未选择出发地！");
            return false;
        }
        if(kk == "Destination" && ScheduleState.addSubmitObj[kk] == ""){
            $.MsgBox_Unload.Alert("提示","未选择目的地！");
            return false;
        }
        if(kk == "DepartureDate" && ScheduleState.addSubmitObj[kk] != "" && !Reg.test(ScheduleState.addSubmitObj[kk])){
            $.MsgBox_Unload.Alert("提示", "出发时间格式错误！");
            return false;
        }
        if(kk == "DestinationDate" && ScheduleState.addSubmitObj[kk] != "" && !Reg.test(ScheduleState.addSubmitObj[kk])){
            $.MsgBox_Unload.Alert("提示", "到达时间格式错误！");
            return false;
        }

    }

    if(searchCity(ScheduleState.addSubmitObj.Departure) == undefined || searchCity(ScheduleState.addSubmitObj.Destination) == undefined){
        $.MsgBox_Unload.Confirm("出发地&目的地异常提示", "无法计算距离，继续提交吗？", function(){
            eouluGlobal.C_btnDisabled(iThat, false);
            ScheduleState.addSubmitObj.TravelDistance = "0.00";
            $.ajax({
                type: 'get',
                url: 'ScheduleOperate',
                data: ScheduleState.addSubmitObj,
                dataType: 'json',
                success: function (data) {
                    if(data==true){
                        $.MsgBox.Alert('提示','添加成功');
                        $('.MailBar_cover_color, .contract_add').hide();
                    }else{
                        $.MsgBox_Unload.Alert("提示", "添加失败！");
                    }
                },
                error : function () {
                    $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
                },
                complete: function(XMLHttpRequest, textStatus){
                    eouluGlobal.C_btnAbled(iThat, false);
                }
            });
        });
    }else{
        alterDistance(ScheduleState.addSubmitObj.Departure, ScheduleState.addSubmitObj.Destination, function(distance){
            eouluGlobal.C_btnDisabled(iThat, false);
            ScheduleState.addSubmitObj.TravelDistance = distance < 20 ? 20.00 : distance;
            $.ajax({
                type: 'get',
                url: 'ScheduleOperate',
                data: ScheduleState.addSubmitObj,
                dataType: 'json',
                success: function (data) {
                    if(data==true){
                        $.MsgBox.Alert('提示','添加成功');
                        $('.MailBar_cover_color, .contract_add').hide();
                    }else{
                        $.MsgBox_Unload.Alert("提示", "添加失败！");
                    }
                },
                error : function () {
                    $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
                },
                complete: function(XMLHttpRequest, textStatus){
                    eouluGlobal.C_btnAbled(iThat, false);
                }
            });
        });
    }
});

// function saveExistCity(){
//     var map = new BMap.Map("allmap");

//     var point = new BMap.Point(116.331398,39.897445);

//     map.centerAndZoom(point,12);
//     // 创建地址解析器实例
//     var myGeo = new BMap.Geocoder();
//     // 将地址解析结果显示在地图上,并调整地图视野
//     myGeo.getPoint($scope.address.to_addr, function(point){
//     if (point) {
//     lon = point.lng;
//     lat = point.lat;

//     alert(lon+','+lat);

//     //在地图上定位到该点

//     map.centerAndZoom(point, 16);

//     map.addOverlay(new BMap.Marker(point));

//     }else{
//     alert("您选择地址没有解析到结果!");
//     }
//     }, "苏州市");
// }

// function saveExistCity(city){
//     var cityURI = encodeURIComponent(city);
//     // $.ajax({
//     //     type : 'get',
//     //     url : 'http://api.map.baidu.com/geocoder?address='+cityURI+'&output=json&key=ZHnpqUp55qaTvkE27KGjUBYLcUfsaRTV&city='+cityURI,
//     //     dataType : 'json',
//     //     success : function(data){
//     //         console.log(data);
//     //     }
//     // });
    
//     $.ajax({
//         async: false,
//         type: "GET",
//         dataType: 'jsonp',
//         jsonp: 'callback',
//         jsonpCallback: 'callbackfunction',
//         url: 'http://api.map.baidu.com/geocoder?address='+cityURI+'&output=json&key=ZHnpqUp55qaTvkE27KGjUBYLcUfsaRTV&city='+cityURI,
//         data: "",
//         timeout: 3000,
//         contentType: "application/json;utf-8",
//         success: function(msg) {
//           console.log(msg.result);
//           alert(msg.result);
//         }
//       });

// }

/* 修改信息界面打开 */
$(document).on("click",".xiugai",function(){
    addupdateAwesomplete("update", $("#update_info_Departure, #update_info_Destination"));
	$(this).css("color","red");
	var tr=$(this).parent();
    $("[id^='update_info_']").each(function(){
        if($(this).is("#update_info_TrafficExpense")) return true;
        var subClassName = $(this).attr("id").replace("update_info_", "td_");
        var newValue = globalDataHandle(tr.find("."+subClassName).text(), "");
        $(this).val(newValue);
    });
	$("#update_info_TransportTool").trigger("change");
    $("#update_info_TrafficExpense").val(globalDataHandle(tr.find(".td_TrafficExpense").text(), ""));

    var oldDay = tr.find(".DateTime").text();
    var iThat = $("#update_submit");
    if(oldDay > moment().subtract(1, "months").format("YYYY-MM-DD")){
        eouluGlobal.C_btnAbled(iThat, false);
        iThat.children("span.glyphicon").css("cursor","pointer");
    }else{
        eouluGlobal.C_btnDisabled(iThat, false);
        iThat.children("span.glyphicon").css("cursor","not-allowed");
    }

    ScheduleState.updateSubmitObj.ID = $(this).attr("value");
    $('.MailBar_cover_color, .contract_update').slideDown(200);
});

// 计算未修改出发地和目的地
function alterDistance(address1, address2, fn){
    var url1 = 'https://api.map.baidu.com/geocoder/v2/?ak=ZHnpqUp55qaTvkE27KGjUBYLcUfsaRTV&output=json&address=' + encodeURIComponent(address1);
    var url2 = 'https://api.map.baidu.com/geocoder/v2/?ak=ZHnpqUp55qaTvkE27KGjUBYLcUfsaRTV&output=json&address=' + encodeURIComponent(address2);
    //根据地点名称获取经纬度信息
    $.ajax({
        type: "POST",
        url: url1,
        dataType: "JSONP",
        success: function(data) {
            if (parseInt(data.status) == 0) {
                var lng1 = data.result.location.lng;
                var lat1 = data.result.location.lat;
                console.log("lng1:"+lng1);
                console.log("lat1:"+lat1);
                $.ajax({
                    type: "POST",
                    url: url2,
                    dataType: "JSONP",
                    success: function(data) {
                        if (parseInt(data.status) == 0) {
                            var lng2 = data.result.location.lng;
                            var lat2 = data.result.location.lat;
                            console.log("lng2:"+lng2);
                            console.log("lat2:"+lat2);
                            var map = new BMap.Map();
                            var pointA = new BMap.Point(lng1, lat1);
                            var pointB = new BMap.Point(lng2, lat2);
                            var distance = (map.getDistance(pointA, pointB) / 1000).toFixed(2);
                            console.warn(distance);
                            fn && fn(distance);
                        }else{
                            alert("第三方库计算距离失败！");
                        }
                    }
                });
            }
        }
    });
}

/*  提交修改后的信息  ************************************/
$('#update_submit').click(function () {
    var iThat = $(this);
    for(var k in ScheduleState.updateSubmitObj){
        if(k=="OperateType"){
            ScheduleState.updateSubmitObj[k] = "Modify";
            continue;
        }
        if(k=="Date"){
            ScheduleState.updateSubmitObj[k] = currentDate;
            continue;
        }
        if(k=="TravelDistance" || k=="ID"){
            continue;
        }
        ScheduleState.updateSubmitObj[k] = $("#update_info_"+k).val();
    }
    // 表单验证
    var Reg = new RegExp( '[0-9]{2}:[0-9]{2}');
    for(var kk in ScheduleState.updateSubmitObj){
        ScheduleState.updateSubmitObj[kk] = globalDataHandle(ScheduleState.updateSubmitObj[kk], "").trim();
        if(kk == "CustomerUnit" && ScheduleState.updateSubmitObj[kk] == ""){
            $.MsgBox_Unload.Alert("提示","客户单位必填！");
            return false;
        }
        if(kk == "HotelExpense" && (ScheduleState.updateSubmitObj[kk] == "" || ScheduleState.updateSubmitObj[kk] == "请选择住宿费用")){
            $.MsgBox_Unload.Alert("提示","未选择住宿费用！");
            return false;
        }
        if(kk == "TrafficExpense" && (ScheduleState.updateSubmitObj[kk] == "" || ScheduleState.updateSubmitObj[kk] == "请选择交通方式")){
            $.MsgBox_Unload.Alert("提示","未选择交通费用！");
            return false;
        }
        if(kk == "Departure" && ScheduleState.updateSubmitObj[kk] == ""){
            $.MsgBox_Unload.Alert("提示","未选择出发地！");
            return false;
        }
        if(kk == "Destination" && ScheduleState.updateSubmitObj[kk] == ""){
            $.MsgBox_Unload.Alert("提示","未选择目的地！");
            return false;
        }
        if(kk == "DepartureDate" && ScheduleState.updateSubmitObj[kk] != "" && !Reg.test(ScheduleState.updateSubmitObj[kk])){
            $.MsgBox_Unload.Alert("提示", "出发时间格式错误！");
            return false;
        }
        if(kk == "DestinationDate" && ScheduleState.updateSubmitObj[kk] != "" && !Reg.test(ScheduleState.updateSubmitObj[kk])){
            $.MsgBox_Unload.Alert("提示", "到达时间格式错误！");
            return false;
        }

    }

    if(searchCity(ScheduleState.updateSubmitObj.Departure) == undefined || searchCity(ScheduleState.updateSubmitObj.Destination) == undefined){
        $.MsgBox_Unload.Confirm("出发地&目的地异常提示", "无法计算距离，继续提交吗？", function(){
            eouluGlobal.C_btnDisabled(iThat, false);
            ScheduleState.updateSubmitObj.TravelDistance = "0.00";
            $.ajax({
                type: 'get',
                url: 'ScheduleOperate',
                data: ScheduleState.updateSubmitObj,
                dataType: 'json',
                success: function (data) {
                    if(data==true){
                        $.MsgBox.Alert('提示','修改成功');
                        $('.MailBar_cover_color, .contract_update').hide();
                    }else{
                        $.MsgBox_Unload.Alert("提示", "修改失败！");
                    }
                },
                error : function () {
                    $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
                },
                complete: function(XMLHttpRequest, textStatus){
                    eouluGlobal.C_btnAbled(iThat, false);
                }
            });
        });
    }else{
        alterDistance(ScheduleState.updateSubmitObj.Departure, ScheduleState.updateSubmitObj.Destination, function(distance){
            eouluGlobal.C_btnDisabled(iThat, false);
            ScheduleState.updateSubmitObj.TravelDistance = distance < 20 ? 20.00 : distance;
            $.ajax({
                type: 'get',
                url: 'ScheduleOperate',
                data: ScheduleState.updateSubmitObj,
                dataType: 'json',
                success: function (data) {
                    if(data==true){
                        $.MsgBox.Alert('提示','修改成功');
                        $('.MailBar_cover_color, .contract_update').hide();
                    }else{
                        $.MsgBox_Unload.Alert("提示", "修改失败！");
                    }
                },
                error : function () {
                    $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
                },
                complete: function(XMLHttpRequest, textStatus){
                    eouluGlobal.C_btnAbled(iThat, false);
                }
            });
        });
    }
});

//点击添加
function AddContract(){
    addupdateAwesomplete("add", $("#add_info_Departure, #add_info_Destination"));
	/*init*/
    $("[id^='add_info_']").each(function(){
        if($(this).is("#add_info_Name") || $(this).is("#add_info_TrafficExpense")) return true;
        if($(this).is("#add_info_TransportTool")){
            $(this).val("请选择");
            return true;
        }
        if($(this).is("#add_info_HotelExpense")){
            $(this).val("请选择住宿费用");
            return true;
        }
        $(this).val("");
    });
    $("#add_info_TransportTool").trigger("change");
    $('.MailBar_cover_color, .contract_add').slideDown(200);
}

//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function () {
    window.location.reload();
});

//点击关闭
$('#add_cancel').click(function() {
    $('.MailBar_cover_color, .contract_add').slideUp(200);
});

$('#update_cancel').click(function () {
    $('.MailBar_cover_color, .contract_update').slideUp(200);
});

//
function INSearch() {
	var classify = $(".classify").find("option:selected").text();
	if(classify == '员工姓名'){
		var parameter = $("select.staffName").find("option:selected").attr("value");
		parameter = Trim(parameter);
	}else{
		var parameter = $("#searchContent1").val();
		parameter = Trim(parameter);
	}
	if(parameter == ''){
		 $.MsgBox_Unload.Alert("提示", "搜索内容不为空！");
		return;
	}
    window.location.href = "ScheduleRoute?classify="+classify+"&parameter="+parameter;
}
function Cancel() {
    $('#search').val('cancel');
    $('input[name="Name"]').val('');
    $("#top_text_from").attr("action", "Schedule?Date="+currentDate);
    $('#top_text_from').submit();
}
$(".backToday").click(function(){
	window.location.href="Schedule";
});

//点击删除和取消删除
$(".deleteInfo").click(function(){
	var txt = $(".deleteInfo").val();
	if(txt == '删除'){
		$(".deleteInfo").val('').val("取消删除");
		$(".deleteInfo").css('width','70px');
		//序号变成删除列 数字变成X图标
		$("#table1 .tbody_tr td.xiugai").removeClass('xiugai').addClass('delete');
		$("#table1  td.xiugai1").eq(0).text('删除');
		$("#table1 .tbody_tr td.delete").text('X');
		//点击每列的X

		$(".tbody_tr td.delete").click(function(){
			var tr = $(this).parent();
			var ID = tr.find("td.delete").attr('value');
			var Name = tr.find("td.Name").text();
			//时效性判断
				var DateTime = tr.find(".DateTime").text();
				var   d=new   Date(Date.parse(DateTime.replace(/-/g,"/")));
				var   curDate=new   Date();
			    var da=d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate(); 
			    var cur=curDate.getFullYear() + '-' + (curDate.getMonth() + 1) + '-' + curDate.getDate(); 
				if(d >curDate || da == cur){
					/*alert("未来时间")*/
							$.ajax({
								type:'get',
								url:'ScheduleOperate',
								data:{
									OperateType:'Delete',
									ID:ID,
									Name:Name
								},
								success:function(data){
									console.log(data);
									if(data){
					                	 $.MsgBox_Unload.Alert("提示", "删除成功！");
					                	tr.remove();
					                }else{
					                	$.MsgBox_Unload.Alert("提示", "删除失败！");
					                }
								},
								error:function(){
									$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
								}
							});
					}else{
						$.MsgBox_Unload.Alert("提示", "不能删除过去的信息！");
					}
		});
	}else{
		window.location.reload();
	}
});
function SelectContent(){
    var engineerOb = $("span.engineer").html().split(',');
	var len = engineerOb.length;
	var str1 = '<select class="staffName" >'+
				'<option value="所有人">所有人</option>';
	var str2 = '';
	var str3 = '';
	var str4 = '</select>'
	for(var i = 1;i<len-1;i++){
			str2 += '<option value="'+engineerOb[i].substring(12,engineerOb[i].length-1)+'">'+engineerOb[i].substring(12,engineerOb[i].length-1)+'</option>';
	}
	str3 += '<option value="'+engineerOb[len-1].substring(12,engineerOb[len-1].length-2)+'">'+engineerOb[len-1].substring(12,engineerOb[len-1].length-2)+'</option>';
	Str = str1+str2+str3+str4;
	$(".select1").html('').html(Str);
}
$(function(){
	var Href = window.location.href;
	var Index = Href.indexOf('?');
	var Index2 = Href.indexOf('Schedule?Date');
	if(Index == -1 || Index2 != -1){
		SelectContent();
	}else{
		var Pa= Href.substring(Index+1);
		var Pa1 =decodeURI(Pa);
		var Index3 = Pa1.indexOf('&');
		var Pa2 = Pa1.substring(9,Index3);
		var Pa3 = Pa1.substring(Index3+1);
		var Index4 = Pa3.indexOf('&');
		var Pa4 = Pa1.substring(Index3+1);
		var Index5 = Pa4.indexOf('=');
		if(Index4 == -1){
			var Pa5 = Pa4.substring(Index5+1);
			Pa5 = Trim(Pa5);
		}else{
			var Pa5 = Pa4.substring(Index5+1,Index4);
			Pa5 = Trim(Pa5);
		}
		if(Pa2 != '员工姓名'){
			$(".classify").find('option[value="'+Pa2+'"]').attr("selected",true);
			$(".select1").html('').html('<input type="text" id="searchContent1" name="parameter" value="'+Pa5+'" style="width:80%;height:29px;border:1px solid darkgrey;border-left:none;float:left;">');
		
		}else{
			SelectContent();
			for(var i =0;i<$("select.staffName").find('option').length;i++){
				var P2 = $("select.staffName").find('option').eq(i).attr('value');
				var P3 = Trim(P2);
				if(P3 == Pa5){
				 $("select.staffName").find('option[value="'+P2+'"]').attr("selected",true);	
				}
			}
			
		}
					
	}
	
});

$(".classify").change(function(engineerOb){
	var Txt = $(".classify").val();
	if(Txt == '员工姓名'){
		SelectContent();
	}else{
		$(".select1").html('').html('<input type="text" id="searchContent1" name="parameter"  style="width:80%;height:29px;border:1px solid darkgrey;border-left:none;float:left;">');
	}
});

$(function(){
    // 日历范围选择
    $('.info-rili i').click(function() {
          $(this).parent().find('input').click();
    });
    var daterangepickerOption = {
        // startDate: moment().hours(0).minutes(0).seconds(0), //设置开始日期
        startDate: moment(new Date(Date.parse(new Date().getFullYear()+'-01-01'))), //设置开始日期
        endDate: moment(new Date()), //设置结束器日期
        maxDate: moment(new Date()), //设置最大日期
        "opens": "left",
        ranges: {
             '今天': [moment(), moment()],
             '昨天': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
             '上周': [moment().subtract(6, 'days'), moment()],
             '前30天': [moment().subtract(29, 'days'), moment()],
             '本月': [moment().startOf('month'), moment().endOf('month')],
             '上月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            },
        showWeekNumbers: true,
        alwaysShowCalendars: true,
        locale: {
            format: "YYYY-MM-DD", //设置显示格式
            // format: "YYYY-MM-DD", //设置显示格式
            applyLabel: '确定', //确定按钮文本
            cancelLabel: '取消', //取消按钮文本
            customRangeLabel: '自定义',
            daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
            monthNames: ['一月', '二月', '三月', '四月', '五月', '六月',
                 '七月', '八月', '九月', '十月', '十一月', '十二月'
            ],
            firstDay: 1
        },
    };

    $('#config-demo').daterangepicker(daterangepickerOption,function(start, end, label) {
         timeRangeChange = [start.format('YYYY-MM-DD HH:mm:ss'), end.format('YYYY-MM-DD HH:mm:ss')];
       console.log(timeRangeChange);
     });
    // $('#config-demo').daterangepicker(daterangepickerOption, function(start, end, label) { console.log('New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')'); });
    
    /*交通方式切换*/
    var TrafficExpenseObj = {
        "飞机": _.range(0, 4200, 200),
        "铁路": _.range(0, 2050, 50),
        "自驾": _.range(0, 3050, 50),
        "打车": _.range(0, 1050, 50),
        "其他": _.range(0, 2010, 10)
    };

    $(document).on("change", "select[name='TransportTool_sel']", function(){
        var changeDOM;
        if($(this).is("#add_info_TransportTool")){
            changeDOM = $("#add_info_TrafficExpense");
        }else if($(this).is("#update_info_TransportTool")){
            changeDOM = $("#update_info_TrafficExpense");
        }
        var iVal = $(this).val();
        var iStr = "";
        if(iVal == "请选择"){
            iStr = "<option value='请选择交通方式'>请选择交通方式</option>";
        }else{
            TrafficExpenseObj[iVal].map(function(v, i, arr){
                iStr+="<option value="+v+">"+v+"元</option>";
            });
        }
        changeDOM.empty().append(iStr);
    });

    // var originDistanceData;
    // var originDistanceDataOper1;
    // var originDistanceDataOper2;
    // var originDistanceDataOper3;
    // var originDistanceDataOper4;
    // var originDistanceDataOper5;
    // var originDistanceDataOper6;
    // var originDistanceDataOper7;
    // var originDistanceDataOper8;
    // var originDistanceDataOper9;
    var errorID = [];
    function CalculateDistanceAjax(item,currentArr,index){
      var cityURI1 = encodeURIComponent(item.Departure);
      var cityURI2 = encodeURIComponent(item.Destination);
      var newObj = {};
      newObj = item;
      $.ajax({
          type: "POST",
          dataType: 'JSONP',
          url: 'https://api.map.baidu.com/geocoder/v2/?address='+cityURI1+'&output=json&ak=9Exo3oapwegLi9dzYT7Ho2GziyIT2KbS',
          success: function(msg){
            // console.log(msg);
            if(parseInt(msg.status) == 0){
              var lng1 = msg.result.location.lng;
              var lat1 = msg.result.location.lat;
              newObj.Departure = msg.result.location.lng+","+msg.result.location.lat;

              $.ajax({
                type: "POST",
                dataType: 'JSONP',
                url: 'https://api.map.baidu.com/geocoder/v2/?address='+cityURI2+'&output=json&ak=9Exo3oapwegLi9dzYT7Ho2GziyIT2KbS',
                // 9Exo3oapwegLi9dzYT7Ho2GziyIT2KbS
                // ZHnpqUp55qaTvkE27KGjUBYLcUfsaRTV
                // 1eXBmyAAwn3hmoIg3lCGiiezscMRG7kb
                success: function(msg2){
                  if(parseInt(msg2.status) == 0){
                    var lng2 = msg2.result.location.lng;
                    var lat2 = msg2.result.location.lat;
                    newObj.Destination = msg2.result.location.lng+","+msg2.result.location.lat;
                    var map = new BMap.Map();
                    var pointA = new BMap.Point(lng1,lat1);  
                    var pointB = new BMap.Point(lng2,lat2); 
                    var distance = (map.getDistance(pointA,pointB)/1000).toFixed(2);
                    if(distance == 0 || distance == "0"){
                      distance = (20).toFixed(2);
                    }
                    newObj.Distance = distance;
                  }else{
                    errorID.push(item.ID);
                    newObj.Distance = (0).toFixed(2);
                  }
                  return newObj;
                },
                error: function(){
                  console.log(currentArr+"操作索引值为"+index+"的再第二步失败了");
                }
              });

            }else{
              errorID.push(item.ID);
              newObj.Distance = (0).toFixed(2);
              return newObj;
            }
            // return newObj;
          },
          error: function(){
            console.log(currentArr+"操作索引值为"+index+"的失败了");
          }
        });
    }


    // $("#newDistanceCalc").on("click",function(){
    //   $.ajax({
    //     type: 'GET',
    //     url: 'CalculateDistance',
    //     success: function(data){
    //       console.log("测距数据获取成功了");
    //       console.log(typeof data);
    //       console.log(data);
    //       var newData = JSON.parse(data);
    //       console.log(typeof newData);
    //       console.log(newData);
    //       originDistanceData = newData.slice(0);
    //       originDistanceDataOper1 = newData.slice(1,100);
    //       originDistanceDataOper2 = newData.slice(100,199);
    //       originDistanceDataOper3 = newData.slice(199,298);
    //       originDistanceDataOper4 = newData.slice(298,397);
    //       originDistanceDataOper5 = newData.slice(397,496);
    //       originDistanceDataOper6 = newData.slice(496,595);
    //       originDistanceDataOper7 = newData.slice(595,694);
    //       originDistanceDataOper8 = newData.slice(694,793);
    //       originDistanceDataOper9 = newData.slice(793);
    //       console.log(originDistanceData);
    //     },
    //     errror: function(){
    //       alert('测距数据获取失败了');
    //     }
    //   });
    // });

    // $("#newDistanceCalc11").on("click",function(){
      
    //   console.log("originDistanceDataOper1的获取经纬度并计算距离");
    //   // console.log(ceshidata);
    //   originDistanceDataOper1.map(function(item,index,array){
    //     CalculateDistanceAjax(item,"originDistanceDataOper1",index);
        
    //   });
    //   console.log("originDistanceDataOper1");
    //   console.log(originDistanceDataOper1);
    //   console.log("errorID");
    //   console.log(errorID);
    //   console.log("originDistanceData");
    //   console.log(originDistanceData);
    // });

    // $("#newDistanceCalc22").on("click",function(){
      
    //   console.log("originDistanceDataOper2的获取经纬度并计算距离");
    //   // console.log(ceshidata);
    //   originDistanceDataOper2.map(function(item,index,array){
    //     CalculateDistanceAjax(item,"originDistanceDataOper2",index);
        
    //   });
    //   console.log("originDistanceDataOper2");
    //   console.log(originDistanceDataOper2);
    //   console.log("errorID");
    //   console.log(errorID);
    //   console.log("originDistanceData");
    //   console.log(originDistanceData);
    // });

    // $("#newDistanceCalc33").on("click",function(){
      
    //   console.log("originDistanceDataOper3的获取经纬度并计算距离");
    //   // console.log(ceshidata);
    //   originDistanceDataOper3.map(function(item,index,array){
    //     // if(item.ID=="323"||item.ID==323)continue;
    //     CalculateDistanceAjax(item,"originDistanceDataOper3",index);
        
    //   });
    //   console.log("originDistanceDataOper3");
    //   console.log(originDistanceDataOper3);
    //   console.log("errorID");
    //   console.log(errorID);
    //   console.log("originDistanceData");
    //   console.log(originDistanceData);
    // });

    // $("#newDistanceCalc44").on("click",function(){
      
    //   console.log("originDistanceDataOper4的获取经纬度并计算距离");
    //   // console.log(ceshidata);
    //   originDistanceDataOper4.map(function(item,index,array){
    //     CalculateDistanceAjax(item,"originDistanceDataOper4",index);
        
    //   });
    //   console.log("originDistanceDataOper4");
    //   console.log(originDistanceDataOper4);
    //   console.log("errorID");
    //   console.log(errorID);
    //   console.log("originDistanceData");
    //   console.log(originDistanceData);
    // });

    // $("#newDistanceCalc55").on("click",function(){
      
    //   console.log("originDistanceDataOper5的获取经纬度并计算距离");
    //   // console.log(ceshidata);
    //   originDistanceDataOper5.map(function(item,index,array){
    //     CalculateDistanceAjax(item,"originDistanceDataOper5",index);
        
    //   });
    //   console.log("originDistanceDataOper5");
    //   console.log(originDistanceDataOper5);
    //   console.log("errorID");
    //   console.log(errorID);
    //   console.log("originDistanceData");
    //   console.log(originDistanceData);
    // });

    // $("#newDistanceCalc66").on("click",function(){
      
    //   console.log("originDistanceDataOper6的获取经纬度并计算距离");
    //   // console.log(ceshidata);
    //   originDistanceDataOper6.map(function(item,index,array){
    //     CalculateDistanceAjax(item,"originDistanceDataOper6",index);
        
    //   });
    //   console.log("originDistanceDataOper6");
    //   console.log(originDistanceDataOper6);
    //   console.log("errorID");
    //   console.log(errorID);
    //   console.log("originDistanceData");
    //   console.log(originDistanceData);
    // });

    // $("#newDistanceCalc77").on("click",function(){
      
    //   console.log("originDistanceDataOper7的获取经纬度并计算距离");
    //   // console.log(ceshidata);
    //   originDistanceDataOper7.map(function(item,index,array){
    //     CalculateDistanceAjax(item,"originDistanceDataOper7",index);
        
    //   });
    //   console.log("originDistanceDataOper7");
    //   console.log(originDistanceDataOper7);
    //   console.log("errorID");
    //   console.log(errorID);
    //   console.log("originDistanceData");
    //   console.log(originDistanceData);
    // });

    // $("#newDistanceCalc88").on("click",function(){
      
    //   console.log("originDistanceDataOper8的获取经纬度并计算距离");
    //   // console.log(ceshidata);
    //   originDistanceDataOper8.map(function(item,index,array){
    //     CalculateDistanceAjax(item,"originDistanceDataOper8",index);
        
    //   });
    //   console.log("originDistanceDataOper8");
    //   console.log(originDistanceDataOper8);
    //   console.log("errorID");
    //   console.log(errorID);
    //   console.log("originDistanceData");
    //   console.log(originDistanceData);
    // });

    // $("#newDistanceCalc99").on("click",function(){
      
    //   console.log("originDistanceDataOper9的获取经纬度并计算距离");
    //   // console.log(ceshidata);
    //   originDistanceDataOper9.map(function(item,index,array){
    //     CalculateDistanceAjax(item,"originDistanceDataOper9",index);
        
    //   });
    //   console.log("originDistanceDataOper9");
    //   console.log(originDistanceDataOper9);
    //   console.log("errorID");
    //   console.log(errorID);
    //   console.log("originDistanceData");
    //   console.log(originDistanceData);
    // });

    // $("#newDistanceCalc100").on("click",function(){
    //   var distanceUl = originDistanceData.slice(0);
    //   console.log("distanceUl");
    //   console.log(distanceUl);
    //   if(distanceUl){
    //     var str="";
    //     for(var i = 1;i<distanceUl.length;i++){
    //       if(distanceUl[i].ID=="323"||distanceUl[i].ID==323) continue;
    //       str+='<li id="'+distanceUl[i].ID+'" departure="'+distanceUl[i].Departure+'" destination="'+distanceUl[i].Destination+'">'+distanceUl[i].Distance+'</li>';
    //     }
    //     $("#distanceUl").append(str);
    //   }
      
    // });

    // $("#newDistanceCalc1000").on("click",function(){
    //   console.log("开始发数据了");
    //   // console.log("originDistanceData");
    //   // console.log(originDistanceData);
    //   // var originDistanceDataBack = originDistanceData.slice(1);
    //   var originDistanceDataBack = [];
    //   var distanceUILength = $("#distanceUI li").length;
    //   console.log("distanceUILength:"+distanceUILength);
    //   for (var i=0;i<distanceUILength;i++){
    //     var item = {};
    //     var currentLi = $("#distanceUI li").eq(i);
    //     item.ID = currentLi.attr("id");
    //     item.Departure = currentLi.attr("departure");
    //     item.Destination = currentLi.attr("destination");
    //     item.Distance = currentLi.text();
    //     originDistanceDataBack.push(item);
    //   }
    //   console.log("originDistanceDataBack");
    //   console.log(originDistanceDataBack);
    //   originDistanceDataBack = JSON.stringify(originDistanceDataBack);
    //   console.log("处理后的originDistanceDataBack");
    //   console.log(typeof originDistanceDataBack);
    //   console.log(originDistanceDataBack);
    //   $.ajax({
    //     type: 'POST',
    //     url: 'CalculateDistance',
    //     data: {
    //       originDistanceDataBack: originDistanceDataBack
    //     },
    //     success: function(data){
    //       console.log("发数据成功了");
    //       console.log(typeof data);
    //       console.log(data);
          
    //     },
    //     errror: function(){
    //       alert('发数据失败了');
    //     }
    //   });
    // });

});


/*原来script标签内*/
var k4 = new Kalendae.Input('week_sel', {
    months: 1,
    mode: 'week',
    format: "YYYY-MM-DD",
    rangeDelimiter: "至",
    side:"bottom",
    disableDate:true,
    selected: Kalendae.moment(),
    subscribe: {
        'change': function (date) {
            // console.log(this.getSelected());
            $(".k-btn-close").trigger("click");
        }}
});
$(document).on("change","input[name='date_sel']",function(){
    if($("input[name='date_sel']:checked").val()=="dateWeek"){
        $(".dateRange_div").css({"display":"none"});
        $(".dateWeek_div").css({"display":"inline-block"});
    }else if($("input[name='date_sel']:checked").val()=="dateRange"){
        $(".dateWeek_div").css({"display":"none"});
        $(".dateRange_div").css({"display":"inline-block"});
    }
});

var schedule_startTime;
var schedule_endTime;

function switchDate(){
    if($("input[name='date_sel']:checked").val()=="dateRange"){
        var start_time0 = $(".calendar.left .input-mini").val();
        var end_time0 = $(".calendar.right .input-mini").val();
        if(start_time0 == "" || start_time0 == null){
            schedule_startTime = new Date().getFullYear()+'-01-01';
        }else{
            schedule_startTime = start_time0;
        }
        if(end_time0 == "" || end_time0 == null){
            var D0 = new Date();
            var yy0=D0.getFullYear(); 
            var mm0=D0.getMonth()+1; 
            if (mm0<10){
            mm0 = "0"+mm0;
            }
            var dd0=D0.getDate();
            if (dd0 < 10){
                dd0 = "0" + dd0;
            }
            schedule_endTime = yy0+'-'+mm0+'-'+dd0;
        }else{
            schedule_endTime = end_time0;
        }
    }else if($("input[name='date_sel']:checked").val()=="dateWeek"){
        if($("#week_sel").val().indexOf("至")>-1){
            schedule_startTime = $("#week_sel").val().split("至")[0];
            schedule_endTime = $("#week_sel").val().split("至")[1];
        }else if($("#week_sel").val().indexOf("至") == -1){
            schedule_startTime = $("#week_sel").val().trim();
            var wD = new Date();
            var wy = wD.getFullYear(); 
            var wm = wD.getMonth()+1; 
            if (wm<10){
            wm = "0"+wm;
            }
            var wd = wD.getDate();
            if (wd < 10){
                wd = "0" + wd;
            }
            schedule_endTime = wy+'-'+wm+'-'+wd;
        }
    }
}

$(function(){
    // 计算修改了一个出发地或目的地
    /*function alterDistance1(address1,address2){
      var url1 = 'https://api.map.baidu.com/geocoder/v2/?ak=ZHnpqUp55qaTvkE27KGjUBYLcUfsaRTV&output=json&address=' + encodeURIComponent(address1);
      var url2 = 'https://api.map.baidu.com/geocoder/v2/?ak=ZHnpqUp55qaTvkE27KGjUBYLcUfsaRTV&output=json&address=' + encodeURIComponent(address2);
            //根据地点名称获取经纬度信息
              var distance;
              $.ajax({
                  type: "POST",
                  url: url1,
                  dataType: "JSONP",
                  success: function (data) {
                    console.log(data);
                  if (parseInt(data.status) == 0) {
                    var lng1= data.result.location.lng;
                    var lat1= data.result.location.lat;
                    console.log(lng1)
                    console.log(lat1)
                    $.ajax({
                       type: "POST",
                       url: url2,
                       dataType: "JSONP",
                       success: function (data) {
                        if (parseInt(data.status) == 0) {
                          var lng2= data.result.location.lng;
                          var lat2= data.result.location.lat;
                        }
                        var map = new BMap.Map();
                    var pointA = new BMap.Point(lng1,lat1);  
              var pointB = new BMap.Point(lng2,lat2);  
              distance = (map.getDistance(pointA,pointB)/1000).toFixed(2);
              // $("#allmap").text(distance);
              // return distance;
              $("#tempDistance2").val(distance);
                       }
                  });
                  }
                } 
            });
    }*/

    /*function initMap(box,dom,item,saveDis){
        // 百度地图API功能  
        var map = new BMap.Map(box, {enableMapClick: false}); //创建百度地图实例，这里的allmap是地图容器的id  
        var point = new BMap.Point(120.737582, 31.261794); //创建一个点对象，这里的参数是地图上的经纬度  
        var marker = new BMap.Marker(point); // 创建标注
        map.centerAndZoom(point, 12); //这里是将地图的中心移动到我们刚才创建的点；这里的12是地图的缩放界别；数值越大，地图看的越细  
        map.enableDragging();
        map.enableScrollWheelZoom();
        map.clearOverlays();
        var geoc = new BMap.Geocoder();
        //切换城市
        var size = new BMap.Size(10, 20);
        map.addControl(new BMap.CityListControl({
            anchor: BMAP_ANCHOR_TOP_LEFT,
            offset: size,
        }));
        //点击地图选址    出现标注点
        map.addEventListener("click", showInfo);
        function showInfo(e) {
            x = e.point.lng; //获取鼠标当前点击的经纬度
            y = e.point.lat;
            if(x != "" && y != "") {
                map.removeOverlay(marker);
                var point1 = new BMap.Point(x, y);
                map.centerAndZoom(point1);
                marker = new BMap.Marker(point1); // 创建新的标注
                map.addOverlay(marker); //将标注添加到地图上
            }
            var pt = e.point;
            console.log(pt);
            if($(item).attr("lngAndlat")){
                var pt0 = {};
                var lng0 = $(item).attr("lngAndlat").split(",")[0];
                // alert(typeof lng0);
                lng0 = lng0*1;
                // alert(typeof lng0);
                var lat0 = $(item).attr("lngAndlat").split(",")[1];
                lat0 = lat0*1;
                pt0.lng = lng0;
                pt0.lat = lat0;
                console.log("pt0的坐标");
                console.log(pt0);
                tempDistance = (map.getDistance(pt,pt0)/1000).toFixed(2);
                // alert(tempDistance);
                $(saveDis).val(tempDistance);
            }
            geoc.getLocation(pt, function(rs) {
                var addComp = rs.addressComponents;
//              var text = addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber;
                var text = addComp.province + "," + addComp.city ;
                // dom.text(text);    // 将选点的地址信息放置元素中
                dom.val(text);    // 将选点的地址信息放置元素中
                dom.removeAttr("lngAndlat"); // 存储坐标点信息
                dom.attr("lngAndlat", pt.lng + "," + pt.lat);

                if($(item).val() && ($(item).val().indexOf(",") == -1)){
                    var address1 = $(item).val().trim();
                    var address2 = addComp.city;
                    alterDistance1(address1,address2);
                }
            });
        }
    }

    $(document).on("click",".startArea",function(){
        $(".mybtn1").fadeIn(100);
        $("#allmap").fadeIn(100);
        initMap("allmap",$(".startArea"),".arriveArea","#tempDistance1");
    });

    $(document).on("click",".arriveArea",function(){
        $(".mybtn1").fadeIn(100);
        $("#allmap").fadeIn(100);
        initMap("allmap",$(".arriveArea"),".startArea","#tempDistance1");
    });
    
    $(document).on("click",".startArea2",function(){
        $(".mybtn2").fadeIn(100);
        $("#allmap").fadeIn(100);
        initMap("allmap",$(".startArea2"),".arriveArea2","#tempDistance2");
    });

    $(document).on("click",".arriveArea2",function(){
        $(".mybtn2").fadeIn(100);
        $("#allmap").fadeIn(100);
        initMap("allmap",$(".arriveArea2"),".startArea2","#tempDistance2");
    });

    //  map.getDistance(pointA,pointB)/1000).toFixed(2);   //获取两地距离 pointA  pointB分别为两地坐标点
    
    $(".mybtn1").on("click",function(){
        // (map.getDistance($(".startArea").attr("lngAndlat"),$(".arriveArea").attr("lngAndlat"))/1000).toFixed(2);
        $("#allmap").fadeOut(100);
        $(".mybtn1").fadeOut(100);
    });
    $(".mybtn2").on("click",function(){
        $("#allmap").fadeOut(100);
        $(".mybtn2").fadeOut(100);
    });*/

    function calcExpense(){
        if($(".tbody_tr").length){
            $(".tbody_tr").each(function(){
                var a = $(this).find(".showTripCost1").text();
                var b = $(this).find(".showTripCost2").text();
                if(a==""||a=="--"||a==null||a==undefined){
                    a=0;
                }
                if(b==""||b=="--"||b==null||b==undefined){
                    b=0;
                }
                $(this).find(".showTripCost").text(a*1+b*1);
            });
        }
    }
    calcExpense();
});

var city = {北京:["东城区","西城区","崇文区","宣武区","朝阳区","海淀区","丰台区","石景山区","房山区","通州区","顺义区","昌平区","大兴区","怀柔区","平谷区","门头沟区","密云县","延庆县"],天津:["和平区","河东区","河西区","南开区","河北区","红桥区","东丽区","西青区","北辰区","津南区","武清区","宝坻区","滨海新区","静海县","宁河县","蓟县"],上海:["黄浦区","卢湾区","徐汇区","长宁区","静安区","普陀区","闸北区","虹口区","杨浦区","闵行区","宝山区","嘉定区","浦东新区","金山区","松江区","青浦区","奉贤区","崇明县"],重庆:["渝中区","大渡口区","江北区","南岸区","北碚区","渝北区","巴南区","长寿区","双桥区","沙坪坝区","万盛区","万州区","涪陵区","黔江区","永川区","合川区","江津区","九龙坡区","南川区","綦江县","潼南县","荣昌县","璧山县","大足县","铜梁县","梁平县","开县","忠县","城口县","垫江县","武隆县","丰都县","奉节县","云阳县","巫溪县","巫山县"],河北:["石家庄","唐山","秦皇岛","邯郸","邢台","保定","张家口","承德","沧州","廊坊","衡水"],山西:["太原","大同","阳泉","长治","晋城","朔州","晋中","运城","忻州","临汾","吕梁"],辽宁:["沈阳","大连","鞍山","抚顺","本溪","丹东","锦州","营口","阜新","辽阳","盘锦","铁岭","朝阳","葫芦岛"],吉林:["长春","吉林","四平","辽源","通化","白山","松原","白城","延边朝鲜族自治州"],黑龙江:["哈尔滨","齐齐哈尔","鹤岗","双鸭山","鸡西","大庆","伊春","牡丹江","佳木斯","七台河","黑河","绥化","大兴安岭"],江苏:["南京","苏州","无锡","常州","镇江","南通","泰州","扬州","盐城","连云港","徐州","淮安","宿迁"],浙江:["杭州","宁波","温州","嘉兴","湖州","绍兴","金华","衢州","舟山","台州","丽水"],安徽:["合肥","芜湖","蚌埠","淮南","马鞍山","淮北","铜陵","安庆","黄山","滁州","阜阳","宿州","巢湖","六安","亳州","池州","宣城"],福建:["福州","厦门","莆田","三明","泉州","漳州","南平","龙岩","宁德"],江西:["南昌","景德镇","萍乡","九江","新余","鹰潭","赣州","吉安","宜春","抚州","上饶"],山东:["济南","青岛","淄博","枣庄","东营","烟台","潍坊","济宁","泰安","威海","日照","莱芜","临沂","德州","聊城","滨州","菏泽"],河南:["郑州","开封","洛阳","平顶山","安阳","鹤壁","新乡","焦作","濮阳","许昌","漯河","三门峡","南阳","商丘","信阳","周口","驻马店"],湖北:["武汉","黄石","十堰","荆州","宜昌","襄樊","鄂州","荆门","孝感","黄冈","咸宁","随州","恩施"],湖南:["长沙","株洲","湘潭","衡阳","邵阳","岳阳","常德","张家界","益阳","郴州","永州","怀化","娄底","湘西"],广东:["广州","深圳","珠海","汕头","韶关","佛山","江门","湛江","茂名","肇庆","惠州","梅州","汕尾","河源","阳江","清远","东莞","中山","潮州","揭阳","云浮"],海南:["海口","三亚"],四川:["成都","自贡","攀枝花","泸州","德阳","绵阳","广元","遂宁","内江","乐山","南充","眉山","宜宾","广安","达州","雅安","巴中","资阳","阿坝","甘孜","凉山"],贵州:["贵阳","六盘水","遵义","安顺","铜仁","毕节","黔西南","黔东南","黔南"],云南:["昆明","曲靖","玉溪","保山","昭通","丽江","普洱","临沧","德宏","怒江","迪庆","大理","楚雄","红河","文山","西双版纳","腾冲"],陕西:["西安","铜川","宝鸡","咸阳","渭南","延安","汉中","榆林","安康","商洛"],甘肃:["兰州","嘉峪关","金昌","白银","天水","武威","酒泉","张掖","庆阳","平凉","定西","陇南","临夏","甘南"],青海:["西宁","海东","海北","海南","黄南","果洛","玉树","海西"],内蒙古:["呼和浩特","包头","乌海","赤峰","通辽","鄂尔多斯","呼伦贝尔","巴彦淖尔","乌兰察布","锡林郭勒盟","兴安盟","阿拉善盟"],广西:["南宁","柳州","桂林","梧州","北海","防城港","钦州","贵港","玉林","百色","贺州","河池","来宾","崇左"],西藏:["拉萨","那曲","昌都","林芝","山南","日喀则","阿里"],宁夏:["银川","石嘴山","吴忠","固原","中卫"],新疆:["乌鲁木齐","克拉玛依","吐鲁番","哈密","和田","阿克苏","喀什","克孜勒苏","巴音郭楞","昌吉","博尔塔拉","伊犁","塔城","阿勒泰"],香港:["香港岛","九龙东","九龙西","新界东","新界西"],澳门:["澳门半岛","离岛"],台湾:["台北","高雄","基隆","新竹","台中","嘉义","台南"],加拿大温哥华: ["加拿大温哥华"]};
function province(a){
    for(var i in city){
        if(city[i].indexOf(a) > -1 ){
            return i;
        }
    }
}

var myChart1,myChart2,myChart3,myChart4,myChart5;

// 函数封装
function showMap(start_time,end_time){
    $.ajax({
        type: 'get',
        url: 'ScheduleProvince',
        data:{
            StartTime : start_time,
            EndTime : end_time,
            Name:""
        },
        success:function(data){
            console.log("出差省份分布");
            console.log(data);
            console.log(typeof data);
            data = JSON.parse(data);
            console.log(data);
            console.log(typeof(data));
            var newArrProvince = [];
            var newArrNo = [];
            if(data.length>1){
                for(var i = 1;i<data.length-1;i++){
                    if(data[i].province!=""&&data[i].province!="--"){
                        newArrProvince.push(data[i].province);
                    }
                    if(data[i].times!=""&&data[i].times!="--"){
                        newArrNo.push(Number(data[i].times));
                    }
                }
                console.log(newArrProvince);
                console.log(newArrNo);
                $(".mapContent").html('');
                var str = '<div id="main"></div>';
                $(".mapContent").append(str);
                provMap(newArrProvince,newArrNo);
            }else{
                $.MsgBox_Unload.Alert("提示","该时间段无出差省份记录");
            }
            
        },
        error:function(){
            alert("网络错误")
        }
    });
    
    
// $.ajax({
//          type: 'get',
//          url: 'ScheduleByTime',
//          dataType : 'json', 
//          data:{
//              StartTime : start_time,
//              EndTime : end_time,
//              Name:""
//              },
//           success: function(result){
//              console.log(222222222222)
//              console.log(result);
                                
//              /* {"常州市":"1","苏州市":"8","徐州市":"1","大连市":"15","北京市":"1","厦门市":"13","盐城市":"1","南京市":"1"} */
                                
//              console.log("heheheheheh")
//              console.log(result);
//              var cityArr = [];
//              var numberArr = [];
//              var allcityArr = [];
//              var allnumberArr = [];
//              for(var k in result){
//                  console.log('123'+k+result[k])
//                  //如果k 为空或者异常，去掉数组中的该项
//                  if(k == ''){
//                      /* alert(k)
//                      alert(result[k]) */
//                      delete result[k];
//                  }
//                      console.log("删除城市名为空的后")
//                      console.log('666'+k+result[k])
//                      console.log("hhh")
//                      console.log(result.toString)
//                  if(result[k] == 0 || result[k] == ''){
//                      delete result[k];
//                      console.log("再次删除城市对应的次数为空或者0的后")
//                      console.log('777'+k+result[k])
//                  }
//                  if(k.lastIndexOf('市') == k.length-1 && k.lastIndexOf('市') != -1){
//                      var city = k.substring(0,k.length-1);
//                      var prov = province(city);
//                      console.log("传进来的带上市，插件要求不带处理去掉")
//                      console.log(prov)
//                      console.log(result);
//                      if(!prov && city != "北京" && city != "上海" && city != "重庆" && city != "天津" && city != "香港" && city != "澳门"){
//                          delete result[k];
//                      }
//                      var number = city + '市';
//                  }else{
//                      var city = k;
//                      var prov = province(city);
//                      console.log("传进来的没有带市")
//                      console.log(prov)
//                      console.log(result);
//                      if(!prov && city != "北京" && city != "上海" && city != "重庆" && city != "天津" && city != "香港" && city != "澳门"){
//                          delete result[k];
//                      }
//                      var number = city;
//                  }
//                      console.log("最终输出的城市数组")
//                      console.log(result)
//                      console.log('888'+k+result[k])
//              }
                                
//              //对处理后的或者无需处理的result操作
//              for(var k in result){
//                  if(k.lastIndexOf('市') == k.length-1 && k.lastIndexOf('市') != -1){
//                      var city = k.substring(0,k.length-1);
//                      var prov = province(city);
//                      console.log(prov)
//                      var number = city + '市';
//                  }else{
//                      var city = k;
//                      var prov = province(city);
//                      console.log(prov)
//                      console.log('999'+k+result[k])
//                      var number = city;
//                  }
                
//              //获取省份以后，和用户点击的省份做比较，省份一样的保留city到新的数组中，保留对应的result[k]
                                        
//                  allcityArr.push(city);
//                  allnumberArr.push(result[number])
                    
//                  $(".mapContent").html('');
//                  var str = '<div id="main"></div>';
//                  $(".mapContent").append(str);
//                  provMap(provArr);
//                      BarMap(Name,allcityArr,allnumberArr);                   
                                            
//              }
//          },
//          error:function(){}
//      });

}

//地图模块
function provMap(newArrProvince,newArrNo){
    var dataArr = [];
    for(var i = 0;i<newArrProvince.length;i++){
        // console.log(provArr[i]); 
        var json = {};
        if(newArrProvince[i].indexOf("内蒙古")>-1){
            json.name = "内蒙古";
        }else if(newArrProvince[i].indexOf("自治区")>-1){
            json.name = newArrProvince[i].substring(0,2);
        }else{
            json.name = newArrProvince[i].substring(0,newArrProvince[i].length - 1);
        }
        json.value = newArrNo[i];
        dataArr.push(json);
    }
    console.log("出差统计分布，去除后缀的省");
    console.log(dataArr);
    console.log(typeof(dataArr));
     myChart1 = echarts.init(document.getElementById('main'));
      var ecConfig = echarts.config;  
var zrEvent = zrender.tool.event;
var curIndx = 0;
var mapType = [
];
fun(echarts);
function fun(ec){
              // 基于准备好的dom，初始化echarts图表
              myChart1 = ec.init(document.getElementById("main")); 
                console.log("基于准备好的dom，初始化echarts图表");
                 console.log(dataArr);
                 
            var option = {
                         tooltip : {
                             trigger: 'item',
                             formatter: '{b}'
                         },
                         series : [
                             {
                                 name: '中国',
                                 type: 'map',
                                 mapType: 'china',
                                 roam: false,
                                 selectedMode : 'multiple',
                                 itemStyle:{
                                     normal:{label:{show:true}},
                                     emphasis:{label:{show:true}},
                                 },
                                 data:dataArr
                                 // data:newArrProvince
                             }
                         ],
                         dataRange: {
                                 x: 'left',
                                 y: 'bottom',
                                 splitList: [
                                     // {start: 1500},
                                     // {start: 900, end: 1500},
                                     {start: 300},
                                     {start: 150, end: 300},
                                     {start: 50, end: 150},
                                     {start: 1, end: 50},
                                     {end: 1}
                                 ],
                                 color: ['#c00000','#ff0000','#ff9600','#ffff00','#fff']
                             },
                             // roamController: {
                             //         show: true,
                             //         x: 'right',
                             //         mapTypeControl: {
                             //             'china': true
                             //         }
                             //     }
            };
                 

            // update
            // var option = {
            //     tooltip: {
            //         trigger: 'item',
            //         formatter: '{b}'
            //     },
            //     visualMap: {
            //         seriesIndex: 0,
            //         min: 0,
            //         max: 500,
            //         left: 'left',
            //         top: 'bottom',
            //         text: ['高','低'],           // 文本，默认为数值文本
            //         calculable: true
            //         // controller:{
            //         //   inRange:{
            //         //       color:[rgb(255,255,255),rgb(255,255,0),rgb(255,128,0),rgb(255,0,0),rgb(116,0,0)]
            //         //   }
            //         // }
            //     },
            //     grid: {
            //         height: 200,
            //         width: 8,
            //         right: 80,
            //         bottom: 10
            //     },
            //     xAxis: {
            //         type: 'category',
            //         data: [],
            //         splitNumber: 1,
            //         show: false
            //     },
            //     yAxis: {
            //         position: 'right',
            //         min: 0,
            //         max: 20,
            //         splitNumber: 20,
            //         inverse: true,
            //         axisLabel: {
            //             show: true
            //         },
            //         axisLine: {
            //             show: false  
            //         },
            //         splitLine: {
            //             show: false
            //         },
            //         axisTick: {
            //             show: false
            //         },
            //         data: []
            //     },
            //     series: [
            //         {
            //             zlevel: 1,
            //             name: '中国',
            //             type: 'map',
            //             mapType: 'china',
            //             selectedMode : 'multiple',
            //             roam: true,
            //             left: 0,
            //             right: '15%',
            //             label: {
            //                 normal: {
            //                     show: true
            //                 },
            //                 emphasis: {
            //                     show: true
            //                 }
            //             },
            //             data:dataArr
            //             // [
            //             //     {name: '北京',value: randomData() },
            //             //     {name: '天津',value: randomData() },
            //             //     {name: '上海',value: randomData() },
            //             //     {name: '重庆',value: randomData() },
            //             //     {name: '河北',value: randomData() },
            //             //     {name: '河南',value: randomData() },
            //             //     {name: '云南',value: randomData() },
            //             //     {name: '辽宁',value: randomData() },
            //             //     {name: '黑龙江',value: randomData() },
            //             //     {name: '湖南',value: randomData() },
            //             //     {name: '安徽',value: randomData() },
            //             //     {name: '山东',value: randomData() },
            //             //     {name: '新疆',value: randomData() },
            //             //     {name: '江苏',value: randomData() },
            //             //     {name: '浙江',value: randomData() },
            //             //     {name: '江西',value: randomData() },
            //             //     {name: '湖北',value: randomData() },
            //             //     {name: '广西',value: randomData() },
            //             //     {name: '甘肃',value: randomData() },
            //             //     {name: '山西',value: randomData() },
            //             //     {name: '内蒙古',value: randomData() },
            //             //     {name: '陕西',value: randomData() },
            //             //     {name: '吉林',value: randomData() },
            //             //     {name: '福建',value: randomData() },
            //             //     {name: '贵州',value: randomData() },
            //             //     {name: '广东',value: randomData() },
            //             //     {name: '青海',value: randomData() },
            //             //     {name: '西藏',value: randomData() },
            //             //     {name: '四川',value: randomData() },
            //             //     {name: '宁夏',value: randomData() },
            //             //     {name: '海南',value: randomData() },
            //             //     {name: '台湾',value: randomData() },
            //             //     {name: '香港',value: randomData() },
            //             //     {name: '澳门',value: randomData() }
            //             // ]
            //         },
            //         {
            //             zlevel: 2,
            //             name: '地图指示',
            //             type: 'bar',
            //             barWidth: 5,
            //             itemStyle: {
            //                 normal: {
            //                     color: undefined,
            //                     shadowColor: 'rgba(0, 0, 0, 0.1)',
            //                     shadowBlur: 10
            //                 }
            //             },
            //             data: [20]
            //         }
            //     ]
            // };


            /**
             * 根据值获取线性渐变颜色
             * @param  {String} start 起始颜色
             * @param  {String} end   结束颜色
             * @param  {Number} max   最多分成多少分
             * @param  {Number} val   渐变取值
             * @return {String}       颜色
             */
            // function getGradientColor (start, end, max, val) {
            //     var rgb = /#((?:[0-9]|[a-fA-F]){2})((?:[0-9]|[a-fA-F]){2})((?:[0-9]|[a-fA-F]){2})/;
            //     var sM = start.match(rgb);
            //     var eM = end.match(rgb);
            //     var err = '';
            //     max = max || 1
            //     val = val || 0
            //     if (sM === null) {
            //         err = 'start';
            //     }
            //     if (eM === null) {
            //         err = 'end';
            //     }
            //     if (err.length > 0) {
            //         throw new Error('Invalid ' + err + ' color format, required hex color'); 
            //     }
            //     var sR = parseInt(sM[1], 16),
            //         sG = parseInt(sM[2], 16),
            //         sB = parseInt(sM[3], 16);
            //     var eR = parseInt(eM[1], 16),
            //         eG = parseInt(eM[2], 16),
            //         eB = parseInt(eM[3], 16);
            //     var p = val / max;
            //     var gR = Math.round(sR + (eR - sR) * p).toString(16),
            //         gG = Math.round(sG + (eG - sG) * p).toString(16),
            //         gB = Math.round(sB + (eB - sB) * p).toString(16);
            //     return '#' + gR + gG + gB;
            // }

            // setTimeout(function() {
            //     var TOPN = 25
                
            //     var option = myChart.getOption()
            //     // 修改top
            //     option.grid[0].height = TOPN * 20
            //     option.yAxis[0].max = TOPN
            //     option.yAxis[0].splitNumber = TOPN
            //     option.series[1].data[0] = TOPN
            //     // 排序
            //     var data = option.series[0].data.sort(function(a, b) {
            //         return b.value - a.value
            //     })
                
            //     var maxValue = data[0].value,
            //         minValue = data.length > TOPN ? data[TOPN - 1].value : data[data.length - 1].value
                
            //     var s = option.visualMap[0].controller.inRange.color[0],
            //         e = option.visualMap[0].controller.inRange.color.slice(-1)[0]
            //     var sColor = getGradientColor(s, e, maxValue, minValue)
            //     var eColor = getGradientColor(s, e, maxValue, maxValue)
                
            //     option.series[1].itemStyle.normal.color = new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
            //         offset: 1,
            //         color: sColor
            //     }, {
            //         offset: 0,
            //         color: eColor
            //     }])
                
            //     // yAxis
            //     var newYAxisArr = []
            //     echarts.util.each(data, function(item, i) {
            //         if (i >= TOPN) {
            //             return false
            //         }
            //         var c = getGradientColor(sColor, eColor, maxValue, item.value)
            //         newYAxisArr.push({
            //             value: item.name,
            //             textStyle: {
            //                 color: c
            //             }
            //         })
            //     })
            //     option.yAxis[0].data = newYAxisArr
            //     option.yAxis[0].axisLabel.formatter = (function(data) {
            //         return function(value, i) {
            //             if (!value) return ''
            //             return value + ' ' + data[i].value
            //         }
            //     })(data)
            //     myChart.setOption(option)
            // }, 0);

              // 为echarts对象加载数据 
              myChart1.setOption(option); 
                //地图模块回调
                 // myChart.on('click', function (parmas) {
                    //  console.log(parmas);
                    //  console.log(parmas.name);
                    //  var $tongji = $(".tongji_btn");
                    //  var start_time = $(".start_time").val();
                    //  var end_time = $(".end_time").val();
                    //  var Name = $(".tongji .staffName").find('option:selected').attr('value');
                    //  if(Name == '所有人'){
                    //   Name = '';
                    //  }
                       
                    // });
    }
}

// 根据时间段显示地图
function showRangeTimeMap(){
    var re = /^(\d{4})-(\d{2})-(\d{2})$/ ;
    showMap(schedule_startTime,schedule_endTime);
}

// 距离统计
function ajaxDistanceStatistics(startTime,endTime){
    return $.ajax({
            type: 'get',
            url: 'DistanceStatistics',
            dataType : 'json', 
            data:{
                startTime: startTime,
                endTime: endTime
                },
            success:function(data){
                console.log("这里是距离统计");
                console.log(data);
                console.log(typeof data);
                var new2CityName = [];
                var new2CityTimes = [];
                if(data.length>1){
                        for(var i=1;i<data.length;i++){
                            new2CityName.push(data[i].Name);
                            if(data[i].SumDistance==""||data[i].SumDistance=="--"||data[i].SumDistance==null){
                                data[i].SumDistance=0;
                            }
                            var tempSumDistance = data[i].SumDistance;
                            var intDistance = (tempSumDistance*1).toFixed(0);
                            new2CityTimes.push(intDistance);
                        }
                        console.log("最新的");
                        console.log(new2CityName);
                        console.log(new2CityTimes);

                        // for (var key in data) {
                        //     console.log(key);     //获取key值
                        //     console.log(data[key]); //获取对应的value值
                        //     new2CityName.push(key);
                        //     if(data[key]=="--"||data[key]==""||data[key]==null){
                        //      data[key]=0;
                        //     }
                        //     new2CityTimes.push(Number(data[key]));
                        //     console.log("新的")
                        //     console.log(key);     //获取key值
                        //     console.log(data[key]); //获取对应的value值
                        // }
                        $(".trip-distance-body").html('');
                        var str = '<div id="main3"></div>';
                        $(".trip-distance-body").append(str);
                        MapDistanceStatistics(new2CityName,new2CityTimes);
                    }else{
                        $.MsgBox_Unload.Alert("提示","该时间段无距离统计记录");
                    }
                
            },
            error:function(){
                $.MsgBox.Alert("提示","网络繁忙，请重新加载");
            }
        });
}

function showDistanceStatistics(){
    ajaxDistanceStatistics(schedule_startTime,schedule_endTime);
}

function MapDistanceStatistics(new2CityName,new2CityTimes){
    myChart2 = echarts.init(document.getElementById('main3'));
        var option = {
                title : {
                    text: '单位：km',
                    textStyle: {
                        color:'#999',
                        fontSize: 14
                    }
                    // subtext: Name
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['','']
                },
                
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        // data : cityArr,
                        data : new2CityName,
                        axisLabel:{  
                            interval:0,//横轴信息全部显示  
                            rotate:0,//-30度角倾斜显示 
                            textStyle:{
                                color: 'rgba(0,0,0,0.9)',
                                // fontFamily: '微软雅黑,宋体,Arial, Verdana, sans-serif',
                                fontSize:14 // 让字体变大
                            },
                            fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
                            // formatter:function(value)  
                   //          {  
                   //             return value.split("").join("\n");  
                   //          } 
                            formatter:function(value)  
                             {  
                                 // debugger  
                                 var ret = "";//拼接加\n返回的类目项  
                                 var maxLength = 1;//每项显示文字个数  
                                 // var maxLength = 2;//每项显示文字个数  
                                 var valLength = value.length;//X轴类目项的文字个数  
                                 var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数  
                                 if (rowN > 1)//如果类目项的文字大于3,  
                                 {  
                                     for (var i = 0; i < rowN; i++) {  
                                         var temp = "";//每次截取的字符串  
                                         var start = i * maxLength;//开始截取的位置  
                                         var end = start + maxLength;//结束截取的位置  
                                         //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧  
                                         temp = value.substring(start, end) + "\n";  
                                         ret += temp; //凭借最终的字符串  
                                     }  
                                     return ret;  
                                 }  
                                 else {  
                                     return value;  
                                 }  
                             }
                        },
                        splitLine:{ 
                            show:false
                        }
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'距离',
                        type:'bar',
                        // data:numberArr,
                        data:new2CityTimes,
                        markPoint : {
                            data : []
                        },
                        markLine : {
                            data : []
                        },
                        // 头部显示数据
                        itemStyle:{
                            normal:{
                                color: function(params) {
                                    // build a color map as your need.
                                    var colorList = [
                                      '#EE4775','#E3497F','#DD4B84','#D04B84','#CA4D91','#C04D91','#BF4E9A','#B84FA0','#B04FA0','#AA51AA','#A051AA','#9353BB','#8754C3','#8054C3','#7E56CA','#7056CA','#6F58D7','#685AE1','#605AE1','#5B5BE6','#505BE6','#4F5CF2','#4A5CF2','#455CF2','#405CA2','#3B5CA2','#305CA2'
                                    ];
                                    return colorList[(params.dataIndex)%(colorList.length)];
                                },
                                // 
                                // color: function(params) {
                                //     // build a color map as your need.
                                //     var colorList = [
                                //       ['#EE4775','#E3497F','#DD4B84'],
                                //       ['#CA4D91','#BF4E9A','#B84FA0'],
                                //       ['#B84FA0','#AA51AA','#9353BB'],
                                //       ['#8754C3','#7E56CA','#6F58D7'],
                                //       ['#625AE1','#5B5BE6','#4B5CF2']
                                //     ];
                                //     return new echarts.graphic.LinearGradient(0, 0, 0, 1,[
                                //      {offset: 0, color: colorList[params.dataIndex][0]},
                                //      {offset: 0.5, color: colorList[params.dataIndex][1]},
                                //      {offset: 1, color: colorList[params.dataIndex][2]}
                                //      ]);
                                // },
                               
                                barBorderRadius: 4,  //柱状角成椭圆形
                                label:{
                                    show:true,
                                    position:'top',
                                    textStyle: {
                                        color:'rgba(0,0,0,0.7)',
                                        fontSize:10
                                    },
                                    formatter:function(params){
                                        if(params.value==0){
                                            return '';
                                        }else{
                                            return params.value;
                                            }
                                    }
                                }
                            },
                            emphasis: {
                                barBorderRadius: 7
                            },
                        }
                  //       label: {
                        //  normal:{
                        //      show:true,
                        //      position:'top',
                        //      textStyle: {
                        //          color:'black'
                        //      }
                        //  }
                        // }
                    },
               
                 
                ]
        };     
    myChart2.setOption(option);
}


// 柱状图ajax
function barGraphAjax(start_time,end_time,Name) {
    return $.ajax({
            type: 'get',
            url: 'ScheduleByTime',
            dataType : 'json', 
            data:{
                StartTime : start_time,
                EndTime : end_time,
                Name:Name
                },
            success: function(result){
                console.log("#员工按姓名出差原始数据");
                console.log(result);
                console.log(typeof result);
                // 兼容老浏览器
                if (!Object.keys) {
                    Object.keys = function (obj) {
                        var keys = [],
                            k;
                        for (k in obj) {
                            if (Object.prototype.hasOwnProperty.call(obj, k)) {
                                keys.push(k);
                            }
                        }
                        return keys;
                    };
                }
                var cityArr = [];
                var numberArr = [];
                var allcityArr = [];
                var allnumberArr = [];
                var allcityArrnull = ["苏州"];
                var allnumberArrnull = ["0"];
                if(Object.keys(result).length){
                    for(var k in result){
                        if(k == ''){
                            delete result[k];
                        }
                        if(result[k] == 0 || result[k] == ''){
                            delete result[k];
                        }
                        // 去市及直辖市处理
                        if(k.lastIndexOf('市') == k.length-1 && k.lastIndexOf('市') != -1){
                            var city = k.substring(0,k.length-1);
                            var prov = province(city);
                            if(!prov && city != "北京" && city != "上海" && city != "重庆" && city != "天津" && city != "香港" && city != "澳门"){
                                delete result[k];
                            }
                            var number = city + '市';
                        }else{
                            // 不带市
                            var city = k;
                            var prov = province(city);
                            if(!prov && city != "北京" && city != "上海" && city != "重庆" && city != "天津" && city != "香港" && city != "澳门"){
                                delete result[k];
                            }
                            var number = city;
                        }
                    }
                    console.log("最终输出的城市数组");
                    console.log(result);
                    //对处理后的或者无需处理的result操作
                    for(var k in result){
                        if(k.lastIndexOf('市') == k.length-1 && k.lastIndexOf('市') != -1){
                            var city = k.substring(0,k.length-1);
                            var prov = province(city);
                            var number = city + '市';
                        }else{
                            var city = k;
                            var prov = province(city);
                            var number = city;
                        }
                    
                    //获取省份以后，和用户点击的省份做比较，省份一样的保留city到新的数组中，保留对应的result[k]
                        allcityArr.push(city);
                        allnumberArr.push(result[number])
                    }
                    console.log("@员工按姓名出差城市分布，处理后的数据");
                    console.log(allcityArr);
                    console.log(allnumberArr);
                    $(".trip-city-body").html('');
                    var str = '<div id="main2"></div>';
                    $(".trip-city-body").append(str);
                    // BarMap(Name,cityArr,numberArr);
                    BarMap(Name,allcityArr,allnumberArr);
                }else{
                    BarMap(Name,allcityArrnull,allnumberArrnull);
                    $.MsgBox_Unload.Alert("提示","该员工此时间段无出差记录");
                }   
            },
            error:function(){
                console.log("***员工出差城市次数统计获取失败了***");
            }
        });
}

function BarMap(Name,cityArr,numberArr){
    
    myChart3 = echarts.init(document.getElementById('main2'));
     function map(){
         
            var option = {
                title : {
                    text: '单位：次数',
                    subtext: Name,
                    textStyle: {
                        color:'#999',
                        // color:'rgba(0,0,0,0.8)',
                        fontSize:14
                    }
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['','']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        data : cityArr,
                        axisLabel:{  
                            interval:0,//横轴信息全部显示  
                            rotate:0,//-30度角倾斜显示 
                            textStyle:{
                                color: 'rgba(0,0,0,0.9)',
                                // fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
                                fontSize:14 // 让字体变大
                            },
                            fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
                            formatter:function(value)  
                            {  
                               return value.split("").join("\n");  
                            }  
                        },
                        splitLine:{ 
                            show:false
                        }
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'次数',
                        type:'bar',
                        data:numberArr,
                        markPoint : {
                            data : []
                        },
                        markLine : {
                            data : []
                        },
                        // 头部显示数据
                        itemStyle:{
                            normal:{
                                // color: function(params) {
                                //     // build a color map as your need.
                                //     var colorList = [
                                //       '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                                //        '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                                //        '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
                                //     ];
                                //     return colorList[params.dataIndex]
                                // },
                                color: function(params) {
                                    // build a color map as your need.
                                    var colorList = [
                                      '#EE4775','#E3497F','#DD4B84','#D04B84','#CA4D91','#C04D91','#BF4E9A','#B84FA0','#B04FA0','#AA51AA','#A051AA','#9353BB','#8754C3','#8054C3','#7E56CA','#7056CA','#6F58D7','#685AE1','#605AE1','#5B5BE6','#505BE6','#4F5CF2','#4A5CF2','#455CF2','#405CF2','#3B5CA2','#305CA2'
                                    ];
                                    // return colorList[params.dataIndex];
                                    return colorList[(params.dataIndex)%(colorList.length)];
                                },
                                barBorderRadius: 5,
                                label:{
                                    show:true,
                                    position:'top',
                                    textStyle: {
                                        color:'rgba(0,0,0,0.7)',
                                        fontSize:10
                                    },
                                    formatter:function(params){
                                        if(params.value==0){
                                            return '';
                                        }else{
                                            return params.value;
                                            }
                                    }
                                }
                            }
                        }
                    },
                 
                ]
            };     
    myChart3.setOption(option);
    }
    map();
}

// 员工出差城市分布显示
function showTripCity(){
    var Name = $(".trip-city-tit-right .staffName").val();
    if (Name == "所有人"){
        Name = "";
    }
    $(".trip-city-tit-left span").text(Name);
    barGraphAjax(schedule_startTime,schedule_endTime,Name);
}


// *****第三个选项卡
// 员工出差次数排行
// echartContainer1:员工出差次数排行
function echartsStaffTripTimes(StaffTripTimes_Name,StaffTripTimes_times){
    myChart4 = echarts.init(document.getElementById('echartContainer1'));
    var option = {
            title : {
                text: '单位：次数',
                // subtext: Name,
                textStyle: {
                    color:'#999',
                    fontSize:14
                }
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:['','']
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    data : StaffTripTimes_Name,
                    axisLabel:{  
                        interval:0,//横轴信息全部显示  
                        rotate:0,//-30度角倾斜显示 
                        textStyle:{
                            color: 'rgba(0,0,0,0.9)',
                            fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
                            fontSize:14 // 让字体变大
                        },  
                        // fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
                        formatter:function(value)  
                        {  
                           return value.split("").join("\n");  
                        }
                    },
                    splitLine:{ 
                        show:false
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'次数',
                    type:'bar',
                    data:StaffTripTimes_times,
                    markPoint : {
                        data : []
                    },
                    markLine : {
                        data : []
                    },
                    // 头部显示数据
                    itemStyle:{
                        normal:{
                            color: function(params) {
                                // build a color map as your need.
                                var colorList = [
                                  '#EE4775','#E3497F','#DD4B84','#D04B84','#CA4D91','#C04D91','#BF4E9A','#B84FA0','#B04FA0','#AA51AA','#A051AA','#9353BB','#8754C3','#8054C3','#7E56CA','#7056CA','#6F58D7','#685AE1','#605AE1','#5B5BE6','#505BE6','#4F5CF2','#4A5CF2','#455CF2','#405CF2','#3B5CA2','#305CA2'
                                ];
                                return colorList[(params.dataIndex)%(colorList.length)];
                            },
                            barBorderRadius: 5,
                            label:{
                                show:true,
                                position:'top',
                                textStyle: {
                                    color:'rgba(0,0,0,0.7)',
                                    fontSize:10
                                },
                                formatter:function(params){
                                    if(params.value==0){
                                        return '';
                                    }else{
                                        return params.value;
                                        }
                                }
                            }
                        }
                    }
                },
             
            ]
    };
    myChart4.setOption(option);
}

function StaffTripTimesAjax(start_time,end_time){
    return $.ajax({
        type: 'get',
        url: 'FrequenceStatistics',
        dataType : 'json', 
        data:{
            startTime : start_time,
            endTime : end_time
            // Name:Name
        },
        success: function(data){
            console.log("@@@员工出差次数排行");
            console.log(typeof data);
            console.log(data);
            var StaffTripTimes_Name = [];
            var StaffTripTimes_times = [];
            if(data.length>1){
                for (var i = 1;i<data.length;i++){
                    if(data[i].Name != null && data[i].Name != ""){
                        StaffTripTimes_Name.push(data[i].Name);
                    }
                    if(data[i].times != null && data[i].times != ""){
                        StaffTripTimes_times.push(Number(data[i].times));
                    }
                }
                if(StaffTripTimes_Name.length != StaffTripTimes_times.length){
                    $.MsgBox.Alert("提示","网络异常，请重新加载");
                }else{
                    console.log("员工出差次数排行处理后数据");
                    console.log(StaffTripTimes_Name);
                    console.log(StaffTripTimes_times);
                    $(".trip-staff-times-body").html('');
                    var str = '<div id="echartContainer1"></div>';
                    $(".trip-staff-times-body").append(str);
                    echartsStaffTripTimes(StaffTripTimes_Name,StaffTripTimes_times);
                }
            }else{
                $.MsgBox_Unload.Alert("提示","该时间段无出差次数排行");
            }
            
        },
        error: function(){
            $.MsgBox_Unload.Alert("提示","网络出错，出差次数排行绘制失败");
        }
    });
}

function showStaffTripTimes(){
    StaffTripTimesAjax(schedule_startTime,schedule_endTime);
}

// 所有员工差旅费统计
// echartContainer2 所有员工差旅费统计
// 
function echartsExpenseStatistics(ExpenseStatistics_Destination,ExpenseStatistics_Expense){
    myChart5 = echarts.init(document.getElementById('echartContainer2'));
    var option = {
            title : {
                text: '单位：元',
                // subtext: Name,
                textStyle: {
                    color:'#999',
                    fontSize:14
                }
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:['','']
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    data : ExpenseStatistics_Destination,
                    axisLabel:{  
                        interval:0,//横轴信息全部显示  
                        rotate:0,//-30度角倾斜显示 
                        textStyle:{
                            color: 'rgba(0,0,0,0.9)',
                            // fontFamily: 'sans-serif',
                            fontSize:14 // 让字体变大
                        },
                        fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
                        formatter:function(value)  
                        {  
                           return value.split("").join("\n");  
                        }  
                    },
                    splitLine:{ 
                        show:false
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'差旅费',
                    type:'bar',
                    data:ExpenseStatistics_Expense,
                    markPoint : {
                        data : []
                    },
                    markLine : {
                        data : []
                    },
                    // 头部显示数据
                    itemStyle:{
                        normal:{
                            color: function(params) {
                                // build a color map as your need.
                                var colorList = [
                                  '#EE4775','#E3497F','#DD4B84','#D04B84','#CA4D91','#C04D91','#BF4E9A','#B84FA0','#B04FA0','#AA51AA','#A051AA','#9353BB','#8754C3','#8054C3','#7E56CA','#7056CA','#6F58D7','#685AE1','#605AE1','#5B5BE6','#505BE6','#4F5CF2','#4A5CF2','#455CF2','#405CF2','#3B5CA2','#305CA2'
                                ];
                                return colorList[(params.dataIndex)%(colorList.length)];
                            },
                            barBorderRadius: 5,
                            label:{
                                show:true,
                                position:'top',
                                textStyle: {
                                    color:'rgba(0,0,0,0.7)',
                                    fontSize:10
                                },
                                formatter:function(params){
                                    if(params.value==0){
                                        return '';
                                    }else{
                                        return params.value;
                                        }
                                }
                            }
                        }
                    }
                },
             
            ]
    };
    myChart5.setOption(option);
}

function ExpenseStatisticsAjax(start_time,end_time){
    return $.ajax({
        type: 'get',
        url: 'ExpenseStatistics',
        dataType : 'json', 
        data:{
            startTime : start_time,
            endTime : end_time
            // Name:Name
        },
        success: function(data){
            console.log("#费用统计原始数据");
            console.log(data);
            console.log(typeof data);
            var ExpenseStatistics_Destination = [];
            var ExpenseStatistics_Expense = [];
            var ExpenseStatistics_Expensenull = ["苏州市"];
            var ExpenseStatistics_Expensenull = ["0"];
            if(data.length > 1){
                for(i=1;i<data.length;i++){
                    var city = data[i].Destination;
                    var expense = data[i].Expense;
                    if(city.lastIndexOf('市') == city.length-1 && city.lastIndexOf('市') != -1){
                        city = city.substring(0,city.length-1);
                        // if(city != "北京" && city != "上海" && city != "重庆" && city != "天津" && city != "香港" && city != "澳门"){
                  //        data.splice(i, 1);
                        // }

                    }else if(city.lastIndexOf('县') == city.length-1 && city.lastIndexOf('县') != -1){
                        city = city.substring(0,city.length-1);
                    }

                    // 再次处理
                    if(city.lastIndexOf('市') == city.length-1 && city.lastIndexOf('市') != -1){
                        city = city.substring(0,city.length-1);
                    }else if(city.lastIndexOf('县') == city.length-1 && city.lastIndexOf('县') != -1){
                        city = city.substring(0,city.length-1);
                    }

                    // 费用处理
                    if(expense == null || expense == "" || expense == "--"){
                        expense = "0";
                    }
                    ExpenseStatistics_Destination.push(city);
                    ExpenseStatistics_Expense.push(expense);
                }
                if(ExpenseStatistics_Destination.length != ExpenseStatistics_Expense.length){
                    $.MsgBox.Alert("提示","网络异常，请重新加载");
                }else{
                    console.log("差旅费用处理后数据");
                    console.log(ExpenseStatistics_Destination);
                    console.log(ExpenseStatistics_Expense);
                    $(".trip-cost-calc-body").html('');
                    var str = '<div id="echartContainer2"></div>';
                    $(".trip-cost-calc-body").append(str);
                    echartsExpenseStatistics(ExpenseStatistics_Destination,ExpenseStatistics_Expense);
                }
                
            }else{
                echartsExpenseStatistics(ExpenseStatistics_Destinationnull,ExpenseStatistics_Expensenull);
                $.MsgBox_Unload.Alert("提示","该时间段无费用数据");
            }
        },
        error: function(){
            $.MsgBox_Unload.Alert("提示","网络出错，费用统计绘制失败");
        }
    });
}

function showExpenseStatistics(){
    ExpenseStatisticsAjax(schedule_startTime,schedule_endTime);
}

// // 图表自适应
// $(window).resize(function() { //这是能够让图表自适应的代码
//     myChart1.resize();
//     myChart2.resize();
//     myChart3.resize();
//     myChart4.resize();
//     myChart5.resize();
// });


//选项卡
var cruurntCardIndex;
// function calcBodyBottomH(){
//  var newH = $("")
// }
// var aa1H = 50;
// var aa2H = 750;
// var aa3H = 1280;
// $(".aa1").on("click",function(){
//   $(window).scrollTop(aa1H);
//   // $(window).stop(true).animate({scrollTop: aa1H}, 500);
// });
// $(".aa2").on("click",function(){
//   $(window).scrollTop(aa2H);
// });
// $(".aa3").on("click",function(){
//   $(window).scrollTop(aa3H);
// });

$(".changeBox .Domestic").click(function(){
    $(".changeBox").css("background","url(image/bg11.png)");
    $(".bodyContent").css("height","705px");
    var bodyContentH = $(".bodyContent").height()+190;
    $("body.nav-body").css("height",bodyContentH+"px");
    $(".reloadDiv").fadeOut(50);
    $(".mapBox").fadeOut(50);
    $(".trip-city").fadeOut(50);
    $(".trip-cost-calc").fadeOut(50);
    $(".trip-staff-times").fadeOut(50);
    $(".trip-distance").fadeOut(50);
    $("#engineer-schedule-container").fadeIn(50);
     // $(".ExitOrEn").css("background","url(image/bg3.png)");
     // $(".Domestic").css("background","none");
     // calcBodyBottomH();
});
$(".changeBox .ExitOrEn").click(function(){
    cruurntCardIndex = 2;
    $(".changeBox").css("background","url(image/bg22.png)");
    $(".bodyContent").css("height","1640px");
    var bodyContentH = $(".bodyContent").height()+190;
    $("body.nav-body").css("height",bodyContentH+"px");
    // aa1H = 50;
    // aa2H = $(".trip-city").offset().top - 50;
    // aa3H = $(".trip-distance").offset().top - 50;
         // var Y = new Date().getFullYear();
            // var thetime = Y+'-01-01T00:00:00-04:00';
            // var  sT=new   Date(Date.parse(thetime));
            // document.getElementById('start_time').valueAsDate = sT;
            // document.getElementById('end_time').valueAsDate = new Date(); 
    $("#engineer-schedule-container").hide();
    $(".trip-staff-times").fadeOut(50);
    $(".trip-distance").fadeOut(50);
    $(".reloadDiv").fadeIn(50);
    $(".mapBox").fadeIn(50);
    $(".trip-city").fadeIn(50);
    $(".trip-cost-calc").fadeIn(50);
    // calcBodyBottomH();
    var engineer = $("span.engineer").html();
    var engineerOb = engineer.split(',')
        var len = engineerOb.length;
        var str1 = '<select class="staffName" style="width: 90%;height:27px;border-radius:5px;border:1px solid #999">'+
                    '<option value="所有人">所有人</option>';
        var str2 = '';
        var str3 = '';
        var str4 = '</select>'
        for(var i = 1;i<len-1;i++){
                str2 += '<option value="'+engineerOb[i].substring(12,engineerOb[i].length-1)+'">'+engineerOb[i].substring(12,engineerOb[i].length-1)+'</option>';
        }
        str3 += '<option value="'+engineerOb[len-1].substring(12,engineerOb[len-1].length-2)+'">'+engineerOb[len-1].substring(12,engineerOb[len-1].length-2)+'</option>';
        Str = str1+str2+str3+str4;
        // $(".tongji .nameContent").remove();
        $(".trip-city-tit-right .staffName").remove();
        $(".trip-city-tit-right").append(Str);
        
     // $(".Domestic").css("background","url(image/bg3.png)");
     // $(".ExitOrEn").css("background","none");    
    switchDate();
    showRangeTimeMap();
    showTripCity();
    showExpenseStatistics();
});

$(".changeBox .staffData").on("click",function(){
    cruurntCardIndex = 3;
    $(".changeBox").css("background","url(image/bg33.png)");
    $(".bodyContent").css("height","1120px");
    var bodyContentH = $(".bodyContent").height()+190;
    $("body.nav-body").css("height",bodyContentH+"px");
    $("#engineer-schedule-container").hide();
    $(".mapBox").fadeOut(50);
    $(".trip-city").fadeOut(50);
    $(".trip-cost-calc").fadeOut(50);
    $(".reloadDiv").fadeIn(50);
    $(".trip-staff-times").fadeIn(50);
    $(".trip-distance").fadeIn(50);
    // calcBodyBottomH();
    switchDate();
    showStaffTripTimes();
    showDistanceStatistics();
});

//点击统计按钮，发送请求***
$(".tongji_btn").click(function(){
    switchDate();
    if(cruurntCardIndex == 2){
        // alert("2");
        showRangeTimeMap();
        showTripCity();
        showExpenseStatistics();
    }else if(cruurntCardIndex == 3){
        // alert("3");
        showStaffTripTimes();
        showDistanceStatistics();
    }
    // showRangeTimeMap();
    // showTripCity();
    // showDistanceStatistics();
});

$(document).on("change",".trip-city-tit-right .staffName",function(){
    switchDate();
    showTripCity();
});

var currentDate;
$(function(){
    var searchDate = '<%=request.getAttribute("allDate")%>';
    console.log(searchDate);
    if(searchDate != null && searchDate != "[{col1=Date}]"){
        searchDate = searchDate.replace(/ /g,"").replace(/{Date=/g,"").replace(/}/g,"").replace("{","").replace("[","").replace("]","").split(",").unique();
    } 
    console.log(searchDate);
    var currentHref = window.location.href;
    var myDate = new Date();
    if(currentHref.indexOf("-")>0){
        currentDate = currentHref.split("?Date=")[1];
         if(currentDate.indexOf("&")>-1)
         {
             currentDate = currentDate.split("&")[0];
         }
    }
    else{
        currentDate = globalGetToday(false);
    }
    /* 日历插件 */
    $('#calendar').fullCalendar({
        header: {
            left: '',
            center: 'prevYear,prev,title,next,nextYear',
            right: ''
        },
        buttonText: {
            prev: '<', // ‹
            next: '>', // ›
            prevYear: '<<', // «
            nextYear: '>>', // »
        },
        titleFormat: {
            month: "yyyy年MM月",
            week: '周ddd'
        },
        locale: "zh-cn",
        weekMode :5,
        firstDay:1,
        editable: false,
        timeFormat: 'H:mm',
        axisFormat: 'H:mm',
        minTime: '7',
        maxTime:'19',
        allDaySlot:false,
        dayNamesShort:['日','一','二','三','四','五','六'],
        events: eventsList(),
        dayClick: function(date, jsEvent, view) {
           //执行表格切换时候的数据函数
            var theDate = $.fullCalendar.formatDate(date,'yyyy-MM-dd')
           /*  alert('当天事件为:' + theDate);  */
            var Date=theDate;
            $('.table1 .DateTime').attr("value",Date);//暂存日期
            currentDate = theDate;
           
            $("#help-form #All_upload").val(Date);
            $("#help-form").submit(); 
            
        },
        eventClick:function(event, jsEvent, view) {
            
        },

    });
    //改变日历的宽高
    function get_height() {
        $('table.fc-border-separate tbody tr').width($('.fc-day.fc-六.fc-widget-content.fc-future').width())

    }
    
    //计算时间差值
    if(currentHref.indexOf("?Date=") >0){
        var myDate = new Date();
        var currentYear = currentHref.split("?Date=")[1].split("-")[0];
         var year = parseFloat(myDate.getFullYear());
        if(currentYear - year > 0){
            var nextYear = document.getElementsByClassName('fc-button-nextYear')[0];
            for(var i = 0 ;i < (currentYear - year) ; i++){
                nextYear.click();
            }
        }
        else if(currentYear - year < 0){
            var lastYear = document.getElementsByClassName('fc-button-prevYear')[0];
            for(var i = 0 ;i < (year - currentYear) ; i++){
                lastYear.click();
            }
        }
         var currentMonth = currentHref.split("?Date=")[1].split("-")[1];
         var month = parseFloat(myDate.getMonth())+1;
         if(currentMonth -month > 0){
                var nextMoth = document.getElementsByClassName('fc-button-next')[0];
                for(var i = 0 ;i < (currentMonth - month) ; i++){
                    nextMoth.click();
                }
            }
            else if(currentMonth - month < 0){
                var lastMonth = document.getElementsByClassName('fc-button-prev')[0];
                for(var i = 0 ;i < (month - currentMonth) ; i++){
                    lastMonth.click();
                }
            }
    }
    for(var i = 0 ;i < $(".fc-border-separate .fc-day").length ; i++){
        //console.log(searchDate)
        if(typeof(searchDate)=="object"){
            for(var j = 1;j <searchDate.length ; j++ ){
            /*  console.log($(".fc-border-separate .fc-day").eq(i).attr("data-date"))
               console.log(searchDate[j]) */
                if($(".fc-border-separate .fc-day").eq(i).attr("data-date") == searchDate[j]){
                    var str='<div class="fc_flag" style="width:8px;height:8px;border-radius: 50%;background:red;float: right;margin-top: 0px;"></div>';
                    $(".fc-border-separate .fc-day").eq(i).find(".fc-day-content").after(str);
                }
            }
        }
        if($(".fc-border-separate .fc-day").eq(i).attr("data-date") == currentDate){
            $(".fc-border-separate .fc-day").eq(i).addClass('fc-state-highlight');
            $(".fc-border-separate .fc-day").eq(i).siblings().removeClass('fc-state-highlight');
        }
    }
    $(".fc-button").click(function(){
        for(var i = 0 ;i < $(".fc-border-separate .fc-day").length ; i++){
            //console.log(searchDate)
            if(typeof(searchDate)=="object"){
                for(var j = 1;j <searchDate.length ; j++ ){
                    if($(".fc-border-separate .fc-day").eq(i).attr("data-date") == searchDate[j]){
                        var str='<div class="fc_flag" style="width:8px;height:8px;border-radius: 50%;background:red;float: right;margin-top: 10px;"></div>';
                        $(".fc-border-separate .fc-day").eq(i).find(".fc-day-content").after(str);
                    }
                }
            }
        }
    });
    
  //改变日历的颜色
    $('body').on('click', '.fc-day', function () {
             var obj = $(this), date = obj.data('date');
             $('.fc-state-highlight').removeClass('fc-state-highlight');
             obj.addClass('fc-state-highlight');
     });
    
    get_height();

    $('#calendar').on('click', 'span.fc-button', function () {
        get_height();
    });
   
   
    //日历上的显示数据
    function eventsList(){
        var data = [
           /* {
                title: ' ',
                start: "2017-12-04",
            } */
        ];
        return data;
    }
    
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
    
    
    //日历的标题
    
    //http://localhost:8080/cfChicken8/Schedule?Date=2018-04-01&currentPage=1
    //http://localhost:8080/cfChicken8/Schedule
    //http://localhost:8080/cfChicken8/Schedule?Date=2018-04-17
    var D=new Date(); 
    var ww=D.getDate();
    var Href = window.location.href;
    var i = Href.indexOf('?Date');
    if(i == '-1'){
        var numberT = ww;
        var DateT = new Date();
    }else{
        var Href1 = Href.substring(i+1); // "Date=2018-04-01&currentPage=1"
        Href1 = Href1.indexOf("&")>-1?Href1.split("&")[0]:Href1;
        var numberT = Href1.substring(13);
        if(numberT<10){
            numberT = numberT.substring(1);
        }
        var numberDate = Href1.substring(5) ;
        var DateT = new Date(Date.parse(numberDate));
    }
    
    var time = $(".fc-header-title h2").html();
    $(".dateBox_title").html(time);
    $(".dateBox_day").html(numberT);
    //日历上显示今天星期几
    function myFunction(DateT)
    {
        var d = DateT;
        var weekday=new Array(7);
        weekday[0]="星期日";
        weekday[1]="星期一";
        weekday[2]="星期二";
        weekday[3]="星期三";
        weekday[4]="星期四";
        weekday[5]="星期五";
        weekday[6]="星期六";
        /* var x = document.getElementById("demo");
        x.innerHTML=weekday[d.getDay()]; */
        $(".dataBox_textDay").html(weekday[d.getDay()]);
    }
    myFunction(DateT);
    
    //日历标题上显示农历
    /*获取当前农历*/
    function showCal(DateT){ 
    var D=DateT; 
    var yy=D.getFullYear(); 
    var mm=D.getMonth()+1; 
    var dd=D.getDate(); 
    var ww=D.getDay(); 
    var ss=parseInt(D.getTime() / 1000); 
    if (yy<100) yy="19"+yy; 
    return GetLunarDay(yy,mm,dd); 
    } 
     
    //定义全局变量 
    var CalendarData=new Array(100); 
    var madd=new Array(12); 
    var tgString="甲乙丙丁戊己庚辛壬癸"; 
    var dzString="子丑寅卯辰巳午未申酉戌亥"; 
    var numString="一二三四五六七八九十"; 
    var monString="正二三四五六七八九十冬腊"; 
    var weekString="日一二三四五六"; 
    var sx="鼠牛虎兔龙蛇马羊猴鸡狗猪"; 
    var cYear,cMonth,cDay,TheDate; 
    CalendarData = new Array(0xA4B,0x5164B,0x6A5,0x6D4,0x415B5,0x2B6,0x957,0x2092F,0x497,0x60C96,0xD4A,0xEA5,0x50DA9,0x5AD,0x2B6,0x3126E, 0x92E,0x7192D,0xC95,0xD4A,0x61B4A,0xB55,0x56A,0x4155B, 0x25D,0x92D,0x2192B,0xA95,0x71695,0x6CA,0xB55,0x50AB5,0x4DA,0xA5B,0x30A57,0x52B,0x8152A,0xE95,0x6AA,0x615AA,0xAB5,0x4B6,0x414AE,0xA57,0x526,0x31D26,0xD95,0x70B55,0x56A,0x96D,0x5095D,0x4AD,0xA4D,0x41A4D,0xD25,0x81AA5,0xB54,0xB6A,0x612DA,0x95B,0x49B,0x41497,0xA4B,0xA164B, 0x6A5,0x6D4,0x615B4,0xAB6,0x957,0x5092F,0x497,0x64B, 0x30D4A,0xEA5,0x80D65,0x5AC,0xAB6,0x5126D,0x92E,0xC96,0x41A95,0xD4A,0xDA5,0x20B55,0x56A,0x7155B,0x25D,0x92D,0x5192B,0xA95,0xB4A,0x416AA,0xAD5,0x90AB5,0x4BA,0xA5B, 0x60A57,0x52B,0xA93,0x40E95); 
    madd[0]=0; 
    madd[1]=31; 
    madd[2]=59; 
    madd[3]=90; 
    madd[4]=120; 
    madd[5]=151; 
    madd[6]=181; 
    madd[7]=212; 
    madd[8]=243; 
    madd[9]=273; 
    madd[10]=304; 
    madd[11]=334; 
     
    function GetBit(m,n){ 
    return (m>>n)&1; 
    } 
    //农历转换 
    function e2c(){ 
    TheDate= (arguments.length!=3) ? new Date() : new Date(arguments[0],arguments[1],arguments[2]); 
    var total,m,n,k; 
    var isEnd=false; 
    var tmp=TheDate.getYear(); 
    if(tmp<1900){ 
    tmp+=1900; 
    } 
    total=(tmp-1921)*365+Math.floor((tmp-1921)/4)+madd[TheDate.getMonth()]+TheDate.getDate()-38; 
     
    if(TheDate.getYear()%4==0&&TheDate.getMonth()>1) { 
    total++; 
    } 
    for(m=0;;m++){ 
    k=(CalendarData[m]<0xfff)?11:12; 
    for(n=k;n>=0;n--){ 
    if(total<=29+GetBit(CalendarData[m],n)){ 
    isEnd=true; break; 
    } 
    total=total-29-GetBit(CalendarData[m],n); 
    } 
    if(isEnd) break; 
    } 
    cYear=1921 + m; 
    cMonth=k-n+1; 
    cDay=total; 
    if(k==12){ 
    if(cMonth==Math.floor(CalendarData[m]/0x10000)+1){ 
    cMonth=1-cMonth; 
    } 
    if(cMonth>Math.floor(CalendarData[m]/0x10000)+1){ 
    cMonth--; 
    } 
    } 
    } 
     
    function GetcDateString(){ 
        var tmp=""; 
        var tmpother = "";
        /*显示农历年：（ 如：甲午(马)年 ）*/
        tmp+=tgString.charAt((cYear-4)%10); 
        tmp+=dzString.charAt((cYear-4)%12); 
        tmp+="("; 
        tmp+=sx.charAt((cYear-4)%12); 
        tmp+=")年 ";
        if(cMonth<1){ 
        tmp+="(闰)"; 
        tmpother+=monString.charAt(-cMonth-1); 
        }else{ 
        tmpother+=monString.charAt(cMonth-1); 
        } 
        tmpother+="月"; 
        tmpother+=(cDay<11)?"初":((cDay<20)?"十":((cDay<30)?"廿":"三十")); 
        if (cDay%10!=0||cDay==10){ 
        tmpother+=numString.charAt((cDay-1)%10); 
        } 

        var Tmp = tmp+'好'+tmpother;
        return Tmp; 
        } 
         
        function GetLunarDay(solarYear,solarMonth,solarDay){ 
        //solarYear = solarYear<1900?(1900+solarYear):solarYear; 
        if(solarYear<1921 || solarYear>2020){ 
        return ""; 
        }else{ 
        solarMonth = (parseInt(solarMonth)>0) ? (solarMonth-1) : 11; 
        e2c(solarYear,solarMonth,solarDay); 
        return GetcDateString(); 
        } 
        }   
                var calendar = showCal(DateT); 
                var index =  calendar.indexOf("好");
                /* alert(index); */
                var Y = calendar.substring(0,index);
                var M = '农历 '+calendar.substring(index+1);
                $(".dataBox_textLunar").html(M);
                $(".dataBox_textYear").html(Y);
                
    //星座
                /* function getAstro(m,d){
                      return "魔羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯".substr(m*2-(d<"102223444433".charAt(m-1)- -19)*2,2);
                    }
                    //下面写一个测试函数
                    function test(m,d){
                    document.writeln(m+"月"+d+"日 "+getAstro(m,d));
                    }
            $(".dataBox_textStar").html(getAstro(m,d)) */

    /*添加修改员工填充*/
    var engineerOb = $("span.engineer").html().split(',');
    var len = engineerOb.length;
    var str2 = '';
    var str3 = '';
    for(var i = 1;i<len-1;i++){
            str2 += '<option value="'+engineerOb[i].substring(12,engineerOb[i].length-1)+'">'+engineerOb[i].substring(12,engineerOb[i].length-1)+'</option>';
    }
    str3 += '<option value="'+engineerOb[len-1].substring(12,engineerOb[len-1].length-2)+'">'+engineerOb[len-1].substring(12,engineerOb[len-1].length-2)+'</option>';
    var Str = str2+str3;
    $("select[name='staffName_sel']").append(Str);

    /*添加修改交通方式填充*/
    ["请选择", "飞机", "铁路", "自驾", "打车", "其他"].map(function(v, i, arr){
        $("select[name='TransportTool_sel']").append("<option value='"+v+"'>"+v+"</option>");
    });

    /*添加修改住宿费用填充 HotelExpense_sel*/
    _.range(0, 1050, 50).map(function(v, i, arr){
        if(i == 0){
            $("select[name='HotelExpense_sel']").append("<option value='请选择住宿费用'>请选择住宿费用</option>");
        }
        $("select[name='HotelExpense_sel']").append("<option value='"+v+"'>"+v+"元</option>");
    });
});

 /****************** 跳页 **********************/

function FistPage(arg) {
        window.location.href = arg + "1";
}
function UpPage(arg) {
        window.location.href = arg ;
}
function NextPage(arg) {
        window.location.href = arg ;
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
//数组去重
Array.prototype.unique =function(){
     var res = [];
     var json = {};
     for(var i = 0; i < this.length; i++){
      if(!json[this[i]]){
       res.push(this[i]);
       json[this[i]] = 1;
      }
     }
    return res;
};

// 滚动响应     
$(function(){
    $(window).scrollTop(0);
    var top0 = $("#main-box").offset().top;
    var left0 = $("#main-box").offset().left-1;
    $(window).scroll(function(){
        var s = $(window).scrollTop();
        if(s>=top0){
            // var left1 = $(".flex-right-con").offset().left - 300
            $(".left-fixed-div").css({'position':'fixed','left':left0+"px",'top':'0'});
        }else{
            $(".left-fixed-div").css({'position':'static'});
        }
    });
    $(".cover-all").fadeOut(30);
    $(".cover-all-img").fadeOut(30);
    $("input[name='date_sel'][value='dateRange']").prop("checked","checked");
}); 