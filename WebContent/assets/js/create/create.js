TaskManager.module("Create", function(Module, App, Backbone, Marionette, $, _) {
    App.addRegions({
        questionOptions: '#questions',
        mainForm: '#form'
    });
    
    App.addInitializer(function(options) {
        App.mainForm.show(new Module.FormView());
    });
});