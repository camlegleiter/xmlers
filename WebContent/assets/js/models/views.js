'use strict';

TaskManager.Models.View = Backbone.Model.extend({
    initialize: function() {
        this.set('formQuestions', new TaskManager.Collections.Questions(this.get('formQuestions')));
        this.set('responses', new TaskManager.Collections.Responses(this.get('responses')));
    },
    
    defaults: function() {
        return {
            formID: -1,
            formName: '',
            formDescription: '',
            formOwner: -1,
            formQuestions: [],
            responses: []
        };
    }
});

