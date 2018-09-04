if (typeof jQuery !== "undefined" && typeof saveAs !== "undefined") {
    (function($) {
        $.fn.wordExport = function(fileName,event) {
            fileName = typeof fileName !== 'undefined' ? fileName : "jQuery-Word-Export";
            var static = {
                mhtml: {
                    top: "Mime-Version: 1.0\nContent-Base: " + location.href + "\nContent-Type: Multipart/related; boundary=\"NEXT.ITEM-BOUNDARY\";type=\"text/html\"\n\n--NEXT.ITEM-BOUNDARY\nContent-Type: text/html; charset=\"utf-8\"\nContent-Location: " 
                    + location.href + "\n\n<!DOCTYPE html>\n<html xmlns:v='urn:schemas-microsoft-com:vml'xmlns:o='urn:schemas-microsoft-com:office:office'xmlns:w='urn:schemas-microsoft-com:office:word'xmlns:m='http://schemas.microsoft.com/office/2004/12/omml'xmlns='http://www.w3.org/TR/REC-html40'  xmlns='http://www.w3.org/1999/xhtml' >\n_html_</html>",
                    head: "<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><meta name=ProgId content=Word.Document><meta name=Generator content='Microsoft Word 12'><meta name=Originator content='Microsoft Word 12'>\n<style>_styles_\n</style><style>html,body{font-family:Arial;font-size:14px;}</style>\n<!--[if gte mso 9]><xml><w:WordDocument><w:View>Print</w:View><w:TrackMoves>false</w:TrackMoves><w:TrackFormatting/><w:ValidateAgainstSchemas/><w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid><w:IgnoreMixedContent>false</w:IgnoreMixedContent><w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText><w:DoNotPromoteQF/><w:LidThemeOther>EN-US</w:LidThemeOther><w:LidThemeAsian>ZH-CN</w:LidThemeAsian><w:LidThemeComplexScript>X-NONE</w:LidThemeComplexScript><w:Compatibility><w:BreakWrappedTables/><w:SnapToGridInCell/><w:WrapTextWithPunct/><w:UseAsianBreakRules/><w:DontGrowAutofit/><w:SplitPgBreakAndParaMark/><w:DontVertAlignCellWithSp/><w:DontBreakConstrainedForcedTables/><w:DontVertAlignInTxbx/><w:Word11KerningPairs/><w:CachedColBalance/><w:UseFELayout/></w:Compatibility><w:BrowserLevel>MicrosoftInternetExplorer4</w:BrowserLevel><m:mathPr><m:mathFont m:val='Cambria Math'/><m:brkBin m:val='before'/><m:brkBinSub m:val='--'/><m:smallFrac m:val='off'/><m:dispDef/><m:lMargin m:val='0'/> <m:rMargin m:val='0'/><m:defJc m:val='centerGroup'/><m:wrapIndent m:val='1440'/><m:intLim m:val='subSup'/><m:naryLim m:val='undOvr'/></m:mathPr></w:WordDocument></xml><![endif]--></head>\n",
                    body: "<body >_body_</body>"
                }
            };
            var options = {
                maxWidth: 624
            };
            // 在操作之前克隆选定的元素
            var markup = $(this).clone();

            // 从输出中删除隐藏的元素
            markup.each(function() {
                var self = $(this);
                if (self.is(':hidden'))
                    self.remove();
            });

            // 使用数据网址嵌入所有图片
            var images = Array();
            var img = markup.find('img');
            for (var i = 0; i < img.length; i++) {
                // 计算输出图像的尺寸
                var w = Math.min(img[i].width, options.maxWidth);
                var h = img[i].height * (w / img[i].width);
                // 创建用于将图像转换为数据URL的画布
                var canvas = document.createElement("CANVAS");
                canvas.width = w;
                canvas.height = h;
                // 将图像绘制到画布上
                var context = canvas.getContext('2d');
                context.drawImage(img[i], 0, 0, w, h);
                // v获取图像的数据URL编码
                var uri = canvas.toDataURL("image/png");
                $(img[i]).attr("src", img[i].src);
                img[i].width = w;
                img[i].height = h;
                // 将编码的图像保存到数组
                images[i] = {
                    type: uri.substring(uri.indexOf(":") + 1, uri.indexOf(";")),
                    encoding: uri.substring(uri.indexOf(";") + 1, uri.indexOf(",")),
                    location: $(img[i]).attr("src"),
                    data: uri.substring(uri.indexOf(",") + 1)
                };
            }

            // 用图像数据准备mhtml文件的底部
            var mhtmlBottom = "\n";
            for (var i = 0; i < images.length; i++) {
                mhtmlBottom += "--NEXT.ITEM-BOUNDARY\n";
                mhtmlBottom += "Content-Location: " + images[i].location + "\n";
                mhtmlBottom += "Content-Type: " + images[i].type + "\n";
                mhtmlBottom += "Content-Transfer-Encoding: " + images[i].encoding + "\n\n";
                mhtmlBottom += images[i].data + "\n\n";
            }
            mhtmlBottom += "--NEXT.ITEM-BOUNDARY--";
            var styles ="html,body{font-family:微软雅黑,Arial;font-size:14px;}   .news,#hiddenView{width:800px;min-height:500px;margin-bottom:30px}  .news table{margin:0 auto;width:90%} .news table tr td{height:50px}  .news table{border-right:1px solid #000;border-bottom:1px solid #000}  .news table td{border-left:1px solid #000;border-top:1px solid #000}  .chooseTem{background:#00aeef}  .outlineNone{outline:none} .tc{text-align:center}  .pl{padding-left:10px} .s14{font-size:14px} .s12{font-size:12px} .lf{float:left} .rt{float:right} .cf{clear:both} .vtop{vertical-align:top} .f14{font-size:14px} .f16{font-size:16px} .b{font-weight:bold} p,span,td{outline:none} #view .yemei,#view .yejiao{width:83%;height:auto;margin:0 auto} #table_Insurance{width:750px;height:auto;margin:0 auto;font-size:14px;line-height:25px}  #table_Insurance span{float:right;padding-right:10px} #table_Insurance p{padding-left:10px} .f29{font-size:29px} .f18{font-size:18px} .m30{margin-top:30px} .m30b{margin-bottom:30px}  .m60b{margin-bottom:60px} .red{color:red;} .noteditDom{display:none;";

            // 汇总文件的部分
            var fileContent = static.mhtml.top.replace("_html_", static.mhtml.head.replace("_styles_", styles) + static.mhtml.body.replace("_body_", markup.html())) + mhtmlBottom;
          
            // 用文件内容创建一个Blob
            var blob = new Blob([fileContent], {
                type: "application/msword;charset=utf-8"
            });
            if(event == "Preview"){
            	
            	downFile(blob, fileName);
                function downFile(blob, fileName) {
                    if (window.navigator.msSaveOrOpenBlob) {
                        navigator.msSaveBlob(blob, fileName);
                    } else {
                        var link = document.createElement('a');
                        link.innerText = "shipping instruction.doc";
                        $(".annexHref").empty();
                        $(".annexHref").append(link);
                    }
                }
                return blob;
            }
            else{
            	saveAs(blob, fileName + ".doc");
            }
        };
    })(jQuery);
} else {
    if (typeof jQuery === "undefined") {
        console.error("jQuery Word Export: missing dependency (jQuery)");
    }
    if (typeof saveAs === "undefined") {
        console.error("jQuery Word Export: missing dependency (FileSaver.js)");
    }
}