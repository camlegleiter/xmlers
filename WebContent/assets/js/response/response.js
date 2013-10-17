'use strict';

TaskManager.module("Response", function(Module, App, Backbone, Marionette, $, _) {
    this.startWithParent = false;
    
    App.addRegions({
        responseContent: '#response-form'
    });
    
    Module.addInitializer(function(model) {
        var formView = new Module.FormView({
            model: model
        });
        
        App.responseContent.show(formView);
    });
});