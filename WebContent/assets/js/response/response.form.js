'use strict';

TaskManager.module("Response", function(Module, App, Backbone, Marionette, $, _) {
    
    /*
     * 
     */
    Module.FormView = Backbone.Marionette.CompositeView.extend({
        template: '#response-template',
        getItemView: function(item) {
            return Module[item.get('type') + 'View'];
        },
        emptyView: function() {
            return new App.EmptyView({
                message: 'This form has no questions to fill out!'
            });
        },
        
        events: {
            'click .submit': 'onSubmit',
            'click .cancel': 'onCancel'
        },
        
        ui: {
            submit: '.submit',
            cancel: '.cancel',
            
            loading: '.loading'
        },
        
        initialize: function(options) {
            this.collection = this.model.get('formQuestions');
        },
        
        onSubmit: function() {
            if (this.inputsAreValid()) {
                
                this.toggleButtonsDisabled(true);
                this.ui.loading.show();
                
                var self = this;
                $.post('/xmlers/app/upsertResponse', {
                    model: JSON.stringify(this.model)
                }).done(function(data, textStatus, jqXHR) {
                    console.log(textStatus);
                }).error(function(jqXHR, textStatus, errorThrown) {
                    console.log(textStatus);
                    console.log(errorThrown);
                }).always(function() {
                    self.toggleButtonsDisabled(false);
                    self.ui.loading.hide();
                });
            }
        },
        
        onCancel: function() {
            return confirm("The current form will not be saved. Are you sure you wish to cancel?");
        },
        
        appendHtml: function(collectionView, itemView) {
            collectionView.$('.response-content').append(itemView.el);
        },
        
        inputsAreValid: function() {
            var $inputs = this.$('.required-input');
            for (var i = 0; i < $inputs.length; ++i) {
                if ($($inputs[i]).is(':invalid')) {
                    $($inputs[i]).focus();
                    return false;
                }
            }
            
            return true;
        },
        
        toggleButtonsDisabled: function(isDisabled) {
            this.ui.submit.toggleClass('disabled', isDisabled);
            this.ui.cancel.toggleClass('disabled', isDisabled);
        }
    });
    
    
    /*
     * 
     */
    Module.CheckboxItemView = Backbone.Marionette.ItemView.extend({
        template: '#checkbox-item-template',
        
        events: {
            'change input': 'onUpdateCheckbox'
        },
        
        initialize: function(options) {
            this.checkboxNameId = options.checkboxNameId;
        },
        
        onUpdateCheckbox: function() {
            this.trigger('checkbox:update', this.model.get('label'));
        },
        
        serializeData: function() {
            return {
                data: this.model.attributes,
                id: this.checkboxNameId
            };
        }
    });
    
    Module.CheckboxView = Backbone.Marionette.CompositeView.extend({
        template: '#checkbox-template',
        tagName: 'li',
        className: 'question',
        
        itemView: Module.CheckboxItemView,
        itemViewOptions: function(model, index) {
            return {
                checkboxNameId: this.cid
            };
        },
        
        initialize: function() {
            this.collection = this.model.get('checkboxes');
        },
        
        onUpdateCheckbox: function(view, label) {
            this.model.set('value', label);
        },
        
        appendHtml: function(collectionView, itemView) {
            collectionView.$('.content').append(itemView.el);
        }
    });
    
    
    /*
     * 
     */
    Module.RadioItemView = Backbone.Marionette.ItemView.extend({
        template: '#radio-item-template',
        
        events: {
            'change input': 'onUpdateRadio'
        },
        
        initialize: function(options) {
            this.radioNameId = options.radioNameId;
        },
        
        onUpdateRadio: function() {
            this.trigger('radio:update', this.model.get('label'));
        },
        
        serializeData: function() {
            return {
                data: this.model.attributes,
                id: this.radioNameId
            };
        }
    });
    
    Module.RadioView = Backbone.Marionette.CompositeView.extend({
        template: '#radio-template',
        tagName: 'li',
        className: 'question',
        
        itemView: Module.RadioItemView,
        itemViewOptions: function(model, index) {
            return { 
                radioNameId: this.cid
            };
        },
        
        initialize: function(options) {
            this.collection = this.model.get('radios');
            
            this.on('itemview:radio:update', this.onUpdateRadio);
        },
        
        onUpdateRadio: function(view, label) {
            this.model.set('value', label);
        },
        
        appendHtml: function(collectionView, itemView) {
            collectionView.$('.content').append(itemView.el);
        }
    });
    
    
    /*
     * 
     */
    Module.TextboxView = Backbone.Marionette.CompositeView.extend({
        template: '#textbox-template',
        tagName: 'li',
        className: 'question',
        
        events: {
            'input textarea': 'onUpdateModel'
        },
        
        ui: {
            textarea: 'textarea',
            charsLeft: '.character-count'
        },
        
        onUpdateModel: function() {
            if (this.updateCharactersLeft()) {
                this.model.set('value', this.ui.textarea.val());
            }
        },
        
        toggleError: function(isError) {
            return this.ui.charsLeft.toggleClass('error', isError);
        },
        
        updateCharactersLeft: function() {
            var left = parseInt(this.model.get('maxLength'), 10) - this.ui.textarea.val().length;
            if (left < 0) {
                this.toggleError(true).text(left);
                return false;
            } else {
                this.toggleError(false).text(left);
                return true;
            }
        }
    });
    
    
    /*
     * 
     */
    Module.SelectOptionItemView = Backbone.Marionette.ItemView.extend({
        template: '#select-option-item-template',
        tagName: 'option'
    });
    
    Module.SelectView = Backbone.Marionette.CompositeView.extend({
        template: '#select-template',
        tagName: 'li',
        className: 'question',
        itemView: Module.SelectOptionItemView,
        
        ui: {
            select: 'select'
        },

        initialize: function(options) {
            this.collection = this.model.get('options');
            this.isMulti = this.model.get('isMulti');            
        },
        
        onRender: function() {
            this.ui.select.select2({
                width: '300px',
                placeholder: 'Select a value'
            });
            
            var self = this;
            this.ui.select.on('change', function(e) {
                self.model.set('values', e.val);
            });
        },
        
        appendHtml: function(collectionView, itemView) {
            collectionView.$('.content').append(itemView.el);
        }
    });
});