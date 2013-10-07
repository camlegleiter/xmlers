'use strict';

TaskManager.module("Create", function(Module, App, Backbone, Marionette, $, _) {
    this.startWithParent = false;
    
    App.addRegions({
        questionView: '#question-options',
        formView: '#user-form'
    });
    
    Module.addInitializer(function(options) {
        var formModel = new App.Models.Form();
        var questionView = new Module.QuestionOptionsView();
        var formView = new Module.FormView({
            model: formModel
        });
        
        Module.listenTo(questionView, 'add:question', function(question) {
            formView.addQuestion(question);
        });
        
        App.questionView.show(questionView);
        App.formView.show(formView);
    });
});