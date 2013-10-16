'use strict';

/*
 * An abstract model that acts as a super class for the different question types
 */
TaskManager.Models.Question = Backbone.Model.extend({
    defaults: {}
});

/*
 * Stores a collection of questions in the form
 */
TaskManager.Collections.Questions = Backbone.Collection.extend({
    model: TaskManager.Models.Question
});


/*
 * 
 */
TaskManager.Models.Checkbox = TaskManager.Models.Question.extend({
    initialize: function() {
        this.set('checkboxes', new TaskManager.Collections.Checkboxes(this.get('checkboxes')));
    },
    
    defaults: function() {
        return {
            type: 'Checkbox',
            prompt: '',
            checkboxes: []
        };
    }
});

TaskManager.Collections.Checkboxes = Backbone.Collection.extend({
    model: TaskManager.Models.CheckboxItem
});

TaskManager.Models.CheckboxItem = Backbone.Model.extend({
    defaults: {
        label: ''
    }
});


/*
 * 
 */
TaskManager.Models.Radio = TaskManager.Models.Question.extend({
    initialize: function() {
        this.set('radios', new TaskManager.Collections.Radios(this.get('radios')));
    },
    
    defaults: function() {
        return {
            type: 'Radio',
            prompt: '',
            radios: []
        };
    }
});

TaskManager.Collections.Radios = Backbone.Collection.extend({
    model: TaskManager.Models.RadioItem
});

TaskManager.Models.RadioItem = Backbone.Model.extend({
    defaults: {
        label: ''
    }
});


/*
 * 
 */
TaskManager.Models.Textbox = TaskManager.Models.Question.extend({
    defaults: function() {
        return {
            type: 'Textbox',
            prompt: '',
            maxLength: 1000
        };
    }
});


/*
 *
 */
TaskManager.Models.Select = TaskManager.Models.Question.extend({
    initialize: function() {
        this.set('options', new TaskManager.Collections.SelectOptions(this.get('options')));
    },
    
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

TaskManager.Collections.SelectOptions = Backbone.Collection.extend({
    model: TaskManager.Models.SelectOption
});

TaskManager.Models.SelectOption = Backbone.Model.extend({
    defaults: {
        value: ''
    }
});