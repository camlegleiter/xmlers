TaskManager.module("Create", function(Module, App, Backbone, Marionette, $, _) {
    App.addRegions({
        questionOptions: '#questionOptions',
        mainForm: '#form'
    });
    
    App.addInitializer(function(options) {
        App.questionOptions.show(new Module.QuestionOptionsView());
        App.mainForm.show(new Module.FormView());
    });
});