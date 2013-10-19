'use strict';

TaskManager.Models.Response = Backbone.Model.extend({
    initialize: function() {
        this.set('responses', new TaskManager.Collections.QuestionResponses(this.get('responses')));
    },
    
    defaults: function() {
        return {
            formID: -1,
            responseOwner: -1,
            formParticipants: [],
            responses: []
        };
    }
});

TaskManager.Collections.Responses = Backbone.Collection.extend({
    model: TaskManager.Models.Response
});


/*
 * An abstract model that acts as a super class for the different response types
 */
TaskManager.Models.QuestionResponse = Backbone.Model.extend({
    defaults: {}
});

/*
 * Stores a collection of responses in the overall model
 */
TaskManager.Collections.QuestionResponses = Backbone.Collection.extend({
    model: TaskManager.Models.QuestionResponse
});


/*
 * 
 */
TaskManager.Models.CheckboxResponse = TaskManager.Models.QuestionResponse.extend({
    defaults: function() {
        return {
            questionID: -1,
            type: 'Checkbox',
            values: new TaskManager.Collections.CheckboxResponses()
        };
    }
});

TaskManager.Collections.CheckboxResponses = Backbone.Collection.extend({
    model: TaskManager.Models.CheckboxValue
});

TaskManager.Models.CheckboxValue = Backbone.Model.extend({
    defaults: {
        value: ''
    }
});


/*
 * 
 */
TaskManager.Models.RadioResponse = TaskManager.Models.QuestionResponse.extend({
    defaults: function() {
        return {
            questionID: -1,
            type: 'Radio',
            values: new TaskManager.Collections.RadioResponses()
        };
    }
});

TaskManager.Collections.RadioResponses = Backbone.Collection.extend({
    model: TaskManager.Models.RadioValue
});

TaskManager.Models.RadioValue = Backbone.Model.extend({
    defaults: {
        value: ''
    }
});


/*
 * 
 */
TaskManager.Models.Textbox = TaskManager.Models.QuestionResponse.extend({
    defaults: function() {
        return {
            questionID: -1,
            type: 'Textbox',
            value: ''
        };
    }
});


/*
 *
 */
TaskManager.Models.SelectResponse = TaskManager.Models.QuestionResponse.extend({
    defaults: function() {
        return {
            questionID: -1,
            type: 'Select',
            values: new TaskManager.Collections.SelectResponses()
        };
    }
});

TaskManager.Collections.SelectResponses = Backbone.Collection.extend({
    model: TaskManager.Models.SelectValue
});

TaskManager.Models.SelectValue = Backbone.Model.extend({
    defaults: {
        value: ''
    }
});
