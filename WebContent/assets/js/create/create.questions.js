'use strict';

TaskManager.module("Create", function(Module, App, Backbone, Marionette, $, _) {
    Module.QuestionOptionView = Backbone.Marionette.ItemView.extend({
        template: '#question-option-template',
        tagName: 'a',
        className: 'list-group-item',
        
        events: {
            'change :radio': 'onRadioChange'
        },
        
        ui: {
            radio: ':radio'
        },
        
        initialize: function(options) {
            this.questionOptionLabel = options.model.get('questionOptionLabel');
        },
        
        serializeData: function() {
	        return {
	        	id: this.model.cid,
	        	label: this.questionOptionLabel
	        };
        },
        
        onRadioChange: function() {
            if (this.ui.radio.is(':checked')) {
                this.model.trigger('change:radio', this.questionOptionLabel);
            }
        }
    });
    
    Module.QuestionOptionsView = Backbone.Marionette.CompositeView.extend({
        template: '#question-options-template',
        itemView: Module.QuestionOptionView,
        itemViewContainer: '.list-group',
        
        events: {
            'click .add': 'onAddQuestion'
        },
        
        ui: {
            addButton: '.add'
        },
        
        initialize: function() {
            this.collection = new App.Collections.QuestionOptions();
            
            this.listenTo(this.collection, 'change:radio', this.onSetSelectedQuestion);
        },
        
        onRender: function() {
            this.collection.add([
                { questionOptionLabel: 'Textbox' },
                { questionOptionLabel: 'Radio' },
                { questionOptionLabel: 'Checkbox' },
                { questionOptionLabel: 'Select' }
            ]);
        },
        
        onAddQuestion: function() {
            if (this.selectedQuestion)
                this.trigger('add:question', this.selectedQuestion);
        },
        
        onSetSelectedQuestion: function(question) {
            this.selectedQuestion = question;
            $(this.ui.addButton).removeClass('disabled');
        }
    });
    
    App.Collections.QuestionOptions = Backbone.Collection.extend();
});