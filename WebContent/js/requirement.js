/**
 * Created by eoulu on 2017/3/29.
 */
$(function(){
    alertResponse("div.contract_send",700,800);
    $(window).on("resize",function(){
        alertResponse("div.contract_send",700,800);
    });
	$("#distpicker2").distpicker();
	$(".icon-province").on("click",function(e){
		$("#distpicker2").distpicker('reset');
		$("#provinceval option:eq(0)").attr("selected",true);
		$("#cityval option:eq(0)").attr("selected",true);
		$(".isprovince").toggle();
		/*$(".isprovince").toggleClass("display-hook");
		var displayhook = $(".isprovince").hasClass("display-hook");
		alert(displayhook);
		if(displayhook==true||displayhook=="true"){
			$(".basic_info").on("click",function(e){ 
				$(".isprovince").hide();
			    e.stopPropagation();
			});
		}*/
		$(document).one("click",function(){
			$(".isprovince").hide();
		});
		e.stopPropagation();
	});
	$(".isprovince").on("click",function(e){
		e.stopPropagation();
	});
	/*$(".pick-resbtn").on("click",function(){
		$("#distpicker2").distpicker('reset');
	});*/
	$("#provinceval").on("change",function(){
		$("#cityval option:eq(0)").attr("selected",true);
	});
	$("#cityval").on("change",function(){
		var vala = $("#provinceval").val();
		var valb = $("#cityval").val();
		var valpro = vala+valb;
		$("#changeProvince").attr("readonly",false);
		$("#changeProvince").val(valpro);
		$("#changeProvince").attr("readonly",true);
		/*alert(valpro);*/
		$(".isprovince").hide();
	});
});

$('.cover-color, .bg_requirement').height($(document).height()-80);

//判断是否报备
$('.report').click(function() {
	var tr=$(this).parent().parent();
	var ID = tr.find('td.xuhao').attr("value");
	 var ChinaCompany = tr.find('td.Company').text();
	 var youjian = tr.find('td.Email').text();
	 var EnglishName = tr.find('td.EnglishName').text();
     var Company;
	 if((ChinaCompany == '' || ChinaCompany == '--'  ) && ( EnglishName == '' ||  EnglishName == '--')){
		 Company = '';
	 }else if((ChinaCompany != '' || ChinaCompany != '--'  ) && ( EnglishName == '' ||  EnglishName == '--')){
		 Company=ChinaCompany;
	 }else if((ChinaCompany == '' || ChinaCompany == '--'  ) && ( EnglishName != '' ||  EnglishName != '--')){
		 Company=EnglishName;
	 }else if((ChinaCompany != '' || ChinaCompany != '--'  ) && ( EnglishName != '' ||  EnglishName != '--')){
		 Company=EnglishName+"("+ChinaCompany+")";
	 }
	 var myDate = new Date();
	 var Y = myDate.getFullYear();
	 var M = myDate.getMonth()+1;
	 var D = myDate.getDate();
	 var date = D +'/'+M+'/'+Y;
	 console.log(date);
	$('.contract_send').find('input[name="Company"]').val(Company);
	$('.contract_send').find('input[name="Attn"]').val(tr.find('td.Attn').text());
	$('.contract_send').find('input[name="Tel"]').val(tr.find('td.Tel').text());
	$('.contract_send').find('input[name="Email"]').val(youjian);
	$('.contract_send').find('input[name="RefNo"]').val(tr.find('td.RefNo').text());
	$('.contract_send').find('input[name="Date"]').val(date);
    $('.contract_send').find('input[name="Website"]').val(tr.find('td.Website').text());
    var iExecutiontime = tr.find('td.ExceptedPayTime').text();
    var newExecutiontime;
    if(iExecutiontime==""||iExecutiontime=="--"){
        newExecutiontime = "";
    }else{
        newExecutiontime = globalDataCNToENMonth(iExecutiontime);
    }
	$('.contract_send').find('.report_Executiontime').val(newExecutiontime);
	$('.cover-color').show();
    $('.contract_send').show();
    
    	 //点击确认按钮
        $(document).on("click","#send_submit",function(){
        	var Preparation="yes";
        	var Company=$('.contract_send input[name="Company"]').val();
        	var Website= $('.contract_send input[name="Website"]').val();
        	var CustomerDepartment = tr.find('td.CustomerDepartment').text();
        	var DepartmentEnglish = tr.find('td.DepartmentEnglish').text();
        	//最新代码
            var Company2;
        	if(Company == '' || Company == '--'  ){
        		Company2 = '';
        	}else if(Company != '' || Company != '--'  ){
        		//不为空判断，如果纯英文则Arial ;中英文都有或者只有中文则微软雅黑
        		if(/[\u4e00-\u9fa5]+/.test(Company)){
            		Company2 = '<span style="font-family: Microsoft YaHei;font-size: 14px;color:black">'+Company+'</span>';
            		}else{
            		Company2 = '<span style="font-family:Arial ;font-size: 14px;color:black">'+Company+'</span>';
            		}
        	}
        	//网站 
        	if(Website == '' || Website == '--'){
        		Website = '';
        	}else{
        		Website = '<span style="font-family: Arial;font-size: 14px;color:black">'+Website+'</span>';
        	}
    	
    	//判断Company2  和 Website 是否存在加<br>@@@@@@@****************************************
        var CDW;
    	if(Company2 && Website){
    		CDW = Company2 +'<br>'+ Website;
    	}else if(!Company2 && Website){
    		CDW = Website;
    	}else if(Company2 && !Website){
    		CDW =  Company2;
    	}
        	//这段代码暂且注释掉，改为从报备面板中直接获取，字体一致************************************
        	//客户公司名称英文（中文）
	        	/*if((ChinaCompany == '' || ChinaCompany == '--'  ) && ( EnglishName == '' ||  EnglishName == '--')){
	       		 var Company2 = '';
		       	 }else if((ChinaCompany != '' || ChinaCompany != '--'  ) && ( EnglishName == '' ||  EnglishName == '--')){
		       	 var Company2 = '<span style="font-family: Microsoft YaHei;font-size: 14px;">'+ChinaCompany+'</span>';
		       	 }else if((ChinaCompany == '' || ChinaCompany == '--'  ) && ( EnglishName != '' ||  EnglishName != '--')){
		       		 var Company2='<span style="font-family: Arial;font-size: 14px;color:black">'+ EnglishName +'</span>';
		       	 }else if((ChinaCompany != '' || ChinaCompany != '--'  ) && ( EnglishName != '' ||  EnglishName != '--')){
		       		 var Company2=EnglishName+"("+ChinaCompany+")";
		       		var Company2='<span style="font-family: Arial;font-size: 14px;color:black">'+ EnglishName +'</span><span style="font-family: Arial;font-size: 14px;color:black">('+ ChinaCompany +')</span>';
		       	 }*/
        	//部门名称英文（中文）
	        	/*if((CustomerDepartment == '' || CustomerDepartment == '--'  ) && ( DepartmentEnglish == '' ||  DepartmentEnglish == '--')){
	          		 var Department = '';
	   	       	 }else if((CustomerDepartment != '' || CustomerDepartment != '--'  ) && ( DepartmentEnglish == '' ||  DepartmentEnglish == '--')){
	   	       	 var Department = '<span style="font-family: Microsoft YaHei;font-size: 14px;">'+CustomerDepartment+'</span>';
	   	       	 }else if((CustomerDepartment == '' || CustomerDepartment == '--'  ) && ( DepartmentEnglish != '' ||  DepartmentEnglish != '--')){
	   	       		 var Department='<span style="font-family: Arial;font-size: 14px;color:black">'+ DepartmentEnglish +'</span>';
	   	       	 }else if((CustomerDepartment != '' || CustomerDepartment != '--'  ) && ( DepartmentEnglish != '' ||  DepartmentEnglish != '--')){
	   	       		
	   	       		var Department='<span style="font-family: Arial;font-size: 14px;color:black">'+ DepartmentEnglish +'</span><span style="font-family: Arial;font-size: 14px;color:black">('+ CustomerDepartment +')</span>';
	   	       	 }*/
        	//网站 
	        	/*if(Website == '' || Website == '--'){
	        		var Website = '';
	        		
	        	}else{
	        		var Website = '<span style="font-family: Arial;font-size: 14px;color:black">'+Website+'</span>';
	        	}*/
        	
        	//判断Company2 Department 和 Website 是否存在加<br>@@@@@@@****************************************
        	/*if(Company2 && Department && Website){
        		var CDW = Company2 +'<br>'+ Department +'<br>'+ Website;
        	}else if(!Company2 && Department && Website){
        		var CDW = Department +'<br>'+ Website;
        	}else if(Company2 && !Department && Website){
        		var CDW = Company2 +'<br>'+ Website;
        	}else if(Company2 && Department && !Website){
        		var CDW = Company2 +'<br>'+ Department;
        	}else if(!Company2 && !Department && Website){
        		var CDW = Website;
        	}else if(Company2 && !Department && !Website){
        		var CDW = Company2 ;
        	}else if(!Company2 && Department && !Website){
        		var CDW = Department;
        	}*/
        	//这段代码暂且注释掉，改为从报备面板中直接获取，字体一致************************************
        /*	var Company2 = Company +'<br>' +DepartmentEnglish+'('+CustomerDepartment+')'+'<br>'+Website;*/
        	var Attn=$('.contract_send input[name="Attn"]').val();
        	var Tel=$('.contract_send input[name="Tel"]').val();
        	var Email=$('.contract_send input[name="Email"]').val();
        	var UserContact = Tel+'</br>'+Email;
        	var Requirement=$('.contract_send input[name="Requirement"]').val();
            var Requirement2;
        	if(/^[\u4e00-\u9fa5]+/.test(Requirement)){
        		Requirement2 = '<span style="font-family: Microsoft YaHei;font-size: 14px;color:black">'+Requirement+'</span>';
        		}else{
        		Requirement2 = '<span style="font-family: Arial;font-size: 14px;color:black">'+Requirement+'</span>';	
        		}
        	var RequirementDetail=tr.find('td.RequirementContent').text();
        	var EndUser=$('.contract_send input[name="EndUser"]').val();
        	var EndAttn=$('.contract_send input[name="EndAttn"]').val();
        	var EndTel=$('.contract_send input[name="EndTel"]').val();
        	var EndEmail=$('.contract_send input[name="EndEmail"]').val();
        	var EndRequirement=$('.contract_send input[name="EndRequirement"]').val();
        	var Area =tr.find('td.Area').text();
        	var Date = date;
        	var RefNo= $('.contract_send input[name="RefNo"]').val();
        	if(/^[\u4e00-\u9fa5]+/.test(RefNo)){
        		RefNo = '<span style="font-family: Microsoft YaHei;font-size: 14px;color:black">'+RefNo+'</span>';
        		}else{
        		RefNo = '<span style="font-family:Arial ;font-size: 14px;color:black">'+RefNo+'</span>';
        		}
        	var BorkerCompanyNameE = $('.contract_send input[name="enborkerCompanyName"]').val();
        	var BorkerCompanyNameC = $('.contract_send input[name="chborkerCompanyName"]').val();
        	var BorkerDepartment = $('.contract_send input[name="borkerDepartment"]').val();
            var BorkerCompany;
        	if(BorkerCompanyNameE == '' && BorkerCompanyNameC == '' && BorkerDepartment == ''){
        		BorkerCompany = '<span style="font-family:  Arial;font-size: 14px;">NA</span>';
        	}else if(BorkerCompanyNameE != '' && BorkerCompanyNameC != '' && BorkerDepartment == ''){
        		BorkerCompany = '<span style="font-family:  Arial;font-size: 14px;">'+BorkerCompanyNameE+'</span> <span style="font-family:Microsoft YaHei;font-size: 14px;">'+BorkerCompanyNameC+'</span>';
        	}else if(BorkerCompanyNameE != '' && BorkerCompanyNameC == '' && BorkerDepartment != ''){
        		BorkerCompany = '<span style="font-family:  Arial;font-size: 14px;">'+BorkerCompanyNameE+'</span><br><span style="font-family: Microsoft YaHei;font-size: 14px;color:black">'+BorkerDepartment+'</span>';
        	}else if(BorkerCompanyNameE == '' && BorkerCompanyNameC != '' && BorkerDepartment != ''){
        		BorkerCompany = '<span style="font-family:Microsoft YaHei;font-size: 14px;">'+BorkerCompanyNameC+'</span><br> <span style="font-family: Microsoft YaHei;font-size: 14px;color:black">'+BorkerDepartment+'</span>';
        	}else if(BorkerCompanyNameE == '' && BorkerCompanyNameC != '' && BorkerDepartment == ''){
        		BorkerCompany = '<span style="font-family:Microsoft YaHei;font-size: 14px;">'+BorkerCompanyNameC+'</span> ';
        	}else if(BorkerCompanyNameE == '' && BorkerCompanyNameC == '' && BorkerDepartment != ''){
        		BorkerCompany = '<span style="font-family: Microsoft YaHei;font-size: 14px;color:black">'+BorkerDepartment+'</span>';
        	}else if(BorkerCompanyNameE != '' && BorkerCompanyNameC == '' && BorkerDepartment == ''){
        		BorkerCompany = '<span style="font-family:  Arial;font-size: 14px;">'+BorkerCompanyNameE+'</span> ';
        	}else{
        		BorkerCompany = '<span style="font-family:  Arial;font-size: 14px;">'+BorkerCompanyNameE+'</span> <span style="font-family:Microsoft YaHei;font-size: 14px;">'+BorkerCompanyNameC+'</span><br> <span style="font-family: Microsoft YaHei;font-size: 14px;color:black">'+BorkerDepartment+'</span>';
        	}
        	var BorkerNameE = $('.contract_send input[name="borkerNameE"]').val();
        	var BorkerNameC = $('.contract_send input[name="borkerNameC"]').val();
        	console.log(BorkerCompany);
            var BorkerName;
        	if(BorkerNameE == '' && BorkerNameC == ''){ 
        		BorkerName = '<span style="font-family:  Arial;font-size: 14px;">NA</span>';
        	}else if(BorkerNameE == '' && BorkerNameC != ''){
        		BorkerName = '<span style="font-family: Microsoft YaHei;font-size: 14px;">'+BorkerNameC+'</span>';
        	}else if(BorkerNameE != '' && BorkerNameC == ''){
        		BorkerName = '<span style="font-family:  Arial;font-size: 14px;">'+BorkerNameE+'</span>';
        	}else if(BorkerNameE != '' && BorkerNameC != ''){
        		BorkerName = '<span style="font-family:  Arial;font-size: 14px;">'+BorkerNameE+'</span><span style="font-family: Microsoft YaHei;font-size: 14px;">('+BorkerNameC+')</span>';
        	}
        	var BorkerPhone = $('.contract_send input[name="borkerPhone"]').val();
        	var BorkerEmail = $('.contract_send input[name="borkerEmail"]').val();
            var BorkerContact;
        	if(BorkerPhone == '' && BorkerEmail == ''){
        		BorkerContact = '<span style="font-family:  Arial;font-size: 14px;">NA</span>';
        	}else if(BorkerPhone == '' && BorkerEmail != ''){
        		BorkerContact = BorkerEmail;
        	}else if(BorkerPhone != '' && BorkerEmail == ''){
        		BorkerContact = BorkerPhone;
        	}else if(BorkerPhone != '' && BorkerEmail != ''){
        		BorkerContact = BorkerPhone+'<br>'+BorkerEmail;
        	}
            var email_Executiontime = '<span style="font-family:  Arial;font-size: 14px;">'+$('.contract_send .report_Executiontime').val().trim()+'</span>';
        	var content = '<table class="customer" style="border-color:#333; border-collapse: collapse;font-size: 14px;color:black">'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Arial;font-size: 14px;color:black">Ref.No</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;color:black">'+RefNo+'</td>'+
		'</tr>'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Arial;font-size: 14px;color:black">Date</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Arial;font-size: 14px;color:black">'+ Date +'</span></td>'+
		'</tr>'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Arial;font-size: 14px;color:black">End User Company, Dept.</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;">'+CDW+'</td>'+
		'</tr>'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Arial;font-size: 14px;color:black">End User Name</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Microsoft YaHei,Arial;font-size: 14px;">'+ Attn +'</span></td>'+
		'</tr>'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Arial;font-size: 14px;color:black">End User Contact Information</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Arial;font-size: 14px;color:black">'+ UserContact +'</span></td>'+
		'</tr>'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Arial;font-size: 14px;color:black">Broker Company</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-size: 14px;color:black">'+ BorkerCompany +'</span></td>'+
		'</tr>'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Arial;font-size: 14px;color:black">Broker Name</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-size: 14px;color:black">'+ BorkerName +'</span></td>'+
		'</tr>'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Arial;font-size: 14px;color:black">Broker Contact Information</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Arial;font-size: 14px;color:black">'+ BorkerContact +'</span></td>'+
		'</tr>'+
		'<tr>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Arial;font-size: 14px;color:black">Requirement</span></td>'+
			'<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;">'+ Requirement2+'</td>'+
		'</tr>'+
        '<tr>'+
            '<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;"><span style="font-family: Arial;font-size: 14px;color:black">Estimated Execution time(Month)</span></td>'+
            '<td style="width: 230px;padding-left: 10px;padding-right: 10px;line-height: 28px;border-color:#333; border:1px solid #333;font-size: 14px;">'+ email_Executiontime+'</td>'+
        '</tr>'+
	'</table>';
        		$.ajax({
    	            type : 'POST',
    	            url : 'RequirementPreparation',
    	            data : {
    	            	ID:ID,
//    	            	Preparation : Preparation,
    	            	Company:Company,
//    	            	Attn:Attn,
//    	            	Tel:Tel,
//    	            	Email:Email,
    	            	Requirement:Requirement,
//    	            	EndUser:EndUser,
//    	            	EndAttn:EndAttn,
//    	            	EndTel:EndTel,
//    	            	EndEmail:EndEmail,
//    	            	EndRequirement:EndRequirement,
    	            	Area:Area,
    	            	Content:content
    	            },
    	            dataType : 'json',
    	            success : function (data) {
                        console.log(data);
                        console.log(typeof data);
                        if (data==true) {
                            $.MsgBox.Alert('提示','发送成功!');
                            $('.cover_color').hide();
                            $('.contract_send').hide();
                        }else{
                            $.MsgBox.Alert("提示", "发送失败！");
                        }
    	                
    	            },
    	            error : function () {
    	                $.MsgBox.Alert("提示", "发送失败！");
    	            }
    	        });
        });
});

//报备取消
	$('#send_cancel').click(function() {
		$('.cover-color').hide();
		$('.contract_send').hide();
		window.location.reload();
	});

//显示最终用户信息
$('#fa-buttonEnd').click(function() {
	$('.cover-color').show();
	$('.Table_End').show();
});
$('#fa-buttonHide').click(function() {
	$('.cover-color').hide();
	$('.Table_End').hide();
});

// ----------------------------------------搜索框----------------------------------------------
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
function Search() {
    $('#search').val('search');
    $('#top_text_from').submit();
}
function Cancel() {
    /*$('#search').val('cancel');
    $('input[name="searchContent1"]').val('');
    $('input[name="searchContent2"]').val('');*/
    /*$('#top_text_from').submit();*/
    window.location.href="Requirement";
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
	$('.info_add input[type="text"]').val('');
	$('.info_add input[type="date"]').val('');
	$('.info_add input[type="month"]').val('');
	$('.info_add input[type="search"]').val('');
	$('.info_add textarea').val('');
	$('.info_add select').each(function(){
	    $(this).find('option:checked').prop("selected",false);
	    $(this).find('option').filter(function(){return $(this).text()=='';}).prop("selected",true);
	 });
    $('.cover-color').show();
    $('.info_add').show();
}
$('.infoAdd_close').click(function () {
    $('.cover-color').hide();
    $('.info_add').hide();
});

// $('.info_add input[name="customer_search"]').keyup(function () {
//     var model = $(this).val();
//     $('.info_add select[name="customer_name"]').find('option').each(function () {
//         if($(this).val().indexOf(model)!=-1){
//             $(this).show();
//         } else{
//             $(this).hide();
//         }
//     });

//     $('.info_add select[name="customer_name"]').show();
// });
$(document).on("keyup",'.info_add input[name="customer_search"]',function(){
    if($(this).val()==""){
        return;
    }
    var addKeyword = $(this).val().trim();
    if(addKeyword == "" || addKeyword == "--"){
        $.MsgBox_Unload.Alert("提示", "客户单位搜索值为空或格式错误！");
        return;
    }else{
        var CustomerName = $(this).val().trim();
        $.ajax({
            type : 'GET',
            url : 'GetCustomer',
            data: {
                CustomerName: CustomerName
            },
            success : function (data) { 
                var newData = JSON.parse(data);
                console.log(newData);
                var str='';
                if(newData.length>1){
                    for(var i = 1;i<newData.length;i++){
                        str+='<option value="'+newData[i].CustomerName+'" text="'+newData[i].ID+'" contact="'+newData[i].Contact+'" oldcontactinfo1="'+newData[i].ContactInfo1+'">'+newData[i].CustomerName+'&nbsp;:&nbsp;'+newData[i].Contact+'</option>';
                    }
                }
                $(".add_customer_select").empty().append(str);
                $('.add_customer_select').show();
            },
            error : function () {
                $.MsgBox_Unload.Alert("提示", "服务器繁忙，客户单位数据获取有误！");
            }
        });
    }
});

$(document).on("click", '.info_add select[name="customer_name"] option', function () {
    $('.info_add select[name="customer_name"]').hide();
    $('.info_add input[name="customer_search"]').val($('.info_add select[name="customer_name"]').val());
    $('.info_add input[name="customer_search"]').attr("ID",$(this).attr("text"));
    $('.info_add input[name="customer_search"]').attr("oldcontact",$(this).attr("contact"));
    $('.info_add input[name="customer_search"]').attr("oldcontactinfo1",$(this).attr("oldcontactinfo1"));
    var CustomerID = $(this).attr("text");
    $.ajax({
    	type:'get',
    	url:'GetCustomerArea',
    	dataType:'json',
    	data:{
    		CustomerID:CustomerID
    	},
    	success:function(data){
    		console.log(data);
    		 $('.info_add input[name="Province"]').val(data.province);
    		 $('.info_add input[name="area"]').val(data.area);
    	},
    	error:function(data){
    		
    	}
    });
});

$(document).on("keyup",'.info_update input[name="customer_search"]',function(){
    if($(this).val()==""){
        return;
    }
    var addKeyword = $(this).val().trim();
    if(addKeyword == "" || addKeyword == "--"){
        $.MsgBox_Unload.Alert("提示", "客户单位搜索值为空或格式错误！");
        return;
    }else{
        var CustomerName = $(this).val().trim();
        $.ajax({
            type : 'GET',
            url : 'GetCustomer',
            data: {
                CustomerName: CustomerName
            },
            success : function (data) { 
                var newData = JSON.parse(data);
                console.log(newData);
                var str='';
                if(newData.length>1){
                    for(var i = 1;i<newData.length;i++){
                        str+='<option value="'+newData[i].CustomerName+'" text="'+newData[i].ID+'" contact="'+newData[i].Contact+'" oldcontactinfo1="'+newData[i].ContactInfo1+'">'+newData[i].CustomerName+'&nbsp;:&nbsp;'+newData[i].Contact+'</option>';
                    }
                }
                $(".update_customer_select").empty().append(str);
                $('.update_customer_select').show();
            },
            error : function () {
                $.MsgBox_Unload.Alert("提示", "服务器繁忙，客户单位数据获取有误！");
            }
        });
    }
});

$(document).on("click", '.info_update select[name="customer_name"] option', function () {
    $('.info_update select[name="customer_name"]').hide();
    $('.info_update input[name="customer_search"]').val($('.info_update select[name="customer_name"]').val());
    $('.info_update input[name="customer_search"]').attr("ID",$(this).attr("text"));
    $('.info_update input[name="customer_search"]').attr("oldcontact",$(this).attr("contact"));
    $('.info_update input[name="customer_search"]').attr("oldcontactinfo1",$(this).attr("oldcontactinfo1"));
    var CustomerID = $(this).attr("text");
    $.ajax({
    	type:'get',
    	url:'GetCustomerArea',
    	dataType:'json',
    	data:{
    		CustomerID:CustomerID
    	},
    	success:function(data){
    		console.log(data);
    		 $('.info_update input[name="Province"]').val(data.province);
    		 $('.info_update input[name="area"]').val(data.area);
    	},
    	error:function(data){
    		
    	}
    })
});

$('#add_submit').click(function () {
	
    var customer_name=$('.info_add select[name="customer_name"] option:selected').attr('text');
    var BusinessMan    = $('.info_add .BusinessMan option:selected').val();
    if(BusinessMan == '请选择'){
    	BusinessMan  = '--';
    }
    var requirement_date = $('.info_add input[name="requirement_date"]').val();
    var customer_search = $('.info_add input[name="customer_search"]').val();
    /*alert(customer_search)*/
    if(requirement_date==""){
        $.MsgBox.Judge('提示', "时间不能为空！");
        return;
    }
    if(customer_name == 0 || customer_search == ''){
        $.MsgBox.Judge('提示', "请选择正确的客户名！");
        return;
    }
    /*alert(BusinessLeader)*/
//  发送ajax请求，传参客户名称id，返回客户联系人和联系方式
    var CustomerID = customer_name;
    $.ajax({
    	type:'get',
    	/*type:'post',*/
    	url:'GetCustomerContact',
    	dataType:'json',
    	data:{
    		CustomerID:CustomerID
    	},
    	/*headers: {'Content-Type':'application/json;charset:UTF-8'},*/
    	success:function(data){
//    	获取值传参用
    		var id = $('.info_add input[name="id"]').val();
    		var requirement_date = $('.info_add input[name="requirement_date"]').val();
    	    var customer_name=$('.info_add select[name="customer_name"] option:selected').attr('text');
    	    console.log(customer_name)
    	    var area = $('.info_add input[name="area"]').val();
    	    if(area == ''){
    	    	 $.MsgBox_Unload.Alert("提示","请至客户信息页面完善区域信息！");
    	        return;
    	    }
    	    else if(area == "北方"){
    	    	area = 3
    	    }
    	    else if(area == "南方"){
    	    	area = 2
    	    }
    	    else{
    	    	area = 1
    	    }
    	    var brand = $('.info_add select[name="brand"]').val();
    	    var requirement_classify = $('.info_add select[name="requirement_classify"]').val();
    	    var demand_sources = $('.info_add select[name="demand_sources"]').val();
    	    var whether_quotes = $('.info_add select[name="whether_quotes"]').val();
    	    var sales_man = $('.info_add select[name="sales_man"]').val();
    	    var single_probability = $('.info_add select[name="single_probability"]').val();
    	    var excepted_pay_time= $('.info_add input[name="excepted_pay_time"]').val();
    	    var whether_single = $('.info_add select[name="whether_single"]').val();
    	    var progress_status = $('.info_add textarea[name="progress_status"]').val();
    	    var follow_plan = $('.info_add textarea[name="follow_plan"]').val();
    	    var quotation_number = $('.info_add input[name="quotation_number"]').val();
    	    var USD_quotes = $('.info_add input[name="USD_quotes"]').val();
    	    var RMB_quotes = $('.info_add input[name="RMB_quotes"]').val();
    	    var Province = $('.info_add input[name="Province"]').val();
    	    var CustomerLevel  = $('.info_add input[name="CustomerLevel"]').val();
    	    console.log(CustomerLevel)
    	    var RefNo  = $('.info_add input[name="RefNo"]').val();
    	    /*alert(RefNo)*/
    		var requirement_content = $('.info_add input[name="requirement_content"]').val();
    		
    		// 获取值拼接Content用   
    		var requirement_date2 = $('.info_add input[name="requirement_date"]').val();
    	    var customer_name2=$('.info_add select[name="customer_name"] option:selected').val();
    	   /* var area2 = $('.info_add select[name="area"] option:selected').text();*/
    	    var area2 = $('.info_add input[name="area"]').val();
    	    var brand2 = $('.info_add select[name="brand"] option:selected').text();
    	    var requirement_classify2 = $('.info_add select[name="requirement_classify"] option:selected').text();
    	    var demand_sources2 = $('.info_add select[name="demand_sources"] option:selected').text();
    	    var whether_quotes2 = $('.info_add select[name="whether_quotes"] option:selected').text();
    	    var sales_man2 = $('.info_add select[name="sales_man"] option:selected').text();
    	    var single_probability2 = $('.info_add select[name="single_probability"] option:selected').text();
    	    var excepted_pay_time2 = $('.info_add input[name="excepted_pay_time"]').val();
    	    var whether_single2 = $('.info_add select[name="whether_single"] option:selected').text();
    	    var progress_status2 = $('.info_add textarea[name="progress_status"]').val();
    	    var follow_plan2 = $('.info_add textarea[name="follow_plan"]').val();
    	    var quotation_number2 = $('.info_add input[name="quotation_number"]').val();
    	    var USD_quotes2 = $('.info_add input[name="USD_quotes"]').val();
    	    var RMB_quotes2 = $('.info_add input[name="RMB_quotes"]').val();
    	    var Province2 = $('.info_add input[name="Province"]').val();
    	    var CustomerLevel2  = $('.info_add input[name="CustomerLevel"]').val();
    	    var RefNo2  = $('.info_add input[name="RefNo"]').val();
    		var requirement_content2 = $('.info_add input[name="requirement_content"]').val();
    		
    		/*商务负责人*/
    		var business_man2 = $('.info_add select.BusinessMan option:selected').text();
    		if(business_man2 == '请选择'){
    			business_man2 = '--';
    		}
    		
    		var Contact2 = data[1].Contact;
    		var ContactInfo1 = data[1].ContactInfo1;
    		
    		//需求内容和联系人可能是中英文所以要判断后进行的拼接
    		if(/^[\u4e00-\u9fa5]+/.test(Contact2)){
    	        		var Contact2 = '<span style="font-family: Microsoft YaHei;">'+Contact2+'</span>';
    	        		}else{
    	        		var Contact2 = '<span style="font-family: Arial;">'+Contact2+'</span>'	
    	        		}
    		 console.log(requirement_content2);
    	     if(/^[\u4e00-\u9fa5]+/.test(requirement_content2)){
    	        		var requirement_content2 = '<span style="font-family: Microsoft YaHei;">'+requirement_content2+'</span>';
    	        		}else{
    	        		var requirement_content2 = '<span style="font-family: Arial;">'+requirement_content2+'</span>'	
    	        		}
    	     console.log(requirement_content2);
    		
    		//增加一个参数Content
    	   var Content2 = '<table border="1" cellspacing="0" cellspadding="0" id="table1" style="width: 100%;height: auto;color: #000;border: 1px solid #00aeef;">'+
				'<tr>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">时间</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">客户名称</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">联系人</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">联系方式</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">区域</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">品牌</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">具体需求 </td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">需求类别</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">需求来源</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">是否报价</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">销售负责人</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">商务负责人</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">成单率</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">预计下单时间</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">是否成单</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">进度状况</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">跟单计划</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">报价单号</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">美金报价</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">人民币报价</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">省份</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">客户等级</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">Ref.No</td>'+
		'</tr>'+
		'<tr>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Arial;">'+requirement_date2+'</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+customer_name2+'</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+Contact2+'</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Arial;">'+ContactInfo1+'</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+area2+'</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+brand2+'</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+requirement_content2+'</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+requirement_classify2+'</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+demand_sources2+'</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+whether_quotes2+'</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+sales_man2+'</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+business_man2+'</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family: Arial;">'+single_probability2+'</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family: Arial;">'+excepted_pay_time2+'</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+whether_single2+'</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;"><a title="'+progress_status2+'">'+progress_status2+'</a></td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+follow_plan2+'</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family: Arial;">'+quotation_number2+'</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Arial;">'+USD_quotes2+'</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Arial;">'+RMB_quotes2+'</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+Province2+'</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Arial;">'+CustomerLevel2+'</td>'+
			'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family: Arial ;">'+RefNo2+'</td>'+
		'</tr>'+
	'</table>';
    	    
    	    if(requirement_date==""){
    	        $.MsgBox.Judge('提示', "时间不能为空！");
    	    }else if(customer_name == 0 || customer_search == ''){
    	        $.MsgBox.Judge('提示', "请选择正确的客户名！");
    	    }else{
    	    	$("#add_submit").attr("disabled",false);
    	    	$("#add_submit").css({
    	    		"background":"#dddddd",
    	    		"color":"#808080",
    	    		"border":"none",
    	    		"box-shadow":"0 0 0 0 #f8fcfd"
    	    	});
    	    	 $.ajax({
    	    	        type: 'post',
    	    	        url: 'RequirementOperate',
    	    	        data: {
    	    	            id: id,
    	    	            requirement_date: requirement_date,
    	    	            customer_name: customer_name,
    	    	            area: area,
    	    	            brand: brand,
    	    	            requirement_content: requirement_content,
    	    	            requirement_classify: requirement_classify,
    	    	            demand_sources: demand_sources,
    	    	            whether_quotes: whether_quotes,
    	    	            sales_man: sales_man,
    	    	            single_probability: single_probability,
    	    	            excepted_pay_time: excepted_pay_time,
    	    	            whether_single: whether_single,
    	    	            progress_status: progress_status,
    	    	            follow_plan: follow_plan,
    	    	            quotation_number: quotation_number,
    	    	            usd_quotes: USD_quotes,
    	    	            rmb_quotes: RMB_quotes,
    	    	            Province : Province,
    	    	            CustomerLevel : CustomerLevel,
    	    	            RefNo : RefNo,
    	    	            Content:Content2,
    	    	            BusinessMan:BusinessMan,
    	    	            classify:'新增'
    	    	        },
    	    	        dataType: 'json',
    	    	        headers: {'Content-Type':'application/x-www-form-urlencoded;charset:UTF-8'},
    	    	        success: function (data) {
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

    	    }
    		
    	}
    	});
    })
    
    
    
    
$('#add_cancel').click(function () {
    $('.cover-color').hide();
    $('.info_add').hide();
});

var update_Contact = "";
//-------------------------------------------修改-----------------------------------------------
$(document).on('click','.edit',function () {
    // var tr = $(this).parent().parent();
    var tr = $(this).parent();
    var info_update = $('.info_update');
    var Prepare = $("#Prepare").html();
    console.log(Prepare);
    /*alert(tr.find('td.RefNo').html())*/
    if(Prepare){
        info_update.find('input[name="RefNo"]').val(tr.find('td.RefNo').html());
    }else{
        info_update.find('input[name="RefNo"]').val(tr.find('td.RefNo').html());
    }
    info_update.find('input[name="id"]').val($(this).attr('value'));
    info_update.find('input[name="requirement_date"]').val(tr.find('td').eq(2).html());
    // info_update.find('select[name="customer_name"]').find('option[text="' + tr.find('td.Company').find('input').val() + '"]').prop("selected", true).siblings().prop("selected", false);
    info_update.find('input[name="customer_search"]').val("").val(tr.find('td.Company').text().trim());
    info_update.find('input[name="area"]').val(tr.find('td.Area').html());
    /*if(tr.find('td.Area').html()==""){
        info_update.find('select[name="area"]').find('option[text="未选择"]').prop("selected", true);
    }else{
        info_update.find('select[name="area"]').find('option[text="' + tr.find('td.Area').html() + '"]').prop("selected", true);
    }*/
    
    if(tr.find('td.Brand').html()==""){
        info_update.find('select[name="brand"]').find('option[text="未选择"]').prop("selected", true);
    }else{
        info_update.find('select[name="brand"]').find('option[text="' + tr.find('td.Brand').html() + '"]').prop("selected", true);
    }
    info_update.find('input[name="requirement_content"]').val(tr.find('td.RequirementContent').html());
    if(tr.find('td.Classify').html()==""){
        info_update.find('select[name="requirement_classify"]').find('option[text="未选择"]').prop("selected", true);
    }else{
        info_update.find('select[name="requirement_classify"]').find('option[text="' + tr.find('td.Classify').html() + '"]').prop("selected", true);
    }
    if(tr.find('td.Source').html()==""){
        info_update.find('select[name="demand_sources"]').find('option[text="未选择"]').prop("selected", true);
    }else{
        info_update.find('select[name="demand_sources"]').find('option[text="' + tr.find('td.Source').html() + '"]').prop("selected", true);
    }
    if(tr.find('td.WhetherQuotes').html()==""){
        info_update.find('select[name="whether_quotes"]').find('option[text="未选择"]').prop("selected", true);
    }else{
        info_update.find('select[name="whether_quotes"]').find('option[text="' + tr.find('td.WhetherQuotes').html() + '"]').prop("selected", true);
    }
    if(tr.find('td.SalesMan').html()==""){
        info_update.find('select[name="sales_man"]').find('option[text="未选择"]').prop("selected", true);
    }else{
        info_update.find('select[name="sales_man"]').find('option[text="' + tr.find('td.SalesMan').html() + '"]').prop("selected", true);
    }
    if(tr.find('td.SingleProbability').find('input').val()==""){
        info_update.find('select[name="single_probability"]').find('option[text="未选择"]').prop("selected", true);
    }else{
        info_update.find('select[name="single_probability"]').find('option[value="' + tr.find('td.SingleProbability').find('input').val() + '"]').prop("selected", true);
    }
    info_update.find('input[name="excepted_pay_time"]').val(tr.find('td.ExceptedPayTime').text());
   
    var whether_single = 1;
    if(tr.find('td.WhetherSingle').html()=="否"){
    	whether_single = 0;
    }
    if(tr.find('td.WhetherSingle').html()==""){
        info_update.find('select[name="whether_single"]').find('option[value="0"]').prop("selected", true);
    }else{
        info_update.find('select[name="whether_single"]').find('option[value="' + whether_single + '"]').prop("selected", true);
    }
    info_update.find('textarea[name="progress_status"]').val(tr.find('td.ProgressStatus').find('a').html());
    info_update.find('textarea[name="follow_plan"]').val(tr.find('td.FollowPlan').html());
    info_update.find('input[name="quotation_number"]').val(tr.find('td.QuotationNumber').html());
    info_update.find('input[name="USD_quotes"]').val(tr.find('td.USDQuotes').html());
    info_update.find('input[name="RMB_quotes"]').val(tr.find('td.RMBQuotes').html());
    info_update.find('input[name="Province"]').val(tr.find('td.Province').html());
    info_update.find('input[name="CustomerLevel"]').val(tr.find('td.CustomerLevel').html());
    
    //商务负责人
   var BusinessMan_text = tr.find('td.BusinessMan').html();
   /*alert(BusinessMan_text)*/
   if(BusinessMan_text == '' || BusinessMan_text == '--'){
	   BusinessMan_text = '请选择';
   }
   info_update.find('.BusinessMan_edit').find('option[value="' + BusinessMan_text + '"]').prop("selected", true);
    $('.cover-color').show();
    info_update.show();
    var id = $(this).attr('value');
    $('.info_update input[name="id"]').val(id);
    
  //点击修改就发请求找到客户的联系人和联系方式
	var customer_name=tr.find('td.Company').find('input').val();
	 console.log(customer_name);
	//  发送ajax请求，传参客户名称id，返回客户联系人和联系方式
	    var CustomerID = customer_name;
        $(".info_update .customer_search").attr("ID",CustomerID);
	    $.ajax({
	    	type:'get',
	    	url:'GetCustomerContact',
	    	dataType:'json',
	    	data:{
	    		CustomerID:CustomerID
	    	},
	    	success:function(data){
	    		var Contact2 = data[1].Contact;
	    		var ContactInfo1 = data[1].ContactInfo1;
	    		//现在已经可以获取到修改前的表格的信息用来做修改前邮件表格
	    		// 获取值拼接Before用   
	    		var requirement_date2 = $('.info_update input[name="requirement_date"]').val();
                // var customer_name2=$('.info_update select[name="customer_name"] option:selected').val();
	    	    var customer_name2=$(".info_update .customer_search").val();
	    	   /* var area2 = $('.info_update select[name="area"] option:selected').text();*/
	    	    var area2 = $('.info_update input[name="area"]').val();
	    	    var brand2 = $('.info_update select[name="brand"] option:selected').text();
	    	    var requirement_classify2 = $('.info_update select[name="requirement_classify"] option:selected').text();
	    	    var demand_sources2 = $('.info_update select[name="demand_sources"] option:selected').text();
	    	    var whether_quotes2 = $('.info_update select[name="whether_quotes"] option:selected').text();
	    	    var sales_man2 = $('.info_update select[name="sales_man"] option:selected').text();
	    	    var single_probability2 = $('.info_update select[name="single_probability"] option:selected').text();
	    	    var excepted_pay_time2 = $('.info_update input[name="excepted_pay_time"]').val();
	    	    var whether_single2 = $('.info_update select[name="whether_single"] option:selected').text();
	    	    var progress_status2 = $('.info_update textarea[name="progress_status"]').val();
	    	    var follow_plan2 = $('.info_update textarea[name="follow_plan"]').val();
	    	    var quotation_number2 = $('.info_update input[name="quotation_number"]').val();
	    	    var USD_quotes2 = $('.info_update input[name="USD_quotes"]').val();
	    	    var RMB_quotes2 = $('.info_update input[name="RMB_quotes"]').val();
	    	    var Province2 = $('.info_update input[name="Province"]').val();
	    	    var CustomerLevel2  = $('.info_update input[name="CustomerLevel"]').val();
	    	    var RefNo2  = $('.info_update input[name="RefNo"]').val();
	    		var requirement_content2 = $('.info_update input[name="requirement_content"]').val();
	    		/*商务负责人*/
	    		var business_man2 = $('.info_update select.BusinessMan_edit option:selected').text();
	    		if(business_man2 == '请选择'){
	    			business_man2 = '--';
	    		}
	    	
	    		var Contact2 = data[1].Contact;
	    		var ContactInfo1 = data[1].ContactInfo1;
	    		// update_Contact = Contact2;
                $(".info_update .customer_search").attr("oldcontact",Contact2);
                $(".info_update .customer_search").attr("oldcontactinfo1",ContactInfo1);
	    		//需求内容和联系人可能是中英文所以要判断后进行的拼接
	    		if(/^[\u4e00-\u9fa5]+/.test(Contact2)){
	    	        		var Contact2 = '<span style="font-family: Microsoft YaHei;">'+Contact2+'</span>';
	    	        		}else{
	    	        		var Contact2 = '<span style="font-family: Arial;">'+Contact2+'</span>'	
	    	        		}
	    		 console.log(requirement_content2);
	    	     if(/^[\u4e00-\u9fa5]+/.test(requirement_content2)){
	    	        		var requirement_content2 = '<span style="font-family: Microsoft YaHei;">'+requirement_content2+'</span>';
	    	        		}else{
	    	        		var requirement_content2 = '<span style="font-family: Arial;">'+requirement_content2+'</span>'	
	    	        		}
	    	     console.log(requirement_content2);
	    		
	    		//增加一个参数Content
	    	   var Before = '<table border="1" cellspacing="0" cellspadding="0" id="table1" style="width: 100%;height: auto;color: #000;border: 1px solid #00aeef;">'+
					'<tr>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">时间</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">客户名称</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">联系人</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">联系方式</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">区域</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">品牌</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">具体需求 </td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">需求类别</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">需求来源</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">是否报价</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">销售负责人</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">商务负责人</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">成单率</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">预计下单时间</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">是否成单</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">进度状况</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">跟单计划</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">报价单号</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">美金报价</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">人民币报价</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">省份</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">客户等级</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">Ref.No</td>'+
			'</tr>'+
			'<tr>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Arial;">'+requirement_date2+'</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+customer_name2+'</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+Contact2+'</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Arial;">'+ContactInfo1+'</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+area2+'</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+brand2+'</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+requirement_content2+'</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+requirement_classify2+'</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+demand_sources2+'</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+whether_quotes2+'</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+sales_man2+'</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+business_man2+'</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family: Arial;">'+single_probability2+'</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family: Arial;">'+excepted_pay_time2+'</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+whether_single2+'</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;"><a title="'+progress_status2+'">'+progress_status2+'</a></td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+follow_plan2+'</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family: Arial;">'+quotation_number2+'</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Arial;">'+USD_quotes2+'</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Arial;">'+RMB_quotes2+'</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+Province2+'</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Arial;">'+CustomerLevel2+'</td>'+
				'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family: Arial ;">'+RefNo2+'</td>'+
			'</tr>'+
		'</table>';
	    		
	    		$('#update_submit').click(function () {
	    			
	    			// 获取值拼接After用   
		    		var requirement_date2 = $('.info_update input[name="requirement_date"]').val();
		    	    // var customer_name2=$('.info_update select[name="customer_name"] option:selected').val();
                    var customer_name2 = $(".info_update .customer_search").val();
//		    	    var area2 = $('.info_update select[name="area"] option:selected').text();
		    	    var area2 = $('.info_update input[name="area"]').val();
		    	    var brand2 = $('.info_update select[name="brand"] option:selected').text();
		    	    var requirement_classify2 = $('.info_update select[name="requirement_classify"] option:selected').text();
		    	    var demand_sources2 = $('.info_update select[name="demand_sources"] option:selected').text();
		    	    var whether_quotes2 = $('.info_update select[name="whether_quotes"] option:selected').text();
		    	    var sales_man2 = $('.info_update select[name="sales_man"] option:selected').text();
		    	    var single_probability2 = $('.info_update select[name="single_probability"] option:selected').text();
		    	    var excepted_pay_time2 = $('.info_update input[name="excepted_pay_time"]').val();
		    	    var whether_single2 = $('.info_update select[name="whether_single"] option:selected').text();
		    	    var progress_status2 = $('.info_update textarea[name="progress_status"]').val();
		    	    var follow_plan2 = $('.info_update textarea[name="follow_plan"]').val();
		    	    var quotation_number2 = $('.info_update input[name="quotation_number"]').val();
		    	    var USD_quotes2 = $('.info_update input[name="USD_quotes"]').val();
		    	    var RMB_quotes2 = $('.info_update input[name="RMB_quotes"]').val();
		    	    var Province2 = $('.info_update input[name="Province"]').val();
		    	    var CustomerLevel2  = $('.info_update input[name="CustomerLevel"]').val();
		    	    var RefNo2  = $('.info_update input[name="RefNo"]').val();
		    	    /*alert(RefNo2)*/
		    		var requirement_content2 = $('.info_update input[name="requirement_content"]').val();

		    		/*商务负责人*/
		    		var business_man2 = $('.info_update select.BusinessMan_edit option:selected').text();
		    		if(business_man2 == '请选择'){
		    			business_man2 = '--';
		    		}

		    		// var Contact2 = update_Contact;
                    var Contact2 = $(".info_update .customer_search").attr("oldcontact");
                    var ContactInfo1 = $(".info_update .customer_search").attr("oldcontactinfo1");
		    		//需求内容和联系人可能是中英文所以要判断后进行的拼接
		    		if(/^[\u4e00-\u9fa5]+/.test(Contact2)){
		    	        		var Contact2 = '<span style="font-family: Microsoft YaHei;">'+Contact2+'</span>';
		    	        		}else{
		    	        		var Contact2 = '<span style="font-family: Arial;">'+Contact2+'</span>'	
		    	        		}
		    		 console.log(requirement_content2);
		    	     if(/^[\u4e00-\u9fa5]+/.test(requirement_content2)){
		    	        		var requirement_content2 = '<span style="font-family: Microsoft YaHei;">'+requirement_content2+'</span>';
		    	        		}else{
		    	        		var requirement_content2 = '<span style="font-family: Arial;">'+requirement_content2+'</span>'	
		    	        		}
		    	     console.log(requirement_content2);
		    		
		    		//增加一个参数Content
		    	   var After = '<table border="1" cellspacing="0" cellspadding="0" id="table1" style="width: 100%;height: auto;color: #000;border: 1px solid #00aeef;">'+
						'<tr>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">时间</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">客户名称</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">联系人</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">联系方式</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">区域</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">品牌</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">具体需求 </td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">需求类别</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">需求来源</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">是否报价</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">销售负责人</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">商务负责人</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">成单率</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">预计下单时间</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">是否成单</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">进度状况</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">跟单计划</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">报价单号</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">美金报价</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">人民币报价</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">省份</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">客户等级</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">Ref.No</td>'+
				'</tr>'+
				'<tr>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Arial;">'+requirement_date2+'</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+customer_name2+'</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+Contact2+'</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Arial;">'+ContactInfo1+'</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+area2+'</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+brand2+'</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+requirement_content2+'</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+requirement_classify2+'</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+demand_sources2+'</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+whether_quotes2+'</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+sales_man2+'</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+business_man2+'</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family: Arial;">'+single_probability2+'</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family: Arial;">'+excepted_pay_time2+'</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+whether_single2+'</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;"><a title="'+progress_status2+'">'+progress_status2+'</a></td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+follow_plan2+'</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family: Arial;">'+quotation_number2+'</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Arial;">'+USD_quotes2+'</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Arial;">'+RMB_quotes2+'</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Microsoft YaHei;">'+Province2+'</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family:  Arial;">'+CustomerLevel2+'</td>'+
					'<td style="text-align: center; height: 30px; min-width: 70px;max-width: 150px; word-break: break-all; font-size: 14px; color: #000; font-weight: normal;font-family: Arial ;">'+RefNo2+'</td>'+
				'</tr>'+
			'</table>';
		    		
	    			//传参用，未改动之前代码
	    		    var id = $('.info_update input[name="id"]').val();
	    		    var requirement_date = $('.info_update input[name="requirement_date"]').val();
	    		    // var customer_name=$('.info_update select[name="customer_name"] option:selected').attr('text');
                    var customer_name = $(".info_update .customer_search").attr("ID");
	    		    console.log(customer_name)
	    		    var area = $('.info_update input[name="area"]').val();
	    		    if(area == ''){
	       	    	 $.MsgBox_Unload.Alert("提示","请至客户信息页面完善区域信息！");
		       	        return;
		       	    }
		       	    else if(area == "北方"){
		       	    	area = 3
		       	    }
		       	    else if(area == "南方"){
		       	    	area = 2
		       	    }
		       	    else{
		       	    	area = 1
		       	    }
	    		    var brand = $('.info_update select[name="brand"]').val();
	    		    var requirement_content = $('.info_update input[name="requirement_content"]').val();
	    		    var requirement_classify = $('.info_update select[name="requirement_classify"]').val();
	    		    var demand_sources = $('.info_update select[name="demand_sources"]').val();
	    		    var whether_quotes = $('.info_update select[name="whether_quotes"]').val();
	    		    var sales_man = $('.info_update select[name="sales_man"]').val();
	    		    var single_probability = $('.info_update select[name="single_probability"]').val();
	    		    var excepted_pay_time = $('.info_update input[name="excepted_pay_time"]').val();
	    		    var whether_single = $('.info_update select[name="whether_single"]').val();
	    		    var progress_status = $('.info_update textarea[name="progress_status"]').val();
	    		    var follow_plan = $('.info_update textarea[name="follow_plan"]').val();
	    		    var quotation_number = $('.info_update input[name="quotation_number"]').val();
	    		    var USD_quotes = $('.info_update input[name="USD_quotes"]').val();
	    		    var RMB_quotes = $('.info_update input[name="RMB_quotes"]').val();
	    		    var Province = $('.info_update input[name="Province"]').val();
	    		    var CustomerLevel = $('.info_update input[name="CustomerLevel"]').val();
	    		    var RefNo = $('.info_update input[name="RefNo"]').val();
	    		   /* alert(RefNo)*/
	    		    console.log(Province);
	    		    console.log(CustomerLevel);
	    		    //获取修改后的商务负责人********************************
	    		  //商务负责人
	    		    var  BusinessMan = $(".BusinessMan_edit option:selected").val();
	    		    /*alert(BusinessMan == '请选择')*/
	    		    if(BusinessMan == '请选择'){
	    		    	BusinessMan = '--';
	    		    }
	    		    if(requirement_date==""){
	    		        $.MsgBox.Alert('提示', "时间不能为空！");
	    		    }else if(customer_name==0){
	    		        $.MsgBox.Alert('提示', "请选择正确的客户名！");
	    		    }else{
	    		    	$("#update_submit").attr("disabled",false);
		    			$("#update_submit").css({
		    				"background":"#dddddd",
		    				"color":"#808080",
		    				"border":"none",
		    				"box-shadow":"0 0 0 0 #f8fcfd"
		    			});
	    		    	 $.ajax({
	    		    	        type: 'post',
	    		    	        url: 'RequirementOperate',
	    		    	        data: {
	    		    	            id: id,
	    		    	            requirement_date: requirement_date,
	    		    	            customer_name: customer_name,
	    		    	            area: area,
	    		    	            brand: brand,
	    		    	            requirement_content: requirement_content,
	    		    	            requirement_classify: requirement_classify,
	    		    	            demand_sources: demand_sources,
	    		    	            whether_quotes: whether_quotes,
	    		    	            sales_man: sales_man,
	    		    	            single_probability: single_probability,
	    		    	            excepted_pay_time: excepted_pay_time,
	    		    	            whether_single: whether_single,
	    		    	            progress_status: progress_status,
	    		    	            follow_plan: follow_plan,
	    		    	            quotation_number: quotation_number,
	    		    	            usd_quotes: USD_quotes,
	    		    	            rmb_quotes: RMB_quotes,
	    		    	            Province : Province,
	    		    	            CustomerLevel : CustomerLevel,
	    		    	            RefNo:RefNo,
	    		    	            Before:Before,
	    		    	            After:After,
	    		    	            BusinessMan:BusinessMan,
	    		    	            classify:'修改'
	    		    	        },
	    		    	        dataType: 'json',
	    		    	        success: function (data) {
	    		    	            data = eval("(" + data + ")");
	    		    	        	if(data.message){
	    		    	                $.MsgBox.Alert("提示", "修改成功！");
	    		    	        	}else{
	    		    	                $.MsgBox.Alert('提示', "修改失败！");
	    		    	        	}
	    		    	        },
	    		    	        error: function () {
	    		    	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	    		    	        }
	    		    	    });
	    		    }
	    		   
	    		});
	    		
	    	},
	    	error:function(){
	    		  $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	    	}
	    	})
    
});

//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function () {
    window.location.reload();
});

$('.update_close').click(function () {
    $('.cover-color').hide();
    $('.info_update').hide();
});
$('#update_cancel').click(function () {
    $('.cover-color').hide();
    $('.info_update').hide();
});

$(".pC-li1").click(function(){
    $(".proTotal").fadeIn(400);
    $(".proSingal").fadeOut(400);
});
$(".pC-li2").click(function(){
    $(".proTotal").fadeOut(400);
    $(".proSingal").fadeIn(400);
    $(".proSingal .sinSouth").fadeIn(400);
    $(".proSingal .sinNorth").fadeOut(400);
    $(".proSingal .sinSouthwest").fadeOut(400);

});
$(".pC-li3").click(function(){
    $(".proTotal").fadeOut(400);
    $(".proSingal").fadeIn(400);
    $(".proSingal .sinSouth").fadeOut(400);
    $(".proSingal .sinNorth").fadeIn(400);
    $(".proSingal .sinSouthwest").fadeOut(400);
});

$(".pC-li4").click(function(){
    $(".proTotal").fadeOut(400);
    $(".proSingal").fadeIn(400);
    $(".proSingal .sinSouth").fadeOut(400);
    $(".proSingal .sinNorth").fadeOut(400);
    $(".proSingal .sinSouthwest").fadeIn(400);
});

$(".pC-li5").click(function(){
    window.open('Statistics')
});

//------------------------------隐藏列-------------------------------------
$('#fa-button1').bind('click',function(){
 $('#fa-button1').toggleClass('fa-minus-square');
 $('#table1 tr').find('td:eq(4)').toggle();
 $('#table1 tr').find('td:eq(5)').toggle();
});
$('#fa-button2').bind('click',function(){
	 $('#fa-button2').toggleClass('fa-minus-square');
	 $('#table1 tr').find('td:eq(7)').toggle();
	});
$('#fa-button3').bind('click',function(){
 $('#fa-button3').toggleClass('fa-minus-square');
 $('#table1 tr').find('td:eq(9)').toggle();
 $('#table1 tr').find('td:eq(10)').toggle();
});
$('#fa-button4').bind('click',function(){
 $('#fa-button4').toggleClass('fa-minus-square');
 $('#table1 tr').find('td:eq(18)').toggle();
});

$('#table1 tr').find('td:eq(4)').hide();
$('#table1 tr').find('td:eq(5)').hide();
$('#table1 tr').find('td:eq(9)').hide();
$('#table1 tr').find('td:eq(10)').hide();
$('#table1 tr').find('td:eq(7)').hide();
$('#table1 tr').find('td:eq(18)').hide();

//导出功能
$(".export_ex").click(function(){
	/*window.location.href="Requirement";*/
	/*selected：值为singleSelect
	type1：搜索项
	searchContent1：搜索内容
	
	selected：值为mixSelect
	type1：搜索项
	searchContent1：搜索内容
	type2：搜索项
	searchContent2：搜索内容*/
	if($('input[name="selected"]:checked').val()=='singleSelect'){
		var type1 = $("#type1 option:selected").html();
		var searchContent1 = $("#searchContent1").val();
			if(!searchContent1){
				$.ajax({
					type:'post',
					url:'Requirement',
					success:function(data){
						window.location.href=data;
					},
					error:function(){
						 $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
					}
				})
			}else{
				$.ajax({
					type:'post',
					url:'Requirement',
					data:{
						selected:'singleSelect',
						type1:type1,
						searchContent1:searchContent1
					},
					success:function(data){
						window.location.href=data;
					},
					error:function(){
						 $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
					}
				})
			}
		
	}else{
		var type1 = $("#type1 option:selected").html();
		var searchContent1 = $("#searchContent1").val();
		var type2 = $("#type2 option:selected").html();
		var searchContent2 = $("#searchContent2").val();
		$.ajax({
			type:'post',
			url:'Requirement',
			data:{
				selected:'mixSelect',
				type1:type1,
				searchContent1:searchContent1,
				type2:type2,
				searchContent2:searchContent2
			},
			success:function(data){
				window.location.href=data;
			},
			error:function(){
				 $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
			}
		});
	}
	
});

$(".choose_requirement_i").click(function(){
    $(".bg_requirement, .choose_requirement").slideDown(300);
    $(".choose_requirement_body_in span").text("");
});

// 获取Requirement choose值
$(".choose_requirement_tit_m").click(function(){
    var requirement_val1 = $(".choose_requirement_body_in input[name='choose_requirement_radio']:checked").val();
    var requirement_val2 = $(".choose_requirement_body_in input[name='choose_requirement_radio']:checked").siblings("span").text().trim();
    $('.contract_send input[name="Requirement"]').val(requirement_val1+requirement_val2);
    $(".bg_requirement, .choose_requirement").slideUp(300);
});

$(".choose_requirement_tit_r").click(function(){
    $(".bg_requirement, .choose_requirement").slideUp(300);
});