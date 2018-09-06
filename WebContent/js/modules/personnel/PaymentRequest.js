/*!
 * Bootstrap v3.3.7 (http://getbootstrap.com)
 * Copyright 2011-2016 Twitter, Inc.
 * Licensed under the MIT license
 */
if("undefined"==typeof jQuery)throw new Error("Bootstrap's JavaScript requires jQuery");+function(a){"use strict";var b=a.fn.jquery.split(" ")[0].split(".");if(b[0]<2&&b[1]<9||1==b[0]&&9==b[1]&&b[2]<1||b[0]>3)throw new Error("Bootstrap's JavaScript requires jQuery version 1.9.1 or higher, but lower than version 4")}(jQuery),+function(a){"use strict";function b(){var a=document.createElement("bootstrap"),b={WebkitTransition:"webkitTransitionEnd",MozTransition:"transitionend",OTransition:"oTransitionEnd otransitionend",transition:"transitionend"};for(var c in b)if(void 0!==a.style[c])return{end:b[c]};return!1}a.fn.emulateTransitionEnd=function(b){var c=!1,d=this;a(this).one("bsTransitionEnd",function(){c=!0});var e=function(){c||a(d).trigger(a.support.transition.end)};return setTimeout(e,b),this},a(function(){a.support.transition=b(),a.support.transition&&(a.event.special.bsTransitionEnd={bindType:a.support.transition.end,delegateType:a.support.transition.end,handle:function(b){if(a(b.target).is(this))return b.handleObj.handler.apply(this,arguments)}})})}(jQuery),+function(a){"use strict";function b(b){return this.each(function(){var c=a(this),e=c.data("bs.alert");e||c.data("bs.alert",e=new d(this)),"string"==typeof b&&e[b].call(c)})}var c='[data-dismiss="alert"]',d=function(b){a(b).on("click",c,this.close)};d.VERSION="3.3.7",d.TRANSITION_DURATION=150,d.prototype.close=function(b){function c(){g.detach().trigger("closed.bs.alert").remove()}var e=a(this),f=e.attr("data-target");f||(f=e.attr("href"),f=f&&f.replace(/.*(?=#[^\s]*$)/,""));var g=a("#"===f?[]:f);b&&b.preventDefault(),g.length||(g=e.closest(".alert")),g.trigger(b=a.Event("close.bs.alert")),b.isDefaultPrevented()||(g.removeClass("in"),a.support.transition&&g.hasClass("fade")?g.one("bsTransitionEnd",c).emulateTransitionEnd(d.TRANSITION_DURATION):c())};var e=a.fn.alert;a.fn.alert=b,a.fn.alert.Constructor=d,a.fn.alert.noConflict=function(){return a.fn.alert=e,this},a(document).on("click.bs.alert.data-api",c,d.prototype.close)}(jQuery),+function(a){"use strict";function b(b){return this.each(function(){var d=a(this),e=d.data("bs.button"),f="object"==typeof b&&b;e||d.data("bs.button",e=new c(this,f)),"toggle"==b?e.toggle():b&&e.setState(b)})}var c=function(b,d){this.$element=a(b),this.options=a.extend({},c.DEFAULTS,d),this.isLoading=!1};c.VERSION="3.3.7",c.DEFAULTS={loadingText:"loading..."},c.prototype.setState=function(b){var c="disabled",d=this.$element,e=d.is("input")?"val":"html",f=d.data();b+="Text",null==f.resetText&&d.data("resetText",d[e]()),setTimeout(a.proxy(function(){d[e](null==f[b]?this.options[b]:f[b]),"loadingText"==b?(this.isLoading=!0,d.addClass(c).attr(c,c).prop(c,!0)):this.isLoading&&(this.isLoading=!1,d.removeClass(c).removeAttr(c).prop(c,!1))},this),0)},c.prototype.toggle=function(){var a=!0,b=this.$element.closest('[data-toggle="buttons"]');if(b.length){var c=this.$element.find("input");"radio"==c.prop("type")?(c.prop("checked")&&(a=!1),b.find(".active").removeClass("active"),this.$element.addClass("active")):"checkbox"==c.prop("type")&&(c.prop("checked")!==this.$element.hasClass("active")&&(a=!1),this.$element.toggleClass("active")),c.prop("checked",this.$element.hasClass("active")),a&&c.trigger("change")}else this.$element.attr("aria-pressed",!this.$element.hasClass("active")),this.$element.toggleClass("active")};var d=a.fn.button;a.fn.button=b,a.fn.button.Constructor=c,a.fn.button.noConflict=function(){return a.fn.button=d,this},a(document).on("click.bs.button.data-api",'[data-toggle^="button"]',function(c){var d=a(c.target).closest(".btn");b.call(d,"toggle"),a(c.target).is('input[type="radio"], input[type="checkbox"]')||(c.preventDefault(),d.is("input,button")?d.trigger("focus"):d.find("input:visible,button:visible").first().trigger("focus"))}).on("focus.bs.button.data-api blur.bs.button.data-api",'[data-toggle^="button"]',function(b){a(b.target).closest(".btn").toggleClass("focus",/^focus(in)?$/.test(b.type))})}(jQuery),+function(a){"use strict";function b(b){return this.each(function(){var d=a(this),e=d.data("bs.carousel"),f=a.extend({},c.DEFAULTS,d.data(),"object"==typeof b&&b),g="string"==typeof b?b:f.slide;e||d.data("bs.carousel",e=new c(this,f)),"number"==typeof b?e.to(b):g?e[g]():f.interval&&e.pause().cycle()})}var c=function(b,c){this.$element=a(b),this.$indicators=this.$element.find(".carousel-indicators"),this.options=c,this.paused=null,this.sliding=null,this.interval=null,this.$active=null,this.$items=null,this.options.keyboard&&this.$element.on("keydown.bs.carousel",a.proxy(this.keydown,this)),"hover"==this.options.pause&&!("ontouchstart"in document.documentElement)&&this.$element.on("mouseenter.bs.carousel",a.proxy(this.pause,this)).on("mouseleave.bs.carousel",a.proxy(this.cycle,this))};c.VERSION="3.3.7",c.TRANSITION_DURATION=600,c.DEFAULTS={interval:5e3,pause:"hover",wrap:!0,keyboard:!0},c.prototype.keydown=function(a){if(!/input|textarea/i.test(a.target.tagName)){switch(a.which){case 37:this.prev();break;case 39:this.next();break;default:return}a.preventDefault()}},c.prototype.cycle=function(b){return b||(this.paused=!1),this.interval&&clearInterval(this.interval),this.options.interval&&!this.paused&&(this.interval=setInterval(a.proxy(this.next,this),this.options.interval)),this},c.prototype.getItemIndex=function(a){return this.$items=a.parent().children(".item"),this.$items.index(a||this.$active)},c.prototype.getItemForDirection=function(a,b){var c=this.getItemIndex(b),d="prev"==a&&0===c||"next"==a&&c==this.$items.length-1;if(d&&!this.options.wrap)return b;var e="prev"==a?-1:1,f=(c+e)%this.$items.length;return this.$items.eq(f)},c.prototype.to=function(a){var b=this,c=this.getItemIndex(this.$active=this.$element.find(".item.active"));if(!(a>this.$items.length-1||a<0))return this.sliding?this.$element.one("slid.bs.carousel",function(){b.to(a)}):c==a?this.pause().cycle():this.slide(a>c?"next":"prev",this.$items.eq(a))},c.prototype.pause=function(b){return b||(this.paused=!0),this.$element.find(".next, .prev").length&&a.support.transition&&(this.$element.trigger(a.support.transition.end),this.cycle(!0)),this.interval=clearInterval(this.interval),this},c.prototype.next=function(){if(!this.sliding)return this.slide("next")},c.prototype.prev=function(){if(!this.sliding)return this.slide("prev")},c.prototype.slide=function(b,d){var e=this.$element.find(".item.active"),f=d||this.getItemForDirection(b,e),g=this.interval,h="next"==b?"left":"right",i=this;if(f.hasClass("active"))return this.sliding=!1;var j=f[0],k=a.Event("slide.bs.carousel",{relatedTarget:j,direction:h});if(this.$element.trigger(k),!k.isDefaultPrevented()){if(this.sliding=!0,g&&this.pause(),this.$indicators.length){this.$indicators.find(".active").removeClass("active");var l=a(this.$indicators.children()[this.getItemIndex(f)]);l&&l.addClass("active")}var m=a.Event("slid.bs.carousel",{relatedTarget:j,direction:h});return a.support.transition&&this.$element.hasClass("slide")?(f.addClass(b),f[0].offsetWidth,e.addClass(h),f.addClass(h),e.one("bsTransitionEnd",function(){f.removeClass([b,h].join(" ")).addClass("active"),e.removeClass(["active",h].join(" ")),i.sliding=!1,setTimeout(function(){i.$element.trigger(m)},0)}).emulateTransitionEnd(c.TRANSITION_DURATION)):(e.removeClass("active"),f.addClass("active"),this.sliding=!1,this.$element.trigger(m)),g&&this.cycle(),this}};var d=a.fn.carousel;a.fn.carousel=b,a.fn.carousel.Constructor=c,a.fn.carousel.noConflict=function(){return a.fn.carousel=d,this};var e=function(c){var d,e=a(this),f=a(e.attr("data-target")||(d=e.attr("href"))&&d.replace(/.*(?=#[^\s]+$)/,""));if(f.hasClass("carousel")){var g=a.extend({},f.data(),e.data()),h=e.attr("data-slide-to");h&&(g.interval=!1),b.call(f,g),h&&f.data("bs.carousel").to(h),c.preventDefault()}};a(document).on("click.bs.carousel.data-api","[data-slide]",e).on("click.bs.carousel.data-api","[data-slide-to]",e),a(window).on("load",function(){a('[data-ride="carousel"]').each(function(){var c=a(this);b.call(c,c.data())})})}(jQuery),+function(a){"use strict";function b(b){var c,d=b.attr("data-target")||(c=b.attr("href"))&&c.replace(/.*(?=#[^\s]+$)/,"");return a(d)}function c(b){return this.each(function(){var c=a(this),e=c.data("bs.collapse"),f=a.extend({},d.DEFAULTS,c.data(),"object"==typeof b&&b);!e&&f.toggle&&/show|hide/.test(b)&&(f.toggle=!1),e||c.data("bs.collapse",e=new d(this,f)),"string"==typeof b&&e[b]()})}var d=function(b,c){this.$element=a(b),this.options=a.extend({},d.DEFAULTS,c),this.$trigger=a('[data-toggle="collapse"][href="#'+b.id+'"],[data-toggle="collapse"][data-target="#'+b.id+'"]'),this.transitioning=null,this.options.parent?this.$parent=this.getParent():this.addAriaAndCollapsedClass(this.$element,this.$trigger),this.options.toggle&&this.toggle()};d.VERSION="3.3.7",d.TRANSITION_DURATION=350,d.DEFAULTS={toggle:!0},d.prototype.dimension=function(){var a=this.$element.hasClass("width");return a?"width":"height"},d.prototype.show=function(){if(!this.transitioning&&!this.$element.hasClass("in")){var b,e=this.$parent&&this.$parent.children(".panel").children(".in, .collapsing");if(!(e&&e.length&&(b=e.data("bs.collapse"),b&&b.transitioning))){var f=a.Event("show.bs.collapse");if(this.$element.trigger(f),!f.isDefaultPrevented()){e&&e.length&&(c.call(e,"hide"),b||e.data("bs.collapse",null));var g=this.dimension();this.$element.removeClass("collapse").addClass("collapsing")[g](0).attr("aria-expanded",!0),this.$trigger.removeClass("collapsed").attr("aria-expanded",!0),this.transitioning=1;var h=function(){this.$element.removeClass("collapsing").addClass("collapse in")[g](""),this.transitioning=0,this.$element.trigger("shown.bs.collapse")};if(!a.support.transition)return h.call(this);var i=a.camelCase(["scroll",g].join("-"));this.$element.one("bsTransitionEnd",a.proxy(h,this)).emulateTransitionEnd(d.TRANSITION_DURATION)[g](this.$element[0][i])}}}},d.prototype.hide=function(){if(!this.transitioning&&this.$element.hasClass("in")){var b=a.Event("hide.bs.collapse");if(this.$element.trigger(b),!b.isDefaultPrevented()){var c=this.dimension();this.$element[c](this.$element[c]())[0].offsetHeight,this.$element.addClass("collapsing").removeClass("collapse in").attr("aria-expanded",!1),this.$trigger.addClass("collapsed").attr("aria-expanded",!1),this.transitioning=1;var e=function(){this.transitioning=0,this.$element.removeClass("collapsing").addClass("collapse").trigger("hidden.bs.collapse")};return a.support.transition?void this.$element[c](0).one("bsTransitionEnd",a.proxy(e,this)).emulateTransitionEnd(d.TRANSITION_DURATION):e.call(this)}}},d.prototype.toggle=function(){this[this.$element.hasClass("in")?"hide":"show"]()},d.prototype.getParent=function(){return a(this.options.parent).find('[data-toggle="collapse"][data-parent="'+this.options.parent+'"]').each(a.proxy(function(c,d){var e=a(d);this.addAriaAndCollapsedClass(b(e),e)},this)).end()},d.prototype.addAriaAndCollapsedClass=function(a,b){var c=a.hasClass("in");a.attr("aria-expanded",c),b.toggleClass("collapsed",!c).attr("aria-expanded",c)};var e=a.fn.collapse;a.fn.collapse=c,a.fn.collapse.Constructor=d,a.fn.collapse.noConflict=function(){return a.fn.collapse=e,this},a(document).on("click.bs.collapse.data-api",'[data-toggle="collapse"]',function(d){var e=a(this);e.attr("data-target")||d.preventDefault();var f=b(e),g=f.data("bs.collapse"),h=g?"toggle":e.data();c.call(f,h)})}(jQuery),+function(a){"use strict";function b(b){var c=b.attr("data-target");c||(c=b.attr("href"),c=c&&/#[A-Za-z]/.test(c)&&c.replace(/.*(?=#[^\s]*$)/,""));var d=c&&a(c);return d&&d.length?d:b.parent()}function c(c){c&&3===c.which||(a(e).remove(),a(f).each(function(){var d=a(this),e=b(d),f={relatedTarget:this};e.hasClass("open")&&(c&&"click"==c.type&&/input|textarea/i.test(c.target.tagName)&&a.contains(e[0],c.target)||(e.trigger(c=a.Event("hide.bs.dropdown",f)),c.isDefaultPrevented()||(d.attr("aria-expanded","false"),e.removeClass("open").trigger(a.Event("hidden.bs.dropdown",f)))))}))}function d(b){return this.each(function(){var c=a(this),d=c.data("bs.dropdown");d||c.data("bs.dropdown",d=new g(this)),"string"==typeof b&&d[b].call(c)})}var e=".dropdown-backdrop",f='[data-toggle="dropdown"]',g=function(b){a(b).on("click.bs.dropdown",this.toggle)};g.VERSION="3.3.7",g.prototype.toggle=function(d){var e=a(this);if(!e.is(".disabled, :disabled")){var f=b(e),g=f.hasClass("open");if(c(),!g){"ontouchstart"in document.documentElement&&!f.closest(".navbar-nav").length&&a(document.createElement("div")).addClass("dropdown-backdrop").insertAfter(a(this)).on("click",c);var h={relatedTarget:this};if(f.trigger(d=a.Event("show.bs.dropdown",h)),d.isDefaultPrevented())return;e.trigger("focus").attr("aria-expanded","true"),f.toggleClass("open").trigger(a.Event("shown.bs.dropdown",h))}return!1}},g.prototype.keydown=function(c){if(/(38|40|27|32)/.test(c.which)&&!/input|textarea/i.test(c.target.tagName)){var d=a(this);if(c.preventDefault(),c.stopPropagation(),!d.is(".disabled, :disabled")){var e=b(d),g=e.hasClass("open");if(!g&&27!=c.which||g&&27==c.which)return 27==c.which&&e.find(f).trigger("focus"),d.trigger("click");var h=" li:not(.disabled):visible a",i=e.find(".dropdown-menu"+h);if(i.length){var j=i.index(c.target);38==c.which&&j>0&&j--,40==c.which&&j<i.length-1&&j++,~j||(j=0),i.eq(j).trigger("focus")}}}};var h=a.fn.dropdown;a.fn.dropdown=d,a.fn.dropdown.Constructor=g,a.fn.dropdown.noConflict=function(){return a.fn.dropdown=h,this},a(document).on("click.bs.dropdown.data-api",c).on("click.bs.dropdown.data-api",".dropdown form",function(a){a.stopPropagation()}).on("click.bs.dropdown.data-api",f,g.prototype.toggle).on("keydown.bs.dropdown.data-api",f,g.prototype.keydown).on("keydown.bs.dropdown.data-api",".dropdown-menu",g.prototype.keydown)}(jQuery),+function(a){"use strict";function b(b,d){return this.each(function(){var e=a(this),f=e.data("bs.modal"),g=a.extend({},c.DEFAULTS,e.data(),"object"==typeof b&&b);f||e.data("bs.modal",f=new c(this,g)),"string"==typeof b?f[b](d):g.show&&f.show(d)})}var c=function(b,c){this.options=c,this.$body=a(document.body),this.$element=a(b),this.$dialog=this.$element.find(".modal-dialog"),this.$backdrop=null,this.isShown=null,this.originalBodyPad=null,this.scrollbarWidth=0,this.ignoreBackdropClick=!1,this.options.remote&&this.$element.find(".modal-content").load(this.options.remote,a.proxy(function(){this.$element.trigger("loaded.bs.modal")},this))};c.VERSION="3.3.7",c.TRANSITION_DURATION=300,c.BACKDROP_TRANSITION_DURATION=150,c.DEFAULTS={backdrop:!0,keyboard:!0,show:!0},c.prototype.toggle=function(a){return this.isShown?this.hide():this.show(a)},c.prototype.show=function(b){var d=this,e=a.Event("show.bs.modal",{relatedTarget:b});this.$element.trigger(e),this.isShown||e.isDefaultPrevented()||(this.isShown=!0,this.checkScrollbar(),this.setScrollbar(),this.$body.addClass("modal-open"),this.escape(),this.resize(),this.$element.on("click.dismiss.bs.modal",'[data-dismiss="modal"]',a.proxy(this.hide,this)),this.$dialog.on("mousedown.dismiss.bs.modal",function(){d.$element.one("mouseup.dismiss.bs.modal",function(b){a(b.target).is(d.$element)&&(d.ignoreBackdropClick=!0)})}),this.backdrop(function(){var e=a.support.transition&&d.$element.hasClass("fade");d.$element.parent().length||d.$element.appendTo(d.$body),d.$element.show().scrollTop(0),d.adjustDialog(),e&&d.$element[0].offsetWidth,d.$element.addClass("in"),d.enforceFocus();var f=a.Event("shown.bs.modal",{relatedTarget:b});e?d.$dialog.one("bsTransitionEnd",function(){d.$element.trigger("focus").trigger(f)}).emulateTransitionEnd(c.TRANSITION_DURATION):d.$element.trigger("focus").trigger(f)}))},c.prototype.hide=function(b){b&&b.preventDefault(),b=a.Event("hide.bs.modal"),this.$element.trigger(b),this.isShown&&!b.isDefaultPrevented()&&(this.isShown=!1,this.escape(),this.resize(),a(document).off("focusin.bs.modal"),this.$element.removeClass("in").off("click.dismiss.bs.modal").off("mouseup.dismiss.bs.modal"),this.$dialog.off("mousedown.dismiss.bs.modal"),a.support.transition&&this.$element.hasClass("fade")?this.$element.one("bsTransitionEnd",a.proxy(this.hideModal,this)).emulateTransitionEnd(c.TRANSITION_DURATION):this.hideModal())},c.prototype.enforceFocus=function(){a(document).off("focusin.bs.modal").on("focusin.bs.modal",a.proxy(function(a){document===a.target||this.$element[0]===a.target||this.$element.has(a.target).length||this.$element.trigger("focus")},this))},c.prototype.escape=function(){this.isShown&&this.options.keyboard?this.$element.on("keydown.dismiss.bs.modal",a.proxy(function(a){27==a.which&&this.hide()},this)):this.isShown||this.$element.off("keydown.dismiss.bs.modal")},c.prototype.resize=function(){this.isShown?a(window).on("resize.bs.modal",a.proxy(this.handleUpdate,this)):a(window).off("resize.bs.modal")},c.prototype.hideModal=function(){var a=this;this.$element.hide(),this.backdrop(function(){a.$body.removeClass("modal-open"),a.resetAdjustments(),a.resetScrollbar(),a.$element.trigger("hidden.bs.modal")})},c.prototype.removeBackdrop=function(){this.$backdrop&&this.$backdrop.remove(),this.$backdrop=null},c.prototype.backdrop=function(b){var d=this,e=this.$element.hasClass("fade")?"fade":"";if(this.isShown&&this.options.backdrop){var f=a.support.transition&&e;if(this.$backdrop=a(document.createElement("div")).addClass("modal-backdrop "+e).appendTo(this.$body),this.$element.on("click.dismiss.bs.modal",a.proxy(function(a){return this.ignoreBackdropClick?void(this.ignoreBackdropClick=!1):void(a.target===a.currentTarget&&("static"==this.options.backdrop?this.$element[0].focus():this.hide()))},this)),f&&this.$backdrop[0].offsetWidth,this.$backdrop.addClass("in"),!b)return;f?this.$backdrop.one("bsTransitionEnd",b).emulateTransitionEnd(c.BACKDROP_TRANSITION_DURATION):b()}else if(!this.isShown&&this.$backdrop){this.$backdrop.removeClass("in");var g=function(){d.removeBackdrop(),b&&b()};a.support.transition&&this.$element.hasClass("fade")?this.$backdrop.one("bsTransitionEnd",g).emulateTransitionEnd(c.BACKDROP_TRANSITION_DURATION):g()}else b&&b()},c.prototype.handleUpdate=function(){this.adjustDialog()},c.prototype.adjustDialog=function(){var a=this.$element[0].scrollHeight>document.documentElement.clientHeight;this.$element.css({paddingLeft:!this.bodyIsOverflowing&&a?this.scrollbarWidth:"",paddingRight:this.bodyIsOverflowing&&!a?this.scrollbarWidth:""})},c.prototype.resetAdjustments=function(){this.$element.css({paddingLeft:"",paddingRight:""})},c.prototype.checkScrollbar=function(){var a=window.innerWidth;if(!a){var b=document.documentElement.getBoundingClientRect();a=b.right-Math.abs(b.left)}this.bodyIsOverflowing=document.body.clientWidth<a,this.scrollbarWidth=this.measureScrollbar()},c.prototype.setScrollbar=function(){var a=parseInt(this.$body.css("padding-right")||0,10);this.originalBodyPad=document.body.style.paddingRight||"",this.bodyIsOverflowing&&this.$body.css("padding-right",a+this.scrollbarWidth)},c.prototype.resetScrollbar=function(){this.$body.css("padding-right",this.originalBodyPad)},c.prototype.measureScrollbar=function(){var a=document.createElement("div");a.className="modal-scrollbar-measure",this.$body.append(a);var b=a.offsetWidth-a.clientWidth;return this.$body[0].removeChild(a),b};var d=a.fn.modal;a.fn.modal=b,a.fn.modal.Constructor=c,a.fn.modal.noConflict=function(){return a.fn.modal=d,this},a(document).on("click.bs.modal.data-api",'[data-toggle="modal"]',function(c){var d=a(this),e=d.attr("href"),f=a(d.attr("data-target")||e&&e.replace(/.*(?=#[^\s]+$)/,"")),g=f.data("bs.modal")?"toggle":a.extend({remote:!/#/.test(e)&&e},f.data(),d.data());d.is("a")&&c.preventDefault(),f.one("show.bs.modal",function(a){a.isDefaultPrevented()||f.one("hidden.bs.modal",function(){d.is(":visible")&&d.trigger("focus")})}),b.call(f,g,this)})}(jQuery),+function(a){"use strict";function b(b){return this.each(function(){var d=a(this),e=d.data("bs.tooltip"),f="object"==typeof b&&b;!e&&/destroy|hide/.test(b)||(e||d.data("bs.tooltip",e=new c(this,f)),"string"==typeof b&&e[b]())})}var c=function(a,b){this.type=null,this.options=null,this.enabled=null,this.timeout=null,this.hoverState=null,this.$element=null,this.inState=null,this.init("tooltip",a,b)};c.VERSION="3.3.7",c.TRANSITION_DURATION=150,c.DEFAULTS={animation:!0,placement:"top",selector:!1,template:'<div class="tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>',trigger:"hover focus",title:"",delay:0,html:!1,container:!1,viewport:{selector:"body",padding:0}},c.prototype.init=function(b,c,d){if(this.enabled=!0,this.type=b,this.$element=a(c),this.options=this.getOptions(d),this.$viewport=this.options.viewport&&a(a.isFunction(this.options.viewport)?this.options.viewport.call(this,this.$element):this.options.viewport.selector||this.options.viewport),this.inState={click:!1,hover:!1,focus:!1},this.$element[0]instanceof document.constructor&&!this.options.selector)throw new Error("`selector` option must be specified when initializing "+this.type+" on the window.document object!");for(var e=this.options.trigger.split(" "),f=e.length;f--;){var g=e[f];if("click"==g)this.$element.on("click."+this.type,this.options.selector,a.proxy(this.toggle,this));else if("manual"!=g){var h="hover"==g?"mouseenter":"focusin",i="hover"==g?"mouseleave":"focusout";this.$element.on(h+"."+this.type,this.options.selector,a.proxy(this.enter,this)),this.$element.on(i+"."+this.type,this.options.selector,a.proxy(this.leave,this))}}this.options.selector?this._options=a.extend({},this.options,{trigger:"manual",selector:""}):this.fixTitle()},c.prototype.getDefaults=function(){return c.DEFAULTS},c.prototype.getOptions=function(b){return b=a.extend({},this.getDefaults(),this.$element.data(),b),b.delay&&"number"==typeof b.delay&&(b.delay={show:b.delay,hide:b.delay}),b},c.prototype.getDelegateOptions=function(){var b={},c=this.getDefaults();return this._options&&a.each(this._options,function(a,d){c[a]!=d&&(b[a]=d)}),b},c.prototype.enter=function(b){var c=b instanceof this.constructor?b:a(b.currentTarget).data("bs."+this.type);return c||(c=new this.constructor(b.currentTarget,this.getDelegateOptions()),a(b.currentTarget).data("bs."+this.type,c)),b instanceof a.Event&&(c.inState["focusin"==b.type?"focus":"hover"]=!0),c.tip().hasClass("in")||"in"==c.hoverState?void(c.hoverState="in"):(clearTimeout(c.timeout),c.hoverState="in",c.options.delay&&c.options.delay.show?void(c.timeout=setTimeout(function(){"in"==c.hoverState&&c.show()},c.options.delay.show)):c.show())},c.prototype.isInStateTrue=function(){for(var a in this.inState)if(this.inState[a])return!0;return!1},c.prototype.leave=function(b){var c=b instanceof this.constructor?b:a(b.currentTarget).data("bs."+this.type);if(c||(c=new this.constructor(b.currentTarget,this.getDelegateOptions()),a(b.currentTarget).data("bs."+this.type,c)),b instanceof a.Event&&(c.inState["focusout"==b.type?"focus":"hover"]=!1),!c.isInStateTrue())return clearTimeout(c.timeout),c.hoverState="out",c.options.delay&&c.options.delay.hide?void(c.timeout=setTimeout(function(){"out"==c.hoverState&&c.hide()},c.options.delay.hide)):c.hide()},c.prototype.show=function(){var b=a.Event("show.bs."+this.type);if(this.hasContent()&&this.enabled){this.$element.trigger(b);var d=a.contains(this.$element[0].ownerDocument.documentElement,this.$element[0]);if(b.isDefaultPrevented()||!d)return;var e=this,f=this.tip(),g=this.getUID(this.type);this.setContent(),f.attr("id",g),this.$element.attr("aria-describedby",g),this.options.animation&&f.addClass("fade");var h="function"==typeof this.options.placement?this.options.placement.call(this,f[0],this.$element[0]):this.options.placement,i=/\s?auto?\s?/i,j=i.test(h);j&&(h=h.replace(i,"")||"top"),f.detach().css({top:0,left:0,display:"block"}).addClass(h).data("bs."+this.type,this),this.options.container?f.appendTo(this.options.container):f.insertAfter(this.$element),this.$element.trigger("inserted.bs."+this.type);var k=this.getPosition(),l=f[0].offsetWidth,m=f[0].offsetHeight;if(j){var n=h,o=this.getPosition(this.$viewport);h="bottom"==h&&k.bottom+m>o.bottom?"top":"top"==h&&k.top-m<o.top?"bottom":"right"==h&&k.right+l>o.width?"left":"left"==h&&k.left-l<o.left?"right":h,f.removeClass(n).addClass(h)}var p=this.getCalculatedOffset(h,k,l,m);this.applyPlacement(p,h);var q=function(){var a=e.hoverState;e.$element.trigger("shown.bs."+e.type),e.hoverState=null,"out"==a&&e.leave(e)};a.support.transition&&this.$tip.hasClass("fade")?f.one("bsTransitionEnd",q).emulateTransitionEnd(c.TRANSITION_DURATION):q()}},c.prototype.applyPlacement=function(b,c){var d=this.tip(),e=d[0].offsetWidth,f=d[0].offsetHeight,g=parseInt(d.css("margin-top"),10),h=parseInt(d.css("margin-left"),10);isNaN(g)&&(g=0),isNaN(h)&&(h=0),b.top+=g,b.left+=h,a.offset.setOffset(d[0],a.extend({using:function(a){d.css({top:Math.round(a.top),left:Math.round(a.left)})}},b),0),d.addClass("in");var i=d[0].offsetWidth,j=d[0].offsetHeight;"top"==c&&j!=f&&(b.top=b.top+f-j);var k=this.getViewportAdjustedDelta(c,b,i,j);k.left?b.left+=k.left:b.top+=k.top;var l=/top|bottom/.test(c),m=l?2*k.left-e+i:2*k.top-f+j,n=l?"offsetWidth":"offsetHeight";d.offset(b),this.replaceArrow(m,d[0][n],l)},c.prototype.replaceArrow=function(a,b,c){this.arrow().css(c?"left":"top",50*(1-a/b)+"%").css(c?"top":"left","")},c.prototype.setContent=function(){var a=this.tip(),b=this.getTitle();a.find(".tooltip-inner")[this.options.html?"html":"text"](b),a.removeClass("fade in top bottom left right")},c.prototype.hide=function(b){function d(){"in"!=e.hoverState&&f.detach(),e.$element&&e.$element.removeAttr("aria-describedby").trigger("hidden.bs."+e.type),b&&b()}var e=this,f=a(this.$tip),g=a.Event("hide.bs."+this.type);if(this.$element.trigger(g),!g.isDefaultPrevented())return f.removeClass("in"),a.support.transition&&f.hasClass("fade")?f.one("bsTransitionEnd",d).emulateTransitionEnd(c.TRANSITION_DURATION):d(),this.hoverState=null,this},c.prototype.fixTitle=function(){var a=this.$element;(a.attr("title")||"string"!=typeof a.attr("data-original-title"))&&a.attr("data-original-title",a.attr("title")||"").attr("title","")},c.prototype.hasContent=function(){return this.getTitle()},c.prototype.getPosition=function(b){b=b||this.$element;var c=b[0],d="BODY"==c.tagName,e=c.getBoundingClientRect();null==e.width&&(e=a.extend({},e,{width:e.right-e.left,height:e.bottom-e.top}));var f=window.SVGElement&&c instanceof window.SVGElement,g=d?{top:0,left:0}:f?null:b.offset(),h={scroll:d?document.documentElement.scrollTop||document.body.scrollTop:b.scrollTop()},i=d?{width:a(window).width(),height:a(window).height()}:null;return a.extend({},e,h,i,g)},c.prototype.getCalculatedOffset=function(a,b,c,d){return"bottom"==a?{top:b.top+b.height,left:b.left+b.width/2-c/2}:"top"==a?{top:b.top-d,left:b.left+b.width/2-c/2}:"left"==a?{top:b.top+b.height/2-d/2,left:b.left-c}:{top:b.top+b.height/2-d/2,left:b.left+b.width}},c.prototype.getViewportAdjustedDelta=function(a,b,c,d){var e={top:0,left:0};if(!this.$viewport)return e;var f=this.options.viewport&&this.options.viewport.padding||0,g=this.getPosition(this.$viewport);if(/right|left/.test(a)){var h=b.top-f-g.scroll,i=b.top+f-g.scroll+d;h<g.top?e.top=g.top-h:i>g.top+g.height&&(e.top=g.top+g.height-i)}else{var j=b.left-f,k=b.left+f+c;j<g.left?e.left=g.left-j:k>g.right&&(e.left=g.left+g.width-k)}return e},c.prototype.getTitle=function(){var a,b=this.$element,c=this.options;return a=b.attr("data-original-title")||("function"==typeof c.title?c.title.call(b[0]):c.title)},c.prototype.getUID=function(a){do a+=~~(1e6*Math.random());while(document.getElementById(a));return a},c.prototype.tip=function(){if(!this.$tip&&(this.$tip=a(this.options.template),1!=this.$tip.length))throw new Error(this.type+" `template` option must consist of exactly 1 top-level element!");return this.$tip},c.prototype.arrow=function(){return this.$arrow=this.$arrow||this.tip().find(".tooltip-arrow")},c.prototype.enable=function(){this.enabled=!0},c.prototype.disable=function(){this.enabled=!1},c.prototype.toggleEnabled=function(){this.enabled=!this.enabled},c.prototype.toggle=function(b){var c=this;b&&(c=a(b.currentTarget).data("bs."+this.type),c||(c=new this.constructor(b.currentTarget,this.getDelegateOptions()),a(b.currentTarget).data("bs."+this.type,c))),b?(c.inState.click=!c.inState.click,c.isInStateTrue()?c.enter(c):c.leave(c)):c.tip().hasClass("in")?c.leave(c):c.enter(c)},c.prototype.destroy=function(){var a=this;clearTimeout(this.timeout),this.hide(function(){a.$element.off("."+a.type).removeData("bs."+a.type),a.$tip&&a.$tip.detach(),a.$tip=null,a.$arrow=null,a.$viewport=null,a.$element=null})};var d=a.fn.tooltip;a.fn.tooltip=b,a.fn.tooltip.Constructor=c,a.fn.tooltip.noConflict=function(){return a.fn.tooltip=d,this}}(jQuery),+function(a){"use strict";function b(b){return this.each(function(){var d=a(this),e=d.data("bs.popover"),f="object"==typeof b&&b;!e&&/destroy|hide/.test(b)||(e||d.data("bs.popover",e=new c(this,f)),"string"==typeof b&&e[b]())})}var c=function(a,b){this.init("popover",a,b)};if(!a.fn.tooltip)throw new Error("Popover requires tooltip.js");c.VERSION="3.3.7",c.DEFAULTS=a.extend({},a.fn.tooltip.Constructor.DEFAULTS,{placement:"right",trigger:"click",content:"",template:'<div class="popover" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'}),c.prototype=a.extend({},a.fn.tooltip.Constructor.prototype),c.prototype.constructor=c,c.prototype.getDefaults=function(){return c.DEFAULTS},c.prototype.setContent=function(){var a=this.tip(),b=this.getTitle(),c=this.getContent();a.find(".popover-title")[this.options.html?"html":"text"](b),a.find(".popover-content").children().detach().end()[this.options.html?"string"==typeof c?"html":"append":"text"](c),a.removeClass("fade top bottom left right in"),a.find(".popover-title").html()||a.find(".popover-title").hide()},c.prototype.hasContent=function(){return this.getTitle()||this.getContent()},c.prototype.getContent=function(){var a=this.$element,b=this.options;return a.attr("data-content")||("function"==typeof b.content?b.content.call(a[0]):b.content)},c.prototype.arrow=function(){return this.$arrow=this.$arrow||this.tip().find(".arrow")};var d=a.fn.popover;a.fn.popover=b,a.fn.popover.Constructor=c,a.fn.popover.noConflict=function(){return a.fn.popover=d,this}}(jQuery),+function(a){"use strict";function b(c,d){this.$body=a(document.body),this.$scrollElement=a(a(c).is(document.body)?window:c),this.options=a.extend({},b.DEFAULTS,d),this.selector=(this.options.target||"")+" .nav li > a",this.offsets=[],this.targets=[],this.activeTarget=null,this.scrollHeight=0,this.$scrollElement.on("scroll.bs.scrollspy",a.proxy(this.process,this)),this.refresh(),this.process()}function c(c){return this.each(function(){var d=a(this),e=d.data("bs.scrollspy"),f="object"==typeof c&&c;e||d.data("bs.scrollspy",e=new b(this,f)),"string"==typeof c&&e[c]()})}b.VERSION="3.3.7",b.DEFAULTS={offset:10},b.prototype.getScrollHeight=function(){return this.$scrollElement[0].scrollHeight||Math.max(this.$body[0].scrollHeight,document.documentElement.scrollHeight)},b.prototype.refresh=function(){var b=this,c="offset",d=0;this.offsets=[],this.targets=[],this.scrollHeight=this.getScrollHeight(),a.isWindow(this.$scrollElement[0])||(c="position",d=this.$scrollElement.scrollTop()),this.$body.find(this.selector).map(function(){var b=a(this),e=b.data("target")||b.attr("href"),f=/^#./.test(e)&&a(e);return f&&f.length&&f.is(":visible")&&[[f[c]().top+d,e]]||null}).sort(function(a,b){return a[0]-b[0]}).each(function(){b.offsets.push(this[0]),b.targets.push(this[1])})},b.prototype.process=function(){var a,b=this.$scrollElement.scrollTop()+this.options.offset,c=this.getScrollHeight(),d=this.options.offset+c-this.$scrollElement.height(),e=this.offsets,f=this.targets,g=this.activeTarget;if(this.scrollHeight!=c&&this.refresh(),b>=d)return g!=(a=f[f.length-1])&&this.activate(a);if(g&&b<e[0])return this.activeTarget=null,this.clear();for(a=e.length;a--;)g!=f[a]&&b>=e[a]&&(void 0===e[a+1]||b<e[a+1])&&this.activate(f[a])},b.prototype.activate=function(b){
this.activeTarget=b,this.clear();var c=this.selector+'[data-target="'+b+'"],'+this.selector+'[href="'+b+'"]',d=a(c).parents("li").addClass("active");d.parent(".dropdown-menu").length&&(d=d.closest("li.dropdown").addClass("active")),d.trigger("activate.bs.scrollspy")},b.prototype.clear=function(){a(this.selector).parentsUntil(this.options.target,".active").removeClass("active")};var d=a.fn.scrollspy;a.fn.scrollspy=c,a.fn.scrollspy.Constructor=b,a.fn.scrollspy.noConflict=function(){return a.fn.scrollspy=d,this},a(window).on("load.bs.scrollspy.data-api",function(){a('[data-spy="scroll"]').each(function(){var b=a(this);c.call(b,b.data())})})}(jQuery),+function(a){"use strict";function b(b){return this.each(function(){var d=a(this),e=d.data("bs.tab");e||d.data("bs.tab",e=new c(this)),"string"==typeof b&&e[b]()})}var c=function(b){this.element=a(b)};c.VERSION="3.3.7",c.TRANSITION_DURATION=150,c.prototype.show=function(){var b=this.element,c=b.closest("ul:not(.dropdown-menu)"),d=b.data("target");if(d||(d=b.attr("href"),d=d&&d.replace(/.*(?=#[^\s]*$)/,"")),!b.parent("li").hasClass("active")){var e=c.find(".active:last a"),f=a.Event("hide.bs.tab",{relatedTarget:b[0]}),g=a.Event("show.bs.tab",{relatedTarget:e[0]});if(e.trigger(f),b.trigger(g),!g.isDefaultPrevented()&&!f.isDefaultPrevented()){var h=a(d);this.activate(b.closest("li"),c),this.activate(h,h.parent(),function(){e.trigger({type:"hidden.bs.tab",relatedTarget:b[0]}),b.trigger({type:"shown.bs.tab",relatedTarget:e[0]})})}}},c.prototype.activate=function(b,d,e){function f(){g.removeClass("active").find("> .dropdown-menu > .active").removeClass("active").end().find('[data-toggle="tab"]').attr("aria-expanded",!1),b.addClass("active").find('[data-toggle="tab"]').attr("aria-expanded",!0),h?(b[0].offsetWidth,b.addClass("in")):b.removeClass("fade"),b.parent(".dropdown-menu").length&&b.closest("li.dropdown").addClass("active").end().find('[data-toggle="tab"]').attr("aria-expanded",!0),e&&e()}var g=d.find("> .active"),h=e&&a.support.transition&&(g.length&&g.hasClass("fade")||!!d.find("> .fade").length);g.length&&h?g.one("bsTransitionEnd",f).emulateTransitionEnd(c.TRANSITION_DURATION):f(),g.removeClass("in")};var d=a.fn.tab;a.fn.tab=b,a.fn.tab.Constructor=c,a.fn.tab.noConflict=function(){return a.fn.tab=d,this};var e=function(c){c.preventDefault(),b.call(a(this),"show")};a(document).on("click.bs.tab.data-api",'[data-toggle="tab"]',e).on("click.bs.tab.data-api",'[data-toggle="pill"]',e)}(jQuery),+function(a){"use strict";function b(b){return this.each(function(){var d=a(this),e=d.data("bs.affix"),f="object"==typeof b&&b;e||d.data("bs.affix",e=new c(this,f)),"string"==typeof b&&e[b]()})}var c=function(b,d){this.options=a.extend({},c.DEFAULTS,d),this.$target=a(this.options.target).on("scroll.bs.affix.data-api",a.proxy(this.checkPosition,this)).on("click.bs.affix.data-api",a.proxy(this.checkPositionWithEventLoop,this)),this.$element=a(b),this.affixed=null,this.unpin=null,this.pinnedOffset=null,this.checkPosition()};c.VERSION="3.3.7",c.RESET="affix affix-top affix-bottom",c.DEFAULTS={offset:0,target:window},c.prototype.getState=function(a,b,c,d){var e=this.$target.scrollTop(),f=this.$element.offset(),g=this.$target.height();if(null!=c&&"top"==this.affixed)return e<c&&"top";if("bottom"==this.affixed)return null!=c?!(e+this.unpin<=f.top)&&"bottom":!(e+g<=a-d)&&"bottom";var h=null==this.affixed,i=h?e:f.top,j=h?g:b;return null!=c&&e<=c?"top":null!=d&&i+j>=a-d&&"bottom"},c.prototype.getPinnedOffset=function(){if(this.pinnedOffset)return this.pinnedOffset;this.$element.removeClass(c.RESET).addClass("affix");var a=this.$target.scrollTop(),b=this.$element.offset();return this.pinnedOffset=b.top-a},c.prototype.checkPositionWithEventLoop=function(){setTimeout(a.proxy(this.checkPosition,this),1)},c.prototype.checkPosition=function(){if(this.$element.is(":visible")){var b=this.$element.height(),d=this.options.offset,e=d.top,f=d.bottom,g=Math.max(a(document).height(),a(document.body).height());"object"!=typeof d&&(f=e=d),"function"==typeof e&&(e=d.top(this.$element)),"function"==typeof f&&(f=d.bottom(this.$element));var h=this.getState(g,b,e,f);if(this.affixed!=h){null!=this.unpin&&this.$element.css("top","");var i="affix"+(h?"-"+h:""),j=a.Event(i+".bs.affix");if(this.$element.trigger(j),j.isDefaultPrevented())return;this.affixed=h,this.unpin="bottom"==h?this.getPinnedOffset():null,this.$element.removeClass(c.RESET).addClass(i).trigger(i.replace("affix","affixed")+".bs.affix")}"bottom"==h&&this.$element.offset({top:g-b-f})}};var d=a.fn.affix;a.fn.affix=b,a.fn.affix.Constructor=c,a.fn.affix.noConflict=function(){return a.fn.affix=d,this},a(window).on("load",function(){a('[data-spy="affix"]').each(function(){var c=a(this),d=c.data();d.offset=d.offset||{},null!=d.offsetBottom&&(d.offset.bottom=d.offsetBottom),null!=d.offsetTop&&(d.offset.top=d.offsetTop),b.call(c,d)})})}(jQuery);
// Awesomplete - Lea Verou - MIT license
!function(){function t(t){var e=Array.isArray(t)?{label:t[0],value:t[1]}:"object"==typeof t&&"label"in t&&"value"in t?t:{label:t,value:t};this.label=e.label||e.value,this.value=e.value}function e(t,e,i){for(var n in e){var s=e[n],r=t.input.getAttribute("data-"+n.toLowerCase());"number"==typeof s?t[n]=parseInt(r):!1===s?t[n]=null!==r:s instanceof Function?t[n]=null:t[n]=r,t[n]||0===t[n]||(t[n]=n in i?i[n]:s)}}function i(t,e){return"string"==typeof t?(e||document).querySelector(t):t||null}function n(t,e){return o.call((e||document).querySelectorAll(t))}function s(){n("input.awesomplete").forEach(function(t){new r(t)})}var r=function(t,n){var s=this;Awesomplete.count=(Awesomplete.count||0)+1,this.count=Awesomplete.count,this.isOpened=!1,this.input=i(t),this.input.setAttribute("autocomplete","off"),this.input.setAttribute("aria-owns","awesomplete_list_"+this.count),this.input.setAttribute("role","combobox"),n=n||{},e(this,{minChars:2,maxItems:10,autoFirst:!1,data:r.DATA,filter:r.FILTER_CONTAINS,sort:!1!==n.sort&&r.SORT_BYLENGTH,item:r.ITEM,replace:r.REPLACE},n),this.index=-1,this.container=i.create("div",{className:"awesomplete",around:t}),this.ul=i.create("ul",{hidden:"hidden",role:"listbox",id:"awesomplete_list_"+this.count,inside:this.container}),this.status=i.create("span",{className:"visually-hidden",role:"status","aria-live":"assertive","aria-atomic":!0,inside:this.container,textContent:0!=this.minChars?"Type "+this.minChars+" or more characters for results.":"Begin typing for results."}),this._events={input:{input:this.evaluate.bind(this),blur:this.close.bind(this,{reason:"blur"}),keydown:function(t){var e=t.keyCode;s.opened&&(13===e&&s.selected?(t.preventDefault(),s.select()):27===e?s.close({reason:"esc"}):38!==e&&40!==e||(t.preventDefault(),s[38===e?"previous":"next"]()))}},form:{submit:this.close.bind(this,{reason:"submit"})},ul:{mousedown:function(t){t.preventDefault()},click:function(t){var e=t.target;if(e!==this){for(;e&&!/li/i.test(e.nodeName);)e=e.parentNode;e&&0===t.button&&(t.preventDefault(),s.select(e,t.target))}}}},i.bind(this.input,this._events.input),i.bind(this.input.form,this._events.form),i.bind(this.ul,this._events.ul),this.input.hasAttribute("list")?(this.list="#"+this.input.getAttribute("list"),this.input.removeAttribute("list")):this.list=this.input.getAttribute("data-list")||n.list||[],r.all.push(this)};r.prototype={set list(t){if(Array.isArray(t))this._list=t;else if("string"==typeof t&&t.indexOf(",")>-1)this._list=t.split(/\s*,\s*/);else if((t=i(t))&&t.children){var e=[];o.apply(t.children).forEach(function(t){if(!t.disabled){var i=t.textContent.trim(),n=t.value||i,s=t.label||i;""!==n&&e.push({label:s,value:n})}}),this._list=e}document.activeElement===this.input&&this.evaluate()},get selected(){return this.index>-1},get opened(){return this.isOpened},close:function(t){this.opened&&(this.ul.setAttribute("hidden",""),this.isOpened=!1,this.index=-1,this.status.setAttribute("hidden",""),i.fire(this.input,"awesomplete-close",t||{}))},open:function(){this.ul.removeAttribute("hidden"),this.isOpened=!0,this.status.removeAttribute("hidden"),this.autoFirst&&-1===this.index&&this.goto(0),i.fire(this.input,"awesomplete-open")},destroy:function(){i.unbind(this.input,this._events.input),i.unbind(this.input.form,this._events.form);var t=this.container.parentNode;t.insertBefore(this.input,this.container),t.removeChild(this.container),this.input.removeAttribute("autocomplete"),this.input.removeAttribute("aria-autocomplete");var e=r.all.indexOf(this);-1!==e&&r.all.splice(e,1)},next:function(){var t=this.ul.children.length;this.goto(this.index<t-1?this.index+1:t?0:-1)},previous:function(){var t=this.ul.children.length,e=this.index-1;this.goto(this.selected&&-1!==e?e:t-1)},goto:function(t){var e=this.ul.children;this.selected&&e[this.index].setAttribute("aria-selected","false"),this.index=t,t>-1&&e.length>0&&(e[t].setAttribute("aria-selected","true"),this.status.textContent=e[t].textContent+", list item "+(t+1)+" of "+e.length,this.input.setAttribute("aria-activedescendant",this.ul.id+"_item_"+this.index),this.ul.scrollTop=e[t].offsetTop-this.ul.clientHeight+e[t].clientHeight,i.fire(this.input,"awesomplete-highlight",{text:this.suggestions[this.index]}))},select:function(t,e){if(t?this.index=i.siblingIndex(t):t=this.ul.children[this.index],t){var n=this.suggestions[this.index];i.fire(this.input,"awesomplete-select",{text:n,origin:e||t})&&(this.replace(n),this.close({reason:"select"}),i.fire(this.input,"awesomplete-selectcomplete",{text:n}))}},evaluate:function(){var e=this,i=this.input.value;i.length>=this.minChars&&this._list&&this._list.length>0?(this.index=-1,this.ul.innerHTML="",this.suggestions=this._list.map(function(n){return new t(e.data(n,i))}).filter(function(t){return e.filter(t,i)}),!1!==this.sort&&(this.suggestions=this.suggestions.sort(this.sort)),this.suggestions=this.suggestions.slice(0,this.maxItems),this.suggestions.forEach(function(t,n){e.ul.appendChild(e.item(t,i,n))}),0===this.ul.children.length?(this.status.textContent="No results found",this.close({reason:"nomatches"})):(this.open(),this.status.textContent=this.ul.children.length+" results found")):(this.close({reason:"nomatches"}),this.status.textContent="No results found")}},r.all=[],r.FILTER_CONTAINS=function(t,e){return RegExp(i.regExpEscape(e.trim()),"i").test(t)},r.FILTER_STARTSWITH=function(t,e){return RegExp("^"+i.regExpEscape(e.trim()),"i").test(t)},r.SORT_BYLENGTH=function(t,e){return t.length!==e.length?t.length-e.length:t<e?-1:1},r.ITEM=function(t,e,n){return i.create("li",{innerHTML:""===e.trim()?t:t.replace(RegExp(i.regExpEscape(e.trim()),"gi"),"<mark>$&</mark>"),"aria-selected":"false",id:"awesomplete_list_"+this.count+"_item_"+n})},r.REPLACE=function(t){this.input.value=t.value},r.DATA=function(t){return t},Object.defineProperty(t.prototype=Object.create(String.prototype),"length",{get:function(){return this.label.length}}),t.prototype.toString=t.prototype.valueOf=function(){return""+this.label};var o=Array.prototype.slice;i.create=function(t,e){var n=document.createElement(t);for(var s in e){var r=e[s];if("inside"===s)i(r).appendChild(n);else if("around"===s){var o=i(r);o.parentNode.insertBefore(n,o),n.appendChild(o)}else s in n?n[s]=r:n.setAttribute(s,r)}return n},i.bind=function(t,e){if(t)for(var i in e){var n=e[i];i.split(/\s+/).forEach(function(e){t.addEventListener(e,n)})}},i.unbind=function(t,e){if(t)for(var i in e){var n=e[i];i.split(/\s+/).forEach(function(e){t.removeEventListener(e,n)})}},i.fire=function(t,e,i){var n=document.createEvent("HTMLEvents");n.initEvent(e,!0,!0);for(var s in i)n[s]=i[s];return t.dispatchEvent(n)},i.regExpEscape=function(t){return t.replace(/[-\\^$*+?.()|[\]{}]/g,"\\$&")},i.siblingIndex=function(t){for(var e=0;t=t.previousElementSibling;e++);return e},"undefined"!=typeof Document&&("loading"!==document.readyState?s():document.addEventListener("DOMContentLoaded",s)),r.$=i,r.$$=n,"undefined"!=typeof self&&(self.Awesomplete=r),"object"==typeof module&&module.exports&&(module.exports=r)}();
//# sourceMappingURL=awesomplete.min.js.map

/*variable、function define*/
var addSubmitObj = new Object();
addSubmitObj.Type = null;
addSubmitObj.Department = null;
addSubmitObj.Applicant = null;
addSubmitObj.ExpenseCategory = null;
addSubmitObj.Amount = null;
addSubmitObj.ExpenseItem = null;
addSubmitObj.ExpenseDetails = null;
addSubmitObj.ApplicationDate = null;
addSubmitObj.PayDate = null;
addSubmitObj.Attachment = null;
addSubmitObj.Payee = null;
addSubmitObj.Account = null;
addSubmitObj.DepositBank = null;
addSubmitObj.PaymentRemark = null;
addSubmitObj.StoreName = null;
addSubmitObj.OrderNO = null;
addSubmitObj.Link = null;
addSubmitObj.LinkRemark = null;
var updateSubmitObj = new Object();
updateSubmitObj.Type = null;
updateSubmitObj.ID = null;
updateSubmitObj.Department = null;
updateSubmitObj.Applicant = null;
updateSubmitObj.ExpenseCategory = null;
updateSubmitObj.Amount = null;
updateSubmitObj.ExpenseItem = null;
updateSubmitObj.ExpenseDetails = null;
updateSubmitObj.ApplicationDate = null;
updateSubmitObj.PayDate = null;
updateSubmitObj.Attachment = null;
updateSubmitObj.Payee = null;
updateSubmitObj.Account = null;
updateSubmitObj.DepositBank = null;
updateSubmitObj.PaymentRemark = null;
updateSubmitObj.StoreName = null;
updateSubmitObj.OrderNO = null;
updateSubmitObj.Link = null;
updateSubmitObj.LinkRemark = null;

// 维护已请求过部门员工
var hasRequestStaffObj = {};

// 维护费用类别
var ExpenseCategoryExpenseItemObj = {
	"采购费用": ["办公用品费","生活用品费","快递费","课程培训费","其他"],
	"办公管理费用": ["办公室租赁费","宿舍租赁费","水电物业费","清洁费","保险费","社保缴纳费","其他"]
};

// 定义状态集
var PaymentRequestState = new Object();
PaymentRequestState.uploadFileNo = 0;
PaymentRequestState.uploadFileList = {};
PaymentRequestState.hasSearch = false;
PaymentRequestState.searchColumn = undefined;
PaymentRequestState.searchContent = undefined;
PaymentRequestState.payStateValue = 0;
PaymentRequestState.popoverPayDetailVal = 0;
PaymentRequestState.popoverLinkDetailVal = 0;
PaymentRequestState.popoverAttachmentVal = 0;
PaymentRequestState.allMail = [];


// 主页面获取数据
function getDataByPage(currentPage, Column, Content){
	$.ajax({
		type: "GET",
		url: "PaymentRequest",
		data: {
			LoadType: "data",
			CurrentPage: currentPage,
			Column: Column,
			Content: Content
		},
		dataType: "json"
	}).then(function(res){
		var data = res.data;
		var pageCount = res.pageCount;
		renderPageData(data, currentPage, pageCount);
		if(!Column && !Content){
			PaymentRequestState.hasSearch = false;
			PaymentRequestState.searchColumn = undefined;
			PaymentRequestState.searchContent = undefined;
		}else{
			PaymentRequestState.hasSearch = true;
			PaymentRequestState.searchColumn = Column;
			PaymentRequestState.searchContent = Content;
		}
	},function(){
		$.MsgBox_Unload.Alert("提示","服务器繁忙！页面数据获取失败");
	}).always(function(){

	});
}

// 渲染页面数据
function renderPageData(data, currentPage, pageCount){
	var str = '';
	if(data.length == 1){
		str+='<tr><td colspan="11">无数据......</td></tr>';
	}else if(data.length > 1){
		data.map(function(currentValue, index, arr){
			if(index > 0){
				var iNO = parseInt((currentPage-1)*10 + index);
				var iAttachment = currentValue.Attachment;
				var iAttachmentArr = iAttachment.split("::");
				iAttachmentArr = $.grep(iAttachmentArr,function(jcurrentValue,jindex){
					return jcurrentValue != "";
				});
				var iAttachmentStr = "<div class='container-fluid' style='padding-left: 1px;padding-right: 1px;padding-bottom:5px;'>";
				if(iAttachmentArr.length == 0){
					iAttachmentStr+="无附件可预览或下载！";
				}else{
					iAttachmentStr+="<ol>";
					iAttachmentArr.map(function(icurrentValue, iindex, iarr){
						iAttachmentStr+="<li title='"+icurrentValue+"'>"+icurrentValue+"</li>";
					});
					iAttachmentStr+="</ol>";
				}
				iAttachmentStr+="</div>";
				// 付款信息字符串
				var paymoney_detail_str = "<table class='collapse_table'><thead><tr><th colspan='2'>付款信息</th></tr></thead><tbody>";
				if(currentValue.Payee!="" || currentValue.Account!="" || currentValue.DepositBank!="" || currentValue.PaymentRemark!=""){
					paymoney_detail_str+="<tr><td>收款户名：</td><td>"+currentValue.Payee+"</td></tr>"+
						"<tr><td>账号：</td><td>"+currentValue.Account+"</td></tr>"+
						"<tr><td>开户行：</td><td>"+currentValue.DepositBank+"</td></tr>"+
						"<tr><td>备注：</td><td>"+currentValue.PaymentRemark+"</td></tr>";
				}else{
					paymoney_detail_str+="<tr><td colspan='2'>无数据...</td></tr>";
				}
				paymoney_detail_str+="</tbody></table>";
				// 链接信息字符串
				var link_detail_str = "<table class='collapse_table'><thead><tr><th colspan='2'>链接信息</th></tr></thead><tbody>";
				if(currentValue.StoreName!="" || currentValue.OrderNO!="" || currentValue.Link!="" || currentValue.LinkRemark!=""){
					link_detail_str+="<tr><td>店家名称：</td><td>"+currentValue.StoreName+"</td></tr>"+
						"<tr><td>订单号：</td><td>"+currentValue.OrderNO+"</td></tr>"+
						"<tr><td>链接：</td><td>"+currentValue.Link+"</td></tr>"+
						"<tr><td>备注：</td><td>"+currentValue.LinkRemark+"</td></tr>";
				}else{
					link_detail_str+="<tr><td colspan='2'>无数据...</td></tr>";
				}
				link_detail_str+="</tbody></table>";

				var SendStateClassName;
				if(currentValue.SendState == "未发送"){
					SendStateClassName = "info";
				}else if(currentValue.SendState == "已发送"){
					SendStateClassName = "success";
				}

				var PayStateClassName;
				if(currentValue.PayState == "未付款"){
					PayStateClassName = "info";
				}else if(currentValue.PayState == "已付款"){
					PayStateClassName = "success";
				}

				str+='<tr>'+
					'<td class="update_td" data-iid="'+currentValue.ID+'">'+iNO+'</td>'+
					'<td class="hastd_ApplicationDate" title="'+currentValue.ApplicationDate+'" data-ivalue="'+currentValue.ApplicationDate+'">'+currentValue.ApplicationDate+'</td>'+
					'<td class="hastd_Department" title="'+currentValue.Department+'" data-ivalue="'+currentValue.Department+'">'+currentValue.Department+'</td>'+
					'<td class="hastd_Applicant" title="'+currentValue.Applicant+'" data-ivalue="'+currentValue.Applicant+'">'+currentValue.Applicant+'</td>'+
					'<td class="hastd_ExpenseCategory" title="'+currentValue.ExpenseCategory+'" data-ivalue="'+currentValue.ExpenseCategory+'">'+currentValue.ExpenseCategory+'</td>'+
					'<td class="hastd_ExpenseItem" title="'+currentValue.ExpenseItem+'" data-ivalue="'+currentValue.ExpenseItem+'">'+currentValue.ExpenseItem+'</td>'+
					'<td class="hastd_ExpenseDetails" title="'+currentValue.ExpenseDetails+'" data-ivalue="'+currentValue.ExpenseDetails+'">'+currentValue.ExpenseDetails+'</td>'+
					'<td class="hastd_Amount" title="'+currentValue.Amount+'" data-ivalue="'+currentValue.Amount+'">'+currentValue.Amount+'</td>'+
					'<td class="paymoney_detail_td"><button type="button" class="btn btn-default" data-container="body" data-toggle="popover" data-trigger="click" data-placement="top" data-content="'+paymoney_detail_str+'" title="'+currentValue.Applicant+'的付款申请--付款信息详细记录"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></button></td>'+
					'<td class="link_detail_td"><button type="button" class="btn btn-default" data-container="body" data-toggle="popover" data-trigger="click" data-placement="top" data-content="'+link_detail_str+'" title="'+currentValue.Applicant+'的付款申请--链接信息详细记录"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></button></td>'+
					'<td class="hastd_PayDate" title="'+currentValue.PayDate+'" data-ivalue="'+currentValue.PayDate+'">'+currentValue.PayDate+'</td>'+
					'<td class="hastd_Attachment" data-ivalue="'+iAttachment+'"><button type="button" class="btn btn-default" data-container="body" data-toggle="popover" data-trigger="click" data-placement="top" data-content="'+iAttachmentStr+'" title="'+currentValue.Applicant+'的付款申请--附件预览及下载"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></button></td>'+
					'<td class="SendState_td" title="'+currentValue.SendState+'" data-ivalue="'+currentValue.SendState+'"><a href="javascript:;" tabindex="0" class="btn btn-sm btn-'+SendStateClassName+'" role="button"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>'+currentValue.SendState+'</a></td>'+
					'<td class="PayState_td" title="'+currentValue.PayState+'" data-ivalue="'+currentValue.PayState+'"><a value="'+currentValue.ID+'" tabindex="0" class="btn btn-sm btn-'+PayStateClassName+'" role="button" data-toggle="popover" data-trigger="focus" data-container="body" data-placement="left" title="对第'+iNO+'条记录操作。申请人：'+currentValue.Applicant+'"><span class="glyphicon glyphicon-usd" aria-hidden="true"></span>'+currentValue.PayState+'</a></td>'+
					'<td class="hastd_Payee" title="'+currentValue.Payee+'" data-ivalue="'+currentValue.Payee+'">'+currentValue.Payee+'</td>'+
					'<td class="hastd_Account" title="'+currentValue.Account+'" data-ivalue="'+currentValue.Account+'">'+currentValue.Account+'</td>'+
					'<td class="hastd_DepositBank" title="'+currentValue.DepositBank+'" data-ivalue="'+currentValue.DepositBank+'">'+currentValue.DepositBank+'</td>'+
					'<td class="hastd_PaymentRemark" title="'+currentValue.PaymentRemark+'" data-ivalue="'+currentValue.PaymentRemark+'">'+currentValue.PaymentRemark+'</td>'+
					'<td class="hastd_StoreName" title="'+currentValue.StoreName+'" data-ivalue="'+currentValue.StoreName+'">'+currentValue.StoreName+'</td>'+
					'<td class="hastd_OrderNO" title="'+currentValue.OrderNO+'" data-ivalue="'+currentValue.OrderNO+'">'+currentValue.OrderNO+'</td>'+
					'<td class="hastd_Link" title="'+currentValue.Link+'" data-ivalue="'+currentValue.Link+'">'+currentValue.Link+'</td>'+
					'<td class="hastd_LinkRemark" title="'+currentValue.LinkRemark+'" data-ivalue="'+currentValue.LinkRemark+'">'+currentValue.LinkRemark+'</td>'+
				'</tr>';
			}
		});
	}
	$(".m_table table tbody").empty().append(str);
	$(".m_page #currentPage").text(currentPage);
	$(".m_page #allPage").text(pageCount);
	pageStyle(currentPage, pageCount);

	// 付款、链接信息popover
	$(".paymoney_detail_td>button, .link_detail_td>button").popover({
		title: "请预览",
		html: true
	});

	// 附件popover
	$(".hastd_Attachment>button").popover({
		title: "请点击下载",
		html: true
	});

	// 付款状态popover
	$(".PayState_td[title='未付款']>a").popover({
		title: "请选择",
		content: '<div class="container-fluid" style="margin-bottom:5px;"><button type="button" class="btn btn-info btn-lg PayState_td_y" style="margin-right:10px;"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span>更新</button><button type="button" class="btn btn-info btn-lg PayState_td_n"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>取消</button></div>',
		html: true
	});
	
}

// 翻页组件按钮逻辑
function pageStyle(currentPage,pageCount){
    if(pageCount == 1){
        $("#fistPage, #upPage, #nextPage, #lastPage, #Gotojump").prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
    }else if(currentPage == 1){
        $("#fistPage, #upPage").prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
        $("#lastPage, #nextPage, #Gotojump").prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }else if(currentPage == pageCount){
        $("#lastPage, #nextPage").prop("disabled","disabled").removeClass("btn-primary").addClass("btn-default");
        $("#fistPage, #upPage, #Gotojump").prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }else{
        $("#lastPage, #nextPage, #fistPage, #upPage, #Gotojump").prop("disabled",false).removeClass("btn-default").addClass("btn-primary");
    }
}

// 翻页获取数据
function getDataByGoPage(currentPage){
	var Column;
	var Content;
	if(!PaymentRequestState.hasSearch){
		Column = undefined;
		Content = undefined;
	}else{
		Column = PaymentRequestState.searchColumn;
		Content = PaymentRequestState.searchContent;
	}
	getDataByPage(currentPage, Column, Content);
}

// 搜索组件Init
function searchInit(){
	$(".m_button_r_in_l #search_year").hide().val("");
	$(".m_button_r_in_l .in_search").show().val("");
	$(".m_button_r_in>div .input-group-btn>button:nth-child(1)").text("选择字段").attr("title","选择字段");
}

//上传文件
function uploadFiles(classify, Folder, iThat){                                               
    var formData = new FormData();
    formData.enctype="multipart/form-data";
    formData.append("Folder",Folder);
    // formData.append("ID",ID);
    var fileList = PaymentRequestState.uploadFileList;
    $.each(fileList, function(iname, ivalue){
    	formData.append("file",ivalue);
    });
    //formData.append("file",$("#serFinRepUpload")[0].files[0]);//append()里面的第一个参数file对应permission/upload里面的参数file         
    // formData.append("Operate","upload");
    $.ajax({
        type: "POST",
        async: true,  //这里要设置异步上传，才能成功调用myXhr.upload.addEventListener('progress',function(e){}),progress的回掉函数
        // accept: 'text/html;charset=UTF-8',
        accept: 'application/json; charset=utf-8',
        data: formData,
        // contentType:"multipart/form-data",
        url: "BatchUpload",
        processData: false, // 告诉jQuery不要去处理发送的数据
        contentType: false, // 告诉jQuery不要去设置Content-Type请求头
        cache: false,
        dataType: "json",
        xhr: function(){                        
            myXhr = $.ajaxSettings.xhr();
            if(myXhr.upload){ // check if upload property exists
                myXhr.upload.addEventListener('progress',function(e){                           
                    var loaded = e.loaded;                  //已经上传大小情况 
                    var total = e.total;                      //附件总大小 
                    var percent = (Math.floor(1000*loaded/total)/10)+"%";     //已经上传的百分比  
                    console.log("已经上传了："+percent);
                    var newWidthFloat =  globalToPoint(percent);
                    var newWidth = (newWidthFloat*100).toFixed(0);
                    $("div."+classify+"_fileList_info>div:nth-child(1)>div.progress-bar").attr("aria-valuenow",newWidth).css("width",percent).text(percent);
                    // console.log("进度条宽度："+newWidth); 
                    // $(".progressIn").css("width",newWidth+"px");
                    // $(".progressIn").text(percent);
                }, false); // for handling the progress of the upload
            }
            return myXhr;
        },                    
        success: function(data){
        	if(data.length > 0){
        		var LiStr = '';
        		var successNO = 0;
        		data.map(function(currentValue, index, arr){
        			if(currentValue.Message.indexOf("成功")>-1){
        				LiStr+='<li class="list-group-item list-group-item-success" title="'+currentValue.FileName+'"><span class="badge">成功</span>'+currentValue.FileName+'</li>';
        				successNO++;
        			}else if(currentValue.Message.indexOf("失败")>-1){
        				LiStr+='<li class="list-group-item list-group-item-danger" title="'+currentValue.FileName+'"><span class="badge">失败</span>'+currentValue.FileName+'</li>';
        			}else{
        				LiStr+='<li class="list-group-item list-group-item-warning" title="'+currentValue.FileName+'"><span class="badge">文件已存在</span>'+currentValue.FileName+'</li>';
        			}
        		});
        		$("#"+classify+"_fileList_ul>li>span:contains('删除')").parent().remove();
        		$("#"+classify+"_fileList_ul").append(LiStr);
        		var successFloat = (successNO/(data.length)).toFixed(3);
        		var successValueNow = successFloat*100;
        		var successPercent = successValueNow+"%";
        		$("div."+classify+"_fileList_info>div:nth-child(2)>div.progress-bar").attr("aria-valuenow",successValueNow).css("width",successPercent).text(successPercent);
        		PaymentRequestState.uploadFileNo = 0;
        		for(var kk in PaymentRequestState.uploadFileList){
        			delete PaymentRequestState.uploadFileList[kk];
        		}
        		$("#"+classify+"_file_Upload").val("");
        	}else if(data.length == 0){
        		$.MsgBox_Unload.Alert("上传提示","文件读取至服务器失败！");
        	}
        	// addWinCleseTimer = setTimeout("addWindowClose()",2000);
        	// getDataByPage(1, undefined, undefined);
        	// 
    //     	if(data.success == undefined && data.error == undefined){
    //     		$.MsgBox_Unload.Alert("提示","网络繁忙，文件读取失败！");
    //     	}else{
	   //      	var errorList = data.error.split("::");
	   //      	var successList = data.success.split("::");
	   //      	errorList.pop();
	   //      	successList.pop();
	   //      	var imsg = '';
	   //      	var iimsg = '';
	   //      	var erLen = errorList.length;
	   //      	var sucLen = successList.length;
	   //      	if(erLen == 0){
	   //      		imsg = '全部上传成功';
	   //      		iimsg = sucLen+'个文件上传成功';
	   //      	}else if(sucLen == 0){
	   //      		imsg = '全部上传失败';
	   //      		iimsg = erLen+'个文件上传失败';
	   //      		$(".progressIn").css("width","30px");
	   //      		$(".progressIn").text("0%");
	   //      	}else{
	   //      		imsg = '部分上传成功';
	   //      		iimsg = sucLen+'个文件上传成功，'+erLen+'个上传失败';
	   //      	}
	   //      	$("span.isUpload").text(imsg);
				// var curFileuploadArr;
				// if(reimburseState.curFileupload.indexOf("++")>-1){
				// 	curFileuploadArr = reimburseState.curFileupload.split("++");
				// }else{
				// 	curFileuploadArr = ["",""];
				// } 
				// var oldFileArray = $("."+curFileuploadArr[0]+" ."+curFileuploadArr[1]).val().split("::");
				// oldFileArray = $.grep(oldFileArray,function(currentValue,index){
				//     return currentValue != "";
				// });
				// var newFileArray0 = oldFileArray.concat(successList);
				// var newFileArray = globalArrStrUnique(newFileArray0);
				// var newFileString = newFileArray.length == 0 ? "" : newFileArray.join("::")+"::";
				// $("."+curFileuploadArr[0]+" ."+curFileuploadArr[1]).val(newFileString);
				// $("."+curFileuploadArr[0]+" ."+curFileuploadArr[1]).attr("title",newFileString);
				// if(sucLen != 0){
				// 	$(".dropFileTit span").trigger("click");
				// }
	   //      	$.MsgBox_Unload.Alert("提示",iimsg);
    //     	}
        },
        error: function(){
        	$("div."+classify+"_fileList_info>div>div.progress-bar").prop("aria-valuenow","0").css("width","0%").text("0%");
            $.MsgBox_Unload.Alert("上传提示","网络繁忙！上传失败！");
        },
		beforeSend: function(XMLHttpRequest){
            iThat.css("cursor","not-allowed").prop("disabled","disabled");
        },
		complete: function(XMLHttpRequest, textStatus){
		    if(textStatus=="success"){
		    }
		    iThat.css("cursor","pointer").prop("disabled",false);
		}
    });                             
}

/*page onload*/
$(function(){
	getDataByPage(1, undefined, undefined);

	// 获取员工列表
	// var iIndex = 0;
	// var myFunc = function(data){
	//     if(data.length > 1){
	//         data.map(function(currentValue,index,arr){
	//             if(index > 0){
	//                 BiddingDocDepartStaff.push(currentValue.StaffName);
	//             }
	//         });
	//     }
	//     iIndex++;
	//     if(iIndex < BiddingDocDepart.length){
	//         $.when(
	//             $.ajax({
	//                 type: 'GET',
	//                 url: 'GetStaffInfo',
	//                 data: {
	//                     Type: "common",
	//                     Department: BiddingDocDepart[iIndex]
	//                 },
	//                 dataType: "json"
	//             })
	//             ).done(myFunc);
	//     }else if(iIndex == BiddingDocDepart.length){
	//         console.log(BiddingDocDepartStaff);
	//         var staffStr = '<option value="" disabled>请选择</option>';
	//         BiddingDocDepartStaff.map(function(currentValue, index, arr){
	//         	staffStr+='<option value="'+currentValue+'">'+currentValue+'</option>';
	//         });
	//         $("select[name='update_info_Submitter']").empty().append(staffStr);
	//     }
	// };
	// $.when(
	//     $.ajax({
	//         type: 'GET',
	//         url: 'GetStaffInfo',
	//         data: {
	//             Type: "common",
	//             Department: BiddingDocDepart[iIndex]
	//         },
	//         dataType: "json"
	//     })
	//     ).then(myFunc, function(){
	//     $.MsgBox_Unload.Alert("获取员工姓名提示","初始化失败！");
	// });

	// globalBuildYOptionStr(2016,$("select[name='search_Year']"));
	
	// 填充部门
	var globalEouluAllDepartStr = '<option value="" disabled>请选择</option>';
	$.each(globalEouluAllDepart, function(index, value){
		globalEouluAllDepartStr+='<option value="'+value+'">'+value+'</option>';
	});
	$("select[name='payment_depart']").empty().append(globalEouluAllDepartStr);

	// 填充费用类别
	var ExpenseCategoryStr = '<option value="" disabled>请选择</option>';
	$.each(ExpenseCategoryExpenseItemObj, function(index, value){
		ExpenseCategoryStr+='<option value="'+index+'">'+index+'</option>';
	});
	$("select[name='payment_ExpenseCategory']").empty().append(ExpenseCategoryStr);

	// 邮箱初始化
	globalGetAllEmail(function(data){
		data.map(function(currentValue, index, arr){
			if(index > 0){
				PaymentRequestState.allMail.push(currentValue.Email);
			}
		});
	}, null);
});


/*event handler*/
// 部门切换
$("select[name='payment_depart']").on("change", function(){
	var iVal = $(this).val();
	var insertDOM;
	if($(this).is("#add_info_Department")){
		insertDOM = $("#add_info_Applicant");
	}else if($(this).is("#update_info_Department")){
		insertDOM = $("#update_info_Applicant");
	}
	if(Object.keys(hasRequestStaffObj).indexOf(iVal) > -1){
		var insertStaffStr = '';
		$.each(hasRequestStaffObj, function(index, value){
			if(index == iVal){
				$.each(value, function(iindex, ivalue){
					insertStaffStr+='<option value="'+ivalue+'">'+ivalue+'</option>';
				});
			}
		});
		insertDOM.empty().append(insertStaffStr);
	}else{
		globalGetStaffAllInfoByDepart(iVal,function(data){
			var insertStaffStr2 = '';
			hasRequestStaffObj[iVal] = [];
			data.map(function(currentValue, index, arr){
				if(index > 0){
					insertStaffStr2+='<option value="'+currentValue.StaffName+'">'+currentValue.StaffName+'</option>';
					hasRequestStaffObj[iVal].push(currentValue.StaffName);
				}
			});
			insertDOM.empty().append(insertStaffStr2);
		},null);
	}
});

// 费用类别切换
$("select[name='payment_ExpenseCategory']").on("change", function(){
	var iVal = $(this).val();
	var insertDOM;
	if($(this).is("#add_info_ExpenseCategory")){
		insertDOM = $("#add_info_ExpenseItem");
	}else if($(this).is("#update_info_ExpenseCategory")){
		insertDOM = $("#update_info_ExpenseItem");
	}
	var insertItemStr = '';
	$.each(ExpenseCategoryExpenseItemObj, function(index, value){
		if(index == iVal){
			$.each(value, function(iindex, ivalue){
				insertItemStr+='<option value="'+ivalue+'">'+ivalue+'</option>';
			});
		}
	});
	insertDOM.empty().append(insertItemStr);
});


// 添加打开
$(".m_button_l>input[value='添加']").click(function(){
	$(".add_NonStandard_body_in select, .add_NonStandard_body_in input").val("");
	$(".bg_cover, .add_NonStandard").slideDown(250);
});
// 添加关闭
$(".add_NonStandard_tit_r, #NonStandard_addclose").click(function(){
	$(".bg_cover, .add_NonStandard").slideUp(250);
	if($('div.add_fileList_info').is(':visible')){
		$('div.add_fileList_info').slideUp(150, function(){
			$("div.add_fileList_info>div>div.progress-bar").attr("aria-valuenow","0").css("width","0%").text("0%");
		});
	}
	$("#add_fileList_ul").empty();
	
	for(var k in addSubmitObj){
		addSubmitObj[k] = null;
	}
	PaymentRequestState.uploadFileNo = 0;
	for(var kk in PaymentRequestState.uploadFileList){
		delete PaymentRequestState.uploadFileList[kk];
	}
	$("#add_file_Upload").val("");
});

// 点击浏览切换文件
$("#add_file_Upload, #update_file_Upload").on("change",function(){
	var hideShowDOM;
	var insertDOM;
	var curFileInput;
	var classify;
	if($(this).is("#add_file_Upload")){
		curFileInput = $("#add_file_Upload");
		hideShowDOM = $(".add_fileList_info");
		insertDOM = $("#add_fileList_ul");
		classify = "add";
	}else if($(this).is("#update_file_Upload")){
		curFileInput = $("#update_file_Upload");
		hideShowDOM = $(".update_fileList_info");
		insertDOM = $("#update_fileList_ul");
		classify = "update";
	}
	if(!hideShowDOM.is(':visible')){
		hideShowDOM.slideDown(150);
	}
	console.log($(this));
	console.log($(this)[0]);
	console.log($(this)[0].files);
	var curFileList = $(this)[0].files;
	var curFileListStr = '';
	var fileNamesuccess = true;
	$.each(curFileList, function(iiname, iivalue){
		if( !regHasDate.test(iivalue.name) && !regHasDateNoCTOR.test(iivalue.name)){
			fileNamesuccess = false;
		}
	});
	if(!fileNamesuccess){
		$.MsgBox_Unload.Alert("文件名规范提示","需包含日期！<br>如：快递费+2018-08-20+小明或<br>办公费用（第二版）+20180901+小华");
		$("#mb_msg").css("line-height","40px");
		curFileInput.val("");
		return false;
	}
	$.each(curFileList, function(iname, ivalue){
		PaymentRequestState.uploadFileNo++;
		curFileListStr+='<li class="list-group-item" title="'+ivalue.name+'" value="'+PaymentRequestState.uploadFileNo+'"><span class="badge">删除</span>'+ivalue.name+'</li>';
		PaymentRequestState.uploadFileList[PaymentRequestState.uploadFileNo] = ivalue;
		// curFileListStr+=ivalue.name+"::";
	});
	insertDOM.append(curFileListStr);
	$("div."+classify+"_fileList_info>div>div.progress-bar").prop("aria-valuenow","0").css("width","0%").text("0%");
	// console.log("文件上传改变值"+$(this).val());
	// var newFileName1 = $(this).val().indexOf("\\fakepath\\")>-1?$(this).val().split("\\fakepath\\")[1]:$(this).val().split("\\").pop();
	// console.log("赋给input的值"+newFileName1);
});

// 添加修改文件删除
$(document).on("click","[id$='_fileList_ul']>li>span:contains('删除')",function(){
	var iValue = $(this).parent().attr("value");
	delete PaymentRequestState.uploadFileList[iValue];
	var emptyFileInput;
	if($(this).parents("#add_fileList_ul").length){
		emptyFileInput = $("#add_file_Upload");
	}else if($(this).parents("#update_fileList_ul").length){
		emptyFileInput = $("#update_file_Upload");
	}
	emptyFileInput.val("");
	$(this).parent().remove();
});

// 添加修改文件上传
$(".add_info_upload>button, .update_info_upload>button").click(function(){
	if(Object.keys(PaymentRequestState.uploadFileList).length == 0){
		$.MsgBox_Unload.Alert("上传提示","请选择文件！");
		return false;
	}
	var classify;
	if($(this).parent().is(".add_info_upload")){
		classify = "add";
	}else if($(this).parent().is(".update_info_upload")){
		classify = "update";
	}
	var Folder = "PaymentRequest";
	var iThat = $(this);
	uploadFiles(classify, Folder, iThat);
});

// 添加提交
$("#NonStandard_addsubmit").click(function(){
	var iThat = $(this);
	for(var kk in addSubmitObj){
		if(kk=="Type"){
			addSubmitObj[kk] = "add";
			continue;
		}
		if(kk=="Attachment"){
			continue;
		}
		addSubmitObj[kk] = $("#add_info_"+kk).val();
	}
	// 表单验证
	for(var kkk in addSubmitObj){
		addSubmitObj[kkk] = globalDataHandle(addSubmitObj[kkk],"").trim();
		if(kkk=="Department"&&addSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择部门！");
			return false;
		}
		if(kkk=="Applicant"&&addSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择姓名！");
			return false;
		}
		if(kkk=="ExpenseCategory"&&addSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择费用类型！");
			return false;
		}
		if(kkk=="ExpenseItem"&&addSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择费用项目！");
			return false;
		}
		if(kkk=="Amount"&&addSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未填写金额！");
			return false;
		}
	}
	var fileArray = [];
	$("#add_fileList_ul>li.list-group-item-success").each(function(){
		fileArray.push($(this).attr("title"));
	});
	var newfileArray = globalArrStrUnique(fileArray);
	var fileStr = newfileArray.length == 0 ? "" : (newfileArray.join("::")+"::");
	addSubmitObj.Attachment = fileStr;
	iThat.css("cursor","not-allowed").prop("disabled","disabled");
	$.ajax({
		type: "POST",
		url: "PaymentRequest",
		data: addSubmitObj,
		dataType: "text"
	}).then(function(data){
		if(data == "添加成功"){
			getDataByPage(1, undefined, undefined);
			$("#NonStandard_addclose").trigger("click");
		}
		$.MsgBox_Unload.Alert("提示", data);
	},function(){
		$.MsgBox_Unload.Alert("提示","服务器繁忙！");
	}).always(function(){
		iThat.css("cursor","pointer").prop("disabled",false);
	});
	
	// if(Object.keys(PaymentRequestState.uploadFileList).length == 0){
	// 	$.MsgBox_Unload.Alert("添加提示","请选择文件！");
	// 	return false;
	// }
	// uploadFiles(Year, iThat);
});

// 修改
$(document).on("click",".update_td",function(){
	$(".bg_cover, .update_NonStandard").slideDown(250);
	var tr = $(this).parent();
	updateSubmitObj.ID = $(this).data("iid").toString();

	var iVal = tr.find(".hastd_Department").data("ivalue").toString();
	var iName = tr.find(".hastd_Applicant").data("ivalue").toString();
	var insertDOM = $("#update_info_Applicant");
	$("#update_info_Department").val(iVal);
	if(Object.keys(hasRequestStaffObj).indexOf(iVal) > -1){
		var insertStaffStr = '';
		$.each(hasRequestStaffObj, function(index, value){
			if(index == iVal){
				$.each(value, function(iindex, ivalue){
					insertStaffStr+='<option value="'+ivalue+'">'+ivalue+'</option>';
				});
				return false;
			}
		});
		insertDOM.empty().append(insertStaffStr).val(iName);
	}else{
		globalGetStaffAllInfoByDepart(iVal,function(data){
			var insertStaffStr2 = '';
			hasRequestStaffObj[iVal] = [];
			data.map(function(currentValue, index, arr){
				if(index > 0){
					insertStaffStr2+='<option value="'+currentValue.StaffName+'">'+currentValue.StaffName+'</option>';
					hasRequestStaffObj[iVal].push(currentValue.StaffName);
				}
			});
			insertDOM.empty().append(insertStaffStr2).val(iName);
		},null);
	}

	$(".update_NonStandard_body_in [id^='update_info_']").each(function(){
		if($(this).is("#update_info_Department") || $(this).is("#update_info_Applicant")){
			return true;
		}
		if($(this).is("#update_info_ExpenseItem")){
			$("#update_info_ExpenseCategory").trigger("change");
		}
		var subClass = $(this).attr("id").replace("update_info_", "hastd_");
		var oldValue = tr.find("."+subClass).data("ivalue").toString();
		var newValue = globalDataHandle(oldValue, "");
		$(this).val(newValue);
	});
	var iAttachment = tr.find(".hastd_Attachment").data("ivalue").toString();
	var iAttachmentArr = iAttachment.split("::");
	iAttachmentArr = $.grep(iAttachmentArr,function(currentValue,index){
	    return currentValue != "";
	});
	var iAttachmentStr = '';
	iAttachmentArr.map(function(currentValue, index, arr){
		iAttachmentStr+='<li class="list-group-item list-group-item-info" title="'+currentValue+'"><span class="badge">已上传</span>'+currentValue+'</li>';
	});
	$("#update_fileList_ul").empty().append(iAttachmentStr);
});
// 修改关闭
$(".update_NonStandard_tit_r, #NonStandard_updateclose").click(function(){
	$(".bg_cover, .update_NonStandard").slideUp(250);
	if($('div.update_fileList_info').is(':visible')){
		$('div.update_fileList_info').slideUp(150, function(){
			$("div.update_fileList_info>div>div.progress-bar").attr("aria-valuenow","0").css("width","0%").text("0%");
		});
	}
	$("#update_fileList_ul").empty();

	for(var k in updateSubmitObj){
		updateSubmitObj[k] = null;
	}
	
	PaymentRequestState.uploadFileNo = 0;
	for(var kk in PaymentRequestState.uploadFileList){
		delete PaymentRequestState.uploadFileList[kk];
	}
	$("#update_file_Upload").val("");
});

// 修改提交
$("#NonStandard_updatesubmit").click(function(){
	var iThat = $(this);
	for(var kk in updateSubmitObj){
		if(kk=="Type"){
			updateSubmitObj[kk] = "update";
			continue;
		}
		if(kk=="Attachment" || kk=="ID"){
			continue;
		}
		updateSubmitObj[kk] = $("#update_info_"+kk).val();
	}
	// 表单验证
	for(var kkk in updateSubmitObj){
		updateSubmitObj[kkk] = globalDataHandle(updateSubmitObj[kkk],"").trim();
		if(kkk=="Department"&&updateSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择部门！");
			return false;
		}
		if(kkk=="Applicant"&&updateSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择姓名！");
			return false;
		}
		if(kkk=="ExpenseCategory"&&updateSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择费用类型！");
			return false;
		}
		if(kkk=="ExpenseItem"&&updateSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未选择费用项目！");
			return false;
		}
		if(kkk=="Amount"&&updateSubmitObj[kkk]==""){
			$.MsgBox_Unload.Alert("提示","未填写金额！");
			return false;
		}
	}
	var fileArray = [];
	$("#update_fileList_ul>li.list-group-item-success, #update_fileList_ul>li.list-group-item-info").each(function(){
		fileArray.push($(this).attr("title"));
	});
	var newfileArray = globalArrStrUnique(fileArray);
	var fileStr = newfileArray.length == 0 ? "" : (newfileArray.join("::")+"::");
	updateSubmitObj.Attachment = fileStr;
	iThat.css("cursor","not-allowed").prop("disabled","disabled");
	$.ajax({
		type: "POST",
		url: "PaymentRequest",
		data: updateSubmitObj,
		dataType: "text"
	}).then(function(data){
		if(data == "修改成功"){
			getDataByPage(Number($("span#currentPage").text()), undefined, undefined);
			$("#NonStandard_updateclose").trigger("click");
		}
		$.MsgBox_Unload.Alert("提示", data);
	},function(){
		$.MsgBox_Unload.Alert("提示","服务器繁忙！");
	}).always(function(){
		iThat.css("cursor","pointer").prop("disabled",false);
	});
});

/*
// 搜索字段点击
$(document).on("click",".m_button_r_in_l ul.dropdown-menu>li",function(){
	var iText = $(this).text();
	var iTitle = $(this).attr("title");
	$(this).parent().siblings("button:nth-child(1)").attr("title",iTitle).text(iText);
	if(iText == "年份"){
		$(".m_button_r_in_l .in_search").hide(50,function(){
			$(".m_button_r_in_l #search_year").show().val("");
		});
	}else{
		$(".m_button_r_in_l #search_year").hide(50,function(){
			$(".m_button_r_in_l .in_search").show().val("");
		});
	}
});

// 搜索
$(".m_button_r_in_r>input:nth-child(1)").click(function(){
	var Column = $(".m_button_r_in_l div.input-group-btn>button:nth-child(1)").attr("title");
	if(!Column || Column == "选择字段"){
		$.MsgBox_Unload.Alert("搜索提示","请选择字段！");
		return false;
	}
	var Content;
	if(Column == "年份"){
		Content = $("select#search_year").val();
	}else{
		Content = $("input.in_search").val().trim();
	}
	if(!Content){
		$.MsgBox_Unload.Alert("搜索内容提示","请选择或填写内容！");
		return false;
	}
	getDataByPage(1, Column, Content);
});

// 取消搜索
$(".m_button_r_in_r>input:nth-child(2)").click(function(){
	getDataByPage(1, undefined, undefined);
	searchInit();
	$("#jumpNumber").val("");
});
*/

// 翻页功能
$("#jumpNumber").on("input propertychange",function(){
    var newVal = $(this).val().replace(/[^\d]/g,'');
    $(this).val(newVal);
});

	// 翻页
$("#fistPage").click(function(){
    var currentPage =1;
    getDataByGoPage(currentPage);
});

$("#lastPage").click(function(){
    var currentPage =Number($("#allPage").text());
    getDataByGoPage(currentPage);
});

$("#upPage").click(function(){
    var currentPage = Number($("#currentPage").text());
    if(currentPage == 1){
        return;
    }else{
        currentPage--;
        getDataByGoPage(currentPage);
    }
});

$("#nextPage").click(function(){
    var currentPage = Number($("#currentPage").text());
    var pageCounts = Number($("#allPage").text());
    if(currentPage == pageCounts){
        return;
    }else{
        currentPage++;
        getDataByGoPage(currentPage);
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
        getDataByGoPage(currentPage);
    }
});

// 下载
// $(document).on("click",".hastd_downloadFile",function(){
// 	var iPath = $(this).attr("value");
// 	if(!iPath || iPath == "--"){
// 		$.MsgBox_Unload.Alert("下载提示","无数据或文件已被删除！");
// 		return false;
// 	}
// 	if(iPath.indexOf("E:")==0){
// 		iPath = iPath.replace(/\\/g,"/").substring(2);
// 		console.log(iPath);
// 	}
// 	window.open(globalHostName+iPath);
// });

// 金额验证 change keydown keyup  input propertychange
$("#add_info_Amount, #update_info_Amount").on("blur",function(e){
	e.stopPropagation();
	var iValue = $(this).val();
	if(iValue == ""){
		return false;
	}
	if(!regIntOr1To2DeciPoi.test(iValue)){
		$(this).val("0");
	}else{
		$(this).val(iValue);
	}
});

// input file域触发点击
$(".trigger_click").click(function(e){
	e.preventDefault();
	$(this).next().trigger("click");
});

// 邮件发送弹出
$(document).on("click","td.SendState_td>a",function(){
	$(".mail_cover_bg, .mail_template").slideDown(250);
	$(".mail_template").attr("value",$(this).parent().siblings(".update_td").data("iid"));
	$(".TO_radio_div>label:eq(0)").addClass("active").children("input").prop("checked",true);
	$(".TO_radio_div>label:eq(1)").removeClass("active").children("input").prop("checked",false);
	$(".TO_wrapper>.mail_item:eq(0)").children(".mail_item_select").show();
	$(".TO_wrapper>.mail_item:eq(1)").children(".mail_item_select").hide();
	// 抄送人填充
	var curEmail = $("span#email").text();
	var curEmailStr;
	if(curEmail!=""){
		curEmailStr = '<div class="mail_item"><span class="mail_item_info">抄送人7</span><input type="text" class="form-control" value="'+curEmail+'"><span class="mail_item_add glyphicon glyphicon-plus" aria-hidden="true"></span><span class="mail_item_del glyphicon glyphicon-remove" aria-hidden="true"></span></div>';
	}else{
		curEmailStr = '';
	}
	var exitEmailStr = '<div class="mail_item"><span class="mail_item_info">抄送人1</span><input type="text" class="form-control" value="wangxiaoliang@eoulu.com"><span class="mail_item_add glyphicon glyphicon-plus" aria-hidden="true"></span><span class="mail_item_del glyphicon glyphicon-remove" aria-hidden="true"></span></div><div class="mail_item"><span class="mail_item_info">抄送人2</span><input type="text" class="form-control" value="luoxiaoxu@eoulu.com"><span class="mail_item_add glyphicon glyphicon-plus" aria-hidden="true"></span><span class="mail_item_del glyphicon glyphicon-remove" aria-hidden="true"></span></div><div class="mail_item"><span class="mail_item_info">抄送人3</span><input type="text" class="form-control" value="zhaona@eoulu.com"><span class="mail_item_add glyphicon glyphicon-plus" aria-hidden="true"></span><span class="mail_item_del glyphicon glyphicon-remove" aria-hidden="true"></span></div><div class="mail_item"><span class="mail_item_info">抄送人4</span><input type="text" class="form-control" value="gaona@eoulu.com"><span class="mail_item_add glyphicon glyphicon-plus" aria-hidden="true"></span><span class="mail_item_del glyphicon glyphicon-remove" aria-hidden="true"></span></div><div class="mail_item"><span class="mail_item_info">抄送人5</span><input type="text" class="form-control" value="zhufei@eoulu.com"><span class="mail_item_add glyphicon glyphicon-plus" aria-hidden="true"></span><span class="mail_item_del glyphicon glyphicon-remove" aria-hidden="true"></span></div><div class="mail_item"><span class="mail_item_info">抄送人6</span><input type="text" class="form-control" value="fanminmin@eoulu.com"><span class="mail_item_add glyphicon glyphicon-plus" aria-hidden="true"></span><span class="mail_item_del glyphicon glyphicon-remove" aria-hidden="true"></span></div>'+curEmailStr;
	$("div.CC_wrapper").empty().append(exitEmailStr).find(".mail_item").each(function(){
	    var iindex = $(this).index() + 1;
	    $(this).find(".mail_item_info").text("抄送人"+iindex);
	});
	// 主题填充
	var iExpenseDetails = $(this).parent().siblings(".hastd_ExpenseDetails").text();
	$("#i_mail_Subject").val("Eoulu："+iExpenseDetails);
	// 内容填充
	var insertPayStr, insertLinkStr;
	var iitd = $(this).parent();
	if(iitd.siblings(".hastd_Payee").attr("title")!="" || iitd.siblings(".hastd_Account").attr("title")!="" || iitd.siblings(".hastd_DepositBank").attr("title")!="" || iitd.siblings(".hastd_PaymentRemark").attr("title")!=""){
		insertPayStr = '<div class="col-md-6 col-lg-6">'+
	                        '<div class="paymoney_detail_table_wrapper">'+
								"<table>"+
		                            "<thead><tr><th colspan='2'>付款信息</th></tr></thead>"+
		                            "<tbody>"+
		                                "<tr><td>收款户名：</td><td>"+iitd.siblings(".hastd_Payee").attr("title")+"</td></tr>"+
		                                "<tr><td>账号：</td><td>"+iitd.siblings(".hastd_Account").attr("title")+"</td></tr>"+
		                                "<tr><td>开户行：</td><td>"+iitd.siblings(".hastd_DepositBank").attr("title")+"</td></tr>"+
		                                "<tr><td>备注：</td><td>"+iitd.siblings(".hastd_PaymentRemark").attr("title")+"</td></tr>"+
		                            "</tbody>"+
                        		"</table>"+
                        	'</div>'+
                        '</div>';
	}else{
		insertPayStr = "";
	}

	if(iitd.siblings(".hastd_StoreName").attr("title")!="" || iitd.siblings(".hastd_OrderNO").attr("title")!="" || iitd.siblings(".hastd_Link").attr("title")!="" || iitd.siblings(".hastd_LinkRemark").attr("title")!=""){
		insertLinkStr = '<div class="col-md-6 col-lg-6">'+
	                        '<div class="link_detail_table_wrapper">'+
								"<table>"+
		                            "<thead><tr><th colspan='2'>链接信息</th></tr></thead>"+
		                            "<tbody>"+
		                                "<tr><td>店家名称：</td><td>"+iitd.siblings(".hastd_StoreName").attr("title")+"</td></tr>"+
		                                "<tr><td>订单号：</td><td>"+iitd.siblings(".hastd_OrderNO").attr("title")+"</td></tr>"+
		                                "<tr><td>链接：</td><td>"+iitd.siblings(".hastd_Link").attr("title")+"</td></tr>"+
		                                "<tr><td>备注：</td><td>"+iitd.siblings(".hastd_LinkRemark").attr("title")+"</td></tr>"+
		                            "</tbody>"+
		                        "</table>"+
		                    '</div>'+
		                '</div>';
	}else{
		insertLinkStr = "";
	}

	var emailConStr = '<div>您好！</div><br>'+
	                        '<div>'+iExpenseDetails+'</div><br>'+
	                        '<div class="row">'+
	                            insertPayStr+
	                            insertLinkStr+
	                        '</div><br>'+
	                        '<div>请协助尽快安排付款，非常感谢！</div>';
	$("fieldset.mail_con_field>.container-fluid").empty().append(emailConStr);

	$(".CC_wrapper>.mail_item input").each(function(i, el){
		new Awesomplete(el, {
			list: PaymentRequestState.allMail,
			minChars: 2,
			maxItems: 12,
			autoFirst: true
		});
	});
	// var input = document.querySelectorAll(".CC_wrapper>.mail_item input")[0];
	// // Show label but insert value into the input:
	// new Awesomplete(input, {
	// 	list: [
	// 		{ label: "Belarus", value: "aaa" },
	// 		{ label: "China", value: "aabb" },
	// 		{ label: "United States", value: "ccbb" }
	// 	]
	// });
});

// 收件人选择
$("input[name='TO_radio']").change(function(){
	var iindex = $(".TO_radio_div>label").index($(this).parent());
	$(".TO_wrapper>.mail_item:eq("+iindex+")").children(".mail_item_select").show();
	$(".TO_wrapper>.mail_item:not(:eq("+iindex+"))").children(".mail_item_select").hide();
});

// 邮件发送关闭
$(".mail_template_tit_r, #mail_template_close").click(function(){
	$(".mail_cover_bg, .mail_template").slideUp(250);
});

// 抄送人增减
$(document).on("click",".mail_item_add",function(){
    var curNo = $(this).parent().siblings(".mail_item").length + 2;
    var curParents = $(this).parents("[class$='_wrapper']");
    var classify;
    if(curParents.is(".TO_wrapper")){
        classify = "收件人"+curNo;
    }else if(curParents.is(".CC_wrapper")){
        classify = "抄送人"+curNo;
    }
    var str = '<div class="mail_item"><span class="mail_item_info">'+classify+'</span>'+
            '<input type="text" class="form-control" value=""><span class="mail_item_add glyphicon glyphicon-plus" aria-hidden="true"></span><span class="mail_item_del glyphicon glyphicon-remove" aria-hidden="true"></span>'+
        '</div>';
    curParents.append(str);

    $(".CC_wrapper>.mail_item input").each(function(i, el){
    	if($(el).is(".CC_wrapper>.mail_item input:last")){
    		new Awesomplete(el, {
    			list: PaymentRequestState.allMail,
    			minChars: 2,
    			maxItems: 12,
    			autoFirst: true
    		});
    		// console.log(2);
    	}
    });

});
$(document).on("click",".mail_item_del",function(){
    var curParents = $(this).parents("[class$='_wrapper']");
    $(this).parent().remove();
    var classify;
    if(curParents.is(".TO_wrapper")){
        classify = "收件人";
    }else if(curParents.is(".CC_wrapper")){
        classify = "抄送人";
    }
    curParents.find(".mail_item").each(function(){
        var iindex = $(this).index() + 1;
        $(this).find(".mail_item_info").text(classify+iindex);
    });
});

// 邮件发送邮件
$("#mail_template_submit").click(function(){
	var iThat = $(this);
	var ID = $(".mail_template").attr("value");
	var Subject = $("#i_mail_Subject").val().trim();
	if(Subject === ""){
		$.MsgBox_Unload.Alert("发邮件提示","邮件主题不能为空！");
		return false;
	}
	// var ToList = 'shiconghua@eoulu.com;';
	var iIndex = $(".TO_radio_div>label").index($("input[name='TO_radio']:checked").parent());
	var ToList = $(".TO_wrapper>.mail_item:eq("+iIndex+")").find("input").val() + ';';
	var CopyList = '';
	$(".CC_wrapper .mail_item").each(function(){
	    var inVal = $(this).find("input.form-control").val().trim();
	    if(inVal){
	        CopyList+=inVal;
	        CopyList+=';';
	    }
	});
	iThat.css("cursor","not-allowed").prop("disabled","disabled");
	$.ajax({
		type: "POST",
		url: "PaymentMailSend",
		data: {
			ID: ID,
			Subject: Subject,
			ToList: ToList,
			CopyList: CopyList
		},
		dataType: "json"
	}).then(function(data){
		if(data){
			getDataByPage(Number($("span#currentPage").text()), undefined, undefined);
			$.MsgBox_Unload.Alert("发邮件提示","操作成功！");
		}else{
			$.MsgBox_Unload.Alert("发邮件提示","操作失败！");
		}
	},function(){
		$.MsgBox_Unload.Alert("发邮件提示","服务器繁忙！");
	}).always(function(){
		iThat.css("cursor","pointer").prop("disabled",false);
	});
});

// 付款状态点击
$(document).on("click",".PayState_td[title='未付款']>a",function(){
	PaymentRequestState.payStateValue = $(this).attr("value");
});

// 付款状态发送
$(document).on("click","button.PayState_td_y",function(){
	var iThat = $(this);
	iThat.css("cursor","not-allowed").prop("disabled","disabled");
	$.ajax({
		type: "GET",
		url: "PaymentMailSend",
		data: {
			ID: PaymentRequestState.payStateValue
		},
		dataType: "json"
	}).then(function(data){
		if(data){
			getDataByPage(Number($("span#currentPage").text()), undefined, undefined);
			$.MsgBox_Unload.Alert("付款状态操作提示","更新成功！");
		}else{
			$.MsgBox_Unload.Alert("付款状态操作提示","更新失败！");
		}
	},function(){
		$.MsgBox_Unload.Alert("付款状态操作提示","服务器繁忙！");
	}).always(function(){
		iThat.css("cursor","pointer").prop("disabled",false);
	});
});

// 添加修改和预览里的文件下载
$(document).on("click","li.list-group-item-success, li.list-group-item-info, ol>li",function(){
	var fileName = $(this).attr("title");
	if(!fileName){
		$.MsgBox_Unload.Alert("下载提示","无数据或文件已被删除！");
		return false;
	}
	var downloadURL = globalHostName+'/LogisticsFile/File/PaymentRequest/'+fileName;
	window.open(downloadURL);
});

// 姓名 类型 项目 显隐
$("#global_table_style thead span").click(function(){
	$(this).toggleClass("glyphicon-plus glyphicon-minus");
	if($(this).parent().is(".t3")){
		$("#global_table_style thead").toggleClass("showName");
		$("#global_table_style tbody").toggleClass("showName");
	}else if($(this).parent().is(".t7")){
		$("#global_table_style thead").toggleClass("showCategoryItem");
		$("#global_table_style tbody").toggleClass("showCategoryItem");
	}
});

// popover 互斥
$(document).on("click",".paymoney_detail_td>button",function(e){
	e.stopPropagation();
	var iValue = $(this).parent().siblings(".update_td").data("iid").toString();
	$("#global_table_style>tbody>tr .update_td:not([data-iid='"+iValue+"'])").siblings(".paymoney_detail_td").children("button").popover('hide');
	$("#global_table_style>tbody>tr .update_td[data-iid='"+iValue+"']").siblings(".paymoney_detail_td").children("button").popover('show');
});
$(document).on("click",".link_detail_td>button",function(e){
	e.stopPropagation();
	var iValue = $(this).parent().siblings(".update_td").data("iid").toString();
	$("#global_table_style>tbody>tr .update_td:not([data-iid='"+iValue+"'])").siblings(".link_detail_td").children("button").popover('hide');
	$("#global_table_style>tbody>tr .update_td[data-iid='"+iValue+"']").siblings(".link_detail_td").children("button").popover('show');
});
$(document).on("click",".hastd_Attachment>button",function(e){
	e.stopPropagation();
	var iValue = $(this).parent().siblings(".update_td").data("iid").toString();
	$("#global_table_style>tbody>tr .update_td:not([data-iid='"+iValue+"'])").siblings(".hastd_Attachment").children("button").popover('hide');
	$("#global_table_style>tbody>tr .update_td[data-iid='"+iValue+"']").siblings(".hastd_Attachment").children("button").popover('show');
});

$(document).on("click","div.popover",function(e){
    e.stopPropagation();
});

$(document).on("click",function(e){
    // $("div.popover").remove();
    $('.paymoney_detail_td>button').popover('hide');
    $('.link_detail_td>button').popover('hide');
    $('.hastd_Attachment>button').popover('hide');
});