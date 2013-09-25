TaskManager.Models.Form = Backbone.Model.extend({
    defaults: function() {
        return {
            formID: -1,
            formName: '',
            formDescription: '',
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
TaskManager.Collections.Questions = new Backbone.Collection.extend({
    model: TaskManager.Models.Question
});




/*
 * 
 */
TaskManager.Models.Checkbox = TaskManager.Models.Question.extend({
    defaults: function() {
        return {
            type: 'checkbox',
            description: '',
            checkboxes: new TaskManager.Collections.Checkboxes()
        };
    }
});

TaskManager.Collections.Checkboxes = Backbone.Collection.extend({
    model: TaskManager.Models.CheckboxItem
});

TaskManager.Models.CheckboxItem = Backbone.Model.extend({
    defaults: {
        checkboxLabel: ''
    }
});



/*
 * 
 */
TaskManager.Models.Radio = TaskManager.Models.Question.extend({
    defaults: function() {
        return {
            type: 'radio',
            description: '',
            radios: new TaskManager.Collections.Radios()
        };
    }
});

TaskManager.Collections.Radios = Backbone.Collection.extend({
    model: TaskManager.Models.RadioItem
});

TaskManager.Models.RadioItem = Backbone.Model.extend({
    defaults: {
        radioLabel: ''
    }
});



/*
 * 
 */
TaskManager.Models.Textbox = TaskManager.Models.Question.extend({
    defaults: function() {
        return {
            type: 'textbox',
            description: '',
            maxLength: 0
        };
    }
});



/*
 *
 */
TaskManager.Models.Select = TaskManager.Models.Question.extend({
    defaults: function() {
        return {
            type: 'select',
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
        optionValue: ''
    }
});