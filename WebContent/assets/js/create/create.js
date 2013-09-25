TaskManager.module("Create", function(Module, App, Backbone, Marionette, $, _) {
    this.startWithParent = false;
    
    App.addRegions({
        questionOptions: '#question-options',
        mainForm: '#form'
    });
    
    Module.addInitializer(function(options) {
        App.questionOptions.show(new Module.QuestionOptionsView());
        //App.mainForm.show(new Module.FormView());
    });
});