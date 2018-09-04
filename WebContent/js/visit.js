//超链接时的模板显示
$('.contract-edit').click(function () {
	    $('.MailBar_cover_color').show();
	    var tr=$(this).parent();
	    var  ID = $(this).attr("value");//即表格中当前条的ID
	    $('.hidePdf .yemei').attr("value",ID);
			$(".hidePdf .tr_spc").remove();
			
			$.ajax({
		            type : 'get',
		            url : "RoutineVisitProject",
		            data : {
		            	ID : ID,
		            },
		            dataType : 'json',
		            success : function (data) { 
		            	console.log(data);
		            	//第一个集合
		            	for(var i = 1 ; i <data[0].basic.length; i++ ){
		            		  $(".hidePdf .CustomerUnit").text(" ").text(data[0].basic[i].CustomerUnit);
		            		  $(".hidePdf .EndUser").text(" ").text(data[0].basic[i].EndUser);
		            		  $(".hidePdf .EndTel").text(" ").text(data[0].basic[i].EndTel);
		            	      $(".hidePdf .ToEngineer").text(" ").text(data[0].basic[i].ToEngineer);
		            	      $(".hidePdf .ModelAndSN").text(" ").text(data[0].basic[i].ModelAndSN);
		            	      $(".hidePdf .Instrument").text(" ").text(data[0].basic[i].Instrument);
		            	      $(".hidePdf .VisitDate").text(" ").text(data[0].basic[i].VisitDate);
		            	      $(".hidePdf .CheckContent").text(" ").text(data[0].basic[i].CheckContent);
		            	      $(".hidePdf .Conclusion").text(" ").text(data[0].basic[i].Conclusion);
		            	      $(".hidePdf .yejiao").attr("value",data[0].basic[i].ID);
		            	}
		            	
		            	//第二个集合
		            	var len=data[0].project.length;
		            	
		            	for(var i = 1 ; i<4; i++){
		            		 $(".hidePdf .pdf_one_tr").eq(i-1).attr("ID",data[0].project[i].ID)
		            		 $(".hidePdf .Project").eq(i-1).text(" ").text(data[0].project[i].Project);
		            	     $(".hidePdf .CheckSituation").eq(i-1).text(" ").text(data[0].project[i].CheckSituation);
		            	     setRadioChecked(data[0].project[i].Evolve,$(".hidePdf .pdf_one_tr").eq(i-1).find(".Evolve"));
		            		 setRadioChecked(data[0].project[i].EvolveAnother,$(".hidePdf .pdf_one_tr").eq(i-1).find(".EvolveAnother"));
		            	}
		            	
		            	var itemStr ="";
		            	for(var i = 4 ; i <len; i++ ){
		            		var EvolveFlag,EvolveAnotherFlag;
		            		  itemStr +=
		            			 '<tr class="pdf_one_tr tr_spc" ID="'+data[0].project[i].ID+'">'+
		         					'<td class="id Project"  contenteditable="true">'+data[0].project[i].Project+'</td>'+
		         					'<td><p class="CheckSituation" contenteditable="true">'+data[0].project[i].CheckSituation+'</p></td>'+
		         					'<td>'+
		         						'<input type="radio" class="Evolve"   name="r_sel'+i+'"  />现场完成维修<br>'+
		         						'<input type="radio" class="EvolveAnother" name="r_sel'+i+'"  />现场无法完成维修，需总部讨论，请参考结论'+
		         					'</td>'+
		         					'<td><input type="button" class="add_del4" style="width:90%;height:90%;" value="删除"></td>'+
		         				'</tr>';
		            	}
		            	$(".hidePdf .pdf_seven_tab").append(itemStr);
		            	for(var i = 4 ; i <= $(".hidePdf .pdf_one_tr").length; i++ ){
		            		setRadioChecked(data[0].project[i].Evolve,$(".hidePdf .pdf_one_tr").eq(i-1).find(".Evolve"));
	            		    setRadioChecked(data[0].project[i].EvolveAnother,$(".hidePdf .pdf_one_tr").eq(i-1).find(".EvolveAnother"))
		            	}
		            },
		            error : function () {
		                $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
		            }
		        });
    $('.hidePdf').show(); 
 })	
 
 function setRadioChecked(data,dom){
		if(data == 1){
			dom.prop('checked',true);
	    }
	    else{
	    	dom.prop('checked',false);
	    }
}
 
	   //修改中添加项目的信息
	   /* var addFlag = 3;*/
	 	$('.hidePdf #StockList #send_ADD').click(function(){
	 		var addFlag = $(".hidePdf .pdf_one_tr").length;
	 		console.log(addFlag);
	 		addFlag++;
	 		var itemStr=
		 		'<tr class="pdf_one_tr tr_spc">'+
					'<td class="id Project" value=""  contenteditable="true">    </td>'+
					'<td><p class="CheckSituation" contenteditable="true"></p></td>'+
					'<td>'+
						'<input type="radio" class="Evolve"   value="1" name="r_sel'+addFlag+'"/>现场完成维修<br>'+
						'<input type="radio" class="EvolveAnother" value="2" name="r_sel'+addFlag+'"/>现场无法完成维修，需总部讨论，请参考结论'+
					'</td>'+
					'<td><input type="button" class="add_del" style="width:90%;height:90%;" value="删除"></td>'+
				'</tr>';
	 		
	 		$(".hidePdf .pdf_seven_tab").append(itemStr);
	 	})
 
 
	//超链接时的模板保存
	 $(document).on("click","#submit_n7",function(){
		 	
			var CustomerUnit=$('.hidePdf .CustomerUnit').text();
		    var ModelAndSN=$('.hidePdf .ModelAndSN').text();
		    var EndUser=$('.hidePdf .EndUser').text();
		    var EndTel=$('.hidePdf .EndTel').text();
		    var Instrument=$('.hidePdf .Instrument').text();
		    var ToEngineer=$('.hidePdf .ToEngineer').text();
		    var VisitDate=$('.hidePdf .VisitDate').text();
		    var CheckContent=$('.hidePdf .CheckContent').text();
		    var ID=$('.hidePdf .yejiao').attr("value");
		    var Conclusion = $('.hidePdf .Conclusion').text();
		    console.log("ID"+ID);
		    console.log("CustomerUnit"+CustomerUnit);
		    console.log("ModelAndSN"+ModelAndSN);
		    console.log("EndUser"+EndUser);
		    console.log("EndTel"+EndTel);
		    console.log("Instrument"+Instrument);
		    console.log("ToEngineer"+ToEngineer);
		    console.log("VisitDate"+VisitDate);
		    console.log("CheckContent"+CheckContent);
		    
		    var Exist;
		    var Project = [];
		    var CheckSituation=[];
		    var Evolve=[];
		    var EvolveAnother=[];
		    var ProjectID=[];
		    
		    
		    
		    if($(".hidePdf .pdf_one_tr").length != 0){
				Exist = "yes";
				console.log( "len"+$(".hidePdf .pdf_one_tr").length)
				for(var i =0 ; i < $(".hidePdf .pdf_one_tr").length ; i++){
			    	 
			    	Project.push($(".hidePdf .pdf_one_tr").eq(i).find(".Project").text())
			    	CheckSituation.push($(".hidePdf .pdf_one_tr").eq(i).find(".CheckSituation").text())
			    		
			    	console.log($(".hidePdf .pdf_one_tr").eq(i).find(".Evolve").is(":checked"))
			    		if($(".hidePdf .pdf_one_tr").eq(i).find(".Evolve").is(":checked") == true){
			    			Evolve.push(1);
			    		}
			    		else{
			    			Evolve.push(0);
			    		}
			    	 	if($(".hidePdf .pdf_one_tr").eq(i).find(".EvolveAnother").is(":checked") == true){
			    			EvolveAnother.push(1);
			    		}
			    		else{
			    			EvolveAnother.push(0);
			    		}
			    	 
			    	 
			    	 
			    	if($(".hidePdf .pdf_one_tr").eq(i).attr("ID")){
			    		ProjectID.push($(".hidePdf .pdf_one_tr").eq(i).attr("ID"));
					}
					else{
						ProjectID.push(0);
					}
			    	 
			    }
			}
			else{
				Exist = "no";
			}
		    
		    
		    console.log("集合");
		    console.log(Exist);
		    console.log(Project);
		    console.log(CheckSituation);
		    console.log("Evolve");
		    console.log(Evolve);
		    console.log("EvolveAnother");
		    console.log(EvolveAnother);
		    console.log("id:"+ProjectID);
		    
		  	$.ajax({
		            type : 'get',
		            url : 'RoutineVisitOperate',
		            data : {
		            	ID:ID,
		            	CustomerUnit : CustomerUnit,
		            	ModelAndSN : ModelAndSN,
		            	EndUser : EndUser,
		            	EndTel:EndTel,
		            	Instrument : Instrument,
		            	ToEngineer : ToEngineer,
		            	VisitDate : VisitDate,
		            	CheckContent : CheckContent,
		            	Exist : Exist,
		            	Project : Project,
		            	CheckSituation : CheckSituation,
		            	Evolve : Evolve,
		            	EvolveAnother : EvolveAnother,
		            	ProjectID : ProjectID ,
		            	Conclusion : Conclusion
		            },
		            dataType : 'json',
		            success : function (data) { 
		            	console.log(data)
		            	if(data==true){
		            		$.MsgBox.Alert('提示','保存成功');
				            $('.MailBar_cover_color').hide();
				            $('.hidePdf').hide();
		            	}else{
		            		 $.MsgBox.Alert("提示", "保存失败！");
		            	}
		            	
		            },
		            error : function () {
		                $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
		            }
		        });
	});

//售后拜访项目 删除
$(document).on("click",".hidePdf .add_del4",function(){
	  var ID = $(this).parent().parent().attr("ID");
	  var that=$(this).parent().parent().remove();
		console.log("ID"+ID)
		$.ajax({
	        type : 'get',
	        url : 'RoutineProjectDelete',
	        data : {
	        	ProjectID : ID
	        },
	        dataType : 'json',
	        success : function (data) {
	        	that.remove();
	        	/*$.MsgBox.Alert("提示", "删除成功！");*/
	        },
	        error : function () {
	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    });
     })

$(document).on("click",".add_del",function(){
	$(this).parent().parent().remove();
})





//添加项目的信息
	    /*var addFlag = 3;*/
	 	$('.hidePdf1 #StockList #send_ADD1').click(function(){
	 			var addFlag = $(".hidePdf .pdf_one_tr").length;
	 		console.log(addFlag);
	 		addFlag++;
	 		var itemStr=
		 		'<tr class="pdf_one_tr tr_spc">'+
					'<td class="id Project" value="">   </td>'+
					'<td><p class="CheckSituation" contenteditable="true"></p></td>'+
					'<td>'+
						'<input type="radio" class="Evolve" value="1" name="r_sel'+addFlag+'"/>现场完成维修<br>'+
						'<input type="radio" class="EvolveAnother" value="2" name="r_sel'+addFlag+'"/>现场无法完成维修，需总部讨论，请参考结论'+
					'</td>'+
					'<td><input type="button" class="add_del1" style="width:90%;height:90%;" value="删除"></td>'+
				'</tr>';
	 		
	 		$(".hidePdf1 .pdf_seven_tab").append(itemStr);
	 	})
 
 
	//添加时的模板保存
	 $(document).on("click","#submit_n1",function(){
			var CustomerUnit=$('.hidePdf1 .CustomerUnit').text();
		    var ModelAndSN=$('.hidePdf1 .ModelAndSN').text();
		    var EndUser=$('.hidePdf1 .EndUser').text();
		    var EndTel=$('.hidePdf1 .EndTel').text();
		    var Instrument=$('.hidePdf1 .Instrument').text();
		    var ToEngineer=$('.hidePdf1 .ToEngineer').text();
		    var VisitDate=$('.hidePdf1 .VisitDate').text();
		    var CheckContent=$('.hidePdf1 .CheckContent').text();
		    var Conclusion = $('.hidePdf1 .Conclusion').text();
			var ID="0";

		    
		    console.log("ID"+ID);
		    console.log("CustomerUnit"+CustomerUnit);
		    console.log("ModelAndSN"+ModelAndSN);
		    console.log("EndUser"+EndUser);
		    console.log("EndTel"+EndTel);
		    console.log("Instrument"+Instrument);
		    console.log("ToEngineer"+ToEngineer);
		    console.log("VisitDate"+VisitDate);
		    console.log("CheckContent"+CheckContent);
		    
		    var Exist;
		    var Project = [];
		    var CheckSituation=[];
		    var Evolve=[];
		    var EvolveAnother=[];
		    var ProjectID=[];
		    
		    
		    
		    if($(".hidePdf1 .pdf_one_tr").length != 0){
				Exist = "yes";
				for(var i =0 ; i < $(".hidePdf1 .pdf_one_tr").length ; i++){
			    	Project.push($(".hidePdf1 .pdf_one_tr").eq(i).find(".Project").text())
			    	CheckSituation.push($(".hidePdf1 .pdf_one_tr").eq(i).find(".CheckSituation").text())
			    		
			    	console.log($(".hidePdf1 .pdf_one_tr").eq(i).find(".Evolve").is(":checked"))
			    		if($(".hidePdf1 .pdf_one_tr").eq(i).find(".Evolve").is(":checked") == true){
			    			Evolve.push(1);
			    		}
			    		else{
			    			Evolve.push(0);
			    		}
			    	 	if($(".hidePdf1 .pdf_one_tr").eq(i).find(".EvolveAnother").is(":checked") == true){
			    			EvolveAnother.push(1);
			    		}
			    		else{
			    			EvolveAnother.push(0);
			    		}
						ProjectID.push(0);
			    }
			}
			else{
				Exist = "no";
			}
		    
		    
		    console.log("集合");
		    console.log(Exist);
		    console.log(Project);
		    console.log(CheckSituation);
		    console.log("Evolve");
		    console.log(Evolve);
		    console.log("EvolveAnother");
		    console.log(EvolveAnother);
		    console.log("id:"+ProjectID);
		    
		    	$.ajax({
		            type : 'get',
		            url : 'RoutineVisitOperate',
		            data : {
		            	ID:ID,
		            	CustomerUnit : CustomerUnit,
		            	ModelAndSN : ModelAndSN,
		            	EndUser : EndUser,
		            	EndTel:EndTel,
		            	Instrument : Instrument,
		            	ToEngineer : ToEngineer,
		            	VisitDate : VisitDate,
		            	CheckContent : CheckContent,
		            	Exist : Exist,
		            	Project : Project,
		            	CheckSituation : CheckSituation,
		            	Evolve : Evolve,
		            	EvolveAnother : EvolveAnother,
		            	ProjectID : ProjectID,
		            	Conclusion : Conclusion
		            },
		            dataType : 'json',
		            success : function (data) { 
		            	console.log(data)
		            	if(data==true){
		            		$.MsgBox.Confirm('提示','数据已保存，是否离开页面');
				            /*$('.MailBar_cover_color').hide();
				            $('.hidePdf1').hide();*/
		            	}else{
		            		 $.MsgBox.Alert("提示", "保存失败！");
		            	}
		            	
		            },
		            error : function () {
		                $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
		            }
		        });
	});

//售后拜访项目 删除
$(document).on("click",".hidePdf1 .add_del4",function(){
	  var ID = $(this).parent().parent().find(".id").attr("value");
	  var that=$(this).parent().parent().remove();
		console.log("ID"+ID)
		$.ajax({
	        type : 'get',
	        url : 'RoutineProjectDelete',
	        data : {
	        	ProjectID : ID
	        },
	        dataType : 'json',
	        success : function (data) {
	        	that.remove();
	        	/*$.MsgBox.Alert("提示", "删除成功！");*/
	        },
	        error : function () {
	            $.MsgBox.Alert("提示", "服务器繁忙，稍后重试！");
	        }
	    });
     })
    var reg = /^(\d{4})-(0\d{1}|1[0-2])-(0\d{1}|[12]\d{1}|3[01])$/;
	$(".hidePdf .VisitDate,.hidePdf1 .VisitDate").on("blur",function(){
		if(reg.test($(this).text())){
		}
		else{
			alert("请输入正确拜访日期，格式如：2017-01-01")
		}
	})
$(document).on("click",".add_del1",function(){
	$(this).parent().parent().remove();
})
     