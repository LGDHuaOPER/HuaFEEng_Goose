$('.cover-color').height($(document).height()-80);

if($('input[name="selected"]:checked').val()=='singleSelect'){
    $('.select-content').css('margin-left','33%');
}else{
    $('.select-content').css('margin-left','23%');

}
$(function(){
	//搜索的内容回显
	var content = $(".scontent").text();
	$(".tsearch").val(content);
	
	//根据装机时间和拜访时间标红
	function fun_submit(visit_time,t){
        var date1 = new Date(visit_time);
      /* alert(date1.getFullYear()+"-"+(date1.getMonth()+1)+"-"+date1.getDate())*/
        var date2 = new Date(date1);
        date2.setDate(date1.getDate()+t);
        var times = date2.getFullYear()+"-"+(date2.getMonth()+1)+"-"+date2.getDate();
       /* alert(times);*/
        return times;
    }
	function changeRed(thetime,tr){
			var   d=new   Date(Date.parse(thetime.replace(/-/g,"/")));
			//今天时间，标准
			var   curDate=new   Date();
		    var da=d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate();
		    var cur=curDate.getFullYear() + '-' + (curDate.getMonth() + 1) + '-' + curDate.getDate(); 
			if(d >curDate){
				/*alert("是未来时间，不用去拜访")*/
				/*tr.find(".CustomerName").css('color','green');*/
				}else{
					/*alert("时间到了你要去拜访了");*/
					tr.find(".CustomerName").css('color','red');
				}
		}
	for(var  i= 1 ;i<$("#table1 tr").length;i++){

	 var tr=$("#table1 tr").eq(i);
	 var CustomerLevel =tr.find(".CustomerLevel").text();
		var InstalledTime =tr.find(".InstalledTime").text();
		var VisitTime = tr.find(".VisitTime").text();
		/*alert(CustomerLevel);*/
		if(VisitTime == '' || VisitTime == '--' ){
			if(InstalledTime != '' && InstalledTime != '--' ){
				var	visit_time = InstalledTime;
			}else{
				var	visit_time = '';
				return;
			}
		}else{
			var  visit_time  = VisitTime ;
		}
		/*alert(visit_time);*/
		var VisitTime_A = fun_submit(visit_time,75);
		var VisitTime_B = fun_submit(visit_time,150);
		var VisitTime_C = fun_submit(visit_time,300);
		/*alert(VisitTime_A)*/
	if(CustomerLevel == 'A'){
			//最后一次拜访时间——要判断的时间
			
			changeRed(VisitTime_A,tr);
	}else if(CustomerLevel == 'B'){
		changeRed(VisitTime_B,tr);
				
	}else if(CustomerLevel == 'C'){
		changeRed(VisitTime_C,tr);		
	}
}
})
//点击搜索
$(".ysearch").click(function(){
	
	/*alert(2)*/
	/*var Year = $(".year2").text();*/
	/*alert(Year)*/
	var Content = $(".tsearch").val();
	if(Content){
		window.location.href="VisitPlan?queryType=search&Content="+Content;
	}else{
		window.location.href="VisitPlan";
	}
	
})
//--------------------------------------------拜访记录弹出-----------------------------------------------
$('.VisitRecord-show').click(function () {
    var ID=$(this).parent().parent().find("td.xunhao  a").attr('value');
    $(".visitRecord .MachineDetailsID").text(ID);
 /*   alert( $(".visitRecord .MachineDetailsID").text())*/
   /* alert(ID)*/
    $.ajax({
        type: 'get',
        url: 'GetVisitPlan',
        data: {
        	ID: ID
        },
        dataType: 'json',
        success: function (data) {
        	console.log(data)
           $('.visitRecord .visitRecord_basic tbody').html('');
            for (var i = 1; i < data.length; i++) {
                var tr = '<tr>' +
                    '<td style="display: none" class="visit_id">' + data[i].ID + '</td>' +
                    '<td  class="visit_name">' + data[i].VisitName + '</td>' +
                    '<td  class="visit_time">' + data[i].VisitTime + '</td>' +
                    '<td  class="visit_engineer">' + data[i].Engineer + '</td>' +
                   /* '<td><input value="' + data[i].Number + '" type="text" style="width: 45px;" disabled="disabled"></td>' +*/
                    '<td  class="visit_details">' + data[i].Details + '</td>' +
                   /* '<td>' + data[i].ExceptDate + '</td>' +
                    '<td>' + data[i].DeliveryNumber + '</td>' +
                    '<td>' + data[i].Status + '</td>' +*/
                    '</tr>';
                $('.visitRecord .visitRecord_basic tbody').append(tr);
            }
            $('.cover-color').show();
            $('.visitRecord').show();
        },
        error: function () {
             $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
           
        }
    });

});

$(".add_visit").click(function(){
	
	var str = '<tr>'+
			  '<td style="display: none" class="visit_id">-1</td>' +
			  '<td class="visit_name"><input type="text" class="VisitName_add" placeholder="第X次拜访" style="border:none;text-align: center;font-size:16px;"></td>'+
			  '<td  class="visit_time"><input type="date" class="VisitTime_add"  style="border:none;text-align: center;font-size:16px"></td>'+
			  '<td  class="visit_engineer"><input type="text" class="Engineer_add"  style="border:none;text-align: center;font-size:16px"></td>'+
			  '<td  class="visit_details"><input type="text" class="Details_add"  style="border:none;text-align: center;font-size:16px"></td>'+
			  '</tr>';
/*	alert($('.visitRecord .visitRecord_basic tbody tr').length)*/
	if($('.visitRecord .visitRecord_basic tbody tr').length == 0){
		$('.visitRecord .visitRecord_basic tbody').append(str);
	}else{
		$('.visitRecord .visitRecord_basic tbody tr').last().after(str);
	}
	
	
	
})
$('.visitRecord_close').click(function () {
    $('.cover-color').hide();
    $('.visitRecord').hide();
});

$('#visit_cancel').click(function () {
    $('.cover-color').hide();
    $('.visitRecord').hide();
});
//添加的提交
//修改中提交
$("#visit_submit").click(function(){
	var MachineDetailsID=$(".visitRecord .MachineDetailsID").text();
	
	
	//5个数组形式
	var IDArr = [];
	var VisitName = [];
	var VisitTime = [];
	var Engineer = [];
	var Details = [];
	/*alert($(".visitRecord tbody tr").length )*/
	//长度大于1说明提交有内容
	if($(".visitRecord tbody tr").length > 0){
		for(var i = 0 ; i < $(".visitRecord tbody tr").length ; i++){
			var temp_id = $(".visitRecord tbody tr").eq(i).find(".visit_id").text();
			//等于-1说明是新加的记录，需要找到input的内容
			if(temp_id == '-1'){
				$(".visitRecord tbody tr").eq(i).find(".visit_name").text($(".visitRecord tbody tr").eq(i).find(".visit_name .VisitName_add").val())
				/*var temp_val3 = $(".visitRecord tbody tr").eq(i).find(".visit_name").text();
				alert(temp_val3)*/
				$(".visitRecord tbody tr").eq(i).find(".visit_time").text($(".visitRecord tbody tr").eq(i).find(".visit_time .VisitTime_add").val());
				/*var temp_val4 = $(".visitRecord tbody tr").eq(i).find(".visit_time").text();
				alert(temp_val4)*/
				$(".visitRecord tbody tr").eq(i).find(".visit_engineer").text($(".visitRecord tbody tr").eq(i).find(".visit_engineer .Engineer_add").val());
				/*var temp_val5 = $(".visitRecord tbody tr").eq(i).find(".visit_engineer").text();
				alert(temp_val5)*/
				$(".visitRecord tbody tr").eq(i).find(".visit_details").text($(".visitRecord tbody tr").eq(i).find(".visit_details .Details_add").val());
			/*	var temp_val6 = $(".visitRecord tbody tr").eq(i).find(".visit_details").text();
				alert(temp_val6)*/
			};
			IDArr.push($(".visitRecord tbody tr").eq(i).find(".visit_id").text());
			VisitName.push($(".visitRecord tbody tr").eq(i).find(".visit_name").text());
			VisitTime.push($(".visitRecord tbody tr").eq(i).find(".visit_time").text());
			Engineer.push($(".visitRecord tbody tr").eq(i).find(".visit_engineer").text());
			Details.push($(".visitRecord tbody tr").eq(i).find(".visit_details").text());
		} 
	}else{
		//没有内容的提交，传值都是空，不用做任何处理
		IDArr.push('');
		VisitName.push('');
		VisitTime.push('');
		Engineer.push('');
		Details.push('');
	}
	$.ajax({
		    url: 'VisitPlanAdd',
		    type: 'get',
		    data: {
		    	MachineDetailsID:MachineDetailsID,
		    	ID : IDArr,
		    	VisitName:VisitName,
		    	VisitTime:VisitTime,
		    	Engineer:Engineer,
		    	Details:Details
		    },
			success : function (data) {
				console.log(data == "true")
	        	if(data == "true"){
	        		 $.MsgBox.Alert("提示", "保存成功！"); 
	        	}
				 $('.cover-color').hide();
        		 $('.visitRecord').hide();
	        },
	        error : function () {
	        	 $.MsgBox.Alert("提示", "发送失败！"); 
	        }
		});
})






