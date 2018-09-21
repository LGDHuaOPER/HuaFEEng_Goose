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
    $('input[name="searchContent2"]').val('');
    $('#top_text_from').submit();
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

$(function(){
    // 页面加载完成，渲染最新进展
    var i = 1;
    $(".progressData").each(function(){
        console.log("第"+i+"个");
        var data = $(this).attr("title");
        console.log(data);
        console.log(typeof data);
        data = JSON.parse(data);
        console.log(data);
        console.log(typeof data);
        if(data.length == 1){
            $(this).siblings(".lastProgress").attr("title","--");
            $(this).siblings(".lastProgress").text("--");
        }else{
            $(this).siblings(".lastProgress").attr("title",data[data.length-1].CurrentProgress);
            $(this).siblings(".lastProgress").attr("inDate",data[data.length-1].Date);
            $(this).siblings(".lastProgress").text(data[data.length-1].CurrentProgress);
        }
        i++;
    });



    var globalTr1 = '<tr><td><img src="image/Undo.png" style="height:14px;">&nbsp;<span>';
    var globalTr2 = '</span></td><td contenteditable="true"></td><td><input type="date" class="form-control" style="height:30px;width:95%;"></td></tr>';
    $(document).on("click","#u-add-btn",function(){
        var globalTrNo = $(".m-add-table tbody tr").length+1;
        var globalTr = globalTr1+globalTrNo+globalTr2;
        $(".m-add-table tbody").append(globalTr);
        var addToday = globalGetToday();
        // alert(addToday);
        var globalTrNo2 = globalTrNo - 1;
        $(".m-add-table tbody tr").eq(globalTrNo2).find("input[type='date']").val(addToday);
    });

    $(document).on("click",".m-add-table tbody img",function(){
        $(this).parent().parent().remove();
        var trLen = $(".m-add-table tbody tr").length;
        for(var i=1;i<=trLen;i++){
            var j=i-1;
            $(".m-add-table tbody tr").eq(j).find("td:nth-child(1) span").text(i);
        }
    });

    $(document).on("click","#u-update-btn",function(){
        var globalTrNo = $(".m-update-table tbody tr").length+1;
        var globalTr = globalTr1+globalTrNo+globalTr2;
        $(".m-update-table tbody").append(globalTr);
        var addToday = globalGetToday();
        // alert(addToday);
        var globalTrNo2 = globalTrNo - 1;
        $(".m-update-table tbody tr").eq(globalTrNo2).find("input[type='date']").val(addToday);
    });

    $(document).on("click",".m-update-table tbody img",function(){
        $(this).parent().parent().remove();
        var trLen = $(".m-update-table tbody tr").length;
        for(var i=1;i<=trLen;i++){
            var j=i-1;
            $(".m-update-table tbody tr").eq(j).find("td:nth-child(1) span").text(i);
        }
    });
});