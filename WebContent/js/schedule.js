
/*********************添加装机进展信息************************/
/* 添加装机进展信息 */
$(document).on("click","#add_submit",function(){

console.log(currentDate);
	var tr=$(this).parent().parent();
	var Name=$('.contract_add .staffName').find('option:selected').attr('value');
    var CustomerUnit=$('.contract_add #CustomerUnit').val();

    var Departure = $(".startArea").val().split(",")[1];
    var Destination = $(".arriveArea").val().split(",")[1];
   
    var DepartureDate = $('.contract_add input[name="StartTime"]').val();   //出发时间
    var DestinationDate = $('.contract_add input[name="EndTime"]').val();   //到达时间

    var Reg = new RegExp( '[0-9]{2}:[0-9]{2}') ;
    if(!Reg.test(DepartureDate) && DepartureDate != ''){
    	alert("出发时间格式错误");
    	return ;
    }
    if(!Reg.test(DestinationDate) && DestinationDate != ''){
    	alert("到达时间格式错误");
    	return ;
    }
    	
    var Traffic=$("#Traffic1").find("option:selected").text(); //获取Select选择的Text 
    if(Traffic == "请选择"){
    	Traffic = '';
    }else{
    	Traffic = Traffic;
    }
    var HotelExpense;
    if($("#stayCost").val()==""||$("#stayCost").val()==null||$("#stayCost").val()==undefined){
        $.MsgBox_Unload.Alert("提示", "请选择住宿费用！");
        return;
    }else{
        HotelExpense = ($("#stayCost").val())*1;
    }
    var TrafficExpense;
    if($("#travelCost").val()==""||$("#travelCost").val()==null||$("#travelCost").val()==undefined){
        $.MsgBox_Unload.Alert("提示", "请选择交通费用！");
        return;
    }else{
        TrafficExpense = ($("#travelCost").val())*1;
    }
    
    if($(".startArea").val()==""||$(".startArea").val()==null||$(".startArea").val()==undefined){
        $.MsgBox_Unload.Alert("提示", "请点击选择出发地！");
        return;
    }

    if($(".arriveArea").val()==""||$(".arriveArea").val()==null||$(".arriveArea").val()==undefined){
        $.MsgBox_Unload.Alert("提示", "请点击选择目的地！");
        return;
    }

    var TravelDistance = Number($("#tempDistance1").val()).toFixed(2);
    if (TravelDistance<20){
      TravelDistance = 20;
    }
    console.log("添加出发地目的地距离"+TravelDistance);
    var TrainNO=$('.contract_add input[name="TrainNO"]').val();
    
    var Service=$('.contract_add  #Service').val();
    var HotelMessage=$('.contract_add  #HotelMessage').val();
    var Date= currentDate;
    var OperateType="Add";

    console.log(Departure);
    console.log(Destination);
    console.log(CustomerUnit);
    console.log(Traffic);
    console.log(Service);
    console.log(HotelMessage);
    $.ajax({
        type : 'get',
        url : 'ScheduleOperate',
        data : {
        	Name : Name,
        	CustomerUnit : CustomerUnit,
        	ServiceItem : Service,
        	TransportTool : Traffic,
        	TrainNumber : TrainNO,
        	Hotel : HotelMessage,
        	OperateType : OperateType,
        	Departure : Departure,
        	Destination : Destination,
        	DepartureDate : DepartureDate,
        	DestinationDate : DestinationDate,
            HotelExpense : HotelExpense,
            TrafficExpense : TrafficExpense,
            TravelDistance : TravelDistance,
        	Date : Date,
        },
        dataType : 'json',
        success : function (data) {
        	console.log(data);
            if(data==true){
            	$.MsgBox.Alert('提示','添加成功');
                $('.MailBar_cover_color').hide();
                $('.contract_add').hide();
            }else{
            	$.MsgBox_Unload.Alert("提示", "添加失败！");
            }
        },
        error : function () {
        	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
        }
    });

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

/* 修改装机进展信息 */
$(document).on("click",".xiugai",function(){ 
  $(".startArea2").removeAttr("lngAndlat");
  $(".arriveArea2").removeAttr("lngAndlat");
    // saveExistCity("苏州市");
    $("#tempDistance2").val("");
	 $(this).css("color","red");
	 var tr=$(this).parent();
	 var engineer = $("span.engineer").html();
		var engineerOb = engineer.split(',');
			var len = engineerOb.length;
			var str1 = '<select class="staffName" style="width:62%;height:25px;margin-bottom:50px;border-radius:3px;">';
			var str2 = '';
			var str3 = '';
			var str4 = '</select>';
			for(var i = 1;i<len-1;i++){
					str2 += '<option value="'+engineerOb[i].substring(12,engineerOb[i].length-1)+'">'+engineerOb[i].substring(12,engineerOb[i].length-1)+'</option>';
			}
			str3 += '<option value="'+engineerOb[len-1].substring(12,engineerOb[len-1].length-2)+'">'+engineerOb[len-1].substring(12,engineerOb[len-1].length-2)+'</option>';
			Str = str1+str2+str3+str4;
			$(".contract_update .engineer").html('').html(Str);
	 
	var N = tr.find('td').eq(2).text();
	var N1 = Trim(N);
	for(var ii =0;ii<$(".contract_update select.staffName").find('option').length;ii++){
		var P2 = $(".contract_update select.staffName").find('option').eq(ii).attr('value');
		var P3 = Trim(P2);
		if(P3 == N1){
			$(".contract_update select.staffName").find('option[value="'+ P2 +'"]').attr('selected',true);
		}
	}
    $('.contract_update #CustomerUnit').val(tr.find('td').eq(3).text());
    $(".thetime").text(tr.find('td').eq(1).text());

    $("#stayCost2").val(tr.find(".showTripCost1").text());
    $("#Traffic").trigger("change");
    $("#travelCost2").val(tr.find(".showTripCost2").text());

  //获取系统当前时间  
    var nowdate = new Date();  
    var y = nowdate.getFullYear();  
    var m = nowdate.getMonth()+1;  
    var d = nowdate.getDate();  
    var formatnowdate = y+'-'+m+'-'+d;  
    
    //获取系统前一个月的时间  
    nowdate.setMonth(nowdate.getMonth()-2);  
    var y = nowdate.getFullYear();  
    var m = nowdate.getMonth()+1;  
    var d = nowdate.getDate();  
    var formatwdate = y+'-'+m+'-'+d;  
    var curDate = new   Date(Date.parse(formatwdate.replace(/-/g,"/")));
    //获取用户的行程时间
    var thetime = $(".thetime").text();
   /* alert(thetime);*/
	//是过去的时间，那么保存按钮不可用
	var d = new Date(Date.parse(thetime.replace(/-/g,"/")));
	
	/*var da=d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate(); 

    var cur=curDate.getFullYear() + '-' + (curDate.getMonth() + 1) + '-' + curDate.getDate(); */

	if(d >curDate){
		$(".thetime").text("t");
	}else{
		/*alert("是过去两个月的时间哈哈哈不能让你保存啦！");*/
		$(".thetime").text("f");
		$('#update_submit').css({
			"color":"#ccc",
			"border":"none",
			"box-shadow":"0 0 0 0 #f8fcfd"
		});
	}
	/*修改的部分————————————————————————————————————————————————*/ 
    
    //出发地
    var addr1 = tr.find('td').eq(7).text().trim();
    $(".startArea2").val(addr1);
   
    //目的地
    var addr2 = tr.find('td').eq(8).text().trim();
    $(".arriveArea2").val(addr2);
    alterDistance(addr1,addr2);

    $('.contract_update #Service').text(tr.find('td').eq(4).text());
    var tool = tr.find('td').eq(5).text();
   
    if(tool==""){
    	$('.contract_update').find('select[name="Traffic"]').find('option[text="未选择"]').prop("selected", true);
      $("#Traffic").trigger("change");
      $("#travelCost2").val(tr.find(".showTripCost2").text());
    }else{
    	$('.contract_update').find('select[name="Traffic"]').find('option[text="' + tool + '"]').prop("selected", true);
      $("#Traffic").trigger("change");
      $("#travelCost2").val(tr.find(".showTripCost2").text());
    }
    
    if(tool == ""){
    	$('.contract_update').find('select[name="Traffic"]').find('option').prop("selected", false);
    	$('.contract_update').find('select[name="Traffic"]').find('option[text="未选择"]').prop("selected", true);
      $("#Traffic").trigger("change");
      $("#travelCost2").val(tr.find(".showTripCost2").text());
    }else  if(tool == "飞机"){
//    	$('.contract_update').find('select[name="Traffic"]').find('option').text("飞机");
   	$('.contract_update').find('select[name="Traffic"]').find('option[text="飞机"]').attr("selected", true);
    $("#Traffic").trigger("change");
    $("#travelCost2").val(tr.find(".showTripCost2").text());
    }else  if(tool == "铁路" || tool == "高铁"){
    	//   	$('.contract_update').find('select[name="Traffic"]').find('option').text("铁路");
    	$('.contract_update').find('select[name="Traffic"]').find('option[text="铁路"]').attr("selected", true);
      $("#Traffic").trigger("change");
      $("#travelCost2").val(tr.find(".showTripCost2").text());
    }else  if(tool == "自驾"){
    	//    		$('.contract_update').find('select[name="Traffic"]').find('option').text("自驾");
   	$('.contract_update').find('select[name="Traffic"]').find('option[text="自驾"]').attr("selected", true);
    $("#Traffic").trigger("change");
    $("#travelCost2").val(tr.find(".showTripCost2").text());
    }else  if(tool == "打车"){
    	//    		$('.contract_update').find('select[name="Traffic"]').find('option').text("打车");
    	$('.contract_update').find('select[name="Traffic"]').find('option[text="打车"]').attr("selected", true);
      $("#Traffic").trigger("change");
      $("#travelCost2").val(tr.find(".showTripCost2").text());
    }
    
    $('.contract_update input[name="TrainNO"]').val(tr.find('td').eq(6).text());
    $('.contract_update #HotelMessage').val(tr.find('td').eq(11).text());
    /*$('.contract_update input[name="Date"]').val(tr.find('td').eq(2).text());*/
    $('.contract_update input[name="StartTime"]').val(tr.find('td').eq(9).text());
    $('.contract_update input[name="EndTime"]').val(tr.find('td').eq(10).text());
    var  ID = $(this).attr("value");
    $(".contract_update .contract_title").attr("value",ID);  //在修改页面保存当前行的ID信息
    $('.MailBar_cover_color').show();
    $('.contract_update').show();

});

// 计算未修改出发地和目的地
function alterDistance(address1,address2){
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
                console.log(lng1);
                console.log(lat1);
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
}


/*  提交修改后的信息  ************************************/
$('#update_submit').click(function () {
	var bool = $(".thetime").text();
   /* alert(bool);*/
    if(bool == 'f'){
    	$('#update_submit').click(function(){
    		return;
    	});
    }else{
    	
    	var Name =$('.contract_update .staffName').find('option:selected').attr('value');
/*        var CustomerUnit = $('.contract_update input[name="CustomerUnit"]').val();*/
        var CustomerUnit=$('.contract_update #CustomerUnit').val();
        /*var Service = $('.contract_update input[name="Service"]').val();*/
      /*  var Traffic = $('.contract_update input[name="Traffic"]').val();*/
        var TrainNO = $('.contract_update input[name="TrainNO"]').val();
      /*  var HotelMessage = $('.contract_update input[name="HotelMessage"]').val();*/
        var Service=$('.contract_update  #Service').val();
        var HotelMessage=$('.contract_update  #HotelMessage').val();
        
        var Departure = $(".startArea2").val();
        var Destination = $(".arriveArea2").val();
        Departure = Departure.indexOf(",")>-1?Departure.split(",")[1]:Departure.trim();
        Destination = Destination.indexOf(",")>-1?Destination.split(",")[1]:Destination.trim();
        
        var DepartureDate = $('.contract_update #StartTime').val();   //出发时间
        var DestinationDate = $('.contract_update #EndTime').val();   //到达时间
        
        var Reg = new RegExp( '[0-9]{2}:[0-9]{2}') ;
        if(!Reg.test(DepartureDate) && DepartureDate != ''){
        	alert("出发时间格式错误");
        	return ;
        }
        if(!Reg.test(DestinationDate) && DestinationDate != ''){
        	alert("到达时间格式错误");
        	return ;
        }
        
        var Traffic=$(".contract_update #Traffic").find("option:selected").attr("text"); //获取Select选择的Text 
        
        if(Traffic == "请选择"){
        	Traffic = '';
        }else{
        	Traffic = Traffic;
        }
        
        var HotelExpense;
        if($("#stayCost2").val()==""||$("#stayCost2").val()==null||$("#stayCost2").val()==undefined){
            $.MsgBox_Unload.Alert("提示", "请选择住宿费用！");
            return;
        }else{
            HotelExpense = ($("#stayCost2").val())*1;
        }

        var TrafficExpense;
        if($("#travelCost2").val()==""||$("#travelCost2").val()==null||$("#travelCost2").val()==undefined){
            $.MsgBox_Unload.Alert("提示", "请选择交通费用！");
            return;
        }else{
            TrafficExpense = ($("#travelCost2").val())*1;
        }
        var startArea2ab = 0;
        var arriveArea2ab = 0;
        if($(".startArea2").attr("lngandlat")){
          startArea2ab = 1;
        }

        if($(".arriveArea2").attr("lngandlat")){
          arriveArea2ab = 1;
        }
        var TravelDistance = Number($("#tempDistance2").val()).toFixed(2);
        if (TravelDistance<20){
            TravelDistance = 20;
        }
        if (startArea2ab+arriveArea2ab < 2){
          console.log("未修改，算的距离"+TravelDistance);
        }
        
        // alert("出发地"+Departure);
        // alert("目的地"+Destination);
        var Date= currentDate;
        var OperateType="Modify";
        var  ID = $(".contract_update .contract_title").attr("value");

        $.ajax({
            type : 'get',
            url : 'ScheduleOperate',
            data : {
            	ID : ID,
            	OperateType : OperateType,
            	Name : Name,
            	CustomerUnit : CustomerUnit,
            	ServiceItem : Service,
            	TransportTool : Traffic,
            	TrainNumber : TrainNO,
            	Hotel : HotelMessage,
            	Departure : Departure,
            	Destination : Destination,
            	DepartureDate : DepartureDate,
            	DestinationDate : DestinationDate,
              HotelExpense : HotelExpense,
              TrafficExpense : TrafficExpense,
              TravelDistance :TravelDistance,
            	Date : Date,
            },
            dataType : 'json',
            success : function (data) {
                if(data==true){
                	 $.MsgBox.Alert('提示','修改成功');
                	 /*alert(6666)*/
                	 $('.contract_add').hide();
                     $('.MailBar_cover_color').hide();
                }else{
                	$.MsgBox_Unload.Alert("提示", "修改失败！");
                }

            },
            error : function () {
            	$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
            }
        });
    }

});

//点击添加
function AddContract() {
    $("#tempDistance1").val("");
    $(".startArea").val("");
    $(".arriveArea").val("");
    $(".startArea").removeAttr("lngAndlat");
    $(".arriveArea").removeAttr("lngAndlat");
	var engineer = $("span.engineer").html();
	var engineerOb = engineer.split(',');
		var len = engineerOb.length;
		var str1 = '<select class="staffName" style="width:62%;height:25px;margin-bottom:50px;border-radius:3px;">';
		var str2 = '';
		var str3 = '';
		var str4 = '</select>';
		for(var i = 1;i<len-1;i++){
				str2 += '<option value="'+engineerOb[i].substring(12,engineerOb[i].length-1)+'">'+engineerOb[i].substring(12,engineerOb[i].length-1)+'</option>';
		}
		str3 += '<option value="'+engineerOb[len-1].substring(12,engineerOb[len-1].length-2)+'">'+engineerOb[len-1].substring(12,engineerOb[len-1].length-2)+'</option>';
		Str = str1+str2+str3+str4;
		$(".contract_add .engineer").html('').html(Str);
	
    /*默认当前日期 */
    var ddd = new Date();
    var day =ddd.getDate();
    if(ddd.getMonth()<10){
        var month = "0"+(ddd.getMonth()+1);
    }
    else{
    	var month =(ddd.getMonth()+1);
    }	 
    if(ddd.getDate()<10){
        day = "0"+ddd.getDate();
    }
    var datew = ddd.getFullYear()+"-"+month+"-"+day;
    datew = datew.toString();
    var currentHref = window.location.href;
    if(currentHref.indexOf("?Date=") >0){
    	$('.contract_add input[name="Date"]').val(currentDate);
    }
    else{
    	$('.contract_add input[name="Date"]').val(datew);
    }
    /*console.log($('.contract_add input[name="Date"]').val());*/
    $('.MailBar_cover_color').show();
    $('.contract_add').show();
};

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

//点击取消
$('#add_cancel').click(function () {
    $('.MailBar_cover_color').hide();
    $('.contract_add').hide();
    $("#allmap").fadeOut(100);
});

$('#update_cancel').click(function () {
    $('.MailBar_cover_color').hide();
    $('.contract_update').hide();
    $("#allmap").fadeOut(100);
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
							})
					}else{
						$.MsgBox_Unload.Alert("提示", "不能删除过去的信息！");
					}
		})
	}else{
		window.location.reload();
	}
})
function SelectContent(){
	var engineer = $("span.engineer").html();
	var engineerOb = engineer.split(',')
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
    

    $(document).on("change","#Traffic1",function(){
        var Traffic1Val = $(this).val();
        var Traffic1option = "";
        var Traffic1option2 = "<option value='0'>0元</option>";
        if(Traffic1Val==0){
            $("#travelCost").html("");
            Traffic1option = "<option value=''>请选择交通方式</option>";
            $("#travelCost").append(Traffic1option);
        }else if(Traffic1Val==1){
            $("#travelCost").html("");
            for(var i = 2;i<21;i++){
                var b1 = i*200;
                Traffic1option2+="<option value="+b1+">"+b1+"元</option>";
            }
            $("#travelCost").append(Traffic1option2);
        }else if(Traffic1Val==2){
            $("#travelCost").html("");
            for(var i = 1;i<41;i++){
                var b1 = i*50;
                Traffic1option2+="<option value="+b1+">"+b1+"元</option>";
            }
            $("#travelCost").append(Traffic1option2);
        }else if(Traffic1Val==3){
            $("#travelCost").html("");
            for(var i = 1;i<61;i++){
                var b1 = i*50;
                Traffic1option2+="<option value="+b1+">"+b1+"元</option>";
            }
            $("#travelCost").append(Traffic1option2);
        }else if(Traffic1Val==4){
            $("#travelCost").html("");
            for(var i = 1;i<21;i++){
                var b1 = i*50;
                Traffic1option2+="<option value="+b1+">"+b1+"元</option>";
            }
            $("#travelCost").append(Traffic1option2);
        }else if(Traffic1Val==5){
        	$("#travelCost").html("");
            for(var i = 1;i<201;i++){
                var b1 = i*10;
                Traffic1option2+="<option value="+b1+">"+b1+"元</option>";
            }
            $("#travelCost").append(Traffic1option2);
        }
    });

    function stayCost(){
        var stayCostOption="<option value='' checked>请选择住宿费用</option><option value='0'>0元</option>";
        for(var i=2;i<21;i++){
            var c1 = i*50;
            stayCostOption+="<option value="+c1+">"+c1+"元</option>";
        }
        $("#stayCost").html("");
        $("#stayCost").append(stayCostOption);
    }

    stayCost();


    $(document).on("change","#Traffic",function(){
        var Traffic1Val = $(this).val();
        var Traffic1option = "";
        var Traffic1option1 = "<option value='0'>0元</option>";
        if(Traffic1Val==0){
            $("#travelCost2").html("");
            Traffic1option = "<option value=''>请选择交通方式</option>";
            $("#travelCost2").append(Traffic1option);
        }else if(Traffic1Val==1){
            $("#travelCost2").html("");
            for(var i = 2;i<21;i++){
                var b1 = i*200;
                Traffic1option1+="<option value="+b1+">"+b1+"元</option>";
            }
            $("#travelCost2").append(Traffic1option1);
        }else if(Traffic1Val==2){
            $("#travelCost2").html("");
            for(var i = 1;i<41;i++){
                var b1 = i*50;
                Traffic1option1+="<option value="+b1+">"+b1+"元</option>";
            }
            $("#travelCost2").append(Traffic1option1);
        }else if(Traffic1Val==3){
            $("#travelCost2").html("");
            for(var i = 1;i<61;i++){
                var b1 = i*50;
                Traffic1option1+="<option value="+b1+">"+b1+"元</option>";
            }
            $("#travelCost2").append(Traffic1option1);
        }else if(Traffic1Val==4){
            $("#travelCost2").html("");
            for(var i = 1;i<21;i++){
                var b1 = i*50;
                Traffic1option1+="<option value="+b1+">"+b1+"元</option>";
            }
            $("#travelCost2").append(Traffic1option1);
        }else if(Traffic1Val==5){
        	$("#travelCost2").html("");
            for(var i = 1;i<201;i++){
                var b1 = i*10;
                Traffic1option1+="<option value="+b1+">"+b1+"元</option>";
            }
            $("#travelCost2").append(Traffic1option1);
        }
    });

    function stayCost2(){
        var stayCostOption="<option value='' checked>请选择住宿费用</option><option value='0'>0元</option>";
        for(var i=2;i<21;i++){
            var c1 = i*50;
            stayCostOption+="<option value="+c1+">"+c1+"元</option>";
        }
        $("#stayCost2").html("");
        $("#stayCost2").append(stayCostOption);
    }

    stayCost2();

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
