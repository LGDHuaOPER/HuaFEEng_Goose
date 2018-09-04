// version Flag函数
function versionControlFlag(str){
	var versionControlFlag = true;
	globalVersionExceptFileName.map(function(currentValue, index, arr){
		if(str.indexOf(currentValue) > -1){
			versionControlFlag = false;
			return false;
		}
	});
	return versionControlFlag;
}

// 加版本号
$("link").each(function(){
	if($(this).attr("href")){
		var oldHref = $(this).attr("href");
		if(versionControlFlag(oldHref)){
			var newHref;
			if(oldHref.indexOf("?")>-1){
				newHref = oldHref + "&ver=" + globalVersionNo;
			}else{
				newHref = oldHref + "?ver=" + globalVersionNo;
			}
		    $(this).attr("href",newHref);
		}
	}
});
$("script").each(function(){
	if($(this).attr("src")){
		var oldSrc = $(this).attr("src");
		if(oldSrc.indexOf("?")>-1) return true;
		if(versionControlFlag(oldSrc)){
			 var newSrc = oldSrc + "?ver=" + globalVersionNo ;
		     // $(this).attr("src",newSrc);
		     $.getScript(newSrc);
		} 
	}
});