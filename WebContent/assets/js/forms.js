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

TaskManager.Collections.Forms = Backbone.Collection.extend({
    model: TaskManager.Models.Form
});

/*
 * 
 */
TaskManager.EmptyView = Backbone.Marionette.ItemView.extend({
    template: '#empty-view-template',
    className: 'control-group',

    initialize: function(options) {
        this.message = options.message;
    },
    
    serializeData: function() {
        return {
            message: this.message
        };
    }
});