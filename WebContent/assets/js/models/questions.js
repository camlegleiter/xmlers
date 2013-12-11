'use strict';

/**
 * An abstract model that acts as a super class for the different question types
 */
TaskManager.Models.Question = Backbone.Model.extend({
    defaults: {}
});

/**
 * Stores a collection of questions in the form
 */
TaskManager.Collections.Questions = Backbone.Collection.extend({
    model: function(attrs, options) {
    	/*
    	 * See the question JSON template in the form_metadata.json
    	 * 
    	 * If the question type is set, create a new Question model based on
    	 * that type. Otherwise, just create a default Question.
    	 */
        return (attrs.type) ? new TaskManager.Models[attrs.type](attrs, options) : new TaskManager.Models.Question(attrs, options);
    }
});


/**
 * A Backbone model for representing a Checkbox field type.
 */
TaskManager.Models.Checkbox = TaskManager.Models.Question.extend({
    initialize: function() {
    	
		/*
		 * Ensures that creating a new form or instantiating a form with
		 * existing data correctly sets checkboxes as a Backbone collection.
		 */
        this.set('checkboxes', new TaskManager.Collections.Checkboxes(this.get('checkboxes')));
    },
    
	/*
	 * The basic model entries. See the form_metadata.json for more information
	 * on the values used. All values listed here are defaults in the case that
	 * the key hasn't been set in the form itself.
	 */
    defaults: function() {
        return {
            type: 'Checkbox',
            prompt: '',
            checkboxes: []
        };
    }
});

/**
 * A Backbone collection for storing multiple CheckboxItem models
 */
TaskManager.Collections.Checkboxes = Backbone.Collection.extend({
    model: function(attrs, options) {
        return new TaskManager.Models.CheckboxItem(attrs, options);
    }
});

/**
 * A Backbone model for representing one HTML checkbox input
 */
TaskManager.Models.CheckboxItem = Backbone.Model.extend({
    defaults: {
        label: ''
    }
});


/**
 * A Backbone model for representing a Radio field type.
 */
TaskManager.Models.Radio = TaskManager.Models.Question.extend({
    initialize: function() {
    	
		/*
		 * Ensures that creating a new form or instantiating a form with
		 * existing data correctly sets radios as a Backbone collection.
		 */
        this.set('radios', new TaskManager.Collections.Radios(this.get('radios')));
    },
    
	/*
	 * The basic model entries. See the form_metadata.json for more information
	 * on the values used. All values listed here are defaults in the case that
	 * the key hasn't been set in the form itself.
	 */
    defaults: function() {
        return {
            type: 'Radio',
            prompt: '',
            radios: []
        };
    }
});

/**
 * A Backbone collection for storing multiple RadioItem models
 */
TaskManager.Collections.Radios = Backbone.Collection.extend({
    model: function(attrs, options) {
        return new TaskManager.Models.RadioItem(attrs, options);
    }
});

/**
 * A Backbone model for representing one HTML radio input
 */
TaskManager.Models.RadioItem = Backbone.Model.extend({
    defaults: {
        label: ''
    }
});


/**
 * A Backbone model for representing a Text input.
 */
TaskManager.Models.Textbox = TaskManager.Models.Question.extend({
	/*
	 * The basic model entries. See the form_metadata.json for more information
	 * on the values used. All values listed here are defaults in the case that
	 * the key hasn't been set in the form itself.
	 */
    defaults: function() {
        return {
            type: 'Textbox',
            prompt: '',
            maxLength: 1000
        };
    }
});


/**
 * A Backbone model for representing a Radio field type.
 */
TaskManager.Models.Select = TaskManager.Models.Question.extend({
    initialize: function() {
    	
		/*
		 * Ensures that creating a new form or instantiating a form with
		 * existing data correctly sets options as a Backbone collection.
		 */
        this.set('options', new TaskManager.Collections.SelectOptions(this.get('options')));
    },
    
	/*
	 * The basic model entries. See the form_metadata.json for more information
	 * on the values used. All values listed here are defaults in the case that
	 * the key hasn't been set in the form itself.
	 */
    defaults: function() {
        return {
            type: 'Select',
            prompt: '',
            // Defines if the select allows for multiple options
            // false means single select
            isMulti: false,
            
            options: []
        };
    }
});

/**
 * A Backbone collection for storing multiple SelectOption models
 */
TaskManager.Collections.SelectOptions = Backbone.Collection.extend({
    model: function(attrs, options) {
        return new TaskManager.Models.SelectOption(attrs, options);
    }
});

/**
 * A Backbone model for representing one HTML option for a select tag
 */
TaskManager.Models.SelectOption = Backbone.Model.extend({
    defaults: {
        value: ''
    }
});