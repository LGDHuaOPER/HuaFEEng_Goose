/**
 * Bootstrap Multiselect (http://davidstutz.de/bootstrap-multiselect/)
 *
 * Apache License, Version 2.0:
 * Copyright (c) 2012 - 2018 David Stutz
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a
 * copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * BSD 3-Clause License:
 * Copyright (c) 2012 - 2018 David Stutz
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *    - Redistributions of source code must retain the above copyright notice,
 *      this list of conditions and the following disclaimer.
 *    - Redistributions in binary form must reproduce the above copyright notice,
 *      this list of conditions and the following disclaimer in the documentation
 *      and/or other materials provided with the distribution.
 *    - Neither the name of David Stutz nor the names of its contributors may be
 *      used to endorse or promote products derived from this software without
 *      specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
(function (root, factory) {
    // check to see if 'knockout' AMD module is specified if using requirejs
    if (typeof define === 'function' && define.amd &&
        typeof require === 'function' && typeof require.specified === 'function' && require.specified('knockout')) {

        // AMD. Register as an anonymous module.
        define(['jquery', 'knockout'], factory);
    } else {
        // Browser globals
        factory(root.jQuery, root.ko);
    }
})(this, function ($, ko) {
    "use strict";// jshint ;_;

    if (typeof ko !== 'undefined' && ko.bindingHandlers && !ko.bindingHandlers.multiselect) {
        ko.bindingHandlers.multiselect = {
            after: ['options', 'value', 'selectedOptions', 'enable', 'disable'],

            init: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
                var $element = $(element);
                var config = ko.toJS(valueAccessor());

                $element.multiselect(config);

                if (allBindings.has('options')) {
                    var options = allBindings.get('options');
                    if (ko.isObservable(options)) {
                        ko.computed({
                            read: function() {
                                options();
                                setTimeout(function() {
                                    var ms = $element.data('multiselect');
                                    if (ms)
                                        ms.updateOriginalOptions();//Not sure how beneficial this is.
                                    $element.multiselect('rebuild');
                                }, 1);
                            },
                            disposeWhenNodeIsRemoved: element
                        });
                    }
                }

                //value and selectedOptions are two-way, so these will be triggered even by our own actions.
                //It needs some way to tell if they are triggered because of us or because of outside change.
                //It doesn't loop but it's a waste of processing.
                if (allBindings.has('value')) {
                    var value = allBindings.get('value');
                    if (ko.isObservable(value)) {
                        ko.computed({
                            read: function() {
                                value();
                                setTimeout(function() {
                                    $element.multiselect('refresh');
                                }, 1);
                            },
                            disposeWhenNodeIsRemoved: element
                        }).extend({ rateLimit: 100, notifyWhenChangesStop: true });
                    }
                }

                //Switched from arrayChange subscription to general subscription using 'refresh'.
                //Not sure performance is any better using 'select' and 'deselect'.
                if (allBindings.has('selectedOptions')) {
                    var selectedOptions = allBindings.get('selectedOptions');
                    if (ko.isObservable(selectedOptions)) {
                        ko.computed({
                            read: function() {
                                selectedOptions();
                                setTimeout(function() {
                                    $element.multiselect('refresh');
                                }, 1);
                            },
                            disposeWhenNodeIsRemoved: element
                        }).extend({ rateLimit: 100, notifyWhenChangesStop: true });
                    }
                }

                var setEnabled = function (enable) {
                    setTimeout(function () {
                        if (enable)
                            $element.multiselect('enable');
                        else
                            $element.multiselect('disable');
                    });
                };

                if (allBindings.has('enable')) {
                    var enable = allBindings.get('enable');
                    if (ko.isObservable(enable)) {
                        ko.computed({
                            read: function () {
                                setEnabled(enable());
                            },
                            disposeWhenNodeIsRemoved: element
                        }).extend({ rateLimit: 100, notifyWhenChangesStop: true });
                    } else {
                        setEnabled(enable);
                    }
                }

                if (allBindings.has('disable')) {
                    var disable = allBindings.get('disable');
                    if (ko.isObservable(disable)) {
                        ko.computed({
                            read: function () {
                                setEnabled(!disable());
                            },
                            disposeWhenNodeIsRemoved: element
                        }).extend({ rateLimit: 100, notifyWhenChangesStop: true });
                    } else {
                        setEnabled(!disable);
                    }
                }

                ko.utils.domNodeDisposal.addDisposeCallback(element, function() {
                    $element.multiselect('destroy');
                });
            },

            update: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
                var $element = $(element);
                var config = ko.toJS(valueAccessor());

                $element.multiselect('setOptions', config);
                $element.multiselect('rebuild');
            }
        };
    }

    function forEach(array, callback) {
        for (var index = 0; index < array.length; ++index) {
            callback(array[index], index);
        }
    }

    /**
     * Constructor to create a new multiselect using the given select.
     *
     * @param {jQuery} select
     * @param {Object} options
     * @returns {Multiselect}
     */
    function Multiselect(select, options) {

        this.$select = $(select);
        this.options = this.mergeOptions($.extend({}, options, this.$select.data()));

        // Placeholder via data attributes
        if (this.$select.attr("data-placeholder")) {
            this.options.nonSelectedText = this.$select.data("placeholder");
        }

        // Initialization.
        // We have to clone to create a new reference.
        this.originalOptions = this.$select.clone()[0].options;
        this.query = '';
        this.searchTimeout = null;
        this.lastToggledInput = null;

        this.options.multiple = this.$select.attr('multiple') === "multiple";
        this.options.onChange = $.proxy(this.options.onChange, this);
        this.options.onSelectAll = $.proxy(this.options.onSelectAll, this);
        this.options.onDeselectAll = $.proxy(this.options.onDeselectAll, this);
        this.options.onDropdownShow = $.proxy(this.options.onDropdownShow, this);
        this.options.onDropdownHide = $.proxy(this.options.onDropdownHide, this);
        this.options.onDropdownShown = $.proxy(this.options.onDropdownShown, this);
        this.options.onDropdownHidden = $.proxy(this.options.onDropdownHidden, this);
        this.options.onInitialized = $.proxy(this.options.onInitialized, this);
        this.options.onFiltering = $.proxy(this.options.onFiltering, this);

        // Build select all if enabled.
        this.buildContainer();
        this.buildButton();
        this.buildDropdown();
        this.buildReset();
        this.buildSelectAll();
        this.buildDropdownOptions();
        this.buildFilter();

        this.updateButtonText();
        this.updateSelectAll(true);

        if (this.options.enableClickableOptGroups && this.options.multiple) {
            this.updateOptGroups();
        }

        this.options.wasDisabled = this.$select.prop('disabled');
        if (this.options.disableIfEmpty && $('option', this.$select).length <= 0) {
            this.disable();
        }

        this.$select.wrap('<span class="multiselect-native-select" />').after(this.$container);
        this.options.onInitialized(this.$select, this.$container);
    }

    Multiselect.prototype = {

        defaults: {
            /**
             * Default text function will either print 'None selected' in case no
             * option is selected or a list of the selected options up to a length
             * of 3 selected options.
             *
             * @param {jQuery} options
             * @param {jQuery} select
             * @returns {String}
             */
            buttonText: function(options, select) {
                if (this.disabledText.length > 0
                        && (select.prop('disabled') || (options.length == 0 && this.disableIfEmpty)))  {

                    return this.disabledText;
                }
                else if (options.length === 0) {
                    return this.nonSelectedText;
                }
                else if (this.allSelectedText
                        && options.length === $('option', $(select)).length
                        && $('option', $(select)).length !== 1
                        && this.multiple) {

                    if (this.selectAllNumber) {
                        return this.allSelectedText + ' (' + options.length + ')';
                    }
                    else {
                        return this.allSelectedText;
                    }
                }
                else if (this.numberDisplayed != 0 && options.length > this.numberDisplayed) {
                    return options.length + ' ' + this.nSelectedText;
                }
                else {
                    var selected = '';
                    var delimiter = this.delimiterText;

                    options.each(function() {
                        var label = ($(this).attr('label') !== undefined) ? $(this).attr('label') : $(this).text();
                        selected += label + delimiter;
                    });

                    return selected.substr(0, selected.length - this.delimiterText.length);
                }
            },
            /**
             * Updates the title of the button similar to the buttonText function.
             *
             * @param {jQuery} options
             * @param {jQuery} select
             * @returns {@exp;selected@call;substr}
             */
            buttonTitle: function(options, select) {
                if (options.length === 0) {
                    return this.nonSelectedText;
                }
                else {
                    var selected = '';
                    var delimiter = this.delimiterText;

                    options.each(function () {
                        var label = ($(this).attr('label') !== undefined) ? $(this).attr('label') : $(this).text();
                        selected += label + delimiter;
                    });
                    return selected.substr(0, selected.length - this.delimiterText.length);
                }
            },
            checkboxName: function(option) {
                return false; // no checkbox name
            },
            /**
             * Create a label.
             *
             * @param {jQuery} element
             * @returns {String}
             */
            optionLabel: function(element){
                return $(element).attr('label') || $(element).text();
            },
            /**
             * Create a class.
             *
             * @param {jQuery} element
             * @returns {String}
             */
            optionClass: function(element) {
                return $(element).attr('class') || '';
            },
            /**
             * Triggered on change of the multiselect.
             *
             * Not triggered when selecting/deselecting options manually.
             *
             * @param {jQuery} option
             * @param {Boolean} checked
             */
            onChange : function(option, checked) {

            },
            /**
             * Triggered when the dropdown is shown.
             *
             * @param {jQuery} event
             */
            onDropdownShow: function(event) {

            },
            /**
             * Triggered when the dropdown is hidden.
             *
             * @param {jQuery} event
             */
            onDropdownHide: function(event) {

            },
            /**
             * Triggered after the dropdown is shown.
             *
             * @param {jQuery} event
             */
            onDropdownShown: function(event) {

            },
            /**
             * Triggered after the dropdown is hidden.
             *
             * @param {jQuery} event
             */
            onDropdownHidden: function(event) {

            },
            /**
             * Triggered on select all.
             */
            onSelectAll: function() {

            },
            /**
             * Triggered on deselect all.
             */
            onDeselectAll: function() {

            },
            /**
             * Triggered after initializing.
             *
             * @param {jQuery} $select
             * @param {jQuery} $container
             */
            onInitialized: function($select, $container) {

            },
            /**
             * Triggered on filtering.
             *
             * @param {jQuery} $filter
             */
            onFiltering: function($filter) {

            },
            enableHTML: false,
            buttonClass: 'btn btn-default',
            inheritClass: false,
            buttonWidth: 'auto',
            buttonContainer: '<div class="btn-group" />',
            dropRight: false,
            dropUp: false,
            selectedClass: 'active',
            // Maximum height of the dropdown menu.
            // If maximum height is exceeded a scrollbar will be displayed.
            maxHeight: false,
            includeSelectAllOption: false,
            includeSelectAllIfMoreThan: 0,
            selectAllText: ' Select all',
            selectAllValue: 'multiselect-all',
            selectAllName: false,
            selectAllNumber: true,
            selectAllJustVisible: true,
            enableFiltering: false,
            enableCaseInsensitiveFiltering: false,
            enableFullValueFiltering: false,
            enableClickableOptGroups: false,
            enableCollapsibleOptGroups: false,
            collapseOptGroupsByDefault: false,
            filterPlaceholder: 'Search',
            // possible options: 'text', 'value', 'both'
            filterBehavior: 'text',
            includeFilterClearBtn: true,
            preventInputChangeEvent: false,
            nonSelectedText: 'None selected',
            nSelectedText: 'selected',
            allSelectedText: 'All selected',
            numberDisplayed: 3,
            disableIfEmpty: false,
            disabledText: '',
            delimiterText: ', ',
            includeResetOption: false,
            includeResetDivider: false,
            resetText: 'Reset',
            templates: {
                button: '<button type="button" class="multiselect dropdown-toggle" data-toggle="dropdown"><span class="multiselect-selected-text"></span> <b class="caret"></b></button>',
                ul: '<ul class="multiselect-container dropdown-menu"></ul>',
                filter: '<li class="multiselect-item multiselect-filter"><div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span><input class="form-control multiselect-search" type="text" /></div></li>',
                filterClearBtn: '<span class="input-group-btn"><button class="btn btn-default multiselect-clear-filter" type="button"><i class="glyphicon glyphicon-remove-circle"></i></button></span>',
                li: '<li><a tabindex="0"><label></label></a></li>',
                divider: '<li class="multiselect-item divider"></li>',
                liGroup: '<li class="multiselect-item multiselect-group"><label></label></li>',
                resetButton: '<li class="multiselect-reset text-center"><div class="input-group"><a class="btn btn-default btn-block"></a></div></li>'
            }
        },

        constructor: Multiselect,

        /**
         * Builds the container of the multiselect.
         */
        buildContainer: function() {
            this.$container = $(this.options.buttonContainer);
            this.$container.on('show.bs.dropdown', this.options.onDropdownShow);
            this.$container.on('hide.bs.dropdown', this.options.onDropdownHide);
            this.$container.on('shown.bs.dropdown', this.options.onDropdownShown);
            this.$container.on('hidden.bs.dropdown', this.options.onDropdownHidden);
        },

        /**
         * Builds the button of the multiselect.
         */
        buildButton: function() {
            this.$button = $(this.options.templates.button).addClass(this.options.buttonClass);
            if (this.$select.attr('class') && this.options.inheritClass) {
                this.$button.addClass(this.$select.attr('class'));
            }
            // Adopt active state.
            if (this.$select.prop('disabled')) {
                this.disable();
            }
            else {
                this.enable();
            }

            // Manually add button width if set.
            if (this.options.buttonWidth && this.options.buttonWidth !== 'auto') {
                this.$button.css({
                    'width' : '100%', //this.options.buttonWidth,
                    'overflow' : 'hidden',
                    'text-overflow' : 'ellipsis'
                });
                this.$container.css({
                    'width': this.options.buttonWidth
                });
            }

            // Keep the tab index from the select.
            var tabindex = this.$select.attr('tabindex');
            if (tabindex) {
                this.$button.attr('tabindex', tabindex);
            }

            this.$container.prepend(this.$button);
        },

        /**
         * Builds the ul representing the dropdown menu.
         */
        buildDropdown: function() {

            // Build ul.
            this.$ul = $(this.options.templates.ul);

            if (this.options.dropRight) {
                this.$ul.addClass('pull-right');
            }

            // Set max height of dropdown menu to activate auto scrollbar.
            if (this.options.maxHeight) {
                // TODO: Add a class for this option to move the css declarations.
                this.$ul.css({
                    'max-height': this.options.maxHeight + 'px',
                    'overflow-y': 'auto',
                    'overflow-x': 'hidden'
                });
            }

            if (this.options.dropUp) {

                var height = Math.min(this.options.maxHeight, $('option[data-role!="divider"]', this.$select).length*26 + $('option[data-role="divider"]', this.$select).length*19 + (this.options.includeSelectAllOption ? 26 : 0) + (this.options.enableFiltering || this.options.enableCaseInsensitiveFiltering ? 44 : 0));
                var moveCalc = height + 34;

                this.$ul.css({
                    'max-height': height + 'px',
                    'overflow-y': 'auto',
                    'overflow-x': 'hidden',
                    'margin-top': "-" + moveCalc + 'px'
                });
            }

            this.$container.append(this.$ul);
        },

        /**
         * Build the dropdown options and binds all necessary events.
         *
         * Uses createDivider and createOptionValue to create the necessary options.
         */
        buildDropdownOptions: function() {

            this.$select.children().each($.proxy(function(index, element) {

                var $element = $(element);
                // Support optgroups and options without a group simultaneously.
                var tag = $element.prop('tagName')
                    .toLowerCase();

                if ($element.prop('value') === this.options.selectAllValue) {
                    return;
                }

                if (tag === 'optgroup') {
                    this.createOptgroup(element);
                }
                else if (tag === 'option') {

                    if ($element.data('role') === 'divider') {
                        this.createDivider();
                    }
                    else {
                        this.createOptionValue(element);
                    }

                }

                // Other illegal tags will be ignored.
            }, this));

            // Bind the change event on the dropdown elements.
            $(this.$ul).off('change', 'li:not(.multiselect-group) input[type="checkbox"], li:not(.multiselect-group) input[type="radio"]');
            $(this.$ul).on('change', 'li:not(.multiselect-group) input[type="checkbox"], li:not(.multiselect-group) input[type="radio"]', $.proxy(function(event) {
                var $target = $(event.target);

                var checked = $target.prop('checked') || false;
                var isSelectAllOption = $target.val() === this.options.selectAllValue;

                // Apply or unapply the configured selected class.
                if (this.options.selectedClass) {
                    if (checked) {
                        $target.closest('li')
                            .addClass(this.options.selectedClass);
                    }
                    else {
                        $target.closest('li')
                            .removeClass(this.options.selectedClass);
                    }
                }

                // Get the corresponding option.
                var value = $target.val();
                var $option = this.getOptionByValue(value);

                var $optionsNotThis = $('option', this.$select).not($option);
                var $checkboxesNotThis = $('input', this.$container).not($target);

                if (isSelectAllOption) {

                    if (checked) {
                        this.selectAll(this.options.selectAllJustVisible, true);
                    }
                    else {
                        this.deselectAll(this.options.selectAllJustVisible, true);
                    }
                }
                else {
                    if (checked) {
                        $option.prop('selected', true);

                        if (this.options.multiple) {
                            // Simply select additional option.
                            $option.prop('selected', true);
                        }
                        else {
                            // Unselect all other options and corresponding checkboxes.
                            if (this.options.selectedClass) {
                                $($checkboxesNotThis).closest('li').removeClass(this.options.selectedClass);
                            }

                            $($checkboxesNotThis).prop('checked', false);
                            $optionsNotThis.prop('selected', false);

                            // It's a single selection, so close.
                            this.$button.click();
                        }

                        if (this.options.selectedClass === "active") {
                            $optionsNotThis.closest("a").css("outline", "");
                        }
                    }
                    else {
                        // Unselect option.
                        $option.prop('selected', false);
                    }

                    // To prevent select all from firing onChange: #575
                    this.options.onChange($option, checked);

                    // Do not update select all or optgroups on select all change!
                    this.updateSelectAll();

                    if (this.options.enableClickableOptGroups && this.options.multiple) {
                        this.updateOptGroups();
                    }
                }

                this.$select.change();
                this.updateButtonText();

                if(this.options.preventInputChangeEvent) {
                    return false;
                }
            }, this));

            $('li a', this.$ul).on('mousedown', function(e) {
                if (e.shiftKey) {
                    // Prevent selecting text by Shift+click
                    return false;
                }
            });

            $(this.$ul).on('touchstart click', 'li a', $.proxy(function(event) {
                event.stopPropagation();

                var $target = $(event.target);

                if (event.shiftKey && this.options.multiple) {
                    if($target.is("label")){ // Handles checkbox selection manually (see https://github.com/davidstutz/bootstrap-multiselect/issues/431)
                        event.preventDefault();
                        $target = $target.find("input");
                        $target.prop("checked", !$target.prop("checked"));
                    }
                    var checked = $target.prop('checked') || false;

                    if (this.lastToggledInput !== null && this.lastToggledInput !== $target) { // Make sure we actually have a range
                        var from = this.$ul.find("li:visible").index($target.parents("li"));
                        var to = this.$ul.find("li:visible").index(this.lastToggledInput.parents("li"));

                        if (from > to) { // Swap the indices
                            var tmp = to;
                            to = from;
                            from = tmp;
                        }

                        // Make sure we grab all elements since slice excludes the last index
                        ++to;

                        // Change the checkboxes and underlying options
                        var range = this.$ul.find("li").not(".multiselect-filter-hidden").slice(from, to).find("input");

                        range.prop('checked', checked);

                        if (this.options.selectedClass) {
                            range.closest('li')
                                .toggleClass(this.options.selectedClass, checked);
                        }

                        for (var i = 0, j = range.length; i < j; i++) {
                            var $checkbox = $(range[i]);

                            var $option = this.getOptionByValue($checkbox.val());

                            $option.prop('selected', checked);
                        }
                    }

                    // Trigger the select "change" event
                    $target.trigger("change");
                }

                // Remembers last clicked option
                if($target.is("input") && !$target.closest("li").is(".multiselect-item")){
                    this.lastToggledInput = $target;
                }

                $target.blur();
            }, this));

            // Keyboard support.
            this.$container.off('keydown.multiselect').on('keydown.multiselect', $.proxy(function(event) {
                if ($('input[type="text"]', this.$container).is(':focus')) {
                    return;
                }

                if (event.keyCode === 9 && this.$container.hasClass('open')) {
                    this.$button.click();
                }
                else {
                    var $items = $(this.$container).find("li:not(.divider):not(.disabled) a").filter(":visible");

                    if (!$items.length) {
                        return;
                    }

                    var index = $items.index($items.filter(':focus'));
                    
                    // Navigation up.
                    if (event.keyCode === 38 && index > 0) {
                        index--;
                    }
                    // Navigate down.
                    else if (event.keyCode === 40 && index < $items.length - 1) {
                        index++;
                    }
                    else if (!~index) {
                        index = 0;
                    }

                    var $current = $items.eq(index);
                    $current.focus();

                    if (event.keyCode === 32 || event.keyCode === 13) {
                        var $checkbox = $current.find('input');

                        $checkbox.prop("checked", !$checkbox.prop("checked"));
                        $checkbox.change();
                    }

                    event.stopPropagation();
                    event.preventDefault();
                }
            }, this));

            if (this.options.enableClickableOptGroups && this.options.multiple) {
                $("li.multiselect-group input", this.$ul).on("change", $.proxy(function(event) {
                    event.stopPropagation();

                    var $target = $(event.target);
                    var checked = $target.prop('checked') || false;

                    var $li = $(event.target).closest('li');
                    var $group = $li.nextUntil("li.multiselect-group")
                        .not('.multiselect-filter-hidden')
                        .not('.disabled');

                    var $inputs = $group.find("input");

                    var values = [];
                    var $options = [];

                    if (this.options.selectedClass) {
                        if (checked) {
                            $li.addClass(this.options.selectedClass);
                        }
                        else {
                            $li.removeClass(this.options.selectedClass);
                        }
                    }

                    $.each($inputs, $.proxy(function(index, input) {
                        var value = $(input).val();
                        var $option = this.getOptionByValue(value);

                        if (checked) {
                            $(input).prop('checked', true);
                            $(input).closest('li')
                                .addClass(this.options.selectedClass);

                            $option.prop('selected', true);
                        }
                        else {
                            $(input).prop('checked', false);
                            $(input).closest('li')
                                .removeClass(this.options.selectedClass);

                            $option.prop('selected', false);
                        }

                        $options.push(this.getOptionByValue(value));
                    }, this))

                    // Cannot use select or deselect here because it would call updateOptGroups again.

                    this.options.onChange($options, checked);

                    this.$select.change();
                    this.updateButtonText();
                    this.updateSelectAll();
                }, this));
            }

            if (this.options.enableCollapsibleOptGroups && this.options.multiple) {
                $("li.multiselect-group .caret-container", this.$ul).on("click", $.proxy(function(event) {
                    var $li = $(event.target).closest('li');
                    var $inputs = $li.nextUntil("li.multiselect-group")
                            .not('.multiselect-filter-hidden');

                    var visible = true;
                    $inputs.each(function() {
                        visible = visible && !$(this).hasClass('multiselect-collapsible-hidden');
                    });

                    if (visible) {
                        $inputs.hide()
                            .addClass('multiselect-collapsible-hidden');
                    }
                    else {
                        $inputs.show()
                            .removeClass('multiselect-collapsible-hidden');
                    }
                }, this));

                $("li.multiselect-all", this.$ul).css('background', '#f3f3f3').css('border-bottom', '1px solid #eaeaea');
                $("li.multiselect-all > a > label.checkbox", this.$ul).css('padding', '3px 20px 3px 35px');
                $("li.multiselect-group > a > input", this.$ul).css('margin', '4px 0px 5px -20px');
            }
        },

        /**
         * Create an option using the given select option.
         *
         * @param {jQuery} element
         */
        createOptionValue: function(element) {
            var $element = $(element);
            if ($element.is(':selected')) {
                $element.prop('selected', true);
            }

            // Support the label attribute on options.
            var label = this.options.optionLabel(element);
            var classes = this.options.optionClass(element);
            var value = $element.val();
            var inputType = this.options.multiple ? "checkbox" : "radio";

            var $li = $(this.options.templates.li);
            var $label = $('label', $li);
            $label.addClass(inputType);
            $label.attr("title", label);
            $li.addClass(classes);

            // Hide all children items when collapseOptGroupsByDefault is true
            if (this.options.collapseOptGroupsByDefault && $(element).parent().prop("tagName").toLowerCase() === "optgroup") {
                $li.addClass("multiselect-collapsible-hidden");
                $li.hide();
            }

            if (this.options.enableHTML) {
                $label.html(" " + label);
            }
            else {
                $label.text(" " + label);
            }

            var $checkbox = $('<input/>').attr('type', inputType);

            var name = this.options.checkboxName($element);
            if (name) {
                $checkbox.attr('name', name);
            }

            $label.prepend($checkbox);

            var selected = $element.prop('selected') || false;
            $checkbox.val(value);

            if (value === this.options.selectAllValue) {
                $li.addClass("multiselect-item multiselect-all");
                $checkbox.parent().parent()
                    .addClass('multiselect-all');
            }

            $label.attr('title', $element.attr('title'));

            this.$ul.append($li);

            if ($element.is(':disabled')) {
                $checkbox.attr('disabled', 'disabled')
                    .prop('disabled', true)
                    .closest('a')
                    .attr("tabindex", "-1")
                    .closest('li')
                    .addClass('disabled');
            }

            $checkbox.prop('checked', selected);

            if (selected && this.options.selectedClass) {
                $checkbox.closest('li')
                    .addClass(this.options.selectedClass);
            }
        },

        /**
         * Creates a divider using the given select option.
         *
         * @param {jQuery} element
         */
        createDivider: function(element) {
            var $divider = $(this.options.templates.divider);
            this.$ul.append($divider);
        },

        /**
         * Creates an optgroup.
         *
         * @param {jQuery} group
         */
        createOptgroup: function(group) {
            var label = $(group).attr("label");
            var value = $(group).attr("value");
            var $li = $('<li class="multiselect-item multiselect-group"><a href="javascript:void(0);"><label><b></b></label></a></li>');

            var classes = this.options.optionClass(group);
            $li.addClass(classes);

            if (this.options.enableHTML) {
                $('label b', $li).html(" " + label);
            }
            else {
                $('label b', $li).text(" " + label);
            }

            if (this.options.enableCollapsibleOptGroups && this.options.multiple) {
                $('a', $li).append('<span class="caret-container"><b class="caret"></b></span>');
            }

            if (this.options.enableClickableOptGroups && this.options.multiple) {
                $('a label', $li).prepend('<input type="checkbox" value="' + value + '"/>');
            }

            if ($(group).is(':disabled')) {
                $li.addClass('disabled');
            }

            this.$ul.append($li);

            $("option", group).each($.proxy(function($, group) {
                this.createOptionValue(group);
            }, this))
        },

        /**
         * Build the reset.
         *
         */
        buildReset: function() {
            if (this.options.includeResetOption) {

                // Check whether to add a divider after the reset.
                if (this.options.includeResetDivider) {
                    this.$ul.prepend($(this.options.templates.divider));
                }

                var $resetButton = $(this.options.templates.resetButton);

                if (this.options.enableHTML) {
                    $('a', $resetButton).html(this.options.resetText);
                }
                else {
                    $('a', $resetButton).text(this.options.resetText);
                }

                $('a', $resetButton).click($.proxy(function(){
                    this.clearSelection();
                }, this));

                this.$ul.prepend($resetButton);
            }
        },

        /**
         * Build the select all.
         *
         * Checks if a select all has already been created.
         */
        buildSelectAll: function() {
            if (typeof this.options.selectAllValue === 'number') {
                this.options.selectAllValue = this.options.selectAllValue.toString();
            }

            var alreadyHasSelectAll = this.hasSelectAll();

            if (!alreadyHasSelectAll && this.options.includeSelectAllOption && this.options.multiple
                    && $('option', this.$select).length > this.options.includeSelectAllIfMoreThan) {

                // Check whether to add a divider after the select all.
                if (this.options.includeSelectAllDivider) {
                    this.$ul.prepend($(this.options.templates.divider));
                }

                var $li = $(this.options.templates.li);
                $('label', $li).addClass("checkbox");

                if (this.options.enableHTML) {
                    $('label', $li).html(" " + this.options.selectAllText);
                }
                else {
                    $('label', $li).text(" " + this.options.selectAllText);
                }

                if (this.options.selectAllName) {
                    $('label', $li).prepend('<input type="checkbox" name="' + this.options.selectAllName + '" />');
                }
                else {
                    $('label', $li).prepend('<input type="checkbox" />');
                }

                var $checkbox = $('input', $li);
                $checkbox.val(this.options.selectAllValue);

                $li.addClass("multiselect-item multiselect-all");
                $checkbox.parent().parent()
                    .addClass('multiselect-all');

                this.$ul.prepend($li);

                $checkbox.prop('checked', false);
            }
        },

        /**
         * Builds the filter.
         */
        buildFilter: function() {

            // Build filter if filtering OR case insensitive filtering is enabled and the number of options exceeds (or equals) enableFilterLength.
            if (this.options.enableFiltering || this.options.enableCaseInsensitiveFiltering) {
                var enableFilterLength = Math.max(this.options.enableFiltering, this.options.enableCaseInsensitiveFiltering);

                if (this.$select.find('option').length >= enableFilterLength) {

                    this.$filter = $(this.options.templates.filter);
                    $('input', this.$filter).attr('placeholder', this.options.filterPlaceholder);

                    // Adds optional filter clear button
                    if(this.options.includeFilterClearBtn) {
                        var clearBtn = $(this.options.templates.filterClearBtn);
                        clearBtn.on('click', $.proxy(function(event){
                            clearTimeout(this.searchTimeout);

                            this.query = '';
                            this.$filter.find('.multiselect-search').val('');
                            $('li', this.$ul).show().removeClass('multiselect-filter-hidden');

                            this.updateSelectAll();

                            if (this.options.enableClickableOptGroups && this.options.multiple) {
                                this.updateOptGroups();
                            }

                        }, this));
                        this.$filter.find('.input-group').append(clearBtn);
                    }

                    this.$ul.prepend(this.$filter);

                    this.$filter.val(this.query).on('click', function(event) {
                        event.stopPropagation();
                    }).on('input keydown', $.proxy(function(event) {
                        // Cancel enter key default behaviour
                        if (event.which === 13) {
                          event.preventDefault();
                      }

                        // This is useful to catch "keydown" events after the browser has updated the control.
                        clearTimeout(this.searchTimeout);

                        this.searchTimeout = this.asyncFunction($.proxy(function() {

                            if (this.query !== event.target.value) {
                                this.query = event.target.value;

                                var currentGroup, currentGroupVisible;
                                $.each($('li', this.$ul), $.proxy(function(index, element) {
                                    var value = $('input', element).length > 0 ? $('input', element).val() : "";
                                    var text = $('label', element).text();

                                    var filterCandidate = '';
                                    if ((this.options.filterBehavior === 'text')) {
                                        filterCandidate = text;
                                    }
                                    else if ((this.options.filterBehavior === 'value')) {
                                        filterCandidate = value;
                                    }
                                    else if (this.options.filterBehavior === 'both') {
                                        filterCandidate = text + '\n' + value;
                                    }

                                    if (value !== this.options.selectAllValue && text) {

                                        // By default lets assume that element is not
                                        // interesting for this search.
                                        var showElement = false;

                                        if (this.options.enableCaseInsensitiveFiltering) {
                                            filterCandidate = filterCandidate.toLowerCase();
                                            this.query = this.query.toLowerCase();
                                        }

                                        if (this.options.enableFullValueFiltering && this.options.filterBehavior !== 'both') {
                                            var valueToMatch = filterCandidate.trim().substring(0, this.query.length);
                                            if (this.query.indexOf(valueToMatch) > -1) {
                                                showElement = true;
                                            }
                                        }
                                        else if (filterCandidate.indexOf(this.query) > -1) {
                                            showElement = true;
                                        }

                                        // Toggle current element (group or group item) according to showElement boolean.
                                        if(!showElement){
                                          $(element).css('display', 'none');
                                          $(element).addClass('multiselect-filter-hidden');
                                        }
                                        if(showElement){
                                          $(element).css('display', 'block');
                                          $(element).removeClass('multiselect-filter-hidden');
                                        }

                                        // Differentiate groups and group items.
                                        if ($(element).hasClass('multiselect-group')) {
                                            // Remember group status.
                                            currentGroup = element;
                                            currentGroupVisible = showElement;
                                        }
                                        else {
                                            // Show group name when at least one of its items is visible.
                                            if (showElement) {
                                                $(currentGroup).show()
                                                    .removeClass('multiselect-filter-hidden');
                                            }

                                            // Show all group items when group name satisfies filter.
                                            if (!showElement && currentGroupVisible) {
                                                $(element).show()
                                                    .removeClass('multiselect-filter-hidden');
                                            }
                                        }
                                    }
                                }, this));
                            }

                            this.updateSelectAll();

                            if (this.options.enableClickableOptGroups && this.options.multiple) {
                                this.updateOptGroups();
                            }

                            this.options.onFiltering(event.target);

                        }, this), 300, this);
                    }, this));
                }
            }
        },

        /**
         * Unbinds the whole plugin.
         */
        destroy: function() {
            this.$container.remove();
            this.$select.show();

            // reset original state
            this.$select.prop('disabled', this.options.wasDisabled);

            this.$select.data('multiselect', null);
        },

        /**
         * Refreshs the multiselect based on the selected options of the select.
         */
        refresh: function () {
            var inputs = {};
            $('li input', this.$ul).each(function() {
              inputs[$(this).val()] = $(this);
            });

            $('option', this.$select).each($.proxy(function (index, element) {
                var $elem = $(element);
                var $input = inputs[$(element).val()];

                if ($elem.is(':selected')) {
                    $input.prop('checked', true);

                    if (this.options.selectedClass) {
                        $input.closest('li')
                            .addClass(this.options.selectedClass);
                    }
                }
                else {
                    $input.prop('checked', false);

                    if (this.options.selectedClass) {
                        $input.closest('li')
                            .removeClass(this.options.selectedClass);
                    }
                }

                if ($elem.is(":disabled")) {
                    $input.attr('disabled', 'disabled')
                        .prop('disabled', true)
                        .closest('li')
                        .addClass('disabled');
                }
                else {
                    $input.prop('disabled', false)
                        .closest('li')
                        .removeClass('disabled');
                }
            }, this));

            this.updateButtonText();
            this.updateSelectAll();

            if (this.options.enableClickableOptGroups && this.options.multiple) {
                this.updateOptGroups();
            }
        },

        /**
         * Select all options of the given values.
         *
         * If triggerOnChange is set to true, the on change event is triggered if
         * and only if one value is passed.
         *
         * @param {Array} selectValues
         * @param {Boolean} triggerOnChange
         */
        select: function(selectValues, triggerOnChange) {
            if(!$.isArray(selectValues)) {
                selectValues = [selectValues];
            }

            for (var i = 0; i < selectValues.length; i++) {
                var value = selectValues[i];

                if (value === null || value === undefined) {
                    continue;
                }

                var $option = this.getOptionByValue(value);
                var $checkbox = this.getInputByValue(value);

                if($option === undefined || $checkbox === undefined) {
                    continue;
                }

                if (!this.options.multiple) {
                    this.deselectAll(false);
                }

                if (this.options.selectedClass) {
                    $checkbox.closest('li')
                        .addClass(this.options.selectedClass);
                }

                $checkbox.prop('checked', true);
                $option.prop('selected', true);

                if (triggerOnChange) {
                    this.options.onChange($option, true);
                }
            }

            this.updateButtonText();
            this.updateSelectAll();

            if (this.options.enableClickableOptGroups && this.options.multiple) {
                this.updateOptGroups();
            }
        },

        /**
         * Clears all selected items.
         */
        clearSelection: function () {
            this.deselectAll(false);
            this.updateButtonText();
            this.updateSelectAll();

            if (this.options.enableClickableOptGroups && this.options.multiple) {
                this.updateOptGroups();
            }
        },

        /**
         * Deselects all options of the given values.
         *
         * If triggerOnChange is set to true, the on change event is triggered, if
         * and only if one value is passed.
         *
         * @param {Array} deselectValues
         * @param {Boolean} triggerOnChange
         */
        deselect: function(deselectValues, triggerOnChange) {
            if(!$.isArray(deselectValues)) {
                deselectValues = [deselectValues];
            }

            for (var i = 0; i < deselectValues.length; i++) {
                var value = deselectValues[i];

                if (value === null || value === undefined) {
                    continue;
                }

                var $option = this.getOptionByValue(value);
                var $checkbox = this.getInputByValue(value);

                if($option === undefined || $checkbox === undefined) {
                    continue;
                }

                if (this.options.selectedClass) {
                    $checkbox.closest('li')
                        .removeClass(this.options.selectedClass);
                }

                $checkbox.prop('checked', false);
                $option.prop('selected', false);

                if (triggerOnChange) {
                    this.options.onChange($option, false);
                }
            }

            this.updateButtonText();
            this.updateSelectAll();

            if (this.options.enableClickableOptGroups && this.options.multiple) {
                this.updateOptGroups();
            }
        },

        /**
         * Selects all enabled & visible options.
         *
         * If justVisible is true or not specified, only visible options are selected.
         *
         * @param {Boolean} justVisible
         * @param {Boolean} triggerOnSelectAll
         */
        selectAll: function (justVisible, triggerOnSelectAll) {

            var justVisible = typeof justVisible === 'undefined' ? true : justVisible;
            var allLis = $("li:not(.divider):not(.disabled):not(.multiselect-group)", this.$ul);
            var visibleLis = $("li:not(.divider):not(.disabled):not(.multiselect-group):not(.multiselect-filter-hidden):not(.multiselect-collapisble-hidden)", this.$ul).filter(':visible');

            if(justVisible) {
                $('input:enabled' , visibleLis).prop('checked', true);
                visibleLis.addClass(this.options.selectedClass);

                $('input:enabled' , visibleLis).each($.proxy(function(index, element) {
                    var value = $(element).val();
                    var option = this.getOptionByValue(value);
                    $(option).prop('selected', true);
                }, this));
            }
            else {
                $('input:enabled' , allLis).prop('checked', true);
                allLis.addClass(this.options.selectedClass);

                $('input:enabled' , allLis).each($.proxy(function(index, element) {
                    var value = $(element).val();
                    var option = this.getOptionByValue(value);
                    $(option).prop('selected', true);
                }, this));
            }

            $('li input[value="' + this.options.selectAllValue + '"]', this.$ul).prop('checked', true);

            if (this.options.enableClickableOptGroups && this.options.multiple) {
                this.updateOptGroups();
            }

            if (triggerOnSelectAll) {
                this.options.onSelectAll();
            }
        },

        /**
         * Deselects all options.
         *
         * If justVisible is true or not specified, only visible options are deselected.
         *
         * @param {Boolean} justVisible
         */
        deselectAll: function (justVisible, triggerOnDeselectAll) {

            var justVisible = typeof justVisible === 'undefined' ? true : justVisible;
            var allLis = $("li:not(.divider):not(.disabled):not(.multiselect-group)", this.$ul);
            var visibleLis = $("li:not(.divider):not(.disabled):not(.multiselect-group):not(.multiselect-filter-hidden):not(.multiselect-collapisble-hidden)", this.$ul).filter(':visible');

            if(justVisible) {
                $('input[type="checkbox"]:enabled' , visibleLis).prop('checked', false);
                visibleLis.removeClass(this.options.selectedClass);

                $('input[type="checkbox"]:enabled' , visibleLis).each($.proxy(function(index, element) {
                    var value = $(element).val();
                    var option = this.getOptionByValue(value);
                    $(option).prop('selected', false);
                }, this));
            }
            else {
                $('input[type="checkbox"]:enabled' , allLis).prop('checked', false);
                allLis.removeClass(this.options.selectedClass);

                $('input[type="checkbox"]:enabled' , allLis).each($.proxy(function(index, element) {
                    var value = $(element).val();
                    var option = this.getOptionByValue(value);
                    $(option).prop('selected', false);
                }, this));
            }

            $('li input[value="' + this.options.selectAllValue + '"]', this.$ul).prop('checked', false);

            if (this.options.enableClickableOptGroups && this.options.multiple) {
                this.updateOptGroups();
            }

            if (triggerOnDeselectAll) {
                this.options.onDeselectAll();
            }
        },

        /**
         * Rebuild the plugin.
         *
         * Rebuilds the dropdown, the filter and the select all option.
         */
        rebuild: function() {
            this.$ul.html('');

            // Important to distinguish between radios and checkboxes.
            this.options.multiple = this.$select.attr('multiple') === "multiple";

            this.buildSelectAll();
            this.buildDropdownOptions();
            this.buildFilter();

            this.updateButtonText();
            this.updateSelectAll(true);

            if (this.options.enableClickableOptGroups && this.options.multiple) {
                this.updateOptGroups();
            }

            if (this.options.disableIfEmpty && $('option', this.$select).length <= 0) {
                this.disable();
            }
            else {
                this.enable();
            }

            if (this.options.dropRight) {
                this.$ul.addClass('pull-right');
            }
        },

        /**
         * The provided data will be used to build the dropdown.
         */
        dataprovider: function(dataprovider) {

            var groupCounter = 0;
            var $select = this.$select.empty();

            $.each(dataprovider, function (index, option) {
                var $tag;

                if ($.isArray(option.children)) { // create optiongroup tag
                    groupCounter++;

                    $tag = $('<optgroup/>').attr({
                        label: option.label || 'Group ' + groupCounter,
                        disabled: !!option.disabled,
                        value: option.value
                    });

                    forEach(option.children, function(subOption) { // add children option tags
                        var attributes = {
                            value: subOption.value,
                            label: subOption.label || subOption.value,
                            title: subOption.title,
                            selected: !!subOption.selected,
                            disabled: !!subOption.disabled
                        };

                        //Loop through attributes object and add key-value for each attribute
                       for (var key in subOption.attributes) {
                            attributes['data-' + key] = subOption.attributes[key];
                       }
                         //Append original attributes + new data attributes to option
                        $tag.append($('<option/>').attr(attributes));
                    });
                }
                else {

                    var attributes = {
                        'value': option.value,
                        'label': option.label || option.value,
                        'title': option.title,
                        'class': option['class'],
                        'selected': !!option['selected'],
                        'disabled': !!option['disabled']
                    };
                    //Loop through attributes object and add key-value for each attribute
                    for (var key in option.attributes) {
                      attributes['data-' + key] = option.attributes[key];
                    }
                    //Append original attributes + new data attributes to option
                    $tag = $('<option/>').attr(attributes);

                    $tag.text(option.label || option.value);
                }

                $select.append($tag);
            });

            this.rebuild();
        },

        /**
         * Enable the multiselect.
         */
        enable: function() {
            this.$select.prop('disabled', false);
            this.$button.prop('disabled', false)
                .removeClass('disabled');
        },

        /**
         * Disable the multiselect.
         */
        disable: function() {
            this.$select.prop('disabled', true);
            this.$button.prop('disabled', true)
                .addClass('disabled');
        },

        /**
         * Set the options.
         *
         * @param {Array} options
         */
        setOptions: function(options) {
            this.options = this.mergeOptions(options);
        },

        /**
         * Merges the given options with the default options.
         *
         * @param {Array} options
         * @returns {Array}
         */
        mergeOptions: function(options) {
            return $.extend(true, {}, this.defaults, this.options, options);
        },

        /**
         * Checks whether a select all checkbox is present.
         *
         * @returns {Boolean}
         */
        hasSelectAll: function() {
            return $('li.multiselect-all', this.$ul).length > 0;
        },

        /**
         * Update opt groups.
         */
        updateOptGroups: function() {
            var $groups = $('li.multiselect-group', this.$ul)
            var selectedClass = this.options.selectedClass;

            $groups.each(function() {
                var $options = $(this).nextUntil('li.multiselect-group')
                    .not('.multiselect-filter-hidden')
                    .not('.disabled');

                var checked = true;
                $options.each(function() {
                    var $input = $('input', this);

                    if (!$input.prop('checked')) {
                        checked = false;
                    }
                });

                if (selectedClass) {
                    if (checked) {
                        $(this).addClass(selectedClass);
                    }
                    else {
                        $(this).removeClass(selectedClass);
                    }
                }

                $('input', this).prop('checked', checked);
            });
        },

        /**
         * Updates the select all checkbox based on the currently displayed and selected checkboxes.
         */
        updateSelectAll: function(notTriggerOnSelectAll) {
            if (this.hasSelectAll()) {
                var allBoxes = $("li:not(.multiselect-item):not(.multiselect-filter-hidden):not(.multiselect-group):not(.disabled) input:enabled", this.$ul);
                var allBoxesLength = allBoxes.length;
                var checkedBoxesLength = allBoxes.filter(":checked").length;
                var selectAllLi  = $("li.multiselect-all", this.$ul);
                var selectAllInput = selectAllLi.find("input");

                if (checkedBoxesLength > 0 && checkedBoxesLength === allBoxesLength) {
                    selectAllInput.prop("checked", true);
                    selectAllLi.addClass(this.options.selectedClass);
                }
                else {
                    selectAllInput.prop("checked", false);
                    selectAllLi.removeClass(this.options.selectedClass);
                }
            }
        },

        /**
         * Update the button text and its title based on the currently selected options.
         */
        updateButtonText: function() {
            var options = this.getSelected();

            // First update the displayed button text.
            if (this.options.enableHTML) {
                $('.multiselect .multiselect-selected-text', this.$container).html(this.options.buttonText(options, this.$select));
            }
            else {
                $('.multiselect .multiselect-selected-text', this.$container).text(this.options.buttonText(options, this.$select));
            }

            // Now update the title attribute of the button.
            $('.multiselect', this.$container).attr('title', this.options.buttonTitle(options, this.$select));
        },

        /**
         * Get all selected options.
         *
         * @returns {jQUery}
         */
        getSelected: function() {
            return $('option', this.$select).filter(":selected");
        },

        /**
         * Gets a select option by its value.
         *
         * @param {String} value
         * @returns {jQuery}
         */
        getOptionByValue: function (value) {

            var options = $('option', this.$select);
            var valueToCompare = value.toString();

            for (var i = 0; i < options.length; i = i + 1) {
                var option = options[i];
                if (option.value === valueToCompare) {
                    return $(option);
                }
            }
        },

        /**
         * Get the input (radio/checkbox) by its value.
         *
         * @param {String} value
         * @returns {jQuery}
         */
        getInputByValue: function (value) {

            var checkboxes = $('li input:not(.multiselect-search)', this.$ul);
            var valueToCompare = value.toString();

            for (var i = 0; i < checkboxes.length; i = i + 1) {
                var checkbox = checkboxes[i];
                if (checkbox.value === valueToCompare) {
                    return $(checkbox);
                }
            }
        },

        /**
         * Used for knockout integration.
         */
        updateOriginalOptions: function() {
            this.originalOptions = this.$select.clone()[0].options;
        },

        asyncFunction: function(callback, timeout, self) {
            var args = Array.prototype.slice.call(arguments, 3);
            return setTimeout(function() {
                callback.apply(self || window, args);
            }, timeout);
        },

        setAllSelectedText: function(allSelectedText) {
            this.options.allSelectedText = allSelectedText;
            this.updateButtonText();
        }
    };

    $.fn.multiselect = function(option, parameter, extraOptions) {
        return this.each(function() {
            var data = $(this).data('multiselect');
            var options = typeof option === 'object' && option;

            // Initialize the multiselect.
            if (!data) {
                data = new Multiselect(this, options);
                $(this).data('multiselect', data);
            }

            // Call multiselect method.
            if (typeof option === 'string') {
                data[option](parameter, extraOptions);

                if (option === 'destroy') {
                    $(this).data('multiselect', false);
                }
            }
        });
    };

    $.fn.multiselect.Constructor = Multiselect;

    $(function() {
        $("select[data-role=multiselect]").multiselect();
    });

});

(function (root, factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as an anonymous module.
        define(['exports', 'echarts'], factory);
    } else if (typeof exports === 'object' && typeof exports.nodeName !== 'string') {
        // CommonJS
        factory(exports, require('echarts'));
    } else {
        // Browser globals
        factory({}, root.echarts);
    }
}(this, function (exports, echarts) {
    var log = function (msg) {
        if (typeof console !== 'undefined') {
            console && console.error && console.error(msg);
        }
    };
    if (!echarts) {
        log('ECharts is not Loaded');
        return;
    }
    echarts.registerTheme('eoulu_chart_1', {
        "color": [
            "#3fb1e3",
            "#6be6c1",
            "#626c91",
            "#a0a7e6",
            "#c4ebad",
            "#96dee8"
        ],
        "backgroundColor": "#fcfcfc",
        "textStyle": {},
        "title": {
            "textStyle": {
                "color": "#666666"
            },
            "subtextStyle": {
                "color": "#999999"
            }
        },
        "line": {
            "itemStyle": {
                "normal": {
                    "borderWidth": "2"
                }
            },
            "lineStyle": {
                "normal": {
                    "width": "3"
                }
            },
            "symbolSize": "8",
            "symbol": "emptyCircle",
            "smooth": false
        },
        "radar": {
            "itemStyle": {
                "normal": {
                    "borderWidth": "2"
                }
            },
            "lineStyle": {
                "normal": {
                    "width": "3"
                }
            },
            "symbolSize": "8",
            "symbol": "emptyCircle",
            "smooth": false
        },
        "bar": {
            "itemStyle": {
                "normal": {
                    "barBorderWidth": 0,
                    "barBorderColor": "#ccc"
                },
                "emphasis": {
                    "barBorderWidth": 0,
                    "barBorderColor": "#ccc"
                }
            }
        },
        "pie": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                },
                "emphasis": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                }
            }
        },
        "scatter": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                },
                "emphasis": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                }
            }
        },
        "boxplot": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                },
                "emphasis": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                }
            }
        },
        "parallel": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                },
                "emphasis": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                }
            }
        },
        "sankey": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                },
                "emphasis": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                }
            }
        },
        "funnel": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                },
                "emphasis": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                }
            }
        },
        "gauge": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                },
                "emphasis": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                }
            }
        },
        "candlestick": {
            "itemStyle": {
                "normal": {
                    "color": "#e6a0d2",
                    "color0": "transparent",
                    "borderColor": "#e6a0d2",
                    "borderColor0": "#3fb1e3",
                    "borderWidth": "2"
                }
            }
        },
        "graph": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                }
            },
            "lineStyle": {
                "normal": {
                    "width": "1",
                    "color": "#cccccc"
                }
            },
            "symbolSize": "8",
            "symbol": "emptyCircle",
            "smooth": false,
            "color": [
                "#3fb1e3",
                "#6be6c1",
                "#626c91",
                "#a0a7e6",
                "#c4ebad",
                "#96dee8"
            ],
            "label": {
                "normal": {
                    "textStyle": {
                        "color": "#ffffff"
                    }
                }
            }
        },
        "map": {
            "itemStyle": {
                "normal": {
                    "areaColor": "#eeeeee",
                    "borderColor": "#aaaaaa",
                    "borderWidth": 0.5
                },
                "emphasis": {
                    "areaColor": "rgba(63,177,227,0.25)",
                    "borderColor": "#3fb1e3",
                    "borderWidth": 1
                }
            },
            "label": {
                "normal": {
                    "textStyle": {
                        "color": "#ffffff"
                    }
                },
                "emphasis": {
                    "textStyle": {
                        "color": "rgb(63,177,227)"
                    }
                }
            }
        },
        "geo": {
            "itemStyle": {
                "normal": {
                    "areaColor": "#eeeeee",
                    "borderColor": "#aaaaaa",
                    "borderWidth": 0.5
                },
                "emphasis": {
                    "areaColor": "rgba(63,177,227,0.25)",
                    "borderColor": "#3fb1e3",
                    "borderWidth": 1
                }
            },
            "label": {
                "normal": {
                    "textStyle": {
                        "color": "#ffffff"
                    }
                },
                "emphasis": {
                    "textStyle": {
                        "color": "rgb(63,177,227)"
                    }
                }
            }
        },
        "categoryAxis": {
            "axisLine": {
                "show": true,
                "lineStyle": {
                    "color": "#cccccc"
                }
            },
            "axisTick": {
                "show": false,
                "lineStyle": {
                    "color": "#333"
                }
            },
            "axisLabel": {
                "show": true,
                "textStyle": {
                    "color": "#999999"
                }
            },
            "splitLine": {
                "show": true,
                "lineStyle": {
                    "color": [
                        "#eeeeee"
                    ]
                }
            },
            "splitArea": {
                "show": false,
                "areaStyle": {
                    "color": [
                        "rgba(250,250,250,0.05)",
                        "rgba(200,200,200,0.02)"
                    ]
                }
            }
        },
        "valueAxis": {
            "axisLine": {
                "show": true,
                "lineStyle": {
                    "color": "#cccccc"
                }
            },
            "axisTick": {
                "show": false,
                "lineStyle": {
                    "color": "#333"
                }
            },
            "axisLabel": {
                "show": true,
                "textStyle": {
                    "color": "#999999"
                }
            },
            "splitLine": {
                "show": true,
                "lineStyle": {
                    "color": [
                        "#eeeeee"
                    ]
                }
            },
            "splitArea": {
                "show": false,
                "areaStyle": {
                    "color": [
                        "rgba(250,250,250,0.05)",
                        "rgba(200,200,200,0.02)"
                    ]
                }
            }
        },
        "logAxis": {
            "axisLine": {
                "show": true,
                "lineStyle": {
                    "color": "#cccccc"
                }
            },
            "axisTick": {
                "show": false,
                "lineStyle": {
                    "color": "#333"
                }
            },
            "axisLabel": {
                "show": true,
                "textStyle": {
                    "color": "#999999"
                }
            },
            "splitLine": {
                "show": true,
                "lineStyle": {
                    "color": [
                        "#eeeeee"
                    ]
                }
            },
            "splitArea": {
                "show": false,
                "areaStyle": {
                    "color": [
                        "rgba(250,250,250,0.05)",
                        "rgba(200,200,200,0.02)"
                    ]
                }
            }
        },
        "timeAxis": {
            "axisLine": {
                "show": true,
                "lineStyle": {
                    "color": "#cccccc"
                }
            },
            "axisTick": {
                "show": false,
                "lineStyle": {
                    "color": "#333"
                }
            },
            "axisLabel": {
                "show": true,
                "textStyle": {
                    "color": "#999999"
                }
            },
            "splitLine": {
                "show": true,
                "lineStyle": {
                    "color": [
                        "#eeeeee"
                    ]
                }
            },
            "splitArea": {
                "show": false,
                "areaStyle": {
                    "color": [
                        "rgba(250,250,250,0.05)",
                        "rgba(200,200,200,0.02)"
                    ]
                }
            }
        },
        "toolbox": {
            "iconStyle": {
                "normal": {
                    "borderColor": "#999999"
                },
                "emphasis": {
                    "borderColor": "#666666"
                }
            }
        },
        "legend": {
            "textStyle": {
                "color": "#999999"
            }
        },
        "tooltip": {
            "axisPointer": {
                "lineStyle": {
                    "color": "#cccccc",
                    "width": 1
                },
                "crossStyle": {
                    "color": "#cccccc",
                    "width": 1
                }
            }
        },
        "timeline": {
            "lineStyle": {
                "color": "#626c91",
                "width": 1
            },
            "itemStyle": {
                "normal": {
                    "color": "#626c91",
                    "borderWidth": 1
                },
                "emphasis": {
                    "color": "#626c91"
                }
            },
            "controlStyle": {
                "normal": {
                    "color": "#626c91",
                    "borderColor": "#626c91",
                    "borderWidth": 0.5
                },
                "emphasis": {
                    "color": "#626c91",
                    "borderColor": "#626c91",
                    "borderWidth": 0.5
                }
            },
            "checkpointStyle": {
                "color": "#3fb1e3",
                "borderColor": "rgba(63,177,227,0.15)"
            },
            "label": {
                "normal": {
                    "textStyle": {
                        "color": "#626c91"
                    }
                },
                "emphasis": {
                    "textStyle": {
                        "color": "#626c91"
                    }
                }
            }
        },
        "visualMap": {
            "color": [
                "#2a99c9",
                "#afe8ff"
            ]
        },
        "dataZoom": {
            "backgroundColor": "rgba(255,255,255,0)",
            "dataBackgroundColor": "rgba(222,222,222,1)",
            "fillerColor": "rgba(114,230,212,0.25)",
            "handleColor": "#cccccc",
            "handleSize": "100%",
            "textStyle": {
                "color": "#999999"
            }
        },
        "markPoint": {
            "label": {
                "normal": {
                    "textStyle": {
                        "color": "#ffffff"
                    }
                },
                "emphasis": {
                    "textStyle": {
                        "color": "#ffffff"
                    }
                }
            }
        }
    });
}));

/*****函数定义与变量定义*****/
var multiSelectOption = {
		disableIfEmpty: true,
		disabledText: '未选择',
		delimiterText: '; ',
		enableFiltering: true,
        filterPlaceholder: '搜索',
		includeResetOption: true,
		includeResetDivider: true,
		includeSelectAllOption: true,
        enableClickableOptGroups: true,
        enableCollapsibleOptGroups: true,
        collapseOptGroupsByDefault: true,
        // selectAllJustVisible: false,
		// selectAllText: '选择所有人员!',
		resetText: "重置",
		maxHeight: 250,
		buttonWidth: '360px',
        numberDisplayed: 4,
		onChange: function(option, checked, select) {
        },
        onDropdownHide: function(event) {
        },
        optionClass: function(element) {
            var value = $(element).index();
            if (value%2 == 0) {
                return 'even';
            }
            else {
                return 'odd';
            }
        },
	};

// echarts配置
// var departmentIcons = {
//     '人事部': 'image/index/department0.png',
//     '商务部': 'image/index/department1.png',
//     '市场部': 'image/index/department2.png'
// };

var seriesLabel = {
    normal: {
        show: true,
        textBorderColor: '#333',
        textBorderWidth: 2
    }
};

var examChartOption = {
    title: {
        text: '',
        textStyle: {
            color:'#888',
            fontSize: 15
        },
        subtext: ''
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    legend: {
        data: ['已完成', '未完成']
    },
    grid: {
        left: 100
    },
    toolbox: {
        show: true,
        feature: {
            // mark: {show: true},
            dataView: {show: true, readOnly: false},
            magicType: {show: true, type: ['line', 'bar']},
            // magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
            restore: {show: true},
            saveAsImage: {show: true},
            // brush: {type: ['rect', 'polygon', 'lineX', 'lineY', 'keep', 'clear']}
        }
    },
    xAxis: {
        type: 'value',
        name: '人数',
        minInterval : 1,
        axisLabel: {
            textStyle: {
                color: 'rgba(0,0,0,0.9)',
                // fontFamily: '微软雅黑,宋体,Arial, Verdana, sans-serif',
                fontSize: 16 // 让字体变大
            },
            formatter: '{value}'
        }
    },
    yAxis: {
        type: 'category',
        inverse: true,
        data: [],
        axisLabel: {
            textStyle: {
                color: 'rgba(0,0,0,0.9)',
                // fontFamily: '微软雅黑,宋体,Arial, Verdana, sans-serif',
                fontSize: 13 // 让字体变大
            },
            formatter: function (value) {
                // return '{' + value + '| }\n{value|' + value + '}';
                var ret = "";//拼接加\n返回的类目项  
                var maxLength = 3;//每项显示文字个数  
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
            },
            margin: 5,
            // rich: {
            //     value: {
            //         lineHeight: 30,
            //         align: 'center'
            //     },
            //     "人事部": {
            //         height: 40,
            //         align: 'center',
            //         backgroundColor: {
            //             image: departmentIcons.Sunny
            //         }
            //     },
            //     "商务部": {
            //         height: 40,
            //         align: 'center',
            //         backgroundColor: {
            //             image: departmentIcons.Cloudy
            //         }
            //     },
            //     "市场部": {
            //         height: 40,
            //         align: 'center',
            //         backgroundColor: {
            //             image: departmentIcons.Showers
            //         }
            //     }
            // }
        }
    },
    series: [
        {
            name: '已完成',
            type: 'bar',
            data: [],
            label: seriesLabel,
            itemStyle: {
                normal: {
                    // color:function(d){return "#"+Math.floor(Math.random()*(256*256*256-1)).toString(16);}
                    color: function(params) {
                        // console.log(params);
                        // build a color map as your need.
                        // var colorList = [
                        //   '#DE3656','#D6395B','#C93F64','#C0436C','#B64873','#A35283','#9D5588','#885F98','#7468A6','#6C6BA6','#5F72B6','#5A74B9','#5077BE','#4B7BC5','#467DC9','#4081CE','#3A83D3','#3183DA','#2A8BDF','#248EE3','#1E91E8','#1993EC','#1396F0','#0F98F4','#0B9AF7','#069CFA','#019FFF'
                        // ];
                        if(params.name == "汇总"){
                            return "#C93F64";
                        }else{
                            return "#3fb1e3";
                        }
                        // return colorList[(params.dataIndex)%(colorList.length)];
                    },
                    barBorderRadius: 3,  //柱状角成椭圆形
                    // label:{
                    //     show:true,
                    //     position:'top',
                    //     textStyle: {
                    //         color:'rgba(0,0,0,0.8)',
                    //         fontSize:13
                    //     },
                    //     formatter:function(params){
                    //         if(params.value==0){
                    //             return '';
                    //         }else{
                    //             return params.value;
                    //             }
                    //     }
                    // }
                },
            },
            // markPoint: {
            //     symbolSize: 1,
            //     symbolOffset: [0, '50%'],
            //     label: {
            //        normal: {
            //             formatter: '{a|{a}\n}{b|{b} }{c|{c}}',
            //             backgroundColor: 'rgb(242,242,242)',
            //             borderColor: '#aaa',
            //             borderWidth: 1,
            //             borderRadius: 4,
            //             padding: [4, 10],
            //             lineHeight: 26,
            //             // shadowBlur: 5,
            //             // shadowColor: '#000',
            //             // shadowOffsetX: 0,
            //             // shadowOffsetY: 1,
            //             position: 'right',
            //             distance: 20,
            //             rich: {
            //                 a: {
            //                     align: 'center',
            //                     color: '#fff',
            //                     fontSize: 18,
            //                     textShadowBlur: 2,
            //                     textShadowColor: '#000',
            //                     textShadowOffsetX: 0,
            //                     textShadowOffsetY: 1,
            //                     textBorderColor: '#333',
            //                     textBorderWidth: 2
            //                 },
            //                 b: {
            //                      color: '#333'
            //                 },
            //                 c: {
            //                     color: '#ff8811',
            //                     textBorderColor: '#000',
            //                     textBorderWidth: 1,
            //                     fontSize: 22
            //                 }
            //             }
            //        }
            //     }
            //     // data: [
            //     //     {type: 'max', name: 'max days: '},
            //     //     {type: 'min', name: 'min days: '}
            //     // ]
            // }
        },
        {
            name: '未完成',
            type: 'bar',
            label: seriesLabel,
            data: [],
            itemStyle: {
                normal: {
                    color: function(params) {
                        if(params.name == "汇总"){
                            return "#4B7BC5";
                        }else{
                            return "#6be6c1";
                        }
                    },
                    barBorderRadius: 3,  //柱状角成椭圆形
                },
            },
        }
    ]
};


var addSubmitObj = new Object();
addSubmitObj.Item = null;
addSubmitObj.Type = null;
addSubmitObj.ID = null;
addSubmitObj.SerialNumber = null;
addSubmitObj.Subject = null;
addSubmitObj.Number = null;
// addSubmitObj.Title = null;
addSubmitObj.Time = null;
// addSubmitObj.Classify = null;
var updateSubmitObj = new Object();
updateSubmitObj.Item = null;
updateSubmitObj.Type = null;
updateSubmitObj.ID = null;
updateSubmitObj.SerialNumber = null;
updateSubmitObj.Subject = null;
updateSubmitObj.Number = null;
// updateSubmitObj.Title = null;
updateSubmitObj.Time = null;
// updateSubmitObj.Classify = null;

var initEchartsHook = 0;
var ichart;
var subjectArr = [];
// 分页切换标志
var presentSwitchClassify;
// 更改状态标志
var changeState = 0;
// 定义部门与考试者的Obj
var department2ExamierObj = {};
var curNoticeIndex;
// 遮罩层宽高
function setCoverWH(){
	var ww= $("#NonStandard_sticker").width();
	var hh= $("#NonStandard_sticker").height();
	$(".bg_cover, .cover_bg2").css({"width":ww+"px","height":hh+"px"});
}

// 分数可录入属性init
function scoreContenteditable(flag){
    $(".exam_detail_more_detable .hastd_details_Score").prop("contenteditable",flag);
}

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

// 根据科目返回试题
function getQuesBySubject(Subject, fn, fn2){
    $.ajax({
        type: "GET",
        url: "ExaminationDetails",
        data: {
            Type: "number",
            // Type: "select",
            Subject: Subject
        },
        dataType: "json"
    }).then(function(data, textStatus, jqXHR){
        fn && fn(data);
    },function(jqXHR, textStatus, errorThrown){
        $.MsgBox_Unload.Alert("提示","该科目无相应试题数据！");
    }).always(function(){
        fn2 && fn2();
    });
}

function setChartsContH(DepartmentArr){
    var iLen = DepartmentArr.length;
    var iH = iLen * 65 + 60;
    $("#exam_statis_chart").css("height",iH+"px");
    var iiH = iH + 50;
    $(".exam_statistics_chart").css("height",iiH+"px");
}

// echarts init
function initEcharts(DepartmentArr){
    setChartsContH(DepartmentArr);
	$(window).trigger("resize");
	ichart = echarts.init(document.getElementById('exam_statis_chart'), 'eoulu_chart_1');
    initEchartsHook = 1;
}

function renderEcharts(DepartmentArr,FinishedArr,NotFinishedArr,text,subText){
    setChartsContH(DepartmentArr);
    ichart.resize();
    examChartOption.yAxis.data = DepartmentArr;
    examChartOption.series[0].data = FinishedArr;
    examChartOption.series[1].data = NotFinishedArr;
    examChartOption.title.text = text;
    examChartOption.title.subtext = subText;
    if (examChartOption && typeof examChartOption === "object") {
        ichart.setOption(examChartOption, true);
    }
    ichart.off('click');
    ichart.on('click', function (params) {
        // 控制台打印数据的名称
        console.log(params.name);
        console.log(text);
        console.log(subText);
        if(params.name == "汇总"){
            return false;
        }
        $("#exam_detail_department").val(params.name);
        $(".g_container_info_l li:nth-child(2)").trigger("click");
    });
    /*窗口自适应，关键代码*/  
    setTimeout(function(){
        window.onresize = function(){  
            ichart.resize();
        };
    },150);
    $(".exam_statistics_chart_tit").text(text+" "+subText+" 数据统计图表");
}

// 图表请求数据
function chartGetData(searchNumber,text,subText){
    $.ajax({
        type: "GET",
        url: "AssessmentStatistics",
        data: {
            LoadType: "data",
            Number: searchNumber
        },
        dataType: "json"
    }).then(function(res){
        if(res.length==1){
            $.MsgBox_Unload.Alert("提示","无相应数据！");
        }else{
            var DepartmentArr = ["汇总"];
            var FinishedArr = [];
            var NotFinishedArr = [];
            res.map(function(currentValue,index,arr){
                if(index>0){
                    DepartmentArr.push(currentValue.Department);
                    FinishedArr.push(currentValue.Finished);
                    NotFinishedArr.push(currentValue.NotFinished);
                }
            });
            var sum1 = 0;
            var sum2 = 0;
            FinishedArr.map(function(currentValue,index,arr){
                sum1+=Number(currentValue);
            });
            NotFinishedArr.map(function(currentValue,index,arr){
                sum2+=Number(currentValue);
            });
            FinishedArr.unshift(sum1);
            NotFinishedArr.unshift(sum2);
            if(initEchartsHook == 0){
                initEcharts(DepartmentArr);
            }
            renderEcharts(DepartmentArr,FinishedArr,NotFinishedArr,text,subText);
        }
    },function(){
        $.MsgBox_Unload.Alert("统计图提示","网络繁忙！请求数据失败");
    }).always(function(){
    });
}

// 数据统计导航栏初始化
function examStatisInit(){
    $.ajax({
        type: "GET",
        url: "ExaminationDetails",
        data: {
            Type: "subject"
        },
        dataType: "json",
        success: function(data){
            var tempArr = [];
            data.map(function(currentValue,index,arr){
                if(index > 0){
                    tempArr.push(currentValue.Subject);
                }
            });
            subjectArr = globalArrStrUnique(tempArr);
            if(subjectArr.length>0){
                var str1 = '';
                subjectArr.map(function(currentValue,index,arr){
                    str1+='<option value="'+currentValue+'">'+currentValue.replace(/^《/,"").replace(/》$/,"")+'</option>';
                });
                $("select[name='subject_select']").empty().append(str1);
            }else{
                $.MsgBox_Unload.Alert("提示","科目列表长度为0！");
            }
        },
        error: function(){
            $.MsgBox_Unload.Alert("提示","服务器繁忙！获取科目列表有误");
        },
        complete: function(XMLHttpRequest, textStatus){
            if(textStatus=='success'){
                var subject = $("#exam_detail_subject").val();
                getQuesBySubject(subject,function(data){
                    var str = '';
                    data.map(function(currentValue, index, arr){
                        if(index > 0){
                            str+='<option value="'+currentValue.Number+'">'+currentValue.Number+'</option>';
                        }
                    });
                    $("#exam_detail_paper").empty().append(str);
                    $("#exam_detail_freshen").trigger("click");
                },function(){
                    // always回调
                });
            }else if(textStatus=='error'){
            }else if(textStatus=='timeout'){
                var xmlhttp = window.XMLHttpRequest ? new window.XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHttp");  
                xmlhttp.abort();
            }
        }
    });
}

// 详细信息页面 科目 试题 部门 搜素获取数据
function getDetailBySearch(icurPage){
    var iNumber = $("#exam_detail_paper").val();
    var Department = $("#exam_detail_department").val();
    $.ajax({
        type: "GET",
        url: "ExaminationDetails",
        data: {
            Type: "details",
            CurrentPage: icurPage,
            Number: iNumber,
            Department: Department
        },
        dataType: "json",
        success: function(res){
            var data = res.data;
            var pageCounts = res.pageCount;
            renderDetailBySearch(data,icurPage,pageCounts,Department);
            pageStyle(icurPage,pageCounts);
            tdDateHandle("td.hastd_details_Time","","#000");
        },
        error: function(){
            $.MsgBox_Unload.Alert("提示","服务器繁忙！");
        },
        complete: function(XMLHttpRequest, textStatus){
            if(textStatus=='success'){
                setCoverWH();
            }
        }
    });
}

// 详细信息页面 科目 试题 部门 搜素后渲染数据
function renderDetailBySearch(data,icurPage,pageCounts,Department){
    var str1;
    if(data.length == 1){
        var visibleLen = $(".exam_detail_more_detable thead tr:nth-child(1) th:visible").length;
        str1 = '<tr><td colspan="'+visibleLen+'" style="text-align:center;">无数据......</td></tr>';
    }else if(data.length > 1){
        data.map(function(currentValue,index,arr){
            if(index>0){
                str1+='<tr>'+
                        '<td data-id="'+currentValue.ScoreID+'" data-classify="'+currentValue.Classify+'" data-serialnumber="'+currentValue.SerialNumber+'">'+parseInt((parseInt(icurPage)-1)*10+index)+'</td>'+
                        '<td class="hastd_details_department">'+Department+'</td>'+
                        '<td class="hastd_details_Examiner">'+currentValue.Examiner+'</td>';
                // var inState = currentValue.State.toLowerCase();
                var inScore = currentValue.Score;
                if(inScore=="P"){
                    str1+='<td class="hastd_details_Score" title="P"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span></td>';
                }else if(inScore==="" || inScore=="--"){
                    str1+='<td class="hastd_details_Score">待批阅</td>';
                }else{
                    str1+='<td class="hastd_details_Score" title="'+inScore+'">'+inScore+'</td>';
                }
                str1+='<td class="hastd_details_Time">'+currentValue.Time+'</td>'+
                    '</tr>';
            }
        });
    }
    $(".exam_detail_more_detable tbody").empty().append(str1);
    $(".m_page #currentPage").text(icurPage);
    $(".m_page #allPage").text(pageCounts);
}

// 获取考核通知数据
function getAssessNotice(icurPage){
    $.ajax({
        type: "GET",
        url: "AssessmentNotice",
        data: {
            CurrentPage: icurPage
        },
        dataType: "json",
        success: function(res){
            var data = res.data;
            var pageCounts = res.pageCount;
            renderAssessNotice(data,icurPage,pageCounts);
            pageStyle(icurPage,pageCounts);
            tdDateHandle("td.notice_Time","","#000");
        },
        error: function(){
            $.MsgBox_Unload.Alert("提示","服务器繁忙！");
        },
        complete: function(XMLHttpRequest, textStatus){
            if(textStatus=='success'){
                setCoverWH();
            }
        }
    });
}

// 渲染考核通知数据
function renderAssessNotice(data,icurPage,pageCounts){
    var str = '';
    for(var i = 1;i<data.length;i++){
        var examineridarr = data[i].ExaminersID;
        var examineridstr;
        if(examineridarr.length==0){
            examineridstr = "";
        }else{
            examineridstr = examineridarr.join(",");
        }
        str+='<tr>'+
            '<td class="update_td_notice" data-id="'+data[i].ID+'" data-classify="'+data[i].Classify+'" data-serialnumber="'+data[i].SerialNumber+'">'+parseInt((parseInt(icurPage)-1)*10+i)+'</td>'+
            '<td class="notice_Subject" title="'+data[i].Subject+'">'+data[i].Subject+'</td>'+
            '<td class="notice_Number" title="'+data[i].Number+'">'+data[i].Number+'</td>'+
            '<td class="notice_Time">'+data[i].Time+'</td>'+
            '<td class="notice_departments" title="'+data[i].Department+'">'+data[i].Department+'</td>'+
            '<td class="notice_Examiners" title="'+data[i].Examiners+'" data-examineridstr="'+examineridstr+'">'+data[i].Examiners+'</td>'+
            '<td class="notice_PublishDate">'+data[i].PublishDate+'</td>'+
            '<td class="notice_Status"><input type="button" class="examiner_publish_save" value="保存"><input type="button" value="'+data[i].Status+'"></td>'+
            '</tr>';
    }
    $(".m_table4 tbody").empty().append(str);
    $(".m_page #currentPage").text(icurPage);
    $(".m_page #allPage").text(pageCounts);
}

// 跳页、翻页获取数据
function getGoPage(currentPage){
    if(presentSwitchClassify == "detail"){
        $(".exam_btn_div .InScore").val("录入分数");
        getDetailBySearch(currentPage);
    }else if(presentSwitchClassify == "notice"){
        getAssessNotice(currentPage);
    }
}

function departmentInit(){
    var departStr = '';
    var departmentOptStr = '';
    globalAllDepartArr.map(function(currentValue,index,arr){
        departStr+='<option value="'+currentValue+'">'+currentValue+'</option>';
        departmentOptStr+='<option value="'+currentValue+'">'+currentValue+'</option>';
    });
    $("#exam_detail_department").empty().append(departStr);
    $("#department_select").empty().append(departmentOptStr);
}

/* 以后参考
// 渲染科目、编号、分数
function renderSubjectScore(icurPage,Classify,pageCounts,data){
	var item = getClassItem(Classify);
	var personLen = $(item).find(".scroll_table_div thead th").length;
	var str = '';
	var str2 = '';
	var SerialNumberArr = [];
	var NumberArr = [];
	var hasRowspan = [];
	for(var i = 1;i<data.length;i++){
		SerialNumberArr.push(data[i].SerialNumber);
		NumberArr.push(data[i].Number);
	}
	var SerialNumberCount = globalGetArrCounts(SerialNumberArr);
	var NumberCount = globalGetArrCounts(NumberArr);
	console.log(SerialNumberCount);
	console.log(NumberCount);
	for(var ii = 1;ii<data.length;ii++){
		var iSerialNumber = data[ii].SerialNumber;
		var curSerNoCount = SerialNumberCount[data[ii].SerialNumber];
		if($.inArray(iSerialNumber, hasRowspan)==-1){
			str+='<tr>'+
					'<td rowspan="'+curSerNoCount+'">'+iSerialNumber+'</td>'+
					'<td rowspan="'+curSerNoCount+'">'+data[ii].Subject+'</td>'+
					'<td class="over_width_1 hastd_Number" data-id="'+data[ii].ID+'" data-serialnumber="'+iSerialNumber+'" data-subject="'+data[ii].Subject+'">'+data[ii].Number+'</td>'+
					'<td class="over_width_2 hastd_Title">'+data[ii].Title+'</td>'+
					'<td class="over_width_3 hastd_Time">'+data[ii].Time+'</td>'+
				'</tr>';
			hasRowspan.push(iSerialNumber);
		}else if($.inArray(iSerialNumber, hasRowspan)>-1){
			str+='<tr>'+
					'<td class="over_width_1 hastd_Number" data-id="'+data[ii].ID+'" data-serialnumber="'+iSerialNumber+'" data-subject="'+data[ii].Subject+'">'+data[ii].Number+'</td>'+
					'<td class="over_width_2 hastd_Title">'+data[ii].Title+'</td>'+
					'<td class="over_width_3 hastd_Time">'+data[ii].Time+'</td>'+
				'</tr>';
		}
		
		str2+='<tr>';
		for(var q = 0;q<personLen;q++){
			str2+='<td><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></td>';
		}
		str2+="</tr>";
	}
	$(item).find(".fixed_table_div tbody").empty().append(str);
	$(item).find(".scroll_table_div tbody").empty().append(str2);
	for(var iii = 1;iii<data.length;iii++){
		var ScoreInfoObj = JSON.parse(data[iii].ScoreInfo);
		if(ScoreInfoObj.length>1){
			ScoreInfoObj.map(function(currentValue, index, arr){
				if(index>0){
					var examinerId = currentValue.ExaminerID;
					var curTr = $(item).find(".scroll_table_div tbody tr").eq(iii-1);
					var curExaminerIndex = $(item).find(".scroll_table_div th[data-id='"+examinerId+"']").index();
					var State = currentValue.State; 
					var Score = currentValue.Score; 
					if(State=="待批阅"){
						curTr.find("td").eq(curExaminerIndex).empty().text(State);
					}else{
						curTr.find("td").eq(curExaminerIndex).empty().text(Score);
					}
					curTr.find("td").eq(curExaminerIndex).data("id",currentValue.ID);
				}
			});
		}
	}
	$(".m_page #currentPage").text(icurPage);
	$(".m_page #allPage").text(pageCounts);
}
*/

// select获取考核人员
// function getSelAssessPerson(item,flag,ExaminerIDArr){
// 	$.ajax({
// 		type: "GET",
// 		url: "ExaminationDetails",
// 		data:{
// 			LoadType: "examiner"
// 		},
// 		dataType: "json",
// 		success: function(data){
// 			console.log(typeof data);
// 			// [ ...PersonArr ] = data;
// 			var str = '';
// 			for(var i = 1; i<data.length; i++){
// 				str+='<option data-examiner="'+data[i].Examiner+'" value="'+data[i].ID+'">'+data[i].Examiner+'</option>';
// 			}
// 			$(item).empty().append(str);
// 		},
// 		error: function(){
// 			$.MsgBox_Unload.Alert("提示","服务器繁忙！");
// 		},
// 		complete: function(XMLHttpRequest, textStatus){
// 			if(textStatus=='success'){
// 	    		$(item).multiselect('destroy').multiselect(multiSelectOption);
// 	    		if(flag==true){
// 	    			$(item).multiselect('select', ExaminerIDArr);
// 	    		}
// 			}
// 		}
// 	});
// }

/*****页面加载完成*****/
$(function(){
    examStatisInit();
	setCoverWH();
    departmentInit();
    multiSelectOption.selectAllText = '选择所有部门！';
    // $("#department_select").multiselect('destroy').multiselect(multiSelectOption);
    $("#department_select").multiselect(multiSelectOption);

    multiSelectOption.selectAllText = '选择所有人员！';
    $('#examiner_select').multiselect(multiSelectOption);
    var selectOptionsData = [];
    var iIndex = 0;
    var myFunc = function(data){
        if(data.length > 1){
            department2ExamierObj[globalAllDepartArr[iIndex]] = [];
            var item = {};
            item.label = globalAllDepartArr[iIndex];
            item.value = globalAllDepartArr[iIndex];
            item.children = [];
            data.map(function(currentValue,index,arr){
                if(index > 0){
                    var iitem = {};
                    iitem["label"] = currentValue.StaffName;
                    // iitem["text"] = currentValue.StaffName+":";
                    iitem["value"] = currentValue.ID;
                    iitem["select"] = false;
                    iitem["disabled"] = true;
                    iitem["attributes"] = {
                        "examiner": currentValue.StaffName
                    };
                    item.children.push(iitem);
                    var iiitem = {};
                    iiitem.ID = currentValue.ID;
                    iiitem.StaffName = currentValue.StaffName;
                    department2ExamierObj[globalAllDepartArr[iIndex]].push(iiitem);
                }
            });
            selectOptionsData.push(item);
        }
        iIndex++;
        if(iIndex < globalAllDepartArr.length){
            $.when(
                $.ajax({
                    type: 'GET',
                    url: 'GetStaffInfo',
                    data: {
                        Type: "common",
                        Department: globalAllDepartArr[iIndex]
                    },
                    dataType: "json"
                })
                ).done(myFunc);
        }else if(iIndex == globalAllDepartArr.length){
            // console.log(selectOptionsData);
            $('#examiner_select').multiselect('dataprovider', selectOptionsData);
        }
    };
    $.when(
        $.ajax({
            type: 'GET',
            url: 'GetStaffInfo',
            data: {
                Type: "common",
                Department: globalAllDepartArr[iIndex]
            },
            dataType: "json"
        })
        ).then(myFunc, function(){
        $.MsgBox_Unload.Alert("获取员工姓名提示","初始化失败！");
    });

    //
    // var testArr = [];
    // var promise = new Promise(function(resolve){
    //     for(var i = 0; i<10; i++){
    //         var test1 = new Promise(function(resolve){
    //             setTimeout(function(){
    //                 return resolve(i);
    //             },100);
    //         });
    //         Promise.all([test1]).then(function([result1]){
    //             testArr.push(result1);
    //             if(i === 9){
    //                 resolve();
    //             }
    //         });
    //     }
    // });
    // promise.then(function(){
    //     console.log(testArr);
    //     console.log(testArr.length);
    // });
});


/*****
* event listener
*****/
// add
$(".exam_btn_div input[value='添加']").on("click",function(){
	$(".add_NonStandard_body_in [class^='info_']").each(function(){
		$(this).val("");
	});
	$(".bg_cover, .add_NonStandard").slideDown(350);
});
// update
$(document).on("click",".update_td_notice",function(){
	// jQuery.data($(this), "customerId");
	updateSubmitObj.ID = Number($(this).data("id")).toString();
	var iserialnumber = $(this).data("serialnumber");
	var that = $(this);
	$(".update_NonStandard_body_in [class^='info_']").each(function(){
		var subClassName = $(this).attr("class").split(" ")[0].replace("info_","notice_");
		var oldVal;
		switch(subClassName)
		{
		    case "notice_SerialNumber":
		    	oldVal = iserialnumber;
		        break;
		    default:
		    	oldVal = that.siblings("."+subClassName).text();
		}
		var newVal = globalDataHandle(oldVal,"");
		$(this).val(newVal);
	});
	$(".bg_cover, .update_NonStandard").slideDown(350);
});

// 录入分数
$(".exam_btn_div .InScore").on("click",function(){
    if($(".exam_detail_more_detable tbody tr").length == 1 && $(".exam_detail_more_detable tbody>tr>td").length == 1){
        return false;
    }
	if($(this).val()=="录入分数"){
		scoreContenteditable("true");
		$(this).val("提交分数");
	}else if($(this).val()=="提交分数"){
		var that = $(this);
		var iflag;
		var ScoreJson = [];
		var currentPage = Number($("span#currentPage").text());
		$(".exam_detail_more_detable .hastd_details_Score").each(function(){
			// if($(this).html().indexOf("span")>-1){
			// 	return true;
			// }
			if($(this).text().trim()=="待批阅"){
                return true;
            }
			var item = {};
			item.Score = $(this).attr("title");
			item.ScoreID = $(this).siblings("td:nth-child(1)").data("id");
			ScoreJson.push(item);
		});
		ScoreJson = JSON.stringify(ScoreJson);
		$.ajax({
			type: "POST",
			url: "ExaminationDetails",
			data: {
				Item: "score",
				ScoreJson: ScoreJson
			},
			dataType: "text",
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            success: function(data){
                if(data=="保存成功"){
                	$.MsgBox_Unload.Alert("提示","修改分数成功！");
                	iflag = 1;
                }else if(data=="部分分数保存失败"){
                	$.MsgBox_Unload.Alert("提示","部分分数保存失败！");
                	iflag = 0;
                }else{
                    $.MsgBox_Unload.Alert("提示","修改分数失败！");
                    iflag = 0;
                }
            },
            error: function(){
                $.MsgBox_Unload.Alert("提示","服务器繁忙！");
            },
            complete: function(XMLHttpRequest, textStatus){
			    if(textStatus=="success"){
			    	if(iflag == 1){
                        getDetailBySearch(currentPage);
			    		scoreContenteditable("false");
                        that.val("录入分数");
			    	}
			    }
            }
		});
	}
});

// 更改状态 & 提交状态
// $(".exam_btn_div .InState").click(function(){
//     if($(".exam_detail_more_detable tbody tr").length == 1 && $(".exam_detail_more_detable tbody>tr>td").length == 1){
//         return false;
//     }
//     if($(this).val() == "更改状态"){
//         changeState = 1;
//         $(this).val("提交状态");
//     }else if($(this).val() == "提交状态"){
//         var iflag = 0;
//         var ConfirmJson = [];
//         var currentPage = Number($("span#currentPage").text());
//         $(".exam_detail_more_detable .hastd_details_State").each(function(){
//             var item = {};
//             item.State = $(this).data("state");
//             item.ScoreID = $(this).siblings("td:nth-child(1)").data("id");
//             ConfirmJson.push(item);
//         });
//         ConfirmJsonStr = JSON.stringify(ConfirmJson);
//         $.ajax({
//             type: "POST",
//             url: "ExaminationDetails",
//             data: {
//                 Item: "confirm",
//                 ConfirmJson: ConfirmJsonStr
//             },
//             dataType: "text",
//             contentType: "application/x-www-form-urlencoded;charset=utf-8",
//             success: function(data){
//                 if(data=="保存成功"){
//                     $.MsgBox_Unload.Alert("提示","修改状态成功！");
//                     iflag = 1;
//                 }else{
//                     $.MsgBox_Unload.Alert("提示",data);
//                     iflag = 0;
//                 }
//             },
//             error: function(){
//                 $.MsgBox_Unload.Alert("提示","服务器繁忙！");
//             },
//             complete: function(XMLHttpRequest, textStatus){
//                 if(textStatus=="success"){
//                     if(iflag == 1){
//                         getDetailBySearch(currentPage);
//                         buttonInit();
//                     }
//                 }
//             }
//         });
//     }
// });

// 点击改变状态
// $(document).on("click",".hastd_details_State",function(){
//     if(changeState == 0){
//         return false;
//     }
//     if($(this).data("state") == "no"){
//         $(this).data("state", "yes");
//     }else if($(this).data("state") == "yes"){
//         $(this).data("state", "no");
//     }
//     $(this).children("span.glyphicon ").toggleClass("glyphicon-remove glyphicon-ok");
// });

// 关闭
  // 添加
$("#NonStandard_addclose, .add_NonStandard_tit_r").on("click",function(){
	for(var k in addSubmitObj){
		addSubmitObj[k] = null;
	}
	$(".bg_cover, .add_NonStandard").slideUp(350);
});
  // update
$("#NonStandard_updateclose, .update_NonStandard_tit_r").on("click",function(){
	for(var k in updateSubmitObj){
		updateSubmitObj[k] = null;
	}
	$(".bg_cover, .update_NonStandard").slideUp(350);
});

// 翻页功能
$("#jumpNumber").on("input propertychange",function(){
    var newVal = $(this).val().replace(/[^\d]/g,'');
    $(this).val(newVal);
});

	// 翻页
$("#fistPage").click(function(){
    var currentPage =1;
    getGoPage(currentPage);
    // if(hasSearch==0){
    // 	tableRenderAjax(currentPage);
    // }else if(hasSearch==1){
    // 	searchGetParam(currentPage);
    // }
});

$("#lastPage").click(function(){
    var currentPage =Number($("#allPage").text());
    getGoPage(currentPage);
});

$("#upPage").click(function(){
    var currentPage = Number($("#currentPage").text());
    if(currentPage == 1){
        return;
    }else{
        currentPage--;
        getGoPage(currentPage);
    }
});

$("#nextPage").click(function(){
    var currentPage = Number($("#currentPage").text());
    var pageCounts = Number($("#allPage").text());
    if(currentPage == pageCounts){
        return;
    }else{
        currentPage++;
        getGoPage(currentPage);
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
        getGoPage(currentPage);
    }
});

// 添加提交
$("#NonStandard_addsubmit").on("click",function(){
	var SerialNoHook = $(".add_NonStandard .info_SerialNumber").val().trim();
	var NoHook = $(".add_NonStandard .info_Number").val().trim();
    if(SerialNoHook == "" || NoHook == ""){
        $.MsgBox_Unload.Alert("提示","序号与编号不能为空");
        return false;
    }
	if(NoHook.indexOf(SerialNoHook)!=0){
		$.MsgBox_Unload.Alert("提示","序号与编号不匹配");
		return false;
	}
	for(var kk in addSubmitObj){
		if(kk=="Item"){
			addSubmitObj[kk] = "examination";
			continue;
		}
		if(kk=="Type"){
			addSubmitObj[kk] = "add";
			continue;
		}
		if(kk=="ID"){
			addSubmitObj[kk] = "0";
			continue;
		}
		addSubmitObj[kk] = $(".add_NonStandard").find(".info_"+kk).val();
	}
	// 表单验证
	for(var kkk in addSubmitObj){
		addSubmitObj[kkk] = globalDataHandle(addSubmitObj[kkk],"").trim();
		// if(kkk=="Classify"&&addSubmitObj[kkk]==""){
		// 	$.MsgBox_Unload.Alert("提示","考核明细切换出错！");
		// 	return false;
		// }
	}
	console.log("add我执行了吗");
	console.log(addSubmitObj);
    // var pageCounts = Number($("#allPage").text());
	$.ajax({
		type: "POST",
		url: "ExaminationDetails",
		dataType: 'text',
		data: addSubmitObj,
		contentType: "application/x-www-form-urlencoded;charset=utf-8",
		success: function(data){
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
                // getAssessNotice(pageCounts);
				getAssessNotice(1);
		    }
		}
	});
});

// 修改提交
$("#NonStandard_updatesubmit").on("click",function(){
	var SerialNoHook = $(".update_NonStandard .info_SerialNumber").val().trim();
	var NoHook = $(".update_NonStandard .info_Number").val().trim();
    if(SerialNoHook == "" || NoHook == ""){
        $.MsgBox_Unload.Alert("提示","序号与编号不能为空");
        return false;
    }
	if(NoHook.indexOf(SerialNoHook)!=0){
		$.MsgBox_Unload.Alert("提示","序号与编号不匹配");
		return false;
	}
	for(var kk in updateSubmitObj){
		if(kk=="Item"){
			updateSubmitObj[kk] = "examination";
			continue;
		}
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
	}
	var iicurrentPage = Number($("span#currentPage").text());
	console.log("update我执行了吗");
	console.log(updateSubmitObj);
	$.ajax({
		type: "POST",
		url: "ExaminationDetails",
		dataType: 'text',
		data: updateSubmitObj,
		contentType: "application/x-www-form-urlencoded;charset=utf-8",
		success: function(data){
			console.log(typeof data);
			if(data=="true"){
				$.MsgBox_Unload.Alert("提示","修改成功！");
				$("#NonStandard_updateclose").trigger("click");
			}else if(data=="false"){
				$.MsgBox_Unload.Alert("提示","修改失败！");
			}
		},
		error: function(){
			$.MsgBox_Unload.Alert("提示","网络繁忙，刷新页面试下！");
		},
		complete: function(XMLHttpRequest, textStatus){
		    if(textStatus=="success"){
				getAssessNotice(iicurrentPage);
		    }
		}
	});
});

// 发布按钮事件
$(document).on("click",".notice_Status input",function(){
    if($(this).val()=="保存"){
        var email_curPage = Number($(".pageInfo #currentPage").text());
        var SubjectID = $(this).parent().siblings("td:nth-child(1)").data("id");
        var currentTr = $(".m_table4 tbody tr").find("td:nth-child(1)[data-id='"+SubjectID+"']").parent();
        var Subject = currentTr.find(".notice_Subject").text();
        var iNumber = currentTr.find(".notice_Number").text();
        var Time = currentTr.find(".notice_Time").text();
        var ExaminerIDStr = currentTr.find(".notice_Examiners").data("examineridstr").toString();
        var ExaminersStr = currentTr.find(".notice_Examiners").text();
        var Examiners = [];
        var ExaminerID = [];
        if(ExaminersStr==""){
            Examiners = [];
        }else{
            Examiners = ExaminersStr.split("、");
        }
        if(ExaminerIDStr==""){
            ExaminerID = [];
        }else{
            ExaminerID = ExaminerIDStr.split(",");
        }
        if(Examiners.length==0||ExaminerID.length==0){
            $.MsgBox_Unload.Alert("提示","考核人员未选");
            return;
        }else if(Examiners.length!=ExaminerID.length){
            $.MsgBox_Unload.Alert("提示","考核人员选择操作失误");
            return;
        }else{
            var Department = currentTr.find(".notice_departments").text();
            $.ajax({
                type: 'POST',
                url: 'AssessmentNotice',
                dataType: 'text',
                data: {
                    Type: "save",
                    SubjectID: SubjectID,
                    Examiners: Examiners,
                    ExaminerID: ExaminerID,
                    Subject: Subject,
                    Number: iNumber,
                    Time: Time,
                    Department: Department
                },
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                success: function(data){
                    $.MsgBox_Unload.Alert("提示",data);
                },
                error: function(){
                    $.MsgBox_Unload.Alert("提示","服务器繁忙！");
                },
                complete: function(XMLHttpRequest, textStatus){
                    if(textStatus=="success"){
                        getAssessNotice(email_curPage);
                    }
                }
            });
        }
    }else{
        var examinerNameStr = $(this).parent().siblings(".notice_Examiners").text();
        if(examinerNameStr == "" || examinerNameStr == null){
            $.MsgBox_Unload.Alert("提示","考核人员未选");
            return false;
        }else{
            var examinerNameArr = examinerNameStr.split("、");
            var str11 = '';
            examinerNameArr.map(function(currentValue,index,arr){
                str11+='<li title="'+currentValue+'"><input type="checkbox">'+currentValue+'</li>';
            });
            $("#publish_Candidate").empty().append(str11);
        }
        $(".bg_cover, .publish_NonStandard").slideDown(200);
        $(".publish_NonStandard").attr("value",$(this).parent().siblings("td:nth-child(1)").data("id"));
    }
});

// 发布框关闭
$(document).on("click","#NonStandard_publishSend, .publish_NonStandard_tit_r",function(){
    $(".publish_NonStandard, .bg_cover").slideUp(200);
});

// 发布
$(document).on("click","#NonStandard_publishSave",function(){
    var email_curPage = 1;
    var SubjectID = $(".publish_NonStandard").attr("value");
    var currentTr = $(".m_table4 tbody tr").find("td:nth-child(1)[data-id='"+SubjectID+"']").parent();
    var Subject = currentTr.find(".notice_Subject").text();
    var iNumber = currentTr.find(".notice_Number").text();
    var Time = currentTr.find(".notice_Time").text();
    var ExaminerIDStr = currentTr.find(".notice_Examiners").data("examineridstr").toString();
    var ExaminersStr = currentTr.find(".notice_Examiners").text();
    var Examiners = [];
    var ExaminerID = [];
    if(ExaminersStr==""){
    	Examiners = [];
    }else{
    	Examiners = ExaminersStr.split("、");
    }
    if(ExaminerIDStr==""){
    	ExaminerID = [];
    }else{
    	ExaminerID = ExaminerIDStr.split(",");
    }
    var RecipientArr = [];
    var RecipientStr;
    $("#publish_Candidate li").each(function(){
        // console.log($(this).find("input").prop("checked"));
        if($(this).find("input").prop("checked")){
            RecipientArr.push($(this).attr("title"));
        }
    });
    if(RecipientArr.length == 0){
        $.MsgBox_Unload.Alert("提示","未选择收件人");
        return false;
    }else{
        RecipientStr = RecipientArr.join(";");
    }
    if(Examiners.length==0||ExaminerID.length==0){
        $.MsgBox_Unload.Alert("提示","考核人员未选");
        $("#NonStandard_publishSend").trigger("click");
        return;
    }else if(Examiners.length!=ExaminerID.length){
        $.MsgBox_Unload.Alert("提示","考核人员选择操作失误");
        $("#NonStandard_publishSend").trigger("click");
        return;
    }else{
        var Department = currentTr.find(".notice_departments").text();
        $.ajax({
            type: 'POST',
            url: 'AssessmentNotice',
            dataType: 'text',
            data: {
                Type: "send",
            	SubjectID: SubjectID,
            	Examiners: Examiners,
            	ExaminerID: ExaminerID,
            	Subject: Subject,
            	Number: iNumber,
            	Time: Time,
                Department: Department,
                Recipient: RecipientStr
            },
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            success: function(data){
                $.MsgBox_Unload.Alert("提示",data);
            },
            error: function(){
                $.MsgBox_Unload.Alert("提示","服务器繁忙！");
            },
            beforeSend: function(XMLHttpRequest){
                $("#NonStandard_publishSave").css({"cursor":"not-allowed"});
                $("#NonStandard_publishSave").prop("disabled",true);
            },
            complete: function(XMLHttpRequest, textStatus){
            	$("#NonStandard_publishSave").css({"cursor":"pointer"});
            	$("#NonStandard_publishSave").prop("disabled",false);
			    if(textStatus=="success"){
                    getAssessNotice(email_curPage);
	    			$("#NonStandard_publishSend").trigger("click");
			    }
            }
        });
    }
});

// 考核通知选部门
$(document).on("click",".notice_departments",function(){
    curNoticeIndex = $(this).parent("tr").index();
    var iDepartmentStr = $(this).attr("title");
    var iDepartmentArr;
    if(iDepartmentStr==undefined || iDepartmentStr=="" || iDepartmentStr=="--"){
        iDepartmentArr = [];
    }else{
        iDepartmentArr = iDepartmentStr.split("、");
    }
    // false表示所有不选中
    $('#department_select').multiselect('deselectAll', false);
    $('#department_select').multiselect('updateButtonText');
    $("#department_select").multiselect('select', iDepartmentArr);
    $(".cover_bg2, .department_sele").slideDown(300);
});

// 考核通知选部门关闭
$(".department_sele_top_r").click(function(){
    var departmentSelect = [];
    $("#department_select option:selected").each(function () {  
        departmentSelect.push($(this).val());
    });
    var curTr = $(".exam_detail_notice_detable tbody tr").eq(curNoticeIndex);
    var departmentSelectStr = departmentSelect.join("、");
    curTr.find(".notice_departments").text(departmentSelectStr).attr("title",departmentSelectStr);
    $(".cover_bg2, .department_sele").slideUp(300);
});

// 考核通知选人
$(document).on("click",".notice_Examiners",function(){
    var curDepartStr = $(this).siblings(".notice_departments").text();
    if(curDepartStr=="" || curDepartStr=="--"){
        $.MsgBox_Unload.Alert("提示","请先选择部门！");
        return false;
    }
    var curDepartArr = curDepartStr.split("、");
    // 给abled的考核人员disabled false
    var hasAbledDepart = [];
    var hasDisabledDepart = [];
    for(var iii in department2ExamierObj){
        if(curDepartArr.indexOf(iii) > -1){
            department2ExamierObj[iii].map(function(currentValue,index,arr){
                hasAbledDepart.push(currentValue.ID);
            });
        }else{
            hasDisabledDepart.push(iii);
        }
    }
    // 初始化，都不可点击
    $('option', $('#examiner_select')).prop('disabled', true);
    $('option', $('#examiner_select')).each(function(element) {
        $(this).removeAttr('selected').prop('selected', false);
    });
    // 初始化，部门都可点击
    $(".examiner_sel .multiselect-item.multiselect-group input").prop("disabled",false);
    // 可用部门的成员可选
    hasAbledDepart.map(function(currentValue,index,arr){
        $('option[value="'+currentValue+'"]', $('#examiner_select')).prop('disabled', false);
    });
    $('#examiner_select').multiselect('refresh');
    // 全部取消disabled部门的默认选中
    // $(".examiner_sel .multiselect-item.multiselect-group input").removeAttr('checked').prop("checked",false);
    // 保存记录ID
    var subjectid = $(this).siblings("td:nth-child(1)").data("id");
    $(".examiner_sel").attr("value",subjectid);
    var ExaminerIDArr;
    if($(this).data("examineridstr")==undefined || $(this).data("examineridstr")==""){
        ExaminerIDArr = [];
    }else{
        ExaminerIDArr = $(this).data("examineridstr").toString().split(",");
    }
    // 选中回显的考核人员
    $("#examiner_select").multiselect('select', ExaminerIDArr);
    // 把disabled的部门的默认点击取消
    hasDisabledDepart.map(function(currentValue,index,arr){
        // $(".examiner_sel .multiselect-item.multiselect-group input[value='"+currentValue+"']").trigger("click");
        $(".examiner_sel .multiselect-item.multiselect-group input[value='"+currentValue+"']").prop("disabled",true).removeAttr('checked').prop("checked",false).parents("li.multiselect-item.multiselect-group.active").removeClass("active");
    });
    $(".cover_bg2, .examiner_sel").slideDown(300);

    /*
    * 2018/07/25 注释
    var curDepartStr = $(this).siblings(".notice_departments").text();
    if(curDepartStr=="" || curDepartStr=="--"){
        $.MsgBox_Unload.Alert("提示","请先选择部门！");
        return false;
    }
    var curDepartArr = curDepartStr.split("、");
    // 初始化，都不可点击
    $('option', $('#examiner_select')).prop('disabled', true);
    $('option', $('#examiner_select')).each(function(element) {
        $(this).removeAttr('selected').prop('selected', false);
    });
    $('#examiner_select').multiselect('refresh');
    // 全部取消disabled的默认选中
    $(".examiner_sel .multiselect-item.multiselect-group input").trigger("click");
    // 给abled的考核人员disabled false
    var hasAbledDepart = [];
    var hasDisabledDepart = [];
    for(var iii in department2ExamierObj){
        if(curDepartArr.indexOf(iii) > -1){
            department2ExamierObj[iii].map(function(currentValue,index,arr){
                hasAbledDepart.push(currentValue.ID);
            });
        }else{
            hasDisabledDepart.push(iii);
        }
    }
    hasAbledDepart.map(function(currentValue,index,arr){
        $('option[value="'+currentValue+'"]', $('#examiner_select')).prop('disabled', false);
    });
    $('#examiner_select').multiselect('refresh');
    // 保存记录ID
    var subjectid = $(this).siblings("td:nth-child(1)").data("id");
    $(".examiner_sel").attr("value",subjectid);
    var ExaminerIDArr;
    if($(this).data("examineridstr")==undefined || $(this).data("examineridstr")==""){
        ExaminerIDArr = [];
    }else{
        ExaminerIDArr = $(this).data("examineridstr").toString().split(",");
    }
    // 选中回显的考核人员
    $("#examiner_select").multiselect('select', ExaminerIDArr);
    // 把disabled的部门的默认点击取消
    hasDisabledDepart.map(function(currentValue,index,arr){
        $(".examiner_sel .multiselect-item.multiselect-group input[value='"+currentValue+"']").trigger("click");
    });
    $(".cover_bg2, .examiner_sel").slideDown(300);
    */

    /*
    var ii = 0;
    var iiLen = curDepartArr.length;
    var IDARRAY = [];
    var STAFFARRAY =[];

    var fn = function(data){
        data.map(function(currentValue,index,arr){
            if(index>0){
                IDARRAY.push(currentValue.ID);
                STAFFARRAY.push(currentValue.StaffName);
            }
        });
        ii++;
        if(ii < iiLen){
            globalGetStaffAllInfoByDepart(curDepartArr[ii],fn);
        }else if(ii == iiLen){
            // console.log(IDARRAY);
            // console.log(STAFFARRAY);
            var str = '';
            for(var i = 0; i<IDARRAY.length; i++){
                str+='<option data-examiner="'+STAFFARRAY[i]+'" value="'+IDARRAY[i]+'">'+STAFFARRAY[i]+'</option>';
            }
            $('#examiner_select').empty().append(str);
            multiSelectOption.selectAllText = '选择所有人员！';
            $('#examiner_select').multiselect('destroy').multiselect(multiSelectOption);
            $("#examiner_select").multiselect('select', ExaminerIDArr);
            $(".cover_bg2, .examiner_sel").slideDown(300);
        }
    };
    globalGetStaffAllInfoByDepart(curDepartArr[ii],fn);
    */
	// $('#examiner_select').multiselect('deselectAll', false);
    // $('#examiner_select').multiselect('updateButtonText');
});
// 关闭
$(".examiner_sel_top_r").on("click",function(){
	var ExaminerIDArr = [];
	var ExaminersArr = [];
	$("#examiner_select option:selected").each(function () {  
	    ExaminerIDArr.push($(this).val());
	    ExaminersArr.push($(this).data("examiner"));
	});
	var ExaminerIDStr = ExaminerIDArr.join(",");
	var ExaminersStr = ExaminersArr.join("、");
	var curSubjectId = $(".examiner_sel").attr("value");
	var notice_Examiners = $(".m_table4 tbody tr").find("td:nth-child(1)[data-id='"+curSubjectId+"']").parent().find(".notice_Examiners");
	notice_Examiners.data("examineridstr",ExaminerIDStr);
	notice_Examiners.text(ExaminersStr);
	notice_Examiners.attr("title",ExaminersStr);
	console.log("考核通知选人关闭");
	console.log(notice_Examiners.data("examineridstr"));
	console.log(typeof notice_Examiners.data("examineridstr"));
	$(".cover_bg2, .examiner_sel").slideUp(300);
});

// 录入分数事件
$(document).on("focus",".exam_detail_more_detable .hastd_details_Score",function(){
    if($(this).attr("title") == "P"){
        $(this).text("");
        return false;
    }
	var curVal = $(this).text().trim();
	if(curVal=="待批阅"){
		$(this).text("");
		return false;
	}

});
$(document).on("blur",".exam_detail_more_detable .hastd_details_Score",function(){
	var curVal = $(this).text().trim();
	// if(curVal=="待批阅"){
	// 	$(this).text("待批阅");
	// 	return false;
	// }
    if(curVal=="P" || curVal=="p"){
        $(this).html('<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>');
        $(this).attr("title","P");
        return false;
    }
    var reg = new RegExp("^(\\d|[1-9]\\d|[1][0-1]\\d|120)$");
	// var reg = new RegExp("^(\\d|[1-9]\\d|100)$");
	if(!reg.test(curVal)){
		$(this).text("待批阅");
	}else{
        $(this).text(curVal);
        $(this).attr("title",curVal);
	}
});

// 导航切换
$(".g_container_info_l li").click(function(){
    setCoverWH();
	$(this).addClass("current_tab").siblings("li").removeClass("current_tab");
	// var searchClassify = $(this).data("classify").replace("_tab","_form");
	// if(searchClassify=="exam_statistics_form"){
 //        $("form.exam_detail_form").fadeIn(200);
 //        $(".form_sel_depart").hide();
 //    }else if(searchClassify=="exam_detail_form"){
 //        $(".form_sel_depart").show();
 //    }
	var bodyClassify = $(this).data("classify").replace("_tab","_content");
	$("div[class$='_content']:not(."+bodyClassify+")").slideUp(250,"swing",function(){
		$("div."+bodyClassify).slideDown(300);
        if(bodyClassify == "exam_statistics_content"){
            $("form.exam_detail_form").fadeIn(100);
            $(".form_sel_depart").fadeOut(50,function(){
                examStatisInit();
            });
        }else if(bodyClassify == "exam_detail_content"){
            $(".form_sel_depart").fadeIn(50,function(){
                $(".presentation_div ul li:nth-child(1)").trigger("click");
            });
        }
	});
});

// 分页切换
$(".presentation_div ul>li").click(function(){
    setCoverWH();
    $(this).addClass("active").siblings("li").removeClass("active");
    var presentationClassify = $(this).data("classify").replace("_presentation","_detable");
    $("div[class$='_detable']:not(."+presentationClassify+")").slideUp(250,"swing",function(){
        $("div."+presentationClassify).slideDown(250);
        if(presentationClassify == "exam_detail_more_detable"){
            presentSwitchClassify = "detail";
            $(".exam_btn_div input[value='添加']").fadeOut(125, function(){
                $("form.exam_detail_form, .exam_btn_div .InScore").fadeIn(125, function(){
                    // 请求数据
                    $("#exam_detail_freshen").trigger("click");
                });
                $(".exam_btn_div .InScore").val("录入分数");
            });
        }else if(presentationClassify == "exam_detail_notice_detable"){
            getAssessNotice(1);
            presentSwitchClassify = "notice";
            $("form.exam_detail_form, .exam_btn_div .InScore").fadeOut(125, function(){
                $(".exam_btn_div input[value='添加']").fadeIn(125);
            });
        }
    });
});

// 窗口resize
$(window).on("resize",function(){
	var iw = $(".exam_statistics_chart").width();
	$('#exam_statis_chart').css({"width": iw+"px"});
});

// 科目切换事件
$("select[name='subject_select']").on("change",function(){
    var subject = $(this).val();
    var paperClassify = $(this).attr("id").replace("_subject","_paper");
    getQuesBySubject(subject,function(data){
        var str = '';
        data.map(function(currentValue, index, arr){
            if(index > 0){
                str+='<option value="'+currentValue.Number+'" data-tit="'+currentValue.Title+'">'+currentValue.Number+'&nbsp;&nbsp;'+currentValue.Title.substring(0,10)+'</option>';
            }
        });
        $("#"+paperClassify).empty().append(str);
    },function(){
        // always回调
    });
});

// 数据统计页面刷新 & 详细信息页面搜索刷新
$("#exam_detail_freshen").click(function(){
    if($(".form_sel_depart").is(':visible')){
        getDetailBySearch(1);
    }else{
        var iiSubject = $("#exam_detail_subject").val();
        var searchNumber = $("#exam_detail_paper").val();
        var text = iiSubject;
        var subText = searchNumber;
        chartGetData(searchNumber,text,subText);
    }
    $(".exam_btn_div .InScore").val("录入分数");
});