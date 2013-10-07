'use strict';

TaskManager.Models.Form = Backbone.Model.extend({
    defaults: function() {
        return {
            formID: -1,
            formName: '',
            formDescription: '',
            formOwner: -1,
            formParticipants: [],
            participantsCanSeeAll: false,
            formQuestions: new TaskManager.Collections.Questions()
        };
    }
});


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
    defaults: function() {
        return {
            type: 'Checkbox',
            prompt: '',
            checkboxes: new TaskManager.Collections.Checkboxes()
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
    defaults: function() {
        return {
            type: 'Radio',
            prompt: '',
            radios: new TaskManager.Collections.Radios()
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
    defaults: function() {
        return {
            type: 'Select',
            prompt: '',
            // Defines if the select allows for multiple options
            // false means single select
            isMulti: false,
            
            options: new TaskManager.Collections.SelectOptions()
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