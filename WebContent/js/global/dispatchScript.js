$(function(){
	var dispatchCurHref = eouluGlobal.S_getCurPageHref();
	if(eouluGlobal.S_getPageHrefImportFileMapAllKey().indexOf(dispatchCurHref) > -1){
		var Environment = eouluGlobal.S_getEnvironment();
		var Reference = Environment == "development" ? "srcPath" : "referencePath";
		var notSortFileJsonArr = [];
		var loadJsFileObj = eouluGlobal.S_getCssJsPrimaryByHref(dispatchCurHref, "jsPrimary");
		$.each(loadJsFileObj, function(name, value){
			notSortFileJsonArr.push(value);
		});
		var SortFileJsonArr = ecDo.arraySort(notSortFileJsonArr,"referenceOrder");
		console.log(SortFileJsonArr);
		var mergeToParentFileArr = [];
		SortFileJsonArr.map(function(currentValue, index, arr){
			if(currentValue["mergeToParentFile"] != null){
				if(mergeToParentFileArr.indexOf(currentValue["mergeToParentFile"]) == -1){
					mergeToParentFileArr.push(currentValue["mergeToParentFile"]);
				}
			}
		});
		console.log(mergeToParentFileArr);
		var mergeToParentFilePathArr = [];
		mergeToParentFileArr.map(function(val, i, ar){
			mergeToParentFilePathArr.push(eouluGlobal.S_getCompressFilePathMapLowerValue("jsMerge", val, Reference));
		});
		console.log(mergeToParentFilePathArr);
		/*加载脚本部分*/

		function useWhenLoadScript(index){
			eouluGlobal.S_loadScript(mergeToParentFilePathArr[index],true,true,null,null,function(data){
				console.log(mergeToParentFilePathArr[index]+"加载完成！");
				index++;
				if(index < mergeToParentFilePathArr.length){
					useWhenLoadScript(index);
				}else if(index == mergeToParentFilePathArr.length){
					console.log("全部加载完毕！");
					$(".loading_div_g_div").hide();
				}else{
					console.warn("异常！index > mergeToParentFilePathArr.length");
				}
			},function(){
				$.MsgBox_Unload.Alert("提示","网络繁忙，加载脚本失败！");
			},null,undefined);
		}

		useWhenLoadScript(0);

		// var curIndex = 0;
		// var myFunct = function(data){
		//     curIndex++;
		//     if(curIndex < mergeToParentFilePathArr.length){
		//         $.when(
		//             eouluGlobal.S_loadScript(mergeToParentFilePathArr[curIndex],true,true,null,null,null,null,null,undefined)
		//         ).done(myFunct);
		//     }else if(curIndex == mergeToParentFilePathArr.length){
		//     	console.log("加载完毕！");
		//     	$(".loading_div_g_div").hide();
		//     }
		// };
		// $.when(
		//     eouluGlobal.S_loadScript(mergeToParentFilePathArr[curIndex],true,true,null,null,null,null,null,undefined)
		// ).then(myFunct, function(){
		//     	$.MsgBox_Unload.Alert("提示","网络繁忙，加载脚本失败！");
		// 	});
		/*加载脚本部分结束*/
	}else{
		console.warn("dispatchScript.js未执行！");
	}
});