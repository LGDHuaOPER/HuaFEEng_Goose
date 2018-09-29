// 翻页组件按钮逻辑
function pageStyle(currentPage,pageCounts){
    if(pageCounts == 1){
        $("#fistPage, #upPage, #nextPage, #lastPage, #Gotojump").prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
    }else if(currentPage == 1){
        $("#fistPage, #upPage").prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
        $("#lastPage, #nextPage, #Gotojump").prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }else if(currentPage == pageCounts){
        $("#lastPage, #nextPage").prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
        $("#fistPage, #upPage, #Gotojump").prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }else{
        $("#lastPage, #nextPage, #fistPage, #upPage, #Gotojump").prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }
}

// 定义文件上传保存路径对象
var fileUploadSavePath = new Object();
fileUploadSavePath.InstructionsPath = null;
fileUploadSavePath.UISpecificationPath = null;
fileUploadSavePath.addInstructions = null;
fileUploadSavePath.addUISpecification = null;
fileUploadSavePath.updateInstructions = null;
fileUploadSavePath.updateUISpecification = null;

$('.cover-color').height($(document).height()-80);
//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function() {
	window.location.reload();
});
//--------------------------------------------添加信息-----------------------------------------------
function AddContract() {
	$(".add_fileCont1, .add_fileCont2").html('');
	$(".contract_add .basic_info_in .form-control").val("");
	$(".contract_add span.span_text").text("");
	$(".contract_add input[type='file']").attr("title","");
	$(".contract_add input[type='file']").val("");
	$('.cover-color, .contract_add').slideDown(250);
	fileUploadSavePath.InstructionsPath = null;
	fileUploadSavePath.UISpecificationPath = null;
	fileUploadSavePath.addInstructions = null;
	fileUploadSavePath.addUISpecification = null;
}

$('.contractAdd_close, #add_cancel').click(function(){
    $('.cover-color, .contract_add').slideUp(250);
    fileUploadSavePath.InstructionsPath = null;
    fileUploadSavePath.UISpecificationPath = null;
    fileUploadSavePath.addInstructions = null;
    fileUploadSavePath.addUISpecification = null;
});

$('#add_submit').click(function(){
	var Product = $('.contract_add select[name="Product"]').val();
    var Priority = $('.contract_add select[name="Priority"]').val();
    var State = $('.contract_add select[name="State"]').val();
    var Front = $('.contract_add select[name="Front"]').val();
    var Back = $('.contract_add select[name="Back"]').val();
    var UIDesigner = $('.contract_add select[name="UIDesigner"]').val();
    var Leader = $('.contract_add select[name="Leader"]').val();
    if(!Product){
    	$.MsgBox.Judge('提示', "请选择产品！");
    	return false;
    }
    if(!Priority){
    	$.MsgBox.Judge('提示', "请选择优先级！");
    	return false;
    }
    if(!State){
    	$.MsgBox.Judge('提示', "请选择状态！");
    	return false;
    }
    if(!Leader){
    	$.MsgBox.Judge('提示', "请选择主导人！");
    	return false;
    }
    //UI文档和开发文档
    var Instructions, UISpecification, InstructionsPath, UISpecificationPath;
    if(fileUploadSavePath.addInstructions && fileUploadSavePath.addInstructions!=""){
    	Instructions = fileUploadSavePath.addInstructions;
    }else{
    	Instructions = "";
    }


    if(fileUploadSavePath.addUISpecification && fileUploadSavePath.addUISpecification!=""){
    	UISpecification = fileUploadSavePath.addUISpecification;
    }else{
    	UISpecification = "";
    }

    if(fileUploadSavePath.InstructionsPath && fileUploadSavePath.InstructionsPath!=""){
    	InstructionsPath = fileUploadSavePath.InstructionsPath;
    }else{
    	InstructionsPath = "";
    }

    if(fileUploadSavePath.UISpecificationPath && fileUploadSavePath.UISpecificationPath!=""){
    	UISpecificationPath = fileUploadSavePath.UISpecificationPath;
    }else{
    	UISpecificationPath = "";
    }
    
    // 规划时间PlanningTime，手动选择日期，作为项目的计划交付时间
    var PlanningTime = $('.contract_add input[name="PlanningTime"]').val();
	//提交时间SubmissionTime 当前提交时的日期
	
	 var Remark = $('.contract_add textarea[name="Remark"]').val();
	 var Password = $('.contract_add input[name="ipassword"]').val();
	
	 //邮件部分
	 /*var ToList = 'songdaiao@eoulu.com';*/
	 var ToList = 'yeqingliu@eoulu.com';
	 var email =$("#email").text();
	
	 /*var copyList = 'zhaolili@eoulu.com;zhaowenzhen@eoulu.com;'+email;*/
	 var copyList = 'sunmengying@eoulu.com';
	 console.log(copyList);
	 var content1 = '您好！';
	 var content2 ='';
	 
	if(PlanningTime == ''){
	    $.MsgBox.Judge('提示', "请选择项目规划时间！");
	    return;
	}
	 if(Password == ''){
	    $.MsgBox.Judge('提示', "请输入你的邮箱密码！");
	    return;
	}
   
	//邮件的内容Content
	 var ID = 'id';
	 var SubmissionTime = 'SubmissionTime';
	 var DelayTime = 'DelayTime';
	 var GraduateInstitutions = 'GraduateInstitutions';
	 var Content = '<table border="1" style="border-color:#333; border-collapse: collapse;font-size: 14px;color:black">'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">序号</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;color:black">'+ID+'</td>'+
		'</tr>'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">产品</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei,Arial;font-size: 14px;color:black">'+ Product +'</span></td>'+
		'</tr>'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Arial;font-size: 14px;color:black">开发说明书</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei,Arial;font-size: 14px;">'+ Instructions +'</span></td>'+
		'</tr>'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family:Microsoft YaHei,Arial;font-size: 14px;color:black">UI规范</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei,Arial;font-size: 14px;">'+ UISpecification +'</span></td>'+
		'</tr>'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">优先级</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei,Arial;font-size: 14px;color:black">'+ Priority +'</span></td>'+
		'</tr>'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">提交人</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-size: 14px;color:black">'+ GraduateInstitutions +'</span></td>'+
		'</tr>'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">前端</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">'+ Front +'</span></td>'+
		'</tr>'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">后台</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;;font-size: 14px;color:black">'+ Back +'</span></td>'+
		'</tr>'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei,Arial;font-size: 14px;color:black">UI设计</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;;font-size: 14px;color:black">'+ UIDesigner +'</span></td>'+
		'</tr>'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">主导人</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;;font-size: 14px;color:black">'+ Leader +'</span></td>'+
		'</tr>'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">提交时间</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Arial;font-size: 14px;color:black">'+ SubmissionTime +'</span></td>'+
		'</tr>'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">规划时间</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Arial;font-size: 14px;color:black">'+ PlanningTime +'</span></td>'+
		'</tr>'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">交付时间</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Arial;font-size: 14px;color:black"></span></td>'+
		'</tr>'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">延期时间</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Arial;font-size: 14px;color:black">'+ DelayTime +'</span></td>'+
		'</tr>'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">状态</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;">'+ State+'</td>'+
		'</tr>'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">备注</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;">'+ Remark+'</td>'+
		'</tr>'+
	'</table>';
	 
	//点击提交以后避免用户重复提交
		$("#add_submit").attr("disabled","disabled");
		$("#add_submit").css({
			"background":"#dddddd",
			"color":"#808080",
			"border":"none",
			"box-shadow":"0 0 0 0 #f8fcfd"
		});
		 $.MsgBox.Prompt("提示", "冲锋鹅正在处理中......请等待");
	 
//  发送ajax请求，传参客户名称id，返回客户联系人和联系方式
  
    $.ajax({
    	type:'post',
    	url:'SoftwareProjectOperate',
    	data:{
    		type:'add',
    		Product: Product,
    		Instructions: Instructions,
    		UISpecification: UISpecification,
    		Priority: Priority,
    		State:State,
    		Front:Front,
    		Back:Back,
    		UIDesigner:UIDesigner,
    		Leader:Leader,
    		PlanningTime:PlanningTime,
    		// LeadTime:LeadTime,
    		Remark:Remark,
    		Password:Password,
    		Content:Content,
    		InstructionsPath: InstructionsPath,
    		UISpecificationPath: UISpecificationPath
    	},
    	success:function(data){
			$("#mb_box,#mb_con").remove();
			$.MsgBox.Alert("提示", data);
			$('.cover-color').hide();
		    $('.contract_add').hide();
    	},
	    error: function () {
	    	$("#mb_box,#mb_con").remove();
	        $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	    }
    });
});
  

//-------------------------------------------修改-----------------------------------------------
$('.contract-edit').click(function () {
	fileUploadSavePath.InstructionsPath = null;
	fileUploadSavePath.UISpecificationPath = null;
	fileUploadSavePath.updateInstructions = null;
	fileUploadSavePath.updateUISpecification = null;
	$(".contract_update span.span_text").text("");
	$(".contract_update input[type='file']").val("");
	var tr = $(this).parent();
    $('.contract_update').find('.update_id').text(tr.find('.contract-edit').attr('value'));
    $('.contract_update').find('[name="Product"]').val(tr.find(".Product").text());
    $('.contract_update').find('.update_fileCont1').html('').html(tr.find('.Instructions').text());
    $('.contract_update').find('#update_agreement1').attr("title",tr.find('.Instructions').text());
    $('.contract_update').find('.update_fileCont2').html('').html(tr.find('.UISpecification').text());
    $('.contract_update').find('#update_agreement2').attr("title",tr.find('.UISpecification').text());
    $('.contract_update').find('[name="Priority"]').val(tr.find(".Priority").text());
    $('.contract_update').find('[name="State"]').val(tr.find(".State").text());
    $('.contract_update').find('[name="PlanningTime"]').val(tr.find(".PlanningTime").text());
    $('.contract_update').find('[name="Leader"]').val(tr.find(".Leader").text());
    $('.contract_update').find('[name="Front"]').val(tr.find(".Front").text());
    $('.contract_update').find('[name="Back"]').val(tr.find(".Back").text());
    $('.contract_update').find('[name="UIDesigner"]').val(tr.find(".UIDesigner").text());
    $('.contract_update').find('[name="Remark"]').val(tr.find(".Remark").text());
    if(tr.find(".State").text()=="完成"){
    	$("#update_submit").css("cursor","not-allowed").prop("disabled","disabled");
    }else{
    	$("#update_submit").css("cursor","pointer").prop("disabled",false);
    }
	$('.cover-color, .contract_update').slideDown(250);
});

$("#update_submit").click(function(){
	var ID = $('.contract_update .update_id').text();
	var Product = $('.contract_update select[name="Product"]').val();
    if(!Product){
    	Product = '';
    }

	var Priority = $('.contract_update select[name="Priority"]').val();
    if(!Priority){
    	Priority = '';
    }
	
	var State = $('.contract_update select[name="State"]').val();
    if(!State){
    	State = '';
    }

	var Front = $('.contract_update select[name="Front"]').val();
    if(!Front){
    	Front = '';
    }

	var Back = $('.contract_update select[name="Back"]').val();
    if(!Back){
    	Back = '';
    }

    var UIDesigner = $('.contract_update select[name="UIDesigner"]').val();
    if(!UIDesigner){
    	UIDesigner = '';
    }
	 
	var Leader = $('.contract_update select[name="Leader"]').val();
	if(!Leader){
		Leader = '';
	}
	 
//  规划时间，手动选择日期，作为项目的计划交付时间
var PlanningTime = $('.contract_update [name="PlanningTime"]').val();
//提交时间 当前提交时的日期
var today = new  Date();
var SubmissionTime = today.getFullYear()+"-"+(today.getMonth()+1)+"-"+today.getDate();
console.log(SubmissionTime);

var Remark = $('.contract_update [name="Remark"]').val();
var Password = $('.contract_update [name="ipassword"]').val();
//UI文档和开发文档
var Instructions, UISpecification, InstructionsPath, UISpecificationPath;
if(fileUploadSavePath.updateInstructions && fileUploadSavePath.updateInstructions!=""){
	Instructions = fileUploadSavePath.updateInstructions;
}else{
	Instructions = $(".contract_update .update_fileCont1").text().replace("C:\\fakepath\\",""); 
}

if(fileUploadSavePath.updateUISpecification && fileUploadSavePath.updateUISpecification!=""){
	UISpecification = fileUploadSavePath.updateUISpecification;
}else{
	UISpecification = $(".contract_update .update_fileCont2").text().replace("C:\\fakepath\\","");
}

if(fileUploadSavePath.InstructionsPath && fileUploadSavePath.InstructionsPath!=""){
	InstructionsPath = fileUploadSavePath.InstructionsPath;
}else{
	InstructionsPath = "";
}

if(fileUploadSavePath.UISpecificationPath && fileUploadSavePath.UISpecificationPath!=""){
	UISpecificationPath = fileUploadSavePath.UISpecificationPath;
}else{
	UISpecificationPath = "";
}
// var Instructions=$(".contract_update .update_fileCont1").text().replace("C:\\fakepath\\","");   
// var UISpecification = $(".contract_update .update_fileCont2").text().replace("C:\\fakepath\\","");
if(PlanningTime == ''){
    $.MsgBox.Judge('提示', "请选择项目规划时间！");
    return;
}
if(Password == ''){
    $.MsgBox.Judge('提示', "请输入你的邮箱密码！");
    return;
}

//邮件的内容Content
var SubmissionTime = 'SubmissionTime';
var DelayTime = 'DelayTime';
var GraduateInstitutions = 'GraduateInstitutions';
var Content = '<table border="1" style="border-color:#333; border-collapse: collapse;font-size: 14px;color:black">'+
	'<tr>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">序号</span></td>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;color:black">'+ID+'</td>'+
	'</tr>'+
	'<tr>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">产品</span></td>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei,Arial;font-size: 14px;color:black">'+ Product +'</span></td>'+
	'</tr>'+
	'<tr>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Arial;font-size: 14px;color:black">开发说明书</span></td>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei,Arial;font-size: 14px;">'+ Instructions +'</span></td>'+
	'</tr>'+
	'<tr>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family:Microsoft YaHei,Arial;font-size: 14px;color:black">UI规范</span></td>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei,Arial;font-size: 14px;">'+ UISpecification +'</span></td>'+
	'</tr>'+
	'<tr>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">优先级</span></td>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei,Arial;font-size: 14px;color:black">'+ Priority +'</span></td>'+
	'</tr>'+
	'<tr>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">提交人</span></td>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-size: 14px;color:black">'+ GraduateInstitutions +'</span></td>'+
	'</tr>'+
	'<tr>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">前端</span></td>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">'+ Front +'</span></td>'+
	'</tr>'+
	'<tr>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">后台</span></td>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;;font-size: 14px;color:black">'+ Back +'</span></td>'+
	'</tr>'+
	'<tr>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei,Arial;font-size: 14px;color:black">UI设计</span></td>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;;font-size: 14px;color:black">'+ UIDesigner +'</span></td>'+
	'</tr>'+
	'<tr>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">主导人</span></td>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;;font-size: 14px;color:black">'+ Leader +'</span></td>'+
	'</tr>'+
	'<tr>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">提交时间</span></td>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Arial;font-size: 14px;color:black">'+ SubmissionTime +'</span></td>'+
	'</tr>'+
	'<tr>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">规划时间</span></td>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Arial;font-size: 14px;color:black">'+ PlanningTime +'</span></td>'+
	'</tr>'+
	'<tr>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">交付时间</span></td>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Arial;font-size: 14px;color:black"></span></td>'+
	'</tr>'+
	'<tr>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">延期时间</span></td>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Arial;font-size: 14px;color:black">'+ DelayTime +'</span></td>'+
	'</tr>'+
	'<tr>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">状态</span></td>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;">'+ State+'</td>'+
	'</tr>'+
	'<tr>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">备注</span></td>'+
		'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;">'+ Remark+'</td>'+
	'</tr>'+
'</table>';
//点击提交以后避免用户重复提交
$("#update_submit").attr("disabled","disabled");
$("#update_submit").css({
	"background":"#dddddd",
	"color":"#808080",
	"border":"none",
	"box-shadow":"0 0 0 0 #f8fcfd"
});
$.MsgBox.Prompt("提示", "冲锋鹅正在处理中......请等待");

//  发送ajax请求，传参客户名称id，返回客户联系人和联系方式
  
    $.ajax({
    	type:'post',
    	url:'SoftwareProjectOperate',
    	data:{
    		type:'update',
    		ID:ID,
    		Product:Product,
    		Instructions:Instructions,
    		UISpecification:UISpecification,
    		Priority:Priority,
    		State:State,
    		Front:Front,
    		Back:Back,
    		UIDesigner:UIDesigner,
    		Leader:Leader,
    		PlanningTime:PlanningTime,
    		// LeadTime:LeadTime,
    		Remark:Remark,
    		Password:Password,
    		Content:Content,
    		InstructionsPath: InstructionsPath,
    		UISpecificationPath: UISpecificationPath
    	},
    	success:function(data){
    		$("#mb_box,#mb_con").remove();
    		$.MsgBox.Alert('提示',data);
    		$('.cover-color').hide();
    		$('.contract_update').hide();
    	},
	    error: function () {
	    	$("#mb_box,#mb_con").remove();
	        $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	    }
    });
});
$('.update_close, #update_cancel').click(function () {
    $('.cover-color, .contract_update').slideUp(250);
    fileUploadSavePath.InstructionsPath = null;
    fileUploadSavePath.UISpecificationPath = null;
    fileUploadSavePath.updateInstructions = null;
    fileUploadSavePath.updateUISpecification = null;
});

// 说明书  添加上传   
$(document).on("change",".add_agreement1",function(){
	var ithat = $(this).parent().siblings("span");
    var filePath=$(this).val().replace("C:\\fakepath\\","");
    $.ajaxFileUpload({  
    	type: 'POST',
        url: 'ProjectDocumentUpload',
        secureuri: false,  
        fileElementId: 'add_agreement1',//file标签的id  
        dataType: 'text',
        data: {
        	type:'add',
       	 	Classify:'Instructions'
       	},
    	success: function(data){
    		var newData = JSON.parse(data);
    		if(newData.Response.indexOf("成功")>-1){
    			ithat.text("成功！");
    			$(".add_fileCont1").html(filePath);
    			$(".contract_add #add_agreement1").attr("title",filePath);
    			fileUploadSavePath.InstructionsPath = newData.Path;
    			fileUploadSavePath.addInstructions = filePath;
    		}
    	},
    	error: function(){
    		$.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
    	}
    });
});
//UI添加上传
$(document).on("change",".add_agreement2",function(){
	var ithat = $(this).parent().siblings("span");
    var filePath=$(this).val().replace("C:\\fakepath\\","");
    $.ajaxFileUpload({  
    	type: 'POST',
        url: 'ProjectDocumentUpload',
        secureuri: false,  
        fileElementId: 'add_agreement2',//file标签的id  
        data: {
        	type:'add'	,
        	Classify: "UISpecification"
        },
        dataType: 'text',
    	success: function(data){
    		var newData = JSON.parse(data);
    		if(newData.Response.indexOf("成功")>-1){
    			ithat.text("成功！");
    			$(".add_fileCont2").html(filePath);
    			$(".contract_add #add_agreement2").attr("title",filePath);
    			fileUploadSavePath.UISpecificationPath = newData.Path;
    			fileUploadSavePath.addUISpecification = filePath;
    		}
    	},
    	error: function(){
    		$.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
    	}
    });
});
//说明书修改上传
$(document).on("change",".update_agreement1",function(){
	var ithat = $(this).parent().siblings("span");
	var ID = $(".contract_update .update_id").text();
    var filePath=$(this).val().replace("C:\\fakepath\\","");
    $.ajaxFileUpload({ 
    	type: 'POST', 
        url: 'ProjectDocumentUpload',
        secureuri: false,  
        fileElementId: 'update_agreement1',//file标签的id  
        data: {
        	ID: ID,
        	type: 'update',
        	Classify: "Instructions"
        },
	    dataType: 'text',
		success: function(data){
			var newData = JSON.parse(data);
			if(newData.Response.indexOf("成功")>-1){
				ithat.text("成功！");
				$(".update_fileCont1").html(filePath);
				$(".contract_update #update_agreement1").attr("title",filePath);
				fileUploadSavePath.InstructionsPath = newData.Path;
				fileUploadSavePath.updateInstructions = filePath;
			}
		},
		error: function(){
			$.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
		}
    });
});
//UI修改上传
$(document).on("change",".update_agreement2",function(){
	var ithat = $(this).parent().siblings("span");
	var ID = $(".contract_update .update_id").text();
    var filePath=$(this).val().replace("C:\\fakepath\\","");
    $.ajaxFileUpload({  
    	type: 'POST',
        url: 'ProjectDocumentUpload',
        secureuri: false,  
        fileElementId: 'update_agreement2',//file标签的id  
        data: {
        	ID:ID,
        	type:'update',
        	Classify:"UISpecification"
        },
	    dataType: 'text',
		success: function(data){
			var newData = JSON.parse(data);
			if(newData.Response.indexOf("成功")>-1){
				ithat.text("成功！");
				$(".update_fileCont2").html(filePath);
				$(".contract_update #update_agreement2").attr("title",filePath);
				fileUploadSavePath.UISpecificationPath = newData.Path;
				fileUploadSavePath.updateUISpecification = filePath;
			}
		},
		error: function(){
			$.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
		}
    });
});

//导出功能
$(".export").click(function(){
	window.location.href="ExportProject";
});
	
// 密码框显隐
$(".basic_info_in .form-control-feedback").click(function(){
	$(this).toggleClass("glyphicon-eye-open glyphicon-eye-close");
	if($(this).is(".glyphicon-eye-close")){
		$(this).siblings("input").attr("type","text");
	}else if($(this).is(".glyphicon-eye-open")){
		$(this).siblings("input").attr("type","password");
	}
});

// 取消搜索
$("input.cancel_search").click(function(){
	window.location.href = 'SoftwareProject';
});

/*原script标签*/
/****************** 跳页 **********************/
var allParam = ecDo.getUrlPrmt(window.location.href);

$(function(){
	var ProductArr = ["IT系统","EUCP","futureC","futureD"];
	var PriorityArr = ["最高","高","普通","低","最低"];
	var StateArr = ["完成","进行中","未完成"];
	var ProductStr = '<option value="" disabled>请选择</option>';
	var PriorityStr = '<option value="" disabled>请选择</option>';
	var StateStr = '<option value="" disabled>请选择</option>';
	ProductArr.map(function(currentValue, index, arr){
		ProductStr+='<option value="'+currentValue+'">'+currentValue+'</option>';
	});
	PriorityArr.map(function(currentValue, index, arr){
		PriorityStr+='<option value="'+currentValue+'">'+currentValue+'</option>';
	});
	StateArr.map(function(currentValue, index, arr){
		StateStr+='<option value="'+currentValue+'">'+currentValue+'</option>';
	});
	$("select[name='Product']").empty().append(ProductStr);
	$("select[name='Priority']").empty().append(PriorityStr);
	$("select[name='State']").empty().append(StateStr);
	pageStyle(Number($('#currentPage').text()),Number($('#allPage').text()));
	// 加载软件部员工
	$.ajax({
		type: 'get',
		url: 'GetSoftwareStaff',
		dataType: 'json',
		success: function(data){
			if(data.length > 1){
				var str11 = '<option value="" disabled>请选择</option>';
				data.map(function(currentValue, index, arr){
					if(index > 0){
						str11+='<option value="'+currentValue.StaffName+'">'+currentValue.StaffName+'</option>';
					}
				});
				$("div.insert_staff>select, select.insert_staff_sel").empty().append(str11);
			}else{
				$.MsgBox.Judge('提示', "获取软件部员工数据为空!");
			}
		},
		error: function(){
			$.MsgBox.Judge('提示', "网络错误请稍后重试!"); 
		},
		complete: function(){
			if(!_.isNil(allParam.QueryJson)){
				_.forIn(JSON.parse(allParam.QueryJson), function(value, key) {
					$("#search_con_"+key).parent().prev().children().trigger("click");
					$("#search_con_"+key).val(value);
					$("#global_table_style th>span[data-isearch='"+key+"']").removeClass("hide_span").addClass("glyphicon-ok");
				});
			}
			
			if(!_.isNil(allParam.Order) && allParam.Order != ""){
				$("input.isOrderd").trigger("click");
				$("input[name='Order_radio'][data-order='"+allParam.Order+"']").trigger("click").parent().next().children().val(allParam.Column);
				if(allParam.Order == "DESC"){
					$("#global_table_style th>span[data-isearch='"+allParam.Column+"']").removeClass("hide_span glyphicon-ok").addClass("glyphicon-sort-by-attributes-alt");
				}else if(allParam.Order == "ASC"){
					$("#global_table_style th>span[data-isearch='"+allParam.Column+"']").removeClass("hide_span glyphicon-ok").addClass("glyphicon-sort-by-attributes");
				}
			}
		}
	});

	$("#global_table_style tr.tbody_tr").each(function(){
		var iState = $(this).find(".State").text();
		if(iState == "未完成"){
			$(this).addClass("line_red");
		}else if(iState == "进行中"){
			$(this).addClass("line_orange");
		}else if(iState == "完成"){
			$(this).addClass("line_green");
		}
	});
});

function FistPage() {
	var newallParam = _.cloneDeep(allParam);
	newallParam.currentPage = "1";
	eouluGlobal.S_settingURLParam(newallParam, false, false, false);
}
function UpPage(arg) {
	var newallParam = _.cloneDeep(allParam);
	newallParam.currentPage = Number($('#currentPage').text()) - 1;
	eouluGlobal.S_settingURLParam(newallParam, false, false, false);
}
function NextPage(arg) {
	var newallParam = _.cloneDeep(allParam);
	newallParam.currentPage = Number($('#currentPage').text()) + 1;
	eouluGlobal.S_settingURLParam(newallParam, false, false, false);
}
function PageJump(arg) {
	var jumpNumber = document.getElementById("jumpNumber").value;
	if (jumpNumber == null || jumpNumber == 0) {
		jumpNumber = $('#currentPage').html();
	} else if (jumpNumber > parseInt($('#allPage').html())) {
		jumpNumber = $('#allPage').html();
	}
	var newallParam = _.cloneDeep(allParam);
	newallParam.currentPage = jumpNumber;
	eouluGlobal.S_settingURLParam(newallParam, false, false, false);
}
function LastPage(arg) {
	var newallParam = _.cloneDeep(allParam);
	newallParam.currentPage = $("#search_i_pageCounts").val();
	eouluGlobal.S_settingURLParam(newallParam, false, false, false);
}
/*原script标签 end*/

/*2018-09-29更新*/
$("input.search_all_box").click(function(){
	$(".cover-color, .search_all").slideDown(200);
});

$("input[name='search_checkbox']").change(function(){
	var $formitem = $(this).parent().next().children();
	$formitem.prop("disabled", !$(this).prop("checked"));
	if($(this).prop("checked")){
		$("select[name='Order_select']").append("<option value='"+$formitem.attr("id").replace("search_con_", "")+"'>"+$(this).parent().text().trim()+"</option>");
	}else{
		$("select[name='Order_select']>option[value='"+$formitem.attr("id").replace("search_con_", "")+"']").remove();
	}
});

$("input[name='Order_radio']").click(function(){
	if($("input.isOrderd").prop("checked")) return false;
});

$("input[name='Order_radio']").change(function(){
	$("input[name='Order_radio']:checked").parent().next().children().prop("disabled", false);
	$("input[name='Order_radio']:not(:checked)").parent().next().children().prop("disabled", true);
});

$("input.isOrderd").change(function(){
	if($(this).prop("checked")){
		$("input[name='Order_radio']").prop("checked", false).parent().next().children().prop("disabled", true);
	}else{
		$("input[name='Order_radio']:first").trigger("click");
	}
});

$("#search_all_submit").click(function(){
	var searchObj = {};
	var queryJson = {};
	$(".search_all_bodyin input[name='search_checkbox']").each(function(){
		var iDOM = $(this).parent().next().children();
		var iKey = iDOM.attr("id").replace("search_con_", "");
		if($(this).prop("checked")){
			queryJson[iKey] = iDOM.val().trim();
		}
	});
	searchObj.QueryJson = JSON.stringify(queryJson);
	if($("input.isOrderd").prop("checked")){
		searchObj.Column = "";
		searchObj.Order = "";
	}else{
		searchObj.Order = $("input[name='Order_radio']:checked").data("order");
		searchObj.Column = $("input[name='Order_radio']:checked").parent().next().children().val();
	}
	searchObj.currentPage = "1";
	eouluGlobal.S_settingURLParam(searchObj, false, false, false);
});

$(".search_all_tit>span, #search_all_cancel").click(function(){
	$(".cover-color, .search_all").slideUp(200);
});

$(document).on("click", ".delete_span", function(){
	var ID = $(this).attr("value");
	$.ajax({
		type: "POST",
		url: "SoftwareProjectOperate",
		data: {
			ID: ID,
			type: "delete"
		},
		dataType: "text",
		success: function(data){
			$.MsgBox.Alert("删除提示", data);
		},
		error: function(){
			$.MsgBox.Judge('提示', "网络繁忙！");
		}
	});
});
/*2018-09-29更新 end*/
