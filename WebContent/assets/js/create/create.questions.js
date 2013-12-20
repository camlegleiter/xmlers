'use strict';

TaskManager.module('Create', function(Module, App, Backbone, Marionette, $, _) {
	
	/**
	 * An individual entry for the user to select from. By default, there are
	 * four entry options: Textbox, Checkbox, Radio and Select. Later implementations
	 * may include complex types, which are made up of the four default types.
	 */
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
        	// Set the displayed label that is shown on the UI
            this.questionOptionLabel = options.model.get('questionOptionLabel');
        },
        
        // Used for the HTML template
        serializeData: function() {
	        return {
	        	id: this.model.cid,
	        	label: this.questionOptionLabel
	        };
        },
        
        // If this option gets selected, inform the collection
        onRadioChange: function() {
            if (this.ui.radio.is(':checked')) {
                this.model.trigger('change:radio', this.questionOptionLabel);
            }
        }
    });
    
    /**
     * The overall view for storing the different entry types that the user can
     * select from.
     */
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
        	/*
        	 * Adds the default entry options to the collection after rendering.
        	 * Later implementations should fix this to have bootstrapped data
        	 * provided when the page loads so all possible entry types are
        	 * accessible.
        	 */
            this.collection.add([
                { questionOptionLabel: 'Textbox' },
                { questionOptionLabel: 'Radio' },
                { questionOptionLabel: 'Checkbox' },
                { questionOptionLabel: 'Select' }
            ]);
        },
        
        // When the "Add Entry" button is clicked, trigger an event to the
        // FormView
        onAddQuestion: function() {
            if (this.selectedQuestion)
                this.trigger('add:question', this.selectedQuestion);
        },
        
        // Selecting a different entry type should update the internally selected
        // question
        onSetSelectedQuestion: function(question) {
            this.selectedQuestion = question;
            $(this.ui.addButton).removeClass('disabled');
        }
    });
    
    App.Collections.QuestionOptions = Backbone.Collection.extend();
});