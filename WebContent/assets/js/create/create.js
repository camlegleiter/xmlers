'use strict';

TaskManager.module('Create', function(Module, App, Backbone, Marionette, $, _) {
    this.startWithParent = false;
    
    App.addRegions({
        questionView: '#question-options',
        formView: '#user-form'
    });
    
    Module.addInitializer(function(options) {
        var questionView = new Module.QuestionOptionsView();
        var formView = new Module.FormView({
            model: options.model
        });
        
        Module.listenTo(questionView, 'add:question', function(question) {
            formView.addQuestion(question);
        });
        
        App.questionView.show(questionView);
        App.formView.show(formView);
    });
});