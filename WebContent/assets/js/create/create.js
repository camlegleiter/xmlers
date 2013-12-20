'use strict';

TaskManager.module('Create', function(Module, App, Backbone, Marionette, $, _) {
    this.startWithParent = false;
    
    App.addRegions({
        questionView: '#question-options',
        formView: '#user-form'
    });
    
    // Add an initializer to be called when starting the Create module
    Module.addInitializer(function(options) {
        var questionView = new Module.QuestionOptionsView();
        var formView = new Module.FormView({
            model: options.model
        });
        
        // Add listener between the two views
        Module.listenTo(questionView, 'add:question', function(question) {
            formView.addQuestion(question);
        });
        
        // Render the two views
        App.questionView.show(questionView);
        App.formView.show(formView);
    });
});