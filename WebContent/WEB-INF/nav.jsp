<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- <link rel="stylesheet" href="css/libs/lava_lamp_demo.css" type="text/css"> -->
<style type="text/css">
* {
	margin: 0;
	padding:0;
}
.pageInfo{
	margin-right:20px;
}
html,body{
    width:100%;
    height:auto;
    font-family: "Arial","microsoft yahei";
    background: #e4e8eb;
}
.contain{
    width: 100%;
    height: auto;
}

input,select{
    font-family: "Arial","microsoft yahei";
    outline:none;
}
ul,li{
	list-style: none;
}
a{
	text-decoration: none;
}
	
/*-------------------------婢舵挳鏁撻弬銈嗗------------------------------*/
.header{
    width: 100%;
    height: 80px;
    background: #00aeef;
    color: #fff;
    line-height: 80px;
    font-size: 32px;
}
.header span{
    padding-left: 30px;
}
.header .t-span{
    float: right;
    font-size: 14px;
    margin-right: 20px;
    padding-top:20px;
}
.header a{
    color: #ffffff;
    text-decoration: none;
    cursor:pointer;
}
.header a:hover{
    text-decoration:none!important;
    color:#fff!important;
}
a:hover{
    text-decoration:none!important;
}

/*-----------------------------------导航栏---------------------------------*/
.nav{
	clear:both;
    width: 90%;
    min-width: 1000px;
    height: 50px;
    line-height: 30px;
    margin: 10px auto 0;
    background: #00aeef;
    font-size:16px;
}
.nav ul {
	background: #00aeef;
    margin: 0;
    padding: 0;
}
.nav a{
	color: white;
    text-decoration: none;
    cursor:pointer;
}
.nav ul li{	
    display:block;
    float:left;
    text-decoration: none;
    text-align:center;
    padding: 10px 0;
    position: relative;
     width:11.5%; 
    }
 
.nav ul li:hover{	
    background: white;
}
.nav ul li:hover ul{
	display:block;
    background: white;
    width: 100%;
}
.nav ul li:hover a{	
    color:#00aeef;
}
 .nav ul li ul{
    display:none;
     width: 100%;
} 
.nav ul li ul li{
	width: 100%;
    display: block;
    text-decoration: none;
    margin: 0;
    padding: 0;
}
.nav ul li ul li:hover{
	background:#00aeef;
	cursor:pointer;
}
.nav ul li ul li:hover a{
	color:#fff;
}
.info_list{
    position: absolute;
    display: none;
    z-index:999;
}
.currentPage{
    background: white;
 }
 .currentPage a{
    color:#00aeef;
   }
  .navHidden{
  	 display: none!important;
  }
  .Nav_More{
  	font-weight:bolder;
  	color:#fff;
  	cursor:pointer;
  	font-size:17px;
  	font-weight:bolder;
  	width:5.5%!important;
  }
  .Nav_More:hover{
  	color:#00aeef!important;
  }
.content{
    width: 100%;
    height: 100%;
    background: #e4e8eb;
    padding-bottom: 80px;
    position: relative;
}
.content .select-content{
    padding-top: 25px;
    display: inline-block;
    position: absolute;
    right: 5%;
}
.content .select-button{
    display: inline-block;
}
.content label{
    font-weight:normal;
}
.content .select1,.content .select2{
    width: auto;
    height: 30px;
    border: 1px solid darkgrey;
    display: inline-block;
}
.content .select-content select{
    width: 120px;
    height: 30px;
    color: #000;
    outline: none;
    border: none;
    border-right: 2px solid rgba(169, 169, 169, 0.2);
}
.content .select-content input[type="text"]{
    width: 120px;
    height: 27px;
    margin-left: -5px;
    color: #000;
    outline: none;
    border: none;
}
 .time{
	display:none;
} 
.content .select-content input[type="date"]{
    width: 120px;
    height: 29px;
    color: #000;
    outline: none;
    border: none;
}
.content .select-content input[type="radio"]{
    margin-bottom: 10px;
}
.content .select-button input[type="button"]{
    width: 50px;
    height: 30px;
    border: none;
}
.content .choose{
    margin-left: 5%;
    margin-bottom: 20px;
}
.content .choose input[type="button"]{
    width: 50px;
    height: 30px;
    border: none;
    margin: 35px 0 10px 0;
}

 .bToggle{
    background: #00aeef;
    border: solid 1px #00aeef;
    color:#fff;
    cursor: pointer;
    box-shadow: 1px 2px 5px 0 #00aeef;
}
 .bToggle:hover{
    background: #fff;
    color:#00aeef;
}

.nav-container {
    width: 100%;
    height: 50px;
    box-sizing: border-box;
}

.nav-container-in {
    margin: 20px auto 0 auto;
    width: 90%;
    height: 50px;
    display: flex;
    box-sizing: border-box !important;
    border: 1px solid #ccc;
    background: rgba(255,255,255,0.8);
    font-size:15px !important;
}

.nav-container-in-l {
    flex: 5;
    height: 50px !important;
    line-height: 50px !important;
    padding-left: 10px !important;
}

.nav-container-in-r {
    flex: 11;
}
.nav-container-in-l ul {
    display: inline-block;
}

.nav-container-in-l li {
    display: inline-block;
    list-style:none;
    height:50px !important;
    min-width:65px;
    text-align:center;
}

.nav-container-in-l li a{
    text-decoration: none;
    color: #000 !important;
}

.nav-container-in-l li a:hover{
    text-decoration: none;
    color:#00aeef !important;
}

.nav-container-in-l span {
    display:inline-block;
    width:20px !important;
    height:50px !important;
    line-height:50px !important;
    text-align:center;
    display: none;
}
</style>
</head>
<body class="nav-body">
    <div class="nav-container">
        <div class="nav-container-in" style="opacity:0;">
            <div class="nav-container-in-l">位置：
                <ul>
                    <li class="nav-container-in-li-1"><a href="" class="nav-title"></a></li><span class="nav-sub-span">&gt;</span>
                    <li class="nav-container-in-li-2" style="display: none;"><a href="" class="nav-sub-tit"></a></li><span class="nav-dbsub-span">&gt;</span>
                    <li class="nav-container-in-li-3" style="display: none;"><a href="" class="nav-dbsub-tit"></a></li>
                </ul>
            </div>
            <div class="nav-container-in-r"></div>
        </div>
    </div>
<script>  
    //去除空格处理
        function Trim(str){
            str = str.replace(/\s/g,'') ;
            return str;
        }
    var isOpacityNav = eouluGlobal.S_getShowNavArr();
    $(function(){
        var newNavProHref = eouluGlobal.S_getCurPageHref();
        console.log("nav页面的处理后的href:"+newNavProHref);
        if(isOpacityNav.indexOf(newNavProHref)==-1){
            $(".nav-container-in").css("opacity",0);
        }else{
            if(newNavProHref=='Transport' || newNavProHref == 'GetOrderRoute' || newNavProHref == 'GetOrderByPageOne' || newNavProHref == 'GetOrderByPageTwo'){
                newNavProHref = 'Transport?ActualDelivery=no&column=DateOfSign&condition=All';
            }else if(newNavProHref=='StockPurchasing'){
                newNavProHref = 'StockPurchasing?ActualDelivery=no&column=DateOfSign&condition=All';
            }else if(newNavProHref=='Price'){
                newNavProHref = 'Price?ActualDelivery=no&column=DateOfSign&condition=All';
            }else if(newNavProHref=='DocumentUpload'){
                newNavProHref = $("li.MachineDetails0 h5.DocumentUpload").children("a").attr("href");
            }else if(newNavProHref=='GetMachineDetailsRoute'){
                newNavProHref = 'MachineDetails';
            }
            console.log("nav页面的再次处理后的href:"+newNavProHref);
            var firstTit = $(".eou-container .m-li.current").children("a").text();
            var firstTitHref = $(".eou-container .m-li.current").children("a").attr("href");
            $(".nav-container-in-li-1").children("a").text(firstTit);
            $(".nav-container-in-li-1").children("a").attr("href",firstTitHref);
            // 2级标题处理
            $("li.m-li div.m-nav-div h5").each(function(){
                // 说明有3级标题
                if($(this).parent('div.subTit').length>0){
                    var that = $(this);
                    $(this).siblings("h6").each(function(){
                        if($(this).children("a").attr("href")==newNavProHref){
                            var subInsertHref = that.children("a").attr("href");
                            var subInsertText = that.children("a").text();
                            $(".nav-container-in-li-2").children("a").attr("href",subInsertHref).text(subInsertText);
                            var dbSubInsertHref = $(this).children("a").attr("href");
                            var dbSubInsertText = $(this).children("a").text();
                            $(".nav-container-in-li-3").children("a").attr("href",dbSubInsertHref).text(dbSubInsertText);
                            subTitGroupShow();
                            dbSubTitGroupShow();
                            // console.log(subInsertHref);
                            // console.log(subInsertText);
                            // console.log(dbSubInsertHref);
                            // console.log(dbSubInsertText);
                        }
                    });
                }else{
                    if($(this).children("a").attr("href")==newNavProHref){
                        var insertHref = $(this).children("a").attr("href");
                        var insertText = $(this).children("a").text();
                        $(".nav-container-in-li-2").children("a").attr("href",insertHref).text(insertText);
                        subTitGroupShow();
                        dbSubTitGroupHide();
                    }
                }
            });
            $(".nav-container-in").fadeTo(250,1);
        }
        
        function subTitGroupShow(){
            $("span.nav-sub-span, .nav-container-in-li-2").show();
        }
        function subTitGroupHide(){
            $("span.nav-sub-span, .nav-container-in-li-2").hide();
        }
        function dbSubTitGroupShow(){
            $("span.nav-dbsub-span, .nav-container-in-li-3").show();
        }
        function dbSubTitGroupHide(){
            $("span.nav-dbsub-span, .nav-container-in-li-3").hide();
        }

        function navCopyResponse(){
            if($("body.nav-body").length){
                // debugger;
                var windowH = $(window).height();
                var bodyH = $("body.nav-body").height();
                var str = '<div id="eoulu-copy-out" style="height:40px;width:calc(100% - 2px);"><div style="width:100%;height:5px;"></div><div id="eoulu-copy" style="width:100%;height:35px;font-size:12px;color:#888;line-height:35px;z-index: 2;"><hr style="height:1px;color:#999;width: calc(100% - 3px);" /><div style="width:100%;text-align:center;display:inline-block;">Copyright  ©&nbsp;<span class="YEAR">2018</span>&nbsp<a href="http://www.eoulu.com/h-col-268.html" class="EHref" target="_blank" style="color:blue;">Eoulu</a> Tech. Co.,Ltd.</div></div></div>';
                $("body.nav-body").css("max-height","none");
                $("body.nav-body").css("overflow-y","auto");
                
                // console.log("添加str后");
                // console.log($(window).height());
                // console.log($("body.nav-body").height());
                if($("body.nav-body").find("div.eou-container").length){
                    if(windowH-bodyH>40){
                        var newBodyHeight = $(window).height();
                        $("body.nav-body").css({
                            "height":newBodyHeight+"px",
                            "position":"relative"
                        });
                        $("body.nav-body").append(str);
                        $("div#eoulu-copy-out").css({
                            "position":"absolute",
                            "bottom":0
                        });
                    }else if(windowH-bodyH<=40 && windowH-bodyH>0){
                        var bodyH2 = bodyH + 40;
                        $("body.nav-body").css({
                            "height":bodyH2+"px",
                            "position":"relative"
                        });
                        $("body.nav-body").append(str);
                        $("div#eoulu-copy-out").css({
                            "position":"absolute",
                            "bottom":0
                        });
                    }else{
                        var bodyH3 = bodyH + 40;
                        $("body.nav-body").css({
                            "height":bodyH3+"px",
                            "position":"relative"
                        });
                        $("body.nav-body").append(str);
                        $("div#eoulu-copy-out").css({
                            "position":"absolute",
                            "bottom":0
                        });
                    }
                }
            }
        }
        var notCopyHref = eouluGlobal.S_getNotEouluCopy();
        var copyNewNavProHref = newNavProHref.indexOf("?")>-1?newNavProHref.split("?")[0]:newNavProHref;
        // console.log("版权处理方法的copyNewNavProHref为"+copyNewNavProHref);
        if(notCopyHref.indexOf(copyNewNavProHref)>-1){
            console.log("我跳过了nav的版权处理函数.");
            return;
        }else{
            navCopyResponse();
        }

        $(document).on("click",".nav-container-in-li-1 a[href='Tasking'], .nav-container-in-li-2 a[href='Tasking']",function(e){
            if ( e && e.preventDefault ){
                e.preventDefault();
            }else{
                window.event.returnValue = false;
            }
            var body3 = $(document.body),
                form3 = $("<form method='post'></form>");
            form3.attr({"action":"Tasking"});
            form3.appendTo(document.body);
            form3.submit();
            document.body.removeChild(form3[0]);
        });
    });
</script>
</body>

</html>