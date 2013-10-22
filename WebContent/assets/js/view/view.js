'use strict';

TaskManager.module('View', function(Module, App, Backbone, Marionette, $, _) {
    this.startWithParent = false;
    
    App.addRegions({
        viewOptions: '#viewOptions',
        responseTable: '#responseTable'
    });
    
    Module.addInitializer(function(options) {
//        var responseOptions = new Module.ResponseOptionsView();
        var tableLayout = new Module.TableLayout({
            model: options.model
        });
        
//        Module.listenTo(questionView, 'add:question', function(question) {
//            formView.addQuestion(question);
//        });
        
//        App.questionView.show(questionView);
        App.responseTable.show(tableLayout);
    });
});