'use strict';

TaskManager.Models.Form = Backbone.Model.extend({
    initialize: function() {
        this.set('formQuestions', new TaskManager.Collections.Questions(this.get('formQuestions')));
    },
    
    defaults: function() {
        return {
            formID: -1,
            formName: '',
            formDescription: '',
            formOwner: -1,
            formParticipants: [],
            participantsCanSeeAll: false,
            participantsCanEditResponse: false,
            participantResponseIsRequired: true,
            formQuestions: []
        };
    }
});

TaskManager.Collections.Forms = Backbone.Collection.extend({
    model: TaskManager.Models.Form
});