/*!
 * FullCalendar v1.6.4
 * Docs & License: http://arshaw.com/fullcalendar/
 * (c) 2013 Adam Shaw
 */
(function(t,e){function n(e){t.extend(!0,Ce,e)}function r(n,r,c){function u(t){ae?p()&&(S(),M(t)):f()}function f(){oe=r.theme?"ui":"fc",n.addClass("fc"),r.isRTL?n.addClass("fc-rtl"):n.addClass("fc-ltr"),r.theme&&n.addClass("ui-widget"),ae=t("<div class='fc-content' style='position:relative'/>").prependTo(n),ne=new a(ee,r),re=ne.render(),re&&n.prepend(re),y(r.defaultView),r.handleWindowResize&&t(window).resize(x),m()||v()}function v(){setTimeout(function(){!ie.start&&m()&&C()},0)}function h(){ie&&(te("viewDestroy",ie,ie,ie.element),ie.triggerEventDestroy()),t(window).unbind("resize",x),ne.destroy(),ae.remove(),n.removeClass("fc fc-rtl ui-widget")}function p(){return n.is(":visible")}function m(){return t("body").is(":visible")}function y(t){ie&&t==ie.name||D(t)}function D(e){he++,ie&&(te("viewDestroy",ie,ie,ie.element),Y(),ie.triggerEventDestroy(),G(),ie.element.remove(),ne.deactivateButton(ie.name)),ne.activateButton(e),ie=new Se[e](t("<div class='fc-view fc-view-"+e+"' style='position:relative'/>").appendTo(ae),ee),C(),$(),he--}function C(t){(!ie.start||t||ie.start>ge||ge>=ie.end)&&p()&&M(t)}function M(t){he++,ie.start&&(te("viewDestroy",ie,ie,ie.element),Y(),N()),G(),ie.render(ge,t||0),T(),$(),(ie.afterRender||A)(),_(),P(),te("viewRender",ie,ie,ie.element),ie.trigger("viewDisplay",de),he--,z()}function E(){p()&&(Y(),N(),S(),T(),F())}function S(){le=r.contentHeight?r.contentHeight:r.height?r.height-(re?re.height():0)-R(ae):Math.round(ae.width()/Math.max(r.aspectRatio,.5))}function T(){le===e&&S(),he++,ie.setHeight(le),ie.setWidth(ae.width()),he--,se=n.outerWidth()}function x(){if(!he)if(ie.start){var t=++ve;setTimeout(function(){t==ve&&!he&&p()&&se!=(se=n.outerWidth())&&(he++,E(),ie.trigger("windowResize",de),he--)},200)}else v()}function k(){N(),W()}function H(t){N(),F(t)}function F(t){p()&&(ie.setEventData(pe),ie.renderEvents(pe,t),ie.trigger("eventAfterAllRender"))}function N(){ie.triggerEventDestroy(),ie.clearEvents(),ie.clearEventData()}function z(){!r.lazyFetching||ue(ie.visStart,ie.visEnd)?W():F()}function W(){fe(ie.visStart,ie.visEnd)}function O(t){pe=t,F()}function L(t){H(t)}function _(){ne.updateTitle(ie.title)}function P(){var t=new Date;t>=ie.start&&ie.end>t?ne.disableButton("today"):ne.enableButton("today")}function q(t,n,r){ie.select(t,n,r===e?!0:r)}function Y(){ie&&ie.unselect()}function B(){C(-1)}function j(){C(1)}function I(){i(ge,-1),C()}function X(){i(ge,1),C()}function J(){ge=new Date,C()}function V(t,e,n){t instanceof Date?ge=d(t):g(ge,t,e,n),C()}function U(t,n,r){t!==e&&i(ge,t),n!==e&&s(ge,n),r!==e&&l(ge,r),C()}function Z(){return d(ge)}function G(){ae.css({width:"100%",height:ae.height(),overflow:"hidden"})}function $(){ae.css({width:"",height:"",overflow:""})}function Q(){return ie}function K(t,n){return n===e?r[t]:(("height"==t||"contentHeight"==t||"aspectRatio"==t)&&(r[t]=n,E()),e)}function te(t,n){return r[t]?r[t].apply(n||de,Array.prototype.slice.call(arguments,2)):e}var ee=this;ee.options=r,ee.render=u,ee.destroy=h,ee.refetchEvents=k,ee.reportEvents=O,ee.reportEventChange=L,ee.rerenderEvents=H,ee.changeView=y,ee.select=q,ee.unselect=Y,ee.prev=B,ee.next=j,ee.prevYear=I,ee.nextYear=X,ee.today=J,ee.gotoDate=V,ee.incrementDate=U,ee.formatDate=function(t,e){return w(t,e,r)},ee.formatDates=function(t,e,n){return b(t,e,n,r)},ee.getDate=Z,ee.getView=Q,ee.option=K,ee.trigger=te,o.call(ee,r,c);var ne,re,ae,oe,ie,se,le,ce,ue=ee.isFetchNeeded,fe=ee.fetchEvents,de=n[0],ve=0,he=0,ge=new Date,pe=[];g(ge,r.year,r.month,r.date),r.droppable&&t(document).bind("dragstart",function(e,n){var a=e.target,o=t(a);if(!o.parents(".fc").length){var i=r.dropAccept;(t.isFunction(i)?i.call(a,o):o.is(i))&&(ce=a,ie.dragStart(ce,e,n))}}).bind("dragstop",function(t,e){ce&&(ie.dragStop(ce,t,e),ce=null)})}function a(n,r){function a(){v=r.theme?"ui":"fc";var n=r.header;return n?h=t("<table class='fc-header' style='width:100%'/>").append(t("<tr/>").append(i("left")).append(i("center")).append(i("right"))):e}function o(){h.remove()}function i(e){var a=t("<td class='fc-header-"+e+"'/>"),o=r.header[e];return o&&t.each(o.split(" "),function(e){e>0&&a.append("<span class='fc-header-space'/>");var o;t.each(this.split(","),function(e,i){if("title"==i)a.append("<span class='fc-header-title'><h2>&nbsp;</h2></span>"),o&&o.addClass(v+"-corner-right"),o=null;else{var s;if(n[i]?s=n[i]:Se[i]&&(s=function(){u.removeClass(v+"-state-hover"),n.changeView(i)}),s){var l=r.theme?P(r.buttonIcons,i):null,c=P(r.buttonText,i),u=t("<span class='fc-button fc-button-"+i+" "+v+"-state-default'>"+(l?"<span class='fc-icon-wrap'><span class='ui-icon ui-icon-"+l+"'/>"+"</span>":c)+"</span>").click(function(){u.hasClass(v+"-state-disabled")||s()}).mousedown(function(){u.not("."+v+"-state-active").not("."+v+"-state-disabled").addClass(v+"-state-down")}).mouseup(function(){u.removeClass(v+"-state-down")}).hover(function(){u.not("."+v+"-state-active").not("."+v+"-state-disabled").addClass(v+"-state-hover")},function(){u.removeClass(v+"-state-hover").removeClass(v+"-state-down")}).appendTo(a);Y(u),o||u.addClass(v+"-corner-left"),o=u}}}),o&&o.addClass(v+"-corner-right")}),a}function s(t){h.find("h2").html(t)}function l(t){h.find("span.fc-button-"+t).addClass(v+"-state-active")}function c(t){h.find("span.fc-button-"+t).removeClass(v+"-state-active")}function u(t){h.find("span.fc-button-"+t).addClass(v+"-state-disabled")}function f(t){h.find("span.fc-button-"+t).removeClass(v+"-state-disabled")}var d=this;d.render=a,d.destroy=o,d.updateTitle=s,d.activateButton=l,d.deactivateButton=c,d.disableButton=u,d.enableButton=f;var v,h=t([])}function o(n,r){function a(t,e){return!E||E>t||e>S}function o(t,e){E=t,S=e,W=[];var n=++R,r=F.length;N=r;for(var a=0;r>a;a++)i(F[a],n)}function i(e,r){s(e,function(a){if(r==R){if(a){n.eventDataTransform&&(a=t.map(a,n.eventDataTransform)),e.eventDataTransform&&(a=t.map(a,e.eventDataTransform));for(var o=0;a.length>o;o++)a[o].source=e,w(a[o]);W=W.concat(a)}N--,N||k(W)}})}function s(r,a){var o,i,l=Ee.sourceFetchers;for(o=0;l.length>o;o++){if(i=l[o](r,E,S,a),i===!0)return;if("object"==typeof i)return s(i,a),e}var c=r.events;if(c)t.isFunction(c)?(m(),c(d(E),d(S),function(t){a(t),y()})):t.isArray(c)?a(c):a();else{var u=r.url;if(u){var f,v=r.success,h=r.error,g=r.complete;f=t.isFunction(r.data)?r.data():r.data;var p=t.extend({},f||{}),w=X(r.startParam,n.startParam),b=X(r.endParam,n.endParam);w&&(p[w]=Math.round(+E/1e3)),b&&(p[b]=Math.round(+S/1e3)),m(),t.ajax(t.extend({},Te,r,{data:p,success:function(e){e=e||[];var n=I(v,this,arguments);t.isArray(n)&&(e=n),a(e)},error:function(){I(h,this,arguments),a()},complete:function(){I(g,this,arguments),y()}}))}else a()}}function l(t){t=c(t),t&&(N++,i(t,R))}function c(n){return t.isFunction(n)||t.isArray(n)?n={events:n}:"string"==typeof n&&(n={url:n}),"object"==typeof n?(b(n),F.push(n),n):e}function u(e){F=t.grep(F,function(t){return!D(t,e)}),W=t.grep(W,function(t){return!D(t.source,e)}),k(W)}function f(t){var e,n,r=W.length,a=x().defaultEventEnd,o=t.start-t._start,i=t.end?t.end-(t._end||a(t)):0;for(e=0;r>e;e++)n=W[e],n._id==t._id&&n!=t&&(n.start=new Date(+n.start+o),n.end=t.end?n.end?new Date(+n.end+i):new Date(+a(n)+i):null,n.title=t.title,n.url=t.url,n.allDay=t.allDay,n.className=t.className,n.editable=t.editable,n.color=t.color,n.backgroundColor=t.backgroundColor,n.borderColor=t.borderColor,n.textColor=t.textColor,w(n));w(t),k(W)}function v(t,e){w(t),t.source||(e&&(H.events.push(t),t.source=H),W.push(t)),k(W)}function h(e){if(e){if(!t.isFunction(e)){var n=e+"";e=function(t){return t._id==n}}W=t.grep(W,e,!0);for(var r=0;F.length>r;r++)t.isArray(F[r].events)&&(F[r].events=t.grep(F[r].events,e,!0))}else{W=[];for(var r=0;F.length>r;r++)t.isArray(F[r].events)&&(F[r].events=[])}k(W)}function g(e){return t.isFunction(e)?t.grep(W,e):e?(e+="",t.grep(W,function(t){return t._id==e})):W}function m(){z++||T("loading",null,!0,x())}function y(){--z||T("loading",null,!1,x())}function w(t){var r=t.source||{},a=X(r.ignoreTimezone,n.ignoreTimezone);t._id=t._id||(t.id===e?"_fc"+xe++:t.id+""),t.date&&(t.start||(t.start=t.date),delete t.date),t._start=d(t.start=p(t.start,a)),t.end=p(t.end,a),t.end&&t.end<=t.start&&(t.end=null),t._end=t.end?d(t.end):null,t.allDay===e&&(t.allDay=X(r.allDayDefault,n.allDayDefault)),t.className?"string"==typeof t.className&&(t.className=t.className.split(/\s+/)):t.className=[]}function b(t){t.className?"string"==typeof t.className&&(t.className=t.className.split(/\s+/)):t.className=[];for(var e=Ee.sourceNormalizers,n=0;e.length>n;n++)e[n](t)}function D(t,e){return t&&e&&C(t)==C(e)}function C(t){return("object"==typeof t?t.events||t.url:"")||t}var M=this;M.isFetchNeeded=a,M.fetchEvents=o,M.addEventSource=l,M.removeEventSource=u,M.updateEvent=f,M.renderEvent=v,M.removeEvents=h,M.clientEvents=g,M.normalizeEvent=w;for(var E,S,T=M.trigger,x=M.getView,k=M.reportEvents,H={events:[]},F=[H],R=0,N=0,z=0,W=[],A=0;r.length>A;A++)c(r[A])}function i(t,e,n){return t.setFullYear(t.getFullYear()+e),n||f(t),t}function s(t,e,n){if(+t){var r=t.getMonth()+e,a=d(t);for(a.setDate(1),a.setMonth(r),t.setMonth(r),n||f(t);t.getMonth()!=a.getMonth();)t.setDate(t.getDate()+(a>t?1:-1))}return t}function l(t,e,n){if(+t){var r=t.getDate()+e,a=d(t);a.setHours(9),a.setDate(r),t.setDate(r),n||f(t),c(t,a)}return t}function c(t,e){if(+t)for(;t.getDate()!=e.getDate();)t.setTime(+t+(e>t?1:-1)*Fe)}function u(t,e){return t.setMinutes(t.getMinutes()+e),t}function f(t){return t.setHours(0),t.setMinutes(0),t.setSeconds(0),t.setMilliseconds(0),t}function d(t,e){return e?f(new Date(+t)):new Date(+t)}function v(){var t,e=0;do t=new Date(1970,e++,1);while(t.getHours());return t}function h(t,e){return Math.round((d(t,!0)-d(e,!0))/He)}function g(t,n,r,a){n!==e&&n!=t.getFullYear()&&(t.setDate(1),t.setMonth(0),t.setFullYear(n)),r!==e&&r!=t.getMonth()&&(t.setDate(1),t.setMonth(r)),a!==e&&t.setDate(a)}function p(t,n){return"object"==typeof t?t:"number"==typeof t?new Date(1e3*t):"string"==typeof t?t.match(/^\d+(\.\d+)?$/)?new Date(1e3*parseFloat(t)):(n===e&&(n=!0),m(t,n)||(t?new Date(t):null)):null}function m(t,e){var n=t.match(/^([0-9]{4})(-([0-9]{2})(-([0-9]{2})([T ]([0-9]{2}):([0-9]{2})(:([0-9]{2})(\.([0-9]+))?)?(Z|(([-+])([0-9]{2})(:?([0-9]{2}))?))?)?)?)?$/);if(!n)return null;var r=new Date(n[1],0,1);if(e||!n[13]){var a=new Date(n[1],0,1,9,0);n[3]&&(r.setMonth(n[3]-1),a.setMonth(n[3]-1)),n[5]&&(r.setDate(n[5]),a.setDate(n[5])),c(r,a),n[7]&&r.setHours(n[7]),n[8]&&r.setMinutes(n[8]),n[10]&&r.setSeconds(n[10]),n[12]&&r.setMilliseconds(1e3*Number("0."+n[12])),c(r,a)}else if(r.setUTCFullYear(n[1],n[3]?n[3]-1:0,n[5]||1),r.setUTCHours(n[7]||0,n[8]||0,n[10]||0,n[12]?1e3*Number("0."+n[12]):0),n[14]){var o=60*Number(n[16])+(n[18]?Number(n[18]):0);o*="-"==n[15]?1:-1,r=new Date(+r+1e3*60*o)}return r}function y(t){if("number"==typeof t)return 60*t;if("object"==typeof t)return 60*t.getHours()+t.getMinutes();var e=t.match(/(\d+)(?::(\d+))?\s*(\w+)?/);if(e){var n=parseInt(e[1],10);return e[3]&&(n%=12,"p"==e[3].toLowerCase().charAt(0)&&(n+=12)),60*n+(e[2]?parseInt(e[2],10):0)}}function w(t,e,n){return b(t,null,e,n)}function b(t,e,n,r){r=r||Ce;var a,o,i,s,l=t,c=e,u=n.length,f="";for(a=0;u>a;a++)if(o=n.charAt(a),"'"==o){for(i=a+1;u>i;i++)if("'"==n.charAt(i)){l&&(f+=i==a+1?"'":n.substring(a+1,i),a=i);break}}else if("("==o){for(i=a+1;u>i;i++)if(")"==n.charAt(i)){var d=w(l,n.substring(a+1,i),r);parseInt(d.replace(/\D/,""),10)&&(f+=d),a=i;break}}else if("["==o){for(i=a+1;u>i;i++)if("]"==n.charAt(i)){var v=n.substring(a+1,i),d=w(l,v,r);d!=w(c,v,r)&&(f+=d),a=i;break}}else if("{"==o)l=e,c=t;else if("}"==o)l=t,c=e;else{for(i=u;i>a;i--)if(s=Ne[n.substring(a,i)]){l&&(f+=s(l,r)),a=i-1;break}i==a&&l&&(f+=o)}return f}function D(t){var e,n=new Date(t.getTime());return n.setDate(n.getDate()+4-(n.getDay()||7)),e=n.getTime(),n.setMonth(0),n.setDate(1),Math.floor(Math.round((e-n)/864e5)/7)+1}function C(t){return t.end?M(t.end,t.allDay):l(d(t.start),1)}function M(t,e){return t=d(t),e||t.getHours()||t.getMinutes()?l(t,1):f(t)}function E(n,r,a){n.unbind("mouseover").mouseover(function(n){for(var o,i,s,l=n.target;l!=this;)o=l,l=l.parentNode;(i=o._fci)!==e&&(o._fci=e,s=r[i],a(s.event,s.element,s),t(n.target).trigger(n)),n.stopPropagation()})}function S(e,n,r){for(var a,o=0;e.length>o;o++)a=t(e[o]),a.width(Math.max(0,n-x(a,r)))}function T(e,n,r){for(var a,o=0;e.length>o;o++)a=t(e[o]),a.height(Math.max(0,n-R(a,r)))}function x(t,e){return k(t)+F(t)+(e?H(t):0)}function k(e){return(parseFloat(t.css(e[0],"paddingLeft",!0))||0)+(parseFloat(t.css(e[0],"paddingRight",!0))||0)}function H(e){return(parseFloat(t.css(e[0],"marginLeft",!0))||0)+(parseFloat(t.css(e[0],"marginRight",!0))||0)}function F(e){return(parseFloat(t.css(e[0],"borderLeftWidth",!0))||0)+(parseFloat(t.css(e[0],"borderRightWidth",!0))||0)}function R(t,e){return N(t)+W(t)+(e?z(t):0)}function N(e){return(parseFloat(t.css(e[0],"paddingTop",!0))||0)+(parseFloat(t.css(e[0],"paddingBottom",!0))||0)}function z(e){return(parseFloat(t.css(e[0],"marginTop",!0))||0)+(parseFloat(t.css(e[0],"marginBottom",!0))||0)}function W(e){return(parseFloat(t.css(e[0],"borderTopWidth",!0))||0)+(parseFloat(t.css(e[0],"borderBottomWidth",!0))||0)}function A(){}function O(t,e){return t-e}function L(t){return Math.max.apply(Math,t)}function _(t){return(10>t?"0":"")+t}function P(t,n){if(t[n]!==e)return t[n];for(var r,a=n.split(/(?=[A-Z])/),o=a.length-1;o>=0;o--)if(r=t[a[o].toLowerCase()],r!==e)return r;return t[""]}function q(t){return t.replace(/&/g,"&amp;").replace(/</g,"&lt;").replace(/>/g,"&gt;").replace(/'/g,"&#039;").replace(/"/g,"&quot;").replace(/\n/g,"<br />")}function Y(t){t.attr("unselectable","on").css("MozUserSelect","none").bind("selectstart.ui",function(){return!1})}function B(t){t.children().removeClass("fc-first fc-last").filter(":first-child").addClass("fc-first").end().filter(":last-child").addClass("fc-last")}function j(t,e){var n=t.source||{},r=t.color,a=n.color,o=e("eventColor"),i=t.backgroundColor||r||n.backgroundColor||a||e("eventBackgroundColor")||o,s=t.borderColor||r||n.borderColor||a||e("eventBorderColor")||o,l=t.textColor||n.textColor||e("eventTextColor"),c=[];return i&&c.push("background-color:"+i),s&&c.push("border-color:"+s),l&&c.push("color:"+l),c.join(";")}function I(e,n,r){if(t.isFunction(e)&&(e=[e]),e){var a,o;for(a=0;e.length>a;a++)o=e[a].apply(n,r)||o;return o}}function X(){for(var t=0;arguments.length>t;t++)if(arguments[t]!==e)return arguments[t]}function J(t,e){function n(t,e){e&&(s(t,e),t.setDate(1));var n=a("firstDay"),f=d(t,!0);f.setDate(1);var v=s(d(f),1),g=d(f);l(g,-((g.getDay()-n+7)%7)),i(g);var p=d(v);l(p,(7-p.getDay()+n)%7),i(p,-1,!0);var m=c(),y=Math.round(h(p,g)/7);"fixed"==a("weekMode")&&(l(p,7*(6-y)),y=6),r.title=u(f,a("titleFormat")),r.start=f,r.end=v,r.visStart=g,r.visEnd=p,o(y,m,!0)}var r=this;r.render=n,Z.call(r,t,e,"month");var a=r.opt,o=r.renderBasic,i=r.skipHiddenDays,c=r.getCellsPerWeek,u=e.formatDate}function V(t,e){function n(t,e){e&&l(t,7*e);var n=l(d(t),-((t.getDay()-a("firstDay")+7)%7)),u=l(d(n),7),f=d(n);i(f);var v=d(u);i(v,-1,!0);var h=s();r.start=n,r.end=u,r.visStart=f,r.visEnd=v,r.title=c(f,l(d(v),-1),a("titleFormat")),o(1,h,!1)}var r=this;r.render=n,Z.call(r,t,e,"basicWeek");var a=r.opt,o=r.renderBasic,i=r.skipHiddenDays,s=r.getCellsPerWeek,c=e.formatDates}function U(t,e){function n(t,e){e&&l(t,e),i(t,0>e?-1:1);var n=d(t,!0),c=l(d(n),1);r.title=s(t,a("titleFormat")),r.start=r.visStart=n,r.end=r.visEnd=c,o(1,1,!1)}var r=this;r.render=n,Z.call(r,t,e,"basicDay");var a=r.opt,o=r.renderBasic,i=r.skipHiddenDays,s=e.formatDate}function Z(e,n,r){function a(t,e,n){ee=t,ne=e,re=n,o(),j||i(),s()}function o(){le=he("theme")?"ui":"fc",ce=he("columnFormat"),ue=he("weekNumbers"),de=he("weekNumberTitle"),ve="iso"!=he("weekNumberCalculation")?"w":"W"}function i(){Z=t("<div class='fc-event-container' style='position:absolute;z-index:8;top:0;left:0'/>").appendTo(e)}function s(){var n=c();L&&L.remove(),L=t(n).appendTo(e),_=L.find("thead"),P=_.find(".fc-day-header"),j=L.find("tbody"),I=j.find("tr"),X=j.find(".fc-day"),J=I.find("td:first-child"),V=I.eq(0).find(".fc-day > div"),U=I.eq(0).find(".fc-day-content > div"),B(_.add(_.find("tr"))),B(I),I.eq(0).addClass("fc-first"),I.filter(":last").addClass("fc-last"),X.each(function(e,n){var r=Ee(Math.floor(e/ne),e%ne);ge("dayRender",O,r,t(n))}),y(X)}function c(){var t="<table class='fc-border-separate' style='width:100%' cellspacing='0'>"+u()+v()+"</table>";return t}function u(){var t,e,n=le+"-widget-header",r="";for(r+="<thead><tr>",ue&&(r+="<th class='fc-week-number "+n+"'>"+q(de)+"</th>"),t=0;ne>t;t++)e=Ee(0,t),r+="<th class='fc-day-header fc-"+ke[e.getDay()]+" "+n+"'>"+q(xe(e,ce))+"</th>";return r+="</tr></thead>"}function v(){var t,e,n,r=le+"-widget-content",a="";for(a+="<tbody>",t=0;ee>t;t++){for(a+="<tr class='fc-week'>",ue&&(n=Ee(t,0),a+="<td class='fc-week-number "+r+"'>"+"<div>"+q(xe(n,ve))+"</div>"+"</td>"),e=0;ne>e;e++)n=Ee(t,e),a+=h(n);a+="</tr>"}return a+="</tbody>"}function h(t){var e=le+"-widget-content",n=O.start.getMonth(),r=f(new Date),a="",o=["fc-day","fc-"+ke[t.getDay()],e];return t.getMonth()!=n&&o.push("fc-other-month"),+t==+r?o.push("fc-today",le+"-state-highlight"):r>t?o.push("fc-past"):o.push("fc-future"),a+="<td class='"+o.join(" ")+"'"+" data-date='"+xe(t,"yyyy-MM-dd")+"'"+">"+"<div>",re&&(a+="<div class='fc-day-number'>"+t.getDate()+"</div>"),a+="<div class='fc-day-content'><div style='position:relative'>&nbsp;</div></div></div></td>"}function g(e){Q=e;var n,r,a,o=Q-_.height();"variable"==he("weekMode")?n=r=Math.floor(o/(1==ee?2:6)):(n=Math.floor(o/ee),r=o-n*(ee-1)),J.each(function(e,o){ee>e&&(a=t(o),a.find("> div").css("min-height",(e==ee-1?r:n)-R(a)))})}function p(t){$=t,ie.clear(),se.clear(),te=0,ue&&(te=_.find("th.fc-week-number").outerWidth()),K=Math.floor(($-te)/ne),S(P.slice(0,-1),K)}function y(t){t.click(w).mousedown(Me)}function w(e){if(!he("selectable")){var n=m(t(this).data("date"));ge("dayClick",this,n,!0,e)}}function b(t,e,n){n&&ae.build();for(var r=Te(t,e),a=0;r.length>a;a++){var o=r[a];y(D(o.row,o.leftCol,o.row,o.rightCol))}}function D(t,n,r,a){var o=ae.rect(t,n,r,a,e);return be(o,e)}function C(t){return d(t)}function M(t,e){b(t,l(d(e),1),!0)}function E(){Ce()}function T(t,e,n){var r=Se(t),a=X[r.row*ne+r.col];ge("dayClick",a,t,e,n)}function x(t,e){oe.start(function(t){Ce(),t&&D(t.row,t.col,t.row,t.col)},e)}function k(t,e,n){var r=oe.stop();if(Ce(),r){var a=Ee(r);ge("drop",t,a,!0,e,n)}}function H(t){return d(t.start)}function F(t){return ie.left(t)}function N(t){return ie.right(t)}function z(t){return se.left(t)}function W(t){return se.right(t)}function A(t){return I.eq(t)}var O=this;O.renderBasic=a,O.setHeight=g,O.setWidth=p,O.renderDayOverlay=b,O.defaultSelectionEnd=C,O.renderSelection=M,O.clearSelection=E,O.reportDayClick=T,O.dragStart=x,O.dragStop=k,O.defaultEventEnd=H,O.getHoverListener=function(){return oe},O.colLeft=F,O.colRight=N,O.colContentLeft=z,O.colContentRight=W,O.getIsCellAllDay=function(){return!0},O.allDayRow=A,O.getRowCnt=function(){return ee},O.getColCnt=function(){return ne},O.getColWidth=function(){return K},O.getDaySegmentContainer=function(){return Z},fe.call(O,e,n,r),me.call(O),pe.call(O),G.call(O);var L,_,P,j,I,X,J,V,U,Z,$,Q,K,te,ee,ne,re,ae,oe,ie,se,le,ce,ue,de,ve,he=O.opt,ge=O.trigger,be=O.renderOverlay,Ce=O.clearOverlays,Me=O.daySelectionMousedown,Ee=O.cellToDate,Se=O.dateToCell,Te=O.rangeToSegments,xe=n.formatDate;Y(e.addClass("fc-grid")),ae=new ye(function(e,n){var r,a,o;P.each(function(e,i){r=t(i),a=r.offset().left,e&&(o[1]=a),o=[a],n[e]=o}),o[1]=a+r.outerWidth(),I.each(function(n,i){ee>n&&(r=t(i),a=r.offset().top,n&&(o[1]=a),o=[a],e[n]=o)}),o[1]=a+r.outerHeight()}),oe=new we(ae),ie=new De(function(t){return V.eq(t)}),se=new De(function(t){return U.eq(t)})}function G(){function t(t,e){n.renderDayEvents(t,e)}function e(){n.getDaySegmentContainer().empty()}var n=this;n.renderEvents=t,n.clearEvents=e,de.call(n)}function $(t,e){function n(t,e){e&&l(t,7*e);var n=l(d(t),-((t.getDay()-a("firstDay")+7)%7)),u=l(d(n),7),f=d(n);i(f);var v=d(u);i(v,-1,!0);var h=s();r.title=c(f,l(d(v),-1),a("titleFormat")),r.start=n,r.end=u,r.visStart=f,r.visEnd=v,o(h)}var r=this;r.render=n,K.call(r,t,e,"agendaWeek");var a=r.opt,o=r.renderAgenda,i=r.skipHiddenDays,s=r.getCellsPerWeek,c=e.formatDates}function Q(t,e){function n(t,e){e&&l(t,e),i(t,0>e?-1:1);var n=d(t,!0),c=l(d(n),1);r.title=s(t,a("titleFormat")),r.start=r.visStart=n,r.end=r.visEnd=c,o(1)}var r=this;r.render=n,K.call(r,t,e,"agendaDay");var a=r.opt,o=r.renderAgenda,i=r.skipHiddenDays,s=e.formatDate}function K(n,r,a){function o(t){We=t,i(),K?c():s()}function i(){qe=Ue("theme")?"ui":"fc",Ye=Ue("isRTL"),Be=y(Ue("minTime")),je=y(Ue("maxTime")),Ie=Ue("columnFormat"),Xe=Ue("weekNumbers"),Je=Ue("weekNumberTitle"),Ve="iso"!=Ue("weekNumberCalculation")?"w":"W",Re=Ue("snapMinutes")||Ue("slotMinutes")}function s(){var e,r,a,o,i,s=qe+"-widget-header",l=qe+"-widget-content",f=0==Ue("slotMinutes")%15;for(c(),ce=t("<div style='position:absolute;z-index:2;left:0;width:100%'/>").appendTo(n),Ue("allDaySlot")?(ue=t("<div class='fc-event-container' style='position:absolute;z-index:8;top:0;left:0'/>").appendTo(ce),e="<table style='width:100%' class='fc-agenda-allday' cellspacing='0'><tr><th class='"+s+" fc-agenda-axis'>"+Ue("allDayText")+"</th>"+"<td>"+"<div class='fc-day-content'><div style='position:relative'/></div>"+"</td>"+"<th class='"+s+" fc-agenda-gutter'>&nbsp;</th>"+"</tr>"+"</table>",de=t(e).appendTo(ce),ve=de.find("tr"),C(ve.find("td")),ce.append("<div class='fc-agenda-divider "+s+"'>"+"<div class='fc-agenda-divider-inner'/>"+"</div>")):ue=t([]),he=t("<div style='position:absolute;width:100%;overflow-x:hidden;overflow-y:auto'/>").appendTo(ce),ge=t("<div style='position:relative;width:100%;overflow:hidden'/>").appendTo(he),be=t("<div class='fc-event-container' style='position:absolute;z-index:8;top:0;left:0'/>").appendTo(ge),e="<table class='fc-agenda-slots' style='width:100%' cellspacing='0'><tbody>",r=v(),o=u(d(r),je),u(r,Be),Ae=0,a=0;o>r;a++)i=r.getMinutes(),e+="<tr class='fc-slot"+a+" "+(i?"fc-minor":"")+"'>"+"<th class='fc-agenda-axis "+s+"'>"+(f&&i?"&nbsp;":on(r,Ue("axisFormat")))+"</th>"+"<td class='"+l+"'>"+"<div style='position:relative'>&nbsp;</div>"+"</td>"+"</tr>",u(r,Ue("slotMinutes")),Ae++;e+="</tbody></table>",Ce=t(e).appendTo(ge),M(Ce.find("td"))}function c(){var e=h();K&&K.remove(),K=t(e).appendTo(n),ee=K.find("thead"),ne=ee.find("th").slice(1,-1),re=K.find("tbody"),ae=re.find("td").slice(0,-1),oe=ae.find("> div"),ie=ae.find(".fc-day-content > div"),se=ae.eq(0),le=oe.eq(0),B(ee.add(ee.find("tr"))),B(re.add(re.find("tr")))}function h(){var t="<table style='width:100%' class='fc-agenda-days fc-border-separate' cellspacing='0'>"+g()+p()+"</table>";return t}function g(){var t,e,n,r=qe+"-widget-header",a="";for(a+="<thead><tr>",Xe?(t=nn(0,0),e=on(t,Ve),Ye?e+=Je:e=Je+e,a+="<th class='fc-agenda-axis fc-week-number "+r+"'>"+q(e)+"</th>"):a+="<th class='fc-agenda-axis "+r+"'>&nbsp;</th>",n=0;We>n;n++)t=nn(0,n),a+="<th class='fc-"+ke[t.getDay()]+" fc-col"+n+" "+r+"'>"+q(on(t,Ie))+"</th>";return a+="<th class='fc-agenda-gutter "+r+"'>&nbsp;</th>"+"</tr>"+"</thead>"}function p(){var t,e,n,r,a,o=qe+"-widget-header",i=qe+"-widget-content",s=f(new Date),l="";for(l+="<tbody><tr><th class='fc-agenda-axis "+o+"'>&nbsp;</th>",n="",e=0;We>e;e++)t=nn(0,e),a=["fc-col"+e,"fc-"+ke[t.getDay()],i],+t==+s?a.push(qe+"-state-highlight","fc-today"):s>t?a.push("fc-past"):a.push("fc-future"),r="<td class='"+a.join(" ")+"'>"+"<div>"+"<div class='fc-day-content'>"+"<div style='position:relative'>&nbsp;</div>"+"</div>"+"</div>"+"</td>",n+=r;return l+=n,l+="<td class='fc-agenda-gutter "+i+"'>&nbsp;</td>"+"</tr>"+"</tbody>"}function m(t){t===e&&(t=Se),Se=t,sn={};var n=re.position().top,r=he.position().top,a=Math.min(t-n,Ce.height()+r+1);le.height(a-R(se)),ce.css("top",n),he.height(a-r-1),Fe=Ce.find("tr:first").height()+1,Ne=Ue("slotMinutes")/Re,ze=Fe/Ne}function w(e){Ee=e,_e.clear(),Pe.clear();var n=ee.find("th:first");de&&(n=n.add(de.find("th:first"))),n=n.add(Ce.find("th:first")),Te=0,S(n.width("").each(function(e,n){Te=Math.max(Te,t(n).outerWidth())}),Te);var r=K.find(".fc-agenda-gutter");de&&(r=r.add(de.find("th.fc-agenda-gutter")));var a=he[0].clientWidth;He=he.width()-a,He?(S(r,He),r.show().prev().removeClass("fc-last")):r.hide().prev().addClass("fc-last"),xe=Math.floor((a-Te)/We),S(ne.slice(0,-1),xe)}function b(){function t(){he.scrollTop(r)}var e=v(),n=d(e);n.setHours(Ue("firstHour"));var r=_(e,n)+1;t(),setTimeout(t,0)}function D(){b()}function C(t){t.click(E).mousedown(tn)}function M(t){t.click(E).mousedown(U)}function E(t){if(!Ue("selectable")){var e=Math.min(We-1,Math.floor((t.pageX-K.offset().left-Te)/xe)),n=nn(0,e),r=this.parentNode.className.match(/fc-slot(\d+)/);if(r){var a=parseInt(r[1])*Ue("slotMinutes"),o=Math.floor(a/60);n.setHours(o),n.setMinutes(a%60+Be),Ze("dayClick",ae[e],n,!1,t)}else Ze("dayClick",ae[e],n,!0,t)}}function x(t,e,n){n&&Oe.build();for(var r=an(t,e),a=0;r.length>a;a++){var o=r[a];C(k(o.row,o.leftCol,o.row,o.rightCol))}}function k(t,e,n,r){var a=Oe.rect(t,e,n,r,ce);return Ge(a,ce)}function H(t,e){for(var n=0;We>n;n++){var r=nn(0,n),a=l(d(r),1),o=new Date(Math.max(r,t)),i=new Date(Math.min(a,e));if(i>o){var s=Oe.rect(0,n,0,n,ge),c=_(r,o),u=_(r,i);s.top=c,s.height=u-c,M(Ge(s,ge))}}}function F(t){return _e.left(t)}function N(t){return Pe.left(t)}function z(t){return _e.right(t)}function W(t){return Pe.right(t)}function A(t){return Ue("allDaySlot")&&!t.row}function L(t){var e=nn(0,t.col),n=t.row;return Ue("allDaySlot")&&n--,n>=0&&u(e,Be+n*Re),e}function _(t,n){if(t=d(t,!0),u(d(t),Be)>n)return 0;if(n>=u(d(t),je))return Ce.height();var r=Ue("slotMinutes"),a=60*n.getHours()+n.getMinutes()-Be,o=Math.floor(a/r),i=sn[o];return i===e&&(i=sn[o]=Ce.find("tr").eq(o).find("td div")[0].offsetTop),Math.max(0,Math.round(i-1+Fe*(a%r/r)))}function P(){return ve}function j(t){var e=d(t.start);return t.allDay?e:u(e,Ue("defaultEventMinutes"))}function I(t,e){return e?d(t):u(d(t),Ue("slotMinutes"))}function X(t,e,n){n?Ue("allDaySlot")&&x(t,l(d(e),1),!0):J(t,e)}function J(e,n){var r=Ue("selectHelper");if(Oe.build(),r){var a=rn(e).col;if(a>=0&&We>a){var o=Oe.rect(0,a,0,a,ge),i=_(e,e),s=_(e,n);if(s>i){if(o.top=i,o.height=s-i,o.left+=2,o.width-=5,t.isFunction(r)){var l=r(e,n);l&&(o.position="absolute",Me=t(l).css(o).appendTo(ge))}else o.isStart=!0,o.isEnd=!0,Me=t(en({title:"",start:e,end:n,className:["fc-select-helper"],editable:!1},o)),Me.css("opacity",Ue("dragOpacity"));Me&&(M(Me),ge.append(Me),S(Me,o.width,!0),T(Me,o.height,!0))}}}else H(e,n)}function V(){$e(),Me&&(Me.remove(),Me=null)}function U(e){if(1==e.which&&Ue("selectable")){Ke(e);var n;Le.start(function(t,e){if(V(),t&&t.col==e.col&&!A(t)){var r=L(e),a=L(t);n=[r,u(d(r),Re),a,u(d(a),Re)].sort(O),J(n[0],n[3])}else n=null},e),t(document).one("mouseup",function(t){Le.stop(),n&&(+n[0]==+n[1]&&Z(n[0],!1,t),Qe(n[0],n[3],!1,t))})}}function Z(t,e,n){Ze("dayClick",ae[rn(t).col],t,e,n)}function G(t,e){Le.start(function(t){if($e(),t)if(A(t))k(t.row,t.col,t.row,t.col);else{var e=L(t),n=u(d(e),Ue("defaultEventMinutes"));H(e,n)}},e)}function $(t,e,n){var r=Le.stop();$e(),r&&Ze("drop",t,L(r),A(r),e,n)}var Q=this;Q.renderAgenda=o,Q.setWidth=w,Q.setHeight=m,Q.afterRender=D,Q.defaultEventEnd=j,Q.timePosition=_,Q.getIsCellAllDay=A,Q.allDayRow=P,Q.getCoordinateGrid=function(){return Oe},Q.getHoverListener=function(){return Le},Q.colLeft=F,Q.colRight=z,Q.colContentLeft=N,Q.colContentRight=W,Q.getDaySegmentContainer=function(){return ue},Q.getSlotSegmentContainer=function(){return be},Q.getMinMinute=function(){return Be},Q.getMaxMinute=function(){return je},Q.getSlotContainer=function(){return ge},Q.getRowCnt=function(){return 1},Q.getColCnt=function(){return We},Q.getColWidth=function(){return xe},Q.getSnapHeight=function(){return ze},Q.getSnapMinutes=function(){return Re},Q.defaultSelectionEnd=I,Q.renderDayOverlay=x,Q.renderSelection=X,Q.clearSelection=V,Q.reportDayClick=Z,Q.dragStart=G,Q.dragStop=$,fe.call(Q,n,r,a),me.call(Q),pe.call(Q),te.call(Q);var K,ee,ne,re,ae,oe,ie,se,le,ce,ue,de,ve,he,ge,be,Ce,Me,Ee,Se,Te,xe,He,Fe,Re,Ne,ze,We,Ae,Oe,Le,_e,Pe,qe,Ye,Be,je,Ie,Xe,Je,Ve,Ue=Q.opt,Ze=Q.trigger,Ge=Q.renderOverlay,$e=Q.clearOverlays,Qe=Q.reportSelection,Ke=Q.unselect,tn=Q.daySelectionMousedown,en=Q.slotSegHtml,nn=Q.cellToDate,rn=Q.dateToCell,an=Q.rangeToSegments,on=r.formatDate,sn={};Y(n.addClass("fc-agenda")),Oe=new ye(function(e,n){function r(t){return Math.max(l,Math.min(c,t))}var a,o,i;ne.each(function(e,r){a=t(r),o=a.offset().left,e&&(i[1]=o),i=[o],n[e]=i}),i[1]=o+a.outerWidth(),Ue("allDaySlot")&&(a=ve,o=a.offset().top,e[0]=[o,o+a.outerHeight()]);for(var s=ge.offset().top,l=he.offset().top,c=l+he.outerHeight(),u=0;Ae*Ne>u;u++)e.push([r(s+ze*u),r(s+ze*(u+1))])}),Le=new we(Oe),_e=new De(function(t){return oe.eq(t)}),Pe=new De(function(t){return ie.eq(t)})}function te(){function n(t,e){var n,r=t.length,o=[],i=[];for(n=0;r>n;n++)t[n].allDay?o.push(t[n]):i.push(t[n]);y("allDaySlot")&&(te(o,e),k()),s(a(i),e)}function r(){H().empty(),F().empty()}function a(e){var n,r,a,s,l,c=Y(),f=W(),v=z(),h=t.map(e,i),g=[];for(r=0;c>r;r++)for(n=P(0,r),u(n,f),l=o(e,h,n,u(d(n),v-f)),l=ee(l),a=0;l.length>a;a++)s=l[a],s.col=r,g.push(s);return g}function o(t,e,n,r){var a,o,i,s,l,c,u,f,v=[],h=t.length;for(a=0;h>a;a++)o=t[a],i=o.start,s=e[a],s>n&&r>i&&(n>i?(l=d(n),u=!1):(l=i,u=!0),s>r?(c=d(r),f=!1):(c=s,f=!0),v.push({event:o,start:l,end:c,isStart:u,isEnd:f}));return v.sort(ue)}function i(t){return t.end?d(t.end):u(d(t.start),y("defaultEventMinutes"))}function s(n,r){var a,o,i,s,l,u,d,v,h,g,p,m,b,D,C,M,S=n.length,T="",k=F(),H=y("isRTL");for(a=0;S>a;a++)o=n[a],i=o.event,s=A(o.start,o.start),l=A(o.start,o.end),u=L(o.col),d=_(o.col),v=d-u,d-=.025*v,v=d-u,h=v*(o.forwardCoord-o.backwardCoord),y("slotEventOverlap")&&(h=Math.max(2*(h-10),h)),H?(p=d-o.backwardCoord*v,g=p-h):(g=u+o.backwardCoord*v,p=g+h),g=Math.max(g,u),p=Math.min(p,d),h=p-g,o.top=s,o.left=g,o.outerWidth=h,o.outerHeight=l-s,T+=c(i,o);for(k[0].innerHTML=T,m=k.children(),a=0;S>a;a++)o=n[a],i=o.event,b=t(m[a]),D=w("eventRender",i,i,b),D===!1?b.remove():(D&&D!==!0&&(b.remove(),b=t(D).css({position:"absolute",top:o.top,left:o.left}).appendTo(k)),o.element=b,i._id===r?f(i,b,o):b[0]._fci=a,V(i,b));for(E(k,n,f),a=0;S>a;a++)o=n[a],(b=o.element)&&(o.vsides=R(b,!0),o.hsides=x(b,!0),C=b.find(".fc-event-title"),C.length&&(o.contentTop=C[0].offsetTop));for(a=0;S>a;a++)o=n[a],(b=o.element)&&(b[0].style.width=Math.max(0,o.outerWidth-o.hsides)+"px",M=Math.max(0,o.outerHeight-o.vsides),b[0].style.height=M+"px",i=o.event,o.contentTop!==e&&10>M-o.contentTop&&(b.find("div.fc-event-time").text(re(i.start,y("timeFormat"))+" - "+i.title),b.find("div.fc-event-title").remove()),w("eventAfterRender",i,i,b))}function c(t,e){var n="<",r=t.url,a=j(t,y),o=["fc-event","fc-event-vert"];return b(t)&&o.push("fc-event-draggable"),e.isStart&&o.push("fc-event-start"),e.isEnd&&o.push("fc-event-end"),o=o.concat(t.className),t.source&&(o=o.concat(t.source.className||[])),n+=r?"a href='"+q(t.url)+"'":"div",n+=" class='"+o.join(" ")+"'"+" style="+"'"+"position:absolute;"+"top:"+e.top+"px;"+"left:"+e.left+"px;"+a+"'"+">"+"<div class='fc-event-inner'>"+"<div class='fc-event-time'>"+q(ae(t.start,t.end,y("timeFormat")))+"</div>"+"<div class='fc-event-title'>"+q(t.title||"")+"</div>"+"</div>"+"<div class='fc-event-bg'></div>",e.isEnd&&D(t)&&(n+="<div class='ui-resizable-handle ui-resizable-js'>=</div>"),n+="</"+(r?"a":"div")+">"}function f(t,e,n){var r=e.find("div.fc-event-time");b(t)&&g(t,e,r),n.isEnd&&D(t)&&p(t,e,r),S(t,e)}function v(t,e,n){function r(){c||(e.width(a).height("").draggable("option","grid",null),c=!0)}var a,o,i,s=n.isStart,c=!0,u=N(),f=B(),v=I(),g=X(),p=W();e.draggable({opacity:y("dragOpacity","month"),revertDuration:y("dragRevertDuration"),start:function(n,p){w("eventDragStart",e,t,n,p),Z(t,e),a=e.width(),u.start(function(n,a){if(K(),n){o=!1;var u=P(0,a.col),p=P(0,n.col);i=h(p,u),n.row?s?c&&(e.width(f-10),T(e,v*Math.round((t.end?(t.end-t.start)/Re:y("defaultEventMinutes"))/g)),e.draggable("option","grid",[f,1]),c=!1):o=!0:(Q(l(d(t.start),i),l(C(t),i)),r()),o=o||c&&!i
}else r(),o=!0;e.draggable("option","revert",o)},n,"drag")},stop:function(n,a){if(u.stop(),K(),w("eventDragStop",e,t,n,a),o)r(),e.css("filter",""),U(t,e);else{var s=0;c||(s=Math.round((e.offset().top-J().offset().top)/v)*g+p-(60*t.start.getHours()+t.start.getMinutes())),G(this,t,i,s,c,n,a)}}})}function g(t,e,n){function r(){K(),s&&(f?(n.hide(),e.draggable("option","grid",null),Q(l(d(t.start),b),l(C(t),b))):(a(D),n.css("display",""),e.draggable("option","grid",[T,x])))}function a(e){var r,a=u(d(t.start),e);t.end&&(r=u(d(t.end),e)),n.text(ae(a,r,y("timeFormat")))}var o,i,s,c,f,v,g,p,b,D,M,E=m.getCoordinateGrid(),S=Y(),T=B(),x=I(),k=X();e.draggable({scroll:!1,grid:[T,x],axis:1==S?"y":!1,opacity:y("dragOpacity"),revertDuration:y("dragRevertDuration"),start:function(n,r){w("eventDragStart",e,t,n,r),Z(t,e),E.build(),o=e.position(),i=E.cell(n.pageX,n.pageY),s=c=!0,f=v=O(i),g=p=0,b=0,D=M=0},drag:function(t,n){var a=E.cell(t.pageX,t.pageY);if(s=!!a){if(f=O(a),g=Math.round((n.position.left-o.left)/T),g!=p){var l=P(0,i.col),u=i.col+g;u=Math.max(0,u),u=Math.min(S-1,u);var d=P(0,u);b=h(d,l)}f||(D=Math.round((n.position.top-o.top)/x)*k)}(s!=c||f!=v||g!=p||D!=M)&&(r(),c=s,v=f,p=g,M=D),e.draggable("option","revert",!s)},stop:function(n,a){K(),w("eventDragStop",e,t,n,a),s&&(f||b||D)?G(this,t,b,f?0:D,f,n,a):(s=!0,f=!1,g=0,b=0,D=0,r(),e.css("filter",""),e.css(o),U(t,e))}})}function p(t,e,n){var r,a,o=I(),i=X();e.resizable({handles:{s:".ui-resizable-handle"},grid:o,start:function(n,o){r=a=0,Z(t,e),w("eventResizeStart",this,t,n,o)},resize:function(s,l){r=Math.round((Math.max(o,e.height())-l.originalSize.height)/o),r!=a&&(n.text(ae(t.start,r||t.end?u(M(t),i*r):null,y("timeFormat"))),a=r)},stop:function(n,a){w("eventResizeStop",this,t,n,a),r?$(this,t,0,i*r,n,a):U(t,e)}})}var m=this;m.renderEvents=n,m.clearEvents=r,m.slotSegHtml=c,de.call(m);var y=m.opt,w=m.trigger,b=m.isEventDraggable,D=m.isEventResizable,M=m.eventEnd,S=m.eventElementHandlers,k=m.setHeight,H=m.getDaySegmentContainer,F=m.getSlotSegmentContainer,N=m.getHoverListener,z=m.getMaxMinute,W=m.getMinMinute,A=m.timePosition,O=m.getIsCellAllDay,L=m.colContentLeft,_=m.colContentRight,P=m.cellToDate,Y=m.getColCnt,B=m.getColWidth,I=m.getSnapHeight,X=m.getSnapMinutes,J=m.getSlotContainer,V=m.reportEventElement,U=m.showEvents,Z=m.hideEvents,G=m.eventDrop,$=m.eventResize,Q=m.renderDayOverlay,K=m.clearOverlays,te=m.renderDayEvents,ne=m.calendar,re=ne.formatDate,ae=ne.formatDates;m.draggableDayEvent=v}function ee(t){var e,n=ne(t),r=n[0];if(re(n),r){for(e=0;r.length>e;e++)ae(r[e]);for(e=0;r.length>e;e++)oe(r[e],0,0)}return ie(n)}function ne(t){var e,n,r,a=[];for(e=0;t.length>e;e++){for(n=t[e],r=0;a.length>r&&se(n,a[r]).length;r++);(a[r]||(a[r]=[])).push(n)}return a}function re(t){var e,n,r,a,o;for(e=0;t.length>e;e++)for(n=t[e],r=0;n.length>r;r++)for(a=n[r],a.forwardSegs=[],o=e+1;t.length>o;o++)se(a,t[o],a.forwardSegs)}function ae(t){var n,r,a=t.forwardSegs,o=0;if(t.forwardPressure===e){for(n=0;a.length>n;n++)r=a[n],ae(r),o=Math.max(o,1+r.forwardPressure);t.forwardPressure=o}}function oe(t,n,r){var a,o=t.forwardSegs;if(t.forwardCoord===e)for(o.length?(o.sort(ce),oe(o[0],n+1,r),t.forwardCoord=o[0].backwardCoord):t.forwardCoord=1,t.backwardCoord=t.forwardCoord-(t.forwardCoord-r)/(n+1),a=0;o.length>a;a++)oe(o[a],0,t.forwardCoord)}function ie(t){var e,n,r,a=[];for(e=0;t.length>e;e++)for(n=t[e],r=0;n.length>r;r++)a.push(n[r]);return a}function se(t,e,n){n=n||[];for(var r=0;e.length>r;r++)le(t,e[r])&&n.push(e[r]);return n}function le(t,e){return t.end>e.start&&t.start<e.end}function ce(t,e){return e.forwardPressure-t.forwardPressure||(t.backwardCoord||0)-(e.backwardCoord||0)||ue(t,e)}function ue(t,e){return t.start-e.start||e.end-e.start-(t.end-t.start)||(t.event.title||"").localeCompare(e.event.title)}function fe(n,r,a){function o(e,n){var r=V[e];return t.isPlainObject(r)?P(r,n||a):r}function i(t,e){return r.trigger.apply(r,[t,e||_].concat(Array.prototype.slice.call(arguments,2),[_]))}function s(t){var e=t.source||{};return X(t.startEditable,e.startEditable,o("eventStartEditable"),t.editable,e.editable,o("editable"))&&!o("disableDragging")}function c(t){var e=t.source||{};return X(t.durationEditable,e.durationEditable,o("eventDurationEditable"),t.editable,e.editable,o("editable"))&&!o("disableResizing")}function f(t){j={};var e,n,r=t.length;for(e=0;r>e;e++)n=t[e],j[n._id]?j[n._id].push(n):j[n._id]=[n]}function v(){j={},I={},J=[]}function g(t){return t.end?d(t.end):q(t)}function p(t,e){J.push({event:t,element:e}),I[t._id]?I[t._id].push(e):I[t._id]=[e]}function m(){t.each(J,function(t,e){_.trigger("eventDestroy",e.event,e.event,e.element)})}function y(t,n){n.click(function(r){return n.hasClass("ui-draggable-dragging")||n.hasClass("ui-resizable-resizing")?e:i("eventClick",this,t,r)}).hover(function(e){i("eventMouseover",this,t,e)},function(e){i("eventMouseout",this,t,e)})}function w(t,e){D(t,e,"show")}function b(t,e){D(t,e,"hide")}function D(t,e,n){var r,a=I[t._id],o=a.length;for(r=0;o>r;r++)e&&a[r][0]==e[0]||a[r][n]()}function C(t,e,n,r,a,o,s){var l=e.allDay,c=e._id;E(j[c],n,r,a),i("eventDrop",t,e,n,r,a,function(){E(j[c],-n,-r,l),B(c)},o,s),B(c)}function M(t,e,n,r,a,o){var s=e._id;S(j[s],n,r),i("eventResize",t,e,n,r,function(){S(j[s],-n,-r),B(s)},a,o),B(s)}function E(t,n,r,a){r=r||0;for(var o,i=t.length,s=0;i>s;s++)o=t[s],a!==e&&(o.allDay=a),u(l(o.start,n,!0),r),o.end&&(o.end=u(l(o.end,n,!0),r)),Y(o,V)}function S(t,e,n){n=n||0;for(var r,a=t.length,o=0;a>o;o++)r=t[o],r.end=u(l(g(r),e,!0),n),Y(r,V)}function T(t){return"object"==typeof t&&(t=t.getDay()),G[t]}function x(){return U}function k(t,e,n){for(e=e||1;G[(t.getDay()+(n?e:0)+7)%7];)l(t,e)}function H(){var t=F.apply(null,arguments),e=R(t),n=N(e);return n}function F(t,e){var n=_.getColCnt(),r=K?-1:1,a=K?n-1:0;"object"==typeof t&&(e=t.col,t=t.row);var o=t*n+(e*r+a);return o}function R(t){var e=_.visStart.getDay();return t+=$[e],7*Math.floor(t/U)+Q[(t%U+U)%U]-e}function N(t){var e=d(_.visStart);return l(e,t),e}function z(t){var e=W(t),n=A(e),r=O(n);return r}function W(t){return h(t,_.visStart)}function A(t){var e=_.visStart.getDay();return t+=e,Math.floor(t/7)*U+$[(t%7+7)%7]-$[e]}function O(t){var e=_.getColCnt(),n=K?-1:1,r=K?e-1:0,a=Math.floor(t/e),o=(t%e+e)%e*n+r;return{row:a,col:o}}function L(t,e){for(var n=_.getRowCnt(),r=_.getColCnt(),a=[],o=W(t),i=W(e),s=A(o),l=A(i)-1,c=0;n>c;c++){var u=c*r,f=u+r-1,d=Math.max(s,u),v=Math.min(l,f);if(v>=d){var h=O(d),g=O(v),p=[h.col,g.col].sort(),m=R(d)==o,y=R(v)+1==i;a.push({row:c,leftCol:p[0],rightCol:p[1],isStart:m,isEnd:y})}}return a}var _=this;_.element=n,_.calendar=r,_.name=a,_.opt=o,_.trigger=i,_.isEventDraggable=s,_.isEventResizable=c,_.setEventData=f,_.clearEventData=v,_.eventEnd=g,_.reportEventElement=p,_.triggerEventDestroy=m,_.eventElementHandlers=y,_.showEvents=w,_.hideEvents=b,_.eventDrop=C,_.eventResize=M;var q=_.defaultEventEnd,Y=r.normalizeEvent,B=r.reportEventChange,j={},I={},J=[],V=r.options;_.isHiddenDay=T,_.skipHiddenDays=k,_.getCellsPerWeek=x,_.dateToCell=z,_.dateToDayOffset=W,_.dayOffsetToCellOffset=A,_.cellOffsetToCell=O,_.cellToDate=H,_.cellToCellOffset=F,_.cellOffsetToDayOffset=R,_.dayOffsetToDate=N,_.rangeToSegments=L;var U,Z=o("hiddenDays")||[],G=[],$=[],Q=[],K=o("isRTL");(function(){o("weekends")===!1&&Z.push(0,6);for(var e=0,n=0;7>e;e++)$[e]=n,G[e]=-1!=t.inArray(e,Z),G[e]||(Q[n]=e,n++);if(U=n,!U)throw"invalid hiddenDays"})()}function de(){function e(t,e){var n=r(t,!1,!0);he(n,function(t,e){N(t.event,e)}),w(n,e),he(n,function(t,e){k("eventAfterRender",t.event,t.event,e)})}function n(t,e,n){var a=r([t],!0,!1),o=[];return he(a,function(t,r){t.row===e&&r.css("top",n),o.push(r[0])}),o}function r(e,n,r){var o,l,c=Z(),d=n?t("<div/>"):c,v=a(e);return i(v),o=s(v),d[0].innerHTML=o,l=d.children(),n&&c.append(l),u(v,l),he(v,function(t,e){t.hsides=x(e,!0)}),he(v,function(t,e){e.width(Math.max(0,t.outerWidth-t.hsides))}),he(v,function(t,e){t.outerHeight=e.outerHeight(!0)}),f(v,r),v}function a(t){for(var e=[],n=0;t.length>n;n++){var r=o(t[n]);e.push.apply(e,r)}return e}function o(t){for(var e=t.start,n=C(t),r=ee(e,n),a=0;r.length>a;a++)r[a].event=t;return r}function i(t){for(var e=T("isRTL"),n=0;t.length>n;n++){var r=t[n],a=(e?r.isEnd:r.isStart)?V:X,o=(e?r.isStart:r.isEnd)?U:J,i=a(r.leftCol),s=o(r.rightCol);r.left=i,r.outerWidth=s-i}}function s(t){for(var e="",n=0;t.length>n;n++)e+=c(t[n]);return e}function c(t){var e="",n=T("isRTL"),r=t.event,a=r.url,o=["fc-event","fc-event-hori"];H(r)&&o.push("fc-event-draggable"),t.isStart&&o.push("fc-event-start"),t.isEnd&&o.push("fc-event-end"),o=o.concat(r.className),r.source&&(o=o.concat(r.source.className||[]));var i=j(r,T);return e+=a?"<a href='"+q(a)+"'":"<div",e+=" class='"+o.join(" ")+"'"+" style="+"'"+"position:absolute;"+"left:"+t.left+"px;"+i+"'"+">"+"<div class='fc-event-inner'>",!r.allDay&&t.isStart&&(e+="<span class='fc-event-time'>"+q(G(r.start,r.end,T("timeFormat")))+"</span>"),e+="<span class='fc-event-title'>"+q(r.title||"")+"</span>"+"</div>",t.isEnd&&F(r)&&(e+="<div class='ui-resizable-handle ui-resizable-"+(n?"w":"e")+"'>"+"&nbsp;&nbsp;&nbsp;"+"</div>"),e+="</"+(a?"a":"div")+">"}function u(e,n){for(var r=0;e.length>r;r++){var a=e[r],o=a.event,i=n.eq(r),s=k("eventRender",o,o,i);s===!1?i.remove():(s&&s!==!0&&(s=t(s).css({position:"absolute",left:a.left}),i.replaceWith(s),i=s),a.element=i)}}function f(t,e){var n=v(t),r=y(),a=[];if(e)for(var o=0;r.length>o;o++)r[o].height(n[o]);for(var o=0;r.length>o;o++)a.push(r[o].position().top);he(t,function(t,e){e.css("top",a[t.row]+t.top)})}function v(t){for(var e=P(),n=B(),r=[],a=g(t),o=0;e>o;o++){for(var i=a[o],s=[],l=0;n>l;l++)s.push(0);for(var c=0;i.length>c;c++){var u=i[c];u.top=L(s.slice(u.leftCol,u.rightCol+1));for(var l=u.leftCol;u.rightCol>=l;l++)s[l]=u.top+u.outerHeight}r.push(L(s))}return r}function g(t){var e,n,r,a=P(),o=[];for(e=0;t.length>e;e++)n=t[e],r=n.row,n.element&&(o[r]?o[r].push(n):o[r]=[n]);for(r=0;a>r;r++)o[r]=p(o[r]||[]);return o}function p(t){for(var e=[],n=m(t),r=0;n.length>r;r++)e.push.apply(e,n[r]);return e}function m(t){t.sort(ge);for(var e=[],n=0;t.length>n;n++){for(var r=t[n],a=0;e.length>a&&ve(r,e[a]);a++);e[a]?e[a].push(r):e[a]=[r]}return e}function y(){var t,e=P(),n=[];for(t=0;e>t;t++)n[t]=I(t).find("div.fc-day-content > div");return n}function w(t,e){var n=Z();he(t,function(t,n,r){var a=t.event;a._id===e?b(a,n,t):n[0]._fci=r}),E(n,t,b)}function b(t,e,n){H(t)&&S.draggableDayEvent(t,e,n),n.isEnd&&F(t)&&S.resizableDayEvent(t,e,n),z(t,e)}function D(t,e){var n,r=te();e.draggable({delay:50,opacity:T("dragOpacity"),revertDuration:T("dragRevertDuration"),start:function(a,o){k("eventDragStart",e,t,a,o),A(t,e),r.start(function(r,a,o,i){if(e.draggable("option","revert",!r||!o&&!i),Q(),r){var s=ne(a),c=ne(r);n=h(c,s),$(l(d(t.start),n),l(C(t),n))}else n=0},a,"drag")},stop:function(a,o){r.stop(),Q(),k("eventDragStop",e,t,a,o),n?O(this,t,n,0,t.allDay,a,o):(e.css("filter",""),W(t,e))}})}function M(e,r,a){var o=T("isRTL"),i=o?"w":"e",s=r.find(".ui-resizable-"+i),c=!1;Y(r),r.mousedown(function(t){t.preventDefault()}).click(function(t){c&&(t.preventDefault(),t.stopImmediatePropagation())}),s.mousedown(function(o){function s(n){k("eventResizeStop",this,e,n),t("body").css("cursor",""),u.stop(),Q(),f&&_(this,e,f,0,n),setTimeout(function(){c=!1},0)}if(1==o.which){c=!0;var u=te();P(),B();var f,d,v=r.css("top"),h=t.extend({},e),g=ie(oe(e.start));K(),t("body").css("cursor",i+"-resize").one("mouseup",s),k("eventResizeStart",this,e,o),u.start(function(r,o){if(r){var s=re(o),c=re(r);if(c=Math.max(c,g),f=ae(c)-ae(s)){h.end=l(R(e),f,!0);var u=d;d=n(h,a.row,v),d=t(d),d.find("*").css("cursor",i+"-resize"),u&&u.remove(),A(e)}else d&&(W(e),d.remove(),d=null);Q(),$(e.start,l(C(e),f))}},o)}})}var S=this;S.renderDayEvents=e,S.draggableDayEvent=D,S.resizableDayEvent=M;var T=S.opt,k=S.trigger,H=S.isEventDraggable,F=S.isEventResizable,R=S.eventEnd,N=S.reportEventElement,z=S.eventElementHandlers,W=S.showEvents,A=S.hideEvents,O=S.eventDrop,_=S.eventResize,P=S.getRowCnt,B=S.getColCnt;S.getColWidth;var I=S.allDayRow,X=S.colLeft,J=S.colRight,V=S.colContentLeft,U=S.colContentRight;S.dateToCell;var Z=S.getDaySegmentContainer,G=S.calendar.formatDates,$=S.renderDayOverlay,Q=S.clearOverlays,K=S.clearSelection,te=S.getHoverListener,ee=S.rangeToSegments,ne=S.cellToDate,re=S.cellToCellOffset,ae=S.cellOffsetToDayOffset,oe=S.dateToDayOffset,ie=S.dayOffsetToCellOffset}function ve(t,e){for(var n=0;e.length>n;n++){var r=e[n];if(r.leftCol<=t.rightCol&&r.rightCol>=t.leftCol)return!0}return!1}function he(t,e){for(var n=0;t.length>n;n++){var r=t[n],a=r.element;a&&e(r,a,n)}}function ge(t,e){return e.rightCol-e.leftCol-(t.rightCol-t.leftCol)||e.event.allDay-t.event.allDay||t.event.start-e.event.start||(t.event.title||"").localeCompare(e.event.title)}function pe(){function e(t,e,a){n(),e||(e=l(t,a)),c(t,e,a),r(t,e,a)}function n(t){f&&(f=!1,u(),s("unselect",null,t))}function r(t,e,n,r){f=!0,s("select",null,t,e,n,r)}function a(e){var a=o.cellToDate,s=o.getIsCellAllDay,l=o.getHoverListener(),f=o.reportDayClick;if(1==e.which&&i("selectable")){n(e);var d;l.start(function(t,e){u(),t&&s(t)?(d=[a(e),a(t)].sort(O),c(d[0],d[1],!0)):d=null},e),t(document).one("mouseup",function(t){l.stop(),d&&(+d[0]==+d[1]&&f(d[0],!0,t),r(d[0],d[1],!0,t))})}}var o=this;o.select=e,o.unselect=n,o.reportSelection=r,o.daySelectionMousedown=a;var i=o.opt,s=o.trigger,l=o.defaultSelectionEnd,c=o.renderSelection,u=o.clearSelection,f=!1;i("selectable")&&i("unselectAuto")&&t(document).mousedown(function(e){var r=i("unselectCancel");r&&t(e.target).parents(r).length||n(e)})}function me(){function e(e,n){var r=o.shift();return r||(r=t("<div class='fc-cell-overlay' style='position:absolute;z-index:3'/>")),r[0].parentNode!=n[0]&&r.appendTo(n),a.push(r.css(e).show()),r}function n(){for(var t;t=a.shift();)o.push(t.hide().unbind())}var r=this;r.renderOverlay=e,r.clearOverlays=n;var a=[],o=[]}function ye(t){var e,n,r=this;r.build=function(){e=[],n=[],t(e,n)},r.cell=function(t,r){var a,o=e.length,i=n.length,s=-1,l=-1;for(a=0;o>a;a++)if(r>=e[a][0]&&e[a][1]>r){s=a;break}for(a=0;i>a;a++)if(t>=n[a][0]&&n[a][1]>t){l=a;break}return s>=0&&l>=0?{row:s,col:l}:null},r.rect=function(t,r,a,o,i){var s=i.offset();return{top:e[t][0]-s.top,left:n[r][0]-s.left,width:n[o][1]-n[r][0],height:e[a][1]-e[t][0]}}}function we(e){function n(t){be(t);var n=e.cell(t.pageX,t.pageY);(!n!=!i||n&&(n.row!=i.row||n.col!=i.col))&&(n?(o||(o=n),a(n,o,n.row-o.row,n.col-o.col)):a(n,o),i=n)}var r,a,o,i,s=this;s.start=function(s,l,c){a=s,o=i=null,e.build(),n(l),r=c||"mousemove",t(document).bind(r,n)},s.stop=function(){return t(document).unbind(r,n),i}}function be(t){t.pageX===e&&(t.pageX=t.originalEvent.pageX,t.pageY=t.originalEvent.pageY)}function De(t){function n(e){return a[e]=a[e]||t(e)}var r=this,a={},o={},i={};r.left=function(t){return o[t]=o[t]===e?n(t).position().left:o[t]},r.right=function(t){return i[t]=i[t]===e?r.left(t)+n(t).width():i[t]},r.clear=function(){a={},o={},i={}}}var Ce={defaultView:"month",aspectRatio:1.35,header:{left:"title",center:"",right:"today prev,next"},weekends:!0,weekNumbers:!1,weekNumberCalculation:"iso",weekNumberTitle:"W",allDayDefault:!0,ignoreTimezone:!0,lazyFetching:!0,startParam:"start",endParam:"end",titleFormat:{month:"MMMM yyyy",week:"MMM d[ yyyy]{ '&#8212;'[ MMM] d yyyy}",day:"dddd, MMM d, yyyy"},columnFormat:{month:"ddd",week:"ddd M/d",day:"dddd M/d"},timeFormat:{"":"h(:mm)t"},isRTL:!1,firstDay:0,monthNames:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十月","十二月"],monthNamesShort:["一","二","三","四","五","六","七","八","九","十","十一","十二"],dayNames:["星期日","星期一","星期二","星期三","星期四","星期五","星期六"],dayNamesShort:["日","一","二","三","四","五","六"],buttonText:{prev:"<span class='fc-text-arrow'>&lsaquo;</span>",next:"<span class='fc-text-arrow'>&rsaquo;</span>",prevYear:"<span class='fc-text-arrow'>&laquo;</span>",nextYear:"<span class='fc-text-arrow'>&raquo;</span>",today:"今天",month:"月",week:"周",day:"天"},theme:!1,buttonIcons:{prev:"circle-triangle-w",next:"circle-triangle-e"},unselectAuto:!0,dropAccept:"*",handleWindowResize:!0},Me={header:{left:"next,prev today",center:"",right:"title"},buttonText:{prev:"<span class='fc-text-arrow'>&rsaquo;</span>",next:"<span class='fc-text-arrow'>&lsaquo;</span>",prevYear:"<span class='fc-text-arrow'>&raquo;</span>",nextYear:"<span class='fc-text-arrow'>&laquo;</span>"},buttonIcons:{prev:"circle-triangle-e",next:"circle-triangle-w"}},Ee=t.fullCalendar={version:"1.6.4"},Se=Ee.views={};t.fn.fullCalendar=function(n){if("string"==typeof n){var a,o=Array.prototype.slice.call(arguments,1);return this.each(function(){var r=t.data(this,"fullCalendar");if(r&&t.isFunction(r[n])){var i=r[n].apply(r,o);a===e&&(a=i),"destroy"==n&&t.removeData(this,"fullCalendar")}}),a!==e?a:this}n=n||{};var i=n.eventSources||[];return delete n.eventSources,n.events&&(i.push(n.events),delete n.events),n=t.extend(!0,{},Ce,n.isRTL||n.isRTL===e&&Ce.isRTL?Me:{},n),this.each(function(e,a){var o=t(a),s=new r(o,n,i);o.data("fullCalendar",s),s.render()}),this},Ee.sourceNormalizers=[],Ee.sourceFetchers=[];var Te={dataType:"json",cache:!1},xe=1;Ee.addDays=l,Ee.cloneDate=d,Ee.parseDate=p,Ee.parseISO8601=m,Ee.parseTime=y,Ee.formatDate=w,Ee.formatDates=b;var ke=["日","一","二","三","四","五","六"],He=864e5,Fe=36e5,Re=6e4,Ne={s:function(t){return t.getSeconds()},ss:function(t){return _(t.getSeconds())},m:function(t){return t.getMinutes()},mm:function(t){return _(t.getMinutes())},h:function(t){return t.getHours()%12||12},hh:function(t){return _(t.getHours()%12||12)},H:function(t){return t.getHours()},HH:function(t){return _(t.getHours())},d:function(t){return t.getDate()},dd:function(t){return _(t.getDate())},ddd:function(t,e){return e.dayNamesShort[t.getDay()]},dddd:function(t,e){return e.dayNames[t.getDay()]},M:function(t){return t.getMonth()+1},MM:function(t){return _(t.getMonth()+1)},MMM:function(t,e){return e.monthNamesShort[t.getMonth()]},MMMM:function(t,e){return e.monthNames[t.getMonth()]},yy:function(t){return(t.getFullYear()+"").substring(2)},yyyy:function(t){return t.getFullYear()},t:function(t){return 12>t.getHours()?"a":"p"},tt:function(t){return 12>t.getHours()?"上午":"下午"},T:function(t){return 12>t.getHours()?"A":"P"},TT:function(t){return 12>t.getHours()?"上午":"下午"},u:function(t){return w(t,"yyyy-MM-dd'T'HH:mm:ss'Z'")},S:function(t){var e=t.getDate();return e>10&&20>e?"th":["st","nd","rd"][e%10-1]||"th"},w:function(t,e){return e.weekNumberCalculation(t)},W:function(t){return D(t)}};Ee.dateFormatters=Ne,Ee.applyAll=I,Se.month=J,Se.basicWeek=V,Se.basicDay=U,n({weekMode:"fixed"}),Se.agendaWeek=$,Se.agendaDay=Q,n({allDaySlot:!0,allDayText:"全天",firstHour:6,slotMinutes:30,defaultEventMinutes:120,axisFormat:"h(:mm)tt",timeFormat:{agenda:"h:mm{ - h:mm}"},dragOpacity:{agenda:.5},minTime:0,maxTime:24,slotEventOverlap:!0})})(jQuery);
/********************************************************************
 *	Kalendae, a framework agnostic javascript date picker           *
 *	Copyright(c) 2013-2016 Jarvis Badgley (chipersoft@gmail.com)    *
 *	http://github.com/ChiperSoft/Kalendae                           *
 *	Version 0.6.1                                                   *
 ********************************************************************/
(function (undefined) {

    (function (factory) {
        if (typeof define === 'function' && define.amd) {
            // AMD. Register as an anonymous module.
            define([], factory);
        } else if (typeof exports === 'object') {
            // Node/CommonJS
            module.exports = factory();
        } else {
            // Browser globals
            window.Kalendae = factory();
        }
    }(function () {

        var moment;
        var getTodayYearDate = function () {
            return Kalendae.moment().startOf('day').yearDay();
        };

        var Kalendae = function (targetElement, options) {
            if (typeof document.addEventListener !== 'function' && !util.isIE8())
                return;

            //if the first argument isn't an element and isn't a string, assume that it is the options object
            var is_element = false;
            try {
                is_element = targetElement instanceof Element;
            }
            catch (err) {
                is_element = !!targetElement && is_element.nodeType === 1;
            }
            if (!(is_element || typeof (targetElement) === 'string'))
                options = targetElement;

            var self = this,
                    classes = self.classes,
                    opts = self.settings = util.merge(self.defaults, {attachTo: targetElement}, options || {}),
                    $container = self.container = util.make('div', {'class': classes.container}),
                    calendars = self.calendars = [],
                    startDay = moment().day(opts.weekStart),
                    vsd,
                    columnHeaders = [],
                    $cal,
                    $title,
                    $caption,
                    $header,
                    $days, $week, dayNodes = [],
                    $span,
                    i = 0,
                    j = opts.months;

            if (util.isIE8())
                util.addClassName($container, 'ie8');

            //generate the column headers (Su, Mo, Tu, etc)
            i = 7;
            while (i--) {
                columnHeaders.push(startDay.format(opts.columnHeaderFormat));
                startDay.add(1, 'days');
            }

            //setup publish/subscribe and apply any subscriptions passed in settings
            MinPubSub(self);
            if (typeof opts.subscribe === 'object') {
                for (i in opts.subscribe)
                    if (opts.subscribe.hasOwnProperty(i)) {
                        self.subscribe(i, opts.subscribe[i]);
                    }
            }

            //set the view month
            if (!!opts.viewStartDate) {
                vsd = moment(opts.viewStartDate, opts.format);
            } else {
                vsd = moment();
            }
            self.viewStartDate = vsd.date(1);

            //process default selected dates
            self._sel = [];
            if (!!opts.selected) {
                self.setSelected(opts.selected, false);
                self.viewStartDate = moment(self._sel[0]);
            }

            var viewDelta = ({
                'past': opts.months - 1,
                'today-past': opts.months - 1,
                'any': opts.months > 2 ? Math.floor(opts.months / 2) : 0,
                'today-future': 0,
                'future': 0
            })[this.settings.direction];


            if (viewDelta && moment().month() == moment(self.viewStartDate).month()) {
                self.viewStartDate = moment(self.viewStartDate).subtract({M: viewDelta}).date(1);
            }

            // store the view that the calendar initialized with in-case we want to reset.
            self.defaultView = moment(self.viewStartDate);

            if (typeof opts.blackout === 'function') {
                self.blackout = opts.blackout;
            } else if (!!opts.blackout) {
                var bdates = parseDates(opts.blackout, opts.parseSplitDelimiter, opts.format);
                self.blackout = function (input) {
                    input = moment(input).startOf('day').yearDay();
                    if (input < 1 || !self._sel)
                        return false;
                    var i = bdates.length;
                    while (i--)
                        if (bdates[i].startOf('day').yearDay() === input)
                            return true;
                    return false;
                };
            } else {
                self.blackout = function () {
                    return false;
                };
            }


            self.direction = self.directions[opts.direction] ? self.directions[opts.direction] : self.directions['any'];


            //for the total months setting, generate N calendar views and add them to the container
            j = Math.max(opts.months, 1);
            while (j--) {
                $cal = util.make('div', {'class': classes.calendar}, $container);

                $cal.setAttribute('data-cal-index', j);
                if (opts.months > 1) {
                    if (j == Math.max(opts.months - 1, 1))
                        util.addClassName($cal, classes.monthFirst);
                    else if (j === 0)
                        util.addClassName($cal, classes.monthLast);
                    else
                        util.addClassName($cal, classes.monthMiddle);
                }

                //title bar
                $title = util.make('div', {'class': classes.title}, $cal);
                if (!opts.useYearNav) {
                    util.addClassName($title, classes.disableYearNav);
                }
                util.make('a', {'class': classes.previousYear}, $title);           //previous button
                util.make('a', {'class': classes.previousMonth}, $title);          //previous button
                util.make('a', {'class': classes.nextYear}, $title);               //next button
                util.make('a', {'class': classes.nextMonth}, $title);              //next button
                $caption = util.make('span', {'class': classes.caption}, $title);  //title caption

                //column headers
                $header = util.make('div', {'class': classes.header + ' ' + (opts.dayHeaderClickable == true ? classes.dayActive : '')}, $cal);
                i = 0;
                do {
                    $span = util.make('span', {'data-day': i}, $header);

                    if (opts.dayHeaderClickable == true && opts.mode == 'multiple') {
                        $span.addEventListener("mouseover", function (e) {
                            var daysContainer = e.target.parentNode.nextSibling;
                            daysToHover = daysContainer.getElementsByClassName('k-day-week-' + e.target.getAttribute('data-day'));
                            if (daysToHover.length > 0) {
                                for (var i = 0; i < daysToHover.length; i++) {
                                    if (util.hasClassName(daysToHover[i], classes.dayActive))
                                        util.addClassName(daysToHover[i], 'k-day-hover-active');
                                }
                            }
                        });
                        $span.addEventListener("mouseleave", function (e) {
                            var daysContainer = e.target.parentNode.nextSibling;
                            daysToHover = daysContainer.getElementsByClassName('k-day-week-' + e.target.getAttribute('data-day'));
                            if (daysToHover.length > 0) {
                                for (var i = 0; i < daysToHover.length; i++) {
                                    if (util.hasClassName(daysToHover[i], classes.dayActive))
                                        util.removeClassName(daysToHover[i], 'k-day-hover-active');
                                }
                            }
                        });
                    }
                    $span.innerHTML = columnHeaders[i];
                } while (++i < 7);

                //individual day cells
                $days = util.make('div', {'class': classes.days}, $cal);
                i = 0;
                dayNodes = [];
                do {
                    if (opts.mode == 'week') {
                        if ((i % 7) === 0) {
                            $week = util.make('div', {'class': classes.week + ' clearfix'}, $days);
                            dayNodes.push($week);
                        }
                        util.make('span', {}, $week);
                    } else {
                        dayNodes.push(util.make('span', {}, $days));
                    }
                } while (++i < 42);

                //store each calendar view for easy redrawing
                calendars.push({
                    header: $header,
                    caption: $caption,
                    days: dayNodes
                });

                if (j)
                    util.make('div', {'class': classes.monthSeparator}, $container);
            }

            self.draw();

            util.addEvent($container, 'mousedown', function (event, target) {
                var clickedDate;
                if (util.hasClassName(target, classes.nextMonth)) {
                    //NEXT MONTH BUTTON
                    if (!self.disableNext && self.publish('view-changed', self, ['next-month']) !== false) {
                        self.viewStartDate.add(1, 'months');
                        self.draw();
                    }
                    return false;

                } else if (util.hasClassName(target, classes.previousMonth)) {
                    //PREVIOUS MONTH BUTTON
                    if (!self.disablePreviousMonth && self.publish('view-changed', self, ['previous-month']) !== false) {
                        self.viewStartDate.subtract(1, 'months');
                        self.draw();
                    }
                    return false;

                } else if (util.hasClassName(target, classes.nextYear)) {
                    //NEXT MONTH BUTTON
                    if (!self.disableNext && self.publish('view-changed', self, ['next-year']) !== false) {
                        self.viewStartDate.add(1, 'years');
                        self.draw();
                    }
                    return false;

                } else if (util.hasClassName(target, classes.previousYear)) {
                    //PREVIOUS MONTH BUTTON
                    if (!self.disablePreviousMonth && self.publish('view-changed', self, ['previous-year']) !== false) {
                        self.viewStartDate.subtract(1, 'years');
                        self.draw();
                    }
                    return false;

                } else if ((util.hasClassName(target.parentNode, classes.days) || util.hasClassName(target.parentNode, classes.week)) && util.hasClassName(target, classes.dayActive) && (clickedDate = target.getAttribute('data-date'))) {
                    //DAY CLICK
                    clickedDate = moment(clickedDate, opts.dayAttributeFormat).hours(12);
                    if (self.publish('date-clicked', self, [clickedDate]) !== false) {

                        switch (opts.mode) {
                            case 'multiple':
                                if (!self.addSelected(clickedDate))
                                    self.removeSelected(clickedDate);
                                break;
                            case 'range':
                                self.addSelected(clickedDate);
                                break;
                            case 'week':
                                self.weekSelected(clickedDate);
                                break;
                            case 'single':
                                /* falls through */
                            default:
                                self.addSelected(clickedDate);
                                break;
                        }

                    }
                    return false;

                } else if (util.hasClassName(target.parentNode, classes.week) && (clickedDate = target.getAttribute('data-date'))) {
                    //INACTIVE WEEK CLICK
                    clickedDate = moment(clickedDate, opts.dayAttributeFormat).hours(12);
                    if (self.publish('date-clicked', self, [clickedDate]) !== false) {
                        if (opts.mode == 'week') {
                            self.weekSelected(clickedDate);
                        }
                    }
                    return false;

                } else if (util.hasClassName(target.parentNode, classes.header)) {
                    if (opts.mode == 'multiple' && opts.dayHeaderClickable == true) {
                        var parentSelected = util.hasClassName(target, classes.daySelected),
                                month = target.parentNode.parentNode.getAttribute('data-datestart'),
                                dayToSelect = target.getAttribute('data-day');

                        if (parentSelected == true) {
                            self.monthDaySelected(month, dayToSelect, true);
                        } else {
                            self.monthDaySelected(month, dayToSelect, false);
                        }
                    }
                    return false;
                }

                return false;
            });


            if (!!(opts.attachTo = util.$(opts.attachTo))) {
                opts.attachTo.appendChild($container);
            }

        };

        Kalendae.prototype = {
            defaults: {
                attachTo: null, /* the element to attach the root container to. can be string or DOMElement */
                months: 1, /* total number of months to display side by side */
                weekStart: 0, /* day to use for the start of the week. 0 is Sunday */
				disableDate:false, /* Future dates are not available */
                direction: 'any', /* past, today-past, any, today-future, future */
                directionScrolling: true, /* if a direction other than any is defined, prevent scrolling out of range */
                viewStartDate: null, /* date in the month to display.  When multiple months, this is the left most */
                blackout: null, /* array of dates, or function to be passed a date */
                selected: null, /* dates already selected.  can be string, date, or array of strings or dates. */
                mode: 'single', /* single, multiple, range */
                dayOutOfMonthClickable: false,
                dayHeaderClickable: false,
                format: null, /* string used for parsing dates. */
                subscribe: null, /* object containing events to subscribe to */

                columnHeaderFormat: 'dd', /* number of characters to show in the column headers */
                titleFormat: 'MMMM, YYYY', /* format mask for month titles. See momentjs.com for rules */
                dayNumberFormat: 'D', /* format mask for individual days */
                dayAttributeFormat: 'YYYY-MM-DD', /* format mask for the data-date attribute set on every span */
                parseSplitDelimiter: /,\s*|\s+-\s+/, /* regex to use for splitting multiple dates from a passed string */
                rangeDelimiter: ' - ', /* string to use between dates when outputting in range mode */
                multipleDelimiter: ', ', /* string to use between dates when outputting in multiple mode */
                useYearNav: true,
                dateClassMap: {}
            },
            classes: {
                container: 'kalendae',
                calendar: 'k-calendar',
                monthFirst: 'k-first-month',
                monthMiddle: 'k-middle-month',
                monthLast: 'k-last-month',
                title: 'k-title',
                previousMonth: 'k-btn-previous-month',
                nextMonth: 'k-btn-next-month',
                previousYear: 'k-btn-previous-year',
                nextYear: 'k-btn-next-year',
                caption: 'k-caption',
                header: 'k-header',
                days: 'k-days',
                week: 'k-week',
                dayOutOfMonth: 'k-out-of-month',
                dayInMonth: 'k-in-month',
                dayActive: 'k-active',
                daySelected: 'k-selected',
                dayInRange: 'k-range',
                dayInRangeStart: 'k-range-start',
                dayInRangeEnd: 'k-range-end',
                dayToday: 'k-today',
                monthSeparator: 'k-separator',
                disablePreviousMonth: 'k-disable-previous-month-btn',
                disableNextMonth: 'k-disable-next-month-btn',
                disablePreviousYear: 'k-disable-previous-year-btn',
                disableNextYear: 'k-disable-next-year-btn',
                disableYearNav: 'k-disable-year-nav'
            },
            disablePreviousMonth: false,
            disableNextMonth: false,
            disablePreviousYear: false,
            disableNextYear: false,
            directions: {
                'past': function (date) {
                    return moment(date).startOf('day').yearDay() >= getTodayYearDate();
                },
                'today-past': function (date) {
                    return moment(date).startOf('day').yearDay() > getTodayYearDate();
                },
                'any': function (date) {
                    return false;
                },
                'today-future': function (date) {
                    return moment(date).startOf('day').yearDay() < getTodayYearDate();
                },
                'future': function (date) {
                    return moment(date).startOf('day').yearDay() <= getTodayYearDate();
                }
            },
            getSelectedAsDates: function () {
                var out = [];
                var i = 0, c = this._sel.length;
                for (; i < c; i++) {
                    out.push(this._sel[i].toDate());
                }
                return out;
            },
            getSelectedAsText: function (format) {
                var out = [];
                var i = 0, c = this._sel.length;
                for (; i < c; i++) {
                    out.push(this._sel[i].format(format || this.settings.format || 'YYYY-MM-DD'));
                }
                return out;
            },
            getSelectedRaw: function () {
                var out = [];
                var i = 0, c = this._sel.length;
                for (; i < c; i++) {
                    out.push(moment(this._sel[i]));
                }
                return out;
            },
            getSelected: function (format) {
                var sel = this.getSelectedAsText(format);
                switch (this.settings.mode) {
                    case 'week':
                        /* falls through range */

                    case 'range':
                        sel.splice(2); //shouldn't be more than two, but lets just make sure.
                        return sel.join(this.settings.rangeDelimiter);

                    case 'multiple':
                        return sel.join(this.settings.multipleDelimiter);

                    case 'single':
                        /* falls through */
                    default:
                        return (sel[0] || null);
                }
            },
            isSelected: function (input) {
                input = moment(input).startOf('day').yearDay();
                if (input < 1 || !this._sel || this._sel.length < 1)
                    return false;

                switch (this.settings.mode) {
                    case 'week':
                        /* falls through range */
                    case 'range':
                        var a = this._sel[0] ? this._sel[0].startOf('day').yearDay() : 0,
                                b = this._sel[1] ? this._sel[1].startOf('day').yearDay() : 0;

                        if (a === input || b === input)
                            return 1;
                        if (!a || !b)
                            return 0;

                        if ((input > a && input < b) || (a < b && input < a && input > b))
                            return -1;
                        return false;

                    case 'multiple':
                        var i = this._sel.length;
                        while (i--) {
                            if (this._sel[i].startOf('day').yearDay() === input) {
                                return true;
                            }
                        }
                        return false;


                    case 'single':
                        /* falls through */
                    default:
                        return (this._sel[0] && (this._sel[0].startOf('day').yearDay() === input));
                }

                return false;
            },
            setSelected: function (input, draw) {
                var i,
                        new_dates = parseDates(input, this.settings.parseSplitDelimiter, this.settings.format),
                        old_dates = parseDates(this.getSelected(), this.settings.parseSplitDelimiter, this.settings.format);

                i = old_dates.length;
                while (i--) {
                    this.removeSelected(old_dates[i], false);
                }

                i = new_dates.length;
                while (i--) {
                    this.addSelected(new_dates[i], false);
                }

                if (draw !== false) {
                    if (new_dates[0]) {
                        this.viewStartDate = moment(new_dates[0], this.settings.format);
                    }
                    this.draw();
                }
            },
            addSelected: function (date, draw) {
                date = moment(date, this.settings.format).hours(12);

                if (this.settings.dayOutOfMonthClickable && this.settings.mode !== 'range') {
                    this.makeSelectedDateVisible(date);
                }

                switch (this.settings.mode) {
                    case 'multiple':
                        if (!this.isSelected(date))
                            this._sel.push(date);
                        else
                            return false;
                        break;
                    case 'range':

                        if (this._sel.length !== 1)
                            this._sel = [date];
                        else {
                            if (date.startOf('day').yearDay() > this._sel[0].startOf('day').yearDay())
                                this._sel[1] = date;
                            else
                                this._sel = [date, this._sel[0]];
                        }
                        break;
                    case 'single':
                        /* falls through */
                    default:
                        this._sel = [date];
                        break;
                }
                this._sel.sort(function (a, b) {
                    return a.startOf('day').yearDay() - b.startOf('day').yearDay();
                });
                this.publish('change', this, [date]);
                if (draw !== false)
                    this.draw();
                return true;
            },
            weekSelected: function (mom) {
                var x = mom.toDate();
                var start = moment(x).startOf('week');
                var end = moment(x).endOf('week').subtract(1, 'day');
                this._sel = [start, end];
                this.publish('change', this, [mom.day()]);
                this.draw();
            },
            monthDaySelected: function (month, daynumber, unselected) {
                var days = moment(month).startOf('month').weekday(daynumber),
                        endMonth = moment(month).endOf('month');
                selected = [];

                while (days <= endMonth) {
                    if (days >= moment(month).startOf('month') && !this.direction(days)) {
                        if (unselected) {
                            this.removeSelected(moment(days).hours(12));
                        } else {
                            this.addSelected(moment(days).hours(12));
                        }
                    }
                    days.add(7, 'd');
                }
            },
            makeSelectedDateVisible: function (date) {
                outOfViewMonth = moment(date).date('1').diff(this.viewStartDate, 'months');

                if (outOfViewMonth < 0) {
                    this.viewStartDate.subtract(1, 'months');
                }
                else if (outOfViewMonth > 0 && outOfViewMonth >= this.settings.months) {
                    this.viewStartDate.add(1, 'months');
                }
            },
            removeSelected: function (date, draw) {
                date = moment(date, this.settings.format).hours(12);
                var i = this._sel.length;
                while (i--) {
                    if (this._sel[i].startOf('day').yearDay() === date.startOf('day').yearDay()) {
                        this._sel.splice(i, 1);
                        this.publish('change', this, [date]);
                        if (draw !== false)
                            this.draw();
                        return true;
                    }
                }
                return false;
            },
            draw: function draw() {
                // return;
                var month = moment(this.viewStartDate).startOf('month').add(12, 'hours'), //force middle of the day to avoid any weird date shifts
                        day,
                        classes = this.classes,
                        cal,
                        $span,
                        klass,
                        i = 0, c,
                        j = 0, k,
                        t = 0, z,
                        w,
                        s,
                        headers,
                        dateString,
                        opts = this.settings,
                        diff;

                c = this.calendars.length;

                do {
                    day = moment(month).date(1);
                    day.day(day.day() < this.settings.weekStart ? this.settings.weekStart - 7 : this.settings.weekStart);
                    //if the first day of the month is less than our week start, back up a week

                    cal = this.calendars[i];

                    cal.header.parentNode.setAttribute('data-datestart', month.format(this.settings.dayAttributeFormat));

                    cal.caption.innerHTML = month.format(this.settings.titleFormat);
                    j = 0;
                    w = 0;
                    t = 0;
                    headers = [];
                    for (var z = 0; z < 7; z++) {
                        util.removeClassName(cal.header.children[z], classes.daySelected);
                        headers[z] = 0;
                    }

                    do {
                        if (opts.mode == 'week') {
                            if (((j % 7) === 0) && (j !== 0)) {
                                w++;
                            }
                            $span = cal.days[w].childNodes[j % 7];
                        } else {
                            $span = cal.days[j];
                        }

                        klass = [];

                        s = this.isSelected(day);

                        if (s)
                            klass.push(({'-1': classes.dayInRange, '1': classes.daySelected, 'true': classes.daySelected})[s]);

                        if (opts.mode === 'range') {
                            if (this._sel[0] && this._sel[0].startOf('day').yearDay() === day.clone().startOf('day').yearDay()) {
                                klass.push(classes.dayInRangeStart);
                            }
                            if (this._sel[1] && this._sel[1].startOf('day').yearDay() === day.clone().startOf('day').yearDay()) {
                                klass.push(classes.dayInRangeEnd);
                            }
                        }

                        if (opts.dayHeaderClickable && opts.mode === 'multiple') {
                            klass.push('k-day-week-' + day.weekday());
                            if ((s == true || s == 1) && !this.direction(day) && month.format('M') == day.format('M')) {
                                headers[day.weekday()] = headers[day.weekday()] + 1;
                            }
                        }

                        if (day.month() != month.month())
                            klass.push(classes.dayOutOfMonth);
                        else
                            klass.push(classes.dayInMonth);

                        if (!(this.blackout(day) || this.direction(day) || (day.month() != month.month() && opts.dayOutOfMonthClickable === false)) || s > 0)
                            klass.push(classes.dayActive);

                        if (day.clone().startOf('day').yearDay() === getTodayYearDate())
                            klass.push(classes.dayToday);

                        dateString = day.format(this.settings.dayAttributeFormat);
                        if (opts.dateClassMap[dateString])
                            klass.push(opts.dateClassMap[dateString]);

                        $span.innerHTML = day.format(opts.dayNumberFormat);
                        $span.className = klass.join(' ');
                        $span.setAttribute('data-date', dateString);


                        day.add(1, 'days');
                    } while (++j < 42);
                    z = 0;
                    if (headers.length > 0) {
                        do {
                            if (headers[z] > 0) {
                                var firstDay = Kalendae.moment(month).startOf('month').weekday(z),
                                        startMonth = Kalendae.moment(month).startOf('month');
                                endMonth = Kalendae.moment(month).endOf('month');
                                t = 0;
                                do {
                                    if (firstDay >= startMonth && !this.direction(firstDay))
                                        t++;
                                    firstDay.add(7, 'd');
                                } while (firstDay <= endMonth)

                                if (t == headers[z])
                                    util.addClassName(cal.header.children[z], classes.daySelected);
                                else
                                    util.removeClassName(cal.header.children[z], classes.daySelected);
                            }
                        } while (++z < headers.length)
                    }

                    month.add(1, 'months');
                } while (++i < c);

                if (opts.directionScrolling) {
                    var diffComparison = moment().startOf('day').hours(12);
                    diff = month.diff(diffComparison, 'months', true);

                    if (opts.direction === 'today-past' || opts.direction === 'past') {
                        if (diff <= 0) {
                            this.disableNextMonth = false;
                            util.removeClassName(this.container, classes.disableNextMonth);
                        } else {
                            this.disableNextMonth = true;
                            util.addClassName(this.container, classes.disableNextMonth);
                        }
                    } else if (opts.direction === 'today-future' || opts.direction === 'future') {
                        if (diff > opts.months) {
                            this.disablePreviousMonth = false;
                            util.removeClassName(this.container, classes.disablePreviousMonth);
                        } else {
                            this.disablePreviousMonth = true;
                            util.addClassName(this.container, classes.disablePreviousMonth);
                        }
                    }

                    if (opts.direction === 'today-past' || opts.direction === 'past') {
                        if (diff <= -11) {
                            this.disableNextYear = false;
                            util.removeClassName(this.container, classes.disableNextYear);
                        } else {
                            this.disableNextYear = true;
                            util.addClassName(this.container, classes.disableNextYear);
                        }
                    } else if (opts.direction === 'today-future' || opts.direction === 'future') {
                        if (diff > (11 + opts.months)) {
                            this.disablePreviousYear = false;
                            util.removeClassName(this.container, classes.disablePreviousYear);
                        } else {
                            this.disablePreviousYear = true;
                            util.addClassName(this.container, classes.disablePreviousYear);
                        }
                    }
                }

				if(opts.disableDate){//新增将来日期不可选择选项
					var _cdt = new Date();
					var y =  _cdt.getFullYear(), m =  (_cdt.getMonth())+1, d =  _cdt.getDate() ;

					$(this.container).find('.k-week').each(function(index,elem){
						var _dom = $(elem).find('span'),
							flag  = false ;
						_dom.each(function(i,x){
							var _dArr = $(x).attr('data-date').split('-');
							var _y = Number(_dArr[0]), _m = Number(_dArr[1]), _d = Number(_dArr[2]);
							if(!(_y > y  || (_y == y && _m > m)  || (_y == y && _m == m && _d > d)) ){
								flag = true ;
								return false;
							}
						});

						$(elem).css("pointer-events", "auto");
						!flag && $(elem).css("pointer-events", "none");
					});
				}	
            }
        };

        var parseDates = function (input, delimiter, format) {
            var output = [];

            if (typeof input === 'string') {
                input = input.split(delimiter);
            } else if (!util.isArray(input)) {
                input = [input];
            }

            var c = input.length,
                    i = 0,
                    m;

            do {
                if (input[i]) {
                    m = moment(input[i], format).hours(12);
                    if (m.isValid())
                        output.push(m);
                }
            } while (++i < c);

            return output;
        };



        window.Kalendae = Kalendae;

        var util = Kalendae.util = {
            isIE8: function () {
                return !!((/msie 8./i).test(navigator.appVersion) && !(/opera/i).test(navigator.userAgent) && window.ActiveXObject && XDomainRequest && !window.msPerformance);
            },
// ELEMENT FUNCTIONS

            $: function (elem) {
                return (typeof elem == 'string') ? document.getElementById(elem) : elem;
            },
            $$: function (selector) {
                return document.querySelectorAll(selector);
            },
            make: function (tagName, attributes, attach) {
                var k, e = document.createElement(tagName);
                if (!!attributes)
                    for (k in attributes)
                        if (attributes.hasOwnProperty(k))
                            e.setAttribute(k, attributes[k]);
                if (!!attach)
                    attach.appendChild(e);
                return e;
            },
            // Returns true if the DOM element is visible, false if it's hidden.
            // Checks if display is anything other than none.
            isVisible: function (elem) {
                // shamelessly copied from jQuery
                return elem.offsetWidth > 0 || elem.offsetHeight > 0;
            },
            getStyle: function (elem, styleProp) {
                var y, s;
                if (elem.currentStyle) {
                    y = elem.currentStyle[styleProp];
                } else if (window.getComputedStyle) {
                    s = window.getComputedStyle(elem, null);
                    y = s ? s[styleProp] : '';
                }
                return y;
            },
            domReady: function (f) {
                var state = document.readyState;
                if (state === 'complete' || state === 'interactive') {
                    f();
                } else {
                    setTimeout(function () {
                        util.domReady(f);
                    }, 9);
                }
            },
            // Adds a listener callback to a DOM element which is fired on a specified
            // event.  Callback is sent the event object and the element that triggered the event
            addEvent: function (elem, eventName, callback) {
                var listener = function (event) {
                    event = event || window.event;
                    var target = event.target || event.srcElement;
                    var block = callback.apply(elem, [event, target]);
                    if (block === false) {
                        if (!!event.preventDefault)
                            event.preventDefault();
                        else {
                            event.returnValue = false;
                            event.cancelBubble = true;
                        }
                    }
                    return block;
                };
                if (elem.attachEvent) { // IE only.  The "on" is mandatory.
                    elem.attachEvent("on" + eventName, listener);
                } else { // Other browsers.
                    elem.addEventListener(eventName, listener, false);
                }
                return listener;
            },
            // Removes a listener callback from a DOM element which is fired on a specified
            // event.
            removeEvent: function (elem, event, listener) {
                if (elem.detachEvent) {	// IE only.  The "on" is mandatory.
                    elem.detachEvent("on" + event, listener);
                } else { // Other browsers.
                    elem.removeEventListener(event, listener, false);
                }
            },
            fireEvent: function (elem, event) {
                if (document.createEvent) {
                    var e = document.createEvent('HTMLEvents');
                    e.initEvent(event, true, true);
                    elem.dispatchEvent(e);
                } else if (document.createEventObject) {
                    elem.fireEvent('on' + event);
                } else if (typeof elem['on' + event] == 'function') {
                    elem['on' + event]();
                }
            },
            hasClassName: function (elem, className) { //copied and modified from Prototype.js
                if (!(elem = util.$(elem)))
                    return false;
                var eClassName = elem.className;
                return (eClassName.length > 0 && (eClassName == className || new RegExp("(^|\\s)" + className + "(\\s|$)").test(eClassName)));
            },
            addClassName: function (elem, className) { //copied and modified from Prototype.js
                if (!(elem = util.$(elem)))
                    return;
                if (!util.hasClassName(elem, className))
                    elem.className += (elem.className ? ' ' : '') + className;
            },
            removeClassName: function (elem, className) { //copied and modified from Prototype.js
                if (!(elem = util.$(elem)))
                    return;
                elem.className = util.trimString(elem.className.replace(new RegExp("(^|\\s+)" + className + "(\\s+|$)"), ' '));
            },
            isFixed: function (elem) {
                do {
                    if (util.getStyle(elem, 'position') === 'fixed')
                        return true;
                } while ((elem = elem.offsetParent));
                return false;
            },
            scrollContainer: function (elem) {
                do {
                    var overflow = util.getStyle(elem, 'overflow');
                    if (overflow === 'auto' || overflow === 'scroll')
                        return elem;
                } while ((elem = elem.parentNode) && elem != window.document.body);
                return null;
            },
            getPosition: function (elem, isInner) {
                var x = elem.offsetLeft,
                        y = elem.offsetTop,
                        r = {};

                if (!isInner) {
                    while ((elem = elem.offsetParent)) {
                        x += elem.offsetLeft;
                        y += elem.offsetTop;
                    }
                }

                r[0] = r.left = x;
                r[1] = r.top = y;
                return r;
            },
            getHeight: function (elem) {
                return elem.offsetHeight || elem.scrollHeight;
            },
            getWidth: function (elem) {
                return elem.offsetWidth || elem.scrollWidth;
            },
// TEXT FUNCTIONS

            trimString: function (input) {
                return input.replace(/^\s+/, '').replace(/\s+$/, '');
            },
// OBJECT FUNCTIONS

            merge: function () {
                /* Combines multiple objects into one.
                 * Syntax: util.extend([true], object1, object2, ... objectN)
                 * If first argument is true, function will merge recursively.
                 */

                var deep = (arguments[0] === true),
                        d = {},
                        i = deep ? 1 : 0;

                var _c = function (a, b) {
                    if (typeof b !== 'object')
                        return;
                    for (var k in b)
                        if (b.hasOwnProperty(k)) {
                            //if property is an object or array, merge the contents instead of overwriting, if extend() was called as such
                            if (deep && typeof a[k] === 'object' && typeof b[k] === 'object')
                                _update(a[k], b[k]);
                            else
                                a[k] = b[k];
                        }
                    return a;
                };

                for (; i < arguments.length; i++) {
                    _c(d, arguments[i]);
                }
                return d;
            },
            isArray: function (array) {
                return Object.prototype.toString.call(array) == "[object Array]";
            }
        };


//auto-initializaiton code
        if (typeof document.addEventListener === 'function')
            Kalendae.util.domReady(function () {
                var els = util.$$('.auto-kal'),
                        i = els.length,
                        e,
                        options,
                        optionsRaw;

                while (i--) {
                    e = els[i];
                    optionsRaw = e.getAttribute('data-kal');
                    options = (optionsRaw == null || optionsRaw == "") ? {} : (new Function('return {' + optionsRaw + '};'))();

                    if (e.tagName === 'INPUT') {
                        //if element is an input, bind a popup calendar to the input.
                        new Kalendae.Input(e, options);
                    } else {
                        //otherwise, insert a flat calendar into the element.
                        new Kalendae(util.merge(options, {attachTo: e}));
                    }

                }
            });
        Kalendae.Input = function (targetElement, options) {
            if (typeof document.addEventListener !== 'function' && !util.isIE8())
                return;

            var $input = this.input = util.$(targetElement),
                    overwriteInput,
                    $closeButton,
                    changing = false;

            if (!$input || $input.tagName !== 'INPUT')
                throw "First argument for Kalendae.Input must be an <input> element or a valid element id.";

            var self = this,
                    classes = self.classes,
                    opts = self.settings = util.merge(self.defaults, options);

            this._events = {};

            //force attachment to the body
            opts.attachTo = window.document.body;

            //if no override provided, use the input's contents
            if (!opts.selected)
                opts.selected = $input.value;
            else
                overwriteInput = true;

            //call our parent constructor
            Kalendae.call(self, opts);

            //create the close button
            if (opts.closeButton) {
                $closeButton = util.make('a', {'class': classes.closeButton}, self.container);
                util.addEvent($closeButton, 'click', function () {
                    $input.blur();
                });
            }

            if (overwriteInput)
                $input.value = self.getSelected();

            var $container = self.container,
                    noclose = false;

            $container.style.display = 'none';
            util.addClassName($container, classes.positioned);

            this._events.containerMouseDown = util.addEvent($container, 'mousedown', function (event, target) {
                noclose = true; //IE8 doesn't obey event blocking when it comes to focusing, so we have to do this shit.
            });

            this._events.documentMousedown = util.addEvent(window.document, 'mousedown', function (event, target) {
                noclose = false;
            });

            this._events.inputFocus = util.addEvent($input, 'focus', function () {
                changing = true; // prevent setSelected from altering the input contents.
                self.setSelected(this.value);
                changing = false;
                self.show();
            });

            this._events.inputBlur = util.addEvent($input, 'blur', function () {
                if (noclose && util.isIE8()) {
                    noclose = false;
                    $input.focus();
                }
                else
                    self.hide();
            });

            this._events.inputKeyup = util.addEvent($input, 'keyup', function (event) {
                changing = true; // prevent setSelected from altering the input contents.
                var dateValue = parseDates(this.value, self.settings.parseSplitDelimiter, self.settings.format);

                // If the date in the field is parsable as a valid date, update.  Otherwise deselect and show default view.
                if (dateValue && dateValue.length && dateValue[0] && dateValue[0].year() > 1000) {
                    self.setSelected(this.value);
                } else {
                    self.setSelected('', null);
                    self.viewStartDate = moment(self.defaultView);
                    self.draw();
                }
                changing = false;
            });

            var $scrollContainer = util.scrollContainer($input);

            if ($scrollContainer) {

                // Hide calendar when $scrollContainer is scrolled
                util.addEvent($scrollContainer, 'scroll', function (event) {
                    $input.blur();
                });
            }

            self.subscribe('change', function () {
                if (changing) {
                    // the change event came from an internal modification, don't update the field contents
                    return;
                }
                $input.value = self.getSelected();
                util.fireEvent($input, 'change');
            });

        };

        Kalendae.Input.prototype = util.merge(Kalendae.prototype, {
            defaults: util.merge(Kalendae.prototype.defaults, {
                format: 'MM/DD/YYYY',
                side: 'bottom',
                closeButton: true,
                offsetLeft: 0,
                offsetTop: 0
            }),
            classes: util.merge(Kalendae.prototype.classes, {
                positioned: 'k-floating',
                closeButton: 'k-btn-close'
            }),
            show: function () {
                var $container = this.container,
                        style = $container.style,
                        $input = this.input,
                        pos = util.getPosition($input),
                        $scrollContainer = util.scrollContainer($input),
                        scrollTop = $scrollContainer ? $scrollContainer.scrollTop : 0,
                        scrollLeft = $scrollContainer ? $scrollContainer.scrollLeft : 0,
                        opts = this.settings;

                style.display = '';
                switch (opts.side) {
                    case 'left':
                        style.left = (pos.left - util.getWidth($container) + opts.offsetLeft - scrollLeft) + 'px';
                        style.top = (pos.top + opts.offsetTop - scrollTop) + 'px';
                        break;
                    case 'right':
                        style.left = (pos.left + util.getWidth($input) - scrollLeft) + 'px';
                        style.top = (pos.top + opts.offsetTop - scrollTop) + 'px';
                        break;
                    case 'top':
                        style.left = (pos.left + opts.offsetLeft - scrollLeft) + 'px';
                        style.top = (pos.top - util.getHeight($container) + opts.offsetTop - scrollTop) + 'px';
                        break;
                    case 'bottom right':
                        style.left = (pos.left - util.getWidth($container) + util.getWidth($input) + opts.offsetLeft) + 'px';
                        style.top = (pos.top + util.getHeight($input) + opts.offsetTop - scrollTop) + 'px';
                        break;
                    case 'middle'://add by haorooms
                        style.left = 0+ 'px';
                        style.top = 0 + 'px';
                        break;
                    case 'bottom':
                        /* falls through */
                    default:
                        style.left = (pos.left + opts.offsetLeft - scrollLeft) + 'px';
                        style.top = (pos.top + util.getHeight($input) + opts.offsetTop - scrollTop) + 'px';
                        break;
                }

                style.position = util.isFixed($input) ? 'fixed' : 'absolute';

                this.publish('show', this);
            },
            hide: function () {
                this.container.style.display = 'none';
                this.publish('hide', this);
            },
            destroy: function () {
                var $container = this.container;
                var $input = this.input;

                util.removeEvent($container, 'mousedown', this._events.containerMousedown);

                util.removeEvent(window.document, 'mousedown', this._events.documentMousedown);

                util.removeEvent($input, 'focus', this._events.inputFocus);

                util.removeEvent($input, 'blur', this._events.inputBlur);

                util.removeEvent($input, 'keyup', this._events.inputKeyup);

                if ($container.parentNode) {
                    $container.parentNode.removeChild($container);
                }
            }
        });


        /*!
         * MinPubSub, modified for use on Kalendae
         * Copyright(c) 2011 Daniel Lamb <daniellmb.com>
         * https://github.com/daniellmb/MinPubSub
         * MIT Licensed
         */

        var MinPubSub = function (d) {

            if (!d)
                d = this;

            // the topic/subscription hash
            var cache = d.c_ || {}; //check for "c_" cache for unit testing

            d.publish = function (/* String */ topic, /* Object */ target, /* Array? */ args) {
                // summary:
                //		Publish some data on a named topic.
                // topic: String
                //		The channel to publish on
                // args: Array?
                //		The data to publish. Each array item is converted into an ordered
                //		arguments on the subscribed functions.
                //
                // example:
                //		Publish stuff on '/some/topic'. Anything subscribed will be called
                //		with a function signature like: function(a,b,c){ ... }
                //
                //		publish("/some/topic", ["a","b","c"]);

                var subs = cache[topic],
                        len = subs ? subs.length : 0,
                        r;

                //can change loop or reverse array if the order matters
                while (len--) {
                    r = subs[len].apply(target, args || []);
                    if (typeof r === 'boolean')
                        return r;
                }
            };

            d.subscribe = function (/* String */ topic, /* Function */ callback, /* Boolean */ topPriority) {
                // summary:
                //		Register a callback on a named topic.
                // topic: String
                //		The channel to subscribe to
                // callback: Function
                //		The handler event. Anytime something is publish'ed on a
                //		subscribed channel, the callback will be called with the
                //		published array as ordered arguments.
                //
                // returns: Array
                //		A handle which can be used to unsubscribe this particular subscription.
                //
                // example:
                //		subscribe("/some/topic", function(a, b, c){ /* handle data */ });

                if (!cache[topic]) {
                    cache[topic] = [];
                }
                if (topPriority)
                    cache[topic].push(callback);
                else
                    cache[topic].unshift(callback);
                return [topic, callback]; // Array
            };

            d.unsubscribe = function (/* Array */ handle) {
                // summary:
                //		Disconnect a subscribed function for a topic.
                // handle: Array
                //		The return value from a subscribe call.
                // example:
                //		var handle = subscribe("/some/topic", function(){});
                //		unsubscribe(handle);

                var subs = cache[handle[0]],
                        callback = handle[1],
                        len = subs ? subs.length : 0;

                while (len--) {
                    if (subs[len] === callback) {
                        subs.splice(len, 1);
                    }
                }
            };

        };//! moment.js
//! version : 2.9.0
//! authors : Tim Wood, Iskren Chernev, Moment.js contributors
//! license : MIT
//! momentjs.com

        (function (undefined) {
            /************************************
             Constants
             ************************************/

            var moment,
                    VERSION = '2.9.0',
                    // the global-scope this is NOT the global object in Node.js
                    globalScope = (typeof global !== 'undefined' && (typeof window === 'undefined' || window === global.window)) ? global : this,
                    oldGlobalMoment,
                    round = Math.round,
                    hasOwnProperty = Object.prototype.hasOwnProperty,
                    i,
                    YEAR = 0,
                    MONTH = 1,
                    DATE = 2,
                    HOUR = 3,
                    MINUTE = 4,
                    SECOND = 5,
                    MILLISECOND = 6,
                    // internal storage for locale config files
                    locales = {},
                    // extra moment internal properties (plugins register props here)
                    momentProperties = [],
                    // check for nodeJS
                    hasModule = (typeof module !== 'undefined' && module && module.exports),
                    // ASP.NET json date format regex
                    aspNetJsonRegex = /^\/?Date\((\-?\d+)/i,
                    aspNetTimeSpanJsonRegex = /(\-)?(?:(\d*)\.)?(\d+)\:(\d+)(?:\:(\d+)\.?(\d{3})?)?/,
                    // from http://docs.closure-library.googlecode.com/git/closure_goog_date_date.js.source.html
                    // somewhat more in line with 4.4.3.2 2004 spec, but allows decimal anywhere
                    isoDurationRegex = /^(-)?P(?:(?:([0-9,.]*)Y)?(?:([0-9,.]*)M)?(?:([0-9,.]*)D)?(?:T(?:([0-9,.]*)H)?(?:([0-9,.]*)M)?(?:([0-9,.]*)S)?)?|([0-9,.]*)W)$/,
                    // format tokens
                    formattingTokens = /(\[[^\[]*\])|(\\)?(Mo|MM?M?M?|Do|DDDo|DD?D?D?|ddd?d?|do?|w[o|w]?|W[o|W]?|Q|YYYYYY|YYYYY|YYYY|YY|gg(ggg?)?|GG(GGG?)?|e|E|a|A|hh?|HH?|mm?|ss?|S{1,4}|x|X|zz?|ZZ?|.)/g,
                    localFormattingTokens = /(\[[^\[]*\])|(\\)?(LTS|LT|LL?L?L?|l{1,4})/g,
                    // parsing token regexes
                    parseTokenOneOrTwoDigits = /\d\d?/, // 0 - 99
                    parseTokenOneToThreeDigits = /\d{1,3}/, // 0 - 999
                    parseTokenOneToFourDigits = /\d{1,4}/, // 0 - 9999
                    parseTokenOneToSixDigits = /[+\-]?\d{1,6}/, // -999,999 - 999,999
                    parseTokenDigits = /\d+/, // nonzero number of digits
                    parseTokenWord = /[0-9]*['a-z\u00A0-\u05FF\u0700-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+|[\u0600-\u06FF\/]+(\s*?[\u0600-\u06FF]+){1,2}/i, // any word (or two) characters or numbers including two/three word month in arabic.
                    parseTokenTimezone = /Z|[\+\-]\d\d:?\d\d/gi, // +00:00 -00:00 +0000 -0000 or Z
                    parseTokenT = /T/i, // T (ISO separator)
                    parseTokenOffsetMs = /[\+\-]?\d+/, // 1234567890123
                    parseTokenTimestampMs = /[\+\-]?\d+(\.\d{1,3})?/, // 123456789 123456789.123

                    //strict parsing regexes
                    parseTokenOneDigit = /\d/, // 0 - 9
                    parseTokenTwoDigits = /\d\d/, // 00 - 99
                    parseTokenThreeDigits = /\d{3}/, // 000 - 999
                    parseTokenFourDigits = /\d{4}/, // 0000 - 9999
                    parseTokenSixDigits = /[+-]?\d{6}/, // -999,999 - 999,999
                    parseTokenSignedNumber = /[+-]?\d+/, // -inf - inf

                    // iso 8601 regex
                    // 0000-00-00 0000-W00 or 0000-W00-0 + T + 00 or 00:00 or 00:00:00 or 00:00:00.000 + +00:00 or +0000 or +00)
                    isoRegex = /^\s*(?:[+-]\d{6}|\d{4})-(?:(\d\d-\d\d)|(W\d\d$)|(W\d\d-\d)|(\d\d\d))((T| )(\d\d(:\d\d(:\d\d(\.\d+)?)?)?)?([\+\-]\d\d(?::?\d\d)?|\s*Z)?)?$/,
                    isoFormat = 'YYYY-MM-DDTHH:mm:ssZ',
                    isoDates = [
                        ['YYYYYY-MM-DD', /[+-]\d{6}-\d{2}-\d{2}/],
                        ['YYYY-MM-DD', /\d{4}-\d{2}-\d{2}/],
                        ['GGGG-[W]WW-E', /\d{4}-W\d{2}-\d/],
                        ['GGGG-[W]WW', /\d{4}-W\d{2}/],
                        ['YYYY-DDD', /\d{4}-\d{3}/]
                    ],
                    // iso time formats and regexes
                    isoTimes = [
                        ['HH:mm:ss.SSSS', /(T| )\d\d:\d\d:\d\d\.\d+/],
                        ['HH:mm:ss', /(T| )\d\d:\d\d:\d\d/],
                        ['HH:mm', /(T| )\d\d:\d\d/],
                        ['HH', /(T| )\d\d/]
                    ],
                    // timezone chunker '+10:00' > ['10', '00'] or '-1530' > ['-', '15', '30']
                    parseTimezoneChunker = /([\+\-]|\d\d)/gi,
                    // getter and setter names
                    proxyGettersAndSetters = 'Date|Hours|Minutes|Seconds|Milliseconds'.split('|'),
                    unitMillisecondFactors = {
                        'Milliseconds': 1,
                        'Seconds': 1e3,
                        'Minutes': 6e4,
                        'Hours': 36e5,
                        'Days': 864e5,
                        'Months': 2592e6,
                        'Years': 31536e6
                    },
            unitAliases = {
                ms: 'millisecond',
                s: 'second',
                m: 'minute',
                h: 'hour',
                d: 'day',
                D: 'date',
                w: 'week',
                W: 'isoWeek',
                M: 'month',
                Q: 'quarter',
                y: 'year',
                DDD: 'dayOfYear',
                e: 'weekday',
                E: 'isoWeekday',
                gg: 'weekYear',
                GG: 'isoWeekYear'
            },
            camelFunctions = {
                dayofyear: 'dayOfYear',
                isoweekday: 'isoWeekday',
                isoweek: 'isoWeek',
                weekyear: 'weekYear',
                isoweekyear: 'isoWeekYear'
            },
            // format function strings
            formatFunctions = {},
                    // default relative time thresholds
                    relativeTimeThresholds = {
                        s: 45, // seconds to minute
                        m: 45, // minutes to hour
                        h: 22, // hours to day
                        d: 26, // days to month
                        M: 11   // months to year
                    },
            // tokens to ordinalize and pad
            ordinalizeTokens = 'DDD w W M D d'.split(' '),
                    paddedTokens = 'M D H h m s w W'.split(' '),
                    formatTokenFunctions = {
                        M: function () {
                            return this.month() + 1;
                        },
                        MMM: function (format) {
                            return this.localeData().monthsShort(this, format);
                        },
                        MMMM: function (format) {
                            return this.localeData().months(this, format);
                        },
                        D: function () {
                            return this.date();
                        },
                        DDD: function () {
                            return this.dayOfYear();
                        },
                        d: function () {
                            return this.day();
                        },
                        dd: function (format) {
                            return this.localeData().weekdaysMin(this, format);
                        },
                        ddd: function (format) {
                            return this.localeData().weekdaysShort(this, format);
                        },
                        dddd: function (format) {
                            return this.localeData().weekdays(this, format);
                        },
                        w: function () {
                            return this.week();
                        },
                        W: function () {
                            return this.isoWeek();
                        },
                        YY: function () {
                            return leftZeroFill(this.year() % 100, 2);
                        },
                        YYYY: function () {
                            return leftZeroFill(this.year(), 4);
                        },
                        YYYYY: function () {
                            return leftZeroFill(this.year(), 5);
                        },
                        YYYYYY: function () {
                            var y = this.year(), sign = y >= 0 ? '+' : '-';
                            return sign + leftZeroFill(Math.abs(y), 6);
                        },
                        gg: function () {
                            return leftZeroFill(this.weekYear() % 100, 2);
                        },
                        gggg: function () {
                            return leftZeroFill(this.weekYear(), 4);
                        },
                        ggggg: function () {
                            return leftZeroFill(this.weekYear(), 5);
                        },
                        GG: function () {
                            return leftZeroFill(this.isoWeekYear() % 100, 2);
                        },
                        GGGG: function () {
                            return leftZeroFill(this.isoWeekYear(), 4);
                        },
                        GGGGG: function () {
                            return leftZeroFill(this.isoWeekYear(), 5);
                        },
                        e: function () {
                            return this.weekday();
                        },
                        E: function () {
                            return this.isoWeekday();
                        },
                        a: function () {
                            return this.localeData().meridiem(this.hours(), this.minutes(), true);
                        },
                        A: function () {
                            return this.localeData().meridiem(this.hours(), this.minutes(), false);
                        },
                        H: function () {
                            return this.hours();
                        },
                        h: function () {
                            return this.hours() % 12 || 12;
                        },
                        m: function () {
                            return this.minutes();
                        },
                        s: function () {
                            return this.seconds();
                        },
                        S: function () {
                            return toInt(this.milliseconds() / 100);
                        },
                        SS: function () {
                            return leftZeroFill(toInt(this.milliseconds() / 10), 2);
                        },
                        SSS: function () {
                            return leftZeroFill(this.milliseconds(), 3);
                        },
                        SSSS: function () {
                            return leftZeroFill(this.milliseconds(), 3);
                        },
                        Z: function () {
                            var a = this.utcOffset(),
                                    b = '+';
                            if (a < 0) {
                                a = -a;
                                b = '-';
                            }
                            return b + leftZeroFill(toInt(a / 60), 2) + ':' + leftZeroFill(toInt(a) % 60, 2);
                        },
                        ZZ: function () {
                            var a = this.utcOffset(),
                                    b = '+';
                            if (a < 0) {
                                a = -a;
                                b = '-';
                            }
                            return b + leftZeroFill(toInt(a / 60), 2) + leftZeroFill(toInt(a) % 60, 2);
                        },
                        z: function () {
                            return this.zoneAbbr();
                        },
                        zz: function () {
                            return this.zoneName();
                        },
                        x: function () {
                            return this.valueOf();
                        },
                        X: function () {
                            return this.unix();
                        },
                        Q: function () {
                            return this.quarter();
                        }
                    },
            deprecations = {},
                    lists = ['months', 'monthsShort', 'weekdays', 'weekdaysShort', 'weekdaysMin'],
                    updateInProgress = false;

            // Pick the first defined of two or three arguments. dfl comes from
            // default.
            function dfl(a, b, c) {
                switch (arguments.length) {
                    case 2:
                        return a != null ? a : b;
                    case 3:
                        return a != null ? a : b != null ? b : c;
                    default:
                        throw new Error('Implement me');
                }
            }

            function hasOwnProp(a, b) {
                return hasOwnProperty.call(a, b);
            }

            function defaultParsingFlags() {
                // We need to deep clone this object, and es5 standard is not very
                // helpful.
                return {
                    empty: false,
                    unusedTokens: [],
                    unusedInput: [],
                    overflow: -2,
                    charsLeftOver: 0,
                    nullInput: false,
                    invalidMonth: null,
                    invalidFormat: false,
                    userInvalidated: false,
                    iso: false
                };
            }

            function printMsg(msg) {
                if (moment.suppressDeprecationWarnings === false &&
                        typeof console !== 'undefined' && console.warn) {
                    console.warn('Deprecation warning: ' + msg);
                }
            }

            function deprecate(msg, fn) {
                var firstTime = true;
                return extend(function () {
                    if (firstTime) {
                        printMsg(msg);
                        firstTime = false;
                    }
                    return fn.apply(this, arguments);
                }, fn);
            }

            function deprecateSimple(name, msg) {
                if (!deprecations[name]) {
                    printMsg(msg);
                    deprecations[name] = true;
                }
            }

            function padToken(func, count) {
                return function (a) {
                    return leftZeroFill(func.call(this, a), count);
                };
            }
            function ordinalizeToken(func, period) {
                return function (a) {
                    return this.localeData().ordinal(func.call(this, a), period);
                };
            }

            function monthDiff(a, b) {
                // difference in months
                var wholeMonthDiff = ((b.year() - a.year()) * 12) + (b.month() - a.month()),
                        // b is in (anchor - 1 month, anchor + 1 month)
                        anchor = a.clone().add(wholeMonthDiff, 'months'),
                        anchor2, adjust;

                if (b - anchor < 0) {
                    anchor2 = a.clone().add(wholeMonthDiff - 1, 'months');
                    // linear across the month
                    adjust = (b - anchor) / (anchor - anchor2);
                } else {
                    anchor2 = a.clone().add(wholeMonthDiff + 1, 'months');
                    // linear across the month
                    adjust = (b - anchor) / (anchor2 - anchor);
                }

                return -(wholeMonthDiff + adjust);
            }

            while (ordinalizeTokens.length) {
                i = ordinalizeTokens.pop();
                formatTokenFunctions[i + 'o'] = ordinalizeToken(formatTokenFunctions[i], i);
            }
            while (paddedTokens.length) {
                i = paddedTokens.pop();
                formatTokenFunctions[i + i] = padToken(formatTokenFunctions[i], 2);
            }
            formatTokenFunctions.DDDD = padToken(formatTokenFunctions.DDD, 3);


            function meridiemFixWrap(locale, hour, meridiem) {
                var isPm;

                if (meridiem == null) {
                    // nothing to do
                    return hour;
                }
                if (locale.meridiemHour != null) {
                    return locale.meridiemHour(hour, meridiem);
                } else if (locale.isPM != null) {
                    // Fallback
                    isPm = locale.isPM(meridiem);
                    if (isPm && hour < 12) {
                        hour += 12;
                    }
                    if (!isPm && hour === 12) {
                        hour = 0;
                    }
                    return hour;
                } else {
                    // thie is not supposed to happen
                    return hour;
                }
            }

            /************************************
             Constructors
             ************************************/

            function Locale() {
            }

            // Moment prototype object
            function Moment(config, skipOverflow) {
                if (skipOverflow !== false) {
                    checkOverflow(config);
                }
                copyConfig(this, config);
                this._d = new Date(+config._d);
                // Prevent infinite loop in case updateOffset creates new moment
                // objects.
                if (updateInProgress === false) {
                    updateInProgress = true;
                    moment.updateOffset(this);
                    updateInProgress = false;
                }
            }

            // Duration Constructor
            function Duration(duration) {
                var normalizedInput = normalizeObjectUnits(duration),
                        years = normalizedInput.year || 0,
                        quarters = normalizedInput.quarter || 0,
                        months = normalizedInput.month || 0,
                        weeks = normalizedInput.week || 0,
                        days = normalizedInput.day || 0,
                        hours = normalizedInput.hour || 0,
                        minutes = normalizedInput.minute || 0,
                        seconds = normalizedInput.second || 0,
                        milliseconds = normalizedInput.millisecond || 0;

                // representation for dateAddRemove
                this._milliseconds = +milliseconds +
                        seconds * 1e3 + // 1000
                        minutes * 6e4 + // 1000 * 60
                        hours * 36e5; // 1000 * 60 * 60
                // Because of dateAddRemove treats 24 hours as different from a
                // day when working around DST, we need to store them separately
                this._days = +days +
                        weeks * 8;//chang by haorooms
                // It is impossible translate months into days without knowing
                // which months you are are talking about, so we have to store
                // it separately.
                this._months = +months +
                        quarters * 3 +
                        years * 12;

                this._data = {};

                this._locale = moment.localeData();

                this._bubble();
            }

            /************************************
             Helpers
             ************************************/


            function extend(a, b) {
                for (var i in b) {
                    if (hasOwnProp(b, i)) {
                        a[i] = b[i];
                    }
                }

                if (hasOwnProp(b, 'toString')) {
                    a.toString = b.toString;
                }

                if (hasOwnProp(b, 'valueOf')) {
                    a.valueOf = b.valueOf;
                }

                return a;
            }

            function copyConfig(to, from) {
                var i, prop, val;

                if (typeof from._isAMomentObject !== 'undefined') {
                    to._isAMomentObject = from._isAMomentObject;
                }
                if (typeof from._i !== 'undefined') {
                    to._i = from._i;
                }
                if (typeof from._f !== 'undefined') {
                    to._f = from._f;
                }
                if (typeof from._l !== 'undefined') {
                    to._l = from._l;
                }
                if (typeof from._strict !== 'undefined') {
                    to._strict = from._strict;
                }
                if (typeof from._tzm !== 'undefined') {
                    to._tzm = from._tzm;
                }
                if (typeof from._isUTC !== 'undefined') {
                    to._isUTC = from._isUTC;
                }
                if (typeof from._offset !== 'undefined') {
                    to._offset = from._offset;
                }
                if (typeof from._pf !== 'undefined') {
                    to._pf = from._pf;
                }
                if (typeof from._locale !== 'undefined') {
                    to._locale = from._locale;
                }

                if (momentProperties.length > 0) {
                    for (i in momentProperties) {
                        prop = momentProperties[i];
                        val = from[prop];
                        if (typeof val !== 'undefined') {
                            to[prop] = val;
                        }
                    }
                }

                return to;
            }

            function absRound(number) {
                if (number < 0) {
                    return Math.ceil(number);
                } else {
                    return Math.floor(number);
                }
            }

            // left zero fill a number
            // see http://jsperf.com/left-zero-filling for performance comparison
            function leftZeroFill(number, targetLength, forceSign) {
                var output = '' + Math.abs(number),
                        sign = number >= 0;

                while (output.length < targetLength) {
                    output = '0' + output;
                }
                return (sign ? (forceSign ? '+' : '') : '-') + output;
            }

            function positiveMomentsDifference(base, other) {
                var res = {milliseconds: 0, months: 0};

                res.months = other.month() - base.month() +
                        (other.year() - base.year()) * 12;
                if (base.clone().add(res.months, 'M').isAfter(other)) {
                    --res.months;
                }

                res.milliseconds = +other - +(base.clone().add(res.months, 'M'));

                return res;
            }

            function momentsDifference(base, other) {
                var res;
                other = makeAs(other, base);
                if (base.isBefore(other)) {
                    res = positiveMomentsDifference(base, other);
                } else {
                    res = positiveMomentsDifference(other, base);
                    res.milliseconds = -res.milliseconds;
                    res.months = -res.months;
                }

                return res;
            }

            // TODO: remove 'name' arg after deprecation is removed
            function createAdder(direction, name) {
                return function (val, period) {
                    var dur, tmp;
                    //invert the arguments, but complain about it
                    if (period !== null && !isNaN(+period)) {
                        deprecateSimple(name, 'moment().' + name + '(period, number) is deprecated. Please use moment().' + name + '(number, period).');
                        tmp = val;
                        val = period;
                        period = tmp;
                    }

                    val = typeof val === 'string' ? +val : val;
                    dur = moment.duration(val, period);
                    addOrSubtractDurationFromMoment(this, dur, direction);
                    return this;
                };
            }

            function addOrSubtractDurationFromMoment(mom, duration, isAdding, updateOffset) {
                var milliseconds = duration._milliseconds,
                        days = duration._days,
                        months = duration._months;
                updateOffset = updateOffset == null ? true : updateOffset;

                if (milliseconds) {
                    mom._d.setTime(+mom._d + milliseconds * isAdding);
                }
                if (days) {
                    rawSetter(mom, 'Date', rawGetter(mom, 'Date') + days * isAdding);
                }
                if (months) {
                    rawMonthSetter(mom, rawGetter(mom, 'Month') + months * isAdding);
                }
                if (updateOffset) {
                    moment.updateOffset(mom, days || months);
                }
            }

            // check if is an array
            function isArray(input) {
                return Object.prototype.toString.call(input) === '[object Array]';
            }

            function isDate(input) {
                return Object.prototype.toString.call(input) === '[object Date]' ||
                        input instanceof Date;
            }

            // compare two arrays, return the number of differences
            function compareArrays(array1, array2, dontConvert) {
                var len = Math.min(array1.length, array2.length),
                        lengthDiff = Math.abs(array1.length - array2.length),
                        diffs = 0,
                        i;
                for (i = 0; i < len; i++) {
                    if ((dontConvert && array1[i] !== array2[i]) ||
                            (!dontConvert && toInt(array1[i]) !== toInt(array2[i]))) {
                        diffs++;
                    }
                }
                return diffs + lengthDiff;
            }

            function normalizeUnits(units) {
                if (units) {
                    var lowered = units.toLowerCase().replace(/(.)s$/, '$1');
                    units = unitAliases[units] || camelFunctions[lowered] || lowered;
                }
                return units;
            }

            function normalizeObjectUnits(inputObject) {
                var normalizedInput = {},
                        normalizedProp,
                        prop;

                for (prop in inputObject) {
                    if (hasOwnProp(inputObject, prop)) {
                        normalizedProp = normalizeUnits(prop);
                        if (normalizedProp) {
                            normalizedInput[normalizedProp] = inputObject[prop];
                        }
                    }
                }

                return normalizedInput;
            }

            function makeList(field) {
                var count, setter;

                if (field.indexOf('week') === 0) {
                    count = 7;
                    setter = 'day';
                }
                else if (field.indexOf('month') === 0) {
                    count = 12;
                    setter = 'month';
                }
                else {
                    return;
                }

                moment[field] = function (format, index) {
                    var i, getter,
                            method = moment._locale[field],
                            results = [];

                    if (typeof format === 'number') {
                        index = format;
                        format = undefined;
                    }

                    getter = function (i) {
                        var m = moment().utc().set(setter, i);
                        return method.call(moment._locale, m, format || '');
                    };

                    if (index != null) {
                        return getter(index);
                    }
                    else {
                        for (i = 0; i < count; i++) {
                            results.push(getter(i));
                        }
                        return results;
                    }
                };
            }

            function toInt(argumentForCoercion) {
                var coercedNumber = +argumentForCoercion,
                        value = 0;

                if (coercedNumber !== 0 && isFinite(coercedNumber)) {
                    if (coercedNumber >= 0) {
                        value = Math.floor(coercedNumber);
                    } else {
                        value = Math.ceil(coercedNumber);
                    }
                }

                return value;
            }

            function daysInMonth(year, month) {
                return new Date(Date.UTC(year, month + 1, 0)).getUTCDate();
            }

            function weeksInYear(year, dow, doy) {
                return weekOfYear(moment([year, 11, 31 + dow - doy]), dow, doy).week;
            }

            function daysInYear(year) {
                return isLeapYear(year) ? 366 : 365;
            }

            function isLeapYear(year) {
                return (year % 4 === 0 && year % 100 !== 0) || year % 400 === 0;
            }

            function checkOverflow(m) {
                var overflow;
                if (m._a && m._pf.overflow === -2) {
                    overflow =
                            m._a[MONTH] < 0 || m._a[MONTH] > 11 ? MONTH :
                            m._a[DATE] < 1 || m._a[DATE] > daysInMonth(m._a[YEAR], m._a[MONTH]) ? DATE :
                            m._a[HOUR] < 0 || m._a[HOUR] > 24 ||
                            (m._a[HOUR] === 24 && (m._a[MINUTE] !== 0 ||
                                    m._a[SECOND] !== 0 ||
                                    m._a[MILLISECOND] !== 0)) ? HOUR :
                            m._a[MINUTE] < 0 || m._a[MINUTE] > 59 ? MINUTE :
                            m._a[SECOND] < 0 || m._a[SECOND] > 59 ? SECOND :
                            m._a[MILLISECOND] < 0 || m._a[MILLISECOND] > 999 ? MILLISECOND :
                            -1;

                    if (m._pf._overflowDayOfYear && (overflow < YEAR || overflow > DATE)) {
                        overflow = DATE;
                    }

                    m._pf.overflow = overflow;
                }
            }

            function isValid(m) {
                if (m._isValid == null) {
                    m._isValid = !isNaN(m._d.getTime()) &&
                            m._pf.overflow < 0 &&
                            !m._pf.empty &&
                            !m._pf.invalidMonth &&
                            !m._pf.nullInput &&
                            !m._pf.invalidFormat &&
                            !m._pf.userInvalidated;

                    if (m._strict) {
                        m._isValid = m._isValid &&
                                m._pf.charsLeftOver === 0 &&
                                m._pf.unusedTokens.length === 0 &&
                                m._pf.bigHour === undefined;
                    }
                }
                return m._isValid;
            }

            function normalizeLocale(key) {
                return key ? key.toLowerCase().replace('_', '-') : key;
            }

            // pick the locale from the array
            // try ['en-au', 'en-gb'] as 'en-au', 'en-gb', 'en', as in move through the list trying each
            // substring from most specific to least, but move to the next array item if it's a more specific variant than the current root
            function chooseLocale(names) {
                var i = 0, j, next, locale, split;

                while (i < names.length) {
                    split = normalizeLocale(names[i]).split('-');
                    j = split.length;
                    next = normalizeLocale(names[i + 1]);
                    next = next ? next.split('-') : null;
                    while (j > 0) {
                        locale = loadLocale(split.slice(0, j).join('-'));
                        if (locale) {
                            return locale;
                        }
                        if (next && next.length >= j && compareArrays(split, next, true) >= j - 1) {
                            //the next array item is better than a shallower substring of this one
                            break;
                        }
                        j--;
                    }
                    i++;
                }
                return null;
            }

            function loadLocale(name) {
                var oldLocale = null;
                if (!locales[name] && hasModule) {
                    try {
                        oldLocale = moment.locale();
                        require('./locale/' + name);
                        // because defineLocale currently also sets the global locale, we want to undo that for lazy loaded locales
                        moment.locale(oldLocale);
                    } catch (e) {
                    }
                }
                return locales[name];
            }

            // Return a moment from input, that is local/utc/utcOffset equivalent to
            // model.
            function makeAs(input, model) {
                var res, diff;
                if (model._isUTC) {
                    res = model.clone();
                    diff = (moment.isMoment(input) || isDate(input) ?
                            +input : +moment(input)) - (+res);
                    // Use low-level api, because this fn is low-level api.
                    res._d.setTime(+res._d + diff);
                    moment.updateOffset(res, false);
                    return res;
                } else {
                    return moment(input).local();
                }
            }

            /************************************
             Locale
             ************************************/


            extend(Locale.prototype, {
                set: function (config) {
                    var prop, i;
                    for (i in config) {
                        prop = config[i];
                        if (typeof prop === 'function') {
                            this[i] = prop;
                        } else {
                            this['_' + i] = prop;
                        }
                    }
                    // Lenient ordinal parsing accepts just a number in addition to
                    // number + (possibly) stuff coming from _ordinalParseLenient.
                    this._ordinalParseLenient = new RegExp(this._ordinalParse.source + '|' + /\d{1,2}/.source);
                },
                _months: "一月_二月_三月_四月_五月_六月_七月_八月_九月_十月_十一月_十二月".split("_"),
                months: function (m) {
                    return this._months[m.month()];
                },
                _monthsShort: "1月_2月_3月_4月_5月_6月_7月_8月_9月_10月_11月_12月".split("_"),
                monthsShort: function (m) {
                    return this._monthsShort[m.month()];
                },
                monthsParse: function (monthName, format, strict) {
                    var i, mom, regex;

                    if (!this._monthsParse) {
                        this._monthsParse = [];
                        this._longMonthsParse = [];
                        this._shortMonthsParse = [];
                    }

                    for (i = 0; i < 12; i++) {
                        // make the regex if we don't have it already
                        mom = moment.utc([2000, i]);
                        if (strict && !this._longMonthsParse[i]) {
                            this._longMonthsParse[i] = new RegExp('^' + this.months(mom, '').replace('.', '') + '$', 'i');
                            this._shortMonthsParse[i] = new RegExp('^' + this.monthsShort(mom, '').replace('.', '') + '$', 'i');
                        }
                        if (!strict && !this._monthsParse[i]) {
                            regex = '^' + this.months(mom, '') + '|^' + this.monthsShort(mom, '');
                            this._monthsParse[i] = new RegExp(regex.replace('.', ''), 'i');
                        }
                        // test the regex
                        if (strict && format === 'MMMM' && this._longMonthsParse[i].test(monthName)) {
                            return i;
                        } else if (strict && format === 'MMM' && this._shortMonthsParse[i].test(monthName)) {
                            return i;
                        } else if (!strict && this._monthsParse[i].test(monthName)) {
                            return i;
                        }
                    }
                },
                  _weekdays: "星期天_星期一_星期二_星期三_星期四_星期五_星期六".split("_"),
                weekdays: function (m) {
                    return this._weekdays[m.day()];
                },
                _weekdaysShort: "周天_周一_周二_周三_周四_周五_周六".split("_"),
                weekdaysShort: function (m) {
                    return this._weekdaysShort[m.day()];
                },
               _weekdaysMin: "日_一_二_三_四_五_六".split("_"),
                weekdaysMin: function (m) {
                    return this._weekdaysMin[m.day()];
                },
                weekdaysParse: function (weekdayName) {
                    var i, mom, regex;

                    if (!this._weekdaysParse) {
                        this._weekdaysParse = [];
                    }

                    for (i = 0; i < 7; i++) {
                        // make the regex if we don't have it already
                        if (!this._weekdaysParse[i]) {
                            mom = moment([2000, 1]).day(i);
                            regex = '^' + this.weekdays(mom, '') + '|^' + this.weekdaysShort(mom, '') + '|^' + this.weekdaysMin(mom, '');
                            this._weekdaysParse[i] = new RegExp(regex.replace('.', ''), 'i');
                        }
                        // test the regex
                        if (this._weekdaysParse[i].test(weekdayName)) {
                            return i;
                        }
                    }
                },
                _longDateFormat: {
                    LTS: 'h:mm:ss A',
                    LT: 'h:mm A',
                    L: 'MM/DD/YYYY',
                    LL: 'MMMM D, YYYY',
                    LLL: 'MMMM D, YYYY LT',
                    LLLL: 'dddd, MMMM D, YYYY LT'
                },
                longDateFormat: function (key) {
                    var output = this._longDateFormat[key];
                    if (!output && this._longDateFormat[key.toUpperCase()]) {
                        output = this._longDateFormat[key.toUpperCase()].replace(/MMMM|MM|DD|dddd/g, function (val) {
                            return val.slice(1);
                        });
                        this._longDateFormat[key] = output;
                    }
                    return output;
                },
                isPM: function (input) {
                    // IE8 Quirks Mode & IE7 Standards Mode do not allow accessing strings like arrays
                    // Using charAt should be more compatible.
                    return ((input + '').toLowerCase().charAt(0) === 'p');
                },
                _meridiemParse: /[ap]\.?m?\.?/i,
                meridiem: function (hours, minutes, isLower) {
                    if (hours > 11) {
                        return isLower ? 'pm' : 'PM';
                    } else {
                        return isLower ? 'am' : 'AM';
                    }
                },
                _calendar: {
                    sameDay: '[Today at] LT',
                    nextDay: '[Tomorrow at] LT',
                    nextWeek: 'dddd [at] LT',
                    lastDay: '[Yesterday at] LT',
                    lastWeek: '[Last] dddd [at] LT',
                    sameElse: 'L'
                },
                calendar: function (key, mom, now) {
                    var output = this._calendar[key];
                    return typeof output === 'function' ? output.apply(mom, [now]) : output;
                },
                _relativeTime: {
                    future: 'in %s',
                    past: '%s ago',
                    s: 'a few seconds',
                    m: 'a minute',
                    mm: '%d minutes',
                    h: 'an hour',
                    hh: '%d hours',
                    d: 'a day',
                    dd: '%d days',
                    M: 'a month',
                    MM: '%d months',
                    y: 'a year',
                    yy: '%d years'
                },
                relativeTime: function (number, withoutSuffix, string, isFuture) {
                    var output = this._relativeTime[string];
                    return (typeof output === 'function') ?
                            output(number, withoutSuffix, string, isFuture) :
                            output.replace(/%d/i, number);
                },
                pastFuture: function (diff, output) {
                    var format = this._relativeTime[diff > 0 ? 'future' : 'past'];
                    return typeof format === 'function' ? format(output) : format.replace(/%s/i, output);
                },
                ordinal: function (number) {
                    return this._ordinal.replace('%d', number);
                },
                _ordinal: '%d',
                _ordinalParse: /\d{1,2}/,
                preparse: function (string) {
                    return string;
                },
                postformat: function (string) {
                    return string;
                },
                week: function (mom) {
                    return weekOfYear(mom, this._week.dow, this._week.doy).week;
                },
                _week: {
                    dow: 0, // Sunday is the first day of the week.
                    doy: 6  // The week that contains Jan 1st is the first week of the year.
                },
                firstDayOfWeek: function () {
                    return this._week.dow;
                },
                firstDayOfYear: function () {
                    return this._week.doy;
                },
                _invalidDate: 'Invalid date',
                invalidDate: function () {
                    return this._invalidDate;
                }
            });

            /************************************
             Formatting
             ************************************/


            function removeFormattingTokens(input) {
                if (input.match(/\[[\s\S]/)) {
                    return input.replace(/^\[|\]$/g, '');
                }
                return input.replace(/\\/g, '');
            }

            function makeFormatFunction(format) {
                var array = format.match(formattingTokens), i, length;

                for (i = 0, length = array.length; i < length; i++) {
                    if (formatTokenFunctions[array[i]]) {
                        array[i] = formatTokenFunctions[array[i]];
                    } else {
                        array[i] = removeFormattingTokens(array[i]);
                    }
                }

                return function (mom) {
                    var output = '';
                    for (i = 0; i < length; i++) {
                        output += array[i] instanceof Function ? array[i].call(mom, format) : array[i];
                    }
                    return output;
                };
            }

            // format date using native date object
            function formatMoment(m, format) {
                if (!m.isValid()) {
                    return m.localeData().invalidDate();
                }

                format = expandFormat(format, m.localeData());

                if (!formatFunctions[format]) {
                    formatFunctions[format] = makeFormatFunction(format);
                }

                return formatFunctions[format](m);
            }

            function expandFormat(format, locale) {
                var i = 5;

                function replaceLongDateFormatTokens(input) {
                    return locale.longDateFormat(input) || input;
                }

                localFormattingTokens.lastIndex = 0;
                while (i >= 0 && localFormattingTokens.test(format)) {
                    format = format.replace(localFormattingTokens, replaceLongDateFormatTokens);
                    localFormattingTokens.lastIndex = 0;
                    i -= 1;
                }

                return format;
            }


            /************************************
             Parsing
             ************************************/


            // get the regex to find the next token
            function getParseRegexForToken(token, config) {
                var a, strict = config._strict;
                switch (token) {
                    case 'Q':
                        return parseTokenOneDigit;
                    case 'DDDD':
                        return parseTokenThreeDigits;
                    case 'YYYY':
                    case 'GGGG':
                    case 'gggg':
                        return strict ? parseTokenFourDigits : parseTokenOneToFourDigits;
                    case 'Y':
                    case 'G':
                    case 'g':
                        return parseTokenSignedNumber;
                    case 'YYYYYY':
                    case 'YYYYY':
                    case 'GGGGG':
                    case 'ggggg':
                        return strict ? parseTokenSixDigits : parseTokenOneToSixDigits;
                    case 'S':
                        if (strict) {
                            return parseTokenOneDigit;
                        }
                        /* falls through */
                    case 'SS':
                        if (strict) {
                            return parseTokenTwoDigits;
                        }
                        /* falls through */
                    case 'SSS':
                        if (strict) {
                            return parseTokenThreeDigits;
                        }
                        /* falls through */
                    case 'DDD':
                        return parseTokenOneToThreeDigits;
                    case 'MMM':
                    case 'MMMM':
                    case 'dd':
                    case 'ddd':
                    case 'dddd':
                        return parseTokenWord;
                    case 'a':
                    case 'A':
                        return config._locale._meridiemParse;
                    case 'x':
                        return parseTokenOffsetMs;
                    case 'X':
                        return parseTokenTimestampMs;
                    case 'Z':
                    case 'ZZ':
                        return parseTokenTimezone;
                    case 'T':
                        return parseTokenT;
                    case 'SSSS':
                        return parseTokenDigits;
                    case 'MM':
                    case 'DD':
                    case 'YY':
                    case 'GG':
                    case 'gg':
                    case 'HH':
                    case 'hh':
                    case 'mm':
                    case 'ss':
                    case 'ww':
                    case 'WW':
                        return strict ? parseTokenTwoDigits : parseTokenOneOrTwoDigits;
                    case 'M':
                    case 'D':
                    case 'd':
                    case 'H':
                    case 'h':
                    case 'm':
                    case 's':
                    case 'w':
                    case 'W':
                    case 'e':
                    case 'E':
                        return parseTokenOneOrTwoDigits;
                    case 'Do':
                        return strict ? config._locale._ordinalParse : config._locale._ordinalParseLenient;
                    default :
                        a = new RegExp(regexpEscape(unescapeFormat(token.replace('\\', '')), 'i'));
                        return a;
                }
            }

            function utcOffsetFromString(string) {
                string = string || '';
                var possibleTzMatches = (string.match(parseTokenTimezone) || []),
                        tzChunk = possibleTzMatches[possibleTzMatches.length - 1] || [],
                        parts = (tzChunk + '').match(parseTimezoneChunker) || ['-', 0, 0],
                        minutes = +(parts[1] * 60) + toInt(parts[2]);

                return parts[0] === '+' ? minutes : -minutes;
            }

            // function to convert string input to date
            function addTimeToArrayFromToken(token, input, config) {
                var a, datePartArray = config._a;

                switch (token) {
                    // QUARTER
                    case 'Q':
                        if (input != null) {
                            datePartArray[MONTH] = (toInt(input) - 1) * 3;
                        }
                        break;
                        // MONTH
                    case 'M' : // fall through to MM
                    case 'MM' :
                        if (input != null) {
                            datePartArray[MONTH] = toInt(input) - 1;
                        }
                        break;
                    case 'MMM' : // fall through to MMMM
                    case 'MMMM' :
                        a = config._locale.monthsParse(input, token, config._strict);
                        // if we didn't find a month name, mark the date as invalid.
                        if (a != null) {
                            datePartArray[MONTH] = a;
                        } else {
                            config._pf.invalidMonth = input;
                        }
                        break;
                        // DAY OF MONTH
                    case 'D' : // fall through to DD
                    case 'DD' :
                        if (input != null) {
                            datePartArray[DATE] = toInt(input);
                        }
                        break;
                    case 'Do' :
                        if (input != null) {
                            datePartArray[DATE] = toInt(parseInt(
                                    input.match(/\d{1,2}/)[0], 10));
                        }
                        break;
                        // DAY OF YEAR
                    case 'DDD' : // fall through to DDDD
                    case 'DDDD' :
                        if (input != null) {
                            config._dayOfYear = toInt(input);
                        }

                        break;
                        // YEAR
                    case 'YY' :
                        datePartArray[YEAR] = moment.parseTwoDigitYear(input);
                        break;
                    case 'YYYY' :
                    case 'YYYYY' :
                    case 'YYYYYY' :
                        datePartArray[YEAR] = toInt(input);
                        break;
                        // AM / PM
                    case 'a' : // fall through to A
                    case 'A' :
                        config._meridiem = input;
                        // config._isPm = config._locale.isPM(input);
                        break;
                        // HOUR
                    case 'h' : // fall through to hh
                    case 'hh' :
                        config._pf.bigHour = true;
                        /* falls through */
                    case 'H' : // fall through to HH
                    case 'HH' :
                        datePartArray[HOUR] = toInt(input);
                        break;
                        // MINUTE
                    case 'm' : // fall through to mm
                    case 'mm' :
                        datePartArray[MINUTE] = toInt(input);
                        break;
                        // SECOND
                    case 's' : // fall through to ss
                    case 'ss' :
                        datePartArray[SECOND] = toInt(input);
                        break;
                        // MILLISECOND
                    case 'S' :
                    case 'SS' :
                    case 'SSS' :
                    case 'SSSS' :
                        datePartArray[MILLISECOND] = toInt(('0.' + input) * 1000);
                        break;
                        // UNIX OFFSET (MILLISECONDS)
                    case 'x':
                        config._d = new Date(toInt(input));
                        break;
                        // UNIX TIMESTAMP WITH MS
                    case 'X':
                        config._d = new Date(parseFloat(input) * 1000);
                        break;
                        // TIMEZONE
                    case 'Z' : // fall through to ZZ
                    case 'ZZ' :
                        config._useUTC = true;
                        config._tzm = utcOffsetFromString(input);
                        break;
                        // WEEKDAY - human
                    case 'dd':
                    case 'ddd':
                    case 'dddd':
                        a = config._locale.weekdaysParse(input);
                        // if we didn't get a weekday name, mark the date as invalid
                        if (a != null) {
                            config._w = config._w || {};
                            config._w['d'] = a;
                        } else {
                            config._pf.invalidWeekday = input;
                        }
                        break;
                        // WEEK, WEEK DAY - numeric
                    case 'w':
                    case 'ww':
                    case 'W':
                    case 'WW':
                    case 'd':
                    case 'e':
                    case 'E':
                        token = token.substr(0, 1);
                        /* falls through */
                    case 'gggg':
                    case 'GGGG':
                    case 'GGGGG':
                        token = token.substr(0, 2);
                        if (input) {
                            config._w = config._w || {};
                            config._w[token] = toInt(input);
                        }
                        break;
                    case 'gg':
                    case 'GG':
                        config._w = config._w || {};
                        config._w[token] = moment.parseTwoDigitYear(input);
                }
            }

            function dayOfYearFromWeekInfo(config) {
                var w, weekYear, week, weekday, dow, doy, temp;

                w = config._w;
                if (w.GG != null || w.W != null || w.E != null) {
                    dow = 1;
                    doy = 4;

                    // TODO: We need to take the current isoWeekYear, but that depends on
                    // how we interpret now (local, utc, fixed offset). So create
                    // a now version of current config (take local/utc/offset flags, and
                    // create now).
                    weekYear = dfl(w.GG, config._a[YEAR], weekOfYear(moment(), 1, 4).year);
                    week = dfl(w.W, 1);
                    weekday = dfl(w.E, 1);
                } else {
                    dow = config._locale._week.dow;
                    doy = config._locale._week.doy;

                    weekYear = dfl(w.gg, config._a[YEAR], weekOfYear(moment(), dow, doy).year);
                    week = dfl(w.w, 1);

                    if (w.d != null) {
                        // weekday -- low day numbers are considered next week
                        weekday = w.d;
                        if (weekday < dow) {
                            ++week;
                        }
                    } else if (w.e != null) {
                        // local weekday -- counting starts from begining of week
                        weekday = w.e + dow;
                    } else {
                        // default to begining of week
                        weekday = dow;
                    }
                }
                temp = dayOfYearFromWeeks(weekYear, week, weekday, doy, dow);

                config._a[YEAR] = temp.year;
                config._dayOfYear = temp.dayOfYear;
            }

            // convert an array to a date.
            // the array should mirror the parameters below
            // note: all values past the year are optional and will default to the lowest possible value.
            // [year, month, day , hour, minute, second, millisecond]
            function dateFromConfig(config) {
                var i, date, input = [], currentDate, yearToUse;

                if (config._d) {
                    return;
                }

                currentDate = currentDateArray(config);

                //compute day of the year from weeks and weekdays
                if (config._w && config._a[DATE] == null && config._a[MONTH] == null) {
                    dayOfYearFromWeekInfo(config);
                }

                //if the day of the year is set, figure out what it is
                if (config._dayOfYear) {
                    yearToUse = dfl(config._a[YEAR], currentDate[YEAR]);

                    if (config._dayOfYear > daysInYear(yearToUse)) {
                        config._pf._overflowDayOfYear = true;
                    }

                    date = makeUTCDate(yearToUse, 0, config._dayOfYear);
                    config._a[MONTH] = date.getUTCMonth();
                    config._a[DATE] = date.getUTCDate();
                }

                // Default to current date.
                // * if no year, month, day of month are given, default to today
                // * if day of month is given, default month and year
                // * if month is given, default only year
                // * if year is given, don't default anything
                for (i = 0; i < 3 && config._a[i] == null; ++i) {
                    config._a[i] = input[i] = currentDate[i];
                }

                // Zero out whatever was not defaulted, including time
                for (; i < 7; i++) {
                    config._a[i] = input[i] = (config._a[i] == null) ? (i === 2 ? 1 : 0) : config._a[i];
                }

                // Check for 24:00:00.000
                if (config._a[HOUR] === 24 &&
                        config._a[MINUTE] === 0 &&
                        config._a[SECOND] === 0 &&
                        config._a[MILLISECOND] === 0) {
                    config._nextDay = true;
                    config._a[HOUR] = 0;
                }

                config._d = (config._useUTC ? makeUTCDate : makeDate).apply(null, input);
                // Apply timezone offset from input. The actual utcOffset can be changed
                // with parseZone.
                if (config._tzm != null) {
                    config._d.setUTCMinutes(config._d.getUTCMinutes() - config._tzm);
                }

                if (config._nextDay) {
                    config._a[HOUR] = 24;
                }
            }

            function dateFromObject(config) {
                var normalizedInput;

                if (config._d) {
                    return;
                }

                normalizedInput = normalizeObjectUnits(config._i);
                config._a = [
                    normalizedInput.year,
                    normalizedInput.month,
                    normalizedInput.day || normalizedInput.date,
                    normalizedInput.hour,
                    normalizedInput.minute,
                    normalizedInput.second,
                    normalizedInput.millisecond
                ];

                dateFromConfig(config);
            }

            function currentDateArray(config) {
                var now = new Date();
                if (config._useUTC) {
                    return [
                        now.getUTCFullYear(),
                        now.getUTCMonth(),
                        now.getUTCDate()
                    ];
                } else {
                    return [now.getFullYear(), now.getMonth(), now.getDate()];
                }
            }

            // date from string and format string
            function makeDateFromStringAndFormat(config) {
                if (config._f === moment.ISO_8601) {
                    parseISO(config);
                    return;
                }

                config._a = [];
                config._pf.empty = true;

                // This array is used to make a Date, either with `new Date` or `Date.UTC`
                var string = '' + config._i,
                        i, parsedInput, tokens, token, skipped,
                        stringLength = string.length,
                        totalParsedInputLength = 0;

                tokens = expandFormat(config._f, config._locale).match(formattingTokens) || [];

                for (i = 0; i < tokens.length; i++) {
                    token = tokens[i];
                    parsedInput = (string.match(getParseRegexForToken(token, config)) || [])[0];
                    if (parsedInput) {
                        skipped = string.substr(0, string.indexOf(parsedInput));
                        if (skipped.length > 0) {
                            config._pf.unusedInput.push(skipped);
                        }
                        string = string.slice(string.indexOf(parsedInput) + parsedInput.length);
                        totalParsedInputLength += parsedInput.length;
                    }
                    // don't parse if it's not a known token
                    if (formatTokenFunctions[token]) {
                        if (parsedInput) {
                            config._pf.empty = false;
                        }
                        else {
                            config._pf.unusedTokens.push(token);
                        }
                        addTimeToArrayFromToken(token, parsedInput, config);
                    }
                    else if (config._strict && !parsedInput) {
                        config._pf.unusedTokens.push(token);
                    }
                }

                // add remaining unparsed input length to the string
                config._pf.charsLeftOver = stringLength - totalParsedInputLength;
                if (string.length > 0) {
                    config._pf.unusedInput.push(string);
                }

                // clear _12h flag if hour is <= 12
                if (config._pf.bigHour === true && config._a[HOUR] <= 12) {
                    config._pf.bigHour = undefined;
                }
                // handle meridiem
                config._a[HOUR] = meridiemFixWrap(config._locale, config._a[HOUR],
                        config._meridiem);
                dateFromConfig(config);
                checkOverflow(config);
            }

            function unescapeFormat(s) {
                return s.replace(/\\(\[)|\\(\])|\[([^\]\[]*)\]|\\(.)/g, function (matched, p1, p2, p3, p4) {
                    return p1 || p2 || p3 || p4;
                });
            }

            // Code from http://stackoverflow.com/questions/3561493/is-there-a-regexp-escape-function-in-javascript
            function regexpEscape(s) {
                return s.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&');
            }

            // date from string and array of format strings
            function makeDateFromStringAndArray(config) {
                var tempConfig,
                        bestMoment,
                        scoreToBeat,
                        i,
                        currentScore;

                if (config._f.length === 0) {
                    config._pf.invalidFormat = true;
                    config._d = new Date(NaN);
                    return;
                }

                for (i = 0; i < config._f.length; i++) {
                    currentScore = 0;
                    tempConfig = copyConfig({}, config);
                    if (config._useUTC != null) {
                        tempConfig._useUTC = config._useUTC;
                    }
                    tempConfig._pf = defaultParsingFlags();
                    tempConfig._f = config._f[i];
                    makeDateFromStringAndFormat(tempConfig);

                    if (!isValid(tempConfig)) {
                        continue;
                    }

                    // if there is any input that was not parsed add a penalty for that format
                    currentScore += tempConfig._pf.charsLeftOver;

                    //or tokens
                    currentScore += tempConfig._pf.unusedTokens.length * 10;

                    tempConfig._pf.score = currentScore;

                    if (scoreToBeat == null || currentScore < scoreToBeat) {
                        scoreToBeat = currentScore;
                        bestMoment = tempConfig;
                    }
                }

                extend(config, bestMoment || tempConfig);
            }

            // date from iso format
            function parseISO(config) {
                var i, l,
                        string = config._i,
                        match = isoRegex.exec(string);

                if (match) {
                    config._pf.iso = true;
                    for (i = 0, l = isoDates.length; i < l; i++) {
                        if (isoDates[i][1].exec(string)) {
                            // match[5] should be 'T' or undefined
                            config._f = isoDates[i][0] + (match[6] || ' ');
                            break;
                        }
                    }
                    for (i = 0, l = isoTimes.length; i < l; i++) {
                        if (isoTimes[i][1].exec(string)) {
                            config._f += isoTimes[i][0];
                            break;
                        }
                    }
                    if (string.match(parseTokenTimezone)) {
                        config._f += 'Z';
                    }
                    makeDateFromStringAndFormat(config);
                } else {
                    config._isValid = false;
                }
            }

            // date from iso format or fallback
            function makeDateFromString(config) {
                parseISO(config);
                if (config._isValid === false) {
                    delete config._isValid;
                    moment.createFromInputFallback(config);
                }
            }

            function map(arr, fn) {
                var res = [], i;
                for (i = 0; i < arr.length; ++i) {
                    res.push(fn(arr[i], i));
                }
                return res;
            }

            function makeDateFromInput(config) {
                var input = config._i, matched;
                if (input === undefined) {
                    config._d = new Date();
                } else if (isDate(input)) {
                    config._d = new Date(+input);
                } else if ((matched = aspNetJsonRegex.exec(input)) !== null) {
                    config._d = new Date(+matched[1]);
                } else if (typeof input === 'string') {
                    makeDateFromString(config);
                } else if (isArray(input)) {
                    config._a = map(input.slice(0), function (obj) {
                        return parseInt(obj, 10);
                    });
                    dateFromConfig(config);
                } else if (typeof (input) === 'object') {
                    dateFromObject(config);
                } else if (typeof (input) === 'number') {
                    // from milliseconds
                    config._d = new Date(input);
                } else {
                    moment.createFromInputFallback(config);
                }
            }

            function makeDate(y, m, d, h, M, s, ms) {
                //can't just apply() to create a date:
                //http://stackoverflow.com/questions/181348/instantiating-a-javascript-object-by-calling-prototype-constructor-apply
                var date = new Date(y, m, d, h, M, s, ms);

                //the date constructor doesn't accept years < 1970
                if (y < 1970) {
                    date.setFullYear(y);
                }
                return date;
            }

            function makeUTCDate(y) {
                var date = new Date(Date.UTC.apply(null, arguments));
                if (y < 1970) {
                    date.setUTCFullYear(y);
                }
                return date;
            }

            function parseWeekday(input, locale) {
                if (typeof input === 'string') {
                    if (!isNaN(input)) {
                        input = parseInt(input, 10);
                    }
                    else {
                        input = locale.weekdaysParse(input);
                        if (typeof input !== 'number') {
                            return null;
                        }
                    }
                }
                return input;
            }

            /************************************
             Relative Time
             ************************************/


            // helper function for moment.fn.from, moment.fn.fromNow, and moment.duration.fn.humanize
            function substituteTimeAgo(string, number, withoutSuffix, isFuture, locale) {
                return locale.relativeTime(number || 1, !!withoutSuffix, string, isFuture);
            }

            function relativeTime(posNegDuration, withoutSuffix, locale) {
                var duration = moment.duration(posNegDuration).abs(),
                        seconds = round(duration.as('s')),
                        minutes = round(duration.as('m')),
                        hours = round(duration.as('h')),
                        days = round(duration.as('d')),
                        months = round(duration.as('M')),
                        years = round(duration.as('y')),
                        args = seconds < relativeTimeThresholds.s && ['s', seconds] ||
                        minutes === 1 && ['m'] ||
                        minutes < relativeTimeThresholds.m && ['mm', minutes] ||
                        hours === 1 && ['h'] ||
                        hours < relativeTimeThresholds.h && ['hh', hours] ||
                        days === 1 && ['d'] ||
                        days < relativeTimeThresholds.d && ['dd', days] ||
                        months === 1 && ['M'] ||
                        months < relativeTimeThresholds.M && ['MM', months] ||
                        years === 1 && ['y'] || ['yy', years];

                args[2] = withoutSuffix;
                args[3] = +posNegDuration > 0;
                args[4] = locale;
                return substituteTimeAgo.apply({}, args);
            }


            /************************************
             Week of Year
             ************************************/


            // firstDayOfWeek       0 = sun, 6 = sat
            //                      the day of the week that starts the week
            //                      (usually sunday or monday)
            // firstDayOfWeekOfYear 0 = sun, 6 = sat
            //                      the first week is the week that contains the first
            //                      of this day of the week
            //                      (eg. ISO weeks use thursday (4))
            function weekOfYear(mom, firstDayOfWeek, firstDayOfWeekOfYear) {
                var end = firstDayOfWeekOfYear - firstDayOfWeek,
                        daysToDayOfWeek = firstDayOfWeekOfYear - mom.day(),
                        adjustedMoment;


                if (daysToDayOfWeek > end) {
                    daysToDayOfWeek -= 7;
                }

                if (daysToDayOfWeek < end - 7) {
                    daysToDayOfWeek += 7;
                }

                adjustedMoment = moment(mom).add(daysToDayOfWeek, 'd');
                return {
                    week: Math.ceil(adjustedMoment.dayOfYear() / 7),
                    year: adjustedMoment.year()
                };
            }

            //http://en.wikipedia.org/wiki/ISO_week_date#Calculating_a_date_given_the_year.2C_week_number_and_weekday
            function dayOfYearFromWeeks(year, week, weekday, firstDayOfWeekOfYear, firstDayOfWeek) {
                var d = makeUTCDate(year, 0, 1).getUTCDay(), daysToAdd, dayOfYear;

                d = d === 0 ? 7 : d;
                weekday = weekday != null ? weekday : firstDayOfWeek;
                daysToAdd = firstDayOfWeek - d + (d > firstDayOfWeekOfYear ? 7 : 0) - (d < firstDayOfWeek ? 7 : 0);
                dayOfYear = 7 * (week - 1) + (weekday - firstDayOfWeek) + daysToAdd + 1;

                return {
                    year: dayOfYear > 0 ? year : year - 1,
                    dayOfYear: dayOfYear > 0 ? dayOfYear : daysInYear(year - 1) + dayOfYear
                };
            }

            /************************************
             Top Level Functions
             ************************************/

            function makeMoment(config) {
                var input = config._i,
                        format = config._f,
                        res;

                config._locale = config._locale || moment.localeData(config._l);

                if (input === null || (format === undefined && input === '')) {
                    return moment.invalid({nullInput: true});
                }

                if (typeof input === 'string') {
                    config._i = input = config._locale.preparse(input);
                }

                if (moment.isMoment(input)) {
                    return new Moment(input, true);
                } else if (format) {
                    if (isArray(format)) {
                        makeDateFromStringAndArray(config);
                    } else {
                        makeDateFromStringAndFormat(config);
                    }
                } else {
                    makeDateFromInput(config);
                }

                res = new Moment(config);
                if (res._nextDay) {
                    // Adding is smart enough around DST
                    res.add(1, 'd');
                    res._nextDay = undefined;
                }

                return res;
            }

            moment = function (input, format, locale, strict) {
                var c;

                if (typeof (locale) === 'boolean') {
                    strict = locale;
                    locale = undefined;
                }
                // object construction must be done this way.
                // https://github.com/moment/moment/issues/1423
                c = {};
                c._isAMomentObject = true;
                c._i = input;
                c._f = format;
                c._l = locale;
                c._strict = strict;
                c._isUTC = false;
                c._pf = defaultParsingFlags();

                return makeMoment(c);
            };

            moment.suppressDeprecationWarnings = false;

            moment.createFromInputFallback = deprecate(
                    'moment construction falls back to js Date. This is ' +
                    'discouraged and will be removed in upcoming major ' +
                    'release. Please refer to ' +
                    'https://github.com/moment/moment/issues/1407 for more info.',
                    function (config) {
                        config._d = new Date(config._i + (config._useUTC ? ' UTC' : ''));
                    }
            );

            // Pick a moment m from moments so that m[fn](other) is true for all
            // other. This relies on the function fn to be transitive.
            //
            // moments should either be an array of moment objects or an array, whose
            // first element is an array of moment objects.
            function pickBy(fn, moments) {
                var res, i;
                if (moments.length === 1 && isArray(moments[0])) {
                    moments = moments[0];
                }
                if (!moments.length) {
                    return moment();
                }
                res = moments[0];
                for (i = 1; i < moments.length; ++i) {
                    if (moments[i][fn](res)) {
                        res = moments[i];
                    }
                }
                return res;
            }

            moment.min = function () {
                var args = [].slice.call(arguments, 0);

                return pickBy('isBefore', args);
            };

            moment.max = function () {
                var args = [].slice.call(arguments, 0);

                return pickBy('isAfter', args);
            };

            // creating with utc
            moment.utc = function (input, format, locale, strict) {
                var c;

                if (typeof (locale) === 'boolean') {
                    strict = locale;
                    locale = undefined;
                }
                // object construction must be done this way.
                // https://github.com/moment/moment/issues/1423
                c = {};
                c._isAMomentObject = true;
                c._useUTC = true;
                c._isUTC = true;
                c._l = locale;
                c._i = input;
                c._f = format;
                c._strict = strict;
                c._pf = defaultParsingFlags();

                return makeMoment(c).utc();
            };

            // creating with unix timestamp (in seconds)
            moment.unix = function (input) {
                return moment(input * 1000);
            };

            // duration
            moment.duration = function (input, key) {
                var duration = input,
                        // matching against regexp is expensive, do it on demand
                        match = null,
                        sign,
                        ret,
                        parseIso,
                        diffRes;

                if (moment.isDuration(input)) {
                    duration = {
                        ms: input._milliseconds,
                        d: input._days,
                        M: input._months
                    };
                } else if (typeof input === 'number') {
                    duration = {};
                    if (key) {
                        duration[key] = input;
                    } else {
                        duration.milliseconds = input;
                    }
                } else if (!!(match = aspNetTimeSpanJsonRegex.exec(input))) {
                    sign = (match[1] === '-') ? -1 : 1;
                    duration = {
                        y: 0,
                        d: toInt(match[DATE]) * sign,
                        h: toInt(match[HOUR]) * sign,
                        m: toInt(match[MINUTE]) * sign,
                        s: toInt(match[SECOND]) * sign,
                        ms: toInt(match[MILLISECOND]) * sign
                    };
                } else if (!!(match = isoDurationRegex.exec(input))) {
                    sign = (match[1] === '-') ? -1 : 1;
                    parseIso = function (inp) {
                        // We'd normally use ~~inp for this, but unfortunately it also
                        // converts floats to ints.
                        // inp may be undefined, so careful calling replace on it.
                        var res = inp && parseFloat(inp.replace(',', '.'));
                        // apply sign while we're at it
                        return (isNaN(res) ? 0 : res) * sign;
                    };
                    duration = {
                        y: parseIso(match[2]),
                        M: parseIso(match[3]),
                        d: parseIso(match[4]),
                        h: parseIso(match[5]),
                        m: parseIso(match[6]),
                        s: parseIso(match[7]),
                        w: parseIso(match[8])
                    };
                } else if (duration == null) {// checks for null or undefined
                    duration = {};
                } else if (typeof duration === 'object' &&
                        ('from' in duration || 'to' in duration)) {
                    diffRes = momentsDifference(moment(duration.from), moment(duration.to));

                    duration = {};
                    duration.ms = diffRes.milliseconds;
                    duration.M = diffRes.months;
                }

                ret = new Duration(duration);

                if (moment.isDuration(input) && hasOwnProp(input, '_locale')) {
                    ret._locale = input._locale;
                }

                return ret;
            };

            // version number
            moment.version = VERSION;

            // default format
            moment.defaultFormat = isoFormat;

            // constant that refers to the ISO standard
            moment.ISO_8601 = function () {
            };

            // Plugins that add properties should also add the key here (null value),
            // so we can properly clone ourselves.
            moment.momentProperties = momentProperties;

            // This function will be called whenever a moment is mutated.
            // It is intended to keep the offset in sync with the timezone.
            moment.updateOffset = function () {
            };

            // This function allows you to set a threshold for relative time strings
            moment.relativeTimeThreshold = function (threshold, limit) {
                if (relativeTimeThresholds[threshold] === undefined) {
                    return false;
                }
                if (limit === undefined) {
                    return relativeTimeThresholds[threshold];
                }
                relativeTimeThresholds[threshold] = limit;
                return true;
            };

            moment.lang = deprecate(
                    'moment.lang is deprecated. Use moment.locale instead.',
                    function (key, value) {
                        return moment.locale(key, value);
                    }
            );

            // This function will load locale and then set the global locale.  If
            // no arguments are passed in, it will simply return the current global
            // locale key.
            moment.locale = function (key, values) {
                var data;
                if (key) {
                    if (typeof (values) !== 'undefined') {
                        data = moment.defineLocale(key, values);
                    }
                    else {
                        data = moment.localeData(key);
                    }

                    if (data) {
                        moment.duration._locale = moment._locale = data;
                    }
                }

                return moment._locale._abbr;
            };

            moment.defineLocale = function (name, values) {
                if (values !== null) {
                    values.abbr = name;
                    if (!locales[name]) {
                        locales[name] = new Locale();
                    }
                    locales[name].set(values);

                    // backwards compat for now: also set the locale
                    moment.locale(name);

                    return locales[name];
                } else {
                    // useful for testing
                    delete locales[name];
                    return null;
                }
            };

            moment.langData = deprecate(
                    'moment.langData is deprecated. Use moment.localeData instead.',
                    function (key) {
                        return moment.localeData(key);
                    }
            );

            // returns locale data
            moment.localeData = function (key) {
                var locale;

                if (key && key._locale && key._locale._abbr) {
                    key = key._locale._abbr;
                }

                if (!key) {
                    return moment._locale;
                }

                if (!isArray(key)) {
                    //short-circuit everything else
                    locale = loadLocale(key);
                    if (locale) {
                        return locale;
                    }
                    key = [key];
                }

                return chooseLocale(key);
            };

            // compare moment object
            moment.isMoment = function (obj) {
                return obj instanceof Moment ||
                        (obj != null && hasOwnProp(obj, '_isAMomentObject'));
            };

            // for typechecking Duration objects
            moment.isDuration = function (obj) {
                return obj instanceof Duration;
            };

            for (i = lists.length - 1; i >= 0; --i) {
                makeList(lists[i]);
            }

            moment.normalizeUnits = function (units) {
                return normalizeUnits(units);
            };

            moment.invalid = function (flags) {
                var m = moment.utc(NaN);
                if (flags != null) {
                    extend(m._pf, flags);
                }
                else {
                    m._pf.userInvalidated = true;
                }

                return m;
            };

            moment.parseZone = function () {
                return moment.apply(null, arguments).parseZone();
            };

            moment.parseTwoDigitYear = function (input) {
                return toInt(input) + (toInt(input) > 68 ? 1900 : 2000);
            };

            moment.isDate = isDate;

            /************************************
             Moment Prototype
             ************************************/


            extend(moment.fn = Moment.prototype, {
                clone: function () {
                    return moment(this);
                },
                valueOf: function () {
                    return +this._d - ((this._offset || 0) * 60000);
                },
                unix: function () {
                    return Math.floor(+this / 1000);
                },
                toString: function () {
                    return this.clone().locale('en').format('ddd MMM DD YYYY HH:mm:ss [GMT]ZZ');
                },
                toDate: function () {
                    return this._offset ? new Date(+this) : this._d;
                },
                toISOString: function () {
                    var m = moment(this).utc();
                    if (0 < m.year() && m.year() <= 9999) {
                        if ('function' === typeof Date.prototype.toISOString) {
                            // native implementation is ~50x faster, use it when we can
                            return this.toDate().toISOString();
                        } else {
                            return formatMoment(m, 'YYYY-MM-DD[T]HH:mm:ss.SSS[Z]');
                        }
                    } else {
                        return formatMoment(m, 'YYYYYY-MM-DD[T]HH:mm:ss.SSS[Z]');
                    }
                },
                toArray: function () {
                    var m = this;
                    return [
                        m.year(),
                        m.month(),
                        m.date(),
                        m.hours(),
                        m.minutes(),
                        m.seconds(),
                        m.milliseconds()
                    ];
                },
                isValid: function () {
                    return isValid(this);
                },
                isDSTShifted: function () {
                    if (this._a) {
                        return this.isValid() && compareArrays(this._a, (this._isUTC ? moment.utc(this._a) : moment(this._a)).toArray()) > 0;
                    }

                    return false;
                },
                parsingFlags: function () {
                    return extend({}, this._pf);
                },
                invalidAt: function () {
                    return this._pf.overflow;
                },
                utc: function (keepLocalTime) {
                    return this.utcOffset(0, keepLocalTime);
                },
                local: function (keepLocalTime) {
                    if (this._isUTC) {
                        this.utcOffset(0, keepLocalTime);
                        this._isUTC = false;

                        if (keepLocalTime) {
                            this.subtract(this._dateUtcOffset(), 'm');
                        }
                    }
                    return this;
                },
                format: function (inputString) {
                    var output = formatMoment(this, inputString || moment.defaultFormat);
                    return this.localeData().postformat(output);
                },
                add: createAdder(1, 'add'),
                subtract: createAdder(-1, 'subtract'),
                diff: function (input, units, asFloat) {
                    var that = makeAs(input, this),
                            zoneDiff = (that.utcOffset() - this.utcOffset()) * 6e4,
                            anchor, diff, output, daysAdjust;

                    units = normalizeUnits(units);

                    if (units === 'year' || units === 'month' || units === 'quarter') {
                        output = monthDiff(this, that);
                        if (units === 'quarter') {
                            output = output / 3;
                        } else if (units === 'year') {
                            output = output / 12;
                        }
                    } else {
                        diff = this - that;
                        output = units === 'second' ? diff / 1e3 : // 1000
                                units === 'minute' ? diff / 6e4 : // 1000 * 60
                                units === 'hour' ? diff / 36e5 : // 1000 * 60 * 60
                                units === 'day' ? (diff - zoneDiff) / 864e5 : // 1000 * 60 * 60 * 24, negate dst
                                units === 'week' ? (diff - zoneDiff) / 6048e5 : // 1000 * 60 * 60 * 24 * 7, negate dst
                                diff;
                    }
                    return asFloat ? output : absRound(output);
                },
                from: function (time, withoutSuffix) {
                    return moment.duration({to: this, from: time}).locale(this.locale()).humanize(!withoutSuffix);
                },
                fromNow: function (withoutSuffix) {
                    return this.from(moment(), withoutSuffix);
                },
                calendar: function (time) {
                    // We want to compare the start of today, vs this.
                    // Getting start-of-today depends on whether we're locat/utc/offset
                    // or not.
                    var now = time || moment(),
                            sod = makeAs(now, this).startOf('day'),
                            diff = this.diff(sod, 'days', true),
                            format = diff < -6 ? 'sameElse' :
                            diff < -1 ? 'lastWeek' :
                            diff < 0 ? 'lastDay' :
                            diff < 1 ? 'sameDay' :
                            diff < 2 ? 'nextDay' :
                            diff < 7 ? 'nextWeek' : 'sameElse';
                    return this.format(this.localeData().calendar(format, this, moment(now)));
                },
                isLeapYear: function () {
                    return isLeapYear(this.year());
                },
                isDST: function () {
                    return (this.utcOffset() > this.clone().month(0).utcOffset() ||
                            this.utcOffset() > this.clone().month(5).utcOffset());
                },
                day: function (input) {
                    var day = this._isUTC ? this._d.getUTCDay() : this._d.getDay();
                    if (input != null) {
                        input = parseWeekday(input, this.localeData());
                        return this.add(input - day, 'd');
                    } else {
                        return day;
                    }
                },
                month: makeAccessor('Month', true),
                startOf: function (units) {
                    units = normalizeUnits(units);
                    // the following switch intentionally omits break keywords
                    // to utilize falling through the cases.
                    switch (units) {
                        case 'year':
                            this.month(0);
                            /* falls through */
                        case 'quarter':
                        case 'month':
                            this.date(1);
                            /* falls through */
                        case 'week':
                        case 'isoWeek':
                        case 'day':
                            this.hours(0);
                            /* falls through */
                        case 'hour':
                            this.minutes(0);
                            /* falls through */
                        case 'minute':
                            this.seconds(0);
                            /* falls through */
                        case 'second':
                            this.milliseconds(0);
                            /* falls through */
                    }

                    // weeks are a special case
                    if (units === 'week') {
                        this.weekday(0);
                    } else if (units === 'isoWeek') {
                        this.isoWeekday(1);
                    }

                    // quarters are also special
                    if (units === 'quarter') {
                        this.month(Math.floor(this.month() / 3) * 3);
                    }

                    return this;
                },
                endOf: function (units) {
                    units = normalizeUnits(units);
                    if (units === undefined || units === 'millisecond') {
                        return this;
                    }
                    return this.startOf(units).add(1, (units === 'isoWeek' ? 'week' : units)).subtract(1, 'ms');
                },
                isAfter: function (input, units) {
                    var inputMs;
                    units = normalizeUnits(typeof units !== 'undefined' ? units : 'millisecond');
                    if (units === 'millisecond') {
                        input = moment.isMoment(input) ? input : moment(input);
                        return +this > +input;
                    } else {
                        inputMs = moment.isMoment(input) ? +input : +moment(input);
                        return inputMs < +this.clone().startOf(units);
                    }
                },
                isBefore: function (input, units) {
                    var inputMs;
                    units = normalizeUnits(typeof units !== 'undefined' ? units : 'millisecond');
                    if (units === 'millisecond') {
                        input = moment.isMoment(input) ? input : moment(input);
                        return +this < +input;
                    } else {
                        inputMs = moment.isMoment(input) ? +input : +moment(input);
                        return +this.clone().endOf(units) < inputMs;
                    }
                },
                isBetween: function (from, to, units) {
                    return this.isAfter(from, units) && this.isBefore(to, units);
                },
                isSame: function (input, units) {
                    var inputMs;
                    units = normalizeUnits(units || 'millisecond');
                    if (units === 'millisecond') {
                        input = moment.isMoment(input) ? input : moment(input);
                        return +this === +input;
                    } else {
                        inputMs = +moment(input);
                        return +(this.clone().startOf(units)) <= inputMs && inputMs <= +(this.clone().endOf(units));
                    }
                },
                min: deprecate(
                        'moment().min is deprecated, use moment.min instead. https://github.com/moment/moment/issues/1548',
                        function (other) {
                            other = moment.apply(null, arguments);
                            return other < this ? this : other;
                        }
                ),
                max: deprecate(
                        'moment().max is deprecated, use moment.max instead. https://github.com/moment/moment/issues/1548',
                        function (other) {
                            other = moment.apply(null, arguments);
                            return other > this ? this : other;
                        }
                ),
                zone: deprecate(
                        'moment().zone is deprecated, use moment().utcOffset instead. ' +
                        'https://github.com/moment/moment/issues/1779',
                        function (input, keepLocalTime) {
                            if (input != null) {
                                if (typeof input !== 'string') {
                                    input = -input;
                                }

                                this.utcOffset(input, keepLocalTime);

                                return this;
                            } else {
                                return -this.utcOffset();
                            }
                        }
                ),
                // keepLocalTime = true means only change the timezone, without
                // affecting the local hour. So 5:31:26 +0300 --[utcOffset(2, true)]-->
                // 5:31:26 +0200 It is possible that 5:31:26 doesn't exist with offset
                // +0200, so we adjust the time as needed, to be valid.
                //
                // Keeping the time actually adds/subtracts (one hour)
                // from the actual represented time. That is why we call updateOffset
                // a second time. In case it wants us to change the offset again
                // _changeInProgress == true case, then we have to adjust, because
                // there is no such time in the given timezone.
                utcOffset: function (input, keepLocalTime) {
                    var offset = this._offset || 0,
                            localAdjust;
                    if (input != null) {
                        if (typeof input === 'string') {
                            input = utcOffsetFromString(input);
                        }
                        if (Math.abs(input) < 16) {
                            input = input * 60;
                        }
                        if (!this._isUTC && keepLocalTime) {
                            localAdjust = this._dateUtcOffset();
                        }
                        this._offset = input;
                        this._isUTC = true;
                        if (localAdjust != null) {
                            this.add(localAdjust, 'm');
                        }
                        if (offset !== input) {
                            if (!keepLocalTime || this._changeInProgress) {
                                addOrSubtractDurationFromMoment(this,
                                        moment.duration(input - offset, 'm'), 1, false);
                            } else if (!this._changeInProgress) {
                                this._changeInProgress = true;
                                moment.updateOffset(this, true);
                                this._changeInProgress = null;
                            }
                        }

                        return this;
                    } else {
                        return this._isUTC ? offset : this._dateUtcOffset();
                    }
                },
                isLocal: function () {
                    return !this._isUTC;
                },
                isUtcOffset: function () {
                    return this._isUTC;
                },
                isUtc: function () {
                    return this._isUTC && this._offset === 0;
                },
                zoneAbbr: function () {
                    return this._isUTC ? 'UTC' : '';
                },
                zoneName: function () {
                    return this._isUTC ? 'Coordinated Universal Time' : '';
                },
                parseZone: function () {
                    if (this._tzm) {
                        this.utcOffset(this._tzm);
                    } else if (typeof this._i === 'string') {
                        this.utcOffset(utcOffsetFromString(this._i));
                    }
                    return this;
                },
                hasAlignedHourOffset: function (input) {
                    if (!input) {
                        input = 0;
                    }
                    else {
                        input = moment(input).utcOffset();
                    }

                    return (this.utcOffset() - input) % 60 === 0;
                },
                daysInMonth: function () {
                    return daysInMonth(this.year(), this.month());
                },
                dayOfYear: function (input) {
                    var dayOfYear = round((moment(this).startOf('day') - moment(this).startOf('year')) / 864e5) + 1;
                    return input == null ? dayOfYear : this.add((input - dayOfYear), 'd');
                },
                quarter: function (input) {
                    return input == null ? Math.ceil((this.month() + 1) / 3) : this.month((input - 1) * 3 + this.month() % 3);
                },
                weekYear: function (input) {
                    var year = weekOfYear(this, this.localeData()._week.dow, this.localeData()._week.doy).year;
                    return input == null ? year : this.add((input - year), 'y');
                },
                isoWeekYear: function (input) {
                    var year = weekOfYear(this, 1, 4).year;
                    return input == null ? year : this.add((input - year), 'y');
                },
                week: function (input) {
                    var week = this.localeData().week(this);
                    return input == null ? week : this.add((input - week) * 7, 'd');
                },
                isoWeek: function (input) {
                    var week = weekOfYear(this, 1, 4).week;
                    return input == null ? week : this.add((input - week) * 7, 'd');
                },
                weekday: function (input) {
                    var weekday = (this.day() + 7 - this.localeData()._week.dow) % 7;
                    return input == null ? weekday : this.add(input - weekday, 'd');
                },
                isoWeekday: function (input) {
                    // behaves the same as moment#day except
                    // as a getter, returns 7 instead of 0 (1-7 range instead of 0-6)
                    // as a setter, sunday should belong to the previous week.
                    return input == null ? this.day() || 7 : this.day(this.day() % 7 ? input : input - 7);
                },
                isoWeeksInYear: function () {
                    return weeksInYear(this.year(), 1, 4);
                },
                weeksInYear: function () {
                    var weekInfo = this.localeData()._week;
                    return weeksInYear(this.year(), weekInfo.dow, weekInfo.doy);
                },
                get: function (units) {
                    units = normalizeUnits(units);
                    return this[units]();
                },
                set: function (units, value) {
                    var unit;
                    if (typeof units === 'object') {
                        for (unit in units) {
                            this.set(unit, units[unit]);
                        }
                    }
                    else {
                        units = normalizeUnits(units);
                        if (typeof this[units] === 'function') {
                            this[units](value);
                        }
                    }
                    return this;
                },
                // If passed a locale key, it will set the locale for this
                // instance.  Otherwise, it will return the locale configuration
                // variables for this instance.
                locale: function (key) {
                    var newLocaleData;

                    if (key === undefined) {
                        return this._locale._abbr;
                    } else {
                        newLocaleData = moment.localeData(key);
                        if (newLocaleData != null) {
                            this._locale = newLocaleData;
                        }
                        return this;
                    }
                },
                lang: deprecate(
                        'moment().lang() is deprecated. Instead, use moment().localeData() to get the language configuration. Use moment().locale() to change languages.',
                        function (key) {
                            if (key === undefined) {
                                return this.localeData();
                            } else {
                                return this.locale(key);
                            }
                        }
                ),
                localeData: function () {
                    return this._locale;
                },
                _dateUtcOffset: function () {
                    // On Firefox.24 Date#getTimezoneOffset returns a floating point.
                    // https://github.com/moment/moment/pull/1871
                    return -Math.round(this._d.getTimezoneOffset() / 15) * 15;
                }

            });

            function rawMonthSetter(mom, value) {
                var dayOfMonth;

                // TODO: Move this out of here!
                if (typeof value === 'string') {
                    value = mom.localeData().monthsParse(value);
                    // TODO: Another silent failure?
                    if (typeof value !== 'number') {
                        return mom;
                    }
                }

                dayOfMonth = Math.min(mom.date(),
                        daysInMonth(mom.year(), value));
                mom._d['set' + (mom._isUTC ? 'UTC' : '') + 'Month'](value, dayOfMonth);
                return mom;
            }

            function rawGetter(mom, unit) {
                return mom._d['get' + (mom._isUTC ? 'UTC' : '') + unit]();
            }

            function rawSetter(mom, unit, value) {
                if (unit === 'Month') {
                    return rawMonthSetter(mom, value);
                } else {
                    return mom._d['set' + (mom._isUTC ? 'UTC' : '') + unit](value);
                }
            }

            function makeAccessor(unit, keepTime) {
                return function (value) {
                    if (value != null) {
                        rawSetter(this, unit, value);
                        moment.updateOffset(this, keepTime);
                        return this;
                    } else {
                        return rawGetter(this, unit);
                    }
                };
            }

            moment.fn.millisecond = moment.fn.milliseconds = makeAccessor('Milliseconds', false);
            moment.fn.second = moment.fn.seconds = makeAccessor('Seconds', false);
            moment.fn.minute = moment.fn.minutes = makeAccessor('Minutes', false);
            // Setting the hour should keep the time, because the user explicitly
            // specified which hour he wants. So trying to maintain the same hour (in
            // a new timezone) makes sense. Adding/subtracting hours does not follow
            // this rule.
            moment.fn.hour = moment.fn.hours = makeAccessor('Hours', true);
            // moment.fn.month is defined separately
            moment.fn.date = makeAccessor('Date', true);
            moment.fn.dates = deprecate('dates accessor is deprecated. Use date instead.', makeAccessor('Date', true));
            moment.fn.year = makeAccessor('FullYear', true);
            moment.fn.years = deprecate('years accessor is deprecated. Use year instead.', makeAccessor('FullYear', true));

            // add plural methods
            moment.fn.days = moment.fn.day;
            moment.fn.months = moment.fn.month;
            moment.fn.weeks = moment.fn.week;
            moment.fn.isoWeeks = moment.fn.isoWeek;
            moment.fn.quarters = moment.fn.quarter;

            // add aliased format methods
            moment.fn.toJSON = moment.fn.toISOString;

            // alias isUtc for dev-friendliness
            moment.fn.isUTC = moment.fn.isUtc;

            /************************************
             Duration Prototype
             ************************************/


            function daysToYears(days) {
                // 400 years have 146097 days (taking into account leap year rules)
                return days * 400 / 146097;
            }

            function yearsToDays(years) {
                // years * 365 + absRound(years / 4) -
                //     absRound(years / 100) + absRound(years / 400);
                return years * 146097 / 400;
            }

            extend(moment.duration.fn = Duration.prototype, {
                _bubble: function () {
                    var milliseconds = this._milliseconds,
                            days = this._days,
                            months = this._months,
                            data = this._data,
                            seconds, minutes, hours, years = 0;

                    // The following code bubbles up values, see the tests for
                    // examples of what that means.
                    data.milliseconds = milliseconds % 1000;

                    seconds = absRound(milliseconds / 1000);
                    data.seconds = seconds % 60;

                    minutes = absRound(seconds / 60);
                    data.minutes = minutes % 60;

                    hours = absRound(minutes / 60);
                    data.hours = hours % 24;

                    days += absRound(hours / 24);

                    // Accurately convert days to years, assume start from year 0.
                    years = absRound(daysToYears(days));
                    days -= absRound(yearsToDays(years));

                    // 30 days to a month
                    // TODO (iskren): Use anchor date (like 1st Jan) to compute this.
                    months += absRound(days / 30);
                    days %= 30;

                    // 12 months -> 1 year
                    years += absRound(months / 12);
                    months %= 12;

                    data.days = days;
                    data.months = months;
                    data.years = years;
                },
                abs: function () {
                    this._milliseconds = Math.abs(this._milliseconds);
                    this._days = Math.abs(this._days);
                    this._months = Math.abs(this._months);

                    this._data.milliseconds = Math.abs(this._data.milliseconds);
                    this._data.seconds = Math.abs(this._data.seconds);
                    this._data.minutes = Math.abs(this._data.minutes);
                    this._data.hours = Math.abs(this._data.hours);
                    this._data.months = Math.abs(this._data.months);
                    this._data.years = Math.abs(this._data.years);

                    return this;
                },
                weeks: function () {
                    return absRound(this.days() / 7);
                },
                valueOf: function () {
                    return this._milliseconds +
                            this._days * 864e5 +
                            (this._months % 12) * 2592e6 +
                            toInt(this._months / 12) * 31536e6;
                },
                humanize: function (withSuffix) {
                    var output = relativeTime(this, !withSuffix, this.localeData());

                    if (withSuffix) {
                        output = this.localeData().pastFuture(+this, output);
                    }

                    return this.localeData().postformat(output);
                },
                add: function (input, val) {
                    // supports only 2.0-style add(1, 's') or add(moment)
                    var dur = moment.duration(input, val);

                    this._milliseconds += dur._milliseconds;
                    this._days += dur._days;
                    this._months += dur._months;

                    this._bubble();

                    return this;
                },
                subtract: function (input, val) {
                    var dur = moment.duration(input, val);

                    this._milliseconds -= dur._milliseconds;
                    this._days -= dur._days;
                    this._months -= dur._months;

                    this._bubble();

                    return this;
                },
                get: function (units) {
                    units = normalizeUnits(units);
                    return this[units.toLowerCase() + 's']();
                },
                as: function (units) {
                    var days, months;
                    units = normalizeUnits(units);

                    if (units === 'month' || units === 'year') {
                        days = this._days + this._milliseconds / 864e5;
                        months = this._months + daysToYears(days) * 12;
                        return units === 'month' ? months : months / 12;
                    } else {
                        // handle milliseconds separately because of floating point math errors (issue #1867)
                        days = this._days + Math.round(yearsToDays(this._months / 12));
                        switch (units) {
                            case 'week':
                                return days / 7 + this._milliseconds / 6048e5;
                            case 'day':
                                return days + this._milliseconds / 864e5;
                            case 'hour':
                                return days * 24 + this._milliseconds / 36e5;
                            case 'minute':
                                return days * 24 * 60 + this._milliseconds / 6e4;
                            case 'second':
                                return days * 24 * 60 * 60 + this._milliseconds / 1000;
                                // Math.floor prevents floating point math errors here
                            case 'millisecond':
                                return Math.floor(days * 24 * 60 * 60 * 1000) + this._milliseconds;
                            default:
                                throw new Error('Unknown unit ' + units);
                        }
                    }
                },
                lang: moment.fn.lang,
                locale: moment.fn.locale,
                toIsoString: deprecate(
                        'toIsoString() is deprecated. Please use toISOString() instead ' +
                        '(notice the capitals)',
                        function () {
                            return this.toISOString();
                        }
                ),
                toISOString: function () {
                    // inspired by https://github.com/dordille/moment-isoduration/blob/master/moment.isoduration.js
                    var years = Math.abs(this.years()),
                            months = Math.abs(this.months()),
                            days = Math.abs(this.days()),
                            hours = Math.abs(this.hours()),
                            minutes = Math.abs(this.minutes()),
                            seconds = Math.abs(this.seconds() + this.milliseconds() / 1000);

                    if (!this.asSeconds()) {
                        // this is the same as C#'s (Noda) and python (isodate)...
                        // but not other JS (goog.date)
                        return 'P0D';
                    }

                    return (this.asSeconds() < 0 ? '-' : '') +
                            'P' +
                            (years ? years + 'Y' : '') +
                            (months ? months + 'M' : '') +
                            (days ? days + 'D' : '') +
                            ((hours || minutes || seconds) ? 'T' : '') +
                            (hours ? hours + 'H' : '') +
                            (minutes ? minutes + 'M' : '') +
                            (seconds ? seconds + 'S' : '');
                },
                localeData: function () {
                    return this._locale;
                },
                toJSON: function () {
                    return this.toISOString();
                }
            });

            moment.duration.fn.toString = moment.duration.fn.toISOString;

            function makeDurationGetter(name) {
                moment.duration.fn[name] = function () {
                    return this._data[name];
                };
            }

            for (i in unitMillisecondFactors) {
                if (hasOwnProp(unitMillisecondFactors, i)) {
                    makeDurationGetter(i.toLowerCase());
                }
            }

            moment.duration.fn.asMilliseconds = function () {
                return this.as('ms');
            };
            moment.duration.fn.asSeconds = function () {
                return this.as('s');
            };
            moment.duration.fn.asMinutes = function () {
                return this.as('m');
            };
            moment.duration.fn.asHours = function () {
                return this.as('h');
            };
            moment.duration.fn.asDays = function () {
                return this.as('d');
            };
            moment.duration.fn.asWeeks = function () {
                return this.as('weeks');
            };
            moment.duration.fn.asMonths = function () {
                return this.as('M');
            };
            moment.duration.fn.asYears = function () {
                return this.as('y');
            };

            /************************************
             Default Locale
             ************************************/


            // Set default locale, other locale will inherit from English.
            moment.locale('en', {
                ordinalParse: /\d{1,2}(th|st|nd|rd)/,
                ordinal: function (number) {
                    var b = number % 10,
                            output = (toInt(number % 100 / 10) === 1) ? 'th' :
                            (b === 1) ? 'st' :
                            (b === 2) ? 'nd' :
                            (b === 3) ? 'rd' : 'th';
                    return number + output;
                }
            });

            /* EMBED_LOCALES */

            /************************************
             Exposing Moment
             ************************************/

            globalScope.moment = moment;
        }).call(Kalendae);
        if (typeof moment !== 'undefined') {
            Kalendae.moment = moment;
        }

        if (!Kalendae.moment) {
            if (window.moment) {
                Kalendae.moment = window.moment;
            } else {
                throw "Kalendae requires moment.js. You must use kalendae.standalone.js if moment is not available on the page.";
            }
        }

        moment = Kalendae.moment;

//function to get the total number of days since the epoch.
        moment.fn.yearDay = function (input) {
            var yearday = Math.floor(this._d / 86400000);
            return (typeof input === 'undefined') ? yearday :
                    this.add({d: input - yearday});
        };
        if (typeof jQuery !== 'undefined' && (typeof document.addEventListener === 'function' || util.isIE8())) {
            jQuery.fn.kalendae = function (options) {
                this.each(function (i, e) {
                    if (e.tagName === 'INPUT') {
                        //if element is an input, bind a popup calendar to the input.
                        jQuery(e).data('kalendae', new Kalendae.Input(e, options));
                    } else {
                        //otherwise, insert a flat calendar into the element.
                        jQuery(e).data('kalendae', new Kalendae(jQuery.extend({}, {attachTo: e}, options)));
                    }
                });
                return this;
            };
        }

        return Kalendae;
    }));

})();

// Awesomplete - Lea Verou - MIT license
!function(){function t(t){var e=Array.isArray(t)?{label:t[0],value:t[1]}:"object"==typeof t&&"label"in t&&"value"in t?t:{label:t,value:t};this.label=e.label||e.value,this.value=e.value}function e(t,e,i){for(var n in e){var s=e[n],r=t.input.getAttribute("data-"+n.toLowerCase());"number"==typeof s?t[n]=parseInt(r):!1===s?t[n]=null!==r:s instanceof Function?t[n]=null:t[n]=r,t[n]||0===t[n]||(t[n]=n in i?i[n]:s)}}function i(t,e){return"string"==typeof t?(e||document).querySelector(t):t||null}function n(t,e){return o.call((e||document).querySelectorAll(t))}function s(){n("input.awesomplete").forEach(function(t){new r(t)})}var r=function(t,n){var s=this;Awesomplete.count=(Awesomplete.count||0)+1,this.count=Awesomplete.count,this.isOpened=!1,this.input=i(t),this.input.setAttribute("autocomplete","off"),this.input.setAttribute("aria-owns","awesomplete_list_"+this.count),this.input.setAttribute("role","combobox"),n=n||{},e(this,{minChars:2,maxItems:10,autoFirst:!1,data:r.DATA,filter:r.FILTER_CONTAINS,sort:!1!==n.sort&&r.SORT_BYLENGTH,item:r.ITEM,replace:r.REPLACE},n),this.index=-1,this.container=i.create("div",{className:"awesomplete",around:t}),this.ul=i.create("ul",{hidden:"hidden",role:"listbox",id:"awesomplete_list_"+this.count,inside:this.container}),this.status=i.create("span",{className:"visually-hidden",role:"status","aria-live":"assertive","aria-atomic":!0,inside:this.container,textContent:0!=this.minChars?"Type "+this.minChars+" or more characters for results.":"Begin typing for results."}),this._events={input:{input:this.evaluate.bind(this),blur:this.close.bind(this,{reason:"blur"}),keydown:function(t){var e=t.keyCode;s.opened&&(13===e&&s.selected?(t.preventDefault(),s.select()):27===e?s.close({reason:"esc"}):38!==e&&40!==e||(t.preventDefault(),s[38===e?"previous":"next"]()))}},form:{submit:this.close.bind(this,{reason:"submit"})},ul:{mousedown:function(t){t.preventDefault()},click:function(t){var e=t.target;if(e!==this){for(;e&&!/li/i.test(e.nodeName);)e=e.parentNode;e&&0===t.button&&(t.preventDefault(),s.select(e,t.target))}}}},i.bind(this.input,this._events.input),i.bind(this.input.form,this._events.form),i.bind(this.ul,this._events.ul),this.input.hasAttribute("list")?(this.list="#"+this.input.getAttribute("list"),this.input.removeAttribute("list")):this.list=this.input.getAttribute("data-list")||n.list||[],r.all.push(this)};r.prototype={set list(t){if(Array.isArray(t))this._list=t;else if("string"==typeof t&&t.indexOf(",")>-1)this._list=t.split(/\s*,\s*/);else if((t=i(t))&&t.children){var e=[];o.apply(t.children).forEach(function(t){if(!t.disabled){var i=t.textContent.trim(),n=t.value||i,s=t.label||i;""!==n&&e.push({label:s,value:n})}}),this._list=e}document.activeElement===this.input&&this.evaluate()},get selected(){return this.index>-1},get opened(){return this.isOpened},close:function(t){this.opened&&(this.ul.setAttribute("hidden",""),this.isOpened=!1,this.index=-1,this.status.setAttribute("hidden",""),i.fire(this.input,"awesomplete-close",t||{}))},open:function(){this.ul.removeAttribute("hidden"),this.isOpened=!0,this.status.removeAttribute("hidden"),this.autoFirst&&-1===this.index&&this.goto(0),i.fire(this.input,"awesomplete-open")},destroy:function(){i.unbind(this.input,this._events.input),i.unbind(this.input.form,this._events.form);var t=this.container.parentNode;t.insertBefore(this.input,this.container),t.removeChild(this.container),this.input.removeAttribute("autocomplete"),this.input.removeAttribute("aria-autocomplete");var e=r.all.indexOf(this);-1!==e&&r.all.splice(e,1)},next:function(){var t=this.ul.children.length;this.goto(this.index<t-1?this.index+1:t?0:-1)},previous:function(){var t=this.ul.children.length,e=this.index-1;this.goto(this.selected&&-1!==e?e:t-1)},goto:function(t){var e=this.ul.children;this.selected&&e[this.index].setAttribute("aria-selected","false"),this.index=t,t>-1&&e.length>0&&(e[t].setAttribute("aria-selected","true"),this.status.textContent=e[t].textContent+", list item "+(t+1)+" of "+e.length,this.input.setAttribute("aria-activedescendant",this.ul.id+"_item_"+this.index),this.ul.scrollTop=e[t].offsetTop-this.ul.clientHeight+e[t].clientHeight,i.fire(this.input,"awesomplete-highlight",{text:this.suggestions[this.index]}))},select:function(t,e){if(t?this.index=i.siblingIndex(t):t=this.ul.children[this.index],t){var n=this.suggestions[this.index];i.fire(this.input,"awesomplete-select",{text:n,origin:e||t})&&(this.replace(n),this.close({reason:"select"}),i.fire(this.input,"awesomplete-selectcomplete",{text:n}))}},evaluate:function(){var e=this,i=this.input.value;i.length>=this.minChars&&this._list&&this._list.length>0?(this.index=-1,this.ul.innerHTML="",this.suggestions=this._list.map(function(n){return new t(e.data(n,i))}).filter(function(t){return e.filter(t,i)}),!1!==this.sort&&(this.suggestions=this.suggestions.sort(this.sort)),this.suggestions=this.suggestions.slice(0,this.maxItems),this.suggestions.forEach(function(t,n){e.ul.appendChild(e.item(t,i,n))}),0===this.ul.children.length?(this.status.textContent="No results found",this.close({reason:"nomatches"})):(this.open(),this.status.textContent=this.ul.children.length+" results found")):(this.close({reason:"nomatches"}),this.status.textContent="No results found")}},r.all=[],r.FILTER_CONTAINS=function(t,e){return RegExp(i.regExpEscape(e.trim()),"i").test(t)},r.FILTER_STARTSWITH=function(t,e){return RegExp("^"+i.regExpEscape(e.trim()),"i").test(t)},r.SORT_BYLENGTH=function(t,e){return t.length!==e.length?t.length-e.length:t<e?-1:1},r.ITEM=function(t,e,n){return i.create("li",{innerHTML:""===e.trim()?t:t.replace(RegExp(i.regExpEscape(e.trim()),"gi"),"<mark>$&</mark>"),"aria-selected":"false",id:"awesomplete_list_"+this.count+"_item_"+n})},r.REPLACE=function(t){this.input.value=t.value},r.DATA=function(t){return t},Object.defineProperty(t.prototype=Object.create(String.prototype),"length",{get:function(){return this.label.length}}),t.prototype.toString=t.prototype.valueOf=function(){return""+this.label};var o=Array.prototype.slice;i.create=function(t,e){var n=document.createElement(t);for(var s in e){var r=e[s];if("inside"===s)i(r).appendChild(n);else if("around"===s){var o=i(r);o.parentNode.insertBefore(n,o),n.appendChild(o)}else s in n?n[s]=r:n.setAttribute(s,r)}return n},i.bind=function(t,e){if(t)for(var i in e){var n=e[i];i.split(/\s+/).forEach(function(e){t.addEventListener(e,n)})}},i.unbind=function(t,e){if(t)for(var i in e){var n=e[i];i.split(/\s+/).forEach(function(e){t.removeEventListener(e,n)})}},i.fire=function(t,e,i){var n=document.createEvent("HTMLEvents");n.initEvent(e,!0,!0);for(var s in i)n[s]=i[s];return t.dispatchEvent(n)},i.regExpEscape=function(t){return t.replace(/[-\\^$*+?.()|[\]{}]/g,"\\$&")},i.siblingIndex=function(t){for(var e=0;t=t.previousElementSibling;e++);return e},"undefined"!=typeof Document&&("loading"!==document.readyState?s():document.addEventListener("DOMContentLoaded",s)),r.$=i,r.$$=n,"undefined"!=typeof self&&(self.Awesomplete=r),"object"==typeof module&&module.exports&&(module.exports=r)}();
//# sourceMappingURL=awesomplete.min.js.map

(function () {
  $.MsgBox = {
  Alert: function (title, msg, callback) {
    GenerateHtml("alert", title, msg);
    btnOk(callback); //alert只是弹出消息，因此没必要用到回调函数callback
    btnNo();
  },
  Judge: function (title, msg, callback) {
	    GenerateHtml("judge", title, msg);
	    btnJug(callback); //alert只是弹出消息，因此没必要用到回调函数callback
	    btnNo();
	  },
    Confirm: function (title, msg, callback) {
      GenerateHtml("confirm", title, msg);
      btnOk(callback);
      btnNo();
    },
    Prompt: function (title, msg, callback) {
        GenerateHtml("prompt", title, msg);
        btnOk(callback);
        btnNo();
      },
     Remind : function (title, msg, callback) {
         GenerateHtml("remind", title, msg);
         btnOk(callback);
         btnNo();
       }
  };
 
  //生成Html
  var GenerateHtml = function (type, title, msg) {
 
    var _html = "";
 
    _html += '<div id="mb_box"></div><div id="mb_con"><span id="mb_tit">' + title + '</span>';
    _html += '<a id="mb_ico"></a><div id="mb_msg">' + msg + '</div><div id="mb_btnbox">';
 
    if (type == "alert") {
      _html += '<input id="mb_btn_ok" type="button" value="确定" />';
    }
    if (type == "judge") {
        _html += '<input id="mb_jug_ok" type="button" value="确定" />';
      }
    if (type == "confirm") {
      _html += '<input id="mb_btn_ok" type="button" value="是" />';
      _html += '<input id="mb_btn_no" type="button" value="否" />';
    }
    if (type == "prompt") {
      _html += '<span style="display:inline-block;width:100%;height:27px;background:#00aeef"></span>';
    }

    if (type == "remind") {
      _html += '<span style="display:inline-block;width:100%;height:27px;background:#fff;margin-top: -1px;"></span>';
    }
    _html += '</div></div>';
 
    //必须先将_html添加到body，再设置Css样式
    $("body").append(_html); GenerateCss();
  };
 
  //生成Css
  var GenerateCss = function () {
 
    $("#mb_box").css({ width: '100%', height: '100%', zIndex: '99999', position: 'fixed',
      filter: 'Alpha(opacity=60)', backgroundColor: 'rgba(10%,20%,30%,0.6)', top: '0', left: '0', opacity: '0.6'
    });
 
    $("#mb_con").css({ zIndex: '999999', width: '355px',height:'189px', position: 'fixed',
      backgroundColor: 'White', borderRadius: '',border:'1px solid #fff'
    });
 
    $("#mb_tit").css({ display: 'block', fontSize: '16px', color: '#444', padding: '2px 15px',
      backgroundColor: '#00aeef', borderRadius: '',
      borderBottom: '', fontWeight: 'normal',
      fontFamily:'microsoft yahei'
    });
 
    $("#mb_msg").css({ height: '60px', lineHeight: '125px',
      fontSize: '13px',textAlign:'center',fontSize:'16px', fontFamily:'microsoft yahei'
    });
    $("#mb_msg p").css({ height: '30px', lineHeight: '30px',width:'350px',textOverflow:'ellipsis',whiteSpace:'nowrap',overflow:'hidden'
      });

 
//  $("#mb_ico").css({ display: 'block', position: 'absolute', right: '10px', top: '9px',
//    border: '1px solid Gray', width: '18px', height: '18px', textAlign: 'center',
//    lineHeight: '16px', cursor: 'pointer', borderRadius: '12px', fontFamily: '微软雅黑'
//  });
 
    $("#mb_btnbox").css({ margin: '75px 0 0', textAlign: 'center' ,background:'#00aeef',paddingTop:'1px'});
    $("#mb_btn_ok,#mb_jug_ok,#mb_btn_no").css({ width: '60px', height: '28px', color: '#000', border: '1px solid #fff',borderRadius:'3px',background:'#00aeef',fontSize:'14px', fontFamily:'microsoft yahei' });
    $("#mb_btn_ok,#mb_jug_ok").css({ backgroundColor: '#00aeef' });
    $("#mb_btn_no").css({ backgroundColor: '#00aeef', marginLeft: '20px' });
 
 
    //右上角关闭按钮hover样式
    $("#mb_ico").hover(function () {
      $(this).css({ backgroundColor: 'Red', color: 'White' });
    }, function () {
      $(this).css({ backgroundColor: '#DDD', color: 'black' });
    });
 
    var _widht = document.documentElement.clientWidth; //屏幕宽
    var _height = document.documentElement.clientHeight; //屏幕高
 
    var boxWidth = $("#mb_con").width();
    var boxHeight = $("#mb_con").height();
 
    //让提示框居中
    $("#mb_con").css({ top: (_height - boxHeight) / 2 + "px", left: (_widht - boxWidth) / 2 + "px" });
  };
 
 
  //确定按钮事件
  var btnOk = function (callback) {
    $("#mb_btn_ok").click(function () {
      $("#mb_box,#mb_con").remove();
      if (typeof (callback) == 'function') {
        callback();
      }
    });
  };
 
  //判断按钮事件
  var btnJug = function (callback) {
    $("#mb_jug_ok").click(function () {
      $("#mb_box,#mb_con").remove();
      if (typeof (callback) == 'function') {
        callback();
      }
    });
  };
  //取消按钮事件
  var btnNo = function () {
    $("#mb_btn_no,#mb_ico").click(function () {
      $("#mb_box,#mb_con").remove();
    });
  }
})();

/*保存省市数据*/
/*进入页面验证本地存储*/
// storage.clear(); // 将localStorage的所有内容清除
// storage.removeItem("a"); // 删除某个键值对
// storage.key(i); // 使用key()方法，向其中出入索引即可获取对应的键
var schedule_provinceANDcity;
if(!window.localStorage){
    alert("浏览器不支持localstorage");
}else{
    // 主逻辑业务
    var storage = window.localStorage;
    var inow = Date.now();
    var schedule_provinceANDcityStr = storage.getItem("schedule_provinceANDcity");
    if(schedule_provinceANDcityStr == undefined){
        // 第一次存
        getprovinceANDcityJSON();
    }else{
        if(eouluGlobal.S_getPageAllConfig()["Schedule"].provinceANDcityRefreshFlag){
            getprovinceANDcityJSON();
        }else{
            var iexpires = JSON.parse(schedule_provinceANDcityStr).expires;
            if(iexpires < inow){
                // 已超期
                getprovinceANDcityJSON();
            }else{
                // 未超期
                schedule_provinceANDcity = JSON.parse(schedule_provinceANDcityStr).data;
            }
        }
    }
}

/*get 省市JSON*/
function getprovinceANDcityJSON(){
    jQuery.getJSON("html/modules/serviced/provinceANDcity.json", undefined, function(data, status, xhr){
        if(status == "success"){
            var iObj = {};
            iObj.expires = inow + 30*24*60*60*1000;
            iObj.data = _.cloneDeep(data);
            console.log("iObj.data");
            console.log(iObj.data);
            schedule_provinceANDcity = _.cloneDeep(data);
            // _.cloneDeep(a)
            var iStr = JSON.stringify(iObj);
            // storage["InventoryAllCustomerInfo"] = iStr;
            storage.setItem("schedule_provinceANDcity", iStr);
        }else{
            $.MsgBox_Unload.Alert("提示","获取中国省市数据失败！");
        }
    });
}

/*查找城市*/
function searchCity(city){
    if(ScheduleState.provinceANDcityStr === null){
        return undefined;
    }
    return _.find(ScheduleState.provinceANDcityStr, function(o) { return o.value == city; });
}

var ScheduleState = new Object();
ScheduleState.addSubmitObj = new Object();
ScheduleState.addSubmitObj.Name = null;
ScheduleState.addSubmitObj.CustomerUnit = null;
ScheduleState.addSubmitObj.ServiceItem = null;
ScheduleState.addSubmitObj.TransportTool = null;
ScheduleState.addSubmitObj.TrainNumber = null;
ScheduleState.addSubmitObj.Hotel = null;
ScheduleState.addSubmitObj.OperateType = null;
ScheduleState.addSubmitObj.Departure = null;
ScheduleState.addSubmitObj.Destination = null;
ScheduleState.addSubmitObj.DepartureDate = null;
ScheduleState.addSubmitObj.DestinationDate = null;
ScheduleState.addSubmitObj.HotelExpense = null;
ScheduleState.addSubmitObj.TrafficExpense = null;
ScheduleState.addSubmitObj.TravelDistance = null;
ScheduleState.addSubmitObj.Date = null;

ScheduleState.updateSubmitObj = new Object();
ScheduleState.updateSubmitObj.ID = null;
ScheduleState.updateSubmitObj.Name = null;
ScheduleState.updateSubmitObj.CustomerUnit = null;
ScheduleState.updateSubmitObj.ServiceItem = null;
ScheduleState.updateSubmitObj.TransportTool = null;
ScheduleState.updateSubmitObj.TrainNumber = null;
ScheduleState.updateSubmitObj.Hotel = null;
ScheduleState.updateSubmitObj.OperateType = null;
ScheduleState.updateSubmitObj.Departure = null;
ScheduleState.updateSubmitObj.Destination = null;
ScheduleState.updateSubmitObj.DepartureDate = null;
ScheduleState.updateSubmitObj.DestinationDate = null;
ScheduleState.updateSubmitObj.HotelExpense = null;
ScheduleState.updateSubmitObj.TrafficExpense = null;
ScheduleState.updateSubmitObj.TravelDistance = null;
ScheduleState.updateSubmitObj.Date = null;

ScheduleState.provinceANDcityStr = null;
ScheduleState.addprovinceANDcityFlag = false;
ScheduleState.updateprovinceANDcityFlag = false;

/*添加修改自动填充 init*/
function addupdateAwesomplete(classify, dom){
    if(ScheduleState.provinceANDcityStr === null){
        var str = [];
        schedule_provinceANDcity.provinces.map(function(v, i, arr){
            v.citys.map(function(vv, ii, array){
                var item = {};
                item.label = v.provinceName + "," + vv.citysName;
                item.value = vv.citysName;
                str.push(item);
            });
        });
        ScheduleState.provinceANDcityStr = _.cloneDeep(str);
    }
    if(classify == "add"){
        if(!ScheduleState.addprovinceANDcityFlag){
            dom.each(function(i, el){
                new Awesomplete(el, {
                    list: ScheduleState.provinceANDcityStr,
                    minChars: 1,
                    maxItems: 20,
                    autoFirst: true
                });
            });
            ScheduleState.addprovinceANDcityFlag = true;
        }
    }else if(classify == "update"){
        if(!ScheduleState.updateprovinceANDcityFlag){
            dom.each(function(i, el){
                new Awesomplete(el, {
                    list: ScheduleState.provinceANDcityStr,
                    minChars: 1,
                    maxItems: 20,
                    autoFirst: true
                });
            });
            ScheduleState.updateprovinceANDcityFlag = true;
        }
    }
}

/* 添加提交 */
$(document).on("click","#add_submit",function(){
    var iThat = $(this);
    for(var k in ScheduleState.addSubmitObj){
        if(k=="OperateType"){
            ScheduleState.addSubmitObj[k] = "Add";
            continue;
        }
        if(k=="Date"){
            ScheduleState.addSubmitObj[k] = currentDate;
            continue;
        }
        if(k=="TravelDistance"){
            continue;
        }
        ScheduleState.addSubmitObj[k] = $("#add_info_"+k).val();
    }
    // 表单验证
    var Reg = new RegExp( '[0-9]{2}:[0-9]{2}');
    for(var kk in ScheduleState.addSubmitObj){
        ScheduleState.addSubmitObj[kk] = globalDataHandle(ScheduleState.addSubmitObj[kk], "").trim();
        if(kk == "CustomerUnit" && ScheduleState.addSubmitObj[kk] == ""){
            $.MsgBox_Unload.Alert("提示","客户单位必填！");
            return false;
        }
        if(kk == "HotelExpense" && (ScheduleState.addSubmitObj[kk] == "" || ScheduleState.addSubmitObj[kk] == "请选择住宿费用")){
            $.MsgBox_Unload.Alert("提示","未选择住宿费用！");
            return false;
        }
        if(kk == "TrafficExpense" && (ScheduleState.addSubmitObj[kk] == "" || ScheduleState.addSubmitObj[kk] == "请选择交通方式")){
            $.MsgBox_Unload.Alert("提示","未选择交通费用！");
            return false;
        }
        if(kk == "Departure" && ScheduleState.addSubmitObj[kk] == ""){
            $.MsgBox_Unload.Alert("提示","未选择出发地！");
            return false;
        }
        if(kk == "Destination" && ScheduleState.addSubmitObj[kk] == ""){
            $.MsgBox_Unload.Alert("提示","未选择目的地！");
            return false;
        }
        if(kk == "DepartureDate" && ScheduleState.addSubmitObj[kk] != "" && !Reg.test(ScheduleState.addSubmitObj[kk])){
            $.MsgBox_Unload.Alert("提示", "出发时间格式错误！");
            return false;
        }
        if(kk == "DestinationDate" && ScheduleState.addSubmitObj[kk] != "" && !Reg.test(ScheduleState.addSubmitObj[kk])){
            $.MsgBox_Unload.Alert("提示", "到达时间格式错误！");
            return false;
        }

    }

    if(searchCity(ScheduleState.addSubmitObj.Departure) == undefined || searchCity(ScheduleState.addSubmitObj.Destination) == undefined){
        $.MsgBox_Unload.Confirm("出发地&目的地异常提示", "无法计算距离，继续提交吗？", function(){
            eouluGlobal.C_btnDisabled(iThat, false);
            ScheduleState.addSubmitObj.TravelDistance = "0.00";
            $.ajax({
                type: 'get',
                url: 'ScheduleOperate',
                data: ScheduleState.addSubmitObj,
                dataType: 'json',
                success: function (data) {
                    if(data==true){
                        $.MsgBox.Alert('提示','添加成功');
                        $('.MailBar_cover_color, .contract_add').hide();
                    }else{
                        $.MsgBox_Unload.Alert("提示", "添加失败！");
                    }
                },
                error : function () {
                    $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
                },
                complete: function(XMLHttpRequest, textStatus){
                    eouluGlobal.C_btnAbled(iThat, false);
                }
            });
        });
    }else{
        alterDistance(ScheduleState.addSubmitObj.Departure, ScheduleState.addSubmitObj.Destination, function(distance){
            eouluGlobal.C_btnDisabled(iThat, false);
            ScheduleState.addSubmitObj.TravelDistance = distance < 20 ? 20.00 : distance;
            $.ajax({
                type: 'get',
                url: 'ScheduleOperate',
                data: ScheduleState.addSubmitObj,
                dataType: 'json',
                success: function (data) {
                    if(data==true){
                        $.MsgBox.Alert('提示','添加成功');
                        $('.MailBar_cover_color, .contract_add').hide();
                    }else{
                        $.MsgBox_Unload.Alert("提示", "添加失败！");
                    }
                },
                error : function () {
                    $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
                },
                complete: function(XMLHttpRequest, textStatus){
                    eouluGlobal.C_btnAbled(iThat, false);
                }
            });
        });
    }
});

// function saveExistCity(){
//     var map = new BMap.Map("allmap");

//     var point = new BMap.Point(116.331398,39.897445);

//     map.centerAndZoom(point,12);
//     // 创建地址解析器实例
//     var myGeo = new BMap.Geocoder();
//     // 将地址解析结果显示在地图上,并调整地图视野
//     myGeo.getPoint($scope.address.to_addr, function(point){
//     if (point) {
//     lon = point.lng;
//     lat = point.lat;

//     alert(lon+','+lat);

//     //在地图上定位到该点

//     map.centerAndZoom(point, 16);

//     map.addOverlay(new BMap.Marker(point));

//     }else{
//     alert("您选择地址没有解析到结果!");
//     }
//     }, "苏州市");
// }

// function saveExistCity(city){
//     var cityURI = encodeURIComponent(city);
//     // $.ajax({
//     //     type : 'get',
//     //     url : 'http://api.map.baidu.com/geocoder?address='+cityURI+'&output=json&key=ZHnpqUp55qaTvkE27KGjUBYLcUfsaRTV&city='+cityURI,
//     //     dataType : 'json',
//     //     success : function(data){
//     //         console.log(data);
//     //     }
//     // });
    
//     $.ajax({
//         async: false,
//         type: "GET",
//         dataType: 'jsonp',
//         jsonp: 'callback',
//         jsonpCallback: 'callbackfunction',
//         url: 'http://api.map.baidu.com/geocoder?address='+cityURI+'&output=json&key=ZHnpqUp55qaTvkE27KGjUBYLcUfsaRTV&city='+cityURI,
//         data: "",
//         timeout: 3000,
//         contentType: "application/json;utf-8",
//         success: function(msg) {
//           console.log(msg.result);
//           alert(msg.result);
//         }
//       });

// }

/* 修改信息界面打开 */
$(document).on("click",".xiugai",function(){
    addupdateAwesomplete("update", $("#update_info_Departure, #update_info_Destination"));
	$(this).css("color","red");
	var tr=$(this).parent();
    $("[id^='update_info_']").each(function(){
        if($(this).is("#update_info_TrafficExpense")) return true;
        var subClassName = $(this).attr("id").replace("update_info_", "td_");
        var newValue = globalDataHandle(tr.find("."+subClassName).text(), "");
        $(this).val(newValue);
    });
	$("#update_info_TransportTool").trigger("change");
    $("#update_info_TrafficExpense").val(globalDataHandle(tr.find(".td_TrafficExpense").text(), ""));

    var oldDay = tr.find(".DateTime").text();
    var iThat = $("#update_submit");
    if(oldDay > moment().subtract(1, "months").format("YYYY-MM-DD")){
        eouluGlobal.C_btnAbled(iThat, false);
        iThat.children("span.glyphicon").css("cursor","pointer");
    }else{
        eouluGlobal.C_btnDisabled(iThat, false);
        iThat.children("span.glyphicon").css("cursor","not-allowed");
    }

    ScheduleState.updateSubmitObj.ID = $(this).attr("value");
    $('.MailBar_cover_color, .contract_update').slideDown(200);
});

// 计算未修改出发地和目的地
function alterDistance(address1, address2, fn){
    var url1 = 'https://api.map.baidu.com/geocoder/v2/?ak=ZHnpqUp55qaTvkE27KGjUBYLcUfsaRTV&output=json&address=' + encodeURIComponent(address1);
    var url2 = 'https://api.map.baidu.com/geocoder/v2/?ak=ZHnpqUp55qaTvkE27KGjUBYLcUfsaRTV&output=json&address=' + encodeURIComponent(address2);
    //根据地点名称获取经纬度信息
    $.ajax({
        type: "POST",
        url: url1,
        dataType: "JSONP",
        success: function(data) {
            if (parseInt(data.status) == 0) {
                var lng1 = data.result.location.lng;
                var lat1 = data.result.location.lat;
                console.log("lng1:"+lng1);
                console.log("lat1:"+lat1);
                $.ajax({
                    type: "POST",
                    url: url2,
                    dataType: "JSONP",
                    success: function(data) {
                        if (parseInt(data.status) == 0) {
                            var lng2 = data.result.location.lng;
                            var lat2 = data.result.location.lat;
                            console.log("lng2:"+lng2);
                            console.log("lat2:"+lat2);
                            var map = new BMap.Map();
                            var pointA = new BMap.Point(lng1, lat1);
                            var pointB = new BMap.Point(lng2, lat2);
                            var distance = (map.getDistance(pointA, pointB) / 1000).toFixed(2);
                            console.warn(distance);
                            fn && fn(distance);
                        }else{
                            alert("第三方库计算距离失败！");
                        }
                    }
                });
            }
        }
    });
}

/*  提交修改后的信息  ************************************/
$('#update_submit').click(function () {
    var iThat = $(this);
    for(var k in ScheduleState.updateSubmitObj){
        if(k=="OperateType"){
            ScheduleState.updateSubmitObj[k] = "Modify";
            continue;
        }
        if(k=="Date"){
            ScheduleState.updateSubmitObj[k] = currentDate;
            continue;
        }
        if(k=="TravelDistance" || k=="ID"){
            continue;
        }
        ScheduleState.updateSubmitObj[k] = $("#update_info_"+k).val();
    }
    // 表单验证
    var Reg = new RegExp( '[0-9]{2}:[0-9]{2}');
    for(var kk in ScheduleState.updateSubmitObj){
        ScheduleState.updateSubmitObj[kk] = globalDataHandle(ScheduleState.updateSubmitObj[kk], "").trim();
        if(kk == "CustomerUnit" && ScheduleState.updateSubmitObj[kk] == ""){
            $.MsgBox_Unload.Alert("提示","客户单位必填！");
            return false;
        }
        if(kk == "HotelExpense" && (ScheduleState.updateSubmitObj[kk] == "" || ScheduleState.updateSubmitObj[kk] == "请选择住宿费用")){
            $.MsgBox_Unload.Alert("提示","未选择住宿费用！");
            return false;
        }
        if(kk == "TrafficExpense" && (ScheduleState.updateSubmitObj[kk] == "" || ScheduleState.updateSubmitObj[kk] == "请选择交通方式")){
            $.MsgBox_Unload.Alert("提示","未选择交通费用！");
            return false;
        }
        if(kk == "Departure" && ScheduleState.updateSubmitObj[kk] == ""){
            $.MsgBox_Unload.Alert("提示","未选择出发地！");
            return false;
        }
        if(kk == "Destination" && ScheduleState.updateSubmitObj[kk] == ""){
            $.MsgBox_Unload.Alert("提示","未选择目的地！");
            return false;
        }
        if(kk == "DepartureDate" && ScheduleState.updateSubmitObj[kk] != "" && !Reg.test(ScheduleState.updateSubmitObj[kk])){
            $.MsgBox_Unload.Alert("提示", "出发时间格式错误！");
            return false;
        }
        if(kk == "DestinationDate" && ScheduleState.updateSubmitObj[kk] != "" && !Reg.test(ScheduleState.updateSubmitObj[kk])){
            $.MsgBox_Unload.Alert("提示", "到达时间格式错误！");
            return false;
        }

    }

    if(searchCity(ScheduleState.updateSubmitObj.Departure) == undefined || searchCity(ScheduleState.updateSubmitObj.Destination) == undefined){
        $.MsgBox_Unload.Confirm("出发地&目的地异常提示", "无法计算距离，继续提交吗？", function(){
            eouluGlobal.C_btnDisabled(iThat, false);
            ScheduleState.updateSubmitObj.TravelDistance = "0.00";
            $.ajax({
                type: 'get',
                url: 'ScheduleOperate',
                data: ScheduleState.updateSubmitObj,
                dataType: 'json',
                success: function (data) {
                    if(data==true){
                        $.MsgBox.Alert('提示','修改成功');
                        $('.MailBar_cover_color, .contract_update').hide();
                    }else{
                        $.MsgBox_Unload.Alert("提示", "修改失败！");
                    }
                },
                error : function () {
                    $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
                },
                complete: function(XMLHttpRequest, textStatus){
                    eouluGlobal.C_btnAbled(iThat, false);
                }
            });
        });
    }else{
        alterDistance(ScheduleState.updateSubmitObj.Departure, ScheduleState.updateSubmitObj.Destination, function(distance){
            eouluGlobal.C_btnDisabled(iThat, false);
            ScheduleState.updateSubmitObj.TravelDistance = distance < 20 ? 20.00 : distance;
            $.ajax({
                type: 'get',
                url: 'ScheduleOperate',
                data: ScheduleState.updateSubmitObj,
                dataType: 'json',
                success: function (data) {
                    if(data==true){
                        $.MsgBox.Alert('提示','修改成功');
                        $('.MailBar_cover_color, .contract_update').hide();
                    }else{
                        $.MsgBox_Unload.Alert("提示", "修改失败！");
                    }
                },
                error : function () {
                    $.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
                },
                complete: function(XMLHttpRequest, textStatus){
                    eouluGlobal.C_btnAbled(iThat, false);
                }
            });
        });
    }
});

//点击添加
function AddContract(){
    addupdateAwesomplete("add", $("#add_info_Departure, #add_info_Destination"));
	/*init*/
    $("[id^='add_info_']").each(function(){
        if($(this).is("#add_info_Name") || $(this).is("#add_info_TrafficExpense")) return true;
        if($(this).is("#add_info_TransportTool")){
            $(this).val("请选择");
            return true;
        }
        if($(this).is("#add_info_HotelExpense")){
            $(this).val("请选择住宿费用");
            return true;
        }
        $(this).val("");
    });
    $("#add_info_TransportTool").trigger("change");
    $('.MailBar_cover_color, .contract_add').slideDown(200);
}

//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function () {
    window.location.reload();
});

//点击关闭
$('#add_cancel').click(function() {
    $('.MailBar_cover_color, .contract_add').slideUp(200);
});

$('#update_cancel').click(function () {
    $('.MailBar_cover_color, .contract_update').slideUp(200);
});

//
function INSearch() {
	var classify = $(".classify").find("option:selected").text();
	if(classify == '员工姓名'){
		var parameter = $("select.staffName").find("option:selected").attr("value");
		parameter = Trim(parameter);
	}else{
		var parameter = $("#searchContent1").val();
		parameter = Trim(parameter);
	}
	if(parameter == ''){
		 $.MsgBox_Unload.Alert("提示", "搜索内容不为空！");
		return;
	}
    window.location.href = "ScheduleRoute?classify="+classify+"&parameter="+parameter;
}
function Cancel() {
    $('#search').val('cancel');
    $('input[name="Name"]').val('');
    $("#top_text_from").attr("action", "Schedule?Date="+currentDate);
    $('#top_text_from').submit();
}
$(".backToday").click(function(){
	window.location.href="Schedule";
});

//点击删除和取消删除
$(".deleteInfo").click(function(){
	var txt = $(".deleteInfo").val();
	if(txt == '删除'){
		$(".deleteInfo").val('').val("取消删除");
		$(".deleteInfo").css('width','70px');
		//序号变成删除列 数字变成X图标
		$("#table1 .tbody_tr td.xiugai").removeClass('xiugai').addClass('delete');
		$("#table1  td.xiugai1").eq(0).text('删除');
		$("#table1 .tbody_tr td.delete").text('X');
		//点击每列的X

		$(".tbody_tr td.delete").click(function(){
			var tr = $(this).parent();
			var ID = tr.find("td.delete").attr('value');
			var Name = tr.find("td.Name").text();
			//时效性判断
				var DateTime = tr.find(".DateTime").text();
				var   d=new   Date(Date.parse(DateTime.replace(/-/g,"/")));
				var   curDate=new   Date();
			    var da=d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate(); 
			    var cur=curDate.getFullYear() + '-' + (curDate.getMonth() + 1) + '-' + curDate.getDate(); 
				if(d >curDate || da == cur){
					/*alert("未来时间")*/
							$.ajax({
								type:'get',
								url:'ScheduleOperate',
								data:{
									OperateType:'Delete',
									ID:ID,
									Name:Name
								},
								success:function(data){
									console.log(data);
									if(data){
					                	 $.MsgBox_Unload.Alert("提示", "删除成功！");
					                	tr.remove();
					                }else{
					                	$.MsgBox_Unload.Alert("提示", "删除失败！");
					                }
								},
								error:function(){
									$.MsgBox_Unload.Alert("提示", "服务器繁忙，稍后重试！");
								}
							});
					}else{
						$.MsgBox_Unload.Alert("提示", "不能删除过去的信息！");
					}
		});
	}else{
		window.location.reload();
	}
});
function SelectContent(){
    var engineerOb = $("span.engineer").html().split(',');
	var len = engineerOb.length;
	var str1 = '<select class="staffName" >'+
				'<option value="所有人">所有人</option>';
	var str2 = '';
	var str3 = '';
	var str4 = '</select>';
	for(var i = 1;i<len-1;i++){
			str2 += '<option value="'+engineerOb[i].substring(12,engineerOb[i].length-1)+'">'+engineerOb[i].substring(12,engineerOb[i].length-1)+'</option>';
	}
	str3 += '<option value="'+engineerOb[len-1].substring(12,engineerOb[len-1].length-2)+'">'+engineerOb[len-1].substring(12,engineerOb[len-1].length-2)+'</option>';
	Str = str1+str2+str3+str4;
	$(".select1").html('').html(Str);
}
$(function(){
	var Href = window.location.href;
	var Index = Href.indexOf('?');
	var Index2 = Href.indexOf('Schedule?Date');
	if(Index == -1 || Index2 != -1){
		SelectContent();
	}else{
		var Pa= Href.substring(Index+1);
		var Pa1 =decodeURI(Pa);
		var Index3 = Pa1.indexOf('&');
		var Pa2 = Pa1.substring(9,Index3);
		var Pa3 = Pa1.substring(Index3+1);
		var Index4 = Pa3.indexOf('&');
		var Pa4 = Pa1.substring(Index3+1);
		var Index5 = Pa4.indexOf('=');
		if(Index4 == -1){
			var Pa5 = Pa4.substring(Index5+1);
			Pa5 = Trim(Pa5);
		}else{
			var Pa5 = Pa4.substring(Index5+1,Index4);
			Pa5 = Trim(Pa5);
		}
		if(Pa2 != '员工姓名'){
			$(".classify").find('option[value="'+Pa2+'"]').attr("selected",true);
			$(".select1").html('').html('<input type="text" id="searchContent1" name="parameter" value="'+Pa5+'" style="width:80%;height:29px;border:1px solid darkgrey;border-left:none;float:left;">');
		
		}else{
			SelectContent();
			for(var i =0;i<$("select.staffName").find('option').length;i++){
				var P2 = $("select.staffName").find('option').eq(i).attr('value');
				var P3 = Trim(P2);
				if(P3 == Pa5){
				 $("select.staffName").find('option[value="'+P2+'"]').attr("selected",true);	
				}
			}
			
		}
					
	}
	
});

$(".classify").change(function(engineerOb){
	var Txt = $(".classify").val();
	if(Txt == '员工姓名'){
		SelectContent();
	}else{
		$(".select1").html('').html('<input type="text" id="searchContent1" name="parameter"  style="width:80%;height:29px;border:1px solid darkgrey;border-left:none;float:left;">');
	}
});

$(function(){
    // 日历范围选择
    $('.info-rili i').click(function() {
          $(this).parent().find('input').click();
    });
    var daterangepickerOption = {
        // startDate: moment().hours(0).minutes(0).seconds(0), //设置开始日期
        startDate: moment(new Date(Date.parse(new Date().getFullYear()+'-01-01'))), //设置开始日期
        endDate: moment(new Date()), //设置结束器日期
        maxDate: moment(new Date()), //设置最大日期
        "opens": "left",
        ranges: {
             '今天': [moment(), moment()],
             '昨天': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
             '上周': [moment().subtract(6, 'days'), moment()],
             '前30天': [moment().subtract(29, 'days'), moment()],
             '本月': [moment().startOf('month'), moment().endOf('month')],
             '上月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            },
        showWeekNumbers: true,
        alwaysShowCalendars: true,
        locale: {
            format: "YYYY-MM-DD", //设置显示格式
            // format: "YYYY-MM-DD", //设置显示格式
            applyLabel: '确定', //确定按钮文本
            cancelLabel: '取消', //取消按钮文本
            customRangeLabel: '自定义',
            daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
            monthNames: ['一月', '二月', '三月', '四月', '五月', '六月',
                 '七月', '八月', '九月', '十月', '十一月', '十二月'
            ],
            firstDay: 1
        },
    };

    $('#config-demo').daterangepicker(daterangepickerOption,function(start, end, label) {
         timeRangeChange = [start.format('YYYY-MM-DD HH:mm:ss'), end.format('YYYY-MM-DD HH:mm:ss')];
       console.log(timeRangeChange);
     });
    // $('#config-demo').daterangepicker(daterangepickerOption, function(start, end, label) { console.log('New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')'); });
    
    /*交通方式切换*/
    var TrafficExpenseObj = {
        "飞机": _.range(0, 4200, 200),
        "铁路": _.range(0, 2050, 50),
        "自驾": _.range(0, 3050, 50),
        "打车": _.range(0, 1050, 50),
        "其他": _.range(0, 2010, 10)
    };

    $(document).on("change", "select[name='TransportTool_sel']", function(){
        var changeDOM;
        if($(this).is("#add_info_TransportTool")){
            changeDOM = $("#add_info_TrafficExpense");
        }else if($(this).is("#update_info_TransportTool")){
            changeDOM = $("#update_info_TrafficExpense");
        }
        var iVal = $(this).val();
        var iStr = "";
        if(iVal == "请选择" || iVal === null || iVal === undefined){
            iStr = "<option value='请选择交通方式'>请选择交通方式</option>";
        }else{
            TrafficExpenseObj[iVal].map(function(v, i, arr){
                iStr+="<option value="+v+">"+v+"元</option>";
            });
        }
        changeDOM.empty().append(iStr);
    });

    // var originDistanceData;
    // var originDistanceDataOper1;
    // var originDistanceDataOper2;
    // var originDistanceDataOper3;
    // var originDistanceDataOper4;
    // var originDistanceDataOper5;
    // var originDistanceDataOper6;
    // var originDistanceDataOper7;
    // var originDistanceDataOper8;
    // var originDistanceDataOper9;
    /*var errorID = [];
    function CalculateDistanceAjax(item,currentArr,index){
      var cityURI1 = encodeURIComponent(item.Departure);
      var cityURI2 = encodeURIComponent(item.Destination);
      var newObj = {};
      newObj = item;
      $.ajax({
          type: "POST",
          dataType: 'JSONP',
          url: 'https://api.map.baidu.com/geocoder/v2/?address='+cityURI1+'&output=json&ak=9Exo3oapwegLi9dzYT7Ho2GziyIT2KbS',
          success: function(msg){
            // console.log(msg);
            if(parseInt(msg.status) == 0){
              var lng1 = msg.result.location.lng;
              var lat1 = msg.result.location.lat;
              newObj.Departure = msg.result.location.lng+","+msg.result.location.lat;

              $.ajax({
                type: "POST",
                dataType: 'JSONP',
                url: 'https://api.map.baidu.com/geocoder/v2/?address='+cityURI2+'&output=json&ak=9Exo3oapwegLi9dzYT7Ho2GziyIT2KbS',
                // 9Exo3oapwegLi9dzYT7Ho2GziyIT2KbS
                // ZHnpqUp55qaTvkE27KGjUBYLcUfsaRTV
                // 1eXBmyAAwn3hmoIg3lCGiiezscMRG7kb
                success: function(msg2){
                  if(parseInt(msg2.status) == 0){
                    var lng2 = msg2.result.location.lng;
                    var lat2 = msg2.result.location.lat;
                    newObj.Destination = msg2.result.location.lng+","+msg2.result.location.lat;
                    var map = new BMap.Map();
                    var pointA = new BMap.Point(lng1,lat1);  
                    var pointB = new BMap.Point(lng2,lat2); 
                    var distance = (map.getDistance(pointA,pointB)/1000).toFixed(2);
                    if(distance == 0 || distance == "0"){
                      distance = (20).toFixed(2);
                    }
                    newObj.Distance = distance;
                  }else{
                    errorID.push(item.ID);
                    newObj.Distance = (0).toFixed(2);
                  }
                  return newObj;
                },
                error: function(){
                  console.log(currentArr+"操作索引值为"+index+"的再第二步失败了");
                }
              });

            }else{
              errorID.push(item.ID);
              newObj.Distance = (0).toFixed(2);
              return newObj;
            }
            // return newObj;
          },
          error: function(){
            console.log(currentArr+"操作索引值为"+index+"的失败了");
          }
        });
    }*/


    // $("#newDistanceCalc").on("click",function(){
    //   $.ajax({
    //     type: 'GET',
    //     url: 'CalculateDistance',
    //     success: function(data){
    //       console.log("测距数据获取成功了");
    //       console.log(typeof data);
    //       console.log(data);
    //       var newData = JSON.parse(data);
    //       console.log(typeof newData);
    //       console.log(newData);
    //       originDistanceData = newData.slice(0);
    //       originDistanceDataOper1 = newData.slice(1,100);
    //       originDistanceDataOper2 = newData.slice(100,199);
    //       originDistanceDataOper3 = newData.slice(199,298);
    //       originDistanceDataOper4 = newData.slice(298,397);
    //       originDistanceDataOper5 = newData.slice(397,496);
    //       originDistanceDataOper6 = newData.slice(496,595);
    //       originDistanceDataOper7 = newData.slice(595,694);
    //       originDistanceDataOper8 = newData.slice(694,793);
    //       originDistanceDataOper9 = newData.slice(793);
    //       console.log(originDistanceData);
    //     },
    //     errror: function(){
    //       alert('测距数据获取失败了');
    //     }
    //   });
    // });

    // $("#newDistanceCalc11").on("click",function(){
      
    //   console.log("originDistanceDataOper1的获取经纬度并计算距离");
    //   // console.log(ceshidata);
    //   originDistanceDataOper1.map(function(item,index,array){
    //     CalculateDistanceAjax(item,"originDistanceDataOper1",index);
        
    //   });
    //   console.log("originDistanceDataOper1");
    //   console.log(originDistanceDataOper1);
    //   console.log("errorID");
    //   console.log(errorID);
    //   console.log("originDistanceData");
    //   console.log(originDistanceData);
    // });

    // $("#newDistanceCalc22").on("click",function(){
      
    //   console.log("originDistanceDataOper2的获取经纬度并计算距离");
    //   // console.log(ceshidata);
    //   originDistanceDataOper2.map(function(item,index,array){
    //     CalculateDistanceAjax(item,"originDistanceDataOper2",index);
        
    //   });
    //   console.log("originDistanceDataOper2");
    //   console.log(originDistanceDataOper2);
    //   console.log("errorID");
    //   console.log(errorID);
    //   console.log("originDistanceData");
    //   console.log(originDistanceData);
    // });

    // $("#newDistanceCalc33").on("click",function(){
      
    //   console.log("originDistanceDataOper3的获取经纬度并计算距离");
    //   // console.log(ceshidata);
    //   originDistanceDataOper3.map(function(item,index,array){
    //     // if(item.ID=="323"||item.ID==323)continue;
    //     CalculateDistanceAjax(item,"originDistanceDataOper3",index);
        
    //   });
    //   console.log("originDistanceDataOper3");
    //   console.log(originDistanceDataOper3);
    //   console.log("errorID");
    //   console.log(errorID);
    //   console.log("originDistanceData");
    //   console.log(originDistanceData);
    // });

    // $("#newDistanceCalc44").on("click",function(){
      
    //   console.log("originDistanceDataOper4的获取经纬度并计算距离");
    //   // console.log(ceshidata);
    //   originDistanceDataOper4.map(function(item,index,array){
    //     CalculateDistanceAjax(item,"originDistanceDataOper4",index);
        
    //   });
    //   console.log("originDistanceDataOper4");
    //   console.log(originDistanceDataOper4);
    //   console.log("errorID");
    //   console.log(errorID);
    //   console.log("originDistanceData");
    //   console.log(originDistanceData);
    // });

    // $("#newDistanceCalc55").on("click",function(){
      
    //   console.log("originDistanceDataOper5的获取经纬度并计算距离");
    //   // console.log(ceshidata);
    //   originDistanceDataOper5.map(function(item,index,array){
    //     CalculateDistanceAjax(item,"originDistanceDataOper5",index);
        
    //   });
    //   console.log("originDistanceDataOper5");
    //   console.log(originDistanceDataOper5);
    //   console.log("errorID");
    //   console.log(errorID);
    //   console.log("originDistanceData");
    //   console.log(originDistanceData);
    // });

    // $("#newDistanceCalc66").on("click",function(){
      
    //   console.log("originDistanceDataOper6的获取经纬度并计算距离");
    //   // console.log(ceshidata);
    //   originDistanceDataOper6.map(function(item,index,array){
    //     CalculateDistanceAjax(item,"originDistanceDataOper6",index);
        
    //   });
    //   console.log("originDistanceDataOper6");
    //   console.log(originDistanceDataOper6);
    //   console.log("errorID");
    //   console.log(errorID);
    //   console.log("originDistanceData");
    //   console.log(originDistanceData);
    // });

    // $("#newDistanceCalc77").on("click",function(){
      
    //   console.log("originDistanceDataOper7的获取经纬度并计算距离");
    //   // console.log(ceshidata);
    //   originDistanceDataOper7.map(function(item,index,array){
    //     CalculateDistanceAjax(item,"originDistanceDataOper7",index);
        
    //   });
    //   console.log("originDistanceDataOper7");
    //   console.log(originDistanceDataOper7);
    //   console.log("errorID");
    //   console.log(errorID);
    //   console.log("originDistanceData");
    //   console.log(originDistanceData);
    // });

    // $("#newDistanceCalc88").on("click",function(){
      
    //   console.log("originDistanceDataOper8的获取经纬度并计算距离");
    //   // console.log(ceshidata);
    //   originDistanceDataOper8.map(function(item,index,array){
    //     CalculateDistanceAjax(item,"originDistanceDataOper8",index);
        
    //   });
    //   console.log("originDistanceDataOper8");
    //   console.log(originDistanceDataOper8);
    //   console.log("errorID");
    //   console.log(errorID);
    //   console.log("originDistanceData");
    //   console.log(originDistanceData);
    // });

    // $("#newDistanceCalc99").on("click",function(){
      
    //   console.log("originDistanceDataOper9的获取经纬度并计算距离");
    //   // console.log(ceshidata);
    //   originDistanceDataOper9.map(function(item,index,array){
    //     CalculateDistanceAjax(item,"originDistanceDataOper9",index);
        
    //   });
    //   console.log("originDistanceDataOper9");
    //   console.log(originDistanceDataOper9);
    //   console.log("errorID");
    //   console.log(errorID);
    //   console.log("originDistanceData");
    //   console.log(originDistanceData);
    // });

    // $("#newDistanceCalc100").on("click",function(){
    //   var distanceUl = originDistanceData.slice(0);
    //   console.log("distanceUl");
    //   console.log(distanceUl);
    //   if(distanceUl){
    //     var str="";
    //     for(var i = 1;i<distanceUl.length;i++){
    //       if(distanceUl[i].ID=="323"||distanceUl[i].ID==323) continue;
    //       str+='<li id="'+distanceUl[i].ID+'" departure="'+distanceUl[i].Departure+'" destination="'+distanceUl[i].Destination+'">'+distanceUl[i].Distance+'</li>';
    //     }
    //     $("#distanceUl").append(str);
    //   }
      
    // });

    // $("#newDistanceCalc1000").on("click",function(){
    //   console.log("开始发数据了");
    //   // console.log("originDistanceData");
    //   // console.log(originDistanceData);
    //   // var originDistanceDataBack = originDistanceData.slice(1);
    //   var originDistanceDataBack = [];
    //   var distanceUILength = $("#distanceUI li").length;
    //   console.log("distanceUILength:"+distanceUILength);
    //   for (var i=0;i<distanceUILength;i++){
    //     var item = {};
    //     var currentLi = $("#distanceUI li").eq(i);
    //     item.ID = currentLi.attr("id");
    //     item.Departure = currentLi.attr("departure");
    //     item.Destination = currentLi.attr("destination");
    //     item.Distance = currentLi.text();
    //     originDistanceDataBack.push(item);
    //   }
    //   console.log("originDistanceDataBack");
    //   console.log(originDistanceDataBack);
    //   originDistanceDataBack = JSON.stringify(originDistanceDataBack);
    //   console.log("处理后的originDistanceDataBack");
    //   console.log(typeof originDistanceDataBack);
    //   console.log(originDistanceDataBack);
    //   $.ajax({
    //     type: 'POST',
    //     url: 'CalculateDistance',
    //     data: {
    //       originDistanceDataBack: originDistanceDataBack
    //     },
    //     success: function(data){
    //       console.log("发数据成功了");
    //       console.log(typeof data);
    //       console.log(data);
          
    //     },
    //     errror: function(){
    //       alert('发数据失败了');
    //     }
    //   });
    // });

});


/*原来script标签内*/
var k4 = new Kalendae.Input('week_sel', {
    months: 1,
    mode: 'week',
    format: "YYYY-MM-DD",
    rangeDelimiter: "至",
    side:"bottom",
    disableDate:true,
    selected: Kalendae.moment(),
    subscribe: {
        'change': function (date) {
            // console.log(this.getSelected());
            $(".k-btn-close").trigger("click");
        }}
});
$(document).on("change","input[name='date_sel']",function(){
    if($("input[name='date_sel']:checked").val()=="dateWeek"){
        $(".dateRange_div").css({"display":"none"});
        $(".dateWeek_div").css({"display":"inline-block"});
    }else if($("input[name='date_sel']:checked").val()=="dateRange"){
        $(".dateWeek_div").css({"display":"none"});
        $(".dateRange_div").css({"display":"inline-block"});
    }
});

var schedule_startTime;
var schedule_endTime;

function switchDate(){
    if($("input[name='date_sel']:checked").val()=="dateRange"){
        var start_time0 = $(".calendar.left .input-mini").val();
        var end_time0 = $(".calendar.right .input-mini").val();
        if(start_time0 == "" || start_time0 == null){
            schedule_startTime = new Date().getFullYear()+'-01-01';
        }else{
            schedule_startTime = start_time0;
        }
        if(end_time0 == "" || end_time0 == null){
            var D0 = new Date();
            var yy0=D0.getFullYear(); 
            var mm0=D0.getMonth()+1; 
            if (mm0<10){
            mm0 = "0"+mm0;
            }
            var dd0=D0.getDate();
            if (dd0 < 10){
                dd0 = "0" + dd0;
            }
            schedule_endTime = yy0+'-'+mm0+'-'+dd0;
        }else{
            schedule_endTime = end_time0;
        }
    }else if($("input[name='date_sel']:checked").val()=="dateWeek"){
        if($("#week_sel").val().indexOf("至")>-1){
            schedule_startTime = $("#week_sel").val().split("至")[0];
            schedule_endTime = $("#week_sel").val().split("至")[1];
        }else if($("#week_sel").val().indexOf("至") == -1){
            schedule_startTime = $("#week_sel").val().trim();
            var wD = new Date();
            var wy = wD.getFullYear(); 
            var wm = wD.getMonth()+1; 
            if (wm<10){
            wm = "0"+wm;
            }
            var wd = wD.getDate();
            if (wd < 10){
                wd = "0" + wd;
            }
            schedule_endTime = wy+'-'+wm+'-'+wd;
        }
    }
}

$(function(){
    // 计算修改了一个出发地或目的地
    /*function alterDistance1(address1,address2){
      var url1 = 'https://api.map.baidu.com/geocoder/v2/?ak=ZHnpqUp55qaTvkE27KGjUBYLcUfsaRTV&output=json&address=' + encodeURIComponent(address1);
      var url2 = 'https://api.map.baidu.com/geocoder/v2/?ak=ZHnpqUp55qaTvkE27KGjUBYLcUfsaRTV&output=json&address=' + encodeURIComponent(address2);
            //根据地点名称获取经纬度信息
              var distance;
              $.ajax({
                  type: "POST",
                  url: url1,
                  dataType: "JSONP",
                  success: function (data) {
                    console.log(data);
                  if (parseInt(data.status) == 0) {
                    var lng1= data.result.location.lng;
                    var lat1= data.result.location.lat;
                    console.log(lng1)
                    console.log(lat1)
                    $.ajax({
                       type: "POST",
                       url: url2,
                       dataType: "JSONP",
                       success: function (data) {
                        if (parseInt(data.status) == 0) {
                          var lng2= data.result.location.lng;
                          var lat2= data.result.location.lat;
                        }
                        var map = new BMap.Map();
                    var pointA = new BMap.Point(lng1,lat1);  
              var pointB = new BMap.Point(lng2,lat2);  
              distance = (map.getDistance(pointA,pointB)/1000).toFixed(2);
              // $("#allmap").text(distance);
              // return distance;
              $("#tempDistance2").val(distance);
                       }
                  });
                  }
                } 
            });
    }*/

    /*function initMap(box,dom,item,saveDis){
        // 百度地图API功能  
        var map = new BMap.Map(box, {enableMapClick: false}); //创建百度地图实例，这里的allmap是地图容器的id  
        var point = new BMap.Point(120.737582, 31.261794); //创建一个点对象，这里的参数是地图上的经纬度  
        var marker = new BMap.Marker(point); // 创建标注
        map.centerAndZoom(point, 12); //这里是将地图的中心移动到我们刚才创建的点；这里的12是地图的缩放界别；数值越大，地图看的越细  
        map.enableDragging();
        map.enableScrollWheelZoom();
        map.clearOverlays();
        var geoc = new BMap.Geocoder();
        //切换城市
        var size = new BMap.Size(10, 20);
        map.addControl(new BMap.CityListControl({
            anchor: BMAP_ANCHOR_TOP_LEFT,
            offset: size,
        }));
        //点击地图选址    出现标注点
        map.addEventListener("click", showInfo);
        function showInfo(e) {
            x = e.point.lng; //获取鼠标当前点击的经纬度
            y = e.point.lat;
            if(x != "" && y != "") {
                map.removeOverlay(marker);
                var point1 = new BMap.Point(x, y);
                map.centerAndZoom(point1);
                marker = new BMap.Marker(point1); // 创建新的标注
                map.addOverlay(marker); //将标注添加到地图上
            }
            var pt = e.point;
            console.log(pt);
            if($(item).attr("lngAndlat")){
                var pt0 = {};
                var lng0 = $(item).attr("lngAndlat").split(",")[0];
                // alert(typeof lng0);
                lng0 = lng0*1;
                // alert(typeof lng0);
                var lat0 = $(item).attr("lngAndlat").split(",")[1];
                lat0 = lat0*1;
                pt0.lng = lng0;
                pt0.lat = lat0;
                console.log("pt0的坐标");
                console.log(pt0);
                tempDistance = (map.getDistance(pt,pt0)/1000).toFixed(2);
                // alert(tempDistance);
                $(saveDis).val(tempDistance);
            }
            geoc.getLocation(pt, function(rs) {
                var addComp = rs.addressComponents;
//              var text = addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber;
                var text = addComp.province + "," + addComp.city ;
                // dom.text(text);    // 将选点的地址信息放置元素中
                dom.val(text);    // 将选点的地址信息放置元素中
                dom.removeAttr("lngAndlat"); // 存储坐标点信息
                dom.attr("lngAndlat", pt.lng + "," + pt.lat);

                if($(item).val() && ($(item).val().indexOf(",") == -1)){
                    var address1 = $(item).val().trim();
                    var address2 = addComp.city;
                    alterDistance1(address1,address2);
                }
            });
        }
    }

    $(document).on("click",".startArea",function(){
        $(".mybtn1").fadeIn(100);
        $("#allmap").fadeIn(100);
        initMap("allmap",$(".startArea"),".arriveArea","#tempDistance1");
    });

    $(document).on("click",".arriveArea",function(){
        $(".mybtn1").fadeIn(100);
        $("#allmap").fadeIn(100);
        initMap("allmap",$(".arriveArea"),".startArea","#tempDistance1");
    });
    
    $(document).on("click",".startArea2",function(){
        $(".mybtn2").fadeIn(100);
        $("#allmap").fadeIn(100);
        initMap("allmap",$(".startArea2"),".arriveArea2","#tempDistance2");
    });

    $(document).on("click",".arriveArea2",function(){
        $(".mybtn2").fadeIn(100);
        $("#allmap").fadeIn(100);
        initMap("allmap",$(".arriveArea2"),".startArea2","#tempDistance2");
    });

    //  map.getDistance(pointA,pointB)/1000).toFixed(2);   //获取两地距离 pointA  pointB分别为两地坐标点
    
    $(".mybtn1").on("click",function(){
        // (map.getDistance($(".startArea").attr("lngAndlat"),$(".arriveArea").attr("lngAndlat"))/1000).toFixed(2);
        $("#allmap").fadeOut(100);
        $(".mybtn1").fadeOut(100);
    });
    $(".mybtn2").on("click",function(){
        $("#allmap").fadeOut(100);
        $(".mybtn2").fadeOut(100);
    });*/

    function calcExpense(){
        if($(".tbody_tr").length){
            $(".tbody_tr").each(function(){
                var a = $(this).find(".showTripCost1").text();
                var b = $(this).find(".showTripCost2").text();
                if(a==""||a=="--"||a==null||a==undefined){
                    a=0;
                }
                if(b==""||b=="--"||b==null||b==undefined){
                    b=0;
                }
                $(this).find(".showTripCost").text(a*1+b*1);
            });
        }
    }
    calcExpense();
});

var city = {北京:["东城区","西城区","崇文区","宣武区","朝阳区","海淀区","丰台区","石景山区","房山区","通州区","顺义区","昌平区","大兴区","怀柔区","平谷区","门头沟区","密云县","延庆县"],天津:["和平区","河东区","河西区","南开区","河北区","红桥区","东丽区","西青区","北辰区","津南区","武清区","宝坻区","滨海新区","静海县","宁河县","蓟县"],上海:["黄浦区","卢湾区","徐汇区","长宁区","静安区","普陀区","闸北区","虹口区","杨浦区","闵行区","宝山区","嘉定区","浦东新区","金山区","松江区","青浦区","奉贤区","崇明县"],重庆:["渝中区","大渡口区","江北区","南岸区","北碚区","渝北区","巴南区","长寿区","双桥区","沙坪坝区","万盛区","万州区","涪陵区","黔江区","永川区","合川区","江津区","九龙坡区","南川区","綦江县","潼南县","荣昌县","璧山县","大足县","铜梁县","梁平县","开县","忠县","城口县","垫江县","武隆县","丰都县","奉节县","云阳县","巫溪县","巫山县"],河北:["石家庄","唐山","秦皇岛","邯郸","邢台","保定","张家口","承德","沧州","廊坊","衡水"],山西:["太原","大同","阳泉","长治","晋城","朔州","晋中","运城","忻州","临汾","吕梁"],辽宁:["沈阳","大连","鞍山","抚顺","本溪","丹东","锦州","营口","阜新","辽阳","盘锦","铁岭","朝阳","葫芦岛"],吉林:["长春","吉林","四平","辽源","通化","白山","松原","白城","延边朝鲜族自治州"],黑龙江:["哈尔滨","齐齐哈尔","鹤岗","双鸭山","鸡西","大庆","伊春","牡丹江","佳木斯","七台河","黑河","绥化","大兴安岭"],江苏:["南京","苏州","无锡","常州","镇江","南通","泰州","扬州","盐城","连云港","徐州","淮安","宿迁"],浙江:["杭州","宁波","温州","嘉兴","湖州","绍兴","金华","衢州","舟山","台州","丽水"],安徽:["合肥","芜湖","蚌埠","淮南","马鞍山","淮北","铜陵","安庆","黄山","滁州","阜阳","宿州","巢湖","六安","亳州","池州","宣城"],福建:["福州","厦门","莆田","三明","泉州","漳州","南平","龙岩","宁德"],江西:["南昌","景德镇","萍乡","九江","新余","鹰潭","赣州","吉安","宜春","抚州","上饶"],山东:["济南","青岛","淄博","枣庄","东营","烟台","潍坊","济宁","泰安","威海","日照","莱芜","临沂","德州","聊城","滨州","菏泽"],河南:["郑州","开封","洛阳","平顶山","安阳","鹤壁","新乡","焦作","濮阳","许昌","漯河","三门峡","南阳","商丘","信阳","周口","驻马店"],湖北:["武汉","黄石","十堰","荆州","宜昌","襄樊","鄂州","荆门","孝感","黄冈","咸宁","随州","恩施"],湖南:["长沙","株洲","湘潭","衡阳","邵阳","岳阳","常德","张家界","益阳","郴州","永州","怀化","娄底","湘西"],广东:["广州","深圳","珠海","汕头","韶关","佛山","江门","湛江","茂名","肇庆","惠州","梅州","汕尾","河源","阳江","清远","东莞","中山","潮州","揭阳","云浮"],海南:["海口","三亚"],四川:["成都","自贡","攀枝花","泸州","德阳","绵阳","广元","遂宁","内江","乐山","南充","眉山","宜宾","广安","达州","雅安","巴中","资阳","阿坝","甘孜","凉山"],贵州:["贵阳","六盘水","遵义","安顺","铜仁","毕节","黔西南","黔东南","黔南"],云南:["昆明","曲靖","玉溪","保山","昭通","丽江","普洱","临沧","德宏","怒江","迪庆","大理","楚雄","红河","文山","西双版纳","腾冲"],陕西:["西安","铜川","宝鸡","咸阳","渭南","延安","汉中","榆林","安康","商洛"],甘肃:["兰州","嘉峪关","金昌","白银","天水","武威","酒泉","张掖","庆阳","平凉","定西","陇南","临夏","甘南"],青海:["西宁","海东","海北","海南","黄南","果洛","玉树","海西"],内蒙古:["呼和浩特","包头","乌海","赤峰","通辽","鄂尔多斯","呼伦贝尔","巴彦淖尔","乌兰察布","锡林郭勒盟","兴安盟","阿拉善盟"],广西:["南宁","柳州","桂林","梧州","北海","防城港","钦州","贵港","玉林","百色","贺州","河池","来宾","崇左"],西藏:["拉萨","那曲","昌都","林芝","山南","日喀则","阿里"],宁夏:["银川","石嘴山","吴忠","固原","中卫"],新疆:["乌鲁木齐","克拉玛依","吐鲁番","哈密","和田","阿克苏","喀什","克孜勒苏","巴音郭楞","昌吉","博尔塔拉","伊犁","塔城","阿勒泰"],香港:["香港岛","九龙东","九龙西","新界东","新界西"],澳门:["澳门半岛","离岛"],台湾:["台北","高雄","基隆","新竹","台中","嘉义","台南"],加拿大温哥华: ["加拿大温哥华"]};
function province(a){
    for(var i in city){
        if(city[i].indexOf(a) > -1 ){
            return i;
        }
    }
}

var myChart1,myChart2,myChart3,myChart4,myChart5;

// 函数封装
function showMap(start_time,end_time){
    $.ajax({
        type: 'get',
        url: 'ScheduleProvince',
        data:{
            StartTime : start_time,
            EndTime : end_time,
            Name:""
        },
        success:function(data){
            console.log("出差省份分布");
            console.log(data);
            console.log(typeof data);
            data = JSON.parse(data);
            console.log(data);
            console.log(typeof(data));
            var newArrProvince = [];
            var newArrNo = [];
            if(data.length>1){
                for(var i = 1;i<data.length-1;i++){
                    if(data[i].province!=""&&data[i].province!="--"){
                        newArrProvince.push(data[i].province);
                    }
                    if(data[i].times!=""&&data[i].times!="--"){
                        newArrNo.push(Number(data[i].times));
                    }
                }
                console.log(newArrProvince);
                console.log(newArrNo);
                $(".mapContent").html('');
                var str = '<div id="main"></div>';
                $(".mapContent").append(str);
                provMap(newArrProvince,newArrNo);
            }else{
                $.MsgBox_Unload.Alert("提示","该时间段无出差省份记录");
            }
            
        },
        error:function(){
            alert("网络错误");
        }
    });
    
    
// $.ajax({
//          type: 'get',
//          url: 'ScheduleByTime',
//          dataType : 'json', 
//          data:{
//              StartTime : start_time,
//              EndTime : end_time,
//              Name:""
//              },
//           success: function(result){
//              console.log(222222222222)
//              console.log(result);
                                
//              /* {"常州市":"1","苏州市":"8","徐州市":"1","大连市":"15","北京市":"1","厦门市":"13","盐城市":"1","南京市":"1"} */
                                
//              console.log("heheheheheh")
//              console.log(result);
//              var cityArr = [];
//              var numberArr = [];
//              var allcityArr = [];
//              var allnumberArr = [];
//              for(var k in result){
//                  console.log('123'+k+result[k])
//                  //如果k 为空或者异常，去掉数组中的该项
//                  if(k == ''){
//                      /* alert(k)
//                      alert(result[k]) */
//                      delete result[k];
//                  }
//                      console.log("删除城市名为空的后")
//                      console.log('666'+k+result[k])
//                      console.log("hhh")
//                      console.log(result.toString)
//                  if(result[k] == 0 || result[k] == ''){
//                      delete result[k];
//                      console.log("再次删除城市对应的次数为空或者0的后")
//                      console.log('777'+k+result[k])
//                  }
//                  if(k.lastIndexOf('市') == k.length-1 && k.lastIndexOf('市') != -1){
//                      var city = k.substring(0,k.length-1);
//                      var prov = province(city);
//                      console.log("传进来的带上市，插件要求不带处理去掉")
//                      console.log(prov)
//                      console.log(result);
//                      if(!prov && city != "北京" && city != "上海" && city != "重庆" && city != "天津" && city != "香港" && city != "澳门"){
//                          delete result[k];
//                      }
//                      var number = city + '市';
//                  }else{
//                      var city = k;
//                      var prov = province(city);
//                      console.log("传进来的没有带市")
//                      console.log(prov)
//                      console.log(result);
//                      if(!prov && city != "北京" && city != "上海" && city != "重庆" && city != "天津" && city != "香港" && city != "澳门"){
//                          delete result[k];
//                      }
//                      var number = city;
//                  }
//                      console.log("最终输出的城市数组")
//                      console.log(result)
//                      console.log('888'+k+result[k])
//              }
                                
//              //对处理后的或者无需处理的result操作
//              for(var k in result){
//                  if(k.lastIndexOf('市') == k.length-1 && k.lastIndexOf('市') != -1){
//                      var city = k.substring(0,k.length-1);
//                      var prov = province(city);
//                      console.log(prov)
//                      var number = city + '市';
//                  }else{
//                      var city = k;
//                      var prov = province(city);
//                      console.log(prov)
//                      console.log('999'+k+result[k])
//                      var number = city;
//                  }
                
//              //获取省份以后，和用户点击的省份做比较，省份一样的保留city到新的数组中，保留对应的result[k]
                                        
//                  allcityArr.push(city);
//                  allnumberArr.push(result[number])
                    
//                  $(".mapContent").html('');
//                  var str = '<div id="main"></div>';
//                  $(".mapContent").append(str);
//                  provMap(provArr);
//                      BarMap(Name,allcityArr,allnumberArr);                   
                                            
//              }
//          },
//          error:function(){}
//      });

}

//地图模块
function provMap(newArrProvince,newArrNo){
    var dataArr = [];
    for(var i = 0;i<newArrProvince.length;i++){
        // console.log(provArr[i]); 
        var json = {};
        if(newArrProvince[i].indexOf("内蒙古")>-1){
            json.name = "内蒙古";
        }else if(newArrProvince[i].indexOf("自治区")>-1){
            json.name = newArrProvince[i].substring(0,2);
        }else{
            json.name = newArrProvince[i].substring(0,newArrProvince[i].length - 1);
        }
        json.value = newArrNo[i];
        dataArr.push(json);
    }
    console.log("出差统计分布，去除后缀的省");
    console.log(dataArr);
    console.log(typeof(dataArr));
     myChart1 = echarts.init(document.getElementById('main'));
      var ecConfig = echarts.config;  
var zrEvent = zrender.tool.event;
var curIndx = 0;
var mapType = [
];
fun(echarts);
function fun(ec){
              // 基于准备好的dom，初始化echarts图表
              myChart1 = ec.init(document.getElementById("main")); 
                console.log("基于准备好的dom，初始化echarts图表");
                 console.log(dataArr);
                 
            var option = {
                         tooltip : {
                             trigger: 'item',
                             formatter: '{b}'
                         },
                         series : [
                             {
                                 name: '中国',
                                 type: 'map',
                                 mapType: 'china',
                                 roam: false,
                                 selectedMode : 'multiple',
                                 itemStyle:{
                                     normal:{label:{show:true}},
                                     emphasis:{label:{show:true}},
                                 },
                                 data:dataArr
                                 // data:newArrProvince
                             }
                         ],
                         dataRange: {
                                 x: 'left',
                                 y: 'bottom',
                                 splitList: [
                                     // {start: 1500},
                                     // {start: 900, end: 1500},
                                     {start: 300},
                                     {start: 150, end: 300},
                                     {start: 50, end: 150},
                                     {start: 1, end: 50},
                                     {end: 1}
                                 ],
                                 color: ['#c00000','#ff0000','#ff9600','#ffff00','#fff']
                             },
                             // roamController: {
                             //         show: true,
                             //         x: 'right',
                             //         mapTypeControl: {
                             //             'china': true
                             //         }
                             //     }
            };
                 

            // update
            // var option = {
            //     tooltip: {
            //         trigger: 'item',
            //         formatter: '{b}'
            //     },
            //     visualMap: {
            //         seriesIndex: 0,
            //         min: 0,
            //         max: 500,
            //         left: 'left',
            //         top: 'bottom',
            //         text: ['高','低'],           // 文本，默认为数值文本
            //         calculable: true
            //         // controller:{
            //         //   inRange:{
            //         //       color:[rgb(255,255,255),rgb(255,255,0),rgb(255,128,0),rgb(255,0,0),rgb(116,0,0)]
            //         //   }
            //         // }
            //     },
            //     grid: {
            //         height: 200,
            //         width: 8,
            //         right: 80,
            //         bottom: 10
            //     },
            //     xAxis: {
            //         type: 'category',
            //         data: [],
            //         splitNumber: 1,
            //         show: false
            //     },
            //     yAxis: {
            //         position: 'right',
            //         min: 0,
            //         max: 20,
            //         splitNumber: 20,
            //         inverse: true,
            //         axisLabel: {
            //             show: true
            //         },
            //         axisLine: {
            //             show: false  
            //         },
            //         splitLine: {
            //             show: false
            //         },
            //         axisTick: {
            //             show: false
            //         },
            //         data: []
            //     },
            //     series: [
            //         {
            //             zlevel: 1,
            //             name: '中国',
            //             type: 'map',
            //             mapType: 'china',
            //             selectedMode : 'multiple',
            //             roam: true,
            //             left: 0,
            //             right: '15%',
            //             label: {
            //                 normal: {
            //                     show: true
            //                 },
            //                 emphasis: {
            //                     show: true
            //                 }
            //             },
            //             data:dataArr
            //             // [
            //             //     {name: '北京',value: randomData() },
            //             //     {name: '天津',value: randomData() },
            //             //     {name: '上海',value: randomData() },
            //             //     {name: '重庆',value: randomData() },
            //             //     {name: '河北',value: randomData() },
            //             //     {name: '河南',value: randomData() },
            //             //     {name: '云南',value: randomData() },
            //             //     {name: '辽宁',value: randomData() },
            //             //     {name: '黑龙江',value: randomData() },
            //             //     {name: '湖南',value: randomData() },
            //             //     {name: '安徽',value: randomData() },
            //             //     {name: '山东',value: randomData() },
            //             //     {name: '新疆',value: randomData() },
            //             //     {name: '江苏',value: randomData() },
            //             //     {name: '浙江',value: randomData() },
            //             //     {name: '江西',value: randomData() },
            //             //     {name: '湖北',value: randomData() },
            //             //     {name: '广西',value: randomData() },
            //             //     {name: '甘肃',value: randomData() },
            //             //     {name: '山西',value: randomData() },
            //             //     {name: '内蒙古',value: randomData() },
            //             //     {name: '陕西',value: randomData() },
            //             //     {name: '吉林',value: randomData() },
            //             //     {name: '福建',value: randomData() },
            //             //     {name: '贵州',value: randomData() },
            //             //     {name: '广东',value: randomData() },
            //             //     {name: '青海',value: randomData() },
            //             //     {name: '西藏',value: randomData() },
            //             //     {name: '四川',value: randomData() },
            //             //     {name: '宁夏',value: randomData() },
            //             //     {name: '海南',value: randomData() },
            //             //     {name: '台湾',value: randomData() },
            //             //     {name: '香港',value: randomData() },
            //             //     {name: '澳门',value: randomData() }
            //             // ]
            //         },
            //         {
            //             zlevel: 2,
            //             name: '地图指示',
            //             type: 'bar',
            //             barWidth: 5,
            //             itemStyle: {
            //                 normal: {
            //                     color: undefined,
            //                     shadowColor: 'rgba(0, 0, 0, 0.1)',
            //                     shadowBlur: 10
            //                 }
            //             },
            //             data: [20]
            //         }
            //     ]
            // };


            /**
             * 根据值获取线性渐变颜色
             * @param  {String} start 起始颜色
             * @param  {String} end   结束颜色
             * @param  {Number} max   最多分成多少分
             * @param  {Number} val   渐变取值
             * @return {String}       颜色
             */
            // function getGradientColor (start, end, max, val) {
            //     var rgb = /#((?:[0-9]|[a-fA-F]){2})((?:[0-9]|[a-fA-F]){2})((?:[0-9]|[a-fA-F]){2})/;
            //     var sM = start.match(rgb);
            //     var eM = end.match(rgb);
            //     var err = '';
            //     max = max || 1
            //     val = val || 0
            //     if (sM === null) {
            //         err = 'start';
            //     }
            //     if (eM === null) {
            //         err = 'end';
            //     }
            //     if (err.length > 0) {
            //         throw new Error('Invalid ' + err + ' color format, required hex color'); 
            //     }
            //     var sR = parseInt(sM[1], 16),
            //         sG = parseInt(sM[2], 16),
            //         sB = parseInt(sM[3], 16);
            //     var eR = parseInt(eM[1], 16),
            //         eG = parseInt(eM[2], 16),
            //         eB = parseInt(eM[3], 16);
            //     var p = val / max;
            //     var gR = Math.round(sR + (eR - sR) * p).toString(16),
            //         gG = Math.round(sG + (eG - sG) * p).toString(16),
            //         gB = Math.round(sB + (eB - sB) * p).toString(16);
            //     return '#' + gR + gG + gB;
            // }

            // setTimeout(function() {
            //     var TOPN = 25
                
            //     var option = myChart.getOption()
            //     // 修改top
            //     option.grid[0].height = TOPN * 20
            //     option.yAxis[0].max = TOPN
            //     option.yAxis[0].splitNumber = TOPN
            //     option.series[1].data[0] = TOPN
            //     // 排序
            //     var data = option.series[0].data.sort(function(a, b) {
            //         return b.value - a.value
            //     })
                
            //     var maxValue = data[0].value,
            //         minValue = data.length > TOPN ? data[TOPN - 1].value : data[data.length - 1].value
                
            //     var s = option.visualMap[0].controller.inRange.color[0],
            //         e = option.visualMap[0].controller.inRange.color.slice(-1)[0]
            //     var sColor = getGradientColor(s, e, maxValue, minValue)
            //     var eColor = getGradientColor(s, e, maxValue, maxValue)
                
            //     option.series[1].itemStyle.normal.color = new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
            //         offset: 1,
            //         color: sColor
            //     }, {
            //         offset: 0,
            //         color: eColor
            //     }])
                
            //     // yAxis
            //     var newYAxisArr = []
            //     echarts.util.each(data, function(item, i) {
            //         if (i >= TOPN) {
            //             return false
            //         }
            //         var c = getGradientColor(sColor, eColor, maxValue, item.value)
            //         newYAxisArr.push({
            //             value: item.name,
            //             textStyle: {
            //                 color: c
            //             }
            //         })
            //     })
            //     option.yAxis[0].data = newYAxisArr
            //     option.yAxis[0].axisLabel.formatter = (function(data) {
            //         return function(value, i) {
            //             if (!value) return ''
            //             return value + ' ' + data[i].value
            //         }
            //     })(data)
            //     myChart.setOption(option)
            // }, 0);

              // 为echarts对象加载数据 
              myChart1.setOption(option); 
                //地图模块回调
                 // myChart.on('click', function (parmas) {
                    //  console.log(parmas);
                    //  console.log(parmas.name);
                    //  var $tongji = $(".tongji_btn");
                    //  var start_time = $(".start_time").val();
                    //  var end_time = $(".end_time").val();
                    //  var Name = $(".tongji .staffName").find('option:selected').attr('value');
                    //  if(Name == '所有人'){
                    //   Name = '';
                    //  }
                       
                    // });
    }
}

// 根据时间段显示地图
function showRangeTimeMap(){
    var re = /^(\d{4})-(\d{2})-(\d{2})$/ ;
    showMap(schedule_startTime,schedule_endTime);
}

// 距离统计
function ajaxDistanceStatistics(startTime,endTime){
    return $.ajax({
            type: 'get',
            url: 'DistanceStatistics',
            dataType : 'json', 
            data:{
                startTime: startTime,
                endTime: endTime
                },
            success:function(data){
                console.log("这里是距离统计");
                console.log(data);
                console.log(typeof data);
                var new2CityName = [];
                var new2CityTimes = [];
                if(data.length>1){
                        for(var i=1;i<data.length;i++){
                            new2CityName.push(data[i].Name);
                            if(data[i].SumDistance==""||data[i].SumDistance=="--"||data[i].SumDistance==null){
                                data[i].SumDistance=0;
                            }
                            var tempSumDistance = data[i].SumDistance;
                            var intDistance = (tempSumDistance*1).toFixed(0);
                            new2CityTimes.push(intDistance);
                        }
                        console.log("最新的");
                        console.log(new2CityName);
                        console.log(new2CityTimes);

                        // for (var key in data) {
                        //     console.log(key);     //获取key值
                        //     console.log(data[key]); //获取对应的value值
                        //     new2CityName.push(key);
                        //     if(data[key]=="--"||data[key]==""||data[key]==null){
                        //      data[key]=0;
                        //     }
                        //     new2CityTimes.push(Number(data[key]));
                        //     console.log("新的")
                        //     console.log(key);     //获取key值
                        //     console.log(data[key]); //获取对应的value值
                        // }
                        $(".trip-distance-body").html('');
                        var str = '<div id="main3"></div>';
                        $(".trip-distance-body").append(str);
                        MapDistanceStatistics(new2CityName,new2CityTimes);
                    }else{
                        $.MsgBox_Unload.Alert("提示","该时间段无距离统计记录");
                    }
                
            },
            error:function(){
                $.MsgBox.Alert("提示","网络繁忙，请重新加载");
            }
        });
}

function showDistanceStatistics(){
    ajaxDistanceStatistics(schedule_startTime,schedule_endTime);
}

function MapDistanceStatistics(new2CityName,new2CityTimes){
    myChart2 = echarts.init(document.getElementById('main3'));
        var option = {
                title : {
                    text: '单位：km',
                    textStyle: {
                        color:'#999',
                        fontSize: 14
                    }
                    // subtext: Name
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['','']
                },
                
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        // data : cityArr,
                        data : new2CityName,
                        axisLabel:{  
                            interval:0,//横轴信息全部显示  
                            rotate:0,//-30度角倾斜显示 
                            textStyle:{
                                color: 'rgba(0,0,0,0.9)',
                                // fontFamily: '微软雅黑,宋体,Arial, Verdana, sans-serif',
                                fontSize:14 // 让字体变大
                            },
                            fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
                            // formatter:function(value)  
                   //          {  
                   //             return value.split("").join("\n");  
                   //          } 
                            formatter:function(value)  
                             {  
                                 // debugger  
                                 var ret = "";//拼接加\n返回的类目项  
                                 var maxLength = 1;//每项显示文字个数  
                                 // var maxLength = 2;//每项显示文字个数  
                                 var valLength = value.length;//X轴类目项的文字个数  
                                 var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数  
                                 if (rowN > 1)//如果类目项的文字大于3,  
                                 {  
                                     for (var i = 0; i < rowN; i++) {  
                                         var temp = "";//每次截取的字符串  
                                         var start = i * maxLength;//开始截取的位置  
                                         var end = start + maxLength;//结束截取的位置  
                                         //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧  
                                         temp = value.substring(start, end) + "\n";  
                                         ret += temp; //凭借最终的字符串  
                                     }  
                                     return ret;  
                                 }  
                                 else {  
                                     return value;  
                                 }  
                             }
                        },
                        splitLine:{ 
                            show:false
                        }
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'距离',
                        type:'bar',
                        // data:numberArr,
                        data:new2CityTimes,
                        markPoint : {
                            data : []
                        },
                        markLine : {
                            data : []
                        },
                        // 头部显示数据
                        itemStyle:{
                            normal:{
                                color: function(params) {
                                    // build a color map as your need.
                                    var colorList = [
                                      '#EE4775','#E3497F','#DD4B84','#D04B84','#CA4D91','#C04D91','#BF4E9A','#B84FA0','#B04FA0','#AA51AA','#A051AA','#9353BB','#8754C3','#8054C3','#7E56CA','#7056CA','#6F58D7','#685AE1','#605AE1','#5B5BE6','#505BE6','#4F5CF2','#4A5CF2','#455CF2','#405CA2','#3B5CA2','#305CA2'
                                    ];
                                    return colorList[(params.dataIndex)%(colorList.length)];
                                },
                                // 
                                // color: function(params) {
                                //     // build a color map as your need.
                                //     var colorList = [
                                //       ['#EE4775','#E3497F','#DD4B84'],
                                //       ['#CA4D91','#BF4E9A','#B84FA0'],
                                //       ['#B84FA0','#AA51AA','#9353BB'],
                                //       ['#8754C3','#7E56CA','#6F58D7'],
                                //       ['#625AE1','#5B5BE6','#4B5CF2']
                                //     ];
                                //     return new echarts.graphic.LinearGradient(0, 0, 0, 1,[
                                //      {offset: 0, color: colorList[params.dataIndex][0]},
                                //      {offset: 0.5, color: colorList[params.dataIndex][1]},
                                //      {offset: 1, color: colorList[params.dataIndex][2]}
                                //      ]);
                                // },
                               
                                barBorderRadius: 4,  //柱状角成椭圆形
                                label:{
                                    show:true,
                                    position:'top',
                                    textStyle: {
                                        color:'rgba(0,0,0,0.7)',
                                        fontSize:10
                                    },
                                    formatter:function(params){
                                        if(params.value==0){
                                            return '';
                                        }else{
                                            return params.value;
                                            }
                                    }
                                }
                            },
                            emphasis: {
                                barBorderRadius: 7
                            },
                        }
                  //       label: {
                        //  normal:{
                        //      show:true,
                        //      position:'top',
                        //      textStyle: {
                        //          color:'black'
                        //      }
                        //  }
                        // }
                    },
               
                 
                ]
        };     
    myChart2.setOption(option);
}


// 柱状图ajax
function barGraphAjax(start_time,end_time,Name) {
    return $.ajax({
            type: 'get',
            url: 'ScheduleByTime',
            dataType : 'json', 
            data:{
                StartTime : start_time,
                EndTime : end_time,
                Name:Name
                },
            success: function(result){
                console.log("#员工按姓名出差原始数据");
                console.log(result);
                console.log(typeof result);
                // 兼容老浏览器
                if (!Object.keys) {
                    Object.keys = function (obj) {
                        var keys = [],
                            k;
                        for (k in obj) {
                            if (Object.prototype.hasOwnProperty.call(obj, k)) {
                                keys.push(k);
                            }
                        }
                        return keys;
                    };
                }
                var cityArr = [];
                var numberArr = [];
                var allcityArr = [];
                var allnumberArr = [];
                var allcityArrnull = ["苏州"];
                var allnumberArrnull = ["0"];
                if(Object.keys(result).length){
                    for(var k in result){
                        if(k == ''){
                            delete result[k];
                        }
                        if(result[k] == 0 || result[k] == ''){
                            delete result[k];
                        }
                        // 去市及直辖市处理
                        if(k.lastIndexOf('市') == k.length-1 && k.lastIndexOf('市') != -1){
                            var city = k.substring(0,k.length-1);
                            var prov = province(city);
                            if(!prov && city != "北京" && city != "上海" && city != "重庆" && city != "天津" && city != "香港" && city != "澳门"){
                                delete result[k];
                            }
                            var number = city + '市';
                        }else{
                            // 不带市
                            var city = k;
                            var prov = province(city);
                            if(!prov && city != "北京" && city != "上海" && city != "重庆" && city != "天津" && city != "香港" && city != "澳门"){
                                delete result[k];
                            }
                            var number = city;
                        }
                    }
                    console.log("最终输出的城市数组");
                    console.log(result);
                    //对处理后的或者无需处理的result操作
                    for(var k in result){
                        if(k.lastIndexOf('市') == k.length-1 && k.lastIndexOf('市') != -1){
                            var city = k.substring(0,k.length-1);
                            var prov = province(city);
                            var number = city + '市';
                        }else{
                            var city = k;
                            var prov = province(city);
                            var number = city;
                        }
                    
                    //获取省份以后，和用户点击的省份做比较，省份一样的保留city到新的数组中，保留对应的result[k]
                        allcityArr.push(city);
                        allnumberArr.push(result[number]);
                    }
                    console.log("@员工按姓名出差城市分布，处理后的数据");
                    console.log(allcityArr);
                    console.log(allnumberArr);
                    $(".trip-city-body").html('');
                    var str = '<div id="main2"></div>';
                    $(".trip-city-body").append(str);
                    // BarMap(Name,cityArr,numberArr);
                    BarMap(Name,allcityArr,allnumberArr);
                }else{
                    BarMap(Name,allcityArrnull,allnumberArrnull);
                    $.MsgBox_Unload.Alert("提示","该员工此时间段无出差记录");
                }   
            },
            error:function(){
                console.log("***员工出差城市次数统计获取失败了***");
            }
        });
}

function BarMap(Name,cityArr,numberArr){
    
    myChart3 = echarts.init(document.getElementById('main2'));
     function map(){
         
            var option = {
                title : {
                    text: '单位：次数',
                    subtext: Name,
                    textStyle: {
                        color:'#999',
                        // color:'rgba(0,0,0,0.8)',
                        fontSize:14
                    }
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['','']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        data : cityArr,
                        axisLabel:{  
                            interval:0,//横轴信息全部显示  
                            rotate:0,//-30度角倾斜显示 
                            textStyle:{
                                color: 'rgba(0,0,0,0.9)',
                                // fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
                                fontSize:14 // 让字体变大
                            },
                            fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
                            formatter:function(value)  
                            {  
                               return value.split("").join("\n");  
                            }  
                        },
                        splitLine:{ 
                            show:false
                        }
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'次数',
                        type:'bar',
                        data:numberArr,
                        markPoint : {
                            data : []
                        },
                        markLine : {
                            data : []
                        },
                        // 头部显示数据
                        itemStyle:{
                            normal:{
                                // color: function(params) {
                                //     // build a color map as your need.
                                //     var colorList = [
                                //       '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                                //        '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                                //        '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
                                //     ];
                                //     return colorList[params.dataIndex]
                                // },
                                color: function(params) {
                                    // build a color map as your need.
                                    var colorList = [
                                      '#EE4775','#E3497F','#DD4B84','#D04B84','#CA4D91','#C04D91','#BF4E9A','#B84FA0','#B04FA0','#AA51AA','#A051AA','#9353BB','#8754C3','#8054C3','#7E56CA','#7056CA','#6F58D7','#685AE1','#605AE1','#5B5BE6','#505BE6','#4F5CF2','#4A5CF2','#455CF2','#405CF2','#3B5CA2','#305CA2'
                                    ];
                                    // return colorList[params.dataIndex];
                                    return colorList[(params.dataIndex)%(colorList.length)];
                                },
                                barBorderRadius: 5,
                                label:{
                                    show:true,
                                    position:'top',
                                    textStyle: {
                                        color:'rgba(0,0,0,0.7)',
                                        fontSize:10
                                    },
                                    formatter:function(params){
                                        if(params.value==0){
                                            return '';
                                        }else{
                                            return params.value;
                                            }
                                    }
                                }
                            }
                        }
                    },
                 
                ]
            };     
    myChart3.setOption(option);
    }
    map();
}

// 员工出差城市分布显示
function showTripCity(){
    var Name = $(".trip-city-tit-right .staffName").val();
    if (Name == "所有人"){
        Name = "";
    }
    $(".trip-city-tit-left span").text(Name);
    barGraphAjax(schedule_startTime,schedule_endTime,Name);
}


// *****第三个选项卡
// 员工出差次数排行
// echartContainer1:员工出差次数排行
function echartsStaffTripTimes(StaffTripTimes_Name,StaffTripTimes_times){
    myChart4 = echarts.init(document.getElementById('echartContainer1'));
    var option = {
            title : {
                text: '单位：次数',
                // subtext: Name,
                textStyle: {
                    color:'#999',
                    fontSize:14
                }
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:['','']
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    data : StaffTripTimes_Name,
                    axisLabel:{  
                        interval:0,//横轴信息全部显示  
                        rotate:0,//-30度角倾斜显示 
                        textStyle:{
                            color: 'rgba(0,0,0,0.9)',
                            fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
                            fontSize:14 // 让字体变大
                        },  
                        // fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
                        formatter:function(value)  
                        {  
                           return value.split("").join("\n");  
                        }
                    },
                    splitLine:{ 
                        show:false
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'次数',
                    type:'bar',
                    data:StaffTripTimes_times,
                    markPoint : {
                        data : []
                    },
                    markLine : {
                        data : []
                    },
                    // 头部显示数据
                    itemStyle:{
                        normal:{
                            color: function(params) {
                                // build a color map as your need.
                                var colorList = [
                                  '#EE4775','#E3497F','#DD4B84','#D04B84','#CA4D91','#C04D91','#BF4E9A','#B84FA0','#B04FA0','#AA51AA','#A051AA','#9353BB','#8754C3','#8054C3','#7E56CA','#7056CA','#6F58D7','#685AE1','#605AE1','#5B5BE6','#505BE6','#4F5CF2','#4A5CF2','#455CF2','#405CF2','#3B5CA2','#305CA2'
                                ];
                                return colorList[(params.dataIndex)%(colorList.length)];
                            },
                            barBorderRadius: 5,
                            label:{
                                show:true,
                                position:'top',
                                textStyle: {
                                    color:'rgba(0,0,0,0.7)',
                                    fontSize:10
                                },
                                formatter:function(params){
                                    if(params.value==0){
                                        return '';
                                    }else{
                                        return params.value;
                                        }
                                }
                            }
                        }
                    }
                },
             
            ]
    };
    myChart4.setOption(option);
}

function StaffTripTimesAjax(start_time,end_time){
    return $.ajax({
        type: 'get',
        url: 'FrequenceStatistics',
        dataType : 'json', 
        data:{
            startTime : start_time,
            endTime : end_time
            // Name:Name
        },
        success: function(data){
            console.log("@@@员工出差次数排行");
            console.log(typeof data);
            console.log(data);
            var StaffTripTimes_Name = [];
            var StaffTripTimes_times = [];
            if(data.length>1){
                for (var i = 1;i<data.length;i++){
                    if(data[i].Name != null && data[i].Name != ""){
                        StaffTripTimes_Name.push(data[i].Name);
                    }
                    if(data[i].times != null && data[i].times != ""){
                        StaffTripTimes_times.push(Number(data[i].times));
                    }
                }
                if(StaffTripTimes_Name.length != StaffTripTimes_times.length){
                    $.MsgBox.Alert("提示","网络异常，请重新加载");
                }else{
                    console.log("员工出差次数排行处理后数据");
                    console.log(StaffTripTimes_Name);
                    console.log(StaffTripTimes_times);
                    $(".trip-staff-times-body").html('');
                    var str = '<div id="echartContainer1"></div>';
                    $(".trip-staff-times-body").append(str);
                    echartsStaffTripTimes(StaffTripTimes_Name,StaffTripTimes_times);
                }
            }else{
                $.MsgBox_Unload.Alert("提示","该时间段无出差次数排行");
            }
            
        },
        error: function(){
            $.MsgBox_Unload.Alert("提示","网络出错，出差次数排行绘制失败");
        }
    });
}

function showStaffTripTimes(){
    StaffTripTimesAjax(schedule_startTime,schedule_endTime);
}

// 所有员工差旅费统计
// echartContainer2 所有员工差旅费统计
// 
function echartsExpenseStatistics(ExpenseStatistics_Destination,ExpenseStatistics_Expense){
    myChart5 = echarts.init(document.getElementById('echartContainer2'));
    var option = {
            title : {
                text: '单位：元',
                // subtext: Name,
                textStyle: {
                    color:'#999',
                    fontSize:14
                }
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:['','']
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    data : ExpenseStatistics_Destination,
                    axisLabel:{  
                        interval:0,//横轴信息全部显示  
                        rotate:0,//-30度角倾斜显示 
                        textStyle:{
                            color: 'rgba(0,0,0,0.9)',
                            // fontFamily: 'sans-serif',
                            fontSize:14 // 让字体变大
                        },
                        fontFamily: '微软雅黑, 宋体, Arial, Verdana, sans-serif',
                        formatter:function(value)  
                        {  
                           return value.split("").join("\n");  
                        }  
                    },
                    splitLine:{ 
                        show:false
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'差旅费',
                    type:'bar',
                    data:ExpenseStatistics_Expense,
                    markPoint : {
                        data : []
                    },
                    markLine : {
                        data : []
                    },
                    // 头部显示数据
                    itemStyle:{
                        normal:{
                            color: function(params) {
                                // build a color map as your need.
                                var colorList = [
                                  '#EE4775','#E3497F','#DD4B84','#D04B84','#CA4D91','#C04D91','#BF4E9A','#B84FA0','#B04FA0','#AA51AA','#A051AA','#9353BB','#8754C3','#8054C3','#7E56CA','#7056CA','#6F58D7','#685AE1','#605AE1','#5B5BE6','#505BE6','#4F5CF2','#4A5CF2','#455CF2','#405CF2','#3B5CA2','#305CA2'
                                ];
                                return colorList[(params.dataIndex)%(colorList.length)];
                            },
                            barBorderRadius: 5,
                            label:{
                                show:true,
                                position:'top',
                                textStyle: {
                                    color:'rgba(0,0,0,0.7)',
                                    fontSize:10
                                },
                                formatter:function(params){
                                    if(params.value==0){
                                        return '';
                                    }else{
                                        return params.value;
                                        }
                                }
                            }
                        }
                    }
                },
             
            ]
    };
    myChart5.setOption(option);
}

function ExpenseStatisticsAjax(start_time,end_time){
    return $.ajax({
        type: 'get',
        url: 'ExpenseStatistics',
        dataType : 'json', 
        data:{
            startTime : start_time,
            endTime : end_time
            // Name:Name
        },
        success: function(data){
            console.log("#费用统计原始数据");
            console.log(data);
            console.log(typeof data);
            var ExpenseStatistics_Destination = [];
            var ExpenseStatistics_Expense = [];
            var ExpenseStatistics_Destinationnull = ["苏州市"];
            var ExpenseStatistics_Expensenull = ["0"];
            if(data.length > 1){
                for(i=1;i<data.length;i++){
                    var city = data[i].Destination;
                    var expense = data[i].Expense;
                    if(city.lastIndexOf('市') == city.length-1 && city.lastIndexOf('市') != -1){
                        city = city.substring(0,city.length-1);
                        // if(city != "北京" && city != "上海" && city != "重庆" && city != "天津" && city != "香港" && city != "澳门"){
                  //        data.splice(i, 1);
                        // }

                    }else if(city.lastIndexOf('县') == city.length-1 && city.lastIndexOf('县') != -1){
                        city = city.substring(0,city.length-1);
                    }

                    // 再次处理
                    if(city.lastIndexOf('市') == city.length-1 && city.lastIndexOf('市') != -1){
                        city = city.substring(0,city.length-1);
                    }else if(city.lastIndexOf('县') == city.length-1 && city.lastIndexOf('县') != -1){
                        city = city.substring(0,city.length-1);
                    }

                    // 费用处理
                    if(expense == null || expense == "" || expense == "--"){
                        expense = "0";
                    }
                    ExpenseStatistics_Destination.push(city);
                    ExpenseStatistics_Expense.push(expense);
                }
                if(ExpenseStatistics_Destination.length != ExpenseStatistics_Expense.length){
                    $.MsgBox.Alert("提示","网络异常，请重新加载");
                }else{
                    console.log("差旅费用处理后数据");
                    console.log(ExpenseStatistics_Destination);
                    console.log(ExpenseStatistics_Expense);
                    $(".trip-cost-calc-body").html('');
                    var str = '<div id="echartContainer2"></div>';
                    $(".trip-cost-calc-body").append(str);
                    echartsExpenseStatistics(ExpenseStatistics_Destination,ExpenseStatistics_Expense);
                }
                
            }else{
                echartsExpenseStatistics(ExpenseStatistics_Destinationnull,ExpenseStatistics_Expensenull);
                $.MsgBox_Unload.Alert("提示","该时间段无费用数据");
            }
        },
        error: function(){
            $.MsgBox_Unload.Alert("提示","网络出错，费用统计绘制失败");
        }
    });
}

function showExpenseStatistics(){
    ExpenseStatisticsAjax(schedule_startTime,schedule_endTime);
}

// // 图表自适应
// $(window).resize(function() { //这是能够让图表自适应的代码
//     myChart1.resize();
//     myChart2.resize();
//     myChart3.resize();
//     myChart4.resize();
//     myChart5.resize();
// });


//选项卡
var cruurntCardIndex;
// function calcBodyBottomH(){
//  var newH = $("")
// }
// var aa1H = 50;
// var aa2H = 750;
// var aa3H = 1280;
// $(".aa1").on("click",function(){
//   $(window).scrollTop(aa1H);
//   // $(window).stop(true).animate({scrollTop: aa1H}, 500);
// });
// $(".aa2").on("click",function(){
//   $(window).scrollTop(aa2H);
// });
// $(".aa3").on("click",function(){
//   $(window).scrollTop(aa3H);
// });

$(".changeBox .Domestic").click(function(){
    $(".changeBox").css("background","url(image/bg11.png)");
    $(".bodyContent").css("height","705px");
    var bodyContentH = $(".bodyContent").height()+190;
    $("body.nav-body").css("height",bodyContentH+"px");
    $(".reloadDiv").fadeOut(50);
    $(".mapBox").fadeOut(50);
    $(".trip-city").fadeOut(50);
    $(".trip-cost-calc").fadeOut(50);
    $(".trip-staff-times").fadeOut(50);
    $(".trip-distance").fadeOut(50);
    $("#engineer-schedule-container").fadeIn(50);
     // $(".ExitOrEn").css("background","url(image/bg3.png)");
     // $(".Domestic").css("background","none");
     // calcBodyBottomH();
});
$(".changeBox .ExitOrEn").click(function(){
    cruurntCardIndex = 2;
    $(".changeBox").css("background","url(image/bg22.png)");
    $(".bodyContent").css("height","1640px");
    var bodyContentH = $(".bodyContent").height()+190;
    $("body.nav-body").css("height",bodyContentH+"px");
    // aa1H = 50;
    // aa2H = $(".trip-city").offset().top - 50;
    // aa3H = $(".trip-distance").offset().top - 50;
         // var Y = new Date().getFullYear();
            // var thetime = Y+'-01-01T00:00:00-04:00';
            // var  sT=new   Date(Date.parse(thetime));
            // document.getElementById('start_time').valueAsDate = sT;
            // document.getElementById('end_time').valueAsDate = new Date(); 
    $("#engineer-schedule-container").hide();
    $(".trip-staff-times").fadeOut(50);
    $(".trip-distance").fadeOut(50);
    $(".reloadDiv").fadeIn(50);
    $(".mapBox").fadeIn(50);
    $(".trip-city").fadeIn(50);
    $(".trip-cost-calc").fadeIn(50);
    // calcBodyBottomH();
    var engineer = $("span.engineer").html();
    var engineerOb = engineer.split(',')
        var len = engineerOb.length;
        var str1 = '<select class="staffName" style="width: 90%;height:27px;border-radius:5px;border:1px solid #999">'+
                    '<option value="所有人">所有人</option>';
        var str2 = '';
        var str3 = '';
        var str4 = '</select>'
        for(var i = 1;i<len-1;i++){
                str2 += '<option value="'+engineerOb[i].substring(12,engineerOb[i].length-1)+'">'+engineerOb[i].substring(12,engineerOb[i].length-1)+'</option>';
        }
        str3 += '<option value="'+engineerOb[len-1].substring(12,engineerOb[len-1].length-2)+'">'+engineerOb[len-1].substring(12,engineerOb[len-1].length-2)+'</option>';
        Str = str1+str2+str3+str4;
        // $(".tongji .nameContent").remove();
        $(".trip-city-tit-right .staffName").remove();
        $(".trip-city-tit-right").append(Str);
        
     // $(".Domestic").css("background","url(image/bg3.png)");
     // $(".ExitOrEn").css("background","none");    
    switchDate();
    showRangeTimeMap();
    showTripCity();
    showExpenseStatistics();
});

$(".changeBox .staffData").on("click",function(){
    cruurntCardIndex = 3;
    $(".changeBox").css("background","url(image/bg33.png)");
    $(".bodyContent").css("height","1120px");
    var bodyContentH = $(".bodyContent").height()+190;
    $("body.nav-body").css("height",bodyContentH+"px");
    $("#engineer-schedule-container").hide();
    $(".mapBox").fadeOut(50);
    $(".trip-city").fadeOut(50);
    $(".trip-cost-calc").fadeOut(50);
    $(".reloadDiv").fadeIn(50);
    $(".trip-staff-times").fadeIn(50);
    $(".trip-distance").fadeIn(50);
    // calcBodyBottomH();
    switchDate();
    showStaffTripTimes();
    showDistanceStatistics();
});

//点击统计按钮，发送请求***
$(".tongji_btn").click(function(){
    switchDate();
    if(cruurntCardIndex == 2){
        // alert("2");
        showRangeTimeMap();
        showTripCity();
        showExpenseStatistics();
    }else if(cruurntCardIndex == 3){
        // alert("3");
        showStaffTripTimes();
        showDistanceStatistics();
    }
    // showRangeTimeMap();
    // showTripCity();
    // showDistanceStatistics();
});

$(document).on("change",".trip-city-tit-right .staffName",function(){
    switchDate();
    showTripCity();
});

var currentDate;
$(function(){
    var searchDate = '<%=request.getAttribute("allDate")%>';
    console.log(searchDate);
    if(searchDate != null && searchDate != "[{col1=Date}]"){
        searchDate = searchDate.replace(/ /g,"").replace(/{Date=/g,"").replace(/}/g,"").replace("{","").replace("[","").replace("]","").split(",").unique();
    } 
    console.log(searchDate);
    var currentHref = window.location.href;
    var myDate = new Date();
    if(currentHref.indexOf("-")>0){
        currentDate = currentHref.split("?Date=")[1];
         if(currentDate.indexOf("&")>-1)
         {
             currentDate = currentDate.split("&")[0];
         }
    }
    else{
        currentDate = globalGetToday(false);
    }
    /* 日历插件 */
    $('#calendar').fullCalendar({
        header: {
            left: '',
            center: 'prevYear,prev,title,next,nextYear',
            right: ''
        },
        buttonText: {
            prev: '<', // ‹
            next: '>', // ›
            prevYear: '<<', // «
            nextYear: '>>', // »
        },
        titleFormat: {
            month: "yyyy年MM月",
            week: '周ddd'
        },
        locale: "zh-cn",
        weekMode :5,
        firstDay:1,
        editable: false,
        timeFormat: 'H:mm',
        axisFormat: 'H:mm',
        minTime: '7',
        maxTime:'19',
        allDaySlot:false,
        dayNamesShort:['日','一','二','三','四','五','六'],
        events: eventsList(),
        dayClick: function(date, jsEvent, view) {
           //执行表格切换时候的数据函数
            var theDate = $.fullCalendar.formatDate(date,'yyyy-MM-dd')
           /*  alert('当天事件为:' + theDate);  */
            var Date=theDate;
            $('.table1 .DateTime').attr("value",Date);//暂存日期
            currentDate = theDate;
           
            $("#help-form #All_upload").val(Date);
            $("#help-form").submit(); 
            
        },
        eventClick:function(event, jsEvent, view) {
            
        },

    });
    //改变日历的宽高
    function get_height() {
        $('table.fc-border-separate tbody tr').width($('.fc-day.fc-六.fc-widget-content.fc-future').width())

    }
    
    //计算时间差值
    if(currentHref.indexOf("?Date=") >0){
        var myDate = new Date();
        var currentYear = currentHref.split("?Date=")[1].split("-")[0];
         var year = parseFloat(myDate.getFullYear());
        if(currentYear - year > 0){
            var nextYear = document.getElementsByClassName('fc-button-nextYear')[0];
            for(var i = 0 ;i < (currentYear - year) ; i++){
                nextYear.click();
            }
        }
        else if(currentYear - year < 0){
            var lastYear = document.getElementsByClassName('fc-button-prevYear')[0];
            for(var i = 0 ;i < (year - currentYear) ; i++){
                lastYear.click();
            }
        }
         var currentMonth = currentHref.split("?Date=")[1].split("-")[1];
         var month = parseFloat(myDate.getMonth())+1;
         if(currentMonth -month > 0){
                var nextMoth = document.getElementsByClassName('fc-button-next')[0];
                for(var i = 0 ;i < (currentMonth - month) ; i++){
                    nextMoth.click();
                }
            }
            else if(currentMonth - month < 0){
                var lastMonth = document.getElementsByClassName('fc-button-prev')[0];
                for(var i = 0 ;i < (month - currentMonth) ; i++){
                    lastMonth.click();
                }
            }
    }
    for(var i = 0 ;i < $(".fc-border-separate .fc-day").length ; i++){
        //console.log(searchDate)
        if(typeof(searchDate)=="object"){
            for(var j = 1;j <searchDate.length ; j++ ){
            /*  console.log($(".fc-border-separate .fc-day").eq(i).attr("data-date"))
               console.log(searchDate[j]) */
                if($(".fc-border-separate .fc-day").eq(i).attr("data-date") == searchDate[j]){
                    var str='<div class="fc_flag" style="width:8px;height:8px;border-radius: 50%;background:red;float: right;margin-top: 0px;"></div>';
                    $(".fc-border-separate .fc-day").eq(i).find(".fc-day-content").after(str);
                }
            }
        }
        if($(".fc-border-separate .fc-day").eq(i).attr("data-date") == currentDate){
            $(".fc-border-separate .fc-day").eq(i).addClass('fc-state-highlight');
            $(".fc-border-separate .fc-day").eq(i).siblings().removeClass('fc-state-highlight');
        }
    }
    $(".fc-button").click(function(){
        for(var i = 0 ;i < $(".fc-border-separate .fc-day").length ; i++){
            //console.log(searchDate)
            if(typeof(searchDate)=="object"){
                for(var j = 1;j <searchDate.length ; j++ ){
                    if($(".fc-border-separate .fc-day").eq(i).attr("data-date") == searchDate[j]){
                        var str='<div class="fc_flag" style="width:8px;height:8px;border-radius: 50%;background:red;float: right;margin-top: 10px;"></div>';
                        $(".fc-border-separate .fc-day").eq(i).find(".fc-day-content").after(str);
                    }
                }
            }
        }
    });
    
  //改变日历的颜色
    $('body').on('click', '.fc-day', function () {
             var obj = $(this), date = obj.data('date');
             $('.fc-state-highlight').removeClass('fc-state-highlight');
             obj.addClass('fc-state-highlight');
     });
    
    get_height();

    $('#calendar').on('click', 'span.fc-button', function () {
        get_height();
    });
   
   
    //日历上的显示数据
    function eventsList(){
        var data = [
           /* {
                title: ' ',
                start: "2017-12-04",
            } */
        ];
        return data;
    }
    
    if ($('#currentPage').html() == 1) {
        $('#fistPage').attr('disabled', 'true');
        $('#fistPage').removeClass('bToggle');
        $('#upPage').attr('disabled', 'true');
        $('#upPage').removeClass('bToggle');
    }
    if ($('#allPage').html() == $('#currentPage').html()) {
        $('#lastPage').attr('disabled', 'true');
        $('#lastPage').removeClass('bToggle');
        $('#nextPage').attr('disabled', 'true');
        $('#nextPage').removeClass('bToggle');
    }
    
    
    //日历的标题
    
    //http://localhost:8080/cfChicken8/Schedule?Date=2018-04-01&currentPage=1
    //http://localhost:8080/cfChicken8/Schedule
    //http://localhost:8080/cfChicken8/Schedule?Date=2018-04-17
    var D=new Date(); 
    var ww=D.getDate();
    var Href = window.location.href;
    var i = Href.indexOf('?Date');
    if(i == '-1'){
        var numberT = ww;
        var DateT = new Date();
    }else{
        var Href1 = Href.substring(i+1); // "Date=2018-04-01&currentPage=1"
        Href1 = Href1.indexOf("&")>-1?Href1.split("&")[0]:Href1;
        var numberT = Href1.substring(13);
        if(numberT<10){
            numberT = numberT.substring(1);
        }
        var numberDate = Href1.substring(5) ;
        var DateT = new Date(Date.parse(numberDate));
    }
    
    var time = $(".fc-header-title h2").html();
    $(".dateBox_title").html(time);
    $(".dateBox_day").html(numberT);
    //日历上显示今天星期几
    function myFunction(DateT)
    {
        var d = DateT;
        var weekday=new Array(7);
        weekday[0]="星期日";
        weekday[1]="星期一";
        weekday[2]="星期二";
        weekday[3]="星期三";
        weekday[4]="星期四";
        weekday[5]="星期五";
        weekday[6]="星期六";
        /* var x = document.getElementById("demo");
        x.innerHTML=weekday[d.getDay()]; */
        $(".dataBox_textDay").html(weekday[d.getDay()]);
    }
    myFunction(DateT);
    
    //日历标题上显示农历
    /*获取当前农历*/
    function showCal(DateT){ 
    var D=DateT; 
    var yy=D.getFullYear(); 
    var mm=D.getMonth()+1; 
    var dd=D.getDate(); 
    var ww=D.getDay(); 
    var ss=parseInt(D.getTime() / 1000); 
    if (yy<100) yy="19"+yy; 
    return GetLunarDay(yy,mm,dd); 
    } 
     
    //定义全局变量 
    var CalendarData=new Array(100); 
    var madd=new Array(12); 
    var tgString="甲乙丙丁戊己庚辛壬癸"; 
    var dzString="子丑寅卯辰巳午未申酉戌亥"; 
    var numString="一二三四五六七八九十"; 
    var monString="正二三四五六七八九十冬腊"; 
    var weekString="日一二三四五六"; 
    var sx="鼠牛虎兔龙蛇马羊猴鸡狗猪"; 
    var cYear,cMonth,cDay,TheDate; 
    CalendarData = new Array(0xA4B,0x5164B,0x6A5,0x6D4,0x415B5,0x2B6,0x957,0x2092F,0x497,0x60C96,0xD4A,0xEA5,0x50DA9,0x5AD,0x2B6,0x3126E, 0x92E,0x7192D,0xC95,0xD4A,0x61B4A,0xB55,0x56A,0x4155B, 0x25D,0x92D,0x2192B,0xA95,0x71695,0x6CA,0xB55,0x50AB5,0x4DA,0xA5B,0x30A57,0x52B,0x8152A,0xE95,0x6AA,0x615AA,0xAB5,0x4B6,0x414AE,0xA57,0x526,0x31D26,0xD95,0x70B55,0x56A,0x96D,0x5095D,0x4AD,0xA4D,0x41A4D,0xD25,0x81AA5,0xB54,0xB6A,0x612DA,0x95B,0x49B,0x41497,0xA4B,0xA164B, 0x6A5,0x6D4,0x615B4,0xAB6,0x957,0x5092F,0x497,0x64B, 0x30D4A,0xEA5,0x80D65,0x5AC,0xAB6,0x5126D,0x92E,0xC96,0x41A95,0xD4A,0xDA5,0x20B55,0x56A,0x7155B,0x25D,0x92D,0x5192B,0xA95,0xB4A,0x416AA,0xAD5,0x90AB5,0x4BA,0xA5B, 0x60A57,0x52B,0xA93,0x40E95); 
    madd[0]=0; 
    madd[1]=31; 
    madd[2]=59; 
    madd[3]=90; 
    madd[4]=120; 
    madd[5]=151; 
    madd[6]=181; 
    madd[7]=212; 
    madd[8]=243; 
    madd[9]=273; 
    madd[10]=304; 
    madd[11]=334; 
     
    function GetBit(m,n){ 
    return (m>>n)&1; 
    } 
    //农历转换 
    function e2c(){ 
    TheDate= (arguments.length!=3) ? new Date() : new Date(arguments[0],arguments[1],arguments[2]); 
    var total,m,n,k; 
    var isEnd=false; 
    var tmp=TheDate.getYear(); 
    if(tmp<1900){ 
    tmp+=1900; 
    } 
    total=(tmp-1921)*365+Math.floor((tmp-1921)/4)+madd[TheDate.getMonth()]+TheDate.getDate()-38; 
     
    if(TheDate.getYear()%4==0&&TheDate.getMonth()>1) { 
    total++; 
    } 
    for(m=0;;m++){ 
    k=(CalendarData[m]<0xfff)?11:12; 
    for(n=k;n>=0;n--){ 
    if(total<=29+GetBit(CalendarData[m],n)){ 
    isEnd=true; break; 
    } 
    total=total-29-GetBit(CalendarData[m],n); 
    } 
    if(isEnd) break; 
    } 
    cYear=1921 + m; 
    cMonth=k-n+1; 
    cDay=total; 
    if(k==12){ 
    if(cMonth==Math.floor(CalendarData[m]/0x10000)+1){ 
    cMonth=1-cMonth; 
    } 
    if(cMonth>Math.floor(CalendarData[m]/0x10000)+1){ 
    cMonth--; 
    } 
    } 
    } 
     
    function GetcDateString(){ 
        var tmp=""; 
        var tmpother = "";
        /*显示农历年：（ 如：甲午(马)年 ）*/
        tmp+=tgString.charAt((cYear-4)%10); 
        tmp+=dzString.charAt((cYear-4)%12); 
        tmp+="("; 
        tmp+=sx.charAt((cYear-4)%12); 
        tmp+=")年 ";
        if(cMonth<1){ 
        tmp+="(闰)"; 
        tmpother+=monString.charAt(-cMonth-1); 
        }else{ 
        tmpother+=monString.charAt(cMonth-1); 
        } 
        tmpother+="月"; 
        tmpother+=(cDay<11)?"初":((cDay<20)?"十":((cDay<30)?"廿":"三十")); 
        if (cDay%10!=0||cDay==10){ 
        tmpother+=numString.charAt((cDay-1)%10); 
        } 

        var Tmp = tmp+'好'+tmpother;
        return Tmp; 
        } 
         
        function GetLunarDay(solarYear,solarMonth,solarDay){ 
        //solarYear = solarYear<1900?(1900+solarYear):solarYear; 
        if(solarYear<1921 || solarYear>2020){ 
        return ""; 
        }else{ 
        solarMonth = (parseInt(solarMonth)>0) ? (solarMonth-1) : 11; 
        e2c(solarYear,solarMonth,solarDay); 
        return GetcDateString(); 
        } 
        }   
                var calendar = showCal(DateT); 
                var index =  calendar.indexOf("好");
                /* alert(index); */
                var Y = calendar.substring(0,index);
                var M = '农历 '+calendar.substring(index+1);
                $(".dataBox_textLunar").html(M);
                $(".dataBox_textYear").html(Y);
                
    //星座
                /* function getAstro(m,d){
                      return "魔羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯".substr(m*2-(d<"102223444433".charAt(m-1)- -19)*2,2);
                    }
                    //下面写一个测试函数
                    function test(m,d){
                    document.writeln(m+"月"+d+"日 "+getAstro(m,d));
                    }
            $(".dataBox_textStar").html(getAstro(m,d)) */

    /*添加修改员工填充*/
    var engineerOb = $("span.engineer").html().split(',');
    var len = engineerOb.length;
    var str2 = '';
    var str3 = '';
    for(var i = 1;i<len-1;i++){
            str2 += '<option value="'+engineerOb[i].substring(12,engineerOb[i].length-1)+'">'+engineerOb[i].substring(12,engineerOb[i].length-1)+'</option>';
    }
    str3 += '<option value="'+engineerOb[len-1].substring(12,engineerOb[len-1].length-2)+'">'+engineerOb[len-1].substring(12,engineerOb[len-1].length-2)+'</option>';
    var Str = str2+str3;
    $("select[name='staffName_sel']").append(Str);

    /*添加修改交通方式填充*/
    ["请选择", "飞机", "铁路", "自驾", "打车", "其他"].map(function(v, i, arr){
        $("select[name='TransportTool_sel']").append("<option value='"+v+"'>"+v+"</option>");
    });

    /*添加修改住宿费用填充 HotelExpense_sel*/
    _.range(0, 1050, 50).map(function(v, i, arr){
        if(i == 0){
            $("select[name='HotelExpense_sel']").append("<option value='请选择住宿费用'>请选择住宿费用</option>");
        }
        $("select[name='HotelExpense_sel']").append("<option value='"+v+"'>"+v+"元</option>");
    });
});

 /****************** 跳页 **********************/

function FistPage(arg) {
        window.location.href = arg + "1";
}
function UpPage(arg) {
        window.location.href = arg ;
}
function NextPage(arg) {
        window.location.href = arg ;
}
function PageJump(arg) {
    var jumpNumber = document.getElementById("jumpNumber").value;
    if (jumpNumber == null || jumpNumber == 0) {
        jumpNumber = $('#currentPage').html();
    } else if (jumpNumber > parseInt($('#allPage').html())) {
        jumpNumber = $('#allPage').html();
    }
        window.location.href = arg + jumpNumber;
}
function LastPage(arg) {
    var jumpNumber = parseInt($('#allPage').html());
        window.location.href = arg + jumpNumber;
}
//数组去重
Array.prototype.unique =function(){
     var res = [];
     var json = {};
     for(var i = 0; i < this.length; i++){
      if(!json[this[i]]){
       res.push(this[i]);
       json[this[i]] = 1;
      }
     }
    return res;
};

// 滚动响应     
$(function(){
    $(window).scrollTop(0);
    var top0 = $("#main-box").offset().top;
    var left0 = $("#main-box").offset().left-1;
    $(window).scroll(function(){
        var s = $(window).scrollTop();
        if(s>=top0){
            // var left1 = $(".flex-right-con").offset().left - 300
            $(".left-fixed-div").css({'position':'fixed','left':left0+"px",'top':'0'});
        }else{
            $(".left-fixed-div").css({'position':'static'});
        }
    });
    $(".cover-all").fadeOut(30);
    $(".cover-all-img").fadeOut(30);
    $("input[name='date_sel'][value='dateRange']").prop("checked","checked");
}); 

/*原来script标签内 end*/