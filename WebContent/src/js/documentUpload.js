//选时间blur
$(".MailBar_All .upyear").blur(function(){
	var Year = $(".MailBar_All .upyear option:selected").val();
	$("#All_Send3").val(Year);
})

$(function(){
		if($("#catalog").text() == "Log"){
			$("#year_area").show();
			$(".InterSearch").hide();   //搜索框收起
			sessionStorage.removeItem("tab")  //清除软件文档 缓存
		}else{
			$("#year_area").hide();
		}
		if($("#catalog").text() == "Original"||$("#catalog").text()=="Sales"||$("#catalog").text()=="Image"){
			$("#area").hide();
			sessionStorage.removeItem("tab")  //清除软件文档 缓存
		}else{
			$("#area").show();
		}
		if($("#catalog").text()=="Software"){
			$("#area").hide();
			$(".changeBox").show();
			/*if(sessionStorage.getItem("tab")){
				var session = sessionStorage.getItem("tab");
				$("."+session).click();
			}*/
		}else{
			$("#area").show();
			sessionStorage.removeItem("tab")  //清除软件文档 缓存
		}
		//页面加载 a  href 
		var catalog = $("#catalog").text();
		$(".south a").attr("href","DocumentUpload?Area=south&catalog="+catalog+"&Year=2018&Type=Cascade");
		$(".north a").attr("href","DocumentUpload?Area=north&catalog="+catalog+"&Year=2018&Type=Cascade");
		$(".southwest a").attr("href","DocumentUpload?Area=southwest&catalog="+catalog+"&Year=2018&Type=Cascade");
		jumpDisable();
})

//装机实施手册相关页面
$('.MV_CFCs').click(function(){
	$('#show_MV_CFCs').show();
})

//装机报告和装机日志
$('.MV_report_daily').click(function(){
	$('#show_MV_CFCs').show();
})

//装机图片
$('.MV_picture').click(function(){
	$('#show_MV_CFCs').show();
})

//服务请求和服务完成
$('.serve').click(function(){
	$('#show_MV_CFCs').show();
})

//EUCP软件实施管理文档
$('.software_actualize').click(function(){
	$('#show_MV_CFCs').show();
})

//点击南方那页，字体颜色改变，离开后字体还原
/*
$('ul').on('click','a',function(){$(this).addClass('current').siblings('a').removeClass('current')})
*/
/*$('ul').on('click','a',function(e){
	e.preventDefault()
	$(this).addClass('current').parent().siblings().find('a').removeClass('current')
})*/

//点击上传文件
	$(".writeEmail").click(function(){
		var myDate = new Date();
		var Y = myDate.getFullYear();
		var date = Y ;
		$(".MailBar .upyear option[value="+ date +"]").attr("selected",true);
		if($("#catalog").text() == "Software"){
			$(".PwdCont").show();
			$(".PwdInput").val("");
			if($("#currentTab").attr("class")=="CommonProblem"){
				$(".NameFormat").show();
			}
			else{
				$(".NameFormat").hide();
			}
		}
		$(".MailBar").show()
		$(".MailBar_cover_color").show();
		$(".MailBar_cover_color").css("height",$(document).height()+10);
		$(".MailBar_All").hide()
		$("#Mail_Send").attr("disabled",false);
		$("#Mail_Send").removeClass("isClick").addClass("bToggle");
	})
	
//点击批量上传文件
	$(".All_upload").click(function(){
		var myDate = new Date();
		var Y = myDate.getFullYear();
		var date = Y ;
		$(".MailBar_All .upyear option[value="+ date +"]").attr("selected",true);
		$(".MailBar_All").show()
		$(".MailBar_cover_color").show();
		$(".MailBar_cover_color").css("height",$(document).height()+10);
		$(".MailBar").hide();
		$("#All_Send").attr("disabled",false);
		$("#All_Send").removeClass("isClick").addClass("bToggle");
		if($("#catalog").text() == "Software"){
			$(".PwdCont").show();
			$(".PwdInput").val("");
			if($("#currentTab").attr("class")=="CommonProblem"){
				$(".NameFormat").show();
			}
			else{
				$(".NameFormat").hide();
			}
		}
	})
	
	$(".MailBar_close").click(function(){
		$(".MailBar_cover_color").hide();
		$(".MailBar").hide();
		$(".MailBar_All").hide()
	})

 //选择文件之后执行上传  
	/*$('#Mail_Send').on('click', function() {
		if($("#catalog").text() == "VisitReport"){
			var Year  = $(".upyear option:selected").val();
			var upload = 'only';
			$.ajaxFileUpload({  
                url:'SoftwareVisitReportOperate',
                secureuri:false,  
                fileElementId:'Mail_fileToUpload',//file标签的id  
                dataType: 'JSON',//返回数据的类型  
                data:{
                	Year: Year,
                	upload : upload
                },//一同上传的数据  
                success: function (data) {
               		$.MsgBox.Alert("提示", "上传成功！");	
               		$(".MailBar").hide();
               		$(".MailBar_cover_color").hide();
                },  
                error: function (e) {  
                    alert("error");  
                }  
            }); 
		}
	})*/
    $('#Mail_Send').on('click', function() {
    	$(this).attr("disabled",true);
    	$("#Mail_Send").removeClass("bToggle").addClass("isClick");
    	console.log($("#Mail_fileToUpload").val());
    	var catalog;
    	var Area;
    	var Year;
    	var Type;
    	var v1 = $(".MailBar #Mail_fileToUpload").val();
		if(v1 == ''){
			$.MsgBox_Unload.Alert("提示", "请检查是否选择文件！");
			$(this).attr("disabled",false);
			$("#Mail_Send").removeClass("isClick").addClass("bToggle");
			return;
		}
    	// debugger;
    	if($("#catalog").text() == "Manual"){
    		//装机实施手册：
    		catalog = "Manual";
	        	if($("#ManualArea").text() == "north"){
	        		Area = "north";
	        	}
	        	else if($("#ManualArea").text() == "south"){
	        		Area = "south";
	        	}
	        	else{
	        		Area = "southwest";
	        	}
		        	$.ajaxFileUpload({  
	                    url:'ManualUpload',
	                    secureuri:false,  
	                    fileElementId:'Mail_fileToUpload',//file标签的id  
	                    dataType: 'JSON',//返回数据的类型  
	                    data:{
	                    	Area: Area,
                            Type: "InstallationManual"
                            // type: "InstallationManual"
	                    },//一同上传的数据  
	                    success: function (data) {
	                    	if(data){
	                    		$.MsgBox.Alert("提示", "上传成功！");	
		                   		$(".MailBar").hide();
		                   		$(".MailBar_cover_color").hide();
	                    	}else{
	                    		$.MsgBox.Alert("提示", "上传失败！");	
	                    	}
	                    	$(this).attr("disabled",false);
                            $("#Mail_Send").removeClass("isClick").addClass("bToggle");
	                    },  
	                    error: function (e) {  
	                    	$.MsgBox.Alert("提示", "网络错误！"); 
	                    	$(this).attr("disabled",false);
                            $("#Mail_Send").removeClass("isClick").addClass("bToggle");
	                    }  
	                }); 
	        	
    		
    	}else if($("#catalog").text() == "Log"){
    		//装机报告和装机日志:
    		catalog = "Log";
        	if($("#ManualArea").text() == "north"){
        		//装机报告和装机日志北方区域:
        		Area = "north";
        		
	            	if($("#ManualAreaYear").text() == "2014"){
	            		Year = "2014";
	            	}
	            	else if($("#ManualAreaYear").text() == "2015"){
	            		Year = "2015";
	            	}
	            	else if($("#ManualAreaYear").text() == "2016"){
	            		Year = "2016";
	            		
	            	}
	            	else if($("#ManualAreaYear").text() == "2017"){
	            		Year = "2017";
	            		
	            	}
	            	else{
	            		Year = "2018";
	            	}
	            	  
        	}else if($("#ManualArea").text() == "south"){
        		//装机报告和装机日志南方区域:
        		console.log("in");
        		Area = "south";
	            	if($("#ManualAreaYear").text() == "2014"){
	            		Year = "2014";
	            	}
	            	else if($("#ManualAreaYear").text() == "2015"){
	            		Year = "2015";
	            	}
	            	else if($("#ManualAreaYear").text() == "2016"){
	            		Year = "2016";
	            		
	            	}
	            	else if($("#ManualAreaYear").text() == "2017"){
	            		Year = "2017";
	            		
	            	}
	            	else{
	            		Year = "2018";
	            	}
        	}else{
        		//装机报告和装机日志西南方区域:
        		Area = "southwest";
        			if($("#ManualAreaYear").text() == "2014"){
                		Year = "2014";
                	}
                	else if($("#ManualAreaYear").text() == "2015"){
                		Year = "2015";
                	}
                	else if($("#ManualAreaYear").text() == "2016"){
                		Year = "2016";
                		
                	}
                	else if($("#ManualAreaYear").text() == "2017"){
                		Year = "2017";
                		
                	}
                	else{
                		Year = "2018";
                	}
        	}
        
	        	 $.ajaxFileUpload({  
	                 url:'ReportLogUpload',
	                 secureuri:false,  
	                 fileElementId:'Mail_fileToUpload',//file标签的id  
	                 dataType: 'JSON',//返回数据的类型  
	                 data:{
	                 	catalog :catalog,
	                 	Area: Area,
	                 	Year : Year,
	                 },//一同上传的数据  
	                 success: function (data) {
	                	 if(data){
	                			$.MsgBox.Alert("提示", "上传成功！");	
		                		$(".MailBar").hide();
		                		$(".MailBar_cover_color").hide();
	                	 }else{
	                			$.MsgBox.Alert("提示", "上传失败！");	
	                	 }
	                	 $(this).attr("disabled",false);
	                	 $("#Mail_Send").removeClass("isClick").addClass("bToggle");
	                 },  
	                 error: function (e) {  
	                	 $.MsgBox.Alert("提示", "网络错误！");	
	                	 $(this).attr("disabled",false);
	                	 $("#Mail_Send").removeClass("isClick").addClass("bToggle");
	                 }  
	             });  
    	}else if($("#catalog").text() == "Image"){
    		//装机图片
    		catalog = "Image";
	        	$.ajaxFileUpload({  
                    url:'InstallationImageUpload',
                    secureuri:false,  
                    fileElementId:'Mail_fileToUpload',//file标签的id  
                    dataType: 'JSON',//返回数据的类型  
                    data:{
                    	catalog :catalog,
                    },//一同上传的数据  
                    success: function (data) {
                    	if(data){
                       		$.MsgBox.Alert("提示", "上传成功！");	
                       		$(".MailBar").hide();
                       		$(".MailBar_cover_color").hide();
                    	}else{
                    		$.MsgBox.Alert("提示", "上传失败！");	
                    	}
                    	 $(this).attr("disabled",false);
                    	 $("#Mail_Send").removeClass("isClick").addClass("bToggle");
                    },  
                    error: function (e) {  
                    	$.MsgBox.Alert("提示", "网络错误！");	 
                    	 $(this).attr("disabled",false);
                    	 $("#Mail_Send").removeClass("isClick").addClass("bToggle");
                    }  
                }); 
    	}else if($("#catalog").text() == "Environment"){
            catalog = "Manual";
                if($("#ManualArea").text() == "north"){
                    Area = "north";
                }
                else if($("#ManualArea").text() == "south"){
                    Area = "south";
                }
                else{
                    Area = "southwest";
                }
                    $.ajaxFileUpload({  
                        url:'ManualUpload',
                        secureuri:false,  
                        fileElementId:'Mail_fileToUpload',//file标签的id  
                        dataType: 'JSON',//返回数据的类型  
                        data:{
                            Area: Area,
                            Type: "InstallationEnvironment"
                            // type: "Environment"
                        },//一同上传的数据  
                        success: function (data) {
                            if(data){
                                $.MsgBox.Alert("提示", "上传成功！");  
                                $(".MailBar").hide();
                                $(".MailBar_cover_color").hide();
                            }else{
                                $.MsgBox.Alert("提示", "上传失败！");  
                            }
                            $(this).attr("disabled",false);
                            $("#Mail_Send").removeClass("isClick").addClass("bToggle");
                        },  
                        error: function (e) {  
                            $.MsgBox.Alert("提示", "网络错误！"); 
                            $(this).attr("disabled",false);
                            $("#Mail_Send").removeClass("isClick").addClass("bToggle");
                        }  
                    });  
        }else if($("#catalog").text() == "Achieve"){
    		//服务请求和服务完成
    		catalog = "Achieve";
        	if($("#ManualArea").text() == "north"){
        		Area = "north";
        	}
        	else if($("#ManualArea").text() == "south"){
        		Area = "south";
        	}
        	else{
        		Area = "southwest";
        	}
	        	$.ajaxFileUpload({  
                    url:'RequestAchieveUpload',
                    secureuri:false,  
                    fileElementId:'Mail_fileToUpload',//file标签的id  
                    dataType: 'JSON',//返回数据的类型  
                    data:{
                    	catalog :catalog,
                    	Area: Area,
                    },//一同上传的数据  
                    success: function (data) {
                    	if(data){
                       		$.MsgBox.Alert("提示", "上传成功！");	
                       		$(".MailBar").hide();
                       		$(".MailBar_cover_color").hide();
                    	}else{
                    		$.MsgBox.Alert("提示", "上传失败！");	
                    	}
                    	 $(this).attr("disabled",false);
                    	 $("#Mail_Send").removeClass("isClick").addClass("bToggle");
                    },  
                    error: function (e) {  
                    	$.MsgBox.Alert("提示", "网络错误！");	
                    	 $(this).attr("disabled",false);
                    	 $("#Mail_Send").removeClass("isClick").addClass("bToggle");
                    }  
                }); 
	        	
    	      
    	}else if($("#catalog").text() == "Original"){
    		//原厂文档资料
    		catalog = "Original";
        	if($("#ManualAreaType").text() == "Cascade"){
        		Type = "Cascade";
        		
        	}else if($("#ManualAreaType").text() == "Keysight"){
        		Type = "Keysight";
        		
        	}else  if($("#ManualAreaType").text() == "EOULU"){
        		Type = "EOULU";
        		 
        	}else  if($("#ManualAreaType").text() == "Maury"){
        		Type = "Maury";
        		
        	}else{
        		Type = "Other";
        	}
	        	$.ajaxFileUpload({  
                    url:'OriginalDocumentUpload',
                    secureuri:false,  
                    fileElementId:'Mail_fileToUpload',//file标签的id  
                    dataType: 'JSON',//返回数据的类型  
                    data:{
                    	catalog :catalog,
                    	Type: Type,
                    },//一同上传的数据  
                    success: function (data) {
                    	if(data){
                       		$.MsgBox.Alert("提示", "上传成功！");	
                       		$(".MailBar").hide();
                       		$(".MailBar_cover_color").hide();
                    	}else{
                    		$.MsgBox.Alert("提示", "上传失败！");	
                    	}
                    	 $(this).attr("disabled",false);
                    	 $("#Mail_Send").removeClass("isClick").addClass("bToggle");
                    },  
                    error: function (e) {  
                    	$.MsgBox.Alert("提示", "网络错误！");	 
                    	 $(this).attr("disabled",false);
                    	 $("#Mail_Send").removeClass("isClick").addClass("bToggle");
                    }  
                }); 
	        	
    	      
    	}else if($("#catalog").text() == "Sales"){
    		//EOULU销售人员表格
    		catalog = "Sales";
    		Type ="Sales";
	        	$.ajaxFileUpload({  
                    url:'OriginalDocumentUpload',
                    secureuri:false,  
                    fileElementId:'Mail_fileToUpload',//file标签的id  
                    dataType: 'JSON',//返回数据的类型  
                    data:{
                    	catalog :catalog,
                    	Type: Type,
                    },//一同上传的数据  
                    success: function (data) {
                    	if(data){
                       		$.MsgBox.Alert("提示", "上传成功！");	
                       		$(".MailBar").hide();
                       		$(".MailBar_cover_color").hide();
                    	}else{
                    		$.MsgBox.Alert("提示", "上传失败！");	
                    	}
                    	 $(this).attr("disabled",false);
                    	 $("#Mail_Send").removeClass("isClick").addClass("bToggle");
                    },  
                    error: function (e) {  
                    	$.MsgBox.Alert("提示", "网络错误！");	
                    	 $(this).attr("disabled",false);
                    	 $("#Mail_Send").removeClass("isClick").addClass("bToggle");
                    } 
                }); 
    	
    	}else if($("#catalog").text() == "Software"){
    		var flag = true;
    		var FileName = $(".MailBar #Mail_fileToUpload").val();
    		var Password = $(".MailBar .PwdInput").val();
    		console.log(FileName);
    		console.log(Password);
    		if($("#currentTab").attr("class")=="Norms"){  //开发规范
    			var url = "SoftwareSpecificationUpload";
    			var data={
    					FileName:FileName,
    					Password:Password,
    			}
    		}
    		else if($("#currentTab").attr("class")=="CommonProblem"){  //问题
    			var url = "CommonProblemUpload";
    			if(FileName.split("\\")[2].split("-").length !=3){   //判断上传文件名 格式
    				flag = false;
    				$.MsgBox_Unload.Alert("提示", "文件名格式错误!",function(){ $("#Mail_Send").attr("disabled",false);$("#Mail_Send").removeClass("isClick").addClass("bToggle");});	
    			}
    			else{
    				flag = true;
    			}
    			var data={
    					FileName:FileName,
    					Password:Password,
    			}
    		}
    		else{
    			var url = "SoftwareDepartmentUpload";
    			var data={
    					FileName:FileName,
    					Password:Password,
    			}
    		}
    		//软件管理
    		catalog = "Software";
    		Type ="Software";
    		if(flag){
    			$.ajaxFileUpload({  
                    url:url,
                    secureuri:false,  
                    fileElementId:'Mail_fileToUpload',//file标签的id  
                    dataType : 'text',//返回数据的类型  
                    data:data,//一同上传的数据  
                    success: function (data) {
                    	var ClassName = $("#currentTab").attr("class");
                   		$.MsgBox_Unload.Alert("提示",data,Load(ClassName));	
                   		$(".MailBar").hide();
                   		$(".MailBar_cover_color").hide();
                   		$(this).attr("disabled",false);
                   		$("#Mail_Send").removeClass("isClick").addClass("bToggle");
                    },  
                    error: function (e) {  
                    	$.MsgBox_Unload.Alert("提示","上传失败！");	
                        $(this).attr("disabled",false);
                        $("#Mail_Send").removeClass("isClick").addClass("bToggle");
                    }  
                });
    		}
	       	 
    	}else if($("#catalog").text() == "VisitReport"){
    		//拜访报告
    		var Year  = $(".MailBar .upyear option:selected").val();
			var upload = 'only';
			var v1 = $(".MailBar #Mail_fileToUpload").val();
			if(v1 == ''){
				$.MsgBox_Unload.Alert("提示", "请检查是否选择文件！");
				 $(this).attr("disabled",false);
				 $("#Mail_Send").removeClass("isClick").addClass("bToggle");
				return;
			}
			$.ajaxFileUpload({  
                url:'SoftwareVisitReportOperate',
                secureuri:false,  
                fileElementId:'Mail_fileToUpload',//file标签的id  
                dataType: 'JSON',//返回数据的类型  
                data:{
                	Year: Year,
                	upload : upload
                },//一同上传的数据  
                success: function (data) {
                	if(data){
                   		$.MsgBox.Alert("提示", "上传成功！");	
                   		$(".MailBar").hide();
                   		$(".MailBar_cover_color").hide();
                	}else{
                		$.MsgBox.Alert("提示", "上传失败请稍候再试！");
                	}
                	 $(this).attr("disabled",false);
                	 $("#Mail_Send").removeClass("isClick").addClass("bToggle");
                },  
                error: function (e) {  
                    alert("error");  
                    $(this).attr("disabled",false);
                    $("#Mail_Send").removeClass("isClick").addClass("bToggle");
                }  
            }); 
		}
    });  


//-------------------------------------下载装机实施手册-----------------------------------------------------
$(document).on("click",".export",function () {
	/*alert(5555)*/
	var tr=$(this).parent().parent();
	var ID = tr.find('td').eq(0).attr("value");
	console.log(ID);
	if(($("#catalog").text() == "Manual")){
		$.ajax({
	        type: 'get',
	        url: 'ManualDownload',
	        data: {
	            ID :ID,
	        },
	        success: function (data) {
	        	console.log(data);
	        	window.location.assign(data)
	        },
	        error: function () {
	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    });
	}else if(($("#catalog").text() == "Log")){
		//下载装机报告和装机日志:
		$.ajax({
	        type: 'get',
	        url: 'ReportLogDownload',
	        data: {
	            ID :ID,
	        },
	        success: function (data) {
	        	console.log(data);
	        	window.location.href=data;
	        },
	        error: function () {
	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    });
	}else if(($("#catalog").text() == "Image")){
		//下载装机图片
		$.ajax({
	        type: 'get',
	        url: 'InstallationImageDownload',
	        data: {
	            ID :ID,
	        },
	        success: function (data) {
	        	console.log(data);
	        	window.location.href=data;
	        },
	        error: function () {
	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    });
	}else if($("#catalog").text() == "Environment"){
        $.ajax({
            type: 'get',
            url: 'ManualDownload',
            data: {
                ID :ID,
            },
            success: function (data) {
                console.log(data);
                window.location.assign(data);
            },
            error: function () {
                $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
            }
        });
    }else if(($("#catalog").text() == "Achieve")){
		//下载服务请求和服务完成
		$.ajax({
	        type: 'get',
	        url: 'RequestAchieveDownload',
	        data: {
	            ID :ID,
	        },
	        success: function (data) {
	        	console.log(data);
	        	window.location.href=data;
	        },
	        error: function () {
	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    });
	}else if(($("#catalog").text() == "Original"||$("#catalog").text() == "Sales")){
		//下载原厂文档资料和EOULU销售人员表格
		$.ajax({
	        type: 'get',
	        url: 'OriginalDocumentDownload',
	        data: {
	            ID :ID,
	        },
	        success: function (data) {
	        	console.log(data);
	        	window.location.href=data;
	        },
	        error: function () {
	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    });
	}else if(($("#catalog").text() == "Software")){
		//下载软件管理
		$.ajax({
	        type: 'get',
	        url: 'SoftwareDepartmentDownload',
	        data: {
	            ID :ID,
	        },
	        success: function (data) {
	        	console.log(data);
	        	window.location.href=data;
	        },
	        error: function () {
	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    });
	}else if(($("#catalog").text() == "VisitReport")){
		//下载软件管理
		$.ajax({
	        type: 'get',
	        url: 'SoftwareVisitReportDownload',
	        data: {
	            ID :ID,
	        },
	        success: function (data) {
	        	console.log(data);
	        	window.location.href=data;
	        },
	        error: function () {
	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    });
	}else if($("#catalog").text() == "InstallationPackage"){
        var body12 = $(document.body),
            form12 = $("<form method='get'><input name='ID' value='"+ID+"'></form>");
        form12.attr({"action":"InstallationPackageDownload"});
        form12.appendTo(document.body);
        form12.submit();
        document.body.removeChild(form12[0]);
    }
});

//*************************装机实施手册区域跳转****************
$(function(){
		if($("#ManualArea").text() == "north"){
			$(".north a").addClass('current').parent().siblings().find("a").removeClass('current');
		}
		else if($("#ManualArea").text() == "south"){
			$(".south a").addClass('current').parent().siblings().find("a").removeClass('current');
		}
		else{
			$(".southwest a").addClass('current').parent().siblings().find("a").removeClass('current');
		}
})

//*************************装机报告和装机日志年份跳转****************
$(function(){
		if($("#ManualAreaYear").text() == "2014"){
			$(".year2014 a").addClass('current').parent().siblings().find("a").removeClass('current');
		}
		else if($("#ManualAreaYear").text() == "2015"){
			$(".year2015 a").addClass('current').parent().siblings().find("a").removeClass('current');
		}
		else if($("#ManualAreaYear").text() == "2016"){
			$(".year2016 a").addClass('current').parent().siblings().find("a").removeClass('current');
		}
		else if($("#ManualAreaYear").text() == "2017"){
			$(".year2017 a").addClass('current').parent().siblings().find("a").removeClass('current');
		}
		else{
			$(".year2018 a").addClass('current').parent().siblings().find("a").removeClass('current');
		}
	
})

//搜索
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
   /* $('input[name="searchContent2"]').val('');*/
    $('#top_text_from').submit();
}
$('#searchContent1').keypress(function (event) {
    $('#search').val('search');
    var keynum = (event.keyCode ? event.keyCode : event.which);
    if (keynum == '13') {
        $('#top_text_from').submit();
    }
});

/*$('.south').click(function () {
$(this).find("a").addClass('current').parent().siblings().find("a").removeClass('current');
//$('.south_area').show();
//$('.north_area').hide();
//$('.southwest_area').hide();
});
//显示北方信息
$('.north').click(function () {
$(this).find("a").addClass('current').parent().siblings().find("a").removeClass('current');
//	$('.south_area').hide();
//$('.north_area').show();
//$('.southwest_area').hide();

});
//显示西北方信息
$('.southwest').click(function () {
$(this).find("a").addClass('current').parent().siblings().find("a").removeClass('current');
//$('.south_area').hide();
//$('.north_area').hide();
//$('.southwest_area').show();

});*/

//点南方的上传
/*$(function () {
document.getElementById('add-file').onchange=function(){
 console.log(document.getElementById('add-file').files[0])
 console.log(document.getElementById('add-file').files[0]['name'])
 var file_name = document.getElementById('add-file').files[0]['name'];
 var _html ='<li><a id="file-lists">'+file_name+'</a>'+
 '<input type="button" value="下载" class="bToggle" id="download_submit"></li>';
 $('#file-lists').append(_html);
}
})*/
//实现批量上传压缩包
$('#All_Send').on('click', function() {
	$("#All_Send").attr("disabled",true);
	$("#All_Send").removeClass("bToggle").addClass("isClick");
	var v2 = $(".MailBar_All  #Mail_fileToUpload2").val();
		if(v2 == ''){
			$.MsgBox_Unload.Alert("提示", "请检查是否选择文件！");
			$("#All_Send").attr("disabled",false);
			$("#All_Send").removeClass("isClick").addClass("bToggle");
			return;
		}
		console.log(v2)
    var form = new FormData(document.getElementById("f"));
	var catalog;
	var Area;
	var Year;
	var Type;
	if($("#catalog").text() == "Manual"){
		//装机实施手册：
		catalog = "Manual";
        	if($("#ManualArea").text() == "north"){
        		Area = "north";
        	}
        	else if($("#ManualArea").text() == "south"){
        		Area = "south";
        	}
        	else{
        		Area = "southwest";
        	}
             $("#All_Send2").val(Area);
             $("#All_Send4").val("InstallationManual");
             $("#All_Send6").val("InstallationManual");
             var form_1 = new FormData(document.getElementById("f"));
	         // $("#All_Send6").val("InstallationManual");
	         	 $.ajax({
                     // url:"/Logistics/ManualBatchUpload",
	                 url:"/cfChicken8/ManualBatchUpload",
	                 type:"post",
	                 data:form_1,
	                 processData:false,
	                 contentType:false,
	                 success:function(data){
	                     $.MsgBox.Alert("提示", "上传成功！");	
	                     $("#All_Send").attr("disabled",false);
	                     $("#All_Send").removeClass("isClick").addClass("bToggle");
	                     $(".MailBar_All").hide();
	                     $(".MailBar_cover_color").hide();
	                     window.clearInterval();
	                 },
	                 error:function(e){
	                     alert("error");
	                     $("#All_Send").attr("disabled",false);
	                     $("#All_Send").removeClass("isClick").addClass("bToggle");
	                     window.clearInterval();
	                 }
	             });  
	}else if($("#catalog").text() == "Log"){
		//装机报告和装机日志:
		catalog = "Log";
		 console.log(catalog);
         	console.log(Area);
         	console.log(Year);
         	console.log(Type);
    	if($("#ManualArea").text() == "north"){
    		//装机报告和装机日志北方区域:
    		Area = "north";
            	if($("#ManualAreaYear").text() == "2014"){
            		Year = "2014";
            	}
            	else if($("#ManualAreaYear").text() == "2015"){
            		Year = "2015";
            	}
            	else if($("#ManualAreaYear").text() == "2016"){
            		Year = "2016";
            	}
            	else if($("#ManualAreaYear").text() == "2017"){
            		Year = "2017";
            	}else{
            		Year = "2018";
            	}
            	  
    	}else if($("#ManualArea").text() == "south"){
    		//装机报告和装机日志南方区域:
    		console.log("in");
    		Area = "south";
    		 console.log(catalog);
	         	console.log(Area);
	         	console.log(Year);
	         	console.log(Type);
            	if($("#ManualAreaYear").text() == "2014"){
            		Year = "2014";
            	}
            	else if($("#ManualAreaYear").text() == "2015"){
            		Year = "2015";
            	}
            	else if($("#ManualAreaYear").text() == "2016"){
            		Year = "2016";
            	}
            	else if($("#ManualAreaYear").text() == "2017"){
            		Year = "2017";
            	}else{
            		Year = "2018";
            	}
    	}else{
    		//装机报告和装机日志西南方区域:
    		Area = "southwest";
    		 console.log(catalog);
	         	console.log(Area);
	         	console.log(Year);
	         	console.log(Type);
    			if($("#ManualAreaYear").text() == "2014"){
            		Year = "2014";
            	}
            	else if($("#ManualAreaYear").text() == "2015"){
            		Year = "2015";
            	}
            	else if($("#ManualAreaYear").text() == "2016"){
            		Year = "2016";
            	}
            	else if($("#ManualAreaYear").text() == "2017"){
            		Year = "2017";
            	}else{
            		Year = "2018";
            	}
    	}
    	$("#All_Send2").val(Area);
    	$("#All_Send3").val(Year);
    	 $.ajax({
             // url:"/Logistics/ReportLogBatchUpload",
             url:"/cfChicken8/ReportLogBatchUpload",
             type:"post",
             data:form,
             processData:false,
             contentType:false,
             success:function(data){
                 $.MsgBox.Alert("提示", "上传成功！");	
                 $("#All_Send").attr("disabled",false);
                 $("#All_Send").removeClass("isClick").addClass("bToggle");
                 $(".MailBar_All").hide();
                 $(".MailBar_cover_color").hide();
                 window.clearInterval();
             },
             error:function(e){
                 alert("error");
                 $("#All_Send").attr("disabled",false);
                 $("#All_Send").removeClass("isClick").addClass("bToggle");
                 window.clearInterval();
             }
         });  
	}else if($("#catalog").text() == "Image"){
		//装机图片
		catalog = "Image";
         	$("#All_Send2").val(Area);
        	$("#All_Send3").val(Year);
        	$("#All_Send4").val(Type);
         	$.ajax({
                // url:"/Logistics/InstallationImageBatchUpload",
                url:"/cfChicken8/InstallationImageBatchUpload",
                type:"post",
                data:form,
                processData:false,
                contentType:false,
                success:function(data){
                    $.MsgBox.Alert("提示", "上传成功！");
                    $("#All_Send").attr("disabled",false);
                    $("#All_Send").removeClass("isClick").addClass("bToggle");
                    $(".MailBar_All").hide();
                    $(".MailBar_cover_color").hide();
                    window.clearInterval();
                    console.log("over");
                },
                error:function(e){
                    alert("error");
                    $("#All_Send").attr("disabled",false);
                    $("#All_Send").removeClass("isClick").addClass("bToggle");
                    window.clearInterval();
                }
            });  
       	
	}else if($("#catalog").text() == "Environment"){
        catalog = "Environment";
            if($("#ManualArea").text() == "north"){
                Area = "north";
            }
            else if($("#ManualArea").text() == "south"){
                Area = "south";
            }
            else{
                Area = "southwest";
            }
             $("#All_Send2").val(Area);
             $("#All_Send4").val("InstallationEnvironment");
             $("#All_Send6").val("InstallationEnvironment");
             var form_0 = new FormData(document.getElementById("f"));
             console.log(form_0);
             // $("#All_Send6").val("Environment");
                 $.ajax({
                     // url:"/Logistics/ManualBatchUpload",
                     url:"/cfChicken8/ManualBatchUpload",
                     type:"post",
                     data:form_0,
                     processData:false,
                     contentType:false,
                     success:function(data){
                         $.MsgBox.Alert("提示", "上传成功！"); 
                         $("#All_Send").attr("disabled",false);
                         $("#All_Send").removeClass("isClick").addClass("bToggle");
                         $(".MailBar_All").hide();
                         $(".MailBar_cover_color").hide();
                         window.clearInterval();
                     },
                     error:function(e){
                         alert("error");
                         $("#All_Send").attr("disabled",false);
                         $("#All_Send").removeClass("isClick").addClass("bToggle");
                         window.clearInterval();
                     }
                 });  
    }else if($("#catalog").text() == "Achieve"){
		//服务请求和服务完成
		catalog = "Achieve";
    	if($("#ManualArea").text() == "north"){
    		Area = "north";
    	}
    	else if($("#ManualArea").text() == "south"){
    		Area = "south";
    	}
    	else{
    		Area = "southwest";
    	}
         	$("#All_Send2").val(Area);
        	$("#All_Send3").val(Year);
        	$("#All_Send4").val(Type);
         	 $.ajax({
                 // url:"/Logistics/RequestAchieveBatchUpload",
                 url:"/cfChicken8/RequestAchieveBatchUpload",
                 type:"post",
                 data:form,
                 processData:false,
                 contentType:false,
                 success:function(data){
                     $.MsgBox.Alert("提示", "上传成功！");	
                     $("#All_Send").attr("disabled",false);
                     $("#All_Send").removeClass("isClick").addClass("bToggle");
                     $(".MailBar_All").hide();
                     $(".MailBar_cover_color").hide();
                     window.clearInterval();
                     console.log("over");
                 },
                 error:function(e){
                     alert("error");
                     $("#All_Send").attr("disabled",false);
                     $("#All_Send").removeClass("isClick").addClass("bToggle");
                     window.clearInterval();
                 }
             });  
	}else if($("#catalog").text() == "Original"){
		//原厂文档资料
		catalog = "Original";
    	if($("#ManualAreaType").text() == "Cascade"){
    		Type = "Cascade";
    	}else if($("#ManualAreaType").text() == "Keysight"){
    		Type = "Keysight";
    	}else  if($("#ManualAreaType").text() == "EOULU"){
    		Type = "EOULU";
    	}else  if($("#ManualAreaType").text() == "Maury"){
    		Type = "Maury";
    	}else{
    		Type = "Other";
    	}
    	 console.log(catalog);
         	console.log(Area);
         	console.log(Year);
         	console.log(Type);
         	$("#All_Send2").val(Area);
        	$("#All_Send3").val(Year);
        	$("#All_Send4").val(Type);
         	 $.ajax({
                 // url:"/Logistics/OriginalDocumentBatchUpload",
                 url:"/cfChicken8/OriginalDocumentBatchUpload",
                 type:"post",
                 data:form,
                 processData:false,
                 contentType:false,
                 success:function(data){
                     $.MsgBox.Alert("提示", "上传成功！");	
                     $("#All_Send").attr("disabled",false);
                     $("#All_Send").removeClass("isClick").addClass("bToggle");
                     $(".MailBar_All").hide();
                     $(".MailBar_cover_color").hide();
                     window.clearInterval();
                     console.log("over");
                 },
                 error:function(e){
                     alert("error");
                     $("#All_Send").attr("disabled",false);
                     $("#All_Send").removeClass("isClick").addClass("bToggle");
                     window.clearInterval();
                 }
             });  
	      
	}else if($("#catalog").text() == "Sales"){
		//EOULU销售人员表格
		catalog = "Sales";
		Type ="Sales";
		 console.log(catalog);
         	console.log(Area);
         	console.log(Year);
         	console.log(Type);
         	$("#All_Send2").val(Area);
        	$("#All_Send3").val(Year);
        	$("#All_Send4").val(Type);
         	 $.ajax({
                 // url:"/Logistics/OriginalDocumentBatchUpload",
                 url:"/cfChicken8/OriginalDocumentBatchUpload",
                 type:"post",
                 data:form,
                 processData:false,
                 contentType:false,
                 success:function(data){
                     $.MsgBox.Alert("提示", "上传成功！");	
                     $("#All_Send").attr("disabled",false);
                     $("#All_Send").removeClass("isClick").addClass("bToggle");
                     $(".MailBar_All").hide();
                     $(".MailBar_cover_color").hide();
                     window.clearInterval();
                     console.log("over");
                 },
                 error:function(e){
                     alert("error");
                     $("#All_Send").attr("disabled",false);
                     $("#All_Send").removeClass("isClick").addClass("bToggle");
                     window.clearInterval();
                 }
             });  
	}else if($("#catalog").text() == "Software"){
		//软件管理
		catalog = "Software";
		Type ="Software";
		 console.log('11111111'+catalog);
         	$("#All_Send2").val(Area);
        	$("#All_Send3").val(Year);
        	$("#All_Send4").val(Type);
    		var Password = $(".MailBar_All .PwdInput").val();
    		console.log(Password);
    		if($("#currentTab").attr("class") =="Norms"){  //开发规范
    			var url = "SoftwareSpecificationBatchUpload";
    			var dataType = "TEXT";
    		}
    		else if($("#currentTab").attr("class")=="CommonProblem"){  //问题
    			var url = "CommonProblemBatchUpload";
    			var dataType = "JSON";
    		}
    		else{
    			var url = "SoftwareDepartmentBatchUpload";
    			var dataType = "JSON";
    		}
    		form.append("Password", Password);
    		console.log(form)
    		$.ajax({
              url:url,
              type:"post",
              data:form,
              processData:false,
              contentType:false,
              dataType : dataType,
              success:function(data){
            	var ClassName = $("#currentTab").attr("class");
                $.MsgBox_Unload.Alert("提示",data,Load(ClassName));
                $("#All_Send").attr("disabled",false);
                $("#All_Send").removeClass("isClick").addClass("bToggle");
                  $(".MailBar_All").hide();
                  $(".MailBar_cover_color").hide();
                  window.clearInterval();
                 
              },
              error:function(e){
                  alert("error");
                  $("#All_Send").attr("disabled",false);
                  $("#All_Send").removeClass("isClick").addClass("bToggle");
                  window.clearInterval();
              }
          });  
	}else if($("#catalog").text() == "VisitReport"){
		//批量上传
		catalog = "VisitReport";
		Type ="VisitReport";
		var v2 = $(".MailBar_All  #Mail_fileToUpload").val();
		if(v2 == ''){
			$.MsgBox_Unload.Alert("提示", "请检查是否选择文件！");
			$("#All_Send").attr("disabled",false);
			$("#All_Send").removeClass("isClick").addClass("bToggle");
			return;
		}
		//拜访报告
		$.ajax({
            url:"SoftwareVisitReportOperateBatch",
            type:"post",
            data:form,
            processData:false,
            contentType:false,
            success:function(data){
            	if(data){
                    $.MsgBox.Alert("提示", "上传成功！");	
                    $("#All_Send").attr("disabled",false);
                    $("#All_Send").removeClass("isClick").addClass("bToggle");
                    $(".MailBar_All").hide();
                    $(".MailBar_cover_color").hide();
                    window.clearInterval();
                    console.log("over");
            	}else{
            		$.MsgBox.Alert("提示", "上传失败请稍候再试！");
            		 $("#All_Send").attr("disabled",false);
                     $("#All_Send").removeClass("isClick").addClass("bToggle");
            	}
            },
            error:function(e){
                alert("error");
                $("#All_Send").attr("disabled",false);
                $("#All_Send").removeClass("isClick").addClass("bToggle");
                window.clearInterval();
            }
        });  
	}
});  
$(function(){
	var content = $(".scontent").text();
	$(".tsearch").val(content);
})
//点击年代切换
$(".turnLeft").click(function(){
	var year_max = Number($(".year1").text());
	if(year_max == 2019){
		$.MsgBox_Unload.Alert("提示", "年份最多可选到2018！");
		return ;
	}else{
		$(".year1").text(Number($(".year1").text())+1);
		$(".year2").text(Number($(".year2").text())+1);
		$(".year3").text(Number($(".year3").text())+1);

		$(".year3").removeClass('yearBg');
		$(".year1").removeClass('yearBg');
		$(".year2").removeClass('yearBg');
	}
})
$(".turnRight").click(function(){
	var year_min = $(".year3").text();
	if(year_min == 2009){
		$.MsgBox_Unload.Alert("提示", "年份最早可选2010！");
		return ;
	}else{
		$(".year1").text($(".year1").text()-1);
		$(".year2").text($(".year2").text()-1);
		$(".year3").text($(".year3").text()-1);

		$(".year3").removeClass('yearBg');
		$(".year1").removeClass('yearBg');
		$(".year2").removeClass('yearBg');
	}
})
//点击搜索
$(".ysearch").click(function(){
	$("#show_MV_CFCs").attr("searchFlag","SingleSelect");
	var year;
	if($(".year1").hasClass("yearBg")){
		year = $(".year1").text();
	}else if($(".year2").hasClass("yearBg")){
		year = $(".year2").text();
	}else if($(".year3").hasClass("yearBg")){
		year = $(".year3").text();
	}else{
		year = "ALL";
	}
	var year2;
	if($(".year2014 a").hasClass("current")){
		year2 = 2014;
	}else if($(".year2015 a").hasClass("current")){
		year2 = 2015;
	}else if($(".year2016 a").hasClass("current")){
		year2 = 2016;
	}else if($(".year2017 a").hasClass("current")){
		year2 = 2017;
	}else if($(".year2018 a").hasClass("current")){
		year2 = 2018;
	}
	var content = $(".tsearch").val();
	var catalog = $(".scatalog").text();
	var Area = $(".sarea").text();
	var type = $(".otype").text();
	if(catalog == 'VisitReport'){
		window.location.href="DocumentUpload?queryType=SingleSelect&Year="+year+"&catalog="+catalog+"&Type="+type+"&content="+content+"&currentPage=1";
	}else if(catalog == 'Manual' || catalog =='Achieve'){
		window.location.href="DocumentUpload?queryType=SingleSelect&Area="+Area+"&catalog="+catalog+"&Type="+type+"&content="+content+"&currentPage=1";
	}else if(catalog == 'Log'){
		window.location.href="DocumentUpload?queryType=SingleSelect&Area="+Area+"&Year="+year2+"&catalog="+catalog+"&Type="+type+"&content="+content+"&currentPage=1";
	}
	else if(catalog == 'Software'){   
		searchOrjump(1,content,sortFlag);
	}else if(catalog == 'Environment'){
        window.location.href="DocumentUpload?queryType=SingleSelect&Area="+Area+"&catalog="+catalog+"&Type="+type+"&content="+content+"&currentPage=1";
    }else{
		window.location.href="DocumentUpload?queryType=SingleSelect&Type="+type+"&catalog="+catalog+"&content="+content+"&currentPage=1";
	}
})
//点击年份变色
$(".year1").click(function(){
	if(Number($(".year1").text()) == 2019){
		$.MsgBox_Unload.Alert("提示", "该年份没有数据！");
		return;
	}else{
		$(".year2").removeClass('yearBg');
		$(".year3").removeClass('yearBg');
		$(".allTime").removeClass("bg");
		var year = $(".year1").text() ;
		window.location.href="DocumentUpload?queryType=common&Year="+year +"&catalog=VisitReport&currentPage=1";
	}
})
$(".year2").click(function(){
		$(".year1").removeClass('yearBg');
		$(".year3").removeClass('yearBg');
		$(".allTime").removeClass("bg");
		var year = $(".year2").text() ;
		window.location.href="DocumentUpload?queryType=common&Year="+year +"&catalog=VisitReport&currentPage=1";
})
$(".year3").click(function(){
	if(Number($(".year3").text()) == 2009){
		$.MsgBox_Unload.Alert("提示", "该年份没有数据！");
		return;
	}else{
		$(".year1").removeClass('yearBg');
		$(".year2").removeClass('yearBg');
		$(".allTime").removeClass("bg");
		var year = $(".year3").text() ;
		window.location.href="DocumentUpload?queryType=common&Year="+year +"&catalog=VisitReport&currentPage=1";
	}
})
//点击全部按钮
$(".allTime").click(function(){
	$(".allTime").addClass("bg");
	$(".year3").removeClass('yearBg');
	$(".year1").removeClass('yearBg');
	$(".year2").removeClass('yearBg');
	window.location.href="DocumentUpload?queryType=common&Year=ALL&catalog=VisitReport&currentPage=1";
});

//软件文档 tab切换   Norms CommonProblem Manage
$(".Norms,.CommonProblem,.Manage").click(function(){
	$(this).css("background","url(image/bg2.png)");
	$(this).siblings().css("background","url(image/bg3.png)");
	var ClassName = $(this).attr("class") ;
	 Load(ClassName);
});

function Load(ClassName){
	if(ClassName == "Norms"){       //开发规范
		//$(".All_upload").hide();
		sessionStorage.setItem("tab", "Norms"); 
		$(".Norms").parent().css("background","url(image/wendang3.png)");
		$(".Norms").attr("ID","currentTab").siblings().removeAttr("ID");
	 	   $.ajax({
	 	        type : 'get',
	 	        url : 'Software',
	 	        data:{
	 	        	Type :"Specification",
	 	        	currentPage : 1,
	 	        	queryType :"common"
	 	        },
	 	        dataType:"json",
	 	        success : function (data) {
	 	        	//data = eval(data);
	 	        	console.log(data);
	 	        	$("#table1").show();
	 	        	$("#table1").empty();
	 	        	$("#table2").hide();
	 	        	var str = '<tr>'+
					'<td style="width:15%;">序号</td>'+
					'<td style="display:none;">文件类型</td>'+
					'<td style="width:65%;cursor:pointer;"  title="排序"  class="TitleName">文件名称 </td>'+
					'<td style="width:20%;">下载</td>'+
                    '<td style="display:none;"></td>'+
				'</tr>';
	 	        	if( data.datas.length> 1){
	 	        		for(var i = 1; i < data.datas.length;i++){
	 	        			str += 
	 	 	        			'<tr>'+
	 								'<td class="Serial" value="'+data.datas[i].ID+'">'+i+'</td>'+
	 								'<td style="display:none;"><i class="fa fa-file-zip-o contract-edit" ></i></td>'+
	 								'<td class="ManualWest">'+data.datas[i].FileName+'</td>'+       
	 								'<td><span class="export" ></span></td>'+
                                    '<td style="display:none;"></td>'+
	 							'</tr>';
		 	        	}
	 	        	}
	 	        	$(".pageInfo #currentPage").text(data.currentPage);
	 	        	$(".pageInfo #allPage").text(data.pageCounts);
	 	        	$("#table1").append(str);
	 	        	jumpDisable();
	 	        },
	 	        error : function () {
	 	        }
	 	    });
	}
	else if(ClassName == "CommonProblem"){
		//$(".All_upload").hide();
		sessionStorage.setItem("tab", "CommonProblem"); 
		$(".CommonProblem").parent().css("background","url(image/wendang2.png)");
		$(".CommonProblem").attr("ID","currentTab").siblings().removeAttr("ID");
		$.ajax({
 	        type : 'get',
 	        url : 'Software',
 	       data:{
	        	Type :"CommonProblem",
	        	currentPage : 1,
	        	queryType :"common"
	        },
	        dataType:"json",
 	        success : function (data) {
 	        	console.log(data);
 	        	$("#table1").hide();
 	        	$("#table2").empty();
 	        	$("#table2").show();
 	        	var str = '';
 	          	if( data.datas.length> 1){
 	        		for(var i = 1; i < data.datas.length;i++){
 	        			var ProblemCont_name = data.datas[i].FileName.split("-")[0];
 	        			var ProblemCont_Author = data.datas[i].FileName.split("-")[2].split(".")[0];
 	        			str += 
 	        			'<tr style="width:97%;padding:0 1.5%;">'+
							'<td >'+
								'<p  style="cursor:pointer;" title="排序" class="ProblemNum Serial" value="'+data.datas[i].ID+'">'+i+'</p>'+
								'<ol  class="ProblemCont">'+
									'<li class="ProblemCont_name" >'+ProblemCont_name+'</li>'+
									'<li class="ProblemCont_cont" >'+data.datas[i].Content+'</li>'+
									'<li class="ProblemCont_Marking">'+
										'<div class="ProblemCont_Author" >作者：'+ProblemCont_Author+'</div>'+
										'<div class="ProblemCont_Time">'+data.datas[i].OperatingTime.slice(0,16)+'</div>'+			
									'</li>'+
								'</ol>'+
							'</td>'+
						'</tr>';
	 	        	}
 	        	}
 	          	$(".pageInfo #currentPage").text(data.currentPage);
 	        	$(".pageInfo #allPage").text(data.pageCounts);
 	        	$("#table2").append(str);
 	        	jumpDisable();
 	        },
 	        error : function () {
 	        }
 	    });
	}
	else{
		sessionStorage.removeItem("tab"); 
		$(".Manage").parent().css("background","url(image/wendang1.png)");
		$(".Manage").attr("ID","currentTab").siblings().removeAttr("ID");
		$.ajax({
 	        type : 'get',
 	        url : 'Software',
 	        data:{
 	        	Type :"Software",
 	        	currentPage : 1,
 	        	queryType :"common"
 	        },
 	        dataType:"json",
 	        success : function (data) {
 	        	console.log(data);
 	        	$("#table1").show();
 	        	$("#table1").empty();
 	        	$("#table2").hide();
 	        	var str = '<tr>'+
							'<td style="width:15%;" >序号</td>'+
							'<td style="display:none;">文件类型</td>'+
							'<td style="width:65%;cursor:pointer;" title="排序"  class="TitleName">文件名称 </td>'+
							'<td style="width:20%;">下载</td>'+
                            '<td style="display:none;"></td>'+
						'</tr>';
 	        	for(var i = 1; i < data.datas.length;i++){
 	        		str += 
 	        			'<tr>'+
							'<td class="Serial" value="'+data.datas[i].ID+'">'+i+'</td>'+
							'<td style="display:none;"><i class="fa fa-file-zip-o contract-edit" ></i></td>'+
							'<td class="ManualWest">'+data.datas[i].FileName+'</td>'+       
							'<td><span class="export"></span></td>'+
                            '<td style="display:none;"></td>'+
						'</tr>';
	 	        	}
 	        	$(".pageInfo #currentPage").text(data.currentPage);
 	        	$(".pageInfo #allPage").text(data.pageCounts);
 	        	$("#table1").append(str);
 	        	jumpDisable();
 	        },
 	        error : function () {
 	        }
 	    });
	}

}
	// 软件文档    排序
	$(document).on("click","#table1 tr:first .TitleName,#table2 .ProblemNum ",function(){
		if($("#catalog").text()=="Software"){
			sortFlag =!sortFlag;
			console.log("sadasdas");
			var currentPage = $("#currentPage").text();
			var content = $(".tsearch").val();
			searchOrjump(currentPage,content,sortFlag);	
		}
	});

// 软件文档    预览
	$(document).on("click",".ProblemCont_name,.ManualWest",function(){
		if($("#catalog").text()=="Software"){
            var ID;
            var url;
			if($(this).attr("Class")=="ProblemCont_name"){
				ID = $(this).parent().prev().attr("value");
			}
			else{
				ID = $(this).parent().find("td").eq(0).attr("value");
			}
			console.log(ID);
			if($("#currentTab").attr("class")== "Norms"){
				url ="SoftwareSpecificationDownload"; 
			}
			else if($("#currentTab").attr("class")== "CommonProblem"){
				url ="CommonProblemDownload"; 
			}
			else{
				url ="SoftwareDepartmentDownload"; 
			}
			$.ajax({
	 	        type : 'get',
	 	        url : url,
	 	        async:false,
	 	        data:{
	 	        	ID :ID,
	 	        	/*Preview : "Preview"*/
	 	        },
//	 	       dataType:"JSON",
	 	        success : function (data) {
	 	        	console.log(data);
	 	        	window.location.href=data;
	 	        },
	 	        error : function () {
	 	        	console.log("error");
	 	        }
	 	    });
	 	   
		}
	});
