 var TempletCont = $(".MailBar_content").val().replace(/\n|\r\n/g,"<br>");
     
    $('#Mail_Send').on('click', function() { 
    	console.log(ContractNo + "===="+ ContractTitle)
        $.ajaxFileUpload({  
            url:'FileUpload',  
            secureuri:false,  
            fileElementId:'Mail_fileToUpload',//file标签的id  
            dataType: 'JSON',//返回数据的类型  
            data:{
            	"ContractNo":ContractNo,
            	Content:EmailCont,
            	
            	"ContractTitle":ContractTitle
            },
            success: function (data) {
           		$.MsgBox.Alert("提示", "发送成功！");	
           		$(".MailBar").hide();
           		$(".MailBar_cover_color").hide();
            },  
            error: function (e) {  
                alert("error");  
            }  
        });  
    });  
    