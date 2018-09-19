var curOperation;
// $('.cover-color').height($(document).height()-80);
//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function() {
	window.location.reload();
});
//--------------------------------------------添加信息-----------------------------------------------
function AddContract() {
    curOperation = 1;
	$('.info_add input[type="date"]').val('');
	$('.info_add input[type="month"]').val('');
	$('.info_add input[type="search"]').val('');
	$('.info_add textarea').val('');
	$('.info_add select').each(function(){
	    $(this).find('option:checked').prop("selected",false);
	    $(this).find('option').filter(function(){return $(this).text()=='';}).prop("selected",true);
	 });
    $('.cover-color').show();
    $('.contract_add').show();
}
$('.contractAdd_close').click(function () {
    $('.contract_add input[type="text"]').val("");
    $(".add_idcard_div .flex_2 input").attr("title","");
    $(".add_passport_div .flex_2 input").attr("title","");
    $('.cover-color').hide();
    $('.contract_add').hide();
});
$('#add_cancel').click(function () {
    $('.cover-color').hide();
    $('.contract_add').hide();
});
$('#add_submit').click(function () {
	 var StaffName    = $('.contract_add .RealName').val();
	 var IDCard    = $('.contract_add .IDCard').val();
	 var Nation    = $('.contract_add .Nation').val();
	 var NativePlace    = $('.contract_add .NativePlace').val();
	 var PoliticalStatus    = $('.contract_add .PoliticalStatus').val();
	 var LinkTel    = $('.contract_add .LinkTel').val();
	 var GraduateInstitutions    = $('.contract_add .GraduateInstitutions').val();
	 var Major    = $('.contract_add .Major').val();
	 var DetailAddress    = $('.contract_add .DetailAddress').val(); 
	 var WorkPlace    = $('.contract_add .WorkPlace').val();
	 var EntryDate    = $('.contract_add .EntryDate').val();
	 var Job    = $('.contract_add .Job').val();
	/*var EducationalBackground = $('.contract_add .EducationalBackground').val();*/
	 var EducationalBackground = $('#EducationalBackground').find('option:selected').attr('text');
	 var Passport = $('.contract_add .Passport').val();
	 var Gender=$('.contract_add select[name="Gender"] option:selected').attr('text');
	    if(Gender=='请选择'){
	    	Gender = '';
	    }
    var Department=$('.contract_add select[name="Department"] option:selected').attr('text');
    if(Department=='请选择'){
    	Department = '';
    }
    var StaffCode    = $('.contract_add .StaffCode').val();
    var Other    = $('.contract_add .Other').val();
    if(EntryDate == ''){
    	EntryDate = '0000-00-00';
    }
    var AccountNumber = $("#add_AccountNumber").val().trim();
    var DepositBank = $("#add_DepositBank").val().trim();
    var Remarks = $("#add_Remarks").val().trim();
    var IDCardFile = $(".add_idcard_div .flex_2 input").val();
    var PassportFile = $(".add_passport_div .flex_2 input").val();
    $.ajax({
    	type:'get',
    	url:'StaffInfoOperate',
    	dataType:'json',
    	data:{
    		StaffName:StaffName,
    		IDCard:IDCard,
    		Nation:Nation,
    		NativePlace:NativePlace,
    		PoliticalStatus:PoliticalStatus,
    		LinkTel:LinkTel,
    		GraduateInstitutions:GraduateInstitutions,
    		Major:Major,
    		DetailAddress:DetailAddress,
    		WorkPlace:WorkPlace,
    		EntryDate:EntryDate,
    		Job:Job,
    		Department:Department,
    		StaffCode:StaffCode,
    		StaffMail:Other,
    		EducationalBackground:EducationalBackground,
    		Passport:Passport,
    		Gender:Gender,
            AccountNumber: AccountNumber,
            DepositBank: DepositBank,
            Remarks: Remarks,
            IDCardFile: IDCardFile,
            PassportFile: PassportFile
    	},
    	success:function(data){
    		if(data){
    			 $.MsgBox.Alert("提示", "添加成功！");
    			 $('.cover-color').hide();
    		    $('.contract_add').hide();
    		}else{
    			 $.MsgBox.Alert("提示", "添加失败，稍后重试！");
    		}
    	},
	    error: function () {
	        $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	    }
    });
});

//-------------------------------------------修改-----------------------------------------------
$('.contract-edit').click(function () {
    curOperation = 2;
    var tr = $(this).parent();
   /* alert(tr.find('.contract-edit').attr('value'))*/
    $('.contract_update').find('.update_id').text(tr.find('.contract-edit').attr('value'));
    $('.contract_update').find('.RealName').val(tr.find('.StaffName').text());
    $('.contract_update').find('.LinkTel').val(tr.find('.LinkTel').text());
    $('.contract_update').find('.Nation').val(tr.find('.Nation').text());
    $('.contract_update').find('.NativePlace').val(tr.find('.NativePlace').text());
    $('.contract_update').find('.GraduateInstitutions').val(tr.find('.GraduateInstitutions').text());
    $('.contract_update').find('.PoliticalStatus').val(tr.find('.PoliticalStatus').text());
    $('.contract_update').find('.EntryDate').val(tr.find('.EntryDate').text());
    $('.contract_update').find('.Job').val(tr.find('.Job').text());
    $('.contract_update').find('.Passport').val(tr.find('.Passport').text());
/*    $('.contract_update').find('.EducationalBackground').val(tr.find('.EducationalBackground').text());*/
    $(".contract_update").find('#EducationalBackground').find('option[text="'+tr.find('.EducationalBackground').text() + '"]').attr("selected",true);
    //性别
    var Gender = tr.find('.Gender').text();
    if(Gender == ''){
    	$('.contract_update').find('select[name="Gender"]').find('option[text="请选择"]').prop("selected", true);
    }else{
    	$('.contract_update').find('select[name="Gender"]').find('option[text="' + Gender + '"]').prop("selected", true);
    }
    //部门
    var Department = tr.find('.Department').text();
    if(Department == ''){
    	$('.contract_update').find('select[name="Department"]').find('option[text="请选择"]').prop("selected", true);
    }else{
    	$('.contract_update').find('select[name="Department"]').find('option[text="' + Department + '"]').prop("selected", true);
    }
    $('.contract_update').find('.Gender').val(tr.find('.Gender').text());
    $('.contract_update').find('.Department').val(tr.find('.Department').text());
    $('.contract_update').find('.WorkPlace').val(tr.find('.WorkPlace').text());
    $('.contract_update').find('.IDCard').val(tr.find('.IDCard').text());
    $('.contract_update').find('.Major').val(tr.find('.Major').text());
    $('.contract_update').find('.DetailAddress').val(tr.find('.DetailAddress').text());
    $('.contract_update').find('.StaffCode').val(tr.find('.StaffCode').text());
    $('.contract_update').find('.Other').val(tr.find('.Other').text());
    $("#update_AccountNumber").val(tr.find(".AccountNumber").text());
    $("#update_DepositBank").val(tr.find(".DepositBank").text());
    $("#update_Remarks").val(tr.find(".Remarks").text());
    $(".update_idcard_div .flex_2 input").val(tr.find(".IDCardFile.t27").attr("title"));
    $(".update_idcard_div .flex_2 input").attr("title",tr.find(".IDCardFile.t27").attr("title"));
    $(".update_passport_div .flex_2 input").val(tr.find(".PassportFile.t28").attr("title"));
    $(".update_passport_div .flex_2 input").attr("title",tr.find(".PassportFile.t28").attr("title"));
	$('.cover-color, .contract_update').show();
    $('.contract_update .flex_3 [data-toggle="popover"]').popover({
        html: true
    });
});
$("#update_submit").click(function(){
	var ID = $('.contract_update .update_id').text();
	var StaffName    = $('.contract_update .RealName').val();
	 var IDCard    = $('.contract_update .IDCard').val();
	 var Nation    = $('.contract_update .Nation').val();
	 var NativePlace    = $('.contract_update .NativePlace').val();
	 var PoliticalStatus    = $('.contract_update .PoliticalStatus').val();
	 var LinkTel    = $('.contract_update .LinkTel').val();
	 var GraduateInstitutions    = $('.contract_update .GraduateInstitutions').val();
	 var Major    = $('.contract_update .Major').val();
	 var DetailAddress    = $('.contract_update .DetailAddress').val(); 
	 var WorkPlace    = $('.contract_update .WorkPlace').val();
	 var EntryDate    = $('.contract_update .EntryDate').val();
	 var Job    = $('.contract_update .Job').val();
	/* var EducationalBackground    = $('.contract_update .EducationalBackground').val();*/
	 var EducationalBackground =$('.contract_update').find('#EducationalBackground').find('option:selected').attr('text');
	 var Passport    = $('.contract_update .Passport').val();
	 var Gender=$('.contract_update select[name="Gender"] option:selected').attr('text');
	    if(Gender=='请选择'){
	    	Gender = '';
	    }
    var Department=$('.contract_update select[name="Department"] option:selected').attr('text');
    if(Department=='请选择'){
    	Department = '';
    }
    var StaffCode    = $('.contract_update .StaffCode').val();
    var Other    = $('.contract_update .Other').val();
    if(EntryDate == ''){
    	EntryDate = '0000-00-00';
    }
    var AccountNumber = $("#update_AccountNumber").val().trim();
    var DepositBank = $("#update_DepositBank").val().trim();
    var Remarks = $("#update_Remarks").val().trim();
    var IDCardFile = $(".update_idcard_div .flex_2 input").val();
    var PassportFile = $(".update_passport_div .flex_2 input").val();
    $.ajax({
    	type:'get',
    	url:'StaffInfoOperate',
    	dataType:'json',
    	data:{
    		ID:ID,
    		StaffName:StaffName,
    		IDCard:IDCard,
    		Nation:Nation,
    		NativePlace:NativePlace,
    		PoliticalStatus:PoliticalStatus,
    		LinkTel:LinkTel,
    		GraduateInstitutions:GraduateInstitutions,
    		Major:Major,
    		DetailAddress:DetailAddress,
    		WorkPlace:WorkPlace,
    		EntryDate:EntryDate,
    		Job:Job,
    		Department:Department,
    		StaffCode:StaffCode,
    		StaffMail:Other,
    		EducationalBackground:EducationalBackground,
    		Passport:Passport,
    		Gender:Gender,
            AccountNumber: AccountNumber,
            DepositBank: DepositBank,
            Remarks: Remarks,
            IDCardFile: IDCardFile,
            PassportFile: PassportFile
    	},
    	success:function(data){
    		if(data){
    			$.MsgBox.Alert('提示','修改成功');
    			 $('.cover-color').hide();
    			  $('.contract_update').hide();
    		}else{
    			 $.MsgBox.Alert("提示", "修改失败，稍后重试！");
    		}
    	},
   
	    error: function () {
	        $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	    }
    });
})
$('.update_close').click(function () {
    $('.cover-color').hide();
    $('.contract_update').hide();
});
/*$('#update_cancel').click(function () {
    $('.cover-color').hide();
    $('.contract_update').hide();
});*/
//删除功能
$("#update_cancel").click(function(){
	 $.MsgBox_Unload.Confirm("提示", "确定要删除该员工信息?");
	/* alert("确定要删除该员工信息！");*/
	 $("#mb_btn_ok_unload").click(function(){
		 var ID =$(".contract_update  .update_id").text();
		 $.ajax({
			 type:'get',
			 url:'StaffInfoOperate',
			 data:{
				 ID:ID,
				 type:'delete'
			 },
			 success:function(data){
				/* alert(1)*/
				 console.log(data)
				if(data == 'true'){
					 $.MsgBox.Alert("提示", "删除成功！");
				}else{
					$.MsgBox.Alert("提示", "删除失败！");
				}
			 },
			 error:function(){
				 $.MsgBox.Alert("提示", "网络错误请稍候重试！");
			 }
		 })
	 })
})

//导出功能
$(".export").click(function(){
	window.open("ExportStaffInfo");
    var body10 = $(document.body),
        form10 = $("<form method='post'></form>");
    form10.attr({"action":"ExportStaffInfo"});
    form10.appendTo(document.body);
    form10.submit();
    document.body.removeChild(form10[0]);
});

//搜索功能
// $('.cover-color').height($(document).height()-80);

if($('input[name="queryType"]:checked').val()=='singleQuery'){
    $('.select-content').css('margin-left','33%');
}else{
    $('.select-content').css('margin-left','23%');

}
function Check(queryType) {
	 if (queryType == 'singleQuery') {
	        $('.select2').hide();
	        $('.select-content').css('margin-left','33%');
	    } else {
	        $('.select2').show();
	        $('.select-content').css('margin-left','23%');
	    }
}

function INSearch() {
    $('#top_text_from').submit();
}
function Cancel() {
   window.location.href="StaffInfo";
}
//--------------------------------------------姓名6-----------------------------------------------
/*$('.RealName').onchange(function(){*/
$(".contract_add .RealName").on("input propertychange",function(){
	var RealName = $(".contract_add .RealName").val();
	$.ajax({
		type:'get',
		url:'GetStaffApplicationName',
		dataType:'JSON',
		data:{
			keyword:RealName
		},
		success:function(data){
			console.log(data);
			 var str = "";
             for(var i = 1 ; i < data.length ; i++){
            	 str += "<li  class='addListLi'>"+data[i].StaffName+"</li>";
             }
             $(".contract_add .RealNamelist").empty();
            $(".contract_add .RealNamelist").append(str);
             $(".contract_add .RealNamelist").show();
		},
		error:function(){
			 $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
		}
	})
})
$(document).on("click",function(){
	$(".contract_add .RealNamelist").hide();
})
$(document).on("click",".contract_add .addListLi",function(){
	var currentCont = $(this).text();
	$(this).parent().prev().val(currentCont);
	$(".contract_add .RealNamelist").hide();
})
//修改的姓名
$(".contract_update .RealName").on("input propertychange",function(){
	var RealName = $(".contract_update .RealName").val();
	$.ajax({
		type:'get',
		url:'GetStaffApplicationName',
		dataType:'JSON',
		data:{
			keyword:RealName
		},
		success:function(data){
			console.log(data);
			 var str = "";
             for(var i = 1 ; i < data.length ; i++){
            	 str += "<li  class='addListLi'>"+data[i].StaffName+"</li>";
             }
             $(".contract_update .RealNamelist").empty();
             $(".contract_update .RealNamelist").append(str);
             $(".contract_update .RealNamelist").show();
		},
		error:function(){
			 $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
		}
	})
})
$(document).on("click",function(){
	$(".contract_update .RealNamelist").hide();
})
$(document).on("click",".contract_update .addListLi",function(){
	var currentCont = $(this).text();
	$(this).parent().prev().val(currentCont);
	$(".contract_update .RealNamelist").hide();
});

//上传文件
function uploadFiles(){                                               
    var formData = new FormData();
    formData.enctype="multipart/form-data";
    formData.append("file",$("#serFinRepUpload")[0].files[0]);//append()里面的第一个参数file对应permission/upload里面的参数file
    // formData.append("FileName",iFileName);
    // formData.append("Type",iType);
    // formData.append("ID",iID);
    $.ajax({
        type:"POST",
        async:true,  //这里要设置异步上传，才能成功调用myXhr.upload.addEventListener('progress',function(e){}),progress的回掉函数
        accept:'text/html;charset=UTF-8',
        data:formData,
        // contentType:"multipart/form-data",
        url: "StaffInfoOperate",
        processData: false, // 告诉jQuery不要去处理发送的数据
        contentType: false, // 告诉jQuery不要去设置Content-Type请求头
        cache: false,
        dataType: "text",
        xhr:function(){                        
            myXhr = $.ajaxSettings.xhr();
            if(myXhr.upload){ // check if upload property exists
                myXhr.upload.addEventListener('progress',function(e){                           
                    var loaded = e.loaded;                  //已经上传大小情况 
                    var total = e.total;                      //附件总大小 
                    var percent = Math.floor(100*loaded/total)+"%";     //已经上传的百分比  
                    console.log("已经上传了："+percent);  
                    var newWidthFloat =  globalToPoint(percent);  
                    var newWidth = newWidthFloat*400;
                    console.log("进度条宽度："+newWidth);   
                    $(".progressIn").css("width",newWidth+"px");
                    $(".progressIn").text(percent);
                }, false); // for handling the progress of the upload
            }
            return myXhr;
        },                    
        success:function(data){
            console.log(typeof data);
            var fileName_add = $("input.serFinRepUploadName").val();
            var uploadClassify = $(".dropFileBox").attr("value");
            if(data.indexOf("文件上传成功")>-1){
                if(curOperation == 1){
                    if(uploadClassify=="IDCard"){
                        var val3 = $(".add_idcard_div .flex_2 input").val().trim();
                        var val3Arr = val3.split(";");
                        val3Arr = $.grep(val3Arr,function(currentValue,index){
                            return currentValue != "";
                        });
                        val3Arr.push(fileName_add);
                        var newval3Arr = globalArrStrUnique(val3Arr);
                        var newval3ArrStr = newval3Arr.join(";")+";";
                        $(".add_idcard_div .flex_2 input").val(newval3ArrStr);
                        $(".add_idcard_div .flex_2 input").attr("title",newval3ArrStr);
                    }else if(uploadClassify=="passport"){
                        var val4 = $(".add_passport_div .flex_2 input").val().trim();
                        var val4Arr = val4.split(";");
                        val4Arr = $.grep(val4Arr,function(currentValue,index){
                            return currentValue != "";
                        });
                        val4Arr.push(fileName_add);
                        var newval4Arr = globalArrStrUnique(val4Arr);
                        var newval4ArrStr = newval4Arr.join(";")+";";
                        $(".add_passport_div .flex_2 input").val(newval4ArrStr);
                        $(".add_passport_div .flex_2 input").attr("title",newval4ArrStr);
                    }
                }else if(curOperation == 2){
                    if(uploadClassify=="IDCard"){
                        var val1 = $(".update_idcard_div .flex_2 input").val().trim();
                        var val1Arr = val1.split(";");
                        val1Arr = $.grep(val1Arr,function(currentValue,index){
                            return currentValue != "";
                        });
                        val1Arr.push(fileName_add);
                        var newval1Arr = globalArrStrUnique(val1Arr);
                        var newval1ArrStr = newval1Arr.join(";")+";";
                        $(".update_idcard_div .flex_2 input").val(newval1ArrStr);
                        $(".update_idcard_div .flex_2 input").attr("title",newval1ArrStr);
                    }else if(uploadClassify=="passport"){
                        var val2 = $(".update_passport_div .flex_2 input").val().trim();
                        var val2Arr = val2.split(";");
                        val2Arr = $.grep(val2Arr,function(currentValue,index){
                            return currentValue != "";
                        });
                        val2Arr.push(fileName_add);
                        var newval2Arr = globalArrStrUnique(val2Arr);
                        var newval2ArrStr = newval2Arr.join(";")+";";
                        $(".update_passport_div .flex_2 input").val(newval2ArrStr);
                        $(".update_passport_div .flex_2 input").attr("title",newval2ArrStr);
                    }
                }
                $("span.isUpload").text("上传成功！");
                setTimeout(function(){
                    $(".dropFileTit span").trigger("click");
                },1000);
            }else if(data.indexOf("文件上传失败")>-1){
                $("span.isUpload").text("上传失败！");
                $(".progressIn").css("width","30px");
                $(".progressIn").text("0%");
            }
            // var fileName_add = $("input.serFinRepUploadName").val();
            // if(data.indexOf("上传失败")>-1){
            //     $("span.isUpload").text("上传失败！");
            //     $(".progressIn").css("width","30px");
            //     $(".progressIn").text("0%");
            // }else if(data.indexOf("读取报价失败，请手输")>-1){
            //     if(IsFileUpload ==1){
            //         $(".add_NonStandard .info_FilePath").val(fileName_add);
            //         $(".add_NonStandard .info_FilePath").attr("title",fileName_add);
            //         $("span.isUpload").text("上传成功！");
            //         $.MsgBox_Unload.Alert("提示","读取报价失败，请手输价格");
            //         setTimeout(function(){
            //             $(".dropFileTit span").trigger("click");
            //         },1000);
            //     }else if(IsFileUpload==2){
            //         $(".update_NonStandard .info_FilePath").val(fileName_add);
            //         $(".update_NonStandard .info_FilePath").attr("title",fileName_add);
            //         $("span.isUpload").text("上传成功！");
            //         $.MsgBox_Unload.Alert("提示","读取报价失败，请手输价格");
            //         setTimeout(function(){
            //             $(".dropFileTit span").trigger("click");
            //         },1000);
            //     }else{
            //         $("span.isUpload").text("操作有误！");
            //     }
            // }else{
            //     if(IsFileUpload ==1){
            //         $(".add_NonStandard .info_FilePath").val(fileName_add);
            //         $(".add_NonStandard .info_FilePath").attr("title",fileName_add);
            //         $(".add_NonStandard .info_QuoteTotal").val(data);
            //         $("span.isUpload").text("上传成功！");
            //         setTimeout(function(){
            //             $(".dropFileTit span").trigger("click");
            //         },1000);
            //     }else if(IsFileUpload==2){
            //         $(".update_NonStandard .info_FilePath").val(fileName_add);
            //         $(".update_NonStandard .info_FilePath").attr("title",fileName_add);
            //         $(".update_NonStandard .info_QuoteTotal").val(data);
            //         $("span.isUpload").text("上传成功！");
            //         setTimeout(function(){
            //             $(".dropFileTit span").trigger("click");
            //         },1000);
            //     }else{
            //         $("span.isUpload").text("操作有误！");
            //     }
            // }
        },
        error:function(){
            $("span.isUpload").text("");
            $(".progressIn").css("width","30px");
            $(".progressIn").text("0%");
            $.MsgBox_Unload.Alert("上传提示","网络繁忙！上传失败！");
        }
    });                             
}

// 打开上传框
$(".flex_3 input:nth-child(1)").on("click",function(){
    $(".cover-color2, .dropFileBox").show();
    var typeVal = $(this).parent().parent().attr("class");
    if(typeVal.indexOf("idcard")>-1){
        $(".dropFileBox").attr("value","IDCard");
    }else if(typeVal.indexOf("passport")>-1){
        $(".dropFileBox").attr("value","passport");
    }
});

// 关闭上传框
$(".dropFileTit span").on("click",function(){
    $(".dropFileBox").hide();
    $(".cover-color2").hide();
    $("span.isUpload").text("");
    $(".progressIn").css("width","30px");
    $(".progressIn").text("0%");
    $("input.serFinRepUploadName").val("");
    $("input.serFinRepUploadName").attr("title","");
    $("#serFinRepUpload").val("");
});

// 点击浏览
$("#serFinRepUpload").on("change",function(){
    $("span.isUpload").text("");
    $(".progressIn").css("width","30px");
    $(".progressIn").text("0%");
    console.log("文件上传改变值"+$(this).val());
    var newFileName1 = $(this).val().indexOf("\\fakepath\\")>-1?$(this).val().split("\\fakepath\\")[1]:$(this).val().split("\\").pop();
    console.log("赋给input的值"+newFileName1);
    $("input.serFinRepUploadName").val(newFileName1);
    $("input.serFinRepUploadName").attr("title",newFileName1);
});

// 上传文件
$("input.dropUp2").on("click",function(){
    var judgeFile = $("input.serFinRepUploadName").val();
    var judgeFile2 = $("#serFinRepUpload").val();
    var filename1=judgeFile.replace(/.*(\/|\\)/, ""); 
    var filename2=judgeFile2.replace(/.*(\/|\\)/, ""); 
    var fileExt1=(/[.]/.exec(filename1)) ? /[^.]+$/.exec(filename1.toLowerCase()) : '';
    var fileExt2=(/[.]/.exec(filename2)) ? /[^.]+$/.exec(filename2.toLowerCase()) : '';
    // var iType = $(".dropFileBox").attr("value");
    // var iID = $(".contract_update .update_id").text();
    // console.log(judgeFile);
    // console.log(judgeFile2);
    // console.log(filename1);
    // console.log(filename2);
    if(fileExt1==''||fileExt2==''){
        $.MsgBox_Unload.Alert("上传提示","文件没有后缀名，请重新上传");
        return false;
    }
    if(judgeFile && judgeFile2){
        uploadFiles();
    }else{
        $.MsgBox_Unload.Alert("上传提示","请检查是否选择或更换了文件");
    }
});

// 更多信息
$("#more_info").on("click",function(){
    $(this).toggleClass("fa-plus-square").toggleClass("fa-minus-square");
    $(".td_IDCard, .td_Passport, .td_AccountNumber, .td_DepositBank, .td_reminded, .td_Remarks, .td_IDCardFile, .td_PassportFile").toggle();
    $(".tbody_tr").find(".IDCard, .Passport, .AccountNumber, .DepositBank, .reminded, .Remarks, .IDCardFile, .PassportFile").toggle();
});

