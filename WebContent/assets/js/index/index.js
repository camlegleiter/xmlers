'use strict';

TaskManager.module("Index", function(Module, App, Backbone, Marionette, $, _) {
    this.startWithParent = false;
    
    App.addRegions({
        forms: '#forms',
        formContent: '#form-contents'
    });
    
    Module.addInitializer(function(options) {
        var formsLayout = new Module.FormsLayout(options);
        var formContentsView = new Module.FormContentsView();
        
        Module.listenTo(formsLayout, 'select:form', function(options) {
            formContentsView.showForm(options);
        });
        
        App.forms.show(formsLayout);
        App.formContent.show(formContentsView);
    });
});