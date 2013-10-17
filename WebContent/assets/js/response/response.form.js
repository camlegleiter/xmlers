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
        
        onCancel: function() {
            if (confirm("The current form will not be saved. Are you sure you wish to cancel?")) {
                window.location.href = 'index.jsp';
            }
        },
        
        appendHtml: function(collectionView, itemView) {
            collectionView.$('.response-content').append(itemView.el);
        }
    });
    
    /*
     * 
     */
    Module.ResponseView = Backbone.Marionette.ItemView.extend({

    });
    
    /*
     * 
     */
    Module.ResponsesView = Backbone.Marionette.CompositeView.extend({
        className: 'response',

        appendHtml: function(collectionView, itemView) {
            collectionView.$('.content').append(itemView.el);
        }
    });
    
    
    /*
     * 
     */
    Module.CheckboxItemView = Module.ResponseView.extend({
        template: '#checkbox-item-template',
        
        events: {

        },
        
        ui: {
            
        },
        
        initialize: function() {
            console.log();
        }
    });
    
    Module.CheckboxView = Module.ResponsesView.extend({
        template: '#checkbox-template',
        tagName: 'li',
        className: 'question',
        itemView: Module.CheckboxItemView,
        
        events: {
            
        },
        
        ui: {
            
        },
        
        initialize: function() {
            this.collection = this.model.get('checkboxes');
        }
    });
    
    
    /*
     * 
     */
    Module.RadioItemView = Module.ResponseView.extend({
        template: '#radio-item-template',
        
        events: {
            'change input': 'onUpdateSelection'
        },
        
        ui: {
        
        },
        
        onUpdateSelection: function() {
            this.trigger('radio:updateSelect', this.model.get('label'));
        }
    });
    
    Module.RadioView = Module.ResponsesView.extend({
        template: '#radio-template',
        tagName: 'li',
        className: 'question',
        itemView: Module.RadioItemView,
        
        initialize: function(options) {
            this.collection = this.model.get('radios');
        },
        
        onRender: function() {
            console.log(this.el);
        }
    });
    
    
    /*
     * 
     */
    Module.TextboxView = Module.ResponsesView.extend({
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
        
        updateCharactersLeft: function() {
            var left = this.model.get('maxLength') - this.ui.textarea.val().length;
            if (left < 0) {
                this.ui.charsLeft.toggleClass('error', true).text(left);
                return false;
            } else {
                this.ui.charsLeft.toggleClass('error', false).text(left);
                return true;
            }
        }
    });
    
    
    /*
     * 
     */
    Module.SelectOptionItemView = Module.ResponseView.extend({
        template: '#select-option-item-template',
        tagName: 'option',
        
        events: {
            
        },
        
        ui: {
            
        }
    });
    
    Module.SelectView = Module.ResponsesView.extend({
        template: '#select-template',
        tagName: 'li',
        className: 'question',
        itemView: Module.SelectOptionItemView,
        
        events: {
            
        },
        
        ui: {
            
        },

        initialize: function(options) {
            this.collection = this.model.get('options');
        }
    });
});