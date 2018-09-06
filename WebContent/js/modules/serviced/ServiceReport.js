/*

jSignature v2 "2016-11-05T00:40" "commit ID d9ac2f271cfdf479d005a449193e09985043cafa"
Copyright (c) 2012 Willow Systems Corp http://willow-systems.com
Copyright (c) 2010 Brinley Ang http://www.unbolt.net
MIT License <http://www.opensource.org/licenses/mit-license.php>


Simplify.js BSD 
(c) 2012, Vladimir Agafonkin
mourner.github.com/simplify-js


base64 encoder
MIT, GPL
http://phpjs.org/functions/base64_encode
+   original by: Tyler Akins (http://rumkin.com)
+   improved by: Bayron Guevara
+   improved by: Thunder.m
+   improved by: Kevin van Zonneveld (http://kevin.vanzonneveld.net)
+   bugfixed by: Pellentesque Malesuada
+   improved by: Kevin van Zonneveld (http://kevin.vanzonneveld.net)
+   improved by: Rafal Kukawski (http://kukawski.pl)


jSignature v2 jSignature's Undo Button and undo functionality plugin


jSignature v2 jSignature's custom "base30" format export and import plugins.


jSignature v2 SVG export plugin.

*/
(function(){function t(b){var a,c=b.css("color"),e;b=b[0];for(var h=!1;b&&!e&&!h;){try{a=$(b).css("background-color")}catch(l){a="transparent"}"transparent"!==a&&"rgba(0, 0, 0, 0)"!==a&&(e=a);h=b.body;b=b.parentNode}b=/rgb[a]*\((\d+),\s*(\d+),\s*(\d+)/;var h=/#([AaBbCcDdEeFf\d]{2})([AaBbCcDdEeFf\d]{2})([AaBbCcDdEeFf\d]{2})/,m;a=void 0;(a=c.match(b))?m={r:parseInt(a[1],10),g:parseInt(a[2],10),b:parseInt(a[3],10)}:(a=c.match(h))&&(m={r:parseInt(a[1],16),g:parseInt(a[2],16),b:parseInt(a[3],16)});var d;
e?(a=void 0,(a=e.match(b))?d={r:parseInt(a[1],10),g:parseInt(a[2],10),b:parseInt(a[3],10)}:(a=e.match(h))&&(d={r:parseInt(a[1],16),g:parseInt(a[2],16),b:parseInt(a[3],16)})):d=m?127<Math.max.apply(null,[m.r,m.g,m.b])?{r:0,g:0,b:0}:{r:255,g:255,b:255}:{r:255,g:255,b:255};a=function(a){return"rgb("+[a.r,a.g,a.b].join(", ")+")"};m&&d?(b=Math.max.apply(null,[m.r,m.g,m.b]),m=Math.max.apply(null,[d.r,d.g,d.b]),m=Math.round(m+-.75*(m-b)),m={r:m,g:m,b:m}):m?(m=Math.max.apply(null,[m.r,m.g,m.b]),b=1,127<m&&
(b=-1),m=Math.round(m+96*b),m={r:m,g:m,b:m}):m={r:191,g:191,b:191};return{color:c,"background-color":d?a(d):e,"decor-color":a(m)}}function k(b,a){this.x=b;this.y=a;this.reverse=function(){return new this.constructor(-1*this.x,-1*this.y)};this._length=null;this.getLength=function(){this._length||(this._length=Math.sqrt(Math.pow(this.x,2)+Math.pow(this.y,2)));return this._length};var c=function(a){return Math.round(a/Math.abs(a))};this.resizeTo=function(a){if(0===this.x&&0===this.y)this._length=0;else if(0===
this.x)this._length=a,this.y=a*c(this.y);else if(0===this.y)this._length=a,this.x=a*c(this.x);else{var b=Math.abs(this.y/this.x),e=Math.sqrt(Math.pow(a,2)/(1+Math.pow(b,2))),b=b*e;this._length=a;this.x=e*c(this.x);this.y=b*c(this.y)}return this};this.angleTo=function(a){var b=this.getLength()*a.getLength();return 0===b?0:Math.acos(Math.min(Math.max((this.x*a.x+this.y*a.y)/b,-1),1))/Math.PI}}function g(b,a){this.x=b;this.y=a;this.getVectorToCoordinates=function(a,b){return new k(a-this.x,b-this.y)};
this.getVectorFromCoordinates=function(a,b){return this.getVectorToCoordinates(a,b).reverse()};this.getVectorToPoint=function(a){return new k(a.x-this.x,a.y-this.y)};this.getVectorFromPoint=function(a){return this.getVectorToPoint(a).reverse()}}function n(b,a,c,e,h){this.data=b;this.context=a;if(b.length)for(var m=b.length,d,l,f=0;f<m;f++){d=b[f];l=d.x.length;c.call(a,d);for(var p=1;p<l;p++)e.call(a,d,p);h.call(a,d)}this.changed=function(){};this.startStrokeFn=c;this.addToStrokeFn=e;this.endStrokeFn=
h;this.inStroke=!1;this._stroke=this._lastPoint=null;this.startStroke=function(a){if(a&&"number"==typeof a.x&&"number"==typeof a.y){this._stroke={x:[a.x],y:[a.y]};this.data.push(this._stroke);this._lastPoint=a;this.inStroke=!0;var b=this._stroke,c=this.startStrokeFn,e=this.context;setTimeout(function(){c.call(e,b)},3);return a}return null};this.addToStroke=function(a){if(this.inStroke&&"number"===typeof a.x&&"number"===typeof a.y&&4<Math.abs(a.x-this._lastPoint.x)+Math.abs(a.y-this._lastPoint.y)){var b=
this._stroke.x.length;this._stroke.x.push(a.x);this._stroke.y.push(a.y);this._lastPoint=a;var c=this._stroke,e=this.addToStrokeFn,h=this.context;setTimeout(function(){e.call(h,c,b)},3);return a}return null};this.endStroke=function(){var a=this.inStroke;this.inStroke=!1;this._lastPoint=null;if(a){var b=this._stroke,c=this.endStrokeFn,e=this.context,h=this.changed;setTimeout(function(){c.call(e,b);h.call(e)},3);return!0}return null}}function r(b,a,c,e){if("ratio"===a||"%"===a.split("")[a.length-1])this.eventTokens[c+
".parentresized"]=e.subscribe(c+".parentresized",function(a,m,d,l){return function(){var h=m.width();if(h!==d){for(var l in a)a.hasOwnProperty(l)&&(e.unsubscribe(a[l]),delete a[l]);var f=b.settings;b.$parent.children().remove();for(l in b)b.hasOwnProperty(l)&&delete b[l];l=f.data;var h=1*h/d,u=[],A,E,g,k,r,n;E=0;for(g=l.length;E<g;E++){n=l[E];A={x:[],y:[]};k=0;for(r=n.x.length;k<r;k++)A.x.push(n.x[k]*h),A.y.push(n.y[k]*h);u.push(A)}f.data=u;m[c](f)}}}(this.eventTokens,this.$parent,this.$parent.width(),
1*this.canvas.width/this.canvas.height))}function x(b,a,c){var e=this.$parent=$(b);b=this.eventTokens={};this.events=new v(this);var h=$.fn.jSignature("globalEvents"),d={width:"ratio",height:"ratio",sizeRatio:4,color:"#000","background-color":"#fff","decor-color":"#eee",lineWidth:0,minFatFingerCompensation:-10,showUndoButton:!1,readOnly:!1,data:[],signatureLine:!1};$.extend(d,t(e));a&&$.extend(d,a);this.settings=d;for(var f in c)c.hasOwnProperty(f)&&c[f].call(this,f);this.events.publish("jSignature.initializing");
this.$controlbarUpper=$('<div style="padding:0 !important; margin:0 !important;width: 100% !important; height: 0 !important; -ms-touch-action: none; touch-action: none;margin-top:-1em !important; margin-bottom:1em !important;"></div>').appendTo(e);this.isCanvasEmulator=!1;a=this.canvas=this.initializeCanvas(d);c=$(a);this.$controlbarLower=$('<div style="padding:0 !important; margin:0 !important;width: 100% !important; height: 0 !important; -ms-touch-action: none; touch-action: none;margin-top:-1.5em !important; margin-bottom:1.5em !important; position: relative;"></div>').appendTo(e);
this.canvasContext=a.getContext("2d");c.data("jSignature.this",this);d.lineWidth=function(a,b){return a?a:Math.max(Math.round(b/400),2)}(d.lineWidth,a.width);this.lineCurveThreshold=3*d.lineWidth;d.cssclass&&""!=$.trim(d.cssclass)&&c.addClass(d.cssclass);this.fatFingerCompensation=0;e=function(a){var b,c,e=function(e){e=e.changedTouches&&0<e.changedTouches.length?e.changedTouches[0]:e;return new g(Math.round(e.pageX+b),Math.round(e.pageY+c)+a.fatFingerCompensation)},d=new w(750,function(){a.dataEngine.endStroke()});
this.drawEndHandler=function(b){if(!a.settings.readOnly){try{b.preventDefault()}catch(F){}d.clear();a.dataEngine.endStroke()}};this.drawStartHandler=function(h){if(!a.settings.readOnly){h.preventDefault();var m=$(a.canvas).offset();b=-1*m.left;c=-1*m.top;a.dataEngine.startStroke(e(h));d.kick()}};this.drawMoveHandler=function(b){a.settings.readOnly||(b.preventDefault(),a.dataEngine.inStroke&&(a.dataEngine.addToStroke(e(b)),d.kick()))};return this}.call({},this);(function(a,b,c){var e=this.canvas,h=
$(e);this.isCanvasEmulator?(h.bind("mousemove.jSignature",c),h.bind("mouseup.jSignature",a),h.bind("mousedown.jSignature",b)):(e.ontouchstart=function(h){e.onmousedown=e.onmouseup=e.onmousemove=void 0;this.fatFingerCompensation=d.minFatFingerCompensation&&-3*d.lineWidth>d.minFatFingerCompensation?-3*d.lineWidth:d.minFatFingerCompensation;b(h);e.ontouchend=a;e.ontouchstart=b;e.ontouchmove=c},e.onmousedown=function(d){e.ontouchstart=e.ontouchend=e.ontouchmove=void 0;b(d);e.onmousedown=b;e.onmouseup=
a;e.onmousemove=c},window.navigator.msPointerEnabled&&(e.onmspointerdown=b,e.onmspointerup=a,e.onmspointermove=c))}).call(this,e.drawEndHandler,e.drawStartHandler,e.drawMoveHandler);b["jSignature.windowmouseup"]=h.subscribe("jSignature.windowmouseup",e.drawEndHandler);this.events.publish("jSignature.attachingEventHandlers");r.call(this,this,d.width.toString(10),"jSignature",h);this.resetCanvas(d.data);this.events.publish("jSignature.initialized");return this}function y(b){if(b.getContext)return!1;
var a=b.ownerDocument.parentWindow,c=a.FlashCanvas?b.ownerDocument.parentWindow.FlashCanvas:"undefined"===typeof FlashCanvas?void 0:FlashCanvas;if(c){b=c.initElement(b);c=1;a&&a.screen&&a.screen.deviceXDPI&&a.screen.logicalXDPI&&(c=1*a.screen.deviceXDPI/a.screen.logicalXDPI);if(1!==c)try{$(b).children("object").get(0).resize(Math.ceil(b.width*c),Math.ceil(b.height*c)),b.getContext("2d").scale(c,c)}catch(e){}return!0}throw Error("Canvas element does not support 2d context. jSignature cannot proceed.");
}var w=function(b,a){var c;this.kick=function(){clearTimeout(c);c=setTimeout(a,b)};this.clear=function(){clearTimeout(c)};return this},v=function(b){this.topics={};this.context=b?b:this;this.publish=function(a,b,e,d){if(this.topics[a]){var c=this.topics[a],h=Array.prototype.slice.call(arguments,1),f=[],g=[],p,q,u,A;q=0;for(u=c.length;q<u;q++)A=c[q],p=A[0],A[1]&&(A[0]=function(){},f.push(q)),g.push(p);q=0;for(u=f.length;q<u;q++)c.splice(f[q],1);q=0;for(u=g.length;q<u;q++)g[q].apply(this.context,h)}};
this.subscribe=function(a,b,e){this.topics[a]?this.topics[a].push([b,e]):this.topics[a]=[[b,e]];return{topic:a,callback:b}};this.unsubscribe=function(a){if(this.topics[a.topic])for(var b=this.topics[a.topic],e=0,d=b.length;e<d;e++)b[e]&&b[e][0]===a.callback&&b.splice(e,1)}},z=function(b,a,c,e,d){b.beginPath();b.moveTo(a,c);b.lineTo(e,d);b.closePath();b.stroke()},D=function(b){var a=this.canvasContext,c=b.x[0];b=b.y[0];var e=this.settings.lineWidth,d=a.fillStyle;a.fillStyle=a.strokeStyle;a.fillRect(c+
e/-2,b+e/-2,e,e);a.fillStyle=d},f=function(b,a){var c=new g(b.x[a-1],b.y[a-1]),e=new g(b.x[a],b.y[a]),d=c.getVectorToPoint(e);if(1<a){var m=new g(b.x[a-2],b.y[a-2]),f=m.getVectorToPoint(c),l;if(f.getLength()>this.lineCurveThreshold){l=2<a?(new g(b.x[a-3],b.y[a-3])).getVectorToPoint(m):new k(0,0);var n=.35*f.getLength(),p=f.angleTo(l.reverse()),q=d.angleTo(f.reverse());l=(new k(l.x+f.x,l.y+f.y)).resizeTo(Math.max(.05,p)*n);var u=(new k(f.x+d.x,f.y+d.y)).reverse().resizeTo(Math.max(.05,q)*n),f=this.canvasContext,
n=m.x,q=m.y,p=c.x,A=c.y,r=m.x+l.x,m=m.y+l.y;l=c.x+u.x;u=c.y+u.y;f.beginPath();f.moveTo(n,q);f.bezierCurveTo(r,m,l,u,p,A);f.closePath();f.stroke()}}d.getLength()<=this.lineCurveThreshold&&z(this.canvasContext,c.x,c.y,e.x,e.y)},d=function(b){var a=b.x.length-1;if(0<a){var c=new g(b.x[a],b.y[a]),e=new g(b.x[a-1],b.y[a-1]),d=e.getVectorToPoint(c);if(d.getLength()>this.lineCurveThreshold)if(1<a){b=(new g(b.x[a-2],b.y[a-2])).getVectorToPoint(e);var f=(new k(b.x+d.x,b.y+d.y)).resizeTo(d.getLength()/2),d=
this.canvasContext;b=e.x;var a=e.y,E=c.x,l=c.y,n=e.x+f.x,e=e.y+f.y,f=c.x,c=c.y;d.beginPath();d.moveTo(b,a);d.bezierCurveTo(n,e,f,c,E,l);d.closePath();d.stroke()}else z(this.canvasContext,e.x,e.y,c.x,c.y)}};x.prototype.resetCanvas=function(b,a){var c=this.canvas,e=this.settings,h=this.canvasContext,m=this.isCanvasEmulator,g=c.width,l=c.height;a||h.clearRect(0,0,g+30,l+30);h.shadowColor=h.fillStyle=e["background-color"];m&&h.fillRect(0,0,g+30,l+30);h.lineWidth=Math.ceil(parseInt(e.lineWidth,10));h.lineCap=
h.lineJoin="round";if(e.signatureLine){if(null!=e["decor-color"]){h.strokeStyle=e["decor-color"];h.shadowOffsetX=0;h.shadowOffsetY=0;var k=Math.round(l/5);z(h,1.5*k,l-k,g-1.5*k,l-k)}m||(h.shadowColor=h.strokeStyle,h.shadowOffsetX=.5*h.lineWidth,h.shadowOffsetY=-.6*h.lineWidth,h.shadowBlur=0)}h.strokeStyle=e.color;b||(b=[]);h=this.dataEngine=new n(b,this,D,f,d);e.data=b;$(c).data("jSignature.data",b).data("jSignature.settings",e);h.changed=function(a,b,c){return function(){b.publish(c+".change");a.trigger("change")}}(this.$parent,
this.events,"jSignature");h.changed();return!0};x.prototype.initializeCanvas=function(b){var a=document.createElement("canvas"),c=$(a);b.width===b.height&&"ratio"===b.height&&(b.width="100%");c.css({margin:0,padding:0,border:"none",height:"ratio"!==b.height&&b.height?b.height.toString(10):1,width:"ratio"!==b.width&&b.width?b.width.toString(10):1,"-ms-touch-action":"none","touch-action":"none","background-color":b["background-color"]});c.appendTo(this.$parent);"ratio"===b.height?c.css("height",Math.round(c.width()/
b.sizeRatio)):"ratio"===b.width&&c.css("width",Math.round(c.height()*b.sizeRatio));c.addClass("jSignature");a.width=c.width();a.height=c.height();this.isCanvasEmulator=y(a);a.onselectstart=function(a){a&&a.preventDefault&&a.preventDefault();a&&a.stopPropagation&&a.stopPropagation();return!1};return a};(function(b){function a(a,b,c){var d=new Image,e=this;d.onload=function(){var a=e.getContext("2d"),b=a.shadowColor;a.shadowColor="transparent";a.drawImage(d,0,0,d.width<e.width?d.width:e.width,d.height<
e.height?d.height:e.height);a.shadowColor=b};d.src="data:"+b+","+a}function c(a,b){this.find("canvas.jSignature").add(this.filter("canvas.jSignature")).data("jSignature.this").resetCanvas(a,b);return this}function d(a,b){if(void 0===b&&"string"===typeof a&&"data:"===a.substr(0,5)&&(b=a.slice(5).split(",")[0],a=a.slice(6+b.length),b===a))return;var c=this.find("canvas.jSignature").add(this.filter("canvas.jSignature"));if(l.hasOwnProperty(b))0!==c.length&&l[b].call(c[0],a,b,function(a){return function(){return a.resetCanvas.apply(a,
arguments)}}(c.data("jSignature.this")));else throw Error("jSignature is unable to find import plugin with for format '"+String(b)+"'");return this}var h=new v;(function(a,b,c,d){var e,h=function(){a.publish(b+".parentresized")};c(d).bind("resize."+b,function(){e&&clearTimeout(e);e=setTimeout(h,500)}).bind("mouseup."+b,function(c){a.publish(b+".windowmouseup")})})(h,"jSignature",$,b);var f={},g={"default":function(a){return this.toDataURL()},"native":function(a){return a},image:function(a){a=this.toDataURL();
if("string"===typeof a&&4<a.length&&"data:"===a.slice(0,5)&&-1!==a.indexOf(",")){var b=a.indexOf(",");return[a.slice(5,b),a.substr(b+1)]}return[]}},l={"native":function(a,b,c){c(a)},image:a,"image/png;base64":a,"image/jpeg;base64":a,"image/jpg;base64":a},k=function(a){var b=!1;for(a=a.parentNode;a&&!b;)b=a.body,a=a.parentNode;return!b},p={"export":g,"import":l,instance:f},n={init:function(a){return this.each(function(){k(this)||new x(this,a,f)})},destroy:function(){return this.each(function(){if(!k(this)){var a=
$(this).find("canvas").data("jSignature.this");a&&(a.$controlbarLower.remove(),a.$controlbarUpper.remove(),$(a.canvas).remove())}})},getSettings:function(){return this.find("canvas.jSignature").add(this.filter("canvas.jSignature")).data("jSignature.this").settings},isModified:function(){return null!==this.find("canvas.jSignature").add(this.filter("canvas.jSignature")).data("jSignature.this").dataEngine._stroke},updateSetting:function(a,b,c){var d=this.find("canvas.jSignature").add(this.filter("canvas.jSignature")).data("jSignature.this");
d.settings[a]=b;d.resetCanvas(c?null:d.settings.data,!0);return d.settings[a]},clear:c,reset:c,addPlugin:function(a,b,c){p.hasOwnProperty(a)&&(p[a][b]=c);return this},listPlugins:function(a){var b=[];if(p.hasOwnProperty(a)){a=p[a];for(var c in a)a.hasOwnProperty(c)&&b.push(c)}return b},getData:function(a){var b=this.find("canvas.jSignature").add(this.filter("canvas.jSignature"));void 0===a&&(a="default");if(0!==b.length&&g.hasOwnProperty(a))return g[a].call(b.get(0),b.data("jSignature.data"),b.data("jSignature.settings"))},
importData:d,setData:d,globalEvents:function(){return h},disable:function(){this.find("input").attr("disabled",1);this.find("canvas.jSignature").addClass("disabled").data("jSignature.this").settings.readOnly=!0},enable:function(){this.find("input").removeAttr("disabled");this.find("canvas.jSignature").removeClass("disabled").data("jSignature.this").settings.readOnly=!1},events:function(){return this.find("canvas.jSignature").add(this.filter("canvas.jSignature")).data("jSignature.this").events}};$.fn.jSignature=
function(a){if(a&&"object"!==typeof a){if("string"===typeof a&&n[a])return n[a].apply(this,Array.prototype.slice.call(arguments,1));$.error("Method "+String(a)+" does not exist on jQuery.jSignature")}else return n.init.apply(this,arguments)}})(window)})();
(function(){function t(k,g,n){k=k.call(this);(function(g,k,n){g.events.subscribe(n+".change",function(){g.dataEngine.data.length?k.show():k.hide()})})(this,k,g);(function(g,k,n){var r=n+".undo";k.bind("click",function(){g.events.publish(r)});g.events.subscribe(r,function(){var k=g.dataEngine.data;k.length&&(k.pop(),g.resetCanvas(k))})})(this,k,this.events.topics.hasOwnProperty(g+".undo")?n:g)}$.fn.jSignature("addPlugin","instance","UndoButton",function(k){this.events.subscribe("jSignature.attachingEventHandlers",
function(){if(this.settings[k]){var g=this.settings[k];"function"!==typeof g&&(g=function(){var g=$('<input type="button" value="Undo last stroke" style="position:absolute;display:none;margin:0 !important;top:auto" />').appendTo(this.$controlbarLower),k=g.width();g.css("left",Math.round((this.canvas.width-k)/2));k!==g.width()&&g.width(k);return g});t.call(this,g,"jSignature",k)}})})})();
(function(){for(var t={},k={},g="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWX".split(""),n=g.length/2,r=n-1;-1<r;r--)t[g[r]]=g[r+n],k[g[r+n]]=g[r];var x=function(d){d=d.split("");for(var b=d.length,a=1;a<b;a++)d[a]=t[d[a]];return d.join("")},y=function(d){for(var b=[],a=0,c=1,e=d.length,h,f,g=0;g<e;g++)h=Math.round(d[g]),f=h-a,a=h,0>f&&0<c?(c=-1,b.push("Z")):0<f&&0>c&&(c=1,b.push("Y")),h=Math.abs(f),h>=n?b.push(x(h.toString(n))):b.push(h.toString(n));return b.join("")},w=function(d){var b=
[];d=d.split("");for(var a=d.length,c,e=1,f=[],m=0,g=0;g<a;g++)c=d[g],c in t||"Z"===c||"Y"===c?(0!==f.length&&(f=parseInt(f.join(""),n)*e+m,b.push(f),m=f),"Z"===c?(e=-1,f=[]):"Y"===c?(e=1,f=[]):f=[c]):f.push(k[c]);b.push(parseInt(f.join(""),n)*e+m);return b},v=function(d){for(var b=[],a=d.length,c,e=0;e<a;e++)c=d[e],b.push(y(c.x)),b.push(y(c.y));return b.join("_")},z=function(d){var b=[];d=d.split("_");for(var a=d.length/2,c=0;c<a;c++)b.push({x:w(d[2*c]),y:w(d[2*c+1])});return b},D=function(d){return["image/jsignature;base30",
v(d)]},f=function(d,b,a){"string"===typeof d&&("image/jsignature;base30"===d.substring(0,23).toLowerCase()&&(d=d.substring(24)),a(z(d)))};if(null==this.jQuery)throw Error("We need jQuery for some of the functionality. jQuery is not detected. Failing to initialize...");(function(d){d=d.fn.jSignature;d("addPlugin","export","base30",D);d("addPlugin","export","image/jsignature;base30",D);d("addPlugin","import","base30",f);d("addPlugin","import","image/jsignature;base30",f)})(this.jQuery);this.jSignatureDebug&&
(this.jSignatureDebug.base30={remapTailChars:x,compressstrokeleg:y,uncompressstrokeleg:w,compressstrokes:v,uncompressstrokes:z,charmap:t})}).call("undefined"!==typeof window?window:this);
(function(){function t(f,d){this.x=f;this.y=d;this.reverse=function(){return new this.constructor(-1*this.x,-1*this.y)};this._length=null;this.getLength=function(){this._length||(this._length=Math.sqrt(Math.pow(this.x,2)+Math.pow(this.y,2)));return this._length};var b=function(a){return Math.round(a/Math.abs(a))};this.resizeTo=function(a){if(0===this.x&&0===this.y)this._length=0;else if(0===this.x)this._length=a,this.y=a*b(this.y);else if(0===this.y)this._length=a,this.x=a*b(this.x);else{var c=Math.abs(this.y/
this.x),d=Math.sqrt(Math.pow(a,2)/(1+Math.pow(c,2))),c=c*d;this._length=a;this.x=d*b(this.x);this.y=c*b(this.y)}return this};this.angleTo=function(a){var b=this.getLength()*a.getLength();return 0===b?0:Math.acos(Math.min(Math.max((this.x*a.x+this.y*a.y)/b,-1),1))/Math.PI}}function k(f,d){this.x=f;this.y=d;this.getVectorToCoordinates=function(b,a){return new t(b-this.x,a-this.y)};this.getVectorFromCoordinates=function(b,a){return this.getVectorToCoordinates(b,a).reverse()};this.getVectorToPoint=function(b){return new t(b.x-
this.x,b.y-this.y)};this.getVectorFromPoint=function(b){return this.getVectorToPoint(b).reverse()}}function g(f,d){var b=Math.pow(10,d);return Math.round(f*b)/b}function n(f,d,b){d+=1;var a=new k(f.x[d-1],f.y[d-1]),c=new k(f.x[d],f.y[d]),c=a.getVectorToPoint(c),e=new k(f.x[d-2],f.y[d-2]),a=e.getVectorToPoint(a);return a.getLength()>b?(b=2<d?(new k(f.x[d-3],f.y[d-3])).getVectorToPoint(e):new t(0,0),f=.35*a.getLength(),e=a.angleTo(b.reverse()),d=c.angleTo(a.reverse()),b=(new t(b.x+a.x,b.y+a.y)).resizeTo(Math.max(.05,
e)*f),c=(new t(a.x+c.x,a.y+c.y)).reverse().resizeTo(Math.max(.05,d)*f),c=new t(a.x+c.x,a.y+c.y),["c",g(b.x,2),g(b.y,2),g(c.x,2),g(c.y,2),g(a.x,2),g(a.y,2)]):["l",g(a.x,2),g(a.y,2)]}function r(f,d){var b=f.x.length-1,a=new k(f.x[b],f.y[b]),c=new k(f.x[b-1],f.y[b-1]),a=c.getVectorToPoint(a);if(1<b&&a.getLength()>d){var b=(new k(f.x[b-2],f.y[b-2])).getVectorToPoint(c),c=a.angleTo(b.reverse()),e=.35*a.getLength(),b=(new t(b.x+a.x,b.y+a.y)).resizeTo(Math.max(.05,c)*e);return["c",g(b.x,2),g(b.y,2),g(a.x,
2),g(a.y,2),g(a.x,2),g(a.y,2)]}return["l",g(a.x,2),g(a.y,2)]}function x(f,d,b){d=["M",g(f.x[0]-d,2),g(f.y[0]-b,2)];b=1;for(var a=f.x.length-1;b<a;b++)d.push.apply(d,n(f,b,1));0<a?d.push.apply(d,r(f,b,1)):0===a&&d.push.apply(d,["l",1,1]);return d.join(" ")}function y(f){for(var d=[],b=[["fill",void 0,"none"],["stroke","color","#000000"],["stroke-width","lineWidth",2],["stroke-linecap",void 0,"round"],["stroke-linejoin",void 0,"round"]],a=b.length-1;0<=a;a--){var c=b[a][1],e=b[a][2];d.push(b[a][0]+
'="'+(c in f&&f[c]?f[c]:e)+'"')}return d.join(" ")}function w(f,d){var b=['<?xml version="1.0" encoding="UTF-8" standalone="no"?>','<!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN" "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd">'],a,c=f.length,e,h=[],g=[],k=e=a=0,l=0,n=[];if(0!==c){for(a=0;a<c;a++){k=f[a];l=[];e={x:[],y:[]};var p,q;p=0;for(q=k.x.length;p<q;p++)l.push({x:k.x[p],y:k.y[p]});l=simplify(l,.7,!0);p=0;for(q=l.length;p<q;p++)e.x.push(l[p].x),e.y.push(l[p].y);n.push(e);h=h.concat(e.x);g=
g.concat(e.y)}c=Math.min.apply(null,h)-1;a=Math.max.apply(null,h)+1;h=Math.min.apply(null,g)-1;g=Math.max.apply(null,g)+1;k=0>c?0:c;l=0>h?0:h;a-=c;e=g-h}b.push('<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="'+a.toString()+'" height="'+e.toString()+'">');a=0;for(c=n.length;a<c;a++)e=n[a],b.push("<path "+y(d)+' d="'+x(e,k,l)+'"/>');b.push("</svg>");return b.join("")}function v(f,d){return["image/svg+xml",w(f,d)]}function z(f,d){return["image/svg+xml;base64",D(w(f,d))]}(function(f,d){"use strict";
(typeof exports!=d+""?exports:f).simplify=function(b,a,c){a=a!==d?a*a:1;if(!c){var e=b.length,f,g=b[0],k=[g];for(c=1;c<e;c++){f=b[c];var l=f.x-g.x,n=f.y-g.y;l*l+n*n>a&&(k.push(f),g=f)}b=(g!==f&&k.push(f),k)}f=b;c=f.length;var e=new (typeof Uint8Array!=d+""?Uint8Array:Array)(c),g=0,k=c-1,p,q,r=[],t=[],z=[];for(e[g]=e[k]=1;k;){n=0;for(l=g+1;l<k;l++){p=f[l];var B=f[g],x=f[k],v=B.x,w=B.y,B=x.x-v,C=x.y-w,y;if(0!==B||0!==C)y=((p.x-v)*B+(p.y-w)*C)/(B*B+C*C),1<y?(v=x.x,w=x.y):0<y&&(v+=B*y,w+=C*y);p=(B=p.x-
v,C=p.y-w,B*B+C*C);p>n&&(q=l,n=p)}n>a&&(e[q]=1,r.push(g),t.push(q),r.push(q),t.push(k));g=r.pop();k=t.pop()}for(l=0;l<c;l++)e[l]&&z.push(f[l]);return b=z,b}})(window);if("function"!==typeof D)var D=function(f){var d="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".split(""),b,a,c,e,g=0,k=0,n=[];do b=f.charCodeAt(g++),a=f.charCodeAt(g++),c=f.charCodeAt(g++),e=b<<16|a<<8|c,b=e>>18&63,a=e>>12&63,c=e>>6&63,e&=63,n[k++]=d[b]+d[a]+d[c]+d[e];while(g<f.length);d=n.join("");f=f.length%3;
return(f?d.slice(0,f-3):d)+"===".slice(f||3)};if("undefined"===typeof $)throw Error("We need jQuery for some of the functionality. jQuery is not detected. Failing to initialize...");(function(f){f=f.fn.jSignature;f("addPlugin","export","svg",v);f("addPlugin","export","image/svg+xml",v);f("addPlugin","export","svgbase64",z);f("addPlugin","export","image/svg+xml;base64",z)})($)})();
// colResizable 1.6 - a jQuery plugin by Alvaro Prieto Lauroba http://www.bacubacu.com/colresizable/

!function(t){var e,i=t(document),r=t("head"),o=null,s={},d=0,n="id",a="px",l="JColResizer",c="JCLRFlex",f=parseInt,h=Math,p=navigator.userAgent.indexOf("Trident/4.0")>0;try{e=sessionStorage}catch(g){}r.append("<style type='text/css'>  .JColResizer{table-layout:fixed;} .JColResizer > tbody > tr > td, .JColResizer > tbody > tr > th{overflow:hidden;padding-left:0!important; padding-right:0!important;}  .JCLRgrips{ height:0px; position:relative;} .JCLRgrip{margin-left:-5px; position:absolute; z-index:5; } .JCLRgrip .JColResizer{position:absolute;background-color:red;filter:alpha(opacity=1);opacity:0;width:10px;height:100%;cursor: e-resize;top:0px} .JCLRLastGrip{position:absolute; width:1px; } .JCLRgripDrag{ border-left:1px dotted black;	} .JCLRFlex{width:auto!important;} .JCLRgrip.JCLRdisabledGrip .JColResizer{cursor:default; display:none;}</style>");var u=function(e,i){var o=t(e);if(o.opt=i,o.mode=i.resizeMode,o.dc=o.opt.disabledColumns,o.opt.disable)return w(o);var a=o.id=o.attr(n)||l+d++;o.p=o.opt.postbackSafe,!o.is("table")||s[a]&&!o.opt.partialRefresh||("e-resize"!==o.opt.hoverCursor&&r.append("<style type='text/css'>.JCLRgrip .JColResizer:hover{cursor:"+o.opt.hoverCursor+"!important}</style>"),o.addClass(l).attr(n,a).before('<div class="JCLRgrips"/>'),o.g=[],o.c=[],o.w=o.width(),o.gc=o.prev(),o.f=o.opt.fixed,i.marginLeft&&o.gc.css("marginLeft",i.marginLeft),i.marginRight&&o.gc.css("marginRight",i.marginRight),o.cs=f(p?e.cellSpacing||e.currentStyle.borderSpacing:o.css("border-spacing"))||2,o.b=f(p?e.border||e.currentStyle.borderLeftWidth:o.css("border-left-width"))||1,s[a]=o,v(o))},w=function(t){var e=t.attr(n),t=s[e];t&&t.is("table")&&(t.removeClass(l+" "+c).gc.remove(),delete s[e])},v=function(i){var r=i.find(">thead>tr:first>th,>thead>tr:first>td");r.length||(r=i.find(">tbody>tr:first>th,>tr:first>th,>tbody>tr:first>td, >tr:first>td")),r=r.filter(":visible"),i.cg=i.find("col"),i.ln=r.length,i.p&&e&&e[i.id]&&m(i,r),r.each(function(e){var r=t(this),o=-1!=i.dc.indexOf(e),s=t(i.gc.append('<div class="JCLRgrip"></div>')[0].lastChild);s.append(o?"":i.opt.gripInnerHtml).append('<div class="'+l+'"></div>'),e==i.ln-1&&(s.addClass("JCLRLastGrip"),i.f&&s.html("")),s.bind("touchstart mousedown",J),o?s.addClass("JCLRdisabledGrip"):s.removeClass("JCLRdisabledGrip").bind("touchstart mousedown",J),s.t=i,s.i=e,s.c=r,r.w=r.width(),i.g.push(s),i.c.push(r),r.width(r.w).removeAttr("width"),s.data(l,{i:e,t:i.attr(n),last:e==i.ln-1})}),i.cg.removeAttr("width"),i.find("td, th").not(r).not("table th, table td").each(function(){t(this).removeAttr("width")}),i.f||i.removeAttr("width").addClass(c),C(i)},m=function(t,i){var r,o,s=0,d=0,n=[];if(i){if(t.cg.removeAttr("width"),t.opt.flush)return void(e[t.id]="");for(r=e[t.id].split(";"),o=r[t.ln+1],!t.f&&o&&(t.width(o*=1),t.opt.overflow&&(t.css("min-width",o+a),t.w=o));d<t.ln;d++)n.push(100*r[d]/r[t.ln]+"%"),i.eq(d).css("width",n[d]);for(d=0;d<t.ln;d++)t.cg.eq(d).css("width",n[d])}else{for(e[t.id]="";d<t.c.length;d++)r=t.c[d].width(),e[t.id]+=r+";",s+=r;e[t.id]+=s,t.f||(e[t.id]+=";"+t.width())}},C=function(t){t.gc.width(t.w);for(var e=0;e<t.ln;e++){var i=t.c[e];t.g[e].css({left:i.offset().left-t.offset().left+i.outerWidth(!1)+t.cs/2+a,height:t.opt.headerOnly?t.c[0].outerHeight(!1):t.outerHeight(!1)})}},b=function(t,e,i){var r=o.x-o.l,s=t.c[e],d=t.c[e+1],n=s.w+r,l=d.w-r;s.width(n+a),t.cg.eq(e).width(n+a),t.f?(d.width(l+a),t.cg.eq(e+1).width(l+a)):t.opt.overflow&&t.css("min-width",t.w+r),i&&(s.w=n,d.w=t.f?l:d.w)},R=function(e){var i=t.map(e.c,function(t){return t.width()});e.width(e.w=e.width()).removeClass(c),t.each(e.c,function(t,e){e.width(i[t]).w=i[t]}),e.addClass(c)},x=function(t){if(o){var e=o.t,i=t.originalEvent.touches,r=i?i[0].pageX:t.pageX,s=r-o.ox+o.l,d=e.opt.minWidth,n=o.i,l=1.5*e.cs+d+e.b,c=n==e.ln-1,f=n?e.g[n-1].position().left+e.cs+d:l,p=e.f?n==e.ln-1?e.w-l:e.g[n+1].position().left-e.cs-d:1/0;if(s=h.max(f,h.min(p,s)),o.x=s,o.css("left",s+a),c){var g=e.c[o.i];o.w=g.w+s-o.l}if(e.opt.liveDrag){c?(g.width(o.w),!e.f&&e.opt.overflow?e.css("min-width",e.w+s-o.l):e.w=e.width()):b(e,n),C(e);var u=e.opt.onDrag;u&&(t.currentTarget=e[0],u(t))}return!1}},y=function(r){if(i.unbind("touchend."+l+" mouseup."+l).unbind("touchmove."+l+" mousemove."+l),t("head :last-child").remove(),o){if(o.removeClass(o.t.opt.draggingClass),o.x-o.l!=0){var s=o.t,d=s.opt.onResize,n=o.i,a=n==s.ln-1,c=s.g[n].c;a?(c.width(o.w),c.w=o.w):b(s,n,!0),s.f||R(s),C(s),d&&(r.currentTarget=s[0],d(r)),s.p&&e&&m(s)}o=null}},J=function(e){var d=t(this).data(l),n=s[d.t],a=n.g[d.i],c=e.originalEvent.touches;if(a.ox=c?c[0].pageX:e.pageX,a.l=a.position().left,a.x=a.l,i.bind("touchmove."+l+" mousemove."+l,x).bind("touchend."+l+" mouseup."+l,y),r.append("<style type='text/css'>*{cursor:"+n.opt.dragCursor+"!important}</style>"),a.addClass(n.opt.draggingClass),o=a,n.c[d.i].l)for(var f,h=0;h<n.ln;h++)f=n.c[h],f.l=!1,f.w=f.width();return!1},L=function(){for(var t in s)if(s.hasOwnProperty(t)){t=s[t];var i,r=0;if(t.removeClass(l),t.f){for(t.w=t.width(),i=0;i<t.ln;i++)r+=t.c[i].w;for(i=0;i<t.ln;i++)t.c[i].css("width",h.round(1e3*t.c[i].w/r)/10+"%").l=!0}else R(t),"flex"==t.mode&&t.p&&e&&m(t);C(t.addClass(l))}};t(window).bind("resize."+l,L),t.fn.extend({colResizable:function(e){var i={resizeMode:"fit",draggingClass:"JCLRgripDrag",gripInnerHtml:"",liveDrag:!1,minWidth:15,headerOnly:!1,hoverCursor:"e-resize",dragCursor:"e-resize",postbackSafe:!1,flush:!1,marginLeft:null,marginRight:null,disable:!1,partialRefresh:!1,disabledColumns:[],onDrag:null,onResize:null},e=t.extend(i,e);switch(e.fixed=!0,e.overflow=!1,e.resizeMode){case"flex":e.fixed=!1;break;case"overflow":e.fixed=!1,e.overflow=!0}return this.each(function(){u(this,e)})}})}(jQuery);
/*****函数定义与变量定义*****/
var addSubmitObj = new Object();
addSubmitObj.Type = null;
addSubmitObj.Number = null;
addSubmitObj.Project = null;
addSubmitObj.CustomerTitle = null;
addSubmitObj.CustomerName = null;
addSubmitObj.LinkInfo = null;
addSubmitObj.StaffName = null;
addSubmitObj.ContractNo = null;
addSubmitObj.ProductVersion = null;
addSubmitObj.FileName = null;
var updateSubmitObj = new Object();
updateSubmitObj.ID = null;
updateSubmitObj.Type = null;
updateSubmitObj.Number = null;
updateSubmitObj.Project = null;
updateSubmitObj.CustomerTitle = null;
updateSubmitObj.CustomerName = null;
updateSubmitObj.LinkInfo = null;
updateSubmitObj.StaffName = null;
updateSubmitObj.ContractNo = null;
updateSubmitObj.ProductVersion = null;
updateSubmitObj.FileName = null;
var downloadObj = new Object();
downloadObj.Number = null;
downloadObj.Project = null;
downloadObj.CustomerTitle = null;
downloadObj.CustomerName = null;
downloadObj.LinkInfo = null;
downloadObj.StaffName = null;
downloadObj.ContractNo = null;
downloadObj.ProductVersion = null;
downloadObj.Type = null;
downloadObj.FileName = null;
downloadObj.PreviewJson = null;
var hasSearch = 0;
var signatureObj = {};
var ReportID;
var ReportNumber;
var curFileName = "";
var down_FileName = "";
// 删除记录用的变量
var delete_email_curPage = 1;
var delete_ID = null;
var delete_res_flag = null;
// var regDate1 = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
// var regDate2 = /^[1-9]\d{3}-(0[1-9]|1[0-2])$/;
// var regDate3 = /^(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
// var regDate4 = /^[1-9]\d{3}$/;
// var regDate5 = /^(0[1-9]|1[0-2])$/;
// var regDate6 = /^(0[1-9]|[1-2][0-9]|3[0-1])$/;
// 服务完成报告item字符串
var serviceReportStr = '<tr>'+
                            '<td class="item-No"><span class="glyphicon glyphicon-remove-circle delete_item"></span>&nbsp;<span class="NoService"></span></td>'+
                            '<td class="service_ServiceItem"></td>'+
                            '<td class="service_Isfinished"><select class="isFinish_sel"><option value="0">请选择</option><option value="是">是</option><option value="否">否</option></select></td>'+
                            '<td class="service_Remarks"></td>'+
                            '<td class="service_ConfirmedSignature"><span class="glyphicon glyphicon-pencil iSignature"></span></td>'+
                            '<td class="service_ConfirmDate"><span class="glyphicon glyphicon-pencil iSignature"></span></td>'+
                        '</tr>';

function pageStyle(currentPage,pageCounts){
    if(pageCounts == 1){
        $("#fistPage").attr("disabled","disabled");
        $("#upPage").attr("disabled","disabled");
        $("#nextPage").attr("disabled","disabled");
        $("#lastPage").attr("disabled","disabled");
        buttonDisabled("#fistPage, #upPage, #nextPage, #lastPage");
    }else if(currentPage == 1){
        $("#fistPage").attr("disabled","disabled");
        $("#upPage").attr("disabled","disabled");
        $("#lastPage").attr("disabled",false);
        $("#nextPage").attr("disabled",false);
        buttonDisabled("#fistPage, #upPage");
        buttonAbled("#nextPage, #lastPage");
    }else if(currentPage == pageCounts){
        $("#lastPage").attr("disabled","disabled");
        $("#nextPage").attr("disabled","disabled");
        $("#fistPage").attr("disabled",false);
        $("#upPage").attr("disabled",false);
        buttonDisabled("#nextPage, #lastPage");
        buttonAbled("#fistPage, #upPage");
    }else{
        $("#lastPage, #nextPage, #fistPage, #upPage").attr("disabled",false);
        buttonAbled("#lastPage, #nextPage, #fistPage, #upPage");
    }
}
function buttonDisabled(iClass) {
    return $(iClass).removeClass("buttonDisabled buttonAbled").addClass("buttonDisabled");
}
function buttonAbled(jClass) {
    return $(jClass).removeClass("buttonDisabled buttonAbled").addClass("buttonAbled");
}

// 表格渲染
function tableRender(icurPage,pageCounts,data){
	var str = '';
	for(var i =1;i<data.length;i++){
	    str+='<tr>'+
	            '<td class="update_td" data-iid="'+data[i].ID+'">'+parseInt((icurPage-1)*10 + i)+'</td>'+
	            '<td class="hastd_Number" title="'+data[i].Number+'">'+data[i].Number+'</td>'+
	            '<td class="hastd_Project" title="'+data[i].Project+'">'+data[i].Project+'</td>'+
	            '<td class="hastd_CustomerTitle" title="'+data[i].CustomerTitle+'">'+data[i].CustomerTitle+'</td>'+
	            '<td class="hastd_CustomerName" title="'+data[i].CustomerName+'">'+data[i].CustomerName+'</td>'+
	            '<td class="hastd_LinkInfo" title="'+data[i].LinkInfo+'">'+data[i].LinkInfo+'</td>'+
	            '<td class="hastd_StaffName" title="'+data[i].StaffName+'">'+data[i].StaffName+'</td>'+
	            '<td class="reportView" title="报告预览"><button type="button" class="btn btn-default" aria-label="Left Align"><span class="glyphicon glyphicon-eye-open"></span></button></td>'+
	            '<td class="hastd_ContractNo" title="'+data[i].ContractNo+'" style="display:none">'+data[i].ContractNo+'</td>'+
	            '<td class="hastd_ProductVersion" title="'+data[i].ProductVersion+'" style="display:none">'+data[i].ProductVersion+'</td>'+
	            '<td class="hastd_FileName" title="'+data[i].FileName+'" style="display:none">'+data[i].FileName+'</td>'+
	            '<td class="delete_report_td" title="删除" style="display:none"><input type="button" value="删除"></td>'+
	        '</tr>';
	}
	$(".m_table table tbody").empty().append(str);
	$(".m_page #currentPage").text(icurPage);
	$(".m_page #allPage").text(pageCounts);
	deleteServiceReport();
	setTimeout(tableCanDrag,50);
}

// 表格渲染ajax请求
function tableRenderAjax(icurPage){
	$.ajax({
		type: "GET",
		url: "ServiceReport",
		data: {
			LoadType: "data",
			CurrentPage: icurPage
		},
		dataType: 'json',
		success: function(res){
			console.log(typeof res);
			var pageCounts = res.pageCount;
			var data = res.data;
			tableRender(icurPage,pageCounts,data);
			pageStyle(icurPage,pageCounts);
		},
		error:function(){
			$.MsgBox_Unload.Alert("提示","服务器繁忙！");
		},
		complete: function(XMLHttpRequest, textStatus){
		    // var newInfo1 = transportInfo();
		    // $(".g_container_body_info_l").text(newInfo1);
		    // tdDateHandle(".DateOfSign, .CargoPeriod, .ExpectedDeliveryPeriod, .ActualDelivery","","#000");
		}
	});
}

// 搜索后表格渲染ajax请求
function searchTABRenderAjax(icurPage,QueryType,Column1,Content1,Column2,Content2){
	// 单一搜索
	if(Column2==undefined&&Content2==undefined){
		$.ajax({
			type: "GET",
			url: "ServiceReport",
			data: {
				LoadType: "data",
				QueryType: QueryType,
				Column1: Column1,
				Content1: Content1,
				CurrentPage: icurPage
			},
			dataType: 'json',
			success: function(res){
				console.log(typeof res);
				var pageCounts = res.pageCount;
				var data = res.data;
				tableRender(icurPage,pageCounts,data);
				pageStyle(icurPage,pageCounts);
			},
			error: function(){
				$.MsgBox_Unload.Alert("提示","服务器繁忙！");
			},
			complete: function(XMLHttpRequest, textStatus){
			    if(textStatus=="success"){
					hasSearch = 1;
			    }
			}
		});
	}else{
		// 组合查询
		$.ajax({
			type: "GET",
			url: "ServiceReport",
			data: {
				LoadType: "data",
				QueryType: QueryType,
				Column1: Column1,
				Content1: Content1,
				Column2: Column2,
				Content2: Content2,
				CurrentPage: icurPage
			},
			dataType:'json',
			success:function(res){
				console.log(typeof res);
				var pageCounts = res.pageCount;
				var data = res.data;
				tableRender(icurPage,pageCounts,data);
				pageStyle(icurPage,pageCounts);
			},
			error:function(){
				$.MsgBox_Unload.Alert("提示","服务器繁忙！");
			},
			complete: function(XMLHttpRequest, textStatus){
			    if(textStatus=="success"){
					hasSearch = 1;
			    }
			}
		});
	}
}

// 搜索获取参数，参数判断
function searchGetParam(icurPage){
	var QueryType = $(".m_button_r_top input[name='querytype']:checked").val();
	var Column1 = $(".m_button_r_m .input-group-btn button").attr("title");
	var Content1 = $(".m_button_r_m input").val().trim();
	// if(Column1=="发布时间"||Column1=="阶段预计完成时间"||Column1=="项目预计完成时间"||Column1=="阶段实际完成时间"){
	// 	if(!regDate1.test(Content1)&&!regDate2.test(Content1)&&!regDate3.test(Content1)&&!regDate4.test(Content1)&&!regDate5.test(Content1)&&!regDate6.test(Content1)){
	// 		$.MsgBox_Unload.Alert("格式错误提示","例子：2018-05-02,2018-06,2018,06-01,08");
	// 		return false;
	// 	}
	// }
	if(QueryType==undefined||QueryType==""||QueryType==null||QueryType=="on"){
		$.MsgBox_Unload.Alert("提示","请选择搜索类别");
		return false;
	}else if(QueryType=="singleSelect"){
		if(Column1==undefined||Column1==""||Column1==null||Column1=="选择"){
			$.MsgBox_Unload.Alert("提示","请选择搜索字段");
			return false;
		}else if(Content1==""||Content1==null||Content1==undefined){
			$.MsgBox_Unload.Alert("提示","搜索内容为空");
			return false;
		}else{
			searchTABRenderAjax(icurPage,QueryType,Column1,Content1);
		}
	}else if(QueryType=="mixSelect"){
		var Column2 = $(".m_button_r_l .input-group-btn button").attr("title");
		var Content2 = $(".m_button_r_l input").val().trim();
		var tempColumn1,tempColumn2,tempContent1,tempContent2;
		if(Column1==undefined||Column1==""||Column1==null||Column1=="选择"){
			tempColumn1=1;
		}
		if(Column2==undefined||Column2==""||Column2==null||Column2=="选择"){
			tempColumn2=1;
		}
		if(Content1==""||Content1==null||Content1==undefined){
			tempContent1=1;
		}
		if(Content2==""||Content2==null||Content2==undefined){
			tempContent2=1;
		}
		// if(Column2=="发布时间"||Column2=="阶段预计完成时间"||Column2=="项目预计完成时间"||Column2=="阶段实际完成时间"){
		// 	if(!regDate1.test(Content2)&&!regDate2.test(Content2)&&!regDate3.test(Content2)&&!regDate4.test(Content2)&&!regDate5.test(Content2)&&!regDate6.test(Content2)){
		// 		$.MsgBox_Unload.Alert("格式错误提示","例子：2018-05-02,2018-06,2018,06-01,08");
		// 		return false;
		// 	}
		// }
		if(tempColumn1==1||tempColumn2==1){
			$.MsgBox_Unload.Alert("提示","请选择搜索字段");
			return false;
		}else if(tempContent1==1||tempContent2==1){
			$.MsgBox_Unload.Alert("提示","搜索内容为空");
			return false;
		}else{
			searchTABRenderAjax(icurPage,QueryType,Column1,Content1,Column2,Content2);
		}
	}
}

// 搜索初始化
function sosuoInit(){
    $("input[name='querytype'][value='singleSelect']").prop("checked","checked");
    $("input[name='querytype'][value='singleSelect']").parent().addClass("active").css("color","#333");
    $("input[name='querytype'][value='mixSelect']").prop("checked",false);
    $("input[name='querytype'][value='mixSelect']").parent().removeClass("active").css("color","#fff");
    $(".m_button_r_l .input-group").fadeOut(100);
    $(".m_button_r_l .input-group-btn button").html("选择<span class='caret'></span>");
    // $(".m_button_r_l .input-group-btn button").text("选择");
    $(".m_button_r_l .input-group-btn button").attr("title","");
    $(".m_button_r_l .input-group input").val("");
    $(".m_button_r_m .input-group-btn button").html("选择<span class='caret'></span>");
    $(".m_button_r_m .input-group-btn button").attr("title","");
    $(".m_button_r_m .input-group input").val("");
}

// 计算服务完成报告item序号
function calcServiceNo(){
	$(".serviceReport_table tbody tr").each(function(){
		var No = $(this).index() + 1;
		$(this).find(".NoService").text(No);
	});
}

// 可填项
function canFillItem(){
	$(".service_ServiceItem, .service_Remarks").prop("contenteditable","true");
}

// 删除权限判断
function deleteServiceReport(){
	if($("#DeleteServiceReport_span").length && $("#DeleteServiceReport_span").text()=="是"){
		$(".t2, .t3, .t4, .t5, .t6, .t7, .t8, .t12").css({
			"min-width":"118.125px",
			"width":"11.8125%"
		});
		$(".delete_report_td").css("border-right","0px solid #999");
		$(".t12, .delete_report_td").show();
	}
}

// 表格拖拽
function tableCanDrag(){
	if(ecDo.browserInfo("android") || ecDo.browserInfo("iphone")){
		return false;
	}
	$(".m_table .JCLRgrips").remove();
	$(".m_table table").colResizable({
	    gripInnerHtml: '<span class="glyphicon glyphicon-resize-horizontal" aria-hidden="true"></span>',
	    partialRefresh: true,
	    // postbackSafe: true,
	    minWidth: 53
	});
}

/*****页面加载完成*****/
$(function(){
	sosuoInit();
	tableRenderAjax(1);
	// 暂时注释
	// $.ajax({
	// 	type: "GET",
	// 	url: "GetStaffInfo",
	// 	data: {
	// 		param: "",
	// 		Type: "staff"
	// 	},
	// 	dataType: "json",
	// 	success: function(data){
	// 		var str = '<option value="" disabled>请选择</option>';
	// 		data.map(function(currentValue,index,arr){
	// 			if(index>0){
	// 				str+='<option value="'+currentValue.StaffName+'" data-id="'+currentValue.ID+'">'+currentValue.StaffName+'</option>';
	// 			}
	// 		});
	// 		$("#addStaffName, #updateStaffName").empty().append(str);
	// 	},
	// 	error: function(){
	// 		$.MsgBox_Unload.Alert("提示","服务器繁忙！获取员工姓名失败");
	// 	}
	// });
	// 
	// deleteServiceReport();
	// setTimeout(tableCanDrag,100);
	$("#exampleModal").modal({keyboard: true, show: false});
});


/*****
* event listener
*****/
// 打开
$(".m_button_l input[value='添加']").on("click",function(){
	var staffCode;
	var iThat = $(this);
	$.ajax({
		type: "GET",
		url: "ServiceReport",
		data: {
			LoadType: "staffCode"
		},
		dataType: 'text',
		beforeSend: function(XMLHttpRequest){
		    eouluGlobal.C_btnDisabled(iThat, false);
		},
		success: function(res){
			staffCode = res;
		},
		error: function(){
			$.MsgBox_Unload.Alert("提示","服务器繁忙！");
		},
		complete: function(XMLHttpRequest, textStatus){
		    if(textStatus=="success"){
		    	$(".add_NonStandard_body_in [class^='info_']").each(function(){
		    		// if($(this).prop("tagName")=="SELECT"||$(this).prop("tagName")=="select"){
		    		// 	$(this).val("0");
		    		// }else{
		    		// 	$(this).val("");
		    		// }
		    		if($(this).is(".info_Number")){
		    			$(this).val(staffCode);
		    			return true;
		    		}
		    		$(this).val("");
		    	});
		    	$(".bg_cover, .add_NonStandard").slideDown(150);
		    }
		    eouluGlobal.C_btnAbled(iThat, false);
		}
	});
});

$(document).on("click",".update_td",function(){
	// jQuery.data($(this), "customerId");
	updateSubmitObj.ID = Number($(this).data("iid")).toString();
	var that = $(this);
	$(".update_NonStandard_body_in [class^='info_']").each(function(){
		var subClassName = $(this).attr("class").split(" ")[0].replace("info_","hastd_");
		var oldVal = that.siblings("."+subClassName).text();
		var newVal = globalDataHandle(oldVal,"");
		$(this).val(newVal);
	});
	$(".bg_cover").slideDown(350);
	$(".update_NonStandard").slideDown(350);
});

// 关闭
$("#NonStandard_addclose, .add_NonStandard_tit_r").on("click",function(){
	for(var k in addSubmitObj){
		addSubmitObj[k] = null;
	}
	$(".bg_cover").slideUp(350);
	$(".add_NonStandard").slideUp(350);
});

$("#NonStandard_updateclose, .update_NonStandard_tit_r").on("click",function(){
	for(var k in updateSubmitObj){
		updateSubmitObj[k] = null;
	}
	$(".bg_cover").slideUp(350);
	$(".update_NonStandard").slideUp(350);
});

// 翻页功能
$("#jumpNumber").on("input propertychange",function(){
    var newVal = $(this).val().replace(/[^\d]/g,'');
    $(this).val(newVal);
});

	// 翻页
$("#fistPage").click(function(){
    var currentPage =1;
    if(hasSearch==0){
    	tableRenderAjax(currentPage);
    }else if(hasSearch==1){
    	searchGetParam(currentPage);
    }
});

$("#lastPage").click(function(){
    var currentPage =Number($("#allPage").text());
    if(hasSearch==0){
    	tableRenderAjax(currentPage);
    }else if(hasSearch==1){
    	searchGetParam(currentPage);
    }
});

$("#upPage").click(function(){
    var currentPage = Number($("#currentPage").text());
    if(currentPage == 1){
        return;
    }else{
        currentPage--;
        if(hasSearch==0){
        	tableRenderAjax(currentPage);
        }else if(hasSearch==1){
        	searchGetParam(currentPage);
        }
    }
});

$("#nextPage").click(function(){
    var currentPage = Number($("#currentPage").text());
    var pageCounts = Number($("#allPage").text());
    if(currentPage == pageCounts){
        return;
    }else{
        currentPage++;
        if(hasSearch==0){
        	tableRenderAjax(currentPage);
        }else if(hasSearch==1){
        	searchGetParam(currentPage);
        }
    }
});
	//跳页
$("#Gotojump").click(function(){
    var currentPage = $("#jumpNumber").val().trim();
    var pageCounts = Number($("#allPage").text());
    var oldCurrentPage = Number($("#currentPage").text());
    if(currentPage == oldCurrentPage || currentPage <= 0 || currentPage>pageCounts){
        $("#jumpNumber").val('');
        return;
    }else{
        if(hasSearch==0){
        	tableRenderAjax(currentPage);
        }else if(hasSearch==1){
        	searchGetParam(currentPage);
        }
    }
});

// 添加提交
$("#NonStandard_addsubmit").on("click",function(){
	var info_Number_arr = $(".add_NonStandard .info_Number").val().trim().split("-");
	// console.log(info_Number_arr);
	// console.log(info_Number_arr.length);
	if(info_Number_arr.length != 3){
		$.MsgBox_Unload.Alert("报告编号格式提示","格式错误！示例：SRV-T023-001");
		return false;
	}else{
		if($.inArray("", info_Number_arr)>-1 || $.inArray(undefined, info_Number_arr)>-1){
			$.MsgBox_Unload.Alert("报告编号格式提示","格式错误！示例：SRV-T023-002");
			return false;
		}
	}
	for(var kk in addSubmitObj){
		if(kk=="Type"){
			addSubmitObj[kk] = "add";
			continue;
		}
		addSubmitObj[kk] = $(".add_NonStandard").find(".info_"+kk).val();
	}
	// 表单验证
	for(var kkk in addSubmitObj){
		addSubmitObj[kkk] = globalDataHandle(addSubmitObj[kkk],"").trim();
		if(kkk=="Number"&&addSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未填写报告编号！");
			return false;
		}
		if(kkk=="Project"&&addSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择项目！");
			return false;
		}
		if(kkk=="FileName"&&addSubmitObj[kkk].indexOf(".")>-1){
			$.MsgBox_Unload.Alert("提示","文件名不能含有“.”！");
			return false;
		}
	}
	// console.log("add我执行了吗");
	// console.log(addSubmitObj);
	var iThat = $(this);
	$.ajax({
		type: "POST",
		url: "ServiceReport",
		dataType: 'text',
		data: addSubmitObj,
		beforeSend: function(XMLHttpRequest){
		    eouluGlobal.C_btnDisabled(iThat, true, "send...");
		},
		success: function(data){
			// console.log(typeof data);
			if(data=="true"){
				$.MsgBox_Unload.Alert("提示","添加成功！");
				$("#NonStandard_addclose").trigger("click");
			}else if(data=="false"){
				$.MsgBox_Unload.Alert("提示","添加失败！");
			}
		},
		error:function(){
			$.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
		},
		complete: function(XMLHttpRequest, textStatus){
		    if(textStatus=="success"){
				tableRenderAjax(1);
				hasSearch = 0;
    			sosuoInit();
		    }
		    eouluGlobal.C_btnAbled(iThat, true, "提交");
		}
	});
});

// 修改提交
$("#NonStandard_updatesubmit").on("click",function(){
	var info_Number_arr = $(".update_NonStandard .info_Number").val().trim().split("-");
	if(info_Number_arr.length != 3){
		$.MsgBox_Unload.Alert("报告编号格式提示","格式错误！示例：SRV-T023-001");
		return false;
	}else{
		if($.inArray("", info_Number_arr)>-1 || $.inArray(undefined, info_Number_arr)>-1){
			$.MsgBox_Unload.Alert("报告编号格式提示","格式错误！示例：SRV-T023-002");
			return false;
		}
	}
	for(var kk in updateSubmitObj){
		if(kk=="Type"){
			updateSubmitObj[kk] = "update";
			continue;
		}
		if(kk=="ID"){
			continue;
		}
		updateSubmitObj[kk] = $(".update_NonStandard").find(".info_"+kk).val();
	}
	// 表单验证
	for(var kkk in updateSubmitObj){
		updateSubmitObj[kkk] = globalDataHandle(updateSubmitObj[kkk],"").trim();
		if(kkk=="Number"&&updateSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未填写报告编号！");
			return false;
		}
		if(kkk=="Project"&&updateSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择项目！");
			return false;
		}
		if(kkk=="FileName"&&updateSubmitObj[kkk].indexOf(".")>-1){
			$.MsgBox_Unload.Alert("提示","文件名不能含有“.”！");
			return false;
		}
	}
	console.log("update我执行了吗");
	console.log(updateSubmitObj);
	$.ajax({
		type: "POST",
		url: "ServiceReport",
		dataType: 'text',
		data: updateSubmitObj,
		success: function(data){
			console.log(typeof data);
			if(data=="true"){
				$.MsgBox_Unload.Alert("提示","修改成功！");
				$("#NonStandard_updateclose").trigger("click");
			}else if(data=="false"){
				$.MsgBox_Unload.Alert("提示","修改失败！");
			}
		},
		error:function(){
			$.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
		},
		complete: function(XMLHttpRequest, textStatus){
		    if(textStatus=="success"){
				tableRenderAjax(1);
				hasSearch = 0;
    			sosuoInit();
		    }
		}
	});
});

// 搜索功能
$(".m_button_r_top label").on("click",function(){
    if($(this).children().val()=="singleSelect"){
        $(this).css("color","#333");
        $(this).siblings("label").css("color","#fff");
        $(".m_button_r_l div.input-group").fadeOut(100);
    }else{
        $(this).css("color","#333");
        $(this).siblings("label").css("color","#fff");
        $(".m_button_r_l div.input-group").fadeIn(100);
    }
});
$(".m_button_r_l .dropdown-menu li").on("click",function(){
	var inText = $(this).text();
	var inTitle = $(this).attr("title");
	$(this).parent().siblings("button").text(inTitle);
	$(this).parent().siblings("button").attr("title",inText);
});
$(".m_button_r_m .dropdown-menu li").on("click",function(){
	var inText = $(this).text();
	var inTitle = $(this).attr("title");
	$(this).parent().siblings("button").text(inTitle);
	$(this).parent().siblings("button").attr("title",inText);
});

// 取消搜索
$("#NonStandard_cancel").on("click",function(){
	hasSearch = 0;
	sosuoInit();
	tableRenderAjax(1);
	for(var k in addSubmitObj){
		addSubmitObj[k] = null;
	}
	for(var kk in updateSubmitObj){
		updateSubmitObj[kk] = null;
	}
	$("#jumpNumber").val("");
});

// 搜索请求
$("#NonStandard_search").on("click",function(){
	searchGetParam(1);
});

// 表格悬浮
$(document).on("mouseenter",".m_table tbody td",function(){
	$(this).parent().css({"border":"2px solid red"});
});
$(document).on("mouseleave",".m_table tbody td",function(){
	$(this).parent().css({"border":"1px solid transparent"});
});

// 服务完成报告模板
$(document).on("click",".reportView>button",function(){
	// setTimeout(function(){
	// 	$("#input_down_file")[0].focus();
	// },50);
	var iThat = $(this);
	ReportID = iThat.parent().siblings(".update_td").data("iid");
	eouluGlobal.C_btnDisabled(iThat, false);
	$.ajax({
		type: "GET",
		url: "ServiceReportPreview",
		data: {
			ReportID: ReportID
		},
		dataType: "json"
	}).then(function(data){
		$(".serviceReport_div, .bg_cover").slideDown(100);
		var str = '';
		data.map(function(currentValue,index,arr){
			if(index>0){
				str+='<tr>'+
	                    '<td class="item-No" data-id="'+currentValue.ID+'"><span class="glyphicon glyphicon-remove-circle delete_item"></span>&nbsp;<span class="NoService"></span></td>'+
	                    '<td class="service_ServiceItem">'+currentValue.ServiceItem+'</td>'+
	                    '<td class="service_Isfinished">'+currentValue.Isfinished+'</td>'+
	                    '<td class="service_Remarks">'+currentValue.Remarks+'</td>'+
	                    '<td class="service_ConfirmedSignature"><span class="glyphicon glyphicon-pencil iSignature"></span><img src="data:image/png;base64,'+currentValue.ConfirmedSignature+'" style="max-height: 60px; max-width: 110px;"></td>'+
	                    '<td class="service_ConfirmDate"><span class="glyphicon glyphicon-pencil iSignature"></span><img src="data:image/png;base64,'+currentValue.ConfirmDate+'" style="max-height: 60px; max-width: 110px;"></td>'+
	                '</tr>';
			}
		});
		$(".serviceReport_table tbody").empty().append(str);
		calcServiceNo();
		canFillItem();
		// 保存数据
		ReportNumber = iThat.parent().siblings(".hastd_Number").text();
		curFileName = iThat.parent().siblings(".hastd_FileName").text();
		var filenameHook = ReportNumber.replace("SRV-","").replace("-","");
		if(curFileName==""||curFileName=="--"){
			down_FileName = filenameHook;
		}else{
			down_FileName = curFileName;
		}
		
		for(var ii in downloadObj){
			if(ii == "Type"){
				downloadObj[ii] = "download";
				continue;
			}
			if(ii == "FileName"){
				continue;
			}
			if(ii == "PreviewJson"){
				continue;
			}
			downloadObj[ii] = iThat.parent().siblings(".hastd_"+ii).text();
		}
		// 保存数据结束
	},function(){
		$.MsgBox_Unload.Alert("提示", "网络繁忙！");
	}).always(function(){
		eouluGlobal.C_btnAbled(iThat, false);
	});
});

$(".serviceReport_top_r").click(function(){
	for(var k in downloadObj){
		downloadObj[k] = null;
	}
	$(".serviceReport_div").slideUp(300);
	$(".bg_cover").slideUp(300);
	curFileName = "";
	down_FileName = "";
	ReportNumber = "";
});

// item添加
$("#add_serviceReport").on("click",function(){
	$(".serviceReport_table tbody").append(serviceReportStr);
	calcServiceNo();
	canFillItem();
});
// 删除
$(document).on("click",".delete_item",function(){
	$(this).parent().parent().remove();
	calcServiceNo();
});

// 电子签名打开
$(document).on("click",".iSignature",function(){
	var signatureID = $(this).parent().siblings(".item-No").data("id");
	if(signatureID){
		var preSrc = $(this).siblings("img").attr("src").indexOf(";base64,")>-1 ? $(this).siblings("img").attr("src").split(";base64,")[1] : "";
		if(preSrc !== ""){
			return false;
		}
	}
	$(".wrapper_sign, .cover_bg2").slideDown(250);
	$("#signature").empty();
	$("#signature").jSignature();
	var index = $(this).parent().parent().index();
	var classify = $(this).parent().attr("class");
	signatureObj.index = index;
	signatureObj.classify = classify;
});

$(".wrapper_sign_tit_r").click(function(){
	$(".wrapper_sign").slideUp(300);
	$(".cover_bg2").slideUp(300);
});

var $sigdiv = $("#signature");
$("#signature_reset").on("click",function(){
	$sigdiv.jSignature("reset");
});

$("#signature_OK").on("click",function(){
	var datapair = $sigdiv.jSignature("getData", "svgbase64");
	var datapairPng = $sigdiv.jSignature("getData", "image");
	// console.log(datapair);
	var i = new Image();
	i.src = "data:" + datapairPng[0] + "," + datapairPng[1];
	// i.width = "110";
	i.style = "max-height:60px;max-width:110px;";
	// $(i).data("png","data:" + datapairPng[0] + "," + datapairPng[1]);
	// console.log(i);
	// console.log($(i).data("png"));
	var index = signatureObj.index;
	var classify = signatureObj.classify;
	var curTd = $(".serviceReport_table tbody tr").eq(index).find("."+classify);
	curTd.find("img").remove();
	curTd.append(i);
	$(".wrapper_sign").slideUp(300);
	$(".cover_bg2").slideUp(300);
});

// 保存
$(".serviceReport_top_save").click(function(){
	var PreviewJson = [];
	var len = $(".serviceReport_table tbody tr").length;
	if(len == 0){
		$.MsgBox_Unload.Alert("提示", "请先添加一条再保存！");
		return false;
	}
	var isAllData = 1;
	var isFinishVal = 1;
	$(".serviceReport_table tbody tr").each(function(){
		var item = {};
		var iServiceItem = $(this).children(".service_ServiceItem").text().trim();
		if(iServiceItem==""||iServiceItem=="--"){
			isAllData = 0;
			return false;
		}
		var iIsfinished = $(this).children(".service_Isfinished").text();
		if(iIsfinished!="是"&&iIsfinished!="否"){
			isFinishVal = 0;
			return false;
		}
		var ConfirmedSignature;
		var ConfirmDate;
		if(!$(this).children(".service_ConfirmedSignature").find("img").length){
			ConfirmedSignature = "";
		}else{
			var srcVal1 = $(this).children(".service_ConfirmedSignature").find("img").attr("src");
			if(srcVal1 == "data:image/png;base64,"){
				ConfirmedSignature = "";
			}else{
				ConfirmedSignature = srcVal1;
			}
			// data:image/png;base64,
			// ConfirmedSignature = $(this).children(".service_ConfirmedSignature").find("img").data("png");
		}

		if(!$(this).children(".service_ConfirmDate").find("img").length){
			ConfirmDate = "";
		}else{
			var srcVal2 = $(this).children(".service_ConfirmDate").find("img").attr("src");
			if(srcVal2 == "data:image/png;base64,"){
				ConfirmDate = "";
			}else{
				ConfirmDate = srcVal2;
			}
			// ConfirmDate = $(this).children(".service_ConfirmDate").find("img").attr("src");
			// ConfirmDate = $(this).children(".service_ConfirmDate").find("img").data("png");
		}
		
		item.ServiceItem = iServiceItem;
		item.Isfinished = iIsfinished;
		item.Remarks = $(this).children(".service_Remarks").text().trim();
		item.ConfirmedSignature = ConfirmedSignature;
		item.ConfirmDate = ConfirmDate;
		PreviewJson.push(item);
	});
	if(isAllData==0){
		$.MsgBox_Unload.Alert("提示", "有完成内容项未填！");
		return false;
	}
	if(isFinishVal==0){
		$.MsgBox_Unload.Alert("提示", "有是否达标项未填！");
		return false;
	}
	
	var PreviewJsonStr = $.fn.stringifyArr(PreviewJson);
	// downloadObj.PreviewJson = PreviewJsonStr;
	var iNumber = ReportNumber;
	$.ajax({
		type: "POST",
		url: "ServiceReportPreview",
		data: {
			Type: "save",
			PreviewJson: PreviewJsonStr,
			ReportID: ReportID,
			Number: iNumber
		},
		dataType: "text"
	}).then(function(data){
		console.log(typeof data);
		if(data=="true"){
			$.MsgBox_Unload.Alert("提示", "保存成功！");
			// $(".serviceReport_top_r").trigger("click");
		}else if(data=="false"){
			$.MsgBox_Unload.Alert("提示", "保存失败！");
		}
	},
	function(){
		$.MsgBox_Unload.Alert("提示", "服务器繁忙，保存有误！");
	});
});

// 选择是否达标
$(document).on("click",".service_Isfinished",function(e){
  e.stopPropagation();
  if($(this).text()=="是"||$(this).text()=="否"){
    $(this).text("");
    var str11 = '<select class="isFinish_sel">'+
                    '<option value="0">请选择</option>'+
                    '<option value="是">是</option>'+
                    '<option value="否">否</option>'+
                    '</select>';
    $(this).html(str11);
  }
});

// 选择是否
$(document).on("change",".isFinish_sel",function(){
  var ival = $(this).val();
  if(ival == 0){
    return;
  }else{
    var that = $(this).parent();
    $(this).detach();
    that.html("").text(ival);
  }
});

// 下载
$(".serviceReport_top_down").click(function(){
	// $.MsgBox_Unload.Alert("提示", "下载前请记得保存！");
	var PreviewJson = [];
	var len = $(".serviceReport_table tbody tr").length;
	if(len == 0){
		$.MsgBox_Unload.Alert("提示", "请先添加一条再下载！");
		return false;
	}
	var isAllData = 1;
	var isFinishVal = 1;
	$(".serviceReport_table tbody tr").each(function(){
		var item = {};
		var iServiceItem = $(this).children(".service_ServiceItem").text().trim();
		if(iServiceItem==""||iServiceItem=="--"){
			isAllData = 0;
			return false;
		}
		var iIsfinished = $(this).children(".service_Isfinished").text();
		if(iIsfinished!="是"&&iIsfinished!="否"){
			isFinishVal = 0;
			return false;
		}
		var ConfirmedSignature;
		var ConfirmDate;
		if(!$(this).children(".service_ConfirmedSignature").find("img").length){
			ConfirmedSignature = "";
		}else{
			var srcVal1 = $(this).children(".service_ConfirmedSignature").find("img").attr("src");
			if(srcVal1 == "data:image/png;base64,"){
				ConfirmedSignature = "";
			}else{
				ConfirmedSignature = srcVal1;
			}
		}
		if(!$(this).children(".service_ConfirmDate").find("img").length){
			ConfirmDate = "";
		}else{
			var srcVal2 = $(this).children(".service_ConfirmDate").find("img").attr("src");
			if(srcVal2 == "data:image/png;base64,"){
				ConfirmDate = "";
			}else{
				ConfirmDate = srcVal2;
			}
		}
		
		item.ServiceItem = iServiceItem;
		item.Isfinished = iIsfinished;
		item.Remarks = $(this).children(".service_Remarks").text().trim();
		item.ConfirmedSignature = ConfirmedSignature;
		item.ConfirmDate = ConfirmDate;
		PreviewJson.push(item);
	});
	if(isAllData==0){
		$.MsgBox_Unload.Alert("提示", "有完成内容项未填！");
		return false;
	}
	if(isFinishVal==0){
		$.MsgBox_Unload.Alert("提示", "有是否达标项未填！");
		return false;
	}
	
	var PreviewJsonStr = $.fn.stringifyArr(PreviewJson);
	downloadObj.PreviewJson = PreviewJsonStr;
	// var newFileName = $("#input_down_file").val().trim();
	// if(newFileName==""||newFileName=="--"||newFileName.indexOf(".")>-1){
	// 	$.MsgBox_Unload.Alert("文件名格式提示", "不能为空或含有“.”等特殊符号！");
	// 	return false;
	// }
	downloadObj.FileName = down_FileName+".doc";
	$.ajax({
		type: "POST",
		url: "ServiceReportPreview",
		data: downloadObj,
		dataType: "text",
		beforeSend: function(XMLHttpRequest){
		    $(".serviceReport_top_down").attr("disabled","disabled");
		    $(".serviceReport_top_down").css({
		      "cursor":"not-allowed"
		    });
		},
		success: function(data){
			var baseHref = window.location.href.split("cfChicken8")[0]+"cfChicken8/";
		  	window.location.href = baseHref+data;
		},
		error:function(){
		  $.MsgBox_Unload.Alert("提示","网络繁忙！");
		},
		complete: function(XMLHttpRequest, textStatus){
		    $(".serviceReport_top_down").attr("disabled",false);
		    $(".serviceReport_top_down").css({
		      "cursor":"pointer"
		    });
		}
	});
});

// 删除记录模态框弹出
$(document).on("click",".delete_report_td input",function(){
	// $(window).trigger("resize");
	delete_email_curPage = Number($(".pageInfo #currentPage").text());
	delete_ID = $(this).parent().siblings(".update_td").data("iid");
	delete_res_flag = null;
	$(".cover_bg3").slideDown(200);
	$('#exampleModal').modal('show');
	// $.ajax({
	// 	type: "POST",
	// 	url: "ServiceReport",
	// 	data: {
	// 		Type: "delete",
	// 		ID: ID
	// 	},
	// 	dataType: "text"
	// }).then(function(data){
	// 	if(data.indexOf("没有权限")>-1){
	// 		$.MsgBox_Unload.Alert("提示","没有权限！");
	// 	}else if(data=="false"){
	// 		$.MsgBox_Unload.Alert("提示","删除失败！");
	// 	}else if(data=="true"){
	// 		$.MsgBox_Unload.Alert("提示","删除成功！");
	// 		if(hasSearch == 0){
	//     		tableRenderAjax(email_curPage);
	//     	}else if(hasSearch == 1){
	//     		searchGetParam(email_curPage);
	//     	}
	// 	}
	// },function(){
	// 	$.MsgBox_Unload.Alert("提示","网络繁忙！");
	// });
});

// 删除模态框关闭事件
$('#exampleModal').on('hide.bs.modal', function (e) {
	$(".cover_bg3").slideUp(200,function(){
		delete_email_curPage = 1;
		delete_ID = null;
	});
});
$('#exampleModal').on('hidden.bs.modal', function (e) {
	if(delete_res_flag == 1){
		$.MsgBox_Unload.Alert("提示","没有权限！");
	}else if(delete_res_flag == 2){
		$.MsgBox_Unload.Alert("提示","删除失败！");
	}else if(delete_res_flag == 3){
		$.MsgBox_Unload.Alert("提示","删除成功！");
		if(hasSearch == 0){
    		tableRenderAjax(delete_email_curPage);
    	}else if(hasSearch == 1){
    		searchGetParam(delete_email_curPage);
    	}
	}else if(delete_res_flag == 4){
		$.MsgBox_Unload.Alert("提示","网络繁忙！");
	}else{
		// $.MsgBox_Unload.Alert("提示","网络繁忙！");
	}
});

// 删除记录
$(document).on("click","#delete_service_yes",function(){
	if(delete_ID){
		var dom1 = $("#delete_service_yes");
		globalBtnNotAllow(dom1);
		$.ajax({
			type: "POST",
			url: "ServiceReport",
			data: {
				Type: "delete",
				ID: delete_ID
			},
			dataType: "text"
		}).then(function(data){
			if(data.indexOf("没有权限")>-1){
				delete_res_flag = 1;
			}else if(data=="false"){
				delete_res_flag = 2;
			}else if(data=="true"){
				delete_res_flag = 3;
			}
		},function(){
			delete_res_flag = 4;
		}).always(function(){
			globalBtnAllow(dom1);
			$('#exampleModal').modal('hide');
		});
	}
});

// 验证非法文件名
$(".info_FileName").on("blur",function(e){
	var iVal = $(this).val().trim();
	if(iVal==""){
		$(this).val("");
		return false;
	}
	if(fileIllegalChar.test(iVal)){
		$.MsgBox_Unload.Alert("文件名提示","有非法字符！");
		var iiVal = iVal.replace(fileReplaceIllegalChar,"");
		$(this).val(iiVal);
	}else{
		console.log("无");
	}
});

// $(window).on("resize",function(){
// 	var iw = $("#NonStandard_wrapper").width();
// 	var ih = $("#NonStandard_wrapper").height();
// 	$(".bg_cover, .cover_bg2, .cover_bg3").css({"width":iw+"px", "height":ih+"px"});
// });