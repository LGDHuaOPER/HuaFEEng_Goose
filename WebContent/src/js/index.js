/*update by shiconghua at 18/03/16
* @license MIT 保留这段注释和说明
* */

$(function () {
    // 页面入口的麻将
    var globalIndexDepartmentMap = {
        "软件部":"image/index/department23.png",
        "商务部":"image/index/department24.png",
        "物流部":"image/index/department26.png",
        "物流统计":"image/index/department31.png",
        "合同统计":"image/index/department0.png",
        "服务部":"image/index/department20.png",
        "销售统计":"image/index/department18.png",
        "人事部":"image/index/department6.png",
        "实验室":"image/index/department4.png",
        "需求统计":"image/index/department15.png",
        "销售部":"image/index/department13.png"
    };
	
	// 获取时间戳
	// var date = new Date();
	var dateNow = "201808291119";
    // init
    var departNo = [];
    for (var i = 0;i<34;i++){
        departNo.push(i);
    }
    var uimgLen = $(".u-img").length;
    var randomNo = Math.floor(Math.random()*34);
    var randomNo1 = Math.floor(Math.random()*15);
    // console.log("主体img长度为"+$(".m-img img").length);
    // console.log("u-img长度为"+uimgLen);
   $(".m-img img").each(function () {
        var departmentImg;
        var departmentText = $(this).prev("a").text();
        for(var k in globalIndexDepartmentMap){
            if(k==departmentText){
                departmentImg = globalIndexDepartmentMap[k];
            }
        }
       var imgInit = departmentImg+"?" + dateNow;
       $(this).attr("src",imgInit);
   });
   
    $(".u-usual img").each(function () {
        var imgInit1 = "./image/index/usual" + randomNo1 +".png?" + dateNow;
        $(this).attr("src",imgInit1);
        randomNo1 = Math.floor(Math.random()*15);
    });

    $(".new img").each(function () {
    	/*var randomNo2 = Math.floor(Math.random()*34);*/
        var imgInit2 = "./image/index/usual14.png?" + dateNow;
        $(this).attr("src",imgInit2);
    });

    // 麻将发牌动画
    // s是对象，a是要执行的动画，ra是回复的动画，t是动画时间，n是执行次数（默认无限次）
    // 前三个参数一定要设置
    // 最后一个参数不要设置
    // function sA(s, a, ra, t, n, ba) {
    //     if (!ba) {
    //         var ba = s;
    //     }
    //     s.animate(a, t || "normal", function() {
    //         s.animate(ra, t || "normal");
    //         if (s.next().length) {
    //             sA(s.next(), a, ra, t, n, ba);
    //         } else {
    //             if (n === 0) {
    //                 return false;
    //             }
    //             if (typeof n != "number") {
    //                 n = "forever";
    //             } else {
    //                 n--;
    //             }
    //             if (n || n == "forever") {
    //
    //                 sA(ba, a, ra, t, n, ba);
    //             }
    //         }
    //     });
    // }
    // setTimeout(sA($(".u-img img").eq(0),{opacity:1},{bottom:0},500,1),1000);
    /*var uimgLen2 = $(".u-img").length;*/
    for (var ii =0;ii<uimgLen;ii++){
        // var imgI = document.getElementsByClassName("img-i");
        // var rect = imgI[o].getBoundingClientRect();
        // var rect = $(".img-"+ii).offset();
        // console.log(rect.left + ":" + rect.top);
        var rect = $(".u-img").eq(ii).offset();
        var x = rect.left;
        var y = rect.top;
        var el = $(".u-img img").eq(ii);
        // el.style.opacity = "1";
        // el.style.webkitTransform = "translate3d("+x+"px,"+y+"px,0)";
        // el.style.transform = "translate3d("+x+"px,"+y+"px,0)";
        // el.style.transition = "all 0.4s cubic-bezier(0.49, -0.29, 0.75, 0.41)";
        el.animate({ left:-x+"px",top: -y+"px",opacity:0.5},50,"easeInBack").animate({left: 0,top: 0,opacity:1},700,"easeInBack");
    }
    function newImg(){
        if($(".new .u-usual").length){
            var rect = $(".new .u-usual").offset();
            var x = rect.left;
            var y = rect.top;
            $(".new img").animate({ left:-x+"px",top: -y+"px",opacity:0.5},50,"easeInBack").animate({left: 0,top: 0,opacity:1},700,"easeInBack");
        }
    }
    newImg();

    // function addPos() {
    //     $(".u-img img").addClass("img-pos");
    // }
    // setTimeout(addPos,700);


    // //  2秒内按钮不可点击
    // var disTime=2;
    // // 兼容火狐
    // if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){
    //     $("button.btn-danger").fadeOut();
    //     function fireDisable() {
    //         $("button.btn-danger").fadeIn();
    //         $("button.btn-danger").html("洗牌");
    //         $("button.btn-danger").removeAttr("disabled");
    //     }
    //     setTimeout(fireDisable,2000);
    // }else{
    //     var timehwnd=setInterval(Countdown,1000);
    //     function Countdown(){
    //         disTime--;
    //         if(disTime == 0){
    //             $("button.btn-danger").html("洗牌");
    //             $("button.btn-danger").removeAttr("disabled");
    //             clearInterval(timehwnd);
    //         }else{
    //             $("button.btn-danger").html("洗牌("+disTime+")");
    //         }
    //     }
    // }

    // // 洗牌
    // $("button").click(function () {
    //     $(".m-img img").stop(true).animate({opacity : 0},300,"easeInOutBack",shuffleImg).animate({opacity : 1},600,"easeInOutBack");
    //     /*$(".new img").stop(true).animate({opacity : 0},300,"easeInOutBack",shuffleImg2).animate({opacity : 1},600,"easeInOutBack");*/
    // });
    // function shuffleImg() {
    //     departNo = _.shuffle(departNo);
    //     // console.log(departNo);
    //     for (var m=0;m<uimgLen;m++){
    //         var newDepartNo = "./image/index/department" + departNo[m] +".png?" + dateNow;
    //         $(".m-img img").eq(m).attr("src",newDepartNo);
    //     }
    // }

    /*function shuffleImg2() {
    	var randomNo3 = Math.floor(Math.random()*34);
        var imgInit3 = "./image/index/department" + randomNo3 +".png?" + dateNow;
        $(this).attr("src",imgInit3);
    }*/
    
    // 麻将移入事件
    function cardToTop() {
        $(".u-img, .new .u-usual").mouseenter(function () {
            // console.log(1);
            $(this).removeClass("card-hover-out").addClass("card-hover").siblings().removeClass("card-hover");
            $(this).find("a").css("color","#00aeef");
        });
        $(".u-img, .new .u-usual").mouseleave(function () {
            // console.log(1);
            $(this).addClass("card-hover-out");
            $(this).find("a").css("color","rgba(0,0,0,0.9)");
        });
    }
    setTimeout(cardToTop,800);

});
